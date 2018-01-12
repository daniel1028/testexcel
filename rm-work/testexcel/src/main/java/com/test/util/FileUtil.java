package com.test.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.test.mapping.ExcelToPojoSerializer;
import com.test.util.ExcelConfigs.ExcelSettingsBuilder;

public final class FileUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);

	private FileUtil() {
	}

	public static boolean writeFile(String fileName, String data) {
		File file = new File(fileName);

		if (file.exists()) {
			if (deleteFile(file)) {
				return createFile(file, data);
			} else {
				return deleteFile(file);
			}
		} else {
			return createFile(file, data);
		}
	}

	private static boolean deleteFile(File fileName) {
		fileName.delete();
		if (fileName.exists()) {
			LOGGER.error("Error file can not be deleted");
			return false;
		} else {
			return true;
		}

	}

	private static boolean createFile(File fileName, String data) {
		try {
			if (fileName.createNewFile()) {
				FileWriter file = new FileWriter(fileName);
				file.write(data);
				file.close();
				LOGGER.info("File is created!");
				return true;
			} else {
				LOGGER.info("File is created!");
				return false;
			}
		} catch (IOException e) {
			LOGGER.error("", e);
			return false;
		}
	}

	public static String getExtension(String fileName) {
		int i = fileName.lastIndexOf('.');
		if (i >= 0) {
			return fileName.substring(i);
		}
		return "";
	}
	
	/**
     * converts excel rows into a list of java objects
     *
     * @param file
     *         excel file ending with .xls or .xlsx.
     * @param type
     *         type of the root object.
     * @param <T>
     *         type of the root object.
     * @return
     *         the newly created a list of java objects
     *
     */
    public static synchronized <T> List<T>  fromExcel(final File file, final Class<T> type) {
        final ExcelToPojoSerializer unmarshaller = deserializer(file, ExcelSettingsBuilder.settings().build());
        return unmarshaller.serializeToPojo(type);
    }

    /**
     * converts excel rows into a list of java objects
     *
     * @param file
     *          excel file ending with .xls or .xlsx.
     * @param type
     *          type of root object.
     * @param <T>
     *          type of root object.
     * @param excelConfigs
     *          specifies to change the default behavior of the excel2pojoconverter.
     * @return
     *         the newly created a list of java objects
     *
     */
    public static synchronized <T> List<T> fromExcel(final File file, final Class<T> type, final ExcelConfigs excelConfigs) {
        final ExcelToPojoSerializer deserializer = deserializer(file, excelConfigs);
        return deserializer.serializeToPojo(type);
    }

	private static ExcelToPojoSerializer deserializer(final File file, final ExcelConfigs excelConfigs) {
		String extension = FileUtil.getExtension(file.getName());
		if (ExcelConstants.SUPPORTED_EXCEL_EXTENSION.matcher(extension).matches()) {
			ExcelWorkBook poiWorkbookHSSH = new ExcelWorkBook(file);
			return ExcelToPojoSerializer.instance(poiWorkbookHSSH, excelConfigs);
		} else {
			LOGGER.warn("File Extension does not match...It Should (.xls) or (.xlsx)");
			return null;
		}
	}
}
