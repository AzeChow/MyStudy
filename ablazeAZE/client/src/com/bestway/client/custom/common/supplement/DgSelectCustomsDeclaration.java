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
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Calendar;
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
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxUI;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.client.custom.common.DgBaseExportCustomsDeclaration;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.constant.SupplmentType;
import com.bestway.customs.common.action.BaseEncAction;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.customs.common.entity.BaseEmsHead;
import com.bestway.dzsc.dzscmanage.action.DzscAction;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import java.awt.Rectangle;

/**
 * 
 * @author chl
 */
@SuppressWarnings("unchecked")
public class DgSelectCustomsDeclaration extends JDialogBase {

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
	private JButton btnQuery = null;
	private JScrollPane jScrollPane = null;

	private String emsSpec = "账册编号"; // @jve:decl-index=0:
	private Integer supplmentType;
	public Integer result = Integer.valueOf(0);
	private BaseCustomsDeclaration baseCustomsDeclaration; // @jve:decl-index=0:

	private ManualDeclareAction manualDeclareAction = null;
	private ContractAction contractAction = null;
	private DzscAction dzscAction = null;

	private BaseEncAction baseEncAction;
	private JLabel lbSupplmentType = null;
	private JComboBox cbbSupplmentType = null;
	private JLabel lbDeclarationDate = null;
	private JCalendarComboBox cbbBeginDate = null;
	private JLabel lbTo = null;
	private JCalendarComboBox cbbEndDate = null;

	/** Creates new form DgErrorMessage */
	public DgSelectCustomsDeclaration(int projectType, Integer supplmentType) {
		super();
		this.projectType = projectType;
		this.supplmentType = supplmentType;
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
				result = Integer.valueOf(0);
			}
		});
	}

	private void initialize() {
		this.setSize(new Dimension(650, 450));
		this.setContentPane(getPnContext());
		setTitle("选择需要补充申报的报关单");
	}

	public void setVisible(boolean b) {
		if (b) {
			this.initUI();
			this.initTable(new ArrayList());
		}
		super.setVisible(b);
	}

	private void initUI() {
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
		
		cbbSupplmentType.removeAllItems();

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
		if (supplmentType != null) {
			cbbSupplmentType.setSelectedItem(new ItemProperty(supplmentType
					.toString(), SupplmentType
					.getSupplmentTypeDesc(supplmentType)));
		} else {
			cbbSupplmentType
					.setSelectedItem(new ItemProperty(
							SupplmentType.PASSIVITY_SYPPLMENT.toString(),
							SupplmentType
									.getSupplmentTypeDesc(SupplmentType.PASSIVITY_SYPPLMENT)));
		}
		
		/*
		 * 初始化查询条件，开始时间和结束时间
		 * 
		 * 开始时间 默认 为当月的开始时间（就是当月1号凌晨0时）  
		 * 结束时间 默认 为当月的结束时间（就是当月最后一天23点59分59秒999毫秒）
		 */
		Calendar calendar = Calendar.getInstance();    
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));  
	    Date begin = calendar.getTime();
	    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));  
	    Date end = calendar.getTime();
	    
	    cbbBeginDate.setDate(begin);
	    cbbEndDate.setDate(end);

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
			lbTo = new JLabel();
			lbTo.setText("到");
			lbTo.setBounds(new Rectangle(198, 38, 12, 18));
			lbDeclarationDate = new JLabel();
			lbDeclarationDate.setText("进出口日期");
			lbDeclarationDate.setBounds(new Rectangle(5, 38, 63, 18));
			lbSupplmentType = new JLabel();
			lbSupplmentType.setText("补充申报类型");
			lbSupplmentType.setBounds(new Rectangle(198, 8, 72, 18));
			lbSerialNumber = new JLabel();
			lbSerialNumber.setText("报关单流水号");
			lbSerialNumber.setBounds(new Rectangle(400, 7, 75, 20));
			lbSerialNumber.setPreferredSize(new Dimension(75, 20));
			lbCommit = new JLabel();
			lbCommit.setText("");
			lbCommit.setBounds(new Rectangle(5, 7, 63, 20));
			lbCommit.setPreferredSize(new Dimension(63, 20));
			pnTop = new JPanel();
			pnTop.setLayout(null);
			pnTop.setPreferredSize(new Dimension(300, 65));
			pnTop.add(lbCommit, null);
			pnTop.add(getCbbEmshead(), null);
			pnTop.add(lbSupplmentType, null);
			pnTop.add(getCbbSupplmentType(), null);
			pnTop.add(lbSerialNumber, null);
			pnTop.add(getTfSerialNumber(), null);
			pnTop.add(getBtnQuery(), null);
			pnTop.add(lbDeclarationDate, null);
			pnTop.add(getCbbBeginDate(), null);
			pnTop.add(lbTo, null);
			pnTop.add(getCbbEndDate(), null);
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
						list.add(addColumn("流水号", "serialNumber", 40,
								Integer.class)); // 1
						list.add(addColumn("报关单号", "customsDeclarationCode",
								100)); // 2
						list.add(addColumn("进出口类型", "impExpType", 80)); // 3
						list.add(addColumn("进出口日期", "impExpDate", 80)); // 4
						list.add(addColumn("申报日期", "declarationDate", 80)); // 5
						list.add(addColumn(emsSpec, "emsHeadH2k", 100));// "帐册编号"
						// // 6
						return list;
					}
				});
		tblCenter.getColumnModel().getColumn(3).setCellRenderer(
				new DefaultTableCellRenderer() {
					private static final long serialVersionUID = 1L;

					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						String str = "";
						if (value != null) {
							str = ImpExpType.getImpExpTypeDesc(Integer
									.parseInt(value.toString()));
						}
						this.setText(str);
						return this;
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
								DgSelectCustomsDeclaration.this,
								"请选择要补充申报的报关单", "提示", 0);
						return;
					}
					result = 1;
					baseCustomsDeclaration = (BaseCustomsDeclaration) tableModel
							.getCurrentRow();
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
			cbbEmshead.setPreferredSize(new Dimension(120, 25));
			cbbEmshead.setBounds(new Rectangle(73, 5, 120, 25));
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
			tfSerialNumber.setPreferredSize(new Dimension(70, 21));
			tfSerialNumber.setBounds(new Rectangle(480, 7, 70, 21));
		}
		return tfSerialNumber;
	}

	/**
	 * This method initializes btnQuery
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnQuery() {
		if (btnQuery == null) {
			btnQuery = new JButton();
			btnQuery.setPreferredSize(new Dimension(80, 20));
			btnQuery.setBounds(new Rectangle(554, 7, 80, 21));
			btnQuery.setText("查询");
			btnQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					initTable(baseEncAction
							.getCustomsDeclaration(new Request(CommonVars
									.getCurrUser()), ((BaseEmsHead) (cbbEmshead
									.getSelectedItem())).getEmsNo(),
									supplmentType,
									CommonUtils.notEmpty(tfSerialNumber
											.getText()) ? Integer
											.valueOf(tfSerialNumber.getText())
											: null, cbbBeginDate.getDate(), cbbEndDate.getDate()));
				}
			});
		}
		return btnQuery;
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
			cbbSupplmentType.setBounds(new Rectangle(275, 7, 120, 20));
			cbbSupplmentType.setPreferredSize(new Dimension(120, 20));
		}
		return cbbSupplmentType;
	}

	public BaseCustomsDeclaration getBaseCustomsDeclaration() {
		return baseCustomsDeclaration;
	}

	public void setBaseCustomsDeclaration(
			BaseCustomsDeclaration baseCustomsDeclaration) {
		this.baseCustomsDeclaration = baseCustomsDeclaration;
	}

	public void setSupplmentType(Integer supplmentType) {
		this.supplmentType = supplmentType;
	}

	/**
	 * This method initializes cbbBeginDate	
	 * 	
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox	
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setPreferredSize(new Dimension(120, 24));
			cbbBeginDate.setBounds(new Rectangle(73, 35, 120, 24));
		}
		return cbbBeginDate;
	}

	/**
	 * This method initializes cbbEndDate	
	 * 	
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox	
	 */
	private JCalendarComboBox getCbbEndDate() {
		if (cbbEndDate == null) {
			cbbEndDate = new JCalendarComboBox();
			cbbEndDate.setPreferredSize(new Dimension(120, 24));
			cbbEndDate.setBounds(new Rectangle(215, 35, 120, 24));
		}
		return cbbEndDate;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
