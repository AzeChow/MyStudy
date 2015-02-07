/*
 * Created on 2004-9-29
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.cas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.entity.StatCusNameRelationMt;
import com.bestway.bcus.client.common.CommonDataSource;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.CommonUtils;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import java.awt.Dimension;

/**
 * @author ls
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgStoreInfo extends JDialogBase {

	private JPanel				jPanel			= null;
	private JTable				jTable			= null;
	private JScrollPane			jScrollPane		= null;
	private JLabel				jLabel			= null;
	private JCalendarComboBox	cbbEndDate		= null;
	private JComboBox			cmbType			= null;
	private JButton				btnSearch		= null;
	private JButton				btnPrint		= null;
	JTableListModel				tableModel		= null;
	CasAction					casAction		= null;
	private JPanel				jContentPane	= null;
	private JToolBar			jToolBar		= null;
	private JButton				btnExit			= null;
	private JTextField			tfPtNo			= null;
	private JTextField			tfEndPtNo		= null;
	private JButton				btnPtNo			= null;
	private JButton				btnEndPtNo		= null;
	private JPanel				jPanel1			= null;
	private JLabel				jLabel2			= null;
	private JToolBar			jToolBar1		= null;
	private JPanel				jPanel2			= null;
	private JLabel				jLabel1			= null;
	private JLabel				jLabel3			= null;
	private JCalendarComboBox	cbbBeginDate	= null;

	/**
	 * This method initializes
	 * 
	 */
	public DgStoreInfo() {
		super(false);
		initialize();
		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
				"casAction");
		initUIComponents();
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	public void setVisible(boolean isFlag) {
		if (isFlag) {

		}
		super.setVisible(isFlag);
	}

	private void initUIComponents() {
		int year = com.bestway.bcus.client.cas.parameter.CasCommonVars
				.getYearInt();
		Calendar beginClarendar = Calendar.getInstance();
		beginClarendar.set(Calendar.YEAR, year);
		beginClarendar.set(Calendar.MONTH, 0);
		beginClarendar.set(Calendar.DAY_OF_MONTH, 1);
		this.cbbBeginDate.setDate(beginClarendar.getTime());
		this.cbbBeginDate.setCalendar(beginClarendar);

		Calendar endClarendar = Calendar.getInstance();
		endClarendar.set(Calendar.YEAR, year);
		endClarendar.set(Calendar.MONTH, 11);
		endClarendar.set(Calendar.DAY_OF_MONTH, 31);
		this.cbbEndDate.setDate(endClarendar.getTime());
		this.cbbEndDate.setCalendar(endClarendar);

		cmbType.addItem(new ItemProperty(MaterielType.MATERIEL, "料件"));
		cmbType.addItem(new ItemProperty(MaterielType.FINISHED_PRODUCT, "成品"));
		cmbType.addItem(new ItemProperty(MaterielType.MACHINE, "设备"));
		cmbType.setSelectedIndex(0);
		initTable(new ArrayList());

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("料件，成品，设备库存情况统计表");
		this.setContentPane(getJContentPane());
		this.setSize(733, 541);

	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel3 = new JLabel();
			jLabel3.setBounds(new java.awt.Rectangle(201, 3, 56, 23));
			jLabel3.setText("开始日期");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new java.awt.Rectangle(7, 4, 58, 23));
			jLabel2.setText("单据类型");
			jLabel = new JLabel();
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jLabel.setText("截止日期");
			jLabel.setBounds(new java.awt.Rectangle(356, 3, 48, 23));
			jPanel.add(jLabel, null);
			jPanel.add(getCbbEndDate(), null);
			jPanel.add(getCmbType(), null);
			jPanel.add(jLabel2, null);
			jPanel.add(jLabel3, null);
			jPanel.add(getCbbBeginDate(), null);
		}
		return jPanel;
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
	 * This method initializes cbbEndDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbEndDate() {
		if (cbbEndDate == null) {
			cbbEndDate = new JCalendarComboBox();
			cbbEndDate.setPreferredSize(new java.awt.Dimension(98, 22));
			cbbEndDate.setBounds(new java.awt.Rectangle(407, 3, 91, 23));
		}
		return cbbEndDate;
	}

	/**
	 * This method initializes cmbType
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCmbType() {
		if (cmbType == null) {
			cmbType = new JComboBox();
			cmbType.setPreferredSize(new java.awt.Dimension(100, 22));
			cmbType.setBounds(new java.awt.Rectangle(69, 3, 117, 23));
		}
		return cmbType;
	}

	/**
	 * This method initializes btnSearch
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSearch() {
		if (btnSearch == null) {
			btnSearch = new JButton();
			btnSearch.setText("查询");
			btnSearch.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					search();

				}

			});
		}
		return btnSearch;
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
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					printReport();
				}

			});
		}
		return btnPrint;
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
			jContentPane.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
			jContentPane.add(getJPanel1(), java.awt.BorderLayout.NORTH);
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
			jToolBar.add(getBtnSearch());
			jToolBar.add(getBtnPrint());
			jToolBar.add(getBtnExit());
		}
		return jToolBar;
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
					dispose();
				}
			});
		}
		return btnExit;
	}

	/**
	 * This method initializes tfPtNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfPtNo() {
		if (tfPtNo == null) {
			tfPtNo = new JTextField();
			tfPtNo.setPreferredSize(new java.awt.Dimension(100, 23));
			tfPtNo.getDocument().addDocumentListener(new DocumentListener() {
				private void setState() {
					String editAfterQueryValue = tfPtNo.getText().trim();
					if (!"".equalsIgnoreCase(editAfterQueryValue)) {
						tfEndPtNo.setEditable(true);
						btnEndPtNo.setEnabled(true);
					} else {
						tfEndPtNo.setEditable(false);
						btnEndPtNo.setEnabled(false);
					}
				}

				public void insertUpdate(DocumentEvent e) {
					setState();
				}

				public void removeUpdate(DocumentEvent e) {
					setState();
				}

				public void changedUpdate(DocumentEvent e) {
					setState();
				}

			});
		}
		return tfPtNo;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPtNo() {
		if (btnPtNo == null) {
			btnPtNo = new JButton();
			btnPtNo.setText("...");
			btnPtNo.setPreferredSize(new java.awt.Dimension(22, 22));
			btnPtNo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String type = ((ItemProperty) cmbType.getSelectedItem())
							.getCode();
					List<StatCusNameRelationMt> list = CasQuery.getInstance()
							.getStatCusNameRelationMt(false, type,
									new ArrayList());
					if (list != null && list.size() > 0) {
						StatCusNameRelationMt sm = list.get(0);
						Materiel m = sm.getMateriel();
						tfPtNo.setText(m == null ? "" : m.getPtNo());
					}
				}
			});
		}
		return btnPtNo;
	}

	/**
	 * This method initializes tfEndPtNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getEndPtNo() {
		if (tfEndPtNo == null) {
			tfEndPtNo = new JTextField();
			tfEndPtNo.setEditable(false);
			tfEndPtNo.setPreferredSize(new java.awt.Dimension(100, 23));
		}
		return tfEndPtNo;
	}

	/**
	 * This method initializes btnEndPtNo
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEndPtNo() {
		if (btnEndPtNo == null) {
			btnEndPtNo = new JButton();
			btnEndPtNo.setEnabled(false);
			btnEndPtNo.setPreferredSize(new java.awt.Dimension(22, 22));
			btnEndPtNo.setText("...");
			btnEndPtNo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String type = ((ItemProperty) cmbType.getSelectedItem())
							.getCode();
					List<StatCusNameRelationMt> list = CasQuery.getInstance()
							.getStatCusNameRelationMt(false, type,
									new ArrayList());
					if (list != null && list.size() > 0) {
						StatCusNameRelationMt sm = list.get(0);
						Materiel m = sm.getMateriel();
						tfEndPtNo.setText(m == null ? "" : m.getPtNo());
					}
				}
			});
		}
		return btnEndPtNo;
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
			jPanel1.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jPanel1.add(getJToolBar1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jToolBar1
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar1() {
		if (jToolBar1 == null) {
			jToolBar1 = new JToolBar();
			jToolBar1.add(getJPanel2());
		}
		return jToolBar1;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("查询的料号");
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel2 = new JPanel();
			jPanel2.setLayout(flowLayout);
			jPanel2.add(jLabel1, null);
			jPanel2.add(getTfPtNo(), null);
			jPanel2.add(getBtnPtNo(), null);
			jPanel2.add(getEndPtNo(), null);
			jPanel2.add(getBtnEndPtNo(), null);
		}
		return jPanel2;
	}

	/**
	 * init table model
	 * 
	 * @param list
	 */
	private void initTable(final List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new ArrayList<JTableListColumn>();
						list.add(addColumn("BOM编号", "ptPart", 100));
						list.add(addColumn("商品编码", "complex", 100));
						list.add(addColumn("商品名称", "hsName", 120));
						list.add(addColumn("商品规格", "hsSpec", 80));
						list.add(addColumn("今日数量", "todayAmount", 80));
						list.add(addColumn("仓库地点", "wareSet.name", 80));
						list.add(addColumn("昨天数量", "yestdayAmount", 80));
						list.add(addColumn("单位", "unit.name", 80));

						return list;
					}
				});
	}

	/**
	 * 打印
	 * 
	 */
	private void printReport() {
		try {
			List storeInfos = tableModel.getList();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			CommonDataSource storeInfoDS = new CommonDataSource(storeInfos);
						
			InputStream masterReportStream = DgStoreInfo.class
					.getResourceAsStream("report/StoreInfo.jasper");	
			
			Map<String, Object> parameters = new HashMap<String, Object>();
			Date beginDate = this.cbbBeginDate.getDate();
			Date endDate = this.cbbEndDate.getDate();
			parameters.put("beginDate", sdf.format(beginDate));
			parameters.put("endDate", sdf.format(endDate));
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(masterReportStream,
					parameters, storeInfoDS);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (JRException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 查询
	 * 
	 */
	private void search() {
		Date beginDate = this.cbbBeginDate.getDate();
		Date endDate = this.cbbEndDate.getDate();
		beginDate = CommonUtils.getBeginDate(beginDate);
		endDate = CommonUtils.getEndDate(endDate);
		if (beginDate != null && endDate != null) {
			if (beginDate.after(endDate)) {
				JOptionPane.showMessageDialog(this, "开始日期大于结束日期!!", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
		}
		if (tfPtNo.getText().trim().equals("")) {
			if (JOptionPane.showConfirmDialog(DgStoreInfo.this,
					"料号查询条件为空!!\n如果你的单据数据量超过100万条,查询可能要较长的时间，要继续吗??", "确认",
					JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) != JOptionPane.YES_OPTION) {
				return;
			}
		}
		new SearchThread().start();
	}

	/**
	 * 线程查询
	 * 
	 * @author ls
	 * 
	 */
	class SearchThread extends Thread {
		
		public void run() {
			//
			// 用于标识这个对话框的ID
			//
			UUID uuid = UUID.randomUUID();
			final String flag = uuid.toString();
			try {
				//
				// 日期
				//
				Date beginDate = cbbBeginDate.getDate();
				Date endDate = cbbEndDate.getDate();
				beginDate = CommonUtils.getBeginDate(beginDate);
				endDate = CommonUtils.getEndDate(endDate);
				
				long beginTime = System.currentTimeMillis();
				CommonProgress.showProgressDialog(flag, DgStoreInfo.this);
				CommonProgress.setMessage(flag, "系统正在获取数据，请稍后...");
				btnSearch.setEnabled(false);
				String type = ((ItemProperty) cmbType.getSelectedItem())
						.getCode();
				String beginPtNo = tfPtNo.getText();
				String endPtNo = tfEndPtNo.getText();
				List storeInfos = casAction.findStoreInfos(new Request(
						CommonVars.getCurrUser()), type, beginDate,endDate, beginPtNo, endPtNo);
				tableModel.setList(storeInfos);
				CommonProgress.closeProgressDialog(flag);
				String info = " 查询完成,共用时:"
						+ (System.currentTimeMillis() - beginTime) + " 毫秒 ";
				JOptionPane.showMessageDialog(DgStoreInfo.this, info, "提示",
						JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception e) {
				e.printStackTrace();
				CommonProgress.closeProgressDialog(flag);
				JOptionPane
						.showMessageDialog(DgStoreInfo.this, "系统获取数据失败！！！"
								+ e.getMessage(), "提示",
								JOptionPane.INFORMATION_MESSAGE);
			}
			btnSearch.setEnabled(true);
		}
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(new java.awt.Rectangle(260, 3, 88, 23));
			cbbBeginDate.setPreferredSize(new Dimension(98, 22));
		}
		return cbbBeginDate;
	}

}
