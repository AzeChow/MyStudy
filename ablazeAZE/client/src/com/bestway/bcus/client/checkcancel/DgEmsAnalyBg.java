/*
 * Created on 2005-4-6
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.checkcancel;

import java.awt.BorderLayout;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ExecutionException;

import javax.swing.ImageIcon;
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
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import com.bestway.bcus.checkcancel.action.CheckCancelAction;
import com.bestway.bcus.checkcancel.entity.EmsAnalyHead;
import com.bestway.bcus.checkcancel.entity.EmsBgExg;
import com.bestway.bcus.client.common.CommonDataSource;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.footer.JFooterScrollPane;
import com.bestway.client.util.footer.JFooterTable;
import com.bestway.common.Condition;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpType;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author xxm
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class DgEmsAnalyBg extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;
	private JPanel jPanel = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JLabel jLabel2 = null;
	private JPanel jPanel2 = null;
	private JLabel jLabel3 = null;
	private JLabel jLabel4 = null;
	private JCalendarComboBox jCalendarComboBox = null;
	private JLabel jLabel5 = null;
	private JCalendarComboBox jCalendarComboBox1 = null;
	private JButton jButton1 = null;
	private JButton jButton2 = null;
	private JSplitPane jSplitPane = null;
	private JPanel jPanel7 = null;
	private JTabbedPane jTabbedPane = null;
	private JPanel jPanel1 = null;
	private JPanel jPanel3 = null;
	private JPanel jPanel4 = null;
	private JToolBar jToolBar = null;
	private JCheckBox jCheckBox = null;
	private JLabel jLabel6 = null;
	private JFooterTable tbBgImg = null;
	private JFooterScrollPane jScrollPane = null;
	private JSplitPane jSplitPane1 = null;
	private JPanel jPanel5 = null;
	private JPanel jPanel6 = null;
	private JToolBar jToolBar1 = null;
	private JButton btnAllExgToImg = null;
	private JFooterTable tbBgExgToImg = null;
	private JFooterScrollPane jScrollPane1 = null;
	private JFooterTable tbBgExg = null;
	private JFooterScrollPane jScrollPane2 = null;
	private JTable jTable3 = null;
	private JScrollPane jScrollPane3 = null;
	private JButton jButton5 = null;
	private JTextField jTextField = null;
	private JTableListModel tableModel = null;
	private JTableListModel tableModelImg = null; // 料件
	private JTableListModel tableModelExgS = null; // 成品
	private JTableListModel tableModelExgX = null;
	private JTableListModel tableModelTotal = null; // 总表
	private EmsAnalyHead head = null;
	private CheckCancelAction checkCancelAction = null;
	private JButton btnSingExgToImg = null;
	private JButton btnShowAllExgToImg = null;

	/**
	 * This is the default constructor
	 */
	public DgEmsAnalyBg() {
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
		this.setSize(834, 550);
		this.setContentPane(getJContentPane());
		this.setTitle("报关材料/工厂实物帐面分析");
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {
				if (tableModel.getCurrentRow() != null) {
					head = (EmsAnalyHead) tableModel.getCurrentRow();
					fillWindow();
					tableModelImg = EmsLogic.initTableBgImg(tableModelImg,
							tbBgImg, head);
					tableModelExgS = EmsLogic.initTableBgExgS(tableModelExgS,
							tbBgExg, head);
					String exgId = null;
					if (tableModelExgS.getCurrentRow() != null) {
						EmsBgExg emsBgExg = (EmsBgExg) tableModelExgS
								.getCurrentRow();
						exgId = emsBgExg.getId();
					}
					tableModelExgX = EmsLogic.initTableBgExgX(tableModelExgX,
							tbBgExgToImg, head, exgId);
					tableModelTotal = EmsLogic.initTableBgTotal(
							tableModelTotal, jTable3, head); // 总表
					setState();
				}
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

	private void setState() {
		jButton2.setEnabled(this.jTabbedPane.getSelectedIndex() == 2);
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
			jContentPane.add(getJPanel(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
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
			jLabel = new JLabel();
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jLabel.setText("报关材料/工厂实物帐面分析----报关数据分析");
			jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
			jLabel.setForeground(new java.awt.Color(0, 102, 51));
			jLabel1.setText("");
			jLabel1.setIcon(new ImageIcon(getClass().getResource(
					"/com/bestway/bcus/client/resources/images/titlepic.jpg")));
			jLabel2.setText("");
			jLabel2
					.setIcon(new ImageIcon(
							getClass()
									.getResource(
											"/com/bestway/bcus/client/resources/images/titlepoint.jpg")));
			jPanel.setBackground(java.awt.Color.white);
			jPanel.add(jLabel, java.awt.BorderLayout.CENTER);
			jPanel.add(jLabel1, java.awt.BorderLayout.EAST);
			jPanel.add(jLabel2, java.awt.BorderLayout.WEST);
		}
		return jPanel;
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
			jLabel3.setText("核销流水号:");
			jLabel3.setBounds(5, 5, 67, 18);
			jLabel4.setText("起始日期");
			jLabel4.setBounds(148, 6, 52, 18);
			jLabel5.setText("截止日期");
			jLabel5.setBounds(300, 7, 52, 18);
			jPanel2.add(jLabel3, null);
			jPanel2.add(jLabel4, null);
			jPanel2.add(getJTextField(), null);
			jPanel2.add(getJCalendarComboBox(), null);
			jPanel2.add(jLabel5, null);
			jPanel2.add(getJCalendarComboBox1(), null);
			jPanel2.add(getJButton1(), null);
			jPanel2.add(getJButton2(), null);
			jPanel2.add(getJButton5(), null);
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
			jCalendarComboBox.setBounds(205, 6, 91, 22);
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
			jCalendarComboBox1.setBounds(355, 6, 91, 22);
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
			jButton1.setBounds(460, 4, 80, 25);
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (DgEmsAnalyBg.this.jTabbedPane.getSelectedIndex() == 0) { // 进口料件
						if (tableModelImg.getRowCount() > 0
								&& JOptionPane.showConfirmDialog(
										DgEmsAnalyBg.this,
										"已经统计过进出口原材料，确定要重新统计吗？", "确认", 2) == 2) {
							return;
						}
					} else if (DgEmsAnalyBg.this.jTabbedPane.getSelectedIndex() == 1) {
						if (tableModelExgS.getRowCount() > 0
								&& JOptionPane.showConfirmDialog(
										DgEmsAnalyBg.this,
										"已经统计过进出成品，确定要重新统计吗？", "确认", 2) == 2) {
							return;
						}
					} else {
						if (tableModelTotal.getRowCount() > 0
								&& JOptionPane.showConfirmDialog(
										DgEmsAnalyBg.this,
										"已经统计过进出口总表，确定要重新统计吗？", "确认", 2) == 2) {
							return;
						}
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
				CommonProgress.showProgressDialog(DgEmsAnalyBg.this);
				CommonProgress.setMessage("系统正在统计资料，请稍后...");

				if (DgEmsAnalyBg.this.jTabbedPane.getSelectedIndex() == 0) { // 进口料件
					// 删除tableModelImg
					checkCancelAction.deleteEmsBgImgAll(new Request(CommonVars
							.getCurrUser()), head);
					List conditions = getConditions(0);
					checkCancelAction.calculateBgImg(new Request(CommonVars
							.getCurrUser()), conditions, head);
					tableModelImg = EmsLogic.initTableBgImg(tableModelImg,
							tbBgImg, head);
					CommonProgress.closeProgressDialog();
					if (tableModelImg.getRowCount() == 0) {
						JOptionPane.showMessageDialog(DgEmsAnalyBg.this,
								"统计数据为空！", "提示", 2);
					}
				} else if (DgEmsAnalyBg.this.jTabbedPane.getSelectedIndex() == 1) { // 出口成品
					// 删除tableModelExgS
					checkCancelAction.deleteEmsBgExgImgAll(new Request(
							CommonVars.getCurrUser()), head, "", true);
					checkCancelAction.deleteEmsBgExgAll(new Request(CommonVars
							.getCurrUser()), head);
					List conditions = getConditions(1);
					checkCancelAction.calculateBgExg(new Request(CommonVars
							.getCurrUser()), conditions, head);
					tableModelExgS = EmsLogic.initTableBgExgS(tableModelExgS,
							tbBgExg, head);
					CommonProgress.closeProgressDialog();
					if (tableModelExgS.getRowCount() == 0) {
						JOptionPane.showMessageDialog(DgEmsAnalyBg.this,
								"统计数据为空！", "提示", 2);
					}
				} else {
					// 删除tableModelTotal
					checkCancelAction.deleteEmsBgTotal(new Request(CommonVars
							.getCurrUser()), head);
					List list = checkCancelAction.findBgExgImg(new Request(
							CommonVars.getCurrUser()), head);
					tableModelTotal = EmsLogic.initTableBgTotal(
							tableModelTotal, jTable3, head); // 总表
					CommonProgress.closeProgressDialog();
					if (tableModelTotal.getRowCount() == 0) {
						JOptionPane.showMessageDialog(DgEmsAnalyBg.this,
								"统计数据为空！", "提示", 2);
					}
				}
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				e.printStackTrace();
				// JOptionPane.showMessageDialog(DgEmsAnalyBg.this, "统计失败：！"
				// + e.getMessage(), "提示", 2);
			}
		}
	}

	private List getConditions(int i) {
		List<Object> conditions = new Vector<Object>();
		if (i == 0) { // 料件
			conditions.add(new Condition("and", "(",
					"baseCustomsDeclaration.impExpType", "=",
					ImpExpType.DIRECT_IMPORT, null));
			conditions.add(new Condition("or", null,
					"baseCustomsDeclaration.impExpType", "=",
					ImpExpType.TRANSFER_FACTORY_IMPORT, null));
			conditions.add(new Condition("or", null,
					"baseCustomsDeclaration.impExpType", "=",
					ImpExpType.BACK_MATERIEL_EXPORT, null));
			conditions.add(new Condition("or", null,
					"baseCustomsDeclaration.impExpType", "=",
					ImpExpType.REMAIN_FORWARD_IMPORT, null));
			conditions.add(new Condition("or", null,
					"baseCustomsDeclaration.impExpType", "=",
					ImpExpType.REMAIN_FORWARD_EXPORT, ")"));
		} else {
			conditions.add(new Condition("and", "(",
					"baseCustomsDeclaration.impExpType", "=",
					ImpExpType.DIRECT_EXPORT, null));
			conditions.add(new Condition("or", null,
					"baseCustomsDeclaration.impExpType", "=",
					ImpExpType.TRANSFER_FACTORY_EXPORT, null));
			conditions.add(new Condition("or", null,
					"baseCustomsDeclaration.impExpType", "=",
					ImpExpType.BACK_FACTORY_REWORK, ")"));
		}
		conditions.add(new Condition("and", null,
				"baseCustomsDeclaration.declarationDate", ">=", CommonVars
						.dateToStandDate(jCalendarComboBox.getDate()), null));
		conditions.add(new Condition("and", null,
				"baseCustomsDeclaration.declarationDate", "<=", CommonVars
						.dateToStandDate(jCalendarComboBox1.getDate()), null));
		conditions.add(new Condition("and", null,
				"baseCustomsDeclaration.effective", "=", true, null));
		conditions.add(new Condition("and", null,
				"baseCustomsDeclaration.company.id", "=", CommonVars
						.getCurrUser().getCompany().getId(), null));
		return conditions;
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
			jButton2.setBounds(549, 4, 80, 25);
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelTotal != null
							&& tableModelTotal.getRowCount() > 0) {
						List list = checkCancelAction.findEmsBgTotal(
								new Request(CommonVars.getCurrUser()), head);
						CommonDataSource imgExgDS = new CommonDataSource(list);
						List company = new Vector();
						company.add(CommonVars.getCurrUser().getCompany());
						CommonDataSource companyDS = new CommonDataSource(
								company);

						InputStream masterReportStream = DgEmsAnalyBg.class
								.getResourceAsStream("report/bgDataSumReport.jasper");
						InputStream detailReportStream = DgEmsAnalyBg.class
								.getResourceAsStream("report/bgDataSumReportSubb.jasper");
						try {
							JasperReport detailReport = (JasperReport) JRLoader
									.loadObject(detailReportStream);

							Map parameters = new HashMap();
							parameters.put("imgExgDS", imgExgDS);
							parameters.put("detailReport", detailReport);
							parameters.put("reportTilte", DgEmsAnalyBg.this
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
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setDividerSize(0);
			jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setBottomComponent(getJPanel7());
			jSplitPane.setDividerLocation(35);
			jSplitPane.setTopComponent(getJPanel2());
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jPanel7
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel7() {
		if (jPanel7 == null) {
			jPanel7 = new JPanel();
			jPanel7.setLayout(new BorderLayout());
			jPanel7.add(getJTabbedPane(), java.awt.BorderLayout.CENTER);
			jPanel7.add(getJToolBar(), java.awt.BorderLayout.NORTH);
		}
		return jPanel7;
	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.addTab("原料进出口", null, getJPanel1(), null);
			jTabbedPane.addTab("成品进出口", null, getJPanel3(), null);
			jTabbedPane.addTab("进出口总表", null, getJPanel4(), null);
			jTabbedPane.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
			jTabbedPane
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							setState();
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
			jPanel1.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
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
			jPanel3.add(getJSplitPane1(), java.awt.BorderLayout.CENTER);
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
			jPanel4.add(getJScrollPane3(), java.awt.BorderLayout.CENTER);
		}
		return jPanel4;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.setFloatable(false);
			jToolBar.add(getJCheckBox());
			jLabel6 = new JLabel();
			jToolBar.add(jLabel6);
			jLabel6.setText("  所有料件，成品均为报关商品   ");
		}
		return jToolBar;
	}

	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			jCheckBox = new JCheckBox();
			jCheckBox.setText("所有成品都已经折料");
			jCheckBox.setVisible(false);
		}
		return jCheckBox;
	}

	/**
	 * This method initializes tbBgImg
	 * 
	 * @return javax.swing.JTable
	 */
	private JFooterTable getTbBgImg() {
		if (tbBgImg == null) {
			tbBgImg = new JFooterTable();
		}
		return tbBgImg;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JFooterScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JFooterScrollPane();
			jScrollPane.setViewportView(getTbBgImg());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jSplitPane1
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane1() {
		if (jSplitPane1 == null) {
			jSplitPane1 = new JSplitPane();
			jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane1.setDividerSize(0);
			jSplitPane1.setDividerLocation(160);
			jSplitPane1.setTopComponent(getJPanel5());
			jSplitPane1.setBottomComponent(getJPanel6());
		}
		return jSplitPane1;
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
			jPanel5.add(getJScrollPane2(), java.awt.BorderLayout.CENTER);
		}
		return jPanel5;
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
			jPanel6.add(getJToolBar1(), java.awt.BorderLayout.NORTH);
			jPanel6.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel6;
	}

	/**
	 * This method initializes jToolBar1
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar1() {
		if (jToolBar1 == null) {
			jToolBar1 = new JToolBar();
			jToolBar1.add(getBtnSingExgToImg());
			jToolBar1.add(getBtnAllExgToImg());
			jToolBar1.add(getBtnShowAllExgToImg());
		}
		return jToolBar1;
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
							if (tableModelExgS.getCurrentRow() == null) {
								return;
							}
//							EmsBgExg emsBgExg = (EmsBgExg) tableModelExgS
//									.getCurrentRow();
//							new convertImg(emsBgExg.getId(), true).start();
							
							SwingUtilities.invokeLater(new Runnable() {
								@Override
								public void run() {
									ExgConvertImg img = new ExgConvertImg();
									img.execute();
								}
							});
						}
					});
		}
		return btnAllExgToImg;
	}
	
	class ExgConvertImg extends SwingWorker{

		@Override
		protected Object doInBackground() throws Exception {
			CommonProgress.showProgressDialog(DgEmsAnalyBg.this);
			CommonProgress.setMessage("系统正在折成料件，请稍后...");
			
			checkCancelAction.deleteEmsBgExgImgAll(new Request(
					CommonVars.getCurrUser()), head, "", true);
			checkCancelAction.convertBgExgToImg(new Request(CommonVars.getCurrUser()),head);
			EmsBgExg emsExgAfter = (EmsBgExg) tableModelExgS
					.getCurrentRow();
			return emsExgAfter;
		}

		@Override
		protected void done() {
			try {
				EmsBgExg emsExgAfter = (EmsBgExg)get();
				tableModelExgX = EmsLogic.initTableBgExgX(
						tableModelExgX, tbBgExgToImg, head,
						emsExgAfter.getId());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}finally{
				CommonProgress.closeProgressDialog();
			}
			
		}
	}
	

	class convertImg extends Thread {

		boolean isAllOrSing = false;
		String emsBgExgId = "";

		public convertImg(String emsBgExgId, boolean isAllOrSing) {
			this.isAllOrSing = isAllOrSing;
			this.emsBgExgId = emsBgExgId;
		}

		public void run() {
			try {
				CommonProgress.showProgressDialog(DgEmsAnalyBg.this);
				CommonProgress.setMessage("系统正在折成料件，请稍后...");
				long b = System.currentTimeMillis();
				System.out.println("开始删除EmsBgExgBg。");
				if (isAllOrSing) {
					checkCancelAction.deleteEmsBgExgImgAll(new Request(
							CommonVars.getCurrUser()), head, "", isAllOrSing);
				} else {
					checkCancelAction.deleteEmsBgExgImgAll(new Request(
							CommonVars.getCurrUser()), head, emsBgExgId, isAllOrSing);
				}
				long e = System.currentTimeMillis();
				System.out.println("删除EmsBgExgBg时间：" + (e-b)/1000.0);
				
				checkCancelAction.convertBgExgToImg(
						new Request(CommonVars.getCurrUser()), head,
						emsBgExgId, isAllOrSing);
				tableModelExgX = EmsLogic.initTableBgExgX(tableModelExgX,
						tbBgExgToImg, head, emsBgExgId);
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgEmsAnalyBg.this, "折成料件失败：！"
						+ e.getMessage(), "提示", 2);
			}
		}
	}

	/**
	 * This method initializes tbBgExgToImg
	 * 
	 * @return javax.swing.JTable
	 */
	private JFooterTable getTbBgExgToImg() {
		if (tbBgExgToImg == null) {
			tbBgExgToImg = new JFooterTable();
		}
		return tbBgExgToImg;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JFooterScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JFooterScrollPane();
			jScrollPane1.setViewportView(getTbBgExgToImg());
		}
		return jScrollPane1;
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
							if (tableModelExgS == null
									|| tableModelExgS.getCurrentRow() == null) {
								return;
							}
							EmsBgExg emsExgAfter = (EmsBgExg) tableModelExgS
									.getCurrentRow();
							tableModelExgX = EmsLogic.initTableBgExgX(
									tableModelExgX, tbBgExgToImg, head,
									emsExgAfter.getId());
						}
					});
		}
		return tbBgExg;
	}

	/**
	 * This method initializes jScrollPane2
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JFooterScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JFooterScrollPane();
			jScrollPane2.setViewportView(getTbBgExg());
		}
		return jScrollPane2;
	}

	/**
	 * This method initializes jTable3
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable3() {
		if (jTable3 == null) {
			jTable3 = new JTable();
		}
		return jTable3;
	}

	/**
	 * This method initializes jScrollPane3
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane3() {
		if (jScrollPane3 == null) {
			jScrollPane3 = new JScrollPane();
			jScrollPane3.setViewportView(getJTable3());
		}
		return jScrollPane3;
	}

	/**
	 * This method initializes jButton5
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton5() {
		if (jButton5 == null) {
			jButton5 = new JButton();
			jButton5.setBounds(643, 4, 80, 25);
			jButton5.setText("关闭");
			jButton5.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgEmsAnalyBg.this.dispose();
				}
			});
		}
		return jButton5;
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
			jTextField.setBounds(76, 5, 71, 22);
		}
		return jTextField;
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
	 * This method initializes btnSingExgToImg
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSingExgToImg() {
		if (btnSingExgToImg == null) {
			btnSingExgToImg = new JButton();
			btnSingExgToImg.setText("单个成品折料");
			btnSingExgToImg
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (tableModelExgS.getCurrentRow() == null) {
								return;
							}
							EmsBgExg emsBgExg = (EmsBgExg) tableModelExgS
									.getCurrentRow();
							new convertImg(emsBgExg.getId(), false).start();
						}
					});
		}
		return btnSingExgToImg;
	}

	/**
	 * This method initializes btnShowAllExgToImg	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnShowAllExgToImg() {
		if (btnShowAllExgToImg == null) {
			btnShowAllExgToImg = new JButton();
			btnShowAllExgToImg.setText("显示全部折料");
			btnShowAllExgToImg.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					tableModelExgX = EmsLogic.initTableBgExgX(tableModelExgX,
							tbBgExgToImg, head, "");
				}
			});
		}
		return btnShowAllExgToImg;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
