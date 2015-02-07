package com.bestway.util;

import java.awt.Container;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableColumn;

import com.bestway.client.util.DgSaveTableListToExcel;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.SerialColumn;
import com.bestway.client.windows.form.FmMain;

public class FileUtils {
	/**
	 * 下载文件
	 * @param url
	 * @param ext
	 */
	public static void downloadFile(final URL url, final String ext){
		new AsynSwingWorker<Object>() {
			protected Object submit() {
				JFileChooser f = new JFileChooser();
				javax.swing.filechooser.FileFilter filter = new FileNameExtensionFilter(ext,ext);
			    f.setFileFilter(filter);                        
			    String name = FmMain.getInstance().getDeskPanel().getSelectedFrame().getTitle()+"导入样板.xls";
			    f.setSelectedFile(new File(name));
			    if(f.showSaveDialog(FmMain.getInstance()) == JFileChooser.APPROVE_OPTION){
			        File file = f.getSelectedFile();
			        String path = file.getAbsolutePath();         
			        if(!path.endsWith("."+ext)){
			        	path = path.concat(".").concat(ext);
			        }            
			        FileOutputStream out  = null;
			        InputStream in = null;
			        try{
			        	in = url.openStream();
			            out = new FileOutputStream(new File(path));
			            IOUtils.copy(in, out);
			            JOptionPane.showMessageDialog(FmMain.getInstance(), "下载完成！","提示",JOptionPane.INFORMATION_MESSAGE);                    
			        }catch(Exception ex){
			        	errorHandler(ex);
			        }finally{            	
			        	IOUtils.closeQuietly(out);
			        	IOUtils.closeQuietly(in);
			        }
			    }
				return null;
			}
			
		}.doWork();
	}
	/**
	 * 从表格模型中导出Excel,其中表格中的序号列不导出
	 * @param model
	 * @param container
	 */
	public static void exportTableToExcel(JTableListModel model,Container container){
		JFileChooser f = new JFileChooser();
		javax.swing.filechooser.FileFilter filter = new FileNameExtensionFilter("xls","xls");
	    f.setFileFilter(filter);                        
	    String name = FmMain.getInstance().getDeskPanel().getSelectedFrame().getTitle()+"导入样板.xls";
	    f.setSelectedFile(new File(name));
	    if(f.showSaveDialog(FmMain.getInstance()) == JFileChooser.APPROVE_OPTION){
	        File file = f.getSelectedFile();
	        String path = file.getAbsolutePath();         
	        if(!path.endsWith(".xls")){
	        	path = path.concat(".").concat("xls");
	        }	        
	        if(new File(path).exists()){
	        	if (JOptionPane.showConfirmDialog(container, "文件已经存在,是否覆盖原文件?","警告!", JOptionPane.YES_NO_OPTION,
						JOptionPane.WARNING_MESSAGE) != JOptionPane.YES_OPTION) {
					return;
				}
	        }
	        DgSaveTableListToExcel dg = new DgSaveTableListToExcel(){	        	
	        	public List<Integer> getRemoveExportColumns(JTable table) {
	        		List<Integer> removeExportColumns = new ArrayList<Integer>();
	        		int columnCount = table.getColumnCount();
	        		for (int i = 0; i < columnCount; i++) {
	        			TableColumn tableColumn = table.getColumnModel().getColumn(i);
	        			if (tableColumn.getPreferredWidth() <= 0 || "行号".equals(tableColumn.getIdentifier())) {
	        				removeExportColumns.add(i);
	        			}
	        		}
	        		return removeExportColumns;
	        	}
	        };
	        String excelFileName = FmMain.getInstance().getDeskPanel().getSelectedFrame().getTitle();
			dg.setTableModel(model);
			dg.setFileName(path);
			dg.setSheetCaption(excelFileName);
			dg.setTitle("保存(" + excelFileName+ ")到Excel");
			dg.setAll(true);			
	        dg.setExportCatpion(false);
	        dg.setVisible(true);
	    }
	}
	
	public static void writeStringToFile(File file, String data, String encoding)
			throws IOException {
		OutputStream out = new FileOutputStream(file);
		try {
			IOUtils.write(data, out, encoding);
		} finally {
			IOUtils.closeQuietly(out);
		}
	}

	
}
