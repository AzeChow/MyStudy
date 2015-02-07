package com.bestway.bcs.client.contractstat;

import java.awt.BorderLayout;

import com.bestway.bcus.custombase.action.CustomBaseAction;
import com.bestway.bcus.custombase.entity.parametercode.CustomsBroker;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.Date;

import com.bestway.bcs.contractstat.action.ContractStatAction;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseComboBoxUI;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JToolBar;
import javax.swing.JTextField;
import javax.swing.JLabel;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Font;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.customs.common.action.BaseEncAction;
import javax.swing.DefaultComboBoxModel;
import com.bestway.bcus.client.common.CustomBaseRender;


public class DgCustomsBrokersList extends JInternalFrameBase{

	private JPanel jPanel = null;
	private JToolBar jToolBar = null;
	private JPanel jPanel1 = null;
	private JLabel jLabel = null;
	private JCalendarComboBox jCalendarComboBox = null;
	private JLabel jLabel1 = null;
	private JCalendarComboBox jCalendarComboBox1 = null;
	private JLabel jLabel2 = null;
	private JComboBox jComboBox = null;
	private JButton jButton = null;
	private JTableListModel tableModel = null;
	private JTable jTable = null;
	private JScrollPane jScrollPane = null;
	private JButton jButton1 = null;
	
	private ContractStatAction contractStatAction = null;
	private MaterialManageAction materialManageAction = null;
	protected BaseEncAction baseEncAction = null;
	private CustomBaseAction customBaseAction = null;
	
	/**
	 * This method initializes 
	 * 
	 */
	public DgCustomsBrokersList() {
		super();
		materialManageAction = (MaterialManageAction) CommonVars
		.getApplicationContext().getBean("materialManageAction");
		customBaseAction = (CustomBaseAction) CommonVars
		.getApplicationContext().getBean("customBaseAction");
		contractStatAction = (ContractStatAction) CommonVars
		.getApplicationContext().getBean("contractStatAction");
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new Dimension(954, 359));
        this.setContentPane(getJPanel());
        this.setTitle("报关行申报登记表");
		this
		.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.jCalendarComboBox.setDate(CommonVars.getBeginDate());
		this.jCalendarComboBox1.setDate(CommonVars.getEndDate(new Date()));
		List list = customBaseAction.findCustomsBroker();
		this.getJComboBox().setModel(
				new DefaultComboBoxModel(list.toArray()));
		this.getJComboBox().setRenderer(CustomBaseRender.getInstance().getRender(
				"code","name" ,100,100));
		CustomBaseComboBoxEditor.getInstance().
			setComboBoxEditor(this.jComboBox,"","");
			this.jComboBox.setSelectedIndex(-1);
	}

	public void setVisible(boolean b){
		if(b){
			List list = new ArrayList();
			initTable(list);
		}
		super.setVisible(b);
	}
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new java.awt.BorderLayout());
			jPanel.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jPanel.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * This method initializes jToolBar	
	 * 	
	 * @return javax.swing.JToolBar	
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.setPreferredSize(new Dimension(19, 35));
			jToolBar.setFloatable(false);
			jToolBar.add(getJPanel1());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(283, 3, 59, 18));
			jLabel2.setFont(new Font("Dialog", Font.BOLD, 13));
			jLabel2.setText("报关行");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(142, 4, 26, 18));
			jLabel1.setText("至");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(24, 4, 38, 18));
			jLabel.setText("日期");
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.add(jLabel, null);
			jPanel1.add(getJCalendarComboBox(), null);
			jPanel1.add(jLabel1, null);
			jPanel1.add(getJCalendarComboBox1(), null);
			jPanel1.add(jLabel2, null);
			jPanel1.add(getJComboBox(), null);
			jPanel1.add(getJButton(), null);
			jPanel1.add(getJButton1(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jCalendarComboBox	
	 * 	
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox	
	 */
	private JCalendarComboBox getJCalendarComboBox() {
		if (jCalendarComboBox == null) {
			jCalendarComboBox = new JCalendarComboBox();
			jCalendarComboBox.setBounds(new Rectangle(61, 4, 79, 22));
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
			jCalendarComboBox1.setBounds(new Rectangle(164, 4, 79, 22));
		}
		return jCalendarComboBox1;
	}

	/**
	 * This method initializes jComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setBounds(new Rectangle(331, 3, 260, 27));
		}
		return jComboBox;
	}
	private void initTable(List list) {
		if (list == null) {
			list = new ArrayList();
		}
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("申报日期",
								"declarationDate", 120));//1
						list.add(addColumn("报关行代码", "customsBrokerCode", 120));//2
						list.add(addColumn("报关行名称", "customsBrokerName", 200));//3
						list.add(addColumn("进出口类型",
								"impExpFlag", 120));//4
						list.add(addColumn("贸易方式",
								"tradeMode", 120));//5
						list.add(addColumn("申报人",
								"customserName", 100));//1
						list
						.add(addColumn(
								"账册号",
								"emsNo",
								120));
						return list;
					}
				});
	}
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new Rectangle(608, 3, 83, 25));
			jButton.setEnabled(true);
			jButton.setText("查询");
			jButton.addActionListener(new java.awt.event.ActionListener(){
				public void actionPerformed(java.awt.event.ActionEvent e){
					Date beginDate = jCalendarComboBox.getDate();
					Date endDate = jCalendarComboBox1.getDate();
					String customsbrokerName = "";
					if(jComboBox.getSelectedItem() != null){
					CustomsBroker customsBroker = (CustomsBroker) jComboBox.getSelectedItem();
					customsbrokerName = customsBroker.getCode();//获得代码
					System.out.println("@@@" + customsbrokerName);
					}
					List list = contractStatAction.findCustomsBrokerList(new
							Request (CommonVars.getCurrUser()), beginDate, endDate, customsbrokerName);
					if(list != null){
						initTable(list);
					}
					
				}
			});
		}
		return jButton;
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
			jScrollPane.setBounds(new Rectangle(1, 36, 940, 286));
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(new Rectangle(695, 3, 83, 25));
			jButton1.setText("关闭");
			jButton1.addActionListener(new java.awt.event.ActionListener(){
				public void actionPerformed(java.awt.event.ActionEvent e){
					dispose();
					
				}
			});
		}
		return jButton1;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
