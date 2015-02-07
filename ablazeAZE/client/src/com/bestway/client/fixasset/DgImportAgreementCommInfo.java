package com.bestway.client.fixasset;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EtchedBorder;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import com.bestway.bcus.client.common.CommonFileFilter;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.fixasset.action.FixAssetAction;
import com.bestway.fixasset.entity.Agreement;
import com.bestway.fixasset.entity.TempImportAgreementCommInfo;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgImportAgreementCommInfo extends JDialogBase {

	private JPanel jContentPane = null;

	private JLabel jLabel = null;

	private JComboBox cbbMainNo = null;

	private JComboBox cbbSecNo = null;

	private JComboBox cbbCommCode = null;

	private JComboBox cbbCommName = null;

	private JComboBox cbbCommSpec = null;

	private JComboBox cbbUnit = null;

	private JComboBox cbbAmount = null;

	private JComboBox cbbUnitPrice = null;

	private JComboBox cbbTotalPrice = null;

	private JComboBox cbbCountry = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel11 = null;

	private JLabel jLabel12 = null;

	private JLabel jLabel13 = null;

	private JLabel jLabel14 = null;

	private JLabel jLabel15 = null;

	private JLabel jLabel16 = null;

	private JLabel jLabel17 = null;

	private JLabel jLabel18 = null;

	private JPanel pnCorrespond = null; // @jve:decl-index=0:visual-constraint="592,166"

	private JPanel pnFile = null; // @jve:decl-index=0:visual-constraint="596,19"

	private JTextField tfImportFile = null;

	private JButton jButton2 = null;

	private JPanel jPanel2 = null;

	private JLabel lbInfo = null;

	private JPanel pnNave = null;

	private JButton btnPre = null;

	private JButton btnNext = null;

	private JButton btnCancel = null;

	private JPanel pnContent = null;

	private JPanel pnCommInfo = null; // @jve:decl-index=0:visual-constraint="589,414"

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;

	private int step = 0;

	private File file = null;

	private boolean isChange = false;// 是否是变更

	private boolean isAddMoney = false;// 是否增资

	private Agreement agreement = null;

	private FixAssetAction fixAssetAction;

	private int fileColumnCount = 0;

	private JTableListModel tableModelCommInfo = null;

	private JCheckBox cbIsTitle = null;

	public Agreement getAgreement() {
		return agreement;
	}

	public void setAgreement(Agreement agreement) {
		this.agreement = agreement;
	}

	public boolean isChange() {
		return isChange;
	}

	public void setChange(boolean isChange) {
		this.isChange = isChange;
	}

	public boolean isAddMoney() {
		return isAddMoney;
	}

	public void setAddMoney(boolean isAddMoney) {
		this.isAddMoney = isAddMoney;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgImportAgreementCommInfo() {
		super();
		initialize();
		fixAssetAction = (FixAssetAction) CommonVars.getApplicationContext()
				.getBean("fixAssetAction");
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(562, 396));
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("设备资料导入");
		this.setContentPane(getJContentPane());

	}

	public void setVisible(boolean b) {
		if (b) {
			step();
		}
		super.setVisible(b);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel18 = new JLabel();
			jLabel18.setText("原产国");
			jLabel18.setBounds(new Rectangle(232, 141, 64, 22));
			jLabel17 = new JLabel();
			jLabel17.setText("金额");
			jLabel17.setBounds(new Rectangle(232, 113, 64, 22));
			jLabel16 = new JLabel();
			jLabel16.setText("单价");
			jLabel16.setBounds(new Rectangle(233, 86, 64, 22));
			jLabel15 = new JLabel();
			jLabel15.setText("数量");
			jLabel15.setBounds(new Rectangle(232, 57, 64, 22));
			jLabel14 = new JLabel();
			jLabel14.setText("单位");
			jLabel14.setBounds(new Rectangle(232, 31, 64, 22));
			jLabel13 = new JLabel();
			jLabel13.setText("型号规格");
			jLabel13.setBounds(new Rectangle(34, 139, 64, 22));
			jLabel12 = new JLabel();
			jLabel12.setText("商品名称");
			jLabel12.setBounds(new Rectangle(34, 114, 64, 22));
			jLabel11 = new JLabel();
			jLabel11.setText("商品编码");
			jLabel11.setBounds(new Rectangle(34, 89, 64, 22));
			jLabel1 = new JLabel();
			jLabel1.setText("次序号");
			jLabel1.setBounds(new Rectangle(34, 59, 64, 22));
			jLabel = new JLabel();
			jLabel.setText("主序号");
			jLabel.setBounds(new Rectangle(34, 30, 64, 22));
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJPanel2(), null);
			jContentPane.add(getPnNave(), null);
			jContentPane.add(getPnContent(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbMainNo() {
		if (cbbMainNo == null) {
			cbbMainNo = new JComboBox();
			cbbMainNo.setBounds(new Rectangle(105, 30, 119, 24));
		}
		return cbbMainNo;
	}

	/**
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbSecNo() {
		if (cbbSecNo == null) {
			cbbSecNo = new JComboBox();
			cbbSecNo.setBounds(new Rectangle(105, 57, 119, 24));
		}
		return cbbSecNo;
	}

	/**
	 * This method initializes jComboBox11
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCommCode() {
		if (cbbCommCode == null) {
			cbbCommCode = new JComboBox();
			cbbCommCode.setBounds(new Rectangle(105, 84, 119, 24));
		}
		return cbbCommCode;
	}

	/**
	 * This method initializes jComboBox12
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCommName() {
		if (cbbCommName == null) {
			cbbCommName = new JComboBox();
			cbbCommName.setBounds(new Rectangle(105, 112, 119, 24));
		}
		return cbbCommName;
	}

	/**
	 * This method initializes jComboBox13
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCommSpec() {
		if (cbbCommSpec == null) {
			cbbCommSpec = new JComboBox();
			cbbCommSpec.setBounds(new Rectangle(105, 140, 119, 24));
		}
		return cbbCommSpec;
	}

	/**
	 * This method initializes jComboBox14
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbUnit() {
		if (cbbUnit == null) {
			cbbUnit = new JComboBox();
			cbbUnit.setBounds(new Rectangle(300, 31, 119, 24));
		}
		return cbbUnit;
	}

	/**
	 * This method initializes jComboBox15
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbAmount() {
		if (cbbAmount == null) {
			cbbAmount = new JComboBox();
			cbbAmount.setBounds(new Rectangle(300, 58, 119, 24));
		}
		return cbbAmount;
	}

	/**
	 * This method initializes jComboBox16
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbUnitPrice() {
		if (cbbUnitPrice == null) {
			cbbUnitPrice = new JComboBox();
			cbbUnitPrice.setBounds(new Rectangle(300, 85, 119, 24));
		}
		return cbbUnitPrice;
	}

	/**
	 * This method initializes jComboBox161
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbTotalPrice() {
		if (cbbTotalPrice == null) {
			cbbTotalPrice = new JComboBox();
			cbbTotalPrice.setBounds(new Rectangle(300, 112, 119, 24));
		}
		return cbbTotalPrice;
	}

	/**
	 * This method initializes jComboBox162
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCountry() {
		if (cbbCountry == null) {
			cbbCountry = new JComboBox();
			cbbCountry.setBounds(new Rectangle(300, 140, 119, 24));
		}
		return cbbCountry;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnCorrespond() {
		if (pnCorrespond == null) {
			pnCorrespond = new JPanel();
			pnCorrespond.setLayout(null);
			pnCorrespond.add(jLabel, null);
			pnCorrespond.add(jLabel1, null);
			pnCorrespond.add(jLabel11, null);
			pnCorrespond.add(jLabel12, null);
			pnCorrespond.add(jLabel13, null);
			pnCorrespond.add(jLabel14, null);
			pnCorrespond.add(getCbbMainNo(), null);
			pnCorrespond.add(getCbbSecNo(), null);
			pnCorrespond.add(getCbbCommCode(), null);
			pnCorrespond.add(getCbbCommName(), null);
			pnCorrespond.add(getCbbCommSpec(), null);
			pnCorrespond.add(getCbbUnit(), null);
			pnCorrespond.add(jLabel15, null);
			pnCorrespond.add(jLabel16, null);
			pnCorrespond.add(jLabel17, null);
			pnCorrespond.add(jLabel18, null);
			pnCorrespond.add(getCbbAmount(), null);
			pnCorrespond.add(getCbbUnitPrice(), null);
			pnCorrespond.add(getCbbTotalPrice(), null);
			pnCorrespond.add(getCbbCountry(), null);
			fillAllComboBox();
			initValues();
		}
		return pnCorrespond;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnFile() {
		if (pnFile == null) {
			pnFile = new JPanel();
			pnFile.setLayout(null);
			pnFile.setSize(new Dimension(442, 137));
			pnFile.add(getTfImportFile(), null);
			pnFile.add(getJButton2(), null);
			pnFile.add(getCbIsTitle(), null);
		}
		return pnFile;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfImportFile() {
		if (tfImportFile == null) {
			tfImportFile = new JTextField();
			tfImportFile.setBounds(new Rectangle(33, 41, 306, 26));
		}
		return tfImportFile;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setBounds(new Rectangle(340, 41, 24, 25));
			jButton2.setText("...");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.removeChoosableFileFilter(fileChooser
							.getFileFilter());
					fileChooser.setFileFilter(new CommonFileFilter(
							new String[] { "txt", "xls" }, "选择文档"));
					int state = fileChooser
							.showOpenDialog(DgImportAgreementCommInfo.this);
					if (state == JFileChooser.APPROVE_OPTION) {
						file = fileChooser.getSelectedFile();
						if (file.exists()) {
							tfImportFile.setText(file.getPath());
						}
						selectFile();
					}
				}
			});
		}
		return jButton2;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			lbInfo = new JLabel();
			lbInfo.setBounds(new Rectangle(14, 19, 354, 40));
			lbInfo.setFont(new Font("Dialog", Font.BOLD, 18));
			lbInfo.setText("JLabel");
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.setBackground(Color.white);
			jPanel2.setBorder(BorderFactory
					.createEtchedBorder(EtchedBorder.RAISED));
			jPanel2.setBounds(new Rectangle(1, 1, 552, 74));
			jPanel2.add(lbInfo, null);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnNave() {
		if (pnNave == null) {
			pnNave = new JPanel();
			pnNave.setLayout(null);
			pnNave.setBorder(BorderFactory
					.createEtchedBorder(EtchedBorder.RAISED));
			pnNave.setBounds(new Rectangle(0, 308, 555, 53));
			pnNave.add(getBtnPre(), null);
			pnNave.add(getBtnNext(), null);
			pnNave.add(getBtnCancel(), null);
		}
		return pnNave;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPre() {
		if (btnPre == null) {
			btnPre = new JButton();
			btnPre.setBounds(new Rectangle(305, 13, 73, 27));
			btnPre.setText("上一步");
			btnPre.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					step--;
					// switch (step) {
					// case 0:
					// pnFile = null;
					// pnContent = null;
					// pnCommInfo = null;
					// break;
					// case 1:
					// pnContent = null;
					// pnCommInfo = null;
					// break;
					// case 2:
					// pnCommInfo = null;
					// break;
					// }
					step();
				}
			});
		}
		return btnPre;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnNext() {
		if (btnNext == null) {
			btnNext = new JButton();
			btnNext.setBounds(new Rectangle(381, 13, 74, 27));
			btnNext.setText("下一步");
			btnNext.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (step == 2) {
						List list = tableModelCommInfo.getList();
						fixAssetAction.importAgreementCommInfo(new Request(
								CommonVars.getCurrUser()), agreement, list,
								isChange, isAddMoney);
						dispose();
					}
					step++;
					step();
				}
			});
		}
		return btnNext;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(new Rectangle(464, 13, 66, 27));
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnContent() {
		if (pnContent == null) {
			pnContent = new JPanel();
			pnContent.setLayout(new BorderLayout());
			pnContent.setBounds(new Rectangle(1, 76, 554, 232));
		}
		return pnContent;
	}

	/**
	 * This method initializes jPanel5
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnCommInfo() {
		if (pnCommInfo == null) {
			pnCommInfo = new JPanel();
			pnCommInfo.setLayout(new BorderLayout());
			pnCommInfo.setSize(new Dimension(451, 182));
			pnCommInfo.add(getJScrollPane(), BorderLayout.CENTER);
		}
		return pnCommInfo;
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
		}
		return jTable;
	}

	private void step() {
		this.btnNext.setText("下一步");
		pnContent.removeAll();
		if (step == 0) {
			lbInfo.setText("选择要导入的文件");
			pnContent.add(this.getPnFile(), BorderLayout.CENTER);
		} else if (step == 1) {
			if (file == null) {
				step--;
				step();
				return;
			}
			lbInfo.setText("选择文件列与数据栏位的对应");
			pnContent.add(this.getPnCorrespond(), BorderLayout.CENTER);
		} else if (step == 2) {
			lbInfo.setText("导入数据预览");
			this.btnNext.setText("确定");
			pnContent.add(this.getPnCommInfo(), BorderLayout.CENTER);
			showCommInfoData();
		}
		setState();
		pnContent.repaint();
	}

	private void setState() {
		this.btnPre.setEnabled(step > 0);
		this.btnNext.setEnabled(step < 3);
	}

	private void selectFile() {
		try {
			if (file.isDirectory() == true) {
				return;
			}
			String suffix = getSuffix(file);
			if (suffix != null) {
				if (suffix.toLowerCase().equals("txt")) {
					BufferedReader in = new BufferedReader(new FileReader(file));
					String s = "";
					try {
						s = in.readLine();
					} catch (IOException e3) {
						e3.printStackTrace();
					}
					String[] strs = s.split(String.valueOf((char) 9));
					fileColumnCount = strs.length;
					// DgDzscCorrespondFile dialog = new DgDzscCorrespondFile();
					// dialog.setFileColumnCount(strs.length);
					// dialog.setSelectedValues(columnNo);
					// dialog.setVisible(true);
					// Hashtable ht = dialog
					// .getSelectedValues();
					// if (ht != null) {
					// columnNo = ht;
					// }
					try {
						in.close();
					} catch (IOException e2) {
						e2.printStackTrace();
					}
				} else if (suffix.toLowerCase().equals("xls")) {
					InputStream is = null;
					try {
						// 构建Workbook对象, 只读Workbook对象
						// 直接从本地文件创建Workbook
						// 从输入流创建Workbook
						is = new FileInputStream(file);
						jxl.Workbook rwb = Workbook.getWorkbook(is);
						// 获取第一张Sheet表
						Sheet rs = rwb.getSheet(0);
						fileColumnCount = rs.getColumns();
						// DgDzscCorrespondFile dialog = new
						// DgDzscCorrespondFile();
						// dialog.setFileColumnCount(rs
						// .getColumns());
						// dialog.setSelectedValues(columnNo);
						// dialog.setVisible(true);
						// Hashtable ht = dialog
						// .getSelectedValues();
						// if (ht != null) {
						// columnNo = ht;
						// }
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

	private String getSuffix(File f) {
		String s = f.getPath(), suffix = null;
		int i = s.lastIndexOf('.');
		if (i > 0 && i < s.length() - 1)
			suffix = s.substring(i + 1).toLowerCase();
		return suffix;
	}

	private void initValues() {
		setComboBoxInitValue(this.cbbMainNo, 1);
		setComboBoxInitValue(this.cbbSecNo, 2);
		setComboBoxInitValue(this.cbbCommCode, 3);
		setComboBoxInitValue(this.cbbCommName, 4);
		setComboBoxInitValue(this.cbbCommSpec, 5);
		setComboBoxInitValue(this.cbbUnit, 6);
		setComboBoxInitValue(this.cbbAmount, 7);
		setComboBoxInitValue(this.cbbUnitPrice, 8);
		setComboBoxInitValue(this.cbbTotalPrice, 9);
		setComboBoxInitValue(this.cbbCountry, 10);

	}

	private int getComboBoxSelectValue(JComboBox comboBox) {
		if (comboBox.getSelectedItem() == null) {
			return -1;
		}
		return Integer.valueOf(((ItemProperty) comboBox.getSelectedItem())
				.getCode()) - 1;
	}

	private void setComboBoxInitValue(JComboBox comboBox, int selectedIndex) {
		if (selectedIndex > comboBox.getItemCount() - 1) {
			comboBox.setSelectedIndex(0);
		} else {
			comboBox.setSelectedIndex(selectedIndex);
		}
	}

	private void fillAllComboBox() {
		fillComboBox(this.cbbMainNo);
		fillComboBox(this.cbbSecNo);
		fillComboBox(this.cbbCommCode);
		fillComboBox(this.cbbCommName);
		fillComboBox(this.cbbCommSpec);
		fillComboBox(this.cbbUnit);
		fillComboBox(this.cbbAmount);
		fillComboBox(this.cbbUnitPrice);
		fillComboBox(this.cbbTotalPrice);
		fillComboBox(this.cbbCountry);
	}

	private void fillComboBox(JComboBox comboBox) {
		comboBox.removeAllItems();
		comboBox.addItem(new ItemProperty(String.valueOf(0), "  "));
		for (int i = 0; i < fileColumnCount; i++) {
			comboBox.addItem(new ItemProperty(String.valueOf(i + 1), "第"
					+ (i + 1) + "列"));
		}
		// comboBox.setRenderer(CustomBaseRender.getInstance()
		// .getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(comboBox);
	}

	private void showCommInfoData() {
		List list = parseFile(file);
		tableModelCommInfo = null;
		tableModelCommInfo = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("主序号", "mainNo", 50, Integer.class));
						list.add(addColumn("次序号", "secNo", 50, Integer.class));
						list.add(addColumn("商品编码", "commCode", 100));
						list.add(addColumn("商品名称", "commName", 120));
						list.add(addColumn("型号规格", "commSpec", 120));
						list.add(addColumn("单位", "unit", 30));
						list.add(addColumn("数量", "amount", 100));
						list.add(addColumn("单价", "unitPrice", 100));
						list.add(addColumn("金额", "totalPrice", 100));
						list.add(addColumn("原产国", "country", 100));
						return list;
					}
				});
	}

	private List parseFile(File file) {
		BufferedReader in;
		InputStream is = null;
		ArrayList<TempImportAgreementCommInfo> list = new ArrayList<TempImportAgreementCommInfo>();
		String[] strs = null;
		String[] materielNameSpec;
		int row = 0;// 文件的行数，主要是控制当文件第一行为标题行的时候跳出
		try {
			if (file.isDirectory() == true) {
				return new ArrayList();
			}
			String suffix = getSuffix(file);
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
							convertToFileData(list, strs);
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
						is = new FileInputStream(file);
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
							//
							// to 简体
							//
							convertToFileData(list, strs);
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

	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIsTitle() {
		if (cbIsTitle == null) {
			cbIsTitle = new JCheckBox();
			cbIsTitle.setBounds(new Rectangle(32, 77, 145, 21));
			cbIsTitle.setText("第一行是否为标题行");
		}
		return cbIsTitle;
	}

	private void convertToFileData(ArrayList<TempImportAgreementCommInfo> list,
			String[] strs) {
		// strs = changStrs(strs);
		TempImportAgreementCommInfo data = new TempImportAgreementCommInfo();

		data.setMainNo(getFileColumnValue(strs,
				getComboBoxSelectValue(this.cbbMainNo)));
		data.setSecNo(getFileColumnValue(strs,
				getComboBoxSelectValue(this.cbbSecNo)));
		String commCode = getFileColumnValue(strs,
				getComboBoxSelectValue(this.cbbCommCode));
		if ((commCode != null) && (commCode.length() == 10)
				&& (commCode.substring(8).equals("00"))) {
			commCode = commCode.substring(0, 8);
		}
		data.setCommCode(commCode);
		data.setCommName(getFileColumnValue(strs,
				getComboBoxSelectValue(this.cbbCommName)));
		data.setCommSpec(getFileColumnValue(strs,
				getComboBoxSelectValue(this.cbbCommSpec)));
		data.setUnit(getFileColumnValue(strs,
				getComboBoxSelectValue(this.cbbUnit)));
		data.setAmount(getFileColumnValue(strs,
				getComboBoxSelectValue(this.cbbAmount)));
		data.setUnitPrice(getFileColumnValue(strs,
				getComboBoxSelectValue(this.cbbUnitPrice)));
		data.setTotalPrice(getFileColumnValue(strs,
				getComboBoxSelectValue(this.cbbTotalPrice)));
		data.setCountry(getFileColumnValue(strs,
				getComboBoxSelectValue(this.cbbCountry)));
		list.add(data);
	}

	private String getFileColumnValue(String[] fileRow, int dataIndex) {
		// if (columnNo.get(Integer.valueOf(dataIndex)) == null
		// ||"".equals(columnNo.get(Integer.valueOf(dataIndex)))) {
		// return "";
		// }
		// int columnIndex = Integer.parseInt(columnNo.get(
		// Integer.valueOf(dataIndex)).toString());
		int columnIndex = dataIndex;
		if (columnIndex < 0 || columnIndex > fileRow.length - 1) {
			return "";
		} else {
			return fileRow[columnIndex];
		}
	}
} // @jve:decl-index=0:visual-constraint="10,10"
