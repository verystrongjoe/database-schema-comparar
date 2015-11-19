package org.tolmie.dc.derby.integrity.conflict;

public abstract class ConflictType {
	
	protected String tableName;
	protected String columnName;
	
	protected String type;
	protected String reportSentence;
	
	abstract protected void setType();
	//abstract protected void makeReport(String srcInfo, String targetInfo);
	
	public ConflictType(String tableName, String columnName) {
		super();
		this.tableName = tableName;
		this.columnName = columnName;
		
		setType();
		
		reportSentence = 	
				type + 
				") " + 
				tableName +  
				"." + 
				columnName + 
				" : " +
				"%s" + 
				"(source), " + 
				"%s" + 
				"(target)";
	}
	
	
	@Override
	public String toString() {
		return reportSentence;
	}
	
}
