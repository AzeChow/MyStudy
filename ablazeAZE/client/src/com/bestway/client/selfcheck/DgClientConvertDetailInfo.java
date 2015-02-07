package com.bestway.client.selfcheck;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.border.TitledBorder;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcus.cas.action.CasCheckAction;
import com.bestway.bcus.cas.entity.TempObject;
import com.bestway.bcus.cas.invoice.entity.ConditionBalance;
import com.bestway.bcus.cas.invoice.entity.ProductBOMtoMateriel;
import com.bestway.bcus.client.cas.CasQuery;
import com.bestway.bcus.client.common.CheckBoxListItem;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomReportDataSourceNew;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.client.common.SteppedMetalComboBoxUI;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Condition;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.constant.DzscState;
import com.bestway.dzsc.dzscmanage.action.DzscAction;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

public class DgClientConvertDetailInfo extends JDialogBase {

	private JPanel jContentPane = null;
	private JScrollPane jScrollPane = null;
	private JTable jTable = null;
	private JTableListModel tableModel = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JLabel jLabel3 = null;
	private JLabel jLabel4 = null;
	private JLabel jLabel5 = null;
	private JLabel jLabel6 = null;
	private JTextField tfMaterielName = null;
	private JCalendarComboBox cbbDate = null;
	private JTextField tfProductSpec = null;
	private JTextField tfMaterielSpec = null;
	private JButton btnQuery = null;
	private JButton btnPrint = null;
	private JButton btnClose = null;
	private JSplitPane jSplitPane = null;
	private JPanel pnTop = null;
	private JTextField tfProductName = null;
	private JButton btProductName = null;
	private JButton btMaterialName = null;
	private JButton btProductSpec = null;
	private JButton btMaterialSpec = null;
	private ButtonGroup contractTypeGroup = null;  //  @jve:decl-index=0:
	private JPanel jPanel = null;
	private CasCheckAction casCheckAction = null;
	private List list = null;
	private int contractType = 0;
	private JComboBox cbbContract = null;
	private ContractAction contractAction = null; // @jve:decl-index=0:
	private DzscAction dzscAction = null;
	private ManualDeclareAction manualDeclearAction = null;
	private JComboBox cbbContractType = null;
	private JButton btnRelation = null;
	
	private JPopupMenu pmRelation = null; 
	private JMenuItem miImpExpDetail = null;
	//关联查询时传递 的参数
	private Date sDate;
	private String sHsName;
	private String sHsSpec;

	public int getContractType() {
		return contractType;
	}

	public void setContractType(int contractType) {
		this.contractType = contractType;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgClientConvertDetailInfo() {
		super();
		casCheckAction = (CasCheckAction) CommonVars.getApplicationContext()
				.getBean("casCheckAction");
		dzscAction = (DzscAction) CommonVars.getApplicationContext().getBean(
				"dzscAction");
		contractAction = (ContractAction) CommonVars.getApplicationContext()
				.getBean("contractAction");
		manualDeclearAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		initialize();
		initUIComponents();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(787, 609));
		this.setContentPane(getJContentPane());
		this.setTitle("结转折料明细表");
		cbbContractType.addItem("电子化手册");
		cbbContractType.addItem("电子手册");
		cbbContractType.addItem("电子帐册");
		initTable(new Vector()); // 初始化表格
	}

	private void initUIComponents() {
		fillContract(contractType);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJSplitPane(), BorderLayout.CENTER);
		}
		return jContentPane;
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
	 * 初始化表格
	 */
	private JTableListModel initTable(List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("客户名称", "customerName", 120));
						list.add(addColumn("成品名称", "productName", 100));
						list.add(addColumn("成品规格", "productSpec", 100));
						list.add(addColumn("成品单位", "productUnitName", 60));
						list.add(addColumn("转厂差额数量", "productAmount", 60));
						list.add(addColumn("料件名称", "materialName", 120));
						list.add(addColumn("料件规格", "materialSpec", 100));
						list.add(addColumn("料件单位", "materialUnitName", 60));
						list.add(addColumn("单耗", "unitWaste", 40));
						list.add(addColumn("损耗", "waste", 40));
						list.add(addColumn("单项用量", "unitDosage", 60));
						list.add(addColumn("耗用数量", "unitAmout", 80));
						list.add(addColumn("合同号", "contractNo", 100));
						return list;
					}
				});
		return tableModel;
	}

	/**
	 * This method initializes tfMaterielName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfMaterielName() {
		if (tfMaterielName == null) {
			tfMaterielName = new JTextField();
			tfMaterielName.setLocation(new Point(213, 43));
			tfMaterielName.setSize(new Dimension(138, 25));
		}
		return tfMaterielName;
	}

	/**
	 * This method initializes cbbDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbDate() {
		if (cbbDate == null) {
			cbbDate = new JCalendarComboBox();
			// cbbBeginDate.setDate(findBeginData2());
			cbbDate.setSize(new Dimension(158, 25));
			cbbDate.setLocation(new Point(452, 13));
		}
		return cbbDate;
	}

	/**
	 * This method initializes tfProductSpec
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfProductSpec() {
		if (tfProductSpec == null) {
			tfProductSpec = new JTextField();
			tfProductSpec.setLocation(new Point(452, 73));
			tfProductSpec.setSize(new Dimension(138, 25));
		}
		return tfProductSpec;
	}

	/**
	 * This method initializes tfMaterielSpec
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfMaterielSpec() {
		if (tfMaterielSpec == null) {
			tfMaterielSpec = new JTextField();
			tfMaterielSpec.setLocation(new Point(452, 45));
			tfMaterielSpec.setSize(new Dimension(138, 25));
		}
		return tfMaterielSpec;
	}

	/**
	 * This method initializes btnQuery
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnQuery() {
		if (btnQuery == null) {
			btnQuery = new JButton();
			btnQuery.setText("查询");
			btnQuery.setSize(new Dimension(65, 25));
			btnQuery.setMnemonic(KeyEvent.VK_UNDEFINED);
			btnQuery.setLocation(new Point(704, 16));
			btnQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (null==cbbContract.getSelectedItem()) {
						if (JOptionPane.showConfirmDialog(DgClientConvertDetailInfo.this,
								"请选择折料合同！", "确认",
								JOptionPane.CLOSED_OPTION, JOptionPane.WARNING_MESSAGE) != JOptionPane.YES_OPTION) {
							return;
						}
						return;
					}
					
					queryData();
				}
			});
		}
		return btnQuery;
	}

	
	/**
	 * 查询
	 */
	public void queryData(){
		List<Condition> conditionsMaterial = null;
		List<Condition> conditionsContract = null;
		ConditionBalance conditionsProduct = new ConditionBalance();
		
		conditionsProduct.setM(false);
		conditionsProduct.setCustomerCode("");
		conditionsProduct.setCustomerName("");
		conditionsProduct
				.setDate(cbbDate.getDate() == null ? new Date()
						: cbbDate.getDate());
		conditionsProduct.setName(tfProductName.getText().trim());
		conditionsProduct.setSpec(tfProductSpec.getText().trim());
		conditionsMaterial = getConditionsMaterial();
		conditionsContract = getConditionsContract();
		// 执行查询线程
		new Find(conditionsProduct, conditionsMaterial,
				conditionsContract).execute();

	}
	protected List<Condition> getConditionsContract() {
		List<Condition> conditions = new ArrayList<Condition>();

		// 折料合同
		CheckBoxListItem item = (CheckBoxListItem) this.cbbContract
		.getSelectedItem();
//		for (int j = 0; j < this.cbbContract.getModel().getSize(); j++) {
//			CheckBoxListItem item = (CheckBoxListItem) this.cbbContract
//					.getModel().getElementAt(j);
//			if (item.getIsSelected()) {
//				checkableItemList.add(item);
//			}
//		}
		if(item!=null){
			Condition condition=null;
			if (contractType == 0) {
					condition = new Condition("and", null, "emsNo", "=",
							item.getCode().trim(), null);
				conditions.add(condition);
			}
		}
		
		return conditions;
	}

	class Find extends SwingWorker {
		// 成品查询条件
		ConditionBalance conditionsProduct = null;
		// 料件查询
		List<Condition> conditionsMaterial = null;
		// 合同号
		List<Condition> conditionsContract = null;

		public Find(ConditionBalance conditionsProduct,
				List<Condition> conditionsMaterial,
				List<Condition> conditionsContract) {
			this.conditionsProduct = conditionsProduct;
			this.conditionsMaterial = conditionsMaterial;
			this.conditionsContract = conditionsContract;

		}

		@Override
		protected Object doInBackground() throws Exception {// 后台计算
			// 查询返回结果
			List list = null;
			// 查询
			CommonProgress.showProgressDialog(DgClientConvertDetailInfo.this);
			CommonProgress.setMessage("系统正在执行查询，请稍后...");
			list = casCheckAction.getCurrentBalanceBOMInfo(new Request(
					CommonVars.getCurrUser()), conditionsProduct,
					conditionsMaterial, conditionsContract, contractType);
			return list;
		}

		@Override
		protected void done() {// 更新视图
			list = null;
			try {
				list = (List) this.get();
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (list == null) {
				list = new ArrayList();
			}
			CommonProgress.closeProgressDialog();
			initTable(list);
		}
	}

	protected List<Condition> getConditionsMaterial() {
		List<Condition> conditions = new ArrayList<Condition>();
		// 料件名称
		if (!tfMaterielName.getText().trim().equals("")) {
			conditions.add(new Condition("and", null, "name", "=",
					tfMaterielName.getText(), null));
		}
		// 料件规格
		if (!tfMaterielSpec.getText().trim().equals("")) {
			conditions.add(new Condition("and", null, "spec", "=",
					tfMaterielSpec.getText(), null));
		}
		return conditions;
	}

	protected List<Condition> getConditionsProduct() {
		List<Condition> conditions = new ArrayList<Condition>();
		// 成品名称
		if (!tfProductName.getText().trim().equals("")) {
			conditions.add(new Condition("and", null, "ptName", "=",
					tfProductName.getText(), null));
		}
		// 成品规格
		if (!tfProductSpec.getText().trim().equals("")) {
			conditions.add(new Condition("and", null, "ptSpec", "=",
					tfProductSpec.getText(), null));
		}
		// 日期
		if (cbbDate.getDate() != null) {
			Date date = cbbDate.getDate();
			conditions.add(new Condition("and", null, "billMaster.validDate",
					">=", CommonVars.getBeginDate(date), null));
		}
		return conditions;
	}
	/**
	 * This method initializes btnPrint
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton();
			btnPrint.setText("打印");
			btnPrint.setSize(new Dimension(65, 25));
			btnPrint.setLocation(new Point(704, 46));
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				try {
					List list = tableModel.getList();
					CustomReportDataSourceNew ds = new CustomReportDataSourceNew(
							list);
					InputStream masterReportStream = DgMaterialThatDayBalance.class
							.getResourceAsStream("report/ConverDetail.jasper");
					Map<String, Object> parameters = new HashMap<String, Object>();
					parameters.put("title", getTitle());
					parameters.put("date", cbbDate.getDate());
					JasperPrint jasperPrint = JasperFillManager
							.fillReport(masterReportStream,
									parameters, ds);
					DgReportViewer viewer = new DgReportViewer(
							jasperPrint);
					viewer.setVisible(true);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
});
		}
		return btnPrint;
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
			btnClose.setSize(new Dimension(65,25));
			btnClose.setLocation(new Point(704, 75));
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnClose;
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setDividerSize(1);
			jSplitPane.setBottomComponent(getJScrollPane());
			jSplitPane.setTopComponent(getPnTop());
			jSplitPane.setDividerLocation(110);

		}
		return jSplitPane;
	}

	/**
	 * This method initializes pnTop
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnTop() {
		if (pnTop == null) {
			pnTop = new JPanel();
			pnTop.setLayout(null);
			pnTop.setBorder(BorderFactory.createTitledBorder(null, "折料选项",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), Color.blue));
			jLabel6 = new JLabel();
			jLabel6.setText("折料合同");
			jLabel6.setLocation(new Point(150, 13));
			jLabel6.setSize(new Dimension(62, 25));
			jLabel5 = new JLabel();
			jLabel5.setText("料件规格");
			jLabel5.setLocation(new Point(387, 43));
			jLabel5.setSize(new Dimension(62, 25));
			jLabel4 = new JLabel();
			jLabel4.setText("成品规格");
			jLabel4.setLocation(new Point(387, 73));
			jLabel4.setSize(new Dimension(62, 25));
			jLabel3 = new JLabel();
			jLabel3.setText("报表日期");
			jLabel3.setLocation(new Point(387, 13));
			jLabel3.setSize(new Dimension(62, 25));
			jLabel1 = new JLabel();
			jLabel1.setText("料件名称");
			jLabel1.setLocation(new Point(150, 43));
			jLabel1.setSize(new Dimension(62, 25));
			jLabel = new JLabel();
			jLabel.setText("成品名称");
			jLabel.setLocation(new Point(150, 73));
			jLabel.setSize(new Dimension(62, 25));
			pnTop.add(jLabel, null);
			pnTop.add(jLabel1, null);
			pnTop.add(getTfMaterielName(), null);
			pnTop.add(jLabel3, null);
			pnTop.add(getCbbDate(), null);
			pnTop.add(jLabel6, null);
			pnTop.add(jLabel5, null);
			pnTop.add(jLabel4, null);
			pnTop.add(getTfProductSpec(), null);
			pnTop.add(getTfMaterielSpec(), null);
			pnTop.add(getBtnClose(), null);
			pnTop.add(getBtnPrint(), null);
			pnTop.add(getBtnQuery(), null);
			pnTop.add(getTfProductName(), null);
			pnTop.add(getBtProductName(), null);
			pnTop.add(getBtMaterialName(), null);
			pnTop.add(getBtProductSpec(), null);
			pnTop.add(getBtMaterialSpec(), null);
			pnTop.add(getJPanel(), null);
			pnTop.add(getCbbContract(), null);
			pnTop.add(getBtnRelation(), null);
		}
		return pnTop;
	}

	/**
	 * This method initializes tfProductName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfProductName() {
		if (tfProductName == null) {
			tfProductName = new JTextField();
			tfProductName.setLocation(new Point(213, 73));
			tfProductName.setSize(new Dimension(138, 25));
		}
		return tfProductName;
	}

	/**
	 * This method initializes btProductName
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtProductName() {
		if (btProductName == null) {
			btProductName = new JButton();
			btProductName.setText("...");
			btProductName.setSize(new Dimension(18, 25));
			btProductName.setLocation(new Point(353, 73));
			btProductName
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (null==cbbContract.getSelectedItem()) {
								if (JOptionPane.showConfirmDialog(DgClientConvertDetailInfo.this,
										"请选择折料合同！", "确认",
										JOptionPane.CLOSED_OPTION, JOptionPane.WARNING_MESSAGE) != JOptionPane.YES_OPTION) {
									return;
								}
								return;
							}String[] contracts=getSelectContractsString();
							
							Object object = CasQuery.getInstance()
									.findMertailOrFinishedProductHs(MaterielType.FINISHED_PRODUCT,contractType,contracts);
							if (object != null) {
								tfProductName
										.setText((String) ((TempObject) object)
												.getObject());
								tfProductSpec
										.setText((String) ((TempObject) object)
												.getObject1());
							}
						}
					});

		}
		return btProductName;
	}

	/**
	 * This method initializes btMaterialName
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtMaterialName() {
		if (btMaterialName == null) {
			btMaterialName = new JButton();
			btMaterialName.setText("...");
			btMaterialName.setSize(new Dimension(18, 25));
			btMaterialName.setLocation(new Point(353, 43));
			btMaterialName
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (null==cbbContract.getSelectedItem()) {
								if (JOptionPane.showConfirmDialog(DgClientConvertDetailInfo.this,
										"请选择折料合同！", "确认",
										JOptionPane.CLOSED_OPTION, JOptionPane.WARNING_MESSAGE) != JOptionPane.YES_OPTION) {
									return;
								}
								return;
							}
							String[] contracts=getSelectContractsString();
							
							Object object = CasQuery.getInstance()
									.findMertailOrFinishedProductHs(MaterielType.MATERIEL,contractType,contracts);
							if (object != null) {
								tfMaterielName
										.setText((String) ((TempObject) object)
												.getObject());
								tfMaterielSpec
										.setText((String) ((TempObject) object)
												.getObject1());
							}
						}
					});
		}
		return btMaterialName;
	}

	protected String[] getSelectContractsString() {
		List<CheckBoxListItem> checkableItemList = new ArrayList<CheckBoxListItem>();
		for (int j = 0; j < this.cbbContract.getModel().getSize(); j++) {
			CheckBoxListItem item = (CheckBoxListItem) this.cbbContract
					.getModel().getElementAt(j);
			if (item.getIsSelected()) {
				checkableItemList.add(item);
			}
		}
		String[] contracts=new String[checkableItemList.size()];
		for (int j = 0; j < checkableItemList.size(); j++) {
			CheckBoxListItem item = checkableItemList.get(j);
			contracts[j]=item.getCode().trim();
		}
		return contracts;
	}

	/**
	 * This method initializes btProductSpec
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtProductSpec() {
		if (btProductSpec == null) {
			btProductSpec = new JButton();
			btProductSpec.setText("...");
			btProductSpec.setSize(new Dimension(18, 25));
			btProductSpec.setLocation(new Point(592, 73));
			btProductSpec
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (null==cbbContract.getSelectedItem()) {
								if (JOptionPane.showConfirmDialog(DgClientConvertDetailInfo.this,
										"请选择折料合同！", "确认",
										JOptionPane.CLOSED_OPTION, JOptionPane.WARNING_MESSAGE) != JOptionPane.YES_OPTION) {
									return;
								}
								return;
							}
							Object object = CasQuery.getInstance()
							.findMertailOrFinishedProductSpecHs(
									MaterielType.FINISHED_PRODUCT,
									tfProductName.getText());
					if (object != null) {
						tfProductSpec
								.setText((String) ((TempObject) object)
										.getObject());
					}
				}
			});
		}
		return btProductSpec;
	}

	/**
	 * This method initializes btMaterialSpec
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtMaterialSpec() {
		if (btMaterialSpec == null) {
			btMaterialSpec = new JButton();
			btMaterialSpec.setText("...");
			btMaterialSpec.setSize(new Dimension(18, 25));
			btMaterialSpec.setLocation(new Point(592, 45));
			btMaterialSpec
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (null==cbbContract.getSelectedItem()) {
								if (JOptionPane.showConfirmDialog(DgClientConvertDetailInfo.this,
										"请选择折料合同！", "确认",
										JOptionPane.CLOSED_OPTION, JOptionPane.WARNING_MESSAGE) != JOptionPane.YES_OPTION) {
									return;
								}
								return;
							}
							Object object = CasQuery.getInstance()
									.findMertailOrFinishedProductSpecHs(
											MaterielType.MATERIEL,
											tfMaterielName.getText());
							if (object != null) {
								tfMaterielSpec.setText((String) ((TempObject) object)
												.getObject());
							}
						}
					});
		}
		return btMaterialSpec;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			GridLayout gridLayout = new GridLayout();
			gridLayout.setRows(1);
			jPanel = new JPanel();
			jPanel.setBorder(BorderFactory.createTitledBorder(null,
					"\u624b\u518c\u7c7b\u578b",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.PLAIN, 12), SystemColor.activeCaption));
			jPanel.setSize(new Dimension(134, 54));
			jPanel.setLocation(new Point(8, 25));
			jPanel.setLayout(gridLayout);
			jPanel.setFont(new Font("Dialog", Font.PLAIN, 12));
			jPanel.add(getCbbContractType(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes cbbContract
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbContract() {
		if (cbbContract == null) {
			cbbContract = new JComboBox();
			cbbContract.setLocation(new Point(213, 13));
			cbbContract.setSize(new Dimension(158, 25));
		}
		return cbbContract;
	}

	private void fillContract(int Type) {
		//Type==0电子化手册
		//Type==1电子手册
		//Type==2电子帐册
		cbbContract.removeAllItems();
		List contracts = null;
		if (Type == 0) {
			contracts = this.contractAction
					.findContractByProcessExe(new Request(CommonVars
							.getCurrUser(), true));
			for (int i = 0; i < contracts.size(); i++) {
				Contract contract = (Contract) contracts.get(i);
				CheckBoxListItem item = new CheckBoxListItem();
				item.setCode(contract.getEmsNo());
				item.setName(contract.getEmsNo());
				cbbContract.addItem(item);
			}
		} else if (Type == 1) {
			contracts = dzscAction.findDzscEmsPorHead(new Request(CommonVars
					.getCurrUser(), true), DzscState.EXECUTE);
			for (int i = 0; i < contracts.size(); i++) {
				DzscEmsPorHead contractHead = (DzscEmsPorHead) contracts.get(i);
				CheckBoxListItem item = new CheckBoxListItem();
				item.setCode(contractHead.getEmsNo());
				item.setName(contractHead.getEmsNo());
				cbbContract.addItem(item);
			}
		} else {
			// TODO..
			contracts = manualDeclearAction.findEmsHeadH2k(new Request(
					CommonVars.getCurrUser()));
			System.out.println(contracts.size());
			for (int i = 0; i < contracts.size() - 1; i++) {
				EmsHeadH2k contractHead = (EmsHeadH2k) contracts.get(i);
				CheckBoxListItem item = new CheckBoxListItem();
				item.setCode(contractHead.getEmsNo());
				item.setName(contractHead.getEmsNo());
				cbbContract.addItem(item);

			}
		}

		this.cbbContract.setSelectedItem(null);
//		this.cbbContract.setRenderer(new CheckBoxListCellRenderer(cbbContract));
		this.cbbContract.setUI(new SteppedMetalComboBoxUI(400));
//		this.setCmbBillTypeEvent(cbbContract);
		
	}

	/**
	 * 设置怎么样编辑JComboBox 上的 JList 和 JTextField
	 * 
	 * @param cbb
	 */
//	private void setCmbBillTypeEvent(final JComboBox cbb) {
//		final JList listBox;
//		try {
//			Field field = JComponent.class.getDeclaredField("ui");
//			field.setAccessible(true);
//			BasicComboBoxUI ui = (BasicComboBoxUI) field.get(cbb);
//			field = BasicComboBoxUI.class.getDeclaredField("listBox");
//			field.setAccessible(true);
//			listBox = (JList) field.get(ui);
//		} catch (NoSuchFieldException nsfe) {
//			throw new RuntimeException(nsfe);
//		} catch (IllegalAccessException iae) {
//			throw new RuntimeException(iae);
//		}
//		if (listBox == null) {
//			return;
//		}
//
//		listBox.setBackground(Color.white);
//		listBox.setFixedCellHeight(20);
//
//		MouseListener[] mouseListener = listBox.getMouseListeners();
//		for (int i = 0; i < mouseListener.length; i++) {
//			listBox.removeMouseListener(mouseListener[i]);
//		}
//
//		listBox.addMouseListener(new MouseAdapter() {
//			public void mouseClicked(MouseEvent e) {
//				int index = listBox.locationToIndex(e.getPoint());
//				if (index == -1) {
//					return;
//				}
//				CheckBoxListItem item = (CheckBoxListItem) listBox.getModel()
//						.getElementAt(index);
//				item.setIsSelected(!item.getIsSelected());
//				Rectangle rect = listBox.getCellBounds(index, index);
//				listBox.repaint(rect);
//				ComboBoxEditor com = cbb.getEditor();
//				JTextField tf = (JTextField) com.getEditorComponent();
//				if (tf != null) {
//					if (item.getIsSelected()) {
//						tf.setText("".equals(tf.getText()) ? item.getName()
//								: tf.getText() + "," + item.getName());
//					} else {
//						String[] strs = tf.getText().split(",");
//						String str = "";
//						for (int i = 0; i < strs.length; i++) {
//							if (item.getName().equalsIgnoreCase(strs[i])) {
//								continue;
//							}
//							if ("".equals(str)) {
//								str += strs[i];
//							} else {
//								str += "," + strs[i];
//							}
//						}
//						tf.setText(str);
//					}
//				}
//			}
//		});
//
//		//
//		// 当焦点丢失的时候
//		//
//		ComboBoxEditor com = cbb.getEditor();
//		final JTextField tf = (JTextField) com.getEditorComponent();
//		tf.addFocusListener(new FocusAdapter() {
//			public void focusLost(FocusEvent e) {
//
//				String[] strs = tf.getText().split(",");
//				Map<String, CheckBoxListItem> checkBoxListItemMap = new HashMap<String, CheckBoxListItem>();
//
//				int size = listBox.getModel().getSize();
//				for (int i = 0; i < size; i++) {
//					CheckBoxListItem item = (CheckBoxListItem) listBox
//							.getModel().getElementAt(i);
//					checkBoxListItemMap.put(item.getName(), item);
//				}
//				//
//				// 根据输入的字符串选中JList中的列表
//				//
//				String tempText = "";
//				// System.out.println("strs.length =="+strs.length);
//				for (String str : strs) {
//					// System.out.println(str);
//					CheckBoxListItem item = checkBoxListItemMap.get(str);
//					if (item != null) {
//						item.setIsSelected(true);
//						if ("".equals(tempText)) {
//							tempText += item.getName();
//						} else {
//							tempText += "," + item.getName();
//						}
//						checkBoxListItemMap.remove(str);
//					}
//				}
//				//
//				// 清除未选中的记录
//				//
//				Iterator<CheckBoxListItem> iterator = checkBoxListItemMap
//						.values().iterator();
//				for (; iterator.hasNext();) {
//					CheckBoxListItem checkBoxListItem = iterator.next();
//					checkBoxListItem.setIsSelected(false);
//				}
//				//
//				// 重新设置其显示文本值
//				//
//				tf.setText(tempText);
//			}
//		});
//
//	}

	/**
	 * This method initializes cbbContractType	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbContractType() {
		if (cbbContractType == null) {
			cbbContractType = new JComboBox();
			cbbContractType.setLocation(new Point(33, 48));
			cbbContractType.setSize(new Dimension(120, 20));
			cbbContractType.addActionListener(new java.awt.event.ActionListener

() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(cbbContractType.getSelectedIndex()==0){
						System.out.println("0");
						setContractType(0);
						fillContract(contractType);
					}else if(cbbContractType.getSelectedIndex()==1){
						setContractType(1);
						fillContract(contractType);
						System.out.println("1");
					}else{
						setContractType(2);
						fillContract(contractType);
						System.out.println("2");
					}
				}
			});
		}
		return cbbContractType;
	}
	
	
	/**
	 * 关联查询时的参数注入
	 * @param startDate
	 * @param endDate
	 * @param hsCode
	 * @param hsName
	 * @param hsSpec
	 * @param ptNo
	 * @param ptName
	 * @param ptSpec
	 * @author wss
	 */
	public void setQueryCondition(Date startDate,Date endDate,String hsCode,
			String hsName,String hsSpec,String ptNo,String ptName,String ptSpec){
		//如果结束日期不为空
		if(endDate != null){
			this.cbbDate.setDate(endDate);	
		}

		this.tfMaterielName.setText(hsName);
		this.tfMaterielSpec.setText(hsSpec);

	}

	/**
	 * This method initializes btnRelation	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnRelation() {
		if (btnRelation == null) {
			btnRelation = new JButton();
			btnRelation.setBounds(new Rectangle(613, 46, 85, 25));
			btnRelation.setText("关联报表");
			btnRelation.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
				
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(
								DgClientConvertDetailInfo.this,
								"请选择你要查看的资料", "确认", 1);
						return;
					}
					ProductBOMtoMateriel b = (ProductBOMtoMateriel)tableModel.getCurrentRow();
					sDate = cbbDate.getDate();
					sHsName = b.getProductName();
					sHsSpec = b.getProductSpec();
					
					Component comp = (Component) e.getSource();
					getPmRelation().show(comp, 0, comp.getHeight());
				}
			});
		}
		return btnRelation;
	}private JPopupMenu getPmRelation() {
		if (pmRelation == null) {
			pmRelation = new JPopupMenu();
			//结转差额明细表
			pmRelation.add(getMiImpExpDetail());//进出仓帐ok2010.06.28
			
			
		
			
						
		}
		return pmRelation;
	}
	/**
	 * This method initializes miImpExpDetail	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMiImpExpDetail() {
		if (miImpExpDetail == null) {
			miImpExpDetail = new JMenuItem();
			miImpExpDetail.setText("结转差额明细表");
			miImpExpDetail.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
					
					DgSupplierBalanceDetailInfo dialog =  new DgSupplierBalanceDetailInfo();

					dialog.setIsM(false);
					dialog.setQueryCondition( sDate, sHsName, sHsSpec);
					
					dialog.queryData();
					
					
					
					dialog.setVisible(true);
					
					dialog.setLocation(200, 200);
					
				}
			});
		}
		return miImpExpDetail;
	}
	
} // @jve:decl-index=0:visual-constraint="10,10"
