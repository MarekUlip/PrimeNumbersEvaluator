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
	/**
	 * Determines whether provided string is a positive integer
	 * @param text string to evaluate
	 * @return true if number is positive integer otherwise false
	 */
	private static boolean isPositiveInteger(String text) {
		return text!=null?text.matches("\\d+"):false;
	}
	
	/**
	 * Determines whether number is prime
	 * @param num number to evaluate
	 * @return true if number is prime otherwise false
	 */
	private static boolean isPrime(Long num) {
		//Using long in case square root of num would be larger than int max size
		for(long i = 2;i<=(long)Math.sqrt(num);i++) {
			if(num%i==0)return false;
		}
		return true;
	}
	
	/**
	 * Loads all positive integers from specified column of specified .xlsx file.
	 * @param path path to a .xlsx file
	 * @param columnToLoad name of a column that contains numbers
	 * @return list of all positive integers
	 */
	private static List<Long> loadNumbers(String path,String columnToLoad){
		List<Long> numbers = new ArrayList<>();
		
		try(	FileInputStream file = new FileInputStream(new File(path));
				Workbook workbook = new XSSFWorkbook(file)) {
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
				if(isPositiveInteger(num)) {
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
