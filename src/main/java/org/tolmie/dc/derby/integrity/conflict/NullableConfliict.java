package org.tolmie.dc.derby.integrity.conflict;

public class NullableConfliict extends ConflictType{

	private String sourceNullable;
	private String targetNullable;
	
	@Override
	public void setType() {
		this.type = "NullableConfliict";
	}

	public NullableConfliict(String tableName, String columnName,
			String sourceNullable, String targetNullable) {
		super(tableName, columnName);
		this.sourceNullable = sourceNullable;
		this.targetNullable = targetNullable;
	}
	
	@Override
	public String toString() {
		return String.format(reportSentence, sourceNullable, targetNullable);
	}
	
}
