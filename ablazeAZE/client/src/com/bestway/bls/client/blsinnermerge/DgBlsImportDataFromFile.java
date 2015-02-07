/*
 * Created on 2004-7-17
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bls.client.blsinnermerge;

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

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import jxl.Sheet;
import jxl.Workbook;

import com.bestway.bcus.client.common.CommonClientBig5GBConverter;
import com.bestway.bcus.client.common.CommonFileFilter;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonStepProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.FileReading;
import com.bestway.bls.action.BlsInnerMergeAction;
import com.bestway.bls.entity.BlsInnerMergeDataOrder;
import com.bestway.bls.entity.BlsInnerMergeFileData;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author bsway
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class DgBlsImportDataFromFile extends JDialogBase {

	private JPanel jContentPane = null;

	private JPanel jPanel = null;

	private JButton btnOk = null;

	private JButton btnCancel = null;

	// private ButtonGroup buttonGroup = null; //
	// @jve:decl-index=0:parse,visual-constraint="522,16"

	private JTextField tfImportFile = null;

	private JButton btnImportFile = null;

	private File txtFile = null;

	private boolean ok = false;

	// private String materielType = null; // @jve:decl-index=0:

	private Hashtable<Integer, Integer> columnNo = null; // @jve:decl-index=0:

	private JButton btnFileCorrespondColumn = null;

	private JCheckBox cbIsTitle = null;

	private BlsInnerMergeAction blsInnerMergeAction = null;

	/**
	 * This method initializes
	 * 
	 */
	public DgBlsImportDataFromFile() {
		super();
		initialize();
		// getButtonGroup();
		blsInnerMergeAction = (BlsInnerMergeAction) CommonVars
				.getApplicationContext().getBean("blsInnerMergeAction");
		// initStrMap();
		getInitedOrderValue();
	}

	private void getInitedOrderValue() {
		if (columnNo == null) {
			columnNo = new Hashtable<Integer, Integer>();
		}
		BlsInnerMergeDataOrder order = blsInnerMergeAction
				.findBlsInnerMergeDataOrder(new Request(CommonVars
						.getCurrUser(), true));
		columnNo.put(Integer.valueOf(BlsInnerMergeFileData.SERIAL_NO), order
				.getSerialNo() == null ? -1 : order.getSerialNo());
		columnNo.put(Integer
				.valueOf(BlsInnerMergeFileData.BEFORE_MATERIEL_CODE), order
				.getBeforeMaterielCode() == null ? -1 : order
				.getBeforeMaterielCode());
		columnNo.put(Integer
				.valueOf(BlsInnerMergeFileData.BEFORE_TEN_COMPLEX_CODE), order
				.getBeforeTenComplexCode() == null ? -1 : order
				.getBeforeTenComplexCode());
		// columnNo.put(Integer
		// .valueOf(BlsInnerMergeFileData.BEFORE_MATERIEL_NAME_SPEC),
		// order.getBeforeMaterielNameSpec() == null ? -1 : order
		// .getBeforeMaterielNameSpec());
		columnNo.put(Integer
				.valueOf(BlsInnerMergeFileData.BEFORE_MATERIEL_NAME), order
				.getBeforeMaterielName() == null ? -1 : order
				.getBeforeMaterielName());
		columnNo.put(Integer
				.valueOf(BlsInnerMergeFileData.BEFORE_MATERIEL_SPEC), order
				.getBeforeMaterielSpec() == null ? -1 : order
				.getBeforeMaterielSpec());
		columnNo.put(Integer.valueOf(BlsInnerMergeFileData.BEFORE_UNIT), order
				.getBeforeUnit() == null ? -1 : order.getBeforeUnit());
		columnNo.put(Integer
				.valueOf(BlsInnerMergeFileData.BEFORE_ENTERPRISE_UNIT), order
				.getBeforeEnterpriseUnit() == null ? -1 : order
				.getBeforeEnterpriseUnit());
		columnNo.put(Integer.valueOf(BlsInnerMergeFileData.AFTER_TEN_MEMO_NO),
				order.getAfterTenMemoNo() == null ? -1 : order
						.getAfterTenMemoNo());
		columnNo.put(Integer
				.valueOf(BlsInnerMergeFileData.AFTER_TEN_COMPLEX_CODE), order
				.getAfterTenComplexCode() == null ? -1 : order
				.getAfterTenComplexCode());
		// columnNo.put(Integer
		// .valueOf(BlsInnerMergeFileData.AFTER_COMPLEX_NAME_SPEC), order
		// .getAfterComplexlNameSpec() == null ? -1 : order
		// .getAfterComplexlNameSpec());
		columnNo.put(Integer.valueOf(BlsInnerMergeFileData.AFTER_COMPLEX_NAME),
				order.getAfterComplexlName() == null ? -1 : order
						.getAfterComplexlName());
		columnNo.put(Integer.valueOf(BlsInnerMergeFileData.AFTER_COMPLEX_SPEC),
				order.getAfterComplexSpec() == null ? -1 : order
						.getAfterComplexSpec());
		columnNo.put(Integer.valueOf(BlsInnerMergeFileData.AFTER_UNIT), order
				.getAfterUnit() == null ? -1 : order.getAfterUnit());

		columnNo.put(Integer.valueOf(BlsInnerMergeFileData.UNIT_CONVERT), order
				.getUnitConvert() == null ? -1 : order.getUnitConvert());
		columnNo.put(Integer.valueOf(BlsInnerMergeFileData.UNIT_WEIGHT), order
				.getUnitWeight() == null ? -1 : order.getUnitWeight());
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
			jPanel.setLayout(null);
			jPanel.setBounds(36, 18, 392, 204);
			jPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null,
					"", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			jLabel1.setBounds(34, 82, 89, 25);
			jLabel1.setText("导入数据的文件");
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
								DgBlsImportDataFromFile.this, "请选择要导入的文件",
								"提示", 0);
						return;
					}
					if (!txtFile.exists()) {
						JOptionPane.showMessageDialog(
								DgBlsImportDataFromFile.this, "你选择的文件不存在",
								"提示", 0);
						return;
					}
					if (columnNo == null) {
						JOptionPane.showMessageDialog(
								DgBlsImportDataFromFile.this,
								"你没有选择导入数据与文件数据列的对应关系", "提示", 0);
						return;
					}
					// if (rbProduct.isSelected()) {
					// setMaterielType(MaterielType.FINISHED_PRODUCT);
					// } else if (rbMateriel.isSelected()) {
					// setMaterielType(MaterielType.MATERIEL);
					// } else if (rbSemiFinishedProduct.isSelected()) {
					// setMaterielType(MaterielType.SEMI_FINISHED_PRODUCT);
					// }
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
				List invalidationData = blsInnerMergeAction.checkFileData(
						request, list, columnNo);
				if (invalidationData.size() > 0) {
					CommonStepProgress.closeStepProgressDialog();
					DgBlsInnerMergeFileData dgInvalidationFileData = new DgBlsInnerMergeFileData();
					dgInvalidationFileData.setDataSource(invalidationData);
					dgInvalidationFileData.setErrorData(true);
					dgInvalidationFileData.setTitle("无效的数据");
					dgInvalidationFileData.setVisible(true);
					btnOk.setEnabled(true);
					return;
				} else {
					CommonStepProgress.closeStepProgressDialog();
					DgBlsInnerMergeFileData dg = new DgBlsInnerMergeFileData();
					dg.setDataSource(list);
					dg.setErrorData(false);
					dg.setTitle("归并数据浏览");
					dg.setVisible(true);
					if (!dg.isContinue()) {
						btnOk.setEnabled(true);
						return;
					}
				}
				CommonStepProgress.showStepProgressDialog(taskId);
				CommonStepProgress.setStepMessage("系统正在导入资料，请稍后...");
				blsInnerMergeAction.importDataFromTxtFile(request, list);

				// JOptionPane.showMessageDialog(DgImportDataFromFile.this,
				// "导入数据成功！", "提示", 0);

				DgBlsImportDataFromFile.this.dispose();
			} catch (Exception ex) {
				ex.printStackTrace();
				CommonStepProgress.closeStepProgressDialog();
				JOptionPane.showMessageDialog(DgBlsImportDataFromFile.this,
						"导入数据失败！" + ex.getMessage(), "提示",
						JOptionPane.ERROR_MESSAGE);
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
		if (fileRow == null || columnIndex < 0
				|| columnIndex > fileRow.length - 1) {
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
	//
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
		if (source == null) {
			return null;
		}
		String newStrs[] = new String[source.length];
		for (int i = 0, n = source.length; i < n; i++) {
			// newStrs[i] = changeStr(source[i]);
			// System.out.println(newStrs[i]);
			newStrs[i] = CommonClientBig5GBConverter.getInstance()
					.big5ConvertToGB(source[i]);
		}
		return newStrs;
	}

	private List parseTxtFile(File file) {
		ArrayList<BlsInnerMergeFileData> list = new ArrayList<BlsInnerMergeFileData>();
		String[] strs = null;
		if (txtFile.isDirectory() == true) {
			return new ArrayList();
		}
		String suffix = getSuffix(txtFile);
		if (suffix != null) {
			// String materielType = "";
			// if (rbProduct.isSelected()) {
			// materielType = MaterielType.FINISHED_PRODUCT;
			// } else if (rbMateriel.isSelected()) {
			// materielType = MaterielType.MATERIEL;
			// } else if (rbSemiFinishedProduct.isSelected()) {
			// materielType = MaterielType.SEMI_FINISHED_PRODUCT;
			// }
			List lsExsitPtNo = blsInnerMergeAction
					.findExistedMaterialPtNoInInnerMerge(new Request(CommonVars
							.getCurrUser()));
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
				convertToFileData(list, strs, lsExsitPtNo);// materielType,
			}
		}
		return list;
	}

	private void convertToFileData(ArrayList<BlsInnerMergeFileData> list,
			String[] strs, List lsExitPtNo) {// String materielType,
		strs = changStrs(strs);
		BlsInnerMergeFileData data = new BlsInnerMergeFileData();
		// if (rbProduct.isSelected()) {
		// data.setMaterielType(MaterielType.FINISHED_PRODUCT);
		// } else if (rbMateriel.isSelected()) {
		// data.setMaterielType(MaterielType.MATERIEL);
		// } else if (rbSemiFinishedProduct.isSelected()) {
		// data.setMaterielType(MaterielType.SEMI_FINISHED_PRODUCT);
		// }
		// data.setMaterielType(materielType);
		data.setSerialNo(getFileColumnValue(strs,
				BlsInnerMergeFileData.SERIAL_NO));// ;strs[0].trim());
		if (cbUpperCase.isSelected()) {
			data.setBeforeMaterielCode(getFileColumnValue(strs,
					BlsInnerMergeFileData.BEFORE_MATERIEL_CODE).trim()
					.toUpperCase());
		} else {
			data.setBeforeMaterielCode(getFileColumnValue(strs,
					BlsInnerMergeFileData.BEFORE_MATERIEL_CODE));
		}
//		if (lsExitPtNo.contains(data.getBeforeMaterielCode())) {
//			return;
//		}
		String beforeTenComplexCode = getFileColumnValue(strs,
				BlsInnerMergeFileData.BEFORE_TEN_COMPLEX_CODE);
		// if ((beforeTenComplexCode != null)
		// && (beforeTenComplexCode.length() == 10)
		// && (beforeTenComplexCode.substring(8).equals("00"))) {
		// beforeTenComplexCode = beforeTenComplexCode.substring(0, 8);
		// }//海关编码已经升级成10位，不需要再加上些判断
		data.setBeforeTenComplexCode(beforeTenComplexCode);
		// String s1 = getFileColumnValue(strs,
		// BlsInnerMergeFileData.BEFORE_MATERIEL_NAME_SPEC);
		// if (s1 != null && !"".equals(s1.trim())) {
		// int x = s1.indexOf(tfSpaceSymbol.getText().trim());
		// if (x == -1) {
		// data.setBeforeMaterielName(s1);
		// } else {
		// data.setBeforeMaterielName(s1.substring(0, x).trim());
		// data.setBeforeMaterielSpec(s1.substring(x + 1, s1.length()));
		// }
		// } else {
		data.setBeforeMaterielName(getFileColumnValue(strs,
				BlsInnerMergeFileData.BEFORE_MATERIEL_NAME));
		data.setBeforeMaterielSpec(getFileColumnValue(strs,
				BlsInnerMergeFileData.BEFORE_MATERIEL_SPEC));
		// }
		data.setBeforeUnit(getFileColumnValue(strs,
				BlsInnerMergeFileData.BEFORE_UNIT));
		data.setBeforeEnterpriseUnit(getFileColumnValue(strs,
				BlsInnerMergeFileData.BEFORE_ENTERPRISE_UNIT));
		data.setAfterTenMemoNo(getFileColumnValue(strs,
				BlsInnerMergeFileData.AFTER_TEN_MEMO_NO));
		String afterTenComplexCode = getFileColumnValue(strs,
				BlsInnerMergeFileData.AFTER_TEN_COMPLEX_CODE);
		// if (afterTenComplexCode != null && afterTenComplexCode.length() == 10
		// && afterTenComplexCode.substring(8).equals("00")) {
		// afterTenComplexCode = afterTenComplexCode.substring(0, 8);
		// }//海关编码已经升级成10位，不需要再加上些判断
		data.setAfterTenComplexCode(afterTenComplexCode);

		// String s2 = getFileColumnValue(strs,
		// BlsInnerMergeFileData.AFTER_COMPLEX_NAME_SPEC);
		// if (s2 != null && !"".equals(s2.trim())) {
		// int x = s2.indexOf(tfSpaceSymbol.getText().trim());
		// if (x == -1) {
		// data.setAfterComplexlName(s2);
		// } else {
		// data.setAfterComplexlName(s2.substring(0, x).trim());
		// data.setAfterComplexSpec(s2.substring(x + 1, s2.length()));
		// }
		// } else {
		data.setAfterComplexlName(getFileColumnValue(strs,
				BlsInnerMergeFileData.AFTER_COMPLEX_NAME));
		data.setAfterComplexSpec(getFileColumnValue(strs,
				BlsInnerMergeFileData.AFTER_COMPLEX_SPEC));
		// }
		data.setAfterUnit(getFileColumnValue(strs,
				BlsInnerMergeFileData.AFTER_UNIT));

		data.setUnitConvert(getFileColumnValue(strs,
				BlsInnerMergeFileData.UNIT_CONVERT));
		data.setUnitWeight(getFileColumnValue(strs,
				BlsInnerMergeFileData.UNIT_WEIGHT));
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
					DgBlsImportDataFromFile.this.dispose();
					setOk(false);
				}
			});
		}
		return btnCancel;
	}

	// /**
	// * This method initializes buttonGroup
	// *
	// * @return javax.swing.ButtonGroup
	// */
	// private ButtonGroup getButtonGroup() {
	// if (buttonGroup == null) {
	// buttonGroup = new ButtonGroup();
	// buttonGroup.add(getRbProduct());
	// buttonGroup.add(getRbSemiFinishedProduct());
	// buttonGroup.add(getRbMateriel());
	// }
	// return buttonGroup;
	// }

	/**
	 * This method initializes tfImportFile
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfImportFile() {
		if (tfImportFile == null) {
			tfImportFile = new JTextField();
			tfImportFile.setBounds(124, 83, 220, 22);
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
			btnImportFile.setBounds(344, 83, 24, 22);
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
									.showOpenDialog(DgBlsImportDataFromFile.this);
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
			btnFileCorrespondColumn.setBounds(34, 113, 334, 21);
			btnFileCorrespondColumn.setText("设定导入资料与文件资料的列对应关系");
			btnFileCorrespondColumn
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (txtFile == null) {
								JOptionPane.showMessageDialog(
										DgBlsImportDataFromFile.this,
										"请选择要导入的文件", "提示", 0);
								return;
							}
							if (!txtFile.exists()) {
								JOptionPane.showMessageDialog(
										DgBlsImportDataFromFile.this,
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
										DgBlsCorrespondFile dialog = new DgBlsCorrespondFile();
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
											DgBlsCorrespondFile dialog = new DgBlsCorrespondFile();
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

	// /**
	// * @return Returns the materielType.
	// */
	// public String getMaterielType() {
	// return materielType;
	// }
	//
	// /**
	// * @param materielType
	// * The materielType to set.
	// */
	// public void setMaterielType(String materielType) {
	// this.materielType = materielType;
	// }

	/**
	 * This method initializes cbIsTitle
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIsTitle() {
		if (cbIsTitle == null) {
			cbIsTitle = new JCheckBox();
			cbIsTitle.setBounds(31, 35, 168, 22);
			cbIsTitle.setActionCommand("文件第一行为标题行");
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
			cbUpperCase.setBounds(new Rectangle(31, 59, 343, 16));
			cbUpperCase.setText("是否将物料编码去尾部空格并且转换成大写");
		}
		return cbUpperCase;
	}
} // @jve:decl-index=0:visual-constraint="10,10"

