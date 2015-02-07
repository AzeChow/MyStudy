package com.bestway.bls.client.storagebill;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.custombase.action.CustomBaseAction;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.parametercode.Wrap;
import com.bestway.bls.action.BlsInOutStockBillAction;
import com.bestway.bls.entity.BillImpExpFlag;
import com.bestway.bls.entity.BlsInOutStockSwichParameterSet;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Rectangle;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;

/**
 * 转仓单参数设置界面
 * 
 * @author hw
 * 
 */
public class DgBlsSwitchParameterSet extends JDialogBase {

	private JPanel jPanel = null;

	private JButton btnOK = null;

	private JButton btnCanel = null;

	private JLabel jLabel = null;

	private JTextField tfBooksNo = null;

	private JLabel jLabel1 = null;

	private JComboBox cbbImpExpPort = null;

	private JLabel jLabel2 = null;

	private JComboBox cbbCustoms = null;

	private JLabel jLabel3 = null;

	private JComboBox cbbBillType = null;

	private JLabel jLabel4 = null;

	private JComboBox cbbPackagingType = null;

	private JLabel jLabel5 = null;

	private JComboBox cbbIOFlag = null;

	/**
	 * 海关基础资料服务器端接口
	 */
	private CustomBaseAction customBaseAction = null;

	/**
	 * 进出仓单据转仓单实体类
	 */
	private BlsInOutStockSwichParameterSet bosp = null; // @jve:decl-index=0:

	private JTableListModel tableModel = null;

	public JTableListModel getTableModel() {
		return tableModel;
	}

	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
	}

	public BlsInOutStockBillAction blsInOutStockBillAction = (BlsInOutStockBillAction) CommonVars
			.getApplicationContext().getBean("blsInOutStockBillAction"); // @jve:decl-index=0:

	private boolean isAdd = true;

	public boolean isAdd() {
		return isAdd;
	}

	public void setAdd(boolean isAdd) {
		this.isAdd = isAdd;
	}

	/**
	 * 状态控制标识符
	 */
	private int dataState = DataState.ADD;

	/**
	 * 获取状态控制标识符
	 * 
	 * @return
	 */
	public int getDataState() {
		return dataState;
	}

	/**
	 * 设置状态控制标识符
	 * 
	 * @param dataState
	 */
	public void setDataState(int dataState) {
		this.dataState = dataState;
	}

	/**
	 * 构造方法
	 * 
	 */
	public DgBlsSwitchParameterSet() {
		super();
		initialize();

		customBaseAction = (CustomBaseAction) CommonVars
				.getApplicationContext().getBean("customBaseAction");
		initComponents();

	}

	/**
	 * 初始化组建
	 */
	private void initComponents() {

		// 初始化进出仓类型
		this.cbbIOFlag.removeAllItems();
		this.cbbIOFlag.addItem(new ItemProperty(BillImpExpFlag.IMPORT, "进仓"));
		this.cbbIOFlag.addItem(new ItemProperty(BillImpExpFlag.EXPORT, "出仓"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbIOFlag);
		this.cbbIOFlag.setRenderer(CustomBaseRender.getInstance().getRender());
		this.cbbIOFlag.setSelectedIndex(-1);

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

		// 初始化申报海关
		this.cbbCustoms
				.setModel(CustomBaseModel.getInstance().getCustomModel());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbCustoms);
		this.cbbCustoms.setRenderer(CustomBaseRender.getInstance().getRender());
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
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(490, 325));
		this.setContentPane(getJPanel());
		this.setTitle("转仓单参数设置");

	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(288, 144, 62, 18));
			jLabel5.setText("进出仓类型");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(31, 144, 62, 18));
			jLabel4.setText("包装种类");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(288, 92, 62, 18));
			jLabel3.setText("仓单类型");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(30, 92, 62, 18));
			jLabel2.setText("申报海关");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(288, 41, 62, 18));
			jLabel1.setText("进出口岸");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(30, 41, 62, 18));
			jLabel.setText("帐册编号");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(getBtnOK(), null);
			jPanel.add(getBtnCanel(), null);
			jPanel.add(jLabel, null);
			jPanel.add(getTfBooksNo(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(getCbbImpExpPort(), null);
			jPanel.add(jLabel2, null);
			jPanel.add(getCbbCustoms(), null);
			jPanel.add(jLabel3, null);
			jPanel.add(getCbbBillType(), null);
			jPanel.add(jLabel4, null);
			jPanel.add(getCbbPackagingType(), null);
			jPanel.add(jLabel5, null);
			jPanel.add(getCbbIOFlag(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes btnOK
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOK() {
		if (btnOK == null) {
			btnOK = new JButton();
			btnOK.setBounds(new Rectangle(170, 255, 59, 22));
			btnOK.setText("确定");
			btnOK.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!checkData()) {
						return;
					}
					if (dataState == DataState.ADD) {
						addData();
					} else {
						modifyData();
					}
					dispose();
				}
			});
		}
		return btnOK;
	}

	/**
	 * 检查必填数据
	 * 
	 * @return
	 */
	private boolean checkData() {
		if (cbbIOFlag.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(DgBlsSwitchParameterSet.this,
					"进出仓标志必需选择！", "提示！", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		String booksNo = this.tfBooksNo.getText();
		if (booksNo == null || booksNo.equals("")) {
			JOptionPane.showMessageDialog(DgBlsSwitchParameterSet.this,
					"帐册编号必需填写！", "提示！", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (cbbPackagingType.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(DgBlsSwitchParameterSet.this,
					"包装种类必需选择！", "提示！", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (cbbCustoms.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(DgBlsSwitchParameterSet.this,
					"申报海关必需选择！", "提示！", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (cbbImpExpPort.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(DgBlsSwitchParameterSet.this,
					"进出口岸必需选择！", "提示！", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (cbbPackagingType.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(DgBlsSwitchParameterSet.this,
					"进出仓类型必需选择！", "提示！", JOptionPane.WARNING_MESSAGE);
			return false;
		}

		return true;
	}

	/**
	 * 填充数据
	 */
	public void fillData(BlsInOutStockSwichParameterSet obj) {
		ItemProperty item = (ItemProperty) this.cbbIOFlag.getSelectedItem();
		ItemProperty item2 = (ItemProperty) cbbBillType.getSelectedItem();
		obj.setDeclarationCustoms((Customs) this.cbbCustoms.getSelectedItem());
		obj.setCustoms((Customs) this.cbbImpExpPort.getSelectedItem());
		obj.setIoFlag(item.getCode());
		if(item2!=null){			
			obj.setBillType(item2.getCode());
		}else{
			obj.setBillType(null);
		}
		obj.setWrapType((Wrap) this.cbbPackagingType.getSelectedItem());
		obj.setEmsNo(this.tfBooksNo.getText());
	}

	/**
	 * 向父类表体力增加数据以及保存新增数据
	 */
	public void addData() {
		BlsInOutStockSwichParameterSet obj = new BlsInOutStockSwichParameterSet();
		fillData(obj);
		obj = blsInOutStockBillAction.saveBlsSwitchParameterSet(new Request(
				CommonVars.getCurrUser()), obj);
		this.tableModel.addRow(obj);
	}

	/**
	 * 修改父类表体数据以及保存修改数据
	 */
	public void modifyData() {
		bosp = (BlsInOutStockSwichParameterSet) this.tableModel.getCurrentRow();
		fillData(bosp);
		bosp = blsInOutStockBillAction.saveBlsSwitchParameterSet(new Request(
				CommonVars.getCurrUser()), bosp);
		tableModel.updateRow(bosp);
	}

	/**
	 * 显示数据
	 */
	public void showData() {
		bosp = (BlsInOutStockSwichParameterSet) this.tableModel.getCurrentRow();
		this.tfBooksNo.setText(bosp.getEmsNo());
		String btype = bosp.getBillType();
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
		this.cbbCustoms.setSelectedItem((Customs) bosp.getDeclarationCustoms());
		this.cbbImpExpPort.setSelectedItem((Customs) bosp.getCustoms());
		this.cbbPackagingType.setSelectedItem((Wrap) bosp.getWrapType());
		if (bosp.getIoFlag() != null) {
			cbbIOFlag.setSelectedIndex(ItemProperty.getIndexByCode(this.bosp
					.getIoFlag(), cbbIOFlag));
		} else {
			cbbIOFlag.setSelectedIndex(-1);
		}
	}

	/**
	 * This method initializes btnCanel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCanel() {
		if (btnCanel == null) {
			btnCanel = new JButton();
			btnCanel.setBounds(new Rectangle(272, 255, 59, 22));
			btnCanel.setText("取消");
			btnCanel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnCanel;
	}

	/**
	 * This method initializes tfBooksNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfBooksNo() {
		if (tfBooksNo == null) {
			tfBooksNo = new JTextField();
			tfBooksNo.setBounds(new Rectangle(96, 39, 98, 22));
		}
		return tfBooksNo;
	}

	/**
	 * This method initializes cbbImpExpPort
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbImpExpPort() {
		if (cbbImpExpPort == null) {
			cbbImpExpPort = new JComboBox();
			cbbImpExpPort.setBounds(new Rectangle(353, 39, 98, 22));
		}
		return cbbImpExpPort;
	}

	/**
	 * This method initializes cbbCustoms
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCustoms() {
		if (cbbCustoms == null) {
			cbbCustoms = new JComboBox();
			cbbCustoms.setBounds(new Rectangle(96, 91, 98, 22));
		}
		return cbbCustoms;
	}

	/**
	 * This method initializes cbbBillType
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbBillType() {
		if (cbbBillType == null) {
			cbbBillType = new JComboBox();
			cbbBillType.setBounds(new Rectangle(354, 90, 98, 22));
		}
		return cbbBillType;
	}

	/**
	 * This method initializes cbbPackagingType
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbPackagingType() {
		if (cbbPackagingType == null) {
			cbbPackagingType = new JComboBox();
			cbbPackagingType.setBounds(new Rectangle(96, 143, 98, 22));
		}
		return cbbPackagingType;
	}

	/**
	 * This method initializes cbbIOFlag
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbIOFlag() {
		if (cbbIOFlag == null) {
			cbbIOFlag = new JComboBox();
			cbbIOFlag.setBounds(new Rectangle(354, 141, 98, 22));
		}
		return cbbIOFlag;
	}

	/**
	 * 设置显示和组建状态
	 */
	public void setVisibles(boolean f) {
		if (f) {
			showData();
			super.setVisible(f);
		}
	}
} // @jve:decl-index=0:visual-constraint="10,10"
