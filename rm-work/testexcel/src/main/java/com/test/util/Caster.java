package com.test.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Caster {
	private static final Logger LOGGER = LoggerFactory.getLogger(Caster.class);

	private static final Caster instance = new Caster();

	public static Caster getInstance() {
		return instance;
	}

	private Caster() {
	}

	private Date dateParser(String value, ExcelConfigs options) {
		LocalDateTime formatDateTime = LocalDateTime.parse(value, DateTimeFormatter.ofPattern("MM/DD/yy"));
		Date date = Date.from(formatDateTime.toLocalDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
		return date;
	}

	private Date dateValue(String value, ExcelConfigs options) {
		try {
			final SimpleDateFormat sdf = new SimpleDateFormat(ExcelConstants.DATE_PATTERN);
			return sdf.parse(value);
		} catch (ParseException e) {
			if (Boolean.TRUE.equals(options.getIsNullAsDefault())) {
				return null;
			} else {
				Calendar calendar = Calendar.getInstance();
				return calendar.getTime();
			}
		}
	}

	public Object castValue(Class<?> fieldType, String value, ExcelConfigs options) {
		Object o;
		LOGGER.debug("Type : {} ", fieldType.getName());
		switch (fieldType.getName()) {
		case ExcelConstants.STRING:
			o = value.toString();
			break;
		case ExcelConstants.INTEGER:
		case ExcelConstants._INTEGER:
			o = Objects.equals(value, "") ? 0 : new Integer(value);
			break;
		case ExcelConstants.DOUBLE:
		case ExcelConstants._DOUBLE:
			o = Objects.equals(value, "") ? 0d : new Double(value);
			break;
		case ExcelConstants.LONG:
		case ExcelConstants._LONG:
			o = Objects.equals(value, "") ? 0L : new Long(value);
			break;
		case ExcelConstants.FLOAT:
		case ExcelConstants._FLOAT:
			o = Objects.equals(value, "") ? 0f : new Float(value);
			break;
		case ExcelConstants.BOOLEAN:
		case ExcelConstants._BOOLEAN:
			o = Objects.equals(value, "") ? 0f : new Boolean(value);
			break;
		case ExcelConstants.DATE:
			o = dateValue(value, options);
			break;
		case ExcelConstants.LOCAL_DATE:
			o = dateParser(value, options);
			break;
		default:
			o = value.toString();
			break;
		}
		return o;
	}

	public Object castCellValue(XSSFCell cell, String fieldType) {
		LOGGER.debug("Type : {} ", fieldType);
		Object o = null;
		switch (fieldType) {
		case ExcelConstants.BOOLEAN:
		case ExcelConstants._BOOLEAN:
			o = cell.getBooleanCellValue();
			break;
		case ExcelConstants.STRING:
			o = cell.getStringCellValue();
			break;
		case ExcelConstants.NUMBER:
		case ExcelConstants.INTEGER:
		case ExcelConstants._INTEGER:
		case ExcelConstants.DOUBLE:
		case ExcelConstants._DOUBLE:
		case ExcelConstants.FLOAT:
		case ExcelConstants._FLOAT:
		case ExcelConstants.LONG:
		case ExcelConstants._LONG:
			o = cell.getNumericCellValue();
			break;
		case ExcelConstants.DATE:
			o = cell.getDateCellValue();
			break;
		default:
			break;
		}
		return o;
	}
}
