/*
 * Created on 2004-8-7
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.client.custom.common;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpType;
import com.bestway.customs.common.action.BaseEncAction;
import com.bestway.customs.common.entity.BaseEmsHead;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import java.awt.Rectangle;
import java.awt.Dimension;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public abstract class FmBaseImExportCustomsDeclarationDelete extends JInternalFrameBase {

	protected JPanel jContentPane = null;

	protected JToolBar jToolBar = null;

	protected JButton btnClose = null;

	protected JTableListModel tableModel = null;

	protected BaseEncAction baseEncAction = null;  //  @jve:decl-index=0:

	protected int projectType;


	protected JLabel lbEmsHead = null;

	protected JComboBox cbbEmshead = null;

	private JPanel jPanel1 = null;

	private JCalendarComboBox jCalendarComboBox = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JCalendarComboBox jCalendarComboBox1 = null;

	private JButton jButton2 = null;

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;

	private JPopupMenu jPopupMenuPrint = null;

	private JMenuItem miCoverPrint = null;

	private JMenuItem miNonCoverPrint = null;

	/**
	 * This method initializes
	 * 
	 */
	public FmBaseImExportCustomsDeclarationDelete() {
		super();
		initialize();
		jTable.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
						if (e.getValueIsAdjusting()) {
							return;
						}
						JTableListModel tableModel = (JTableListModel) jTable
								.getModel();
						if (tableModel == null) {
							return;
						}
						try {
							if (tableModel.getCurrentRow() != null) {
							}
						} catch (Exception ee) {
						}
					}
				});
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	protected void initialize() {
		this.setTitle("进出口报关单删单查询");
		this.setContentPane(getJContentPane());
		this.setSize(845, 371);
		this.jCalendarComboBox.setDate(CommonVars.getBeginDate());
		this.jCalendarComboBox1.setDate(new Date());
		this
				.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
					public void internalFrameOpened(
							javax.swing.event.InternalFrameEvent e) {
						initUIComponents();
						initTable(getDataSource());
					}
				});
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	protected JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	protected JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.setPreferredSize(new Dimension(19, 34));
			jToolBar.add(getJPanel1());
		}
		return jToolBar;
	}

	/**
	 * This method initializes btnClose
	 * 
	 * @return javax.swing.JButton
	 */
	protected JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("关闭");
			btnClose.setBounds(new Rectangle(732, 3, 62, 25));
			//jButton2.setBounds(664, 4, 62, 22);
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmBaseImExportCustomsDeclarationDelete.this.doDefaultCloseAction();
				}
			});
		}
		return btnClose;
	}

	protected List getDataSource() {
		BaseEmsHead emsHead = (BaseEmsHead) this.cbbEmshead.getSelectedItem();
		if (emsHead == null) {
			return new ArrayList();
		}
		String emsNo = emsHead.getEmsNo();
		Date beginDate = CommonVars
				.dateToStandDate(jCalendarComboBox.getDate());
		Date endDate = CommonVars.dateToStandDate(jCalendarComboBox1.getDate());
		List list = baseEncAction.getImExportCustomsDeclarationDelete(new Request(
				CommonVars.getCurrUser()),projectType, emsNo, beginDate, endDate);
		return(list);
	}

	protected void initTable(List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("操作人", "baseCustomsDeclaration.workEr",60));
						list.add(addColumn("操作日期", "baseCustomsDeclaration.workDate", 80));
						list.add(addColumn("操作类型", "baseCustomsDeclaration.impExpType", 80));
						list.add(addColumn("流水号", "baseCustomsDeclaration.serialNumber", 50,Integer.class));
						list.add(addColumn("进出口岸", "baseCustomsDeclaration.customs.name", 180));
						list.add(addColumn("报关单号", "baseCustomsDeclaration.customsDeclarationCode",150));
						list.add(addColumn("运输方式", "baseCustomsDeclaration.transferMode.name", 100));
						list.add(addColumn("运输工具", "baseCustomsDeclaration.conveyance", 100));
						list.add(addColumn("提运单号", "baseCustomsDeclaration.billOfLading", 100));
						list.add(addColumn("贸易方式", "baseCustomsDeclaration.tradeMode.name", 80));
						list.add(addColumn("装货港","baseCustomsDeclaration.portOfLoadingOrUnloading.name", 80));
						list.add(addColumn("成交方式","baseCustomsDeclaration.transac.name", 80));											
						list.add(addColumn("包装种类", "baseCustomsDeclaration.wrapType.name", 80));
						list.add(addColumn("件数", "baseCustomsDeclaration.commodityNum", 80,Integer.class));
						list.add(addColumn("毛重", "baseCustomsDeclaration.grossWeight", 80));
						list.add(addColumn("净重", "baseCustomsDeclaration.netWeight", 80));						
						list.add(addColumn("装箱号", "baseCustomsDeclaration.wlserialNumber", 100));
						list.add(addColumn("币制", "baseCustomsDeclaration.currency.name", 80));
						list.add(addColumn("汇率", "baseCustomsDeclaration.exchangeRate", 80));
						list.add(addColumn("柜号", "baseCustomsDeclaration.allContainerNumInEleven", 80));
						list.add(addColumn("出口发票", "baseCustomsDeclaration.invoiceCode", 80));
						list.add(addColumn("核销单号", "baseCustomsDeclaration.authorizeFile", 80));
						
						list.add(addColumn("商品编码", "baseCustomsDeclarationCommInfo.complex.code", 80));
						list.add(addColumn("序号", "baseCustomsDeclarationCommInfo.commSerialNo", 60));
						list.add(addColumn("名称", "baseCustomsDeclarationCommInfo.commName", 100));
						list.add(addColumn("规格型号", "baseCustomsDeclarationCommInfo.commSpec", 150));
						
						list.add(addColumn("数量", "baseCustomsDeclarationCommInfo.commAmount", 80));
						list.add(addColumn("单位", "baseCustomsDeclarationCommInfo.unit.name", 80));
						list.add(addColumn("单位重量", "baseCustomsDeclarationCommInfo.unitWeight", 80));
						list.add(addColumn("美元单价", "baseCustomsDeclarationCommInfo.commUnitPrice", 80));
						list.add(addColumn("美元总价", "baseCustomsDeclarationCommInfo.commTotalPrice", 80));
						list.add(addColumn("版本", "baseCustomsDeclarationCommInfo.version", 80));
						
						return list;
					}
				});
		jTable.getColumnModel().removeColumn(
				jTable.getColumnModel().getColumn(0));
		jTable.getColumnModel().getColumn(2).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						String str = "";
						if (value != null) {
							switch (Integer.parseInt(value.toString())) {
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
							case ImpExpType.REMAIN_FORWARD_EXPORT:
								str = "余料结转";
								break;
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
							}
						}
						this.setText(str);
						return this;
					}
				});
	}


	protected boolean getBoolean(Boolean b) {
		if (b == null) {
			return false;
		}
		return b.booleanValue();
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	protected JComboBox getCbbEmshead() {
		if (cbbEmshead == null) {
			cbbEmshead = new JComboBox();
			cbbEmshead.setSelectedItem(null);
			cbbEmshead.setBounds(new Rectangle(118, 1, 210, 27));
			cbbEmshead.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
						initTable(getDataSource());
					}
				}
			});
		}
		return cbbEmshead;
	}

	/**
	 * 初始化窗口上控件的初始值
	 * 
	 */
	protected abstract void initUIComponents();

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			lbEmsHead = new JLabel();
			lbEmsHead.setText("执行的合同(合同号+帐册号)");
			lbEmsHead.setBounds(new Rectangle(8, 5, 109, 18));
			jLabel1 = new JLabel();
			jLabel = new JLabel();
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jLabel.setBounds(335, 3, 75, 23);
			jLabel.setText("删单日期 从");
			jLabel1.setBounds(523, 3, 18, 21);
			jLabel1.setText("到");
			jPanel1.add(getBtnClose(), null);
			jPanel1.add(lbEmsHead, null);
			jPanel1.add(getCbbEmshead(), null);
			jPanel1.add(getJCalendarComboBox(), null);
			jPanel1.add(jLabel, null);
			jPanel1.add(jLabel1, null);
			jPanel1.add(getJCalendarComboBox1(), null);
			jPanel1.add(getJButton2(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getJCalendarComboBox() {
		if (jCalendarComboBox == null) {
			jCalendarComboBox = new JCalendarComboBox();
			jCalendarComboBox.setBounds(408, 2, 112, 22);
		}
		return jCalendarComboBox;
	}

	/**
	 * This method initializes jCalendarComboBox1
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getJCalendarComboBox1() {
		if (jCalendarComboBox1 == null) {
			jCalendarComboBox1 = new JCalendarComboBox();
			jCalendarComboBox1.setBounds(545, 2, 105, 22);
		}
		return jCalendarComboBox1;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("查询");
			jButton2.setBounds(664, 3, 62, 25);
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					initTable(getDataSource());
				}
			});
		}
		return jButton2;
	}

	/**
	 * @return Returns the projectType.
	 */
	public int getProjectType() {
		return projectType;
	}

	/**
	 * @param projectType
	 *            The projectType to set.
	 */
	public void setProjectType(int projectType) {
		this.projectType = projectType;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTable	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
			jTable.setRowHeight(25);
		}
		return jTable;
	}

	/**
	 * This method initializes jPopupMenuPrint	
	 * 	
	 * @return javax.swing.JPopupMenu	
	 */
	private JPopupMenu getJPopupMenuPrint() {
		if(jPopupMenuPrint == null) {
			jPopupMenuPrint = new JPopupMenu();
			jPopupMenuPrint.add(getMiCoverPrint());
			jPopupMenuPrint.add(getMiNonCoverPrint());
		}
		return jPopupMenuPrint;
	}

	/**
	 * This method initializes miCoverPrint	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMiCoverPrint() {
		if(miCoverPrint == null) {
			miCoverPrint = new JMenuItem();
		}
		return miCoverPrint;
	}

	/**
	 * This method initializes miNonCoverPrint	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMiNonCoverPrint() {
		if(miNonCoverPrint == null) {
			miNonCoverPrint = new JMenuItem();
		}
		return miNonCoverPrint;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
