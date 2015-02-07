/*
 * Created on 2004-10-25
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.dataexport;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

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

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.FileReading;
import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.bcus.client.common.TableTextFieldEditor;
import com.bestway.bcus.client.common.TableTextFieldEditorEvent;
import com.bestway.bcus.system.entity.Company;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.FileType;
import com.bestway.common.dataexport.action.DataExportAction;
import com.bestway.common.dataexport.entity.DBToTxtRegion;
import com.bestway.common.dataexport.entity.TxtDBTask;
import com.bestway.common.dataexport.entity.TxtDBTaskDetail;
import com.bestway.common.dataexport.entity.TxtToDBRegion;
import com.bestway.common.tools.entity.TempResultSet;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author luosheng 2006/9/1
 * 
 */

public class DgTxtTaskExe extends JDialogBase {

	private javax.swing.JPanel	jContentPane		= null;
	private DataExportAction	dataExportAction	= null;
	private JPanel				jPanel				= null;
	private JSplitPane			jSplitPane			= null;
	private JPanel				jPanel1				= null;
	private JPanel				jPanel2				= null;
	private JToolBar			jToolBar			= null;
	private JButton				btnExecuteTask		= null;
	private JTable				jTable				= null;
	private JScrollPane			jScrollPane			= null;
	private JTable				jTable1				= null;
	private JScrollPane			jScrollPane1		= null;
	private TxtDBTask			txtDBTask			= null;
	private JTableListModel		tableModel			= null;
	private JTableListModel		tableModel1			= null;
	private JButton				btnClose			= null;
	private static final String	LINE_SEPARATOR		= System
															.getProperty("line.separator");

	/**
	 * This is the default constructor
	 */
	public DgTxtTaskExe() {
		super(false);
		dataExportAction = (DataExportAction) CommonVars
				.getApplicationContext().getBean("dataExportAction");
		initialize();
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		initUIComponents();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("文本导入导出任务执行");
		this.setSize(733, 541);
		this.setContentPane(getJContentPane());

	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new java.awt.BorderLayout());
			jContentPane.add(getJPanel(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	private void initTable(List dataSource) {
		tableModel = new JTableListModel(jTable, dataSource,
				new JTableListModelAdapter() {
					@Override
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("任务名称", "taskname", 200));
						list.add(addColumn("是否是父任务", "isParentTask", 100));
						list.add(addColumn("运行状态", "isExecute", 80));
						list.add(addColumn("备注", "note", 80));
						return list;
					}
				});
		jTable.getColumnModel().getColumn(2).setCellRenderer(
				new TableCheckBoxRender());
		jTable.getColumnModel().getColumn(3).setCellRenderer(
				new TableCheckBoxRender());

	}

	private void initTableDetail(List dataSource, final boolean isParentTask) {
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			@Override
			public List<JTableListColumn> InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				if (isParentTask) {
					list.add(addColumn("执行顺序", "seqNum", 80));
					list.add(addColumn("子任务名称", "subTxtDBTask.taskname", 200));
				} else {
					list.add(addColumn("执行顺序", "seqNum", 80));
					list.add(addColumn("执行事件名称", "jdbcSqlEvent.name", 200));
					list.add(addColumn("DB导出到文本域名称",
							"dbToTxtRegion.regionName", 200));
					list.add(addColumn("文本导出到DB域名称",
							"txtToDBRegion.regionName", 200));
				}
				return list;
			}
		};

		tableModel1 = new JTableListModel(jTable1, dataSource,
				jTableListModelAdapter, ListSelectionModel.SINGLE_SELECTION);

		//
		// 设置列的修改和修改 enter key event
		//
		List<Integer> editColumns = new ArrayList<Integer>();
		for (int i = 1; i < 2; i++) {
			editColumns.add(i);
			jTable1.getColumnModel().getColumn(i).setCellEditor(
					new TableTextFieldEditor(new JTextField(), event));
		}
		jTableListModelAdapter.setEditableColumn(editColumns);
	}

	/**
	 * cellEditor 回车事件
	 */
	private TableTextFieldEditorEvent	event	= new TableTextFieldEditorEvent() {
													public Object saveObject(
															Object obj) {
														return dataExportAction
																.saveTxtDBTaskDetail(
																		new Request(
																				CommonVars
																						.getCurrUser()),
																		(TxtDBTaskDetail) obj);
													}
												};

	private JPanel getJPanel() {
		if (jPanel == null) {
			javax.swing.JLabel jLabel2 = new JLabel();

			javax.swing.JLabel jLabel1 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();

			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jLabel.setText("");
			jLabel
					.setIcon(new ImageIcon(
							getClass()
									.getResource(
											"/com/bestway/bcus/client/resources/images/titlepoint.jpg")));
			jLabel1.setText("");
			jLabel1.setIcon(new ImageIcon(getClass().getResource(
					"/com/bestway/bcus/client/resources/images/titlepic.jpg")));
			jLabel2.setText("文本导入导出任务执行");
			jLabel2
					.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 20));
			jLabel2.setBackground(java.awt.Color.white);
			jLabel2.setForeground(new java.awt.Color(251, 131, 15));
			jPanel.setBackground(java.awt.Color.white);
			jPanel
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jPanel.add(jLabel, java.awt.BorderLayout.WEST);
			jPanel.add(jLabel1, java.awt.BorderLayout.EAST);
			jPanel.add(jLabel2, java.awt.BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * 
	 * This method initializes jSplitPane
	 * 
	 * 
	 * 
	 * @return javax.swing.JSplitPane
	 * 
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setDividerSize(8);
			jSplitPane.setDividerLocation(250);
			jSplitPane.setTopComponent(getJPanel1());
			jSplitPane.setBottomComponent(getJPanel2());
		}
		return jSplitPane;
	}

	/**
	 * 
	 * This method initializes jPanel1
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jPanel1.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel1;
	}

	/**
	 * 
	 * This method initializes jPanel2
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(new BorderLayout());
			jPanel2.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel2;
	}

	/**
	 * 
	 * This method initializes jToolBar
	 * 
	 * 
	 * 
	 * @return javax.swing.JToolBar
	 * 
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getBtnExecuteTask());
			jToolBar.add(getBtnClose());
		}
		return jToolBar;
	}

	/**
	 * 
	 * This method initializes btnExecute
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnExecuteTask() {
		if (btnExecuteTask == null) {
			btnExecuteTask = new JButton();
			btnExecuteTask.setText("执行任务");
			btnExecuteTask
					.addActionListener(new java.awt.event.ActionListener() {

						public void actionPerformed(java.awt.event.ActionEvent e) {

							if (tableModel.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(
										DgTxtTaskExe.this, "请选择你要立即执行的任务",
										"确认", 2);
								return;
							}
							TxtDBTask txtDBTask = (TxtDBTask) tableModel
									.getCurrentRow();
							ExecTxtDBTask thread = new ExecTxtDBTask(txtDBTask);
							thread.start();

						}
					});

		}
		return btnExecuteTask;
	}

	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
			jTable.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {

						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							if (tableModel == null
									|| tableModel.getCurrentRow() == null) {
								return;
							}
							ListSelectionModel lsm = (ListSelectionModel) e
									.getSource();
							if (!lsm.isSelectionEmpty()) {
								txtDBTask = (TxtDBTask) tableModel
										.getCurrentRow();
								showTaskDetail(txtDBTask);
							}

						}
					});
		}
		return jTable;
	}

	/**
	 * 
	 * This method initializes jScrollPane
	 * 
	 * 
	 * 
	 * @return javax.swing.JScrollPane
	 * 
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTable());
			jScrollPane
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
		}
		return jScrollPane;
	}

	/**
	 * 
	 * This method initializes jTable1
	 * 
	 * 
	 * 
	 * @return javax.swing.JTable
	 * 
	 */
	private JTable getJTable1() {
		if (jTable1 == null) {
			jTable1 = new JTable();
			jTable1.setIntercellSpacing(new java.awt.Dimension(0, 0));
		}
		return jTable1;
	}

	/**
	 * 
	 * This method initializes jScrollPane1
	 * 
	 * 
	 * 
	 * @return javax.swing.JScrollPane
	 * 
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJTable1());
			jScrollPane1
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
		}
		return jScrollPane1;
	}

	/**
	 * @return Returns the tableModel1.
	 */
	public JTableListModel getTableModel1() {
		return tableModel1;
	}

	/**
	 * @param tableModel1
	 *            The tableModel1 to set.
	 */
	public void setTableModel1(JTableListModel tableModel1) {
		this.tableModel1 = tableModel1;
	}

	/**
	 * This method initializes btnClose
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("关闭");
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgTxtTaskExe.this.dispose();
				}
			});
		}
		return btnClose;
	}

	/** init ui components data */
	private void initUIComponents() {
		List list = dataExportAction.findTxtDBTask(new Request(CommonVars
				.getCurrUser()));
		initTableDetail(new Vector(), false);
		initTable(list);
		if (list.size() > 0) {
			showTaskDetail((TxtDBTask) list.get(0));
		}
	}

	private void showTaskDetail(TxtDBTask txtDBTask) {
		List selList = dataExportAction.findTxtDBTaskDetail(new Request(
				CommonVars.getCurrUser()), txtDBTask);
		initTableDetail(selList, (txtDBTask.getIsParentTask() == null ? false
				: txtDBTask.getIsParentTask()));
	}

	class ExecTxtDBTask extends Thread {
		TxtDBTask	txtDBTask	= null;

		public ExecTxtDBTask(TxtDBTask txtDBTask) {
			this.txtDBTask = txtDBTask;
		}

		@Override
		public void run() {

			//
			// 用于标识这个对话框的ID
			//
			UUID uuid = UUID.randomUUID();
			final String flag = uuid.toString();

			CommonProgress.showProgressDialog(flag, DgTxtTaskExe.this, false,
					null, 0);
			CommonProgress.setMessage(flag, "正在执行任务, 请稍后...");

			btnExecuteTask.setEnabled(false);
			try {
				this.exec();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new RuntimeException(ex);
			} finally {
				btnExecuteTask.setEnabled(true);
				CommonProgress.closeProgressDialog(flag);
			}
		}

		private void exec() {
			long beginTime = System.currentTimeMillis();
			executeTxtDBImportExportData(txtDBTask, (Company) CommonVars
					.getCurrUser().getCompany());
			JOptionPane.showMessageDialog(DgTxtTaskExe.this, txtDBTask
					.getTaskname()
					+ " 任务执行完成！ 共用 "
					+ (System.currentTimeMillis() - beginTime)
					+ " 毫秒 ", "确认", 2);
		}
	}

	/** 执行导入导出数据 */
	public void executeTxtDBImportExportData(TxtDBTask txtDBTask,
			Company company) {

		if (txtDBTask.getIsParentTask() != null
				&& txtDBTask.getIsParentTask().booleanValue() == true) {
			List<TxtDBTaskDetail> list = this.dataExportAction
					.findTxtDBTaskDetail(new Request(CommonVars.getCurrUser()),
							txtDBTask);
			if (list.size() <= 0) {
				String info = txtDBTask.getTaskname() + " 没有任何子任务!!";
				throw new RuntimeException(info);
			}
			for (TxtDBTaskDetail txtDBTaskDetail : list) {
				TxtDBTask subTxtDBTask = txtDBTaskDetail.getSubTxtDBTask();
				if (subTxtDBTask != null) {
					executeSignTaskTxtDBImportExportData(subTxtDBTask, company);
				}
			}
		} else {
			//
			// 执行单任务
			//
			executeSignTaskTxtDBImportExportData(txtDBTask, company);
		}
	}

	/** 执行导入导出数据(不是父任务的) */
	private void executeSignTaskTxtDBImportExportData(TxtDBTask txtDBTask,
			Company company) {
		//
		// 验证是否在导入
		//
		String info = "";
		boolean isExecute = this.dataExportAction.isExecute(new Request(
				CommonVars.getCurrUser()), txtDBTask);
		if (isExecute == true) {
			info = txtDBTask.getTaskname() + "正在执行，请先停止该任务!!";
			throw new RuntimeException(info);
		}
		try {
			//
			// 加入正在导入标志
			//
			txtDBTask.setIsExecute(true);
			txtDBTask = this.dataExportAction.saveTxtDBTask(new Request(
					CommonVars.getCurrUser()), txtDBTask);
			//
			// 获得所有任务明细
			//
			List<TxtDBTaskDetail> txtDBTaskDetails = this.dataExportAction
					.findTxtDBTaskDetail(new Request(CommonVars.getCurrUser()),
							txtDBTask);

			String txtDBTaskName = txtDBTask.getTaskname();

			if (txtDBTaskDetails.size() <= 0) {
				info = txtDBTaskName + " 没有任何任务明细!!";
				throw new RuntimeException(info);
			}

			for (TxtDBTaskDetail txtDBTaskDetail : txtDBTaskDetails) {
				Integer taskType = txtDBTaskDetail.getTaskType();
				int seqNum = txtDBTaskDetail.getSeqNum();
				String taskName = txtDBTaskDetail.getTxtDBTask().getTaskname();

				if (taskType == null) {
					info = "任务名 = " + taskName + " 明细序号 = " + seqNum
							+ " 的明细任务类型为空 ";
					throw new RuntimeException(info);
				}
				//
				// 是域对应类型
				//
				if (taskType.intValue() == TxtDBTaskDetail.DB_TO_TXT_REGION_TYPE) {
					//
					// 获得执行DB导出到文本的数据源
					//
					TempResultSet tempResultSet = this.dataExportAction
							.getDBViewData(
									new Request(CommonVars.getCurrUser()),
									txtDBTaskDetail);
					if (tempResultSet == null) {
						continue;
					}
					DBToTxtRegion dbToTxtRegion = txtDBTaskDetail
							.getDbToTxtRegion();
					if (dbToTxtRegion == null) {
						continue;
					}
					boolean isExistCaption = dbToTxtRegion.getIsExistCaption() == null ? false
							: dbToTxtRegion.getIsExistCaption();
					int fileType = dbToTxtRegion.getFileType();
					String encoding = dbToTxtRegion.getEncoding();
					String filePath = dbToTxtRegion.getDestFilePath();
					if (filePath == null) {
						info = "任务名 = " + taskName + " 明细序号 = " + seqNum
								+ " 的导出文件路径为空 ";
						throw new RuntimeException(info);
					}
					if (fileType == FileType.TXT) {
						if (!filePath.toLowerCase().trim().endsWith("txt")) {
							filePath += ".txt";
						}
					} else if (fileType == FileType.XLS) {
						if (!filePath.toLowerCase().trim().endsWith("xls")) {
							filePath += ".xls";
						}
					}
					//
					// 以下两个条件在保存域的时候进行验证 ....
					//
					File file = new File(filePath);
					if (file.isDirectory()) {
						info = "任务名 = " + taskName + " 明细序号 = " + seqNum
								+ " 的导出文件路径不能是目录 ";
						throw new RuntimeException(info);
					}

					if (fileType == FileType.TXT) {
						//
						// 导出txt
						//						
						exportTxtFile(tempResultSet, isExistCaption, file,
								encoding);
					} else if (fileType == FileType.XLS) {
						//
						// 导出xls
						//						
						exportXlsFile(tempResultSet, isExistCaption, file,
								encoding);
					}

				} else if (taskType.intValue() == TxtDBTaskDetail.TXT_TO_DB_REGION_TYPE) {
					TxtToDBRegion txtToDBRegion = txtDBTaskDetail
							.getTxtToDBRegion();
					if (txtToDBRegion == null) {
						continue;
					}					
					int fileType = txtToDBRegion.getFileType();
					String filePath = txtToDBRegion.getSrcFilePath();
					if (filePath == null) {
						info = "任务名 = " + taskName + " 明细序号 = " + seqNum
								+ " 的导入文件路径为空 ";
						throw new RuntimeException(info);
					}
					//
					// 以下两个条件在保存域的时候进行验证 ....
					//
					File file = new File(filePath);
					if (!file.exists()) {
						info = "任务名 = " + taskName + " 明细序号 = " + seqNum
								+ " 的导入文件路径不存在 ";
						throw new RuntimeException(info);
					}
					if (file.isDirectory()) {
						info = "任务名 = " + taskName + " 明细序号 = " + seqNum
								+ " 的导入文件路径不能是目录 ";
						throw new RuntimeException(info);
					}								
					int importRowNumber = txtToDBRegion.getImportRowNumber() == null ? 1
							: txtToDBRegion.getImportRowNumber();				
					String encoding = txtToDBRegion.getEncoding();
					String[] columnNames = new String[0];
					List<List> list = new ArrayList<List>();
					if (fileType == FileType.XLS) {
						//
						// 导入txt
						//
						columnNames = FileReading.readExcelCaption(filePath,
								importRowNumber - 1, encoding);
						list = FileReading.readExcel(filePath, importRowNumber, encoding);
					} else {
						//
						// 导入xls
						//
						columnNames = FileReading.readTxtCaption(filePath,
								importRowNumber - 1, encoding);
						list = FileReading.readTxt(filePath, importRowNumber, encoding);
					}
					if(columnNames.length==0 || list.size()<=0 ){						 
						System.out.println("任务名 = " + taskName + "导入数据记录为空!!");
						continue;
					}
					TempResultSet tempResultSet = new TempResultSet();
					tempResultSet.setColumnNames(columnNames);
					tempResultSet.setRows(list);
					//
					// 执行文本导出到DB 
					//
					this.dataExportAction.executeTxtToDBTask(new Request(CommonVars.getCurrUser()),txtToDBRegion,
							tempResultSet);
					
				} else if (taskType.intValue() == TxtDBTaskDetail.JDBC_SQL_EVENT_TYPE) { // 是事件
					//
					// 执行事件
					//
					this.dataExportAction.txtDBTaskEventExecute(new Request(
							CommonVars.getCurrUser()), company,
							txtDBTaskDetail, seqNum);
				}
			}
		} finally {
			txtDBTask.setIsExecute(false);
			txtDBTask = this.dataExportAction.saveTxtDBTask(new Request(
					CommonVars.getCurrUser()), txtDBTask);
			tableModel.updateRow(txtDBTask);
		}
	}

	/** 导出xsl */
	private void exportXlsFile(TempResultSet tempResultSet,
			boolean isExistCaption, File file, String encoding) {
		WorkbookSettings wbs = new WorkbookSettings();
		List<List> rows = tempResultSet.getRows();
		String[] columnNames = tempResultSet.getColumnNames();
		WritableWorkbook workbook = null;
		//
		// excel is default ISO-8859-1 encoding
		// setEncoding("UTF-8"); utf-8 no support in excel
		//
		if (encoding == null || encoding.trim().equals("")) {
			wbs.setEncoding("ISO-8859-1");
		} else {
			wbs.setEncoding(encoding);
		}

		try {
			workbook = Workbook.createWorkbook(file, wbs);
			String sheetCaption = "";
			WritableSheet ws = workbook.createSheet(
					sheetCaption.equals("") ? "导出的数据" : sheetCaption, 0);

			WritableFont wfHead = new WritableFont(WritableFont.ARIAL, 12,
					WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
					Colour.WHITE);
			WritableCellFormat wcfFHead = new WritableCellFormat(wfHead);
			try {
				wcfFHead.setAlignment(Alignment.CENTRE);
				wcfFHead.setVerticalAlignment(VerticalAlignment.CENTRE);
				wcfFHead.setBackground(Colour.GRAY_25);
				wcfFHead.setBorder(Border.ALL, BorderLineStyle.THIN);
			} catch (WriteException e2) {
				e2.printStackTrace();
			}

			WritableCellFormat wcfFDetail = new WritableCellFormat();
			try {
				wcfFDetail.setBorder(Border.ALL, BorderLineStyle.THIN);
			} catch (WriteException e2) {
				e2.printStackTrace();
			}
			if (isExistCaption) {
				for (int i = 0; i < columnNames.length; i++) {
					String columnName = columnNames[i];
					Label lb = new Label(i, 0, columnName, wcfFHead);
					try {
						ws.addCell(lb);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			int tempValue = isExistCaption ? 1 : 0;
			// 
			// 保存记录
			//
			for (int i = 0; i < rows.size(); i++) {
				List row = rows.get(i);
				for (int j = 0; j < row.size(); j++) {
					Object value = row.get(j);
					if (value instanceof Integer || value instanceof Double) {
						if(value instanceof Integer){
							Integer intValue = (value == null) ? 0
									: (Integer) value;
							jxl.write.Number labelN = new jxl.write.Number(j, i
									+ tempValue, intValue, wcfFDetail);
							try {
								ws.addCell(labelN);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}else{
							Double intValue = (value == null) ? 0
									: (Double) value;
							jxl.write.Number labelN = new jxl.write.Number(j, i
									+ tempValue, intValue, wcfFDetail);
							try {
								ws.addCell(labelN);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						
					} else if (value instanceof Boolean) {
						Boolean intValue = (value == null) ? false
								: (Boolean) value;
						jxl.write.Boolean labelN = new jxl.write.Boolean(j, i
								+ tempValue, intValue, wcfFDetail);
						try {
							ws.addCell(labelN);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						String str = "";
						if (value instanceof Date) {
							SimpleDateFormat bartDateFormat = new SimpleDateFormat(
									"yyyy-MM-dd");
							str = bartDateFormat.format((Date) value);
						} else {
							if (value != null) {
								str = value.toString();
							}
						}
						jxl.write.Label labelN = new jxl.write.Label(j, i
								+ tempValue, str, wcfFDetail);
						try {
							ws.addCell(labelN);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				workbook.write();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				workbook.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	/** 导出text */
	private void exportTxtFile(TempResultSet tempResultSet,
			boolean isExistCaption, File file, String encoding) {
		OutputStreamWriter out = null;
		BufferedWriter bw = null;
		try {
			if (encoding != null && !encoding.trim().equals("")) {
				out = new OutputStreamWriter(new FileOutputStream(file),
						encoding);
			} else {
				out = new OutputStreamWriter(new FileOutputStream(file));
			}
			bw = new BufferedWriter(out);
			String[] columnNames = tempResultSet.getColumnNames();
			if (isExistCaption) {
				String captionRow = "";
				for (int i = 0; i < columnNames.length; i++) {
					String columnName = columnNames[i];
					if (i == 0) {
						captionRow += columnName;
					} else {
						captionRow += String.valueOf((char) KeyEvent.VK_TAB)
								+ columnName;
					}
				}
				bw.write(captionRow);
			}
			List<List> rows = tempResultSet.getRows();
			for (int rowCount = 0; rowCount < rows.size(); rowCount++) {
				List row = rows.get(rowCount);
				String rowStr = "";
				for (int i = 0; i < row.size(); i++) {
					Object obj = row.get(i);
					String temp = "";
					if (obj instanceof Date) {
						SimpleDateFormat bartDateFormat = new SimpleDateFormat(
								"yyyy-MM-dd");
						temp = bartDateFormat.format((Date) obj);
					} else {
						if (obj != null) {
							temp = obj.toString();
						}
					}

					if (i == 0) {
						rowStr += temp;
					} else {
						rowStr += String.valueOf((char) KeyEvent.VK_TAB) + temp;
					}
				}
				if (isExistCaption == false && rowCount == 0) {
					bw.write(rowStr);
				} else {
					bw.write(LINE_SEPARATOR + rowStr);
				}
			}
			bw.flush();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			try {
				if (bw != null) {
					bw.close();
				}
			} catch (Exception ex) {

			}
			try {
				if (out != null) {
					out.close();
				}
			} catch (Exception ex) {

			}
		}
	}
}
