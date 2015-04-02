/*
 * Created on 2005-6-8
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.cas.specificontrol;

/**
 * 单据对应
 * 刘民添加部分注释
 * 修改时间： 
 * @author ls // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 * 
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import org.apache.commons.beanutils.PropertyUtils;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.bill.entity.BillDetail;
import com.bestway.bcus.cas.bill.entity.BillMaster;
import com.bestway.bcus.cas.entity.BillType;
import com.bestway.bcus.cas.parameterset.entity.BillCorrespondingControl;
import com.bestway.bcus.cas.parameterset.entity.BillCorrespondingOption;
import com.bestway.bcus.cas.specificontrol.entity.CustomsDeclarationCommInfoBillCorresponding;
import com.bestway.bcus.cas.specificontrol.entity.MakeBillCorrespondingInfoBase;
import com.bestway.bcus.cas.specificontrol.entity.TempResult;
import com.bestway.bcus.client.cas.parameter.CasCommonVars;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseComboBoxUI;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.client.common.ProgressTask;
import com.bestway.client.util.CommonTableContextPopupEvent;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.CommonUtils;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.client.erpbillcenter.DgBillMaster;
import com.bestway.common.client.erpbillcenter.parameter.ErpBillParameterCommonVars;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.ui.winuicontrol.JFrameBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author ls
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class FmBillCorresponding extends JFrameBase {

	private JPanel jContentPane = null;

	private JTabbedPane jTabbedPane = null;

	private JPanel pn1 = null;

	private JPanel pn2 = null;

	private JPanel pn3 = null;

	private ButtonGroup buttonGroup = null; // @jve:decl-index=0:

	private ButtonGroup buttonGroup2 = null; // @jve:decl-index=0:

	private JPanel jPanel = null;

	private JToolBar jToolBar = null;

	private JButton btnSearch = null;

	private JButton btnCorresponding = null;

	private JButton btnCancelCorresponding = null;

	private JButton btnExit = null;

	private JComboBox cbbName = null;

	private JLabel jLabel2 = null;

	private JComboBox cbbScmCoc = null;

	private JRadioButton rbProduct = null;

	private JRadioButton rbMateriel = null;

	// private JLabel jLabel3 = null;

	private JComboBox cbbImpExpType = null;

	private JCalendarComboBox cbbBeginDate = null;

	private JCalendarComboBox cbbEndDate = null;

	private JTable tb1 = null;

	private JScrollPane jScrollPane = null;

	private JScrollPane jScrollPane1 = null;

	private JTable tb2 = null;

	private JScrollPane jScrollPane2 = null;

	private JTable tb3 = null;

	private JSplitPane jSplitPane = null;

	private MaterialManageAction materialManageAction = null;

	private CasAction casAction = null;

	private JTableListModel tableModelBillDetail = null;

	private JMenuItem miCancelCorresponding = null;

	private JPanel pn4 = null;

	private JCheckBox cbIsBillCorrespondingAffirm = null;

	private JButton btnSave = null;

	private JButton btnEdit = null;

	private JLabel lbHsAmount = null;

	private JLabel lbCustomAmount = null;

	private boolean isNameSpec = false;// 判断是选择名称(true)还是名称+规格(false)

	/**
	 * 报关单商品信息数据模型
	 */
	private JTableListModel tableModelCustomsDeclarationCommInfo = null;

	/**
	 * 报关单商品信息与单据对应数据模型
	 */
	private JTableListModel tableModelMakeBillCorrespondingInfo = null;

	private JPopupMenu jPopupMenu1 = null;

	private JPopupMenu jPopupMenu2 = null;

	private boolean isCopyMenuEnabled1 = false;

	private boolean isCopyMenuEnabled2 = false;

	private boolean isCopyMenuEnabled3 = false;

	private String materielType = MaterielType.FINISHED_PRODUCT; // @jve:decl-index=0:

	private BillCorrespondingOption b = CasCommonVars
			.getBillCorrespondingOption(); // @jve:decl-index=0:

	private Integer maximumFractionDigits = CasCommonVars.getOtherOption()
			.getInOutMaximumFractionDigits() == null ? 6 : CasCommonVars
			.getOtherOption().getInOutMaximumFractionDigits(); // @jve:decl-index=0:

	// 是否显示 对应记录 当单据对应时数量小于？时
	private boolean isShowBillCorrRecord = ErpBillParameterCommonVars
			.getCasBillOption().getIsShowBillCorrRecord();

	// 小于多少时不显示？（大小）
	private double noShowItemValue = ErpBillParameterCommonVars
			.getCasBillOption().getShowBillCorrRecord();

	/**
	 * 是否需提示关封号不相同
	 */
	private boolean isWarnEnvelopNo = false;

	/**
	 * This method initializes
	 * 
	 */
	public FmBillCorresponding() {

		super();

		initialize();

		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");

		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
				"casAction");

		initUIComponents();

		showBillCorrespondingOption();

		setStatePn3(DataState.BROWSE);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("单据对应");
		this.setContentPane(getJContentPane());
		this.setSize(835, 605);
		this.getButtonGroup();
		this.getButtonGroup2();
	}

	private ButtonGroup getButtonGroup() {
		if (this.buttonGroup == null) {
			buttonGroup = new ButtonGroup();
			buttonGroup.add(rbMateriel);
			buttonGroup.add(rbProduct);
		}
		return buttonGroup;
	}

	private ButtonGroup getButtonGroup2() {
		if (this.buttonGroup2 == null) {
			buttonGroup2 = new ButtonGroup();
			buttonGroup2.add(rbName);
			buttonGroup2.add(rbNameSpec);
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
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setTabPlacement(javax.swing.JTabbedPane.TOP);
			jTabbedPane.addTab("单据对应", null, getPn1(), null);
			jTabbedPane.addTab("取消对应", null, getPn2(), null);
			jTabbedPane.addTab("对应选项", null, getPn4(), null);
			jTabbedPane.addChangeListener(new ChangeListener() {

				public void stateChanged(ChangeEvent e) {
					setState();
				}

			});
		}
		return jTabbedPane;
	}

	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPn1() {
		if (pn1 == null) {
			pn1 = new JPanel();
			pn1.setLayout(new BorderLayout());
			pn1.setToolTipText("单据对应");
			pn1.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
		}
		return pn1;
	}

	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPn2() {
		if (pn2 == null) {
			pn2 = new JPanel();
			pn2.setLayout(new BorderLayout());
			pn2.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
		}
		return pn2;
	}

	/**
	 * This method initializes jPanel6
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPn4() {
		if (pn3 == null) {
			jLabel6 = new JLabel();
			jLabel6.setBounds(new Rectangle(62, 280, 686, 18));
			jLabel6.setText("4.单据和报关单均可多选再进行对应。如果两边数量不等，则以数量小的作为对应数量，余下数量可在下一次进行对应。");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(62, 260, 456, 18));
			jLabel5.setText("3.如果单据做了与报关单的对应，会在单据体的‘对应报关数量’栏位中体现。");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(62, 240, 457, 18));
			jLabel4.setText("2.对应后可在【取消对应】中查询对应情况，同时也可以在这里取消对应情况。");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(62, 220, 454, 18));
			jLabel3.setText("1.此功能将工厂单据数据与报关单中的资料进行对应。");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(22, 190, 73, 18));
			jLabel1.setFont(new Font("Dialog", Font.BOLD, 12));
			jLabel1.setForeground(new Color(255, 51, 51));
			jLabel1.setText("单据对应：");
			pn3 = new JPanel();
			pn3.setLayout(null);
			pn3.add(getPn42(), null);
			pn3.add(jLabel1, null);
			pn3.add(jLabel3, null);
			pn3.add(jLabel4, null);
			pn3.add(jLabel5, null);
			pn3.add(jLabel6, null);
		}
		return pn3;
	}

	/**
	 * This method initializes pn4
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPn42() {
		if (pn4 == null) {
			pn4 = new JPanel();
			pn4.setLayout(null);
			pn4.setBounds(new Rectangle(19, 13, 707, 158));
			pn4.setBorder(javax.swing.BorderFactory.createTitledBorder(
					javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED),
					"选项",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			pn4.add(getCbIsBillCorrespondingAffirm(), null);
			pn4.add(getBtnSave(), null);
			pn4.add(getBtnEdit(), null);
		}
		return pn4;
	}

	/**
	 * This method initializes cbIsBillCorrespondingAffirm
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIsBillCorrespondingAffirm() {
		if (cbIsBillCorrespondingAffirm == null) {
			cbIsBillCorrespondingAffirm = new JCheckBox();
			cbIsBillCorrespondingAffirm.setBounds(new java.awt.Rectangle(36,
					48, 119, 21));
			cbIsBillCorrespondingAffirm.setText("单据对应需确认");
		}
		return cbIsBillCorrespondingAffirm;
	}

	/**
	 * This method initializes btnSave
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setBounds(new Rectangle(114, 103, 93, 23));
			btnSave.setText("保存设置");
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					saveBillCorrespondingOption();
				}
			});
		}
		return btnSave;
	}

	/**
	 * This method initializes btnEdit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setBounds(new java.awt.Rectangle(40, 103, 69, 23));
			btnEdit.setText("修改");
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setStatePn3(DataState.EDIT);
				}
			});
		}
		return btnEdit;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel7 = new JLabel();
			jLabel7.setBounds(new Rectangle(301, 100, 86, 18));
			jLabel7.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel7.setForeground(Color.red);
			jLabel7.setText("单据对应条件:");
			lbPtAmount = new JLabel();
			lbPtAmount.setBounds(new Rectangle(354, 124, 218, 22));
			lbPtAmount.setText("");
			lbPtAmount.setForeground(new java.awt.Color(255, 153, 0));
			lbInfo = new JLabel();
			lbInfo.setBounds(new Rectangle(22, 149, 640, 21));
			lbInfo.setFont(new Font("\u5b8b\u4f53", Font.PLAIN, 12));
			lbInfo.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			lbInfo.setForeground(Color.red);
			lbInfo.setText("建议一条“单据对应”表的数据所对应的报关单数不要超过7张，否则单据表体的 “对应报关单号”栏位会截取前7张!");
			jLabel33 = new JLabel();
			jLabel33.setBounds(new Rectangle(544, 75, 61, 22));
			jLabel33.setText("结束日期");
			jLabel32 = new JLabel();
			jLabel32.setBounds(new Rectangle(302, 75, 61, 22));
			jLabel32.setText("起始日期");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(301, 49, 61, 22));
			jLabel.setText("名称/规格");
			jLabel31 = new JLabel();
			jLabel31.setBounds(new Rectangle(18, 48, 57, 22));
			jLabel31.setText("单据类型");
			lbCustomAmount = new JLabel();
			lbHsAmount = new JLabel();
			jLabel2 = new JLabel();
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jLabel2.setBounds(18, 75, 57, 22);
			jLabel2.setText("客户名称:");
			lbHsAmount.setBounds(130, 124, 222, 22);
			lbHsAmount.setText("");
			lbHsAmount.setForeground(new java.awt.Color(255, 153, 0));
			lbCustomAmount.setBounds(577, 124, 212, 22);
			lbCustomAmount.setText("");
			lbCustomAmount.setForeground(new java.awt.Color(255, 153, 0));
			jPanel.add(getJToolBar(), null);
			jPanel.add(jLabel2, null);
			jPanel.add(getCbbScmCoc(), null);
			jPanel.add(lbHsAmount, null);
			jPanel.add(lbCustomAmount, null);
			jPanel.add(getCbbImpExpType(), null);
			// jPanel.add(jLabel3, null);
			jPanel.add(getRbProduct(), null);
			jPanel.add(getRbMateriel(), null);
			jPanel.add(jLabel31, null);
			jPanel.add(getCbbName(), null);
			jPanel.add(jLabel, null);
			jPanel.add(getCbbBeginDate(), null);
			jPanel.add(getCbbEndDate(), null);
			jPanel.add(jLabel32, null);
			jPanel.add(jLabel33, null);
			jPanel.add(lbInfo, null);
			jPanel.add(lbPtAmount, null);
			jPanel.add(jLabel7, null);
			jPanel.add(getRbName(), null);
			jPanel.add(getRbNameSpec(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.setBounds(0, 0, 747, 34);
			jToolBar.add(getBtnSearch());
			jToolBar.add(getBtnCorresponding());
			jToolBar.add(getBtnCancelCorresponding());
			jToolBar.add(getBtnExit());
		}
		return jToolBar;
	}

	/**
	 * This method initializes btnSearch
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSearch() {
		if (btnSearch == null) {
			btnSearch = new JButton();
			btnSearch.setText("单据查询");
			btnSearch.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					search();
				}
			});
		}
		return btnSearch;
	}

	/**
	 * This method initializes btnCorresponding
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCorresponding() {
		if (btnCorresponding == null) {
			btnCorresponding = new JButton();
			btnCorresponding.setText("单据对应");
			btnCorresponding
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							billCorresponding();
						}
					});
		}
		return btnCorresponding;
	}

	/**
	 * This method initializes btnCancelCorresponding
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancelCorresponding() {
		if (btnCancelCorresponding == null) {
			btnCancelCorresponding = new JButton();
			btnCancelCorresponding.setText("取消对应");
			btnCancelCorresponding.setEnabled(false);
			btnCancelCorresponding
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							cancelCorresponding();
						}
					});
		}
		return btnCancelCorresponding;
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
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnExit;
	}

	/**
	 * This method initializes cbbName
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbName() {

		// 商品名称
		if (cbbName == null) {
			cbbName = new JComboBox();
			cbbName.setUI(new CustomBaseComboBoxUI(270));
			cbbName.setEditable(true);
			cbbName.setBounds(new Rectangle(365, 49, 365, 22));
			cbbName.setBorder(javax.swing.BorderFactory.createLineBorder(
					new java.awt.Color(106, 135, 171), 1));
		}
		return cbbName;
	}

	/**
	 * This method initializes cbbScmCoc
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbScmCoc() {
		if (cbbScmCoc == null) {
			cbbScmCoc = new JComboBox();
			cbbScmCoc.setBounds(79, 75, 162, 22);
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
			rbProduct.setText("成品");
			rbProduct.setBounds(new Rectangle(14, 100, 55, 22));
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
			rbMateriel.setBounds(new Rectangle(69, 100, 55, 22));
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
			cbbImpExpType.setBounds(new Rectangle(79, 48, 163, 22));
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
			jSplitPane1.setDividerLocation(170);
			jSplitPane1.setDividerSize(3);
			jSplitPane1.setBottomComponent(getJTabbedPane());
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
			cbbBeginDate.setBounds(new Rectangle(365, 75, 123, 22));
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
			cbbEndDate.setBounds(new Rectangle(607, 75, 123, 22));
		}
		return cbbEndDate;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTb1() {
		if (tb1 == null) {

			tb1 = new JTable();

			tb1.getSelectionModel().addListSelectionListener(

			new ListSelectionListener() {

				public void valueChanged(ListSelectionEvent e) {

					if (e.getValueIsAdjusting()) {
						return;
					}

					JTableListModel tableModel = (JTableListModel) tb1
							.getModel();

					if (tableModel == null) {
						return;

					}
					try {

						if (tableModel.getCurrentRow() != null) {

							// 显示对应的数量信息
							showTipMessage();

						}

					} catch (Exception cx) {

					}
				}
			});

			tb1.addMouseListener(new java.awt.event.MouseAdapter() {

				public void mouseClicked(MouseEvent e) {

					if (e.getClickCount() >= 2) {

						DgBillMaster dgBillMaster = new DgBillMaster(
								FmBillCorresponding.this);

						JTableListModel model = (JTableListModel) tb1
								.getModel();

						BillMaster temp = ((BillDetail) model.getCurrentRow())
								.getBillMaster();

						BillType billType = temp.getBillType();

						dgBillMaster.setTableModel(model);

						dgBillMaster.setBillType(billType);

						dgBillMaster.setDataState(DataState.EDIT);

						dgBillMaster.setValidDate(temp.getValidDate());

						dgBillMaster.setVisible(true);

						//
						// 重新刷新本记录
						//
						NameSpecProperty nameSpec = (NameSpecProperty) cbbName
								.getSelectedItem();

						String nameSpecStr = "";

						if (nameSpec != null) {

							nameSpecStr = nameSpec.getNameSpec();
						}

						Integer impExpType = Integer
								.valueOf(((ItemProperty) cbbImpExpType
										.getSelectedItem()).getCode());

						boolean isNameSpec = false;// 判断是选择名称(true)还是名称+规格(false)查询

						if (rbName.isSelected()) {

							isNameSpec = true;

						} else {
							isNameSpec = false;

						}

						final List<BillDetail> billDetailList = casAction
								.findBillDetail(
										new Request(CommonVars.getCurrUser()),
										impExpType,
										(ScmCoc) cbbScmCoc.getSelectedItem(),
										cbbBeginDate.getDate(),
										cbbEndDate.getDate(), nameSpecStr,
										isNameSpec);

						// 过滤单据对应
						filterBillDetailList(billDetailList);

						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								initTb1(billDetailList);
							}
						});

					}
				}

				public void mousePressed(java.awt.event.MouseEvent e) {

					if (e.getModifiers() != InputEvent.BUTTON3_MASK) {// 左
						return;
					} // 右
					int[] columns = tb1.getSelectedColumns();
					int[] rows = tb1.getSelectedRows();
					int selectStartPointX = 0;
					int selectEndPointX = 0;
					boolean hasTableContextPopupMenu = true;
					if (columns.length > 0 && rows.length > 0) {

						for (int i = 0; i < columns[0]; i++) {
							selectStartPointX += tb1.getColumnModel()
									.getColumn(i).getWidth()
									+ tb1.getColumnModel().getColumnMargin();
						}
						for (int i = 0; i <= columns[columns.length - 1]; i++) {
							selectEndPointX += tb1.getColumnModel()
									.getColumn(i).getWidth()
									+ tb1.getColumnModel().getColumnMargin();
						}
						//
						// 列不变，行可以不是连续的
						//
						for (int j = rows[0]; j <= rows[rows.length - 1]; j++) {
							boolean isSelectedRow = false;
							for (int i = 0; i < rows.length; i++) {
								if (j == rows[i]) {
									isSelectedRow = true;
									break;
								}
							}
							if (isSelectedRow == false) {
								continue;
							}
							int selectStartPointY = 0;
							int selectEndPointY = 0;
							for (int i = 0; i <= j; i++) {
								if (i < j) {
									selectStartPointY += tb1.getRowHeight(i);
									// + tb1.getRowMargin();
								}
								if (i == j) {
									selectEndPointY += selectStartPointY
											+ tb1.getRowHeight(i);
									// + tb1.getRowMargin();
								}
							}
							if (e.getPoint().x >= selectStartPointX
									&& e.getPoint().x <= selectEndPointX
									&& e.getPoint().y >= selectStartPointY
									&& e.getPoint().y <= selectEndPointY) {
								isCopyMenuEnabled1 = true;
								getJPopupMenu1().show(tb1, e.getPoint().x,
										e.getPoint().y);
								hasTableContextPopupMenu = false;
								break;
							}
						}
						if (hasTableContextPopupMenu == true) {
							isCopyMenuEnabled1 = false;
							getJPopupMenu1().show(tb1, e.getPoint().x,
									e.getPoint().y);
							return;
						}
					}
				}
			});
		}
		return tb1;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTb1());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getTb3());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes tb2
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTb2() {
		if (tb2 == null) {
			tb2 = new JTable();
			tb2.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							JTableListModel tableModel = (JTableListModel) tb2
									.getModel();
							if (tableModel == null) {
								return;
							}
							try {
								if (tableModel.getCurrentRow() != null) {
									showTipMessage();
								}
							} catch (Exception cx) {

							}
						}
					});
			tb2.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mousePressed(java.awt.event.MouseEvent e) {

					if (e.getModifiers() != InputEvent.BUTTON3_MASK) {// 左
						return;
					} // 右
					int[] columns = tb2.getSelectedColumns();
					int[] rows = tb2.getSelectedRows();
					int selectStartPointX = 0;
					int selectEndPointX = 0;
					boolean hasTableContextPopupMenu = true;
					if (columns.length > 0 && rows.length > 0) {

						for (int i = 0; i < columns[0]; i++) {
							selectStartPointX += tb2.getColumnModel()
									.getColumn(i).getWidth()
									+ tb2.getColumnModel().getColumnMargin();// 单元格之间的宽度
						}
						for (int i = 0; i <= columns[columns.length - 1]; i++) {
							selectEndPointX += tb2.getColumnModel()
									.getColumn(i).getWidth()
									+ tb2.getColumnModel().getColumnMargin();
						}
						//
						// 列不变，行可以不是连续的
						//
						for (int j = rows[0]; j <= rows[rows.length - 1]; j++) {
							boolean isSelectedRow = false;
							for (int i = 0; i < rows.length; i++) {
								if (j == rows[i]) {
									isSelectedRow = true;
									break;
								}
							}
							if (isSelectedRow == false) {
								continue;
							}
							int selectStartPointY = 0;
							int selectEndPointY = 0;
							for (int i = 0; i <= j; i++) {
								if (i < j) {
									selectStartPointY += tb2.getRowHeight(i);
									// + tb1.getRowMargin();
								}
								if (i == j) {
									selectEndPointY += selectStartPointY
											+ tb2.getRowHeight(i);
									// + tb1.getRowMargin();
								}
							}
							if (e.getPoint().x >= selectStartPointX
									&& e.getPoint().x <= selectEndPointX
									&& e.getPoint().y >= selectStartPointY
									&& e.getPoint().y <= selectEndPointY) {
								isCopyMenuEnabled2 = true;
								getJPopupMenu2().show(tb2, e.getPoint().x,
										e.getPoint().y);
								hasTableContextPopupMenu = false;
								break;
							}
						}
						if (hasTableContextPopupMenu == true) {
							isCopyMenuEnabled2 = false;
							getJPopupMenu2().show(tb2, e.getPoint().x,
									e.getPoint().y);
							return;
						}
					}
				}
			});
		}
		return tb2;
	}

	/**
	 * This method initializes jScrollPane2
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setViewportView(getTb2());
		}
		return jScrollPane2;
	}

	/**
	 * This method initializes tb1
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTb3() {
		if (tb3 == null) {
			tb3 = new JTable();
			tb3.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mousePressed(java.awt.event.MouseEvent e) {
					if (e.getModifiers() != InputEvent.BUTTON3_MASK) {
						return;
					}
					int[] columns = tb3.getSelectedColumns();
					int[] rows = tb3.getSelectedRows();
					int selectStartPointX = 0;
					int selectEndPointX = 0;
					boolean isEnabled = true;

					if (columns.length > 0 && rows.length > 0) {

						for (int i = 0; i < columns[0]; i++) {
							selectStartPointX += tb3.getColumnModel()
									.getColumn(i).getWidth()
									+ tb3.getColumnModel().getColumnMargin();
						}
						for (int i = 0; i <= columns[columns.length - 1]; i++) {
							selectEndPointX += tb3.getColumnModel()
									.getColumn(i).getWidth()
									+ tb3.getColumnModel().getColumnMargin();
						}
						//
						// 列不变，行可以不是连续的
						//
						for (int j = rows[0]; j <= rows[rows.length - 1]; j++) {
							boolean isSelectedRow = false;
							for (int i = 0; i < rows.length; i++) {
								if (j == rows[i]) {
									isSelectedRow = true;
									break;
								}
							}
							if (isSelectedRow == false) {
								continue;
							}
							int selectStartPointY = 0;
							int selectEndPointY = 0;
							for (int i = 0; i < j; i++) {
								selectStartPointY += tb3.getRowHeight(i);
								// + tb3.getRowMargin();
							}
							for (int i = 0; i <= j; i++) {
								selectEndPointY += tb3.getRowHeight(i);
								// + tb3.getRowMargin();
							}
							if (e.getPoint().x >= selectStartPointX
									&& e.getPoint().x <= selectEndPointX
									&& e.getPoint().y >= selectStartPointY
									&& e.getPoint().y <= selectEndPointY) {
								isCopyMenuEnabled3 = true;
								getJPopupMenu3().show(tb3, e.getPoint().x,
										e.getPoint().y);
								isEnabled = false;
								break;
							}
						}
						if (isEnabled) {
							isCopyMenuEnabled3 = false;
							getJPopupMenu3().show(tb3, e.getPoint().x,
									e.getPoint().y);
						}
					}

				}
			});
		}
		return tb3;
	}

	/**
	 * This method initializes jPopupMenu
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getJPopupMenu1() {
		if (jPopupMenu1 == null) {
			jPopupMenu1 = new JPopupMenu();
		}
		if (tableModelBillDetail != null) {
			jPopupMenu1.removeAll();
			jPopupMenu1.add(getMiBillCorresponding());
			getMiBillCorresponding().setEnabled(isCopyMenuEnabled1);
			jPopupMenu1.addSeparator();
			jPopupMenu1.add(tableModelBillDetail.getMiCopy());
			jPopupMenu1.add(tableModelBillDetail.getMiSearch());
			jPopupMenu1.add(tableModelBillDetail.getMiSaveTableListToExcel());
			jPopupMenu1.add(tableModelBillDetail.getMiRenderColumn());
			tableModelBillDetail.getMiCopy().setEnabled(isCopyMenuEnabled1);
		}
		return jPopupMenu1;
	}

	private JPopupMenu getJPopupMenu2() {

		if (jPopupMenu2 == null) {

			jPopupMenu2 = new JPopupMenu();
		}

		// 右键菜单
		if (tableModelCustomsDeclarationCommInfo != null) {

			jPopupMenu2.removeAll();

			jPopupMenu2.add(getMiBillCorresponding());

			getMiBillCorresponding().setEnabled(isCopyMenuEnabled2);

			jPopupMenu2.addSeparator();

			jPopupMenu2.add(tableModelCustomsDeclarationCommInfo.getMiCopy());

			jPopupMenu2.add(tableModelCustomsDeclarationCommInfo.getMiSearch());

			jPopupMenu2.add(tableModelCustomsDeclarationCommInfo
					.getMiSaveTableListToExcel());

			jPopupMenu2.add(tableModelCustomsDeclarationCommInfo
					.getMiRenderColumn());

			tableModelCustomsDeclarationCommInfo.getMiCopy().setEnabled(
					isCopyMenuEnabled2);
		}
		return jPopupMenu2;
	}

	/**
	 * This method initializes jPopupMenu
	 * 
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu jPopupMenu3 = null;

	private JPopupMenu getJPopupMenu3() {
		if (jPopupMenu3 == null) {
			jPopupMenu3 = new JPopupMenu();
		}
		if (tableModelMakeBillCorrespondingInfo != null) {
			jPopupMenu3.removeAll();
			jPopupMenu3.add(getCancelCorresponding());
			getCancelCorresponding().setEnabled(isCopyMenuEnabled3);
			jPopupMenu3.addSeparator();
			jPopupMenu3.add(tableModelMakeBillCorrespondingInfo.getMiCopy());
			jPopupMenu3.add(tableModelMakeBillCorrespondingInfo.getMiSearch());
			jPopupMenu3.add(tableModelMakeBillCorrespondingInfo
					.getMiSaveTableListToExcel());
			tableModelMakeBillCorrespondingInfo.getMiCopy().setEnabled(
					isCopyMenuEnabled3);
		}
		return jPopupMenu3;
	}

	private JMenuItem miBillCorresponding = null;

	private JSplitPane jSplitPane1 = null;

	private JLabel jLabel31 = null;

	private JLabel jLabel = null;

	private JLabel jLabel32 = null;

	private JLabel jLabel33 = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel3 = null;

	private JLabel jLabel4 = null;

	private JLabel jLabel5 = null;

	private JLabel jLabel6 = null;

	private JLabel lbInfo = null;

	private JLabel lbPtAmount = null;

	private JLabel jLabel7 = null;

	private JRadioButton rbName = null;

	private JRadioButton rbNameSpec = null;

	private JMenuItem getMiBillCorresponding() {
		if (miBillCorresponding == null) {
			miBillCorresponding = new JMenuItem();
			miBillCorresponding.setText("单据对应");
			miBillCorresponding
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							billCorresponding();
						}
					});
		}
		return miBillCorresponding;
	}

	private JMenuItem getCancelCorresponding() {
		if (miCancelCorresponding == null) {
			miCancelCorresponding = new JMenuItem();
			miCancelCorresponding.setText("取消对应");
			miCancelCorresponding
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							cancelCorresponding();
						}
					});
		}
		return miCancelCorresponding;
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setDividerSize(2);
			jSplitPane.setLeftComponent(getJScrollPane());
			jSplitPane.setRightComponent(getJScrollPane2());
			jSplitPane.setDividerLocation(385);
		}
		return jSplitPane;
	}

	/**
	 * 初始化类型控件 || 成品 -- 料件
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

		// 初始化商品大类名称
		List<Object[]> list = casAction.findStatCusNameRelationName(
				new Request(CommonVars.getCurrUser(), true), materielType);

		this.cbbName.removeAllItems();

		if (rbName.isSelected()) {

			List<String> tempList = new ArrayList<String>();

			for (Object[] item : list) {

				String name = item[0] == null ? "" : (String) item[0];

				if (tempList.size() > 0) {

					if (!tempList.contains(name)) {

						tempList.add(name);

					}

				} else {

					tempList.add(name);
				}
			}
			for (Object item : tempList) {

				// 内部类 用于 渲染数据
				NameSpecProperty nameSpec;

				String name = item == null ? "" : (String) item;

				nameSpec = new NameSpecProperty(name);

				this.cbbName.addItem(nameSpec);
			}
		} else {

			for (Object[] item : list) {

				NameSpecProperty nameSpec;

				String name = item[0] == null ? "" : (String) item[0];

				String spec = item[1] == null ? "" : (String) item[1];

				nameSpec = new NameSpecProperty(name, spec);

				this.cbbName.addItem(nameSpec);
			}
		}

		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbName,
				"nameSpec", "nameSpec", 400);

		this.cbbName.setRenderer(CustomBaseRender.getInstance().getRender(
				"nameSpec", "nameSpec", 400, 0));

		if (this.rbMateriel.isSelected()) {

			// 查询 供应商 列表 ScmCoc
			List list2 = casAction.findManufacturer(new Request(CommonVars
					.getCurrUser(), true));

			this.cbbScmCoc.setModel(new DefaultComboBoxModel(list2.toArray()));

			this.cbbScmCoc.setRenderer(CustomBaseRender.getInstance()
					.getRender("code", "name", 65, 250));

			CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
					this.cbbScmCoc, "code", "name", 315);

		} else {

			// 查询 客户 列表 ScmCoc
			List list3 = casAction.findIsCustomer(new Request(CommonVars
					.getCurrUser(), true));

			this.cbbScmCoc.setModel(new DefaultComboBoxModel(list3.toArray()));

			this.cbbScmCoc.setRenderer(CustomBaseRender.getInstance()
					.getRender("code", "name", 65, 250));

			CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
					this.cbbScmCoc, "code", "name", 315);
		}

	}

	public class NameSpecProperty {
		private String nameSpec;

		private String spec;

		private String name;

		public NameSpecProperty(String name, String spec) {
			this.spec = spec;
			this.name = name;
		}

		public NameSpecProperty(String name) {
			this.name = name;
		}

		public String toString() {
			return name;
		}

		public String getSpec() {
			return spec;
		}

		public void setSpec(String code) {
			this.spec = code;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		/**
		 * @return the nameSpec
		 */
		public String getNameSpec() {
			String nameSpec = "";
			if (name != null)
				nameSpec = name;
			if (spec != null)
				nameSpec = nameSpec + "/" + spec;
			return nameSpec;
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
				"code", "name", 65, 250));

		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbScmCoc, "code", "name", 315);

		this.cbbScmCoc.setSelectedItem(null);

		//
		// 初始化类型
		//
		initBillTypeUI();

		//
		// 初始化日历控件
		//
		this.cbbBeginDate.setDate(null);

		this.cbbEndDate.setDate(null);

		// 左边列表1 -- 单据对应
		initTb1(new ArrayList<BillDetail>());

		// 右边列表2 -- 单据对应
		initTb2(new ArrayList());

		// 取消单据对应 列表
		initTb3(new ArrayList());
	}

	/**
	 * -- 单据查询 ---------
	 */
	private void search() {

		Date beginDate = this.cbbBeginDate.getDate();

		Date endDate = this.cbbEndDate.getDate();

		beginDate = CommonUtils.getBeginDate(beginDate);

		endDate = CommonUtils.getEndDate(endDate);

		// 判断起始日期 是否 大于 结束日期
		if (beginDate != null && endDate != null) {

			if (beginDate.after(endDate)) {
				JOptionPane.showMessageDialog(this, "开始日期大于结束日期!!", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
		}

		if (this.cbbImpExpType.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(this, "请选择要查询的单据类型!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		// 单据类型代码
		Integer impExpType = Integer.valueOf(((ItemProperty) this.cbbImpExpType
				.getSelectedItem()).getCode());

		// 转厂信息 提示
		if (ImpExpType.TRANSFER_FACTORY_IMPORT == impExpType
				&& ImpExpType.TRANSFER_FACTORY_EXPORT == impExpType) {

			isWarnEnvelopNo = true;
		} else {

			isWarnEnvelopNo = false;
		}

		// 单据对应
		if (this.jTabbedPane.getSelectedIndex() == 0) {

			if (this.cbbScmCoc.getSelectedItem() == null) {

				if (JOptionPane.showConfirmDialog(this,
						"如果不选择客户供应商,可能会出现对应不合规范!!\n确定要继续吗??", "警告!!!",
						JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) != JOptionPane.YES_OPTION) {
					return;
				}
			}

			// 检验数据的合法性
			if (vaildate() == false) {
				return;
			}

		} else if (this.jTabbedPane.getSelectedIndex() == 1) { // 取消对应
			// String name = (String) this.cbbName.getSelectedItem();
			if (this.cbbName.getSelectedItem() == null) {
				if (JOptionPane.showConfirmDialog(this,
						"名称查询条件为空,如果你的数据量大的话,可能造成服务器死机!!\n确定要继续吗??", "警告!!!",
						JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) != JOptionPane.YES_OPTION) {
					return;
				}
			}
		}

		new SearchThread(impExpType, beginDate, endDate).start();
	}

	/**
	 * 取消单据对应线程
	 * 
	 * @author ls
	 * 
	 */
	class SearchThread extends Thread {
		private int impExpType;

		private Date beginDate;

		private Date endDate;

		public SearchThread(int impExpType, Date beginDate, Date endDate) {
			this.impExpType = impExpType;
			this.beginDate = beginDate;
			this.endDate = endDate;
		}

		@Override
		public void run() {

			Map<String, Object> map = new HashMap<String, Object>();
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

					}
				};

				CommonProgress.showProgressDialog(flag,
						FmBillCorresponding.this, false, progressTask, 5000);

				CommonProgress.setMessage(flag, "系统正在获取数据，请稍后...");

				String name = "";

				String spec = "";

				String nameSpecStr = "";

				NameSpecProperty nameSpec = (NameSpecProperty) cbbName
						.getSelectedItem();

				if (nameSpec != null) {

					name = nameSpec.getName();

					spec = nameSpec.getSpec();

					nameSpecStr = nameSpec.getNameSpec();
				}

				// 单据对应
				if (jTabbedPane.getSelectedIndex() == 0) {

					if (rbName.isSelected()) {

						isNameSpec = true;
					} else {
						isNameSpec = false;
					}

					// 1.查找符合条件的单据体（符合条件的所有条）
					final List<BillDetail> billDetailList = casAction
							.findBillDetail(
									new Request(CommonVars.getCurrUser()),
									impExpType,
									(ScmCoc) cbbScmCoc.getSelectedItem(),
									beginDate, endDate, nameSpecStr, isNameSpec);

					// 依照设置的参数 进行 过滤
					filterBillDetailList(billDetailList);// 这里会把退货的 折算报关数量 改成负数

					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							initTb1(billDetailList);
						}
					});
					// 未对应的 报关单商品信息与海关帐单据的对应
					final List tempList = casAction
							.findCustomsDeclarationCommInfoBillCorresponding(
									new Request(CommonVars.getCurrUser()),
									impExpType,
									(ScmCoc) cbbScmCoc.getSelectedItem(),
									beginDate, endDate, name, spec, isNameSpec);

					for (int i = tempList.size() - 1; i >= 0; i--) {
						CustomsDeclarationCommInfoBillCorresponding item = (CustomsDeclarationCommInfoBillCorresponding) tempList
								.get(i);

						if (isShowBillCorrRecord
								&& item.getNoCorrespondingAmount() < noShowItemValue) {
							tempList.remove(i);
						}
					}
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							initTb2(tempList);
						}
					});

					System.out.println("tempList.size() = " + tempList.size());
					// initTb2(tempList);// 初始化报关单

				} else if (jTabbedPane.getSelectedIndex() == 1) { // 取消对应
					if (rbName.isSelected()) {
						isNameSpec = true;
					} else {
						isNameSpec = false;
					}
					// 生产单据对应的中间信息
					final List tempList = casAction
							.findMakeBillCorrespondingInfo(new Request(
									CommonVars.getCurrUser()), impExpType,
									(ScmCoc) cbbScmCoc.getSelectedItem(),
									beginDate, endDate, nameSpecStr, isNameSpec);
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							initTb3(tempList);
						}
					});
				}

				CommonProgress.closeProgressDialog(flag);
				info += " 任务完成,共用时:" + (System.currentTimeMillis() - beginTime)
						+ " 毫秒 ";
				JOptionPane.showMessageDialog(FmBillCorresponding.this, info,
						"提示", 2);

			} catch (Exception e) {
				e.printStackTrace();
				CommonProgress.closeProgressDialog(flag);
				JOptionPane.showMessageDialog(FmBillCorresponding.this,
						"系统正在获取数据失败！！！" + e.getMessage(), "提示", 2);
			}
			btnSearch.setEnabled(true);
		}

	}

	/*
	 * 过滤单据 如果是退货的话 那么 就显示成 负数
	 */
	private void filterBillDetailList(final List<BillDetail> billDetailList) {

		NameSpecProperty ns = (NameSpecProperty) cbbName.getSelectedItem();

		if (billDetailList != null && billDetailList.size() != 0) {

			for (int i = billDetailList.size() - 1; i >= 0; i--) {

				BillDetail item = billDetailList.get(i);

				item.setUnitConvert((item.getHsAmount() == null ? 0.0 : item
						.getHsAmount())
						/ (item.getPtAmount() == null ? 0.0 : item
								.getPtAmount()));

				String typeCode = item.getBillMaster().getBillType().getCode()
						.trim();

				// 1106结转料件退货单 2009结转成品退货单,1016已转厂为收货期初单,2012已结转未交货期初单
				if (typeCode.equals("1106") || typeCode.equals("2009")
						|| typeCode.equals("1016") || typeCode.equals("2012")) {

					// 工厂数量
					Double ptAmount = item.getPtAmount() == null ? Double
							.valueOf(0.0) : item.getPtAmount();

					// 折算数量
					double hsAmount = (item.getHsAmount() == null ? 0.0 : item
							.getHsAmount());

					// 在这里改成负数了（退的）
					item.setHsAmount(hsAmount == 0.0 ? 0.0 : -hsAmount);

					item.setPtAmount(ptAmount == 0.0 ? 0.0 : -ptAmount);

					if (isShowBillCorrRecord
							&& item.getNoCustomNum() > -noShowItemValue) {

						billDetailList.remove(i);
					}

				} else {
					if (isShowBillCorrRecord
							&& item.getNoCustomNum() < noShowItemValue) {

						billDetailList.remove(i);
					}
				}

				if (ns != null) {

					String commName;
					String commSpec;

					if (rbName.isSelected()) {

						commName = item.getHsName() == null ? "" : item
								.getHsName();
						commSpec = "";
					} else {

						commName = item.getHsName() == null ? "" : item
								.getHsName();
						commSpec = item.getHsSpec() == null ? "" : item
								.getHsSpec();
					}
					String nameSpec = ns == null ? ""
							: ((ns.name == null ? "" : ns.name) + "/" + (ns.spec == null ? ""
									: ns.spec));
					String matchStr = commName + "/" + commSpec;
					if (matchStr.equals(nameSpec)
							|| matchStr.equals(nameSpec + "/")
							|| matchStr.equals(nameSpec + "//")) {
						continue;
					} else {
						billDetailList.remove(i);
					}
				}
			}
		}
	}

	/**
	 * 初始化表1 装的是单据体（BillDetail）
	 */
	private void initTb1(List<BillDetail> list) {
		tableModelBillDetail = new JTableListModel(tb1, list,
				new JTableListModelAdapter(maximumFractionDigits) {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("单据号", "billMaster.billNo", 100));
						list.add(addColumn("单据类型", "billMaster.billType.name",
								60));
						list.add(addColumn("工厂数量", "ptAmount", 60));
						list.add(addColumn("折算报关数量", "hsAmount", 120));
						list.add(addColumn("已对应报关数量", "customNum", 120));
						list.add(addColumn("未对应报关数量", "noCustomNum", 120));
						list.add(addColumn("报关单价", "hsPrice", 80));
						list.add(addColumn("生效时间", "billMaster.validDate", 100));
						list.add(addColumn("商品料号", "ptPart", 100));
						list.add(addColumn("商品名称", "ptName", 100));
						list.add(addColumn("商品规格", "ptSpec", 100));
						list.add(addColumn("商品单位", "ptUnit.name", 100));
						list.add(addColumn("商品版本", "version", 100));
						list.add(addColumn("制单号", "productNo", 80));
						list.add(addColumn("商品海关编码", "complex.code", 120));
						list.add(addColumn("报关商品名称", "hsName", 120));
						list.add(addColumn("报关商品规格 ", "hsSpec", 120));
						list.add(addColumn("折算报关系数", "unitConvert", 120));

						// wss2010.09.20添加关封号
						if (cbbImpExpType != null
								&& cbbImpExpType.getSelectedItem() != null) {
							String s = ((ItemProperty) cbbImpExpType
									.getSelectedItem()).getCode();
							if (s.equals(Integer.valueOf(
									ImpExpType.TRANSFER_FACTORY_EXPORT)
									.toString())
									|| s.equals(Integer.valueOf(
											ImpExpType.TRANSFER_FACTORY_IMPORT)
											.toString())) {
								list.add(addColumn("关封号",
										"billMaster.envelopNo", 150));

							}
						}

						return list;
					}
				});

		deleteCommonTableContextPopupEventMouseListener(tb1);

		tb1.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

	}

	/**
	 * 初始化表2 装的是 报关单商品信息与海关帐单据的对应 （CustomsDeclarationCommInfoBillCorresponding）
	 */
	private void initTb2(List list) {
		tableModelCustomsDeclarationCommInfo = new JTableListModel(tb2, list,
				new JTableListModelAdapter(maximumFractionDigits) {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("报关单号", "customsDeclarationCode",
								100));
						list.add(addColumn("电子帐册号码/合同手册号", "emsHeadH2k", 150));
						list.add(addColumn("单据类型", "impExpType", 60));
						list.add(addColumn("报关数量", "commAmount", 60));
						// list.add(addColumn("单位折算比率", "unitConvert", 100));
						list.add(addColumn("已对应报关数量(折算后)",
								"alreadyCorrespondingAmount", 100));
						list.add(addColumn("未对应报关数量(折算后)",
								"noCorrespondingAmount", 100));
						// list.add(addColumn("未对应报关数量(test)",
						// "noCorrespondingAmount2", 100));
						list.add(addColumn("客户名称", "scmCoc.name", 80));
						list.add(addColumn("进出口日期", "impExpDate", 120));
						list.add(addColumn("流水号", "serialNumber", 60));
						list.add(addColumn("商品序号", "commSerialNo", 80));
						list.add(addColumn("商品编号", "complex.code", 80));
						list.add(addColumn("商品名称", "commName", 100));
						list.add(addColumn("型号规格", "commSpec", 100));

						list.add(addColumn("单位", "unit.name", 60));
						list.add(addColumn("法定数量", "legalAmount", 80));
						list.add(addColumn("法定单位", "legalUnit.name", 80));
						list.add(addColumn("第二法定数量", "secondLegalAmount", 100));
						list.add(addColumn("第二法定单位", "secondLegalUnit.name",
								100));
						list.add(addColumn("版本号", "version", 50));

						// wss2010.09.20添加关封号
						if (cbbImpExpType != null
								&& cbbImpExpType.getSelectedItem() != null) {
							String s = ((ItemProperty) cbbImpExpType
									.getSelectedItem()).getCode();
							if (s.equals(Integer.valueOf(
									ImpExpType.TRANSFER_FACTORY_EXPORT)
									.toString())
									|| s.equals(Integer.valueOf(
											ImpExpType.TRANSFER_FACTORY_IMPORT)
											.toString())) {
								list.add(addColumn("关封号", "envelopNo", 150));

							}
						}

						return list;
					}
				});
		tb2.getColumnModel().getColumn(3)
				.setCellRenderer(new tableCellRender());
		deleteCommonTableContextPopupEventMouseListener(tb2);
		tb2.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	}

	/**
	 * 初始化表3 生产单据对应的中间信息 （MakeBillCorrespondingInfoBase）
	 */
	private void initTb3(List list) {
		tableModelMakeBillCorrespondingInfo = new JTableListModel(tb3, list,
				new JTableListModelAdapter(maximumFractionDigits) {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("单据号", "billNo", 100));
						list.add(addColumn("报关单号", "customsDeclarationCode",
								100));
						list.add(addColumn("单据类型", "billTypeName", 100));
						list.add(addColumn("进出口类型", "impExpType", 120));
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
		tb3.getColumnModel().getColumn(4)
				.setCellRenderer(new tableCellRender());
		deleteCommonTableContextPopupEventMouseListener(tb3);
		tb3.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
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
	 * 取消对应
	 * 
	 */
	private void cancelCorresponding() {
		if (tableModelMakeBillCorrespondingInfo.getCurrentRow() == null) {
			JOptionPane.showMessageDialog(this, "请选择要取消的记录项!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		new CancelCorrespondingThread().start();
	}

	/**
	 * 取消单据对应线程
	 * 
	 * @author ls
	 * 
	 */
	class CancelCorrespondingThread extends Thread {
		public void run() {
			//
			// 用于标识这个对话框的ID
			//
			UUID uuid = UUID.randomUUID();
			final String flag = uuid.toString();
			try {
				getCancelCorresponding().setEnabled(false);
				btnCancelCorresponding.setEnabled(false);

				String info = "";
				long beginTime = System.currentTimeMillis();
				ProgressTask progressTask = new ProgressTask() {
					protected void setClientTipMessage() {

					}
				};
				CommonProgress.showProgressDialog(flag,
						FmBillCorresponding.this, false, progressTask, 5000);
				CommonProgress.setMessage(flag, "系统正在取消单据对应，请稍后...");

				List<MakeBillCorrespondingInfoBase> list = tableModelMakeBillCorrespondingInfo
						.getCurrentRows();
				casAction.cancelCorresponding(
						new Request(CommonVars.getCurrUser()), list);
				tableModelMakeBillCorrespondingInfo.deleteRows(list);

				CommonProgress.closeProgressDialog(flag);
				info += " 取消单据对应任务完成,共用时:"
						+ (System.currentTimeMillis() - beginTime) + " 毫秒 ";
				JOptionPane.showMessageDialog(FmBillCorresponding.this, info,
						"提示", 2);

			} catch (Exception e) {
				e.printStackTrace();
				CommonProgress.closeProgressDialog(flag);
				JOptionPane.showMessageDialog(FmBillCorresponding.this,
						"取消单据对应失败！！！" + e.getMessage(), "提示", 2);
			}
			getCancelCorresponding().setEnabled(true);
			btnCancelCorresponding.setEnabled(true);
		}
	}

	/**
	 * 单据对应
	 * 
	 */
	private void billCorresponding() {

		// 选取的单据体
		List<BillDetail> listBill = tableModelBillDetail.getCurrentRows();
		if (listBill.isEmpty()) {
			JOptionPane.showMessageDialog(this, "请选择要对应单据信息!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		// 第二个表的信息
		List<CustomsDeclarationCommInfoBillCorresponding> listC = tableModelCustomsDeclarationCommInfo
				.getCurrentRows();
		Map<String, CustomsDeclarationCommInfoBillCorresponding> mapC = new HashMap<String, CustomsDeclarationCommInfoBillCorresponding>();

		if (listC.isEmpty()) {
			JOptionPane.showMessageDialog(this, "请选择要对应的报关商品信息!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		// wss2010.09.20
		if (isWarnEnvelopNo) {
			for (int i = 0; i < listBill.size(); i++) {
				BillDetail bill = listBill.get(i);

				String commEnvelopNo = "";

				for (int j = 0; j < listC.size(); j++) {
					commEnvelopNo = listC.get(j).getEnvelopNo() == null ? ""
							: listC.get(j).getEnvelopNo();

					if (!commEnvelopNo.equals(bill.getBillMaster()
							.getEnvelopNo())) {
						if (JOptionPane.showConfirmDialog(this,
								"单据中的关封号与报关单中的关封号不符" + " \n" + "\n 是否继续？",
								"提示", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
							return;
						}
					}
				}
			}
		}

		// 单据参数设置
		if (b.getIsBillCorrespondingAffirm()) {// 如果单据对应须确认

			String customsDeclarationCode = "";

			for (int i = 0; i < listC.size(); i++) {

				CustomsDeclarationCommInfoBillCorresponding c = listC.get(i);

				// key为：报关单编号 + 报关商品编号
				String key = c.getCustomsDeclarationId()
						+ c.getCustomsDeclarationCommInfoId();

				mapC.put(key, c);

				int oldSplit = customsDeclarationCode.length() % 350;

				if (i == listC.size() - 1) {
					customsDeclarationCode += c.getCustomsDeclarationCode();
				} else {
					customsDeclarationCode += c.getCustomsDeclarationCode()
							+ ",";
				}
				int newSplit = customsDeclarationCode.length() % 350;
				if (oldSplit < newSplit) {
					customsDeclarationCode += "\n";
				}
			}
			String message = "";
			if (listC.size() > 7)
				message = "\n建议一条单据对应报关单数不要超过7张，否则单据表体的 “对应报关单号”栏位会截取前7张!是否继续执行？";
			if (JOptionPane.showConfirmDialog(this, "是否确定单据选中的记录与" + " \n"
					+ customsDeclarationCode + "\n 号报关单生成对应!" + message, "提示",
					JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
				return;
			}
		} else {
			for (int i = 0; i < listC.size(); i++) {
				CustomsDeclarationCommInfoBillCorresponding c = listC.get(i);
				// key为：报关单编号 + 报关商品编号
				String key = c.getCustomsDeclarationId()
						+ c.getCustomsDeclarationCommInfoId();
				mapC.put(key, c);// value为
									// CustomsDeclarationCommInfoBillCorresponding
			}
		}
		new BillCorrespondingThread(listC, mapC, listBill).start();

	}

	/**
	 * 单据对应线程
	 * 
	 * @author ls
	 * 
	 */
	class BillCorrespondingThread extends Thread {

		List<CustomsDeclarationCommInfoBillCorresponding> listC = null;

		Map<String, CustomsDeclarationCommInfoBillCorresponding> mapC = null;

		List<BillDetail> listBill = null;

		public BillCorrespondingThread(
				List<CustomsDeclarationCommInfoBillCorresponding> listC,
				Map<String, CustomsDeclarationCommInfoBillCorresponding> mapC,
				List<BillDetail> listBill) {

			this.listC = listC;
			this.mapC = mapC;
			this.listBill = listBill;
		}

		public void run() {

			// 用于标识这个对话框的ID
			UUID uuid = UUID.randomUUID();
			final String flag = uuid.toString();

			try {
				getMiBillCorresponding().setEnabled(false);
				btnCorresponding.setEnabled(false);
				String info = "";
				long beginTime = System.currentTimeMillis();
				ProgressTask progressTask = new ProgressTask() {
					protected void setClientTipMessage() {

					}
				};
				CommonProgress.showProgressDialog(flag,
						FmBillCorresponding.this, false, progressTask, 5000);
				CommonProgress.setMessage(flag, "系统正在进行单据对应，请稍后...");

				// 中间信息
				TempResult tempResult = casAction.billCorresponding(
						new Request(CommonVars.getCurrUser()), listC, listBill);

				// 修改单据与报关单对应中间表
				List<CustomsDeclarationCommInfoBillCorresponding> lsC = tempResult
						.getC();

				List<BillDetail> lsBillDetails = tempResult.getBillDetail();

				for (int i = 0; i < lsC.size(); i++) {
					// getAlreadyCorrespondingAmount:已对应报关数量
					CustomsDeclarationCommInfoBillCorresponding tempC = lsC
							.get(i);
					String key = tempC.getCustomsDeclarationId()
							+ tempC.getCustomsDeclarationCommInfoId();
					// System.out.println(tempC.getCustomsDeclarationId()+"/"
					// + tempC.getCustomsDeclarationCommInfoId());

					Double alreadyCorrespondingAmount = getDigi(
							tempC.getAlreadyCorrespondingAmount(), 8);

					Double commAmount = getDigi(tempC.getCommAmount(), 8);

					System.out
							.println("tempC.getAlreadyCorrespondingAmount() == "
									+ alreadyCorrespondingAmount);
					System.out
							.println("tempC.getCommAmount() == " + commAmount);

					// 如果已对应报关数量 > 报关数量
					if (alreadyCorrespondingAmount >= commAmount
							|| (isShowBillCorrRecord && tempC
									.getNoCorrespondingAmount() < noShowItemValue)) {
						// tempC = mapC.get(key);
						tableModelCustomsDeclarationCommInfo.deleteRow(mapC
								.get(key));

						System.out.println("delete ok == "
								+ tempC.getCommName());
					}
					// 更新
					else {
						CustomsDeclarationCommInfoBillCorresponding temp = mapC
								.get(key);
						try {
							PropertyUtils.copyProperties(temp, tempC);
							tableModelCustomsDeclarationCommInfo
									.updateRow(temp);

							System.out.println("update ok == "
									+ tempC.getCommName());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}

				Map<String, BillDetail> map = new HashMap<String, BillDetail>();

				for (int i = 0; i < listBill.size(); i++) {

					BillDetail temp = listBill.get(i);

					map.put(temp.getId(), temp);
				}

				for (int i = 0; i < lsBillDetails.size(); i++) {

					BillDetail tempBillDetail1 = lsBillDetails.get(i);

					// 如果 已对应报关数量 > 折算报关数量
					if (tempBillDetail1.getCustomNum() >= tempBillDetail1
							.getHsAmount()
							|| (isShowBillCorrRecord && tempBillDetail1
									.getNoCustomNum() < noShowItemValue)) {

						tempBillDetail1 = map.get(tempBillDetail1.getId());

						tableModelBillDetail.deleteRow(tempBillDetail1);

					}
					// 更新行
					else {
						BillDetail temp = map.get(tempBillDetail1.getId());
						try {
							PropertyUtils.copyProperties(temp, tempBillDetail1);
							tableModelBillDetail.updateRow(temp);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}

				CommonProgress.closeProgressDialog(flag);
				info += " 单据对应任务完成,共用时:"
						+ (System.currentTimeMillis() - beginTime) + " 毫秒 ";
				JOptionPane.showMessageDialog(FmBillCorresponding.this, info,
						"提示", 2);

			} catch (Exception e) {
				e.printStackTrace();
				CommonProgress.closeProgressDialog(flag);
				JOptionPane.showMessageDialog(FmBillCorresponding.this,
						"单据对应失败！！！" + e.getMessage(), "提示", 2);
			}
			getMiBillCorresponding().setEnabled(true);
			btnCorresponding.setEnabled(true);
		}
	}

	/**
	 * 显示对应提示信息
	 * 
	 */
	private void showTipMessage() {

		double billDetailAmount = 0.0;

		double customsAmount = 0.0;

		// 工厂数量
		double ptAmount = 0.0;

		List<BillDetail> list = tableModelBillDetail.getCurrentRows();

		for (int i = 0; i < list.size(); i++) {

			BillDetail temp = list.get(i);

			billDetailAmount += temp.getNoCustomNum();

			ptAmount += temp.getPtAmount();
		}

		//
		// 建义从数据库里来解决之根本,不建义在这里去解决问题.
		//
		billDetailAmount = getDigi(billDetailAmount, 8);

		List<CustomsDeclarationCommInfoBillCorresponding> listC = tableModelCustomsDeclarationCommInfo
				.getCurrentRows();

		for (int i = 0; i < listC.size(); i++) {

			CustomsDeclarationCommInfoBillCorresponding c = listC.get(i);

			customsAmount += c.getNoCorrespondingAmount();

		}

		customsAmount = getDigi(customsAmount, 8);

		this.lbHsAmount.setText("  选中单据未对应总数量   [ " + billDetailAmount + " ]");

		this.lbPtAmount.setText("  选中单据工厂数量   [ " + ptAmount + " ]");

		this.lbCustomAmount.setText("  选中报关单未对应总数量 [ " + customsAmount + " ]");

	}

	/**
	 * 取消对应 根据所选择的面板 判断哪些组件需要启动或关闭
	 */
	private void setState() {

		if (this.jTabbedPane.getSelectedIndex() == 0) {

			this.btnCancelCorresponding.setEnabled(false);
			this.btnCorresponding.setEnabled(true);
			this.btnSearch.setEnabled(true);
			this.lbHsAmount.setEnabled(true);
			this.lbCustomAmount.setEnabled(true);
			this.lbPtAmount.setEnabled(true);

		} else if (this.jTabbedPane.getSelectedIndex() == 1) {

			this.btnCancelCorresponding.setEnabled(true);
			this.btnCorresponding.setEnabled(false);
			this.btnSearch.setEnabled(true);
			this.lbHsAmount.setEnabled(false);
			this.lbCustomAmount.setEnabled(false);
			this.lbPtAmount.setEnabled(true);

		} else if (this.jTabbedPane.getSelectedIndex() == 2) {

			this.btnCancelCorresponding.setEnabled(false);
			this.btnCorresponding.setEnabled(false);
			this.btnSearch.setEnabled(false);
			this.lbHsAmount.setEnabled(false);
			this.lbCustomAmount.setEnabled(false);
			this.lbPtAmount.setEnabled(true);
		}
	}

	/**
	 * 删除
	 * 
	 * @param table
	 */
	private void deleteCommonTableContextPopupEventMouseListener(JTable table) {

		MouseListener[] mouseListeners = table.getMouseListeners();

		for (int i = 0; i < mouseListeners.length; i++) {
			if (mouseListeners[i] instanceof CommonTableContextPopupEvent) {
				table.removeMouseListener(mouseListeners[i]);
			}
		}
	}

	/**
	 * 显示单据对应选项
	 * 
	 */
	private void showBillCorrespondingOption() {
		if (b != null) {
			this.cbIsBillCorrespondingAffirm.setSelected(b
					.getIsBillCorrespondingAffirm() == null ? false : b
					.getIsBillCorrespondingAffirm());
		}

	}

	/**
	 * 填充单据对应选项
	 * 
	 */
	private void fillBillCorrespondingOption() {
		if (b != null) {
			b.setIsBillCorrespondingAffirm(this.cbIsBillCorrespondingAffirm
					.isSelected());
		}

	}

	/**
	 * 保存设置
	 * 
	 */
	private void saveBillCorrespondingOption() {
		fillBillCorrespondingOption();
		b = casAction.saveBillCorrespondingOption(
				new Request(CommonVars.getCurrUser()), b);
		setStatePn3(DataState.BROWSE);
	}

	/**
	 * 设置面板三的状态
	 * 
	 * @param dataState
	 */
	private void setStatePn3(int dataState) {

		this.cbIsBillCorrespondingAffirm
				.setEnabled(dataState != DataState.BROWSE);

	}

	/**
	 * 验证对应情况
	 * 
	 * @return
	 */
	private boolean vaildate() {// 为 False 返回

		BillCorrespondingControl billControl = CasCommonVars
				.getBillCorrespondingControl();

		// 系统自动控制
		if (billControl.getIsSystemControl() == true) {

			if (this.cbbName.getSelectedItem() == null) {
				JOptionPane.showMessageDialog(this, "当你选择的单据对应是自动对应时,名称不可为空!!",
						"提示", JOptionPane.INFORMATION_MESSAGE);
				return false;
			}

			// 特殊控制
		} else if (billControl.getIsSpecialControl() == true) {
			// 所有控制代码写在服务器端
		}
		return true;
	}

	private Double getDigi(Double d, int num) {
		BigDecimal b = new BigDecimal(d);
		return b.setScale(num, BigDecimal.ROUND_HALF_DOWN).doubleValue();
	}

	/**
	 * This method initializes rbName
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbName() {
		if (rbName == null) {
			rbName = new JRadioButton();
			rbName.setBounds(new Rectangle(396, 100, 61, 21));
			rbName.setFont(new Font("Dialog", Font.PLAIN, 12));
			rbName.setForeground(Color.red);
			rbName.setSelected(true);
			rbName.setText("名称");
			rbName.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					// 初始化 类型与报关名称 控件
					initBillTypeUI();

				}
			});
		}
		return rbName;
	}

	/**
	 * This method initializes rbNameSpec
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbNameSpec() {
		if (rbNameSpec == null) {
			rbNameSpec = new JRadioButton();
			rbNameSpec.setBounds(new Rectangle(459, 100, 104, 21));
			rbNameSpec.setFont(new Font("Dialog", Font.PLAIN, 12));
			rbNameSpec.setForeground(Color.red);
			rbNameSpec.setText("名称+规格");
			rbNameSpec.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					// 初始化 类型与报关名称 控件
					initBillTypeUI();

				}
			});
		}
		return rbNameSpec;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
