package org.tolmie.dc.derby.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import org.tolmie.dc.derby.loader.ExtendedJdbcTemplate;

@Repository
public class RedcaIntegrityInspectionDao {
	@Autowired
	private NamedParameterJdbcTemplate comparingJdbcTemplate;
	
	@Autowired  @Qualifier("targetJdbcTemplate") 
	protected ExtendedJdbcTemplate comparedJdbcTemplate;
	
	
	@Resource(name="query.integrity.check")
	private Map<String,String> queries; 
	
	public List checkSchema(String derbySchemaName) {
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("derbySchemaName", derbySchemaName);
	    return comparingJdbcTemplate.queryForList(queries.get("integrity.checkSchema"), paramMap);
	}
	
	public List selectIfTableColumnInformationFromDerby(String interfacetablePrefix) {
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("interfacetablePrefix", interfacetablePrefix);
	    return comparingJdbcTemplate.queryForList(queries.get("integrity.derby.selectIfTableColumnInformation"), paramMap);
	}
	
	public List selectIfTableColumnInformationFromTibero(String interfacetablePrefix, String outboundSchemaName) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("outboundSchemaName", outboundSchemaName);
		paramMap.put("interfacetablePrefix", interfacetablePrefix);
	    return comparedJdbcTemplate.query(queries.get("integrity.tibero.selectIfTableColumnInformation"), paramMap);
	}
}