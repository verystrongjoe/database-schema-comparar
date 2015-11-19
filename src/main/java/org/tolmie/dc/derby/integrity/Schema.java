package org.tolmie.dc.derby.integrity;

// enum includes the property of static and final already.
public enum Schema {
	tableName(1), columnName(2), columnType(3), dataType(4), dataLength(5), columnOrder(6), nullable(7);
	private int value;
	
	private Schema(int value) {
		this.value = value;
	}
	@Override
	  public String toString() {
//	       switch (this) {
//	         case tableName:
//	              System.out.println("*tableName: " + value);
//	              break;
//	         case columnType:
//	              System.out.println("*columnType: " + value);
//	              break;
//	         case dataType:
//	              System.out.println("*dataType: " + value);
//	              break;
//	         case dataLength:
//	              System.out.println("*dataLength: " + value);
//	         case columnorder:
//	              System.out.println("*columnorder: " + value);		
//	         case nullable:
//	              System.out.println("*nullable: " + value);		 		              
//	        }
	  return super.toString();
	 }
	
};