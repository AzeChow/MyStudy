/*
 * Created on 2005-5-20
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.enc.report;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonQueryPage;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.enc.entity.TempContainerCode;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgImgUnitWeightDiff extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;

	private JTableListModel tableModel = null;

	private EncAction encAction = null;

	private List list = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane1 = null;

	private JLabel jLabel = null;

	private JButton jButton = null;

	private List lsResult = null;

	private JButton jButton1 = null;

	private JLabel jLabel1 = null;

	private JCalendarComboBox jCalendarComboBox = null;

	private JLabel jLabel2 = null;

	private JCalendarComboBox jCalendarComboBox1 = null;

	private Hashtable hs = new Hashtable();  //  @jve:decl-index=0:

	private ManualDeclareAction manualDeclareAction = null;

	/**
	 * This is the default constructor
	 */
	public DgImgUnitWeightDiff() {
		super();
		encAction = (EncAction) CommonVars.getApplicationContext().getBean(
				"encAction");
		manualDeclareAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		initialize();

	}

	private void initUIComponents() {
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setContentPane(getJContentPane());
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setSize(752, 454);
		this.setTitle("进口料件单重差异稽核表");
//		this.addWindowListener(new java.awt.event.WindowAdapter() {
//
//			public void windowOpened(java.awt.event.WindowEvent e) {
//
//				initUIComponents();
//				list = new Vector();
//				initTable(list);
//			}
//		});
	}
	@Override
	public void setVisible(boolean b) {
		if (b) {
			initUIComponents();
			list = new Vector();
			initTable(list);
		}
		super.setVisible(b);
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
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
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
						list.add(addColumn("料号", "bill1", 80));
						list.add(addColumn("品名", "bill2", 120));
						list.add(addColumn("规格", "bill3", 100));
						list.add(addColumn("单位", "bill4", 80));
						list.add(addColumn("报关品名", "bill5", 120));
						list.add(addColumn("申报日期", "bill6", 80));
						list.add(addColumn("装箱单号", "bill7", 80));
						list.add(addColumn("报关单号", "bill8", 100));
						list.add(addColumn("1.数量", "billSum1", 80));
						list.add(addColumn("2.报关净重", "billSum2", 80));
						list.add(addColumn("3.ERP净重", "billSum3", 80));
						list.add(addColumn("4.净重差异=3-2", "billSum4", 80));
						list.add(addColumn("5.报关毛重", "billSum5", 80));
						list.add(addColumn("6.ERP毛重", "billSum6", 80));
						list.add(addColumn("7.毛重差异=6-5", "billSum7", 80));
						list.add(addColumn("8.数量*净重差异=1*4", "billSum8", 120));
						list.add(addColumn("9.数量*毛重差异=1*7", "billSum9", 120));
						list.add(addColumn("柜号", "bill9", 100));
						return list;
					}
				});

		// 截取小数位
		String reportDecimalLength = manualDeclareAction.getBpara(new Request(
				CommonVars.getCurrUser(), true),
				BcusParameter.ReportDecimalLength);
		Integer decimalLength = 2;
		if (reportDecimalLength != null)
			decimalLength = Integer.valueOf(reportDecimalLength);

		List<JTableListColumn> cm = tableModel.getColumns();

		cm.get(9).setFractionCount(decimalLength);
		cm.get(10).setFractionCount(decimalLength);
		cm.get(11).setFractionCount(decimalLength);
		cm.get(12).setFractionCount(decimalLength);
		cm.get(13).setFractionCount(decimalLength);
		cm.get(14).setFractionCount(decimalLength);
		cm.get(15).setFractionCount(decimalLength);
		cm.get(16).setFractionCount(decimalLength);
		cm.get(17).setFractionCount(decimalLength);
		
		CommonVars.castMultiplicationValue(jTable,16,9,12,decimalLength);
		CommonVars.castMultiplicationValue(jTable,17,15,9,decimalLength);
		CompanyOther other = CommonVars.getOther();
		if (other == null) {
			return;
		}
		tableModel.setAllColumnsshowThousandthsign(other
				.getIsAutoshowThousandthsign() == null ? false : other
				.getIsAutoshowThousandthsign());
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setDividerLocation(50);
			jSplitPane.setDividerSize(0);
			jSplitPane.setTopComponent(getJPanel());
			jSplitPane.setBottomComponent(getJPanel1());
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel2 = new JLabel();
			jLabel2.setBounds(new java.awt.Rectangle(435, 17, 15, 18));
			jLabel2.setText("至");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new java.awt.Rectangle(289, 17, 65, 20));
			jLabel1.setText("申报日期：");
			jLabel = new JLabel();
			jLabel.setText("进口料件单重差异稽核表");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jLabel.setBounds(8, 8, 298, 34);

			jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 24));
			jLabel.setForeground(java.awt.Color.blue);

			jPanel.add(jLabel, null);
			jPanel.add(getJButton(), null);
			jPanel.add(getJButton1(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(getJCalendarComboBox(), null);
			jPanel.add(jLabel2, null);
			jPanel.add(getJCalendarComboBox1(), null);
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
			jPanel1.setLayout(new BorderLayout());
			jPanel1.add(getJScrollPane(), java.awt.BorderLayout.NORTH);
			jPanel1.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
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
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJTable());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(543, 15, 94, 23);
			jButton.setText("选择单据");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					Date begindate = CommonVars
							.dateToStandDate(DgImgUnitWeightDiff.this.jCalendarComboBox
									.getDate());
					Date enddate = CommonVars
							.dateToStandDate(DgImgUnitWeightDiff.this.jCalendarComboBox1
									.getDate());
					List list = (List) CommonQueryPage.getInstance()
							.getMaterielFromContainer(begindate, enddate, hs);

					if (list == null || list.size() <= 0) {
						return;
					}
					new find(list).start();
				}
			});
		}
		return jButton;
	}

	class find extends Thread {
		private List list = null;

		private List resultList = null;

		public find(List list) {
			this.list = list;
		}

		public void run() {
			try {
				CommonProgress.showProgressDialog();
				CommonProgress.setMessage("系统正获取数据，请稍后...");
				resultList = encAction.accountImpUnitWeightCheck(new Request(
						CommonVars.getCurrUser()), list);
				for (int i = 0; i < list.size(); i++) {
					TempContainerCode obj = (TempContainerCode) list.get(i);
					hs.put(obj.getContainerCode(), obj.getContainerCode());
				}
				CommonProgress.closeProgressDialog();
				System.out.println("--:" + resultList.size());
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgImgUnitWeightDiff.this,
						"获取数据失败：！" + e.getMessage(), "提示", 2);
			} finally {
				if (tableModel.getSize() == 0) {
					initTable(resultList);
				} else {
					tableModel.addRows(resultList);
				}
			}
		}
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(646, 15, 86, 23);
			jButton1.setText("关闭");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getJCalendarComboBox() {
		if (jCalendarComboBox == null) {
			jCalendarComboBox = new JCalendarComboBox();
			jCalendarComboBox
					.setBounds(new java.awt.Rectangle(346, 17, 86, 20));
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
			jCalendarComboBox1
					.setBounds(new java.awt.Rectangle(451, 17, 84, 19));
		}
		return jCalendarComboBox1;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
