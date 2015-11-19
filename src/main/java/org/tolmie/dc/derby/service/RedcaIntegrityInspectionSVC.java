package org.tolmie.dc.derby.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.tolmie.dc.derby.dao.RedcaIntegrityInspectionDao;
import org.tolmie.dc.derby.exception.RedCAException;
import org.tolmie.dc.derby.integrity.DBVendor;
import org.tolmie.dc.derby.integrity.InspectStrategy;
import org.tolmie.dc.derby.integrity.Schema;
import org.tolmie.dc.derby.integrity.conflict.ConflictType;
import org.tolmie.dc.derby.integrity.conflict.DataTypeConflict;
import org.tolmie.dc.derby.integrity.conflict.LengthConflict;
import org.tolmie.dc.derby.integrity.conflict.NullableConfliict;
import org.tolmie.dc.derby.integrity.conflict.OrderConflict;




@Service
public class RedcaIntegrityInspectionSVC {
	
	private static final Logger logger = LoggerFactory.getLogger(RedcaIntegrityInspectionSVC.class);
	
	@Autowired
	private RedcaIntegrityInspectionDao derbyInntegrityInsepctionDao;
	
	List<ConflictType> conflictList = new ArrayList<ConflictType>();
	List<HashMap> missedColumnList = new ArrayList<HashMap>();
	
//	private @Value("#{application['derby.repo.schemaName']}")   String derbySchemaName; // = "SRC";
//	private @Value("#{application['outbound.repo.schemaName']}")   String outboundSchemaName; // = "OPENPMS_SRC_IF";
	

	private @Value("${source.repo.schemaName}")   String derbySchemaName; // = "SRC";
	private @Value("${target.repo.schemaName}")   String outboundSchemaName; // = "OPENPMS_SRC_IF";

	
	@SuppressWarnings("unchecked")
	public boolean checkSchema(String paramRedcaSchemaName) {
		List<HashMap> list = null;
		
		boolean result = false;
		list = derbyInntegrityInsepctionDao.checkSchema(paramRedcaSchemaName);
		if(list.contains(paramRedcaSchemaName)) result = true;
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
//	public void compare(String derbySchema, String outboundSchemaName) throws RedCAException {
	public void compare() throws RedCAException {

//		boolean result = true;
		InspectStrategy is = new InspectStrategy();
		
		List<HashMap> derbyList =  derbyInntegrityInsepctionDao.selectIfTableColumnInformationFromDerby("IF_");
		List<HashMap> tiberoList = derbyInntegrityInsepctionDao.selectIfTableColumnInformationFromTibero("IF_", outboundSchemaName);
		
		List<HashMap> sourceNormalizedList = is.normalizeSchemaInformation(DBVendor.derby, derbyList);
		List<HashMap> targetNormalizedList = is.normalizeSchemaInformation(DBVendor.tibero, tiberoList);
		
		DataTypeConflict dc = null;
		LengthConflict lc  = null;
		NullableConfliict nc  = null;
		OrderConflict oc  = null;
		
		missedColumnList = new ArrayList<HashMap>(sourceNormalizedList);
		
		for(HashMap srow : sourceNormalizedList) {
			
			String sourceTableName = (String) srow.get(splitCamelCase(Schema.tableName.toString()));
			String sourceColumnName = (String) srow.get(splitCamelCase(Schema.columnName.toString()));
			String sourceColumnOrder = ( (Integer) srow.get(splitCamelCase(Schema.columnOrder.toString())) ).toString();
			String sourceColumnType = (String) srow.get(splitCamelCase(Schema.dataType.toString()));
//			String sourceColumnType = (String) srow.get(splitCamelCase(Schema.columnType.toString()));
			String sourceDataLength = ( (Integer) srow.get(splitCamelCase(Schema.dataLength.toString())) ).toString();
			String sourceNullable = (String) srow.get(splitCamelCase(Schema.nullable.toString()));
			
			for(HashMap trow : targetNormalizedList) {
				
				String targetTableName = (String) trow.get(splitCamelCase(Schema.tableName.toString()));
				String targetColumnName = (String) trow.get(splitCamelCase(Schema.columnName.toString()));
				String targetColumnOrder = ((BigDecimal) trow.get(splitCamelCase(Schema.columnOrder.toString()))).toString()   ;
				String targetColumnType = (String) trow.get(splitCamelCase(Schema.dataType.toString()));
//				String targetColumnType = (String) trow.get(splitCamelCase(Schema.columnType.toString()));
				String targetDataLength =  ((BigDecimal) trow.get(splitCamelCase(Schema.dataLength.toString()))).toString();
				String targetNullable = (String) trow.get(splitCamelCase(Schema.nullable.toString()));

				// case when table & column matched 
				if(sourceTableName.equals(targetTableName) && sourceColumnName.equals(targetColumnName) ) {
					
					// 1. compare column type
					if(!is.checkDataTypeWithVariance(sourceColumnType, targetColumnType, is.getMappingColumnList() ))  {
						dc = new DataTypeConflict(sourceTableName, sourceColumnName, sourceColumnType, targetColumnType );
						conflictList.add(dc);
					}
					
					// 2. compare column length
					if(!sourceDataLength.equals(targetDataLength))  {
						lc = new LengthConflict(sourceTableName, sourceColumnName, sourceDataLength, targetDataLength );
						conflictList.add(lc);					
					}
					// 3. compare column nullable
					if(!sourceNullable.equals(targetNullable)) {
						nc = new NullableConfliict(sourceTableName, sourceColumnName, sourceNullable, targetNullable );
						conflictList.add(nc);						
					}
					// 4. compare column order ( in default it will be ignored..)
					if(!sourceNullable.equals(targetNullable)) {
						oc = new OrderConflict(sourceTableName, sourceColumnName, false, sourceColumnOrder, targetColumnOrder );
						conflictList.add(oc);								
					}
					
					missedColumnList.remove(srow);
					
				//case when table & column unmatched
				} else {
					//missedColumnList.add(srow);
				}
				
			}
			
		}
		
//		return result;
	}
	
//	public boolean compareIgnoreOrdering(String derbySchema, String outboundSchema) {
//		boolean result = false;
//		derbyInntegrityInsepctionDao.deleteValdObjectInfo(vo);
//		return result;
//	}
	
	public String splitCamelCase(String s) {
		   return s.replaceAll(
		      String.format("%s|%s|%s",
		         "(?<=[A-Z])(?=[A-Z][a-z])",
		         "(?<=[^A-Z])(?=[A-Z])",
		         "(?<=[A-Za-z])(?=[^A-Za-z])"
		      ),
		      "_"
		   );
		}
	
	
	
//	public void report(String derbySchemaName, String outboundSchemaName) {
	public void report() {

		if(this.checkSchema(derbySchemaName)) {
			logger.error("derby Schame doesn't exist..");
		}
		
		for(ConflictType report : conflictList) {
			logger.info(report.toString());
		}
		for(HashMap missedColumnReport : missedColumnList) {
			logger.info( "Missed Column ::" + missedColumnReport.get( splitCamelCase(Schema.tableName.toString())) + "." + missedColumnReport.get( splitCamelCase(Schema.columnName.toString()))  );
		}
		
	}

	public String getRedcaSchemaName() {
		return derbySchemaName;
	}

	public void setRedcaSchemaName(String derbySchemaName) {
		this.derbySchemaName = derbySchemaName;
	}

	public String getOutboundSchemaName() {
		return outboundSchemaName;
	}

	public void setOutboundSchemaName(String outboundSchemaName) {
		this.outboundSchemaName = outboundSchemaName;
	}
	
	
}