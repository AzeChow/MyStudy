/*
 * Created on 2004-7-5
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.checkcancel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcus.checkcancel.action.CheckCancelAction;
import com.bestway.bcus.checkcancel.action.CheckCancelAuthorityAction;
import com.bestway.bcus.checkcancel.entity.CancelCusHead;
import com.bestway.bcus.checkcancel.entity.CancelHead;
import com.bestway.bcus.checkcancel.entity.CheckHead;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.bcus.client.message.DgRecvProcessMessage;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.message.action.MessageAction;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.ErrorMessage;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.common.constant.RrportDelcareType;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public class FmCancelCusHead extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JButton jButton2 = null;

	private JButton jButton4 = null;

	private JButton jButton7 = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private CheckHead checkHead = null; // @jve:decl-index=0:

	private CheckCancelAction checkCancelAction = null; // @jve:decl-index=0:

	private ManualDeclareAction manualDeclearAction = null;

	private MessageAction messageAction = null;

	private JTableListModel tableModel = null;

	private CancelCusHead cancelHead = null;

	private boolean isChange = false;

	private JButton btnDeclare = null;

	private JButton btnProcess = null;

	private JButton jButton3 = null;

	private JLabel jLabel = null;
	private CheckCancelAuthorityAction checkCancelAuthorityAction = null;

	/**
	 * This is the default constructor
	 */
	public FmCancelCusHead() {
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
		checkCancelAuthorityAction.brownDataCancel(new Request(CommonVars
				.getCurrUser()));

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(919, 377);
		this.setTitle("核销计算-表头");
		this.setContentPane(getJContentPane());

		List dataSource = null;
		dataSource = checkCancelAction.findCancelHead(
				new Request(CommonVars.getCurrUser()), false);
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
		jTable.getColumnModel().getColumn(2)
				.setCellRenderer(new DefaultTableCellRenderer() {
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
		jTable.getColumnModel().getColumn(4)
				.setCellRenderer(new DefaultTableCellRenderer() {
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
			jLabel = new JLabel();
			jLabel.setText("    核查核销计算表");
			jLabel.setForeground(new java.awt.Color(0, 102, 0));
			jLabel.setFont(new Font("\u65b0\u5b8b\u4f53", Font.BOLD, 18));
			FlowLayout f = new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(1);
			f.setHgap(1);
			jToolBar = new JToolBar();
			jToolBar.setLayout(f);
			jToolBar.add(getJButton());
			jToolBar.add(getJButton1());
			jToolBar.add(getJButton2());
			jToolBar.add(getJButton4());
			jToolBar.add(getBtnDeclare());
			jToolBar.add(getBtnProcess());
			jToolBar.add(getJButton3());
			jToolBar.add(getJButton7());
			jToolBar.add(jLabel);
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

	private void fillCancelHead(CancelCusHead cancelHead, EmsHeadH2k emsHeadH2k) {

		cancelHead.setEmsNo(emsHeadH2k.getEmsNo());
		cancelHead.setDeclareType(RrportDelcareType.BEGINDELCARE); // 预报核
		cancelHead.setDeclareState(DeclareState.APPLY_POR);// 正在预录入
		cancelHead.setEmsApprNo(emsHeadH2k.getEmsApprNo()); // 批准证号

		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();
		String defaultDate = bartDateFormat.format(now);
		SimpleDateFormat formatHour = new SimpleDateFormat("HH:mm:ss");
		CancelHead maxObj = checkCancelAction.findMaxCancelTimesCancelHead(
				new Request(CommonVars.getCurrUser()), false, null);
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

					checkCancelAuthorityAction.addDataCancel(new Request(
							CommonVars.getCurrUser()));
					List list = checkCancelAction.findCancelHeadByType(
							new Request(CommonVars.getCurrUser()),
							RrportDelcareType.BEGINDELCARE, false);
					if (list != null && list.size() > 0) {
						JOptionPane.showMessageDialog(FmCancelCusHead.this,
								"已经存在预报核！", "提示！", 0);
						return;
					}

					List emsH2kList = null;
					emsH2kList = checkCancelAction.findEmsHeadH2k(new Request(
							CommonVars.getCurrUser()));
					if (emsH2kList.isEmpty())
						return;
					else {
						EmsHeadH2k emsHeadh2k = (EmsHeadH2k) emsH2kList.get(0);
						CancelCusHead cancelHead = new CancelCusHead();
						fillCancelHead(cancelHead, emsHeadh2k);

						cancelHead = (CancelCusHead) checkCancelAction
								.saveCancelHead(
										new Request(CommonVars.getCurrUser()),
										cancelHead);
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
			JOptionPane.showMessageDialog(FmCancelCusHead.this, "请选择你将要修改的记录",
					"提示！", 0);
			return;
		}
		DgCancelCus dgCancel = new DgCancelCus();
		dgCancel.setTableModel(tableModel);
		cancelHead = (CancelCusHead) tableModel.getCurrentRow();
	
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
					checkCancelAuthorityAction.deleteDataCancel(new Request(
							CommonVars.getCurrUser()));
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmCancelCusHead.this,
								"请选择你要删除的资料", "确认", 1);
						return;
					}

					CancelCusHead cancelHead = (CancelCusHead) tableModel
							.getCurrentRow();

					if (cancelHead.getDeclareState().equals(
							DeclareState.WAIT_EAA)) {
						JOptionPane.showMessageDialog(FmCancelCusHead.this,
								"不能删除已申报的预报核记录！", "确认", 1);
						return;
					}

					if (JOptionPane.showConfirmDialog(FmCancelCusHead.this,
							"确定要删除此记录吗?\n注意：其表体将一并被删除", "确认", 0) == 0) {

						checkCancelAction.deleteCancelAll(new Request(
								CommonVars.getCurrUser()), cancelHead, false);
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

	/**
	 * 
	 * This method initializes jButton4
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new JButton();
			jButton4.setText("正式报核");
			jButton4.setPreferredSize(new Dimension(65, 30));
			jButton4.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					checkCancelAuthorityAction.dataCancelDueData(new Request(
							CommonVars.getCurrUser()));
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmCancelCusHead.this,
								"请选择你将要正式报核的记录", "提示！", 0);
						return;
					}
					cancelHead = (CancelCusHead) tableModel.getCurrentRow();
					if (cancelHead.getDeclareType() != null
							&& cancelHead.getDeclareType().equals(
									RrportDelcareType.DELCARE)) {
						JOptionPane.showMessageDialog(FmCancelCusHead.this,
								"该申报已经为正式报核!", "提示！", 0);
						return;
					}
					if (cancelHead.getDeclareType() != null
							&& cancelHead.getDeclareType().equals(
									RrportDelcareType.BEGINDELCARE)
							&& !cancelHead.getDeclareState().equals(
									DeclareState.PROCESS_EXE)) {
						JOptionPane.showMessageDialog(FmCancelCusHead.this,
								"预报核未审批通过!", "提示！", 0);
						return;
					}

					if (JOptionPane.showConfirmDialog(FmCancelCusHead.this,
							"确定要进入正式报核吗？", "提示信息", 0) == 0) {
						cancelHead = (CancelCusHead) tableModel.getCurrentRow();
						cancelHead.setDeclareType(RrportDelcareType.DELCARE);// 正式报核
						cancelHead.setDeclareState(DeclareState.APPLY_POR); // 正在预录入
						cancelHead = (CancelCusHead) checkCancelAction
								.saveCancelHead(
										new Request(CommonVars.getCurrUser()),
										cancelHead);
						tableModel.updateRow(cancelHead);
						setState();
					}
				}
			});

		}
		return jButton4;
	}

	private void setState() {
		jButton1.setEnabled(tableModel.getRowCount() > 0); // 修改
		// jButton2.setEnabled(tableModel.getRowCount() > 0); // 删除
		if (tableModel != null && tableModel.getCurrentRow() != null) {
			CancelCusHead cancelHead = (CancelCusHead) tableModel
					.getCurrentRow();
			jButton2.setEnabled(DeclareState.APPLY_POR.equals(cancelHead
					.getDeclareState()));
		}
		jButton4.setEnabled(tableModel.getRowCount() > 0); // 正式报核
		btnDeclare.setEnabled(tableModel.getRowCount() > 0); // 海关申报
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

					FmCancelCusHead.this.dispose();

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

			jTable.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						@Override
						public void valueChanged(ListSelectionEvent e) {
							if (tableModel != null
									&& tableModel.getCurrentRow() != null) {
								CancelCusHead cancelHead = (CancelCusHead) tableModel
										.getCurrentRow();
								jButton2.setEnabled(DeclareState.APPLY_POR
										.equals(cancelHead.getDeclareState()));
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
						CancelCusHead cancelHead = (CancelCusHead) tableModel
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
						cancelHead = (CancelCusHead) checkCancelAction
								.saveCancelHead(
										new Request(CommonVars.getCurrUser()),
										cancelHead);
						tableModel.updateRow(cancelHead);
					} else if (e.isControlDown() == true
							&& e.getKeyCode() == KeyEvent.VK_P) {
						cancelHead = (CancelCusHead) tableModel.getCurrentRow();
						CancelCusHead cancelHead = (CancelCusHead) tableModel
								.getCurrentRow();
						if (cancelHead.getDeclareState().equals(
								DeclareState.APPLY_POR)) {
							cancelHead.setDeclareState(DeclareState.WAIT_EAA);
						} else if (cancelHead.getDeclareState().equals(
								DeclareState.WAIT_EAA)) {
							if (cancelHead.getDeclareType().equals(
									RrportDelcareType.BEGINDELCARE)) {
								checkCancelAction
										.makeCustomsDeclarationisEmsCav(
												new Request(CommonVars
														.getCurrUser()),
												cancelHead, true);
							}
							cancelHead
									.setDeclareState(DeclareState.PROCESS_EXE);
						} else if (cancelHead.getDeclareState().equals(
								DeclareState.PROCESS_EXE)) {
							if (cancelHead.getDeclareType().equals(
									RrportDelcareType.BEGINDELCARE)) {
								checkCancelAction
										.makeCustomsDeclarationisEmsCav(
												new Request(CommonVars
														.getCurrUser()),
												cancelHead, false);
							}
							cancelHead.setDeclareState(DeclareState.APPLY_POR);
						}

						cancelHead = (CancelCusHead) checkCancelAction
								.saveCancelHead(
										new Request(CommonVars.getCurrUser()),
										cancelHead);
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
			btnDeclare.setPreferredSize(new Dimension(65, 30));
			btnDeclare.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					checkCancelAuthorityAction.dataCancelCustoms(new Request(
							CommonVars.getCurrUser()));
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmCancelCusHead.this,
								"请选择你将要申报的记录", "提示！", 0);
						return;
					}
					cancelHead = (CancelCusHead) tableModel.getCurrentRow();
					if (cancelHead.getDeclareType().equals(
							RrportDelcareType.DELCARE)) {
						// 海关申报逻辑检查
						List<ErrorMessage> listError = checkCancelAction
								.checkCancelData(
										new Request(CommonVars.getCurrUser()),
										cancelHead);
						if (listError != null && listError.size() > 0) {
							DgCancelErrorMessage dg = new DgCancelErrorMessage();
							dg.setList(listError);
							dg.setVisible(true);
							listError = dg.getJTableListModel().getList();
							boolean result = false;
							for (ErrorMessage em : listError) {
								if (em.getIsAllow()) {
									result = true;
								} else {
									result = false;
									break;
								}
							}
							if (!result) {
								return;
							}
						}
					}
					HashMap<String, Boolean> parameter = new HashMap<String, Boolean>();
					if (cancelHead.getDeclareType().equals(
							RrportDelcareType.DELCARE)) {// 正式报核
						DgCancelSelectDeclare dgSelectDeclare = new DgCancelSelectDeclare();
						dgSelectDeclare.setVisible(true);
						if (dgSelectDeclare.isSelectOk()) {
							parameter = dgSelectDeclare.getParameter();
						} else {
							return;
						}
					}
					if (JOptionPane.showConfirmDialog(FmCancelCusHead.this,
							"您确定要将报文发送到海关审批吗?", "提示信息", 0) == 0) {
						new chelonian(parameter).start();
					}

				}
			});

		}
		return btnDeclare;
	}

	class chelonian extends Thread {

		HashMap<String, Boolean> parameter = new HashMap<String, Boolean>();

		public chelonian(HashMap<String, Boolean> parameter2) {
			this.parameter = parameter2;
		}

		public void run() {
			try {
				CommonProgress.showProgressDialog(FmCancelCusHead.this);
				CommonProgress.setMessage("系统正在进行海关申报，请稍后...");
				cancelHead = (CancelCusHead) tableModel.getCurrentRow();
				String messageName = null;
				List list = CustomBaseList.getInstance().getGbtobigs();

				messageName = messageAction.exportCancelCusMessage(new Request(
						CommonVars.getCurrUser()), cancelHead, 1, list,
						parameter)[0];

				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmCancelCusHead.this,
						"报文已经生成，位置在中间服务器的：" + messageName, "报文已生成", 1);
				cancelHead.setDeclareState(DeclareState.WAIT_EAA);
				cancelHead = (CancelCusHead) checkCancelAction.saveCancelHead(
						new Request(CommonVars.getCurrUser()), cancelHead);
				tableModel.updateRow(cancelHead);
				setState();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmCancelCusHead.this, "海关申报失败！"
						+ e.getMessage(), "提示", 2);
			}
		}
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
			btnProcess.setPreferredSize(new Dimension(65, 30));
		}
		btnProcess.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent e) {
				checkCancelAuthorityAction.dataCancelDealReturn(new Request(
						CommonVars.getCurrUser()));
				DgRecvProcessMessage dgProcessMessage = new DgRecvProcessMessage();
				dgProcessMessage.setType("CancelHead");
				dgProcessMessage.setVisible(true);
				// 处理数据报核回执后，回写报关单中的是否已核销栏位isEmsCav
				if (dgProcessMessage.isOk()) {
					if (cancelHead == null) {
						JOptionPane.showMessageDialog(FmCancelCusHead.this,
								"核销表头为空！", "提示！", JOptionPane.WARNING_MESSAGE);
						return;
					}
					if (cancelHead.getDeclareType() == null) {
						JOptionPane.showMessageDialog(FmCancelCusHead.this,
								"申报类型为空！", "提示！", JOptionPane.WARNING_MESSAGE);
						return;
					}
					if (cancelHead.getDeclareType().equals(
							RrportDelcareType.BEGINDELCARE)) {
						try {
							cancelHead = (CancelCusHead) tableModel
									.getCurrentRow();
							checkCancelAction.makeCustomsDeclarationisEmsCav(
									new Request(CommonVars.getCurrUser()),
									cancelHead, true);
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(FmCancelCusHead.this,
									"回写报关单是否报核栏位失败!!!!", "提示", 2);
						}
					}
				}
				List dataSource = checkCancelAction.findCancelHead(new Request(
						CommonVars.getCurrUser()), false);
				initTable(dataSource);
				setState();
			}

		});
		return btnProcess;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setText("自用核销");
			jButton3.setVisible(false);
			jButton3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
				}
			});
		}
		return jButton3;
	}

}
