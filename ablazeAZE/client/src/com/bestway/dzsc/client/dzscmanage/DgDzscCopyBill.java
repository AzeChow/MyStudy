
/*
 * Created on 2005-5-17
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.client.dzscmanage;

import java.awt.Component;
import java.util.List;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.DzscState;
import com.bestway.dzsc.dzscmanage.action.DzscAction;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author ls
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgDzscCopyBill extends JDialogBase {

	private javax.swing.JPanel	jContentPane				= null;
	private JTable				tb1							= null;
	private JScrollPane			jScrollPane					= null;
	private JButton				btnCopyBill					= null;
	private JButton				btnExit						= null;
	private DzscAction			dzscAction					= null;
	private JScrollPane			jScrollPane1				= null;
	private JTable				tb2							= null;
	private JPanel				pn1							= null;
	private JPanel				pn2							= null;
	private JRadioButton		rbProductMaterielUnitWaste	= null;
	private JRadioButton		rbProduct					= null;
	private JRadioButton		rbMateriel					= null;
	private JCheckBox			cbMaterielAmountToZero		= null;
	private JCheckBox			cbMaterielPriceToZero		= null;
	private JCheckBox			cbProductAmountToZero		= null;
	private JCheckBox			cbProductPriceToZero		= null;
	private JCheckBox			cbUnitWasteToZero			= null;
	private JLabel				jLabel						= null;
	private ButtonGroup			group						= new ButtonGroup();
	private DzscEmsPorHead		head						= null;

	/**
	 * This is the default constructor
	 */
	public DgDzscCopyBill() {
		super();
		dzscAction = (DzscAction) CommonVars.getApplicationContext().getBean(
				"dzscAction");
		initialize();
		group.add(this.rbMateriel);
		group.add(this.rbProduct);
		group.add(this.rbProductMaterielUnitWaste);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("转抄清单");
		this.setSize(720, 500);
		this.setContentPane(getJContentPane());
	}

	public DzscEmsPorHead getHead() {
		return head;
	}

	public void setHead(DzscEmsPorHead head) {
		this.head = head;
	}

	public void setVisible(boolean isShow) {
		if (isShow) {
			List list = this.dzscAction.findDzscEmsPorHead(new Request(
					CommonVars.getCurrUser()), DzscState.EXECUTE);
			initTb1(list);
			// initTb2(new ArrayList(), false);
		}
		super.setVisible(isShow);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(7, 152, 181, 15));
			jLabel.setText("成品");
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJScrollPane(), null);
			jContentPane.add(getJScrollPane1(), null);
			jContentPane.add(getBtnCopyBill(), null);
			jContentPane.add(getBtnExit(), null);
			jContentPane.add(getPn1(), null);
			jContentPane.add(getPn2(), null);
			jContentPane.add(jLabel, null);
		}
		return jContentPane;
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
							JTableListModel dataModel = (JTableListModel) tb1
									.getModel();
							if (dataModel == null) {
								return;
							}
							ListSelectionModel lsm = (ListSelectionModel) e
									.getSource();
							if (lsm.isSelectionEmpty()) {
								return;
							}
							showData();

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
			jScrollPane.setBounds(new java.awt.Rectangle(0, 0, 712, 151));
			jScrollPane.setViewportView(getTb1());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCopyBill() {
		if (btnCopyBill == null) {
			btnCopyBill = new JButton();
			btnCopyBill.setText("转抄");
			btnCopyBill.setBounds(new java.awt.Rectangle(619, 410, 77, 26));
			btnCopyBill.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					copyBill();
				}
			});
		}
		return btnCopyBill;
	}

	/**
	 * This method initializes jButton4
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("关闭");
			btnExit.setBounds(new java.awt.Rectangle(619, 440, 77, 26));
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgDzscCopyBill.this.dispose();
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
			jScrollPane1.setBounds(new java.awt.Rectangle(0, 168, 712, 233));
			jScrollPane1.setViewportView(getTb2());
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
		}
		return tb2;
	}

	/**
	 * This method initializes pn1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPn1() {
		if (pn1 == null) {
			pn1 = new JPanel();
			pn1.setLayout(null);
			pn1.setBounds(new java.awt.Rectangle(2, 401, 251, 72));
			pn1
					.setBorder(javax.swing.BorderFactory
							.createTitledBorder(
									javax.swing.BorderFactory
											.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED),
									"转抄项目",
									javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
									javax.swing.border.TitledBorder.DEFAULT_POSITION,
									null, null));
			pn1.add(getRbProductMaterielUnitWaste(), null);
			pn1.add(getRbProduct(), null);
			pn1.add(getRbMateriel(), null);
		}
		return pn1;
	}

	/**
	 * This method initializes pn2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPn2() {
		if (pn2 == null) {
			pn2 = new JPanel();
			pn2.setLayout(null);
			pn2.setBounds(new java.awt.Rectangle(251, 401, 354, 72));
			pn2
					.setBorder(javax.swing.BorderFactory
							.createTitledBorder(
									javax.swing.BorderFactory
											.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED),
									"转抄条件",
									javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
									javax.swing.border.TitledBorder.DEFAULT_POSITION,
									null, null));
			pn2.add(getCbMaterielAmountToZero(), null);
			pn2.add(getCbMaterielPriceToZero(), null);
			pn2.add(getCbProductAmountToZero(), null);
			pn2.add(getCbProductPriceToZero(), null);
			pn2.add(getCbUnitWasteToZero(), null);
		}
		return pn2;
	}

	/**
	 * This method initializes rbProductMaterielUnitWaste
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbProductMaterielUnitWaste() {
		if (rbProductMaterielUnitWaste == null) {
			rbProductMaterielUnitWaste = new JRadioButton();
			rbProductMaterielUnitWaste.setBounds(new java.awt.Rectangle(20, 21,
					126, 19));
			rbProductMaterielUnitWaste.setText("成品、单耗、料件");
			rbProductMaterielUnitWaste.setSelected(true);
			rbProductMaterielUnitWaste
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							jLabel.setText("成品表:");
							showData();
						}
					});

		}
		return rbProductMaterielUnitWaste;
	}

	/**
	 * This method initializes rbProduct
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbProduct() {
		if (rbProduct == null) {
			rbProduct = new JRadioButton();
			rbProduct.setBounds(new java.awt.Rectangle(155, 21, 50, 19));
			rbProduct.setText("成品");
			rbProduct.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					jLabel.setText("成品表:");
					showData();
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
			rbMateriel.setBounds(new java.awt.Rectangle(20, 42, 55, 19));
			rbMateriel.setText("料件");
			rbMateriel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					jLabel.setText("料件表:");
					showData();
				}
			});
		}
		return rbMateriel;
	}

	/**
	 * This method initializes cbMaterielAmountToZero
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbMaterielAmountToZero() {
		if (cbMaterielAmountToZero == null) {
			cbMaterielAmountToZero = new JCheckBox();
			cbMaterielAmountToZero.setBounds(new java.awt.Rectangle(26, 21,
					102, 19));
			cbMaterielAmountToZero.setText("料件数量置零");
		}
		return cbMaterielAmountToZero;
	}

	/**
	 * This method initializes cbMaterielPriceToZreo
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbMaterielPriceToZero() {
		if (cbMaterielPriceToZero == null) {
			cbMaterielPriceToZero = new JCheckBox();
			cbMaterielPriceToZero.setBounds(new java.awt.Rectangle(130, 21,
					103, 19));
			cbMaterielPriceToZero.setText("料件单价置零");
		}
		return cbMaterielPriceToZero;
	}

	/**
	 * This method initializes cbProductAmountToZero
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbProductAmountToZero() {
		if (cbProductAmountToZero == null) {
			cbProductAmountToZero = new JCheckBox();
			cbProductAmountToZero.setBounds(new java.awt.Rectangle(234, 21,
					102, 19));
			cbProductAmountToZero.setText("成品数量置零");
		}
		return cbProductAmountToZero;
	}

	/**
	 * This method initializes cbProductPriceToZero
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbProductPriceToZero() {
		if (cbProductPriceToZero == null) {
			cbProductPriceToZero = new JCheckBox();
			cbProductPriceToZero.setBounds(new java.awt.Rectangle(26, 41, 100,
					23));
			cbProductPriceToZero.setText("成品单价置零");
		}
		return cbProductPriceToZero;
	}

	/**
	 * This method initializes cbUnitWasteToZero
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbUnitWasteToZero() {
		if (cbUnitWasteToZero == null) {
			cbUnitWasteToZero = new JCheckBox();
			cbUnitWasteToZero
					.setBounds(new java.awt.Rectangle(130, 41, 111, 23));
			cbUnitWasteToZero.setText("成品单耗置零");
		}
		return cbUnitWasteToZero;
	}

	/**
	 * 初始化正在执行的合同
	 * 
	 * @param list
	 */
	private void initTb1(List dataSource) {
		new JTableListModel(tb1, dataSource, new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("流水号", "seqNum", 50));
				list.add(addColumn("合同号", "ieContractNo", 100));
				list.add(addColumn("手册编号", "emsNo", 100));
				list.add(addColumn("合同状态", "declareState", 100));
				list.add(addColumn("起始日期", "beginDate", 100));
				list.add(addColumn("结束日期", "endDate", 100));
				list.add(addColumn("核销期限", "destroyDate", 100));
				list.add(addColumn("合同类型", "bargainType.name", 100));
				return list;
			}
		});
		tb1.getColumnModel().getColumn(4).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue1(value));
						return this;
					}

					private String castValue1(Object value) {
						String returnValue = "";
						if (String.valueOf(value).trim().equals("")) {
							return "";
						}
						if (value.equals(DzscState.APPLY)) {
							returnValue = "正在申请";
						} else if (value.equals(DzscState.EXECUTE)) {
							returnValue = "正在执行";
						} else if (value.equals(DzscState.CHANGE)) {
							returnValue = "正在变更";
						}
						return returnValue;
					}
				});
	}

	/**
	 * 初始化成品或者料件表
	 * 
	 * @param dataSource
	 * @param isMateriel
	 */
	private void initTb2(List dataSource, final boolean isMateriel) {
		new JTableListModel(tb2, dataSource, new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				if (isMateriel) {
					list.add(addColumn("序号", "no", 30));
					list.add(addColumn("海关编码", "complex", 80));
					list.add(addColumn("料件名称", "name", 100));
					list.add(addColumn("型号规格", "spec", 100));
					list.add(addColumn("单价", "price", 40));
					list.add(addColumn("数量", "amount", 40));
					list.add(addColumn("金额", "money", 40));
					list.add(addColumn("单位", "unit.name", 40));
					list.add(addColumn("单位重量", "unitWeight", 80));
					list.add(addColumn("总重量", "weight", 70));
					list.add(addColumn("原产地", "country.name", 70));
					list.add(addColumn("合同序号", "seqNum", 80));
					list.add(addColumn("征免方式", "levyMode.name", 80));
				} else {
					list.add(addColumn("序号", "no", 30));
					list.add(addColumn("商品编码", "complex", 80));
					list.add(addColumn("商品名称", "name", 100));
					list.add(addColumn("型号规格", "spec", 100));
					list.add(addColumn("出口数量", "amount", 80));
					list.add(addColumn("单位", "unit.name", 40));
					list.add(addColumn("单价", "price", 40));
					list.add(addColumn("总额", "money", 40));
					list.add(addColumn("原料费", "imgMoney", 50));
					list.add(addColumn("消费国", "country.name", 70));
					list.add(addColumn("加工费单价", "machinPrice", 100));
					list.add(addColumn("补充说明", "note", 80));
					list.add(addColumn("单位净重", "unitNetWeight", 80));
					list.add(addColumn("单位毛重", "unitGrossWeight", 80));
					list.add(addColumn("合同序号", "seqNum", 80));
					list.add(addColumn("征免方式", "levyMode.name", 80));
				}
				return list;
			}
		});
		tb2.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	}

	/**
	 * 转抄单据
	 * 
	 * @param isOverprint
	 */
	private void copyBill() {
		JTableListModel dataModel = (JTableListModel) tb2.getModel();
		if (dataModel.getCurrentRow() == null) {
			JOptionPane.showMessageDialog(this, "请选择要转抄的记录!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		List list = dataModel.getCurrentRows();
		if (this.rbProductMaterielUnitWaste.isSelected()) { // 转抄成品,料件,单耗
			dzscAction.copyProductMaterielUnitWaste(new Request(CommonVars
					.getCurrUser()), list,head, this.cbProductAmountToZero
					.isSelected(), this.cbProductPriceToZero.isSelected(),
					this.cbMaterielAmountToZero.isSelected(),
					this.cbMaterielPriceToZero.isSelected(),
					this.cbUnitWasteToZero.isSelected());
		} else if (this.rbProduct.isSelected()) { // 转抄成品
			dzscAction.copyProduct(new Request(CommonVars.getCurrUser()), list,head,
					this.cbProductAmountToZero.isSelected(),
					this.cbProductPriceToZero.isSelected());
		} else if (this.rbMateriel.isSelected()) {// 转抄料件
			dzscAction.copyMateiel(new Request(CommonVars.getCurrUser()), list,head,
					this.cbMaterielAmountToZero.isSelected(),
					this.cbMaterielPriceToZero.isSelected());
		}
	}

	/**
	 * 显示数据
	 * 
	 */
	private void showData() {
		JTableListModel dataModel = (JTableListModel) tb1.getModel();
		DzscEmsPorHead head = (DzscEmsPorHead) dataModel.getCurrentRow();
		if (head == null) {
			return;
		}
		if (this.rbMateriel.isSelected()) { // 显示料件
			List list = dzscAction.findEmsPorImgBill(new Request(CommonVars
					.getCurrUser()), head);
			initTb2(list, true);
		} else {// 显示成品
			List list = dzscAction.findEmsPorExgBill(new Request(CommonVars
					.getCurrUser()), head);
			initTb2(list, false);
		}
	}
}


/*滚动条摸版
 * 
  
  class find extends Thread{	
	public void run(){
		 try {
            CommonProgress.showProgressDialog(dg.this);
            CommonProgress.setMessage("系统正获取数据，请稍后...");
            
            CommonProgress.closeProgressDialog();
		 } catch (Exception e) {
	        CommonProgress.closeProgressDialog();
	        JOptionPane.showMessageDialog(dg.this,
	                "获取数据失败：！" + e.getMessage(), "提示", 2);    
		 } 
	}
  }

*/
