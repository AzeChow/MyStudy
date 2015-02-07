/*
 * Created on 2004-7-27
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.enc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.custombase.action.CustomBaseAction;
import com.bestway.bcus.custombase.entity.basecode.Brief;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.bcus.custombase.entity.parametercode.Transf;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.enc.entity.ApplyToCustomsBillList;
import com.bestway.bcus.enc.entity.AtcMergeAfterComInfo;
import com.bestway.bcus.enc.entity.AtcMergeBeforeComInfo;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kExg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kImg;
import com.bestway.bcus.system.action.SystemAction;
import com.bestway.bcus.system.entity.Company;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.footer.JFooterScrollPane;
import com.bestway.client.util.footer.JFooterTable;
import com.bestway.client.util.footer.TableFooterType;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.KeyAdapterControl;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
@SuppressWarnings("unchecked")
public class DgApplyToCustomsBillList extends JDialogBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ManualDeclareAction manualDeclareAction = null;
	
	private MaterialManageAction materialManageAction = null;

	private JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JTabbedPane jTabbedPane = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JButton btnAdd = null;

	private JButton btnEdit = null;

	private JButton btnDelete = null;

	private JButton btnSave = null;

	private JButton btnCancel = null;

	private JComboBox cbbImpExpType = null;

	private JTextField tfBillListNo = null;

	private JTextField tfTradeName = null;

	private JComboBox cbbMaterielProductFlag = null;

	private JComboBox cbbTransportMode = null;

	private JTextField tfCreater = null;

	private JTextField tfCreateCompanyName = null;

	private JTextField tfMemo = null;

	private JTextField tfEmsNo = null;

	private JTextField tfTradeCode = null;

	private JTextField tfMaterialNum = null;

	private JTextField tfListState = null;

	private JFooterTable tbMergeAfterCommInfo = null;

	private JFooterScrollPane jScrollPane = null;

	private DefaultFormatterFactory defaultFormatterFactory = null; // @jve:decl-index=0:parse

	private DateFormatter dateFormatter = null; // @jve:decl-index=0:parse

	private DateFormatSymbols dateFormatSymbols = null; // @jve:decl-index=0:parse

	private DateFormatter dateFormatter1 = null; // @jve:decl-index=0:parse

	private JTextField tfListDeclareDate = null;

	private JComboBox cbbTradeMode = null;

	// private JTableListModel billListModel = null;

	private int impExpFlag = ApplyToCustomsBillList.IMPORT;

	private EmsHeadH2k emsHeadH2k = null;// 电子帐册 // @jve:decl-index=0:

	private EncAction encAction = null;

	private CustomBaseAction customBaseAction = null;

	private JTableListModel billListTableModel = null;

	private JTableListModel beforeCommInfoTableModel = null;

	private JTableListModel afterCommInfoTableModel = null;

	private ApplyToCustomsBillList applyToCustomsBillList = null; // @jve:decl-index=0:

	private List beforeTableDataSource = null;

	private List afterTableDataSource = null;

	private int dataState = DataState.BROWSE;

	private JFooterTable tbMergeBeforeCommInfo = null;

	private JFooterScrollPane jScrollPane1 = null;

	private JSplitPane jSplitPane = null;

	private boolean isTransferAtc = false;// 是否产生报关单

	private JComboBox jComboBox = null;

	private JComboBox jComboBox1 = null;

	private JButton jButton = null;

	private JLabel jLabel3 = null;

	private JTextField jTextField = null;

	private JLabel jLabel18 = null;

	private JTextField jTextField1 = null;

	private JButton jButton1 = null;

	private JCheckBox jCheckBox = null;

	private boolean isMerger = false;

	private AtcMergeAfterComInfo atcAfter = null;

	private JLabel jLabel19 = null;

	private JComboBox jComboBox2 = null;

	private SystemAction systemAction = null;

	private JLabel jLabel20 = null;

	private JCalendarComboBox jCalendarComboBox = null;

	private JButton jButton2 = null;

	private JButton jButton3 = null;

	private boolean ok = false;

	private JButton btneffect = null;

	private JButton btnNoteffect = null;

	private JLabel jLabel21 = null;

	private JTextField jTextField2 = null;

	private JButton btnPrint = null;

	private JLabel lbScmCoc = null;

	private JComboBox jComboBox3 = null;

	private JPopupMenu jPopupMenu = null;

	private JMenuItem jMenuItem = null;

	public boolean isTransferAtc() {
		return isTransferAtc;
	}

	public void setTransferAtc(boolean isTransferAtc) {
		this.isTransferAtc = isTransferAtc;
	}

	public EmsHeadH2k getEmsHeadH2k() {
		return emsHeadH2k;
	}

	public void setEmsHeadH2k(EmsHeadH2k emsHeadH2k) {
		this.emsHeadH2k = emsHeadH2k;
	}

	public JTableListModel getBillListTableModel() {
		return billListTableModel;
	}

	public void setBillListTableModel(JTableListModel tableModel) {
		this.billListTableModel = tableModel;
	}

	public int getDataState() {
		return dataState;
	}

	public void setDataState(int dataState) {
		this.dataState = dataState;
	}

	public int getImpExpFlag() {
		return impExpFlag;
	}

	public void setImpExpFlag(int impExpFlag) {
		this.impExpFlag = impExpFlag;
	}

	public DgApplyToCustomsBillList() {
		super();
		encAction = (EncAction) CommonVars.getApplicationContext().getBean(
				"encAction");
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		manualDeclareAction = (ManualDeclareAction) CommonVars
		.getApplicationContext().getBean("manualdeclearAction");
		customBaseAction = (CustomBaseAction) CommonVars
				.getApplicationContext().getBean("customBaseAction");
		systemAction = (SystemAction) CommonVars.getApplicationContext()
				.getBean("systemAction");
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("报关清单编辑");
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setSize(785, 541);
		jCheckBox.setSelected(true);
	}

	class FocusActionListner extends KeyAdapter {
		Component component;

		FocusActionListner(Component component) {
			this.component = component;
		}

		public void keyPressed(KeyEvent e) {
			KeyAdapterControl.setAddListener(true);
			if (e.getKeyCode() == 10) {
				component.requestFocus();
			}
		}
	}

	public void setVisible(boolean b) {
		if (b) {
			initUIComponents();
			showPrimalData();
			setState();
			initAfterTable();
			if (afterTableDataSource != null && afterTableDataSource.size() > 0) {
				atcAfter = (AtcMergeAfterComInfo) afterTableDataSource.get(0);
			}
			initBeforeTable(isMerger, atcAfter);
		}
		super.setVisible(b);
	}

	public void setVisible(String billNo, String emsNo, int impExpFlag) {
		ApplyToCustomsBillList billHead = encAction.getApplyBillList(
				new Request(CommonVars.getCurrUser()), billNo, impExpFlag);
		applyToCustomsBillList = billHead;
		this.setDataState(DataState.BROWSE);
		this.setImpExpFlag(impExpFlag);
		//encAction.gete
		this.setEmsHeadH2k(emsHeadH2k);
		this.setVisible(true);
	}
	
	/**
	 * 显示电子帐册表头
	 * 
	 * @return
	 */

	private EmsHeadH2k getEmsHeadH2ks() {
		EmsHeadH2k ems = null;
		List list = manualDeclareAction.findEmsHeadH2k(new Request(CommonVars
				.getCurrUser(), true));
		for (int i = 0; i < list.size(); i++) {
			ems = (EmsHeadH2k) list.get(i);
			if (ems.getDeclareState().equals(DeclareState.PROCESS_EXE)) {
				return ems;
			}
		}
		return ems;
	}

	private Brief getBriefByCode(String code) {
		List list = customBaseAction.findBrief("code", code);
		if (list.size() > 0) {
			return (Brief) list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 当新增或删除时，显示最初数据
	 */
	private void showPrimalData() {
		if (dataState == DataState.ADD) {
			this.setTransferAtc(false);
			applyToCustomsBillList = new ApplyToCustomsBillList();
			applyToCustomsBillList.setMaterialNum(0);
			applyToCustomsBillList.setListNo(getMaxBillListNo());
			applyToCustomsBillList.setImpExpFlag(Integer
					.valueOf(getImpExpFlag()));
			applyToCustomsBillList.setEmsHeadH2k(emsHeadH2k.getEmsNo());
			applyToCustomsBillList.setTradeCode(emsHeadH2k.getTradeCode());
			applyToCustomsBillList.setTradeName(emsHeadH2k.getTradeName());
			applyToCustomsBillList.setCreatedUser(CommonVars.getCurrUser());
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			applyToCustomsBillList.setCreatedDate(java.sql.Date
					.valueOf(dateFormat.format(new Date())));
			applyToCustomsBillList
					.setCreatedCompany(getBriefByCode(((Company) CommonVars
							.getCurrUser().getCompany()).getCode()));
			applyToCustomsBillList.setListState(Integer
					.valueOf(ApplyToCustomsBillList.DRAFT));
			applyToCustomsBillList
					.setDeclarationCompany(CommonVars.getOther() == null ? null
							: CommonVars.getOther().getDeclarationCompany());
		} else if (dataState == DataState.EDIT || dataState == DataState.BROWSE) {
			if(applyToCustomsBillList == null) {
				applyToCustomsBillList = (ApplyToCustomsBillList) billListTableModel
				.getCurrentRow();
			}
			if ( // applyToCustomsBillList.getListState().intValue() ==
			// ApplyToCustomsBillList.GENERATED_CUSTOMS_DECLARATION ||
			applyToCustomsBillList.getListState().intValue() == ApplyToCustomsBillList.PASSED) {
				this.setTransferAtc(true);
			}
		}
		showData(applyToCustomsBillList);
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
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJTabbedPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
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
			jToolBar.add(getBtnAdd());
			jToolBar.add(getJButton1());
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnSave());
			jToolBar.add(getBtnPrint());
			jToolBar.add(getBtnDelete());
			jToolBar.add(getJButton());
			jToolBar.add(getBtnCancel());
			jToolBar.add(getJButton2());
			jToolBar.add(getJButton3());
			jToolBar.add(getBtneffect());
			jToolBar.add(getBtnNoteffect());
			jToolBar.add(getJCheckBox());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * jTabbedPane.addTab(null, null, getJPanel1(), null);
	 * jTabbedPane.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
			jTabbedPane.addTab("基本信息", null, getJPanel1(), null);
			jTabbedPane.addTab("商品信息", null, getJPanel(), null);
			jTabbedPane
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							DgApplyToCustomsBillList.this.setCursor(new Cursor(
									Cursor.WAIT_CURSOR));
							dataState = DataState.BROWSE;
							if (jTabbedPane.getSelectedIndex() == 0) {
								if (applyToCustomsBillList != null) {
									showPrimalData();
								} else {
									showEmptyData();
								}
							} else {
								initAfterTable();
								if (afterTableDataSource != null
										&& afterTableDataSource.size() > 0) {
									atcAfter = (AtcMergeAfterComInfo) afterTableDataSource
											.get(0);
								}
								initBeforeTable(isMerger, atcAfter);
							}
							setState();
							DgApplyToCustomsBillList.this.setCursor(new Cursor(
									Cursor.DEFAULT_CURSOR));
						}
					});
		}
		return jTabbedPane;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
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
			lbScmCoc = new JLabel();
			lbScmCoc.setBounds(new Rectangle(395, 346, 89, 24));
			lbScmCoc.setForeground(Color.blue);
			lbScmCoc.setText("客户");
			jLabel21 = new JLabel();
			jLabel21.setBounds(new Rectangle(395, 133, 89, 24));
			jLabel21.setText("清单编号");
			jLabel21.setForeground(java.awt.Color.blue);
			jLabel20 = new JLabel();
			jLabel20.setBounds(new Rectangle(62, 396, 308, 32));
			jLabel20.setForeground(java.awt.Color.red);
			jLabel20.setText("注：蓝色字体为申报海关栏位");
			jLabel19 = new JLabel();
			jLabel19.setBounds(new Rectangle(98, 316, 96, 21));
			jLabel19.setForeground(java.awt.Color.blue);
			jLabel19.setText("申报单位");
			jLabel18 = new JLabel();
			jLabel18.setBounds(new Rectangle(395, 283, 89, 24));
			jLabel18.setForeground(java.awt.Color.blue);
			jLabel18.setText("录入单位代码");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(98, 134, 96, 21));
			jLabel3.setForeground(java.awt.Color.blue);
			jLabel3.setText("统一编号");
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
			javax.swing.JLabel jLabel2 = new JLabel();
			javax.swing.JLabel jLabel1 = new JLabel();
			javax.swing.JLabel jLabel = new JLabel();
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jLabel.setBounds(98, 45, 96, 21);
			jLabel.setText("进出口类型");
			jLabel1.setBounds(98, 75, 96, 21);
			jLabel1.setForeground(java.awt.Color.blue);
			jLabel1.setText("企业内部清单编号");
			jLabel2.setBounds(98, 104, 96, 21);
			jLabel2.setForeground(java.awt.Color.blue);
			jLabel2.setText("经营单位名称");
			jLabel4.setBounds(98, 165, 96, 21);
			jLabel4.setForeground(java.awt.Color.blue);
			jLabel4.setText("料件成品标志");
			jLabel5.setBounds(98, 196, 96, 21);
			jLabel5.setForeground(java.awt.Color.blue);
			jLabel5.setText("申报地海关");
			jLabel6.setBounds(98, 227, 96, 21);
			jLabel6.setForeground(java.awt.Color.blue);
			jLabel6.setText("运输方式");
			jLabel7.setBounds(98, 257, 96, 21);
			jLabel7.setForeground(java.awt.Color.blue);
			jLabel7.setText("录入员代码");
			jLabel8.setBounds(98, 287, 96, 21);
			jLabel8.setForeground(java.awt.Color.blue);
			jLabel8.setText("录入单位名称");
			jLabel9.setBounds(98, 346, 96, 21);
			jLabel9.setForeground(java.awt.Color.blue);
			jLabel9.setText("备注");
			jLabel10.setBounds(395, 44, 89, 24);
			jLabel10.setForeground(java.awt.Color.blue);
			jLabel10.setText("电子帐册编号");
			jLabel11.setBounds(395, 74, 89, 24);
			jLabel11.setForeground(java.awt.Color.blue);
			jLabel11.setText("经营单位编码");
			jLabel12.setBounds(395, 103, 89, 24);
			jLabel12.setForeground(java.awt.Color.blue);
			jLabel12.setText("清单申报日期");
			jLabel13.setBounds(395, 160, 89, 24);
			jLabel13.setForeground(java.awt.Color.blue);
			jLabel13.setText("商品项数");
			jLabel14.setBounds(395, 191, 89, 24);
			jLabel14.setForeground(java.awt.Color.blue);
			jLabel14.setText("进/出口岸");
			jLabel15.setBounds(395, 222, 89, 24);
			jLabel15.setForeground(java.awt.Color.blue);
			jLabel15.setText("监管方式");
			jLabel16.setBounds(395, 253, 89, 24);
			jLabel16.setForeground(java.awt.Color.blue);
			jLabel16.setText("录入日期");
			jLabel17.setBounds(395, 313, 89, 24);
			jLabel17.setForeground(java.awt.Color.blue);
			jLabel17.setText("清单状态");
			jPanel1.add(jLabel, null);
			jPanel1.add(jLabel1, null);
			jPanel1.add(jLabel2, null);
			jPanel1.add(jLabel4, null);
			jPanel1.add(jLabel5, null);
			jPanel1.add(jLabel6, null);
			jPanel1.add(jLabel7, null);
			jPanel1.add(jLabel8, null);
			jPanel1.add(jLabel9, null);
			jPanel1.add(jLabel10, null);
			jPanel1.add(jLabel11, null);
			jPanel1.add(jLabel12, null);
			jPanel1.add(jLabel13, null);
			jPanel1.add(jLabel14, null);
			jPanel1.add(jLabel15, null);
			jPanel1.add(jLabel16, null);
			jPanel1.add(jLabel17, null);
			jPanel1.add(getCbbImpExpType(), null);
			jPanel1.add(getTfBillListNo(), null);
			jPanel1.add(getTfTradeName(), null);
			jPanel1.add(getCbbMaterielProductFlag(), null);
			jPanel1.add(getCbbTransportMode(), null);
			jPanel1.add(getTfCreater(), null);
			jPanel1.add(getTfCreateCompanyName(), null);
			jPanel1.add(getTfMemo(), null);
			jPanel1.add(getTfEmsNo(), null);
			jPanel1.add(getTfTradeCode(), null);
			jPanel1.add(getTfMaterialNum(), null);
			jPanel1.add(getTfListState(), null);
			jPanel1.add(getTfListDeclareDate(), null);
			jPanel1.add(getCbbTradeMode(), null);
			jPanel1.add(getJComboBox(), null);
			jPanel1.add(getJComboBox1(), null);
			jPanel1.add(jLabel3, null);
			jPanel1.add(getJTextField(), null);
			jPanel1.add(jLabel18, null);
			jPanel1.add(getJTextField1(), null);
			jPanel1.add(jLabel19, null);
			jPanel1.add(getJComboBox2(), null);
			jPanel1.add(jLabel20, null);
			jPanel1.add(getJCalendarComboBox(), null);
			jPanel1.add(jLabel21, null);
			jPanel1.add(getJTextField2(), null);
			jPanel1.add(lbScmCoc, null);
			jPanel1.add(getJComboBox3(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes btnAdd
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setText("新增");
			btnAdd.setPreferredSize(new Dimension(55, 30));
			btnAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jTabbedPane.getSelectedIndex() == 0) {
						dataState = DataState.ADD;
						showPrimalData();
						setState();
					} else {
						if (applyToCustomsBillList == null) {
							JOptionPane.showMessageDialog(
									DgApplyToCustomsBillList.this, "请先新增基本信息！",
									"提示", 0);
							return;
						}
						Integer materielProductFlag = cbbMaterielProductFlag
								.getSelectedItem() == null ? null : Integer
								.valueOf(((ItemProperty) cbbMaterielProductFlag
										.getSelectedItem()).getCode());
						List list = CommonQuery.getInstance()
								.getTempDeclaredCommInfo(
										applyToCustomsBillList,
										materielProductFlag);
						if (list != null) {
							System.out.println("选择的列表行数为:" + list.size());
							applyToCustomsBillList = encAction
									.saveAtcMergeBeforeComInfo(new Request(
											CommonVars.getCurrUser()), list,
											applyToCustomsBillList);
							billListTableModel
									.updateRow(applyToCustomsBillList);
							initAfterTable();
							if (afterCommInfoTableModel != null
									&& afterCommInfoTableModel.getCurrentRow() != null) {
								atcAfter = (AtcMergeAfterComInfo) afterCommInfoTableModel
										.getCurrentRow();
							}
							initBeforeTable(isMerger, atcAfter);
						}
					}
				}
			});
		}
		return btnAdd;
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
			btnEdit.setPreferredSize(new Dimension(55, 30));
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jTabbedPane.getSelectedIndex() == 0) {
						dataState = DataState.EDIT;
						showPrimalData();
						setState();
					} else {
						if (beforeCommInfoTableModel.getCurrentRow() == null) {
							JOptionPane.showMessageDialog(
									DgApplyToCustomsBillList.this, "请选择要修改的资料",
									"提示", 0);
							return;
						}
						DgAtcMergeCommInfo dgAtcMergeCommInfo = new DgAtcMergeCommInfo(
								beforeCommInfoTableModel);
						dgAtcMergeCommInfo.setVisible(true);
						if (dgAtcMergeCommInfo.isOk()) {
							AtcMergeBeforeComInfo beforeComInfo = (AtcMergeBeforeComInfo) beforeCommInfoTableModel
									.getCurrentRow();
							afterCommInfoTableModel.updateRow(beforeComInfo
									.getAfterComInfo());
						}
						refreshAfterStatInfo();
					}
				}
			});
		}
		return btnEdit;
	}

	/**
	 * This method initializes btnDelete
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setText("归并前删除");
			btnDelete.setPreferredSize(new Dimension(72, 30));
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (beforeCommInfoTableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(
								DgApplyToCustomsBillList.this, "请选择要删除的归并前数据",
								"提示", 0);
						return;
					}
					if (JOptionPane.showConfirmDialog(
							DgApplyToCustomsBillList.this, "是否确定删除归并前数据!",
							"提示", 0) != 0) {
						return;
					}
					ApplyToCustomsBillList billList = encAction
							.deleteAtcMergeBeforeComInfo(new Request(CommonVars
									.getCurrUser()), beforeCommInfoTableModel
									.getCurrentRows());
					billListTableModel.updateRow(billList);// 修改表头
					if (afterCommInfoTableModel != null
							&& afterCommInfoTableModel.getCurrentRow() != null) {
						atcAfter = (AtcMergeAfterComInfo) afterCommInfoTableModel
								.getCurrentRow();
					}
					initBeforeTable(isMerger, atcAfter);
					refreshAfterStatInfo();
				}
			});
		}
		return btnDelete;
	}

	private void refreshAfterStatInfo() {
		if (afterCommInfoTableModel == null
				|| afterCommInfoTableModel.getList().size() <= 0) {
			return;
		}
		List list = afterCommInfoTableModel.getList();
		AtcMergeAfterComInfo totalAfterInfo = null;
		for (int i = 0; i < list.size(); i++) {
			AtcMergeAfterComInfo afterCommInfo = (AtcMergeAfterComInfo) list
					.get(i);
			if (afterCommInfo.getComplexName() != null
					&& afterCommInfo.getComplexName().indexOf("合计") >= 0) {
				totalAfterInfo = afterCommInfo;
				list.remove(i);
				break;
			}
		}
		if (totalAfterInfo != null) {
			totalAfterInfo.setDeclaredAmount(0.0);
			totalAfterInfo.setLegalAmount(0.0);
			totalAfterInfo.setSecondLegalAmount(0.0);
			totalAfterInfo.setGrossWeight(0.0);
			totalAfterInfo.setNetWeight(0.0);
			totalAfterInfo.setPiece(0);
			totalAfterInfo.setTotalPrice(0.0);
			totalAfterInfo.setWorkUsd(0.0);
			for (int i = 0; i < list.size(); i++) {
				AtcMergeAfterComInfo afterCommInfo = (AtcMergeAfterComInfo) list
						.get(i);
				totalAfterInfo
						.setDeclaredAmount(CommonVars
								.getDoubleExceptNull(totalAfterInfo
										.getDeclaredAmount())
								+ CommonVars.getDoubleExceptNull(afterCommInfo
										.getDeclaredAmount()));
				totalAfterInfo.setLegalAmount(CommonVars
						.getDoubleExceptNull(totalAfterInfo.getLegalAmount())
						+ CommonVars.getDoubleExceptNull(afterCommInfo
								.getLegalAmount()));
				totalAfterInfo.setSecondLegalAmount(CommonVars
						.getDoubleExceptNull(totalAfterInfo
								.getSecondLegalAmount())
						+ CommonVars.getDoubleExceptNull(afterCommInfo
								.getSecondLegalAmount()));
				totalAfterInfo.setGrossWeight(CommonVars
						.getDoubleExceptNull(totalAfterInfo.getGrossWeight())
						+ CommonVars.getDoubleExceptNull(afterCommInfo
								.getGrossWeight()));
				totalAfterInfo.setNetWeight(CommonVars
						.getDoubleExceptNull(totalAfterInfo.getNetWeight())
						+ CommonVars.getDoubleExceptNull(afterCommInfo
								.getNetWeight()));
				totalAfterInfo.setPiece((totalAfterInfo.getPiece() == null ? 0
						: totalAfterInfo.getPiece())
						+ (afterCommInfo.getPiece() == null ? 0 : afterCommInfo
								.getPiece()));
				totalAfterInfo.setTotalPrice(CommonVars
						.getDoubleExceptNull(totalAfterInfo.getTotalPrice())
						+ CommonVars.getDoubleExceptNull(afterCommInfo
								.getTotalPrice()));
				totalAfterInfo.setWorkUsd(CommonVars
						.getDoubleExceptNull(totalAfterInfo.getWorkUsd())
						+ CommonVars.getDoubleExceptNull(afterCommInfo
								.getWorkUsd()));
			}
			list.add(totalAfterInfo);
			afterCommInfoTableModel.setList(list);
		}
	}

	/**
	 * This method initializes btnSave
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("保存");
			btnSave.setPreferredSize(new Dimension(55, 30));
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					saveApplyToCustomsBillList();
				}
			});
		}
		return btnSave;
	}

	/**
	 * This method initializes btnCancel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText("取消");
			btnCancel.setPreferredSize(new Dimension(55, 30));
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dataState = DataState.BROWSE;
					if (applyToCustomsBillList != null) {
						showPrimalData();
					} else {
						applyToCustomsBillList = null;
						showEmptyData();
					}
					setState();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * This method initializes cbbImpExpType
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbImpExpType() {
		if (cbbImpExpType == null) {
			cbbImpExpType = new JComboBox();
			cbbImpExpType.setBounds(197, 45, 190, 20);
			cbbImpExpType.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (dataState != DataState.BROWSE
							&& e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
						int impExpType = Integer
								.parseInt(((ItemProperty) cbbImpExpType
										.getSelectedItem()).getCode());
						switch (impExpType) {
						case ImpExpType.DIRECT_IMPORT:
							// cbbMaterielProductFlag.
							cbbMaterielProductFlag
									.setSelectedIndex(ItemProperty
											.getIndexByCode(
													MaterielType.MATERIEL,
													cbbMaterielProductFlag));
							break;
						case ImpExpType.TRANSFER_FACTORY_IMPORT:
							cbbMaterielProductFlag
									.setSelectedIndex(ItemProperty
											.getIndexByCode(
													MaterielType.MATERIEL,
													cbbMaterielProductFlag));
							break;
						case ImpExpType.BACK_FACTORY_REWORK:
							cbbMaterielProductFlag
									.setSelectedIndex(ItemProperty
											.getIndexByCode(
													MaterielType.FINISHED_PRODUCT,
													cbbMaterielProductFlag));
							break;
						case ImpExpType.GENERAL_TRADE_IMPORT:
							cbbMaterielProductFlag
									.setSelectedIndex(ItemProperty
											.getIndexByCode(
													MaterielType.MATERIEL,
													cbbMaterielProductFlag));
							break;
						case ImpExpType.DIRECT_EXPORT:
							cbbMaterielProductFlag
									.setSelectedIndex(ItemProperty
											.getIndexByCode(
													MaterielType.FINISHED_PRODUCT,
													cbbMaterielProductFlag));
							break;
						case ImpExpType.TRANSFER_FACTORY_EXPORT:
							cbbMaterielProductFlag
									.setSelectedIndex(ItemProperty
											.getIndexByCode(
													MaterielType.FINISHED_PRODUCT,
													cbbMaterielProductFlag));
							break;
						case ImpExpType.BACK_MATERIEL_EXPORT:
							cbbMaterielProductFlag
									.setSelectedIndex(ItemProperty
											.getIndexByCode(
													MaterielType.MATERIEL,
													cbbMaterielProductFlag));
							break;
						case ImpExpType.REWORK_EXPORT:
							cbbMaterielProductFlag
									.setSelectedIndex(ItemProperty
											.getIndexByCode(
													MaterielType.FINISHED_PRODUCT,
													cbbMaterielProductFlag));
							break;
						case ImpExpType.REMIAN_MATERIAL_BACK_PORT:
							cbbMaterielProductFlag
									.setSelectedIndex(ItemProperty
											.getIndexByCode(
													MaterielType.MATERIEL,
													cbbMaterielProductFlag));
							break;
						case ImpExpType.GENERAL_TRADE_EXPORT:
							cbbMaterielProductFlag
									.setSelectedIndex(ItemProperty
											.getIndexByCode(
													MaterielType.FINISHED_PRODUCT,
													cbbMaterielProductFlag));
							break;
						case 25: // 进料成品退换
							cbbMaterielProductFlag
									.setSelectedIndex(ItemProperty
											.getIndexByCode(
													MaterielType.FINISHED_PRODUCT,
													cbbMaterielProductFlag));
							break;
						case 9: // 边角料内销
							cbbMaterielProductFlag
									.setSelectedIndex(ItemProperty
											.getIndexByCode(
													MaterielType.MATERIEL,
													cbbMaterielProductFlag));
							break;

						}
					}
				}
			});

		}
		return cbbImpExpType;
	}

	/**
	 * This method initializes tfBillListNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfBillListNo() {
		if (tfBillListNo == null) {
			tfBillListNo = new JTextField();
			tfBillListNo.setBounds(197, 75, 190, 20);
			// tfBillListNo.setEditable(false);
		}
		return tfBillListNo;
	}

	/**
	 * This method initializes tfTradeName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfTradeName() {
		if (tfTradeName == null) {
			tfTradeName = new JTextField();
			tfTradeName.setBounds(197, 104, 190, 20);
			tfTradeName.setHorizontalAlignment(javax.swing.JTextField.LEFT);
			tfTradeName.setText("");
			tfTradeName.setEditable(false);
		}
		return tfTradeName;
	}

	/**
	 * This method initializes cbbMaterielProductFlag
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbMaterielProductFlag() {
		if (cbbMaterielProductFlag == null) {
			cbbMaterielProductFlag = new JComboBox();
			cbbMaterielProductFlag.setBounds(197, 165, 190, 20);
		}
		return cbbMaterielProductFlag;
	}

	/**
	 * This method initializes cbbTransportMode
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbTransportMode() {
		if (cbbTransportMode == null) {
			cbbTransportMode = new JComboBox();
			cbbTransportMode.setBounds(197, 227, 190, 20);
		}
		return cbbTransportMode;
	}

	/**
	 * This method initializes tfCreater
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCreater() {
		if (tfCreater == null) {
			tfCreater = new JTextField();
			tfCreater.setBounds(197, 257, 190, 20);
			tfCreater.setEditable(false);
		}
		return tfCreater;
	}

	/**
	 * This method initializes tfCreateCompanyName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCreateCompanyName() {
		if (tfCreateCompanyName == null) {
			tfCreateCompanyName = new JTextField();
			tfCreateCompanyName.setBounds(197, 287, 190, 20);
			tfCreateCompanyName.setEditable(false);
		}
		return tfCreateCompanyName;
	}

	/**
	 * This method initializes tfMemo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfMemo() {
		if (tfMemo == null) {
			tfMemo = new JTextField();
			tfMemo.setBounds(197, 347, 190, 20);
		}
		return tfMemo;
	}

	/**
	 * This method initializes tfEmsNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfEmsNo() {
		if (tfEmsNo == null) {
			tfEmsNo = new JTextField();
			tfEmsNo.setBounds(490, 44, 190, 23);
			// tfEmsNo.setEditable(false);
		}
		return tfEmsNo;
	}

	/**
	 * This method initializes tfTradeCode
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfTradeCode() {
		if (tfTradeCode == null) {
			tfTradeCode = new JTextField();
			tfTradeCode.setBounds(490, 74, 190, 23);
			tfTradeCode.setEditable(false);
		}
		return tfTradeCode;
	}

	/**
	 * This method initializes tfMaterialNum
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfMaterialNum() {
		if (tfMaterialNum == null) {
			tfMaterialNum = new JTextField();
			tfMaterialNum.setBounds(490, 160, 190, 23);
			// tfMaterialNum.setEditable(false);
		}
		return tfMaterialNum;
	}

	/**
	 * This method initializes tfListState
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfListState() {
		if (tfListState == null) {
			tfListState = new JTextField();
			tfListState.setBounds(490, 313, 190, 23);
			tfListState.setEditable(false);
		}
		return tfListState;
	}

	/**
	 * This method initializes tbMergeAfterCommInfo
	 * 
	 * @return javax.swing.JTable
	 */
	private JFooterTable getTbMergeAfterCommInfo() {
		if (tbMergeAfterCommInfo == null) {
			tbMergeAfterCommInfo = new JFooterTable();
			tbMergeAfterCommInfo.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}

							if (afterCommInfoTableModel != null
									&& afterCommInfoTableModel.getCurrentRow() != null) {
								atcAfter = (AtcMergeAfterComInfo) afterCommInfoTableModel
										.getCurrentRow();
							}
							initBeforeTable(isMerger, atcAfter);

						}
					});
		}
		return tbMergeAfterCommInfo;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JFooterScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JFooterScrollPane();
			jScrollPane.setViewportView(getTbMergeAfterCommInfo());
			jScrollPane.setBorder(javax.swing.BorderFactory.createTitledBorder(
					null, "归并后商品信息（不可修改）",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
		}
		return jScrollPane;
	}

	/**
	 * This method initializes defaultFormatterFactory
	 * 
	 * @return javax.swing.text.DefaultFormatterFactory
	 */
	private DefaultFormatterFactory getDefaultFormatterFactory() {
		if (defaultFormatterFactory == null) {
			defaultFormatterFactory = new DefaultFormatterFactory();
			defaultFormatterFactory.setDisplayFormatter(getDateFormatter());
			defaultFormatterFactory.setEditFormatter(getDateFormatter1());
		}
		return defaultFormatterFactory;
	}

	/**
	 * This method initializes dateFormatter
	 * 
	 * @return javax.swing.text.DateFormatter
	 */
	private DateFormatter getDateFormatter() {
		if (dateFormatter == null) {
			dateFormatter = new DateFormatter();
			java.text.SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat(); // @jve:decl-index=0:parse
			simpleDateFormat1.setDateFormatSymbols(getDateFormatSymbols());
			dateFormatter.setFormat(simpleDateFormat1);
		}
		return dateFormatter;
	}

	/**
	 * This method initializes dateFormatSymbols
	 * 
	 * @return java.text.DateFormatSymbols
	 */
	private DateFormatSymbols getDateFormatSymbols() {
		if (dateFormatSymbols == null) {
			dateFormatSymbols = new DateFormatSymbols();
		}
		return dateFormatSymbols;
	}

	/**
	 * This method initializes dateFormatter1
	 * 
	 * @return javax.swing.text.DateFormatter
	 */
	private DateFormatter getDateFormatter1() {
		if (dateFormatter1 == null) {
			dateFormatter1 = new DateFormatter();
		}
		return dateFormatter1;
	}

	/**
	 * This method initializes tfListDeclareDate
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JTextField getTfListDeclareDate() {
		if (tfListDeclareDate == null) {
			tfListDeclareDate = new JTextField();
			tfListDeclareDate.setBounds(490, 103, 190, 23);
			// tfListDeclareDate.setFormatterFactory(getDefaultFormatterFactory());
			tfListDeclareDate.setEditable(false);
		}
		return tfListDeclareDate;
	}

	/**
	 * This method initializes cbbTradeMode
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbTradeMode() {
		if (cbbTradeMode == null) {
			cbbTradeMode = new JComboBox();
			cbbTradeMode.setBounds(490, 222, 190, 23);
		}
		return cbbTradeMode;
	}

	private void initUIComponents() {
		// 初始化进出口类型
		if (impExpFlag == ApplyToCustomsBillList.IMPORT) {
			this.cbbImpExpType.removeAllItems();
			this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.DIRECT_IMPORT).toString(), "料件进口"));
			this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.TRANSFER_FACTORY_IMPORT).toString(), "料件转厂"));
			this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.BACK_FACTORY_REWORK).toString(), "退厂返工"));
			this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.GENERAL_TRADE_IMPORT).toString(), "一般贸易进口"));

			this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.IMPORT_EXG_BACK).toString(), "进料成品退换"));
			this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.IMPORT_REPAIR_MATERIAL).toString(), "修理物品"));
			this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.MATERIAL_DOMESTIC_SALES).toString(), "料件内销"));

		} else {
			this.cbbImpExpType.removeAllItems();
			this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.DIRECT_EXPORT).toString(), "成品出口"));
			this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.TRANSFER_FACTORY_EXPORT).toString(), "转厂出口"));
			this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.BACK_MATERIEL_EXPORT).toString(), "退料出口"));

			this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.REWORK_EXPORT).toString(), "返工复出"));
			this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.REMIAN_MATERIAL_BACK_PORT).toString(), "边角料退港"));
			this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES).toString(),
					"边角料内销"));
			this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.GENERAL_TRADE_EXPORT).toString(), "一般贸易出口"));

			this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.EXPORT_MATERIAL_REBACK).toString(), "修理物品复出"));
			this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.EXPORT_EXG_REBACK).toString(), "进料成品退换复出"));
		}

		this.cbbImpExpType.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbImpExpType);
		this.cbbImpExpType.setSelectedIndex(-1);
		// 初始化料件/成品标志
		this.cbbMaterielProductFlag.removeAllItems();
		this.cbbMaterielProductFlag.addItem(new ItemProperty(
				MaterielType.MATERIEL, "料件"));
		this.cbbMaterielProductFlag.addItem(new ItemProperty(
				MaterielType.FINISHED_PRODUCT, "成品"));
		this.cbbMaterielProductFlag.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbMaterielProductFlag);
		this.cbbMaterielProductFlag.setSelectedIndex(-1);
		// 初始化运输方式
		this.cbbTransportMode.setModel(CustomBaseModel.getInstance()
				.getTransfModel());
		this.cbbTransportMode.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbTransportMode);
		this.cbbTransportMode.setSelectedIndex(-1);

		// 初始化客户commonBaseCodeAction
		List list = materialManageAction.findScmCocs(new Request(CommonVars
				.getCurrUser(), true));
		this.jComboBox3.setModel(new DefaultComboBoxModel(list.toArray()));
		this.jComboBox3.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 100, 170));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.jComboBox3, "code", "name", 270);

		// 保送海关
		this.jComboBox.setModel(CustomBaseModel.getInstance().getCustomModel());
		this.jComboBox.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance()
				.setComboBoxEditor(this.jComboBox);
		this.jComboBox.setSelectedIndex(-1);

		// 进出口岸
		this.jComboBox1
				.setModel(CustomBaseModel.getInstance().getCustomModel());
		this.jComboBox1.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.jComboBox1);
		this.jComboBox1.setSelectedIndex(-1);

		// 初始化贸易方式
		this.cbbTradeMode.setModel(CustomBaseModel.getInstance()
				.getTradeModel());
		this.cbbTradeMode.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbTradeMode);
		this.cbbTradeMode.setSelectedIndex(-1);

		// 初始申报单位systemAction
		List companyList = systemAction.findEnableCompanies();
		this.jComboBox2
				.setModel(new DefaultComboBoxModel(companyList.toArray()));
		this.jComboBox2.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 100, 170));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.jComboBox2, "code", "name", 270);

	}

	private String getMaxBillListNo() {
		return encAction.getApplyToCustomsBillListMaxNo(new Request(CommonVars
				.getCurrUser()));
	}

	private void fillBillListData(ApplyToCustomsBillList data) {
		if (dataState == DataState.ADD) {
			data.setMaterialNum("".equals(tfMaterialNum.getText().trim()) ? 0
					: Integer.valueOf(tfMaterialNum.getText()));
			data.setListUniteNo(jTextField.getText().trim());
			data.setListQpBillNo(jTextField2.getText().trim());
			data.setListNo(tfBillListNo.getText().trim());
			data.setCompany(CommonVars.getCurrUser().getCompany());
			data.setImpExpType(Integer
					.valueOf(((ItemProperty) this.cbbImpExpType
							.getSelectedItem()).getCode()));
			this.tfBillListNo.setText(getMaxBillListNo());
			data.setListNo(this.tfBillListNo.getText().trim());
			if (!this.tfListDeclareDate.getText().trim().equals("")) {
				try {
					data.setListDeclareDate(DateFormat.getInstance().parse(
							this.tfListDeclareDate.getText().trim()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			data.setEmsHeadH2k(tfEmsNo.getText().trim());
			data.setTradeCode(this.tfTradeCode.getText().trim());
			data.setTradeName(this.tfTradeName.getText().trim());
			data.setMaterielProductFlag(Integer
					.valueOf(((ItemProperty) this.cbbMaterielProductFlag
							.getSelectedItem()).getCode()));
			data.setDeclareCIQ((Customs) this.jComboBox.getSelectedItem());
			data.setImpExpCIQ((Customs) this.jComboBox1.getSelectedItem());
			data.setDeclarationCompany((Company) this.jComboBox2
					.getSelectedItem());
			if (this.cbbTransportMode.getSelectedItem() != null) {
				data.setTransportMode((Transf) this.cbbTransportMode
						.getSelectedItem());
			}
			if (this.jComboBox3.getSelectedItem() != null) {
				data.setScmCoc((ScmCoc) this.jComboBox3.getSelectedItem());
			}
			if (this.cbbTradeMode.getSelectedItem() != null) {
				data.setTradeMode((Trade) this.cbbTradeMode.getSelectedItem());
			}
			data.setMemos(tfMemo.getText().trim());

		} else if (dataState == DataState.EDIT) {
			data.setMaterialNum("".equals(tfMaterialNum.getText().trim()) ? 0
					: Integer.valueOf(tfMaterialNum.getText()));
			data.setListUniteNo(jTextField.getText().trim());
			data.setListQpBillNo(jTextField2.getText().trim());
			data.setEmsHeadH2k(tfEmsNo.getText().trim());
			data.setListNo(tfBillListNo.getText().trim());
			data.setDeclareCIQ((Customs) this.jComboBox.getSelectedItem());
			data.setImpExpCIQ((Customs) this.jComboBox1.getSelectedItem());
			data.setDeclarationCompany((Company) this.jComboBox2
					.getSelectedItem());
			if (this.cbbTransportMode.getSelectedItem() != null) {
				data.setTransportMode((Transf) this.cbbTransportMode
						.getSelectedItem());
			}

			if (this.jComboBox3.getSelectedItem() != null) {
				data.setScmCoc((ScmCoc) this.jComboBox3.getSelectedItem());
			}
			if (this.cbbImpExpType.getSelectedItem() != null) {
				data.setImpExpType(Integer
						.valueOf(((ItemProperty) this.cbbImpExpType
								.getSelectedItem()).getCode()));
			}
			if (this.cbbTradeMode.getSelectedItem() != null) {
				data.setTradeMode((Trade) this.cbbTradeMode.getSelectedItem());
			}
			data.setMemos(tfMemo.getText().trim());
		}
	}

	private void showData(ApplyToCustomsBillList data) {
		if (data.getImpExpType() != null) {
			System.out.println(data.getImpExpType().toString());
			this.cbbImpExpType.setSelectedIndex(ItemProperty.getIndexByCode(
					data.getImpExpType().toString(), this.cbbImpExpType));
		} else {
			this.cbbImpExpType.setSelectedIndex(-1);
		}
		this.tfEmsNo.setText(data.getEmsHeadH2k());

		this.tfTradeCode.setText(data.getTradeCode());
		this.tfTradeName.setText(data.getTradeName());
		tfTradeName.setCaretPosition(0);
		jTextField.setText(data.getListUniteNo());// 清单统一编号
		jTextField2.setText(data.getListQpBillNo());
		this.tfBillListNo.setText(data.getListNo());
		if (data.getListDeclareDate() != null) {
			this.tfListDeclareDate
					.setText(data.getListDeclareDate().toString());
		} else {
			this.tfListDeclareDate.setText("");
		}
		if (data.getMaterielProductFlag() != null) {
			this.cbbMaterielProductFlag.setSelectedIndex(ItemProperty
					.getIndexByCode(data.getMaterielProductFlag().toString(),
							this.cbbMaterielProductFlag));
		} else {
			this.cbbMaterielProductFlag.setSelectedIndex(-1);
		}
		if (data.getMaterialNum() != null) {
			this.tfMaterialNum.setText(data.getMaterialNum().toString());
		} else {
			this.tfMaterialNum.setText("");
		}
		this.jComboBox.setSelectedItem(data.getDeclareCIQ());
		this.jComboBox1.setSelectedItem(data.getImpExpCIQ());
		this.jComboBox2.setSelectedItem(data.getDeclarationCompany());

		this.cbbTransportMode.setSelectedItem(data.getTransportMode());
		this.jComboBox3.setSelectedItem(data.getScmCoc());
		this.cbbTradeMode.setSelectedItem(data.getTradeMode());
		this.tfCreater.setText(data.getCreatedUser() == null ? "" : data
				.getCreatedUser().getLoginName()); // 录入员代
		this.jCalendarComboBox.setDate(data.getCreatedDate());
		if (data.getCreatedCompany() != null) {// 录入单位名称,录入单位代码
			this.tfCreateCompanyName
					.setText(data.getCreatedCompany().getName());
			jTextField1.setText(data.getCreatedCompany().getCode());
		} else {
			this.tfCreateCompanyName.setText("");
			this.jTextField1.setText("");
		}
		tfCreateCompanyName.setCaretPosition(0);

		if (data.getListState() != null) {
			switch (data.getListState().intValue()) {
			case ApplyToCustomsBillList.DRAFT:
				this.tfListState.setText("草稿(正在录入)");
				break;
			case ApplyToCustomsBillList.ALREADY_SEND:
				this.tfListState.setText("已经申报");
				break;
			case ApplyToCustomsBillList.PASSED:
				this.tfListState.setText("审批通过");
				break;
			// case ApplyToCustomsBillList.GENERATED_CUSTOMS_DECLARATION:
			// this.tfListState.setText("已转报关单");
			// break;
			case ApplyToCustomsBillList.Effect:
				this.tfListState.setText("生效");
				break;
			}
		} else {
			this.tfListState.setText("");
		}
		this.tfMemo.setText(data.getMemos());
	}

	/**
	 * 显示空数据。
	 */
	private void showEmptyData() {
		this.cbbImpExpType.setSelectedIndex(-1);
		this.tfEmsNo.setText("");
		this.tfTradeCode.setText("");
		this.tfTradeName.setText("");
		this.tfBillListNo.setText("");
		this.tfListDeclareDate.setText("");
		this.cbbMaterielProductFlag.setSelectedIndex(-1);
		this.tfMaterialNum.setText("");
		this.jComboBox.setSelectedIndex(-1);
		this.jComboBox1.setSelectedIndex(-1);
		this.cbbTransportMode.setSelectedIndex(-1);
		this.jComboBox3.setSelectedIndex(-1);
		this.cbbTradeMode.setSelectedIndex(-1);
		this.tfCreater.setText("");
		this.jCalendarComboBox.setDate(new Date());
		this.tfCreateCompanyName.setText("");
		this.tfListState.setText("");
		this.tfMemo.setText("");
	}

	public boolean isAvailability(boolean isAva) {
		boolean isAvailability = false;

		if ((getTfMaterialNum().getText() != null)
				&& !"".equals(getTfMaterialNum().getText())) {

			int itemCount = Integer.parseInt(getTfMaterialNum().getText());
			if (itemCount > 0) {
				if (isAva) {
					isAvailability = !(applyToCustomsBillList.getListState()
							.intValue() == applyToCustomsBillList.Effect);
				} else {
					isAvailability = applyToCustomsBillList.getListState()
							.intValue() == applyToCustomsBillList.Effect;
				}
			}
		}
		return isAvailability;
	}

	private boolean isAvailability() {
		boolean isAvailability = false;
		if (this.applyToCustomsBillList != null) {
			if (applyToCustomsBillList.getListState().intValue() != 0) {
				isAvailability = applyToCustomsBillList.getListState()
						.intValue() == applyToCustomsBillList.Effect;
			}
		}
		return isAvailability;
	}

	/**
	 * 显示窗口上控件在不同编辑状态时的状态
	 */
	private void setState() {
		this.btnAdd.setVisible(jTabbedPane.getSelectedIndex() == 0);
		this.btnAdd.setEnabled(((dataState == DataState.BROWSE) && (jTabbedPane
				.getSelectedIndex() == 0)));

		this.btnEdit.setEnabled((dataState == DataState.BROWSE)
				&& (applyToCustomsBillList != null)
				&& (!isTransferAtc) && (!isAvailability()));
		jButton1.setEnabled((dataState == DataState.BROWSE) && (!isTransferAtc)
				&& (!isAvailability()));

		this.btnDelete.setEnabled((dataState == DataState.BROWSE)
				&& (!isTransferAtc) && (!isAvailability()));
		jButton2
				.setEnabled((dataState == DataState.BROWSE) && (!isTransferAtc));
		jButton3
				.setEnabled((dataState == DataState.BROWSE) && (!isTransferAtc));
		jButton.setEnabled((dataState == DataState.BROWSE) && (!isTransferAtc)
				&& (!isAvailability()));
		this.btnSave.setEnabled(!(dataState == DataState.BROWSE)
				&& !isTransferAtc && !isAvailability());

		this.btnCancel.setEnabled((!(dataState == DataState.BROWSE))
				&& (!isTransferAtc));

		this.btneffect.setEnabled(isAvailability(true)
				&& dataState == DataState.BROWSE && (!isTransferAtc));
		this.btnNoteffect.setEnabled(isAvailability(false)
				&& (dataState == DataState.BROWSE) && (!isTransferAtc));

		this.btnDelete.setVisible(jTabbedPane.getSelectedIndex() == 1);
		jButton2.setVisible(jTabbedPane.getSelectedIndex() == 1);
		jButton3.setVisible(jTabbedPane.getSelectedIndex() == 1);
		btnSave.setVisible(jTabbedPane.getSelectedIndex() == 1);
		jCheckBox.setVisible(jTabbedPane.getSelectedIndex() == 1);
		jButton.setVisible(jTabbedPane.getSelectedIndex() == 1);
		jButton1.setVisible(jTabbedPane.getSelectedIndex() == 1);
		this.btnSave.setVisible(jTabbedPane.getSelectedIndex() == 0);
		this.btnCancel.setVisible(jTabbedPane.getSelectedIndex() == 0);

		this.btneffect.setVisible(jTabbedPane.getSelectedIndex() == 0);
		this.btnNoteffect.setVisible(jTabbedPane.getSelectedIndex() == 0);

		if (beforeCommInfoTableModel == null) {
			initAfterTable();
			if (afterTableDataSource != null && afterTableDataSource.size() > 0) {
				atcAfter = (AtcMergeAfterComInfo) afterTableDataSource.get(0);
			}
			initBeforeTable(isMerger, atcAfter);
		}
		this.cbbImpExpType.setEnabled((!(dataState == DataState.BROWSE))
				&& (!isTransferAtc));
		if (dataState == DataState.EDIT) {
			this.cbbImpExpType
					.setEnabled(beforeCommInfoTableModel.getSize() == 0 ? false
							: true);
		}
		this.tfBillListNo.setEditable((!(dataState == DataState.BROWSE))
				&& (!isTransferAtc));
		// 一旦料件成品标志确定后不能不修改
		this.cbbMaterielProductFlag
				.setEnabled((!(dataState == DataState.BROWSE))
						&& (!isTransferAtc)
						&& (applyToCustomsBillList == null ? true
								: applyToCustomsBillList
										.getMaterielProductFlag() == null));

		this.jComboBox.setEnabled((!(dataState == DataState.BROWSE))
				&& (!isTransferAtc));
		this.jComboBox1.setEnabled((!(dataState == DataState.BROWSE))
				&& (!isTransferAtc));
		this.jComboBox2.setEnabled((!(dataState == DataState.BROWSE))
				&& (!isTransferAtc));
		this.cbbTransportMode.setEnabled((!(dataState == DataState.BROWSE))
				&& (!isTransferAtc));
		this.jComboBox3.setEnabled((!(dataState == DataState.BROWSE))
				&& (!isTransferAtc));
		this.cbbTradeMode.setEnabled((!(dataState == DataState.BROWSE))
				&& (!isTransferAtc));
		this.tfMemo.setEditable((!(dataState == DataState.BROWSE))
				&& (!isTransferAtc));
		tfEmsNo.setEditable((!(dataState == DataState.BROWSE))
				&& (!isTransferAtc));
		tfBillListNo.setEditable((!(dataState == DataState.BROWSE))
				&& (!isTransferAtc));
		jTextField.setEditable((!(dataState == DataState.BROWSE))
				&& (!isTransferAtc));
		jTextField2.setEditable((!(dataState == DataState.BROWSE))
				&& (!isTransferAtc));
		tfMaterialNum.setEditable((!(dataState == DataState.BROWSE))
				&& (!isTransferAtc));
		tfBillListNo.setEditable((!(dataState == DataState.BROWSE))
				&& (!isTransferAtc));
		this.jTabbedPane.setEnabled(dataState == DataState.BROWSE);
	}

	/**
	 * 保存报关清单 *
	 */
	private void saveApplyToCustomsBillList() {
		if (!checkData()) {
			return;
		}
		fillBillListData(applyToCustomsBillList);
		applyToCustomsBillList = encAction.saveApplyToCustomsBillList(
				new Request(CommonVars.getCurrUser()), applyToCustomsBillList);
		if (dataState == DataState.ADD) {
			billListTableModel.addRow(applyToCustomsBillList);
		} else if (dataState == DataState.EDIT) {
			billListTableModel.updateRow(applyToCustomsBillList);
		}
		dataState = DataState.BROWSE;
		setState();
	}

	private boolean checkData() {
		if (this.cbbImpExpType.getSelectedIndex() < 0) {
			JOptionPane.showMessageDialog(DgApplyToCustomsBillList.this,
					"请选择进出口类型", "提示！", 0);
			return false;
		}
		if (this.tfBillListNo.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(DgApplyToCustomsBillList.this,
					"清单编号不能为空", "提示！", 0);
			return false;
		}
		if (this.cbbMaterielProductFlag.getSelectedIndex() < 0) {
			JOptionPane.showMessageDialog(DgApplyToCustomsBillList.this,
					"请选择料件成品标志！", "提示！", 0);
			return false;
		}
		if (this.jComboBox1.getSelectedIndex() < 0) {
			JOptionPane.showMessageDialog(DgApplyToCustomsBillList.this,
					"请选择进出口岸！", "提示！", 0);
			return false;
		}
		if (this.jComboBox.getSelectedIndex() < 0) {
			JOptionPane.showMessageDialog(DgApplyToCustomsBillList.this,
					"请选择申报地海关！", "提示！", 0);
			return false;
		}
		if (this.jComboBox2.getSelectedIndex() < 0) {
			JOptionPane.showMessageDialog(DgApplyToCustomsBillList.this,
					"请选择申报单位！", "提示！", 0);
			return false;
		}
		return true;

	}

	// 需要修改
	private void initBeforeTable(boolean isMerger, AtcMergeAfterComInfo atcAfter) {
		if (atcAfter == null) {
			beforeTableDataSource = new ArrayList();
		} else {
			if (!isMerger) {
				beforeTableDataSource = encAction
						.findAtcMergeBeforeComInfoByAfterID(new Request(
								CommonVars.getCurrUser()), atcAfter);
			} else {
				beforeTableDataSource = encAction
						.findAllAtcMergerBeforeComInfo(new Request(CommonVars
								.getCurrUser()), applyToCustomsBillList);
			}
			if (beforeTableDataSource == null
					|| beforeTableDataSource.size() < 0) {
				beforeTableDataSource = new ArrayList();
			}
		}
		beforeCommInfoTableModel = new JTableListModel(tbMergeBeforeCommInfo,
				beforeTableDataSource, new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("商品序号", "serialNo", 50,
								Integer.class));
						list.add(addColumn("帐册序号", "afterComInfo.emsSerialNo",
								60, Integer.class));
						list.add(addColumn("商品货号", "materiel.ptNo", 100));
						list.add(addColumn("对应报关单商品号", "customsNo", 120));
						list.add(addColumn("商品编码", "afterComInfo.complex.code",
								80));
						list
								.add(addColumn("商品名称", "materiel.factoryName",
										120));
						list
								.add(addColumn("型号规格", "materiel.factorySpec",
										120));
						list.add(addColumn("企业申报单价", "declaredPrice", 100));
						list.add(addColumn("申报数量", "declaredAmount", 70));
						list
								.add(addColumn("备案单位",
										"afterComInfo.unit.name", 50));
						list.add(addColumn("总金额", "totalPrice", 70));
						list.add(addColumn("加工费总价", "workUsd", 70));
						list.add(addColumn("法定数量", "legalAmount", 70));
						list.add(addColumn("第二法定数量", "secondLegalAmount", 80));
						list.add(addColumn("毛重", "grossWeight", 70));
						list.add(addColumn("净重", "netWeight", 70));
						list.add(addColumn("件数", "piece", 60));
						list.add(addColumn("箱号", "boxNo", 60));
						list.add(addColumn("版本", "version", 60));
						list.add(addColumn("原产国", "salesCountry.name", 100));
						list.add(addColumn("备注", "memos", 120));
						list.add(addColumn("事业部", "projectDept.name", 120));

						return list;
					}
				});
		List<JTableListColumn> columns = beforeCommInfoTableModel.getColumns();
		for (int i = 0; i < columns.size(); i++) {
			columns.get(i).setFractionCount(4);
		}
		tbMergeBeforeCommInfo
				.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		// 页脚
		beforeCommInfoTableModel.clearFooterTypeInfo();
		beforeCommInfoTableModel.addFooterTypeInfo(new TableFooterType(0,
				TableFooterType.CONSTANT, "合计"));
		beforeCommInfoTableModel.addFooterTypeInfo(new TableFooterType(8,
				TableFooterType.SUM, ""));
		beforeCommInfoTableModel.addFooterTypeInfo(new TableFooterType(9,
				TableFooterType.SUM, ""));

		beforeCommInfoTableModel.addFooterTypeInfo(new TableFooterType(11,
				TableFooterType.SUM, ""));
		beforeCommInfoTableModel.addFooterTypeInfo(new TableFooterType(12,
				TableFooterType.SUM, ""));
		beforeCommInfoTableModel.addFooterTypeInfo(new TableFooterType(13,
				TableFooterType.SUM, ""));
		beforeCommInfoTableModel.addFooterTypeInfo(new TableFooterType(14,
				TableFooterType.SUM, ""));
		beforeCommInfoTableModel.addFooterTypeInfo(new TableFooterType(15,
				TableFooterType.SUM, ""));
		beforeCommInfoTableModel.addFooterTypeInfo(new TableFooterType(16,
				TableFooterType.SUM, ""));
		beforeCommInfoTableModel.addFooterTypeInfo(new TableFooterType(17,
				TableFooterType.SUM, ""));
	}

	// 初始化归并后
	private void initAfterTable() {
		if (applyToCustomsBillList == null) {
			afterTableDataSource = new ArrayList();
		} else {
			afterTableDataSource = encAction.findAtcMergeAfterComInfoByListID(
					new Request(CommonVars.getCurrUser()),
					applyToCustomsBillList);
		}
		afterCommInfoTableModel = new JTableListModel(tbMergeAfterCommInfo,
				afterTableDataSource, new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("是否已转报关单", "isTransferCustomsBill",
								90));
						list.add(addColumn("帐册序号", "emsSerialNo", 60,
								Integer.class));
						list.add(addColumn("商品编码", "complex.code", 100));
						list.add(addColumn("商品名称", "complexName", 100));
						list.add(addColumn("商品规格", "complexSpec", 100));
						list.add(addColumn("申报数量", "declaredAmount", 70));
						list.add(addColumn("备案单位", "unit.name", 50));
						list.add(addColumn("法定数量", "legalAmount", 70));
						list.add(addColumn("第二法定数量", "secondLegalAmount", 70));
						list.add(addColumn("毛重", "grossWeight", 70));
						list.add(addColumn("净重", "netWeight", 70));
						list.add(addColumn("件数", "piece", 60));
						list.add(addColumn("箱号", "boxNo", 60));
						list.add(addColumn("总价", "totalPrice", 70));
						list.add(addColumn("加工费总价", "workUsd", 70));
						list.add(addColumn("版本", "version", 50));
						list.add(addColumn("原产国",   "country.name", 100));
						return list;
					}
				});
		List<JTableListColumn> columns = afterCommInfoTableModel.getColumns();
		for (int i = 0; i < columns.size(); i++) {
			columns.get(i).setFractionCount(4);
		}
		tbMergeAfterCommInfo.getColumnModel().getColumn(1).setCellRenderer(
				new MultiRenderer());
		afterCommInfoTableModel.clearFooterTypeInfo();
		afterCommInfoTableModel.addFooterTypeInfo(new TableFooterType(0,
				TableFooterType.CONSTANT, "合计"));
		afterCommInfoTableModel.addFooterTypeInfo(new TableFooterType(6,
				TableFooterType.SUM, ""));
		afterCommInfoTableModel.addFooterTypeInfo(new TableFooterType(8,
				TableFooterType.SUM, ""));
		afterCommInfoTableModel.addFooterTypeInfo(new TableFooterType(9,
				TableFooterType.SUM, ""));
		afterCommInfoTableModel.addFooterTypeInfo(new TableFooterType(10,
				TableFooterType.SUM, ""));
		afterCommInfoTableModel.addFooterTypeInfo(new TableFooterType(11,
				TableFooterType.SUM, ""));
		afterCommInfoTableModel.addFooterTypeInfo(new TableFooterType(12,
				TableFooterType.SUM, ""));
		afterCommInfoTableModel.addFooterTypeInfo(new TableFooterType(14,
				TableFooterType.SUM, ""));

		afterCommInfoTableModel.addFooterTypeInfo(new TableFooterType(15,
				TableFooterType.SUM, ""));

	}

	/**
	 * render table JchcckBox 列
	 */
	class MultiRenderer extends DefaultTableCellRenderer {
		JCheckBox checkBox = new JCheckBox();

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			if (value == null) {
				checkBox.setSelected(false);
				checkBox.setHorizontalAlignment(JLabel.CENTER);
				checkBox.setBackground(table.getBackground());
				if (isSelected) {
					checkBox.setForeground(table.getSelectionForeground());
					checkBox.setBackground(table.getSelectionBackground());
				} else {
					checkBox.setForeground(table.getForeground());
					checkBox.setBackground(table.getBackground());
				}
				return checkBox;
			}
			if (Boolean.valueOf(value.toString()) instanceof Boolean) {
				checkBox.setSelected(Boolean.parseBoolean(value.toString()));
				checkBox.setHorizontalAlignment(JLabel.CENTER);
				checkBox.setBackground(table.getBackground());
				if (isSelected) {
					checkBox.setForeground(table.getSelectionForeground());
					checkBox.setBackground(table.getSelectionBackground());
				} else {
					checkBox.setForeground(table.getForeground());
					checkBox.setBackground(table.getBackground());
				}
				return checkBox;
			}
			String str = (value == null) ? "" : value.toString();
			return super.getTableCellRendererComponent(table, str, isSelected,
					hasFocus, row, column);
		}
	}

	/**
	 * This method initializes tbMergeBeforeCommInfo
	 * 
	 * @return javax.swing.JTable
	 */
	private JFooterTable getTbMergeBeforeCommInfo() {
		if (tbMergeBeforeCommInfo == null) {
			tbMergeBeforeCommInfo = new JFooterTable();
			tbMergeBeforeCommInfo
					.addMouseListener(new java.awt.event.MouseAdapter() {
						public void mouseClicked(java.awt.event.MouseEvent e) {
							if (e.getClickCount() == 2) {
								if (beforeCommInfoTableModel.getCurrentRow() == null
										|| (beforeCommInfoTableModel
												.getCurrRowCount() >= (beforeCommInfoTableModel
												.getRowCount() - 1))) {
									return;
								}
								DgAtcMergeCommInfo dgAtcMergeCommInfo = new DgAtcMergeCommInfo(
										beforeCommInfoTableModel);
								boolean isMakeCustomsDeclaration = applyToCustomsBillList
										.getListState() == null ? false : true;
								dgAtcMergeCommInfo
										.setMakeCustomsDeclaration(isMakeCustomsDeclaration);
								dgAtcMergeCommInfo.setVisible(true);
								if (dgAtcMergeCommInfo.isOk()) {
									AtcMergeBeforeComInfo beforeComInfo = (AtcMergeBeforeComInfo) beforeCommInfoTableModel
											.getCurrentRow();
									afterCommInfoTableModel
											.updateRow(beforeComInfo
													.getAfterComInfo());
								}
							}
						}
					});
		}
		return tbMergeBeforeCommInfo;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JFooterScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JFooterScrollPane();
			jScrollPane1.setViewportView(getTbMergeBeforeCommInfo());
			jScrollPane1
					.setBorder(javax.swing.BorderFactory
							.createTitledBorder(
									null,
									"归并前商品信息（可修改）",
									javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
									javax.swing.border.TitledBorder.DEFAULT_POSITION,
									null, null));
		}
		return jScrollPane1;
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
			jSplitPane.setTopComponent(getJScrollPane());
			jSplitPane.setBottomComponent(getJScrollPane1());
			jSplitPane.setDividerLocation(180);
			jSplitPane.setDividerSize(8);
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setBounds(197, 196, 190, 20);
		}
		return jComboBox;
	}

	/**
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox1() {
		if (jComboBox1 == null) {
			jComboBox1 = new JComboBox();
			jComboBox1.setBounds(490, 191, 190, 23);
		}
		return jComboBox1;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("归并后删除");
			jButton.setPreferredSize(new Dimension(72, 30));
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (JOptionPane
							.showConfirmDialog(DgApplyToCustomsBillList.this,
									"是否确定删除数据!", "提示", 0) != 0) {
						return;
					}
					if (afterCommInfoTableModel == null) {
						return;
					}
					if (afterCommInfoTableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(
								DgApplyToCustomsBillList.this, "请选择要删除的数据",
								"提示", 0);
						return;
					}
					AtcMergeAfterComInfo afterInfo = (AtcMergeAfterComInfo) afterCommInfoTableModel
							.getCurrentRow();
					if (afterInfo.getIsTransferCustomsBill() != null
							&& afterInfo.getIsTransferCustomsBill().equals(
									new Boolean(true))) {
						JOptionPane.showMessageDialog(
								DgApplyToCustomsBillList.this,
								"该清单已转报关单，请先删除报关单!", "提示", 0);
						return;
					}
					encAction.deleteAtcMergeAfterComInfo(new Request(CommonVars
							.getCurrUser()), afterInfo);
					ApplyToCustomsBillList bills = afterInfo.getBillList();
					bills.setMaterialNum(bills.getMaterialNum() == null ? 0
							: bills.getMaterialNum() - 1);
					bills = encAction.saveApplyToCustomsBillList(new Request(
							CommonVars.getCurrUser()), bills);
					billListTableModel.updateRow(bills);
					initAfterTable();
					if (afterCommInfoTableModel != null
							&& afterCommInfoTableModel.getCurrentRow() != null) {
						atcAfter = (AtcMergeAfterComInfo) afterCommInfoTableModel
								.getCurrentRow();
					}
					initBeforeTable(isMerger, atcAfter);
				}
			});
		}
		return jButton;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setEditable(false);
			jTextField.setBounds(new Rectangle(197, 134, 190, 20));
		}
		return jTextField;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			jTextField1.setEditable(false);
			jTextField1.setBounds(new Rectangle(490, 283, 190, 23));
		}
		return jTextField1;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("归并前新增");
			jButton1.setPreferredSize(new Dimension(72, 30));
			jButton1.setForeground(java.awt.Color.blue);
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Integer materielProductFlag = cbbMaterielProductFlag
							.getSelectedItem() == null ? null : Integer
							.valueOf(((ItemProperty) cbbMaterielProductFlag
									.getSelectedItem()).getCode());
					DgNewBillBeforeDialog dg = new DgNewBillBeforeDialog();
					dg.setImpExpType(String.valueOf(materielProductFlag));
					dg.setVisible(true);
					if (dg.isOk()) {
						List list = null;
						if (CommonVars.isCompany("明门幼童")
								|| CommonVars.isCompany("宝钜儿童用品")) {
							DgBillFromMaterielEdit dgbill = new DgBillFromMaterielEdit();
							Materiel m = dg.getMateriel();
							dgbill.setMateriel(m);
							dgbill.setProjectType(ProjectType.BCUS);
							EmsHeadH2kImg img = dg.getBcusimgbill();
							EmsHeadH2kExg exg = dg.getBcusexgbill();
							dgbill.setBcusimgbill(img);
							dgbill.setBcusexgbill(exg);
							dgbill
									.setApplyToCustomsBillList(applyToCustomsBillList);
							dgbill.setVisible(true);
							if (dgbill.isOk()) {
								list = dgbill.getAllList();
							}
						} else {
							EmsHeadH2kImg img = dg.getBcusimgbill();
							EmsHeadH2kExg exg = dg.getBcusexgbill();
							Materiel m = dg.getMateriel();
							list = encAction.newBillBefore(new Request(
									CommonVars.getCurrUser()), img, exg, m,
									applyToCustomsBillList);

						}
						atcAfter = (AtcMergeAfterComInfo) list.get(1);
						afterCommInfoTableModel.addRow(atcAfter);

						applyToCustomsBillList = (ApplyToCustomsBillList) list
								.get(0);
						billListTableModel.updateRow(applyToCustomsBillList);
						tfMaterialNum.setText(String
								.valueOf(applyToCustomsBillList
										.getMaterialNum()));// 商品项数
						initAfterTable();
						if (afterCommInfoTableModel != null
								&& afterCommInfoTableModel.getCurrentRow() != null) {
							atcAfter = (AtcMergeAfterComInfo) afterCommInfoTableModel
									.getCurrentRow();
						}
						initBeforeTable(isMerger, atcAfter);
						// refreshAfterStatInfo();
					}
				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			jCheckBox = new JCheckBox();
			jCheckBox.setForeground(java.awt.Color.blue);
			jCheckBox.setText("显示所有");
			jCheckBox.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					if (jCheckBox.isSelected()) {
						isMerger = true;
					} else {
						isMerger = false;
					}
					if (afterCommInfoTableModel != null
							&& afterCommInfoTableModel.getCurrentRow() != null) {
						atcAfter = (AtcMergeAfterComInfo) afterCommInfoTableModel
								.getCurrentRow();
					}
					initBeforeTable(isMerger, atcAfter);
				}
			});
		}
		return jCheckBox;
	}

	/**
	 * This method initializes jComboBox2
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox2() {
		if (jComboBox2 == null) {
			jComboBox2 = new JComboBox();
			jComboBox2.setBounds(new Rectangle(197, 315, 190, 20));
		}
		return jComboBox2;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getJCalendarComboBox() {
		if (jCalendarComboBox == null) {
			jCalendarComboBox = new JCalendarComboBox();
			jCalendarComboBox.setEnabled(false);
			jCalendarComboBox.setBounds(new Rectangle(490, 253, 190, 23));
		}
		return jCalendarComboBox;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("自动排序");
			jButton2.setPreferredSize(new Dimension(60, 30));
			jButton2.setForeground(java.awt.Color.blue);
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new billSort().start();
				}
			});
		}
		return jButton2;
	}

	class billSort extends Thread {
		public void run() {
			try {
				CommonProgress
						.showProgressDialog(DgApplyToCustomsBillList.this);
				CommonProgress.setMessage("系统正在排序资料，请稍后...");
				if (afterCommInfoTableModel != null
						&& afterCommInfoTableModel.getCurrentRow() != null) {
					atcAfter = (AtcMergeAfterComInfo) afterCommInfoTableModel
							.getCurrentRow();
					String billNo = atcAfter.getBillList().getListNo();
					encAction.billSort(new Request(CommonVars.getCurrUser()),
							billNo);
					if (jCheckBox.isSelected()) {
						isMerger = true;
					} else {
						isMerger = false;
					}
					initBeforeTable(isMerger, atcAfter);
				}
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgApplyToCustomsBillList.this,
						"排序完成!", "提示", 2);
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgApplyToCustomsBillList.this,
						"排序失败：！" + e.getMessage(), "提示", 2);
			}
		}
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setText("自定义排序");
			jButton3.setPreferredSize(new Dimension(72, 30));
			jButton3.setForeground(java.awt.Color.blue);
			jButton3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List list = encAction.findAllAtcMergerBeforeComInfo(
							new Request(CommonVars.getCurrUser()),
							applyToCustomsBillList);
					Vector<AtcMergeBeforeComInfo> vec = new Vector<AtcMergeBeforeComInfo>();
					for (int i = 0; i < list.size(); i++) {
						vec.addElement((AtcMergeBeforeComInfo) list.get(i));
					}
					DgBillSort dg = new DgBillSort();
					dg.setBaseEncAction(encAction);
					dg.setList(vec);
					dg.setVisible(true);
					if (jCheckBox.isSelected()) {
						isMerger = true;
					} else {
						isMerger = false;
					}
					initBeforeTable(isMerger, atcAfter);
				}
			});
		}
		return jButton3;
	}

	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	/**
	 * This method initializes btneffect
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtneffect() {
		if (btneffect == null) {
			btneffect = new JButton();
			btneffect.setText("生效");
			btneffect.setPreferredSize(new Dimension(55, 30));
			btneffect.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// if (applyToCustomsBillList.getListState().intValue() ==
					// applyToCustomsBillList.GENERATED_CUSTOMS_DECLARATION) {
					if (applyToCustomsBillList.getEffectstate()) {
						JOptionPane.showMessageDialog(
								DgApplyToCustomsBillList.this,
								"你的清单状态是[已转报关单]，不能生效", "提示", 2);
						return;
					}
					List errlist = encAction.checkIsNull(new Request(CommonVars
							.getCurrUser()), applyToCustomsBillList);
					if (errlist != null && errlist.size() > 0) {
						CommonProgress.closeProgressDialog();
						String str = "";
						boolean checkFT = true;
						String str1 = "";
						for (int j = 0; j < errlist.size(); j++) {
							str1 = (String) errlist.get(j);
							str += (String) errlist.get(j);
							checkFT = (str1.substring(5, str1.length()).equals(
									"法定数量不可为空")
									|| str1.substring(5, str1.length()).equals(
											"法定数量不可为空,法定数量二不可为空") || str1
									.substring(5, str1.length()).equals(
											"法定数量二不可为空"))
									&& checkFT;
						}
						// 因为施耐德法定数量在系统中不能确认。而需要在Qp上录入。所以对于法定数量栏位只做提示！
						if (!str.equals("") && checkFT) {
							if (JOptionPane.showConfirmDialog(
									DgApplyToCustomsBillList.this, str
											+ " ,是否继续!", "提示", 0) != 0) {
								return;
							}
						} else {
							JOptionPane.showMessageDialog(
									DgApplyToCustomsBillList.this, str
											+ " ,不能生效", "提示", 2);
							return;
						}

					}
					beginAvailability(true);

				}
			});
		}
		return btneffect;
	}

	private void beginAvailability(boolean isAvailability) {
		try {

			if (isAvailability == true) {
				applyToCustomsBillList
						.setListState(ApplyToCustomsBillList.Effect);
				fillBillListData(applyToCustomsBillList);

			} else
				applyToCustomsBillList
						.setListState(ApplyToCustomsBillList.DRAFT);

			applyToCustomsBillList = encAction.saveApplyToCustomsBillList(
					new Request(CommonVars.getCurrUser()),
					applyToCustomsBillList);
			this.billListTableModel.updateRow(applyToCustomsBillList);
			dataState = DataState.BROWSE;
			setState();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "数据可能有误,生效失败!", "提示", 0);
		}
	}

	/**
	 * This method initializes btnNoteffect
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnNoteffect() {
		if (btnNoteffect == null) {
			btnNoteffect = new JButton();
			btnNoteffect.setText("撤消");
			btnNoteffect.setPreferredSize(new Dimension(45, 30));
			btnNoteffect.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// if (applyToCustomsBillList.getListState().intValue() ==
					// ApplyToCustomsBillList.GENERATED_CUSTOMS_DECLARATION) {
					if (applyToCustomsBillList.getEffectstate()) {
						JOptionPane.showMessageDialog(
								DgApplyToCustomsBillList.this,
								"报关清单数据已转报关单,不能撤消生效!!", "警告",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					if (applyToCustomsBillList.getListState().intValue() == ApplyToCustomsBillList.PASSED) {
						JOptionPane.showMessageDialog(
								DgApplyToCustomsBillList.this,
								"报关清单已经审批通过,不能撤消生效!!", "警告",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					beginAvailability(false);
				}
			});
		}
		return btnNoteffect;
	}

	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField2() {
		if (jTextField2 == null) {
			jTextField2 = new JTextField();
			jTextField2.setBounds(new Rectangle(490, 133, 190, 23));
			jTextField2.setEditable(false);
		}
		return jTextField2;
	}

	/**
	 * This method initializes btnPrint
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton();
			btnPrint.setText("打印");
			btnPrint.setPreferredSize(new Dimension(55, 30));
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Component comp = (Component) e.getSource();
					getJPopupMenu().show(comp, 0, comp.getHeight());
//					printReport();
				}
			});
		}
		return btnPrint;
	}

	/**
	 * This method initializes jPopupMenu 套打
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	protected JPopupMenu getJPopupMenu() {
		if (jPopupMenu == null) {
			jPopupMenu = new JPopupMenu();
			jPopupMenu.add(getJMenuItem());
		}
		return jPopupMenu;
	}

	/**
	 * This method initializes jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItem() {
		if (jMenuItem == null) {
			jMenuItem = new JMenuItem();
			jMenuItem.setText(" 打印清单");
			jMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					printReport();
				}
			});
		}
		return jMenuItem;
	}

	/**
	 * 打印清单
	 */
	private void printReport() {
		List<AtcMergeAfterComInfo> dsList = new ArrayList();
		List reDataSource = new ArrayList();
		if (afterCommInfoTableModel == null) {
			initAfterTable();
			initBeforeTable(isMerger, atcAfter);
		}
		dsList = afterCommInfoTableModel.getList();
		for (AtcMergeAfterComInfo data : dsList) {
			reDataSource.addAll(encAction.findAtcMergeBeforeComInfoByAfterID(
					new Request(CommonVars.getCurrUser()), data));
		}
		System.out.println("reDataSource.size()=" + reDataSource.size());
		// dzscCustomsBillList
		CustomReportDataSource ds = new CustomReportDataSource(reDataSource);
		InputStream reportStream = DgApplyToCustomsBillList.class
				.getResourceAsStream("report/DzzcCustomsBillList.jasper");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("company.name", applyToCustomsBillList.getCompany()
				.getName());
		if (applyToCustomsBillList.getScmCoc() != null) {
			parameters.put("scmcoc", applyToCustomsBillList.getScmCoc()
					.getName() == null ? "" : applyToCustomsBillList
					.getScmCoc().getName());
		}
		// parameters.put("copEmsNo", applyToCustomsBillList.getCopEmsNo());
		Integer flag = applyToCustomsBillList.getMaterielProductFlag();

		Integer impExpType = applyToCustomsBillList.getImpExpType();
		if (impExpType == null) {
			parameters.put("impExpType", "");
		} else {
			int in = impExpType.intValue();
			switch (in) {
			case 0:
				parameters.put("impExpType", "料件进口");
				break;
			case 1:
				parameters.put("impExpType", "料件转厂(料件)");
				break;
			case 2:
				parameters.put("impExpType", "退厂返工(成品)");
				break;
			case 3:
				parameters.put("impExpType", "一般贸易进口(料件)");
				break;
			case 4:
				parameters.put("impExpType", "成品出口");
				break;
			case 5:
				parameters.put("impExpType", "转厂出口(成品)");
				break;
			case 6:
				parameters.put("impExpType", "退料出口");
				break;
			case 7:
				parameters.put("impExpType", "返工复出(成品)");
				break;
			case 8:
				parameters.put("impExpType", "边角料退港");
				break;
			case 9:
				parameters.put("impExpType", "边角料内销");
				break;
			case 10:
				parameters.put("impExpType", "一般贸易出口(成品)");
				break;
			case 11:
				parameters.put("impExpType", "设备进口");
				break;
			case 12:
				parameters.put("impExpType", "退港维修");
				break;
			case 13:
				parameters.put("impExpType", "设备退港");
				break;
			case 14:
				parameters.put("impExpType", "余料结转(出口报关单)");
				break;
			case 15:
				parameters.put("impExpType", "出口仓储");
				break;
			case 16:
				parameters.put("impExpType", "进口仓储");
				break;
			case 17:
				parameters.put("impExpType", "料件内销");
				break;
			case 18:
				parameters.put("impExpType", "料件退换");
				break;
			case 19:
				parameters.put("impExpType", "料件复出");
				break;
			case 20:
				parameters.put("impExpType", "所有");
				break;
			case 21:
				parameters.put("impExpType", "余料结转(进口报关单)");
				break;
			case 25:
				parameters.put("impExpType", "进料成品退换");
				break;
			case 26:
				parameters.put("impExpType", "修理物品(成品)");
				break;
			case 27:
				parameters.put("impExpType", "修理物品复出(成品)");
				break;
			case 28:
				parameters.put("impExpType", "进料成品退换复出(成品)");
				break;
			default:
				parameters.put("impExpType", "");
				break;
			}
		}
		// this.cbbMaterialType.addItem(new ItemProperty(MaterielType.MATERIEL,
		// "料件"));
		// this.cbbMaterialType.addItem(new ItemProperty(
		// MaterielType.FINISHED_PRODUCT, "成品"));
		if (flag == null) {
			parameters.put("customsImgExgFlag", "");
		} else {
			int in = flag.intValue();
			switch (in) {
			case 0:
				parameters.put("customsImgExgFlag", "成品");
				break;
			case 1:
				parameters.put("customsImgExgFlag", "半成品");
				break;
			case 2:
				parameters.put("customsImgExgFlag", "料件");
				break;
			case 3:
				parameters.put("customsImgExgFlag", "设备");
				break;
			case 4:
				parameters.put("customsImgExgFlag", "边角料");
				break;
			case 5:
				parameters.put("customsImgExgFlag", "残次品");
				break;
			case 6:
				parameters.put("customsImgExgFlag", "辅料");
				break;
			case 7:
				parameters.put("customsImgExgFlag", "消耗料");
				break;
			default:
				parameters.put("customsImgExgFlag", "");
				break;
			}
		}
		Integer num = applyToCustomsBillList.getMaterialNum() == null ? 0
				: applyToCustomsBillList.getMaterialNum();
		String materialNum = String.valueOf(num);
		parameters.put("materialNum", materialNum);
		parameters.put("emsHeadH2k", applyToCustomsBillList.getEmsHeadH2k());
		parameters.put("impExpCIQ",
				applyToCustomsBillList.getDeclareCIQ() == null ? ""
						: applyToCustomsBillList.getDeclareCIQ().getName());
		// Date impExpDate = dzscCustomsBillList.getImpExpDate();
		// if (impExpDate == null) {
		// parameters.put("impExpDate", null);
		// } else {
		// parameters.put("impExpDate", dateFormat.format(impExpDate));
		// }
		Date listDeclareDate = applyToCustomsBillList.getListDeclareDate();
		if (listDeclareDate == null) {
			parameters.put("listDeclareDate", null);
		} else {
			parameters.put("listDeclareDate", dateFormat
					.format(listDeclareDate));
		}
		
		parameters.put("listNo", applyToCustomsBillList.getListQpBillNo());
		parameters.put("listUniteNo", applyToCustomsBillList.getListUniteNo());
		// Integer type = dzscCustomsBillList.getListType();
		// if (type == null) {
		// parameters.put("listType", "");
		// } else {
		// switch (type) {
		// case 0:
		// parameters.put("listType", "一般报关");
		// break;
		// case 1:
		// parameters.put("listType", "提前转关");
		// break;
		// case 2:
		// parameters.put("listType", "二次转关");
		// break;
		// case 3:
		// parameters.put("listType", "属地报关");
		// break;
		//
		// default:
		// break;
		// }
		// }

		parameters.put("tradeMode.name",
				applyToCustomsBillList.getTradeMode() == null ? ""
						: applyToCustomsBillList.getTradeMode().getName());

		JasperPrint jasperPrint = null;
		try {
			jasperPrint = JasperFillManager.fillReport(reportStream,
					parameters, ds);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (JRException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * This method initializes jComboBox3
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox3() {
		if (jComboBox3 == null) {
			jComboBox3 = new JComboBox();
			jComboBox3.setBounds(new Rectangle(490, 346, 190, 23));

		}
		return jComboBox3;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
