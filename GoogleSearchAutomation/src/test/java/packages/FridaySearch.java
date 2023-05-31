package packages;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class FridaySearch {

	public static void main(String[] args) throws IOException, InterruptedException {
		
		
		File src = new File("C:\\Users\\ShawoN\\Downloads\\Data2.xlsx");
		FileInputStream file = new FileInputStream(src);
		
		
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		
		//This is for .xlsx file
		
		//Select the Saturday Sheet
		XSSFSheet sheet = workbook.getSheet("Friday");
		
		 int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();

         // Iterate over each row
         for (int i = 2; i <= rowCount; i++) { // Start from 1 to skip header row
             Row row = sheet.getRow(i);

             // Read data from desired columns (assuming column indexes are 0 and 1)
             Cell cell1 = row.getCell(2);
             row.getCell(3);
             
             String searchData1 = cell1.getStringCellValue();
             WebDriverManager.chromedriver();
     		WebDriverManager.firefoxdriver();
     		WebDriverManager.edgedriver();
     		
          // Perform Google search
             WebDriver driver = new ChromeDriver();
     		driver.manage().window().maximize();
             driver.get("https://www.google.com/");
             
             WebElement searchBox = driver.findElement(By.name("q"));
             
             // Enter search data and submit
             searchBox.sendKeys(searchData1);
             Thread.sleep(2000);
             
             
            
       
             //Finding the Suggestion Elements
             List <WebElement> suggestionList = driver.findElements(By.xpath("//ul[@role='listbox']//li/descendant::div[@class='wM6W7d']"));
             
             for (WebElement suggestion : suggestionList) {
     			suggestion.getText();
             }
     		
     		
     		// Find the largest and smallest suggestion text
             List<String> suggestionTexts = new ArrayList<>();
             for (WebElement suggestion : suggestionList) {
                 String suggestionText = suggestion.getText();
                 suggestionTexts.add(suggestionText);
             }

             // Print largest and smallest suggestion text
             String largestSuggestion = Collections.max(suggestionTexts);
             String smallestSuggestion = Collections.min(suggestionTexts);

             System.out.println("Largest Suggestion: " + largestSuggestion);
             System.out.println("Smallest Suggestion: " + smallestSuggestion);
     		
             
             
             
             
             
          // Write largest and smallest suggestion to Excel file (assuming columns 4 and 5)
             Cell largestSuggestionCell = row.createCell(3);
             largestSuggestionCell.setCellValue(largestSuggestion);
             Cell smallestSuggestionCell = row.createCell(4);
             smallestSuggestionCell.setCellValue(smallestSuggestion);
             
             
          // Clear search box for the next iteration
             searchBox.clear();
             driver.close();
 		
 		}
         
         	
      // Save the changes to the Excel file
         FileOutputStream outputStream = new FileOutputStream(src);
         workbook.write(outputStream);
         outputStream.close();

         workbook.close();
         file.close();
	}

}
