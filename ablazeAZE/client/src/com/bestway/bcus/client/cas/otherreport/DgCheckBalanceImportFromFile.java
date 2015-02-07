package com.bestway.bcus.client.cas.otherreport;

import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.entity.BalanceInfo;
import com.bestway.bcus.cas.entity.CheckBalance;
import com.bestway.bcus.client.cas.otherreport.DgBaLanceInfoImportFromFile.ImportFileDataRunnable;
import com.bestway.bcus.client.common.CommonClientBig5GBConverter;
import com.bestway.bcus.client.common.CommonFileFilter;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonStepProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.WareSet;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Dimension;

public class DgCheckBalanceImportFromFile extends JDialogBase {

	private JPanel jContentPane = null;

	private JPanel jPanel = null;

	private JButton btnOk = null;

	private JButton btnCancel = null;

	private ButtonGroup buttonGroup = null; // @jve:decl-index=0:parse,visual-constraint="522,16"

	private JTextField tfImportFile = null;

	private JButton btnImportFile = null;

	private File txtFile = null;

	private boolean ok = false;

	private Hashtable columnNo = null;

	private JButton btnFileCorrespondColumn = null;

	private JCheckBox cbIsTitle = null;

	private CasAction casAction = null;
	
	private MaterialManageAction materialManageAction = null;
	
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
	public DgCheckBalanceImportFromFile() {
		super();
		initialize();
		casAction = (CasAction) CommonVars
				.getApplicationContext().getBean("casAction");
		materialManageAction = (MaterialManageAction) CommonVars
		.getApplicationContext().getBean("materialManageAction");
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
		this.setTitle("盘点平衡表数据导入");
		this.setContentPane(getJContentPane());
		this.setSize(477, 273);

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
			jPanel.setLayout(null);
			jPanel.setBounds(36, 18, 392, 163);
			jPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null,
					"", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			jLabel1.setBounds(34, 100, 89, 25);
			jLabel1.setText("导入数据的文件");
			jPanel.add(jLabel1, null);
			jPanel.add(getTfImportFile(), null);
			jPanel.add(getBtnImportFile(), null);
			jPanel.add(getBtnFileCorrespondColumn(), null);
			jPanel.add(getCbIsTitle(), null);
			jPanel.add(getCbUpperCase(), null);
			jPanel.add(getCbBig5ConvertToGB(), null);
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
			btnOk.setBounds(253, 198, 69, 23);
			btnOk.setText("确定");
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (txtFile == null) {
						JOptionPane.showMessageDialog(
								DgCheckBalanceImportFromFile.this, "请选择要导入的文件",
								"提示", 0);
						return;
					}
					if (!txtFile.exists()) {
						JOptionPane.showMessageDialog(
								DgCheckBalanceImportFromFile.this, "你选择的文件不存在",
								"提示", 0);
						return;
					}
					if (columnNo == null) {
						JOptionPane.showMessageDialog(
								DgCheckBalanceImportFromFile.this,
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
				
				//啊，就在这里啦
				CommonStepProgress.setStepMessage("系统正在检查数据...");
				List<CheckBalance> invalidationData = casAction.checkFileDataForCheckBalance(
						request, list, columnNo);//导入前的检查columnNo为栏位对应HashTable
				
				
				//如果有错，显示经检查错误的记录
				if (invalidationData.size() > 0) {
					CommonStepProgress.closeStepProgressDialog();
					DgCheckBalanceInvalidationFileData dgInvalidationFileData = new DgCheckBalanceInvalidationFileData();
					dgInvalidationFileData.setDataSource(invalidationData);
					dgInvalidationFileData.setVisible(true);
					return;
				}
				
				CommonStepProgress.setStepMessage("系统正在保存数据...");
				list = casAction.batchSaveCheckBalance(request, list);
				
				DgCheckBalanceImportFromFile.this.tableModel.addRows(list);
				
				DgCheckBalanceImportFromFile.this.dispose();
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

	private JCheckBox cbBig5ConvertToGB = null;

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
		ArrayList<CheckBalance> list = new ArrayList<CheckBalance>();
		String[] strs = null;
		String[] materielNameSpec;
		int row = 0;// 文件的行数，主要是控制当文件第一行为标题行的时候跳出
		try {
			if (txtFile.isDirectory() == true) {
				return new ArrayList();
			}
			String suffix = getSuffix(txtFile);
			if (suffix != null) {
//				List lsExsit = casAction
//						.findBalanceInfoNameSpecUnitName(new Request(
//								CommonVars.getCurrUser()));
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
							//每一行数据
							strs = s.split(String.valueOf((char) 9));//以空格分  水平制表符 
							convertToFileData(list, strs, cbUpperCase.isSelected(),
												cbBig5ConvertToGB.isSelected());
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
								if(cell[j].getType() == CellType.DATE){
									DateCell date = (DateCell)cell[j];
									SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
									strs[j] = sdf.format(date.getDate());
								}else{
									strs[j] = cell[j].getContents().trim();
									System.out.println("wss strs" + j + " = " + strs[j]);
								}
							}
							System.out.println("***************************************");
							for(int j=0;j<strs.length;j++){
								System.out.println("wss strs" + j + " = " + strs[j]);
							}
							
							
							convertToFileData(list, strs,cbUpperCase.isSelected(),
													cbBig5ConvertToGB.isSelected());
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

	private void convertToFileData(ArrayList<CheckBalance> list,
			String[] strs, boolean isUpperCase,boolean isToGB) {
		
		//wss2010.08.16增加  是否繁体转简体
		if(isToGB){
			strs = changStrs(strs);
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		CheckBalance data = new CheckBalance();
		
		String s1 = getFileColumnValue(strs,0);
		try{
			data.setCheckDate(sdf.parse(s1));
			if(isUpperCase){
				data.setPtNo(getFileColumnValue(strs,1).trim().toUpperCase());
			}else{
				data.setPtNo(getFileColumnValue(strs,1).trim());
			}
			
			System.out.println("wss " + getFileColumnValue(strs,2).trim());
			WareSet wareSet = materialManageAction.findWarerByName(new Request(CommonVars.getCurrUser()),
					getFileColumnValue(strs,2).trim());
			
			System.out.println("wss wareset.name" + wareSet.getName());
			data.setWareSet(wareSet);
			
			data.setCheckAmount(Double.valueOf(getFileColumnValue(strs,3)));
			
			data.setLjCpMark(String.valueOf(getFileColumnValue(strs,4)));//料件成品标记
			data.setKcType(String.valueOf(getFileColumnValue(strs,5)));//库存标记
			data.setBomVersion(String.valueOf(getFileColumnValue(strs,6)));//Bom版本号
		}catch(Exception e){
			e.printStackTrace();
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
			btnCancel.setBounds(342, 198, 69, 23);
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgCheckBalanceImportFromFile.this.dispose();
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
			tfImportFile.setBounds(124, 101, 220, 22);
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
			btnImportFile.setBounds(344, 101, 24, 22);
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
									.showOpenDialog(DgCheckBalanceImportFromFile.this);
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
			btnFileCorrespondColumn.setBounds(34, 131, 334, 21);
			btnFileCorrespondColumn.setText("设定导入资料与文件资料的列对应关系");
			btnFileCorrespondColumn
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (txtFile == null) {
								JOptionPane.showMessageDialog(
										DgCheckBalanceImportFromFile.this,
										"请选择要导入的文件", "提示", 0);
								return;
							}
							if (!txtFile.exists()) {
								JOptionPane.showMessageDialog(
										DgCheckBalanceImportFromFile.this,
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
												.valueOf((char) 9));//这个是？水平制表符
										DgCheckBalanceCorrespondFile dialog = new DgCheckBalanceCorrespondFile();
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
											DgCheckBalanceCorrespondFile dialog = new DgCheckBalanceCorrespondFile();
											dialog.setFileColumnCount(rs.getColumns());
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
			cbIsTitle.setBounds(31, 16, 168, 22);
			cbIsTitle.setText("文件第一行为标题行");
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
			cbUpperCase.setText("将物料编码去尾部空格并且转换成大写");
		}
		return cbUpperCase;
	}

	/**
	 * This method initializes cbBig5ConvertToGB	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbBig5ConvertToGB() {
		if (cbBig5ConvertToGB == null) {
			cbBig5ConvertToGB = new JCheckBox();
			cbBig5ConvertToGB.setBounds(new Rectangle(31, 67, 235, 21));
			cbBig5ConvertToGB.setText("繁转简");
		}
		return cbBig5ConvertToGB;
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"
