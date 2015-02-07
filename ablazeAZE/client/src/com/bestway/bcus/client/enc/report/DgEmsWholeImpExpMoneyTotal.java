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
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.CustomsDeclarationState;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgEmsWholeImpExpMoneyTotal extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;

	private JTableListModel tableModel = null;

	private EncAction encAction = null;

	private List list = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel = null;

	private JLabel jLabel = null;

	private JButton jButton = null;

	private List lsResult = null;

	private JButton jButton1 = null;

	private ManualDeclareAction manualDecleareAction = null;

	private JPanel jPanel1 = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private JLabel jLabel1 = null;

	private JCalendarComboBox jCalendarComboBox = null;

	private JLabel jLabel2 = null;

	private JCalendarComboBox jCalendarComboBox1 = null;

	private ManualDeclareAction manualDeclareAction = null;

	private JPanel jPanel2 = null;

	private JRadioButton Radiotrue = null;

	private JRadioButton Radiofalse = null;

	private JRadioButton Radioall = null;
	
	private ButtonGroup buttonGroup = null;  //  @jve:decl-index=0:

	/**
	 * This is the default constructor
	 */
	public DgEmsWholeImpExpMoneyTotal() {
		super();
		encAction = (EncAction) CommonVars.getApplicationContext().getBean(
				"encAction");
		manualDeclareAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		initialize();

	}
	public ButtonGroup getButtonGroup() {
		if (buttonGroup == null) {
			buttonGroup = new ButtonGroup();
			buttonGroup.add(getRadiotrue());
			buttonGroup.add(getRadiofalse());
			buttonGroup.add(getRadioall());
		}
		return buttonGroup;
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
		this.setSize(737, 454);
		this.setTitle("帐册总体进出口金额状况统计表");
//		this.addWindowListener(new java.awt.event.WindowAdapter() {
//
//			public void windowOpened(java.awt.event.WindowEvent e) {
//
//				
//			}
//		});
		getButtonGroup();
	}
	@Override
	public void setVisible(boolean b) {
		if (b) {
			initTable(new Vector());
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
						list.add(addColumn("帐册编号", "bill1", 120));
						list.add(addColumn("帐册总周转金额", "billSum1", 100));
						list.add(addColumn("已报关料件进口总金额", "billSum2", 130));
						list.add(addColumn("已报关成品出口总金额", "billSum3", 130));
						list.add(addColumn("已报关成品折原材料总金额", "billSum4", 160));
						list
								.add(addColumn("进口总金额 - 成品折料总金额差异", "billSum5",
										200));
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
			jLabel2 = new JLabel();
			jLabel2.setText("至");
			jLabel2.setBounds(new Rectangle(176, 15, 34, 23));
			jLabel1 = new JLabel();
			jLabel1.setText("报关日期：");
			jLabel1.setBounds(new Rectangle(5, 15, 60, 22));
			jLabel = new JLabel();
			jLabel.setText("帐册总体进出口金额状况统计表");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jLabel.setBounds(165, 4, 361, 34);

			jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 24));
			jLabel.setForeground(java.awt.Color.blue);

			jPanel.add(jLabel, null);
			jPanel.add(getJButton(), null);
			jPanel.add(getJButton1(), null);
			jPanel.add(getJPanel2(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(531, 56, 94, 23);
			jButton.setText("查询");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new find().start();
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
				Date beginDate = CommonVars.dateToStandDate(jCalendarComboBox
						.getDate());
				Date endDate = CommonVars.dateToStandDate(jCalendarComboBox1
						.getDate());
				int iseffect = -1;
				if (Radiotrue.isSelected()) {
					iseffect = CustomsDeclarationState.EFFECTIVED;
				} else if (Radiofalse.isSelected()) {
					iseffect = CustomsDeclarationState.NOT_EFFECTIVED;
				} else if (Radioall.isSelected()) {
					iseffect = CustomsDeclarationState.ALL;
				}
				lsResult = encAction.jisuanEmsImpExpMoneyTotal(new Request(
						CommonVars.getCurrUser()), beginDate, endDate, iseffect);
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgEmsWholeImpExpMoneyTotal.this,
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
			jButton1.setBounds(635, 57, 86, 23);
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
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getJCalendarComboBox() {
		if (jCalendarComboBox == null) {
			jCalendarComboBox = new JCalendarComboBox();
			jCalendarComboBox.setBounds(new Rectangle(71, 15, 98, 23));
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
			jCalendarComboBox1.setBounds(new Rectangle(216, 14, 95, 23));
		}
		return jCalendarComboBox1;
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
			jPanel2.setBounds(new Rectangle(8, 42, 521, 51));
			jPanel2.add(jLabel1, null);
			jPanel2.add(getJCalendarComboBox(), null);
			jPanel2.add(jLabel2, null);
			jPanel2.add(getJCalendarComboBox1(), null);
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
			Radiotrue.setBounds(new Rectangle(319, 16, 62, 21));
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
			Radiofalse.setBounds(new Rectangle(383, 17, 71, 21));
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
			Radioall.setBounds(new Rectangle(455, 16, 56, 21));
			Radioall.setText("全部");
		}
		return Radioall;
	}
}
