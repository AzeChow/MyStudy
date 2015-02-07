package com.bestway.client.owp;

import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcus.client.common.CommonStepProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxUI;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.license.LicenseClient;
import com.bestway.client.custom.common.DgBaseExportCustomsDeclaration;
import com.bestway.client.owp.FmOwpAppHead.DeclareThread;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.client.fpt.DgMakeCustomsOrderToFptAppHead;
import com.bestway.common.constant.DeclareFileInfo;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.OwpBusinessType;
import com.bestway.owp.action.OwpBillAction;
import com.bestway.owp.action.OwpMessageAction;
import com.bestway.owp.entity.OwpAppHead;
import com.bestway.owp.entity.OwpBillRecvHead;
import com.bestway.owp.entity.OwpBillSendHead;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JToolBar;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.Rectangle;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JComboBox;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;


/**
 * 外发加工收货登记表
 * @author 石小凯 10.9.30
 *
 */
public class FmOwpBillRecv extends JInternalFrameBase {

	private JPanel pnMain = null;
	private JToolBar tbToolBar = null;
	private JButton btnAdd = null;
	private JButton btnEdit = null;
	private JButton btnDelete = null;
	private JButton btnBrowse = null;
	private JButton btnDeclare = null;
	private JButton btnReturnReceipt = null;
	private JButton btnRefurbish = null;
	private JScrollPane spnBillHead = null;
	private JTable tbBillHead = null;
	private JToolBar tbAppHead = null;
	private JComboBox cbbAppHead = null;
	private JButton btnNull = null;
	private JPanel pnSouth = null;
	private JComboBox cbbAppHeadNo = null;
	private JLabel lbAppHeadNo = null;
	private OwpAppHead owpAppHead = null;  //  @jve:decl-index=0:
	private JPopupMenu jPopupMenu = null; 
	private JMenuItem jMenuItem = null;
	private JMenuItem jMenuItem1 = null; 
	/**
	 * 外发加工登记表接口
	 */
	private OwpBillAction owpBillAction = null;
	/**
	 * 外发加工发货登记表表头tableModel
	 */
	private JTableListModel tableModel = null;
	private JButton btnAnnul = null;
	private JButton btnQuery = null;
	private JButton btnShowAll = null;
	private JButton btnClose = null;
	/**
	 * 窗口类构造函数
	 */
	public FmOwpBillRecv() {
		super();
		//外发加工登记表接口
		owpBillAction = (OwpBillAction) CommonVars
		.getApplicationContext().getBean("owpBillAction");
		initialize();
		setState();
	}
	/**
	 * 初始化控件
	 */
	public  void initUIComponents() {
		//查找申请表编号、填充进下拉框
		List owpHead=this.owpBillAction.findOwpAppHead(new Request(
				CommonVars.getCurrUser()));
		System.out.println("owpHead="+owpHead.size());
		if (owpHead != null && owpHead.size() > 0) {
			for (int i = 0; i < owpHead.size(); i++) {
				this.cbbAppHeadNo.addItem((OwpAppHead)owpHead.get(i));
			}
			this.cbbAppHeadNo.setRenderer(CustomBaseRender.getInstance()
					.getRender("appNo", "seqNo", 100, 150)
					.setForegroundColor(java.awt.Color.red));
		}
		if (this.cbbAppHeadNo.getItemCount() > 0) {
			this.cbbAppHeadNo.setSelectedIndex(0);
		}
	}
	/**
	 * 初始化容器
	 * 
	 */
	private void initialize() {
        this.setSize(new Dimension(880, 397));
        this.setContentPane(getJContentPane());
        this.setTitle("外发加工收货登记表");
        this
		.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
			public void internalFrameOpened(
					javax.swing.event.InternalFrameEvent e) {
				initUIComponents();
				initTable(getDataSource());
			}
		});
	}
	
	/**
	 * 获取申请表的登记表表头
	 * @return
	 */
	protected List getDataSource() {
		owpAppHead = (OwpAppHead)this.cbbAppHeadNo.getSelectedItem();
		if(null==owpAppHead){
			 return new Vector();
		}
		String appNo = owpAppHead.getAppNo();
		//获取外发加工登记表表头
		List list = owpBillAction.findOwpRecvBillHeadInfo(new Request(CommonVars
				.getCurrUser()), appNo);
		return  list;
	}
	/**
	 * 窗体状态控制
	 */
	private void setState() {
		btnAdd.setEnabled(true);
		OwpBillRecvHead c = null;
		if(null!=tableModel){
			c = (OwpBillRecvHead) tableModel.getCurrentRow();
		}
		if (c == null) {
			return;
		}

		String declareState = c.getDeclareState();
		System.out.println("declareState"+declareState);
		btnAnnul.setEnabled(DeclareState.PROCESS_EXE.equals(declareState)
				&& !c.getCancelMark().equals(new Integer(1)));
		//删除按钮
		btnDelete.setEnabled(DeclareState.APPLY_POR.equals(declareState)
				|| DeclareState.CHANGING_EXE.equals(declareState));
		
		//修改按钮
		btnEdit.setEnabled(DeclareState.APPLY_POR.equals(declareState)
				|| DeclareState.CHANGING_EXE.equals(declareState));
		
		//申报按钮
		btnDeclare.setEnabled(DeclareState.APPLY_POR.equals(declareState)
				|| DeclareState.CHANGING_EXE.equals(declareState));
		
		//回执查看
		btnReturnReceipt.setEnabled(DeclareState.WAIT_EAA.equals(declareState));
	}
	/**
	 * 初始化表格
	 */
	private JTableListModel initTable(List list) {
		tableModel = new JTableListModel(tbBillHead, list,
				new JTableListModelAdapter() {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("流水号", "seqNum", 50));
						list.add(addColumn("发货单编号", "billNo", 120));
						list.add(addColumn("电子口岸统一编号", "seqNo", 120));
						list.add(addColumn("申请表编号", "appNo", 100));
						list.add(addColumn("导入方式", "isInsertByApp", 100));
						list.add(addColumn("申报状态", "declareState", 60));//4
						list.add(addColumn("企业内部编号", "copBillNo", 120));
						list.add(addColumn("帐册号", "emsNo", 60));
						list.add(addColumn("委托方企业编码", "outTradeCode", 120));
						list.add(addColumn("委托方企业名称", "outTradeName", 120));
						list.add(addColumn("承揽方企业编码", "inTradeCode", 120));
						list.add(addColumn("承揽方企业名称", "inTradeName", 120));
						list.add(addColumn("发货申报人", "isDeclaEm", 60));
						list.add(addColumn("发货日期", "collectDate", 60));
						list.add(addColumn("备注", "note", 80));
						list.add(addColumn("录入人", "creater", 100));
						list.add(addColumn("是否撤销", "cancelMark", 100));
						list.add(addColumn("撤销原因", "cancelReason", 200));
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
		tbBillHead.getColumnModel().getColumn(5).setCellRenderer(
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
						String b = (String)value;
						String result = "";
						if(b.equals("true")){
							result = "申请表";
						}else{
							result = "单据中心";
						}
						return result;
					}
				});
		tbBillHead.getColumnModel().getColumn(6).setCellRenderer(
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
		tbBillHead.getColumnModel().getColumn(17).setCellRenderer(
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
						String cancelMark = (String)value;
						String result = "";
						if(cancelMark.equals("1")){
							result = "已撤销";
						}else{
							result = "非撤销";
						}
						
						return result;
					}
				});
		return tableModel;
	}
	
	/**
	 * 主面板容器设置
	 */
	private JPanel getJContentPane() {
		if (pnMain == null) {
			pnMain = new JPanel();
			pnMain.setLayout(new BorderLayout());
			pnMain.add(getJToolBar(), BorderLayout.NORTH);
			pnMain.add(getJScrollPane(), BorderLayout.CENTER);
			pnMain.add(getJToolBar1(), BorderLayout.SOUTH);
		}
		return pnMain;
	}

	/**
	 * 菜单栏
	 */
	private JToolBar getJToolBar() {
		if (tbToolBar == null) {
			tbToolBar = new JToolBar();
			tbToolBar.add(getBtnAdd());
			tbToolBar.add(getBtnEdit());
			tbToolBar.add(getBtnDelete());
			tbToolBar.add(getBtnBrowse());
			tbToolBar.add(getBtnDeclare());
			tbToolBar.add(getBtnReturnReceipt());
			tbToolBar.add(getBtnAnnul());
			tbToolBar.add(getJButton2());
			tbToolBar.add(getBtnShowAll());
			tbToolBar.add(getBtnRefurbish());
			tbToolBar.add(getBtnClose());
		}
		return tbToolBar;
	}

	/**
	 * 新增按钮	
	 */
	private JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setText("新增");
			btnAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getJPopupMenu().show(
							getBtnAdd(),
							0,
							getBtnAdd().getY()
									+ getBtnAdd().getPreferredSize().height);
//					DgOwpBillSend dg = new DgOwpBillSend();
//					dg.setTableModelHead(tableModel);
//					dg.setOwpAppHead(owpAppHead);
//					dg.setDataState(DataState.ADD);
//					dg.setVisible(true);
					
				}
			});
		}
		return btnAdd;
	}
	/**
	 * This method initializes jPopupMenu
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getJPopupMenu() {
		if (jPopupMenu == null) {
			jPopupMenu = new JPopupMenu();
			jPopupMenu.setSize(new Dimension(45, 28));
			jPopupMenu.add(getJMenuItem());
			jPopupMenu.addSeparator();
			jPopupMenu.add(getJMenuItem1());
		}
		return jPopupMenu;
	}
	private JMenuItem getJMenuItem() {
		if (jMenuItem == null) {
			jMenuItem = new JMenuItem("从申请表导入");
			jMenuItem.setSize(new Dimension(23, 23));
			jMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgOwpBillRecv dg = new DgOwpBillRecv();
					dg.setTableModelHead(tableModel);
					dg.setOwpAppHead(owpAppHead);
					dg.setDataState(DataState.ADD);
					dg.setInsertByApp(true);
					dg.setVisible(true);
				}
			});
		}
		return jMenuItem;
	}
	private JMenuItem getJMenuItem1() {
		if (jMenuItem1 == null) {
			jMenuItem1 = new JMenuItem("从单据中心导入");
			jMenuItem1.setSize(new Dimension(18, 18));
			jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgOwpBillRecv dg = new DgOwpBillRecv();
					dg.setTableModelHead(tableModel);
					dg.setOwpAppHead(owpAppHead);
					dg.setDataState(DataState.ADD);
					dg.setInsertByApp(false);
					dg.setVisible(true);
				}
			});
		}
		return jMenuItem1;
	}
	/**
	 * 修改按钮	
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改");
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmOwpBillRecv.this,
								"请选择你要删除的资料", "提示", 0);
						return;
					}
					DgOwpBillRecv dg = new DgOwpBillRecv();
					dg.setTableModelHead(tableModel);
					dg.setOwpAppHead(owpAppHead);
					dg.setDataState(DataState.EDIT);
					dg.setVisible(true);
				}
			});
		}
		return btnEdit;
	}

	/**
	 * 删除按钮
	 */
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setText("删除");
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					 deleteBillHead();
				}
			});
			
		}
		return btnDelete;
	}
	/**
	 * 删除登记表头
	 */
	private void deleteBillHead(){
		if (tableModel.getCurrentRow() == null) {
			JOptionPane.showMessageDialog(FmOwpBillRecv.this,
					"请选择你要删除的资料", "提示", 0);
			return;
		}
		if (JOptionPane.showConfirmDialog(FmOwpBillRecv.this,
				"确定要删除吗？", "确认", 0) != 0) {
			return;
		}
		OwpBillRecvHead head = (OwpBillRecvHead) tableModel
			.getCurrentRow();
//		owpMessageAction.deleteOwpSendBillHead(new Request(
//				CommonVars.getCurrUser()), head);
		owpBillAction.deleteOwpRecvBillHead(new Request(
				CommonVars.getCurrUser()), head);
		tableModel.deleteRow(head);
		
	}
	/**
	 * 浏览按钮
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
	 * 浏览
	 */
	private void browse() {
		if (tableModel.getCurrentRow() == null) {
			JOptionPane.showMessageDialog(FmOwpBillRecv.this,
					"请选择你要删除的资料", "提示", 0);
			return;
		}
		DgOwpBillRecv dg = new DgOwpBillRecv();
		dg.setTableModelHead(tableModel);
		dg.setOwpAppHead(owpAppHead);
		dg.setDataState(DataState.BROWSE);
		dg.setVisible(true);
	}

	/**
	 * 审批按钮
	 */
	private JButton getBtnDeclare() {
		if (btnDeclare == null) {
			btnDeclare = new JButton();
			btnDeclare.setText("海关申报");
			btnDeclare.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					OwpBillRecvHead owpBillRecvHead = (OwpBillRecvHead) tableModel.getCurrentRow();
					if (owpBillRecvHead == null) {
						JOptionPane.showMessageDialog(FmOwpBillRecv.this,
								"请选择要申报的申请表!", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					if (JOptionPane.showConfirmDialog(FmOwpBillRecv.this,
							"是否确定申报申请表!", "提示", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
						return;
					}
					if (validateDataIsNull() == true) {
						return;
					}
					owpBillRecvHead = owpBillAction.findOwpBillRecvHeadById(new Request(
							CommonVars.getCurrUser()), owpBillRecvHead.getId());
					
					tableModel.updateRow(owpBillRecvHead);
					
					new DeclareThread().start();
				}
			});
		}
		return btnDeclare;
	}
	/***
	 * 执行申报线程
	 * @author sxk
	 */
	class DeclareThread extends Thread {
		public void run() {
			try {
				String taskId = CommonStepProgress.getExeTaskId();
				CommonStepProgress.showStepProgressDialog(taskId);
				CommonStepProgress.setStepMessage("系统正获取数据，请稍后...");
				Request request = new Request(CommonVars.getCurrUser());
				request.setTaskId(taskId);
				
				OwpBillRecvHead head = (OwpBillRecvHead) tableModel.getCurrentRow();
				
				try {
					DeclareFileInfo fileInfo = owpBillAction
							.declareOwpBillRecvHead(new Request(CommonVars
									.getCurrUser()), head, taskId);
					
					
					head = owpBillAction.findOwpBillRecvHeadById(new Request(
							CommonVars.getCurrUser()), head.getId());
					if (head != null) {
						tableModel.updateRow(head);
					}
					CommonStepProgress.closeStepProgressDialog();
					JOptionPane.showMessageDialog(FmOwpBillRecv.this, fileInfo
							.getFileInfoSpec(), "提示", 1);
				} catch (Exception ex) {
					CommonStepProgress.closeStepProgressDialog();
					JOptionPane.showMessageDialog(FmOwpBillRecv.this, "系统申报失败 "
							+ ex.getMessage(), "确认", 1);
				}
//				setState();
			} catch (Exception ex) {
				System.out.println(ex.getStackTrace() + "\n-->"
						+ ex.getMessage());
			} finally {
				CommonStepProgress.closeStepProgressDialog();
			}
		}
	}
	/**
	 * 向海关申报时 检验外发加工备案申请数据
	 */
	private boolean validateDataIsNull() {
		OwpBillRecvHead head = (OwpBillRecvHead) tableModel
			.getCurrentRow();
		if (head == null) {
			return true;
		}
		if (head.getAppNo() == null
				|| "".equals(head.getAppNo())) {
			JOptionPane.showMessageDialog(null, "申请表编号不可为空!!", "警告",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
//		if (head.getBillNo() == null
//				|| "".equals(head.getBillNo())) {
//			JOptionPane.showMessageDialog(null, "发货单编号不可为空!!", "警告",
//					JOptionPane.INFORMATION_MESSAGE);
//			return true;
//		}
		return false;
	}
	/**
	 * 查看回执按钮
	 */
	private JButton getBtnReturnReceipt() {
		if (btnReturnReceipt == null) {
			btnReturnReceipt = new JButton();
			btnReturnReceipt.setText("处理回执");
			btnReturnReceipt.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					process();
				}
			});
		}
		return btnReturnReceipt;
	}

	/**
	 * 刷新按钮
	 */
	private JButton getBtnRefurbish() {
		if (btnRefurbish == null) {
			btnRefurbish = new JButton();
			btnRefurbish.setText("刷新");
		}
		return btnRefurbish;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (spnBillHead == null) {
			spnBillHead = new JScrollPane();
			spnBillHead.setViewportView(getJTable());
		}
		return spnBillHead;
	}

	/**
	 * This method initializes jTable	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getJTable() {
		if (tbBillHead == null) {
			tbBillHead = new JTable();
			tbBillHead.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							JTableListModel tableModel = (JTableListModel) tbBillHead
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
			tbBillHead.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 2) {
						browse();
					}
				}
			});
		}
		return tbBillHead;
	}

	/**
	 * 下方菜单栏	
	 */
	private JToolBar getJToolBar1() {
		if (tbAppHead == null) {
			tbAppHead = new JToolBar();
			tbAppHead.setFloatable(false);
			tbAppHead.add(getJButton());
			tbAppHead.add(getJPanel());
		}
		return tbAppHead;
	}

	/**
	 * 
	 */
	private JComboBox getJComboBox() {
		if (cbbAppHead == null) {
			cbbAppHead = new JComboBox();
		}
		return cbbAppHead;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (btnNull == null) {
			btnNull = new JButton();
			btnNull.setText("a");
			btnNull.setVisible(false);
		}
		return btnNull;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (pnSouth == null) {
			lbAppHeadNo = new JLabel();
			lbAppHeadNo.setText("申请表编号：");
			pnSouth = new JPanel();
			pnSouth = new JPanel();
			FlowLayout f = new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setHgap(1);
			f.setVgap(2);
			pnSouth.setLayout(f);
			pnSouth.add(lbAppHeadNo, null);
			pnSouth.add(getCbbAppHeadNo(), null);
		}
		return pnSouth;
	}

	/**
	 * 下拉列表申请表表头	下按事件	
	 */
	private JComboBox getCbbAppHeadNo() {
		if (cbbAppHeadNo == null) {
			cbbAppHeadNo = new JComboBox();
			cbbAppHeadNo.setPreferredSize(new Dimension(150, 24));
			cbbAppHeadNo.setUI(new CustomBaseComboBoxUI(300));
			cbbAppHeadNo.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
						initTable(getDataSource());
						setState();
					}
				}
			});
		}
		return cbbAppHeadNo;
	}
	/**
	 * This method initializes btnAnnul	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnAnnul() {
		if (btnAnnul == null) {
			btnAnnul = new JButton();
			btnAnnul.setText("撤销");
			btnAnnul.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmOwpBillRecv.this,
								"请选择你要撤销的资料", "提示", 0);
						return;
					}
					List list = owpBillAction.findOwpBillRecvHead(new Request(
							CommonVars.getCurrUser()),owpAppHead.getAppNo());
					if(list!=null&&list.size()>0){
						OwpBillRecvHead owpBillRecvHead = (OwpBillRecvHead)list.get(list.size()-1);
						OwpBillRecvHead head = (OwpBillRecvHead)tableModel.getCurrentRow();
						if(!owpBillRecvHead.getSeqNum().equals(head.getSeqNum())){
							JOptionPane.showMessageDialog(FmOwpBillRecv.this,
									"只能撤销最后一次审批通过的登记表！", "提示", 0);
							return;
						}
					}
					DgOwpBillAnnul dg = new DgOwpBillAnnul();
					dg.setTableModelHead(tableModel);
					dg.setOutFlag(false);
					dg.setVisible(true);
				}
			});
		}
		return btnAnnul;
	}
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton2() {
		if (btnQuery == null) {
			btnQuery = new JButton();
			btnQuery.setText("查询");
			btnQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgOwpBillHeadQuery dg = new DgOwpBillHeadQuery();
					dg.setTableModelHead(tableModel);
					dg.setOutFlag(false);
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
					initTable(getDataSource());
				}
			});
		}
		return btnShowAll;
	}

	/**
	 * 处理回执
	 */
	private void process() {
		if (tableModel.getCurrentRow() == null) {
			JOptionPane.showMessageDialog(FmOwpBillRecv.this, "请选择要回执处理的合同",
					"提示", 2);
			return;
		}
		OwpBillRecvHead head = (OwpBillRecvHead) tableModel
		.getCurrentRow();

		System.out.println("获得变更前的存在的数据");
		// 获得变更前的存在的数据
		OwpBillRecvHead existOwpBillRecvHead = null;
	
		List lsReturnFile = new ArrayList();
	
			String copBillNo = head.getCopBillNo();
			lsReturnFile = OwpMessageHelper.getInstance().showOwpReceiptFile(
					OwpBusinessType.OWP_BILL_RECV, copBillNo);
			if (lsReturnFile.size() <= 0) {
				return;
			}
			
			existOwpBillRecvHead = this.owpBillAction.findOwpBillRecvHeadByCopBillNo(
					new Request(CommonVars.getCurrUser()),copBillNo);
			System.out.println("处理报文existOwpBillRecvHead="+existOwpBillRecvHead);
			System.out.println("处理报文");
		// 处理报文
		try {
			System.out.println("111111111111111");
			String result = owpBillAction.processOwpBillRecvHead(new Request(
					CommonVars.getCurrUser()), head, existOwpBillRecvHead,
					lsReturnFile);
			System.out.println("111111111111111");
			head = owpBillAction.findOwpBillRecvHeadById(new Request(
					CommonVars.getCurrUser()), head.getId());
			System.out.println("111111111111111");
			tableModel.updateRow(head);
			if (existOwpBillRecvHead != null) {
				OwpBillRecvHead exingHeadTemp = owpBillAction.findOwpBillRecvHeadById(new Request(
						CommonVars.getCurrUser()), existOwpBillRecvHead.getId());
				if (exingHeadTemp == null) {
					tableModel.deleteRow(existOwpBillRecvHead);
				}
			}
			JOptionPane.showMessageDialog(FmOwpBillRecv.this, "回执处理成功！\n"
					+ result, "提示", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(FmOwpBillRecv.this, "回执处理失败"
					+ ex.getMessage(), "提示", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

//		setState();
	}
	/**
	 * This method initializes btnClose	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("关闭");
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmOwpBillRecv.this.doDefaultCloseAction();
				}
			});
		}
		return btnClose;
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"
