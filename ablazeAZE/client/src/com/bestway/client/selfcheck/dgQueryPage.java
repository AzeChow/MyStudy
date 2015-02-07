package com.bestway.client.selfcheck;

import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.Rectangle;
import javax.swing.JComboBox;
import javax.swing.JButton;

public class dgQueryPage extends JDialogBase {

	private JPanel jPanel = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JLabel jLabel2 = null;
	private JLabel jLabel3 = null;
	private JLabel jLabel4 = null;
	private JLabel jLabel5 = null;
	private JLabel jLabel6 = null;
	private JComboBox cbbCheckupWare = null;
	private JComboBox cbbName = null;
	private JComboBox cbbCode = null;
	private JComboBox cbbId = null;
	private JComboBox cbbClient = null;
	private JComboBox cbbTurnoverType = null;
	private JComboBox cbbBeginDate = null;
	private JComboBox cbbEndDate = null;
	private JLabel jLabel7 = null;
	private JButton btnOK = null;
	private JButton btnCancel = null;
	/**
	 * This method initializes 
	 * 
	 */
	public dgQueryPage() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new Dimension(446, 346));
        this.setTitle("请输入检索条件");
        this.setContentPane(getJPanel());
			
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel7 = new JLabel();
			jLabel7.setBounds(new Rectangle(269, 240, 16, 18));
			jLabel7.setText("至");
			jLabel6 = new JLabel();
			jLabel6.setBounds(new Rectangle(56, 235, 84, 18));
			jLabel6.setText("报关单日期范围");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(56, 200, 84, 18));
			jLabel5.setText("进出口类型");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(56, 165, 84, 18));
			jLabel4.setText("检索客户");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(56, 130, 84, 18));
			jLabel3.setText("序号");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(56, 95, 84, 18));
			jLabel2.setText("编码");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(56, 60, 84, 18));
			jLabel1.setText("名称");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(56, 25, 84, 18));
			jLabel.setText("商检商品");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel, null);
			jPanel.add(jLabel1, null);
			jPanel.add(jLabel2, null);
			jPanel.add(jLabel3, null);
			jPanel.add(jLabel4, null);
			jPanel.add(jLabel5, null);
			jPanel.add(jLabel6, null);
			jPanel.add(getCbbCheckupWare(), null);
			jPanel.add(getCbbName(), null);
			jPanel.add(getCbbCode(), null);
			jPanel.add(getCbbId(), null);
			jPanel.add(getCbbClient(), null);
			jPanel.add(getCbbTurnoverType(), null);
			jPanel.add(getCbbBeginDate(), null);
			jPanel.add(getCbbEndDate(), null);
			jPanel.add(jLabel7, null);
			jPanel.add(getBtnOK(), null);
			jPanel.add(getBtnCancel(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes cbbCheckupWare	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbCheckupWare() {
		if (cbbCheckupWare == null) {
			cbbCheckupWare = new JComboBox();
			cbbCheckupWare.setBounds(new Rectangle(157, 25, 216, 27));
		}
		return cbbCheckupWare;
	}

	/**
	 * This method initializes cbbName	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbName() {
		if (cbbName == null) {
			cbbName = new JComboBox();
			cbbName.setBounds(new Rectangle(157, 60, 216, 27));
		}
		return cbbName;
	}

	/**
	 * This method initializes cbbCode	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbCode() {
		if (cbbCode == null) {
			cbbCode = new JComboBox();
			cbbCode.setBounds(new Rectangle(157, 95, 216, 27));
		}
		return cbbCode;
	}

	/**
	 * This method initializes cbbId	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbId() {
		if (cbbId == null) {
			cbbId = new JComboBox();
			cbbId.setBounds(new Rectangle(157, 130, 216, 27));
		}
		return cbbId;
	}

	/**
	 * This method initializes cbbClient	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbClient() {
		if (cbbClient == null) {
			cbbClient = new JComboBox();
			cbbClient.setBounds(new Rectangle(157, 165, 216, 27));
		}
		return cbbClient;
	}

	/**
	 * This method initializes cbbTurnoverType	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbTurnoverType() {
		if (cbbTurnoverType == null) {
			cbbTurnoverType = new JComboBox();
			cbbTurnoverType.setBounds(new Rectangle(157, 200, 216, 27));
		}
		return cbbTurnoverType;
	}

	/**
	 * This method initializes cbbBeginDate	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JComboBox();
			cbbBeginDate.setBounds(new Rectangle(157, 235, 110, 27));
		}
		return cbbBeginDate;
	}

	/**
	 * This method initializes cbbEndDate	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbEndDate() {
		if (cbbEndDate == null) {
			cbbEndDate = new JComboBox();
			cbbEndDate.setBounds(new Rectangle(288, 235, 110, 27));
		}
		return cbbEndDate;
	}

	/**
	 * This method initializes btnOK	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnOK() {
		if (btnOK == null) {
			btnOK = new JButton();
			btnOK.setBounds(new Rectangle(103, 280, 74, 26));
			btnOK.setText("确定");
		}
		return btnOK;
	}

	/**
	 * This method initializes btnCancel	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(new Rectangle(231, 280, 74, 26));
			btnCancel.setText("取消");
		}
		return btnCancel;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
