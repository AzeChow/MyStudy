/*
 * Created on 2005-4-21
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.client.contractcav;

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
import java.awt.event.ItemEvent;
import java.io.InputStream;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcs.client.contractcav.report.ContractcavReportVars;
import com.bestway.bcs.client.contractcav.report.ContractcavReportVars2;
import com.bestway.bcs.client.contractcav.report.ContractcavReportVars3;
import com.bestway.bcs.client.dictpor.DgAllChangState;
import com.bestway.bcs.client.message.BcsMessageHelper;
import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcs.contract.entity.BcsParameterSet;
import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contractcav.action.ContractCavAction;
import com.bestway.bcs.contractcav.entity.ContractCav;
import com.bestway.bcs.contractcav.entity.ContractCavTotalValue;
import com.bestway.bcs.contractcav.entity.ContractExgCav;
import com.bestway.bcs.contractcav.entity.ContractImgCav;
import com.bestway.bcs.contractcav.entity.ContractUnitWasteCav;
import com.bestway.bcs.contractcav.entity.TempContractCheckCav;
import com.bestway.bcs.contractexe.entity.BcsCustomsDeclaration;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonStepProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomFormattedTextFieldUtils;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.system.entity.Company;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.mutispan.AttributiveCellTableModel;
import com.bestway.client.util.mutispan.MultiSpanCellTable;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.constant.ContractKind;
import com.bestway.common.constant.DeclareFileInfo;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.DzscBusinessType;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.common.materialbase.entity.CurrRate;
import com.bestway.customs.common.action.BaseEncAction;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * 刘民添加部分注释 修改时间: 2008-11-25
 * 
 * @author ? // change the template for this generated type comment go to Window
 *         - Preferences - Java - Code Style - Code Templates
 */
@SuppressWarnings({ "unchecked", "serial" })
public class DgContractCav extends JDialogBase {
	private javax.swing.JPanel pn = null;
	private JTabbedPane tpn = null;
	private JPanel pnHead = null;
	private JPanel pnExg = null;
	private JPanel pnImg = null;
	private JPanel pnBom = null;
	private JPanel pnOther = null;
	private JToolBar tb = null;
	private JRadioButton rbSelf = null;
	private JRadioButton rbCustoms = null;
	private ButtonGroup bgSelectModel = null; // @jve:decl-index=0:visual-constraint="743,146"
	private JButton btnClose = null;
	// private JComboBox cbbPrint = null;
	private JButton btnPrint = null;
	private JLabel lbCaption = null;
	private JLabel lbCaption1 = null;
	private JTextField tfEmsNo = null;
	private JTextField tfContractNo = null;
	private JLabel lbCaption2 = null;
	private JLabel lbCaption3 = null;
	private JTextField tfCompanyName = null;
	private JTextField tfTelephone = null;
	private JLabel lbCaption4 = null;
	private JLabel lbCaption5 = null;
	private JTextField tfEndDate = null;
	private JTextField tfEmsApprNo = null;
	private JLabel lbCaption6 = null;
	private JLabel lbCaption8 = null;
	private JLabel lbCaption9 = null;
	/** 格式化输入：进口总金额 */
	private JFormattedTextField tfImpTotalMoney = null;
	/** 格式化输入：出口总金额 */
	private JFormattedTextField tfExpTotalMoney = null;
	/** 格式化输入：加工费 */
	private JFormattedTextField tfProcessTotalPrice = null;
	private JLabel lbCurrency = null;
	private JLabel lbCaption10 = null;
	private JLabel lbCaption11 = null;
	/** 格式化输入：进口报关单份数 */
	private JFormattedTextField tfImpCDCount = null;
	/** 格式化输入：出口报关单份数 */
	private JFormattedTextField tfExpCDCount = null;
	private JLabel lbCaption12 = null;
	private JLabel lbCaption13 = null;
	private JLabel lbCaption14 = null;
	private JLabel lbCaption15 = null;
	private JTextField tfInternalSaleWarrant = null;
	private JTextField tfInternalSaleTaxBill = null;
	/** 格式化输入：内销金额 */
	private JFormattedTextField tfInternalSale = null;
	/** 格式化输入：内销补税金额 */
	private JFormattedTextField tfInternalSaleTax = null;
	private JLabel lbCaption16 = null;
	private JLabel lbCaption17 = null;
	/** 格式化输入：余料金额 */
	private JFormattedTextField tfRemainMoney = null;
	private JTextField tfRemainEmsNo = null;
	private JLabel lbCaption18 = null;
	private JLabel lbCaption19 = null;
	private JLabel lbCaption20 = null;
	private JLabel lbCaption21 = null;
	private JLabel lbCaption22 = null;
	private JTextField tfCurrency = null;
	/** 格式化输入：保证金额 */
	private JFormattedTextField tfCautionMoney = null;
	private JComboBox cbbIsSusceptivity = null;
	private JComboBox cbbIsLimit = null;
	private JButton btnReCav = null;
	private JButton btnSave = null;
	private JButton btnUndo = null;
	private JButton btnCheck = null;
	/** 格式化输入：总页数 */
	private JFormattedTextField tfTotalPages = null;
	private JTable tbExg = null;
	private JScrollPane spn = null;
	private JTable tbImg = null;
	private JScrollPane spn1 = null;
	private JTable tbBom = null;
	private JScrollPane spn2 = null;
	private JButton btnInteralBuy = null;
	private JButton btnDeleteImg = null;
	private JButton btnLeftoverMaterial = null;
	private JCheckBox cbImg = null;
	private JRadioButton rbTotal = null;
	private JRadioButton rbWaste = null;
	private JButton btnRoundAmount = null;
	private JCheckBox cbBom1 = null;
	private JCheckBox cbBom2 = null;
	private JCheckBox cbBom3 = null;
	private ButtonGroup bgAmount = null; // @jve:decl-index=0:visual-constraint="742,100"
	private JPanel pn6 = null;
	private JLabel lbCaption23 = null;
	private JLabel lbCaption24 = null;
	private JLabel lbCaption25 = null;
	private JLabel lbCaption26 = null;
	private JLabel lbCaption27 = null;
	private JLabel lbCaption28 = null;
	private JLabel lbCaption29 = null;
	private JLabel lbCaption30 = null;
	private JTextField tfImpTotalValue = null;
	private JTextField tfExpTotalValue = null;
	private JTextField tfExpManufactureMoney = null;
	private JTextField tfExpTotalItems = null;
	private JTextField tfImpTotalGrossWeight = null;
	private JTextField tfImpNetWeight = null;
	private JTextField tfExpTotalGrossWeight = null;
	private JTextField tfExpNetWeight = null;
	private JButton btnCal = null;
	private JPanel pnCustomsDeclaration = null;
	protected BaseEncAction baseEncAction = null;
	private JScrollPane spn3 = null;
	private ButtonGroup btnGroup = null; // @jve:decl-index=0:

	private JTable tbCD = null;
	/** 合同核销 */
	private ContractCavAction contractCavAction = null;
	/** 默认格式化工厂 */
	private DefaultFormatterFactory defaultFormatterFactory = null;
	/** 数字格式化 */
	private NumberFormatter numberFormatter = null;
	/** 合同核销表头 */
	private ContractCav contractCavSelf = null; // @jve:decl-index=0:
	/** 合同核销表头 */
	private ContractCav contractCavCustoms = null;
	/** 存放合同备案表头资料 */
	private Contract contract = null;
	/** 数据初始状态 */
	private int dataState = DataState.BROWSE;
	private JButton btnEdit = null;
	/** 核销报关单 */
	private JTableListModel tableModelCD = null;
	/** 核销成品表 */
	private JTableListModel tableModelExg = null;
	/** 核销料件表 */
	private JTableListModel tableModelImg = null;
	/** 核销单耗表 */
	private JTableListModel tableModelBom = null;
	/** 合同核销成品资料统计 */
	private int count = 0;
	private JButton btnEditExg = null;
	private JButton btnEditImg = null;
	private JButton btnEditUnitWaste = null;
	private ButtonGroup buttonGroup = null; // @jve:decl-index=0:visual-constraint="757,405"
	private JButton btnDeclare = null;
	private JButton btnProccess = null;
	/** 分隔符 */
	private JSeparator jSeparator = null;
	private JCheckBox cb = null;
	private JCheckBox cb1 = null;
	private JCheckBox cb2 = null;
	private JButton btnPrintCount = null;
	private JCheckBox cb3 = null; // @jve:decl-index=0:visual-constraint="794,203"
	private JCheckBox cb4 = null; // @jve:decl-index=0:visual-constraint="765,257"
	private JCheckBox cb5 = null;
	private JCheckBox cbIsPrintZeroAsNull;
	private JButton btnChangeDeclareState = null;
	/** 下拉列表：改变申报状态 */
	private JPopupMenu pmChangeDeclareState = null; // @jve:decl-index=0:visual-constraint="839,87"
	/** 取消申报 */
	private JMenuItem miUndoDeclare = null;
	private JLabel lbCaption31 = null;
	private JTextField tfdeclareState = null;
	/** 打印非套打核销表 */
	private JMenuItem miNonCoverPrintApplied = null;
	/** 打印套打核销表 */
	private JMenuItem miCoverPrintApplied = null;
	/** 打印非套打核销料件表 */
	private JMenuItem miNonCoverPrintAppliedImg = null;
	/** 打印套打核销料件表 */
	private JMenuItem miCoverPrintAppliedImg = null;
	/** 打印非套打核销成品表 */
	private JMenuItem miNonCoverPrintAppliedExg = null;
	/** 打印套打核销成品表 */
	private JMenuItem miCoverPrintAppliedExg = null;
	/** 打印非套打核销单耗表 */
	private JMenuItem miNonCoverPrintAppliedUnitConsumption = null;
	/** 打印套打核销单耗表 */
	private JMenuItem miCoverPrintAppliedUnitConsumption = null;
	/** 打印非套打核销表(新) */
	private JMenuItem miNonCoverPrintAppliedNew = null;

	/** 打印非套打核销表(新) */
	private JMenuItem miNonCoverPrintAppliedNewSmall = null;

	/** 打印非套打核销表(新) */
	private JMenuItem miNonCoverPrintAppliedNewBig = null;
	/** 打印非套打核销单耗表(新) */
	private JMenuItem miCoverPrintAppliedUnitConsumptionNew = null;
	/** 打印非套打合同核销申请（结案）表 */
	private JMenuItem miNonCoverPrintAppliedContractConsumptionCased = null;
	/** 下拉列表：打印 */
	private JPopupMenu jPopupMenuPrintReports = null;
	private JLabel lbCaption32 = null;
	private JTextField tfCoLevel = null;
	/** 外商投资企业加工贸易业务核销表 */
	private JMenuItem miForeignInvestment = null;
	/** 加工贸易业务核销清单(出口制成品部分) */
	private JMenuItem miProcessingTradeVerificationBillExprotExg = null;
	/** 加工贸易业务核销清单(进口料件部分) */
	private JMenuItem miProcessingTradeVerificationBillImportImg = null;
	/** 购物清单 */
	private JMenuItem miShopList = null;
	/** 打印非套打合同核销表 */
	private JMenuItem miPrintMiConverContract = null;
	/** 合同国内购料清单 */
	private JMenuItem miContractCavDomesticPurchaseBill = null;
	/** 备案总耗与实际总耗对照表 */
	private JMenuItem miBomFilingRealityCompare = null;
	private JLabel jLabel = null;
	private JCustomFormattedTextField tfValueAddedRate = null;
	private JLabel jLabel1 = null;
	private JTextField tfNetWeightLossRate = null;
	private JPanel pn7 = null;
	private JLabel jLabel2 = null;
	private JCustomFormattedTextField tfBackImpGoods = null;
	private JLabel jLabel3 = null;
	private JCustomFormattedTextField tfFollowTax = null;
	private JLabel jLabel4 = null;
	private JCustomFormattedTextField tfBackExpGoods = null;
	private JLabel jLabel5 = null;
	private JCustomFormattedTextField tfImpBackGoods = null;
	private JLabel jLabel6 = null;
	private JCustomFormattedTextField tfExpBackGoods = null;
	private JLabel jLabel7 = null;
	private JCustomFormattedTextField tfImportDeviceGross = null;
	private JPanel jPanel = null;
	private JButton btnChangeImgModifyMark = null;
	private JMenuItem miNotModified = null;
	private JPopupMenu pmChangeModifyMark = null;
	private ContractAction contractAction = null;
	private BcsParameterSet parameterSet = null;
	private String emsNo;

	private Double sumContractImgTotalPrice;

	private Double sumContractExgTotalPrice;

	public String getEmsNo() {
		return emsNo;
	}

	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}

	/**
	 * @return Returns the contract.
	 */
	public Contract getContract() {
		return contract;
	}

	/**
	 * @param contract
	 *            The contract to set.
	 */
	public void setContract(Contract contract) {
		this.contract = contract;
	}

	/**
	 * @return Returns the contractCavCustoms.
	 */
	public ContractCav getContractCavCustoms() {
		return contractCavCustoms;
	}

	/**
	 * @return Returns the contractCavSelf.
	 */
	public ContractCav getContractCavSelf() {
		return contractCavSelf;
	}

	/**
	 * @param contractCavCustoms
	 *            The contractCavCustoms to set.
	 */
	public void setContractCavCustoms(ContractCav contractCavCustoms) {
		this.contractCavCustoms = contractCavCustoms;
	}

	/**
	 * @param contractCavSelf
	 *            The contractCavSelf to set.
	 */
	public void setContractCavSelf(ContractCav contractCavSelf) {
		this.contractCavSelf = contractCavSelf;
	}

	/**
	 * This is the default constructor
	 */
	public DgContractCav() {
		super();
		initialize();
		this.getBgAmount();
		this.getBgSelectModel();
		tbBom = new MultiSpanCellTable();
		contractCavAction = (ContractCavAction) CommonVars
				.getApplicationContext().getBean("contractCavAction");
		contractAction = (ContractAction) CommonVars.getApplicationContext()
				.getBean("contractAction");

		parameterSet = contractAction.findBcsParameterSet(new Request(
				CommonVars.getCurrUser(), true));
		tbBom.getColumnModel().addColumnModelListener(
				new TableColumnModelListener() {
					/** Tells listeners that a column was added to the model. */
					public void columnAdded(TableColumnModelEvent e) {
					}

					/**
					 * Tells listeners that a column was removed from the model.
					 */
					public void columnRemoved(TableColumnModelEvent e) {
					}

					/** Tells listeners that a column was repositioned. */
					public void columnMoved(TableColumnModelEvent e) {
					}

					/**
					 * Tells listeners that a column was moved due to a margin
					 * change.
					 */
					public void columnMarginChanged(ChangeEvent e) {
					}

					public void columnSelectionChanged(ListSelectionEvent e) {
						int[] columns = ((MultiSpanCellTable) tbBom)
								.getSelectedColumns();
						int[] rows = ((MultiSpanCellTable) tbBom)
								.getSelectedRows();
						// int[] columns = jTable.getSelectedColumns();
						// int[] rows = jTable.getSelectedRows();
						if (columns.length < 1 || rows.length < 1) {
							return;
						}
						// for (int i = 0; i < rows.length; i++) {
						// System.out.println("row:" + rows[i]);
						// }
						if (columns[0] >= 0 && columns[0] < 4) {
							tbBom.setColumnSelectionInterval(1, 3);
							return;
						} else if (columns[0] >= 4 && columns[0] < 10) {
							tbBom.setColumnSelectionInterval(4, 9);
							return;
						}
						// else if (columns[0] >= 12 && columns[0] <= 16) {
						// tblBom.setColumnSelectionInterval(12, 16);
						// return;
						// }
					}
				});
		spn2.setViewportView(tbBom);
		// tfImpBackGoods.setText("0");
		// tfExpBackGoods.setText("0");
		// tfBackImpGoods.setText("0");
		// tfBackExpGoods.setText("0");
		// tfFollowTax.setText("0");
		// getMiShopList().setVisible(false);
		getMiProcessingTradeVerificationBillImportImg().setVisible(false);
		getMiProcessingTradeVerificationBillExprotExg().setVisible(false);
		getMiForeignInvestment().setVisible(false);
		getMiNonCoverPrintAppliedContractConsumptionCased().setVisible(false);
		getMiContractCavDomesticPurchaseBill().setVisible(false);
		getMiBomFilingRealityCompare().setVisible(false);
	}

	/**
	 * 设置控件可见性
	 */
	public void setVisible(boolean b) {
		if (b) {
			InitUIComponents();
			switchSelfCustoms(this.rbCustoms.isSelected());
			if (rbSelf.isSelected()) {
				showData(this.contractCavSelf);
			} else {
				showData(this.contractCavCustoms);
			}
			setState();
		}
		super.setVisible(b);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setSize(new Dimension(792, 560));
		this.setTitle("核销申请表计算");
		this.setSize(792, 538);
		this.setContentPane(getPn());
		this.getButtonGroup();
	}

	/**
	 * This method initializes pn
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getPn() {
		if (pn == null) {
			pn = new javax.swing.JPanel();
			pn.setLayout(new java.awt.BorderLayout());
			pn.add(getTpn(), java.awt.BorderLayout.CENTER);
			pn.add(getTb(), java.awt.BorderLayout.NORTH);
		}
		return pn;
	}

	/**
	 * This method initializes tpn
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getTpn() {
		if (tpn == null) {
			tpn = new JTabbedPane();
			tpn.addTab("海关核销表表头", null, getPnHead(), null);
			tpn.addTab("海关核销报关单", null, getPnCustomsDeclaration(), null);
			tpn.addTab("海关核销成品表", null, getPnExg(), null);
			tpn.addTab("海关核销料件表", null, getPnImg(), null);
			tpn.addTab("海关核销单耗表", null, getPnBom(), null);
			tpn.addTab("其他", null, getPnOther(), null);
			tpn.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					switch (((JTabbedPane) e.getSource()).getSelectedIndex()) {
					case 0:
						if (rbCustoms.isSelected()) {
							showData(contractCavCustoms);
						} else {
							showData(contractCavSelf);
						}
						break;
					case 1:
						showCustomsDeclaration(rbCustoms.isSelected());
						break;
					case 2:
						showExgData(rbCustoms.isSelected());
						break;
					case 3:
						showImgData(rbCustoms.isSelected());
						break;
					case 4:
						showBomData(rbCustoms.isSelected());
						break;
					case 5:
						// showElse(rbCustoms.isSelected());
						break;
					}
				}
			});
		}
		return tpn;
	}

	// private void showElse(boolean isCustoms) {
	// if (isCustoms) {
	// jPanel1.setVisible(false);
	// }
	// else{
	// jPanel1.setVisible(true);
	// }
	// }

	/**
	 * This method initializes pnHead
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnHead() {
		if (pnHead == null) {
			jLabel7 = new JLabel();
			jLabel7.setBounds(new Rectangle(464, 184, 73, 24));
			jLabel7.setText("进口设备总值");
			lbCaption32 = new JLabel();
			lbCaption32.setBounds(new Rectangle(373, 54, 89, 22));
			lbCaption32.setText("企业管理类别");
			lbCaption31 = new JLabel();
			lbCaption31.setBounds(new Rectangle(484, 325, 52, 24));
			lbCaption31.setForeground(Color.blue);
			lbCaption31.setText("申报状态");
			lbCaption22 = new JLabel();
			lbCaption21 = new JLabel();
			lbCaption20 = new JLabel();
			lbCaption19 = new JLabel();
			lbCaption18 = new JLabel();
			lbCaption17 = new JLabel();
			lbCaption16 = new JLabel();
			lbCaption15 = new JLabel();
			lbCaption14 = new JLabel();
			lbCaption13 = new JLabel();
			lbCaption12 = new JLabel();
			lbCaption11 = new JLabel();
			lbCaption10 = new JLabel();
			lbCurrency = new JLabel();
			lbCaption9 = new JLabel();
			lbCaption8 = new JLabel();
			lbCaption6 = new JLabel();
			lbCaption5 = new JLabel();
			lbCaption4 = new JLabel();
			lbCaption3 = new JLabel();
			lbCaption2 = new JLabel();
			lbCaption1 = new JLabel();
			lbCaption = new JLabel();
			pnHead = new JPanel();
			pnHead.setLayout(null);
			lbCaption.setText("手册号");
			lbCaption.setBounds(new Rectangle(58, 21, 98, 24));
			lbCaption1.setText("合同号");
			lbCaption1.setBounds(new Rectangle(374, 21, 87, 24));
			lbCaption2.setText("公司名称");
			lbCaption2.setBounds(new Rectangle(58, 53, 98, 24));
			lbCaption3.setText("电话");
			lbCaption3.setBounds(new Rectangle(504, 53, 39, 24));
			lbCaption4.setText("合同有效期");
			lbCaption4.setBounds(new Rectangle(58, 85, 98, 24));
			lbCaption5.setText("合同批准证号");
			lbCaption5.setBounds(new Rectangle(374, 85, 87, 24));
			lbCaption6.setText("进口总金额");
			lbCaption6.setBounds(new Rectangle(58, 118, 98, 24));
			lbCaption8.setText("出口总金额");
			lbCaption8.setBounds(new Rectangle(291, 118, 63, 24));
			lbCaption9.setText("加工费");
			lbCaption9.setBounds(new Rectangle(464, 118, 40, 24));
			lbCurrency.setText("JLabel");
			lbCurrency.setBounds(new Rectangle(597, 118, 47, 24));
			lbCaption10.setText("进口报关单份数");
			lbCaption10.setBounds(new Rectangle(58, 150, 98, 24));
			lbCaption11.setText("出口报关单份数");
			lbCaption11.setBounds(new Rectangle(374, 150, 87, 24));
			lbCaption12.setText("内销金额");
			lbCaption12.setBounds(new Rectangle(291, 183, 63, 24));
			lbCaption13.setText("内销补税金额");
			lbCaption13.setBounds(new Rectangle(58, 184, 98, 24));
			lbCaption14.setText("内销批准证号");
			lbCaption14.setBounds(new Rectangle(58, 218, 98, 24));
			lbCaption15.setText("内销补税单号");
			lbCaption15.setBounds(new Rectangle(374, 218, 87, 24));
			lbCaption16.setText("余料金额");
			lbCaption16.setBounds(new Rectangle(58, 254, 98, 24));
			lbCaption17.setText("转入余料手册编号");
			lbCaption17.setBounds(new Rectangle(360, 254, 101, 24));
			lbCaption18.setText("保证金额");
			lbCaption18.setBounds(new Rectangle(58, 290, 98, 24));
			lbCaption19.setText("是否敏感产品");
			lbCaption19.setBounds(new Rectangle(291, 290, 76, 24));
			lbCaption20.setText("是否限制产品");
			lbCaption20.setBounds(new Rectangle(464, 290, 77, 24));
			lbCaption21.setText("总页数");
			lbCaption21.setBounds(new Rectangle(58, 324, 98, 24));
			lbCaption22.setText("币制");
			lbCaption22.setBounds(new Rectangle(365, 324, 45, 24));
			pnHead.add(lbCaption, null);
			pnHead.add(lbCaption1, null);
			pnHead.add(getTfEmsNo(), null);
			pnHead.add(getTfContractNo(), null);
			pnHead.add(lbCaption2, null);
			pnHead.add(lbCaption3, null);
			pnHead.add(getTfCompanyName(), null);
			pnHead.add(getTfTelephone(), null);
			pnHead.add(lbCaption4, null);
			pnHead.add(lbCaption5, null);
			pnHead.add(getTfEndDate(), null);
			pnHead.add(getTfEmsApprNo(), null);
			pnHead.add(lbCaption6, null);
			pnHead.add(lbCaption8, null);
			pnHead.add(lbCaption9, null);
			pnHead.add(getTfImpTotalMoney(), null);
			pnHead.add(getTfExpTotalMoney(), null);
			pnHead.add(getBtnEdit(), null);
			pnHead.add(getTfProcessTotalPrice(), null);
			pnHead.add(lbCurrency, null);
			pnHead.add(lbCaption10, null);
			pnHead.add(lbCaption11, null);
			pnHead.add(getTfImpCDCount(), null);
			pnHead.add(getTfExpCDCount(), null);
			pnHead.add(lbCaption12, null);
			pnHead.add(lbCaption13, null);
			pnHead.add(lbCaption14, null);
			pnHead.add(lbCaption15, null);
			pnHead.add(getTfInternalSaleWarrant(), null);
			pnHead.add(getTfInternalSaleTaxBill(), null);
			pnHead.add(getTfInternalSale(), null);
			pnHead.add(getTfInternalSaleTax(), null);
			pnHead.add(lbCaption16, null);
			pnHead.add(lbCaption17, null);
			pnHead.add(getTfRemainMoney(), null);
			pnHead.add(getTfRemainEmsNo(), null);
			pnHead.add(lbCaption18, null);
			pnHead.add(lbCaption19, null);
			pnHead.add(lbCaption20, null);
			pnHead.add(lbCaption21, null);
			pnHead.add(lbCaption22, null);
			pnHead.add(getTfCurrency(), null);
			pnHead.add(getTfCautionMoney(), null);
			pnHead.add(getCbbIsSusceptivity(), null);
			pnHead.add(getCbbIsLimit(), null);
			pnHead.add(getBtnReCav(), null);
			pnHead.add(getTfTotalPages(), null);
			pnHead.add(getBtnSave(), null);
			pnHead.add(getBtnUndo(), null);
			pnHead.add(getBtnCheck(), null);
			pnHead.add(getJSeparator(), null);
			pnHead.add(getCb(), null);
			pnHead.add(getCb1(), null);
			pnHead.add(getCb2(), null);
			pnHead.add(lbCaption31, null);
			pnHead.add(getTfdeclareState(), null);
			pnHead.add(lbCaption32, null);
			pnHead.add(getJTextField(), null);
			pnHead.add(jLabel7, null);
			pnHead.add(getTfImportDeviceGross(), null);
		}
		return pnHead;
	}

	/**
	 * This method initializes pnExg
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnExg() {
		if (pnExg == null) {
			GridBagConstraints gridBagConstraints51 = new GridBagConstraints();
			gridBagConstraints51.insets = new java.awt.Insets(4, 28, 18, 599);
			gridBagConstraints51.gridy = 1;
			gridBagConstraints51.ipadx = 23;
			gridBagConstraints51.ipady = -2;
			gridBagConstraints51.gridx = 0;
			GridBagConstraints gridBagConstraints50 = new GridBagConstraints();
			gridBagConstraints50.fill = java.awt.GridBagConstraints.BOTH;
			gridBagConstraints50.gridx = 0;
			gridBagConstraints50.gridy = 0;
			gridBagConstraints50.ipadx = 255;
			gridBagConstraints50.ipady = -37;
			gridBagConstraints50.weightx = 1.0;
			gridBagConstraints50.weighty = 1.0;
			gridBagConstraints50.insets = new java.awt.Insets(1, 0, 3, 0);
			pnExg = new JPanel();
			pnExg.setLayout(new GridBagLayout());
			pnExg.add(getSpn(), gridBagConstraints50);
			pnExg.add(getJButton6(), gridBagConstraints51);
		}
		return pnExg;
	}

	/**
	 * This method initializes pnImg
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnImg() {
		if (pnImg == null) {
			GridBagConstraints gridBagConstraints57 = new GridBagConstraints();
			gridBagConstraints57.insets = new java.awt.Insets(5, 12, 14, 23);
			gridBagConstraints57.gridy = 1;
			gridBagConstraints57.ipadx = 12;
			gridBagConstraints57.ipady = -4;
			gridBagConstraints57.gridx = 3;
			GridBagConstraints gridBagConstraints56 = new GridBagConstraints();
			gridBagConstraints56.insets = new java.awt.Insets(7, 23, 17, 75);
			gridBagConstraints56.gridy = 1;
			gridBagConstraints56.ipadx = 12;
			gridBagConstraints56.ipady = -7;
			gridBagConstraints56.gridx = 4;
			GridBagConstraints gridBagConstraints55 = new GridBagConstraints();
			gridBagConstraints55.insets = new java.awt.Insets(5, 14, 14, 11);
			gridBagConstraints55.gridy = 1;
			gridBagConstraints55.ipadx = 8;
			gridBagConstraints55.ipady = -4;
			gridBagConstraints55.gridx = 2;
			GridBagConstraints gridBagConstraints54 = new GridBagConstraints();
			gridBagConstraints54.insets = new java.awt.Insets(5, 14, 14, 13);
			gridBagConstraints54.gridy = 1;
			gridBagConstraints54.ipadx = 7;
			gridBagConstraints54.ipady = -4;
			gridBagConstraints54.gridx = 1;
			GridBagConstraints gridBagConstraints53 = new GridBagConstraints();
			gridBagConstraints53.insets = new java.awt.Insets(5, 13, 14, 14);
			gridBagConstraints53.gridy = 1;
			gridBagConstraints53.ipadx = 7;
			gridBagConstraints53.ipady = -4;
			gridBagConstraints53.gridx = 0;
			GridBagConstraints gridBagConstraints52 = new GridBagConstraints();
			gridBagConstraints52.fill = java.awt.GridBagConstraints.BOTH;
			gridBagConstraints52.gridwidth = 5;
			gridBagConstraints52.gridx = 0;
			gridBagConstraints52.gridy = 0;
			gridBagConstraints52.ipadx = 253;
			gridBagConstraints52.ipady = -34;
			gridBagConstraints52.weightx = 1.0;
			gridBagConstraints52.weighty = 1.0;
			gridBagConstraints52.insets = new java.awt.Insets(1, 1, 5, 0);
			pnImg = new JPanel();
			pnImg.setLayout(new GridBagLayout());
			pnImg.add(getSpn1(), gridBagConstraints52);
			pnImg.add(getBtnInteralBuy(), gridBagConstraints53);
			pnImg.add(getBtnDeleteImg(), gridBagConstraints54);
			pnImg.add(getBtnLeftoverMaterial(), gridBagConstraints55);
			pnImg.add(getCbImg(), gridBagConstraints56);
			pnImg.add(getBtnEditImg(), gridBagConstraints57);
		}
		return pnImg;
	}

	/**
	 * This method initializes pnBom
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnBom() {
		if (pnBom == null) {
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints.gridx = 3;
			gridBagConstraints.gridy = 2;
			GridBagConstraints gridBagConstraints65 = new GridBagConstraints();
			gridBagConstraints65.insets = new Insets(8, 18, 11, 21);
			gridBagConstraints65.gridx = 1;
			gridBagConstraints65.gridy = 2;
			gridBagConstraints65.ipadx = 9;
			gridBagConstraints65.ipady = -3;
			gridBagConstraints65.gridheight = 2;
			GridBagConstraints gridBagConstraints64 = new GridBagConstraints();
			gridBagConstraints64.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints64.gridheight = -1;
			gridBagConstraints64.gridx = 1;
			gridBagConstraints64.gridy = 1;
			gridBagConstraints64.ipadx = -325;
			gridBagConstraints64.ipady = -26;
			gridBagConstraints64.gridwidth = -1;
			GridBagConstraints gridBagConstraints62 = new GridBagConstraints();
			gridBagConstraints62.anchor = GridBagConstraints.WEST;
			gridBagConstraints62.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints62.insets = new Insets(0, 13, 5, 0);
			gridBagConstraints62.gridx = 4;
			gridBagConstraints62.gridy = 3;
			gridBagConstraints62.ipadx = 2;
			gridBagConstraints62.ipady = -13;
			gridBagConstraints62.gridwidth = -1;
			GridBagConstraints gridBagConstraints61 = new GridBagConstraints();
			gridBagConstraints61.insets = new Insets(2, 0, 5, 5);
			gridBagConstraints61.gridx = 3;
			gridBagConstraints61.gridy = 3;
			gridBagConstraints61.ipadx = 8;
			gridBagConstraints61.ipady = -3;
			GridBagConstraints gridBagConstraints60 = new GridBagConstraints();
			gridBagConstraints60.insets = new Insets(1, 21, 8, 5);
			gridBagConstraints60.gridy = 3;
			gridBagConstraints60.ipadx = 10;
			gridBagConstraints60.ipady = -11;
			gridBagConstraints60.gridx = 2;
			GridBagConstraints gridBagConstraints59 = new GridBagConstraints();
			gridBagConstraints59.insets = new Insets(4, 21, 5, 5);
			gridBagConstraints59.gridy = 2;
			gridBagConstraints59.ipadx = 9;
			gridBagConstraints59.ipady = -11;
			gridBagConstraints59.gridx = 2;
			GridBagConstraints gridBagConstraints58 = new GridBagConstraints();
			gridBagConstraints58.fill = GridBagConstraints.BOTH;
			gridBagConstraints58.gridwidth = 4;
			gridBagConstraints58.gridx = 1;
			gridBagConstraints58.gridy = 1;
			gridBagConstraints58.ipadx = 251;
			gridBagConstraints58.ipady = -33;
			gridBagConstraints58.weightx = 1.0;
			gridBagConstraints58.weighty = 1.0;
			gridBagConstraints58.insets = new Insets(1, 1, 5, 0);
			pnBom = new JPanel();
			GridBagLayout gbl_pnBom = new GridBagLayout();
			gbl_pnBom.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0 };
			gbl_pnBom.rowHeights = new int[] { 0, 0, 12, 11, 0 };
			pnBom.setLayout(gbl_pnBom);
			pnBom.add(getSpn2(), gridBagConstraints58);
			pnBom.add(getRbTotal(), gridBagConstraints59);
			GridBagConstraints gridBagConstraints63 = new GridBagConstraints();
			gridBagConstraints63.anchor = GridBagConstraints.WEST;
			gridBagConstraints63.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints63.insets = new Insets(0, 13, 5, 0);
			gridBagConstraints63.gridx = 4;
			gridBagConstraints63.gridy = 2;
			gridBagConstraints63.ipadx = 2;
			gridBagConstraints63.ipady = -13;
			gridBagConstraints63.gridwidth = -1;
			pnBom.add(getRbWaste(), gridBagConstraints60);
			pnBom.add(getBtnRoundAmount(), gridBagConstraints61);
			pnBom.add(getCbBom1(), gridBagConstraints62);
			pnBom.add(getCbBom2(), gridBagConstraints63);
			pnBom.add(getCbBom3(), gridBagConstraints64);
			pnBom.add(getBtnEditUnitWaste(), gridBagConstraints65);
			pnBom.add(getBtnChangeImgModifyMark(), gridBagConstraints);
			GridBagConstraints gbc_cbBom4 = new GridBagConstraints();
			gbc_cbBom4.insets = new Insets(0, 13, 5, 0);
			gbc_cbBom4.anchor = GridBagConstraints.WEST;
			gbc_cbBom4.fill = GridBagConstraints.VERTICAL;
			gbc_cbBom4.gridx = 4;
			gbc_cbBom4.gridy = 4;
			pnBom.add(getCbBom4(), gbc_cbBom4);
		}
		return pnBom;
	}

	/**
	 * This method initializes pnOther
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnOther() {
		if (pnOther == null) {
			pnOther = new JPanel();
			pnOther.setLayout(null);
			pnOther.add(getPn6(), null);
			pnOther.add(getJButton5(), null);
			pnOther.add(getBtnPrintCount(), null);
			pnOther.add(getPn7(), null);
			pnOther.add(getJPanel(), null);
			pnOther.add(getJPanel1(), null);
		}
		return pnOther;
	}

	/**
	 * This method initializes tb
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getTb() {
		if (tb == null) {
			FlowLayout f = new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(1);
			f.setHgap(1);
			tb = new JToolBar();
			tb.setFloatable(false);
			tb.setLayout(f);
			tb.add(getRbSelf());
			tb.add(getRbCustoms());
			tb.add(getBtnDeclare());
			tb.add(getBtnProccess());
			tb.add(getBtnChangeDeclareState());
			tb.add(getCb5());
			tb.add(getBtnPrint());
			tb.add(getBtnClose());
		}
		return tb;
	}

	/**
	 * This method initializes rbSelf
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbSelf() {
		if (rbSelf == null) {
			rbSelf = new JRadioButton();
			rbSelf.setSelected(true);
			rbSelf.setPreferredSize(new Dimension(90, 30));
			rbSelf.setText("自用核销表");
			rbSelf.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						switchSelfCustoms(rbCustoms.isSelected());
						switch (tpn.getSelectedIndex()) {
						case 0:
							showData(contractCavSelf);
							break;
						case 1:
							showCustomsDeclaration(rbCustoms.isSelected());
							break;
						case 2:
							showExgData(rbCustoms.isSelected());
							break;
						case 3:
							showImgData(rbCustoms.isSelected());
							break;
						case 4:
							showBomData(rbCustoms.isSelected());
							break;
						}
						setState();
					}
				}
			});
		}
		return rbSelf;
	}

	/**
	 * This method initializes rbCustoms
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbCustoms() {
		if (rbCustoms == null) {
			rbCustoms = new JRadioButton();
			rbCustoms.setText("海关核销表");
			// rbCustoms.setSelected(true);
			rbCustoms.setPreferredSize(new Dimension(90, 30));
			rbCustoms.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						switchSelfCustoms(rbCustoms.isSelected());
						switch (tpn.getSelectedIndex()) {
						case 0:
							showData(contractCavCustoms);
							System.out.println(contractCavCustoms
									.getImpCDCount() + " ---IMPMM");
							System.out.println(contractCavCustoms
									.getExpCDCount() + " ---EXPMM");
							break;
						case 1:
							showCustomsDeclaration(rbCustoms.isSelected());
							break;
						case 2:
							showExgData(rbCustoms.isSelected());
							break;
						case 3:
							showImgData(rbCustoms.isSelected());
							break;
						case 4:
							showBomData(rbCustoms.isSelected());
							break;
						}
						setState();
					}
				}
			});
		}
		return rbCustoms;
	}

	/**
	 * This method initializes bgSelectModel
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getBgSelectModel() {
		if (bgSelectModel == null) {
			bgSelectModel = new ButtonGroup();
			bgSelectModel.add(this.getRbSelf());
			bgSelectModel.add(this.getRbCustoms());
		}
		return bgSelectModel;
	}

	/**
	 * This method initializes btnClose
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("关闭");
			btnClose.setPreferredSize(new Dimension(60, 30));
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnClose;
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
			btnPrint.setPreferredSize(new Dimension(60, 30));
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getJPopupMenuPrintReports().show(btnPrint, 0,
							btnPrint.getHeight());
				}
			});
		}
		return btnPrint;
	}

	/**
	 * 外商投资企业加工贸易业务核销表（番禺）
	 */
	private void printPyForeignInvestment() {
		ContractCav contractCav = null;
		if (this.rbCustoms.isSelected()) {
			contractCav = this.contractCavCustoms;
		} else if (this.rbSelf.isSelected()) {
			contractCav = this.contractCavSelf;
		}
		if (contractCav == null) {
			return;
		}
		String emsNo = contractCav.getEmsNo();
		List list = this.contractCavAction.findCavContractByEmsNo(new Request(
				CommonVars.getCurrUser()), emsNo);
		Map<String, Object> parameters = new HashMap<String, Object>();
		Company company = (Company) contractCav.getCompany();
		Contract contract = null;
		if (!list.isEmpty()) {
			contract = (Contract) list.get(0);
		}
		List ls = new ArrayList();
		ls.add("");
		CustomReportDataSource ds = new CustomReportDataSource(ls);
		InputStream reportStream = null;
		reportStream = DgContractCav.class // ContractCavHead.jasper
				.getResourceAsStream("report/PYContractCav.jasper");
		parameters = new HashMap<String, Object>();
		parameters.put("companyCode", company.getBuCode());
		parameters.put("companyName", company.getBuName());
		parameters.put("SJexpTotalMoney",
				contractCav.getExpAmount() == null ? "" : contractCav
						.getExpAmount().toString());
		parameters.put("SJimpTotalMoney",
				contractCav.getImpAmount() == null ? "" : contractCav
						.getImpAmount().toString());
		if (contract != null) {
			parameters.put("expTotalMoney",
					contract.getExgAmount() == null ? "" : contract
							.getExgAmount().toString());
			parameters.put("impTotalMoney",
					contract.getImgAmount() == null ? "" : contract
							.getImgAmount().toString());
			parameters.put("cusCode", contract.getDeclareCustoms() == null ? ""
					: contract.getDeclareCustoms().getCode());
			parameters.put("cusName", contract.getDeclareCustoms() == null ? ""
					: contract.getDeclareCustoms().getName());
		}
		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					reportStream, parameters, ds);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (JRException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 打印加工贸易业务核销清单表(进口料件部分)（番禺）
	 */
	private void printPyProcessingTradeVerificationBillImportImg() {
		ContractCav contractCav = null;
		if (this.rbCustoms.isSelected()) {
			contractCav = this.contractCavCustoms;
		} else if (this.rbSelf.isSelected()) {
			contractCav = this.contractCavSelf;
		}
		if (contractCav == null) {
			return;
		}
		String emsNo = contractCav.getEmsNo();
		List list = this.contractCavAction.findCavContractByEmsNo(new Request(
				CommonVars.getCurrUser()), emsNo);
		Map<String, Object> parameters = new HashMap<String, Object>();
		Contract contract = null;
		if (!list.isEmpty()) {
			contract = (Contract) list.get(0);
		}
		List ls = new ArrayList();
		ls.add("");
		CustomReportDataSource ds = new CustomReportDataSource(ls);
		InputStream reportStream = null;
		list = contractCavAction.findCustomsDeclaretionDetailByContract(
				new Request(CommonVars.getCurrUser()), contractCav.getEmsNo(),
				true);
		if (list == null || list.size() == 0) {
			list = new ArrayList();
			list.add("");
		}
		ds = new CustomReportDataSource(list);
		reportStream = DgContractCav.class
				.getResourceAsStream("report/PYMaterielList.jasper");
		parameters = new HashMap<String, Object>();
		parameters.put("isMateriel", true);
		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					reportStream, parameters, ds);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (JRException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 打印加工贸易业务核销清单表(出口制成品部分)（番禺）
	 */
	private void printPyProcessingTradeVerificationBillExprotExg() {
		ContractCav contractCav = null;
		if (this.rbCustoms.isSelected()) {
			contractCav = this.contractCavCustoms;
		} else if (this.rbSelf.isSelected()) {
			contractCav = this.contractCavSelf;
		}
		if (contractCav == null) {
			return;
		}
		String emsNo = contractCav.getEmsNo();
		List list = this.contractCavAction.findCavContractByEmsNo(new Request(
				CommonVars.getCurrUser()), emsNo);
		Map<String, Object> parameters = new HashMap<String, Object>();
		List ls = new ArrayList();
		ls.add("");
		CustomReportDataSource ds = new CustomReportDataSource(ls);
		InputStream reportStream = null;
		list = contractCavAction.findCustomsDeclaretionDetailByContract(
				new Request(CommonVars.getCurrUser()), contractCav.getEmsNo(),
				false);
		if (list == null || list.size() == 0) {
			list = new ArrayList();
			list.add("");
		}
		ds = new CustomReportDataSource(list);
		reportStream = DgContractCav.class
				.getResourceAsStream("report/PYMaterielList.jasper");
		parameters = new HashMap<String, Object>();
		parameters.put("isMateriel", false);
		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					reportStream, parameters, ds);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (JRException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 打印套打核销料件表（番禺）
	 */
	private void printPyShopList() {
		ContractCav contractCav = null;
		if (this.rbCustoms.isSelected()) {
			contractCav = this.contractCavCustoms;
		} else if (this.rbSelf.isSelected()) {
			contractCav = this.contractCavSelf;
		}
		if (contractCav == null) {
			return;
		}
		String emsNo = contractCav.getEmsNo();
		List list = this.contractCavAction.findCavContractByEmsNo(new Request(
				CommonVars.getCurrUser()), emsNo);
		Map<String, Object> parameters = new HashMap<String, Object>();
		Company company = (Company) contractCav.getCompany();
		Contract contract = null;
		if (!list.isEmpty()) {
			contract = (Contract) list.get(0);
		}
		List ls = new ArrayList();
		ls.add("");
		CustomReportDataSource ds = new CustomReportDataSource(ls);
		InputStream reportStream = null;
		parameters = new HashMap<String, Object>();
		parameters.put("name", company.getName());
		parameters.put("companyName", company.getBuName());
		parameters.put("contractNo", contractCav.getContractNo());
		if (contract != null) {
			parameters.put("expTotalMoney",
					contract.getExgAmount() == null ? "" : contract
							.getExgAmount().toString());
		}
		list = contractCavAction.findContractImgCavByBuiner(new Request(
				CommonVars.getCurrUser()), contractCav.getId());
		ds = new CustomReportDataSource(list);
		reportStream = DgContractCav.class
				.getResourceAsStream("report/PYBuyInner.jasper");
		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					reportStream, parameters, ds);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (JRException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 0打印非套打核销表
	 */
	private void printNonCoverPrintApplied() {
		ContractCav contractCav = null;
		if (this.rbCustoms.isSelected()) {
			contractCav = this.contractCavCustoms;
		} else if (this.rbSelf.isSelected()) {
			contractCav = this.contractCavSelf;
		}
		if (contractCav == null) {
			return;
		}
		InputStream reportStream = null;
		List<Object> list = new ArrayList<Object>();
		Map<String, Object> parameters = new HashMap<String, Object>();
		CustomReportDataSource ds = new CustomReportDataSource(list);
		Company company = (Company) contractCav.getCompany();
		list.add(contractCav);
		ds = new CustomReportDataSource(list);
		reportStream = DgContractCav.class
				.getResourceAsStream("report/ContractCavHead.jasper");
		parameters = new HashMap<String, Object>();
		parameters.put("companyCode", company.getCode());
		parameters.put("companyName", company.getName());
		parameters.put("companyTel", company.getTel());
		parameters.put("overprint", new Boolean(false));
		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					reportStream, parameters, ds);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (JRException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 1打印套打核销表
	 */
	private void printCoverPrintApplied() {
		ContractCav contractCav = null;
		if (this.rbCustoms.isSelected()) {
			contractCav = this.contractCavCustoms;
		} else if (this.rbSelf.isSelected()) {
			contractCav = this.contractCavSelf;
		}
		if (contractCav == null) {
			return;
		}
		InputStream reportStream = null;
		List<Object> list = new ArrayList<Object>();
		Map<String, Object> parameters = new HashMap<String, Object>();
		CustomReportDataSource ds = new CustomReportDataSource(list);
		Company company = (Company) contractCav.getCompany();
		list.add(contractCav);
		ds = new CustomReportDataSource(list);
		reportStream = DgContractCav.class
				.getResourceAsStream("report/ContractCavHead.jasper");
		parameters.put("companyCode", company.getBuCode());
		parameters.put("companyName", company.getBuName());
		parameters.put("companyTel", company.getTel());
		parameters.put("overprint", new Boolean(true));
		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					reportStream, parameters, ds);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (JRException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 2打印非套打核销料件表
	 */
	private void printNonCoverPrintAppliedImg() {
		ContractCav contractCav = null;
		if (this.rbCustoms.isSelected()) {
			contractCav = this.contractCavCustoms;
		} else if (this.rbSelf.isSelected()) {
			contractCav = this.contractCavSelf;
		}
		if (contractCav == null) {
			return;
		}

		Boolean isPrintZeroAsNull = false;
		Object[] message = new Object[1];
		message[0] = getCbIsPrintZeroAsNull();
		JOptionPane.showOptionDialog(DgContractCav.this, message,
				"请选择！",
				JOptionPane.CLOSED_OPTION, // option type
				JOptionPane.INFORMATION_MESSAGE, null, new Object[] { "确定" },
				"确定");
		if (getCbIsPrintZeroAsNull().isSelected()) {
			isPrintZeroAsNull = true;
		}

		InputStream reportStream = null;
		List<Object> list = new ArrayList<Object>();
		Map<String, Object> parameters = new HashMap<String, Object>();
		CustomReportDataSource ds = new CustomReportDataSource(list);
		list = contractCavAction.findContractImgCav(
				new Request(CommonVars.getCurrUser()), contractCav,
				this.cb1.isSelected());
		ds = new CustomReportDataSource(list);
		reportStream = DgContractCav.class
				.getResourceAsStream("report/ContractImgCav.jasper");

		parameters.put("contractNo", contractCav.getContractNo());
		parameters.put("emsNo", contractCav.getEmsNo());
		parameters.put("overprint", new Boolean(false));
		parameters.put("isPrintZeroAsNull", isPrintZeroAsNull);

		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					reportStream, parameters, ds);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (JRException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 3打印套打核销料件表
	 */
	private void printCoverPrintAppliedImg() {
		ContractCav contractCav = null;
		if (this.rbCustoms.isSelected()) {
			contractCav = this.contractCavCustoms;
		} else if (this.rbSelf.isSelected()) {
			contractCav = this.contractCavSelf;
		}
		if (contractCav == null) {
			return;
		}

		Boolean isPrintZeroAsNull = false;
		Object[] message = new Object[1];
		message[0] = getCbIsPrintZeroAsNull();
		JOptionPane.showOptionDialog(DgContractCav.this, message,
				"请选择！",
				JOptionPane.CLOSED_OPTION, // option type
				JOptionPane.INFORMATION_MESSAGE, null, new Object[] { "确定" },
				"确定");
		if (getCbIsPrintZeroAsNull().isSelected()) {
			isPrintZeroAsNull = true;
		}

		InputStream reportStream = null;
		List<Object> list = new ArrayList<Object>();
		Map<String, Object> parameters = new HashMap<String, Object>();
		CustomReportDataSource ds = new CustomReportDataSource(list);
		list = contractCavAction.findContractImgCav(
				new Request(CommonVars.getCurrUser()), contractCav,
				this.cb1.isSelected());
		ds = new CustomReportDataSource(list);
		reportStream = DgContractCav.class
				.getResourceAsStream("report/ContractImgCav.jasper");
		parameters.put("contractNo", contractCav.getContractNo());
		parameters.put("emsNo", contractCav.getEmsNo());
		parameters.put("overprint", new Boolean(true));
		parameters.put("isPrintZeroAsNull", isPrintZeroAsNull);
		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					reportStream, parameters, ds);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (JRException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 4打印非套打核销料成品件表
	 */
	private void printNonCoverPrintAppliedExg() {
		ContractCav contractCav = null;
		if (this.rbCustoms.isSelected()) {
			contractCav = this.contractCavCustoms;
		} else if (this.rbSelf.isSelected()) {
			contractCav = this.contractCavSelf;
		}
		if (contractCav == null) {
			return;
		}

		Boolean isPrintZeroAsNull = false;
		Object[] message = new Object[1];
		message[0] = getCbIsPrintZeroAsNull();
		JOptionPane.showOptionDialog(DgContractCav.this, message,
				"请选择！",
				JOptionPane.CLOSED_OPTION, // option type
				JOptionPane.INFORMATION_MESSAGE, null, new Object[] { "确定" },
				"确定");
		if (getCbIsPrintZeroAsNull().isSelected()) {
			isPrintZeroAsNull = true;
		}

		InputStream reportStream = null;
		List<Object> list = new ArrayList<Object>();
		Map<String, Object> parameters = new HashMap<String, Object>();
		CustomReportDataSource ds = new CustomReportDataSource(list);
		list = contractCavAction.findContractExgCav(
				new Request(CommonVars.getCurrUser()), contractCav,
				this.cb1.isSelected());
		ds = new CustomReportDataSource(list);
		reportStream = DgContractCav.class
				.getResourceAsStream("report/ContractExgCav.jasper");
		parameters.put("contractNo", contractCav.getContractNo());
		parameters.put("emsNo", contractCav.getEmsNo());
		parameters.put("overprint", new Boolean(false));
		parameters.put("isPrintZeroAsNull", isPrintZeroAsNull);
		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					reportStream, parameters, ds);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (JRException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 5打印非套打核销料成品件表
	 */
	private void printCoverPrintAppliedExg() {
		ContractCav contractCav = null;
		if (this.rbCustoms.isSelected()) {
			contractCav = this.contractCavCustoms;
		} else if (this.rbSelf.isSelected()) {
			contractCav = this.contractCavSelf;
		}
		if (contractCav == null) {
			return;
		}

		Boolean isPrintZeroAsNull = false;
		Object[] message = new Object[1];
		message[0] = getCbIsPrintZeroAsNull();
		JOptionPane.showOptionDialog(DgContractCav.this, message,
				"请选择！",
				JOptionPane.CLOSED_OPTION, // option type
				JOptionPane.INFORMATION_MESSAGE, null, new Object[] { "确定" },
				"确定");
		if (getCbIsPrintZeroAsNull().isSelected()) {
			isPrintZeroAsNull = true;
		}

		InputStream reportStream = null;
		List<Object> list = new ArrayList<Object>();
		Map<String, Object> parameters = new HashMap<String, Object>();
		CustomReportDataSource ds = new CustomReportDataSource(list);
		list = contractCavAction.findContractExgCav(
				new Request(CommonVars.getCurrUser()), contractCav,
				this.cb1.isSelected());
		ds = new CustomReportDataSource(list);
		reportStream = DgContractCav.class
				.getResourceAsStream("report/ContractExgCav.jasper");
		parameters.put("contractNo", contractCav.getContractNo());
		parameters.put("emsNo", contractCav.getEmsNo());
		parameters.put("overprint", new Boolean(true));
		parameters.put("isPrintZeroAsNull", isPrintZeroAsNull);
		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					reportStream, parameters, ds);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (JRException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 6打印非套打核销料单耗件表
	 */
	private void printNonCoverPrintAppliedUnitConsumption() {
		ContractCav contractCav = null;
		if (this.rbCustoms.isSelected()) {
			contractCav = this.contractCavCustoms;
		} else if (this.rbSelf.isSelected()) {
			contractCav = this.contractCavSelf;
		}
		if (contractCav == null) {
			return;
		}
		Map<String, Object> parameters = new HashMap<String, Object>();
		try {
			Boolean isPrintZero = true;
			// if (JOptionPane.showConfirmDialog(DgContractCav.this,
			// "当损耗数等为0时，是否打印？", "提示", JOptionPane.YES_NO_OPTION) !=
			// JOptionPane.YES_OPTION) {
			// isPrintZero = false;
			// }
			Object[] message = new Object[2];
			message[0] = getCb3();
			message[1] = getCb4();
			JOptionPane.showOptionDialog(DgContractCav.this, message,
					"请选择！",
					JOptionPane.CLOSED_OPTION, // option type
					JOptionPane.INFORMATION_MESSAGE, null,
					new Object[] { "确定" }, "确定");
			if (!getCb3().isSelected()) {
				isPrintZero = false;
			}
			boolean isShowZero = false;
			if (!getCb4().isSelected()) {
				isShowZero = true;
			}
			boolean isNoPrintProductZero;
			if (getCb2().isSelected()) {
				isNoPrintProductZero = true;
			} else {
				isNoPrintProductZero = false;
			}
			if (getCb2().isSelected()) {
				isNoPrintProductZero = true;
			} else {
				isNoPrintProductZero = false;
			}
			// System.out.println(isPrintZero);
			// System.out.println(isShowZero);
			List<List> resultList = new ArrayList<List>();
			List<ContractUnitWasteCav> unitWasteList = new ArrayList<ContractUnitWasteCav>();
			List<ContractExgCav> exgCavList = new ArrayList<ContractExgCav>();
			int count = 0;
			if (contractCav != null) {
				String parentId = contractCav.getId();
				resultList = contractCavAction.findContractUnitWasteCav(
						new Request(CommonVars.getCurrUser()), parentId, -1,
						-1, isShowZero, isNoPrintProductZero);
				exgCavList = resultList.get(0);
				unitWasteList = resultList.get(1);
				count = exgCavList.size();
			}
			ContractcavReportVars.setContractExgCavList(exgCavList);
			CustomReportDataSource ds1 = new CustomReportDataSource(
					unitWasteList);
			InputStream masterReportStream = DgContractCav.class
					.getResourceAsStream("report/ContractUnitWasteCav.jasper");
			parameters.put("isOverprint", new Boolean(false));
			parameters.put("count", count);
			parameters.put("isPrintZero", isPrintZero);
			parameters.put("contractNo", contractCav.getContractNo());
			parameters.put("emsNo", contractCav.getEmsNo());
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					masterReportStream, parameters, ds1);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 7打印套打核销料单耗件表
	 */
	private void printCoverPrintAppliedUnitConsumption() {
		ContractCav contractCav = null;
		if (this.rbCustoms.isSelected()) {
			contractCav = this.contractCavCustoms;
		} else if (this.rbSelf.isSelected()) {
			contractCav = this.contractCavSelf;
		}
		if (contractCav == null) {
			return;
		}
		Map<String, Object> parameters = new HashMap<String, Object>();
		try {
			Boolean isPrintZero = true;
			// if (JOptionPane.showConfirmDialog(DgContractCav.this,
			// "当损耗数等为0时，是否打印？", "提示", JOptionPane.YES_NO_OPTION) !=
			// JOptionPane.YES_OPTION) {
			// isPrintZero = false;
			// }
			Object[] message = new Object[2];
			message[0] = getCb3();
			message[1] = getCb4();
			JOptionPane.showOptionDialog(DgContractCav.this, message,
					"请选择！",
					JOptionPane.CLOSED_OPTION, // option type
					JOptionPane.INFORMATION_MESSAGE, null,
					new Object[] { "确定" }, "确定");
			if (!getCb3().isSelected()) {
				isPrintZero = false;
			}
			boolean isShowZero = false;
			if (!getCb4().isSelected()) {
				isShowZero = true;
			}
			boolean isNoPrintProductZero;
			if (getCb2().isSelected()) {
				isNoPrintProductZero = true;
			} else {
				isNoPrintProductZero = false;
			}
			List<List> resultList = new ArrayList<List>();
			List<ContractUnitWasteCav> unitWasteList = new ArrayList<ContractUnitWasteCav>();
			List<ContractExgCav> exgCavList = new ArrayList<ContractExgCav>();
			int count = 0;
			if (contractCav != null) {
				String parentId = contractCav.getId();
				resultList = contractCavAction.findContractUnitWasteCav(
						new Request(CommonVars.getCurrUser()), parentId, -1,
						-1, isShowZero, isNoPrintProductZero);
				exgCavList = resultList.get(0);
				unitWasteList = resultList.get(1);
				count = exgCavList.size();
			}
			ContractcavReportVars.setContractExgCavList(exgCavList);
			CustomReportDataSource ds1 = new CustomReportDataSource(
					unitWasteList);
			InputStream masterReportStream = DgContractCav.class
					.getResourceAsStream("report/ContractUnitWasteCav.jasper");
			parameters.put("isOverprint", new Boolean(true));
			parameters.put("count", count);
			parameters.put("isPrintZero", isPrintZero);
			parameters.put("contractNo", contractCav.getContractNo());
			parameters.put("emsNo", contractCav.getEmsNo());
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					masterReportStream, parameters, ds1);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 8打印非套打核销料件表（新）
	 */
	private void printNonCoverPrintAppliedNew(Boolean small) {
		ContractCav contractCav = null;
		if (this.rbCustoms.isSelected()) {
			contractCav = this.contractCavCustoms;
		} else if (this.rbSelf.isSelected()) {
			contractCav = this.contractCavSelf;
		}
		if (contractCav == null) {
			return;
		}
		InputStream reportStream = null;
		List<Object> list = new ArrayList<Object>();
		Map<String, Object> parameters = new HashMap<String, Object>();
		CustomReportDataSource ds = null;
		Company company = (Company) contractCav.getCompany();
		list.add(contractCav);
		ds = new CustomReportDataSource(list);
		if (small) {
			reportStream = DgContractCav.class
					.getResourceAsStream("report/ContractCavHeadNew.jasper");
		} else {
			reportStream = DgContractCav.class
					.getResourceAsStream("report/ContractCavHeadNewBig.jasper");
		}

		parameters = new HashMap<String, Object>();
		parameters.put("companyCode", company.getCode());
		parameters.put("companyName", company.getName());
		parameters.put("companyTel", company.getTel());
		parameters.put("companyCoLevel", company.getCoLevel());
		parameters.put("overprint", new Boolean(false));

		// 料件总金额和
		parameters.put("sumImgTotalPrice", sumContractImgTotalPrice.toString());

		// 成品总金额和
		parameters.put("sumExgTotalPrice", sumContractExgTotalPrice.toString());

		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					reportStream, parameters, ds);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (JRException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 9打印非套打核销料单耗件表（新）
	 */
	private void printNonCoverPrintAppliedUnitConsumptionNew() {
		ContractCav contractCav = null;
		if (this.rbCustoms.isSelected()) {
			contractCav = this.contractCavCustoms;
		} else if (this.rbSelf.isSelected()) {
			contractCav = this.contractCavSelf;
		}
		if (contractCav == null) {
			return;
		}
		Map<String, Object> parameters = new HashMap<String, Object>();
		try {
			Boolean isPrintZero = true;
			// if (JOptionPane.showConfirmDialog(DgContractCav.this,
			// "当损耗数等为0时，是否打印？", "提示", JOptionPane.YES_NO_OPTION) !=
			// JOptionPane.YES_OPTION) {
			// isPrintZero = false;
			// }
			Object[] message = new Object[5];
			getBtnGroup();
			message[0] = getCb3();
			message[1] = getCb4();
			message[2] = getCb6();
			message[3] = getCb7();
			message[4] = getCb8();
			int ms = JOptionPane.showOptionDialog(DgContractCav.this, message,
					"请选择！",
					JOptionPane.CLOSED_OPTION, // option type
					JOptionPane.INFORMATION_MESSAGE, null,
					new Object[] { "确定" }, "确定");
			if (ms >= 0) {
				if (!getCb3().isSelected()) {
					isPrintZero = false;
				}
				boolean isShowZero = false;
				if (!getCb4().isSelected()) {
					isShowZero = true;
				}
				boolean isNoPrintProductZero;
				if (getCb2().isSelected()) {
					isNoPrintProductZero = true;
				} else {
					isNoPrintProductZero = false;
				}
				List<List> resultList = new ArrayList<List>();
				List<ContractUnitWasteCav> unitWasteList = new ArrayList<ContractUnitWasteCav>();
				List<ContractUnitWasteCav> unitWasteListNew = new ArrayList<ContractUnitWasteCav>();
				List<ContractExgCav> exgCavList = new ArrayList<ContractExgCav>();
				int count = 0;
				if (contractCav != null) {
					String parentId = contractCav.getId();
					resultList = contractCavAction.findContractUnitWasteCav(
							new Request(CommonVars.getCurrUser()), parentId,
							-1, -1, isPrintZero, isNoPrintProductZero);
					exgCavList = resultList.get(0);
					unitWasteList = resultList.get(1);
					count = exgCavList.size();
				}
				ContractcavReportVars.setContractExgCavList(exgCavList);
				if (getCb6().isSelected()) {
					ContractUnitWasteCav cuwc = null;
					for (int i = 0; i < unitWasteList.size(); i++) {
						cuwc = unitWasteList.get(i);
						if (cuwc != null) {
							cuwc.setIsPrintUnitNet1(false);// 打印净耗
							cuwc.setIsPrintUnitNet2(false);// 打印净耗
							cuwc.setIsPrintUnitNet3(false);// 打印净耗
							cuwc.setIsPrintUnitNet4(false);// 打印净耗
						}
						unitWasteListNew.add(cuwc);
					}
				}
				if (getCb7().isSelected()) {
					ContractUnitWasteCav cuwc = null;
					for (int i = 0; i < unitWasteList.size(); i++) {
						cuwc = unitWasteList.get(i);
						if (cuwc != null) {
							cuwc.setIsPrintUnitNet1(true);// 打印单耗
							cuwc.setIsPrintUnitNet2(true);// 打印单耗
							cuwc.setIsPrintUnitNet3(true);// 打印单耗
							cuwc.setIsPrintUnitNet4(true);// 打印单耗
						}
						unitWasteListNew.add(cuwc);
					}
				}
				if (getCb8().isSelected()) {
					ContractUnitWasteCav cuwc = null;
					for (int i = 0; i < unitWasteList.size(); i++) {
						cuwc = unitWasteList.get(i);
						if (cuwc != null) {
							if (cuwc.getWasteAmount1() == null
									|| cuwc.getWasteAmount1() == 0) {
								cuwc.setIsPrintUnitNet1(true);// 打印单耗
							} else {
								cuwc.setIsPrintUnitNet1(false);// 打印净耗
							}
							if (cuwc.getWasteAmount2() == null
									|| cuwc.getWasteAmount2() == 0) {
								cuwc.setIsPrintUnitNet2(true);// 打印单耗
							} else {
								cuwc.setIsPrintUnitNet2(false);// 打印净耗
							}
							if (cuwc.getWasteAmount3() == null
									|| cuwc.getWasteAmount3() == 0) {
								cuwc.setIsPrintUnitNet3(true);// 打印单耗
							} else {
								cuwc.setIsPrintUnitNet3(false);// 打印净耗
							}
							if (cuwc.getWasteAmount4() == null
									|| cuwc.getWasteAmount4() == 0) {
								cuwc.setIsPrintUnitNet4(true);// 打印单耗
							} else {
								cuwc.setIsPrintUnitNet4(false);// 打印净耗
							}
						}
						unitWasteListNew.add(cuwc);
					}
				}
				CustomReportDataSource ds1 = new CustomReportDataSource(
						unitWasteListNew);
				InputStream masterReportStream = DgContractCav.class
						.getResourceAsStream("report/ContractUnitWasteCavNew.jasper");
				parameters.put("isOverprint", new Boolean(false));
				parameters.put("count", count);
				parameters.put("isPrintZero", isPrintZero);
				parameters.put("contractNo", contractCav.getContractNo());
				parameters.put("emsNo", contractCav.getEmsNo());
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters, ds1);
				DgReportViewer viewer = new DgReportViewer(jasperPrint);
				viewer.setVisible(true);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 10打印非套打对外加工装配、补偿贸易合同核销申请（结案）表
	 */
	private void printNonCoverPrintAppliedContractConsumptionCased() {
		ContractCav contractCav = null;
		if (this.rbCustoms.isSelected()) {
			contractCav = this.contractCavCustoms;
		} else if (this.rbSelf.isSelected()) {
			contractCav = this.contractCavSelf;
		}
		if (contractCav == null) {
			return;
		}
		Map<String, Object> parameters = new HashMap<String, Object>();
		try {
			List<List> resultList = new ArrayList<List>();
			List<ContractUnitWasteCav> unitWasteList = new ArrayList<ContractUnitWasteCav>();
			List<ContractExgCav> exgCavList = new ArrayList<ContractExgCav>();
			int count = 0;
			String emsNo = "";
			String contractNo = "";
			String impContractNo = "";
			String expContractNo = "";
			double processTotalPrice = 0.0;
			double impAmount = 0.0;
			double expAmount = 0.0;
			boolean isSZQY = false;
			if (contractCav != null) {
				String parentId = contractCav.getId();
				emsNo = contractCav.getEmsNo() == null ? "" : contract
						.getEmsNo();
				resultList = contractCavAction.findContractUnitWasteCav2(
						new Request(CommonVars.getCurrUser()), parentId, emsNo,
						-1, -1);
				impContractNo = contract.getImpContractNo() == null ? ""
						: contract.getImpContractNo();
				expContractNo = contract.getExpContractNo() == null ? ""
						: contract.getExpContractNo();
				contractNo = contractCav.getContractNo() == null ? ""
						: contractCav.getContractNo();
				processTotalPrice = contractCav.getProcessTotalPrice() == null ? 0.0
						: contractCav.getProcessTotalPrice();
				impAmount = contractCav.getImpAmount() == null ? 0.0
						: contractCav.getImpAmount();
				expAmount = contractCav.getExpAmount() == null ? 0.0
						: contractCav.getExpAmount();
				Company company = (Company) contractCav.getCompany();
				if (company.getBuName() == company.getName()) {
					isSZQY = true;
				} else {
					isSZQY = false;
				}
				exgCavList = resultList.get(0);
				unitWasteList = resultList.get(1);
				count = exgCavList.size();
			}
			ContractcavReportVars2.setContractExgCavList(exgCavList);
			ContractcavReportVars3.setContractExgCavList(exgCavList);
			parameters.put("emsNo", emsNo);
			parameters.put("contractNo", contractNo);
			parameters.put("impContractNo", impContractNo);
			parameters.put("expContractNo", expContractNo);
			parameters.put("processTotalPrice", processTotalPrice);
			parameters.put("impAmount",
					CommonUtils.formatDoubleByDigit(impAmount, 4));
			parameters.put("expAmount",
					CommonUtils.formatDoubleByDigit(expAmount, 4));
			parameters.put("isOverprint", new Boolean(false));
			parameters.put("count", count);
			parameters.put("isSZQY", isSZQY);
			CustomReportDataSource ds1 = new CustomReportDataSource(
					unitWasteList);
			ds1.setMaximumFractionDigits(9);
			InputStream masterReportStream = DgContractCav.class
					.getResourceAsStream("report/ContractVerificationAppliedReport.jasper");
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					masterReportStream, parameters, ds1);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/*
	 * private void printReport() { ContractCav contractCav = null;
	 * if(this.rbCustoms.isSelected()) { contractCav = this.contractCavCustoms;
	 * } else if(this.rbSelf.isSelected()) { contractCav = this.contractCavSelf;
	 * } if(contractCav == null) { return; } InputStream reportStream = null;
	 * List<Object> list = new ArrayList<Object>(); Map<String, Object>
	 * parameters = new HashMap<String, Object>(); CustomReportDataSource ds =
	 * new CustomReportDataSource(list); Company company = (Company)
	 * contractCav.getCompany(); switch (cbbPrint.getSelectedIndex()){ case 0://
	 * 非套打核销单头 // list=contractCavAction.findContractImgCav(new Request( //
	 * CommonVars.getCurrUser()), contractCavCustoms); list.add(contractCav); ds
	 * = new CustomReportDataSource(list); reportStream = DgContractCav.class
	 * .getResourceAsStream("report/ContractCavHead.jasper"); parameters = new
	 * HashMap<String, Object>(); parameters.put("companyCode",
	 * company.getCode()); parameters.put("companyName", company.getName());
	 * parameters.put("companyTel", company.getTel());
	 * parameters.put("overprint", new Boolean(false)); try { JasperPrint
	 * jasperPrint = JasperFillManager.fillReport( reportStream, parameters,
	 * ds); DgReportViewer viewer = new DgReportViewer(jasperPrint);
	 * viewer.setVisible(true); } catch (JRException e1) { e1.printStackTrace();
	 * } break; case 1:// 套打核销单头 //
	 * list=contractCavAction.findContractImgCav(new Request( //
	 * CommonVars.getCurrUser()), contractCavCustoms); list.add(contractCav); ds
	 * = new CustomReportDataSource(list); reportStream = DgContractCav.class
	 * .getResourceAsStream("report/ContractCavHead.jasper");
	 * parameters.put("companyCode", company.getBuCode());
	 * parameters.put("companyName", company.getBuName());
	 * parameters.put("companyTel", company.getTel());
	 * parameters.put("overprint", new Boolean(true)); try { JasperPrint
	 * jasperPrint = JasperFillManager.fillReport( reportStream, parameters,
	 * ds); DgReportViewer viewer = new DgReportViewer(jasperPrint);
	 * viewer.setVisible(true); } catch (JRException e1) { e1.printStackTrace();
	 * } break; case 2:// 非套打核销料件表 list =
	 * contractCavAction.findContractImgCav(new Request(CommonVars
	 * .getCurrUser()), contractCav, this.jCheckBox1.isSelected()); ds = new
	 * CustomReportDataSource(list); reportStream = DgContractCav.class
	 * .getResourceAsStream("report/ContractImgCav.jasper");
	 * parameters.put("overprint", new Boolean(false)); try { JasperPrint
	 * jasperPrint = JasperFillManager.fillReport( reportStream, parameters,
	 * ds); DgReportViewer viewer = new DgReportViewer(jasperPrint);
	 * viewer.setVisible(true); } catch (JRException e1) { e1.printStackTrace();
	 * } break; case 3:// 套打核销料件表 list =
	 * contractCavAction.findContractImgCav(new Request(CommonVars
	 * .getCurrUser()), contractCav, this.jCheckBox1.isSelected()); ds = new
	 * CustomReportDataSource(list); reportStream = DgContractCav.class
	 * .getResourceAsStream("report/ContractImgCav.jasper");
	 * parameters.put("overprint", new Boolean(true)); try { JasperPrint
	 * jasperPrint = JasperFillManager.fillReport( reportStream, parameters,
	 * ds); DgReportViewer viewer = new DgReportViewer(jasperPrint);
	 * viewer.setVisible(true); } catch (JRException e1) { e1.printStackTrace();
	 * } break; case 4:// 非套打核销成品表 list =
	 * contractCavAction.findContractExgCav(new Request(CommonVars
	 * .getCurrUser()), contractCav, this.jCheckBox1.isSelected()); ds = new
	 * CustomReportDataSource(list); reportStream = DgContractCav.class
	 * .getResourceAsStream("report/ContractExgCav.jasper");
	 * parameters.put("overprint", new Boolean(false)); try { JasperPrint
	 * jasperPrint = JasperFillManager.fillReport( reportStream, parameters,
	 * ds); DgReportViewer viewer = new DgReportViewer(jasperPrint);
	 * viewer.setVisible(true); } catch (JRException e1) { e1.printStackTrace();
	 * } break; case 5:// 套打核销成品表
	 * 
	 * list = contractCavAction.findContractExgCav(new Request(CommonVars
	 * .getCurrUser()), contractCav, this.jCheckBox1.isSelected()); ds = new
	 * CustomReportDataSource(list); reportStream = DgContractCav.class
	 * .getResourceAsStream("report/ContractExgCav.jasper");
	 * parameters.put("overprint", new Boolean(true)); try { JasperPrint
	 * jasperPrint = JasperFillManager.fillReport( reportStream, parameters,
	 * ds); DgReportViewer viewer = new DgReportViewer(jasperPrint);
	 * viewer.setVisible(true); } catch (JRException e1) { e1.printStackTrace();
	 * } break; case 6:// 非套打核销单耗表 try { Boolean isPrintZero = true; // if
	 * (JOptionPane.showConfirmDialog(DgContractCav.this, // "当损耗数等为0时，是否打印？",
	 * "提示", JOptionPane.YES_NO_OPTION) != // JOptionPane.YES_OPTION) { //
	 * isPrintZero = false; // } Object[] message = new Object[2];
	 * 
	 * message[0] = getJCheckBox3(); message[1] = getJCheckBox4(); int ms =
	 * JOptionPane.showOptionDialog(DgContractCav.this, message, "请选择！",
	 * JOptionPane.CLOSED_OPTION, // option type
	 * JOptionPane.INFORMATION_MESSAGE, null, new Object[] { "确定" }, "确定"); if
	 * (!getJCheckBox3().isSelected()) { isPrintZero = false; } boolean
	 * isShowZero = false; if (!getJCheckBox4().isSelected()) { isShowZero =
	 * true; } System.out.println(isPrintZero); System.out.println(isShowZero);
	 * List<List> resultList = new ArrayList<List>(); List<ContractUnitWasteCav>
	 * unitWasteList = new ArrayList<ContractUnitWasteCav>();
	 * List<ContractExgCav> exgCavList = new ArrayList<ContractExgCav>(); int
	 * count = 0; if (contractCav != null) { String parentId =
	 * contractCav.getId(); resultList =
	 * contractCavAction.findContractUnitWasteCav( new
	 * Request(CommonVars.getCurrUser()), parentId, -1, -1, isShowZero);
	 * exgCavList = resultList.get(0); unitWasteList = resultList.get(1); count
	 * = exgCavList.size(); }
	 * ContractcavReportVars.setContractExgCavList(exgCavList);
	 * CustomReportDataSource ds1 = new CustomReportDataSource( unitWasteList);
	 * InputStream masterReportStream = DgContractCav.class
	 * .getResourceAsStream("report/ContractUnitWasteCav.jasper");
	 * parameters.put("isOverprint", new Boolean(false));
	 * parameters.put("count", count); parameters.put("isPrintZero",
	 * isPrintZero); JasperPrint jasperPrint = JasperFillManager.fillReport(
	 * masterReportStream, parameters, ds1); DgReportViewer viewer = new
	 * DgReportViewer(jasperPrint); viewer.setVisible(true); } catch (Exception
	 * ex) { ex.printStackTrace(); } break; case 7:// 套打核销单耗表 try { Boolean
	 * isPrintZero = true; // if
	 * (JOptionPane.showConfirmDialog(DgContractCav.this, // "当损耗数等为0时，是否打印？",
	 * "提示", JOptionPane.YES_NO_OPTION) != // JOptionPane.YES_OPTION) { //
	 * isPrintZero = false; // } Object[] message = new Object[2];
	 * 
	 * message[0] = getJCheckBox3(); message[1] = getJCheckBox4(); int ms =
	 * JOptionPane.showOptionDialog(DgContractCav.this, message, "请选择！",
	 * JOptionPane.CLOSED_OPTION, // option type
	 * JOptionPane.INFORMATION_MESSAGE, null, new Object[] { "确定" }, "确定"); if
	 * (!getJCheckBox3().isSelected()) { isPrintZero = false; } boolean
	 * isShowZero = false; if (!getJCheckBox4().isSelected()) { isShowZero =
	 * true; } List<List> resultList = new ArrayList<List>();
	 * List<ContractUnitWasteCav> unitWasteList = new
	 * ArrayList<ContractUnitWasteCav>(); List<ContractExgCav> exgCavList = new
	 * ArrayList<ContractExgCav>(); int count = 0; if (contractCav != null) {
	 * String parentId = contractCav.getId(); resultList =
	 * contractCavAction.findContractUnitWasteCav( new
	 * Request(CommonVars.getCurrUser()), parentId, -1, -1, isShowZero);
	 * exgCavList = resultList.get(0); unitWasteList = resultList.get(1); count
	 * = exgCavList.size(); }
	 * ContractcavReportVars.setContractExgCavList(exgCavList);
	 * CustomReportDataSource ds1 = new CustomReportDataSource( unitWasteList);
	 * InputStream masterReportStream = DgContractCav.class
	 * .getResourceAsStream("report/ContractUnitWasteCav.jasper");
	 * parameters.put("isOverprint", new Boolean(true)); parameters.put("count",
	 * count); parameters.put("isPrintZero", isPrintZero); JasperPrint
	 * jasperPrint = JasperFillManager.fillReport( masterReportStream,
	 * parameters, ds1); DgReportViewer viewer = new
	 * DgReportViewer(jasperPrint); viewer.setVisible(true); } catch (Exception
	 * ex) { ex.printStackTrace(); } break; case 8:// 非套打核销单头(新) //
	 * list=contractCavAction.findContractImgCav(new Request( //
	 * CommonVars.getCurrUser()), contractCavCustoms); list.add(contractCav); ds
	 * = new CustomReportDataSource(list); reportStream = DgContractCav.class
	 * .getResourceAsStream("report/ContractCavHeadNew.jasper"); parameters =
	 * new HashMap<String, Object>(); parameters.put("companyCode",
	 * company.getCode()); parameters.put("companyName", company.getName());
	 * parameters.put("companyTel", company.getTel());
	 * parameters.put("overprint", new Boolean(false)); try { JasperPrint
	 * jasperPrint = JasperFillManager.fillReport( reportStream, parameters,
	 * ds); DgReportViewer viewer = new DgReportViewer(jasperPrint);
	 * viewer.setVisible(true); } catch (JRException e1) { e1.printStackTrace();
	 * } break; case 9:// 非套打核销单耗表(新) try { Boolean isPrintZero = true; // if
	 * (JOptionPane.showConfirmDialog(DgContractCav.this, // "当损耗数等为0时，是否打印？",
	 * "提示", JOptionPane.YES_NO_OPTION) != // JOptionPane.YES_OPTION) { //
	 * isPrintZero = false; // } Object[] message = new Object[2];
	 * 
	 * message[0] = getJCheckBox3(); message[1] = getJCheckBox4(); int ms =
	 * JOptionPane.showOptionDialog(DgContractCav.this, message, "请选择！",
	 * JOptionPane.CLOSED_OPTION, // option type
	 * JOptionPane.INFORMATION_MESSAGE, null, new Object[] { "确定" }, "确定"); if
	 * (!getJCheckBox3().isSelected()) { isPrintZero = false; } boolean
	 * isShowZero = false; if (!getJCheckBox4().isSelected()) { isShowZero =
	 * true; } List<List> resultList = new ArrayList<List>();
	 * List<ContractUnitWasteCav> unitWasteList = new
	 * ArrayList<ContractUnitWasteCav>(); List<ContractExgCav> exgCavList = new
	 * ArrayList<ContractExgCav>(); int count = 0; if (contractCav != null) {
	 * String parentId = contractCav.getId(); resultList =
	 * contractCavAction.findContractUnitWasteCav( new
	 * Request(CommonVars.getCurrUser()), parentId, -1, -1, isPrintZero);
	 * exgCavList = resultList.get(0); unitWasteList = resultList.get(1); count
	 * = exgCavList.size(); }
	 * ContractcavReportVars.setContractExgCavList(exgCavList);
	 * CustomReportDataSource ds1 = new CustomReportDataSource( unitWasteList);
	 * InputStream masterReportStream = DgContractCav.class
	 * .getResourceAsStream("report/ContractUnitWasteCavNew.jasper");
	 * parameters.put("isOverprint", new Boolean(false));
	 * parameters.put("count", count); parameters.put("isPrintZero",
	 * isPrintZero); JasperPrint jasperPrint = JasperFillManager.fillReport(
	 * masterReportStream, parameters, ds1); DgReportViewer viewer = new
	 * DgReportViewer(jasperPrint); viewer.setVisible(true); } catch (Exception
	 * ex) { ex.printStackTrace(); } break; } }
	 */
	/**
	 * This method initializes tfEmsNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfEmsNo() {
		if (tfEmsNo == null) {
			tfEmsNo = new JTextField();
			tfEmsNo.setEditable(false);
			tfEmsNo.setBounds(new Rectangle(158, 21, 202, 24));
			// tfEmsNo.setBackground(java.awt.Color.white);
		}
		return tfEmsNo;
	}

	/**
	 * This method initializes tfContractNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfContractNo() {
		if (tfContractNo == null) {
			tfContractNo = new JTextField();
			tfContractNo.setEditable(false);
			tfContractNo.setBounds(new Rectangle(464, 21, 180, 24));
			// tfContractNo.setBackground(java.awt.Color.white);
		}
		return tfContractNo;
	}

	/**
	 * This method initializes tfCompanyName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCompanyName() {
		if (tfCompanyName == null) {
			tfCompanyName = new JTextField();
			tfCompanyName.setEditable(false);
			tfCompanyName.setBounds(new Rectangle(158, 53, 202, 24));
			// tfCompanyName.setBackground(java.awt.Color.white);
		}
		return tfCompanyName;
	}

	/**
	 * This method initializes tfTelephone
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfTelephone() {
		if (tfTelephone == null) {
			tfTelephone = new JTextField();
			tfTelephone.setEditable(false);
			tfTelephone.setBounds(new Rectangle(546, 54, 98, 24));
			// tfTelephone.setBackground(java.awt.Color.white);
		}
		return tfTelephone;
	}

	/**
	 * This method initializes tfEndDate
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfEndDate() {
		if (tfEndDate == null) {
			tfEndDate = new JTextField();
			tfEndDate.setEditable(false);
			tfEndDate.setBounds(new Rectangle(158, 85, 202, 24));
			// tfEndDate.setBackground(java.awt.Color.white);
		}
		return tfEndDate;
	}

	/**
	 * This method initializes tfEmsApprNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfEmsApprNo() {
		if (tfEmsApprNo == null) {
			tfEmsApprNo = new JTextField();
			tfEmsApprNo.setEditable(false);
			tfEmsApprNo.setBounds(new Rectangle(464, 85, 180, 24));
			// tfEmsApprNo.setBackground(java.awt.Color.white);
		}
		return tfEmsApprNo;
	}

	/**
	 * This method initializes tfImpTotalMoney
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfImpTotalMoney() {
		if (tfImpTotalMoney == null) {
			tfImpTotalMoney = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			// tfImpTotalMoney.setEditable(false);
			tfImpTotalMoney.setBounds(new Rectangle(158, 118, 120, 24));
			// tfImpTotalMoney.setBackground(java.awt.Color.white);
			tfImpTotalMoney.setToolTipText("国内购买不计算在内");
			tfImpTotalMoney.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfImpTotalMoney;
	}

	/**
	 * This method initializes tfExpTotalMoney
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfExpTotalMoney() {
		if (tfExpTotalMoney == null) {
			tfExpTotalMoney = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			// tfExpTotalMoney.setEditable(false);
			tfExpTotalMoney.setBounds(new Rectangle(357, 118, 104, 24));
			// tfExpTotalMoney.setBackground(java.awt.Color.white);
			tfExpTotalMoney.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfExpTotalMoney;
	}

	/**
	 * This method initializes tfProcessTotalPrice
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfProcessTotalPrice() {
		if (tfProcessTotalPrice == null) {
			tfProcessTotalPrice = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			// tfProcessTotalPrice.setEditable(false);
			tfProcessTotalPrice.setBounds(new Rectangle(503, 118, 92, 24));
			// tfProcessTotalPrice.setBackground(java.awt.Color.white);
			tfProcessTotalPrice
					.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfProcessTotalPrice;
	}

	/**
	 * This method initializes tfImpCDCount
	 * 
	 * @return javax.swing.JTextField
	 */
	private JFormattedTextField getTfImpCDCount() {
		if (tfImpCDCount == null) {
			tfImpCDCount = new JFormattedTextField();
			tfImpCDCount.setBounds(new Rectangle(158, 150, 202, 24));
			// tfImpCDCount.setBackground(java.awt.Color.white);
			tfImpCDCount.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfImpCDCount;
	}

	/**
	 * This method initializes tfExpCDCount
	 * 
	 * @return javax.swing.JTextField
	 */
	private JFormattedTextField getTfExpCDCount() {
		if (tfExpCDCount == null) {
			tfExpCDCount = new JFormattedTextField();
			tfExpCDCount.setBounds(new Rectangle(464, 150, 180, 24));
			// tfExpCDCount.setBackground(java.awt.Color.white);
			tfExpCDCount.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfExpCDCount;
	}

	/**
	 * This method initializes tfInternalSaleWarrant
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfInternalSaleWarrant() {
		if (tfInternalSaleWarrant == null) {
			tfInternalSaleWarrant = new JTextField();
			tfInternalSaleWarrant.setBounds(new Rectangle(158, 218, 202, 24));
		}
		return tfInternalSaleWarrant;
	}

	/**
	 * This method initializes tfInternalSaleTaxBill
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfInternalSaleTaxBill() {
		if (tfInternalSaleTaxBill == null) {
			tfInternalSaleTaxBill = new JTextField();
			tfInternalSaleTaxBill.setBounds(new Rectangle(464, 218, 180, 24));
		}
		return tfInternalSaleTaxBill;
	}

	/**
	 * This method initializes tfInternalSale
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfInternalSale() {
		if (tfInternalSale == null) {
			tfInternalSale = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfInternalSale.setBounds(new Rectangle(358, 184, 104, 24));
			tfInternalSale.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfInternalSale;
	}

	/**
	 * This method initializes tfInternalSaleTax
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfInternalSaleTax() {
		if (tfInternalSaleTax == null) {
			tfInternalSaleTax = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfInternalSaleTax.setBounds(new Rectangle(158, 184, 120, 24));
			tfInternalSaleTax.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfInternalSaleTax;
	}

	// tfImportDeviceGross
	/**
	 * This method initializes tfRemainMoney
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfRemainMoney() {
		if (tfRemainMoney == null) {
			tfRemainMoney = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			// tfRemainMoney.setEditable(false);
			tfRemainMoney.setBounds(new Rectangle(158, 254, 202, 24));
			// tfRemainMoney.setBackground(java.awt.Color.white);
			tfRemainMoney.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfRemainMoney;
	}

	/**
	 * This method initializes tfRemainEmsNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfRemainEmsNo() {
		if (tfRemainEmsNo == null) {
			tfRemainEmsNo = new JTextField();
			// tfRemainEmsNo.setEditable(false);
			tfRemainEmsNo.setBounds(new Rectangle(464, 254, 180, 24));
			// tfRemainEmsNo.setBackground(java.awt.Color.white);
		}
		return tfRemainEmsNo;
	}

	/**
	 * This method initializes tfCurrency
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCurrency() {
		if (tfCurrency == null) {
			tfCurrency = new JTextField();
			tfCurrency.setEditable(false);
			tfCurrency.setBounds(new Rectangle(415, 324, 65, 24));
			// tfCurrency.setBackground(java.awt.Color.white);
		}
		return tfCurrency;
	}

	/**
	 * This method initializes tfCautionMoney
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCautionMoney() {
		if (tfCautionMoney == null) {
			tfCautionMoney = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			// tfCautionMoney.setEditable(false);
			tfCautionMoney.setBounds(new Rectangle(158, 290, 109, 24));
			// tfCautionMoney.setBackground(java.awt.Color.white);
			tfCautionMoney.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfCautionMoney;
	}

	/**
	 * This method initializes cbbIsSusceptivity
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbIsSusceptivity() {
		if (cbbIsSusceptivity == null) {
			cbbIsSusceptivity = new JComboBox();
			cbbIsSusceptivity.setBounds(new Rectangle(367, 290, 95, 24));
		}
		return cbbIsSusceptivity;
	}

	/**
	 * This method initializes cbbIsLimit
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbIsLimit() {
		if (cbbIsLimit == null) {
			cbbIsLimit = new JComboBox();
			cbbIsLimit.setBounds(new Rectangle(543, 290, 100, 24));
		}
		return cbbIsLimit;
	}

	/**
	 * This method initializes btnReCav
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnReCav() {
		if (btnReCav == null) {
			btnReCav = new JButton();
			btnReCav.setText("重新获取自用核销表");
			btnReCav.setBounds(new Rectangle(484, 395, 166, 23));
			btnReCav.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (rbSelf.isSelected()) {
						if (JOptionPane.showConfirmDialog(DgContractCav.this,
								"你确定想重新计算自用核销表吗?", "提示",
								JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
							new ReMakeSelfuseContractCavThread().start();
						}
					} else if (rbCustoms.isSelected()) {
						if (JOptionPane.showConfirmDialog(DgContractCav.this,
								"你确定想重新获取海关核销表吗?", "提示",
								JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
							new ReGetCustomsContractCavThread().start();
						}
					}
				}
			});
		}
		return btnReCav;
	}

	/**
	 * 多线程
	 * 
	 * @author ower
	 * 
	 */
	private class ReMakeSelfuseContractCavThread extends Thread {
		public void run() {
			CommonProgress.showProgressDialog();
			CommonProgress.setMessage("系统正在重新计算自用核销表,请等待...");
			try {
				contractCavAction.reMakeSelfuseContractCav(new Request(
						CommonVars.getCurrUser()), contract.getEmsNo());
				contractCavSelf = (ContractCav) contractCavAction
						.findContractCav(new Request(CommonVars.getCurrUser()),
								contract.getEmsNo(), false);
				showData(contractCavSelf);
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				e.printStackTrace();
			}
		}
	}

	/**
	 * 多线程
	 * 
	 * @author ower
	 * 
	 */
	private class ReGetCustomsContractCavThread extends Thread {
		public void run() {
			CommonProgress.showProgressDialog();
			CommonProgress.setMessage("系统正在重新获取海关核销表,请等待...");
			try {
				contractCavAction.reGetCustomsContractCav(new Request(
						CommonVars.getCurrUser()), contract.getEmsNo(), cb
						.isSelected());
				contractCavCustoms = (ContractCav) contractCavAction
						.findContractCav(new Request(CommonVars.getCurrUser()),
								contract.getEmsNo(), true);
				showData(contractCavCustoms);
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				e.printStackTrace();
			}
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
			btnSave.setBounds(new Rectangle(121, 395, 59, 23));
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					fillData(contractCavCustoms);
					contractCavCustoms = contractCavAction.saveContractCav(
							new Request(CommonVars.getCurrUser()),
							contractCavCustoms);
					dataState = DataState.BROWSE;
					System.out.println(contractCavCustoms.getImpCDCount()
							+ " ---IMP");
					System.out.println(contractCavCustoms.getExpCDCount()
							+ " ---EXP");
					setState(); // actionPerformed()
				}
			});
		}
		return btnSave;
	}

	/**
	 * This method initializes btnUndo
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnUndo() {
		if (btnUndo == null) {
			btnUndo = new JButton();
			btnUndo.setText("取消");
			btnUndo.setBounds(new Rectangle(197, 395, 58, 23));
			btnUndo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (rbSelf.isSelected()) {
						showData(contractCavSelf);
					} else {
						showData(contractCavCustoms);
					}
					dataState = DataState.BROWSE;
					setState(); // actionPerformed()
				}
			});
		}
		return btnUndo;
	}

	/**
	 * This method initializes btnCheck
	 * 
	 * @return javax.swing.JButton
	 */
	// hwy2012-11-12
	private JButton getBtnCheck() {
		if (btnCheck == null) {
			btnCheck = new JButton();
			btnCheck.setText("检查");
			btnCheck.setBounds(new Rectangle(276, 395, 60, 23));
			btnCheck.setVisible(true);
			btnCheck.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					StringBuffer logImg = new StringBuffer("");
					logImg = contractCavAction.checkContractCavData(
							new Request(CommonVars.getCurrUser()),
							contractCavCustoms);
					if (logImg.length() <= 0) {
						JOptionPane.showMessageDialog(DgContractCav.this,
								"检查完毕，没有错误！", "提示",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(DgContractCav.this,
								logImg, "提示", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});
		}
		return btnCheck;
	}

	// private JButton getBtnCheck() {
	// if (btnCheck == null) {
	// btnCheck = new JButton();
	// btnCheck.setText("检查");
	// btnCheck.setBounds(new Rectangle(276, 395, 60, 23));
	// btnCheck.setVisible(true);
	// btnCheck.addActionListener(new java.awt.event.ActionListener() {
	// public void actionPerformed(java.awt.event.ActionEvent e) {
	// if (contractCavAction.checkContractCavData(new Request(
	// CommonVars.getCurrUser()), contractCavCustoms)) {
	// JOptionPane.showMessageDialog(DgContractCav.this,
	// "检查完毕，没有错误", "提示",
	// JOptionPane.INFORMATION_MESSAGE);
	// }
	// }
	// });
	// }
	// return btnCheck;
	// }

	/**
	 * This method initializes tfTotalPages
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfTotalPages() {
		if (tfTotalPages == null) {
			tfTotalPages = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfTotalPages.setBounds(new Rectangle(158, 324, 202, 24));
			tfTotalPages.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfTotalPages;
	}

	/**
	 * This method initializes tbExg
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbExg() {
		if (tbExg == null) {
			tbExg = new JTable();
			tbExg.setRowHeight(25);
		}
		return tbExg;
	}

	/**
	 * This method initializes spn
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getSpn() {
		if (spn == null) {
			spn = new JScrollPane();
			spn.setViewportView(getTbExg());
		}
		return spn;
	}

	/**
	 * This method initializes tbImg
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbImg() {
		if (tbImg == null) {
			tbImg = new JTable();
			tbImg.setRowHeight(25);
		}
		return tbImg;
	}

	/**
	 * This method initializes spn1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getSpn1() {
		if (spn1 == null) {
			spn1 = new JScrollPane();
			spn1.setViewportView(getTbImg());
		}
		return spn1;
	}

	/**
	 * This method initializes tbBom
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbBom() {
		if (tbBom == null) {
			tbBom = new JTable();
		}
		return tbBom;
	}

	/**
	 * This method initializes spn2
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getSpn2() {
		if (spn2 == null) {
			spn2 = new JScrollPane();
			spn2.setViewportView(getTbBom());
		}
		return spn2;
	}

	/**
	 * This method initializes btnInteralBuy
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnInteralBuy() {
		if (btnInteralBuy == null) {
			btnInteralBuy = new JButton();
			btnInteralBuy.setText("国内购买");
			btnInteralBuy
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgContractInteralBuy dgContractImgCav = new DgContractInteralBuy();
							dgContractImgCav.setContractCav(contractCavCustoms);
							dgContractImgCav.setTableModel(tableModelImg);
							dgContractImgCav.setVisible(true);
							// ContractImgCav contractImgCav = dgContractImgCav
							// .getContractImgCav();
							// List list=tableModelImg.getList();
							// list.add(contractImgCav);
							// resortDataForInteralBuy(list);
							// if (contractImgCav != null) {
							// tableModelImg.setList(list);
							// }
							showImgData(true);
						}
					});
		}
		return btnInteralBuy;
	}

	/**
	 * This method initializes btnDeleteImg
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDeleteImg() {
		if (btnDeleteImg == null) {
			btnDeleteImg = new JButton();
			btnDeleteImg.setText("删除");
			btnDeleteImg.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelImg.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgContractCav.this,
								"请选择要删除的数据", "提示", JOptionPane.OK_OPTION);
						return;
					}
					ContractImgCav contractImgCav = (ContractImgCav) tableModelImg
							.getCurrentRow();
					if (contractImgCav.getName().indexOf("(国内购买)") < 0) {
						JOptionPane.showMessageDialog(DgContractCav.this,
								"只删除国内购买的数据", "提示", JOptionPane.OK_OPTION);
						return;
					}
					if (JOptionPane.showConfirmDialog(DgContractCav.this,
							"你确定要删除此海关核销料件吗?", "提示!",
							JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
						contractCavAction.deleteContractImgCav(new Request(
								CommonVars.getCurrUser()), contractImgCav);
						tableModelImg.deleteRow(contractImgCav);
					}
				}
			});
		}
		return btnDeleteImg;
	}

	/**
	 * This method initializes btnLeftoverMaterial
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnLeftoverMaterial() {
		if (btnLeftoverMaterial == null) {
			btnLeftoverMaterial = new JButton();
			btnLeftoverMaterial.setText("计算边角料");
			btnLeftoverMaterial
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (contractCavCustoms == null) {
								return;
							}
							new doSwingWorker<Object, List<String>>().execute();
						}
					});
		}
		return btnLeftoverMaterial;
	}

	class doSwingWorker<T, V> extends SwingWorker<T, V> {

		@Override
		protected T doInBackground() throws Exception {
			String taskId = CommonProgress.getExeTaskId();
			CommonStepProgress.showStepProgressDialog(taskId);
			CommonStepProgress.setStepMessage("系统计算边角料，请稍后...");
			contractCavAction.recalRemainMaterialAmount(
					new Request(CommonVars.getCurrUser()), contractCavCustoms);
			return null;
		}

		@Override
		protected void done() {
			CommonStepProgress.closeStepProgressDialog();
			showExgData(true);
			showImgData(true);
		}

	}

	/**
	 * This method initializes cbImg
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbImg() {
		if (cbImg == null) {
			cbImg = new JCheckBox();
			cbImg.setText("修改成品耗用不反算单耗");
			cbImg.setSelected(true);
		}
		return cbImg;
	}

	/**
	 * This method initializes rbTotal
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbTotal() {
		if (rbTotal == null) {
			rbTotal = new JRadioButton();
			rbTotal.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cbBom4.setEnabled(false);
					cbBom2.setEnabled(true);
				}
			});
			rbTotal.setSelected(true);
			rbTotal.setText("总用量");
		}
		return rbTotal;
	}

	/**
	 * This method initializes rbWaste
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbWaste() {
		if (rbWaste == null) {
			rbWaste = new JRadioButton();
			rbWaste.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cbBom4.setEnabled(true);
					cbBom2.setEnabled(false);
				}
			});
			rbWaste.setText("损耗量");
		}
		return rbWaste;
	}

	/**
	 * This method initializes btnRoundAmount
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnRoundAmount() {
		if (btnRoundAmount == null) {
			btnRoundAmount = new JButton();
			btnRoundAmount.setText("   数量取整  ");
			btnRoundAmount
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (JOptionPane.showConfirmDialog(
									DgContractCav.this, "你确定要数量取整?", "提示",
									JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) {
								return;
							}
							contractCavAction.convertWasteAmountToInteger(
									new Request(CommonVars.getCurrUser()),
									contractCavCustoms, rbTotal.isSelected(),
									cbBom2.isSelected(), cbBom3.isSelected());
							showBomData(rbCustoms.isSelected());
						}
					});
		}
		return btnRoundAmount;
	}

	/**
	 * This method initializes cbBom1
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbBom1() {
		if (cbBom1 == null) {
			cbBom1 = new JCheckBox();
			cbBom1.setText("修改单耗不反算损耗量,总用量,成品耗用量和料件余量");
			cbBom1.setSelected(true);
		}
		return cbBom1;
	}

	/**
	 * This method initializes cbBom2
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbBom2() {
		if (cbBom2 == null) {
			cbBom2 = new JCheckBox();
			cbBom2.setText("修改总用量不反算损耗量,单耗,成品耗用量和料件余量");
			cbBom2.setSelected(true);
		}
		return cbBom2;
	}

	/**
	 * This method initializes cbBom3
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbBom3() {
		if (cbBom3 == null) {
			cbBom3 = new JCheckBox();
			cbBom3.setText("修改损耗量不反算单耗,总用量,成品耗用量和料件余量");
			cbBom3.setVisible(false);
		}
		return cbBom3;
	}

	/**
	 * This method initializes bgAmount
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getBgAmount() {
		if (bgAmount == null) {
			bgAmount = new ButtonGroup();
			bgAmount.add(this.getRbTotal());
			bgAmount.add(this.getRbWaste());
		}
		return bgAmount;
	}

	/**
	 * This method initializes pn6
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPn6() {
		if (pn6 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("净重损失率(参考)");
			jLabel1.setBounds(new Rectangle(20, 20, 92, 20));
			jLabel = new JLabel();
			jLabel.setText("增值率");
			jLabel.setBounds(new Rectangle(33, 18, 38, 21));
			pn6 = new JPanel();
			lbCaption23 = new JLabel();
			lbCaption24 = new JLabel();
			lbCaption25 = new JLabel();
			lbCaption26 = new JLabel();
			lbCaption27 = new JLabel();
			lbCaption28 = new JLabel();
			lbCaption29 = new JLabel();
			lbCaption30 = new JLabel();
			pn6.setLayout(null);
			pn6.setBorder(BorderFactory.createTitledBorder(null, "",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.PLAIN, 12), new Color(51, 51, 51)));
			pn6.setBounds(new Rectangle(18, 23, 670, 105));
			lbCaption23.setBounds(26, 52, 50, 21);
			lbCaption23.setText("出口总值");
			lbCaption24.setBounds(171, 17, 64, 22);
			lbCaption24.setText("出口加工费");
			lbCaption25.setBounds(170, 51, 65, 20);
			lbCaption25.setText("出口总件数");
			lbCaption26.setBounds(331, 15, 50, 22);
			lbCaption26.setText("进口毛重");
			lbCaption27.setBounds(330, 49, 50, 21);
			lbCaption27.setText("出口毛重");
			lbCaption28.setBounds(486, 17, 50, 20);
			lbCaption28.setText("进口净重");
			lbCaption29.setBounds(485, 51, 55, 17);
			lbCaption29.setText("出口净重 ");
			lbCaption30.setBounds(2, 17, 75, 24);
			lbCaption30.setText("实际进口总值");
			pn6.add(lbCaption23, null);
			pn6.add(lbCaption24, null);
			pn6.add(lbCaption25, null);
			pn6.add(lbCaption26, null);
			pn6.add(lbCaption27, null);
			pn6.add(lbCaption28, null);
			pn6.add(lbCaption29, null);
			pn6.add(lbCaption30, null);
			pn6.add(getTfImpTotalValue(), null);
			pn6.add(getTfExpTotalValue(), null);
			pn6.add(getTfExpManufactureMoney(), null);
			pn6.add(getTfExpTotalItems(), null);
			pn6.add(getTfImpTotalGrossWeight(), null);
			pn6.add(getTfImpNetWeight(), null);
			pn6.add(getTfExpTotalGrossWeight(), null);
			pn6.add(getTfExpNetWeight(), null);
		}
		return pn6;
	}

	/**
	 * This method initializes tfImpTotalValue
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfImpTotalValue() {
		if (tfImpTotalValue == null) {
			tfImpTotalValue = new JTextField();
			tfImpTotalValue.setEditable(false);
			tfImpTotalValue.setBounds(new Rectangle(81, 18, 85, 22));
			// tfImpTotalValue.setBackground(java.awt.Color.white);
		}
		return tfImpTotalValue;
	}

	/**
	 * This method initializes tfExpTotalValue
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfExpTotalValue() {
		if (tfExpTotalValue == null) {
			tfExpTotalValue = new JTextField();
			tfExpTotalValue.setEditable(false);
			tfExpTotalValue.setBounds(new Rectangle(81, 51, 85, 22));
			// tfExpTotalValue.setBackground(java.awt.Color.white);
		}
		return tfExpTotalValue;
	}

	/**
	 * This method initializes tfExpManufactureMoney
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfExpManufactureMoney() {
		if (tfExpManufactureMoney == null) {
			tfExpManufactureMoney = new JTextField();
			tfExpManufactureMoney.setEditable(false);
			tfExpManufactureMoney.setBounds(new Rectangle(235, 17, 89, 22));
			// tfExpManufactureMoney.setBackground(java.awt.Color.white);
		}
		return tfExpManufactureMoney;
	}

	/**
	 * This method initializes tfExpTotalItems
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfExpTotalItems() {
		if (tfExpTotalItems == null) {
			tfExpTotalItems = new JTextField();
			tfExpTotalItems.setEditable(false);
			tfExpTotalItems.setBounds(new Rectangle(235, 50, 89, 22));
			// tfExpTotalItems.setBackground(java.awt.Color.white);
		}
		return tfExpTotalItems;
	}

	/**
	 * This method initializes tfImpTotalGrossWeight
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfImpTotalGrossWeight() {
		if (tfImpTotalGrossWeight == null) {
			tfImpTotalGrossWeight = new JTextField();
			tfImpTotalGrossWeight.setEditable(false);
			tfImpTotalGrossWeight.setBounds(new Rectangle(383, 16, 97, 22));
			// tfImpTotalGrossWeight.setBackground(java.awt.Color.white);
		}
		return tfImpTotalGrossWeight;
	}

	/**
	 * This method initializes tfImpNetWeight
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfImpNetWeight() {
		if (tfImpNetWeight == null) {
			tfImpNetWeight = new JTextField();
			tfImpNetWeight.setEditable(false);
			tfImpNetWeight.setBounds(new Rectangle(544, 16, 106, 21));
			// tfImpNetWeight.setBackground(java.awt.Color.white);
		}
		return tfImpNetWeight;
	}

	/**
	 * This method initializes tfExpTotalGrossWeight
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfExpTotalGrossWeight() {
		if (tfExpTotalGrossWeight == null) {
			tfExpTotalGrossWeight = new JTextField();
			tfExpTotalGrossWeight.setEditable(false);
			tfExpTotalGrossWeight.setBounds(new Rectangle(383, 49, 97, 22));
			// tfExpTotalGrossWeight.setBackground(java.awt.Color.white);
		}
		return tfExpTotalGrossWeight;
	}

	/**
	 * This method initializes tfExpNetWeight
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfExpNetWeight() {
		if (tfExpNetWeight == null) {
			tfExpNetWeight = new JTextField();
			tfExpNetWeight.setEditable(false);
			tfExpNetWeight.setBounds(new Rectangle(544, 49, 106, 21));
			// tfExpNetWeight.setBackground(java.awt.Color.white);
		}
		return tfExpNetWeight;
	}

	public static String formatDoubleToString(Double d, int digitsSize) {
		if (d == null) {
			return "";
		}
		DecimalFormat format = new DecimalFormat();
		format.setGroupingSize(999);
		format.setMaximumFractionDigits(digitsSize);
		return format.format(d).toString();
	}

	/**
	 * This method initializes btnCal
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton5() {
		if (btnCal == null) {
			btnCal = new JButton();
			btnCal.setText("计算");
			btnCal.setBounds(new Rectangle(452, 290, 85, 24));
			btnCal.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ContractCav contractCav = null;
					if (rbSelf.isSelected()) {
						contractCav = contractCavSelf;
					} else if (rbCustoms.isSelected()) {
						contractCav = contractCavCustoms;
					}
					if (contractCav == null) {
						return;
					}
					ContractCavTotalValue totalValue = contractCavAction
							.findCavTotalValue(
									new Request(CommonVars.getCurrUser()),
									contractCav, contract);
					contractCav = (ContractCav) contractCavAction.load(
							new Request(CommonVars.getCurrUser()),
							ContractCav.class, contractCav.getId());
					fillContractCavTotalValue(totalValue, contractCav);
				}
			});
		}
		return btnCal;
	}

	/**
	 * 填充其它
	 * 
	 * @param totalValue
	 */
	private void fillContractCavTotalValue(ContractCavTotalValue totalValue,
			ContractCav contractCav) {
		int reportDecimalLength = parameterSet.getReportDecimalLength() == null ? 8
				: parameterSet.getReportDecimalLength();
		tfImpTotalValue.setText(totalValue.getImpAmount() == null ? ""
				: CommonVars.formatDoubleToString(totalValue.getImpAmount(),
						999, reportDecimalLength));
		tfExpTotalValue.setText(totalValue.getExpAmount() == null ? ""
				: CommonVars.formatDoubleToString(totalValue.getExpAmount(),
						999, reportDecimalLength));
		tfExpManufactureMoney.setText(formatDoubleToString(
				totalValue.getProcessTotalPrice(), reportDecimalLength));
		tfExpTotalItems.setText(formatDoubleToString(
				totalValue.getExpTotalPieces(), reportDecimalLength));
		tfImpTotalGrossWeight.setText(formatDoubleToString(
				totalValue.getImpTotalGrossWeight(), reportDecimalLength));
		tfExpTotalGrossWeight.setText(formatDoubleToString(
				totalValue.getExpTotalGrossWeight(), reportDecimalLength));
		tfImpNetWeight.setText(formatDoubleToString(
				totalValue.getImpTotalNetWeight(), reportDecimalLength));
		tfExpNetWeight.setText(formatDoubleToString(
				totalValue.getExpTotalNetWeight(), reportDecimalLength));
		tfValueAddedRate
				.setText(String.valueOf(contractCav.getValueAddedRate()));
		double resultWeight = contractCavAction.cavContractNetWeightLossRate(
				new Request(CommonVars.getCurrUser()), contract.getEmsNo());
		tfNetWeightLossRate.setText(String.valueOf(resultWeight));
		// 本币汇率
		Double currrate = 0.0;
		if (!contract.getCurr().getCurrSymb().trim().equals("CNY")) {
			List list = contractAction.findCurrRateByCurr(new Request(
					CommonVars.getCurrUser()), contract.getCurr().getCurrSymb()
					.trim());
			if (list.size() == 0) {
				JOptionPane.showMessageDialog(DgContractCav.this,
						"没有找到本币对人民币汇率!", "提示", JOptionPane.INFORMATION_MESSAGE);
				return;
			} else {
				CurrRate cr = (CurrRate) list.get(list.size() - 1);
				currrate = cr.getRate();
				System.out.println("currrate=" + currrate);
			}
		}
		Double REMAIN_FORWARD = 0.0;
		List<BcsCustomsDeclaration> list = contractCavAction
				.findBcsCustomsDeclarationCav(
						new Request(CommonVars.getCurrUser()), contractCav);
		Map<String, Double> map = contractCavAction
				.findCustomsDeclarationMoney(
						new Request(CommonVars.getCurrUser()),
						ImpExpFlag.IMPORT);
		for (BcsCustomsDeclaration b : list) {
			// System.out.println("map.get(b.getId())="
			// + map.get(b.getId()));
			REMAIN_FORWARD += map.get(b.getId());
		}
		// 口岸费
		Double fund = 0.0;
		// 进出口总值
		Double ImpExptotalValue = 0.0;
		// 获取合同性质
		String emsType = null;
		if (null != contract) {
			emsType = ContractKind.getContractKindSpec(contract.getEmsType());
		}
		// 计算比例
		Double countScale = 0.0;
		if (null != contract.getCountScale()) {
			countScale = contract.getCountScale();
		} else {
			System.out.println("计算比例为空");
			if (emsType.trim().equals("来料加工")
					|| emsType.trim().equals("纸质手册电子化来料手册")) {
				countScale = 0.002;
			} else if (emsType.trim().equals("进料加工")
					|| emsType.trim().equals("纸质手册电子化进料手册")) {
				countScale = 0.0005;
			}
		}
		if (emsType.trim().equals("来料加工")
				|| emsType.trim().equals("纸质手册电子化来料手册")) {
			fund = totalValue.getProcessTotalPrice() * countScale * currrate;
			tfFund.setText(format(fund));
		} else if (emsType.trim().equals("进料加工")
				|| emsType.trim().equals("纸质手册电子化进料手册")) {
			ImpExptotalValue = Double.valueOf(tfImpTotalValue.getText())
					+ Double.valueOf(tfExpTotalValue.getText())
					- REMAIN_FORWARD;
			fund = ImpExptotalValue * countScale * currrate;
			tfFund.setText(format(fund));
		}

	}

	/**
	 * This method initializes defaultFormatterFactory
	 * 
	 * @return javax.swing.text.DefaultFormatterFactory
	 */
	private DefaultFormatterFactory getDefaultFormatterFactory() {
		if (defaultFormatterFactory == null) {
			defaultFormatterFactory = new DefaultFormatterFactory();
			defaultFormatterFactory.setDefaultFormatter(getNumberFormatter());
			defaultFormatterFactory.setEditFormatter(getNumberFormatter());
			defaultFormatterFactory.setNullFormatter(getNumberFormatter());
			defaultFormatterFactory.setDisplayFormatter(getNumberFormatter());
		}
		return defaultFormatterFactory;
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
	 * 显示数据
	 * 
	 * @param contractCav
	 */
	private void showData(ContractCav contractCav) {
		if (contractCav == null) {
			return;
		}
		this.tfEmsNo.setText(contractCav.getEmsNo());
		this.tfContractNo.setText(contractCav.getContractNo());
		this.tfCompanyName.setText(contractCav.getCompany() == null ? ""
				: ((Company) contractCav.getCompany()).getName());
		this.tfTelephone.setText(contractCav.getCompany() == null ? ""
				: ((Company) contractCav.getCompany()).getTel());
		this.tfCoLevel.setText(contractCav.getCompany() == null ? ""
				: ((Company) contractCav.getCompany()).getCoLevel());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		this.tfEndDate.setText(contractCav.getEndDate() == null ? ""
				: dateFormat.format(contractCav.getEndDate()));
		this.tfEmsApprNo.setText(contractCav.getEmsApprNo());
		this.tfImpTotalMoney.setValue(contractCav.getImpAmount());
		this.tfExpTotalMoney.setValue(contractCav.getExpAmount());
		this.tfProcessTotalPrice.setValue(contractCav.getProcessTotalPrice());
		this.lbCurrency.setText(contractCav.getCurr() == null ? ""
				: contractCav.getCurr().getName());
		this.tfImpCDCount.setValue(contractCav.getImpCDCount());
		this.tfExpCDCount.setValue(contractCav.getExpCDCount());
		this.tfInternalSale.setValue(contractCav.getInternalSale());
		this.tfInternalSaleTax.setValue(contractCav.getInternalSaleTax());
		this.tfInternalSaleWarrant
				.setText(contractCav.getInternalSaleWarrant());
		this.tfInternalSaleTaxBill
				.setText(contractCav.getInternalSaleTaxBill());
		this.tfRemainMoney.setValue(contractCav.getRemainMoney());
		this.tfImportDeviceGross.setValue(contractCav.getImpDeviceGross());
		this.tfRemainEmsNo.setText(contractCav.getRemainEmsNo());
		this.tfCautionMoney.setValue(contractCav.getCautionMoney());
		if (contractCav.getIsSusceptivity() == null) {
			this.cbbIsSusceptivity.setSelectedIndex(-1);
		} else {
			this.cbbIsSusceptivity.setSelectedIndex(ItemProperty
					.getIndexByCode(contractCav.getIsSusceptivity().toString(),
							this.cbbIsSusceptivity));
		}
		if (contractCav.getIsLimit() == null) {
			this.cbbIsLimit.setSelectedIndex(-1);
		} else {
			this.cbbIsLimit.setSelectedIndex(ItemProperty.getIndexByCode(
					contractCav.getIsLimit().toString(), this.cbbIsLimit));
		}
		this.tfTotalPages.setText(contractCav.getTotalPages() == null ? ""
				: contractCav.getTotalPages().toString());
		this.tfCurrency.setText(contractCav.getCurr() == null ? ""
				: contractCav.getCurr().getName());
		this.tfdeclareState.setText(contractCav.getDeclareState() == null ? ""
				: DeclareState.getDeclareStateSpec(contractCav
						.getDeclareState()));
	}

	/**
	 * 填充数据
	 * 
	 * @param contractCav
	 */
	private void fillData(ContractCav contractCav) {
		if (contractCav == null) {
			return;
		}
		contractCav
				.setInternalSale(this.tfInternalSale.getValue() == null ? new Double(
						0) : Double.valueOf(this.tfInternalSale.getValue()
						.toString()));
		contractCav
				.setInternalSaleTax(this.tfInternalSaleTax.getValue() == null ? new Double(
						0) : Double.valueOf(this.tfInternalSaleTax.getValue()
						.toString()));
		contractCav
				.setInternalSaleWarrant(this.tfInternalSaleWarrant.getText());
		contractCav
				.setInternalSaleTaxBill(this.tfInternalSaleTaxBill.getText());
		contractCav
				.setTotalPages(this.tfTotalPages.getValue() == null ? new Integer(
						0) : Integer.valueOf(this.tfTotalPages.getValue()
						.toString()));
		contractCav.setImpAmount(this.tfImpTotalMoney.getValue() == null ? 0.0
				: Double.valueOf(this.tfImpTotalMoney.getValue().toString()));
		contractCav.setExpAmount(this.tfExpTotalMoney.getValue() == null ? 0.0
				: Double.valueOf(this.tfExpTotalMoney.getValue().toString()));
		contractCav
				.setProcessTotalPrice(this.tfProcessTotalPrice.getValue() == null ? 0.0
						: Double.valueOf(this.tfProcessTotalPrice.getValue()
								.toString()));
		contractCav.setRemainMoney(this.tfRemainMoney.getValue() == null ? 0.0
				: Double.valueOf(this.tfRemainMoney.getValue().toString()));
		contractCav.setRemainEmsNo(this.tfRemainEmsNo.getText() == null ? ""
				: this.tfRemainEmsNo.getText());
		contractCav
				.setCautionMoney(this.tfCautionMoney.getValue() == null ? 0.0
						: Double.valueOf(this.tfCautionMoney.getValue()
								.toString()));
		contractCav.setExpCDCount(this.tfExpCDCount.getValue() == null ? 0
				: Double.valueOf(this.tfExpCDCount.getValue().toString())
						.intValue());
		contractCav.setImpCDCount(this.tfImpCDCount.getValue() == null ? 0
				: Double.valueOf(this.tfImpCDCount.getValue().toString())
						.intValue());
		contractCav
				.setImpDeviceGross(this.tfImportDeviceGross.getValue() == null ? 0
						: Double.valueOf(this.tfImportDeviceGross.getValue()
								.toString()));
		// System.out.println(contractCav.getImpCDCount() + " ---IMPJJJJJ");
		// System.out.println(contractCav.getExpCDCount() + " ---EXPJJJJJ");
		if (this.cbbIsLimit.getSelectedIndex() > -1) {
			contractCav.setIsLimit(Boolean
					.valueOf(((ItemProperty) this.cbbIsLimit.getSelectedItem())
							.getCode()));
		} else {
			contractCav.setIsLimit(null);
		}
		if (this.cbbIsSusceptivity.getSelectedIndex() > -1) {
			contractCav.setIsSusceptivity(Boolean
					.valueOf(((ItemProperty) this.cbbIsSusceptivity
							.getSelectedItem()).getCode()));
		} else {
			contractCav.setIsSusceptivity(null);
		}
	}

	/**
	 * 设置控件状态
	 */
	private void setState() {
		String declareState = (contractCavCustoms == null ? ""
				: ((contractCavCustoms.getDeclareState() == null || ""
						.equals(contractCavCustoms.getDeclareState().trim())) ? DeclareState.APPLY_POR
						: contractCavCustoms.getDeclareState()));
		boolean isCustoms = rbCustoms.isSelected();
		this.rbSelf.setEnabled(dataState == DataState.BROWSE);
		this.rbCustoms.setEnabled(dataState == DataState.BROWSE);
		this.btnPrint.setEnabled(dataState == DataState.BROWSE);
		this.btnClose.setEnabled(dataState == DataState.BROWSE);
		this.btnEdit.setEnabled(dataState == DataState.BROWSE
				&& DeclareState.APPLY_POR.equals(declareState));
		this.btnSave.setEnabled(dataState != DataState.BROWSE
				&& DeclareState.APPLY_POR.equals(declareState));
		this.btnUndo.setEnabled(dataState != DataState.BROWSE
				&& DeclareState.APPLY_POR.equals(declareState));
		this.btnCheck.setEnabled(dataState == DataState.BROWSE);
		this.tpn.setEnabled(dataState == DataState.BROWSE);
		this.btnReCav.setEnabled(dataState == DataState.BROWSE
				&& DeclareState.APPLY_POR.equals(declareState));
		this.cb.setEnabled(dataState == DataState.BROWSE
				&& DeclareState.APPLY_POR.equals(declareState));
		this.tfInternalSale.setEditable(dataState != DataState.BROWSE);
		this.tfImpTotalMoney.setEditable(dataState != DataState.BROWSE);
		this.tfExpTotalMoney.setEditable(dataState != DataState.BROWSE);
		this.tfProcessTotalPrice.setEditable(dataState != DataState.BROWSE);
		this.tfRemainMoney.setEditable(dataState != DataState.BROWSE);
		this.tfRemainEmsNo.setEditable(dataState != DataState.BROWSE);
		this.tfCautionMoney.setEditable(dataState != DataState.BROWSE);
		getTfExpCDCount().setEditable(dataState != DataState.BROWSE);// 报关单份数可以修改
		getTfImpCDCount().setEditable(dataState != DataState.BROWSE);// 报关单份数可以修改
		this.tfInternalSaleTax.setEditable(dataState != DataState.BROWSE);
		this.tfInternalSaleWarrant.setEditable(dataState != DataState.BROWSE);
		this.tfImportDeviceGross.setEditable(dataState != DataState.BROWSE);
		this.tfInternalSaleTaxBill.setEditable(dataState != DataState.BROWSE);
		this.tfTotalPages.setEditable(dataState != DataState.BROWSE);
		this.cbbIsLimit.setEnabled(dataState != DataState.BROWSE);
		// this.cbbIsLimit.setBackground(java.awt.Color.white);
		this.cbbIsLimit.setForeground(java.awt.Color.BLACK);
		this.cbbIsSusceptivity.setEnabled(dataState != DataState.BROWSE);
		// this.cbbIsSusceptivity.setBackground(java.awt.Color.white);
		this.cbbIsSusceptivity.setForeground(java.awt.Color.BLACK);
		this.btnDeclare.setEnabled(isCustoms
				&& DeclareState.APPLY_POR.equals(declareState));
		this.btnProccess.setEnabled(isCustoms
				&& DeclareState.WAIT_EAA.equals(declareState));
		this.btnEditExg.setEnabled(DeclareState.APPLY_POR.equals(declareState));
		this.btnEditImg.setEnabled(DeclareState.APPLY_POR.equals(declareState));
		this.btnEditUnitWaste.setEnabled(DeclareState.APPLY_POR
				.equals(declareState));
		this.btnInteralBuy.setEnabled(DeclareState.APPLY_POR
				.equals(declareState));
		this.btnDeleteImg.setEnabled(DeclareState.APPLY_POR
				.equals(declareState));
		this.btnLeftoverMaterial.setEnabled(DeclareState.APPLY_POR
				.equals(declareState));
		this.cbImg.setEnabled(DeclareState.APPLY_POR.equals(declareState));
		this.cbImg.setEnabled(DeclareState.APPLY_POR.equals(declareState));
		this.rbTotal.setEnabled(DeclareState.APPLY_POR.equals(declareState));
		this.rbWaste.setEnabled(DeclareState.APPLY_POR.equals(declareState));
		this.btnRoundAmount.setEnabled(DeclareState.APPLY_POR
				.equals(declareState));
		this.cbBom1.setEnabled(DeclareState.APPLY_POR.equals(declareState));
		this.cbBom2.setEnabled(DeclareState.APPLY_POR.equals(declareState));
		this.getMiUndoDeclare().setEnabled(
				DeclareState.WAIT_EAA.equals(declareState));
	}

	/**
	 * 改变海关核销表申报
	 * 
	 * @param isCustoms
	 */
	private void switchSelfCustoms(boolean isCustoms) {
		this.lbCaption31.setVisible(isCustoms);
		this.tfdeclareState.setVisible(isCustoms);
		if (isCustoms) {
			this.jPanel1.setVisible(false);
		} else {
			this.jPanel1.setVisible(true);
		}
		this.btnEdit.setVisible(isCustoms);
		this.btnSave.setVisible(isCustoms);
		this.btnUndo.setVisible(isCustoms);
		this.btnCheck.setVisible(isCustoms);
		this.btnInteralBuy.setVisible(isCustoms);
		this.btnDeleteImg.setVisible(isCustoms);
		this.btnLeftoverMaterial.setVisible(isCustoms);
		this.cbImg.setVisible(isCustoms);
		this.rbTotal.setVisible(isCustoms);
		this.rbWaste.setVisible(isCustoms);
		this.btnRoundAmount.setVisible(isCustoms);
		this.cbBom1.setVisible(isCustoms);
		this.cbBom2.setVisible(isCustoms);
		this.cbBom3.setVisible(isCustoms);
		this.btnEditExg.setVisible(isCustoms);
		this.btnEditImg.setVisible(isCustoms);
		this.btnEditUnitWaste.setVisible(isCustoms);
		this.cb.setVisible(isCustoms);
		this.btnChangeImgModifyMark.setVisible(isCustoms);
		// this.btnCal.setVisible(isCustoms);
		this.tpn.setTitleAt(0, isCustoms ? "海关核销表表头" : "自用核销表表头");
		this.tpn.setTitleAt(1, isCustoms ? "海关核销报关单" : "自用核销报关单");
		this.tpn.setTitleAt(2, isCustoms ? "海关核销成品表" : "自用核销成品表");
		this.tpn.setTitleAt(3, isCustoms ? "海关核销料件表" : "自用核销料件表");
		this.tpn.setTitleAt(4, isCustoms ? "海关核销单耗表" : "自用核销单耗表");
		this.btnReCav.setText(isCustoms ? "重新获取自用核销表" : "重新计算自用核销表");
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
			btnEdit.setBounds(new Rectangle(54, 395, 59, 23));
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
	 * 初始化UI控件
	 */
	private void InitUIComponents() {
		this.cbbIsSusceptivity.removeAllItems();
		this.cbbIsSusceptivity.addItem(new ItemProperty("true", "是"));
		this.cbbIsSusceptivity.addItem(new ItemProperty("false", "否"));
		this.cbbIsLimit.removeAllItems();
		this.cbbIsLimit.addItem(new ItemProperty("true", "是"));
		this.cbbIsLimit.addItem(new ItemProperty("false", "否"));
		// 初始化打印选项
		// this.cbbPrint.removeAllItems();
		// this.cbbPrint.addItem("非套打核销表头");
		// this.cbbPrint.addItem(" 套打核销表头");
		// this.cbbPrint.addItem("非套打核销料件表");
		// this.cbbPrint.addItem(" 套打核销料件表");
		// this.cbbPrint.addItem("非套打核销成品表");
		// this.cbbPrint.addItem(" 套打核销成品表");
		// this.cbbPrint.addItem("非套打核销单耗表");
		// this.cbbPrint.addItem(" 套打核销单耗表");
		// this.cbbPrint.addItem("非套打核销表头(新)");
		// this.cbbPrint.addItem("非套打核销单耗表(新)");
		// this.cbbPrint.setUI(new CustomBaseComboBoxUI(300));
	}

	/**
	 * 显示海关申报
	 * 
	 * @param isCustoms
	 */
	private void showCustomsDeclaration(boolean isCustoms) {
		List list = new ArrayList();
		if (isCustoms) {
			if (contractCavCustoms != null) {
				list = contractCavAction.findCustomsDeclarationCav(new Request(
						CommonVars.getCurrUser()), contractCavCustoms);
			}
		} else {
			if (contractCavSelf != null) {
				list = contractCavAction.findCustomsDeclarationCav(new Request(
						CommonVars.getCurrUser()), contractCavSelf);
			}
		}
		/**
		 * 初始化表
		 */
		tableModelCD = new JTableListModel(this.tbCD, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("修改标志", "modifyMark", 60));
						list.add(addColumn("报关单号", "customsDeclarationCode",
								150));
						list.add(addColumn("申报地海关", "declarationCustoms.name",
								100));
						list.add(addColumn("报关单类型", "impExpFlag", 80));
						list.add(addColumn("进出口标志", "customsImpExpType", 80));
						list.add(addColumn("申报日期", "declarationDate", 80));
						list.add(addColumn("进出口日期", "impExpDate", 80));
						list.add(addColumn("核扣方式", "deduc.name", 100));
						return list;
					}
				});
		// //设置小数位
		// Integer decimalLength = 2;
		// if (parameterSet != null
		// && parameterSet.getReportDecimalLength() != null)
		// decimalLength = parameterSet.getReportDecimalLength();
		// tableModelCD.setAllColumnsFractionCount(decimalLength);

		tbCD.getColumnModel().getColumn(1)
				.setCellRenderer(new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue(value));
						return this;
					}

					private String castValue(Object value) {
						return ModifyMarkState.getModifyMarkSpec(value
								.toString());
					}
				});
		tbCD.getColumnModel().getColumn(4)
				.setCellRenderer(new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						String str = "";
						if (value != null) {
							switch (Integer.parseInt(value.toString())) {
							case ImpExpFlag.IMPORT:
								str = "进口报关单";
								break;
							case ImpExpFlag.EXPORT:
								str = "出口报关单";
								break;
							case ImpExpFlag.SPECIAL:
								str = "特殊报关单";
								break;
							}
						}
						this.setText(str);
						return this;
					}
				});
	}

	/**
	 * 显示海关申报信息
	 * 
	 * @param isCustoms
	 */
	private void showExgData(boolean isCustoms) {
		List list = new ArrayList();
		if (isCustoms) {
			if (contractCavCustoms != null) {
				list = contractCavAction.findContractExgCav(new Request(
						CommonVars.getCurrUser()), contractCavCustoms, this.cb1
						.isSelected());
			}
		} else {
			if (contractCavSelf != null) {
				list = contractCavAction.findContractExgCav(new Request(
						CommonVars.getCurrUser()), contractCavSelf, this.cb1
						.isSelected());
			}
		}
		/**
		 * 初始化表格
		 */
		tableModelExg = new JTableListModel(this.tbExg, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("修改标志", "modifyMark", 60));
						list.add(addColumn("备案序号", "seqNum", 60, Integer.class));
						list.add(addColumn("商品编码", "complex.code", 100));
						list.add(addColumn("商品名称", "name", 100));
						list.add(addColumn("规格型号", "spec", 100));
						list.add(addColumn("计量单位", "unit.name", 60));
						list.add(addColumn("出口总数", "expTotal", 100));
						list.add(addColumn("直接出口数量", "directExport", 100));
						list.add(addColumn("结转出口数量", "transferExpAmount", 100));
						list.add(addColumn("退厂返工", "backFactoryRework", 100));
						list.add(addColumn("返工复出", "reworkExport", 100));
						list.add(addColumn("库存数量", "stockAmount", 100));
						return list;
					}
				});
		// //设置小数位
		// Integer decimalLength = 2;
		// if (parameterSet != null
		// && parameterSet.getReportDecimalLength() != null)
		// decimalLength = parameterSet.getReportDecimalLength();
		// tableModelExg.setAllColumnsFractionCount(decimalLength);

		tbExg.getColumnModel().getColumn(1)
				.setCellRenderer(new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue(value));
						return this;
					}

					private String castValue(Object value) {
						return ModifyMarkState.getModifyMarkSpec(value
								.toString());
					}
				});
	}

	/**
	 * 显示图片信息
	 * 
	 * @param isCustoms
	 */
	private void showImgData(boolean isCustoms) {
		List list = new ArrayList();
		if (isCustoms) {
			if (contractCavCustoms != null) {
				list = contractCavAction.findContractImgCav(new Request(
						CommonVars.getCurrUser()), contractCavCustoms, this.cb1
						.isSelected());
				System.out.println("list size = " + list.size());
			}
		} else {
			if (contractCavSelf != null) {
				list = contractCavAction.findContractImgCav(new Request(
						CommonVars.getCurrUser()), contractCavSelf, this.cb1
						.isSelected());
			}
		}
		tableModelImg = new JTableListModel(this.tbImg, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("修改标志", "modifyMark", 60));
						list.add(addColumn("备案序号", "seqNum", 60, Integer.class));
						list.add(addColumn("商品编码", "complex.code", 100));
						list.add(addColumn("商品名称", "name", 150));
						list.add(addColumn("规格型号", "spec", 100));
						list.add(addColumn("计量单位", "unit.name", 60));
						list.add(addColumn("进口总数", "impTotal", 100));
						list.add(addColumn("料件直接进口", "directImport", 100));
						list.add(addColumn("料件转厂", "transferFactoryImport", 100));
						list.add(addColumn("余料结转进口", "remainImport", 100));
						list.add(addColumn("余料结转出口", "remainExport", 100));
						list.add(addColumn("成品耗用", "productWaste", 100));
						list.add(addColumn("内销数量", "internalAmount", 100));
						list.add(addColumn("退运出口", "backExport", 100));
						list.add(addColumn("边角料", "leftoverMaterial", 100));
						list.add(addColumn("余料", "remainMaterial", 100));
						list.add(addColumn("库存数量", "stockAmount", 100));
						list.add(addColumn("内购数量(番禺)", "domesticPurchase", 100));
						list.add(addColumn("净耗重量KG(番禺)", "netLossWeight", 100));
						list.add(addColumn("说明原因(番禺)", "explain", 100));
						return list;
					}
				});
		// // 显示小数位,默认2位
		// Integer decimalLength = 2;
		// if (parameterSet != null
		// && parameterSet.getReportDecimalLength() != null)
		// decimalLength = parameterSet.getReportDecimalLength();
		// tableModelImg.setAllColumnsFractionCount(decimalLength);

		tbImg.getColumnModel().getColumn(1)
				.setCellRenderer(new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue(value));
						return this;
					}

					private String castValue(Object value) {
						return ModifyMarkState.getModifyMarkSpec(value
								.toString());
					}
				});
	}

	/**
	 * 显示BOM表数据
	 * 
	 * @param isCustoms
	 */
	private void showBomData(boolean isCustoms) {
		List list = new ArrayList();
		if (isCustoms) {
			if (contractCavCustoms != null) {
				list = contractCavAction.findContractBomCav(new Request(
						CommonVars.getCurrUser()), contractCavCustoms, this.cb2
						.isSelected());
			}
		} else {
			if (contractCavSelf != null) {
				list = contractCavAction.findContractBomCav(new Request(
						CommonVars.getCurrUser()), contractCavSelf, this.cb2
						.isSelected());
			}
		}
		tableModelBom = new AttributiveCellTableModel(
				(MultiSpanCellTable) this.tbBom, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("修改标志", "modifyMark", 60));
						list.add(addColumn("成品序号", "seqProductNum", 60,
								Integer.class));
						list.add(addColumn("成品名称", "productName", 100));
						list.add(addColumn("成品规格", "productSpec", 100));
						list.add(addColumn("料件序号", "seqMaterialNum", 60,
								Integer.class));
						list.add(addColumn("料件名称", "materialName", 100));
						list.add(addColumn("料件规格", "materialSpec", 100));
						list.add(addColumn("单耗/净耗", "unitWaste", 100));
						list.add(addColumn("损耗量", "wasteAmount", 100));
						list.add(addColumn("总用量", "totalAmount", 100));
						list.add(addColumn("非保税料件比例%", "nonMnlRatio", 100));
						return list;
					}
				});
		// //设置小数位
		// Integer decimalLength = 2;
		// if (parameterSet != null
		// && parameterSet.getReportDecimalLength() != null)
		// decimalLength = parameterSet.getReportDecimalLength();
		// tableModelBom.setAllColumnsFractionCount(decimalLength);

		List<JTableListColumn> cm = tableModelBom.getColumns();
		cm.get(9).setFractionCount(5);
		tbBom.getColumnModel().getColumn(1)
				.setCellRenderer(new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						// List list = contractAction.findEquipModeState(new
						// Request(
						// CommonVars.getCurrUser()),emsNo);
						// if(list.size()>0&&list.get(0) != null){
						// String strEquipModeState =(String)list.get(0);
						//
						// if(strEquipModeState.equals(EquipMode.PUT_ON_RECORD)){
						// value = ModifyMarkState.MODIFIED;
						// }
						// }
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue(value));

						return this;
					}

					private String castValue(Object value) {
						return ModifyMarkState.getModifyMarkSpec(value
								.toString());
					}
				});
		this.tbBom.getColumnModel().getColumn(8)
				.setCellRenderer(new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						Color fg = null;
						Color bg = null;
						JTable.DropLocation dropLocation = table
								.getDropLocation();
						if (dropLocation != null && !dropLocation.isInsertRow()
								&& !dropLocation.isInsertColumn()
								&& dropLocation.getRow() == row
								&& dropLocation.getColumn() == column) {
							fg = UIManager.getColor("Table.dropCellForeground");
							bg = UIManager.getColor("Table.dropCellBackground");
							isSelected = true;
						}
						if (isSelected) {
							super.setForeground(fg == null ? table
									.getSelectionForeground() : fg);
							super.setBackground(bg == null ? table
									.getSelectionBackground() : bg);
						} else {
							if (value == null
									|| Double.parseDouble(value.toString()) == 0) {
								super.setBackground(Color.BLUE);
							} else {
								super.setBackground(table.getBackground());
							}
							super.setForeground(table.getForeground());
						}
						setFont(table.getFont());
						if (hasFocus) {
							Border border = null;
							if (isSelected) {
								border = UIManager
										.getBorder("Table.focusSelectedCellHighlightBorder");
							}
							if (border == null) {
								border = UIManager
										.getBorder("Table.focusCellHighlightBorder");
							}
							setBorder(border);
							if (!isSelected
									&& table.isCellEditable(row, column)) {
								Color col;
								col = UIManager
										.getColor("Table.focusCellForeground");
								if (col != null) {
									super.setForeground(col);
								}
								col = UIManager
										.getColor("Table.focusCellBackground");
								if (col != null) {
									super.setBackground(col);
								}
							}
						} else {
							setBorder(new EmptyBorder(1, 1, 1, 1));
						}
						setValue(value);
						if (value == null
								|| Double.parseDouble(value.toString()) == 0) {
							super.setBackground(Color.BLUE);
						}
						return this;
					}
				});

		refreshTable();
	}

	/**
	 * 刷新表格
	 */
	private void refreshTable() {
		// if (cbCombineRows.isSelected()) {
		((MultiSpanCellTable) tbBom).combineRows(new int[] { 2, 5 }, new int[] {
				2, 3, 4 });
		// ((MultiSpanCellTable) tblBom).combineRows(7, new int[] { 7, 8, 9,
		// 10, 11 });
		// } else {
		// ((MultiSpanCellTable) jTable).splitRows(new int[] { 12, 7 });
		// }
	}

	/**
	 * This method initializes btnEditExg
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton6() {
		if (btnEditExg == null) {
			btnEditExg = new JButton();
			btnEditExg.setText("修改");
			btnEditExg.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelExg.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgContractCav.this,
								"请选择要修改的成品", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					DgContractCavExg dg = new DgContractCavExg();
					dg.setTableModel(tableModelExg);
					dg.setDataState(DataState.EDIT);
					dg.setVisible(true);
				}
			});
		}
		return btnEditExg;
	}

	/**
	 * This method initializes btnEditImg
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEditImg() {
		if (btnEditImg == null) {
			btnEditImg = new JButton();
			btnEditImg.setText("修改");
			btnEditImg.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgContractCavImg dg = new DgContractCavImg();
					dg.setTableModel(tableModelImg);
					dg.setDataState(DataState.EDIT);
					dg.setModifyProductUsedAmountWriteBack(!cbImg.isSelected());
					dg.setVisible(true);
				}
			});
		}
		return btnEditImg;
	}

	/**
	 * This method initializes btnEditUnitWaste
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEditUnitWaste() {
		if (btnEditUnitWaste == null) {
			btnEditUnitWaste = new JButton();
			btnEditUnitWaste.setText("修改");
			btnEditUnitWaste
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (tableModelBom.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(
										DgContractCav.this, "请选择要修改的单耗", "提示",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							DgContractCavBom dg = new DgContractCavBom();
							dg.setTableModel(tableModelBom);
							dg.setDataState(DataState.EDIT);
							dg.setModifyUnitWasteNotWriteBack(cbBom1
									.isSelected());
							dg.setModifyTotalAmountNotWriteBack(cbBom2
									.isSelected());
							dg.setModifyWasteAmountNotWriteBack(cbBom3
									.isSelected());
							dg.setModifyWasteAmount(rbWaste.isSelected(),
									cbBom4.isSelected());

							dg.setVisible(true);
						}
					});
		}
		return btnEditUnitWaste;
	}

	/**
	 * This method initializes buttonGroup
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup() {
		if (buttonGroup == null) {
			buttonGroup = new ButtonGroup();
			buttonGroup.add(this.rbTotal);
			buttonGroup.add(this.rbWaste);
		}
		return buttonGroup;
	}

	/**
	 * This method initializes pnCustomsDeclaration
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnCustomsDeclaration() {
		if (pnCustomsDeclaration == null) {
			pnCustomsDeclaration = new JPanel();
			pnCustomsDeclaration.setLayout(new BorderLayout());
			pnCustomsDeclaration.setSize(new java.awt.Dimension(8, 10));
			pnCustomsDeclaration.add(getSpn3(), java.awt.BorderLayout.CENTER);
		}
		return pnCustomsDeclaration;
	}

	/**
	 * This method initializes spn3
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getSpn3() {
		if (spn3 == null) {
			spn3 = new JScrollPane();
			spn3.setViewportView(getTbCD());
		}
		return spn3;
	}

	/**
	 * This method initializes tbCD
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbCD() {
		if (tbCD == null) {
			tbCD = new JTable();
			tbCD.setRowHeight(25);
		}
		return tbCD;
	}

	/**
	 * This method initializes btnDeclare
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDeclare() {
		if (btnDeclare == null) {
			btnDeclare = new JButton();
			btnDeclare.setText("海关申报");
			btnDeclare.setPreferredSize(new Dimension(60, 30));
			btnDeclare.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					boolean isAppBom = true;
					if (JOptionPane.showConfirmDialog(DgContractCav.this,
							"您是否需要申报单耗，点击 ”是“ 将会把单耗申报给海关，点击 ”否“ 将不会把单耗申报给海关。",
							"提示", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
						isAppBom = false;
					}
					if (JOptionPane.showConfirmDialog(DgContractCav.this,
							"你真的要申报此核销表吗？", "提示", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
						return;
					}
					// try {
					// String fileName = contractCavAction
					// .applyDzscContractCav(new Request(CommonVars
					// .getCurrUser()), dzscContractCavCustoms);
					// JOptionPane.showMessageDialog(DgDzscContractCav.this,
					// "申报成功，文件" + fileName, "提示",
					// JOptionPane.INFORMATION_MESSAGE);
					// dzscContractCavCustoms = contractCavAction
					// .findDzscContractCavById(new Request(CommonVars
					// .getCurrUser()), dzscContractCavCustoms
					// .getId());
					// setState();
					// } catch (Exception ex) {
					// JOptionPane.showMessageDialog(DgDzscContractCav.this,
					// "申报失败 " + ex.getMessage(), "提示",
					// JOptionPane.INFORMATION_MESSAGE);
					// }
					new ApplyThread(isAppBom).start();
				}
			});
		}
		return btnDeclare;
	}

	/**
	 * 多线程
	 * 
	 * @author ower
	 * 
	 */
	class ApplyThread extends Thread {
		// 是否申报单耗
		boolean isAppBom = false;

		public ApplyThread(boolean isAppBom) {

			this.isAppBom = isAppBom;
		}

		public void run() {
			try {
				String taskId = CommonStepProgress.getExeTaskId();
				CommonStepProgress.showStepProgressDialog(taskId);
				CommonStepProgress.setStepMessage("系统正获取数据，请稍后...");
				Request request = new Request(CommonVars.getCurrUser());
				request.setTaskId(taskId);
				try {
					DeclareFileInfo fileInfo = contractCavAction
							.applyContractCav(
									new Request(CommonVars.getCurrUser()),
									contractCavCustoms, isAppBom);
					CommonStepProgress.closeStepProgressDialog();
					JOptionPane.showMessageDialog(DgContractCav.this,
							fileInfo.getFileInfoSpec(), "提示",
							JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception ex) {
					CommonStepProgress.closeStepProgressDialog();
					JOptionPane.showMessageDialog(DgContractCav.this, "系统申报失败 "
							+ ex.getMessage(), "确认",
							JOptionPane.INFORMATION_MESSAGE);
				}
				contractCavCustoms = contractCavAction.findContractCavById(
						new Request(CommonVars.getCurrUser()),
						contractCavCustoms.getId());
				setState();
			} catch (Exception ex) {
				System.out.println(ex.getStackTrace() + "\n-->"
						+ ex.getMessage());
			} finally {
				CommonStepProgress.closeStepProgressDialog();
			}
		}
	}

	/**
	 * This method initializes btnProccess
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnProccess() {
		if (btnProccess == null) {
			btnProccess = new JButton();
			btnProccess.setText("处理回执");
			btnProccess.setPreferredSize(new Dimension(60, 30));
			btnProccess.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// if (JOptionPane.showConfirmDialog(DgDzscContractCav.this,
					// "你真的要申报此核销表吗？", "提示", JOptionPane.YES_NO_OPTION) !=
					// JOptionPane.YES_OPTION) {
					// return;
					// }
					List lsReturnFile = BcsMessageHelper.getInstance()
							.showBcsReceiptFile(
									DzscBusinessType.CANCEL_AFTER_VERIFICA,
									contractCavCustoms.getCopEmsNo());
					if (lsReturnFile.size() <= 0) {
						return;
					}
					try {
						String result = contractCavAction.processContractCav(
								new Request(CommonVars.getCurrUser()),
								contractCavCustoms, lsReturnFile);
						JOptionPane.showMessageDialog(DgContractCav.this,
								"处理回执成功\n" + result, "提示",
								JOptionPane.INFORMATION_MESSAGE);
						contractCavCustoms = contractCavAction
								.findContractCavById(
										new Request(CommonVars.getCurrUser()),
										contractCavCustoms.getId());
						setState();
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(DgContractCav.this,
								"处理回执失败 " + ex.getMessage(), "提示",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});
		}
		return btnProccess;
	}

	/**
	 * This method initializes jSeparator
	 * 
	 * @return javax.swing.JSeparator
	 */
	private JSeparator getJSeparator() {
		if (jSeparator == null) {
			jSeparator = new JSeparator();
			jSeparator.setBounds(new Rectangle(44, 354, 615, 8));
		}
		return jSeparator;
	}

	/**
	 * This method initializes cb
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCb() {
		if (cb == null) {
			cb = new JCheckBox();
			cb.setBounds(new Rectangle(515, 370, 151, 16));
			cb.setText("仅获取核销表头");
		}
		return cb;
	}

	/**
	 * This method initializes cb1
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCb1() {
		if (cb1 == null) {
			cb1 = new JCheckBox();
			cb1.setBounds(new Rectangle(59, 370, 183, 16));
			cb1.setText("不显示数量为0的成品和料件");
		}
		return cb1;
	}

	/**
	 * This method initializes cb2
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCb2() {
		if (cb2 == null) {
			cb2 = new JCheckBox();
			cb2.setBounds(new Rectangle(266, 370, 212, 16));
			cb2.setText("不显示成品数量为0的单耗资料");
		}
		return cb2;
	}

	/**
	 * This method initializes btnPrintCount
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrintCount() {
		if (btnPrintCount == null) {
			btnPrintCount = new JButton();
			btnPrintCount.setBounds(new Rectangle(562, 290, 85, 24));
			btnPrintCount.setText("打印");
			btnPrintCount
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							ContractCav contractCav = null;
							if (rbCustoms.isSelected()) {
								contractCav = contractCavCustoms;
							} else if (rbSelf.isSelected()) {
								contractCav = contractCavSelf;
							}
							if (contractCav == null) {
								return;
							}
							InputStream reportStream = null;
							List<Object> list = new ArrayList<Object>();
							list.add("");
							Map<String, Object> parameters = new HashMap<String, Object>();
							CustomReportDataSource ds = new CustomReportDataSource(
									list);
							Company company = (Company) contractCav
									.getCompany();
							reportStream = DgContractCav.class
									.getResourceAsStream("report/ContractCavReport.jasper");
							parameters.put("impMT", getTfImpTotalValue()
									.getText());
							parameters.put("expPT", getTfExpTotalValue()
									.getText());
							parameters.put("impG", getTfImpTotalGrossWeight()
									.getText());
							parameters.put("expG", getTfExpTotalGrossWeight()
									.getText());
							parameters.put("impN", getTfImpNetWeight()
									.getText());
							parameters.put("expN", getTfExpNetWeight()
									.getText());
							parameters.put("companyName", company.getName());
							try {
								JasperPrint jasperPrint = JasperFillManager
										.fillReport(reportStream, parameters,
												ds);
								DgReportViewer viewer = new DgReportViewer(
										jasperPrint);
								viewer.setVisible(true);
							} catch (JRException e1) {
								e1.printStackTrace();
							}
						}
					});
		}
		return btnPrintCount;
	}

	/**
	 * This method initializes cb3
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCb3() {
		if (cb3 == null) {
			cb3 = new JCheckBox();
			cb3.setSize(new Dimension(153, 27));
			cb3.setText("当单损耗为零时打印“0”,否则打印空");
		}
		return cb3;
	}

	/**
	 * This method initializes cb4
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIsPrintZeroAsNull() {
		if (cbIsPrintZeroAsNull == null) {
			cbIsPrintZeroAsNull = new JCheckBox();
			cbIsPrintZeroAsNull.setSize(new Dimension(195, 35));
			cbIsPrintZeroAsNull.setText("数量为'0'时，打印空代替'0'");
		}
		return cbIsPrintZeroAsNull;
	}

	/**
	 * This method initializes cb4
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCb4() {
		if (cb4 == null) {
			cb4 = new JCheckBox();
			cb4.setSize(new Dimension(195, 35));
			cb4.setText("单耗为零时不打印");
		}
		return cb4;
	}

	/**
	 * This method initializes cb5
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCb5() {
		if (cb5 == null) {
			cb5 = new JCheckBox();
			cb5.setText("番禺海关");
			cb5.setPreferredSize(new Dimension(77, 30));
			cb5.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (getCb5().isSelected()) {
						// getCbbPrint().removeAllItems();
						// getCbbPrint().addItem("非套打核销表头");
						// getCbbPrint().addItem("非套打核销清单（料件）");
						// getCbbPrint().addItem("非套打核销清单（成品）");
						// // getCbbPrint().addItem("非套打对外加工装配申请表");
						// getCbbPrint().addItem("国内购料清单");
						// getCbbPrint().setUI(new CustomBaseComboBoxUI(300));
						// getMiShopList().setVisible(true);
						getMiProcessingTradeVerificationBillImportImg()
								.setVisible(true);
						getMiProcessingTradeVerificationBillExprotExg()
								.setVisible(true);
						getMiForeignInvestment().setVisible(true);
						getMiNonCoverPrintAppliedContractConsumptionCased()
								.setVisible(true);
						// getMiNonCoverPrintAppliedContractConsumptionCased()
						// .setVisible(false);
						getMiNonCoverPrintAppliedContractConsumptionCased()
								.setVisible(true);
						getMiContractCavDomesticPurchaseBill().setVisible(true);
						getMiBomFilingRealityCompare().setVisible(true);
						// getMiNonCoverPrintAppliedContractConsumptionCased()
						// .setVisible(false);
						getMiCoverPrintAppliedUnitConsumptionNew().setVisible(
								false);
						// getMiNonCoverPrintAppliedNew().setVisible(false);
						getMiNonCoverPrintAppliedNewSmall().setVisible(false);
						getMiNonCoverPrintAppliedNewBig().setVisible(false);
						getMiCoverPrintAppliedUnitConsumption().setVisible(
								false);
						getMiNonCoverPrintAppliedUnitConsumption().setVisible(
								false);
						getMiCoverPrintAppliedExg().setVisible(false);
						getMiNonCoverPrintAppliedExg().setVisible(false);
						getMiCoverPrintAppliedImg().setVisible(false);
						getMiNonCoverPrintAppliedImg().setVisible(false);
						getMiCoverPrintApplied().setVisible(false);
						getMiNonCoverPrintApplied().setVisible(false);
						getBtnPrintMiConverContract().setVisible(true);
					} else {
						// getCbbPrint().removeAllItems();
						// getCbbPrint().addItem("非套打核销表头");
						// getCbbPrint().addItem(" 套打核销表头");
						// getCbbPrint().addItem("非套打核销料件表");
						// getCbbPrint().addItem(" 套打核销料件表");
						// getCbbPrint().addItem("非套打核销成品表");
						// getCbbPrint().addItem(" 套打核销成品表");
						// getCbbPrint().addItem("非套打核销单耗表");
						// getCbbPrint().addItem(" 套打核销单耗表");
						// getCbbPrint().addItem("非套打核销表头(新)");
						// getCbbPrint().addItem("非套打核销单耗表(新)");
						// getCbbPrint().setUI(new CustomBaseComboBoxUI(300));
						// getMiShopList().setVisible(false);
						getMiProcessingTradeVerificationBillImportImg()
								.setVisible(false);
						getMiProcessingTradeVerificationBillExprotExg()
								.setVisible(false);
						getMiForeignInvestment().setVisible(false);
						getMiNonCoverPrintAppliedContractConsumptionCased()
								.setVisible(false);
						// getMiNonCoverPrintAppliedContractConsumptionCased()
						// .setVisible(true);
						getMiNonCoverPrintAppliedContractConsumptionCased()
								.setVisible(false);
						getMiContractCavDomesticPurchaseBill()
								.setVisible(false);
						getMiContractCavDomesticPurchaseBill()
								.setVisible(false);
						getMiBomFilingRealityCompare().setVisible(false);
						// getMiNonCoverPrintAppliedContractConsumptionCased()
						// .setVisible(true);
						getMiCoverPrintAppliedUnitConsumptionNew().setVisible(
								true);
						// getMiNonCoverPrintAppliedNew().setVisible(true);
						getMiNonCoverPrintAppliedNewSmall().setVisible(true);
						getMiNonCoverPrintAppliedNewBig().setVisible(true);
						getBtnPrintMiConverContract().setVisible(false);
						getMiCoverPrintAppliedUnitConsumption()
								.setVisible(true);
						getMiNonCoverPrintAppliedUnitConsumption().setVisible(
								true);
						getMiCoverPrintAppliedExg().setVisible(true);
						getMiNonCoverPrintAppliedExg().setVisible(true);
						getMiCoverPrintAppliedImg().setVisible(true);
						getMiNonCoverPrintAppliedImg().setVisible(true);
						getMiCoverPrintApplied().setVisible(true);
						getMiNonCoverPrintApplied().setVisible(true);
					}
				}
			});
		}
		return cb5;
	}

	/**
	 * This method initializes btnChangeDeclareState
	 * 
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnChangeDeclareState() {
		if (btnChangeDeclareState == null) {
			btnChangeDeclareState = new JButton();
			btnChangeDeclareState.setText("改变申报状态");
			btnChangeDeclareState.setPreferredSize(new Dimension(84, 30));
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
			miUndoDeclare.setText("\u53d6\u6d88\u7533\u62a5");
			miUndoDeclare
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							String declareState = "";
							if (contractCavCustoms.getEmsNo() != null
									&& !"".equals(contractCavCustoms.getEmsNo()
											.trim())) {
								declareState = DeclareState.APPLY_POR;
								;
							} else {
								// declareState = DeclareState.APPLY_POR;
							}
							contractCavCustoms = contractCavAction
									.changeContractCavDeclareState(new Request(
											CommonVars.getCurrUser()),
											contractCavCustoms, declareState);
							setState();
							showData(contractCavCustoms);
						}
					});
		}
		return miUndoDeclare;
	}

	/**
	 * This method initializes tfdeclareState
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfdeclareState() {
		if (tfdeclareState == null) {
			tfdeclareState = new JTextField();
			tfdeclareState.setBounds(new Rectangle(548, 327, 94, 22));
			tfdeclareState.setEditable(false);
		}
		return tfdeclareState;
	}

	/**
	 * This method initializes jPopupMenuPrintPYReport
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getJPopupMenuPrintReports() {
		if (jPopupMenuPrintReports == null) {
			jPopupMenuPrintReports = new JPopupMenu();
			jPopupMenuPrintReports.add(getMiNonCoverPrintApplied());
			jPopupMenuPrintReports.add(getMiCoverPrintApplied());
			jPopupMenuPrintReports.add(getMiNonCoverPrintAppliedImg());
			jPopupMenuPrintReports.add(getMiCoverPrintAppliedImg());
			jPopupMenuPrintReports.add(getMiNonCoverPrintAppliedExg());
			jPopupMenuPrintReports.add(getMiCoverPrintAppliedExg());
			jPopupMenuPrintReports
					.add(getMiNonCoverPrintAppliedUnitConsumption());
			jPopupMenuPrintReports.add(getMiCoverPrintAppliedUnitConsumption());
			jPopupMenuPrintReports.add(getMiNonCoverPrintAppliedNewSmall());
			jPopupMenuPrintReports.add(getMiNonCoverPrintAppliedNewBig());
			// jPopupMenuPrintReports.add(getMiShopList());
			jPopupMenuPrintReports.add(getBtnPrintMiConverContract());
			jPopupMenuPrintReports
					.add(getMiCoverPrintAppliedUnitConsumptionNew());
			jPopupMenuPrintReports.add(getMiForeignInvestment());
			jPopupMenuPrintReports
					.add(getMiProcessingTradeVerificationBillExprotExg());
			jPopupMenuPrintReports
					.add(getMiProcessingTradeVerificationBillImportImg());
			jPopupMenuPrintReports
					.add(getMiNonCoverPrintAppliedContractConsumptionCased());
			// jPopupMenuPrintReports
			// .add(getMiNonCoverPrintAppliedContractConsumptionCased());
			jPopupMenuPrintReports.add(getMiContractCavDomesticPurchaseBill());
			jPopupMenuPrintReports.add(getMiBomFilingRealityCompare());
		}
		return jPopupMenuPrintReports;
	}

	/**
	 * 打印非套打核销表
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiNonCoverPrintApplied() {
		if (miNonCoverPrintApplied == null) {
			miNonCoverPrintApplied = new JMenuItem();
			miNonCoverPrintApplied.setText("打印非套打核销表");
			miNonCoverPrintApplied
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							printNonCoverPrintApplied();
						}
					});
		}
		return miNonCoverPrintApplied;
	}

	/**
	 * 打印套打核销表
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiCoverPrintApplied() {
		if (miCoverPrintApplied == null) {
			miCoverPrintApplied = new JMenuItem();
			miCoverPrintApplied.setText("打印套打核销表");
			miCoverPrintApplied
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							printCoverPrintApplied();
						}
					});
		}
		return miCoverPrintApplied;
	}

	/**
	 * 打印非套打核销料件表
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiNonCoverPrintAppliedImg() {
		if (miNonCoverPrintAppliedImg == null) {
			miNonCoverPrintAppliedImg = new JMenuItem();
			miNonCoverPrintAppliedImg.setText("打印非套打核销料件表");
			miNonCoverPrintAppliedImg
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							printNonCoverPrintAppliedImg();
						}
					});
		}
		return miNonCoverPrintAppliedImg;
	}

	/**
	 * 打印套打核销料件表
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiCoverPrintAppliedImg() {
		if (miCoverPrintAppliedImg == null) {
			miCoverPrintAppliedImg = new JMenuItem();
			miCoverPrintAppliedImg.setText("打印套打核销料件表");
			miCoverPrintAppliedImg
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							printCoverPrintAppliedImg();
						}
					});
		}
		return miCoverPrintAppliedImg;
	}

	/**
	 * 打印非套打核销成品表
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiNonCoverPrintAppliedExg() {
		if (miNonCoverPrintAppliedExg == null) {
			miNonCoverPrintAppliedExg = new JMenuItem();
			miNonCoverPrintAppliedExg.setText("打印非套打核销成品表");
			miNonCoverPrintAppliedExg
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							printNonCoverPrintAppliedExg();
						}
					});
		}
		return miNonCoverPrintAppliedExg;
	}

	/**
	 * 打印套打核销成品表
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiCoverPrintAppliedExg() {
		if (miCoverPrintAppliedExg == null) {
			miCoverPrintAppliedExg = new JMenuItem();
			miCoverPrintAppliedExg.setText("打印套打核销成品表");
			miCoverPrintAppliedExg
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							printCoverPrintAppliedExg();
						}
					});
		}
		return miCoverPrintAppliedExg;
	}

	/**
	 * 打印非套打核销单耗表
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiNonCoverPrintAppliedUnitConsumption() {
		if (miNonCoverPrintAppliedUnitConsumption == null) {
			miNonCoverPrintAppliedUnitConsumption = new JMenuItem();
			miNonCoverPrintAppliedUnitConsumption.setText("打印非套打核销单耗表");
			miNonCoverPrintAppliedUnitConsumption
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							printNonCoverPrintAppliedUnitConsumption();
						}
					});
		}
		return miNonCoverPrintAppliedUnitConsumption;
	}

	/**
	 * 打印套打核销单耗表
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiCoverPrintAppliedUnitConsumption() {
		if (miCoverPrintAppliedUnitConsumption == null) {
			miCoverPrintAppliedUnitConsumption = new JMenuItem();
			miCoverPrintAppliedUnitConsumption.setText("打印套打核销单耗表");
			miCoverPrintAppliedUnitConsumption
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							printCoverPrintAppliedUnitConsumption();
						}
					});
		}
		return miCoverPrintAppliedUnitConsumption;
	}

	// /**
	// * 打印非套打核销表(新)
	// *
	// * @return javax.swing.JMenuItem
	// */
	// private JMenuItem getMiNonCoverPrintAppliedNew() {
	// if (miNonCoverPrintAppliedNew == null) {
	// miNonCoverPrintAppliedNew = new JMenuItem();
	// miNonCoverPrintAppliedNew.setText("打印非套打核销表(新)");
	// // miNonCoverPrintAppliedNew.add(getMiNonCoverPrintAppliedNewSmall());
	// // miNonCoverPrintAppliedNew.add(getMiNonCoverPrintAppliedNewSmall());
	// miNonCoverPrintAppliedNew
	// .addActionListener(new java.awt.event.ActionListener() {
	// public void actionPerformed(java.awt.event.ActionEvent e) {
	// JPopupMenu jPopupMenu = new JPopupMenu();
	// jPopupMenu.add(getMiNonCoverPrintAppliedNewSmall());
	// jPopupMenu.add(getMiNonCoverPrintAppliedBig());
	// getJPopupMenuPrintReports().show(miNonCoverPrintAppliedNew, 0,
	// miNonCoverPrintAppliedNew.getHeight());
	// }
	// });
	// }
	// return miNonCoverPrintAppliedNew;
	// }
	//
	/**
	 * 打印非套打核销表(新)
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiNonCoverPrintAppliedNewSmall() {
		if (miNonCoverPrintAppliedNewSmall == null) {
			miNonCoverPrintAppliedNewSmall = new JMenuItem();
			miNonCoverPrintAppliedNewSmall.setText("打印非套打核销表(新1)");

			miNonCoverPrintAppliedNewSmall
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							printNonCoverPrintAppliedNew(true);
						}
					});
		}
		return miNonCoverPrintAppliedNewSmall;
	}

	/**
	 * 打印非套打核销表(新)
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiNonCoverPrintAppliedNewBig() {
		if (miNonCoverPrintAppliedNewBig == null) {
			miNonCoverPrintAppliedNewBig = new JMenuItem();
			miNonCoverPrintAppliedNewBig.setText("打印非套打核销表(新2)");

			miNonCoverPrintAppliedNewBig
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							printNonCoverPrintAppliedNew(false);
						}
					});
		}
		return miNonCoverPrintAppliedNewBig;
	}

	/**
	 * 打印非套打核销单耗表(新)
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiCoverPrintAppliedUnitConsumptionNew() {
		if (miCoverPrintAppliedUnitConsumptionNew == null) {
			miCoverPrintAppliedUnitConsumptionNew = new JMenuItem();
			miCoverPrintAppliedUnitConsumptionNew.setText("打印非套打核销单耗表(新)");
			miCoverPrintAppliedUnitConsumptionNew
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							printNonCoverPrintAppliedUnitConsumptionNew();
						}
					});
		}
		return miCoverPrintAppliedUnitConsumptionNew;
	}

	/**
	 * 打印非套打对外加工装配、补偿贸易合同核销申请（结案）表
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiNonCoverPrintAppliedContractConsumptionCased() {
		if (miNonCoverPrintAppliedContractConsumptionCased == null) {
			miNonCoverPrintAppliedContractConsumptionCased = new JMenuItem();
			miNonCoverPrintAppliedContractConsumptionCased
					.setText("打印非套打合同核销申请（结案）表");
			miNonCoverPrintAppliedContractConsumptionCased
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							printNonCoverPrintAppliedContractConsumptionCased();
						}
					});
		}
		return miNonCoverPrintAppliedContractConsumptionCased;
	}

	/**
	 * This method initializes tfCoLevel
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField() {
		if (tfCoLevel == null) {
			tfCoLevel = new JTextField();
			tfCoLevel.setEditable(false);
			tfCoLevel.setBounds(new Rectangle(466, 54, 32, 22));
		}
		return tfCoLevel;
	}

	/**
	 * This method initializes miForeignInvestment
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiForeignInvestment() {
		if (miForeignInvestment == null) {
			miForeignInvestment = new JMenuItem();
			miForeignInvestment.setText("外商投资企业加工贸易业务核销表");
			miForeignInvestment
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							printPyForeignInvestment();
						}
					});
		}
		return miForeignInvestment;
	}

	/**
	 * This method initializes miProcessingTradeVerificationBillExprotExg
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiProcessingTradeVerificationBillExprotExg() {
		if (miProcessingTradeVerificationBillExprotExg == null) {
			miProcessingTradeVerificationBillExprotExg = new JMenuItem();
			miProcessingTradeVerificationBillExprotExg
					.setText("加工贸易业务核销清单(出口制成品部分)");
			miProcessingTradeVerificationBillExprotExg
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							printPyProcessingTradeVerificationBillExprotExg();
						}
					});
		}
		return miProcessingTradeVerificationBillExprotExg;
	}

	/**
	 * This method initializes miProcessingTradeVerificationBillImportImg
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiProcessingTradeVerificationBillImportImg() {
		if (miProcessingTradeVerificationBillImportImg == null) {
			miProcessingTradeVerificationBillImportImg = new JMenuItem();
			miProcessingTradeVerificationBillImportImg
					.setText("加工贸易业务核销清单(进口料件部分)");
			miProcessingTradeVerificationBillImportImg
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							printPyProcessingTradeVerificationBillImportImg();
						}
					});
		}
		return miProcessingTradeVerificationBillImportImg;
	}

	/**
	 * 打印非套打合同核销表 This method initializes btnPrintMiConverContract
	 * 
	 * @return javax.swing.JButton
	 */
	private JMenuItem getBtnPrintMiConverContract() {
		if (miPrintMiConverContract == null) {
			miPrintMiConverContract = new JMenuItem("打印非套打合同核销表");
			miPrintMiConverContract.setVisible(false);
			miPrintMiConverContract
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							protectedvoidprintMiConverContract();
						}
					});
		}
		return miPrintMiConverContract;
	}

	/**
	 * 打印非套打合同核销表
	 * 
	 * @return
	 */
	public void protectedvoidprintMiConverContract() {
		// TODOAuto-generatedmethodstub
		ContractCav contractCav = this.contractCavSelf;
		List<TempContractCheckCav> list = new ArrayList<TempContractCheckCav>();
		TempContractCheckCav contractCheckCav = contractCavAction
				.findCavContract(new Request(CommonVars.getCurrUser()),
						contract);
		if (contractCheckCav == null) {
			JOptionPane.showMessageDialog(DgContractCav.this, "没有数据!");
			return;
		} else {
			list.add(contractCheckCav);
		}
		CustomReportDataSource ds1 = new CustomReportDataSource(list);
		InputStream masterReportStream = DgContractCav.class
				.getResourceAsStream("report/ContractCav.jasper");
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("contracNo", contractCav.getContractNo());
		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					masterReportStream, parameters, ds1);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * This method initializes miContractCavDomesticPurchaseBill
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiContractCavDomesticPurchaseBill() {
		if (miContractCavDomesticPurchaseBill == null) {
			miContractCavDomesticPurchaseBill = new JMenuItem();
			miContractCavDomesticPurchaseBill.setText("合同国内购料清单");
			miContractCavDomesticPurchaseBill
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							printContractCavDomesticPurchaseBill();
						}
					});
		}
		return miContractCavDomesticPurchaseBill;
	}

	/**
	 * 打印合同国内购料清单表
	 */
	public void printContractCavDomesticPurchaseBill() {
		ContractCav contractCav = null;
		if (this.rbCustoms.isSelected()) {
			contractCav = this.contractCavCustoms;
		} else if (this.rbSelf.isSelected()) {
			contractCav = this.contractCavSelf;
		}
		if (contractCav == null) {
			return;
		}
		List list2 = null;
		// String parentId = contract.getId();
		String id = contractCav.getId();
		String contarctNo = contractCav.getContractNo() == null ? ""
				: contractCav.getContractNo();
		System.out.println(id);
		list2 = contractCavAction.setContractCavDomesticPurchaseBill(
				new Request(CommonVars.getCurrUser()), id);
		System.out.println(list2);
		System.out.println("list2.size()=" + list2.size());
		CustomReportDataSource ds = new CustomReportDataSource(list2);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("contarctNo", contarctNo);
		InputStream masterReportStream = DgContractCav.class
				.getResourceAsStream("report/ContractDomesticPurchaseBill.jasper");
		JasperPrint jasperPrint;
		try {
			jasperPrint = JasperFillManager.fillReport(masterReportStream,
					parameters, ds);
			DgReportViewer viewer;
			viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * This method initializes miBomFilingRealityCompare
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiBomFilingRealityCompare() {
		if (miBomFilingRealityCompare == null) {
			miBomFilingRealityCompare = new JMenuItem();
			miBomFilingRealityCompare.setText("备案总耗与实际总耗对照表");
			miBomFilingRealityCompare
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							printBomFilingRealityCompareBill();
						}
					});
		}
		return miBomFilingRealityCompare;
	}

	/**
	 * 打印备案总耗与实际总耗对照表
	 */
	public void printBomFilingRealityCompareBill() {
		ContractCav contractCav = null;
		if (this.rbCustoms.isSelected()) {
			contractCav = this.contractCavCustoms;
		} else if (this.rbSelf.isSelected()) {
			contractCav = this.contractCavSelf;
		}
		if (contractCav == null) {
			return;
		}
		List list2 = null;
		// String parentId = contract.getId();
		String id = contractCav.getId();
		System.out.println(id);
		String contarctNo = contractCav.getContractNo() == null ? ""
				: contractCav.getContractNo();
		list2 = contractCavAction.setBomFilingRealityCompareBill(new Request(
				CommonVars.getCurrUser()), id);
		System.out.println(list2);
		CustomReportDataSource ds = new CustomReportDataSource(list2);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("contarctNo", contarctNo);
		InputStream masterReportStream = DgContractCav.class
				.getResourceAsStream("report/BomFilingRealityCompare.jasper");
		JasperPrint jasperPrint;
		try {
			jasperPrint = JasperFillManager.fillReport(masterReportStream,
					parameters, ds);
			DgReportViewer viewer;
			viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * This method initializes tfValueAddedRate
	 * 
	 * @return javax.swing.JTextField
	 */
	private JCustomFormattedTextField getTfValueAddedRate() {
		if (tfValueAddedRate == null) {
			tfValueAddedRate = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			// tfValueAddedRate.setBackground(Color.white);
			tfValueAddedRate.setBounds(new Rectangle(73, 18, 76, 22));
			tfValueAddedRate.setEditable(false);
			CustomFormattedTextFieldUtils.setFormatterFactory(tfValueAddedRate,
					4);
		}
		return tfValueAddedRate;
	}

	/**
	 * This method initializes tfNetWeightLossRate
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfNetWeightLossRate() {
		if (tfNetWeightLossRate == null) {
			tfNetWeightLossRate = new JTextField();
			// tfNetWeightLossRate.setBackground(Color.white);
			tfNetWeightLossRate.setBounds(new Rectangle(113, 19, 76, 22));
			tfNetWeightLossRate.setEditable(false);
		}
		return tfNetWeightLossRate;
	}

	/**
	 * This method initializes pn7
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPn7() {
		if (pn7 == null) {
			TitledBorder titledBorder = BorderFactory.createTitledBorder(null,
					"", TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.PLAIN, 12), new Color(51, 51, 51));
			titledBorder.setTitle("净重损失率");
			// jLabel6 = new JLabel();
			// jLabel6.setBounds(new Rectangle(181, 22, 82, 21));
			// jLabel6.setText("退运货物(净重)");
			// jLabel5 = new JLabel();
			// jLabel5.setBounds(new Rectangle(20, 22, 82, 21));
			// jLabel5.setText("退运货物(净重)");
			// jLabel4 = new JLabel();
			// jLabel4.setText(" 退运货物(金额) ");
			// jLabel4.setBounds(new Rectangle(335, 17, 86, 21));
			// jLabel3 = new JLabel();
			// jLabel3.setText(" 后续补税(金额)");
			// jLabel3.setBounds(new Rectangle(178, 17, 84, 21));
			// jLabel2 = new JLabel();
			// jLabel2.setText(" 退运货物(金额)");
			// jLabel2.setBounds(new Rectangle(19, 17, 83, 21));
			pn7 = new JPanel();
			pn7.setLayout(null);
			pn7.setBounds(new Rectangle(17, 203, 673, 47));
			pn7.setBorder(titledBorder);
			pn7.add(jLabel1, null);
			pn7.add(getTfNetWeightLossRate(), null);
			// pn7.add(jLabel5, null);
			// pn7.add(getTfImpBackGoods(), null);
			// pn7.add(jLabel6, null);
			// pn7.add(getTfExpBackGoods(), null);
			pn7.setBorder(javax.swing.BorderFactory.createTitledBorder("净重损失率"));
		}
		return pn7;
	}

	// /**
	// * This method initializes tfBackImpGoods
	// *
	// * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	// */
	// private JCustomFormattedTextField getTfBackImpGoods() {
	// if (tfBackImpGoods == null) {
	// tfBackImpGoods = new
	// com.bestway.ui.winuicontrol.JCustomFormattedTextField();
	// tfBackImpGoods.setEnabled(true);
	// tfBackImpGoods.setBounds(new Rectangle(103, 17, 76, 22));
	// tfBackImpGoods.setToolTipText("贸易方式为4561的进口报关单");
	// CustomFormattedTextFieldUtils
	// .setFormatterFactory(tfBackImpGoods, 4);
	// }
	// return tfBackImpGoods;
	// }

	// /**
	// * This method initializes tfFollowTax
	// *
	// * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	// */
	// private JCustomFormattedTextField getTfFollowTax() {
	// if (tfFollowTax == null) {
	// tfFollowTax = new
	// com.bestway.ui.winuicontrol.JCustomFormattedTextField();
	// tfFollowTax.setEnabled(true);
	// tfFollowTax.setBounds(new Rectangle(261, 17, 76, 22));
	// tfFollowTax.setToolTipText("贸易方式为9700的进口报关单");
	// CustomFormattedTextFieldUtils.setFormatterFactory(tfFollowTax, 4);
	// }
	// return tfFollowTax;
	// }

	// /**
	// * This method initializes tfBackExpGoods
	// *
	// * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	// */
	// private JCustomFormattedTextField getTfBackExpGoods() {
	// if (tfBackExpGoods == null) {
	// tfBackExpGoods = new
	// com.bestway.ui.winuicontrol.JCustomFormattedTextField();
	// tfBackExpGoods.setToolTipText("贸易方式为4561的出口报关单");
	// tfBackExpGoods.setBounds(new Rectangle(422, 17, 76, 22));
	// CustomFormattedTextFieldUtils
	// .setFormatterFactory(tfBackExpGoods, 4);
	// }
	// return tfBackExpGoods;
	// }

	// /**
	// * This method initializes tfImpBackGoods
	// *
	// * @return javax.swing.JTextField
	// */
	// private JTextField getTfImpBackGoods() {
	// if (tfImpBackGoods == null) {
	// tfImpBackGoods = new
	// com.bestway.ui.winuicontrol.JCustomFormattedTextField();
	// tfImpBackGoods.setBounds(new Rectangle(104, 22, 76, 22));
	// tfImpBackGoods.setToolTipText("贸易方式为4561的进口报关单");
	// CustomFormattedTextFieldUtils
	// .setFormatterFactory(tfImpBackGoods, 4);
	// }
	// return tfImpBackGoods;
	// }

	// /**
	// * This method initializes tfExpBackGoods
	// *
	// * @return javax.swing.JTextField
	// */
	// private JTextField getTfExpBackGoods() {
	// if (tfExpBackGoods == null) {
	// tfExpBackGoods = new
	// com.bestway.ui.winuicontrol.JCustomFormattedTextField();
	// tfExpBackGoods.setBounds(new Rectangle(263, 22, 76, 22));
	// tfExpBackGoods.setToolTipText("贸易方式为4561的出口报关单");
	// CustomFormattedTextFieldUtils
	// .setFormatterFactory(tfExpBackGoods, 4);
	// }
	// return tfExpBackGoods;
	// }

	/**
	 * This method initializes tfImportDeviceGross
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfImportDeviceGross() {
		if (tfImportDeviceGross == null) {
			tfImportDeviceGross = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfImportDeviceGross.setBounds(new Rectangle(540, 184, 104, 24));
			tfImportDeviceGross
					.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfImportDeviceGross;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setBounds(new Rectangle(17, 142, 673, 46));
			jPanel.setName("");
			// jPanel.add(jLabel2, null);
			// jPanel.add(getTfBackImpGoods(), null);
			// jPanel.add(jLabel3, null);
			// jPanel.add(getTfFollowTax(), null);
			// jPanel.add(jLabel4, null);
			// jPanel.add(getTfBackExpGoods(), null);
			jPanel.add(jLabel, null);
			jPanel.add(getTfValueAddedRate(), null);
			jPanel.setBorder(javax.swing.BorderFactory
					.createTitledBorder("增值率"));
		}
		return jPanel;
	}

	/**
	 * This method initializes miNotModified
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiNotModified() {
		if (miNotModified == null) {
			miNotModified = new JMenuItem();
			miNotModified.setText("\u672a\u4fee\u6539");
		}
		return miNotModified;
	}

	private JMenuItem miModified = null;

	private JMenuItem miDelete = null;

	private JMenuItem miAdd = null;
	private JPanel jPanel1 = null;
	private JLabel lbFund = null;
	private JTextField tfFund = null;
	private JLabel jLabel21 = null;
	private JCheckBox cb6 = null;
	private JCheckBox cb7 = null;
	private JCheckBox cb8 = null;
	private JCheckBox cbBom4;

	/**
	 * This method initializes miModified
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiModified() {
		if (miModified == null) {
			miModified = new JMenuItem();
			miModified.setText("\u5df2\u4fee\u6539");
		}
		return miModified;
	}

	/**
	 * This method initializes miDelete
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiDelete() {
		if (miDelete == null) {
			miDelete = new JMenuItem();
			miDelete.setText("\u5df2\u5220\u9664");
		}
		return miDelete;
	}

	private JMenuItem getMiAdd() {
		if (miAdd == null) {
			miAdd = new JMenuItem();
			miAdd.setText("新  增");
		}
		return miAdd;
	}

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

	private void addChangeModifyMarkListener(JMenuItem menuItem,
			ActionListener actionListener) {
		ActionListener[] actionListeners = menuItem.getActionListeners();
		for (int i = 0; i < actionListeners.length; i++) {
			menuItem.removeActionListener(actionListeners[i]);
		}
		menuItem.addActionListener(actionListener);
	}

	/**
	 * This method initializes btnChangeImgModifyMark
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnChangeImgModifyMark() {
		if (btnChangeImgModifyMark == null) {
			btnChangeImgModifyMark = new JButton();
			btnChangeImgModifyMark.setName("");
			btnChangeImgModifyMark.setText("改变修改标志");
			btnChangeImgModifyMark.setToolTipText(ModifyMarkState
					.getToolTipText());
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
								addChangeModifyMarkListener(getMiNotModified(),
										new ChangeImgModifyMarkListener(
												ModifyMarkState.UNCHANGE));
								addChangeModifyMarkListener(getMiModified(),
										new ChangeImgModifyMarkListener(
												ModifyMarkState.MODIFIED));
								addChangeModifyMarkListener(getMiDelete(),
										new ChangeImgModifyMarkListener(
												ModifyMarkState.DELETED));
								addChangeModifyMarkListener(getMiAdd(),
										new ChangeImgModifyMarkListener(
												ModifyMarkState.ADDED));
								getPmChangeModifyMark().show(comp, 0,
										comp.getHeight());
							}

						}
					});

		}
		return btnChangeImgModifyMark;
	}

	class ChangeImgModifyMarkListener implements ActionListener {

		private String modifyMark = "";

		public ChangeImgModifyMarkListener(String modifyMark) {
			this.modifyMark = modifyMark;
		}

		public void actionPerformed(ActionEvent e) {
			if (tableModelBom.getCurrentRow() == null) {
				JOptionPane.showMessageDialog(DgContractCav.this, "请选择BOM",
						"提示", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			int start = tableModelBom.getCurrRowCount();
			int size = tbBom.getSelectedRowCount();
			// System.out.println(start);
			// System.out.println(size);
			List list = new ArrayList();
			for (int i = 0; i < size; i++) {
				list.add(tableModelBom.getObjectByRow(start + i));
			}
			contractCavAction.changeContractImgModifyMark(new Request(
					CommonVars.getCurrUser()), list, modifyMark);
			showBomData(true);
			System.out.println("second modifyMark" + modifyMark);
		}

	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLabel21 = new JLabel();
			jLabel21.setBounds(new Rectangle(153, 21, 26, 18));
			jLabel21.setText("￥");
			lbFund = new JLabel();
			lbFund.setText("口岸基础设施建设基金");
			lbFund.setBounds(new Rectangle(23, 20, 125, 18));
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setBounds(new Rectangle(16, 266, 401, 50));
			jPanel1.setBorder(BorderFactory
					.createTitledBorder(
							null,
							"\u53e3\u5cb8\u57fa\u7840\u8bbe\u65bd\u5efa\u8bbe\u57fa\u91d1",
							TitledBorder.DEFAULT_JUSTIFICATION,
							TitledBorder.DEFAULT_POSITION, new Font("Dialog",
									Font.PLAIN, 12), new Color(51, 51, 51)));
			jPanel1.add(getTfFund(), null);
			jPanel1.add(jLabel21, null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes tfFund
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfFund() {
		if (tfFund == null) {
			tfFund = new JTextField();
			tfFund.setBounds(new Rectangle(168, 19, 109, 22));
			tfFund.setEditable(false);
		}
		return tfFund;
	}

	public String format(Double dou) {

		// if (nf == null) {
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(2);
		// nf.setMinimumFractionDigits(2);
		nf.setMaximumIntegerDigits(99);
		nf.setGroupingUsed(false);
		nf.setRoundingMode(RoundingMode.HALF_UP);
		// }
		// System.out.println("-------------->>>>>" + nf.format(dou == null ?
		// 0.00 : dou.doubleValue()));
		return nf.format(dou == null ? 0.00 : dou.doubleValue());
	}

	/**
	 * This method initializes cb6
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCb6() {
		if (cb6 == null) {
			cb6 = new JCheckBox();
			cb6.setSize(new Dimension(195, 35));
			cb6.setText("单耗栏位为空，打印净耗");
		}
		return cb6;
	}

	private JCheckBox getCb7() {
		if (cb7 == null) {
			cb7 = new JCheckBox();
			cb7.setSize(new Dimension(195, 35));
			cb7.setText("净耗栏位为空，打印单耗");
		}
		return cb7;
	}

	private JCheckBox getCb8() {
		if (cb8 == null) {
			cb8 = new JCheckBox();
			cb8.setSize(new Dimension(195, 35));
			cb8.setText("损耗量栏位为空，打印单耗，否则打印净耗");
		}
		return cb8;
	}

	public ButtonGroup getBtnGroup() {
		if (btnGroup == null) {
			btnGroup = new ButtonGroup();
		}
		btnGroup.add(getCb6());
		btnGroup.add(getCb7());
		btnGroup.add(getCb8());
		return btnGroup;
	}

	public void setBtnGroup(ButtonGroup btnGroup) {
		this.btnGroup = btnGroup;
	}

	private JCheckBox getCbBom4() {
		if (cbBom4 == null) {
			cbBom4 = new JCheckBox("修改损耗量不反算总用量，损耗，成品耗用量和料件余量");
			cbBom4.setSelected(true);
			cbBom4.setEnabled(false);
		}
		return cbBom4;
	}

	public Double getSumContractImgTotalPrice() {
		return sumContractImgTotalPrice;
	}

	public void setSumContractImgTotalPrice(Double sumContractImgTotalPrice) {
		this.sumContractImgTotalPrice = sumContractImgTotalPrice;
	}

	public Double getSumContractExgTotalPrice() {
		return sumContractExgTotalPrice;
	}

	public void setSumContractExgTotalPrice(Double sumContractExgTotalPrice) {
		this.sumContractExgTotalPrice = sumContractExgTotalPrice;
	}

} // @jve:decl-index=0:visual-constraint="10,446"
