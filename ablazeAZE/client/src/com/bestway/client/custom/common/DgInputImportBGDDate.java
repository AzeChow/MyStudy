package com.bestway.client.custom.common;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.common.Request;
import com.bestway.customs.common.action.BaseEncAction;
import com.bestway.customs.common.entity.ImportBGDCondition;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.DateValueListener;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

public class DgInputImportBGDDate extends JDialogBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JLabel jLabel = null;

	private JCalendarComboBox cbbBeginDate = null;

	private JLabel jLabel1 = null;

	private JCalendarComboBox cbbEndDate = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JLabel jLabel2 = null;

	private boolean isImportBGD;

	protected BaseEncAction baseEncAction = null; // @jve:decl-index=0:

	private boolean isOK = false;

	private JButton jButton2 = null;

	public void setBaseEncAction(BaseEncAction baseEncAction) {
		this.baseEncAction = baseEncAction;
	}

	public boolean isImportBGD() {
		return isImportBGD;
	}

	public void setImportBGD(boolean isImportBGD) {
		this.isImportBGD = isImportBGD;
	}

	public boolean isOK() {
		return isOK;
	}


	/**
	 * This method initializes
	 * 
	 */
	public DgInputImportBGDDate() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(352, 211));
		this.setContentPane(getJContentPane());
		this.setTitle("导入报关单的日期选择");

	}

	public void setVisible(boolean b) {
		if (b) {
			Date date = baseEncAction.getLastImportBGDDate(new Request(
					CommonVars.getCurrUser(), true), isImportBGD);			
			if(date==null){
				date=new Date();
				GregorianCalendar calendar = new GregorianCalendar();
				calendar.setTime(date);
				calendar.add(GregorianCalendar.DATE, -7);
				date=calendar.getTime();
			}
			this.cbbBeginDate.setDate(date);
				this.cbbEndDate.setDate(new Date());
		
		}
		super.setVisible(b);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(48, 88, 258, 35));
			jLabel2.setForeground(Color.blue);
//			jLabel2.setText("开始日期:最近一次导入报关单的日期减去三天");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(52, 58, 57, 27));
			jLabel1.setText("结束日期");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(51, 22, 54, 27));
			jLabel.setText("开始日期");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(jLabel, null);
			jContentPane.add(getCbbBeginDate(), null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getCbbEndDate(), null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton1(), null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(getJButton2(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(new Rectangle(112, 25, 170, 23));
			cbbBeginDate.addDateValueListener(new DateValueListener() {

				public void valueChanged(Date newDate) {
				
				}

			});
		}
		return cbbBeginDate;
	}

	/**
	 * This method initializes jCalendarComboBox1
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbEndDate() {
		if (cbbEndDate == null) {
			cbbEndDate = new JCalendarComboBox();
			cbbEndDate.setBounds(new Rectangle(115, 58, 166, 25));
		}
		return cbbEndDate;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new Rectangle(66, 136, 66, 25));
			jButton.setText("确定");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
										isOK = true;
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
			jButton1.setBounds(new Rectangle(212, 137, 59, 24));
			jButton1.setText("取消");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton1;
	}


	public ImportBGDCondition getCondition(){
		ImportBGDCondition condition=new ImportBGDCondition();
		condition.setImportBGD(this.isImportBGD);
		condition.setBeginDate(this.cbbBeginDate.getDate());
		condition.setEndDate(this.cbbEndDate.getDate());
		return condition;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setBounds(new Rectangle(142, 137, 60, 24));
			jButton2.setText("查询");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgQueryQpImport dg = new DgQueryQpImport();
					// dg.setImpExpFlag(impExpFlag);
					dg.setBaseEncAction(baseEncAction);
					dg.setVisible(true);
				}
			});
		}
		return jButton2;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
