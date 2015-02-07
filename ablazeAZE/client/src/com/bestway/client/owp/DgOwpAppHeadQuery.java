package com.bestway.client.owp;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.bestway.bcus.cas.entity.TempObject;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Condition;
import com.bestway.common.Request;
import com.bestway.owp.action.OwpAppAction;
import com.bestway.owp.entity.OwpAppHead;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * 组合查询外发加工申请表
 * @author wss
 * 2010.08.04
 */
public class DgOwpAppHeadQuery extends JDialogBase {

	private JPanel jPanel = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JLabel jLabel2 = null;
	private JLabel jLabel3 = null;
	private JLabel jLabel4 = null;
	private JTextField tfAppNo = null;
	private JTextField tfInTradeCode = null;
	private JTextField tfInTradeName = null;
	private JButton btnOK = null;
	private JButton btnCancel = null;
	
	private JCalendarComboBox cbbBeginDate = null;

	private JCalendarComboBox cbbEndDate = null;
	private JButton btnAppNo = null;
	private JButton btnInTradeCode = null;
	private JButton btnInTradeName = null;
	
	/**
	 * 外发加工远程服务接口
	 */
	private OwpAppAction owpAppAction = null;

	/**
	 * 外发加工申请表tableModel
	 * （从主窗口传至过来）
	 */
	private JTableListModel tableModelHead = null;
	
	/**
	 * 构造方法
	 */
	public DgOwpAppHeadQuery() {
		super();
		/**
		 * 初始化外发加工远程服务接口
		 */
		owpAppAction = (OwpAppAction) CommonVars.getApplicationContext()
																.getBean("owpAppAction");
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new Dimension(389, 224));
        this.setContentPane(getJPanel());
        this.setTitle("外发加工申请表查询");
			
	}
	
	
	/**
	 * 外发加工申请表tableModel
	 * @return
	 */
	public JTableListModel getTableModelHead() {
		return tableModelHead;
	}

	/**
	 * 外发加工申请表tableModel
	 * @param tableModelHead
	 */
	public void setTableModelHead(JTableListModel tableModelHead) {
		this.tableModelHead = tableModelHead;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(205, 108, 12, 25));
			jLabel4.setText("至");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(30, 108, 59, 25));
			jLabel3.setText("申报日期");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(30, 71, 87, 25));
			jLabel2.setText("承揽方企业名称");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(30, 42, 87, 25));
			jLabel1.setText("承揽方企业代码");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(30, 12, 87, 25));
			jLabel.setText("申请表编号");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel, null);
			jPanel.add(jLabel1, null);
			jPanel.add(jLabel2, null);
			jPanel.add(jLabel3, null);
			jPanel.add(jLabel4, null);
			jPanel.add(getTfAppNo(), null);
			jPanel.add(getTfInTradeCode(), null);
			jPanel.add(getTfInTradeName(), null);
			jPanel.add(getBtnOK(), null);
			jPanel.add(getBtnCancel(), null);
			jPanel.add(getCbbEndDate(), null);
			jPanel.add(getCbbBeginDate(), null);
			jPanel.add(getBtnAppNo(), null);
			jPanel.add(getBtnInTradeCode(), null);
			jPanel.add(getBtnInTradeName(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes tfAppNo	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfAppNo() {
		if (tfAppNo == null) {
			tfAppNo = new JTextField();
			tfAppNo.setBounds(new Rectangle(132, 12, 154, 25));
		}
		return tfAppNo;
	}

	/**
	 * This method initializes tfInTradeCode	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfInTradeCode() {
		if (tfInTradeCode == null) {
			tfInTradeCode = new JTextField();
			tfInTradeCode.setBounds(new Rectangle(132, 41, 154, 25));
		}
		return tfInTradeCode;
	}

	/**
	 * This method initializes tfInTradeName	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfInTradeName() {
		if (tfInTradeName == null) {
			tfInTradeName = new JTextField();
			tfInTradeName.setBounds(new Rectangle(132, 71, 154, 25));
		}
		return tfInTradeName;
	}

	/**
	 * This method initializes btnOK	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnOK() {
		if (btnOK == null) {
			btnOK = new JButton();
			btnOK.setBounds(new Rectangle(85, 151, 92, 31));
			btnOK.setText("确定");
			btnOK.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new MyFindThread().start();
				}
			});
		}
		return btnOK;
	}
	
	/**
	 * 查询线程
	 * @author wss
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
		
		//申报日期
		if(cbbBeginDate.getDate() != null){
			conditions.add(new Condition("and", null,"appDate", ">=",cbbBeginDate.getDate(), null));
		}
		if(cbbEndDate.getDate() != null){
			conditions.add(new Condition("and", null,"appDate", "<=",cbbEndDate.getDate(), null));
		}
		
		/**
		 * 组合条件查询外发加工申请表表头
		 */
		List<OwpAppHead> list = owpAppAction.findOwpAppHeadByConditions(new Request(CommonVars
				.getCurrUser()),conditions);
		System.out.println("wss list.size = " + list.size());
		
		tableModelHead.setList(list == null ? (new ArrayList<OwpAppHead>()) : list);
		
		//自动消失
		dispose();
	}

	/**
	 * This method initializes btnCancel	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(new Rectangle(203, 151, 94, 31));
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgOwpAppHeadQuery.this.dispose();
				}
			});
		}
		return btnCancel;
	}
	
	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setPreferredSize(new java.awt.Dimension(95, 25));
			cbbBeginDate.setBounds(new Rectangle(103, 109, 97, 25));
		}
		return cbbBeginDate;
	}
	
	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbEndDate() {
		if (cbbEndDate == null) {
			cbbEndDate = new JCalendarComboBox();
			cbbEndDate.setPreferredSize(new java.awt.Dimension(95, 25));
			cbbEndDate.setBounds(new Rectangle(222, 110, 94, 25));
		}
		return cbbEndDate;
	}

	/**
	 * This method initializes btnAppNo	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnAppNo() {
		if (btnAppNo == null) {
			btnAppNo = new JButton();
			btnAppNo.setBounds(new Rectangle(286, 12, 18, 25));
			btnAppNo.setText("...");
			btnAppNo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List  list = OwpQuery.getInstance().findOwpAppHeadInfo("appNo");
					if (list != null && list.size()>0) {
						TempObject temp = (TempObject)list.get(0);
						if(temp != null){
							tfAppNo.setText(temp.getObject() == null ? "":(String)temp.getObject());
						}
					}
				}
			});
		}
		return btnAppNo;
	}

	/**
	 * This method initializes btnInTradeCode	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnInTradeCode() {
		if (btnInTradeCode == null) {
			btnInTradeCode = new JButton();
			btnInTradeCode.setBounds(new Rectangle(286, 41, 18, 25));
			btnInTradeCode.setText("...");
			btnInTradeCode.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List  list = OwpQuery.getInstance().findOwpAppHeadInfo("inTradeCode");
					if (list != null && list.size()>0) {
						TempObject temp = (TempObject)list.get(0);
						if(temp != null){
							tfInTradeCode.setText(temp.getObject1() == null ? "":(String)temp.getObject1());
						}
					}
				}
			});
		}
		return btnInTradeCode;
	}

	/**
	 * This method initializes btnInTradeName	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnInTradeName() {
		if (btnInTradeName == null) {
			btnInTradeName = new JButton();
			btnInTradeName.setBounds(new Rectangle(286, 71, 18, 25));
			btnInTradeName.setText("...");
			btnInTradeName.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List  list = OwpQuery.getInstance().findOwpAppHeadInfo("inTradeName");
					if (list != null && list.size()>0) {
						TempObject temp = (TempObject)list.get(0);
						if(temp != null){
							tfInTradeName.setText(temp.getObject2() == null ? "":(String)temp.getObject2());
						}
					}
				}
			});
		}
		return btnInTradeName;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
