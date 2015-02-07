/*
 * Created on 2005-7-19
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.erpbillcenter.parameter;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomFormattedTextFieldUtils;
import com.bestway.bcus.client.common.DataState;
import com.bestway.common.Request;
import com.bestway.common.erpbill.action.OrderCommonAction;
import com.bestway.common.erpbill.entity.Customparames;

/**
 * @author ls
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class PnOrderOption extends PnErpBillParameterCommon {

	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JButton btnEdit = null;
	private JButton btnAvailability = null;
	private JButton btnExit = null;
	private JLabel jLabel = null;
	private static PnOrderOption pnBillOption = null;
	private OrderCommonAction orderCommonAction = null; // @jve:decl-index=0:
	private ButtonGroup group = new ButtonGroup(); // @jve:decl-index=0:
	private ButtonGroup group2 = new ButtonGroup(); // @jve:decl-index=0:
	private Customparames parames = null; // @jve:decl-index=0:
	private Integer setbgtype = 0; // @jve:decl-index=0:
	private Integer rateSource = 0;  //  @jve:decl-index=0:
	private Integer decimalSize = 5;//料件及成品单耗表商品数量保留小数位 ，默认为5  //  @jve:decl-index=0:

	/**
	 * This method initializes
	 * 
	 */
	private PnOrderOption() {
		super();
		orderCommonAction = (OrderCommonAction) CommonVars
				.getApplicationContext().getBean("orderCommonAction");
		initialize();
		showData();
	}

	public static PnOrderOption getInstance() {
		if (pnBillOption == null) {
			pnBillOption = new PnOrderOption();
		}
		return pnBillOption;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		jLabel2 = new JLabel();
		jLabel2.setBounds(new Rectangle(80, 330, 235, 18));
		jLabel2.setText("料件及成品单耗表商品数量保留小数位：");
		jLabel1 = new JLabel();
		jLabel1.setBounds(22, 406, 140, 26);
		jLabel1.setText("");

		jLabel = new JLabel();
		this.setLayout(null);
		this.setSize(631, 446);
		jLabel.setBounds(3, 31, 586, 21);
		jLabel.setText("订单管理参数设定");
		jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12));
		this.add(getJPanel(), null);
		this.add(getJPanel1(), null);
		// this.add(getCbIsDoubleClickBillState(), null);
		this.add(getBtnEdit(), null);
		this.add(getBtnAvailability(), null);
		this.add(getBtnExit(), null);
		this.add(jLabel, null);
		this.add(jLabel1, null);
		this.add(getJPanel2(), null);
		this.add(getJPanel3(), null);
		this.add(jLabel2, null);
		this.add(getTfDecimalSize(), null);
		this.btnAvailability.setEnabled(true);
		this.btnEdit.setEnabled(true);
		this.btnExit.setEnabled(false);
		this.rbDzsc.setEnabled(false);
		this.rbZzsc.setEnabled(false);
		this.rbZzscDz.setEnabled(false);
		group.add(rbDzsc);
		group.add(rbZzsc);
		group.add(rbZzscDz);
		group2.add(jRadioButton3);
		group2.add(jRadioButton4);
		group2.add(jRadioButton5);
		//this.tfDecimalSize.setEditable(false);
		CustomFormattedTextFieldUtils.setFormatterFactory(this.tfDecimalSize, 0);

		parames = orderCommonAction.findCustomparames(new Request(CommonVars
				.getCurrUser()));
		if (parames == null) {
			parames = new Customparames();
			parames.setDecimalSize(5);
		}

		setbgtype = parames.getSetbgtype();
		rateSource = parames.getRateSource();
		decimalSize = parames.getDecimalSize();
		init(setbgtype, rateSource,decimalSize);
		
		this.jRadioButton3.setEnabled(false);
		this.jRadioButton4.setEnabled(false);
		this.jRadioButton5.setEnabled(false);

	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setBounds(3, 55, 625, 3);
			jPanel
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
		}
		return jPanel;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setBounds(3, 394, 625, 3);
			jPanel1
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
		}
		return jPanel1;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setBounds(387, 406, 68, 26);
			btnEdit.setText("修改");
			btnEdit
					.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN,
							12));
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// Auto-generated Event stub actionPerformed()

					btnAvailability.setEnabled(true);
					btnEdit.setEnabled(false);
					btnExit.setEnabled(false);
					rbDzsc.setEnabled(true);
					rbZzsc.setEnabled(true);
					rbZzscDz.setEnabled(true);
					if (rbDzsc.isSelected()) {
						jRadioButton4.setEnabled(true);
						jRadioButton5.setEnabled(true);
					} else if (rbZzsc.isSelected()) {
						jRadioButton3.setEnabled(true);
					} else if (rbZzscDz.isSelected()) {
						jRadioButton3.setEnabled(true);

					}
					tfDecimalSize.setEnabled(true);

				}
			});
		}
		return btnEdit;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAvailability() {
		if (btnAvailability == null) {
			btnAvailability = new JButton();
			btnAvailability.setBounds(463, 406, 68, 26);
			btnAvailability.setText("生效");
			btnAvailability.setFont(new java.awt.Font("Dialog",
					java.awt.Font.PLAIN, 12));
			btnAvailability
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {

							parames.setSetbgtype(setbgtype);
							parames.setRateSource(rateSource);
							parames.setDecimalSize(tfDecimalSize.getValue() == null ? 5 : Integer
									.valueOf(tfDecimalSize.getValue().toString()));
							orderCommonAction.saveCustomparames(new Request(
									CommonVars.getCurrUser()), parames);
							btnEdit.setEnabled(true);
							btnExit.setEnabled(true);
							btnAvailability.setEnabled(false);
							rbDzsc.setEnabled(false);
							rbZzsc.setEnabled(false);
							rbZzscDz.setEnabled(false);
							jRadioButton3.setEnabled(false);
							jRadioButton4.setEnabled(false);
							jRadioButton5.setEnabled(false);
							tfDecimalSize.setEnabled(false);

						}
					});
		}
		return btnAvailability;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setBounds(539, 406, 68, 26);
			btnExit.setText("关闭");
			btnExit
					.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN,
							12));
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					close();
				}
			});
		}
		return btnExit;
	}

	/**
	 * 初始化组件
	 * 
	 */
	private void initUIComponents() {
		jPanel.setBounds(3, this.getHeight() - 50, this.getWidth() - 6, 3);
		jPanel1.setBounds(3, 55, this.getWidth() - 6, 3);

		btnExit.setBounds(this.getWidth() - 92, this.getHeight() - 40, 68, 26);
		btnAvailability.setBounds(
				this.getWidth() - 92 - btnExit.getWidth() - 5,
				this.getHeight() - 40, 68, 26);
		btnEdit.setBounds(this.getWidth() - 92 - btnExit.getWidth()
				- btnAvailability.getWidth() - 10, this.getHeight() - 40, 68,
				26);
		jLabel1.setBounds(jLabel1.getX(), btnEdit.getY(), jLabel1.getWidth(),
				jLabel1.getHeight());
	}

	protected void paintComponent(Graphics g) {
		initUIComponents();
		super.paintComponent(g);
	}

	// /**
	// * 填充数据
	// *
	// */
	// private void fillData() {
	// this.btnAvailability.setEnabled(true);
	// this.btnEdit.setEnabled(true);
	// this.btnExit.setEnabled(false);
	// this.rbDzsc.setEnabled(false);
	// this.rbZzsc.setEnabled(false);
	// this.rbZzscDz.setEnabled(false);
	// // this.jRadioButton.setEnabled(false);
	// // this.jRadioButton1.setEnabled(false);
	// // this.jRadioButton2.setEnabled(false);
	// group.add(rbDzsc);
	// group.add(rbZzsc);
	// group.add(rbZzscDz);
	// group2.add(jRadioButton3);
	// group2.add(jRadioButton4);
	// group2.add(jRadioButton5);
	//
	// parames = orderCommonAction.findCustomparames(new
	// Request(CommonVars.getCurrUser()));
	// if (parames ==null){
	// parames = new Customparames();
	// }else{
	//
	// setbgtype = parames.getSetbgtype();
	// rateSource = parames.getRateSource();
	// init(setbgtype,rateSource);
	// }
	// this.jRadioButton3.setEnabled(false);
	// this.jRadioButton4.setEnabled(false);
	// this.jRadioButton5.setEnabled(false);
	// }

	private void init(Integer bgtype, Integer rateSource,Integer decimalSize) {

		if (bgtype == 1) {

			this.rbDzsc.setSelected(true);
		} else if (bgtype == 2) {

			this.rbZzsc.setSelected(true);
		} else if (bgtype == 3) {
			this.rbZzscDz.setSelected(true);
		}
		rateSource = rateSource == null ? 1 : rateSource;
		if (rateSource == 1) { // 1: 报关常用工厂ＢＯＭ ２：ＢＯＭ备案 ３：报关单耗
			this.jRadioButton3.setSelected(true);

		} else if (rateSource == 2) {
			this.jRadioButton4.setSelected(true);
		} else if (rateSource == 3) {
			this.jRadioButton5.setSelected(true);
		}
		
		this.tfDecimalSize.setValue(decimalSize);
	}

	/**
	 * 显示数据
	 * 
	 */
	private void showData() {

		super.setContainerEnabled(this, false);
	}

	private int dataSate = DataState.BROWSE;
	private JLabel jLabel1 = null;
	private JPanel jPanel2 = null;
	private JRadioButton rbZzsc = null;
	private JRadioButton rbZzscDz = null;
	private JRadioButton rbDzsc = null;
	private JPanel jPanel3 = null;
	private JRadioButton jRadioButton3 = null;
	private JRadioButton jRadioButton4 = null;
	private JRadioButton jRadioButton5 = null;
	private JLabel jLabel2 = null;
	private JFormattedTextField tfDecimalSize = null;

	/**
	 * 设置状态
	 * 
	 */
	private void setState() {
		btnEdit.setEnabled(dataSate == DataState.BROWSE);
		btnAvailability.setEnabled(dataSate != DataState.BROWSE);
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.setBounds(new Rectangle(75, 85, 479, 85));
			jPanel2.setBorder(BorderFactory.createTitledBorder(null,
					"\u62a5\u5173\u7c7b\u578b",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), new Color(51, 51, 51)));
			jPanel2.add(getRbZzsc(), null);
			jPanel2.add(getRbZzscDz(), null);
			jPanel2.add(getRbDzsc(), null);
		}
		return jPanel2;
	}

	/**
	 * This method initializes rbZzsc
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbZzsc() {
		if (rbZzsc == null) {
			rbZzsc = new JRadioButton();
			rbZzsc.setBounds(new Rectangle(40, 34, 90, 21));
			rbZzsc.setText("纸质手册");
			rbZzsc.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
						setbgtype = 2;
						jRadioButton4.setEnabled(false);
						jRadioButton5.setEnabled(false);
						jRadioButton3.setEnabled(true);
						jRadioButton4.setSelected(false);
						jRadioButton5.setSelected(false);
						jRadioButton3.setSelected(true);

					}
				}
			});
		}

		return rbZzsc;
	}

	/**
	 * This method initializes rbZzscDz
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbZzscDz() {
		if (rbZzscDz == null) {
			rbZzscDz = new JRadioButton();
			rbZzscDz.setBounds(new Rectangle(160, 35, 90, 21));
			rbZzscDz.setText("电子化手册");
			rbZzscDz.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
						setbgtype = 3;
						jRadioButton4.setEnabled(false);
						jRadioButton5.setEnabled(false);
						jRadioButton3.setEnabled(true);
						jRadioButton4.setSelected(false);
						jRadioButton5.setSelected(false);
						jRadioButton3.setSelected(true);

					}
				}
			});
		}
		return rbZzscDz;
	}

	/**
	 * This method initializes rbDzsc
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbDzsc() {
		if (rbDzsc == null) {
			rbDzsc = new JRadioButton();
			rbDzsc.setBounds(new Rectangle(280, 34, 143, 21));
			rbDzsc.setText("电子手册");
			rbDzsc.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
						setbgtype = 1;
						jRadioButton4.setEnabled(true);
						jRadioButton5.setEnabled(true);
						jRadioButton3.setEnabled(false);
						jRadioButton4.setSelected(false);
						jRadioButton5.setSelected(true);
					}
				}
			});
		}

		return rbDzsc;
	}

	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setLayout(null);
			jPanel3.setBounds(new Rectangle(75, 200, 483, 85));
			jPanel3.setBorder(BorderFactory.createTitledBorder(null,
					"\u5355\u8017\u6765\u6e90",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), new Color(51, 51, 51)));
			jPanel3.add(getJRadioButton3(), null);
			jPanel3.add(getJRadioButton4(), null);
			jPanel3.add(getJRadioButton5(), null);
		}
		return jPanel3;
	}

	/**
	 * This method initializes jRadioButton3
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton3() {
		if (jRadioButton3 == null) {
			jRadioButton3 = new JRadioButton();
			jRadioButton3.setBounds(new Rectangle(40, 30, 116, 21));
			jRadioButton3.setText("报关常用ＢＯＭ");
			jRadioButton3.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
						rateSource = 1;
					}
				}
			});
		}

		return jRadioButton3;
	}

	/**
	 * This method initializes jRadioButton4
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton4() {
		if (jRadioButton4 == null) {
			jRadioButton4 = new JRadioButton();
			jRadioButton4.setBounds(new Rectangle(170, 30, 100, 21));
			jRadioButton4.setText("ＢＯＭ备案");
			jRadioButton4.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
						rateSource = 2;
					}
				}
			});
		}
		return jRadioButton4;
	}

	/**
	 * This method initializes jRadioButton5
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton5() {
		if (jRadioButton5 == null) {
			jRadioButton5 = new JRadioButton();
			jRadioButton5.setBounds(new Rectangle(280, 30, 90, 21));
			jRadioButton5.setText("报关单耗");
			jRadioButton5.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
						rateSource = 3;
					}
				}
			});
		}
		return jRadioButton5;
	}

	/**
	 * This method initializes tfDecimalSize	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JFormattedTextField getTfDecimalSize() {
		if (tfDecimalSize == null) {
			tfDecimalSize = new JFormattedTextField();
			tfDecimalSize.setBounds(new Rectangle(324, 330
					, 57, 20));
		}
		return tfDecimalSize;
	}
}
