package com.codeittoday.dev_checkpoint.service;

import java.io.ByteArrayOutputStream;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.codeittoday.dev_checkpoint.dto.EnvironmentDto;
import com.codeittoday.dev_checkpoint.dto.FormRequest;
import com.codeittoday.dev_checkpoint.dto.StepDto;
import com.codeittoday.dev_checkpoint.dto.SubStatus;

@Service
public class ExcelService {

    public byte[] generateExcel(FormRequest request) throws Exception {

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Report");

        int rowNum = 0;

        // ===== HEADER STYLE =====
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());

        headerStyle.setFont(headerFont);
        headerStyle.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        setBorder(headerStyle);

        // ===== NORMAL TEXT =====
        CellStyle textStyle = workbook.createCellStyle();
        textStyle.setAlignment(HorizontalAlignment.LEFT);
        textStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        setBorder(textStyle);

        // ===== CENTER TEXT (STATUS) =====
        CellStyle centerStyle = workbook.createCellStyle();
        centerStyle.setAlignment(HorizontalAlignment.CENTER);
        centerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        setBorder(centerStyle);

        // ===== BOLD STEP =====
        CellStyle boldStyle = workbook.createCellStyle();
        Font boldFont = workbook.createFont();
        boldFont.setBold(true);
        boldStyle.setFont(boldFont);
        boldStyle.setAlignment(HorizontalAlignment.LEFT);
        boldStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        setBorder(boldStyle);

        // ===== GREEN / RED =====
        CellStyle greenStyle = workbook.createCellStyle();
        greenStyle.cloneStyleFrom(centerStyle);
        greenStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        greenStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        CellStyle redStyle = workbook.createCellStyle();
        redStyle.cloneStyleFrom(centerStyle);
        redStyle.setFillForegroundColor(IndexedColors.ROSE.getIndex());
        redStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // ===== INDENT STYLE (Testing rows) =====
        CellStyle indentStyle = workbook.createCellStyle();
        indentStyle.cloneStyleFrom(textStyle);
        indentStyle.setIndention((short) 2);

        // ===== HEADER ROW =====
        Row header = sheet.createRow(rowNum++);
        header.setHeightInPoints(25);

        String[] headers = {"Environment", "Development Steps", "Status", "Remarks"};

        for (int i = 0; i < headers.length; i++) {
            Cell cell = header.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        // ===== DATA =====
        for (EnvironmentDto env : request.getEnvironments()) {

            int startRow = rowNum;

            for (StepDto step : env.getSteps()) {

                // NORMAL STEP
                if (step.getTestingStatus() == null) {

                    Row row = sheet.createRow(rowNum++);

                    createCell(row, 0, env.getEnvironmentName(), textStyle);

                    createCell(row, 1,
                            step.getStepNumber() + ". " + step.getName(),
                            boldStyle);

                    createStatusCell(row, 2, step.getStatus(), greenStyle, redStyle, centerStyle);

                    createCell(row, 3, step.getDescription(), textStyle);
                }

                // TESTING STATUS
                else {

                    // 🔥 MAIN Testing Status Row
                    Row mainRow = sheet.createRow(rowNum++);

                    createCell(mainRow, 0, env.getEnvironmentName(), textStyle);

                    createCell(mainRow, 1,
                            step.getStepNumber() + ". " + step.getName(),
                            boldStyle);

                    createCell(mainRow, 2, "", centerStyle);
                    createCell(mainRow, 3, "", textStyle);

                    // 🔽 Sub rows
                    rowNum = createTestingRow(sheet, rowNum, env, step,
                            "Success", step.getTestingStatus().getSuccess(),
                            greenStyle, redStyle, centerStyle, indentStyle);

                    rowNum = createTestingRow(sheet, rowNum, env, step,
                            "Fault", step.getTestingStatus().getFault(),
                            greenStyle, redStyle, centerStyle, indentStyle);

                    rowNum = createTestingRow(sheet, rowNum, env, step,
                            "Exception", step.getTestingStatus().getException(),
                            greenStyle, redStyle, centerStyle, indentStyle);
                }
            }

            int endRow = rowNum - 1;

            // ===== MERGE ENVIRONMENT COLUMN =====
            if (startRow <= endRow) {
                sheet.addMergedRegion(new CellRangeAddress(startRow, endRow, 0, 0));
            }
        }

        // ===== COLUMN WIDTH =====
        sheet.setColumnWidth(0, 7000);
        sheet.setColumnWidth(1, 12000);
        sheet.setColumnWidth(2, 4000);
        sheet.setColumnWidth(3, 12000);

        // ===== FREEZE HEADER =====
        sheet.createFreezePane(0, 1);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        return out.toByteArray();
    }

    // ===== HELPER METHODS =====

    private void setBorder(CellStyle style) {
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
    }

    private void createCell(Row row, int col, String value, CellStyle style) {
        Cell cell = row.createCell(col);
        cell.setCellValue(value != null ? value : "");
        cell.setCellStyle(style);
    }

    private void createStatusCell(Row row, int col, String status,
                                  CellStyle green, CellStyle red, CellStyle normal) {

        Cell cell = row.createCell(col);
        cell.setCellValue(status != null ? status : "");

        if ("YES".equalsIgnoreCase(status)) {
            cell.setCellStyle(green);
        } else if ("NO".equalsIgnoreCase(status)) {
            cell.setCellStyle(red);
        } else {
            cell.setCellStyle(normal);
        }
    }

    private int createTestingRow(Sheet sheet, int rowNum,
                                 EnvironmentDto env, StepDto step,
                                 String type, SubStatus sub,
                                 CellStyle green, CellStyle red,
                                 CellStyle center, CellStyle indentStyle) {

        Row row = sheet.createRow(rowNum++);

        createCell(row, 0, env.getEnvironmentName(), indentStyle);

        createCell(row, 1, "   ↳ " + type, indentStyle);

        if (sub != null) {
            createStatusCell(row, 2, sub.getStatus(), green, red, center);
            createCell(row, 3, sub.getDescription(), indentStyle);
        } else {
            createCell(row, 2, "", center);
            createCell(row, 3, "", indentStyle);
        }

        return rowNum;
    }
}