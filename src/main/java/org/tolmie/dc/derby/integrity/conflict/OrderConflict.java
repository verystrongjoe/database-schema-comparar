package org.tolmie.dc.derby.integrity.conflict;

public class OrderConflict extends ConflictType{

	boolean isIgnored = false;
	
	String sourceColumnOrder = "";
	String targetColumnOrder = "";
	
	@Override
	public void setType() {
		this.type = "OrderConflict";
	}

	public OrderConflict(String tableName, String columnName,
			boolean isIgnored, String sourceColumnOrder,
			String targetColumnOrder) {
		super(tableName, columnName);
		this.isIgnored = isIgnored;
		this.sourceColumnOrder = sourceColumnOrder;
		this.targetColumnOrder = targetColumnOrder;
	}
	
	@Override
	public String toString() {
		return String.format(reportSentence, sourceColumnOrder, targetColumnOrder);
	}
	
}
