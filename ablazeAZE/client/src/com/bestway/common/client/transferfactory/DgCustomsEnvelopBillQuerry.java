package com.bestway.common.client.transferfactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.common.Request;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.fecav.action.FecavAction;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import java.awt.Dimension;
import java.awt.Rectangle;
import javax.swing.JRadioButton;
import javax.swing.BorderFactory;
import java.awt.Color;
import javax.swing.ButtonGroup;

public class DgCustomsEnvelopBillQuerry extends DgCommon {

	private JPanel jPanel = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel3 = null;

	private JButton btnOk = null;

	private JButton btnCancel = null;

	private JTextField tfCustomsEnvelopBillNo = null;

	private JCalendarComboBox ccbBeginDate = null;

	private JCalendarComboBox ccbEndData = null;

	private FecavAction fecavAction = null;

	private List resultList;

	private JComboBox cbbScmcoc = null;

	public boolean isImportGoods = true;

	public boolean isOK = false;

	private JPanel jPanel1 = null;

	private JRadioButton jRadioButton = null;

	private JRadioButton jRadioButton1 = null;

	private JRadioButton jRadioButton2 = null;

	private ButtonGroup buttonGroup = null; // @jve:decl-index=0:visual-constraint="626,84"

	/**
	 * This method initializes
	 * 
	 */
	public DgCustomsEnvelopBillQuerry() {
		super();
		fecavAction = (FecavAction) CommonVars.getApplicationContext().getBean(
				"fecavAction");
		initialize();
		this.getButtonGroup();
	}

	@Override
	public void setVisible(boolean is) {
		initUIComponents();
		super.setVisible(is);
	}

	protected void initUIComponents() {
		// 初始化客户commonBaseCodeAction
		List list;
		if (this.isImportGoods) {
			list = this.getManufacturer();
		} else
			list = this.getCustomer();
		this.cbbScmcoc.setModel(new DefaultComboBoxModel(list.toArray()));
		this.cbbScmcoc.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 100, 170));
		this.cbbScmcoc.setSelectedItem(null);
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbScmcoc, "code", "name", 270);
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(362, 269));
		this.setContentPane(getJPanel());
		this.setTitle("关封查询");

	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel3 = new JLabel();
			jLabel3.setBounds(new java.awt.Rectangle(37, 109, 91, 26));
			jLabel3.setText("生效截止日期");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new java.awt.Rectangle(37, 78, 91, 26));
			jLabel2.setText("生效开始日期");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new java.awt.Rectangle(37, 47, 91, 26));
			jLabel1.setText("客户/供应商");
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(37, 16, 91, 26));
			jLabel.setText("关封号");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel, null);
			jPanel.add(jLabel1, null);
			jPanel.add(jLabel2, null);
			jPanel.add(jLabel3, null);
			jPanel.add(getBtnOk(), null);
			jPanel.add(getBtnCancel(), null);
			jPanel.add(getTfCustomsEnvelopBillNo(), null);
			jPanel.add(getCcbBeginDate(), null);
			jPanel.add(getCcbEndData(), null);
			jPanel.add(getCbbScomcoc(), null);
			jPanel.add(getJPanel1(), null);
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
			btnOk.setBounds(new Rectangle(57, 192, 64, 25));
			btnOk.setText("确定");
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					fillData();
					isOK = true;
					DgCustomsEnvelopBillQuerry.this.dispose();
				}
			});
		}
		return btnOk;
	}

	private void fillData() {
		String customsEnvelopBillNo = this.tfCustomsEnvelopBillNo.getText();
		ScmCoc sc = (ScmCoc) this.cbbScmcoc.getSelectedItem();
		Date beginDate = this.ccbBeginDate.getDate();
		Date endDate = this.ccbEndData.getDate();
		Boolean isEndCase = null;
		if (this.jRadioButton.isSelected()) {
			isEndCase = false;
		} else if (this.jRadioButton1.isSelected()) {
			isEndCase = true;
		}
		this.resultList = this.transferFactoryManageAction
				.findCustomsEnvelopBill(new Request(CommonVars.getCurrUser()),
						isImportGoods, customsEnvelopBillNo, sc, beginDate,
						endDate, isEndCase);
		System.out.println(resultList.size());
	}

	/**
	 * This method initializes btnCancel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(new Rectangle(227, 194, 64, 25));
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgCustomsEnvelopBillQuerry.this.dispose();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * This method initializes tfCustomsEnvelopBillNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCustomsEnvelopBillNo() {
		if (tfCustomsEnvelopBillNo == null) {
			tfCustomsEnvelopBillNo = new JTextField();
			tfCustomsEnvelopBillNo.setBounds(new Rectangle(129, 16, 181, 26));
		}
		return tfCustomsEnvelopBillNo;
	}

	/**
	 * This method initializes ccbBeginDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCcbBeginDate() {
		if (ccbBeginDate == null) {
			ccbBeginDate = new JCalendarComboBox();
			ccbBeginDate.setBounds(new Rectangle(129, 78, 181, 26));
			ccbBeginDate.setDate(getBeginDate());
		}
		return ccbBeginDate;
	}

	public Date getBeginDate(){
		Date beginDate = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-");
		String str = format.format(new Date())+"01";
		try {
			beginDate = new SimpleDateFormat("yyyy-MM-dd").parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return beginDate;
	}
	
	/**
	 * This method initializes ccbEndData
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCcbEndData() {
		if (ccbEndData == null) {
			ccbEndData = new JCalendarComboBox();
			ccbEndData.setBounds(new Rectangle(129, 109, 181, 26));
			ccbEndData.setDate(new Date());
		}
		return ccbEndData;
	}

	/**
	 * This method initializes cbbScomcoc
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbScomcoc() {
		if (cbbScmcoc == null) {
			cbbScmcoc = new JComboBox();
			cbbScmcoc.setBounds(new Rectangle(129, 47, 181, 26));

		}
		return cbbScmcoc;
	}

	public List getResultList() {
		return resultList;
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
			jPanel1.setBounds(new Rectangle(37, 141, 274, 39));
			jPanel1.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
			jPanel1.add(getJRadioButton(), null);
			jPanel1.add(getJRadioButton1(), null);
			jPanel1.add(getJRadioButton2(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton() {
		if (jRadioButton == null) {
			jRadioButton = new JRadioButton();
			jRadioButton.setBounds(new Rectangle(10, 9, 83, 20));
			jRadioButton.setText("未结案");
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
			jRadioButton1.setBounds(new Rectangle(94, 9, 65, 21));
			jRadioButton1.setText("已结案");
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
			jRadioButton2.setBounds(new Rectangle(179, 9, 75, 23));
			jRadioButton2.setSelected(true);
			jRadioButton2.setText("全部");
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
			buttonGroup.add(this.getJRadioButton());
			buttonGroup.add(this.getJRadioButton1());
			buttonGroup.add(this.getJRadioButton2());
		}
		return buttonGroup;
	}

} // @jve:decl-index=0:visual-constraint="190,29"
