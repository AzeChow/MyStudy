package com.bestway.bcs.client.dictpor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.bestway.bcs.dictpor.action.BcsDictPorAction;
import com.bestway.bcs.dictpor.entity.BcsDictPorHead;
import com.bestway.bcus.client.common.CommonFileFilter;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonStepProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.custombase.entity.basecode.MachiningType;
import com.bestway.bcus.custombase.entity.countrycode.District;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.LevyKind;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.ManageObject;
import com.bestway.ui.winuicontrol.JDialogBase;
import javax.swing.JCheckBox;

public class DgImportDictPorFromQPExcel extends JDialogBase {

	private JPanel jContentPane = null;

	private JLabel jLabel = null;

	private JPanel jPanel = null;

	private JRadioButton jRadioButton = null;

	private JRadioButton jRadioButton1 = null;

	private JRadioButton jRadioButton2 = null;

	private JTextField jTextField = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JButton jButton2 = null;

	private BcsDictPorAction bcsDictPorAction = null;

	private ButtonGroup buttonGroup = null; // @jve:decl-index=0:visual-constraint="440,82"

	private boolean isOk = false;

	private BcsDictPorHead bcsDictPorHead = null;

	private File excelFile = null;

	private JPanel jPanel1 = null;

	private JLabel jLabel3 = null;

	private JLabel jLabel5 = null;

	private JLabel jLabel7 = null;

	private JLabel jLabel4 = null;

	private JLabel jLabel6 = null;

	private JLabel jLabel8 = null;

	private JComboBox cbbManageObject = null;

	private JComboBox cbbTrade = null;

	private JComboBox cbbCurr = null;

	private JComboBox cbbLevyKind = null;

	private JComboBox cbbReceiveArea = null;

	private JComboBox cbbMachiningType = null;

	private JPanel pnCover = null;

	private JCheckBox cbConver = null;
	
	private FmBcsDictPor fm=null;

	public boolean isOk() {
		return isOk;
	}

	public void setOk(boolean isOk) {
		this.isOk = isOk;
	}

	public BcsDictPorHead getBcsDictPorHead() {
		return bcsDictPorHead;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgImportDictPorFromQPExcel(FmBcsDictPor fm) {
		super();
		this.fm=fm;
		initialize();
		bcsDictPorAction = (BcsDictPorAction) CommonVars
				.getApplicationContext().getBean("bcsDictPorAction");
		initUIComponents();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setTitle("导入Excel");
		this.setBounds(new Rectangle(0, 0, 384, 371));
		this.setContentPane(getJContentPane());
		this.getButtonGroup();
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(29, 32, 55, 20));
			jLabel.setText("Excel文件");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(jLabel, null);
			jContentPane.add(getJTextField(), null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton1(), null);
			jContentPane.add(getJButton2(), null);
			jContentPane.add(getJPanel1(), null);
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
			jPanel = new JPanel();
			jPanel.setLayout(new FlowLayout());
			jPanel
					.setBorder(BorderFactory
							.createTitledBorder(
									null,
									"\u5bfc\u5165\u7684\u5907\u6848\u8d44\u6599\u5e93\u72b6\u6001",
									TitledBorder.DEFAULT_JUSTIFICATION,
									TitledBorder.DEFAULT_POSITION, new Font(
											"Dialog", Font.PLAIN, 12),
									new Color(51, 51, 51)));
			jPanel.setBounds(new Rectangle(9, 104, 306, 66));
			jPanel.add(getJRadioButton(), null);
			jPanel.add(getJRadioButton1(), null);
			jPanel.add(getJRadioButton2(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton() {
		if (jRadioButton == null) {
			jRadioButton = new JRadioButton();
			jRadioButton.setText("草稿状态");
		}
		return jRadioButton;
	}

	/**
	 * This method initializes jRadioButton1
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton1() {
		if (jRadioButton1 == null) {
			jRadioButton1 = new JRadioButton();
			jRadioButton1.setText("正在变更");
		}
		return jRadioButton1;
	}

	/**
	 * This method initializes jRadioButton2
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton2() {
		if (jRadioButton2 == null) {
			jRadioButton2 = new JRadioButton();
			jRadioButton2.setText("正在执行");
			jRadioButton2.setSelected(true);
		}
		return jRadioButton2;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(new Rectangle(85, 32, 237, 22));
			jTextField.setEditable(false);
		}
		return jTextField;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new Rectangle(322, 32, 25, 22));
			jButton.setText("...");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.removeChoosableFileFilter(fileChooser
							.getFileFilter());
					fileChooser.setFileFilter(new CommonFileFilter(
							new String[] { "xls" }, "选择文档"));
					int state = fileChooser
							.showOpenDialog(DgImportDictPorFromQPExcel.this);
					if (state != JFileChooser.APPROVE_OPTION) {
						return;
					}
					excelFile = fileChooser.getSelectedFile();
					jTextField.setText(excelFile.getPath());
				}
			});
		}
		return jButton;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(new Rectangle(108, 304, 70, 26));
			jButton1.setText("确定");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new ImportFileDataRunnable().start();
				}
			});
		}
		return jButton1;
	}

	class ImportFileDataRunnable extends Thread {
		public void run() {
			try {				
				if (excelFile == null || !excelFile.exists()) {
					JOptionPane.showMessageDialog(
							DgImportDictPorFromQPExcel.this, "请选择需要导入的文件！", "提示",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				if (cbbManageObject.getSelectedItem() == null) {
					JOptionPane.showMessageDialog(
							DgImportDictPorFromQPExcel.this, "请选择管理对象！", "提示",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				if (cbbLevyKind.getSelectedItem() == null) {
					JOptionPane.showMessageDialog(
							DgImportDictPorFromQPExcel.this, "请选择征免性质！", "提示",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				if (cbbTrade.getSelectedItem() == null) {
					JOptionPane.showMessageDialog(
							DgImportDictPorFromQPExcel.this, "请选择贸易方式！", "提示",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				if (cbbReceiveArea.getSelectedItem() == null) {
					JOptionPane.showMessageDialog(
							DgImportDictPorFromQPExcel.this, "请选择地区代码！", "提示",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				if (cbbCurr.getSelectedItem() == null) {
					JOptionPane.showMessageDialog(
							DgImportDictPorFromQPExcel.this, "请选择币制！", "提示",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				if (cbbMachiningType.getSelectedItem() == null) {
					JOptionPane.showMessageDialog(
							DgImportDictPorFromQPExcel.this, "请选择加工种类！", "提示",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}

				String taskId = CommonProgress.getExeTaskId();
				CommonStepProgress.showStepProgressDialog(taskId);
				CommonStepProgress.setStepMessage("系统正在读取文件资料，请稍后...");
				Request request = new Request(CommonVars.getCurrUser());
				request.setTaskId(taskId);
				byte[] excelFileContent = null;
				FileInputStream fileInputStream = null;
				try {
					fileInputStream = new FileInputStream(excelFile);
					excelFileContent = new byte[(int) excelFile.length()];
					fileInputStream.read(excelFileContent);
				} catch (Exception ex) {
					throw new RuntimeException(ex.getMessage());
				} finally {
					try {
						fileInputStream.close();
					} catch (IOException ex1) {
						throw new RuntimeException(ex1.getMessage());
					}
				}
				String declareState = null;
				if (jRadioButton.isSelected()) {
					declareState = DeclareState.APPLY_POR;
				} else if (jRadioButton1.isSelected()) {
					declareState = DeclareState.CHANGING_EXE;
				} else if (jRadioButton2.isSelected()) {
					declareState = DeclareState.PROCESS_EXE;
				}
				String manageObject = ((ItemProperty) cbbManageObject
						.getSelectedItem()).getCode();
				CommonStepProgress.setStepMessage("系统正在导入资料，请稍后...");
				bcsDictPorHead = bcsDictPorAction
						.importBcsDictPorHeadFromQPExportFile(new Request(
								CommonVars.getCurrUser()), excelFileContent,
								declareState, manageObject,
								(LevyKind) cbbLevyKind.getSelectedItem(),
								(Trade) cbbTrade.getSelectedItem(),
								(District) cbbReceiveArea.getSelectedItem(),
								(Curr) cbbCurr.getSelectedItem(),
								(MachiningType) cbbMachiningType
										.getSelectedItem(), taskId,getCbConver().isSelected());
				DgImportDictPorFromQPExcel.this.isOk = true;
				DgImportDictPorFromQPExcel.this.dispose();
				fm.initTable();
				JOptionPane.showMessageDialog(DgImportDictPorFromQPExcel.this,
						"导入数据成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception ex) {
				ex.printStackTrace();
				CommonStepProgress.closeStepProgressDialog();
				JOptionPane.showMessageDialog(DgImportDictPorFromQPExcel.this,
						"导入数据失败！" + ex.getMessage(), "提示",
						JOptionPane.ERROR_MESSAGE);
			} finally {
				CommonStepProgress.closeStepProgressDialog();
			}
		}
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setBounds(new Rectangle(194, 304, 70, 26));
			jButton2.setText("取消");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					isOk = false;
					dispose();
				}
			});
		}
		return jButton2;
	}

	/**
	 * This method initializes buttonGroup
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup() {
		if (buttonGroup == null) {
			buttonGroup = new ButtonGroup();
			buttonGroup.add(this.getJRadioButton());
			buttonGroup.add(this.getJRadioButton1());
			buttonGroup.add(this.getJRadioButton2());
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
			jLabel8 = new JLabel();
			jLabel8.setBounds(new Rectangle(166, 76, 48, 18));
			jLabel8.setText("\u52a0\u5de5\u79cd\u7c7b");
			jLabel8.setForeground(Color.blue);
			jLabel6 = new JLabel();
			jLabel6.setBounds(new Rectangle(166, 51, 48, 18));
			jLabel6.setText("\u5730\u533a\u4ee3\u7801");
			jLabel6.setForeground(Color.blue);
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(166, 24, 48, 18));
			jLabel4.setText("\u5f81\u514d\u6027\u8d28");
			jLabel4.setForeground(Color.blue);
			jLabel7 = new JLabel();
			jLabel7.setBounds(new Rectangle(7, 76, 48, 18));
			jLabel7.setText("\u5e01\u5236");
			jLabel7.setForeground(Color.blue);
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(7, 49, 48, 18));
			jLabel5.setText("\u8d38\u6613\u65b9\u5f0f");
			jLabel5.setForeground(Color.blue);
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(7, 24, 48, 18));
			jLabel3.setText("\u7ba1\u7406\u5bf9\u8c61");
			jLabel3.setForeground(Color.blue);
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setBounds(new Rectangle(27, 62, 322, 238));
			jPanel1.setBorder(BorderFactory.createTitledBorder(null, "\u5bfc\u5165\u4fe1\u606f\u8865\u5145", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.PLAIN, 12), new Color(51, 51, 51)));
			jPanel1.add(getJPanel(), null);
			jPanel1.add(jLabel3, null);
			jPanel1.add(jLabel5, null);
			jPanel1.add(jLabel7, null);
			jPanel1.add(jLabel4, null);
			jPanel1.add(jLabel6, null);
			jPanel1.add(jLabel8, null);
			jPanel1.add(getCbbManageObject(), null);
			jPanel1.add(getCbbTrade(), null);
			jPanel1.add(getCbbCurr(), null);
			jPanel1.add(getCbbLevyKind(), null);
			jPanel1.add(getCbbReceiveArea(), null);
			jPanel1.add(getCbbMachiningType(), null);
			jPanel1.add(getPnCover(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes cbbManageObject
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbManageObject() {
		if (cbbManageObject == null) {
			cbbManageObject = new JComboBox();
			cbbManageObject.setBounds(new Rectangle(54, 21, 106, 23));
		}
		return cbbManageObject;
	}

	/**
	 * This method initializes cbbTrade
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbTrade() {
		if (cbbTrade == null) {
			cbbTrade = new JComboBox();
			cbbTrade.setBounds(new Rectangle(54, 47, 106, 23));
		}
		return cbbTrade;
	}

	/**
	 * This method initializes cbbCurr
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCurr() {
		if (cbbCurr == null) {
			cbbCurr = new JComboBox();
			cbbCurr.setBounds(new Rectangle(54, 74, 106, 23));
		}
		return cbbCurr;
	}

	/**
	 * This method initializes cbbLevyKind
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbLevyKind() {
		if (cbbLevyKind == null) {
			cbbLevyKind = new JComboBox();
			cbbLevyKind.setBounds(new Rectangle(216, 21, 101, 23));
		}
		return cbbLevyKind;
	}

	/**
	 * This method initializes cbbReceiveArea
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbReceiveArea() {
		if (cbbReceiveArea == null) {
			cbbReceiveArea = new JComboBox();
			cbbReceiveArea.setBounds(new Rectangle(216, 47, 101, 23));
		}
		return cbbReceiveArea;
	}

	/**
	 * This method initializes cbbMachiningType
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbMachiningType() {
		if (cbbMachiningType == null) {
			cbbMachiningType = new JComboBox();
			cbbMachiningType.setBounds(new Rectangle(216, 74, 101, 23));
		}
		return cbbMachiningType;
	}

	/**
	 * 初始化组件
	 * 
	 */
	private void initUIComponents() {
		// 初始化贸易方式
		this.cbbTrade.setModel(CustomBaseModel.getInstance().getTradeModel());
		this.cbbTrade.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbTrade);
		this.cbbTrade.setSelectedItem(null);

		// 初始化征免方式
		this.cbbLevyKind.setModel(CustomBaseModel.getInstance()
				.getLevyKindModel());
		this.cbbLevyKind
				.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbLevyKind);
		this.cbbLevyKind.setSelectedItem(null);

		// 初始化地区代码
		this.cbbReceiveArea.setModel(CustomBaseModel.getInstance()
				.getDistrictModelModel());
		this.cbbReceiveArea.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance()
				.setComboBoxEditor(cbbReceiveArea);
		this.cbbReceiveArea.setSelectedItem(null);

		// 初始化币别
		this.cbbCurr.setModel(CustomBaseModel.getInstance().getCurrModel());
		this.cbbCurr.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbCurr);
		this.cbbCurr.setSelectedItem(null);

		// 初始化加工种类
		this.cbbMachiningType.setModel(CustomBaseModel.getInstance()
				.getMachiningTypeModel());
		this.cbbMachiningType.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				cbbMachiningType);
		this.cbbMachiningType.setSelectedItem(null);

		// 初始化管理对象
		this.cbbManageObject.removeAllItems();
		this.cbbManageObject.addItem(new ItemProperty(ManageObject.MANAGE_COP,
				"经营单位"));
		this.cbbManageObject.addItem(new ItemProperty(
				ManageObject.MANUFACTURE_COP, "加工单位"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbManageObject);
		this.cbbManageObject.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		cbbManageObject.setSelectedIndex(-1);
		// // 限制类标志
		// cbbLimitFlag.removeAllItems();
		// cbbLimitFlag.addItem(new ItemProperty(LimitFlag.ADJUST_BEFORE_EMS,
		// "调整前旧手册"));
		// cbbLimitFlag.addItem(new ItemProperty(LimitFlag.ADJUST_AFTER_EMS,
		// "调整后新手册"));
		// cbbLimitFlag.addItem(new ItemProperty(LimitFlag.ACOUNT_BOOK_USE,
		// "台账专用手册"));
		// cbbLimitFlag.setRenderer(CustomBaseRender.getInstance().getRender());
		// CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbLimitFlag);
	}

	/**
	 * This method initializes pnCover	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPnCover() {
		if (pnCover == null) {
			pnCover = new JPanel();
			pnCover.setLayout(new FlowLayout());
			pnCover.setBounds(new Rectangle(11, 173, 304, 59));
			pnCover.setBorder(BorderFactory.createTitledBorder(null, "备案资料库重复处理", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.PLAIN, 12), new Color(51, 51, 51)));
			pnCover.add(getCbConver(), null);
		}
		return pnCover;
	}

	/**
	 * This method initializes cbConver	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbConver() {
		if (cbConver == null) {
			cbConver = new JCheckBox();
			cbConver.setText("覆盖旧备案资料库");
		}
		return cbConver;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
