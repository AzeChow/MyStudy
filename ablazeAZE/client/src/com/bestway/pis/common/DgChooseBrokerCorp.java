package com.bestway.pis.common;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.BaseEntity;
import com.bestway.common.Request;
import com.bestway.pis.action.PisAction;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgChooseBrokerCorp extends JDialogBase {
	private JPanel panel;
	private JToolBar toolBar;
	private JTextField tfQuery;
	private JLabel lbQuery;
	private JToolBar toolBar_1;
	private JScrollPane scrollPane;
	private JTable table;
	private JButton btnOk;
	private JButton btnCancel;

	private JTableListModel tableModel = null;
	private PisAction pisAction = null;
	protected boolean isMulti = false;
	private Object returnValue;
	public boolean isOk = false;

	public DgChooseBrokerCorp() {
		this.setBounds(new Rectangle(800, 500));
		this.setTitle("请选择代理报关公司");
		pisAction = (PisAction) CommonVars.getApplicationContext().getBean(
				"pisAction");
		getContentPane().add(getPanel(), BorderLayout.CENTER);
	}

	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setLayout(new BorderLayout(0, 0));
			panel.add(getToolBar(), BorderLayout.NORTH);
			panel.add(getToolBar_1(), BorderLayout.SOUTH);
			panel.add(getScrollPane(), BorderLayout.CENTER);
		}
		return panel;
	}

	private JToolBar getToolBar() {
		if (toolBar == null) {
			toolBar = new JToolBar();
			FlowLayout f = new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(0);
			f.setHgap(0);
			toolBar.setLayout(f);
			toolBar.add(getLbQuery());
			toolBar.add(getTfQuery());
			toolBar.add(getBtnQuery());
		}
		return toolBar;
	}

	private JTextField getTfQuery() {
		if (tfQuery == null) {
			tfQuery = new JTextField();
			tfQuery.setColumns(10);
		}
		return tfQuery;
	}

	private JLabel getLbQuery() {
		if (lbQuery == null) {
			lbQuery = new JLabel("加工单位海关编码");
		}
		return lbQuery;
	}

	private JToolBar getToolBar_1() {
		if (toolBar_1 == null) {
			toolBar_1 = new JToolBar();
			FlowLayout f = new FlowLayout();
			f.setAlignment(FlowLayout.CENTER);
			f.setVgap(0);
			f.setHgap(0);
			toolBar_1.setLayout(f);
			toolBar_1.add(getBtnOk());
			toolBar_1.add(getBtnCancel());
		}
		return toolBar_1;
	}

	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTable());
		}
		return scrollPane;
	}

	private JTable getTable() {
		if (table == null) {
			table = new JTable();
			table.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 2) {
						btnOk.doClick();
					}
				}
			});
		}
		return table;
	}

	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton("确定");
			btnOk.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					if (isMulti) {
						List<BaseEntity> res = tableModel.getCurrentRows();
						if (res.isEmpty()) {
							JOptionPane.showMessageDialog(
									DgChooseBrokerCorp.this, "必须选择一条记录！", "提示",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						setReturnValue(res);
					} else {
						List<BaseEntity> res = tableModel.getCurrentRows();

						if (res.isEmpty()) {
							JOptionPane.showMessageDialog(
									DgChooseBrokerCorp.this, "必须选择一条记录！", "提示",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}

						if (res.size() > 1) {
							JOptionPane.showMessageDialog(
									DgChooseBrokerCorp.this, "只能选择一条记录！", "提示",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						setReturnValue(res.get(0));
					}

					isOk = true;
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
					isOk = false;
					dispose();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * 初始化报关单商品信息
	 */
	protected void initTable(List list) {
		tableModel = new JTableListModel(table, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(new JTableListColumn("代理公司名称", "orgaName", 100));
						list.add(new JTableListColumn("服务器地址",
								"pisEspServer.serverAddress", 120));
						list.add(new JTableListColumn("端口",
								"pisEspServer.portNumber", 80));
						list.add(new JTableListColumn("邮箱", "email", 150));
						list.add(new JTableListColumn("联系人", "linkMan", 80));
						list.add(new JTableListColumn("手机号码", "phoneNumber", 80));
						list.add(new JTableListColumn("关区代码", "customs.name",
								80));
						return list;
					}
				});
	}

	private String espMainBusinessType = null;
	private JButton btnQuery;

	private List getDataSource() {
		return pisAction.findBrokerCorpByMainBusiness(
				new Request(CommonVars.getCurrUser()), espMainBusinessType);
	}

	@Override
	public void setVisible(boolean b) {
		if (b) {
			initTable(getDataSource());
		}
		super.setVisible(b);
	}

	public PisAction getPisAction() {
		return pisAction;
	}

	public void setPisAction(PisAction pisAction) {
		this.pisAction = pisAction;
	}

	public String getEspMainBusinessType() {
		return espMainBusinessType;
	}

	public void setEspMainBusinessType(String espMainBusinessType) {
		this.espMainBusinessType = espMainBusinessType;
	}

	public void setIsMulti(boolean isMulti) {
		this.isMulti = isMulti;
	}

	public Object getReturnValue() {
		return returnValue;
	}

	public void setReturnValue(Object returnValue) {
		this.returnValue = returnValue;
	}

	private JButton getBtnQuery() {
		if (btnQuery == null) {
			btnQuery = new JButton("查询");
			btnQuery.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

				}
			});
		}
		return btnQuery;
	}
}
