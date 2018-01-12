package com.test.beans;

import java.util.Date;

import com.test.util.ExcelCell;

public class TestColumnBean {

	@ExcelCell(0)
	private String StringColumn;
	@ExcelCell(1)
	private Number IntColumn;
	@ExcelCell(2)
	private Number DoubleColumn;
	@ExcelCell(3)
	private Boolean BooleanColumn;
	@ExcelCell(4)
	private Date DateColumn;
	@ExcelCell(5)
	
	private Number FloatColuumn;
	public String getStringColumn() {
		return StringColumn;
	}
	public void setStringColumn(String stringColumn) {
		StringColumn = stringColumn;
	}
	public Number getIntColumn() {
		return IntColumn;
	}
	public void setIntColumn(Number intColumn) {
		IntColumn = intColumn;
	}
	public Number getDoubleColumn() {
		return DoubleColumn;
	}
	public void setDoubleColumn(Number doubleColumn) {
		DoubleColumn = doubleColumn;
	}
	public Boolean getBooleanColumn() {
		return BooleanColumn;
	}
	public void setBooleanColumn(Boolean booleanColumn) {
		BooleanColumn = booleanColumn;
	}
	public Date getDateColumn() {
		return DateColumn;
	}
	public void setDateColumn(Date dateColumn) {
		DateColumn = dateColumn;
	}
	public Number getFloatColuumn() {
		return FloatColuumn;
	}
	public void setFloatColuumn(Number floatColuumn) {
		FloatColuumn = floatColuumn;
	}
	@Override
	public String toString() {
		return "TestColumnBean [StringColumn=" + StringColumn + ", IntColumn=" + IntColumn + ", DoubleColumn="
				+ DoubleColumn + ", BooleanColumn=" + BooleanColumn + ", DateColumn=" + DateColumn + ", FloatColuumn="
				+ FloatColuumn + "]";
	}
	
}
