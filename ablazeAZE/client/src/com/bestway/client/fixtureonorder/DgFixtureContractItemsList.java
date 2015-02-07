/*
 * Created on 2005-5-20
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.client.fixtureonorder;

import java.awt.Component;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableCellRenderer;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcs.client.contractanalyse.DgPreCustomsDeclaration;
import com.bestway.bcs.contractstat.action.ContractStatAction;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.CustomsDeclarationState;
import com.bestway.common.constant.ImpExpType;
import com.bestway.fixtureonorder.action.FixtureContractAction;
import com.bestway.fixtureonorder.entity.FixtureContract;
import com.bestway.fixtureonorder.entity.ImpExpCustomsDeclarationCommInfo;
import com.bestway.ui.winuicontrol.JDialogBase;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JSplitPane;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import com.bestway.bcs.client.contractanalyse.JContractList;
import javax.swing.JComboBox;

/**
 * @author fhz
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class DgFixtureContractItemsList extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private JButton btnQuery = null;

	private JButton btnPrint = null;

	private JButton btnExit = null;

	private JTableListModel tableModel = null;

	private JPanel jPanel = null;

	private JLabel jLabel = null;

	private JRadioButton rbYes = null;

	private JRadioButton rbNo = null;

	private JRadioButton rbAll = null;

	private ButtonGroup buttonGroup = null; // @jve:decl-index=0:visual-constraint="770,116"

	private JPanel jPanel1 = null;

	private JComboBox cbbContract = null;

	private FixtureContractAction fixtureContractAction = null;

	/**
	 * This is the default constructor
	 */
	public DgFixtureContractItemsList() {
		super();
		fixtureContractAction = (FixtureContractAction) CommonVars
				.getApplicationContext().getBean("fixtureContractAction");
		initialize();
		this.getButtonGroup();

	}

	public void setVisible(boolean b) {
		if (b) {
			List list = new ArrayList();
			initTable(list);
		}
		super.setVisible(b);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("设备报关登记表");
		this.setSize(750, 510);
		this.setContentPane(getJContentPane());
		initcbbContract();
	}

	private void initcbbContract() {
		List contracts = fixtureContractAction
				.findContractByProcessExe(new Request(CommonVars.getCurrUser()));
		this.cbbContract.removeAllItems();
		if (contracts != null && contracts.size() > 0) {
			for (int i = 0; i < contracts.size(); i++) {
				this.cbbContract.addItem((FixtureContract) contracts.get(i));
			}
			this.cbbContract.setRenderer(CustomBaseRender.getInstance()
					.getRender("impContractNo", "emsNo", 100, 100)
					.setForegroundColor(java.awt.Color.red));
		}
		if (this.cbbContract.getItemCount() > 0) {
			cbbContract.setSelectedIndex(0);
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
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.SOUTH);
			jContentPane.add(getJScrollPane(), BorderLayout.CENTER);
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
			jToolBar.add(getJPanel());
			jToolBar.add(getBtnExit());
		}
		return jToolBar;
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
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnQuery() {
		if (btnQuery == null) {
			btnQuery = new JButton();
			btnQuery.setText("查询");
			btnQuery.setBounds(new Rectangle(563, 2, 58, 24));
			btnQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (cbbContract.getSelectedItem() == null) {
						JOptionPane.showMessageDialog(
								DgFixtureContractItemsList.this, "请选择合同!",
								"提示!", JOptionPane.WARNING_MESSAGE);
						return;
					}
					int state = -1;
					if (rbAll.isSelected()) {
						state = CustomsDeclarationState.ALL;
					} else if (rbNo.isSelected()) {
						state = CustomsDeclarationState.NOT_EFFECTIVED;
					} else if (rbYes.isSelected()) {
						state = CustomsDeclarationState.EFFECTIVED;
					}
					DgStatQueryCondition dg = new DgStatQueryCondition();
					dg.setFixtureContract((FixtureContract) cbbContract
							.getSelectedItem());
					dg.setState(state);
					dg.setVisible(true);
					List list = dg.getLsResult();
					if (list != null) {
						initTable(list);
					}
				}
			});
		}
		return btnQuery;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton();
			btnPrint.setText("打印");
			btnPrint.setBounds(new Rectangle(621, 2, 58, 24));
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (cbbContract.getSelectedItem() == null) {
						JOptionPane.showMessageDialog(
								DgFixtureContractItemsList.this, "没有正在执行的合同!",
								"提示!", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					FixtureContract fixtureContract = (FixtureContract) cbbContract
							.getSelectedItem();
					List<ImpExpCustomsDeclarationCommInfo> dlist = tableModel
							.getList();
					List dsList = new ArrayList();
					for (ImpExpCustomsDeclarationCommInfo data : dlist) {
						if (data.getBaseCustomsDeclaration().getEmsHeadH2k()
								.equals(
										((FixtureContract) cbbContract
												.getSelectedItem()).getEmsNo())) {
							dsList.add(data);
						}
					}
					if (dsList.size() <= 0) {
						JOptionPane.showMessageDialog(
								DgFixtureContractItemsList.this, "没有数据可打印",
								"提示", JOptionPane.INFORMATION_MESSAGE);
						return;
					}

					CustomReportDataSource ds = new CustomReportDataSource(
							dsList);
					InputStream reportStream = DgFixtureContractItemsList.class
							.getResourceAsStream("report/ImpExpCommodityStatus.jasper");
					SimpleDateFormat dateFormat = new SimpleDateFormat(
							"yyyy-MM-dd");
					Map<String, Object> parameters = new HashMap<String, Object>();
					parameters.put("isImport", new Boolean(true));
					parameters.put("contractNo", fixtureContract
							.getExpContractNo());
					parameters.put("emsNo", fixtureContract.getEmsNo());
					parameters.put("beginDate",
							fixtureContract.getBeginDate() == null ? ""
									: dateFormat.format(fixtureContract
											.getBeginDate()));
					parameters.put("availabilityDate", fixtureContract
							.getAvailabilityDate() == null ? "" : dateFormat
							.format(fixtureContract.getAvailabilityDate()));
					parameters.put("companyName", CommonVars.getCurrUser()
							.getCompany().getName());
					parameters.put("reportTitle", "设备报关登记表");
					JasperPrint jasperPrint = null;
					try {
						jasperPrint = JasperFillManager.fillReport(
								reportStream, parameters, ds);
						DgReportViewer viewer = new DgReportViewer(jasperPrint);
						viewer.setVisible(true);
					} catch (JRException e1) {
						e1.printStackTrace();
					}
				}
			});
		}
		return btnPrint;
	}

	/**
	 * This method initializes jButton2
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

	/**
	 * 初始化数据Table
	 */
	private void initTable(List list) {
		if (list == null) {
			list = new ArrayList();
		}
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("报关日期",
								"baseCustomsDeclaration.declarationDate", 100));
						list.add(addColumn("运输工具名称",
								"baseCustomsDeclaration.conveyance", 100));
						list.add(addColumn("商品编码", "complex.code", 100));
						list.add(addColumn("品名、规格", "commNameAndSpec", 200));
						list.add(addColumn("单位", "unit.name", 50));
						list.add(addColumn("设备性质", "fixKind", 100));
						list.add(addColumn("数量", "commAmount", 100));
						list.add(addColumn("价值", "commTotalPrice", 100));
						list.add(addColumn("进口数量累计", "commAddInAmount", 100));
						list.add(addColumn("出口数量累计", "commAddOutAmount", 100));
						list.add(addColumn("进口地海关查验人签章", "", 100));
						list
								.add(addColumn(
										"原产国",
										"country.name",
										100));
						list.add(addColumn("备注", "materielNo", 150));
						// list
						// .add(addColumn(
						// "报关单号",
						// "baseCustomsDeclaration.customsDeclarationCode",
						// 100));
						//
						// list.add(addColumn("进出口类型",
						// "baseCustomsDeclaration.impExpType", 100));
						// list.add(addColumn("贸易方式",
						// "baseCustomsDeclaration.tradeMode.name", 100));
						// list.add(addColumn("供应商名称",
						// "baseCustomsDeclaration.customer.name", 100));
						// list.add(addColumn("法定数量", "firstAmount", 100));
						// list.add(addColumn("法定单位", "legalUnit.name", 100));
						// list.add(addColumn("报关单单价", "commUnitPrice", 100));
						//
						// list.add(addColumn("毛重", "grossWeight", 100));
						// list.add(addColumn("净重", "netWeight", 100));
						// list.add(addColumn("总毛重",
						// "baseCustomsDeclaration.grossWeight", 100));
						// list.add(addColumn("总净重",
						// "baseCustomsDeclaration.netWeight", 100));
						// list.add(addColumn("包装方式",
						// "baseCustomsDeclaration.wrapType.name", 50));
						// list.add(addColumn("件数",
						// "baseCustomsDeclaration.commodityNum", 50));
						// list.add(addColumn("总重量", "grossWeight", 100));
						return list;
					}
				});
		jTable.getColumnModel().getColumn(6).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText(change(value));
						return this;
					}

				});
	}

	private String change(Object value) {
		if (value == null)
			return "";
		else
			return Integer.valueOf(value.toString()) == 0 ? "不作价设备" : "借用设备";

	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(2, 5, 68, 20));
			jLabel.setText("报关单状态");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel, null);
			jPanel.add(getRbYes(), null);
			jPanel.add(getRbNo(), null);
			jPanel.add(getRbAll(), null);
			jPanel.add(getCbbContract(), null);
			jPanel.add(getBtnQuery(), null);
			jPanel.add(getBtnPrint(), null);

		}
		return jPanel;
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbYes() {
		if (rbYes == null) {
			rbYes = new JRadioButton();
			rbYes.setBounds(new Rectangle(111, 5, 64, 20));
			rbYes.setText("已生效");
			rbYes.setSelected(true);
		}
		return rbYes;
	}

	/**
	 * This method initializes jRadioButton1
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbNo() {
		if (rbNo == null) {
			rbNo = new JRadioButton();
			rbNo.setBounds(new Rectangle(175, 5, 64, 20));
			rbNo.setText("未生效");
		}
		return rbNo;
	}

	/**
	 * This method initializes jRadioButton2
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbAll() {
		if (rbAll == null) {
			rbAll = new JRadioButton();
			rbAll.setBounds(new Rectangle(235, 5, 59, 20));
			rbAll.setText("全部");
		}
		return rbAll;
	}

	/**
	 * This method initializes buttonGroup
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup() {
		if (buttonGroup == null) {
			buttonGroup = new ButtonGroup();
			buttonGroup.add(rbYes);
			buttonGroup.add(rbNo);
			buttonGroup.add(rbAll);
		}
		return buttonGroup;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes cbbContract
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbContract() {
		if (cbbContract == null) {
			cbbContract = new JComboBox();
			cbbContract.setBounds(new Rectangle(368, 2, 195, 24));
		}
		return cbbContract;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
