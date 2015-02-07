package com.bestway.dzsc.client.impexprequest;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import bsh.This;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.bcus.custombase.entity.parametercode.Transf;
import com.bestway.bcus.dataimport.entity.ClassList;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.enc.entity.ImpExpRequestBill;
import com.bestway.bcus.enc.entity.MakeCusomsDeclarationParam;
import com.bestway.bcus.enc.entity.TempImpExpCommodityInfo;
import com.bestway.bcus.enc.entity.TempImpExpRequestBill;
import com.bestway.client.custom.common.EncCommon;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.constant.DzscListType;
import com.bestway.common.constant.DzscState;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ImpExpType;
import com.bestway.dzsc.client.impexprequest.PnDzscMakeCustomsList3.CheckBoxEditor;
import com.bestway.dzsc.client.impexprequest.PnDzscMakeCustomsList3.ColorTableCellRenderer;
import com.bestway.dzsc.client.impexprequest.PnDzscMakeCustomsList3.MultiRenderer;
import com.bestway.dzsc.customslist.action.DzscListAction;
import com.bestway.dzsc.customslist.entity.DzscCustomsBillList;
import com.bestway.dzsc.dzscmanage.action.DzscAction;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.ui.winuicontrol.JDialogBase;

import java.awt.Rectangle;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Point;

public class DgDzscMakeCustomsListNew extends JDialogBase {
	private DzscEmsPorHead dzscEmsPorHead = null; // @jve:decl-index=0:

	private JTableListModel tableMode = null;

	private DzscCustomsBillList applyToCustomsBillList = null;

	private ButtonGroup buttonGroup = null;

	private ButtonGroup buttonGroup1 = null;

	private boolean isImportGoods = true;

	private int billType = -1;

	private List commodityList = null;

	private List colorList = null; // @jve:decl-index=0:

	private ImpExpRequestBill impExpRequestBill = null;

//	private int type = -1;

	protected DzscAction dzscAction = null;

	private JTableListModel tableModel = null;

	private EncAction encAction = null;

	private JSplitPane jSplitPane = null;

	private JScrollPane jScrollPane = null;

	private JSplitPane jSplitPane1 = null;

	private JPanel jPanel = null;

	private JRadioButton jRadioButton = null;

	private JRadioButton jRadioButton1 = null;

	private JRadioButton jRadioButton2 = null;

	private JRadioButton jRadioButton3 = null;

	private JComboBox cbbImpExpType = null;

	private JComboBox cbbContract = null;

	private JComboBox cbbCustoms = null;

	private JComboBox cbbImpExpPort = null;

	private JComboBox cbbListType = null;

	private JComboBox cbbTransportMode = null;

	private JComboBox cbbTradeMode = null;

	private JTextField jTextField = null;

	private JButton btnEnterpriseBillNo = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel3 = null;

	private JLabel jLabel4 = null;

	private JLabel jLabel5 = null;

	private JLabel jLabel6 = null;

	private JLabel jLabel7 = null;

	private JLabel jLabel8 = null;

	private JTextField jTextField1 = null;

	// private boolean isNewBgd = false;

	// private int step = 0;
	private List list = null;

	private boolean isexe = false;

	private int impExpType = -1;

	private int impExpTypeIxp = -1;

	private int materielProductType = -1;

	private JPanel jPanel1 = null;

	private JPanel jPanel2 = null;

	private JToolBar jJToolBarBar = null;

	private JToolBar jJToolBarBar1 = null;

	private DzscListAction dzscListAction = null;

	private List parentList = null;

	private JTableListModel tableModel1 = null;

	private JButton btnSelectAll = null;

	private JButton btnNoSelectAll = null;

	private JButton jButton2 = null;

	private JButton jButton1 = null;

	private JButton btnDone = null;

	private JButton btnExit = null;

	private JScrollPane jScrollPane1 = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane2 = null;

	private JTable jTable1 = null;

	private JPanel jPanel3 = null;

	private JPanel jPanel4 = null;

	private JCheckBox jCheckBox = null;

	/**
	 * @param owner
	 */
	public DgDzscMakeCustomsListNew() {
		super();
		initialize();
		this.encAction = (EncAction) CommonVars.getApplicationContext()
				.getBean("encAction");
		this.dzscAction = (DzscAction) CommonVars.getApplicationContext()
				.getBean("dzscAction");

		this.dzscListAction = (DzscListAction) CommonVars
				.getApplicationContext().getBean("dzscListAction");
		List list = dzscAction.findDzscEmsPorHeadExcu(new Request(CommonVars
				.getCurrUser(), true));

		this.cbbContract.removeAllItems();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				this.cbbContract.addItem((DzscEmsPorHead) list.get(i));
			}
			this.cbbContract.setRenderer(CustomBaseRender.getInstance()
					.getRender("", "emsNo", 0, 200));
		}
		if (this.cbbContract.getItemCount() > 0) {
			this.cbbContract.setSelectedIndex(0);
			dzscEmsPorHead = (DzscEmsPorHead) (list.get(0));
		} else {
			dzscEmsPorHead = null;
		}
		getButtonGroup();
		getButtonGroup1();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	public void initialize() {
		this.setTitle("进出口申请单转---报关清单");
		this.setSize(807, 573);
		this.setContentPane(getJSplitPane());
	}

	public void setImpExpType(int impExpType) {
		this.impExpType = impExpType;
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOneTouchExpandable(true);
			jSplitPane.setDividerLocation(190);
			jSplitPane
					.setComponentOrientation(java.awt.ComponentOrientation.UNKNOWN);
			jSplitPane.setForeground(new java.awt.Color(51, 51, 51));
			jSplitPane.setDividerSize(6);
			jSplitPane.setTopComponent(getJScrollPane());
			jSplitPane.setBottomComponent(getJSplitPane1());
			jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJPanel());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jSplitPane1
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane1() {
		if (jSplitPane1 == null) {
			jSplitPane1 = new JSplitPane();
			jSplitPane1.setDividerLocation(150);
			jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane1.setDividerSize(6);
			jSplitPane1.setTopComponent(getJPanel1());
			jSplitPane1.setBottomComponent(getJPanel2());
			jSplitPane1.setPreferredSize(new java.awt.Dimension(445, 116));
		}
		return jSplitPane1;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel8 = new JLabel();
			jLabel8.setBounds(new Rectangle(26, 138, 80, 25));
			jLabel8
					.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			jLabel8.setText("备注");
			jLabel7 = new JLabel();
			jLabel7.setBounds(new Rectangle(26, 110, 80, 25));
			jLabel7
					.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			jLabel7.setText("贸易方式");
			jLabel6 = new JLabel();
			jLabel6.setBounds(new Rectangle(302, 110, 63, 25));
			jLabel6
					.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			jLabel6.setText("清单类型");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(553, 82, 68, 25));
			jLabel5
					.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			jLabel5.setText("进出口岸");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(552, 54, 68, 25));
			jLabel4
					.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			jLabel4.setText("申报地海关");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(301, 54, 63, 25));
			jLabel3
					.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			jLabel3.setText("手册编号");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(302, 82, 63, 25));
			jLabel2
					.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			jLabel2.setText("运输方式");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(26, 82, 80, 25));
			jLabel1
					.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			jLabel1.setText("企业清单编号");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(26, 54, 80, 25));
			jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			jLabel.setText("进出口类型");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(getCbbImpExpType(), null);
			jPanel.add(getCbbContract(), null);
			jPanel.add(getCbbCustoms(), null);
			jPanel.add(getCbbImpExpPort(), null);
			jPanel.add(getCbbListType(), null);
			jPanel.add(getCbbTransportMode(), null);
			jPanel.add(getJTextField(), null);
			jPanel.add(getBtnEnterpriseBillNo(), null);
			jPanel.add(jLabel, null);
			jPanel.add(jLabel1, null);
			jPanel.add(jLabel2, null);
			jPanel.add(jLabel3, null);
			jPanel.add(jLabel4, null);
			jPanel.add(jLabel5, null);
			jPanel.add(jLabel6, null);
			jPanel.add(jLabel7, null);
			jPanel.add(jLabel8, null);
			jPanel.add(getJTextField1(), null);
			jPanel.add(getCbbTradeMode(), null);
			jPanel.add(getJPanel3(), null);
			jPanel.add(getJPanel4(), null);
			jPanel.add(getJCheckBox(), null);
		}
		return jPanel;
	}

	public List getParentList() {
		if (this.tableModel == null) {
			return null;
		}
		List list = tableModel.getList();
		List newList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) instanceof TempImpExpRequestBill) {
				TempImpExpRequestBill t = (TempImpExpRequestBill) list.get(i);
				if (t.getIsSelected().booleanValue() == true) {
					newList.add(t);
				}
			}
		}
		return newList;
	}

	public void setParentList(List parentList) {
		this.parentList = parentList;
	}

	private void selectAll(boolean isSelected) {
		if (this.tableModel == null) {
			return;
		}
		List list = tableModel.getList();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) instanceof TempImpExpRequestBill) {
				TempImpExpRequestBill t = (TempImpExpRequestBill) list.get(i);
				t.setIsSelected(new Boolean(isSelected));
			}
		}
		tableModel.updateRows(list);
		initTable1(getParentList());
	}

	private void selectAll1(boolean isSelected) {
		if (this.tableModel1 == null) {
			return;
		}
		List list = tableModel1.getList();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) instanceof TempImpExpCommodityInfo) {
				TempImpExpCommodityInfo temp = (TempImpExpCommodityInfo) list
						.get(i);
				temp.setIsSelected(new Boolean(isSelected));
			}
		}
		tableModel1.updateRows(list);
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton() {
		if (jRadioButton == null) {
			jRadioButton = new JRadioButton();
			jRadioButton.setFont(new java.awt.Font("Dialog",
					java.awt.Font.BOLD, 12));
			jRadioButton.setBounds(new Rectangle(46, 3, 140, 29));
			jRadioButton.setText("生成新的报关清单");
			jRadioButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setState(true);
				}
			});
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
			jRadioButton1.setText("追加到现在的报关清单");
			jRadioButton1.setEnabled(true);
			jRadioButton1.setPreferredSize(new java.awt.Dimension(155, 26));
			jRadioButton1.setBounds(new Rectangle(186, 3, 156, 29));
			jRadioButton1.setFont(new java.awt.Font("Dialog",
					java.awt.Font.BOLD, 12));
			jRadioButton1
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							setState(false);
						}
					});
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
			jRadioButton2.setText("进口");
			jRadioButton2.setBounds(new Rectangle(60, 3, 51, 26));
			jRadioButton2.setFont(new java.awt.Font("Dialog",
					java.awt.Font.BOLD, 12));
			jRadioButton2
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							isImportGoods = true;
							initCbbComponents();
						}
					});
		}
		return jRadioButton2;
	}

	/**
	 * This method initializes jRadioButton3
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton3() {
		if (jRadioButton3 == null) {
			jRadioButton3 = new JRadioButton();
			jRadioButton3.setText("出口");
			jRadioButton3.setBounds(new Rectangle(190, 4, 51, 26));
			jRadioButton3.setFont(new java.awt.Font("Dialog",
					java.awt.Font.BOLD, 12));
			jRadioButton3
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							isImportGoods = false;
							initCbbComponents();
						}
					});
		}
		return jRadioButton3;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbImpExpType() {
		if (cbbImpExpType == null) {
			cbbImpExpType = new JComboBox();
			cbbImpExpType.setBounds(new Rectangle(106, 54, 165, 26));
		}
		return cbbImpExpType;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbContract() {
		if (cbbContract == null) {
			cbbContract = new JComboBox();
			cbbContract.setBounds(new Rectangle(377, 54, 156, 25));
		}
		return cbbContract;
	}

	/**
	 * This method initializes cbbCustoms
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCustoms() {
		if (cbbCustoms == null) {
			cbbCustoms = new JComboBox();
			cbbCustoms.setBounds(new Rectangle(622, 54, 144, 25));
		}
		return cbbCustoms;
	}

	/**
	 * This method initializes cbbImpExpPort
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbImpExpPort() {
		if (cbbImpExpPort == null) {
			cbbImpExpPort = new JComboBox();
			cbbImpExpPort.setBounds(new Rectangle(623, 82, 144, 25));
		}
		return cbbImpExpPort;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbListType() {
		if (cbbListType == null) {
			cbbListType = new JComboBox();
			cbbListType.setBounds(new Rectangle(378, 110, 156, 25));
		}
		return cbbListType;
	}

	public boolean isImportGoods() {
		return isImportGoods;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbTransportMode() {
		if (cbbTransportMode == null) {
			cbbTransportMode = new JComboBox();
			cbbTransportMode.setBounds(new Rectangle(378, 82, 156, 25));
		}
		return cbbTransportMode;
	}

	/**
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbTradeMode() {
		if (cbbTradeMode == null) {
			cbbTradeMode = new JComboBox();
			cbbTradeMode.setBounds(new Rectangle(106, 110, 165, 25));
		}
		return cbbTradeMode;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(new Rectangle(106, 82, 146, 26));
		}
		return jTextField;
	}

	/**
	 * This method initializes btnEnterpriseBillNo
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEnterpriseBillNo() {
		if (btnEnterpriseBillNo == null) {
			btnEnterpriseBillNo = new JButton();
			btnEnterpriseBillNo.setBounds(251, 82, 20, 25);
			btnEnterpriseBillNo.setText("……");
			btnEnterpriseBillNo
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							Object obj = DzscImpExpRequestQuery
									.getInstance()
									.getDzscCustomsBillListByType(
											Integer
													.parseInt(((ItemProperty) (cbbImpExpType
															.getSelectedItem()))
															.getCode()));
							if (obj != null) {
								applyToCustomsBillList = (DzscCustomsBillList) obj;
								jTextField.setText(applyToCustomsBillList
										.getListNo());
							} else {
								jTextField.setText("");
							}

						}
					});
		}
		return btnEnterpriseBillNo;
	}

	private void initUIComponents() {
		this.initCbbComponents();
		this.initRbComponents();
		this.initOtherCbbComponents();
	}

	private void initRbComponents() {
		this.jRadioButton3.setSelected(!this.isImportGoods());
		this.jRadioButton2.setSelected(this.isImportGoods());
		this.jRadioButton.setSelected(true);
		setState(true);
	}

	private void initOtherCbbComponents() {
		// 初始化进口口岸
		this.cbbCustoms
				.setModel(CustomBaseModel.getInstance().getCustomModel());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbCustoms);
		this.cbbCustoms.setRenderer(CustomBaseRender.getInstance().getRender());
		this.cbbCustoms.setSelectedItem(null);
		// 初始化进出口海关
		this.cbbImpExpPort.setModel(CustomBaseModel.getInstance()
				.getCustomModel());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbImpExpPort);
		this.cbbImpExpPort.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		this.cbbImpExpPort.setSelectedItem(null);
		// 初始化运输方式
		this.cbbTransportMode.setModel(CustomBaseModel.getInstance()
				.getTransfModel());
		this.cbbTransportMode.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbTransportMode);
		this.cbbTransportMode.setSelectedIndex(-1);
		// 初始化贸易方式
		this.cbbTradeMode.setModel(CustomBaseModel.getInstance()
				.getTradeModel());
		this.cbbTradeMode.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbTradeMode);
		this.cbbTradeMode.setSelectedIndex(-1);
	}

	/**
	 * 验证报关清单表头数据类型是否为空
	 */
	public boolean validateImpExpTypeIsNull() {
		boolean isNull = false;
		if (this.cbbImpExpType.getSelectedItem() == null) {
			isNull = true;
		}
		return isNull;
	}

	/**
	 * 验证报关清单表头数据是否为空
	 */
	// public boolean validateApplyToCustomsBillListIsNull() {
	// boolean isNull = false;
	// if (this.jRadioButton1.isSelected() == true) {
	// if (this.applyToCustomsBillList == null) {
	// isNull = true;
	// }
	// }
	// return isNull;
	// }
	//
	/**
	 * 验证报关清单类型是否为空
	 */
	public boolean validateListTypeIsNull() {
		boolean isNull = false;
		if (this.cbbListType.getSelectedItem() == null) {
			isNull = true;
		}
		return isNull;
	}

	//	
	// public MakeCusomsDeclarationParam getMakeCusomsDeclarationParam() {
	// if (this.jRadioButton.isSelected() == true) {
	// MakeCusomsDeclarationParam a = new MakeCusomsDeclarationParam();
	// a.setTransf((Transf) this.cbbTransportMode.getSelectedItem());
	// a.setTrade((Trade) this.cbbTradeMode.getSelectedItem());
	// a.setCustoms((Customs) this.cbbImpExpPort.getSelectedItem());
	// a
	// .setDeclarationCustoms((Customs) this.cbbCustoms
	// .getSelectedItem());
	// return a;
	// }
	// return null;
	// }
	//	

	/**
	 * 获得供应商或公司名称
	 */
	public int getImpExpType() {
		if (this.cbbImpExpType.getSelectedItem() == null) {
			return -1;
		}
		return Integer.parseInt(((ItemProperty) this.cbbImpExpType
				.getSelectedItem()).getCode());
	}

	/**
	 * 是否是新的记录
	 */
	public boolean getIsNewRecord() {
		return this.jRadioButton.isSelected();
	}

	/**
	 * @return Returns the emsHeadH2k.
	 */
	public DzscEmsPorHead getDzscEmsPorHead() {
		return dzscEmsPorHead;
	}

	/**
	 * @param emsHeadH2k
	 *            The emsHeadH2k to set.
	 */
	public void setDzscEmsPorHead(DzscEmsPorHead emsHeadH2k) {
		this.dzscEmsPorHead = emsHeadH2k;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			jTextField1.setBounds(new Rectangle(106, 138, 336, 25));
		}
		return jTextField1;
	}

//	public int getType() {
//		return type;
//	}
//
//	public void setType(int type) {
//		this.type = type;
//	}

	public JTableListModel getTableMode() {
		return tableMode;
	}

	/**
	 * @param tableMode
	 *            The tableMode to set.
	 */
	public void setTableMode(JTableListModel tableMode) {
		this.tableMode = tableMode;
	}

	/**
	 * @param isImportGoods
	 *            The isImportGoods to set.
	 */
	public void setImportGoods(boolean isImportGoods) {
		this.isImportGoods = isImportGoods;
	}

	class MultiRenderer extends DefaultTableCellRenderer {
		JCheckBox checkBox = new JCheckBox();

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			if (value == null) {
				return this;
			}
			if (Boolean.valueOf(value.toString()) instanceof Boolean) {
				checkBox.setSelected(Boolean.parseBoolean(value.toString()));
				checkBox.setHorizontalAlignment(JLabel.CENTER);
				checkBox.setBackground(table.getBackground());
				if (isSelected) {
					checkBox.setForeground(table.getSelectionForeground());
					checkBox.setBackground(table.getSelectionBackground());
				} else {
					checkBox.setForeground(table.getForeground());
					checkBox.setBackground(table.getBackground());
				}
				return checkBox;
			}
			String str = (value == null) ? "" : value.toString();
			return super.getTableCellRendererComponent(table, str, isSelected,
					hasFocus, row, column);
		}
	}

	class CheckBoxEditor extends DefaultCellEditor implements ActionListener {
		private JCheckBox cb;

		private JTable table = null;

		public CheckBoxEditor(JCheckBox checkBox) {
			super(checkBox);
		}

		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {
			if (value == null) {
				return null;
			}
			if (Boolean.valueOf(value.toString()) instanceof Boolean) {
				cb = new JCheckBox();
				cb
						.setSelected(Boolean.valueOf(value.toString())
								.booleanValue());
			}
			cb.setHorizontalAlignment(JLabel.CENTER);
			cb.addActionListener(this);
			this.table = table;
			return cb;
		}

		public Object getCellEditorValue() {
			cb.removeActionListener(this);
			return cb;
		}

		public void actionPerformed(ActionEvent e) {
			JTableListModel tableModel = (JTableListModel) this.table
					.getModel();
			Object obj = tableModel.getCurrentRow();
			if (obj instanceof TempImpExpRequestBill) {
				TempImpExpRequestBill temp = (TempImpExpRequestBill) obj;
				temp.setIsSelected(new Boolean(cb.isSelected()));
				tableModel.updateRow(obj);
			} else if (obj instanceof TempImpExpCommodityInfo) {
				TempImpExpCommodityInfo temp = (TempImpExpCommodityInfo) obj;
				temp.setIsSelected(new Boolean(cb.isSelected()));
				tableModel.updateRow(obj);
			}
			fireEditingStopped();
		}
	}

	public int getImpExpTypeIxp() {
		return impExpTypeIxp;
	}

	public void setImpExpTypeIxp(int type) {
		this.impExpTypeIxp = type;
	}

	public int getBillType() {
		return billType;
	}

	public void setBillType(int billType) {
		this.billType = billType;
	}

	private void initTable(List list) {
		if (list == null) {
			list = new Vector();
		}
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List list = new Vector();
				list.add(addColumn("选择", "isSelected", 100));
				list.add(addColumn("单据号", "impExpRequestBill.billNo", 100));
				list.add(addColumn("生效日期",
						"impExpRequestBill.beginAvailability", 80));
				list
						.add(addColumn("有效",
								"impExpRequestBill.isAvailability", 50));
				list.add(addColumn("已转报关清单", "impExpRequestBill.isCustomsBill",
						80));
				list.add(addColumn("项目个数", "impExpRequestBill.itemCount", 60));
				// list.add(addColumn("仓库名称", "impExpRequestBill.wareSet.name",
				// 100));
				list.add(addColumn("客户/供应商名称", "impExpRequestBill.scmCoc.name",
						150));
				list.add(addColumn("运输工具", "impExpRequestBill.conveyance", 60));
				list.add(addColumn("录入日期", "impExpRequestBill.imputDate", 80));
				return list;
			}
		};
		jTableListModelAdapter.setEditableColumn(1);
		tableModel = new JTableListModel(jTable, list, jTableListModelAdapter);
		jTable.getColumnModel().getColumn(1).setCellRenderer(
				new MultiRenderer());
		jTable.getColumnModel().getColumn(1).setCellEditor(
				new CheckBoxEditor(new JCheckBox()));
		jTable.getColumnModel().getColumn(4).setCellRenderer(
				new MultiRenderer());
		jTable.getColumnModel().getColumn(5).setCellRenderer(
				new MultiRenderer());
	}

	private void initTable1(List aaa) {
		// TempImpExpCommodityInfo
		// tempImpExpCommodityInfos=(TempImpExpCommodityInfo)aaa.get(0);
		List list = this.encAction.findTempImpExpCommodityInfoByParent(
				new Request(CommonVars.getCurrUser()), aaa);
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List list = new Vector();
				list.add(addColumn("选择", "isSelected", 100));
				list.add(addColumn("料号", "impExpCommodityInfo.materiel.ptNo",
						90));
				list.add(addColumn("名称",
						"impExpCommodityInfo.materiel.factoryName", 120));
				list.add(addColumn("规格型号",
						"impExpCommodityInfo.materiel.factorySpec", 120));
				list.add(addColumn("单位",
						"impExpCommodityInfo.materiel.calUnit.name", 60));
				list.add(addColumn("数量", "impExpCommodityInfo.quantity", 60));
				list.add(addColumn("单价", "impExpCommodityInfo.unitPrice", 60));
				list
						.add(addColumn("总金额",
								"impExpCommodityInfo.amountPrice", 60));
				list
						.add(addColumn("毛重", "impExpCommodityInfo.grossWeight",
								60));
				list.add(addColumn("净重", "impExpCommodityInfo.netWeight", 60));
				list.add(addColumn("仓库名称",
						"impExpCommodityInfo.impExpRequestBill.wareSet.name",
						100));
				list.add(addColumn("仓库毛重",
						"impExpCommodityInfo.materiel.ckoutWeight", 60));
				list.add(addColumn("仓库净重",
						"impExpCommodityInfo.materiel.cknetWeight", 60));
				list.add(addColumn("件数", "impExpCommodityInfo.piece", 60));
				list.add(addColumn("体积", "impExpCommodityInfo.bulks", 60));
				list
						.add(addColumn("制单号", "impExpCommodityInfo.makeBillNo",
								60));
				list.add(addColumn("版本号", "impExpCommodityInfo.version", 60));
				list.add(addColumn("未归并的料号", "impExpCommodityInfo.ptNo", 80));
				return list;
			}
		};
		if (aaa.size() == 0) {
			list.clear();
		}
		jTableListModelAdapter.setEditableColumn(1);
		tableModel1 = new JTableListModel(jTable1, list, jTableListModelAdapter);

		jTable1.getColumnModel().getColumn(1).setCellRenderer(
				new MultiRenderer());
		jTable1.getColumnModel().getColumn(1).setCellEditor(
				new CheckBoxEditor(new JCheckBox()));
	}

	public void setVisible(boolean b) {
		if (b) {
			// impExpRequestBill.setBillType(Integer.valueOf(getImpExpTypeIxp()));
			list = this.encAction.findTempImpExpRequestBillByImpExpTypeToATC(
					new Request(CommonVars.getCurrUser()), this.impExpTypeIxp);
			Integer.valueOf(getImpExpType());
			initTable(list);
			isexe = false;
			initUIComponents();
			// this.jSplitPane.repaint();
			// this.jSplitPane.validate();
		}
		super.setVisible(b);
	}

	// /**
	// * @param isNewBgd
	// * The isNewBgd to set.
	// */
	// public void setNewBgd(boolean isNewBgd) {
	// this.isNewBgd = isNewBgd;
	// }

	public ButtonGroup getButtonGroup() {
		if (buttonGroup == null) {
			buttonGroup = new ButtonGroup();
			buttonGroup.add(getJRadioButton());
			buttonGroup.add(getJRadioButton1());
		}
		return buttonGroup;
	}

	public ButtonGroup getButtonGroup1() {
		if (buttonGroup1 == null) {
			buttonGroup1 = new ButtonGroup();
			buttonGroup1.add(getJRadioButton2());
			buttonGroup1.add(getJRadioButton3());
		}
		return buttonGroup1;
	}

	private void setState(boolean isNewApplyToCustomsBill) {
		this.jTextField.setEditable(!isNewApplyToCustomsBill);
		this.btnEnterpriseBillNo.setEnabled(!isNewApplyToCustomsBill);
		// this.tfEmsNo.setEditable(isNewApplyToCustomsBill);
		this.jTextField1.setEditable(isNewApplyToCustomsBill);
		this.cbbCustoms.setEnabled(isNewApplyToCustomsBill);
		this.cbbImpExpPort.setEnabled(isNewApplyToCustomsBill);
		// this.cbbImpExpType.setEnabled(isNewApplyToCustomsBill);
		this.cbbTradeMode.setEnabled(isNewApplyToCustomsBill);
		this.cbbTransportMode.setEnabled(isNewApplyToCustomsBill);
		if (isNewApplyToCustomsBill == true) {
			this.applyToCustomsBillList = null;
		}
	}

	/**
	 * @return Returns the materielProductType.
	 */
	public int getMaterielProductType() {
		return materielProductType;
	}

	/**
	 * @param materielProductType
	 *            The materielProductType to set.
	 */
	public void setMaterielProductType(int materielProductType) {
		this.materielProductType = materielProductType;
	}

	public DzscCustomsBillList getApplyToCustomsBillList() {
		if (this.jRadioButton.isSelected() == true) {
			applyToCustomsBillList = new DzscCustomsBillList();
			applyToCustomsBillList.setCompany(CommonVars.getCurrUser()
					.getCompany());
			applyToCustomsBillList.setMemos(this.jTextField1.getText());
			applyToCustomsBillList.setCreatedDate(new Date());
			applyToCustomsBillList.setCreatedUser(CommonVars.getCurrUser());
			String copEntNo=getMaxBillListNo();
			applyToCustomsBillList.setListNo(copEntNo.substring(3));
			applyToCustomsBillList.setCopEmsNo(copEntNo);
			applyToCustomsBillList.setDeclareCIQ((Customs) this.cbbCustoms
					.getSelectedItem());
			applyToCustomsBillList.setImpExpCIQ((Customs) this.cbbImpExpPort
					.getSelectedItem());
			applyToCustomsBillList.setTradeMode((Trade) this.cbbTradeMode
					.getSelectedItem());
			applyToCustomsBillList
					.setTransportMode((Transf) this.cbbTransportMode
							.getSelectedItem());
			applyToCustomsBillList.setEmsHeadH2k(dzscEmsPorHead == null ? ""
					: this.dzscEmsPorHead.getEmsNo());
			applyToCustomsBillList.setListState(Integer
					.valueOf(DzscState.ORIGINAL));
			Integer impExpType = Integer
					.valueOf(((ItemProperty) this.cbbImpExpType
							.getSelectedItem()).getCode());
			applyToCustomsBillList.setImpExpType(impExpType);
			applyToCustomsBillList.setMaterielProductFlag(EncCommon
					.getMaterielTypeByBillType(impExpType));
			applyToCustomsBillList
					.setTradeName(this.dzscEmsPorHead == null ? ""
							: this.dzscEmsPorHead.getTradeName());
			applyToCustomsBillList
					.setTradeCode(this.dzscEmsPorHead == null ? ""
							: this.dzscEmsPorHead.getTradeCode());
			if (this.isImportGoods == true) {
				applyToCustomsBillList.setImpExpFlag(Integer
						.valueOf(ImpExpFlag.IMPORT));
			} else {
				applyToCustomsBillList.setImpExpFlag(Integer
						.valueOf(ImpExpFlag.EXPORT));
			}
			Integer listType = Integer.valueOf(((ItemProperty) this.cbbListType
					.getSelectedItem()).getCode());
			applyToCustomsBillList.setListType(listType);
			return applyToCustomsBillList;
		} else {
			return this.applyToCustomsBillList;
		}
	}

	/**
	 * 生成报关清单
	 * 
	 */

	private void makeDzscCustomsBillList() {
		// try {
		boolean isMateriel = this.materielProductType == Integer
				.parseInt(MaterielType.MATERIEL);
		getApplyToCustomsBillList();
		// applyToCustomsBillList.setListNo(this.getMaxBillListNo()); // 清单序号
		List list = null;
		// String emsFrom = CommonVars.getEmsFrom();
		list = this.dzscListAction.makeApplyToCustomsRequestBillList(
				new Request(CommonVars.getCurrUser()), applyToCustomsBillList,
				getCommodityList(), isMateriel, getIsNewRecord(), jCheckBox
						.isSelected());
		String listNo = "";
		for (int i = 0; i < list.size(); i++) {
			DzscCustomsBillList billList = (DzscCustomsBillList) list.get(i);// 清单单据头
			listNo += (billList.getListNo()) + ";";
		}
		// List impExpRequestList = (List) list.get(1);// 申请单据头
		// if (isNewBgd) {
		// this.tableMode.updateRows(impExpRequestList);
		// }
		JOptionPane.showMessageDialog(null, "报关清单生成成功!\n报关清单编号为:" + listNo,
				"提示", JOptionPane.INFORMATION_MESSAGE);
		// } catch (Exception ex) {
		// JOptionPane.showMessageDialog(null, "报关清单生成失败," + ex.getMessage(),
		// "提示", JOptionPane.INFORMATION_MESSAGE);
		// }
	}

	public boolean vaildateData() {
		// 检查商品信息归并后信息是否在电子手册备案过
		if (cbbContract.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(null, "没有选择的手册号", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		} else {
			List list = dzscListAction.checkRequestBillForBillList(new Request(
					CommonVars.getCurrUser()), this.getCommodityList(),
					dzscEmsPorHead);
			if (list.size() > 0) {
				setValidateDataColor(list);
				return false;
			}
		}
		return true;
	}

	public void setValidateDataColor(List checkList) {
		this.colorList = checkList;
		for (int i = 2; i < this.tableModel1.getColumnCount(); i++) {
			jTable1.getColumnModel().getColumn(i).setCellRenderer(
					new ColorTableCellRenderer());
		}
		jTable1.getColumnModel().getColumn(1).setCellRenderer(
				new MultiRenderer());
		jTable1.getColumnModel().getColumn(1).setCellEditor(
				new CheckBoxEditor(new JCheckBox()));
		jTable1.repaint();
		jTable1.validate();
	}

	/**
	 * render table color row
	 */
	class ColorTableCellRenderer extends DefaultTableCellRenderer {
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			Component c = super.getTableCellRendererComponent(table, value,
					isSelected, hasFocus, row, column);
			if (checkValue(table, row, column)) {
				c.setBackground(Color.BLUE);
				c.setForeground(Color.WHITE);
			} else {
				if (isSelected) {
					c.setForeground(table.getSelectionForeground());
					c.setBackground(table.getSelectionBackground());
				} else {
					c.setForeground(table.getForeground());
					c.setBackground(table.getBackground());
				}
			}
			return c;
		}
	}

	private boolean checkValue(JTable table, int row, int column) {
		if (colorList == null) {
			return false;
		}
		TempImpExpCommodityInfo data = (TempImpExpCommodityInfo) tableModel1
				.getDataByRow(row);
		for (int i = 0; i < colorList.size(); i++) {
			TempImpExpCommodityInfo c = (TempImpExpCommodityInfo) colorList
					.get(i);
			if (data.getImpExpCommodityInfo().getId().equals(
					c.getImpExpCommodityInfo().getId()) == true) {
				return true;
			}
		}
		return false;
	}

	private String getMaxBillListNo() {
		return dzscListAction.getApplyToCustomsBillListMaxNo(new Request(
				CommonVars.getCurrUser()));
	}

	public List getCommodityList() {
		if (this.tableModel1 == null) {
			return null;
		}
		List list = tableModel1.getList();
		List newList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) instanceof TempImpExpCommodityInfo) {
				TempImpExpCommodityInfo temp = (TempImpExpCommodityInfo) list
						.get(i);
				if (temp.getIsSelected().booleanValue() == true) {
					newList.add(temp);
				}
			}
		}
		return newList;
	}

	private void initCbbComponents() {
		// 初始化进出口类型
		this.cbbImpExpType.removeAllItems();
		if (this.isImportGoods() == true) {

			this.cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.DIRECT_IMPORT), "料件进口"));
			this.cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.TRANSFER_FACTORY_IMPORT), "料件转厂"));
			this.cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.BACK_FACTORY_REWORK), "退厂返工"));
			this.cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.GENERAL_TRADE_IMPORT), "一般贸易进口"));

		} else {

			this.cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.DIRECT_EXPORT), "成品出口"));
			this.cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.TRANSFER_FACTORY_EXPORT), "转厂出口"));
			this.cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.BACK_MATERIEL_EXPORT), "退料出口"));
			this.cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.REWORK_EXPORT), "返工复出"));
			this.cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.REMIAN_MATERIAL_BACK_PORT), "边角料退港"));
			this.cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES),
					"边角料内销"));
			this.cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.GENERAL_TRADE_EXPORT), "一般贸易出口"));
		}
		this.cbbImpExpType.setSelectedIndex(ItemProperty.getIndexByCode(String
				.valueOf(this.impExpType), cbbImpExpType));
		// this.tfEmsNo.setText(dzscEmsPorHead == null ? "" :
		// dzscEmsPorHead.getEmsNo());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbImpExpType);
		this.cbbImpExpType.setRenderer(CustomBaseRender.getInstance()
				.getRender());

		// 清单类型
		this.cbbListType.removeAllItems();
		this.cbbListType.addItem(new ItemProperty(Integer.valueOf(
				DzscListType.NORMAL_DCR).toString(), DzscListType
				.getListTypeDesc(DzscListType.NORMAL_DCR)));
		this.cbbListType.addItem(new ItemProperty(Integer.valueOf(
				DzscListType.PRE_TRANS_DCR).toString(), DzscListType
				.getListTypeDesc(DzscListType.PRE_TRANS_DCR)));
		this.cbbListType.addItem(new ItemProperty(Integer.valueOf(
				DzscListType.SECOND_TRANS_DCR).toString(), DzscListType
				.getListTypeDesc(DzscListType.SECOND_TRANS_DCR)));
		this.cbbListType.addItem(new ItemProperty(Integer.valueOf(
				DzscListType.APANAGE_DCR).toString(), DzscListType
				.getListTypeDesc(DzscListType.APANAGE_DCR)));
		this.cbbListType
				.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbListType);
		this.cbbListType.setSelectedIndex(-1);
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.add(getJJToolBarBar(), java.awt.BorderLayout.NORTH);
			jPanel1.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(new BorderLayout());
			jPanel2.add(getJJToolBarBar1(), java.awt.BorderLayout.NORTH);
			jPanel2.add(getJScrollPane2(), java.awt.BorderLayout.CENTER);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jJToolBarBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJJToolBarBar() {
		if (jJToolBarBar == null) {
			jJToolBarBar = new JToolBar();
			jJToolBarBar.setPreferredSize(new java.awt.Dimension(18, 34));
			jJToolBarBar.add(getBtnSelectAll());
			jJToolBarBar.add(getBtnNoSelectAll());
		}
		return jJToolBarBar;
	}

	/**
	 * This method initializes jJToolBarBar1
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJJToolBarBar1() {
		if (jJToolBarBar1 == null) {
			jJToolBarBar1 = new JToolBar();
			jJToolBarBar1.setPreferredSize(new java.awt.Dimension(18, 34));
			jJToolBarBar1.add(getJButton2());
			jJToolBarBar1.add(getJButton3());
			jJToolBarBar1.add(getJButton1());
			jJToolBarBar1.add(getJButton());
		}
		return jJToolBarBar1;
	}

	/**
	 * This method initializes btnSelectAll
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSelectAll() {
		if (btnSelectAll == null) {
			btnSelectAll = new JButton();
			btnSelectAll.setText("全选");
			btnSelectAll.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgDzscMakeCustomsListNew.this.setCursor(new Cursor(
							Cursor.WAIT_CURSOR));
					selectAll(true);
					DgDzscMakeCustomsListNew.this.setCursor(new Cursor(
							Cursor.DEFAULT_CURSOR));
				}
			});
		}
		return btnSelectAll;
	}

	/**
	 * This method initializes btnNoSelectAll
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnNoSelectAll() {
		if (btnNoSelectAll == null) {
			btnNoSelectAll = new JButton();
			btnNoSelectAll.setText("不选");
			btnNoSelectAll
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgDzscMakeCustomsListNew.this.setCursor(new Cursor(
									Cursor.WAIT_CURSOR));
							selectAll(false);
							DgDzscMakeCustomsListNew.this.setCursor(new Cursor(
									Cursor.DEFAULT_CURSOR));
						}
					});
		}
		return btnNoSelectAll;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("全选");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					selectAll1(true);

				}
			});
		}
		return jButton2;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("不选");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					selectAll1(false);
				}
			});

		}
		return jButton1;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (btnDone == null) {
			btnDone = new JButton();
			btnDone.setText("生成清单");
			btnDone.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					commodityList = getCommodityList();// 申请单据体
					if (commodityList == null || commodityList.size() <= 0) {
						JOptionPane.showMessageDialog(null, "请选择进出口申请单据商品信息!",
								"提示", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					if (validateListTypeIsNull() != false) {
						JOptionPane.showMessageDialog(null, "清单类型为空!", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					if (!vaildateData()) {
						JOptionPane.showMessageDialog(null,
								"选择的商品信息中,着色的记录没有对应的合同备案数据!", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					makeDzscCustomsBillList();
				}
			});
		}
		return btnDone;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton3() {
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
			jTable.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {

						public void valueChanged(ListSelectionEvent arg0) {
							if (tableModel == null) {
								return;
							}

							initTable1(getParentList());
						}
					});
		}
		return jTable;
	}

	/**
	 * This method initializes jScrollPane2
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setViewportView(getJTable1());
		}
		return jScrollPane2;
	}

	/**
	 * This method initializes jTable1
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable1() {
		if (jTable1 == null) {
			jTable1 = new JTable();
		}
		return jTable1;
	}

	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setLayout(null);
			jPanel3.setBounds(new Rectangle(424, 15, 342, 34));
			jPanel3.setBorder(BorderFactory.createTitledBorder(null, "",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.PLAIN, 12), new Color(51, 51, 51)));
			jPanel3.add(getJRadioButton2(), null);
			jPanel3.add(getJRadioButton3(), null);
		}
		return jPanel3;
	}

	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jPanel4 = new JPanel();
			jPanel4.setLayout(null);
			jPanel4.setBounds(new Rectangle(26, 15, 374, 34));
			jPanel4.setBorder(BorderFactory.createTitledBorder(null, "",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.PLAIN, 12), new Color(51, 51, 51)));
			jPanel4.add(getJRadioButton(), null);
			jPanel4.add(getJRadioButton1(), null);
		}
		return jPanel4;
	}

	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			jCheckBox = new JCheckBox();
			jCheckBox.setBounds(new Rectangle(550, 110, 212, 27));
			jCheckBox.setText("是否将相同料号和国别的商品合并");
		}
		return jCheckBox;
	}

} // @jve:decl-index=0:visual-constraint="14,-38"
