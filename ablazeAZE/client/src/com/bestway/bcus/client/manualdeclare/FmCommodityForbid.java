/*
 * Created on 2005-12-26
 * 商品禁用功能
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.manualdeclare;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.CommdityForbid;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author wp
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
@SuppressWarnings("unchecked")
public class FmCommodityForbid extends JInternalFrameBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private javax.swing.JPanel jContentPane = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JCalendarComboBox jCalendarComboBox = null;

	private JLabel jLabel2 = null;

	private JCalendarComboBox jCalendarComboBox1 = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JTableListModel tableModel = null;

	private JLabel jLabel3 = null;

	private JComboBox jComboBox = null;

	private ManualDeclareAction manualDecleareAction = null;

	private JButton jButton2 = null;

	private JToolBar jToolBar = null;

	private JLabel lbSerialNo = null;

	private JTextField tfSerialNo = null;

	private JButton btnHis = null;

	/**
	 * This is the default constructor
	 */
	public FmCommodityForbid() {
		super();
        manualDecleareAction = (ManualDeclareAction) CommonVars
           .getApplicationContext().getBean("manualdeclearAction");
        manualDecleareAction.controlFmCommodityForbidBrowser(new Request(CommonVars.getCurrUser()));
        initialize();
		this.jCalendarComboBox.setDate(CommonVars.getBeginDate());
        this.jCalendarComboBox1.setDate(new Date());
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(1041, 537);
		this.setContentPane(getJContentPane());
		this.setTitle("帐册商品禁用功能");
		initTable(null);
		initUIComponents();
		findList(true);
	}

	private void initUIComponents() {
		List list = manualDecleareAction.findEmsHeadH2k(new Request(CommonVars
				.getCurrUser(), true));
		if (list != null && list.size() > 0) {
			String emsno = ((EmsHeadH2k) list.get(0)).getEmsNo();
			jLabel.setText("帐册编号：" + emsno);
		}
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
						list.add(addColumn("帐册序号", "seqNum", 60));
						list.add(addColumn("成品版本", "version", 60));
						list.add(addColumn("商品编码", "complex", 80));
						list.add(addColumn("商品名称", "name", 120));
						list.add(addColumn("型号规格", "spec", 120));
						list.add(addColumn("单位", "unit", 50));
						list.add(addColumn("禁止时间", "forbiddate", 80));
						list.add(addColumn("禁止人", "forbiduser", 60));
						list.add(addColumn("恢复时间", "revertdate", 80));
						list.add(addColumn("恢复人", "revertuser", 80));
						return list;
					}
				});
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
			jContentPane.add(getJToolBar(), BorderLayout.NORTH);
			jContentPane.add(getJPanel1(), BorderLayout.CENTER);
		} // @jve:decl-index=0:
		return jContentPane;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			lbSerialNo = new JLabel();
			lbSerialNo.setBounds(new Rectangle(442, 4, 54, 24));
			lbSerialNo.setText("账册序号");
			jLabel3 = new JLabel();
			jLabel2 = new JLabel();
			jLabel1 = new JLabel();
			jLabel = new JLabel();
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setPreferredSize(new Dimension(1000, 30));
			jLabel.setBounds(283, 4, 151, 24);
			jLabel.setText("帐册编号");
			jLabel1.setBounds(578, 4, 58, 24);
			jLabel1.setText("禁用时间");
			jLabel2.setBounds(738, 4, 23, 23);
			jLabel2.setText("至");
			jLabel3.setBounds(135, 5, 32, 23);
			jLabel3.setText("类型");
			jPanel.add(jLabel, null);
			jPanel.add(jLabel1, null);
			jPanel.add(getJCalendarComboBox(), null);
			jPanel.add(jLabel2, null);
			jPanel.add(getJCalendarComboBox1(), null);
			jPanel.add(jLabel3, null);
			jPanel.add(getJComboBox(), null);
			jPanel.add(getJButton2(), null);
			jPanel.add(lbSerialNo, null);
			jPanel.add(getTfSerialNo(), null);
			jPanel.add(getBtnHis(), null);
			jPanel.add(getJButton1(), null);
			jPanel.add(getJButton(), null);
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
			jTable.setRowHeight(25);
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
			jCalendarComboBox.setBounds(637, 4, 98, 23);
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
			jCalendarComboBox1.setBounds(757, 3, 97, 24);
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
			jButton.setText("新增");
			jButton.setBounds(new Rectangle(3, 3, 64, 25));
			jButton.setPreferredSize(new Dimension(64, 30));
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					manualDecleareAction.controlFmCommodityForbidAdd(new Request(CommonVars.getCurrUser()));
					add();
				}
			});
		}
		return jButton;
	}

	private void add() {
		boolean isMaterial = true;
		if (jComboBox.getSelectedItem() != null
				&& jComboBox.getSelectedItem().equals("料件")) {
			isMaterial = true;
		} else {
			isMaterial = false;
		}
		List list = CommonQuery.getInstance()
				.getTempCustomsDeclarationCommInfo(isMaterial);
		if (list == null) {
			return;
		}
		list = manualDecleareAction.addCommdityForbid(new Request(CommonVars
				.getCurrUser()), list, isMaterial);
		tableModel.addRows(list);

	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("恢复");
			jButton1.setBounds(new Rectangle(70, 3, 64, 25));
			jButton1.setPreferredSize(new Dimension(64, 30));
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					manualDecleareAction.controlFmCommodityForbidBack(new Request(CommonVars.getCurrUser()));
					if (tableModel.getCurrentRow() == null) {
						return;
					}
					boolean isMaterial = true;
					if (jComboBox.getSelectedItem() != null
							&& jComboBox.getSelectedItem().equals("料件")) {
						isMaterial = true;
					} else {
						isMaterial = false;
					}
					List ls = tableModel.getCurrentRows();
					for (int i=0;i<ls.size();i++){
						CommdityForbid obj = (CommdityForbid) ls.get(i);
						String seqNum = obj.getSeqNum();
						String version = obj.getVersion();
						manualDecleareAction.deleteCommdityForbid(new Request(
								CommonVars.getCurrUser()), obj);
						manualDecleareAction.changeEmsEdiForbid(new Request(
								CommonVars.getCurrUser()), seqNum, isMaterial,
								false, version);
						tableModel.deleteRow(obj);
					}
				}
			});
		}
		return jButton1;
	}

	private void findList(boolean isMateriel) {
		Date begindate = jCalendarComboBox.getDate();
		Date enddate = jCalendarComboBox1.getDate();
		String serialNo = tfSerialNo.getText();
		List list = manualDecleareAction.findCommdityForbid(new Request(
				CommonVars.getCurrUser()), isMateriel,serialNo, begindate, enddate);
		initTable(list);
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.addItem("料件");
			jComboBox.addItem("成品");
			jComboBox.setBounds(168, 4, 112, 24);
			jComboBox.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (jComboBox.getSelectedItem() != null
							&& jComboBox.getSelectedItem().equals("料件")) {
						findList(true);
					} else if (jComboBox.getSelectedItem() != null
							&& jComboBox.getSelectedItem().equals("成品")) {
						findList(false);
					}
				}
			});
		}
		return jComboBox;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("查询");
			jButton2.setBounds(new Rectangle(856, 5, 60, 21));
			jButton2.setPreferredSize(new Dimension(64, 30));
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jComboBox.getSelectedItem() != null
							&& jComboBox.getSelectedItem().equals("料件")) {
						findList(true);
					} else if (jComboBox.getSelectedItem() != null
							&& jComboBox.getSelectedItem().equals("成品")) {
						findList(false);
					}
				}
			});
		}
		return jButton2;
	}

	/**
	 * This method initializes jToolBar	
	 * 	
	 * @return javax.swing.JToolBar	
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			FlowLayout f=new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(1);
			f.setHgap(1);
			jToolBar = new JToolBar();
			jToolBar.setPreferredSize(new Dimension(1216, 36));
			jToolBar.setLayout(f);
			jToolBar.setFloatable(false);
			jToolBar.add(getJPanel());
			
			
		}
		return jToolBar;
	}

	/**
	 * This method initializes tfSerialNo	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfSerialNo() {
		if (tfSerialNo == null) {
			tfSerialNo = new JTextField();
			tfSerialNo.setBounds(new Rectangle(498, 4, 74, 24));
		}
		return tfSerialNo;
	}

	/**
	 * This method initializes btnHis	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnHis() {
		if (btnHis == null) {
			btnHis = new JButton();
			btnHis.setBounds(new Rectangle(920, 5, 60, 21));
			btnHis.setText("历史");
			btnHis.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgCommodityForbidHis dg = new DgCommodityForbidHis();
					dg.setVisible(jComboBox.getSelectedItem().toString(), jCalendarComboBox.getDate(), 
							jCalendarComboBox1.getDate(), tfSerialNo.getText());
				}
			});
		}
		return btnHis;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
