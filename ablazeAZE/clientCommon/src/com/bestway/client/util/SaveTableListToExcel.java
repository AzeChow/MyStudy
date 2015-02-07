/*
 * Created on 2004-11-18
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.client.util;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.bestway.client.util.groupableheader.ColumnGroup;
import com.bestway.client.util.groupableheader.GroupableTableHeader;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class SaveTableListToExcel extends SwingWorker {// implements Runnable

	private String fileName = null;

	private String sheetCaption = "";

	private JTableListModelBase tableModel = null;

	private WritableWorkbook workbook = null;

	private DgSaveTableListToExcel dialog = null;

	private String encoding = null;
	/**是否输出标题**/
	private boolean exportCaption = true;

	// private List<Object> dataSource = null;

	private List<ColumnGroup> lsGroupColumn = new ArrayList<ColumnGroup>();

	private Map<Integer, Integer> removeColumnsMap = new HashMap<Integer, Integer>();

	private Map<Integer, Class> hmType = new HashMap<Integer, Class>();

	private boolean isAll = true;

	public void setAll(boolean isAll) {
		this.isAll = isAll;
	}

	/**
	 * @return Returns the dialog.
	 */
	public DgSaveTableListToExcel getDialog() {
		return dialog;
	}

	/**
	 * @param dialog
	 *            The dialog to set.
	 */
	public void setDialog(DgSaveTableListToExcel dialog) {
		this.dialog = dialog;
	}

	private Class getTypeByColumn(int row, int col) {
		if (hmType.get(col) == null) {
			hmType.put(col, tableModel.getTypeByColumn(col));
		}
		return (Class) hmType.get(col);
	}

	public String nowToDate() {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat formater = new SimpleDateFormat(pattern);
		Date date1 = new Date();
		String str1 = formater.format(date1);
		return str1;
	}
	
	public boolean toExcel(){
		return exportExcel(exportCaption);
	}

	public boolean exportExcel(boolean exportCaption) {
		if (fileName == null || tableModel == null) {
			return false;
		}
		if (dialog != null) {
			dialog.doBeforThreadRun();
		}
		//
		// 编码
		//
		WorkbookSettings wbs = new WorkbookSettings();
		if (encoding != null) {
			wbs.setEncoding(this.encoding);
		}

		//
		// 数据源
		//
		// if (tableModel == null) {
		// return;
		// }
		// if (isAll) {
		// dataSource = tableModel.getList();
		// } else {
		// dataSource = ((JTableListModel) tableModel).getCurrentRows();
		// }
		// }
		try {
			workbook = Workbook.createWorkbook(new File(fileName), wbs);
		} catch (IOException e) {
			if (dialog != null) {
				dialog.fileIsOpenedException();
				// dialog.doAfterThreadRun();
				return false;
			}
			e.printStackTrace();
		}
		try {

			if (dialog != null) {
				if (sheetCaption == null || "".equals(sheetCaption.trim())) {
					sheetCaption = dialog.getModuleName();
				}
			}
			WritableSheet ws = workbook.createSheet(sheetCaption, 0);
			//
			// 显示标题
			//
			// int columnCounts = tableModel.getColumnCount();
			int columnCount = tableModel.getTable().getColumnModel()
					.getColumnCount();
			// System.out.println(columnCounts+":::: "+columnCount);
			int rowCount = (isAll ? tableModel.getList().size() : tableModel
					.getTable().getSelectedRowCount());// dataSource.size();
			int beginRow = (isAll ? 0 : (rowCount <= 0 ? 0 : tableModel
					.getTable().getSelectedRows()[0]));
			if (dialog != null) {
				dialog.setProgressBarMaximum(rowCount + 1);
			}
			WritableFont wfHead = new WritableFont(WritableFont.ARIAL, 12,
					WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
					Colour.BLACK);
			WritableCellFormat wcfFHead = new WritableCellFormat(wfHead);
			try {
				wcfFHead.setAlignment(Alignment.CENTRE);
				wcfFHead.setVerticalAlignment(VerticalAlignment.CENTRE);
				wcfFHead.setBackground(Colour.GRAY_25);
				wcfFHead.setBorder(Border.ALL, BorderLineStyle.THIN);
			} catch (WriteException e2) {
				e2.printStackTrace();
			}
			JTable table = tableModel.getTable();
			boolean isGroupableHeader = false;
			int groupHeight = 1;
			//
			// check is GroupableTableHeader
			//
			GroupableTableHeader tableHeader = null;
			JTableHeader jTableHeader = table.getTableHeader();
			if (jTableHeader instanceof GroupableTableHeader) {
				tableHeader = (GroupableTableHeader) jTableHeader;
				isGroupableHeader = true;
				groupHeight = tableHeader.getTableHeaderLayerCount();
			}
			if(exportCaption){
				// 添加标题头------------------
				WritableFont wfHead1 = new WritableFont(WritableFont.ARIAL, 14,
						WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
						Colour.BLACK);
				WritableCellFormat wcfFHead1 = new WritableCellFormat(wfHead1);
				try {
					wcfFHead1.setAlignment(Alignment.CENTRE);
					wcfFHead1.setVerticalAlignment(VerticalAlignment.CENTRE);
					wcfFHead1.setBorder(Border.ALL, BorderLineStyle.THIN);
				} catch (WriteException e2) {
					e2.printStackTrace();
				}
	
				Label lb1 = new Label(0, 0, sheetCaption + " (Date:" + nowToDate()+ ")", wcfFHead1);
				try {
					ws.addCell(lb1);
					ws.setColumnView(0, 100);
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					ws.mergeCells(0, 0, columnCount - removeColumnsMap.size() - 1,0);
				} catch (RowsExceededException e) {
					e.printStackTrace();
				} catch (WriteException e) {
					e.printStackTrace();
				}
			}

			// ----------------------------
			if (isGroupableHeader) {
				Enumeration enumeration = tableHeader.getColumnModel()
						.getColumns();

				for (int i = 0, removeColumnCount = 0; enumeration
						.hasMoreElements()
						&& i < columnCount; i++) {
					TableColumn aColumn = (TableColumn) enumeration
							.nextElement();
					if (removeColumnsMap.containsKey(i)) {
						removeColumnCount++;
						continue;
					}
					Enumeration cGroups = tableHeader.getColumnGroups(aColumn);
					int j = 0;
					int tempI = i - removeColumnCount;

					if (cGroups != null) {
						while (cGroups.hasMoreElements()) {
							ColumnGroup cGroup = (ColumnGroup) cGroups
									.nextElement();
							if (!lsGroupColumn.contains(cGroup)) {
								Label lb = new Label(tempI, j + (exportCaption? 1 :0), cGroup
										.getHeaderValue() == null ? "" : cGroup
										.getHeaderValue().toString(), wcfFHead);
								try {
									ws.addCell(lb);
									ws.setColumnView(tempI, tableModel
											.getColumnWidth(i) / 6);
								} catch (Exception e) {
									e.printStackTrace();
								}
								try {
									ws.mergeCells(tempI, j + (exportCaption? 1 :0), tempI
											+ cGroup.getColumnCount() - 1,
											j + (exportCaption? 1 :0));
								} catch (RowsExceededException e) {
									e.printStackTrace();
								} catch (WriteException e) {
									e.printStackTrace();
								}
								lsGroupColumn.add(cGroup);
							}
							j++;
						}
					}

					if (cGroups == null) {
						Label lb = new Label(tempI, (exportCaption? 1 :0), tableModel
								.getColumnName(i), wcfFHead);
						try {
							ws.addCell(lb);
							ws.setColumnView(tempI, tableModel
									.getColumnWidth(i) / 6);
						} catch (Exception e) {
							e.printStackTrace();
						}
						try {
							ws.mergeCells(tempI, (exportCaption? 1 :0), tempI, groupHeight-(exportCaption? 0 :1));
						} catch (RowsExceededException e) {
							e.printStackTrace();
						} catch (WriteException e) {
							e.printStackTrace();
						}
					} else {
						Label lb = new Label(tempI, groupHeight-(exportCaption? 0 :1), tableModel
								.getColumnName(i), wcfFHead);
						try {
							ws.addCell(lb);
							ws.setColumnView(tempI, tableModel
									.getColumnWidth(i) / 6);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}

			} else {
				int removeColumnCount = 0;
				for (int i = 0; i < columnCount; i++) {
					if (removeColumnsMap.containsKey(i)) {
						removeColumnCount++;
						continue;
					}
					int tempI = i - removeColumnCount;
					Label lb = new Label(tempI, (exportCaption? 1 :0), tableModel.getColumnName(i),
							wcfFHead);
					try {
						ws.addCell(lb);
						ws.setColumnView(tempI,
								tableModel.getColumnWidth(i) / 6);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			if (dialog != null) {
				dialog.setProgressBarValue(1);
			}
			WritableCellFormat wcfFDetail = new WritableCellFormat();
			try {
				wcfFDetail.setBorder(Border.ALL, BorderLineStyle.THIN);
			} catch (WriteException e2) {
				e2.printStackTrace();
			}
			// 
			// 保存记录
			//
			int currentRow = beginRow;
			for (int i = 0; i < rowCount; i++, currentRow++) {
				int removeColumnCount = 0;
				for (int j = 0; j < columnCount; j++) {
					if (removeColumnsMap.containsKey(j)) {
						removeColumnCount++;
						continue;
					}
					if (j == 0) {
						jxl.write.Number labelN = new jxl.write.Number(j, i + (exportCaption? 1 :0)
								+ groupHeight, currentRow + 1, wcfFHead);// i +
						// 1
						try {
							ws.addCell(labelN);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						int tempJ = j - removeColumnCount;
						// Object value = tableModel.getValueAt(objValue, j);
						Object value = tableModel.getValueAt(currentRow, j);
						String caption = tableModel.getColumnName(j);
						Class cls = this.getTypeByColumn(i, j);
						// System.out.println("===cls=="+cls+" caption="+caption);
						if (cls.equals(Integer.class)) {

							if ("进出口类型".equals(caption)) {

								String str = ((DefaultTableCellRenderer) table
										.getCellRenderer(i, j)
										.getTableCellRendererComponent(table,
												value, false, false, i, j))
										.getText();
								Label lb = new Label(tempJ,
										i + (exportCaption? 1 :0) + groupHeight, str, wcfFDetail);
								try {
									ws.addCell(lb);
								} catch (Exception e) {
									e.printStackTrace();
								}
							} else {
								Integer intValue = (value == null) ? 0
										: Integer.parseInt(value.toString());
								jxl.write.Number labelN = new jxl.write.Number(
										tempJ, i + (exportCaption? 1 :0) + groupHeight, intValue,
										wcfFDetail);

								try {
									ws.addCell(labelN);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}

						} else if (cls.equals(Double.class)) {
							Double doubleValue = (value == null ? 0.0
									: new DecimalFormat().parse(
											value.toString()).doubleValue());// Double.parseDouble(value.toString());
							jxl.write.Number labelN = new jxl.write.Number(
									tempJ, i + (exportCaption? 1 :0) + groupHeight, doubleValue,
									wcfFDetail);
							try {
								ws.addCell(labelN);
							} catch (Exception e) {
								e.printStackTrace();
							}
						} else if (cls.equals(Boolean.class)) {
							jxl.write.Boolean labelB = new jxl.write.Boolean(
									tempJ, i + (exportCaption? 1 :0) + groupHeight,
									value == null ? false : Boolean
											.parseBoolean(value.toString()),
									wcfFDetail);
							try {
								ws.addCell(labelB);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						// else if (cls.equals(Date.class)) {
						// jxl.write.DateTime labelDT = new
						// jxl.write.DateTime(
						// j, i + 1, (Date) value, wcfFDetail);
						// try {
						// ws.addCell(labelDT);
						// } catch (Exception e) {
						// e.printStackTrace();
						// }
						// }
						else {
							String valueStr=(value == null ? "" : value.toString());
							if ("进出口类型".equals(caption)) {

								String str = ((DefaultTableCellRenderer) table
										.getCellRenderer(i, j)
										.getTableCellRendererComponent(table,
												value, false, false, i, j))
										.getText();
								valueStr=str;
							}
							
							Label lb = new Label(tempJ, i + (exportCaption? 1 :0) + groupHeight,
									valueStr,
									wcfFDetail);
							try {
								ws.addCell(lb);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}

				}
				if (dialog != null) {
					dialog.setProgressBarValue(i + 2);
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			try {
				workbook.write();
			} catch (IOException e1) {
				e1.printStackTrace();
				return false;
			}
			try {
				workbook.close();
			} catch (Exception e2) {
				e2.printStackTrace();
				return false;
			}
		}
		return true;
	}

	/**
	 * @return Returns the fileName.
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName
	 *            The fileName to set.
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getSheetCaption() {
		return sheetCaption;
	}

	public void setSheetCaption(String sheetCaption) {
		this.sheetCaption = sheetCaption;
	}

	public void setRemoveExportColumns(List<Integer> removeExportColumns) {
		for (int i = 0, n = removeExportColumns.size(); i < n; i++) {
			this.removeColumnsMap.put(removeExportColumns.get(i),removeExportColumns.get(i));
		}
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	// public void setDataSource(List<Object> dataSource) {
	// this.dataSource = dataSource;
	// }

	public JTableListModelBase getTableModel() {
		return tableModel;
	}

	public void setTableModel(JTableListModelBase tableModel) {
		this.tableModel = tableModel;
	}

	@Override
	protected Object doInBackground() throws Exception {
		return this.toExcel();
	}

	@Override
	protected void done() {
		if (dialog != null) {
			try {
				Boolean isSuccess = (Boolean) this.get();
				if (isSuccess != null && isSuccess) {
					dialog.showSuccessMessage();
				} else {
					dialog.showFailMessage();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			dialog.doAfterThreadRun();
		}
	}

	public boolean isExportCaption() {
		return exportCaption;
	}

	public void setExportCaption(boolean exportCaption) {
		this.exportCaption = exportCaption;
	}
}