/*
 * Created on 2005-5-23
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.client.contractanalyse;

import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contractanalyse.action.ContractAnalyseAction;
import com.bestway.bcs.contractstat.action.ContractStatAction;
import com.bestway.bcs.contractstat.entity.TempBcsCustomsDeclarCommInfo;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.common.Request;
import com.bestway.common.constant.CustomsDeclarationState;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import java.awt.Rectangle;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import javax.swing.ButtonGroup;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgAnalyseQueryCondition extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel3 = null;

	private JComboBox cbbCommInfo = null;

	private JComboBox cbbCustomer = null;

	private JComboBox cbbImpExpType = null;

	private JCalendarComboBox cbbBeginDate = null;

	private JCalendarComboBox cbbEndDate = null;

	private JLabel jLabel4 = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private Contract contract = null;

	private ContractAnalyseAction contractAnalyseAction = null;

	private ContractStatAction contractStatAction = null;

	private List lsResult = null;

	private boolean isImport;

	private JPanel jPanel = null;

	private JRadioButton rbYes = null;

	private JRadioButton rbNo = null;

	private JRadioButton rbAll = null;

	private ButtonGroup buttonGroup = null;  //  @jve:decl-index=0:visual-constraint="423,18"

	private int impExpFlag = 0;
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
	
	public void setImpExpFlag(int impExpFlag){
		this.impExpFlag = impExpFlag;
	}

	public int getImpExpFlag(){
		return impExpFlag;
	}
	/**
	 * @return Returns the contract.
	 */
	public Contract getContract() {
		return contract;
	}

	/**
	 * @param contract
	 *            The contract to set.
	 */
	public void setContract(Contract contract) {
		this.contract = contract;
	}

	/**
	 * This is the default constructor
	 */
	public DgAnalyseQueryCondition() {
		super();
		initialize();
		contractAnalyseAction = (ContractAnalyseAction) CommonVars
				.getApplicationContext().getBean("contractAnalyseAction");
		contractStatAction = (ContractStatAction) CommonVars
				.getApplicationContext().getBean("contractStatAction");
	}

	public void setVisible(boolean b) {
		if (b) {
			initUIComponents();
			if (contract != null) {
				this.cbbBeginDate.setDate(contract.getBeginDate());
			} else {
				this.cbbBeginDate.setDate(null);
			}
		}
		super.setVisible(b);
	}

	/**
	 * 初始化窗口上 控件的值
	 * 
	 */
	private void initUIComponents() {
		// 商品
		this.cbbCommInfo
				.setModel(new DefaultComboBoxModel(contractStatAction
						.findCustomsDeclarationCommInfo(
								new Request(CommonVars.getCurrUser()),
								isImport, contract,
								CustomsDeclarationState.EFFECTIVED).toArray()));
		this.cbbCommInfo.setRenderer(CustomBaseRender.getInstance().getRender(
				"seqNum", "name", 100, 100));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbCommInfo, "seqNum", "name");
		this.cbbCommInfo.setSelectedIndex(-1);
		// 客户
		this.cbbCustomer
				.setModel(new DefaultComboBoxModel(contractStatAction
						.findCustomsDeclarationCustomer(
								new Request(CommonVars.getCurrUser()),
								isImport, contract,
								CustomsDeclarationState.EFFECTIVED).toArray()));
		this.cbbCustomer.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 100, 100));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbCustomer, "code", "name");
		this.cbbCustomer.setSelectedIndex(-1);
		// 初始化进出口类型
		this.cbbImpExpType.removeAllItems();
		if (isImport) {
			this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.DIRECT_IMPORT).toString(), "料件进口"));
			this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.TRANSFER_FACTORY_IMPORT).toString(), "料件转厂"));
			this.cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.BACK_MATERIEL_EXPORT), "退料出口"));
		} else {
			this.cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.DIRECT_EXPORT), "成品出口"));
			this.cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.TRANSFER_FACTORY_EXPORT), "转厂出口"));
			this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.BACK_FACTORY_REWORK).toString(), "退厂返工"));
			this.cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.REWORK_EXPORT), "返工复出"));

		}
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbImpExpType);
		this.cbbImpExpType.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		this.cbbImpExpType.setSelectedIndex(-1);
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
		this.setSize(379, 266);
		this.setContentPane(getJContentPane());
		this.getButtonGroup();
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel4 = new JLabel();
			jLabel3 = new JLabel();
			jLabel2 = new JLabel();
			jLabel1 = new JLabel();
			jLabel = new JLabel();
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jLabel.setBounds(42, 24, 93, 24);
			jLabel.setText("检索商品");
			jLabel1.setBounds(42, 52, 93, 24);
			jLabel1.setText("检索客户");
			jLabel2.setBounds(42, 82, 93, 24);
			jLabel2.setText("进出口类型");
			jLabel3.setBounds(42, 112, 93, 24);
			jLabel3.setText("报关单日期范围");
			jLabel4.setBounds(232, 113, 13, 18);
			jLabel4.setText("至");
			jContentPane.add(jLabel, null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(getCbbCommInfo(), null);
			jContentPane.add(getCbbCustomer(), null);
			jContentPane.add(getCbbImpExpType(), null);
			jContentPane.add(getCbbBeginDate(), null);
			jContentPane.add(getCbbEndDate(), null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton1(), null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(getJPanel(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCommInfo() {
		if (cbbCommInfo == null) {
			cbbCommInfo = new JComboBox();
			cbbCommInfo.setBounds(140, 24, 197, 22);
		}
		return cbbCommInfo;
	}

	/**
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCustomer() {
		if (cbbCustomer == null) {
			cbbCustomer = new JComboBox();
			cbbCustomer.setBounds(140, 52, 197, 22);
		}
		return cbbCustomer;
	}

	/**
	 * This method initializes jComboBox2
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbImpExpType() {
		if (cbbImpExpType == null) {
			cbbImpExpType = new JComboBox();
			cbbImpExpType.setBounds(140, 82, 197, 22);
		}
		return cbbImpExpType;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(141, 113, 89, 20);
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
			cbbEndDate.setBounds(247, 113, 90, 19);
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
			jButton.setBounds(41, 192, 105, 25);
			jButton.setText("确定");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String commCode = "";
					String customer = "";
					String impExpType = "";
					Integer seqNum=null;
					if (cbbCommInfo.getSelectedItem() != null) {
						commCode = ((TempBcsCustomsDeclarCommInfo) cbbCommInfo
								.getSelectedItem()).getCode();
						seqNum = ((TempBcsCustomsDeclarCommInfo) cbbCommInfo
								.getSelectedItem()).getSeqNum();
					}
					if (cbbCustomer.getSelectedItem() != null) {
						customer = ((ScmCoc) cbbCustomer.getSelectedItem())
								.getId();
					}
					if (cbbImpExpType.getSelectedItem() != null) {
						impExpType = ((ItemProperty) cbbImpExpType
								.getSelectedItem()).getCode();
					}
					Date beginDate = cbbBeginDate.getDate();
					Date endDate = cbbEndDate.getDate();
					int state = -1;
					if (rbYes.isSelected()) {
						state = CustomsDeclarationState.EFFECTIVED;
					} else if (rbNo.isSelected()) {
						state = CustomsDeclarationState.NOT_EFFECTIVED;
					} else {
						state = CustomsDeclarationState.ALL;
					}
					lsResult = contractAnalyseAction.findImpExpCommodityStatus(
							new Request(CommonVars.getCurrUser()), isImport,
							seqNum, customer, impExpType, beginDate, endDate,
							contract,state,impExpFlag);
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
			jButton1.setBounds(228, 192, 111, 25);
			jButton1.setText("取消");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setBounds(new java.awt.Rectangle(42,141,296,38));
			jPanel.setBorder(BorderFactory.createTitledBorder(null, "", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
			jPanel.add(getRbYes(), null);
			jPanel.add(getRbNo(), null);
			jPanel.add(getRbAll(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jRadioButton	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRbYes() {
		if (rbYes == null) {
			rbYes = new JRadioButton();
			rbYes.setBounds(new java.awt.Rectangle(11,9,71,20));
			rbYes.setText("\u5df2\u751f\u6548");
			rbYes.setSelected(true);
		}
		return rbYes;
	}

	/**
	 * This method initializes jRadioButton1	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRbNo() {
		if (rbNo == null) {
			rbNo = new JRadioButton();
			rbNo.setBounds(new java.awt.Rectangle(116,9,86,20));
			rbNo.setText("\u672a\u751f\u6548");
		}
		return rbNo;
	}

	/**
	 * This method initializes jRadioButton2	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRbAll() {
		if (rbAll == null) {
			rbAll = new JRadioButton();
			rbAll.setBounds(new java.awt.Rectangle(225,9,52,20));
			rbAll.setText("\u5168\u90e8");
			
		}
		return rbAll;
	}

	/**
	 * This method initializes buttonGroup	
	 * 	
	 * @return javax.swing.ButtonGroup	
	 */
	private ButtonGroup getButtonGroup() {
		if (buttonGroup == null) {
			buttonGroup = new ButtonGroup();
			buttonGroup.add(this.rbAll);
			buttonGroup.add(this.rbYes);
			buttonGroup.add(this.rbNo);
		}
		return buttonGroup;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
