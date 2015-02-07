package com.bestway.client.owp;

import com.bestway.bcus.client.common.CommonStepProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.owp.FmOwpBillSend.DeclareThread;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareFileInfo;
import com.bestway.common.constant.DeclareState;
import com.bestway.owp.action.OwpBillAction;
import com.bestway.owp.entity.OwpBillRecvHead;
import com.bestway.owp.entity.OwpBillSendHead;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.Rectangle;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

public class DgOwpBillAnnul extends JDialogBase {

	/**
	 * 外发加工登记表远程服务接口
	 */
	private OwpBillAction owpBillAction = null;
	private JPanel pnMain = null;
	private JLabel lbSure = null;
	private JLabel lbAuunlReason = null;
	private JTextField tfAuunlReason = null;
	private JButton btnOK = null;
	private JButton btnCancel = null;
	/**
	 * 进出货标志。true:出 、false:进
	 */
	private boolean isOutFlag = false;
	/**
	 * 外发加工发货登记表表头tableModelHead
	 */
	private JTableListModel tableModelHead = null;
	
	private OwpBillSendHead owpSendHead =null;
	/**
	 * 窗体构造函数
	 */
	public DgOwpBillAnnul() {
		super();
		/**
		 * 初始化外发加工登记表远程服务接口
		 */
		owpBillAction = (OwpBillAction) CommonVars
			.getApplicationContext().getBean("owpBillAction");
		initialize();
	}

	/**
	 * 初始化窗体
	 */
	private void initialize() {
        this.setSize(new Dimension(357, 136));
        this.setContentPane(getPnMain());
        this.setTitle("撤销提示");
	}

	/**
	 * 获取主容器	
	 */
	private JPanel getPnMain() {
		if (pnMain == null) {
			lbAuunlReason = new JLabel();
			lbAuunlReason.setBounds(new Rectangle(197, 10, 136, 18));
			lbAuunlReason.setText("请录入撤销原因并确认：");
			lbSure = new JLabel();
			lbSure.setBounds(new Rectangle(13, 10, 95, 18));
			lbSure.setText("确认要撤销吗？");
			pnMain = new JPanel();
			pnMain.setLayout(null);
			pnMain.add(lbSure, null);
			pnMain.add(lbAuunlReason, null);
			pnMain.add(getTfAuunlReason(), null);
			pnMain.add(getBtnOK(), null);
			pnMain.add(getBtnCancel(), null);
		}
		return pnMain;
	}

	/**
	 * This method initializes tfAuunlReason	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfAuunlReason() {
		if (tfAuunlReason == null) {
			tfAuunlReason = new JTextField();
			tfAuunlReason.setBounds(new Rectangle(12, 37, 329, 22));
		}
		return tfAuunlReason;
	}

	/**
	 * 确认按钮
	 */
	private JButton getBtnOK() {
		if (btnOK == null) {
			btnOK = new JButton();
			btnOK.setBounds(new Rectangle(156, 69, 73, 22));
			btnOK.setText("确认");
			btnOK.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (JOptionPane.showConfirmDialog(DgOwpBillAnnul.this,
							"确定要撤销吗？", "确认", 0) != 0) {
						return;
					}
					if (vaildatorDataIsNull()) {
						return;
					}
					saveData();
					dispose();
				}
			});
		}
		return btnOK;
	}

	/**
	 * 验证错误
	 */
	private boolean vaildatorDataIsNull(){
		if(null==tfAuunlReason.getText()||"".equals(tfAuunlReason.getText())){
			JOptionPane.showMessageDialog(null, "撤销原因不可为空", "警告",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		return false;
	}
	/**
	 * 保存数据、填充实体
	 */
	private void saveData(){
		if(isOutFlag){
			new DeclareThread().start();
			
		}else{
			OwpBillRecvHead owpBillRecvHead = (OwpBillRecvHead) tableModelHead
			.getCurrentRow();
			owpBillRecvHead.setCancelReason(tfAuunlReason.getText().trim());
			owpBillRecvHead.setCancelMark(1);
			this.owpBillAction.saveOwpBillRecvHead(new Request(CommonVars.getCurrUser()),
					owpBillRecvHead);
		}
	}
	/***
	 * 执行申报线程
	 * @author sxk
	 */
	class DeclareThread extends Thread {
		public void run() {
			try {
				String taskId = CommonStepProgress.getExeTaskId();
				CommonStepProgress.showStepProgressDialog(taskId);
				CommonStepProgress.setStepMessage("系统正获取数据，请稍后...");
				Request request = new Request(CommonVars.getCurrUser());
				request.setTaskId(taskId);
				
				OwpBillSendHead owpBillSendHead = (OwpBillSendHead) tableModelHead
				.getCurrentRow();
				owpBillSendHead.setCancelReason(tfAuunlReason.getText().trim());
				owpBillSendHead.setCancelMark(1);
				
				try {
					System.out.println("1111111111");
					DeclareFileInfo fileInfo = owpBillAction
							.declareOwpBillCancelHead(new Request(CommonVars
									.getCurrUser()), owpBillSendHead, taskId);
					System.out.println("22222222222");
					
					owpSendHead = owpBillAction.findOwpBillSendHeadById(new Request(
							CommonVars.getCurrUser()), owpBillSendHead.getId());
					
					System.out.println("333333333333");
					
					owpBillAction.saveOwpSendBillHead(new Request(CommonVars.getCurrUser()),
							owpSendHead);
					System.out.println("4444444444444");
					
					tableModelHead.updateRow(owpSendHead);
					
					CommonStepProgress.closeStepProgressDialog();
					JOptionPane.showMessageDialog(DgOwpBillAnnul.this, fileInfo
							.getFileInfoSpec(), "提示", 1);
				} catch (Exception ex) {
					CommonStepProgress.closeStepProgressDialog();
					JOptionPane.showMessageDialog(DgOwpBillAnnul.this, "系统申报失败 "
							+ ex.getMessage(), "确认", 1);
				}
//				setState();
			} catch (Exception ex) {
				System.out.println(ex.getStackTrace() + "\n-->"
						+ ex.getMessage());
			} finally {
				CommonStepProgress.closeStepProgressDialog();
			}
		}
	}
	/**
	 * 取消按钮	
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(new Rectangle(257, 69, 75, 21));
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgOwpBillAnnul.this.dispose();
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
