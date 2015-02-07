package com.bestway.client.common;

import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.LabelCell;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;

public class FileReading {

    /**
     * ��ȡ excel return Object[]
     *
     * @param beginRow
     *            1,2,3,4,5....
     * @param fileName
     * @return
     */
    public static List<List> readExcel(String fileName, int beginRow,
            String encoding) {
        return readExcel(new File(fileName), beginRow, encoding);
    }

    /**
     * 读多个sheet
     * @param file
     * @param beginRow
     * @param encoding
     * @param sheetMuch
     * @return
     */
    public static List readExcel(File file, int beginRow, String encoding, boolean muchSheet) {
        List list = new ArrayList();
        Workbook workbook = null;
        try {

            WorkbookSettings wbs = new WorkbookSettings();
            //
            // excel is default ISO-8859-1 encoding
            // setEncoding("UTF-8"); utf-8 no support in excel
            //
            if (encoding == null || encoding.trim().equals("")) {
                wbs.setEncoding("ISO-8859-1");
            } else {
                wbs.setEncoding(encoding);
            }
            workbook = Workbook.getWorkbook(file, wbs);
            //
            // ��ȡ�Sheet�� default 0
            //
            int sheetCount = workbook.getNumberOfSheets();
            for (int m = 0; m < sheetCount; m++) {
                List relist = new ArrayList();
                Sheet sheet = workbook.getSheet(m);
                String sheetName = sheet.getName();
                int columns = sheet.getColumns();
                int rows = sheet.getRows();
                for (int j = 0; j < rows; j++) {
                    if (beginRow > 1 && beginRow - 2 >= j) {
                        continue;
                    }
                    List<Object> row = new ArrayList<Object>();
                    for (int i = 0; i < columns; i++) {
                        row.add(getValue(sheet.getCell(i, j)));
                    }
                    relist.add(row);
                }
                list.add(sheetName);
                list.add(relist);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                workbook.close();
            }
        }
        return list;
    }

    /**
     * 读单个sheet
     * @param file
     * @param beginRow
     * @param encoding
     * @return
     */
    public static List<List> readExcel(File file, int beginRow, String encoding) {
        List<List> list = new ArrayList<List>();
        Workbook workbook = null;
        try {

            WorkbookSettings wbs = new WorkbookSettings();
            //
            // excel is default ISO-8859-1 encoding
            // setEncoding("UTF-8"); utf-8 no support in excel
            //
            if (encoding == null || encoding.trim().equals("")) {
                wbs.setEncoding("ISO-8859-1");
            } else {
                wbs.setEncoding(encoding);
            }
            workbook = Workbook.getWorkbook(file, wbs);
            //
            // ��ȡ�Sheet�� default 0
            //
            workbook.getNumberOfSheets();
            Sheet sheet = workbook.getSheet(0);

            int columns = sheet.getColumns();
            int rows = sheet.getRows();

            for (int j = 0; j < rows; j++) {
                if (beginRow > 1 && beginRow - 2 >= j) {
                    continue;
                }
                List<Object> row = new ArrayList<Object>();
                for (int i = 0; i < columns; i++) {
                    row.add(getValue(sheet.getCell(i, j)));
                }
                list.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                workbook.close();
            }
        }
        return list;
    }

    /**
     * 读单个sheet
     * @param file
     * @param beginRow
     * @param encoding
     * @return
     */
    public static List<List> readExcel(File file, int beginRow, String encoding, int sheetIndex) {
        List<List> list = new ArrayList<List>();
        Workbook workbook = null;
        try {
            WorkbookSettings wbs = new WorkbookSettings();
            //
            // excel is default ISO-8859-1 encoding
            // setEncoding("UTF-8"); utf-8 no support in excel
            //
            if (encoding == null || encoding.trim().equals("")) {
                wbs.setEncoding("ISO-8859-1");
            } else {
                wbs.setEncoding(encoding);
            }
            workbook = Workbook.getWorkbook(file, wbs);
            workbook.getNumberOfSheets();
            Sheet sheet = workbook.getSheet(sheetIndex);

            int columns = sheet.getColumns();
            int rows = sheet.getRows();

            for (int j = 0; j < rows; j++) {
                if (beginRow > 1 && beginRow - 2 >= j) {
                    continue;
                }
                List<Object> row = new ArrayList<Object>();
                for (int i = 0; i < columns; i++) {
                    row.add(getValue(sheet.getCell(i, j)));
                }
                list.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                workbook.close();
            }
        }
        return list;
    }

    /**
     * 读单个sheet
     * @param file
     * @param beginRow
     * @param encoding
     * @return
     */
    public static int readExcelSheetCount(File file) {
        Workbook workbook = null;
        try {
            WorkbookSettings wbs = new WorkbookSettings();
            workbook = Workbook.getWorkbook(file, wbs);
            return workbook.getNumberOfSheets();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                workbook.close();
            }
        }
        return 0;
    }

    /**
     * ��ȡ excel return String[]
     *
     * @param row
     *            1,2,3,4,5....
     * @param fileName
     * @return
     */
    public static String[] readExcelCaption(File file, int row, String encoding) {
        String[] columnNames = null;
        Workbook workbook = null;
        try {
            WorkbookSettings wbs = new WorkbookSettings();
            //
            // excel is default ISO-8859-1 encoding
            // setEncoding("UTF-8"); utf-8 no support in excel
            //
            if (encoding == null || encoding.trim().equals("")) {
                wbs.setEncoding("ISO-8859-1");
            } else {
                wbs.setEncoding(encoding);
            }
            workbook = Workbook.getWorkbook(file, wbs);
            //
            // ��ȡ�Sheet�� default 0
            //
            Sheet sheet = workbook.getSheet(0);

            int columns = sheet.getColumns();
            int rows = sheet.getRows();

            //
            // init Object[]
            //
            columnNames = new String[columns];

            if (row > rows || row <= 0) {
                for (int i = 0; i < columnNames.length; i++) {
                    columnNames[i] = String.valueOf(i + 1);
                }
            } else {
                for (int i = 0; i < columns; i++) {
                    String contents = sheet.getCell(i, row - 1).getContents();
                    contents = (contents == null && contents.trim().equals("")) ? String.valueOf(i + 1)
                            : contents;
                    columnNames[i] = contents;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                workbook.close();
            }
        }
        return columnNames;
    }

    public static String[] readExcelCaption(String fileName, int row,
            String encoding) {
        return readExcelCaption(new File(fileName), row, encoding);
    }

    // private static String formatBig(String amount, int bigD, boolean isZero)
    // {
    // if (amount == null || amount.equals("")) {
    // amount = "0";
    // }
    // BigDecimal bd = new BigDecimal(amount);
    // return bd.setScale(bigD, BigDecimal.ROUND_HALF_UP).toString();
    // // String amountStr =
    // // if (isZero) {
    // // return amountStr;
    // // }
    // // for (int i = amountStr.length(); i > 0; i--) {
    // // if (amountStr.substring(i - 1, i).equals("0")) {
    // // amountStr = amountStr.substring(0, i - 1);
    // // } else if (amountStr.substring(i - 1, i).equals(".")) {
    // // amountStr = amountStr.substring(0, i - 1);
    // // break;
    // // } else {
    // // break;
    // // }
    // // }
    // // return amountStr;
    // }
    /**
     * ��ȡ��Ԫ���ֵ
     *
     * @param cell
     * @return
     */
    private static Object getValue(Cell cell) {
        Object returnValue = null;
        if (cell.getType() == CellType.LABEL) {
            LabelCell labelCell = (LabelCell) cell;
            returnValue = labelCell.getString();
        } else if (cell.getType() == CellType.NUMBER) {
//            NumberCell numberCell = (NumberCell) cell;
//            numberCell.getNumberFormat().setGroupingUsed(false);
//            numberCell.getNumberFormat().setMaximumFractionDigits(99);
//            numberCell.getNumberFormat().setRoundingMode(RoundingMode.HALF_UP);
//            String content = cell.getContents();
            Double doubleValue = ((NumberCell) cell).getValue();
//            System.out.println("doubleValue  is " + doubleValue);
            NumberFormat format = NumberFormat.getInstance();
            format.setMinimumFractionDigits(0);
            format.setMaximumFractionDigits(9);
            format.setMaximumIntegerDigits(10);
            format.setMinimumIntegerDigits(1);
            format.setGroupingUsed(false);
            format.setRoundingMode(RoundingMode.HALF_UP);
            String content = format.format(doubleValue);
//            System.out.println("double  is " + content);
            if (content == null || "".equals(content.trim())) {
                return "";
            }
            if (content.indexOf(".") < 0) {
                return content;
            } else if (checkAllIsZeroAfterDot(content.trim())) {//判断小数点后面是否全部为0，为True则截取小数点之前的内容
                if (content.equals("0")) {
                    return content;
                }
                returnValue = content.trim().substring(0,
                        content.trim().indexOf("."));
            } else {
                Double value = doubleValue;//numberCell.getValue();
                if (value != null) {
                    returnValue = value.toString().trim();
                    String tempValue = returnValue.toString().trim();
                    if (tempValue.substring(tempValue.length() - 2,
                            tempValue.length()).equals(".0")) {
                        returnValue = String.valueOf(Double.valueOf(tempValue).intValue());
                    }
                }
            }
        } else if (cell.getType() == CellType.DATE) {
            DateCell dateCell = (DateCell) cell;
            Date date = dateCell.getDate();
            if (date != null) {
                SimpleDateFormat bartDateFormat = new SimpleDateFormat(
                        "yyyy-MM-dd");
                returnValue = bartDateFormat.format((Date) date);
            }
        } else {
            returnValue = cell.getContents();
        }
        return returnValue;
    }

    /**
     * 判断小数点后面是否全部为0，是返回True,否返回False.
     *
     * @param str
     * @return
     */
    public static boolean checkAllIsZeroAfterDot(String str) {
        String s = str.substring(str.indexOf(".") + 1);
        for (int i = 0; i < s.length(); i++) {
            String temp = String.valueOf(s.charAt(i));
            if (!temp.equals("0")) {
                return false;
            }
        }
        return true;
    }

    /**
     * ��ȡ excel return Object[]
     *
     * @param beginRow
     *            1,2,3,4,5....
     * @param fileName
     * @return
     */
    public static List<List> readTxt(File file, int beginRow, String encoding) {
        BufferedReader in = null;
        List<List> list = new ArrayList<List>();
        try {
            if (encoding != null && !encoding.trim().equals("")) {
                in = new BufferedReader(new InputStreamReader(
                        new FileInputStream(file), encoding));//
            } else {
                in = new BufferedReader(new FileReader(file));
            }
            String s = "";
            for (int row = 0; (s = in.readLine()) != null; row++) {
                if (beginRow > 1 && beginRow - 2 >= row) {
                    continue;
                }
                if (s.trim().equals("")) {
                    continue;
                }
                List<Object> rows = new ArrayList<Object>();
                String[] strs = s.split(String.valueOf((char) KeyEvent.VK_TAB));
                for (int column = 0; column < strs.length; column++) {
                    rows.add(strs[column]);
                }
                list.add(rows);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception ex) {
            }
        }
        return list;
    }

    public static List<List> readTxt(String fileName, int beginRow,
            String encoding) {
        return readTxt(new File(fileName), beginRow, encoding);
    }

    /**
     * ��ȡ excel return String[]
     *
     * @param row
     *            1,2,3,4,5....
     * @param fileName
     * @return
     */
    public static String[] readTxtCaption(File file, int row, String encoding) {
        String[] columnNames = new String[0];
        BufferedReader in = null;
        try {
            if (encoding != null && !encoding.trim().equals("")) {
                in = new BufferedReader(new InputStreamReader(
                        new FileInputStream(file), encoding));
            } else {
                in = new BufferedReader(new FileReader(file));
            }
            int columns = 0;
            boolean isFirst = true;
            boolean isExistCaption = false;
            String s = "";
            for (int i = 0; (s = in.readLine()) != null; i++) {
                if (s.trim().equals("")) {
                    continue;
                }
                if (isFirst) {
                    String[] strs = s.split(String.valueOf((char) KeyEvent.VK_TAB));
                    columns = strs.length;
                    isFirst = false;
                }
                if (i == row - 1) {
                    String[] strs = s.split(String.valueOf((char) KeyEvent.VK_TAB));
                    columnNames = strs;
                    isExistCaption = true;
                    break;
                }
            }
            if (isExistCaption == false) {
                columnNames = new String[columns];
                for (int i = 0; i < columns; i++) {
                    columnNames[i] = String.valueOf(i + 1);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception ex) {
            }
        }
        return columnNames;
    }

    public static String[] readTxtCaption(String fileName, int row,
            String encoding) {
        return readTxtCaption(new File(fileName), row, encoding);
    }
}
