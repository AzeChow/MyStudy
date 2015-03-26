package com.bestway.bcus.client.manualdeclare;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

public class DgEmsPrintMonth extends JDialogBase {

	private JLabel lbChangeDate;

	private JLabel lbEnd;

	private JButton btnOK;

	private JButton btnCancel;

	private JCalendarComboBox cbbEndDate;

	private JCalendarComboBox cbbBeginDate;

	private JPanel panel;

	private boolean isOk = false;
	private JLabel lbToDate;

	public DgEmsPrintMonth() {

		initialize();

	}

	public boolean isOk() {
		return isOk;
	}

	public void setOk(boolean isOk) {
		this.isOk = isOk;
	}

	/**
	 * 初始化所有组件
	 */
	private void initialize() {

		setTitle("按变更日期打印");

		setSize(new Dimension(412, 187));

		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		setContentPane(getJPanel());

	}

	private JPanel getJPanel() {

		if (panel == null) {

			panel = new JPanel();

			panel.setBounds(0, 10, 442, 260);

			panel.setLayout(null);

			panel.add(getBtnOk());

			panel.add(getBtnCancel());

			panel.add(getCbbBeginDate());

			panel.add(getCbbEndDate());

			panel.add(getLbChangeDate());

			panel.add(getLbToDate());

		}

		return panel;

	}

	/**
	 * 变更日期
	 * 
	 * @return
	 */
	private JLabel getLbChangeDate() {

		if (lbChangeDate == null) {

			lbChangeDate = new JLabel("变更日期");

			lbChangeDate.setBounds(31, 46, 54, 21);

		}

		return lbChangeDate;
	}

	private JCalendarComboBox getCbbBeginDate() {

		if (cbbBeginDate == null) {

			Calendar calendar = Calendar.getInstance();

			calendar.add(Calendar.DATE, -30);

			Date beginTime = calendar.getTime();

			cbbBeginDate = new JCalendarComboBox();

			cbbBeginDate.setDate(beginTime);

			cbbBeginDate.setBounds(93, 46, 111, 21);

		}
		return cbbBeginDate;
	}

	private JCalendarComboBox getCbbEndDate() {

		if (cbbEndDate == null) {

			cbbEndDate = new JCalendarComboBox();

			cbbEndDate.setDate(new Date());

			cbbEndDate.setBounds(239, 46, 111, 21);

		}
		return cbbEndDate;
	}

	/**
	 * 确定按钮
	 * 
	 * @return
	 */
	private JButton getBtnOk() {

		if (btnOK == null) {

			btnOK = new JButton("确定");

			btnOK.setBounds(66, 111, 93, 23);

			btnOK.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					isOk = true;

					dispose();

				}
			});

		}

		return btnOK;

	}

	/**
	 * 取消按钮
	 * 
	 * @return
	 */
	private JButton getBtnCancel() {

		if (btnCancel == null) {

			btnCancel = new JButton("取消");

			btnCancel.setBounds(203, 111, 93, 23);

			btnCancel.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					dispose();

				}
			});

		}

		return btnCancel;

	}

	private JLabel getLbToDate() {

		if (lbToDate == null) {

			lbToDate = new JLabel("至");

			lbToDate.setBounds(214, 46, 26, 21);

		}
		return lbToDate;
	}

	public Date getBeginDate() {

		return cbbBeginDate.getDate() == null ? new Date() : cbbBeginDate
				.getDate();
	}

	public Date getEndDate() {
		return cbbEndDate.getDate() == null ? new Date() : cbbEndDate.getDate();
	}

}
