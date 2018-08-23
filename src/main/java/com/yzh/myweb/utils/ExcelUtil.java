/**  
 */
package com.yzh.myweb.utils;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.formula.eval.ErrorEval;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.util.LocaleUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**   
 * @ClassName:  ExcelUtil   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: david.li 
 * @date:   2018年7月24日 下午1:50:59      
 */
public class ExcelUtil {
	
	
	public static Map<String, Object> readXls(InputStream is, String dateType) throws Exception {

        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);

        Map<String, Object> map = new HashMap<String, Object>(xssfWorkbook.getNumberOfSheets());

        for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
            List<Map<String, String>> list = new ArrayList<Map<String, String>>();
            List<List<String>> hiddenList = new ArrayList<List<String>>();
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
            if (xssfSheet == null) {
                continue;
            }
            if (xssfSheet.getSheetName().equals("HiddenSelect")) {
                if (xssfSheet.getLastRowNum() > 0) {
                    for (int rowNum = 0; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                        XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                        int minCellX = xssfRow.getFirstCellNum();
                        int maxCellX = xssfRow.getLastCellNum();
                        List<String> rowList = new ArrayList<String>();
                        for (int cellX = minCellX; cellX < maxCellX; cellX++) {
                            XSSFCell xssfCell = xssfRow.getCell(cellX);
                            if (xssfCell == null) {
                                continue;
                            }
                            rowList.add(xssfCell.getStringCellValue());
                        }
                        hiddenList.add(rowList);
                    }
                }
                map.put("HiddenSelect", hiddenList);
            } else {
                List<String> handList = new ArrayList<String>();
                XSSFRow handRow = xssfSheet.getRow(0);
                int minCellX = handRow.getFirstCellNum();
                int maxCellX = handRow.getLastCellNum();
                for (int cellX = minCellX; cellX < maxCellX; cellX++) {
                    XSSFCell xssfCell = handRow.getCell(cellX);
                    if (xssfCell == null) {
                        continue;
                    }
                    handList.add(getStringVal(xssfCell, dateType));
                }

                for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                    XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                    Map<String, String> rowMap = new HashMap<String, String>(maxCellX);
                    for (int cellX = minCellX; cellX < maxCellX; cellX++) {
                        XSSFCell xssfCell = xssfRow.getCell(cellX);
                        if (xssfCell == null) {
                            continue;
                        }
                        rowMap.put("value" + (cellX + 1), getStringVal(xssfCell, dateType));
                    }
                    list.add(rowMap);
                }
                map.put(numSheet + "", list);
            }
        }
        return map;
    }
	
	
	/**
     * 将每个cell中值进行格式转换
     *
     * @param cell
     * @return
     */
    public static String getStringVal(XSSFCell cell, String dateType) {
        if (dateType == null || dateType.isEmpty()) {
            dateType = "yyyy-MM-dd HH:mm:ss";
        }
        switch (cell.getCellTypeEnum()) {
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    DateFormat sdf = new SimpleDateFormat(dateType);
                    sdf.setTimeZone(LocaleUtil.getUserTimeZone());
                    return sdf.format(cell.getDateCellValue());
                }
                NumberFormat nf = NumberFormat.getInstance();
                nf.setGroupingUsed(false);
                return nf.format(cell.getNumericCellValue());
            case STRING:
                return cell.getRichStringCellValue().toString();
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return "";
            case BOOLEAN:
                return cell.getBooleanCellValue() ? "TRUE" : "FALSE";
            case ERROR:
                return ErrorEval.getText(cell.getErrorCellValue());
            default:
                return "Unknown Cell Type: " + cell.getCellTypeEnum();
        }
    }

}
