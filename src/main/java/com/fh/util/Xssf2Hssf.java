package com.fh.util;

import java.util.HashMap;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Xssf2Hssf {

    private int lastColumn = 0;
    private HashMap<Integer, HSSFCellStyle> styleMap = new HashMap();

    public void transformXSSF(XSSFWorkbook workbookOld,
                              HSSFWorkbook workbookNew) {
        String call = "transform ";
        System.out.println(call + "Workbook");
        HSSFSheet sheetNew;
        XSSFSheet sheetOld;
        // TODO::workbookNew.setForceFormulaRecalculation(workbookOld.getForceFormulaRecalculation());
        // workbookNew.setHidden(workbookOld.isHidden()); //ST@03.05.2012 -
        // von Apache noch nicht implementiert
        workbookNew.setMissingCellPolicy(workbookOld.getMissingCellPolicy());

        for (int i = 0; i < workbookOld.getNumberOfSheets(); i++) {
            sheetOld = workbookOld.getSheetAt(i);
            sheetNew = workbookNew.getSheet(sheetOld.getSheetName());
            System.out.println(call + "Sheet Name: " + sheetOld.getSheetName());
            sheetNew = workbookNew.createSheet(sheetOld.getSheetName());
            this.transform(workbookOld, workbookNew, sheetOld, sheetNew);
        }
        System.out.println(call + "Styles size: " + this.styleMap.size());
        System.out.println(call + "abgeschlossen");
    }

    private void transform(XSSFWorkbook workbookOld, HSSFWorkbook workbookNew,
                           XSSFSheet sheetOld, HSSFSheet sheetNew) {
        System.out.println("transform Sheet");

        sheetNew.setDisplayFormulas(sheetOld.isDisplayFormulas());
        sheetNew.setDisplayGridlines(sheetOld.isDisplayGridlines());
        sheetNew.setDisplayGuts(sheetOld.getDisplayGuts());
        sheetNew.setDisplayRowColHeadings(sheetOld.isDisplayRowColHeadings());
        sheetNew.setDisplayZeros(sheetOld.isDisplayZeros());
        sheetNew.setFitToPage(sheetOld.getFitToPage());
        //
        // TODO::sheetNew.setForceFormulaRecalculation(sheetOld.getForceFormulaRecalculation());
        sheetNew.setHorizontallyCenter(sheetOld.getHorizontallyCenter());
        sheetNew.setMargin(Sheet.BottomMargin,
                sheetOld.getMargin(Sheet.BottomMargin));
        sheetNew.setMargin(Sheet.FooterMargin,
                sheetOld.getMargin(Sheet.FooterMargin));
        sheetNew.setMargin(Sheet.HeaderMargin,
                sheetOld.getMargin(Sheet.HeaderMargin));
        sheetNew.setMargin(Sheet.LeftMargin,
                sheetOld.getMargin(Sheet.LeftMargin));
        sheetNew.setMargin(Sheet.RightMargin,
                sheetOld.getMargin(Sheet.RightMargin));
        sheetNew.setMargin(Sheet.TopMargin, sheetOld.getMargin(Sheet.TopMargin));
        sheetNew.setPrintGridlines(sheetNew.isPrintGridlines());
        sheetNew.setRightToLeft(sheetNew.isRightToLeft());
        sheetNew.setRowSumsBelow(sheetNew.getRowSumsBelow());
        sheetNew.setRowSumsRight(sheetNew.getRowSumsRight());
        sheetNew.setVerticallyCenter(sheetOld.getVerticallyCenter());

        HSSFRow rowNew;
        for (Row row : sheetOld) {
            rowNew = sheetNew.createRow(row.getRowNum());
            if (rowNew != null)
                this.transform(workbookOld, workbookNew, (XSSFRow) row, rowNew);
        }

        for (int i = 0; i < this.lastColumn; i++) {
            sheetNew.setColumnWidth(i, sheetOld.getColumnWidth(i));
            sheetNew.setColumnHidden(i, sheetOld.isColumnHidden(i));
        }

        for (int i = 0; i < sheetOld.getNumMergedRegions(); i++) {
            CellRangeAddress merged = sheetOld.getMergedRegion(i);
            sheetNew.addMergedRegion(merged);
        }
    }

    private void transform(XSSFWorkbook workbookOld, HSSFWorkbook workbookNew,
                           XSSFRow rowOld, HSSFRow rowNew) {
        HSSFCell cellNew;
        rowNew.setHeight(rowOld.getHeight());
        // TODO::if (rowOld.getRowStyle() != null) {
        /*
         * Integer hash = rowOld.getRowStyle().hashCode(); if
         * (!this.styleMap.containsKey(hash))
         * this.transform(workbookOld,workbookNew,hash,
         * (XSSFCellStyle)rowOld.getRowStyle
         * (),(HSSFCellStyle)workbookNew.createCellStyle());
         * rowNew.setRowStyle(this.styleMap.get(hash)); }
         */
        for (Cell cell : rowOld) {
            cellNew = rowNew.createCell(cell.getColumnIndex(),
                    cell.getCellType());
            if (cellNew != null)
                this.transform(workbookOld, workbookNew, (XSSFCell) cell,
                        cellNew);
        }
        this.lastColumn = Math.max(this.lastColumn, rowOld.getLastCellNum());
    }

    private void transform(XSSFWorkbook workbookOld, HSSFWorkbook workbookNew,
                           XSSFCell cellOld, HSSFCell cellNew) {
        try{
        cellNew.setCellComment(cellOld.getCellComment());

        Integer hash = cellOld.getCellStyle().hashCode();
        if (this.styleMap != null && !this.styleMap.containsKey(hash)) {
            this.transform(workbookOld, workbookNew, hash,
                    cellOld.getCellStyle(),
                    (HSSFCellStyle) workbookNew.createCellStyle());
        }
        cellNew.setCellStyle(this.styleMap.get(hash));

        switch (cellOld.getCellType()) {
            case Cell.CELL_TYPE_BLANK:
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                cellNew.setCellValue(cellOld.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_ERROR:
                cellNew.setCellValue(cellOld.getErrorCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA:
                cellNew.setCellValue(cellOld.getCellFormula());
                break;
            case Cell.CELL_TYPE_NUMERIC:
                cellNew.setCellValue(cellOld.getNumericCellValue());
                break;
            case Cell.CELL_TYPE_STRING:
                cellNew.setCellValue(cellOld.getStringCellValue());
                break;
            default:
                System.out.println("transform: Unbekannter Zellentyp "
                        + cellOld.getCellType());
        }
    }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void transform(XSSFWorkbook workbookOld, HSSFWorkbook workbookNew,
                           Integer hash, XSSFCellStyle styleOld, HSSFCellStyle styleNew) {
        styleNew.setAlignment(styleOld.getAlignment());
        styleNew.setBorderBottom(styleOld.getBorderBottom());
        styleNew.setBorderLeft(styleOld.getBorderLeft());
        styleNew.setBorderRight(styleOld.getBorderRight());
        styleNew.setBorderTop(styleOld.getBorderTop());
        styleNew.setDataFormat(this.transform(workbookOld, workbookNew,
                styleOld.getDataFormat()));
        styleNew.setFillBackgroundColor(styleOld.getFillBackgroundColor());
        styleNew.setFillForegroundColor(styleOld.getFillForegroundColor());
        styleNew.setFillPattern(styleOld.getFillPattern());
        styleNew.setFont(this.transform(workbookNew,
                (XSSFFont) styleOld.getFont()));
        styleNew.setHidden(styleOld.getHidden());
        styleNew.setIndention(styleOld.getIndention());
        styleNew.setLocked(styleOld.getLocked());
        styleNew.setVerticalAlignment(styleOld.getVerticalAlignment());
        styleNew.setWrapText(styleOld.getWrapText());
        this.styleMap.put(hash, styleNew);
    }

    private short transform(XSSFWorkbook workbookOld, HSSFWorkbook workbookNew,
                            short index) {
        DataFormat formatOld = workbookOld.createDataFormat();
        DataFormat formatNew = workbookNew.createDataFormat();
        return formatNew.getFormat(formatOld.getFormat(index));
    }

    private HSSFFont transform(HSSFWorkbook workbookNew, XSSFFont fontOld) {
        HSSFFont fontNew = workbookNew.createFont();
        fontNew.setBoldweight(fontOld.getBoldweight());
        fontNew.setCharSet(fontOld.getCharSet());
        fontNew.setColor(fontOld.getColor());
        fontNew.setFontName(fontOld.getFontName());
        fontNew.setFontHeight(fontOld.getFontHeight());
        fontNew.setItalic(fontOld.getItalic());
        fontNew.setStrikeout(fontOld.getStrikeout());
        fontNew.setTypeOffset(fontOld.getTypeOffset());
        fontNew.setUnderline(fontOld.getUnderline());
        return fontNew;
    }
}

