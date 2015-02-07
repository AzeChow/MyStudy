/*
 * Created on 2004-7-21
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.common;

import java.awt.Dialog;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;

import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.JDialogBaseHelper;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class CommonStepProgress {
	
	private static DgStepProgress dgStepProgress = null;

	public static void showStepProgressDialog() {
		if (dgStepProgress == null) {
			dgStepProgress = new DgStepProgress();
			dgStepProgress.setModal(false);
		}
		dgStepProgress.setVisible(true);
	}

	public static void showStepProgressDialog(String taskId) {
		if (dgStepProgress == null) {
			dgStepProgress = new DgStepProgress();
			dgStepProgress.setModal(false);
			dgStepProgress.setTaskId(taskId);
		}
		dgStepProgress.setVisible(true);
	}

	public static void closeStepProgressDialog() {
		if (dgStepProgress != null) {
			dgStepProgress.dispose();
			// // Thread.interrupted();
			dgStepProgress.close();
			dgStepProgress = null;
		}
	}

	public static void setStepMessage(String message) {
		if (dgStepProgress != null) {
			dgStepProgress.setMessage(message);
		}
	}

	public static void setStepProgressMaximum(int maximum) {
		if (dgStepProgress != null) {
			dgStepProgress.setProgressMaximum(maximum);
		}
	}

	public static void setStepProgressValue(int value) {
		if (dgStepProgress != null) {
			dgStepProgress.setProgressValue(value);
		}
	}

	// //////////////////////////////////////////////////////////
	// // 用于运行独立对象的进度条对话框
	// //////////////////////////////////////////////////////////
	//    
	// public static void showProgressDialog(String flag,Dialog owner, boolean
	// isModal,
	// ProgressTask progressTask, long period) {
	//	        
	// JDialogBase dialog = JDialogBaseHelper.getJDialogBaseByFlag(flag);
	// if (dialog == null) {
	// dialog = new DgProgress(owner, isModal, progressTask, period);
	// JDialogBaseHelper.putJDialogBaseToFlag(flag,dialog);
	// dialog.setVisible(true);
	// } else {
	// dialog.setVisibleNoChange(true);
	// }
	// }
	//    
	// public static void showProgressDialog(String flag,JFrame owner, boolean
	// isModal,
	// ProgressTask progressTask, long period) {
	//	        
	// JDialogBase dialog = JDialogBaseHelper.getJDialogBaseByFlag(flag);
	// if (dialog == null) {
	// dialog = new DgProgress(owner, isModal, progressTask, period);
	// JDialogBaseHelper.putJDialogBaseToFlag(flag,dialog);
	// dialog.setVisible(true);
	// } else {
	// dialog.setVisibleNoChange(true);
	// }
	// }
	//    
	//    
	//    
	// public static void showProgressDialog(String flag,Dialog owner) {
	// JDialogBase dialog = JDialogBaseHelper.getJDialogBaseByFlag(flag);
	// if (dialog == null) {
	// dialog = new DgProgress(owner, false, null, 0);
	// JDialogBaseHelper.putJDialogBaseToFlag(flag,dialog);
	// dialog.setVisible(true);
	// } else {
	// dialog.setVisibleNoChange(true);
	// }
	// }
	//    
	//    
	// public static void closeProgressDialog(String flag) {
	// JDialogBase dialog = JDialogBaseHelper.getJDialogBaseByFlag(flag);
	// if (dialog != null) {
	// dialog.dispose();
	// }
	// }
	//    
	// public static void setMessage(String flag,String message) {
	// JDialogBase dialog = JDialogBaseHelper.getJDialogBaseByFlag(flag);
	// if (dialog != null) {
	// ((DgProgress)dialog).setMessage(message);
	// }
	// }

	public static String getExeTaskId() {
		return Double.toString(Math.random());
	}
	// private static DgStepProgress dgStepProgress = null;
	//
	// // public static void showStepProgressDialog() {
	// // if (dgStepProgress == null) {
	// // dgStepProgress = new DgStepProgress();
	// // dgStepProgress.setModal(false);
	// // }
	// // dgStepProgress.setVisible(true);
	// // }
	//
	// public static void showStepProgressDialog(String taskId) {
	// if (dgStepProgress == null) {
	// dgStepProgress = new DgStepProgress();
	// dgStepProgress.setModal(false);
	// dgStepProgress.setTaskId(taskId);
	// }
	// dgStepProgress.setVisible(true);
	// }
	//     
	//    
	// public static void closeStepProgressDialog() {
	// if (dgStepProgress != null) {
	// dgStepProgress.dispose();
	// // // Thread.interrupted();
	// dgStepProgress.close();
	// dgStepProgress = null;
	// }
	// }
	//  
	// public static void setStepMessage(String message) {
	// if (dgStepProgress != null) {
	// dgStepProgress.setMessage(message);
	// }
	// }
	//
	// public static void setStepProgressMaximum(int maximum) {
	// if (dgStepProgress != null) {
	// dgStepProgress.setProgressMaximum(maximum);
	// }
	// }
	//
	// public static void setStepProgressValue(int value) {
	// if (dgStepProgress != null) {
	// dgStepProgress.setProgressValue(value);
	// }
	// }
	//   
	// // //////////////////////////////////////////////////////////
	// // // 用于运行独立对象的进度条对话框
	// // //////////////////////////////////////////////////////////
	// //
	// // public static void showProgressDialog(String flag,Dialog owner,
	// boolean isModal,
	// // ProgressTask progressTask, long period) {
	// //
	// // JDialogBase dialog = JDialogBaseHelper.getJDialogBaseByFlag(flag);
	// // if (dialog == null) {
	// // dialog = new DgProgress(owner, isModal, progressTask, period);
	// // JDialogBaseHelper.putJDialogBaseToFlag(flag,dialog);
	// // dialog.setVisible(true);
	// // } else {
	// // dialog.setVisibleNoChange(true);
	// // }
	// // }
	// //
	// // public static void showProgressDialog(String flag,JFrame owner,
	// boolean isModal,
	// // ProgressTask progressTask, long period) {
	// //
	// // JDialogBase dialog = JDialogBaseHelper.getJDialogBaseByFlag(flag);
	// // if (dialog == null) {
	// // dialog = new DgProgress(owner, isModal, progressTask, period);
	// // JDialogBaseHelper.putJDialogBaseToFlag(flag,dialog);
	// // dialog.setVisible(true);
	// // } else {
	// // dialog.setVisibleNoChange(true);
	// // }
	// // }
	// //
	// //
	// //
	// // public static void showProgressDialog(String flag,Dialog owner) {
	// // JDialogBase dialog = JDialogBaseHelper.getJDialogBaseByFlag(flag);
	// // if (dialog == null) {
	// // dialog = new DgProgress(owner, false, null, 0);
	// // JDialogBaseHelper.putJDialogBaseToFlag(flag,dialog);
	// // dialog.setVisible(true);
	// // } else {
	// // dialog.setVisibleNoChange(true);
	// // }
	// // }
	// //
	// //
	// // public static void closeProgressDialog(String flag) {
	// // JDialogBase dialog = JDialogBaseHelper.getJDialogBaseByFlag(flag);
	// // if (dialog != null) {
	// // dialog.dispose();
	// // }
	// // }
	// //
	// // public static void setMessage(String flag,String message) {
	// // JDialogBase dialog = JDialogBaseHelper.getJDialogBaseByFlag(flag);
	// // if (dialog != null) {
	// // ((DgProgress)dialog).setMessage(message);
	// // }
	// // }
	//    
	// public static String getExeTaskId() {
	// return Double.toString(Math.random());
	// }
}