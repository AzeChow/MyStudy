/*
 * Created on 2005-6-8
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.cas.specificontrol;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.bill.entity.BillTempMaster;
import com.bestway.bcus.cas.entity.BillType;
import com.bestway.bcus.client.cas.parameter.CasCommonVars;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author ls
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgEditBillPrice extends JDialogBase {

	private JPanel					jContentPane						= null;
	private JPanel					pn1									= null;
	private CasAction				casAction							= null;
	private JTableListModel			tableModelBillMaster				= null;
	private JTableListModel			tableModelBillDetail				= null;
	private JToolBar				jToolBar							= null;
	private JButton					bnShow								= null; // 显示
	private JTable					jTableBillMaster					= null;
	private JScrollPane				jScrollPane							= null;
	private JTable					jTableBillDetail					= null;
	private JScrollPane				jScrollPane1						= null;
	private JCheckBox				cbSelectAll							= null; // 全选
	private JComboBox				cbMostlyBillType					= null; // 单据大类
	private JComboBox				cbBillType							= null; // 单据类型
	private JButton					bnModify							= null; // 修改

	private JTableListModelAdapter	jTableListModelAdapterBillMaster	= null;
	private JTableListModelAdapter	jTableListModelAdapterBillDetail	= null;
	private JSplitPane				jSplitPane							= null;
	private JPanel					jPanel								= null;
	private JToolBar				jToolBar1							= null;
	private JPanel					jPanel1								= null;
	private JButton					jButton								= null;
	private JLabel					jLabel1								= null;
	private JLabel jLabel = null;
	/**
	 * 开始日期
	 */
	private JCalendarComboBox cbbBeginDate = null;

	/**
	 * 结束日期
	 */
	private JCalendarComboBox cbbEndDate = null;
	private Integer 		   		maximumFractionDigits 	= CasCommonVars.getOtherOption()
															.getInOutMaximumFractionDigits() == null ? 6 : CasCommonVars
															.getOtherOption().getInOutMaximumFractionDigits();
	private JButton jButton1 = null;
	private JButton jButton2 = null;

	/**
	 * This method initializes
	 * 
	 */
	public DgEditBillPrice() {
		super(false);
		initialize();
		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
				"casAction");
		initJTable();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("修改单据单价");
		this.setContentPane(getJContentPane());
		this.setSize(755, 539);
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
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
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
			pn1.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
			pn1.add(getJToolBar1(), java.awt.BorderLayout.NORTH);
		}
		return pn1;
	}

	/**
	 * 初始化两个Table
	 * 
	 */
	private void initJTable() {
		initJTableBillMaster();
		initJTableBillDetail();

	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getBnShow());			
			jToolBar.add(getBnModify());
			jToolBar.add(getCbSelectAll());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBnShow() {
		if (bnShow == null) {
			bnShow = new JButton();
			bnShow.setText("显示");
			bnShow.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					fillJTableBillDetail();
				}
			});

		}
		return bnShow;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTableBillMaster() {
		if (jTableBillMaster == null) {
			jTableBillMaster = new JTable();
		}
		return jTableBillMaster;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTableBillMaster());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTable1
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTableBillDetail() {
		if (jTableBillDetail == null) {
			jTableBillDetail = new JTable();
		}
		return jTableBillDetail;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJTableBillDetail());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jCheckBox2
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbSelectAll() {
		if (cbSelectAll == null) {
			cbSelectAll = new JCheckBox();
			cbSelectAll.setText("全选");
			cbSelectAll.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (cbSelectAll.isSelected() == true)
						selectAll(true);
					else
						selectAll(false);
				}
			});
		}
		return cbSelectAll;
	}

	/**
	 * 全选或取消全选
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private void selectAll(boolean isSelected) {
		if (this.tableModelBillMaster == null) {
			return;
		}
		List list = tableModelBillMaster.getList();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) instanceof BillTempMaster) {
				BillTempMaster t = (BillTempMaster) list.get(i);
				t.setIsSelected(new Boolean(isSelected));
			}
		}
		tableModelBillMaster.updateRows(list);
	}

	/**
	 * This method initializes jComboBox2
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbMostlyBillType() {
		if (cbMostlyBillType == null) {
			cbMostlyBillType = new JComboBox();
			cbMostlyBillType.addItem(new ItemProperty(MaterielType.MATERIEL,
					"料件"));
			cbMostlyBillType.addItem(new ItemProperty(
					MaterielType.FINISHED_PRODUCT, "成品"));
			cbMostlyBillType.addItem(new ItemProperty(MaterielType.MACHINE,
					"设备"));
			cbMostlyBillType.addItem(new ItemProperty(
					MaterielType.SEMI_FINISHED_PRODUCT, "半成品"));
			cbMostlyBillType.addItem(new ItemProperty(MaterielType.BAD_PRODUCT,
					"残次品"));
			cbMostlyBillType.addItem(new ItemProperty(
					MaterielType.REMAIN_MATERIEL, "边角料"));
			cbMostlyBillType.setSelectedItem(null);
			cbMostlyBillType.setPreferredSize(new java.awt.Dimension(128, 24));
			cbMostlyBillType.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					List<BillType> billType = casAction
							.findBillTypeByProduceType(new Request(CommonVars
									.getCurrUser(), true),
									((ItemProperty) cbMostlyBillType
											.getSelectedItem()).getCode());
					if (cbBillType.getSelectedItem() != null)
						cbBillType.removeAllItems();
					for (BillType bt : billType)
						cbBillType.addItem(new ItemProperty(bt.getCode(), bt
								.getName()));

				}
			});
		}

		return cbMostlyBillType;
	}

	/**
	 * This method initializes jComboBox3
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbBillType() {
		if (cbBillType == null) {
			cbBillType = new JComboBox();
			cbBillType.setPreferredSize(new java.awt.Dimension(128, 24));
			cbBillType.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					fillJTableBillMaster();
					initJTableBillDetail();
					cbSelectAll.setSelected(false);

				}
			});
		}
		return cbBillType;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBnModify() {
		if (bnModify == null) {
			bnModify = new JButton();
			bnModify.setText("修改");
			bnModify.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (tableModelBillDetail == null
							|| tableModelBillDetail.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgEditBillPrice.this,
								"请选择一条数据", "提示", 0);
						return;
					}
					int i=tableModelBillDetail.getCurrRowCount();
					DgModifyBillPrice dgModifyBillPrice = new DgModifyBillPrice(
							((ItemProperty) cbMostlyBillType.getSelectedItem())
									.getName());
					dgModifyBillPrice.setTableModel(tableModelBillDetail);
					dgModifyBillPrice.initUIComponents();
					dgModifyBillPrice.setVisible(true);
					tableModelBillDetail.updateRows(fillJTableBillDetail());
					tableModelBillDetail.setTableSelectRowByRow(i+1);
				}
			});
		}
		return bnModify;
	}

	private void fillJTableBillMaster() {
		List list = new ArrayList();
		List listBillMaster = new ArrayList();
		String billTypeName, mostlyBillTypeName;
		if (cbBillType.getSelectedItem() == null) {
			billTypeName = null;
			mostlyBillTypeName = null;
			list = null;
			return;
		} else {
			billTypeName = ((ItemProperty) cbBillType.getSelectedItem())
					.getName();
			mostlyBillTypeName = ((ItemProperty) cbMostlyBillType
					.getSelectedItem()).getName();
			list = this.casAction
					.findBillTempDetailByBillTypeName(new Request(CommonVars
							.getCurrUser()), mostlyBillTypeName, billTypeName,cbbBeginDate.getDate(),cbbEndDate.getDate());
			// for(int i=0;i<list.size();i++){
			// if(((BillDetail)list.get(i)).)

			// }

		}

		jTableListModelAdapterBillMaster.setEditableColumn(1);
		tableModelBillMaster = new JTableListModel(jTableBillMaster, list,
				jTableListModelAdapterBillMaster);
		jTableBillMaster.getColumnModel().getColumn(1).setCellRenderer(
				new TableCheckBoxRender());
		jTableBillMaster.getColumnModel().getColumn(1).setCellEditor(
				new CheckBoxEditor(new JCheckBox()));
	}

	private void initJTableBillMaster() {
		List list = new ArrayList();
		jTableListModelAdapterBillMaster = new JTableListModelAdapter() {
			public List<JTableListColumn> InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("选择", "isSelected", 50));
				list.add(addColumn("单据类型", "billMaster.billType.name", 100));
				list.add(addColumn("单据号", "billMaster.billNo", 100));
				list.add(addColumn("客户名称", "billMaster.scmCoc.name", 150));
				list.add(addColumn("生效日期", "billMaster.validDate", 80));
				// list.add(addColumn("仓库", "billDetail.wareSet.name", 80));
				// list.add(addColumn("备注", "billDetail.note", 80));
				return list;
			}
		};
		tableModelBillMaster = new JTableListModel(jTableBillMaster, list,
				jTableListModelAdapterBillMaster);
	}

	private List fillJTableBillDetail() {
		List list = new ArrayList();
		List listA;
		if (this.tableModelBillMaster == null) {
			list = null;
		} else {
			List list2 = tableModelBillMaster.getList();
			for (int i = 0; i < list2.size(); i++) {
				BillTempMaster t = (BillTempMaster) list2.get(i);
				if (t.getIsSelected() == true)
					list.add(t);
			}
		}
		listA = casAction.findBillDetailByBillTypeName(new Request(CommonVars
				.getCurrUser()), list, ((ItemProperty) cbMostlyBillType
				.getSelectedItem()).getName());
		tableModelBillDetail = new JTableListModel(jTableBillDetail, listA,
				jTableListModelAdapterBillDetail);
		return listA;
	}

	private void initJTableBillDetail() {
		List list = new ArrayList();
		jTableListModelAdapterBillDetail = new JTableListModelAdapter(maximumFractionDigits) {
			public List<JTableListColumn> InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("单据号", "billMaster.billNo", 100));
				list.add(addColumn("商品编号", "complex.code", 100));
				list.add(addColumn("产品BOM编码", "ptPart", 80));
				list.add(addColumn("商品名称", "ptName", 120));
				list.add(addColumn("商品规格", "ptSpec", 80));
				list.add(addColumn("单位", "ptUnit.name", 80));
				list.add(addColumn("单价", "ptPrice", 80));
				list.add(addColumn("合同单价", "hsPrice", 80));
				return list;
			}
		};
		tableModelBillDetail = new JTableListModel(jTableBillDetail, list,
				jTableListModelAdapterBillDetail);

	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setPreferredSize(new java.awt.Dimension(250, 41));
			jSplitPane.setDividerSize(5);
			jSplitPane.setDividerLocation(250);
			jSplitPane.setBottomComponent(getJPanel());
			jSplitPane.setTopComponent(getPn1());
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jPanel.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * This method initializes jToolBar1
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar1() {
		if (jToolBar1 == null) {
			jToolBar1 = new JToolBar();
			jToolBar1.add(getJPanel1());
		}
		return jToolBar1;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLabel = new JLabel();
			jLabel.setText("物料类型");
			jLabel1 = new JLabel();
			jLabel1.setText("单据类型");
			jPanel1 = new JPanel();
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setAlignment(java.awt.FlowLayout.LEFT);
			flowLayout.setVgap(5);
			jPanel1.setLayout(flowLayout);
			jPanel1.add(jLabel, null);
			jPanel1.add(getCbMostlyBillType(), null);
			jPanel1.add(jLabel1, null);
			jPanel1.add(getCbBillType(), null);
			jPanel1.add(getCbbBeginDate(), null);
			jPanel1.add(getCbbEndDate(), null);
			jPanel1.add(getJButton2(), null);
			jPanel1.add(getJButton(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("关闭");
			jButton.setPreferredSize(new java.awt.Dimension(58, 25));
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton;
	}
	/**
	 * This method initializes cbbBeginDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();			
//			cbbBeginDate.setPreferredSize(new Dimension(58, 25));
			cbbBeginDate.setPreferredSize(new java.awt.Dimension(100, 24));
			cbbBeginDate.setDate(null);
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
			cbbEndDate.setPreferredSize(new java.awt.Dimension(100, 24));
//			cbbEndDate.setDate(new Date());
		}
		return cbbEndDate;
	}

	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(new Rectangle(0, 0, 0, 0));
		}
		return jButton1;
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
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					fillJTableBillMaster();
					initJTableBillDetail();
				}
			});
		}
		return jButton2;
	}

	// private void fillData(){
	// int index=-1;
	// if(jComboBox2.getSelectedIndex()==0)
	// index=SBillType.MATERIEL_INOUT;
	// else if(jComboBox2.getSelectedIndex()==1)
	// index=SBillType.PRODUCE_INOUT;
	// else if(jComboBox2.getSelectedIndex()==2)
	// index=SBillType.FIXTURE_INOUT;
	// else if(jComboBox2.getSelectedIndex()==3)
	// index=SBillType.HALF_PRODUCE_INOUT;
	// else if(jComboBox2.getSelectedIndex()==4)
	// index=SBillType.REMAIN_PRODUCE_INOUT;
	// else if(jComboBox2.getSelectedIndex()==5)
	// index=SBillType.LEFTOVER_MATERIEL_INOUT;
	// billComboBox3Data(index);
	// }
	//	
	// private void billComboBox3Data(int index){
	// CasAction casAction;
	// casAction = (CasAction) CommonVars.getApplicationContext().getBean(
	// "casAction");
	// Vector vector = new Vector();
	// List billTypes = casAction.findBillType(new Request(CommonVars
	// .getCurrUser()), index);
	// for (int i = 0; i < billTypes.size(); i++) {
	// BillType billType = (BillType) billTypes.get(i);
	// vector.add(billType);
	// }
	// jComboBox3.addItem(vector);
	// }
	// casAction = (CasAction) CommonVars.getApplicationContext().getBean(
	// "casAction");
	// List list = casAction.findBillType(new Request(CommonVars
	// .getCurrUser()), index);
	// jComboBox3.removeAllItems();
	//		
	//
	// jComboBox3.setModel(new DefaultComboBoxModel(list.toArray()));
	// jComboBox3.setRenderer(CustomBaseRender.getInstance().getRender(
	// "code","name"));
	// CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
	// this.jComboBox3, "code", "name");

	// }

} // @jve:decl-index=0:visual-constraint="12,46"

/**
 * render table JchcckBox 列
 */
class MultiRenderer extends DefaultTableCellRenderer {
	JCheckBox	checkBox	= new JCheckBox();

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
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

/**
 * 编辑列
 */
class CheckBoxEditor extends DefaultCellEditor implements ActionListener {
	private JCheckBox	cb;
	private JTable		table	= null;

	public CheckBoxEditor(JCheckBox checkBox) {
		super(checkBox);
	}

	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		if (value != null) {
			// return null;
			if (Boolean.valueOf(value.toString()) instanceof Boolean) {
				cb = new JCheckBox();
				cb
						.setSelected(Boolean.valueOf(value.toString())
								.booleanValue());
			}
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
		JTableListModel tableModel = (JTableListModel) this.table.getModel();
		Object obj = tableModel.getCurrentRow();
		if (obj instanceof BillTempMaster) {
			BillTempMaster temp = (BillTempMaster) obj;
			temp.setIsSelected(new Boolean(cb.isSelected()));
			tableModel.updateRow(obj);
		}
		fireEditingStopped();
	}
	
	
}
