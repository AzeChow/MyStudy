/*
 * Created on 2005-5-20
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.manualdeclare;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import com.bestway.bcus.cas.entity.BillTemp;
import com.bestway.bcus.client.common.CommonDataSource;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.windows.control.ShowFormControl;
import com.bestway.common.Request;
import com.bestway.common.constant.CustomsDeclarationState;
import com.bestway.common.constant.ImpExpType;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public class DgExpUseRecord extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JButton jButton2 = null;

	private JTableListModel tableModel = null;

	private EncAction encAction = null;

	private List list = null;  //  @jve:decl-index=0:

	private ManualDeclareAction manualDecleareAction = null;

	/**
	 * This is the default constructor
	 */
	public DgExpUseRecord() {
		super();
		initialize();
		encAction = (EncAction) CommonVars.getApplicationContext().getBean(
				"encAction");
		manualDecleareAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
	}

	public void setVisible(boolean b) {
		if (b) {
			/*
			 * list = this.encAction.findImpExpCommInfoUseForExg(new Request(
			 * CommonVars.getCurrUser()), true,null,null,null,null,null);
			 */
			list = new Vector();
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
		this.setTitle("出口成品使用情况表");
		this.setSize(744, 454);
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
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
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
			jToolBar.add(getJButton());
			jToolBar.add(getJButton1());
			jToolBar.add(getJButton2());
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
			jTable.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if(e.getClickCount() == 2) {
						showImpCustomsRecord();
					}
				}
			});
		}
		return jTable;
	}
	
	private int iseffect;
	private Date begin;
	private Date end;
	private boolean isDeclare;
	
	public void showImpCustomsRecord() {
		int col = jTable.getSelectedColumn();
		if(col == 13 || col == 14 || col == 15 || col == 16) {
			Integer seqNum = null;
			BillTemp billTemp = (BillTemp) tableModel.getCurrentRow();
			seqNum = Integer.parseInt(billTemp.getBill1());
			String impExpType = null;
			if(col == 13) {
				impExpType = ImpExpType.DIRECT_EXPORT + "";
			} else if(col == 14) {
				impExpType = ImpExpType.TRANSFER_FACTORY_EXPORT + "";
			} else if(col == 15) {
				impExpType = ImpExpType.REWORK_EXPORT + "";
			} else if(col == 16) {
				impExpType = ImpExpType.BACK_FACTORY_REWORK + "";
			}
			DgExpCustomsRecord dg = new DgExpCustomsRecord();
			dg.showData(seqNum, null, impExpType, begin, 
					end, null,isDeclare,
					iseffect, false, null, false);
			ShowFormControl.showForm(dg);
		}
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
	
	private DgImpExpUseQueryCondition condition = null;

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("查询");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(condition == null) {
						condition = new DgImpExpUseQueryCondition();
					}
					condition.setImport(false);
					condition.setImg(false);// 成品
					condition.setVisible(true);
					if (condition.Radiotrue.isSelected()) {
						iseffect = CustomsDeclarationState.EFFECTIVED;
					} else if (condition.Radiofalse.isSelected()) {
						iseffect = CustomsDeclarationState.NOT_EFFECTIVED;
					} else if (condition.Radioall.isSelected()) {
						iseffect = CustomsDeclarationState.ALL;
					}
					begin = condition.cbbBeginDate.getDate();
					end = condition.cbbEndDate.getDate();
					isDeclare = condition.jRadioButton.isSelected();
					
					list = condition.getLsResult();
					if (list != null) {
						initTable(list);
					}
				}
			});
		}
		return jButton;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("打印");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (tableModel == null) {
						JOptionPane.showMessageDialog(DgExpUseRecord.this,
								"没有可打印的记录！", "提示", 2);
						return;
					}
					CommonDataSource imgExgDS = new CommonDataSource(list);

					List company = new Vector(); // 只有一条记录
					company.add(CommonVars.getCurrUser().getCompany());
					CommonDataSource companyDS = new CommonDataSource(company);

					InputStream masterReportStream = DgExpUseRecord.class
							.getResourceAsStream("report/ExpUseRecord.jasper");
					InputStream detailReportStream = DgExpUseRecord.class
							.getResourceAsStream("report/ExpUseRecordSub.jasper");
					try {
						JasperReport detailReport = (JasperReport) JRLoader
								.loadObject(detailReportStream);
						Map<String, Object> parameters = new HashMap<String, Object>();
						parameters.put("printDate", CommonVars.nowToDate());
						parameters.put("reportTitle", "出口成品情况统计报表");
						parameters.put("companyName", CommonVars.getCurrUser()
								.getCompany().getName());
						parameters.put("imgExgDS", imgExgDS);// 子数据源
						parameters.put("detailReport", detailReport);// 子报表
						JasperPrint jasperPrint;
						jasperPrint = JasperFillManager.fillReport(
								masterReportStream, parameters, companyDS);
						DgReportViewer viewer = new DgReportViewer(jasperPrint);
						viewer.setVisible(true);
					} catch (JRException e1) {
						e1.printStackTrace();
					}
				}
			});
		}
		return jButton1;
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
	 * 初始化数据Table
	 */
	private void initTable(List list) {
		if (list == null) {
			list = new ArrayList();
		}
		JTableListModelAdapter tableModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("备案序号", "bill1", 60, Integer.class));	// 1
				list.add(addColumn("商品编号", "bill2", 100));				// 2
				list.add(addColumn("品名", "bill3", 120));					// 3
				list.add(addColumn("规格", "bill9", 120));					// 4
				list.add(addColumn("单位", "bill4", 60));					// 5
				list.add(addColumn("版本号", "bill5", 80, Integer.class));	// 6
				list.add(addColumn("事业部", "bill6", 100));					// 7
				list.add(addColumn("单价", "billSum1", 60));					// 8
				list.add(addColumn("总额", "billSum2", 80));					// 9
				list.add(addColumn("1.总出口量=2+6-5", "billSum3", 120));		// 10
				list.add(addColumn("事业部小计", "billSum14", 100));			// 11
				list.add(addColumn("2.大单出口量=3+4", "billSum4", 120));		// 12
				list.add(addColumn("3.成品出口量", "billSum5", 80));			// 13
				list.add(addColumn("4.转厂出口量", "billSum6", 80));			// 14
				list.add(addColumn("5.退厂返工数量", "billSum7", 100));		// 15
				list.add(addColumn("6.返工复出数量", "billSum8", 100));		// 16

				return list;
			}
		};

		JTableListModel.dataBind(jTable, list, tableModelAdapter,
				ListSelectionModel.SINGLE_SELECTION);
		tableModel = (JTableListModel) jTable.getModel();
		// 截取小数位
		String reportDecimalLength = manualDecleareAction.getBpara(new Request(
				CommonVars.getCurrUser(), true),
				BcusParameter.ReportDecimalLength);
		Integer decimalLength = 2;
		if (reportDecimalLength != null)
			decimalLength = Integer.valueOf(reportDecimalLength);
		tableModel.setAllColumnsFractionCount(decimalLength);
		CompanyOther other = CommonVars.getOther();
		if (other == null) {
			return;
		}
		tableModel.setAllColumnsshowThousandthsign(other
				.getIsAutoshowThousandthsign() == null ? false : other
				.getIsAutoshowThousandthsign());
		setUpSportColumn(jTable, 13, "双击可关联‘成品出口类型’成品出口报关登记表");
		setUpSportColumn(jTable, 14, "双击可关联‘转厂出口类型’成品出口报关登记表");
		setUpSportColumn(jTable, 15, "双击可关联‘返工复出类型’成品出口报关登记表");
		setUpSportColumn(jTable, 16, "双击可关联‘退厂返工类型’成品出口报关登记表");
	}
	
	/**
	 * 为表格的某个列设置提示（不包含表头）
	 * 
	 * @param table
	 * @param colIndex
	 * @param tipText
	 */
	public void setUpSportColumn(JTable table, int colIndex, String tipText) {
		TableColumn sportColumn = table
				.getColumnModel().getColumn(colIndex);
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setToolTipText(tipText);
		sportColumn.setCellRenderer(renderer);
	}
}
