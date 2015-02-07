package com.bestway.bls.client.storagebill;

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
import java.util.List;
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
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.bcus.custombase.action.CustomBaseAction;
import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.custombase.entity.parametercode.Wrap;
import com.bestway.bcus.enc.entity.ApplyToCustomsBillList;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bls.action.BlsInOutStockBillAction;
import com.bestway.bls.entity.BillImpExpFlag;
import com.bestway.bls.entity.BlsIOStockBillIOF;
import com.bestway.bls.entity.BlsInOutStockBill;
import com.bestway.bls.entity.BlsInOutStockBillDetail;
import com.bestway.bls.entity.BlsInOutStockSwichParameterSet;
import com.bestway.bls.entity.BlsInnerMerge;
import com.bestway.bls.entity.BlsTenInnerMerge;
import com.bestway.bls.entity.MakeBillToWarehouse;
import com.bestway.bls.entity.StorageBill;
import com.bestway.bls.entity.StorageBillAfter;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.WareSet;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * 进出仓单据转仓单窗体
 * 
 * @author hw
 * 
 */
public class DgMakeBillToWarehousewarrant extends JDialogBase {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private CustomBaseAction customBaseAction = null;

	/**
	 * 出入仓单据（表头）实体类
	 */
	private BlsInOutStockBill blsInOutStockBill = null; // @jve:decl-index=0:

	private JTableListModel tableModelBlsInOutStock = null;

	private JTableListModel tableModelBlsInOutStockDetail = null;

	private JToolBar jJToolBarBar = null;

	private JButton btnCancel = null;

	private JButton btnExecute = null;

	private JPanel jPanel2 = null;

	private ButtonGroup bg = null; // @jve:decl-index=0:

	private JTextField tfEmsNo = null;

	private JComboBox cbbCustoms = null;

	private JComboBox cbbImpExpPort = null;

	private JLabel jLabel11 = null;

	private JLabel jLabel51 = null;

	private JLabel jLabel32 = null;

	private JLabel jLabel4 = null;

	private DefaultFormatterFactory defaultFormatterFactory = null; // @jve:decl-index=0:parse

	private MaterialManageAction materialManageAction = null;

	private JLabel jLabel7 = null;

	private JComboBox cbbPackagingType = null;

	/**
	 * 电子帐册表头
	 */
	private EmsHeadH2k emsHeadH2k = null;

	private ManualDeclareAction manualDeclearAction = null;

	private NumberFormatter numberFormatter = null; // @jve:decl-index=0:parse

	/**
	 * 仓单实体类
	 */
	private StorageBill sb = null; // @jve:decl-index=0:

	private JRadioButton rbNewWareHouseWarrant = null;

	private JRadioButton rbToOldWareHouseWarrant = null;

	private JPanel jPanel = null;

	private ButtonGroup buttonGroup = null; // @jve:decl-index=0:visual-constraint="1136,75"

	private ButtonGroup buttonGroup2 = null; // @jve:decl-index=0:

	private MakeBillToWarehouse paras = null; // @jve:decl-index=0:

	private JButton btnNextStep = null;

	private JPanel jPanel11 = null;

	private JLabel jLabel17 = null;

	private JLabel lbHeadText = null;

	private JLabel jLabel19 = null;

	private JPanel jPanel1 = null;

	private JPanel jPanel3 = null; // @jve:decl-index=0:visual-constraint="741,44"

	private JScrollPane jScrollPane = null;

	/**
	 * 体现仓单头表体
	 */
	private JTable tbBlsInOutStock = null;

	private JPanel jPanel4 = null; // @jve:decl-index=0:visual-constraint="741,184"

	private JScrollPane jScrollPane1 = null;

	/**
	 * 体现仓单表体明细表体
	 */
	private JTable tbBlsInOutStockDetail = null;

	private JButton btnLastStep = null;

	/**
	 * 跳转参数
	 */
	private int step = 0;

	private JLabel jLabel9 = null;

	private JTextField tfWareHouseWarrantCode = null;

	private JButton btnChoose = null;

	/**
	 * 储存仓单表体明细
	 */
	private List rlist = null; // @jve:decl-index=0:

	private JPanel jPanel5 = null; // @jve:decl-index=0:visual-constraint="739,213"

	private JRadioButton jRadioButton2 = null;

	private JRadioButton rbChooseNot = null;

	private JRadioButton rb = null;

	private JPanel jPanel51 = null; // @jve:decl-index=0:visual-constraint="736,170"

	private JRadioButton rbChioceAll = null;

	private JRadioButton rbChioceNot = null;

	/**
	 * 储存仓单头
	 */
	private List rlist1; // @jve:decl-index=0:

	private JComboBox cbbBillType = null;

	private BlsInOutStockBillAction blsInOutStockBillAction = (BlsInOutStockBillAction) CommonVars
			.getApplicationContext().getBean("blsInOutStockBillAction");

	private JLabel jLabel = null;

	private JTextField tfWarehouseBillNo = null;

	private JRadioButton jbImp = null;

	private JRadioButton jbExp = null;

	private JCheckBox cbOneToOne = null;

	public BlsInOutStockBill getBlsInOutStockBill() {
		return blsInOutStockBill;
	}

	public void setBlsInOutStockBill(BlsInOutStockBill blsInOutStockBill) {
		this.blsInOutStockBill = blsInOutStockBill;
	}

	/**
	 * 窗体构造函数
	 */
	public DgMakeBillToWarehousewarrant(Integer impExpFlag) {
		super();
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

		// 根据转仓单的参数设置初始化窗体组建
		BlsInOutStockSwichParameterSet other = null;// 以下是系统参数设置
		if (impExpFlag == ImpExpFlag.IMPORT) {
			jbImp.setSelected(true);
			other = this.blsInOutStockBillAction
					.findBlsSwitchParameterSetByInOutFlag(new Request(
							CommonVars.getCurrUser()), BlsIOStockBillIOF.IMPORT);
			if (other != null) {
				cbbCustoms.setSelectedItem((Customs) other
						.getDeclarationCustoms());
				cbbImpExpPort.setSelectedItem((Customs) other.getCustoms());
				this.tfEmsNo.setText(other.getEmsNo());
				this.cbbPackagingType.setSelectedItem((Wrap) other
						.getWrapType());
				String btype = other.getBillType();
				if (btype != null && !btype.trim().equals("")) {
					int s = cbbBillType.getModel().getSize();
					for (int i = 0; i < s; i++) {
						Object obj = cbbBillType.getModel().getElementAt(i);
						if (obj instanceof ItemProperty) {
							ItemProperty itemp = (ItemProperty) obj;
							if (itemp.getCode().equals(btype)) {
								cbbBillType.setSelectedItem(itemp);
							}
						}
					}
				}
			}
		} else if (impExpFlag == ImpExpFlag.EXPORT) {
			jbExp.setSelected(true);
			other = this.blsInOutStockBillAction
					.findBlsSwitchParameterSetByInOutFlag(new Request(
							CommonVars.getCurrUser()), BlsIOStockBillIOF.EXPORT);
			if (other != null) {
				cbbCustoms.setSelectedItem((Customs) other
						.getDeclarationCustoms());
				cbbImpExpPort.setSelectedItem((Customs) other.getCustoms());
				this.tfEmsNo.setText(other.getEmsNo());
				this.cbbPackagingType.setSelectedItem((Wrap) other
						.getWrapType());
				String btype = other.getBillType();
				if (btype != null && !btype.trim().equals("")) {
					int s = cbbBillType.getModel().getSize();
					for (int i = 0; i < s; i++) {
						Object obj = cbbBillType.getModel().getElementAt(i);
						if (obj instanceof ItemProperty) {
							ItemProperty itemp = (ItemProperty) obj;
							if (itemp.getCode().equals(btype)) {
								cbbBillType.setSelectedItem(itemp);
							}
						}
					}
				}
			}
		}

		getButtonGroup();
		getButtonGroups();
		getButtonGroup2();
		setState(true);
		if (impExpFlag == ImpExpFlag.IMPORT) {
			System.out.println("impExpFlag=" + impExpFlag);
			jbImp.setSelected(true);
		} else if (impExpFlag == ImpExpFlag.EXPORT) {
			System.out.println("impExpFlag=" + impExpFlag);
			jbExp.setSelected(true);
		}
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(590, 505);
		this.setContentPane(getJContentPane());
		this.setTitle("进出仓单据转仓单");
	}

	/**
	 * 初始化组建
	 */
	private void initComponents() {
		btnExecute.setEnabled(false);

		// 仓单类型
		cbbBillType.addItem(new ItemProperty("00", "申报初始库存"));
		cbbBillType.addItem(new ItemProperty("01", "后报关方式"));
		cbbBillType.addItem(new ItemProperty("02", "先报关分批送货方式"));
		cbbBillType.addItem(new ItemProperty("03", "特殊审核"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbBillType,
				"code", "name", 250);
		this.cbbBillType.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 80, 170));
		cbbBillType.setSelectedIndex(-1);

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

		// 初始化客户commonBaseCodeAction
		List list = materialManageAction.findScmCocs(new Request(CommonVars
				.getCurrUser(), true));

		// 初始化进申报地海关
		this.cbbCustoms
				.setModel(CustomBaseModel.getInstance().getCustomModel());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbImpExpPort);
		this.cbbCustoms.setRenderer(CustomBaseRender.getInstance().getRender());
		this.cbbCustoms.setSelectedItem(null);
		this.cbbCustoms.setSelectedIndex(-1);

		// 初始化包装种类
		List listwarp = customBaseAction.findWrap();
		this.cbbPackagingType.setModel(new DefaultComboBoxModel(listwarp
				.toArray()));
		this.cbbPackagingType.setRenderer(CustomBaseRender.getInstance()
				.getRender("code", "name"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbPackagingType, "code", "name");
		this.cbbPackagingType.setSelectedIndex(-1);

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

	/**
	 * 编辑列
	 */

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
			jJToolBarBar.add(getBtnLastStep());
			jJToolBarBar.add(getBtnNextStep());
			jJToolBarBar.add(getBtnExecute());
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
	 * This method initializes btnExecute
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExecute() {
		if (btnExecute == null) {
			btnExecute = new JButton();
			btnExecute.setText("执行");
			btnExecute.setPreferredSize(new Dimension(50, 28));
			btnExecute.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (fillPara()) {
						// new execute().start();
						btnExecute.setEnabled(false);
						JOptionPane.showMessageDialog(
								DgMakeBillToWarehousewarrant.this, "转仓单成功！",
								"提示！", 1);
					}
				}
			});
			// dispose();
		}
		return btnExecute;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(313, 119, 78, 23));
			jLabel.setForeground(Color.blue);
			jLabel.setText("仓单号");
			jLabel7 = new JLabel();
			jLabel7.setBounds(new Rectangle(23, 117, 78, 23));
			jLabel7.setForeground(Color.blue);
			jLabel7.setText("包装种类");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(313, 70, 78, 23));
			jLabel4.setForeground(Color.blue);
			jLabel4.setText("仓单类型");
			jLabel32 = new JLabel();
			jLabel32.setBounds(new Rectangle(313, 23, 78, 23));
			jLabel32.setForeground(Color.blue);
			jLabel32.setText("\u8fdb\u51fa\u53e3\u5cb8");
			jLabel51 = new JLabel();
			jLabel51.setBounds(new Rectangle(23, 70, 78, 23));
			jLabel51.setForeground(Color.blue);
			jLabel51.setText("\u7533\u62a5\u6d77\u5173");
			jLabel11 = new JLabel();
			jLabel11.setForeground(Color.blue);
			jLabel11.setBounds(new Rectangle(23, 23, 78, 23));
			jLabel11.setBackground(Color.blue);
			jLabel11.setText("\u5e10\u518c\u7f16\u53f7");
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.setBorder(BorderFactory.createTitledBorder(BorderFactory
					.createEtchedBorder(EtchedBorder.LOWERED), "仓单表头设置",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, null, null));
			jPanel2.add(getTfEmsNo(), null);
			jPanel2.add(getCbbCustoms(), null);
			jPanel2.add(getCbbImpExpPort(), null);
			jPanel2.add(jLabel11, null);
			jPanel2.add(jLabel51, null);
			jPanel2.add(jLabel32, null);
			jPanel2.add(jLabel4, null);
			jPanel2.add(jLabel7, null);
			jPanel2.add(getCbbPackagingType(), null);
			jPanel2.add(getCbbBillType(), null);
			jPanel2.add(jLabel, null);
			jPanel2.add(getTfWarehouseBillNo(), null);
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
	 * This method initializes tfEmsNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfEmsNo() {
		if (tfEmsNo == null) {
			tfEmsNo = new JTextField();
			tfEmsNo.setBounds(new Rectangle(108, 23, 131, 23));
			tfEmsNo.setEditable(true);
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
			cbbCustoms.setBounds(new Rectangle(108, 70, 131, 23));
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
			cbbImpExpPort.setBounds(new Rectangle(389, 23, 131, 23));
		}
		return cbbImpExpPort;
	}

	/**
	 * This method initializes cbbPackagingType
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbPackagingType() {
		if (cbbPackagingType == null) {
			cbbPackagingType = new JComboBox();
			cbbPackagingType.setBounds(new Rectangle(108, 117, 131, 23));
		}
		return cbbPackagingType;
	}

	/**
	 * 新仓单
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbNewWareHouseWarrant() {
		if (rbNewWareHouseWarrant == null) {
			rbNewWareHouseWarrant = new JRadioButton();
			rbNewWareHouseWarrant.setText("新仓单");
			rbNewWareHouseWarrant.setBounds(new Rectangle(20, 19, 63, 24));
			rbNewWareHouseWarrant.setSelected(true);
			rbNewWareHouseWarrant
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (getRbNewWareHouseWarrant().isSelected()) {
								setState(true);
							}
						}
					});
		}
		return rbNewWareHouseWarrant;
	}

	/**
	 * 追加到现有仓单
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbToOldWareHouseWarrant() {
		if (rbToOldWareHouseWarrant == null) {
			rbToOldWareHouseWarrant = new JRadioButton();
			rbToOldWareHouseWarrant.setText("追加到现有仓单");
			rbToOldWareHouseWarrant.setBounds(new Rectangle(211, 18, 112, 24));
			rbToOldWareHouseWarrant
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (getRbToOldWareHouseWarrant().isSelected()) {
								setState(false);
							}
						}
					});
		}
		return rbToOldWareHouseWarrant;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel9 = new JLabel();
			jLabel9.setBounds(new Rectangle(324, 19, 45, 24));
			jLabel9.setText("仓单号:");
			jLabel9.setForeground(Color.BLUE);
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(getRbNewWareHouseWarrant(), null);
			// jPanel.add(getRbToOldWareHouseWarrant(), null);
			jPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory
					.createEtchedBorder(EtchedBorder.LOWERED), "生成仓单方式设置",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, null, null));
			jPanel.add(jLabel9, null);
			jPanel.add(getTfWareHouseWarrantCode(), null);
			jPanel.add(getBtnChoose(), null);
			jPanel.add(getRbToOldWareHouseWarrant(), null);
			jPanel.add(getJbImp(), null);
			jPanel.add(getJbExp(), null);
			jPanel.add(getCbOneToOne(), null);
		}
		return jPanel;
	}

	public ButtonGroup getButtonGroup2() {
		if (buttonGroup2 == null) {
			buttonGroup2 = new ButtonGroup();
			buttonGroup2.add(getJbImp());
			buttonGroup2.add(getJbExp());
		}
		return buttonGroup2;
	}

	/**
	 * This method initializes buttonGroup
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup() {
		if (buttonGroup == null) {
			buttonGroup = new ButtonGroup();
			buttonGroup.add(getRbNewWareHouseWarrant());
			buttonGroup.add(getRbToOldWareHouseWarrant());
		}
		return buttonGroup;
	}

	/**
	 * 转仓单
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private boolean fillPara() {
		if (paras == null) {
			paras = new MakeBillToWarehouse();
		}

		rlist = new ArrayList();
		rlist1 = new ArrayList();
		List temp = this.tableModelBlsInOutStockDetail.getList();

		//
		for (int j = 0; j < temp.size(); j++) {
			BlsInOutStockBillDetail bsds = (BlsInOutStockBillDetail) temp
					.get(j);
			if (bsds.getIsSelected() != null && bsds.getIsSelected()) {
				rlist.add(bsds);
			}
		}
		if (rlist.size() == 0) {
			JOptionPane.showMessageDialog(DgMakeBillToWarehousewarrant.this,
					"请选择所要转的出入仓单体！", "提示！", 1);
			return false;
		}

		List temp1 = tableModelBlsInOutStock.getList();

		for (int k = 0; k < temp1.size(); k++) {
			BlsInOutStockBill bsb = (BlsInOutStockBill) temp1.get(k);
			if (bsb.getIsSelected() != null && bsb.getIsSelected()) {
				rlist1.add(bsb);
			}
		}

		// 入仓单据与入仓但一一对应
		if (cbOneToOne.isSelected() && rbNewWareHouseWarrant.isSelected()) {
			ItemProperty item = (ItemProperty) cbbBillType.getSelectedItem();
			// this.tfWarehouseBillNo.setEnabled(false);

			// 用来获取选中的入仓单据
			List temp2 = new ArrayList();
			List arl = this.tableModelBlsInOutStock.getList();
			for (int m = 0; m < arl.size(); m++) {
				BlsInOutStockBill bsb = (BlsInOutStockBill) arl.get(m);
				if (bsb.getIsSelected() != null && bsb.getIsSelected()) {
					temp2.add(bsb);
				}
			}
			if (temp2.size() != 0) {
				String strErr = "";
				for (int i = 0; i < temp2.size(); i++) {
					BlsInOutStockBill blsInOutStockBill = (BlsInOutStockBill) temp2
					.get(i);
					String id = blsInOutStockBill.getId();
					List list2 = blsInOutStockBillAction
					.findBlsInOutStockBillDetailNot(new Request(
							CommonVars.getCurrUser()), id);
					if (jbImp.isSelected()) {
						for (int k = 0; k < list2.size(); k++) {
							List list = null;
							BlsInOutStockBillDetail af = (BlsInOutStockBillDetail) list2
									.get(k);

							if (jbImp.isSelected()
									&& af.getBsb().getIoFlag().equals(
											BlsIOStockBillIOF.IMPORT)) {
								if (af.getEntryGNo() == null
										|| af.getEntryGNo().equals("")
										&& (af.getEntryID() == null || af
												.getEntryID().equals(""))) {
									strErr += "此份单据编号为"
											+ af.getBsb().getBillNo() + "的入仓单据"
											+ "的第" + af.getSeqNo()
											+ "项商品信息的报关单号或者序号栏位为空\n";
								}
							}
						}
					}
				}
				if ((item.getCode().equals("01") || item.getCode()
						.equals("02"))
						&& !strErr.equals("")) {
					JOptionPane.showMessageDialog(
							DgMakeBillToWarehousewarrant.this, strErr,
							"提示！", 1);
					return false;
				}
			}
			
			if (temp2.size() != 0) {
				List lists = new ArrayList();
				for (int i = 0; i < temp2.size(); i++) {
					System.out.println("this temp2.size()=" + temp2.size());
					String id = null;
					BlsInOutStockBill blsInOutStockBill = (BlsInOutStockBill) temp2
							.get(i);
					WareSet ws = blsInOutStockBill.getWareHouseCode();
					id = blsInOutStockBill.getId();
					List listAfter = new ArrayList();
					List blsInnerMergeList = new ArrayList();
					sb = new StorageBill();
					sb.setBillNo(blsInOutStockBill.getBillNo());
					sb.setIePort((Customs) this.cbbImpExpPort.getSelectedItem());
					sb.setWareHouseCode(ws);
					sb.setWrap((Wrap) cbbPackagingType.getSelectedItem());
					sb.setEmsNo(tfEmsNo.getText());
					sb.setBillType(item.getCode());
					sb.setIoFlag("I");
					sb.setCustomsCode((Customs) cbbCustoms.getSelectedItem());
					sb = this.blsInOutStockBillAction.saveStorageBill(
							new Request(CommonVars.getCurrUser()), sb);
					List list2 = blsInOutStockBillAction
							.findBlsInOutStockBillDetailNot(new Request(
									CommonVars.getCurrUser()), id);
					System.out.println("this list2.size()=" + list2.size());

		
					for (int k = 0; k < list2.size(); k++) {
						BlsInOutStockBillDetail af = (BlsInOutStockBillDetail) list2
								.get(k);
						if (jbImp.isSelected()
								&& af.getBsb().getIoFlag().equals(
										BlsIOStockBillIOF.IMPORT)) {
							Materiel materiel = af.getPartNo();
							List bti = this.blsInOutStockBillAction
									.findBlsTenInnerMergeByMateriel(
											new Request(CommonVars
													.getCurrUser()), materiel);
							System.out.println("bti.size()=" + bti.size());
							StorageBillAfter storageBillAfter = new StorageBillAfter();
							BlsTenInnerMerge blsTenInnerMerge = (BlsTenInnerMerge) bti
									.get(0);
							List list = new ArrayList();
							BlsInnerMerge inner = new BlsInnerMerge();
							inner.setMateriel(materiel);
							inner.setBlsTenInnerMerge(blsTenInnerMerge);
							blsInnerMergeList.add(inner);
							Curr curr = af.getCurr();
							Country country = af.getOriginCountry() == null ? null
									: af.getOriginCountry();
							int seqNum = blsTenInnerMerge.getSeqNum();
							Complex codeTs = blsTenInnerMerge.getComplex();
							String name = blsTenInnerMerge.getName();
							String model = blsTenInnerMerge.getSpec() == null ? ""
									: blsTenInnerMerge.getSpec();
							double totalPrice = af.getTotalPrice() == null ? 0.0
									: af.getTotalPrice();

							System.out.println("totalPrice=" + totalPrice);
							double price = af.getUnitPrice() == null ? 0.0 : af
									.getUnitPrice();
							System.out.println("price=" + price);

							// 申报单位
							Unit unit = af.getUnit() == null ? null : af
									.getUnit().getUnit();
							// 第一法定单位
							String fst = blsTenInnerMerge.getComplex()
									.getFirstUnit() == null ? ""
									: blsTenInnerMerge.getComplex()
											.getFirstUnit().getName();
							// 第二法定单位
							String set = blsTenInnerMerge.getComplex()
									.getSecondUnit() == null ? ""
									: blsTenInnerMerge.getComplex()
											.getSecondUnit().getName();

							// 备注一
							String remark1 = af.getRemarks1() == null ? "" : af
									.getRemarks1();
							// 备注二
							String remark2 = af.getRemarks2() == null ? "" : af
									.getRemarks2();
							// 申报数量
							double amount = af.getDetailQty() == null ? 0.0
									: af.getDetailQty();
							System.out.println("amount=" + amount);
							// 净重
							double netWeight = af.getNetWeight() == null ? 0.0
									: af.getNetWeight();
							
							// 账册序号
							Integer emsNo=af.getEmsNo()==null?null:af.getEmsNo();
							
							// 单位与法定数量控制
							if (unit != null
									&& !unit.getName().trim().equals("")) {
								if (fst.equals(unit.getName().trim())) {
									storageBillAfter.setQty1(amount);
								}
								if (set.equals(unit.getName().trim())) {
									storageBillAfter.setQty2(amount);
								}
								if (set.equals("千克")
										&& !unit.getName().equals("千克")) {
									// storageBillAfter.setQty1(netWeight);
									storageBillAfter.setQty2(netWeight);
								}
								if ((fst.equals("千克"))
										&& !unit.getName().equals("千克")) {
									storageBillAfter.setQty1(netWeight);
									// storageBillAfter.setQty2(netWeight);
								}
								if ((fst.equals("个"))
										&& unit.getName().equals("千个")) {
									storageBillAfter.setQty1(Double
											.valueOf(CommonVars
													.formatDoubleToString(
															(amount * 1000),
															999, 3)));
									// storageBillAfter.setQty2(netWeight);
								} else if ((fst.equals("千个"))
										&& unit.getName().equals("个")) {

									storageBillAfter.setQty1(Double
											.valueOf(CommonVars
													.formatDoubleToString(
															(amount / 1000),
															999, 3)));
								}
								if ((set.equals("千克"))
										&& !unit.getName().equals("千克")) {
									// storageBillAfter.setQty1(netWeight);
									storageBillAfter.setQty2(netWeight);
								}
								if ((set.equals("个"))
										&& unit.getName().equals("千个")) {
									storageBillAfter.setQty2(Double
											.valueOf(CommonVars
													.formatDoubleToString(
															(amount * 1000),
															999, 3)));
									// storageBillAfter.setQty2(netWeight);
								} else if ((set.equals("千个"))
										&& unit.getName().equals("个")) {
									storageBillAfter.setQty2(Double
											.valueOf(CommonVars
													.formatDoubleToString(
															(amount / 1000),
															999, 3)));
								}
							}

							if (af.getEntryGNo() != null
									&& !af.getEntryGNo().equals("")) {
								String ss = af.getEntryGNo() == null ? "" : af
										.getEntryGNo();
								Integer entryGNo = Integer.valueOf(ss);
								storageBillAfter.setEntryGNo(entryGNo);
							}
							if (af.getEntryID() != null
									&& !af.getEntryID().equals("")) {
								String entryID = af.getEntryID() == null ? ""
										: af.getEntryID();
								storageBillAfter.setEntryID(entryID);
							}
							storageBillAfter.setStorageBill(sb);
							storageBillAfter.setQty(amount);
							storageBillAfter.setSeqNum(seqNum);
							storageBillAfter.setCodeTS(codeTs);
							storageBillAfter.setName(name);
							storageBillAfter.setApprTime(af.getBsb()
									.getValidDate());
							storageBillAfter.setCurr(curr);
							storageBillAfter.setOriginCountry(country);
							storageBillAfter.setSeqNo(k + 1);
							storageBillAfter.setModel(model);
							storageBillAfter.setUnitPrice(price);
							storageBillAfter.setTotalP(totalPrice);
							storageBillAfter.setUnit(unit);
							storageBillAfter.setRemarks1(remark1);
							storageBillAfter.setRemarks2(remark2);
							storageBillAfter.setContrItem(emsNo);
							list.add(storageBillAfter);
							listAfter.addAll(list);
							System.out.println("listAfter.size()="
									+ listAfter.size());
						}
					}
					blsInOutStockBillAction.addStorageBillBefore2(new Request(
							CommonVars.getCurrUser()), blsInnerMergeList, sb,
							listAfter, list2);
					// lists.addAll(list2);
				}
			}
		} else {
			if (rbNewWareHouseWarrant.isSelected()) {
				// if (getRbNewWareHouseWarrant().isSelected()) {
				ItemProperty item = (ItemProperty) cbbBillType
						.getSelectedItem();
				// if (rlist.size() > 20) {
				// JOptionPane.showMessageDialog(
				// DgMakeBillToWarehousewarrant.this,
				// "被选中的出入仓单据商品明细数量总合已超过20项！", "提示！", 1);
				// return false;
				// }

				if (jbImp.isSelected()) {
					String strErr = "";
					for (int k = 0; k < rlist.size(); k++) {
						List list = null;
						BlsInOutStockBillDetail af = (BlsInOutStockBillDetail) rlist
								.get(k);

						if (jbImp.isSelected()
								&& af.getBsb().getIoFlag().equals(
										BlsIOStockBillIOF.IMPORT)) {
							if (af.getEntryGNo() == null
									|| af.getEntryGNo().equals("")
									&& (af.getEntryID() == null || af
											.getEntryID().equals(""))) {
								strErr += "此份单据编号为" + af.getBsb().getBillNo()
										+ "的入仓单据" + "的第" + af.getSeqNo()
										+ "项商品信息的报关单号或者序号栏位为空\n";
							}

							// 若新增转进仓单，若选择单据类型为00，则允许报关单号与序号栏位为空
							// if (!item.getCode().equals("00")
							// && (af.getEntryGNo() == null || af
							// .getEntryGNo().equals(""))
							// && (af.getEntryID() == null || af.getEntryID()
							// .equals(""))) {
							//
							// JOptionPane.showMessageDialog(
							// DgMakeBillToWarehousewarrant.this,
							// "若新增仓单类型非申报初始库存类型\n" + "此份单据号为"
							// + af.getBsb().getBillNo()
							// + "的入仓单据报关单号与序号栏位不能为空！不能继续转仓单",
							// "提示！", 1);
							// return false;
							// }
						}
					}
					if (!item.getCode().equals("00") && !strErr.equals("")) {
						JOptionPane.showMessageDialog(
								DgMakeBillToWarehousewarrant.this, strErr,
								"提示！", 1);
						return false;
					}
					// 判断所选择的商品明晰在不在归并关系中内
					String str = "";
					List s = new ArrayList();
					WareSet ws = null;
					for (int a = 0; a < rlist.size(); a++) {
						BlsInOutStockBillDetail af = (BlsInOutStockBillDetail) rlist
								.get(a);
						ws = af.getBsb().getWareHouseCode();
						Materiel materiel = af.getPartNo();
						s = this.blsInOutStockBillAction
								.findBlsTenInnerMergeByMateriel(new Request(
										CommonVars.getCurrUser()), materiel);
						if (s.size() == 0) {
							str += "您所选择的商品料号为" + af.getPartNo().getPtNo()
									+ "不在归并关系内！不能继续转仓单" + "\n";
						}
					}
					if (s.size() == 0) {
						JOptionPane.showMessageDialog(
								DgMakeBillToWarehousewarrant.this, str, "提示！",
								1);
						return false;
					}

					List listAfter = new ArrayList();
					List blsInnerMergeList = new ArrayList();
					this.sb = null;
					sb = new StorageBill();
					sb.setIePort((Customs) this.cbbImpExpPort.getSelectedItem());
					sb.setWareHouseCode(ws);
					sb.setWrap((Wrap) cbbPackagingType.getSelectedItem());
					sb.setBillNo(tfWarehouseBillNo.getText());
					sb.setEmsNo(tfEmsNo.getText());
					sb.setBillType(item.getCode());
					sb.setIoFlag("I");
					sb.setCustomsCode((Customs) cbbCustoms.getSelectedItem());
					sb = this.blsInOutStockBillAction.saveStorageBill(
							new Request(CommonVars.getCurrUser()), sb);
					for (int k = 0; k < rlist.size(); k++) {
						BlsInOutStockBillDetail af = (BlsInOutStockBillDetail) rlist
								.get(k);
						if (jbImp.isSelected()
								&& af.getBsb().getIoFlag().equals(
										BlsIOStockBillIOF.IMPORT)) {
							Materiel materiel = af.getPartNo();
							List bti = this.blsInOutStockBillAction
									.findBlsTenInnerMergeByMateriel(
											new Request(CommonVars
													.getCurrUser()), materiel);
							System.out.println("bti.size()=" + bti.size());
							StorageBillAfter storageBillAfter = new StorageBillAfter();
							BlsTenInnerMerge blsTenInnerMerge = (BlsTenInnerMerge) bti
									.get(0);
							List lists = new ArrayList();
							BlsInnerMerge inner = new BlsInnerMerge();
							inner.setMateriel(materiel);
							inner.setBlsTenInnerMerge(blsTenInnerMerge);
							blsInnerMergeList.add(inner);
							Curr curr = af.getCurr();
							Country country = af.getOriginCountry() == null ? null
									: af.getOriginCountry();
							int seqNum = blsTenInnerMerge.getSeqNum();
							Complex codeTs = blsTenInnerMerge.getComplex();
							String name = blsTenInnerMerge.getName();
							String model = blsTenInnerMerge.getSpec() == null ? ""
									: blsTenInnerMerge.getSpec();
							double totalPrice = af.getTotalPrice() == null ? 0.0
									: af.getTotalPrice();

							System.out.println("totalPrice=" + totalPrice);
							double price = af.getUnitPrice() == null ? 0.0 : af
									.getUnitPrice();
							System.out.println("price=" + price);

							// 申报单位
							Unit unit = af.getUnit() == null ? null : af
									.getUnit().getUnit();
							// 第一法定单位
							String fst = blsTenInnerMerge.getComplex()
									.getFirstUnit() == null ? ""
									: blsTenInnerMerge.getComplex()
											.getFirstUnit().getName();
							// 第二法定单位
							String set = blsTenInnerMerge.getComplex()
									.getSecondUnit() == null ? ""
									: blsTenInnerMerge.getComplex()
											.getSecondUnit().getName();

							// 备注一
							String remark1 = af.getRemarks1() == null ? "" : af
									.getRemarks1();
							// 备注二
							String remark2 = af.getRemarks2() == null ? "" : af
									.getRemarks2();
							// 申报数量
							double amount = af.getDetailQty() == null ? 0.0
									: af.getDetailQty();
							System.out.println("amount=" + amount);
							// 净重
							double netWeight = af.getNetWeight() == null ? 0.0
									: af.getNetWeight();
							
							// 账册序号
							Integer emsNo=af.getEmsNo()==null?null:af.getEmsNo();
							// 单位与法定数量控制
							if (unit != null
									&& !unit.getName().trim().equals("")) {
								if (fst.equals(unit.getName().trim())) {
									storageBillAfter.setQty1(amount);
								}
								if (set.equals(unit.getName().trim())) {
									storageBillAfter.setQty2(amount);
								}
								if (set.equals("千克")
										&& !unit.getName().equals("千克")) {
									// storageBillAfter.setQty1(netWeight);
									storageBillAfter.setQty2(netWeight);
								}
								if ((fst.equals("千克"))
										&& !unit.getName().equals("千克")) {
									storageBillAfter.setQty1(netWeight);
									// storageBillAfter.setQty2(netWeight);
								}
								if ((fst.equals("个"))
										&& unit.getName().equals("千个")) {
									storageBillAfter.setQty1(Double
											.valueOf(CommonVars
													.formatDoubleToString(
															(amount * 1000),
															999, 3)));
									// storageBillAfter.setQty2(netWeight);
								} else if ((fst.equals("千个"))
										&& unit.getName().equals("个")) {

									storageBillAfter.setQty1(Double
											.valueOf(CommonVars
													.formatDoubleToString(
															(amount / 1000),
															999, 3)));
								}
								if ((set.equals("千克"))
										&& !unit.getName().equals("千克")) {
									// storageBillAfter.setQty1(netWeight);
									storageBillAfter.setQty2(netWeight);
								}
								if ((set.equals("个"))
										&& unit.getName().equals("千个")) {
									storageBillAfter.setQty2(Double
											.valueOf(CommonVars
													.formatDoubleToString(
															(amount * 1000),
															999, 3)));
									// storageBillAfter.setQty2(netWeight);
								} else if ((set.equals("千个"))
										&& unit.getName().equals("个")) {
									storageBillAfter.setQty2(Double
											.valueOf(CommonVars
													.formatDoubleToString(
															(amount / 1000),
															999, 3)));
								}
							}

							if (af.getEntryGNo() != null
									&& !af.getEntryGNo().equals("")) {
								String ss = af.getEntryGNo() == null ? "" : af
										.getEntryGNo();
								Integer entryGNo = Integer.valueOf(ss);
								storageBillAfter.setEntryGNo(entryGNo);
							}
							if (af.getEntryID() != null
									&& !af.getEntryID().equals("")) {
								String entryID = af.getEntryID() == null ? ""
										: af.getEntryID();
								storageBillAfter.setEntryID(entryID);
							}
							storageBillAfter.setStorageBill(sb);
							storageBillAfter.setQty(amount);
							storageBillAfter.setSeqNum(seqNum);
							storageBillAfter.setCodeTS(codeTs);
							storageBillAfter.setName(name);
							storageBillAfter.setApprTime(af.getBsb()
									.getValidDate());
							storageBillAfter.setCurr(curr);
							storageBillAfter.setOriginCountry(country);
							storageBillAfter.setSeqNo(k + 1);
							storageBillAfter.setModel(model);
							storageBillAfter.setUnitPrice(price);
							storageBillAfter.setTotalP(totalPrice);
							storageBillAfter.setUnit(unit);
							storageBillAfter.setRemarks1(remark1);
							storageBillAfter.setRemarks2(remark2);
							storageBillAfter.setContrItem(emsNo);
							lists.add(storageBillAfter);
							listAfter.addAll(lists);
							System.out.println("listAfter.size()="
									+ listAfter.size());
						}
					}
					blsInOutStockBillAction.addStorageBillBefore2(new Request(
							CommonVars.getCurrUser()), blsInnerMergeList, sb,
							listAfter, rlist);
				} else if (jbExp.isSelected()) {
					List blsInnerMergeList = new ArrayList();
					List<StorageBillAfter> listAfter = new ArrayList<StorageBillAfter>();

					// 检查出仓单据对应的入仓单据的单据号码和单据序号是否存在系统内
					String str = "";
					String str2 = "";
					String str3 = "";
					String str4 = "";
					List lists = new ArrayList();
					List list2 = new ArrayList();
					for (int i = 0; i < rlist.size(); i++) {
						BlsInOutStockBillDetail af = (BlsInOutStockBillDetail) rlist
								.get(i);

						String inBillNo = af.getInBillNo();
						System.out.println("inBillNo=" + inBillNo);
						Integer seqNo = af.getInBillGoodNo();

						System.out.println("seqNo=" + seqNo);
						lists = this.blsInOutStockBillAction
								.findBlsInOutStockBillInByBillNo(new Request(
										CommonVars.getCurrUser()), inBillNo);
						System.out.println("this lists.size()=" + lists.size());
						if (lists.size() == 0) {
							// str4 += af.getPartNo().getPtNo();
							str += "此份单据编号为:" + af.getBsb().getBillNo()
									+ "的出仓单据的第：" + af.getSeqNo()
									+ "项商品信息不在系统中的入仓单据内 ,提示！\n";
						}

						if (lists.size() != 0) {
							list2 = this.blsInOutStockBillAction
									.findBlsInOutStockBillInByBillNoAndSeqNo(
											new Request(CommonVars
													.getCurrUser()), inBillNo,
											seqNo);
							System.out.println("list2.size()=" + list2.size());
							if (list2.size() == 0) {
								str2 += "此份单据编号为:" + af.getBsb().getBillNo()
										+ "的出仓单据的第：" + af.getSeqNo()
										+ "项商品信息所对应的入仓单据序号不存在系统中的入仓单据内，提示！\n";
							}
						}
					}
					System.out.println("这里的lists.size()=" + lists.size());
					System.out.println("这里的list2.size()=" + list2.size());
					if (!str.equals("") || !str2.equals("")) {
						JOptionPane.showMessageDialog(
								DgMakeBillToWarehousewarrant.this, str + str2,
								"提示！", 1);
						return false;
					}
					if (!str.equals("")) {
						JOptionPane.showMessageDialog(
								DgMakeBillToWarehousewarrant.this, str, "提示！",
								1);
						return false;
					}
					if (!str2.equals("")) {
						JOptionPane.showMessageDialog(
								DgMakeBillToWarehousewarrant.this, str2, "提示！",
								1);
						return false;
					}

					// 检查出仓单据内的入仓单据是否已经转成入仓单
					List exitList = new ArrayList();
					List exitList2 = new ArrayList();
					String exit = "";
					for (int i = 0; i < rlist.size(); i++) {
						BlsInOutStockBillDetail af = (BlsInOutStockBillDetail) rlist
								.get(i);
						System.out.println("SSSSSSS");
						String inBillNo = af.getInBillNo();
						Integer seqNo = af.getInBillGoodNo();
						exitList = this.blsInOutStockBillAction
								.findBlsInOutStockBillInByBillNoAndSeqNo(
										new Request(CommonVars.getCurrUser()),
										inBillNo, seqNo);
						System.out
								.println("exitList.size()=" + exitList.size());
						if (exitList.size() != 0) {
							for (int j = 0; j < exitList.size(); j++) {
								BlsInOutStockBillDetail bsd = (BlsInOutStockBillDetail) exitList
										.get(j);
								exitList2 = this.blsInOutStockBillAction
										.findBlsInOutStockBillDeInByBlsInOutStockBillDetail(
												new Request(CommonVars
														.getCurrUser()), bsd);
								System.out.println("exitList2.size()="
										+ exitList2.size());
								if (exitList2.size() == 0) {
									exit += "此份单据编号为:"
											+ af.getBsb().getBillNo()
											+ "的出仓单据的第：" + af.getSeqNo()
											+ "项商品对应的入仓单据未转成入仓单" + "\n";
								}
							}
						}
					}
					if (exitList2.size() == 0) {
						JOptionPane.showMessageDialog(
								DgMakeBillToWarehousewarrant.this, exit, "提示！",
								1);
						return false;
					}

					// 检查出仓单据的数量是否大于对应来源的入仓单据
					String strs = "";
					double sum = 0.0;
					double sums = 0.0;
					for (int i = 0; i < rlist.size(); i++) {
						BlsInOutStockBillDetail af = (BlsInOutStockBillDetail) rlist
								.get(i);
						sum = af.getDetailQty();
						String inBillNos = af.getInBillNo();
						System.out.println("inBillNos=" + inBillNos);
						int inBillGoodNo = af.getInBillGoodNo();
						System.out.println("inBillGoodNo=" + inBillGoodNo);
						sums = blsInOutStockBillAction
								.findDetailQtyByBlsInStockBillNos(new Request(
										CommonVars.getCurrUser()), inBillNos,
										inBillGoodNo);
						System.out.println("sums=" + sums);
						if (sum > sums) {
							strs += "此项料号为:" + af.getPartNo().getPtNo()
									+ "出仓单商品数量大于来源数量，不能继续转仓单\n";
						}
					}
					if (sum > sums) {
						JOptionPane.showMessageDialog(
								DgMakeBillToWarehousewarrant.this, strs, "提示！",
								1);
						return false;
					}

					String strss = "";
					List btis = new ArrayList();
					for (int i = 0; i < rlist.size(); i++) {
						BlsInOutStockBillDetail af = (BlsInOutStockBillDetail) rlist
								.get(i);
						Materiel materiel = af.getPartNo();
						btis = this.blsInOutStockBillAction
								.findBlsTenInnerMergeByMateriel(new Request(
										CommonVars.getCurrUser()), materiel);
						if (btis.size() == 0) {
							strss += "您所选择的商品料号为" + af.getPartNo().getPtNo()
									+ "不在归并关系内！不能继续转仓单" + "\n";
						}
					}
					if (btis.size() == 0) {
						JOptionPane.showMessageDialog(
								DgMakeBillToWarehousewarrant.this, strss,
								"提示！", 1);
						return false;
					}

					String err = "";
					Integer inBillGoodNo = null;
					String inBillNo = "";
					WareSet ws = null;
					for (int i = 0; i < rlist.size(); i++) {
						BlsInOutStockBillDetail af = (BlsInOutStockBillDetail) rlist
								.get(i);
						ws = af.getBsb().getWareHouseCode();
						inBillGoodNo = af.getInBillGoodNo();
						inBillNo = af.getInBillNo();
						if (jbExp.isSelected()
								&& af.getBsb().getIoFlag().equals(
										BlsIOStockBillIOF.EXPORT)) {
							if (af.getInBillGoodNo() == null
									&& af.getInBillNo() == null) {
								err += "选中的出仓单据体料号为:"
										+ af.getPartNo().getPtNo()
										+ "\n的商品的入仓单号码与商品序号为空！不能继续转仓单";
							}
						}
					}
					if (inBillGoodNo == null && inBillNo == null) {
						JOptionPane.showMessageDialog(
								DgMakeBillToWarehousewarrant.this, err, "提示！",
								1);
						return false;
					}

					this.sb = null;
					sb = new StorageBill();
					sb
							.setIePort((Customs) this.cbbImpExpPort
									.getSelectedItem());
					sb.setWareHouseCode(ws);
					sb.setWrap((Wrap) cbbPackagingType.getSelectedItem());
					sb.setBillNo(tfWarehouseBillNo.getText());
					sb.setEmsNo(tfEmsNo.getText());
					sb.setBillType(item.getCode());
					sb.setIoFlag("O");
					sb.setCustomsCode((Customs) cbbCustoms.getSelectedItem());
					sb = this.blsInOutStockBillAction.saveStorageBill(
							new Request(CommonVars.getCurrUser()), sb);
					for (int i = 0; i < rlist.size(); i++) {
						System.out.println("rlist.size()=" + rlist.size());
						BlsInOutStockBillDetail bsd = (BlsInOutStockBillDetail) rlist
								.get(i);
						List listAdd = new ArrayList();
						if (jbExp.isSelected()
								&& bsd.getBsb().getIoFlag().equals(
										BlsIOStockBillIOF.EXPORT)) {

							System.out.println("rlist.size()=" + rlist.size());

							String s = bsd.getInBillNo();

							System.out.println("s=" + s);

							Materiel materiel = bsd.getPartNo();

							int seqNum = bsd.getSeqNum() == null ? 0 : bsd
									.getSeqNum();
							List list = blsInOutStockBillAction
									.findBlsInnerMerge(new Request(CommonVars
											.getCurrUser()), materiel, seqNum);
							// blsInnerMergeList.addAll(list);

							List bti = this.blsInOutStockBillAction
									.findBlsTenInnerMergeByMateriel(
											new Request(CommonVars
													.getCurrUser()), materiel);

							System.out.println("bti.size()=" + bti.size());

							StorageBillAfter storageBillAfter = new StorageBillAfter();
							BlsTenInnerMerge blsTenInnerMerge = (BlsTenInnerMerge) bti
									.get(0);
							List lis = new ArrayList();
							BlsInnerMerge inner = new BlsInnerMerge();
							inner.setMateriel(materiel);
							inner.setBlsTenInnerMerge(blsTenInnerMerge);
							blsInnerMergeList.add(inner);
							int seqNums = blsTenInnerMerge.getSeqNum();
							Curr curr = bsd.getCurr();
							Country country = bsd.getOriginCountry() == null ? null
									: bsd.getOriginCountry();
							Complex codeTs = blsTenInnerMerge.getComplex();
							String name = blsTenInnerMerge.getName();
							String model = blsTenInnerMerge.getSpec() == null ? ""
									: blsTenInnerMerge.getSpec();

							String remark1 = bsd.getRemarks1() == null ? "" : bsd
									.getRemarks1();
							String remark2 = bsd.getRemarks2() == null ? "" : bsd
									.getRemarks2();
							double totalPrice = bsd.getTotalPrice() == null ? 0.0
									: bsd.getTotalPrice();
							System.out.println("totalPrice=" + totalPrice);
							double price = bsd.getUnitPrice() == null ? 0.0 : bsd
									.getUnitPrice();

							// 申报单位
							Unit unit = bsd.getUnit() == null ? null : bsd
									.getUnit().getUnit();
							// 第一法定单位
							String fst = blsTenInnerMerge.getComplex()
									.getFirstUnit() == null ? ""
									: blsTenInnerMerge.getComplex()
											.getFirstUnit().getName();
							// 第二法定单位
							String set = blsTenInnerMerge.getComplex()
									.getSecondUnit() == null ? ""
									: blsTenInnerMerge.getComplex()
											.getSecondUnit().getName();
							System.out.println("set=" + set);
							double amount = bsd.getDetailQty() == null ? 0.0
									: bsd.getDetailQty();

							double netWeight = bsd.getNetWeight() == null ? 0.0
									: bsd.getNetWeight();
							// 账册序号
							Integer emsNo=bsd.getEmsNo()==null?null:bsd.getEmsNo();
							if (unit != null
									&& !unit.getName().trim().equals("")) {
								if (fst.equals(unit.getName().trim())) {
									storageBillAfter.setQty1(amount);
								}
								if (set.equals(unit.getName().trim())) {
									storageBillAfter.setQty2(amount);
								}
								if ((fst.equals("千克"))
										&& !unit.getName().equals("千克")) {
									storageBillAfter.setQty1(netWeight);
									// storageBillAfter.setQty2(netWeight);
								}
								if ((fst.equals("个"))
										&& unit.getName().equals("千个")) {
									storageBillAfter.setQty1(Double
											.valueOf(CommonVars
													.formatDoubleToString(
															(amount * 1000),
															999, 3)));
									// storageBillAfter.setQty2(netWeight);
								} else if ((fst.equals("千个"))
										&& unit.getName().equals("个")) {

									storageBillAfter.setQty1(Double
											.valueOf(CommonVars
													.formatDoubleToString(
															(amount / 1000),
															999, 3)));
								}
								if ((set.equals("千克"))
										&& !unit.getName().equals("千克")) {
									// storageBillAfter.setQty1(netWeight);
									storageBillAfter.setQty2(netWeight);
								}
								if ((set.equals("个"))
										&& unit.getName().equals("千个")) {
									storageBillAfter.setQty2(Double
											.valueOf(CommonVars
													.formatDoubleToString(
															(amount * 1000),
															999, 3)));
									// storageBillAfter.setQty2(netWeight);
								} else if ((set.equals("千个"))
										&& unit.getName().equals("个")) {
									storageBillAfter.setQty2(Double
											.valueOf(CommonVars
													.formatDoubleToString(
															(amount / 1000),
															999, 3)));
								}
							}

							// Integer seqNo=af.getSeqNo();
							System.out
									.println("af.getSeqNo()=" + bsd.getSeqNo());
							System.out.println("name=" + name);
							storageBillAfter.setSeqNo(i + 1);
							storageBillAfter.setCurr(curr);
							storageBillAfter.setOriginCountry(country);
							storageBillAfter.setStorageBill(sb);
							storageBillAfter.setSeqNum(seqNums);
							storageBillAfter.setCodeTS(codeTs);
							storageBillAfter.setName(name);
							storageBillAfter.setModel(model);
							storageBillAfter.setBlsDocuments(s);
							storageBillAfter.setContrItem(emsNo);
							storageBillAfter.setInBillGoodNo(bsd
									.getInBillGoodNo());
							storageBillAfter.setUnitPrice(price);
							storageBillAfter.setTotalP(totalPrice);
							storageBillAfter.setUnit(unit);
							storageBillAfter.setQty(amount);
							storageBillAfter.setRemarks1(remark1);
							storageBillAfter.setRemarks2(remark2);
							listAdd.add(storageBillAfter);
							listAfter.addAll(listAdd);
							System.out.println("listAfter.size()="
									+ listAfter.size());
						}
					}
					blsInOutStockBillAction.addStorageBillBefore2(new Request(
							CommonVars.getCurrUser()), blsInnerMergeList, sb,
							listAfter, rlist);
				}
			} else if (getRbToOldWareHouseWarrant().isSelected()) {
				if (sb == null) {
					JOptionPane.showMessageDialog(
							DgMakeBillToWarehousewarrant.this, "请选择要追加的仓单！"

							, "提示！", 1);
					return false;
				}
				int liist = blsInOutStockBillAction
						.findMaxStorageBillAfterSeqNo(new Request(CommonVars
								.getCurrUser()), sb);

				System.out.println("liist=" + liist);

				System.out.println("rlist.size()=" + rlist.size());

				System.out.println("liist + rlist.size()="
						+ (liist + rlist.size()));

				if (liist + rlist.size() > 20) {
					JOptionPane.showMessageDialog(
							DgMakeBillToWarehousewarrant.this,
							"被选中的出入仓单据商品明细数量为:" + rlist.size()
									+ "  原仓单的商品明细数量为: " + liist
									+ "\n  其商品明细总合已超过20项, 不能转换！"

							, "提示！", 1);

					return false;
				}
				List arrayList = new ArrayList();
				// BlsInnerMerge inner=new BlsInnerMerge();
				for (int k = 0; k < rlist.size(); k++) {
					BlsInOutStockBillDetail af = (BlsInOutStockBillDetail) rlist
							.get(k);
					BlsInnerMerge inner = new BlsInnerMerge();
					System.out.println("af.getPartNo()=" + af.getPartNo());
					inner.setMateriel(af.getPartNo());
					arrayList.add(inner);
				}

				System.out.println("arrayList.size()=" + arrayList.size());

				blsInOutStockBillAction.addStorageBillBefores(new Request(
						CommonVars.getCurrUser()), arrayList, sb);
			}
		}
		return true;
	}

	/**
	 * 执行进程
	 * 
	 * @author hw
	 * 
	 */
	class execute extends Thread {
		public void run() {
			btnExecute.setEnabled(false);
			try {
				CommonProgress
						.showProgressDialog(DgMakeBillToWarehousewarrant.this);
				CommonProgress.setMessage("系统正在执行数据，请稍后...");

				// CustomsDeclaration cc = encAction
				// .makeBilllistsToCustomDeclaretions(new Request(
				// CommonVars.getCurrUser()), para
				// .getApplyToCustomsBillList(), para, sb, rlist);
				// if (cc == null) {
				// JOptionPane.showMessageDialog(
				// DgMakeBillToWarehousewarrant.this,
				// "转报关单失败！\n 所选的商品已转报关单！", "提示！", 1);
				// return;
				// } else {
				// CommonProgress.closeProgressDialog();
				// JOptionPane.showMessageDialog(
				// DgMakeBillToWarehousewarrant.this,
				// "转报关单成功！\n 报关单流水号为"
				// + (cc.getSerialNumber() == null ? "空" : cc
				// .getSerialNumber()), "提示！", 1);
				// }

			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				e.printStackTrace();
				JOptionPane.showMessageDialog(
						DgMakeBillToWarehousewarrant.this, "执行数据失败：！"
								+ e.getMessage(), "提示", 2);
			} finally {
				rlist.clear();
			}
		}
	}

	/**
	 * This method initializes btnNextStep
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnNextStep() {
		if (btnNextStep == null) {
			btnNextStep = new JButton();
			btnNextStep.setText("下一步");
			btnNextStep.setForeground(Color.blue);
			btnNextStep.setPreferredSize(new Dimension(50, 28));
			btnNextStep.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!checkData()) {
						return;
					}
					addStep(step);
				}
			});
		}
		return btnNextStep;
	}

	/**
	 * 检查必填数据
	 * 
	 * @return
	 */
	private boolean checkData() {
		if (this.tfEmsNo.getText().equals("") && this.tfEmsNo.getText() == null) {
			JOptionPane.showMessageDialog(DgMakeBillToWarehousewarrant.this,
					"账册编号必需填写！", "提示！", JOptionPane.WARNING_MESSAGE);
			return false;
		}

		if (this.cbOneToOne.isSelected()) {
			String warehouseBillNo = this.tfWarehouseBillNo.getText();
			if (warehouseBillNo == null || warehouseBillNo.equals("")) {
				return true;
			}
		} else {
			String warehouseBillNo = this.tfWarehouseBillNo.getText();
			if (warehouseBillNo == null || warehouseBillNo.equals("")) {
				JOptionPane.showMessageDialog(
						DgMakeBillToWarehousewarrant.this, "仓单号必需填写！", "提示！",
						JOptionPane.WARNING_MESSAGE);
				return false;
			}
		}

		// if (this.tfWarehouseBillNo.getText().equals("")
		// && this.tfWarehouseBillNo.getText() == null) {
		// JOptionPane.showMessageDialog(DgMakeBillToWarehousewarrant.this,
		// "仓单号必需填写！", "提示！", JOptionPane.WARNING_MESSAGE);
		// return false;
		// }

		if (cbbCustoms.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(DgMakeBillToWarehousewarrant.this,
					"申报海关必需选择！", "提示！", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (cbbPackagingType.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(DgMakeBillToWarehousewarrant.this,
					"包装种类必需选择！", "提示！", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (cbbImpExpPort.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(DgMakeBillToWarehousewarrant.this,
					"进出口岸必需选择！", "提示！", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (cbbBillType.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(DgMakeBillToWarehousewarrant.this,
					"仓单类型必需选择！", "提示！", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}

	/**
	 * 下一步
	 * 
	 * @param step
	 */
	private void addStep(int step) {
		if (step == 0) {
			if (getRbToOldWareHouseWarrant().isSelected() && sb == null) {
				JOptionPane.showMessageDialog(
						DgMakeBillToWarehousewarrant.this, " ！", "提示！",
						JOptionPane.WARNING_MESSAGE);
				return;
			} else if (rbNewWareHouseWarrant.isSelected()) {
				if (!checkData()) {
					return;
				}
			}

			// if (!cbImp.isSelected()) {
			// JOptionPane.showMessageDialog(
			// DgMakeBillToWarehousewarrant.this, "没有选择进仓类型！", "提示！",
			// JOptionPane.WARNING_MESSAGE);
			// return;
			// }

			getJContentPane().remove(getJPanel1());
			getJContentPane().add(getJPanel3(), BorderLayout.CENTER);
			jPanel3.add(getJPanel51(), BorderLayout.NORTH);
			initTable();
			getBtnLastStep().setEnabled(true);
			lbHeadText.setText("第二步：选择所要转的出入仓单据");
			this.setVisible(true);
			this.repaint();
			this.step++;

		} else if (step == 1) {
			List temp = new ArrayList();
			List arl = this.tableModelBlsInOutStock.getList();
			for (int m = 0; m < arl.size(); m++) {
				BlsInOutStockBill bsb = (BlsInOutStockBill) arl.get(m);
				if (bsb.getIsSelected() != null && bsb.getIsSelected()) {
					temp.add(bsb);
				}
			}
			if (temp.size() == 0) {
				JOptionPane.showMessageDialog(
						DgMakeBillToWarehousewarrant.this, "没有选择出入仓单据！", "提示！",
						JOptionPane.WARNING_MESSAGE);
				return;
			}
			getJContentPane().remove(getJPanel3());
			getJContentPane().add(getJPanel4(), BorderLayout.CENTER);
			jPanel4.add(getJPanel51(), BorderLayout.NORTH);

			List lists = new ArrayList();
			for (int i = 0; i < temp.size(); i++) {
				String id = null;
				BlsInOutStockBill blsInOutStockBill = (BlsInOutStockBill) temp
						.get(i);
				id = blsInOutStockBill.getId();
				List list2 = blsInOutStockBillAction
						.findBlsInOutStockBillDetailNot(new Request(CommonVars
								.getCurrUser()), id);
				lists.addAll(list2);

				// System.out.println("list2.size()=" + list2.size());
			}
			System.out.println("lists.size()=" + lists.size());
			List mertList = this.blsInOutStockBillAction
					.mergerBlsInOutStockCommodityInfo(new Request(CommonVars
							.getCurrUser()), lists, jbImp.isSelected(), jbExp
							.isSelected());
			if (this.cbOneToOne.isSelected()) {
				initTable1(lists);
			} else {
				initTable1(mertList);
			}

			// List
			// list2=blsInOutStockBillAction.findBlsInOutStockBillDetail(new
			// Request(CommonVars
			// .getCurrUser()),temp);

			getBtnNextStep().setEnabled(false);
			getBtnExecute().setEnabled(true);
			lbHeadText.setText("第三步：选择所要转的出入仓单据体");
			this.setVisible(true);
			this.repaint();
			this.step++;
		}
	}

	// 检查是不是限制类商品并且是否超出了限制类商品中定义的数量
	// public boolean checkRerictCommodity(List commodityList) {
	// double DeclarationCommInfo = 0;
	// double Amount = 0;
	// double commodityamount = 0;
	// String seqNum = null;
	//       
	// Collection cl = new Vector();
	// Collection commoditycl = new Vector();
	// Collection c2 = new Vector();
	// // List commodityList = this.encAction.findAtcMergeAfterComInfoByParents(
	// // new Request(CommonVars.getCurrUser()), arl);
	//
	// for (int i = 0; i < commodityList.size(); i++) {
	// AtcMergeAfterComInfo tt = (AtcMergeAfterComInfo) commodityList
	// .get(i);
	// seqNum = tt.getEmsSerialNo() == null ? "" : tt.getEmsSerialNo()
	// .toString();
	// Boolean isMaterial = EncCommon.isMaterial(tt.getBillList()
	// .getImpExpType());
	//
	// if (seqNum != null && !seqNum.equals("")) {
	//
	// // 得到报关单中的数量
	// DeclarationCommInfo = materialManageAction
	// .findCustomsDeclarationCommInfo(new Request(CommonVars
	// .getCurrUser()), isMaterial, seqNum,null);
	//
	// Amount = DeclarationCommInfo
	// + Double.valueOf(tt.getDeclaredAmount().toString());
	//
	// RestirictCommodity commodity = manualDeclearAction
	// .findRerictCommodity(new Request(CommonVars
	// .getCurrUser()), isMaterial, seqNum);
	//
	// if (commodity != null) {
	// if (commodity.getAmount() != null
	// && !commodity.getAmount().equals("")) {
	// commodityamount = Double.parseDouble(commodity
	// .getAmount().toString());
	// if (Amount > commodityamount) {
	// cl.add(seqNum);
	// commoditycl.add(Amount);
	// c2.add(commodity.getAmount().toString());
	// }
	// }
	// }
	// }
	// }
	// // if (commoditycl.size() > 0 && cl.size() > 0) {
	// // if (JOptionPane.showConfirmDialog(this, "备案号："
	// // + cl+ "已进(出)数量[" + commoditycl
	// // + "] 超出了限制类商品中备的数量[" + c2 + "]!\n", "提示", -1) == 0) {
	// // return true;
	// // }
	// // }
	// return false;
	// }

	/**
	 * 上一步
	 */
	private void mupStep(int step) {
		if (step == 1) {
			getJContentPane().remove(getJPanel3());
			getJContentPane().add(getJPanel1(), BorderLayout.CENTER);

			getBtnNextStep().setEnabled(true);
			getBtnLastStep().setEnabled(false);
			lbHeadText.setText("第一步：选择生成仓单生成方式 ");
			this.repaint();
			// parent.clear();

			// initTable();
		} else if (step == 2) {
			getJContentPane().remove(getJPanel4());
			getJContentPane().add(getJPanel3(), BorderLayout.CENTER);
			jPanel3.add(getJPanel51(), BorderLayout.NORTH);
			getBtnNextStep().setEnabled(true);
			getBtnExecute().setEnabled(false);
			lbHeadText.setText("第二步：选择所要转的出入仓单据");
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
			lbHeadText.setText("第一步：选择生成仓单生成方式 ");
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
			gridBagConstraints.gridheight = 1;
			gridBagConstraints.gridy = 0;
			jPanel1 = new JPanel();
			jPanel1.setLayout(new GridBagLayout());
			jPanel1.add(getJPanel(), gridBagConstraints);
			jPanel1.add(getJPanel2(), gridBagConstraints1);
		}
		return jPanel1;
	}

	/**
	 * 初始化单据头选择表体
	 */
	private void initTable() {
		List list = new ArrayList();
		if (jbImp.isSelected()) {
			list = blsInOutStockBillAction.findBlsInOutStockBillNotSwitch(
					new Request(CommonVars.getCurrUser()),
					BillImpExpFlag.IMPORT);
			// System.out.println("list.siza()=" + list.size());
		} else if (jbExp.isSelected()) {
			list = blsInOutStockBillAction.findBlsInOutStockBillNotSwitch(
					new Request(CommonVars.getCurrUser()),
					BillImpExpFlag.EXPORT);
		}
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List list = new Vector();
				list.add(addColumn("选择", "isSelected", 40));
				list.add(addColumn("进出仓标志", "ioFlag", 100));
				list.add(addColumn("单据编号", "billNo", 100));
				list.add(addColumn("单据生效日期", "validDate", 100));
				list.add(addColumn("供货方企业", "corrOwner.name", 100));
				list.add(addColumn("仓库编码", "wareHouseCode.code", 50));
				return list;
			}
		};

		tableModelBlsInOutStock = new JTableListModel(getTbBlsInOutStock(),
				list, jTableListModelAdapter);
		jTableListModelAdapter.setEditableColumn(1);
		getTbBlsInOutStock().getColumnModel().getColumn(1).setCellRenderer(
				new TableCheckBoxRender());
		getTbBlsInOutStock().getColumnModel().getColumn(1).setCellEditor(
				new CheckBoxEditor(new JCheckBox()));

		getTbBlsInOutStock().getColumnModel().getColumn(2).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue(value));
						return this;
					}

					private String castValue(Object value) {
						return BlsIOStockBillIOF.getImpExpFlagSpec(value
								.toString());
					}
				});

	}

	/**
	 * 初始化单据商品明细表体
	 * 
	 * @param arl
	 */
	private void initTable1(List arl) {
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List list = new Vector();
				list.add(addColumn("选择", "isSelected", 40));
				list.add(addColumn("原产国", "originCountry.name", 100));
				list.add(addColumn("企业内部货号", "partNo.ptNo", 100));
				list.add(addColumn("数量", "detailQty", 80));
				list.add(addColumn("申报单价", "unitPrice", 100));
				list.add(addColumn("币制", "curr.name", 100));
				list.add(addColumn("毛重", "grossWeight", 60));
				list.add(addColumn("净重", "netWeight", 60));
				list.add(addColumn("件数", "packCount", 60));
				list.add(addColumn("已全转仓单", "isStockBill", 60));
				return list;
			}
		};

		tableModelBlsInOutStockDetail = new JTableListModel(
				getTbBlsInOutStockDetail(), arl, jTableListModelAdapter);
		jTableListModelAdapter.setEditableColumn(1);
		getTbBlsInOutStockDetail().getColumnModel().getColumn(1)
				.setCellRenderer(new TableCheckBoxRender());
		getTbBlsInOutStockDetail().getColumnModel().getColumn(1).setCellEditor(
				new CheckBoxEditor(new JCheckBox()));
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
			jPanel3.setSize(new Dimension(201, 119));
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
			jScrollPane.setViewportView(getTbBlsInOutStock());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes tbBlsInOutStock
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbBlsInOutStock() {
		if (tbBlsInOutStock == null) {
			tbBlsInOutStock = new JTable();
		}
		return tbBlsInOutStock;
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
			jScrollPane1.setViewportView(getTbBlsInOutStockDetail());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes tbBlsInOutStockDetail
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbBlsInOutStockDetail() {
		if (tbBlsInOutStockDetail == null) {
			tbBlsInOutStockDetail = new JTable();
		}
		return tbBlsInOutStockDetail;
	}

	/**
	 * This method initializes btnLastStep
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnLastStep() {
		if (btnLastStep == null) {
			btnLastStep = new JButton();
			btnLastStep.setText("上一步");
			btnLastStep.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					mupStep(step);
					step--;
				}
			});
			btnLastStep.setEnabled(false);
		}
		return btnLastStep;
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
			if (obj instanceof BlsInOutStockBill) {
				BlsInOutStockBill temp = (BlsInOutStockBill) obj;
				temp.setIsSelected(new Boolean(cb.isSelected()));
				tableModel.updateRow(obj);
			} else if (obj instanceof BlsInOutStockBillDetail) {
				BlsInOutStockBillDetail temp = (BlsInOutStockBillDetail) obj;
				temp.setIsSelected(new Boolean(cb.isSelected()));
				tableModel.updateRow(obj);
			}
			fireEditingStopped();
		}
	}

	/**
	 * This method initializes tfWareHouseWarrantCode
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfWareHouseWarrantCode() {
		if (tfWareHouseWarrantCode == null) {
			tfWareHouseWarrantCode = new JTextField();
			tfWareHouseWarrantCode.setBounds(new Rectangle(371, 19, 176, 24));
			tfWareHouseWarrantCode.setEditable(false);
		}
		return tfWareHouseWarrantCode;
	}

	/**
	 * This method initializes btnChoose
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnChoose() {
		if (btnChoose == null) {
			btnChoose = new JButton();
			btnChoose.setBounds(new Rectangle(546, 19, 23, 24));
			btnChoose.setText("...");
			btnChoose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					if ((getRbToOldWareHouseWarrant().isSelected()
							&& !jbImp.isSelected() && !jbExp.isSelected())
							|| ((getRbToOldWareHouseWarrant().isSelected()
									&& !jbExp.isSelected() && !jbImp
									.isSelected()))) {
						JOptionPane.showMessageDialog(
								DgMakeBillToWarehousewarrant.this, "请选择进出仓类型！",
								"提示！", JOptionPane.WARNING_MESSAGE);
						return;
					}
					Object obj = findWareHouseWarrant();
					if (obj != null) {
						sb = (StorageBill) obj;
						tfWareHouseWarrantCode.setText(sb.getBillNo());
						// + " / "
						// + (cm.getSerialNumber() == null ? " " : cm
						// .getSerialNumber().toString()));
					}

				}
			});
		}
		return btnChoose;
	}

	/**
	 * 根据进出仓类型抓取已生效仓单资料
	 * 
	 * @param projectType
	 * @return
	 */
	public Object findWareHouseWarrant() {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("仓单号", "billNo", 100));
		list.add(new JTableListColumn("仓库编码", "wareHouseCode.code", 100));
		list.add(new JTableListColumn("进出仓标志", "ioFlag", 80));
		list.add(new JTableListColumn("生效", "effective", 100));
		list.add(new JTableListColumn("车次号", "delivery.deliveryNo", 100));
		list.add(new JTableListColumn("申报状态", "delivery.declareState", 100));
		list.add(new JTableListColumn("仓单类型", "billType", 100));
		list.add(new JTableListColumn("申报海关", "customsCode.name", 100));
		list.add(new JTableListColumn("仓单有效期", "validDate", 90));
		list.add(new JTableListColumn("订单号", "orderNo", 100));
		list.add(new JTableListColumn("计划编号", "planNo", 100));
		list.add(new JTableListColumn("总件数", "packCount", 100));
		list.add(new JTableListColumn("包装种类", "wrap.name", 100));
		list.add(new JTableListColumn("供货商", "supplierCd.name", 150));
		list.add(new JTableListColumn("供货方企业", "corrOwnerCode.name", 150));
		list.add(new JTableListColumn("毛重", "grossWeight", 80));
		list.add(new JTableListColumn("净重", "netWeight", 80));
		list.add(new JTableListColumn("手册号", "manualNo", 100));
		list.add(new JTableListColumn("帐册编号", "emsNo", 100));
		list.add(new JTableListColumn("商品项数", "itemsCount", 90));
		list.add(new JTableListColumn("进出口岸", "iePort.name", 100));
		list.add(new JTableListColumn("转出方名称", "outName", 150));
		list.add(new JTableListColumn("转入方名称", "inName", 150));
		DgCommonQueryPage.setTableColumns(list);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {

			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				if (jbImp.isSelected()) {

					return blsInOutStockBillAction.findStorageBillByInOut(
							new Request(CommonVars.getCurrUser()),
							BillImpExpFlag.IMPORT);
				} else if (jbExp.isSelected()) {

					return blsInOutStockBillAction.findStorageBillByInOut(
							new Request(CommonVars.getCurrUser()),
							BillImpExpFlag.EXPORT);
				}
				// EncAction encAction = (EncAction) CommonVars
				// .getApplicationContext().getBean("encAction");
				// return encAction.findCustomsDeclarationByImpExpFlag(
				// new Request(CommonVars.getCurrUser()), impExpFlag);
				return null;
			}

			@Override
			public void doSomethingBeforeVisable(JTable table) {
				table.getColumnModel().getColumn(4).setCellRenderer(
						new TableCheckBoxRender());
				table.getColumnModel().getColumn(5).setCellRenderer(
						new TableCheckBoxRender());
				table.getColumnModel().getColumn(3).setCellRenderer(
						new DefaultTableCellRenderer() {
							public Component getTableCellRendererComponent(
									JTable table, Object value,
									boolean isSelected, boolean hasFocus,
									int row, int column) {
								super.getTableCellRendererComponent(table,
										value, isSelected, hasFocus, row,
										column);
								if (value == null
										|| value.toString().trim().equals("")) {
									super.setText("");
								} else if (value.equals(BillImpExpFlag.IMPORT)) {
									super.setText("进仓");
								} else if (value.equals(BillImpExpFlag.EXPORT)) {
									super.setText("出仓");
								} else {
									super.setText("");
								}
								return this;
							}

						});
			}
		};
		dgCommonQuery.setTitle("请选择仓单！");
		DgCommonQueryPage.setSingleResult(true);

		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	// /**
	// * This method initializes jPanel5
	// *
	// * @return javax.swing.JPanel
	// */
	// private JPanel getJPanel5() {
	// if (jPanel5 == null) {
	// jPanel5 = new JPanel();
	// FlowLayout fl = new FlowLayout();
	// fl.setAlignment(FlowLayout.RIGHT);
	// jPanel5.setLayout(new FlowLayout());
	// jPanel5.setSize(new Dimension(263, 33));
	// jPanel5.add(getJRadioButton2(), null);
	// jPanel5.add(getJRadioButton3(), null);
	// jPanel5.add(getJRadioButton4(), null);
	// }
	// return jPanel5;
	// }
	//
	// /**
	// * This method initializes jRadioButton2
	// *
	// * @return javax.swing.JRadioButton
	// */
	// private JRadioButton getJRadioButton2() {
	// if (jRadioButton2 == null) {
	// jRadioButton2 = new JRadioButton();
	// jRadioButton2.setText("全选");
	// }
	// return jRadioButton2;
	// }

	/**
	 * This method initializes rbChooseNot
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbChooseNot() {
		if (rbChooseNot == null) {
			rbChooseNot = new JRadioButton();
			rbChooseNot.setText("全否");
		}
		return rbChooseNot;
	}

	/**
	 * This method initializes rb
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRb() {
		if (rb == null) {
			rb = new JRadioButton();
			rb.setText("反选");
		}
		return rb;
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
			jPanel51.add(getRbChioceAll(), null);
			jPanel51.add(getRbChioceNot(), null);
		}
		return jPanel51;
	}

	/**
	 * This method initializes rbChioceAll
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbChioceAll() {
		if (rbChioceAll == null) {
			rbChioceAll = new JRadioButton();
			rbChioceAll.setText("全选");
			rbChioceAll.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (getRbChioceAll().isSelected()) {
						setSelectedAll(true);
					}
				}
			});
		}
		return rbChioceAll;
	}

	private ButtonGroup getButtonGroups() {
		if (bg == null) {
			bg = new ButtonGroup();
			bg.add(getRbChioceAll());
			bg.add(getRbChioceNot());
		}
		return bg;
	}

	/**
	 * This method initializes rbChioceNot
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbChioceNot() {
		if (rbChioceNot == null) {
			rbChioceNot = new JRadioButton();
			rbChioceNot.setText("不选");
			rbChioceNot.setSelected(true);
			rbChioceNot.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (getRbChioceNot().isSelected()) {
						setSelectedAll(false);
					}
				}
			});
		}
		return rbChioceNot;
	}

	/**
	 * 设置是否全选
	 * 
	 * @param isAll
	 *            Boolean变量
	 */
	private void setSelectedAll(boolean isAll) {
		if (step == 1) {
			List dlist = tableModelBlsInOutStock.getList();
			for (int i = 0; i < dlist.size(); i++) {
				Object obj = dlist.get(i);
				if (obj instanceof BlsInOutStockBill) {
					BlsInOutStockBill temp = (BlsInOutStockBill) obj;
					temp.setIsSelected(isAll);
				}
			}
			tableModelBlsInOutStock.getTable().repaint();
		} else if (step == 2) {
			List dlist = tableModelBlsInOutStockDetail.getList();
			for (int i = 0; i < dlist.size(); i++) {
				Object obj = dlist.get(i);
				if (obj instanceof BlsInOutStockBillDetail) {
					BlsInOutStockBillDetail temp = (BlsInOutStockBillDetail) obj;
					temp.setIsSelected(isAll);
				}
			}
			tableModelBlsInOutStockDetail.getTable().repaint();
		}
	}

	/**
	 * This method initializes cbbBillType
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbBillType() {
		if (cbbBillType == null) {
			cbbBillType = new JComboBox();
			cbbBillType.setBounds(new Rectangle(389, 70, 131, 23));
		}
		return cbbBillType;
	}

	/**
	 * This method initializes tfWarehouseBillNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfWarehouseBillNo() {
		if (tfWarehouseBillNo == null) {
			tfWarehouseBillNo = new JTextField();
			tfWarehouseBillNo.setBounds(new Rectangle(389, 119, 131, 23));
		}
		return tfWarehouseBillNo;
	}

	/**
	 * This method initializes jbImp
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJbImp() {
		if (jbImp == null) {
			jbImp = new JRadioButton();
			jbImp.setBounds(new Rectangle(21, 40, 57, 21));
			jbImp.setText("进仓");
			jbImp.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					BlsInOutStockSwichParameterSet other = null;// 以下是系统参数设置
					other = blsInOutStockBillAction
							.findBlsSwitchParameterSetByInOutFlag(new Request(
									CommonVars.getCurrUser()),
									BlsIOStockBillIOF.IMPORT);
					if (other != null) {
						cbbCustoms.setSelectedItem((Customs) other
								.getDeclarationCustoms());
						cbbImpExpPort.setSelectedItem((Customs) other
								.getCustoms());
						tfEmsNo.setText(other.getEmsNo());
						cbbPackagingType.setSelectedItem((Wrap) other
								.getWrapType());
						String btype = other.getBillType();
						if (btype != null && !btype.trim().equals("")) {
							int s = cbbBillType.getModel().getSize();
							for (int i = 0; i < s; i++) {
								Object obj = cbbBillType.getModel()
										.getElementAt(i);
								if (obj instanceof ItemProperty) {
									ItemProperty itemp = (ItemProperty) obj;
									if (itemp.getCode().equals(btype)) {
										cbbBillType.setSelectedItem(itemp);
									}
								}
							}
						}
						// cbbBillType.setSelectedItem((String) other
						// .getBillType());
					}
				}
			});
		}
		return jbImp;
	}

	/**
	 * This method initializes jbExp
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJbExp() {
		if (jbExp == null) {
			jbExp = new JRadioButton();
			jbExp.setBounds(new Rectangle(211, 41, 84, 21));
			jbExp.setText("出仓");
			jbExp.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					BlsInOutStockSwichParameterSet other = null;// 以下是系统参数设置
					other = blsInOutStockBillAction
							.findBlsSwitchParameterSetByInOutFlag(new Request(
									CommonVars.getCurrUser()),
									BlsIOStockBillIOF.EXPORT);
					if (other != null) {
						cbbCustoms.setSelectedItem((Customs) other
								.getDeclarationCustoms());
						cbbImpExpPort.setSelectedItem((Customs) other
								.getCustoms());
						tfEmsNo.setText(other.getEmsNo());
						cbbPackagingType.setSelectedItem((Wrap) other
								.getWrapType());
						String btype = other.getBillType();
						if (btype != null && !btype.trim().equals("")) {
							int s = cbbBillType.getModel().getSize();
							for (int i = 0; i < s; i++) {
								Object obj = cbbBillType.getModel()
										.getElementAt(i);
								if (obj instanceof ItemProperty) {
									ItemProperty itemp = (ItemProperty) obj;
									if (itemp.getCode().equals(btype)) {
										cbbBillType.setSelectedItem(itemp);
									}
								}
							}
						}
					}
				}
			});
		}
		return jbExp;
	}

	/**
	 * This method initializes cbOneToOne
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbOneToOne() {
		if (cbOneToOne == null) {
			cbOneToOne = new JCheckBox();
			cbOneToOne.setBounds(new Rectangle(85, 20, 130, 21));
			cbOneToOne.setText("单据与仓单一对一");
			cbOneToOne.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					tfWarehouseBillNo.setEnabled(!cbOneToOne.isSelected());
				}
			});
		}
		return cbOneToOne;
	}

	/*
	 * // -------------------------------------- // AtcMergeAfterComInfo dt =
	 * (AtcMergeAfterComInfo) rlist.get(0); // ApplyToCustomsBillList app =
	 * dt.getBillList(); // para.setDelcarationDate(para.getDelcarationDate() ==
	 * null ? app // .getListDeclareDate() : para.getDelcarationDate());// 申报日期
	 * // para.setImpExpType(para.getImpExpType());// 进出口类型 //
	 * para.setTransf(para.getTransf() == null ? app.getTransportMode() // :
	 * para.getTransf());// 运输方式 // para.setTrade(para.getTrade() == null ?
	 * app.getTradeMode() : para // .getTrade());// 贸易方式 //
	 * para.setMemo(para.getMemo() == null ? app.getMemos() : para //
	 * .getMemo());// 备注 // para //
	 * .setDeclarationCustoms(para.getDeclarationCustoms() == null ? app //
	 * .getDeclareCIQ() // : para.getDeclarationCustoms());// 申报海关 //
	 * para.setCustoms(para.getCustoms() == null ? app.getImpExpCIQ() // :
	 * para.getCustoms());// 进出口岸 //
	 * ------------------------------------如果没有填写，则会默认第一份清单
	 */

	// if (k != 0 && k % 20 == 0) {
	// try {// 超过二十项就拆分到另一个新的进仓单内
	// sb = (StorageBill) BeanUtils.cloneBean(sb);
	// sb.setIoFlag("I");
	// sb.setWrap((Wrap) jComboBox12.getSelectedItem());
	// sb.setBillNo(tfWarehouseBillNo.getText());
	// sb.setEmsNo(tfEmsNo.getText());
	// sb.setBillType(item.getName());
	// sb.setIoFlag("I");
	// sb.setCustomsCode((Customs) cbbCustoms
	// .getSelectedItem());
	// sb = this.blsInOutStockBillAction.saveStorageBill(
	// new Request(CommonVars.getCurrUser()), sb);
	// Materiel materiel = af.getPartNo();
	// List bti = this.blsInOutStockBillAction
	// .findBlsTenInnerMergeByMateriel(
	// new Request(CommonVars
	// .getCurrUser()), materiel);
	//
	// StorageBillAfter storageBillAfter = new StorageBillAfter();
	// BlsTenInnerMerge blsTenInnerMerge = (BlsTenInnerMerge) bti
	// .get(0);
	// List lists = new ArrayList();
	// BlsInnerMerge inner = new BlsInnerMerge();
	// inner.setMateriel(materiel);
	// inner.setBlsTenInnerMerge(blsTenInnerMerge);
	// blsInnerMergeList.add(inner);
	//
	// int seqNum = blsTenInnerMerge.getSeqNum();
	// Complex codeTs = blsTenInnerMerge.getComplex();
	//
	// storageBillAfter.setStorageBill(sb);
	// storageBillAfter.setSeqNum(seqNum);
	// storageBillAfter.setCodeTS(codeTs);
	// lists.add(storageBillAfter);
	// listAfter.addAll(lists);
	// } catch (IllegalAccessException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (InstantiationException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (InvocationTargetException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (NoSuchMethodException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// } else {
} // @jve:decl-index=0:visual-constraint="129,16"