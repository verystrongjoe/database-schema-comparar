package org.tolmie.dc.derby.integrity.conflict;


public class DataTypeConflict extends ConflictType {
	
	String sourceColumnType;
	String targetColumnType;
	
	public DataTypeConflict(String tableName, String columnName,
			String sourceColumnType, String targetColumnType) {
		super(tableName, columnName);
		this.sourceColumnType = sourceColumnType;
		this.targetColumnType = targetColumnType;
	}

	@Override
	public void setType() {
		this.type = "DataTypeConflict";
	}

	@Override
	public String toString() {
		return String.format(reportSentence, sourceColumnType, targetColumnType);
	}
	
}
