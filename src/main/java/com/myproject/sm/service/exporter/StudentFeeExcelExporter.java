package com.myproject.sm.service.exporter;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.myproject.sm.domain.StudentFee;
import com.myproject.sm.domain.StudentFee.ClassFee;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

public class StudentFeeExcelExporter {

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<StudentFee> studentFees;
    private int month;

    public StudentFeeExcelExporter(List<StudentFee> studentFees, int month) {
        this.studentFees = studentFees;
        this.month = month;
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Tháng " + month);
    }

    private void writeHeaderLine() {
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "STT", style);
        createCell(row, 1, "Họ và Tên", style);
        createCell(row, 2, "Tháng", style);
        createCell(row, 3, "Số buổi học", style);
        createCell(row, 4, "Học phí (đồng)", style);

    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        cell.getRow().setHeightInPoints(cell.getSheet().getDefaultRowHeightInPoints() * 3);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowCount = 1;
        int stt = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        style.setWrapText(true);

        for (StudentFee studentFee : studentFees) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, stt++, style);
            createCell(row, columnCount++, studentFee.getStudent().getName(), style);
            createCell(row, columnCount++, month, style);
            if (studentFee.getFeeOfEachClass().size() == 1) {
                createCell(row, columnCount++, "" + studentFee.getTotalAttendedDay() + "/" + studentFee.getTotalDay(),
                        style);
                createCell(row, columnCount++, "" + String.format(Locale.US, "%1$,.2f", studentFee.getTotalFee()),
                        style);
            } else {
                String day = "";
                String fee = "";
                for (ClassFee c : studentFee.getFeeOfEachClass()) {
                    day += "" + c.getClassName() + ": " + c.getClassAttendedDay() + "/" + c.getClassTotalDay() + "\n";
                    fee += "" + c.getClassName() + ": " + String.format(Locale.US, "%1$,.2f", c.getFee()) + "\n";
                }
                createCell(row, columnCount++, day.trim(), style);
                createCell(row, columnCount++, fee.trim(), style);
            }
        }
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();
    }

}
