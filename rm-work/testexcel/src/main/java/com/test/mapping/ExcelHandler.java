package com.test.mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler.SheetContentsHandler;
import org.apache.poi.xssf.usermodel.XSSFComment;

import com.test.util.Caster;
import com.test.util.ExcelCell;
import com.test.util.ExcelConfigs;

final class ExcelHandler<T> implements SheetContentsHandler {

	private T instance;
	private List<T> dataset;
	private int internalCount;

	private Class<T> type;
	private ExcelConfigs excelConfigs;

	private final Caster caster;

	ExcelHandler(Class<T> type, ExcelConfigs excelConfigs) {
		this.type = type;
		this.excelConfigs = excelConfigs;

		caster = Caster.getInstance();
	}

	List<T> getDataset() {
		return dataset;
	}

	private <T> T newInstanceOf(Class<T> type) {
		T newInstance = null;
		try {
			newInstance = type.getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return newInstance;
	}

	private void setFieldValue(String content, Class<? super T> subclass, int column) {
		if (subclass != Object.class) {
			setValue(content, subclass, column);

			setFieldValue(content, subclass.getSuperclass(), column);
		}
	}

	private void setValue(String content, Class<? super T> type, int column) {
		Stream.of(type.getDeclaredFields()).forEach(field -> {
			ExcelCell index = field.getAnnotation(ExcelCell.class);
			if (index != null && column == index.value()) {
				Class<?> fieldType = field.getType();
				Object o = caster.castValue(fieldType, content, excelConfigs);
				try {
					field.setAccessible(true);
					field.set(instance, o);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public void startRow(int rowNum) {
		if (rowNum == 1) {
			dataset = new ArrayList<>();
		}

		if (rowNum + 1 > excelConfigs.getNoOfRowsToSkip()) {
			instance = newInstanceOf(type);
		}
	}

	@Override
	public void endRow(int rowNum) {

		if (internalCount != rowNum)
			return;

		if (rowNum + 1 > excelConfigs.getNoOfRowsToSkip()) {
			dataset.add(instance);
		}
	}

	@Override
	public void cell(String cellReference, String formattedValue, XSSFComment comment) {

		CellAddress cellAddress = new CellAddress(cellReference);
		int row = cellAddress.getRow();

		if (row + 1 <= excelConfigs.getNoOfRowsToSkip()) {
			return;
		}

		internalCount = row;
		int column = cellAddress.getColumn();
		setFieldValue(formattedValue, type, column);
	}

	@Override
	public void headerFooter(String text, boolean isHeader, String tagName) {
		// Do nothing
	}
}
