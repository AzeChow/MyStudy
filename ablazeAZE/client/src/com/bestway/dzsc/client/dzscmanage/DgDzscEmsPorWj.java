/*
 * Created on 2005-3-23
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.client.dzscmanage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.event.ItemEvent;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.NumberFormatter;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import com.bestway.bcs.client.dictpor.DgAllChangState;
import com.bestway.bcus.client.common.CommonQueryPage;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseComboBoxUI;
import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.custombase.entity.basecode.MachiningType;
import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.countrycode.District;
import com.bestway.bcus.custombase.entity.depcode.RedDep;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.LevyKind;
import com.bestway.bcus.custombase.entity.parametercode.PayMode;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.bcus.custombase.entity.parametercode.Transac;
import com.bestway.bcus.system.action.SystemAction;
import com.bestway.bcus.system.entity.Company;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.common.constant.DzscEmsType;
import com.bestway.common.constant.DzscManageType;
import com.bestway.common.constant.DzscState;
import com.bestway.common.constant.LimitFlag;
import com.bestway.common.constant.ManageObject;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.dzsc.dzscmanage.action.DzscAction;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgWj;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsImgWj;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorWjHead;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class DgDzscEmsPorWj extends JDialogBase {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	private javax.swing.JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JButton btnPrint = null;

	// private JComboBox cbbPrint = null;

	private JButton btnSave = null;

	private JButton btnEdit = null;

	private JButton btnExit = null;

	private JPanel jPanel = null;

	private JTabbedPane jTabbedPane = null;

	private JPanel jPanel1 = null;

	private JPanel jPanel2 = null;

	private JPanel jPanel3 = null;

	private JLabel jLabel2 = null;

	private JTextField tfEmsState = null;

	private JLabel jLabel3 = null;

	private JTextField tfCopTrNo = null;

	private JLabel jLabel4 = null;

	private JLabel jLabel5 = null;

	private JComboBox cbbDeclareCustoms = null;

	private JComboBox cbbEmsType = null;

	private MaterialManageAction materialManageAction = null;

	private JLabel jLabel8 = null;

	private JLabel jLabel9 = null;

	private JCalendarComboBox cbbBeginDate = null;

	private JCalendarComboBox cbbAvailabilityDate = null;

	private JLabel jLabel10 = null;

	private JLabel jLabel11 = null;

	private JComboBox jComboBox5 = null;

	private JLabel jLabel12 = null;

	private JTextField jTextField3 = null;

	private JLabel jLabel13 = null;

	private JComboBox jComboBox7 = null;

	private JLabel jLabel14 = null;

	private JCalendarComboBox jCalendarComboBox2 = null;

	private JLabel jLabel15 = null;

	private JTextField jTextField4 = null;

	private JLabel jLabel16 = null;

	private JTextField jTextField5 = null;

	private JLabel jLabel17 = null;

	private JCalendarComboBox cbbDestroyDate = null;

	private JLabel jLabel18 = null;

	private JTextField jTextField6 = null;

	private JLabel jLabel19 = null;

	private JTextField jTextField7 = null;

	private JLabel jLabel20 = null;

	private JComboBox jComboBox8 = null;

	private JLabel jLabel21 = null;

	private JTextField jTextField8 = null;

	private JLabel jLabel22 = null;

	private JTextField jTextField9 = null;

	private JLabel jLabel23 = null;

	private JTextField jTextField10 = null;

	private JLabel jLabel24 = null;

	private JTextField tfImpContractNo = null;

	private JLabel jLabel25 = null;

	private JTextField tfExpContractNo = null;

	private JLabel jLabel26 = null;

	private JLabel jLabel27 = null;

	private JLabel jLabel28 = null;

	private JComboBox jComboBox9 = null;

	private JLabel jLabel29 = null;

	private JComboBox cbbWardshipRate = null;

	private JLabel jLabel30 = null;

	private JLabel jLabel31 = null;

	private JComboBox jComboBox11 = null;

	private JLabel jLabel36 = null;

	private JTextField jTextField16 = null;

	private JLabel jLabel37 = null;

	private JCalendarComboBox jCalendarComboBox4 = null;

	private JLabel jLabel38 = null;

	private JComboBox jComboBox16 = null;

	private JLabel jLabel39 = null;

	private JComboBox jComboBox17 = null;

	private JLabel jLabel40 = null;

	private JLabel jLabel41 = null;

	private JTextField jTextField17 = null;

	private DzscAction dzscAction = null;

	private JTableListModel tableModelHead = null; // 表头

	private JTableListModel tableModelImg = null; // 料件清单备案

	private JTableListModel tableModelExg = null; // 成品清单备案

	private DzscEmsPorWjHead head = null; // 合同备案表头  //  @jve:decl-index=0:

	private JFormattedTextField tfImgAmount = null;

	private JFormattedTextField tfExgAmount = null;

	private JFormattedTextField tfWardshipFee = null;

	private JTextField tfEmsApprNo = null;

	private int dataState = DataState.BROWSE;

	private JToolBar jToolBar6 = null;

	private JLabel jLabel44 = null;

	private JButton btnAddImg = null;

	private JButton btnDeleteImg = null;

	private JTable tbImg = null;

	private JScrollPane jScrollPane2 = null;

	private JPanel jPanel7 = null;

	private JToolBar jToolBar7 = null;

	private JLabel jLabel45 = null;

	private JButton jButton21 = null;

	private JButton jButton23 = null;

	private JTable tbExg = null;

	private JScrollPane jScrollPane3 = null;

	private SystemAction service = null;

	private JButton btnUndo = null;

	private JLabel lables = null;

	private JTextField tfEmsNo = null;

	private NumberFormatter numberFormatter = null; // @jve:decl-index=0:

	private JLabel jLabel6 = null;

	private JComboBox cbbManageObject = null;

	private JLabel jLabel42 = null;

	private JComboBox cbbReceiveArea = null;

	private boolean isFirstTabbedPage = true;

	private JButton btnEditImg = null;

	private JButton btnEditExg = null;

	private JLabel jLabel1 = null;

	private JPanel jPanel4 = null;

	private JRadioButton rbMaterial = null;

	private JRadioButton rbComplex = null;

	private ButtonGroup buttonGroup = null; // @jve:decl-index=0:visual-constraint="775,192"

	private JTextField tfTradeCode = null;

	private JLabel jLabel101 = null;

	private JTextField tfTradeName = null;

	private JLabel jLabel43 = null;

	private JComboBox cbbRedDep = null;

	private JLabel jLabel46 = null;

	private JComboBox cbbLimitFlag = null;

	private JButton btnChangeDeclareState = null;

	private JPopupMenu pmChangeDeclareState = null; // @jve:decl-index=0:visual-constraint="779,110"

	private JMenuItem miUndoDeclare = null;

	private JButton btnChangeImgModifyMark = null;

	private JPopupMenu pmChangeModifyMark = null; // @jve:decl-index=0:visual-constraint="808,68"

	private JMenuItem miNotModified = null;

	private JMenuItem miModified = null;

	private JMenuItem miDelete = null;
	private JMenuItem miAdd = null;

	private JButton btnChangeExgModifyMark = null;

	private JPopupMenu jPopupMenuPrintReport = null;

	private JMenuItem miCoverPrintExg = null;

	private JMenuItem miNonCoverPrintExg = null;

	private JMenuItem miConverPrintImg = null;

	private JMenuItem miNonCoverPrintImg = null;

	private JMenuItem miExportExgRecordationChecklistChange = null;

	private JMenuItem miImportExgRecordationChecklistChange = null;

	private JMenuItem miNonDZSCContractFile = null;

	private JLabel jLabel = null;

	private JTextField tfCorrEmsNo = null;

	private JButton btnIEPort = null;

	private JLabel jLabel81 = null;

	private JCalendarComboBox calendarSaveDate = null;
	
	private String tfCopTrNoOld =null;

	/**
	 * @throws java.awt.HeadlessException
	 */
	public DgDzscEmsPorWj() throws HeadlessException {
		super();
		initialize();
		dzscAction = (DzscAction) CommonVars.getApplicationContext().getBean(
				"dzscAction");
		service = (SystemAction) CommonVars.getApplicationContext().getBean(
				"systemAction");
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(733, 632);
		this.setContentPane(getJContentPane());
		this.setTitle("合同修改");
		this.getButtonGroup();
	}

	public int getDataState() {
		return dataState;
	}

	public void setDataState(int dataState) {
		this.dataState = dataState;
	}

	public void setVisible(boolean isFlag) {
		if (isFlag == true) {
			initUI();
			showData();
		}
		super.setVisible(isFlag);
	}

	private void showData() {
		if (tableModelHead.getCurrentRow() != null) {
			head = (DzscEmsPorWjHead) tableModelHead.getCurrentRow();
			if (jTabbedPane.getSelectedIndex() == 0) {
				showHeadData();
			} else if (jTabbedPane.getSelectedIndex() == 1) {
				tableModelImg = DzscClientLogic.initTableImgWj(tbImg, head); // 料件
			} else if (jTabbedPane.getSelectedIndex() == 2) {
				tableModelExg = DzscClientLogic.initTableExgWj(tbExg, head);
			}
			setState();
			isFirstTabbedPage = jTabbedPane.getSelectedIndex() == 0;
		}
	}

	private void showHeadData() {
		if (head.getDeclareState().equals(DzscState.ORIGINAL)) {
			tfEmsState.setText("初始状态");
		} else if (head.getDeclareState().equals(DzscState.APPLY)) {
			tfEmsState.setText("正在申请");
		} else if (head.getDeclareState().equals(DzscState.CHANGE)) {
			tfEmsState.setText("正在变更");
		} else if (head.getDeclareState().equals(DzscState.EXECUTE)) {
			tfEmsState.setText("正在执行");
		}
		cbbDeclareCustoms.setSelectedItem(head.getCustomNo());
		cbbRedDep.setSelectedItem(head.getRedDep());
		if (head.getEmsType() != null) {
			cbbEmsType.setSelectedIndex(ItemProperty.getIndexByCode(head
					.getEmsType(), cbbEmsType));
		} else {
			cbbEmsType.setSelectedIndex(-1);
		}
		tfEmsNo.setText(head.getEmsNo() == null ? "" : head.getEmsNo());
		tfCopTrNo.setText(head.getCopTrNo());
		cbbBeginDate.setDate(head.getBeginDate());
		cbbAvailabilityDate.setDate(head.getEndDate());
		jComboBox5.setSelectedItem(head.getTrade());
		jComboBox7.setSelectedItem(head.getTradeCountry());
		tfTradeCode.setText(head.getTradeCode());
		tfTradeName.setText(head.getTradeName());
		jTextField3.setText(head.getMachName());
		jTextField4.setText(head.getMachCode());
		jTextField5.setText(head.getEnterpriseAddress());
		jCalendarComboBox2.setDate(head.getDeferDate());
		cbbDestroyDate.setDate(head.getDestroyDate());
		jComboBox8.setSelectedItem(head.getLevyKind());
		jTextField7.setText(head.getContactTel());
		jTextField6.setText(head.getLinkMan());
		jTextField8.setText(head.getOutTradeCo());
		jTextField9.setText(head.getSancEmsNo());
		tfExpContractNo.setText(head.getImContractNo());
		tfImpContractNo.setText(head.getIeContractNo());
		jTextField10.setText(head.getAgreementNo());
		tfImgAmount.setText(doubleToStr(head.getImgAmount()));
		tfExgAmount.setText(doubleToStr(head.getExgAmount()));
		jComboBox9.setSelectedItem(head.getCurr());
		jComboBox11.setSelectedItem(head.getTransac());
		tfWardshipFee.setText(doubleToStr(head.getWardshipFee()));
		cbbWardshipRate.setSelectedItem(head.getWardshipRate());
		jTextField16.setText(head.getApprover());
		jCalendarComboBox4.setDate(head.getApproveDate());
		jComboBox16.setSelectedItem(head.getPayMode());
		jComboBox17.setSelectedItem(head.getMachiningType());
		jTextField17.setText(head.getNote());
		tfEmsApprNo.setText(head.getEmsApprNo());
		calendarSaveDate.setDate(head.getSaveDate());
		this.cbbReceiveArea.setSelectedItem(head.getReceiveArea());
		this.cbbManageObject.setSelectedIndex(ItemProperty.getIndexByCode(
				String.valueOf(head.getManageObject()), cbbManageObject));
		this.cbbLimitFlag.setSelectedIndex(ItemProperty.getIndexByCode(head
				.getLimitFlag(), cbbLimitFlag));
		this.tfCorrEmsNo.setText(head.getCorrEmsNo());
		if(tfCopTrNo.getText()!=null){
		tfCopTrNoOld=tfCopTrNo.getText();
		}
		if (head.getDzscManageType() != null) {
			if (head.getDzscManageType().equals(DzscManageType.MATERIAL)) {
				this.rbMaterial.setSelected(true);
			} else if (head.getDzscManageType().equals(DzscManageType.COMPLEX)) {
				this.rbComplex.setSelected(true);
			}
		}
	}

	public NumberFormatter getNumberFormatter() {
		if (numberFormatter == null) {
			numberFormatter = new NumberFormatter();
		}
		return numberFormatter;
	}

	/**
	 * This method initializes pnContent
	 * 
	 * @return javax.swing.JPanel
	 */
	public Double strToDouble(String value) { // 转换strToDouble 填充数据
		try {
			if (value == null || "".equals(value)) {
				// return new Double("0");
				return null;
			}
			getNumberFormatter().setValueClass(Double.class);
			return (Double) getNumberFormatter().stringToValue(value);
		} catch (ParseException e) {
			e.printStackTrace();
			// return new Double("0");
			return null;
		}
	}

	public String doubleToStr(Double value) { // 转换doubleToStr 取数据
		try {
			if (value == null || value.doubleValue() == 0) {
				return null;
			}
			getNumberFormatter().setValueClass(Double.class);
			return getNumberFormatter().valueToString(value);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	private void initUI() {
		List list = null;
		// 主管外经贸部门
		list = CustomBaseList.getInstance().getRedDeps();
		DzscClientLogic.intoComboBox(list, cbbRedDep);
		// 申报关区号
		list = CustomBaseList.getInstance().getCustoms();
		DzscClientLogic.intoComboBox(list, cbbDeclareCustoms);
		// 贸易方式
		list = CustomBaseList.getInstance().getTrades();
		DzscClientLogic.intoComboBox(list, jComboBox5);
		// 贸易国别
		list = CustomBaseList.getInstance().getCountrys();
		DzscClientLogic.intoComboBox(list, jComboBox7);
		// 征面性质
		list = CustomBaseList.getInstance().getLevyKind();
		DzscClientLogic.intoComboBox(list, jComboBox8);
		// 保税方式
		list = CustomBaseList.getInstance().getPayModes();
		DzscClientLogic.intoComboBox(list, jComboBox16);
		// 加工种类
		list = CustomBaseList.getInstance().getMachiningTypes();
		DzscClientLogic.intoComboBox(list, jComboBox17);
		// 币制
		list = CustomBaseList.getInstance().getCurrs();
		DzscClientLogic.intoComboBox(list, jComboBox9);
		// 成交方式
		list = CustomBaseList.getInstance().getTransacs();
		DzscClientLogic.intoComboBox(list, jComboBox11);
		// 收货地区
		list = CustomBaseList.getInstance().getDistrict();
		DzscClientLogic.intoComboBox(list, this.cbbReceiveArea);
		// 合同性质
		cbbEmsType.removeAllItems();
		cbbEmsType.addItem(new ItemProperty(DzscEmsType.COME_PROCESS_CONTRACT,
				"来料加工合同手册"));
		cbbEmsType.addItem(new ItemProperty(
				DzscEmsType.IMPORT_PROCESS_CONTRACT, "进料加工合同手册"));
		cbbEmsType.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbEmsType);
		// 监管费用率
		cbbWardshipRate.removeAllItems();
		cbbWardshipRate.addItem("0.001");
		cbbWardshipRate.addItem("0.0015");
		cbbWardshipRate.addItem("0.003");
		cbbWardshipRate.setUI(new CustomBaseComboBoxUI(127));
		// 打印
		// cbbPrint.removeAllItems();
		// cbbPrint.addItem(" 套打成品表");
		// cbbPrint.addItem("非套打成品表");
		// cbbPrint.addItem(" 套打料件表");
		// cbbPrint.addItem("非套打料件表");
		// // 新增两张变更表
		//		
		// cbbPrint.addItem("出口成品备案清单变更表");
		// cbbPrint.addItem("进口料件备案清单变更表");
		// 初始化管理对象
		this.cbbManageObject.removeAllItems();
		this.cbbManageObject.addItem(new ItemProperty(ManageObject.MANAGE_COP,
				"经营单位"));
		this.cbbManageObject.addItem(new ItemProperty(
				ManageObject.MANUFACTURE_COP, "加工单位"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbManageObject);
		this.cbbManageObject.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		cbbManageObject.setSelectedIndex(-1);

		// 限制类标志
		cbbLimitFlag.removeAllItems();
		cbbLimitFlag.addItem(new ItemProperty(LimitFlag.ADJUST_BEFORE_EMS,
				"调整前旧手册"));
		cbbLimitFlag.addItem(new ItemProperty(LimitFlag.ADJUST_AFTER_EMS,
				"调整后新手册"));
		cbbLimitFlag.addItem(new ItemProperty(LimitFlag.ACOUNT_BOOK_USE,
				"台账专用手册"));
		cbbLimitFlag.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbLimitFlag);
	}

	/**
	 * This method initializes pnContent
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new java.awt.BorderLayout());
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJPanel(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes tb
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnSave());
			jToolBar.add(getBtnUndo());
			jToolBar.add(getBtnChangeDeclareState());
			jToolBar.add(getBtnPrint());
			jToolBar.add(getBtnExit());
		}
		return jToolBar;
	}

	/**
	 * This method initializes btnCustomSort
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton();
			btnPrint.setText("打印表格");
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// printReport();
					getJPopupMenuPrintReport().show(btnPrint, 0,
							btnPrint.getHeight());
				}
			});
		}
		return btnPrint;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	/*
	 * private JComboBox getCbbPrint() { if (cbbPrint == null) { cbbPrint = new
	 * JComboBox(); cbbPrint.setBounds(new java.awt.Rectangle(9, 3, 175, 24)); }
	 * return cbbPrint; }
	 */

	private String getDeclareState() {
		// if (head.getDeclareState().equals(DzscState.APPLY)) {
		// jTextField.setText("正在申请");
		// } else if (head.getDeclareState().equals(DzscState.CHANGE)) {
		// jTextField.setText("正在变更");
		// } else if (head.getDeclareState().equals(DzscState.EXECUTE)) {
		// jTextField.setText("正在执行");
		// }
		return head.getDeclareState();
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("保存");
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!saveHeadData()) {
						return;
					} else {
						// showHeadData();
						JOptionPane.showMessageDialog(DgDzscEmsPorWj.this,
								"合同备案表头保存成功", "提示",
								JOptionPane.INFORMATION_MESSAGE);
					}
					// dataState = DataState.BROWSE;
					// setState();
				}
			});
		}
		return btnSave;
	}

	/**
	 * 判断手册号是否重复
	 * 
	 * @param head
	 * @return
	 */
	private boolean checkEmsPorWjHeadDuple(DzscEmsPorWjHead head) {
		return this.dzscAction.checkEmsPorWjHeadDuple(new Request(CommonVars
				.getCurrUser(), true), head);
	}

	private void fillData(DzscEmsPorWjHead head) {
		head.setCustomNo((Customs) cbbDeclareCustoms.getSelectedItem());
		head.setRedDep((RedDep) cbbRedDep.getSelectedItem());
		if (cbbEmsType.getSelectedItem() != null) {
			head.setEmsType(((ItemProperty) cbbEmsType.getSelectedItem())
					.getCode());
		}
		head.setCopTrNo(tfCopTrNo.getText());
		head.setBeginDate(cbbBeginDate.getDate());
		head.setEndDate(cbbAvailabilityDate.getDate());
		head.setTrade((Trade) jComboBox5.getSelectedItem());
		head.setTradeCountry((Country) jComboBox7.getSelectedItem());
		head.setDeferDate(jCalendarComboBox2.getDate());
		head.setDestroyDate(cbbDestroyDate.getDate());
		head.setEnterpriseAddress(jTextField5.getText());
		head.setLinkMan(jTextField6.getText());
		head.setContactTel(jTextField7.getText());
		head.setLevyKind((LevyKind) jComboBox8.getSelectedItem());
		head.setSancEmsNo(jTextField9.getText());
		head.setOutTradeCo(jTextField8.getText());
		head.setAgreementNo(jTextField10.getText());
		head.setIeContractNo(tfImpContractNo.getText());
		head.setImContractNo(tfExpContractNo.getText());
		head.setCurr((Curr) jComboBox9.getSelectedItem());
		head.setExgAmount(strToDouble(tfExgAmount.getText()));
		head.setImgAmount(strToDouble(tfImgAmount.getText()));
		head.setSaveDate(calendarSaveDate.getDate());
		if (cbbWardshipRate.getSelectedItem() != null
				&& !cbbWardshipRate.getSelectedItem().equals("")) {
			head.setWardshipRate(Double.valueOf(cbbWardshipRate
					.getSelectedItem().toString()));
		}
		head.setWardshipFee(strToDouble(tfWardshipFee.getText()));
		head.setTransac((Transac) jComboBox11.getSelectedItem());
		head.setReceiveArea((District) this.cbbReceiveArea.getSelectedItem());
		head.setApprover(jTextField16.getText());
		head.setApproveDate(jCalendarComboBox4.getDate());
		head.setEmsApprNo(tfEmsApprNo.getText());
		head.setEmsNo(tfEmsNo.getText().trim());
		head.setCorrEmsNo(tfCorrEmsNo.getText().trim());
		head.setMachiningType((MachiningType) jComboBox17.getSelectedItem());
		head.setPayMode((PayMode) jComboBox16.getSelectedItem());
		if (this.cbbLimitFlag.getSelectedItem() != null) {
			head.setLimitFlag(((ItemProperty) cbbLimitFlag.getSelectedItem())
					.getCode());
		} else {
			head.setLimitFlag(null);
		}
		head.setNote(jTextField17.getText());
		if (this.cbbManageObject.getSelectedItem() != null) {
			ItemProperty item = (ItemProperty) cbbManageObject
					.getSelectedItem();
			head.setManageObject(item.getCode());
		}
		if (!ModifyMarkState.ADDED.equals(head.getModifyMark())) {
			head.setModifyMark(ModifyMarkState.MODIFIED);
		}
		if (this.rbMaterial.isSelected()) {
			head.setDzscManageType(DzscManageType.MATERIAL);
		} else if (this.rbComplex.isSelected()) {
			head.setDzscManageType(DzscManageType.COMPLEX);
		}
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改");
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					dataState = DataState.EDIT;
					setState();

				}
			});
		}
		return btnEdit;
	}

	/**
	 * This method initializes jButton5
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("关闭");
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgDzscEmsPorWj.this.dispose();
				}
			});
		}
		return btnExit;
	}

	/**
	 * This method initializes pn
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.add(getJTabbedPane(), java.awt.BorderLayout.CENTER);

		}
		return jPanel;
	}

	/**
	 * This method initializes tpn
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setTabPlacement(javax.swing.JTabbedPane.TOP);
			jTabbedPane.addTab("基本情况", null, getJPanel1(), null);
			jTabbedPane.addTab("料件总表", null, getJPanel2(), null);
			jTabbedPane.addTab("成品总表", null, getJPanel3(), null);
			jTabbedPane
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							if (jTabbedPane.getSelectedIndex() != 0
									&& dataState != DataState.BROWSE
									&& isFirstTabbedPage) {
								if (!saveHeadData()) {
									return;
								}
							}
							showData();
							setState();
						}
					});
		}
		return jTabbedPane;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLabel81 = new JLabel();
			jLabel81.setBounds(new Rectangle(60, 468, 48, 20));
			jLabel81.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel81.setText("录入日期");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(22, 403, 86, 24));
			jLabel.setText("合同备案手册号");
			jLabel46 = new JLabel();
			jLabel46.setBounds(new Rectangle(257, 375, 66, 22));
			jLabel46.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel46.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel46.setForeground(Color.blue);
			jLabel46.setText("限制类标志");
			jLabel43 = new JLabel();
			jLabel43.setBounds(new Rectangle(240, 43, 83, 23));
			jLabel43.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel43.setText("主管外经部门");
			jLabel43.setForeground(Color.blue);
			jLabel101 = new JLabel();
			jLabel101.setBounds(new Rectangle(241, 72, 82, 23));
			jLabel101.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel101.setText("经营单位名称");
			jLabel101.setForeground(Color.black);
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(9, 433, 99, 24));
			jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel1.setText("电子手册监管模式");
			jLabel42 = new JLabel();
			jLabel42.setBounds(new Rectangle(48, 377, 60, 21));
			jLabel42.setText("收货地区");
			jLabel42.setForeground(java.awt.Color.blue);
			jLabel42.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel6 = new JLabel();
			jLabel6.setBounds(new Rectangle(477, 43, 65, 21));
			jLabel6.setText("管理对象");
			jLabel6.setForeground(java.awt.Color.blue);
			jLabel6.setHorizontalAlignment(SwingConstants.RIGHT);
			lables = new JLabel();
			lables.setBounds(new Rectangle(24, 133, 86, 22));
			lables.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			lables.setText("通关备案手册号");
			jLabel41 = new JLabel();
			jLabel40 = new JLabel();
			jLabel39 = new JLabel();
			jLabel38 = new JLabel();
			jLabel37 = new JLabel();
			jLabel36 = new JLabel();
			jLabel31 = new JLabel();
			jLabel30 = new JLabel();
			jLabel29 = new JLabel();
			jLabel28 = new JLabel();
			jLabel27 = new JLabel();
			jLabel26 = new JLabel();
			jLabel25 = new JLabel();
			jLabel24 = new JLabel();
			jLabel23 = new JLabel();
			jLabel22 = new JLabel();
			jLabel21 = new JLabel();
			jLabel20 = new JLabel();
			jLabel19 = new JLabel();
			jLabel18 = new JLabel();
			jLabel17 = new JLabel();
			jLabel16 = new JLabel();
			jLabel15 = new JLabel();
			jLabel14 = new JLabel();
			jLabel13 = new JLabel();
			jLabel12 = new JLabel();
			jLabel11 = new JLabel();
			jLabel10 = new JLabel();
			jLabel9 = new JLabel();
			jLabel8 = new JLabel();
			jLabel5 = new JLabel();
			jLabel4 = new JLabel();
			jLabel3 = new JLabel();
			jLabel2 = new JLabel();
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jLabel2.setBounds(54, 15, 57, 22);
			jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel2.setText("合同状态");
			jLabel3.setBounds(464, 15, 77, 22);
			jLabel3.setForeground(java.awt.Color.blue);
			jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel3.setText("企业内部编号");
			jLabel4.setBounds(50, 43, 60, 22);
			jLabel4.setForeground(Color.blue);
			jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel4.setText("主管海关");
			jLabel5.setBounds(265, 15, 58, 22);
			jLabel5.setForeground(java.awt.Color.blue);
			jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel5.setText("手册类型");
			jLabel8.setBounds(256, 133, 66, 22);
			jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel8.setText("起始日期");
			jLabel8.setForeground(java.awt.Color.blue);
			jLabel9.setBounds(476, 133, 64, 20);
			jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel9.setText("有效日期");
			jLabel9.setForeground(java.awt.Color.blue);
			jLabel10.setBounds(52, 72, 57, 22);
			jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel10.setText("经营单位");
			jLabel10.setForeground(Color.black);
			jLabel11.setBounds(50, 162, 60, 22);
			jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel11.setText("贸易方式");
			jLabel11.setForeground(java.awt.Color.blue);
			jLabel12.setBounds(49, 100, 61, 22);
			jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel12.setText("收货单位");
			jLabel13.setBounds(260, 162, 63, 22);
			jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel13.setText("贸易国别");
			jLabel14.setBounds(258, 189, 66, 22);
			jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel14.setText("延期期限");
			jLabel15.setBounds(243, 103, 80, 22);
			jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel15.setText("收货单位名称");
			jLabel16.setBounds(48, 190, 62, 22);
			jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel16.setText("企业地址");
			jLabel16.setForeground(java.awt.Color.blue);
			jLabel17.setBounds(478, 191, 63, 22);
			jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel17.setText("核销到期");
			jLabel18.setBounds(47, 216, 63, 22);
			jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel18.setText("联系人");
			jLabel18.setForeground(java.awt.Color.blue);
			jLabel19.setBounds(263, 215, 60, 22);
			jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel19.setText("联系电话");
			jLabel19.setForeground(java.awt.Color.blue);
			jLabel20.setBounds(484, 216, 57, 22);
			jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel20.setText("征免性质");
			jLabel20.setForeground(java.awt.Color.blue);
			jLabel21.setBounds(49, 243, 61, 22);
			jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel21.setText("外商公司");
			jLabel21.setForeground(java.awt.Color.blue);
			jLabel22.setBounds(482, 243, 59, 22);
			jLabel22.setForeground(java.awt.Color.black);
			jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel22.setText("批文号");
			jLabel23.setBounds(50, 269, 60, 22);
			jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel23.setText("协议书号");
			jLabel24.setBounds(255, 268, 68, 22);
			jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel24.setText("进口合同号");
			jLabel24.setForeground(java.awt.Color.blue);
			jLabel25.setBounds(467, 269, 74, 22);
			jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel25.setText("出口合同号");
			jLabel26.setBounds(53, 295, 55, 22);
			jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel26.setText("进口总值");
			jLabel26.setForeground(java.awt.Color.blue);
			jLabel27.setBounds(259, 294, 64, 22);
			jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel27.setText("出口总值");
			jLabel28.setBounds(493, 295, 48, 22);
			jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel28.setText("币制");
			jLabel28.setForeground(java.awt.Color.blue);
			jLabel29.setBounds(45, 321, 63, 22);
			jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel29.setText("监管费率");
			jLabel30.setBounds(259, 320, 64, 22);
			jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel30.setText("监管费");
			jLabel31.setBounds(479, 321, 62, 22);
			jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel31.setText("成交方式");
			jLabel36.setBounds(474, 348, 67, 22);
			jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel36.setText("审批人");
			jLabel37.setBounds(485, 375, 57, 22);
			jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel37.setText("审批日期");
			jLabel38.setBounds(47, 349, 61, 22);
			jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel38.setText("保税方式");
			jLabel39.setBounds(255, 348, 67, 22);
			jLabel39.setForeground(java.awt.Color.blue);
			jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel39.setText("加工种类");
			jLabel40.setBounds(486, 163, 55, 22);
			jLabel40.setForeground(java.awt.Color.blue);
			jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel40.setText("批准文号");
			jLabel41.setBounds(261, 403, 61, 22);
			jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel41.setText("备注");
			jPanel1.add(jLabel2, null);
			jPanel1.add(getTfEmsState(), null);
			jPanel1.add(jLabel3, null);
			jPanel1.add(getTfCopTrNo(), null);
			jPanel1.add(jLabel4, null);
			jPanel1.add(jLabel5, null);
			jPanel1.add(getCbbDeclareCustoms(), null);
			jPanel1.add(getCbbEmsType(), null);
			jPanel1.add(jLabel8, null);
			jPanel1.add(jLabel9, null);
			jPanel1.add(getCbbBeginDate(), null);
			jPanel1.add(getCbbAvailabilityDate(), null);
			jPanel1.add(jLabel10, null);
			jPanel1.add(jLabel11, null);
			jPanel1.add(getJComboBox5(), null);
			jPanel1.add(jLabel12, null);
			jPanel1.add(getJTextField3(), null);
			jPanel1.add(jLabel13, null);
			jPanel1.add(getJComboBox7(), null);
			jPanel1.add(jLabel14, null);
			jPanel1.add(getJCalendarComboBox2(), null);
			jPanel1.add(jLabel15, null);
			jPanel1.add(getJTextField4(), null);
			jPanel1.add(jLabel16, null);
			jPanel1.add(getJTextField5(), null);
			jPanel1.add(jLabel17, null);
			jPanel1.add(getCbbDestroyDate(), null);
			jPanel1.add(jLabel18, null);
			jPanel1.add(getJTextField6(), null);
			jPanel1.add(jLabel19, null);
			jPanel1.add(getJTextField7(), null);
			jPanel1.add(jLabel20, null);
			jPanel1.add(getJComboBox8(), null);
			jPanel1.add(jLabel21, null);
			jPanel1.add(getJTextField8(), null);
			jPanel1.add(jLabel22, null);
			jPanel1.add(getJTextField9(), null);
			jPanel1.add(jLabel23, null);
			jPanel1.add(getJTextField10(), null);
			jPanel1.add(jLabel24, null);
			jPanel1.add(getTfImpContractNo(), null);
			jPanel1.add(jLabel25, null);
			jPanel1.add(getTfExpContractNo(), null);
			jPanel1.add(jLabel26, null);
			jPanel1.add(jLabel27, null);
			jPanel1.add(jLabel28, null);
			jPanel1.add(getJComboBox9(), null);
			jPanel1.add(jLabel29, null);
			jPanel1.add(getCbbWardshipRate(), null);
			jPanel1.add(jLabel30, null);
			jPanel1.add(jLabel31, null);
			jPanel1.add(getJComboBox11(), null);
			jPanel1.add(jLabel36, null);
			jPanel1.add(getJTextField16(), null);
			jPanel1.add(jLabel37, null);
			jPanel1.add(getJCalendarComboBox4(), null);
			jPanel1.add(jLabel38, null);
			jPanel1.add(getJComboBox16(), null);
			jPanel1.add(jLabel39, null);
			jPanel1.add(getJComboBox17(), null);
			jPanel1.add(jLabel40, null);
			jPanel1.add(jLabel41, null);
			jPanel1.add(getJTextField17(), null);
			jPanel1.add(getTfImgAmount(), null);
			jPanel1.add(getTfExgAmount(), null);
			jPanel1.add(getTfWardshipFee(), null);
			jPanel1.add(getTfEmsApprNo(), null);
			jPanel1.add(lables, null);
			jPanel1.add(getTfEmsNo(), null);
			jPanel1.add(jLabel6, null);
			jPanel1.add(getCbbManageObject(), null);
			jPanel1.add(jLabel42, null);
			jPanel1.add(getCbbReceiveArea(), null);
			jPanel1.add(jLabel1, null);
			jPanel1.add(getJPanel4(), null);
			jPanel1.add(getTfTradeCode(), null);
			jPanel1.add(jLabel101, null);
			jPanel1.add(getTfTradeName(), null);
			jPanel1.add(jLabel43, null);
			jPanel1.add(getCbbRedDep(), null);
			jPanel1.add(jLabel46, null);
			jPanel1.add(getCbbLimitFlag(), null);
			jPanel1.add(jLabel, null);
			jPanel1.add(getTfCorrEmsNo(), null);
			jPanel1.add(getBtnIEPort(), null);
			jPanel1.add(jLabel81, null);
			jPanel1.add(getCalendarSaveDate(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(new BorderLayout());
			jPanel2.add(getJToolBar6(), java.awt.BorderLayout.NORTH);
			jPanel2.add(getJScrollPane2(), java.awt.BorderLayout.CENTER);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setLayout(new BorderLayout());
			jPanel3.add(getJPanel7(), java.awt.BorderLayout.CENTER);
		}
		return jPanel3;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfEmsState() {
		if (tfEmsState == null) {
			tfEmsState = new JTextField();
			tfEmsState.setEditable(false);
			tfEmsState.setBounds(114, 15, 126, 22);
		}
		return tfEmsState;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCopTrNo() {
		if (tfCopTrNo == null) {
			tfCopTrNo = new JTextField();
			tfCopTrNo.setEditable(false);
			tfCopTrNo.setBounds(544, 15, 127, 22);
		}
		return tfCopTrNo;
	}

	/**
	 * This method initializes jComboBox2
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbDeclareCustoms() {
		if (cbbDeclareCustoms == null) {
			cbbDeclareCustoms = new JComboBox();
			cbbDeclareCustoms.setBounds(114, 42, 126, 22);
		}
		return cbbDeclareCustoms;
	}

	/**
	 * This method initializes jComboBox3
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbEmsType() {
		if (cbbEmsType == null) {
			cbbEmsType = new JComboBox();
			cbbEmsType.setBounds(325, 15, 127, 22);
		}
		return cbbEmsType;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(325, 132, 127, 22);
			cbbBeginDate.addChangeListener(new ChangeListener() {

				public void stateChanged(ChangeEvent arg0) {
					if (dataState == DataState.BROWSE) {
						return;
					}
					if (head == null || head.getIsImportFromQP() == null
							|| head.getIsImportFromQP()) {
						return;
					}
					Calendar beginDate = cbbBeginDate.getCalendar();
					Calendar endDate = null;
					Calendar destroyDate = null;
					if (beginDate != null) {
						endDate = (Calendar) beginDate.clone();
						endDate.add(Calendar.MONTH, 6);
						destroyDate = (Calendar) endDate.clone();
						destroyDate.add(Calendar.MONTH, 1);
					}
					cbbAvailabilityDate.setCalendar(endDate);
					cbbDestroyDate.setCalendar(destroyDate);
				}

			});
		}
		return cbbBeginDate;
	}

	/**
	 * This method initializes jCalendarComboBox1
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbAvailabilityDate() {
		if (cbbAvailabilityDate == null) {
			cbbAvailabilityDate = new JCalendarComboBox();
			cbbAvailabilityDate.setBounds(543, 133, 127, 22);
		}
		return cbbAvailabilityDate;
	}

	/**
	 * This method initializes jComboBox5
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox5() {
		if (jComboBox5 == null) {
			jComboBox5 = new JComboBox();
			jComboBox5.setBounds(114, 162, 126, 22);
		}
		return jComboBox5;
	}

	/**
	 * This method initializes jTextField3
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField3() {
		if (jTextField3 == null) {
			jTextField3 = new JTextField();
			jTextField3.setEditable(false);
			jTextField3.setBounds(325, 101, 346, 25);
		}
		return jTextField3;
	}

	/**
	 * This method initializes jComboBox7
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox7() {
		if (jComboBox7 == null) {
			jComboBox7 = new JComboBox();
			jComboBox7.setBounds(325, 162, 127, 22);
		}
		return jComboBox7;
	}

	/**
	 * This method initializes jCalendarComboBox2
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getJCalendarComboBox2() {
		if (jCalendarComboBox2 == null) {
			jCalendarComboBox2 = new JCalendarComboBox();
			jCalendarComboBox2.setBounds(325, 189, 127, 20);
		}
		return jCalendarComboBox2;
	}

	/**
	 * This method initializes jTextField4
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField4() {
		if (jTextField4 == null) {
			jTextField4 = new JTextField();
			jTextField4.setEditable(false);
			jTextField4.setBounds(114, 101, 126, 25);
		}
		return jTextField4;
	}

	/**
	 * This method initializes jTextField5
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField5() {
		if (jTextField5 == null) {
			jTextField5 = new JTextField();
			jTextField5.setBounds(114, 190, 126, 22);
		}
		return jTextField5;
	}

	/**
	 * This method initializes jCalendarComboBox3
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbDestroyDate() {
		if (cbbDestroyDate == null) {
			cbbDestroyDate = new JCalendarComboBox();
			cbbDestroyDate.setBounds(544, 191, 127, 23);
		}
		return cbbDestroyDate;
	}

	/**
	 * This method initializes jTextField6
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField6() {
		if (jTextField6 == null) {
			jTextField6 = new JTextField();
			jTextField6.setBounds(114, 216, 126, 22);
		}
		return jTextField6;
	}

	/**
	 * This method initializes jTextField7
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField7() {
		if (jTextField7 == null) {
			jTextField7 = new JTextField();
			jTextField7.setBounds(325, 215, 127, 22);
		}
		return jTextField7;
	}

	/**
	 * This method initializes jComboBox8
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox8() {
		if (jComboBox8 == null) {
			jComboBox8 = new JComboBox();
			jComboBox8.setBounds(544, 216, 127, 22);
		}
		return jComboBox8;
	}

	/**
	 * This method initializes jTextField8
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField8() {
		if (jTextField8 == null) {
			jTextField8 = new JTextField();
			jTextField8.setBounds(113, 243, 340, 22);
		}
		return jTextField8;
	}

	/**
	 * This method initializes jTextField9
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField9() {
		if (jTextField9 == null) {
			jTextField9 = new JTextField();
			jTextField9.setBounds(544, 243, 127, 22);
		}
		return jTextField9;
	}

	/**
	 * This method initializes jTextField10
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField10() {
		if (jTextField10 == null) {
			jTextField10 = new JTextField();
			jTextField10.setBounds(113, 269, 127, 22);
		}
		return jTextField10;
	}

	/**
	 * This method initializes jTextField11
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfImpContractNo() {
		if (tfImpContractNo == null) {
			tfImpContractNo = new JTextField();
			tfImpContractNo.setBounds(326, 268, 127, 22);
			tfImpContractNo.getDocument().addDocumentListener(
					new DocumentListener() {

						public void insertUpdate(DocumentEvent e) {
							tfExpContractNo.setText(tfImpContractNo.getText());
						}

						public void removeUpdate(DocumentEvent e) {
							tfExpContractNo.setText(tfImpContractNo.getText());
						}

						public void changedUpdate(DocumentEvent e) {
							tfExpContractNo.setText(tfImpContractNo.getText());
						}

					});
		}
		return tfImpContractNo;
	}

	/**
	 * This method initializes jTextField12
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfExpContractNo() {
		if (tfExpContractNo == null) {
			tfExpContractNo = new JTextField();
			tfExpContractNo.setBounds(544, 269, 127, 22);
		}
		return tfExpContractNo;
	}

	/**
	 * This method initializes jComboBox9
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox9() {
		if (jComboBox9 == null) {
			jComboBox9 = new JComboBox();
			jComboBox9.setBounds(544, 295, 127, 22);
		}
		return jComboBox9;
	}

	/**
	 * This method initializes jComboBox10
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbWardshipRate() {
		if (cbbWardshipRate == null) {
			cbbWardshipRate = new JComboBox();
			cbbWardshipRate.setBounds(113, 321, 127, 22);
			cbbWardshipRate.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						setWardshipFee();
					}
				}
			});
		}
		return cbbWardshipRate;
	}

	/**
	 * This method initializes jComboBox11
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox11() {
		if (jComboBox11 == null) {
			jComboBox11 = new JComboBox();
			jComboBox11.setBounds(544, 321, 127, 22);
		}
		return jComboBox11;
	}

	/**
	 * This method initializes jTextField16
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField16() {
		if (jTextField16 == null) {
			jTextField16 = new JTextField();
			jTextField16.setBounds(544, 348, 127, 22);
		}
		return jTextField16;
	}

	/**
	 * This method initializes jCalendarComboBox4
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getJCalendarComboBox4() {
		if (jCalendarComboBox4 == null) {
			jCalendarComboBox4 = new JCalendarComboBox();
			jCalendarComboBox4.setBounds(544, 375, 127, 22);
		}
		return jCalendarComboBox4;
	}

	/**
	 * This method initializes jComboBox16
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox16() {
		if (jComboBox16 == null) {
			jComboBox16 = new JComboBox();
			jComboBox16.setBounds(113, 349, 127, 22);
		}
		return jComboBox16;
	}

	/**
	 * This method initializes jComboBox17
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox17() {
		if (jComboBox17 == null) {
			jComboBox17 = new JComboBox();
			jComboBox17.setBounds(326, 348, 127, 22);
		}
		return jComboBox17;
	}

	/**
	 * This method initializes jTextField17
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField17() {
		if (jTextField17 == null) {
			jTextField17 = new JTextField();
			jTextField17.setBounds(326, 403, 128, 22);
		}
		return jTextField17;
	}

	/**
	 * This method initializes jFormattedTextField
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfImgAmount() {
		if (tfImgAmount == null) {
			tfImgAmount = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfImgAmount.setText("0");
			tfImgAmount.setBounds(113, 295, 127, 22);
			tfImgAmount.getDocument().addDocumentListener(
					new DocumentListener() {

						public void insertUpdate(DocumentEvent e) {
							setWardshipFee();
						}

						public void removeUpdate(DocumentEvent e) {
							setWardshipFee();

						}

						public void changedUpdate(DocumentEvent e) {
							setWardshipFee();

						}

					});
		}
		return tfImgAmount;
	}

	/**
	 * This method initializes jFormattedTextField1
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfExgAmount() {
		if (tfExgAmount == null) {
			tfExgAmount = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfExgAmount.setText("0");
			tfExgAmount.setBounds(326, 296, 127, 22);
		}
		return tfExgAmount;
	}

	/**
	 * This method initializes jFormattedTextField2
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfWardshipFee() {
		if (tfWardshipFee == null) {
			tfWardshipFee = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfWardshipFee.setText("0");
			tfWardshipFee.setBounds(326, 322, 127, 22);
		}
		return tfWardshipFee;
	}

	/**
	 * This method initializes jTextField13
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfEmsApprNo() {
		if (tfEmsApprNo == null) {
			tfEmsApprNo = new JTextField();
			tfEmsApprNo.setBounds(544, 162, 127, 22);
		}
		return tfEmsApprNo;
	}

	/**
	 * @return Returns the tableModelHead.
	 */
	public JTableListModel getTableModelHead() {
		return tableModelHead;
	}

	/**
	 * @param tableModelHead
	 *            The tableModelHead to set.
	 */
	public void setTableModelHead(JTableListModel tableModelHead) {
		this.tableModelHead = tableModelHead;
	}

	private void setTabbedPaneChangeState() {
		btnUndo.setEnabled((jTabbedPane.getSelectedIndex() == 0)
				&& dataState != DataState.BROWSE); // 撤消
		btnEdit
				.setEnabled((jTabbedPane.getSelectedIndex() == 0)
						&& dataState == DataState.BROWSE
						&& (DzscState.ORIGINAL.equals(getDeclareState()) || DzscState.CHANGE
								.equals(getDeclareState()))); // 修改
		btnSave.setEnabled((jTabbedPane.getSelectedIndex() == 0)
				&& dataState != DataState.BROWSE); // 保存
	}

	private void setState() {
		setTabbedPaneChangeState();
		cbbDeclareCustoms.setEnabled(dataState != DataState.BROWSE);
		cbbRedDep.setEnabled(dataState != DataState.BROWSE);
		tfCopTrNo.setEditable(dataState != DataState.BROWSE);
		cbbEmsType.setEnabled(dataState != DataState.BROWSE);
		cbbBeginDate.setEnabled(dataState != DataState.BROWSE);
		cbbAvailabilityDate.setEnabled(dataState != DataState.BROWSE);
		jComboBox5.setEnabled(dataState != DataState.BROWSE);
		jComboBox7.setEnabled(dataState != DataState.BROWSE);
		jTextField5.setEditable(dataState != DataState.BROWSE);
		jCalendarComboBox2.setEnabled(dataState != DataState.BROWSE);
		cbbDestroyDate.setEnabled(dataState != DataState.BROWSE);
		jComboBox8.setEnabled(dataState != DataState.BROWSE);
		jTextField7.setEditable(dataState != DataState.BROWSE);
		jTextField6.setEditable(dataState != DataState.BROWSE);
		jTextField8.setEditable(dataState != DataState.BROWSE);
		jTextField9.setEditable(dataState != DataState.BROWSE);
		tfExpContractNo.setEditable(dataState != DataState.BROWSE);
		tfImpContractNo.setEditable(dataState != DataState.BROWSE);
		jTextField10.setEditable(dataState != DataState.BROWSE);
		tfImgAmount.setEditable(dataState != DataState.BROWSE);
		tfExgAmount.setEditable(dataState != DataState.BROWSE);
		jComboBox9.setEnabled(dataState != DataState.BROWSE);
		jComboBox11.setEnabled(dataState != DataState.BROWSE);
		tfWardshipFee.setEditable(dataState != DataState.BROWSE);
		cbbWardshipRate.setEnabled(dataState != DataState.BROWSE);
		cbbReceiveArea.setEnabled(dataState != DataState.BROWSE);
		jTextField16.setEditable(dataState != DataState.BROWSE);
		jCalendarComboBox4.setEnabled(dataState != DataState.BROWSE);
		jComboBox16.setEnabled(dataState != DataState.BROWSE);
		jComboBox17.setEnabled(dataState != DataState.BROWSE);
		jTextField17.setEditable(dataState != DataState.BROWSE);
		tfEmsApprNo.setEditable(dataState != DataState.BROWSE);
		cbbLimitFlag.setEnabled(dataState != DataState.BROWSE);
		calendarSaveDate.setEnabled(dataState!=DataState.BROWSE);
		this.cbbManageObject.setEnabled(dataState != DataState.BROWSE);
		int imgCount = dzscAction.findEmsPorImgWjCount(new Request(CommonVars
				.getCurrUser()), head);
		int exgCount = dzscAction.findEmsPorExgWjCount(new Request(CommonVars
				.getCurrUser()), head);
		this.rbComplex.setEnabled(dataState != DataState.BROWSE
				&& DzscState.ORIGINAL.equals(getDeclareState())
				&& imgCount == 0 && exgCount == 0);
		this.rbMaterial.setEnabled(dataState != DataState.BROWSE
				&& DzscState.ORIGINAL.equals(getDeclareState())
				&& imgCount == 0 && exgCount == 0);
		//
		// 料件总表
		//		
		btnAddImg.setEnabled(DzscState.ORIGINAL.equals(getDeclareState())
				|| DzscState.CHANGE.equals(getDeclareState()));
		btnDeleteImg.setEnabled(DzscState.ORIGINAL.equals(getDeclareState())
				|| DzscState.CHANGE.equals(getDeclareState()));
		btnEditImg.setEnabled(DzscState.ORIGINAL.equals(getDeclareState())
				|| DzscState.CHANGE.equals(getDeclareState()));
		//
		// 成品总表
		//	
		jButton21.setEnabled(DzscState.ORIGINAL.equals(getDeclareState())
				|| DzscState.CHANGE.equals(getDeclareState()));
		jButton23.setEnabled(DzscState.ORIGINAL.equals(getDeclareState())
				|| DzscState.CHANGE.equals(getDeclareState()));
		btnEditExg.setEnabled(DzscState.ORIGINAL.equals(getDeclareState())
				|| DzscState.CHANGE.equals(getDeclareState()));

		this.getMiUndoDeclare().setEnabled(
				DzscState.APPLY.equals(getDeclareState()));
		this.btnChangeExgModifyMark.setEnabled(DzscState.ORIGINAL
				.equals(getDeclareState())
				|| DzscState.CHANGE.equals(getDeclareState()));
		this.btnChangeImgModifyMark.setEnabled(DzscState.ORIGINAL
				.equals(getDeclareState())
				|| DzscState.CHANGE.equals(getDeclareState()));
	}

	/**
	 * This method initializes jToolBar6
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar6() {
		if (jToolBar6 == null) {
			jLabel44 = new JLabel();
			jToolBar6 = new JToolBar();
			jToolBar6.setFloatable(false);
			jLabel44.setText(" 合同料件汇总表     ");
			jLabel44.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN,
					14));
			jLabel44.setForeground(new java.awt.Color(0, 51, 255));
			jToolBar6.add(jLabel44);
			jToolBar6.add(getBtnAddImg());
			// jToolBar6.add(getJButton18());
			jToolBar6.add(getBtnEditImg());
			jToolBar6.add(getBtnDeleteImg());
			jToolBar6.add(getBtnChangeImgModifyMark());
		}
		return jToolBar6;
	}

	/**
	 * This method initializes jButton17
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAddImg() {
		if (btnAddImg == null) {
			btnAddImg = new JButton();
			btnAddImg.setText("增加料件");// 料件清单(来自十码)
			btnAddImg.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Integer dzscManageType = null;
					if (head.getDzscManageType() != null) {
						dzscManageType = head.getDzscManageType();
					}
					// else {
					// dzscManageType = DzscCommon.getInstance()
					// .getDzscManageType();
					// }
					if (dzscManageType != null
							&& dzscManageType == DzscManageType.MATERIAL) {// dzscManageType
						// == null
						// ||
						List list = DzscClientHelper.getInstance()
								.getDzscImgWjFromMergerImg(head);
						if (list == null || list.size() <= 0) {
							return;
						}
						list = dzscAction.saveWjImg(new Request(CommonVars
								.getCurrUser()), list, head);
						tableModelImg.addRows(list);
					} else {
						List list = CommonQueryPage.getInstance().getComplex();
						if (list == null || list.size() <= 0) {
							return;
						}
						list = dzscAction.saveWjImgFromComplex(new Request(
								CommonVars.getCurrUser()), list, head);
						tableModelImg.addRows(list);
					}

				}
			});
		}
		return btnAddImg;
	}

	/**
	 * This method initializes jButton19
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDeleteImg() {
		if (btnDeleteImg == null) {
			btnDeleteImg = new JButton();
			btnDeleteImg.setText("删除料件");// 清单
			btnDeleteImg.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelImg.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgDzscEmsPorWj.this,
								"请选择要删除的料件资料!", "提示", 2);
						return;
					}
					if (JOptionPane.showConfirmDialog(DgDzscEmsPorWj.this,
							"确定要删除吗?", "提示", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
						return;
					}
					List list = tableModelImg.getCurrentRows();
					for (int i = 0; i < list.size(); i++) {
						DzscEmsImgWj img = (DzscEmsImgWj) list.get(i);
						if (ModifyMarkState.ADDED.equals(img.getModifyMark())) {
							dzscAction.deleteDzscEmsImgWj(new Request(
									CommonVars.getCurrUser()), img);
							tableModelImg.deleteRow(img);
						} else if (ModifyMarkState.UNCHANGE.equals(img
								.getModifyMark())) {
							img = dzscAction.markDeleteDzscEmsImgWj(
									new Request(CommonVars.getCurrUser()), img);
							tableModelImg.updateRow(img);
						}
					}
				}
			});
		}
		return btnDeleteImg;
	}

	/**
	 * This method initializes jTable2
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbImg() {
		if (tbImg == null) {
			tbImg = new JTable();
		}
		return tbImg;
	}

	/**
	 * This method initializes jScrollPane2
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setViewportView(getTbImg());
		}
		return jScrollPane2;
	}

	/**
	 * This method initializes jPanel7
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel7() {
		if (jPanel7 == null) {
			jPanel7 = new JPanel();
			jPanel7.setLayout(new BorderLayout());
			jPanel7.add(getJToolBar7(), java.awt.BorderLayout.NORTH);
			jPanel7.add(getJScrollPane3(), java.awt.BorderLayout.CENTER);
		}
		return jPanel7;
	}

	/**
	 * This method initializes jToolBar7
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar7() {
		if (jToolBar7 == null) {
			jLabel45 = new JLabel();
			jToolBar7 = new JToolBar();
			jToolBar7.setFloatable(false);
			jLabel45.setText("  出口成品表   ");
			jLabel45.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN,
					14));
			jLabel45.setForeground(java.awt.Color.blue);
			jToolBar7.add(jLabel45);
			jToolBar7.add(getJButton21());
			// jToolBar7.add(getJButton22());
			jToolBar7.add(getBtnEditExg());
			jToolBar7.add(getJButton23());
			jToolBar7.add(getBtnChangeExgModifyMark());
		}
		return jToolBar7;
	}

	/**
	 * This method initializes jButton21
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton21() {
		if (jButton21 == null) {
			jButton21 = new JButton();
			jButton21.setText("增加成品");
			jButton21.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Integer dzscManageType = null;
					if (head.getDzscManageType() != null) {
						dzscManageType = head.getDzscManageType();
					}
					// else {
					// dzscManageType = DzscCommon.getInstance()
					// .getDzscManageType();
					// }
					if (dzscManageType != null
							&& dzscManageType == DzscManageType.MATERIAL) {
						List list = DzscClientHelper.getInstance()
								.getDzscExgWjFromMergerExg(head);
						if (list == null || list.size() <= 0) {
							return;
						}
						list = dzscAction.saveWjExg(new Request(CommonVars
								.getCurrUser()), list, head);
						tableModelExg.addRows(list);
					} else {
						List list = CommonQueryPage.getInstance().getComplex();
						if (list == null || list.size() <= 0) {
							return;
						}
						list = dzscAction.saveWjExgFromComplex(new Request(
								CommonVars.getCurrUser()), list, head);
						tableModelExg.addRows(list);
					}
				}
			});
		}
		return jButton21;
	}

	/**
	 * This method initializes jButton23
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton23() {
		if (jButton23 == null) {
			jButton23 = new JButton();
			jButton23.setText("删除成品");
			jButton23.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelExg.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgDzscEmsPorWj.this,
								"请选中要删除的成品!", "提示", 2);
						return;
					}

					if (JOptionPane.showConfirmDialog(DgDzscEmsPorWj.this,
							"确定要删除吗?", "提示", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
						return;
					}
					List list = tableModelExg.getCurrentRows();
					for (int i = 0; i < list.size(); i++) {
						DzscEmsExgWj exg = (DzscEmsExgWj) list.get(i);
						if (ModifyMarkState.ADDED.equals(exg.getModifyMark())) {
							dzscAction.deleteDzscEmsExgWj(new Request(
									CommonVars.getCurrUser()), exg);
							tableModelExg.deleteRow(exg);
						} else if (ModifyMarkState.UNCHANGE.equals(exg
								.getModifyMark())) {
							exg = dzscAction.markDeleteDzscEmsExgWj(
									new Request(CommonVars.getCurrUser()), exg);
							tableModelExg.updateRow(exg);
						}
					}

				}
			});
		}
		return jButton23;
	}

	/**
	 * This method initializes jTable3
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbExg() {
		if (tbExg == null) {
			tbExg = new JTable();
		}
		return tbExg;
	}

	/**
	 * This method initializes jScrollPane3
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane3() {
		if (jScrollPane3 == null) {
			jScrollPane3 = new JScrollPane();
			jScrollPane3.setViewportView(getTbExg());
		}
		return jScrollPane3;
	}

	/**
	 * This method initializes btnUndo
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnUndo() {
		if (btnUndo == null) {
			btnUndo = new JButton();
			btnUndo.setText("放弃");
			btnUndo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					showHeadData();
					dataState = DataState.BROWSE;
					setState();
				}
			});
		}
		return btnUndo;
	}

	/**
	 * This method initializes emsNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfEmsNo() {
		if (tfEmsNo == null) {
			tfEmsNo = new JTextField();
			tfEmsNo.setEnabled(true);
			tfEmsNo.setEditable(false);
			tfEmsNo.setBounds(new Rectangle(113, 403, 127, 24));
		}
		return tfEmsNo;
	}

	/**
	 * 设置监管费
	 * 
	 */
	private void setWardshipFee() {
		String amountStr = "0";
		try {
			double wardship = cbbWardshipRate.getSelectedItem() != null ? Double
					.valueOf(cbbWardshipRate.getSelectedItem().toString())
					: 0.0;
			double imgAmount = Double.valueOf((tfImgAmount.getValue()
					.toString()));

			BigDecimal bd = new BigDecimal(wardship * imgAmount);
			amountStr = bd.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
			tfWardshipFee.setValue(Double.valueOf(amountStr));
		} catch (Exception ex) {
		}

	}

	/**
	 * 套打成品表
	 */
	private void printConverPrintExg() {
		if (head == null) {
			JOptionPane.showMessageDialog(this, "请先保存合同记录!!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		// int flag = this.cbbPrint.getSelectedIndex();
		String emsNo = "";
		try {
			List list = new ArrayList();
			if (head != null) {
				list = dzscAction.findEmsPorExgWj(new Request(CommonVars
						.getCurrUser()), head);
				// emsNo = head.getImContractNo() == null ? "" : head
				// .getImContractNo();
				emsNo = head.getEmsApprNo() == null ? "" : head.getEmsApprNo();
			}
			InputStream subReportStream = DgDzscEmsPorWj.class
					.getResourceAsStream("report/FinishedProductSubReport.jasper");
			JasperReport subReport = (JasperReport) JRLoader
					.loadObject(subReportStream);
			CustomReportDataSource ds = new CustomReportDataSource(list);
			// List<Double> currRateList = this.getHDKAndUSDRate();
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("isOverprint", new Boolean(true));
			parameters.put("list", list);
			parameters.put("emsNo", emsNo);
			// parameters.put("rateHDK", currRateList.get(0));
			// parameters.put("rateUSD", currRateList.get(1));
			parameters.put("companyName", head.getMachName());
			// parameters.put("currName", head.getCurr() == null ? "" : head
			// .getCurr().getName());
			parameters.put("subReport", subReport);
			parameters.put("subReportDataSource", ds);
			InputStream masterReportStream = DgDzscEmsPorWj.class
					.getResourceAsStream("report/FinishedProductReport.jasper");
			List<String> tempList = new ArrayList<String>();
			tempList.add("tempData");
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					masterReportStream, parameters, new CustomReportDataSource(
							tempList));
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 非套打成品表
	 */
	private void printNonConverPrintExg() {
		if (head == null) {
			JOptionPane.showMessageDialog(this, "请先保存合同记录!!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		// int flag = this.cbbPrint.getSelectedIndex();
		String emsNo = "";
		try {
			List list = new ArrayList();
			if (head != null) {
				list = dzscAction.findEmsPorExgWj(new Request(CommonVars
						.getCurrUser()), head);
				// emsNo = head.getImContractNo() == null ? "" : head
				// .getImContractNo();
				emsNo = head.getEmsApprNo() == null ? "" : head.getEmsApprNo();
			}
			InputStream subReportStream = DgDzscEmsPorWj.class
					.getResourceAsStream("report/FinishedProductSubReport.jasper");
			JasperReport subReport = (JasperReport) JRLoader
					.loadObject(subReportStream);
			CustomReportDataSource ds = new CustomReportDataSource(list);
			// List<Double> currRateList = this.getHDKAndUSDRate();
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("isOverprint", new Boolean(false));
			parameters.put("list", list);
			parameters.put("emsNo", emsNo);
			parameters.put("companyName", head.getMachName());
			// parameters.put("rateHDK", currRateList.get(0));
			// parameters.put("rateUSD", currRateList.get(1));
			// parameters.put("currName", head.getCurr() == null ? "" : head
			// .getCurr().getName());
			parameters.put("subReport", subReport);
			parameters.put("subReportDataSource", ds);
			InputStream masterReportStream = DgDzscEmsPorWj.class
					.getResourceAsStream("report/FinishedProductReport.jasper");
			List<String> tempList = new ArrayList<String>();
			tempList.add("tempData");
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					masterReportStream, parameters, new CustomReportDataSource(
							tempList));
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 套打料件表
	 */
	private void printConverPrintImg() {
		if (head == null) {
			JOptionPane.showMessageDialog(this, "请先保存合同记录!!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		// int flag = this.cbbPrint.getSelectedIndex();
		String emsNo = "";
		try {
			List list = new ArrayList();
			if (head != null) {
				list = dzscAction.findEmsPorImgWj(new Request(CommonVars
						.getCurrUser()), head);
				// emsNo = head.getIeContractNo() == null ? "" : head
				// .getIeContractNo();
				emsNo = head.getEmsApprNo() == null ? "" : head.getEmsApprNo();
			}
			InputStream subReportStream = DgDzscEmsPorWj.class
					.getResourceAsStream("report/MaterielSubReport.jasper");
			JasperReport subReport = (JasperReport) JRLoader
					.loadObject(subReportStream);
			CustomReportDataSource ds = new CustomReportDataSource(list);
			// List<Double> currRateList = this.getHDKAndUSDRate();
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("isOverprint", new Boolean(true));
			parameters.put("list", list);
			parameters.put("emsNo", emsNo);
			// parameters.put("rateHDK", currRateList.get(0));
			// parameters.put("rateUSD", currRateList.get(1));
			parameters.put("companyName", head.getMachName());
			// parameters.put("currName", head.getCurr() == null ? "" : head
			// .getCurr().getName());
			parameters.put("subReport", subReport);
			parameters.put("subReportDataSource", ds);
			InputStream masterReportStream = DgDzscEmsPorWj.class
					.getResourceAsStream("report/MaterielReport.jasper");
			List<String> tempList = new ArrayList<String>();
			tempList.add("tempData");
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					masterReportStream, parameters, new CustomReportDataSource(
							tempList));
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 非套打料件表
	 */
	private void printNonConverPrintImg() {
		if (head == null) {
			JOptionPane.showMessageDialog(this, "请先保存合同记录!!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		// int flag = this.cbbPrint.getSelectedIndex();
		String emsNo = "";
		try {
			List list = new ArrayList();
			if (head != null) {
				list = dzscAction.findEmsPorImgWj(new Request(CommonVars
						.getCurrUser()), head);
				// emsNo = head.getIeContractNo() == null ? "" : head
				// .getIeContractNo();
				emsNo = head.getEmsApprNo() == null ? "" : head.getEmsApprNo();
			}
			InputStream subReportStream = DgDzscEmsPorWj.class
					.getResourceAsStream("report/MaterielSubReport.jasper");
			JasperReport subReport = (JasperReport) JRLoader
					.loadObject(subReportStream);
			CustomReportDataSource ds = new CustomReportDataSource(list);
			// List<Double> currRateList = this.getHDKAndUSDRate();
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("isOverprint", new Boolean(false));
			parameters.put("list", list);
			parameters.put("emsNo", emsNo);
			// parameters.put("rateHDK", currRateList.get(0));
			// parameters.put("rateUSD", currRateList.get(1));
			parameters.put("companyName", head.getMachName());
			// parameters.put("currName", head.getCurr() == null ? "" : head
			// .getCurr().getName());
			parameters.put("subReport", subReport);
			parameters.put("subReportDataSource", ds);
			InputStream masterReportStream = DgDzscEmsPorWj.class
					.getResourceAsStream("report/MaterielReport.jasper");
			List<String> tempList = new ArrayList<String>();
			tempList.add("tempData");
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					masterReportStream, parameters, new CustomReportDataSource(
							tempList));
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 出口成品备案清单变更表
	 */
	private void printExportRecordationChecklistChange() {
		if (head == null) {
			JOptionPane.showMessageDialog(this, "请先保存合同记录!!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		// int flag = this.cbbPrint.getSelectedIndex();
		String emsNo = "";
		try {
			List list = new ArrayList();
			if (head != null) {
				list = dzscAction.findEmsPorExgWj(new Request(CommonVars
						.getCurrUser()), head);
				emsNo = head.getImContractNo() == null ? "" : head
						.getImContractNo();
			}
			DgDzscPrint dg = new DgDzscPrint();
			dg.setEmsWJhead(head);
			dg.setFlag(DgDzscPrint.EMS_PRODUCT_BILL_CHANGE);
			dg.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 进口成品备案清单变更表
	 */
	private void printImportRecordationChecklistChange() {
		if (head == null) {
			JOptionPane.showMessageDialog(this, "请先保存合同记录!!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		// int flag = this.cbbPrint.getSelectedIndex();
		String emsNo = "";
		try {
			List list = new ArrayList();
			if (head != null) {
				list = dzscAction.findEmsPorExgWj(new Request(CommonVars
						.getCurrUser()), head);
				emsNo = head.getImContractNo() == null ? "" : head
						.getImContractNo();
			}
			DgDzscPrint dg = new DgDzscPrint();
			dg.setEmsWJhead(head);
			dg.setFlag(DgDzscPrint.EMS_MATERIEL_BILL_CHANGE);
			dg.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 打印电子手册合同备案表
	 */
	private void printDZSCContractFile() {
		if (head == null) {
			JOptionPane.showMessageDialog(this, "请先保存合同记录!!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		try {
			InputStream masterReportStream = null;
			List<Object> list = new ArrayList<Object>();
			list.add("");
			Map<String, Object> parameters = new HashMap<String, Object>();
			CustomReportDataSource ds = new CustomReportDataSource(list);
			masterReportStream = DgDzscEmsPorWj.class
					.getResourceAsStream("report/DZSCContractFile.jasper");
			parameters.put("copTrNo", getTfCopTrNo().getText());
			parameters.put("redDep",
					((RedDep) getCbbRedDep().getSelectedItem()).getName());
			parameters.put("companyName", getJTextField3().getText());
			parameters.put("companyCode", getJTextField4().getText());
			parameters.put("buName", getTfTradeName().getText());
			parameters.put("tradeCode", getTfTradeCode().getText());
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			parameters.put("declareDate", head.getDeclareDate() == null ? ""
					: df.format(head.getDeclareDate()));
			Date d = getCbbAvailabilityDate().getDate();
			parameters.put("amountProduct", ((Company) head.getCompany())
					.getAmountProduct() == null ? "0.0" : ((Company) head
					.getCompany()).getAmountProduct().toString());
			parameters.put("endDate", head.getEndDate() == null ? "" : df
					.format(d));
			parameters.put("emsApprNo", getTfEmsApprNo().getText());
			parameters.put("customName", ((Customs) getCbbDeclareCustoms()
					.getSelectedItem()).getName());
			parameters.put("sancEmsNo", getJTextField9().getText());
			parameters.put("note", getJTextField17().getText());
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					masterReportStream, parameters, ds);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 报表打印
	 */
	/*
	 * private void printReport() { if (head == null) {
	 * JOptionPane.showMessageDialog(this, "请先保存合同记录!!!", "提示",
	 * JOptionPane.INFORMATION_MESSAGE); return; } this.setCursor(new
	 * Cursor(Cursor.WAIT_CURSOR)); //int flag =
	 * this.cbbPrint.getSelectedIndex(); String emsNo = ""; //switch (flag) {
	 * case 0: // 套打成品表 try { List list = new ArrayList(); if (head != null) {
	 * list = dzscAction.findEmsPorExgWj(new Request(CommonVars .getCurrUser()),
	 * head); emsNo = head.getImContractNo() == null ? "" : head
	 * .getImContractNo(); } InputStream subReportStream = DgDzscEmsPorWj.class
	 * .getResourceAsStream("report/FinishedProductSubReport.jasper");
	 * JasperReport subReport = (JasperReport) JRLoader
	 * .loadObject(subReportStream); CustomReportDataSource ds = new
	 * CustomReportDataSource(list); // List<Double> currRateList =
	 * this.getHDKAndUSDRate(); Map<String, Object> parameters = new
	 * HashMap<String, Object>(); parameters.put("isOverprint", new
	 * Boolean(true)); parameters.put("list", list); parameters.put("emsNo",
	 * emsNo); // parameters.put("rateHDK", currRateList.get(0)); //
	 * parameters.put("rateUSD", currRateList.get(1));
	 * parameters.put("companyName", head.getMachName()); //
	 * parameters.put("currName", head.getCurr() == null ? "" : head //
	 * .getCurr().getName()); parameters.put("subReport", subReport);
	 * parameters.put("subReportDataSource", ds); InputStream masterReportStream
	 * = DgDzscEmsPorWj.class
	 * .getResourceAsStream("report/FinishedProductReport.jasper"); List<String>
	 * tempList = new ArrayList<String>(); tempList.add("tempData"); JasperPrint
	 * jasperPrint = JasperFillManager.fillReport( masterReportStream,
	 * parameters, new CustomReportDataSource(tempList)); DgReportViewer viewer
	 * = new DgReportViewer(jasperPrint); viewer.setVisible(true); } catch
	 * (Exception ex) { ex.printStackTrace(); } break; case 1: // 非套打成品表 try {
	 * List list = new ArrayList(); if (head != null) { list =
	 * dzscAction.findEmsPorExgWj(new Request(CommonVars .getCurrUser()), head);
	 * emsNo = head.getImContractNo() == null ? "" : head .getImContractNo(); }
	 * InputStream subReportStream = DgDzscEmsPorWj.class
	 * .getResourceAsStream("report/FinishedProductSubReport.jasper");
	 * JasperReport subReport = (JasperReport) JRLoader
	 * .loadObject(subReportStream); CustomReportDataSource ds = new
	 * CustomReportDataSource(list); // List<Double> currRateList =
	 * this.getHDKAndUSDRate(); Map<String, Object> parameters = new
	 * HashMap<String, Object>(); parameters.put("isOverprint", new
	 * Boolean(false)); parameters.put("list", list); parameters.put("emsNo",
	 * emsNo); parameters.put("companyName", head.getMachName()); //
	 * parameters.put("rateHDK", currRateList.get(0)); //
	 * parameters.put("rateUSD", currRateList.get(1)); //
	 * parameters.put("currName", head.getCurr() == null ? "" : head //
	 * .getCurr().getName()); parameters.put("subReport", subReport);
	 * parameters.put("subReportDataSource", ds); InputStream masterReportStream
	 * = DgDzscEmsPorWj.class
	 * .getResourceAsStream("report/FinishedProductReport.jasper"); List<String>
	 * tempList = new ArrayList<String>(); tempList.add("tempData"); JasperPrint
	 * jasperPrint = JasperFillManager.fillReport( masterReportStream,
	 * parameters, new CustomReportDataSource(tempList)); DgReportViewer viewer
	 * = new DgReportViewer(jasperPrint); viewer.setVisible(true); } catch
	 * (Exception ex) { ex.printStackTrace(); } break; case 2: // 套打料件表 try {
	 * List list = new ArrayList(); if (head != null) { list =
	 * dzscAction.findEmsPorImgWj(new Request(CommonVars .getCurrUser()), head);
	 * emsNo = head.getIeContractNo() == null ? "" : head .getIeContractNo(); }
	 * InputStream subReportStream = DgDzscEmsPorWj.class
	 * .getResourceAsStream("report/MaterielSubReport.jasper"); JasperReport
	 * subReport = (JasperReport) JRLoader .loadObject(subReportStream);
	 * CustomReportDataSource ds = new CustomReportDataSource(list); //
	 * List<Double> currRateList = this.getHDKAndUSDRate(); Map<String, Object>
	 * parameters = new HashMap<String, Object>(); parameters.put("isOverprint",
	 * new Boolean(true)); parameters.put("list", list); parameters.put("emsNo",
	 * emsNo); // parameters.put("rateHDK", currRateList.get(0)); //
	 * parameters.put("rateUSD", currRateList.get(1));
	 * parameters.put("companyName", head.getMachName()); //
	 * parameters.put("currName", head.getCurr() == null ? "" : head //
	 * .getCurr().getName()); parameters.put("subReport", subReport);
	 * parameters.put("subReportDataSource", ds); InputStream masterReportStream
	 * = DgDzscEmsPorWj.class
	 * .getResourceAsStream("report/MaterielReport.jasper"); List<String>
	 * tempList = new ArrayList<String>(); tempList.add("tempData"); JasperPrint
	 * jasperPrint = JasperFillManager.fillReport( masterReportStream,
	 * parameters, new CustomReportDataSource(tempList)); DgReportViewer viewer
	 * = new DgReportViewer(jasperPrint); viewer.setVisible(true); } catch
	 * (Exception ex) { ex.printStackTrace(); } break; case 3: // 非套打料件表 try {
	 * List list = new ArrayList(); if (head != null) { list =
	 * dzscAction.findEmsPorImgWj(new Request(CommonVars .getCurrUser()), head);
	 * emsNo = head.getIeContractNo() == null ? "" : head .getIeContractNo(); }
	 * InputStream subReportStream = DgDzscEmsPorWj.class
	 * .getResourceAsStream("report/MaterielSubReport.jasper"); JasperReport
	 * subReport = (JasperReport) JRLoader .loadObject(subReportStream);
	 * CustomReportDataSource ds = new CustomReportDataSource(list); //
	 * List<Double> currRateList = this.getHDKAndUSDRate(); Map<String, Object>
	 * parameters = new HashMap<String, Object>(); parameters.put("isOverprint",
	 * new Boolean(false)); parameters.put("list", list);
	 * parameters.put("emsNo", emsNo); // parameters.put("rateHDK",
	 * currRateList.get(0)); // parameters.put("rateUSD", currRateList.get(1));
	 * parameters.put("companyName", head.getMachName()); //
	 * parameters.put("currName", head.getCurr() == null ? "" : head //
	 * .getCurr().getName()); parameters.put("subReport", subReport);
	 * parameters.put("subReportDataSource", ds); InputStream masterReportStream
	 * = DgDzscEmsPorWj.class
	 * .getResourceAsStream("report/MaterielReport.jasper"); List<String>
	 * tempList = new ArrayList<String>(); tempList.add("tempData"); JasperPrint
	 * jasperPrint = JasperFillManager.fillReport( masterReportStream,
	 * parameters, new CustomReportDataSource(tempList)); DgReportViewer viewer
	 * = new DgReportViewer(jasperPrint); viewer.setVisible(true); } catch
	 * (Exception ex) { ex.printStackTrace(); } break; case 4: // 出口成品备案清单变更表
	 * try { List list = new ArrayList(); if (head != null) { list =
	 * dzscAction.findEmsPorExgWj(new Request(CommonVars .getCurrUser()), head);
	 * emsNo = head.getImContractNo() == null ? "" : head .getImContractNo(); }
	 * DgDzscPrint dg = new DgDzscPrint(); dg.setEmsWJhead(head);
	 * dg.setFlag(DgDzscPrint.EMS_PRODUCT_BILL_CHANGE); dg.setVisible(true);
	 * break; } catch (Exception ex) { ex.printStackTrace(); } case 5: //
	 * 进口料件备案清单变更表 try { List list = new ArrayList(); if (head != null) { list =
	 * dzscAction.findEmsPorExgWj(new Request(CommonVars .getCurrUser()), head);
	 * emsNo = head.getImContractNo() == null ? "" : head .getImContractNo(); }
	 * DgDzscPrint dg = new DgDzscPrint(); dg.setEmsWJhead(head);
	 * dg.setFlag(DgDzscPrint.EMS_MATERIEL_BILL_CHANGE); dg.setVisible(true);
	 * break; } catch (Exception ex) { ex.printStackTrace(); }
	 * 
	 * // case 4: // 套打单耗表 // try { // // List list = new ArrayList(); // // int
	 * count = 0; // // if (head != null) { // // String parentId =
	 * head.getId(); // // list = dzscAction.findContractUnitWasteByWJ(new
	 * Request( // // CommonVars.getCurrUser()), parentId, -1, -1); // // count
	 * = this.dzscAction.findDzscEmsExgWjCount(new Request( // //
	 * CommonVars.getCurrUser()), head.getId()); // // emsNo =
	 * head.getImContractNo() == null ? "" : head // // .getImContractNo(); //
	 * // } // // Map<String, Object> parameters = new HashMap<String, //
	 * Object>(); // // parameters.put("emsNo", emsNo); // //
	 * parameters.put("isOverprint", new Boolean(true)); // //
	 * parameters.put("companyName", head.getMachName()); // //
	 * parameters.put("count", count); // // CustomReportDataSource ds = new
	 * CustomReportDataSource(list); // // InputStream masterReportStream =
	 * DgContract.class // //
	 * .getResourceAsStream("report/MaterielUnitWasteReport.jasper"); // //
	 * JasperPrint jasperPrint = JasperFillManager.fillReport( // //
	 * masterReportStream, parameters, ds); // // DgReportViewer viewer = new
	 * DgReportViewer(jasperPrint); // // viewer.setVisible(true); // } catch
	 * (Exception ex) { // ex.printStackTrace(); // } // break; // case 5: //
	 * 非套打单耗表 // try { // // List list = new ArrayList(); // // int count = 0;
	 * // // if (head != null) { // // String parentId = head.getId(); // //
	 * list = dzscAction.findContractUnitWasteByWJ(new Request( // //
	 * CommonVars.getCurrUser()), parentId, -1, -1); // // count =
	 * this.dzscAction.findDzscEmsExgWjCount(new Request( // //
	 * CommonVars.getCurrUser()), head.getId()); // // emsNo =
	 * head.getImContractNo() == null ? "" : head // // .getImContractNo(); //
	 * // } // // Map<String, Object> parameters = new HashMap<String, //
	 * Object>(); // // parameters.put("emsNo", emsNo); // //
	 * parameters.put("isOverprint", new Boolean(false)); // //
	 * parameters.put("companyName", head.getMachName()); // //
	 * parameters.put("count", count); // // CustomReportDataSource ds = new
	 * CustomReportDataSource(list); // // InputStream masterReportStream =
	 * DgContract.class // //
	 * .getResourceAsStream("report/MaterielUnitWasteReport.jasper"); // //
	 * JasperPrint jasperPrint = JasperFillManager.fillReport( // //
	 * masterReportStream, parameters, ds); // // DgReportViewer viewer = new
	 * DgReportViewer(jasperPrint); // // viewer.setVisible(true); // } catch
	 * (Exception ex) { // ex.printStackTrace(); // } // // break;
	 * 
	 * } this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); }
	 */

	/**
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbManageObject() {
		if (cbbManageObject == null) {
			cbbManageObject = new JComboBox();
			cbbManageObject.setBounds(new Rectangle(544, 42, 127, 22));
		}
		return cbbManageObject;
	}

	private boolean saveHeadData() {
		head = this.dzscAction.findDzscEmsPorWjHeadById(new Request(CommonVars
				.getCurrUser(), true), head.getId());
		fillData(head);
		if (!tfEmsNo.getText().trim().equals("")
				&& !DzscState.CHANGE.equals(head.getDeclareState())) {
			if (checkEmsPorWjHeadDuple(head)) {
				JOptionPane.showMessageDialog(DgDzscEmsPorWj.this, "手册编号不能重复",
						"提示", JOptionPane.INFORMATION_MESSAGE);
				jTabbedPane.setSelectedIndex(0);
				return false;
			}
		}
		head = dzscAction.saveEmsPorWj(new Request(CommonVars.getCurrUser()),
				head,tfCopTrNoOld);
		if(tfCopTrNo.getText()!=null){
		tfCopTrNoOld=tfCopTrNo.getText();
		}
		tableModelHead.updateRow(head);
		dataState = DataState.BROWSE;
		setState();
		return true;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbReceiveArea() {
		if (cbbReceiveArea == null) {
			cbbReceiveArea = new JComboBox();
			cbbReceiveArea.setBounds(new Rectangle(113, 376, 127, 22));
		}
		return cbbReceiveArea;
	}

	/**
	 * This method initializes btnCustomSort
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEditImg() {
		if (btnEditImg == null) {
			btnEditImg = new JButton();
			btnEditImg.setText("修改料件");
			btnEditImg.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelImg.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgDzscEmsPorWj.this,
								"请选中要修改的料件!", "提示", 2);
						return;
					}
					DgDzscEmsPorWjImg dg = new DgDzscEmsPorWjImg();
					dg.setTableModel(tableModelImg);
					dg.setVisible(true);
				}
			});
		}
		return btnEditImg;
	}

	/**
	 * This method initializes btnCustomSort
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEditExg() {
		if (btnEditExg == null) {
			btnEditExg = new JButton();
			btnEditExg.setText("修改成品");
			btnEditExg.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelExg.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgDzscEmsPorWj.this,
								"请选中要修改的成品!", "提示", 2);
						return;
					}
					DgDzscEmsPorWjExg dg = new DgDzscEmsPorWjExg();
					dg.setTableModel(tableModelExg);
					dg.setVisible(true);
				}
			});
		}
		return btnEditExg;
	}

	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jPanel4 = new JPanel();
			jPanel4.setLayout(null);
			jPanel4.setBounds(new Rectangle(111, 432, 563, 30));
			jPanel4.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
			jPanel4.add(getRbMaterial(), null);
			jPanel4.add(getRbComplex(), null);
		}
		return jPanel4;
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbMaterial() {
		if (rbMaterial == null) {
			rbMaterial = new JRadioButton();
			rbMaterial.setBounds(new Rectangle(65, 5, 130, 21));
			rbMaterial.setText("以料号为管理单元");
		}
		return rbMaterial;
	}

	/**
	 * This method initializes jRadioButton1
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbComplex() {
		if (rbComplex == null) {
			rbComplex = new JRadioButton();
			rbComplex.setBounds(new Rectangle(300, 3, 150, 23));
			rbComplex.setText("以HS编码为管理单元");
		}
		return rbComplex;
	}

	/**
	 * This method initializes buttonGroup
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup() {
		if (buttonGroup == null) {
			buttonGroup = new ButtonGroup();
			buttonGroup.add(this.getRbMaterial());
			buttonGroup.add(this.getRbComplex());
		}
		return buttonGroup;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfTradeCode() {
		if (tfTradeCode == null) {
			tfTradeCode = new JTextField();
			tfTradeCode.setBounds(new Rectangle(114, 71, 126, 24));
			tfTradeCode.setEditable(false);
		}
		return tfTradeCode;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfTradeName() {
		if (tfTradeName == null) {
			tfTradeName = new JTextField();
			tfTradeName.setBounds(new Rectangle(325, 71, 345, 24));
			tfTradeName.setEditable(false);
		}
		return tfTradeName;
	}

	/**
	 * This method initializes cbbDeclareCustoms1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbRedDep() {
		if (cbbRedDep == null) {
			cbbRedDep = new JComboBox();
			cbbRedDep.setBounds(new Rectangle(325, 42, 127, 22));
		}
		return cbbRedDep;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbLimitFlag() {
		if (cbbLimitFlag == null) {
			cbbLimitFlag = new JComboBox();
			cbbLimitFlag.setBounds(new Rectangle(326, 375, 127, 22));
		}
		return cbbLimitFlag;
	}

	/**
	 * This method initializes btnChangeDeclareState
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnChangeDeclareState() {
		if (btnChangeDeclareState == null) {
			btnChangeDeclareState = new JButton();
			btnChangeDeclareState.setText("改变申报状态");
			btnChangeDeclareState
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgAllChangState dg = new DgAllChangState();
							dg.setVisible(true);
							if (!dg.isOk()) {
								return;
							}
							if (dg.isCheckBoxState()) {
								Component comp = (Component) e.getSource();
								getPmChangeDeclareState().show(comp, 0,
										comp.getHeight());
							}

						}
					});
		}
		return btnChangeDeclareState;
	}

	/**
	 * This method initializes pmChangeDeclareState
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getPmChangeDeclareState() {
		if (pmChangeDeclareState == null) {
			pmChangeDeclareState = new JPopupMenu();
			pmChangeDeclareState.add(getMiUndoDeclare());
		}
		return pmChangeDeclareState;
	}

	/**
	 * This method initializes miUndoDeclare
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiUndoDeclare() {
		if (miUndoDeclare == null) {
			miUndoDeclare = new JMenuItem();
			miUndoDeclare.setText("取消申报");
			miUndoDeclare
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							String declareState = "";
							if (head.getEmsNo() != null
									&& !"".equals(head.getEmsNo().trim())) {
								declareState = DzscState.CHANGE;
							} else {
								declareState = DzscState.ORIGINAL;
							}
							head = dzscAction.changeDictPorHeadDeclareState(
									new Request(CommonVars.getCurrUser()),
									head, declareState);
							setState();
							if (tableModelHead != null) {
								tableModelHead.updateRow(head);
							}
						}
					});
		}
		return miUndoDeclare;
	}

	/**
	 * This method initializes btnChangeImgModifyMark
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnChangeImgModifyMark() {
		if (btnChangeImgModifyMark == null) {
			btnChangeImgModifyMark = new JButton();
			btnChangeImgModifyMark.setToolTipText(ModifyMarkState
					.getToolTipText());
			btnChangeImgModifyMark
					.setText("\u6539\u53d8\u4fee\u6539\u6807\u5fd7");
			btnChangeImgModifyMark
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgAllChangState dg = new DgAllChangState();
							dg.setVisible(true);
							if (!dg.isOk()) {
								return;
							}
							if (dg.isCheckBoxState()) {
								Component comp = (Component) e.getSource();
								getPmChangeModifyMark().show(comp, 0,
										comp.getHeight());
							}
						}
					});
		}
		return btnChangeImgModifyMark;
	}

	/**
	 * This method initializes pmChangeModifyMark
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getPmChangeModifyMark() {
		if (pmChangeModifyMark == null) {
			pmChangeModifyMark = new JPopupMenu();
			pmChangeModifyMark.add(getMiNotModified());
			pmChangeModifyMark.add(getMiModified());
			pmChangeModifyMark.add(getMiDelete());
			pmChangeModifyMark.add(getMiAdd());
		}
		return pmChangeModifyMark;
	}

	/**
	 * This method initializes miNotModified
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiNotModified() {
		if (miNotModified == null) {
			miNotModified = new JMenuItem();
			miNotModified.setText("未修改");
			miNotModified
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (jTabbedPane.getSelectedIndex() == 1) {
								if (tableModelImg.getCurrentRow() == null) {
									JOptionPane.showMessageDialog(
											DgDzscEmsPorWj.this, "请选择料件", "提示",
											JOptionPane.INFORMATION_MESSAGE);
									return;
								}
								List list = tableModelImg.getCurrentRows();
								dzscAction.changeDictPorImgExgModifyMark(
										new Request(CommonVars.getCurrUser()),
										list, ModifyMarkState.UNCHANGE);
								tableModelImg = DzscClientLogic.initTableImgWj(
										tbImg, head);
							} else if (jTabbedPane.getSelectedIndex() == 2) {
								if (tableModelExg.getCurrentRow() == null) {
									JOptionPane.showMessageDialog(
											DgDzscEmsPorWj.this, "请选择成品", "提示",
											JOptionPane.INFORMATION_MESSAGE);
									return;
								}
								List list = tableModelExg.getCurrentRows();
								dzscAction.changeDictPorImgExgModifyMark(
										new Request(CommonVars.getCurrUser()),
										list, ModifyMarkState.UNCHANGE);
								tableModelExg = DzscClientLogic.initTableExgWj(
										tbExg, head);
							}
						}
					});
		}
		return miNotModified;
	}

	/**
	 * This method initializes miModified
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiModified() {
		if (miModified == null) {
			miModified = new JMenuItem();
			miModified.setText("已修改");
			miModified.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jTabbedPane.getSelectedIndex() == 1) {
						if (tableModelImg.getCurrentRow() == null) {
							JOptionPane.showMessageDialog(DgDzscEmsPorWj.this,
									"请选择料件", "提示",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						List list = tableModelImg.getCurrentRows();
						dzscAction.changeDictPorImgExgModifyMark(new Request(
								CommonVars.getCurrUser()), list,
								ModifyMarkState.MODIFIED);
						tableModelImg = DzscClientLogic.initTableImgWj(tbImg,
								head);
					} else if (jTabbedPane.getSelectedIndex() == 2) {
						if (tableModelExg.getCurrentRow() == null) {
							JOptionPane.showMessageDialog(DgDzscEmsPorWj.this,
									"请选择成品", "提示",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						List list = tableModelExg.getCurrentRows();
						dzscAction.changeDictPorImgExgModifyMark(new Request(
								CommonVars.getCurrUser()), list,
								ModifyMarkState.MODIFIED);
						tableModelExg = DzscClientLogic.initTableExgWj(tbExg,
								head);
					}
				}
			});
		}
		return miModified;
	}

	/**
	 * This method initializes jMenuItem2
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiDelete() {
		if (miDelete == null) {
			miDelete = new JMenuItem();
			miDelete.setText("已删除");
			miDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jTabbedPane.getSelectedIndex() == 1) {
						if (tableModelImg.getCurrentRow() == null) {
							JOptionPane.showMessageDialog(DgDzscEmsPorWj.this,
									"请选择料件", "提示",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						List list = tableModelImg.getCurrentRows();
						dzscAction.changeDictPorImgExgModifyMark(new Request(
								CommonVars.getCurrUser()), list,
								ModifyMarkState.DELETED);
						tableModelImg = DzscClientLogic.initTableImgWj(tbImg,
								head);
					} else if (jTabbedPane.getSelectedIndex() == 2) {
						if (tableModelExg.getCurrentRow() == null) {
							JOptionPane.showMessageDialog(DgDzscEmsPorWj.this,
									"请选择成品", "提示",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						List list = tableModelExg.getCurrentRows();
						dzscAction.changeDictPorImgExgModifyMark(new Request(
								CommonVars.getCurrUser()), list,
								ModifyMarkState.DELETED);
						tableModelExg = DzscClientLogic.initTableExgWj(tbExg,
								head);
					}
				}
			});
		}
		return miDelete;
	}

	private JMenuItem getMiAdd() {
		if (miAdd == null) {
			miAdd = new JMenuItem();
			miAdd.setText("新 增");
			miAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jTabbedPane.getSelectedIndex() == 1) {
						if (tableModelImg.getCurrentRow() == null) {
							JOptionPane.showMessageDialog(DgDzscEmsPorWj.this,
									"请选择料件", "提示",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						List list = tableModelImg.getCurrentRows();
						dzscAction.changeDictPorImgExgModifyMark(new Request(
								CommonVars.getCurrUser()), list,
								ModifyMarkState.ADDED);
						tableModelImg = DzscClientLogic.initTableImgWj(tbImg,
								head);
					} else if (jTabbedPane.getSelectedIndex() == 2) {
						if (tableModelExg.getCurrentRow() == null) {
							JOptionPane.showMessageDialog(DgDzscEmsPorWj.this,
									"请选择成品", "提示",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						List list = tableModelExg.getCurrentRows();
						dzscAction.changeDictPorImgExgModifyMark(new Request(
								CommonVars.getCurrUser()), list,
								ModifyMarkState.ADDED);
						tableModelExg = DzscClientLogic.initTableExgWj(tbExg,
								head);
					}
				}
			});
		}
		return miAdd;
	}

	/**
	 * This method initializes btnChangeExgModifyMark
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnChangeExgModifyMark() {
		if (btnChangeExgModifyMark == null) {
			btnChangeExgModifyMark = new JButton();
			btnChangeExgModifyMark.setToolTipText(ModifyMarkState
					.getToolTipText());
			btnChangeExgModifyMark
					.setText("\u6539\u53d8\u4fee\u6539\u6807\u5fd7");
			btnChangeExgModifyMark
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgAllChangState dg = new DgAllChangState();
							dg.setVisible(true);
							if (!dg.isOk()) {
								return;
							}
							if (dg.isCheckBoxState()) {
								Component comp = (Component) e.getSource();
								getPmChangeModifyMark().show(comp, 0,
										comp.getHeight());
							}

						}
					});
		}
		return btnChangeExgModifyMark;
	}

	/**
	 * This method initializes jPopupMenuPrintReport
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getJPopupMenuPrintReport() {
		if (jPopupMenuPrintReport == null) {
			jPopupMenuPrintReport = new JPopupMenu();
			jPopupMenuPrintReport.add(getMiCoverPrintExg());
			jPopupMenuPrintReport.add(getMiNonCoverPrintExg());
			jPopupMenuPrintReport.add(getMiConverPrintImg());
			jPopupMenuPrintReport.add(getMiNonCoverPrintImg());
			jPopupMenuPrintReport.add(getMiNonDZSCContractFile());
			jPopupMenuPrintReport
					.add(getMiExportExgRecordationChecklistChange());
			jPopupMenuPrintReport
					.add(getMiImportExgRecordationChecklistChange());
		}
		return jPopupMenuPrintReport;
	}

	/**
	 * This method initializes miCoverPrintExg
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiCoverPrintExg() {
		if (miCoverPrintExg == null) {
			miCoverPrintExg = new JMenuItem();
			miCoverPrintExg.setText("套打成品表");
			miCoverPrintExg.setSize(new Dimension(100, 30));
			miCoverPrintExg
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							printConverPrintExg();
						}
					});
		}
		return miCoverPrintExg;
	}

	/**
	 * This method initializes miNonCoverPrintExg
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiNonCoverPrintExg() {
		if (miNonCoverPrintExg == null) {
			miNonCoverPrintExg = new JMenuItem();
			miNonCoverPrintExg.setText("非套打成品表");
			miNonCoverPrintExg.setSize(new Dimension(100, 30));
			miNonCoverPrintExg
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							printNonConverPrintExg();
						}
					});
		}
		return miNonCoverPrintExg;
	}

	/**
	 * This method initializes miConverPrintImg
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiConverPrintImg() {
		if (miConverPrintImg == null) {
			miConverPrintImg = new JMenuItem();
			miConverPrintImg.setText("套打料件表");
			miConverPrintImg.setSize(new Dimension(100, 30));
			miConverPrintImg
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							printConverPrintImg();
						}
					});
		}
		return miConverPrintImg;
	}

	/**
	 * This method initializes miNonCoverPrintImg
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiNonCoverPrintImg() {
		if (miNonCoverPrintImg == null) {
			miNonCoverPrintImg = new JMenuItem();
			miNonCoverPrintImg.setText("非套打料件表");
			miNonCoverPrintImg.setSize(new Dimension(100, 30));
			miNonCoverPrintImg
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							printNonConverPrintImg();
						}
					});
		}
		return miNonCoverPrintImg;
	}

	/**
	 * This method initializes miExportExgRecordationChecklistChange
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiExportExgRecordationChecklistChange() {
		if (miExportExgRecordationChecklistChange == null) {
			miExportExgRecordationChecklistChange = new JMenuItem();
			miExportExgRecordationChecklistChange.setText("出口成品备案清单变更表");
			miExportExgRecordationChecklistChange
					.setSize(new Dimension(100, 30));
			miExportExgRecordationChecklistChange
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							printExportRecordationChecklistChange();
						}
					});
		}
		return miExportExgRecordationChecklistChange;
	}

	/**
	 * This method initializes miImportExgRecordationChecklistChange
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiImportExgRecordationChecklistChange() {
		if (miImportExgRecordationChecklistChange == null) {
			miImportExgRecordationChecklistChange = new JMenuItem();
			miImportExgRecordationChecklistChange.setText("进口成品备案清单变更表");
			miImportExgRecordationChecklistChange
					.setSize(new Dimension(100, 30));
			miImportExgRecordationChecklistChange
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							printImportRecordationChecklistChange();
						}
					});
		}
		return miImportExgRecordationChecklistChange;
	}

	/**
	 * This method initializes miImportExgRecordationChecklistChange
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiNonDZSCContractFile() {
		if (miNonDZSCContractFile == null) {
			miNonDZSCContractFile = new JMenuItem();
			miNonDZSCContractFile.setText("电子手册合同备案表");
			miNonDZSCContractFile.setSize(new Dimension(100, 30));
			miNonDZSCContractFile
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							printDZSCContractFile();
						}
					});
		}
		return miNonDZSCContractFile;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCorrEmsNo() {
		if (tfCorrEmsNo == null) {
			tfCorrEmsNo = new JTextField();
			tfCorrEmsNo.setBounds(new Rectangle(114, 132, 126, 24));
			tfCorrEmsNo.setEditable(false);
		}
		return tfCorrEmsNo;
	}

	/**
	 * This method initializes btnIEPort
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnIEPort() {
		if (btnIEPort == null) {
			btnIEPort = new JButton();
			btnIEPort.setBounds(new Rectangle(485, 401, 186, 26));
			btnIEPort.setText("\u8fdb\u51fa\u53e3\u5cb8");
			btnIEPort.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgDzscEmsPorWjIEPort dg = new DgDzscEmsPorWjIEPort();
					dg.setDzscEmsPorWjHead(head);
					dg.setDataState(dataState);
					dg.setVisible(true);
				}
			});
		}
		return btnIEPort;
	}

	/**
	 * This method initializes calendarSaveDate	
	 * 	
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox	
	 */
	private JCalendarComboBox getCalendarSaveDate() {
		if (calendarSaveDate == null) {
			calendarSaveDate = new JCalendarComboBox();
			calendarSaveDate.setBounds(new Rectangle(110, 468, 127, 22));
		}
		return calendarSaveDate;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
