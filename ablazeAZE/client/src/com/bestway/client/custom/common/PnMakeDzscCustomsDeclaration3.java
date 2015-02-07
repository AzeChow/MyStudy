/*
 * Created on 2004-8-16
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.client.custom.common;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.bcus.enc.entity.ImpExpCommodityInfo;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.CommonUtils;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.constant.DzscState;
import com.bestway.dzsc.contractexe.action.DzscContractExeAction;
import com.bestway.dzsc.contractexe.entity.DzscCustomsDeclaration;
import com.bestway.dzsc.contractexe.entity.TempDzscImpExpCommodityInfo;
import com.bestway.dzsc.dzscmanage.action.DzscAction;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsImgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.ui.message.DgMessage;
import com.bestway.ui.winuicontrol.JPanelBase;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class PnMakeDzscCustomsDeclaration3 extends JPanelBase {
	private PnMakeDzscCustomsDeclaration3	pnMakeCustomsEnvelopBill2	= null;
	private JTableListModel					tableModel					= null;
	private JTable							jTable						= null;
	private JScrollPane						jScrollPane					= null;
	private List							parentList					= null;
	private List							colorList					= null;
	private JButton							btnSelectAll				= null;
	private JButton							btnNotSelectAll				= null;
	private DzscContractExeAction			dzsccontractExeAction		= null;
	private DzscAction             dzscAction             = null;
	private int								materielProductType			= -1;
	private DzscEmsPorHead					dzscEmsPorHead				= null;  //  @jve:decl-index=0:
	private Map<String, JComboBox>			map							= new HashMap<String, JComboBox>();  //  @jve:decl-index=0:
	private boolean							isProduct					= false;
	private boolean isAutoSelectContract = false;
	private Map<String,Double[]> numAndMoneyMap = new HashMap<String,Double[]>();  //  @jve:decl-index=0:
	private StringBuffer bf = new StringBuffer();//日志记录  //  @jve:decl-index=0:
	private boolean isNewRecord = true;
	private DzscCustomsDeclaration dzscCustomsDeclaration = null;
	/**
	 * This is the default constructor
	 */
	public PnMakeDzscCustomsDeclaration3() {
		dzsccontractExeAction = (DzscContractExeAction) CommonVars
				.getApplicationContext().getBean("dzscContractExeAction");
		 this.dzscAction = (DzscAction) CommonVars.getApplicationContext()
         .getBean("dzscAction");
		initialize();
	}

	public void setVisible(boolean isFlag) {
		if (isFlag == true) {
			try {
				isProduct = this.materielProductType == Integer
				.parseInt(MaterielType.FINISHED_PRODUCT);
				initTable();				
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), "提示",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
		super.setVisible(isFlag);
	}
	
	/**
	 * 数量.金额map获得key的方法
	 * @param dzscEmsPorHead
	 * @param temSeqNum
	 * @return
	 */
	private String getNumAndMoneyMapKey(DzscEmsPorHead dzscEmsPorHead,Integer temSeqNum){
		return dzscEmsPorHead.getEmsNo()+"/" + temSeqNum;
	}
	/**
	 * 获取数量，金额
	 * @param key
	 * @return
	 */
	private Double[] getNumAndMoney(String key){
		return (Double[])numAndMoneyMap.get(key);
	}
	
	/**
	 * 保存数量金额
	 * @param key
	 * @param numAndMoney
	 */
	private void putNumAndMoney(String key,Double[] numAndMoney){
		Double[] old =  (Double[])numAndMoneyMap.get(key);
		if(old != null ){
			numAndMoney[0] += old[0];
			numAndMoney[1] += old[1];
		}
		numAndMoneyMap.put(key, numAndMoney);
	}

	/**
	 * @return Returns the materielProductType.
	 */
	public int getMaterielProductType() {
		return materielProductType;
	}

	/**
	 * @param materielProductType
	 *            The materielProductType to set.
	 */
	public void setMaterielProductType(int materielProductType) {
		this.materielProductType = materielProductType;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		javax.swing.JLabel jLabel = new JLabel();
		this.setLayout(null);
		this.setSize(630, 248);
		jLabel.setBounds(13, 11, 132, 21);
		jLabel.setText("可供选择的商品信息");
		this.add(jLabel, null);
		this.add(getJScrollPane(), null);
		this.add(getBtnSelectAll(), null);
		this.add(getBtnNotSelectAll(), null);
	}

	/**
	 * @return Returns the pnMakeCustomsEnvelopBill2.
	 */
	public PnMakeDzscCustomsDeclaration3 getPnMakeCustomsEnvelopBill2() {
		return pnMakeCustomsEnvelopBill2;
	}

	/**
	 * @param pnMakeCustomsEnvelopBill2
	 *            The pnMakeCustomsEnvelopBill2 to set.
	 */
	public void setPnMakeCustomsEnvelopBill2(
			PnMakeDzscCustomsDeclaration3 pnMakeCustomsEnvelopBill2) {
		this.pnMakeCustomsEnvelopBill2 = pnMakeCustomsEnvelopBill2;
	}

	/**
	 * @return Returns the parentList.
	 */
	public List getParentList() {
		return parentList;
	}

	/**
	 * @param parentList
	 *            The parentList to set.
	 */
	public void setParentList(List parentList) {
		this.parentList = parentList;
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
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setBounds(2, 39, 627, 209);
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes btnSelectAll
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSelectAll() {
		if (btnSelectAll == null) {
			btnSelectAll = new JButton();
			btnSelectAll.setBounds(441, 11, 93, 21);
			btnSelectAll.setText("全部分配");
			btnSelectAll.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List<TempDzscImpExpCommodityInfo> list = tableModel.getList();
					bf.delete(0, bf.length());
					bf.append("全部分配开始...\n");
					for(TempDzscImpExpCommodityInfo info : list){
						assignContract(info);
					}
					bf.append("\n----------------------\n分配结束...\n");
					DgMessage message = new DgMessage("手册分配", bf.toString());
				}
			});
		}
		return btnSelectAll;
	}

	/**
	 * This method initializes btnNotSelectAll
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnNotSelectAll() {
		if (btnNotSelectAll == null) {
			btnNotSelectAll = new JButton();
			btnNotSelectAll.setBounds(540, 11, 61, 21);
			btnNotSelectAll.setText("不选");
			btnNotSelectAll
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if(tableModel == null){
								return;
							}
							List<TempDzscImpExpCommodityInfo> list = tableModel.getList();
							for(TempDzscImpExpCommodityInfo temp : list){
								unAssignContract(temp);
							}
						}
					});
		}
		return btnNotSelectAll;
	}

	
	
	/**
	 * 申请单分配通关手册
	 */
	private void assignAnDzscEmsPorHead(ImpExpCommodityInfo impExpCommodityInfo){
		
	}

	/**
	 * 初始化数据Table
	 */
	private void initTable() {
		List list = this.dzsccontractExeAction
				.findTempDzscImpExpCommodityInfoByParent(new Request(CommonVars
						.getCurrUser()), this.parentList);
		final boolean isProduct = this.isProduct;
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("分配", "isSelected", 60));
				list.add(addColumn("料号", "impExpCommodityInfo.materiel.ptNo",
						90));
                list.add(addColumn("名称", "impExpCommodityInfo.materiel.factoryName",
                        120));
                list.add(addColumn("规格型号",
                        "impExpCommodityInfo.materiel.factorySpec", 120));
				list.add(addColumn("集装箱号码", "containerCode", 100));
				
				if (isProduct) {//成品
					list.add(addColumn("通关手册编号", "dzscEmsExgBill.dzscEmsPorHead.emsNo", 100));
					list.add(addColumn("进口合同号", "dzscEmsExgBill.dzscEmsPorHead.ieContractNo", 100));
					list.add(addColumn("出口合同号", "dzscEmsExgBill.dzscEmsPorHead.imContractNo", 100));
				}else{//料件
					list.add(addColumn("通关手册编号", "dzscEmsImgBill.dzscEmsPorHead.emsNo", 100));
					list.add(addColumn("进口合同号", "dzscEmsImgBill.dzscEmsPorHead.ieContractNo", 100));
					list.add(addColumn("出口合同号", "dzscEmsImgBill.dzscEmsPorHead.imContractNo", 100));
				}
//				if (isProduct) {
//					list.add(addColumn("合同备案成品", "dzscEmsExgBill.no", 100));
//				} else {
//					list.add(addColumn("合同备案料件", "dzscEmsImgBill.no", 100));
//				}
				list.add(addColumn("单位",
						"impExpCommodityInfo.materiel.calUnit.name", 60));
				list.add(addColumn("数量", "impExpCommodityInfo.amountPrice", 60));
				list.add(addColumn("金额", "impExpCommodityInfo.quantity", 60));
				list
						.add(addColumn("毛重", "impExpCommodityInfo.grossWeight",
								60));
				list.add(addColumn("净重", "impExpCommodityInfo.netWeight", 60));
				list.add(addColumn("件数", "impExpCommodityInfo.piece", 60));
				return list;
			}
		};
		jTableListModelAdapter.setEditableColumn(1);
		jTableListModelAdapter.setEditableColumn(6);
		tableModel = new JTableListModel(jTable, list, jTableListModelAdapter);
		//
		// 初始化所有的表中的编辑的ComboBox对象
		//        
//		for (int i = 0; i < list.size(); i++) {
//			TempDzscImpExpCommodityInfo temp = (TempDzscImpExpCommodityInfo) list
//					.get(i);
//			JComboBox cbb = new JComboBox();			
//			//
//			// 用进出口明细做Id
//			//
//			map.put(temp.getImpExpCommodityInfo().getId(), cbb);
//		}
		jTable.getColumnModel().getColumn(1).setCellRenderer(
				new TableCheckBoxRender());
		// jTable.getColumnModel().getColumn(10).setCellRenderer(
		// new JComboBoxRenderer());
		jTable.getColumnModel().getColumn(1).setCellEditor(
				new CheckBoxEditor(new JCheckBox(), isProduct,this.isAutoSelectContract));
//		jTable.getColumnModel().getColumn(6).setCellEditor(
//				new JComboBoxEditor(new JComboBox(), isProduct, map));
	}

	/**
	 * render table JchcckBox 列
	 */
	class JComboBoxRenderer extends DefaultTableCellRenderer {

		public JComboBoxRenderer() {
		}

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			JComboBox jComboBox = new JComboBox();
			jComboBox.addItem(value);
			jComboBox.setSelectedItem(value);
			jComboBox.setBackground(table.getBackground());
			if (isSelected) {
				jComboBox.setForeground(table.getSelectionForeground());
				jComboBox.setBackground(table.getSelectionBackground());
			} else {
				jComboBox.setForeground(table.getForeground());
				jComboBox.setBackground(table.getBackground());
			}
			return jComboBox;
		}
	}

	/**
	 * 编辑列
	 */
	class JComboBoxEditor extends DefaultCellEditor implements ItemListener {

		private JTable			table		= null;
		private boolean			isProduct	= true;
		Map<String, JComboBox>	map			= new HashMap<String, JComboBox>();
		JComboBox				jComboBox	= null;

		public JComboBoxEditor(JComboBox cbb, boolean isProduct,
				Map<String, JComboBox> map) {
			super(cbb);
			this.isProduct = isProduct;
			this.map = map;
		}

		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {
			table.setRowSelectionInterval(row, row);
			JTableListModel tableModel = (JTableListModel) table.getModel();
			Object obj = tableModel.getCurrentRow();
			String key = "";
			if (obj instanceof TempDzscImpExpCommodityInfo) {
				TempDzscImpExpCommodityInfo temp = (TempDzscImpExpCommodityInfo) obj;
				key = temp.getImpExpCommodityInfo().getId();
			}
			jComboBox = map.get(key);
			if (jComboBox == null) {
				return null;
			}
			jComboBox.setBackground(table.getBackground());
			if (isSelected) {
				jComboBox.setForeground(table.getSelectionForeground());
				jComboBox.setBackground(table.getSelectionBackground());
			} else {
				jComboBox.setForeground(table.getForeground());
				jComboBox.setBackground(table.getBackground());
			}
			jComboBox.addItemListener(this);
			this.table = table;
			return jComboBox;
		}

		public Object getCellEditorValue() {
			jComboBox.removeItemListener(this);
			return jComboBox;
		}

		public void itemStateChanged(java.awt.event.ItemEvent e) {
			JTableListModel tableModel = (JTableListModel) this.table
					.getModel();
			Object obj = tableModel.getCurrentRow();
			if (obj instanceof TempDzscImpExpCommodityInfo) {
				TempDzscImpExpCommodityInfo temp = (TempDzscImpExpCommodityInfo) obj;
				JComboBox cbb = map.get(temp.getImpExpCommodityInfo().getId());
				if (isProduct) {
					temp.setDzscEmsExgBill((DzscEmsExgBill) cbb
							.getSelectedItem());
				} else {
					temp.setDzscEmsImgBill((DzscEmsImgBill) cbb
							.getSelectedItem());

				}
				tableModel.updateRow(obj);
			}
			fireEditingStopped();
		}
	}

	/**
	 * 编辑列
	 */
	class CheckBoxEditor extends DefaultCellEditor implements ActionListener {
		private JCheckBox		cb;
		private JTable			table		= null;
		private boolean			isProduct	= true;
		private boolean isAutoSelectContract ;

		public CheckBoxEditor(JCheckBox checkBox, boolean isProduct,
				boolean map) {
			super(checkBox);
			this.isProduct = isProduct;
			this.isAutoSelectContract = isAutoSelectContract;
		}

		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {
			if (value == null) {
				return null;
			}
			if (Boolean.valueOf(value.toString()) instanceof Boolean) {
				cb = new JCheckBox();
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
			cb.removeActionListener(this);
			return cb;
		}

		public void actionPerformed(ActionEvent e) {
			if(cb.isSelected()){
				JTableListModel tableModel = (JTableListModel) this.table
						.getModel();
				Object obj = tableModel.getCurrentRow();
				if (obj instanceof TempDzscImpExpCommodityInfo) {
					bf.delete(0, bf.length());
					bf.append("分配开始...\n");
					assignContract((TempDzscImpExpCommodityInfo)obj);
					bf.append("\n----------------------\n分配结束...\n");
					DgMessage message = new DgMessage("手册分配",bf.toString());
				}
				fireEditingStopped();
			} else {
				JTableListModel tableModel = (JTableListModel) this.table
						.getModel();
				Object obj = tableModel.getCurrentRow();
				if (obj instanceof TempDzscImpExpCommodityInfo) {
					unAssignContract((TempDzscImpExpCommodityInfo)obj);
				}
			}
		}
	}
	
	/**
	 * 取消合同分配
	 */
	private void unAssignContract(TempDzscImpExpCommodityInfo temp) {
		if(!temp.getIsSelected()){
			return;
		}
		temp.setIsSelected(false);
		Double[] maps = null;
		if(isProduct){
			maps = numAndMoneyMap.get(getNumAndMoneyMapKey(temp.getDzscEmsExgBill().getDzscEmsPorHead(),temp.getDzscEmsExgBill().getTenSeqNum()));
			if(maps!=null){
				maps[0] = maps[0] - CommonUtils.getDoubleExceptNull(temp.getImpExpCommodityInfo().getQuantity());
				maps[1] = maps[1] - CommonUtils.getDoubleExceptNull(temp.getImpExpCommodityInfo().getAmountPrice());
			}
			numAndMoneyMap.put(getNumAndMoneyMapKey(temp.getDzscEmsExgBill().getDzscEmsPorHead(),temp.getDzscEmsExgBill().getTenSeqNum()),maps);
			temp.setDzscEmsExgBill(null);
			tableModel.updateRow(temp);
		}else{
			maps = numAndMoneyMap.get(getNumAndMoneyMapKey(temp.getDzscEmsImgBill().getDzscEmsPorHead(),temp.getDzscEmsImgBill().getTenSeqNum()));
			if(maps!=null){
				maps[0] = maps[0] - CommonUtils.getDoubleExceptNull(temp.getImpExpCommodityInfo().getQuantity());
				maps[1] = maps[1] - CommonUtils.getDoubleExceptNull(temp.getImpExpCommodityInfo().getAmountPrice());
			}
			numAndMoneyMap.put(getNumAndMoneyMapKey(temp.getDzscEmsImgBill().getDzscEmsPorHead(),temp.getDzscEmsImgBill().getTenSeqNum()),maps);
			temp.setDzscEmsImgBill(null);
			tableModel.updateRow(temp);
		}
	
		
	}
	
	/**
	 * 合同分配
	 */
	private boolean assignContract(TempDzscImpExpCommodityInfo temp){
		bf.append("----------------------\n"+(isProduct?"成品":"料件")+"货号："+temp.getImpExpCommodityInfo().getMateriel().getPtNo()+"\n");
		//已经分配，直接返回
		if(temp.getIsSelected()){
			bf.append("已经分配...\n");
			return true;
		}
		//检测归并序号
		Integer temSeqNum = null;
		if(this.isProduct){//成品
			//检查归并序号
			 temSeqNum =dzsccontractExeAction.findTenSeqNum(new Request(CommonVars.getCurrUser())
			      ,temp.getImpExpCommodityInfo().getMateriel().getPtNo(),MaterielType.FINISHED_PRODUCT);
		}else{//料件
             //检查归并序号
			  temSeqNum =dzsccontractExeAction.findTenSeqNum(new Request(CommonVars.getCurrUser())
		          ,temp.getImpExpCommodityInfo().getMateriel().getPtNo(),MaterielType.MATERIEL);
		}
		if(temSeqNum==null){
			bf.append("没有归并关系，分配失败！\n");
			return false;
		}
		//申请单数量
		 Double qt = CommonUtils.getDoubleExceptNull(temp.getImpExpCommodityInfo().getQuantity());
		if(qt == 0){
			bf.append("申请单数量为零，不进行分配！");
			return true;
		}
		
		//分配
		if(this.isAutoSelectContract){//自动分配
			 List<DzscEmsPorHead> contracts = new ArrayList();
				 contracts = dzscAction.findDzscEmsPorHead(new Request(CommonVars
			                .getCurrUser()), DzscState.EXECUTE);
				 //通关手册按结束日期升顺
				 Collections.sort(contracts, new Comparator<DzscEmsPorHead>() {
					public int compare(DzscEmsPorHead o1, DzscEmsPorHead o2) {
						// TODO Auto-generated method stub
						if(o1.getEndDate().before(o2.getEndDate())){
							return -1;
						}
						if(o1.getEndDate().equals(o2.getEndDate())){
							return 0;
						}
						return 1;
					}
				});
			 
			 //分配通关手册
			 for(DzscEmsPorHead dzscEmsPorHead : contracts){
				 bf.append("\n自动分配，手册号："+dzscEmsPorHead.getEmsNo()+"\n");
				 if(isProduct){//成品
						if(assignContractExg(temp,dzscEmsPorHead,temSeqNum)){
							tableModel.updateRow(temp);
							return true;
						}
					}else{//料件
						if(assignContractImg(temp,dzscEmsPorHead,temSeqNum)){
							tableModel.updateRow(temp);
							return true;
						}
					}
			 }
			 //一个料件分配给多份手册
			 bf.append("\n自动分配，以多本手册进行分配\n");
			 //每本手册的剩余数量，金额
			 Double[] remain = null;
			 //累计和
			 Double[] sum = new Double[]{0d,0d};
			 //对应通关手册余额
			 List<Double[]> remains = new ArrayList<Double[]>();			 
			 if(isProduct){
				 //合同备案成品
				 DzscEmsExgBill dzscEmsExgBill = null;
				 //对应通关手册成品
				 List<DzscEmsExgBill> exgs = new ArrayList<DzscEmsExgBill>();
				 for(DzscEmsPorHead dzscEmsPorHead : contracts){
					 dzscEmsExgBill =dzsccontractExeAction.findDzscEmsExgBillBytenSeqNum(new Request(CommonVars.getCurrUser()),dzscEmsPorHead,temSeqNum);
					if(dzscEmsExgBill==null){
							continue;
					}
					//获取每本手册的剩余数量，金额
					remain = getExgNumAndMoneyRemain(dzscEmsExgBill);
					bf.append("手册号："+dzscEmsExgBill.getDzscEmsPorHead().getEmsNo()+"\t剩余数量："+remain[0]+"\t剩余金额："+remain[1]+"\n");
					if(remain[0]>0 && remain[1]>0){
						exgs.add(dzscEmsExgBill);
						remains.add(remain);
					}else{
						continue;
					}
					sum[0]+=remain[0];
					sum[1]+=remain[1];
					if(numAndMoneyTest(temp.getImpExpCommodityInfo(),sum)){//数量合适，进行分配
						sum[0]=temp.getImpExpCommodityInfo().getQuantity();
						sum[1]=temp.getImpExpCommodityInfo().getAmountPrice();
//						bf.append("总数:"+sum[0]+"\t"+sum[1]+"\n");
						//合同余量
						Double[] obj = null;
						//原来净毛重
						Double grossWeight =CommonUtils.getDoubleExceptNull(temp.getImpExpCommodityInfo().getGrossWeight());
						Double netWeight =CommonUtils.getDoubleExceptNull(temp.getImpExpCommodityInfo().getNetWeight());
//						Double[] totals = new Double[] {temp.getImpExpCommodityInfo().getQuantity(),temp.getImpExpCommodityInfo().getAmountPrice()};
						obj = remains.get(0);
						DzscEmsExgBill exg = exgs.get(0);
						temp.getImpExpCommodityInfo().setQuantity(obj[0]);
						temp.getImpExpCommodityInfo().setAmountPrice(obj[1]);
						//净毛重=分配数量比例＊申请单净毛重
						temp.getImpExpCommodityInfo().setGrossWeight(grossWeight	*(obj[0])/qt);
						temp.getImpExpCommodityInfo().setNetWeight(netWeight*(obj[0])/qt);
						temp.setDzscEmsExgBill(exgs.get(0));
						temp.setIsSelected(true);
						List<TempDzscImpExpCommodityInfo> temps = new ArrayList();
						tableModel.updateRow(temp);
						Double[] maps = numAndMoneyMap.get(getNumAndMoneyMapKey(exg.getDzscEmsPorHead(),exg.getTenSeqNum()));
						if(maps!=null){
							numAndMoneyMap.put(getNumAndMoneyMapKey(exg.getDzscEmsPorHead(),exg.getTenSeqNum()), new Double[]{obj[0]+maps[0]
							                                                                                                                                 ,obj[1]+maps[1]});
						}else{
							numAndMoneyMap.put(getNumAndMoneyMapKey(exg.getDzscEmsPorHead(),exg.getTenSeqNum()), new Double[]{obj[0],obj[1]});
						}
						sum[0]-=obj[0];
						sum[1]-=obj[1];
						bf.append("分配手册号："+temp.getDzscEmsExgBill().getDzscEmsPorHead().getEmsNo()+"\t数量："+obj[0]+"\t金额："+obj[1]+"\n");
						for(int i=1;i<exgs.size();i++){
							obj = remains.get(i);
							exg = exgs.get(i);
//							bf.append("sum:"+sum[0]+"\t"+sum[1]+"\n");
							if(obj[0]>=sum[0]){
								obj[0] = sum[0];
							}
							if(obj[1]>=sum[1]){
								obj[1] = sum[1];
							}
							sum[0]-=obj[0];
							sum[1]-=obj[1];
							TempDzscImpExpCommodityInfo newTemp = new TempDzscImpExpCommodityInfo();
							ImpExpCommodityInfo newImpExpCommodityInfo = new  ImpExpCommodityInfo();
							newImpExpCommodityInfo.setMateriel(temp.getImpExpCommodityInfo().getMateriel());
							newTemp.setImpExpCommodityInfo(newImpExpCommodityInfo);
							newTemp.getImpExpCommodityInfo().setQuantity(obj[0]);
							newTemp.getImpExpCommodityInfo().setAmountPrice(obj[1]);
							//净毛重=分配数量比例＊申请单净毛重
							newTemp.getImpExpCommodityInfo().setGrossWeight(grossWeight	*(obj[0])/qt);
							newTemp.getImpExpCommodityInfo().setNetWeight(netWeight*(obj[0])/qt);
							newTemp.setDzscEmsExgBill(exg);
						    newTemp.setIsSelected(true);
							 maps = numAndMoneyMap.get(getNumAndMoneyMapKey(exg.getDzscEmsPorHead(),exg.getTenSeqNum()));
								if(maps!=null){
									numAndMoneyMap.put(getNumAndMoneyMapKey(exg.getDzscEmsPorHead(),exg.getTenSeqNum()), new Double[]{obj[0]+maps[0]
									                                                                                                                                 ,obj[1]+maps[1]});
								}else{
									numAndMoneyMap.put(getNumAndMoneyMapKey(exg.getDzscEmsPorHead(),exg.getTenSeqNum()), new Double[]{obj[0],obj[1]});
								}
							 bf.append("分配手册号："+newTemp.getDzscEmsExgBill().getDzscEmsPorHead().getEmsNo()+"\t数量："+obj[0]+"\t金额："+obj[1]+"\n");
							 temps.add(newTemp);
						}
						for(TempDzscImpExpCommodityInfo newTemp : temps){
							tableModel.addRowAfter(temp, newTemp);
						}
						bf.append("自动分配成功！\n");
						return true;
					}
				 }
				 bf.append("没有满足所有条件，自动分配失败！\n");
				 return false;
			 }else{
				 //合同备案料件
				 DzscEmsImgBill dzscEmsImgBill = null;
				 //对应通关手册成品
				 List<DzscEmsImgBill> imgs = new ArrayList<DzscEmsImgBill>();
				 for(DzscEmsPorHead dzscEmsPorHead : contracts){
					 dzscEmsImgBill =dzsccontractExeAction.findDzscEmsImgBillBytenSeqNum(new Request(CommonVars.getCurrUser()),dzscEmsPorHead,temSeqNum);
					if(dzscEmsImgBill==null){
							continue;
					}
					//获取每本手册的剩余数量，金额
					remain = getImgNumAndMoneyRemain(dzscEmsImgBill);
					bf.append("手册号："+dzscEmsImgBill.getDzscEmsPorHead().getEmsNo()+"\t剩余数量："+remain[0]+"\t剩余金额："+remain[1]+"\n");
					if(remain[0]>0 && remain[1]>0){
						imgs.add(dzscEmsImgBill);
						remains.add(remain);
					}else{
						continue;
					}
					sum[0]+=remain[0];
					sum[1]+=remain[1];
					if(numAndMoneyTest(temp.getImpExpCommodityInfo(),sum)){//数量合适，进行分配
						sum[0]=temp.getImpExpCommodityInfo().getQuantity();
						sum[1]=temp.getImpExpCommodityInfo().getAmountPrice();
						bf.append("总数:"+sum[0]+"\t"+sum[1]+"\n");
						Double[] obj = null;
//						Double[] totals = new Double[] {temp.getImpExpCommodityInfo().getQuantity(),temp.getImpExpCommodityInfo().getAmountPrice()};
						obj = remains.get(0);
						DzscEmsImgBill img = imgs.get(0);
						temp.getImpExpCommodityInfo().setQuantity(obj[0]);
						temp.getImpExpCommodityInfo().setAmountPrice(obj[1]);
						temp.setDzscEmsImgBill(imgs.get(0));
						temp.setIsSelected(true);
						List<TempDzscImpExpCommodityInfo> temps = new ArrayList();
						tableModel.updateRow(temp);
						Double[] maps = numAndMoneyMap.get(getNumAndMoneyMapKey(img.getDzscEmsPorHead(),img.getTenSeqNum()));
						if(maps!=null){
							numAndMoneyMap.put(getNumAndMoneyMapKey(img.getDzscEmsPorHead(),img.getTenSeqNum()), new Double[]{obj[0]+maps[0]
							                                                                                                                                 ,obj[1]+maps[1]});
						}else{
							numAndMoneyMap.put(getNumAndMoneyMapKey(img.getDzscEmsPorHead(),img.getTenSeqNum()), new Double[]{obj[0],obj[1]});
						}
						sum[0]-=obj[0];
						sum[1]-=obj[1];
						bf.append("分配手册号："+temp.getDzscEmsImgBill().getDzscEmsPorHead().getEmsNo()+"\t数量："+obj[0]+"\t金额："+obj[1]+"\n");
						for(int i=1;i<imgs.size();i++){
							obj = remains.get(i);
							img = imgs.get(i);
//							bf.append("sum:"+sum[0]+"\t"+sum[1]+"\n");
							if(obj[0]>=sum[0]){
								obj[0] = sum[0];
							}
							if(obj[1]>=sum[1]){
								obj[1] = sum[1];
							}
							sum[0]-=obj[0];
							sum[1]-=obj[1];
							TempDzscImpExpCommodityInfo newTemp = new TempDzscImpExpCommodityInfo();
							ImpExpCommodityInfo newImpExpCommodityInfo = new  ImpExpCommodityInfo();
							newImpExpCommodityInfo.setMateriel(temp.getImpExpCommodityInfo().getMateriel());
							newTemp.setImpExpCommodityInfo(newImpExpCommodityInfo);
							newTemp.getImpExpCommodityInfo().setQuantity(obj[0]);
							newTemp.getImpExpCommodityInfo().setAmountPrice(obj[1]);
							newTemp.setDzscEmsImgBill(img);
						    newTemp.setIsSelected(true);
							 maps = numAndMoneyMap.get(getNumAndMoneyMapKey(img.getDzscEmsPorHead(),img.getTenSeqNum()));
								if(maps!=null){
									numAndMoneyMap.put(getNumAndMoneyMapKey(img.getDzscEmsPorHead(),img.getTenSeqNum()), new Double[]{obj[0]+maps[0]
									                                                                                                                                 ,obj[1]+maps[1]});
								}else{
									numAndMoneyMap.put(getNumAndMoneyMapKey(img.getDzscEmsPorHead(),img.getTenSeqNum()), new Double[]{obj[0],obj[1]});
								}
							 bf.append("分配手册号："+newTemp.getDzscEmsImgBill().getDzscEmsPorHead().getEmsNo()+"\t数量："+obj[0]+"\t金额："+obj[1]+"\n");
							 temps.add(newTemp);
						}
						for(TempDzscImpExpCommodityInfo newTemp : temps){
							tableModel.addRowAfter(temp, newTemp);
						}
						bf.append("自动分配成功！\n");
						return true;
					}
				 }
				 bf.append("没有满足所有条件，自动分配失败！\n");
				 return false;
			 }
		}else{//手动分配
			bf.append("手动分配，手册号："+dzscEmsPorHead.getEmsNo()+"\n");
			if(dzscEmsPorHead==null){
				JOptionPane.showMessageDialog(PnMakeDzscCustomsDeclaration3.this, "没有选择通关手册！");
				return false;
			}
			if(isProduct){//成品
				assignContractExg(temp,dzscEmsPorHead,temSeqNum);			
			}else{//料件
				assignContractImg(temp,dzscEmsPorHead,temSeqNum);
			}
		}
		//更新
		tableModel.updateRow(temp);
		return true;
	}
	
	/**
	 * 料件合同分配
	 * @return
	 */
	private boolean assignContractExg(TempDzscImpExpCommodityInfo temp,DzscEmsPorHead dzscEmsPorHead,Integer temSeqNum){
		//检测是否通关手册备案
		DzscEmsExgBill dzscEmsExgBill =dzsccontractExeAction.findDzscEmsExgBillBytenSeqNum(new Request(CommonVars.getCurrUser()),dzscEmsPorHead,temSeqNum);
		if(dzscEmsExgBill==null){
//			JOptionPane.showMessageDialog(PnMakeDzscCustomsDeclaration3.this, (isProduct?"成品":"料件")+"货号："
//			                                            +temp.getImpExpCommodityInfo().getMateriel().getPtNo()
//			                                            +"\n 归并序号："+temSeqNum
//			                                            +"\n在通关手册号："+dzscEmsPorHead.getEmsNo()
//			                                            +"\n没有注册！");
			bf.append("归并序号："+temSeqNum+"\t在通关手册号："+dzscEmsPorHead.getEmsNo()+"没有注册！分配失败...\n");
			return false;
		}
		Double[] remain = getExgNumAndMoneyRemain(dzscEmsExgBill);
		bf.append("手册剩余数量："+remain[0]+"\t手册剩余金额："+remain[1]+"\n");
		bf.append("申请数量："+temp.getImpExpCommodityInfo().getQuantity()+"\t申请金："+temp.getImpExpCommodityInfo().getAmountPrice()+"\n");
		if(!numAndMoneyTest(temp.getImpExpCommodityInfo(),remain)){
//			JOptionPane.showMessageDialog(PnMakeDzscCustomsDeclaration3.this, (isProduct?"成品":"料件")+"货号："
//                    +temp.getImpExpCommodityInfo().getMateriel().getPtNo()
//                    +"\n 归并序号："+temSeqNum
//                    +"\n在通关手册号："+dzscEmsPorHead.getEmsNo()
//                    +"\n数量或者金额不够，分配失败！");
			bf.append("归并序号："+temSeqNum+"\t在通关手册号："+dzscEmsPorHead.getEmsNo()+"的数量或者金额不够！分配失败...\n");
			return false;
		}
		//分配
		Double[] maps = numAndMoneyMap.get(getNumAndMoneyMapKey(dzscEmsPorHead,temSeqNum));
		if(maps!=null){
			numAndMoneyMap.put(getNumAndMoneyMapKey(dzscEmsPorHead,temSeqNum), new Double[]{temp.getImpExpCommodityInfo().getQuantity()+maps[0]
			                                                                                                                                 ,temp.getImpExpCommodityInfo().getAmountPrice()+maps[1]});
		}else{
			numAndMoneyMap.put(getNumAndMoneyMapKey(dzscEmsPorHead,temSeqNum), new Double[]{temp.getImpExpCommodityInfo().getQuantity(),temp.getImpExpCommodityInfo().getAmountPrice()});
		}
		temp.setDzscEmsExgBill(dzscEmsExgBill);
		temp.setIsSelected(true);
		bf.append("归并序号："+temSeqNum+"\t在通关手册号："+dzscEmsPorHead.getEmsNo()+"分配成功!\n");
		return true;
	}
	
	/**
	 * 料件合同分配
	 * @return
	 */
	private boolean assignContractImg(TempDzscImpExpCommodityInfo temp,DzscEmsPorHead dzscEmsPorHead,Integer temSeqNum){
		//检测是否通关手册备案
		DzscEmsImgBill dzscEmsImgBill =dzsccontractExeAction.findDzscEmsImgBillBytenSeqNum(new Request(CommonVars.getCurrUser()),dzscEmsPorHead,temSeqNum);
		if(dzscEmsImgBill==null){
//			JOptionPane.showMessageDialog(PnMakeDzscCustomsDeclaration3.this, (isProduct?"成品":"料件")+"货号："
//			                                            +temp.getImpExpCommodityInfo().getMateriel().getPtNo()
//			                                            +"\n 归并序号："+temSeqNum
//			                                            +"\n在通关手册号："+dzscEmsPorHead.getEmsNo()
//			                                            +"\n没有注册！");
			bf.append("归并序号："+temSeqNum+"\t在通关手册号："+dzscEmsPorHead.getEmsNo()+"没有注册！分配失败...\n");
			return false;
		}
		Double[] remain = getImgNumAndMoneyRemain(dzscEmsImgBill);
		bf.append("手册数量余量："+remain[0]+"\t手册金额余量："+remain[1]+"\n");
		bf.append("申请数量："+temp.getImpExpCommodityInfo().getQuantity()+"\t申请金："+temp.getImpExpCommodityInfo().getAmountPrice()+"\n");
		if(!numAndMoneyTest(temp.getImpExpCommodityInfo(),remain)){
//			JOptionPane.showMessageDialog(PnMakeDzscCustomsDeclaration3.this, (isProduct?"成品":"料件")+"货号："
//                    +temp.getImpExpCommodityInfo().getMateriel().getPtNo()
//                    +"\n 归并序号："+temSeqNum
//                    +"\n在通关手册号："+dzscEmsPorHead.getEmsNo()
//                    +"\n数量或者金额不够，分配失败！");
			bf.append("归并序号："+temSeqNum+"\t在通关手册号："+dzscEmsPorHead.getEmsNo()+"的数量或者金额不够！分配失败...\n");
			return false;
		}
		//分配
		Double[] maps = numAndMoneyMap.get(getNumAndMoneyMapKey(dzscEmsPorHead,temSeqNum));
		if(maps!=null){
			numAndMoneyMap.put(getNumAndMoneyMapKey(dzscEmsPorHead,temSeqNum), new Double[]{temp.getImpExpCommodityInfo().getQuantity()+maps[0]
			                                                                                                                                 ,temp.getImpExpCommodityInfo().getAmountPrice()+maps[1]});
		}else{
			numAndMoneyMap.put(getNumAndMoneyMapKey(dzscEmsPorHead,temSeqNum), new Double[]{temp.getImpExpCommodityInfo().getQuantity()
				,temp.getImpExpCommodityInfo().getAmountPrice()});
		}
		temp.setDzscEmsImgBill(dzscEmsImgBill);
		temp.setIsSelected(true);
		bf.append("归并序号："+temSeqNum+"\t在通关手册号："+dzscEmsPorHead.getEmsNo()+"分配成功!\n");
		return true;
	}
	
	/**
	 * 获取手册中的料件合同余量
	 * @return
	 */
	public Double[] getImgNumAndMoneyRemain(DzscEmsImgBill imgBill){
		//合同余量
		Double[] objs = {100d,100d};
		objs = dzsccontractExeAction.processImgRemainContractMountAndMoney(new Request(CommonVars.getCurrUser()),imgBill);
		//本次已经分配数量，金额
		Double[] maps = numAndMoneyMap.get(getNumAndMoneyMapKey(imgBill.getDzscEmsPorHead(),imgBill.getTenSeqNum()));
//		for(String key : numAndMoneyMap.keySet()){
//			bf.append("key="+key+"\tvalue="+numAndMoneyMap.get(key)+"\n");
//		}
		if(maps!=null){
			bf.append("本次已经分配数量："+maps[0]+"\t金额："+maps[1]+"\n");
			objs[0] -= maps[0];
			objs[1] -= maps[1];
		}
		bf.append("最终剩余数量："+objs[0]+"\t金额："+objs[1]+"\n");
		return objs;
	}
	
	/**
	 * 获取手册中的料件合同余量
	 * @return
	 */
	public Double[] getExgNumAndMoneyRemain(DzscEmsExgBill exgBill){
		//合同余量
//		Double[] objs = {100d,100d};
		Double[] objs = null;
		objs = dzsccontractExeAction.processImgRemainContractMountAndMoney(new Request(CommonVars.getCurrUser()),exgBill);
		//本次已经分配数量，金额
		Double[] maps = numAndMoneyMap.get(getNumAndMoneyMapKey(exgBill.getDzscEmsPorHead(),exgBill.getTenSeqNum()));
//		for(String key : numAndMoneyMap.keySet()){
//			bf.append("key="+key+"\tvalue="+numAndMoneyMap.get(key)+"\n");
//		}
		if(maps!=null){
			bf.append("map数据："+maps[0]+"\t"+maps[1]+"\n");
			objs[0] -= maps[0];
			objs[1] -= maps[1];
		}
//		bf.append("最终剩余："+objs[0]+"\t"+objs[1]+"\n");
		return objs;
	}
	
	/**
	 * 料件数量，金额对比
	 * true 合适
	 * false 不合适
	 */
	public boolean numAndMoneyTest(ImpExpCommodityInfo impExpCommodityInfo,Double[] objs){
		if((objs[0]-impExpCommodityInfo.getQuantity())<0 || (objs[1]-impExpCommodityInfo.getAmountPrice())<0){
			return false;
		}
		return true;
	}
	
	
	/**
	 * 多列文本数据呈现
	 */
	class MultiColumnTextListCellRenderer extends DefaultListCellRenderer {
		private JLabel	lbCode		= new JLabel();

		private JLabel	lbName		= new JLabel();

		private JLabel	lbSrtUsing	= new JLabel();

		private JLabel	lbSrtWeight	= new JLabel();

		private JLabel	lbMemo		= new JLabel();

		public MultiColumnTextListCellRenderer() {
			lbCode.setBounds(0, 0, 50, 20);
			this.add(lbCode);
			lbName.setBounds(50, 0, 80, 20);
			this.add(lbName);
			lbSrtUsing.setBounds(130, 0, 100, 20);
			this.add(lbSrtUsing);
			lbSrtWeight.setBounds(230, 0, 50, 20);
			this.add(lbSrtWeight);
			lbMemo.setBounds(280, 0, 80, 20);
			this.add(lbMemo);
		}

		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {

			super.getListCellRendererComponent(list, value, index, isSelected,
					cellHasFocus);
			if (value != null) {
				if (value instanceof DzscEmsExgBill) {
					DzscEmsExgBill obj = (DzscEmsExgBill) value;
					this.lbCode.setText(obj.getSeqNum() == null ? "" : String
							.valueOf(obj.getSeqNum()));
					this.lbName.setText(obj.getComplex().getCode());
					this.lbSrtUsing.setText(obj.getName());
					this.lbSrtWeight.setText(obj.getAmount() == null ? "0"
							: obj.getAmount().toString());
					// this.lbMemo.setText(obj.getBracketEnglishName());
				} else if (value instanceof DzscEmsImgBill) {
					DzscEmsImgBill obj = (DzscEmsImgBill) value;
					this.lbCode.setText(obj.getSeqNum() == null ? "" : String
							.valueOf(obj.getSeqNum()));
					this.lbName.setText(obj.getComplex().getCode());
					this.lbSrtUsing.setText(obj.getName());
					this.lbSrtWeight.setText(obj.getAmount() == null ? "0"
							: obj.getAmount().toString());
					// this.lbMemo.setText();
				}
			} else {
				this.lbCode.setText("");
				this.lbName.setText("");
				this.lbSrtUsing.setText("");
				this.lbSrtWeight.setText("");
			}
			this.setText("                     ");
			return this;
		}
	}

	/**
	 * 获得选中关封申请单中商品信息的信息
	 */
	public List getCommodityList() {
		if (this.tableModel == null) {
			return null;
		}
		List list = tableModel.getList();
		List newList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) instanceof TempDzscImpExpCommodityInfo) {
				TempDzscImpExpCommodityInfo temp = (TempDzscImpExpCommodityInfo) list
						.get(i);
				if (temp.getIsSelected()) {
					newList.add(temp);
				}
			}
		}
		return newList;
	}

	/**
	 * 设置无效数据着色
	 */
	private void setValidateDataColor(List checkList) {
		this.colorList = checkList;
		for (int i = 2; i < this.tableModel.getColumnCount(); i++) {
			jTable.getColumnModel().getColumn(i).setCellRenderer(
					new ColorTableCellRenderer());
		}
		jTable.repaint();
		jTable.validate();
	}

	/**
	 * render table color row
	 */
	class ColorTableCellRenderer extends DefaultTableCellRenderer {
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			Component c = super.getTableCellRendererComponent(table, value,
					isSelected, hasFocus, row, column);
			if (checkValue(table, row, column)) {
				c.setBackground(Color.BLUE);
				c.setForeground(Color.WHITE);
			} else {
				if (isSelected) {
					c.setForeground(table.getSelectionForeground());
					c.setBackground(table.getSelectionBackground());
				} else {
					c.setForeground(table.getForeground());
					c.setBackground(table.getBackground());
				}
			}
			return c;
		}
	}

	private boolean checkValue(JTable table, int row, int column) {
		if (colorList == null) {
			return false;
		}
		TempDzscImpExpCommodityInfo data = (TempDzscImpExpCommodityInfo) tableModel
				.getObjectByRow(row);
		for (int i = 0; i < colorList.size(); i++) {
			TempDzscImpExpCommodityInfo c = (TempDzscImpExpCommodityInfo) colorList
					.get(i);
			if (data.getImpExpCommodityInfo().getId().equals(
					c.getImpExpCommodityInfo().getId()) == true) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @return Returns the dzscEmsPorHead.
	 */
	public DzscEmsPorHead getDzscEmsPorHead() {
		return dzscEmsPorHead;
	}

	/**
	 * @param dzscEmsPorHead
	 *            The dzscEmsPorHead to set.
	 */
	public void setDzscEmsPorHead(DzscEmsPorHead dzscEmsPorHead) {
		this.dzscEmsPorHead = dzscEmsPorHead;
	}

	public boolean isProduct() {
		return isProduct;
	}

	public void setProduct(boolean isProduct) {
		this.isProduct = isProduct;
	}

	public boolean isAutoSelectContract() {
		return isAutoSelectContract;
	}

	public void setAutoSelectContract(boolean isAutoSelectContract) {
		this.isAutoSelectContract = isAutoSelectContract;
	}

	/**
	 * 验证
	 * 
	 * @return
	 */
	public boolean vaildateData() {
		boolean isProduct = this.materielProductType == Integer
				.parseInt(MaterielType.FINISHED_PRODUCT);
		List selectCommodityList = this.getCommodityList();
		if (selectCommodityList == null || selectCommodityList.size() <= 0) {
			JOptionPane.showMessageDialog(this, "请选择转成报关单的商品信息!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		List checkList = new ArrayList();
		for (int i = 0; i < selectCommodityList.size(); i++) {
			TempDzscImpExpCommodityInfo t = (TempDzscImpExpCommodityInfo) selectCommodityList
					.get(i);
			if (isProduct == true && t.getDzscEmsExgBill() == null) {
				checkList.add(t);
			} else if (isProduct == false && t.getDzscEmsImgBill() == null) {
				checkList.add(t);
			}
		}
		if (checkList.size() > 0) {
			JOptionPane.showMessageDialog(this, "选择的商品信息中,着色的记录没有对应的合同备案数据!",
					"提示", JOptionPane.INFORMATION_MESSAGE);
			setValidateDataColor(checkList);
			return false;
		}
		return true;
	}
	
	public void clearNumAndMoneyMap(){
		numAndMoneyMap.clear();
	}

	public boolean isNewRecord() {
		return isNewRecord;
	}

	public void setNewRecord(boolean isNewRecord) {
		this.isNewRecord = isNewRecord;
	}

	public void setDzscCustomsDeclaration(
			DzscCustomsDeclaration dzscCustomsDeclaration) {
		this.dzscCustomsDeclaration = dzscCustomsDeclaration;
	}
	
	
}  //  @jve:decl-index=0:visual-constraint="10,10"
