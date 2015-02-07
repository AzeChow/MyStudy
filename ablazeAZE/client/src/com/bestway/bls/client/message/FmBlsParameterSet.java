/*
 * Created on 2004-8-24
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bls.client.message;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.TitledBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.bls.action.BlsMessageAction;
import com.bestway.bls.client.storagebill.DgSwitchBlsInOutStockToStorageBillParameterSet;
import com.bestway.bls.entity.BlsParameterSet;
import com.bestway.common.Request;
import com.bestway.common.fpt.entity.FptParameterSet;
import com.bestway.cspcard.entity.ICCardInfo;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import java.awt.event.KeyEvent;
import java.awt.GridBagLayout;
import javax.swing.border.EtchedBorder;
import javax.swing.border.SoftBevelBorder;
import com.bestway.ui.winuicontrol.JIPTextField;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;
import java.awt.GridBagConstraints;
import javax.swing.JComboBox;
import javax.swing.JSlider;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public class FmBlsParameterSet extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;

	private JPanel jPanel = null;

	private JButton btnSave = null;

	private JButton btnEdit = null;

	private int dataState = DataState.BROWSE;

	private JPanel jPanel1 = null;

	private JButton jButton3 = null;

	private BlsMessageAction blsMessageAction = null;

	private JButton btnClose = null;

	private JPanel jPanel4 = null;

	private JToolBar jJToolBarBar = null;

	private JPanel jPanel41 = null;

	private JLabel jLabel51 = null;

	private JLabel jLabel6 = null;

	private JTextField tfSeqNo = null;

	private JTextField tfPwd = null;

	private JIPTextField tfRemoteHostAddress = null;

	private JButton btnReadCard = null;

	private String tempDir = "./";

	private JPanel jPanel5 = null;

	private JLabel jLabel3 = null;

	private JLabel jLabel4 = null;

	private JTextField tfRemoteHostPort = null;

	private JLabel jLabel5 = null;

	private JLabel jLabel61 = null;

	private JPanel jPanel2 = null;

	private JRadioButton rbInnerUse = null;

	private JRadioButton rbCustomsDeclare = null;

	private ButtonGroup buttonGroup = null; // @jve:decl-index=0:visual-constraint="948,180"

	private JLabel jLabel = null;

	private JTextField tfHpServiceUrl = null;

	private JButton btnSwitch = null;

	private JPanel jPanel3 = null;

	private JRadioButton rbAutoDeclare = null;

	private JRadioButton rbHandDeclare = null;

	private ButtonGroup buttonGroup1 = null; // @jve:decl-index=0:visual-constraint="955,256"

	private JPanel jPanel6 = null;

	private JCheckBox cbImpMon = null;

	private JCheckBox cbImpTue = null;

	private JCheckBox cbImpWed = null;

	private JCheckBox cbImpThu = null;

	private JCheckBox cbImpFri = null;

	private JCheckBox cbImpSat = null;

	private JCheckBox cbImpSun = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel62 = null;

	private JComboBox cbbImpBeginTime = null;

	private JComboBox cbbImpEndTime = null;

	private JPanel jPanel8 = null;

	private JTextField tfImpEverydayFrequency = null;

	private JComboBox cbbImpEverydayFrequencyType = null;

	private JLabel jLabel2 = null;

	private JPanel jPanel31 = null;

	private JPanel jPanel61 = null;

	private JCheckBox cbExpMon = null;

	private JCheckBox cbExpTue = null;

	private JCheckBox cbExpWed = null;

	private JCheckBox cbExpThu = null;

	private JCheckBox cbExpFri = null;

	private JCheckBox cbExpSat = null;

	private JCheckBox cbExpSun = null;

	private JPanel jPanel81 = null;

	private JTextField tfExpEverydayFrequency = null;

	private JComboBox cbbExpEverydayFrequencyType = null;

	private JLabel jLabel11 = null;

	private JComboBox cbbExpBeginTime = null;

	private JLabel jLabel621 = null;

	private JComboBox cbbExpEndTime = null;

	private JLabel jLabel21 = null;

	private JCheckBox cbAutoProcess = null;

	private JPanel jPanel311 = null;

	private JPanel jPanel611 = null;

	private JCheckBox cbCobMon = null;

	private JCheckBox cbCobTue = null;

	private JCheckBox cbCobWed = null;

	private JCheckBox cbCobThu = null;

	private JCheckBox cbCobFri = null;

	private JCheckBox cbCobSat = null;

	private JCheckBox cbCobSun = null;

	private JPanel jPanel811 = null;

	private JTextField tfCobEverydayFrequency = null;

	private JComboBox cbbCobEverydayFrequencyType = null;

	private JLabel jLabel111 = null;

	private JComboBox cbbCobBeginTime = null;

	private JLabel jLabel6211 = null;

	private JComboBox cbbCobEndTime = null;

	private JLabel jLabel211 = null;

	private JLabel jLabel63 = null;

	private JTextField tfEmsNo = null;

	/**
	 * This is the default constructor
	 */
	public FmBlsParameterSet() {
		super();
		blsMessageAction = (BlsMessageAction) CommonVars
				.getApplicationContext().getBean("blsMessageAction");
		initialize();
		initUIComponents();
		showData();
		dataState = DataState.BROWSE;
		setState();
	}

	private void initUIComponents() {
		this.cbbImpEverydayFrequencyType.removeAllItems();
		this.cbbImpEverydayFrequencyType.addItem(new ItemProperty("0", "秒"));
		this.cbbImpEverydayFrequencyType.addItem(new ItemProperty("1", "分"));
		this.cbbImpEverydayFrequencyType.addItem(new ItemProperty("2", "时"));
		this.cbbImpEverydayFrequencyType.setRenderer(CustomBaseRender
				.getInstance().getRender(20, 50));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				cbbImpEverydayFrequencyType);

		this.cbbImpBeginTime.removeAllItems();
		for (int i = 0; i <= 23; i++) {
			this.cbbImpBeginTime.addItem(new ItemProperty(String.valueOf(i),
					String.valueOf(i) + "点"));
		}
		this.cbbImpBeginTime.setRenderer(CustomBaseRender.getInstance()
				.getRender(30, 50));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				cbbImpBeginTime);
		this.cbbImpBeginTime.setSelectedIndex(-1);

		this.cbbImpEndTime.removeAllItems();
		for (int i = 0; i <= 23; i++) {
			this.cbbImpEndTime.addItem(new ItemProperty(String.valueOf(i),
					String.valueOf(i) + "点"));
		}
		this.cbbImpEndTime.setRenderer(CustomBaseRender.getInstance()
				.getRender(30, 50));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbImpEndTime);
		this.cbbImpEndTime.setSelectedIndex(-1);

		this.cbbExpEverydayFrequencyType.removeAllItems();
		this.cbbExpEverydayFrequencyType.addItem(new ItemProperty("0", "秒"));
		this.cbbExpEverydayFrequencyType.addItem(new ItemProperty("1", "分"));
		this.cbbExpEverydayFrequencyType.addItem(new ItemProperty("2", "时"));
		this.cbbExpEverydayFrequencyType.setRenderer(CustomBaseRender
				.getInstance().getRender(20, 50));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				cbbExpEverydayFrequencyType);

		this.cbbExpBeginTime.removeAllItems();
		for (int i = 0; i <= 23; i++) {
			this.cbbExpBeginTime.addItem(new ItemProperty(String.valueOf(i),
					String.valueOf(i) + "点"));
		}
		this.cbbExpBeginTime.setRenderer(CustomBaseRender.getInstance()
				.getRender(30, 50));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				cbbExpBeginTime);
		this.cbbExpBeginTime.setSelectedIndex(-1);

		this.cbbExpEndTime.removeAllItems();
		for (int i = 0; i <= 23; i++) {
			this.cbbExpEndTime.addItem(new ItemProperty(String.valueOf(i),
					String.valueOf(i) + "点"));
		}
		this.cbbExpEndTime.setRenderer(CustomBaseRender.getInstance()
				.getRender(30, 50));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbExpEndTime);
		this.cbbExpEndTime.setSelectedIndex(-1);

		this.cbbCobEverydayFrequencyType.removeAllItems();
		this.cbbCobEverydayFrequencyType.addItem(new ItemProperty("0", "秒"));
		this.cbbCobEverydayFrequencyType.addItem(new ItemProperty("1", "分"));
		this.cbbCobEverydayFrequencyType.addItem(new ItemProperty("2", "时"));
		this.cbbCobEverydayFrequencyType.setRenderer(CustomBaseRender
				.getInstance().getRender(20, 50));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				cbbCobEverydayFrequencyType);

		this.cbbCobBeginTime.removeAllItems();
		for (int i = 0; i <= 23; i++) {
			this.cbbCobBeginTime.addItem(new ItemProperty(String.valueOf(i),
					String.valueOf(i) + "点"));
		}
		this.cbbCobBeginTime.setRenderer(CustomBaseRender.getInstance()
				.getRender(30, 50));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				cbbCobBeginTime);
		this.cbbCobBeginTime.setSelectedIndex(-1);

		this.cbbCobEndTime.removeAllItems();
		for (int i = 0; i <= 23; i++) {
			this.cbbCobEndTime.addItem(new ItemProperty(String.valueOf(i),
					String.valueOf(i) + "点"));
		}
		this.cbbCobEndTime.setRenderer(CustomBaseRender.getInstance()
				.getRender(30, 50));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbCobEndTime);
		this.cbbCobEndTime.setSelectedIndex(-1);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("保税物流管理参数设置");
		this.setSize(895, 651);
		this.setContentPane(getJContentPane());
		this.getButtonGroup();
		this.getButtonGroup1();
	}

	private void showData() {
		BlsParameterSet parameterSet = blsMessageAction
				.findBlsParameterSet(new Request(CommonVars.getCurrUser(), true));
		this.tfRemoteHostAddress.setText(parameterSet.getRemoteHostAddress());
		this.tfRemoteHostPort.setText(parameterSet.getRemoteHostPort());
		this.tfSeqNo.setText(parameterSet.getSeqNo());
		this.tfPwd.setText(parameterSet.getPwd());
		this.tfHpServiceUrl.setText(parameterSet.getHpServiceUrl());
		if (parameterSet.getIsInnerUse() == null
				|| !parameterSet.getIsInnerUse()) {
			this.rbCustomsDeclare.setSelected(true);
		} else {
			this.rbInnerUse.setSelected(true);
		}
		if (parameterSet.getIsAutoDeclare() == null
				|| !parameterSet.getIsAutoDeclare()) {
			this.rbHandDeclare.setSelected(true);
		} else {
			this.rbAutoDeclare.setSelected(true);
		}
		if (parameterSet.getIsAutoProcess() == null
				|| !parameterSet.getIsAutoProcess()) {
			this.cbAutoProcess.setSelected(false);
		} else {
			this.cbAutoProcess.setSelected(true);
		}
		showCheckBoxIsSelected(this.cbImpSun, parameterSet
				.getImpWeeklyFrequency(), 0);
		showCheckBoxIsSelected(this.cbImpMon, parameterSet
				.getImpWeeklyFrequency(), 1);
		showCheckBoxIsSelected(this.cbImpTue, parameterSet
				.getImpWeeklyFrequency(), 2);
		showCheckBoxIsSelected(this.cbImpWed, parameterSet
				.getImpWeeklyFrequency(), 3);
		showCheckBoxIsSelected(this.cbImpThu, parameterSet
				.getImpWeeklyFrequency(), 4);
		showCheckBoxIsSelected(this.cbImpFri, parameterSet
				.getImpWeeklyFrequency(), 5);
		showCheckBoxIsSelected(this.cbImpSat, parameterSet
				.getImpWeeklyFrequency(), 6);
		this.tfImpEverydayFrequency.setText(String.valueOf(parameterSet
				.getImpEverydayFrequency()));
		this.cbbImpEverydayFrequencyType.setSelectedIndex(ItemProperty
				.getIndexByCode(String.valueOf(parameterSet
						.getImpEverydayFrequencyType()),
						cbbImpEverydayFrequencyType));
		System.out.println(parameterSet.getImpBeginTime() + "-------"
				+ parameterSet.getImpEndTime());
		this.cbbImpBeginTime
				.setSelectedIndex(parameterSet.getImpBeginTime() == null ? -1
						: ItemProperty.getIndexByCode(String
								.valueOf(parameterSet.getImpBeginTime()),
								cbbImpBeginTime));
		this.cbbImpEndTime
				.setSelectedIndex(parameterSet.getImpEndTime() == null ? -1
						: ItemProperty.getIndexByCode(String
								.valueOf(parameterSet.getImpEndTime()),
								cbbImpEndTime));

		showCheckBoxIsSelected(this.cbExpSun, parameterSet
				.getExpWeeklyFrequency(), 0);
		showCheckBoxIsSelected(this.cbExpMon, parameterSet
				.getExpWeeklyFrequency(), 1);
		showCheckBoxIsSelected(this.cbExpTue, parameterSet
				.getExpWeeklyFrequency(), 2);
		showCheckBoxIsSelected(this.cbExpWed, parameterSet
				.getExpWeeklyFrequency(), 3);
		showCheckBoxIsSelected(this.cbExpThu, parameterSet
				.getExpWeeklyFrequency(), 4);
		showCheckBoxIsSelected(this.cbExpFri, parameterSet
				.getExpWeeklyFrequency(), 5);
		showCheckBoxIsSelected(this.cbExpSat, parameterSet
				.getExpWeeklyFrequency(), 6);
		this.tfExpEverydayFrequency.setText(String.valueOf(parameterSet
				.getExpEverydayFrequency()));
		this.cbbExpEverydayFrequencyType.setSelectedIndex(ItemProperty
				.getIndexByCode(String.valueOf(parameterSet
						.getExpEverydayFrequencyType()),
						cbbExpEverydayFrequencyType));
		System.out.println(parameterSet.getExpBeginTime() + "-------"
				+ parameterSet.getExpEndTime());
		this.cbbExpBeginTime
				.setSelectedIndex(parameterSet.getExpBeginTime() == null ? -1
						: ItemProperty.getIndexByCode(String
								.valueOf(parameterSet.getExpBeginTime()),
								cbbExpBeginTime));
		this.cbbExpEndTime
				.setSelectedIndex(parameterSet.getExpEndTime() == null ? -1
						: ItemProperty.getIndexByCode(String
								.valueOf(parameterSet.getExpEndTime()),
								cbbExpEndTime));

		showCheckBoxIsSelected(this.cbCobSun, parameterSet
				.getCobWeeklyFrequency(), 0);
		showCheckBoxIsSelected(this.cbCobMon, parameterSet
				.getCobWeeklyFrequency(), 1);
		showCheckBoxIsSelected(this.cbCobTue, parameterSet
				.getCobWeeklyFrequency(), 2);
		showCheckBoxIsSelected(this.cbCobWed, parameterSet
				.getCobWeeklyFrequency(), 3);
		showCheckBoxIsSelected(this.cbCobThu, parameterSet
				.getCobWeeklyFrequency(), 4);
		showCheckBoxIsSelected(this.cbCobFri, parameterSet
				.getCobWeeklyFrequency(), 5);
		showCheckBoxIsSelected(this.cbCobSat, parameterSet
				.getCobWeeklyFrequency(), 6);
		this.tfCobEverydayFrequency.setText(String.valueOf(parameterSet
				.getCobEverydayFrequency()));
		this.cbbCobEverydayFrequencyType.setSelectedIndex(ItemProperty
				.getIndexByCode(String.valueOf(parameterSet
						.getCobEverydayFrequencyType()),
						cbbCobEverydayFrequencyType));
		System.out.println(parameterSet.getCobBeginTime() + "-------"
				+ parameterSet.getCobEndTime());
		this.cbbCobBeginTime
				.setSelectedIndex(parameterSet.getCobBeginTime() == null ? -1
						: ItemProperty.getIndexByCode(String
								.valueOf(parameterSet.getCobBeginTime()),
								cbbCobBeginTime));
		this.cbbCobEndTime
				.setSelectedIndex(parameterSet.getCobEndTime() == null ? -1
						: ItemProperty.getIndexByCode(String
								.valueOf(parameterSet.getCobEndTime()),
								cbbCobEndTime));
		this.tfEmsNo.setText(parameterSet.getEmsNO());
	}

	private void showCheckBoxIsSelected(JCheckBox checkBox, String value,
			int index) {
		if (value == null || value.length() < (index + 1)) {
			checkBox.setSelected(false);
		} else {
			String s = value.substring(index, index + 1);
			if ("1".equals(s)) {
				checkBox.setSelected(true);
			} else {
				checkBox.setSelected(false);
			}
		}
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.setBorder(javax.swing.BorderFactory
					.createCompoundBorder(null, null));
			jContentPane.add(getJPanel(), java.awt.BorderLayout.CENTER);
			jContentPane.add(getJPanel1(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJJToolBarBar(), BorderLayout.SOUTH);
		}
		return jContentPane;
	}

	/**
	 * 
	 * This method initializes jPanel
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel63 = new JLabel();
			jLabel63.setBounds(new Rectangle(771, 15, 59, 18));
			jLabel63.setText("账册编号");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setForeground(new java.awt.Color(51, 0, 255));
			jPanel.add(getJPanel41(), null);
			jPanel.add(jLabel63, null);
			jPanel.add(getTfEmsNo(), null);
		}
		return jPanel;
	}

	/**
	 * 
	 * This method initializes jButton
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("保存");
			btnSave.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (rbAutoDeclare.isSelected()) {
						if (!cbImpSun.isSelected() && !cbImpMon.isSelected()
								&& !cbImpTue.isSelected()
								&& !cbImpWed.isSelected()
								&& !cbImpThu.isSelected()
								&& !cbImpFri.isSelected()
								&& !cbImpSat.isSelected()) {
							JOptionPane.showMessageDialog(
									FmBlsParameterSet.this, "请选择入仓申报时间排程的作业周次");
							return;
						}
						if ("".equals(tfImpEverydayFrequency.getText().trim())
								|| cbbImpEverydayFrequencyType
										.getSelectedItem() == null) {
							JOptionPane.showMessageDialog(
									FmBlsParameterSet.this, "请选择入仓申报时间排程的发生周期");
							return;
						}

						if (!cbExpSun.isSelected() && !cbExpMon.isSelected()
								&& !cbExpTue.isSelected()
								&& !cbExpWed.isSelected()
								&& !cbExpThu.isSelected()
								&& !cbExpFri.isSelected()
								&& !cbExpSat.isSelected()) {
							JOptionPane.showMessageDialog(
									FmBlsParameterSet.this, "请选择出仓申报时间排程的作业周次");
							return;
						}
						if ("".equals(tfExpEverydayFrequency.getText().trim())
								|| cbbExpEverydayFrequencyType
										.getSelectedItem() == null) {
							JOptionPane.showMessageDialog(
									FmBlsParameterSet.this, "请选择出仓申报时间排程的发生周期");
							return;
						}

						if (!cbCobSun.isSelected() && !cbCobMon.isSelected()
								&& !cbCobTue.isSelected()
								&& !cbCobWed.isSelected()
								&& !cbCobThu.isSelected()
								&& !cbCobFri.isSelected()
								&& !cbCobSat.isSelected()) {
							JOptionPane.showMessageDialog(
									FmBlsParameterSet.this, "请选择核销申报时间排程的作业周次");
							return;
						}
						if ("".equals(tfCobEverydayFrequency.getText().trim())
								|| cbbCobEverydayFrequencyType
										.getSelectedItem() == null) {
							JOptionPane.showMessageDialog(
									FmBlsParameterSet.this, "请选择核销申报时间排程的发生周期");
							return;
						}
						
						int impFrequency = "".equals(tfImpEverydayFrequency
								.getText().trim()) ? 0 : Integer
								.valueOf(tfImpEverydayFrequency.getText().trim());
						String impFrequencyType = ((ItemProperty) cbbImpEverydayFrequencyType
								.getSelectedItem()).getCode();
						if ("0".equals(impFrequencyType)) {
							if (impFrequency < 0 || impFrequency > 59) {
								JOptionPane.showMessageDialog(
										FmBlsParameterSet.this,
										"如果入仓申报时间排程的发生周期类型选择的是“秒”，那么周期值是0到59的整数。");
								return;
							}
						} else if ("1".equals(impFrequencyType)) {
							if (impFrequency < 0 || impFrequency > 59) {
								JOptionPane.showMessageDialog(
										FmBlsParameterSet.this,
										"如果入仓申报时间排程的发生周期类型选择的是“分”，那么周期值是0到59的整数。");
								return;
							}
						} else if ("2".equals(impFrequencyType)) {
							if (impFrequency < 0 || impFrequency > 23) {
								JOptionPane.showMessageDialog(
										FmBlsParameterSet.this,
										"如果入仓申报时间排程的发生周期类型选择的是“时”，那么周期值是0到23的整数。");
								return;
							}
						}

						int expFrequency = "".equals(tfExpEverydayFrequency
								.getText().trim()) ? 0 : Integer
								.valueOf(tfExpEverydayFrequency.getText().trim());
						String expFrequencyType = ((ItemProperty) cbbExpEverydayFrequencyType
								.getSelectedItem()).getCode();
						if ("0".equals(expFrequencyType)) {
							if (expFrequency < 0 || expFrequency > 59) {
								JOptionPane.showMessageDialog(
										FmBlsParameterSet.this,
										"如果出仓申报时间排程的发生周期类型选择的是“秒”，那么周期值是0到59的整数。");
								return;
							}
						} else if ("1".equals(expFrequencyType)) {
							if (expFrequency < 0 || expFrequency > 59) {
								JOptionPane.showMessageDialog(
										FmBlsParameterSet.this,
										"如果出仓申报时间排程的发生周期类型选择的是“分”，那么周期值是0到59的整数。");
								return;
							}
						} else if ("2".equals(expFrequencyType)) {
							if (expFrequency < 0 || expFrequency > 59) {
								JOptionPane.showMessageDialog(
										FmBlsParameterSet.this,
										"如果出仓申报时间排程的发生周期类型选择的是“时”，那么周期值是0到23的整数。");
								return;
							}
						}

						int cobFrequency = "".equals(tfCobEverydayFrequency
								.getText().trim()) ? 0 : Integer
								.valueOf(tfCobEverydayFrequency.getText().trim());
						String cobFrequencyType = ((ItemProperty) cbbCobEverydayFrequencyType
								.getSelectedItem()).getCode();
						if ("0".equals(cobFrequencyType)) {
							if (cobFrequency < 0 || cobFrequency > 59) {
								JOptionPane.showMessageDialog(
										FmBlsParameterSet.this,
										"如果核销申报时间排程的发生周期类型选择的是“秒”，那么周期值是0到59的整数。");
								return;
							}
						} else if ("1".equals(cobFrequencyType)) {
							if (cobFrequency < 0 || cobFrequency > 59) {
								JOptionPane.showMessageDialog(
										FmBlsParameterSet.this,
										"如果核销申报时间排程的发生周期类型选择的是“分”，那么周期值是0到59的整数。");
								return;
							}
						} else if ("2".equals(cobFrequencyType)) {
							if (cobFrequency < 0 || cobFrequency > 59) {
								JOptionPane.showMessageDialog(
										FmBlsParameterSet.this,
										"如果核销申报时间排程的发生周期类型选择的是“时”，那么周期值是0到23的整数。");
								return;
							}
						}
					}					

					BlsParameterSet parameterSet = blsMessageAction
							.findBlsParameterSet(new Request(CommonVars
									.getCurrUser(), true));
					// parameterSet.setSendDir(tfSendDir.getText().trim());
					// parameterSet.setRecvDir(tfRecvDir.getText().trim());
					// parameterSet.setFinallyDir(tfFinallyDir.getText().trim());
					// parameterSet.setLogDir(tfLogDir.getText().trim());
					parameterSet.setSeqNo(tfSeqNo.getText().trim());
					parameterSet.setPwd(tfPwd.getText().trim());
					// parameterSet.setReadFromCard(rbFromCard.isSelected());
					// parameterSet.setIdCard(tfIdCard.getText().trim());
					// parameterSet.setRemoteSignData(cbRemoteSignData
					// .isSelected());
					// parameterSet.setRemoteDxpMail(rbEnhance.isSelected());
					// parameterSet.setPort9097IsOpen(cbPort9097Open.isSelected());
					parameterSet.setRemoteHostAddress(tfRemoteHostAddress
							.getText());
					parameterSet.setRemoteHostPort(tfRemoteHostPort.getText());
					parameterSet.setIsInnerUse(rbInnerUse.isSelected());
					parameterSet.setHpServiceUrl(tfHpServiceUrl.getText()
							.trim());
					// if (cbbProjectType.getSelectedItem() != null) {
					// ItemProperty item = (ItemProperty) cbbProjectType
					// .getSelectedItem();
					// int projectType = Integer.parseInt(item.getCode());
					// parameterSet.setProjectType(projectType);
					// } else {
					// parameterSet.setProjectType(null);
					// }
					parameterSet.setIsAutoDeclare(rbAutoDeclare.isSelected());
					parameterSet.setIsAutoProcess(cbAutoProcess.isSelected());
					parameterSet
							.setImpWeeklyFrequency(getCheckBoxSelectedValue(cbImpSun)
									+ getCheckBoxSelectedValue(cbImpMon)
									+ getCheckBoxSelectedValue(cbImpTue)
									+ getCheckBoxSelectedValue(cbImpWed)
									+ getCheckBoxSelectedValue(cbImpThu)
									+ getCheckBoxSelectedValue(cbImpFri)
									+ getCheckBoxSelectedValue(cbImpSat));
					parameterSet
							.setImpEverydayFrequency(""
									.equals(tfImpEverydayFrequency.getText()
											.trim()) ? 0 : Integer
									.valueOf(tfImpEverydayFrequency.getText()
											.trim()));
					parameterSet
							.setImpEverydayFrequencyType(cbbImpEverydayFrequencyType
									.getSelectedItem() == null ? -1
									: Integer
											.valueOf(((ItemProperty) cbbImpEverydayFrequencyType
													.getSelectedItem())
													.getCode()));
					parameterSet.setImpBeginTime(cbbImpBeginTime
							.getSelectedItem() == null ? -1 : Integer
							.valueOf(((ItemProperty) cbbImpBeginTime
									.getSelectedItem()).getCode()));
					parameterSet
							.setImpEndTime(cbbImpEndTime.getSelectedItem() == null ? -1
									: Integer
											.valueOf(((ItemProperty) cbbImpEndTime
													.getSelectedItem())
													.getCode()));

					parameterSet
							.setExpWeeklyFrequency(getCheckBoxSelectedValue(cbExpSun)
									+ getCheckBoxSelectedValue(cbExpMon)
									+ getCheckBoxSelectedValue(cbExpTue)
									+ getCheckBoxSelectedValue(cbExpWed)
									+ getCheckBoxSelectedValue(cbExpThu)
									+ getCheckBoxSelectedValue(cbExpFri)
									+ getCheckBoxSelectedValue(cbExpSat));
					parameterSet
							.setExpEverydayFrequency(""
									.equals(tfExpEverydayFrequency.getText()
											.trim()) ? 0 : Integer
									.valueOf(tfExpEverydayFrequency.getText()
											.trim()));
					parameterSet
							.setExpEverydayFrequencyType(cbbExpEverydayFrequencyType
									.getSelectedItem() == null ? -1
									: Integer
											.valueOf(((ItemProperty) cbbExpEverydayFrequencyType
													.getSelectedItem())
													.getCode()));
					parameterSet.setExpBeginTime(cbbExpBeginTime
							.getSelectedItem() == null ? -1 : Integer
							.valueOf(((ItemProperty) cbbExpBeginTime
									.getSelectedItem()).getCode()));
					parameterSet
							.setExpEndTime(cbbExpEndTime.getSelectedItem() == null ? -1
									: Integer
											.valueOf(((ItemProperty) cbbExpEndTime
													.getSelectedItem())
													.getCode()));

					parameterSet
							.setCobWeeklyFrequency(getCheckBoxSelectedValue(cbCobSun)
									+ getCheckBoxSelectedValue(cbCobMon)
									+ getCheckBoxSelectedValue(cbCobTue)
									+ getCheckBoxSelectedValue(cbCobWed)
									+ getCheckBoxSelectedValue(cbCobThu)
									+ getCheckBoxSelectedValue(cbCobFri)
									+ getCheckBoxSelectedValue(cbCobSat));
					parameterSet
							.setCobEverydayFrequency(""
									.equals(tfCobEverydayFrequency.getText()
											.trim()) ? 0 : Integer
									.valueOf(tfCobEverydayFrequency.getText()
											.trim()));
					parameterSet
							.setCobEverydayFrequencyType(cbbCobEverydayFrequencyType
									.getSelectedItem() == null ? -1
									: Integer
											.valueOf(((ItemProperty) cbbCobEverydayFrequencyType
													.getSelectedItem())
													.getCode()));
					parameterSet.setCobBeginTime(cbbCobBeginTime
							.getSelectedItem() == null ? -1 : Integer
							.valueOf(((ItemProperty) cbbCobBeginTime
									.getSelectedItem()).getCode()));
					parameterSet
							.setCobEndTime(cbbCobEndTime.getSelectedItem() == null ? -1
									: Integer
											.valueOf(((ItemProperty) cbbCobEndTime
													.getSelectedItem())
													.getCode()));
					parameterSet.setEmsNO(tfEmsNo.getText().trim());

					blsMessageAction.saveBlsParameterSet(new Request(CommonVars
							.getCurrUser(), true), parameterSet);
					blsMessageAction.refreshCronExpression(new Request(
							CommonVars.getCurrUser(), true));
					dataState = DataState.BROWSE;
					setState();
				}
			});

		}
		return btnSave;
	}

	private String getCheckBoxSelectedValue(JCheckBox checkBox) {
		return checkBox.isSelected() ? "1" : "0";
	}

	/**
	 * 
	 * This method initializes jButton2
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改");
			btnEdit.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					dataState = DataState.EDIT;
					setState();
				}
			});

		}
		return btnEdit;
	}

	/*
	 * 设置组键的状态，默认为浏览状态。
	 */
	private void setState() {

		this.btnSave.setEnabled(!(dataState == DataState.BROWSE));
		this.btnEdit.setEnabled(dataState == DataState.BROWSE);

		// jCheckBox.setEnabled(dataState != DataState.BROWSE);

		// this.rbFromCard.setEnabled(dataState != DataState.BROWSE);
		// this.rbFromManual.setEnabled(dataState != DataState.BROWSE);
		//
		// this.tfFinallyDir.setEditable(dataState != DataState.BROWSE);
		//
		// this.tfLogDir.setEditable(dataState != DataState.BROWSE);
		// this.tfRecvDir.setEditable(dataState != DataState.BROWSE);
		// this.tfSendDir.setEditable(dataState != DataState.BROWSE);

		// this.tfIdCard.setEditable(dataState != DataState.BROWSE);
		this.tfPwd.setEditable(dataState != DataState.BROWSE);
		this.tfSeqNo.setEditable(dataState != DataState.BROWSE);
		// this.btnFinallyDir.setEnabled(dataState != DataState.BROWSE);
		// this.btnLogDir.setEnabled(dataState != DataState.BROWSE);
		// this.btnRecvDir.setEnabled(dataState != DataState.BROWSE);
		// this.btnSendDir.setEnabled(dataState != DataState.BROWSE);

		// this.jTextField.setEditable(!(dataState == DataState.BROWSE));
		// this.jCheckBox.setEnabled(dataState != DataState.BROWSE);
		// this.cbRemoteSignData.setEnabled(dataState != DataState.BROWSE);
		this.tfRemoteHostAddress.setEditable(dataState != DataState.BROWSE);
		this.tfRemoteHostPort.setEditable(dataState != DataState.BROWSE);
		this.rbInnerUse.setEnabled(dataState != DataState.BROWSE);
		this.rbCustomsDeclare.setEnabled(dataState != DataState.BROWSE);
		this.tfHpServiceUrl.setEditable(dataState != DataState.BROWSE);
		// this.rbNormal.setEnabled(this.cbRemoteSignData.isSelected()
		// && dataState != DataState.BROWSE);
		// this.rbEnhance.setEnabled(this.cbRemoteSignData.isSelected()
		// && dataState != DataState.BROWSE);
		// this.cbPort9097Open.setEnabled(this.rbEnhance.isSelected()
		// && dataState != DataState.BROWSE);
		//
		// this.cbbProjectType.setEnabled(dataState != DataState.BROWSE);
		this.btnReadCard.setEnabled(dataState == DataState.BROWSE
				&& this.rbCustomsDeclare.isSelected());

		this.rbAutoDeclare.setEnabled(dataState != DataState.BROWSE);
		this.rbHandDeclare.setEnabled(dataState != DataState.BROWSE);
		this.cbAutoProcess.setEnabled(dataState != DataState.BROWSE
				&& this.rbAutoDeclare.isSelected());

		this.cbImpMon.setEnabled(dataState != DataState.BROWSE
				&& this.rbAutoDeclare.isSelected());
		this.cbImpTue.setEnabled(dataState != DataState.BROWSE
				&& this.rbAutoDeclare.isSelected());
		this.cbImpWed.setEnabled(dataState != DataState.BROWSE
				&& this.rbAutoDeclare.isSelected());
		this.cbImpThu.setEnabled(dataState != DataState.BROWSE
				&& this.rbAutoDeclare.isSelected());
		this.cbImpFri.setEnabled(dataState != DataState.BROWSE
				&& this.rbAutoDeclare.isSelected());
		this.cbImpSat.setEnabled(dataState != DataState.BROWSE
				&& this.rbAutoDeclare.isSelected());
		this.cbImpSun.setEnabled(dataState != DataState.BROWSE
				&& this.rbAutoDeclare.isSelected());

		this.tfImpEverydayFrequency.setEditable(dataState != DataState.BROWSE
				&& this.rbAutoDeclare.isSelected());
		this.cbbImpEverydayFrequencyType
				.setEnabled(dataState != DataState.BROWSE
						&& this.rbAutoDeclare.isSelected());
		this.cbbImpBeginTime.setEnabled(dataState != DataState.BROWSE
				&& this.rbAutoDeclare.isSelected());
		this.cbbImpEndTime.setEnabled(dataState != DataState.BROWSE
				&& this.rbAutoDeclare.isSelected());

		this.cbExpMon.setEnabled(dataState != DataState.BROWSE
				&& this.rbAutoDeclare.isSelected());
		this.cbExpTue.setEnabled(dataState != DataState.BROWSE
				&& this.rbAutoDeclare.isSelected());
		this.cbExpWed.setEnabled(dataState != DataState.BROWSE
				&& this.rbAutoDeclare.isSelected());
		this.cbExpThu.setEnabled(dataState != DataState.BROWSE
				&& this.rbAutoDeclare.isSelected());
		this.cbExpFri.setEnabled(dataState != DataState.BROWSE
				&& this.rbAutoDeclare.isSelected());
		this.cbExpSat.setEnabled(dataState != DataState.BROWSE
				&& this.rbAutoDeclare.isSelected());
		this.cbExpSun.setEnabled(dataState != DataState.BROWSE
				&& this.rbAutoDeclare.isSelected());

		this.tfExpEverydayFrequency.setEditable(dataState != DataState.BROWSE
				&& this.rbAutoDeclare.isSelected());
		this.cbbExpEverydayFrequencyType
				.setEnabled(dataState != DataState.BROWSE
						&& this.rbAutoDeclare.isSelected());
		this.cbbExpBeginTime.setEnabled(dataState != DataState.BROWSE
				&& this.rbAutoDeclare.isSelected());
		this.cbbExpEndTime.setEnabled(dataState != DataState.BROWSE
				&& this.rbAutoDeclare.isSelected());
		
		this.cbCobMon.setEnabled(dataState != DataState.BROWSE
				&& this.rbAutoDeclare.isSelected());
		this.cbCobTue.setEnabled(dataState != DataState.BROWSE
				&& this.rbAutoDeclare.isSelected());
		this.cbCobWed.setEnabled(dataState != DataState.BROWSE
				&& this.rbAutoDeclare.isSelected());
		this.cbCobThu.setEnabled(dataState != DataState.BROWSE
				&& this.rbAutoDeclare.isSelected());
		this.cbCobFri.setEnabled(dataState != DataState.BROWSE
				&& this.rbAutoDeclare.isSelected());
		this.cbCobSat.setEnabled(dataState != DataState.BROWSE
				&& this.rbAutoDeclare.isSelected());
		this.cbCobSun.setEnabled(dataState != DataState.BROWSE
				&& this.rbAutoDeclare.isSelected());

		this.tfCobEverydayFrequency.setEditable(dataState != DataState.BROWSE
				&& this.rbAutoDeclare.isSelected());
		this.cbbCobEverydayFrequencyType
				.setEnabled(dataState != DataState.BROWSE
						&& this.rbAutoDeclare.isSelected());
		this.cbbCobBeginTime.setEnabled(dataState != DataState.BROWSE
				&& this.rbAutoDeclare.isSelected());
		this.cbbCobEndTime.setEnabled(dataState != DataState.BROWSE
				&& this.rbAutoDeclare.isSelected());
		this.tfEmsNo.setEditable(dataState != DataState.BROWSE);
	}

	/**
	 * 
	 * This method initializes jPanel1
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			javax.swing.JLabel jLabel17 = new JLabel();

			javax.swing.JLabel jLabel18 = new JLabel();

			javax.swing.JLabel jLabel19 = new JLabel();

			jPanel1.setLayout(new BorderLayout());
			jLabel17.setText("");
			jLabel17
					.setIcon(new ImageIcon(
							getClass()
									.getResource(
											"/com/bestway/bcus/client/resources/images/titlepoint.jpg")));
			jLabel18.setText("保税物流管理参数设置");
			jLabel18
					.setFont(new Font("\u65b0\u5b8b\u4f53", Font.BOLD, 20));
			jLabel18.setForeground(new java.awt.Color(255, 153, 0));
			jLabel19.setText("");
			jLabel19.setIcon(new ImageIcon(getClass().getResource(
					"/com/bestway/bcus/client/resources/images/titlepic.jpg")));
			jPanel1
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jPanel1.setBackground(java.awt.Color.white);
			jPanel1.add(jLabel17, java.awt.BorderLayout.WEST);
			jPanel1.add(jLabel18, java.awt.BorderLayout.CENTER);
			jPanel1.add(jLabel19, java.awt.BorderLayout.EAST);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("关闭");
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmBlsParameterSet.this.doDefaultCloseAction();
				}
			});
		}
		return btnClose;
	}

	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setAlignment(FlowLayout.CENTER);
			jPanel4 = new JPanel();
			jPanel4.setLayout(flowLayout);
			jPanel4.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			jPanel4.add(getBtnEdit(), null);
			jPanel4.add(getBtnSave(), null);
			jPanel4.add(getBtnClose(), null);
		}
		return jPanel4;
	}

	/**
	 * This method initializes jJToolBarBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJJToolBarBar() {
		if (jJToolBarBar == null) {
			jJToolBarBar = new JToolBar();
			jJToolBarBar.add(getJPanel4());
		}
		return jJToolBarBar;
	}

	/**
	 * This method initializes jPanel41
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel41() {
		if (jPanel41 == null) {
			jPanel41 = new JPanel();
			jPanel41.setLayout(null);
			jPanel41.setBounds(new Rectangle(16, 3, 751, 531));
			jPanel41.add(getJPanel5(), null);
			jPanel41.add(getJPanel2(), null);
			jPanel41.add(getJPanel3(), null);
			jPanel41.add(getJPanel31(), null);
			jPanel41.add(getJPanel311(), null);
		}
		return jPanel41;
	}

	/**
	 * This method initializes tfSeqNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfSeqNo() {
		if (tfSeqNo == null) {
			tfSeqNo = new JTextField();
			tfSeqNo.setBounds(new Rectangle(128, 58, 336, 24));
		}
		return tfSeqNo;
	}

	/**
	 * This method initializes tfPwd
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfPwd() {
		if (tfPwd == null) {
			tfPwd = new JTextField();
			tfPwd.setBounds(new Rectangle(128, 85, 336, 24));
		}
		return tfPwd;
	}

	/**
	 * This method initializes tfRemoteHostAddress
	 * 
	 * @return javax.swing.JTextField
	 */
	private JIPTextField getTfRemoteHostAddress() {
		if (tfRemoteHostAddress == null) {
			tfRemoteHostAddress = new JIPTextField();
			tfRemoteHostAddress.setBounds(new Rectangle(128, 27, 140, 24));
		}
		return tfRemoteHostAddress;
	}

	/**
	 * This method initializes btnReadCard
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnReadCard() {
		if (btnReadCard == null) {
			btnReadCard = new JButton();
			btnReadCard.setText("\u8bfb\u5361\u6d4b\u8bd5");
			btnReadCard.setBounds(new Rectangle(483, 61, 87, 22));
			btnReadCard.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// ICCardInfo cardInfo = fptMessageAction.getICCardInfo(
					// new Request(CommonVars.getCurrUser()), tfSeqNo
					// .getText().trim(), tfPwd.getText().trim());
					// if (cardInfo != null) {
					// StringBuffer sb = new StringBuffer();
					// // sb.append("------------------"+i);
					// sb.append("卡号-------------" + cardInfo.getIcCode()
					// + "\n");
					// sb.append("证书-------------" + cardInfo.getCertSeqNo()
					// + "\n");
					// sb.append("申请者名称--------" + cardInfo.getApplier()
					// + "\n");
					// sb.append("单位名称----------" + cardInfo.getTradeName()
					// + "\n");
					// sb.append("单位类型代码-------" + cardInfo.getTradeType()
					// + "\n");
					// sb.append("单位代码-----------" + cardInfo.getTradeCode()
					// + "\n");
					// sb.append("数字签名-----------"
					// + cardInfo.getSignData().substring(0, 10)
					// + "\n");
					// // System.out.println(sb.toString());
					// JOptionPane.showMessageDialog(FmBlsParameterSet.this,
					// sb.toString(), "提示",
					// JOptionPane.INFORMATION_MESSAGE);
					// } else {
					// JOptionPane.showMessageDialog(FmBlsParameterSet.this,
					// "没有读到卡信息", "提示",
					// JOptionPane.INFORMATION_MESSAGE);
					// }
					JOptionPane.showMessageDialog(FmBlsParameterSet.this,
							"海关接口未确定，所以此功能暂未提供！", "提示",
							JOptionPane.INFORMATION_MESSAGE);
				}
			});
		}
		return btnReadCard;
	}

	private void setDir(JTextField tf) {
		if (tf.getText() != null && !"".equals(tf.getText())) {
			File file = new File(tf.getText());
			if (file.exists())
				setTempDir(tf.getText());
		}
		JFileChooser fileChooser = new JFileChooser(getTempDir());
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int state = fileChooser.showDialog(FmBlsParameterSet.this, "选择路径");
		if (state == JFileChooser.APPROVE_OPTION) {
			fileChooser.getSelectedFile();
			File f = fileChooser.getSelectedFile();
			tf.setText(f.getPath());
			setTempDir(tf.getText());
		}
	}

	public String getTempDir() {
		return tempDir;
	}

	public void setTempDir(String tempDir) {
		this.tempDir = tempDir;
	}

	/**
	 * This method initializes jPanel5
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel5() {
		if (jPanel5 == null) {
			jLabel61 = new JLabel();
			jLabel61.setBounds(new Rectangle(58, 87, 67, 20));
			jLabel61.setText("密码");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(58, 55, 67, 27));
			jLabel5.setText("读卡唯一号");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(284, 29, 29, 20));
			jLabel4.setText("端口");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(58, 29, 37, 22));
			jLabel3.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			jLabel3.setBackground(Color.blue);
			jLabel3.setText("地址");
			jPanel5 = new JPanel();
			jPanel5.setLayout(null);
			jPanel5.setBounds(new Rectangle(693, 39, 45, 49));
			jPanel5.setBorder(BorderFactory.createTitledBorder(null,
					"\u8fdc\u7a0b\u670d\u52a1\u5730\u5740",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), Color.blue));
			jPanel5.setVisible(false);
			jPanel5.add(getTfRemoteHostAddress(), null);
			jPanel5.add(jLabel3, null);
			jPanel5.add(jLabel4, null);
			jPanel5.add(getTfRemoteHostPort(), null);
			jPanel5.add(getTfPwd(), null);
			jPanel5.add(getTfSeqNo(), null);
			jPanel5.add(getBtnReadCard(), null);
			jPanel5.add(jLabel5, null);
			jPanel5.add(jLabel61, null);
		}
		return jPanel5;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfRemoteHostPort() {
		if (tfRemoteHostPort == null) {
			tfRemoteHostPort = new JTextField();
			tfRemoteHostPort.setBounds(new Rectangle(318, 27, 144, 24));
			tfRemoteHostPort.setDocument(new PlainDocument() {
				public void insertString(int offs, String str, AttributeSet a)
						throws BadLocationException {
					for (int i = 0; i < str.length(); i++) {
						if (str.charAt(i) < '0' || str.charAt(i) > '9') {
							return;
						}
					}
					super.insertString(offs, str, a);
				}
			});
		}
		return tfRemoteHostPort;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(70, 45, 67, 18));
			jLabel.setText("服务器地址");
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.setBounds(new Rectangle(87, 7, 597, 102));
			jPanel2.setBorder(BorderFactory.createTitledBorder(null,
					"\u4f7f\u7528\u7c7b\u578b",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), Color.blue));
			jPanel2.add(getRbInnerUse(), null);
			jPanel2.add(getRbCustomsDeclare(), null);
			jPanel2.add(jLabel, null);
			jPanel2.add(getTfHpServiceUrl(), null);
			jPanel2.add(getBtnSwitch(), null);
			jPanel2.add(getRbAutoDeclare(), null);
			jPanel2.add(getRbHandDeclare(), null);
			jPanel2.add(getCbAutoProcess(), null);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbInnerUse() {
		if (rbInnerUse == null) {
			rbInnerUse = new JRadioButton();
			rbInnerUse.setBounds(new Rectangle(59, 18, 113, 21));
			rbInnerUse.setText("内部管理");
		}
		return rbInnerUse;
	}

	/**
	 * This method initializes jRadioButton1
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbCustomsDeclare() {
		if (rbCustomsDeclare == null) {
			rbCustomsDeclare = new JRadioButton();
			rbCustomsDeclare.setBounds(new Rectangle(247, 18, 85, 21));
			rbCustomsDeclare.setSelected(true);
			rbCustomsDeclare.setText("海关申报 ");
		}
		return rbCustomsDeclare;
	}

	/**
	 * This method initializes buttonGroup2
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup() {
		if (buttonGroup == null) {
			buttonGroup = new ButtonGroup();
			buttonGroup.add(this.getRbInnerUse());
			buttonGroup.add(this.getRbCustomsDeclare());
		}
		return buttonGroup;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfHpServiceUrl() {
		if (tfHpServiceUrl == null) {
			tfHpServiceUrl = new JTextField();
			tfHpServiceUrl.setBounds(new Rectangle(137, 44, 383, 24));
		}
		return tfHpServiceUrl;
	}

	/**
	 * This method initializes btnSwitch
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSwitch() {
		if (btnSwitch == null) {
			btnSwitch = new JButton();
			btnSwitch.setBounds(new Rectangle(385, 16, 134, 24));
			btnSwitch.setText("转仓单参数设置");
			btnSwitch.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgSwitchBlsInOutStockToStorageBillParameterSet dg = new DgSwitchBlsInOutStockToStorageBillParameterSet();
					dg.setVisible(true);
				}
			});
		}
		return btnSwitch;
	}

	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jLabel62 = new JLabel();
			jLabel62.setText("到");
			jLabel62.setBounds(new Rectangle(332, 19, 18, 20));
			jLabel1 = new JLabel();
			jLabel1.setText("从");
			jLabel1.setBounds(new Rectangle(240, 19, 19, 20));
			jPanel3 = new JPanel();
			jPanel3.setLayout(null);
			jPanel3.setBounds(new Rectangle(87, 113, 597, 133));
			jPanel3
					.setBorder(BorderFactory
							.createTitledBorder(
									null,
									"\u5165\u4ed3\u81ea\u52a8\u7533\u62a5\u65f6\u95f4\u6392\u7a0b",
									TitledBorder.DEFAULT_JUSTIFICATION,
									TitledBorder.DEFAULT_POSITION, new Font(
											"Dialog", Font.BOLD, 12),
									Color.blue));
			jPanel3.add(getJPanel6(), null);
			jPanel3.add(getJPanel8(), null);
		}
		return jPanel3;
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbAutoDeclare() {
		if (rbAutoDeclare == null) {
			rbAutoDeclare = new JRadioButton();
			rbAutoDeclare.setBounds(new Rectangle(60, 73, 84, 21));
			rbAutoDeclare.setText("自动申报");
			rbAutoDeclare
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							setState();
						}
					});
		}
		return rbAutoDeclare;
	}

	/**
	 * This method initializes jRadioButton1
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbHandDeclare() {
		if (rbHandDeclare == null) {
			rbHandDeclare = new JRadioButton();
			rbHandDeclare.setBounds(new Rectangle(162, 73, 88, 21));
			rbHandDeclare.setSelected(true);
			rbHandDeclare.setText("手动申报");
			rbHandDeclare
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							setState();
						}
					});
		}
		return rbHandDeclare;
	}

	/**
	 * This method initializes buttonGroup1
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup1() {
		if (buttonGroup1 == null) {
			buttonGroup1 = new ButtonGroup();
			buttonGroup1.add(this.getRbAutoDeclare());
			buttonGroup1.add(this.getRbHandDeclare());
		}
		return buttonGroup1;
	}

	/**
	 * This method initializes jPanel6
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel6() {
		if (jPanel6 == null) {
			jPanel6 = new JPanel();
			jPanel6.setLayout(null);
			jPanel6.setBounds(new Rectangle(58, 22, 469, 44));
			jPanel6.setBorder(BorderFactory.createTitledBorder(null,
					"\u4f5c\u4e1a\u5468\u6b21",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), new Color(51, 51, 51)));
			jPanel6.add(getCbImpMon(), null);
			jPanel6.add(getCbImpTue(), null);
			jPanel6.add(getCbImpWed(), null);
			jPanel6.add(getCbImpThu(), null);
			jPanel6.add(getCbImpFri(), null);
			jPanel6.add(getCbImpSat(), null);
			jPanel6.add(getCbImpSun(), null);
		}
		return jPanel6;
	}

	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbImpMon() {
		if (cbImpMon == null) {
			cbImpMon = new JCheckBox();
			cbImpMon.setBounds(new Rectangle(40, 17, 55, 21));
			cbImpMon.setText("周一");
		}
		return cbImpMon;
	}

	/**
	 * This method initializes jCheckBox1
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbImpTue() {
		if (cbImpTue == null) {
			cbImpTue = new JCheckBox();
			cbImpTue.setBounds(new Rectangle(99, 17, 55, 21));
			cbImpTue.setText("周二");
		}
		return cbImpTue;
	}

	/**
	 * This method initializes jCheckBox2
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbImpWed() {
		if (cbImpWed == null) {
			cbImpWed = new JCheckBox();
			cbImpWed.setBounds(new Rectangle(165, 17, 55, 21));
			cbImpWed.setText("周三");
		}
		return cbImpWed;
	}

	/**
	 * This method initializes jCheckBox3
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbImpThu() {
		if (cbImpThu == null) {
			cbImpThu = new JCheckBox();
			cbImpThu.setBounds(new Rectangle(221, 17, 55, 21));
			cbImpThu.setText("周四");
		}
		return cbImpThu;
	}

	/**
	 * This method initializes jCheckBox4
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbImpFri() {
		if (cbImpFri == null) {
			cbImpFri = new JCheckBox();
			cbImpFri.setBounds(new Rectangle(275, 17, 55, 21));
			cbImpFri.setText("周五");
		}
		return cbImpFri;
	}

	/**
	 * This method initializes jCheckBox5
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbImpSat() {
		if (cbImpSat == null) {
			cbImpSat = new JCheckBox();
			cbImpSat.setBounds(new Rectangle(335, 17, 55, 21));
			cbImpSat.setText("周六");
		}
		return cbImpSat;
	}

	/**
	 * This method initializes jCheckBox6
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbImpSun() {
		if (cbImpSun == null) {
			cbImpSun = new JCheckBox();
			cbImpSun.setBounds(new Rectangle(402, 17, 55, 21));
			cbImpSun.setText("周日");
		}
		return cbImpSun;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbImpBeginTime() {
		if (cbbImpBeginTime == null) {
			cbbImpBeginTime = new JComboBox();
			cbbImpBeginTime.setBounds(new Rectangle(263, 19, 65, 20));
		}
		return cbbImpBeginTime;
	}

	/**
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbImpEndTime() {
		if (cbbImpEndTime == null) {
			cbbImpEndTime = new JComboBox();
			cbbImpEndTime.setBounds(new Rectangle(355, 19, 68, 20));
		}
		return cbbImpEndTime;
	}

	/**
	 * This method initializes jPanel8
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel8() {
		if (jPanel8 == null) {
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(31, 20, 30, 18));
			jLabel2.setText("周期");
			jPanel8 = new JPanel();
			jPanel8.setLayout(null);
			jPanel8.setBounds(new Rectangle(58, 70, 469, 49));
			jPanel8.setBorder(BorderFactory.createTitledBorder(null,
					"\u53d1\u751f\u5468\u671f",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), new Color(51, 51, 51)));
			jPanel8.add(getTfImpEverydayFrequency(), null);
			jPanel8.add(getCbbImpEverydayFrequencyType(), null);
			jPanel8.add(jLabel1, null);
			jPanel8.add(getCbbImpBeginTime(), null);
			jPanel8.add(jLabel62, null);
			jPanel8.add(getCbbImpEndTime(), null);
			jPanel8.add(jLabel2, null);
		}
		return jPanel8;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfImpEverydayFrequency() {
		if (tfImpEverydayFrequency == null) {
			tfImpEverydayFrequency = new JTextField();
			tfImpEverydayFrequency.setBounds(new Rectangle(61, 19, 51, 20));
			tfImpEverydayFrequency.setDocument(new IntegerDocument());
		}
		return tfImpEverydayFrequency;
	}

	class IntegerDocument extends PlainDocument {

		// private int limit;
		//
		// private int min;
		//
		// private int max;

		public IntegerDocument() {// int limit, int min, int max
			super();
			// this.parentComponent = parentComponent;
			// this.limit = limit;
			// this.min = min;
			// this.max = max;
		}

		/** */
		/**
		 * offset 输入位置 str 输入字符串.有多个输入的情况,如粘贴剪贴板中的内容 attr 总是null
		 */
		public void insertString(int offset, String str, AttributeSet attr)
				throws BadLocationException {
			if (str == null) {
				return;
			}
			// if (((getText(0, getLength()).trim()).length() + str.length()) <=
			// limit) {

			char[] upper = str.toCharArray();
			int length = 0;
			for (int i = 0; i < upper.length; i++) {
				if (upper[i] >= '0' && upper[i] <= '9') {
					upper[length++] = upper[i];
				}
			}
			String valueStr = getText(0, offset) + new String(upper, 0, length)
					+ getText(offset, getLength() - offset).trim();
			if (!valueStr.equals("")) {
				int value = Integer.parseInt(valueStr.trim());
				// if (value >= min && value <= max) {
				super.remove(0, getLength());
				super.insertString(0, String.valueOf(value), attr);
				// } else {
				// if (parentComponent == null) {
				// parentComponent = JOptionPane.getRootFrame();
				// }
				// JOptionPane.showMessageDialog(null, "非法输入",
				// "WARNING", JOptionPane.WARNING_MESSAGE);
				// super.remove(0, getLength());
				// }
			}

			// }
		}
		//
		// public int getLimit() {
		// return limit;
		// }
		//
		// public void setLimit(int limit) {
		// this.limit = limit;
		// }
		//
		// public int getMax() {
		// return max;
		// }
		//
		// public void setMax(int max) {
		// this.max = max;
		// }
		//
		// public int getMin() {
		// return min;
		// }
		//
		// public void setMin(int min) {
		// this.min = min;
		// }
	}

	/**
	 * This method initializes jComboBox2
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbImpEverydayFrequencyType() {
		if (cbbImpEverydayFrequencyType == null) {
			cbbImpEverydayFrequencyType = new JComboBox();
			cbbImpEverydayFrequencyType
					.setBounds(new Rectangle(116, 19, 70, 20));
		}
		return cbbImpEverydayFrequencyType;
	}

	/**
	 * This method initializes jPanel31
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel31() {
		if (jPanel31 == null) {
			jPanel31 = new JPanel();
			jPanel31.setLayout(null);
			jPanel31.setBounds(new Rectangle(88, 253, 597, 126));
			jPanel31
					.setBorder(BorderFactory
							.createTitledBorder(
									null,
									"\u51fa\u4ed3\u81ea\u52a8\u7533\u62a5\u65f6\u95f4\u6392\u7a0b",
									TitledBorder.DEFAULT_JUSTIFICATION,
									TitledBorder.DEFAULT_POSITION, new Font(
											"Dialog", Font.BOLD, 12),
									Color.blue));
			jPanel31.add(getJPanel61(), null);
			jPanel31.add(getJPanel81(), null);
		}
		return jPanel31;
	}

	/**
	 * This method initializes jPanel61
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel61() {
		if (jPanel61 == null) {
			jPanel61 = new JPanel();
			jPanel61.setLayout(null);
			jPanel61.setBounds(new Rectangle(57, 23, 474, 43));
			jPanel61.setBorder(BorderFactory.createTitledBorder(null,
					"\u4f5c\u4e1a\u5468\u6b21",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), new Color(51, 51, 51)));
			jPanel61.add(getCbExpMon(), null);
			jPanel61.add(getCbExpTue(), null);
			jPanel61.add(getCbExpWed(), null);
			jPanel61.add(getCbExpThu(), null);
			jPanel61.add(getCbExpFri(), null);
			jPanel61.add(getCbExpSat(), null);
			jPanel61.add(getCbExpSun(), null);
		}
		return jPanel61;
	}

	/**
	 * This method initializes cbImpMon1
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbExpMon() {
		if (cbExpMon == null) {
			cbExpMon = new JCheckBox();
			cbExpMon.setBounds(new Rectangle(41, 17, 55, 21));
			cbExpMon.setText("\u5468\u4e00");
		}
		return cbExpMon;
	}

	/**
	 * This method initializes cbImpTue1
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbExpTue() {
		if (cbExpTue == null) {
			cbExpTue = new JCheckBox();
			cbExpTue.setBounds(new Rectangle(100, 17, 55, 21));
			cbExpTue.setText("\u5468\u4e8c");
		}
		return cbExpTue;
	}

	/**
	 * This method initializes cbImpWed1
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbExpWed() {
		if (cbExpWed == null) {
			cbExpWed = new JCheckBox();
			cbExpWed.setBounds(new Rectangle(166, 17, 55, 21));
			cbExpWed.setText("\u5468\u4e09");
		}
		return cbExpWed;
	}

	/**
	 * This method initializes cbImpThu1
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbExpThu() {
		if (cbExpThu == null) {
			cbExpThu = new JCheckBox();
			cbExpThu.setBounds(new Rectangle(222, 17, 55, 21));
			cbExpThu.setText("\u5468\u56db");
		}
		return cbExpThu;
	}

	/**
	 * This method initializes cbImpFri1
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbExpFri() {
		if (cbExpFri == null) {
			cbExpFri = new JCheckBox();
			cbExpFri.setBounds(new Rectangle(276, 17, 55, 21));
			cbExpFri.setText("\u5468\u4e94");
		}
		return cbExpFri;
	}

	/**
	 * This method initializes cbImpSat1
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbExpSat() {
		if (cbExpSat == null) {
			cbExpSat = new JCheckBox();
			cbExpSat.setBounds(new Rectangle(336, 17, 55, 21));
			cbExpSat.setText("\u5468\u516d");
		}
		return cbExpSat;
	}

	/**
	 * This method initializes cbImpSun1
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbExpSun() {
		if (cbExpSun == null) {
			cbExpSun = new JCheckBox();
			cbExpSun.setBounds(new Rectangle(403, 17, 55, 21));
			cbExpSun.setText("\u5468\u65e5");
		}
		return cbExpSun;
	}

	/**
	 * This method initializes jPanel81
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel81() {
		if (jPanel81 == null) {
			jLabel21 = new JLabel();
			jLabel21.setBounds(new Rectangle(32, 18, 30, 18));
			jLabel21.setText("\u5468\u671f");
			jLabel621 = new JLabel();
			jLabel621.setBounds(new Rectangle(333, 17, 18, 20));
			jLabel621.setText("\u5230");
			jLabel11 = new JLabel();
			jLabel11.setBounds(new Rectangle(241, 17, 19, 20));
			jLabel11.setText("\u4ece");
			jPanel81 = new JPanel();
			jPanel81.setLayout(null);
			jPanel81.setBounds(new Rectangle(57, 71, 474, 46));
			jPanel81.setBorder(BorderFactory.createTitledBorder(null,
					"\u53d1\u751f\u5468\u671f",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), new Color(51, 51, 51)));
			jPanel81.add(getTfExpEverydayFrequency(), null);
			jPanel81.add(getCbbExpEverydayFrequencyType(), null);
			jPanel81.add(jLabel11, null);
			jPanel81.add(getCbbExpBeginTime(), null);
			jPanel81.add(jLabel621, null);
			jPanel81.add(getCbbExpEndTime(), null);
			jPanel81.add(jLabel21, null);
		}
		return jPanel81;
	}

	/**
	 * This method initializes tfImpEverydayFrequency1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfExpEverydayFrequency() {
		if (tfExpEverydayFrequency == null) {
			tfExpEverydayFrequency = new JTextField();
			tfExpEverydayFrequency.setBounds(new Rectangle(62, 17, 51, 20));
			tfExpEverydayFrequency.setDocument(new IntegerDocument());
		}
		return tfExpEverydayFrequency;
	}

	/**
	 * This method initializes cbbImpEverydayFrequencyType1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbExpEverydayFrequencyType() {
		if (cbbExpEverydayFrequencyType == null) {
			cbbExpEverydayFrequencyType = new JComboBox();
			cbbExpEverydayFrequencyType
					.setBounds(new Rectangle(117, 17, 70, 20));
		}
		return cbbExpEverydayFrequencyType;
	}

	/**
	 * This method initializes cbbImpBeginTime1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbExpBeginTime() {
		if (cbbExpBeginTime == null) {
			cbbExpBeginTime = new JComboBox();
			cbbExpBeginTime.setBounds(new Rectangle(264, 17, 65, 20));
		}
		return cbbExpBeginTime;
	}

	/**
	 * This method initializes cbbImpEndTime1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbExpEndTime() {
		if (cbbExpEndTime == null) {
			cbbExpEndTime = new JComboBox();
			cbbExpEndTime.setBounds(new Rectangle(356, 17, 68, 20));
		}
		return cbbExpEndTime;
	}

	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbAutoProcess() {
		if (cbAutoProcess == null) {
			cbAutoProcess = new JCheckBox();
			cbAutoProcess.setBounds(new Rectangle(260, 71, 259, 21));
			cbAutoProcess.setText("在自动申报时，同时自动进行回执处理");
		}
		return cbAutoProcess;
	}

	/**
	 * This method initializes jPanel311
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel311() {
		if (jPanel311 == null) {
			jPanel311 = new JPanel();
			jPanel311.setLayout(null);
			jPanel311
					.setBorder(BorderFactory
							.createTitledBorder(
									null,
									"\u6838\u9500\u81ea\u52a8\u7533\u62a5\u65f6\u95f4\u6392\u7a0b",
									TitledBorder.DEFAULT_JUSTIFICATION,
									TitledBorder.DEFAULT_POSITION, new Font(
											"Dialog", Font.BOLD, 12),
									Color.blue));
			jPanel311.setBounds(new Rectangle(89, 386, 598, 132));
			jPanel311.add(getJPanel611(), null);
			jPanel311.add(getJPanel811(), null);
		}
		return jPanel311;
	}

	/**
	 * This method initializes jPanel611
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel611() {
		if (jPanel611 == null) {
			jPanel611 = new JPanel();
			jPanel611.setLayout(null);
			jPanel611.setBounds(new Rectangle(58, 26, 475, 44));
			jPanel611.setBorder(BorderFactory.createTitledBorder(null,
					"\u4f5c\u4e1a\u5468\u6b21",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), new Color(51, 51, 51)));
			jPanel611.add(getCbCobMon(), null);
			jPanel611.add(getCbCobTue(), null);
			jPanel611.add(getCbCobWed(), null);
			jPanel611.add(getCbCobThu(), null);
			jPanel611.add(getCbCobFri(), null);
			jPanel611.add(getCbCobSat(), null);
			jPanel611.add(getCbCobSun(), null);
		}
		return jPanel611;
	}

	/**
	 * This method initializes cbExpMon1
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbCobMon() {
		if (cbCobMon == null) {
			cbCobMon = new JCheckBox();
			cbCobMon.setBounds(new Rectangle(40, 18, 55, 21));
			cbCobMon.setText("\u5468\u4e00");
		}
		return cbCobMon;
	}

	/**
	 * This method initializes cbExpTue1
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbCobTue() {
		if (cbCobTue == null) {
			cbCobTue = new JCheckBox();
			cbCobTue.setBounds(new Rectangle(99, 18, 55, 21));
			cbCobTue.setText("\u5468\u4e8c");
		}
		return cbCobTue;
	}

	/**
	 * This method initializes cbExpWed1
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbCobWed() {
		if (cbCobWed == null) {
			cbCobWed = new JCheckBox();
			cbCobWed.setBounds(new Rectangle(165, 18, 55, 21));
			cbCobWed.setText("\u5468\u4e09");
		}
		return cbCobWed;
	}

	/**
	 * This method initializes cbExpThu1
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbCobThu() {
		if (cbCobThu == null) {
			cbCobThu = new JCheckBox();
			cbCobThu.setBounds(new Rectangle(221, 18, 55, 21));
			cbCobThu.setText("\u5468\u56db");
		}
		return cbCobThu;
	}

	/**
	 * This method initializes cbExpFri1
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbCobFri() {
		if (cbCobFri == null) {
			cbCobFri = new JCheckBox();
			cbCobFri.setBounds(new Rectangle(275, 18, 55, 21));
			cbCobFri.setText("\u5468\u4e94");
		}
		return cbCobFri;
	}

	/**
	 * This method initializes cbExpSat1
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbCobSat() {
		if (cbCobSat == null) {
			cbCobSat = new JCheckBox();
			cbCobSat.setBounds(new Rectangle(335, 18, 55, 21));
			cbCobSat.setText("\u5468\u516d");
		}
		return cbCobSat;
	}

	/**
	 * This method initializes cbExpSun1
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbCobSun() {
		if (cbCobSun == null) {
			cbCobSun = new JCheckBox();
			cbCobSun.setBounds(new Rectangle(402, 18, 55, 21));
			cbCobSun.setText("\u5468\u65e5");
		}
		return cbCobSun;
	}

	/**
	 * This method initializes jPanel811
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel811() {
		if (jPanel811 == null) {
			jLabel211 = new JLabel();
			jLabel211.setBounds(new Rectangle(29, 19, 30, 18));
			jLabel211.setText("\u5468\u671f");
			jLabel6211 = new JLabel();
			jLabel6211.setBounds(new Rectangle(330, 18, 18, 20));
			jLabel6211.setText("\u5230");
			jLabel111 = new JLabel();
			jLabel111.setBounds(new Rectangle(238, 18, 19, 20));
			jLabel111.setText("\u4ece");
			jPanel811 = new JPanel();
			jPanel811.setLayout(null);
			jPanel811.setBounds(new Rectangle(58, 74, 475, 46));
			jPanel811.setBorder(BorderFactory.createTitledBorder(null,
					"\u53d1\u751f\u5468\u671f",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), new Color(51, 51, 51)));
			jPanel811.add(getTfCobEverydayFrequency(), null);
			jPanel811.add(getCbbCobEverydayFrequencyType(), null);
			jPanel811.add(jLabel111, null);
			jPanel811.add(getCbbCobBeginTime(), null);
			jPanel811.add(jLabel6211, null);
			jPanel811.add(getCbbCobEndTime(), null);
			jPanel811.add(jLabel211, null);
		}
		return jPanel811;
	}

	/**
	 * This method initializes tfExpEverydayFrequency1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCobEverydayFrequency() {
		if (tfCobEverydayFrequency == null) {
			tfCobEverydayFrequency = new JTextField();
			tfCobEverydayFrequency.setBounds(new Rectangle(59, 18, 51, 20));
			tfCobEverydayFrequency.setDocument(new IntegerDocument());
		}
		return tfCobEverydayFrequency;
	}

	/**
	 * This method initializes cbbExpEverydayFrequencyType1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCobEverydayFrequencyType() {
		if (cbbCobEverydayFrequencyType == null) {
			cbbCobEverydayFrequencyType = new JComboBox();
			cbbCobEverydayFrequencyType.setBounds(new Rectangle(114, 18, 70, 20));
		}
		return cbbCobEverydayFrequencyType;
	}

	/**
	 * This method initializes cbbExpBeginTime1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCobBeginTime() {
		if (cbbCobBeginTime == null) {
			cbbCobBeginTime = new JComboBox();
			cbbCobBeginTime.setBounds(new Rectangle(261, 18, 65, 20));
		}
		return cbbCobBeginTime;
	}

	/**
	 * This method initializes cbbExpEndTime1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCobEndTime() {
		if (cbbCobEndTime == null) {
			cbbCobEndTime = new JComboBox();
			cbbCobEndTime.setBounds(new Rectangle(353, 18, 68, 20));
		}
		return cbbCobEndTime;
	}

	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfEmsNo() {
		if (tfEmsNo == null) {
			tfEmsNo = new JTextField();
			tfEmsNo.setBounds(new Rectangle(830, 13, 126, 22));
		}
		return tfEmsNo;
	}

} // @jve:decl-index=0:visual-constraint="10,16"
