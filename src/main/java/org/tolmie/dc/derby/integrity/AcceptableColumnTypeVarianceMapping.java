package org.tolmie.dc.derby.integrity;
public class AcceptableColumnTypeVarianceMapping {
		DBVendor sourceDB;
		DBVendor targetDB;
		COLUMN_TYPE sourceColumnType;
		COLUMN_TYPE targetColumnType;
		
		public AcceptableColumnTypeVarianceMapping(DBVendor sourceDB,
				DBVendor targetDB, COLUMN_TYPE sourceColumnType,
				COLUMN_TYPE targetColumnType) {
			super();
			this.sourceDB = sourceDB;
			this.targetDB = targetDB;
			this.sourceColumnType = sourceColumnType;
			this.targetColumnType = targetColumnType;
		}
		public DBVendor getSourceDB() {
			return sourceDB;
		}
		public void setSourceDB(DBVendor sourceDB) {
			this.sourceDB = sourceDB;
		}
		public DBVendor getTargetDB() {
			return targetDB;
		}
		public void setTargetDB(DBVendor targetDB) {
			this.targetDB = targetDB;
		}
		public COLUMN_TYPE getSourceColumnType() {
			return sourceColumnType;
		}
		public void setSourceColumnType(COLUMN_TYPE sourceColumnType) {
			this.sourceColumnType = sourceColumnType;
		}
		public COLUMN_TYPE getTargetColumnType() {
			return targetColumnType;
		}
		public void setTargetColumnType(COLUMN_TYPE targetColumnType) {
			this.targetColumnType = targetColumnType;
		}
		
	}