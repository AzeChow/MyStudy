package com.bestway.bcus.client.common;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;

import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.ui.winuicontrol.JDialogBase;

public abstract class NewDgCommonQueryPage extends JDialogBase  {


	private JPanel jContentPane = null;
	private JPanel bottomPanel = null;

	private JButton btnOK = null;

	private JButton btnCancel = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private boolean ok = false;

	private Object returnValue = null;

	private List returnList = null;

	private JTableListModel tableModel = null;

	private static boolean singleResult = true;

	protected JToolBar jToolBar = null;

	private static List tableColumns = null; // @jve:decl-index=0:
	
	

	protected NewPnCommonQueryPage newpnCommonQueryPage = null;
	private JLabel lbMessage = null;

	/**
	 * 显示
	 */
	@Override
	public void setVisible(boolean b) {
		if (b) {
			newpnCommonQueryPage.setInitState();
			newpnCommonQueryPage.cbbQueryField.setSelectedIndex(0);
			doSomethingBeforeVisable(jTable);
		}
		super.setVisible(b);
	}

	/**
	 * 
	 */
	private void setSelectedFirstRow() {
		if (((JTableListModel) getJTable().getModel()).getRowCount() > 0) {
			List list = ((JTableListModel) getJTable().getModel())
					.getCurrentRows();
			if (list.size() > 0) {
				// setReturnValue(list.get(0));
				// ((JTableListModel) getJTable().getModel()).sets(list.get(0));
			} else {
				// setReturnValue(((JTableListModel) getJTable().getModel())
				// .getValueAt(0));
				((JTableListModel) getJTable().getModel()).setSelectRow(0);
			}
		}

		if (((JTableListModel) getJTable().getModel()).getRowCount() > 0) {
			// ((JTableListModel) getJTable().getModel()).setSelectRow(0);
			getJTable().requestFocus();
		}
	}

	public static void setSingleResult(boolean singleResult) {
		NewDgCommonQueryPage.singleResult = singleResult;
	}

	public static boolean getSingleResult() {
		return NewDgCommonQueryPage.singleResult;
	}

	/**
	 * @return Returns the tableColumns.
	 */
	public static List getTableColumns() {
		return tableColumns;
	}

	/**
	 * @param tableColumns
	 *            The tableColumns to set.
	 */
	public static void setTableColumns(List columns) {
		tableColumns = columns;
	}

	/**
	 * @return Returns the singleResult.
	 */
	public boolean isSingleResult() {
		return singleResult;
	}

	/**
	 * @return Returns the returnList.
	 */
	public List getReturnList() {
		return returnList;
	}

	/**
	 * @param returnList
	 *            The returnList to set.
	 */
	private void setReturnList(List returnList) {
		this.returnList = returnList;
	}

	/**
	 * @return Returns the returnValue.
	 */
	public Object getReturnValue() {
		return returnValue;
	}

	/**
	 * @param returnValue
	 *            The returnValue to set.
	 */
	private void setReturnValue(Object returnValue) {
		this.returnValue = returnValue;
	}

	/**
	 * This method initializes
	 * 
	 */
	public NewDgCommonQueryPage() {
		super();
		initialize();
		InitialFocusSetter.setInitialFocus(this, newpnCommonQueryPage
				.getTfQueryValue());
		// this.addWindowFocusListener(new FocusSetter(pnCommonQueryPage
		// .getTfQueryValue()));
		jTable.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 10) {// Enter
					getResult();
				}
			}
		});
		newpnCommonQueryPage.getTfQueryValue().addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyChar() == KeyEvent.VK_ENTER) {
					setSelectedFirstRow();
				}
			}
		});
		newpnCommonQueryPage.getTfQueryValue().addCaretListener(new javax.swing.event.CaretListener() {
			public void caretUpdate(javax.swing.event.CaretEvent e) {
				Integer col = null;
				Object obj = newpnCommonQueryPage.getCbbQueryField().getSelectedItem();
				if (obj == null || !(obj instanceof Item)) {
					return;
				}
				Item itm = (Item) obj;
				if (itm == null || itm.getName() == null) {
					return;
				}
				String pro = itm.getName();
				List<JTableListColumn> list = ((JTableListModel) tableModel)
						.getColumns();
				for (int i = 0; i < list.size(); i++) {
					JTableListColumn data = list.get(i);
					if (data.getCaption() == null) {
						continue;
					}
					if (data.getCaption().equals(pro))
						col = i;
				}
				boolean isEqual = true;
				if (newpnCommonQueryPage.getRbPrecision().isSelected()) {
					isEqual = true;
				} else {
					isEqual = false;
				}
				String value = newpnCommonQueryPage.getTfQueryValue().getText();				
				if (value == null || value.equals("")) {
					return;
				}				
				if (tableModel instanceof JTableListModel
						&& col != null) {					
					((JTableListModel) tableModel).filter(col,
							value, isEqual, 1);
				}
			}
		});		
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("查询");
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setSize(750, 460);

	}

	/**
	 * 
	 * This method initializes jContentPane
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getBottomPanel(), java.awt.BorderLayout.SOUTH);
			jContentPane.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
		}
		return jContentPane;
	}

	/**
	 * 
	 * This method initializes jPanel1
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	public JPanel getBottomPanel() {
		if (bottomPanel == null) {
			lbMessage = new JLabel();
			lbMessage.setText("");
			bottomPanel = new JPanel();
			bottomPanel.add(getBtnOK(), null);
			bottomPanel.add(getBtnCancel(), null);
			bottomPanel.add(lbMessage, null);
		}
		return bottomPanel;
	}

	/**
	 * 
	 * This method initializes btnOK
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnOK() {
		if (btnOK == null) {
			btnOK = new JButton();
			btnOK.setText("确定");
			btnOK.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					getResult();
				}

			});

		}
		return btnOK;
	}

	/**
	 * 
	 */
	private void getResult() {
		if (NewDgCommonQueryPage.getSingleResult()) {
			if (((JTableListModel) getJTable().getModel()).getRowCount() > 0) {
				List list = ((JTableListModel) getJTable().getModel())
						.getCurrentRows();
				if (list.size() > 0) {
					setReturnValue(list.get(0));
				} else {
					setReturnValue(((JTableListModel) getJTable().getModel())
							.getValueAt(0));
				}
			}
		} else {

			List list = ((JTableListModel) getJTable().getModel())
					.getCurrentRows();
			if (list.size() > 0) {
				setReturnList(list);
				// setReturnValue(list.get(0));
			} else {
				setReturnValue(((JTableListModel) getJTable().getModel())
						.getValueAt(0));
			}

		}
		this.setOk(true);
		this.dispose();
	}

	/**
	 * 
	 * This method initializes btnCancel
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					NewDgCommonQueryPage.this.setOk(false);
					NewDgCommonQueryPage.this.dispose();
				}
			});

		}
		return btnCancel;
	}

	/**
	 * 
	 * This method initializes jTable
	 * 
	 * 
	 * 
	 * @return javax.swing.JTable
	 * 
	 */
	public JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
			jTable.addMouseListener(new java.awt.event.MouseAdapter() {

				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 2) {
						getResult();
					}
				}
			});

		}
		return jTable;
	}

	/**
	 * 
	 * This method initializes jScrollPane
	 * 
	 * 
	 * 
	 * @return javax.swing.JScrollPane
	 * 
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
	}

	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	/**
	 * 
	 * This method initializes jToolBar
	 * 
	 * 
	 * 
	 * @return javax.swing.JToolBar
	 * 
	 */
	protected JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.setFloatable(false);
			jToolBar.add(getPnCommonQueryPage());
		}
		return jToolBar;
	}

	/**
	 * 初始化表
	 * 
	 * @param dataSource
	 */
	public JTableListModel initTable(List dataSource) {
		tableModel = new JTableListModel(jTable, dataSource,
				new JTableListModelAdapter() {
					public List InitColumns() {
						if (NewDgCommonQueryPage.getTableColumns() == null) {
							List<JTableListColumn> list = new Vector<JTableListColumn>();
							list.add(addColumn("编号", "code", 100));
							list.add(addColumn("名称", "name", 150));
							return list;
						} else {
							return NewDgCommonQueryPage.tableColumns;
						}

					}
				});
		//
		// 不能自定义列
		//
		tableModel.setMiRenderColumnEnabled(false);

		if (DgCommonQueryPage.getSingleResult()) {
			this.jTable
					.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		} else {
			this.jTable
					.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		}
		return tableModel;
	}

	public abstract List getDataSource(int index, int length, String property,
			Object value, boolean isLike);
	
	public abstract List findMaterielByPara(int index, int length, String property,
			Object value, boolean isLike);

	/**
	 * This method initializes pnCommonQueryPage
	 * 
	 * @return com.bestway.bcus.client.common.PnCommonQueryPage
	 */
	public NewPnCommonQueryPage getPnCommonQueryPage() {
		if (newpnCommonQueryPage == null) {
			newpnCommonQueryPage = new NewPnCommonQueryPage() {

				public List getDataSource(int index, int length,
						String property, Object value, boolean isLike) {
					return NewDgCommonQueryPage.this.getDataSource(index, length,
							property, value, isLike);

				}
				public List findMaterielByPara(int index, int length,
						String property, Object value, boolean isLike) {
					return NewDgCommonQueryPage.this.findMaterielByPara(index, length,
							property, value, isLike);
				}
				public JTableListModel initTable(List dataSource) {
					return NewDgCommonQueryPage.this.initTable(dataSource);

				}
			};
		}
		return newpnCommonQueryPage;
	}

	public void doSomethingBeforeVisable(JTable table) {

	}

	public JLabel getLbMessage() {
		return lbMessage;
	}

}
