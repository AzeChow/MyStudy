/*
 * Created on 2004-6-25
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.materialbase;

import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.custombase.action.CustomBaseAction;
import com.bestway.bcus.custombase.entity.basecode.Brief;
import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.countrycode.PortLin;
import com.bestway.bcus.custombase.entity.countrycode.PreDock;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.bcus.custombase.entity.parametercode.Transf;
import com.bestway.bcus.custombase.entity.parametercode.Wrap;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.KeyAdapterControl;

/**
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
/**
 * 基础代码客户/供应商新增或修改界面
 */
public class DgScmCoc extends JDialogBase {
	private JPanel jContentPane = null;

	private JButton btnSave = null;

	private JTextField tfCocCode = null;

	private JTextField tfCocName = null;

	private JButton btnCancel = null;

	private JTableListModel tableModel = null;
	/**
	 * 是否添加参数
	 */
	private boolean isAdd = true;
	/**
	 * 客户供应商资料
	 */
	private ScmCoc currentScmCoc = null;

	private CustomBaseAction customBaseAction = null;
	/**
	 * 料件管理操作接口
	 */
	private MaterialManageAction materialManageAction = null;

	private JCheckBox cbCustomer = null;

	private JCheckBox cbTransferFactoryOut = null;

	private JCheckBox cbTransferFactoryIn = null;

	private JCheckBox cbStraightIn = null;

	private JCheckBox cbStraightOut = null;

	private JCheckBox cbHomeBuy = null;

	private JCheckBox cbManufacturer = null;

	private JCheckBox cbCollaborater = null;

	private JButton btnQueryBrief = null;

	private JTextField tfBrief = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JPanel jPanel2 = null;

	private JPanel jPanel3 = null;
	/**
	 * 存放海关注册公司的资料的实体类实例
	 */
	private Brief brief;

	private JLabel jLabel3 = null;

	private JTextField tfReferredToAs = null;

	private JCheckBox cbIsBusinessUnits = null;

	private JLabel jLabel4 = null;

	private JLabel jLabel5 = null;

	private JLabel jLabel6 = null;

	private JLabel jLabel7 = null;

	private JLabel jLabel8 = null;

	private JComboBox cbbArrivedInCountry = null;

	private JComboBox cbbArrivedInPort = null;

	private JComboBox cbbModeOfTransport = null;

	private JComboBox cbbStatesProductionAndSales = null;

	private JComboBox cbbExportPort = null;

	private JComboBox cbbTheirCustoms = null;

	private JLabel jLabel9 = null;

	private JLabel jLabel10 = null;

	private JLabel jLabel11 = null;

	private JLabel jLabel12 = null;

	private JTextField tfGBCode = null;

	private JTextField tfContact = null;

	private JTextField tfTel = null;

	private JTextField tfAddress = null;

	private JLabel jLabel13 = null;

	private JTextField tfBankAndCount = null;

	private JLabel jLabel14 = null;

	private JLabel jLabel15 = null;

	private JComboBox cbbPredock = null;

	private JLabel jLabel16 = null;

	private JLabel jLabel17 = null;

	private JComboBox cbbTradeMode = null;

	private JComboBox cbbWrapType = null;

	private JLabel jLabel18 = null;

	private JLabel jLabel19 = null;

	private JTextField tfEngName = null;

	private JTextField tfFax = null;

	private JLabel jLabel20 = null;

	private JCustomFormattedTextField tfTransportationTime = null;

	private JLabel jLabel21 = null;

	private JCustomFormattedTextField tfDeliveryDistance = null;

	private DefaultFormatterFactory defaultFormatterFactory = null;
	/**
	 * 格式化TextField所用参数
	 */
	private NumberFormatter numberFormatter = null;

	private JCheckBox cbIsLeiXiao = null;

	/**
	 * 返回是否添加参数
	 * 
	 * @return Returns the isAdd.
	 */
	public boolean isAdd() {
		return isAdd;
	}

	/**
	 * 设置是否添加参数
	 * 
	 * @param isAdd
	 *            The isAdd to set.
	 */
	public void setAdd(boolean isAdd) {
		this.isAdd = isAdd;
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
	 * 构造函数 This method initializes
	 * 
	 */
	public DgScmCoc() {
		super();
		customBaseAction = (CustomBaseAction) CommonVars
				.getApplicationContext().getBean("customBaseAction");
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		initialize();
	}

	/**
	 * 设置数据填充
	 */
	private void Window() {
		if (currentScmCoc != null) {
			this.tfCocCode.setText(currentScmCoc.getCode());
//			this.tfCocCode.setEditable(false);
			this.tfCocName.setText(currentScmCoc.getName());
//			this.tfCocName.setEditable(false);
			this.tfReferredToAs.setText(currentScmCoc.getFname());
			this.cbCustomer
					.setSelected(currentScmCoc.getIsCustomer() == null ? false
							: currentScmCoc.getIsCustomer().booleanValue());
			this.cbCollaborater
					.setSelected(currentScmCoc.getIsCollaborater() == null ? false
							: currentScmCoc.getIsCollaborater().booleanValue());
			this.cbManufacturer
					.setSelected(currentScmCoc.getIsManufacturer() == null ? false
							: currentScmCoc.getIsManufacturer().booleanValue());
			this.cbTransferFactoryOut.setSelected(currentScmCoc
					.getIsTransferFactoryOut() == null ? false : currentScmCoc
					.getIsTransferFactoryOut().booleanValue());
			this.cbTransferFactoryIn.setSelected(currentScmCoc
					.getIsTransferFactoryIn() == null ? false : currentScmCoc
					.getIsTransferFactoryIn().booleanValue());
			this.cbStraightOut
					.setSelected(currentScmCoc.getIsStraightOut() == null ? false
							: currentScmCoc.getIsStraightOut().booleanValue());
			this.cbIsLeiXiao
			.setSelected(currentScmCoc.getIsLeiXiao() == null ? false
					: currentScmCoc.getIsLeiXiao().booleanValue());
			this.cbStraightIn
					.setSelected(currentScmCoc.getIsStraightIn() == null ? false
							: currentScmCoc.getIsStraightIn().booleanValue());
			this.cbHomeBuy
					.setSelected(currentScmCoc.getIsHomeBuy() == null ? false
							: currentScmCoc.getIsHomeBuy().booleanValue());
			if (currentScmCoc.getIsWork() != null
					&& currentScmCoc.getIsWork().equals(new Boolean(true))) {
				cbIsBusinessUnits.setSelected(true);
			} else {
				cbIsBusinessUnits.setSelected(false);
			}
			if (this.brief != null) {
				this.getTfBrief().setText(brief.getName());
			} else {
				this.getTfBrief().setText("");
			}
			tfGBCode.setText(currentScmCoc.getFlatCode());
			tfContact.setText(currentScmCoc.getLinkMan());
			tfTel.setText(currentScmCoc.getLinkTel());
			tfAddress.setText(currentScmCoc.getAddre());
			tfBankAndCount.setText(currentScmCoc.getBank());
			this.tfEngName.setText(currentScmCoc.getEngName());
			this.tfFax.setText(currentScmCoc.getFax());
			if (currentScmCoc.getCountry() != null) {
				cbbArrivedInCountry.setSelectedItem(currentScmCoc.getCountry());
			}
			if (currentScmCoc.getPortLin() != null) {
				cbbArrivedInPort.setSelectedItem(currentScmCoc.getPortLin());
			}
			if (currentScmCoc.getTransf() != null) {
				cbbModeOfTransport.setSelectedItem(currentScmCoc.getTransf());
			}
			if (currentScmCoc.getCountry2() != null) {
				cbbStatesProductionAndSales.setSelectedItem(currentScmCoc
						.getCountry2());
			}
			if (currentScmCoc.getCustoms() != null) {
				cbbExportPort.setSelectedItem(currentScmCoc.getCustoms());
			}
			if (currentScmCoc.getBelongToCustoms() != null) {
				cbbTheirCustoms.setSelectedItem(currentScmCoc
						.getBelongToCustoms());
			}
			if (currentScmCoc.getTrade() != null) {
				this.cbbTradeMode.setSelectedItem(currentScmCoc.getTrade());
			}
			if (currentScmCoc.getWarp() != null) {
				this.cbbWrapType.setSelectedItem(currentScmCoc.getWarp());
			}
			if (currentScmCoc.getPredock() != null) {
				this.cbbPredock.setSelectedItem(currentScmCoc.getPredock());
			}
			if (currentScmCoc.getDeliveryDistance() != null) {
				this.tfDeliveryDistance.setValue(currentScmCoc
						.getDeliveryDistance());
			}
			if (currentScmCoc.getTransportationTime() != null) {
				this.tfTransportationTime.setValue(currentScmCoc
						.getTransportationTime());
			}
		}
	}

	/**
	 * 初始化窗口打开动作 This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setContentPane(getJContentPane());
		this.setTitle("客户/供应商/合作伙伴/经营单位/转厂客户资料编辑");
		this.setSize(583, 513);
		initUI();
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowOpened(java.awt.event.WindowEvent e) {
				if (isAdd == false) {
					if (tableModel.getCurrentRow() != null) {
						currentScmCoc = (ScmCoc) tableModel.getCurrentRow();
						brief = currentScmCoc.getBrief();
					}
					Window();
				}
			}
		});
	}

	/**
	 * 初始化组建
	 */
	private void initUI() {
		// 运抵国
		this.cbbArrivedInCountry.setModel(CustomBaseModel.getInstance()
				.getCountryModel());
		this.cbbArrivedInCountry.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbArrivedInCountry);
		cbbArrivedInCountry.setSelectedIndex(-1);
		// 产销国
		this.cbbStatesProductionAndSales.setModel(CustomBaseModel.getInstance()
				.getCountryModel());
		this.cbbStatesProductionAndSales.setRenderer(CustomBaseRender
				.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbStatesProductionAndSales);
		cbbStatesProductionAndSales.setSelectedIndex(-1);
		// 指运港
		this.cbbArrivedInPort.setModel(CustomBaseModel.getInstance()
				.getPortLinModel());
		this.cbbArrivedInPort.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbArrivedInPort);
		cbbArrivedInPort.setSelectedIndex(-1);
		// 初始化出口口岸
		this.cbbExportPort.setModel(CustomBaseModel.getInstance()
				.getCustomModel());
		this.cbbExportPort.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbExportPort);
		cbbExportPort.setSelectedIndex(-1);
		// 初始化运输方式
		this.cbbModeOfTransport.setModel(CustomBaseModel.getInstance()
				.getTransfModel());
		this.cbbModeOfTransport.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbModeOfTransport);
		cbbModeOfTransport.setSelectedIndex(-1);
		// 初始化所属海关
		this.cbbTheirCustoms.setModel(CustomBaseModel.getInstance()
				.getCustomModel());
		this.cbbTheirCustoms.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbTheirCustoms);
		cbbTheirCustoms.setSelectedIndex(-1);
		// 初始化码头
		this.cbbPredock.setModel(CustomBaseModel.getInstance()
				.getPreDockModel());
		this.cbbPredock.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbPredock);
		cbbPredock.setSelectedIndex(-1);
		// 初始化贸易方式
		this.cbbTradeMode.setModel(CustomBaseModel.getInstance()
				.getTradeModel());
		this.cbbTradeMode.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbTradeMode);
		cbbTradeMode.setSelectedIndex(-1);
		// 初始化包装种类
		List listwarp = customBaseAction.findWrap();
		this.cbbWrapType.setModel(new DefaultComboBoxModel(listwarp.toArray()));
		this.cbbWrapType.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbWrapType, "code", "name");
		// this.jComboBox8
		// .setModel(CustomBaseModel.getInstance().getCustomModel());
		// this.jComboBox8.setRenderer(CustomBaseRender.getInstance().getRender());
		// CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
		// this.jComboBox8);
		cbbWrapType.setSelectedIndex(-1);
	}

	/**
	 * 
	 * This method initializes jContentPane
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getBtnSave(), null);
			jContentPane.add(getBtnCancel(), null);
			jContentPane.add(getJPanel(), null);
			jContentPane.add(getJPanel1(), null);
			jContentPane.add(getJPanel2(), null);
			jContentPane.add(getJPanel3(), null);
		}
		return jContentPane;
	}

	/**
	 * 填充数据
	 * 
	 * @param coc
	 */
	private void fillData(ScmCoc coc) {
		if (CommonVars.getCurrUser().getCompany() != null) {
			coc.setCompany(CommonVars.getCurrUser().getCompany());
		}
		coc.setCode(tfCocCode.getText().trim());
		coc.setName(tfCocName.getText().trim());
		coc.setFname(this.tfReferredToAs.getText().trim());
		coc.setEngName(this.tfEngName.getText().trim());
		coc.setFax(this.tfFax.getText().trim());
		coc.setIsCollaborater(new Boolean(this.cbCollaborater.isSelected()));
		coc.setIsCustomer(new Boolean(this.cbCustomer.isSelected()));
		coc.setIsManufacturer(new Boolean(this.cbManufacturer.isSelected()));
		coc.setIsWork(new Boolean(this.cbIsBusinessUnits.isSelected()));
		coc.setIsTransferFactoryOut(new Boolean(this.cbTransferFactoryOut
				.isSelected()));
		coc.setIsTransferFactoryIn(new Boolean(this.cbTransferFactoryIn
				.isSelected()));
		coc.setIsStraightOut(new Boolean(this.cbStraightOut.isSelected()));
		coc.setIsLeiXiao(new Boolean(this.cbIsLeiXiao.isSelected()));
		coc.setIsStraightIn(new Boolean(this.cbStraightIn.isSelected()));
		coc.setIsHomeBuy(new Boolean(this.cbHomeBuy.isSelected()));
		coc.setBrief(brief);
		coc.setFlatCode(tfGBCode.getText());
		coc.setLinkMan(tfContact.getText());
		coc.setLinkTel(tfTel.getText());
		coc.setAddre(tfAddress.getText());
		coc.setBank(tfBankAndCount.getText());
		coc.setTransportationTime(tfTransportationTime.getValue() == null ? 0.0
				: Double.valueOf(tfTransportationTime.getValue().toString()));
		coc.setDeliveryDistance(tfDeliveryDistance.getValue() == null ? 0.0
				: Double.valueOf(tfDeliveryDistance.getValue().toString()));
		if (cbbArrivedInCountry.getSelectedItem() != null) {
			coc.setCountry((Country) cbbArrivedInCountry.getSelectedItem());
		}
		if (cbbArrivedInPort.getSelectedItem() != null) {
			coc.setPortLin((PortLin) cbbArrivedInPort.getSelectedItem());
		}
		if (cbbModeOfTransport.getSelectedItem() != null) {
			coc.setTransf((Transf) cbbModeOfTransport.getSelectedItem());
		}
		if (cbbStatesProductionAndSales.getSelectedItem() != null) {
			coc.setCountry2((Country) cbbStatesProductionAndSales
					.getSelectedItem());
		}
		if (cbbExportPort.getSelectedItem() != null) {
			coc.setCustoms((Customs) cbbExportPort.getSelectedItem());
		}
		if (cbbTheirCustoms.getSelectedItem() != null) {
			coc.setBelongToCustoms((Customs) cbbTheirCustoms.getSelectedItem());
		}
		if (this.cbbPredock.getSelectedItem() != null) {
			coc.setPredock((PreDock) cbbPredock.getSelectedItem());
		}
		if (this.cbbTradeMode.getSelectedItem() != null) {
			coc.setTrade((Trade) cbbTradeMode.getSelectedItem());
		}
		if (this.cbbWrapType.getSelectedItem() != null) {
			coc.setWarp((Wrap) cbbWrapType.getSelectedItem());
		}
	}

	/**
	 * 保存数据
	 */
	private void saveData() {
		if (isAdd()) {
			ScmCoc coc = new ScmCoc();
			fillData(coc);
			coc = materialManageAction.saveScmCoc(new Request(CommonVars
					.getCurrUser()), coc);
			tableModel.addRow(coc);
			emptyWindowData();
		} else {
			ScmCoc coc = (ScmCoc) tableModel.getCurrentRow();
			fillData(coc);
			coc = materialManageAction.saveScmCoc(new Request(CommonVars
					.getCurrUser()), coc);
			tableModel.updateRow(coc);
			this.dispose();
		}
	}

	/**
	 * 检查数据是否为空
	 * 
	 * @return
	 */
	private boolean checkNull() {
		if (tfCocCode.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(this, "编号不能为空,请输入!", "提示!", 1);
			return true;
		}
		if (tfCocName.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(this, "名称不能为空,请输入!", "提示!", 1);
			return true;
		}
		// 若改动，请先联系我，---xxm
		/*
		 * if (this.jTextField1.getText().length() != 9) {
		 * 
		 * JOptionPane.showMessageDialog(this, "国标代码应该是9位!", "提示!", 1); return
		 * true; }
		 */
		if (this.tfBrief.getText().trim().equals("")
				&& (this.cbTransferFactoryIn.isSelected() || this.cbTransferFactoryOut
						.isSelected())) {
			JOptionPane.showMessageDialog(this, "关务海关注册公司不能为空!!", "提示!",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}

		if (!this.cbCollaborater.isSelected()
				&& !this.cbCustomer.isSelected()
				&& !this.cbManufacturer.isSelected()
				&& !this.cbIsBusinessUnits.isSelected()
				// && !this.cbTransferFactoryIn.isSelected()
				// && !this.cbTransferFactoryOut.isSelected()
				&& !this.cbStraightIn.isSelected()
				&& !this.cbStraightOut.isSelected()
				&& !this.cbIsLeiXiao.isSelected()
				&& !this.cbHomeBuy.isSelected()) {
			JOptionPane.showMessageDialog(this, "类别选择框中至少有一个被选中!!", "提示!",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		return false;
	}

	/**
	 * 检查数据是否重复
	 * 
	 * @return
	 */
	private boolean checkMultiple() {
		ScmCoc coc = materialManageAction.findScmCocByCode(new Request(
				CommonVars.getCurrUser()), tfCocCode.getText().trim());
		if (coc != null) {
			if (!isAdd) {
				if (!coc.getId().equals(currentScmCoc.getId())) {
					JOptionPane.showMessageDialog(this, "编号不能重复,请重新输入!", "提示!",
							1);
					return true;
				}
			} else {
				JOptionPane.showMessageDialog(this, "编号不能重复,请重新输入!", "提示!", 1);
				return true;
			}
		}
		return false;
	}

	/**
	 * 组建数据清空
	 */
	private void emptyWindowData() {
		this.tfCocCode.setText("");
		this.tfCocName.setText("");
		this.tfReferredToAs.setText("");
		this.tfBrief.setText("");
		this.cbCollaborater.setSelected(false);
		this.cbCustomer.setSelected(false);
		this.cbManufacturer.setSelected(false);
		this.cbTransferFactoryOut.setSelected(false);
		this.cbTransferFactoryIn.setSelected(false);
		this.cbStraightOut.setSelected(false);
		this.cbIsLeiXiao.setSelected(false);
		this.cbStraightIn.setSelected(false);
		this.cbHomeBuy.setSelected(false);
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
			btnSave.setBounds(154, 453, 74, 26);
			btnSave.setMnemonic(java.awt.event.KeyEvent.VK_S);
			btnSave.setText("确定(S)");
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (checkNull()) {
						return;
					}
					if (checkMultiple()) {
						return;
					}
					saveData();
				}
			});
		}
		return btnSave;
	}

	/**
	 * 
	 * This method initializes tfCocCode
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfCocCode() {
		if (tfCocCode == null) {
			tfCocCode = new JTextField();
			tfCocCode.setBounds(83, 15, 170, 20);
		}
		return tfCocCode;
	}

	/**
	 * 
	 * This method initializes tfCocName
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfCocName() {
		if (tfCocName == null) {
			tfCocName = new JTextField();
			tfCocName.setBounds(82, 40, 170, 20);
		}
		return tfCocName;
	}

	/**
	 * 
	 * This method initializes btnCancel
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(331, 453, 74, 26);
			btnCancel.setMnemonic(java.awt.event.KeyEvent.VK_X);
			btnCancel.setText("取消(X)");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgScmCoc.this.dispose();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * 
	 * This method initializes cbCustomer
	 * 
	 * 
	 * 
	 * @return javax.swing.JCheckBox
	 * 
	 */
	private JCheckBox getCbCustomer() {
		if (cbCustomer == null) {
			cbCustomer = new JCheckBox();
			cbCustomer.setText("是否客户 ");
			cbCustomer.setBounds(new Rectangle(279, 12, 79, 26));
			cbCustomer
					.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
		}
		return cbCustomer;
	}

	/**
	 * 
	 * This method initializes cbTransferFactoryOut
	 * 
	 * 
	 * 
	 * @return javax.swing.JCheckBox
	 * 
	 */
	private JCheckBox getCbTransferFactoryOut() {
		if (cbTransferFactoryOut == null) {
			cbTransferFactoryOut = new JCheckBox();
			cbTransferFactoryOut.setText("是否结转出口  ");
			cbTransferFactoryOut.setBounds(new Rectangle(71, 10, 106, 26));
			cbTransferFactoryOut
					.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
			cbTransferFactoryOut
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (cbTransferFactoryOut.isSelected())
								cbCustomer.setSelected(true);
						}
					});
		}
		return cbTransferFactoryOut;
	}

	/**
	 * 
	 * This method initializes cbManufacturer
	 * 
	 * 
	 * 
	 * @return javax.swing.JCheckBox
	 * 
	 */
	private JCheckBox getCbManufacturer() {
		if (cbManufacturer == null) {
			cbManufacturer = new JCheckBox();
			cbManufacturer.setText("是否供应商   ");
			cbManufacturer
					.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
			cbManufacturer.setBounds(new Rectangle(178, 12, 100, 26));
			cbManufacturer
					.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		}
		return cbManufacturer;
	}

	/**
	 * 
	 * This method initializes cbCollaborater
	 * 
	 * 
	 * 
	 * @return javax.swing.JCheckBox
	 * 
	 */
	private JCheckBox getCbCollaborater() {
		if (cbCollaborater == null) {
			cbCollaborater = new JCheckBox();
			cbCollaborater.setText("是否为合作伙伴");
			cbCollaborater.setBounds(new Rectangle(65, 12, 109, 26));
			cbCollaborater
					.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
		}
		return cbCollaborater;
	}

	/**
	 * 
	 * This method initializes btnQueryBrief
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnQueryBrief() {
		if (btnQueryBrief == null) {
			btnQueryBrief = new JButton();
			btnQueryBrief.setBounds(252, 107, 26, 22);
			btnQueryBrief.setText("...");
			btnQueryBrief
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							Brief brief = (Brief) CommonQuery.getInstance()
									.getCustomBrief(DgScmCoc.this.brief);
							if (brief != null) {
								DgScmCoc.this.brief = brief;
								getTfBrief().setText(brief.getName());
							}
						}
					});
		}
		return btnQueryBrief;
	}

	/**
	 * 
	 * This method initializes tfBrief
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfBrief() {
		if (tfBrief == null) {
			tfBrief = new JTextField();
			tfBrief.setBounds(125, 107, 127, 22);
			tfBrief.setEditable(true);
		}
		return tfBrief;
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
			jLabel21 = new JLabel();
			jLabel21.setBounds(new Rectangle(284, 254, 61, 18));
			jLabel21.setText("送货距离");
			jLabel20 = new JLabel();
			jLabel20.setBounds(new Rectangle(21, 206, 49, 18));
			jLabel20.setText("运输耗时");
			jLabel19 = new JLabel();
			jLabel19.setBounds(new Rectangle(282, 85, 64, 22));
			jLabel19.setText("传真号");
			jLabel18 = new JLabel();
			jLabel18.setBounds(new Rectangle(20, 87, 61, 25));
			jLabel18.setText("英文名称");
			jLabel17 = new JLabel();
			jLabel17.setBounds(new Rectangle(283, 229, 62, 20));
			jLabel17.setText("贸易方式");
			jLabel16 = new JLabel();
			jLabel16.setBounds(new Rectangle(21, 252, 53, 20));
			jLabel16.setText("包装种类");
			jLabel15 = new JLabel();
			jLabel15.setBounds(new Rectangle(283, 205, 62, 20));
			jLabel15.setText("码        头");
			jLabel14 = new JLabel();
			jLabel14.setBounds(new Rectangle(21, 228, 53, 20));
			jLabel14.setText("所属海关");
			jLabel13 = new JLabel();
			jLabel12 = new JLabel();
			jLabel11 = new JLabel();
			jLabel10 = new JLabel();
			jLabel9 = new JLabel();
			jLabel8 = new JLabel();
			jLabel7 = new JLabel();
			jLabel6 = new JLabel();
			jLabel5 = new JLabel();
			jLabel4 = new JLabel();
			jLabel3 = new JLabel();
			jPanel = new JPanel();
			javax.swing.JLabel jLabel2 = new JLabel();
			javax.swing.JLabel jLabel1 = new JLabel();
			javax.swing.JLabel jLabel = new JLabel();
			jLabel.setText("编号");
			jLabel.setBounds(20, 17, 24, 20);
			jLabel1.setText("全称");
			jLabel1.setBounds(20, 41, 24, 20);
			jLabel2.setText("关务海关注册公司");
			jLabel2.setBounds(21, 111, 96, 18);
			jPanel.setLayout(null);
			jPanel.setBounds(11, 19, 539, 278);
			jPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null,
					"基本资料",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION,
					new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12),
					new java.awt.Color(51, 51, 51)));
			jLabel3.setBounds(20, 65, 42, 18);
			jLabel3.setText("简称");
			jLabel4.setBounds(21, 131, 49, 22);
			jLabel4.setText("运抵国");
			jLabel5.setBounds(21, 157, 47, 20);
			jLabel5.setText("指运港");
			jLabel6.setBounds(283, 157, 62, 20);
			jLabel6.setText("产销国");
			jLabel7.setBounds(283, 181, 62, 20);
			jLabel7.setText("出口口岸");
			jLabel8.setBounds(21, 179, 50, 22);
			jLabel8.setText("运输方式");
			jLabel9.setBounds(282, 15, 62, 20);
			jLabel9.setText("国标代码");
			jLabel10.setBounds(282, 40, 62, 20);
			jLabel10.setText("联系人");
			jLabel11.setBounds(282, 63, 62, 20);
			jLabel11.setText("联系电话");
			jLabel12.setBounds(283, 109, 62, 20);
			jLabel12.setText("地址");
			jLabel13.setBounds(283, 133, 62, 20);
			jLabel13.setText("银行及帐号");
			jPanel.add(getTfCocCode(), null);
			jPanel.add(getTfCocName(), null);
			jPanel.add(getTfBrief(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(jLabel, null);
			jPanel.add(jLabel2, null);
			jPanel.add(getBtnQueryBrief(), null);
			jPanel.add(jLabel3, null);
			jPanel.add(getTfReferredToAs(), null);
			jPanel.add(jLabel4, null);
			jPanel.add(jLabel5, null);
			jPanel.add(jLabel6, null);
			jPanel.add(jLabel7, null);
			jPanel.add(jLabel8, null);
			jPanel.add(getCbbArrivedInCountry(), null);
			jPanel.add(getCbbArrivedInPort(), null);
			jPanel.add(getCbbModeOfTransport(), null);
			jPanel.add(getCbbStatesProductionAndSales(), null);
			jPanel.add(getCbbExportPort(), null);
			jPanel.add(jLabel9, null);
			jPanel.add(jLabel10, null);
			jPanel.add(jLabel11, null);
			jPanel.add(jLabel12, null);
			jPanel.add(getTfGBCode(), null);
			jPanel.add(getTfContact(), null);
			jPanel.add(getTfTel(), null);
			jPanel.add(getTfAddress(), null);
			jPanel.add(jLabel13, null);
			jPanel.add(getTfBankAndCount(), null);
			jPanel.add(jLabel14, null);
			jPanel.add(getCbbTheirCustoms(), null);
			jPanel.add(jLabel15, null);
			jPanel.add(getJComboBox6(), null);
			jPanel.add(jLabel16, null);
			jPanel.add(jLabel17, null);
			jPanel.add(getJComboBox7(), null);
			jPanel.add(getJComboBox8(), null);
			jPanel.add(jLabel18, null);
			jPanel.add(jLabel19, null);
			jPanel.add(getTfEngName(), null);
			jPanel.add(getTfFax(), null);
			jPanel.add(jLabel20, null);
			jPanel.add(getTfTransportationTime(), null);
			jPanel.add(jLabel21, null);
			jPanel.add(getTfDeliveryDistance(), null);
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
			jPanel1.setLayout(null);
			jPanel1.setBounds(12, 301, 540, 47);
			jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(
					null, "类别选择",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			jPanel1.add(getCbCollaborater(), null);
			jPanel1.add(getCbManufacturer(), null);
			jPanel1.add(getCbCustomer(), null);
			jPanel1.add(getCbIsBusinessUnits(), null);
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
			// jPanel2.setBounds(12, 237, 540, 130);
			jPanel2.setLayout(null);
			jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(
					null, "供应商类别",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			jPanel2.setBounds(new Rectangle(11, 356, 539, 43));
			// jPanel2.add(getCbTransferFactoryIn(), null);
			// jPanel2.add(getCbStraightIn(), null);
			// jPanel2.add(getCbHomeBuy(), null);
			jPanel2.add(getCbTransferFactoryIn(), null);
			jPanel2.add(getCbStraightIn(), null);
			jPanel2.add(getCbHomeBuy(), null);
		}
		return jPanel2;
	}

	/**
	 * 
	 * This method initializes jPanel3
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setLayout(null);
			jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(
					null, "客户类别",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			jPanel3.setBounds(new Rectangle(10, 406, 543, 41));
			// jPanel3.add(getCbTransferFactoryOut(), null);
			// jPanel3.add(getCbStraightOut(), null);
			jPanel3.add(getCbTransferFactoryOut(), null);
			jPanel3.add(getCbStraightOut(), null);
			jPanel3.add(getCbIsLeiXiao(), null);
		}
		return jPanel3;
	}

	/**
	 * This method initializes tfReferredToAs
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfReferredToAs() {
		if (tfReferredToAs == null) {
			tfReferredToAs = new JTextField();
			tfReferredToAs.setBounds(83, 61, 170, 22);
		}
		return tfReferredToAs;
	}

	/**
	 * This method initializes cbIsBusinessUnits
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIsBusinessUnits() {
		if (cbIsBusinessUnits == null) {
			cbIsBusinessUnits = new JCheckBox();
			cbIsBusinessUnits.setText("是否经营单位");
			cbIsBusinessUnits
					.setBounds(new Rectangle(363, 12, 97, 26));
		}
		return cbIsBusinessUnits;
	}

	/**
	 * This method initializes cbbArrivedInCountry
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbArrivedInCountry() {
		if (cbbArrivedInCountry == null) {
			cbbArrivedInCountry = new JComboBox();
			cbbArrivedInCountry.setBounds(84, 131, 170, 22);
			cbbArrivedInCountry.addKeyListener(new java.awt.event.KeyAdapter() {
				@Override
				public void keyPressed(java.awt.event.KeyEvent e) {
					System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxx");
				}
			});
			// jComboBox.addKeyListener(new
			// FocusActionListner(getJTextField3()));
		}
		return cbbArrivedInCountry;
	}

	/**
	 * This method initializes cbbArrivedInPort
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbArrivedInPort() {
		if (cbbArrivedInPort == null) {
			cbbArrivedInPort = new JComboBox();
			cbbArrivedInPort.setBounds(84, 155, 170, 22);
		}
		return cbbArrivedInPort;
	}

	/**
	 * This method initializes cbbModeOfTransport
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbModeOfTransport() {
		if (cbbModeOfTransport == null) {
			cbbModeOfTransport = new JComboBox();
			cbbModeOfTransport.setBounds(84, 179, 170, 22);
		}
		return cbbModeOfTransport;
	}

	/**
	 * This method initializes cbbStatesProductionAndSales
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbStatesProductionAndSales() {
		if (cbbStatesProductionAndSales == null) {
			cbbStatesProductionAndSales = new JComboBox();
			cbbStatesProductionAndSales.setBounds(357, 155, 145, 22);
		}
		return cbbStatesProductionAndSales;
	}

	/**
	 * This method initializes cbbExportPort
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbExportPort() {
		if (cbbExportPort == null) {
			cbbExportPort = new JComboBox();
			cbbExportPort.setBounds(357, 179, 145, 22);
		}
		return cbbExportPort;
	}

	/**
	 * This method initializes tfGBCode
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfGBCode() {
		if (tfGBCode == null) {
			tfGBCode = new JTextField();
			tfGBCode.setBounds(356, 15, 145, 20);
		}
		return tfGBCode;
	}

	/**
	 * This method initializes tfContact
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfContact() {
		if (tfContact == null) {
			tfContact = new JTextField();
			tfContact.setBounds(356, 40, 145, 20);
		}
		return tfContact;
	}

	/**
	 * This method initializes tfTel
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfTel() {
		if (tfTel == null) {
			tfTel = new JTextField();
			tfTel.setBounds(356, 61, 145, 22);
		}
		return tfTel;
	}

	/**
	 * This method initializes tfAddress
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfAddress() {
		if (tfAddress == null) {
			tfAddress = new JTextField();
			tfAddress.setBounds(357, 107, 145, 22);
		}
		return tfAddress;
	}

	/**
	 * This method initializes tfBankAndCount
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfBankAndCount() {
		if (tfBankAndCount == null) {
			tfBankAndCount = new JTextField();
			tfBankAndCount.setBounds(357, 131, 145, 22);
		}
		return tfBankAndCount;
	}

	/**
	 * This method initializes cbStraightOut
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbStraightOut() {
		if (cbStraightOut == null) {
			cbStraightOut = new JCheckBox();
			cbStraightOut.setBounds(new Rectangle(183, 10, 108, 26));
			cbStraightOut.setText("是否直接出口  ");
			cbStraightOut.setHorizontalTextPosition(SwingConstants.RIGHT);
			cbStraightOut
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (cbStraightOut.isSelected())
								cbCustomer.setSelected(true);
						}
					});
		}
		return cbStraightOut;
	}

	/**
	 * This method initializes cbTransferFactoryIn
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbTransferFactoryIn() {
		if (cbTransferFactoryIn == null) {
			cbTransferFactoryIn = new JCheckBox();
			cbTransferFactoryIn.setText("是否结转进口 ");
			cbTransferFactoryIn.setBounds(new Rectangle(73, 11, 114, 26));
			cbTransferFactoryIn.setHorizontalTextPosition(SwingConstants.RIGHT);
			cbTransferFactoryIn
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (cbTransferFactoryIn.isSelected())
								cbManufacturer.setSelected(true);
						}
					});
		}
		return cbTransferFactoryIn;
	}

	/**
	 * This method initializes cbStraightIn
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbStraightIn() {
		if (cbStraightIn == null) {
			cbStraightIn = new JCheckBox();
			cbStraightIn.setBounds(new java.awt.Rectangle(186, 10, 105, 26));
			cbStraightIn.setText("是否直接进口  ");
			cbStraightIn.setHorizontalTextPosition(SwingConstants.RIGHT);
			cbStraightIn.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (cbStraightIn.isSelected())
						cbManufacturer.setSelected(true);
				}
			});
		}
		return cbStraightIn;
	}

	/**
	 * 内部类继承KeyAdapter接收键盘事件的抽象适配器类。此类中的方法为空。此类存在的目的是方便创建侦听器对象。
	 * 扩展keyAdapter
	 * @author ?
	 * 
	 */
	class FocusActionListner extends KeyAdapter {
		Component component;

		FocusActionListner(Component component) {
			this.component = component;
		}

		@Override
		public void keyPressed(KeyEvent e) {
			KeyAdapterControl.setAddListener(true);
			if (e.getKeyCode() == 10) {
				component.requestFocus();
			}
		}
	}

	/**
	 * This method initializes cbHomeBuy
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbHomeBuy() {
		if (cbHomeBuy == null) {
			cbHomeBuy = new JCheckBox();
			cbHomeBuy.setBounds(new java.awt.Rectangle(288, 10, 105, 26));
			cbHomeBuy.setText("是否国内购买  ");
			cbHomeBuy.setHorizontalTextPosition(SwingConstants.RIGHT);
			cbHomeBuy.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (cbHomeBuy.isSelected())
						cbManufacturer.setSelected(true);
				}
			});
		}
		return cbHomeBuy;
	}

	/**
	 * This method initializes cbbTheirCustoms
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbTheirCustoms() {
		if (cbbTheirCustoms == null) {
			cbbTheirCustoms = new JComboBox();
			cbbTheirCustoms.setBounds(new Rectangle(85, 228, 170, 22));
		}
		return cbbTheirCustoms;
	}

	/**
	 * This method initializes jComboBox6
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox6() {
		if (cbbPredock == null) {
			cbbPredock = new JComboBox();
			cbbPredock.setBounds(new Rectangle(357, 203, 145, 22));
		}
		return cbbPredock;
	}

	/**
	 * This method initializes jComboBox7
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox7() {
		if (cbbTradeMode == null) {
			cbbTradeMode = new JComboBox();
			cbbTradeMode.setBounds(new Rectangle(357, 227, 145, 22));
		}
		return cbbTradeMode;
	}

	/**
	 * This method initializes jComboBox8
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox8() {
		if (cbbWrapType == null) {
			cbbWrapType = new JComboBox();
			cbbWrapType.setBounds(new Rectangle(85, 252, 170, 22));
		}
		return cbbWrapType;
	}

	/**
	 * This method initializes jTextField6
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfEngName() {
		if (tfEngName == null) {
			tfEngName = new JTextField();
			tfEngName.setBounds(new Rectangle(84, 85, 168, 23));
		}
		return tfEngName;
	}

	/**
	 * This method initializes jTextField7
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfFax() {
		if (tfFax == null) {
			tfFax = new JTextField();
			tfFax.setBounds(new Rectangle(356, 84, 146, 21));
		}
		return tfFax;
	}

	/**
	 * This method initializes tfTransportationTime
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfTransportationTime() {
		if (tfTransportationTime == null) {
			tfTransportationTime = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			;
			tfTransportationTime.setBounds(new Rectangle(84, 203, 171, 22));
			tfTransportationTime
					.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfTransportationTime;
	}

	private DefaultFormatterFactory getDefaultFormatterFactory() {
		if (defaultFormatterFactory == null) {
			defaultFormatterFactory = new DefaultFormatterFactory();
			defaultFormatterFactory.setDefaultFormatter(getNumberFormatter());
			defaultFormatterFactory.setDisplayFormatter(getNumberFormatter());
			defaultFormatterFactory.setEditFormatter(getNumberFormatter());
		}
		return defaultFormatterFactory;
	}

	private NumberFormatter getNumberFormatter() {
		if (numberFormatter == null) {
			numberFormatter = new NumberFormatter();
		}
		return numberFormatter;
	}

	/**
	 * This method initializes tfDeliveryDistance
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfDeliveryDistance() {
		if (tfDeliveryDistance == null) {
			tfDeliveryDistance = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfDeliveryDistance.setBounds(new Rectangle(357, 252, 147, 22));
			tfDeliveryDistance
					.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfDeliveryDistance;
	}

	/**
	 * This method initializes cbIsLeiXiao	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbIsLeiXiao() {
		if (cbIsLeiXiao == null) {
			cbIsLeiXiao = new JCheckBox();
			cbIsLeiXiao.setText("是否国内销售客户");
			cbIsLeiXiao.setBounds(new Rectangle(292, 10, 127, 21));
		}
		return cbIsLeiXiao;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
