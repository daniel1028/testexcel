package com.test.mapping;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;

import com.test.util.Caster;
import com.test.util.ExcelCell;
import com.test.util.ExcelConfigs;
import com.test.util.ExcelWorkBook;

public final class HSSFPojoSerializer extends ExcelToPojoSerializer {
	private final ExcelConfigs excelConfigs;
	private final ExcelWorkBook excelWorkBook;
	private final Caster caster;


	HSSFPojoSerializer(final ExcelWorkBook excelWorkBook, ExcelConfigs options) {
		this.excelWorkBook = excelWorkBook;
		this.excelConfigs = options;
		caster = Caster.getInstance();
	}

	public <T> List<T> serializeToPojo(Class<T> type) {
		Workbook workbook = excelWorkBook.getWorkBook();
		Sheet sheet = workbook.getSheet(excelConfigs.getSheetName());
		List<T> list = new ArrayList<>();
		IntStream.range(excelConfigs.getNoOfRowsToSkip(), sheet.getLastRowNum() + 1).forEach(rowNum -> {
			T t = deserialize(sheet.getRow(rowNum), type);
			list.add(t);
		});
		return list;
	}

	private <T> T deserialize(Row currentRow, Class<T> type) {
		T instance = null;
		try {
			instance = type.getDeclaredConstructor().newInstance();
		} catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException
				| InstantiationException e) {
			e.printStackTrace();
		}

		return setFieldValue(currentRow, type, instance);
	}

	private <T> T tailSetFieldValue(Row currentRow, Class<? super T> type, T instance) {
		for (Field field : type.getDeclaredFields()) {
			ExcelCell index = field.getAnnotation(ExcelCell.class);
			if (index != null) {
				Object o = null;
				XSSFCell cc = (XSSFCell) currentRow.getCell(index.value());
				if (cc != null && (excelConfigs.getColorToSkipRows() == null
						|| (cc.getCellStyle().getFillForegroundXSSFColor() == null
								|| (cc.getCellStyle().getFillForegroundXSSFColor() != null
										&& (excelConfigs.getColorToSkipRows() != null
												&& !cc.getCellStyle().getFillForegroundXSSFColor().getARGBHex()
														.equalsIgnoreCase(excelConfigs.getColorToSkipRows())))))) {
					o = caster.castCellValue(cc, field.getType().getTypeName());
				}
				try {
					field.setAccessible(true);
					field.set(instance, o);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		return instance;
	}

	private <T> T setFieldValue(Row currentRow, Class<? super T> subclass, T instance) {
		return subclass == null ? instance
				: tailSetFieldValue(currentRow, subclass,
						setFieldValue(currentRow, subclass.getSuperclass(), instance));
	}
}
