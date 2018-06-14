package net.itw.wcms.x27.utils;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import net.itw.wcms.toolkit.DateTimeUtils;

public class ExcelTool {
    
    private static final Logger log = Logger.getLogger(ExcelTool.class);
    
    public static Date getCellValueDate(HSSFRow row, int cellNum, String format) {
        Object obj = getCellValue(row, cellNum, Date.class);
        if (null == obj) {
            return null;
        }
        if (obj instanceof Date) {
            return (Date) obj; 
        } else {
            String dateStr = (String) obj;
            if (dateStr.length() == 8) {
                dateStr = dateStr.substring(0, 4) + "-" + dateStr.substring(4, 6) + "-" + dateStr.substring(6);
            }
            return DateTimeUtils.str2Date(dateStr, format);
        }
    }
    
    public static HSSFSheet createSheet(HSSFWorkbook workbook, int sheetNum, String sheetName) {
        HSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(sheetNum, sheetName);
        return sheet;
    }
    
    public static HSSFRow createRow(HSSFSheet sheet, int rowNum) {
        return sheet.createRow((short)rowNum);
    }
    
    public static HSSFCell createCellValueStr(HSSFRow row, int cellNum, String valueStr) {
        HSSFCell cell = row.createCell((short)cellNum);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue(new HSSFRichTextString(valueStr));
        return cell;
    }
    public static HSSFCell createCellValueStrStyle(HSSFRow row, int cellNum, String valueStr,HSSFCellStyle style,HSSFFont font) {
        HSSFCell cell = row.createCell((short)cellNum);
        style.setFont(font);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue(new HSSFRichTextString(valueStr));
        cell.setCellStyle(style);
        return cell;
    }
    public static String getCellValueStr(HSSFRow row, int cellNum, String defaultStr) {
        return StringUtils.defaultIfEmpty((String) ExcelTool.getCellValue(row, cellNum, String.class), defaultStr);
    }
    
    public static Object getCellValue(HSSFRow row, int cellNum, Class clazz) {
        if (null == row) {
            return null;
        }
        HSSFCell cell = row.getCell((short) cellNum);
        if (null == cell) {
            return null;
        }
        switch (cell.getCellType()) {
            case HSSFCell.CELL_TYPE_NUMERIC:
//                log.info("HSSFCell.CELL_TYPE_NUMERIC");///////
                double num = cell.getNumericCellValue();
                if (clazz == Date.class) {
                    return HSSFDateUtil.getJavaDate(num);
//                    如果时间格式为20080101
//                    Double dd = new Double(num);
//                    String dateStr = "" + dd.longValue();
//                    if (dateStr.length() == 8) {
//                        
//                    } else {
//                        return HSSFDateUtil.getJavaDate(num);
//                    }
                }
                BigDecimal decimal = new BigDecimal(num);
                if (clazz == BigDecimal.class) {
                    return decimal;
                } else {
                    return decimal.toString();
                }
            case HSSFCell.CELL_TYPE_STRING:
//                log.info("HSSFCell.CELL_TYPE_STRING");///////
                return cell.getRichStringCellValue().getString();
            case HSSFCell.CELL_TYPE_BLANK:
                return null;
            case HSSFCell.CELL_TYPE_FORMULA:
                System.out.println("CELL_TYPE_FORMULA");
            case HSSFCell.CELL_TYPE_BOOLEAN:
                System.out.println("BOOLEAN");
            case HSSFCell.CELL_TYPE_ERROR:
                System.out.println("ERROR");
            default:
                throw new IllegalArgumentException("invalid cellType:" + cell.getCellType());
        }
    }
    
    public static HSSFWorkbook getWorkBook(String fileName) {
        try {
            return new HSSFWorkbook(new FileInputStream(fileName));
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalArgumentException(ex.getMessage());
        }
    }
    

}
