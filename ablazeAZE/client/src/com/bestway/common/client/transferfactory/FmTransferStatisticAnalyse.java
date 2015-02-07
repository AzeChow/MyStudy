package com.bestway.common.client.transferfactory;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableColumnModel;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import com.bestway.bcus.client.common.CommonDataSource;
import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.footer.JFooterScrollPane;
import com.bestway.client.util.footer.JFooterTable;
import com.bestway.client.util.footer.TableFooterType;
import com.bestway.client.util.groupableheader.ColumnGroup;
import com.bestway.client.util.groupableheader.GroupableHeaderTable;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.transferfactory.action.TransferFactoryManageAction;
import com.bestway.common.transferfactory.entity.TempEnvelopComplex;
import com.bestway.common.transferfactory.entity.TransferFactoryBill;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * by 2009-1-10 lm checked
 * 
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public class FmTransferStatisticAnalyse extends JInternalFrameBase {

	private JTabbedPane tabPn = null;

	private JPanel pnTransferImpExpGoodsList = null;

	private JPanel pnTransferImpExpGoodsStatusList = null;

	private JPanel pnTransferImpExpPortList = null;

	private JFooterTable tbList = null;

	private JFooterScrollPane spn = null;

	private JPanel pn1 = null;

	private JPanel pn2 = null;

	private JCalendarComboBox cbbListBeginDate = null;

	private JCalendarComboBox cbbListEndDate = null;

	private JComboBox cbbListBillType = null;

	private JTextField tfListBillCode = null;

	private JButton btnListBillCode = null;

	private JTextField tfListMaterialCode = null;

	private JTextField tfListMaterialName = null;

	private JTextField tfListSeqNum = null;

	private JButton btnListMaterialCode = null;

	private JButton btnListQuery = null;

	private JButton btnListPrint = null;

	private JPanel pn11 = null;

	private JTable tbStatus = null;

	private JScrollPane spn1 = null;

	private JPanel pn3 = null;

	private JPanel pn4 = null;

	private JPanel pn5 = null;

	private JButton btnStatusQuery = null;

	private JButton btnStatusPrint = null;

	private JCalendarComboBox cbbStatusBeginDate = null;

	private JCalendarComboBox cbbStatusEndDate = null;

	private JComboBox cbbStatusImpExp = null;

	private JTextField tfStatusMaterialCode = null;

	private JTextField tfStatusMaterialName = null;

	private JTextField tfStatusMaterialSpec = null;

	private JButton btnStatusMaterialCode = null;

	private JTable tbPort = null;

	private JScrollPane spn2 = null;

	private JPanel pn6 = null;

	private JPanel pn7 = null;

	private JPanel pn8 = null;

	private JButton btnPortQuery = null;

	private JButton btnPortPrint = null;

	private JTextField tfPortSeqNum = null;

	private JTextField tfPortMaterialCode = null;

	private JTextField tfPortMaterialName = null;

	private JButton btnPortEmsNo = null;

	private JCalendarComboBox cbbPortBeginDate = null;

	private JCalendarComboBox cbbPortEndDate = null;

	private JComboBox cbbDeclarationCustomsType = null;

	private JTextField tfDeclarationCustomsCode = null;

	private JButton btnDeclarationCustomsCode = null;

	private JTableListModel listTableModel = null;

	private JTableListModel statusTableModel = null;

	private JTableListModel portTableModel = null;

	private TransferFactoryManageAction transferFactoryManageAction = null;  //  @jve:decl-index=0:

	private JLabel lb7 = null;

	private JComboBox cbb = null;

	private MaterialManageAction materialManageAction = null;

	/**
	 * This method initializes
	 * 
	 */
	public FmTransferStatisticAnalyse() {
		super();
		initialize();
		tbStatus = new GroupableHeaderTable();
		spn1.setViewportView(tbStatus);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("转厂统计分析");
		this.setContentPane(getTabPn());
		this.setSize(725, 513);
		this
				.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
					public void internalFrameOpened(
							javax.swing.event.InternalFrameEvent e) {
						initUIComponents();
						showEmptyData();
					}
				});
		transferFactoryManageAction = (TransferFactoryManageAction) CommonVars
				.getApplicationContext().getBean("transferFactoryManageAction");
		transferFactoryManageAction.checkTransferStatisticAnalyse(new Request(
				CommonVars.getCurrUser()));
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
	}

	/**
	 * This method initializes tabPn
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getTabPn() {
		if (tabPn == null) {
			tabPn = new JTabbedPane();
			tabPn.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
			tabPn.addTab("转厂进出货明细表", null,
					getPnTransferImpExpGoodsList(), null);
			tabPn.addTab("转厂进出货状况表", null,
					getPnTransferImpExpGoodsStatusList(), null);
			tabPn.addTab("结转进出口明细表", null, getPnTransferImpExpPortList(),
					null);
			tabPn
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							showEmptyData();
						}
					});
		}
		return tabPn;
	}

	/**
	 * This method initializes pnTransferImpExpGoodsList
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnTransferImpExpGoodsList() {
		if (pnTransferImpExpGoodsList == null) {
			java.awt.GridBagConstraints gridBagConstraints22 = new GridBagConstraints();
			java.awt.GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
			java.awt.GridBagConstraints gridBagConstraints20 = new GridBagConstraints();
			java.awt.GridBagConstraints gridBagConstraints19 = new GridBagConstraints();
			java.awt.GridBagConstraints gridBagConstraints18 = new GridBagConstraints();
			java.awt.GridBagConstraints gridBagConstraints17 = new GridBagConstraints();
			pnTransferImpExpGoodsList = new JPanel();
			pnTransferImpExpGoodsList.setLayout(new GridBagLayout());
			gridBagConstraints17.gridx = 0;
			gridBagConstraints17.gridy = 0;
			gridBagConstraints17.gridwidth = 3;
			gridBagConstraints17.weightx = 1.0;
			gridBagConstraints17.weighty = 1.0;
			gridBagConstraints17.fill = java.awt.GridBagConstraints.BOTH;
			gridBagConstraints17.ipadx = 178;
			gridBagConstraints17.ipady = -302;
			gridBagConstraints17.insets = new java.awt.Insets(1, 0, 1, 0);
			gridBagConstraints18.gridx = 0;
			gridBagConstraints18.gridy = 1;
			gridBagConstraints18.gridheight = 2;
			gridBagConstraints18.ipadx = 335;
			gridBagConstraints18.ipady = 54;
			gridBagConstraints18.insets = new java.awt.Insets(1, 0, 11, 0);
			gridBagConstraints19.gridx = 0;
			gridBagConstraints19.gridy = 2;
			gridBagConstraints19.gridheight = 2;
			gridBagConstraints19.ipadx = 335;
			gridBagConstraints19.ipady = 54;
			gridBagConstraints19.insets = new java.awt.Insets(9, 0, 3, 0);
			gridBagConstraints20.gridx = 1;
			gridBagConstraints20.gridy = 1;
			gridBagConstraints20.gridheight = 3;
			gridBagConstraints20.ipadx = 223;
			gridBagConstraints20.ipady = 108;
			gridBagConstraints20.insets = new java.awt.Insets(1, 1, 3, 1);
			gridBagConstraints21.gridx = 2;
			gridBagConstraints21.gridy = 1;
			gridBagConstraints21.ipadx = 2;
			gridBagConstraints21.ipady = -4;
			gridBagConstraints21.insets = new java.awt.Insets(14, 2, 8, 5);
			gridBagConstraints22.gridx = 2;
			gridBagConstraints22.gridy = 3;
			gridBagConstraints22.ipadx = 3;
			gridBagConstraints22.ipady = -6;
			gridBagConstraints22.insets = new java.awt.Insets(11, 2, 13, 4);
			pnTransferImpExpGoodsList.add(getSpn(),
					gridBagConstraints17);
			pnTransferImpExpGoodsList.add(getPn1(), gridBagConstraints18);
			pnTransferImpExpGoodsList.add(getPn11(), gridBagConstraints19);
			pnTransferImpExpGoodsList.add(getPn2(), gridBagConstraints20);
			pnTransferImpExpGoodsList.add(getBtnListQuery(),
					gridBagConstraints21);
			pnTransferImpExpGoodsList.add(getBtnListPrint(),
					gridBagConstraints22);
		}
		return pnTransferImpExpGoodsList;
	}

	/**
	 * This method initializes pnTransferImpExpGoodsStatusList
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnTransferImpExpGoodsStatusList() {
		if (pnTransferImpExpGoodsStatusList == null) {
			java.awt.GridBagConstraints gridBagConstraints24 = new GridBagConstraints();
			java.awt.GridBagConstraints gridBagConstraints23 = new GridBagConstraints();
			java.awt.GridBagConstraints gridBagConstraints221 = new GridBagConstraints();
			java.awt.GridBagConstraints gridBagConstraints211 = new GridBagConstraints();
			java.awt.GridBagConstraints gridBagConstraints201 = new GridBagConstraints();
			java.awt.GridBagConstraints gridBagConstraints191 = new GridBagConstraints();
			pnTransferImpExpGoodsStatusList = new JPanel();
			pnTransferImpExpGoodsStatusList.setLayout(new GridBagLayout());
			gridBagConstraints191.gridx = 0;
			gridBagConstraints191.gridy = 0;
			gridBagConstraints191.gridwidth = 3;
			gridBagConstraints191.weightx = 1.0;
			gridBagConstraints191.weighty = 1.0;
			gridBagConstraints191.fill = java.awt.GridBagConstraints.BOTH;
			gridBagConstraints191.ipadx = 175;
			gridBagConstraints191.ipady = -301;
			gridBagConstraints191.insets = new java.awt.Insets(1, 1, 0, 1);
			gridBagConstraints201.gridx = 0;
			gridBagConstraints201.gridy = 1;
			gridBagConstraints201.ipadx = 316;
			gridBagConstraints201.ipady = 54;
			gridBagConstraints201.insets = new java.awt.Insets(0, 1, 0, 0);
			gridBagConstraints211.gridx = 0;
			gridBagConstraints211.gridy = 2;
			gridBagConstraints211.ipadx = 316;
			gridBagConstraints211.ipady = 50;
			gridBagConstraints211.insets = new java.awt.Insets(0, 1, 7, 0);
			gridBagConstraints221.gridx = 1;
			gridBagConstraints221.gridy = 1;
			gridBagConstraints221.gridheight = 2;
			gridBagConstraints221.ipadx = 230;
			gridBagConstraints221.ipady = 105;
			gridBagConstraints221.insets = new java.awt.Insets(0, 0, 7, 4);
			gridBagConstraints23.gridx = 2;
			gridBagConstraints23.gridy = 1;
			gridBagConstraints23.ipadx = 3;
			gridBagConstraints23.ipady = -6;
			gridBagConstraints23.insets = new java.awt.Insets(14, 5, 19, 9);
			gridBagConstraints24.gridx = 2;
			gridBagConstraints24.gridy = 2;
			gridBagConstraints24.ipadx = 5;
			gridBagConstraints24.ipady = -5;
			gridBagConstraints24.insets = new java.awt.Insets(10, 5, 25, 7);
			pnTransferImpExpGoodsStatusList.add(getSpn1(),
					gridBagConstraints191);
			pnTransferImpExpGoodsStatusList.add(getPn3(),
					gridBagConstraints201);
			pnTransferImpExpGoodsStatusList.add(getPn4(),
					gridBagConstraints211);
			pnTransferImpExpGoodsStatusList.add(getPn5(),
					gridBagConstraints221);
			pnTransferImpExpGoodsStatusList.add(getBtnStatusQuery(),
					gridBagConstraints23);
			pnTransferImpExpGoodsStatusList.add(getBtnStatusPrint(),
					gridBagConstraints24);
		}
		return pnTransferImpExpGoodsStatusList;
	}

	/**
	 * This method initializes pnTransferImpExpPortList
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnTransferImpExpPortList() {
		if (pnTransferImpExpPortList == null) {
			java.awt.GridBagConstraints gridBagConstraints181 = new GridBagConstraints();
			java.awt.GridBagConstraints gridBagConstraints171 = new GridBagConstraints();
			java.awt.GridBagConstraints gridBagConstraints16 = new GridBagConstraints();
			java.awt.GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
			java.awt.GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
			java.awt.GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
			pnTransferImpExpPortList = new JPanel();
			pnTransferImpExpPortList.setLayout(new GridBagLayout());
			gridBagConstraints13.gridx = 0;
			gridBagConstraints13.gridy = 0;
			gridBagConstraints13.gridwidth = 3;
			gridBagConstraints13.weightx = 1.0;
			gridBagConstraints13.weighty = 1.0;
			gridBagConstraints13.fill = java.awt.GridBagConstraints.BOTH;
			gridBagConstraints13.ipadx = 176;
			gridBagConstraints13.ipady = -300;
			gridBagConstraints13.insets = new java.awt.Insets(0, 1, 0, 0);
			gridBagConstraints14.gridx = 0;
			gridBagConstraints14.gridy = 1;
			gridBagConstraints14.gridheight = 2;
			gridBagConstraints14.ipadx = 315;
			gridBagConstraints14.ipady = 53;
			gridBagConstraints14.insets = new java.awt.Insets(1, 0, 8, 1);
			gridBagConstraints15.gridx = 0;
			gridBagConstraints15.gridy = 2;
			gridBagConstraints15.gridheight = 2;
			gridBagConstraints15.ipadx = 315;
			gridBagConstraints15.ipady = 56;
			gridBagConstraints15.insets = new java.awt.Insets(6, 0, 5, 1);
			gridBagConstraints16.gridx = 1;
			gridBagConstraints16.gridy = 1;
			gridBagConstraints16.gridheight = 3;
			gridBagConstraints16.ipadx = 230;
			gridBagConstraints16.ipady = 107;
			gridBagConstraints16.insets = new java.awt.Insets(1, 1, 4, 2);
			gridBagConstraints171.gridx = 2;
			gridBagConstraints171.gridy = 1;
			gridBagConstraints171.ipadx = 13;
			gridBagConstraints171.ipady = -2;
			gridBagConstraints171.insets = new java.awt.Insets(13, 2, 6, 4);
			gridBagConstraints181.gridx = 2;
			gridBagConstraints181.gridy = 3;
			gridBagConstraints181.ipadx = 13;
			gridBagConstraints181.ipady = -2;
			gridBagConstraints181.insets = new java.awt.Insets(8, 2, 16, 4);
			pnTransferImpExpPortList.add(getSpn2(),
					gridBagConstraints13);
			pnTransferImpExpPortList.add(getPn6(), gridBagConstraints14);
			pnTransferImpExpPortList.add(getPn7(), gridBagConstraints15);
			pnTransferImpExpPortList.add(getPn8(), gridBagConstraints16);
			pnTransferImpExpPortList.add(getBtnPortQuery(),
					gridBagConstraints171);
			pnTransferImpExpPortList.add(getBtnPortPrint(),
					gridBagConstraints181);
		}
		return pnTransferImpExpPortList;
	}

	/**
	 * This method initializes tbList
	 * 
	 * @return javax.swing.JTable
	 */
	private JFooterTable getTbList() {
		if (tbList == null) {
			tbList = new JFooterTable();
		}
		return tbList;
	}

	/**
	 * This method initializes spn
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JFooterScrollPane getSpn() {
		if (spn == null) {
			spn = new JFooterScrollPane();
			spn.setViewportView(getTbList());
		}
		return spn;
	}

	/**
	 * This method initializes pn1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPn1() {
		if (pn1 == null) {
			pn1 = new JPanel();
			javax.swing.JLabel jLabel = new JLabel();
			javax.swing.JLabel jLabel1 = new JLabel();
			pn1.setLayout(null);
			pn1.setBorder(javax.swing.BorderFactory.createTitledBorder(null,
					"日期条件",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			jLabel.setBounds(12, 23, 60, 17);
			jLabel.setText("起始日期");
			jLabel1.setBounds(163, 20, 68, 21);
			jLabel1.setText("截止日期");
			pn1.add(jLabel, null);
			pn1.add(getCbbListBeginDate(), null);
			pn1.add(jLabel1, null);
			pn1.add(getCbbListEndDate(), null);
		}
		return pn1;
	}

	/**
	 * This method initializes pn2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPn2() {
		if (pn2 == null) {
			pn2 = new JPanel();
			javax.swing.JLabel jLabel4 = new JLabel();
			javax.swing.JLabel jLabel5 = new JLabel();
			javax.swing.JLabel jLabel6 = new JLabel();
			pn2.setLayout(null);
			pn2.setBorder(javax.swing.BorderFactory.createTitledBorder(
					null, "商品条件",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			jLabel4.setBounds(12, 21, 66, 16);
			jLabel4.setText("商品编码");
			jLabel5.setBounds(12, 52, 66, 16);
			jLabel5.setText("商品名称");
			jLabel6.setBounds(12, 80, 66, 16);
			jLabel6.setText("备案序号");
			pn2.add(jLabel4, null);
			pn2.add(jLabel5, null);
			pn2.add(jLabel6, null);
			pn2.add(getTfListMaterialCode(), null);
			pn2.add(getTfListMaterialName(), null);
			pn2.add(getTfListSeqNum(), null);
			pn2.add(getBtnListMaterialCode(), null);
		}
		return pn2;
	}

	/**
	 * This method initializes cbListBeginDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbListBeginDate() {
		if (cbbListBeginDate == null) {
			cbbListBeginDate = new JCalendarComboBox();
			cbbListBeginDate.setBounds(75, 20, 86, 22);
		}
		return cbbListBeginDate;
	}

	/**
	 * This method initializes cbbListEndDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbListEndDate() {
		if (cbbListEndDate == null) {
			cbbListEndDate = new JCalendarComboBox();
			cbbListEndDate.setBounds(239, 20, 85, 22);
		}
		return cbbListEndDate;
	}

	/**
	 * This method initializes cbbListBillType
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbListBillType() {
		if (cbbListBillType == null) {
			cbbListBillType = new JComboBox();
			cbbListBillType.setBounds(66, 22, 84, 21);
		}
		return cbbListBillType;
	}

	/**
	 * This method initializes tfListBillCode
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfListBillCode() {
		if (tfListBillCode == null) {
			tfListBillCode = new JTextField();
			tfListBillCode.setBounds(230, 22, 77, 21);
		}
		return tfListBillCode;
	}

	/**
	 * This method initializes btnListBillCode
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnListBillCode() {
		if (btnListBillCode == null) {
			btnListBillCode = new JButton();
			btnListBillCode.setText("...");
			btnListBillCode.setBounds(308, 21, 24, 21);
			btnListBillCode
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							TransferFactoryBill transferFactoryBill = (TransferFactoryBill) CommonQuery
									.getInstance()
									.getTransferFactoryBill(
											cbbListBillType.getSelectedIndex() == 0);
							if (transferFactoryBill != null) {
								tfListBillCode
										.setText(transferFactoryBill
												.getTransferFactoryBillNo() == null ? ""
												: transferFactoryBill
														.getTransferFactoryBillNo()
														.toString());
							}
						}
					});
		}
		return btnListBillCode;
	}

	/**
	 * This method initializes tfListMaterialCode
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfListMaterialCode() {
		if (tfListMaterialCode == null) {
			tfListMaterialCode = new JTextField();
			tfListMaterialCode.setBounds(78, 20, 118, 20);
		}
		return tfListMaterialCode;
	}

	/**
	 * This method initializes tfListMaterialName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfListMaterialName() {
		if (tfListMaterialName == null) {
			tfListMaterialName = new JTextField();
			tfListMaterialName.setBounds(78, 51, 118, 20);
		}
		return tfListMaterialName;
	}

	/**
	 * This method initializes tfListEmsNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfListSeqNum() {
		if (tfListSeqNum == null) {
			tfListSeqNum = new JTextField();
			tfListSeqNum.setBounds(78, 80, 118, 20);
		}
		return tfListSeqNum;
	}

	/**
	 * This method initializes btnListMaterialCode
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnListMaterialCode() {
		if (btnListMaterialCode == null) {
			btnListMaterialCode = new JButton();
			btnListMaterialCode.setBounds(195, 20, 23, 19);
			btnListMaterialCode.setText("...");
			btnListMaterialCode
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							boolean isImport = (cbbListBillType
									.getSelectedIndex() == 0 ? true : false);
							TempEnvelopComplex temp = (TempEnvelopComplex) TransferFactoryQuery
									.getInstance().findCustomsEnvelopComplex(
											isImport, true);
							if (temp != null) {
								tfListSeqNum
										.setText(temp.getSeqNum() == null ? ""
												: temp.getSeqNum().toString());
								tfListMaterialCode.setText(temp.getCode());
								tfListMaterialName.setText(temp.getName());
							}
						}
					});
		}
		return btnListMaterialCode;
	}

	/**
	 * This method initializes btnListQuery
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnListQuery() {
		if (btnListQuery == null) {
			btnListQuery = new JButton();
			btnListQuery.setText("查询");
			btnListQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Date beginDate = cbbListBeginDate.getDate();
					Date endDate = cbbListEndDate.getDate();
					if (beginDate != null && endDate != null
							&& endDate.before(beginDate)) {
						JOptionPane.showMessageDialog(
								FmTransferStatisticAnalyse.this,
								"截止时间小于开始时间，无法查询", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					Integer billType = new Integer(
							cbbListBillType.getSelectedIndex() == 0 ? ImpExpType.TRANSFER_FACTORY_IMPORT
									: ImpExpType.TRANSFER_FACTORY_EXPORT);
					String billCode = tfListBillCode.getText();
					String materielCode = tfListMaterialCode.getText();
					String materielName = tfListMaterialName.getText();
					String seqNum = tfListSeqNum.getText();
					String companyName = "";// tfListCompanyName.getName();
					List list = transferFactoryManageAction
							.findTransferFactoryImpExpGoodsList(new Request(
									CommonVars.getCurrUser()), beginDate,
									endDate, billType, billCode, materielCode,
									materielName, seqNum, companyName);
					if (list.isEmpty()) {
						JOptionPane.showMessageDialog(
								FmTransferStatisticAnalyse.this, "查到0笔数据",
								"提示", JOptionPane.INFORMATION_MESSAGE);

					}
					listTableModel = new JTableListModel(tbList, list,
							new JTableListModelAdapter() {
								public List InitColumns() {
									List list = new Vector();
									list.add(addColumn("日期", "billDate", 100));
									list
											.add(addColumn("单据类型", "billType",
													100));
									list.add(addColumn("结转单据号码", "billCode",
											100));
									list.add(addColumn("关封号", "envelopNo", 50));
									list.add(addColumn("帐册序号", "emsNo", 50));
									list.add(addColumn("商品编码", "materielCode",
											100));
									list.add(addColumn("名称", "materielName",
											100));
									list.add(addColumn("单位", "unit",
											100));
									list.add(addColumn("数量",
											"transferQuantity", 100));
									list.add(addColumn("供应商/客户名称", "scmCoc",
											150));
									return list;
								}
							});
					listTableModel.clearFooterTypeInfo();
					listTableModel.addFooterTypeInfo(new TableFooterType(0,
							TableFooterType.CONSTANT, "合计"));
					listTableModel.addFooterTypeInfo(new TableFooterType(9,
							TableFooterType.SUM, ""));
				}
				
			});
		}
		return btnListQuery;
	}

	/**
	 * This method initializes btnListPrint
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnListPrint() {
		if (btnListPrint == null) {
			btnListPrint = new JButton();
			btnListPrint.setText("打印");
			btnListPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (listTableModel == null
							|| listTableModel.getList().size() < 1) {
						JOptionPane.showMessageDialog(
								FmTransferStatisticAnalyse.this, "没有数据可打印",
								"提示", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					try {
						Date Begindate = cbbListBeginDate.getDate();
						Date Enddate = cbbListEndDate.getDate();
						String TextBegindate = "起始日期";
						String TextEnddate = "截止日期";
						String Begindate1=CommonVars.dateToString(Begindate);
						String Enddate1= CommonVars.dateToString(Enddate);
						if (Begindate == null) {
							Begindate1="";
							TextBegindate = "";
						}
						if (Enddate == null) {
							 Enddate1="";
							TextEnddate = "";
						}
						CustomReportDataSource ds = new CustomReportDataSource(
								listTableModel.getList());
						Map<String, Object> parameters = new HashMap<String, Object>();
						parameters.put("Begindate", Begindate1);
						parameters.put("Enddate", Enddate1);
						parameters.put("TextBegindate", TextBegindate);
						parameters.put("TextEnddate", TextEnddate);
						
						InputStream masterReportStream = FmTransferStatisticAnalyse.class
								.getResourceAsStream("report/TransferImpExpGoodsListReport.jasper");
						JasperPrint jasperPrint = JasperFillManager.fillReport(
								masterReportStream, parameters, ds);
						DgReportViewer viewer = new DgReportViewer(jasperPrint);
						viewer.setVisible(true);
					} catch (JRException e1) {
						e1.printStackTrace();
					}
				}
			});
		}
		return btnListPrint;
	}

	/**
	 * This method initializes pn11
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPn11() {
		if (pn11 == null) {
			pn11 = new JPanel();
			javax.swing.JLabel jLabel2 = new JLabel();
			javax.swing.JLabel jLabel3 = new JLabel();
			pn11.setLayout(null);
			pn11.setBorder(javax.swing.BorderFactory.createTitledBorder(
					null, "单据条件",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			jLabel2.setBounds(13, 22, 53, 20);
			jLabel2.setText("单据类型");
			jLabel3.setBounds(149, 22, 79, 20);
			jLabel3.setText("结转单据号码");
			pn11.add(getBtnListBillCode(), null);
			pn11.add(getCbbListBillType(), null);
			pn11.add(getTfListBillCode(), null);
			pn11.add(jLabel2, null);
			pn11.add(jLabel3, null);
		}
		return pn11;
	}

	/**
	 * This method initializes tbStatus
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbStatus() {
		if (tbStatus == null) {
			tbStatus = new JTable();
		}
		return tbStatus;
	}

	/**
	 * This method initializes spn1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getSpn1() {
		if (spn1 == null) {
			spn1 = new JScrollPane();
			spn1.setViewportView(getTbStatus());
		}
		return spn1;
	}

	/**
	 * This method initializes pn3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPn3() {
		if (pn3 == null) {
			pn3 = new JPanel();
			javax.swing.JLabel jLabel8 = new JLabel();
			javax.swing.JLabel jLabel9 = new JLabel();
			pn3.setLayout(null);
			pn3.setBorder(javax.swing.BorderFactory.createTitledBorder(
					null, "日期条件",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			jLabel8.setBounds(9, 22, 60, 18);
			jLabel8.setText("起始日期");
			jLabel9.setBounds(159, 23, 58, 17);
			jLabel9.setText("截止日期");
			pn3.add(jLabel8, null);
			pn3.add(jLabel9, null);
			pn3.add(getCbbStatusBeginDate(), null);
			pn3.add(getCbbStatusEndDate(), null);
		}
		return pn3;
	}

	/**
	 * This method initializes pn4
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPn4() {
		if (pn4 == null) {
			lb7 = new JLabel();
			lb7.setBounds(new java.awt.Rectangle(149, 19, 35, 16));
			lb7.setText("客户");
			pn4 = new JPanel();
			javax.swing.JLabel jLabel10 = new JLabel();
			pn4.setLayout(null);
			pn4.setBorder(javax.swing.BorderFactory.createTitledBorder(
					null, "单据条件",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			jLabel10.setBounds(6, 19, 63, 16);
			jLabel10.setText("单据类型");
			pn4.add(jLabel10, null);
			pn4.add(getCbbStatusImpExp(), null);
			pn4.add(lb7, null);
			pn4.add(getCbb(), null);
		}
		return pn4;
	}

	/**
	 * This method initializes pn5
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPn5() {
		if (pn5 == null) {
			pn5 = new JPanel();
			javax.swing.JLabel jLabel11 = new JLabel();
			javax.swing.JLabel jLabel12 = new JLabel();
			javax.swing.JLabel jLabel13 = new JLabel();
			pn5.setLayout(null);
			pn5.setBorder(javax.swing.BorderFactory.createTitledBorder(
					null, "商品条件",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			jLabel11.setBounds(12, 23, 69, 17);
			jLabel11.setText("商品编码");
			jLabel12.setBounds(12, 50, 69, 17);
			jLabel12.setText("商品名称");
			jLabel13.setBounds(12, 79, 69, 17);
			jLabel13.setText("型号规格");
			pn5.add(jLabel11, null);
			pn5.add(jLabel12, null);
			pn5.add(jLabel13, null);
			pn5.add(getTfStatusMaterialCode(), null);
			pn5.add(getTfStatusMaterialName(), null);
			pn5.add(getTfStatusMaterialSpec(), null);
			pn5.add(getBtnStatusMaterialCode(), null);
		}
		return pn5;
	}

	/**
	 * This method initializes btnStatusQuery
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnStatusQuery() {
		if (btnStatusQuery == null) {
			btnStatusQuery = new JButton();
			btnStatusQuery.setText("查询");
			btnStatusQuery
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							Date beginDate = cbbStatusBeginDate.getDate();
							Date endDate = cbbStatusEndDate.getDate();
							if (beginDate != null && endDate != null
									&& endDate.before(beginDate)) {
								JOptionPane.showMessageDialog(
										FmTransferStatisticAnalyse.this,
										"截止时间小于开始时间，无法查询", "提示",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							Integer billType = new Integer(
									cbbStatusImpExp.getSelectedIndex() == 0 ? ImpExpType.TRANSFER_FACTORY_IMPORT
											: ImpExpType.TRANSFER_FACTORY_EXPORT);
							// String billCode = tfListBillCode.getText();
							String materielCode = tfStatusMaterialCode
									.getText();
							String materielName = tfStatusMaterialName
									.getText();
							String materielSpec = tfStatusMaterialSpec
									.getText();
							// ---------------------------------------------------------
							String scmCocCode = null;
							ScmCoc co = (ScmCoc) cbb.getSelectedItem();
							if (co != null) {
								scmCocCode = co.getCode();
							}

							String companyName = "";// tfListCompanyName.getName();
							List list = transferFactoryManageAction
									.findStatisticTotalTransferStatusQuantity(
											new Request(CommonVars
													.getCurrUser()), beginDate,
											endDate, billType, "",
											materielCode, materielName,
											materielSpec, companyName,
											scmCocCode);
							if (list.isEmpty()) {
								JOptionPane.showMessageDialog(
										FmTransferStatisticAnalyse.this,
										"查到0笔数据", "提示",
										JOptionPane.INFORMATION_MESSAGE);

							}
							statusTableModel = new JTableListModel(
									(GroupableHeaderTable) tbStatus, list,
									new JTableListModelAdapter() {
										public List InitColumns() {
											List list = new Vector();
											list.add(addColumn("帐册/手册号",
													"emsNo", 120));
											list.add(addColumn("帐册/手册序号",
													"seqNum", 80));
											list.add(addColumn("客户名称",
													"scmcocName", 120));
											list.add(addColumn("商品编码",
													"materielCode", 80));
											list.add(addColumn("名称",
													"materielName", 100));
											list
													.add(addColumn("单位",
															"unit", 50));
											list.add(addColumn("关封号",
													"envlopNo", 80));

											list.add(addColumn("进出货累计",
													"preImpExpGoodsQuantity",
													100));
											list
													.add(addColumn(
															"转厂累计",
															"preTransferQuantity",
															100));
											list.add(addColumn("未转厂累计",
													"preNoTransferQuantity",
													100));
											list.add(addColumn("进出货累计",
													"nowImpExpGoodsQuantity",
													100));
											list
													.add(addColumn(
															"转厂累计",
															"nowTransferQuantity",
															100));
											list.add(addColumn("未转厂累计",
													"nowNoTransferQuantity",
													100));
											list.add(addColumn("未转厂累计",
													"totalNoTransferQuantity",
													100));
											return list;
										}
									});

							TableColumnModel cm = tbStatus.getColumnModel();

							ColumnGroup gPrevious = new ColumnGroup("前期");

							gPrevious.add(cm.getColumn(8));
							gPrevious.add(cm.getColumn(9));
							gPrevious.add(cm.getColumn(10));
							ColumnGroup gNow = new ColumnGroup("本期");
							gNow.add(cm.getColumn(11));
							gNow.add(cm.getColumn(12));
							gNow.add(cm.getColumn(13));
							ColumnGroup gTotal = new ColumnGroup("总计");
							gTotal.add(cm.getColumn(14));

							GroupableTableHeader header = (GroupableTableHeader) tbStatus
									.getTableHeader();
							header.addColumnGroup(gPrevious);
							header.addColumnGroup(gNow);
							header.addColumnGroup(gTotal);
						}
					});
		}
		return btnStatusQuery;
	}

	/**
	 * This method initializes btnStatusPrint
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnStatusPrint() {
		if (btnStatusPrint == null) {
			btnStatusPrint = new JButton();
			btnStatusPrint.setText("打印");
			btnStatusPrint
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (statusTableModel == null
									|| statusTableModel.getList().size() < 1) {
								return;
							}
							Date Begindate = cbbStatusBeginDate.getDate();
							Date Enddate = cbbStatusEndDate.getDate();
							String TextBegindate = "起始日期";
							String TextEnddate = "截止日期";
							String Begindate1=CommonVars.dateToString(Begindate);
							String Enddate1= CommonVars.dateToString(Enddate);
							if (Begindate == null) {
								Begindate1="";
								TextBegindate = "";
							}
							if (Enddate == null) {
								 Enddate1="";
								TextEnddate = "";
							}
							CommonDataSource imgExgDS = new CommonDataSource(
									statusTableModel.getList());

							List company = new Vector(); // 只有一条记录
							company.add(CommonVars.getCurrUser().getCompany());
							CommonDataSource companyDS = new CommonDataSource(
									company);

							InputStream masterReportStream = FmTransferStatisticAnalyse.class
									.getResourceAsStream("report/TransferImpExpState.jasper");
							InputStream detailReportStream = FmTransferStatisticAnalyse.class
									.getResourceAsStream("report/TransferImpExpStateSub.jasper");
							try {
								JasperReport detailReport = (JasperReport) JRLoader
										.loadObject(detailReportStream);
								Map<String, Object> parameters = new HashMap<String, Object>();
								parameters.put("printDate", CommonVars
										.nowToDate());
								parameters.put("Begindate", Begindate1);
								parameters.put("Enddate", Enddate1);
								parameters.put("TextBegindate", TextBegindate);
								parameters.put("TextEnddate", TextEnddate);
								parameters.put("companyName", CommonVars
										.getCurrUser().getCompany().getName());
								parameters.put("imgExgDS", imgExgDS);// 子数据源
								parameters.put("detailReport", detailReport);// 子报表
								JasperPrint jasperPrint;
								jasperPrint = JasperFillManager.fillReport(
										masterReportStream, parameters,
										companyDS);
								DgReportViewer viewer = new DgReportViewer(
										jasperPrint);
								viewer.setVisible(true);
							} catch (JRException e1) {
								e1.printStackTrace();
							}

							/*
							 * try { CustomReportDataSource ds = new
							 * CustomReportDataSource(
							 * statusTableModel.getList()); InputStream
							 * masterReportStream =
							 * FmTransferStatisticAnalyse.class
							 * .getResourceAsStream("report/TransferImpExpGoodsStatusReport.jasper");
							 * JasperPrint jasperPrint = JasperFillManager
							 * .fillReport(masterReportStream, null, ds);
							 * DgReportViewer viewer = new DgReportViewer(
							 * jasperPrint); viewer.setVisible(true); } catch
							 * (JRException e1) { e1.printStackTrace(); }
							 */
						}
					});
		}
		return btnStatusPrint;
	}

	/**
	 * This method initializes cbbStatusBeginDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbStatusBeginDate() {
		if (cbbStatusBeginDate == null) {
			cbbStatusBeginDate = new JCalendarComboBox();
			cbbStatusBeginDate.setBounds(71, 20, 88, 23);
		}
		return cbbStatusBeginDate;
	}

	/**
	 * This method initializes cbbStatusEndDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbStatusEndDate() {
		if (cbbStatusEndDate == null) {
			cbbStatusEndDate = new JCalendarComboBox();
			cbbStatusEndDate.setBounds(220, 20, 89, 23);
		}
		return cbbStatusEndDate;
	}

	/**
	 * This method initializes cbbStatusImpExp
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbStatusImpExp() {
		if (cbbStatusImpExp == null) {
			cbbStatusImpExp = new JComboBox();
			cbbStatusImpExp.setBounds(62, 17, 83, 22);
		}
		return cbbStatusImpExp;
	}

	/**
	 * This method initializes tfStatusMaterialCode
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfStatusMaterialCode() {
		if (tfStatusMaterialCode == null) {
			tfStatusMaterialCode = new JTextField();
			tfStatusMaterialCode.setBounds(85, 19, 101, 21);
		}
		return tfStatusMaterialCode;
	}

	/**
	 * This method initializes tfStatusMaterialName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfStatusMaterialName() {
		if (tfStatusMaterialName == null) {
			tfStatusMaterialName = new JTextField();
			tfStatusMaterialName.setBounds(85, 48, 101, 21);
		}
		return tfStatusMaterialName;
	}

	/**
	 * This method initializes tfStatusMaterialSpec
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfStatusMaterialSpec() {
		if (tfStatusMaterialSpec == null) {
			tfStatusMaterialSpec = new JTextField();
			tfStatusMaterialSpec.setBounds(85, 77, 101, 21);
		}
		return tfStatusMaterialSpec;
	}

	/**
	 * This method initializes btnStatusMaterialCode
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnStatusMaterialCode() {
		if (btnStatusMaterialCode == null) {
			btnStatusMaterialCode = new JButton();
			btnStatusMaterialCode.setBounds(184, 19, 22, 20);
			btnStatusMaterialCode.setText("...");
			btnStatusMaterialCode
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							// Materiel materiel = (Materiel) CommonQuery
							// .getInstance().getMateriel();
							// if (materiel != null) {
							// tfStatusMaterialCode
							// .setText(materiel.getPtNo());
							// tfStatusMaterialName.setText(materiel
							// .getFactoryName());
							// }
							boolean isImport = (cbbStatusImpExp
									.getSelectedIndex() == 0 ? true : false);
							TempEnvelopComplex temp = (TempEnvelopComplex) TransferFactoryQuery
									.getInstance().findCustomsEnvelopComplex(
											isImport, false);
							if (temp != null) {
								tfStatusMaterialCode.setText(temp.getCode());
								tfStatusMaterialName.setText(temp.getName());
							}
						}
					});
		}
		return btnStatusMaterialCode;
	}

	/**
	 * This method initializes tbPort
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbPort() {
		if (tbPort == null) {
			tbPort = new JTable();
		}
		return tbPort;
	}

	/**
	 * This method initializes spn2
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getSpn2() {
		if (spn2 == null) {
			spn2 = new JScrollPane();
			spn2.setViewportView(getTbPort());
		}
		return spn2;
	}

	/**
	 * This method initializes pn6
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPn6() {
		if (pn6 == null) {
			pn6 = new JPanel();
			javax.swing.JLabel jLabel15 = new JLabel();
			javax.swing.JLabel jLabel16 = new JLabel();
			pn6.setLayout(null);
			pn6.setBorder(javax.swing.BorderFactory.createTitledBorder(
					null, "日期条件",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			jLabel15.setBounds(7, 24, 58, 15);
			jLabel15.setText("起始日期");
			jLabel16.setBounds(156, 23, 55, 17);
			jLabel16.setText("截止日期");
			pn6.add(jLabel15, null);
			pn6.add(jLabel16, null);
			pn6.add(getCbbPortBeginDate(), null);
			pn6.add(getCbbPortEndDate(), null);
		}
		return pn6;
	}

	/**
	 * This method initializes pn7
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPn7() {
		if (pn7 == null) {
			pn7 = new JPanel();
			javax.swing.JLabel jLabel17 = new JLabel();
			javax.swing.JLabel jLabel18 = new JLabel();
			pn7.setLayout(null);
			pn7.setBorder(javax.swing.BorderFactory.createTitledBorder(
					null, "单据条件",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			jLabel17.setBounds(6, 26, 67, 17);
			jLabel17.setText("报关单类型");
			jLabel18.setBounds(159, 22, 54, 20);
			jLabel18.setText("报关单号");
			pn7.add(jLabel17, null);
			pn7.add(jLabel18, null);
			pn7.add(getCbbDeclarationCustomsType(), null);
			pn7.add(getTfDeclarationCustomsCode(), null);
			pn7.add(getBtnDeclarationCustomsCode(), null);
		}
		return pn7;
	}

	/**
	 * This method initializes pn8
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPn8() {
		if (pn8 == null) {
			pn8 = new JPanel();
			javax.swing.JLabel jLabel19 = new JLabel();
			javax.swing.JLabel jLabel20 = new JLabel();
			javax.swing.JLabel jLabel21 = new JLabel();
			pn8.setLayout(null);
			pn8.setBorder(javax.swing.BorderFactory.createTitledBorder(
					null, "商品条件",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			jLabel19.setBounds(12, 19, 86, 17);
			jLabel19.setText("备案序号");
			jLabel20.setBounds(12, 49, 86, 18);
			jLabel20.setText("商品编码");
			jLabel21.setBounds(12, 79, 86, 18);
			jLabel21.setText("商品名称");
			pn8.add(jLabel19, null);
			pn8.add(jLabel20, null);
			pn8.add(jLabel21, null);
			pn8.add(getTfPortSeqNum(), null);
			pn8.add(getTfPortMaterialCode(), null);
			pn8.add(getTfPortMaterialName(), null);
			pn8.add(getBtnPortEmsNo(), null);
		}
		return pn8;
	}

	/**
	 * This method initializes btnPortQuery
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPortQuery() {
		if (btnPortQuery == null) {
			btnPortQuery = new JButton();
			btnPortQuery.setText("查询");
			btnPortQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Date beginDate = cbbPortBeginDate.getDate();
					Date endDate = cbbPortEndDate.getDate();
					if (beginDate != null && endDate != null
							&& endDate.before(beginDate)) {
						JOptionPane.showMessageDialog(
								FmTransferStatisticAnalyse.this,
								"截止时间小于开始时间，无法查询", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					Integer billType = Integer
							.valueOf(((ItemProperty) cbbDeclarationCustomsType
									.getSelectedItem()).getCode());
					String billCode = tfDeclarationCustomsCode.getText();
					String materielCode = tfPortMaterialCode.getText();
					String materielName = tfPortMaterialName.getText();
					String seqNum = tfPortSeqNum.getText().trim();
					String companyName = "";// tfPortCompanyName.getName();
					List list = transferFactoryManageAction
							.findTransferImpExpCustomsList(new Request(
									CommonVars.getCurrUser()), beginDate,
									endDate, billType, billCode, materielCode,
									materielName, seqNum, companyName);
					if (list.isEmpty()) {
						JOptionPane.showMessageDialog(
								FmTransferStatisticAnalyse.this, "查到0笔数据",
								"提示", JOptionPane.INFORMATION_MESSAGE);

					}
					portTableModel = new JTableListModel(tbPort, list,
							new JTableListModelAdapter() {
								public List InitColumns() {
									List list = new Vector();
									list
											.add(addColumn("申报日期", "billDate",
													100));
									list
											.add(addColumn("报关单类型", "billType",
													100));
									list.add(addColumn("报关单流水号", "billCode",
											100));
									list.add(addColumn("报关单号", "customsDeclarationCode", 150));
									list.add(addColumn("帐册序号", "emsNo", 50));
									list.add(addColumn("商品编号", "materielCode",
											100));
									list.add(addColumn("商品名称", "materielName",
											100));
									list.add(addColumn("数量",
											"transferQuantity", 100));
									list.add(addColumn("单位", "unit", 100));
									list.add(addColumn("供应商/客户名称", "scmCoc",
											150));
									return list;
								}
							});
				}
			});
		}
		return btnPortQuery;
	}

	/**
	 * This method initializes btnPortPrint
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPortPrint() {
		if (btnPortPrint == null) {
			btnPortPrint = new JButton();
			btnPortPrint.setText("打印");
			btnPortPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (portTableModel == null
							|| portTableModel.getList().size() < 1) {
						return;
					}
					try {
						CustomReportDataSource ds = new CustomReportDataSource(
								portTableModel.getList());
						InputStream masterReportStream = FmTransferStatisticAnalyse.class
								.getResourceAsStream("report/TransferImpExpPortListReport.jasper");
						JasperPrint jasperPrint = JasperFillManager.fillReport(
								masterReportStream, null, ds);
						DgReportViewer viewer = new DgReportViewer(jasperPrint);
						viewer.setVisible(true);
					} catch (JRException e1) {
						e1.printStackTrace();
					}
				}
			});
		}
		return btnPortPrint;
	}

	/**
	 * This method initializes tfPortSeqNum
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfPortSeqNum() {
		if (tfPortSeqNum == null) {
			tfPortSeqNum = new JTextField();
			tfPortSeqNum.setBounds(100, 18, 105, 21);
		}
		return tfPortSeqNum;
	}

	/**
	 * This method initializes tfPortMaterialCode
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfPortMaterialCode() {
		if (tfPortMaterialCode == null) {
			tfPortMaterialCode = new JTextField();
			tfPortMaterialCode.setBounds(100, 47, 105, 21);
		}
		return tfPortMaterialCode;
	}

	/**
	 * This method initializes tfPortMaterialName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfPortMaterialName() {
		if (tfPortMaterialName == null) {
			tfPortMaterialName = new JTextField();
			tfPortMaterialName.setBounds(100, 77, 105, 21);
		}
		return tfPortMaterialName;
	}

	/**
	 * This method initializes btnPortEmsNo
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPortEmsNo() {
		if (btnPortEmsNo == null) {
			btnPortEmsNo = new JButton();
			btnPortEmsNo.setBounds(204, 18, 21, 20);
			btnPortEmsNo.setText("...");
			btnPortEmsNo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// Object obj = CommonQuery.getInstance()
					// .findEmsEdiMergerImgExg();
					// if (obj != null) {
					// if (obj instanceof EmsEdiMergerExgAfter) {
					// EmsEdiMergerExgAfter emsEdiMergerExgAfter =
					// (EmsEdiMergerExgAfter) obj;
					// tfPortEmsNo.setText(emsEdiMergerExgAfter
					// .getSeqNum() == null ? ""
					// : emsEdiMergerExgAfter.getSeqNum()
					// .toString());
					// tfPortMaterialCode.setText(emsEdiMergerExgAfter
					// .getComplex() == null ? ""
					// : emsEdiMergerExgAfter.getComplex()
					// .getCode());
					// tfPortMaterialName.setText(emsEdiMergerExgAfter
					// .getName());
					// } else if (obj instanceof EmsEdiMergerImgAfter) {
					// EmsEdiMergerImgAfter emsEdiMergerImgAfter =
					// (EmsEdiMergerImgAfter) obj;
					// tfPortEmsNo.setText(emsEdiMergerImgAfter
					// .getSeqNum() == null ? ""
					// : emsEdiMergerImgAfter.getSeqNum()
					// .toString());
					// tfPortMaterialCode.setText(emsEdiMergerImgAfter
					// .getComplex() == null ? ""
					// : emsEdiMergerImgAfter.getComplex()
					// .getCode());
					// tfPortMaterialName.setText(emsEdiMergerImgAfter
					// .getName());
					// }
					//
					// }
					boolean isImport = (cbbStatusImpExp.getSelectedIndex() == 0 ? true
							: false);
					TempEnvelopComplex temp = (TempEnvelopComplex) TransferFactoryQuery
							.getInstance().findCustomsEnvelopComplex(isImport,
									true);
					if (temp != null) {
						tfPortSeqNum.setText(temp.getSeqNum() == null ? ""
								: temp.getSeqNum().toString());
						tfPortMaterialCode.setText(temp.getCode());
						tfPortMaterialName.setText(temp.getName());
					}
				}
			});
		}
		return btnPortEmsNo;
	}

	/**
	 * This method initializes cbbPortBeginDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbPortBeginDate() {
		if (cbbPortBeginDate == null) {
			cbbPortBeginDate = new JCalendarComboBox();
			cbbPortBeginDate.setBounds(66, 18, 87, 23);
		}
		return cbbPortBeginDate;
	}

	/**
	 * This method initializes cbbPortEndDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbPortEndDate() {
		if (cbbPortEndDate == null) {
			cbbPortEndDate = new JCalendarComboBox();
			cbbPortEndDate.setBounds(212, 18, 86, 23);
		}
		return cbbPortEndDate;
	}

	/**
	 * This method initializes cbbDeclarationCustomsType
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbDeclarationCustomsType() {
		if (cbbDeclarationCustomsType == null) {
			cbbDeclarationCustomsType = new JComboBox();
			cbbDeclarationCustomsType.setBounds(72, 24, 87, 21);
		}
		return cbbDeclarationCustomsType;
	}

	/**
	 * This method initializes tfDeclarationCustomsCode
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfDeclarationCustomsCode() {
		if (tfDeclarationCustomsCode == null) {
			tfDeclarationCustomsCode = new JTextField();
			tfDeclarationCustomsCode.setBounds(213, 24, 74, 22);
		}
		return tfDeclarationCustomsCode;
	}

	/**
	 * This method initializes btnDeclarationCustomsCode
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDeclarationCustomsCode() {
		if (btnDeclarationCustomsCode == null) {
			btnDeclarationCustomsCode = new JButton();
			btnDeclarationCustomsCode.setBounds(287, 23, 23, 22);
			btnDeclarationCustomsCode.setText("...");
		}
		return btnDeclarationCustomsCode;
	}
	/***
	 * 初始化表
	 */
	private void showEmptyData() {
		switch (tabPn.getSelectedIndex()) {
		case 0:
			if (listTableModel != null) {
				return;
			}
			listTableModel = new JTableListModel(tbList, new ArrayList(),
					new JTableListModelAdapter() {
						public List InitColumns() {
							List list = new Vector();
							list.add(addColumn("日期", "billDate", 100));
							list.add(addColumn("单据类型", "billType", 100));
							list.add(addColumn("单据号码", "billCode", 100));
							list.add(addColumn("帐册序号", "emsNo", 50));
							list.add(addColumn("商品编码", "materielCode", 100));
							list.add(addColumn("名称", "materielName", 100));
							list.add(addColumn("数量", "transferQuantity", 100));
							list.add(addColumn("单位", "unit", 100));
							list.add(addColumn("公司名称", "companyName", 150));
							return list;
						}
					});
			break;
		case 1:
			if (statusTableModel != null) {
				return;
			}// (GroupableHeaderTable)
			statusTableModel = new JTableListModel(tbStatus, new ArrayList(),
					new JTableListModelAdapter() {
						public List InitColumns() {
							List list = new Vector();
							list.add(addColumn("帐册序号", "seqNum", 80));
							list.add(addColumn("客户名称", "scmcocName", 120));
							list.add(addColumn("商品编码", "materielCode", 80));
							list.add(addColumn("名称", "materielName", 100));
							list.add(addColumn("单位", "unit", 50));
							list.add(addColumn("关封号", "envlopNo", 80));
							list.add(addColumn("进出货累计",
									"preImpExpGoodsQuantity", 100));
							list.add(addColumn("转厂累计", "preTransferQuantity",
									100));
							list.add(addColumn("未转厂累计",
									"preNoTransferQuantity", 100));
							list.add(addColumn("进出货累计",
									"nowImpExpGoodsQuantity", 100));
							list.add(addColumn("转厂累计", "nowTransferQuantity",
									100));
							list.add(addColumn("未转厂累计",
									"nowNoTransferQuantity", 100));
							list.add(addColumn("未转厂累计",
									"totalNoTransferQuantity", 100));
							return list;
						}
					});

			TableColumnModel cm = tbStatus.getColumnModel();

			ColumnGroup gPrevious = new ColumnGroup("前期");
			gPrevious.add(cm.getColumn(7));
			gPrevious.add(cm.getColumn(8));
			gPrevious.add(cm.getColumn(9));

			ColumnGroup gNow = new ColumnGroup("本期");
			gNow.add(cm.getColumn(10));
			gNow.add(cm.getColumn(11));
			gNow.add(cm.getColumn(12));

			ColumnGroup gTotal = new ColumnGroup("总计");
			gTotal.add(cm.getColumn(13));

			GroupableTableHeader header = (GroupableTableHeader) tbStatus
					.getTableHeader();
			header.addColumnGroup(gPrevious);
			header.addColumnGroup(gNow);
			header.addColumnGroup(gTotal);
			break;
		case 2:
			if (portTableModel != null) {
				return;
			}
			portTableModel = new JTableListModel(tbPort, new ArrayList(),
					new JTableListModelAdapter() {
						public List InitColumns() {
							List list = new Vector();
							list.add(addColumn("日期", "billDate", 100));
							list.add(addColumn("单据类型", "billType", 100));
							list.add(addColumn("单据号码", "billCode", 100));
							list.add(addColumn("帐册序号", "emsNo", 50));
							list.add(addColumn("商品编码", "materielCode", 100));
							list.add(addColumn("名称", "materielName", 100));
							list.add(addColumn("数量", "transferQuantity", 100));
							list.add(addColumn("公司名称", "companyName", 150));
							return list;
						}
					});
			break;
		default:
		}
	}
	/***
	 * this method is initializes this 
	 */

	private void initUIComponents() {
		this.cbbListBillType.addItem("转厂进口");
		this.cbbListBillType.addItem("转厂出口");
		this.cbbListBillType.setSelectedIndex(0);

		this.cbbStatusImpExp.addItem("进货转厂");
		this.cbbStatusImpExp.addItem("出货转厂");
		this.cbbStatusImpExp.setSelectedIndex(0);

		this.cbbDeclarationCustomsType.addItem(new ItemProperty(String
				.valueOf(ImpExpType.TRANSFER_FACTORY_IMPORT), "料件转厂"));
		this.cbbDeclarationCustomsType.addItem(new ItemProperty(String
				.valueOf(ImpExpType.TRANSFER_FACTORY_EXPORT), "转厂出口"));
		this.cbbDeclarationCustomsType.setSelectedIndex(0);

		this.cbbListBeginDate.setDate(null);
		this.cbbListEndDate.setDate(null);

		this.cbbStatusBeginDate.setDate(null);
		this.cbbStatusEndDate.setDate(null);

		this.cbbPortBeginDate.setDate(null);
		this.cbbPortEndDate.setDate(null);

		List list = materialManageAction.findScmCocs(new Request(CommonVars
				.getCurrUser(), true));
		this.cbb.setModel(new DefaultComboBoxModel(list.toArray()));
		this.cbb.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 100, 170));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbb, "code", "name", 270);
		cbb.setSelectedIndex(-1);
	}

	/**
	 * This method initializes cbb
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbb() {
		if (cbb == null) {
			cbb = new JComboBox();
			cbb.setBounds(new java.awt.Rectangle(186, 19, 123, 21));
		}
		return cbb;
	}
} // @jve:decl-index=0:visual-constraint="22,12"
