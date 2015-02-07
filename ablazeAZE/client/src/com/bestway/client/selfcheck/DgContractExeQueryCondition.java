
package com.bestway.client.selfcheck;

import java.awt.Rectangle;
import java.util.Date;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcus.cas.action.CasCheckAction;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.enc.entity.TempComplex;
import com.bestway.common.Request;
import com.bestway.common.constant.CustomsDeclarationState;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.ProjectDept;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author Administrator
 *  // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgContractExeQueryCondition extends JDialogBase {

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
	
	private CasCheckAction casCheckAction = null;
	
	private List lsResult = null;
	private boolean isImport;
	private boolean isImg;
	private ButtonGroup buttonGroup1 = null;  //  @jve:decl-index=0:
	private ButtonGroup buttonGroup2 = null;  //  @jve:decl-index=0:
	private JRadioButton jRadioButton = null;
	private JRadioButton jRadioButton1 = null;
	private JLabel jLabel5 = null;
	private JComboBox jComboBox = null;
	private MaterialManageAction materialManageAction = null;
	private JCheckBox jCheckBox = null;
	private JPanel jPanel = null;
	private JRadioButton Radiotrue = null;
	private JRadioButton Radiofalse = null;
	private JRadioButton Radioall = null;

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
	public DgContractExeQueryCondition() {
		super();
		initialize();
		getButtonGroup1();
	    getButtonGroup2();
		casCheckAction = (CasCheckAction) CommonVars.getApplicationContext().getBean(
				"casCheckAction");
		//下面的actioin为公共的，可用
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
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
		// 商品
		this.cbbCommInfo.setModel(new DefaultComboBoxModel(casCheckAction
				.findCustomsDeclarationCommInfo(
						new Request(CommonVars.getCurrUser()), isImport)
				.toArray()));
		this.cbbCommInfo.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 50, 320));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbCommInfo, "code", "name", 400);
		this.cbbCommInfo.setSelectedIndex(-1);
		// 客户
		this.cbbCustomer.setModel(new DefaultComboBoxModel(casCheckAction
				.findCustomsDeclarationCustomer(
						new Request(CommonVars.getCurrUser()), isImport)
				.toArray()));
		this.cbbCustomer.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "fname", 80, 200));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbCustomer, "code", "fname", 280);
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
			this.cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.REMAIN_FORWARD_IMPORT), "余料结转进口"));
			this.cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.MATERIAL_DOMESTIC_SALES), "海关批准内销"));
		} else {
			this.cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.DIRECT_EXPORT), "成品出口"));
			this.cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.TRANSFER_FACTORY_EXPORT), "转厂出口"));
			this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.BACK_FACTORY_REWORK).toString(), "退厂返工"));
			this.cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.REWORK_EXPORT), "返工复出"));
			this.cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.REMAIN_FORWARD_EXPORT), "余料结转"));

		}
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbImpExpType);
		this.cbbImpExpType.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		this.cbbImpExpType.setSelectedIndex(-1);

		// 事业部
		List projectList = materialManageAction.findProjectDept(new Request(
				CommonVars.getCurrUser(), true));
		this.jComboBox
				.setModel(new DefaultComboBoxModel(projectList.toArray()));
		this.jComboBox.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.jComboBox, "code", "name");
		jComboBox.setSelectedIndex(-1);
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
		this.setSize(379, 318);
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
			jLabel5.setBounds(new java.awt.Rectangle(174, 110, 43, 18));
			jLabel5.setForeground(java.awt.Color.red);
			jLabel5.setText("事业部");
			jLabel4 = new JLabel();
			jLabel3 = new JLabel();
			jLabel2 = new JLabel();
			jLabel1 = new JLabel();
			jLabel = new JLabel();
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jLabel.setBounds(42, 20, 93, 24);
			jLabel.setText("检索商品");
			jLabel1.setBounds(42, 46, 93, 24);
			jLabel1.setText("检索客户");
			jLabel2.setBounds(42, 73, 93, 24);
			jLabel2.setText("进出口类型");
			jLabel3.setBounds(40, 138, 93, 24);
			jLabel3.setText("报关单日期范围");
			jLabel4.setBounds(231, 141, 13, 18);
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
			jContentPane.add(getJRadioButton(), null);
			jContentPane.add(getJRadioButton1(), null);
			jContentPane.add(jLabel5, null);
			jContentPane.add(getJComboBox(), null);
			jContentPane.add(getJCheckBox(), null);
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
			cbbCommInfo.setBounds(140, 20, 197, 22);
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
			cbbCustomer.setBounds(140, 46, 197, 22);
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
			cbbImpExpType.setBounds(140, 73, 197, 22);
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
			cbbBeginDate.setBounds(139, 139, 89, 20);
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
			cbbEndDate.setBounds(245, 139, 90, 19);
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
			jButton.setBounds(40, 238, 105, 25);
			jButton.setText("确定");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jCheckBox.isSelected()) { // 分事业部
						new findfordept().start();
					} else {// 不分事业部
						new find().start();
					}
				}
			});
		}
		return jButton;
	}

	class find extends Thread {// 不分事业部

		public void run() {
			try {
				CommonProgress
						.showProgressDialog(DgContractExeQueryCondition.this);
				CommonProgress.setMessage("系统正获取数据，请稍后...");
				Integer seqNum = null;// 序号
				String customer = null; // 客户名称
				String impExpType = null;// 进出口类型

				int iseffect = -1;
				if (Radiotrue.isSelected()) {
					iseffect = CustomsDeclarationState.EFFECTIVED;
				} else if (Radiofalse.isSelected()) {
					iseffect = CustomsDeclarationState.NOT_EFFECTIVED;
				} else if (Radioall.isSelected()) {
					iseffect = CustomsDeclarationState.ALL;
				}

				if (cbbCommInfo.getSelectedItem() != null) {// 序号
					seqNum = Integer.valueOf(((TempComplex) cbbCommInfo
							.getSelectedItem()).getCode());
				}
				if (cbbCustomer.getSelectedItem() != null) { // 客户
					customer = ((ScmCoc) cbbCustomer.getSelectedItem()).getId();
				}
				if (cbbImpExpType.getSelectedItem() != null) {// 类型
					impExpType = ((ItemProperty) cbbImpExpType
							.getSelectedItem()).getCode();
				}
				Date beginDate = CommonVars.dateToStandDate(cbbBeginDate
						.getDate());
				Date endDate = CommonVars.dateToStandDate(cbbEndDate.getDate());
				if (isImg) {// 料件
					if (jRadioButton.isSelected()) { // 申报日期 (isDeclaration =
														// true)
						lsResult = casCheckAction.findImpExpCommInfoUseNoDept(
								new Request(CommonVars.getCurrUser()),
								isImport, seqNum, customer, impExpType,
								beginDate, endDate, true,  iseffect);
					} else if (jRadioButton1.isSelected()) { // 结关日期
						lsResult = casCheckAction.findImpExpCommInfoUseNoDept(
								new Request(CommonVars.getCurrUser()),
								isImport, seqNum, customer, impExpType,
								beginDate, endDate, false, iseffect);
					}
				} else {
//					if (jRadioButton.isSelected()) {// 申报日期
//						lsResult = casCheckAction.findImpExpCommInfoUseForExgNoDept(
//								new Request(CommonVars.getCurrUser()),
//								isImport, seqNum, customer, impExpType,
//								beginDate, endDate, true, iseffect);
//					} else if (jRadioButton1.isSelected()) {// 结关日
//						lsResult = casCheckAction.findImpExpCommInfoUseForExgNoDept(
//								new Request(CommonVars.getCurrUser()),
//								isImport, seqNum, customer, impExpType,
//								beginDate, endDate, false, iseffect);
//					}
				}
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgContractExeQueryCondition.this,
						"获取数据失败：！" + e.getMessage(), "提示", 2);
			} finally {
				dispose();
			}
		}
	}

	class findfordept extends Thread {

		public void run() {
			try {
				CommonProgress
						.showProgressDialog(DgContractExeQueryCondition.this);
				CommonProgress.setMessage("系统正获取数据，请稍后...");
				Integer seqNum = null;
				String customer = ""; // 客户名称
				String impExpType = "";// 进出口类型
				if (cbbCommInfo.getSelectedItem() != null) {
					seqNum = Integer.valueOf(((TempComplex) cbbCommInfo
							.getSelectedItem()).getCode());
				}
				if (cbbCustomer.getSelectedItem() != null) {
					customer = ((ScmCoc) cbbCustomer.getSelectedItem()).getId();
				}
				String deptCode = null;
				// 事业部
				if (jComboBox.getSelectedItem() != null
						&& !jComboBox.getSelectedItem().toString().trim()
								.equals("")) {
					deptCode = ((ProjectDept) jComboBox.getSelectedItem())
							.getCode();
				}
				if (cbbImpExpType.getSelectedItem() != null) {
					impExpType = ((ItemProperty) cbbImpExpType
							.getSelectedItem()).getCode();
				}
				boolean isdept = false;
				if (jCheckBox.isSelected()) {
					isdept = true;
				} else {
					isdept = false;
				}

				int iseffect = -1;
				if (Radiotrue.isSelected()) {
					iseffect = CustomsDeclarationState.EFFECTIVED;
				} else if (Radiofalse.isSelected()) {
					iseffect = CustomsDeclarationState.NOT_EFFECTIVED;
				} else if (Radioall.isSelected()) {
					iseffect = CustomsDeclarationState.ALL;
				}

				Date beginDate = CommonVars.dateToStandDate(cbbBeginDate
						.getDate());
				Date endDate = CommonVars.dateToStandDate(cbbEndDate.getDate());
				if (isImg) {// 料件
					if (jRadioButton.isSelected()) {
						lsResult = casCheckAction.findImpExpCommInfoUseForDept(
								new Request(CommonVars.getCurrUser()),
								isImport, seqNum, customer, impExpType,
								beginDate, endDate, true, deptCode, iseffect);
					} else if (jRadioButton1.isSelected()) {
						lsResult = casCheckAction.findImpExpCommInfoUseForDept(
								new Request(CommonVars.getCurrUser()),
								isImport, seqNum, customer, impExpType,
								beginDate, endDate, false, deptCode, iseffect);
					}
				} else {
//					if (jRadioButton.isSelected()) {
//						lsResult = casCheckAction
//								.findImpExpCommInfoUseForExgForDept(
//										new Request(CommonVars.getCurrUser()),
//										isImport, seqNum, customer, impExpType,
//										beginDate, endDate, true, isdept,
//										deptCode, iseffect);
//					} else if (jRadioButton1.isSelected()) {
//						lsResult = casCheckAction
//								.findImpExpCommInfoUseForExgForDept(
//										new Request(CommonVars.getCurrUser()),
//										isImport, seqNum, customer, impExpType,
//										beginDate, endDate, false, isdept,
//										deptCode, iseffect);
//					}
				}
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgContractExeQueryCondition.this,
						"获取数据失败：！" + e.getMessage(), "提示", 2);
			} finally {
				dispose();
			}
		}
	}

	/**
	 * 
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(227, 238, 111, 25);
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
	 * @return Returns the isImg.
	 */
	public boolean isImg() {
		return isImg;
	}

	/**
	 * @param isImg
	 *            The isImg to set.
	 */
	public void setImg(boolean isImg) {
		this.isImg = isImg;
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton() {
		if (jRadioButton == null) {
			jRadioButton = new JRadioButton();
			jRadioButton.setBounds(new java.awt.Rectangle(67, 172, 117, 19));
			jRadioButton.setText("按申报日期查询");
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
			jRadioButton1.setBounds(new java.awt.Rectangle(200, 171, 112, 21));
			jRadioButton1.setText("按结关日期查询");
		}
		return jRadioButton1;
	}

	public ButtonGroup getButtonGroup1() {
		if (buttonGroup1 == null) {
			buttonGroup1 = new ButtonGroup();
			buttonGroup1.add(getJRadioButton());
			buttonGroup1.add(getJRadioButton1());
		}
		return buttonGroup1;
	}

	public ButtonGroup getButtonGroup2() {
		if (buttonGroup2 == null) {
			buttonGroup2 = new ButtonGroup();
			buttonGroup2.add(getRadiotrue());
			buttonGroup2.add(getRadiofalse());
			buttonGroup2.add(getRadioall());
		}
		return buttonGroup2;
	}
	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setEnabled(false);
			jComboBox.setBounds(new java.awt.Rectangle(222, 109, 113, 21));
		}
		return jComboBox;
	}

	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			jCheckBox = new JCheckBox();
			jCheckBox.setBounds(new java.awt.Rectangle(41, 109, 123, 21));
			jCheckBox.setForeground(java.awt.Color.red);
			jCheckBox.setText("是否分事业部统计");
			jCheckBox.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (jCheckBox.isSelected()) {
						jComboBox.setEnabled(true);
					} else {
						jComboBox.setEnabled(false);
					}
				}
			});
		}
		return jCheckBox;
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
			jPanel.setBounds(new Rectangle(41, 197, 292, 31));
			jPanel.add(getRadiotrue(), null);
			jPanel.add(getRadiofalse(), null);
			jPanel.add(getRadioall(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes Radiotrue
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRadiotrue() {
		if (Radiotrue == null) {
			Radiotrue = new JRadioButton();
			Radiotrue.setText("已生效");
			Radiotrue.setBounds(new Rectangle(60, 2, 61, 26));
			Radiotrue.setSelected(true);
		}
		return Radiotrue;
	}

	/**
	 * This method initializes Radiofalse
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRadiofalse() {
		if (Radiofalse == null) {
			Radiofalse = new JRadioButton();
			Radiofalse.setText("未生效");
			Radiofalse.setBounds(new Rectangle(121, 2, 61, 26));
		}
		return Radiofalse;
	}

	/**
	 * This method initializes Radioall
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRadioall() {
		if (Radioall == null) {
			Radioall = new JRadioButton();
			Radioall.setText("全部");
			Radioall.setBounds(new Rectangle(182, 2, 49, 26));
		}
		return Radioall;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
