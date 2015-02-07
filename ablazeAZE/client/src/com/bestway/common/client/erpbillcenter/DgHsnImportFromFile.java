/**
 * 刘民添加部分注释
 * 
 * 实际报关资料数据导入
 * 
 * @author 余鹏// change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 * 
 */
package com.bestway.common.client.erpbillcenter;

import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import jxl.Sheet;
import jxl.Workbook;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.entity.StatCusNameRelationHsn;
import com.bestway.bcus.client.common.CommonClientBig5GBConverter;
import com.bestway.bcus.client.common.CommonFileFilter;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonStepProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.FileReading;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.dataimport.entity.ImportDataOrder;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgHsnImportFromFile extends JDialogBase {

	private JPanel jContentPane = null;

	private JPanel jPanel = null;

	private JButton btnOk = null;

	private JButton btnCancel = null;

	private ButtonGroup buttonGroup = null;

	private JTextField tfImportFile = null;

	private JButton btnImportFile = null;
	/**
	 * 定义导入文件
	 */

	private File txtFile = null;

	private boolean ok = false;
	/**
	 * 定义列
	 */

	private Hashtable columnNo = null;  //  @jve:decl-index=0:

	private JButton btnFileCorrespondColumn = null;

	private JCheckBox cbIsTitle = null;

	private CasAction casAction = null;

	private JTableListModel tableModel = null;
	/**
	 * 物料类型
	 */

	private String materielType = null;

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
	public DgHsnImportFromFile() {
		super();
		initialize();
		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
				"casAction");
		// initStrMap();
		getInitedOrderValue();
	}

	/**
	 * This method is initializes
	 * 
	 * @param owner
	 * @param isModal
	 */

	public DgHsnImportFromFile(JFrame owner, boolean isModal) {
		super(owner, isModal);
		initialize();
		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
				"casAction");
		// initStrMap();
		getInitedOrderValue();

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("实际报关资料数据导入");
		this.setContentPane(getJContentPane());
		this.setSize(410, 235);

	}

	/**
	 * 获取所有列值
	 */

	private void getInitedOrderValue() {
		if (columnNo == null) {
			columnNo = new Hashtable<Integer, Integer>();
		}
		List<ImportDataOrder> order = casAction
				.findImportDataOrder(new Request(CommonVars.getCurrUser()),
						"StatCusNameRelationHsn");
		for (ImportDataOrder item : order) {
			if (item.getColumnName().equals("complex")) {
				columnNo.put(Integer.valueOf(1),
						item.getPosition() == null ? -1 : item.getPosition());
			} else if (item.getColumnName().equals("cusName")) {
				columnNo.put(Integer.valueOf(2),
						item.getPosition() == null ? -1 : item.getPosition());
			} else if (item.getColumnName().equals("cusSpec")) {
				columnNo.put(Integer.valueOf(3),
						item.getPosition() == null ? -1 : item.getPosition());
			} else if (item.getColumnName().equals("cusUnit")) {
				columnNo.put(Integer.valueOf(4),
						item.getPosition() == null ? -1 : item.getPosition());
			} else if (item.getColumnName().equals("emsNo")) {
				columnNo.put(Integer.valueOf(5),
						item.getPosition() == null ? -1 : item.getPosition());
			} else if (item.getColumnName().equals("emsBeginDate")) {
				columnNo.put(Integer.valueOf(6),
						item.getPosition() == null ? -1 : item.getPosition());
			} else if (item.getColumnName().equals("emsEndDate")) {
				columnNo.put(Integer.valueOf(7),
						item.getPosition() == null ? -1 : item.getPosition());
			} else if (item.getColumnName().equals("version")) {
				columnNo.put(Integer.valueOf(8),
						item.getPosition() == null ? -1 : item.getPosition());
			}
		}
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
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(145, 45, 64, 25));
			jLabel2.setText("管理类型:");
			javax.swing.JLabel jLabel1 = new JLabel();
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setBounds(11, 14, 381, 153);
			jPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null,
					"", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			jLabel1.setBounds(7, 79, 89, 25);
			jLabel1.setText("导入数据的文件");
			jPanel.add(jLabel1, null);
			jPanel.add(getTfImportFile(), null);
			jPanel.add(getBtnImportFile(), null);
			jPanel.add(getBtnFileCorrespondColumn(), null);
			jPanel.add(getCbIsTitle(), null);
			jPanel.add(getCbIsComplexToSimplified(), null);
			jPanel.add(jLabel2, null);
			jPanel.add(getCbbManageType(), null);
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
			btnOk.setBounds(230, 172, 69, 23);
			btnOk.setText("确定");
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (txtFile == null) {
						JOptionPane.showMessageDialog(DgHsnImportFromFile.this,
								"请选择要导入的文件", "提示", 0);
						return;
					}
					if (!txtFile.exists()) {
						JOptionPane.showMessageDialog(DgHsnImportFromFile.this,
								"你选择的文件不存在", "提示", 0);
						return;
					}
					if (columnNo == null) {
						JOptionPane.showMessageDialog(DgHsnImportFromFile.this,
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

	/**
	 * 验证导入文件其数据的有效性
	 * 
	 * @author ower
	 * 
	 */

	class ImportFileDataRunnable extends Thread {
		@Override
		public void run() {
			try {
				String taskId = CommonProgress.getExeTaskId();
				CommonStepProgress.showStepProgressDialog(taskId);
				CommonStepProgress.setStepMessage("系统正在读取文件资料，请稍后...");
				Boolean isFzj = null;
				if (cbIsComplexToSimplified.isSelected()) {
					isFzj = new Boolean(true);
				}
				List list = parseTxtFile(txtFile, isFzj);
				if (list == null || list.size() == 0) {
					JOptionPane.showMessageDialog(DgHsnImportFromFile.this,
							"您导入的文档没有数据,请检查!", "提示",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				Request request = new Request(CommonVars.getCurrUser());
				request.setTaskId(taskId);
				List<StatCusNameRelationHsn> data = casAction
						.checkImportDataForHsn(request, list, columnNo, taskId,
								materielType);
				if (data != null && data.size() != 0) {
					StatCusNameRelationHsn tmp = data.get(0);
					if (tmp.getErrorReason() != null) {
						CommonStepProgress.closeStepProgressDialog();
						DgInvalidationFileData dgInvalidationFileData = new DgInvalidationFileData();
						dgInvalidationFileData.setDataSource(data);
						dgInvalidationFileData.setVisible(true);
						return;
					} else {
						casAction.batchUpdateStatCusNameRelationHsn(request,
								data);
						DgHsnImportFromFile.this.tableModel.addRows(data);
						DgHsnImportFromFile.this.dispose();
					}
				} else {
					DgHsnImportFromFile.this.dispose();
				}
			} finally {
				CommonStepProgress.closeStepProgressDialog();
			}
		}
	}

	/**
	 * 获得导入文件的所有列值
	 * 
	 * @param fileRow
	 * @param dataIndex
	 * @return
	 */

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

	// Map<String, String> strMap = new HashMap<String, String>();

	private JCheckBox cbIsComplexToSimplified = null;

	private JLabel jLabel2 = null;

	private JComboBox cbbManageType = null;

	/**
	 * 繁体转换成简体
	 * 
	 * @param source
	 * @return
	 */

	private String[] changStrs(String[] source) {
		String newStrs[] = new String[source.length];
		for (int i = 0, n = source.length; i < n; i++) {
			newStrs[i] = CommonClientBig5GBConverter.getInstance().big5ConvertToGB(
					source[i]);
		}
		return newStrs;
	}

	/**
	 * 导入文件解析
	 * 
	 * @param file
	 * @param isFzj
	 * @return
	 */

	private List parseTxtFile(File file, Boolean isFzj) {
		BufferedReader in;
		InputStream is = null;
		ArrayList<StatCusNameRelationHsn> list = new ArrayList<StatCusNameRelationHsn>();
		String[] strs = null;
		String[] materielNameSpec;
		int row = 0;// 文件的行数，主要是控制当文件第一行为标题行的时候跳出
		try {
			if (txtFile.isDirectory() == true) {
				return new ArrayList();
			}
			String suffix = getSuffix(txtFile);
			if (suffix != null) {
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
							convertToFileData(list, strs, isFzj);
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
					int beginRow = 1;
					if (cbIsTitle.isSelected()) {
						beginRow = 2;
					}
					List<List> rows = FileReading.readExcel(file, beginRow,
							null);
					int rowCount = rows.size();
					for (int i = 0; i < rowCount; i++) {
						List rList = rows.get(i);
						strs = new String[rList.size()];
						for (int j = 0; j < rList.size(); j++) {
							strs[j] = (String) rList.get(j);
						}
						convertToFileData(list, strs, isFzj);
					}
				}
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		return list;
	}

	/**
	 * 导入文件对应
	 * 
	 * @param list
	 * @param strs
	 * @param isFzj
	 */

	private void convertToFileData(ArrayList<StatCusNameRelationHsn> list,
			String[] strs, Boolean isFzj) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (isFzj != null && isFzj.booleanValue()) {
			strs = changStrs(strs);// 繁转简
		}
		StatCusNameRelationHsn data = new StatCusNameRelationHsn();

		// =================报关资料部分
		data.setTemp1(getFileColumnValue(strs, 1));// 报关商品编码
		data.setCusName(getFileColumnValue(strs, 2));
		data.setCusSpec(getFileColumnValue(strs, 3));
		data.setTemp2(getFileColumnValue(strs, 4));// 报关商品单位
		data.setEmsNo(getFileColumnValue(strs, 5));
		// 手册开始与结束日期
		try {
			data.setEmsBeginDate(sdf.parse(getFileColumnValue(strs, 6)));
		} catch (ParseException e) {
			data.setEmsBeginDate(null);
		}
		try {
			data.setEmsEndDate(sdf.parse(getFileColumnValue(strs, 7)));
		} catch (ParseException e) {
			data.setEmsEndDate(null);
		}
		ItemProperty ptype = (ItemProperty) cbbManageType.getSelectedItem();
		data.setProjectType(Integer.parseInt(ptype.getCode()));// 管理类型
		data.setMaterielType(materielType);// 物料类型
		if (materielType.equals(MaterielType.FINISHED_PRODUCT)) {
			String version = getFileColumnValue(strs, 8);
			try {
				data.setVersion(Integer.valueOf(version));
			} catch (Exception e) {
				data.setVersion(null);
			}
		}

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
			btnCancel.setBounds(318, 172, 69, 23);
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgHsnImportFromFile.this.dispose();
					setOk(false);
				}
			});
		}
		return btnCancel;
	}

	/**
	 * This method initializes tfImportFile
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfImportFile() {
		if (tfImportFile == null) {
			tfImportFile = new JTextField();
			tfImportFile.setBounds(97, 79, 220, 25);
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
			btnImportFile.setBounds(322, 79, 24, 22);
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
									.showOpenDialog(DgHsnImportFromFile.this);
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

	/**
	 * 获取文件后缀
	 * 
	 * @param f
	 * @return
	 */

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
			btnFileCorrespondColumn.setBounds(7, 113, 334, 21);
			btnFileCorrespondColumn.setText("设定导入资料与文件资料的列对应关系");
			btnFileCorrespondColumn
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (txtFile == null) {
								JOptionPane.showMessageDialog(
										DgHsnImportFromFile.this, "请选择要导入的文件",
										"提示", 0);
								return;
							}
							if (!txtFile.exists()) {
								JOptionPane.showMessageDialog(
										DgHsnImportFromFile.this, "你选择的文件不存在",
										"提示", 0);
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
										DgHsnCorrespondFile dialog = new DgHsnCorrespondFile();
										dialog.setFileColumnCount(strs.length);
										dialog.setSelectedValues(columnNo);
										dialog.setMaterielType(materielType);
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
											DgHsnCorrespondFile dialog = new DgHsnCorrespondFile();
											dialog.setFileColumnCount(rs
													.getColumns());
											dialog.setSelectedValues(columnNo);
											dialog
													.setMaterielType(materielType);
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
			cbIsTitle.setBounds(7, 16, 168, 22);
			cbIsTitle.setText("文件第一行是否为标题行");
		}
		return cbIsTitle;
	}

	/**
	 * @return the materielType
	 */
	public String getMaterielType() {
		return materielType;
	}

	/**
	 * @param materielType
	 *            the materielType to set
	 */
	public void setMaterielType(String materielType) {
		this.materielType = materielType;
	}

	/**
	 * This method initializes cbIsComplexToSimplified
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIsComplexToSimplified() {
		if (cbIsComplexToSimplified == null) {
			cbIsComplexToSimplified = new JCheckBox();
			cbIsComplexToSimplified.setBounds(new Rectangle(7, 45, 130, 21));
			cbIsComplexToSimplified.setText("是否繁转简");
		}
		return cbIsComplexToSimplified;
	}

	/**
	 * This method initializes cbbManageType
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbManageType() {
		if (cbbManageType == null) {
			cbbManageType = new JComboBox();
			cbbManageType.setBounds(new Rectangle(213, 45, 92, 25));
			if (cbbManageType.getSelectedItem() != null)
				cbbManageType.removeAllItems();
			cbbManageType.addItem(new ItemProperty("" + ProjectType.BCS,
					"电子化手册"));
			cbbManageType.addItem(new ItemProperty("" + ProjectType.DZSC,
					"电子手册"));
			cbbManageType.addItem(new ItemProperty("" + ProjectType.BCUS,
					"电子帐册"));
		}
		return cbbManageType;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
