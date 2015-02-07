/**
 * 刘民添加部分注释
 * 修改时间： 2008-9-20
 * @author 余鹏// change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 * 
 */
package com.bestway.common.client.erpbillcenter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.bill.entity.CasInnerMergeDataOrder;
import com.bestway.bcus.cas.entity.FactoryAndFactualCustomsRalation;
import com.bestway.bcus.cas.entity.StatCusNameRelationHsn;
import com.bestway.bcus.client.common.CommonClientBig5GBConverter;
import com.bestway.bcus.client.common.CommonFileFilter;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonStepProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.CommonUtils;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.util.RegexUtil;

@SuppressWarnings("unchecked")
public class DgRelationImportFromFile extends JDialogBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2454614155910086525L;

	private JPanel jContentPane = null;

	private JPanel jPanel = null;

	private JButton btnOk = null;

	private JButton btnCancel = null;

	private ButtonGroup groupButton = null; // @jve:decl-index=0:

	private JTextField tfImportFile = null;

	private JButton btnImportFile = null;
	/**
	 * 定义导入文件变量
	 */

	private File txtFile = null;
	/**
	 * 判断导入的是否为有效数据
	 */

	private boolean ok = false;
	/**
	 * 用于存放数据列的列号
	 */

	private int[] columnNo = null; // @jve:decl-index=0:

	private JButton btnFileCorrespondColumn = null;

	private JCheckBox cbIsTitle = null;

	private CasAction casAction = null;

	private JTableListModel tableModel = null;
	/**
	 * 定义物料类型变量
	 */

	private String materielType = null; // @jve:decl-index=0:
	/**
	 * 是否多对一
	 */

	private boolean isOneToMany = false;

	private HashMap<String, Boolean> parameter = new HashMap(); // @jve:decl-index=0:

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
	public DgRelationImportFromFile() {
		super();
		initialize();
		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
				"casAction");
		// initStrMap();
		getInitedOrderValue();
	}

	/**
	 * 带参数的构造器
	 * 
	 * @param owner
	 * @param isModal
	 */

	public DgRelationImportFromFile(JFrame owner, boolean isModal) {
		super(owner, isModal);
		initialize();
		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
				"casAction");
		// initStrMap();
		this.rbMaterielAndCustomsOneToOneOrManyToOne.setSelected(true);// 物料与报关一对一
		this.rbMaterielAndCustomsOneToMany.setSelected(false);// 物料与报关一对多
		getInitedOrderValue();
		getGroupButton();

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("对应关系数据导入");
		this.setContentPane(getJContentPane());
		this.setSize(683, 410);

	}

	/**
	 * This method initializes buttonGroup1
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getGroupButton() {
		if (groupButton == null) {
			groupButton = new ButtonGroup();
			groupButton.add(getRbMaterielAndCustomsOneToOneOrManyToOne());
			groupButton.add(getRbMaterielAndCustomsOneToMany());
		}
		return groupButton;
	}

	/**
	 * 获取所有列的值
	 */
	private void getInitedOrderValue() {
		if (columnNo == null) {
			columnNo = new int[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
		}
		CasInnerMergeDataOrder order = casAction
				.findCasInnerMergeDataOrder(new Request(CommonVars
						.getCurrUser(), true));
		columnNo[0] = order.getCasF1() == null ? columnNo[0] : order.getCasF1();
		columnNo[1] = order.getCasF2() == null ? columnNo[1] : order.getCasF2();
		columnNo[2] = order.getCasF3() == null ? columnNo[2] : order.getCasF3();
		columnNo[3] = order.getCasF4() == null ? columnNo[3] : order.getCasF4();
		columnNo[4] = order.getCasF5() == null ? columnNo[4] : order.getCasF5();
		columnNo[5] = order.getCasF6() == null ? columnNo[5] : order.getCasF6();
		columnNo[6] = order.getCasF7() == null ? columnNo[6] : order.getCasF7();
		columnNo[7] = order.getCasF8() == null ? columnNo[7] : order.getCasF8();
		columnNo[8] = order.getCasF9() == null ? columnNo[8] : order.getCasF9();
		columnNo[9] = order.getCasF10() == null ? columnNo[9] : order.getCasF10();
		columnNo[10] = order.getCasF11() == null ? columnNo[10] : order.getCasF11();
		columnNo[11] = order.getCasF12() == null ? columnNo[11] : order.getCasF12();
		columnNo[12] = order.getCasF13() == null ? columnNo[12] : order.getCasF13();
		columnNo[13] = order.getCasF14() == null ? columnNo[13] : order.getCasF14();
		columnNo[14] = order.getCasF15() == null ? columnNo[14] : order.getCasF15();
		columnNo[15] = order.getCasF16() == null ? columnNo[15] : order.getCasF16();
		columnNo[16] = order.getCasF17() == null ? columnNo[16] : order.getCasF17();
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
			jContentPane.add(getJPanel1(), null);
			jContentPane.add(getJPanel2(), null);
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
			jLabel46 = new JLabel();
			jLabel46.setBounds(new Rectangle(30, 205, 19, 18));
			jLabel46.setText("7、");
			jLabel45 = new JLabel();
			jLabel45.setBounds(new Rectangle(30, 172, 19, 18));
			jLabel45.setText("6、");
			jLabel44 = new JLabel();
			jLabel44.setBounds(new Rectangle(30, 142, 19, 18));
			jLabel44.setText("5、");
			jLabel43 = new JLabel();
			jLabel43.setBounds(new Rectangle(30, 111, 19, 18));
			jLabel43.setText("4、");
			jLabel42 = new JLabel();
			jLabel42.setBounds(new Rectangle(30, 84, 19, 18));
			jLabel42.setText("3、");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(30, 56, 19, 18));
			jLabel4.setText("2、");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(30, 31, 19, 18));
			jLabel.setText("1、");
			TitledBorder titledBorder = BorderFactory.createTitledBorder(null,
					"", TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, null, null);
			titledBorder.setTitle("数据导入条件");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setBounds(18, 13, 373, 228);
			jPanel.setBorder(titledBorder);
			jPanel.add(getCbIsTitle(), null);
			jPanel.add(getCbIsUpperCase(), null);
			jPanel.add(getCbIsMateriel(), null);
			jPanel.add(getCbIsComplexToSimplified(), null);
			jPanel.add(getRbMaterielAndCustomsOneToOneOrManyToOne(), null);
			jPanel.add(getRbMaterielAndCustomsOneToMany(), null);
			jPanel.add(getCbbCover(), null);
			jPanel.add(jLabel, null);
			jPanel.add(jLabel4, null);
			jPanel.add(jLabel42, null);
			jPanel.add(jLabel43, null);
			jPanel.add(jLabel44, null);
			jPanel.add(jLabel45, null);
			jPanel.add(jLabel46, null);
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
			btnOk.setBounds(265, 336, 78, 29);
			btnOk.setPreferredSize(new Dimension(85, 28));
			btnOk.setText("确定");
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (txtFile == null) {
						JOptionPane.showMessageDialog(
								DgRelationImportFromFile.this, "请选择要导入的文件",
								"提示", 0);
						return;
					}
					if (!txtFile.exists()) {
						JOptionPane.showMessageDialog(
								DgRelationImportFromFile.this, "你选择的文件不存在",
								"提示", 0);
						return;
					}
					if (columnNo == null) {
						JOptionPane.showMessageDialog(
								DgRelationImportFromFile.this,
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

				List list = parseTxtFile(txtFile, cbIsComplexToSimplified.isSelected(),	cbIsTitle.isSelected());

				if (list == null || list.size() == 0) {
					JOptionPane.showMessageDialog(
							DgRelationImportFromFile.this, "您导入的文档没有数据,请检查!",
							"提示", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				
				CommonStepProgress.setStepMessage("系统取文件资料完毕。");
				Request request = new Request(CommonVars.getCurrUser());
				request.setTaskId(taskId);

				List<FactoryAndFactualCustomsRalation> data = casAction
						.checkImportDataForRelation(request, list, columnNo,
								taskId, materielType, getParameter());

				
				
				if (data != null && data.size() != 0) {
					FactoryAndFactualCustomsRalation tmp = data.get(0);
					if ((data.size() != list.size())
							|| (data.size() == list.size() && tmp
									.getErrorReason() != null)) {
						List<FactoryAndFactualCustomsRalation> errorData = new ArrayList<FactoryAndFactualCustomsRalation>();
						List<FactoryAndFactualCustomsRalation> exportData = new ArrayList<FactoryAndFactualCustomsRalation>();
						
						for (FactoryAndFactualCustomsRalation factoryAndFactualCustomsRalation : data) {
							if(factoryAndFactualCustomsRalation.getErrorReason() == null) {
								exportData.add(factoryAndFactualCustomsRalation);
							} else {
								errorData.add(factoryAndFactualCustomsRalation);
							}
						}
						DgInvalidationFileData dgInvalidationFileData = new DgInvalidationFileData();
						dgInvalidationFileData.setDataSource(errorData);
						dgInvalidationFileData.setVisible(true);
						if(dgInvalidationFileData.getResult().intValue() == 1) {
							data = exportData;
						} else {
							return;
						}
						
					}
					
					data = casAction
							.batchSaveFactoryAndFactualCustomsRalation(
									request, data, cbIsMateriel
											.isSelected(), materielType,
									cbbCover.isSelected(),getParameter());
					CommonStepProgress.closeStepProgressDialog();
					JOptionPane.showMessageDialog(DgRelationImportFromFile.this, "导入数据成功!","提示", JOptionPane.INFORMATION_MESSAGE);
					DgRelationImportFromFile.this.tableModel.addRows(data);
					DgRelationImportFromFile.this.dispose();					
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
		int columnIndex = columnNo[dataIndex - 1];
		if (columnIndex < 0 || columnIndex > fileRow.length - 1) {
			return "";
		} else {
			return fileRow[columnIndex];
		}
	}

	// Map<String, String> strMap = new HashMap<String, String>();

	private JCheckBox cbIsUpperCase = null;

	private JCheckBox cbIsMateriel = null;

	private JCheckBox cbIsComplexToSimplified = null;

	private JRadioButton rbMaterielAndCustomsOneToOneOrManyToOne = null;

	private JRadioButton rbMaterielAndCustomsOneToMany = null;

	private JCheckBox cbbCover = null;

	private JPanel jPanel1 = null;

	private JLabel jLabel3 = null;

	private JPanel jPanel2 = null;

	private JTextArea jTextArea = null;

	private JLabel jLabel = null;

	private JLabel jLabel4 = null;

	private JLabel jLabel42 = null;

	private JLabel jLabel43 = null;

	private JLabel jLabel44 = null;

	private JLabel jLabel45 = null;

	private JLabel jLabel46 = null;

	private JTextArea jTextArea1 = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel5 = null;

	/**
	 * 繁体转换成简体
	 * 
	 * @param source
	 * @return
	 */

	private String[] changStrs(String[] source) {
		String newStrs[] = new String[source.length];
		for (int i = 0, n = source.length; i < n; i++) {
			newStrs[i] = CommonClientBig5GBConverter.getInstance()
					.big5ConvertToGB(source[i]);
		}
		return newStrs;
	}

	/**
	 * 导入文件解析
	 * 
	 * @param file
	 *            要导入的数据文件
	 * @param isFzj
	 *            是否繁转简
	 * @param isFristTitle
	 *            第一行是否标题行
	 * @return
	 */
	private List parseTxtFile(File file, boolean isFzj, boolean isFristTitle) {
		
		long begin = System.currentTimeMillis();
		System.out.println("开始导入数据：0");
		
		// 返回结果 工厂和实际客户对应 list 
		ArrayList<FactoryAndFactualCustomsRalation> list = new ArrayList<FactoryAndFactualCustomsRalation>();
		
		BufferedReader in;
		InputStream is = null;
		
		String[] strs = null;
		
		// 文件的行数，主要是控制当文件第一行为标题行的时候跳出
		int row = 0;
		try {
			if (txtFile.isDirectory() == true) {
				return new ArrayList();
			}
			
			// 获得文件名后缀
			String suffix = getSuffix(txtFile);
			if (suffix != null) {
				if (suffix.toLowerCase().equals("txt")) {
					in = new BufferedReader(new FileReader(file));
					String s = new String();
					try {
						while ((s = in.readLine()) != null) {
							row++;
							if (isFristTitle && row == 1) {
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
					try {

						System.out.println("开始读取文件："
								+ (System.currentTimeMillis() - begin) / 1000);
						// 构建Workbook对象, 只读Workbook对象
						// 直接从本地文件创建Workbook
						// 从输入流创建Workbook
						is = new FileInputStream(file);
						WorkbookSettings workbookSettings = new WorkbookSettings();
						workbookSettings.setEncoding("ISO-8859-1");
						jxl.Workbook rwb = Workbook.getWorkbook(is,
								workbookSettings);
						
						// 获取第一张Sheet表
						Sheet rs = rwb.getSheet(0);
						// int columnCount = rs.getColumns();
						System.out.println("列数：" + rs.getColumns());
						int rowCount = rs.getRows();
						System.out.println("结束读取文件："	+ (System.currentTimeMillis() - begin) / 1000);
						System.out.println("开始转化文件数据："	+ (System.currentTimeMillis() - begin) / 1000);
						
						Cell[] cells = null;
						DateCell date = null;
						NumberCell numberCell = null;
						String format = "yyyy-MM-dd";
						
						for (int i = 0; i < rowCount; i++) {
							if (isFristTitle && i == 0) {
								continue;
							}
							cells = rs.getRow(i);
							if (cells[0].getContents().trim().equals("")) {
								continue;
							}
							strs = new String[cells.length];
							for (int j = 0; j < cells.length; j++) {
								if (cells[j].getType() == CellType.DATE) {
									date = (DateCell) cells[j];
									strs[j] = CommonUtils.getDate(date.getDate(), format);
								} else if (cells[j].getType() == CellType.NUMBER) {
									numberCell = (NumberCell) cells[j];
									strs[j] = CommonUtils.formatDoubleByDigitNull(numberCell
											.getValue(), 6);
								} else {
									strs[j] = cells[j].getContents().trim();
								}
								if(i % 10000 == 0) {
									System.gc();
								}
							}
							
							convertToFileData(list, strs, isFzj);
						}
						
						System.out.println("结束转化文件数据："	+ (System.currentTimeMillis() - begin) / 1000);
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

	/**
	 * 导入文件验证
	 * 
	 * @param list
	 * @param strs
	 * @param isFzj
	 */

	private void convertToFileData(
			ArrayList<FactoryAndFactualCustomsRalation> list, String[] strs,
			boolean isFzj) {
		if (isFzj) {
			strs = changStrs(strs);// 繁转简
		}

		FactoryAndFactualCustomsRalation data = new FactoryAndFactualCustomsRalation();
		Materiel materiel = new Materiel();
		StatCusNameRelationHsn hsn = new StatCusNameRelationHsn();
		
		// ==============工厂资料部分
		materiel.setPtNo(getFileColumnValue(strs, 1));// 料号
		materiel.setFactoryName(getFileColumnValue(strs, 2));// 名称
		materiel.setFactorySpec(getFileColumnValue(strs, 3));// 规格
		
		
		// 净重
		String netWeight = getFileColumnValue(strs, 4);
		if(CommonUtils.notEmpty(netWeight) && RegexUtil.checkFloat(netWeight)) {
			materiel.setPtNetWeight(Double.valueOf(netWeight));
		} else {
			materiel.setPtNetWeight(null);
		}

		// 单价
		String price = getFileColumnValue(strs, 5);
		if(CommonUtils.notEmpty(price) && RegexUtil.checkFloat(price)) {
			materiel.setPtPrice(Double.valueOf(price));
		} else {
			materiel.setPtPrice(null);
		}
			
		data.setTemp1(getFileColumnValue(strs, 6));// 工厂单位

		data.setMateriel(materiel);
		// ==================折算系数
		String unitConvert = getFileColumnValue(strs, 7);
		if(CommonUtils.notEmpty(unitConvert) && RegexUtil.checkFloat(unitConvert)) {
			data.setUnitConvert(Double.valueOf(unitConvert));
		} else {
			data.setUnitConvert(null);
		}
			
			
		// =================报关资料部分
		data.setTemp2(getFileColumnValue(strs, 8));
		hsn.setTemp2(getFileColumnValue(strs, 8));// 报关商品编码
		hsn.setCusName(getFileColumnValue(strs, 9));// 报关名称
		hsn.setCusSpec(getFileColumnValue(strs, 10));// 报关规格
		data.setTemp3(getFileColumnValue(strs, 11));// 报关商品单位
		hsn.setEmsNo(getFileColumnValue(strs, 12));// 手册号
		
		// 序号
		String seqNum = getFileColumnValue(strs, 13);
		if(CommonUtils.notEmpty(seqNum) && RegexUtil.checkInteger(seqNum)) {
			hsn.setSeqNum(Integer.valueOf(seqNum));
		} else {
			hsn.setSeqNum(null);
		}
		// 手册开始日期
		hsn.setEmsBeginDate(convertStrToDate(getFileColumnValue(strs, 14)));
		// 手册结束日期
		hsn.setEmsEndDate(convertStrToDate(getFileColumnValue(strs, 15)));
			
		String pType = getFileColumnValue(strs, 16);// 手册类型
		// System.out.println("pType == "+pType);
		if (pType != null) {
			if (pType.trim().equals("电子化手册") || pType.trim().equals("纸质手册")
					|| pType.trim().equals("BCS")) {
				hsn.setProjectType(ProjectType.BCS);
			} else if (pType.trim().equals("电子手册")
					|| pType.trim().equals("DZSC")) {
				hsn.setProjectType(ProjectType.DZSC);
			} else if (pType.trim().equals("电子账册")
					|| pType.trim().equals("电子帐册")
					|| pType.trim().equals("BCUS")) {
				hsn.setProjectType(ProjectType.BCUS);
			} else {
				hsn.setProjectType(null);
			}
		} else {
			hsn.setProjectType(null);
		}
		hsn.setMaterielType(materielType);// 物料类型
		if (materielType.equals(MaterielType.FINISHED_PRODUCT)) {
			String version = getFileColumnValue(strs, 17);
			if(CommonUtils.notEmpty(version) && RegexUtil.checkInteger(version)) {
				hsn.setVersion(Integer.valueOf(version));
			} else {
				hsn.setVersion(null);
			}
			data.setRemark(getFileColumnValue(strs, 18));
		}else{
			data.setRemark(getFileColumnValue(strs, 17));
		}
		data.setStatCusNameRelationHsn(hsn);
		list.add(data);
	}
	
	private static final DateFormat formate = new SimpleDateFormat("yyyy-MM-dd");

	private JLabel jLabel1 = null;

	private JLabel jLabel6 = null; 
	public static Date convertStrToDate(String dateStr) {
		if (CommonUtils.notEmpty(dateStr)) {
			try {
				return formate.parse(dateStr);
			} catch (ParseException e) {
			}
		} 
		return null;
	}

	/**
	 * This method initializes btnCancel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(353, 336, 85, 28);
			btnCancel.setPreferredSize(new Dimension(85, 28));
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgRelationImportFromFile.this.dispose();
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
			tfImportFile.setEditable(false);
			tfImportFile.setBounds(new Rectangle(76, 32, 158, 21));
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
			btnImportFile.setText("...");
			btnImportFile.setBounds(new Rectangle(230, 33, 34, 20));
			btnImportFile
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							JFileChooser fileChooser = new JFileChooser();
							fileChooser.removeChoosableFileFilter(fileChooser
									.getFileFilter());
							fileChooser.setFileFilter(new CommonFileFilter(
									new String[] { "txt", "xls" }, "选择文档"));
							int state = fileChooser
									.showOpenDialog(DgRelationImportFromFile.this);
							if (state == JFileChooser.APPROVE_OPTION) {
								txtFile = fileChooser.getSelectedFile();
								if (txtFile.exists()) {
									tfImportFile.setText(txtFile.getPath());
									btnFileCorrespondColumn.setEnabled(true);
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
			btnFileCorrespondColumn.setText("设定栏位");
			btnFileCorrespondColumn.setEnabled(false);
			btnFileCorrespondColumn.setBounds(new Rectangle(268, 30, 88, 28));
			btnFileCorrespondColumn
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (txtFile == null) {
								JOptionPane.showMessageDialog(
										DgRelationImportFromFile.this,
										"请选择要导入的文件", "提示", 0);
								return;
							}
							if (!txtFile.exists()) {
								JOptionPane.showMessageDialog(
										DgRelationImportFromFile.this,
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
										DgRelationCorrespondFile dialog = new DgRelationCorrespondFile();// 设定导入资料与文件资料的列对应关系
										dialog.setFileColumnCount(strs.length);
										dialog.setSelectedValues(columnNo);
										dialog.setMaterielType(materielType);
										dialog.setVisible(true);
										int[] ht = dialog
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
											DgRelationCorrespondFile dialog = new DgRelationCorrespondFile();
											dialog.setFileColumnCount(rs
													.getColumns());
											dialog.setSelectedValues(columnNo);
											dialog
													.setMaterielType(materielType);
											dialog.setVisible(true);
											int[] ht = dialog
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
			cbIsTitle.setBounds(58, 27, 175, 22);
			cbIsTitle.setText("文件第一行为标题行");
		}
		return cbIsTitle;
	}

	/**
	 * This method initializes cbIsUpperCase
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIsUpperCase() {
		if (cbIsUpperCase == null) {
			cbIsUpperCase = new JCheckBox();
			cbIsUpperCase.setBounds(new Rectangle(58, 56, 237, 16));
			cbIsUpperCase.setText("将物料编码去尾部空格并且转换成大写");
		}
		return cbIsUpperCase;
	}

	/**
	 * This method initializes cbIsMateriel
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIsMateriel() {
		if (cbIsMateriel == null) {
			cbIsMateriel = new JCheckBox();
			cbIsMateriel.setBounds(new Rectangle(58, 84, 319, 21));
			cbIsMateriel.setText("将导入的文本资料反写至常用物料和实际报关资料");
		}
		return cbIsMateriel;
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
			cbIsComplexToSimplified.setBounds(new Rectangle(58, 111, 71, 21));
			cbIsComplexToSimplified.setText("繁转简");
		}
		return cbIsComplexToSimplified;
	}

	/**
	 * This method initializes rbMaterielAndCustomsOneToOneOrManyToOne
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbMaterielAndCustomsOneToOneOrManyToOne() {
		if (rbMaterielAndCustomsOneToOneOrManyToOne == null) {
			rbMaterielAndCustomsOneToOneOrManyToOne = new JRadioButton();
			rbMaterielAndCustomsOneToOneOrManyToOne.setBounds(new Rectangle(58,
					169, 178, 21));
			rbMaterielAndCustomsOneToOneOrManyToOne.setText("物料与报关一对一或多对一");
			rbMaterielAndCustomsOneToOneOrManyToOne.setSelected(true);
			rbMaterielAndCustomsOneToOneOrManyToOne
					.addActionListener(new java.awt.event.ActionListener() {

						public void actionPerformed(ActionEvent arg0) {
							rbMaterielAndCustomsOneToOneOrManyToOne
									.setSelected(true);
							rbMaterielAndCustomsOneToMany.setSelected(false);
							isOneToMany = false;
						}

					});
		}
		return rbMaterielAndCustomsOneToOneOrManyToOne;
	}

	/**
	 * This method initializes rbMaterielAndCustomsOneToMany
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbMaterielAndCustomsOneToMany() {
		if (rbMaterielAndCustomsOneToMany == null) {
			rbMaterielAndCustomsOneToMany = new JRadioButton();
			rbMaterielAndCustomsOneToMany.setBounds(new Rectangle(57, 202, 175,
					21));
			rbMaterielAndCustomsOneToMany.setText("物料与报关一对多");
			rbMaterielAndCustomsOneToMany
					.addActionListener(new java.awt.event.ActionListener() {

						public void actionPerformed(ActionEvent arg0) {
							rbMaterielAndCustomsOneToOneOrManyToOne
									.setSelected(false);
							rbMaterielAndCustomsOneToMany.setSelected(true);
							isOneToMany = true;
						}

					});
		}
		return rbMaterielAndCustomsOneToMany;
	}

	/**
	 * 获取参数
	 * 
	 * 判断是否覆盖，是否为一对多
	 * 
	 * @return
	 */
	private Map getParameter() {
		parameter.put("isReWriteMateriel", cbIsMateriel.isSelected());
		parameter.put("isOneToMany", isOneToMany);
		parameter.put("isDupImport", cbbCover.isSelected());
		return parameter;
	}

	/**
	 * This method initializes cbbCover
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbbCover() {
		if (cbbCover == null) {
			cbbCover = new JCheckBox();
			cbbCover.setBounds(new Rectangle(58, 142, 280, 21));
			cbbCover.setText("导入数据与对应关系数据一致时，覆盖导入");
		}
		return cbbCover;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(23, 33, 54, 18));
			jLabel2.setText("选择文件");
			jLabel3 = new JLabel();
			jLabel3.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel3.setBounds(new Rectangle(8, 153, 258, 30));
			jLabel3.setText("4、设定栏位:导入数据与文件数据列的对应关系 ");
			TitledBorder titledBorder1 = BorderFactory.createTitledBorder(null,
					"\u9009\u62e9\u6587\u4ef6\u5e76\u8bbe\u7f6e\u680f\u4f4d",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.PLAIN, 12), new Color(51, 51, 51));
			titledBorder1.setTitle("选择文件并设定栏位");
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setBounds(new Rectangle(18, 249, 371, 72));
			jPanel1.setBorder(titledBorder1);
			jPanel1.add(getBtnImportFile(), null);
			jPanel1.add(getTfImportFile(), null);
			jPanel1.add(getBtnFileCorrespondColumn(), null);
			jPanel1.add(jLabel2, null);
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
			jLabel6 = new JLabel();
			jLabel6.setBounds(new Rectangle(8, 233, 258, 46));
			jLabel6
					.setText("<html><body><font color='blue'>6、如选择第5和7项，覆盖导入的主键为料号<br/>+编码+报关名称+报关规格+报关单位+手册号</font></body></html>");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(8, 185, 258, 46));
			jLabel1
					.setText("<html><body><font color='blue'>5、如选择第5和6项，覆盖导入的主键为料号<br/>+编码+报关名称+报关规格+报关单位</font></body></html>");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(10, 127, 255, 18));
			jLabel5.setText("3、第6项与第7项只能选择其一");
			TitledBorder titledBorder2 = BorderFactory.createTitledBorder(null,
					"\u8bf4\u660e", TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.PLAIN, 12), new Color(51, 51, 51));
			titledBorder2.setBorder(null);
			titledBorder2.setTitle("说明");
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.setBounds(new Rectangle(398, 15, 265, 306));
			jPanel2.setBorder(titledBorder2);
			jPanel2.add(jLabel3, null);
			jPanel2.add(getJTextArea(), null);
			jPanel2.add(getJTextArea1(), null);
			jPanel2.add(jLabel5, null);
			jPanel2.add(jLabel1, null);
			jPanel2.add(jLabel6, null);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jTextArea
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getJTextArea() {
		if (jTextArea == null) {
			jTextArea = new JTextArea();
			jTextArea.setBounds(new Rectangle(9, 36, 232, 37));
			jTextArea.setLineWrap(true);
			jTextArea.setText("1、当选择第6项导入报关资料中的名称、规格、单位缺一不可");
			jTextArea.setBackground(javax.swing.UIManager.getDefaults()
					.getColor("Button.background"));
		}
		return jTextArea;
	}

	/**
	 * This method initializes jTextArea1
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getJTextArea1() {
		if (jTextArea1 == null) {
			jTextArea1 = new JTextArea();
			jTextArea1.setBounds(new Rectangle(10, 82, 236, 38));
			jTextArea1.setText("2、当选择第7项导入报关资料中的名称、规格、单位、手册号缺一不可");
			jTextArea1.setLineWrap(true);
			jTextArea1.setFont(new Font("Dialog", Font.PLAIN, 12));
			jTextArea1.setBackground(javax.swing.UIManager.getDefaults()
					.getColor("Button.background"));
		}
		return jTextArea1;
	}
} // @jve:decl-index=0:visual-constraint="-4,10"
