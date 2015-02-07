/*
 * Created on 2004-7-5
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.manualdeclare;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.custombase.entity.basecode.CoType;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.countrycode.District;
import com.bestway.bcus.custombase.entity.depcode.RedDep;
import com.bestway.bcus.custombase.entity.parametercode.Curr;

import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.EmsEdiTrExg;
import com.bestway.bcus.manualdeclare.entity.EmsEdiTrHead;
import com.bestway.bcus.manualdeclare.entity.EmsEdiTrImg;
import com.bestway.bcus.system.entity.ParameterSet;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.DelcareType;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.common.constant.ParameterType;
import com.bestway.ui.winuicontrol.JDialogBase;

import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import javax.swing.border.BevelBorder;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public class DgEmsEdiTr extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JButton btnAdd = null;

	private JButton btnEdit = null;

	private JButton btnDelete = null;

	private JButton btnSave = null;

	private JButton btnExit = null;

	private JTabbedPane jTabbedPane = null; // @jve:decl-index=0:visual-constraint="10,477"

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JPanel jPanel2 = null;

	private JTextField tfCopEmsNo = null;

	private JTextField tfEmsNo = null;

	private JTextField tfEmsApprNo = null;

	private JTextField tfTradeCode = null;

	private JTextField tfTradeName = null;

	private JTextField tfMachCode = null;

	private JTextField tfMachName = null;

	private JTextField tfDeclareType = null;

	private JComboBox cmbDeclareErType = null;

	private JTextField tfTel = null;

	private JTextField tfAddress = null;

	private JComboBox cmbCoType = null;

	private DefaultFormatterFactory defaultFormatterFactory = null; // @jve:decl-index=0:visual-constraint="619,74"

	private JTextField tfInputUser = null;

	private JTextField tfDeclareTime = null;

	private JTextField tfEmsType = null;

	private JTextField tfNote = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private JTable jTable1 = null;

	private JScrollPane jScrollPane1 = null;

	private JTextField tfDistrict = null;

	private JButton btnSelDistrict = null;

	private District district = null; // 海关地区

	private Curr curr = null; // 海关币制

	private RedDep reddep = null; // 外经委

	private Customs custom = null; // 海关关区

	private ManualDeclareAction manualdeclearAction = null;

	private JTableListModel tableModel = null;

	private JTableListModel tableModelImg = null;

	private JTableListModel tableModelExg = null;

	private EmsEdiTrHead emseditr = null; // 经营范围表头

	private JTextField tfCurr = null;

	private JButton btnSelCurr = null;

	private JTextField tfRedDep = null;

	private JButton btnSelRedDep = null;

	private JTextField tfCustom = null;

	private JButton btnSelCustom = null;

	private JFormattedTextField ftfInvestAmount = null;

	private NumberFormatter numberFormatter = null; // @jve:decl-index=0:visual-constraint="636,13"

	private JFormattedTextField ftfMachinAbility = null;

	private JTextField tfInputDate = null;

	private JTextField tfDeclareDate = null;

	private boolean isChange = false; // 判断是否变更状态

	private JCalendarComboBox cmbBeginDate = null;

	private JCalendarComboBox cmbEndDate = null;

	private JTextField tfNewApprDate = null;

	private JTextField tfChangeApprDate = null;

	private int dataState = DataState.BROWSE;

	private JButton jButton = null;
	private boolean isHistoryChange = false;
	private boolean isH2kTrInput = false;// 当参数设置后可手工新增经营范围
	/**
	 * This is the default constructor
	 */
	public DgEmsEdiTr() {
		super();
		manualdeclearAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		initialize();
		List listPara = manualdeclearAction.findnameValues(new Request(CommonVars
				.getCurrUser()), ParameterType.H2K_TR_INPUT);
		if (listPara != null && listPara.size() > 0) {
			ParameterSet para = (ParameterSet) listPara.get(0);
			isH2kTrInput = ("1".equals(para.getNameValues()) ? true
					: false);
		}
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(751, 515);
		this.setTitle("经营范围备案维护");
		this.setContentPane(getJContentPane());
		this.addWindowListener(new java.awt.event.WindowAdapter() {

			public void windowOpened(java.awt.event.WindowEvent e) {

				if (tableModel.getCurrentRow() != null) {
					emseditr = (EmsEdiTrHead) tableModel.getCurrentRow();
					district = emseditr.getArea();
					curr = emseditr.getCurr();
					reddep = emseditr.getDeclareDep();
					custom = emseditr.getMasterCustoms();
				}
				fillWindow();
				List dataSourceImg = null;
				dataSourceImg = manualdeclearAction.findEmsEdiTrImg(
						new Request(CommonVars.getCurrUser()), emseditr);
				initTableImg(dataSourceImg);
				List dataSourceExg = null;
				dataSourceExg = manualdeclearAction.findEmsEdiTrExg(
						new Request(CommonVars.getCurrUser()), emseditr);
				initTableExg(dataSourceExg);
				if ((emseditr.getDeclareState()
						.equals(DeclareState.PROCESS_EXE))
						|| (emseditr.getDeclareState()
								.equals(DeclareState.WAIT_EAA))
						|| isHistoryChange == true) {
					dataState = DataState.READONLY;
				} else
					dataState = DataState.BROWSE;
				setState();
				beginState();
			}
		});
	}

	private void initTableImg(final List list) {
		tableModelImg = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("修改标志", "modifyMark", 60));
						list.add(addColumn("料件序号", "ptNo", 80, Integer.class));
						list.add(addColumn("商品编码", "complex", 100));
						list.add(addColumn("商品名称", "name", 120));
						list.add(addColumn("备注", "note", 150));
						list.add(addColumn("变更日期", "changeDate", 100));
						return list;
					}
				});
		jTable.getColumnModel().getColumn(1).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue1(value));
						return this;
					}

					private String castValue1(Object value) {
						String returnValue = "";
						if (String.valueOf(value).trim().equals("")) {
							return "";
						}
						if (value.equals("0")) {
							returnValue = "未修改";
						} else if (value.equals("1")) {
							returnValue = "已修改";
						} else if (value.equals("2")) {
							returnValue = "已删除";
						} else if (value.equals("3")) {
							returnValue = "新增";
						}
						return returnValue;
					}
				});
	}

	private void initTableExg(final List list) {
		tableModelExg = new JTableListModel(jTable1, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("修改标志", "modifyMark", 60));
						list.add(addColumn("成品序号", "ptNo", 80, Integer.class));
						list.add(addColumn("商品编码", "complex", 100));
						list.add(addColumn("商品名称", "name", 120));
						list.add(addColumn("备注", "note", 150));
						list.add(addColumn("变更日期", "changeDate", 100));
						return list;
					}
				});
		jTable1.getColumnModel().getColumn(1).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue1(value));
						return this;
					}

					private String castValue1(Object value) {
						String returnValue = "";
						if (String.valueOf(value).trim().equals("")) {
							return "";
						}
						if (value.equals("0")) {
							returnValue = "未修改";
						} else if (value.equals("1")) {
							returnValue = "已修改";
						} else if (value.equals("2")) {
							returnValue = "已删除";
						} else if (value.equals("3")) {
							returnValue = "新增";
						}
						return returnValue;
					}
				});
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */

	private void fillWindow() {
		if (emseditr != null) {
			// 初始基本信息

			this.cmbCoType.setModel(CustomBaseModel.getInstance()
					.getCoTypeModel());
			this.cmbCoType.setRenderer(CustomBaseRender.getInstance()
					.getRender());
			CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
					this.cmbCoType);

			getCmbCoType().setSelectedItem(emseditr.getOwnerType());
			tfCopEmsNo.setText(emseditr.getCopEmsNo()); // 企业内部编号
			tfEmsNo.setText(emseditr.getEmsNo()); // 帐册编号
			tfEmsApprNo.setText(emseditr.getEmsApprNo()); // 批准证编号
			tfTradeCode.setText(emseditr.getTradeCode()); // 经营单位代码
			tfTradeName.setText(emseditr.getTradeName()); // 经营单位名称
			tfMachCode.setText(emseditr.getOwnerCode()); // 加工单位代码
			tfMachName.setText(emseditr.getOwnerName()); // 加工单位名称
			tfNote.setText(emseditr.getNote());
			cmbBeginDate.setDate(emseditr.getBeginDate());
			cmbEndDate.setDate(emseditr.getEndDate());

			SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			if (emseditr.getInputDate() != null) {
				String inputDate = bartDateFormat.format(emseditr
						.getInputDate());
				tfInputDate.setText(inputDate);// 录入日期
			}

			if (emseditr.getDeclareDate() != null) {
				String declareDate = bartDateFormat.format(emseditr
						.getDeclareDate());
				tfDeclareDate.setText(declareDate);// 申报日期
			}

			if (emseditr.getNewApprDate() != null) {
				String declareDate = bartDateFormat.format(emseditr
						.getNewApprDate());
				tfNewApprDate.setText(declareDate);// 备案批准日期
			}
			if (emseditr.getChangeApprDate() != null) {
				String declareDate = bartDateFormat.format(emseditr
						.getChangeApprDate());
				tfChangeApprDate.setText(declareDate);// 变更批准日期
			}

			SimpleDateFormat bartTimeFormat = new SimpleDateFormat("HH:mm:ss");
			if (emseditr.getDeclareTime() != null) {
				String declareTime = bartTimeFormat.format(emseditr
						.getDeclareTime());
				tfDeclareTime.setText(declareTime);// 申报时间
			}
			tfInputUser.setText(emseditr.getInputEr());// 录入代号
			if (emseditr.getDeclareErType() == "1") // 申请单位类型
				this.cmbDeclareErType.setSelectedItem("企业");
			else if (emseditr.getDeclareErType() == "2")
				this.cmbDeclareErType.setSelectedItem("代理公司");
			else if (emseditr.getDeclareErType() == "3")
				this.cmbDeclareErType.setSelectedItem("报关行");
			if (this.district != null) // 地区代码
			{
				this.tfDistrict.setText(district.getName());
			} else {
				this.tfDistrict.setText("");
			}
			tfTel.setText(emseditr.getPhone()); // 联系电话
			tfAddress.setText(emseditr.getAddress()); // 联系地址
			ftfInvestAmount.setText(doubleToStr(emseditr.getInvestAmount())); // 投资金额
			ftfMachinAbility.setText(doubleToStr(emseditr.getProductRatio()));// 年加工能力
			if (this.curr != null) // 币制
			{
				this.tfCurr.setText(curr.getName());
			} else {
				this.tfCurr.setText("");
			}

			if (this.custom != null) // 主管海关
			{
				this.tfCustom.setText(custom.getName());
			} else {
				this.tfCustom.setText("");
			}
			if (this.reddep != null) // 外经委
			{
				this.tfRedDep.setText(reddep.getName());
			} else {
				this.tfRedDep.setText("");
			}

		}
	}

	private Double strToDouble(String value) { // 转换strToDouble 填充数据
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

	private String doubleToStr(Double value) { // 转换doubleToStr 取数据
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

	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new java.awt.BorderLayout());
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJTabbedPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	private void beginState() {

		if (DgEmsEdiTr.this.isChange) { // 变更状态
			if (dataState == DataState.EDIT)
				DgEmsEdiTr.this.setTitle("经营范围申请变更维护");
			else
				DgEmsEdiTr.this.setTitle("经营范围申请变更浏览");
		} else if (DgEmsEdiTr.this.isChange == false) { // 备案状态
			if (dataState == DataState.EDIT)
				DgEmsEdiTr.this.setTitle("经营范围申请备案维护");
			else
				DgEmsEdiTr.this.setTitle("经营范围申请备案浏览");
		}
	}

	/**
	 * 
	 * This method initializes jToolBar
	 * 
	 * 
	 * 
	 * @return javax.swing.JToolBar
	 * 
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			FlowLayout f = new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(1);
			f.setHgap(1);
			jToolBar = new JToolBar();
			jToolBar.setFloatable(false);
			jToolBar.setLayout(f);
			jToolBar.add(getBtnAdd());
			jToolBar.add(getJButton());
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnDelete());
			jToolBar.add(getBtnSave());
			jToolBar.add(getBtnExit());
		}
		return jToolBar;
	}

	/**
	 * 
	 * This method initializes btnAdd
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setText("新增");
			btnAdd.setPreferredSize(new Dimension(65, 30));
			btnAdd.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jTabbedPane.getSelectedIndex() == 1)// 料件
					{
						if (isH2kTrInput) {// 当为True时进入手工模式
							DgEmsEdiTrImgExg dgEmsEdiTrImgExg = new DgEmsEdiTrImgExg();
							dgEmsEdiTrImgExg.setImg(true);
							dgEmsEdiTrImgExg.setEmsEdiTrHead(emseditr);
							dgEmsEdiTrImgExg.setTableModel(tableModelImg);
							dgEmsEdiTrImgExg.setAdd(true);
							dgEmsEdiTrImgExg.setVisible(true);
						} else {
							EmsEdiTrImg emsEdiTrImg = null;
							List list = (List) CommonQuery.getInstance()
									.getImg4(false, emseditr, "");
							if (list == null || list.isEmpty())
								return;
							for (int i = 0; i < list.size(); i++) {
								emsEdiTrImg = (EmsEdiTrImg) list.get(i);
								emsEdiTrImg.setEmsEdiTrHead(emseditr);
								emsEdiTrImg.setModifyTimes(Integer.valueOf(0)); // 变更次数
								emsEdiTrImg
										.setModifyMark(ModifyMarkState.ADDED);
								emsEdiTrImg.setCompany(CommonVars.getCurrUser()
										.getCompany());
								emsEdiTrImg.setChangeDate(new Date()); // 修改时间
								emsEdiTrImg = manualdeclearAction
										.saveEmsEdiTrImg(new Request(CommonVars
												.getCurrUser()), emsEdiTrImg);
								tableModelImg.addRow(emsEdiTrImg);
							}
						}
					} else if (jTabbedPane.getSelectedIndex() == 2) {// 成品
						if (isH2kTrInput) {// 当为True时进入手工模式
							DgEmsEdiTrImgExg dgEmsEdiTrImgExg = new DgEmsEdiTrImgExg();
							dgEmsEdiTrImgExg.setImg(false);
							dgEmsEdiTrImgExg.setEmsEdiTrHead(emseditr);
							dgEmsEdiTrImgExg.setTableModel(tableModelExg);
							dgEmsEdiTrImgExg.setAdd(true);
							dgEmsEdiTrImgExg.setVisible(true);
						} else {//
							EmsEdiTrExg emsEdiTrExg = null;
							List list = (List) CommonQuery.getInstance()
									.getExg4(false, emseditr, "");
							if (list == null || list.isEmpty())
								return;
							for (int i = 0; i < list.size(); i++) {
								emsEdiTrExg = (EmsEdiTrExg) list.get(i);
								emsEdiTrExg.setEmsEdiTrHead(emseditr);
								emsEdiTrExg.setModifyTimes(Integer.valueOf(0));
								emsEdiTrExg
										.setModifyMark(ModifyMarkState.ADDED);
								emsEdiTrExg.setChangeDate(new Date());// 修改时间
								emsEdiTrExg.setCompany(CommonVars.getCurrUser()
										.getCompany());
								emsEdiTrExg = manualdeclearAction
										.saveEmsEdiTrExg(new Request(CommonVars
												.getCurrUser()), emsEdiTrExg);
								tableModelExg.addRow(emsEdiTrExg);
							}
						}
					}
					setState();
				}
			});

		}
		return btnAdd;
	}

	private void fillDataExg(EmsEdiTrExg emsEdiTrExg) {
		emsEdiTrExg.setEmsEdiTrHead(emseditr);
		emsEdiTrExg.setPtNo(Integer.valueOf(1));
		emsEdiTrExg.setName("成品");
		emsEdiTrExg.setCompany(CommonVars.getCurrUser().getCompany()); // 公司id
	}

	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改");
			btnEdit.setPreferredSize(new Dimension(65, 30));
			btnEdit.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jTabbedPane.getSelectedIndex() == 0) {
						dataState = DataState.EDIT;
						setState();
					} else {
						DgEmsEdiTrImgExg dgEmsEdiTrImgExg = new DgEmsEdiTrImgExg();
						if (jTabbedPane.getSelectedIndex() == 1)// 料件
						{
							if (tableModelImg.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(DgEmsEdiTr.this,
										"请选择你将要修改的记录", "提示！", 0);
								return;
							}
							dgEmsEdiTrImgExg.setImg(true);
							dgEmsEdiTrImgExg.setEmsEdiTrHead(emseditr);
							dgEmsEdiTrImgExg.setTableModel(tableModelImg);
						} else if (jTabbedPane.getSelectedIndex() == 2) {// 成品
							if (tableModelExg.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(DgEmsEdiTr.this,
										"请选择你将要修改的记录", "提示！", 0);
								return;
							}
							dgEmsEdiTrImgExg.setImg(false);
							dgEmsEdiTrImgExg.setEmsEdiTrHead(emseditr);
							dgEmsEdiTrImgExg.setTableModel(tableModelExg);

						}
						// 当参数设置后可进行修改序号
						if (CommonVars.getH2kMergerModifyNo() != null) {
							if (CommonVars.getH2kMergerModifyNo()) {
								dgEmsEdiTrImgExg.setModifySeqNUm(true);
							}
						}
						dgEmsEdiTrImgExg.setVisible(true);
					}
				}
			});

		}
		return btnEdit;
	}

	/**
	 * 
	 * This method initializes btnDelete
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setText("删除");
			btnDelete.setPreferredSize(new Dimension(65, 30));
			btnDelete.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (jTabbedPane.getSelectedIndex() == 1)// 料件
					{
						if (tableModelImg.getCurrentRows() == null) {
							JOptionPane.showMessageDialog(DgEmsEdiTr.this,
									"请选择你要删除的资料", "确认", 1);
							return;
						}
						if (JOptionPane.showConfirmDialog(DgEmsEdiTr.this,
								"你确定要删除此记录吗?", "确认", 0) == 0) {
							List ls = tableModelImg.getCurrentRows();
							for (int i = 0; i < ls.size(); i++) {
								EmsEdiTrImg emsEdi = (EmsEdiTrImg) ls.get(i);
								if (emseditr.getDeclareType().equals(
										DelcareType.APPLICATION)
										|| emsEdi.getModifyMark().equals(
												ModifyMarkState.ADDED)) {
									manualdeclearAction.deleteEmsEdiTrImg(
											new Request(CommonVars
													.getCurrUser()), emsEdi);
									tableModelImg.deleteRow(emsEdi);
								} else {
									emsEdi
											.setModifyMark(ModifyMarkState.DELETED);
									emsEdi = manualdeclearAction
											.saveEmsEdiTrImg(new Request(
													CommonVars.getCurrUser()),
													emsEdi);
									tableModelImg.updateRow(emsEdi);
								}
							}
						}
					} else if (jTabbedPane.getSelectedIndex() == 2) {// 成品
						if (tableModelExg.getCurrentRows() == null) {
							JOptionPane.showMessageDialog(DgEmsEdiTr.this,
									"请选择你要删除的资料", "确认", 1);
							return;
						}
						if (JOptionPane.showConfirmDialog(DgEmsEdiTr.this,
								"你确定要删除此记录吗?", "确认", 0) == 0) {
							List ls = tableModelExg.getCurrentRows();
							for (int i = 0; i < ls.size(); i++) {
								EmsEdiTrExg emsEdi = (EmsEdiTrExg) ls.get(i);

								if (emseditr.getDeclareType().equals(
										DelcareType.APPLICATION)
										|| emsEdi.getModifyMark().equals(
												ModifyMarkState.ADDED)) {
									manualdeclearAction.deleteEmsEdiTrExg(
											new Request(CommonVars
													.getCurrUser()), emsEdi);
									tableModelExg.deleteRow(emsEdi);
								} else {
									emsEdi
											.setModifyMark(ModifyMarkState.DELETED);
									emsEdi = manualdeclearAction
											.saveEmsEdiTrExg(new Request(
													CommonVars.getCurrUser()),
													emsEdi);
									tableModelExg.updateRow(emsEdi);
								}
							}
						}
					}
					setState();
				}
			});

		}
		return btnDelete;
	}

	/**
	 * 
	 * This method initializes btnSave
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("保存");
			btnSave.setPreferredSize(new Dimension(65, 30));
			btnSave.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) { // 保存
					// 表头保存
					fillEmsEdiTrHead(emseditr);
					emseditr = manualdeclearAction.saveEmsEdiTrHead(
							new Request(CommonVars.getCurrUser()), emseditr);
					tableModel.updateRow(emseditr);
					dataState = DataState.BROWSE;
					setState();
				}
			});

		}
		return btnSave;
	}

	private void fillEmsEdiTrHead(EmsEdiTrHead emseditr) {
		EmsEdiTrHead emsEdiTrHeadOld = new EmsEdiTrHead();
		emsEdiTrHeadOld = (EmsEdiTrHead) emseditr.clone();
		emseditr.setEmsApprNo(tfEmsApprNo.getText().trim());// 批准证编号
		if (cmbDeclareErType.getSelectedItem().equals("企业"))
			emseditr.setDeclareErType("1");
		else if (cmbDeclareErType.getSelectedItem().equals("代理公司"))
			emseditr.setDeclareErType("2");
		else if (cmbDeclareErType.getSelectedItem().equals("代理公司"))
			emseditr.setDeclareErType("3");
		emseditr.setArea(DgEmsEdiTr.this.district); // 地区代码
		emseditr.setPhone(tfTel.getText().trim()); // 联系电话
		emseditr.setAddress(tfAddress.getText().trim());// 联系地址
		emseditr.setTradeName(tfTradeName.getText().trim()); // 经营单位名称
		emseditr.setOwnerName(tfMachName.getText().trim()); // 加工单位名称
		emseditr.setInvestAmount(strToDouble(ftfInvestAmount.getText())); // 投资金额
		emseditr.setProductRatio(strToDouble(ftfMachinAbility.getText())); // 年加工能力
		emseditr.setCurr(DgEmsEdiTr.this.curr); // 币制
		emseditr.setMasterCustoms(DgEmsEdiTr.this.custom);// 主管海关
		emseditr.setDeclareDep(DgEmsEdiTr.this.reddep); // 外经委
		emseditr.setOwnerType((CoType) cmbCoType.getSelectedItem()); // 企业性质
		emseditr.setBeginDate(cmbBeginDate.getDate()); // 开始有效期
		emseditr.setEndDate(cmbEndDate.getDate()); // 结束有效期
		if (!emseditr.fullEquals(emsEdiTrHeadOld)
				&& emseditr.getDeclareType().equals(DelcareType.MODIFY)) {
			emseditr.setModifyMark(ModifyMarkState.MODIFIED); // 已修改
		}
		emseditr.setNote(tfNote.getText());
	}

	/**
	 * 
	 * This method initializes btnExit
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("关闭");
			btnExit.setPreferredSize(new Dimension(65, 30));
			btnExit.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgEmsEdiTr.this.dispose();

				}
			});

		}
		return btnExit;
	}

	/**
	 * 
	 * This method initializes jTabbedPane
	 * 
	 * 
	 * 
	 * @return javax.swing.JTabbedPane
	 * 
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
			jTabbedPane.setSize(568, 477);
			jTabbedPane.addTab("基本资料", null, getJPanel(), null);
			jTabbedPane.addTab("料件表", null, getJPanel1(), null);
			jTabbedPane.addTab("成品表", null, getJPanel2(), null);
			jTabbedPane
					.addChangeListener(new javax.swing.event.ChangeListener() {

						public void stateChanged(javax.swing.event.ChangeEvent e) {
							setState();
						}
					});

		}
		return jTabbedPane;
	}

	/**
	 * 
	 * This method initializes jPanel
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			javax.swing.JLabel jLabel29 = new JLabel();

			javax.swing.JLabel jLabel28 = new JLabel();

			javax.swing.JLabel jLabel27 = new JLabel();

			javax.swing.JLabel jLabel26 = new JLabel();

			javax.swing.JLabel jLabel25 = new JLabel();

			javax.swing.JLabel jLabel24 = new JLabel();

			javax.swing.JLabel jLabel23 = new JLabel();

			javax.swing.JLabel jLabel22 = new JLabel();

			javax.swing.JLabel jLabel21 = new JLabel();

			javax.swing.JLabel jLabel20 = new JLabel();

			javax.swing.JLabel jLabel19 = new JLabel();

			javax.swing.JLabel jLabel18 = new JLabel();

			javax.swing.JLabel jLabel17 = new JLabel();

			javax.swing.JLabel jLabel16 = new JLabel();

			javax.swing.JLabel jLabel15 = new JLabel();

			javax.swing.JLabel jLabel14 = new JLabel();

			javax.swing.JLabel jLabel13 = new JLabel();

			javax.swing.JLabel jLabel12 = new JLabel();

			javax.swing.JLabel jLabel11 = new JLabel();

			javax.swing.JLabel jLabel10 = new JLabel();

			javax.swing.JLabel jLabel9 = new JLabel();

			javax.swing.JLabel jLabel8 = new JLabel();

			javax.swing.JLabel jLabel7 = new JLabel();

			javax.swing.JLabel jLabel6 = new JLabel();

			javax.swing.JLabel jLabel5 = new JLabel();

			javax.swing.JLabel jLabel4 = new JLabel();

			javax.swing.JLabel jLabel3 = new JLabel();

			javax.swing.JLabel jLabel2 = new JLabel();

			javax.swing.JLabel jLabel1 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();

			jPanel = new JPanel();
			jPanel.setLayout(null);
			jLabel.setText("企业内部编号");
			jLabel.setBounds(28, 45, 77, 21);
			jPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory
					.createBevelBorder(BevelBorder.LOWERED),
					"\u7ecf\u8425\u8303\u56f4\u8868\u5934\u4fe1\u606f",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.PLAIN, 12), new Color(51, 51, 51)));
			jLabel1.setBounds(262, 45, 84, 22);
			jLabel1.setText("帐册编号");
			jLabel2.setBounds(498, 46, 83, 20);
			jLabel2.setText("批准证编号");
			jLabel3.setBounds(28, 77, 77, 21);
			jLabel3.setText("经营单位代码");
			jLabel4.setBounds(262, 77, 84, 22);
			jLabel4.setText("经营单位名称");
			jLabel5.setBounds(28, 109, 77, 21);
			jLabel5.setText("加工单位代码");
			jLabel6.setBounds(262, 109, 84, 22);
			jLabel6.setText("加工单位名称");
			jLabel7.setBounds(28, 142, 77, 21);
			jLabel7.setText("申报类型");
			jLabel8.setBounds(262, 142, 84, 22);
			jLabel8.setText("申请单位类型");
			jLabel9.setBounds(498, 143, 83, 20);
			jPanel.add(getTfInputDate(), null);
			jLabel9.setText("地区代码");
			jLabel10.setBounds(28, 172, 77, 21);
			jLabel10.setText("联系电话");
			jLabel11.setBounds(262, 171, 85, 22);
			jLabel11.setText("联系地址");
			jLabel12.setBounds(28, 201, 77, 21);
			jLabel12.setText("投资金额");
			jLabel12.setForeground(new java.awt.Color(0, 51, 255));
			jLabel13.setBounds(206, 199, 50, 20);
			jLabel13.setText("万美元");
			jLabel13.setForeground(new java.awt.Color(0, 51, 255));
			jLabel14.setBounds(262, 200, 84, 22);
			jLabel14.setText("年加工生产能力");
			jLabel14.setForeground(java.awt.Color.blue);
			jLabel15.setBounds(450, 198, 49, 22);
			jLabel15.setText("万美元");
			jLabel15.setForeground(new java.awt.Color(0, 51, 255));
			jLabel16.setBounds(28, 233, 77, 21);
			jLabel16.setText("币制");
			jLabel17.setBounds(498, 234, 83, 20);
			jLabel17.setText("企业性质");
			jLabel18.setBounds(262, 232, 84, 22);
			jLabel18.setText("主管海关");
			jLabel19.setBounds(498, 202, 83, 20);
			jLabel19.setText("外经贸部门");
			jLabel20.setBounds(28, 267, 77, 21);
			jLabel20.setText("开始有效期");
			jLabel21.setBounds(262, 266, 84, 22);
			jLabel21.setText("结束有效期");
			jLabel22.setBounds(498, 268, 83, 20);
			jLabel22.setText("申报日期");
			jLabel23.setBounds(28, 298, 77, 21);
			jLabel23.setText("录入日期");
			jLabel24.setBounds(262, 297, 84, 22);
			jLabel24.setText("录入员代号");
			jLabel25.setBounds(498, 299, 83, 20);
			jLabel25.setText("申报时间");
			jLabel26.setBounds(28, 328, 77, 21);
			jLabel26.setText("帐册类型");
			jLabel27.setBounds(262, 327, 84, 22);
			jLabel27.setText("备案批准日期");
			jLabel28.setBounds(498, 329, 83, 20);
			jLabel28.setText("变更批准日期");
			jLabel29.setBounds(28, 362, 77, 21);
			jLabel29.setText("备注");
			jPanel.add(jLabel, null);
			jPanel.add(getTfCopEmsNo(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(getTfEmsNo(), null);
			jPanel.add(jLabel2, null);
			jPanel.add(getTfEmsApprNo(), null);
			jPanel.add(jLabel3, null);
			jPanel.add(getTfTradeCode(), null);
			jPanel.add(jLabel4, null);
			jPanel.add(getTfTradeName(), null);
			jPanel.add(jLabel5, null);
			jPanel.add(getTfMachCode(), null);
			jPanel.add(jLabel6, null);
			jPanel.add(getTfMachName(), null);
			jPanel.add(jLabel7, null);
			jPanel.add(getTfDeclareType(), null);
			jPanel.add(jLabel8, null);
			jPanel.add(getCmbDeclareErType(), null);
			jPanel.add(jLabel9, null);
			jPanel.add(getTfTel(), null);
			jPanel.add(jLabel10, null);
			jPanel.add(jLabel11, null);
			jPanel.add(getTfAddress(), null);
			jPanel.add(jLabel12, null);
			jPanel.add(jLabel13, null);
			jPanel.add(jLabel14, null);
			jPanel.add(jLabel15, null);
			jPanel.add(jLabel16, null);
			jPanel.add(jLabel17, null);
			jPanel.add(getCmbCoType(), null);
			jPanel.add(jLabel18, null);
			jPanel.add(jLabel19, null);
			jPanel.add(jLabel20, null);
			jPanel.add(jLabel21, null);
			jPanel.add(jLabel22, null);
			jPanel.add(jLabel23, null);
			jPanel.add(jLabel24, null);
			jPanel.add(getTfInputUser(), null);
			jPanel.add(jLabel25, null);
			jPanel.add(getTfDeclareTime(), null);
			jPanel.add(jLabel26, null);
			jPanel.add(getTfEmsType(), null);
			jPanel.add(jLabel27, null);
			jPanel.add(jLabel28, null);
			jPanel.add(jLabel29, null);
			jPanel.add(getTfNote(), null);
			jPanel.add(getTfDistrict(), null);
			jPanel.add(getBtnSelDistrict(), null);
			jPanel.add(getTfCurr(), null);
			jPanel.add(getBtnSelCurr(), null);
			jPanel.add(getJTextField18(), null);
			jPanel.add(getBtnSelRedDep(), null);
			jPanel.add(getJTextField19(), null);
			jPanel.add(getBtnSelCustom(), null);
			jPanel.add(getFtfInvestAmount(), null);
			jPanel.add(getFtf(), null);
			jPanel.add(getTfDeclareDate(), null);
			jPanel.add(getCmbBeginDate(), null);
			jPanel.add(getCmbEndDate(), null);
			jPanel.add(getTfNewApprDate(), null);
			jPanel.add(getTfChangeApprDate(), null);
		}
		return jPanel;
	}

	/**
	 * 
	 * This method initializes jPanel1
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(
					null, "料件备案申请",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			jPanel1.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel1;
	}

	/**
	 * 
	 * This method initializes jPanel2
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(new BorderLayout());
			jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(
					null, "成品备案申请",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			jPanel2.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel2;
	}

	/**
	 * 
	 * This method initializes jTextField
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfCopEmsNo() {
		if (tfCopEmsNo == null) {
			tfCopEmsNo = new JTextField();
			tfCopEmsNo.setBounds(113, 41, 141, 25);
			tfCopEmsNo.setEditable(false);
		}
		return tfCopEmsNo;
	}

	/**
	 * 
	 * This method initializes jTextField1
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfEmsNo() {
		if (tfEmsNo == null) {
			tfEmsNo = new JTextField();
			tfEmsNo.setBounds(348, 41, 140, 25);
			tfEmsNo.setEditable(false);
		}
		return tfEmsNo;
	}

	/**
	 * 
	 * This method initializes jTextField2
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfEmsApprNo() {
		if (tfEmsApprNo == null) {
			tfEmsApprNo = new JTextField();
			tfEmsApprNo.setBounds(582, 41, 141, 25);
		}
		return tfEmsApprNo;
	}

	/**
	 * 
	 * This method initializes jTextField3
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfTradeCode() {
		if (tfTradeCode == null) {
			tfTradeCode = new JTextField();
			tfTradeCode.setBounds(113, 73, 141, 25);
			tfTradeCode.setEditable(false);
		}
		return tfTradeCode;
	}

	/**
	 * 
	 * This method initializes jTextField4
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfTradeName() {
		if (tfTradeName == null) {
			tfTradeName = new JTextField();
			tfTradeName.setBounds(348, 73, 373, 25);
		}
		return tfTradeName;
	}

	/**
	 * 
	 * This method initializes jTextField5
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfMachCode() {
		if (tfMachCode == null) {
			tfMachCode = new JTextField();
			tfMachCode.setBounds(113, 105, 141, 25);
			tfMachCode.setEditable(false);
		}
		return tfMachCode;
	}

	/**
	 * 
	 * This method initializes jTextField6
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfMachName() {
		if (tfMachName == null) {
			tfMachName = new JTextField();
			tfMachName.setBounds(348, 105, 373, 25);
		}
		return tfMachName;
	}

	/**
	 * 
	 * This method initializes tfDeclareType
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfDeclareType() {
		if (tfDeclareType == null) {
			tfDeclareType = new JTextField();
			tfDeclareType.setBounds(113, 137, 141, 25);
			tfDeclareType.setText("备案申请");
			tfDeclareType.setEditable(false);
		}
		return tfDeclareType;
	}

	/**
	 * 
	 * This method initializes cmbDeclareErType
	 * 
	 * 
	 * 
	 * @return javax.swing.JComboBox
	 * 
	 */
	private JComboBox getCmbDeclareErType() {
		if (cmbDeclareErType == null) {
			cmbDeclareErType = new JComboBox(
					new String[] { "企业", "代理公司", "报关行" });
			cmbDeclareErType.setBounds(348, 138, 140, 25);
		}
		return cmbDeclareErType;
	}

	/**
	 * 
	 * This method initializes jTextField8
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfTel() {
		if (tfTel == null) {
			tfTel = new JTextField();
			tfTel.setBounds(113, 168, 141, 25);
		}
		return tfTel;
	}

	/**
	 * 
	 * This method initializes jTextField9
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfAddress() {
		if (tfAddress == null) {
			tfAddress = new JTextField();
			tfAddress.setBounds(348, 168, 373, 25);
		}
		return tfAddress;
	}

	/**
	 * 
	 * This method initializes cmbCoType
	 * 
	 * 
	 * 
	 * @return javax.swing.JComboBox
	 * 
	 */
	private JComboBox getCmbCoType() {
		if (cmbCoType == null) {
			cmbCoType = new JComboBox();
			cmbCoType.setBounds(582, 229, 141, 25);
		}
		return cmbCoType;
	}

	/**
	 * 
	 * This method initializes defaultFormatterFactory
	 * 
	 * 
	 * 
	 * @return javax.swing.text.DefaultFormatterFactory
	 * 
	 */
	private DefaultFormatterFactory getDefaultFormatterFactory() {
		if (defaultFormatterFactory == null) {
			defaultFormatterFactory = new DefaultFormatterFactory();
			defaultFormatterFactory.setDefaultFormatter(getNumberFormatter());
			defaultFormatterFactory.setDisplayFormatter(getNumberFormatter());
		}
		return defaultFormatterFactory;
	}

	/**
	 * 
	 * This method initializes jTextField12
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfInputUser() {
		if (tfInputUser == null) {
			tfInputUser = new JTextField();
			tfInputUser.setBounds(348, 294, 140, 25);
			tfInputUser.setEditable(false);
		}
		return tfInputUser;
	}

	/**
	 * 
	 * This method initializes jTextField13
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfDeclareTime() {
		if (tfDeclareTime == null) {
			tfDeclareTime = new JTextField();
			tfDeclareTime.setBounds(582, 294, 141, 25);
			tfDeclareTime.setEditable(false);
		}
		return tfDeclareTime;
	}

	/**
	 * 
	 * This method initializes tfEmsType
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfEmsType() {
		if (tfEmsType == null) {
			tfEmsType = new JTextField();
			tfEmsType.setBounds(113, 324, 141, 25);
			tfEmsType.setText("经营范围帐册");
			tfEmsType.setEditable(false);
		}
		return tfEmsType;
	}

	/**
	 * 
	 * This method initializes tfNote
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfNote() {
		if (tfNote == null) {
			tfNote = new JTextField();
			tfNote.setBounds(113, 358, 609, 25);
		}
		return tfNote;
	}

	/**
	 * 
	 * This method initializes jTable
	 * 
	 * 
	 * 
	 * @return javax.swing.JTable
	 * 
	 */
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
			jTable.setRowHeight(25);
		}
		return jTable;
	}

	/**
	 * 
	 * This method initializes jScrollPane
	 * 
	 * 
	 * 
	 * @return javax.swing.JScrollPane
	 * 
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
	}

	/**
	 * 
	 * This method initializes jTable1
	 * 
	 * 
	 * 
	 * @return javax.swing.JTable
	 * 
	 */
	private JTable getJTable1() {
		if (jTable1 == null) {
			jTable1 = new JTable();
			jTable1.setRowHeight(25);
		}
		return jTable1;
	}

	/**
	 * 
	 * This method initializes jScrollPane1
	 * 
	 * 
	 * 
	 * @return javax.swing.JScrollPane
	 * 
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJTable1());
		}
		return jScrollPane1;
	}

	/**
	 * 
	 * This method initializes jTextField16
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfDistrict() {
		if (tfDistrict == null) {
			tfDistrict = new JTextField();
			tfDistrict.setBounds(582, 138, 116, 25);
			tfDistrict.setEditable(false);
		}
		return tfDistrict;
	}

	/**
	 * 
	 * This method initializes jButton5
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnSelDistrict() {
		if (btnSelDistrict == null) {
			btnSelDistrict = new JButton();
			btnSelDistrict.setBounds(698, 137, 24, 25);
			btnSelDistrict.setText("...");
			btnSelDistrict
					.addActionListener(new java.awt.event.ActionListener() {

						public void actionPerformed(java.awt.event.ActionEvent e) {

							District c = (District) CommonQuery.getInstance()
									.getDistrict(DgEmsEdiTr.this.district);
							if (c != null) {
								DgEmsEdiTr.this.district = c;
								getTfDistrict().setText(c.getName());
							}

						}
					});

		}
		return btnSelDistrict;
	}

	/**
	 * 
	 * This method initializes jTextField17
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfCurr() {
		if (tfCurr == null) {
			tfCurr = new JTextField();
			tfCurr.setBounds(113, 229, 117, 25);
			tfCurr.setEditable(false);
		}
		return tfCurr;
	}

	/**
	 * 
	 * This method initializes jButton6
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnSelCurr() {
		if (btnSelCurr == null) {
			btnSelCurr = new JButton();
			btnSelCurr.setBounds(230, 228, 21, 25);
			btnSelCurr.setText("...");
			btnSelCurr.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					Curr c = (Curr) CommonQuery.getInstance().getCustomCurr();
					if (c != null) {
						curr = c;
						getTfCurr().setText(c.getName());
					}
				}
			});

		}
		return btnSelCurr;
	}

	/**
	 * 
	 * This method initializes tfRedDep
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField18() {
		if (tfRedDep == null) {
			tfRedDep = new JTextField();
			tfRedDep.setBounds(582, 197, 119, 25);
			tfRedDep.setEditable(false);
		}
		return tfRedDep;
	}

	/**
	 * 
	 * This method initializes jButton7
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnSelRedDep() {
		if (btnSelRedDep == null) {
			btnSelRedDep = new JButton();
			btnSelRedDep.setBounds(702, 197, 22, 25);
			btnSelRedDep.setText("...");
			btnSelRedDep.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					RedDep c = (RedDep) CommonQuery.getInstance().getRedDep();
					if (c != null) {
						reddep = c;
						getJTextField18().setText(c.getName());
					}

				}
			});

		}
		return btnSelRedDep;
	}

	/**
	 * 
	 * This method initializes tfCustom
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField19() {
		if (tfCustom == null) {
			tfCustom = new JTextField();
			tfCustom.setBounds(348, 229, 119, 25);
			tfCustom.setEditable(false);
		}
		return tfCustom;
	}

	/**
	 * 
	 * This method initializes jButton8
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnSelCustom() {
		if (btnSelCustom == null) {
			btnSelCustom = new JButton();
			btnSelCustom.setBounds(465, 232, 21, 21);
			btnSelCustom.setText("...");
			btnSelCustom.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					Customs c = (Customs) CommonQuery.getInstance()
							.getCustoms();
					if (c != null) {
						custom = c;
						getJTextField19().setText(c.getName());
					}
				}
			});

		}
		return btnSelCustom;
	}

	/**
	 * @return Returns the tableModel.
	 */
	public JTableListModel getTableModel() {
		return tableModel;
	}

	/**
	 * @param tableModel
	 *            The tableModel to set.
	 */
	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
	}

	/**
	 * 
	 * This method initializes jFormattedTextField3
	 * 
	 * 
	 * 
	 * @return javax.swing.JFormattedTextField
	 * 
	 */
	private JFormattedTextField getFtfInvestAmount() {
		if (ftfInvestAmount == null) {
			ftfInvestAmount = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			ftfInvestAmount.setBounds(113, 197, 93, 25);
			ftfInvestAmount.setFormatterFactory(getDefaultFormatterFactory());
		}
		return ftfInvestAmount;
	}

	/**
	 * 
	 * This method initializes numberFormatter
	 * 
	 * 
	 * 
	 * @return javax.swing.text.NumberFormatter
	 * 
	 */
	private NumberFormatter getNumberFormatter() {
		if (numberFormatter == null) {
			numberFormatter = new NumberFormatter();
		}
		return numberFormatter;
	}

	/**
	 * 
	 * This method initializes jFormattedTextField4
	 * 
	 * 
	 * 
	 * @return javax.swing.JFormattedTextField
	 * 
	 */
	private JFormattedTextField getFtf() {
		if (ftfMachinAbility == null) {
			ftfMachinAbility = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			ftfMachinAbility.setBounds(348, 197, 103, 25);
			ftfMachinAbility.setFormatterFactory(getDefaultFormatterFactory());
		}
		return ftfMachinAbility;
	}

	/**
	 * 
	 * This method initializes jTextField10
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfInputDate() {
		if (tfInputDate == null) {
			tfInputDate = new JTextField();
			tfInputDate.setBounds(113, 294, 141, 25);
			tfInputDate.setEditable(false);
		}
		return tfInputDate;
	}

	/**
	 * 
	 * This method initializes jTextField11
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfDeclareDate() {
		if (tfDeclareDate == null) {
			tfDeclareDate = new JTextField();
			tfDeclareDate.setBounds(582, 263, 141, 25);
			tfDeclareDate.setEditable(false);
		}
		return tfDeclareDate;
	}

	/**
	 * @return Returns the isChange.
	 */
	public boolean isChange() {
		return isChange;
	}

	/**
	 * @param isChange
	 *            The isChange to set.
	 */
	public void setChange(boolean isChange) {
		this.isChange = isChange;
	}

	/**
	 * 
	 * This method initializes jCalendarComboBox
	 * 
	 * 
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 * 
	 */
	private JCalendarComboBox getCmbBeginDate() {
		if (cmbBeginDate == null) {
			cmbBeginDate = new JCalendarComboBox();
			cmbBeginDate.setBounds(113, 263, 141, 25);
		}
		return cmbBeginDate;
	}

	/**
	 * 
	 * This method initializes jCalendarComboBox1
	 * 
	 * 
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 * 
	 */
	private JCalendarComboBox getCmbEndDate() {
		if (cmbEndDate == null) {
			cmbEndDate = new JCalendarComboBox();
			cmbEndDate.setBounds(348, 263, 140, 25);
		}
		return cmbEndDate;
	}

	/**
	 * 
	 * This method initializes tfNewApprDate
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfNewApprDate() {
		if (tfNewApprDate == null) {
			tfNewApprDate = new JTextField();
			tfNewApprDate.setBounds(348, 324, 140, 25);
			tfNewApprDate.setEditable(false);
		}
		return tfNewApprDate;
	}

	/**
	 * 
	 * This method initializes tfChangeApprDate
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfChangeApprDate() {
		if (tfChangeApprDate == null) {
			tfChangeApprDate = new JTextField();
			tfChangeApprDate.setBounds(582, 324, 141, 25);
			tfChangeApprDate.setEditable(false);
		}
		return tfChangeApprDate;
	}

	private void setState() {
		jTabbedPane.setEnabled((emseditr.getDeclareState().equals(
				DeclareState.APPLY_POR) && dataState == DataState.BROWSE)
				|| !emseditr.getDeclareState().equals(DeclareState.APPLY_POR));
		btnAdd.setEnabled(!(jTabbedPane.getSelectedIndex() == 0)
				&& (dataState != DataState.READONLY)); // 新增
		jButton.setEnabled(!(jTabbedPane.getSelectedIndex() == 0)
				&& (dataState != DataState.READONLY)); // 新增(半成品)
		btnEdit
				.setEnabled((dataState == DataState.BROWSE && (jTabbedPane
						.getSelectedIndex() == 0))
						|| ((dataState != DataState.READONLY) && isImgExgPageAndExistData())); // 修改
		btnDelete.setEnabled((dataState != DataState.READONLY)
				&& isImgExgPageAndExistData()); // 删除
		btnSave.setEnabled((jTabbedPane.getSelectedIndex() == 0)
				&& dataState != DataState.READONLY
				&& dataState != DataState.BROWSE);// 保存
		tfEmsApprNo
				.setEditable(dataState != DataState.BROWSE
						&& !DgEmsEdiTr.this.isChange
						&& dataState != DataState.READONLY);
		cmbDeclareErType
				.setEnabled(dataState != DataState.BROWSE
						&& !DgEmsEdiTr.this.isChange
						&& dataState != DataState.READONLY);
		btnSelDistrict.setEnabled(dataState != DataState.BROWSE
				&& dataState != DataState.READONLY);
		tfTel.setEditable(dataState != DataState.BROWSE
				&& dataState != DataState.READONLY);
		/*
		 * tfAddress.setEditable(dataState != DataState.BROWSE &&
		 * !DgEmsEdiTr.this.isChange && dataState != DataState.READONLY);
		 */
		tfAddress.setEditable(dataState != DataState.BROWSE
				&& dataState != DataState.READONLY);
		tfMachName.setEditable(dataState != DataState.BROWSE
				&& dataState != DataState.READONLY);
		tfTradeName.setEditable(dataState != DataState.BROWSE
				&& dataState != DataState.READONLY);
		ftfInvestAmount.setEditable(dataState != DataState.BROWSE
				&& dataState != DataState.READONLY);
		ftfMachinAbility.setEditable(dataState != DataState.BROWSE
				&& dataState != DataState.READONLY);
		btnSelCurr.setEnabled(dataState != DataState.BROWSE
				&& dataState != DataState.READONLY);
		cmbCoType.setEnabled(dataState != DataState.BROWSE
				&& dataState != DataState.READONLY);
		btnSelCustom.setEnabled(dataState != DataState.BROWSE
				&& dataState != DataState.READONLY);
		btnSelRedDep.setEnabled(dataState != DataState.BROWSE
				&& dataState != DataState.READONLY);
		tfNote.setEditable(dataState != DataState.BROWSE
				&& dataState != DataState.READONLY);
		cmbBeginDate
				.setEnabled(dataState != DataState.BROWSE
						&& !DgEmsEdiTr.this.isChange
						&& dataState != DataState.READONLY);
		cmbEndDate.setEnabled(dataState != DataState.BROWSE
				&& dataState != DataState.READONLY);
	}

	private boolean isImgExgPageAndExistData() {
		if ((jTabbedPane.getSelectedIndex() == 1)
				&& (tableModelImg.getRowCount() > 0)) {
			return true;
		}
		if ((jTabbedPane.getSelectedIndex() == 2)
				&& (tableModelExg.getRowCount() > 0)) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * This method initializes jButton
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("增加(半成品)");
			jButton.setLocation(new Point(85, 1));
			jButton.setPreferredSize(new Dimension(80, 30));
			jButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jTabbedPane.getSelectedIndex() == 1)// 料件
					{
						if (isH2kTrInput) {// 当为True时进入手工模式
							DgEmsEdiTrImgExg dgEmsEdiTrImgExg = new DgEmsEdiTrImgExg();
							dgEmsEdiTrImgExg.setImg(true);
							dgEmsEdiTrImgExg.setEmsEdiTrHead(emseditr);
							dgEmsEdiTrImgExg.setTableModel(tableModelImg);
							dgEmsEdiTrImgExg.setAdd(true);
							dgEmsEdiTrImgExg.setVisible(true);
						} else {
							EmsEdiTrImg emsEdiTrImg = null;
							List list = (List) CommonQuery.getInstance()
									.getImg4(false, emseditr,
											MaterielType.MATERIEL);
							if (list == null || list.isEmpty())
								return;
							for (int i = 0; i < list.size(); i++) {
								emsEdiTrImg = (EmsEdiTrImg) list.get(i);
								emsEdiTrImg.setEmsEdiTrHead(emseditr);
								emsEdiTrImg.setModifyTimes(emseditr.getModifyTimes()); // 变更次数
								emsEdiTrImg
										.setModifyMark(ModifyMarkState.ADDED);
								emsEdiTrImg.setChangeDate(new Date());// 修改日期
								emsEdiTrImg.setCompany(CommonVars.getCurrUser()
										.getCompany());
								emsEdiTrImg = manualdeclearAction
										.saveEmsEdiTrImg(new Request(CommonVars
												.getCurrUser()), emsEdiTrImg);
								tableModelImg.addRow(emsEdiTrImg);
							}
						}
					} else if (jTabbedPane.getSelectedIndex() == 2) {// 成品
						if (isH2kTrInput) {// 当为True时进入手工模式
							DgEmsEdiTrImgExg dgEmsEdiTrImgExg = new DgEmsEdiTrImgExg();
							dgEmsEdiTrImgExg.setImg(false);
							dgEmsEdiTrImgExg.setEmsEdiTrHead(emseditr);
							dgEmsEdiTrImgExg.setTableModel(tableModelExg);
							dgEmsEdiTrImgExg.setAdd(true);
							dgEmsEdiTrImgExg.setVisible(true);
						} else {//
							EmsEdiTrExg emsEdiTrExg = null;
							List list = (List) CommonQuery.getInstance()
									.getExg4(false, emseditr,
											MaterielType.FINISHED_PRODUCT);
							if (list == null || list.isEmpty())
								return;
							for (int i = 0; i < list.size(); i++) {
								emsEdiTrExg = (EmsEdiTrExg) list.get(i);
								emsEdiTrExg.setEmsEdiTrHead(emseditr);
								emsEdiTrExg.setModifyTimes(emseditr.getModifyTimes());
								emsEdiTrExg
										.setModifyMark(ModifyMarkState.ADDED);
								emsEdiTrExg.setChangeDate(new Date());
								emsEdiTrExg.setCompany(CommonVars.getCurrUser()
										.getCompany());
								emsEdiTrExg = manualdeclearAction
										.saveEmsEdiTrExg(new Request(CommonVars
												.getCurrUser()), emsEdiTrExg);
								tableModelExg.addRow(emsEdiTrExg);
							}
						}
					}
					setState();
				}
			});

		}
		return jButton;
	}

	/**
	 * @return Returns the isHistoryChange.
	 */
	public boolean isHistoryChange() {
		return isHistoryChange;
	}

	/**
	 * @param isHistoryChange
	 *            The isHistoryChange to set.
	 */
	public void setHistoryChange(boolean isHistoryChange) {
		this.isHistoryChange = isHistoryChange;
	}
} // @jve:decl-index=0:parse,visual-constraint="30,10"

