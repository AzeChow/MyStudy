/*
 * Created on 2004-7-5
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.checkcancel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcus.checkcancel.action.CheckCancelAction;
import com.bestway.bcus.checkcancel.action.CheckCancelAuthorityAction;
import com.bestway.bcus.checkcancel.entity.CancelHead;
import com.bestway.bcus.checkcancel.entity.CancelOwnerHead;
import com.bestway.bcus.checkcancel.entity.CheckHead;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.message.action.MessageAction;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.common.constant.RrportDelcareType;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

/**
 * @author Administrator
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class FmCancelOwnerHead extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JButton jButton2 = null;

	private JButton jButton7 = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private CheckHead checkHead = null;

	private CheckCancelAction checkCancelAction = null;  //  @jve:decl-index=0:

	private ManualDeclareAction manualDeclearAction = null;

	private MessageAction messageAction = null;

	private JTableListModel tableModel = null;

	private CancelOwnerHead cancelHead = null;

	private boolean isChange = false;

	private JPanel jPanel = null;

	private JLabel jLabel = null;

	private JTextField jTextField = null;

	private JButton jButton3 = null;

	private CheckCancelAuthorityAction checkCancelAuthorityAction = null;

	private JLabel jLabel1 = null;

	/**
	 * This is the default constructor
	 */
	public FmCancelOwnerHead() {
		super();
		checkCancelAction = (CheckCancelAction) CommonVars
				.getApplicationContext().getBean("checkCancelAction");
		manualDeclearAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		messageAction = (MessageAction) CommonVars.getApplicationContext()
				.getBean("messageAction");
		checkCancelAuthorityAction = (CheckCancelAuthorityAction) CommonVars
				.getApplicationContext().getBean("checkCancelAuthorityAction");
		initialize();
		checkCancelAuthorityAction.brownCancelOwner(new Request(CommonVars
				.getCurrUser()));
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(899, 377);
		this.setTitle("自用核销计算-表头");
		this.setContentPane(getJContentPane());

		List dataSource = null;
		dataSource = checkCancelAction.findCancelHead(new Request(CommonVars
				.getCurrUser()), true);
		if (dataSource == null) {
			dataSource = new Vector();
		}
		initTable(dataSource);
		setState();

	}

	private void initTable(final List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("帐册编号", "emsNo", 120));
						list.add(addColumn("报核类型", "declareType", 80));
						list.add(addColumn("报核次数", "cancelTimes", 100));
						list.add(addColumn("处理状态", "declareState", 100));
						list.add(addColumn("报核开始日期", "beginDate", 150));
						list.add(addColumn("报核截止日期", "endDate", 150));
						list.add(addColumn("进口报关单数", "inportCustomNum", 80));
						list.add(addColumn("出口报关单数", "outportCustomNum", 80));
						list.add(addColumn("申报日期", "declareDate", 100));
						list.add(addColumn("输入日期", "inputDate", 100));
						list.add(addColumn("输入员", "inputUser", 100));
						list.add(addColumn("备注", "note", 100));
						return list;
					}
				});
		jTable.getColumnModel().getColumn(2).setCellRenderer(
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
						if (value.equals(RrportDelcareType.BEGINDELCARE)) {
							returnValue = "预报核";
						} else if (value.equals(RrportDelcareType.DELCARE)) {
							returnValue = "正式报核";
						}
						return returnValue;
					}
				});
		jTable.getColumnModel().getColumn(4).setCellRenderer(
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
						if (value.equals(DeclareState.APPLY_POR)) {
							returnValue = "正在预录入";
						} else if (value.equals(DeclareState.WAIT_EAA)) {
							returnValue = "等待审批";
						} else if (value.equals(DeclareState.PROCESS_EXE)) {
							returnValue = "审批通过";
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
			jContentPane.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
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
			jLabel1 = new JLabel();
			jLabel1.setFont(new Font("\u65b0\u5b8b\u4f53", Font.BOLD, 18));
			jLabel1.setText("   自用核销计算表");
			jLabel1.setForeground(new Color(0, 102, 0));
			FlowLayout f = new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(1);
			f.setHgap(1);
			jToolBar = new JToolBar();
			jToolBar.setLayout(f);
			jToolBar.add(getJButton());
			jToolBar.add(getJButton1());
			jToolBar.add(getJButton2());
			jToolBar.add(getJButton7());
			jToolBar.add(getJPanel());

			jToolBar.add(jLabel1);
		}
		return jToolBar;
	}

	private Date dateToStandDate(Date date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String defaultDate = bartDateFormat.format(date);
		return java.sql.Date.valueOf(defaultDate);
	}

	private void fillCancelHead(CancelOwnerHead cancelHead,
			EmsHeadH2k emsHeadH2k) {
		cancelHead.setEmsNo(emsHeadH2k.getEmsNo());
		cancelHead.setDeclareType(RrportDelcareType.BEGINDELCARE); // 预报核
		cancelHead.setDeclareState(DeclareState.APPLY_POR);// 正在预录入
		// ManualDeclareAction manualdeclearAction = (ManualDeclareAction)
		// CommonVars
		// .getApplicationContext().getBean("manualdeclearAction");
		// List list = manualdeclearAction.findEmsHeadH2k(new
		// Request(CommonVars.getCurrUser()));
		// for (int i = 0; i < list.size(); i++) {
		// if (((EmsHeadH2k) list.get(i)).getDeclareState().equals(
		// DeclareState.PROCESS_EXE)){
		// cancelHead.setEmsApprNo(((EmsHeadH2k) list.get(i)).getEmsApprNo());
		// //批准证号
		// break;
		// }
		// }
		cancelHead.setEmsApprNo(emsHeadH2k.getEmsApprNo()); // 批准证号
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formatHour = new SimpleDateFormat("HH:mm:ss");
		Date now = new Date();
		String defaultDate = bartDateFormat.format(now);
		CancelHead maxObj = checkCancelAction.findMaxCancelTimesCancelHead(
				new Request(CommonVars.getCurrUser()), true,null);
		if (maxObj != null) {
			Date d = maxObj.getEndDate();
			if (d != null) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(CommonVars.dateToStandDate(d));
				Calendar nowDate = (Calendar) calendar.clone();
				nowDate.add(Calendar.DAY_OF_YEAR, +1);
				Date nowDates = nowDate.getTime();
				cancelHead.setBeginDate(nowDates);
			}
			cancelHead
					.setCancelTimes((maxObj.getCancelTimes() == null || maxObj
							.getCancelTimes().equals("")) ? "0"
							: String.valueOf(Integer.valueOf(maxObj
									.getCancelTimes()) + 1));
		} else {
			cancelHead.setBeginDate(java.sql.Date.valueOf(defaultDate));
			cancelHead.setCancelTimes("1");
		}
		cancelHead.setDeclareDate(java.sql.Date.valueOf(defaultDate));
		cancelHead.setDeclareTime(formatHour.format(now));
		cancelHead.setInputDate(java.sql.Date.valueOf(defaultDate));
		cancelHead.setCompany(CommonVars.getCurrUser().getCompany()); // 公司id
		if (CommonVars.getCurrUser() != null)
			cancelHead.setInputUser(CommonVars.getCurrUser().getLoginName());
		else
			cancelHead.setInputUser("000");
		cancelHead.setModifyMark(ModifyMarkState.ADDED);
	}

	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("新增");
			jButton.setPreferredSize(new Dimension(65, 30));
			jButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					
					checkCancelAuthorityAction.addCancelOwner(new Request(CommonVars
							.getCurrUser()));
					
					List list = checkCancelAction.findCancelHeadByType(
							new Request(CommonVars.getCurrUser()),
							RrportDelcareType.BEGINDELCARE, true);
					/*
					 * if (list != null && list.size()>0){
					 * JOptionPane.showMessageDialog(FmCancelOwnerHead.this,
					 * "已经存在预报核！", "提示！", 0); return; }
					 */

					List emsH2kList = null;
					emsH2kList = checkCancelAction
							.findEmsHeadH2k(new Request(CommonVars
									.getCurrUser()));
					if (emsH2kList.isEmpty())
						return;
					else {
						EmsHeadH2k emsHeadh2k = (EmsHeadH2k) emsH2kList.get(0);
						CancelOwnerHead cancelHead = new CancelOwnerHead();
						fillCancelHead(cancelHead, emsHeadh2k);
						cancelHead = (CancelOwnerHead) checkCancelAction
								.saveCancelHead(new Request(CommonVars
										.getCurrUser()), cancelHead);
						tableModel.addRow(cancelHead);
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
			jButton1.setPreferredSize(new Dimension(65, 30));
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
			JOptionPane.showMessageDialog(FmCancelOwnerHead.this,
					"请选择你将要修改的记录", "提示！", 0);
			return;
		}
		DgCancelOwner dgCancel = new DgCancelOwner();
		dgCancel.setTableModel(tableModel);
		cancelHead = (CancelOwnerHead) tableModel.getCurrentRow();
		if (cancelHead.getDeclareType().equals(RrportDelcareType.BEGINDELCARE))
			dgCancel.setShow(false);
		else if (cancelHead.getDeclareType().equals(RrportDelcareType.DELCARE))
			dgCancel.setShow(true);
		dgCancel.setVisible(true);
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
			jButton2.setPreferredSize(new Dimension(65, 30));
			jButton2.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					checkCancelAuthorityAction.deleteCancelOwner(new Request(
							CommonVars.getCurrUser()));
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmCancelOwnerHead.this,
								"请选择你要删除的资料", "确认", 1);
						return;
					}

					CancelOwnerHead cancelHead = (CancelOwnerHead) tableModel
							.getCurrentRow();

					if (cancelHead.getDeclareState().equals(
							DeclareState.WAIT_EAA)) {
						JOptionPane.showMessageDialog(FmCancelOwnerHead.this,
								"不能删除已申报的预报核记录！", "确认", 1);
						return;
					}

					if (JOptionPane.showConfirmDialog(FmCancelOwnerHead.this,
							"确定要删除此记录吗?\n注意：其表体将一并被删除", "确认", 0) == 0) {

						checkCancelAction.deleteCancelAll(new Request(
								CommonVars.getCurrUser()), cancelHead, true);
						checkCancelAction.deleteCancelHead(new Request(
								CommonVars.getCurrUser()), cancelHead);
						tableModel.deleteRow(cancelHead);
						setState();
					}
				}
			});
		}
		return jButton2;
	}

	private void setState() {
		jButton1.setEnabled(tableModel.getRowCount() > 0); // 修改
		jButton2.setEnabled(tableModel.getRowCount() > 0); // 删除
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
			jButton7.setPreferredSize(new Dimension(65, 30));
			jButton7.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					FmCancelOwnerHead.this.dispose();

				}
			});

		}
		return jButton7;
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
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
			jTable.setRowHeight(25);
			jTable.addMouseListener(new java.awt.event.MouseAdapter() {

				public void mouseClicked(java.awt.event.MouseEvent e) {

					if (e.getClickCount() == 2) {
						edit();
					}
				}
			});

			jTable.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					if (tableModel == null)
						return;
					if (tableModel.getCurrentRow() == null)
						return;
					if (e.isControlDown() == true
							&& e.getKeyCode() == KeyEvent.VK_L) {
						CancelOwnerHead cancelHead = (CancelOwnerHead) tableModel
								.getCurrentRow();
						if (cancelHead.getDeclareType().equals(
								RrportDelcareType.BEGINDELCARE)) {
							cancelHead
									.setDeclareType(RrportDelcareType.DELCARE);
						} else if (cancelHead.getDeclareType().equals(
								RrportDelcareType.DELCARE)) {
							cancelHead
									.setDeclareType(RrportDelcareType.BEGINDELCARE);
						}
						cancelHead = (CancelOwnerHead) checkCancelAction
								.saveCancelHead(new Request(CommonVars
										.getCurrUser()), cancelHead);
						tableModel.updateRow(cancelHead);
					} else if (e.isControlDown() == true
							&& e.getKeyCode() == KeyEvent.VK_P) {
						CancelOwnerHead cancelHead = (CancelOwnerHead) tableModel
								.getCurrentRow();
						if (cancelHead.getDeclareState().equals(
								DeclareState.APPLY_POR)) {
							cancelHead.setDeclareState(DeclareState.WAIT_EAA);
						} else if (cancelHead.getDeclareState().equals(
								DeclareState.WAIT_EAA)) {
							cancelHead
									.setDeclareState(DeclareState.PROCESS_EXE);
						} else if (cancelHead.getDeclareState().equals(
								DeclareState.PROCESS_EXE)) {
							cancelHead.setDeclareState(DeclareState.APPLY_POR);
						}
						cancelHead = (CancelOwnerHead) checkCancelAction
								.saveCancelHead(new Request(CommonVars
										.getCurrUser()), cancelHead);
						tableModel.updateRow(cancelHead);
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
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(5, 8, 91, 24));
			jLabel.setText("请输入报核次数");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setPreferredSize(new Dimension(350, 34));
			jPanel.add(jLabel, null);
			jPanel.add(getJTextField(), null);
			jPanel.add(getJButton3(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(new Rectangle(95, 5, 87, 25));
		}
		return jTextField;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setBounds(new Rectangle(186, 5, 159, 28));
			jButton3.setText("从数据报核中获取数据");
			jButton3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					checkCancelAuthorityAction.cancelOwnerGetData(new Request(
							CommonVars.getCurrUser()));
					String times = jTextField.getText().trim();
					if ("".equals(times)) {
						JOptionPane.showMessageDialog(FmCancelOwnerHead.this,
								"请输入报核次数！", "提示", 2);
						return;
					}
					new getCancelHead().start();
				}
			});
		}
		return jButton3;
	}

	class getCancelHead extends Thread {
		public void run() {
			try {
				CommonProgress.showProgressDialog(FmCancelOwnerHead.this);
				CommonProgress.setMessage("系统正在从数据报核中获取数据，请稍后...");
				String times = jTextField.getText().trim();
				checkCancelAction.getCancel(new Request(CommonVars
						.getCurrUser()), times);
				List dataSource = checkCancelAction.findCancelHead(new Request(
						CommonVars.getCurrUser()), true);
				if (dataSource == null) {
					dataSource = new Vector();
				}
				initTable(dataSource);
				setState();
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmCancelOwnerHead.this,
						"获取数据完毕!", "提示", 2);
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(null,
						"获取数据失败：！" + e.getMessage(), "提示", 2);
			}

		}
	}
} // @jve:decl-index=0:visual-constraint="10,10"
