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
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.ProjectDept;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgEmsExpExg extends JInternalFrameBase {

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

	private JLabel jLabel1 = null;

	private JCalendarComboBox jCalendarComboBox1 = null;

	private JCheckBox jCheckBox = null;

	private JButton jButton = null;

	private List lsResult = null;

	private JButton jButton1 = null;

	private ManualDeclareAction manualDecleareAction = null;

	private JLabel jLabel2 = null;

	private JCalendarComboBox jCalendarComboBox = null;

	private JCheckBox jCheckBox1 = null;

	private JComboBox jComboBox = null;

	private JLabel jLabel3 = null;

	private MaterialManageAction materialManageAction = null;

	private ManualDeclareAction manualDeclareAction = null;

	/**
	 * This is the default constructor
	 */
	public DgEmsExpExg() {
		super();
		encAction = (EncAction) CommonVars.getApplicationContext().getBean(
				"encAction");
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		manualDeclareAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");

		initialize();

	}

	private void initUIComponents() {
		// 事业部
		List projectList = materialManageAction.findProjectDept(new Request(
				CommonVars.getCurrUser(), true));
		this.jComboBox
				.setModel(new DefaultComboBoxModel(projectList.toArray()));
		this.jComboBox.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.jComboBox, "code", "name");
		jComboBox.setSelectedIndex(-1);
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
		this.setSize(744, 454);
		this.setTitle("帐册出口成品状况表");
		this.jCalendarComboBox1.setDate(new Date());
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
						list.add(addColumn("帐册号", "bill1", 100));
						list.add(addColumn("商品编码", "bill2", 80));
						list.add(addColumn("品名", "bill3", 150));
						list.add(addColumn("备案序号", "bill4", 50));
						list.add(addColumn("版本号", "bill5", 50));
						list.add(addColumn("事业部", "bill8", 100));
						list.add(addColumn("企业申报单价", "billSum1", 100));
						list.add(addColumn("合同签订净重(KG)", "billSum2", 100));
						list.add(addColumn("合同签订毛重(KG)", "billSum3", 100));
						list.add(addColumn("合同签订比例(%)", "billSum4", 100));
						list.add(addColumn("申报单位", "bill6", 80));
						list.add(addColumn("帐册累计已出口数量", "billSum5", 120));
						list.add(addColumn("帐册累计已出口件数", "bill7", 120));
						list.add(addColumn("帐册应出口净重", "billSum7", 100));
						list.add(addColumn("实际报关出口净重", "billSum8", 100));
						list.add(addColumn("帐册净重-实际出口差异", "billSum9", 150));

						list.add(addColumn("帐册应出口毛重", "billSum10", 100));
						list.add(addColumn("实际报关出口毛重", "billSum11", 100));
						list.add(addColumn("帐册毛重-实际出口差异", "billSum12", 150));
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
		tableModel.setAllColumnsFractionCount(decimalLength);
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
			jSplitPane.setDividerLocation(100);
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
			jLabel3 = new JLabel();
			jLabel3.setBounds(new java.awt.Rectangle(357, 69, 48, 21));
			jLabel3.setForeground(java.awt.Color.red);
			jLabel3.setText("事业部");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new java.awt.Rectangle(318, 11, 84, 19));
			jLabel2.setText("申报开始日期：");
			jLabel1 = new JLabel();
			jLabel = new JLabel();
			jLabel.setText("帐册出口成品状况表");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jLabel.setBounds(8, 8, 249, 34);

			jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 24));
			jLabel.setForeground(java.awt.Color.blue);
			jLabel1.setBounds(500, 12, 89, 18);
			jLabel1.setText("申报终止日期：");

			jPanel.add(jLabel, null);
			jPanel.add(jLabel1, null);
			jPanel.add(getJCalendarComboBox1(), null);
			jPanel.add(getJCheckBox(), null);
			jPanel.add(getJButton(), null);
			jPanel.add(getJButton1(), null);
			jPanel.add(jLabel2, null);
			jPanel.add(getJCalendarComboBox(), null);
			jPanel.add(getJCheckBox1(), null);
			jPanel.add(getJComboBox(), null);
			jPanel.add(jLabel3, null);
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
	 * This method initializes jCalendarComboBox1
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getJCalendarComboBox1() {
		if (jCalendarComboBox1 == null) {
			jCalendarComboBox1 = new JCalendarComboBox();
			jCalendarComboBox1.setBounds(591, 11, 87, 22);
		}
		return jCalendarComboBox1;
	}

	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			jCheckBox = new JCheckBox();
			jCheckBox.setBounds(500, 36, 138, 23);
			jCheckBox.setText("计算未生效报关单");
		}
		return jCheckBox;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(500, 66, 94, 23);
			jButton.setText("查询");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jCheckBox1.isSelected()) {// 分事业部
						new findfordept().start();
					} else {// 不分事业部
						new find().start();
					}
				}
			});
		}
		return jButton;
	}

	class find extends Thread {

		public void run() {
			try {
				CommonProgress.showProgressDialog();
				CommonProgress.setMessage("系统正获取数据，请稍后...");
				boolean iseffect = !(jCheckBox.isSelected());
				Date endDate = CommonVars.dateToStandDate(jCalendarComboBox1
						.getDate());
				Date beginDate = CommonVars.dateToStandDate(jCalendarComboBox
						.getDate());
				lsResult = encAction.jisuanEmsExpExg(new Request(CommonVars
						.getCurrUser()), beginDate, endDate, Boolean
						.valueOf(iseffect));
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgEmsExpExg.this, "获取数据失败：！"
						+ e.getMessage(), "提示", 2);
			} finally {
				initTable(lsResult);
			}
		}
	}

	class findfordept extends Thread {

		public void run() {
			try {
				CommonProgress.showProgressDialog();
				CommonProgress.setMessage("系统正获取数据，请稍后...");
				boolean iseffect = !(jCheckBox.isSelected());
				String deptCode = null;
				// 事业部
				if (jComboBox.getSelectedItem() != null
						&& !jComboBox.getSelectedItem().toString().trim()
								.equals("")) {
					deptCode = ((ProjectDept) jComboBox.getSelectedItem())
							.getCode();
				}
				Date endDate = CommonVars.dateToStandDate(jCalendarComboBox1
						.getDate());
				Date beginDate = CommonVars.dateToStandDate(jCalendarComboBox
						.getDate());
				lsResult = encAction.jisuanEmsExpExgForDept(new Request(
						CommonVars.getCurrUser()), beginDate, endDate, Boolean
						.valueOf(iseffect), deptCode);
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgEmsExpExg.this, "获取数据失败：！"
						+ e.getMessage(), "提示", 2);
			} finally {
				initTable(lsResult);
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
			jButton1.setBounds(603, 66, 86, 23);
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
					.setBounds(new java.awt.Rectangle(403, 10, 84, 22));
		}
		return jCalendarComboBox;
	}

	/**
	 * This method initializes jCheckBox1
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox1() {
		if (jCheckBox1 == null) {
			jCheckBox1 = new JCheckBox();
			jCheckBox1.setBounds(new java.awt.Rectangle(225, 67, 126, 24));
			jCheckBox1.setForeground(java.awt.Color.red);
			jCheckBox1.setText("是否分事业部统计");
			jCheckBox1.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (jCheckBox1.isSelected()) {
						jComboBox.setEnabled(true);
					} else {
						jComboBox.setEnabled(false);
					}
				}
			});
		}
		return jCheckBox1;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setEnabled(false);
			jComboBox.setBounds(new java.awt.Rectangle(407, 69, 90, 21));
		}
		return jComboBox;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
