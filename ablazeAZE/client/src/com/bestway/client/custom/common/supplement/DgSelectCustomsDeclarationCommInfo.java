/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DgErrorMessage.java
 *
 * Created on Mar 21, 2009, 11:33:32 AM
 */
package com.bestway.client.custom.common.supplement;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxUI;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.constant.SupplmentType;
import com.bestway.customs.common.action.BaseEncAction;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;
import com.bestway.dzsc.dzscmanage.action.DzscAction;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * 
 * @author chl
 */
@SuppressWarnings("unchecked")
public class DgSelectCustomsDeclarationCommInfo extends JDialogBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel pnContext = null;
	private JPanel pnTop = null;
	private JPanel pnBottom = null;
	private JTable tblCenter = null;
	protected JTableListModel tableModel = null;
	protected Integer projectType = 0; // @jve:decl-index=0:
	private JLabel lbCommit = null;
	private JButton btnPrev = null;
	private JButton btnNext = null;
	private JComboBox cbbEmshead = null;
	private JLabel lbSerialNumber = null;
	private JTextField tfSerialNumber = null;
	private JScrollPane jScrollPane = null;

	private String emsSpec = "账册编号"; // @jve:decl-index=0:
	private Integer supplmentType;
	public Integer result = Integer.valueOf(0);  //  @jve:decl-index=0:
	
	private BaseCustomsDeclaration baseCustomsDeclaration;
	private BaseCustomsDeclarationCommInfo baseCustomsDeclarationCommInfo;  //  @jve:decl-index=0:
	
	private ManualDeclareAction manualDeclareAction = null;
	private ContractAction contractAction = null;
	private DzscAction dzscAction = null;

	private BaseEncAction baseEncAction;
	private JLabel lbSupplmentType = null;
	private JComboBox cbbSupplmentType = null;

	/** Creates new form DgErrorMessage */
	public DgSelectCustomsDeclarationCommInfo(int projectType, Integer supplmentType, BaseCustomsDeclaration baseCustomsDeclaration) {
		super();
		this.projectType = projectType;
		this.supplmentType = supplmentType;
		this.baseCustomsDeclaration = baseCustomsDeclaration;
		String id = baseCustomsDeclaration.getId();
		manualDeclareAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		contractAction = (ContractAction) CommonVars.getApplicationContext()
				.getBean("contractAction");
		dzscAction = (DzscAction) CommonVars.getApplicationContext().getBean(
				"dzscAction");
		switch (projectType) {
		case ProjectType.BCUS:
			baseEncAction = (BaseEncAction) CommonVars.getApplicationContext()
					.getBean("encAction");
			break;
		case ProjectType.BCS:
			baseEncAction = (BaseEncAction) CommonVars.getApplicationContext()
					.getBean("contractExeAction");
			break;
		case ProjectType.DZSC:
			baseEncAction = (BaseEncAction) CommonVars.getApplicationContext()
					.getBean("dzscContractExeAction");
			break;
		}
		initialize();
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				result = 0;
			}
		});
	}

	private void initialize() {
		this.setSize(new Dimension(650, 450));
		this.setContentPane(getPnContext());
		setTitle("选择需要补充申报的报关单【商品】");
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				result = Integer.valueOf(0);
			}
		});
	}

	public void setVisible(boolean b) {
		if (b) {
			this.initUI();
			this.initTable(baseEncAction.findCustomsDeclarationCommInfo(new Request(CommonVars
					.getCurrUser()), baseCustomsDeclaration));
		}
		super.setVisible(b);
	}

	private void initUI() {
		if(baseCustomsDeclaration != null) {
			tfSerialNumber.setText(baseCustomsDeclaration.getSerialNumber().toString());
		}
		cbbEmshead.removeAllItems();
		
		List contracts = null;
		switch (projectType) {
		case ProjectType.BCS:
			// 电子化手册
			contracts = contractAction.findContractByProcessExe(new Request(
					CommonVars.getCurrUser(), true));
			if (contracts != null && contracts.size() > 0) {
				for (int i = 0; i < contracts.size(); i++) {
					this.cbbEmshead.addItem((Contract) contracts.get(i));
				}
				this.cbbEmshead.setRenderer(CustomBaseRender.getInstance()
						.getRender("emsNo", "impContractNo", 100, 150)
						.setForegroundColor(java.awt.Color.red));
			}
			emsSpec = "手册编号";
			break;
		case ProjectType.BCUS:
			// 电子帐册
			contracts = manualDeclareAction
					.findEmsHeadH2kInExecuting(new Request(CommonVars
							.getCurrUser(), true));
			if (contracts != null && contracts.size() > 0) {
				for (int i = 0; i < contracts.size(); i++) {
					this.cbbEmshead.addItem((EmsHeadH2k) contracts.get(i));
				}
				this.cbbEmshead.setRenderer(CustomBaseRender.getInstance()
						.getRender("preEmsNo", "emsNo", 0, 150));
			}
			emsSpec = "账册编号";
			break;
		case ProjectType.DZSC:
			// 电子手册
			contracts = dzscAction.findExeEmsPorHead(new Request(CommonVars
					.getCurrUser()));
			if (contracts != null && contracts.size() > 0) {
				for (int i = 0; i < contracts.size(); i++) {
					this.cbbEmshead.addItem((DzscEmsPorHead) contracts.get(i));
				}
				this.cbbEmshead.setRenderer(CustomBaseRender.getInstance()
						.getRender("emsNo", "ieContractNo", 100, 150));
			}
			emsSpec = "手册编号";
			break;
		}
		// 选择第一行
		if (this.cbbEmshead.getItemCount() > 0) {
			this.cbbEmshead.setSelectedIndex(0);
		}

		lbCommit.setText(emsSpec);
	}

	/**
	 * This method initializes pnContext
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnContext() {
		if (pnContext == null) {
			BorderLayout borderLayout = new BorderLayout();
			borderLayout.setHgap(30);
			pnContext = new JPanel();
			pnContext.setLayout(borderLayout);
			pnContext.add(getPnTop(), BorderLayout.NORTH);
			pnContext.add(getPnBottom(), BorderLayout.SOUTH);
			pnContext.add(getJScrollPane(), BorderLayout.CENTER);
		}
		return pnContext;
	}

	/**
	 * This method initializes pnTop
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnTop() {
		if (pnTop == null) {
			lbSupplmentType = new JLabel();
			lbSupplmentType.setText("补充申报类型");
			lbSerialNumber = new JLabel();
			lbSerialNumber.setText("报关单流水号");
			lbSerialNumber.setPreferredSize(new Dimension(75, 20));
			lbCommit = new JLabel();
			lbCommit.setText("");
			lbCommit.setPreferredSize(new Dimension(55, 20));
			pnTop = new JPanel();
			pnTop.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
			pnTop.setPreferredSize(new Dimension(300, 35));
			pnTop.add(lbCommit, null);
			pnTop.add(getCbbEmshead(), null);
			pnTop.add(lbSupplmentType, null);
			pnTop.add(getCbbSupplmentType(), null);
			pnTop.add(lbSerialNumber, null);
			pnTop.add(getTfSerialNumber(), null);
		}
		return pnTop;
	}

	/**
	 * This method initializes pnBottom
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnBottom() {
		if (pnBottom == null) {
			pnBottom = new JPanel();
			pnBottom.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 15));
			pnBottom.setPreferredSize(new Dimension(300, 50));
			pnBottom.add(getBtnPrev(), null);
			pnBottom.add(getBtnNext(), null);
		}
		return pnBottom;
	}

	/**
	 * This method initializes tblCenter
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTblCenter() {
		if (tblCenter == null) {
			tblCenter = new JTable();
		}
		return tblCenter;
	}

	protected void initTable(List list) {
		tableModel = new JTableListModel(tblCenter, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("项号", "serialNumber", 60,
								Integer.class));
						list.add(addColumn("商品序号", "commSerialNo", 60,
								Integer.class));
						list.add(addColumn("商品编号", "complex.code", 80));
						list.add(addColumn("商品名称", "commName", 100));
						list.add(addColumn("型号规格", "commSpec", 100));
						list.add(addColumn("数量", "commAmount", 60));
						list.add(addColumn("单位", "unit.name", 60));
						list.add(addColumn("法定数量", "firstAmount", 80));
						list.add(addColumn("法定单位", "legalUnit.name", 80));
						list.add(addColumn("单位重量", "unitWeight", 80));
						list.add(addColumn("最终目的国", "country.name", 80));
						list.add(addColumn("单价", "commUnitPrice", 60));
						list.add(addColumn("总价", "commTotalPrice", 60));
						return list;
					}
				});
		tblCenter
				.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	}

	/**
	 * This method initializes btnPrev
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrev() {
		if (btnPrev == null) {
			btnPrev = new JButton();
			btnPrev.setPreferredSize(new Dimension(80, 20));
			btnPrev.setText("上一步");
			btnPrev.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
					result = -1;
				}
			});
		}
		return btnPrev;
	}

	/**
	 * This method initializes btnNext
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnNext() {
		if (btnNext == null) {
			btnNext = new JButton();
			btnNext.setPreferredSize(new Dimension(80, 20));
			btnNext.setText("下一步");
			btnNext.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(
								DgSelectCustomsDeclarationCommInfo.this, "请选择要补充申报的报关单【商品】",
								"提示", 0);
						return;
					}
					result = 1;
					baseCustomsDeclarationCommInfo = (BaseCustomsDeclarationCommInfo) tableModel.getCurrentRow();
					dispose();
				}
			});
		}
		return btnNext;
	}

	/**
	 * This method initializes cbbEmshead
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbEmshead() {
		if (cbbEmshead == null) {
			cbbEmshead = new JComboBox();
			cbbEmshead.setPreferredSize(new Dimension(110, 25));
			cbbEmshead.setUI(new CustomBaseComboBoxUI(200));
		}
		return cbbEmshead;
	}

	/**
	 * This method initializes tfSerialNumber
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfSerialNumber() {
		if (tfSerialNumber == null) {
			tfSerialNumber = new JTextField();
			tfSerialNumber.setPreferredSize(new Dimension(80, 21));
			tfSerialNumber.setEnabled(false);
		}
		return tfSerialNumber;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTblCenter());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes cbbSupplmentType
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbSupplmentType() {
		if (cbbSupplmentType == null) {
			cbbSupplmentType = new JComboBox();
			cbbSupplmentType.setEnabled(false);
			cbbSupplmentType.setPreferredSize(new Dimension(120, 20));
			cbbSupplmentType
					.addItem(new ItemProperty(
							SupplmentType.INITIATIVE_SYPPLMENT.toString(),
							SupplmentType
									.getSupplmentTypeDesc(SupplmentType.INITIATIVE_SYPPLMENT)));
			cbbSupplmentType
					.addItem(new ItemProperty(
							SupplmentType.PASSIVITY_SYPPLMENT.toString(),
							SupplmentType
									.getSupplmentTypeDesc(SupplmentType.PASSIVITY_SYPPLMENT)));
			if(supplmentType != null) {
				cbbSupplmentType.setSelectedItem(new ItemProperty(supplmentType
						.toString(), SupplmentType
						.getSupplmentTypeDesc(supplmentType)));
			} else {
				cbbSupplmentType.setSelectedItem(new ItemProperty(
						SupplmentType.PASSIVITY_SYPPLMENT.toString(),
						SupplmentType
								.getSupplmentTypeDesc(SupplmentType.PASSIVITY_SYPPLMENT)));
			}
			
		}
		return cbbSupplmentType;
	}

	public BaseCustomsDeclarationCommInfo getBaseCustomsDeclarationCommInfo() {
		return baseCustomsDeclarationCommInfo;
	}

	public void setBaseCustomsDeclarationCommInfo(
			BaseCustomsDeclarationCommInfo baseCustomsDeclarationCommInfo) {
		this.baseCustomsDeclarationCommInfo = baseCustomsDeclarationCommInfo;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
