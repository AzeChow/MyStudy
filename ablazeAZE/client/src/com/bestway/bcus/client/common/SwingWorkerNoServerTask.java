package com.bestway.bcus.client.common;

import java.util.UUID;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import com.bestway.client.windows.form.FmMain;
import com.bestway.ui.message.JMessage;

public class SwingWorkerNoServerTask extends Thread {

	private JDialog dialog = null;
	public SwingWorkerNoServerTask(JDialog dialog){
		this.dialog = dialog;
	}
	
	public SwingWorkerNoServerTask(){
		
	}

	@Override
	public void run() {

		//
		// 用于标识这个对话框的ID
		//
		UUID taskId = UUID.randomUUID();
		final String flag = taskId.toString();
		try {
			long beginTime = System.currentTimeMillis();
			
			if(dialog != null){
				CommonProgress.showProgressDialog(flag, dialog,
					false, null, 5000);
			}else{
				CommonProgress.showProgressDialog(flag, FmMain.getInstance(),
					false, null, 5000);
			}
			CommonProgress.setMessage(flag, "系统正在获取资料，请稍后...");
			// //////////////////
			// do run logic
			//
			doRun(flag);
			//
			// ///////////////////
			CommonProgress.closeProgressDialog(flag);
			
			
			//StringBuffer messageDetail = lastInfo.getInfo();
			
			String info = " 任务完成,共用时:"
					+ (System.currentTimeMillis() - beginTime) + " 毫秒 ";
			//
			// 显示结果
			//
			JMessage.showMessageDialog(FmMain.getInstance(), info,
					JOptionPane.WARNING_MESSAGE, JOptionPane.DEFAULT_OPTION);			

		} catch (Exception e) {
			e.printStackTrace();
			CommonProgress.closeProgressDialog(flag);
			JMessage.showMessageDialog(FmMain.getInstance(), "获取失败！！！" + e.getMessage()+"",
					JOptionPane.WARNING_MESSAGE, JOptionPane.CLOSED_OPTION, e );				
		} finally {
			
		}
	}
	
	

	/**
	 * 唯一值,及调用方法
	 * 
	 * @param uuid
	 */
	protected void doRun(String taskId) {

	}


}
