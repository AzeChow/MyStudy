/*
 * Created on 2004-7-10
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.manualdeclare;

import java.awt.BorderLayout;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.custombase.entity.basecode.ApplicationMode;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kFas;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kFasExg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kFasImg;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.DelcareType;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import javax.swing.JComboBox;
/**
 * @author Administrator
 * 
 * 电子分册备案维护
 * 2010-08-03 check by hcl
 */
public class DgEmsHeadH2kFas extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;	//主面板

	private JToolBar jToolBar = null;	//菜单按钮Bar

	private JButton btnAdd = null;	//新增按钮

	private JButton jbEdit = null;	//成品面板

	private JButton jbDelete = null;	//删除按钮

	private JButton jbSave = null;	//保存按钮

	private JButton jbExit = null;	//关闭按钮

	private JTabbedPane jTabbedPane = null; //分面板

	private JPanel pnExg = null;    //成品面板

	private JPanel jpImg = null;	//料件面板
	
	private JPanel jpHead = null;	//表头面板

	private JTextField jTextField2 = null;

	private JTextField jTextField3 = null;

	private JTextField jTextField4 = null;

	private JTextField jTextField6 = null;

	private JTextField jTextField7 = null;

	private JTextField jTextField8 = null;

	private JTextField jTextField9 = null;

	private JTextField jTextField10 = null;

	private JTextField jTextField12 = null;

	private JCalendarComboBox jCalendarComboBox1 = null;	//分册期限

	private JTextField jTextField16 = null;

	private JTextField jTextField17 = null;

	private JTextField jTextField18 = null;

	private JTextField jTextField19 = null;

	private JTextField jTextField21 = null;

	private JTextField jTextField22 = null;

	private List dataSourceBom = null;//数据源BOM

	private ManualDeclareAction manualdeclearAction = null;//ManualDeclareAction接口

	private JTableListModel tableModel = null;    //头
 
	private JTableListModel tableModelImg = null; //料件
 
   	private JTableListModel tableModelExg = null; //成品

	private boolean isChange = false; //判断是否变更状态

	private boolean isEdit = true;    //判断是否为编辑状态

	private JTextField jTextField24 = null;

	private JTable jTableImg = null;	//料件表

	private JScrollPane jScrollPane = null;

	private JTable jTableExg = null;	//成品表

	private JScrollPane jScrollPane1 = null;

	private int dataState = DataState.BROWSE;	//浏览状态
	private JComboBox jComboBox = null;//分册类型
	private JComboBox jComboBox1 = null;//进出口岸
	private JComboBox jComboBox2 = null;//申报标志
	private EmsHeadH2kFas fas = null;//电子分册
	private boolean isHistoryChange = false;
	/**
	 * 默认构造方法
	 */
	public DgEmsHeadH2kFas() {
		super();
		manualdeclearAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		initialize();
	}

	/**
	 * 初始化方法
	 * @return void
	 */
	private void initialize() {
		this.setSize(719, 503);
		this.setTitle("电子分册备案维护");
		this.setContentPane(getJContentPane());
		this.addWindowListener(new java.awt.event.WindowAdapter() {

			public void windowOpened(java.awt.event.WindowEvent e) {
				initUI();
				fas = (EmsHeadH2kFas) tableModel.getCurrentRow();
				if(fas==null){
					return;
				}
				fillWindow();
				//料件
				List imgList = null;
				imgList = manualdeclearAction.findEmsHeadH2kFasImg(
						new Request(CommonVars.getCurrUser()), fas);
				if (imgList != null && !imgList.isEmpty()) {
					initTableImg(imgList);
					
				} else {
					initTableImg(new Vector());
				}
				List exgList = null;
				exgList = manualdeclearAction.findEmsHeadH2kFasExg(
						new Request(CommonVars.getCurrUser()), fas);
				if (exgList != null && !exgList.isEmpty()) {
					initTableExg(exgList);					
				} else {
					initTableExg(new Vector());
				}
				if (fas.getDeclareState().equals(
						DeclareState.PROCESS_EXE) || (fas.getDeclareState().equals(
								DeclareState.WAIT_EAA)) || isHistoryChange == true) {
					dataState = DataState.READONLY;
				} else{
					dataState = DataState.BROWSE;
				}
				setState();
				setEmsTitle();
			}
		});
	}
	/**
	 * 初始化UI
	 */
    private void initUI(){
    	//分册类型
    	this.jComboBox.removeAllItems();
		this.jComboBox.addItem(new ItemProperty("2", "异地报关分册"));
		this.jComboBox.addItem(new ItemProperty("3", "异地结转分册"));
		this.jComboBox
				.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.jComboBox);
		//进出口岸
		List list = CustomBaseList.getInstance().getCustoms();
		this.jComboBox1.setModel(new DefaultComboBoxModel(list.toArray()));
		this.jComboBox1.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.jComboBox1, "code", "name");
		//申报标志
		List list1 = CustomBaseList.getInstance().getDModes();
		this.jComboBox2.setModel(new DefaultComboBoxModel(list1.toArray()));
		this.jComboBox2.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.jComboBox2, "code", "name");
    }
    /**
     * 初始化料件表
     * @param list
     */
	private void initTableImg(final List list) {
		tableModelImg = new JTableListModel(jTableImg, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List <JTableListColumn>list = new Vector<JTableListColumn>();
						list.add(addColumn("修改标志","modifyMark",60));
						list.add(addColumn("料件序号", "seqNum", 60, Integer.class));
						list.add(addColumn("商品编码", "complex.code", 80));
						list.add(addColumn("商品名称", "name", 100));
						list.add(addColumn("型号规格", "spec", 100));
						list.add(addColumn("计量单位", "unit.name", 80));
						list.add(addColumn("币制", "curr.name", 60));
						list.add(addColumn("允许数量", "allowAmount", 80));
						list.add(addColumn("企业申报单价", "declarePrice", 80));
						list.add(addColumn("备注", "note", 100));
						return list;
					}
				});
		EmsEdiMergerClientLogic.transModifyMark(jTableImg);	
	}
/**
 * 初始化成品表
 * @param list
 */
	private void initTableExg(final List list) {
		tableModelExg = new JTableListModel(jTableExg, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List <JTableListColumn>list = new Vector<JTableListColumn>();
						list.add(addColumn("修改标志","modifyMark",60));
						list.add(addColumn("成品序号", "seqNum", 60, Integer.class));
						list.add(addColumn("商品编码", "complex.code", 80));
						list.add(addColumn("商品名称", "name", 100));
						list.add(addColumn("型号规格", "spec", 100));
						list.add(addColumn("计量单位", "unit.name", 80));
						list.add(addColumn("币制", "curr.name", 60));
						list.add(addColumn("允许数量", "allowAmount", 80));
						list.add(addColumn("企业申报单价", "declarePrice", 80));
						list.add(addColumn("备注", "note", 100));
						return list;
					}
				});
		EmsEdiMergerClientLogic.transModifyMark(jTableExg);
	}

	/**
	 * 设置窗口标题名称
	 */
	private void setEmsTitle() {
		if (DgEmsHeadH2kFas.this.isChange) { //变更状态
			if (dataState == DataState.EDIT)
				DgEmsHeadH2kFas.this.setTitle("电子分册申请变更维护");
			else
				DgEmsHeadH2kFas.this.setTitle("电子分册申请变更浏览");
		} else if (DgEmsHeadH2kFas.this.isChange == false) { //备案状态
			if (dataState == DataState.EDIT)
				DgEmsHeadH2kFas.this.setTitle("电子分册申请备案维护");
			else
				DgEmsHeadH2kFas.this.setTitle("电子分册申请备案浏览");
		}
	}
	/**
	 * 按分册数据填充内容
	 */
	private void fillWindow() {
		//分册类型
		if (fas.getEmsType() != null){
			jComboBox.setSelectedIndex(ItemProperty.getIndexByCode(String
					.valueOf(this.fas.getEmsType()), jComboBox));
		} else {
			jComboBox.setSelectedIndex(-1);
		}
		//进出口岸
		if (fas.getIePort() != null){
			jComboBox1.setSelectedItem(fas.getIePort());
		} else {
			jComboBox1.setSelectedItem(null);
		}
		//申报标志
		if (fas.getDeclareMark() != null){
			jComboBox2.setSelectedItem(fas.getDeclareMark());
		} else {
			jComboBox2.setSelectedItem(null);
		}
		if (fas.getDeclareType().equals("1"))
			jTextField2.setText("备案申请");
		else
			jTextField2.setText("备案变更");
		jTextField12.setText(fas.getEmsNo()); //分册号
		jTextField6.setText(fas.getTradeCode());
		jTextField7.setText(fas.getTradeName());
		jTextField8.setText(fas.getDeclareCode());
		jTextField9.setText(fas.getDeclareName());
		jTextField4.setText(fas.getEmsHeadH2kNo());
		jTextField10.setText(String.valueOf(fas.getModifyTimes()));
		jTextField3.setText(fas.getCopEmsNo());
		jCalendarComboBox1.setDate(fas.getLimitDate());
		jTextField16.setText(fas.getInputEr());
		jTextField17.setText(CommonVars.dateToString(fas.getInputDate()));
		jTextField19.setText(CommonVars.dateToString(fas.getDeclareDate()));
		jTextField24.setText(CommonVars.dateToTimeString(fas.getDeclareTime()));
		jTextField22.setText(fas.getNote());
		jTextField18.setText(CommonVars.dateToString(fas.getNewApprDate()));
		jTextField21.setText(CommonVars.dateToString(fas.getChangeApprDate()));

	}

	/**
	 * 获取jContentPane
	 * @return javax.swing.JPanel
	 */

	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new java.awt.BorderLayout());
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJTabbedPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * 
	 * 获取jToolBar
	 * @return javax.swing.JToolBar
	 *  
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getBtnAdd());
			jToolBar.add(getJbEdit());
			jToolBar.add(getJbDelete());
			jToolBar.add(getJbSave());
			jToolBar.add(getJbExit());
		}
		return jToolBar;
	}

	/**
	 * 
	 * 获取并初始化新增按钮
	 * @return javax.swing.JButton
	 *  
	 */
	private JButton getBtnAdd() { //新增料件/成品
		if (btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setText("新增");
			btnAdd.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					List listH2k = manualdeclearAction.findEmsHeadH2k(new Request(
			                CommonVars.getCurrUser()));
			        EmsHeadH2k emsHead = null;
			        for (int i = 0; i < listH2k.size(); i++) {
			            if (((EmsHeadH2k) listH2k.get(i)).getDeclareState().equals(
			                    DeclareState.PROCESS_EXE))
			                emsHead = (EmsHeadH2k) listH2k.get(i); //获取正在执行的电子帐册
			        }
			        if (emsHead==null){
						JOptionPane.showMessageDialog(DgEmsHeadH2kFas.this,"没有数据可以获得，电子帐册没有正在执行的数据!","提示",2);
						return;
					}
					if (jTabbedPane.getSelectedIndex() == 1)//料件
					{					
						EmsHeadH2kFasImg emsHeadH2kFasImg = null;
						List list = (List) CommonQuery.getInstance()
								.getEmsH2kImg( //电子帐册料件
										false, fas,emsHead);
						if (list == null || list.isEmpty())
							return;
						for (int i = 0; i < list.size(); i++) {
							emsHeadH2kFasImg = (EmsHeadH2kFasImg) list.get(i);
							emsHeadH2kFasImg.setEmsHeadH2kFas(fas);
							/*if (fas.getDeclareType().equals(DelcareType.APPLICATION))
								emsHeadH2kFasImg.setModifyMark(ModifyMarkState.UNCHANGE);
							else*/
								emsHeadH2kFasImg.setModifyMark(ModifyMarkState.ADDED);
							emsHeadH2kFasImg.setCompany(CommonVars.getCurrUser()
									.getCompany());
							emsHeadH2kFasImg = manualdeclearAction
									.saveEmsHeadH2kFasImg(new Request(CommonVars
											.getCurrUser()), emsHeadH2kFasImg);
							tableModelImg.addRow(emsHeadH2kFasImg);
							EmsEdiMergerClientLogic.transModifyMark(jTableImg);
						}
					} else if (jTabbedPane.getSelectedIndex() == 2) //成品
					{
						EmsHeadH2kFasExg emsHeadH2kFasExg = null;
						List list = (List) CommonQuery.getInstance()
								.getEmsH2kExg( //电子帐册料件
										false, fas ,emsHead);
						if (list == null || list.isEmpty())
							return;
						for (int i = 0; i < list.size(); i++) {
							emsHeadH2kFasExg = (EmsHeadH2kFasExg) list.get(i);
							emsHeadH2kFasExg.setEmsHeadH2kFas(fas);
							/*if (fas.getDeclareType().equals(DelcareType.APPLICATION))
								emsHeadH2kFasExg.setModifyMark(ModifyMarkState.UNCHANGE);
							else*/
								emsHeadH2kFasExg.setModifyMark(ModifyMarkState.ADDED);
							emsHeadH2kFasExg.setCompany(CommonVars.getCurrUser()
									.getCompany());
							emsHeadH2kFasExg = manualdeclearAction
									.saveEmsHeadH2kFasExg(new Request(CommonVars
											.getCurrUser()), emsHeadH2kFasExg);
							tableModelExg.addRow(emsHeadH2kFasExg);
							EmsEdiMergerClientLogic.transModifyMark(jTableExg);
						}
					}
					setState();
				}
			});

		}
		return btnAdd;
	}

	/**
	 * 
	 * 
	 * 获取并初始化修改按钮
	 * @return javax.swing.JButton
	 *  
	 */
	private JButton getJbEdit() {
		if (jbEdit == null) {
			jbEdit = new JButton();
			jbEdit.setText("修改");
			jbEdit.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
				    if (jTabbedPane.getSelectedIndex() == 0) {
						dataState = DataState.EDIT;
						setState();
					} else {
						DgEmsHeadH2kFasImgExg dgEmsHeadH2kImgExg = new DgEmsHeadH2kFasImgExg();
						if (jTabbedPane.getSelectedIndex() == 1)//料件
						{
							if (tableModelImg.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(
										DgEmsHeadH2kFas.this, "请选择你要修改的料件资料",
										"确认", 1);
								return;
							}
							dgEmsHeadH2kImgExg.setImg(true);
							dgEmsHeadH2kImgExg.setTableModel(tableModelImg);
						} else if (jTabbedPane.getSelectedIndex() == 2) {//成品
							if (tableModelExg.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(
										DgEmsHeadH2kFas.this, "请选择你要修改的成品资料",
										"确认", 1);
								return;
							}
							dgEmsHeadH2kImgExg.setImg(false);
							dgEmsHeadH2kImgExg.setTableModel(tableModelExg);
						}
						dgEmsHeadH2kImgExg.setEmsHeadH2kFas(fas);
						dgEmsHeadH2kImgExg.setVisible(true);
					}
				}
			});

		}
		return jbEdit;
	}

	/**
	 * 
	 * 获取并初始化删除按钮
	 * 
	 * @return javax.swing.JButton
	 *  
	 */
	private JButton getJbDelete() { //删除料件/成品
		if (jbDelete == null) {
			jbDelete = new JButton();
			jbDelete.setText("删除");
			jbDelete.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					switch (getJTabbedPane().getSelectedIndex()) {
					case 0:
						break;
					case 1:
						EmsHeadH2kFasImg currRowImg = (EmsHeadH2kFasImg) tableModelImg
								.getCurrentRow();
						if (currRowImg == null) {
							JOptionPane.showMessageDialog(DgEmsHeadH2kFas.this,
									"请选择要删除的记录", "删除提示", 1);
							return;
						}
						if (JOptionPane.showConfirmDialog(DgEmsHeadH2kFas.this,
								"您确认要删除所选料件吗？", "删除提示", 0) == 0) {
							if (fas.getDeclareType().equals(DelcareType.APPLICATION) ||
									currRowImg.getModifyMark().equals(ModifyMarkState.ADDED)){
							manualdeclearAction.deleteEmsHeadH2kFasImg(
									new Request(CommonVars.getCurrUser()),
									currRowImg);
							tableModelImg.deleteRow(currRowImg);
							} else
							{
								currRowImg.setModifyMark(ModifyMarkState.DELETED);	
								currRowImg = manualdeclearAction
								.saveEmsHeadH2kFasImg(new Request(CommonVars
										.getCurrUser()), currRowImg);
								tableModelImg.updateRow(currRowImg);
							}
						}
						break;
					case 2:
						EmsHeadH2kFasExg currRowExg = (EmsHeadH2kFasExg) tableModelExg
								.getCurrentRow();
						if (currRowExg == null) {
							JOptionPane.showMessageDialog(DgEmsHeadH2kFas.this,
									"请选择要删除的记录", "删除提示", 1);
							return;
						}
						if (JOptionPane.showConfirmDialog(DgEmsHeadH2kFas.this,
								"您确认要删除所选成品吗？", "删除提示", 0) == 0) {
							if (fas.getDeclareType().equals(DelcareType.APPLICATION) ||
									currRowExg.getModifyMark().equals(ModifyMarkState.ADDED)){
							manualdeclearAction.deleteEmsHeadH2kFasExg(
									new Request(CommonVars.getCurrUser()),
									currRowExg);
							tableModelExg.deleteRow(currRowExg);
							} else
							{
								currRowExg.setModifyMark(ModifyMarkState.DELETED);	
								currRowExg = manualdeclearAction
								.saveEmsHeadH2kFasExg(new Request(CommonVars
										.getCurrUser()), currRowExg);
								tableModelExg.updateRow(currRowExg);
							}
						}
						break;
					}
					setState();
				}
			});

		}
		return jbDelete;
	}

	/**
	 * 
	 * 
	 * 
	 * 获取并初始化保存按钮
	 * @return javax.swing.JButton
	 *  
	 */
	private JButton getJbSave() {
		if (jbSave == null) {
			jbSave = new JButton();
			jbSave.setText("保存");
			jbSave.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					//保存
					fillEmsHeadH2kFas(fas);
					fas = manualdeclearAction.saveEmsHeadH2kFas(
							new Request(CommonVars.getCurrUser()), fas);
					tableModel.updateRow(fas);
					dataState = DataState.BROWSE;
					setState();

				}
			});

		}
		return jbSave;
	}
	/**
	 * 将表头表格内容等信息填充到电子分册中
	 * @param fas
	 */
	private void fillEmsHeadH2kFas(EmsHeadH2kFas fas) {
		EmsHeadH2kFas emsHeadOld = new EmsHeadH2kFas();
		emsHeadOld = (EmsHeadH2kFas) fas.clone();
		if (jComboBox.getSelectedItem() != null){
			fas.setEmsType(((ItemProperty) this.jComboBox.getSelectedItem())
					.getCode());
		}
		fas.setLimitDate(jCalendarComboBox1.getDate());
		if (jComboBox1.getSelectedItem() != null){
			fas.setIePort((Customs) jComboBox1.getSelectedItem());
		}
		if (jComboBox2.getSelectedItem() != null){
			fas.setDeclareMark((ApplicationMode) jComboBox2.getSelectedItem());
		}
		fas.setNote(jTextField22.getText());
		if (!fas.fullEquals(emsHeadOld) && fas.getDeclareType().equals(DelcareType.MODIFY)){
			fas.setModifyMark(ModifyMarkState.MODIFIED);  //打上已修改标志，
		}
	}

	/**
	 * 
	 * 
	 * 
	 * 获取并初始化关闭按钮
	 * @return javax.swing.JButton
	 *  
	 */
	private JButton getJbExit() {
		if (jbExit == null) {
			jbExit = new JButton();
			jbExit.setText("关闭");
			jbExit.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgEmsHeadH2kFas.this.dispose();

				}
			});

		}
		return jbExit;
	}

	/**
	 * 
	 * 
	 * 
	 * 获取并初始化jTabbedPane
	 * @return javax.swing.JTabbedPane
	 *  
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
			jTabbedPane.addTab("基本资料", null, getJpHead(), null);
			jTabbedPane.addTab("料件表", null, getJpImg(), null);
			jTabbedPane.addTab("成品表", null, getPnExg(), null);
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
	 * 
	 * 
	 * 获取并初始化成品面板
	 * @return javax.swing.JPanel
	 *  
	 */
	private JPanel getPnExg() {
		if (pnExg == null) {
			pnExg = new JPanel();
			pnExg.setLayout(new BorderLayout());
			pnExg.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
		}
		return pnExg;
	}

	/**
	 * 
	 * 
	 * 
	 * 获取并初始化料件面板
	 * @return javax.swing.JPanel
	 *  
	 */
	private JPanel getJpImg() {
		if (jpImg == null) {
			jpImg = new JPanel();
			jpImg.setLayout(new BorderLayout());
			jpImg.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jpImg;
	}

	/**
	 * 
	 * 
	 * 
	 * 获取并初始化表头面板
	 * @return javax.swing.JPanel
	 *  
	 */
	private JPanel getJpHead() {
		if (jpHead == null) {
			javax.swing.JLabel jLabel30 = new JLabel();

			javax.swing.JLabel jLabel27 = new JLabel();

			javax.swing.JLabel jLabel26 = new JLabel();

			javax.swing.JLabel jLabel24 = new JLabel();

			javax.swing.JLabel jLabel23 = new JLabel();

			javax.swing.JLabel jLabel22 = new JLabel();

			javax.swing.JLabel jLabel21 = new JLabel();

			javax.swing.JLabel jLabel19 = new JLabel();

			javax.swing.JLabel jLabel17 = new JLabel();

			javax.swing.JLabel jLabel15 = new JLabel();

			javax.swing.JLabel jLabel14 = new JLabel();

			javax.swing.JLabel jLabel11 = new JLabel();

			javax.swing.JLabel jLabel10 = new JLabel();

			javax.swing.JLabel jLabel9 = new JLabel();

			javax.swing.JLabel jLabel8 = new JLabel();

			javax.swing.JLabel jLabel7 = new JLabel();

			javax.swing.JLabel jLabel6 = new JLabel();

			javax.swing.JLabel jLabel4 = new JLabel();

			javax.swing.JLabel jLabel3 = new JLabel();

			javax.swing.JLabel jLabel2 = new JLabel();

			jpHead = new JPanel();
			jpHead.setLayout(null);
			jLabel2.setBounds(466, 71, 62, 19);
			jLabel2.setText("申报类型");
			jLabel3.setBounds(81, 41, 76, 20);
			jLabel3.setText("企业内部编号");
			jLabel4.setBounds(279, 41, 64, 20);
			jLabel4.setText("帐册编号");
			jLabel6.setBounds(81, 95, 74, 20);
			jLabel6.setText("经营单位代码");
			jLabel7.setBounds(279, 96, 80, 21);
			jLabel7.setText("经营单位名称");
			jLabel8.setBounds(81, 123, 74, 21);
			jLabel8.setText("申报单位代码");
			jLabel9.setBounds(279, 124, 80, 20);
			jLabel9.setText("申报单位名称");
			jLabel10.setBounds(81, 153, 57, 21);
			jLabel10.setText("分册期限");
			jLabel11.setBounds(279, 154, 55, 19);
			jLabel11.setText("进出口岸");
			jLabel14.setBounds(466, 156, 59, 21);
			jLabel14.setText("申报标志");
			jLabel15.setBounds(81, 69, 75, 20);
			jLabel15.setText("分册类型");
			jLabel17.setBounds(279, 68, 85, 20);
			jLabel17.setText("分册号");
			jLabel19.setBounds(466, 41, 65, 19);
			jLabel19.setText("变更次数");
			jLabel21.setBounds(81, 185, 67, 19);
			jLabel21.setText("录入员代号");
			jLabel22.setBounds(279, 187, 51, 18);
			jLabel22.setText("录入日期");
			jLabel23.setBounds(81, 217, 74, 23);
			jLabel23.setText("备案批准日期");
			jLabel24.setBounds(466, 186, 53, 22);
			jLabel24.setText("申报日期");
			jLabel26.setBounds(279, 220, 82, 20);
			jLabel26.setText("变更批准日期");
			jLabel27.setBounds(81, 256, 49, 19);
			jLabel27.setText("备注");
			jLabel30.setBounds(466, 220, 51, 20);
			jLabel30.setText("申报时间");
			jpHead.add(jLabel2, null);
			jpHead.add(getJTextField2(), null);
			jpHead.add(jLabel3, null);
			jpHead.add(getJTextField3(), null);
			jpHead.add(jLabel4, null);
			jpHead.add(getJTextField4(), null);
			jpHead.add(jLabel6, null);
			jpHead.add(getJTextField6(), null);
			jpHead.add(jLabel7, null);
			jpHead.add(getJTextField7(), null);
			jpHead.add(jLabel8, null);
			jpHead.add(getJTextField8(), null);
			jpHead.add(jLabel9, null);
			jpHead.add(getJTextField9(), null);
			jpHead.add(jLabel10, null);
			jpHead.add(getJTextField10(), null);
			jpHead.add(jLabel11, null);
			jpHead.add(getJTextField12(), null);
			jpHead.add(getJCalendarComboBox1(), null);
			jpHead.add(jLabel14, null);
			jpHead.add(jLabel15, null);
			jpHead.add(jLabel17, null);
			jpHead.add(jLabel19, null);
			jpHead.add(jLabel21, null);
			jpHead.add(getJTextField16(), null);
			jpHead.add(jLabel22, null);
			jpHead.add(getJTextField17(), null);
			jpHead.add(jLabel23, null);
			jpHead.add(getJTextField18(), null);
			jpHead.add(jLabel24, null);
			jpHead.add(getJTextField19(), null);
			jpHead.add(jLabel26, null);
			jpHead.add(getJTextField21(), null);
			jpHead.add(jLabel27, null);
			jpHead.add(jLabel30, null);
			jpHead.add(getJTextField24(), null);
			jpHead.add(getJTextField22(), null);
			jpHead.add(getJComboBox(), null);
			jpHead.add(getJComboBox1(), null);
			jpHead.add(getJComboBox2(), null);
		}
		return jpHead;
	}

	/**
	 * 
	 * 
	 * 
	 * 获取并初始化jTextField2
	 * @return javax.swing.JTextField
	 *  
	 */
	private JTextField getJTextField2() {
		if (jTextField2 == null) {
			jTextField2 = new JTextField();
			jTextField2.setBounds(539, 70, 91, 22);
			jTextField2.setEditable(false);
		}
		return jTextField2;
	}

	/**
	 * 
	 * 
	 * 
	 *  获取并初始化jTextField3
	 * @return javax.swing.JTextField
	 *  
	 */
	private JTextField getJTextField3() {
		if (jTextField3 == null) {
			jTextField3 = new JTextField();
			jTextField3.setBounds(155, 41, 94, 22);
			jTextField3.setEditable(false);
		}
		return jTextField3;
	}

	/**
	 * 
	 * 
	 * 
	 *  获取并初始化jTextField4
	 * @return javax.swing.JTextField
	 *  
	 */
	private JTextField getJTextField4() {
		if (jTextField4 == null) {
			jTextField4 = new JTextField();
			jTextField4.setBounds(362, 41, 93, 22);
			jTextField4.setEditable(false);
		}
		return jTextField4;
	}

	/**
	 * 
	 * 
	 * 
	 *  获取并初始化jTextField6
	 * @return javax.swing.JTextField
	 *  
	 */
	private JTextField getJTextField6() {
		if (jTextField6 == null) {
			jTextField6 = new JTextField();
			jTextField6.setBounds(155, 95, 94, 22);
			jTextField6.setEditable(false);
		}
		return jTextField6;
	}

	/**
	 * 
	 * 
	 * 
	 *  获取并初始化jTextField7
	 * @return javax.swing.JTextField
	 *  
	 */
	private JTextField getJTextField7() {
		if (jTextField7 == null) {
			jTextField7 = new JTextField();
			jTextField7.setBounds(362, 96, 269, 22);
			jTextField7.setEditable(false);
		}
		return jTextField7;
	}

	/**
	 * 
	 * 
	 * 
	 * 获取并初始化jTextField8
	 * @return javax.swing.JTextField
	 *  
	 */
	private JTextField getJTextField8() {
		if (jTextField8 == null) {
			jTextField8 = new JTextField();
			jTextField8.setBounds(155, 123, 93, 22);
			jTextField8.setEditable(false);
		}
		return jTextField8;
	}

	/**
	 * 
	 * 
	 * 
	 * 获取并初始化jTextField9
	 * @return javax.swing.JTextField
	 *  
	 */
	private JTextField getJTextField9() {
		if (jTextField9 == null) {
			jTextField9 = new JTextField();
			jTextField9.setBounds(362, 124, 268, 22);
			jTextField9.setEditable(false);
		}
		return jTextField9;
	}

	/**
	 * 
	 * 
	 * 
	 * 获取并初始化jTextField10
	 * @return javax.swing.JTextField
	 *  
	 */
	private JTextField getJTextField10() {
		if (jTextField10 == null) {
			jTextField10 = new JTextField();
			jTextField10.setEditable(false);
			jTextField10.setBounds(539, 41, 92, 22);

		}
		return jTextField10;
	}

	/**
	 * 
	 * 
	 * 
	 * 获取并初始化jTextField12
	 * @return javax.swing.JTextField
	 *  
	 */
	private JTextField getJTextField12() {
		if (jTextField12 == null) {
			jTextField12 = new JTextField();
			jTextField12.setBounds(362, 68, 93, 22);
			jTextField12.setEditable(false);
		}
		return jTextField12;
	}

	/**
	 * 
	 * 
	 * 
	 * 获取并初始化jCalendarComboBox1
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 *  
	 */
	private JCalendarComboBox getJCalendarComboBox1() {
		if (jCalendarComboBox1 == null) {
			jCalendarComboBox1 = new JCalendarComboBox();
			jCalendarComboBox1.setBounds(155, 154, 94, 22);
		}
		return jCalendarComboBox1;
	}

	/**
	 * 
	 * 
	 * 
	 *  获取并初始化jTextField16
	 * @return javax.swing.JTextField
	 *  
	 */
	private JTextField getJTextField16() {
		if (jTextField16 == null) {
			jTextField16 = new JTextField();
			jTextField16.setBounds(155, 184, 94, 22);
			jTextField16.setEditable(false);
		}
		return jTextField16;
	}

	/**
	 * 
	 * 
	 * 
	 * 获取并初始化jTextField17
	 * @return javax.swing.JTextField
	 *  
	 */
	private JTextField getJTextField17() {
		if (jTextField17 == null) {
			jTextField17 = new JTextField();
			jTextField17.setBounds(362, 187, 94, 22);
			jTextField17.setEditable(false);
		}
		return jTextField17;
	}

	/**
	 * 
	 * 
	 * 
	 * 获取并初始化jTextField18
	 * @return javax.swing.JTextField
	 *  
	 */
	private JTextField getJTextField18() {
		if (jTextField18 == null) {
			jTextField18 = new JTextField();
			jTextField18.setBounds(155, 218, 94, 22);
			jTextField18.setEditable(false);
		}
		return jTextField18;
	}

	/**
	 * 
	 * 
	 * 
	 * 获取并初始化jTextField19
	 * @return javax.swing.JTextField
	 *  
	 */
	private JTextField getJTextField19() {
		if (jTextField19 == null) {
			jTextField19 = new JTextField();
			jTextField19.setBounds(539, 186, 91, 22);
			jTextField19.setEditable(false);
		}
		return jTextField19;
	}

	/**
	 * 
	 * 
	 * 获取并初始化jTextField21
	 * @return javax.swing.JTextField
	 *  
	 */
	private JTextField getJTextField21() {
		if (jTextField21 == null) {
			jTextField21 = new JTextField();
			jTextField21.setBounds(362, 218, 94, 22);
			jTextField21.setEditable(false);
		}
		return jTextField21;
	}

	/**
	 * 
	 * 
	 * 
	 * 获取并初始化jTextField22
	 * @return javax.swing.JTextField
	 *  
	 */
	private JTextField getJTextField22() {
		if (jTextField22 == null) {
			jTextField22 = new JTextField();
			jTextField22.setBounds(155, 256, 479, 22);
		}
		return jTextField22;
	}

	/**
	 * 获取tableModel
	 * @return Returns the tableModel.
	 */
	public JTableListModel getTableModel() {
		return tableModel;
	}

	/**
	 * 设置tableModel
	 * @param tableModel
	 *            The tableModel to set.
	 */
	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
	}

	/**
	 * 获取isChange
	 * @return Returns the isChange.
	 */
	public boolean isChange() {
		return isChange;
	}

	/**
	 * 设置isChange
	 * @param isChange
	 *            The isChange to set.
	 */
	public void setChange(boolean isChange) {
		this.isChange = isChange;
	}

	/**
	 * 获取isEdit
	 * @return Returns the isEdit.
	 */
	public boolean isEdit() {
		return isEdit;
	}

	/**
	 * 设置isEdit
	 * @param isEdit
	 *            The isEdit to set.
	 */
	public void setEdit(boolean isEdit) {
		this.isEdit = isEdit;
	}

	/**
	 * 
	 * 
	 * 
	 * 获取并初始化jTextField24
	 * @return javax.swing.JTextField
	 *  
	 */
	private JTextField getJTextField24() {
		if (jTextField24 == null) {
			jTextField24 = new JTextField();
			jTextField24.setBounds(539, 222, 93, 22);
			jTextField24.setEditable(false);
		}
		return jTextField24;
	}

	/**
	 * 
	 * 
	 * 
	 * 获取并初始化jTableImg
	 * @return javax.swing.JTable
	 *  
	 */
	private JTable getJTableImg() {
		if (jTableImg == null) {
			jTableImg = new JTable();
		}
		return jTableImg;
	}

	/**
	 * 
	 * 
	 * 
	 * 获取并初始化jScrollPane
	 * @return javax.swing.JScrollPane
	 *  
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTableImg());
		}
		return jScrollPane;
	}

	/**
	 * 
	 * 
	 * 
	 * 获取成品jTableExg
	 * @return javax.swing.JTable
	 *  
	 */
	private JTable getJTableExg() {
		if (jTableExg == null) {
			jTableExg = new JTable();
		}
		return jTableExg;
	}

	/**
	 * 
	 * 
	 * 
	 * 获取并初始化jScrollPane1
	 * @return javax.swing.JScrollPane
	 *  
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJTableExg());
		}
		return jScrollPane1;
	}
	/**
	 * 设置浏览、修改状态
	 */
	private void setState() {
		jTabbedPane.setEnabled((fas.getDeclareState().equals(DeclareState.APPLY_POR) && dataState == DataState.BROWSE)
				||  !fas.getDeclareState().equals(DeclareState.APPLY_POR));
		btnAdd.setEnabled(!(jTabbedPane.getSelectedIndex() == 0)
				&& (dataState != DataState.READONLY)); //新增
		jbEdit.setEnabled((dataState == DataState.BROWSE && (jTabbedPane
						.getSelectedIndex() == 0))
						|| ((dataState != DataState.READONLY) && isImgExgPageAndExistData()));//修改
		jbDelete.setEnabled((dataState != DataState.READONLY)
				&& isImgExgPageAndExistData()); //删除
		jbSave.setEnabled((jTabbedPane.getSelectedIndex() == 0)
				&& dataState != DataState.BROWSE && dataState != DataState.READONLY); //保存	
		jComboBox.setEnabled(dataState != DataState.READONLY
				&& dataState != DataState.BROWSE && !DgEmsHeadH2kFas.this.isChange);
		jCalendarComboBox1.setEnabled(dataState != DataState.READONLY
				&& dataState != DataState.BROWSE);
		jComboBox1.setEnabled(dataState != DataState.READONLY
				&& dataState != DataState.BROWSE);
		jComboBox2.setEnabled(dataState != DataState.READONLY
				&& dataState != DataState.BROWSE);
		jTextField22.setEditable(dataState != DataState.READONLY
				&& dataState != DataState.BROWSE);
		
	}
	/**
	 * 获取是有成品或了解数据
	 * @return
	 */
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
	 * 	获取并初始化jComboBox
	 * @return javax.swing.JComboBox	
	 */    
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setBounds(154, 68, 95, 22);
		}
		return jComboBox;
	}

	/**
	 * 	获取并初始化jComboBox1
	 * @return javax.swing.JComboBox	

	 */    
	private JComboBox getJComboBox1() {
		if (jComboBox1 == null) {
			jComboBox1 = new JComboBox();
			jComboBox1.setBounds(362, 154, 94, 23);
		}
		return jComboBox1;
	}

	/**
	 * 	获取并初始化jComboBox2
	 * @return javax.swing.JComboBox	
	 */    
	private JComboBox getJComboBox2() {
		if (jComboBox2 == null) {
			jComboBox2 = new JComboBox();
			jComboBox2.setBounds(538, 154, 92, 23);
		}
		return jComboBox2;
	}

	/**
	 * 获取isHistoryChange
	 * @return Returns the isHistoryChange.
	 */
	public boolean isHistoryChange() {
		return isHistoryChange;
	}
	/**
	 * 设置isHistoryChange
	 * @param isHistoryChange The isHistoryChange to set.
	 */
	public void setHistoryChange(boolean isHistoryChange) {
		this.isHistoryChange = isHistoryChange;
	}
   } //  @jve:decl-index=0:visual-constraint="10,5"
