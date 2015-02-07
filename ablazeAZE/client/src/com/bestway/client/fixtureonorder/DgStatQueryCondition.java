/*
 * Created on 2005-5-23
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
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
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.common.Request;
import com.bestway.common.constant.CustomsDeclarationState;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.fixtureonorder.action.FixtureContractAction;
import com.bestway.fixtureonorder.entity.FixtureContract;
import com.bestway.fixtureonorder.entity.TempFixtureCustomsDeclarCommInfo;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author fhz
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgStatQueryCondition extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel3 = null;

	private JComboBox cbbCustomer = null;

	private JComboBox cbbImpExpType = null;

	private JCalendarComboBox cbbBeginDate = null;

	private JCalendarComboBox cbbEndDate = null;

	private JLabel jLabel4 = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private FixtureContract fixtureContract = null; // @jve:decl-index=0:

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
	public DgStatQueryCondition() {
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
		// 商品
		List<TempFixtureCustomsDeclarCommInfo> dlist = fixtureContractAction
				.findCustomsDeclarationCommInfo(new Request(CommonVars
						.getCurrUser()), fixtureContract, state);

		Map mapName = new HashMap();
		Map mapCode = new HashMap();
		Map mapSeqNo = new HashMap();

		for (TempFixtureCustomsDeclarCommInfo data : dlist) {
			if (mapName.get(data.getName()) == null) {
				mapName.put(data.getName(), data);
			}
			if (mapCode.get(data.getCode()) == null) {
				mapCode.put(data.getCode(), data);
			}
			if (mapSeqNo.get(data.getSeqNum()) == null) {
				mapSeqNo.put(data.getSeqNum(), data);
			}
		}
		List listName = new ArrayList(mapName.values());
		List listCode = new ArrayList(mapCode.values());
		List listSeqNo = new ArrayList(mapSeqNo.values());
		// ***********************************************
		cbbName.setModel(new DefaultComboBoxModel(listName.toArray()));
		cbbName.setRenderer(CustomBaseRender.getInstance().getRender("name",
				"name", 0, 160));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(this.cbbName,
				"name", "name");
		cbbName.setSelectedIndex(-1);
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
		// 客户
		this.cbbCustomer.setModel(new DefaultComboBoxModel(
				fixtureContractAction.findCustomsDeclarationCustomer(
						new Request(CommonVars.getCurrUser()), fixtureContract,
						state).toArray()));
		this.cbbCustomer.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 100, 100));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbCustomer, "code", "name");
		this.cbbCustomer.setSelectedIndex(-1);
		// 初始化进出口类型
		this.cbbImpExpType.removeAllItems();
		this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
				ImpExpType.EQUIPMENT_IMPORT).toString(), "设备进口"));
		this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
				ImpExpType.BACK_PORT_REPAIR).toString(), "退港维修"));
		this.cbbImpExpType.addItem(new ItemProperty(String
				.valueOf(ImpExpType.EQUIPMENT_BACK_PORT), "设备退港"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbImpExpType);
		this.cbbImpExpType.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		this.cbbImpExpType.setSelectedIndex(-1);

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
		this.setSize(398, 279);
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel7 = new JLabel();
			jLabel7.setBounds(new Rectangle(44, 75, 89, 22));
			jLabel7.setText("商品序号");
			jLabel6 = new JLabel();
			jLabel6.setBounds(new Rectangle(44, 47, 89, 22));
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
			jLabel1.setBounds(44, 105, 89, 22);
			jLabel1.setText("检索客户");
			jLabel2.setBounds(44, 134, 89, 22);
			jLabel2.setText("进出口类型");
			jLabel3.setBounds(44, 163, 89, 22);
			jLabel3.setText("报关单日期范围");
			jLabel4.setBounds(232, 165, 13, 18);
			jLabel4.setText("至");
			jContentPane.add(jLabel1, null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(getCbbCustomer(), null);
			jContentPane.add(getCbbImpExpType(), null);
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
		}
		return jContentPane;
	}

	/**
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCustomer() {
		if (cbbCustomer == null) {
			cbbCustomer = new JComboBox();
			cbbCustomer.setBounds(140, 105, 197, 22);
		}
		return cbbCustomer;
	}

	/**
	 * This method initializes jComboBox2
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbImpExpType() {
		if (cbbImpExpType == null) {
			cbbImpExpType = new JComboBox();
			cbbImpExpType.setBounds(140, 134, 197, 22);
		}
		return cbbImpExpType;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(140, 164, 89, 20);
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
			cbbEndDate.setBounds(246, 164, 90, 19);
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
			jButton.setBounds(75, 201, 90, 21);
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
		String customer = "";
		String impExpType = "";
		Integer seqNum = null;
		String complexCode = "";
		String name = "";
		if (cbbSeqNo.getSelectedItem() != null) {
			seqNum = ((TempFixtureCustomsDeclarCommInfo) cbbSeqNo
					.getSelectedItem()).getSeqNum();
		}
		if (cbbName.getSelectedItem() != null) {
			name = ((TempFixtureCustomsDeclarCommInfo) cbbName
					.getSelectedItem()).getName();
		}
		if (cbbCode.getSelectedItem() != null) {
			commCode = ((TempFixtureCustomsDeclarCommInfo) cbbCode
					.getSelectedItem()).getCode();
		}

		if (cbbCustomer.getSelectedItem() != null) {
			customer = ((ScmCoc) cbbCustomer.getSelectedItem()).getId();
		}
		if (cbbImpExpType.getSelectedItem() != null) {
			impExpType = ((ItemProperty) cbbImpExpType.getSelectedItem())
					.getCode();
		}
		Date beginDate = cbbBeginDate.getDate();
		Date endDate = cbbEndDate.getDate();
		lsResult = this.fixtureContractAction.findImpExpCommInfoList(
				new Request(CommonVars.getCurrUser()), seqNum, commCode, name,
				customer, impExpType, beginDate, endDate, fixtureContract,
				state);
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
			jButton1.setBounds(211, 201, 90, 21);
			jButton1.setText("取消");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton1;
	}

	public FixtureContract getFixtureContract() {
		return fixtureContract;
	}

	public void setFixtureContract(FixtureContract fixtureContract) {
		this.fixtureContract = fixtureContract;
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
			cbbCode.setBounds(new Rectangle(140, 48, 197, 22));
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
			cbbSeqNo.setBounds(new Rectangle(140, 76, 197, 22));
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
} // @jve:decl-index=0:visual-constraint="10,10"
