/*
 * Created on 2004-7-26
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.fpt;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Rectangle;
import java.io.File;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcus.client.common.CommonStepProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.FptBusinessType;
import com.bestway.common.constant.FptInOutFlag;
import com.bestway.common.fpt.action.FptManageAction;
import com.bestway.common.fpt.entity.FptEmail;
import com.bestway.common.fpt.entity.TempFptEmail;

/**
 * @author bsway // change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class DgFptExcEmail extends DgCommon {

	private JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JButton btnSendMail = null;

	private JButton btnExit = null;

	private FptEmail email = null; // @jve:decl-index=0:

	private JSplitPane jSplitPane = null;

	private File excelFile = null;

	private JLabel jLabel2 = null;

	private JComboBox tfSysType = null;

	private String smptServer = null;
	private int dataState = -1;// DataState.BROWSE;

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;

	private JLabel jLabel = null;

	private JButton tbSerach = null;
	private JTableListModel tableModel = null;

	private JTextArea textShow = null;

	private JSplitPane jSplitPane1 = null;

	private JPanel jPanel1 = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel3 = null;

	private JScrollPane jScrollPane1 = null;

	public int getDataState() {
		return dataState;
	}

	public void setDataState(int dataState) {
		this.dataState = dataState;
	}

	public DgFptExcEmail() {
		super();
		fptManageAction = (FptManageAction) CommonVars
				.getApplicationContext().getBean("fptManageAction");
		initialize();
		List ls = new Vector();
		ininTable(ls);
	}

	private void initialize() {
		this.setContentPane(getJContentPane());
		this.setTitle("处理邮件信息表");
		this.setSize(674, 484);
	}

	@Override
	public void setVisible(boolean isFlag) {
		if (isFlag) {
			initComponents();

		}
		super.setVisible(isFlag);
	}

	private void initComponents() {

		tfSysType.removeAllItems();
		tfSysType.addItem(new ItemProperty("1", "结转申请表"));
		tfSysType.addItem(new ItemProperty("2", "发货单"));
		tfSysType.addItem(new ItemProperty("3", "收退货单"));
		CustomBaseComboBoxEditor.getInstance()
				.setComboBoxEditor(this.tfSysType);
		this.tfSysType.setRenderer(CustomBaseRender.getInstance().getRender());
		tfSysType.setSelectedIndex(-1);
		if(email !=null){
			this.textShow.setText(email.getMailbody());
		}

	}

	/**
	 * 
	 * 初始化表格对象
	 */
	private void ininTable(List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					@Override
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list
						.add(new JTableListColumn("收发类型",
								"sysType", 60));
						list.add(new JTableListColumn("转入企业内部编号",
								"outCopAppNo", 180));
						list
								.add(new JTableListColumn("转入企业名称", "outName",
										180));
						list
								.add(new JTableListColumn("转入企业编号", "outCode",
										100));
						list.add(new JTableListColumn("申报状态", "appState", 60));
						list
								.add(new JTableListColumn("转入转出标志",
										"inOutFlag", 60));
						
						return list;
					}
				});
		jTable.getColumnModel().getColumn(1).setCellRenderer(
				new DefaultTableCellRenderer() {
					@Override
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : FptBusinessType.getFptBusinessTypeDesc(value.toString()));
						return this;
					}
				});		
		jTable.getColumnModel().getColumn(5).setCellRenderer(
				new DefaultTableCellRenderer() {
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
		jTable.getColumnModel().getColumn(6).setCellRenderer(
				new DefaultTableCellRenderer() {
					@Override
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : FptInOutFlag
								.getInOutFlagSpec(value.toString()));
						return this;
					}
				});
	
		jTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	}

	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJSplitPane(), BorderLayout.CENTER);
			jContentPane.add(getJToolBar(), BorderLayout.NORTH);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getTbSerach());
			jToolBar.add(getBtnSendMail());
			jToolBar.add(getBtnExit());
		}
		return jToolBar;
	}

	/**
	 * This method initializes btnSendMail
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSendMail() {
		if (btnSendMail == null) {
			btnSendMail = new JButton();
			btnSendMail.setText("处理邮件");
			btnSendMail.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(null, "请选择要处理的单据内容!",
								"提示", JOptionPane.INFORMATION_MESSAGE);
						return;
					}else{
						if (JOptionPane
								.showConfirmDialog(null,
										"请确认要处理的邮件是否与选择的单据类型是否一致!",
										"提示",
								JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
							return;
						}
					}
					new ExcEmailThread().start();
				}

			});
		}
		return btnSendMail;
	}

	class ExcEmailThread extends Thread {
		@Override
		public void run() {
			try {
				String taskId = CommonStepProgress.getExeTaskId();
				CommonStepProgress.showStepProgressDialog(taskId);
				CommonStepProgress.setStepMessage("系统正在写入数据，请稍后...");
				Request request = new Request(CommonVars.getCurrUser());
				request.setTaskId(taskId);
				try {
					TempFptEmail temp = (TempFptEmail) tableModel
							.getCurrentRow();
					fptManageAction.excEmail(new Request(CommonVars
							.getCurrUser()), email, temp);// TODO
					// Auto-generated
					CommonStepProgress.closeStepProgressDialog();
					JOptionPane.showMessageDialog(null, "处理数据成功！", "提示", 1);
				} catch (Exception ex) {
					CommonStepProgress.closeStepProgressDialog();

					JOptionPane.showMessageDialog(null, "处理邮件失败! "
							+ ex.getMessage(), "确认", 1);
				}
			} catch (Exception ex) {
				System.out.println(ex.getStackTrace() + "\n-->"
						+ ex.getMessage());
			} finally {
				CommonStepProgress.closeStepProgressDialog();
			}
		}
	}

	/**
	 * This method initializes btnExit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("关闭");
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnExit;
	}

	public FptEmail getEmail() {
		return email;
	}

	public void setEmail(FptEmail email) {
		this.email = email;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
//	private JPanel getJPanel() {
//		if (jPanel == null) {
//			jLabel = new JLabel();
//			jLabel.setText("请选择要处理到单据");
//			jLabel.setBounds(new Rectangle(7, 74, 156, 18));
//			jLabel2 = new JLabel();
//			jLabel2.setText("收/发送类型");
//			jLabel2.setBounds(new Rectangle(14, 13, 63, 18));
//		}
//		return jPanel;
//	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setBounds(new Rectangle(6, 46, 436, 324));
			jSplitPane.setDividerSize(0);
			jSplitPane.setDividerLocation(100);
			jSplitPane.setBottomComponent(getJScrollPane());
			jSplitPane.setTopComponent(getJSplitPane1());
			jSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		}
		return jSplitPane;
	}

	/**
	 * This method initializes tfSysType
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getTfSysType() {
		if (tfSysType == null) {
			tfSysType = new JComboBox();
			tfSysType.setBounds(new Rectangle(85, 9, 159, 27));
			tfSysType.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {

					}
				}
			});
		}
		return tfSysType;
	}

	public String getSmptServer() {
		return smptServer;
	}

	public void setSmptServer(String smptServer) {
		this.smptServer = smptServer;
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
	 * This method initializes tbSerach
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getTbSerach() {
		if (tbSerach == null) {
			tbSerach = new JButton();
			tbSerach.setText("查询");
			tbSerach.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tfSysType.getSelectedItem() == null) {
						JOptionPane.showMessageDialog(null, "请选择收/发送类型!", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					List lists = fptManageAction
							.findAppBillToFptEmail(new Request(CommonVars
									.getCurrUser()), ((ItemProperty) tfSysType
									.getSelectedItem()).getCode(),
									DeclareState.APPLY_POR, FptInOutFlag.IN,
									"0");
					ininTable(lists);
				}
			});
		}
		return tbSerach;
	}

	/**
	 * This method initializes textShow	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getTextShow() {
		if (textShow == null) {
			textShow = new JTextArea();
			textShow.setEditable(false);
			textShow.setLineWrap(true);
		}
		return textShow;
	}

	/**
	 * This method initializes jSplitPane1	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */
	private JSplitPane getJSplitPane1() {
		if (jSplitPane1 == null) {
			jSplitPane1 = new JSplitPane();
			jSplitPane1.setDividerSize(0);
			jSplitPane1.setLeftComponent(getJPanel1());
			jSplitPane1.setRightComponent(getJScrollPane1());
			jSplitPane1.setDividerLocation(260);
		}
		return jSplitPane1;
	}

	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(11, 13, 65, 18));
			jLabel3.setText("类型");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(4, 72, 170, 18));
			jLabel1.setText("选择要处理到单据");
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			//jPanel1.add(jLabel2, null);
			jPanel1.add(getTfSysType(), null);
			jPanel1.add(jLabel1, null);
			jPanel1.add(jLabel3, null);
			//jPanel1.add(jLabel, null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jScrollPane1	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getTextShow());
		}
		return jScrollPane1;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
