/*
 * Created on 2004-7-10
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.client.dzscmanage;

import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.custombase.entity.basecode.ApplicationMode;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.parametercode.BargainType;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.common.constant.DelcareType;
import com.bestway.common.constant.DzscState;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.dzsc.dzscmanage.action.DzscAction;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHeadFas;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgDzscFascicule extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JButton jbSave = null;

	private JButton jbExit = null;

	private JPanel jpHead = null;

	private JTextField jTextField2 = null;

	private JTextField jTextField3 = null;

	private JTextField jTextField4 = null;

	private JTextField jTextField6 = null;

	private JTextField jTextField7 = null;

	private JTextField jTextField8 = null;

	private JTextField jTextField9 = null;

	private JTextField jTextField10 = null;

	private JTextField jTextField12 = null;

	private JCalendarComboBox jCalendarComboBox1 = null;

	private JTextField jTextField16 = null;

	private JTextField jTextField17 = null;

	private JTextField jTextField18 = null;

	private JTextField jTextField19 = null;

	private JTextField jTextField21 = null;

	private JTextField jTextField22 = null;

	private DzscAction dzscAction = null;

	private JTableListModel tableModel = null; // 头

	private boolean isChange = false; // 判断是否变更状态

	// private boolean isEdit = true; //判断是否为编辑状态

	private JTextField jTextField24 = null;

	private int dataState = DataState.BROWSE;

	private JComboBox jComboBox = null;

	private JComboBox jComboBox1 = null;

	private JComboBox jComboBox2 = null;

	private DzscEmsPorHeadFas fas = null;

	private boolean isHistoryChange = false;

	public int getDataState() {
		return dataState;
	}

	public void setDataState(int dataState) {
		this.dataState = dataState;
	}

	/**
	 * This is the default constructor
	 */
	public DgDzscFascicule() {
		super();
		dzscAction = (DzscAction) CommonVars.getApplicationContext().getBean(
				"dzscAction");
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(634, 408);
		this.setTitle("电子分册备案维护");
		this.setContentPane(getJContentPane());
		this.addWindowListener(new java.awt.event.WindowAdapter() {

			public void windowOpened(java.awt.event.WindowEvent e) {
				initUI();
				fas = (DzscEmsPorHeadFas) tableModel.getCurrentRow();
				fillWindow();
				setState();
				setEmsTitle();
			}
		});
	}

	private void initUI() {
		// 分册类型
		this.jComboBox.removeAllItems();
		this.jComboBox.addItem(new ItemProperty("0", "异地报关分册"));
		this.jComboBox.addItem(new ItemProperty("1", "异地结转分册"));
		this.jComboBox.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance()
				.setComboBoxEditor(this.jComboBox);
		// 进出口岸
		List list = CustomBaseList.getInstance().getCustoms();
		this.jComboBox1.setModel(new DefaultComboBoxModel(list.toArray()));
		this.jComboBox1.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.jComboBox1, "code", "name");
		// 申报标志
		List list1 = CustomBaseList.getInstance().getDModes();
		this.jComboBox2.setModel(new DefaultComboBoxModel(list1.toArray()));
		this.jComboBox2.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.jComboBox2, "code", "name");
	}

	// private void initTableImg(final List list) {
	// tableModelImg = new JTableListModel(jTableImg, list,
	// new JTableListModelAdapter() {
	// public List InitColumns() {
	// List <JTableListColumn>list = new Vector<JTableListColumn>();
	// list.add(addColumn("修改标志","modifyMark",60));
	// list.add(addColumn("料件序号", "seqNum", 60, Integer.class));
	// list.add(addColumn("商品编码", "complex.code", 80));
	// list.add(addColumn("商品名称", "name", 100));
	// list.add(addColumn("型号规格", "spec", 100));
	// list.add(addColumn("计量单位", "unit.name", 80));
	// list.add(addColumn("币制", "curr.name", 60));
	// list.add(addColumn("允许数量", "allowAmount", 80));
	// list.add(addColumn("企业申报单价", "declarePrice", 80));
	// list.add(addColumn("备注", "note", 100));
	// return list;
	// }
	// });
	// // EmsEdiMergerClientLogic.transModifyMark(jTableImg);
	// }

	// private void initTableExg(final List list) {
	// tableModelExg = new JTableListModel(jTableExg, list,
	// new JTableListModelAdapter() {
	// public List InitColumns() {
	// List <JTableListColumn>list = new Vector<JTableListColumn>();
	// list.add(addColumn("修改标志","modifyMark",60));
	// list.add(addColumn("成品序号", "seqNum", 60, Integer.class));
	// list.add(addColumn("商品编码", "complex.code", 80));
	// list.add(addColumn("商品名称", "name", 100));
	// list.add(addColumn("型号规格", "spec", 100));
	// list.add(addColumn("计量单位", "unit.name", 80));
	// list.add(addColumn("币制", "curr.name", 60));
	// list.add(addColumn("允许数量", "allowAmount", 80));
	// list.add(addColumn("企业申报单价", "declarePrice", 80));
	// list.add(addColumn("备注", "note", 100));
	// return list;
	// }
	// });
	// // EmsEdiMergerClientLogic.transModifyMark(jTableExg);
	// }

	private void setEmsTitle() {
		if (DgDzscFascicule.this.isChange) { // 变更状态
			if (dataState == DataState.EDIT)
				DgDzscFascicule.this.setTitle("电子分册申请变更维护");
			else
				DgDzscFascicule.this.setTitle("电子分册申请变更浏览");
		} else if (DgDzscFascicule.this.isChange == false) { // 备案状态
			if (dataState == DataState.EDIT)
				DgDzscFascicule.this.setTitle("电子分册申请备案维护");
			else
				DgDzscFascicule.this.setTitle("电子分册申请备案浏览");
		}
	}

	private void fillWindow() {
		// 分册类型
		if (fas.getFasEmsType() != null) {
			jComboBox.setSelectedIndex(ItemProperty.getIndexByCode(String
					.valueOf(this.fas.getFasEmsType()), jComboBox));
		} else {
			jComboBox.setSelectedIndex(0);
		}
		// 进出口岸
		if (fas.getIePortFas() != null) {
			jComboBox1.setSelectedItem(fas.getIePortFas());
		} else {
			jComboBox1.setSelectedIndex(0);
		}
		// 申报标志
		if (fas.getDeclareMark() != null) {
			jComboBox2.setSelectedItem(fas.getDeclareMark());
		} else {
			jComboBox2.setSelectedIndex(0);
		}
		if ("1".equals(fas.getDeclareType()))
			jTextField2.setText("备案申请");
		else
			jTextField2.setText("备案变更");
		jTextField12.setText(fas.getFasEmsNo()); // 分册号
		jTextField6.setText(fas.getTradeCode());
		jTextField7.setText(fas.getTradeName());
		jTextField8.setText(fas.getMachCode());
		jTextField9.setText(fas.getMachName());
		jTextField4.setText(fas.getEmsNo());
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
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */

	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new java.awt.BorderLayout());
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJpHead(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * 
	 * This method initializes jToolBar
	 * 
	 * 
	 * 
	 * @return javax.swing.JToolBar
	 * 
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getJbSave());
			jToolBar.add(getJbExit());
		}
		return jToolBar;
	}

	/**
	 * 
	 * This method initializes jbSave
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJbSave() {
		if (jbSave == null) {
			jbSave = new JButton();
			jbSave.setText("保存");
			jbSave.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					// 保存
					fillEmsHeadH2kFas(fas);
					fas = dzscAction.saveDzscEmsPorHeadFas(new Request(
							CommonVars.getCurrUser()), fas);
					tableModel.updateRow(fas);
					dataState = DataState.BROWSE;
					setState();

				}
			});

		}
		return jbSave;
	}

	private void fillEmsHeadH2kFas(DzscEmsPorHeadFas fas) {
		// DzscEmsPorHeadFas emsHeadOld = (DzscEmsPorHeadFas) fas.clone();
		if (jComboBox.getSelectedItem() != null) {
			fas.setFasEmsType(((ItemProperty) this.jComboBox.getSelectedItem())
					.getCode());
		}
		fas.setLimitDate(jCalendarComboBox1.getDate());
		if (jComboBox1.getSelectedItem() != null) {
			fas.setIePortFas((Customs) jComboBox1.getSelectedItem());
		}
		if (jComboBox2.getSelectedItem() != null) {
			fas.setDeclareMark((ApplicationMode) jComboBox2.getSelectedItem());
		}
		fas.setNote(jTextField22.getText());
		if (fas.getDeclareState().equals(DzscState.CHANGE)) {
			fas.setModifyMark(ModifyMarkState.MODIFIED); // 打上已修改标志，
		}

		// }
		fas.setFasEmsNo(this.jTextField12.getText());

	}

	/**
	 * 
	 * This method initializes jbExit
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJbExit() {
		if (jbExit == null) {
			jbExit = new JButton();
			jbExit.setText("关闭");
			jbExit.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgDzscFascicule.this.dispose();

				}
			});

		}
		return jbExit;
	}

	/**
	 * 
	 * This method initializes jpHead
	 * 
	 * 
	 * 
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
			jLabel2.setBounds(420, 66, 62, 19);
			jLabel2.setText("申报类型");
			jLabel3.setBounds(35, 36, 76, 20);
			jLabel3.setText("企业内部编号");
			jLabel4.setBounds(233, 36, 64, 20);
			jLabel4.setText("帐册编号");
			jLabel6.setBounds(35, 90, 74, 20);
			jLabel6.setText("经营单位代码");
			jLabel7.setBounds(233, 91, 80, 21);
			jLabel7.setText("经营单位名称");
			jLabel8.setBounds(35, 118, 74, 21);
			jLabel8.setText("申报单位代码");
			jLabel9.setBounds(233, 119, 80, 20);
			jLabel9.setText("申报单位名称");
			jLabel10.setBounds(35, 148, 57, 21);
			jLabel10.setText("分册期限");
			jLabel11.setBounds(233, 149, 55, 19);
			jLabel11.setText("进出口岸");
			jLabel14.setBounds(420, 151, 59, 21);
			jLabel14.setText("申报标志");
			jLabel15.setBounds(35, 64, 75, 20);
			jLabel15.setText("分册类型");
			jLabel17.setBounds(233, 63, 85, 20);
			jLabel17.setText("分册号");
			jLabel19.setBounds(420, 36, 65, 19);
			jLabel19.setText("变更次数");
			jLabel21.setBounds(35, 180, 67, 19);
			jLabel21.setText("录入员代号");
			jLabel22.setBounds(233, 182, 51, 18);
			jLabel22.setText("录入日期");
			jLabel23.setBounds(35, 212, 74, 23);
			jLabel23.setText("备案批准日期");
			jLabel24.setBounds(420, 181, 53, 22);
			jLabel24.setText("申报日期");
			jLabel26.setBounds(233, 215, 82, 20);
			jLabel26.setText("变更批准日期");
			jLabel27.setBounds(35, 251, 49, 19);
			jLabel27.setText("备注");
			jLabel30.setBounds(420, 215, 51, 20);
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
	 * This method initializes jTextField2
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField2() {
		if (jTextField2 == null) {
			jTextField2 = new JTextField();
			jTextField2.setBounds(493, 65, 91, 22);
			jTextField2.setEditable(false);
		}
		return jTextField2;
	}

	/**
	 * 
	 * This method initializes jTextField3
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField3() {
		if (jTextField3 == null) {
			jTextField3 = new JTextField();
			jTextField3.setBounds(109, 36, 94, 22);
			jTextField3.setEditable(false);
		}
		return jTextField3;
	}

	/**
	 * 
	 * This method initializes jTextField4
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField4() {
		if (jTextField4 == null) {
			jTextField4 = new JTextField();
			jTextField4.setBounds(316, 36, 93, 22);
			jTextField4.setEditable(false);
		}
		return jTextField4;
	}

	/**
	 * 
	 * This method initializes jTextField6
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField6() {
		if (jTextField6 == null) {
			jTextField6 = new JTextField();
			jTextField6.setBounds(109, 90, 94, 22);
			jTextField6.setEditable(false);
		}
		return jTextField6;
	}

	/**
	 * 
	 * This method initializes jTextField7
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField7() {
		if (jTextField7 == null) {
			jTextField7 = new JTextField();
			jTextField7.setBounds(316, 91, 269, 22);
			jTextField7.setEditable(false);
		}
		return jTextField7;
	}

	/**
	 * 
	 * This method initializes jTextField8
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField8() {
		if (jTextField8 == null) {
			jTextField8 = new JTextField();
			jTextField8.setBounds(109, 118, 93, 22);
			jTextField8.setEditable(false);
		}
		return jTextField8;
	}

	/**
	 * 
	 * This method initializes jTextField9
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField9() {
		if (jTextField9 == null) {
			jTextField9 = new JTextField();
			jTextField9.setBounds(316, 119, 268, 22);
			jTextField9.setEditable(false);
		}
		return jTextField9;
	}

	/**
	 * 
	 * This method initializes jTextField10
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField10() {
		if (jTextField10 == null) {
			jTextField10 = new JTextField();
			jTextField10.setEditable(false);
			jTextField10.setBounds(493, 36, 92, 22);

		}
		return jTextField10;
	}

	/**
	 * 
	 * This method initializes jTextField12
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField12() {
		if (jTextField12 == null) {
			jTextField12 = new JTextField();
			jTextField12.setBounds(316, 63, 93, 22);
			// jTextField12.setEditable(false);
		}
		return jTextField12;
	}

	/**
	 * 
	 * This method initializes jCalendarComboBox1
	 * 
	 * 
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 * 
	 */
	private JCalendarComboBox getJCalendarComboBox1() {
		if (jCalendarComboBox1 == null) {
			jCalendarComboBox1 = new JCalendarComboBox();
			jCalendarComboBox1.setBounds(109, 149, 94, 22);
		}
		return jCalendarComboBox1;
	}

	/**
	 * 
	 * This method initializes jTextField16
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField16() {
		if (jTextField16 == null) {
			jTextField16 = new JTextField();
			jTextField16.setBounds(109, 179, 94, 22);
			jTextField16.setEditable(false);
		}
		return jTextField16;
	}

	/**
	 * 
	 * This method initializes jTextField17
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField17() {
		if (jTextField17 == null) {
			jTextField17 = new JTextField();
			jTextField17.setBounds(316, 182, 94, 22);
			jTextField17.setEditable(false);
		}
		return jTextField17;
	}

	/**
	 * 
	 * This method initializes jTextField18
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField18() {
		if (jTextField18 == null) {
			jTextField18 = new JTextField();
			jTextField18.setBounds(109, 213, 94, 22);
			jTextField18.setEditable(false);
		}
		return jTextField18;
	}

	/**
	 * 
	 * This method initializes jTextField19
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField19() {
		if (jTextField19 == null) {
			jTextField19 = new JTextField();
			jTextField19.setBounds(493, 181, 91, 22);
			jTextField19.setEditable(false);
		}
		return jTextField19;
	}

	/**
	 * 
	 * This method initializes jTextField21
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField21() {
		if (jTextField21 == null) {
			jTextField21 = new JTextField();
			jTextField21.setBounds(317, 214, 94, 22);
			jTextField21.setEditable(false);
		}
		return jTextField21;
	}

	/**
	 * 
	 * This method initializes jTextField22
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField22() {
		if (jTextField22 == null) {
			jTextField22 = new JTextField();
			jTextField22.setBounds(109, 251, 479, 22);
		}
		return jTextField22;
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
	 * @return Returns the isChange.
	 */
	public boolean isChange() {
		return isChange;
	}

	/**
	 * @param isChange
	 *            The isChange to set.
	 */
	public void setChange(boolean isChange) {
		this.isChange = isChange;
	}

	// /**
	// * @return Returns the isEdit.
	// */
	// public boolean isEdit() {
	// return isEdit;
	// }
	//
	// /**
	// * @param isEdit
	// * The isEdit to set.
	// */
	// public void setEdit(boolean isEdit) {
	// this.isEdit = isEdit;
	// }

	/**
	 * 
	 * This method initializes jTextField24
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField24() {
		if (jTextField24 == null) {
			jTextField24 = new JTextField();
			jTextField24.setBounds(493, 217, 93, 22);
			jTextField24.setEditable(false);
		}
		return jTextField24;
	}

	private void setState() {
		// jTabbedPane.setEnabled((fas.getDeclareState().equals(DeclareState.APPLY_POR)
		// && dataState == DataState.BROWSE)
		// || !fas.getDeclareState().equals(DeclareState.APPLY_POR));
		// jbAdd.setEnabled((dataState != DataState.READONLY)); //新增
		// jbEdit.setEnabled((dataState == DataState.BROWSE && ((dataState !=
		// DataState.READONLY))));//修改
		// jbDelete.setEnabled((dataState != DataState.READONLY)); //删除
		jbSave.setEnabled(dataState != DataState.BROWSE
				&& dataState != DataState.READONLY
				&& fas.getDeclareState().equals(DzscState.ORIGINAL)); // 保存
		jComboBox.setEnabled(dataState != DataState.READONLY
				&& dataState != DataState.BROWSE
				&& !DgDzscFascicule.this.isChange);
		jCalendarComboBox1.setEnabled(dataState != DataState.READONLY
				&& dataState != DataState.BROWSE);
		jComboBox1.setEnabled(dataState != DataState.READONLY
				&& dataState != DataState.BROWSE);
		jComboBox2.setEnabled(dataState != DataState.READONLY
				&& dataState != DataState.BROWSE);
		jTextField22.setEditable(dataState != DataState.READONLY
				&& dataState != DataState.BROWSE);
		jTextField12.setEditable(dataState != DataState.READONLY
				&& dataState != DataState.BROWSE);
	}

	// private boolean isImgExgPageAndExistData() {
	// if ((jTabbedPane.getSelectedIndex() == 1)
	// && (tableModelImg.getRowCount() > 0)) {
	// return true;
	// }
	// if ((jTabbedPane.getSelectedIndex() == 2)
	// && (tableModelExg.getRowCount() > 0)) {
	// return true;
	// }
	// return false;
	// }
	/**
	 * 
	 * This method initializes jComboBox
	 * 
	 * 
	 * 
	 * @return javax.swing.JComboBox
	 * 
	 */
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setBounds(108, 63, 95, 22);
		}
		return jComboBox;
	}

	/**
	 * 
	 * This method initializes jComboBox1
	 * 
	 * 
	 * 
	 * @return javax.swing.JComboBox
	 * 
	 */
	private JComboBox getJComboBox1() {
		if (jComboBox1 == null) {
			jComboBox1 = new JComboBox();
			jComboBox1.setBounds(316, 149, 94, 23);
		}
		return jComboBox1;
	}

	/**
	 * 
	 * This method initializes jComboBox2
	 * 
	 * 
	 * 
	 * @return javax.swing.JComboBox
	 * 
	 */
	private JComboBox getJComboBox2() {
		if (jComboBox2 == null) {
			jComboBox2 = new JComboBox();
			jComboBox2.setBounds(492, 149, 92, 23);
		}
		return jComboBox2;
	}

	/**
	 * @return Returns the isHistoryChange.
	 */
	public boolean isHistoryChange() {
		return isHistoryChange;
	}

	/**
	 * @param isHistoryChange
	 *            The isHistoryChange to set.
	 */
	public void setHistoryChange(boolean isHistoryChange) {
		this.isHistoryChange = isHistoryChange;
	}
} // @jve:decl-index=0:visual-constraint="10,5"
