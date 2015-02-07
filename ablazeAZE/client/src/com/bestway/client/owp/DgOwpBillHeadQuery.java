package com.bestway.client.owp;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.owp.DgOwpAppHeadQuery.MyFindThread;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Condition;
import com.bestway.common.Request;
import com.bestway.owp.action.OwpBillAction;
import com.bestway.owp.entity.OwpAppHead;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JTextField;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JButton;

/**
 * 外发加工登记表查询框
 * @author sxk
 *
 */
public class DgOwpBillHeadQuery extends JDialogBase {

	private JPanel pnMain = null;
	private JTextField tfAppNo = null;
	private JLabel lbAppNo = null;
	private JLabel lbInTradeCode = null;
	private JLabel lbInTradeName = null;
	private JTextField tfInTradeName = null;
	private JTextField tfInTradeCode = null;
	private JCalendarComboBox cbbBeginDate = null;
	private JCalendarComboBox cbbEndDate = null;
	private JLabel lbCollectDate = null;
	private JLabel lbzhi = null;
	private JButton btnQuery = null;
	private JButton btnCancel = null;
	
	//进出口标识: true:出口 、false:进口
	private boolean isOutFlag = false;
	
	/**
	 * 外发加工登记表接口
	 */
	private OwpBillAction owpBillAction = null;
	/**
	 * 外发加工登记表tableModel
	 * （从主窗口传至过来）
	 */
	private JTableListModel tableModelHead = null;
	/**
	 * 窗口构造函数
	 * 
	 */
	public DgOwpBillHeadQuery() {
		super();
		//外发加工登记表接口
		owpBillAction = (OwpBillAction) CommonVars
		.getApplicationContext().getBean("owpBillAction");
		initialize();
	}

	/**
	 * 窗口初始化类
	 * 
	 */
	private void initialize() {
        this.setSize(new Dimension(384, 268));
        this.setTitle("外发加工登记表查询");
        this.setContentPane(getPnMain());
			
	}

	/**
	 * 主容器设置类
	 */
	private JPanel getPnMain() {
		if (pnMain == null) {
			lbzhi = new JLabel();
			lbzhi.setBounds(new Rectangle(242, 152, 15, 18));
			lbzhi.setText("至");
			lbCollectDate = new JLabel();
			lbCollectDate.setBounds(new Rectangle(40, 152, 86, 18));
			lbCollectDate.setText("收发货日期");
			lbInTradeName = new JLabel();
			lbInTradeName.setBounds(new Rectangle(38, 108, 93, 18));
			lbInTradeName.setText("承揽方企业名称");
			lbInTradeCode = new JLabel();
			lbInTradeCode.setBounds(new Rectangle(38, 68, 91, 20));
			lbInTradeCode.setText("承揽方企业编码");
			lbAppNo = new JLabel();
			lbAppNo.setBounds(new Rectangle(38, 29, 79, 21));
			lbAppNo.setText("申请表编号");
			pnMain = new JPanel();
			pnMain.setLayout(null);
			pnMain.add(getTfAppNo(), null);
			pnMain.add(lbAppNo, null);
			pnMain.add(lbInTradeCode, null);
			pnMain.add(lbInTradeName, null);
			pnMain.add(getTfInTradeName(), null);
			pnMain.add(getTfInTradeCode(), null);
			pnMain.add(getCbbEndDate(), null);
			pnMain.add(getCbbBeginDate(), null);
			pnMain.add(lbCollectDate, null);
			pnMain.add(lbzhi, null);
			pnMain.add(getBtnQuery(), null);
			pnMain.add(getBtnCancel(), null);
		}
		return pnMain;
	}
	/**
	 * 结束日期选择框
	 * @return
	 */
	private JCalendarComboBox getCbbEndDate() {
		if (cbbEndDate == null) {
			cbbEndDate = new JCalendarComboBox();
			cbbEndDate.setPreferredSize(new java.awt.Dimension(95, 25));
			cbbEndDate.setBounds(new Rectangle(265, 147, 85, 25));
		}
		return cbbEndDate;
	}
	/**
	 * 开始日期选择框
	 * @return
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setPreferredSize(new java.awt.Dimension(95, 25));
			cbbBeginDate.setBounds(new Rectangle(150, 147, 83, 25));
		}
		return cbbBeginDate;
	}
	/**
	 * 申请表编号	
	 */
	private JTextField getTfAppNo() {
		if (tfAppNo == null) {
			tfAppNo = new JTextField();
			tfAppNo.setBounds(new Rectangle(150, 30, 147, 22));
		}
		return tfAppNo;
	}

	/**
	 * 承揽方企业名称
	 */
	private JTextField getTfInTradeName() {
		if (tfInTradeName == null) {
			tfInTradeName = new JTextField();
			tfInTradeName.setBounds(new Rectangle(149, 106, 145, 22));
		}
		return tfInTradeName;
	}

	/**
	 * 承揽方企业编码	
	 */
	private JTextField getTfInTradeCode() {
		if (tfInTradeCode == null) {
			tfInTradeCode = new JTextField();
			tfInTradeCode.setBounds(new Rectangle(150, 68, 146, 22));
		}
		return tfInTradeCode;
	}

	/**
	 * 查询按钮
	 */
	private JButton getBtnQuery() {
		if (btnQuery == null) {
			btnQuery = new JButton();
			btnQuery.setBounds(new Rectangle(61, 190, 102, 30));
			btnQuery.setText("查询");
			btnQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new MyFindThread().start();
				}
			});
		}
		return btnQuery;
	}
	/**
	 * 查询线程
	 * @author sxk
	 */
	class MyFindThread extends Thread {
		public void run() {
			CommonProgress.showProgressDialog();
			CommonProgress.setMessage("系统正获取数据，请稍后...");
			queryData();
			CommonProgress.closeProgressDialog();
		}
	}
	/**
	 * 查询
	 */
	public void queryData(){
		//查询条件
		List<Condition> conditions = new ArrayList<Condition>();
		
		//申请表编号
		if( tfAppNo.getText()!= null && !"".equals(tfAppNo.getText().trim())){
			conditions.add(new Condition("and", null,"appNo", "=",tfAppNo.getText().trim(), null));
		}
		
		//承揽方企业代码
		if( tfInTradeCode.getText()!= null && !"".equals(tfInTradeCode.getText().trim())){
			conditions.add(new Condition("and", null,"inTradeCode", "=",tfInTradeCode.getText().trim(), null));
		}
		
		//承揽方企业名称
		if( tfInTradeName.getText()!= null && !"".equals(tfInTradeName.getText().trim())){
			conditions.add(new Condition("and", null,"inTradeName", "=",tfInTradeName.getText().trim(), null));
		}
		
		//收发货日期
		if(cbbBeginDate.getDate() != null){
			conditions.add(new Condition("and", null,"collectDate", ">=",cbbBeginDate.getDate(), null));
		}
		if(cbbEndDate.getDate() != null){
			conditions.add(new Condition("and", null,"collectDate", "<=",cbbEndDate.getDate(), null));
		}
		
		/**
		 * 组合条件查询外发加工登记表表头
		 */	
		List list = new ArrayList();
		if(isOutFlag){
			list = owpBillAction.findOwpBillSendHeadByConditions(new Request(CommonVars
				.getCurrUser()),conditions);
		}else{
			list = owpBillAction.findOwpBillRecvHeadByConditions(new Request(CommonVars
					.getCurrUser()),conditions);
		}
		tableModelHead.setList(list == null ? (new ArrayList()) : list);
		
		//自动消失
		dispose();
	}
	/**
	 * 取消按钮	
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(new Rectangle(198, 191, 108, 29));
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgOwpBillHeadQuery.this.dispose();
				}
			});
		}
		return btnCancel;
	}

	public JTableListModel getTableModelHead() {
		return tableModelHead;
	}

	public void setTableModelHead(JTableListModel tableModelHead) {
		this.tableModelHead = tableModelHead;
	}

	public boolean isOutFlag() {
		return isOutFlag;
	}

	public void setOutFlag(boolean isOutFlag) {
		this.isOutFlag = isOutFlag;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
