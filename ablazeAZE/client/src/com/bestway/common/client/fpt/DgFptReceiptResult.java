package com.bestway.common.client.fpt;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.FptBusinessType;
import com.bestway.common.constant.FptInOutFlag;
import com.bestway.common.constant.FptProcessResult;
import com.bestway.common.fpt.action.FptMessageAction;
import com.bestway.common.message.entity.CspReceiptResult;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgFptReceiptResult extends JDialogBase {
	private JPanel panel;
	private JScrollPane scrollPane;
	private JTable tbHead;
	private JButton btnOk;
	private JButton btnCancel;

	private JTableListModel tableModel;
	private JTableListModel tableModelDetail;
	private String sysType;
	private String inOutFlag;
	private String copEmsNo;
	private FptMessageAction fptMessageAction = null;
	private JSplitPane splitPane;
	private JScrollPane scrollPane_1;
	private JTable tbDetail;
	
	public String getSysType() {
		return sysType;
	}

	public void setSysType(String sysType) {
		this.sysType = sysType;
	}

	public String getInOutFlag() {
		return inOutFlag;
	}

	public void setInOutFlag(String inOutFlag) {
		this.inOutFlag = inOutFlag;
	}

	public String getCopEmsNo() {
		return copEmsNo;
	}

	public void setCopEmsNo(String copEmsNo) {
		this.copEmsNo = copEmsNo;
	}

	public DgFptReceiptResult() {
		initialize();
		fptMessageAction = (FptMessageAction) CommonVars
				.getApplicationContext().getBean("fptMessageAction");
	}
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(744, 454);
		setTitle("回执内容");		
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(getPanel(), BorderLayout.SOUTH);
		getContentPane().add(getSplitPane(), BorderLayout.CENTER);
	}

	@Override
	public void setVisible(boolean b) {
		if (b) {
			List list = fptMessageAction.findFptReceiptResultByCopNo(
					new Request(CommonVars.getCurrUser()), sysType, inOutFlag,copEmsNo);
			this.initTable(list);
			this.showDetailData();
		}
		super.setVisible(b);
	}

	private void initTable(List list) {
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("转入转出标记", "inOutFlag", 80));		
				list.add(addColumn("序号", "retNo", 50));
				// list.add(addColumn("经营单位代码", "tradeCode", 100));
				list.add(addColumn("企业内部编号", "copEmsNo", 150));
				// list.add(addColumn("核查/报核次数", "colDcrTime", 100));						
				list.add(addColumn("数据中心统一编号", "seqNo", 140));
				list.add(addColumn("处理结果", "chkMark", 100));
				if (FptBusinessType.FPT_APP.equals(sysType)) {
					list.add(addColumn("申请单号", "customsNo", 130));
				} else if (FptBusinessType.FPT_BILL.equals(sysType)
						|| FptBusinessType.FPT_BILL_BACK
								.equals(sysType)) {
					list.add(addColumn("收发货单号", "customsNo", 150));
				} else {
					list.add(addColumn("海关编号", "customsNo", 130));
				}
				list.add(addColumn("通知时间", "formatedNoticeDate", 120));
				list.add(addColumn("文件名称", "fileName", 300));
				return list;
			}
		};
		tableModel = new JTableListModel(tbHead, list, jTableListModelAdapter);
		tbHead.getColumnModel().getColumn(1).setCellRenderer(//转入转出标记
				new DefaultTableCellRenderer() {
					@Override
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : FptInOutFlag
								.getInOutFlagSpec(value.toString().trim()));
						return this;
					}

				});
		tbHead.getColumnModel().getColumn(5).setCellRenderer(//处理结果
				new DefaultTableCellRenderer() {
					@Override
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : FptProcessResult
								.getResultDesc(value.toString().trim()));
						return this;
					}
				});
		
	}
	
	/**
	 * 显示已处理接收的回执明细信息
	 * 
	 */
	private void showDetailData() {
		List list = new ArrayList();
		if (this.tableModel != null) {
			CspReceiptResult result = (CspReceiptResult) this.tableModel
					.getCurrentRow();
			if (result != null) {	
				list = fptMessageAction.findCspReceiptResultDetail(new Request(
						CommonVars.getCurrUser()), result);
			}
		}
		this.initDetailTable(list);
	}
	/**
	 * 显示已处理的回执的明细信息
	 * 
	 * @param list
	 */
	private void initDetailTable(List list) {
		tableModelDetail = new JTableListModel(tbDetail, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("信息明细", "resultInfo", 500));
						return list;
					}
				});
	}

	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.add(getBtnOk());
			panel.add(getBtnCancel());
		}
		return panel;
	}

	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTbHead());
		}
		return scrollPane;
	}

	private JTable getTbHead() {
		if (tbHead == null) {
			tbHead = new JTable();
			tbHead.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							System.out.println("---------------------------");
							showDetailData();
						}
					});
		}
		return tbHead;
	}

	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton("确定");
			btnOk.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
		}
		return btnOk;
	}

	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton("取消");
			btnCancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
		}
		return btnCancel;
	}
	private JSplitPane getSplitPane() {
		if (splitPane == null) {
			splitPane = new JSplitPane();
			splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			splitPane.setDividerLocation(200);
			splitPane.setLeftComponent(getScrollPane());
			splitPane.setRightComponent(getScrollPane_1());
		}
		return splitPane;
	}
	private JScrollPane getScrollPane_1() {
		if (scrollPane_1 == null) {
			scrollPane_1 = new JScrollPane();
			scrollPane_1.setViewportView(getTbDetail());
		}
		return scrollPane_1;
	}
	private JTable getTbDetail() {
		if (tbDetail == null) {
			tbDetail = new JTable();			
		}
		return tbDetail;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
