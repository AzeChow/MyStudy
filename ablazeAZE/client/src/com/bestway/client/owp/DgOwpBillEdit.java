package com.bestway.client.owp;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomFormattedTextFieldUtils;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.custombase.action.CustomBaseAction;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.common.constant.FptInOutFlag;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.fpt.entity.FptAppItem;
import com.bestway.owp.action.OwpBillAction;
import com.bestway.owp.entity.OwpAppRecvItem;
import com.bestway.owp.entity.OwpAppSendItem;
import com.bestway.owp.entity.OwpBillRecvHead;
import com.bestway.owp.entity.OwpBillRecvItem;
import com.bestway.owp.entity.OwpBillSendHead;
import com.bestway.owp.entity.OwpBillSendItem;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Dimension;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;

import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Rectangle;
import java.math.BigDecimal;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.JComboBox;

/**
 * 外发加工登记表明细编辑
 * @author sxk
 *
 */
public class DgOwpBillEdit extends JDialogBase {

	private JPanel pnContentPane = null;
	private JToolBar tbJToolBarBar = null;
	private JButton btnEdit = null;
	private JButton btnSave = null;
	private JButton btnPrevious = null;
	private JButton btnNext = null;
	private JButton btnCancel = null;
	private JButton btnClose = null;
	private JPanel pnMain = null;
	private JLabel lbMessage = null;
	private JLabel lbListNo = null;
	private JTextField tfListNo = null;
	private JLabel lbAppGNo = null;
	private JTextField tfAppGNo = null;
	private JTextField tfComplex = null;
	private JLabel lbComplex = null;
	private JLabel lbHsName = null;
	private JTextField tfHsName = null;
	private JLabel lbHsSpec = null;
	private JTextField tfHsSpec = null;
	private JComboBox cbbHsUnit = null;
	private JLabel lbHsUnit = null;
	private JLabel lbQty = null;
	private JLabel lbNote = null;
	private JTextField tfNote = null;
	/**
	 * 登记表表体table模型
	 */
	private JTableListModel tableModel = null;
	/**
	 * 进出货标志。true:出 、false:进
	 */
	private boolean isOutFlag = false;
	/**
	 * 编辑窗体控件状态
	 */
	private int dataState = DataState.BROWSE;
	/**
	 * 外发加工出货登记表表头
	 */
	private OwpBillSendHead owpBillSendHead = null;  //  @jve:decl-index=0:
	/**
	 * 外发加工收货登记表表头
	 */
	private OwpBillRecvHead owpBillRecvHead = null; 
	/**
	 * 外发加工登记表远程服务接口
	 */
	private OwpBillAction owpBillAction = null;
	/**
	 * 外发加工出货登记表表体
	 */
	private OwpBillSendItem owpBillSendItem = null;
	/**
	 * 外发加工收货登记表表体
	 */
	private OwpBillRecvItem owpBillRecvItem = null;
	/**
	 * 格式化处理后的输入框
	 */
	private JFormattedTextField ftfSendQty = null;
	/**
	 * 海关基础资料服务器端接口
	 */
	private CustomBaseAction customBaseAction = null;

	//申请表申报数量
	private Double appQty = 0.0;
	//已申报数量
	private Double qtyTotal = 0.0;
	//当前可申报数量
	private Double canQty = 0.0;
	/**
	 * 窗体构造函数
	 */
	public DgOwpBillEdit() {
		super();
		/**
		 * 初始化外发加工登记表远程服务接口
		 */
		owpBillAction = (OwpBillAction) CommonVars
			.getApplicationContext().getBean("owpBillAction");
		/** 
		 * 初始化海关基础资料服务器端接口
		 */
		customBaseAction = (CustomBaseAction) CommonVars.getApplicationContext()
																.getBean("customBaseAction");
		initialize();
	}

	/**
	 * 初始化窗体
	 */
	private void initialize() {
        this.setSize(new Dimension(596, 327));
        this.setTitle("外发加工登记表明细编辑");
        this.setContentPane(getPnContentPane());
 
	}

	/**
	 * 窗体显示类
	 */
	public void setVisible(boolean isFlag) {
		if (isFlag) {
			initUIComponents();
			if (dataState == DataState.ADD) {
			} else {
				showData();
			}
			setState();
		}
		super.setVisible(isFlag);
	}
	
	/**
	 * 显示控件中的数据
	 */
	private void showData(){
		if(isOutFlag){
			OwpBillSendItem newOwpBillSendItem =(OwpBillSendItem) tableModel.getCurrentRow();
			owpBillSendItem = newOwpBillSendItem;
			tfListNo.setText(owpBillSendItem.getListNo()==null ? "" : owpBillSendItem.getListNo().toString());
			tfAppGNo.setText(owpBillSendItem.getAppGNo()==null ? "" : owpBillSendItem.getAppGNo().toString());
			tfComplex.setText(owpBillSendItem.getComplex()==null? "" : owpBillSendItem.getComplex().getCode());
			cbbHsUnit.setSelectedItem(owpBillSendItem.getHsUnit());
			tfHsName.setText(owpBillSendItem.getHsName()==null ? "" : owpBillSendItem.getHsName());
			tfHsSpec.setText(owpBillSendItem.getHsSpec()==null ? "" : owpBillSendItem.getHsSpec());
			if(null==owpBillSendItem.getQty()){
//				CustomFormattedTextFieldUtils.setFormatterFactory(this.ftfSendQty,0);
				ftfSendQty.setText("0.0");
			}else{
				ftfSendQty.setText(owpBillSendItem.getQty().toString());
			}
			tfNote.setText(owpBillSendItem.getNote()==null?"":owpBillSendItem.getNote());
			showSendMessage(owpBillSendItem);
		}else{
			OwpBillRecvItem newOwpBillRecvItem =(OwpBillRecvItem) tableModel.getCurrentRow();
			owpBillRecvItem = newOwpBillRecvItem;
			tfListNo.setText(owpBillRecvItem.getListNo()==null ? "" : owpBillRecvItem.getListNo().toString());
			tfAppGNo.setText(owpBillRecvItem.getAppGNo()==null ? "" : owpBillRecvItem.getAppGNo().toString());
			tfComplex.setText(owpBillRecvItem.getComplex()==null? "" : owpBillRecvItem.getComplex().getCode());
			cbbHsUnit.setSelectedItem(owpBillRecvItem.getHsUnit());
			tfHsName.setText(owpBillRecvItem.getHsName()==null ? "" : owpBillRecvItem.getHsName());
			tfHsSpec.setText(owpBillRecvItem.getHsSpec()==null ? "" : owpBillRecvItem.getHsSpec());
			if(null==owpBillRecvItem.getQty()){
//				CustomFormattedTextFieldUtils.setFormatterFactory(this.ftfSendQty,0);
				ftfSendQty.setText("0.0");
			}else{
				ftfSendQty.setText(owpBillRecvItem.getQty().toString());
			}
			tfNote.setText(owpBillRecvItem.getNote()==null?"":owpBillRecvItem.getNote());
			showSendRecvMessage(owpBillRecvItem);
		}
	}
	/**
	 * 显示出货数量信息
	 * @param owpBillSendItem 外发加工出货登记表表体
	 */
	private void showSendMessage(OwpBillSendItem owpBillSendItem){
		//查询申请表外发料件申报数量
		List appList = this.owpBillAction.findOwpAppSendItemQty(new Request(
					CommonVars.getCurrUser()), 
					owpBillSendItem.getHead().getAppNo(), 
					owpBillSendItem.getAppGNo());
		
		//查询登记表外发料件
		List billList = this.owpBillAction.findOwpBillSendItemBySeqNum(new Request(
					CommonVars.getCurrUser()), 
					owpBillSendItem.getHead().getAppNo(), 
					owpBillSendItem.getAppGNo());
		
		//登记表料件累计申报数量
		Double qtyTotal = 0.0;
		if(billList.size()>0){
			for(int i = 0;i<billList.size();i++){
				OwpBillSendItem owpBill = (OwpBillSendItem)billList.get(i);
				if(null!=owpBill.getQty()){
					qtyTotal += owpBill.getQty();
				}
			}
		}
		String str = "";
		if(null!=appList && appList.size()!=0){
			OwpAppSendItem owpAppSendItem = (OwpAppSendItem)appList.get(0);
			str = "申请表申报数量：" + owpAppSendItem.getQty() + ";  已申报数量：" +
			qtyTotal + ";  当前可申报数量：" + (owpAppSendItem.getQty()-qtyTotal);

			//当前可申报数量
			canQty = owpAppSendItem.getQty()-qtyTotal;
		}else{
			str = "申请表没有该商品";
		}
		this.lbMessage.setText(str);
	}
	/**
	 * 显示收货数量信息
	 * @param owpBillRecvItem 外发加工收货登记表表体
	 */
	private void showSendRecvMessage(OwpBillRecvItem owpBillRecvItem){
		//查询申请表外发料件申报数量
		List appList = this.owpBillAction.findOwpAppRecvItemQty(new Request(
					CommonVars.getCurrUser()), 
					owpBillRecvItem.getHead().getAppNo(), 
					owpBillRecvItem.getAppGNo());
		
		//查询登记表外发料件
		List billList = this.owpBillAction.findOwpBillRecvItemBySeqNum(new Request(
					CommonVars.getCurrUser()), 
					owpBillRecvItem.getHead().getAppNo(), 
					owpBillRecvItem.getAppGNo());
		
		//登记表料件累计申报数量
		Double qtyTotal = 0.0;
		if(billList.size()>0){
			for(int i = 0;i<billList.size();i++){
				OwpBillRecvItem owpBill = (OwpBillRecvItem)billList.get(i);
				if(null!=owpBill.getQty()){
					qtyTotal += owpBill.getQty();
				}
			}
		}
		String str = "";
		if(null!=appList && appList.size()!=0){
			OwpAppRecvItem owpAppRecvItem = (OwpAppRecvItem)appList.get(0);
			str = "申请表申报数量：" + owpAppRecvItem.getQty() + ";  已申报数量：" +
			qtyTotal + ";  当前可申报数量：" + (owpAppRecvItem.getQty()-qtyTotal);
			
			//当前可申报数量
			canQty = owpAppRecvItem.getQty()-qtyTotal;
		}else{
			str = "申请表没有该商品";
		}
		this.lbMessage.setText(str);
	}
	/**
	 * 初始化组件
	 */
	private void initUIComponents() {
		// 对方公司商品信息单位
		this.cbbHsUnit.setModel(CustomBaseModel.getInstance().getUnitModel());
		this.cbbHsUnit.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(this.cbbHsUnit);
		cbbHsUnit.setSelectedItem(null);
	}
	/**
	 * 设置控件状态
	 */
	private void setState() {
		if(isOutFlag){
			OwpBillSendItem owpBillSendItem =(OwpBillSendItem) tableModel.getCurrentRow();
			if (owpBillSendItem == null) {
				return;
			}
		}else{
			OwpBillRecvItem owpBillRecvItem =(OwpBillRecvItem) tableModel.getCurrentRow();
			if (owpBillRecvItem == null) {
				return;
			}
		}
		btnEdit.setEnabled(this.dataState == DataState.BROWSE);
		btnSave.setEnabled(this.dataState != DataState.BROWSE);
		this.btnPrevious.setEnabled(tableModel.hasPreviousRow());
		this.btnNext.setEnabled(tableModel.hasNextRow());
		tfListNo.setEditable(false);
		tfAppGNo.setEditable(false);
		tfComplex.setEditable(false);
		cbbHsUnit.setEnabled(false);
		tfHsName.setEditable(false);
		tfHsSpec.setEditable(false);
		ftfSendQty.setEditable(this.dataState != DataState.BROWSE);
		tfNote.setEditable(this.dataState != DataState.BROWSE);
		
	}
	/**
	 * 主容器填充	
	 */
	private JPanel getPnContentPane() {
		if (pnContentPane == null) {
			pnContentPane = new JPanel();
			pnContentPane.setLayout(new BorderLayout());
			pnContentPane.add(getTbJToolBarBar(), BorderLayout.NORTH);
			pnContentPane.add(getPnMain(), BorderLayout.CENTER);
		}
		return pnContentPane;
	}

	/**
	 * 工具栏设置
	 */
	private JToolBar getTbJToolBarBar() {
		if (tbJToolBarBar == null) {
			tbJToolBarBar = new JToolBar();
			tbJToolBarBar.add(getBtnEdit());
			tbJToolBarBar.add(getBtnSave());
			tbJToolBarBar.add(getBtnPrevious());
			tbJToolBarBar.add(getBtnNext());
			tbJToolBarBar.add(getBtnCancel());
			tbJToolBarBar.add(getBtnClose());
		}
		return tbJToolBarBar;
	}

	/**
	 * 修改按钮	
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
	 * 保存按钮	
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("保存");
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (vaildatorDataIsNull()) {
						return;
					}
					if(isOutFlag){
						saveSendData();
					}else{
						saveRecvData();
					}
					dataState = DataState.BROWSE;
					setState();
				}
			});
		}
		return btnSave;
	}
	/**
	 * 保存发货数据
	 */
	private void saveSendData(){
		OwpBillSendItem newOwpBillSendItem = (OwpBillSendItem) tableModel.getCurrentRow();
		fillSendData(newOwpBillSendItem);
		this.owpBillAction.saveOwpSendBillItem(new Request(
					CommonVars.getCurrUser()), newOwpBillSendItem);
		showSendMessage(newOwpBillSendItem);
		tableModel.updateRow(newOwpBillSendItem);
	}
	/**
	 * 保存收货数据
	 */
	private void saveRecvData(){
		OwpBillRecvItem newOwpBillRecvItem = (OwpBillRecvItem) tableModel.getCurrentRow();
		fillRecvData(newOwpBillRecvItem);
		this.owpBillAction.saveOwpBillRecvItem(new Request(
					CommonVars.getCurrUser()), newOwpBillRecvItem);
		showSendRecvMessage(newOwpBillRecvItem);
		tableModel.updateRow(newOwpBillRecvItem);
	}
	/**
	 * 填充发货数据
	 * @param newOwpBillSendItem 外发加工出货登记表表体
	 */
	private void fillSendData(OwpBillSendItem newOwpBillSendItem){
		if(null!=newOwpBillSendItem){
			if(null != tfListNo.getText()&&!"".equals(tfListNo.getText())){
				newOwpBillSendItem.setListNo(Integer.valueOf(tfListNo.getText()));
			}
			if(null != tfAppGNo.getText()&&!"".equals(tfAppGNo.getText())){
				newOwpBillSendItem.setAppGNo(Integer.valueOf(tfAppGNo.getText()));
			}
			newOwpBillSendItem.setComplex(owpBillSendItem.getComplex());
			newOwpBillSendItem.setHsUnit(owpBillSendItem.getHsUnit());
			newOwpBillSendItem.setHsName(owpBillSendItem.getHsName());
			newOwpBillSendItem.setHsSpec(owpBillSendItem.getHsSpec());
			if(null == ftfSendQty.getText()){
				
			}
			newOwpBillSendItem.setQty(getDoubleByDigit(Double.valueOf(ftfSendQty.getText()),5));
			newOwpBillSendItem.setNote(tfNote.getText()==null?"":tfNote.getText());
		}
	}
	public static Double getDoubleByDigit(Double sourceDouble, int n) {
		if (sourceDouble == null) {
			return 0.0;
		}
		BigDecimal b = new BigDecimal(sourceDouble);
		return b.setScale(n, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	/**
	 * 填充收货数据
	 * @param newOwpBillSendItem 外发加工收货登记表表体
	 */
	private void fillRecvData(OwpBillRecvItem newOwpBillRecvItem){
		if(null!=newOwpBillRecvItem){
			if(null != tfListNo.getText()&&!"".equals(tfListNo.getText())){
				newOwpBillRecvItem.setListNo(Integer.valueOf(tfListNo.getText()));
			}
			if(null != tfAppGNo.getText()&&!"".equals(tfAppGNo.getText())){
				newOwpBillRecvItem.setAppGNo(Integer.valueOf(tfAppGNo.getText()));
			}
			if(null!=owpBillRecvItem.getComplex()){
				newOwpBillRecvItem.setComplex(owpBillRecvItem.getComplex());
			}
			newOwpBillRecvItem.setHsUnit(owpBillRecvItem.getHsUnit());
			newOwpBillRecvItem.setHsName(owpBillRecvItem.getHsName());
			newOwpBillRecvItem.setHsSpec(owpBillRecvItem.getHsSpec());
			newOwpBillRecvItem.setQty(getDoubleByDigit(Double.valueOf(ftfSendQty.getText()),5));
			newOwpBillRecvItem.setNote(tfNote.getText()==null?"":tfNote.getText());
		}
	}
	/**
	 * 判断保存表单内容
	 * @return
	 */
	private boolean vaildatorDataIsNull() {
		double qty = 0.0;
		try{
			 qty = Double.parseDouble(this.ftfSendQty.getText().toString());
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "申报数量必须为数字！", "警告",
					JOptionPane.INFORMATION_MESSAGE);
			e.printStackTrace();
			return true;
		}
		if (qty == 0.0) {
			JOptionPane.showMessageDialog(null, "申报数量不可为0", "警告",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		if(null==tfListNo.getText()||"".equals(tfListNo.getText())){
			JOptionPane.showMessageDialog(null, "序号不可为空", "警告",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		if(isOutFlag){
		//查询申请表外发料件申报数量
		List appList = this.owpBillAction.findOwpAppSendItemQty(new Request(
					CommonVars.getCurrUser()), 
					owpBillSendItem.getHead().getAppNo(), 
					owpBillSendItem.getAppGNo());
		
		//查询登记表外发料件
		List billList = this.owpBillAction.findOwpBillSendItemBySeqNum(new Request(
					CommonVars.getCurrUser()), 
					owpBillSendItem.getHead().getAppNo(), 
					owpBillSendItem.getAppGNo());
		
		//登记表料件累计申报数量
		Double qtyTotal = 0.0;
		if(billList.size()>0){
			for(int i = 0;i<billList.size();i++){
				OwpBillSendItem owpBill = (OwpBillSendItem)billList.get(i);
				if(null!=owpBill.getQty()){
					qtyTotal += owpBill.getQty();
				}
			}
		}
		Double aqty = 0.0;
		Double bqty = 0.0;
		if(null!=appList && appList.size()!=0){
			OwpAppSendItem owpAppSendItem = (OwpAppSendItem)appList.get(0);

			//申报数量为正数
			aqty = owpAppSendItem.getQty()-qtyTotal-Double.parseDouble(ftfSendQty.getText());
			//申报数量为负数
			bqty = Double.parseDouble(ftfSendQty.getText())*(-1)-qtyTotal;
		}
			if(qty>0){
				if(aqty<0){
					JOptionPane.showMessageDialog(null, "申报数量不可以大于可申报数量", "警告",
							JOptionPane.INFORMATION_MESSAGE);
					return true;
				}
			}else{
				if(bqty>0){
					JOptionPane.showMessageDialog(null, "申报数量不可以大于已申报数量", "警告",
							JOptionPane.INFORMATION_MESSAGE);
					return true;
				}
			}
		}else{
			//查询申请表外发料件申报数量
			List appList = this.owpBillAction.findOwpAppRecvItemQty(new Request(
						CommonVars.getCurrUser()), 
						owpBillRecvItem.getHead().getAppNo(), 
						owpBillRecvItem.getAppGNo());
			
			//查询登记表外发料件
			List billList = this.owpBillAction.findOwpBillRecvItemBySeqNum(new Request(
						CommonVars.getCurrUser()), 
						owpBillRecvItem.getHead().getAppNo(), 
						owpBillRecvItem.getAppGNo());
			
			//登记表料件累计申报数量
			Double qtyTotal = 0.0;
			if(billList.size()>0){
				for(int i = 0;i<billList.size();i++){
					OwpBillRecvItem owpBill = (OwpBillRecvItem)billList.get(i);
					if(null!=owpBill.getQty()){
						qtyTotal += owpBill.getQty();
					}
				}
			}
			Double aqty = 0.0;
			Double bqty = 0.0;
			if(null!=appList && appList.size()!=0){
				OwpAppRecvItem owpAppRecvItem = (OwpAppRecvItem)appList.get(0);

				//申报数量为正数
				aqty = owpAppRecvItem.getQty()-qtyTotal-Double.parseDouble(ftfSendQty.getText());
				//申报数量为负数
				bqty = Double.parseDouble(ftfSendQty.getText())*(-1)-qtyTotal;
			}
				if(qty>0){
					if(aqty<0){
						JOptionPane.showMessageDialog(null, "申报数量不可以大于可申报数量", "警告",
								JOptionPane.INFORMATION_MESSAGE);
						return true;
					}
				}else{
					if(bqty>0){
						JOptionPane.showMessageDialog(null, "申报数量不可以大于已申报数量", "警告",
								JOptionPane.INFORMATION_MESSAGE);
						return true;
					}
				}
		}
		return false;
	}
	/**
	 * 上笔按钮
	 */
	private JButton getBtnPrevious() {
		if (btnPrevious == null) {
			btnPrevious = new JButton();
			btnPrevious.setText("上笔");
			btnPrevious.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!tableModel.previousRow()) {
						btnPrevious.setEnabled(false);
						btnNext.setEnabled(true);
					} else {
						btnPrevious.setEnabled(true);
						btnNext.setEnabled(true);
					}
					showData();
					dataState = DataState.EDIT;
					setState();
				}
			});
		}
		return btnPrevious;
	}

	/**
	 * 下笔按钮
	 */
	private JButton getBtnNext() {
		if (btnNext == null) {
			btnNext = new JButton();
			btnNext.setText("下笔");
			btnNext.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!tableModel.nextRow()) {
						btnPrevious.setEnabled(true);
						btnNext.setEnabled(false);
					} else {
						btnPrevious.setEnabled(true);
						btnNext.setEnabled(true);
					}
					showData();
					dataState = DataState.EDIT;
					setState();
				}
			});
		}
		return btnNext;
	}

	/**
	 * 取消按钮	
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dataState = DataState.BROWSE;
					showData();
					setState();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * 关闭按钮
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("关闭");
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnClose;
	}

	/**
	 * 主容器设置	
	 */
	private JPanel getPnMain() {
		if (pnMain == null) {
			lbNote = new JLabel();
			lbNote.setBounds(new Rectangle(298, 203, 77, 18));
			lbNote.setText("备注");
			lbQty = new JLabel();
			lbQty.setBounds(new Rectangle(46, 204, 63, 18));
			lbQty.setText("申报数量");
			lbHsUnit = new JLabel();
			lbHsUnit.setBounds(new Rectangle(297, 115, 74, 18));
			lbHsUnit.setText("计量单位");
			lbHsSpec = new JLabel();
			lbHsSpec.setBounds(new Rectangle(297, 160, 74, 18));
			lbHsSpec.setText("商品规格");
			lbHsName = new JLabel();
			lbHsName.setBounds(new Rectangle(46, 161, 65, 18));
			lbHsName.setText("商品名称");
			lbComplex = new JLabel();
			lbComplex.setBounds(new Rectangle(46, 111, 61, 18));
			lbComplex.setText("商品编号");
			lbAppGNo = new JLabel();
			lbAppGNo.setBounds(new Rectangle(294, 69, 80, 18));
			lbAppGNo.setText("申请表序号");
			lbListNo = new JLabel();
			lbListNo.setBounds(new Rectangle(47, 68, 61, 18));
			lbListNo.setText("序号");
			lbMessage = new JLabel();
			lbMessage.setBounds(new Rectangle(5, 15, 576, 35));
			lbMessage.setFont(new Font("Dialog", Font.BOLD, 14));
			lbMessage.setText("JLabel");
			lbMessage.setForeground(new Color(0, 51, 255));
			lbMessage.setBackground(new Color(238, 238, 238));
			pnMain = new JPanel();
			pnMain.setLayout(null);
			pnMain.add(lbMessage, null);
			pnMain.add(lbListNo, null);
			pnMain.add(getTfListNo(), null);
			pnMain.add(lbAppGNo, null);
			pnMain.add(getTfAppGNo(), null);
			pnMain.add(getTfComplex(), null);
			pnMain.add(lbComplex, null);
			pnMain.add(lbHsName, null);
			pnMain.add(getTfHsName(), null);
			pnMain.add(lbHsSpec, null);
			pnMain.add(getTfHsSpec(), null);
			pnMain.add(getCbbHsUnit(), null);
			pnMain.add(lbHsUnit, null);
			pnMain.add(lbQty, null);
			pnMain.add(lbNote, null);
			pnMain.add(getTfNote(), null);
			pnMain.add(getFtfSendQty(), null);
		}
		return pnMain;
	}

	/**
	 * This method initializes tfListNo	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfListNo() {
		if (tfListNo == null) {
			tfListNo = new JTextField();
			tfListNo.setBounds(new Rectangle(114, 66, 134, 22));
		}
		return tfListNo;
	}

	/**
	 * This method initializes tfAppGNo	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfAppGNo() {
		if (tfAppGNo == null) {
			tfAppGNo = new JTextField();
			tfAppGNo.setBounds(new Rectangle(384, 69, 130, 22));
		}
		return tfAppGNo;
	}

	/**
	 * This method initializes tfComplex	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfComplex() {
		if (tfComplex == null) {
			tfComplex = new JTextField();
			tfComplex.setBounds(new Rectangle(115, 110, 133, 22));
		}
		return tfComplex;
	}

	/**
	 * This method initializes tfHsName	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfHsName() {
		if (tfHsName == null) {
			tfHsName = new JTextField();
			tfHsName.setBounds(new Rectangle(116, 160, 130, 22));
		}
		return tfHsName;
	}

	/**
	 * This method initializes tfHsSpec	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfHsSpec() {
		if (tfHsSpec == null) {
			tfHsSpec = new JTextField();
			tfHsSpec.setBounds(new Rectangle(386, 165, 129, 22));
		}
		return tfHsSpec;
	}

	/**
	 * This method initializes cbbHsUnit	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbHsUnit() {
		if (cbbHsUnit == null) {
			cbbHsUnit = new JComboBox();
			cbbHsUnit.setBounds(new Rectangle(385, 112, 129, 27));
		}
		return cbbHsUnit;
	}

	/**
	 * This method initializes tfNote	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfNote() {
		if (tfNote == null) {
			tfNote = new JTextField();
			tfNote.setBounds(new Rectangle(387, 202, 128, 22));
		}
		return tfNote;
	}

	public JTableListModel getTableModel() {
		return tableModel;
	}

	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
	}

	public OwpBillSendHead getOwpBillSendHead() {
		return owpBillSendHead;
	}

	public void setOwpBillSendHead(OwpBillSendHead owpBillSendHead) {
		this.owpBillSendHead = owpBillSendHead;
	}

	public JLabel getLbMessage() {
		return lbMessage;
	}

	public void setLbMessage(JLabel lbMessage) {
		this.lbMessage = lbMessage;
	}

	public int getDataState() {
		return dataState;
	}

	public void setDataState(int dataState) {
		this.dataState = dataState;
	}

	public boolean isOutFlag() {
		return isOutFlag;
	}

	public void setOutFlag(boolean isOutFlag) {
		this.isOutFlag = isOutFlag;
	}
	private JFormattedTextField getFtfSendQty() {
		if (ftfSendQty == null) {
			ftfSendQty = new JFormattedTextField();
			ftfSendQty.setBounds(new Rectangle(117, 203, 129, 24));
		}
		return ftfSendQty;
	}
	
}  //  @jve:decl-index=0:visual-constraint="10,10"
