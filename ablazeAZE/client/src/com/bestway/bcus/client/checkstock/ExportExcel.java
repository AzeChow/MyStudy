package com.bestway.bcus.client.checkstock;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ExportExcel {

	List<String[]> columns = null;
	
	public List<Object> dataSource = null;
	
	public void setDataSource(List<Object> dataSource){
		this.dataSource = dataSource;
	}
	
	/**
	 * 0:中文名称 1：对象的属性名称 2：列的宽
	 * @param columns
	 */
	public void setColumns(List<String[]> columns){
		this.columns = columns;
	}
	
	public void export(File file){
		try {
			if(file==null){
				System.out.println("导出路径为空");
				return;
			}
            WritableWorkbook book = Workbook.createWorkbook(file);
            WritableSheet sheet = book.createSheet("第一页", 0);
            setTitle(sheet);//设置标题
            setContent(sheet);//设置内容
            book.write();
            book.close();
        } catch (Exception e) {
        	e.printStackTrace();
        }
		   
	}
	
	/**
	 * 设置内容
	 * @param sheet
	 */
	public void setContent(WritableSheet sheet){
        try { 
        	jxl.write.WritableCellFormat contentFormat = getStyle(false);
            
            for (int i = 0; i < dataSource.size(); i++) {
            	for (int j = 0; j < columns.size(); j++) {
            		Object object = dataSource.get(i);
            		String[] propertyName = columns.get(j)[1].split("\\.");
            		Object obj = null;
            		for (int k = 0; k < propertyName.length; k++) {
            			obj = PropertyUtils.getSimpleProperty(object, propertyName[k]);
            			object = obj;
            		}
            		
            		
            		jxl.write.Number serialNumber = new jxl.write.Number(0,i+1,i+1,contentFormat);
        			sheet.addCell(serialNumber);
            		
            		if(obj instanceof String){
            			jxl.write.Label label = new jxl.write.Label(j+1,i+1,obj.toString(),contentFormat);
            			sheet.addCell(label);
            		}else if(obj instanceof Integer){
            			 jxl.write.Number number = new jxl.write.Number(j+1,i+1,(Integer)obj,contentFormat);
            			 sheet.addCell(number);
            		}else if(obj instanceof Double){
           			 	jxl.write.Number number = new jxl.write.Number(j+1,i+1,(Double)obj,contentFormat);
           			 	sheet.addCell(number);
            		}else if(obj instanceof Date){
            			String date = new SimpleDateFormat("yyyy-MM-dd").format((Date)obj);
            			jxl.write.Label label = new jxl.write.Label(j+1,i+1,date,contentFormat);
    					sheet.addCell(label);
            		}else{
            			jxl.write.Label label = new jxl.write.Label(j+1,i+1,"",contentFormat);
            			sheet.addCell(label);
            		}
    			}
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 设置标题
	 * @param sheet
	 */
	public void setTitle(WritableSheet sheet){
		
        try {
        	jxl.write.WritableCellFormat titleFormat = getStyle(true);//获取样式
    		sheet.setColumnView(0,4);
            jxl.write.Label serialNum = new jxl.write.Label(0,0,"序号",titleFormat);
        	sheet.addCell(serialNum);
            for (int i = 0; i < columns.size(); i++) {
            	sheet.setColumnView(i+1,Integer.parseInt(columns.get(i)[2]));
            	jxl.write.Label label = new jxl.write.Label(i+1,0,columns.get(i)[0],titleFormat);
            	sheet.addCell(label);
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取样式
	 * @param isTitle
	 * @return
	 */
	public jxl.write.WritableCellFormat getStyle(boolean isTitle){
		int fontSize = 8;
		if(isTitle){
			fontSize = 14;
		}
		WritableFont titleFont = new WritableFont(WritableFont.ARIAL, fontSize,
				WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
				Colour.BLACK);
		jxl.write.WritableCellFormat wcfFHead = new jxl.write.WritableCellFormat(
				titleFont);
  	    try {
    	    wcfFHead.setAlignment(Alignment.CENTRE);
			wcfFHead.setVerticalAlignment(VerticalAlignment.CENTRE);
			if(isTitle){
				wcfFHead.setBackground(Colour.GRAY_25);
			}
			wcfFHead.setBorder(Border.ALL, BorderLineStyle.THIN);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return wcfFHead;
	}
	
	public static void main(String[] args) {
	}
}
