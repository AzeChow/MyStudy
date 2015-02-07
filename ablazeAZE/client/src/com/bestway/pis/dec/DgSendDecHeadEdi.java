package com.bestway.pis.dec;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

import com.bestway.common.CommonUtils;
import com.bestway.pis.constant.ProcessConsts;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

public class DgSendDecHeadEdi extends JDialogBase {
	private JPanel panel;
	private JLabel lblNewLabel;
	private JLabel label;
	private JCalendarComboBox ccbSendDate;
	private JCalendarComboBox ccbHopeDate;
	private JComboBox ccbHopeHourDate;
	private JLabel lblNewLabel_1;
	private JLabel label_1;
	private JRadioButton rb1;
	private JRadioButton rb2;
	private JRadioButton rb3;
	private JLabel label_2;
	private JTextArea taEntNote;
	private JButton btnOk;
	private JButton btnCancel;

	public boolean isOk = false;
	private Map<String, Object> returnValue = new HashMap<String, Object>();
	private final ButtonGroup buttonGroup = new ButtonGroup();

	public DgSendDecHeadEdi() {
		this.setBounds(new Rectangle(800, 500));
		getContentPane().add(getPanel(), BorderLayout.CENTER);
		initUIComponents();
	}

	/**
	 * 初始化组件
	 */
	private void initUIComponents() {

		Date now = new Date();
		this.ccbSendDate.setDate(now);
		this.ccbSendDate.setEnabled(false);
		for (int i = 1; i < 13; i++) {
			this.ccbHopeHourDate.addItem(i);
		}
		this.rb2.setSelected(true);
		// 设置实际工作完成时间，默认加8小时
		Calendar c = Calendar.getInstance();
		c.add(Calendar.HOUR_OF_DAY, 8);
		Date finishDate = c.getTime();
		ccbHopeDate.setDate(finishDate);
		ccbHopeHourDate.setSelectedItem(11);
	}

	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setLayout(null);
			panel.add(getLblNewLabel());
			panel.add(getLabel());
			panel.add(getCcbSendDate());
			panel.add(getCcbHopeDate());
			panel.add(getCcbHopeHourDate());
			panel.add(getLblNewLabel_1());
			panel.add(getLabel_1());
			panel.add(getRb1());
			panel.add(getRb2());
			panel.add(getRb3());
			panel.add(getLabel_2());
			panel.add(getTaEntNote());
			panel.add(getBtnOk());
			panel.add(getBtnCancel());
		}
		return panel;
	}

	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("上传日期：");
			lblNewLabel.setBounds(35, 26, 92, 15);
		}
		return lblNewLabel;
	}

	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel("期望完成日期：");
			label.setBounds(35, 51, 92, 15);
		}
		return label;
	}

	private JCalendarComboBox getCcbSendDate() {
		if (ccbSendDate == null) {
			ccbSendDate = new JCalendarComboBox();
			ccbSendDate.setBounds(119, 26, 117, 20);
		}
		return ccbSendDate;
	}

	private JCalendarComboBox getCcbHopeDate() {
		if (ccbHopeDate == null) {
			ccbHopeDate = new JCalendarComboBox();
			ccbHopeDate.setBounds(119, 51, 117, 20);
		}
		return ccbHopeDate;
	}

	private JComboBox getCcbHopeHourDate() {
		if (ccbHopeHourDate == null) {
			ccbHopeHourDate = new JComboBox();
			ccbHopeHourDate.setBounds(240, 50, 43, 21);
		}
		return ccbHopeHourDate;
	}

	private JLabel getLblNewLabel_1() {
		if (lblNewLabel_1 == null) {
			lblNewLabel_1 = new JLabel("时");
			lblNewLabel_1.setBounds(289, 51, 24, 15);
		}
		return lblNewLabel_1;
	}

	private JLabel getLabel_1() {
		if (label_1 == null) {
			label_1 = new JLabel("紧急程度：");
			label_1.setBounds(35, 83, 78, 15);
		}
		return label_1;
	}

	private JRadioButton getRb1() {
		if (rb1 == null) {
			rb1 = new JRadioButton("紧急");
			buttonGroup.add(rb1);
			rb1.setBounds(115, 79, 60, 23);
		}
		return rb1;
	}

	private JRadioButton getRb2() {
		if (rb2 == null) {
			rb2 = new JRadioButton("一般");
			buttonGroup.add(rb2);
			rb2.setBounds(176, 79, 60, 23);
		}
		return rb2;
	}

	private JRadioButton getRb3() {
		if (rb3 == null) {
			rb3 = new JRadioButton("不急");
			buttonGroup.add(rb3);
			rb3.setBounds(238, 79, 60, 23);
		}
		return rb3;
	}

	private JLabel getLabel_2() {
		if (label_2 == null) {
			label_2 = new JLabel("企业留言：");
			label_2.setBounds(35, 108, 78, 15);
		}
		return label_2;
	}

	private JTextArea getTaEntNote() {
		if (taEntNote == null) {
			taEntNote = new JTextArea();
			taEntNote.setBounds(35, 133, 627, 254);
		}
		return taEntNote;
	}

	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton("确定");
			btnOk.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (!checkData()) {
						return;
					}

					Date nowDate = CommonUtils.getBeginDate(new Date());

					// Date hopeDate = ccbHopeDate.getDate();

					// 期望日期
					Date hopeHourDate = getHourDate(ccbHopeDate.getDate(),
							(Integer) ccbHopeHourDate.getSelectedItem());

					Date nowTime = new Date();

					// 判断 期望完成时间 是否小于 当前时间
					if (hopeHourDate.before(nowDate)
							&& hopeHourDate.before(nowTime)) {
						JOptionPane.showMessageDialog(DgSendDecHeadEdi.this,
								"期望完成日期不能早于今天。");
						return;
					}

					// 填充数据
					fillData();
					isOk = true;

					dispose();
				}
			});
			btnOk.setBounds(189, 404, 93, 23);
		}
		return btnOk;
	}

	/**
	 * 填充数据
	 */
	private void fillData() {
		Date hopeHourDate = getHourDate(ccbHopeDate.getDate(),
				ccbHopeHourDate.getSelectedIndex());

		System.out.println(hopeHourDate + "       hOPE !!!!");

		returnValue.put(ProcessConsts.CUSTOM_START_DATE, (new SimpleDateFormat(
				"yyyyMMdd")).format(ccbSendDate.getDate()));

		returnValue.put(ProcessConsts.CUSTOM_FINISH_DATE,
				(new SimpleDateFormat("yyyyMMddHHmmss")).format(hopeHourDate));

		String level = ProcessConsts.PROCESS_LEVEL_NORMAL;

		if (rb1.isSelected()) {

			level = ProcessConsts.PROCESS_LEVEL_FAST;

		} else if (rb2.isSelected()) {

			level = ProcessConsts.PROCESS_LEVEL_SLOW;

		}

		returnValue.put(ProcessConsts.CUSTOM_PROCESS_LEVEL, level);

		returnValue.put(ProcessConsts.CUSTOM_NOTE, taEntNote.getText().trim());

	}

	private Boolean checkData() {
		boolean boo = true;
		if (ccbHopeDate.getDate() == null) {
			JOptionPane.showMessageDialog(this, "请选择期望完成日期");
			return false;
		}

		if (ccbHopeHourDate.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(this, "请选择期望完成日期的小时");
			return false;
		}
		return boo;
	}

	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton("取消");
			btnCancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					isOk = false;
					dispose();
				}
			});
			btnCancel.setBounds(315, 404, 93, 23);
		}
		return btnCancel;
	}

	/**
	 * 返回值
	 * 
	 * @return
	 */
	public Map<String, Object> getReturnValue() {
		if (isOk) {
			return returnValue;
		}
		return null;
	}

	/**
	 * 设置小时时间
	 *
	 * @param date
	 * @param hour
	 *            小时
	 * @return
	 */
	public static Date getHourDate(Date date, int hour) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		return calendar.getTime();
	}

	@Override
	public void setVisible(boolean flag) {
		if (flag) {
			// initUIComponents();
		}
		super.setVisible(flag);
	}
}
