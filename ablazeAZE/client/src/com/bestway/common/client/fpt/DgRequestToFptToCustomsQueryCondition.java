/*
 * Created on 2005-5-23
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.fpt;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcus.cas.bill.entity.BillMaster;
import com.bestway.bcus.cas.entity.TempObject;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.common.constant.FptInOutFlag;
import com.bestway.common.fpt.action.FptManageAction;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public class DgRequestToFptToCustomsQueryCondition extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JCalendarComboBox cbbBeginDate = null;

	private JCalendarComboBox cbbEndDate = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private FptManageAction fptManageAction = null;

	private List lsResult = null; // @jve:decl-index=0:

	private boolean isImport;

	private JTableListModel contractTableModel = null;

	private JPanel jPanel = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel5 = null;

	private JRadioButton cbbIn = null;

	private JRadioButton cbbOut = null;

	private JPanel jPanel1 = null;

	private JPanel jPanel5 = null;

	private JLabel jLabel = null;

	private JComboBox cbbBillNo = null;

	private JLabel jLabel8 = null;

	private JComboBox cbbScmCoc = null;

	private JLabel jLabel6 = null;

	private JTextField tfBomNo = null;

	private JButton btnBomNo = null;

	private ButtonGroup bg = new ButtonGroup(); // @jve:decl-index=0:
	
	private String billNo = null;
	private String bomNo = null;
	private Date beginDate = null;
	private Date endDate = null;  //  @jve:decl-index=0:
	private ScmCoc scmCoc= null;  //  @jve:decl-index=0:

	/**
	 * @return Returns the isImport.
	 */
	public boolean isImport() {
		return isImport;
	}

	/**
	 * @param isImport
	 *            The isImport to set.
	 */
	public void setImport(boolean isImport) {
		this.isImport = isImport;
	}

	/**
	 * @return Returns the lsResult.
	 */
	public List getLsResult() {
		return lsResult;
	}

	/**
	 * This is the default constructor
	 */
	public DgRequestToFptToCustomsQueryCondition() {
		super();
		initialize();
		fptManageAction = (FptManageAction) CommonVars.getApplicationContext()
				.getBean("fptManageAction");
	}

	@Override
	public void setVisible(boolean b) {
		if (b) {
			initUIComponents();
		}
		super.setVisible(b);
	}

	/**
	 * 初始化窗口上 控件的值
	 * 
	 */
	private void initUIComponents() {
		bg.add(cbbIn);
		bg.add(cbbOut);
		this.cbbBeginDate.setDate(CommonVars.getBeginDate());
		this.cbbEndDate.setDate(new Date());
		if (this.cbbIn.isSelected()) {
			isImport = true;
			// 初始客户供应商品
			initCbbScmCoc(FptInOutFlag.OUT, null);
			// 初始单据号
			initCbbBillNo(true);
		}
	}

	private void initCbbScmCoc(String impExpFlagCode, String billTypeCode) {
		cbbScmCoc.removeAllItems();
		List list = fptManageAction.findScmCocsByPara(new Request(CommonVars
				.getCurrUser(), true), impExpFlagCode, billTypeCode);
		this.cbbScmCoc.setModel(new DefaultComboBoxModel(list.toArray()));
		this.cbbScmCoc.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 150, 170));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbScmCoc, "code", "name", 320);
		cbbScmCoc.setSelectedIndex(-1);
	}

	private void initCbbBillNo(boolean impExpFlagCode) {
		cbbBillNo.removeAllItems();
		List list = fptManageAction.findBillNoByPara(new Request(CommonVars
				.getCurrUser(), true), impExpFlagCode);
		this.cbbBillNo.setModel(new DefaultComboBoxModel(list.toArray()));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbBillNo, "billNo", "billNo");
		cbbBillNo.setRenderer(CustomBaseRender.getInstance().getRender(
				"billNo", "billNo", 200, 0));
		cbbBillNo.setSelectedIndex(-1);

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("请输入检索条件");
		this.setSize(349, 300);
		this.setContentPane(getJContentPane());
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
			jContentPane.add(getJPanel(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(new Rectangle(128, 73, 187, 22));
		}
		return cbbBeginDate;
	}

	/**
	 * This method initializes jCalendarComboBox1
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbEndDate() {
		if (cbbEndDate == null) {
			cbbEndDate = new JCalendarComboBox();
			cbbEndDate.setBounds(new Rectangle(128, 100, 187, 22));
		}
		return cbbEndDate;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("确定");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new MyFindThread().start();
				}
			});
		}
		return jButton;
	}

	/**
	 * 开始查询
	 */
	private void find() {

		if (cbbBillNo.getSelectedItem() != null) {
			billNo = ((BillMaster) cbbBillNo.getSelectedItem()).getBillNo();
		}
		 scmCoc = (ScmCoc) cbbScmCoc.getSelectedItem();// 客户供应商
		if (tfBomNo.getText() != null) {
			bomNo = tfBomNo.getText().trim();
		}
		beginDate = cbbBeginDate.getDate();
		endDate = cbbEndDate.getDate();
		lsResult = fptManageAction.getMakeFptBillFromCasBill(new Request(
				CommonVars.getCurrUser()), isImport, scmCoc, beginDate,
				endDate, billNo, bomNo);
		dispose();
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("取消");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton1;
	}

	private void selectAll(boolean isSelected) {
		if (contractTableModel == null) {
			return;
		}
		List list = contractTableModel.getList();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) instanceof Contract) {
				Contract temp = (Contract) list.get(i);
				temp.setIsSelected(new Boolean(isSelected));
			}
		}
		contractTableModel.updateRows(list);
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.insets = new Insets(0, 1, 9, 5);
			gridBagConstraints3.gridx = 0;
			gridBagConstraints3.gridy = 1;
			gridBagConstraints3.ipadx = 334;
			gridBagConstraints3.ipady = 171;
			gridBagConstraints3.gridwidth = 2;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.insets = new Insets(0, 1, 0, 5);
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.gridy = 0;
			gridBagConstraints2.ipadx = 334;
			gridBagConstraints2.ipady = 33;
			gridBagConstraints2.gridwidth = 2;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.insets = new Insets(11, 77, 16, 23);
			gridBagConstraints1.gridy = 2;
			gridBagConstraints1.ipadx = 9;
			gridBagConstraints1.ipady = -4;
			gridBagConstraints1.gridx = 0;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.insets = new Insets(9, 24, 18, 83);
			gridBagConstraints.gridy = 2;
			gridBagConstraints.ipadx = 9;
			gridBagConstraints.ipady = -4;
			gridBagConstraints.gridx = 1;
			jLabel5 = new JLabel();
			jLabel5.setText("至");
			jLabel5.setBounds(new Rectangle(90, 99, 23, 22));
			jLabel1 = new JLabel();
			jLabel1.setText("单据生效日期从");
			jLabel1.setBounds(new Rectangle(19, 71, 94, 22));
			jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());
			jPanel.add(getJButton1(), gridBagConstraints);
			jPanel.add(getJButton(), gridBagConstraints1);
			jPanel.add(getJPanel1(), gridBagConstraints2);
			jPanel.add(getJPanel5(), gridBagConstraints3);
		}
		return jPanel;
	}

	class MyFindThread extends Thread {
		@Override
		public void run() {
			CommonProgress.showProgressDialog();
			CommonProgress.setMessage("系统正获取数据，请稍后...");
			find();
			CommonProgress.closeProgressDialog();
		}
	}

	/**
	 * This method initializes cbbIn
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getCbbIn() {
		if (cbbIn == null) {
			cbbIn = new JRadioButton();
			cbbIn.setText("转入");
			cbbIn.setBounds(new Rectangle(72, 5, 49, 26));
			cbbIn.setSelected(true);
			cbbIn.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					isImport = true;
					initCbbBillNo(true);
					// 初始客户供应商品
					initCbbScmCoc(FptInOutFlag.OUT, null);
				}
			});
		}
		return cbbIn;
	}

	/**
	 * This method initializes cbbOut
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getCbbOut() {
		if (cbbOut == null) {
			cbbOut = new JRadioButton();
			cbbOut.setText("转出");
			cbbOut.setBounds(new Rectangle(201, 6, 49, 26));
			cbbOut.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					isImport = false;
					initCbbBillNo(false);
					// 初始客户供应商品
					initCbbScmCoc(FptInOutFlag.IN, null);
				}
			});
		}
		return cbbOut;
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
			jPanel1.add(getCbbIn(), null);
			jPanel1.add(getCbbOut(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jPanel5
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel5() {
		if (jPanel5 == null) {
			jLabel6 = new JLabel();
			jLabel6.setBounds(new Rectangle(53, 133, 60, 18));
			jLabel6.setText("BOM\u7f16\u53f7");
			jLabel8 = new JLabel();
			jLabel8.setBounds(new Rectangle(37, 44, 76, 18));
			jLabel8.setText("客户/供应商");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(37, 20, 76, 18));
			jLabel.setText("单据号");
			jPanel5 = new JPanel();
			jPanel5.setLayout(null);
			jPanel5.setBorder(BorderFactory.createTitledBorder(null, "单据查询条件",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), Color.blue));
			jPanel5.add(jLabel, null);
			jPanel5.add(getCbbBillNo(), null);
			jPanel5.add(jLabel8, null);
			jPanel5.add(getCbbScmCoc(), null);
			jPanel5.add(jLabel1, null);
			jPanel5.add(getCbbBeginDate(), null);
			jPanel5.add(getCbbEndDate(), null);
			jPanel5.add(jLabel5, null);
			jPanel5.add(jLabel6, null);
			jPanel5.add(getTfBomNo(), null);
			jPanel5.add(getBtnBomNo(), null);
		}
		return jPanel5;
	}

	/**
	 * This method initializes cbbBillNo
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbBillNo() {
		if (cbbBillNo == null) {
			cbbBillNo = new JComboBox();
			cbbBillNo.setBounds(new Rectangle(128, 18, 187, 22));
		}
		return cbbBillNo;
	}

	/**
	 * This method initializes cbbScmCoc
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbScmCoc() {
		if (cbbScmCoc == null) {
			cbbScmCoc = new JComboBox();
			cbbScmCoc.setBounds(new Rectangle(128, 44, 187, 22));
		}
		return cbbScmCoc;
	}

	/**
	 * This method initializes tfBomNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfBomNo() {
		if (tfBomNo == null) {
			tfBomNo = new JTextField();
			tfBomNo.setBounds(new Rectangle(128, 132, 167, 22));
		}
		return tfBomNo;
	}

	/**
	 * This method initializes btnBomNo
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnBomNo() {
		if (btnBomNo == null) {
			btnBomNo = new JButton();
			btnBomNo.setBounds(new Rectangle(294, 131, 23, 23));
			btnBomNo.setText("...");
			btnBomNo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Object object = FptQuery.getInstance()
							.getCasCommodityInfoByPara(isImport, "结转单资料");
					if (object != null) {
						tfBomNo.setText((String) ((TempObject) object)
								.getObject());
					}

				}
			});
		}
		return btnBomNo;
	}

	public String getBillNo() {
		return billNo;
	}

	public String getBomNo() {
		return bomNo;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public ScmCoc getScmCoc() {
		return scmCoc;
	}

} // @jve:decl-index=0:visual-constraint="227,15"
