package com.bestway.common.client.materialbase;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.Date;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.CurrRate;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

public class DgUpdateRateParaSet extends JDialogBase {

	private JPanel jPanel = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JCalendarComboBox ccBoxBegin = null;

	private JCalendarComboBox ccBoxEnd = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JRadioButton jRadioButton = null;

	private JRadioButton jRadioButton1 = null;

	private JRadioButton jRadioButton2 = null;

	public boolean isOk = false;

	private Map result = null; // @jve:decl-index=0:

	private ButtonGroup buttonGroup = null; // @jve:decl-index=0:visual-constraint="731,128"
	private JTableListModel currRateModel = null;
	private CurrRate currRate = null; // @jve:decl-index=0:
	private MaterialManageAction materialManageAction = null;

	/**
	 * This method initializes
	 * 
	 */
	public DgUpdateRateParaSet() {
		super();
		this.setResizable(false);
		initialize();
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
	}

	@Override
	public void setVisible(boolean b) {
		if (b) {

			currRate = (CurrRate) currRateModel.getCurrentRow();
			initUIComponents();
		}
		super.setVisible(b);
	}

	private void initUIComponents() {
		// 取得创建日期作为开始时间

		ccBoxBegin.setDate(currRate.getCreateDate());
		Date newDate = materialManageAction.findExchangeRateData(new Request(
				CommonVars.getCurrUser()), currRate.getCurr(), currRate
				.getCurr1(), currRate.getCreateDate());
		ccBoxEnd.setDate(newDate);
		// 取得结束日期
		// Calendar cal = Calendar.getInstance();
		// cal.setTime(ccBoxBegin.getDate());
		// cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1, 0, 0, 0);
		// cal.add(Calendar.MONTH, 1);
		// cal.add(Calendar.DAY_OF_MONTH, -1);
		// Date newDate = cal.getTime();
		// ccBoxEnd.setDate(newDate);
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(335, 243));
		this.setContentPane(getJPanel());
		this.setTitle("选择条件");
		getButtonGroup();

	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(32, 58, 113, 25));
			jLabel1.setText("报关单申报结束时间");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(32, 25, 113, 25));
			jLabel.setText("报关单申报开始时间");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel, null);
			jPanel.add(jLabel1, null);
			jPanel.add(getJCalendarComboBox(), null);
			jPanel.add(getJCalendarComboBox1(), null);
			jPanel.add(getJButton(), null);
			jPanel.add(getJButton1(), null);
			jPanel.add(getJRadioButton(), null);
			jPanel.add(getJRadioButton1(), null);
			jPanel.add(getJRadioButton2(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getJCalendarComboBox() {
		if (ccBoxBegin == null) {
			ccBoxBegin = new JCalendarComboBox();
			ccBoxBegin.setBounds(new Rectangle(153, 25, 142, 25));
			ccBoxBegin.setEnabled(false);
		}
		return ccBoxBegin;
	}

	/**
	 * This method initializes jCalendarComboBox1
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getJCalendarComboBox1() {
		if (ccBoxEnd == null) {
			ccBoxEnd = new JCalendarComboBox();
			ccBoxEnd.setBounds(new Rectangle(153, 58, 142, 26));
			ccBoxEnd.setEnabled(false);
		}
		return ccBoxEnd;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new Rectangle(94, 167, 58, 28));
			jButton.setText("确定");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					result.clear();
					result.put("beginDate", ccBoxBegin.getDate());
					result.put("endDate", ccBoxEnd.getDate());
					if (getJRadioButton().isSelected()) {
						result.put("isRateNull", new Boolean(false));
					} else if (getJRadioButton1().isSelected()) {
						result.put("isRateNull", new Boolean(true));
					}
					if (getJRadioButton2().isSelected()) {
						result.put("isRateNull", null);
					}
					DgUpdateRateParaSet.this.isOk = true;
					dispose();
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
			jButton1.setBounds(new Rectangle(177, 167, 58, 28));
			jButton1.setText("退出");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton() {
		if (jRadioButton == null) {
			jRadioButton = new JRadioButton();
			jRadioButton.setBounds(new Rectangle(27, 87, 194, 19));
			jRadioButton.setText("更新存在汇率的报关单");
			jRadioButton.setSelected(true);
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
			jRadioButton1.setBounds(new Rectangle(27, 114, 198, 21));
			jRadioButton1.setText("更新不存在汇率的报关单");
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
			jRadioButton2.setBounds(new Rectangle(27, 141, 245, 21));
			jRadioButton2.setText("更新所有报关单(存在或不存在都更新)");
		}
		return jRadioButton2;
	}

	/**
	 * This method initializes buttonGroup
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup() {
		if (buttonGroup == null) {
			buttonGroup = new ButtonGroup();
			buttonGroup.add(getJRadioButton());
			buttonGroup.add(getJRadioButton1());
			buttonGroup.add(getJRadioButton2());
		}
		return buttonGroup;
	}

	public Map getResult() {
		return result;
	}

	public void setResult(Map result) {
		this.result = result;
	}

	public JTableListModel getCurrRateModel() {
		return currRateModel;
	}

	public void setCurrRateModel(JTableListModel currRateModel) {
		this.currRateModel = currRateModel;
	}

} // @jve:decl-index=0:visual-constraint="334,43"
