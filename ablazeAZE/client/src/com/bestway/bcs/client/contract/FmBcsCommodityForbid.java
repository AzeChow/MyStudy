/*
 * Created on 2005-12-26
 * 商品禁用功能
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.client.contract;

import javax.swing.JFrame;

import javax.swing.JSplitPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JLabel;

import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcs.contract.entity.BcsCommdityForbid;
import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.enc.entity.CustomsDeclaration;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.CommdityForbid;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.client.custom.common.EncCommon;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.Rectangle;

/**
 * @author wp
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FmBcsCommodityForbid extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;

	private JSplitPane jSplitPane = null;

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

	private JButton jButton2 = null;

	private JComboBox cbEmsNo = null;

	private ContractAction contractAction = null;  //  @jve:decl-index=0:
	/**
	 * This is the default constructor
	 */
	public FmBcsCommodityForbid() {
		super();
		contractAction = (ContractAction) CommonVars
           .getApplicationContext().getBean("contractAction");
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
		this.setSize(828, 537);
		this.setContentPane(getJContentPane());
		this.setTitle("帐册商品禁用功能");
		initTable(null);
		initUIComponents();
		findList(true);
	}

	private void initUIComponents() {
		// 电子化手册
		cbEmsNo.removeAllItems();
		List contracts = contractAction.findContractByProcessExe(new Request(
				CommonVars.getCurrUser(), true));
		if (contracts != null && contracts.size() > 0) {
			for (int i = 0; i < contracts.size(); i++) {
				this.cbEmsNo.addItem((Contract) contracts.get(i));
			}
			this.cbEmsNo.setRenderer(CustomBaseRender.getInstance()
					.getRender("emsNo", "impContractNo", 100, 150)
					.setForegroundColor(java.awt.Color.red));
		}
		if (this.cbEmsNo.getItemCount() > 0) {
			this.cbEmsNo.setSelectedIndex(0);
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
						list.add(addColumn("帐册序号", "seqNum", 80));
						list.add(addColumn("成品版本", "version", 80));
						list.add(addColumn("商品编码", "complex", 100));
						list.add(addColumn("商品名称", "name", 120));
						list.add(addColumn("型号规格", "spec", 120));
						list.add(addColumn("单位", "unit", 120));
						list.add(addColumn("禁止时间", "forbiddate", 80));
						list.add(addColumn("禁止人", "forbiduser", 80));
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
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
		} // @jve:decl-index=0:
		return jContentPane;
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
			jSplitPane.setTopComponent(getJPanel());
			jSplitPane.setBottomComponent(getJPanel1());
			jSplitPane.setDividerLocation(80);
			jSplitPane.setDividerSize(5);
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
			jLabel2 = new JLabel();
			jLabel1 = new JLabel();
			jLabel = new JLabel();
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jLabel.setBounds(18, 44, 57, 24);
			jLabel.setText("帐册编号");
			jLabel1.setBounds(263, 45, 58, 24);
			jLabel1.setText("禁用时间");
			jLabel2.setBounds(438, 45, 23, 23);
			jLabel2.setText("至");
			jLabel3.setBounds(42, 12, 32, 23);
			jLabel3.setText("类型");
			jPanel.add(jLabel, null);
			jPanel.add(jLabel1, null);
			jPanel.add(getJCalendarComboBox(), null);
			jPanel.add(jLabel2, null);
			jPanel.add(getJCalendarComboBox1(), null);
			jPanel.add(getJButton(), null);
			jPanel.add(getJButton1(), null);
			jPanel.add(jLabel3, null);
			jPanel.add(getJComboBox(), null);
			jPanel.add(getJButton2(), null);
			jPanel.add(getCbEmsNo(), null);
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
			jCalendarComboBox.setBounds(324, 45, 98, 23);
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
			jCalendarComboBox1.setBounds(466, 45, 97, 24);
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
			jButton.setBounds(642, 45, 68, 24);
			jButton.setText("新增");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
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
		Contract contract=null;
		String emsNo="";
		contract=(Contract)cbEmsNo.getSelectedItem();
		if(contract!=null){
			emsNo=contract.getEmsNo();
		}
		List list = CommonQuery.getInstance()
				.getTempBcsCustomsDeclarationCommInfo(isMaterial,emsNo);
		if (list == null) {
			return;
		}
		list = contractAction.addCommdityForbid(new Request(CommonVars
				.getCurrUser()), list, isMaterial,emsNo);
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
			jButton1.setBounds(714, 45, 68, 24);
			jButton1.setText("恢复");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
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
					Contract contract=null;
					String emsNo="";
					contract=(Contract)cbEmsNo.getSelectedItem();
					if(contract!=null){
						emsNo=contract.getEmsNo();
					}
					List ls = tableModel.getCurrentRows();
					for (int i=0;i<ls.size();i++){
						BcsCommdityForbid obj = (BcsCommdityForbid) ls.get(i);
						String seqNum = obj.getSeqNum();
						contractAction.deleteCommdityForbid(new Request(
								CommonVars.getCurrUser()), obj);
						contractAction.changeEmsEdiForbid(new Request(
								CommonVars.getCurrUser()), emsNo,seqNum, isMaterial);
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
		Contract contract=null;
		String emsNo="";
		contract=(Contract)cbEmsNo.getSelectedItem();
		if(contract!=null){
			emsNo=contract.getEmsNo();
		}
		List list = contractAction.findCommdityForbid(new Request(
				CommonVars.getCurrUser()), emsNo,isMateriel, begindate, enddate);
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
			jComboBox.setBounds(79, 12, 174, 24);
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
			jButton2.setBounds(new Rectangle(570, 45, 68, 24));
			jButton2.setText("查询");
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
	 * This method initializes cbEmsNo	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbEmsNo() {
		if (cbEmsNo == null) {
			cbEmsNo = new JComboBox();
			cbEmsNo.setBounds(new Rectangle(79, 44, 175, 24));
			cbEmsNo.addActionListener(new java.awt.event.ActionListener() {
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
		return cbEmsNo;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
