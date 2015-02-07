package com.bestway.bcus.client.message;

import javax.swing.JPanel;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.message.action.MessageAction;
import com.bestway.bcus.message.entity.MessageQuery;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.EdiType;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableCellRenderer;

import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.ImageIcon;
import java.awt.Font;
/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FmMessageQuery extends JInternalFrameBase {

	private MessageAction messageAction = null;

	private JTableListModel tableModelSend = null;

	private JTableListModel tableModelRecv = null;

	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JToolBar jToolBar = null;
	private JSplitPane jSplitPane = null;
	private JPanel jPanel2 = null;
	private JPanel jPanel3 = null;
	private JToolBar jToolBar1 = null;
	private JButton jButton6 = null;
	private JToolBar jToolBar2 = null;
	private JButton jButton7 = null;
	private JTable jTable = null;
	private JScrollPane jScrollPane = null;
	private JTable jTable1 = null;
	private JScrollPane jScrollPane1 = null;
	private JRadioButton jRadioButton = null;
	private JRadioButton jRadioButton1 = null;
	private JRadioButton jRadioButton2 = null;
	private JRadioButton jRadioButton3 = null;
	private JRadioButton jRadioButton4 = null;
	private JRadioButton jRadioButton5 = null;
	private ButtonGroup group = new ButtonGroup();

	private JButton jButton = null;
	/**
	 * This is the default constructor
	 */
	public FmMessageQuery() {
		super();
		messageAction = (MessageAction) CommonVars.getApplicationContext()
				.getBean("messageAction");
        messageAction.checkMessageBrowseAuthority(new Request(CommonVars.getCurrUser()));
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setContentPane(getJPanel());
		this.setTitle("报文收发信息查询");
		this.setSize(665, 390);
		group.add(jRadioButton);
		group.add(jRadioButton1);
		group.add(jRadioButton2);
		group.add(jRadioButton3);
		group.add(jRadioButton4);
		group.add(jRadioButton5);
		jRadioButton.getModel().setSelected(true);
		getList(EdiType.EMS_EDI_TR);
	}

	private void getList(int ediType) {
		List listSend = null;
		List listRecv = null;
		listSend = messageAction.findMessageSend(new Request(CommonVars
				.getCurrUser()), ediType,null,null);
		if (listSend != null && !listSend.isEmpty())
		  initTableSend(listSend);
		else
		  initTableSend(new Vector());
		listRecv = messageAction.findMessageRecv(new Request(CommonVars
				.getCurrUser()), ediType,null,null);
		if (listRecv != null && !listRecv.isEmpty())
		  initTableRecv(listRecv);
		else 
		  initTableRecv(new Vector());	

	}

	private void initTableSend(final List list) {
		tableModelSend = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("发送电脑名", "computerName", 80));
						list.add(addColumn("发送时间", "sendRecvTime", 80));
						list.add(addColumn("报文类型", "messageType", 60));
						list.add(addColumn("文件名", "fileName", 200));
						list.add(addColumn("企业编码", "copEmsNo", 100));
						list.add(addColumn("报文代码", "messageCode", 60));
						list.add(addColumn("发送者", "sendRecvEr", 80));
						return list;
					}
				});
		jTable.getColumnModel().getColumn(3).setCellRenderer(
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
							returnValue = "备案";
						} else if (value.equals("2")) {
							returnValue = "变更";
						}
						return returnValue;
					}
				});
	}

	private void initTableRecv(final List list) {
		tableModelRecv = new JTableListModel(jTable1, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("接收电脑名", "computerName", 100));
						list.add(addColumn("接收时间", "sendRecvTime", 100));
						list.add(addColumn("报文类型", "messageType", 120));
						list.add(addColumn("审核结果", "checkResult", 100));
						list.add(addColumn("文件名", "fileName", 200));
						list.add(addColumn("企业编号", "copEmsNo", 80));
						list.add(addColumn("报文代码", "messageCode", 100));
						list.add(addColumn("接收者", "sendRecvEr", 100));
						return list;
					}
				});
		jTable1.getColumnModel().getColumn(3).setCellRenderer(
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
							returnValue = "备案";
						} else if (value.equals("2")) {
							returnValue = "变更";
						}
						return returnValue;
					}
				});
		jTable1.getColumnModel().getColumn(4).setCellRenderer(
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
							returnValue = "入库成功";
						} else if (value.equals("2")) {
							returnValue = "审批通过";
						} else if (value.equals("3")) {
							returnValue = "退单";
						} else if (value.equals("4")) {
							returnValue = "重复申报(退单)";
						} else if (value.equals("5")) {
							returnValue = "入库成功(自动审核检查通过)";
						} else if (value.equals("0")){
							returnValue = "";
						}
						return returnValue;
					}
				});	
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
			jPanel.add(getJPanel1(), java.awt.BorderLayout.NORTH);
			jPanel.add(getJToolBar(), java.awt.BorderLayout.SOUTH);
			jPanel.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
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
			javax.swing.JLabel jLabel2 = new JLabel();

			javax.swing.JLabel jLabel1 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();

			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jLabel.setText("报文收发信息查询");
			jLabel.setFont(new Font("\u65b0\u5b8b\u4f53", Font.BOLD, 18));
			jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
			jPanel1.setBackground(java.awt.Color.white);
			jLabel1.setText("");
			jLabel1.setIcon(new ImageIcon(getClass().getResource("/com/bestway/bcus/client/resources/images/titlepic.jpg")));
			jLabel2.setText("");
			jLabel2.setIcon(new ImageIcon(getClass().getResource("/com/bestway/bcus/client/resources/images/titlepoint.jpg")));
			jPanel1.add(jLabel, java.awt.BorderLayout.CENTER);
			jPanel1.add(jLabel1, java.awt.BorderLayout.EAST);
			jPanel1.add(jLabel2, java.awt.BorderLayout.WEST);
		}
		return jPanel1;
	}

	/**

	 * This method initializes jToolBar	

	 * 	

	 * @return javax.swing.JToolBar	

	 */    
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.setFloatable(false);
			jToolBar.setBackground(java.awt.Color.white);
			jToolBar.add(getJRadioButton());
			jToolBar.add(getJRadioButton1());
			jToolBar.add(getJRadioButton2());
			jToolBar.add(getJRadioButton3());
			jToolBar.add(getJRadioButton4());
			jToolBar.add(getJRadioButton5());
		}
		return jToolBar;
	}

	/**

	 * This method initializes jSplitPane	

	 * 	

	 * @return javax.swing.JSplitPane	

	 */    
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setDividerSize(10);
			jSplitPane.setDividerLocation(335);
			jSplitPane.setOneTouchExpandable(true);
			jSplitPane.setLeftComponent(getJPanel2());
			jSplitPane.setRightComponent(getJPanel3());
		}
		return jSplitPane;
	}

	/**

	 * This method initializes jPanel2	

	 * 	

	 * @return javax.swing.JPanel	

	 */    
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(new BorderLayout());
			jPanel2.add(getJToolBar1(), java.awt.BorderLayout.SOUTH);
			jPanel2.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel2;
	}

	/**

	 * This method initializes jPanel3	

	 * 	

	 * @return javax.swing.JPanel	

	 */    
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setLayout(new BorderLayout());
			jPanel3.add(getJToolBar2(), java.awt.BorderLayout.SOUTH);
			jPanel3.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel3;
	}

	/**

	 * This method initializes jToolBar1	

	 * 	

	 * @return javax.swing.JToolBar	

	 */    
	private JToolBar getJToolBar1() {
		if (jToolBar1 == null) {
			jToolBar1 = new JToolBar();
			jToolBar1.add(getJButton6());
			jToolBar1.add(getJButton());
		}
		return jToolBar1;
	}

	/**

	 * This method initializes jButton6	

	 * 	

	 * @return javax.swing.JButton	

	 */    
	private JButton getJButton6() {
		if (jButton6 == null) {
			jButton6 = new JButton();
			jButton6.setText("删除");
			jButton6.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {    

					if (tableModelSend.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmMessageQuery.this,
								"请选择你要删除的资料", "确认", 1);
						return;
					}

					MessageQuery messageQuery = (MessageQuery) tableModelSend
							.getCurrentRow();

					if (JOptionPane.showConfirmDialog(FmMessageQuery.this,
							"确定要删除此记录吗?", "确认", 0) == 0) {
						messageAction.deleteMessage(new Request(CommonVars
								.getCurrUser()), messageQuery);
						tableModelSend.deleteRow(messageQuery);

					}
				}
			});


		}
		return jButton6;
	}

	/**

	 * This method initializes jToolBar2	

	 * 	

	 * @return javax.swing.JToolBar	

	 */    
	private JToolBar getJToolBar2() {
		if (jToolBar2 == null) {
			jToolBar2 = new JToolBar();
			jToolBar2.add(getJButton7());
		}
		return jToolBar2;
	}

	/**

	 * This method initializes jButton7	

	 * 	

	 * @return javax.swing.JButton	

	 */    
	private JButton getJButton7() {
		if (jButton7 == null) {
			jButton7 = new JButton();
			jButton7.setText("删除");
			jButton7.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {    

					if (tableModelRecv.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmMessageQuery.this,
								"请选择你要删除的资料", "确认", 1);
						return;
					}

					MessageQuery messageQuery = (MessageQuery) tableModelRecv
							.getCurrentRow();

					if (JOptionPane.showConfirmDialog(FmMessageQuery.this,
							"确定要删除此记录吗?", "确认", 0) == 0) {
						messageAction.deleteMessage(new Request(CommonVars
								.getCurrUser()), messageQuery);
						tableModelRecv.deleteRow(messageQuery);
					}
				}
			});

		}
		return jButton7;
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

	 * This method initializes jScrollPane	

	 * 	

	 * @return javax.swing.JScrollPane	

	 */    
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTable());
			jScrollPane.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
		}
		return jScrollPane;
	}

	/**

	 * This method initializes jTable1	

	 * 	

	 * @return javax.swing.JTable	

	 */    
	private JTable getJTable1() {
		if (jTable1 == null) {
			jTable1 = new JTable();
		}
		return jTable1;
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
			jScrollPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
		}
		return jScrollPane1;
	}

	/**

	 * This method initializes jRadioButton	

	 * 	

	 * @return javax.swing.JRadioButton	

	 */    
	private JRadioButton getJRadioButton() {
		if (jRadioButton == null) {
			jRadioButton = new JRadioButton();
			jRadioButton.setText("经营范围");
			jRadioButton.setName("emsEdiTr");
			jRadioButton.setBackground(java.awt.Color.white);
			jRadioButton.addActionListener(new RadioActionListner());
		}
		return jRadioButton;
	}

	/**

	 * This method initializes jRadioButton1	

	 * 	

	 * @return javax.swing.JRadioButton	

	 */    
	private JRadioButton getJRadioButton1() {
		if (jRadioButton1 == null) {
			jRadioButton1 = new JRadioButton();
			jRadioButton1.setText("归并关系(前)");
			jRadioButton1.setName("emsEdiMergerBefore");
			jRadioButton1.setBackground(java.awt.Color.white);
			jRadioButton1.addActionListener(new RadioActionListner());
		}
		return jRadioButton1;
	}

	/**

	 * This method initializes jRadioButton2	

	 * 	

	 * @return javax.swing.JRadioButton	

	 */    
	private JRadioButton getJRadioButton2() {
		if (jRadioButton2 == null) {
			jRadioButton2 = new JRadioButton();
			jRadioButton2.setText("归并关系(后)");
			jRadioButton2.setName("emsEdiMergerAfter");
			jRadioButton2.setBackground(java.awt.Color.white);
			jRadioButton2.addActionListener(new RadioActionListner());
		}
		return jRadioButton2;
	}

	/**

	 * This method initializes jRadioButton3	

	 * 	

	 * @return javax.swing.JRadioButton	

	 */    
	private JRadioButton getJRadioButton3() {
		if (jRadioButton3 == null) {
			jRadioButton3 = new JRadioButton();
			jRadioButton3.setText("电子帐册");
			jRadioButton3.setName("emsEdi");
			jRadioButton3.setBackground(java.awt.Color.white);
			jRadioButton3.addActionListener(new RadioActionListner());
		}
		return jRadioButton3;
	}

	/**

	 * This method initializes jRadioButton4	

	 * 	

	 * @return javax.swing.JRadioButton	

	 */    
	private JRadioButton getJRadioButton4() {
		if (jRadioButton4 == null) {
			jRadioButton4 = new JRadioButton();
			jRadioButton4.setText("联网核查");
			jRadioButton4.setName("check");
			jRadioButton4.setBackground(java.awt.Color.white);
			jRadioButton4.addActionListener(new RadioActionListner());
		}
		return jRadioButton4;
	}
	private class RadioActionListner implements ActionListener { 
		public void actionPerformed(java.awt.event.ActionEvent e) { 			
			if(((JRadioButton)e.getSource()).getName().equals("emsEdiTr")){
				getList(EdiType.EMS_EDI_TR);
			}
			else if(((JRadioButton)e.getSource()).getName().equals("emsEdiMergerBefore")){
				getList(EdiType.EMS_EDI_MERGER_BEFORE);		
			}
			if(((JRadioButton)e.getSource()).getName().equals("emsEdiMergerAfter")){
				getList(EdiType.EMS_EDI_MERGER_AFTER);
			}
			else if(((JRadioButton)e.getSource()).getName().equals("emsEdi")){
				getList(EdiType.EMS_EDI_H2K);			
			}
			if(((JRadioButton)e.getSource()).getName().equals("check")){
				getList(EdiType.CHECK);
			}
			else if(((JRadioButton)e.getSource()).getName().equals("cancel")){
				getList(EdiType.CANCEL);			
			}
		}
	}
	/**

	 * This method initializes jRadioButton5	

	 * 	

	 * @return javax.swing.JRadioButton	

	 */    
	private JRadioButton getJRadioButton5() {
		if (jRadioButton5 == null) {
			jRadioButton5 = new JRadioButton();
			jRadioButton5.setText("帐册报核");
			jRadioButton5.setName("cancel");
			jRadioButton5.setBackground(java.awt.Color.white);
			jRadioButton5.addActionListener(new RadioActionListner());
		}
		return jRadioButton5;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("查看");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.out.println("actionPerformed()"); // 
				}
			});
		}
		return jButton;
	}

              }  //  @jve:decl-index=0:visual-constraint="10,10"
