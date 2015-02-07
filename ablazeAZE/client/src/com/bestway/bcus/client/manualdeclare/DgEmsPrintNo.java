/*
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.manualdeclare;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;
import javax.swing.SwingConstants;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Rectangle;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.BorderFactory;

import org.apache.commons.lang.math.NumberUtils;

/**
 * @author Administrator
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class DgEmsPrintNo extends JDialogBase {

	private JPanel jContentPane = null;

	private JFormattedTextField tfBegin = null;

	private JButton btnOk = null;

	private JButton btnCancel = null;

	private boolean ok = false;

	private JFormattedTextField tfEnd = null;
	/**
	 * 是否显示间隔打印部分版本
	 */
	private boolean isShow = false;

	public boolean isShow() {
		return isShow;
	}

	public void setShow(boolean isShow) {
		this.isShow = isShow;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgEmsPrintNo() {
		super();
		initialize();
	}

	public void setVisible(boolean b) {
		if (b) {
			setState();
		}
		super.setVisible(b);
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(422, 322));
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("按备案序号打印");
		this.setContentPane(getJContentPane());
		getButtonGroup();
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel3 = new JLabel();
			jLabel3.setForeground(Color.blue);
			jLabel3.setBounds(new Rectangle(204, 80, 168, 26));
			jLabel3.setText("例如：序号1，序号2，序号3.....");
			jLabel2 = new JLabel();
			jLabel2.setText("备案序号");
			jLabel2.setBounds(new Rectangle(37, 46, 48, 18));
			jLabel1 = new JLabel();
			jLabel1.setText("到");
			jLabel1.setBounds(new Rectangle(216, 46, 12, 18));
			jLabel = new JLabel();
			jLabel.setText("备案序号");
			jLabel.setBounds(new Rectangle(37, 116, 48, 18));
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getBtnOk(), null);
			jContentPane.add(getBtnCancel(), null);
			jContentPane.add(getJPanel(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes tf
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfBegin() {
		if (tfBegin == null) {
			tfBegin = new JFormattedTextField();
			tfBegin.setBounds(new Rectangle(98, 46, 110, 22));
			tfBegin.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfBegin;
	}

	/**
	 * This method initializes defaultFormatterFactory
	 * 
	 * @return javax.swing.text.DefaultFormatterFactory
	 */
	private DefaultFormatterFactory defaultFormatterFactory = null;

	private DefaultFormatterFactory getDefaultFormatterFactory() {
		if (defaultFormatterFactory == null) {
			defaultFormatterFactory = new DefaultFormatterFactory();
			defaultFormatterFactory.setDisplayFormatter(getNumberFormatter());
			defaultFormatterFactory.setEditFormatter(getNumberFormatter());
		}
		return defaultFormatterFactory;
	}

	/**
	 * This method initializes numberFormatter
	 * 
	 * @return javax.swing.text.NumberFormatter
	 */
	private NumberFormatter numberFormatter = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	private JTextField tfBomAllVersion = null;

	private JRadioButton rbContinuPrint = null;

	private JRadioButton rbIntervalAllVersionPrint = null;

	private JLabel jLabel3 = null;

	private ButtonGroup buttonGroup = null; // @jve:decl-index=0:visual-constraint="378,27"

	private JPanel jPanel = null;

	private JRadioButton rbIntervalSingleVersionPrint = null;

	private JLabel jLabel31 = null;

	private JLabel jLabel4 = null;

	private JTextField tfBomSinglVersion = null;

	private String selectPrintString = "1"; // @jve:decl-index=0:

	private NumberFormatter getNumberFormatter() {
		if (numberFormatter == null) {
			numberFormatter = new NumberFormatter();
		}
		return numberFormatter;
	}

	public String getSelectPrintString() {
		return selectPrintString;
	}

	public void setSelectPrintString(String selectPrintString) {
		this.selectPrintString = selectPrintString;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton();
			btnOk.setBounds(new Rectangle(118, 259, 58, 22));
			btnOk.setText("确定");
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ok = true;
					dispose();
				}
			});
		}
		return btnOk;
	}

	/**
	 * This method initializes btnCancel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(new Rectangle(196, 259, 58, 22));
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ok = false;
					dispose();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * 获得开始备案序号
	 * 
	 * @return
	 */
	public int getBeginNo() {
		int returnInt = -1;
		if (tfBegin.getValue() != null) {
			Double d = Double.valueOf(tfBegin.getValue().toString());
			returnInt = d.intValue();
		}
		return returnInt;
	}

	/**
	 * 获得结束备案序号
	 * 
	 * @return
	 */
	public int getEndNo() {
		int returnInt = -1;
		if (tfEnd.getValue() != null) {
			Double d = Double.valueOf(tfEnd.getValue().toString());
			returnInt = d.intValue();
		}
		return returnInt;
	}

	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	/**
	 * This method initializes jCustomFormattedTextField
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JFormattedTextField getTfEnd() {
		if (tfEnd == null) {
			tfEnd = new JFormattedTextField();
			tfEnd.setBounds(new Rectangle(240, 46, 110, 22));
			tfEnd.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfEnd;
	}

	/**
	 * This method initializes tfBomAllVersion
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfBomAllVersion() {
		if (tfBomAllVersion == null) {
			tfBomAllVersion = new JTextField();
			tfBomAllVersion.setBounds(new Rectangle(98, 112, 249, 22));
		}
		return tfBomAllVersion;
	}

	/**
	 * This method initializes rbContinuPrint
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbContinuPrint() {
		if (rbContinuPrint == null) {
			rbContinuPrint = new JRadioButton();
			rbContinuPrint.setSelected(true);
			rbContinuPrint.setBounds(new Rectangle(14, 12, 73, 26));
			rbContinuPrint.setText("连续打印");
			rbContinuPrint.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					setState();
					setSelectPrintString("1");
				}
			});
		}
		return rbContinuPrint;
	}

	/**
	 * This method initializes rbIntervalAllVersionPrint
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbIntervalAllVersionPrint() {
		if (rbIntervalAllVersionPrint == null) {
			rbIntervalAllVersionPrint = new JRadioButton();
			rbIntervalAllVersionPrint.setText("间隔打印 序号之间以逗号间隔 ");
			rbIntervalAllVersionPrint.setBounds(new Rectangle(14, 80, 192, 26));
			rbIntervalAllVersionPrint
					.addItemListener(new java.awt.event.ItemListener() {
						public void itemStateChanged(java.awt.event.ItemEvent e) {
							setState();
							setSelectPrintString("2");
						}
					});
		}
		return rbIntervalAllVersionPrint;
	}

	/**
	 * This method initializes buttonGroup
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup() {
		if (buttonGroup == null) {
			buttonGroup = new ButtonGroup();
			buttonGroup.add(this.getRbContinuPrint());
			buttonGroup.add(this.getRbIntervalAllVersionPrint());
			buttonGroup.add(this.getRbIntervalSingleVersionPrint());
		}
		return buttonGroup;
	}

	private void setState() {
		this.tfBegin.setEditable(this.rbContinuPrint.isSelected());
		this.tfEnd.setEditable(this.rbContinuPrint.isSelected());
		this.tfBomAllVersion.setEditable(this.rbIntervalAllVersionPrint
				.isSelected());
		this.tfBomSinglVersion.setEditable(this.rbIntervalSingleVersionPrint
				.isSelected()
				&& isShow);
		this.rbIntervalSingleVersionPrint.setEnabled(isShow);
		jLabel31.setEnabled(isShow);
		jLabel4.setEnabled(isShow);
	}

	public Integer[] getSeqNumArray() {
		return CommonVars.getIntegerArrayBySplit(this.tfBomAllVersion.getText()
				.trim(), ",");
	}

	public String getSeqNum() {
		return this.tfBomAllVersion.getText().trim();
	}

	public String getAllSeqNum() {
		String returnString = "";
		String text = this.tfBomSinglVersion.getText().trim();
		String[] strArray = text.trim().split(",");
		for (int i = 0; i < strArray.length; i++) {
			if (strArray[i] != null && !"".equals(strArray[i].trim())) {
				String str = "";
				if (!"".equals(returnString)) {
					if (strArray[i].indexOf("[") >= 0) {
						str = strArray[i]
								.substring(0, strArray[i].indexOf("["));
					} else {
						str = strArray[i];
					}
					returnString = returnString + "," + str;
				} else {
					if (strArray[i].indexOf("[") >= 0) {
						str = strArray[i]
								.substring(0, strArray[i].indexOf("["));
					} else {
						str = strArray[i];
					}
					returnString = str;
				}
			}
		}
		return returnString;
	}

	public Map<Integer, List<Integer>> getAllSeqNumArray() {
		String text = this.tfBomSinglVersion.getText().trim();
		Map<Integer, List<Integer>> mapAllSeqNum = new HashMap<Integer, List<Integer>>();
		String[] strArray = text.trim().split(",");
		for (int i = 0; i < strArray.length; i++) {
			if (strArray[i].indexOf("[") >= 0 && strArray[i].indexOf("]") >= 0) {
				if (NumberUtils.isNumber(strArray[i].substring(0, strArray[i]
						.indexOf("[")))) {
					Integer strExg = Integer.valueOf(strArray[i].substring(0,
							strArray[i].indexOf("[")));
					String strVersion = strArray[i].substring(strArray[i]
							.indexOf("[") + 1, strArray[i].indexOf("]"));
					List<Integer> listVersion = new ArrayList();
					String[] strVerArray = strVersion.trim().split(";");
					for (int j = 0; j < strVerArray.length; j++) {
						if (strVerArray[j] != null
								&& !"".equals(strVerArray[j].trim())) {
							if (NumberUtils.isNumber(strVerArray[j])) {
								listVersion
										.add(Integer.valueOf(strVerArray[j]));
							}
						}
					}
					mapAllSeqNum.put(strExg, listVersion);
				}
			} else {
				if (NumberUtils.isNumber(strArray[i])) {
					mapAllSeqNum.put(Integer.valueOf(strArray[i]), null);
				}
			}
		}
		return mapAllSeqNum;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(37, 210, 48, 18));
			jLabel4.setText("备案序号");
			jLabel31 = new JLabel();
			jLabel31.setBounds(new Rectangle(37, 172, 337, 26));
			jLabel31.setText(" 例如：序号1[版本0 ; 版本1...]，序号2，序号3[版本0]....");
			jLabel31.setForeground(Color.blue);
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setBounds(new Rectangle(16, 11, 379, 237));
			jPanel.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
			jPanel.add(getRbContinuPrint(), null);
			jPanel.add(getRbIntervalAllVersionPrint(), null);
			jPanel.add(getTfBomAllVersion(), null);
			jPanel.add(jLabel2, new GridBagConstraints());
			jPanel.add(getTfBegin(), null);
			jPanel.add(getTfEnd(), null);
			jPanel.add(jLabel, null);
			jPanel.add(jLabel1, null);
			jPanel.add(jLabel3, null);
			jPanel.add(getRbIntervalSingleVersionPrint(), null);
			jPanel.add(jLabel31, null);
			jPanel.add(jLabel4, null);
			jPanel.add(getTfBomSinglVersion(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes rbIntervalSingleVersionPrint
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbIntervalSingleVersionPrint() {
		if (rbIntervalSingleVersionPrint == null) {
			rbIntervalSingleVersionPrint = new JRadioButton();
			rbIntervalSingleVersionPrint.setBounds(new Rectangle(14, 150, 363,
					26));
			rbIntervalSingleVersionPrint
					.setText("间隔打印(部分版本)序号之间以逗号间隔，版本之间以分号间隔");
			rbIntervalSingleVersionPrint
					.addItemListener(new java.awt.event.ItemListener() {
						public void itemStateChanged(java.awt.event.ItemEvent e) {
							setState();
							setSelectPrintString("3");
						}
					});
		}
		return rbIntervalSingleVersionPrint;
	}

	/**
	 * This method initializes tfBomSinglVersion
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfBomSinglVersion() {
		if (tfBomSinglVersion == null) {
			tfBomSinglVersion = new JTextField();
			tfBomSinglVersion.setBounds(new Rectangle(98, 206, 249, 22));
		}
		return tfBomSinglVersion;
	}
} // @jve:decl-index=0:visual-constraint="10,10"

