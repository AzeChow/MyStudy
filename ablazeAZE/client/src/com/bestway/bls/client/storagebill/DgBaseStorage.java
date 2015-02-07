package com.bestway.bls.client.storagebill;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
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
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import org.apache.commons.beanutils.BeanUtilsBean;

import com.bestway.bcs.client.contract.DgContract;
import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomFormattedTextFieldUtils;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.DgCommonQuery;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.custombase.action.CustomBaseAction;
import com.bestway.bcus.custombase.entity.basecode.Brief;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.parametercode.Wrap;
import com.bestway.bls.action.BlsMessageAction;
import com.bestway.bls.action.StorageBillAction;
import com.bestway.bls.entity.BlsInnerMerge;
import com.bestway.bls.entity.BlsParameterSet;
import com.bestway.bls.entity.BlsTenInnerMerge;
import com.bestway.bls.entity.StorageBill;
import com.bestway.bls.entity.StorageBillAfter;
import com.bestway.bls.entity.StorageBillBefore;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.TableCellRenderers.TableCheckBoxRender;
import com.bestway.client.util.footer.JFooterScrollPane;
import com.bestway.client.util.footer.JFooterTable;
import com.bestway.client.util.footer.TableFooterType;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.materialbase.entity.WareSet;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * 仓单管理主界面 checked by kcb 2009-1-10
 * 
 * @author kangbo
 * 
 */
public class DgBaseStorage extends JDialogBase {

	private JTabbedPane jTabbedPane = null;
	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JToolBar jJToolBarBar = null;
	private JButton btnSaveHead = null;
	private JButton btnEditHead = null;
	private JButton btnEffect = null;
	private JButton btnCacelHaed = null;
	private JLabel jLabel = null;
	private JTextField tfBillNo = null;
	private JToolBar jJToolBarBar1 = null;
	private JButton jButton4 = null;
	private JButton btnEditAfter = null;
	private JButton btnDeleteAfter = null;
	private JButton btnCancel = null;
	private JFooterScrollPane jScrollPane = null;
	protected JFooterTable tbAfter = null;
	private JSplitPane jSplitPane = null;
	private JPanel jPanel2 = null;
	private JPanel jPanel3 = null;
	private JToolBar jJToolBarBar2 = null;
	private JButton btnAddBefer = null;
	private JButton btnEditBefer = null;
	private JButton btnDeleteBefore = null;
	private JLabel jLabel1 = null;
	private JLabel jLabel2 = null;
	private JLabel jLabel3 = null;
	private JComboBox cbbWareHouseCode = null;
	private JLabel jLabel4 = null;
	private JTextField tfEmsNo = null;
	private JLabel jLabel5 = null;
	private JComboBox cbbBillType = null;
	private JLabel jLabel6 = null;
	private JCalendarComboBox cbbValidDate = null;
	private JLabel jLabel7 = null;
	private JComboBox cbbCustomsCode = null;
	private JLabel jLabel8 = null;
	private JComboBox cbbIoFlag = null;
	private JLabel jLabel9 = null;
	private JTextField tfManualNo = null;
	private JLabel jLabel10 = null;
	private JTextField tfPlanNo = null;
	private JLabel jLabel11 = null;
	private JTextField tfOrderNo = null;
	private JLabel jLabel12 = null;
	private JComboBox cbbWrap = null;
	private JLabel jLabel14 = null;
	private JLabel jLabel15 = null;
	private JCustomFormattedTextField tfGrossWeight = null;
	private JLabel jLabel16 = null;
	private JCustomFormattedTextField tfPackCount = null;
	private JLabel jLabel17 = null;
	private JCustomFormattedTextField tfNetWeight = null;
	private JLabel jLabel18 = null;
	private JTextField tfInName = null;
	private JLabel jLabel19 = null;
	private JComboBox jComboBox7 = null;
	private JLabel jLabel20 = null;
	private JTextField tfOutName = null;
	private JLabel jLabel21 = null;
	private JCustomFormattedTextField tfItemsCount = null;
	private JFooterScrollPane jScrollPane1 = null;
	private JFooterTable tbBefore = null;
	private CustomBaseAction customBaseAction = null;
	private StorageBillAction storageBillAction;
	private MaterialManageAction materialManageAction = null;
	private BlsMessageAction blsMessageAction = null;
	private JButton jButton12 = null;
	private JButton jButton13 = null;
	private JButton btnReEffect = null;
	private StorageBill storageBill = null; // @jve:decl-index=0:
	private JLabel jLabel23 = null;
	private JComboBox cbbSupplierCd = null;
	private JComboBox cbbCorrOwnerCode = null;
	private JTableListModel fathertableModel;
	private JTableListModel beforetableModel;
	private JTableListModel aftertableModel;
	private int dataState = DataState.ADD;
	private JButton btnBrowseAfter = null;

	public JTableListModel getFathertableModel() {
		return fathertableModel;
	}

	public void setFathertableModel(JTableListModel fathertableModel) {
		this.fathertableModel = fathertableModel;
	}

	public int getDataState() {
		return dataState;
	}

	public void setDataState(int dataState) {
		this.dataState = dataState;
	}

	public StorageBill getStorageBill() {
		return storageBill;
	}

	public void setStorageBill(StorageBill storageBill) {
		this.storageBill = storageBill;
	}

	/**
	 * 构造方法
	 * 
	 */
	public DgBaseStorage() {
		super();
		customBaseAction = (CustomBaseAction) CommonVars
				.getApplicationContext().getBean("customBaseAction");
		storageBillAction = (StorageBillAction) CommonVars
				.getApplicationContext().getBean("storageBillAction");
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		blsMessageAction = (BlsMessageAction) CommonVars
				.getApplicationContext().getBean("blsMessageAction");
		initialize();
		initCompnents();
	}

	@Override
	public void setVisible(boolean f) {
		BlsParameterSet parameterSet = blsMessageAction
				.findBlsParameterSet(new Request(CommonVars.getCurrUser(), true));
		if (f) {
			showData();
			setState();
			initAuto();
			if (parameterSet != null) {
				if (storageBill.getEmsNo() == null
						&& dataState == DataState.ADD
						&& DgBaseStorage.this instanceof DgImportStorage) {
					storageBill.setEmsNo(parameterSet.getEmsNO());
				}
			}
			super.setVisible(f);
		}

	}

	/**
	 * 填充数据
	 */
	private void fillData() {
		BlsParameterSet parameterSet = blsMessageAction
				.findBlsParameterSet(new Request(CommonVars.getCurrUser(), true));
		if (storageBill == null) {
			storageBill = new StorageBill();
		}
		storageBill.setBillNo(tfBillNo.getText());
		if (parameterSet != null && tfEmsNo.getText().trim() == null) {
			storageBill.setEmsNo(parameterSet.getEmsNO());
		} else {
			storageBill.setEmsNo(tfEmsNo.getText());
		}
		storageBill.setManualNo(tfManualNo.getText());
		storageBill.setOrderNo(tfOrderNo.getText());
		storageBill.setPlanNo(tfPlanNo.getText());
		storageBill
				.setGrossWeight((tfGrossWeight.getText() == null || tfGrossWeight
						.getText().trim().equals("")) ? null : Double
						.valueOf(tfGrossWeight.getText()));
		storageBill.setNetWeight((tfNetWeight.getText() == null || tfNetWeight
				.getText().trim().equals("")) ? null : Double
				.valueOf(tfNetWeight.getText()));
		storageBill.setInName(tfInName.getText());
		storageBill.setOutName(tfOutName.getText());
		storageBill.setItemsCount(Integer
				.parseInt(tfItemsCount.getValue() == null ? "0" : tfItemsCount
						.getValue().toString()));
		storageBill.setPackCount(Integer
				.parseInt(tfPackCount.getValue() == null ? "0" : tfPackCount
						.getValue().toString()));
		storageBill.setValidDate(cbbValidDate.getDate());
		// -----------------------------------------------------------
		if (cbbWareHouseCode.getSelectedItem() != null) {
			storageBill.setWareHouseCode((WareSet) cbbWareHouseCode
					.getSelectedItem());
		} else {
			storageBill.setWareHouseCode(null);
		}

		if (cbbBillType.getSelectedItem() != null) {
			storageBill.setBillType(((ItemProperty) cbbBillType
					.getSelectedItem()).getCode());
		} else {
			storageBill.setBillType(null);
		}
		if (cbbCustomsCode.getSelectedItem() != null) {
			storageBill.setCustomsCode((Customs) cbbCustomsCode
					.getSelectedItem());
		} else {
			storageBill.setCustomsCode(null);
		}

		if (cbbIoFlag.getSelectedItem() != null) {
			storageBill.setIoFlag((((ItemProperty) cbbIoFlag.getSelectedItem())
					.getCode()));
		} else {
			storageBill.setIoFlag(null);
		}

		if (cbbWrap.getSelectedItem() != null) {
			storageBill.setWrap((Wrap) cbbWrap.getSelectedItem());
		} else {
			storageBill.setWrap(null);
		}
		if (jComboBox7.getSelectedItem() != null) {
			storageBill.setIePort((Customs) jComboBox7.getSelectedItem());
		} else {
			storageBill.setIePort(null);
		}
		if (cbbSupplierCd.getSelectedItem() != null) {
			storageBill.setSupplierCd((ScmCoc) cbbSupplierCd.getSelectedItem());
		} else {
			storageBill.setSupplierCd(null);
		}
		if (cbbCorrOwnerCode.getSelectedItem() != null) {
			storageBill.setCorrOwnerCode((ScmCoc) cbbCorrOwnerCode
					.getSelectedItem());
		} else {
			storageBill.setCorrOwnerCode(null);
		}
	}

	/**
	 * 显示数据
	 */
	private void showData() {
		BlsParameterSet parameterSet = blsMessageAction
				.findBlsParameterSet(new Request(CommonVars.getCurrUser(), true));
		if (storageBill == null) {
			storageBill = new StorageBill();
		}
		tfBillNo.setText(storageBill.getBillNo());
		if (parameterSet != null && storageBill.getEmsNo() == null) {
			tfEmsNo.setText(parameterSet.getEmsNO());
		} else {
			tfEmsNo.setText(storageBill.getEmsNo());
		}
		tfManualNo.setText(storageBill.getManualNo());
		tfOrderNo.setText(storageBill.getOrderNo());
		tfPlanNo.setText(storageBill.getPlanNo());
		tfGrossWeight.setText(storageBill.getGrossWeight() == null ? null
				: storageBill.getGrossWeight().toString());
		tfNetWeight.setText(storageBill.getNetWeight() == null ? null
				: storageBill.getNetWeight().toString());

		tfItemsCount.setText(storageBill.getItemsCount() == null ? "0"
				: storageBill.getItemsCount().toString());
		tfPackCount.setText(storageBill.getPackCount() == null ? "0"
				: storageBill.getPackCount().toString());
		tfInName.setText(storageBill.getInName());
		tfOutName.setText(storageBill.getOutName());
		cbbValidDate.setDate(storageBill.getValidDate());
		// -----------------------------------------------------------
		// if (jComboBox.getSelectedItem() != null) {
		// storageBill.setWareHouseCode((WareSet) jComboBox.getSelectedItem());
		// } else {
		// storageBill.setWareHouseCode(null);
		// }
		cbbWareHouseCode.setSelectedItem(storageBill.getWareHouseCode());
		// if (jComboBox1.getSelectedItem() != null) {
		// storageBill.setBillType(((ItemProperty) jComboBox1
		// .getSelectedItem()).getCode());
		// } else {
		// storageBill.setBillType(null);
		// }
		// String btype = storageBill.getBillType();
		// if (btype != null && !btype.trim().equals("")) {
		// int s = cbbBillType.getModel().getSize();
		// for (int i = 0; i < s; i++) {
		// Object obj = cbbBillType.getModel().getElementAt(i);
		// if (obj instanceof ItemProperty) {
		// ItemProperty itemp = (ItemProperty) obj;
		// if (itemp.getCode().equals(btype)) {
		// cbbBillType.setSelectedItem(itemp);
		// }
		// }
		// }
		// }
		System.out.println("-----------" + storageBill.getBillType());
		this.cbbBillType.setSelectedIndex(ItemProperty.getIndexByCode(
				storageBill.getBillType(), this.cbbBillType));
		// if (jComboBox2.getSelectedItem() != null) {
		// storageBill.setCustomsCode((Customs) jComboBox2.getSelectedItem());
		// } else {
		// storageBill.setBillType(null);
		// }
		cbbCustomsCode.setSelectedItem(storageBill.getCustomsCode());
		// if (jComboBox3.getSelectedItem() != null) {
		// storageBill.setIoFlag(Integer.parseInt(((ItemProperty) jComboBox3
		// .getSelectedItem()).getCode()));
		// } else {
		// storageBill.setIoFlag(null);
		// }
		// Integer tcode = storageBill.getIoFlag() == null ? null : storageBill
		// .getIoFlag();
		// if (tcode != null && !tcode.toString().trim().equals("")) {
		// int s = jComboBox3.getModel().getSize();
		// for (int i = 0; i < s; i++) {
		// Object obj = jComboBox3.getModel().getElementAt(i);
		// if (obj instanceof ItemProperty) {
		// ItemProperty itemp = (ItemProperty) obj;
		// if (itemp.getCode().equals(tcode.toString())) {
		// jComboBox3.setSelectedItem(itemp);
		// }
		// }
		// }
		// } else {
		// jComboBox3.setSelectedItem(null);
		// }
		// if (jComboBox4.getSelectedItem() != null) {
		// storageBill.setWrap((Wrap) jComboBox4.getSelectedItem());
		// } else {
		// storageBill.setWrap(null);
		// }
		cbbWrap.setSelectedItem(storageBill.getWrap());
		// if (jComboBox7.getSelectedItem() != null) {
		// storageBill.setIePort((Customs) jComboBox7.getSelectedItem());
		// } else {
		// storageBill.setIePort(null);
		// }
		jComboBox7.setSelectedItem(storageBill.getIePort());
		// if (jComboBox6.getSelectedItem() != null) {
		// storageBill.setSupplierCd((ScmCoc) jComboBox6.getSelectedItem());
		// } else {
		// storageBill.setWrap(null);
		// }
		cbbSupplierCd.setSelectedItem(storageBill.getSupplierCd());
		// if (jComboBox8.getSelectedItem() != null) {
		// storageBill.setCorrOwnerCode((ScmCoc) jComboBox8.getSelectedItem());
		// } else {
		// storageBill.setWrap(null);
		// }
		cbbCorrOwnerCode.setSelectedItem(storageBill.getCorrOwnerCode());

	}

	/**
	 *初始化
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(659, 428));
		this.setTitle("仓单信息管理");
		this.setContentPane(getJTabbedPane());

	}

	/**
	 *初始化界面组件
	 * 
	 */
	private void initCompnents() {
		// 初始化报送海关
		this.cbbCustomsCode.setModel(CustomBaseModel.getInstance()
				.getCustomModel());
		this.cbbCustomsCode.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbCustomsCode);
		// 初始化包装种类
		List listwarp = customBaseAction.findWrap();
		this.cbbWrap.setModel(new DefaultComboBoxModel(listwarp.toArray()));
		this.cbbWrap.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(this.cbbWrap,
				"code", "name");
		cbbWrap.setSelectedItem(null);
		// 仓库
		List wareSetList = this.materialManageAction.findWareSet(new Request(
				CommonVars.getCurrUser()));
		this.cbbWareHouseCode.setModel(new DefaultComboBoxModel(wareSetList
				.toArray()));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				cbbWareHouseCode, "code", "name", 250);
		this.cbbWareHouseCode.setRenderer(CustomBaseRender.getInstance()
				.getRender("code", "name", 80, 170));
		// 仓单类型
		cbbBillType.addItem(new ItemProperty("00", "申报初始库存"));
		cbbBillType.addItem(new ItemProperty("01", "后报关方式"));
		cbbBillType.addItem(new ItemProperty("02", "先报关分批送货方式"));
		cbbBillType.addItem(new ItemProperty("03", "特殊审核"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbBillType,
				"code", "name", 250);
		this.cbbBillType.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 80, 170));
		// 进出仓标志
		cbbIoFlag.addItem(new ItemProperty("I", "进仓"));
		cbbIoFlag.addItem(new ItemProperty("O", "出仓"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbIoFlag,
				"code", "name", 100);
		this.cbbIoFlag.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 80, 100));
		if (DgBaseStorage.this instanceof DgImportStorage) {
			System.out.println("in");
			cbbIoFlag.setSelectedIndex(0);
		} else if (DgBaseStorage.this instanceof DgExportStorage) {
			System.out.println("out");
			cbbIoFlag.setSelectedIndex(1);
		}
		cbbIoFlag.setEnabled(false);
		// 客户供应商
		// if (this.materielProductType == Integer
		// .parseInt(MaterielType.FINISHED_PRODUCT)) {
		// list = this.getCustomer();
		// } else if (materielProductType == Integer
		// .parseInt(MaterielType.MATERIEL)) {
		// list = this.getManufacturer();
		// }
		List listcos = this.materialManageAction.findScmCocs(new Request(
				CommonVars.getCurrUser()));
		// 客户供应商
		// if (this.materielProductType == Integer
		// .parseInt(MaterielType.FINISHED_PRODUCT)) {
		// list = this.getCustomer();
		// } else if (materielProductType == Integer
		// .parseInt(MaterielType.MATERIEL)) {
		// list = this.getManufacturer();
		// }
		this.cbbSupplierCd
				.setModel(new DefaultComboBoxModel(listcos.toArray()));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbSupplierCd,
				"code", "name", 250);
		this.cbbSupplierCd.setRenderer(CustomBaseRender.getInstance()
				.getRender("code", "name", 80, 170));

		this.cbbCorrOwnerCode.setModel(new DefaultComboBoxModel(listcos
				.toArray()));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				cbbCorrOwnerCode, "code", "name", 250);
		this.cbbCorrOwnerCode.setRenderer(CustomBaseRender.getInstance()
				.getRender("code", "name", 80, 170));

		// 初始化进口口岸
		this.jComboBox7
				.setModel(CustomBaseModel.getInstance().getCustomModel());
		this.jComboBox7.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.jComboBox7);
	}

	/**
	 * 获得是供应商的对象列表
	 */
	// protected List getManufacturer() {
	// List list = null;
	// if (this.billType == ImpExpType.TRANSFER_FACTORY_IMPORT) {
	//
	// list = this.materialManageAction.findScmManuFactoryIn(new Request(
	// CommonVars.getCurrUser()));
	//
	// } else
	//
	// list = this.materialManageAction.findScmManufacturer(new Request(
	// CommonVars.getCurrUser()));
	//
	// List manufacturerList = new ArrayList();
	// for (int i = 0; i < list.size(); i++) {
	// ScmCoc scmCoc = (ScmCoc) list.get(i);
	// if (scmCoc.getIsManufacturer() != null
	// && scmCoc.getIsManufacturer().booleanValue()) {
	// manufacturerList.add(scmCoc);
	// }
	// }
	// return manufacturerList;
	// }
	/**
	 * 获得是客户的对象列表
	 */
	// protected List getCustomer() {
	// List list = null;
	// if (this.billType == ImpExpType.TRANSFER_FACTORY_EXPORT) {
	//
	// list = this.materialManageAction.findScmManuFactoryOut(new Request(
	// CommonVars.getCurrUser()));
	// // String string[] =(String[])list.get(0);
	// // System.out.print(string[0]+"sdfgsdgfdsgdfsgdfsh");
	// } else
	// list = this.materialManageAction.findScmManucustomer(new Request(
	// CommonVars.getCurrUser()));
	// List customerList = new ArrayList();
	// for (int i = 0; i < list.size(); i++) {
	// ScmCoc scmCoc = (ScmCoc) list.get(i);
	// if (scmCoc.getIsCustomer() != null
	// && scmCoc.getIsCustomer().booleanValue()) {
	// customerList.add(scmCoc);
	// }
	// }
	// return customerList;
	// }
	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setTabPlacement(JTabbedPane.BOTTOM);
			jTabbedPane.addTab("仓单基本信息", null, getJPanel(), null);
			jTabbedPane.addTab("仓单商品信息", null, getJPanel2(), null);
			jTabbedPane
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							initTableBefore(new ArrayList());
							initTableAfter(new ArrayList());
							if (jTabbedPane.getSelectedIndex() == 1) {
								if (storageBill.getId() == null) {
									JOptionPane.showMessageDialog(
											DgBaseStorage.this, "请先保存表头！",
											"提示！", JOptionPane.WARNING_MESSAGE);
									jTabbedPane.setSelectedIndex(0);
									return;
								}
								refreshTable();

							} else if (jTabbedPane.getSelectedIndex() == 0) {
								initAuto();
							}

						}
					});
		}
		return jTabbedPane;
	}

	/**
	 * 自动计算相关栏位
	 */
	private void initAuto() {
		if (storageBill.getId() == null) {
			return;
		}
		Integer amount = storageBillAction.findAmountpicesFromStorageBill(
				new Request(CommonVars.getCurrUser()), storageBill);
		tfItemsCount.setValue(amount);
		storageBill.setItemsCount(amount);
		if (storageBill.getId() != null) {
			storageBill = storageBillAction.findStorageBillByID(new Request(
					CommonVars.getCurrUser()), storageBill.getId());
		}
		storageBillAction.saveOrUpdateObject(new Request(CommonVars
				.getCurrUser()), storageBill);
	}

	/**
	 * 刷新表格
	 */
	private void refreshTable() {
		List klist = storageBillAction.findStorageBillAfterForDelivery(
				new Request(CommonVars.getCurrUser()), storageBill);
		initTableAfter(klist);
		if (aftertableModel.getList() != null
				&& aftertableModel.getList().size() > 0) {
			aftertableModel.setSelectRow(0);
			StorageBillAfter af = (StorageBillAfter) aftertableModel
					.getCurrentRow();
			if (af != null) {
				List list = storageBillAction.findStorageBillBeforeForDelivery(
						new Request(CommonVars.getCurrUser()), af);
				initTableBefore(list);

			}
		} else {
			initTableAfter(new ArrayList());
			initTableBefore(new ArrayList());
		}

	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel23 = new JLabel();
			jLabel23.setBounds(new Rectangle(336, 254, 90, 22));
			jLabel23.setText("供货方企业编码\n\n");
			jLabel21 = new JLabel();
			jLabel21.setBounds(new Rectangle(80, 254, 65, 22));
			jLabel21.setText("转入方名称");
			jLabel20 = new JLabel();
			jLabel20.setBounds(new Rectangle(80, 283, 65, 22));
			jLabel20.setText("输出方名称");
			jLabel19 = new JLabel();
			jLabel19.setBounds(new Rectangle(336, 196, 90, 22));
			jLabel19.setText("进出口岸");
			jLabel18 = new JLabel();
			jLabel18.setBounds(new Rectangle(336, 283, 90, 22));
			jLabel18.setText("仓单总数量");
			jLabel18.setForeground(Color.blue);
			jLabel17 = new JLabel();
			jLabel17.setBounds(new Rectangle(80, 107, 65, 22));
			jLabel17.setText("手册号");
			jLabel16 = new JLabel();
			jLabel16.setBounds(new Rectangle(80, 197, 65, 22));
			jLabel16.setForeground(Color.blue);
			jLabel16.setText("净重");
			jLabel15 = new JLabel();
			jLabel15.setBounds(new Rectangle(81, 224, 65, 22));
			jLabel15.setForeground(Color.blue);
			jLabel15.setText("毛重");
			jLabel14 = new JLabel();
			jLabel14.setBounds(new Rectangle(336, 225, 90, 22));
			jLabel14.setText("供货商编码");
			jLabel12 = new JLabel();
			jLabel12.setBounds(new Rectangle(336, 167, 90, 22));
			jLabel12.setText("包装种类");
			jLabel11 = new JLabel();
			jLabel11.setBounds(new Rectangle(336, 313, 90, 22));
			jLabel11.setText("仓单总件数");
			jLabel11.setForeground(Color.blue);
			jLabel10 = new JLabel();
			jLabel10.setBounds(new Rectangle(80, 167, 65, 22));
			jLabel10.setText("计划编号");
			jLabel9 = new JLabel();
			jLabel9.setBounds(new Rectangle(80, 137, 65, 22));
			jLabel9.setText("订单号");
			jLabel8 = new JLabel();
			jLabel8.setBounds(new Rectangle(336, 137, 90, 22));
			jLabel8.setForeground(Color.blue);
			jLabel8.setText("进出仓标志");
			jLabel7 = new JLabel();
			jLabel7.setBounds(new Rectangle(336, 81, 90, 22));
			jLabel7.setForeground(Color.blue);
			jLabel7.setText("仓单类型");
			jLabel6 = new JLabel();
			jLabel6.setBounds(new Rectangle(80, 313, 65, 22));
			jLabel6.setText("仓单有效期");
			jLabel6.setForeground(Color.blue);
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(336, 108, 90, 22));
			jLabel5.setText("海关代码");
			jLabel5.setForeground(Color.blue);
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(80, 80, 65, 22));
			jLabel4.setForeground(Color.blue);
			jLabel4.setText("帐册编号");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(336, 52, 90, 22));
			jLabel3.setForeground(Color.blue);
			jLabel3.setText("仓库代码");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(80, 52, 65, 22));
			jLabel.setForeground(Color.blue);
			jLabel.setText("仓单号");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(getJJToolBarBar(), null);
			jPanel.add(jLabel, null);
			jPanel.add(getJTextField(), null);
			jPanel.add(jLabel3, null);
			jPanel.add(getJComboBox(), null);
			jPanel.add(jLabel4, null);
			jPanel.add(getJTextField1(), null);
			jPanel.add(jLabel5, null);
			jPanel.add(getJComboBox1(), null);
			jPanel.add(jLabel6, null);
			jPanel.add(getJCalendarComboBox(), null);
			jPanel.add(jLabel7, null);
			jPanel.add(getJComboBox2(), null);
			jPanel.add(jLabel8, null);
			jPanel.add(getJComboBox3(), null);
			jPanel.add(jLabel9, null);
			jPanel.add(getJTextField2(), null);
			jPanel.add(jLabel10, null);
			jPanel.add(getJTextField3(), null);
			jPanel.add(jLabel11, null);
			jPanel.add(getJTextField4(), null);
			jPanel.add(jLabel12, null);
			jPanel.add(getJComboBox4(), null);
			jPanel.add(jLabel14, null);
			jPanel.add(jLabel15, null);
			jPanel.add(getJTextField5(), null);
			jPanel.add(jLabel16, null);
			jPanel.add(getJTextField6(), null);
			jPanel.add(jLabel17, null);
			jPanel.add(getJTextField7(), null);
			jPanel.add(jLabel18, null);
			jPanel.add(getJTextField8(), null);
			jPanel.add(jLabel19, null);
			jPanel.add(getJComboBox7(), null);
			jPanel.add(jLabel20, null);
			jPanel.add(getJTextField9(), null);
			jPanel.add(jLabel21, null);
			jPanel.add(getJTextField10(), null);
			jPanel.add(getJButton12(), null);
			jPanel.add(getJButton13(), null);
			jPanel.add(jLabel23, null);
			jPanel.add(getJComboBox6(), null);
			jPanel.add(getJComboBox8(), null);
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
			jPanel1.add(getJJToolBarBar1(), BorderLayout.NORTH);
			jPanel1.add(getJScrollPane(), BorderLayout.CENTER);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jJToolBarBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJJToolBarBar() {
		if (jJToolBarBar == null) {
			jJToolBarBar = new JToolBar();
			jJToolBarBar.setBounds(new Rectangle(1, 3, 685, 29));
			jJToolBarBar.add(getJButton1());
			jJToolBarBar.add(getJButton());
			jJToolBarBar.add(getJButton2());
			jJToolBarBar.add(getJButton14());
			jJToolBarBar.add(getJButton3());
		}
		return jJToolBarBar;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (btnSaveHead == null) {
			btnSaveHead = new JButton();
			btnSaveHead.setText("保存");
			btnSaveHead.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!checkData()) {
						return;
					}
					String id = storageBill.getId();
					if (storageBill.getId() != null) {
						storageBill = storageBillAction.findStorageBillByID(
								new Request(CommonVars.getCurrUser()),
								storageBill.getId());
					}
					fillData();
					System.out.println(storageBill.getDelivery());
					System.out.println("aaaaaaaaaaaaaaaaaa");
					storageBill = (StorageBill) storageBillAction
							.saveOrUpdateObject(new Request(CommonVars
									.getCurrUser()), storageBill);
					System.out.println(storageBill.getDelivery());
					if (id == null) {
						fathertableModel.addRow(storageBill);
					} else {
						fathertableModel.updateRow(storageBill);
					}
					dataState = DataState.BROWSE;
					setState();
				}
			});
		}
		return btnSaveHead;
	}

	/**
	 * 设置状态
	 */
	private void setState() {

		if (storageBill == null) {
			storageBill = new StorageBill();
		}
		this.tfBillNo.setEditable(tfBillNo.getText() == null
				|| tfBillNo.getText().equals(""));
		tfEmsNo.setEditable(dataState != DataState.BROWSE);
		tfManualNo.setEditable(dataState != DataState.BROWSE);
		tfPlanNo.setEditable(dataState != DataState.BROWSE);
		tfOrderNo.setEditable(dataState != DataState.BROWSE);
		tfGrossWeight.setEditable(dataState != DataState.BROWSE);
		tfPackCount.setEditable(dataState != DataState.BROWSE);
		tfNetWeight.setEditable(dataState != DataState.BROWSE);
		tfInName.setEditable(dataState != DataState.BROWSE);
		tfOutName.setEditable(dataState != DataState.BROWSE);
		jButton12.setEnabled(dataState != DataState.BROWSE);
		jButton13.setEnabled(dataState != DataState.BROWSE);
		cbbValidDate.setEnabled(dataState != DataState.BROWSE);

		cbbWareHouseCode.setEnabled(dataState != DataState.BROWSE);
		cbbBillType.setEnabled(dataState != DataState.BROWSE);
		cbbCustomsCode.setEnabled(dataState != DataState.BROWSE);
		// jComboBox3.setEnabled(dataState != DataState.BROWSE);
		cbbWrap.setEnabled(dataState != DataState.BROWSE);
		cbbSupplierCd.setEnabled(dataState != DataState.BROWSE);
		jComboBox7.setEnabled(dataState != DataState.BROWSE);
		cbbCorrOwnerCode.setEnabled(dataState != DataState.BROWSE);
		btnEditHead.setEnabled(dataState == DataState.BROWSE
				&& (storageBill.getEffective() == null || !storageBill
						.getEffective()));// 修改
		btnSaveHead.setEnabled(dataState != DataState.BROWSE);// 保存
		btnEffect
				.setEnabled((storageBill.getEffective() == null || !storageBill
						.getEffective())
						&& dataState == DataState.BROWSE);// 生效
		String tc = (storageBill.getDelivery() == null || storageBill
				.getDelivery().getDeclareState() == null) ? DeclareState.APPLY_POR
				: storageBill.getDelivery().getDeclareState();
		boolean sd = !tc.equals(DeclareState.WAIT_EAA)
				&& !tc.equals(DeclareState.PROCESS_EXE);
		btnReEffect
				.setEnabled((!(storageBill.getEffective() == null || !storageBill
						.getEffective()))
						&& (storageBill.getDelivery() == null
								|| storageBill.getDelivery().getEffective() == null || !storageBill
								.getDelivery().getEffective()) && sd);// 回卷
		// －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		jButton4.setEnabled(storageBill.getEffective() == null
				|| !storageBill.getEffective());
		btnEditAfter.setEnabled(storageBill.getEffective() == null
				|| !storageBill.getEffective());
		btnDeleteAfter.setEnabled(storageBill.getEffective() == null
				|| !storageBill.getEffective());
		btnAddBefer.setEnabled(storageBill.getEffective() == null
				|| !storageBill.getEffective());
		btnEditBefer.setEnabled(storageBill.getEffective() == null
				|| !storageBill.getEffective());
		btnDeleteBefore.setEnabled(storageBill.getEffective() == null
				|| !storageBill.getEffective());
		jTabbedPane.setEnabled(dataState != DataState.ADD);

	}

	/**
	 * 检查数据
	 * 
	 * @return
	 */
	private boolean checkData() {
		String billNo = this.tfBillNo.getText();
		if (billNo == null || billNo.equals("")) {
			JOptionPane.showMessageDialog(DgBaseStorage.this, "仓单号必需填写！",
					"提示！", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (cbbBillType.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(DgBaseStorage.this, "仓单类型必需填写！",
					"提示！", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (cbbIoFlag.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(DgBaseStorage.this, "仓单进出口标志必需填写！",
					"提示！", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (cbbValidDate.getDate() == null) {
			JOptionPane.showMessageDialog(DgBaseStorage.this, "仓单有效期必需填写！",
					"提示！", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (storageBill.getId() == null) {
			List list = storageBillAction.findStorageBill(new Request(
					CommonVars.getCurrUser()), billNo);
			if (!list.isEmpty()) {
				JOptionPane.showMessageDialog(DgBaseStorage.this,
						"仓单号重复，请重新填写！", "提示！", JOptionPane.WARNING_MESSAGE);
				return false;
			}
		}
		// if (tfBillCount.getText() != null
		// && !tfBillCount.getText().trim().equals("")) {
		// try {
		// Integer.parseInt(tfBillCount.getText().trim());
		// } catch (Exception e) {
		// JOptionPane.showMessageDialog(DgDelivery.this, "仓单数必需填写有效整数 ！",
		// "提示！", JOptionPane.WARNING_MESSAGE);
		// return false;
		// }
		// }
		// if (jTextField1.getText() != null
		// && !jTextField1.getText().trim().equals("")) {
		// try {
		// Integer.parseInt(jTextField1.getText().trim());
		// } catch (Exception e) {
		// JOptionPane.showMessageDialog(DgDelivery.this,
		// "该车载货件数必需填写有效整数 ！", "提示！", JOptionPane.WARNING_MESSAGE);
		// return false;
		// }
		// }
		Double nw = 0.0;
		Double gw = 0.0;
		if (tfNetWeight.getText() != null
				&& !tfNetWeight.getText().trim().equals("")) {
			try {
				gw = Double.parseDouble(tfNetWeight.getText().trim());
			} catch (Exception e) {
				JOptionPane.showMessageDialog(DgBaseStorage.this,
						"净重必需填写有效数字 ！", "提示！", JOptionPane.WARNING_MESSAGE);
				return false;
			}
		}
		if (tfGrossWeight.getText() != null
				&& !tfGrossWeight.getText().trim().equals("")) {
			try {
				nw = Double.parseDouble(tfGrossWeight.getText().trim());
			} catch (Exception e) {
				JOptionPane.showMessageDialog(DgBaseStorage.this,
						"毛重必需填写有效数字 ！", "提示！", JOptionPane.WARNING_MESSAGE);
				return false;
			}
		}

		if (gw - nw > 0) {
			JOptionPane.showMessageDialog(DgBaseStorage.this, "毛重必需大于等于净重 ！",
					"提示！", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (btnEditHead == null) {
			btnEditHead = new JButton();
			btnEditHead.setText("修改");
			btnEditHead.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dataState = DataState.EDIT;
					setState();
				}
			});
		}
		return btnEditHead;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (btnEffect == null) {
			btnEffect = new JButton();
			btnEffect.setText("生效");
			btnEffect.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (dataState != DataState.BROWSE) {
						JOptionPane.showMessageDialog(DgBaseStorage.this,
								"请先保存数据！", "提示！", JOptionPane.WARNING_MESSAGE);
						return;
					}
					// if (!checkData()) {
					// return;
					// }
					List lsit = storageBillAction
							.findStorageBillAfterForDelivery(new Request(
									CommonVars.getCurrUser()), storageBill);
					if (lsit == null || lsit.size() == 0 || lsit.get(0) == null) {
						JOptionPane.showMessageDialog(DgBaseStorage.this,
								"仓单表体为空，不能生效！", "提示！",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					String ret = storageBillAction.checkStorageBill(
							new Request(CommonVars.getCurrUser()), storageBill);
					if (ret != null && !ret.trim().equals("")) {
						JOptionPane.showMessageDialog(DgBaseStorage.this,
								"不能生效仓单，以下是原因：\n" + ret, "提示！",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					if (storageBill.getId() != null) {
						storageBill = storageBillAction.findStorageBillByID(
								new Request(CommonVars.getCurrUser()),
								storageBill.getId());
					}
					storageBill.setEffective(true);
					storageBill = (StorageBill) storageBillAction
							.saveOrUpdateObject(new Request(CommonVars
									.getCurrUser()), storageBill);
					fathertableModel.updateRow(storageBill);
					dataState = DataState.BROWSE;
					setState();
				}
			});

		}
		return btnEffect;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton3() {
		if (btnCacelHaed == null) {
			btnCacelHaed = new JButton();
			btnCacelHaed.setText("关闭");
			btnCacelHaed.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnCacelHaed;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField() {
		if (tfBillNo == null) {
			tfBillNo = new JTextField();
			tfBillNo.setBounds(new Rectangle(147, 52, 167, 22));
			tfBillNo.addFocusListener(new FocusListener() {
				public void focusGained(final FocusEvent e) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							((JTextField) e.getSource()).selectAll();
						}
					});
				}

				public void focusLost(FocusEvent e) {
				}
			});
		}
		return tfBillNo;
	}

	/**
	 * This method initializes jJToolBarBar1
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJJToolBarBar1() {
		if (jJToolBarBar1 == null) {
			jLabel1 = new JLabel();
			jLabel1
					.setText("                                              --------(归并后商品)");
			jLabel1.setForeground(Color.blue);
			jJToolBarBar1 = new JToolBar();
			jJToolBarBar1.add(getJButton4());
			jJToolBarBar1.add(getJButton11());
			jJToolBarBar1.add(getJButton5());
			jJToolBarBar1.add(getJButton6());
			jJToolBarBar1.add(getJButton7());
			jJToolBarBar1.add(jLabel1);
		}
		return jJToolBarBar1;
	}

	/**
	 * This method initializes jButton4
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new JButton();
			jButton4.setText("新增");
			jButton4.setVisible(false);
			jButton4.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List addList = getStorageBillAfter();
					if (addList == null) {
						return;
					} else {
						List alist = new ArrayList();
						Integer max = storageBillAction
								.findMaxStorageBillAfterSeqNo(new Request(
										CommonVars.getCurrUser()), storageBill);
						for (int i = 0; i < addList.size(); i++) {
							++max;
							BlsTenInnerMerge sbill = (BlsTenInnerMerge) addList
									.get(i);
							StorageBillAfter after = new StorageBillAfter();
							after.setStorageBill(storageBill);
							after.setCodeTS(sbill.getComplex());
							after.setName(sbill.getName());
							after.setModel(sbill.getSpec());
							after.setSeqNo(max);
							alist.add(after);
						}
						if (storageBill.getId() != null) {
							storageBill = storageBillAction
									.findStorageBillByID(new Request(CommonVars
											.getCurrUser()), storageBill
											.getId());
						}
						storageBillAction.saveOrUpdateObjects(new Request(
								CommonVars.getCurrUser()), alist);
						refreshTable();
					}

				}
			});
		}
		return jButton4;
	}

	/**
	 * 取得仓单信息
	 * 
	 * @return
	 */
	public List getStorageBillAfter() {
		List list = new Vector();
		list.add(new JTableListColumn("归并序号", "seqNum", 100, Integer.class));
		list.add(new JTableListColumn("报关名称", "name", 100));
		list.add(new JTableListColumn("商品规格", "spec", 100));
		list.add(new JTableListColumn("十位商品编码", "complex.code", 100));
		list.add(new JTableListColumn("报关单位", "complex.firstUnit.name", 100));
		// list.add(addColumn("第一法定数量",
		// "bcsTenInnerMerge.legalAmount", 100));
		list
				.add(new JTableListColumn("第二法定单位", "complex.secondUnit.name",
						100));

		DgCommonQuery.setTableColumns(list);
		List slist = storageBillAction.findStorageBillAfterForDeliveryAdd(
				new Request(CommonVars.getCurrUser()), storageBill);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(slist);
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.setTitle("请选择！");
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	/**
	 * 取得仓单信息
	 * 
	 * @return
	 */
	public List getStorageBillBefore(String title) {
		List list = new Vector();
		list.add(new JTableListColumn("料号", "materiel.ptNo", 100));
		list.add(new JTableListColumn("商品编码", "materiel.complex.code", 100));
		list.add(new JTableListColumn("工厂名称", "materiel.factoryName", 100));
		list.add(new JTableListColumn("规格", "materiel.factorySpec", 100));
		list.add(new JTableListColumn("企业单位", "materiel.calUnit.name", 80));
		// list.add(addColumn("单价", "materiel.ptPrice", 80));

		list.add(new JTableListColumn("归并序号", "blsTenInnerMerge.seqNum", 100,
				Integer.class));
		list.add(new JTableListColumn("报关名称", "blsTenInnerMerge.name", 100));
		list.add(new JTableListColumn("商品规格", "blsTenInnerMerge.spec", 100));
		list.add(new JTableListColumn("十位商品编码",
				"blsTenInnerMerge.complex.code", 100));
		list.add(new JTableListColumn("报关单位", "blsTenInnerMerge.comUnit.name",
				50));
		list.add(new JTableListColumn("第一法定单位",
				"blsTenInnerMerge.complex.firstUnit.name", 100));
		// list.add(addColumn("第一法定数量",
		// "blsTenInnerMerge.legalAmount", 100));
		list.add(new JTableListColumn("第二法定单位",
				"blsTenInnerMerge.complex.secondUnit.name", 100));

		DgCommonQuery.setTableColumns(list);
		List slist = storageBillAction.findStorageBillBeforeForDeliveryAdd(
				new Request(CommonVars.getCurrUser()), storageBill);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(slist);
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.setTitle(title);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	/**
	 * 取得仓单信息
	 * 
	 * @return
	 */
	public List getStorageBillAfterForExport(String title, final boolean isFilt) {
		List list = new Vector();
		list.add(new JTableListColumn("流水号", "seqNo", 70)); //
		list.add(new JTableListColumn("归并序号", "seqNum", 70)); //
		list.add(new JTableListColumn("报关单号", "entryID", 150));
		list.add(new JTableListColumn("报关单序号", "entryGNo", 80));
		list.add(new JTableListColumn("入仓单号", "corrBillNo", 80));
		// list.add(new JTableListColumn("已出仓", "isOut", 70)); //
		list.add(new JTableListColumn("申报数量", "qty", 100));
		list.add(new JTableListColumn("剩余数量", "remainingQuantity", 100));
		list.add(new JTableListColumn("帐册序号", "contrItem", 100));
		list.add(new JTableListColumn("商品编码", "codeTS.code", 100));
		list.add(new JTableListColumn("商品名称", "name", 100));
		list.add(new JTableListColumn("型号规格", "model", 100));
		list.add(new JTableListColumn("申报单位", "unit.name", 100));
		list.add(new JTableListColumn("申报单价", "unitPrice", 100));
		list.add(new JTableListColumn("申报总价", "totalP", 100));
		list.add(new JTableListColumn("币制", "curr.name", 100));
		list.add(new JTableListColumn("原产国", "originCountry.name", 100));
		list.add(new JTableListColumn("贸易方式", "tradeModel.name", 100));
		list.add(new JTableListColumn("成效方式", "transModel.name", 100));
		list.add(new JTableListColumn("报关单申报时间", "apprTime", 180));
		list.add(new JTableListColumn("第一法定数量", "qty1", 80));
		list.add(new JTableListColumn("第二法定数量", "qty2", 80));
		list.add(new JTableListColumn("企业内部编号", "copGNo", 100));
		list.add(new JTableListColumn("载货清单", "tafName", 70)); //  

		DgCommonQuery.setTableColumns(list);
		List slist = storageBillAction.findBillAfterNotOut(new Request(
				CommonVars.getCurrUser()));
		System.out.println("slist="+slist.size());
		List newlist = new ArrayList();
		for (int i = 0; i < slist.size(); i++) {
			StorageBillAfter sba = (StorageBillAfter) slist.get(i);
			String corrBillNo = sba.getStorageBill().getBillNo();
			Integer seqNum = sba.getSeqNum();
			// Integer seqNo = sba.getSeqNo();
			Double RemainingQuantity = this.storageBillAction
					.findRemainingQuantity(
							new Request(CommonVars.getCurrUser()), corrBillNo,seqNum);
			Double qty = sba.getQty();

			Double Remain = qty - RemainingQuantity;
			if (isFilt) {
				if (Remain >0){
					sba.setCorrBillNo(corrBillNo);
					sba.setRemainingQuantity(Remain);
					newlist.add(sba);
				}
//				if (Math.abs(Remain) < 0.0000001) {
//					slist.remove(sba);
//					i--;
//				}
			}else{
				sba.setCorrBillNo(corrBillNo);
				sba.setRemainingQuantity(Remain);
				newlist.add(sba);
			}
		}
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(newlist);
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.setTitle(title);
		// dgCommonQuery.getJTable().getColumnModel().getColumn(4).setCellRenderer(
		// new TableCheckBoxRender());
		dgCommonQuery.setVisible(true);

		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	/**
	 * This method initializes jButton5
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton5() {
		if (btnEditAfter == null) {
			btnEditAfter = new JButton();
			btnEditAfter.setText("修改");
			btnEditAfter.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (aftertableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgBaseStorage.this,
								"请选择要修改的数据！", "提示！",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					DgStorageBillAfter dg = new DgStorageBillAfter();
					dg.setDataState(DataState.EDIT);
					dg.setFathertableModel(aftertableModel);
					dg.setStorageBillAfter((StorageBillAfter) aftertableModel
							.getCurrentRow());
					int inout = 0;
					if (DgBaseStorage.this instanceof DgExportStorage) {
						inout = 1;
					}
					dg.inOutFlag = inout;
					dg.setVisible(true);

				}
			});
		}
		return btnEditAfter;
	}

	/**
	 * This method initializes jButton6
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton6() {
		if (btnDeleteAfter == null) {
			btnDeleteAfter = new JButton();
			btnDeleteAfter.setText("删除");
			btnDeleteAfter
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (aftertableModel.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(
										DgBaseStorage.this, "请选择要删除的数据！",
										"提示！", JOptionPane.WARNING_MESSAGE);
								return;
							}
							if (JOptionPane.showConfirmDialog(
									DgBaseStorage.this, "你确定要删除数据吗?", "提示！",
									JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
								return;
							}
							StorageBillAfter storageBill = (StorageBillAfter) aftertableModel
									.getCurrentRow();
							if (DgBaseStorage.this instanceof DgImportStorage) {
								storageBillAction.deleteStorageBillAfter(
										new Request(CommonVars.getCurrUser()),
										storageBill);
								aftertableModel.deleteRow(storageBill);
								setState();
							} else if (DgBaseStorage.this instanceof DgExportStorage) {
								storageBillAction
										.deleteStorageBillAfterByExport(
												new Request(CommonVars
														.getCurrUser()),
												storageBill);
								aftertableModel.deleteRow(storageBill);
								setState();
							}
							refreshTable();

						}
					});
		}
		return btnDeleteAfter;
	}

	/**
	 * This method initializes jButton7
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton7() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText("关闭");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					dispose();

				}
			});
		}
		return btnCancel;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JFooterScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JFooterScrollPane();
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	protected JFooterTable getJTable() {
		if (tbAfter == null) {
			tbAfter = new JFooterTable();
			tbAfter.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					StorageBillAfter af = (StorageBillAfter) aftertableModel
							.getCurrentRow();
					if (af != null) {
						if (e.getClickCount() == 1) {
							List list = storageBillAction
									.findStorageBillBeforeForDelivery(
											new Request(CommonVars
													.getCurrUser()), af);
							initTableBefore(list);

						} else {
							if (aftertableModel.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(
										DgBaseStorage.this, "请选择要修改的数据！",
										"提示！", JOptionPane.WARNING_MESSAGE);
								return;
							}
							DgStorageBillAfter dg = new DgStorageBillAfter();
							dg.setDataState(DataState.BROWSE);
							dg.setFathertableModel(aftertableModel);
							dg
									.setStorageBillAfter((StorageBillAfter) aftertableModel
											.getCurrentRow());
							int inout = 0;
							if (DgBaseStorage.this instanceof DgExportStorage) {
								inout = 1;
							}
							dg.inOutFlag = inout;
							dg.setVisible(true);
						}
					} else {
						initTableBefore(new ArrayList());
					}

				}
			});
		}
		return tbAfter;
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setDividerSize(4);
			jSplitPane.setTopComponent(getJPanel1());
			jSplitPane.setBottomComponent(getJPanel3());
			jSplitPane.setDividerLocation(200);
		}
		return jSplitPane;
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
			jPanel2.add(getJSplitPane(), BorderLayout.CENTER);
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
			jPanel3.add(getJJToolBarBar2(), BorderLayout.NORTH);
			jPanel3.add(getJScrollPane1(), BorderLayout.CENTER);
		}
		return jPanel3;
	}

	/**
	 * This method initializes jJToolBarBar2
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJJToolBarBar2() {
		if (jJToolBarBar2 == null) {
			jLabel2 = new JLabel();
			jLabel2
					.setText("                                                    ---------(归并前商品)");
			jLabel2.setForeground(Color.blue);
			jJToolBarBar2 = new JToolBar();
			jJToolBarBar2.add(getJButton8());
			jJToolBarBar2.add(getJButton9());
			jJToolBarBar2.add(getJButton10());
			jJToolBarBar2.add(jLabel2);
		}
		return jJToolBarBar2;
	}

	/**
	 * This method initializes jButton8
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton8() {
		if (btnAddBefer == null) {
			btnAddBefer = new JButton();
			btnAddBefer.setText("新增");
			btnAddBefer.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (DgBaseStorage.this instanceof DgImportStorage) {
						List bList = getStorageBillBefore("新增－－来自内部归并！");
						storageBillAction.addStorageBillBefore(new Request(
								CommonVars.getCurrUser()), bList, storageBill);
						refreshTable();
					} else if (DgBaseStorage.this instanceof DgExportStorage) {
						boolean isFilt = true;
						if (JOptionPane
								.showConfirmDialog(
										DgBaseStorage.this,
										"是否对查询出来的剩余数量为0的进仓单内容进行过滤\r\n如果选\"是\"是过滤，选\"否\"不过滤",
										"提示", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
							isFilt = false;
						}
						List bList = getStorageBillAfterForExport(
								"新增－－来自进口仓单表体！", isFilt);
						addStorageBillAfterForAfter(bList);
					}

				}
			});
		}
		return btnAddBefer;
	}

	private void addStorageBillBefore(List bList) {
		Map mapss = new HashMap();
		List alist = storageBillAction.findStorageBillAfterForDelivery(
				new Request(CommonVars.getCurrUser()), storageBill);
		for (int i = 0; i < alist.size(); i++) {
			StorageBillAfter aft = (StorageBillAfter) alist.get(i);
			if (aft.getCodeTS() == null) {
				continue;
			}
			if (aft.getSeqNum() == null) {
				continue;
			}
			String str = aft.getCodeTS().getCode() + "/"
					+ aft.getSeqNum().toString();
			mapss.put(str, aft);
		}
		if (bList == null) {
			return;
		} else {// 13592772769
			Map<String, BlsTenInnerMerge> map = new HashMap();
			Integer max = storageBillAction.findMaxStorageBillAfterSeqNo(
					new Request(CommonVars.getCurrUser()), storageBill);
			if (max + bList.size() > 20) {
				JOptionPane.showMessageDialog(DgBaseStorage.this,
						"该仓单已经走过20项商品，请重新选择！", "提示！",
						JOptionPane.WARNING_MESSAGE);
				return;
			}
			Map nomap = new HashMap();
			List hasList = new ArrayList();
			List headList = new ArrayList();
			for (int i = 0; i < bList.size(); i++) {
				BlsInnerMerge inner = (BlsInnerMerge) bList.get(i);
				Materiel m = inner.getMateriel();
				if (m == null) {
					continue;
				}
				BlsTenInnerMerge teninner = inner.getBlsTenInnerMerge();
				if (teninner == null || teninner.getComplex() == null) {
					continue;
				}
				if (teninner.getSeqNum() == null) {
					continue;
				}
				String keystr = teninner.getComplex().getCode() + "/"
						+ teninner.getSeqNum().toString();
				StorageBillAfter afts = (StorageBillAfter) mapss.get(keystr);
				if (afts != null) {
					System.out.println("old -----------------");
					// List arlist = storageBillAction
					// .findStorageBillBeforeForDelivery(new Request(
					// CommonVars.getCurrUser()), afts);
					StorageBillBefore be = new StorageBillBefore();
					be.setPartNo(m);
					be.setStorageBillAfter(afts);
					hasList.add(be);
				} else {
					List ttlist = (List) nomap.get(keystr);
					if (ttlist == null) {
						ttlist = new ArrayList();
						afts = new StorageBillAfter();
						afts
								.setCodeTS(inner.getBlsTenInnerMerge()
										.getComplex());
						afts.setStorageBill(storageBill);
						afts.setCodeTS(teninner.getComplex());
						afts.setName(teninner.getName());
						afts.setModel(teninner.getSpec());
						afts.setSeqNum(teninner.getSeqNum());
						++max;
						afts.setSeqNo(max);
						StorageBillBefore bef = new StorageBillBefore();
						bef.setPartNo(m);
						ttlist.add(bef);
						headList.add(afts);
						nomap.put(keystr, ttlist);
					} else {
						StorageBillBefore bef = new StorageBillBefore();
						bef.setPartNo(m);
						ttlist.add(bef);
					}
				}
			}

			List saveList = new ArrayList();
			saveList.add(hasList);
			saveList.add(headList);
			saveList.add(nomap);
			List clist = storageBillAction.saveStorageBillAfterAndBefore(
					new Request(CommonVars.getCurrUser()), saveList);
			refreshTable();
		}

	}

	private void addStorageBillAfterForAfter(List bList) {
		if (bList == null) {
			return;
		}
		Map mapss = new HashMap();
		// List alist = storageBillAction.findStorageBillAfterForDelivery(
		// new Request(CommonVars.getCurrUser()), storageBill);
		//		
		Integer max = storageBillAction.findMaxStorageBillAfterSeqNo(
				new Request(CommonVars.getCurrUser()), storageBill);
		if (max + bList.size() > 20) {
			JOptionPane.showMessageDialog(DgBaseStorage.this,
					"该仓单已经超过20项商品，请重新选择！", "提示！", JOptionPane.WARNING_MESSAGE);
			return;
		}
		for (int i = 0; i < bList.size(); i++) {
			StorageBillAfter aft = (StorageBillAfter) bList.get(i);
			StorageBillAfter naft = new StorageBillAfter();
			try {
				BeanUtilsBean.getInstance().copyProperties(naft, aft);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			aft.setIsOut(true);
			naft.setId(null);
			naft.setCorrBillGNo(aft.getSeqNo());
			naft.setCorrBillNo(aft.getStorageBill().getBillNo());
			naft.setStorageBill(storageBill);
			naft.setIsOut(false);
			++max;
			naft.setSeqNo(max);
			naft.setEntryGNo(null);
			naft.setEntryID(null);
			System.out.println("aaaaaaaaa");
			mapss.put(aft, naft);
		}
		storageBillAction.saveBillAfterForExport(new Request(CommonVars
				.getCurrUser()), mapss);
		refreshTable();

	}

	private void addStorageBillAfter(List addList) {
		List alist = new ArrayList();
		Integer max = storageBillAction.findMaxStorageBillAfterSeqNo(
				new Request(CommonVars.getCurrUser()), storageBill);
		for (int i = 0; i < addList.size(); i++) {
			++max;
			BlsTenInnerMerge sbill = (BlsTenInnerMerge) addList.get(i);
			StorageBillAfter after = new StorageBillAfter();
			after.setStorageBill(storageBill);
			after.setCodeTS(sbill.getComplex());
			after.setName(sbill.getName());
			after.setModel(sbill.getSpec());
			after.setSeqNo(max);
			alist.add(after);
		}
		if (storageBill.getId() != null) {
			storageBill = storageBillAction.findStorageBillByID(new Request(
					CommonVars.getCurrUser()), storageBill.getId());
		}
		addList = storageBillAction.saveOrUpdateObjects(new Request(CommonVars
				.getCurrUser()), alist);
		aftertableModel.addRows(alist);
	}

	/**
	 * This method initializes jButton9
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton9() {
		if (btnEditBefer == null) {
			btnEditBefer = new JButton();
			btnEditBefer.setText("修改");
			btnEditBefer.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (beforetableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgBaseStorage.this,
								"请选择要修改的数据！", "提示！",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					DgStorageBillBefore dg = new DgStorageBillBefore();
					dg.setDataSate(DataState.EDIT);
					dg.setFathertableModel(beforetableModel);
					dg
							.setStorageBillBefore((StorageBillBefore) beforetableModel
									.getCurrentRow());
					dg.setVisible(true);

				}
			});
		}
		return btnEditBefer;
	}

	/**
	 * This method initializes jButton10
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton10() {
		if (btnDeleteBefore == null) {
			btnDeleteBefore = new JButton();
			btnDeleteBefore.setText("删除");
			btnDeleteBefore
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (beforetableModel.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(
										DgBaseStorage.this, "请选择要删除的数据！",
										"提示！", JOptionPane.WARNING_MESSAGE);
								return;
							}
							List dellist = beforetableModel.getCurrentRows();
							storageBillAction.deleteObjects(new Request(
									CommonVars.getCurrUser()), dellist);
							beforetableModel.deleteRows(dellist);
						}
					});
		}
		return btnDeleteBefore;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox() {
		if (cbbWareHouseCode == null) {
			cbbWareHouseCode = new JComboBox();
			cbbWareHouseCode.setBounds(new Rectangle(427, 51, 133, 22));
			cbbWareHouseCode.setSelectedItem(null);
		}
		return cbbWareHouseCode;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField1() {
		if (tfEmsNo == null) {
			tfEmsNo = new JTextField();
			tfEmsNo.setBounds(new Rectangle(147, 80, 167, 22));
			tfEmsNo.addFocusListener(new FocusListener() {
				public void focusGained(final FocusEvent e) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							((JTextField) e.getSource()).selectAll();
						}
					});
				}

				public void focusLost(FocusEvent e) {
				}
			});
		}
		return tfEmsNo;
	}

	/**
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox1() {
		if (cbbBillType == null) {
			cbbBillType = new JComboBox();
			cbbBillType.setBounds(new Rectangle(427, 80, 133, 22));
			cbbBillType.setSelectedItem(null);
		}
		return cbbBillType;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getJCalendarComboBox() {
		if (cbbValidDate == null) {
			cbbValidDate = new JCalendarComboBox();
			cbbValidDate.setBounds(new Rectangle(149, 313, 167, 22));
			// jCalendarComboBox.setDate(null);
		}
		return cbbValidDate;
	}

	/**
	 * This method initializes jComboBox2
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox2() {
		if (cbbCustomsCode == null) {
			cbbCustomsCode = new JComboBox();
			cbbCustomsCode.setBounds(new Rectangle(427, 108, 133, 22));
			cbbCustomsCode.setSelectedItem(null);
		}
		return cbbCustomsCode;
	}

	/**
	 * This method initializes jComboBox3
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox3() {
		if (cbbIoFlag == null) {
			cbbIoFlag = new JComboBox();
			cbbIoFlag.setBounds(new Rectangle(427, 137, 133, 22));
			cbbIoFlag.setSelectedItem(null);
		}
		return cbbIoFlag;
	}

	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField2() {
		if (tfManualNo == null) {
			tfManualNo = new JTextField();
			tfManualNo.setBounds(new Rectangle(147, 108, 167, 22));
			tfManualNo.addFocusListener(new FocusListener() {
				public void focusGained(final FocusEvent e) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							((JTextField) e.getSource()).selectAll();
						}
					});
				}

				public void focusLost(FocusEvent e) {
				}
			});
		}
		return tfManualNo;
	}

	/**
	 * This method initializes jTextField3
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField3() {
		if (tfPlanNo == null) {
			tfPlanNo = new JTextField();
			tfPlanNo.setBounds(new Rectangle(147, 167, 167, 22));
			tfPlanNo.addFocusListener(new FocusListener() {
				public void focusGained(final FocusEvent e) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							((JTextField) e.getSource()).selectAll();
						}
					});
				}

				public void focusLost(FocusEvent e) {
				}
			});
		}
		return tfPlanNo;
	}

	/**
	 * This method initializes jTextField4
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField4() {
		if (tfOrderNo == null) {
			tfOrderNo = new JTextField();
			tfOrderNo.setBounds(new Rectangle(147, 137, 167, 22));
			tfOrderNo.addFocusListener(new FocusListener() {
				public void focusGained(final FocusEvent e) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							((JTextField) e.getSource()).selectAll();
						}
					});
				}

				public void focusLost(FocusEvent e) {
				}
			});
		}
		return tfOrderNo;
	}

	/**
	 * This method initializes jComboBox4
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox4() {
		if (cbbWrap == null) {
			cbbWrap = new JComboBox();
			cbbWrap.setBounds(new Rectangle(427, 167, 133, 22));
			cbbWrap.setSelectedItem(null);
		}
		return cbbWrap;
	}

	/**
	 * This method initializes jTextField5
	 * 
	 * @return javax.swing.JTextField
	 */
	private JCustomFormattedTextField getJTextField5() {
		if (tfGrossWeight == null) {
			tfGrossWeight = new JCustomFormattedTextField();
			tfGrossWeight.setBounds(new Rectangle(147, 224, 167, 22));
			CustomFormattedTextFieldUtils.setFormatterFactory(tfGrossWeight, 4);
			tfGrossWeight.addFocusListener(new FocusListener() {
				public void focusGained(final FocusEvent e) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							((JTextField) e.getSource()).selectAll();
						}
					});
				}

				public void focusLost(FocusEvent e) {
				}
			});
		}
		return tfGrossWeight;
	}

	/**
	 * This method initializes jTextField6
	 * 
	 * @return javax.swing.JTextField
	 */
	private JCustomFormattedTextField getJTextField6() {
		if (tfPackCount == null) {
			tfPackCount = new JCustomFormattedTextField();
			tfPackCount.setBounds(new Rectangle(427, 313, 133, 22));
			CustomFormattedTextFieldUtils.setFormatterFactory(tfPackCount, 0);
			tfPackCount.addFocusListener(new FocusListener() {
				public void focusGained(final FocusEvent e) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							((JTextField) e.getSource()).selectAll();
						}
					});
				}

				public void focusLost(FocusEvent e) {
				}
			});
		}
		return tfPackCount;
	}

	/**
	 * This method initializes jTextField7
	 * 
	 * @return javax.swing.JTextField
	 */
	private JCustomFormattedTextField getJTextField7() {
		if (tfNetWeight == null) {
			tfNetWeight = new JCustomFormattedTextField();
			tfNetWeight.setBounds(new Rectangle(147, 197, 167, 22));
			CustomFormattedTextFieldUtils.setFormatterFactory(tfNetWeight, 4);
			tfNetWeight.addFocusListener(new FocusListener() {
				public void focusGained(final FocusEvent e) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							((JTextField) e.getSource()).selectAll();
						}
					});
				}

				public void focusLost(FocusEvent e) {
				}
			});
		}
		return tfNetWeight;
	}

	/**
	 * This method initializes jTextField8
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField8() {
		if (tfInName == null) {
			tfInName = new JTextField();
			tfInName.setBounds(new Rectangle(148, 254, 145, 22));
			tfInName.addFocusListener(new FocusListener() {
				public void focusGained(final FocusEvent e) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							((JTextField) e.getSource()).selectAll();
						}
					});
				}

				public void focusLost(FocusEvent e) {
				}
			});
		}
		return tfInName;
	}

	/**
	 * This method initializes jComboBox7
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox7() {
		if (jComboBox7 == null) {
			jComboBox7 = new JComboBox();
			jComboBox7.setBounds(new Rectangle(427, 196, 133, 22));
			jComboBox7.setSelectedItem(null);
		}
		return jComboBox7;
	}

	/**
	 * This method initializes jTextField9
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField9() {
		if (tfOutName == null) {
			tfOutName = new JTextField();
			tfOutName.setBounds(new Rectangle(148, 283, 145, 22));
			tfOutName.addFocusListener(new FocusListener() {
				public void focusGained(final FocusEvent e) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							((JTextField) e.getSource()).selectAll();
						}
					});
				}

				public void focusLost(FocusEvent e) {
				}
			});
		}
		return tfOutName;
	}

	/**
	 * This method initializes jTextField10
	 * 
	 * @return javax.swing.JTextField
	 */
	private JCustomFormattedTextField getJTextField10() {
		if (tfItemsCount == null) {
			tfItemsCount = new JCustomFormattedTextField();
			tfItemsCount.setBounds(new Rectangle(427, 283, 133, 22));
			tfItemsCount.setEditable(false);
			CustomFormattedTextFieldUtils.setFormatterFactory(tfItemsCount, 0);
		}
		return tfItemsCount;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JFooterScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JFooterScrollPane();
			jScrollPane1.setViewportView(getJTable1());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jTable1
	 * 
	 * @return javax.swing.JTable
	 */
	private JFooterTable getJTable1() {
		if (tbBefore == null) {
			tbBefore = new JFooterTable();
			tbBefore.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 2) {

						if (beforetableModel.getCurrentRow() == null) {
							JOptionPane.showMessageDialog(DgBaseStorage.this,
									"请选择要修改的数据！", "提示！",
									JOptionPane.WARNING_MESSAGE);
							return;
						}
						DgStorageBillBefore dg = new DgStorageBillBefore();
						dg.setDataSate(DataState.BROWSE);
						dg.setFathertableModel(beforetableModel);
						dg
								.setStorageBillBefore((StorageBillBefore) beforetableModel
										.getCurrentRow());
						dg.setVisible(true);

					}
				}
			});
		}
		return tbBefore;
	}

	/**
	 * This method initializes jButton12
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton12() {
		if (jButton12 == null) {
			jButton12 = new JButton();
			jButton12.setBounds(new Rectangle(292, 254, 22, 22));
			jButton12.setText("...");
			jButton12.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Brief brief = (Brief) CommonQuery.getInstance()
							.getCustomBrief(null);
					if (brief != null) {
						tfInName.setText(brief.getName());
					}
				}
			});
		}
		return jButton12;
	}

	/**
	 * This method initializes jButton13
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton13() {
		if (jButton13 == null) {
			jButton13 = new JButton();
			jButton13.setBounds(new Rectangle(292, 283, 22, 22));
			jButton13.setText("...");
			jButton13.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Brief brief = (Brief) CommonQuery.getInstance()
							.getCustomBrief(null);
					if (brief != null) {
						tfOutName.setText(brief.getName());
					}
				}
			});
		}
		return jButton13;
	}

	/**
	 * This method initializes jButton14
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton14() {
		if (btnReEffect == null) {
			btnReEffect = new JButton();
			btnReEffect.setText("回卷");
			btnReEffect.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (dataState != DataState.BROWSE) {
						JOptionPane.showMessageDialog(DgBaseStorage.this,
								"请先保存数据！", "提示！", JOptionPane.WARNING_MESSAGE);
						return;
					}
					if (storageBill.getId() != null) {
						storageBill = storageBillAction.findStorageBillByID(
								new Request(CommonVars.getCurrUser()),
								storageBill.getId());
					}
					if (storageBill.getDelivery() != null) {
						if ((storageBill.getEffective() == null || !storageBill
								.getEffective())
								|| storageBill.getDelivery().getDeclareState()
										.equals(DeclareState.WAIT_EAA)) {
							fathertableModel.updateRow(storageBill);
							setState();
							return;
						}
					} else {
						if ((storageBill.getEffective() == null || !storageBill
								.getEffective())) {
							fathertableModel.updateRow(storageBill);
							setState();
							return;
						}
					}
					storageBill.setEffective(false);
					storageBill = (StorageBill) storageBillAction
							.saveOrUpdateObject(new Request(CommonVars
									.getCurrUser()), storageBill);
					fathertableModel.updateRow(storageBill);
					dataState = DataState.EDIT;
					setState();
				}
			});
		}
		return btnReEffect;
	}

	/**
	 * This method initializes jComboBox6
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox6() {
		if (cbbSupplierCd == null) {
			cbbSupplierCd = new JComboBox();
			cbbSupplierCd.setBounds(new Rectangle(427, 225, 133, 22));
			cbbSupplierCd.setSelectedItem(null);
		}
		return cbbSupplierCd;
	}

	/**
	 * This method initializes jComboBox8
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox8() {
		if (cbbCorrOwnerCode == null) {
			cbbCorrOwnerCode = new JComboBox();
			cbbCorrOwnerCode.setBounds(new Rectangle(427, 254, 133, 22));
			cbbCorrOwnerCode.setSelectedItem(null);
		}
		return cbbCorrOwnerCode;
	}

	private void initTableAfter(final List list) {
		aftertableModel = new JTableListModel(tbAfter, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("流水号", "seqNo", 70)); //
						list.add(addColumn("归并序号", "seqNum", 70)); //
						list.add(addColumn("载货清单", "tafName", 70)); //  
						list.add(addColumn("已出仓", "isOut", 70)); //  
						list.add(addColumn("申报数量", "qty", 100));
						list.add(addColumn("企业内部编号", "copGNo", 100));
						list.add(addColumn("商品编码", "codeTS.code", 100));
						list.add(addColumn("商品名称", "name", 100));
						list.add(addColumn("型号规格", "model", 100));
						list.add(addColumn("申报单位", "unit.name", 100));
						list.add(addColumn("申报单价", "unitPrice", 100));
						list.add(addColumn("申报总价", "totalP", 100));
						list.add(addColumn("币制", "curr.name", 100));
						list.add(addColumn("帐册序号", "contrItem", 100));
						list.add(addColumn("原产国", "originCountry.name", 100));
						list.add(addColumn("贸易方式", "tradeModel.name", 100));
						list.add(addColumn("成效方式", "transModel.name", 100));
						list.add(addColumn("报关单申报时间", "apprTime", 180));
						list.add(addColumn("报关单号", "entryID", 150));
						list.add(addColumn("报关单序号", "entryGNo", 80));
						list.add(addColumn("入仓单号", "corrBillNo", 80));
						list.add(addColumn("第一法定数量", "qty1", 80));
						list.add(addColumn("第二法定数量", "qty2", 80));
						return list;
					}
				});
		tbAfter
				.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		aftertableModel.clearFooterTypeInfo();
		aftertableModel.addFooterTypeInfo(new TableFooterType(0,
				TableFooterType.CONSTANT, "合计"));
		aftertableModel.addFooterTypeInfo(new TableFooterType(5,
				TableFooterType.SUM, ""));
		aftertableModel.setColumnsFractionCount(5, 4);
		aftertableModel.addFooterTypeInfo(new TableFooterType(12,
				TableFooterType.SUM, ""));
		aftertableModel.setColumnsFractionCount(12, 2);
		aftertableModel.addFooterTypeInfo(new TableFooterType(22,
				TableFooterType.SUM, ""));
		aftertableModel.setColumnsFractionCount(22, 4);
		aftertableModel.addFooterTypeInfo(new TableFooterType(23,
				TableFooterType.SUM, ""));
		aftertableModel.setColumnsFractionCount(23, 4);
		tbAfter.getColumnModel().getColumn(4).setCellRenderer(
				new TableCheckBoxRender());
	}

	private void initTableBefore(final List list) {
		beforetableModel = new JTableListModel(tbBefore, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("料号", "partNo.ptNo", 70)); //  
						list.add(addColumn("送货传票", "subpoenaNo", 100));
						list.add(addColumn("内部英文名称", "partNameE", 100));
						list.add(addColumn("内部中文名称", "partNameC", 100));
						list.add(addColumn("颜色", "clor", 100));
						list.add(addColumn("数量", "detailQty", 100));
						return list;
					}
				});
		tbBefore
				.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		beforetableModel.clearFooterTypeInfo();
		beforetableModel.addFooterTypeInfo(new TableFooterType(0,
				TableFooterType.CONSTANT, "合计"));
		beforetableModel.addFooterTypeInfo(new TableFooterType(6,
				TableFooterType.SUM, ""));
		beforetableModel.setColumnsFractionCount(6, 4);
	}

	/**
	 * This method initializes jButton11
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton11() {
		if (btnBrowseAfter == null) {
			btnBrowseAfter = new JButton();
			btnBrowseAfter.setText("浏览");
			btnBrowseAfter
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (aftertableModel.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(
										DgBaseStorage.this, "请选择要修改的数据！",
										"提示！", JOptionPane.WARNING_MESSAGE);
								return;
							}

							DgStorageBillAfter dg = new DgStorageBillAfter();
							dg.setDataState(DataState.BROWSE);
							dg.setFathertableModel(aftertableModel);
							dg
									.setStorageBillAfter((StorageBillAfter) aftertableModel
											.getCurrentRow());
							int inout = 0;
							if (DgBaseStorage.this instanceof DgExportStorage) {
								inout = 1;
							}
							dg.inOutFlag = inout;
							dg.setVisible(true);

						}
					});
		}
		return btnBrowseAfter;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
