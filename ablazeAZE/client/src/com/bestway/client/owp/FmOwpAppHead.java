package com.bestway.client.owp;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcus.client.common.CommonStepProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.windows.form.FmMain;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareFileInfo;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.common.constant.OwpBusinessType;
import com.bestway.owp.action.OwpAppAction;
import com.bestway.owp.entity.OwpAppHead;
import com.bestway.owp.entity.OwpAppRecvItem;
import com.bestway.owp.entity.OwpAppSendItem;
import com.bestway.ui.message.JMessage;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

/**
 * 外发加工 申请表
 * @author wss2010.08.04
 */
public class FmOwpAppHead extends JInternalFrameBase {

	private JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JButton btnAdd = null;

	private JButton btnEdit = null;

	private JButton btnDelete = null;

	private JButton btnClose = null;

	private JScrollPane jScrollPane = null;

	private JTable tbAppHead = null;

	private JTableListModel tableModel = null;

	/**
	 * 外发加工远程服务接口
	 */
	private OwpAppAction owpAppAction = null;

	private JButton btnBrowse = null;

	private JButton btnRefresh = null;

	private JButton btnDeclare = null;

	private JButton btnReceipt = null;

	private JButton btnChange = null;

	private JButton btnQuery = null;

	private JButton btnShowAll = null;

	/**
	 * This method initializes
	 * 
	 */
	public FmOwpAppHead() {
		super();
		
		/**
		 * 初始化外发加工远程服务接口
		 */
		owpAppAction = (OwpAppAction) CommonVars.getApplicationContext()
																.getBean("owpAppAction");
		initialize();

		initTable();
		
		setState();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(703, 486));
		this.setTitle("外发加工申请表");
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
			jContentPane.add(getJToolBar(), BorderLayout.NORTH);
			jContentPane.add(getJScrollPane(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * 工具条
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getBtnAdd());
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnDelete());
			jToolBar.add(getBtnBrowse());
			jToolBar.add(getBtnRefresh());
			jToolBar.add(getBtnDeclare());
			jToolBar.add(getBtnChange());
			jToolBar.add(getBtnReceipt());
			jToolBar.add(getBtnQuery());
			jToolBar.add(getBtnShowAll());
			jToolBar.add(getBtnClose());
		}
		return jToolBar;
	}

	/**
	 * 新增按钮
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setText("新增");
			btnAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					OwpAppHead head = owpAppAction.addOwpAppHead(new Request(CommonVars
							.getCurrUser()));
					tableModel.addRow(head);				
				}
			});
		}
		return btnAdd;
	}

	/**
	 * 修改按钮
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改");
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					editData();
				}
			});
		}
		return btnEdit;
	}

	/**
	 * 修改数据
	 */
	public void editData(){

		if (tableModel.getCurrentRow() == null) {
			JOptionPane.showMessageDialog(FmOwpAppHead.this,
					"请选择要修改的申请表资料", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		
		OwpAppHead head = (OwpAppHead) tableModel.getCurrentRow();
		head = owpAppAction.findOwpAppHeadById(new Request(CommonVars
				.getCurrUser()), head.getId());
		if (head == null) {
			JOptionPane.showMessageDialog(this, "申请表企业内部编号 "
					+ head.getAppNo() + " 已经删除不能修改", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		
		if (head != null && head.getDeclareState() != null
				&& head.getDeclareState().equals(DeclareState.PROCESS_EXE)) {
			JOptionPane.showMessageDialog(this, "申请表企业内部编号 "
					+ head.getAppNo() + " 已经生效不能修改", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		
		
		DgOwpAppHead dg = new DgOwpAppHead();
		dg.setTableModelHead(tableModel);
		dg.setDataState(DataState.EDIT);
		dg.setVisible(true);
	
	}
	
	/**
	 * 删除按钮
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setText("删除");
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					deleteData();
				}
			});
		}
		return btnDelete;
	}

	/**
	 * 删除数据
	 */
	public void deleteData(){
		if (tableModel.getCurrentRow() == null) {
			JOptionPane.showMessageDialog(FmOwpAppHead.this,
					"请选择要删除的申请表表头", "提示",
					JOptionPane.INFORMATION_MESSAGE);
		}
		if (JOptionPane.showConfirmDialog(FmOwpAppHead.this,
			"您确定要删除此申请表表头", "提示", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
			return;
		}
		OwpAppHead head = (OwpAppHead) tableModel
				.getCurrentRow();
		owpAppAction.deleteOwpAppHead(new Request(
				CommonVars.getCurrUser()), head);
		tableModel.deleteRow(head);
	}

	/**
	 * 关闭按钮
	 * @return javax.swing.JButton
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("关闭");
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmOwpAppHead.this.doDefaultCloseAction();
				}
			});
		}
		return btnClose;
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
	 * 显示表格
	 * @return javax.swing.JTable
	 */
	private JTable getJTable() {
		if (tbAppHead == null) {
			tbAppHead = new JTable();
			tbAppHead.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							JTableListModel tableModel = (JTableListModel) tbAppHead
									.getModel();
							if (tableModel == null) {
								return;
							}

							try {
								if (tableModel.getCurrentRow() != null) {
									setState();
								}
							} catch (Exception cx) {

							}
						}
					});
			tbAppHead.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 2) {
						browse();
					}
				}
			});

		}
		return tbAppHead;
	}

	/**
	 * 浏览
	 */
	private void browse() {
		if (tableModel.getCurrentRow() == null) {
			JOptionPane.showMessageDialog(this, "请选择你要显示的资料", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		DgOwpAppHead dg = new DgOwpAppHead();
		dg.setTableModelHead(tableModel);
		dg.setDataState(DataState.BROWSE);
		dg.setVisible(true);
	}

	/**
	 * 初始化数据Table,(实体为外发加工申请表表头 OwpAppHead)
	 */
	private void initTable() {
		
		//获取外发加工申请表头
		List list = owpAppAction.findOwpAppHead(new Request(CommonVars
				.getCurrUser()));
		tableModel = new JTableListModel(tbAppHead, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("流水号", "seqNum", 70));//1
						list.add(addColumn("申请表编号", "appNo", 150));//2
						list.add(addColumn("电子口岸统一编号", "seqNo", 150));//3
						list.add(addColumn("申报状态", "declareState", 60));//4
						list.add(addColumn("企业内部编号", "copAppNo", 100));//5
						list.add(addColumn("委托方企业代码", "tradeCode", 100));//6
						list.add(addColumn("委托方企业名称", "tradeName", 150));//7
						list.add(addColumn("委托方手册/帐册编号", "emsNo", 150));//8
						list.add(addColumn("委托加工合同号", "contrNo", 100));//9
						list.add(addColumn("委托企业联系人/联系电话", "corp", 150));//10
						list.add(addColumn("委托申报人/联系电话", "decl", 150));//11
						list.add(addColumn("委托申报日期", "appDate", 100));//12
						list.add(addColumn("修改标志", "modifyMark", 60));//13
						list.add(addColumn("备注", "note", 120));//14
						return list;
					}
				});

		/** 审批状态
		 * APPLY_POR = "1";	申请备案
		 * WAIT_EAA = "2";	等待审批
		 * PROCESS_EXE = "3";	正在执行
		 * CHANGING_EXE = "4";	正在变更
		 * CHANGING_CANCEL = "5";	已经核销
		 */
		tbAppHead.getColumnModel().getColumn(4).setCellRenderer(
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
						return DeclareState.getDeclareStateSpec(value
								.toString());
					}
				});
		
		tbAppHead.getColumnModel().getColumn(13).setCellRenderer(
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
						return ModifyMarkState.getModifyMarkSpec(value
								.toString());
					}
				});
		// jTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	}

	/**
	 * 窗体状态控制
	 */
	private void setState() {
		btnAdd.setEnabled(true);
		OwpAppHead c = (OwpAppHead) tableModel.getCurrentRow();
		if (c == null) {
			return;
		}

		String declareState = c.getDeclareState();
		//删除按钮
		btnDelete.setEnabled(DeclareState.APPLY_POR.equals(declareState)
				|| DeclareState.CHANGING_EXE.equals(declareState));
		
		//修改按钮
		btnEdit.setEnabled(DeclareState.APPLY_POR.equals(declareState)
				|| DeclareState.CHANGING_EXE.equals(declareState));
		
		//申报按钮
		btnDeclare.setEnabled(DeclareState.APPLY_POR.equals(declareState)
				|| DeclareState.CHANGING_EXE.equals(declareState));
		
		//变更按钮
		btnChange.setEnabled(DeclareState.PROCESS_EXE.equals(declareState));
		
		//回执查看
		btnReceipt.setEnabled(DeclareState.WAIT_EAA.equals(declareState));
	}

	/**
	 * 浏览按钮
	 * @return javax.swing.JButton
	 */
	private JButton getBtnBrowse() {
		if (btnBrowse == null) {
			btnBrowse = new JButton();
			btnBrowse.setText("浏览");
			btnBrowse.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					browse();
				}
			});
		}
		return btnBrowse;
	}

	/**
	 * 刷新按钮
	 * @return javax.swing.JButton
	 */
	private JButton getBtnRefresh() {
		if (btnRefresh == null) {
			btnRefresh = new JButton();
			btnRefresh.setText("刷新");
			btnRefresh.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					initTable();
					setState();
				}
			});
		}
		return btnRefresh;
	}

	/**
	 * 	申报按钮
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnDeclare() {
		if (btnDeclare == null) {
			btnDeclare = new JButton();
			btnDeclare.setText("海关申报");
			btnDeclare.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					OwpAppHead owpAppHead = (OwpAppHead) tableModel.getCurrentRow();
					if (owpAppHead == null) {
						JOptionPane.showMessageDialog(FmOwpAppHead.this,
								"请选择要申报的申请表!", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					if (JOptionPane.showConfirmDialog(FmOwpAppHead.this,
							"是否确定申报申请表!", "提示", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
						return;
					}
					if (validateDataIsNull() == true) {
						return;
					}
					owpAppHead = owpAppAction.findOwpAppHeadById(new Request(
							CommonVars.getCurrUser()), owpAppHead.getId());
					
					tableModel.updateRow(owpAppHead);
					
					new DeclareThread().start();
				}
			});
		}
		return btnDeclare;
	}

	/**
	 * 向海关申报时 检验外发加工备案申请数据
	 */
	private boolean validateDataIsNull() {
		OwpAppHead owpAppHead = (OwpAppHead) tableModel.getCurrentRow();
		if (owpAppHead == null) {
			return true;
		}

		if (owpAppHead.getAppClass() == null
				|| "".equals(owpAppHead.getAppClass())) {
			JOptionPane.showMessageDialog(null, "申请表类型不可为空!", "警告",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		if (owpAppHead.getCopAppNo() == null
				|| "".equals(owpAppHead.getCopAppNo())) {
			JOptionPane.showMessageDialog(null, "转出企业内部编号不可为空!", "警告",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		
		if (owpAppHead.getMastCust() == null) {
			JOptionPane.showMessageDialog(null, "委托方海关不可为空!", "警告",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		

		if (owpAppHead.getEmsType() == null) {
			JOptionPane.showMessageDialog(null, "帐册类型不可为空!", "警告",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		
		if (owpAppHead.getEmsNo() == null 
				|| "".equals(owpAppHead.getEmsNo())) {
			JOptionPane.showMessageDialog(null, "委托方手册/帐册类型不可为空!", "警告",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		
		if (owpAppHead.getTradeCode() == null
				|| "".equals(owpAppHead.getTradeCode())) {
			JOptionPane.showMessageDialog(null, "委托方企业代码不可为空!", "警告",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		
		if (owpAppHead.getTradeName() == null
				|| "".equals(owpAppHead.getTradeName())) {
			JOptionPane.showMessageDialog(null, "委托方企业名称不可为空!", "警告",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		

		if (owpAppHead.getInTradeCode() == null
				|| owpAppHead.getInTradeCode().equals("")) {
			JOptionPane.showMessageDialog(null, "转入企业代码不可为空!", "警告",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}

		if (owpAppHead.getInTradeName() == null
				|| "".equals(owpAppHead.getInTradeName())) {
			JOptionPane.showMessageDialog(null, "转入企业名称不可为空!", "警告",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		

		if (owpAppHead.getInDistrict() == null) {
			JOptionPane.showMessageDialog(null, "目的地不可为空!", "警告",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}

		// 检查明细
		List<OwpAppSendItem> sendItems = this.owpAppAction.findOwpAppSendItemByHeadId(
									new Request(CommonVars.getCurrUser()), owpAppHead.getId());
		if (sendItems.size() <= 0) {
			JOptionPane.showMessageDialog(this, "申请表外发货物个数不可为空!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}

		String errorMsg = "";
		boolean isErrorMsg = false;
		for (OwpAppSendItem item : sendItems) {
			String msg = vaildateSendItemIsNull(item);
			if (msg != null && !"".equals(msg.trim())) {
				isErrorMsg = true;
				String listNo = "序号是 [ " + item.getSeqNum().toString()+ " ] 的明细资料有以下空值 : \n";
				errorMsg += listNo + msg;
			}
		}
		if (isErrorMsg) {
			JMessage.showMessageDialog(FmMain.getInstance(),
					"申请明外发资料填写不完整,请查看详细信息!", JOptionPane.WARNING_MESSAGE,
					JOptionPane.DEFAULT_OPTION, new RuntimeException("\n"
							+ errorMsg));
			return true;
		}
		
		List<OwpAppRecvItem> recvItems = this.owpAppAction.findOwpAppRecvItemByHeadId(
				new Request(CommonVars.getCurrUser()), owpAppHead.getId());
		if (recvItems.size() <= 0) {
			JOptionPane.showMessageDialog(this, "申请表外发货物个数不可为空!", "提示",
				JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		
		String errorMsg2 = "";
		boolean isErrorMsg2 = false;
		for (OwpAppRecvItem item : recvItems) {
			String msg = vaildateRecvItemIsNull(item);
			if (msg != null && !"".equals(msg.trim())) {
				isErrorMsg = true;
				String listNo = "序号是 [ " + item.getSeqNum().toString()+ " ] 的明细资料有以下空值 : \n";
				errorMsg += listNo + msg;
			}
		}
		if (isErrorMsg2) {
			JMessage.showMessageDialog(FmMain.getInstance(),
					"申请明外发资料填写不完整,请查看详细信息!", JOptionPane.WARNING_MESSAGE,
					JOptionPane.DEFAULT_OPTION, new RuntimeException("\n"
							+ errorMsg));
			return true;
		}
		
		return false;
	}
	
	/**
	 * 检验外发货物信息
	 * @param item
	 * @return
	 */
	private String vaildateSendItemIsNull(OwpAppSendItem item) {
		StringBuffer sb = new StringBuffer();
//		
//		// 商品项号
//		if (item.getTrNo() == null) {
//			String str = "   商品项号不可为空\n";
//			sb.append(str);
//		}
		
		// 商品编码
		if (item.getComplex() == null) {
			String str = "   商品编码不可为空\n";
			sb.append(str);
		}
		
		// 商品名称
		if (item.getHsName() == null || item.getHsName().trim().equals("")) {
			String str = "   商品名称不可为空\n";
			sb.append(str);
		}
		
		// 验证数量
		if (item.getQty() == null || (item.getQty() == 0.0)) {
			String str = "   申报数量不可为空\n";
			sb.append(str);
		}
		
		// 申报单位
		if (item.getHsUnit() == null) {
			String str = "   计量单位不可为空\n";
			sb.append(str);
		}
		

		return sb.toString();
	}
	
	/**
	 * 检验收回货物信息
	 * @param item
	 * @return
	 */
	private String vaildateRecvItemIsNull(OwpAppRecvItem item) {
		StringBuffer sb = new StringBuffer();
//		
//		// 商品项号
//		if (item.getTrNo() == null) {
//			String str = "   商品项号不可为空\n";
//			sb.append(str);
//		}
		
		// 商品编码
		if (item.getComplex() == null) {
			String str = "   商品编码不可为空\n";
			sb.append(str);
		}
		
		// 商品名称
		if (item.getHsName() == null || item.getHsName().trim().equals("")) {
			String str = "   商品名称不可为空\n";
			sb.append(str);
		}
		
		// 验证数量
		if (item.getQty() == null || (item.getQty() == 0.0)) {
			String str = "   申报数量不可为空\n";
			sb.append(str);
		}
		
		// 申报单位
		if (item.getHsUnit() == null) {
			String str = "   计量单位不可为空\n";
			sb.append(str);
		}
		

		return sb.toString();
	}
	
	
	/***
	 * 执行申报线程
	 * @author wss
	 */
	class DeclareThread extends Thread {
		public void run() {
			try {
				String taskId = CommonStepProgress.getExeTaskId();
				CommonStepProgress.showStepProgressDialog(taskId);
				CommonStepProgress.setStepMessage("系统正获取数据，请稍后...");
				Request request = new Request(CommonVars.getCurrUser());
				request.setTaskId(taskId);
				
				OwpAppHead head = (OwpAppHead) tableModel.getCurrentRow();
				
				try {
					DeclareFileInfo fileInfo = owpAppAction
							.declareOwpAppHead(new Request(CommonVars
									.getCurrUser()), head, taskId);
					
					
					head = owpAppAction.findOwpAppHeadById(new Request(
							CommonVars.getCurrUser()), head.getId());
					if (head != null) {
						tableModel.updateRow(head);
					}
					CommonStepProgress.closeStepProgressDialog();
					JOptionPane.showMessageDialog(FmOwpAppHead.this, fileInfo
							.getFileInfoSpec(), "提示", 1);
				} catch (Exception ex) {
					CommonStepProgress.closeStepProgressDialog();
					JOptionPane.showMessageDialog(FmOwpAppHead.this, "系统申报失败 "
							+ ex.getMessage(), "确认", 1);
				}
				setState();
			} catch (Exception ex) {
				System.out.println(ex.getStackTrace() + "\n-->"
						+ ex.getMessage());
			} finally {
				CommonStepProgress.closeStepProgressDialog();
			}
		}
	}
	
	
	/**
	 * 	回执查看按钮
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnReceipt() {
		if (btnReceipt == null) {
			btnReceipt = new JButton();
			btnReceipt.setText("处理回执");
			btnReceipt.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					process();
				}
			});
		}
		return btnReceipt;
	}

	
	
	/**
	 * 处理回执
	 */
	private void process() {
		if (tableModel.getCurrentRow() == null) {
			JOptionPane.showMessageDialog(FmOwpAppHead.this, "请选择要回执处理的合同",
					"提示", 2);
			return;
		}

		OwpAppHead owpAppHead = (OwpAppHead) tableModel.getCurrentRow();

		
		// 获得变更前的存在的数据
		OwpAppHead existOwpAppHead = null;
	
		List lsReturnFile = new ArrayList();
	
			String copAppNo = owpAppHead.getCopAppNo();
			lsReturnFile = OwpMessageHelper.getInstance().showOwpReceiptFile(
					OwpBusinessType.OWP_APP, copAppNo);
			if (lsReturnFile.size() <= 0) {
				return;
			}
			
			existOwpAppHead = this.owpAppAction.findOwpAppHeadByCopAppNo(
					new Request(CommonVars.getCurrUser()), copAppNo);

	
		// 处理报文
		try {
			String result = owpAppAction.processOwpAppHead(new Request(
					CommonVars.getCurrUser()), owpAppHead, existOwpAppHead,
					lsReturnFile);
			owpAppHead = owpAppAction.findOwpAppHeadById(new Request(
					CommonVars.getCurrUser()), owpAppHead.getId());
			tableModel.updateRow(owpAppHead);
			if (existOwpAppHead != null) {
				OwpAppHead exingHeadTemp = owpAppAction.findOwpAppHeadById(
						new Request(CommonVars.getCurrUser()), existOwpAppHead
								.getId());
				if (exingHeadTemp == null) {
					tableModel.deleteRow(existOwpAppHead);
				}
			}
			JOptionPane.showMessageDialog(FmOwpAppHead.this, "回执处理成功！\n"
					+ result, "提示", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(FmOwpAppHead.this, "回执处理失败"
					+ ex.getMessage(), "提示", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		setState();
	}

	
	
	/**
	 * 	变更按钮
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnChange() {
		if (btnChange == null) {
			btnChange = new JButton();
			btnChange.setText("变更");
			btnChange.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					changingData();
				}
			});
		}
		return btnChange;
	}

	
	/***
	 * 变更
	 */
	private void changingData() {
		if (tableModel.getCurrentRow() != null) {
			if (JOptionPane.showConfirmDialog(this, "是否确定变更申请表!", "提示",
					JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
				return;
			}
			OwpAppHead c = this.owpAppAction.changingOwpAppHead(new Request(
					CommonVars.getCurrUser()), (OwpAppHead) tableModel
					.getCurrentRow());
			if (c != null) {
				tableModel.addRow(c);
			} else {
				JOptionPane.showMessageDialog(this, "已存在变更申请表!", "提示",
						JOptionPane.INFORMATION_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(this, "请选择要变更申请表!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	/**
	 * 	查询按钮
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnQuery() {
		if (btnQuery == null) {
			btnQuery = new JButton();
			btnQuery.setText("查询");
			btnQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgOwpAppHeadQuery dg = new DgOwpAppHeadQuery();
					dg.setTableModelHead(tableModel);
					dg.setVisible(true);
				}
			});
		}
		return btnQuery;
	}

	/**
	 * This method initializes btnShowAll	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnShowAll() {
		if (btnShowAll == null) {
			btnShowAll = new JButton();
			btnShowAll.setText("显示所有");
			btnShowAll.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					initTable();
				}
			});
		}
		return btnShowAll;
	}
	
	
}
