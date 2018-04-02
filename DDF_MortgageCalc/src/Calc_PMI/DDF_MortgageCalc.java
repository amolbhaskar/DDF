package Calc_PMI;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.concurrent.TimeUnit;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Extent.ExtentManager;
import ReadWriteExcel.ReadWriteExcel;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class DDF_MortgageCalc extends ReadWriteExcel {
	
	ExtentReports rep = ExtentManager.getInstance(); 
	ExtentTest test;
	
	public static WebDriver driver;
	String vURL;
	String TDID, vhomevalue,vloanamt,vintrate,vloanterm,vproptax,vexpectedpmi,vactualpmi,result;
	String xlData[][];
	String xl_path, xl_pathresult;
	String sheetname;
	int xRows,xCols,xRowCount,i;
	
	@BeforeTest
	public void inti() throws Exception{
		
		vURL = "http://www.mortgagecalculator.org/";
		
		xl_path= "C:\\Java_Luna_Projects\\DDF_MortgageCalc\\data.xls";
		xl_pathresult="C:\\Java_Luna_Projects\\DDF_MortgageCalc\\calcwrite.xls";
		sheetname = "data";
		xlData = readXL(xl_path, sheetname);
		xRowCount = xlData.length;
	}
	
	@Test
	public void calc() throws InterruptedException{
		
		for (i=1; i<xRowCount; i++){
		
			 TDID = xlData[i][0];
			 vhomevalue = xlData[i][1];
			 vloanamt = xlData[i][2];
			 vintrate = xlData[i][3];
			 vloanterm = xlData[i][4];
			 vproptax = xlData[i][5];
			 vexpectedpmi = xlData[i][6];
		
		test = rep.startTest("calc");
		test.log(LogStatus.INFO, "started feeding data from excel file");
		
		driver = new FirefoxDriver();
		driver.navigate().to(vURL);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		driver.findElement(By.xpath("//input[@name='param[homevalue]']")).clear();
		//driver.findElement(By.xpath(prop.getProperty("homevalue_xpath"))).clear();
		
		Thread.sleep(10);
		driver.findElement(By.xpath("//input[@name='param[homevalue]']")).sendKeys(vhomevalue);
		
		
		driver.findElement(By.xpath("//*[@id='loanamt']")).clear();
		driver.findElement(By.xpath("//*[@id='loanamt']")).sendKeys(vloanamt);
		
		driver.findElement(By.xpath("//*[@id='intrstsrate']")).clear();
		driver.findElement(By.xpath("//*[@id='intrstsrate']")).sendKeys(vintrate);
		
		driver.findElement(By.xpath("//*[@id='loanterm']")).clear();
		driver.findElement(By.xpath("//*[@id='loanterm']")).sendKeys(vloanterm);
		
		driver.findElement(By.xpath("//*[@id='pptytax']")).clear();
		driver.findElement(By.xpath("//*[@id='pptytax']")).sendKeys(vproptax);
		
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		
		vactualpmi = driver.findElement(By.xpath("//*[@id='calc']/form/section/section[2]/div/div/div[1]/div/div/div[3]/div[2]/div[2]/div[1]/div[1]/h3")).getText();
		
		test.log(LogStatus.INFO, "calculated actual PMI");
		System.out.println("actual monthly value is: "+vactualpmi);
		
		
		xlData[i][7] = vactualpmi;
		
		if(vactualpmi.contentEquals(vexpectedpmi)){
			System.out.println("pass");
			xlData[i][8] = "pass";
		}else{
			System.out.println("fail");
			xlData[i][8] = "fail";
		}
	}
	}
	
	
	@AfterTest
	public void tearDown() throws Exception{
		writeXL(xl_pathresult,"write",xlData);
		System.out.println("close the browser");
		driver.quit();
		
		//important/mandatory to write
		rep.endTest(test);
		rep.flush();
		
	}
	


}
