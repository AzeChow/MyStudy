package com.bestway.client.fixtureonorder;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.common.Request;
import com.bestway.common.constant.CustomsDeclarationState;
import com.bestway.fixtureonorder.action.FixtureContractAction;
import com.bestway.fixtureonorder.entity.FixtureContract;
import com.bestway.fixtureonorder.entity.FixtureLocation;
import com.bestway.fixtureonorder.entity.TempFixtureCustomsDeclarCommInfo;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author fhz
 * 
 */
public class DgFixtureChangeLocationSub extends JDialogBase {
	private javax.swing.JPanel jContentPane = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel3 = null;

	private JComboBox cbbFixtureContract = null;

	private JComboBox cbbFixtureLocation = null;

	private JCalendarComboBox cbbBeginDate = null;

	private JCalendarComboBox cbbEndDate = null;

	private JLabel jLabel4 = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private FixtureContractAction fixtureContractAction = null;

	private List lsResult = null; // @jve:decl-index=0:

	private int state = CustomsDeclarationState.EFFECTIVED;

	private boolean isImport;

	private JLabel jLabel5 = null;

	private JLabel jLabel6 = null;

	private JLabel jLabel7 = null;

	private JComboBox cbbName = null;

	private JComboBox cbbCode = null;

	private JComboBox cbbSeqNo = null;

	private JLabel jLabel61 = null;

	private JComboBox cbbSpec = null;

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

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
	public DgFixtureChangeLocationSub() {
		super();
		fixtureContractAction = (FixtureContractAction) CommonVars
				.getApplicationContext().getBean("fixtureContractAction");
		initialize();

	}

	public void setVisible(boolean b) {
		if (b) {
			initUIComponents();
		}
		super.setVisible(b);
	}

	/**
	 * 初始化窗口上 控件的值
	 * 
	 */
	private void initUIComponents() {
		// 设备
		List contracts = fixtureContractAction
				.findContractByProcessExe(new Request(CommonVars.getCurrUser()));
		List arryList = new ArrayList();
		for (int i = 0; i < contracts.size(); i++) {
			FixtureContract fixtureContract = (FixtureContract) contracts
					.get(i);
			List dlist = fixtureContractAction.findCustomsDeclarationCommInfo(
					new Request(CommonVars.getCurrUser()), fixtureContract,
					-100);
			arryList.addAll(dlist);
		}

		Map mapName = new HashMap();
		Map mapCode = new HashMap();
		Map mapSeqNo = new HashMap();
		Map mapSpec = new HashMap();

		for (int i = 0; i < arryList.size(); i++) {
				TempFixtureCustomsDeclarCommInfo data = (TempFixtureCustomsDeclarCommInfo) arryList
						.get(i);
				if (mapName.get(data.getName()) == null) {
					mapName.put(data.getName(), data);
				}
				if (mapCode.get(data.getCode()) == null) {
					mapCode.put(data.getCode(), data);
				}
				if (mapSeqNo.get(data.getSeqNum()) == null) {
					mapSeqNo.put(data.getSeqNum(), data);
				}
				if (mapSpec.get(data.getSpec()) == null) {
					mapSpec.put(data.getSpec(), data);
				}
		}
		List listName = new ArrayList(mapName.values());
		List listCode = new ArrayList(mapCode.values());
		List listSeqNo = new ArrayList(mapSeqNo.values());
		List listSpec = new ArrayList(mapSpec.values());
		// ***********************************************
		cbbName.setModel(new DefaultComboBoxModel(listName.toArray()));
		cbbName.setRenderer(CustomBaseRender.getInstance().getRender("name",
				"name", 0, 160));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(this.cbbName,
				"name", "name");
		cbbName.setSelectedIndex(-1);
		// ***********************************************
		this.cbbSpec.setModel(new DefaultComboBoxModel(listSpec.toArray()));
		cbbSpec.setRenderer(CustomBaseRender.getInstance().getRender("spec",
				"spec", 0, 160));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(this.cbbSpec,
				"spec", "spec");
		cbbSpec.setSelectedIndex(-1);
		// ***********************************************
		cbbCode.setModel(new DefaultComboBoxModel(listCode.toArray()));
		cbbCode.setRenderer(CustomBaseRender.getInstance().getRender("code",
				"code", 100, 0));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(this.cbbCode,
				"code", "code");
		cbbCode.setSelectedIndex(-1);
		// ***********************************************
		Collections.sort(listSeqNo);
		cbbSeqNo.setModel(new DefaultComboBoxModel(listSeqNo.toArray()));
		cbbSeqNo.setRenderer(CustomBaseRender.getInstance().getRender("seqNum",
				"seqNum", 100, 0));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(this.cbbSeqNo,
				"seqNum", "seqNum");
		cbbSeqNo.setSelectedIndex(-1);
		// 设备***********************************************
		this.cbbFixtureContract.setModel(new DefaultComboBoxModel(contracts
				.toArray()));
		this.cbbFixtureContract.setRenderer(CustomBaseRender.getInstance()
				.getRender("emsNo", "agreementNo", 100, 100));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbFixtureContract, "emsNo", "agreementNo");
		this.cbbFixtureContract.setSelectedIndex(-1);
		// 初始位置***********************************************
		List list = this.fixtureContractAction.findFixtureLocation(new Request(
				CommonVars.getCurrUser()));
		this.cbbFixtureLocation.setModel(new DefaultComboBoxModel(list
				.toArray()));
		this.cbbFixtureLocation.setRenderer(CustomBaseRender.getInstance()
				.getRender("code", "name", 50, 100));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbFixtureLocation, "code", "name");
		this.cbbFixtureLocation.setSelectedIndex(-1);

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
		this.setSize(398, 315);
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel61 = new JLabel();
			jLabel61.setBounds(new Rectangle(44, 46, 89, 23));
			jLabel61.setText("商品规格");
			jLabel7 = new JLabel();
			jLabel7.setBounds(new Rectangle(44, 101, 89, 22));
			jLabel7.setText("商品序号");
			jLabel6 = new JLabel();
			jLabel6.setBounds(new Rectangle(44, 73, 89, 22));
			jLabel6.setText("商品编码");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(44, 19, 89, 22));
			jLabel5.setText("商品名称");
			jLabel4 = new JLabel();
			jLabel3 = new JLabel();
			jLabel2 = new JLabel();
			jLabel1 = new JLabel();
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jLabel1.setBounds(44, 131, 89, 22);
			jLabel1.setText("协议手册号");
			jLabel2.setBounds(44, 160, 89, 22);
			jLabel2.setText("设备存放位置");
			jLabel3.setBounds(44, 189, 89, 22);
			jLabel3.setText("报关日期范围");
			jLabel4.setBounds(232, 191, 13, 18);
			jLabel4.setText("至");
			jContentPane.add(jLabel1, null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(getCbbFixtureContract(), null);
			jContentPane.add(getCbbFixtureLocation(), null);
			jContentPane.add(getCbbBeginDate(), null);
			jContentPane.add(getCbbEndDate(), null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton1(), null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(jLabel5, null);
			jContentPane.add(jLabel6, null);
			jContentPane.add(jLabel7, null);
			jContentPane.add(getCbbName(), null);
			jContentPane.add(getCbbCode(), null);
			jContentPane.add(getCbbSeqNo(), null);
			jContentPane.add(jLabel61, null);
			jContentPane.add(getCbbSpec(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbFixtureContract() {
		if (cbbFixtureContract == null) {
			cbbFixtureContract = new JComboBox();
			cbbFixtureContract.setBounds(140, 131, 197, 22);
		}
		return cbbFixtureContract;
	}

	/**
	 * This method initializes jComboBox2
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbFixtureLocation() {
		if (cbbFixtureLocation == null) {
			cbbFixtureLocation = new JComboBox();
			cbbFixtureLocation.setBounds(140, 160, 197, 22);
		}
		return cbbFixtureLocation;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(140, 190, 89, 20);
			cbbBeginDate.setDate(null);
		}
		return cbbBeginDate;
	}

	/**
	 * This method initializes jCalendarComboBox1
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbEndDate() {
		if (cbbEndDate == null) {
			cbbEndDate = new JCalendarComboBox();
			cbbEndDate.setBounds(246, 190, 90, 19);
		}
		return cbbEndDate;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(75, 227, 90, 21);
			jButton.setText("确定");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new MyFindThread().start();
				}
			});
		}
		return jButton;
	}

	private void queryData() {
		String commCode = "";
		FixtureContract fixtureContract = null;
		FixtureLocation fixtureLocation = null;
		Integer seqNum = null;
		String complexCode = "";
		String name = "";
		String spec = "";
		if (cbbName.getSelectedItem() != null) {
			name = ((TempFixtureCustomsDeclarCommInfo) cbbName
					.getSelectedItem()).getName();
		}
		if (this.cbbSpec.getSelectedItem() != null) {
			spec = ((TempFixtureCustomsDeclarCommInfo) cbbSpec
					.getSelectedItem()).getSpec();
		}
		if (cbbCode.getSelectedItem() != null) {
			commCode = ((TempFixtureCustomsDeclarCommInfo) cbbCode
					.getSelectedItem()).getCode();
		}
		if (cbbSeqNo.getSelectedItem() != null) {
			seqNum = ((TempFixtureCustomsDeclarCommInfo) cbbSeqNo
					.getSelectedItem()).getSeqNum();
		}
		if (cbbFixtureContract.getSelectedItem() != null) {
			fixtureContract = (FixtureContract) cbbFixtureContract
					.getSelectedItem();
		}
		if (cbbFixtureLocation.getSelectedItem() != null) {
			fixtureLocation = (FixtureLocation) cbbFixtureLocation
					.getSelectedItem();
		}
		Date beginDate = cbbBeginDate.getDate();
		Date endDate = cbbEndDate.getDate();

		lsResult = this.fixtureContractAction.findFixtureLocationResultInfo(
				new Request(CommonVars.getCurrUser()), seqNum, commCode, name,
				fixtureContract, fixtureLocation, beginDate, endDate, spec);

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
			jButton1.setBounds(211, 227, 90, 21);
			jButton1.setText("取消");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes cbbName
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbName() {
		if (cbbName == null) {
			cbbName = new JComboBox();
			cbbName.setBounds(new Rectangle(140, 19, 197, 22));

		}
		return cbbName;
	}

	/**
	 * This method initializes cbbCode
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCode() {
		if (cbbCode == null) {
			cbbCode = new JComboBox();
			cbbCode.setBounds(new Rectangle(140, 74, 197, 22));
		}
		return cbbCode;
	}

	/**
	 * This method initializes cbbSeqNo
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbSeqNo() {
		if (cbbSeqNo == null) {
			cbbSeqNo = new JComboBox();
			cbbSeqNo.setBounds(new Rectangle(140, 102, 197, 22));
		}
		return cbbSeqNo;
	}

	class MyFindThread extends Thread {
		public void run() {
			CommonProgress.showProgressDialog();
			CommonProgress.setMessage("系统正获取数据，请稍后...");
			queryData();
			CommonProgress.closeProgressDialog();
		}
	}

	/**
	 * This method initializes cbbCode1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbSpec() {
		if (cbbSpec == null) {
			cbbSpec = new JComboBox();
			cbbSpec.setBounds(new Rectangle(141, 46, 195, 23));
		}
		return cbbSpec;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
