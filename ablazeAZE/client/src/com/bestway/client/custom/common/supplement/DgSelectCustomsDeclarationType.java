/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DgErrorMessage.java
 *
 * Created on Mar 21, 2009, 11:33:32 AM
 */
package com.bestway.client.custom.common.supplement;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.constant.SupplmentType;
import com.bestway.ui.winuicontrol.JDialogBase;
import javax.swing.JButton;

/**
 * 
 * @author chl
 */
@SuppressWarnings("unchecked")
public class DgSelectCustomsDeclarationType extends JDialogBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel pnContext = null;
	protected JTableListModel tableModel = null;
	protected int projectType;
	private JLabel lbSupplmentType = null;
	private JComboBox cbbSupplmentType = null;
	private JButton btnNext = null;
	private Integer supplment = 0; // @jve:decl-index=0:
	
	public int result = 0;

	/** Creates new form DgErrorMessage */
	public DgSelectCustomsDeclarationType() {
		super();
		initialize();
	}

	private void initialize() {
		this.setSize(new Dimension(300, 138));
		this.setContentPane(getPnContext());
		setTitle("补充申报类型选择");
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				result = 0;
			}
		});
	}

	/**
	 * This method initializes pnContext
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnContext() {
		if (pnContext == null) {
			lbSupplmentType = new JLabel();
			lbSupplmentType.setText("补充报关类型：");
			pnContext = new JPanel();
			pnContext.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));
			pnContext.add(lbSupplmentType, null);
			pnContext.add(getCbbSupplmentType(), null);
			pnContext.add(getBtnNext(), null);
		}
		return pnContext;
	}

	/**
	 * This method initializes cbbSupplmentType
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbSupplmentType() {
		if (cbbSupplmentType == null) {
			cbbSupplmentType = new JComboBox();
			cbbSupplmentType.setPreferredSize(new Dimension(120, 20));
			cbbSupplmentType
					.addItem(new ItemProperty(
							SupplmentType.INITIATIVE_SYPPLMENT.toString(),
							SupplmentType
									.getSupplmentTypeDesc(SupplmentType.INITIATIVE_SYPPLMENT)));
			cbbSupplmentType
					.addItem(new ItemProperty(
							SupplmentType.PASSIVITY_SYPPLMENT.toString(),
							SupplmentType
									.getSupplmentTypeDesc(SupplmentType.PASSIVITY_SYPPLMENT)));
			
			cbbSupplmentType
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							setSupplment(Integer.valueOf(((ItemProperty)(cbbSupplmentType.getSelectedItem())).getCode()));
						}
					});
			cbbSupplmentType.setSelectedIndex(1);
		}
		return cbbSupplmentType;
	}

	/**
	 * This method initializes btnNext
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnNext() {
		if (btnNext == null) {
			btnNext = new JButton();
			btnNext.setPreferredSize(new Dimension(80, 20));
			btnNext.setText("下一步");
			btnNext.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setVisible(false);
					result = 1;
				}
			});
		}
		return btnNext;
	}

	public Integer getSupplment() {
		return supplment;
	}

	private void setSupplment(Integer supplment) {
		this.supplment = supplment;
	}

}
