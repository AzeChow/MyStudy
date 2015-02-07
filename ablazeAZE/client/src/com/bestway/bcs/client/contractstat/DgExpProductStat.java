/*
 * Created on 2005-5-20
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.client.contractstat;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import com.bestway.bcs.client.contractanalyse.JContractList;
import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcs.contract.entity.BcsParameterSet;
import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contractstat.action.ContractStatAction;
import com.bestway.bcs.contractstat.entity.ExpProductStat;
import com.bestway.bcs.contractstat.entity.ExpProductStatResult;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.constant.CustomsDeclarationState;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import javax.swing.JCheckBox;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import javax.swing.SwingConstants;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
/**
 * 各栏位计算公式 
 * 合同定量=合同中对应成品备案的数量 
 * 总出口量=直接出口总量＋转厂出口总量-退厂返工总量+返工复出总量
 * 本期总出口量=本期直接出口量＋本期深加工结转量-本期退厂返工量+本期返工复出量 
 * 本期直接出口量=出口报关单中出口类型为直接出口的数量
 * 本期深加工结转量=出口报关单中出口类型为转厂出口的数量 
 * 本期退厂返工量=出口报关单中出口类型为退厂返工的数量
 * 本期返工复出量=出口报关单中出口类型为返工复出的数量 
 * 可出口量＝合同定量-本期总出口量
 * 加工费单价=合同中的加工费单价
 * 加工费总价=本期总出口量*加工费单价 
 * 关封余量=关封管理明细中的进货本厂数量—转厂出口量
 * 可直接出口量=可出口量-关封余量
 * 未转报关单数量＝申请单中的出口口送货单转报关单的数量（风岗嘉辉嘉安进出货单据转报关单）
 * 
 * 
 */
public class DgExpProductStat extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;

	private JTabbedPane jTabbedPane = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JToolBar jToolBar = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private JButton btnCancel = null;

	private JButton btnQuery = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	private JTextField tfContractExpTotalMoney = null;

	private JTextField tfExpTotalMoney = null;

	private JTextField tfExpScale = null;

	private JTableListModel tableModel = null;

	private ContractStatAction contractStatAction = null;

	private JPanel jPanel2 = null;

	private JLabel jLabel5 = null;

	private JCalendarComboBox cbbBeginDate = null;

	private JLabel jLabel6 = null;

	private JCalendarComboBox cbbEndDate = null;

	private JRadioButton rbYes = null;

	private JRadioButton rbNo = null;

	private JRadioButton rbAll = null;

	private ButtonGroup buttonGroup = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel11 = null;

	private JScrollPane jScrollPane1 = null;

	private JContractList jList11 = null;

	private JPanel jPanel10 = null;

	private JRadioButton jRadioButton9 = null;

	private JRadioButton jRadioButton10 = null;

	private ButtonGroup buttonGroup1 = null;

	private JButton btnPrint = null;

	private ContractAction contractAction = null;

	private BcsParameterSet parameterSet = null;

	private JCheckBox cbDetachCompute = null;

	private JPanel jPanel3 = null;

	private JCheckBox cbContractExe = null;

	private JCheckBox cbContractCancel = null;

	/**
	 * This is the default constructor
	 */
	public DgExpProductStat() {
		super();

		contractStatAction = (ContractStatAction) CommonVars
				.getApplicationContext().getBean("contractStatAction");
		contractAction = (ContractAction) CommonVars.getApplicationContext()
				.getBean("contractAction");
		initialize();
		getButtonGroup();
		getButtonGroup1();
		this.cbbBeginDate.setDate(null);
		this.cbbEndDate.setDate(null);
		parameterSet = contractAction.findBcsParameterSet(new Request(
				CommonVars.getCurrUser(), true));
		// this.cbbBeginDate.setEnabled(false);
	}

	public void setVisible(boolean b) {
		if (b) {
			initTable(new Vector());
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
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("出口成品情况统计表");
		this.setSize(849, 510);
		this.setContentPane(getJContentPane());
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
			jTabbedPane.addTab("出口数据", null, getJPanel(), null);
			jTabbedPane.addTab("统计数据", null, getJPanel1(), null);
			jTabbedPane
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							if (((JTabbedPane) e.getSource())
									.getSelectedIndex() == 1) {
								if (tableModel == null) {
									tfContractExpTotalMoney.setText("");
									tfExpTotalMoney.setText("");
									tfExpScale.setText("");
									return;
								}
								ExpProductStatResult statResult = contractStatAction
										.expProductStat(new Request(CommonVars
												.getCurrUser()), tableModel
												.getList());
								tfContractExpTotalMoney
										.setText(statResult.getContractMoney() == null
												|| statResult
														.getContractMoney() == 0 ? ""
												: formatBig(statResult
														.getContractMoney()
														.toString(), 3, false));
								tfExpTotalMoney
										.setText(statResult.getExpTotalMoney() == null
												|| statResult
														.getExpTotalMoney() == 0 ? ""
												: formatBig(statResult
														.getExpTotalMoney()
														.toString(), 3, false));
								tfExpScale
										.setText(statResult.getScale() == null
												|| statResult.getScale() == 0 ? ""
												: formatBig(statResult
														.getScale().toString(),
														3, false));
							}
						}
					});
		}
		return jTabbedPane;
	}

	public String formatBig(String amount, int bigD, boolean isZero) {
		if (amount == null || amount.equals("")) {
			amount = "0";
		}
		BigDecimal bd = new BigDecimal(amount);
		String amountStr = bd.setScale(bigD, BigDecimal.ROUND_HALF_UP)
				.toString();
		if (isZero) {
			return amountStr;
		}
		for (int i = amountStr.length(); i > 0; i--) {
			if (amountStr.substring(i - 1, i).equals("0")) {
				amountStr = amountStr.substring(0, i - 1);
			} else if (amountStr.substring(i - 1, i).equals(".")) {
				amountStr = amountStr.substring(0, i - 1);
				break;
			} else {
				break;
			}
		}
		return amountStr;
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
			jPanel.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jPanel.add(getJSplitPane(), BorderLayout.CENTER);
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
			jLabel2 = new JLabel();
			jLabel1 = new JLabel();
			jLabel = new JLabel();
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jLabel.setBounds(161, 44, 100, 25);
			jLabel.setText("合同出口金额");
			jLabel1.setBounds(161, 92, 100, 25);
			jLabel1.setText("出口成品总值");
			jLabel2.setBounds(161, 144, 100, 25);
			jLabel2.setText("出口成品总值比率");
			jPanel1.add(jLabel, null);
			jPanel1.add(jLabel1, null);
			jPanel1.add(jLabel2, null);
			jPanel1.add(getTfContractExpTotalMoney(), null);
			jPanel1.add(getTfExpTotalMoney(), null);
			jPanel1.add(getTfExpScale(), null);
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
			jToolBar.add(getJPanel2());
			jToolBar.add(getBtnPrint());
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
	private JButton getBtnPrint() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText("退出");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnQuery() {
		if (btnQuery == null) {
			btnQuery = new JButton();
			btnQuery.setText("查询");
			btnQuery.setBounds(new Rectangle(617, 3, 71, 23));
			btnQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jList11.getSelectedContracts().size() == 0) {
						JOptionPane.showMessageDialog(DgExpProductStat.this,
								"请选择合同!", "提示!", JOptionPane.WARNING_MESSAGE);
						return;
					}
					new find().start();

				}
			});
		}
		return btnQuery;
	}

	class find extends Thread {

		public void run() {
			List list = new ArrayList();
			try {
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
				List contracts = jList11.getSelectedContracts();
				//判断手册成品在备案库是否存在;
				Boolean ischeck = contractStatAction.ischeckImgExg(contracts,MaterielType.FINISHED_PRODUCT);
				if(ischeck){
					CommonProgress.closeProgressDialog();
					JOptionPane.showMessageDialog(DgExpProductStat.this, "所查询的合同中，有成品记录号在备案资料库中不存在，请检查更新");
					return;
				}
				list = contractStatAction
						.findExpProductStatByContracts(new Request(CommonVars
								.getCurrUser()), contracts, CommonVars
								.dateToStandDate(cbbBeginDate.getDate()),
								CommonVars
										.dateToStandDate(cbbEndDate.getDate()),
								state,cbDetachCompute.isSelected());

				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgExpProductStat.this, "获取数据失败：！"
						+ e.getMessage(), "提示", 2);
			} finally {
				initTable(list);
			}
		}
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfContractExpTotalMoney() {
		if (tfContractExpTotalMoney == null) {
			tfContractExpTotalMoney = new JTextField();
			tfContractExpTotalMoney.setBounds(265, 44, 164, 25);
		}
		return tfContractExpTotalMoney;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfExpTotalMoney() {
		if (tfExpTotalMoney == null) {
			tfExpTotalMoney = new JTextField();
			tfExpTotalMoney.setBounds(265, 92, 164, 25);
		}
		return tfExpTotalMoney;
	}

	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfExpScale() {
		if (tfExpScale == null) {
			tfExpScale = new JTextField();
			tfExpScale.setBounds(265, 144, 164, 25);
		}
		return tfExpScale;
	}

	/**
	 * 初始化数据Table
	 */
	private void initTable(List list) {

		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list
								.add(addColumn("序号", "serialNo", 60,
										Integer.class));
						// list.add(addColumn("手册号", "emsNo", 100));
						list.add(addColumn("手册号", "emsNo",100));
						list.add(addColumn("合同号", "impContractNo",100));
						list.add(addColumn("商品编码", "complex.code", 100));
						list.add(addColumn("品名", "commName", 100));
						list.add(addColumn("规格", "commSpec", 100));
						list.add(addColumn("单位", "unit.name", 50));
						list.add(addColumn("合同定量", "contractAmount", 100));
						list.add(addColumn("总出口量", "allExpTotalAmont", 100));
						list.add(addColumn("本期总出口金额", "expTotalMoney", 100));
						list.add(addColumn("本期总出口量", "expTotalAmont", 100));
						list.add(addColumn("本期直接出口量", "directExport", 100));
						// list.add(addColumn("比例", "scale", 100));
						list.add(addColumn("本期深加工结转量", "transferFactoryExport",
								100));
						list
								.add(addColumn("本期退厂返工量", "backFactoryRework",
										100));
						list.add(addColumn("本期返工复出量", "reworkExport", 100));
						list.add(addColumn("本期未返工复出量", "reworkNoExport", 100));
						list.add(addColumn("可出口量", "canExportAmount", 100));
						// list.add(addColumn("超量", "overAmount", 100));

						list.add(addColumn("加工费单价", "processUnitPrice", 100));
						list.add(addColumn("加工费总价", "processTotalPrice", 100));
						// list.add(addColumn("法定单位数量", "legalAmount", 100));
						// list.add(addColumn("法定单位", "legalUnit.name", 100));
						// list.add(addColumn("单重", "unitWeight", 100));
						// list.add(addColumn("单位毛重", "unitGrossWeight", 100));
						// list.add(addColumn("单位净重", "unitNetWeight", 100));
						// list.add(addColumn("征减免税方式", "levyMode.name", 100));
						list
								.add(addColumn("关封余量", "customsEnvelopRemain",
										100));
						list.add(addColumn("可直接出口量", "canDirectExportAmount",
								100));
						list.add(addColumn("单价", "unitPrice", 100));
						list.add(addColumn("未转报关单数量", "noTranCustomsNum", 120));
						list.add(addColumn("记录号", "credenceNo", 60,
								Integer.class));
						list.add(addColumn("完成百分比", "completeScale", 60));//35
						list.add(addColumn("归并序号", "innerMergeSeqNum", 60));//36;
						return list;
					}
				});

		// 显示小数位,默认2位
		Integer decimalLength = 2;
		if (parameterSet != null
				&& parameterSet.getReportDecimalLength() != null)
			decimalLength = parameterSet.getReportDecimalLength();
		tableModel.setAllColumnsFractionCount(decimalLength);
		CompanyOther other = CommonVars.getOther();
		if (other != null) {
			tableModel.setAllColumnsshowThousandthsign(other
					.getIsAutoshowThousandthsign() == null ? false : other
					.getIsAutoshowThousandthsign());
		}
		
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jLabel6 = new JLabel();
			jLabel6.setBounds(new Rectangle(190, 3, 15, 22));
			jLabel6.setText("到");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(-2, 3, 90, 22));
			jLabel5.setHorizontalAlignment(SwingConstants.CENTER);
			jLabel5.setText("报关申报日期");
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.add(jLabel5, null);
			jPanel2.add(getCbbBeginDate(), null);
			jPanel2.add(jLabel6, null);
			jPanel2.add(getCbbEndDate(), null);
			jPanel2.add(getRbYes(), null);
			jPanel2.add(getRbNo(), null);
			jPanel2.add(getRbAll(), null);
			jPanel2.add(getBtnQuery(), null);
			jPanel2.add(getJButton(), null);
			jPanel2.add(getCbDetachCompute(), null);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(new Rectangle(94, 3, 92, 22));
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
			cbbEndDate.setBounds(new Rectangle(210, 3, 94, 22));
		}
		return cbbEndDate;
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbYes() {
		if (rbYes == null) {
			rbYes = new JRadioButton();
			rbYes.setBounds(new Rectangle(409, 3, 68, 22));
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
			rbNo.setBounds(new Rectangle(485, 3, 69, 22));
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
			rbAll.setBounds(new Rectangle(557, 3, 56, 22));
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
			buttonGroup.add(this.getRbAll());
			buttonGroup.add(this.getRbYes());
			buttonGroup.add(this.getRbNo());
		}
		return buttonGroup;
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
			jSplitPane.setRightComponent(getJPanel11());
			jSplitPane.setDividerSize(4);
			jSplitPane.setDividerLocation(580);
			jSplitPane.setResizeWeight(1);
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jPanel11
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel11() {
		if (jPanel11 == null) {
			jPanel11 = new JPanel();
			jPanel11.setLayout(new BorderLayout());
			jPanel11.add(getJScrollPane1(), BorderLayout.CENTER);
			jPanel11.add(getJPanel10(), BorderLayout.NORTH);
		}
		return jPanel11;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJList11());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jList11
	 * 
	 * @return com.bestway.bcs.client.contractanalyse.JContractList
	 */
	private JContractList getJList11() {
		if (jList11 == null) {
			jList11 = new JContractList();
			jList11.addMouseListener(new java.awt.event.MouseAdapter() 
			{
				public void mouseClicked(java.awt.event.MouseEvent e)
				{
					cbbBeginDate.setDate(findBeginDate());
					cbbEndDate.setDate(findEndDate());
				}
			});

		}
		return jList11;
	}
	/**
	 * 找出合同中最靠前的日期
	 * 
	 * @return
	 */
	public Date findBeginDate()
	{
		List<Date> list = new ArrayList<Date>();
		Date date = null;
		for(int i = 0;i < jList11.getModel().getSize();i++)
		{
			Contract contract = (Contract) jList11.getModel().getElementAt(i);
			if(contract.getIsSelected() == null || !contract.getIsSelected())
			{
				continue;
			}
			if(contract.getBeginDate() == null)
			{
				continue;
			}
			if(date==null)
			{
				date = contract.getBeginDate();
			}
			else
			{
				if(contract.getBeginDate().before(date))
				{
					date = contract.getBeginDate();
				}
			}
		}
		return date;
	}

	/**
	 * 找出合同中最靠后的日期
	 * 
	 * @return
	 */
	public Date findEndDate()
	{
		List<Date> list = new ArrayList<Date>();
		Date date = null;
		for(int i = 0;i < jList11.getModel().getSize();i++)
		{
			Contract contract = (Contract) jList11.getModel().getElementAt(i);
			if(contract.getIsSelected() == null || !contract.getIsSelected())
			{
				continue;
			}
			if(contract.getEndDate() == null)
			{
				continue;
			}
			if(date==null)
			{
				date = contract.getEndDate();
			}
			else
			{
				if(contract.getEndDate().after(date))
				{
					date = contract.getEndDate();
				}
			}
		}
		return date;
	}
	

	/**
	 * This method initializes jPanel10
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel10() {
		if (jPanel10 == null) {
			GridLayout gridLayout = new GridLayout();
			gridLayout.setRows(3);
			gridLayout.setColumns(1);
			jPanel10 = new JPanel();
			jPanel10.setLayout(gridLayout);
			jPanel10.setPreferredSize(new Dimension(190, 60));
			jPanel10.add(getJPanel3(), null);
			jPanel10.add(getCbContractExe(), null);
			jPanel10.add(getCbContractCancel(), null);
		}
		return jPanel10;
	}

	/**
	 * This method initializes jRadioButton9
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton9() {
		if (jRadioButton9 == null) {
			jRadioButton9 = new JRadioButton();
			jRadioButton9.setText("\u5168\u9009");
			jRadioButton9.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (jRadioButton9.isSelected()) {
						for (int i = 0; i < jList11.getModel().getSize(); i++) {
							Contract contract = (Contract) jList11.getModel()
									.getElementAt(i);
							contract.setSelected(true);
						}
						jList11.repaint();
					}
				}
			});
		}
		return jRadioButton9;
	}

	/**
	 * This method initializes jRadioButton10
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton10() {
		if (jRadioButton10 == null) {
			jRadioButton10 = new JRadioButton();
			jRadioButton10.setText("\u5168\u5426");
			jRadioButton10.setSelected(true);
			jRadioButton10.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (jRadioButton10.isSelected()) {
						for (int i = 0; i < jList11.getModel().getSize(); i++) {
							Contract contract = (Contract) jList11.getModel()
									.getElementAt(i);
							contract.setSelected(false);
						}
						jList11.repaint();
					}
				}
			});
		}
		return jRadioButton10;
	}

	/**
	 * This method initializes buttonGroup1
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup1() {
		if (buttonGroup1 == null) {
			buttonGroup1 = new ButtonGroup();
			buttonGroup1.add(this.getJRadioButton10());
			buttonGroup1.add(this.getJRadioButton9());
		}
		return buttonGroup1;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (btnPrint == null) {
			btnPrint = new JButton();
			btnPrint.setBounds(new Rectangle(692, 3, 67, 23));
			btnPrint.setText("打印");
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// ExpProductStat
					// if (cbbContract.getSelectedItem() == null) {
					// JOptionPane.showMessageDialog(DgExpProductStat.this,
					// "没有正在执行的合同!", "提示!",
					// JOptionPane.INFORMATION_MESSAGE);
					// return;
					// }
					// Contract contract = (Contract) cbbContract
					// .getSelectedItem();
					List<ExpProductStat> dlist = tableModel.getList();
					// List dsList = new ArrayList();
					// for (ExpProductStat data : dlist) {
					// if (data.getEmsNo().equals(
					// ((Contract) cbbContract.getSelectedItem())
					// .getEmsNo())) {
					// dsList.add(data);
					// }
					// }
					if (dlist.size() <= 0) {
						JOptionPane.showMessageDialog(DgExpProductStat.this,
								"没有数据可打印", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					JasperReport tempContractNoSubReport = null;
					List<Contract> contracts = jList11.getSelectedContracts();
					CustomReportDataSource ds = new CustomReportDataSource(
							dlist);
					String subContactEmss ="";
					String subContact="";
					for(Contract c : contracts){
						subContactEmss+=c.getEmsNo()+"\n";
						subContact+=c.getImpContractNo()+"\n";
					}
//					CustomReportDataSource subds = new CustomReportDataSource(
//							contracts);
					InputStream reportStream = DgImpExpScheduleDetail.class
							.getResourceAsStream("report/ExpProductStat.jasper");
//					InputStream subreportStream = DgImpExpScheduleDetail.class
//							.getResourceAsStream("report/ExportProductStatSub.jasper");
					Map<String, Object> parameters = new HashMap<String, Object>();
//					try {
//						tempContractNoSubReport = (JasperReport) JRLoader
//								.loadObject(subreportStream);
//					} catch (JRException e2) {
//						e2.printStackTrace();
//					}
					// parameters.put("contractNo",
					// contract.getImpContractNo());
					// parameters.put("emsNo", contract.getEmsNo());
					parameters.put("beginDate",
							cbbBeginDate.getDate() == null ? ""
									: (new SimpleDateFormat("yyyy-MM-dd"))
											.format(cbbBeginDate.getDate()));
					parameters.put("endDate", cbbEndDate.getDate() == null ? ""
							: (new SimpleDateFormat("yyyy-MM-dd"))
									.format(cbbEndDate.getDate()));
					// parameters.put("companyName", contract.getCompany()
					// .getName());
//					parameters.put("subData", subds);
//					parameters.put("sub", tempContractNoSubReport);
					parameters.put("subContactEmss", subContactEmss);
					parameters.put("subContact", subContact);
					putPrintParameters(parameters);
					JasperPrint jasperPrint;
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

	private void putPrintParameters(Map map) {
		ExpProductStatResult statResult = contractStatAction.expProductStat(
				new Request(CommonVars.getCurrUser()), tableModel.getList());
		map.put("ContractExpTotalMoney", statResult.getContractMoney() == null
				|| statResult.getContractMoney() == 0 ? "" : formatBig(
				statResult.getContractMoney().toString(), 3, false));
		// tfContractExpTotalMoney.setText(statResult.getContractMoney() == null
		// || statResult.getContractMoney() == 0 ? "" : formatBig(
		// statResult.getContractMoney().toString(), 3, false));
		map.put("ExpTotalMoney", statResult.getExpTotalMoney() == null
				|| statResult.getExpTotalMoney() == 0 ? "" : formatBig(
				statResult.getExpTotalMoney().toString(), 3, false));
		// tfExpTotalMoney.setText(statResult.getExpTotalMoney() == null
		// || statResult.getExpTotalMoney() == 0 ? "" : formatBig(
		// statResult.getExpTotalMoney().toString(), 3, false));
		map.put("ExpScale", statResult.getScale() == null
				|| statResult.getScale() == 0 ? "" : formatBig(statResult
				.getScale().toString(), 3, false));
		// tfExpScale.setText(statResult.getScale() == null
		// || statResult.getScale() == 0 ? "" : formatBig(statResult
		// .getScale().toString(), 3, false));

	}

	/**
	 * This method initializes cbDetachCompute	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbDetachCompute() {
		if (cbDetachCompute == null) {
			cbDetachCompute = new JCheckBox();
			cbDetachCompute.setBounds(new Rectangle(325, 3, 77, 22));
			cbDetachCompute.setText("\u5206\u5f00\u7edf\u8ba1");
		}
		return cbDetachCompute;
	}

	/**
	 * This method initializes jPanel3	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setLayout(new GridBagLayout());
			jPanel3.add(getJRadioButton10(), new GridBagConstraints());
			jPanel3.add(getJRadioButton9(), new GridBagConstraints());
		}
		return jPanel3;
	}

	/**
	 * This method initializes cbContractExe	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbContractExe() {
		if (cbContractExe == null) {
			cbContractExe = new JCheckBox();
			cbContractExe.setSelected(true);
			cbContractExe.setText("正在执行的合同");
			cbContractExe.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(cbContractExe.isSelected() && cbContractCancel.isSelected()){
						jList11.showContractData(true, true);
					}else if(cbContractExe.isSelected() && !cbContractCancel.isSelected()){
						jList11.showContractData(true, false);
					}else if(!cbContractExe.isSelected() && cbContractCancel.isSelected()){
						jList11.showContractData(false, true);
					}else{
						jList11.showContractData(false, false);
					}
				}
			});
			cbContractExe.setSelected(true);
			
		}
		return cbContractExe;
	}

	/**
	 * This method initializes cbContractCancel	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbContractCancel() {
		if (cbContractCancel == null) {
			cbContractCancel = new JCheckBox();
			cbContractCancel.setText("核销的合同");
			cbContractCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(cbContractExe.isSelected() && cbContractCancel.isSelected()){
						jList11.showContractData(true, true);
					}else if(cbContractExe.isSelected() && !cbContractCancel.isSelected()){
						jList11.showContractData(true, false);
					}else if(!cbContractExe.isSelected() && cbContractCancel.isSelected()){
						jList11.showContractData(false, true);
					}else{
						jList11.showContractData(false, false);
					}
				}
			});
		}
		return cbContractCancel;
	}
} // @jve:decl-index=0:visual-constraint="40,53"
