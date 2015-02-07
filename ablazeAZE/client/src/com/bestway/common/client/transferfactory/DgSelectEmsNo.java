package com.bestway.common.client.transferfactory;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.table.TableColumn;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.transferfactory.action.TransferFactoryManageAction;
import com.bestway.common.transferfactory.entity.TempCustomsEmsInfo;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgSelectEmsNo extends JDialogBase {

	private JPanel jContentPane = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;

	private JPanel jPanel = null;

	private JTableListModel tableModel = null;

	private TransferFactoryManageAction transferFactoryManageAction = null;

	private int projectType;

	private String oldEmsNo; // @jve:decl-index=0:

	private List lsResult=new ArrayList();

	private boolean isOk = false;

	private boolean canEdit = false;

	private boolean isImpExpGoods = true;
	public boolean isCanEdit() {
		return canEdit;
	}

	public void setCanEdit(boolean canEdit) {
		this.canEdit = canEdit;
	}

	public boolean isOk() {
		return isOk;
	}

	public List getLsResult() {
		return lsResult;
	}

	public int getProjectType() {
		return projectType;
	}

	public void setProjectType(int projectType) {
		this.projectType = projectType;
	}

	public String getOldEmsNo() {
		return oldEmsNo;
	}

	public void setOldEmsNo(String emsNo) {
		this.oldEmsNo = emsNo;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgSelectEmsNo() {
		super();
		initialize();
		transferFactoryManageAction = (TransferFactoryManageAction) CommonVars
				.getApplicationContext().getBean("transferFactoryManageAction");
	}

	@Override
	public void setVisible(boolean b) {
		if (b) {
			List dataSource = transferFactoryManageAction
					.findEmsHeadByProjectType(new Request(CommonVars
							.getCurrUser(), true), projectType);

			if (oldEmsNo != null && !"".equals(oldEmsNo.trim())) {
				String[] codes = oldEmsNo.split(";");
				List lsTemp = new ArrayList();
				for (int i = 0; i < codes.length; i++) {
					lsTemp.add(codes[i]);
				}
				for (int i = 0; i < dataSource.size(); i++) {
					TempCustomsEmsInfo temp = (TempCustomsEmsInfo) dataSource
							.get(i);
					if (lsTemp.contains(temp.getEmsNo())) {
						temp.setIsSelect(true);
					}
				}
			}
			initTable(dataSource);
			this.jButton.setEnabled(canEdit);
		}
		super.setVisible(b);
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(397, 246));
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());

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
			jContentPane.add(getJScrollPane(), BorderLayout.CENTER);
			jContentPane.add(getJPanel(), BorderLayout.SOUTH);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("确定");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List dataSource = tableModel.getList();
					for (int i = 0; i < dataSource.size(); i++) {
						TempCustomsEmsInfo temp = (TempCustomsEmsInfo) dataSource
								.get(i);
						if (temp.getIsSelect() != null && temp.getIsSelect()) {
							lsResult.add(temp);
						}
					}
					isOk = true;
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
			jButton1.setText("关闭");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton1;
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

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.gridy = 0;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 1;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.insets = new Insets(0, 20, 0, 0);
			jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());
			jPanel.add(getJButton1(), gridBagConstraints);
			jPanel.add(getJButton(), gridBagConstraints1);
		}
		return jPanel;
	}

	/**
	 * 
	 * 初始化表格对象
	 */
	private void initTable(List list) {
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			@Override
			public List InitColumns() {
				String sysType = "手册";
				switch (projectType) {
				case ProjectType.BCS:
					sysType = "手册";
					break;
				case ProjectType.BCUS:
					sysType = "账册";
					break;
				case ProjectType.DZSC:
					sysType = "手册";
					break;
				}
				List list = new Vector();
				list.add(addColumn("是否选择", "isSelect", 60));
				list.add(addColumn(sysType + "号", "emsNo", 150));
				return list;
			}
		};
		tableModel = new JTableListModel(jTable, list, jTableListModelAdapter);
		TableColumn column = jTable.getColumnModel().getColumn(1);
		column.setCellRenderer(new TableCheckBoxRender());
		if (canEdit) {
			jTableListModelAdapter.setEditableColumn(1);
			column.setCellEditor(new CheckBoxEditor(new JCheckBox()));
		}
	}
	
	/**
	 * 编辑列
	 */
	class CheckBoxEditor extends DefaultCellEditor implements ActionListener {
		private JCheckBox cb;

		private JTable table = null;

		public CheckBoxEditor(JCheckBox checkBox) {
			super(checkBox);
		}

		@Override
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
			cb.setHorizontalAlignment(SwingConstants.CENTER);
			cb.addActionListener(this);
			this.table = table;
			return cb;
		}

		@Override
		public Object getCellEditorValue() {
			cb.removeActionListener(this);
			return cb;
		}

		public void actionPerformed(ActionEvent e) {
			JTableListModel tableModel = (JTableListModel) this.table
					.getModel();
			Object obj = tableModel.getCurrentRow();
			List templist = tableModel.getList();

			if (obj instanceof TempCustomsEmsInfo) {
				TempCustomsEmsInfo temp = (TempCustomsEmsInfo) obj;	
			
				if(!isImpExpGoods){
				for(int i = 0; i< templist.size();i++){
					TempCustomsEmsInfo tempinfo = (TempCustomsEmsInfo)templist.get(i);
					if(tempinfo.getIsSelect() == true && tempinfo.getIsSelect() != temp.getIsSelect()){
						cb.setSelected(!cb.isSelected());
						tableModel.updateRow(obj);
						JOptionPane.showMessageDialog(DgSelectEmsNo.this, "只能选择一本手册/帐册","提示",JOptionPane.INFORMATION_MESSAGE);
						return ;
					}
				}
				}
				temp.setIsSelect(new Boolean(cb.isSelected()));
				tableModel.updateRow(obj);
			}
			fireEditingStopped();
		}
	}

	public boolean isImpExpGoods() {
		return isImpExpGoods;
	}

	public void setImpExpGoods(boolean isImpExpGoods) {
		this.isImpExpGoods = isImpExpGoods;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
