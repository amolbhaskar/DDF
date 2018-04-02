package Extent;

//http://relevantcodes.com/Tools/ExtentReports2/javadoc/index.html?com/relevantcodes/extentreports/ExtentReports.html


import java.io.File;
import java.util.Date;

import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;

public class ExtentManager {
	
	private static ExtentReports extent;
	//static String reportPath = "C:\\Java_Luna_Projects\\DDF_MortgageCalc\\"+fileName;

	public static ExtentReports getInstance() {
		if (extent == null) {
			
			Date d=new Date();
			String fileName=d.toString().replace(":", "_").replace(" ", "_")+".html";
			
			/*filePath - Path of the file, in .htm or .html format
			replaceExisting - Setting to overwrite (TRUE) the existing file or append (FALSE) to it
			true - the file will be replaced with brand new markup, and all existing data will be lost. Use this option to create a brand new report
			false - existing data will remain, new tests will be appended to the existing report. If the the supplied path does not exist, a new file will be created.
			displayOrder - Determines the order in which your tests will be displayed
			OLDEST_FIRST (default) - oldest test at the top, newest at the end
			NEWEST_FIRST - newest test at the top, oldest at the end*/
 
			extent = new ExtentReports("C:\\Java_Luna_Projects\\DDF_MortgageCalc\\"+fileName, true, DisplayOrder.NEWEST_FIRST);

			
			extent.loadConfig(new File(System.getProperty("user.dir")+"//ReportsConfig.xml"));
			// optional
			extent.addSystemInfo("Selenium Version", "2.53.0").addSystemInfo(
					"Environment", "QA");
		}
		return extent;
	}
}
