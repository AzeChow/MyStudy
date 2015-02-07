/*
 * Created on 2005-5-23
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.fpt;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;

import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.FptInOutFlag;
import com.bestway.common.fpt.action.FptManageAction;
import com.bestway.common.fpt.entity.FptAppHead;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.customs.common.entity.BaseEmsHead;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public class DgFptToCustomsQueryCondition extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JComboBox cbbFptApp = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private FptManageAction fptManageAction = null;

	private List lsResult = null; // @jve:decl-index=0:

	private boolean isImport;

	private JTableListModel contractTableModel = null;

	private JPanel jPanel = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel3 = null;

	private JRadioButton cbbIn = null;

	private JRadioButton cbbOut = null;

	private JPanel jPanel1 = null;

	private JPanel jPanel2 = null;

	private JPanel jPanel21 = null;

	private JLabel jLabel = null;

	private JComboBox cbbEmsNo = null;

	private JCalendarComboBox cbbBeginDate = null;

	private JCalendarComboBox cbbEndDate = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel5 = null;

	private ButtonGroup bg = new ButtonGroup(); // @jve:decl-index=0:

	private JComboBox cbbScmCoc = null;

	/**
	 * @return Returns the isImport.
	 */
	public boolean isImport() {
		return isImport;
	}

	/**
	 * @param isImport
	 *            The isImport to set.
	 */
	public void setImport(boolean isImport) {
		this.isImport = isImport;
	}

	/**
	 * @return Returns the lsResult.
	 */
	public List getLsResult() {
		return lsResult;
	}

	/**
	 * This is the default constructor
	 */
	public DgFptToCustomsQueryCondition() {
		super();
		initialize();
		fptManageAction = (FptManageAction) CommonVars.getApplicationContext()
				.getBean("fptManageAction");
	}

	@Override
	public void setVisible(boolean b) {
		if (b) {
			initUIComponents();
			initCbbCondi(false);
		}
		super.setVisible(b);
	}

	/**
	 * 初始化窗口上 控件的值
	 * 
	 */
	private void initUIComponents() {
		bg.add(cbbIn);
		bg.add(cbbOut);
		this.cbbBeginDate.setDate(CommonVars.getBeginDate());
		this.cbbEndDate.setDate(getcbbEndDate(new Date()));

		if (this.cbbIn.isSelected()) {
			isImport = true;
			initFptApp(FptInOutFlag.IN, null);
			initCbbScmCoc(FptInOutFlag.OUT, null);
		}
	}
	//-------向后多大一天.因为有些表中存放的日期包括了分秒
	private Date getcbbEndDate(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.add(5, 1);
		gc.set(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), gc.get(Calendar.DATE));
		return java.sql.Date.valueOf(df.format(gc.getTime()));

	}

	private void initCbbScmCoc(String impExpFlagCode, String billTypeCode) {
		cbbScmCoc.removeAllItems();
		List list = fptManageAction.findScmCocsByPara(new Request(CommonVars
				.getCurrUser(), true), impExpFlagCode, billTypeCode);
		this.cbbScmCoc.setModel(new DefaultComboBoxModel(list.toArray()));
		this.cbbScmCoc.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 150, 170));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbScmCoc, "code", "name", 320);
		cbbScmCoc.setSelectedIndex(-1);
	}

	private void initFptApp(String impExpFlagCode, String billTypeCode) {
		List list = fptManageAction.findFptAppHeadByScmCoc(new Request(
				CommonVars.getCurrUser(), true), impExpFlagCode,
				DeclareState.PROCESS_EXE, null,null);
		cbbFptApp.removeAllItems();
		this.cbbFptApp.setModel(new DefaultComboBoxModel(list.toArray()));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbFptApp,
				"appNo", "appNo");
		cbbFptApp.setRenderer(CustomBaseRender.getInstance().getRender("appNo",
				"appNo", 200, 0));
		cbbFptApp.setSelectedIndex(-1);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("请输入检索条件");
		this.setSize(356, 323);
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJPanel(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbFptApp() {
		if (cbbFptApp == null) {
			cbbFptApp = new JComboBox();
			cbbFptApp.setBounds(new Rectangle(121, 16, 175, 23));
			cbbFptApp.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
						initCbbCondi(true);
						initCbbEmsNo();
					}
				}

			});
		}
		return cbbFptApp;
	}

	// 初始化手册号
	private void initCbbEmsNo() {
		List list = new ArrayList();
		Integer projectType = null;
		if (this.cbbFptApp.getSelectedItem() != null) {
			projectType = ((FptAppHead) this.cbbFptApp.getSelectedItem())
					.getProjectType();
		}
		if (projectType != null && !"".equals(projectType)) {
			list = fptManageAction.findAllEmsExe(new Request(CommonVars
					.getCurrUser()), projectType);
		}
		cbbEmsNo.removeAllItems();
		this.cbbEmsNo.setModel(new DefaultComboBoxModel(list.toArray()));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(this.cbbEmsNo,
				"emsNo", "emsNo");
		this.cbbEmsNo.setRenderer(CustomBaseRender.getInstance().getRender(
				"emsNo", "emsNo", 200, 0));
		cbbEmsNo.setSelectedIndex(-1);
	}

	private void initCbbCondi(boolean isNew) {
		Component[] comps = jPanel21.getComponents();
		for (int i = 0; i < comps.length; i++) {
			comps[i].setEnabled(isNew);
		}
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("确定");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new MyFindThread().start();
				}
			});
		}
		return jButton;
	}

	/**
	 * 开始查询
	 */
	private void find() {
		String emsNo = null;
		String appNo = null;
		if (this.cbbFptApp.getSelectedItem() != null) {
			appNo = ((FptAppHead) this.cbbFptApp.getSelectedItem()).getAppNo();
		}

		ScmCoc scmCoc = (ScmCoc) cbbScmCoc.getSelectedItem();// 客户供应商

		if (this.cbbEmsNo.getSelectedItem() != null) {
			emsNo = ((BaseEmsHead) this.cbbEmsNo.getSelectedItem()).getEmsNo();
		}
		Date beginDate = cbbBeginDate.getDate();
		//-------向后多大一天.因为有些表中存放的日期包括了分秒
		Date endDate = getcbbEndDate(cbbEndDate.getDate());
		lsResult = fptManageAction.getMakeFptBillCustomsDeclaration(
				new Request(CommonVars.getCurrUser()), isImport, appNo,
				beginDate, endDate, scmCoc, emsNo);
		dispose();
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("取消");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton1;
	}

	private void selectAll(boolean isSelected) {
		if (contractTableModel == null) {
			return;
		}
		List list = contractTableModel.getList();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) instanceof Contract) {
				Contract temp = (Contract) list.get(i);
				temp.setIsSelected(new Boolean(isSelected));
			}
		}
		contractTableModel.updateRows(list);
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.insets = new Insets(1, 2, 7, 3);
			gridBagConstraints3.gridx = 0;
			gridBagConstraints3.gridy = 1;
			gridBagConstraints3.ipadx = 342;
			gridBagConstraints3.ipady = 195;
			gridBagConstraints3.gridwidth = 2;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.insets = new Insets(0, 1, 1, 1);
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.gridy = 0;
			gridBagConstraints2.ipadx = 345;
			gridBagConstraints2.ipady = 33;
			gridBagConstraints2.gridwidth = 2;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.insets = new Insets(10, 93, 16, 20);
			gridBagConstraints1.gridy = 2;
			gridBagConstraints1.ipadx = 9;
			gridBagConstraints1.ipady = -4;
			gridBagConstraints1.gridx = 0;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.insets = new Insets(8, 21, 18, 80);
			gridBagConstraints.gridy = 2;
			gridBagConstraints.ipadx = 9;
			gridBagConstraints.ipady = -4;
			gridBagConstraints.gridx = 1;
			jLabel3 = new JLabel();
			jLabel3.setText("申请表编号");
			jLabel3.setBounds(new Rectangle(51, 17, 62, 18));
			jLabel2 = new JLabel();
			jLabel2.setText("客户/供应商");
			jLabel2.setBounds(new Rectangle(45, 42, 68, 22));
			jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());
			jPanel.add(getJButton1(), gridBagConstraints);
			jPanel.add(getJButton(), gridBagConstraints1);
			jPanel.add(getJPanel1(), gridBagConstraints2);
			jPanel.add(getJPanel2(), gridBagConstraints3);
		}
		return jPanel;
	}

	class MyFindThread extends Thread {
		@Override
		public void run() {
			CommonProgress.showProgressDialog();
			CommonProgress.setMessage("系统正获取数据，请稍后...");
			find();
			CommonProgress.closeProgressDialog();
		}
	}

	/**
	 * This method initializes cbbIn
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getCbbIn() {
		if (cbbIn == null) {
			cbbIn = new JRadioButton();
			cbbIn.setText("转入");
			cbbIn.setBounds(new Rectangle(72, 5, 49, 26));
			cbbIn.setSelected(true);
			cbbIn.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					isImport = true;
					initCbbCondi(false);
					initFptApp(FptInOutFlag.IN, null);
					// 初始客户供应商品
					initCbbScmCoc(FptInOutFlag.OUT, null);
				}
			});
		}
		return cbbIn;
	}

	/**
	 * This method initializes cbbOut
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getCbbOut() {
		if (cbbOut == null) {
			cbbOut = new JRadioButton();
			cbbOut.setText("转出");
			cbbOut.setBounds(new Rectangle(201, 6, 49, 26));
			cbbOut.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					isImport = false;
					initCbbCondi(false);
					initFptApp(FptInOutFlag.OUT, null);
					// 初始客户供应商品
					initCbbScmCoc(FptInOutFlag.IN, null);
				}
			});
		}
		return cbbOut;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.add(getCbbIn(), null);
			jPanel1.add(getCbbOut(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.setBorder(BorderFactory.createTitledBorder(null,
					"结转与报关查询条件", TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), Color.blue));
			jPanel2.add(jLabel3, null);
			jPanel2.add(getCbbFptApp(), null);
			jPanel2.add(jLabel2, null);
			jPanel2.add(getJPanel21(), null);
			jPanel2.add(jLabel5, null);
			jPanel2.add(getCbbEndDate(), null);
			jPanel2.add(jLabel1, null);
			jPanel2.add(getCbbBeginDate(), null);
			jPanel2.add(getCbbScmCoc(), null);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jPanel21
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel21() {
		if (jPanel21 == null) {
			jLabel5 = new JLabel();
			jLabel5.setText("\u81f3");
			jLabel5.setBounds(new Rectangle(96, 107, 12, 18));
			jLabel1 = new JLabel();
			jLabel1.setText("录入日期从");
			jLabel1.setBounds(new Rectangle(50, 77, 60, 18));
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(24, 18, 78, 18));
			jLabel.setText("手册号/账册号");
			jPanel21 = new JPanel();
			jPanel21.setLayout(null);
			jPanel21.setBounds(new Rectangle(3, 135, 340, 54));
			jPanel21.add(jLabel, null);
			jPanel21.add(getCbbEmsNo(), null);
		}
		return jPanel21;
	}

	/**
	 * This method initializes cbbEmsNo
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbEmsNo() {
		if (cbbEmsNo == null) {
			cbbEmsNo = new JComboBox();
			cbbEmsNo.setBounds(new Rectangle(117, 15, 175, 23));
			cbbEmsNo.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
						initCbbSeqNo();
					}
				}
			});
		}
		return cbbEmsNo;
	}

	private void initCbbSeqNo() {
		//

	}

	/**
	 * This method initializes cbbBeginDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(new Rectangle(121, 77, 175, 23));
		}
		return cbbBeginDate;
	}

	/**
	 * This method initializes cbbEndDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbEndDate() {
		if (cbbEndDate == null) {
			cbbEndDate = new JCalendarComboBox();
			cbbEndDate.setBounds(new Rectangle(121, 107, 175, 23));
		}
		return cbbEndDate;
	}

	/**
	 * This method initializes cbbScmCoc
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbScmCoc() {
		if (cbbScmCoc == null) {
			cbbScmCoc = new JComboBox();
			cbbScmCoc.setBounds(new Rectangle(121, 46, 175, 23));
		}
		return cbbScmCoc;
	}
} // @jve:decl-index=0:visual-constraint="227,15"
