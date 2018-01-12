package com.test.mapping;

import java.util.List;

import com.test.util.ExcelConfigs;
import com.test.util.ExcelWorkBook;


public abstract class ExcelToPojoSerializer {

    public abstract <T> List<T> serializeToPojo(Class<T> type);

    public static ExcelToPojoSerializer instance(ExcelWorkBook workbook, ExcelConfigs excelConfigs) {
        return new HSSFPojoSerializer(workbook, excelConfigs);
    }
}
