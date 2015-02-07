/*
 * Created on 2005-6-8
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.cas.specificontrol;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.entity.StatCusNameRelationMt;
import com.bestway.bcus.cas.entity.TempObject;
import com.bestway.bcus.cas.specificontrol.entity.TempBillCorrespondingSearch;
import com.bestway.bcus.client.cas.CasQuery;
import com.bestway.bcus.client.cas.parameter.CasCommonVars;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseComboBoxUI;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.client.common.ProgressTask;
import com.bestway.bcus.innermerge.action.CommonBaseCodeAction;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.CommonUtils;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author ls // change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class DgBillCorrespondingSearch extends JDialogBase {

	private JPanel jContentPane = null;

	private ButtonGroup buttonGroup = null; // @jve:decl-index=0:

	private JPanel jPanel = null;

	private JButton btnSearch = null;

	private JButton btnExit = null;

	private JPanel jPanel1 = null;

	private JComboBox cbbName = null;

	private JComboBox cbbSpec = null;

	private JComboBox cbbScmCoc = null;

	private JRadioButton rbProduct = null;

	private JRadioButton rbMateriel = null;

	private JLabel jLabel3 = null;

	private JComboBox cbbImpExpType = null;

	private JCalendarComboBox cbbBeginDate = null;

	private JCalendarComboBox cbbEndDate = null;

	private JScrollPane jScrollPane1 = null;

	private JTable jTable = null;

	private JSplitPane jSplitPane1 = null;

	private JLabel jLabel6 = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel4 = null;

	private JComboBox cbbEmsNo = null;

	private JLabel jLabel5 = null;

	private JComboBox cbbCustomsDeclarationNo = null;

	private JLabel jLabel7 = null;

	private JTextField tfPtNo = null;

	private JTextField tfEndPtNo = null;

	private JTextField tfName = null;

	private JLabel jLabel8 = null;

	private JLabel jLabel9 = null;

	private JLabel jLabel10 = null;

	private JButton btnPtNo = null;

	private JButton btnEndPtNo = null;

	private JButton btnPtName = null;

	private MaterialManageAction materialManageAction = null;

	private CasAction casAction = null;

	private Integer maximumFractionDigits = CasCommonVars.getOtherOption()
			.getInOutMaximumFractionDigits() == null ? 6 : CasCommonVars
			.getOtherOption().getInOutMaximumFractionDigits();

	/**
	 * 报关单商品信息与单据对应数据模型
	 */
	private JTableListModel tableModelMakeBillCorrespondingInfo = null;

	private String materielType = MaterielType.FINISHED_PRODUCT;  //  @jve:decl-index=0:

	/**
	 * This method initializes
	 * 
	 */
	public DgBillCorrespondingSearch() {
		super(false);
		initialize();
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
				"casAction");
		initUIComponents();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("单据对应查询");
		this.setContentPane(getJContentPane());
		this.setSize(755, 555);
		this.getButtonGroup();
	}

	/**
	 * 初始化 buttonGroup
	 * 
	 * @return
	 */

	private ButtonGroup getButtonGroup() {
		if (this.buttonGroup == null) {
			buttonGroup = new ButtonGroup();
			buttonGroup.add(rbMateriel);
			buttonGroup.add(rbProduct);
		}
		return buttonGroup;
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJSplitPane1(), java.awt.BorderLayout.CENTER);
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
			jPanel.setLayout(null);
			jPanel.add(getJPanel1(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes btnSearch
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSearch() {
		if (btnSearch == null) {
			btnSearch = new JButton();
			btnSearch.setText("查询");
			btnSearch.setBounds(new java.awt.Rectangle(651, 34, 66, 23));
			btnSearch.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					search();
				}
			});
		}
		return btnSearch;
	}

	/**
	 * This method initializes btnExit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("关闭");
			btnExit.setBounds(new java.awt.Rectangle(651, 60, 66, 23));
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnExit;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLabel10 = new JLabel();
			jLabel10.setBounds(new java.awt.Rectangle(464, 79, 50, 20));
			jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel10.setText("结束料号");
			jLabel9 = new JLabel();
			jLabel9.setBounds(new java.awt.Rectangle(245, 101, 54, 20));
			jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel9.setText("工厂名称");
			jLabel8 = new JLabel();
			jLabel8.setBounds(new java.awt.Rectangle(245, 79, 54, 20));
			jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel8.setText("开始料号");
			jLabel7 = new JLabel();
			jLabel7.setBounds(new java.awt.Rectangle(243, 57, 56, 20));
			jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel7.setText("报关单号");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new java.awt.Rectangle(5, 58, 82, 18));
			jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
			jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel5.setText("合同号(手册号)");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new java.awt.Rectangle(37, 101, 51, 20));
			jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
			jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel4.setText("报关规格");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new java.awt.Rectangle(37, 79, 51, 20));
			jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
			jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel2.setText("报关名称");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new java.awt.Rectangle(462, 35, 52, 20));
			jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
			jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel1.setText("结束日期");
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(244, 35, 55, 20));
			jLabel.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
			jLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel.setText("开始日期");
			jLabel6 = new JLabel();
			jLabel6.setBounds(new java.awt.Rectangle(463, 57, 51, 20));
			jLabel6.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
			jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel6.setText("客户名称");
			jPanel1 = new JPanel();
			jLabel3 = new JLabel();
			jPanel1.setLayout(null);
			jPanel1.setBounds(1, -3, 745, 133);
			jPanel1
					.setBorder(javax.swing.BorderFactory
							.createTitledBorder(
									javax.swing.BorderFactory
											.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED),
									"过滤条件",
									javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
									javax.swing.border.TitledBorder.DEFAULT_POSITION,
									new java.awt.Font("Dialog",
											java.awt.Font.PLAIN, 12),
									new java.awt.Color(51, 51, 51)));
			jLabel3.setBounds(37, 35, 51, 20);
			jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
			jLabel3.setText("单据类型");
			jPanel1.add(getRbProduct(), null);
			jPanel1.add(getRbMateriel(), null);
			jPanel1.add(jLabel3, null);
			jPanel1.add(getCbbImpExpType(), null);
			jPanel1.add(getCbbScmCoc(), null);
			jPanel1.add(jLabel6, null);
			jPanel1.add(getCbbName(), null);
			jPanel1.add(getCbbSpec(), null);
			jPanel1.add(getCbbBeginDate(), null);
			jPanel1.add(getCbbEndDate(), null);
			jPanel1.add(jLabel, null);
			jPanel1.add(jLabel1, null);
			jPanel1.add(getBtnSearch(), null);
			jPanel1.add(getBtnExit(), null);
			jPanel1.add(jLabel2, null);
			jPanel1.add(jLabel4, null);
			jPanel1.add(getCbbEmsNo(), null);
			jPanel1.add(jLabel5, null);
			jPanel1.add(getCbbCustomsDeclarationNo(), null);
			jPanel1.add(jLabel7, null);
			jPanel1.add(getTfPtNo(), null);
			jPanel1.add(getTfEndPtNo(), null);
			jPanel1.add(getTfName(), null);
			jPanel1.add(jLabel8, null);
			jPanel1.add(jLabel9, null);
			jPanel1.add(jLabel10, null);
			jPanel1.add(getBtnPtNo(), null);
			jPanel1.add(getBtnEndPtNo(), null);
			jPanel1.add(getBtnPtName(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes cbbName
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbName() {// 商品名称
		if (cbbName == null) {
			cbbName = new JComboBox();
			cbbName.setUI(new CustomBaseComboBoxUI(270));
			cbbName.setEditable(true);
			cbbName.setBounds(new java.awt.Rectangle(89, 79, 144, 20));
			cbbName.setBorder(javax.swing.BorderFactory.createLineBorder(
					new java.awt.Color(106, 135, 171), 1));
			cbbName.addItemListener(new ItemListener() {

				public void itemStateChanged(ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						String cusName = (String) cbbName.getSelectedItem();
						if (cusName == null || cusName.trim().equals("")) {
							return;
						}
						//
						// 初始化商品大类规格型号
						//
						List specList = casAction.findStatCusNameRelationSpec(
								new Request(CommonVars.getCurrUser(), true),
								materielType, cusName);
						cbbSpec.removeAllItems();
						for (int i = 0; i < specList.size(); i++) {
							String name = (String) specList.get(i);
							if (name == null || "".equals(name.trim())) {
								continue;
							}
							cbbSpec.addItem(name);
						}
					}
				}

			});
		}
		return cbbName;
	}

	/**
	 * This method initializes cbbSpec
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbSpec() {// 商品规格
		if (cbbSpec == null) {
			cbbSpec = new JComboBox();
			cbbSpec.setUI(new CustomBaseComboBoxUI(270));
			cbbSpec.setEditable(true);
			cbbSpec.setBounds(new java.awt.Rectangle(89, 101, 144, 20));
			cbbSpec.setBorder(javax.swing.BorderFactory.createLineBorder(
					new java.awt.Color(106, 135, 171), 1));
		}
		return cbbSpec;
	}

	/**
	 * This method initializes cbbScmCoc
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbScmCoc() {
		if (cbbScmCoc == null) {
			cbbScmCoc = new JComboBox();
			cbbScmCoc.setBounds(new java.awt.Rectangle(516, 57, 121, 20));
		}
		return cbbScmCoc;
	}

	/**
	 * This method initializes rbProduct
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbProduct() {
		if (rbProduct == null) {
			rbProduct = new JRadioButton();
			rbProduct.setBounds(94, 15, 55, 17);
			rbProduct.setText("成品");
			rbProduct.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					initBillTypeUI();// 初始化 类型与报关名称 控件
				}
			});
		}
		return rbProduct;
	}

	/**
	 * This method initializes rbMateriel
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbMateriel() {
		if (rbMateriel == null) {
			rbMateriel = new JRadioButton();
			rbMateriel.setSelected(true);
			rbMateriel.setBounds(187, 15, 54, 17);
			rbMateriel.setText("料件");
			rbMateriel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					initBillTypeUI();
				}
			});
		}
		return rbMateriel;
	}

	/**
	 * This method initializes cbbImpExpType
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbImpExpType() {
		if (cbbImpExpType == null) {
			cbbImpExpType = new JComboBox();
			cbbImpExpType.setBounds(89, 35, 144, 20);
			cbbImpExpType.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						if (cbbEmsNo.getSelectedItem() != null) {
							initCbbCustomsDeclarationNo();
						}
					}

				}

			});
		}
		return cbbImpExpType;
	}

	/**
	 * This method initializes jSplitPane1
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane1() {
		if (jSplitPane1 == null) {
			jSplitPane1 = new JSplitPane();
			jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane1.setDividerLocation(128);
			jSplitPane1.setDividerSize(5);
			jSplitPane1.setBottomComponent(getJScrollPane1());
			jSplitPane1.setTopComponent(getJPanel());
		}
		return jSplitPane1;
	}

	/**
	 * This method initializes cbbBeginDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(new java.awt.Rectangle(300, 35, 154, 20));
		}
		return cbbBeginDate;
	}

	/**
	 * This method initializes cbbEndDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbEndDate() {
		if (cbbEndDate == null) {
			cbbEndDate = new JCalendarComboBox();
			cbbEndDate.setBounds(new java.awt.Rectangle(516, 35, 121, 20));
		}
		return cbbEndDate;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJTable());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
		}
		return jTable;
	}

	/**
	 * 初始化类型控件
	 * 
	 */
	private void initBillTypeUI() {

		if (this.rbMateriel.isSelected()) {
			// 初始化 ----报关单---- 进口类型
			this.cbbImpExpType.removeAllItems();
			this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.DIRECT_IMPORT).toString(), "直接进口"));
			this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.TRANSFER_FACTORY_IMPORT).toString(), "结转进口"));
			this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.MATERIAL_EXCHANGE).toString(), "料件退换"));
			this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.MATERIAL_REOUT).toString(), "料件复出"));
			CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
					this.cbbImpExpType);
			this.cbbImpExpType.setRenderer(CustomBaseRender.getInstance()
					.getRender());
			materielType = MaterielType.MATERIEL;
		} else if (this.rbProduct.isSelected()) {
			// 初始化出口类型
			this.cbbImpExpType.removeAllItems();
			this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.DIRECT_EXPORT).toString(), "直接出口"));
			this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.TRANSFER_FACTORY_EXPORT).toString(), "结转出口"));
			this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.BACK_FACTORY_REWORK).toString(), "退厂返工"));
			this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.REWORK_EXPORT).toString(), "返工复出"));
			CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
					this.cbbImpExpType);
			this.cbbImpExpType.setRenderer(CustomBaseRender.getInstance()
					.getRender());
			materielType = MaterielType.FINISHED_PRODUCT;
		}
		//
		// 初始化商品大类名称
		//
		List nameList = casAction.findStatCusNameRelationName2(new Request(
				CommonVars.getCurrUser(), true), materielType);
		this.cbbName.removeAllItems();
		for (int i = 0; i < nameList.size(); i++) {
			String name = (String) nameList.get(i);
			if (name == null || "".equals(name.trim())) {
				continue;
			}
			this.cbbName.addItem(name);
		}
	}

	/**
	 * 初始化数据
	 * 
	 */
	private void initUIComponents() {
		// 初始化客户commonBaseCodeAction
		List list = materialManageAction.findScmCocs(new Request(CommonVars
				.getCurrUser(), true));
		this.cbbScmCoc.setModel(new DefaultComboBoxModel(list.toArray()));
		this.cbbScmCoc.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 65, 170));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbScmCoc, "code", "name", 235);
		this.cbbScmCoc.setSelectedItem(null);
		//
		// 初始化类型
		//
		initBillTypeUI();
		//
		// init 手册号(合同号)
		//
		List emsNoList = new ArrayList();
		emsNoList = this.casAction.findEmsNo(new Request(CommonVars
				.getCurrUser()));
		this.cbbEmsNo.removeAllItems();
		for (int i = 0; i < emsNoList.size(); i++) {
			String emsNo = (String) emsNoList.get(i);
			if (emsNo == null) {
				continue;
			}
			this.cbbEmsNo.addItem(emsNo);
		}
		//
		// 初始化日历控件
		//
		this.cbbBeginDate.setDate(null);
		this.cbbEndDate.setDate(null);
		//
		// 初始化表1
		//		
		initTable(new ArrayList());
	}

	/**
	 * 初始化报关单号
	 * 
	 */
	private void initCbbCustomsDeclarationNo() {
		ItemProperty item = (ItemProperty) this.cbbImpExpType.getSelectedItem();
		int impExpType = Integer.parseInt((item.getCode().equals("18") || item
				.getCode().equals("19")) ? "6" : item.getCode());
		//
		// 初始化报关单号
		//				
		List customsDeclarationNoList = new ArrayList();
		customsDeclarationNoList = this.casAction.findCustomsDeclarationCode(
				new Request(CommonVars.getCurrUser()), impExpType,
				(String) this.cbbEmsNo.getSelectedItem());
		this.cbbCustomsDeclarationNo.removeAllItems();
		for (int i = 0; i < customsDeclarationNoList.size(); i++) {
			String customsDeclarationNo = (String) customsDeclarationNoList
					.get(i);
			if (customsDeclarationNo == null) {
				continue;
			}
			this.cbbCustomsDeclarationNo.addItem(customsDeclarationNo);
		}
	}

	/**
	 * 查询
	 * 
	 */
	private void search() {
		if (validateData() == false) {
			return;
		}
		new SearchThread().start();
	}

	/**
	 * 验证查询情况
	 * 
	 * @return
	 */
	private boolean validateData() {// 为 False 返回
		Date beginDate = this.cbbBeginDate.getDate();
		Date endDate = this.cbbEndDate.getDate();
		beginDate = CommonUtils.getBeginDate(beginDate);
		endDate = CommonUtils.getEndDate(endDate);
		if (beginDate != null && endDate != null) {
			if (beginDate.after(endDate)) {
				JOptionPane.showMessageDialog(this, "开始日期大于结束日期!!", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
		}
		if (this.cbbImpExpType.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(this, "请选择要查询的单据类型!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		String customsDeclarationNo = (String) this.cbbCustomsDeclarationNo
				.getSelectedItem();
		String ptNo = tfPtNo.getText().trim();
		if (ptNo.equals("")
				&& (customsDeclarationNo == null || customsDeclarationNo
						.equals(""))) {
			if (JOptionPane.showConfirmDialog(this,
					"[报关单号],[开始料号] 都为空!!\n查询可能要较长的时间，要继续吗??", "确认",
					JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) != JOptionPane.YES_OPTION) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 取消单据对应线程
	 * 
	 * @author ls
	 * 
	 */
	class SearchThread extends Thread {

		public void run() {
			//
			// 用于标识这个对话框的ID
			//
			UUID uuid = UUID.randomUUID();
			final String flag = uuid.toString();
			try {
				btnSearch.setEnabled(false);

				String info = "";
				long beginTime = System.currentTimeMillis();
				ProgressTask progressTask = new ProgressTask() {
					protected void setClientTipMessage() {
						// String tipMessage = casAction
						// .getClientTipMessageByBalanceInfo(new Request(
						// CommonVars.getCurrUser(), true));
						// CommonProgress.setMessage(flag, tipMessage);
					}
				};
				CommonProgress.showProgressDialog(flag,
						DgBillCorrespondingSearch.this, false, progressTask,
						5000);
				CommonProgress.setMessage(flag, "系统正在获取数据，请稍后...");

				List tempList = casAction.findMakeBillCorrespondingInfo(
						new Request(CommonVars.getCurrUser()),
						getTempBillCorrespondingSearch());
				initTable(tempList);

				CommonProgress.closeProgressDialog(flag);
				info += " 任务完成,共用时:" + (System.currentTimeMillis() - beginTime)
						+ " 毫秒 ";
				JOptionPane.showMessageDialog(DgBillCorrespondingSearch.this,
						info, "提示", 2);

			} catch (Exception e) {
				e.printStackTrace();
				CommonProgress.closeProgressDialog(flag);
				JOptionPane.showMessageDialog(DgBillCorrespondingSearch.this,
						"系统正在获取数据失败！！！" + e.getMessage(), "提示", 2);
			}
			btnSearch.setEnabled(true);
		}
	}

	/**
	 * 生成查询条件对象
	 * 
	 * 
	 * @return
	 */
	private TempBillCorrespondingSearch getTempBillCorrespondingSearch() {
		TempBillCorrespondingSearch temp = new TempBillCorrespondingSearch();
		Date beginDate = this.cbbBeginDate.getDate();
		Date endDate = this.cbbEndDate.getDate();
		beginDate = CommonUtils.getBeginDate(beginDate);
		endDate = CommonUtils.getEndDate(endDate);
		Integer impExpType = Integer.valueOf(((ItemProperty) this.cbbImpExpType
				.getSelectedItem()).getCode());// 单据类型代码
		String customsDeclarationNo = (String) this.cbbCustomsDeclarationNo
				.getSelectedItem();
		String ptPart = tfPtNo.getText().trim();
		String endPtPart = tfEndPtNo.getText().trim();
		String ptName = tfName.getText().trim();
		String emsNo = (String) this.cbbEmsNo.getSelectedItem();
		String commName = (String) this.cbbName.getSelectedItem();
		String commSpec = (String) this.cbbSpec.getSelectedItem();

		temp.setBeginDate(beginDate);
		temp.setEndDate(endDate);
		temp.setImpExpType(impExpType);
		temp.setScmCoc((ScmCoc) this.cbbScmCoc.getSelectedItem());
		temp.setCustomsDeclarationCode(customsDeclarationNo);
		temp.setPtPart(ptPart);
		temp.setEndPtPart(endPtPart);
		temp.setPtName(ptName);
		temp.setEmsHeadH2k(emsNo);
		temp.setCommName(commName);
		temp.setCommSpec(commSpec);

		return temp;
	}

	/**
	 * 初始化表3
	 * 
	 */
	private void initTable(List list) {
		tableModelMakeBillCorrespondingInfo = new JTableListModel(jTable, list,
				new JTableListModelAdapter(maximumFractionDigits) {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("单据号", "billNo", 100));
						list.add(addColumn("报关单号", "customsDeclarationCode",
								100));
						list.add(addColumn("单据类型", "billTypeName", 100));
						list.add(addColumn("进出口类型", "impExpType", 100));
						list.add(addColumn("对应报关数量", "amount", 100));
						list.add(addColumn("生效时间", "validDate", 100));
						list.add(addColumn("商品料号", "ptPart", 100));
						list.add(addColumn("商品名称", "ptName", 100));
						list.add(addColumn("商品规格", "ptSpec", 100));
						list.add(addColumn("电子帐册号码/合同手册号", "emsHeadH2k", 150));
						list.add(addColumn("客户名称", "scmCoc.name", 100));
						list.add(addColumn("报关商品名称", "commName", 100));
						list.add(addColumn("报关型号规格", "commSpec", 100));
						return list;
					}
				});
		jTable.getColumnModel().getColumn(4).setCellRenderer(
				new tableCellRender());
		jTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	}

	class tableCellRender extends DefaultTableCellRenderer {
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			super.getTableCellRendererComponent(table, value, isSelected,
					hasFocus, row, column);
			String str = "";
			if (value != null) {
				switch (Integer.parseInt(value.toString())) {
				case ImpExpType.DIRECT_IMPORT:
					str = "料件进口";
					break;
				case ImpExpType.TRANSFER_FACTORY_IMPORT:
					str = "料件转厂";
					break;
				case ImpExpType.BACK_FACTORY_REWORK:
					str = "退厂返工";
					break;
				case ImpExpType.GENERAL_TRADE_IMPORT:
					str = "一般贸易进口";
					break;
				case ImpExpType.DIRECT_EXPORT:
					str = "成品出口";
					break;
				case ImpExpType.TRANSFER_FACTORY_EXPORT:
					str = "转厂出口";
					break;
				case ImpExpType.BACK_MATERIEL_EXPORT:
					str = "退料出口";
					break;
				case ImpExpType.REWORK_EXPORT:
					str = "返工复出";
					break;
				case ImpExpType.REMIAN_MATERIAL_BACK_PORT:
					str = "边角料退港";
					break;
				case ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES:
					str = "边角料内销";
					break;
				case ImpExpType.GENERAL_TRADE_EXPORT:
					str = "一般贸易出口";
					break;
				case ImpExpType.EQUIPMENT_IMPORT:
					str = "设备进口";
					break;
				case ImpExpType.BACK_PORT_REPAIR:
					str = "退港维修";
					break;
				case ImpExpType.EQUIPMENT_BACK_PORT:
					str = "设备退港";
					break;

				case ImpExpType.REMAIN_FORWARD_EXPORT:
					str = "余料结转";
					break;

				case ImpExpType.EXPORT_STORAGE:
					str = "出口仓储";
					break;

				case ImpExpType.IMPORT_STORAGE:
					str = "进口仓储";
					break;
				case ImpExpType.MATERIAL_DOMESTIC_SALES:
					str = "料件内销";
					break;
				case ImpExpType.MATERIAL_EXCHANGE:
					str = "料件退换";
					break;
				case ImpExpType.MATERIAL_REOUT:
					str = "料件复出";
					break;

				}
				this.setText(str);
			}
			return this;
		}
	}

	/**
	 * This method initializes cbbEmsNo
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbEmsNo() {
		if (cbbEmsNo == null) {
			cbbEmsNo = new JComboBox();
			cbbEmsNo.setUI(new CustomBaseComboBoxUI(270));
			cbbEmsNo.setEditable(true);
			cbbEmsNo.setBorder(javax.swing.BorderFactory.createLineBorder(
					new java.awt.Color(106, 135, 171), 1));
			cbbEmsNo.setBounds(new java.awt.Rectangle(89, 57, 144, 20));
			cbbEmsNo.addItemListener(new ItemListener() {

				public void itemStateChanged(ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						initCbbCustomsDeclarationNo();
					}

				}

			});
		}
		return cbbEmsNo;
	}

	/**
	 * This method initializes cbbCustomsDeclarationNo
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCustomsDeclarationNo() {
		if (cbbCustomsDeclarationNo == null) {
			cbbCustomsDeclarationNo = new JComboBox();
			cbbCustomsDeclarationNo.setUI(new CustomBaseComboBoxUI(270));
			cbbCustomsDeclarationNo.setEditable(true);
			cbbCustomsDeclarationNo.setBorder(javax.swing.BorderFactory
					.createLineBorder(new java.awt.Color(106, 135, 171), 1));
			cbbCustomsDeclarationNo.setBounds(new java.awt.Rectangle(300, 57,
					154, 20));
		}
		return cbbCustomsDeclarationNo;
	}

	/**
	 * This method initializes tfPtNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfPtNo() {
		if (tfPtNo == null) {
			tfPtNo = new JTextField();
			tfPtNo.setBounds(new java.awt.Rectangle(300, 79, 135, 20));
			tfPtNo.getDocument().addDocumentListener(new DocumentListener() {
				private void setState() {
					String editAfterQueryValue = tfPtNo.getText().trim();
					if (!"".equalsIgnoreCase(editAfterQueryValue)) {
						tfEndPtNo.setEditable(true);
						btnEndPtNo.setEnabled(true);
					} else {
						tfEndPtNo.setEditable(false);
						btnEndPtNo.setEnabled(false);
					}
				}

				public void insertUpdate(DocumentEvent e) {
					setState();
				}

				public void removeUpdate(DocumentEvent e) {
					setState();
				}

				public void changedUpdate(DocumentEvent e) {
					setState();
				}

			});
		}
		return tfPtNo;
	}

	/**
	 * This method initializes tfEndPtNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfEndPtNo() {
		if (tfEndPtNo == null) {
			tfEndPtNo = new JTextField();
			tfEndPtNo.setEditable(false);
			tfEndPtNo.setBounds(new java.awt.Rectangle(516, 79, 102, 20));
		}
		return tfEndPtNo;
	}

	/**
	 * This method initializes tfName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfName() {
		if (tfName == null) {
			tfName = new JTextField();
			tfName.setBounds(new java.awt.Rectangle(300, 101, 135, 20));
		}
		return tfName;
	}

	/**
	 * This method initializes btnPtNo
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPtNo() {
		if (btnPtNo == null) {
			btnPtNo = new JButton();
			btnPtNo.setBounds(new java.awt.Rectangle(435, 79, 19, 20));
			btnPtNo.setText("...");
			btnPtNo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List<StatCusNameRelationMt> list = CasQuery.getInstance()
							.getStatCusNameRelationMt(false, materielType,
									new ArrayList());
					if (list != null && list.size() > 0) {
						StatCusNameRelationMt sm = list.get(0);
						Materiel m = sm.getMateriel();
						tfPtNo.setText(m == null ? "" : m.getPtNo());
					}
				}
			});

		}
		return btnPtNo;
	}

	/**
	 * This method initializes btnEndPtNo
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEndPtNo() {
		if (btnEndPtNo == null) {
			btnEndPtNo = new JButton();
			btnEndPtNo.setEnabled(false);
			btnEndPtNo.setBounds(new java.awt.Rectangle(620, 79, 18, 20));
			btnEndPtNo.setText("...");
			btnEndPtNo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List<StatCusNameRelationMt> list = CasQuery.getInstance()
							.getStatCusNameRelationMt(false, materielType,
									new ArrayList());
					if (list != null && list.size() > 0) {
						StatCusNameRelationMt sm = list.get(0);
						Materiel m = sm.getMateriel();
						tfEndPtNo.setText(m == null ? "" : m.getPtNo());
					}
				}
			});
		}
		return btnEndPtNo;
	}

	/**
	 * This method initializes btnPtName
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPtName() {
		if (btnPtName == null) {
			btnPtName = new JButton();
			btnPtName.setBounds(new java.awt.Rectangle(435, 101, 19, 20));
			btnPtName.setText("...");
			btnPtName.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Object object = CasQuery.getInstance()
							.findPtNameFromBillDetail(materielType);
					if (object != null) {
						tfName.setText((String) ((TempObject) object)
								.getObject());
					}
				}
			});
		}
		return btnPtName;
	}

}
