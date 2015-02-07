package com.bestway.client.custom.common;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.ComboBoxEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicComboBoxUI;
import com.bestway.bcus.client.common.CheckBoxListCellRenderer;
import com.bestway.bcus.client.common.CheckBoxListItem;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.SteppedMetalComboBoxUI;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.common.Request;
import com.bestway.customs.common.action.BaseEncAction;
import com.bestway.customs.common.entity.BaseEmsHead;
import com.bestway.customs.common.entity.ImportBGDCondition;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.DateValueListener;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

@SuppressWarnings("unchecked")
public class DgChooseDeclarationNo extends JDialogBase {

	private JPanel jContentPane = null;

	private JLabel jLabel = null;

	private JButton jButton = null;

	private JButton jButton1 = null;
	
	private JTextField textField = null;

	protected BaseEncAction baseEncAction = null; // @jve:decl-index=0:

	private boolean isOK = false;

	private String customsDeclarationCode;


	public String getCustomsDeclarationCode() {
		return customsDeclarationCode;
	}

	public void setCustomsDeclarationCode(String customsDeclarationCode) {
		this.customsDeclarationCode = customsDeclarationCode;
	}

	public void setBaseEncAction(BaseEncAction baseEncAction) {
		this.baseEncAction = baseEncAction;
	}

	public boolean isOK() {
		return isOK;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgChooseDeclarationNo() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(314, 156));
		this.setContentPane(getJContentPane());
		this.setTitle("选择报关单号");

	}

	public void setVisible(boolean b) {
		if (b) {
			initUI();
		}
		super.setVisible(b);
	}

	/**
	 * 初始化一些组件
	 */
	private void initUI() {
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(20, 38, 84, 18));
			jLabel.setText("查询报关单号：");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(jLabel, null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton1(), null);
			textField = new JTextField();
			textField.setBounds(114, 37, 168, 21);
			jContentPane.add(textField);
			textField.setColumns(10);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new Rectangle(71, 66, 66, 25));
			jButton.setText("确定");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setCustomsDeclarationCode(textField.getText());
					dispose();
				}
			});
		}
		return jButton;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(new Rectangle(176, 66, 59, 24));
			jButton1.setText("取消");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton1;
	}
}
	