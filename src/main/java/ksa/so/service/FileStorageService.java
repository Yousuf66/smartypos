package ksa.so.service;

import ksa.so.exception.FileStorageException;
import ksa.so.exception.MyFileNotFoundException;
import ksa.so.property.FileStorageProperties;

import static com.google.common.base.Predicates.in;
//import static org.simmetrics.builders.StringDistanceBuilder.with;
import static org.simmetrics.builders.StringMetricBuilder.with;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.simmetrics.StringDistance;
import org.simmetrics.StringMetric;
import org.simmetrics.metrics.Levenshtein;
import org.simmetrics.metrics.StringMetrics;
import org.simmetrics.simplifiers.Simplifiers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Iterator;

@Service
public class FileStorageService {

	/*
	 * private final Path fileStorageLocation;
	 * 
	 * @Autowired public FileStorageService(FileStorageProperties
	 * fileStorageProperties) { this.fileStorageLocation =
	 * Paths.get(fileStorageProperties.getUploadDir())
	 * .toAbsolutePath().normalize();
	 * 
	 * try { Files.createDirectories(this.fileStorageLocation); } catch (Exception
	 * ex) { throw new
	 * FileStorageException("Could not create the directory where the uploaded files will be stored."
	 * , ex); } }
	 */
	/*
	 * public String storeFile(MultipartFile file) { // Normalize file name String
	 * fileName = StringUtils.cleanPath(file.getOriginalFilename());
	 * 
	 * try { // Check if the file's name contains invalid characters
	 * if(fileName.contains("..")) { throw new
	 * FileStorageException("Sorry! Filename contains invalid path sequence " +
	 * fileName); }
	 * 
	 * // Copy file to the target location (Replacing existing file with the same
	 * name) Path targetLocation = this.fileStorageLocation.resolve(fileName);
	 * Files.copy(file.getInputStream(), targetLocation,
	 * StandardCopyOption.REPLACE_EXISTING);
	 * 
	 * return fileName; } catch (IOException ex) { throw new
	 * FileStorageException("Could not store file " + fileName +
	 * ". Please try again!", ex); } }
	 */
    public File convert(MultipartFile file)
    {    
        File convFile = new File(file.getOriginalFilename());
        try {
	        convFile.createNewFile(); 
	        FileOutputStream fos = new FileOutputStream(convFile); 
	        fos.write(file.getBytes());
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        return convFile;
    }
    
    public static void csvToXLSX() {
        try {
            String csvFileAddress = "test.csv"; //csv file address
            String xlsxFileAddress = "test.xlsx"; //xlsx file address
            XSSFWorkbook workBook = new XSSFWorkbook();
            XSSFSheet sheet = workBook.createSheet("sheet1");
            String currentLine=null;
            int RowNum=0;
            BufferedReader br = new BufferedReader(new FileReader(csvFileAddress));
            while ((currentLine = br.readLine()) != null) {
                String str[] = currentLine.split(",");
                RowNum++;
                XSSFRow currentRow=sheet.createRow(RowNum);
                for(int i=0;i<str.length;i++){
                    currentRow.createCell(i).setCellValue(str[i]);
                }
            }

            FileOutputStream fileOutputStream =  new FileOutputStream(xlsxFileAddress);
            workBook.write(fileOutputStream);
            fileOutputStream.close();
            System.out.println("Done");
        } catch (Exception ex) {
            System.out.println(ex.getMessage()+"Exception in try");
        }
    }

    public void xlsxToCSV(File inputFile, File outputFile, String uniqueID, String branch_id) {
        // For storing data into CSV files
        StringBuffer data = new StringBuffer();

        try {
            FileOutputStream fos = new FileOutputStream(outputFile);
            // Get the workbook object for XLSX file
            FileInputStream fis = new FileInputStream(inputFile);
            Workbook workbook = null;

            String ext = FilenameUtils.getExtension(inputFile.toString());

            if (ext.equalsIgnoreCase("xlsx")) {
                workbook = new XSSFWorkbook(fis);
            } else if (ext.equalsIgnoreCase("xls")) {
                workbook = new HSSFWorkbook(fis);
            }

            // Get first sheet from the workbook

            int numberOfSheets = workbook.getNumberOfSheets();
            Row row;
            Cell cell;
            // Iterate through each rows from first sheet

            for (int i = 0; i < numberOfSheets; i++) {
                Sheet sheet = workbook.getSheetAt(0);
                Iterator<Row> rowIterator = sheet.iterator();

                while (rowIterator.hasNext()) {
                    row = rowIterator.next();
                    // For each row, iterate through each columns
                    Iterator<Cell> cellIterator = row.cellIterator();
                    while (cellIterator.hasNext()) {

                        cell = cellIterator.next();

                        switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_BOOLEAN:
                            data.append(cell.getBooleanCellValue() + ",");

                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            data.append(cell.getNumericCellValue() + ",");

                            break;
                        case Cell.CELL_TYPE_STRING:
                            data.append(cell.getStringCellValue() + ",");
                            break;

                        case Cell.CELL_TYPE_BLANK:
                            data.append("" + ",");
                            break;
                        default:
                            data.append(cell + ",");

                        }
                    }
                    data.append(branch_id + ",");
                    data.append(uniqueID + ",");
                    data.append('\n'); // appending new line after each row
                }

            }
            fos.write(data.toString().getBytes());
            fos.close();

        } catch (Exception ioe) {
            ioe.printStackTrace();
        }
    }
    
	/*
	 * public Resource loadFileAsResource(String fileName) { try { Path filePath =
	 * this.fileStorageLocation.resolve(fileName).normalize(); Resource resource =
	 * new UrlResource(filePath.toUri()); if(resource.exists()) { return resource; }
	 * else { throw new MyFileNotFoundException("File not found " + fileName); } }
	 * catch (MalformedURLException ex) { throw new
	 * MyFileNotFoundException("File not found " + fileName, ex); } }
	 */
    public static float LevenshteinDistance(String str1, String str2) {
    	StringMetric metric = new Levenshtein();
		return metric.compare(str1, str2);
	}
    
    public static float LevenshteinDistanceWODiacritics(String str1, String str2) {

		String a = "Chilpéric II son of Childeric II";
		String b = "Chilperic II son of Childeric II";

		StringMetric metric = with(new Levenshtein())
				.simplify(Simplifiers.removeDiacritics())
				.build();

		return metric.compare(str1, str2);
	}
    
    public static float LevenshteinDistanceWODiacriticsCaseInsensitive(String str1, String str2) {
    	StringMetric metric = 
				with(new Levenshtein())
				.simplify(Simplifiers.removeDiacritics())
				.simplify(Simplifiers.toLowerCase())
				.build();

		return metric.compare(str1, str2);
	}
    
    public float LevenshteinDistanceCaseInsensitive(String str1, String str2) {
    	StringMetric metric = 
				with(new Levenshtein())
				.simplify(Simplifiers.toLowerCase())
				.build();

		return metric.compare(str1, str2);
	}
    
    public static float jaro(String str1, String str2) {
    	
    	String str11 = "This is a sentence. It is made of words";
		String str21 = "This sentence is similar. It has almost the same words";
		
		StringMetric metric = StringMetrics.jaro();
		return metric.compare(str1, str2); // 0.7383
	}
    
    public static float example02(String str1, String str2) {

		String str11 = "A quirky thing it is. This is a sentence.";
		String str21 = "This sentence is similar. A quirky thing it is.";

		StringMetric metric = StringMetrics.cosineSimilarity();

		return metric.compare(str1, str2); // 0.7777
	}
}
