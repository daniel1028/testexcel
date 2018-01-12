package testexcel.testexcel;

import java.io.File;
import java.util.List;

import com.test.beans.TestColumnBean;
import com.test.util.ExcelConfigs;
import com.test.util.ExcelConfigs.ExcelSettingsBuilder;
import com.test.util.FileUtil;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        File[] fList = new File("C:\\Daniel\\rm-work\\testexcel").listFiles();
        for(File file : fList) {
        	if (file.getName().toUpperCase().contains("CATALOG")) {
        		ExcelConfigs options = ExcelSettingsBuilder.settings(1).sheetName("TEST").build();
        		List<TestColumnBean> testColumnBean = FileUtil.fromExcel(file, TestColumnBean.class, options);
        		System.out.println(testColumnBean);
        	}
        }
    }
}
