package com.bestway.pis.dec;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;

import org.apache.commons.lang.StringUtils;

import com.bestway.bcs.client.contractexe.DgBcsImportCustomsDeclaration;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.client.enc.DgImportCustomsDeclaration;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.client.common.tableeditor.CheckBoxHeader;
import com.bestway.client.custom.common.DgBaseImportCustomsDeclaration;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.authority.entity.AclUser;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.ProjectType;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.customs.common.entity.BaseEmsHead;
import com.bestway.pis.action.PisAction;
import com.bestway.pis.action.PisVerificationAuthority;
import com.bestway.pis.common.DgChooseBrokerCorp;
import com.bestway.pis.common.DgConsoleInfo;
import com.bestway.pis.common.HttpClientInvoker;
import com.bestway.pis.common.NetUtil;
import com.bestway.pis.constant.EspMainBusinessType;
import com.bestway.pis.entity.BrokerCorp;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @author kpc
 */
public class FmPisImpDecHead extends JInternalFrameBase {
	private JToolBar toolBar;
	private JScrollPane scrollPane;
	private JTable table;
	private JButton btnUpload;
	private JButton btnDownload;
	private JButton btnClose;
	private JRadioButton rbCheck;
	private JRadioButton rbSend;
	private JLabel lblNewLabel;
	private JTextField tfEmsNo;
	private JButton btnQuery;

	private PisAction pisAction = null;
	private PisVerificationAuthority pisVerificationAuthority = null;
	private Request request = null;
	private JTableListModel tableModel = null;
	private JComboBox cbbProjectType;
	private JButton btnCancel;
	private JLabel label;
	private JCalendarComboBox cbbBeginDate;
	private JLabel label_1;
	private JCalendarComboBox cbbEndDate;
	private JComboBox emsNoCombox;

	public FmPisImpDecHead() {

		pisAction = (PisAction) CommonVars.getApplicationContext().getBean(
				"pisAction");

		request = new Request(CommonVars.getCurrUser());

		pisVerificationAuthority = (PisVerificationAuthority) CommonVars
				.getApplicationContext().getBean("PisVerificationAuthority");

		pisVerificationAuthority.checkPisImpDec(request);

		getContentPane().setLayout(new BorderLayout(0, 0));

		getContentPane().add(getScrollPane(), BorderLayout.CENTER);

		getContentPane().add(getToolBar(), BorderLayout.NORTH);

		queryData();

		init();

		setUIStatus();

	}

	private void init() {
		Integer projectType = pisAction.findProjectType();
		if (ProjectType.BCS == projectType) {
			cbbProjectType.setSelectedItem(new ItemProperty(String
					.valueOf(ProjectType.BCS), ProjectType
					.getNote(ProjectType.BCS)));
		} else {
			cbbProjectType.setSelectedItem(new ItemProperty(String
					.valueOf(ProjectType.BCUS), ProjectType
					.getNote(ProjectType.BCUS)));
		}

		initSelectProjectTypeCombox(projectType);

	}

	private void setUIStatus() {
		this.btnUpload.setEnabled(rbCheck.isSelected());
		this.btnDownload.setEnabled(rbSend.isSelected());
	}

	private void queryData() {
		initTable(this.getDataSource());
	}

	private void initTable(List list) {
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(new JTableListColumn("", "isSelected", 50));
				list.add(new JTableListColumn("流水号", "serialNumber", 50));
				// list.add(new JTableListColumn("状态", "decStatus", 80));
				list.add(new JTableListColumn("代理报关公司", "brokerCorp.orgaName",
						120));
				list.add(new JTableListColumn("进口日期", "impExpDate", 80));
				list.add(new JTableListColumn("件数", "commodityNum", 50));
				list.add(new JTableListColumn("包装类型", "wrapType.name", 80));
				list.add(new JTableListColumn("申报日期", "declarationDate", 100));
				list.add(new JTableListColumn("报关单号", "customsDeclarationCode",
						150));
				list.add(new JTableListColumn("备案号", "emsHeadH2k", 100));//
				list.add(new JTableListColumn("合同协议号", "contract", 100));
				list.add(new JTableListColumn("毛重", "grossWeight", 100));
				list.add(new JTableListColumn("净重", "netWeight", 100));
				list.add(new JTableListColumn("回执日期", "tcsResultTime", 100));
				list.add(new JTableListColumn("回执信息", "tcsResultMessage", 100));
				list.add(new JTableListColumn("统一编号", "unificationCode", 100));
				list.add(new JTableListColumn("申报单位",
						"declarationCompany.buName", 100));
				list.add(new JTableListColumn("进口口岸", "customs.name", 100));
				// list.add(new JTableListColumn("装卸口岸", "preDock.name", 100));
				list.add(new JTableListColumn("贸易方式", "tradeMode.name", 100));
				list.add(new JTableListColumn("运抵国",
						"countryOfLoadingOrUnloading.name", 100));
				list.add(new JTableListColumn("运输方式", "transferMode.name", 100));
				list.add(new JTableListColumn("发货单位代码", "machCode", 100));
				list.add(new JTableListColumn("发货单位名称", "machName", 100));
				list.add(new JTableListColumn("装货港",
						"portOfLoadingOrUnloading.name", 100));
				list.add(new JTableListColumn("境内货源地",
						"domesticDestinationOrSource.name", 100));
				list.add(new JTableListColumn("运输工具名称", "conveyance", 100));
				list.add(new JTableListColumn("提运单号", "billOfLading", 100));
				list.add(new JTableListColumn("集装箱号", "containerNum", 100));
				list.add(new JTableListColumn("成交方式", "transac.name", 100));
				// list.add(new JTableListColumn("报关类型", "decType", 100));
				list.add(new JTableListColumn("经营单位代码", "tradeCode", 100));
				list.add(new JTableListColumn("经营单位名称", "tradeName", 100));
				list.add(new JTableListColumn("任务ID", "espTaskId", 300));
				return list;
			}
		};
		jTableListModelAdapter.setEditableColumn(1);
		tableModel = new JTableListModel(table, list, jTableListModelAdapter);
		table.getColumnModel().getColumn(1)
				.setCellRenderer(new MultiRenderer());
		table.getColumnModel().getColumn(1)
				.setCellEditor(new CheckBoxEditor(new JCheckBox()));
		table.getColumnModel().getColumn(1)
				.setHeaderRenderer(new CheckBoxHeader());
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
				BaseCustomsDeclaration temp = (BaseCustomsDeclaration) obj;
				temp.setIsSelected(new Boolean(cb.isSelected()));
				tableModel.updateRow(obj);
			}
			fireEditingStopped();
		}
	}

	/**
	 * render table JcheckBox 列 （用于将boolean 值转为JcheckBox）
	 */
	class MultiRenderer extends DefaultTableCellRenderer {
		JCheckBox checkBox = new JCheckBox();

		/**
		 * 重写getTableCellRendererComponent方法
		 */
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			if (value == null) {
				value = false;
			}
			if (Boolean.valueOf(value.toString()) instanceof Boolean) {
				checkBox.setSelected(Boolean.parseBoolean(value.toString()));
				checkBox.setHorizontalAlignment(JLabel.CENTER);
				checkBox.setBackground(table.getBackground());
				if (isSelected) {
					checkBox.setForeground(table.getSelectionForeground());
					checkBox.setBackground(table.getSelectionBackground());
				} else {
					checkBox.setForeground(table.getForeground());
					checkBox.setBackground(table.getBackground());
				}
				return checkBox;
			}
			String str = (value == null) ? "" : value.toString();
			return super.getTableCellRendererComponent(table, str, isSelected,
					hasFocus, row, column);
		}
	}

	/**
	 * 查询 手册号
	 * 
	 * @return
	 */
	protected List getDataSource() {

		Boolean isCheck = rbCheck.isSelected();

		Boolean isSend = rbSend.isSelected();

		String emsNo = (String) emsNoCombox.getSelectedItem();

		if (cbbProjectType.getSelectedItem() == null) {

			JOptionPane.showMessageDialog(this, "请选择手册类型！");

			return new ArrayList();

		}

		Integer projectType = Integer.parseInt(((ItemProperty) cbbProjectType
				.getSelectedItem()).getCode());

		return pisAction.findDecHead(new Request(CommonVars.getCurrUser()),
				ImpExpFlag.IMPORT, emsNo, cbbBeginDate.getDate(),
				cbbEndDate.getDate(), isCheck, isSend, projectType);
	}

	private List getSelectRows() {
		List<BaseCustomsDeclaration> selectRows = new ArrayList<BaseCustomsDeclaration>();
		List<BaseCustomsDeclaration> list = tableModel.getList();
		for (int i = 0; i < list.size(); i++) {
			BaseCustomsDeclaration head = list.get(i);
			if (head.getIsSelected() != null && head.getIsSelected() == true) {
				selectRows.add(head);
			}
		}
		return selectRows;
	}

	private JToolBar getToolBar() {
		if (toolBar == null) {
			toolBar = new JToolBar();
			FlowLayout f = new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(0);
			f.setHgap(0);
			toolBar.setLayout(f);
			toolBar.add(getBtnUpload());
			toolBar.add(getBtnDownload());
			toolBar.add(getBtnCancel());
			toolBar.add(getBtnClose());
			toolBar.add(getCbbProjectType());
			toolBar.add(getRbCheck());
			toolBar.add(getRbSend());
			toolBar.add(getLabel());
			toolBar.add(getCbbBeginDate());
			toolBar.add(getLabel_1());
			toolBar.add(getCbbEndDate());
			toolBar.add(getLblNewLabel());

			// toolBar.add(getTfEmsNo());

			toolBar.add(getComboBox());

			toolBar.add(getBtnQuery());

		}
		return toolBar;
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
						Integer projectType = Integer
								.parseInt(((ItemProperty) cbbProjectType
										.getSelectedItem()).getCode());
						DgBaseImportCustomsDeclaration dg = null;
						if (ProjectType.BCS == projectType) {
							dg = new DgBcsImportCustomsDeclaration() {
								public void setVisible(boolean b) {
									if (b) {
										getBtnEdit().setVisible(false);
										getBtnEffect().setVisible(false);
										getBtnCheckData().setVisible(false);
										getBtnSplit().setVisible(false);
										getBtnUnreel().setVisible(false);
									}
									super.setVisible(b);
								};
							};
						} else if (ProjectType.BCUS == projectType) {
							dg = new DgImportCustomsDeclaration() {
								public void setVisible(boolean b) {
									if (b) {
										getBtnEdit().setVisible(false);
										getBtnEffect().setVisible(false);
										getBtnCheckData().setVisible(false);
										getBtnSplit().setVisible(false);
										getBtnUnreel().setVisible(false);
									}
									super.setVisible(b);
								};
							};
						}
						BaseCustomsDeclaration customs = (BaseCustomsDeclaration) tableModel
								.getCurrentRow();
						BaseEmsHead emsHead = pisAction.findBaseEmsHeadByEmsNo(
								new Request(CommonVars.getCurrUser()),
								customs.getEmsHeadH2k(), projectType);
						dg.setCustomsDeclarationModel(tableModel);
						dg.setDataState(DataState.BROWSE);
						dg.setEmsHead(emsHead);
						dg.setVisible(true);
					}
				}
			});
		}
		return table;
	}

	private JButton getBtnUpload() {
		if (btnUpload == null) {
			btnUpload = new JButton("发送报关行");
			btnUpload.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					pisVerificationAuthority.checkPisImpDecUpload(request);

					List list = getSelectRows();
					if (list.isEmpty()) {
						JOptionPane.showMessageDialog(rootPane, "请选择进口报关单。");
						return;
					}

					if (!checkData(list)) {
						return;
					}

					DgChooseBrokerCorp dgBrokerCorp = new DgChooseBrokerCorp();

					dgBrokerCorp
							.setEspMainBusinessType(EspMainBusinessType.BUSINESS_TYPE_IMP);

					dgBrokerCorp.setIsMulti(false);

					dgBrokerCorp.setVisible(true);

					if (dgBrokerCorp.isOk) {
						BrokerCorp brokerCorp = (BrokerCorp) dgBrokerCorp
								.getReturnValue();

						if (brokerCorp == null) {
							return;
						}

						if (!testConnnect(brokerCorp)) {
							JOptionPane.showMessageDialog(rootPane, "连接"
									+ brokerCorp.getPisEspServer()
											.getServerAddress()
									+ ":"
									+ brokerCorp.getPisEspServer()
											.getPortNumber()
									+ "测试失败，网络不通，不能进行报关单发送！");
							return;
						}

						Map<String, Object> processParams = new HashMap();
						// 发送
						DgSendDecHeadEdi dgSendDecHeadEdi = new DgSendDecHeadEdi();
						dgSendDecHeadEdi.setVisible(true);

						if (dgSendDecHeadEdi.isOk) {

							processParams = dgSendDecHeadEdi.getReturnValue();
							DgConsoleInfo dgConsoleInfo = new DgConsoleInfo();
							dgConsoleInfo.setModal(false);
							dgConsoleInfo.setVisible(true);
							for (int i = 0; i < list.size(); i++) {
								BaseCustomsDeclaration decHead = (BaseCustomsDeclaration) list
										.get(i);
								String resultInfo = uploadToEsp(decHead,
										brokerCorp, processParams);
								dgConsoleInfo.addInfo(resultInfo);
							}

							queryData();

						}
					}
				}
			});
		}
		return btnUpload;
	}

	public boolean checkData(List<BaseCustomsDeclaration> list) {
		for (BaseCustomsDeclaration base : list) {
			String serialNumber = "";
			if (base.getTransferMode() == null) {
				serialNumber += base.getSerialNumber() + ",";
			}
			if (!StringUtils.isEmpty(serialNumber)) {
				JOptionPane.showMessageDialog(this, "报关单流水为号：" + serialNumber
						+ "的报关单,运输方式为空！");
				return false;
			}
		}
		return true;
	}

	private JButton getBtnDownload() {
		if (btnDownload == null) {
			btnDownload = new JButton("更新报关单数据");
			btnDownload.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					pisVerificationAuthority.checkPisImpDecDownload(request);
					List list = getSelectRows();
					if (list.isEmpty()) {
						JOptionPane.showMessageDialog(rootPane, "请选择进口报关单。");
						return;
					}
					DgConsoleInfo dgConsoleInfo = new DgConsoleInfo();
					dgConsoleInfo.setModal(false);
					dgConsoleInfo.setVisible(true);
					for (int i = 0; i < list.size(); i++) {
						BaseCustomsDeclaration decHead = (BaseCustomsDeclaration) list
								.get(i);
						String resultInfo = downloadDecRecvData(decHead);
						dgConsoleInfo.addInfo(resultInfo);
					}
					queryData();
				}
			});
		}
		return btnDownload;
	}

	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton("关闭");
			btnClose.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
		}
		return btnClose;
	}

	private JRadioButton getRbCheck() {
		if (rbCheck == null) {
			rbCheck = new JRadioButton("已检查");
			rbCheck.setSelected(true);
			rbCheck.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					rbSend.setSelected(false);
					rbCheck.setSelected(true);
					btnCancel.setEnabled(false);
					setUIStatus();
					queryData();
				}
			});
		}
		return rbCheck;
	}

	private JRadioButton getRbSend() {
		if (rbSend == null) {
			rbSend = new JRadioButton("已发送");
			rbSend.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					rbCheck.setSelected(false);
					rbSend.setSelected(true);
					btnCancel.setEnabled(true);
					queryData();
					setUIStatus();
				}
			});
		}
		return rbSend;
	}

	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("  手册号");
		}
		return lblNewLabel;
	}

	private JTextField getTfEmsNo() {
		if (tfEmsNo == null) {
			tfEmsNo = new JTextField();
			tfEmsNo.setColumns(10);
			tfEmsNo.setPreferredSize(new Dimension(6, 27));
		}
		return tfEmsNo;
	}

	private JButton getBtnQuery() {
		if (btnQuery == null) {
			btnQuery = new JButton("查询");
			btnQuery.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					queryData();
				}
			});
		}
		return btnQuery;
	}

	/**
	 * 上传报关单
	 *
	 * @param decHead
	 * @param brokerCorp
	 * @param processParams
	 * @return
	 */
	private String uploadToEsp(BaseCustomsDeclaration decHead,
			BrokerCorp brokerCorp, Map<String, Object> processParams) {
		if (decHead.getEspTaskId() == null
				|| "".equals(decHead.getEspTaskId().trim())) {
			String espTaskId = "";
			try {
				espTaskId = this.getNewEspTaskId(decHead, brokerCorp);
			} catch (Exception ex) {
				return ex.getMessage();
			}
			decHead.setEspTaskId(espTaskId);
			decHead = pisAction.saveOrUpdate(
					new Request(CommonVars.getCurrUser()), decHead);
			tableModel.updateRow(decHead);
		}
		String resultInfo = "";
		Integer projectType = Integer.parseInt(((ItemProperty) cbbProjectType
				.getSelectedItem()).getCode());
		String jsonData = pisAction.makeDecJSONData(
				new Request(CommonVars.getCurrUser()), decHead, projectType);
		System.out.println("jsonData==" + jsonData);
		// try {
		// FileUtils.writeStringToFile(new File("D://decData.json"), jsonData,
		// "GBK");
		// } catch (IOException ex) {
		// Logger.getLogger(FmPisImpDecHead.class.getName()).log(Level.SEVERE,
		// null, ex);
		// }
		Gson gson = new Gson();
		// String urlAddress =
		// "http://127.0.0.1:8080/esp-war/UploadDecServLet";//
		String urlAddress = "http://"
				+ brokerCorp.getPisEspServer().getServerAddress() + ":"
				+ brokerCorp.getPisEspServer().getPortNumber()
				+ "/esp-war/UploadDecServLet";//
		HttpClientInvoker clientInvoker = new HttpClientInvoker();
		AclUser aclUser = pisAction.findAclUserById(
				new Request(CommonVars.getCurrUser()), CommonVars.getCurrUser()
						.getId());
		Map<String, String> params = new HashMap();
		params.put("methodname", "uploadDecData");
		params.put("decdata", jsonData);
		params.put("brokercorporgacode", brokerCorp.getOrgaCode());
		params.put("companycode",
				pisAction.getCompanyCode(new Request(CommonVars.getCurrUser())));
		params.put("processparams", gson.toJson(processParams));
		params.put("useremail", aclUser.getEmail());
		// HttpProxyParam proxyParam=new
		// HttpProxyParam("113.105.139.6",8087,"","");
		String resultData = clientInvoker.executeMethod(urlAddress, params,
				null);
		if (resultData != null && !"".equals(resultData.trim())) {
			Map<String, String> resultMap = jsonToMap(resultData);
			String resultcode = resultMap.get("resultcode");
			String msg = resultMap.get("msg");
			if ("1".equals(resultcode)) {
				// decHead.setDecStatus(DecStatus.WAIT_APPR);
				decHead.setIsSend(true);
				decHead.setBrokerCorp(brokerCorp);
				pisAction.saveOrUpdate(new Request(CommonVars.getCurrUser()),
						decHead);
				resultInfo = "报关单流水号：" + decHead.getSerialNumber() + "，手册号："
						+ decHead.getEmsHeadH2k() + ",已成功向报关代理公司"
						+ brokerCorp.getOrgaName() + "发送。";
			} else {
				resultInfo = "报关单流水号：" + decHead.getSerialNumber() + "，手册号："
						+ decHead.getEmsHeadH2k() + "发送失败，失败原因如下：\n" + msg;
			}
		} else {
			resultInfo = "报关单流水号：" + decHead.getSerialNumber() + "，手册号："
					+ decHead.getEmsHeadH2k() + "发送失败，失败原因如下：产生的报文为空！";
		}
		return resultInfo;
	}

	private String getNewEspTaskId(BaseCustomsDeclaration decHead,
			BrokerCorp brokerCorp) {
		String companyCode = pisAction.getCompanyCode(new Request(CommonVars
				.getCurrUser()));
		HttpClientInvoker clientInvoker = new HttpClientInvoker();
		String urlAddress = "http://"
				+ brokerCorp.getPisEspServer().getServerAddress() + ":"
				+ brokerCorp.getPisEspServer().getPortNumber()
				+ "/esp-war/UploadDecServLet";//
		Map<String, String> params = new HashMap();
		params.put("methodname", "getNewDecEspTaskId");
		params.put("brokercorporgacode", brokerCorp.getOrgaCode());
		params.put("companycode", companyCode);
		params.put("datafrom", "3");
		// params.put("impexpflag", decHead.getImpExpFlag());

		if (decHead.getImpExpFlag().intValue() == 0) {
			params.put("impexpflag", "I");
		} else if (decHead.getImpExpFlag().intValue() == 1) {
			params.put("impexpflag", "E");
		} else if (decHead.getImpExpFlag().intValue() == 2) {
			switch (decHead.getImpExpType()) {
			case ImpExpType.GENERAL_TRADE_IMPORT:
			case ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES:
			case ImpExpType.EQUIPMENT_IMPORT:
			case ImpExpType.BACK_PORT_REPAIR:
			case ImpExpType.IMPORT_STORAGE:
			case ImpExpType.MATERIAL_DOMESTIC_SALES:
			case ImpExpType.MATERIAL_EXCHANGE:
			case ImpExpType.REMAIN_FORWARD_IMPORT:
				params.put("impexpflag", "I");
				break;
			case ImpExpType.REMIAN_MATERIAL_BACK_PORT:
			case ImpExpType.GENERAL_TRADE_EXPORT:
			case ImpExpType.EQUIPMENT_BACK_PORT:
			case ImpExpType.REMAIN_FORWARD_EXPORT:
			case ImpExpType.EXPORT_STORAGE:
			case ImpExpType.MATERIAL_REOUT:
				params.put("impexpflag", "E");
				break;
			default:
				break;
			}
		}

		String resultData = clientInvoker.executeMethod(urlAddress, params,
				null);
		if (resultData != null && !"".equals(resultData.trim())) {
			Map<String, String> resultMap = jsonToMap(resultData);
			String resultcode = resultMap.get("resultcode");
			String espTaskId = resultMap.get("esptaskid");
			String msg = resultMap.get("msg");
			if ("1".equals(resultcode)) {
				return espTaskId;
			} else {
				throw new RuntimeException("报关单流水号："
						+ decHead.getSerialNumber() + "，手册号："
						+ decHead.getEmsHeadH2k() + "生成任务号失败，失败原因如下：" + msg);
			}
		} else {
			throw new RuntimeException("报关单流水号：" + decHead.getSerialNumber()
					+ "，手册号：" + decHead.getEmsHeadH2k()
					+ "生成任务号失败，失败原因如下：产生的回执为空！");
		}
	}

	// /**
	// * 下载报关单回执信息
	// *
	// * @param decHead
	// * @return
	// */
	// private String downloadDecRecvData(BaseCustomsDeclaration decHead) {
	// String resultInfo = "";
	// String espTaskId = decHead.getEspTaskId();
	// if (espTaskId == null || "".equals(espTaskId.trim())) {
	// resultInfo = "进口报关单" + decHead.getSerialNumber() + "的espTaskId为空。";
	// return resultInfo;
	// }
	// if (decHead.getBrokerCorp() == null) {
	// resultInfo = "进口报关单" + decHead.getSerialNumber() + "的代理报关公司栏位为空。";
	// return resultInfo;
	// }
	// if (!testConnnect(decHead.getBrokerCorp())) {
	// resultInfo = "连接"
	// + decHead.getBrokerCorp().getPisEspServer()
	// .getServerAddress() + ":"
	// + decHead.getBrokerCorp().getPisEspServer().getPortNumber()
	// + "测试失败，网络不通！" + "进口报关单" + decHead.getSerialNumber()
	// + "更新回执失败！";
	// return resultInfo;
	// }
	// // String urlAddress =
	// // "http://127.0.0.1:8080/esp-war/UploadDecServLet";//
	// String urlAddress = "http://"
	// + decHead.getBrokerCorp().getPisEspServer().getServerAddress()
	// + ":"
	// + decHead.getBrokerCorp().getPisEspServer().getPortNumber()
	// + "/esp-war/UploadDecServLet";//
	// HttpClientInvoker clientInvoker = new HttpClientInvoker();
	// Map<String, String> params = new HashMap();
	// params.put("methodname", "downloadDecRecvData");
	// params.put("esptaskid", espTaskId);
	// // HttpProxyParam proxyParam=new
	// // HttpProxyParam("113.105.139.6",8087,"","");
	// String resultData = clientInvoker.executeMethod(urlAddress, params,
	// null);
	// if (resultData != null && !"".equals(resultData.trim())) {
	// Map resultMap = jsonToMap(resultData);
	// String resultcode = (String) resultMap.get("resultcode");
	// String msg = (String) resultMap.get("msg");
	// if ("1".equals(resultcode)) {
	// String decData = (String) resultMap.get("decdata");
	// Integer projectType = Integer
	// .parseInt(((ItemProperty) cbbProjectType
	// .getSelectedItem()).getCode());
	// decHead = pisAction.downloadDecData(request, decHead, decData,
	// projectType);
	// resultInfo = "读取进口报关单" + decHead.getSerialNumber()
	// + "回执成功，统一编号为：" + decHead.getUnificationCode()
	// + " 报关单号为：" + decHead.getCustomsDeclarationCode();
	// } else {
	// resultInfo = "读取进口报关单" + decHead.getSerialNumber()
	// + "回执失败，失败原因如下：" + msg;
	// }
	// } else {
	// resultInfo = "读取进口报关单" + decHead.getSerialNumber()
	// + "回执失败，没有读到此报关单的信息。";
	// }
	// return resultInfo;
	// }
	/**
	 * 下载报关单回执信息
	 *
	 * @param decHead
	 * @return
	 */
	private String downloadDecRecvData(BaseCustomsDeclaration decHead) {
		String espTaskId = decHead.getEspTaskId();
		if (espTaskId == null || "".equals(espTaskId.trim())) {
			return "进口报关单" + decHead.getSerialNumber() + "的espTaskId为空。";
		}
		if (decHead.getBrokerCorp() == null) {
			return "进口报关单" + decHead.getSerialNumber() + "的代理报关公司栏位为空。";
		}
		if (!testConnnect(decHead.getBrokerCorp())) {
			return "连接"
					+ decHead.getBrokerCorp().getPisEspServer()
							.getServerAddress() + ":"
					+ decHead.getBrokerCorp().getPisEspServer().getPortNumber()
					+ "测试失败，网络不通！" + "出口报关单" + decHead.getSerialNumber()
					+ "更新回执失败！";
		}
		String urlAddress = "http://"
				+ decHead.getBrokerCorp().getPisEspServer().getServerAddress()
				+ ":"
				+ decHead.getBrokerCorp().getPisEspServer().getPortNumber()
				+ "/esp-war/UploadDecServLet";//
		HttpClientInvoker clientInvoker = new HttpClientInvoker();
		Map<String, String> params = new HashMap();
		params.put("methodname", "findDecHeadEdiStatus");
		params.put("esptaskid", espTaskId);
		String resultData = clientInvoker.executeMethod(urlAddress, params,
				null);
		if (resultData != null && !"".equals(resultData.trim())) {
			Map resultMap = jsonToMap(resultData);
			String resultcode = (String) resultMap.get("resultcode");
			String msg = (String) resultMap.get("msg");
			if ("1".equals(resultcode)) {
				String decStatus = (String) resultMap.get("decstatus");
				if (decStatus != null && !"".equals(decStatus)) {
					// 5:资料检查不通过；8:海关申报退单。
					if ("5".equals(decStatus) || "8".equals(decStatus)) {
						String noPassInfo = "5".equals(decStatus) ? "资料检查不通过"
								: "海关申报退单";
						decHead.setEffective(false);
						decHead.setIsSend(false);
						decHead.setIsCheck(false);
						pisAction.saveOrUpdate(request, decHead);
						return "进口报关单" + decHead.getSerialNumber() + noPassInfo;
					}
				}
			} else {
				return "读取进口报关单" + decHead.getSerialNumber() + "状态失败，失败原因如下："
						+ msg;
			}
		} else {
			return "读取进口报关单" + decHead.getSerialNumber() + "状态失败，没有读到此报关单的信息。";
		}
		params = new HashMap();
		params.put("methodname", "downloadDecRecvData");
		params.put("esptaskid", espTaskId);
		// HttpProxyParam proxyParam=new
		// HttpProxyParam("113.105.139.6",8087,"","");
		resultData = clientInvoker.executeMethod(urlAddress, params, null);
		if (resultData != null && !"".equals(resultData.trim())) {
			Map resultMap = jsonToMap(resultData);
			String resultcode = (String) resultMap.get("resultcode");
			String msg = (String) resultMap.get("msg");
			if ("1".equals(resultcode)) {

				String decData = (String) resultMap.get("decdata");
				Integer projectType = Integer
						.parseInt(((ItemProperty) cbbProjectType
								.getSelectedItem()).getCode());

				Map decMap = jsonToMap(decData);
				Map<String, Object> mapHead = (Map<String, Object>) decMap
						.get("报关单表头");
				if (mapHead == null) {
					throw new RuntimeException("报关单表头为空！");
				}
				String isCustomsDelete = (String) mapHead.get("是否海关删单");
				if ("1".equals(isCustomsDelete)) {
					String customsDeleteReason = (String) mapHead.get("删单原因");
					pisAction.customsDeleteDecl(request, decData, projectType);
					EncAction baseEncAction = (EncAction) CommonVars
							.getApplicationContext().getBean("encAction");
					baseEncAction.deleteCustomsDeclaration(request, decHead,
							true);
					return "海关删单，删单原因:" + customsDeleteReason;
				} else {
					decHead = pisAction.downloadDecData(request, decHead,
							decData, projectType);
					return "读取进口报关单" + decHead.getSerialNumber()
							+ "数据成功，统一编号为：" + decHead.getUnificationCode()
							+ " 报关单号为：" + decHead.getCustomsDeclarationCode();
				}
			} else {
				return "读取进口报关单" + decHead.getSerialNumber() + "数据失败，失败原因如下："
						+ msg;
			}
		} else {
			return "读取进口报关单" + decHead.getSerialNumber() + "数据失败，没有读到此报关单的信息。";
		}
	}

	private Map jsonToMap(String jsonData) {
		Gson gson = new Gson();
		Map<String, String> map = gson.fromJson(jsonData, new TypeToken<Map>() {
		}.getType());
		return map;
	}

	private boolean testConnnect(BrokerCorp brokerCorp) {
		String serverName = brokerCorp.getPisEspServer().getServerAddress();
		String serverPort = brokerCorp.getPisEspServer().getPortNumber();
		return NetUtil.testConnnect(serverName, Integer.valueOf(serverPort));
	}

	private JComboBox getCbbProjectType() {

		if (cbbProjectType == null) {

			cbbProjectType = new JComboBox();

			cbbProjectType.setPreferredSize(new Dimension(100, 22));

			cbbProjectType.addItem(new ItemProperty(String
					.valueOf(ProjectType.BCS), ProjectType
					.getNote(ProjectType.BCS)));

			cbbProjectType.addItem(new ItemProperty(String
					.valueOf(ProjectType.BCUS), ProjectType
					.getNote(ProjectType.BCUS)));

			// cbbProjectType.addItem(new
			// ItemProperty(String.valueOf(ProjectType.DZSC),ProjectType.getNote(ProjectType.DZSC)));

			cbbProjectType.setSelectedIndex(0);

			cbbProjectType.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent event) {

					initSelectProjectTypeCombox(Integer
							.parseInt(((ItemProperty) cbbProjectType
									.getSelectedItem()).getCode()));

				}
			});

		}
		return cbbProjectType;
	}

	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton("撤销");
			btnCancel.setEnabled(false);
			btnCancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					pisVerificationAuthority.checkPisImpDecCancel(request);

					List list = getSelectRows();

					if (list.isEmpty()) {
						JOptionPane.showMessageDialog(rootPane, "请勾选进口报关单。");
						return;
					}

					// 是否真的撤销
					int isTrueForCanel = JOptionPane.showConfirmDialog(
							FmPisImpDecHead.this, "是否需要撤销当前选择的报关单?", "撤销操作",
							JOptionPane.YES_NO_OPTION);

					if (isTrueForCanel != 0) {

						return;

					}

					DgConsoleInfo dgConsoleInfo = new DgConsoleInfo();

					dgConsoleInfo.setModal(false);

					dgConsoleInfo.setVisible(true);

					for (int i = 0; i < list.size(); i++) {

						BaseCustomsDeclaration decHead = (BaseCustomsDeclaration) list
								.get(i);

						String resultInfo = cancelBillDecHeadEdi(decHead);

						dgConsoleInfo.addInfo(resultInfo);

					}

					// 查询数据 刷新
					queryData();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * 用户撤单操作
	 *
	 * @param decHead
	 * @return
	 */
	private String cancelBillDecHeadEdi(BaseCustomsDeclaration decHead) {

		String resultInfo = "";

		String espTaskId = decHead.getEspTaskId();

		if (espTaskId == null || "".equals(espTaskId.trim())) {

			resultInfo = "进口报关单" + decHead.getSerialNumber() + "的espTaskId为空。";

			return resultInfo;

		}
		if (decHead.getBrokerCorp() == null) {

			resultInfo = "进口报关单" + decHead.getSerialNumber() + "的代理报关公司栏位为空。";

			return resultInfo;

		}

		if (!testConnnect(decHead.getBrokerCorp())) {

			resultInfo = "连接"
					+ decHead.getBrokerCorp().getPisEspServer()
							.getServerAddress() + ":"
					+ decHead.getBrokerCorp().getPisEspServer().getPortNumber()
					+ "测试失败，网络不通！" + "出口报关单" + decHead.getSerialNumber()
					+ "更新回执失败！";

			return resultInfo;
		}

		/*
		 * 开始 撤销 操作
		 */

		String urlAddress = "http://"
				+ decHead.getBrokerCorp().getPisEspServer().getServerAddress()
				+ ":"
				+ decHead.getBrokerCorp().getPisEspServer().getPortNumber()
				+ "/esp-war/UploadDecServLet";//

		AclUser aclUser = CommonVars.getCurrUser();

		HttpClientInvoker clientInvoker = new HttpClientInvoker();

		Map<String, String> params = new HashMap();

		params.put("methodname", "cancelBillDecHeadEdi");

		params.put("esptaskid", espTaskId);

		String companyCode = pisAction.getCompanyCode(new Request(CommonVars
				.getCurrUser()));

		params.put("companycode", companyCode);

		params.put("useremail", aclUser.getEmail());

		String resultData = clientInvoker.executeMethod(urlAddress, params,
				null);

		if (resultData != null && !"".equals(resultData.trim())) {

			Map resultMap = jsonToMap(resultData);

			String resultcode = (String) resultMap.get("resultcode");

			String msg = (String) resultMap.get("msg");

			if ("1".equals(resultcode)) {

				decHead.setIsSend(false);

				decHead.setEffective(false);

				decHead.setEspTaskId(null);

				decHead.setBrokerCorp(null);

				decHead.setDeclaraCustomsBroker(null);

				pisAction.saveOrUpdate(new Request(CommonVars.getCurrUser()),
						decHead);

				resultInfo = "撤销进口报关单" + decHead.getSerialNumber() + "成功";

			} else {

				return resultInfo = "撤销进口报关单" + decHead.getSerialNumber()
						+ "失败，失败原因如下：" + msg;

			}

		} else {

			resultInfo = "撤销进口报关单" + decHead.getSerialNumber()
					+ "失败，没有读到此报关单的信息。";

		}

		return resultInfo;
	}

	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel("  申报日期：");
		}
		return label;
	}

	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setDate(CommonVars.getBeginDate());
			cbbBeginDate.setPreferredSize(new Dimension(90, 20));

			cbbBeginDate.addChangeListener(new ChangeListener() {

				// 状态改变
				@Override
				public void stateChanged(ChangeEvent e) {

					activeDateMethod();

				}
			});

		}
		return cbbBeginDate;
	}

	private JLabel getLabel_1() {
		if (label_1 == null) {
			label_1 = new JLabel("至");
		}
		return label_1;
	}

	private JCalendarComboBox getCbbEndDate() {
		if (cbbEndDate == null) {
			cbbEndDate = new JCalendarComboBox();
			cbbEndDate.setPreferredSize(new Dimension(90, 20));

			cbbEndDate.addChangeListener(new ChangeListener() {

				@Override
				public void stateChanged(ChangeEvent e) {

					activeDateMethod();

				}
			});

		}
		return cbbEndDate;
	}

	private JComboBox getComboBox() {

		if (emsNoCombox == null) {

			emsNoCombox = new JComboBox();

			emsNoCombox.setSelectedItem(null);

			emsNoCombox.setPreferredSize(new Dimension(120, 24));

			if (cbbProjectType.getSelectedItem() == null) {

				JOptionPane.showMessageDialog(this, "请选择手册类型！");

				return emsNoCombox;

			}

		}

		return emsNoCombox;
	}

	/**
	 * 初始化 查询手册
	 * 
	 * @param projectType
	 * @param endDate
	 * @param beginDate
	 */
	private void initSelectProjectTypeCombox(final int projectType) {

		emsNoCombox.removeAllItems();

		/**
		 * 电子账册 电子化手册 查找这两个号码
		 */
		List list = pisAction.findBaseEmsHead(
				new Request(CommonVars.getCurrUser()), projectType);

		if (list != null) {

			for (int i = 0; i < list.size(); i++) {

				emsNoCombox.addItem(((BaseEmsHead) list.get(i)).getEmsNo());

			}

		}

		emsNoCombox.setMaximumRowCount(10);

	}

	/**
	 * 当日期更改的时候 动态变动 手册号查询
	 */
	private void activeDateMethod() {

		Date beginDate = cbbBeginDate.getDate();

		Date endDate = cbbEndDate.getDate();

		if (beginDate == null) {

			beginDate = new Date(0L);

		}

		if (null == endDate) {

			endDate = new Date();

		}

		boolean isPassDate = beginDate.after(endDate);

		// 判断日期 提示信息
		if (isPassDate) {

			JOptionPane.showMessageDialog(FmPisImpDecHead.this, "起始日期不能大于结束日期",
					"提示信息", JOptionPane.ERROR_MESSAGE);
			return;

		}

	}

}
