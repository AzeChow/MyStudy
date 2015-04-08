package com.bestway.pis.sync;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.system.entity.Company;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.client.common.tableeditor.CheckBoxHeader;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.authority.entity.AclUser;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.pis.action.PisAction;
import com.bestway.pis.action.PisVerificationAuthority;
import com.bestway.pis.common.HttpClientInvoker;
import com.bestway.pis.constant.EspMainBusinessType;
import com.bestway.pis.constant.PisSyncState;
import com.bestway.pis.entity.AclUserTemp;
import com.bestway.pis.entity.BrokerCorp;
import com.bestway.pis.entity.EspAuthorityBrokerCorp;
import com.bestway.pis.entity.EspAuthorityBrokerCorpItem;
import com.bestway.pis.entity.PisEspServer;
import com.bestway.pis.entity.PisSyncAclUser;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class FmPisSync extends JInternalFrameBase {
	private JSplitPane splitPane;
	private JPanel panel;
	private JLabel lblNewLabel;
	private JLabel label;
	private JLabel label_1;
	private JLabel label_2;
	private JLabel lblcis;
	private JLabel lblcisesp;
	private JLabel lblcisesp_1;
	private JLabel lblcisesp_2;
	private JLabel label_3;
	private JLabel label_4;
	private JLabel label_5;
	private JTabbedPane tabbedPane;
	private JPanel panel_1;
	private JPanel panel_2;
	private JPanel panel_3;
	private JPanel panel_4;
	private JToolBar toolBar;
	private JScrollPane scrollPane;
	private JTable tbSyncBrokerCorp;
	private JButton btnExecute;
	private JSplitPane splitPane_1;
	private JPanel panel_5;
	private JPanel panel_6;
	private JToolBar toolBar_1;
	private JScrollPane scrollPane_1;
	private JTable tbAuthorityBrokerCorp;
	private JToolBar toolBar_2;
	private JScrollPane scrollPane_2;
	private JTable tbAuthorityBrokerCorpItem;
	private JButton btnSelectBrokerCorp;
	private JButton btnDelSelect;
	private JButton btnRefreshThird;
	private JButton btnExecuteSyncThird;
	private JButton btnAddThird;
	private JButton btnEditThird;
	private JButton btnDeleteThird;
	private JToolBar toolBar_3;
	private JScrollPane scrollPane_3;
	private JTable tbSyncUser;
	private JButton btnExecuteSyncUser;
	private JButton btnRefresh;
	private JToolBar toolBar_4;
	private JButton btnExecuteSyncAgentCorp;
	private JScrollPane scrollPane_4;
	private JTable tbSyncAgentCorp;

	private PisAction pisAction = null;
	private PisVerificationAuthority pisVerificationAuthority = null;
	/**
	 * ①同步代理公司
	 */
	private JTableListModel tableModelSyncBrokerCorp = null;
	/**
	 * ②同步账号
	 */
	private JTableListModel tableModelSyncUser = null;
	/**
	 * ③授权代理公司
	 */
	private JTableListModel tableModelAuthorityBrokerCorp = null;
	/**
	 * ③授权代理公司
	 */
	private JTableListModel tableModelAuthorityBrokerCorpItem = null;
	/**
	 * ④同步申报单位
	 */
	private JTableListModel tableModelAgentCorp = null;

	private Request request = null;

	private List cache = null;

	public FmPisSync() {

		pisAction = (PisAction) CommonVars.getApplicationContext().getBean(
				"pisAction");

		pisVerificationAuthority = (PisVerificationAuthority) CommonVars
				.getApplicationContext().getBean("PisVerificationAuthority");

		request = new Request(CommonVars.getCurrUser());

		pisVerificationAuthority.checkPisSync(request);

		getContentPane().setLayout(new BorderLayout(0, 0));

		getContentPane().add(getSplitPane(), BorderLayout.CENTER);

		initOther();

	}

	public void initOther() {

		findBrokerCorp();

		findPisSyncAclUser();

//		findPisSyncAgentCorp();

//		findEspAuthorityBrokerCorp();

//		tbAuthorityBrokerCorp.getSelectionModel().addListSelectionListener(
//				new ListSelectionListener() {
//					@Override
//					public void valueChanged(ListSelectionEvent e) {
//						if (e.getValueIsAdjusting()) {
//							return;
//						}
//						if (tableModelAuthorityBrokerCorp.getCurrentRow() != null) {
//
//							EspAuthorityBrokerCorp corp = (EspAuthorityBrokerCorp) tableModelAuthorityBrokerCorp
//									.getCurrentRow();
//							findEspAuthorityBrokerCorpItem(corp);
//						}
//						setItemStatus(DataState.BROWSE);
//					}
//				});
	}

	public void findBrokerCorp() {
		List brokerList = this.pisAction.findBrokerCorp(new Request(CommonVars
				.getCurrUser()));
		initTableBrokerCorp(brokerList);
	}

	/**
	 * 查找同步用户
	 */
	public void findPisSyncAclUser() {

		List list = pisAction.findPisSyncAclUser(new Request(CommonVars
				.getCurrUser()));

		initTableAclUser(list);
	}

	public void findPisSyncAgentCorp() {
		List list = this.pisAction.findPisSyncAgentCorp(new Request(CommonVars
				.getCurrUser()));
		initTableAgentCorp(list);
	}

	public void findEspAuthorityBrokerCorp() {

		List list = this.pisAction.findEspAuthorityBrokerCorp(new Request(
				CommonVars.getCurrUser()));

		initTableAuthorityBrokerCorp(list);
		EspAuthorityBrokerCorp corp = (EspAuthorityBrokerCorp) tableModelAuthorityBrokerCorp
				.getCurrentRow();

		if (corp != null) {
			findEspAuthorityBrokerCorpItem(corp);
		} else {
			initTableAuthorityBrokerCorpItem(new ArrayList());
		}
	}

	public void findEspAuthorityBrokerCorpItem(
			EspAuthorityBrokerCorp espAuthorityBrokerCorp) {
		List list = this.pisAction.findEspAuthorityBrokerCorpItem(new Request(
				CommonVars.getCurrUser()), espAuthorityBrokerCorp);
		initTableAuthorityBrokerCorpItem(list);
	}

	// 明细行点击事件
	private void setItemStatus(int state) {
		this.btnEditThird.setEnabled(state == DataState.BROWSE);
	}

	private JTableListModel initTableBrokerCorp(List list) {
		JTableListModelAdapter adapter = new JTableListModelAdapter() {

			@Override
			public List InitColumns() {
				List<JTableListColumn> list = new ArrayList();
				list.add(new JTableListColumn("代理公司名称", "orgaName", 200));
				list.add(new JTableListColumn("服务器地址",
						"pisEspServer.serverAddress", 120));
				list.add(new JTableListColumn("端口", "pisEspServer.portNumber",
						80));
				list.add(new JTableListColumn("邮箱", "email", 150));
				list.add(new JTableListColumn("联系人", "linkMan", 80));

				list.add(new JTableListColumn("手机号码", "phoneNumber", 80));
				list.add(new JTableListColumn("关区代码", "customs.name", 80));
				// list.add(new JTableListColumn("是否停用", "customs.isOut", 80));
				list.add(new JTableListColumn("状态", "syncState", 80));
				return list;
			}
		};

		JTableListModel.dataBind(tbSyncBrokerCorp, list, adapter,
				ListSelectionModel.SINGLE_SELECTION);

		tbSyncBrokerCorp.getColumnModel().getColumn(8)
				.setCellRenderer(new TableCellRenderer() {

					@Override
					public Component getTableCellRendererComponent(
							JTable paramJTable, Object paramObject,
							boolean paramBoolean1, boolean paramBoolean2,
							int paramInt1, int paramInt2) {

						return null;
					}

				});
		tableModelSyncBrokerCorp = (JTableListModel) tbSyncBrokerCorp
				.getModel();

		// tableModelSyncBrokerCorp.setTableCellStyleListener(new
		// String[]{"syncState"}, new TableCellStyleListener() {
		// @Override
		// public Map<Integer, Object> callBack(TableCellStyleParameter parm) {
		// Map<Integer, Object> map = new HashMap();
		// Object value = parm.getCellValue();
		// String svalue = value != null ?
		// PisSyncState.getNameByCode(value.toString()) : "";
		// map.put(TableCellStyleType.SHOW_TEXT, svalue);
		// return map;
		// }
		// });

		// tableModelSyncBrokerCorp.setExportDataAuthorityId("core.user_export");
		return tableModelSyncBrokerCorp;

	}

	/**
	 * 初始化用户信息 用于同步企业登录帐号
	 * 
	 * @param list
	 * @return
	 */
	private JTableListModel initTableAclUser(List list) {
		JTableListModelAdapter adapter = new JTableListModelAdapter() {

			@Override
			public List InitColumns() {

				List<JTableListColumn> list = new ArrayList();

				// 选中属性
				list.add(new JTableListColumn("", "isSelected", 70));

				list.add(new JTableListColumn("登录名", "loginName", 100));

				list.add(new JTableListColumn("用户名称", "userName", 120));

				list.add(new JTableListColumn("用户类型", "userFlagCN", 80));

				list.add(new JTableListColumn("邮箱", "email", 150));

				list.add(new JTableListColumn("是否停用", "isForbid", 80));

				list.add(new JTableListColumn("同步状态", "syncState", 90));

				list.add(new JTableListColumn("同步信息", "info", 360));

				return list;
			}
		};

		// 设置可编辑列 1
		adapter.setEditableColumn(1);

		// 数据绑定
		JTableListModel.dataBind(tbSyncUser, list, adapter,
				ListSelectionModel.SINGLE_SELECTION);

		tableModelSyncUser = (JTableListModel) tbSyncUser.getModel();

		JCheckBox checkBox = new JCheckBox();

		tbSyncUser.getColumnModel().getColumn(1)
				.setCellRenderer(new SubDefaultTableCellRenderer(checkBox));

		tbSyncUser.getColumnModel().getColumn(1)
				.setCellEditor(new SubDefaultCellEditor(checkBox));

		tbSyncUser.getColumnModel().getColumn(1)
				.setHeaderRenderer(new CheckBoxHeader(false));

		tbSyncUser.getColumnModel().getColumn(7)
				.setCellRenderer(new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {

						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);

						String str = "";

						if (value != null) {
							str = value != null ? PisSyncState
									.getNameByCode(value.toString()) : "";
						}

						setText(str);

						return this;
					}
				});

		tbSyncUser.getColumnModel().getColumn(8)
				.setCellRenderer(new SyncStateChangeColor());

		return tableModelSyncUser;

	}

	private JTableListModel initTableAgentCorp(List list) {
		JTableListModelAdapter adapter = new JTableListModelAdapter() {

			@Override
			public List InitColumns() {
				List<JTableListColumn> list = new ArrayList();
				list.add(new JTableListColumn("海关注册编码", "briefCode", 80));
				list.add(new JTableListColumn("海关企业名称", "briefName", 165));
				list.add(new JTableListColumn("联系人", "linkMan", 60));
				list.add(new JTableListColumn("联系电话", "linkTel", 100));
				list.add(new JTableListColumn("联系地址", "address", 350));
				list.add(new JTableListColumn("同步状态", "syncState", 90));
				list.add(new JTableListColumn("同步信息", "info", 300));
				return list;
			}
		};
		JTableListModel.dataBind(tbSyncAgentCorp, list, adapter,
				ListSelectionModel.SINGLE_SELECTION);

		tableModelAgentCorp = (JTableListModel) tbSyncAgentCorp.getModel();

		tbSyncAgentCorp.getColumnModel().getColumn(6)
				.setCellRenderer(new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						String str = "";
						if (value != null) {
							str = value != null ? PisSyncState
									.getNameByCode(value.toString()) : "";
						}
						this.setText(str);
						return this;
					}
				});

		return tableModelAgentCorp;

	}

	private JTableListModel initTableAuthorityBrokerCorp(List list) {
		JTableListModelAdapter adapter = new JTableListModelAdapter() {

			@Override
			public List InitColumns() {
				List<JTableListColumn> list = new ArrayList();
				list.add(new JTableListColumn("代理公司名称", "brokerCorp.orgaName",
						100));
				list.add(new JTableListColumn("服务器地址",
						"brokerCorp.pisEspServer.serverAddress", 120));

				list.add(new JTableListColumn("端口",
						"brokerCorp.pisEspServer.portNumber", 80));

				list.add(new JTableListColumn("邮箱", "brokerCorp.email", 150));

				list.add(new JTableListColumn("联系人", "brokerCorp.linkMan", 80));

				list.add(new JTableListColumn("手机号码", "brokerCorp.phoneNumber",
						80));
				list.add(new JTableListColumn("关区代码",
						"brokerCorp.customs.name", 80));

				list.add(new JTableListColumn("同步状态", "syncState", 90));

				list.add(new JTableListColumn("同步信息", "info", 300));
				return list;
			}
		};

		JTableListModel.dataBind(tbAuthorityBrokerCorp, list, adapter,
				ListSelectionModel.SINGLE_SELECTION);

		tableModelAuthorityBrokerCorp = (JTableListModel) tbAuthorityBrokerCorp
				.getModel();

		tbAuthorityBrokerCorp.getColumnModel().getColumn(8)
				.setCellRenderer(new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);

						String str = "";
						if (value != null) {
							str = value != null ? PisSyncState
									.getNameByCode(value.toString()) : "";
						}
						this.setText(str);

						return this;
					}
				});

		return tableModelAuthorityBrokerCorp;

	}

	private JTableListModel initTableAuthorityBrokerCorpItem(List list) {
		JTableListModelAdapter adapter = new JTableListModelAdapter() {

			@Override
			public List InitColumns() {
				List<JTableListColumn> list = new ArrayList();
				list.add(new JTableListColumn("主营业务", "mainBusiness", 200));
				// list.add(new JTableListColumn("IC卡号", "icCard", 120));
				list.add(new JTableListColumn("操作授权", "optionStatus", 80));
				return list;
			}
		};
		JTableListModel.dataBind(tbAuthorityBrokerCorpItem, list, adapter,
				ListSelectionModel.SINGLE_SELECTION);
		tableModelAuthorityBrokerCorpItem = (JTableListModel) tbAuthorityBrokerCorpItem
				.getModel();
		tbAuthorityBrokerCorpItem.getColumnModel().getColumn(2)
				.setCellRenderer(new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						String str = "";
						if (value != null) {
							str = DataState.getNameByCode(Integer
									.parseInt(value.toString()));
						}
						this.setText(str);

						return this;
					}
				});
		tbAuthorityBrokerCorpItem.getColumnModel().getColumn(1)
				.setCellRenderer(new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						String str = "";
						if (value != null) {
							str = EspMainBusinessType.getNameByCode(value
									.toString());
						}
						this.setText(str);
						return this;
					}
				});
		// 设置行编辑
		// tableModelAuthorityBrokerCorpItem.setTableEditColumns(new
		// String[]{"mainBusiness", "icCard", "optionStatus"});

		// 设置主营业务下拉框
		// ComboBoxTableCellEditor mainComboBoxTableCellEditor =
		// (ComboBoxTableCellEditor)
		// this.tableModelAuthorityBrokerCorpItem.getColumnByName("mainBusiness").getCellEditor();
		// mainComboBoxTableCellEditor.setModel(CommonModel.getPisMainBusinessTypeModel());

		// 设置授权操作数据状态
		// ComboBoxTableCellEditor optionStatusComboBoxTableCellEditor =
		// (ComboBoxTableCellEditor)
		// this.tableModelAuthorityBrokerCorpItem.getColumnByName("optionStatus").getCellEditor();
		// JFilterComboBox combox = ((JFilterComboBox)
		// optionStatusComboBoxTableCellEditor.getComboBox());
		// combox.setCodeFieldWidth(25);
		// combox.setTextFieldWidth(25);
		// optionStatusComboBoxTableCellEditor.setModel(CommonModel.getPisOperationStatusModel());

		// 授权操作
		// tableModelAuthorityBrokerCorpItem.setTableCellStyleListener(new
		// String[]{"optionStatus"}, new TableCellStyleListener() {
		// @Override
		// public Map<Integer, Object> callBack(TableCellStyleParameter parm) {
		// Map<Integer, Object> map = new HashMap();
		// EspAuthorityBrokerCorpItem item = (EspAuthorityBrokerCorpItem)
		// parm.getRowData();
		// if (item != null) {
		// Object value = parm.getCellValue();
		// //map.put(TableCellStyleType.SHOW_TEXT,
		// OperationStatus.getOperationStatusDesc(item.getOperationStatusByDesc()));
		// map.put(TableCellStyleType.SHOW_TEXT, (value == null) ? null :
		// EspOperationStatus.getNameByCode(value.toString()));
		// }
		// return map;
		// }
		// });

		return tableModelAuthorityBrokerCorpItem;

	}

	private JSplitPane getSplitPane() {
		if (splitPane == null) {
			splitPane = new JSplitPane();
			splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			splitPane.setLeftComponent(getPanel());
			splitPane.setRightComponent(getTabbedPane());
			splitPane.setDividerLocation(0);
			splitPane.setDividerSize(0);
		}
		return splitPane;
	}

	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setBorder(new TitledBorder(
					UIManager.getBorder("TitledBorder.border"),
					"\u2460\u540C\u6B65\u4EE3\u7406\u516C\u53F8--\u2461\u540C\u6B65\u6388\u6743\u4EE3\u7406\u516C\u53F8--\u2462\u540C\u6B65\u4F01\u4E1A\u767B\u5F55\u8D26\u53F7--\u2463\u540C\u6B65\u4EE3\u7406\u516C\u53F8\u7684\u7533\u62A5\u5355\u4F4D\uFF1B\u8FD0\u7EF4\u5E73\u53F0\u7B80\u79F0BCGS\uFF1B\u5173\u52A1\u4FE1\u606F\u5316\u7BA1\u7406\u7CFB\u7EDF\u7B80\u79F0CIS\uFF1B\u62A5\u5173\u4FDD\u7A0E\u7269\u6D41\u5E73\u53F0\u7B80\u79F0ESP",
					TitledBorder.LEFT, TitledBorder.TOP, null, new Color(255,
							0, 0)));
			panel.setLayout(null);
			panel.add(getLblNewLabel());
			panel.add(getLabel());
			panel.add(getLabel_1());
			panel.add(getLabel_2());
			panel.add(getLblcis());
			panel.add(getLblcisesp());
			panel.add(getLblcisesp_1());
			panel.add(getLblcisesp_2());
			panel.add(getLabel_3());
			panel.add(getLabel_4());
			panel.add(getLabel_5());
		}
		return panel;
	}

	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("①同步代理公司------------");
			lblNewLabel.setBounds(57, 32, 160, 15);
		}
		return lblNewLabel;
	}

	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel("②同步授权代理公司--------");
			label.setBounds(57, 57, 160, 15);
		}
		return label;
	}

	private JLabel getLabel_1() {
		if (label_1 == null) {
			label_1 = new JLabel("③同步企业登录账号--------");
			label_1.setBounds(57, 82, 160, 15);
		}
		return label_1;
	}

	private JLabel getLabel_2() {
		if (label_2 == null) {
			label_2 = new JLabel("④同步代理公司的申报单位--");
			label_2.setBounds(57, 107, 160, 15);
		}
		return label_2;
	}

	private JLabel getLblcis() {

		if (lblcis == null) {

			lblcis = new JLabel("将运维平台代理公司、服务器地址、电话、联系人等资料同步至JBCUS；");

			lblcis.setBounds(216, 32, 407, 15);
		}
		return lblcis;
	}

	private JLabel getLblcisesp() {
		if (lblcisesp == null) {
			lblcisesp = new JLabel("将JBCUS授权代理公司同步至ESP；");

			lblcisesp.setBounds(216, 57, 207, 15);
		}
		return lblcisesp;
	}

	private JLabel getLblcisesp_1() {
		if (lblcisesp_1 == null) {

			lblcisesp_1 = new JLabel("将JBCUS用户账号同步至ESP；");
			lblcisesp_1.setBounds(216, 82, 175, 15);
		}
		return lblcisesp_1;
	}

	private JLabel getLblcisesp_2() {
		if (lblcisesp_2 == null) {

			// 将JBCUS授权代理公司同步至ESP；
			lblcisesp_2 = new JLabel("将JBCUS申报单位同步至ESP；");
			lblcisesp_2.setBounds(216, 107, 260, 15);
		}
		return lblcisesp_2;
	}

	private JLabel getLabel_3() {
		if (label_3 == null) {
			label_3 = new JLabel("(必做)");
			label_3.setBounds(633, 32, 50, 15);
			label_3.setForeground(Color.red);
		}
		return label_3;
	}

	private JLabel getLabel_4() {
		if (label_4 == null) {
			label_4 = new JLabel("(必做)");
			label_4.setForeground(Color.RED);
			label_4.setBounds(433, 57, 43, 15);
		}
		return label_4;
	}

	private JLabel getLabel_5() {
		if (label_5 == null) {
			label_5 = new JLabel("(必做)");
			label_5.setForeground(Color.RED);
			label_5.setBounds(396, 82, 50, 15);
		}
		return label_5;
	}

	private JTabbedPane getTabbedPane() {
		if (tabbedPane == null) {
			tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			tabbedPane.addTab("①同步企业登录账号(必做)", null, getPanel_3(), null);
			tabbedPane.addTab("②查看代理公司(必做)", null, getPanel_1(), null);
//			tabbedPane.addTab("③同步授权代理公司(必做)", null, getPanel_2(), null);
//			tabbedPane.addTab("④同步代理公司的申报单位", null, getPanel_4(), null);
		}
		return tabbedPane;
	}

	private JPanel getPanel_1() {
		if (panel_1 == null) {
			panel_1 = new JPanel();
			panel_1.setToolTipText("");
			panel_1.setLayout(new BorderLayout(0, 0));
//			panel_1.add(getToolBar(), BorderLayout.NORTH);
			panel_1.add(getScrollPane(), BorderLayout.CENTER);
		}
		return panel_1;
	}

	private JPanel getPanel_2() {
		if (panel_2 == null) {
			panel_2 = new JPanel();
			panel_2.setLayout(new BorderLayout(0, 0));
			panel_2.add(getSplitPane_1(), BorderLayout.CENTER);
		}
		return panel_2;
	}

	private JPanel getPanel_3() {
		if (panel_3 == null) {
			panel_3 = new JPanel();
			panel_3.setLayout(new BorderLayout(0, 0));
			panel_3.add(getToolBar_3(), BorderLayout.NORTH);
			panel_3.add(getScrollPane_3(), BorderLayout.CENTER);
		}
		return panel_3;
	}

	private JPanel getPanel_4() {
		if (panel_4 == null) {
			panel_4 = new JPanel();
			panel_4.setLayout(new BorderLayout(0, 0));
			panel_4.add(getToolBar_4(), BorderLayout.NORTH);
			panel_4.add(getScrollPane_4(), BorderLayout.CENTER);
		}
		return panel_4;
	}

	private JToolBar getToolBar() {
		if (toolBar == null) {
			toolBar = new JToolBar();
//			toolBar.add(getBtnExecute());
		}
		return toolBar;
	}

	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTbSyncBrokerCorp());
		}
		return scrollPane;
	}

	private JTable getTbSyncBrokerCorp() {
		if (tbSyncBrokerCorp == null) {
			tbSyncBrokerCorp = new JTable();
		}
		return tbSyncBrokerCorp;
	}

	private JButton getBtnExecute() {
		if (btnExecute == null) {
			btnExecute = new JButton("BCGS同步至JBCUS");
			btnExecute.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					pisVerificationAuthority.checkExecute(request);

					executeGetBrokerCorp();

					findBrokerCorp();

				}
			});
		}
		return btnExecute;
	}

	private JSplitPane getSplitPane_1() {
		if (splitPane_1 == null) {
			splitPane_1 = new JSplitPane();
			splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
			splitPane_1.setLeftComponent(getPanel_5());
			splitPane_1.setRightComponent(getPanel_6());
			splitPane_1.setDividerLocation(200);
		}
		return splitPane_1;
	}

	private JPanel getPanel_5() {
		if (panel_5 == null) {
			panel_5 = new JPanel();
			panel_5.setLayout(new BorderLayout(0, 0));
			panel_5.add(getToolBar_1(), BorderLayout.NORTH);
			panel_5.add(getScrollPane_1(), BorderLayout.CENTER);
		}
		return panel_5;
	}

	private JPanel getPanel_6() {
		if (panel_6 == null) {
			panel_6 = new JPanel();
			panel_6.setLayout(new BorderLayout(0, 0));
			panel_6.add(getToolBar_2(), BorderLayout.NORTH);
			panel_6.add(getScrollPane_2(), BorderLayout.CENTER);
		}
		return panel_6;
	}

	private JToolBar getToolBar_1() {
		if (toolBar_1 == null) {
			toolBar_1 = new JToolBar();
			toolBar_1.add(getBtnSelectBrokerCorp());
			toolBar_1.add(getBtnDelSelect());
			toolBar_1.add(getBtnRefreshThird());
			toolBar_1.add(getBtnExecuteSyncThird());
		}
		return toolBar_1;
	}

	private JScrollPane getScrollPane_1() {
		if (scrollPane_1 == null) {
			scrollPane_1 = new JScrollPane();
			scrollPane_1.setViewportView(getTbAuthorityBrokerCorp());
		}
		return scrollPane_1;
	}

	private JTable getTbAuthorityBrokerCorp() {
		if (tbAuthorityBrokerCorp == null) {
			tbAuthorityBrokerCorp = new JTable();
		}
		return tbAuthorityBrokerCorp;
	}

	private JToolBar getToolBar_2() {
		if (toolBar_2 == null) {
			toolBar_2 = new JToolBar();
			toolBar_2.add(getBtnAddThird());
			toolBar_2.add(getBtnEditThird());
			toolBar_2.add(getBtnDeleteThird());
		}
		return toolBar_2;
	}

	private JScrollPane getScrollPane_2() {
		if (scrollPane_2 == null) {
			scrollPane_2 = new JScrollPane();
			scrollPane_2.setViewportView(getTbAuthorityBrokerCorpItem());
		}
		return scrollPane_2;
	}

	private JTable getTbAuthorityBrokerCorpItem() {
		if (tbAuthorityBrokerCorpItem == null) {
			tbAuthorityBrokerCorpItem = new JTable();
		}
		return tbAuthorityBrokerCorpItem;
	}

	private JButton getBtnSelectBrokerCorp() {
		if (btnSelectBrokerCorp == null) {
			btnSelectBrokerCorp = new JButton("选择代理公司");
			btnSelectBrokerCorp.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					pisVerificationAuthority.checkSelectBrokerCorp(request);
					List<EspAuthorityBrokerCorp> brokerlist = tableModelAuthorityBrokerCorp
							.getList();
					List<BrokerCorp> brokerCorpList = new ArrayList<BrokerCorp>();
					if (brokerlist != null) {
						for (EspAuthorityBrokerCorp espAuthorityBrokerCorp : brokerlist) {
							brokerCorpList.add(espAuthorityBrokerCorp
									.getBrokerCorp());
						}
					}
					DgSelectBrokerCorp dg = new DgSelectBrokerCorp(
							brokerCorpList, Boolean.TRUE);
					dg.setVisible(true);
					if (dg.isOk) {
						List<BrokerCorp> list = dg.getSelectValue();
						pisAction.authorityBrokerCorp(
								new Request(CommonVars.getCurrUser()), list);
						findEspAuthorityBrokerCorp();
					}
				}
			});
		}
		return btnSelectBrokerCorp;
	}

	private JButton getBtnDelSelect() {
		if (btnDelSelect == null) {
			btnDelSelect = new JButton("删除");
			btnDelSelect.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					pisVerificationAuthority.checkDelSelect(request);
					List<EspAuthorityBrokerCorp> companyBrokerCorpItemList = tableModelAuthorityBrokerCorp
							.getCurrentRows();
					if (companyBrokerCorpItemList == null
							|| companyBrokerCorpItemList.isEmpty()) {
						JOptionPane.showMessageDialog(FmPisSync.this,
								"请选择需要删除的代理公司！", "提示信息",
								JOptionPane.ERROR_MESSAGE);
						return;
					}

					// 删除确认
					if (JOptionPane.OK_OPTION == JOptionPane.showOptionDialog(
							FmPisSync.this, "确认删除选中的授权代理公司吗？", "提示",
							JOptionPane.OK_OPTION,
							JOptionPane.INFORMATION_MESSAGE, null,
							new Object[] { "是", "否" }, "否")) {
						pisAction.cancelAuthorityBrokerBrop(new Request(
								CommonVars.getCurrUser()),
								companyBrokerCorpItemList);
						findEspAuthorityBrokerCorp();
					}
				}
			});
		}
		return btnDelSelect;
	}

	private JButton getBtnRefreshThird() {
		if (btnRefreshThird == null) {
			btnRefreshThird = new JButton("刷新");
			btnRefreshThird.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					findEspAuthorityBrokerCorp();
				}
			});
		}
		return btnRefreshThird;
	}

	private JButton getBtnExecuteSyncThird() {

		if (btnExecuteSyncThird == null) {

			btnExecuteSyncThird = new JButton("JBCUS同步至ESP");

			btnExecuteSyncThird.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {

					pisVerificationAuthority.checkExecuteSyncThird(request);

					executeSyncAuthorityToEsp();

					findEspAuthorityBrokerCorp();

				}
			});
		}
		return btnExecuteSyncThird;
	}

	private JButton getBtnAddThird() {
		if (btnAddThird == null) {
			btnAddThird = new JButton("新增");
			btnAddThird.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					pisVerificationAuthority.checkAddThird(request);
					EspAuthorityBrokerCorp cbc = (EspAuthorityBrokerCorp) tableModelAuthorityBrokerCorp
							.getCurrentRow();
					if (cbc == null) {
						JOptionPane
								.showMessageDialog(FmPisSync.this,
										"请选择授权代理公司。", "提示信息",
										JOptionPane.ERROR_MESSAGE);
						return;
					}
					List list = tableModelAuthorityBrokerCorpItem.getList();
					DgEspAuthorityBrokerCorpItem dg = new DgEspAuthorityBrokerCorpItem();
					dg.setItems(list);
					dg.setCbc(cbc);
					dg.setState(DataState.ADD);
					dg.setVisible(true);

					List ls = pisAction.findEspAuthorityBrokerCorpItem(
							new Request(CommonVars.getCurrUser()), cbc);
					initTableAuthorityBrokerCorpItem(ls);
					setItemStatus(DataState.BROWSE);
				}
			});
		}
		return btnAddThird;
	}

	private JButton getBtnEditThird() {
		if (btnEditThird == null) {
			btnEditThird = new JButton("修改");
			btnEditThird.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					pisVerificationAuthority.checkEditThird(request);
					EspAuthorityBrokerCorp cbc = (EspAuthorityBrokerCorp) tableModelAuthorityBrokerCorp
							.getCurrentRow();
					if (cbc == null) {
						JOptionPane.showMessageDialog(FmPisSync.this, "请选择明细。",
								"提示信息", JOptionPane.ERROR_MESSAGE);
						return;
					}
					List list = tableModelAuthorityBrokerCorpItem.getList();
					EspAuthorityBrokerCorpItem item = (EspAuthorityBrokerCorpItem) tableModelAuthorityBrokerCorpItem
							.getCurrentRow();
					DgEspAuthorityBrokerCorpItem dg = new DgEspAuthorityBrokerCorpItem();
					dg.setItems(list);
					dg.setCbc(cbc);
					dg.setEspCorpItem(item);
					dg.setState(DataState.EDIT);
					dg.setVisible(true);

					List ls = pisAction.findEspAuthorityBrokerCorpItem(
							new Request(CommonVars.getCurrUser()), cbc);
					initTableAuthorityBrokerCorpItem(ls);
					setItemStatus(DataState.BROWSE);
				}
			});
		}
		return btnEditThird;
	}

	private JButton getBtnDeleteThird() {
		if (btnDeleteThird == null) {
			btnDeleteThird = new JButton("删除");
			btnDeleteThird.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					pisVerificationAuthority.checkDeleteThird(request);
					List<EspAuthorityBrokerCorpItem> delList = tableModelAuthorityBrokerCorpItem
							.getCurrentRows();
					if (delList.isEmpty()) {
						JOptionPane.showMessageDialog(FmPisSync.this, "请选择数据。",
								"提示信息", JOptionPane.INFORMATION_MESSAGE);
						return;
					}

					// 删除确认
					if (JOptionPane.OK_OPTION == JOptionPane.showOptionDialog(
							FmPisSync.this, "确认删除选中的授权代理公司明细吗？", "提示",
							JOptionPane.OK_OPTION,
							JOptionPane.INFORMATION_MESSAGE, null,
							new Object[] { "是", "否" }, "否")) {
						List<EspAuthorityBrokerCorpItem> delEntityList = new ArrayList<EspAuthorityBrokerCorpItem>();
						for (EspAuthorityBrokerCorpItem cbci : delList) {
							if (cbci.getId() != null
									&& !"".equals(cbci.getId())) {
								delEntityList.add(cbci);
							}
						}

						if (!delEntityList.isEmpty()) {
							pisAction.delEspAuthorityBrokerCorpItem(
									new Request(CommonVars.getCurrUser()),
									delEntityList);
						}

						tableModelAuthorityBrokerCorpItem.deleteRows(delList);

						setItemStatus(DataState.BROWSE);
					}
				}
			});
		}
		return btnDeleteThird;
	}

	private JToolBar getToolBar_3() {
		if (toolBar_3 == null) {
			toolBar_3 = new JToolBar();
			toolBar_3.add(getBtnExecuteSyncUser());
			toolBar_3.add(getBtnRefresh());
		}
		return toolBar_3;
	}

	private JScrollPane getScrollPane_3() {
		if (scrollPane_3 == null) {
			scrollPane_3 = new JScrollPane();
			scrollPane_3.setViewportView(getTbSyncUser());
		}
		return scrollPane_3;
	}

	private JTable getTbSyncUser() {
		if (tbSyncUser == null) {
			tbSyncUser = new JTable();
		}
		return tbSyncUser;
	}

	private JButton getBtnExecuteSyncUser() {
		if (btnExecuteSyncUser == null) {
			btnExecuteSyncUser = new JButton("JBCUS同步至ESP");
			btnExecuteSyncUser.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					pisVerificationAuthority.checkExecuteSyncUser(request);
					if(!executeSyncUser()){
						return;
					}
					findPisSyncAclUser();
					
					//
					pisVerificationAuthority.checkExecute(request);
					executeGetBrokerCorp();
					findBrokerCorp();
				}
			});
		}
		return btnExecuteSyncUser;
	}

	private JButton getBtnRefresh() {
		if (btnRefresh == null) {
			btnRefresh = new JButton("刷新");
			btnRefresh.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					pisAction.refreshPisSyncAclUser(new Request(CommonVars
							.getCurrUser()));
					findPisSyncAclUser();
				}
			});
		}
		return btnRefresh;
	}

	private JToolBar getToolBar_4() {
		if (toolBar_4 == null) {
			toolBar_4 = new JToolBar();
			toolBar_4.add(getBtnExecuteSyncAgentCorp());
		}
		return toolBar_4;
	}

	private JButton getBtnExecuteSyncAgentCorp() {
		if (btnExecuteSyncAgentCorp == null) {
			btnExecuteSyncAgentCorp = new JButton("JBCUS同步至ESP");
			btnExecuteSyncAgentCorp.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					pisVerificationAuthority.checkExecuteSyncAgentCorp(request);
					executeSyncAgentCorp();
					findPisSyncAgentCorp();
				}
			});
		}
		return btnExecuteSyncAgentCorp;
	}

	private JScrollPane getScrollPane_4() {
		if (scrollPane_4 == null) {
			scrollPane_4 = new JScrollPane();
			scrollPane_4.setViewportView(getTbSyncAgentCorp());
		}
		return scrollPane_4;
	}

	private JTable getTbSyncAgentCorp() {
		if (tbSyncAgentCorp == null) {
			tbSyncAgentCorp = new JTable();
		}
		return tbSyncAgentCorp;
	}

	private void executeSyncAuthorityToEsp() {
		List<EspAuthorityBrokerCorp> companyBrokerCorpList = tableModelAuthorityBrokerCorp
				.getCurrentRows();
		if (companyBrokerCorpList == null || companyBrokerCorpList.isEmpty()) {
			JOptionPane.showMessageDialog(FmPisSync.this, "请选择需要同步的代理公司！",
					"提示信息", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		Gson gson = new Gson();

		String companyCode = pisAction.getCompanyCode(new Request(CommonVars
				.getCurrUser()));

		Map<EspAuthorityBrokerCorp, Boolean> infoMap = new HashMap<EspAuthorityBrokerCorp, Boolean>();// 连接服务器是否成功

		Map<EspAuthorityBrokerCorp, Map<String, String>> syncErrMap = new HashMap<EspAuthorityBrokerCorp, Map<String, String>>();
		for (EspAuthorityBrokerCorp corp : companyBrokerCorpList) {
			String urlAddress = "http://"
					+ corp.getBrokerCorp().getPisEspServer().getServerAddress()
					+ ":"
					+ corp.getBrokerCorp().getPisEspServer().getPortNumber()
					+ "/esp-war/CisSyncAclUserServlet";//
			System.out.println("urlAddress = " + urlAddress);
			HttpClientInvoker clientInvoker = new HttpClientInvoker();
			Map<String, String> params = new HashMap();
			params.put("companyCode", companyCode);
			params.put("methodname", "authorityBrokerCorp");
			params.put("brokerCorpCode", corp.getBrokerCorp().getOrgaCode());
			List authryList = this.pisAction.findEspAuthorityBrokerCorpItem(
					new Request(CommonVars.getCurrUser()), corp);
			params.put("authryList", gson.toJson(getAuthryMap(authryList)));

			try {
				String resultData = clientInvoker.executeMethod(urlAddress,
						params, null);
				System.out.println("------------" + urlAddress + " resultData"
						+ resultData);
				if (resultData != null && !"".equals(resultData.trim())) {
					Map<String, String> resultMap = jsonToMap(resultData);
					String resultcode = resultMap.get("resultcode");
					String msg = resultMap.get("msg");
					String strUserErrMap = resultMap.get("errMap");
					if (StringUtils.isNotEmpty(strUserErrMap)) {
						Map<String, String> tuserErrMap = jsonToMap(strUserErrMap);
						syncErrMap.put(corp, tuserErrMap);
					}
					if (resultcode != null) {
						if (resultcode.equals("-1")) {
							// errorInfoMap.put(server, "连接esp服务器" +
							// server.getServerAddress() + ":" +
							// server.getPortNumber() + msg);
						} else {

							JOptionPane.showMessageDialog(FmPisSync.this, msg,
									"执行同步成功！", JOptionPane.INFORMATION_MESSAGE);

						}
					}
				}

				infoMap.put(corp, true);

			} catch (Exception ex) {
				ex.printStackTrace();
				infoMap.put(corp, false);
			}
		}
		this.pisAction.updateEspAuthorityBrokerCorp(
				new Request(CommonVars.getCurrUser()), companyBrokerCorpList,
				infoMap, syncErrMap);

	}

	public List<Map<String, String>> getAuthryMap(List authryList) {
		List<Map<String, String>> mapAuthryList = new ArrayList<Map<String, String>>();
		for (int i = 0; i < authryList.size(); i++) {
			EspAuthorityBrokerCorpItem authry = (EspAuthorityBrokerCorpItem) authryList
					.get(i);
			Map<String, String> mapAuthry = new HashMap<String, String>();
			mapAuthry.put("mainBusiness", authry.getMainBusiness());
			mapAuthry.put("icCard", authry.getIcCard());
			mapAuthry.put("optionStatus", authry.getOptionStatus());
			mapAuthryList.add(mapAuthry);
		}
		return mapAuthryList;
	}

	/**
	 * 检查用户邮箱
	 *
	 * @param pUserlist
	 * @return
	 */
	public boolean checkAclUserEmail(List<PisSyncAclUser> pUserlist) {

		for (PisSyncAclUser pUser : pUserlist) {
			if (StringUtils.isEmpty(pUser.getEmail())) {

				JOptionPane.showMessageDialog(FmPisSync.this,
						"用户" + pUser.getUserName() + "的邮箱为空！");
				return false;
			}
		}
		return true;
	}

	/**
	 * 执行同步用户
	 */
	private boolean executeSyncUser() {

		// 被选择的同步用户esp
		List<PisSyncAclUser> pUserlist = getPisSyncAcUsersIsSelected();

		// 被选择的用户登录名称
		List loginNames = cache;

		// 需要同步的帐号信息
		List<AclUser> userList = pisAction.findSyncAclUserByLoginName(
				new Request(CommonVars.getCurrUser()), loginNames,
				AclUser.class);

		if (userList.isEmpty()) {

			JOptionPane.showMessageDialog(FmPisSync.this, "请勾选需要同步的帐号");

			return false;
		}

		if (!checkAclUserEmail(pUserlist)) {

			return false;
		}

		Gson gson = new Gson();

		String companyCode = pisAction.getCompanyCode(new Request(CommonVars
				.getCurrUser()));

		List<PisEspServer> espServerList = pisAction
				.findEspAuthorityBrokerCorpPisEspServer(new Request(CommonVars
						.getCurrUser()));

		Map<PisEspServer, Boolean> infoMap = new HashMap<PisEspServer, Boolean>();// 连接服务器是否成功

		Map<PisEspServer, Map<String, String>> userErrMap = new HashMap<PisEspServer, Map<String, String>>();

		for (PisEspServer server : espServerList) {

			String urlAddress = "http://" + server.getServerAddress() + ":"
					+ server.getPortNumber() + "/esp-war/CisSyncAclUserServlet";//

			System.out.println("urlAddress = " + urlAddress
					+ "  -----------------");

			HttpClientInvoker clientInvoker = new HttpClientInvoker();

			// 传去服务器的参数
			Map<String, String> params = new HashMap();

			params.put("companyCode", companyCode);

			params.put("methodname", "addAclUser");

			String json = gson.toJson(getAclUserTemps(userList));

			params.put("aclUsers", json);

			try {

				String resultData = clientInvoker.executeMethod(urlAddress,
						params, null);

				if (resultData != null && !"".equals(resultData.trim())) {

					Map<String, String> resultMap = jsonToMap(resultData);

					String resultcode = resultMap.get("resultcode");

					String msg = resultMap.get("msg");

					String strUserErrMap = resultMap.get("errMap");

					if (StringUtils.isNotEmpty(strUserErrMap)) {

						Map<String, String> tuserErrMap = jsonToMap(strUserErrMap);

						userErrMap.put(server, tuserErrMap);

					}
					if (resultcode != null) {
						if (resultcode.equals("-1")) {
							// errorInfoMap.put(server, "连接esp服务器" +
							// server.getServerAddress() + ":" +
							// server.getPortNumber() + msg);
						} else {

						}
					}
				}

				infoMap.put(server, true);

			} catch (Exception ex) {

				ex.printStackTrace();

				infoMap.put(server, false);

			}
		}

		pisAction.updatePisSyncAclUser(new Request(CommonVars.getCurrUser()),
				pUserlist, infoMap, userErrMap);
		return true;
	}

	private List getAclUserTemps(List<AclUser> userList) {
		List<AclUserTemp> returnList = new ArrayList<AclUserTemp>();
		for (int i = 0; i < userList.size(); i++) {
			try {
				AclUserTemp aclUserTemp = new AclUserTemp();
				PropertyUtils.copyProperties(aclUserTemp, userList.get(i));
				returnList.add(aclUserTemp);
				if (aclUserTemp.getPassword().indexOf("{") == 0) {
					aclUserTemp.setPassword("{}" + aclUserTemp.getPassword());
				}
				Company company = (Company) userList.get(i).getCompany();
				aclUserTemp.setCompanyCode(company.getCode());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return returnList;
	}

	private void executeSyncAgentCorp() {
		Gson gson = new Gson();

		String companyCode = pisAction.getCompanyCode(new Request(CommonVars
				.getCurrUser()));
		List<PisEspServer> espServerList = this.pisAction
				.findEspAuthorityBrokerCorpPisEspServer(new Request(CommonVars
						.getCurrUser()));

		Map<PisEspServer, Boolean> infoMap = new HashMap<PisEspServer, Boolean>();// 连接服务器是否成功

		List<String> returnDataList = new ArrayList<String>();
		for (PisEspServer server : espServerList) {
			String urlAddress = "http://" + server.getServerAddress() + ":"
					+ server.getPortNumber() + "/esp-war/CisSyncAclUserServlet";//
			System.out.println("urlAddress = " + urlAddress);
			HttpClientInvoker clientInvoker = new HttpClientInvoker();
			Map<String, String> params = new HashMap();
			params.put("companyCode", companyCode);
			params.put("methodname", "getAgentCorp");

			try {
				String resultData = clientInvoker.executeMethod(urlAddress,
						params, null);
				System.out.println("------------" + urlAddress + " resultData"
						+ resultData);
				if (resultData != null && !"".equals(resultData.trim())) {
					Map<String, String> resultMap = jsonToMap(resultData);
					String resultcode = resultMap.get("resultcode");
					String msg = resultMap.get("msg");
					String returnData = resultMap.get("returnData");
					if (resultcode != null) {
						if (resultcode.equals("-1")) {
							// errorInfoMap.put(server, "连接esp服务器" +
							// server.getServerAddress() + ":" +
							// server.getPortNumber() + msg);
						} else {

							if (StringUtils.isNotEmpty(returnData)) {

								returnDataList.add(returnData);

							}
						}
					}
				}
				infoMap.put(server, true);
			} catch (Exception ex) {
				ex.printStackTrace();
				infoMap.put(server, false);
			}
		}

		pisAction.updatePisSyncAgentCorp(new Request(CommonVars.getCurrUser()),
				returnDataList);

	}

	/**
	 * 执行获取代理公司
	 */
	private void executeGetBrokerCorp() {

		// 获取 当前公司其他参数的设置
		CompanyOther coMsgParam = pisAction.findCompanyOther(new Request(
				CommonVars.getCurrUser()));

		// 获取 加工单位 的 海关编码
		String companyCode = pisAction.getCompanyCode(new Request(CommonVars
				.getCurrUser()));

		System.out.println(companyCode);

		if (coMsgParam == null
				|| StringUtils.isEmpty(coMsgParam.getPisAddress())
				|| coMsgParam.getPisPort() == null) {

			JOptionPane.showMessageDialog(FmPisSync.this,
					"百思维服务器配置为空，请到“系统参数设置”中的“其他参数设置”模块中设置“百思维运维服务器配置”参数",
					"提示！", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		// 平台地址
		String addr = coMsgParam.getPisAddress();

		// 平台端口
		String port = coMsgParam.getPisPort().toString();

		// 平台 url
		String urlAddress = "http://" + addr + ":" + port
				+ "/bcgs-war/GetEspBrokerCorpServlet";//

		System.out.println("urlAddress = " + urlAddress);

		HttpClientInvoker clientInvoker = new HttpClientInvoker();

		Map<String, String> params = new HashMap();

		params.put("companyCode", companyCode);

		// HttpProxyParam proxyParam=new
		// HttpProxyParam("113.105.139.6",8087,"","");

		String resultData = clientInvoker.executeMethod(urlAddress, params,
				null);

		System.out.println("------------" + resultData);

		if (resultData != null && !"".equals(resultData.trim())) {

			Map<String, String> resultMap = jsonToMap(resultData);

			String resultcode = resultMap.get("resultcode");

			String msg = resultMap.get("msg");

			String sMap = resultMap.get("serverMap");

			String aList = resultMap.get("agtCompanyList");

			if (resultcode != null) {

				if (resultcode.equals("-1")) {

					JOptionPane.showMessageDialog(FmPisSync.this, msg, "提示！",
							JOptionPane.INFORMATION_MESSAGE);

				} else {

					Map<String, Map> serverMap = jsonToMap2(sMap);

					List<Map> agtCompanyList = jsonToMap3(aList);

					this.pisAction.syncBrokerCorp(
							new Request(CommonVars.getCurrUser()), serverMap,
							agtCompanyList);

					JOptionPane.showMessageDialog(FmPisSync.this, msg, "执行成功！",
							JOptionPane.INFORMATION_MESSAGE);

				}
			}
		}
	}

	private Map<String, String> jsonToMap(String jsonData) {
		Gson gson = new Gson();
		Map<String, String> map = gson.fromJson(jsonData,
				new TypeToken<Map<String, String>>() {
				}.getType());
		return map;
	}

	private Map<String, Map> jsonToMap2(String jsonData) {
		Gson gson = new Gson();
		Map<String, Map> map = gson.fromJson(jsonData,
				new TypeToken<Map<String, Map>>() {
				}.getType());
		return map;
	}

	private List<Map> jsonToMap3(String jsonData) {
		Gson gson = new Gson();
		List<Map> list = gson.fromJson(jsonData, new TypeToken<List<Map>>() {
		}.getType());
		return list;
	}

	/**
	 * 编辑列 （用于表格JCheckBox的编辑）
	 */
	class CheckBoxEditor extends DefaultCellEditor implements ActionListener {
		private JCheckBox cb;
		private JTable table = null;

		/**
		 * 构造CheckBoxEditor内部类
		 * 
		 * @param checkBox
		 */
		public CheckBoxEditor(JCheckBox checkBox) {
			super(checkBox);
		}

		/**
		 * 编辑方法
		 */
		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {

			if (value == null) {

				return null;

			}
			if (Boolean.valueOf(value.toString()) instanceof Boolean) {
				cb = new JCheckBox();
				cb.setSelected(Boolean.valueOf(value.toString()).booleanValue());
			}
			cb.setHorizontalAlignment(JLabel.CENTER);
			cb.addActionListener(this);
			this.table = table;
			return cb;
		}

		/**
		 * 获取编辑值
		 */
		public Object getCellEditorValue() {
			cb.removeActionListener(this);
			return cb;
		}

		/**
		 * 触发方法
		 */
		public void actionPerformed(ActionEvent e) {
			JTableListModel tableModel = (JTableListModel) this.table
					.getModel();
			Object obj = tableModel.getCurrentRow();
			if (obj instanceof BaseCustomsDeclaration) {
				PisSyncAclUser temp = (PisSyncAclUser) obj;
				temp.setIsSelected(new Boolean(cb.isSelected()));
				tableModel.updateRow(obj);
			}
			fireEditingStopped();
		}
	}

	/**
	 * 重写默认的表格渲染器
	 * 
	 * @author Administrator
	 *
	 */
	class SubDefaultTableCellRenderer extends DefaultTableCellRenderer {

		private JCheckBox checkBox = null;

		public SubDefaultTableCellRenderer(JCheckBox _checkBox) {

			checkBox = _checkBox;

		}

		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean focus, int row,
				int col) {

			// 空值直接空组件
			if (value == null) {

				return null;

			}

			String valueString = (String) value;

			setInheritStyleFromTable(table, isSelected);

			if (valueString.equalsIgnoreCase("true")
					|| valueString.equalsIgnoreCase("false")) {

				Boolean booleanValue = Boolean.valueOf(valueString);

				checkBox.setSelected(booleanValue);

				// 如果不是布尔值 直接返回默认组件显示
			} else {

				return super.getTableCellRendererComponent(table, value,
						isSelected, focus, row, row);

			}

			return checkBox;

		}

		private void setInheritStyleFromTable(JTable table, boolean isSelected) {

			// 居中
			checkBox.setHorizontalAlignment(JLabel.CENTER);

			if (isSelected) {

				checkBox.setForeground(table.getSelectionForeground());

				checkBox.setBackground(table.getSelectionBackground());

			} else {

				checkBox.setForeground(table.getForeground());

				checkBox.setBackground(table.getBackground());

			}

		}
	}

	class SubDefaultCellEditor extends DefaultCellEditor implements
			ActionListener {

		private JCheckBox checkBox;

		// 用于提取当前行数据的 变量 table
		private JTable table;

		public SubDefaultCellEditor(JCheckBox paramJCheckBox) {

			super(paramJCheckBox);

		}

		@Override
		public Component getTableCellEditorComponent(JTable _table,
				Object value, boolean isSelected, int row, int col) {

			checkBox = new JCheckBox();

			table = _table;

			checkBox.setHorizontalAlignment(JLabel.CENTER);

			checkBox.addActionListener(this);

			return checkBox;
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			JTableListModel tableListModel = (JTableListModel) table.getModel();

			Object obj = tableListModel.getCurrentRow();

			if (obj instanceof PisSyncAclUser) {

				PisSyncAclUser espUser = (PisSyncAclUser) obj;

				espUser.setIsSelected(!espUser.getIsSelected());

			}

			fireEditingStopped();

		}
	}

	/**
	 * 获取被选择同步的帐号
	 * 
	 * @return
	 */
	private List getPisSyncAcUsersIsSelected() {

		JTableListModel tableListModel = (JTableListModel) tbSyncUser
				.getModel();

		// 所有的同步帐号
		List pisSyncAcusers = tableListModel.getList();

		// 被选择的 同步帐号 登录名字
		List pisSyncAcUsersIsSelected = new ArrayList();

		for (Object pisSyncAcUserObj : pisSyncAcusers) {

			PisSyncAclUser aclUser = (PisSyncAclUser) pisSyncAcUserObj;

			boolean isSelected = aclUser.getIsSelected();

			// 被选择的就加到 被选择 的 同步帐号
			if (isSelected) {

				pisSyncAcUsersIsSelected.add(aclUser.getLoginName());

			}

		}

		cache = pisSyncAcUsersIsSelected;

		return pisAction.findSyncAclUserByLoginName(
				new Request(CommonVars.getCurrUser()),
				pisSyncAcUsersIsSelected, PisSyncAclUser.class);

	}

	/**
	 * 同步信息 错误信息则显示成红色
	 * 
	 * @author Administrator
	 *
	 */
	class SyncStateChangeColor extends DefaultTableCellRenderer {

		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean focus, int row,
				int col) {

			String v = (String) value;

			super.getTableCellRendererComponent(table, value, isSelected,
					focus, row, col);

			if (StringUtils.isBlank(v)) {

				setForeground(Color.RED);

				// v = "同步信息问题 没有同步信息";

				this.setHorizontalAlignment(JLabel.CENTER);

				// 必须重新 设置 同步信息
				setText(v);

				return this;

			} else {

				if (v.contains("失败") || v.contains("存在")) {

					setForeground(Color.RED);

					// 必须重新 设置 同步信息
					setText(v);

					return this;

				} else if (v.contains("成功")) {

					setText(v);

					setForeground(Color.BLACK);

					return this;

				}

			}

			// 消除其他的渲染颜色效果
			setForeground(Color.BLACK);

			return this;

		}
	}

}
