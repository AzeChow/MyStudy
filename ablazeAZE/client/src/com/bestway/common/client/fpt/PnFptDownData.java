package com.bestway.common.client.fpt;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;
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
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareFileInfo;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.DownLoadState;
import com.bestway.common.constant.FptBusinessType;
import com.bestway.common.constant.FptInOutFlag;
import com.bestway.common.fpt.action.FptManageAction;
import com.bestway.common.fpt.action.FptMessageAction;
import com.bestway.common.fpt.entity.FptDownData;
import com.bestway.ui.winuicontrol.JPanelBase;

public class PnFptDownData extends JPanelBase {

	private JPanel jPanel = null;

	private JToolBar jJToolBarBar = null;

	private JButton btnProcess = null;

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;

	private JButton btnAdd = null;

	private JButton btnDelete = null;

	private JButton btnEdit = null;

	private JTableListModel tableModel = null;

	private JButton btnCustomDeclare = null;

	private JButton btnClose = null;

	private int dataState = -1;

	// private int dataState = DataState.EDIT;
	private FptManageAction fptManageAction = null; // @jve:decl-index=0:

	private FptMessageAction fptMessageAction = null;

	private JButton btnRefresh = null;

	/**
	 * 下载类型（A申请表，B退货，C收发货）
	 */
	private String downloadType;
	/**
	 * 转入转出标记
	 */
	private String inOutFlag = null;

	private JButton btnResend = null;

	public String getDownloadType() {
		return downloadType;
	}

	public void setDownloadType(String downloadType) {
		this.downloadType = downloadType;
	}

	public String getInOutFlag() {
		return inOutFlag;
	}

	public void setInOutFlag(String inOutFlag) {
		this.inOutFlag = inOutFlag;
	}

	/**
	 * This method initializes
	 * 
	 */
	public PnFptDownData() {
		super();
		initialize();
	}

	/**
	 * This method initializes
	 * 
	 */
	public PnFptDownData(String downloadType, String inOutFlag) {
		super();
		initialize();
		this.downloadType = downloadType;
		this.inOutFlag = inOutFlag;
		fptManageAction = (FptManageAction) CommonVars.getApplicationContext()
				.getBean("fptManageAction");
		fptMessageAction = (FptMessageAction) CommonVars
				.getApplicationContext().getBean("fptMessageAction");
		 initTable();
		 setState();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(636, 358));
		this.setLayout(new BorderLayout());
		this.add(getJPanel(), BorderLayout.CENTER);

		
		
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
			jPanel.add(getJJToolBarBar(), BorderLayout.NORTH);
			jPanel.add(getJScrollPane(), BorderLayout.CENTER);
		}
		return jPanel;
	}

	
	/**
	 * This method initializes jJToolBarBar
	 * 资料下载 菜单
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJJToolBarBar() {
		if (jJToolBarBar == null) {
			jJToolBarBar = new JToolBar();
			jJToolBarBar.add(getBtnAdd());
			jJToolBarBar.add(getBtnEdit());
			jJToolBarBar.add(getBtnDelete());
			jJToolBarBar.add(getBtnCustomDeclare());
			jJToolBarBar.add(getBtnResend());
			jJToolBarBar.add(getBtnProcess());
			jJToolBarBar.add(getBtnRefresh());
			jJToolBarBar.add(getBtnClose());
		}
		return jJToolBarBar;
	}

	/**
	 * This method initializes btnProcess
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnProcess() {
		if (btnProcess == null) {
			btnProcess = new JButton();
			btnProcess.setText("回执处理 ");
			btnProcess.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(PnFptDownData.this,
								"请选择要回执处理的下载数据", "提示", 2);
						return;
					}
					FptDownData head = (FptDownData) tableModel.getCurrentRow();
					try {
						// 下载回执,通过服务器下载
						if (FptQuery.getInstance().isHttpUpload()) {
							fptMessageAction.httpDownload(
									new Request(CommonVars.getCurrUser()),
									head.getSeqNo());
						}
					} catch (RuntimeException ex) {
						JOptionPane.showMessageDialog(PnFptDownData.this,
								"下载回执失败！可能原因：1、网络不通、连接不到远程ftp。2、ftp参数配置错误");
					}
					List lsReturnFile = FptMessageHelper.getInstance()
							.showBcsReceiptFile(FptBusinessType.FPT_DOWN_DATA,
									head.getSeqNo());

					System.out.println("=======lsReturnFile=="
							+ lsReturnFile.size());
					if (lsReturnFile.size() <= 0) {
						return;
					}
					try {
						//反写料件申请表
						String result = fptManageAction.processFptDownData(
								new Request(CommonVars.getCurrUser()), head,
								lsReturnFile);
						
						head = fptManageAction.findFptDownDataById(new Request(
								CommonVars.getCurrUser()), head.getId());
						tableModel.updateRow(head);
						
						JOptionPane.showMessageDialog(PnFptDownData.this,
								"回执处理成功！\n" + result, "提示",
								JOptionPane.INFORMATION_MESSAGE);
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(PnFptDownData.this,
								"回执处理失败" + ex.getMessage(), "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					setState();

				}
			});
		}
		return btnProcess;
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
			jTable.addMouseListener(new java.awt.event.MouseAdapter() {
				@Override
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 2) {
						editData();
					}
				}
			});
		}
		jTable.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
						if (e.getValueIsAdjusting()) {
							return;
						}
						JTableListModel tableModel = (JTableListModel) jTable
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
		return jTable;
	}

	public void queryData() {
		this.initTable();
	}

	public void initTable() {
		List list = this.fptManageAction.findRecordationDataDownLoad(
				new Request(CommonVars.getCurrUser()), this.downloadType,
				this.inOutFlag);
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					@Override
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("电子口岸统一编号", "seqNo", 150));
						list.add(addColumn("申报状态", "declareState", 60));
						list.add(addColumn("企业编号", "tradeCode", 100));
						list.add(addColumn("申请单编号", "appNo", 100));
						list.add(addColumn("转出企业内部编号", "outCopNo", 150));
						list.add(addColumn("下载类型", "downLoadState", 60));
						list.add(addColumn("备注", "note", 150));
						return list;
					}
				});
		jTable.getColumnModel().getColumn(2)
				.setCellRenderer(new DefaultTableCellRenderer() {
					@Override
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue(value));
						return this;
					}

					private String castValue(Object value) {
						return DeclareState.getDeclareStateSpec(value
								.toString());
					}
				});
		jTable.getColumnModel().getColumn(6)
				.setCellRenderer(new DefaultTableCellRenderer() {
					@Override
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue(value));
						return this;
					}

					private String castValue(Object value) {
						return DownLoadState.getDownLoadStateSpec(value
								.toString());
					}
				});
	}

	/**
	 * This method initializes btnAdd
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setText("新增");
			btnAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					addData();
				}
			});
		}
		return btnAdd;
	}

	private void addData() {
		DgFptDownData dg = new DgFptDownData(this.downloadType, this.inOutFlag);
		dg.setTableModel(this.tableModel);
		dg.setDataState(DataState.ADD);
		dg.setVisible(true);
		// dg.setIsSaveormodfiy(2);
	}

	/**
	 * This method initializes btnDelete
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setText("删除");
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					deleteDate();
				}
			});
		}
		return btnDelete;
	}

	/**
	 * 删除方法
	 */
	public void deleteDate() {
		if (tableModel.getCurrentRow() == null) {
			JOptionPane.showMessageDialog(null, "请选择要删除的资料!", "提示", 0);
			return;
		}
		if (JOptionPane.showConfirmDialog(PnFptDownData.this, "是否确定删除数据!!!",
				"提示", 0) != 0) {
			return;
		}
		FptDownData data = (FptDownData) tableModel.getCurrentRow();
		fptManageAction.deleteRecordationDataDownLoad(
				new Request(CommonVars.getCurrUser()), data);
		tableModel.deleteRow(data);
	}

	/**
	 * This method initializes btnEdit
	 * 
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
	public void editData() {
		if (tableModel.getCurrentRow() == null) {
			JOptionPane.showMessageDialog(this, "请选择你要修改的资料", "提示", 0);
			return;
		}
		DgFptDownData dg = new DgFptDownData(this.downloadType, this.inOutFlag);
		FptDownData DownData = (FptDownData) tableModel.getCurrentRow();
		if (DownData.getDeclareState().equals(DeclareState.PROCESS_EXE)
				|| DownData.getDeclareState().equals(DeclareState.WAIT_EAA)) {
			dg.setDataState(DataState.BROWSE);
		} else {
			dg.setDataState(DataState.EDIT);
		}
		dg.setTableModel(tableModel);
		dg.setVisible(true);
	}

	/**
	 * This method initializes btnCustomDeclare
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCustomDeclare() {
		if (btnCustomDeclare == null) {
			btnCustomDeclare = new JButton();
			btnCustomDeclare.setForeground(Color.blue);
			btnCustomDeclare.setText("申请下载");
			btnCustomDeclare
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (tableModel.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(null, "请选择数据!",
										"提示", JOptionPane.INFORMATION_MESSAGE);
								return;
							}
//							FptDownData head = (FptDownData) tableModel
//									.getCurrentRow();
//							head = fptManageAction.findFptDownDataById(
//									new Request(CommonVars.getCurrUser()),
//									head.getId());
//							tableModel.updateRow(head);
							new ApplyThread().start();
						}
					});
		}
		return btnCustomDeclare;
	}

	class ApplyThread extends Thread {
		@Override
		public void run() {
			try {
				String taskId = CommonStepProgress.getExeTaskId();
				CommonStepProgress.showStepProgressDialog(taskId);
				CommonStepProgress.setStepMessage("系统正获取数据，请稍后...");
				Request request = new Request(CommonVars.getCurrUser());
				request.setTaskId(taskId);
				FptDownData head = (FptDownData) tableModel.getCurrentRow();
				
				if(head == null){
					JOptionPane.showMessageDialog(null,"请选择要下载回执的数据！", "确认", 1);
				}
				
				try {
					DeclareFileInfo fileInfo = fptManageAction
							.applyFptDownData(
									new Request(CommonVars.getCurrUser()), head);
					head = fptManageAction.findFptDownDataById(new Request(
							CommonVars.getCurrUser()), head.getId());
					if (head != null) {
						tableModel.updateRow(head);
					}
					CommonStepProgress.closeStepProgressDialog();
					JOptionPane.showMessageDialog(null,
							fileInfo.getFileInfoSpec(), "提示", 1);
				} catch (Exception ex) {
					CommonStepProgress.closeStepProgressDialog();
					JOptionPane.showMessageDialog(null,
							"系统生成报文失败 " + ex.getMessage(), "确认", 1);
					return;
				}
				int count = 0;

				try {
					// 上传文件
					if (FptQuery.getInstance().isHttpUpload()) {
						count = fptMessageAction.httpUpload(new Request(
								CommonVars.getCurrUser()));

						if (count == 0) {
							JOptionPane.showMessageDialog(PnFptDownData.this,
									"系统报文发送失败，请重新发送", "确认", 1);
						} else {
							JOptionPane.showMessageDialog(PnFptDownData.this,
									"系统报文发送成功!", "确认", 1);
						}
					}else{
						return;
					}
				} catch (RuntimeException e) {
					CommonStepProgress.closeStepProgressDialog();
					JOptionPane.showMessageDialog(PnFptDownData.this,
							"系统报文发送失败，请重新发送 " + e.getMessage(), "确认", 1);
					return;
				}
				JOptionPane.showMessageDialog(PnFptDownData.this,
						"系统报文已成功发给海关 ", "确认", 1);
				setState();
			} catch (Exception ex) {
				System.out.println(ex.getStackTrace() + "\n-->"
						+ ex.getMessage());
			} finally {
				CommonStepProgress.closeStepProgressDialog();
			}
		}
	}

	private void setState() {
		FptDownData c = (FptDownData) tableModel.getCurrentRow();
		if (c == null) {
			return;
		}
		boolean isProcessExe = (c.getDeclareState() != null && c
				.getDeclareState().equals(DeclareState.PROCESS_EXE));
		btnDelete.setEnabled(DeclareState.APPLY_POR.equals(c.getDeclareState())
				|| DeclareState.CHANGING_EXE.equals(c.getDeclareState()));
		btnEdit.setEnabled(!isProcessExe
				&& (DeclareState.APPLY_POR.equals(c.getDeclareState()) || DeclareState.CHANGING_EXE
						.equals(c.getDeclareState())));
		this.btnCustomDeclare.setEnabled((DeclareState.APPLY_POR.equals(c
				.getDeclareState()) || DeclareState.CHANGING_EXE.equals(c
				.getDeclareState())));
		this.btnProcess.setEnabled(DeclareState.WAIT_EAA.equals(c
				.getDeclareState()));
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
					close();
				}
			});
		}
		return btnClose;
	}

	/**
	 * 关闭窗体
	 * 
	 */
	protected void close() {
		Component component = getComponent(this);
		if (component instanceof JInternalFrame) {
			JInternalFrame frame = (JInternalFrame) component;
			if (frame != null) {
				frame.doDefaultCloseAction();
			}
		} else if (component instanceof JDialog) {
			JDialog frame = (JDialog) component;
			if (frame != null) {
				frame.dispose();
			}
		}
	}

	private Component getComponent(Component component) {
		if (component instanceof JInternalFrame || component instanceof JDialog) {
			return component;
		}
		if (component == null) {
			return null;
		}
		return getComponent(component.getParent());
	}

	public JTableListModel getTableModel() {
		return tableModel;
	}

	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
	}

	/**
	 * This method initializes jButton
	 * 
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
	 * This method initializes btnResend	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnResend() {
		if (btnResend == null) {
			btnResend = new JButton();
			btnResend.setText("补发报文");
			btnResend.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					fptManageAction.permissionCheckResend(new Request(CommonVars.getCurrUser()));
					int count = 0;
					try {
						// 上传文件
						count = fptMessageAction.httpUpload(new Request(
								CommonVars.getCurrUser()));
						if (count == 0) {
							JOptionPane.showMessageDialog(
									PnFptDownData.this,
									"上传目录为空，没有可上传的报文", "确认", 1);
						}
					} catch (Exception ex) {
						CommonStepProgress.closeStepProgressDialog();
						JOptionPane.showMessageDialog(PnFptDownData.this,
								"系统补发报文失败，请重新发送 " + ex.getMessage(), "确认", 1);
						return;
					}
					JOptionPane.showMessageDialog(PnFptDownData.this,
							"系统补发报文成功，一个上传" + count + "个报文", "确认", 1);
				}
			});
		}
		return btnResend;
	}

} // @jve:decl-index=0:visual-constraint="36,0"
