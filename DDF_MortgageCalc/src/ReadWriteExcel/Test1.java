package ReadWriteExcel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.testng.annotations.Test;

public class Test1 {
	
	@Test
	public static void write(String filepath, String shname, String[][] data) throws Exception{
		
		File o = new File(filepath);
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		
		int row = data.length;
		
		int col =data[0].length;
		
		for(int m=0; m<row; m++){
			
			HSSFRow rowout = sheet.createRow(m);
			
				for(int n=0; n<col; n++){
					
					HSSFCell cellout = rowout.createCell(n);
					
					cellout.setCellValue(data[m][n]);
					
					
				}
			
				FileOutputStream out = new FileOutputStream(o);
				
				wb.write(out);
				
				out.flush();
				out.close();
		}
		
		
		
		
		
		
		
	}
	
	public static String[][] readxl1(String filepath, String shname) throws Exception{
		
		String data[][] = null;
		
		File f= new File(filepath);
		FileInputStream fs = new FileInputStream(f);
		HSSFWorkbook hwb = new HSSFWorkbook(fs);
		
		HSSFSheet sheet = hwb.getSheet(shname);
		
		int r = sheet.getLastRowNum()+1;
		int c = sheet.getRow(r).getLastCellNum();
		
		data = new String[r][c];
		
		for(int i=0; i<r; i++){
			
			HSSFRow hrow = sheet.getRow(i);
			
				for(int j=0; j<c; j++){
					
					HSSFCell hcell = sheet.getRow(i).getCell(j);
					
					String value = "-";
					
					if(hcell != null){
						value = cellToString(hcell);
					}
					
					data[i][j]=value;
				}
				
		}
		
		
		return data;
	}

	private static String cellToString(HSSFCell hcell) {
		// TODO Auto-generated method stub
		return null;
	}

}
