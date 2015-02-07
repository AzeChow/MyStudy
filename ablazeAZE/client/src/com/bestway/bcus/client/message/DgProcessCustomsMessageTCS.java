/*
 * Created on 2004-9-7
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */

package com.bestway.bcus.client.message;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JPopupMenu.Separator;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcus.client.common.CommonFileFilter;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxUI;
import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.custombase.action.CustomBaseAction;
import com.bestway.bcus.enc.entity.TempCustomsMessage;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.message.action.MessageAction;
import com.bestway.bcus.system.action.TcsParametersAction;
import com.bestway.client.custom.common.DgCommonErrorMessage;
import com.bestway.client.custom.common.EncCommon;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.CommonUtils;
import com.bestway.common.HttpClientUtil;
import com.bestway.common.MessageHttpUtil;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.ProjectType;
import com.bestway.customs.common.action.BaseEncAction;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;
import com.bestway.dzsc.dzscmanage.action.DzscAction;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * 
 */
@SuppressWarnings({ "unchecked", "deprecation", "serial" })
public class DgProcessCustomsMessageTCS extends JDialogBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private javax.swing.JPanel jContentPane = null;

	private JTabbedPane jTabbedPane = null;

	private JPanel jPanel = null;

	private JButton btnProcess = null;

	private JButton btnViewEdiMessage = null;

	private JButton btnExit = null;

	private JPanel jPanel1 = null;

	private JPanel jPanel2 = null;

	private JRadioButton rbImport = null;

	private JRadioButton rbExport = null;

	private JRadioButton rbSpec = null;

	private JRadioButton rbReceipt = null;

	private JTable tbCustomBill = null;

	private JScrollPane jScrollPane = null;

	private MessageAction messageAction = null; // @jve:decl-index=0:

	private JTableListModel tableModel = null;

	private JTextArea taExecInfo = null;

	private JPanel jPanel3 = null;

	private ButtonGroup buttonGroup = new ButtonGroup(); // @jve:decl-index=0:

	private JPopupMenu jPopupMenu = null;

	private JMenuItem jMenuItem = null;

	private BaseEncAction baseEncAction = null;// 由相应的报关单付值 // // // // //
	private JTableListModel tableModelCustoms = null;

	private JPopupMenu pmEdiFunction = null;

	private JMenuItem btnOut = null; // 外部读取EDI报文

	private JMenuItem btnInner = null; // 内部直接读取EDI报文

	private File ediFile = null; // EDI报文
	public int projectType;

	private ContractAction contractAction = null;

	private CustomBaseAction customBaseAction = null;

	private DzscAction dzscAction = null; // @jve:decl-index=0:

	protected ManualDeclareAction manualDeclareAction = null; // @jve:decl-index=0:

	private TcsParametersAction tcsParametersAction = null;

	/**
	 * This is the default constructor
	 */
	public DgProcessCustomsMessageTCS() {
		super();
		tcsParametersAction = (TcsParametersAction) CommonVars.getApplicationContext().getBean("tcsParametersAction");
		messageAction = (MessageAction) CommonVars.getApplicationContext().getBean("messageAction");
		contractAction = (ContractAction) CommonVars.getApplicationContext().getBean("contractAction");

		dzscAction = (DzscAction) CommonVars.getApplicationContext().getBean("dzscAction");

		manualDeclareAction = (ManualDeclareAction) CommonVars.getApplicationContext().getBean("manualdeclearAction");
		customBaseAction = (CustomBaseAction) CommonVars.getApplicationContext().getBean("customBaseAction");

		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setResizable(false);
		this.setSize(802, 520);
		this.setContentPane(getJContentPane());
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {

				initTable(new Vector());
				// rbImport.setSelected(true);
			}
		});
		buttonGroup.add(rbImport);
		buttonGroup.add(rbExport);
		buttonGroup.add(rbSpec);
		buttonGroup.add(rbReceipt);
		buttonGroup.add(rbAdvoice);
		buttonGroup.add(rbSupplement);
	}

	private void initTable(List list) {
		if (list == null) {
			list = new Vector();
		}
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("选择", "isSelected", 100));
				list.add(addColumn("帐册/手册编号", "customsDeclaration.emsHeadH2k", 100));
				list.add(addColumn("流水号", "customsDeclaration.serialNumber", 60, Integer.class));
				list.add(addColumn("报关单号", "customsDeclaration.customsDeclarationCode", 150));

				list.add(addColumn("申报日期", "customsDeclaration.declarationDate", 100));
				list.add(addColumn("是否检查", "customsDeclaration.isCheck", 100));
				list.add(addColumn("是否生效", "customsDeclaration.effective", 80));
				list.add(addColumn("是否作废", "customsDeclaration.cancel", 80));
				list.add(addColumn("转关确认", "customsDeclaration.confirmTransferCIQ", 80));
				list.add(addColumn("是否申报", "customsDeclaration.isSend", 80));

				list.add(addColumn("合同协议号", "customsDeclaration.contract", 100));
				list.add(addColumn("备注", "customsDeclaration.memos", 100));
				list.add(addColumn("进口类型", "customsDeclaration.impExpType", 100));
				list.add(addColumn("件数", "customsDeclaration.commodityNum", 100));
				list.add(addColumn("毛重", "customsDeclaration.grossWeight", 100));
				list.add(addColumn("净重", "customsDeclaration.netWeight", 100));
				list.add(addColumn("进出口岸", "customsDeclaration.customs.name", 100));
				list.add(addColumn("起运国", "customsDeclaration.countryOfLoadingOrUnloading.name", 100));
				list.add(addColumn("运输方式", "customsDeclaration.transferMode.name", 100));
				list.add(addColumn("运输工具", "customsDeclaration.conveyance", 100));
				list.add(addColumn("收货单位", "customsDeclaration.acceptGoodsCompany", 100));
				list.add(addColumn("贸易方式", "customsDeclaration.tradeMode.name", 100));
				list.add(addColumn("装货港", "customsDeclaration.portOfLoadingOrUnloading.name", 100));
				list.add(addColumn("境内目的地", "customsDeclaration.domesticDestinationOrSource.name", 100));
				list.add(addColumn("集装箱号", "customsDeclaration.containerNum", 100));
				list.add(addColumn("用途", "customsDeclaration.uses.name", 100));
				list.add(addColumn("经营单位", "customsDeclaration.emsHeadH2k.tradeName", 100));

				return list;
			}
		};
		jTableListModelAdapter.setEditableColumn(1);
		tableModel = new JTableListModel(tbCustomBill, list, jTableListModelAdapter);
		tbCustomBill.getColumnModel().getColumn(1).setCellRenderer(new MultiRenderer());
		tbCustomBill.getColumnModel().getColumn(1).setCellEditor(new CheckBoxEditor(new JCheckBox()));

		tbCustomBill.getColumnModel().getColumn(6).setCellRenderer(new checkBoxRenderer());
		tbCustomBill.getColumnModel().getColumn(7).setCellRenderer(new checkBoxRenderer());
		tbCustomBill.getColumnModel().getColumn(8).setCellRenderer(new checkBoxRenderer());
		tbCustomBill.getColumnModel().getColumn(9).setCellRenderer(new checkBoxRenderer());
		tbCustomBill.getColumnModel().getColumn(10).setCellRenderer(new checkBoxRenderer());
		tbCustomBill.getColumnModel().getColumn(13).setCellRenderer(new DefaultTableCellRenderer() {
			/**
					 * 
					 */
			private static final long serialVersionUID = 1L;

			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				String str = "";
				if (value != null && !value.equals("")) {
					switch (Integer.parseInt(value.toString())) {
					case ImpExpType.DIRECT_EXPORT:
						str = "成品出口";
						break;
					case ImpExpType.TRANSFER_FACTORY_EXPORT:
						str = "转厂出口";
						break;
					case ImpExpType.BACK_MATERIEL_EXPORT:
						str = "退料出口";
						break;
					case ImpExpType.REWORK_EXPORT:
						str = "返工复出";
						break;
					case ImpExpType.REMIAN_MATERIAL_BACK_PORT:
						str = "边角料退港";
						break;
					case ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES:
						str = "边角料内销";
						break;
					case ImpExpType.GENERAL_TRADE_EXPORT:
						str = "一般贸易出口";
						break;
					}
				}
				this.setText(str);
				return this;
			}
		});
	}

	// 集成通补充申报表模型
	private void initTableSuppment() {
		List dataList = new Vector();
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("选择", "isSelected", 100));
				list.add(addColumn("帐册/手册编号", "emsHeadH2k", 100));
				list.add(addColumn("流水号", "serialNumber", 100));
				list.add(addColumn("报关单号", "customsDeclarationCode", 150));
				list.add(addColumn("申报日期", "declarationDate", 100));
				list.add(addColumn("是否检查", "isCheck", 100));
				list.add(addColumn("进口类型", "impExpType", 80));
				list.add(addColumn("补充申报ID", "decSupplementListId", 0));
				list.add(addColumn("补充申报单表体ID", "baseCustomsDeclarationCommInfo", 0));
				return list;
			}
		};
		jTableListModelAdapter.setEditableColumn(1);
		tableModel = new JTableListModel(tbCustomBill, dataList, jTableListModelAdapter);
		tbCustomBill.getColumnModel().getColumn(1).setCellRenderer(new MultiRenderer());
		tbCustomBill.getColumnModel().getColumn(1).setCellEditor(new CheckBoxEditor(new JCheckBox()));
		tbCustomBill.getColumnModel().getColumn(6).setCellRenderer(new checkBoxRenderer());
		tbCustomBill.getColumnModel().getColumn(7).setCellRenderer(new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;

			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				String str = "";
				if (value != null) {
					str = ImpExpType.getImpExpTypeDesc(Integer.parseInt(value.toString()));
				}
				this.setText(str);
				return this;
			}
		});
		CommonUtils.hideColumn(tbCustomBill, 8);
		CommonUtils.hideColumn(tbCustomBill, 9);
		tbCustomBill.setModel(tableModel);
	}

	/**
	 * 编辑列
	 */
	class CheckBoxEditor extends DefaultCellEditor implements ActionListener {
		private JCheckBox cb;
		private JTable table = null;

		public CheckBoxEditor(JCheckBox checkBox) {
			super(checkBox);
		}

		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
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

		public Object getCellEditorValue() {
			cb.removeActionListener(this);
			return cb;
		}

		public void actionPerformed(ActionEvent e) {
			JTableListModel tableModel = (JTableListModel) this.table.getModel();
			Object obj = tableModel.getCurrentRow();
			if (obj instanceof TempCustomsMessage) {
				TempCustomsMessage temp = (TempCustomsMessage) obj;
				temp.setIsSelected(new Boolean(cb.isSelected()));
				tableModel.updateRow(obj);
			} else if (obj instanceof Object[]) {
				Object[] temp = (Object[]) obj;
				temp[0] = cb.isSelected();
				tableModel.updateRow(obj);
			}
			fireEditingStopped();
		}
	}

	/**
	 * render table JchcckBox 列
	 */
	class MultiRenderer extends DefaultTableCellRenderer {
		JCheckBox checkBox = new JCheckBox();

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			if (value == null) {
				return this;
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
			return super.getTableCellRendererComponent(table, str, isSelected, hasFocus, row, column);
		}
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new java.awt.BorderLayout());
			jContentPane.add(getJTabbedPane(), java.awt.BorderLayout.CENTER);
			jContentPane.add(getJPanel(), java.awt.BorderLayout.SOUTH);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setToolTipText("");
			jTabbedPane.addTab("报关单发送与回执接收", null, getJPanel1(), null);
		}
		return jTabbedPane;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("手册号+合同号:");
			jPanel = new JPanel();
			jPanel.setLayout(new FlowLayout());
			jPanel.add(getBtnNetwork(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(getCbbEmshead(), null);
			jPanel.add(getBtnProcess(), null);
			jPanel.add(getBtnViewEdiMessage(), null);
			jPanel.add(getJButton(), null);
			jPanel.add(getJButton1(), null);
			jPanel.add(getBtnExit(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes btnProcess
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnProcess() {
		if (btnProcess == null) {
			btnProcess = new JButton();
			btnProcess.setText("执行");
			btnProcess.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (DgProcessCustomsMessageTCS.this.rbReceipt.getModel().isSelected() == false && rbAdvoice.getModel().isSelected() == false) {
						List list = getParentList();
						if (list == null || list.size() < 1) {
							JOptionPane.showMessageDialog(DgProcessCustomsMessageTCS.this, "未选择任何行，请选择要执行的行!", "提示！", 1);
							return;
						}
						List listError = messageAction.checkTcsParameter(new Request(CommonVars.getCurrUser()));
						if (listError != null && listError.size() > 0) {
							DgCommonErrorMessage dg = new DgCommonErrorMessage();
							dg.setList(listError);
							dg.setVisible(true);
							return;
						}
						String[] ids = new String[list.size()];
						String[] supplementListIds = new String[list.size()];
						if (list.get(0) instanceof TempCustomsMessage) {
							// 检测
							for (int j = 0; j < list.size(); j++) {
								TempCustomsMessage tempcustoms = (TempCustomsMessage) list.get(j);
								ids[j] = tempcustoms.getCustomsDeclaration().getId();
//								if (tempcustoms.getCustomsDeclaration().getDeclarationDate() == null) {
//									if (JOptionPane.YES_OPTION != JOptionPane.showConfirmDialog(DgProcessCustomsMessageTCS.this, "报关单"
//											+ tempcustoms.getCustomsDeclaration().getCustomsDeclarationCode() + "申报时间为空,是否继续!")) {
//										return;
//									}
//								} else {
//									int year = tempcustoms.getCustomsDeclaration().getDeclarationDate().getYear();
//									int mon = tempcustoms.getCustomsDeclaration().getDeclarationDate().getMonth();
//									int day = tempcustoms.getCustomsDeclaration().getDeclarationDate().getDay();
//									Date date = new Date();
//									if (date.getYear() != year || date.getMonth() != mon || date.getDay() != day) {
//										if (JOptionPane.YES_OPTION != JOptionPane.showConfirmDialog(DgProcessCustomsMessageTCS.this, "报关单"
//												+ tempcustoms.getCustomsDeclaration().getCustomsDeclarationCode() + "申报时间不是当天,是否继续!")) {
//											return;
//										}
//									}
//								}
							}
							new CustomsMessage(ids, supplementListIds).start();
						} else if (list.get(0) instanceof Object) {
							Iterator it = list.iterator();
							int j = 0;
							while (it.hasNext()) {
								Object[] obj = (Object[]) it.next();
								supplementListIds[j] = obj[7].toString();
								String baseCustomsDeclarationCommInfoId = obj[8].toString();
								// 通过报关单表体Id获得报关单表头信息
								BaseCustomsDeclarationCommInfo baseCustomsDeclarationCommInfo = messageAction.findBaseCustomsDeclarationCommInfoById(
										new Request(CommonVars.getCurrUser()), projectType, baseCustomsDeclarationCommInfoId);
								ids[j] = baseCustomsDeclarationCommInfo.getBaseCustomsDeclaration().getId();
								j++;
							}
							new CustomsMessage(ids, supplementListIds).start();
						}
					} else if (DgProcessCustomsMessageTCS.this.rbReceipt.getModel().isSelected() && rbAdvoice.getModel().isSelected() == false) {// 表示是处理回执
						new ReceiptProcess().start();
					} else if (rbAdvoice.getModel().isSelected()) { // 执行下载通知书
//						new ReceiptAdvoice().start();
					}
				}
			});
		}
		return btnProcess;
	}

	class CustomsMessage extends Thread {
		String[] ids = null;
		String[] supplementListIds = null;

		public CustomsMessage(String[] ids, String[] supplementListIds) {
			this.ids = ids;
			this.supplementListIds = supplementListIds;
		}

		public void run() {
			try {
				CommonProgress.showProgressDialog(DgProcessCustomsMessageTCS.this);
				CommonProgress.setMessage("系统正在发送报文，请稍后...");

				
				/*
				 * 生成报文
				 */
				taExecInfo.append("<==============开始发送==============>\n");
				String id = null;
				String suppId = null;
				String msg = null;
				int count = 0;// 成功笔数
				StringBuilder error = new StringBuilder();
				for (int i = 0; i < ids.length; i++) {
					id = ids[i];
					suppId = supplementListIds[i];
					try {
						msg = messageAction.sendMessages(
								new Request(CommonVars.getCurrUser()), id,
								projectType, suppId);
						taExecInfo.append(msg);
						count++;
					} catch (Exception e) {
						e.printStackTrace();
						error.append(e.getMessage() + "\n");
					}
				}
				taExecInfo.append("总共发送报关单：" + ids.length + "笔。\n");
				taExecInfo.append("成功数：" + count + "笔。\n");
				taExecInfo.append("失败数：" + (ids.length - count) + "笔。\n");
				taExecInfo.append("失败日志：\n");
				taExecInfo.append(error.toString());
				taExecInfo.append("<==============结束发送==============>\n");
				SwingUtilities.invokeLater(new Runnable(){
					@Override
					public void run() {
						radioActionListnerExecute();
					}
					
				});
				
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				taExecInfo.append("发送报文失败：！\n");
				JOptionPane.showMessageDialog(DgProcessCustomsMessageTCS.this, "发送报文失败：！" + e.getMessage(), "提示", 2);
			}
		}
	}

//	/**
//	 * 下载通知书
//	 * 
//	 * @author ower
//	 * 
//	 */
//	class ReceiptAdvoice extends Thread {
//		public void run() {
//			try {
//				CommonProgress.showProgressDialog(DgProcessCustomsMessageTCS.this);
//				CommonProgress.setMessage("系统正在下载回执，请稍后...");
//				List files = new Vector();
//				String filesName = messageAction.checkFtpAdviceInfo(new Request(CommonVars.getCurrUser()), projectType);
//				if (filesName != null && !"".equals(filesName)) {
//					taExecInfo.append("开始下载通知单...\n");
//				} else {
//					taExecInfo.append("下载了0个通知单。\n");
//					CommonProgress.closeProgressDialog();
//					return;
//				}
//				String storefile = getFileName(filesName);
//				if (storefile == null) {
//					JOptionPane.showMessageDialog(DgProcessCustomsMessageTCS.this, "请你下载此文档!", "提示！", 1);
//					CommonProgress.closeProgressDialog();
//					return;
//				}
//				files = messageAction.ftpDownloadAdviceInfo(new Request(CommonVars.getCurrUser()), projectType, storefile);
//				for (int i = 0; i < files.size(); i++) {
//					taExecInfo.append("已下载通知单：" + storefile + "。\n");
//				}
//				for (int i = 0; i < files.size(); i++) {
//					taExecInfo.append("正在处理回执：" + ((File) files.get(i)).getName() + "\n");
//					messageAction.processReturnAdvoiceMessage(new Request(CommonVars.getCurrUser()), (File) files.get(i), projectType);
//					CommonProgress.setMessage("已成功处理完回执：" + ((File) files.get(i)).getName() + "。\n");
//					taExecInfo.append("已成功处理完回执：" + ((File) files.get(i)).getName() + "。\n");
//
//				}
//				CommonProgress.closeProgressDialog();
//			} catch (Exception e) {
//				CommonProgress.closeProgressDialog();
//				JOptionPane.showMessageDialog(DgProcessCustomsMessageTCS.this, "下载通知单失败：！" + e.getMessage(), "提示", 2);
//			}
//		}
//	}

	private String getFileName(String name) {
		JFileChooser fileChooser = new JFileChooser("./");
		fileChooser.addChoosableFileFilter(new ExampleFileFilter("txt"));
		String fileName = "";
		fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
		fileChooser.setSelectedFile(new File(name));
		int state = fileChooser.showSaveDialog(null);
		if (state == JFileChooser.APPROVE_OPTION) {
			File f = fileChooser.getSelectedFile();
			String description = fileChooser.getFileFilter().getDescription();
			if (f.getPath().indexOf(".") > 0 || description.indexOf(".") == -1) {
				fileName = f.getPath();
			} else {
				String suffix = description.substring(description.indexOf("."));
				fileName = f.getPath() + suffix;
			}
		} else {
			return null;
		}
		return fileName;
	}

	class ExampleFileFilter extends FileFilter {
		String suffix = "";

		ExampleFileFilter(String suffix) {
			this.suffix = suffix;
		}

		public boolean accept(File f) {
			String suffix = getSuffix(f);
			if (f.isDirectory() == true) {
				return true;
			}
			if (suffix != null) {
				if (suffix.toLowerCase().equals(this.suffix)) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}

		public String getDescription() {
			return "*." + this.suffix;
		}

		private String getSuffix(File f) {
			String s = f.getPath(), suffix = null;
			int i = s.lastIndexOf('.');
			if (i > 0 && i < s.length() - 1)
				suffix = s.substring(i + 1).toLowerCase();
			return suffix;
		}
	}

	class ReceiptProcess extends Thread {
		public void run() {
			try {
				CommonProgress.showProgressDialog(DgProcessCustomsMessageTCS.this);

				CommonProgress.setMessage("系统开始处理回执，请稍后...");
				List<TempCustomsMessage> list = tableModel.getList();

				if (list == null || list.isEmpty()) {
					JOptionPane.showMessageDialog(DgProcessCustomsMessageTCS.this, "必须选择一条报关单下载！", "提示", 2);
					return;
				}
				Set<String> taskIds = new HashSet<String>();
				TempCustomsMessage tcm = null;
				for (int i = 0; i < list.size(); i++) {
					tcm = list.get(i);
					if (tcm.getIsSelected() && CommonUtils.notEmpty(tcm.getCustomsDeclaration().getTcsTaskId())) {
						taskIds.add(tcm.getCustomsDeclaration().getTcsTaskId());
					}
				}

				List downLoadList = null;
				try {
					taExecInfo.append("开始下载回执...\n");
					downLoadList = messageAction.httpDownloadTCS(new Request(CommonVars.getCurrUser()), projectType, taskIds);
				} catch (RuntimeException e) {
					e.printStackTrace();
				}
				StringBuilder logInfo = null;

				if (downLoadList != null && downLoadList.size() > 0) {
					logInfo = (StringBuilder) downLoadList.get(downLoadList.size() - 1);
					downLoadList.remove(logInfo);
				} else {
					downLoadList = new Vector();
				}

				List files = downLoadList;

				taExecInfo.append(logInfo.toString());

				for (int i = 0; i < files.size(); i++) {
					taExecInfo.append("已下载回执：" + ((File) files.get(i)).getName() + "。\n");
				}
				if (files.size() == 0) {
					taExecInfo.append("下载了0个回执。\n");
				}

				taExecInfo.append("开始处理回执...\n");
				files.clear();

				File[] tempFiles = messageAction.getCustomBillRecvFiles(new Request(CommonVars.getCurrUser()));
				Map<String, File> progessFiles = new HashMap<String, File>();

				for (int i = 0; i < tempFiles.length; i++) {
					progessFiles.put(tempFiles[i].getName(), tempFiles[i]);
					files.add(tempFiles[i].getName());
				}
				Collections.sort(files);

				taExecInfo.append("开始处理回执 Files Total Num:" + files.size() + "\n");
				for (int i = 0; i < files.size(); i++) {
					taExecInfo.append("正在处理回执：" + files.get(i) + "\n");
					messageAction.processTcsReturnMessage(new Request(CommonVars.getCurrUser()), progessFiles.get(files.get(i)), projectType);
					CommonProgress.setMessage("已成功处理完回执：" + files.get(i) + "。\n");
					taExecInfo.append("已成功处理完回执：" + files.get(i) + "。\n");

				}
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgProcessCustomsMessageTCS.this, "处理回执失败：！" + e.getMessage(), "提示", 2);
			}
		}
	}

	class lookEdiMessage extends Thread {
		List files = new Vector();

		public void run() {
			try {
				CommonProgress.showProgressDialog(DgProcessCustomsMessageTCS.this);
				CommonProgress.setMessage("系统正准备生成报文，请稍后...");
				TempCustomsMessage tempcustoms = (TempCustomsMessage) tableModel.getCurrentRows().get(0);
				BaseCustomsDeclaration head1 = tempcustoms.getCustomsDeclaration();
				BaseCustomsDeclaration head = baseEncAction.findCustomsDeclaration(new Request(CommonVars.getCurrUser(), true), head1.getId());
				List ls = CustomBaseList.getInstance().getGbtobigs();
				try {
					if (EncCommon.isImport(head.getImpExpType().intValue())) { // "1":为进口，"2":为出口
						files = messageAction.exportCustom(new Request(CommonVars.getCurrUser()), head, "1", ls);
					} else {
						files = messageAction.exportCustom(new Request(CommonVars.getCurrUser()), head, "2", ls);
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(DgProcessCustomsMessageTCS.this, "生成报文时出错，可能是数据错误。", "生成报文错误", 1);
					CommonProgress.closeProgressDialog();
					ex.printStackTrace();
				}
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgProcessCustomsMessageTCS.this, "生成报文失败：！" + e.getMessage(), "提示", 2);
			} finally {
				DgLookCustomsMessage dg = new DgLookCustomsMessage();
				List message = getFileString(files);
				dg.setList(message);
				dg.setVisible(true);
			}
		}
	}

	private JMenuItem getBtnOut() {
		if (btnOut == null) {
			btnOut = new JMenuItem();
			btnOut.setSize(new Dimension(100, 30));
			btnOut.setText("从外部读取EDI报文                        ");
			btnOut.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.removeChoosableFileFilter(fileChooser.getFileFilter());
					fileChooser.setFileFilter(new CommonFileFilter(new String[] { "imp", "exp" }, "选择文档"));
					int state = fileChooser.showOpenDialog(DgProcessCustomsMessageTCS.this);
					if (state != JFileChooser.APPROVE_OPTION) {
						return;
					}
					ediFile = fileChooser.getSelectedFile();
					List message = readFile(ediFile);
					DgLookCustomsMessage dg = new DgLookCustomsMessage();
					dg.setList(message);
					dg.setVisible(true);

				}
			});
		}
		return btnOut;
	}

	private List readFile(File file) {
		List line = new ArrayList();
		try {
			// 文件为GBK编码，故用GBK编码读
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "GBK"));
			// FileReader fr = new FileReader(file); // 文件流
			// BufferedReader br = new BufferedReader(fr); // 读取流
			System.out.println("-----------------");
			String in;
			while ((in = br.readLine()) != null) { // 读文件
				line.add(in);
				System.out.println("in.gbk=" + in.getBytes().length);
				String a = new String(in.getBytes("GBK"), "ISO8859-1");
				System.out.println("in.iso810=" + a.getBytes("ISO8859-1").length);
			}
			br.close();
			// fr.close();
		} catch (Exception ex) {
			System.out.println("文件没有找到");
			ex.printStackTrace();
		} finally {

		}
		return line;
	}

	private JMenuItem getBtnInner() {
		if (btnInner == null) {
			btnInner = new JMenuItem();
			btnInner.setSize(new Dimension(100, 30));
			btnInner.setText("从内部读取EDI报文                        ");
			btnInner.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel == null || tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgProcessCustomsMessageTCS.this, "请选择要查看的报关单!", "提示", 2);
						return;
					}
					new lookEdiMessage().start();
				}
			});
		}
		return btnInner;
	}

	/**
	 * This method initializes btnViewEdiMessage
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnViewEdiMessage() {
		if (btnViewEdiMessage == null) {
			btnViewEdiMessage = new JButton();
			btnViewEdiMessage.setText("EDI报文信息");
			btnViewEdiMessage.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgCustomsMessageQueryTCS dgCustom = new DgCustomsMessageQueryTCS(projectType);
					dgCustom.setVisible(true);

				}
			});

		}
		return btnViewEdiMessage;
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
					DgProcessCustomsMessageTCS.this.dispose();
				}
			});
		}
		return btnExit;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			javax.swing.JLabel jLabel6 = new JLabel();
			javax.swing.JLabel jLabel = new JLabel();
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jLabel.setText("选取要传送的报关单");
			jLabel.setPreferredSize(new java.awt.Dimension(117, 22));
			jLabel.setBounds(147, 6, 117, 18);
			jLabel6.setBounds(7, 192, 57, 18);
			jLabel6.setText("执行信息");
			jPanel1.add(getJPanel2(), null);
			jPanel1.add(jLabel, null);
			jPanel1.add(getJScrollPane(), null);
			jPanel1.add(jLabel6, null);
			jPanel1.add(getJPanel3(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "请选择要传输的内容", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
			jPanel2.setBounds(6, 8, 132, 177);
			jPanel2.add(getRbImport(), null);
			jPanel2.add(getRbExport(), null);
			jPanel2.add(getRbSpec(), null);
			jPanel2.add(getRbReceipt(), null);
			jPanel2.add(getRbAdvoice(), null);
			jPanel2.add(getRbSupplement(), null);
		}
		return jPanel2;
	}

	/**
	 * This method initializes rbImport
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbImport() {
		if (rbImport == null) {
			rbImport = new JRadioButton();
			rbImport.setText("进口报关单");
			rbImport.setBounds(11, 23, 90, 18);
			rbImport.setName("rbImport");
			rbImport.addActionListener(new RadioActionListner());
		}
		return rbImport;
	}

	/**
	 * This method initializes rbExport
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbExport() {
		if (rbExport == null) {
			rbExport = new JRadioButton();
			rbExport.setText("出口报关单");
			rbExport.setBounds(11, 48, 90, 18);
			rbExport.setName("rbExport");
			rbExport.addActionListener(new RadioActionListner());
		}
		return rbExport;
	}

	/**
	 * This method initializes rbSpec
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbSpec() {
		if (rbSpec == null) {
			rbSpec = new JRadioButton();
			rbSpec.setText("特殊报关单");
			rbSpec.setBounds(11, 73, 90, 18);
			rbSpec.setName("rbSpec");
			rbSpec.addActionListener(new RadioActionListner());
		}
		return rbSpec;
	}

	/**
	 * This method initializes rbReceipt
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbReceipt() {
		if (rbReceipt == null) {
			rbReceipt = new JRadioButton();
			rbReceipt.setBounds(11, 123, 82, 18);
			rbReceipt.setText("下载回执");
			rbReceipt.setName("rbReceipt");
			rbReceipt.addActionListener(new RadioActionListner());
		}
		return rbReceipt;
	}

	/**
	 * This method initializes tbCustomBill
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbCustomBill() {
		if (tbCustomBill == null) {
			tbCustomBill = new JTable();
		}
		return tbCustomBill;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setBounds(146, 24, 635, 186);
			jScrollPane.setViewportView(getTbCustomBill());
		}
		return jScrollPane;
	}

	/**
	 * @return Returns the tableModel.
	 */
	public JTableListModel getTableModel() {
		return tableModel;
	}

	/**
	 * 是进口还是出口
	 */
	boolean isImport = false;

	private JScrollPane jScrollPane1 = null;

	private JButton jButton = null;
	private JScrollPane jScrollPane2 = null;

	private JButton jButton1 = null;

	private JRadioButton rbAdvoice = null;

	private JComboBox cbbEmshead = null;

	private JLabel jLabel1 = null;

	private JRadioButton rbSupplement = null;

	private JButton btnNetwork = null;

	// 选择
	private class RadioActionListner implements ActionListener {
		public void actionPerformed(java.awt.event.ActionEvent e) {
			radioActionListnerExecute();
		}
	}

	private void radioActionListnerExecute() {

		String str1 = "";
		String str2 = "";
		if (projectType == ProjectType.BCS) {
			Contract c = (Contract) cbbEmshead.getSelectedItem();
			str1 = c.getEmsNo();
			str2 = c.getImpContractNo();
		} else if (projectType == ProjectType.BCUS) {
			EmsHeadH2k head = (EmsHeadH2k) cbbEmshead.getSelectedItem();
			str1 = head.getEmsNo();
			str2 = head.getPreEmsNo();
		} else if (projectType == ProjectType.DZSC) {
			DzscEmsPorHead head = (DzscEmsPorHead) cbbEmshead.getSelectedItem();
			str1 = head.getEmsNo();
			str2 = head.getIeContractNo();
		}
		BaseEncAction encAction = baseEncAction;
		List tempList = null;
		if (rbImport.isSelected()) {
			System.out.println("料件");
			if (str1 == null || "".equals(str1)) {
				tempList = encAction.getImportDataSource(new Request(CommonVars.getCurrUser(), true));
			} else {
				tempList = encAction.getImportDataSource(new Request(CommonVars.getCurrUser(), true), str1, str2);
			}
			initTable(null);
		} else if (rbExport.isSelected()) {
			System.out.println("成品");
			if (str1 == null || "".equals(str1)) {
				tempList = encAction.getExportDataSource(new Request(CommonVars.getCurrUser()));
			} else {
				tempList = encAction.getExportDataSource(new Request(CommonVars.getCurrUser()), str1, str2);
			}
			initTable(null);
		} else if (rbSpec.isSelected()) {
			if (str1 == null || "".equals(str1)) {
				tempList = encAction.getSpexportDataSource(new Request(CommonVars.getCurrUser()));
			} else {
				tempList = encAction.getSpexportDataSource(new Request(CommonVars.getCurrUser()), str1, str2);
			}
			initTable(null);
		} else if (rbSupplement.isSelected()) {
			initTableSuppment();
			tempList = new ArrayList();
			List dataList = baseEncAction.findDecSupplementListByIsSend(new Request(CommonVars.getCurrUser()), "0");
			Iterator it = dataList.iterator();
			// initTableSuppment();
			while (it.hasNext()) {
				Object[] obj = (Object[]) it.next();
				Object[] temp = new Object[obj.length + 1];
				temp[0] = false;
				temp[1] = obj[0].toString();
				temp[2] = (obj[1].toString());
				temp[3] = (obj[2].toString());
				temp[4] = (CommonUtils.getDate(CommonUtils.convertStrToDate(obj[3].toString()), "yyyy-MM-dd"));
				temp[5] = (obj[4].toString());
				temp[6] = (obj[5].toString());
				temp[7] = (obj[6].toString());
				temp[8] = (obj[7].toString());
				tempList.add(temp);
			}
		} else if (rbReceipt.isSelected() || rbAdvoice.isSelected()) {
			initTable(null);
			List<BaseCustomsDeclaration> list = encAction.findDeclarationHasTaskIdUnEffect(new Request(CommonVars.getCurrUser()));
			tempList = new ArrayList<TempCustomsMessage>();
			TempCustomsMessage tcm = null;
			BaseCustomsDeclaration declaration = null;
			for (int i = 0; i < list.size(); i++) {
				declaration = list.get(i);
				tcm = new TempCustomsMessage();
				tcm.setCustomsDeclaration(declaration);
				tempList.add(tcm);
				tcm.setIsSelected(true);
			}

		}
		if (getTableModel().getList() != null) {
			getTableModel().getList().clear();
		}
		// Object t = getTableModel().getColumns();
		getTableModel().setList(tempList);

	}

	/**
	 * This method initializes taExecInfo
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getTaExecInfo() {
		if (taExecInfo == null) {
			taExecInfo = new JTextArea();
			taExecInfo.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (jPopupMenu != null) {
						jPopupMenu.setVisible(false);
					} else {
						jPopupMenu = getJPopupMenu();
					}
					taExecInfo.invalidate();
					taExecInfo.repaint();
					if (e.getButton() == MouseEvent.BUTTON3) {
						jPopupMenu.show(e.getComponent(), e.getX(), e.getY());
					}
				}
			});
		}
		return taExecInfo;
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
			jPanel3.setBounds(6, 216, 777, 196);
			jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jPanel3.add(getJScrollPane1(), java.awt.BorderLayout.SOUTH);
			jPanel3.add(getJScrollPane2(), java.awt.BorderLayout.CENTER);
		}
		return jPanel3;
	}

	/**
	 * This method initializes jPopupMenu
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getJPopupMenu() {
		if (jPopupMenu == null) {
			jPopupMenu = new JPopupMenu();
			jPopupMenu.setVisible(true);
			jPopupMenu.add(getJMenuItem());
		}
		return jPopupMenu;
	}

	/**
	 * This method initializes jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItem() {
		if (jMenuItem == null) {
			jMenuItem = new JMenuItem();
			jMenuItem.setText("清除");
			jMenuItem.setVisible(true);
			jMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					taExecInfo.setText("");

					// jPopupMenu.setVisible(false);
				}
			});
		}
		return jMenuItem;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
		}
		return jScrollPane1;
	}

	public List getFileString(List lines) {
		List sb = new ArrayList();
		for (int i = 0; i < lines.size(); i++) {
			try {
				sb.add((String) lines.get(i));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sb;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("查看EDI报文");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getPmEdiFunction().show(jButton, 0, jButton.getHeight());
				}
			});
		}
		return jButton;
	}

	private JPopupMenu getPmEdiFunction() {
		if (pmEdiFunction == null) {
			pmEdiFunction = new JPopupMenu();
			pmEdiFunction.setSize(new Dimension(110, 36));
			pmEdiFunction.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			/**
			 * 从外部读取EDI报文
			 */
			pmEdiFunction.add(getBtnOut());
			Separator separator = new Separator();
			separator.setForeground(Color.gray);
			/**
			 * 增加分割线
			 */
			pmEdiFunction.add(separator);
			/**
			 * 从内部读取EDI报文
			 */
			pmEdiFunction.add(getBtnInner());
		}
		return pmEdiFunction;
	}

	/**
	 * 获得选中关封申请单中的信息
	 */
	public List getParentList() {
		if (this.tableModel == null) {
			return null;
		}
		List list = tableModel.getList();
		List newList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) instanceof TempCustomsMessage) {
				TempCustomsMessage t = (TempCustomsMessage) list.get(i);
				if (t.getIsSelected().booleanValue() == true) {
					newList.add(t);
				}
			} else if (list.get(i) instanceof Object) {
				Object[] obj = (Object[]) list.get(i);
				if (((Boolean) obj[0]).booleanValue() == true) {
					newList.add(obj);
				}
			}
		}
		return newList;
	}

	/**
	 * This method initializes jScrollPane2
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setViewportView(getTaExecInfo());
		}
		return jScrollPane2;
	}

	/**
	 * @param baseEncAction
	 *            The baseEncAction to set.
	 */
	public void setBaseEncAction(BaseEncAction baseEncAction) {
		this.baseEncAction = baseEncAction;
	}

	/**
	 * @return Returns the tableModelCustoms.
	 */
	public JTableListModel getTableModelCustoms() {
		return tableModelCustoms;
	}

	/**
	 * @param tableModelCustoms
	 *            The tableModelCustoms to set.
	 */
	public void setTableModelCustoms(JTableListModel tableModelCustoms) {
		this.tableModelCustoms = tableModelCustoms;
	}

	/**
	 * @return Returns the projectType.
	 */
	public int getProjectType() {
		return projectType;
	}

	/**
	 * @param projectType
	 *            The projectType to set.
	 */
	public void setProjectType(int projectType) {
		this.projectType = projectType;
		System.out.println(projectType);
		// 电子手册
		if (projectType == ProjectType.DZSC) {
			this.jLabel1.setText("手册号+合同号:");
			List<DzscEmsPorHead> heads = dzscAction.findExeEmsPorHead(new Request(CommonVars.getCurrUser()));
			heads.add(0, new DzscEmsPorHead());
			DefaultComboBoxModel listModel = new DefaultComboBoxModel();
			if (heads != null && heads.size() > 0) {
				for (int i = 0; i < heads.size(); i++) {
					// this.cbbEmshead.addItem();
					listModel.addElement((DzscEmsPorHead) heads.get(i));
				}
				this.cbbEmshead.setModel(listModel);
				this.cbbEmshead.setRenderer(CustomBaseRender.getInstance().getRender("emsNo", "ieContractNo", 100, 150).setForegroundColor(java.awt.Color.red));
			}
			// if (this.cbbEmshead.getItemCount() > 0) {
			// this.cbbEmshead.setSelectedIndex(0);
			// }
		} else if (projectType == ProjectType.BCUS) {// 电子帐册
			this.jLabel1.setText("正在执行的帐册号:");
			List<EmsHeadH2k> heads = manualDeclareAction.findEmsHeadH2kInExecuting(new Request(CommonVars.getCurrUser(), true));
			DefaultComboBoxModel listModel = new DefaultComboBoxModel();
			heads.add(0, new EmsHeadH2k());
			if (heads != null && heads.size() > 0) {
				for (int i = 0; i < heads.size(); i++) {
					// this.cbbEmshead.addItem((EmsHeadH2k)heads.get(i));
					listModel.addElement((EmsHeadH2k) heads.get(i));
				}
				this.cbbEmshead.setModel(listModel);
				this.cbbEmshead.setRenderer(CustomBaseRender.getInstance().getRender("emsNo", "preEmsNo", 100, 150).setForegroundColor(java.awt.Color.red));
			}
			// if (this.cbbEmshead.getItemCount() > 0) {
			// this.cbbEmshead.setSelectedIndex(0);
			// }
		} else if (projectType == ProjectType.BCS) {// 电子化手册
			this.jLabel1.setText("手册号+合同号:");
			List<Contract> heads = contractAction.findContractByProcessExe(new Request(CommonVars.getCurrUser(), true));
			DefaultComboBoxModel listModel = new DefaultComboBoxModel();
			heads.add(0, new Contract());
			if (heads != null && heads.size() > 0) {
				for (int i = 0; i < heads.size(); i++) {
					// this.cbbEmshead.addItem((Contract)heads.get(i));
					listModel.addElement((Contract) heads.get(i));
				}
				this.cbbEmshead.setModel(listModel);
				this.cbbEmshead
						.setRenderer(CustomBaseRender.getInstance().getRender("emsNo", "impContractNo", 100, 150).setForegroundColor(java.awt.Color.red));
			}
			// if (this.cbbEmshead.getItemCount() > 0) {
			// this.cbbEmshead.setSelectedIndex(0);
			// }
		}
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("异常回执处理");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgReturnDeal dg = new DgReturnDeal();
					dg.setProjectType(projectType);
					dg.setVisible(true);
				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes rbAdvoice
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbAdvoice() {
		if (rbAdvoice == null) {
			rbAdvoice = new JRadioButton();
			rbAdvoice.setBounds(new Rectangle(11, 148, 94, 18));
			rbAdvoice.setText("下载通知单");
			rbAdvoice.setName("rbAdvoice");
			rbAdvoice.addActionListener(new RadioActionListner());
		}
		return rbAdvoice;
	}

	/**
	 * This method initializes cbbEmshead
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbEmshead() {
		if (cbbEmshead == null) {
			cbbEmshead = new JComboBox();
			cbbEmshead.setPreferredSize(new Dimension(150, 24));
			cbbEmshead.setUI(new CustomBaseComboBoxUI(300));
			cbbEmshead.removeAllItems();
			cbbEmshead.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String str1 = "";
					String str2 = "";
					if (projectType == ProjectType.BCS) {
						Contract c = (Contract) cbbEmshead.getSelectedItem();
						str1 = c.getEmsNo();
						str2 = c.getImpContractNo();
					} else if (projectType == ProjectType.BCUS) {
						EmsHeadH2k head = (EmsHeadH2k) cbbEmshead.getSelectedItem();
						str1 = head.getEmsNo();
						str2 = head.getPreEmsNo();
					} else if (projectType == ProjectType.DZSC) {
						DzscEmsPorHead head = (DzscEmsPorHead) cbbEmshead.getSelectedItem();
						str1 = head.getEmsNo();
						str2 = head.getIeContractNo();
					}
					BaseEncAction encAction = baseEncAction;
					List tempList = null;
					if (rbImport.isSelected()) {
						System.out.println("料件");
						if (str1 == null || "".equals(str1)) {
							tempList = encAction.getImportDataSource(new Request(CommonVars.getCurrUser(), true));
						} else {
							tempList = encAction.getImportDataSource(new Request(CommonVars.getCurrUser(), true), str1, str2);
						}
					} else if (rbExport.isSelected()) {
						System.out.println("成品");
						if (str1 == null || "".equals(str1)) {
							tempList = encAction.getExportDataSource(new Request(CommonVars.getCurrUser()));
						} else {
							tempList = encAction.getExportDataSource(new Request(CommonVars.getCurrUser()), str1, str2);
						}
					} else if (rbSpec.isSelected()) {
						System.out.println("特殊报关单");
						if (str1 == null || "".equals(str1)) {
							tempList = encAction.getSpexportDataSource(new Request(CommonVars.getCurrUser()));
						} else {
							tempList = encAction.getSpexportDataSource(new Request(CommonVars.getCurrUser()), str1, str2);
						}
					} else {
						tempList = new Vector();
					}
					if (DgProcessCustomsMessageTCS.this.getTableModel().getList() != null) {
						DgProcessCustomsMessageTCS.this.getTableModel().getList().clear();
					}
					DgProcessCustomsMessageTCS.this.getTableModel().setList(tempList);

				}
			});

			// btnForcePass.setVisible(false);
		}
		return cbbEmshead;
	}

	/**
	 * This method initializes btnNetwork
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnNetwork() {
		if (btnNetwork == null) {
			btnNetwork = new JButton();
			btnNetwork.setText("网络测试");
			btnNetwork.setToolTipText("测试FTP与平台(ftp://edi.bsw.com.cn)是否畅通");
			btnNetwork.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// TcsParameters p =
					// tcsParametersAction.getTcsParameters(new Request(
					// CommonVars.getCurrUser()));
					// // 系统参数
					// CompanyOther companyOther =
					// customBaseAction.findCompanyOther();
					// String ftpAddress = p.getFtpAddress();
					// if(!RegexUtil.checkIsIP(ftpAddress)){
					// JOptionPane.showMessageDialog(
					// DgProcessCustomsMessageTCS.this,
					// "请看系统设置中FTP是否设置正确！", "提示！", 1);
					// return;
					// }
					// ftpAddress = p.getFtpAddress();
					// // String info =
					// TCSMessageLogic.getNetConnection(p.getFtpAddress(),
					// p.getFtpName(), p.getFtpPwd(),21);
					// FtpClientUtil ftpClientUtil=new FtpClientUtil();
					// ftpClientUtil.setFtpParameters(p.getFtpAddress(),21,p.getFtpName(),
					// p.getFtpPwd(),companyOther.getIsFtpPASV(),3000);
					try {
						boolean isConn = messageAction.testConnect();
						taExecInfo.setText("");
						if (!isConn) {
							JOptionPane.showMessageDialog(DgProcessCustomsMessageTCS.this, "网络畅通", "提示！", 1);
							return;
						} else {
							JOptionPane.showMessageDialog(DgProcessCustomsMessageTCS.this, "网络不通,请检查互联网是否畅通,再查看系统设置中HTTP是否设置正确.", "提示！", 1);
							return;
						}
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(DgProcessCustomsMessageTCS.this, "网络不通,详细信息如下：" + ex.getMessage(), "提示！", 1);
						return;
					}
				}
			});
		}
		return btnNetwork;
	}

	/**
	 * This method initializes rbSupplement
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbSupplement() {
		if (rbSupplement == null) {
			rbSupplement = new JRadioButton();
			rbSupplement.setBounds(new Rectangle(11, 98, 113, 18));
			rbSupplement.setName("rbSupplement");
			rbSupplement.setText("补充申报(被动)");
			rbSupplement.addActionListener(new RadioActionListner());

		}
		return rbSupplement;
	}

}

class checkBoxRenderer extends DefaultTableCellRenderer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JCheckBox checkBox = new JCheckBox();

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		if (value == null) {
			checkBox.setSelected(false);
		} else if (value.equals("")) {
			checkBox.setSelected(false);
		} else if (Boolean.valueOf(value.toString()) instanceof Boolean) {
			checkBox.setSelected(Boolean.parseBoolean(value.toString()));
		}
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

}

class Temp implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Boolean isSelected = null;
	private String emsHeadH2k;
	private String serialNumber;
	private String customsDeclarationCode;
	private String declarationDate;
	private String isCheck;
	private String impExpType;

	public Boolean getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}

	public String getEmsHeadH2k() {
		return emsHeadH2k;
	}

	public void setEmsHeadH2k(String emsHeadH2k) {
		this.emsHeadH2k = emsHeadH2k;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getCustomsDeclarationCode() {
		return customsDeclarationCode;
	}

	public void setCustomsDeclarationCode(String customsDeclarationCode) {
		this.customsDeclarationCode = customsDeclarationCode;
	}

	public String getDeclarationDate() {
		return declarationDate;
	}

	public void setDeclarationDate(String declarationDate) {
		this.declarationDate = declarationDate;
	}

	public String getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}

	public String getImpExpType() {
		return impExpType;
	}

	public void setImpExpType(String impExpType) {
		this.impExpType = impExpType;
	}

}