package marek.ulip.prime_numbers_evaluator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class App 
{
	private static boolean isNumeric(String text) {
		return text!=null?text.matches("-?\\d+(\\.\\d+)?"):false;
	}
	
	private static boolean isPrime(Long num) {
		for(long i = 2;i<=(long)Math.sqrt(num);i++) {
			if(num%i==0)return false;
		}
		return true;
	}
	
	private static List<Long> loadNumbers(String path,String columnToLoad){
		List<Long> numbers = new ArrayList<>();
		try(FileInputStream file = new FileInputStream(new File(path));Workbook workbook = new XSSFWorkbook(file)) {
			Sheet sheet = workbook.getSheetAt(0);
			int dataCellIndex = -1;
			for(Cell cell : sheet.getRow(0)) {
				if(cell.getRichStringCellValue().toString().equals(columnToLoad)) {
					dataCellIndex = cell.getColumnIndex();
					break;
				}
			}
			
			for(Row row : sheet) {
				String num = row.getCell(dataCellIndex).getStringCellValue();
				if(isNumeric(num)) {
					numbers.add(Long.valueOf(num));
				}			
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		return numbers;
	}
	
    public static void main( String[] args )
    {
    	List<Long> numbers = loadNumbers(args[0],"Data");
    	numbers.removeIf(x->!isPrime(x));
    	numbers.forEach(x->System.out.println(x));
    }
}
