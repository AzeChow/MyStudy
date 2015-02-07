package com.bestway.bcus.client.system;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.custombase.action.CustomBaseAction;
import com.bestway.common.Request;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgEditCustomsDeclaretion extends JDialogBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel jPanel = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JLabel jLabel2 = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private JTextField jTextField = null;
	private JTextField jTextField1 = null;
	private JTextField jTextField11 = null;
	private JLabel jLabel3 = null;
	private JLabel jLabel4 = null;
	private JLabel jLabel5 = null;
	private JTextField jTextField2 = null;
	private JTextField jTextField3 = null;
	private JTextField jTextField4 = null;
	private BaseCustomsDeclaration base = null; // @jve:decl-index=0:
	private Map tmap = new HashMap(); // @jve:decl-index=0:

	/**
	 * This method initializes
	 * 
	 */
	public DgEditCustomsDeclaretion() {
		super();
		initialize();
	}

	private void showData() {
		if (base == null) {
			return;
		}
		jTextField.setText(base.getCustomsDeclarationCode() == null ? "" : base
				.getCustomsDeclarationCode());
		jTextField1.setText(base.getUnificationCode() == null ? "" : base
				.getUnificationCode());
		jTextField11.setText(base.getPreCustomsDeclarationCode() == null ? ""
				: base.getPreCustomsDeclarationCode());
	}

	private boolean fillAdnCheckData() {
		List allTYBH = (List) tmap.get("allTYBH");
		List allYLRH = (List) tmap.get("allYLRH");
		List allBGDH = (List) tmap.get("allBGDH");
		String BGDH = jTextField2.getText();
		if (allBGDH != null && allBGDH.contains(BGDH)) {
			JOptionPane.showMessageDialog(DgEditCustomsDeclaretion.this,
					"报关单号有重复！", "提示！", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		String TYBH = jTextField3.getText();
		if (allTYBH != null && allTYBH.contains(TYBH)) {

			JOptionPane.showMessageDialog(DgEditCustomsDeclaretion.this,
					"统一编号有重复！", "提示！", JOptionPane.WARNING_MESSAGE);
			return false;

		}
		String YLRH = jTextField4.getText();
		if (allYLRH != null && allYLRH.contains(YLRH)) {
			JOptionPane.showMessageDialog(DgEditCustomsDeclaretion.this,
					"预录入号有重复！", "提示！", JOptionPane.WARNING_MESSAGE);
			return false;

		}
		if (BGDH != null && !BGDH.equals("")) {
			base.setCustomsDeclarationCode(BGDH);
		}
		if (TYBH != null && !TYBH.equals("")) {
			base.setUnificationCode(TYBH);
		}

		if (YLRH != null && !YLRH.equals("")) {
			base.setPreCustomsDeclarationCode(YLRH);
			base.setTempPreCode(YLRH);
		}

		return true;
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(492, 290));
		this.setContentPane(getJPanel());
		this.setTitle("修改报关单");

	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(257, 122, 62, 25));
			jLabel5.setText("新预录入号");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(257, 79, 62, 25));
			jLabel4.setText("新统一编号");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(257, 34, 62, 25));
			jLabel3.setText("新报关单号");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(39, 122, 62, 25));
			jLabel2.setText("原预录入号");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(39, 79, 62, 25));
			jLabel1.setText("原统一编号");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(39, 34, 62, 25));
			jLabel.setText("原报关单号");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel, null);
			jPanel.add(jLabel1, null);
			jPanel.add(jLabel2, null);
			jPanel.add(getJButton(), null);
			jPanel.add(getJButton1(), null);
			jPanel.add(getJTextField(), null);
			jPanel.add(getJTextField1(), null);
			jPanel.add(getJTextField11(), null);
			jPanel.add(jLabel3, null);
			jPanel.add(jLabel4, null);
			jPanel.add(jLabel5, null);
			jPanel.add(getJTextField2(), null);
			jPanel.add(getJTextField3(), null);
			jPanel.add(getJTextField4(), null);
		}
		return jPanel;
	}

	@Override
	public void setVisible(boolean f) {
		if (f) {
			showData();
		}
		super.setVisible(f);
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new Rectangle(142, 198, 58, 28));
			jButton.setText("确定");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!fillAdnCheckData()) {
						return;
					}
					CustomBaseAction customBaseAction = (CustomBaseAction) CommonVars
							.getApplicationContext()
							.getBean("customBaseAction");
					customBaseAction.saveOrUpdateObject(new Request(CommonVars
							.getCurrUser()), base);
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
			jButton1.setBounds(new Rectangle(268, 198, 58, 28));
			jButton1.setText("取消");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					dispose();
				}

			});
		}
		return jButton1;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(new Rectangle(105, 34, 110, 25));
			jTextField.setEditable(false);
		}
		return jTextField;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			jTextField1.setBounds(new Rectangle(105, 79, 113, 25));
			jTextField1.setEditable(false);
		}
		return jTextField1;
	}

	/**
	 * This method initializes jTextField11
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField11() {
		if (jTextField11 == null) {
			jTextField11 = new JTextField();
			jTextField11.setBounds(new Rectangle(105, 122, 112, 25));
			jTextField11.setEditable(false);
		}
		return jTextField11;
	}

	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField2() {
		if (jTextField2 == null) {
			jTextField2 = new JTextField();
			jTextField2.setBounds(new Rectangle(327, 33, 116, 25));
		}
		return jTextField2;
	}

	/**
	 * This method initializes jTextField3
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField3() {
		if (jTextField3 == null) {
			jTextField3 = new JTextField();
			jTextField3.setBounds(new Rectangle(327, 79, 117, 25));
		}
		return jTextField3;
	}

	/**
	 * This method initializes jTextField4
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField4() {
		if (jTextField4 == null) {
			jTextField4 = new JTextField();
			jTextField4.setBounds(new Rectangle(327, 122, 117, 25));
		}
		return jTextField4;
	}

	public BaseCustomsDeclaration getBase() {
		return base;
	}

	public void setBase(BaseCustomsDeclaration base) {
		this.base = base;
	}

	public Map getTmap() {
		return tmap;
	}

	public void setTmap(Map tmap) {
		this.tmap = tmap;
	}

} // @jve:decl-index=0:visual-constraint="225,42"
