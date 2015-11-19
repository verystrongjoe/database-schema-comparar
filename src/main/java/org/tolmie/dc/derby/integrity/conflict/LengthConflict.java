package org.tolmie.dc.derby.integrity.conflict;

public class LengthConflict extends ConflictType{
	
	String sourceDataLength;
	String targetDataLength;
	
	public LengthConflict(String tableName, String columnName,
			String sourceDataLength, String targetDataLength) {
		super(tableName, columnName);
		this.sourceDataLength = sourceDataLength;
		this.targetDataLength = targetDataLength;
	}

	@Override
	public void setType() {
		this.type = "LengthConflict";
	}
	
	@Override
	public String toString() {
		return String.format(reportSentence, sourceDataLength, targetDataLength);
	}
	
}
