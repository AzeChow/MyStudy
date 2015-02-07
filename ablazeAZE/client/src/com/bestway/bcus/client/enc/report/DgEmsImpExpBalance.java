/*
 * Created on 2005-5-20
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.enc.report;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
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
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.CustomsDeclarationState;
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
public class DgEmsImpExpBalance extends JInternalFrameBase {

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

	private JCalendarComboBox jCalendarComboBox = null;

	private JLabel jLabel2 = null;

	private JCalendarComboBox jCalendarComboBox1 = null;

	private JLabel jLabel3 = null;

	private JButton jButton = null;

	private List lsResult = null;

	private JButton jButton1 = null;

	private JRadioButton jRadioButton = null;

	private JRadioButton jRadioButton1 = null;

	private ButtonGroup buttonGroup = null;  //  @jve:decl-index=0:
	
	private ButtonGroup buttonGroup1 = null;  //  @jve:decl-index=0:
	
	private JCheckBox jCheckBox1 = null;

	private JComboBox jComboBox = null;

	private JLabel jLabel4 = null;

	private MaterialManageAction materialManageAction = null;

	private ManualDeclareAction manualDeclareAction = null;

	private JPanel jPanel2 = null;

	private JRadioButton Radiotrue = null;

	private JRadioButton Radiofalse = null;

	private JRadioButton Radioall = null;

	/**
	 * This is the default constructor
	 */
	public DgEmsImpExpBalance() {
		super();
		encAction = (EncAction) CommonVars.getApplicationContext().getBean(
				"encAction");
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		manualDeclareAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");

		initialize();
		getButtonGroup();
		getButtonGroup1();
	}

	private void initUIComponents() {
		List list = manualDeclareAction.findEmsHeadH2k(new Request(CommonVars
				.getCurrUser(), true));
		if (list != null && list.size() > 0) {
			String emsno = ((EmsHeadH2k) list.get(0)).getEmsNo();
			jLabel3.setText("帐册号：" + emsno);
		}

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
		this.setTitle("帐册进出口量平衡状况表");
		this.jCalendarComboBox.setDate(CommonVars.getBeginDate());
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
	
	public ButtonGroup getButtonGroup1() {
		if (buttonGroup1 == null) {
			buttonGroup1 = new ButtonGroup();
			buttonGroup1.add(getRadiotrue());
			buttonGroup1.add(getRadiofalse());
			buttonGroup1.add(getRadioall());
		}
		return buttonGroup1;
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
						list.add(addColumn("商品编码", "bill1", 80));
						list.add(addColumn("品名规格", "bill2", 150));
						list.add(addColumn("帐册号", "bill3", 100));
						list.add(addColumn("备案序号", "bill4", 50, Integer.class));
						list.add(addColumn("单价", "billSum1", 50));
						list.add(addColumn("单位", "bill5", 50));
						list.add(addColumn("事业部", "bill6", 100));
						list.add(addColumn("1.备案数量", "billSum2", 60));
						list.add(addColumn("2.进口数量", "billSum3", 60));
						list.add(addColumn("3.本期出口成品耗用数量", "billSum6", 120));
						list.add(addColumn("4.出口成品耗用数量", "billSum7", 120));
						list.add(addColumn("5.差异数量=2-4", "billSum5", 200));
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
			jSplitPane.setDividerLocation(120);
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
			jLabel4 = new JLabel();
			jLabel4.setForeground(java.awt.Color.red);
			jLabel4.setBounds(new Rectangle(179, 60, 42, 22));
			jLabel4.setText("事业部");
			jLabel3 = new JLabel();
			jLabel2 = new JLabel();
			jLabel1 = new JLabel();
			jLabel = new JLabel();
			jLabel.setText("帐册进出口量平衡状况");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jLabel.setBounds(204, 0, 298, 34);

			jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 24));
			jLabel.setForeground(java.awt.Color.blue);
			jLabel1.setText("报关日期：");
			jLabel1.setBounds(new Rectangle(14, 12, 61, 18));
			jLabel2.setText("至");
			jLabel2.setBounds(new Rectangle(206, 12, 18, 19));
			jLabel3.setText("帐册号：");

			jLabel3.setBounds(new Rectangle(15, 36, 162, 20));
			jPanel.add(jLabel, null);
			jPanel.add(getJButton(), null);
			jPanel.add(getJButton1(), null);
			jPanel.add(getJPanel2(), null);
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
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getJCalendarComboBox() {
		if (jCalendarComboBox == null) {
			jCalendarComboBox = new JCalendarComboBox();
			jCalendarComboBox.setBounds(new Rectangle(79, 11, 122, 20));
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
			jCalendarComboBox1.setBounds(new Rectangle(229, 11, 136, 21));
		}
		return jCalendarComboBox1;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(627, 51, 86, 23);
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
				String customer = null;

				int iseffect = -1;
				if (Radiotrue.isSelected()) {
					iseffect = CustomsDeclarationState.EFFECTIVED;
				} else if (Radiofalse.isSelected()) {
					iseffect = CustomsDeclarationState.NOT_EFFECTIVED;
				} else if (Radioall.isSelected()) {
					iseffect = CustomsDeclarationState.ALL;
				}
				Date beginDate = CommonVars.dateToStandDate(jCalendarComboBox
						.getDate());
				Date endDate = CommonVars.dateToStandDate(jCalendarComboBox1
						.getDate());
				if (jRadioButton.isSelected()) {// 申报日期
					lsResult = encAction.jisuanEmsImpExpBalance(new Request(
							CommonVars.getCurrUser()), beginDate, endDate,
							iseffect, true);
				} else if (jRadioButton1.isSelected()) { // 结关日期
					lsResult = encAction.jisuanEmsImpExpBalance(new Request(
							CommonVars.getCurrUser()), beginDate, endDate,
							iseffect, false);
				}
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgEmsImpExpBalance.this,
						"获取数据失败：！" + e.getMessage(), "提示", 2);
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
				String customer = null;
				String deptCode = null;
				// 事业部
				if (jComboBox.getSelectedItem() != null
						&& !jComboBox.getSelectedItem().toString().trim()
								.equals("")) {
					deptCode = ((ProjectDept) jComboBox.getSelectedItem())
							.getCode();
				}
				int iseffect = -1;
				if (Radiotrue.isSelected()) {
					iseffect = CustomsDeclarationState.EFFECTIVED;
				} else if (Radiofalse.isSelected()) {
					iseffect = CustomsDeclarationState.NOT_EFFECTIVED;
				} else if (Radioall.isSelected()) {
					iseffect = CustomsDeclarationState.ALL;
				}
				Date beginDate = CommonVars.dateToStandDate(jCalendarComboBox
						.getDate());
				Date endDate = CommonVars.dateToStandDate(jCalendarComboBox1
						.getDate());
				if (jRadioButton.isSelected()) {// 申报日期
					lsResult = encAction.jisuanEmsImpExpBalanceForDept(
							new Request(CommonVars.getCurrUser()), beginDate,
							endDate, iseffect, true, deptCode);
				} else if (jRadioButton1.isSelected()) { // 结关日期
					lsResult = encAction
							.jisuanEmsImpExpBalanceForDept(new Request(
									CommonVars.getCurrUser()), beginDate,
									endDate, iseffect, false,
									deptCode);
				}
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgEmsImpExpBalance.this,
						"获取数据失败：！" + e.getMessage(), "提示", 2);
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
			jButton1.setBounds(628, 89, 86, 23);
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
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton() {
		if (jRadioButton == null) {
			jRadioButton = new JRadioButton();
			jRadioButton.setText("按申报日期查询");
			jRadioButton.setBounds(new Rectangle(380, 13, 110, 18));
			jRadioButton.setSelected(true);
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
			jRadioButton1.setText("按结关日期查询");

			jRadioButton1.setBounds(new Rectangle(497, 11, 110, 20));
		}
		return jRadioButton1;
	}

	public ButtonGroup getButtonGroup() {
		if (buttonGroup == null) {
			ButtonGroup buttonGroup = new ButtonGroup();
			buttonGroup.add(getJRadioButton());
			buttonGroup.add(getJRadioButton1());
		}
		return buttonGroup;
	}

	/**
	 * This method initializes jCheckBox1
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox1() {
		if (jCheckBox1 == null) {
			jCheckBox1 = new JCheckBox();
			jCheckBox1.setForeground(java.awt.Color.red);
			jCheckBox1.setBounds(new Rectangle(16, 61, 122, 21));
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
			jComboBox.setBounds(new Rectangle(230, 60, 140, 21));
		}
		return jComboBox;
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
			jPanel2.setBounds(new Rectangle(3, 30, 613, 91));
			jPanel2.add(jLabel1, null);
			jPanel2.add(getJCalendarComboBox(), null);
			jPanel2.add(jLabel2, null);
			jPanel2.add(getJCalendarComboBox1(), null);
			jPanel2.add(jLabel3, null);
			jPanel2.add(getJRadioButton(), null);
			jPanel2.add(getJRadioButton1(), null);
			jPanel2.add(getJCheckBox1(), null);
			jPanel2.add(jLabel4, null);
			jPanel2.add(getJComboBox(), null);
			jPanel2.add(getRadiotrue(), null);
			jPanel2.add(getRadiofalse(), null);
			jPanel2.add(getRadioall(), null);
		}
		return jPanel2;
	}

	/**
	 * This method initializes Radiotrue	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRadiotrue() {
		if (Radiotrue == null) {
			Radiotrue = new JRadioButton();
			Radiotrue.setBounds(new Rectangle(380, 43, 77, 21));
			Radiotrue.setText("已生效");
			Radiotrue.setSelected(true);
		}
		return Radiotrue;
	}

	/**
	 * This method initializes Radiofalse	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRadiofalse() {
		if (Radiofalse == null) {
			Radiofalse = new JRadioButton();
			Radiofalse.setBounds(new Rectangle(461, 42, 79, 21));
			Radiofalse.setText("未生效");
		}
		return Radiofalse;
	}

	/**
	 * This method initializes Radioall	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRadioall() {
		if (Radioall == null) {
			Radioall = new JRadioButton();
			Radioall.setBounds(new Rectangle(547, 42, 53, 21));
			Radioall.setText("全部");
		}
		return Radioall;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
