/*
 * Created on 2004-7-5
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.checkcancel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;

import com.bestway.bcus.checkcancel.action.CheckCancelAction;
import com.bestway.bcus.checkcancel.entity.CheckHead;
import com.bestway.bcus.checkcancel.entity.CheckParameter;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.bcus.client.manualdeclare.FmEmsHeadH2k;
import com.bestway.bcus.client.message.DgRecvProcessMessage;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.EmsEdiTrHead;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.message.action.MessageAction;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import javax.swing.JSplitPane;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FmCheckHead extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JButton jButton2 = null;

	private JButton btnDeclare = null;

	private JButton jButton7 = null;

	private CheckHead checkHead = null;

	private CheckCancelAction checkCancelAction = null;

	private ManualDeclareAction manualDeclearAction = null;

	private JTableListModel tableModel = null;

	private JTableListModel tableModelpara = null;

	private EmsEdiTrHead emsEdiTrHead = null;

	private boolean isChange = false;

	private JButton btnProcess = null;

	private MessageAction messageAction = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane1 = null;

	private JTable jTable1 = null;

	private CheckParameter head = null;

	/**
	 * This is the default constructor
	 */
	public FmCheckHead() {
		super();
		checkCancelAction = (CheckCancelAction) CommonVars
				.getApplicationContext().getBean("checkCancelAction");
		manualDeclearAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		messageAction = (MessageAction) CommonVars.getApplicationContext()
				.getBean("messageAction");
		initialize();

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(652, 315);
		this.setTitle("中期核查-表头");
		this.setContentPane(getJContentPane());
		checkCancelAction
				.controlCheckHead(new Request(CommonVars.getCurrUser()));
		List dataSource = new Vector();
		dataSource = checkCancelAction.findCheckParameter(new Request(
				CommonVars.getCurrUser(), true));
		if (dataSource != null && dataSource.size() > 0) {
			initTableCustom(dataSource);
			head = (CheckParameter) dataSource.get(0);
			inittable();
		} else {
			initTableCustom(new Vector());
			initTable(new Vector());
		}
		setState();

	}

	private void inittable() {
		// 初始化料件
		List dataSource = checkCancelAction.findCheckHead(new Request(
				CommonVars.getCurrUser(), true), head);
		if (dataSource != null && dataSource.size() > 0) {
			initTable(dataSource);
		} else {
			initTable(new Vector());
		}
	}

	private void initTableCustom(final List list) {
		tableModelpara = new JTableListModel(jTable1, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("核查次数", "emsNo", 60));
						list.add(addColumn("核查起始日", "beginDate", 120));
						list.add(addColumn("核查结束日", "endDate", 120));
						return list;
					}
				});
	}

	private void initTable(final List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("申报类型", "flagHg", 100));
						list.add(addColumn("帐册编号", "emsNo", 100));
						list.add(addColumn("经营单位代码", "tradeCode", 100));
						list.add(addColumn("经营单位名称", "tradeName", 120));
						list.add(addColumn("加工单位代码", "machCode", 100));
						list.add(addColumn("加工单位名称", "machName", 120));
						list.add(addColumn("本期起始日期", "beginDate", 80));
						list.add(addColumn("主管海关", "masterCustoms.name", 100));
						return list;
					}
				});
		
		jTable.getColumnModel().getColumn(1).setCellRenderer(
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
						if (value.equals("1")) {
							returnValue = "中期核查";
						} else if (value.equals("2")) {
							returnValue = "库存调整";
						} 
						return returnValue;
					}
				});
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */

	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
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
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getJButton());
			jToolBar.add(getJButton1());
			jToolBar.add(getJButton2());
			jToolBar.add(getBtnDeclare());
			jToolBar.add(getBtnProcess());
			jToolBar.add(getJButton7());
		}
		return jToolBar;
	}

	private void fillCheckHead(CheckHead checkHead, EmsHeadH2k emsHeadH2k) {
		checkHead.setEmsNo(emsHeadH2k.getEmsNo());
		checkHead.setTradeCode(emsHeadH2k.getTradeCode());
		checkHead.setTradeName(emsHeadH2k.getTradeName());
		checkHead.setMachCode(emsHeadH2k.getMachCode());
		checkHead.setMachName(emsHeadH2k.getMachName());
		checkHead.setHead(head);

		/*
		 * SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		 * Date now = new Date(); String defaultDate =
		 * bartDateFormat.format(now);
		 */
		checkHead.setCompany(CommonVars.getCurrUser().getCompany()); // 公司id
	}

	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("新增");

			jButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					List emsH2kList = null;
					emsH2kList = manualDeclearAction
							.findEmsHeadH2k(new Request(CommonVars
									.getCurrUser(), true));
					if (emsH2kList.isEmpty()) {
						JOptionPane.showMessageDialog(FmCheckHead.this,
								"电子帐册中不存在数据", "提示", 2);
						return;
					} else if (head == null){
						JOptionPane.showMessageDialog(FmCheckHead.this,
								"请选中核查次数!", "提示", 2);
						return;
					} else {
						EmsHeadH2k emsHeadh2k = (EmsHeadH2k) emsH2kList.get(0);
						CheckHead checkHead = new CheckHead();
						fillCheckHead(checkHead, emsHeadh2k);
						checkHead = checkCancelAction.saveCheckHead(
								new Request(CommonVars.getCurrUser()),
								checkHead);
						tableModel.addRow(checkHead);
						setState();
					}
				}
			});

		}
		return jButton;
	}

	/**
	 * 
	 * This method initializes jButton1
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("修改");
			jButton1.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					edit();
				}
			});

		}
		return jButton1;
	}

	private void edit() {
		if (tableModel.getCurrentRow() == null) {
			JOptionPane.showMessageDialog(FmCheckHead.this, "请选择你将要修改的记录",
					"提示！", 0);
			return;
		}
		DgCheck dgCheck = new DgCheck();
		dgCheck.setTableModel(tableModel);
		dgCheck.setVisible(true);
	}

	/**
	 * 
	 * This method initializes jButton2
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("删除");
			jButton2.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmCheckHead.this,
								"请选择你要删除的资料", "确认", 1);
						return;
					}

					CheckHead checkHead = (CheckHead) tableModel
							.getCurrentRow();

					if (JOptionPane.showConfirmDialog(FmCheckHead.this,
							"确定要删除此记录吗?\n注意：其表体将一并被删除", "确认", 0) == 0) {
						checkCancelAction.deleteAllCheckImgExg(new Request(
								CommonVars.getCurrUser()), checkHead);
						checkCancelAction.deleteCheckHead(new Request(
								CommonVars.getCurrUser()), checkHead);
						tableModel.deleteRow(checkHead);
						setState();
					}
				}
			});
		}
		return jButton2;
	}

	/**
	 * 
	 * This method initializes btnDeclare
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnDeclare() {
		if (btnDeclare == null) {
			btnDeclare = new JButton();
			btnDeclare.setText("海关申报");
			btnDeclare.setForeground(java.awt.Color.blue);
			btnDeclare.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmCheckHead.this,
								"请选择你将要申报的记录", "提示！", 0);
						return;
					}
					checkHead = (CheckHead) tableModel.getCurrentRow();
					String messageName = null;
					List list = CustomBaseList.getInstance().getGbtobigs();
					messageName = messageAction.exportMessage(new Request(
							CommonVars.getCurrUser()), checkHead, 1, list)[0];

					JOptionPane.showMessageDialog(FmCheckHead.this,
							"报文已经生成，位置在中间服务器的：" + messageName, "报文已生成", 1);

					tableModel.updateRow(checkHead);

					setState();
				}
			});

		}
		return btnDeclare;
	}

	private void setState() {
		jButton1.setEnabled(tableModel.getRowCount() > 0); // 修改
		jButton2.setEnabled(tableModel.getRowCount() > 0); // 删除
		btnDeclare.setEnabled(tableModel.getRowCount() > 0); // 生成报文
	}

	/**
	 * 
	 * This method initializes jButton7
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton s
	 */
	private JButton getJButton7() {
		if (jButton7 == null) {
			jButton7 = new JButton();
			jButton7.setText("关闭");
			jButton7.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					FmCheckHead.this.dispose();

				}
			});

		}
		return jButton7;
	}

	/**
	 * @return Returns the isChange.
	 */
	public boolean isChange() {
		return isChange;
	}

	/**
	 * @param isChange
	 *            The isChange to set.
	 */
	public void setChange(boolean isChange) {
		this.isChange = isChange;
	}

	/**
	 * @return Returns the checkCancelAction.
	 */
	public CheckCancelAction getCheckCancelAction() {
		return checkCancelAction;
	}

	/**
	 * @param checkCancelAction
	 *            The checkCancelAction to set.
	 */
	public void setCheckCancelAction(CheckCancelAction checkCancelAction) {
		this.checkCancelAction = checkCancelAction;
	}

	/**
	 * @return Returns the checkHead.
	 */
	public CheckHead getCheckHead() {
		return checkHead;
	}

	/**
	 * @param checkHead
	 *            The checkHead to set.
	 */
	public void setCheckHead(CheckHead checkHead) {
		this.checkHead = checkHead;
	}

	/**
	 * This method initializes btnProcess
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnProcess() {
		if (btnProcess == null) {
			btnProcess = new JButton();
			btnProcess.setText("回执处理");
			btnProcess.setForeground(java.awt.Color.blue);
			btnProcess.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgRecvProcessMessage dgProcessMessage = new DgRecvProcessMessage();
					dgProcessMessage.setType("CheckHead");
					dgProcessMessage.setVisible(true);

					List dataSource = checkCancelAction.findCheckHead(
							new Request(CommonVars.getCurrUser()), head);
					initTable(dataSource);
					setState();
				}
			});
		}
		return btnProcess;
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setDividerLocation(200);
			jSplitPane.setLeftComponent(getJPanel());
			jSplitPane.setRightComponent(getJPanel1());
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
			jPanel.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel;
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
			jPanel1.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel1;
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
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJTable1());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jTable1
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable1() {
		if (jTable1 == null) {
			jTable1 = new JTable();
			jTable1.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							if (tableModelpara == null
									|| tableModelpara.getCurrentRow() == null) {
								return;
							}
							head = (CheckParameter) tableModelpara
									.getCurrentRow();
							inittable();
							setState();
						}
					});
		}
		return jTable1;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
