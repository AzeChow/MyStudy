/*
 * Created on 2005-6-8
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.client.contractanalyse;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.TableColumnModel;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcs.contract.entity.BcsParameterSet;
import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contractanalyse.action.ContractAnalyseAction;
import com.bestway.bcs.contractanalyse.entity.StorageContractStat;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.groupableheader.ColumnGroup;
import com.bestway.client.util.groupableheader.GroupableHeaderTable;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.common.CommonUtils;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.constant.CustomsDeclarationState;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import java.awt.Rectangle;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import java.awt.Dimension;
import com.bestway.customs.common.entity.BaseEmsHead;
/**
 * @author ls // change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
/**各栏位计算公式
 * 查询条件是针对一本或多本合同相同(商编＋商品名称＋商品规格＋单位)的统计
                   进口
  总进口量(totalImpAmount)=所有使用该合同进口报关单数量之和(料件进口,料件转厂类型，余料进口)-所有使用该合同出口报关单数量之和(出品类型为退料出口贸易方式为0300,0700)
  大单进口量(orderImpAmount)=所有使用该合同进口报关单进口类型为(料件进口,料件转厂类型)数量之和
  退料出口量(backMaterielExpAmount)=所有使用该合同的出口报关单出口类型为退料出口的总数量
  成品使用量(finishProductDosageAmount)=总出口量*单耗/((1-损耗)*0.01)
  余料(库存)(remainStorageAmount)=总进口量-成品使用量-出口报关单的出口类型为余料结转
  现进口余量(nowImpAmount)=合同备案数量 - 总进口量
  总合计(totalize)=总进口量－成品使用量
                  出口
  合同定量(contractRation)＝合同成品数量
  总出口量(totalExpAmount)＝大单出口量-退厂返工+返工复出
  返工数量(returnAmount)=所有使用该合同出口报关单出口类型退厂返工数量之和
  大单出口量(orderExpAmount)=所有使用该合同出口报关单出口类型为(成品出口,成品转厂类型)数量之和
  可出口数(canExpRemain)=合同定量 - 总出口量
  现出口数量(nowExpAmount)＝合同定量 - 总出口量
 * 
 * 
 */
public class DgStorageAnalyse extends JInternalFrameBase {

	private JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JButton btnRefresh = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel1 = null;

	private JPanel jPanel2 = null;

	private JContractList jList = null;

	private JCheckBox cbContractExe = null;

	private GroupableHeaderTable jTable = null;

	private JScrollPane jScrollPane = null;

	private JCheckBox cbContractCancel = null;

	private JScrollPane jScrollPane1 = null;

	private JPanel jPanel = null;

	private JButton btnPrint = null;

	private JButton btnExit = null;

	private JCheckBox cbMateriel = null;

	private JCheckBox cbFinishedProduct = null;

	private JCalendarComboBox cbbBeginDate = null;

	private JCalendarComboBox cbbEndDate = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	private JTableListModel tableModel = null;

	private ContractAnalyseAction contractAnalyseAction = null;

	private JRadioButton rbPortrait = null;

	private JRadioButton rbTransverse = null;

	private ButtonGroup buttonGroup = null;

	private JPanel jPanel3 = null;

	private JRadioButton rbYes = null;

	private JRadioButton rbNo = null;

	private JRadioButton rbAll = null;

	private ButtonGroup buttonGroup1 = null;

	private JPanel jPanel4 = null;

	private JRadioButton jRadioButton = null;

	private JRadioButton jRadioButton1 = null;

	private ButtonGroup buttonGroup2 = null;

	private BcsParameterSet parameterSet = null;

	private ContractAction contractAction = null;

	/**
	 * This method initializes
	 * 
	 */
	public DgStorageAnalyse() {
		super();
		initialize();
		this.cbbBeginDate.setDate(CommonVars.getBeginDate());
		contractAnalyseAction = (ContractAnalyseAction) CommonVars
				.getApplicationContext().getBean("contractAnalyseAction");
		contractAction = (ContractAction) CommonVars.getApplicationContext()
				.getBean("contractAction");
		parameterSet = contractAction.findBcsParameterSet(new Request(
				CommonVars.getCurrUser(), true));
		initMaterielTable(new ArrayList());

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("料件/成品库存分析");
		this.setContentPane(getJContentPane());
		this.getButtonGroup();
		this.getButtonGroup1();
		this.setSize(750, 510);
		getButtonGroup2();

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
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
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
			jToolBar.add(getBtnRefresh());
			jToolBar.add(getBtnPrint());
			jToolBar.add(getBtnExit());

		}
		return jToolBar;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnRefresh() {
		if (btnRefresh == null) {
			btnRefresh = new JButton();
			btnRefresh.setText("查询");
			btnRefresh.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					new MyFindThread().start();
				}
			});
		}
		return btnRefresh;
	}

	@Override
	public void setVisible(boolean f) {
		if (f) {
			jSplitPane.setDividerLocation(0.8);
		}
		super.setVisible(f);
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setDividerSize(4);
			jSplitPane.setDividerLocation(555);
			jSplitPane.setRightComponent(getJPanel1());
			jSplitPane.setLeftComponent(getJScrollPane());
		}
		return jSplitPane;
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
			jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0,
					0, 0));
			jPanel1.add(getJScrollPane1(), BorderLayout.CENTER);
			jPanel1.add(getJPanel2(), BorderLayout.NORTH);
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
			jPanel2
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jPanel2.setPreferredSize(new Dimension(199, 78));
			jPanel2.add(getCbContractExe(), null);
			jPanel2.add(getCbContractCancel(), null);
			jPanel2.add(getJPanel4(), null);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jList
	 * 
	 * @return javax.swing.JContractList
	 */
	private JContractList getJList() {
		if (jList == null) {
			jList = new JContractList();
			jList.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					Date beginDate = null;
					// Date endDate=null;
					int size = jList.getModel().getSize();
					for (int i = 0; i < size; i++) {
						Contract contract = (Contract) jList.getModel()
								.getElementAt(i);
						if (contract.isSelected()) {
							if (beginDate == null) {
								beginDate = contract.getBeginDate();
							} else {
								if (beginDate
										.compareTo(contract.getBeginDate()) > 0) {
									beginDate = contract.getBeginDate();
								}
							}
						}
					}
					if (beginDate != null) {
						cbbBeginDate.setDate(beginDate);
					}
				}
			});
		}
		return jList;
	}

	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbContractExe() {
		if (cbContractExe == null) {
			cbContractExe = new JCheckBox();
			cbContractExe.setText("正在执行的合同");
			cbContractExe.setBounds(new Rectangle(9, 7, 129, 15));
			cbContractExe.setSelected(true);
			cbContractExe
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							selectContractItem();
						}
					});
		}
		return cbContractExe;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new GroupableHeaderTable();
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
	 * This method initializes jCheckBox1
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbContractCancel() {
		if (cbContractCancel == null) {
			cbContractCancel = new JCheckBox();
			cbContractCancel.setText("核销的合同");
			cbContractCancel.setBounds(new Rectangle(10, 24, 117, 18));
			cbContractCancel
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							selectContractItem();
						}
					});
		}
		return cbContractCancel;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJList());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel2 = new JLabel();
			jLabel1 = new JLabel();
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jLabel1.setBounds(118, 4, 14, 24);
			jLabel1.setText("从");
			jLabel2.setBounds(222, 4, 13, 24);
			jLabel2.setText("到");
			jPanel.add(getCbMateriel(), null);
			jPanel.add(getCbFinishedProduct(), null);
			jPanel.add(getCbbBeginDate(), null);
			jPanel.add(getCbbEndDate(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(jLabel2, null);
			jPanel.add(getRbYes(), null);
			jPanel.add(getRbNo(), null);
			jPanel.add(getJPanel3(), null);
		}
		return jPanel;
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
			btnPrint.setVisible(true);
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					print();
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
					DgStorageAnalyse.this.dispose();
				}
			});
		}
		return btnExit;
	}

	/**
	 * This method initializes jCheckBox2
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbMateriel() {
		if (cbMateriel == null) {
			cbMateriel = new JCheckBox();
			cbMateriel.setBounds(5, 4, 54, 21);
			cbMateriel.setText("料件");
			cbMateriel.setSelected(true);
			cbMateriel.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					cbFinishedProduct.setSelected(false);
					cbMateriel.setSelected(true);
				}

			});
		}
		return cbMateriel;
	}

	/**
	 * This method initializes jCheckBox3
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbFinishedProduct() {
		if (cbFinishedProduct == null) {
			cbFinishedProduct = new JCheckBox();
			cbFinishedProduct.setBounds(56, 4, 54, 21);
			cbFinishedProduct.setText("成品");
			cbFinishedProduct.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					cbFinishedProduct.setSelected(true);
					cbMateriel.setSelected(false);
				}

			});
		}
		return cbFinishedProduct;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(136, 4, 84, 24);
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
			cbbEndDate.setBounds(236, 4, 84, 24);
		}
		return cbbEndDate;
	}

	/**
	 * 刷新
	 * 
	 */
	public void refresh() {
		Date beginDate = this.cbbBeginDate.getDate();
		Date endDate = this.cbbEndDate.getDate();
		List contracts = this.jList.getSelectedContracts();
		
		if(beginDate==null){
			CommonProgress.closeProgressDialog();
			JOptionPane.showMessageDialog(DgStorageAnalyse.this, "请先选择开始时间！");
			return;
		}
		if(endDate==null){
			CommonProgress.closeProgressDialog();
			JOptionPane.showMessageDialog(DgStorageAnalyse.this, "请先选择结束时间！");
			return;
		}
		if(contracts==null||contracts.size()==0){
			CommonProgress.closeProgressDialog();
			JOptionPane.showMessageDialog(DgStorageAnalyse.this, "请先选择合同！");
			return;
		}
		
		int state = -1;
		// 截取小数位
		Integer decimalLength = 2;
		if (parameterSet != null
				&& parameterSet.getReportDecimalLength() != null)
			decimalLength = parameterSet.getReportDecimalLength();
		if (this.rbYes.isSelected()) {
			state = CustomsDeclarationState.EFFECTIVED;
		} else if (this.rbNo.isSelected()) {
			state = CustomsDeclarationState.NOT_EFFECTIVED;
		} else {
			state = CustomsDeclarationState.ALL;
		}
		if (beginDate == null || endDate == null || beginDate.after(endDate)
				|| contracts == null || contracts.size() <= 0) {
			if (this.cbMateriel.isSelected()) {
				initMaterielTableY(new ArrayList());
				return;
			}

			else if (this.cbFinishedProduct.isSelected()) {
				initFinishProductTableY(new ArrayList());
				return;
			}
		}
		if (this.cbMateriel.isSelected()) {
			List list = new ArrayList();
			if (rbTransverse.isSelected()) {//横向显示
				list = this.contractAnalyseAction.findStorageStat(new Request(
						CommonVars.getCurrUser()), contracts, beginDate,
						endDate, MaterielType.MATERIEL, state);
				for(Object o : list){
					CommonUtils.formatDouble(o, decimalLength);
				}
				initMaterielTable(list);
			} else if (rbPortrait.isSelected()) {//纵向显示
				list = this.contractAnalyseAction.findStorageContractStat(
						new Request(CommonVars.getCurrUser()), contracts,
						beginDate, endDate, MaterielType.MATERIEL, state);
				for(Object o : list){
					CommonUtils.formatDouble(o, decimalLength);
				}
				initMaterielTableY(list);
			}

		} else if (this.cbFinishedProduct.isSelected()) {
			List lis = new ArrayList();
			if (rbTransverse.isSelected()) {
				lis = this.contractAnalyseAction.findStorageStat(new Request(
						CommonVars.getCurrUser()), contracts, beginDate,
						endDate, MaterielType.FINISHED_PRODUCT, state);
				for(Object o : lis){
					CommonUtils.formatDouble(o, decimalLength);
				}
				initFinishProductTable(lis);
			} else if (rbPortrait.isSelected()) {
				lis = this.contractAnalyseAction.findStorageContractStat(
						new Request(CommonVars.getCurrUser()), contracts,
						beginDate, endDate, MaterielType.FINISHED_PRODUCT,
						state);
				for(Object o : lis){
					CommonUtils.formatDouble(o, decimalLength);
				}
				initFinishProductTableY(lis);
			}

		}
	}

	/**
	 * 初始化料件数据表
	 * 
	 */
	public void initMaterielTable(List dataSource) {
		List contracts = this.jList.getSelectedContracts();
		final int contractSize = contracts.size();
		tableModel = new JTableListModel(jTable, dataSource,
				new JTableListModelAdapter(true) {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("记录号", "credenceNo", 100));//1
						list.add(addColumn("商品编码", "complexCode", 100));//1
						list.add(addColumn("商品名称", "name", 150));//2
						list.add(addColumn("商品规格", "spec", 150));//3
						list.add(addColumn("单位", "unit.name", 50));//4
						for (int i = 0; i < contractSize; i++) {
							list.add(addColumn("合同定量", "contractRation", 100,//5
									i, "tempContractStatList"));
							list.add(addColumn("总进口量", "totalImpAmount", 100,//6
									i, "tempContractStatList"));
							list.add(addColumn("大单进口量", "orderImpAmount", 100,//7
									i, "tempContractStatList"));
							list.add(addColumn("退料出口量",//8
									"backMaterielExpAmount", 100, i,
									"tempContractStatList"));
							list.add(addColumn("成品使用量",//9
									"finishProductDosageAmount", 100, i,
									"tempContractStatList"));
							list.add(addColumn("余料(库存)", "remainStorageAmount",//10
									100, i, "tempContractStatList"));
							list.add(addColumn("现进口余量", "nowImpAmount", 100, i,//11
									"tempContractStatList"));
						}
						list.add(addColumn("余料(库存)总合计", "totalize", 120));
						return list;
					}
				});

		// 截取小数位
		Integer decimalLength = 2;
		if (parameterSet != null
				&& parameterSet.getReportDecimalLength() != null)
			decimalLength = parameterSet.getReportDecimalLength();

		List<JTableListColumn> cms = tableModel.getColumns();
		for (int i = 0; i < contractSize; i++) {
//			cms.get(5).setFractionCount(decimalLength);
			cms.get(6).setFractionCount(decimalLength);
			cms.get(7).setFractionCount(decimalLength);
			cms.get(8).setFractionCount(decimalLength);
			cms.get(9).setFractionCount(decimalLength);
			cms.get(10).setFractionCount(decimalLength);
			cms.get(11).setFractionCount(decimalLength);
			cms.get(12).setFractionCount(decimalLength);
		}

		TableColumnModel cm = jTable.getColumnModel();
		GroupableTableHeader header = (GroupableTableHeader) jTable
				.getTableHeader();

		//hwy2012-11-07
		for (int i = 0; i < contractSize; i++) {
			Contract contract = (Contract) contracts.get(i);
			ColumnGroup tempColumnGroup = new ColumnGroup(contract
					.getImpContractNo()+"("+contract.getEmsNo()+")");
//			tempColumnGroup.add(cm.getColumn(5 + 7 * i));
			tempColumnGroup.add(cm.getColumn(6 + 7 * i));
			tempColumnGroup.add(cm.getColumn(7 + 7 * i));
			tempColumnGroup.add(cm.getColumn(8 + 7 * i));
			tempColumnGroup.add(cm.getColumn(9 + 7 * i));
			tempColumnGroup.add(cm.getColumn(10 + 7 * i));
		    tempColumnGroup.add(cm.getColumn(11 + 7 * i));
		    tempColumnGroup.add(cm.getColumn(12 + 7 * i));
			header.addColumnGroup(tempColumnGroup);
		}

	}

		
	public void initMaterielTableY(List dataSource) {
		tableModel = new JTableListModel(jTable, dataSource,
				new JTableListModelAdapter(true) {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("记录号", "credenceNo", 70));
						list.add(addColumn("合同编号", "impContractNo", 70));
						list.add(addColumn("商品编码", "complexCode", 80));
						list.add(addColumn("商品名称", "name", 150));
						list.add(addColumn("商品规格", "spec", 150));
						list.add(addColumn("单位", "unit.name", 50));
						list.add(addColumn("合同定量", "contractRation", 100));
						list.add(addColumn("总进口量", "totalImpAmount", 100));
						list.add(addColumn("大单进口量", "orderImpAmount", 100));
						list.add(addColumn("退料出口量", "backMaterielExpAmount",
								100));
						list.add(addColumn("成品使用量",
								"finishProductDosageAmount", 100));
						list
								.add(addColumn("余料(库存)", "remainStorageAmount",
										100));
						list.add(addColumn("现进口余量", "nowImpAmount", 100));

						return list;
					}
				});

		// 截取小数位
		Integer decimalLength = 2;
		if (parameterSet != null
				&& parameterSet.getReportDecimalLength() != null)
			decimalLength = parameterSet.getReportDecimalLength();

		List<JTableListColumn> cms = tableModel.getColumns();
//		cms.get(6).setFractionCount(decimalLength);
		cms.get(7).setFractionCount(decimalLength);
		cms.get(8).setFractionCount(decimalLength);
		cms.get(9).setFractionCount(decimalLength);
		cms.get(10).setFractionCount(decimalLength);
		cms.get(11).setFractionCount(decimalLength);
		cms.get(12).setFractionCount(decimalLength);

	}

	/**
	 * 初始化成品数据表
	 * 
	 */
	public void initFinishProductTable(List dataSource) {
		List contracts = this.jList.getSelectedContracts();
		final int contractSize = contracts.size();
		tableModel = new JTableListModel(jTable, dataSource,
				new JTableListModelAdapter(true) {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("记录号", "credenceNo", 70));
						list.add(addColumn("商品编码", "complexCode", 100));
						list.add(addColumn("商品名称", "name", 150));
						list.add(addColumn("商品规格", "spec", 150));
						list.add(addColumn("单位", "unit.name", 50));
						for (int i = 0; i < contractSize; i++) {
							list.add(addColumn("合同定量", "contractRation", 100,
									i, "tempContractStatList"));
							list.add(addColumn("总出口量", "totalExpAmount", 100,
									i, "tempContractStatList"));
							list.add(addColumn("返工数量", "returnAmount", 100, i,
									"tempContractStatList"));
							list.add(addColumn("大单出口量", "orderExpAmount", 100,
									i, "tempContractStatList"));
							list.add(addColumn("可出口数", "canExpRemain", 100, i,
									"tempContractStatList"));
							list.add(addColumn("现出口数量", "nowExpAmount", 100, i,
									"tempContractStatList"));
						}
						return list;
					}
				});

		// 截取小数位
		Integer decimalLength = 2;
		if (parameterSet != null
				&& parameterSet.getReportDecimalLength() != null)
			decimalLength = parameterSet.getReportDecimalLength();

		List<JTableListColumn> cms = tableModel.getColumns();
		for (int i = 0; i < contractSize; i++) {
//			cms.get(5).setFractionCount(decimalLength);
			cms.get(6).setFractionCount(decimalLength);
			cms.get(7).setFractionCount(decimalLength);
			cms.get(8).setFractionCount(decimalLength);
			cms.get(9).setFractionCount(decimalLength);
			cms.get(10).setFractionCount(decimalLength);
			cms.get(11).setFractionCount(decimalLength);
		}

		TableColumnModel cm = jTable.getColumnModel();
		GroupableTableHeader header = (GroupableTableHeader) jTable
				.getTableHeader();
		//hwy2012-11-07
		for (int i = 0; i < contractSize; i++) {
			Contract contract = (Contract) contracts.get(i);
			ColumnGroup tempColumnGroup = new ColumnGroup(contract
					.getExpContractNo()+"("+contract.getEmsNo()+")");
//			tempColumnGroup.add(cm.getColumn(5 + 6 * i));
			tempColumnGroup.add(cm.getColumn(6 + 6 * i));
			tempColumnGroup.add(cm.getColumn(7 + 6 * i));
			tempColumnGroup.add(cm.getColumn(8 + 6 * i));
			tempColumnGroup.add(cm.getColumn(9 + 6 * i));
			tempColumnGroup.add(cm.getColumn(10 + 6 * i));
			tempColumnGroup.add(cm.getColumn(11 + 6 * i));
			header.addColumnGroup(tempColumnGroup);
		}

	}

	public void initFinishProductTableY(List dataSource) {
		List contracts = this.jList.getSelectedContracts();
		final int contractSize = contracts.size();
		tableModel = new JTableListModel(jTable, dataSource,
				new JTableListModelAdapter(true) {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("记录号", "credenceNo", 70));
						list.add(addColumn("合同编号", "expContractNo", 70));
						list.add(addColumn("商品编码", "complexCode", 80));
						list.add(addColumn("商品名称", "name", 150));
						list.add(addColumn("商品规格", "spec", 150));
						list.add(addColumn("单位", "unit.name", 50));
						list.add(addColumn("合同定量", "contractRation", 100));
						list.add(addColumn("总出口量", "totalExpAmount", 100));
						list.add(addColumn("返工数量", "returnAmount", 100));
						list.add(addColumn("大单出口量", "orderExpAmount", 100));
						list.add(addColumn("可出口数", "canExpRemain", 100));
						list.add(addColumn("现出口数量", "nowExpAmount", 100));

						return list;
					}
				});

	}

	/**
	 * 选择合同项目
	 */
	private void selectContractItem() {
		jList.showContractData(this.cbContractExe.isSelected(),
				this.cbContractCancel.isSelected());
	}

	/**
	 * 打印
	 * 
	 */
	private void print() {
		Integer materiel = 1;
		Integer finishedProduct = 2;
		if (cbMateriel.isSelected() == true) {
			List<StorageContractStat> list = this.tableModel.getList();
			Map<String, Integer> parameters = new HashMap<String, Integer>();
			try {
				if (list == null || list.size() <= 0) {
					JOptionPane.showMessageDialog(DgStorageAnalyse.this,
							"没有数据可打印", "提示", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				parameters.put("materielType", materiel);
				CustomReportDataSource ds = new CustomReportDataSource(list);
				InputStream masterReportStream = DgMaterielExecuteAnalyse.class
						.getResourceAsStream("report/StorageAnalysereport.jasper");
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters, ds);
				DgReportViewer viewer = new DgReportViewer(jasperPrint);
				viewer.setVisible(true);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else if (cbFinishedProduct.isSelected() == true) {
			List<StorageContractStat> list = this.tableModel.getList();
			Map<String, Object> parameters = new HashMap<String, Object>();
			try {

				if (list == null || list.size() <= 0) {
					JOptionPane.showMessageDialog(DgStorageAnalyse.this,
							"没有数据可打印", "提示", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				parameters.put("materielType", finishedProduct);
				CustomReportDataSource ds = new CustomReportDataSource(list);
				InputStream masterReportStream = DgMaterielExecuteAnalyse.class
						.getResourceAsStream("report/StorageAnalysereport.jasper");
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters, ds);
				DgReportViewer viewer = new DgReportViewer(jasperPrint);
				viewer.setVisible(true);

			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}

	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbYes() {
		if (rbPortrait == null) {
			rbPortrait = new JRadioButton();
			rbPortrait.setBounds(new java.awt.Rectangle(328, 2, 79, 14));
			rbPortrait.setSelected(true);
			rbPortrait.setText("纵向显示");
			rbPortrait.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (rbPortrait.isSelected()) {
						refresh();
						btnPrint.setEnabled(true);
					}
				}
			});
		}
		return rbPortrait;
	}

	/**
	 * This method initializes jRadioButton1
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbNo() {
		if (rbTransverse == null) {
			rbTransverse = new JRadioButton();
			rbTransverse.setBounds(new java.awt.Rectangle(328, 16, 79, 14));
			rbTransverse.setText("横向显示");
			rbTransverse.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (rbTransverse.isSelected()) {
						refresh();
						btnPrint.setEnabled(false);
					}
				}
			});
		}
		return rbTransverse;
	}

	private ButtonGroup getButtonGroup() {
		if (buttonGroup == null) {
			buttonGroup = new ButtonGroup();
			buttonGroup.add(getRbYes());
			buttonGroup.add(getRbNo());

		}
		return buttonGroup;
	}

	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setLayout(null);
			jPanel3.setBounds(new java.awt.Rectangle(415, 2, 189, 26));
			jPanel3.setBorder(BorderFactory.createTitledBorder(null, "",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, null, null));
			jPanel3.add(getRbAll(), null);
			jPanel3.add(getJRadioButton12(), null);
			jPanel3.add(getJRadioButton22(), null);
		}
		return jPanel3;
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbAll() {
		if (rbYes == null) {
			rbYes = new JRadioButton();
			rbYes.setBounds(new java.awt.Rectangle(5, 3, 62, 20));
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
	private JRadioButton getJRadioButton12() {
		if (rbNo == null) {
			rbNo = new JRadioButton();
			rbNo.setBounds(new java.awt.Rectangle(63, 3, 66, 20));
			rbNo.setText("\u672a\u751f\u6548");
		}
		return rbNo;
	}

	/**
	 * This method initializes jRadioButton2
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton22() {
		if (rbAll == null) {
			rbAll = new JRadioButton();
			rbAll.setBounds(new java.awt.Rectangle(128, 3, 52, 20));
			rbAll.setText("\u5168\u90e8");

		}
		return rbAll;
	}

	/**
	 * This method initializes buttonGroup1
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup1() {
		if (buttonGroup1 == null) {
			buttonGroup1 = new ButtonGroup();
			buttonGroup1.add(this.rbAll);
			buttonGroup1.add(this.rbYes);
			buttonGroup1.add(this.rbNo);
		}
		return buttonGroup1;
	}

	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jPanel4 = new JPanel();
			jPanel4.setLayout(null);
			jPanel4.setBounds(new Rectangle(9, 44, 145, 23));
			jPanel4.add(getJRadioButton(), null);
			jPanel4.add(getJRadioButton1(), null);
		}
		return jPanel4;
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton() {
		if (jRadioButton == null) {
			jRadioButton = new JRadioButton();
			jRadioButton.setBounds(new Rectangle(3, 1, 67, 22));
			jRadioButton.setText("\u5168\u9009");
			jRadioButton.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (jRadioButton.isSelected()) {
						for (int i = 0; i < jList.getModel().getSize(); i++) {
							Contract contract = (Contract) jList.getModel()
									.getElementAt(i);
							contract.setSelected(true);
						}
						jList.repaint();
					}
				}
			});
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
			jRadioButton1.setBounds(new java.awt.Rectangle(84, 1, 61, 22));
			jRadioButton1.setText("\u5168\u5426");
			jRadioButton1.setSelected(true);
			jRadioButton1.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (jRadioButton1.isSelected()) {
						for (int i = 0; i < jList.getModel().getSize(); i++) {
							Contract contract = (Contract) jList.getModel()
									.getElementAt(i);
							contract.setSelected(false);
						}
						jList.repaint();
					}
				}
			});
		}
		return jRadioButton1;
	}

	/**
	 * This method initializes buttonGroup2
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup2() {
		if (buttonGroup2 == null) {
			buttonGroup2 = new ButtonGroup();
			buttonGroup2.add(getJRadioButton1());
			buttonGroup2.add(getJRadioButton());
		}
		return buttonGroup2;
	}

	class MyFindThread extends Thread {
		public void run() {
			CommonProgress.showProgressDialog();
			CommonProgress.setMessage("系统正获取数据，请稍后...");
			refresh();
			CommonProgress.closeProgressDialog();
		}
	}
}
