/*
 * Created on 2005-12-26
 * 归并关系禁用功能
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.innermerge;

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
import javax.swing.JTable;
import javax.swing.JToolBar;

import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.client.manualdeclare.DgEmsEdiMerger;
import com.bestway.bcus.innermerge.action.CommonBaseCodeAction;
import com.bestway.bcus.innermerge.entity.InnerMergeData;
import com.bestway.bcus.manualdeclare.entity.CommdityForbid;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author wp
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class FmInnerMergeForbid extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private JLabel jLabel1 = null;

	private JCalendarComboBox cbbForbidBeginDate = null;

	private JLabel jLabel2 = null;

	private JCalendarComboBox cbbForbidEndDate = null;

	private JButton btnForbid = null;

	private JButton btnRevert = null;

	private JTableListModel tableModel = null;

	private JLabel jLabel3 = null;

	private JComboBox cbbType = null;

	private CommonBaseCodeAction commonBaseCodeAction = null;

	private JButton btnSrearch = null;

	private JToolBar jToolBar = null;

	/**
	 * This is the default constructor
	 */
	public FmInnerMergeForbid() {
		super();
		commonBaseCodeAction = (CommonBaseCodeAction) CommonVars
				.getApplicationContext().getBean("commonBaseCodeAction");
		commonBaseCodeAction.checkInnerMergeDataIsForbidAuthority(new Request(
				CommonVars.getCurrUser()));
		initialize();
		this.cbbForbidBeginDate.setDate(CommonVars.getBeginDate());
		this.cbbForbidEndDate.setDate(new Date());
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(828, 537);
		this.setContentPane(getJContentPane());
		this.setTitle("归并关系禁用功能");
		findList();
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
						list.add(addColumn("工厂料号", "materiel.ptNo", 100));
						list.add(addColumn("品名", "materiel.factoryName", 100));
						list
								.add(addColumn("规格型号", "materiel.factorySpec",
										100));
						list
								.add(addColumn("企业单位", "materiel.calUnit.name",
										60));
						list.add(addColumn("单位折算", "materiel.unitConvert", 80));
						list.add(addColumn("备案序号", "hsAfterTenMemoNo", 50,
								Integer.class));
						list.add(addColumn("10位商品编码", "hsAfterComplex.code",
								100));
						list.add(addColumn("商品名称", "hsAfterMaterielTenName",
								100));

						list.add(addColumn("商品规格,型号", "hsAfterMaterielTenSpec",
								100));
						JTableListColumn fprbodClumn = addColumn("禁用日期",
								"forbidDate", 120);
						fprbodClumn.setDataFormat("yyyy-MM-dd:HH:ss:mm");
						list.add(fprbodClumn);
						list.add(addColumn("禁用人", "forbidUser", 80));
						JTableListColumn revertClumn = addColumn("恢复日期",
								"revertDate", 120);
						revertClumn.setDataFormat("yyyy-MM-dd:HH:ss:mm");
						list.add(revertClumn);
						list.add(addColumn("恢复人", "revertUser", 80));
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
			jLabel3 = new JLabel();
			jLabel2 = new JLabel();
			jLabel1 = new JLabel();
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setPreferredSize(new Dimension(170, 30));
			jLabel1.setText("禁用时间");
			jLabel2.setText("至");
			jLabel3.setBounds(9, 5, 32, 23);
			jLabel3.setText("类型");
			jPanel.add(jLabel3, null);
			jPanel.add(getCbbType(), null);
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
	 * This method initializes cbbForbidBeginDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbForbidBeginDate() {
		if (cbbForbidBeginDate == null) {
			cbbForbidBeginDate = new JCalendarComboBox();
			cbbForbidBeginDate.setPreferredSize(new Dimension(100, 22));
		}
		return cbbForbidBeginDate;
	}

	/**
	 * This method initializes cbbForbidEndDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbForbidEndDate() {
		if (cbbForbidEndDate == null) {
			cbbForbidEndDate = new JCalendarComboBox();
			cbbForbidEndDate.setPreferredSize(new Dimension(100, 22));
		}
		return cbbForbidEndDate;
	}

	/**
	 * This method initializes btnForbid
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnForbid() {
		if (btnForbid == null) {
			btnForbid = new JButton();
			btnForbid.setText("禁用");
			btnForbid.setPreferredSize(new Dimension(64, 30));
			btnForbid.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					add();
				}
			});
		}
		return btnForbid;
	}

	/**
	 * 增加禁用
	 */
	private void add() {
		String materielType = ((ItemProperty) cbbType.getSelectedItem())
				.getCode();
		if (materielType == null) {
			return;
		}
		List list = CommonQuery.getInstance().getInnerMergeDataIsNotForbid(
				materielType);
		if (list == null) {
			return;
		}
		List returnList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			InnerMergeData data = (InnerMergeData) list.get(i);
			commonBaseCodeAction.updateInnerMergeDataIsForbid(new Request(
					CommonVars.getCurrUser()), materielType, data.getMateriel()
					.getId(), true, CommonVars.getCurrUser().getUserName());
			data = commonBaseCodeAction.findInnerMergeDataById(new Request(
					CommonVars.getCurrUser()), data.getId());
			returnList.add(data);
		}
		tableModel.addRows(returnList);

	}

	/**
	 * This method initializes btnRevert
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnRevert() {
		if (btnRevert == null) {
			btnRevert = new JButton();
			btnRevert.setText("恢复");
			btnRevert.setPreferredSize(new Dimension(64, 30));
			btnRevert.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						return;
					}
					List list = tableModel.getCurrentRows();
					if (list.size() <= 0) {
						JOptionPane.showMessageDialog(FmInnerMergeForbid.this,
								"请选择要恢复的行！", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					if (JOptionPane.showConfirmDialog(FmInnerMergeForbid.this, "确定要恢复数据吗","提示",0)!=0) {
						return ;
					}
					String materielType = ((ItemProperty) cbbType
							.getSelectedItem()).getCode();
					if (materielType == null) {
						return;
					}
					for (int i = 0; i < list.size(); i++) {
						InnerMergeData data = (InnerMergeData) list.get(i);
						commonBaseCodeAction.updateInnerMergeDataIsForbid(
								new Request(CommonVars.getCurrUser()),
								materielType, data.getMateriel().getId(),
								false, CommonVars.getCurrUser().getUserName());
					}
					tableModel.deleteRows(list);
				}
			});
		}
		return btnRevert;
	}

	/**
	 * 查询数据
	 */
	private void findList() {
		String materielType = ((ItemProperty) cbbType.getSelectedItem())
				.getCode();
		if (materielType == null) {
			return;
		}
		Date begindate = cbbForbidBeginDate.getDate();
		Date enddate = cbbForbidEndDate.getDate();
		List list = commonBaseCodeAction.findInnerMergeDataIsForbid(
				new Request(CommonVars.getCurrUser()), begindate, enddate,
				materielType, true);
		initTable(list);
	}

	/**
	 * This method initializes cbbType
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbType() {
		if (cbbType == null) {
			cbbType = new JComboBox();
			cbbType.addItem(new ItemProperty(MaterielType.FINISHED_PRODUCT,
					"成品"));
			cbbType.addItem(new ItemProperty(MaterielType.MATERIEL, "料件"));
			cbbType.setBounds(48, 4, 112, 24);
			cbbType.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					findList();
				}
			});
		}
		return cbbType;
	}

	/**
	 * This method initializes btnSrearch
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSrearch() {
		if (btnSrearch == null) {
			btnSrearch = new JButton();
			btnSrearch.setText("查询");
			btnSrearch.setPreferredSize(new Dimension(64, 30));
			btnSrearch.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					findList();
				}
			});
		}
		return btnSrearch;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			FlowLayout f = new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(1);
			f.setHgap(1);
			jToolBar = new JToolBar();
			jToolBar.setLayout(f);
			jToolBar.setFloatable(false);
			jToolBar.add(getJPanel());
			jToolBar.add(getCbbForbidBeginDate());
			jToolBar.add(getCbbForbidEndDate());
			jToolBar.add(getBtnSrearch());
			jToolBar.add(getBtnForbid());
			jToolBar.add(getBtnRevert());

		}
		return jToolBar;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
