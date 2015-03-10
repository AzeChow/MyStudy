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
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class CommonProgress {

	private static DgProgress dgProgress = null;

	// private static DgStepProgress dgStepProgress = null;

	public static void showProgressDialog(Dialog owner) {
		if (dgProgress == null) {
			dgProgress = new DgProgress(owner, false);
		}
		dgProgress.setVisible(true);
	}

	public static void showProgressDialog(JFrame owner) {
		if (dgProgress == null) {
			dgProgress = new DgProgress(owner, false);
		}
		dgProgress.setVisible(true);
	}

	// add by zhangyong 2006/09/25
	public static void showProgressDialog(JInternalFrame owner) {
		if (dgProgress == null) {
			dgProgress = new DgProgress(owner, false);
		}
		dgProgress.setVisible(true);
	}

	public static void showProgressDialog() {
		if (dgProgress == null) {
			dgProgress = new DgProgress();
			dgProgress.setModal(false);
		}
		dgProgress.setVisible(true);
	}

	public static void closeProgressDialog() {

		if (dgProgress != null) {

			dgProgress.dispose();

			dgProgress = null;
		}
	}

	public static void setMessage(String message) {
		if (dgProgress != null) {
			dgProgress.setMessage(message);
		}
	}

	public static void setDgProgress(DgProgress dgProgress) {
		CommonProgress.dgProgress = dgProgress;
	}

	// ////////////////////////////////////////////////////////
	// 用于运行独立对象的进度条对话框
	// ////////////////////////////////////////////////////////

	public static void showProgressDialog(String flag, Dialog owner,
			boolean isModal, ProgressTask progressTask, long period) {

		JDialogBase dialog = JDialogBaseHelper.getJDialogBaseByFlag(flag);
		if (dialog == null) {
			dialog = new DgProgress(owner, isModal, progressTask, period);
			JDialogBaseHelper.putJDialogBaseToFlag(flag, dialog);
			dialog.setVisible(true);
		} else {
			dialog.setVisibleNoChange(true);
		}
	}

	public static void showProgressDialog(String flag, JFrame owner,
			boolean isModal, ProgressTask progressTask, long period) {

		JDialogBase dialog = JDialogBaseHelper.getJDialogBaseByFlag(flag);
		if (dialog == null) {
			dialog = new DgProgress(owner, isModal, progressTask, period);
			JDialogBaseHelper.putJDialogBaseToFlag(flag, dialog);
			dialog.setVisible(true);
		} else {
			dialog.setVisibleNoChange(true);
		}
	}

	public static void showProgressDialog(String flag, JInternalFrame owner) {
		JDialogBase dialog = JDialogBaseHelper.getJDialogBaseByFlag(flag);
		if (dialog == null) {
			dialog = new DgProgress(owner, false, null, 0);
			JDialogBaseHelper.putJDialogBaseToFlag(flag, dialog);
			dialog.setVisible(true);
		} else {
			dialog.setVisibleNoChange(true);
		}
	}

	public static void showProgressDialog(String flag, Dialog owner) {
		JDialogBase dialog = JDialogBaseHelper.getJDialogBaseByFlag(flag);
		if (dialog == null) {
			dialog = new DgProgress(owner, false, null, 0);
			JDialogBaseHelper.putJDialogBaseToFlag(flag, dialog);
			dialog.setVisible(true);
		} else {
			dialog.setVisibleNoChange(true);
		}
	}

	public static void closeProgressDialog(String flag) {
		JDialogBase dialog = JDialogBaseHelper.getJDialogBaseByFlag(flag);
		if (dialog != null) {
			dialog.dispose();
		}
	}

	public static void setMessage(String flag, String message) {
		JDialogBase dialog = JDialogBaseHelper.getJDialogBaseByFlag(flag);
		if (dialog != null) {
			((DgProgress) dialog).setMessage(message);
		}
	}

	public static void setJProgressBarValue(String flag, final int value) {
		JDialogBase dialog = JDialogBaseHelper.getJDialogBaseByFlag(flag);
		if (dialog != null) {
			((DgProgress) dialog).setJProgressBarValue(value);
		}
	}

	public static void initJProgressBar(String flag, final int maxValue) {
		JDialogBase dialog = JDialogBaseHelper.getJDialogBaseByFlag(flag);
		if (dialog != null) {
			((DgProgress) dialog).initJProgressBar(maxValue);
		}
	}

	public static String getExeTaskId() {
		return Double.toString(Math.random());
	}
}