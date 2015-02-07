package com.bestway.bcs.client.contract;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.dictpor.action.BcsDictPorAction;
import com.bestway.bcs.dictpor.entity.BcsDictPorHead;
import com.bestway.bcus.client.common.CommonFileFilter;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonStepProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.LimitFlag;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgImportContractFromQPExcel extends JDialogBase {

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

	private ContractAction contractAction = null;

	private BcsDictPorAction bcsDictPorAction = null;

	private ButtonGroup buttonGroup = null; // @jve:decl-index=0:visual-constraint="440,82"

	private boolean isOk = false;

	private Contract contract = null;

	private File excelFile = null;

	private JPanel jPanel1 = null;

	private JLabel jLabel2 = null;

	private JComboBox cbbBcsDictPorHead = null;

	private JLabel jLabel1 = null;

	private JComboBox cbbLimitFlag = null;

	private JPanel jPanel2 = null;

	private JCheckBox jCheckBox = null;

	private FmContract fm = null;

	public boolean isOk() {
		return isOk;
	}

	public void setOk(boolean isOk) {
		this.isOk = isOk;
	}

	public Contract getContract() {
		return contract;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgImportContractFromQPExcel(FmContract fm) {
		super();
		this.fm = fm;
		initialize();
		contractAction = (ContractAction) CommonVars.getApplicationContext()
				.getBean("contractAction");
		bcsDictPorAction = (BcsDictPorAction) CommonVars
				.getApplicationContext().getBean("bcsDictPorAction");
	}

	public void setVisible(boolean b) {
		if (b) {
			List list = bcsDictPorAction.findExingBcsDictPorHead(new Request(
					CommonVars.getCurrUser()));
			this.cbbBcsDictPorHead.setModel(new DefaultComboBoxModel(list
					.toArray()));
			this.cbbBcsDictPorHead.setRenderer(new DefaultListCellRenderer() {

				public Component getListCellRendererComponent(JList list,
						Object value, int index, boolean isSelected,
						boolean cellHasFocus) {
					// TODO Auto-generated method stub
					if (value != null && value instanceof BcsDictPorHead) {
						this.setText(((BcsDictPorHead) value).getDictPorEmsNo());
					}
					return this;
				}
			});
			if (this.cbbBcsDictPorHead.getItemCount() > 0) {
				this.cbbBcsDictPorHead.setSelectedIndex(0);
			}
			// 限制类标志
			cbbLimitFlag.removeAllItems();
			cbbLimitFlag.addItem(new ItemProperty(LimitFlag.ADJUST_BEFORE_EMS,
					"调整前旧手册"));
			cbbLimitFlag.addItem(new ItemProperty(LimitFlag.ADJUST_AFTER_EMS,
					"调整后新手册"));
			cbbLimitFlag.addItem(new ItemProperty(LimitFlag.ACOUNT_BOOK_USE,
					"台账专用手册"));
			cbbLimitFlag
					.setRenderer(CustomBaseRender.getInstance().getRender());
			CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
					cbbLimitFlag);
			cbbLimitFlag.setSelectedIndex(1);
		}
		super.setVisible(true);
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(384, 351));
		this.setTitle("导入Excel");
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
			jLabel.setBounds(new Rectangle(39, 32, 55, 20));
			jLabel.setText("Excel文件");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(jLabel, null);
			jContentPane.add(getJTextField(), null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton1(), null);
			jContentPane.add(getJButton2(), null);
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
			jPanel = new JPanel();
			jPanel.setLayout(new FlowLayout());
			jPanel.setBorder(BorderFactory.createTitledBorder(null,
					"\u5bfc\u5165\u7684\u624b\u518c\u72b6\u6001",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.PLAIN, 12), new Color(51, 51, 51)));
			jPanel.setBounds(new Rectangle(7, 85, 293, 62));
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
			jTextField.setBounds(new Rectangle(98, 32, 224, 22));
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
							.showOpenDialog(DgImportContractFromQPExcel.this);
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
			jButton1.setBounds(new Rectangle(111, 280, 70, 26));
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
							DgImportContractFromQPExcel.this, "请选择需要导入的文件！",
							"提示", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				if (cbbLimitFlag.getSelectedItem() == null) {
					JOptionPane.showMessageDialog(
							DgImportContractFromQPExcel.this, "请选择限制类标志！",
							"提示", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				if (jCheckBox.isSelected()) {
					if (JOptionPane.showConfirmDialog(
							DgImportContractFromQPExcel.this,
							"您已经选择覆盖手册,如果手册重复,确定覆盖原来手册吗?", "提示", 0) != 0) {
						return;
					}

				}

				String taskId = CommonProgress.getExeTaskId();

				CommonStepProgress.showStepProgressDialog(taskId);

				CommonStepProgress.setStepMessage("系统正在读取文件资料，请稍后...");

				Request request = new Request(CommonVars.getCurrUser());

				request.setTaskId(taskId);

				// excel 文件内容 字节数组
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

				// 申报状态
				String declareState = null;

				if (jRadioButton.isSelected()) {

					declareState = DeclareState.APPLY_POR;

				} else if (jRadioButton1.isSelected()) {

					declareState = DeclareState.CHANGING_EXE;

				} else if (jRadioButton2.isSelected()) {

					declareState = DeclareState.PROCESS_EXE;

				}

				// 备案资料库编号
				String dictPorEmsNo = "";

				// 获取 所选择的 备案资料库表头
				BcsDictPorHead bcsDictPorHead = (BcsDictPorHead) cbbBcsDictPorHead
						.getSelectedItem();

				if (bcsDictPorHead != null) {

					dictPorEmsNo = bcsDictPorHead.getDictPorEmsNo();

				}

				// 限制标记
				String limitFlag = "";

				if (cbbLimitFlag.getSelectedItem() != null) {

					limitFlag = ((ItemProperty) cbbLimitFlag.getSelectedItem())
							.getCode();

				}

				// CommonStepProgress.showStepProgressDialog(taskId);
				CommonStepProgress.setStepMessage("系统正在导入资料，请稍后...");

				/*
				 * 开始导入 手册
				 */
				contract = contractAction.importContractFromQPExportFile(
						new Request(CommonVars.getCurrUser()),
						excelFileContent, declareState, dictPorEmsNo,
						limitFlag, taskId, jCheckBox.isSelected());

				DgImportContractFromQPExcel.this.isOk = true;

				DgImportContractFromQPExcel.this.dispose();

				fm.initTable();

				JOptionPane.showMessageDialog(DgImportContractFromQPExcel.this,
						"导入数据成功！", "提示", JOptionPane.INFORMATION_MESSAGE);

			} catch (Exception ex) {

				ex.printStackTrace();

				CommonStepProgress.closeStepProgressDialog();

				JOptionPane.showMessageDialog(DgImportContractFromQPExcel.this,
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
			jButton2.setBounds(new Rectangle(197, 280, 70, 26));
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
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(13, 52, 88, 21));
			jLabel1.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			jLabel1.setForeground(Color.blue);
			jLabel1.setText("限制类标志");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(14, 23, 89, 16));
			jLabel2.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			jLabel2.setForeground(Color.blue);
			jLabel2.setText("备案资料库编号");
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setBounds(new Rectangle(35, 58, 312, 152));
			jPanel1.setBorder(BorderFactory.createTitledBorder(null,
					"\u5bfc\u5165\u4fe1\u606f\u8865\u5145",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.PLAIN, 12), new Color(51, 51, 51)));
			jPanel1.add(jLabel2, null);
			jPanel1.add(getJPanel(), null);
			jPanel1.add(getCbbBcsDictPorHead(), null);
			jPanel1.add(jLabel1, null);
			jPanel1.add(getCbbLimitFlag(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbBcsDictPorHead() {
		if (cbbBcsDictPorHead == null) {
			cbbBcsDictPorHead = new JComboBox();
			cbbBcsDictPorHead.setBounds(new Rectangle(107, 20, 193, 22));
		}
		return cbbBcsDictPorHead;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbLimitFlag() {
		if (cbbLimitFlag == null) {
			cbbLimitFlag = new JComboBox();
			cbbLimitFlag.setBounds(new Rectangle(107, 52, 193, 22));
		}
		return cbbLimitFlag;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(new FlowLayout());
			jPanel2.setBounds(new Rectangle(37, 214, 313, 56));
			jPanel2.setBorder(BorderFactory.createTitledBorder(null, "手册重复处理",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.PLAIN, 12), new Color(51, 51, 51)));
			jPanel2.add(getJCheckBox(), null);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			jCheckBox = new JCheckBox();
			jCheckBox.setText("覆盖旧手册");
		}
		return jCheckBox;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
