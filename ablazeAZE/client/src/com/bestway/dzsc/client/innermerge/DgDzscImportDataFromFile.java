/*
 * Created on 2004-7-17
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.client.innermerge;

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
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import jxl.Sheet;
import jxl.Workbook;

import com.bestway.bcus.client.common.CommonClientBig5GBConverter;
import com.bestway.bcus.client.common.CommonFileFilter;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonStepProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.FileReading;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.dzsc.innermerge.action.DzscInnerMergeAction;
import com.bestway.dzsc.innermerge.entity.DzscInnerMergeDataOrder;
import com.bestway.dzsc.innermerge.entity.DzscInnerMergeFileData;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author bsway
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgDzscImportDataFromFile extends JDialogBase {

	private JPanel jContentPane = null;

	private JPanel jPanel = null;

	private JButton btnOk = null;

	private JButton btnCancel = null;

	private JRadioButton rbProduct = null;

	private JRadioButton rbSemiFinishedProduct = null;

	private JRadioButton rbMateriel = null;

	private ButtonGroup buttonGroup = null;

	private JPanel jPanel1 = null;

	private JTextField tfSpaceSymbol = null;

	private JTextField tfImportFile = null;

	private JButton btnImportFile = null;

	private File txtFile = null;

	private boolean ok = false;

	private String materielType = null;

	private Hashtable<Integer, Integer> columnNo = null; // @jve:decl-index=0:

	private JButton btnFileCorrespondColumn = null;

	private JCheckBox cbIsTitle = null;

	private DzscInnerMergeAction dzscInnerMergeAction = null;

	/**
	 * This method initializes
	 * 
	 */
	public DgDzscImportDataFromFile() {
		super();
		initialize();
		getButtonGroup();
		dzscInnerMergeAction = (DzscInnerMergeAction) CommonVars
				.getApplicationContext().getBean("dzscInnerMergeAction");
		// initStrMap();
		getInitedOrderValue();
	}

	private void getInitedOrderValue() {
		if (columnNo == null) {
			columnNo = new Hashtable<Integer, Integer>();
		}
		DzscInnerMergeDataOrder order = dzscInnerMergeAction
				.findDzscInnerMergeDataOrder(new Request(CommonVars
						.getCurrUser(), true));
		columnNo.put(Integer.valueOf(DzscInnerMergeFileData.SERIAL_NO), order
				.getSerialNo() == null ? -1 : order.getSerialNo());
		columnNo.put(Integer
				.valueOf(DzscInnerMergeFileData.BEFORE_MATERIEL_CODE), order
				.getBeforeMaterielCode() == null ? -1 : order
				.getBeforeMaterielCode());
		columnNo.put(Integer
				.valueOf(DzscInnerMergeFileData.BEFORE_TEN_COMPLEX_CODE), order
				.getBeforeTenComplexCode() == null ? -1 : order
				.getBeforeTenComplexCode());
		columnNo.put(Integer
				.valueOf(DzscInnerMergeFileData.BEFORE_MATERIEL_NAME_SPEC),
				order.getBeforeMaterielNameSpec() == null ? -1 : order
						.getBeforeMaterielNameSpec());
		columnNo.put(Integer
				.valueOf(DzscInnerMergeFileData.BEFORE_MATERIEL_NAME), order
				.getBeforeMaterielName() == null ? -1 : order
				.getBeforeMaterielName());
		columnNo.put(Integer
				.valueOf(DzscInnerMergeFileData.BEFORE_MATERIEL_SPEC), order
				.getBeforeMaterielSpec() == null ? -1 : order
				.getBeforeMaterielSpec());
		columnNo.put(Integer.valueOf(DzscInnerMergeFileData.BEFORE_UNIT), order
				.getBeforeUnit() == null ? -1 : order.getBeforeUnit());
		columnNo.put(Integer
				.valueOf(DzscInnerMergeFileData.BEFORE_ENTERPRISE_UNIT), order
				.getBeforeEnterpriseUnit() == null ? -1 : order
				.getBeforeEnterpriseUnit());
		columnNo.put(Integer.valueOf(DzscInnerMergeFileData.AFTER_TEN_MEMO_NO),
				order.getAfterTenMemoNo() == null ? -1 : order
						.getAfterTenMemoNo());
		columnNo.put(Integer
				.valueOf(DzscInnerMergeFileData.AFTER_TEN_COMPLEX_CODE), order
				.getAfterTenComplexCode() == null ? -1 : order
				.getAfterTenComplexCode());
		columnNo.put(Integer
				.valueOf(DzscInnerMergeFileData.AFTER_COMPLEX_NAME_SPEC), order
				.getAfterComplexlNameSpec() == null ? -1 : order
				.getAfterComplexlNameSpec());
		columnNo.put(
				Integer.valueOf(DzscInnerMergeFileData.AFTER_COMPLEX_NAME),
				order.getAfterComplexlName() == null ? -1 : order
						.getAfterComplexlName());
		columnNo.put(
				Integer.valueOf(DzscInnerMergeFileData.AFTER_COMPLEX_SPEC),
				order.getAfterComplexSpec() == null ? -1 : order
						.getAfterComplexSpec());
		columnNo.put(Integer.valueOf(DzscInnerMergeFileData.AFTER_UNIT), order
				.getAfterUnit() == null ? -1 : order.getAfterUnit());
		columnNo.put(Integer.valueOf(DzscInnerMergeFileData.FOUR_NO), order
				.getFourNo() == null ? -1 : order.getFourNo());
		columnNo.put(Integer.valueOf(DzscInnerMergeFileData.FOUR_COMPLEX_NAME),
				order.getFourComplexName() == null ? -1 : order
						.getFourComplexName());
		columnNo.put(Integer.valueOf(DzscInnerMergeFileData.FOUR_COMPLEX_CODE),
				order.getFourComplexCode() == null ? -1 : order
						.getFourComplexCode());
		columnNo.put(Integer.valueOf(DzscInnerMergeFileData.FOUR_UNIT), order
				.getFourUnit() == null ? -1 : order.getFourUnit());
		columnNo.put(Integer.valueOf(DzscInnerMergeFileData.UNIT_CONVERT),
				order.getUnitConvert() == null ? -1 : order.getUnitConvert());
		columnNo.put(Integer.valueOf(DzscInnerMergeFileData.UNIT_WEIGHT), order
				.getUnitWeight() == null ? -1 : order.getUnitWeight());
		columnNo.put(Integer.valueOf(DzscInnerMergeFileData.LEGAL_UNIT_GENE),
				order.getLegalUnitGene() == null ? -1 : order
						.getLegalUnitGene());
		columnNo.put(Integer.valueOf(DzscInnerMergeFileData.LEGAL_UNIT2_GENE),
				order.getLegalUnit2Gene() == null ? -1 : order
						.getLegalUnit2Gene());
		columnNo.put(Integer.valueOf(DzscInnerMergeFileData.WEIGHT_UNIT_GENE),
				order.getWeigthUnitGene() == null ? -1 : order
						.getWeigthUnitGene());
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("内部归并数据导入");
		this.setContentPane(getJContentPane());
		this.setSize(477, 303);

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
			jPanel.setBounds(36, 18, 392, 204);
			jPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null,
					"", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			jLabel.setBounds(34, 78, 101, 15);
			jLabel.setText("名称规格分隔符号");
			jLabel1.setBounds(34, 124, 89, 25);
			jLabel1.setText("导入数据的文件");
			jPanel.add(getJPanel1(), null);
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
			btnOk.setBounds(254, 238, 69, 23);
			btnOk.setText("确定");
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (txtFile == null) {
						JOptionPane.showMessageDialog(
								DgDzscImportDataFromFile.this, "请选择要导入的文件",
								"提示", 0);
						return;
					}
					if (!txtFile.exists()) {
						JOptionPane.showMessageDialog(
								DgDzscImportDataFromFile.this, "你选择的文件不存在",
								"提示", 0);
						return;
					}
					if (columnNo == null) {
						JOptionPane.showMessageDialog(
								DgDzscImportDataFromFile.this,
								"你没有选择导入数据与文件数据列的对应关系", "提示", 0);
						return;
					}
					if (rbProduct.isSelected()) {
						setMaterielType(MaterielType.FINISHED_PRODUCT);
					} else if (rbMateriel.isSelected()) {
						setMaterielType(MaterielType.MATERIEL);
					} else if (rbSemiFinishedProduct.isSelected()) {
						setMaterielType(MaterielType.SEMI_FINISHED_PRODUCT);
					}
					new ImportFileDataRunnable().start();
				}
			});
		}
		return btnOk;
	}

	class ImportFileDataRunnable extends Thread {
		public void run() {
			try {
				btnOk.setEnabled(false);
				String taskId = CommonProgress.getExeTaskId();
				CommonStepProgress.showStepProgressDialog(taskId);
				CommonStepProgress.setStepMessage("系统正在读取文件资料，请稍后...");
				// long begin=System.currentTimeMillis();
				List list = parseTxtFile(txtFile);
				// long ss=System.currentTimeMillis()-begin;
				// System.out.println("-----------diff:"+ss);
				Request request = new Request(CommonVars.getCurrUser());
				request.setTaskId(taskId);
				CommonStepProgress.setStepMessage("系统正在检查文件资料，请稍后...");
				List invalidationData = dzscInnerMergeAction.checkFileData(
						request, list, columnNo);
				if (invalidationData.size() > 0) {
					CommonStepProgress.closeStepProgressDialog();
					DgDzscInnerMergeFileData dg = new DgDzscInnerMergeFileData();
					dg.setDataSource(invalidationData);
					dg.setErrorData(true);
					dg.setTitle("无效的数据");
					dg.setVisible(true);
					btnOk.setEnabled(true);
					System.out.println("导入为无效数据－－－－－－－－－－－－－－");
					return;
				} else {
					CommonStepProgress.closeStepProgressDialog();
					DgDzscInnerMergeFileData dg = new DgDzscInnerMergeFileData();
					dg.setDataSource(list);
					dg.setErrorData(false);
					dg.setTitle("归并数据浏览");
					dg.setVisible(true);
					if (!dg.isContinue()) {
						System.out.println("取消导入－－－－－－－－－－－－－－");
						btnOk.setEnabled(true);
						return;
					}
				}
				System.out.println("继续导入－－－－－－－－－－－－－－1");
				CommonStepProgress.showStepProgressDialog(taskId);
				CommonStepProgress.setStepMessage("系统正在导入资料，请稍后...");
				System.out.println("继续导入－－－－－－－－－－－－－－2");
				dzscInnerMergeAction.importDataFromTxtFile(request, list);
				System.out.println("继续导入－－－－－－－－－－－－－－3");
				setOk(true);
				CommonStepProgress.closeStepProgressDialog();
				JOptionPane.showMessageDialog(DgDzscImportDataFromFile.this,
						"导入数据成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
				DgDzscImportDataFromFile.this.dispose();
			} catch (Exception ex) {
				CommonStepProgress.closeStepProgressDialog();
				JOptionPane.showMessageDialog(DgDzscImportDataFromFile.this,
						"导入数据失败！" + ex.getMessage(), "提示",
						JOptionPane.ERROR_MESSAGE);
			} finally {
				btnOk.setEnabled(true);
				CommonStepProgress.closeStepProgressDialog();
			}
		}
	}

	private String getFileColumnValue(String[] fileRow, int dataIndex) {
		if (columnNo.get(Integer.valueOf(dataIndex)) == null) {
			return "";
		}
		int columnIndex = columnNo.get(Integer.valueOf(dataIndex));
		if (columnIndex < 0 || columnIndex > fileRow.length - 1) {
			return "";
		} else {
			return fileRow[columnIndex];
		}
	}

	// Map<String, String> strMap = new HashMap<String, String>();

	private JCheckBox cbUpperCase = null;

	// private void initStrMap() {
	// List list = CustomBaseList.getInstance().getGbtobigs();
	// for (int i = 0; i < list.size(); i++) {
	// Gbtobig gb = (Gbtobig) list.get(i);
	// strMap.put(gb.getBigname(), gb.getName());
	// }
	// }

	// private String changeStr(String s) {
	// String yy = "";
	// char[] xxx = s.toCharArray();
	// for (int i = 0; i < xxx.length; i++) {
	// String z = String.valueOf(xxx[i]);
	// if (String.valueOf(xxx[i]).getBytes().length == 2) {
	// if (strMap.get(String.valueOf(xxx[i])) != null) {
	// z = (String) strMap.get(String.valueOf(xxx[i]));
	// }
	// }
	// yy = yy + z;
	// }
	// return yy;
	// }

	private String[] changStrs(String[] source) {
		String newStrs[] = new String[source.length];
		for (int i = 0, n = source.length; i < n; i++) {
			// newStrs[i] = changeStr(source[i]);
			// System.out.println(newStrs[i]);
			newStrs[i] = CommonClientBig5GBConverter.getInstance().big5ConvertToGB(
					source[i]);
		}
		return newStrs;
	}

	private List parseTxtFile(File file) {
		ArrayList<DzscInnerMergeFileData> list = new ArrayList<DzscInnerMergeFileData>();
		String[] strs = null;
		if (txtFile.isDirectory() == true) {
			return new ArrayList();
		}
		String suffix = getSuffix(txtFile);
		if (suffix != null) {
			String materielType = "";
			if (rbProduct.isSelected()) {
				materielType = MaterielType.FINISHED_PRODUCT;
			} else if (rbMateriel.isSelected()) {
				materielType = MaterielType.MATERIEL;
			} else if (rbSemiFinishedProduct.isSelected()) {
				materielType = MaterielType.SEMI_FINISHED_PRODUCT;
			}
			List lsExsitPtNo = dzscInnerMergeAction
					.findExistedMaterialPtNoInInnerMerge(new Request(CommonVars
							.getCurrUser()), materielType);
			List<List> lines = new ArrayList<List>();
			if (suffix.toLowerCase().equals("txt")) {
				lines = FileReading.readTxt(txtFile, cbIsTitle.isSelected() ? 2
						: 1, null);
			} else if (suffix.toLowerCase().equals("xls")) {
				lines = FileReading.readExcel(txtFile,
						cbIsTitle.isSelected() ? 2 : 1, null);
			}
			for (int i = 0; i < lines.size(); i++) {
				List line = lines.get(i);
				strs = new String[line.size()];
				for (int j = 0; j < line.size(); j++) {
					Object obj = line.get(j);
					strs[j] = (obj == null ? "" : obj.toString());
				}
				convertToFileData(list, strs, materielType, lsExsitPtNo);
			}
		}
		return list;
	}

	private void convertToFileData(ArrayList<DzscInnerMergeFileData> list,
			String[] strs, String materielType, List lsExitPtNo) {
		strs = changStrs(strs);
		DzscInnerMergeFileData data = new DzscInnerMergeFileData();
		// if (rbProduct.isSelected()) {
		// data.setMaterielType(MaterielType.FINISHED_PRODUCT);
		// } else if (rbMateriel.isSelected()) {
		// data.setMaterielType(MaterielType.MATERIEL);
		// } else if (rbSemiFinishedProduct.isSelected()) {
		// data.setMaterielType(MaterielType.SEMI_FINISHED_PRODUCT);
		// }
		data.setMaterielType(materielType);
		data.setSerialNo(getFileColumnValue(strs,
				DzscInnerMergeFileData.SERIAL_NO));// ;strs[0].trim());
		if (cbUpperCase.isSelected()) {
			data.setBeforeMaterielCode(getFileColumnValue(strs,
					DzscInnerMergeFileData.BEFORE_MATERIEL_CODE).trim()
					.toUpperCase());
		} else {
			data.setBeforeMaterielCode(getFileColumnValue(strs,
					DzscInnerMergeFileData.BEFORE_MATERIEL_CODE));
		}
		if (lsExitPtNo.contains(data.getBeforeMaterielCode())) {
			return;
		}
		String beforeTenComplexCode = getFileColumnValue(strs,
				DzscInnerMergeFileData.BEFORE_TEN_COMPLEX_CODE);
//		if ((beforeTenComplexCode != null)
//				&& (beforeTenComplexCode.length() == 10)
//				&& (beforeTenComplexCode.substring(8).equals("00"))) {
//			beforeTenComplexCode = beforeTenComplexCode.substring(0, 8);
//		}
		data.setBeforeTenComplexCode(beforeTenComplexCode);
		String s1 = getFileColumnValue(strs,
				DzscInnerMergeFileData.BEFORE_MATERIEL_NAME_SPEC);
		if (s1 != null && !"".equals(s1.trim())) {
			int x = s1.indexOf(tfSpaceSymbol.getText().trim());
			if (x == -1) {
				data.setBeforeMaterielName(s1);
			} else {
				data.setBeforeMaterielName(s1.substring(0, x).trim());
				data.setBeforeMaterielSpec(s1.substring(x + 1, s1.length()));
			}
		} else {
			data.setBeforeMaterielName(getFileColumnValue(strs,
					DzscInnerMergeFileData.BEFORE_MATERIEL_NAME));
			data.setBeforeMaterielSpec(getFileColumnValue(strs,
					DzscInnerMergeFileData.BEFORE_MATERIEL_SPEC));
		}
		data.setBeforeUnit(getFileColumnValue(strs,
				DzscInnerMergeFileData.BEFORE_UNIT));
		data.setBeforeEnterpriseUnit(getFileColumnValue(strs,
				DzscInnerMergeFileData.BEFORE_ENTERPRISE_UNIT));
		data.setAfterTenMemoNo(getFileColumnValue(strs,
				DzscInnerMergeFileData.AFTER_TEN_MEMO_NO));
		String afterTenComplexCode = getFileColumnValue(strs,
				DzscInnerMergeFileData.AFTER_TEN_COMPLEX_CODE);
//		if (afterTenComplexCode != null && afterTenComplexCode.length() == 10
//				&& afterTenComplexCode.substring(8).equals("00")) {
//			afterTenComplexCode = afterTenComplexCode.substring(0, 8);
//		}
		data.setAfterTenComplexCode(afterTenComplexCode);

		String s2 = getFileColumnValue(strs,
				DzscInnerMergeFileData.AFTER_COMPLEX_NAME_SPEC);
		if (s2 != null && !"".equals(s2.trim())) {
			int x = s2.indexOf(tfSpaceSymbol.getText().trim());
			if (x == -1) {
				data.setAfterComplexlName(s2);
			} else {
				data.setAfterComplexlName(s2.substring(0, x).trim());
				data.setAfterComplexSpec(s2.substring(x + 1, s2.length()));
			}
		} else {
			data.setAfterComplexlName(getFileColumnValue(strs,
					DzscInnerMergeFileData.AFTER_COMPLEX_NAME));
			data.setAfterComplexSpec(getFileColumnValue(strs,
					DzscInnerMergeFileData.AFTER_COMPLEX_SPEC));
		}
		data.setAfterUnit(getFileColumnValue(strs,
				DzscInnerMergeFileData.AFTER_UNIT));
		data
				.setFourNo(getFileColumnValue(strs,
						DzscInnerMergeFileData.FOUR_NO));
		data.setFourComplexName(getFileColumnValue(strs,
				DzscInnerMergeFileData.FOUR_COMPLEX_NAME));
		data.setFourComplexCode(getFileColumnValue(strs,
				DzscInnerMergeFileData.FOUR_COMPLEX_CODE));
		data.setFourUnit(getFileColumnValue(strs,
				DzscInnerMergeFileData.FOUR_UNIT));

		data.setUnitConvert(getFileColumnValue(strs,
				DzscInnerMergeFileData.UNIT_CONVERT));
		data.setUnitWeight(getFileColumnValue(strs,
				DzscInnerMergeFileData.UNIT_WEIGHT));

		data.setLegalUnitGene(getFileColumnValue(strs,
				DzscInnerMergeFileData.LEGAL_UNIT_GENE));
		data.setLegalUnit2Gene(getFileColumnValue(strs,
				DzscInnerMergeFileData.LEGAL_UNIT2_GENE));
		data.setWeigthUnitGene(getFileColumnValue(strs,
				DzscInnerMergeFileData.WEIGHT_UNIT_GENE));
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
			btnCancel.setBounds(342, 238, 69, 23);
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgDzscImportDataFromFile.this.dispose();
					setOk(false);
				}
			});
		}
		return btnCancel;
	}

	/**
	 * This method initializes rbProduct
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbProduct() {
		if (rbProduct == null) {
			rbProduct = new JRadioButton();
			rbProduct.setBounds(30, 18, 51, 21);
			rbProduct.setText("成品");
			rbProduct.setSelected(true);
		}
		return rbProduct;
	}

	/**
	 * This method initializes rbSemiFinishedProduct
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbSemiFinishedProduct() {
		if (rbSemiFinishedProduct == null) {
			rbSemiFinishedProduct = new JRadioButton();
			rbSemiFinishedProduct.setBounds(178, 19, 71, 21);
			rbSemiFinishedProduct.setText("半成品");
			rbSemiFinishedProduct.setVisible(false);
		}
		return rbSemiFinishedProduct;
	}

	/**
	 * This method initializes rbMateriel
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbMateriel() {
		if (rbMateriel == null) {
			rbMateriel = new JRadioButton();
			rbMateriel.setBounds(167, 19, 54, 21);
			rbMateriel.setText("料件");
		}
		return rbMateriel;
	}

	/**
	 * This method initializes buttonGroup
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup() {
		if (buttonGroup == null) {
			buttonGroup = new ButtonGroup();
			buttonGroup.add(getRbProduct());
			buttonGroup.add(getRbSemiFinishedProduct());
			buttonGroup.add(getRbMateriel());
		}
		return buttonGroup;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setBounds(35, 18, 323, 47);
			jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(
					null, "需要导入的物料类别",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION,
					new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12),
					new java.awt.Color(51, 51, 51)));
			jPanel1.add(getRbProduct(), null);
			jPanel1.add(getRbMateriel(), null);
			jPanel1.add(getRbSemiFinishedProduct(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes tfSpaceSymbol
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfSpaceSymbol() {
		if (tfSpaceSymbol == null) {
			tfSpaceSymbol = new JTextField();
			tfSpaceSymbol.setBounds(142, 75, 45, 22);
			tfSpaceSymbol.setText("/");
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
			tfImportFile.setBounds(124, 125, 220, 22);
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
			btnImportFile.setBounds(344, 125, 24, 22);
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
									.showOpenDialog(DgDzscImportDataFromFile.this);
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
			btnFileCorrespondColumn.setBounds(34, 155, 334, 21);
			btnFileCorrespondColumn.setText("设定导入资料与文件资料的列对应关系");
			btnFileCorrespondColumn
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (txtFile == null) {
								JOptionPane.showMessageDialog(
										DgDzscImportDataFromFile.this,
										"请选择要导入的文件", "提示", 0);
								return;
							}
							if (!txtFile.exists()) {
								JOptionPane.showMessageDialog(
										DgDzscImportDataFromFile.this,
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
										DgDzscCorrespondFile dialog = new DgDzscCorrespondFile();
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
											DgDzscCorrespondFile dialog = new DgDzscCorrespondFile();
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
	 * @return Returns the materielType.
	 */
	public String getMaterielType() {
		return materielType;
	}

	/**
	 * @param materielType
	 *            The materielType to set.
	 */
	public void setMaterielType(String materielType) {
		this.materielType = materielType;
	}

	/**
	 * This method initializes cbIsTitle
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIsTitle() {
		if (cbIsTitle == null) {
			cbIsTitle = new JCheckBox();
			cbIsTitle.setBounds(205, 75, 168, 22);
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
			cbUpperCase.setBounds(new java.awt.Rectangle(31, 101, 343, 16));
			cbUpperCase.setText("是否将物料编码去尾部空格并且转换成大写");
		}
		return cbUpperCase;
	}
} // @jve:decl-index=0:visual-constraint="10,10"

