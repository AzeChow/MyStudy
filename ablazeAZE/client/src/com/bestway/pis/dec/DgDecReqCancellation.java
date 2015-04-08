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

public class DgDecReqCancellation extends JDialogBase {
	private JPanel panel;
	private JLabel label_2;
	private JTextArea txaNote;
	private JButton btnOk;
	private JButton btnCancel;

	public boolean isOk = false;

	public boolean isOk() {
		return isOk;
	}

    public String getNote(){
        return txaNote.getText().trim();
    }


	public DgDecReqCancellation() {
		this.setBounds(new Rectangle(600, 400));
		getContentPane().add(getPanel(), BorderLayout.CENTER);
	}


	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setLayout(null);
			panel.add(getLabel_2());
			panel.add(getTxaNote());
			panel.add(getBtnOk());
			panel.add(getBtnCancel());
		}
		return panel;
	}

	private JLabel getLabel_2() {
		if (label_2 == null) {
			label_2 = new JLabel("撤单原因：");
			label_2.setBounds(10, 35, 71, 15);
		}
		return label_2;
	}

	private JTextArea getTxaNote() {
		if (txaNote == null) {
			txaNote = new JTextArea();
			txaNote.setBounds(87, 35, 462, 254);
		}
		return txaNote;
	}

	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton("确定");
			btnOk.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					isOk = true;
		            dispose();
				}
			});
			btnOk.setBounds(186, 306, 93, 23);
		}
		return btnOk;
	}

	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton("取消");
			btnCancel.setBounds(312, 306, 93, 23);
			btnCancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					isOk = false;
					dispose();
				}
			});
		}
		return btnCancel;
	}
}
