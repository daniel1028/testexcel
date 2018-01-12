package com.test.util;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public final class ExcelConstants {

	public static final String XLS_EXTENSION = ".xls";
	public static final String XLSX_EXTENSION = ".xlsx";
	public static final Pattern SUPPORTED_EXCEL_EXTENSION = Pattern.compile(".xlsx|.xls");
	public static final String ID_PATTERN = "^([A-Z]+)(\\d+)$";
	public static final DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_DATE;
	public static final String DATE_PATTERN = "yyyy-MM-dd'T'hh:mm:ss.SSS'z'";
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(ExcelConstants.DATE_PATTERN);
	public static final String BOOLEAN = "java.lang.Boolean";
	public static final String INTEGER = "java.lang.Integer";
	public static final String NUMBER = "java.lang.Number";
	public static final String DOUBLE = "java.lang.Double";
	public static final String FLOAT = "java.lang.Float";
	public static final String LONG = "java.lang.Long";
	public static final String DATE = "java.util.Date";
	public static final String STRING = "java.lang.String";
	public static final String _BOOLEAN = "boolean";
	public static final String _INTEGER = "int";
	public static final String _DOUBLE = "double";
	public static final String _FLOAT = "float";
	public static final String _LONG = "long";
	public static final String LOCAL_DATE = "java.time.LocalDate";
	private ExcelConstants() {
	}

}
