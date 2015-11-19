package org.tolmie.dc.derby.loader;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

public class ExtendedJdbcTemplate extends NamedParameterJdbcDaoSupport{
	protected @Value("${dao.chunksize:500}") String chunkSize;

	public int update(String query){
		return update(query, EmptySqlParameterSource.INSTANCE);
	}
	public List query(String query){
		return getNamedParameterJdbcTemplate().queryForList(query, EmptySqlParameterSource.INSTANCE);
	}
	public List query(String query, Map<String, Object> param){
		return getNamedParameterJdbcTemplate().queryForList(query, param);
	}
	
	public <T> List<T> query(String sql, Map param, RowMapper<T> rowMapper){
		return getNamedParameterJdbcTemplate().query(sql, param, rowMapper);
	}
	
	public Map<String,Object> queryForMap(String query, Map<String, Object> param){
		return getNamedParameterJdbcTemplate().queryForMap(query, param);
	}
	public int delete(String query){
		return update(query);
	}
	public int delete(String queryString, SqlParameterSource map){
		return update(queryString, map);
	}
	public int  insert(String queryString, SqlParameterSource map){
		return update(queryString, map);
	}

	public int update(String queryString, SqlParameterSource map){
		 return getNamedParameterJdbcTemplate().update(queryString, map);
		
	}
	public int update(String queryString, Map map){
		return getNamedParameterJdbcTemplate().update(queryString, map);
		
	}
	public int[] batchUpdate(String query, List<SqlParameterSource> parameters){
		return getNamedParameterJdbcTemplate().batchUpdate(query, parameters.toArray(new SqlParameterSource[0]));
	}
	
	public int[] batchUpdate(String query, SqlParameterSource[] parameters){
		return getNamedParameterJdbcTemplate().batchUpdate(query, parameters);
	}
	
	public int[] batchUpdate(String query, Map<String, ?>[] batchValues){
//		SqlParameterSource[] parameters = SqlParameterSourceUtils.createBatch(objectArray);
		return getNamedParameterJdbcTemplate().batchUpdate(query, batchValues);
	}
	public <T> List<T> query(String sql, Map<String, ?> paramMap, Class<T> requiredType){
		return getNamedParameterJdbcTemplate().query(sql, paramMap, new BeanPropertyRowMapper(requiredType));
	}
}
