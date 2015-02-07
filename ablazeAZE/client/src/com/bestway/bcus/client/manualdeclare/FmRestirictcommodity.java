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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;

import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.manualdeclare.entity.RestirictCommodity;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import javax.swing.JScrollBar;
import javax.swing.JToolBar;

public class FmRestirictcommodity extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;
	private JPanel jPanel = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JCalendarComboBox jCalendarComboBox = null;
	private JLabel jLabel2 = null;
	private JCalendarComboBox jCalendarComboBox1 = null;
	private JButton btnNew = null;
	private JButton btnResort = null;
	private JLabel jLabel3 = null;
	private JComboBox jComboBox = null;
	private JButton btnSearch = null;
	private JScrollPane jScrollPane = null;
	private JTable jTable = null;
	private JTableListModel tableModel = null;
	private ManualDeclareAction manualDecleareAction = null;
	private JButton btnEdit = null;
	private JToolBar jToolBar = null;
	/**
	 * This method initializes
	 * 
	 */
	public FmRestirictcommodity() {
		manualDecleareAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		manualDecleareAction.restirictCommodity(new Request(CommonVars
				.getCurrUser()));
		initialize();
		this.jCalendarComboBox.setDate(CommonVars.getBeginDate());
		this.jCalendarComboBox1.setDate(new Date());
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(843, 446));
		this.setContentPane(getJContentPane());
		this.setTitle("限制类商品维护");
		this.setVisible(false);
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

	private void initTable(List list) {

		if (list == null) {
			list = new ArrayList();
		}
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("帐册序号", "seqNum", 80));
						list.add(addColumn("商品编码", "complex", 100));
						list.add(addColumn("商品名称", "name", 120));
						list.add(addColumn("型号规格", "spec", 120));
						list.add(addColumn("单位", "unit", 60));
						list.add(addColumn("合同定量", "amount", 80));
						list.add(addColumn("维护时间", "vindicadate", 80));
						list.add(addColumn("维护人", "vindicator", 80));
						list.add(addColumn("恢复时间", "revertdate", 80));
						list.add(addColumn("恢复人", "revertuser", 80));

						return list;
					}
				});
	}

	private void findList(boolean isMateriel) {
		// Date begindate = jCalendarComboBox.getDate();
		// Date enddate = jCalendarComboBox1.getDate();
		List list = manualDecleareAction.findRestirictCommodity(new Request(
				CommonVars.getCurrUser()), isMateriel, jCalendarComboBox
				.getDate(), jCalendarComboBox1.getDate());
		initTable(list);
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
			jContentPane.add(getJToolBar(), BorderLayout.NORTH);
			jContentPane.add(getJScrollPane(), BorderLayout.CENTER);
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
			jLabel3 = new JLabel();
			jLabel3.setText("类型");
			jLabel3.setBounds(new Rectangle(3, 7, 26, 18));
			jLabel2 = new JLabel();
			jLabel2.setText("至");
			jLabel2.setBounds(new Rectangle(427, 7, 13, 18));
			jLabel1 = new JLabel();
			jLabel1.setText("维护时间");
			jLabel1.setBounds(new Rectangle(279, 8, 52, 18));
			jLabel = new JLabel();
			jLabel.setText("帐册编号：");
			jLabel.setBounds(new Rectangle(125, 10, 158, 18));
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setPreferredSize(new Dimension(600, 34));
			jPanel.add(jLabel3, null);
			jPanel.add(jLabel, null);
			jPanel.add(jLabel1, null);
			jPanel.add(getJCalendarComboBox(), null);
			jPanel.add(jLabel2, null);
			jPanel.add(getJCalendarComboBox1(), null);
			jPanel.add(getJComboBox(), null);
			jPanel.add(getBtnSearch(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getJCalendarComboBox() {
		if (jCalendarComboBox == null) {
			jCalendarComboBox = new JCalendarComboBox();
			jCalendarComboBox.setBounds(new Rectangle(332, 6, 91, 22));
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
			jCalendarComboBox1.setBounds(new Rectangle(441, 5, 86, 22));
		}
		return jCalendarComboBox1;
	}

	/**
	 * This method initializes btnNew
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnNew() {
		if (btnNew == null) {
			btnNew = new JButton();
			btnNew.setText("新增");
			btnNew.setPreferredSize(new Dimension(60, 30));
			btnNew.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					boolean isMaterial = true;
					if (jComboBox.getSelectedItem() != null
							&& jComboBox.getSelectedItem().equals("料件")) {
						isMaterial = true;
					} else {
						isMaterial = false;
					}
					List list = CommonQuery.getInstance()
							.getRestirictCommodity(isMaterial);
					if (list == null) {
						return;
					}
					list = manualDecleareAction.addRestirictCommodity(
							new Request(CommonVars.getCurrUser()), list,
							isMaterial);
					tableModel.addRows(list);

				} // TODO Auto-generated Event stub actionPerformed()
			});
		}
		return btnNew;
	}

	/**
	 * This method initializes btnResort
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnResort() {
		if (btnResort == null) {
			btnResort = new JButton();
			btnResort.setText("恢复");
			btnResort.setPreferredSize(new Dimension(60, 30));
			btnResort.addActionListener(new java.awt.event.ActionListener() {
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
					List ls = tableModel.getCurrentRows();
					for (int i = 0; i < ls.size(); i++) {
						RestirictCommodity obj = (RestirictCommodity) ls.get(i);
						String seqNum = obj.getSeqNum();
						manualDecleareAction.deleteRestirictCommodity(
								new Request(CommonVars.getCurrUser()), obj);
						tableModel.deleteRow(obj);
					}
				}
			});
		}
		return btnResort;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setBounds(new Rectangle(31, 3, 93, 27));
			jComboBox.addItem("料件");
			jComboBox.addItem("成品");
			jComboBox.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (jComboBox.getSelectedItem() != null
							&& jComboBox.getSelectedItem().equals("料件")) {
						findList(true);
					} else if (jComboBox.getSelectedItem() != null
							&& jComboBox.getSelectedItem().equals("成品")) {
						findList(false);
					} // TODO Auto-generated Event stub itemStateChanged()
				}
			});
		}
		return jComboBox;
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
			btnSearch.setBounds(new Rectangle(531, 5, 60, 21));
			btnSearch.setPreferredSize(new Dimension(60, 30));
			btnSearch.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jComboBox.getSelectedItem() != null
							&& jComboBox.getSelectedItem().equals("料件")) {
						findList(true);
					} else if (jComboBox.getSelectedItem() != null
							&& jComboBox.getSelectedItem().equals("成品")) {
						findList(false);
					}// TODO Auto-generated Event stub actionPerformed()
				}
			});
		}
		return btnSearch;
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
	 * This method initializes btnEdit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改");
			btnEdit.setPreferredSize(new Dimension(60, 30));
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(
								FmRestirictcommodity.this, "请选择你将要修改的记录",
								"提示！", 0);
						return;
					}
					boolean isMaterial = true;
					if (jComboBox.getSelectedItem() != null
							&& jComboBox.getSelectedItem().equals("料件")) {
						isMaterial = true;
					} else {
						isMaterial = false;
					}
					DgRestirictCommodity yy = new DgRestirictCommodity();
					yy.setMaterial(isMaterial);
					yy.setTableModel(tableModel);
					yy.setVisible(true);
				}
			});
		}
		return btnEdit;
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
			jToolBar.setLayout(f);
			jToolBar.setFloatable(false);
			jToolBar.add(getBtnNew());
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnResort());
			jToolBar.add(getJPanel());
		}
		return jToolBar;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
