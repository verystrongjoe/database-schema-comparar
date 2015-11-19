package org.tolmie.dc.derby.integrity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.derby.catalog.types.TypeDescriptorImpl;

import org.tolmie.dc.derby.exception.RedCAException;


/*
 * Oracle 		: http://docs.oracle.com/javase/6/docs/api/java/sql/DatabaseMetaData.html
 * Apache Derby : https://db.apache.org/derby/docs/10.5/ref/rrefsistabs38369.html 
 * Tibero 		: The official website of Tibero company.
 * Reference 	: http://www.schemacrawler.com/
 */
public class InspectStrategy {
	
	public InspectStrategy() {
		
		mappingColumnList = new ArrayList<AcceptableColumnTypeVarianceMapping>();
		
		//make AcceptableColumnTypeVarianceMapping 
		mappingColumnList.add( new AcceptableColumnTypeVarianceMapping(DBVendor.derby, DBVendor.tibero, 
				DERBY_COLUMN_TYPE.BLOB, TIBERO_COLUMN_TYPE.BLOB ) );
		mappingColumnList.add( new AcceptableColumnTypeVarianceMapping(DBVendor.derby, DBVendor.tibero, 
				DERBY_COLUMN_TYPE.CHAR, TIBERO_COLUMN_TYPE.CHAR ) );
		mappingColumnList.add( new AcceptableColumnTypeVarianceMapping(DBVendor.derby, DBVendor.tibero, 
				DERBY_COLUMN_TYPE.CHAR_FOR_BIT_DATA, TIBERO_COLUMN_TYPE.CHAR ) );
		mappingColumnList.add( new AcceptableColumnTypeVarianceMapping(DBVendor.derby, DBVendor.tibero, 
				DERBY_COLUMN_TYPE.CLOB, TIBERO_COLUMN_TYPE.CLOB ) );
		mappingColumnList.add( new AcceptableColumnTypeVarianceMapping(DBVendor.derby, DBVendor.tibero, 
				DERBY_COLUMN_TYPE.DATE, TIBERO_COLUMN_TYPE.DATE ) );
		mappingColumnList.add( new AcceptableColumnTypeVarianceMapping(DBVendor.derby, DBVendor.tibero, 
				DERBY_COLUMN_TYPE.DECIMAL, TIBERO_COLUMN_TYPE.NUMBER ) );
		mappingColumnList.add( new AcceptableColumnTypeVarianceMapping(DBVendor.derby, DBVendor.tibero, 
				DERBY_COLUMN_TYPE.DOUBLE, TIBERO_COLUMN_TYPE.LONG ) );
		mappingColumnList.add( new AcceptableColumnTypeVarianceMapping(DBVendor.derby, DBVendor.tibero, 
				DERBY_COLUMN_TYPE.DOUBLE_PRECISION, TIBERO_COLUMN_TYPE.LONG ) );
		mappingColumnList.add( new AcceptableColumnTypeVarianceMapping(DBVendor.derby, DBVendor.tibero, 
				DERBY_COLUMN_TYPE.FLOAT, TIBERO_COLUMN_TYPE.LONG ) );
		mappingColumnList.add( new AcceptableColumnTypeVarianceMapping(DBVendor.derby, DBVendor.tibero, 
				DERBY_COLUMN_TYPE.INTEGER, TIBERO_COLUMN_TYPE.NUMBER ) );
		mappingColumnList.add( new AcceptableColumnTypeVarianceMapping(DBVendor.derby, DBVendor.tibero, 
				DERBY_COLUMN_TYPE.LONG_VARCHAR, TIBERO_COLUMN_TYPE.VARCHAR ) );
		mappingColumnList.add( new AcceptableColumnTypeVarianceMapping(DBVendor.derby, DBVendor.tibero, 
				DERBY_COLUMN_TYPE.LONG_VARCHAR_FOR_BIT_DATA, TIBERO_COLUMN_TYPE.LONG) );
		mappingColumnList.add( new AcceptableColumnTypeVarianceMapping(DBVendor.derby, DBVendor.tibero, 
				DERBY_COLUMN_TYPE.NUMERIC, TIBERO_COLUMN_TYPE.NUMBER ) );
		mappingColumnList.add( new AcceptableColumnTypeVarianceMapping(DBVendor.derby, DBVendor.tibero, 
				DERBY_COLUMN_TYPE.REAL, TIBERO_COLUMN_TYPE.LONG) );		
		mappingColumnList.add( new AcceptableColumnTypeVarianceMapping(DBVendor.derby, DBVendor.tibero, 
				DERBY_COLUMN_TYPE.SMALLINT, TIBERO_COLUMN_TYPE.NUMBER) );		 		
		mappingColumnList.add( new AcceptableColumnTypeVarianceMapping(DBVendor.derby, DBVendor.tibero, 
				DERBY_COLUMN_TYPE.TIME, TIBERO_COLUMN_TYPE.TIMESTAMP) );		 		
		mappingColumnList.add( new AcceptableColumnTypeVarianceMapping(DBVendor.derby, DBVendor.tibero, 
				DERBY_COLUMN_TYPE.TIMESTAMP, TIBERO_COLUMN_TYPE.TIMESTAMP) );		 
		mappingColumnList.add( new AcceptableColumnTypeVarianceMapping(DBVendor.derby, DBVendor.tibero, 
				DERBY_COLUMN_TYPE.VARCHAR, TIBERO_COLUMN_TYPE.VARCHAR) );		 
	}
	
	private Collection<AcceptableColumnTypeVarianceMapping> mappingColumnList;
	
	private static enum DERBY_COLUMN_TYPE implements COLUMN_TYPE { BIGINT,
		BLOB,
		CHAR,
		CHAR_FOR_BIT_DATA,
		CLOB,
		DATE,
		DECIMAL,
		DOUBLE,
		DOUBLE_PRECISION,
		FLOAT,
		INTEGER,
		LONG_VARCHAR,
		LONG_VARCHAR_FOR_BIT_DATA,
		NUMERIC,
		REAL,
		SMALLINT,
		TIME,
		TIMESTAMP,
		VARCHAR 
	};
		
	private static enum TIBERO_COLUMN_TYPE implements COLUMN_TYPE {
		CHAR,
		VARCHAR,
		VARCHAR2,
		CLOB,
		LONG,
		NUMBER,
		DATE,
		TIMESTAMP,
		BLOB,
		RAW,
		LONG_RAW,
		ROWID
	};

//	private HashMap<String, Collection<String>> acceptableColumnTypeVariance;
	
	private final String delimeter  = "verystrongjoe";
	private Integer Integer;
	
	public boolean checkDataTypeWithVariance(String sourceColumntype, String targetColumnType, Collection<AcceptableColumnTypeVarianceMapping> mappingList) {
		for( AcceptableColumnTypeVarianceMapping mapping : mappingList ) {
			if( mapping.sourceColumnType.toString().equals(sourceColumntype ) ) {
				if( mapping.targetColumnType.toString().equals(targetColumnType ) ) {
					return true;
				}
			}
//			System.out.println( mapping.sourceColumnType + " <------->" + mapping.targetColumnType);
		}
		return false;
	}

	public List<HashMap> normalizeSchemaInformation(DBVendor dbVendorName, List<HashMap> schemaInfolist) throws RedCAException {
		
		switch(dbVendorName) {
		case oracle :
			throw new RedCAException("Not support oracle database yet..");

		case tibero :
//			((String)map.get(Schema.tableName)).concat( delimeter )
//			.concat( (String)map.get(Schema.tableName) )
//			.concat(  (String)map.get(Schema.columnType) )
//			.concat( (String) map.get(Schema.))
			
			// nothing to do.. pre-processing is exctued in the query.
			
			break;
			
		case derby :
			
			for(HashMap<String, Object> map : schemaInfolist) {
				
				
				TypeDescriptorImpl impl = (TypeDescriptorImpl)map.get("COLUMN_DATA");
//				String columnData = (String)map.get("COLUMN_DATA");
//				if(columnData.contains("NOT NULL")) {
//					map.put(Schema.nullable, "Y");
//				} else {
//					map.put(Schema.nullable, "N");
//				}
				if(impl.isNullable()) {
					map.put( splitCamelCase ( Schema.nullable.toString() ), "Y");
				} else {
					map.put( splitCamelCase ( Schema.nullable.toString() ), "N");
				}
				

//				Integer  size;
//				if( (size = extractLengthNumberInColumnInfo(columnData) )	!= null) {
//					map.put(Schema.dataLength, 0);
//				} else {
//					map.put(Schema.dataLength, size);
//				}
				Integer  size = impl.getMaximumWidth();
				map.put(  splitCamelCase (Schema.dataLength.toString() )   , size);
				
//				String columnType = extractTypeFromDerbyDataInfo(columnData);
				String columnType = impl.getTypeName();
				
				map.put( splitCamelCase ( Schema.dataType.toString() ), columnType);
			}
			
			break;
		}
		
		return schemaInfolist;
	}
	
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
	
	
	public static String extractTypeFromDerbyDataInfo(String v) {
	      String pattern = "^[\\S]+", rs;
	      Pattern r = Pattern.compile(pattern);

	      // Now create matcher object.
	      Matcher m = r.matcher(v.replaceAll("\\([0-9]+\\)", ""));
	      if (m.find( )) {
	         rs = m.group(0);
	      } else {
	         rs = "regular expression error";
	      }
	      return rs;
	}
	
	public Integer extractLengthNumberInColumnInfo(String v) {
		Integer r = null;
//		Pattern p = Pattern.compile("^[a-zA-Z||_]+([0-9]+).*");
//		Pattern p = Pattern.compile("\\d+");
		Pattern p = Pattern.compile("\\(\\d+\\)");
		Matcher m = p.matcher(v);
		if (m.find()) {
		   r = Integer.parseInt(m.group(0).replaceAll("\\(", "").replaceAll("\\)",""));
		}
		return r;
	}
	
	
	public Collection<AcceptableColumnTypeVarianceMapping> getMappingColumnList() {
		return mappingColumnList;
	}

	public void setMappingColumnList(
			Collection<AcceptableColumnTypeVarianceMapping> mappingColumnList) {
		this.mappingColumnList = mappingColumnList;
	}

	public static void main(String[] args) {
		
		String format = "%s %s";
		String a = "joe";
		String b = "bought a house";
		
		System.out.println(String.format(format, a,b));
		
		
//		Integer test = StringUtil.extractNumberInString("VARCHAR2(20) NOT NULL");
//		System.out.println(test);
		
//		Pattern p = Pattern.compile("\\(\\d+\\)");
//		Matcher m = p.matcher("VARCHAR2(20) NOT NULL");
//		Integer r = null;
//		if (m.find()) {
//			String numberString = m.group(0).replaceAll("\\(", "").replaceAll("\\)","");
//		   r = r.parseInt(  numberString  );
//		}
//		System.out.println(r);
		
		
	}
	
}
