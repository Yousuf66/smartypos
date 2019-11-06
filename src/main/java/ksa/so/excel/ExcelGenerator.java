package ksa.so.excel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import ksa.so.domain.MetaLanguage;
import ksa.so.domain.MetaStatus;
import ksa.so.domain.SalesReport;
import ksa.so.repositories.SalesReportRepository;

public class ExcelGenerator {

	public ByteArrayInputStream generateExcelReportA(MetaLanguage language, SalesReportRepository salesReportRepository,
			MetaStatus status) throws IOException {
		LocalDate today = LocalDate.now(ZoneId.of("Asia/Karachi"));
// List<SalesReport> data = salesReportRepository.findDailySalesReport(language,
// new Date(), status);

		List<Object[]> data = salesReportRepository.consolidatedReport();
		String[] columns = { "DATE", "Sale Amount", "Smarty Discount", "no_orders", "Purchase Amount", "Gross p&l",
				"Delivery Expense", "After Delivery p&l", "After Discount P&L" };

		return dailySalesReportOfItemsConsolidated(data, columns, "Daily Sales Report - " + today, today);

	}

	public ByteArrayInputStream generateExcelReportItemSales(MetaLanguage language, SalesReportRepository salesReportRepository,
			MetaStatus status) throws IOException {
		LocalDate today = LocalDate.now(ZoneId.of("Asia/Karachi"));
// List<SalesReport> data = salesReportRepository.findDailySalesReport(language,
// new Date(), status);

		List<Object[]> data = salesReportRepository.itemSalesReport();
		String[] columns = { "id", "order_number", "Date", "name", "Store ID", "Store Name",
				"Item_id", "Item", "sale price"," purchase price", "DIFF", "Quantity", "sales total", "purchase total" };

		return dailySalesReportOfItemsSales(data, columns, "Daily Sales Report - " + today, today);

	}
	
	public ByteArrayInputStream generateExcelReport(MetaLanguage language, SalesReportRepository salesReportRepository,
			MetaStatus status) throws IOException {
		LocalDate today = LocalDate.now(ZoneId.of("Asia/Karachi"));
// List<SalesReport> data = salesReportRepository.findDailySalesReport(language,
// new Date(), status);

		List<Object[]> data = salesReportRepository.orderDetailsReport();
		String[] columns = { "Date", "Store Name", "Order Number", "Customer ID", "Customer Name", "Item ID",
				"Item Name", "Cost", "Price", "Quantity", "Total Cost", "Revenue(Price * Quantity)",
				"Profit / Loss Per item (Price-Cost)", "Gross Profit/Loss (Revenue-Total Cost)", "Discount Eligible",
				"Discount Utilized", "Discount Remaining", "Recievable Amount", "Status", "Cancellation Reason" };

		return dailySalesReportOfItems(data, columns, "Daily Sales Report - " + today, today);

	}

	
	public static ByteArrayInputStream dailySalesReportOfItemsSales(List<Object[]> data, String[] COLUMNs, String sheetName,
			LocalDate today) throws IOException {
		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			CreationHelper createHelper = workbook.getCreationHelper();

			Sheet sheet = workbook.createSheet(sheetName);

			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setColor(IndexedColors.BLACK.getIndex());

			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFont(headerFont);

// Row for Header
			Row headerRow = sheet.createRow(0);

// Header
			for (int col = 0; col < COLUMNs.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(COLUMNs[col]);
				cell.setCellStyle(headerCellStyle);
			}

// CellStyle for Age
//	CellStyle dateCellStyle = workbook.createCellStyle();
//	dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#"));

			CellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("d/m/yy"));

			int rowIdx = 1;

			for (int i = 0; i < data.size(); i++) {
				Row row = sheet.createRow(rowIdx++);


				row.createCell(0).setCellValue(Long.valueOf(String.valueOf(data.get(i)[0])));// id

				row.createCell(1).setCellValue(Long.valueOf(String.valueOf(data.get(i)[1])));// order number

				row.createCell(2).setCellValue((Date) data.get(i)[2]); // Date
				row.createCell(3).setCellValue(String.valueOf(String.valueOf(data.get(i)[3])));// name

				row.createCell(4).setCellValue(Long.valueOf(String.valueOf(data.get(i)[4])));// store id
				
//				row.createCell(5).setCellValue(Double.valueOf(String.valueOf(data.get(i)[5])));// Gross P&L
				row.createCell(5).setCellValue(Long.valueOf(String.valueOf(data.get(i)[5])));// store name
//				row.createCell(6).setCellValue((Double) data.get(i)[6]); // Delivery Expense
				row.createCell(6).setCellValue(String.valueOf(String.valueOf(data.get(i)[6])));// store id
				row.createCell(7).setCellValue(Long.valueOf(String.valueOf(data.get(i)[7])));// item name

//				row.createCell(7).setCellValue(Double.valueOf(String.valueOf(data.get(i)[7])));// After Delivery P&L
				row.createCell(8).setCellValue(String.valueOf(String.valueOf(data.get(i)[8])));// sale price
//				row.createCell(9).setCellValue(Long.valueOf(String.valueOf(data.get(i)[9])));// Quantity
				row.createCell(9).setCellValue(Double.valueOf(String.valueOf(data.get(i)[9])));// purchase price
				row.createCell(10).setCellValue(Double.valueOf(String.valueOf(data.get(i)[10])));// DIFF
				row.createCell(11).setCellValue(Double.valueOf(String.valueOf(data.get(i)[11])));// quantity
				row.createCell(12).setCellValue(Long.valueOf(String.valueOf(data.get(i)[12])));// sale total
				row.createCell(13).setCellValue(Double.valueOf(String.valueOf(data.get(i)[13])));// purchase total
				row.createCell(14).setCellValue(Double.valueOf(String.valueOf(data.get(i)[14])));// purchase total


			}
			autoSizeColumns(workbook);
			workbook.write(out);

			return new ByteArrayInputStream(out.toByteArray());
		}
	}
	
	
	
	
	public static ByteArrayInputStream dailySalesReportOfItemsConsolidated(List<Object[]> data, String[] COLUMNs, String sheetName,
			LocalDate today) throws IOException {
		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			CreationHelper createHelper = workbook.getCreationHelper();

			Sheet sheet = workbook.createSheet(sheetName);

			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setColor(IndexedColors.BLACK.getIndex());

			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFont(headerFont);

// Row for Header
			Row headerRow = sheet.createRow(0);

// Header
			for (int col = 0; col < COLUMNs.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(COLUMNs[col]);
				cell.setCellStyle(headerCellStyle);
			}

// CellStyle for Age
//	CellStyle dateCellStyle = workbook.createCellStyle();
//	dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#"));

			CellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("d/m/yy"));

			int rowIdx = 1;

			for (int i = 0; i < data.size(); i++) {
				Row row = sheet.createRow(rowIdx++);

				row.createCell(0).setCellValue((Date) data.get(i)[0]); // Date
//				row.createCell(1).setCellValue((Double) data.get(i)[1]); 
				row.createCell(1).setCellValue(Double.valueOf(String.valueOf(data.get(i)[1])));// Sale Amount
//				row.createCell(2).setCellValue((Double) data.get(i)[2]); // Smarty Discount
				row.createCell(2).setCellValue(Double.valueOf(String.valueOf(data.get(i)[2])));// Smarty Discount
					
				row.createCell(3).setCellValue(Long.valueOf(String.valueOf(data.get(i)[3])));// no_orders
//				row.createCell(4).setCellValue((Double) data.get(i)[4]); // Purchase Amount
				row.createCell(4).setCellValue(Double.valueOf(String.valueOf(data.get(i)[4])));// Purchase Amount
				
				row.createCell(5).setCellValue(Double.valueOf(String.valueOf(data.get(i)[5])));// Gross P&L
//				row.createCell(6).setCellValue((Double) data.get(i)[6]); // Delivery Expense
				row.createCell(6).setCellValue(String.valueOf(String.valueOf(data.get(i)[6])));// Delivery Expense
				

				row.createCell(7).setCellValue(Double.valueOf(String.valueOf(data.get(i)[7])));// After Delivery P&L
				row.createCell(8).setCellValue(Double.valueOf(String.valueOf(data.get(i)[8])));// After Discount P&L
//				row.createCell(9).setCellValue(Long.valueOf(String.valueOf(data.get(i)[9])));// Quantity
//
//				row.createCell(10).setCellValue(Double.valueOf(String.valueOf(data.get(i)[10]))); // Total Cost
//				row.createCell(11).setCellValue(Double.valueOf(String.valueOf(data.get(i)[11]))); // Revenue
//				row.createCell(12).setCellValue(Double.valueOf(String.valueOf(data.get(i)[12]))); // Profit Loss/Item
//				row.createCell(13).setCellValue(Double.valueOf(String.valueOf(data.get(i)[13]))); // Gross Profit
//
//				row.createCell(14).setCellValue(Double.valueOf(String.valueOf(data.get(i)[14]))); // Discount E
//				row.createCell(15).setCellValue(Double.valueOf(String.valueOf(data.get(i)[15]))); // Discount U
//				row.createCell(16).setCellValue(Double.valueOf(String.valueOf(data.get(i)[16]))); // Discount R
//				row.createCell(17).setCellValue(Double.valueOf(String.valueOf(data.get(i)[17]))); // Recievable
//
//				row.createCell(18).setCellValue((String) data.get(i)[18]); // Status
//				row.createCell(19).setCellValue((String) data.get(i)[19]); // Cancellation Reason

			}
			autoSizeColumns(workbook);
			workbook.write(out);

			return new ByteArrayInputStream(out.toByteArray());
		}
	}

	
	public static ByteArrayInputStream dailySalesReportOfItems(List<Object[]> data, String[] COLUMNs, String sheetName,
			LocalDate today) throws IOException {
		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			CreationHelper createHelper = workbook.getCreationHelper();

			Sheet sheet = workbook.createSheet(sheetName);

			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setColor(IndexedColors.BLACK.getIndex());

			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFont(headerFont);

// Row for Header
			Row headerRow = sheet.createRow(0);

// Header
			for (int col = 0; col < COLUMNs.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(COLUMNs[col]);
				cell.setCellStyle(headerCellStyle);
			}

// CellStyle for Age
//	CellStyle dateCellStyle = workbook.createCellStyle();
//	dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#"));

			CellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("d/m/yy"));

			int rowIdx = 1;

			for (int i = 0; i < data.size(); i++) {
				Row row = sheet.createRow(rowIdx++);

				row.createCell(0).setCellValue((Date) data.get(i)[0]); // Date
				row.createCell(1).setCellValue((String) data.get(i)[1]); // Store Name
				row.createCell(2).setCellValue((String) data.get(i)[2]); // Order Number
				row.createCell(3).setCellValue(Long.valueOf(String.valueOf(data.get(i)[3])));// Customer ID
				row.createCell(4).setCellValue((String) data.get(i)[4]); // Customer Name
				row.createCell(5).setCellValue(Long.valueOf(String.valueOf(data.get(i)[5])));// Item ID
				row.createCell(6).setCellValue((String) data.get(i)[6]); // Item Name

				row.createCell(7).setCellValue(Double.valueOf(String.valueOf(data.get(i)[7])));// Cost
				row.createCell(8).setCellValue(Double.valueOf(String.valueOf(data.get(i)[8])));// Price
				row.createCell(9).setCellValue(Long.valueOf(String.valueOf(data.get(i)[9])));// Quantity

				row.createCell(10).setCellValue(Double.valueOf(String.valueOf(data.get(i)[10]))); // Total Cost
				row.createCell(11).setCellValue(Double.valueOf(String.valueOf(data.get(i)[11]))); // Revenue
				row.createCell(12).setCellValue(Double.valueOf(String.valueOf(data.get(i)[12]))); // Profit Loss/Item
				row.createCell(13).setCellValue(Double.valueOf(String.valueOf(data.get(i)[13]))); // Gross Profit

				row.createCell(14).setCellValue(Double.valueOf(String.valueOf(data.get(i)[14]))); // Discount E
				row.createCell(15).setCellValue(Double.valueOf(String.valueOf(data.get(i)[15]))); // Discount U
				row.createCell(16).setCellValue(Double.valueOf(String.valueOf(data.get(i)[16]))); // Discount R
				row.createCell(17).setCellValue(Double.valueOf(String.valueOf(data.get(i)[17]))); // Recievable

				row.createCell(18).setCellValue((String) data.get(i)[18]); // Status
				row.createCell(19).setCellValue((String) data.get(i)[19]); // Cancellation Reason

			}
			autoSizeColumns(workbook);
			workbook.write(out);

			return new ByteArrayInputStream(out.toByteArray());
		}
	}

	public static ByteArrayInputStream dailySalesReport(List<SalesReport> data, String[] COLUMNs, String sheetName,
			LocalDate today) throws IOException {
		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			CreationHelper createHelper = workbook.getCreationHelper();

			Sheet sheet = workbook.createSheet(sheetName);

			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setColor(IndexedColors.BLACK.getIndex());

			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFont(headerFont);

			// Row for Header
			Row headerRow = sheet.createRow(0);

			// Header
			for (int col = 0; col < COLUMNs.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(COLUMNs[col]);
				cell.setCellStyle(headerCellStyle);
			}

			// CellStyle for Age
//			CellStyle dateCellStyle = workbook.createCellStyle();
//			dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#"));

			CellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("d/m/yy"));

			double purchasePriceTotal = 0;
			double purchasePriceActualTotal = 0;
			double sellingPriceTotal = 0;
			double sellingPriceActualTotal = 0;
			double discountedAmountTotal = 0;
			double discountedAmountActualTotal = 0;
			double payableAmountTotal = 0;
			double netAmountTotal = 0;

			int rowIdx = 1;
			for (SalesReport dataRow : data) {
				Row row = sheet.createRow(rowIdx++);

				int quantity = dataRow.getQuantity();
				double purchasePriceActual = dataRow.getCost() * quantity;
				double sellingPriceActual = dataRow.getPrice() * quantity;
				double discountedAmountActual = dataRow.getDiscountAmount() * quantity;
				double payableAmount = sellingPriceActual - discountedAmountActual;
				double netAmount = payableAmount - purchasePriceActual;

				purchasePriceTotal += dataRow.getCost();
				purchasePriceActualTotal += purchasePriceActual;
				sellingPriceTotal += dataRow.getPrice();
				sellingPriceActualTotal += sellingPriceActual;
				discountedAmountTotal += dataRow.getDiscountAmount();
				discountedAmountActualTotal += discountedAmountActual;
				payableAmountTotal += payableAmount;
				netAmountTotal += netAmount;

				Cell dateCell = row.createCell(0);
				dateCell.setCellValue(new Date());
				dateCell.setCellStyle(cellStyle);

				row.createCell(1).setCellValue(dataRow.getBarcode());
				row.createCell(2).setCellValue(dataRow.getOrderId());
				row.createCell(3).setCellValue(dataRow.getItemName());
				row.createCell(4).setCellValue(dataRow.getBranchName());
				row.createCell(5).setCellValue(quantity);
				row.createCell(6).setCellValue(dataRow.getCost());
				row.createCell(7).setCellValue(purchasePriceActual);
				row.createCell(8).setCellValue(dataRow.getDiscountAmount());
				row.createCell(9).setCellValue(discountedAmountActual);
				row.createCell(10).setCellValue(dataRow.getPrice());
				row.createCell(11).setCellValue(sellingPriceActual);
				row.createCell(12).setCellValue(payableAmount);
				row.createCell(13).setCellValue(netAmount);
			}

			Row row = sheet.createRow(rowIdx);
			row.createCell(0).setCellValue("Total");
			setMerge(sheet, rowIdx, rowIdx, 0, 5, false);

			// cellStyle.setAlignment(CellStyle.ALIGN_RIGHT);

			row.createCell(6).setCellValue(purchasePriceTotal);
			row.createCell(7).setCellValue(purchasePriceActualTotal);
			row.createCell(8).setCellValue(discountedAmountTotal);
			row.createCell(9).setCellValue(discountedAmountActualTotal);
			row.createCell(10).setCellValue(sellingPriceTotal);
			row.createCell(11).setCellValue(sellingPriceActualTotal);
			row.createCell(12).setCellValue(payableAmountTotal);
			row.createCell(13).setCellValue(netAmountTotal);

			autoSizeColumns(workbook);
			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		}
	}

	public static void autoSizeColumns(Workbook workbook) {
		int numberOfSheets = workbook.getNumberOfSheets();
		for (int i = 0; i < numberOfSheets; i++) {
			Sheet sheet = workbook.getSheetAt(i);
			if (sheet.getPhysicalNumberOfRows() > 0) {
				Row row = sheet.getRow(sheet.getFirstRowNum());
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					int columnIndex = cell.getColumnIndex();
					sheet.autoSizeColumn(columnIndex);
				}
			}
		}
	}

	protected static void setMerge(Sheet sheet, int numRow, int untilRow, int numCol, int untilCol, boolean border) {
		CellRangeAddress cellMerge = new CellRangeAddress(numRow, untilRow, numCol, untilCol);
		sheet.addMergedRegion(cellMerge);
		if (border) {
			setBordersToMergedCells(sheet, cellMerge);
		}

	}

	protected static void setBordersToMergedCells(Sheet sheet, CellRangeAddress rangeAddress) {
		RegionUtil.setBorderTop(BorderStyle.MEDIUM, rangeAddress, sheet);
		RegionUtil.setBorderLeft(BorderStyle.MEDIUM, rangeAddress, sheet);
		RegionUtil.setBorderRight(BorderStyle.MEDIUM, rangeAddress, sheet);
		RegionUtil.setBorderBottom(BorderStyle.MEDIUM, rangeAddress, sheet);
	}

	public ByteArrayInputStream generateExcelReportOfRegisteredUsers(MetaLanguage language,
			SalesReportRepository salesReportRepository, MetaStatus status) throws IOException {
		LocalDate today = LocalDate.now(ZoneId.of("Asia/Karachi"));
		List<Object[]> data = salesReportRepository.GenerateRegisteredUserReport();
		String[] columns = { "Customer", "Contact", "Total Orders", "Actual Amount", "Discounted Amount",
				"Amount Payable" };
		return dailyregisteredReport(data, columns, "Daily Registered User Report - " + today, today);

	}

	public static ByteArrayInputStream dailyregisteredReport(List<Object[]> data, String[] COLUMNs, String sheetName,
			LocalDate today) throws IOException {
		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			CreationHelper createHelper = workbook.getCreationHelper();

			Sheet sheet = workbook.createSheet(sheetName);

			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setColor(IndexedColors.BLACK.getIndex());

			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFont(headerFont);

			// Row for Header
			Row headerRow = sheet.createRow(0);

			// Header
			for (int col = 0; col < COLUMNs.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(COLUMNs[col]);
				cell.setCellStyle(headerCellStyle);
			}

			// CellStyle for Age
//			CellStyle dateCellStyle = workbook.createCellStyle();
//			dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#"));

			CellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("d/m/yy"));

			int rowIdx = 1;

			for (int i = 0; i < data.size(); i++) {
				Row row = sheet.createRow(rowIdx++);

				row.createCell(0).setCellValue((String) data.get(i)[0]);
				row.createCell(1).setCellValue((String) data.get(i)[1]);
				row.createCell(2).setCellValue((Long) data.get(i)[2]);
				row.createCell(3).setCellValue((Double) data.get(i)[3]);
				row.createCell(4).setCellValue((Double) data.get(i)[4]);
				row.createCell(5).setCellValue((Double) data.get(i)[5]);

			}
			autoSizeColumns(workbook);
			workbook.write(out);

			return new ByteArrayInputStream(out.toByteArray());
		}
	}

	public ByteArrayInputStream generateExcelReportOfCustomerfeedback(MetaLanguage language,
			SalesReportRepository salesReportRepository) throws IOException {
		LocalDate today = LocalDate.now(ZoneId.of("Asia/Karachi"));
		List<Object[]> data = salesReportRepository.getCustomerFeedback();
		String[] columns = { "Order Number", "Customer Name", "Customer Feedback" };
		return customerFeedbackReport(data, columns, "Customer Feedback Report - " + today, today);

	}

	public static ByteArrayInputStream customerFeedbackReport(List<Object[]> data, String[] COLUMNs, String sheetName,
			LocalDate today) throws IOException {
		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			CreationHelper createHelper = workbook.getCreationHelper();

			Sheet sheet = workbook.createSheet(sheetName);

			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setColor(IndexedColors.BLACK.getIndex());

			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFont(headerFont);

			// Row for Header
			Row headerRow = sheet.createRow(0);

			// Header
			for (int col = 0; col < COLUMNs.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(COLUMNs[col]);
				cell.setCellStyle(headerCellStyle);
			}

			// CellStyle for Age
//			CellStyle dateCellStyle = workbook.createCellStyle();
//			dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#"));

			CellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("d/m/yy"));

			int rowIdx = 1;

			for (int i = 0; i < data.size(); i++) {
				Row row = sheet.createRow(rowIdx++);

				row.createCell(0).setCellValue((Long) data.get(i)[0]);
				row.createCell(1).setCellValue((String) data.get(i)[1]);
				row.createCell(2).setCellValue((String) data.get(i)[2]);

			}
			autoSizeColumns(workbook);
			workbook.write(out);

			return new ByteArrayInputStream(out.toByteArray());
		}
	}

	public ByteArrayInputStream generateDailyDiscountedAmountReport(MetaLanguage language,
			SalesReportRepository salesReportRepository) throws IOException {
		LocalDate today = LocalDate.now(ZoneId.of("Asia/Karachi"));
		List<Object[]> data = salesReportRepository.getDailyDiscountedAmount(language);
		String[] columns = { "Customer Name", "Coupon Title", "Max Amount", "Amount Used" };
		return dailyDiscountedAmountReport(data, columns, "Daily Discounted Amount Report of User - " + today, today);

	}

	public static ByteArrayInputStream dailyDiscountedAmountReport(List<Object[]> data, String[] COLUMNs,
			String sheetName, LocalDate today) throws IOException {
		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			CreationHelper createHelper = workbook.getCreationHelper();

			Sheet sheet = workbook.createSheet(sheetName);

			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setColor(IndexedColors.BLACK.getIndex());

			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFont(headerFont);

			// Row for Header
			Row headerRow = sheet.createRow(0);

			// Header
			for (int col = 0; col < COLUMNs.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(COLUMNs[col]);
				cell.setCellStyle(headerCellStyle);
			}

			// CellStyle for Age
//			CellStyle dateCellStyle = workbook.createCellStyle();
//			dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#"));

			CellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("d/m/yy"));

			int rowIdx = 1;

			for (int i = 0; i < data.size(); i++) {
				Row row = sheet.createRow(rowIdx++);

				row.createCell(0).setCellValue((String) data.get(i)[0]);
				row.createCell(1).setCellValue((String) data.get(i)[1]);
				row.createCell(2).setCellValue((Double) data.get(i)[2]);
				row.createCell(3).setCellValue((Double) data.get(i)[3]);
			}
			autoSizeColumns(workbook);
			workbook.write(out);

			return new ByteArrayInputStream(out.toByteArray());
		}
		
		
		
	}
	
	
//	hammad
	
	

	public ByteArrayInputStream generateReprtB(MetaLanguage language, SalesReportRepository salesReportRepository,
			MetaStatus status) throws IOException {
		LocalDate today = LocalDate.now(ZoneId.of("Asia/Karachi"));
// List<SalesReport> data = salesReportRepository.findDailySalesReport(language,
// new Date(), status);

		List<Object[]> data = salesReportRepository.reportB();
		String[] columns = { "Date", " Order No", "Customer", "Store", "Store TAT (Hours)", "Number of Items Order",
				   "Number of Items Delivered"," Sale Amount"," Smarty Discount", "Discount Percentage", "Delivery Charges", 
				   "Delivery Expense", "Total Amt Payable", "Puchase Amount", "Gross P&L (S-P)", "Net P&L Post Delivery", 
				   "Net P&L Post Discount","Rider", "Distance Expense" , "Order Confirm", "Order Process", "Order Dispatch",
				   "Order Delivered", "TAT Breach", "Used from Wallet", "Payable Amount", "Received Amount", "Pending Amount",
				   "Amount Payable By BM" };

		return dailyReportOfSheetB(data, columns, "Daily Sales Report - " + today, today);

	}
	public static ByteArrayInputStream dailyReportOfSheetB(List<Object[]> data, String[] COLUMNs, String sheetName,
			LocalDate today) throws IOException {
		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			CreationHelper createHelper = workbook.getCreationHelper();

			Sheet sheet = workbook.createSheet(sheetName);

			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setColor(IndexedColors.BLACK.getIndex());

			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFont(headerFont);

// Row for Header
			Row headerRow = sheet.createRow(0);

// Header
			for (int col = 0; col < COLUMNs.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(COLUMNs[col]);
				cell.setCellStyle(headerCellStyle);
			}

// CellStyle for Age
//	CellStyle dateCellStyle = workbook.createCellStyle();
//	dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#"));

			CellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("d/m/yy"));

			int rowIdx = 1;

			for (int i = 0; i < data.size(); i++) {
				Row row = sheet.createRow(rowIdx++);

				row.createCell(0).setCellValue((Date) data.get(i)[0]); // Date
				row.createCell(1).setCellValue((String) data.get(i)[1]); // Order Number
				row.createCell(2).setCellValue((String) data.get(i)[2]); // Customer
				row.createCell(3).setCellValue((String) data.get(i)[3]);//store
				row.createCell(4).setCellValue(Double.valueOf(String.valueOf(data.get(i)[4]))); // TAT hours
				row.createCell(5).setCellValue(Long.valueOf(String.valueOf(data.get(i)[5])));// No of Items order
				row.createCell(6).setCellValue(Long.valueOf(String.valueOf(data.get(i)[6])));// No of Items Delivered
				row.createCell(7).setCellValue(Double.valueOf(String.valueOf(data.get(i)[7]))); // Sale Amt
				row.createCell(8).setCellValue(Double.valueOf(String.valueOf(data.get(i)[8]))); // smarty Discount
				row.createCell(9).setCellValue(Double.valueOf(String.valueOf(data.get(i)[9]))); // Discount Percentage
				row.createCell(10).setCellValue((String) data.get(i)[10]); // Deliver charges
				row.createCell(11).setCellValue((String) data.get(i)[11]); // Delivery Expense
				row.createCell(12).setCellValue(Double.valueOf(String.valueOf(data.get(i)[12]))); // Total Amt Payable
				row.createCell(13).setCellValue(Double.valueOf(String.valueOf(data.get(i)[13]))); // Puchase Amount
				row.createCell(14).setCellValue(Double.valueOf(String.valueOf(data.get(i)[14]))); // Gross P&L (S-P)
				row.createCell(15).setCellValue(Double.valueOf(String.valueOf(data.get(i)[15]))); // Net P&L Post Delivery
				row.createCell(16).setCellValue(Double.valueOf(String.valueOf(data.get(i)[16]))); // Net P&L Post Discount
				row.createCell(17).setCellValue((String) data.get(i)[17]); // Rider
				row.createCell(18).setCellValue((String) data.get(i)[18]); // Distance Expense 
				row.createCell(19).setCellValue((String) data.get(i)[19]); // Order Confirm
				row.createCell(20).setCellValue((String) data.get(i)[20]); // Order Process
				row.createCell(21).setCellValue((String) data.get(i)[21]); // Order Dispatch
				row.createCell(22).setCellValue((String) data.get(i)[22]); //Delivery Delivered
				row.createCell(23).setCellValue((String) data.get(i)[23]); // TAT Breach
				row.createCell(24).setCellValue(Double.valueOf(String.valueOf(data.get(i)[24])));  
				row.createCell(25).setCellValue(Double.valueOf(String.valueOf(data.get(i)[25])));  
				row.createCell(26).setCellValue(Double.valueOf(String.valueOf(data.get(i)[26])));  
				row.createCell(27).setCellValue(Double.valueOf(String.valueOf(data.get(i)[27]))); 
				row.createCell(28).setCellValue(Double.valueOf(String.valueOf(data.get(i)[28])));  
				
				
						}
			autoSizeColumns(workbook);
			workbook.write(out);

			return new ByteArrayInputStream(out.toByteArray());
		}
	}

	
	

	public ByteArrayInputStream generateReprtE(MetaLanguage language, SalesReportRepository salesReportRepository,
			MetaStatus status) throws IOException {
		LocalDate today = LocalDate.now(ZoneId.of("Asia/Karachi"));
// List<SalesReport> data = salesReportRepository.findDailySalesReport(language,
// new Date(), status);

		List<Object[]> data = salesReportRepository.reportE();
		String[] columns = { "Date", "Merchant ID", "Merchant Name", "Order Id", "Customer", "Number of Items Order",
				"Number of Items Delivered", "Sale Amount", "Puchase Amount", "Paid", "Pending Amount"," Rolling Balance",
				"Times of Purchase", "Rider", "Delivery Charges", "Delivery Expense"," Gross P&L (S-P)",
				"Net P&L Post Delivery"," Net P&L Post Discount" };

		return dailyReportOfSheetE(data, columns, "ReportE - " + today, today);

	}
	
	
	public static ByteArrayInputStream dailyReportOfSheetE(List<Object[]> data, String[] COLUMNs, String sheetName,
			LocalDate today) throws IOException {
		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			CreationHelper createHelper = workbook.getCreationHelper();

			Sheet sheet = workbook.createSheet(sheetName);

			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setColor(IndexedColors.BLACK.getIndex());

			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFont(headerFont);

// Row for Header
			Row headerRow = sheet.createRow(0);

// Header
			for (int col = 0; col < COLUMNs.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(COLUMNs[col]);
				cell.setCellStyle(headerCellStyle);
			}

// CellStyle for Age
//	CellStyle dateCellStyle = workbook.createCellStyle();
//	dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#"));

			CellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("d/m/yy"));

			int rowIdx = 1;

			for (int i = 0; i < data.size(); i++) {
				Row row = sheet.createRow(rowIdx++);

				row.createCell(0).setCellValue((Date) data.get(i)[0]); // Date
				row.createCell(1).setCellValue(Long.valueOf(String.valueOf(data.get(i)[1])));//Merchant ID
				row.createCell(2).setCellValue((String) data.get(i)[2]); // Merchant Name
				row.createCell(3).setCellValue((String) data.get(i)[3]); // Merchant Name
				row.createCell(4).setCellValue((String) data.get(i)[4]);//Customer
				row.createCell(5).setCellValue(Long.valueOf(String.valueOf(data.get(i)[5])));//Number of Items Order
				row.createCell(6).setCellValue(Long.valueOf(String.valueOf(data.get(i)[6])));// Number of Items Delivered
				row.createCell(7).setCellValue(Double.valueOf(String.valueOf(data.get(i)[7]))); // Number of Items Order
				row.createCell(8).setCellValue(Double.valueOf(String.valueOf(data.get(i)[8]))); // Puchase Amount
				row.createCell(9).setCellValue((String) data.get(i)[9]); // Deliver charges
				row.createCell(10).setCellValue((String) data.get(i)[10]); // Delivery Expense
				row.createCell(11).setCellValue((String) data.get(i)[11]); // Deliver charges
				row.createCell(12).setCellValue((String) data.get(i)[12]); // Delivery Expense
				row.createCell(13).setCellValue((String) data.get(i)[13]); // Deliver charges
				row.createCell(14).setCellValue((String) data.get(i)[14]); // Delivery Expense
				row.createCell(15).setCellValue((String) data.get(i)[15]); // Deliver charges
				row.createCell(16).setCellValue(Double.valueOf(String.valueOf(data.get(i)[16]))); // Total Amt Payable
				row.createCell(17).setCellValue(Double.valueOf(String.valueOf(data.get(i)[17]))); // Puchase Amount
				row.createCell(18).setCellValue(Double.valueOf(String.valueOf(data.get(i)[18]))); // Puchase Amount
								
						}
			autoSizeColumns(workbook);
			workbook.write(out);

			return new ByteArrayInputStream(out.toByteArray());
		}
	}



	
	
	
	

}