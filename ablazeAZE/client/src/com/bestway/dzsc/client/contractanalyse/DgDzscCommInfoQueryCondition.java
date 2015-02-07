/*
 * Created on 2005-5-23
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.client.contractanalyse;

import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.dzsc.contractanalyse.action.DzscAnalyseAction;
import com.bestway.dzsc.contractstat.action.DzscStatAction;
import com.bestway.dzsc.contractstat.entity.TempDzscCustomsDeclarCommInfo;
import com.bestway.dzsc.dzscmanage.action.DzscAction;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgDzscCommInfoQueryCondition extends JDialogBase {

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

	private DzscAnalyseAction contractAnalyseAction = null;

	private DzscStatAction contractStatAction = null;

	private DzscAction contractAction = null;

	private List lsResult = null;

	private boolean isImport;

	private JLabel jLabel5 = null;

	private JComboBox cbbContract = null;

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
	public DgDzscCommInfoQueryCondition() {
		super();
		initialize();
		contractAnalyseAction = (DzscAnalyseAction) CommonVars
				.getApplicationContext().getBean("dzscAnalyseAction");
		contractStatAction = (DzscStatAction) CommonVars
				.getApplicationContext().getBean("dzscStatAction");
		contractAction = (DzscAction) CommonVars.getApplicationContext()
				.getBean("dzscAction");
	}

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
		// 初始化合同
		this.cbbContract.removeAllItems();
		List list = contractAction.findDzscEmsPorHeadExcu(new Request(
				CommonVars.getCurrUser()));
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				this.cbbContract.addItem((DzscEmsPorHead) list.get(i));
			}
			this.cbbContract.setRenderer(CustomBaseRender.getInstance()
					.getRender("ieContractNo", "emsNo", 100, 100));
		}
		this.cbbContract.setSelectedIndex(-1);

		// 商品
		this.cbbCommInfo.setModel(new DefaultComboBoxModel(contractStatAction
				.findCustomsDeclarationCommInfo(
						new Request(CommonVars.getCurrUser()), isImport, null)
				.toArray()));
		this.cbbCommInfo
				.setRenderer(CustomBaseRender.getInstance().getRender("seqNum", "name", 100, 100));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbCommInfo, "seqNum", "name");
		this.cbbCommInfo.setSelectedIndex(-1);
		// 客户
		this.cbbCustomer.setModel(new DefaultComboBoxModel(contractStatAction
				.findCustomsDeclarationCustomer(
						new Request(CommonVars.getCurrUser()), isImport, null)
				.toArray()));
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
		this.setSize(379, 309);
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel5 = new JLabel();
			jLabel4 = new JLabel();
			jLabel3 = new JLabel();
			jLabel2 = new JLabel();
			jLabel1 = new JLabel();
			jLabel = new JLabel();
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jLabel.setBounds(42, 70, 93, 24);
			jLabel.setText("检索商品");
			jLabel1.setBounds(42, 106, 93, 24);
			jLabel1.setText("检索客户");
			jLabel2.setBounds(42, 146, 93, 24);
			jLabel2.setText("进出口类型");
			jLabel3.setBounds(42, 184, 93, 24);
			jLabel3.setText("报关单日期范围");
			jLabel4.setBounds(233, 186, 13, 18);
			jLabel4.setText("至");
			jLabel5.setBounds(43, 36, 88, 22);
			jLabel5.setText("合同号+账册号");
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
			jContentPane.add(jLabel5, null);
			jContentPane.add(getCbbContract(), null);
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
			cbbCommInfo.setBounds(140, 70, 197, 22);
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
			cbbCustomer.setBounds(140, 106, 197, 22);
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
			cbbImpExpType.setBounds(140, 146, 197, 22);
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
			cbbBeginDate.setBounds(141, 185, 89, 20);
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
			cbbEndDate.setBounds(247, 185, 90, 19);
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
			jButton.setBounds(41, 233, 105, 25);
			jButton.setText("确定");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String commCode = "";
					String customer = "";
					String impExpType = "";
					Integer seqNum=null;
					DzscEmsPorHead contract = (DzscEmsPorHead) cbbContract
							.getSelectedItem();
					if (cbbCommInfo.getSelectedItem() != null) {
						commCode = ((TempDzscCustomsDeclarCommInfo) cbbCommInfo.getSelectedItem())
								.getCode();
						seqNum = ((TempDzscCustomsDeclarCommInfo) cbbCommInfo.getSelectedItem())
						.getSeqNum();
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
					lsResult = contractAnalyseAction.findCommInfoImpExpAnalyse(
							new Request(CommonVars.getCurrUser()), isImport,
							seqNum, customer, impExpType, beginDate, endDate,
							contract);
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
			jButton1.setBounds(228, 233, 111, 25);
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
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbContract() {
		if (cbbContract == null) {
			cbbContract = new JComboBox();
			cbbContract.setBounds(141, 34, 195, 23);
			cbbContract.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
						if (cbbContract.getSelectedItem() == null) {
							return;
						}
						DzscEmsPorHead contract = (DzscEmsPorHead) cbbContract
								.getSelectedItem();
						// 商品
						cbbCommInfo.setModel(new DefaultComboBoxModel(
								contractStatAction
										.findCustomsDeclarationCommInfo(
												new Request(CommonVars
														.getCurrUser()),
												isImport, contract).toArray()));
						cbbCommInfo.setRenderer(CustomBaseRender.getInstance()
								.getRender("code", "name", 100, 100));
						CustomBaseComboBoxEditor.getInstance()
								.setComboBoxEditor(cbbCommInfo,"code", "name");
						cbbCommInfo.setSelectedIndex(-1);
						// 客户
						cbbCustomer.setModel(new DefaultComboBoxModel(
								contractStatAction
										.findCustomsDeclarationCustomer(
												new Request(CommonVars
														.getCurrUser()),
												isImport, contract).toArray()));
						cbbCustomer.setRenderer(CustomBaseRender.getInstance()
								.getRender("code", "name", 100, 100));
						CustomBaseComboBoxEditor.getInstance()
								.setComboBoxEditor(cbbCustomer, "code", "name");
						cbbCustomer.setSelectedIndex(-1);
						cbbBeginDate.setDate(contract.getBeginDate());
					}
				}
			});
		}
		return cbbContract;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
