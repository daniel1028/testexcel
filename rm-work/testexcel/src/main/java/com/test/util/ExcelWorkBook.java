package com.test.util;

import java.io.File;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public final class ExcelWorkBook {

	private File file;

	public ExcelWorkBook(File file) {
		this.file = file;
	}

	public Workbook getWorkBook() {
		try {
			return WorkbookFactory.create(this.file);
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
