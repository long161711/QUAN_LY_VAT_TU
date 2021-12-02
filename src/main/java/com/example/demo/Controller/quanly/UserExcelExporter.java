package com.example.demo.Controller.quanly;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.example.demo.entities.quanly.VatTuEntity;

public class UserExcelExporter {
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;

	private List<VatTuEntity> listvattu;
	private String date;
	public UserExcelExporter(List<VatTuEntity> listvattu, String date) {
		super();
		this.listvattu = listvattu;
		this.date=date;
		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet("Vật Tư");
	}
	private void writeHeaderRow() {
		Row row = sheet.createRow(0);
		
		CellStyle style =workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);
		
		Cell cell = row.createCell(0);
		cell.setCellValue("ID Vật Tư");
		cell.setCellStyle(style);

		cell = row.createCell(1);
		cell.setCellValue("Mã Vật Tư");
		cell.setCellStyle(style);
		
		cell = row.createCell(2);
		cell.setCellValue("Tên Vật Tư");
		cell.setCellStyle(style);
		
		cell = row.createCell(3);
		cell.setCellValue("Thông Số Kĩ Thuật");
		cell.setCellStyle(style);
		
		cell = row.createCell(4);
		cell.setCellValue("Năm Sử Dụng");
		cell.setCellStyle(style);
		
		cell = row.createCell(5);
		cell.setCellValue("Số Lượng");
		cell.setCellStyle(style);
		
		cell = row.createCell(6);
		cell.setCellValue("Tỉ lệ %CL");
		cell.setCellStyle(style);
		
		cell = row.createCell(7);
		cell.setCellValue("Tổng Tiền");
		cell.setCellStyle(style);
		
		cell = row.createCell(8);
		cell.setCellValue("Số Dư");
		cell.setCellStyle(style);
		
		
	}
	private void writeDataRows() {
		int rowCount =1;
		CellStyle style =workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(14);
		style.setFont(font);
		 for (VatTuEntity vattu :listvattu) {
			 Row row = sheet.createRow(rowCount++);
			 
			 Cell cell =row.createCell(0);
			 cell.setCellValue(vattu.getId());
			 sheet.autoSizeColumn(0);
			 cell.setCellStyle(style);
			 
			 cell =row.createCell(1);
			 cell.setCellValue(vattu.getMaVatTu());
			 sheet.autoSizeColumn(1);
			 cell.setCellStyle(style);
			 
			 cell =row.createCell(2);
			 cell.setCellValue(vattu.getTenVatTu());
			 sheet.autoSizeColumn(2);
			 cell.setCellStyle(style);
			 
			 cell =row.createCell(3);
			 cell.setCellValue(vattu.getThongSoKiThuat());
			 sheet.autoSizeColumn(3);
			 cell.setCellStyle(style);
			 
			 cell =row.createCell(4);
			 cell.setCellValue(vattu.getNamSuDung());
			 sheet.autoSizeColumn(4);
			 cell.setCellStyle(style);
			 
			 cell =row.createCell(5);
			 cell.setCellValue(vattu.getSoLuong());
			 sheet.autoSizeColumn(5);
			 cell.setCellStyle(style);
			 
			 cell =row.createCell(6);
			 cell.setCellValue(vattu.getTileCl());
			 sheet.autoSizeColumn(6);
			 cell.setCellStyle(style);
			 
			 cell =row.createCell(7);
			 cell.setCellValue(vattu.getTongTien());
			 sheet.autoSizeColumn(7);
			 cell.setCellStyle(style);
			 
			 cell =row.createCell(8);
			 cell.setCellValue(vattu.getSoDu());
			 sheet.autoSizeColumn(8);
			 cell.setCellStyle(style);
			 
		 }
//		 Row row = sheet.createRow(rowCount+2);
//		 
//		 Cell cell =row.createCell(7);
//		 cell.setCellValue(date);
//		 sheet.autoSizeColumn(7);
//		 cell.setCellStyle(style);
	}
	public void export(HttpServletResponse response) throws IOException {
		writeHeaderRow();
		writeDataRows();
		
		ServletOutputStream outputStream=  response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();
	}
}
