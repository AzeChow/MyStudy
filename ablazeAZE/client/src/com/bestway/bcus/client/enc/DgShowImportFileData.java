/*
 * Created on 2004-7-20
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.enc;

import java.awt.BorderLayout;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.enc.entity.ImportApplyToCustomsBillListTempData;
import com.bestway.bcus.enc.entity.ImportApplyCustomsProperty;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.groupableheader.GroupableHeaderTable;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author fhz
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgShowImportFileData extends JDialogBase {

	private List dataSource = null;  //  @jve:decl-index=0:

	private JTableListModel tableModel = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JButton jButton2 = null;

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;
	private EncAction encAction = null;

	private ImportApplyCustomsProperty importApplyProperty = null;// 参数设置实体类  //  @jve:decl-index=0:

	private List list = null;  //  @jve:decl-index=0:

	/**
	 * This method initializes
	 * 
	 */
	public DgShowImportFileData() {
		super();
		encAction = (EncAction) CommonVars
				.getApplicationContext().getBean("encAction");
		initialize();
		jTable = new GroupableHeaderTable();
		jScrollPane.setViewportView(jTable);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("数据记录确认");
		this.setContentPane(getJPanel());
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setSize(762, 404);
	}

	/**
	 * 
	 */
	public void setVisible(boolean isFlag) {
		if (isFlag) {
			if (dataSource != null) {
				initTable(dataSource);
			}
		}
		super.setVisible(isFlag);
	}


	/**
	 * @return Returns the dataSource.
	 */
	public List getDataSource() {
		return dataSource;
	}

	/**
	 * @param dataSource
	 *            The dataSource to set.
	 */
	public void setDataSource(List dataSource) {
		this.dataSource = dataSource;
	}

	private void initTable(List dataSource) {
		tableModel = new JTableListModel((GroupableHeaderTable) jTable,
				dataSource, new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						// ----------表头
						list.add(addColumn("进出口类型", "headImpExpType", 80));
						list.add(addColumn("清单编号", "headBillListNo", 120));
						if (importApplyProperty.getCbbUnifiedCode() != 0)
							list
									.add(addColumn("清单统一编号", "headUnifiedCode",
											100));
						if (importApplyProperty.getCbbListDeclareDate() != 0)
							list.add(addColumn("清单申报日期", "headListDeclareDate",
									90));
						if (importApplyProperty.getCbbDeclareCustom() != 0)
							list
									.add(addColumn("申报地海关",
											"headDeclareCustom", 80));
						if (importApplyProperty.getCbbTransportMode() != 0)
							list
									.add(addColumn("运输方式", "headTransportMode",
											80));
						if (importApplyProperty.getCbbDeclareCompany() != 0)
							list
									.add(addColumn("申报单位",
											"headDeclareCompany", 150));

						list.add(addColumn("电子帐册编号", "headEmsNo", 100));
						if (importApplyProperty.getCbbEntrancePort() != 0)
							list
									.add(addColumn("进出口岸", "headEntrancePort",
											80));
						if (importApplyProperty.getCbbTradeMode() != 0)
							list.add(addColumn("监管方式", "headTradeMode", 80));
						if (importApplyProperty.getCbbMemo()!= 0)
							list.add(addColumn("备注", "headMemo", 150));
						if (importApplyProperty.getCbbListState() != 0)
							list.add(addColumn("清单状态", "headListState", 80));

						// ----------归并后
						if (importApplyProperty.getCbbAfterEmsSerialNo() != 0)
							list.add(addColumn("帐册序号", "afterEmsSerialNo", 60));

						// ----------归并前
						list.add(addColumn("商品货号", "beforeMaterielPtNo", 100));
						if (importApplyProperty.getCbbBeforeCustomsNo() != 0)
							list.add(addColumn("对应报关单商品号", "beforeCustomsNo",
									100));
						if (importApplyProperty.getCbbBeforeCurrency() != 0)
							list.add(addColumn("币别", "beforeCurrency", 50));
						if (importApplyProperty.getCbbBeforeDeclaredAmount() != 0)
							list.add(addColumn("申报数量", "beforeDeclaredAmount",
									80));
						if (importApplyProperty.getCbbBeforeLegalAmount() != 0)
							list
									.add(addColumn("法定数量", "beforeLegalAmount",
											80));
						if (importApplyProperty.getCbbBeforeSecondLegalAmount() != 0)
							list.add(addColumn("第二法定数量",
									"beforeSecondLegalAmount", 90));
						if (importApplyProperty.getCbbBeforeDeclaredPrice() != 0)
							list.add(addColumn("企业申报单价", "beforeDeclaredPrice",
									90));
						if (importApplyProperty.getCbbBeforeSalesCountry() != 0)
							list.add(addColumn("产销国(地区)", "beforeSalesCountry",
									80));
						if (importApplyProperty.getCbbBeforeLevyModel() != 0)
							list.add(addColumn("征免方式", "beforeLevyModel", 80));
						if (importApplyProperty.getCbbBeforeUsess() != 0)
							list.add(addColumn("用途", "beforeUsess", 90));
						if (importApplyProperty.getCbbBeforeVersion() != 0)
							list.add(addColumn("版本号", "beforeVersion", 50));
						if (importApplyProperty.getCbbBeforeGrossWeight() != 0)
							list.add(addColumn("毛重", "beforeGrossWeight", 80));
						if (importApplyProperty.getCbbBeforeNetWeight() != 0)
							list.add(addColumn("净重", "beforeNetWeight", 80));
						
						if (importApplyProperty.getCbbBeforePeice() != 0)
							list.add(addColumn("件数", "beforePeice", 80));
						
						if (importApplyProperty.getCbbBeforeMemos() != 0)
							list.add(addColumn("备注", "beforeMemos", 150));
						if (importApplyProperty.getCbbBeforeProjectDept() != 0)
							list.add(addColumn("事业部", "beforeProjectDept", 150));
						if (importApplyProperty.getCbbBeforeExtendMemo() != 0)
							list.add(addColumn("扩展备注", "beforeExtendMemo", 150));
						
						list.add(addColumn("错误原因", "errorReason", 500));
						return list;
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
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.add(getJPanel1(), java.awt.BorderLayout.SOUTH);
			jPanel.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
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
			jPanel1 = new JPanel();
			jPanel1.add(getJButton1(), null);
			jPanel1.add(getJButton2(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("开始导入数据");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					new ImportFileDataRunnable().start();
				}
			});
		}
		return jButton1;
	}

	class ImportFileDataRunnable extends Thread {
		public void run() {
			try {
				long beginTime = System.currentTimeMillis();
				CommonProgress.showProgressDialog(DgShowImportFileData.this);
				CommonProgress.setMessage("系统正在保存导入文件资料，请稍后...");
				for (int i = 0; i < dataSource.size(); i++) {
					ImportApplyToCustomsBillListTempData fileData = (ImportApplyToCustomsBillListTempData) dataSource
							.get(i);
					if (fileData.getErrinfo() != null
							&& !fileData.getErrinfo().equals("")) {
						CommonProgress.closeProgressDialog();
						JOptionPane.showMessageDialog(
								DgShowImportFileData.this,
								"导入文件有错误，不可导入! 请看最右边的错误原因", "提示", 2);
						return;
					}
				}

				encAction.importDataFromFile(new Request(
						CommonVars.getCurrUser()), list,importApplyProperty,false);
				CommonProgress.closeProgressDialog();
				long totalTime = System.currentTimeMillis() - beginTime;
				JOptionPane.showMessageDialog(DgShowImportFileData.this,
						"导入数据成功！导入数据记录 " + list.size() + " 条,共用时 " + totalTime
								+ " 毫秒", "提示", JOptionPane.INFORMATION_MESSAGE);

				DgShowImportFileData.this.dispose();
			} catch (Exception ex) {
				ex.printStackTrace();
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgShowImportFileData.this,
						"导入数据失败 " + ex.getMessage(), "提示",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("关闭");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton2;
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

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	/**
	 * @param importApplyProperty
	 *            the importApplyProperty to set
	 */
	public void setImportApplyProperty(
			ImportApplyCustomsProperty importApplyProperty) {
		this.importApplyProperty = importApplyProperty;
	}
} 
