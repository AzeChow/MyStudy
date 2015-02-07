package com.bestway.bcus.client.cas.otherreport;

import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.entity.BalanceInfo;
import com.bestway.bcus.client.common.CommonClientBig5GBConverter;
import com.bestway.bcus.client.common.CommonFileFilter;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonStepProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgBaLanceInfoImportFromFile extends JDialogBase {

	private JPanel jContentPane = null;

	private JPanel jPanel = null;

	private JButton btnOk = null;

	private JButton btnCancel = null;

	private ButtonGroup buttonGroup = null; // @jve:decl-index=0:parse,visual-constraint="522,16"

	private JTextField tfSpaceSymbol = null;

	private JTextField tfImportFile = null;

	private JButton btnImportFile = null;

	private File txtFile = null;

	private boolean ok = false;

	private Hashtable columnNo = null;

	private JButton btnFileCorrespondColumn = null;

	private JCheckBox cbIsTitle = null;

	private CasAction casAction = null;	
	
	private JTableListModel tableModel = null;	

	public JTableListModel getTableModel() {
		return tableModel;
	}

	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgBaLanceInfoImportFromFile() {
		super();
		initialize();
		casAction = (CasAction) CommonVars
				.getApplicationContext().getBean("casAction");
//		initStrMap();
	}

	
	public DgBaLanceInfoImportFromFile(JDialog owner, boolean isModal) {
		super(owner,isModal);
		initialize();
		casAction = (CasAction) CommonVars
				.getApplicationContext().getBean("casAction");
//		initStrMap();
	}

	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("短溢表数据导入");
		this.setContentPane(getJContentPane());
		this.setSize(477, 237);

	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJPanel(), null);
			jContentPane.add(getBtnOk(), null);
			jContentPane.add(getBtnCancel(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			javax.swing.JLabel jLabel1 = new JLabel();
			jPanel = new JPanel();
			javax.swing.JLabel jLabel = new JLabel();
			jPanel.setLayout(null);
			jPanel.setBounds(36, 18, 392, 142);
			jPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null,
					"", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			jLabel.setBounds(34, 19, 101, 15);
			jLabel.setText("名称规格分隔符号");
			jLabel1.setBounds(34, 65, 89, 25);
			jLabel1.setText("导入数据的文件");
			jPanel.add(getTfSpaceSymbol(), null);
			jPanel.add(jLabel, null);
			jPanel.add(jLabel1, null);
			jPanel.add(getTfImportFile(), null);
			jPanel.add(getBtnImportFile(), null);
			jPanel.add(getBtnFileCorrespondColumn(), null);
			jPanel.add(getCbIsTitle(), null);
			jPanel.add(getCbUpperCase(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes btnOk
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton();
			btnOk.setBounds(254, 169, 69, 23);
			btnOk.setText("确定");
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (txtFile == null) {
						JOptionPane.showMessageDialog(
								DgBaLanceInfoImportFromFile.this, "请选择要导入的文件",
								"提示", 0);
						return;
					}
					if (!txtFile.exists()) {
						JOptionPane.showMessageDialog(
								DgBaLanceInfoImportFromFile.this, "你选择的文件不存在",
								"提示", 0);
						return;
					}
					if (columnNo == null) {
						JOptionPane.showMessageDialog(
								DgBaLanceInfoImportFromFile.this,
								"你没有选择导入数据与文件数据列的对应关系", "提示", 0);
						return;
					}
					new ImportFileDataRunnable().start();
					setOk(true);
				}
			});
		}
		return btnOk;
	}

	class ImportFileDataRunnable extends Thread {
		public void run() {
			try {
				String taskId = CommonProgress.getExeTaskId();
				CommonStepProgress.showStepProgressDialog(taskId);
				CommonStepProgress.setStepMessage("系统正在读取文件资料，请稍后...");
				List list = parseTxtFile(txtFile);
				Request request = new Request(CommonVars.getCurrUser());
				request.setTaskId(taskId);
				List<BalanceInfo> invalidationData = casAction.checkImportData(
						request, list, columnNo);
				if (invalidationData.size() > 0) {
					CommonStepProgress.closeStepProgressDialog();
					DgBalanceInfoInvalidationFileData dgInvalidationFileData = new DgBalanceInfoInvalidationFileData();
					dgInvalidationFileData.setDataSource(invalidationData);
					dgInvalidationFileData.setVisible(true);
					return;
				}
				DgBaLanceInfoImportFromFile.this.tableModel.addRows(list);
				DgBaLanceInfoImportFromFile.this.dispose();
			} finally {
				CommonStepProgress.closeStepProgressDialog();
			}
		}
	}

	private String getFileColumnValue(String[] fileRow, int dataIndex) {
		if (columnNo.get(Integer.valueOf(dataIndex)) == null
				|| "".equals(columnNo.get(Integer.valueOf(dataIndex)))) {
			return "";
		}
		int columnIndex = Integer.parseInt(columnNo.get(
				Integer.valueOf(dataIndex)).toString());
		if (columnIndex < 0 || columnIndex > fileRow.length - 1) {
			return "";
		} else {
			return fileRow[columnIndex];
		}
	}

//	Map<String, String> strMap = new HashMap<String, String>();

	private JCheckBox cbUpperCase = null;

//	private void initStrMap() {
//		List list = CustomBaseList.getInstance().getGbtobigs();
//		for (int i = 0; i < list.size(); i++) {
//			Gbtobig gb = (Gbtobig) list.get(i);
//			strMap.put(gb.getBigname(), gb.getName());
//		}
//	}
//
//	private String changeStr(String s) {
//		String yy = "";
//		char[] xxx = s.toCharArray();
//		for (int i = 0; i < xxx.length; i++) {
//			String z = String.valueOf(xxx[i]);
//			if (String.valueOf(xxx[i]).getBytes().length == 2) {
//				if (strMap.get(String.valueOf(xxx[i])) != null) {
//					z = (String) strMap.get(String.valueOf(xxx[i]));
//				}
//			}
//			yy = yy + z;
//		}
//		return yy;
//	}

	private String[] changStrs(String[] source) {
		String newStrs[] = new String[source.length];
		for (int i = 0, n = source.length; i < n; i++) {
//			newStrs[i] = changeStr(source[i]);
			// System.out.println(newStrs[i]);
			newStrs[i]=CommonClientBig5GBConverter.getInstance().big5ConvertToGB(source[i]);
		}
		return newStrs;
	}

	private List parseTxtFile(File file) {
		BufferedReader in;
		InputStream is = null;
		ArrayList<BalanceInfo> list = new ArrayList<BalanceInfo>();
		String[] strs = null;
		String[] materielNameSpec;
		int row = 0;// 文件的行数，主要是控制当文件第一行为标题行的时候跳出
		try {
			if (txtFile.isDirectory() == true) {
				return new ArrayList();
			}
			String suffix = getSuffix(txtFile);
			if (suffix != null) {
				List lsExsitNSU = casAction
						.findBalanceInfoNameSpecUnitName(new Request(
								CommonVars.getCurrUser()));
				if (suffix.toLowerCase().equals("txt")) {
					in = new BufferedReader(new FileReader(file));
					String s = new String();
					try {
						while ((s = in.readLine()) != null) {
							row++;
							if (cbIsTitle.isSelected() && row == 1) {
								continue;
							}
							if (s.trim().equals("")) {
								continue;
							}
							strs = s.split(String.valueOf((char) 9));
							convertToFileData(list, strs,lsExsitNSU);
						}
					} catch (IOException e3) {
						e3.printStackTrace();
					}
					try {
						in.close();
					} catch (IOException e2) {
						e2.printStackTrace();
					}
				} else if (suffix.toLowerCase().equals("xls")) {
					try {
						// 构建Workbook对象, 只读Workbook对象
						// 直接从本地文件创建Workbook
						// 从输入流创建Workbook
						is = new FileInputStream(txtFile);
						jxl.Workbook rwb = Workbook.getWorkbook(is);
						// 获取第一张Sheet表
						Sheet rs = rwb.getSheet(0);
						// int columnCount = rs.getColumns();
						int rowCount = rs.getRows();
						for (int i = 0; i < rowCount; i++) {
							if (cbIsTitle.isSelected() && i == 0) {
								continue;
							}
							Cell[] cell = rs.getRow(i);
							if (cell[0].getContents().trim().equals("")) {
								continue;
							}
							strs = new String[cell.length];
							for (int j = 0; j < cell.length; j++) {
								strs[j] = cell[j].getContents().trim();
							}
							convertToFileData(list, strs,lsExsitNSU);
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					try {
						is.close();
					} catch (IOException e2) {
						e2.printStackTrace();
					}
				}
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		return list;
	}

	private void convertToFileData(ArrayList<BalanceInfo> list,
			String[] strs, List lsExitNSU) {
		strs = changStrs(strs);
		BalanceInfo data = new BalanceInfo();
		
		String s1 = getFileColumnValue(strs,1);
		if (s1 != null) {
			int x = s1.indexOf(tfSpaceSymbol.getText().trim());
			if (x == -1) {
				data.setName(s1);
			} else {
				String split[] = s1.split(tfSpaceSymbol.getText().trim());
				data.setName(split[0].trim());
				data.setSpec(split[1].trim());
				data.setUnitName(split[2].trim());
			}
		}
		String NSU = data.getName()==null?"":data.getName()+
				data.getSpec()==null?"":data.getSpec()+
						data.getUnitName()==null?"":data.getUnitName();
		if(lsExitNSU.contains(NSU)) return;
		
		data.setF1(Double.valueOf(getFileColumnValue(strs,2)));
		data.setF2(Double.valueOf(getFileColumnValue(strs,3)));
		data.setF3(Double.valueOf(getFileColumnValue(strs,4)));
		data.setF4(Double.valueOf(getFileColumnValue(strs,5)));
		data.setF5(Double.valueOf(getFileColumnValue(strs,6)));
		data.setF6(Double.valueOf(getFileColumnValue(strs,7)));
		

		list.add(data);
	}

	/**
	 * This method initializes btnCancel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(342, 169, 69, 23);
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgBaLanceInfoImportFromFile.this.dispose();
					setOk(false);
				}
			});
		}
		return btnCancel;
	}

	/**
	 * This method initializes tfSpaceSymbol
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfSpaceSymbol() {
		if (tfSpaceSymbol == null) {
			tfSpaceSymbol = new JTextField();
			tfSpaceSymbol.setBounds(142, 16, 45, 22);
			tfSpaceSymbol.setText("/");
			tfSpaceSymbol.setEditable(false);
		}
		return tfSpaceSymbol;
	}

	/**
	 * This method initializes tfImportFile
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfImportFile() {
		if (tfImportFile == null) {
			tfImportFile = new JTextField();
			tfImportFile.setBounds(124, 66, 220, 22);
			tfImportFile.setEditable(false);
		}
		return tfImportFile;
	}

	/**
	 * This method initializes btnImportFile
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnImportFile() {
		if (btnImportFile == null) {
			btnImportFile = new JButton();
			btnImportFile.setBounds(344, 66, 24, 22);
			btnImportFile.setText("...");
			btnImportFile
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							JFileChooser fileChooser = new JFileChooser();
							fileChooser.removeChoosableFileFilter(fileChooser
									.getFileFilter());
							fileChooser.setFileFilter(new CommonFileFilter(
									new String[] { "txt", "xls" }, "选择文档"));
							int state = fileChooser
									.showOpenDialog(DgBaLanceInfoImportFromFile.this);
							if (state == JFileChooser.APPROVE_OPTION) {
								txtFile = fileChooser.getSelectedFile();
								if (txtFile.exists()) {
									tfImportFile.setText(txtFile.getPath());
								}
							}
						}
					});

		}
		return btnImportFile;
	}

	private String getSuffix(File f) {
		String s = f.getPath(), suffix = null;
		int i = s.lastIndexOf('.');
		if (i > 0 && i < s.length() - 1)
			suffix = s.substring(i + 1).toLowerCase();
		return suffix;
	}

	/**
	 * This method initializes btnFileCorrespondColumn
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnFileCorrespondColumn() {
		if (btnFileCorrespondColumn == null) {
			btnFileCorrespondColumn = new JButton();
			btnFileCorrespondColumn.setBounds(34, 96, 334, 21);
			btnFileCorrespondColumn.setText("设定导入资料与文件资料的列对应关系");
			btnFileCorrespondColumn
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (txtFile == null) {
								JOptionPane.showMessageDialog(
										DgBaLanceInfoImportFromFile.this,
										"请选择要导入的文件", "提示", 0);
								return;
							}
							if (!txtFile.exists()) {
								JOptionPane.showMessageDialog(
										DgBaLanceInfoImportFromFile.this,
										"你选择的文件不存在", "提示", 0);
								return;
							}
							try {
								if (txtFile.isDirectory() == true) {
									return;
								}
								String suffix = getSuffix(txtFile);
								if (suffix != null) {
									if (suffix.toLowerCase().equals("txt")) {
										BufferedReader in = new BufferedReader(
												new FileReader(txtFile));
										String s = "";
										try {
											s = in.readLine();
										} catch (IOException e3) {
											e3.printStackTrace();
										}
										String[] strs = s.split(String
												.valueOf((char) 9));
										DgBalanceInfoCorrespondFile dialog = new DgBalanceInfoCorrespondFile();
										dialog.setFileColumnCount(strs.length);
										dialog.setSelectedValues(columnNo);
										dialog.setVisible(true);
										Hashtable ht = dialog
												.getSelectedValues();
										if (ht != null) {
											columnNo = ht;
										}
										try {
											in.close();
										} catch (IOException e2) {
											e2.printStackTrace();
										}
									} else if (suffix.toLowerCase().equals(
											"xls")) {
										InputStream is = null;
										try {
											// 构建Workbook对象, 只读Workbook对象
											// 直接从本地文件创建Workbook
											// 从输入流创建Workbook
											is = new FileInputStream(txtFile);
											jxl.Workbook rwb = Workbook
													.getWorkbook(is);
											// 获取第一张Sheet表
											Sheet rs = rwb.getSheet(0);
											DgBalanceInfoCorrespondFile dialog = new DgBalanceInfoCorrespondFile();
											dialog.setFileColumnCount(rs
													.getColumns());
											dialog.setSelectedValues(columnNo);
											dialog.setVisible(true);
											Hashtable ht = dialog
													.getSelectedValues();
											if (ht != null) {
												columnNo = ht;
											}
										} catch (Exception ex) {
											ex.printStackTrace();
										}

										try {
											is.close();
										} catch (IOException e2) {
											e2.printStackTrace();
										}
									}
								} else {
									return;
								}
							} catch (FileNotFoundException e1) {
								e1.printStackTrace();
							}
						}
					});
		}
		return btnFileCorrespondColumn;
	}

	/**
	 * @return Returns the ok.
	 */
	public boolean isOk() {
		return ok;
	}

	/**
	 * @param ok
	 *            The ok to set.
	 */
	public void setOk(boolean ok) {
		this.ok = ok;
	}

	/**
	 * This method initializes cbIsTitle
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIsTitle() {
		if (cbIsTitle == null) {
			cbIsTitle = new JCheckBox();
			cbIsTitle.setBounds(205, 16, 168, 22);
			cbIsTitle.setText("文件第一行是否为标题行");
		}
		return cbIsTitle;
	}

	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbUpperCase() {
		if (cbUpperCase == null) {
			cbUpperCase = new JCheckBox();
			cbUpperCase.setBounds(new Rectangle(31, 42, 343, 16));
			cbUpperCase.setText("是否将物料编码去尾部空格并且转换成大写");
		}
		return cbUpperCase;
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"
