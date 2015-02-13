package com.bestway.pis.common;

import com.bestway.ui.winuicontrol.JDialogBase;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Rectangle;

import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JTextArea;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DgConsoleInfo extends JDialogBase {
	private JPanel panel;
	private JToolBar toolBar;
	private JButton btnOK;
	private JButton btnClose;
	private JTextArea textArea;
	public DgConsoleInfo() {
		this.setBounds(new Rectangle(500,300));
		getContentPane().add(getPanel(), BorderLayout.CENTER);
	}

    public void addInfo(String info){
        this.textArea.append(info+"\r\n");
    }
    
    public void clearInfo(){
        this.textArea.setText("");
    }
	
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setLayout(new BorderLayout(0, 0));
			panel.add(getToolBar(), BorderLayout.SOUTH);
			panel.add(getTextArea(), BorderLayout.CENTER);
		}
		return panel;
	}
	private JToolBar getToolBar() {
		if (toolBar == null) {
			toolBar = new JToolBar();
			FlowLayout f = new FlowLayout();
			f.setAlignment(FlowLayout.CENTER);
			f.setVgap(0);
			f.setHgap(0);
			toolBar.setLayout(f);
			toolBar.add(getBtnOK());
			toolBar.add(getBtnClose());
		}
		return toolBar;
	}
	private JButton getBtnOK() {
		if (btnOK == null) {
			btnOK = new JButton("确定");
			btnOK.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
		}
		return btnOK;
	}
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton("关闭");
			btnClose.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
		}
		return btnClose;
	}
	private JTextArea getTextArea() {
		if (textArea == null) {
			textArea = new JTextArea();
			textArea.setLineWrap(true);
		}
		return textArea;
	}
}
