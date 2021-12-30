package petStore.utility;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class ExcelUtility {

    // Excel file
    private static XSSFWorkbook ExcelWBook;
    // Excel Sheet - which will be used to read the input from
    private static XSSFSheet ExcelWSheet;
    private static XSSFCell Cell;
    private static XSSFRow Row;

    public static Object[][] getTableArray(String filePath, String sheetName, int numberOfColumns){

        String[][] newTableArray = null;

        try {
            // Open excel file
            FileInputStream ExcelFile = new FileInputStream(filePath);

            // Initialize Workbook and Sheet
            ExcelWBook = new XSSFWorkbook(ExcelFile);
            ExcelWSheet = ExcelWBook.getSheet(sheetName);

            // Starting point
            int startRow = 1;
            int startCol = 1;

            int totalRows = ExcelWSheet.getLastRowNum() + 1;
            int totalCols = numberOfColumns;
            //System.out.println(totalRows);
            //System.out.println(totalCols);

            newTableArray = new String[totalRows-1][totalCols-1];

            for(int i=startRow; i<totalRows; i++){
                //System.out.println("Currently reading from: " + ExcelWSheet.getRow(i).getCell(0));
                for(int j=startCol;j<totalCols;j++){
                    //System.out.println(ExcelWSheet.getRow(i).getCell(j).toString());

                    newTableArray[i-1][j-1] = ExcelWSheet.getRow(i).getCell(j).toString();

                }
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return (newTableArray);
    }
}






















