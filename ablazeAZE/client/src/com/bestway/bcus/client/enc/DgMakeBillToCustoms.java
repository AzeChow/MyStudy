package com.bestway.bcus.client.enc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DgCommonQueryPage;
import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.bcus.custombase.action.CustomBaseAction;
import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.countrycode.District;
import com.bestway.bcus.custombase.entity.parametercode.LevyKind;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.bcus.custombase.entity.parametercode.Transac;
import com.bestway.bcus.custombase.entity.parametercode.Transf;
import com.bestway.bcus.custombase.entity.parametercode.Wrap;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.enc.entity.ApplyToCustomsBillList;
import com.bestway.bcus.enc.entity.AtcMergeAfterComInfo;
import com.bestway.bcus.enc.entity.CustomsDeclaration;
import com.bestway.bcus.enc.entity.MakeCusomsDeclarationParam;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.manualdeclare.entity.RestirictCommodity;
import com.bestway.bcus.system.action.SystemAction;
import com.bestway.bcus.system.entity.CustomsDeclarationSet;
import com.bestway.client.custom.common.EncCommon;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

@SuppressWarnings({"unchecked","serial"})
public class DgMakeBillToCustoms extends JDialogBase {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private CustomBaseAction customBaseAction = null;

	private EncAction encAction = null;

	private JTableListModel tableModel = null;
	private JTableListModel tableModel1 = null;

	private int impExpFlag = ImpExpFlag.IMPORT;

//	private ApplyToCustomsBillList billList = null;

	private JToolBar jJToolBarBar = null;

	private JButton btnCancel = null;

	private JButton btnOk = null;

	private JPanel jPanel2 = null;

	private JComboBox cbbTradeMode = null;
	private ButtonGroup bg = null;  //  @jve:decl-index=0:
	private JComboBox cbbTransportMode = null;

	private JTextField tfEmsNo = null;

	private JComboBox cbbCustoms = null;

	private JComboBox cbbImpExpPort = null;

	private JLabel jLabel61 = null;

	private JLabel jLabel71 = null;

	private JLabel jLabel11 = null;

	private JLabel jLabel51 = null;

	private JLabel jLabel32 = null;

	private JLabel jLabel = null;

	private JCalendarComboBox jCalendarComboBox = null;

	private JLabel jLabel1 = null;

	private JComboBox jComboBox = null;

	private JLabel jLabel2 = null;

	private JCustomFormattedTextField jCustomFormattedTextField = null;

	private JLabel jLabel3 = null;

	private JTextField jTextField = null;

	private JLabel jLabel4 = null;

	private JComboBox jComboBox1 = null;

	private JLabel jLabel5 = null;

	private JComboBox jComboBox2 = null;

	private DefaultFormatterFactory defaultFormatterFactory = null; // @jve:decl-index=0:parse

	private JLabel jLabel6 = null;

	private MaterialManageAction materialManageAction = null;

	private JComboBox jComboBox11 = null;

	private JLabel jLabel7 = null;

	private JComboBox jComboBox12 = null;

	private JLabel jLabel8 = null;

	private JComboBox jComboBox121 = null;

	private EmsHeadH2k emsHeadH2k = null;

	private ManualDeclareAction manualDeclearAction = null;

	private NumberFormatter numberFormatter = null; // @jve:decl-index=0:parse

	private SystemAction systemAction = null;

	private CustomsDeclaration cm = null;  //  @jve:decl-index=0:

	private JRadioButton jRadioButton = null;

	private JRadioButton jRadioButton1 = null;

	private JPanel jPanel = null;

	private ButtonGroup buttonGroup = null; // @jve:decl-index=0:visual-constraint="1136,75"

	protected MakeCusomsDeclarationParam para = null;  //  @jve:decl-index=0:

	public int appCount = 0;

	private JLabel jLabel10 = null;

	private JButton jButton1 = null;

	private JPanel jPanel11 = null;

	private JLabel jLabel17 = null;

	private JLabel lbHeadText = null;

	private JLabel jLabel19 = null;

	private JPanel jPanel1 = null;

	private JPanel jPanel3 = null; // @jve:decl-index=0:visual-constraint="741,44"

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;

	private JPanel jPanel4 = null; // @jve:decl-index=0:visual-constraint="741,184"

	private JScrollPane jScrollPane1 = null;

	private JTable jTable1 = null;
	private JButton jButton2 = null;
	private int step = 0;

	private JLabel jLabel9 = null;

	private JTextField jTextField1 = null;

	private JButton btnChoose = null;
	private List rlist = null; // @jve:decl-index=0:

	private JPanel jPanel51 = null; // @jve:decl-index=0:visual-constraint="736,170"

	private JRadioButton jRadioButton21 = null;

	private JRadioButton jRadioButton22 = null;

	private List rlist1;  //  @jve:decl-index=0:

	private JCheckBox cbArray = null;

	/**
	 * This is the default constructor
	 */
	public DgMakeBillToCustoms() {
		super();
		systemAction = (SystemAction) CommonVars.getApplicationContext()
				.getBean("systemAction");
		encAction = (EncAction) CommonVars.getApplicationContext().getBean(
				"encAction");
		manualDeclearAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		customBaseAction = (CustomBaseAction) CommonVars
				.getApplicationContext().getBean("customBaseAction");
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		List list = manualDeclearAction.findEmsHeadH2kInExecuting(new Request(
				CommonVars.getCurrUser(), true));
		if (list == null || list.size() <= 0) {
			emsHeadH2k = null;
		} else {
			emsHeadH2k = (EmsHeadH2k) (list.get(0));
		}
		initialize();
		initComponents();
		getButtonGroup();
		getButtonGroups();
		setState(true);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(591, 455);
		this.setContentPane(getJContentPane());
		this.setTitle("清单转报关单");
	}

	private void initComponents() {

		this.tfEmsNo.setText(emsHeadH2k == null ? "" : emsHeadH2k.getEmsNo());
		// 初始化贸易方式
		this.cbbTradeMode.setModel(CustomBaseModel.getInstance()
				.getTradeModel());
		this.cbbTradeMode.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbTradeMode);
		this.cbbTradeMode.setSelectedIndex(-1);

		// 初始化进出口岸
		this.cbbImpExpPort.setModel(CustomBaseModel.getInstance()
				.getCustomModel());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbImpExpPort);
		this.cbbImpExpPort.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		this.cbbImpExpPort.setSelectedIndex(-1);

		// 初始化进口口岸
		this.cbbCustoms
				.setModel(CustomBaseModel.getInstance().getCustomModel());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbCustoms);
		this.cbbCustoms.setRenderer(CustomBaseRender.getInstance().getRender());
		this.cbbCustoms.setSelectedIndex(-1);

		// 初始化征免性质
		this.jComboBox.setModel(CustomBaseModel.getInstance()
				.getLevyKindModel());
		CustomBaseComboBoxEditor.getInstance()
				.setComboBoxEditor(this.jComboBox);
		this.jComboBox.setRenderer(CustomBaseRender.getInstance().getRender());
		this.jComboBox.setSelectedIndex(-1);

		// 初始化客户commonBaseCodeAction
		List list = materialManageAction.findScmCocs(new Request(CommonVars
				.getCurrUser(), true));
		this.jComboBox121.setModel(new DefaultComboBoxModel(list.toArray()));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.jComboBox121, "code", "name");
		this.jComboBox121.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name"));
		this.jComboBox121.setSelectedIndex(-1);

		// 初始化运输方式
		this.cbbTransportMode.setModel(CustomBaseModel.getInstance()
				.getTransfModel());
		this.cbbTransportMode.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbTransportMode);
		this.cbbTransportMode.setSelectedIndex(-1);

		// 初始化进申报地海关
		this.cbbCustoms
				.setModel(CustomBaseModel.getInstance().getCustomModel());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbImpExpPort);
		this.cbbCustoms.setRenderer(CustomBaseRender.getInstance().getRender());
		this.cbbCustoms.setSelectedItem(null);

		// 初始化装货港
		this.jComboBox1.setModel(CustomBaseModel.getInstance()
				.getDistrictModelModel());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.jComboBox1);
		this.jComboBox1.setRenderer(CustomBaseRender.getInstance().getRender());
		this.jComboBox1.setSelectedIndex(-1);
		// 初始化起运国
		this.jComboBox2.setModel(CustomBaseModel.getInstance()
				.getCountryModel());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.jComboBox2);
		this.jComboBox2.setRenderer(CustomBaseRender.getInstance().getRender());
		this.jComboBox2.setSelectedIndex(-1);

		// 初始化成交方式
		this.jComboBox11.setModel(CustomBaseModel.getInstance()
				.getTransacModel());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.jComboBox11);
		this.jComboBox11
				.setRenderer(CustomBaseRender.getInstance().getRender());
		this.jComboBox11.setSelectedIndex(-1);

		// 初始化包装种类
		List listwarp = customBaseAction.findWrap();
		this.jComboBox12.setModel(new DefaultComboBoxModel(listwarp.toArray()));
		this.jComboBox12.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.jComboBox12, "code", "name");
		this.jComboBox12.setSelectedIndex(-1);

		CustomsDeclarationSet other = null;// 以下是系统参数设置
		if (this.impExpFlag == ImpExpFlag.IMPORT) {
			other = systemAction.findCustomsSet(new Request(CommonVars
					.getCurrUser()), ImpExpType.DIRECT_IMPORT);
			if (other != null) {
				cbbCustoms.setSelectedItem((Customs) other
						.getDeclarationCustoms());
				cbbTransportMode.setSelectedItem((Transf) other
						.getTransferMode());
				cbbImpExpPort.setSelectedItem((Customs) other.getCustoms());
				cbbTradeMode.setSelectedItem((Trade) other.getTradeMode());
			}
		} else {
			other = systemAction.findCustomsSet(new Request(CommonVars
					.getCurrUser()), ImpExpType.DIRECT_IMPORT);
			if (other != null) {
				cbbCustoms.setSelectedItem((Customs) other
						.getDeclarationCustoms());
				cbbTransportMode.setSelectedItem((Transf) other
						.getTransferMode());
				cbbImpExpPort.setSelectedItem((Customs) other.getCustoms());
				cbbTradeMode.setSelectedItem((Trade) other.getTradeMode());
			}
		}
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel10 = new JLabel();
			jLabel10.setText("报关单表头设置暂可不填写，可以在生成报关单之后再填！");
			jLabel10.setBounds(new Rectangle(20, 232, 312, 18));
			jLabel10.setForeground(Color.BLUE);
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJPanel11(), BorderLayout.NORTH);
			jContentPane.add(getJJToolBarBar(), BorderLayout.SOUTH);
			jContentPane.add(getJPanel1(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes numberFormatter
	 * 
	 * @return javax.swing.text.NumberFormatter
	 */
	private NumberFormatter getNumberFormatter() {
		if (numberFormatter == null) {
			numberFormatter = new NumberFormatter();
		}
		return numberFormatter;
	}

	/**
	 * This method initializes defaultFormatterFactory
	 * 
	 * @return javax.swing.text.DefaultFormatterFactory
	 */
	private DefaultFormatterFactory getDefaultFormatterFactory() {
		if (defaultFormatterFactory == null) {
			defaultFormatterFactory = new DefaultFormatterFactory();
			defaultFormatterFactory.setDisplayFormatter(getNumberFormatter());
			defaultFormatterFactory.setEditFormatter(getNumberFormatter());
		}
		return defaultFormatterFactory;
	}

	public void setImpExpFlag(int impExpFlag) {
		this.impExpFlag = impExpFlag;
	}

//	/**
//	 * 编辑列
//	 */
//
//	public ApplyToCustomsBillList getBillList() {
//		return billList;
//	}
//
//	public void setBillList(ApplyToCustomsBillList billList) {
//		this.billList = billList;
//	}

	/**
	 * This method initializes jJToolBarBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJJToolBarBar() {
		if (jJToolBarBar == null) {
			jJToolBarBar = new JToolBar();
			FlowLayout fl = new FlowLayout();
			fl.setHgap(3);
			fl.setVgap(1);
			// fl.setAlignment(1);
			// fl.setAlignOnBaseline(true);
			jJToolBarBar.setLayout(fl);
			jJToolBarBar.add(getJButton2());
			jJToolBarBar.add(getJButton1());
			jJToolBarBar.add(getBtnOk());
			jJToolBarBar.add(getBtnCancel());
		}
		return jJToolBarBar;
	}

	/**
	 * This method initializes btnCancel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText("退出");
			btnCancel.setPreferredSize(new Dimension(50, 28));
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * This method initializes btnOk
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton();
			btnOk.setText("执行");
			btnOk.setPreferredSize(new Dimension(50, 28));
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (fillPara()) {
						new execute().start();
					}
				}
			});
			btnOk.setEnabled(false);// dispose();
		}
		return btnOk;
	}

	private boolean fillPara() {
		rlist = new ArrayList();
		rlist1 = new ArrayList();
		List temp = this.tableModel1.getList();
		for (int k = 0; k < temp.size(); k++) {
			AtcMergeAfterComInfo af = (AtcMergeAfterComInfo) temp.get(k);
			if (af.getIsSelected() != null && af.getIsSelected()) {
				rlist.add(af);
			}
		}
		if (rlist.size() == 0) {
			JOptionPane.showMessageDialog(DgMakeBillToCustoms.this,
					"请选择所要转的清单！"

					, "提示！", 1);
			return false;
		}

		if (para == null) {
			para = new MakeCusomsDeclarationParam();
		}
		List temp1 = tableModel.getList();
		for (int k = 0; k < temp1.size(); k++) {
			ApplyToCustomsBillList af = (ApplyToCustomsBillList) temp1.get(k);
			if (af.getIsSelected() != null && af.getIsSelected()) {
				rlist1.add(af);
			}
		}
		if (para.getImpExpType() != ImpExpType.BACK_FACTORY_REWORK
				&& para.getImpExpType() != ImpExpType.BACK_MATERIEL_EXPORT) {
//			if (checkRerictCommodity(rlist)) {
//				return false;
//			}
			if (checkRerictCommoditys(rlist)) {
				return false;
			}
		}
	
		para.setEmsHeadH2k(this.emsHeadH2k);
		para.setApplyToCustomsBillList((ApplyToCustomsBillList) rlist1.get(0));
		para.setCustomsDeclaration(true);

		if (getJRadioButton1().isSelected()) {
			if (cm == null) {
				JOptionPane.showMessageDialog(DgMakeBillToCustoms.this,
						"请选择要追加的报关单！"

						, "提示！", 1);
				return false;
			}

			List liist = encAction.findCustomsDeclarationCommInfo(new Request(
					CommonVars.getCurrUser()), cm);
//			List alist = encAction.findAtcMergeAfterComInfoByBillNo(
//					new Request(CommonVars.getCurrUser()), this.billList
//							.getListNo());
			if (liist.size() + rlist.size() > 20) {
				JOptionPane.showMessageDialog(DgMakeBillToCustoms.this, "清单数量:"
						+ rlist.size() + "  报关单数量: " + liist.size()
						+ "\n  已超过20项，不能追加到该报关单！"

				, "提示！", 1);
				return false;
			}
		} else if (getJRadioButton().isSelected()) {
			this.cm = null;
			para.setImpExpDate(jCalendarComboBox.getDate());
			// para.setConveyance(cbbTransportMode.getSelectedItem().toString());
			para.setLevyKind((LevyKind) jComboBox.getSelectedItem());
			para.setPerTax(jCustomFormattedTextField.getValue() == null ? 0.0
					: Double.parseDouble(jCustomFormattedTextField.getValue()
							.toString()));
			para.setLicense(jTextField.getText());
			para.setCountryOfLoadingOrUnloading((Country) jComboBox2
					.getSelectedItem());
			para.setTransac((Transac) jComboBox11.getSelectedItem());
			para.setWrapType((Wrap) jComboBox12.getSelectedItem());
			para.setCustomer((ScmCoc) jComboBox121.getSelectedItem());
			para.setTransf((Transf) cbbTransportMode.getSelectedItem());
			para.setDomesticDestinationOrSource((District) jComboBox1
					.getSelectedItem());
			para.setTrade((Trade) cbbTradeMode.getSelectedItem());
			para.setCustoms((Customs) cbbImpExpPort.getSelectedItem());

			// --------------------------------------
			AtcMergeAfterComInfo dt = (AtcMergeAfterComInfo) rlist.get(0);
			ApplyToCustomsBillList app = dt.getBillList();
			para.setDelcarationDate(para.getDelcarationDate() == null ? app
					.getListDeclareDate() : para.getDelcarationDate());// 申报日期
			para.setImpExpType(para.getImpExpType());// 进出口类型
			para.setTransf(para.getTransf() == null ? app.getTransportMode()
					: para.getTransf());// 运输方式
			para.setTrade(para.getTrade() == null ? app.getTradeMode() : para
					.getTrade());// 贸易方式
			para.setMemo(para.getMemo() == null ? app.getMemos() : para
					.getMemo());// 备注
			para.setCurr(dt.getCurrency());
			para
					.setDeclarationCustoms(para.getDeclarationCustoms() == null ? app
							.getDeclareCIQ()
							: para.getDeclarationCustoms());// 申报海关
			para.setCustoms(para.getCustoms() == null ? app.getImpExpCIQ()
					: para.getCustoms());// 进出口岸
			// ------------------------------------如果没有填写，则会默认第一份清单
			if (rlist.size() > 20) {
				JOptionPane.showMessageDialog(DgMakeBillToCustoms.this, "清单数量:"
						+ rlist.size() + "\n  已超过20项，不能生成报关单！"

				, "提示！", 1);
				return false;
			}
		}
		return true;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jLabel8 = new JLabel();
			jLabel8.setBounds(new Rectangle(313, 116, 78, 23));
			jLabel8.setText("客户");
			jLabel7 = new JLabel();
			jLabel7.setBounds(new Rectangle(313, 204, 78, 23));
			jLabel7.setText("包装种类");
			jLabel6 = new JLabel();
			jLabel6.setBounds(new Rectangle(21, 204, 78, 23));
			jLabel6.setText("成交方式");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(21, 174, 78, 23));
			jLabel5.setText("境内目的地");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(313, 174, 78, 23));
			jLabel4.setText("起运国/抵运国");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(23, 55, 78, 23));
			jLabel3.setText("许可证号");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(313, 23, 78, 23));
			jLabel2.setText("征税比例%");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(21, 116, 78, 23));
			jLabel1.setText("征免性质");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(313, 55, 78, 23));
			jLabel.setText("进出口日期");
			jLabel32 = new JLabel();
			jLabel32.setBounds(new Rectangle(313, 86, 78, 23));
			jLabel32.setText("\u8fdb\u51fa\u53e3\u5cb8");
			jLabel51 = new JLabel();
			jLabel51.setBounds(new Rectangle(313, 146, 78, 23));
			jLabel51.setText("\u7533\u62a5\u6d77\u5173");
			jLabel11 = new JLabel();
			jLabel11.setBounds(new Rectangle(23, 23, 78, 23));
			jLabel11.setText("\u5e10\u518c\u7f16\u53f7");
			jLabel71 = new JLabel();
			jLabel71.setBounds(new Rectangle(21, 86, 78, 23));
			jLabel71.setText("\u8d38\u6613\u65b9\u5f0f");
			jLabel61 = new JLabel();
			jLabel61.setBounds(new Rectangle(21, 146, 78, 23));
			jLabel61.setText("\u8fd0\u8f93\u65b9\u5f0f");
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.setBorder(BorderFactory.createTitledBorder(BorderFactory
					.createEtchedBorder(EtchedBorder.LOWERED), "报关单表头设置",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, null, null));
			jPanel2.add(getCbbTradeMode(), null);
			jPanel2.add(getCbbTransportMode(), null);
			jPanel2.add(getTfEmsNo(), null);
			jPanel2.add(getCbbCustoms(), null);
			jPanel2.add(getCbbImpExpPort(), null);
			jPanel2.add(jLabel61, null);
			jPanel2.add(jLabel71, null);
			jPanel2.add(jLabel11, null);
			jPanel2.add(jLabel51, null);
			jPanel2.add(jLabel32, null);
			jPanel2.add(jLabel, null);
			jPanel2.add(getJCalendarComboBox(), null);
			jPanel2.add(jLabel1, null);
			jPanel2.add(getJComboBox(), null);
			jPanel2.add(jLabel2, null);
			jPanel2.add(getJCustomFormattedTextField(), null);
			jPanel2.add(jLabel3, null);
			jPanel2.add(getJTextField(), null);
			jPanel2.add(jLabel4, null);
			jPanel2.add(getJComboBox1(), null);
			jPanel2.add(jLabel5, null);
			jPanel2.add(getJComboBox2(), null);
			jPanel2.add(jLabel6, null);
			jPanel2.add(getJComboBox11(), null);
			jPanel2.add(jLabel7, null);
			jPanel2.add(getJComboBox12(), null);
			jPanel2.add(jLabel8, null);
			jPanel2.add(getJComboBox121(), null);
			jPanel2.add(jLabel10, null);
		}
		return jPanel2;
	}

	private void setState(boolean isNew) {
		Component[] comps = jPanel2.getComponents();
		for (int i = 0; i < comps.length; i++) {
			comps[i].setEnabled(isNew);
		}
		getBtnChoose().setEnabled(!isNew);
	}

	/**
	 * This method initializes cbbTradeMode
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbTradeMode() {
		if (cbbTradeMode == null) {
			cbbTradeMode = new JComboBox();
			cbbTradeMode.setBounds(new Rectangle(107, 86, 131, 23));
		}
		return cbbTradeMode;
	}

	/**
	 * This method initializes cbbTransportMode
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbTransportMode() {
		if (cbbTransportMode == null) {
			cbbTransportMode = new JComboBox();
			cbbTransportMode.setBounds(new Rectangle(107, 146, 131, 23));
		}
		return cbbTransportMode;
	}

	/**
	 * This method initializes tfEmsNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfEmsNo() {
		if (tfEmsNo == null) {
			tfEmsNo = new JTextField();
			tfEmsNo.setBounds(new Rectangle(108, 23, 131, 23));
			tfEmsNo.setEditable(false);
		}
		return tfEmsNo;
	}

	/**
	 * This method initializes cbbCustoms
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCustoms() {
		if (cbbCustoms == null) {
			cbbCustoms = new JComboBox();
			cbbCustoms.setBounds(new Rectangle(389, 146, 131, 23));
		}
		return cbbCustoms;
	}

	/**
	 * This method initializes cbbImpExpPort
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbImpExpPort() {
		if (cbbImpExpPort == null) {
			cbbImpExpPort = new JComboBox();
			cbbImpExpPort.setBounds(new Rectangle(389, 86, 131, 23));
		}
		return cbbImpExpPort;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getJCalendarComboBox() {
		if (jCalendarComboBox == null) {
			jCalendarComboBox = new JCalendarComboBox();
			jCalendarComboBox.setBounds(new Rectangle(389, 55, 131, 23));
		}
		return jCalendarComboBox;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setBounds(new Rectangle(107, 116, 131, 23));
		}
		return jComboBox;
	}

	/**
	 * This method initializes jCustomFormattedTextField
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getJCustomFormattedTextField() {
		if (jCustomFormattedTextField == null) {
			jCustomFormattedTextField = new JCustomFormattedTextField();
			jCustomFormattedTextField
					.setBounds(new Rectangle(389, 23, 131, 23));
			jCustomFormattedTextField
					.setFormatterFactory(getDefaultFormatterFactory());
		}
		return jCustomFormattedTextField;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(new Rectangle(108, 55, 131, 23));
		}
		return jTextField;
	}

	/**
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox1() {
		if (jComboBox1 == null) {
			jComboBox1 = new JComboBox();
			jComboBox1.setBounds(new Rectangle(107, 174, 131, 23));
		}
		return jComboBox1;
	}

	/**
	 * This method initializes jComboBox2
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox2() {
		if (jComboBox2 == null) {
			jComboBox2 = new JComboBox();
			jComboBox2.setBounds(new Rectangle(389, 174, 131, 23));
		}
		return jComboBox2;
	}

	/**
	 * This method initializes jComboBox11
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox11() {
		if (jComboBox11 == null) {
			jComboBox11 = new JComboBox();
			jComboBox11.setBounds(new Rectangle(107, 204, 131, 23));
		}
		return jComboBox11;
	}

	/**
	 * This method initializes jComboBox12
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox12() {
		if (jComboBox12 == null) {
			jComboBox12 = new JComboBox();
			jComboBox12.setBounds(new Rectangle(389, 204, 131, 23));
		}
		return jComboBox12;
	}

	/**
	 * This method initializes jComboBox121
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox121() {
		if (jComboBox121 == null) {
			jComboBox121 = new JComboBox();
			jComboBox121.setBounds(new Rectangle(389, 116, 131, 23));
		}
		return jComboBox121;
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton() {
		if (jRadioButton == null) {
			jRadioButton = new JRadioButton();
			jRadioButton.setText("生成新的报关单");
			jRadioButton.setBounds(new Rectangle(6, 25, 114, 24));
			jRadioButton.setSelected(true);
			jRadioButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (getJRadioButton().isSelected()) {
						setState(true);
					}
				}
			});
		}
		return jRadioButton;
	}

	/**
	 * This method initializes jRadioButton1
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton1() {
		if (jRadioButton1 == null) {
			jRadioButton1 = new JRadioButton();
			jRadioButton1.setText("追加到现有报关单");
			jRadioButton1.setBounds(new Rectangle(119, 25, 122, 24));
			jRadioButton1
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (getJRadioButton1().isSelected()) {
								setState(false);
							}
						}
					});
		}
		return jRadioButton1;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel9 = new JLabel();
			jLabel9.setBounds(new Rectangle(243, 25, 129, 24));
			jLabel9.setText("报关单流水号/报关单号:");
			jLabel9.setForeground(Color.BLUE);
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(getJRadioButton(), null);
			jPanel.add(getJRadioButton1(), null);
			jPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory
					.createEtchedBorder(EtchedBorder.LOWERED), "生成报关单方式设置",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, null, null));
			jPanel.add(jLabel9, null);
			jPanel.add(getJTextField1(), null);
			jPanel.add(getBtnChoose(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes buttonGroup
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup() {
		if (buttonGroup == null) {
			buttonGroup = new ButtonGroup();
			buttonGroup.add(getJRadioButton());
			buttonGroup.add(getJRadioButton1());
		}
		return buttonGroup;
	}
	
	public void work(){
		btnOk.setEnabled(false);
		try {
			CommonProgress.showProgressDialog(DgMakeBillToCustoms.this);
			CommonProgress.setMessage("系统正在执行数据，请稍后...");
			CustomsDeclaration cc = encAction
					.makeBilllistsToCustomDeclaretions(new Request(
							CommonVars.getCurrUser()), para
							.getApplyToCustomsBillList(), para, cm, rlist,!cbArray.isSelected());
			if (cc == null) {
				JOptionPane.showMessageDialog(DgMakeBillToCustoms.this,
						"转报关单失败！\n 所选的商品已转报关单！", "提示！", 1);
				return;
			} else {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgMakeBillToCustoms.this,
						"转报关单成功！\n 报关单流水号为"
								+ (cc.getSerialNumber() == null ? "空" : cc
										.getSerialNumber()), "提示！", 1);
			}

		} catch (Exception e) {
			CommonProgress.closeProgressDialog();
			e.printStackTrace();
			JOptionPane.showMessageDialog(DgMakeBillToCustoms.this,
					"执行数据失败：！" + e.getMessage(), "提示", 2);
		} finally {
			rlist.clear();
		}
	}

	// 执行
	class execute extends Thread {
		public void run() {
			work();
		}
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("下一步");
			jButton1.setPreferredSize(new Dimension(50, 28));
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					addStep(step);
				}
			});
		}
		return jButton1;
	}

	private void addStep(int step) {
		if (step == 0) {
			if (getJRadioButton1().isSelected() && cm == null) {
				JOptionPane.showMessageDialog(DgMakeBillToCustoms.this,
						"请选择报关单！", "提示！", JOptionPane.WARNING_MESSAGE);
				return;
			}
			getJContentPane().remove(getJPanel1());
			getJContentPane().add(getJPanel3(), BorderLayout.CENTER);
			jPanel3.add(getJPanel51(), BorderLayout.NORTH);
			initTable();
			getJButton2().setEnabled(true);
			lbHeadText.setText("第二步：选择所要转的清单");
			cbArray.setVisible(false);
			this.setVisible(true);
			this.repaint();
			this.step++;
		} else if (step == 1) {
			List temp = new ArrayList();
			List arl = this.tableModel.getList();
			for (int m = 0; m < arl.size(); m++) {
				ApplyToCustomsBillList ap = (ApplyToCustomsBillList) arl.get(m);
				if (ap.getIsSelected() != null && ap.getIsSelected()) {
					temp.add(ap);
				}
			}
			if (temp.size() == 0) {
				JOptionPane.showMessageDialog(DgMakeBillToCustoms.this,
						"没有选择清单！", "提示！", JOptionPane.WARNING_MESSAGE);
				return;
			}
			getJContentPane().remove(getJPanel3());
			getJContentPane().add(getJPanel4(), BorderLayout.CENTER);
			jPanel4.add(getJPanel51(), BorderLayout.NORTH);
			initTable1(temp);
			getJButton1().setEnabled(false);
			getBtnOk().setEnabled(true);
			lbHeadText.setText("第三步：选择所要转的清单体");
			System.out.println("temp="+temp.size());
			if(!getJRadioButton1().isSelected()&&temp.size()==1)
				cbArray.setVisible(true);
			else
				cbArray.setVisible(false);
			this.setVisible(true);
			this.repaint();
			this.step++;
		}
	}

	// 检查是不是限制类商品并且是否超出了限制类商品中定义的数量
	public boolean checkRerictCommodity(List commodityList) {
		double DeclarationCommInfo = 0;
		double Amount = 0;
		double commodityamount = 0;
		String seqNum = null;
       
		Collection cl = new Vector();
		Collection commoditycl = new Vector();
		Collection c2 = new Vector();
//		List commodityList = this.encAction.findAtcMergeAfterComInfoByParents(
//				new Request(CommonVars.getCurrUser()), arl);

		for (int i = 0; i < commodityList.size(); i++) {
			AtcMergeAfterComInfo tt = (AtcMergeAfterComInfo) commodityList
					.get(i);
			seqNum = tt.getEmsSerialNo() == null ? "" : tt.getEmsSerialNo()
					.toString();
			Boolean isMaterial = EncCommon.isMaterial(tt.getBillList()
					.getImpExpType());

			
			
			if (seqNum != null && !seqNum.equals("")) {

				// 得到报关单中的数量
				DeclarationCommInfo = materialManageAction
						.findCustomsDeclarationCommInfo(new Request(CommonVars
								.getCurrUser()), isMaterial, seqNum,null);

				Amount = DeclarationCommInfo
						+ Double.valueOf(tt.getDeclaredAmount().toString());

				RestirictCommodity commodity = manualDeclearAction
						.findRerictCommodity(new Request(CommonVars
								.getCurrUser()), isMaterial, seqNum);

				if (commodity != null) {
					if (commodity.getAmount() != null
							&& !commodity.getAmount().equals("")) {
						commodityamount = Double.parseDouble(commodity
								.getAmount().toString());
						if (Amount > commodityamount) {
							cl.add(seqNum);
							commoditycl.add(Amount);
							c2.add(commodity.getAmount().toString());
						}
					}
				}
			}
		}
		if (commoditycl.size() > 0 && cl.size() > 0) {
			if (JOptionPane.showConfirmDialog(this, "备案号："
					+ cl+ "已进(出)数量[" + commoditycl
					+ "] 超出了限制类商品中备的数量[" + c2 + "]!\n", "提示", -1) == 0) {
				return true;
			}
		}
		return false;
	}
	
	// 检查是不是限制类商品并且是否超出了限制类商品中定义的数量
	public boolean checkRerictCommoditys(List commodityList) {
		double declarationCommInfo = 0;
		double Amount = 0;
		double commodityamount = 0;
		String seqNum = null;
       
		Collection cl = new Vector();
		Collection commoditycl = new Vector();
		Collection c2 = new Vector();
//		List commodityList = this.encAction.findAtcMergeAfterComInfoByParents(
//				new Request(CommonVars.getCurrUser()), arl);

		//缓存成品数量
		Map<Integer,Double> mapExgAmount = new HashMap<Integer, Double>();
		
		//缓存料件数量
		Map<Integer,Double> mapImgAmount = new HashMap<Integer, Double>();
		
		//限制类商品成品
		Map<Integer,RestirictCommodity> mapCommodityImg = new HashMap<Integer, RestirictCommodity>();
		
		//限制类商品料件
		Map<Integer,RestirictCommodity> mapCommodityExg = new HashMap<Integer, RestirictCommodity>();
		
		initData(commodityList,mapExgAmount,mapImgAmount,mapCommodityImg,mapCommodityExg);
		
		for (int i = 0; i < commodityList.size(); i++) {
			AtcMergeAfterComInfo tt = (AtcMergeAfterComInfo) commodityList
					.get(i);
			seqNum = tt.getEmsSerialNo() == null ? "" : tt.getEmsSerialNo()
					.toString();
			Boolean isMaterial = EncCommon.isMaterial(tt.getBillList()
					.getImpExpType());

			if (tt.getEmsSerialNo()!=null) {
				if(isMaterial){
					// 得到报关单中的数量
					declarationCommInfo = mapImgAmount.get(tt.getEmsSerialNo())==null?0:mapImgAmount.get(tt.getEmsSerialNo());
				}else{
					declarationCommInfo = mapExgAmount.get(tt.getEmsSerialNo())==null?0:mapExgAmount.get(tt.getEmsSerialNo());
				}
				
				// 得到报关单中的数量
//				DeclarationCommInfo = materialManageAction
//						.findCustomsDeclarationCommInfo(new Request(CommonVars
//								.getCurrUser()), isMaterial, seqNum,null);

				Amount = declarationCommInfo
						+ Double.valueOf(tt.getDeclaredAmount().toString());
				
				RestirictCommodity commodity = null;
//				RestirictCommodity commodity = manualDeclearAction
//						.findRerictCommodity(new Request(CommonVars
//								.getCurrUser()), isMaterial, seqNum);
				
				if(isMaterial){
					commodity = mapCommodityImg.get(tt.getEmsSerialNo());
				}else{
					commodity = mapCommodityExg.get(tt.getEmsSerialNo());
				}

				if (commodity != null) {
					if (commodity.getAmount() != null
							&& !commodity.getAmount().equals("")) {
						commodityamount = Double.parseDouble(commodity
								.getAmount().toString());
						if (Amount > commodityamount) {
							cl.add(seqNum);
							commoditycl.add(Amount);
							c2.add(commodity.getAmount().toString());
						}
					}
				}
			}
		}
		if (commoditycl.size() > 0 && cl.size() > 0) {
			if (JOptionPane.showConfirmDialog(this, "备案号："
					+ cl+ "已进(出)数量[" + commoditycl
					+ "] 超出了限制类商品中备的数量[" + c2 + "]!\n", "提示", -1) == 0) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 初始化 缓存数据
	 * @param list 报关清单归并后商品信息
	 * @param mapExgAmount 成品数量
	 * @param mapImgAmount 料件数量
	 * @param mapCommodityImg 限制类商品料件
	 * @param mapCommodityExg 限制类商品成品
	 */
	private void initData(List list,Map<Integer,Double> mapExgAmount,Map<Integer,Double> mapImgAmount
			,Map<Integer,RestirictCommodity> mapCommodityImg,Map<Integer,RestirictCommodity> mapCommodityExg){
		
		List<Integer> listImg = new ArrayList<Integer>();
		List<Integer> listExg = new ArrayList<Integer>();
		
		Map<Integer,Integer> mapImgSerialNo = new HashMap<Integer, Integer>();//去料件重复
		Map<Integer,Integer> mapExgSerialNo = new HashMap<Integer, Integer>();//去成品重复
		
		for (int i = 0; i < list.size(); i++) {
			AtcMergeAfterComInfo tt = (AtcMergeAfterComInfo) list
					.get(i);
			Boolean isMaterial = EncCommon.isMaterial(tt.getBillList()
					.getImpExpType());
			if(tt.getEmsSerialNo()==null){
				continue;
			}
			
			//是否是料件
			if(isMaterial){
				//去料件重复
				if(mapImgSerialNo.get(tt.getEmsSerialNo())==null){
					listImg.add(tt.getEmsSerialNo());
					mapImgSerialNo.put(tt.getEmsSerialNo(), tt.getEmsSerialNo());
				}
			}else{
				//去成品重复
				if(mapExgSerialNo.get(tt.getEmsSerialNo())==null){
					listExg.add(tt.getEmsSerialNo());
					mapExgSerialNo.put(tt.getEmsSerialNo(), tt.getEmsSerialNo());
				}
			}
		}
		
		List listImgAmount = materialManageAction.findCustomsDeclarationCommInfos(new Request(CommonVars
				.getCurrUser()), true, listImg,null);
		List listExgAmount = materialManageAction.findCustomsDeclarationCommInfos(new Request(CommonVars
				.getCurrUser()), false, listExg,null);
		
		listToMap(listImgAmount,mapImgAmount);
		listToMap(listExgAmount,mapExgAmount);
		
		
		List<RestirictCommodity> commodityImg = manualDeclearAction.findRerictCommoditys(new Request(CommonVars
						.getCurrUser()), true, listImg);
		
		List<RestirictCommodity> commodityExg = manualDeclearAction.findRerictCommoditys(new Request(CommonVars
						.getCurrUser()), false, listExg);
		
		initRestirictCommoditys(commodityImg,mapCommodityImg);
		initRestirictCommoditys(commodityExg,mapCommodityExg);
		
	}
	
	private void listToMap(List list,Map map){
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = (Object[])list.get(i);
			if(obj[1]==null){
				continue;
			}
			map.put(Integer.parseInt(obj[0].toString()), Double.parseDouble(obj[1].toString()));
		}
	}

	private void initRestirictCommoditys(List<RestirictCommodity> list,Map<Integer,RestirictCommodity> map){
		for (int i = 0; i < list.size(); i++) {
			RestirictCommodity commodity = list.get(i);
			if(commodity.getSeqNum()!=null){
				map.put(Integer.parseInt(commodity.getSeqNum()), commodity);
			}
		}
	}
	
	private void mupStep(int step) {
		if (step == 1) {
			getJContentPane().remove(getJPanel3());
			getJContentPane().add(getJPanel1(), BorderLayout.CENTER);

			getJButton1().setEnabled(true);
			getJButton2().setEnabled(false);
			lbHeadText.setText("第一步：选择生成报关单生成方式 ");
			this.repaint();
			// parent.clear();

			// initTable();
		} else if (step == 2) {
			getJContentPane().remove(getJPanel4());
			getJContentPane().add(getJPanel3(), BorderLayout.CENTER);
			jPanel3.add(getJPanel51(), BorderLayout.NORTH);
			getJButton1().setEnabled(true);
			getBtnOk().setEnabled(false);
			lbHeadText.setText("第二步：选择所要转的清单");
			initTable();
			this.repaint();
			// this.detailList.clear();
		}
	}

	/**
	 * This method initializes jPanel11
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel11() {
		if (jPanel11 == null) {
			jLabel19 = new JLabel();
			jLabel19.setIcon(new ImageIcon(getClass().getResource(
					"/com/bestway/bcus/client/resources/images/titlepic.jpg")));
			jLabel19.setText("");
			lbHeadText = new JLabel();
			lbHeadText.setFont(new Font("Dialog", Font.BOLD, 20));
			lbHeadText.setText("第一步：选择生成报关单生成方式 ");
			lbHeadText.setForeground(new Color(255, 153, 0));
			jLabel17 = new JLabel();
			jLabel17
					.setIcon(new ImageIcon(
							getClass()
									.getResource(
											"/com/bestway/bcus/client/resources/images/titlepoint.jpg")));
			jLabel17.setText("");
			jPanel11 = new JPanel();
			jPanel11.setLayout(new BorderLayout());
			jPanel11.setBackground(Color.white);
			jPanel11.setBorder(BorderFactory
					.createEtchedBorder(EtchedBorder.LOWERED));
			jPanel11.add(jLabel17, java.awt.BorderLayout.WEST);
			jPanel11.add(lbHeadText, java.awt.BorderLayout.CENTER);
			jPanel11.add(jLabel19, java.awt.BorderLayout.EAST);
		}
		return jPanel11;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.insets = new Insets(0, 0, 1, 0);
			gridBagConstraints1.gridy = 1;
			gridBagConstraints1.ipadx = 581;
			gridBagConstraints1.ipady = 276;
			gridBagConstraints1.gridheight = 2;
			gridBagConstraints1.gridx = 0;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.ipadx = 581;
			gridBagConstraints.ipady = 66;
			gridBagConstraints.gridy = 0;
			jPanel1 = new JPanel();
			jPanel1.setLayout(new GridBagLayout());
			jPanel1.add(getJPanel(), gridBagConstraints);
			jPanel1.add(getJPanel2(), gridBagConstraints1);
		}
		return jPanel1;
	}

	private void initTable() {
		List list = new ArrayList();
		if (this.impExpFlag == ImpExpFlag.IMPORT) {
			list = encAction.findApplyToCustomsBillListByTypeBoToCustoms(
					new Request(CommonVars.getCurrUser()),
					ApplyToCustomsBillList.IMPORT);
		} else {
			list = encAction.findApplyToCustomsBillListByTypeBoToCustoms(
					new Request(CommonVars.getCurrUser()),
					ApplyToCustomsBillList.EXPORT);
		}
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List list = new Vector();
				list.add(addColumn("选择", "isSelected", 40));
				list.add(addColumn("进出口类型", "impExpType", 100));
				list.add(addColumn("清单状态", "listState", 100));
				list.add(addColumn("电子帐册编号", "emsHeadH2k", 100));
				list.add(addColumn("企业内部清单编号", "listNo", 100));
				list.add(addColumn("商品项数", "materialNum", 50));
				list.add(addColumn("清单申报日期", "listDeclareDate", 100));
				list.add(addColumn("报关单流水号", "customsDeclarationCode", 120));
				list.add(addColumn("录入日期", "createdDate", 80));
				list.add(addColumn("进出口岸", "impExpCIQ.name", 80));
				list.add(addColumn("申报地海关", "declareCIQ.name", 80));
				list.add(addColumn("料件/成品标志", "materielProductFlag", 80));

				return list;
			}
		};

		tableModel = new JTableListModel(getJTable(), list,
				jTableListModelAdapter);
		jTableListModelAdapter.setEditableColumn(1);
		getJTable().getColumnModel().getColumn(1).setCellRenderer(
				new TableCheckBoxRender());
		getJTable().getColumnModel().getColumn(1).setCellEditor(
				new CheckBoxEditor(new JCheckBox()));
		getJTable().getColumnModel().getColumn(2).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						String str = "";
						if (value != null) {
							switch (Integer.parseInt(value.toString())) {
							case ImpExpType.DIRECT_IMPORT:
								str = "直接进口";
								break;
							case ImpExpType.TRANSFER_FACTORY_IMPORT:
								str = "转厂进口";
								break;
							case ImpExpType.BACK_FACTORY_REWORK:
								str = "退厂返工";
								break;
							case ImpExpType.GENERAL_TRADE_IMPORT:
								str = "一般贸易进口";
								break;
							case ImpExpType.DIRECT_EXPORT:
								str = "直接出口";
								break;
							case ImpExpType.TRANSFER_FACTORY_EXPORT:
								str = "转厂出口";
								break;
							case ImpExpType.BACK_MATERIEL_EXPORT:
								str = "退料出口";
								break;
							case ImpExpType.REWORK_EXPORT:
								str = "返工复出";
								break;
							case ImpExpType.REMIAN_MATERIAL_BACK_PORT:
								str = "边角料退港";
								break;
							case ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES:
								str = "边角料内销";
								break;
							case ImpExpType.GENERAL_TRADE_EXPORT:
								str = "一般贸易出口";
								break;
							}
						}
						this.setText(str);
						return this;
					}
				});
		getJTable().getColumnModel().getColumn(3).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						String str = "";
						if (value != null) {
							switch (Integer.parseInt(value.toString())) {
							case ApplyToCustomsBillList.Effect:
								str = "生效";
								break;
							case ApplyToCustomsBillList.ALREADY_SEND:
								str = "已经申报";
								break;
							case ApplyToCustomsBillList.PASSED:
								str = "审批通过";
								break;
							}
						}
						this.setText(str);
						return this;
					}
				});
		getJTable().getColumnModel().getColumn(12).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						String str = "";
						if (value != null) {
							String tempStr = value.toString();
							if (tempStr.equals(MaterielType.MATERIEL)) {
								str = "料件";
							} else if (tempStr
									.equals(MaterielType.FINISHED_PRODUCT)) {
								str = "成品";
							}
						}
						this.setText(str);
						return this;
					}
				});

	}

	private void initTable1(List arl) {
		List detailList = this.encAction.findAtcMergeAfterComInfoByParents(
				new Request(CommonVars.getCurrUser()), arl);
		//只显示清单中还没被转的商品
		List newDetailList = new ArrayList();
		for (int i = 0; i < detailList.size(); i++) {
			AtcMergeAfterComInfo info =(AtcMergeAfterComInfo) detailList.get(i);
			if(!info.getIsTransferCustomsBill()){
				newDetailList.add(info);
			}
		}
		detailList = newDetailList;
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List list = new Vector();
				list.add(addColumn("选择", "isSelected", 40));
				list.add(addColumn("帐册序号", "emsSerialNo", 100));
				list.add(addColumn("企业内部清单编号", "billList.listNo", 100));
				list.add(addColumn("商品编码", "complex.code", 80));
				list.add(addColumn("商品名称", "complexName", 100));
				list.add(addColumn("规格型号", "complexSpec", 100));
				list.add(addColumn("申报数量", "declaredAmount", 60));
				list.add(addColumn("单价", "price", 60));
				list.add(addColumn("总价", "totalPrice", 60));
				list.add(addColumn("申报单位", "unit.name", 60));
				list.add(addColumn("第一法定单位", "legalUnit.name", 100));
				list.add(addColumn("第二法定单位", "secondLegalUnit.name", 100));
				list.add(addColumn("第一法定数量", "legalAmount", 100));
				list.add(addColumn("第二法定数量", "secondLegalAmount", 100));
				list.add(addColumn("毛重", "grossWeight", 60));
				list.add(addColumn("净重", "netWeight", 60));
				list.add(addColumn("件数", "piece", 60));
				list.add(addColumn("原产国", "country.name", 100));
				//list.add(addColumn("是否已转报关单", "isTransferCustomsBill", 60));
				list.add(addColumn("备注", "memos", 100));

				return list;
			}
		};

		tableModel1 = new JTableListModel(getJTable1(), detailList,
				jTableListModelAdapter);
		jTableListModelAdapter.setEditableColumn(1);
		getJTable1().getColumnModel().getColumn(1).setCellRenderer(
				new TableCheckBoxRender());
		getJTable1().getColumnModel().getColumn(1).setCellEditor(
				new CheckBoxEditor(new JCheckBox()));
		//setColor();
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
			jPanel3.setSize(new Dimension(399, 119));
			jPanel3.add(getJScrollPane(), BorderLayout.CENTER);
			jPanel3.add(getJPanel51(), BorderLayout.NORTH);
		}
		return jPanel3;
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
		}
		return jTable;
	}

	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jPanel4 = new JPanel();
			jPanel4.setLayout(new BorderLayout());
			jPanel4.setSize(new Dimension(210, 137));
			jPanel4.add(getJScrollPane1(), BorderLayout.CENTER);
			jPanel4.add(getJPanel51(), BorderLayout.NORTH);
		}
		return jPanel4;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJTable1());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jTable1
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable1() {
		if (jTable1 == null) {
			jTable1 = new JTable();
		}
		return jTable1;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("上一步");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					mupStep(step);
					step--;
				}
			});
			jButton2.setEnabled(false);
		}
		return jButton2;
	}

	/**
	 * 编辑列
	 */
	class CheckBoxEditor extends DefaultCellEditor implements ActionListener {
		private JCheckBox cb = new JCheckBox();
		private JTable table = null;

		public CheckBoxEditor(JCheckBox checkBox) {
			super(checkBox);
		}

		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {
			if (value == null) {
				value = new Boolean(false);
			}
			if (Boolean.valueOf(value.toString()) instanceof Boolean) {
				cb
						.setSelected(Boolean.valueOf(value.toString())
								.booleanValue());
			}
			cb.setHorizontalAlignment(JLabel.CENTER);
			cb.addActionListener(this);
			this.table = table;
			return cb;
		}

		public Object getCellEditorValue() {
			return cb.isSelected();
		}

		public void actionPerformed(ActionEvent e) {
			JTableListModel tableModel = (JTableListModel) this.table
					.getModel();
			Object obj = tableModel.getCurrentRow();
			if (obj instanceof ApplyToCustomsBillList) {
				ApplyToCustomsBillList temp = (ApplyToCustomsBillList) obj;
				temp.setIsSelected(new Boolean(cb.isSelected()));
				tableModel.updateRow(obj);
			} else if (obj instanceof AtcMergeAfterComInfo) {
				AtcMergeAfterComInfo temp = (AtcMergeAfterComInfo) obj;
				temp.setIsSelected(new Boolean(cb.isSelected()));
				tableModel.updateRow(obj);
			}
			fireEditingStopped();
		}
	}
	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			jTextField1.setBounds(new Rectangle(371, 25, 176, 24));
			jTextField1.setEditable(false);
		}
		return jTextField1;
	}

	/**
	 * This method initializes btnChoose
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnChoose() {
		if (btnChoose == null) {
			btnChoose = new JButton();
			btnChoose.setBounds(new Rectangle(546, 24, 23, 24));
			btnChoose.setText("...");
			btnChoose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Object obj = findCustomsDeclaration();
					if (obj != null) {
						cm = (CustomsDeclaration) obj;
						jTextField1.setText((cm.getSerialNumber() == null ? " " : cm
								.getSerialNumber().toString())
								+ " / "
								+ cm.getCustomsDeclarationCode());
					}

				}
			});
		}
		return btnChoose;
	}

	/**
	 * 抓取四码Distinct资料
	 * 
	 * @param projectType
	 * @return
	 */
	public Object findCustomsDeclaration() {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("报关单号", "customsDeclarationCode", 150));
		list.add(new JTableListColumn("流水号", "serialNumber", 50));
		list.add(new JTableListColumn("申报日期", "declarationDate", 100));
		list.add(new JTableListColumn("是否检查", "isCheck", 80));
		list.add(new JTableListColumn("是否生效", "effective", 80));
		DgCommonQueryPage.setTableColumns(list);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {

			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				EncAction encAction = (EncAction) CommonVars
						.getApplicationContext().getBean("encAction");
				return encAction.findCustomsDeclarationByImpExpFlag(
						new Request(CommonVars.getCurrUser()), impExpFlag);
			}

			@Override
			public void doSomethingBeforeVisable(JTable table) {
				table.getColumnModel().getColumn(4).setCellRenderer(
						new TableCheckBoxRender());
				table.getColumnModel().getColumn(5).setCellRenderer(
						new TableCheckBoxRender());
			}
		};
		dgCommonQuery.setTitle("请选择报关单！");
		DgCommonQueryPage.setSingleResult(true);

		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	/**
	 * This method initializes jPanel51
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel51() {
		if (jPanel51 == null) {
			jPanel51 = new JPanel();
			FlowLayout fl = new FlowLayout();
			fl.setAlignment(FlowLayout.LEFT);
			jPanel51.setLayout(fl);
			jPanel51.setSize(new Dimension(310, 50));
			jPanel51.add(getJRadioButton21(), null);
			jPanel51.add(getJRadioButton22(), null);
			jPanel51.add(getCbArray(), null);
		}
		return jPanel51;
	}

	/**
	 * This method initializes jRadioButton21
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton21() {
		if (jRadioButton21 == null) {
			jRadioButton21 = new JRadioButton();
			jRadioButton21.setText("全选");
			jRadioButton21
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (getJRadioButton21().isSelected()) {
								setSelectedAll(true);
							}
						}
					});
		}
		return jRadioButton21;
	}

	private ButtonGroup getButtonGroups() {
		if (bg == null) {
			bg = new ButtonGroup();
			bg.add(getJRadioButton21());
			bg.add(getJRadioButton22());
		}
		return bg;
	}

	/**
	 * This method initializes jRadioButton22
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton22() {
		if (jRadioButton22 == null) {
			jRadioButton22 = new JRadioButton();
			jRadioButton22.setText("不选");
			jRadioButton22.setSelected(true);
			jRadioButton22
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (getJRadioButton22().isSelected()) {
								setSelectedAll(false);
							}
						}
					});
		}
		return jRadioButton22;
	}

	private void setSelectedAll(boolean isAll) {
		if (step == 1) {
			List dlist = tableModel.getList();
			for (int i = 0; i < dlist.size(); i++) {
				Object obj = dlist.get(i);
				if (obj instanceof ApplyToCustomsBillList) {
					ApplyToCustomsBillList temp = (ApplyToCustomsBillList) obj;
					temp.setIsSelected(isAll);
				}
			}
			tableModel.getTable().repaint();
		} else if (step == 2) {
			List dlist = tableModel1.getList();
			for (int i = 0; i < dlist.size(); i++) {
				Object obj = dlist.get(i);
				if (obj instanceof AtcMergeAfterComInfo) {
					AtcMergeAfterComInfo temp = (AtcMergeAfterComInfo) obj;
					temp.setIsSelected(isAll);
				}
			}
			tableModel1.getTable().repaint();
		}

	}

	/**
	 * This method initializes cbArray	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbArray() {
		if (cbArray == null) {
			cbArray = new JCheckBox();
			cbArray.setText("转报关单后，商品序号按清单的顺序进行排序");
		}
		return cbArray;
	}

	public CustomsDeclaration getCm() {
		return cm;
	}

	public void setCm(CustomsDeclaration cm) {
		this.cm = cm;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}
	
} // @jve:decl-index=0:visual-constraint="129,16"
