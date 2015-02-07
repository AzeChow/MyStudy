package com.bestway.client.selfcheck;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcs.client.contractanalyse.JContractList;
import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcus.cas.action.CasCheckAction;
import com.bestway.bcus.cas.invoice.entity.TempCustomsDeclarCommInfo;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomReportDataSourceNew;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.CustomsDeclarationState;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.dzsc.client.dzscmanage.JDzscEmsPorHeadList;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public class DgTransferDetailInfo extends JDialogBase {

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

	private ButtonGroup buttonGroup = null; // @jve:decl-index=0:
	private ButtonGroup buttonGroupType = null; // @jve:decl-index=0:

	private JSplitPane jSplitPane = null;

	private JPanel pnRight = null;

	private JScrollPane jScrollPane1 = null;

	// 电子化手册
	private JContractList contractList = null;

	// 电子帐册

	// 电子手册
	private JDzscEmsPorHeadList dzscList = null;

	private JPanel jPanel10 = null;

	private JRadioButton rbAllYes = null;

	private JRadioButton rbAllNot = null;

	private ButtonGroup buttonGroup1 = null; // @jve:decl-index=0:

	private ContractAction contractAction = null;

	private Boolean isImport = false; // @jve:decl-index=0:

	private JLabel jLabel1 = null;

	private int currentType = 0;

	// 查询数据时选中的合同
	private List clist = new ArrayList();

	private JCheckBox cbDefault = null;

	private static int defaultType = 0;

	private JPanel jPanel1 = null;

	private JPanel jPanel2 = null;

	private JRadioButton rbBcs = null;

	private JRadioButton rbDzsc = null;

	private JRadioButton rbBcus = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel3 = null;

	private JLabel jLabel4 = null;

	private JLabel jLabel5 = null;

	private JLabel jLabel6 = null;

	private JLabel jLabel7 = null;

	private JComboBox cbbHsCode = null;

	private JComboBox cbbHsName = null;

	private JComboBox cbbSeqNo = null;

	private JComboBox cbbCustomer = null;

	private JComboBox cbbImpExpType = null;

	private JLabel jLabel8 = null;

	private JCalendarComboBox cbbBeginDate = null;

	private JCalendarComboBox cbbEndDate = null;

	private CasCheckAction casCheckAction = null;

	// 是否正在执行的合同 选项
	private JCheckBox cbIsExingContract;

	// 是否已核销的合同选项
	private JCheckBox cbIsCavContract;

	/**
	 * This is the default constructor
	 */
	public DgTransferDetailInfo() {
		super();

		contractAction = (ContractAction) CommonVars.getApplicationContext()
				.getBean("contractAction");
		casCheckAction = (CasCheckAction) CommonVars.getApplicationContext()
				.getBean("casCheckAction");
		initialize();

		this.getButtonGroup();
		this.getButtonGroupType();
	}

	public void setVisible(boolean b) {
		if (b) {
			initUI(defaultType);// 初始化界
			jSplitPane.setDividerLocation(0.8);
		}
		super.setVisible(b);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("结转明细表");
		this.setSize(1017, 634);
		this.setContentPane(getJContentPane());
		getButtonGroup1();
	}

	public Boolean getIsImport() {
		return isImport;
	}

	public void setIsImport(Boolean isImport) {
		this.isImport = isImport;
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
			jContentPane.add(getJPanel1(), BorderLayout.NORTH);
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
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
			btnQuery.setBounds(new Rectangle(609, 35, 90, 22));
			btnQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					queryData();
				}
			});
		}
		return btnQuery;
	}

	class MyFindThread extends Thread {
		public void run() {
			CommonProgress.showProgressDialog();
			CommonProgress.setMessage("系统正获取数据，请稍后...");

			int state = -1;
			if (rbAll.isSelected()) {
				state = CustomsDeclarationState.ALL;
			} else if (rbNo.isSelected()) {
				state = CustomsDeclarationState.NOT_EFFECTIVED;
			} else if (rbYes.isSelected()) {
				state = CustomsDeclarationState.EFFECTIVED;
			}

			String commCode = "";
			String customer = "";
			String impExpType = "";
			Integer seqNum = null;
			String complexCode = "";
			String name = "";

			if (cbbSeqNo.getSelectedItem() != null) {
				seqNum = ((TempCustomsDeclarCommInfo) cbbSeqNo
						.getSelectedItem()).getSeqNum();
			}
			if (cbbHsName.getSelectedItem() != null) {
				name = ((TempCustomsDeclarCommInfo) cbbHsName.getSelectedItem())
						.getName();
			}
			if (cbbHsCode.getSelectedItem() != null) {
				commCode = ((TempCustomsDeclarCommInfo) cbbHsCode
						.getSelectedItem()).getCode();
			}
			if (cbbCustomer.getSelectedItem() != null) {
				customer = ((ScmCoc) cbbCustomer.getSelectedItem()).getId();
			}
			if (cbbImpExpType.getSelectedItem() != null) {
				impExpType = ((ItemProperty) cbbImpExpType.getSelectedItem())
						.getCode();
			}

			Date beginDate = cbbBeginDate.getDate();
			Date endDate = cbbEndDate.getDate();

			try {
				List lsResult = casCheckAction
						.findTransferCustomsDeclarationCommInfo(new Request(
								CommonVars.getCurrUser()), getIsImport(),
								seqNum, commCode, name, customer, impExpType,
								beginDate, endDate, clist, state, -1,
								currentType);
				initTable(lsResult);
			} catch (Exception e) {
				e.printStackTrace();

				JOptionPane.showMessageDialog(DgTransferDetailInfo.this,
						"获取数据失败：！" + e.getMessage(), "提示", 2);
			}

			// 查询送出货结转明细信息

			CommonProgress.closeProgressDialog();

		}
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
			btnPrint.setBounds(new Rectangle(609, 59, 90, 22));
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					printData();
				}
			});
		}
		return btnPrint;
	}

	public void printData() {
		try {
			List list = tableModel.getList();
			CustomReportDataSourceNew ds = new CustomReportDataSourceNew(list);

			InputStream masterReportStream = DgMaterialDifferent.class
					.getResourceAsStream("report/TransferDetailInfo.jasper");
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("title", getTitle());
			// parameters.put("type", getMaterielType());
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					masterReportStream, parameters, ds);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
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
						list.add(addColumn("手册编号",
								"baseCustomsDeclaration.emsHeadH2k", 100));// 1
						list.add(addColumn("合同序号", "commSerialNo", 100));// 2

						list.add(addColumn("报关日期",
								"baseCustomsDeclaration.declarationDate", 100));// 3
						list.add(addColumn("运输工具名称",
								"baseCustomsDeclaration.conveyance", 100));// 4
						list.add(addColumn(
								"报关单号",
								"baseCustomsDeclaration.customsDeclarationCode",
								100));// 5
						list.add(addColumn("报关单流水号",
								"baseCustomsDeclaration.serialNumber", 100));// 6
						list.add(addColumn("商品项号", "serialNumber", 100));// 7
						list.add(addColumn("商品编码", "complex.code", 100));// 8
						list.add(addColumn("品名", "commName", 100));// 9
						list.add(addColumn("规格", "commSpec", 100));// 10
						list.add(addColumn("单位", "unit.name", 50));// 11
						list.add(addColumn("币制",
								"baseCustomsDeclaration.currency", 50));// 12
						list.add(addColumn("数量", "commAmount", 100));// 13
						list.add(addColumn("价值", "commTotalPrice", 100));// 14
						list.add(addColumn("数量累计", "commAddUpAmount", 100));// 15
						list.add(addColumn("原产国", "country.name", 100));// 16

						list.add(addColumn("进出口类型",
								"baseCustomsDeclaration.impExpType", 100));// 17
						list.add(addColumn("贸易方式",
								"baseCustomsDeclaration.tradeMode.name", 100));// 18
						list.add(addColumn("进口口岸",
								"baseCustomsDeclaration.customs.name", 100));// 19
						list.add(addColumn("供应商名称",
								"baseCustomsDeclaration.customer.name", 100));// 20
						list.add(addColumn("法定数量", "firstAmount", 100));// 21
						list.add(addColumn("法定单位", "legalUnit.name", 100));// 22
						list.add(addColumn("报关单单价", "commUnitPrice", 100));// 23
						list.add(addColumn("合同单价", "contractUnitPrice", 100));// 24

						list.add(addColumn("毛重", "grossWeight", 100));// 25
						list.add(addColumn("净重", "netWeight", 100));// 26
						list.add(addColumn("总毛重",
								"baseCustomsDeclaration.grossWeight", 100));// 27
						list.add(addColumn("总净重",
								"baseCustomsDeclaration.netWeight", 100));// 28
						list.add(addColumn("包装方式",
								"baseCustomsDeclaration.wrapType.name", 50));// 29
						list.add(addColumn("件数",
								"baseCustomsDeclaration.commodityNum", 50));// 30
						list.add(addColumn("总重量", "grossWeight", 100));// 31
						list.add(addColumn("集装箱号",
								"baseCustomsDeclaration.containerNum", 150)); // 32
						return list;
					}
				});

		// 显示小数位,默认2位
		Integer decimalLength = 2;

		tableModel.setAllColumnsFractionCount(decimalLength);
		CommonVars.castMultiplicationValue(jTable, 14, 13, 23, decimalLength);
		CommonVars.castMultiplicationValue(jTable, 27, 25, 13, decimalLength);
		CommonVars.castMultiplicationValue(jTable, 28, 26, 13, decimalLength);
		CompanyOther other = CommonVars.getOther();
		if (other != null) {
			tableModel.setAllColumnsshowThousandthsign(other
					.getIsAutoshowThousandthsign() == null ? false : other
					.getIsAutoshowThousandthsign());
		}
		jTable.getColumnModel().getColumn(17)
				.setCellRenderer(new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						String str = "";
						if (value != null) {
							str = ImpExpType.getImpExpTypeDesc(Integer
									.parseInt(value.toString()));
						}
						this.setText(str);
						return this;
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
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(10, 5, 64, 18));
			jLabel1.setText("手册类型：");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(440, 5, 78, 20));
			jLabel.setText("报关单状态：");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel, null);
			jPanel.add(getRbYes(), null);
			jPanel.add(getRbNo(), null);
			jPanel.add(getRbAll(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(getCbDefault(), null);
			jPanel.add(getRbBcs(), null);
			jPanel.add(getRbDzsc(), null);
			jPanel.add(getRbBcus(), null);
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
			rbYes.setBounds(new Rectangle(526, 6, 64, 20));
			rbYes.setText("\u5df2\u751f\u6548");
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
			rbNo.setBounds(new Rectangle(593, 6, 64, 20));
			rbNo.setText("\u672a\u751f\u6548");
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
			rbAll.setBounds(new Rectangle(661, 6, 59, 20));
			rbAll.setText("\u5168\u90e8");
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
	 * This method initializes buttonGroup
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroupType() {
		if (buttonGroupType == null) {
			buttonGroupType = new ButtonGroup();
			buttonGroupType.add(rbBcs);
			buttonGroupType.add(rbDzsc);
			buttonGroupType.add(rbBcus);
		}
		return buttonGroupType;
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setLeftComponent(getJScrollPane());
			jSplitPane.setRightComponent(getPnRight());
			jSplitPane.setDividerSize(4);
			jSplitPane.setDividerLocation(630);
			jSplitPane.setResizeWeight(0.7);
		}
		return jSplitPane;
	}

	/**
	 * This method initializes pnRight
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnRight() {
		if (pnRight == null) {
			pnRight = new JPanel();
			pnRight.setLayout(new BorderLayout());
			pnRight.add(getJScrollPane1(), BorderLayout.CENTER);
			pnRight.add(getJPanel10(), BorderLayout.NORTH);
		}
		return pnRight;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();

			// jScrollPane1.setViewportView(getcontractList());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes contractList
	 * 
	 * @return com.bestway.bcs.client.contractanalyse.JContractList
	 */
	private JContractList getcontractList() {
		if (contractList == null) {

			// 正在执行
			boolean isExingContract = cbIsExingContract.isSelected();

			// 已核销
			boolean isCavContract = cbIsCavContract.isSelected();

			contractList = new JContractList(isExingContract, isCavContract);

		}
		return contractList;
	}

	/**
	 * This method initializes dzscList
	 * 
	 * @return com.bestway.bcs.client.contractanalyse.JContractList
	 */
	private JDzscEmsPorHeadList getDzscList() {
		if (dzscList == null) {
			dzscList = new JDzscEmsPorHeadList();
		}
		return dzscList;
	}

	/**
	 * This method initializes jPanel10
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel10() {
		if (jPanel10 == null) {
			jPanel10 = new JPanel();
			jPanel10.setLayout(null);
			jPanel10.setPreferredSize(new Dimension(171, 25));
			jPanel10.add(getRbAllYes(), null);
			jPanel10.add(getRbAllNot(), null);

			jPanel10.add(getCbIsExingContract(), null);

			jPanel10.add(getCbIsCavContract(), null);

		}
		return jPanel10;
	}

	private JCheckBox getCbIsExingContract() {

		if (cbIsExingContract == null) {

			cbIsExingContract = new JCheckBox("正在执行");

			cbIsExingContract.setSelected(true);

			cbIsExingContract.setBounds(147, 1, 84, 23);

			cbIsExingContract.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					contractList.showContractData(
							cbIsExingContract.isSelected(),
							cbIsCavContract.isSelected());

				}
			});

		}

		return cbIsExingContract;

	}

	private JCheckBox getCbIsCavContract() {

		if (cbIsCavContract == null) {

			cbIsCavContract = new JCheckBox("已核销");

			cbIsCavContract.setBounds(233, 1, 76, 23);

			cbIsCavContract.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					contractList.showContractData(
							cbIsExingContract.isSelected(),
							cbIsCavContract.isSelected());
				}
			});

		}

		return cbIsCavContract;

	}

	/**
	 * This method initializes rbAllYes
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbAllYes() {
		if (rbAllYes == null) {
			rbAllYes = new JRadioButton();
			rbAllYes.setBounds(new Rectangle(3, 1, 67, 22));
			rbAllYes.setText("\u5168\u9009");
			rbAllYes.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (rbAllYes.isSelected()) {
						if (currentType == 0) {
							for (int i = 0; i < contractList.getModel()
									.getSize(); i++) {
								Contract contract = (Contract) contractList
										.getModel().getElementAt(i);
								contract.setSelected(true);
							}
							contractList.repaint();
						} else {
							for (int i = 0; i < dzscList.getModel().getSize(); i++) {
								DzscEmsPorHead contract = (DzscEmsPorHead) dzscList
										.getModel().getElementAt(i);
								contract.setSelected(true);
							}
							dzscList.repaint();
						}

					}
				}
			});
		}
		return rbAllYes;
	}

	/**
	 * This method initializes rbAllNot
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbAllNot() {
		if (rbAllNot == null) {
			rbAllNot = new JRadioButton();
			rbAllNot.setBounds(new Rectangle(72, 1, 67, 22));
			rbAllNot.setText("\u5168\u5426");
			rbAllNot.setSelected(true);
			rbAllNot.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (rbAllNot.isSelected()) {
						if (currentType == 0) {
							for (int i = 0; i < contractList.getModel()
									.getSize(); i++) {
								Contract contract = (Contract) contractList
										.getModel().getElementAt(i);
								contract.setSelected(false);
							}
							contractList.repaint();
						} else {
							for (int i = 0; i < dzscList.getModel().getSize(); i++) {
								DzscEmsPorHead contract = (DzscEmsPorHead) dzscList
										.getModel().getElementAt(i);
								contract.setSelected(false);
							}
							dzscList.repaint();
						}

					}
				}
			});
		}
		return rbAllNot;
	}

	/**
	 * This method initializes buttonGroup1
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup1() {
		if (buttonGroup1 == null) {
			buttonGroup1 = new ButtonGroup();
			buttonGroup1.add(this.getRbAllNot());
			buttonGroup1.add(this.getRbAllYes());
		}
		return buttonGroup1;
	}

	public void updateUI(int i) {
		if (i == currentType) {
			return;
		} else {
			currentType = i;// 改变当前手册类型
			initUI(currentType);
		}
	}

	/*
	 * 初始化界面
	 */
	public void initUI(int i) {
		List list = new ArrayList();
		initTable(list);
		if (i == 0) {
			pnRight.setEnabled(true);
			jSplitPane.setRightComponent(getPnRight());
			jScrollPane1.setViewportView(getcontractList());
		} else if (i == 1) {
			pnRight.setEnabled(false);
			jSplitPane.setRightComponent(null);
		} else {
			pnRight.setEnabled(true);
			jSplitPane.setRightComponent(getPnRight());
			jScrollPane1.setViewportView(getDzscList());
		}
		initUIComponents();

	}

	/**
	 * This method initializes cbDefault
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbDefault() {
		if (cbDefault == null) {
			cbDefault = new JCheckBox();
			cbDefault.setBounds(new Rectangle(198, 11, 75, 21));
			cbDefault.setText("设为默认");
			cbDefault.setVisible(false);
			cbDefault.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (cbDefault.isSelected()) {
						// 根本就没用
						// DgTransferDetailInfo.defaultType = currentType;
					}
				}
			});
		}
		return cbDefault;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.add(getJToolBar(), BorderLayout.NORTH);
			jPanel1.add(getJPanel2(), BorderLayout.CENTER);
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
			jLabel8 = new JLabel();
			jLabel8.setBounds(new Rectangle(350, 59, 15, 22));
			jLabel8.setText("至");
			jLabel7 = new JLabel();
			jLabel7.setBounds(new Rectangle(101, 59, 72, 22));
			jLabel7.setText("报关单日期");
			jLabel6 = new JLabel();
			jLabel6.setBounds(new Rectangle(335, 35, 64, 22));
			jLabel6.setText("进出口类型");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(101, 35, 55, 22));
			jLabel5.setText("   客 户");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(573, 11, 56, 22));
			jLabel4.setText("商品序号");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(335, 11, 57, 22));
			jLabel3.setText("商品名称");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(101, 11, 55, 22));
			jLabel2.setText("商品编码");
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.setPreferredSize(new Dimension(1, 88));
			jPanel2.add(getBtnQuery(), null);
			jPanel2.add(getBtnPrint(), null);
			jPanel2.add(jLabel2, null);
			jPanel2.add(jLabel3, null);
			jPanel2.add(jLabel4, null);
			jPanel2.add(jLabel5, null);
			jPanel2.add(jLabel6, null);
			jPanel2.add(jLabel7, null);
			jPanel2.add(getCbbHsCode(), null);
			jPanel2.add(getCbbHsName(), null);
			jPanel2.add(getCbbSeqNo(), null);
			jPanel2.add(getCbbCustomer(), null);
			jPanel2.add(getCbbImpExpType(), null);
			jPanel2.add(jLabel8, null);
			jPanel2.add(getCbbBeginDate(), null);
			jPanel2.add(getCbbEndDate(), null);
			jPanel2.setBorder(BorderFactory.createTitledBorder(null, "查询选项",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), Color.blue));
		}
		return jPanel2;
	}

	/**
	 * This method initializes rbBcs
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbBcs() {
		if (rbBcs == null) {
			rbBcs = new JRadioButton();
			rbBcs.setBounds(new Rectangle(78, 5, 91, 21));
			rbBcs.setText("电子化手册");
			rbBcs.setSelected(true);
			rbBcs.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					updateUI(0);
				}
			});
		}
		return rbBcs;
	}

	/**
	 * This method initializes rbDzsc
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbDzsc() {
		if (rbDzsc == null) {
			rbDzsc = new JRadioButton();
			rbDzsc.setBounds(new Rectangle(175, 5, 75, 21));
			rbDzsc.setText("电子帐册");
			rbDzsc.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					updateUI(1);
				}
			});
		}
		return rbDzsc;
	}

	/**
	 * This method initializes rbBcus
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbBcus() {
		if (rbBcus == null) {
			rbBcus = new JRadioButton();
			rbBcus.setBounds(new Rectangle(259, 5, 82, 21));
			rbBcus.setText("电子手册");
			rbBcus.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					updateUI(2);
				}
			});
		}
		return rbBcus;
	}

	/**
	 * This method initializes cbbHsCode
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbHsCode() {
		if (cbbHsCode == null) {
			cbbHsCode = new JComboBox();
			cbbHsCode.setBounds(new Rectangle(160, 11, 151, 22));
		}
		return cbbHsCode;
	}

	/**
	 * This method initializes cbbHsName
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbHsName() {
		if (cbbHsName == null) {
			cbbHsName = new JComboBox();
			cbbHsName.setBounds(new Rectangle(397, 11, 154, 22));
		}
		return cbbHsName;
	}

	/**
	 * This method initializes cbbSeqNo
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbSeqNo() {
		if (cbbSeqNo == null) {
			cbbSeqNo = new JComboBox();
			cbbSeqNo.setBounds(new Rectangle(632, 11, 67, 22));
		}
		return cbbSeqNo;
	}

	/**
	 * This method initializes cbbCustomer
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCustomer() {
		if (cbbCustomer == null) {
			cbbCustomer = new JComboBox();
			cbbCustomer.setBounds(new Rectangle(160, 35, 151, 22));
		}
		return cbbCustomer;
	}

	/**
	 * This method initializes cbbImpExpType
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbImpExpType() {
		if (cbbImpExpType == null) {
			cbbImpExpType = new JComboBox();
			cbbImpExpType.setBounds(new Rectangle(397, 35, 154, 22));
			cbbImpExpType.setEnabled(false);
		}
		return cbbImpExpType;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setDate(null);
			cbbBeginDate.setBounds(new Rectangle(199, 59, 111, 22));
		}
		return cbbBeginDate;
	}

	/**
	 * This method initializes jCalendarComboBox1
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbEndDate() {
		if (cbbEndDate == null) {
			cbbEndDate = new JCalendarComboBox();
			cbbEndDate.setBounds(new Rectangle(397, 59, 99, 22));
		}
		return cbbEndDate;
	}

	/**
	 * 初始化窗口上 控件的值
	 * 
	 */
	private void initUIComponents() {
		// 商品
		List<TempCustomsDeclarCommInfo> dlist = new ArrayList<TempCustomsDeclarCommInfo>();

		// 查询转厂报关的商品(100原是报关单状态，现在不考虑报关单状态，所以填-1)
		dlist = casCheckAction.findCustomsDeclarationCommInfoDistance(
				new Request(CommonVars.getCurrUser()), isImport,
				new ArrayList(), -1, currentType);

		System.out.println("wss initUI list.size: " + dlist.size());

		Map map = new HashMap();

		Map mapName = new HashMap();

		Map mapCode = new HashMap();

		Map mapSeqNo = new HashMap();

		for (TempCustomsDeclarCommInfo data : dlist) {

			if (map.get(data.getCode() + data.getName()) == null) {

				map.put(data.getCode() + data.getName(), data);
			}

			if (mapName.get(data.getName()) == null) {
				mapName.put(data.getName(), data);
			}
			if (mapCode.get(data.getCode()) == null) {
				mapCode.put(data.getCode(), data);
			}
			if (mapSeqNo.get(data.getSeqNum()) == null) {
				mapSeqNo.put(data.getSeqNum(), data);
			}
		}

		List resultList = new ArrayList(map.values());
		List listName = new ArrayList(mapName.values());
		List listCode = new ArrayList(mapCode.values());
		List listSeqNo = new ArrayList(mapSeqNo.values());

		// //商品下拉框
		// cbbCommInfo.setModel(new DefaultComboBoxModel(resultList.toArray()));
		// cbbCommInfo.setRenderer(CustomBaseRender.getInstance().getRender(
		// "code", "name", 100, 160));
		// CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbCommInfo,
		// "code", "name", 260);
		// cbbCommInfo.setSelectedIndex(-1);

		// 名称下拉框
		cbbHsName.setModel(new DefaultComboBoxModel(listName.toArray()));
		cbbHsName.setRenderer(CustomBaseRender.getInstance().getRender("name",
				"name", 0, 160));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbHsName, "name", "name");
		cbbHsName.setSelectedIndex(-1);

		// 编码下拉框
		cbbHsCode.setModel(new DefaultComboBoxModel(listCode.toArray()));
		cbbHsCode.setRenderer(CustomBaseRender.getInstance().getRender("code",
				"code", 100, 0));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbHsCode, "code", "code");
		cbbHsCode.setSelectedIndex(-1);

		// 序号下拉框
		cbbSeqNo.setModel(new DefaultComboBoxModel(listSeqNo.toArray()));
		cbbSeqNo.setRenderer(CustomBaseRender.getInstance().getRender("seqNum",
				"seqNum", 100, 0));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(this.cbbSeqNo,
				"seqNum", "seqNum");
		cbbSeqNo.setSelectedIndex(-1);

		// 初始化进出口类型
		this.cbbImpExpType.removeAllItems();
		this.cbbImpExpType.addItem(isImport == true ? new ItemProperty(String
				.valueOf(ImpExpType.TRANSFER_FACTORY_IMPORT), "料件转厂")
				: new ItemProperty(String
						.valueOf(ImpExpType.TRANSFER_FACTORY_EXPORT), "转厂出口"));

		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbImpExpType);
		this.cbbImpExpType.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		// 客户
		List list = null;

		// 查询客户(100原是报关单状态，现在不考虑报关单状态，所以填-1)
		list = casCheckAction.findCustomsDeclarationCustomer(new Request(
				CommonVars.getCurrUser()), isImport, new ArrayList(), -1,
				currentType);

		System.out.println("wss initUI customers list.size :" + list.size());

		if (list.size() > 0 && list != null) {
			this.cbbCustomer.setModel(new DefaultComboBoxModel(list.toArray()));
			this.cbbCustomer.setRenderer(CustomBaseRender.getInstance()
					.getRender("code", "name", 100, 100));
			CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
					this.cbbCustomer, "code", "name");
			this.cbbCustomer.setSelectedIndex(-1);
		}

	}

	/**
	 * 数据查询
	 */
	private void queryData() {
		// 如果是电子化手册，电子手册
		if (currentType == 0 || currentType == 2) {
			if (currentType == 0) {
				clist = contractList.getSelectedContracts();
			} else {
				clist = dzscList.getSelectedContracts();
			}
			if (clist.size() == 0) {
				JOptionPane.showMessageDialog(DgTransferDetailInfo.this,
						"请选择合同!", "提示!", JOptionPane.WARNING_MESSAGE);
				return;
			}
		}

		new MyFindThread().start();

	}
} // @jve:decl-index=0:visual-constraint="10,10"
