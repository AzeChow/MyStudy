package com.bestway.common.client.dataimport;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.bcus.custombase.entity.parametercode.Gbtobig;
import com.bestway.bcus.dataimport.action.DataImportAction;
import com.bestway.bcus.dataimport.entity.DBTaskEx;
import com.bestway.bcus.dataimport.entity.DBTaskSel;
import com.bestway.bcus.dataimport.entity.TxtFormat;
import com.bestway.bcus.dataimport.entity.TxtTaskEx;
import com.bestway.bcus.dataimport.entity.TxtTaskSel;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.client.fpt.SendMail;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author lin
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
@SuppressWarnings("unchecked")
public class DgDataTools extends JDialogBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTableListModel tableModel = null; // txt

	private JTableListModel tableModel1 = null; // log

	private JTableListModel tableModel2 = null; // db

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JPanel jPanel2 = null;

	private JButton jButton = null;

	private JButton jButton2 = null;

	private JButton jButton3 = null;

	private JButton jButton4 = null;

	private JTabbedPane jTabbedPane = null;

	private JPanel jPanel3 = null;

	private JPanel jPanel4 = null;

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;

	private DataImportAction dataImportAction = null;

	private JPanel jPanel5 = null;

	private JButton jButton5 = null;

	private String importType = null;

	private JButton jButton6 = null;

	private JPanel jPanel6 = null;

	private Hashtable selectedValues = null; // 存放字段排放次序

	private File file1 = null;

	private int txtRow = 0;

	private int noInsert = 0;

	private int insert = 0;

	private int overInsert = 0;

	private int changeInsert = 0;

	private JTable jTable1 = null;

	private JScrollPane jScrollPane1 = null;

	private String beginDate = null;

	private String endDate = null;

	private JTable jTable2 = null;

	private JScrollPane jScrollPane2 = null;

	public String impType = null;

	private TableModel dm = null;


	private Hashtable gbHash = null;

	public JLabel jLabel1 = null;


	private Hashtable hs = null;
	
	private boolean isBegin = false;

	/**
	 * 
	 */
	public DgDataTools() {
		super();
		dataImportAction = (DataImportAction) CommonVars
				.getApplicationContext().getBean("dataImportAction");
		initialize();

	}

	private void initData() {
		List txtList = dataImportAction.findTxtTaskEx(new Request(CommonVars
				.getCurrUser()));
		if (txtList != null && !txtList.isEmpty()) {
			initTable(txtList);
		} else {
			initTable(new Vector());
		}
		List dbList = dataImportAction.findDBTaskEx(new Request(CommonVars
				.getCurrUser()));
		if (dbList != null && !dbList.isEmpty()) {
			initTable2(dbList);
		} else {
			initTable2(new Vector());
		}
		List logList = dataImportAction.findTxtLog(new Request(CommonVars
				.getCurrUser()));
		if (logList != null && !logList.isEmpty()) {
			initTable1(logList);
		} else {
			initTable1(new Vector());
		}

	}

	private void initTable2(List dataSource) {
		tableModel2 = DgDataToolsLogic.initTable2(dataSource, jTable2);
		jTable2.getColumnModel().getColumn(2).setCellRenderer(
				new DefaultTableCellRenderer() {
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue1(value));
						return this;
					}

					private String castValue1(Object value) {
						String returnValue = "";
						if (String.valueOf(value).trim().equals("")) {
							return "";
						}
						if (value.equals("0")) {
							returnValue = "手动";
						} else if (value.equals("1")) {
							returnValue = "每日";
						} else if (value.equals("2")) {
							returnValue = "每周";
							DgDataTools.this.setImportType("2");
						} else if (value.equals("3")) {
							returnValue = "每月";
							DgDataTools.this.setImportType("3");
						}
						return returnValue;
					}
				});
		DgDataToolsLogic.getColumn(jTable2);
		jTable2.getColumnModel().getColumn(4).setCellRenderer(
				new DefaultTableCellRenderer() {
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue1(value));
						return this;
					}

					private String castValue1(Object value) {

						String returnValue = "";
						if (DgDataTools.this.getImportType() == null) {
							return returnValue;
						}
						if (String.valueOf(value).trim().equals("")
								|| DgDataTools.this.getImportType().equals("")) {
							return "";
						}
						if (DgDataTools.this.getImportType().equals("2")) {
							if (value.equals("0")) {
								returnValue = "星期天";
							} else if (value.equals("1")) {
								returnValue = "星期一";
							} else if (value.equals("2")) {
								returnValue = "星期二";
							} else if (value.equals("3")) {
								returnValue = "星期三";
							} else if (value.equals("4")) {
								returnValue = "星期四";
							} else if (value.equals("5")) {
								returnValue = "星期五";
							} else if (value.equals("6")) {
								returnValue = "星期六";
							}
						} else if (DgDataTools.this.getImportType().equals("3")) {
							returnValue = value + "日";
						}
						return returnValue;
					}
				});
	}

	private void initTable(List dataSource) {
		tableModel = DgDataToolsLogic.initTable2(dataSource, jTable);
		jTable.getColumnModel().getColumn(2).setCellRenderer(
				new DefaultTableCellRenderer() {
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue1(value));
						return this;
					}

					private String castValue1(Object value) {
						String returnValue = "";
						if (String.valueOf(value).trim().equals("")) {
							return "";
						}
						if (value.equals("0")) {
							returnValue = "手动";
						} else if (value.equals("1")) {
							returnValue = "每日";
						} else if (value.equals("2")) {
							returnValue = "每周";
							DgDataTools.this.setImportType("2");
						} else if (value.equals("3")) {
							returnValue = "每月";
							DgDataTools.this.setImportType("3");
						}
						return returnValue;
					}
				});
		DgDataToolsLogic.getColumn(jTable);
		jTable.getColumnModel().getColumn(4).setCellRenderer(
				new DefaultTableCellRenderer() {
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue1(value));
						return this;
					}

					private String castValue1(Object value) {
						String returnValue = "";
						if (String.valueOf(value).trim().equals("")
								|| DgDataTools.this.getImportType().equals("")) {
							return "";
						}
						if (DgDataTools.this.getImportType().equals("2")) {
							if (value.equals("0")) {
								returnValue = "星期天";
							} else if (value.equals("1")) {
								returnValue = "星期一";
							} else if (value.equals("2")) {
								returnValue = "星期二";
							} else if (value.equals("3")) {
								returnValue = "星期三";
							} else if (value.equals("4")) {
								returnValue = "星期四";
							} else if (value.equals("5")) {
								returnValue = "星期五";
							} else if (value.equals("6")) {
								returnValue = "星期六";
							}
						} else if (DgDataTools.this.getImportType().equals("3")) {
							returnValue = value + "日";
						}
						return returnValue;
					}
				});
	}

	private void initTable1(List dataSource) {
		tableModel1 = DgDataToolsLogic.initTable1(dataSource, jTable1);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(getJPanel());
		this.setTitle("数据运行导入");
		this.setSize(690, 454);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {
				if (DgDataTools.this.getImpType().equals("db")) {
					DgDataTools.this.jTabbedPane.setSelectedIndex(1);
				} else {
					DgDataTools.this.jTabbedPane.setSelectedIndex(0);
				}
				initData();
				setState();
			}
		});
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.add(getJPanel1(), java.awt.BorderLayout.CENTER);
			jPanel.add(getJPanel2(), java.awt.BorderLayout.SOUTH);
			jPanel.add(getJPanel5(), java.awt.BorderLayout.NORTH);
		}
		return jPanel;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.add(getJTabbedPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			FlowLayout flowLayout1 = new FlowLayout();
			jPanel2 = new JPanel();
			jPanel2.setLayout(flowLayout1);
			flowLayout1.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel2.setBackground(java.awt.Color.white);
			jPanel2
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jPanel2.add(getJButton5(), null);
			jPanel2.add(getJButton6(), null);
			jPanel2.add(getJButton(), null);
			jPanel2.add(getJButton2(), null);
			jPanel2.add(getJButton3(), null);
			jPanel2.add(getJButton4(), null);
		}
		return jPanel2;
	}

	// 文本保存数据
	private void saveData(TxtTaskSel txtTaskSel, File file) {
		boolean ischange = true;
		if (txtTaskSel.getTxttable().getGbflag().equals("0")) {
			ischange = false;
		}
		BufferedReader in;
		String[] strs = null;
		String inputSelect = txtTaskSel.getTxttask().getInputSelect();// 导入选项
		List txtFormatList = dataImportAction.findTxtFormat(new Request(
				CommonVars.getCurrUser()), txtTaskSel.getTxttable());
		selectedValues = DgDataToolsLogic.getHash(txtFormatList);
		String className = txtTaskSel.getTxttable().getClassList()
				.getClassPath().trim();
		if (hs.get(className) != null) {
			className = (String) hs.get(className);
		}
		noInsert = 0;
		insert = 0;
		overInsert = 0;
		changeInsert = 0;
		int row = 0;
		try {
			in = new BufferedReader(new FileReader(file));
			String s = new String();
			try {
				List valuesList = new ArrayList();
				while ((s = in.readLine()) != null) {
					row++;
					if (row == 1
							&& CommonVars.getIstitleRow().equals(
									new Boolean(true))) {
						continue;
					}
					if (s.trim().equals("")) {
						continue;
					}
					if (ischange) {
						s = changeStr(s);
					}
					strs = s.split(String.valueOf((char) 9));
					if (inputSelect.equals("0")) { // 不处理
						if (DgDataToolsLogic.isRepeat(className, txtFormatList,
								strs, "0", selectedValues).equals("1")) {
							noInsert++;
							continue;
						} else {
							Object obj = DgDataToolsLogic.save(className,
									txtFormatList, strs, selectedValues);
							if (obj != null) {
								valuesList.add(obj);
							}
							insert++;
						}
					} else if (inputSelect.equals("2")) { // 更新
						if (DgDataToolsLogic.isRepeat(className, txtFormatList,
								strs, "2", selectedValues).equals("1")) {// 重复即更新
							changeInsert++;
						} else {
							Object obj = DgDataToolsLogic.save(className,
									txtFormatList,// 没查到即直接插入
									strs, selectedValues);
							if (obj != null) {
								valuesList.add(obj);
							}
							insert++;
						}
					} else if (inputSelect.equals("3")) { // 直接保存
						Object obj = DgDataToolsLogic.save(className,
								txtFormatList, strs, selectedValues);
						if (obj != null) {
							valuesList.add(obj);
						}
						insert++;
					}
				}
				// 保存文本
				dataImportAction.saveTxtList(new Request(CommonVars
						.getCurrUser()), valuesList);
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				in.close();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

	}

	// 文本导入立即运行.....................................................................
	class TxtDataRunnable extends Thread {
		public void run() {
			try {
				TxtTaskEx txtTaskEx = (TxtTaskEx) tableModel.getCurrentRow();// 得到任务
				List selList = dataImportAction.findTxtTaskSel(new Request(
						CommonVars.getCurrUser()), txtTaskEx);// 任务明细
				if (txtTaskEx.getIsParentTask() == null
						|| (!txtTaskEx.getIsParentTask())) {
					for (int i = 0; i < selList.size(); i++) {
						TxtTaskSel txtTaskSel = (TxtTaskSel) selList.get(i);
						if (txtTaskSel.getExecuSql() != null // 执行事件
						) {
							try {
								String message = dataImportAction.execuSql(
										new Request(CommonVars.getCurrUser()),
										txtTaskSel.getExecuSql());
								if (message != null && message.contains("执行失败")) {
									CommonProgress.closeProgressDialog();
									JOptionPane.showMessageDialog(
											DgDataTools.this, "执行事件："
													+ txtTaskSel.getExecuSql()
															.getName() + "\n"
													+ message, "提示", 2);
									return;
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						} else {
							file1 = getFile();
							beginDate = CommonVars.nowToDate();
							CommonProgress.showProgressDialog(DgDataTools.this);
							CommonProgress.setMessage("系统正在读取文件并检查资料，请稍后...");
							List list = parseTxtFile(txtTaskSel, file1);
							CommonProgress.closeProgressDialog();
							if (list != null && !list.isEmpty()) {
								endDate = CommonVars.nowToDate();
								DgDataToolsLogic.saveLog(txtTaskSel
										.getTxttask().getTaskname(), "导入失败",
										txtTaskSel.getTxttable().getTaskname(),
										beginDate, endDate, tableModel1);// 保存日志
								DgTxtErrorHint dg = new DgTxtErrorHint();
								dg.setTask(txtTaskSel.getTxttask()
										.getTaskname());
								dg.setMubiao(txtTaskSel.getTxttable()
										.getClassList().getName());
								dg.setTxtFile(file1.getPath());
								dg.setList(list);
								dg.setVisible(true);
							} else {
								DgDataShow dg = new DgDataShow();
								dg.setDm(dm);
								dg.setVisible(true);
								if (dg.isOk()) {
									CommonProgress
											.showProgressDialog(DgDataTools.this);
									CommonProgress
											.setMessage("系统正在导入文件资料，请稍后...");
									saveData(txtTaskSel, file1);
									CommonProgress.closeProgressDialog();
									endDate = CommonVars.nowToDate();
									DgDataToolsLogic.saveLog(txtTaskSel
											.getTxttask().getTaskname(),
											"导入成功", txtTaskSel.getTxttable()
													.getTaskname(), beginDate,
											endDate, tableModel1);// 保存日志
									String ss = "";
									if (CommonVars.getIstitleRow().equals(
											new Boolean(true))) {
										ss = "  第一行为标题行";
									}
									JOptionPane
											.showMessageDialog(
													DgDataTools.this,
													"导入成功!\n导入任务名称: "
															+ txtTaskSel
																	.getTxttask()
																	.getTaskname()
															+ "\n导入目标表:     "
															+ txtTaskSel
																	.getTxttable()
																	.getClassList()
																	.getName()
															+ "\n文本行总数:     "
															+ txtRow
															+ "  (行)"
															+ ss
															+ "\n不处理重复行数:   "
															+ noInsert
															+ "  (行)\n覆盖行数:   "
															+ overInsert
															+ "  (行)\n更新行数:    "
															+ changeInsert
															+ "  (行)\n新插入行数:   "
															+ insert + "  (行)",
													"提示", 2);
								}
							}
						}
					}
				}

			}

			finally {
				CommonProgress.closeProgressDialog();
			}
		}
	}

	class DBDataRunnable extends Thread {// DB立即运行
		public void run() {
			if(isBegin){
				return;
			}
			//
			// 用于标识这个对话框的ID
			//
			UUID uuid = UUID.randomUUID();
			final String flag = uuid.toString();
			try {
				isBegin = true;				
				long beginTime = System.currentTimeMillis();
				CommonProgress.showProgressDialog(flag, DgDataTools.this);
				CommonProgress.setMessage(flag, "系统正在导入资料，请稍后...");

				List countList = new ArrayList();
				DBTaskEx dbTaskEx = (DBTaskEx) tableModel2.getCurrentRow();// 得到任务
				List selList = dataImportAction.findDBTaskSel(new Request(
						CommonVars.getCurrUser()), dbTaskEx);// 任务明细
				int x = 0;
				int totalTaskCount = selList.size();
				System.out.println("===============================开始执行导入任务=================================");
				if (dbTaskEx.getIsParentTask() == null // 一般任务
						|| (!dbTaskEx.getIsParentTask())) {
					System.out.println("*****一般任务*****");
					for (int i = 0; i < selList.size(); i++) {
						DBTaskSel dbTaskSel = (DBTaskSel) selList.get(i);// 任务明细
						try {
							List list = CustomBaseList.getInstance()
									.getGbtobigs();
							if (dbTaskSel.getExecuSql() != null // 执行事件
									&& dbTaskSel.getDbFormat() == null) {
								System.out.println("------任务明细共："
										+ totalTaskCount + "  正在执行：" + (i + 1)
										+ " 执行事件："
										+ dbTaskSel.getExecuSql().getName());
								String message = dataImportAction.execuSql(
										new Request(CommonVars.getCurrUser()),
										dbTaskSel.getExecuSql());
								System.out.println("结束 " + dbTaskSel.getExecuSql().getName());
								if (message != null && message.contains("执行失败")) {
									CommonProgress.closeProgressDialog(flag);
									JOptionPane.showMessageDialog(
											DgDataTools.this, "执行事件："
													+ dbTaskSel.getExecuSql()
															.getName() + "\n"
													+ message, "提示", 2);
									return;
								}
							} else { // 执行域对应
								System.out.println("------任务明细共："
										+ totalTaskCount
										+ "  正在执行："
										+ (i + 1)
										+ " 执行域："
										+ dbTaskSel.getDbFormat()
												.getRegionName());
								countList = dataImportAction.intoData(
										new Request(CommonVars.getCurrUser()),
										dbTaskSel.getDbFormat(), CommonVars
												.getCurrUser().getCompany(),
										dbTaskEx.getDeal(), dbTaskEx
												.getInputSelect(), list, hs);
								System.out.println("结束 " + dbTaskSel.getDbFormat().getRegionName());
							}
							x = 1;
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						
					}
				} else { // 为父任务
					System.out.println("*****父任务*****");
					for (int i = 0; i < selList.size(); i++) {
						DBTaskSel subDBTaskEx = (DBTaskSel) selList.get(i);
						if (subDBTaskEx.getSubDBTaskEx() == null) {
							continue;
						}
						List lsSubTask = dataImportAction.findDBTaskSel(
								new Request(CommonVars.getCurrUser()),
								subDBTaskEx.getSubDBTaskEx());// 子任务明细
						int zTaskCount = lsSubTask.size();
						for (int j = 0; j < lsSubTask.size(); j++) {
							DBTaskSel dbTaskSel = (DBTaskSel) lsSubTask.get(j);// 子任务明细
							try {
								List list = CustomBaseList.getInstance()
										.getGbtobigs();
								if (dbTaskSel.getExecuSql() != null
										&& dbTaskSel.getDbFormat() == null) {
									System.out
											.println("------父任务共："
													+ totalTaskCount
													+ "  正在执行："
													+ (i + 1)
													+ "子任务共："
													+ zTaskCount
													+ "正在执行："
													+ (j + 1)
													+ " 执行事件："
													+ dbTaskSel.getExecuSql()
															.getName());

									String message = dataImportAction.execuSql(
											new Request(CommonVars
													.getCurrUser()), dbTaskSel
													.getExecuSql());
									if (message != null
											&& message.contains("执行失败")) {
										CommonProgress
												.closeProgressDialog(flag);
										JOptionPane.showMessageDialog(
												DgDataTools.this, "执行事件："
														+ dbTaskSel
																.getExecuSql()
																.getName()
														+ "\n" + message, "提示",
												2);
										return;
									}
								} else {
									System.out.println("------父任务共："
											+ totalTaskCount
											+ "  正在执行："
											+ (i + 1)
											+ "子任务共："
											+ zTaskCount
											+ "正在执行："
											+ (j + 1)
											+ " 执行域："
											+ dbTaskSel.getDbFormat()
													.getRegionName());
									countList = dataImportAction
											.intoData(new Request(CommonVars
													.getCurrUser()), dbTaskSel
													.getDbFormat(),
													CommonVars.getCurrUser()
															.getCompany(),
													dbTaskEx.getDeal(),
													dbTaskEx.getInputSelect(),
													list, hs);
								}
								x = 1;
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
				System.out.println("===============================结束执行导入任务=================================");
				
				int totalCount = 0;
				int updateCount = 0;
				int newInsertCount = 0;
				if (countList != null && countList.size() > 0) {
					totalCount = Integer
							.parseInt(countList.get(0) == null ? "0"
									: countList.get(0).toString());
					updateCount = Integer
							.parseInt(countList.get(1) == null ? "0"
									: countList.get(1).toString());
					newInsertCount = Integer
							.parseInt(countList.get(2) == null ? "0"
									: countList.get(2).toString());
				}
				if (x == 1) {
					String info = " 导入完成,共用时:"
							+ (System.currentTimeMillis() - beginTime)
							+ " 毫秒 \n" + "总记录数：" + totalCount + "\n修改数："
							+ updateCount + "\n新增数：" + newInsertCount;
					JOptionPane.showMessageDialog(DgDataTools.this, info, "提示",
							2);
				}else{//导入失败,给当前用户发送邮件
					String message = "数据导入接口,任务：["+dbTaskEx.getTaskname()+"] 执行异常...";
					SendMail.getInstance().sendEmail("接口数据导入", message);
				}
				
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new RuntimeException(ex);
			} finally{
				jButton.setEnabled(true);
				isBegin = false;
				CommonProgress.closeProgressDialog(flag);
			}

		}
	}

	class RunRow extends Thread {
		public void run() {
			int loopTimes = 0;
			while (1 == 1) {
				if (loopTimes == 150) {
					loopTimes = 0;
					DgDataTools.this.jLabel1.setText("数据运行导入：数据已导入到第 "
							+ CommonUtils.getInputRow() + "行");
					DgDataTools.this.jPanel5.repaint();
					DgDataTools.this.jPanel5.validate();
				} else {
					loopTimes++;
				}
			}
		}
	}

	// 读取并检查数据
	private List parseTxtFile(TxtTaskSel txtTaskSel, File file) {
		boolean ischange = true;
		if (txtTaskSel.getTxttable().getGbflag().equals("2")) {
			injTofHsTable();
		} else if (txtTaskSel.getTxttable().getGbflag().equals("1")) {
			infTojHsTable();
		} else {
			ischange = false;
		}
		BufferedReader in;
		ArrayList list = new ArrayList();
		String[] strs = null;
		txtRow = 0;
		int row = 0;
		Vector columns = new Vector(); // 字段
		Vector vector = new Vector(); // 字段所有的值
		List txtFormatList = dataImportAction.findTxtFormat(new Request(
				CommonVars.getCurrUser()), txtTaskSel.getTxttable());
		selectedValues = DgDataToolsLogic.getHash(txtFormatList);
		try {
			in = new BufferedReader(new FileReader(file));
			String s = new String();
			try {
				while ((s = in.readLine()) != null) {
					Vector array = new Vector(); // 每个字段对应的值
					row++;
					if (row == 1
							&& CommonVars.getIstitleRow().equals(
									new Boolean(true))) {
						continue;
					}
					if (s.trim().equals("")) {
						continue;
					}
					if (ischange) {
						s = changeStr(s);
					}
					strs = s.split(String.valueOf((char) 9));
					if (columns.size() == 0) { // 初始化talbe列
						for (int j = 0; j < txtFormatList.size(); j++) {
							TxtFormat txtFormat = (TxtFormat) txtFormatList
									.get(j);
							columns.add(txtFormat.getName());
						}
					}
					for (int j = 0; j < txtFormatList.size(); j++) {
						//TxtFormat txtFormat = (TxtFormat) txtFormatList.get(j);
						String value = DgDataToolsLogic.getFileColumnValue(
								strs, j + 1, selectedValues);
						array.add(value);
						/*
						 * if (txtFormat.getFieldtype().equals("Double") ||
						 * txtFormat.getFieldtype().equals("double") ||
						 * txtFormat.getFieldtype().equals("int")) { // 统计Double
						 * if (sumHash.get(txtFormat.getFieldname()) != null) {
						 * sumHash.put(txtFormat.getFieldname(), Double
						 * .valueOf(((Double) sumHash
						 * .get(txtFormat.getFieldname())) .doubleValue() +
						 * cgDou(value).doubleValue())); } else {
						 * sumHash.put(txtFormat.getFieldname(), cgDou(value));
						 * } }
						 */
						/*
						 * if (DgDataToolsLogic.checkIsNull(txtFormat, temp,
						 * row, value) != null) { list.add(temp); // 检查是否为空 }
						 * BillTemp temp1 = new BillTemp(); if
						 * (DgDataToolsLogic.checkType(txtFormat, temp1, row,
						 * value) != null) { list.add(temp1); // 检查字段类型 }
						 * BillTemp temp2 = new BillTemp(); int valueLenth =
						 * value.getBytes().length; if
						 * (DgDataToolsLogic.checkLenth(txtFormat, temp2, row,
						 * valueLenth) != null) { list.add(temp2); // 检查字段长度 }
						 */
					}
					txtRow = row;
					vector.add(array);
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		/*
		 * Vector array = new Vector(); for (int j = 0; j <
		 * txtFormatList.size(); j++) { // 增加尾列为和 TxtFormat txtFormat =
		 * (TxtFormat) txtFormatList.get(j); if
		 * (sumHash.get(txtFormat.getFieldname()) != null) {
		 * array.add(sumHash.get(txtFormat.getFieldname())); } else {
		 * array.add(null); } } vector.add(array);
		 */
		dm = new DefaultTableModel(vector, columns);
		return list;
	}

	private void injTofHsTable() {
		if (gbHash == null) {
			gbHash = new Hashtable();
		}
		List list = CustomBaseList.getInstance().getGbtobigs();
		for (int i = 0; i < list.size(); i++) {
			Gbtobig gb = (Gbtobig) list.get(i);
			gbHash.put(gb.getName(), gb.getBigname());
		}
	}

	private void infTojHsTable() {
		if (gbHash == null) {
			gbHash = new Hashtable();
		}
		List list = CustomBaseList.getInstance().getGbtobigs();
		for (int i = 0; i < list.size(); i++) {
			Gbtobig gb = (Gbtobig) list.get(i);
			gbHash.put(gb.getBigname(), gb.getName());
		}
	}

//	private Double cgDou(String str) {
//		try {
//			return Double.valueOf(str);
//		} catch (Exception e) {
//			return new Double(0);
//		}
//	}

	private String changeStr(String s) { // 简转繁
		String yy = "";
		char[] xxx = s.toCharArray();
		for (int i = 0; i < xxx.length; i++) {
			String z = String.valueOf(xxx[i]);
			if (String.valueOf(xxx[i]).getBytes().length == 2) {
				if (gbHash.get(String.valueOf(xxx[i])) != null) {
					z = (String) gbHash.get(String.valueOf(xxx[i]));
				}
			}
			yy = yy + z;
		}
		return yy;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("立即运行");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(isBegin){
						return;
					}
					if (DgDataTools.this.jTabbedPane.getSelectedIndex() == 0) {
						if (tableModel.getCurrentRow() == null) {
							JOptionPane.showMessageDialog(DgDataTools.this,
									"请选择你要立即运行的任务", "确认", 2);
							return;
						}
						new TxtDataRunnable().start(); // 文本导入
					} else {
						if (tableModel2.getCurrentRow() == null) {
							JOptionPane.showMessageDialog(DgDataTools.this,
									"请选择你要立即运行的任务", "确认", 2);
							return;
						}
						jButton.setEnabled(false);
						new DBDataRunnable().start(); // DB导入
					}
				}
			});
		}
		return jButton;
	}

	// 调出文件选择框
	private File getFile() {
		File txtFile = null;
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.removeChoosableFileFilter(fileChooser.getFileFilter());

		fileChooser.setFileFilter(new FileFilter() {
			public boolean accept(File f) {
				String suffix = getSuffix(f);
				if (f.isDirectory() == true) {
					return true;
				}
				if (suffix != null) {
					if (suffix.toLowerCase().equals("txt")) {
						return true;
					} else {
						return false;
					}
				} else {
					return false;
				}
			}

			public String getDescription() {
				return "*.txt";
			}

			private String getSuffix(File f) {
				String s = f.getPath(), suffix = null;
				int i = s.lastIndexOf('.');
				if (i > 0 && i < s.length() - 1)
					suffix = s.substring(i + 1).toLowerCase();
				return suffix;
			}
		});
		int state = fileChooser.showOpenDialog(DgDataTools.this);
		if (state == JFileChooser.APPROVE_OPTION) {
			txtFile = fileChooser.getSelectedFile();
		}
		return txtFile;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("查看明细");
			jButton2.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (DgDataTools.this.jTabbedPane.getSelectedIndex() == 0) {
						if (!isSeleRow(0)) {
							return;
						}
						DgTxtTaskExDetail dg = new DgTxtTaskExDetail();
						dg.setTableModel(tableModel);
						dg.setTitleName("导入任务->"
								+ ((TxtTaskEx) tableModel.getCurrentRow())
										.getTaskname());
						dg.setVisible(true);
					} else {
						if (!isSeleRow(1)) {
							return;
						}

					}
				}
			});

		}
		return jButton2;
	}

	private boolean isSeleRow(int table) {
		if (table == 0) {
			if (tableModel.getCurrentRow() == null) {
				JOptionPane.showMessageDialog(DgDataTools.this, "请选择你要任务资料",
						"确认", 1);
				return false;
			}
		} else if (table == 1) {
			if (tableModel2.getCurrentRow() == null) {
				JOptionPane.showMessageDialog(DgDataTools.this, "请选择你要任务资料",
						"确认", 1);
				return false;
			}
		}
		return true;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setText("清空日志");
			jButton3.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					dataImportAction.deleteTxtLog(new Request(CommonVars
							.getCurrUser()));
					tableModel1.getList().clear();
					initTable1(new Vector());
				}
			});

		}
		return jButton3;
	}

	/**
	 * This method initializes jButton4
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new JButton();
			jButton4.setText("退  出");
			jButton4.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					DgDataTools.this.dispose();
				}

			});
		}
		return jButton4;
	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setBackground(java.awt.Color.white);
			jTabbedPane.addTab("文本任务导入", null, getJPanel4(), null);
			jTabbedPane.addTab("DB任务导入", null, getJPanel3(), null);
			jTabbedPane.addTab("任务运行日志", null, getJPanel6(), null);
			jTabbedPane
					.addChangeListener(new javax.swing.event.ChangeListener() {

						public void stateChanged(javax.swing.event.ChangeEvent e) {

							setState();

						}
					});

		}
		return jTabbedPane;
	}

	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setLayout(new BorderLayout());
			jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(
					null, "",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION,
					new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12),
					new java.awt.Color(51, 51, 51)));
			jPanel3.add(getJScrollPane2(), java.awt.BorderLayout.CENTER);
		}
		return jPanel3;
	}

	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			TitledBorder titledBorder1 = javax.swing.BorderFactory
					.createTitledBorder(
							null,
							"文本任务导入",
							javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
							javax.swing.border.TitledBorder.DEFAULT_POSITION,
							null, null);
			jPanel4 = new JPanel();
			jPanel4.setLayout(new BorderLayout());
			jPanel4.setBorder(titledBorder1);
			titledBorder1.setTitle("");
			jPanel4.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel4;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
			jTable.setShowHorizontalLines(false);
			jTable.setShowVerticalLines(false);
			jTable.setIntercellSpacing(new java.awt.Dimension(0, 0));
		}
		jTable.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {

					public void valueChanged(ListSelectionEvent arg0) {
						if (tableModel == null) {
							return;
						}
						if (tableModel.getCurrentRow() == null) {
							return;
						}
						setState();
					}
				});
		return jTable;
	}

	/**
	 * 
	 * This method initializes jPanel5
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel5() {
		if (jPanel5 == null) {
			javax.swing.JLabel jLabel2 = new JLabel();

			jLabel1 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();

			jPanel5 = new JPanel();
			jPanel5.setLayout(new BorderLayout());
			jLabel.setText("");
			jLabel
					.setIcon(new ImageIcon(
							getClass()
									.getResource(
											"/com/bestway/bcus/client/resources/images/titlepoint.jpg")));
			jLabel1.setText("数据运行导入");
			jLabel1
					.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 20));
			jLabel1.setForeground(new java.awt.Color(255, 153, 0));
			jLabel2.setText("");
			jLabel2.setIcon(new ImageIcon(getClass().getResource(
					"/com/bestway/bcus/client/resources/images/titlepic.jpg")));
			jPanel5
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jPanel5.setBackground(java.awt.Color.white);
			jPanel5.add(jLabel, java.awt.BorderLayout.WEST);
			jPanel5.add(jLabel1, java.awt.BorderLayout.CENTER);
			jPanel5.add(jLabel2, java.awt.BorderLayout.EAST);
		}
		return jPanel5;
	}

	/**
	 * 
	 * This method initializes jButton5
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJButton5() {
		if (jButton5 == null) {
			jButton5 = new JButton();
			jButton5.setText("开始执行");
			jButton5.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (DgDataTools.this.jTabbedPane.getSelectedIndex() == 0) { // 文本导入
						if (!isSeleRow(0)) {
							return;
						}
						TxtTaskEx txtTaskEx = (TxtTaskEx) tableModel
								.getCurrentRow();
						txtTaskEx.setExecuteState(Integer.valueOf(1));
						txtTaskEx = dataImportAction.saveTxtTaskEx(new Request(
								CommonVars.getCurrUser()), txtTaskEx);
						tableModel.updateRow(txtTaskEx);
					} else { // 数据导入
						if (!isSeleRow(1)) {
							return;
						}
						DBTaskEx dbTaskEx = (DBTaskEx) tableModel2
								.getCurrentRow();
						dbTaskEx.setExecuteState(Integer.valueOf(1));
						dbTaskEx = dataImportAction.saveDBTaskEx(new Request(
								CommonVars.getCurrUser()), dbTaskEx);
						tableModel2.updateRow(dbTaskEx);
					}
					setState();
				}
			});

		}
		return jButton5;
	}

	/**
	 * 
	 * This method initializes jButton6
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJButton6() {
		if (jButton6 == null) {
			jButton6 = new JButton();
			jButton6.setText("取消执行");
			jButton6.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (DgDataTools.this.jTabbedPane.getSelectedIndex() == 0) { // 文本导入
						if (!isSeleRow(0)) {
							return;
						}
						TxtTaskEx txtTaskEx = (TxtTaskEx) tableModel
								.getCurrentRow();
						txtTaskEx.setExecuteState(Integer.valueOf(0));
						txtTaskEx = dataImportAction.saveTxtTaskEx(new Request(
								CommonVars.getCurrUser()), txtTaskEx);
						tableModel.updateRow(txtTaskEx);
					} else {
						if (!isSeleRow(1)) {
							return;
						}
						DBTaskEx dbTaskEx = (DBTaskEx) tableModel2
								.getCurrentRow();
						dbTaskEx.setExecuteState(Integer.valueOf(0));
						dbTaskEx = dataImportAction.saveDBTaskEx(new Request(
								CommonVars.getCurrUser()), dbTaskEx);
						tableModel2.updateRow(dbTaskEx);
					}
					setState();
				}
			});

		}
		return jButton6;
	}

	private void setState() {
		jButton5.setEnabled(!getexcute() && noempty()
				&& jTabbedPane.getSelectedIndex() != 2);
		jButton6.setEnabled(getexcute() && noempty()
				&& jTabbedPane.getSelectedIndex() != 2);
		jButton2.setEnabled(noempty() && jTabbedPane.getSelectedIndex() != 2);
		jButton.setEnabled(noempty() && jTabbedPane.getSelectedIndex() != 2);
		jButton3.setEnabled(jTabbedPane.getSelectedIndex() == 2);
		// btnMoreTableImportRun.setEnabled(jTabbedPane.getSelectedIndex() ==
		// 1);

	}

	private boolean getexcute() {
		if (this.jTabbedPane.getSelectedIndex() == 0) {
			if (tableModel != null
					&& tableModel.getCurrentRow() != null
					&& ((TxtTaskEx) tableModel.getCurrentRow())
							.getExecuteState() != null
					&& ((TxtTaskEx) tableModel.getCurrentRow())
							.getExecuteState().equals(Integer.valueOf("1"))) {
				return true;
			}
		} else {
			if (tableModel2 != null
					&& tableModel2.getCurrentRow() != null
					&& ((DBTaskEx) tableModel2.getCurrentRow())
							.getExecuteState() != null
					&& ((DBTaskEx) tableModel2.getCurrentRow())
							.getExecuteState().equals(Integer.valueOf("1"))) {
				return true;
			}
		}
		return false;
	}

	private boolean noempty() {
		if (this.jTabbedPane.getSelectedIndex() == 0) {
			if (tableModel != null && tableModel.getColumnCount() > 0) {
				return true;
			}
		} else {
			if (tableModel2 != null && tableModel2.getColumnCount() > 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @return Returns the type.
	 */
	public String getImportType() {
		return importType;
	}

	/**
	 * @param type
	 *            The type to set.
	 */
	public void setImportType(String type) {
		this.importType = type;
	}

	/**
	 * 
	 * This method initializes jPanel6
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel6() {
		if (jPanel6 == null) {
			jPanel6 = new JPanel();
			jPanel6.setLayout(new BorderLayout());
			jPanel6.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel6;
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
		}
		return jScrollPane1;
	}

	/**
	 * 
	 * This method initializes jTable2
	 * 
	 * 
	 * 
	 * @return javax.swing.JTable
	 * 
	 */
	private JTable getJTable2() {
		if (jTable2 == null) {
			jTable2 = new JTable();
			jTable2.setIntercellSpacing(new java.awt.Dimension(0, 0));
			jTable2.setShowHorizontalLines(false);
			jTable2.setShowVerticalLines(false);
		}
		return jTable2;
	}

	/**
	 * 
	 * This method initializes jScrollPane2
	 * 
	 * 
	 * 
	 * @return javax.swing.JScrollPane
	 * 
	 */
	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setViewportView(getJTable2());
		}
		return jScrollPane2;
	}

	/**
	 * @return Returns the impType.
	 */
	public String getImpType() {
		return impType;
	}

	/**
	 * @param impType
	 *            The impType to set.
	 */
	public void setImpType(String impType) {
		this.impType = impType;
	}

	public void setHs(Hashtable hs) {
		this.hs = hs;
	}

}
