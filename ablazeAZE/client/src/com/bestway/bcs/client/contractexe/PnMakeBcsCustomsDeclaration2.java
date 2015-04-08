/*
 * Created on 2004-8-16
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.client.contractexe;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.enc.entity.ImpExpRequestBill;
import com.bestway.bcus.enc.entity.TempImpExpRequestBill;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JPanelBase;

/**
 * 选取申请单面板 check by hcl 2011-01-07
 * 
 */
public class PnMakeBcsCustomsDeclaration2 extends JPanelBase {
	/**
	 * 选取申请单面板
	 */
	private PnMakeBcsCustomsDeclaration2 pnMakeCustomsEnvelopBill2 = null;
	/**
	 * 进出口类型
	 */
	private int impExpType = -1;
	/**
	 * 表格Model
	 */
	private JTableListModel tableModel = null;
	/**
	 * 表格
	 */
	private JTable tbTable = null;
	/**
	 * 表格面板
	 */
	private JScrollPane spTable = null;
	/**
	 * 选择所有按钮
	 */
	private JButton btnSelectAll = null;
	/**
	 * 全否按钮
	 */
	private JButton btnNotSelectAll = null;
	/**
	 * 申请单与清单接口
	 */
	private EncAction encAction = null;
	/**
	 * 存放数据List
	 */
	private List list = null;
	/**
	 * 是否执行
	 */
	private boolean isexe = false;
	/**
	 * 申请单物料
	 */
	private ImpExpRequestBill headImpExpRequestBill = null;
	private JPanel jPanel = null;

	/**
	 * 构造方法
	 */
	public PnMakeBcsCustomsDeclaration2() {
		super();
		this.encAction = (EncAction) CommonVars.getApplicationContext()
				.getBean("encAction");
		initialize();
	}

	/**
	 * 可见方法
	 */
	public void setVisible(boolean isFlag) {
		if (isFlag == true) {
			list = this.encAction.findTempImpExpRequestBillByImpExpTypeToATC(
					new Request(CommonVars.getCurrUser()), this.impExpType);
			initTable(list);
			isexe = false;
			initCombox(list);
		}
		super.setVisible(isFlag);
		isexe = true;
	}

	/**
	 * 初始化Combox
	 */
	public void initCombox(List list) {
		// this.jComboBox.removeAllItems();
		// for (int i=0;i<list.size();i++){
		// TempImpExpRequestBill temp = (TempImpExpRequestBill) list.get(i);
		// if (temp.getImpExpRequestBill() == null){
		// continue;
		// }
		// this.jComboBox.addItem(temp.getImpExpRequestBill().getBillNo());
		// }
		// this.jComboBox.setSelectedIndex(-1);
	}

	/**
	 * 初始化组件
	 * 
	 */
	private void initialize() {
		this.setLayout(new BorderLayout());
		this.setSize(630, 248);
		this.add(getSpTable(), BorderLayout.CENTER);
		this.add(getJPanel(), BorderLayout.NORTH);
	}

	/**
	 * 获取进出口类型
	 */
	public int getImpExpType() {
		return impExpType;
	}

	/**
	 * 
	 * 设置进出口类型
	 */
	public void setImpExpType(int impExpType) {
		this.impExpType = impExpType;
	}

	/**
	 * 获取选择申请单面板
	 */
	public PnMakeBcsCustomsDeclaration2 getPnMakeCustomsEnvelopBill2() {
		return pnMakeCustomsEnvelopBill2;
	}

	/**
	 * 
	 * 设置选择申请单面板
	 */
	public void setPnMakeCustomsEnvelopBill2(
			PnMakeBcsCustomsDeclaration2 pnMakeCustomsEnvelopBill2) {
		this.pnMakeCustomsEnvelopBill2 = pnMakeCustomsEnvelopBill2;
	}

	/**
	 * 获取表格
	 */
	private JTable getTbTable() {
		if (tbTable == null) {
			tbTable = new JTable();
		}
		return tbTable;
	}

	/**
	 * 
	 * 获取全选按钮
	 */
	private JButton getBtnSelectAll() {
		if (btnSelectAll == null) {
			btnSelectAll = new JButton();
			btnSelectAll.setText("全选");
			btnSelectAll.setBounds(new Rectangle(475, 6, 60, 23));
			btnSelectAll.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					PnMakeBcsCustomsDeclaration2.this.setCursor(new Cursor(
							Cursor.WAIT_CURSOR));
					selectAll(true);
					PnMakeBcsCustomsDeclaration2.this.setCursor(new Cursor(
							Cursor.DEFAULT_CURSOR));

				}
			});
		}
		return btnSelectAll;
	}

	/**
	 * 
	 * 获取全否按钮
	 */
	private JButton getBtnNotSelectAll() {
		if (btnNotSelectAll == null) {
			btnNotSelectAll = new JButton();
			btnNotSelectAll.setText("不选");
			btnNotSelectAll.setBounds(new Rectangle(550, 6, 60, 24));
			btnNotSelectAll
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							PnMakeBcsCustomsDeclaration2.this
									.setCursor(new Cursor(Cursor.WAIT_CURSOR));
							selectAll(false);
							PnMakeBcsCustomsDeclaration2.this
									.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
						}
					});
		}
		return btnNotSelectAll;
	}

	/**
	 * 
	 * 获取表格面板
	 */
	private JScrollPane getSpTable() {
		if (spTable == null) {
			spTable = new JScrollPane();
			spTable.setBounds(2, 39, 649, 295);
			spTable.setViewportView(getTbTable());
		}
		return spTable;
	}

	/**
	 * 选中所有 or 清除所有选择
	 */
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
	}

	/**
	 * 初始化数据Table
	 */
	private void initTable(List list) {
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List list = new Vector();
				list.add(addColumn("选择", "isSelected", 50));
				list.add(addColumn("单据号", "impExpRequestBill.billNo", 100));
				list.add(addColumn("生效日期",
						"impExpRequestBill.beginAvailability", 70));
				list.add(addColumn("有效", "impExpRequestBill.isAvailability", 50));
				list.add(addColumn("已转报关单", "impExpRequestBill.isCustomsBill",
						80));
				list.add(addColumn("项目个数", "impExpRequestBill.itemCount", 60));
				list.add(addColumn("仓库名称", "impExpRequestBill.wareSet.name",
						100));
				list.add(addColumn("客户/供应商名称", "impExpRequestBill.scmCoc.name",
						150));
				list.add(addColumn("集装箱号码", "impExpRequestBill.containerCode",
						100));
				list.add(addColumn("运输工具", "impExpRequestBill.conveyance", 100));
				list.add(addColumn("运输方式", "impExpRequestBill.transfMode.name",
						80));
				list.add(addColumn("进出口岸", "impExpRequestBill.iePort.name", 60));
				list.add(addColumn("备注", "impExpRequestBill.memos", 150));
				list.add(addColumn("录入日期", "impExpRequestBill.imputDate", 80));
				return list;
			}
		};
		jTableListModelAdapter.setEditableColumn(1);
		tableModel = new JTableListModel(tbTable, list, jTableListModelAdapter);
		tbTable.getColumnModel().getColumn(1)
				.setCellRenderer(new MultiRenderer());
		tbTable.getColumnModel().getColumn(1)
				.setCellEditor(new CheckBoxEditor(new JCheckBox()));
		tbTable.getColumnModel().getColumn(4)
				.setCellRenderer(new MultiRenderer());
		tbTable.getColumnModel().getColumn(5)
				.setCellRenderer(new MultiRenderer());
	}

	/**
	 * render table JcheckBox 列 （用于将boolean 值转为JcheckBox）
	 */
	class MultiRenderer extends DefaultTableCellRenderer {
		JCheckBox checkBox = new JCheckBox();

		/**
		 * 重写getTableCellRendererComponent方法
		 */
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			if (value == null) {
				value = false;
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
	 * 编辑列 （用于表格JCheckBox的编辑）
	 */
	class CheckBoxEditor extends DefaultCellEditor implements ActionListener {
		private JCheckBox cb;
		private JTable table = null;

		/**
		 * 构造CheckBoxEditor内部类
		 * 
		 * @param checkBox
		 */
		public CheckBoxEditor(JCheckBox checkBox) {
			super(checkBox);
		}

		/**
		 * 编辑方法
		 */
		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {
			if (value == null) {
				return null;
			}
			if (Boolean.valueOf(value.toString()) instanceof Boolean) {
				cb = new JCheckBox();
				cb.setSelected(Boolean.valueOf(value.toString()).booleanValue());
			}
			cb.setHorizontalAlignment(JLabel.CENTER);
			cb.addActionListener(this);
			this.table = table;
			return cb;
		}

		/**
		 * 获取编辑值
		 */
		public Object getCellEditorValue() {
			cb.removeActionListener(this);
			return cb;
		}

		/**
		 * 触发方法
		 */
		public void actionPerformed(ActionEvent e) {
			JTableListModel tableModel = (JTableListModel) this.table
					.getModel();
			Object obj = tableModel.getCurrentRow();
			if (obj instanceof TempImpExpRequestBill) {
				TempImpExpRequestBill temp = (TempImpExpRequestBill) obj;
				temp.setIsSelected(new Boolean(cb.isSelected()));
				tableModel.updateRow(obj);
			}
			fireEditingStopped();
		}
	}

	/**
	 * 获得选中关封申请单中的信息
	 */
	public List getParentList() {

		if (this.tableModel == null) {

			return null;
		}
		List list = tableModel.getList();

		List newList = new ArrayList();

		for (int i = 0; i < list.size(); i++) {

			if (list.get(i) instanceof TempImpExpRequestBill) {

				TempImpExpRequestBill t = (TempImpExpRequestBill) list.get(i);

				ImpExpRequestBill impExpRequestBill = t.getImpExpRequestBill();

				if (impExpRequestBill != null) {

					String memos = impExpRequestBill.getMemos();

					if (memos != null) {

						impExpRequestBill.setMemos(getLimitLengthString(memos,
								210));

					}
				}
				if (t.getIsSelected().booleanValue() == true) {

					newList.add(t);
				}
			}
		}
		return newList;
	}

	/**
	 * 按长度截取字符，考虑汉字不被截断
	 * 
	 * @param str
	 * @param len
	 * @return
	 */
	public String getLimitLengthString(String str, int len) {
		try {
			int counterOfDoubleByte = 0;
			System.out.println("in Memos=" + str);
			byte[] b = str.getBytes("GBK");
			System.out.println("in Memos-----------");
			if (b.length <= len)
				return str;
			for (int i = 0; i < len; i++) {
				if (b[i] < 0)
					counterOfDoubleByte++;
			}
			if (counterOfDoubleByte % 2 == 0)
				return new String(b, 0, len, "GBK");
			else
				return new String(b, 0, len - 1, "GBK");
		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}
	}

	/**
	 * 验证
	 * 
	 * @return
	 */
	public boolean vaildateData() {
		if (this.getParentList() == null || this.getParentList().size() <= 0) {
			JOptionPane.showMessageDialog(this, "请选择进出口申请单据!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		return true;
	}

	/**
	 * 获取申请单头
	 * 
	 */
	public ImpExpRequestBill getHeadImpExpRequestBill() {
		return headImpExpRequestBill;
	}

	/**
	 * 设置申请单头
	 * 
	 */
	public void setHeadImpExpRequestBill(ImpExpRequestBill headImpExpRequestBill) {
		this.headImpExpRequestBill = headImpExpRequestBill;
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
			jPanel.setPreferredSize(new Dimension(40, 0));
			jPanel.setPreferredSize(new Dimension(40, 35));
			jPanel.add(getBtnSelectAll(), null);
			jPanel.add(getBtnNotSelectAll(), null);
		}
		return jPanel;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
