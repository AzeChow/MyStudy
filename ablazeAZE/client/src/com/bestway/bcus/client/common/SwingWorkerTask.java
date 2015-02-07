package com.bestway.bcus.client.common;

import java.util.UUID;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import com.bestway.client.windows.form.FmMain;
import com.bestway.common.ProgressInfo;
import com.bestway.common.tools.action.ToolsAction;
import com.bestway.ui.message.JMessage;

public class SwingWorkerTask extends Thread {

	private JDialog dialog = null;
	public SwingWorkerTask(JDialog dialog){
		this.dialog = dialog;
	}
	
	public SwingWorkerTask(){
		
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
			ProgressTask progressTask = new ProgressTask() {
				protected void setClientTipMessage() {
					ToolsAction toolsAction = (ToolsAction) CommonVars
							.getApplicationContext().getBean("toolsAction");
					ProgressInfo progressInfo = toolsAction
							.getProgressInfo(flag);
					if (progressInfo != null
							&& progressInfo.getHintMessage() != null) {
						CommonProgress.setMessage(flag, progressInfo
								.getHintMessage());
					}
				}
			};
			if(dialog != null){
				CommonProgress.showProgressDialog(flag, dialog,
					false, progressTask, 5000);
			}else{
				CommonProgress.showProgressDialog(flag, FmMain.getInstance(),
					false, progressTask, 5000);
			}
			CommonProgress.setMessage(flag, "系统正在获取资料，请稍后...");
			// //////////////////
			// do run logic
			//
			doRun(flag);
			//
			// ///////////////////
			CommonProgress.closeProgressDialog(flag);
			progressTask.setProgressTaskClose();
			//
			// 读取结果
			//
			ToolsAction toolsAction = (ToolsAction) CommonVars
					.getApplicationContext().getBean("toolsAction");
			ProgressInfo lastInfo = toolsAction.getProgressInfo(flag);
			
			StringBuffer messageDetail = lastInfo.getInfo();
			
			String info = " 任务完成,共用时:"
					+ (System.currentTimeMillis() - beginTime) + " 毫秒 ";
			//
			// 显示结果
			//
			JMessage.showMessageDialog(FmMain.getInstance(), info,
					JOptionPane.WARNING_MESSAGE, JOptionPane.DEFAULT_OPTION, messageDetail.toString());			

		} catch (Exception e) {
			e.printStackTrace();
			CommonProgress.closeProgressDialog(flag);
			JMessage.showMessageDialog(FmMain.getInstance(), "获取失败！！！" + e.getMessage()+"",
					JOptionPane.WARNING_MESSAGE, JOptionPane.CLOSED_OPTION, e );				
		} finally {
			ToolsAction toolsAction = (ToolsAction) CommonVars
					.getApplicationContext().getBean("toolsAction");
			toolsAction.removeProgressInfo(flag);
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
