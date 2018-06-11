package com.ruizton.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidOperationException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author zhanghuihua
 */
public class ExcelUtil {

	public static ArrayList<ArrayList<Object>> readRows(String excelFile,
			int startRowIndex, int rowCount) throws 
			IOException {
		System.out.println("excelFile: "+excelFile);
		return readRows(new FileInputStream(excelFile), startRowIndex, rowCount);
	}
	
	public static ArrayList<ArrayList<Object>> readRows(InputStream is,
			int startRowIndex, int rowCount) throws 
			IOException {

		Workbook wb = null;
//		try {
//			wb = new XSSFWorkbook(is);
//		} catch (InvalidOperationException ex) {
//			wb = new HSSFWorkbook(is);
//		}

		ByteArrayOutputStream byteOS = new ByteArrayOutputStream();
		byte[] buffer = new byte[512];
		int count = -1;
		while ((count = is.read(buffer)) != -1)
			byteOS.write(buffer, 0, count);
		byteOS.close();
		byte[] allBytes = byteOS.toByteArray();

		try {
			wb = new XSSFWorkbook(new ByteArrayInputStream(allBytes));
		} catch (InvalidOperationException ex) {
			wb = new HSSFWorkbook(new ByteArrayInputStream(allBytes));
		}

		Sheet sheet = wb.getSheetAt(0);

		return readRows(sheet, startRowIndex, rowCount);
	}

	// hhzhang042906
	public static ArrayList<ArrayList<Object>> readRows(Sheet sheet,
			int startRowIndex, int rowCount) {
		ArrayList<ArrayList<Object>> rowList = new ArrayList<ArrayList<Object>>();

		int i = 0;
		for (Row row : sheet) {
			i++;
			if (i > startRowIndex && i<=(startRowIndex+rowCount)){

				ArrayList<Object> cellList = new ArrayList<Object>();
				for (short j = 0; j < row.getLastCellNum(); j++) {
					try {
						Cell cell = row.getCell(j);
						cellList.add(readCell(cell));
					} catch (NullPointerException e) {
						cellList.add("");
					}
				}
	
				rowList.add(cellList);
			}
		}
		return rowList;
	}

	// hhzhang 从Excel读Cell
	public static Object readCell(Cell cell) {

		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			String str = cell.getRichStringCellValue().getString();
			return str == null ? "" : str.trim();

		case Cell.CELL_TYPE_NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				return cell.getDateCellValue();
			} else {
				return cell.getNumericCellValue();
			}

		case Cell.CELL_TYPE_BOOLEAN:
			return cell.getBooleanCellValue();

		case Cell.CELL_TYPE_FORMULA:
			if (DateUtil.isCellDateFormatted(cell)) {
				return cell.getDateCellValue();
			} else {
				return cell.getCellFormula();
			}

		case Cell.CELL_TYPE_BLANK:
			return "";

		default:
			System.out.println("Data error for cell of excel: "
					+ cell.getCellType());
			return "";
		}

	}

	// hhzhang 从Excel读日期型数据
	// private String readDate(Cell cell) throws Exception {
	// String riqi = null;
	// java.util.Date date = cell.getDateCellValue();
	// GregorianCalendar calendar = new GregorianCalendar();
	// calendar.setTime(date);
	// riqi = calendar.get(Calendar.YEAR) + "-";
	// riqi += (calendar.get(Calendar.MONTH) + 1) + "-";
	// riqi += calendar.get(Calendar.DATE);
	// return riqi;
	// }

	public static void main(String[] args) throws FileNotFoundException,
			IOException {
		System.out.println("CELL_TYPE_BLANK:" + Cell.CELL_TYPE_BLANK);
		System.out.println("CELL_TYPE_BOOLEAN:" + Cell.CELL_TYPE_BOOLEAN);
		System.out.println("CELL_TYPE_ERROR:" + Cell.CELL_TYPE_ERROR);
		System.out.println("CELL_TYPE_FORMULA:" + Cell.CELL_TYPE_FORMULA);
		System.out.println("CELL_TYPE_NUMERIC:" + Cell.CELL_TYPE_NUMERIC);
		System.out.println("CELL_TYPE_STRING:" + Cell.CELL_TYPE_STRING);
		ArrayList<ArrayList<Object>> title = readRows("e:/1.xlsx", 0, 1);
		for (ArrayList<Object> row : title) {
			for (Object cell : row) {
				System.out.println(cell);
			}
		}

		System.out.println("======================");
		ArrayList<ArrayList<Object>> rows = readRows("e:/1.xlsx", 1, 10);
		int i = 0;
		for (ArrayList<Object> row : rows) {
			System.out.println(i++);
			for (Object cell : row) {
				System.out.println(cell);
			}
		}
	}
}
