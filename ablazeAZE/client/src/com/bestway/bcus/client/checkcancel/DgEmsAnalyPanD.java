/*
 * Created on 2005-4-6
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.checkcancel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import com.bestway.bcus.checkcancel.action.CheckCancelAction;
import com.bestway.bcus.checkcancel.entity.EmsAnalyHead;
import com.bestway.bcus.checkcancel.entity.EmsPdExg;
import com.bestway.bcus.checkcancel.entity.EmsPdExgBg;
import com.bestway.bcus.checkcancel.entity.EmsPdImg;
import com.bestway.bcus.client.DgErrorMessage;
import com.bestway.bcus.client.common.CommonDataSource;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonQueryPage;
import com.bestway.bcus.client.common.CommonStepProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.footer.JFooterScrollPane;
import com.bestway.client.util.footer.JFooterTable;
import com.bestway.client.util.footer.TableFooterType;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;

/**
 * @author xxm
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
@SuppressWarnings({"serial", "rawtypes", "unchecked"})
public class DgEmsAnalyPanD extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;
	private JPanel jPanel2 = null;
	private JLabel jLabel3 = null;
	private JLabel jLabel4 = null;
	private JCalendarComboBox jCalendarComboBox = null;
	private JLabel jLabel5 = null;
	private JCalendarComboBox jCalendarComboBox1 = null;
	private JButton jButton1 = null;
	private JButton jButton2 = null;
	private JTabbedPane jTabbedPane = null;
	private JPanel jPanel1 = null;
	private JPanel jPanel3 = null;
	private JPanel jPanel4 = null;
	private JPanel jPanel5 = null;
	private JSplitPane jSplitPane1 = null;
	private JPanel jPanel6 = null;
	private JPanel jPanel8 = null;
	private JFooterTable tbPdImg = null;
	private JFooterScrollPane jScrollPane = null;
	private JToolBar jToolBar1 = null;
	private JButton btnAllImgConver = null;
	private JButton btnImgAdd = null;
	private JFooterTable tfPdImgToBg = null;
	private JFooterScrollPane jScrollPane1 = null;
	private JSplitPane jSplitPane2 = null;
	private JPanel jPanel9 = null;
	private JPanel jPanel10 = null;
	private JFooterTable tbPdExg = null;
	private JFooterScrollPane jScrollPane2 = null;
	private JToolBar jToolBar2 = null;
	private JButton btnExgAllConver = null;
	private JButton btnExgAdd = null;
	private JButton btnExgEdit = null;
	private JButton btnExgDelete = null;
	private JFooterTable tbPdExgToBg = null;
	private JFooterScrollPane jScrollPane3 = null;
	private JSplitPane jSplitPane3 = null;
	private JPanel jPanel11 = null;
	private JPanel jPanel12 = null;
	private JFooterTable tbBgExg = null;
	private JFooterScrollPane jScrollPane4 = null;
	private JToolBar jToolBar3 = null;
	private JButton btnAllExgToImg = null;
	private JFooterTable tfBgExgToImg = null;
	private JFooterScrollPane jScrollPane5 = null;
	private JTable tbTotalImg = null;
	private JScrollPane jScrollPane6 = null;
	private JTextField jTextField = null;
	private JButton jButton = null;
	private JTableListModel tableModel = null;
	private JTableListModel tableModelImgS = null; // 料件盘点
	private JTableListModel tableModelImgX = null;
	private JTableListModel tableModelExgS = null; // 成品盘点
	private JTableListModel tableModelExgX = null;
	private JTableListModel tableModelExgImgS = null;// 成品折料
	private JTableListModel tableModelExgImgX = null;
	private JTableListModel tableModelTransferNorth = null; // 转厂分析
	private JTableListModel tableModelTransferCenter = null; 
	private JTableListModel tableModelTotal = null; // 分析总表
	
	private EmsAnalyHead head = null;
	private JButton btnImgEdit = null;
	private JButton btnImgDelete = null;
	private CheckCancelAction checkCancelAction = null;
	private JButton btnEditImgCus = null;
	private JButton btnEditCusExg = null;
	private JButton btnShowNoCus = null;
	private JToolBar jToolBar = null;
	private JToolBar jToolBar4 = null;
	private JToolBar jToolBar5 = null;
	private JButton btnDeletePdExgToImg = null;
	private JCheckBox cbbMaxVersion = null;
	private JPanel pnTransfer;
	private JSplitPane slpnTransfer;
	private JPanel pnTransferNorth;
	private JPanel pnTransferCenter;
	private JToolBar tbTransferNorth;
	private JScrollPane spTransferNorth;
	private JTable tableTransferNorth;
	private JButton btnTxtImportTransfer;
	private JButton btnDeleteTransfer;
	private JToolBar tbTransferCenter;
	private JScrollPane spTransferCenter;
	private JTable tableTransferCenter;
	private JButton btnTransferConvertAll;

	/**
	 * This is the default constructor
	 */
	public DgEmsAnalyPanD() {
		super();
		checkCancelAction = (CheckCancelAction) CommonVars
				.getApplicationContext().getBean("checkCancelAction");
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(834, 566);
		this.setContentPane(getJContentPane());
		this.setTitle("报关材料/工厂实物帐面分析");
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {
				if (tableModel.getCurrentRow() != null) {
					head = (EmsAnalyHead) tableModel.getCurrentRow();
					fillWindow();
					// 显示盘点料件
					List list = checkCancelAction.findEmsPdImg(new Request(
							CommonVars.getCurrUser()), head);
					if (list != null && list.size() > 0) {
						tableModelImgS = initTablePdImgS(tableModelImgS,
								tbPdImg, list);
					} else {
						tableModelImgS = initTablePdImgS(tableModelImgS,
								tbPdImg, new Vector());
					}
					// 显示盘点报关料件
					list = checkCancelAction.findEmsPdImgBg(new Request(
							CommonVars.getCurrUser()), head);
					if (list != null && list.size() > 0) {
						tableModelImgX = initTablePdImgX(tableModelImgX,
								tfPdImgToBg, list);
					} else {
						tableModelImgX = initTablePdImgX(tableModelImgX,
								tfPdImgToBg, new Vector());
					}

					// 显示盘点成品
					list = checkCancelAction.findEmsPdExg(new Request(
							CommonVars.getCurrUser()), head);
					if (list != null && list.size() > 0) {
						tableModelExgS = initTablePdExgS(tableModelExgS,
								tbPdExg, list);
					} else {
						tableModelExgS = initTablePdExgS(tableModelExgS,
								tbPdExg, new Vector());
					}
					// 显示盘点报关成品
					list = checkCancelAction.findEmsPdExgBg(new Request(
							CommonVars.getCurrUser()), head);
					if (list != null && list.size() > 0) {
						tableModelExgX = initTablePdExgX(tableModelExgX,
								tbPdExgToBg, list);
						tableModelExgImgS = initTablePdExgX(tableModelExgImgS,
								tbBgExg, list);
					} else {
						tableModelExgX = initTablePdExgX(tableModelExgX,
								tbPdExgToBg, new Vector());
						tableModelExgImgS = initTablePdExgX(tableModelExgImgS,
								tbBgExg, new Vector());
					}

					// 显示盘点成品折料
					list = checkCancelAction.findEmsExgImgBg(new Request(
							CommonVars.getCurrUser()), head);
					if (list != null && list.size() > 0) {
						tableModelExgImgX = initTablePdExgImgX(
								tableModelExgImgX, tfBgExgToImg, list);
					} else {
						tableModelExgImgX = initTablePdExgImgX(
								tableModelExgImgX, tfBgExgToImg, new Vector());
					}
					
					// 显示转厂
					initTableTransNorth(checkCancelAction.findEmsTransFactory(new Request(
							CommonVars.getCurrUser()), head));
					initTableTransCenter(checkCancelAction.findEmsTransFactoryBg(new Request(
							CommonVars.getCurrUser()), head));

					// 显示盘点分析
					list = checkCancelAction.findEmsPdTotal(new Request(
							CommonVars.getCurrUser()), head);
					if (list != null && list.size() > 0) {
						tableModelTotal = initTablePdAnalyTotal(
								tableModelTotal, tbTotalImg, list);
					} else {
						tableModelTotal = initTablePdAnalyTotal(
								tableModelTotal, tbTotalImg, new Vector());
					}
				}
			}
		});
	}
	
	// 填充--转厂--上
	private void initTableTransNorth(List list) {
		if(list == null) {
			list = new ArrayList();
		}
		tableModelTransferNorth = new JTableListModel(tableTransferNorth, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("料号", "ptNo", 120));
						list.add(addColumn("品名", "ptName", 150));
						list.add(addColumn("规格", "ptSpec", 200));
						list.add(addColumn("计量单位", "calUnit.name", 80));
						list.add(addColumn("已收货未转厂", "unTransferNum", 80));
						list.add(addColumn("已送货未转厂", "unReceiveNum", 80));
						return list;
					}
				});
		tableModelTransferNorth.clearFooterTypeInfo();
		tableModelTransferNorth.addFooterTypeInfo(new TableFooterType(0,
				TableFooterType.CONSTANT, "合计"));
		for (int i = 5; i < 8; i++) {
			tableModelTransferNorth.addFooterTypeInfo(new TableFooterType(i,
					TableFooterType.SUM, "", 5));
		}
	}
	
	// 填充--转厂--下
	private void initTableTransCenter(List list) {
		if(list == null) {
			list = new ArrayList();
		}
			tableModelTransferCenter = new JTableListModel(tableTransferCenter, list,
					new JTableListModelAdapter() {
						public List InitColumns() {
							List list = new Vector();
							list.add(addColumn("料号", "emsTransFactory.ptNo", 110));
							list.add(addColumn("物料名称", "emsTransFactory.ptName", 100));
							list.add(addColumn("物料规格", "emsTransFactory.ptSpec", 100));
							list.add(addColumn("备案序号", "seqNum", 50));
							list.add(addColumn("报关商品名称", "name", 100));
							list.add(addColumn("报关商品规格", "spec", 100));
							list.add(addColumn("计量单位", "unit.name", 60));
							list.add(addColumn("已收货未转厂", "unTransferNum", 80));
							list.add(addColumn("已转厂未收货", "unReceiveNum", 80));
							list.add(addColumn("折算系数", "convertRate", 70));
							return list;
						}
					});
			tableModelTransferCenter.clearFooterTypeInfo();
			tableModelTransferCenter.addFooterTypeInfo(new TableFooterType(0,
					TableFooterType.CONSTANT, "合计"));
			tableModelTransferCenter.addFooterTypeInfo(new TableFooterType(8,
					TableFooterType.SUM, "", 5));
			tableModelTransferCenter.addFooterTypeInfo(new TableFooterType(9,
					TableFooterType.SUM, "", 5));
		}
	

	// 填充盘点--料件--上
	public JTableListModel initTablePdImgS(JTableListModel tableModel,
			JTable jTable, final List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("料号", "ptNo", 120));
						list.add(addColumn("品名", "ptName", 150));
						list.add(addColumn("规格", "ptSpec", 150));
						list.add(addColumn("计量单位", "calUnit.name", 80));
						list.add(addColumn("保税数量", "proNum", 80));
						list.add(addColumn("非保税数量", "notProNum", 80));
						list.add(addColumn("盘点总数", "pdNum", 80));
						return list;
					}
				});
		tableModel.clearFooterTypeInfo();
		tableModel.addFooterTypeInfo(new TableFooterType(0,
				TableFooterType.CONSTANT, "合计"));
		for (int i = 5; i < 8; i++) {
			tableModel.addFooterTypeInfo(new TableFooterType(i,
					TableFooterType.SUM, "", 5));
		}
		return tableModel;
	}

	// 填充盘点--料件--下
	public JTableListModel initTablePdImgX(JTableListModel tableModel,
			JTable jTable, final List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						// list.add(addColumn("帐册编号", "emsNo", 80));
						list.add(addColumn("料号", "pdImg.ptNo", 110));
						list.add(addColumn("物料名称", "pdImg.ptName", 100));
						list.add(addColumn("物料规格", "pdImg.ptSpec", 100));
						list.add(addColumn("备案序号", "seqNum", 50));
						list.add(addColumn("报关商品名称", "name", 100));
						list.add(addColumn("报关商品规格", "spec", 100));
						list.add(addColumn("计量单位", "unit.name", 60));
						list.add(addColumn("总数量", "totalNum", 80));
						list.add(addColumn("折算系数", "convertNUm", 70));
						// list.add(addColumn("保税数量", "proNum", 80));
						// list.add(addColumn("非保税数量", "notProNum", 80));

						return list;
					}
				});
		tableModel.clearFooterTypeInfo();
		tableModel.addFooterTypeInfo(new TableFooterType(0,
				TableFooterType.CONSTANT, "合计"));
		for (int i = 8; i < 9; i++) {
			tableModel.addFooterTypeInfo(new TableFooterType(i,
					TableFooterType.SUM, "", 5));
		}
		return tableModel;
	}

	// 填充盘点--成品--上
	public JTableListModel initTablePdExgS(JTableListModel tableModel,
			JTable jTable, final List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						// list.add(addColumn("帐号编号","emsNo",80));
						// list.add(addColumn("备案序号","seqNum",80));
						// list.add(addColumn("报关品名","name",100));
						// list.add(addColumn("型号规格","spec",100));
						// list.add(addColumn("计量单位","unit",80));
						// list.add(addColumn("版本号","versionNo",100));
						// list.add(addColumn("分配数量","allotNum",80));
						// list.add(addColumn("总数量","totalNum",80));
						// list.add(addColumn("物料类别", "ptNo1", 80));
						list.add(addColumn("料号", "ptNo", 120));
						list.add(addColumn("品名", "ptName", 150));
						list.add(addColumn("规格", "ptSpec", 150));
						list.add(addColumn("工单号", "workBillNo", 100));
						list.add(addColumn("版本号", "versionNo", 80));
						list.add(addColumn("计量单位", "calUnit.name", 80));
						list.add(addColumn("盘点数量", "pdNum", 80));
						return list;
					}
				});
		tableModel.clearFooterTypeInfo();
		tableModel.addFooterTypeInfo(new TableFooterType(0,
				TableFooterType.CONSTANT, "合计"));
		tableModel.addFooterTypeInfo(new TableFooterType(7,
				TableFooterType.SUM, "", 5));
		return tableModel;
	}

	// 填充盘点--成品--下
	public JTableListModel initTablePdExgX(JTableListModel tableModel,
			JTable jTable, final List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("料号", "pdExg.ptNo", 100));
						list.add(addColumn("物料名称", "pdExg.ptName", 90));
						list.add(addColumn("物料规格", "pdExg.ptSpec", 90));
						list.add(addColumn("成品序号", "seqNum", 50));
						list.add(addColumn("报关商品名称", "name", 100));
						list.add(addColumn("报关商品规格", "spec", 100));
						list.add(addColumn("计量单位", "unit.name", 50));
						list.add(addColumn("成品版本", "versionNo", 50));
						list.add(addColumn("分配数量", "allotNum", 80));
						list.add(addColumn("折算系数", "convertNUm", 70));
						list.add(addColumn("总数量", "totalNum", 70));
						return list;
					}
				});
		tableModel.clearFooterTypeInfo();
		tableModel.addFooterTypeInfo(new TableFooterType(0,
				TableFooterType.CONSTANT, "合计"));
		tableModel.addFooterTypeInfo(new TableFooterType(9,
				TableFooterType.SUM, "", 5));
		tableModel.addFooterTypeInfo(new TableFooterType(11,
				TableFooterType.SUM, "", 5));
		return tableModel;
	}

	// 填充盘点--成品折料--下
	public JTableListModel initTablePdExgImgX(JTableListModel tableModel,
			JTable jTable, final List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("成品料号", "pdExgBg.pdExg.ptNo", 110));
						list.add(addColumn("成品序号", "pdExgBg.seqNum", 50));
						list.add(addColumn("成品版本", "pdExgBg.versionNo", 60));
						list.add(addColumn("料件序号", "seqNum", 60));
						list.add(addColumn("料件名称", "name", 100));
						list.add(addColumn("型号规格", "spec", 100));
						list.add(addColumn("计量单位", "unit.name", 80));
						list.add(addColumn("单耗", "unitWare", 80));
						list.add(addColumn("损耗", "ware", 80));
						list.add(addColumn("总耗用", "totalWate", 110));
						return list;
					}
				});
		tableModel.clearFooterTypeInfo();
		tableModel.addFooterTypeInfo(new TableFooterType(0,
				TableFooterType.CONSTANT, "合计"));
		tableModel.addFooterTypeInfo(new TableFooterType(10,
				TableFooterType.SUM, "", 5));
		
		return tableModel;
	}

	// 填充盘点--分析总表
	public JTableListModel initTablePdAnalyTotal(JTableListModel tableModel,
			JTable jTable, final List list) {
		return tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						// list.add(addColumn("帐册编号", "emsNo", 80));
						list.add(addColumn("备案序号", "seqNum", 70));
						list.add(addColumn("商品名称", "name", 80));
						list.add(addColumn("型号规格", "spec", 80));
						list.add(addColumn("计量单位", "unit.name", 70));
						list.add(addColumn("料件库存(A)", "imgNum", 80));
						list.add(addColumn("成品折料库存(B)", "exgNum", 100));
						list.add(addColumn("已收货未转厂(C)", "unTransferNum", 100));
						list.add(addColumn("已送货未转厂(D)", "unReceiveNum", 100));
						list.add(addColumn("总库存(A+B-C+D)", "totalNum", 120));
						return list;
					}
				});
	}

	private void fillWindow() {
		if (head != null) {
			this.jTextField.setText(String.valueOf(head.getEmsNo()));
			this.jCalendarComboBox.setDate(head.getBeginDate());
			this.jCalendarComboBox1.setDate(head.getEndDate());
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
			jContentPane.add(getJTabbedPane(), BorderLayout.CENTER);
			// jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
			jContentPane.add(getJPanel2(), BorderLayout.NORTH);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jLabel5 = new JLabel();
			jLabel4 = new JLabel();
			jLabel3 = new JLabel();
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.setPreferredSize(new Dimension(1, 30));
			jLabel3.setText("核销流水号:");
			jLabel3.setBounds(5, 5, 70, 18);
			jLabel4.setText("起始日期");
			jLabel4.setBounds(153, 6, 52, 18);
			jLabel5.setText("截止日期");
			jLabel5.setBounds(318, 6, 52, 18);
			jPanel2.add(jLabel3, null);
			jPanel2.add(jLabel4, null);
			jPanel2.add(getJCalendarComboBox(), null);
			jPanel2.add(jLabel5, null);
			jPanel2.add(getJCalendarComboBox1(), null);
			jPanel2.add(getJTextField(), null);
			jPanel2.add(getJButton());
		}
		return jPanel2;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getJCalendarComboBox() {
		if (jCalendarComboBox == null) {
			jCalendarComboBox = new JCalendarComboBox();
			jCalendarComboBox.setBounds(210, 6, 99, 22);
			jCalendarComboBox.setEnabled(false);
		}
		return jCalendarComboBox;
	}

	/**
	 * This method initializes jCalendarComboBox1
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getJCalendarComboBox1() {
		if (jCalendarComboBox1 == null) {
			jCalendarComboBox1 = new JCalendarComboBox();
			jCalendarComboBox1.setBounds(374, 6, 89, 22);
			jCalendarComboBox1.setEnabled(false);
		}
		return jCalendarComboBox1;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("统计");
			jButton1.setPreferredSize(new Dimension(65, 30));
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelTotal.getRowCount() > 0
							&& JOptionPane.showConfirmDialog(
									DgEmsAnalyPanD.this, "已经统计过分析总表，是否重新统计？",
									"确认", 2) == 2) {
						return;
					}
					new Jisuan().start();
				}
			});
		}
		return jButton1;
	}

	class Jisuan extends Thread {
		public void run() {
			try {
				CommonProgress.showProgressDialog(DgEmsAnalyPanD.this);
				CommonProgress.setMessage("系统正在统计资料，请稍后...");
				checkCancelAction.deleteEmsPdTotalAll(new Request(CommonVars
						.getCurrUser()), head);
				checkCancelAction.findPdExgImg(new Request(CommonVars
						.getCurrUser()), head);

				List list = checkCancelAction.findEmsPdTotal(new Request(
						CommonVars.getCurrUser()), head);
				if (list != null && list.size() > 0) {
					tableModelTotal = initTablePdAnalyTotal(tableModelTotal,
							tbTotalImg, list);
				} else {
					tableModelTotal = initTablePdAnalyTotal(tableModelTotal,
							tbTotalImg, new Vector());
				}
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgEmsAnalyPanD.this, "统计失败："
						+ e.getMessage(), "提示", 2);
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
			jButton2.setText("打印");
			jButton2.setPreferredSize(new Dimension(65, 30));
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelTotal != null
							&& tableModelTotal.getRowCount() > 0) {
						List list = checkCancelAction.findEmsPdTotal(
								new Request(CommonVars.getCurrUser()), head);
						CommonDataSource imgExgDS = new CommonDataSource(list);
						List company = new Vector();
						company.add(CommonVars.getCurrUser().getCompany());
						CommonDataSource companyDS = new CommonDataSource(
								company);

						InputStream masterReportStream = DgEmsAnalyBg.class
								.getResourceAsStream("report/pdDataSumReport.jasper");
						InputStream detailReportStream = DgEmsAnalyBg.class
								.getResourceAsStream("report/pdDataSumReportSubb.jasper");
						try {
							JasperReport detailReport = (JasperReport) JRLoader
									.loadObject(detailReportStream);

							Map parameters = new HashMap();
							parameters.put("imgExgDS", imgExgDS);
							parameters.put("detailReport", detailReport);
							parameters.put("reportTilte", DgEmsAnalyPanD.this
									.getJTextField().getText());
							JasperPrint jasperPrint;
							jasperPrint = JasperFillManager.fillReport(
									masterReportStream, parameters, companyDS);
							DgReportViewer viewer = new DgReportViewer(
									jasperPrint);
							viewer.setVisible(true);
						} catch (JRException e1) {
							e1.printStackTrace();
						}
					}
				}
			});
		}
		return jButton2;
	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
			jTabbedPane.addTab("料件盘点", null, getJPanel1(), null);
			jTabbedPane.addTab("成品盘点", null, getJPanel3(), null);
			jTabbedPane.addTab("成品折料", null, getJPanel4(), null);
			jTabbedPane.addTab("转厂分析", null, getPnTransfer(), null);
			jTabbedPane.addTab("分析总表", null, getJPanel5(), null);
			jTabbedPane
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {

							if (jTabbedPane.getSelectedIndex() == 2) {
								// JTableListModel[] exgImgModel =
								// initTableExgImg(tableModelExgImgS,jTable4,head,tableModelExgImgX,jTable5);
								/*
								 * tableModelExgImgS = exgImgModel[0]; //成品折料
								 * tableModelExgImgX = exgImgModel[1]; //料件
								 */}
						}
					});
		}
		return jTabbedPane;
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
			jPanel1.add(getJSplitPane1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel1;
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
			jPanel3.add(getJSplitPane2(), java.awt.BorderLayout.CENTER);
		}
		return jPanel3;
	}

	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jPanel4 = new JPanel();
			jPanel4.setLayout(new BorderLayout());
			jPanel4.add(getJSplitPane3(), java.awt.BorderLayout.CENTER);
		}
		return jPanel4;
	}

	/**
	 * This method initializes jPanel5
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel5() {
		if (jPanel5 == null) {
			jPanel5 = new JPanel();
			jPanel5.setLayout(new BorderLayout());
			jPanel5.add(getJScrollPane6(), java.awt.BorderLayout.CENTER);
			jPanel5.add(getJToolBar5(), BorderLayout.NORTH);
		}
		return jPanel5;
	}

	/**
	 * This method initializes jSplitPane1
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane1() {
		if (jSplitPane1 == null) {
			jSplitPane1 = new JSplitPane();
			jSplitPane1.setDividerSize(0);
			jSplitPane1.setDividerLocation(180);
			jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane1.setTopComponent(getJPanel6());
			jSplitPane1.setBottomComponent(getJPanel8());
		}
		return jSplitPane1;
	}

	/**
	 * This method initializes jPanel6
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel6() {
		if (jPanel6 == null) {
			jPanel6 = new JPanel();
			jPanel6.setLayout(new BorderLayout());
			jPanel6.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
			jPanel6.add(getJToolBar(), BorderLayout.NORTH);
		}
		return jPanel6;
	}

	/**
	 * This method initializes jPanel8
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel8() {
		if (jPanel8 == null) {
			jPanel8 = new JPanel();
			jPanel8.setLayout(new BorderLayout());
			jPanel8.add(getJToolBar1(), java.awt.BorderLayout.NORTH);
			jPanel8.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel8;
	}

	/**
	 * This method initializes tbPdImg
	 * 
	 * @return javax.swing.JTable
	 */
	private JFooterTable getTbPdImg() {
		if (tbPdImg == null) {
			tbPdImg = new JFooterTable();
			tbPdImg.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							/*
							 * if (e.getValueIsAdjusting()) { return; } if
							 * (tableModelImgS == null) return; EmsPdImg
							 * emsExgAfter = (EmsPdImg) tableModelImgS
							 * .getCurrentRow(); tableModelImgX =
							 * EmsLogic.initTablePdImgBg
							 * (tableModelImgX,jTable1,emsExgAfter);
							 */
						}
					});
		}
		return tbPdImg;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JFooterScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JFooterScrollPane();
			jScrollPane.setViewportView(getTbPdImg());
			jScrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(
					0, 0, 0, 0));
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jToolBar1
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar1() {
		if (jToolBar1 == null) {
			FlowLayout f = new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(1);
			f.setHgap(1);
			jToolBar1 = new JToolBar();
			jToolBar1.setPreferredSize(new Dimension(189, 34));
			jToolBar1.setLayout(f);
			jToolBar1.add(getBtnAllImgConver());
			jToolBar1.add(getBtnEditImgCus());
		}
		return jToolBar1;
	}

	/**
	 * This method initializes btnAllImgConver
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAllImgConver() {
		if (btnAllImgConver == null) {
			btnAllImgConver = new JButton();
			btnAllImgConver.setText("全部转换");
			btnAllImgConver.setPreferredSize(new Dimension(84, 30));
			btnAllImgConver
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (tableModelImgS.getRowCount() == 0) {
								return;
							}
							if (JOptionPane.showConfirmDialog(
									DgEmsAnalyPanD.this, "确定要重新转换吗？", "确认", 2) == 2) {
								return;
							}
							// 全部折成料件
							new Thread() {
								public void run() {
									try {
										btnAllImgConver.setEnabled(false);
										CommonStepProgress
												.showStepProgressDialog();
										CommonStepProgress
												.setStepMessage("系统正在删除全部转换的料件，请稍后...");
										checkCancelAction.deletePdImgBg(
												new Request(CommonVars
														.getCurrUser()), head);
										List selectList = checkCancelAction
												.findEmsPdImg(
														new Request(CommonVars
																.getCurrUser()),
														head);
										CommonStepProgress
												.setStepProgressMaximum(selectList
														.size());
										CommonStepProgress
												.setStepMessage("系统总有["
														+ selectList.size()
														+ "]开始进行料件转换，请稍后...");

										List<String> msgList = checkCancelAction.convertPdImgBg(
												new Request(CommonVars
														.getCurrUser()),
												head, selectList);
										
										// 有料号没有做归并，或者不在备案料件中
										if(msgList.size() > 0) {
											StringBuilder message = new StringBuilder("料号:");
											int i = 0;
											for (String msg : msgList) {
												if((i & 3) == 3) {
													message.append("\n");
												}
												
												message.append(msg + ",");
												
												i++;
											}
											
											DgErrorMessage.getInstance(message.toString());
											DgErrorMessage.showMessage();
										}
										
										
										// 显示盘点报关料件
										List list = checkCancelAction
												.findEmsPdImgBg(
														new Request(CommonVars
																.getCurrUser()),
														head);
										if (list != null && list.size() > 0) {
											tableModelImgX = initTablePdImgX(
													tableModelImgX,
													tfPdImgToBg, list);
										} else {
											tableModelImgX = initTablePdImgX(
													tableModelImgX,
													tfPdImgToBg, new Vector());
										}
										CommonStepProgress
												.closeStepProgressDialog();
									} catch (Exception exception) {
										CommonStepProgress
												.closeStepProgressDialog();
										exception.printStackTrace();
										JOptionPane.showMessageDialog(
												DgEmsAnalyPanD.this, ""
														+ exception
																.getMessage(),
												"提示", 2);
									} finally {
										CommonStepProgress
												.closeStepProgressDialog();
										btnAllImgConver.setEnabled(true);
									}
								}
							}.start();
						}
					});
		}
		return btnAllImgConver;
	}

	/**
	 * This method initializes btnImgAdd
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnImgAdd() {
		if (btnImgAdd == null) {
			btnImgAdd = new JButton();
			btnImgAdd.setText("增加");
			btnImgAdd.setPreferredSize(new Dimension(84, 30));
			btnImgAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List list = (List) CommonQueryPage.getInstance()
							.getMaterielAll(MaterielType.MATERIEL);
					if (list == null || list.size() <= 0) {
						return;
					}
					List alist = checkCancelAction.addEmsLjPd(new Request(
							CommonVars.getCurrUser()), list, head);
					tableModelImgS.addRows(alist);
				}
			});
		}
		return btnImgAdd;
	}

	/**
	 * This method initializes tfPdImgToBg
	 * 
	 * @return javax.swing.JTable
	 */
	private JFooterTable getTfPdImgToBg() {
		if (tfPdImgToBg == null) {
			tfPdImgToBg = new JFooterTable();
		}
		return tfPdImgToBg;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JFooterScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JFooterScrollPane();
			jScrollPane1.setViewportView(getTfPdImgToBg());
			jScrollPane1
					.setBorder(javax.swing.BorderFactory
							.createTitledBorder(
									null,
									"报关料件",
									javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
									javax.swing.border.TitledBorder.DEFAULT_POSITION,
									null, null));
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jSplitPane2
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane2() {
		if (jSplitPane2 == null) {
			jSplitPane2 = new JSplitPane();
			jSplitPane2.setDividerSize(0);
			jSplitPane2.setDividerLocation(180);
			jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane2.setTopComponent(getJPanel9());
			jSplitPane2.setBottomComponent(getJPanel10());
		}
		return jSplitPane2;
	}

	/**
	 * This method initializes jPanel9
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel9() {
		if (jPanel9 == null) {
			jPanel9 = new JPanel();
			jPanel9.setLayout(new BorderLayout());
			jPanel9.add(getJScrollPane2(), java.awt.BorderLayout.CENTER);
			jPanel9.add(getJToolBar4(), BorderLayout.NORTH);
		}
		return jPanel9;
	}

	/**
	 * This method initializes jPanel10
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel10() {
		if (jPanel10 == null) {
			jPanel10 = new JPanel();
			jPanel10.setLayout(new BorderLayout());
			jPanel10.add(getJToolBar2(), java.awt.BorderLayout.NORTH);
			jPanel10.add(getJScrollPane3(), java.awt.BorderLayout.CENTER);
		}
		return jPanel10;
	}

	/**
	 * This method initializes tbPdExg
	 * 
	 * @return javax.swing.JTable
	 */
	private JFooterTable getTbPdExg() {
		if (tbPdExg == null) {
			tbPdExg = new JFooterTable();
			tbPdExg.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							/*
							 * if (e.getValueIsAdjusting()) { return; } if
							 * (tableModelExgS == null) return; EmsPdExg
							 * emsExgAfter = (EmsPdExg) tableModelExgS
							 * .getCurrentRow(); tableModelExgX =
							 * EmsLogic.initTablePdExgBg
							 * (tableModelExgX,jTable3,emsExgAfter);
							 */
						}
					});
		}
		return tbPdExg;
	}

	/**
	 * This method initializes jScrollPane2
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JFooterScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JFooterScrollPane();
			jScrollPane2.setViewportView(getTbPdExg());
			jScrollPane2.setBorder(javax.swing.BorderFactory.createEmptyBorder(
					0, 0, 0, 0));
		}
		return jScrollPane2;
	}

	/**
	 * This method initializes jToolBar2
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar2() {
		if (jToolBar2 == null) {
			FlowLayout f = new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(1);
			f.setHgap(1);
			jToolBar2 = new JToolBar();
			jToolBar2.setPreferredSize(new Dimension(286, 34));
			jToolBar2.setLayout(f);
			jToolBar2.add(getBtnExgAllConver());
			jToolBar2.add(getBtnEditCusExg());
			jToolBar2.add(getBtnDeletePdExgToImg());
			jToolBar2.add(getBtnShowNoCus());
		}
		return jToolBar2;
	}

	/**
	 * This method initializes btnExgAllConver
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExgAllConver() {
		if (btnExgAllConver == null) {
			btnExgAllConver = new JButton();
			btnExgAllConver.setText("全部转换");
			btnExgAllConver.setPreferredSize(new Dimension(84, 30));
			btnExgAllConver
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (tableModelExgS.getRowCount() == 0) {
								return;
							}
							if (JOptionPane.showConfirmDialog(
									DgEmsAnalyPanD.this, "确定要重新转换吗？", "确认", 2) == 2) {
								return;
							}
							// 全部折成料件
							new Thread() {
								public void run() {
									try {
										btnExgAllConver.setEnabled(false);
										CommonStepProgress
												.showStepProgressDialog();
										CommonStepProgress
												.setStepMessage("系统正在删除全部转换的成品，请稍后...");
										checkCancelAction
												.deleteEmsPdExgImgBgAll(
														new Request(CommonVars
																.getCurrUser()),
														head);
										checkCancelAction.deletePdExgBg(
												new Request(CommonVars
														.getCurrUser()), head); // 删除报关成品
										List selectList = checkCancelAction
												.findEmsPdExg(
														new Request(CommonVars
																.getCurrUser()),
														head);
										CommonStepProgress
												.setStepProgressMaximum(selectList
														.size());
										CommonStepProgress
												.setStepMessage("系统总有["
														+ selectList.size()
														+ "]开始进行成品转换，请稍后...");

										for (int i = 0; i < selectList.size(); i++) {
											EmsPdExg exg = (EmsPdExg) selectList
													.get(i);
											CommonStepProgress
													.setStepProgressValue(i);
											CommonStepProgress
													.setStepMessage("系统正在成品转换 ［ "
															+ i
															+ " 条］ 数据，请稍后...");
											checkCancelAction.findPdExg(
													new Request(CommonVars
															.getCurrUser()),
													head, cbbMaxVersion
															.isSelected(), exg);
										}
										// 显示盘点报关成品
										List list = checkCancelAction
												.findEmsPdExgBg(
														new Request(CommonVars
																.getCurrUser()),
														head);
										if (list != null && list.size() > 0) {
											tableModelExgX = initTablePdExgX(
													tableModelExgX,
													tbPdExgToBg, list);
											tableModelExgImgS = initTablePdExgX(
													tableModelExgImgS, tbBgExg,
													list);
										} else {
											tableModelExgX = initTablePdExgX(
													tableModelExgX,
													tbPdExgToBg, new Vector());
											tableModelExgImgS = initTablePdExgX(
													tableModelExgImgS, tbBgExg,
													new Vector());
										}
										CommonStepProgress
												.closeStepProgressDialog();
									} catch (Exception exception) {
										CommonStepProgress
												.closeStepProgressDialog();
										exception.printStackTrace();
										// JOptionPane.showMessageDialog(
										// DgEmsAnalyPanD.this, "成品转换失败：！"
										// + exception
										// .getMessage(),
										// "提示", 2);
									} finally {
										CommonStepProgress
												.closeStepProgressDialog();
										btnExgAllConver.setEnabled(true);
									}
									// // 删除
									// try {
									// btnExgAllConver.setEnabled(false);
									// checkCancelAction.deleteEmsPdExgImgBgAll(
									// new Request(CommonVars.getCurrUser()),
									// head); // 删除全部折料
									// checkCancelAction.deletePdExgBg(new
									// Request(
									// CommonVars.getCurrUser()), head); //
									// 删除报关成品
									// checkCancelAction.findPdExg(new Request(
									// CommonVars.getCurrUser()), head,
									// cbbMaxVersion.isSelected());
									// // 显示盘点报关成品
									// List list =
									// checkCancelAction.findEmsPdExgBg(
									// new Request(CommonVars.getCurrUser()),
									// head);
									// if (list != null && list.size() > 0) {
									// tableModelExgX = initTablePdExgX(
									// tableModelExgX, tbPdExgToBg, list);
									// tableModelExgImgS = initTablePdExgX(
									// tableModelExgImgS, tbBgExg, list);
									// } else {
									// tableModelExgX = initTablePdExgX(
									// tableModelExgX, tbPdExgToBg,
									// new Vector());
									// tableModelExgImgS = initTablePdExgX(
									// tableModelExgImgS, tbBgExg,
									// new Vector());
									// // tableModelExgImgS =
									// //
									// initTablePdExgS(tableModelExgImgS,jTable4,new
									// // Vector());
									// }
									// } catch (Exception e1) {
									// JOptionPane.showMessageDialog(
									// DgEmsAnalyPanD.this, "转换错误："
									// + e1.getMessage(), "提示", 2);
									// } finally {
									// btnExgAllConver.setEnabled(true);
									// }

								}
							}.start();
						}
					});
		}
		return btnExgAllConver;
	}

	/**
	 * This method initializes btnExgAdd
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExgAdd() {
		if (btnExgAdd == null) {
			btnExgAdd = new JButton();
			btnExgAdd.setText("增加");
			btnExgAdd.setPreferredSize(new Dimension(84, 30));
			btnExgAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// tableModelExgS = EmsLogic.newPdExg(tableModelExgS,head);
					List list = (List) CommonQueryPage.getInstance()
							.getMaterielAll(MaterielType.FINISHED_PRODUCT);
					if (list == null || list.size() <= 0) {
						return;
					}
					List alist = checkCancelAction.addEmsCpPd(new Request(
							CommonVars.getCurrUser()), list, head);
					tableModelExgS.addRows(alist);

				}
			});
		}
		return btnExgAdd;
	}

	/**
	 * This method initializes btnExgEdit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExgEdit() {
		if (btnExgEdit == null) {
			btnExgEdit = new JButton();
			btnExgEdit.setText("修改");
			btnExgEdit.setPreferredSize(new Dimension(84, 30));
			btnExgEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelExgS.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgEmsAnalyPanD.this,
								"请选中要修改的成品！", "提示", 2);
						return;
					}
					DgPanDExgS dg = new DgPanDExgS();
					dg.setTableModel(tableModelExgS);
					dg.setVisible(true);
				}
			});
		}
		return btnExgEdit;
	}

	/**
	 * This method initializes btnExgDelete
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExgDelete() {
		if (btnExgDelete == null) {
			btnExgDelete = new JButton();
			btnExgDelete.setText("删除");
			btnExgDelete.setPreferredSize(new Dimension(84, 30));
			btnExgDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (JOptionPane.showConfirmDialog(DgEmsAnalyPanD.this,
							"确定要全部删除吗？", "确认", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
						return;
					}
					checkCancelAction.deleteEmsPdExgImgBgAll(new Request(
							CommonVars.getCurrUser()), head);
					checkCancelAction.deletePdExgBg(new Request(CommonVars
							.getCurrUser()), head);
					checkCancelAction.deleteEmsPdExgAll(new Request(CommonVars
							.getCurrUser()), head);

					tableModelExgS.setList(new ArrayList());
					tableModelExgX.setList(new ArrayList());
					tableModelExgImgS.setList(new ArrayList());
					tableModelExgImgX.setList(new ArrayList());
				}
			});
		}
		return btnExgDelete;
	}

	/**
	 * This method initializes tbPdExgToBg
	 * 
	 * @return javax.swing.JTable
	 */
	private JFooterTable getTbPdExgToBg() {
		if (tbPdExgToBg == null) {
			tbPdExgToBg = new JFooterTable();
		}
		return tbPdExgToBg;
	}

	/**
	 * This method initializes jScrollPane3
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JFooterScrollPane getJScrollPane3() {
		if (jScrollPane3 == null) {
			jScrollPane3 = new JFooterScrollPane();
			jScrollPane3.setViewportView(getTbPdExgToBg());
			jScrollPane3
					.setBorder(javax.swing.BorderFactory
							.createTitledBorder(
									null,
									"报关成品",
									javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
									javax.swing.border.TitledBorder.DEFAULT_POSITION,
									null, null));
		}
		return jScrollPane3;
	}

	/**
	 * This method initializes jSplitPane3
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane3() {
		if (jSplitPane3 == null) {
			jSplitPane3 = new JSplitPane();
			jSplitPane3.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane3.setDividerSize(0);
			jSplitPane3.setDividerLocation(180);
			jSplitPane3.setTopComponent(getJPanel11());
			jSplitPane3.setBottomComponent(getJPanel12());
		}
		return jSplitPane3;
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
			jPanel11.add(getJScrollPane4(), java.awt.BorderLayout.CENTER);
		}
		return jPanel11;
	}

	/**
	 * This method initializes jPanel12
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel12() {
		if (jPanel12 == null) {
			jPanel12 = new JPanel();
			jPanel12.setLayout(new BorderLayout());
			jPanel12.add(getJToolBar3(), java.awt.BorderLayout.NORTH);
			jPanel12.add(getJScrollPane5(), java.awt.BorderLayout.CENTER);
		}
		return jPanel12;
	}

	/**
	 * This method initializes tbBgExg
	 * 
	 * @return javax.swing.JTable
	 */
	private JFooterTable getTbBgExg() {
		if (tbBgExg == null) {
			tbBgExg = new JFooterTable();
			tbBgExg.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							if (tableModelExgImgS == null)
								return;
							//EmsPdExgBg emsExgAfter = (EmsPdExgBg) tableModelExgImgS
							//		.getCurrentRow();
							// initTablePdExgImgBg(tableModelExgImgX,jTable5,head);
						}
					});
		}
		return tbBgExg;
	}

	/**
	 * This method initializes jScrollPane4
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JFooterScrollPane getJScrollPane4() {
		if (jScrollPane4 == null) {
			jScrollPane4 = new JFooterScrollPane();
			jScrollPane4.setViewportView(getTbBgExg());
		}
		return jScrollPane4;
	}

	/**
	 * This method initializes jToolBar3
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar3() {
		if (jToolBar3 == null) {
			jToolBar3 = new JToolBar();
			jToolBar3.add(getBtnAllExgToImg());
		}
		return jToolBar3;
	}

	/**
	 * This method initializes btnAllExgToImg
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAllExgToImg() {
		if (btnAllExgToImg == null) {
			btnAllExgToImg = new JButton();
			btnAllExgToImg.setText("全部折成料件");
			btnAllExgToImg
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							new convertImg().start();
							// DgEmsAnalyPanDImgConvert dg = new
							// DgEmsAnalyPanDImgConvert();
							// dg.setVisible(true);
							// if (dg.isOk()) {
							// if (dg.isFromUnitWear()) {
							// new convertImg().start();
							// } else {
							//
							// }
							// }
						}
					});
		}
		return btnAllExgToImg;
	}

	// 全部折成料件
	class convertImg extends Thread {
		public void run() {
			try {
				btnAllExgToImg.setEnabled(false);
				List list = new ArrayList();
				List listUpdate = new ArrayList();
				CommonStepProgress.showStepProgressDialog();
				CommonStepProgress.setStepMessage("系统正在删除全部成料件，请稍后...");
				checkCancelAction.deleteEmsPdExgImgBgAll(new Request(CommonVars
						.getCurrUser()), head);
				List selectList = checkCancelAction.findEmsPdExgBgAll(
						new Request(CommonVars.getCurrUser()), head);// 得到所有盘点报关成品
				CommonStepProgress.setStepProgressMaximum(selectList.size());
				CommonStepProgress.setStepMessage("系统总有[" + selectList.size()
						+ "]开始进行折料，请稍后...");

				int i = 0;
				for (; i < selectList.size(); i++) {
					EmsPdExgBg exg = (EmsPdExgBg) selectList.get(i);
					CommonStepProgress.setStepProgressValue(i);
					listUpdate.add(exg);
					if (i % 50 == 0 && i != 0) {
						list.addAll(checkCancelAction.convertPdExgBgToPdExgImgBg(new Request(
								CommonVars.getCurrUser()), head, listUpdate));
						CommonStepProgress.setStepMessage("系统正在折算料件 ［ " + i
								+ " 条］ 数据");
						listUpdate.clear();
					}
				}
				if (listUpdate.size() > 0) {
					list.addAll(checkCancelAction.convertPdExgBgToPdExgImgBg(new Request(
							CommonVars.getCurrUser()), head, listUpdate));
					CommonStepProgress.setStepMessage("系统正在折算料件 ［ " + i
							+ " 条］ 数据");
					listUpdate.clear();
				}
				initTablePdExgImgX(tableModelExgImgX, tfBgExgToImg, list);
				tableModelExgImgX.getList().clear();
				tableModelExgImgX.setList(new Vector());
				tableModelExgImgX.addRows(list);
				CommonStepProgress.closeStepProgressDialog();
			} catch (Exception e) {
				CommonStepProgress.closeStepProgressDialog();
				e.printStackTrace();
				JOptionPane.showMessageDialog(DgEmsAnalyPanD.this, "统计失败：！"
						+ e.getMessage(), "提示", 2);
			} finally {
				CommonStepProgress.closeStepProgressDialog();
				btnAllExgToImg.setEnabled(true);
			}
		}
	}

	/**
	 * This method initializes tfBgExgToImg
	 * 
	 * @return javax.swing.JTable
	 */
	private JFooterTable getTfBgExgToImg() {
		if (tfBgExgToImg == null) {
			tfBgExgToImg = new JFooterTable();
		}
		return tfBgExgToImg;
	}

	/**
	 * This method initializes jScrollPane5
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JFooterScrollPane getJScrollPane5() {
		if (jScrollPane5 == null) {
			jScrollPane5 = new JFooterScrollPane();
			jScrollPane5.setViewportView(getTfBgExgToImg());
			jScrollPane5
					.setBorder(javax.swing.BorderFactory
							.createTitledBorder(
									null,
									"成品所折料件",
									javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
									javax.swing.border.TitledBorder.DEFAULT_POSITION,
									null, null));
		}
		return jScrollPane5;
	}

	/**
	 * This method initializes tbTotalImg
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbTotalImg() {
		if (tbTotalImg == null) {
			tbTotalImg = new JTable();
		}
		return tbTotalImg;
	}

	/**
	 * This method initializes jScrollPane6
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane6() {
		if (jScrollPane6 == null) {
			jScrollPane6 = new JScrollPane();
			jScrollPane6.setViewportView(getTbTotalImg());
		}
		return jScrollPane6;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setEditable(false);
			jTextField.setBounds(77, 5, 71, 22);
		}
		return jTextField;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("关闭");
			jButton.setBounds(new Rectangle(500, 2, 72, 25));
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgEmsAnalyPanD.this.dispose();
				}
			});
		}
		return jButton;
	}

	/**
	 * @return Returns the tableModel.
	 */
	public JTableListModel getTableModel() {
		return tableModel;
	}

	/**
	 * @param tableModel
	 *            The tableModel to set.
	 */
	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
	}

	/**
	 * This method initializes btnImgEdit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnImgEdit() {
		if (btnImgEdit == null) {
			btnImgEdit = new JButton();
			btnImgEdit.setText("修改");
			btnImgEdit.setPreferredSize(new Dimension(84, 30));
			btnImgEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelImgS.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgEmsAnalyPanD.this,
								"请选中要修改的料件！", "提示", 2);
						return;
					}
					DgPanDImgS dg = new DgPanDImgS();
					dg.setTableModel(tableModelImgS);
					dg.setVisible(true);
				}
			});
		}
		return btnImgEdit;
	}

	/**
	 * This method initializes btnImgDelete
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnImgDelete() {
		if (btnImgDelete == null) {
			btnImgDelete = new JButton();
			btnImgDelete.setText("删除");
			btnImgDelete.setPreferredSize(new Dimension(84, 30));
			btnImgDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (JOptionPane.showConfirmDialog(DgEmsAnalyPanD.this,
							"确定要全部删除吗？", "确认", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
						return;
					}
					checkCancelAction.deletePdImgBg(new Request(CommonVars
							.getCurrUser()), head); // 删除盘点料件报关EmsPdImgBg
					tableModelImgS.setList(new ArrayList());
					checkCancelAction.deleteEmsPdImgAll(new Request(CommonVars
							.getCurrUser()), head); // EmsPdImg
					tableModelImgX.setList(new ArrayList());

				}
			});
		}
		return btnImgDelete;
	}

	/**
	 * This method initializes btnEditImgCus
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEditImgCus() {
		if (btnEditImgCus == null) {
			btnEditImgCus = new JButton();
			btnEditImgCus.setText("修改报关料件");
			btnEditImgCus.setVisible(false);
			btnEditImgCus.setPreferredSize(new Dimension(84, 30));
			btnEditImgCus
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (tableModelImgX.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(
										DgEmsAnalyPanD.this, "请选中要修改的报关料件！",
										"提示", 2);
								return;
							}
							DgPanDImgX dg = new DgPanDImgX();
							dg.setTableModel(tableModelImgX);
							dg.setVisible(true);
						}
					});
		}
		return btnEditImgCus;
	}

	/**
	 * This method initializes btnEditCusExg
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEditCusExg() {
		if (btnEditCusExg == null) {
			btnEditCusExg = new JButton();
			btnEditCusExg.setText("修改报关成品");
			btnEditCusExg.setPreferredSize(new Dimension(84, 30));
			btnEditCusExg
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (tableModelExgX.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(
										DgEmsAnalyPanD.this, "请选择要修改的报关成品！",
										"提示", 2);
								return;
							}
							DgPanDExgX dg = new DgPanDExgX();
							dg.setTableModel(tableModelExgX);
							dg.setVisible(true);
						}
					});
		}
		return btnEditCusExg;
	}

	/**
	 * This method initializes btnShowNoCus
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnShowNoCus() {
		if (btnShowNoCus == null) {
			btnShowNoCus = new JButton();
			btnShowNoCus.setText("查看未备案信息");
			btnShowNoCus.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List returnList = checkCancelAction.findAllEmsPdExgNoEms(
							new Request(CommonVars.getCurrUser()), head);
					DgPdExgNoEms dg = new DgPdExgNoEms();
					dg.setReturnList(returnList);
					dg.setVisible(true);
				}
			});
		}
		return btnShowNoCus;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			FlowLayout f = new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(1);
			f.setHgap(1);
			jToolBar = new JToolBar();
			jToolBar.setPreferredSize(new Dimension(274, 34));
			jToolBar.setLayout(f);
			jToolBar.add(getBtnImgAdd());
			jToolBar.add(getBtnImgEdit());
			jToolBar.add(getBtnImgDelete());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jToolBar4
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar4() {
		if (jToolBar4 == null) {
			FlowLayout f = new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(1);
			f.setHgap(1);
			jToolBar4 = new JToolBar();
			jToolBar4.setPreferredSize(new Dimension(274, 34));
			jToolBar4.setLayout(f);
			jToolBar4.add(getBtnExgAdd());
			jToolBar4.add(getBtnExgEdit());
			jToolBar4.add(getBtnExgDelete());
			jToolBar4.add(getCbbMaxVersion());
		}
		return jToolBar4;
	}

	/**
	 * This method initializes jToolBar5
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar5() {
		if (jToolBar5 == null) {
			FlowLayout f = new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(1);
			f.setHgap(1);
			jToolBar5 = new JToolBar();
			jToolBar5.setLayout(f);
			jToolBar5.add(getJButton1());
			jToolBar5.add(getJButton2());
		}
		return jToolBar5;
	}

	/**
	 * This method initializes btnDeletePdExgToImg
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDeletePdExgToImg() {
		if (btnDeletePdExgToImg == null) {
			btnDeletePdExgToImg = new JButton();
			btnDeletePdExgToImg.setText("移出版本");
			btnDeletePdExgToImg.setPreferredSize(new Dimension(65, 30));
			btnDeletePdExgToImg
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (tableModelExgX == null
									|| tableModelExgX.getCurrentRow() == null) {
								return;
							}
							if (JOptionPane.showConfirmDialog(
									DgEmsAnalyPanD.this, "确定要删除选中行吗？", "确认",
									JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
								return;
							}
							checkCancelAction
									.deleteEmsPdExgImgBgAll(new Request(
											CommonVars.getCurrUser()), head);
							List oldList = tableModelExgX.getList();
							List selectList = tableModelExgX.getCurrentRows();
							for (int i = 0; i < selectList.size(); i++) {
								EmsPdExgBg obj = (EmsPdExgBg) selectList.get(i);
								checkCancelAction.deleteObject(new Request(
										CommonVars.getCurrUser()), obj);
								tableModelExgX.deleteRow(obj);
								oldList.remove(obj);
							}
							tableModelExgImgS.setList(oldList);
						}
					});
		}
		return btnDeletePdExgToImg;
	}

	/**
	 * This method initializes cbbMaxVersion
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbbMaxVersion() {
		if (cbbMaxVersion == null) {
			cbbMaxVersion = new JCheckBox();
			cbbMaxVersion.setText("以电子帐册成品单耗最大版本进行转换");
			cbbMaxVersion.setSelected(true);
		}
		return cbbMaxVersion;
	}
	private JPanel getPnTransfer() {
		if (pnTransfer == null) {
			pnTransfer = new JPanel();
			pnTransfer.setLayout(new BorderLayout(0, 0));
			pnTransfer.add(getSplitPane_1(), BorderLayout.CENTER);
		}
		return pnTransfer;
	}
	private JSplitPane getSplitPane_1() {
		if (slpnTransfer == null) {
			slpnTransfer = new JSplitPane();
			slpnTransfer.setOrientation(JSplitPane.VERTICAL_SPLIT);
			slpnTransfer.setDividerSize(0);
			slpnTransfer.setLeftComponent(getPanel_2());
			slpnTransfer.setRightComponent(getPanel_1_1());
			slpnTransfer.setDividerLocation(180);
		}
		return slpnTransfer;
	}
	private JPanel getPanel_2() {
		if (pnTransferNorth == null) {
			pnTransferNorth = new JPanel();
			pnTransferNorth.setLayout(new BorderLayout());
			pnTransferNorth.add(getTbTransferNorth(), BorderLayout.NORTH);
			pnTransferNorth.add(getSpTransferNorth(), BorderLayout.CENTER);
		}
		return pnTransferNorth;
	}
	private JPanel getPanel_1_1() {
		if (pnTransferCenter == null) {
			pnTransferCenter = new JPanel();
			pnTransferCenter.setLayout(new BorderLayout());
			pnTransferCenter.add(getTbTransferCenter(), BorderLayout.NORTH);
			pnTransferCenter.add(getSpTransferCenter(), BorderLayout.CENTER);
		}
		return pnTransferCenter;
	}
	private JToolBar getTbTransferNorth() {
		if (tbTransferNorth == null) {
			tbTransferNorth = new JToolBar();
			tbTransferNorth.setPreferredSize(new Dimension(274, 34));
			tbTransferNorth.add(getBtnTxtImportTransfer());
			tbTransferNorth.add(getBtnDeleteTransfer());
		}
		return tbTransferNorth;
	}
	private JScrollPane getSpTransferNorth() {
		if (spTransferNorth == null) {
			spTransferNorth = new JScrollPane();
			spTransferNorth.setViewportView(getTableTransferNorth());
		}
		return spTransferNorth;
	}
	private JTable getTableTransferNorth() {
		if (tableTransferNorth == null) {
			tableTransferNorth = new JTable();
		}
		return tableTransferNorth;
	}
	private JButton getBtnTxtImportTransfer() {
		if (btnTxtImportTransfer == null) {
			btnTxtImportTransfer = new JButton("文本导入");
			btnTxtImportTransfer.setMaximumSize(new Dimension(84, 30));
			btnTxtImportTransfer.setPreferredSize(new Dimension(84, 30));
			btnTxtImportTransfer.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					DgEmsTransFactoryImport dg = new DgEmsTransFactoryImport();
					dg.head = head;
					dg.setVisible(true);
					if(dg.getReturnList() != null && !dg.getReturnList().isEmpty()) {
						initTableTransNorth(dg.getReturnList());
					}
				}
			});
		}
		return btnTxtImportTransfer;
	}
	private JButton getBtnDeleteTransfer() {
		if (btnDeleteTransfer == null) {
			btnDeleteTransfer = new JButton("删除");
			btnDeleteTransfer.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (JOptionPane.showConfirmDialog(
							DgEmsAnalyPanD.this, "确定要全部删除吗？", "确认",
							JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
						return;
					}
					
					// 先删除报关盘点
					checkCancelAction.deleteEmsTransFactoryBg(new Request(CommonVars
							.getCurrUser()), head);
					initTableTransCenter(null);
					
					
					// 在删除导入数据
					List list = tableModelTransferNorth.getList();
					checkCancelAction.deleteEmsTransFactory(new Request(CommonVars
							.getCurrUser()), list);
					initTableTransNorth(null);
					
					
				}
			});
			btnDeleteTransfer.setMaximumSize(new Dimension(84, 30));
			btnDeleteTransfer.setPreferredSize(new Dimension(84, 30));
		}
		return btnDeleteTransfer;
	}
	private JToolBar getTbTransferCenter() {
		if (tbTransferCenter == null) {
			tbTransferCenter = new JToolBar();
			tbTransferCenter.setPreferredSize(new Dimension(274, 34));
			tbTransferCenter.add(getBtnTransferConvertAll());
		}
		return tbTransferCenter;
	}
	private JScrollPane getSpTransferCenter() {
		if (spTransferCenter == null) {
			spTransferCenter = new JScrollPane();
			spTransferCenter.setBorder(new TitledBorder(null, "\u62A5\u5173\u8D44\u6599", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			spTransferCenter.setViewportView(getTableTransferCenter());
		}
		return spTransferCenter;
	}
	private JTable getTableTransferCenter() {
		if (tableTransferCenter == null) {
			tableTransferCenter = new JTable();
		}
		return tableTransferCenter;
	}
	private JButton getBtnTransferConvertAll() {
		if (btnTransferConvertAll == null) {
			btnTransferConvertAll = new JButton("全部转化");
			btnTransferConvertAll.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (JOptionPane.showConfirmDialog(
							DgEmsAnalyPanD.this, "确定全部转化吗？", "确认",
							JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
						return;
					}
					
					checkCancelAction.convertEmsTransFactoryToBg(new Request(CommonVars
							.getCurrUser()), head, tableModelTransferNorth.getList());
					
					initTableTransCenter(checkCancelAction.findEmsTransFactoryBg(new Request(CommonVars
							.getCurrUser()), head));
				}
			});
			btnTransferConvertAll.setPreferredSize(new Dimension(84, 30));
			btnTransferConvertAll.setMaximumSize(new Dimension(84, 30));
		}
		return btnTransferConvertAll;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
