/*
 *联网监管系统参数设置
 * Created on 2004-8-24
 * 
 * checked on 2009-1-21 by lm
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.transferfactory;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.common.Request;
import com.bestway.common.transferfactory.action.TransferFactoryManageAction;
import com.bestway.common.transferfactory.entity.TransParameterSet;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import java.awt.Font;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FmTransParameterSet extends JInternalFrameBase {

	private javax.swing.JPanel pnContent = null;

	private JPanel pnCenter = null;

	private JTextField tfNumber = null;

	private JButton btnSave = null;

	private JButton btnAmend = null;

	private int dataState = DataState.BROWSE;

	private JPanel pnTop = null;

//	private JButton jButton3 = null;

	private JLabel lbImg = null;

	private JLabel lbNumber = null;

	private ManualDeclareAction manualDecleareAction = null;

	public TransferFactoryManageAction transferFactoryManageAction = null;

	private JButton btnClose = null;

	private JPanel pnLow = null;

	private JPanel pnRow = null;

	private JPanel pnDownIn = null;

	private JLabel lbIsManyCustomsDeclaration = null;

	private JCheckBox cbManyCustomsDeclaration = null;

	private JToolBar tbbDown = null;

	private JCheckBox cbIsAutoJieAn = null;

	private JCheckBox cbIsNoEdit = null;

	private JCheckBox cbLimit = null;

	/**
	 * This is the default constructor
	 */
	public FmTransParameterSet() {
		super();
		manualDecleareAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		this.transferFactoryManageAction = (TransferFactoryManageAction) CommonVars
				.getApplicationContext().getBean("transferFactoryManageAction");
		initialize();

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("联网监管系统参数设置");
		this.setSize(895, 565);
		this.setContentPane(getPnContent());
		transferFactoryManageAction.getParaPurview(new Request(CommonVars
				.getCurrUser()));
		fillWindow();
		dataState = DataState.BROWSE;
		setState();

	}
	/**
	 * 窗体填充数据
	 */

	private void fillWindow() {
		String transferCode = manualDecleareAction.getBpara(new Request(
				CommonVars.getCurrUser()), BcusParameter.TransferCode);
		tfNumber.setText(transferCode);
		TransParameterSet parameterSet = transferFactoryManageAction
				.findTransParameterSet(new Request(CommonVars.getCurrUser(),
						true));
		if (parameterSet == null
				|| parameterSet.getCustomsEnvelopUsedMultiple() == null) {
			this.cbManyCustomsDeclaration.setSelected(false);
		} else {
			this.cbManyCustomsDeclaration.setSelected(parameterSet
					.getCustomsEnvelopUsedMultiple());
		}
		
		if (parameterSet == null
				|| parameterSet.getIsAutoJieAn() == null) {
			this.cbIsAutoJieAn.setSelected(false);
		}else{
			this.cbIsAutoJieAn.setSelected(parameterSet.getIsAutoJieAn());
		}

		if (parameterSet == null
				|| parameterSet.getIsNoEdit() == null) {
			this.cbIsNoEdit.setSelected(false);
		}else{
			this.cbIsNoEdit.setSelected(parameterSet.getIsNoEdit());
		}
		
		if (parameterSet == null
				|| parameterSet.getIsLimit() == null) {
			this.cbLimit.setSelected(false);
		}else{
			this.cbLimit.setSelected(parameterSet.getIsLimit());
		}
	}

	/**
	 * This method initializes pnContent
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getPnContent() {
		if (pnContent == null) {
			pnContent = new javax.swing.JPanel();
			pnContent.setLayout(new BorderLayout());
			pnContent.setBorder(javax.swing.BorderFactory
					.createCompoundBorder(null, null));
			pnContent.add(getPnCenter(), java.awt.BorderLayout.CENTER);
			pnContent.add(getPnTop(), java.awt.BorderLayout.NORTH);
			pnContent.add(getTbbDown(), BorderLayout.SOUTH);
		}
		return pnContent;
	}

	/**
	 * 
	 * This method initializes pnCenter
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getPnCenter() {
		if (pnCenter == null) {
			lbIsManyCustomsDeclaration = new JLabel();
			lbIsManyCustomsDeclaration.setBounds(new Rectangle(41, 84, 304, 23));
			lbIsManyCustomsDeclaration.setText("2，同一关封是否可以使用于多个报关单");
			lbIsManyCustomsDeclaration.setForeground(Color.blue);
			lbNumber = new JLabel();
			lbNumber.setBounds(new java.awt.Rectangle(83, 47, 34, 18));
			lbNumber.setText("编号");
			lbImg = new JLabel();
			lbImg.setBounds(new Rectangle(301, 247, 86, 55));
			lbImg.setIcon(new ImageIcon(getClass().getResource(
					"/com/bestway/bcus/client/resources/images/gj.gif")));
			lbImg.setText("");
			javax.swing.JLabel jLabel = new JLabel();

			pnCenter = new JPanel();
			pnCenter.setLayout(null);
			jLabel.setText("1，关封申请表编号");
			jLabel.setForeground(java.awt.Color.blue);
			jLabel.setBounds(39, 18, 305, 21);
			pnCenter.setForeground(new java.awt.Color(51, 0, 255));
			pnCenter.add(jLabel, null);
			pnCenter.add(getTfNumber(), null);
			pnCenter.add(lbImg, null);
			pnCenter.add(lbNumber, null);
			pnCenter.add(getPnLow(), null);
			pnCenter.add(getPnRow(), null);
			pnCenter.add(lbIsManyCustomsDeclaration, null);
			pnCenter.add(getCbManyCustomsDeclaration(), null);
			pnCenter.add(getCbIsAutoJieAn(), null);
			pnCenter.add(getCbIsNoEdit(), null);
			pnCenter.add(getCbLimit(), null);
		}
		return pnCenter;
	}

	/**
	 * 
	 * This method initializes tfNumber
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfNumber() {
		if (tfNumber == null) {
			tfNumber = new JTextField();
			tfNumber.setBounds(123, 47, 178, 22);
		}
		return tfNumber;
	}

	/**
	 * 
	 * This method initializes btnSave
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("保存");
			btnSave.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					manualDecleareAction.saveBpara(new Request(CommonVars
							.getCurrUser()), BcusParameter.TransferCode,
							tfNumber.getText());
					TransParameterSet parameterSet = transferFactoryManageAction
							.findTransParameterSet(new Request(CommonVars
									.getCurrUser(), true));
					parameterSet.setCustomsEnvelopUsedMultiple(cbManyCustomsDeclaration
							.isSelected());
					parameterSet.setIsAutoJieAn(cbIsAutoJieAn.isSelected());
					parameterSet.setIsNoEdit(cbIsNoEdit.isSelected());
					parameterSet.setIsLimit(cbLimit.isSelected());
					transferFactoryManageAction.saveTransParameterSet(
							new Request(CommonVars.getCurrUser(), true),
							parameterSet);
					dataState = DataState.BROWSE;
					setState();
				}
			});

		}
		return btnSave;
	}

	/**
	 * 
	 * This method initializes btnAmend
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnAmend() {
		if (btnAmend == null) {
			btnAmend = new JButton();
			btnAmend.setText("修改");
			btnAmend.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					dataState = DataState.EDIT;
					setState();
				}
			});

		}
		return btnAmend;
	}
	/**
	 * 设置状态
	 */
	private void setState() {
		this.tfNumber.setEditable(!(dataState == DataState.BROWSE));
		this.cbManyCustomsDeclaration.setEnabled(dataState != DataState.BROWSE);
		this.cbIsAutoJieAn.setEnabled(dataState != DataState.BROWSE);
		this.cbIsNoEdit.setEnabled(dataState != DataState.BROWSE);
		this.cbLimit.setEnabled(dataState != DataState.BROWSE);
	}

	/**
	 * 
	 * This method initializes pnTop
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getPnTop() {
		if (pnTop == null) {
			pnTop = new JPanel();
			javax.swing.JLabel jLabel17 = new JLabel();

			javax.swing.JLabel jLabel18 = new JLabel();

			javax.swing.JLabel jLabel19 = new JLabel();

			pnTop.setLayout(new BorderLayout());
			jLabel17.setText("");
			jLabel17
					.setIcon(new ImageIcon(
							getClass()
									.getResource(
											"/com/bestway/bcus/client/resources/images/titlepoint.jpg")));
			jLabel18.setText("转厂管理参数设置");
			jLabel18
					.setFont(new Font("\u65b0\u5b8b\u4f53", Font.BOLD, 20));
			jLabel18.setForeground(new java.awt.Color(255, 153, 0));
			jLabel19.setText("");
			jLabel19.setIcon(new ImageIcon(getClass().getResource(
					"/com/bestway/bcus/client/resources/images/titlepic.jpg")));
			pnTop
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			pnTop.setBackground(java.awt.Color.white);
			pnTop.add(jLabel17, java.awt.BorderLayout.WEST);
			pnTop.add(jLabel18, java.awt.BorderLayout.CENTER);
			pnTop.add(jLabel19, java.awt.BorderLayout.EAST);
		}
		return pnTop;
	}

	/**
	 * This method initializes btnClose
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("关闭");
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmTransParameterSet.this.doDefaultCloseAction();
				}
			});
		}
		return btnClose;
	}

	/**
	 * This method initializes pnLow
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnLow() {
		if (pnLow == null) {
			pnLow = new JPanel();
			pnLow.setBounds(new Rectangle(394, 2, 8, 344));
			pnLow.setBorder(javax.swing.BorderFactory.createLineBorder(
					java.awt.Color.lightGray, 5));
		}
		return pnLow;
	}

	/**
	 * This method initializes pnRow
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnRow() {
		if (pnRow == null) {
			pnRow = new JPanel();
			pnRow.setBounds(new java.awt.Rectangle(-2, 345, 886, 1));
			pnRow.setBorder(javax.swing.BorderFactory.createLineBorder(
					java.awt.Color.lightGray, 5));
		}
		return pnRow;
	}

	/**
	 * This method initializes pnDownIn
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnDownIn() {
		if (pnDownIn == null) {
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 2;
			gridBagConstraints2.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints2.anchor = GridBagConstraints.CENTER;
			gridBagConstraints2.gridy = 0;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 1;
			gridBagConstraints1.insets = new Insets(0, 10, 0, 10);
			gridBagConstraints1.gridy = 0;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.insets = new Insets(0, 0, 0, 10);
			gridBagConstraints.gridy = 0;
			pnDownIn = new JPanel();
			pnDownIn.setLayout(new GridBagLayout());
			pnDownIn
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
			pnDownIn.add(getBtnAmend(), gridBagConstraints);
			pnDownIn.add(getBtnSave(), gridBagConstraints1);
			pnDownIn.add(getBtnClose(), gridBagConstraints2);
		}
		return pnDownIn;
	}

	/**
	 * This method initializes cbManyCustomsDeclaration
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbManyCustomsDeclaration() {
		if (cbManyCustomsDeclaration == null) {
			cbManyCustomsDeclaration = new JCheckBox();
			cbManyCustomsDeclaration.setBounds(new Rectangle(80, 118, 230, 27));
			cbManyCustomsDeclaration.setText("可以使用于多个报关单");
		}
		return cbManyCustomsDeclaration;
	}

	/**
	 * This method initializes tbbDown
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getTbbDown() {
		if (tbbDown == null) {
			tbbDown = new JToolBar();
			tbbDown.add(getPnDownIn());
		}
		return tbbDown;
	}

	/**
	 * This method initializes cbIsAutoJieAn	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbIsAutoJieAn() {
		if (cbIsAutoJieAn == null) {
			cbIsAutoJieAn = new JCheckBox();
			cbIsAutoJieAn.setBounds(new Rectangle(41, 156, 274, 25));
			cbIsAutoJieAn.setText("是否自动结案");
		}
		return cbIsAutoJieAn;
	}

	/**
	 * This method initializes jCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbIsNoEdit() {
		if (cbIsNoEdit == null) {
			cbIsNoEdit = new JCheckBox();
			cbIsNoEdit.setBounds(new Rectangle(42, 199, 275, 28));
			cbIsNoEdit.setText("进出货转厂单据,单据号可修改");
		}
		return cbIsNoEdit;
	}

	/**
	 * This method initializes cbLimit	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbLimit() {
		if (cbLimit == null) {
			cbLimit = new JCheckBox();
			cbLimit.setBounds(new Rectangle(42, 249, 182, 21));
			cbLimit.setText("是否允许关封单据超量");
		}
		return cbLimit;
	}
} // @jve:decl-index=0:visual-constraint="10,16"
