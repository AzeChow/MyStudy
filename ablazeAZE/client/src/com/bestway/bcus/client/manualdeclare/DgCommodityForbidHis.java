package com.bestway.bcus.client.manualdeclare;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.BomStructureType;
import com.bestway.common.materialbase.entity.EnterpriseMaterial;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

@SuppressWarnings("unchecked")
public class DgCommodityForbidHis extends JDialogBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;

	private MaterialManageAction materialManageAction = null;
	
	private ManualDeclareAction manualDecleareAction = null;

	private int bomStructureType = -1;

	private List lsResult = new ArrayList();  //  @jve:decl-index=0:

	private Integer version = null;

	private JTableListModel tableModel = null;

	private JPanel jPanel = null;

	private JLabel jLabel1 = null;

	private JCalendarComboBox jCalendarComboBox = null;

	private JLabel jLabel2 = null;

	private JCalendarComboBox jCalendarComboBox1 = null;

	private JLabel jLabel3 = null;

	private JComboBox jComboBox = null;

	private JButton jButton2 = null;

	private JLabel lbSerialNo = null;

	private JTextField tfSerialNo = null;

	private JButton btnClose = null;

	public int getBomStructureType() {
		return bomStructureType;
	}

	public void setBomStructureType(int bomStructureType) {
		this.bomStructureType = bomStructureType;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public List getLsResult() {
		return lsResult;
	}

	public void setLsResult(List lsResult) {
		this.lsResult = lsResult;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgCommodityForbidHis() {
		super();
		initialize();
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		manualDecleareAction = (ManualDeclareAction) CommonVars
        .getApplicationContext().getBean("manualdeclearAction");
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(893, 502));
		this.setTitle("BOM成品");
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.jComboBox.addItem("料件");
		this.jComboBox.addItem("成品");
	}

	public void setVisible(boolean b) {
		if (b) {
			this.showData();
		}
		super.setVisible(b);
	}

	private void showData() {
		List list = new ArrayList();
		if (this.bomStructureType == BomStructureType.NO_VERSION_NO_DATE
				&& version != null) {
			list = materialManageAction.findTopProductBomManage(new Request(
					CommonVars.getCurrUser(), true), version);
		} else if (this.bomStructureType == BomStructureType.HAVE_VERSION_NO_DATE) {
			list = materialManageAction.findTopProductBomManageForVersion(new Request(
					CommonVars.getCurrUser(), true));
		} else if (this.bomStructureType == BomStructureType.NO_VERSION_HAS_DATE) {
			list = materialManageAction.findTopProductBomManage(new Request(
					CommonVars.getCurrUser(), true));
		}
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("是否选择", "isSelected", 50));
				list.add(addColumn("类别(0或1)", "scmCoi.coiProperty", 100));//wss:2010.05.10新增:为区分成品与半成品
				list.add(addColumn("料号", "ptNo", 100));
				list.add(addColumn("商品编码", "complex.code", 80));
				list.add(addColumn("工厂商品名称", "factoryName", 100));
				list.add(addColumn("工厂型号规格", "factorySpec", 100));
				list.add(addColumn("单位", "calUnit.name", 50));
				// list.add(addColumn("商品名称", "ptName", 100));
				// list.add(addColumn("型号规格", "ptSpec", 100));
				list.add(addColumn("单价", "ptPrice", 50));
				list.add(addColumn("净重", "ptNetWeight", 50));
				// list.add(addColumn("状态标志", "stateMark", 50));
				return list;
			}
		};
		
		jTableListModelAdapter.setEditableColumn(1);
		tableModel = new JTableListModel(jTable, list, jTableListModelAdapter);
		
		jTable.getColumnModel().getColumn(2).setCellRenderer(
				new DefaultTableCellRenderer() {
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText("0".equals(value)?"成品":"半成品");
		                return this;
					}
				});
		
		TableColumn column = this.getJTable().getColumnModel().getColumn(1);
		column.setCellRenderer(new TableCheckBoxRender());
		column.setCellEditor(new CheckBoxEditor(new JCheckBox()));		
	}

	/**
	 * 编辑列
	 */
	class CheckBoxEditor extends DefaultCellEditor implements ActionListener {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

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
			List<Object> objs = tableModel.getCurrentRows();
			for(Object obj : objs){
				if (obj instanceof EnterpriseMaterial) {
					EnterpriseMaterial temp = (EnterpriseMaterial) obj;
					temp.setIsSelected(new Boolean(cb.isSelected()));
					tableModel.updateRow(obj);
				}
			}
			fireEditingStopped();
		}
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
			jContentPane.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
			jContentPane.add(getJPanel(), BorderLayout.NORTH);
		}
		return jContentPane;
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
		}
		return jTable;
	}

	private void selectAll(boolean isSelected) {
		List list = tableModel.getList();
		for (int i = 0; i < list.size(); i++) {
			EnterpriseMaterial enterpriseMaterial = (EnterpriseMaterial) list
					.get(i);
			enterpriseMaterial.setIsSelected(isSelected);
		}
		// tableModel.updateRows(list);
		tableModel.setList(list);

	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			lbSerialNo = new JLabel();
			lbSerialNo.setBounds(new Rectangle(170, 4, 54, 24));
			lbSerialNo.setText("\u8d26\u518c\u5e8f\u53f7");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new java.awt.Rectangle(9,5,32,23));
			jLabel3.setText("\u7c7b\u578b");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new java.awt.Rectangle(480,4,23,23));
			jLabel2.setText("\u81f3");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new java.awt.Rectangle(320,4,58,24));
			jLabel1.setText("\u7981\u7528\u65f6\u95f4");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setPreferredSize(new Dimension(800, 30));
			jPanel.add(jLabel1, null);
			jPanel.add(getJCalendarComboBox(), null);
			jPanel.add(jLabel2, null);
			jPanel.add(getJCalendarComboBox1(), null);
			jPanel.add(jLabel3, null);
			jPanel.add(getJComboBox(), null);
			jPanel.add(getJButton2(), null);
			jPanel.add(lbSerialNo, null);
			jPanel.add(getTfSerialNo(), null);
			jPanel.add(getBtnClose(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jCalendarComboBox	
	 * 	
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox	
	 */
	private JCalendarComboBox getJCalendarComboBox() {
		if (jCalendarComboBox == null) {
			jCalendarComboBox = new JCalendarComboBox();
			jCalendarComboBox.setBounds(new java.awt.Rectangle(380,4,98,23));
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
			jCalendarComboBox1.setBounds(new java.awt.Rectangle(505,3,97,24));
		}
		return jCalendarComboBox1;
	}

	/**
	 * This method initializes jComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setBounds(new java.awt.Rectangle(48,4,112,24));
		}
		return jComboBox;
	}

	/**
	 * This method initializes jButton2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setBounds(new Rectangle(690, 5, 80, 21));
			jButton2.setText("查询");
			jButton2.setPreferredSize(new Dimension(64, 30));
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jComboBox.getSelectedItem() != null
							&& jComboBox.getSelectedItem().equals("料件")) {
						findList(true);
					} else if (jComboBox.getSelectedItem() != null
							&& jComboBox.getSelectedItem().equals("成品")) {
						findList(false);
					}
				}
			});
		}
		return jButton2;
	}
	
	private void findList(boolean isMateriel) {
		Date begindate = jCalendarComboBox.getDate();
		Date enddate = jCalendarComboBox1.getDate();
		String serialNo = tfSerialNo.getText();
		List list = manualDecleareAction.findCommdityForbidHis(new Request(
				CommonVars.getCurrUser()), isMateriel,serialNo, begindate, enddate);
		initTable(list);
	}
	
	
	public void setVisible(String type, Date begindate, Date enddate, String serialNo) {
		jCalendarComboBox.setDate(begindate);
		jCalendarComboBox1.setDate(enddate);
		tfSerialNo.setText(serialNo);
		jComboBox.setSelectedItem(type);
		this.findList("料件".equals(type));
		super.setVisible(true);
	}
	/**
	 * 初始化数据Table
	 */
	private void initTable(List list) {
		if (list == null) {
			list = new ArrayList();
		}
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("帐册序号", "seqNum", 60));
						list.add(addColumn("成品版本", "version", 60));
						list.add(addColumn("商品编码", "complex", 80));
						list.add(addColumn("商品名称", "name", 120));
						list.add(addColumn("型号规格", "spec", 120));
						list.add(addColumn("单位", "unit", 50));
						list.add(addColumn("禁止时间", "forbiddate", 80));
						list.add(addColumn("禁止人", "forbiduser", 60));
						list.add(addColumn("恢复时间", "revertdate", 80));
						list.add(addColumn("恢复人", "revertuser", 80));
						return list;
					}
				});
	}

	/**
	 * This method initializes tfSerialNo	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfSerialNo() {
		if (tfSerialNo == null) {
			tfSerialNo = new JTextField();
			tfSerialNo.setBounds(new Rectangle(230, 4, 74, 24));
		}
		return tfSerialNo;
	}

	/**
	 * This method initializes btnClose	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setBounds(new Rectangle(780, 5, 80, 21));
			btnClose.setText("关闭");
			btnClose.setPreferredSize(new Dimension(64, 30));
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnClose;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
