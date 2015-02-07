package com.bestway.common.client.fpt;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.custombase.entity.basecode.Brief;
import com.bestway.bcus.custombase.entity.countrycode.District;
import com.bestway.bcus.system.action.SystemAction;
import com.bestway.bcus.system.entity.Company;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgFptAppHeadByCompany extends JDialogBase {

	private JToolBar jToolBar = null; // @jve:decl-index=0:visual-constraint="58,126"

	private JButton btnEdit = null;

	private JButton btnCancel = null;

	private JPanel jPanel3 = null; // @jve:decl-index=0:visual-constraint="80,150"

	private JButton btnSave = null;

	private Company company = null; // @jve:decl-index=0:

	private int dataState = DataState.BROWSE;

	private SystemAction service = null;

	private JPanel pnIn = null;

	private JLabel jLabel573 = null;

	private JLabel jLabel58 = null;

	private JLabel jLabel510 = null;

	private JLabel jLabel511 = null;

	private JLabel jLabel512 = null;

	private JLabel jLabel513 = null;

	private JTextField tfInTradeCode2 = null;

	private JTextField tfInCorp = null;

	private JTextField tfInAgentCode = null;

	private JTextField tfInAgentName = null;

	private JTextField tfInDecl = null;

	private JTextField tfInTradeName2 = null;

	private JButton btnInTradeCode2 = null;

	private JComboBox cbbOutDistrict = null;

	private JLabel jLabel5121 = null;

	/**
	 * This method initializes
	 * 
	 */
	public DgFptAppHeadByCompany() {
		super();
		initialize();
		initUIComponents();
		service = (SystemAction) CommonVars.getApplicationContext().getBean(
				"systemAction");
		List list = service.findCompaniesCurr();
		if(list.size()>0){
			company = (Company)list.get(0);
			if(company == null) throw new RuntimeException("公司为空请设置默认公司！");
		}
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(523, 379));
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("转厂公司申请表默认设置");
		this.setContentPane(getJPanel3());

	}

	@Override
	public void setVisible(boolean is) {
		if (is) {
			showData();
			setState();
		}
		super.setVisible(is);
	}



	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.setSize(new java.awt.Dimension(47, 30));

			jToolBar.add(getJButton3());
			jToolBar.add(getJButton());
			jToolBar.add(getJButton8());

		}
		return jToolBar;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton3() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改");
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgFptAppHeadByCompany.this.dataState = DataState.EDIT;
					setState();
				}
			});

		}
		return btnEdit;
	}

	/**
	 * This method initializes jButton8
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton8() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText("\u5173\u95ed");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgFptAppHeadByCompany.this.dispose();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setSize(new java.awt.Dimension(83, 41));
			jPanel3.setLayout(new BorderLayout());
			jPanel3.add(getJToolBar(), BorderLayout.NORTH);
			jPanel3.add(getPnIn(), BorderLayout.CENTER);
		}
		return jPanel3;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("\u4fdd\u5b58");
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					fillData();
					DgFptAppHeadByCompany.this.service.saveCompany(new Request(
							CommonVars.getCurrUser()), company);
					DgFptAppHeadByCompany.this.dataState = DataState.BROWSE;
					setState();
				}
			});
		}
		return btnSave;
	}

	public void fillData() {

		this.company.setOutDistrict((District) this.cbbOutDistrict
				.getSelectedItem());
		this.company.setInAgentCode(this.tfInAgentCode.getText() == null ? ""
				: this.tfInAgentCode.getText());
		this.company.setInAgentName(this.tfInAgentName.getText() == null ? ""
				: this.tfInAgentName.getText());
		this.company.setInTradeCode2(this.tfInTradeCode2.getText() == null ? ""
				: this.tfInTradeCode2.getText());

		this.company.setInTradeName2(this.tfInTradeName2.getText() == null ? ""
				: this.tfInTradeName2.getText());
		this.company.setInCorp(this.tfInCorp.getText()== null ? "" : this.tfInCorp.getText());
		this.company.setInDecl(this.tfInDecl.getText() == null ? ""
				: this.tfInDecl.getText());

	}

	
	private void setState() {
		this.btnEdit.setEnabled(dataState != DataState.EDIT);
		this.btnSave.setEnabled(dataState != DataState.BROWSE);
		this.cbbOutDistrict.setEnabled(dataState != DataState.BROWSE);
		this.tfInAgentCode.setEditable(dataState != DataState.BROWSE);
		this.tfInAgentName.setEditable(dataState != DataState.BROWSE);
		this.tfInTradeCode2.setEditable(dataState != DataState.BROWSE);
		this.tfInTradeName2.setEditable(dataState != DataState.BROWSE);
		this.tfInCorp.setEditable(dataState != DataState.BROWSE);
		this.tfInDecl.setEditable(dataState != DataState.BROWSE);
	}
	
	
	private void showData() {
		if(null==company){
			company = new Company();
		}
		if(null!=company.getOutDistrict()){
		this.cbbOutDistrict
				.setSelectedItem(this.company.getOutDistrict());
		}
		this.tfInAgentCode.setText(this.company.getInAgentCode() == null ? ""
				: this.company.getInAgentCode());
		this.tfInAgentName.setText(this.company.getInAgentName() == null ? ""
				: this.company.getInAgentName());
		this.tfInTradeCode2.setText(this.company.getInTradeCode2() == null ? ""
				: this.company.getInTradeCode2());

		this.tfInTradeName2.setText(this.company.getInTradeName2() == null ? ""
				: this.company.getInTradeName2());
		this.tfInCorp.setText(this.company.getInCorp() == null ? ""
				: this.company.getInCorp());
		this.tfInDecl.setText(this.company.getInDecl() == null ? ""
				: this.company.getInDecl());

	}


	/**
	 * This method initializes pnIn
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnIn() {
		if (pnIn == null) {
			jLabel5121 = new JLabel();
			jLabel5121.setBounds(new Rectangle(26, 35, 141, 22));
			jLabel5121.setPreferredSize(new Dimension(163, 22));
			jLabel5121.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel5121.setText("转出地");
			jLabel5121.setForeground(Color.blue);
			jLabel513 = new JLabel();
			jLabel513.setBounds(new Rectangle(22, 88, 150, 22));
			jLabel513.setPreferredSize(new Dimension(163, 22));
			jLabel513.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel513
					.setText("申报企业组织机构名称");
			jLabel513.setForeground(Color.blue);
			jLabel512 = new JLabel();
			jLabel512.setBounds(new Rectangle(7, 63, 165, 22));
			jLabel512.setPreferredSize(new Dimension(163, 22));
			jLabel512.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel512
					.setText("申报企业9位组织机构代码");
			jLabel512.setForeground(Color.blue);
			jLabel511 = new JLabel();
			jLabel511.setBounds(new Rectangle(58, 186, 113, 22));
			jLabel511.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel511
					.setText("申报人/联系电话");
			jLabel511.setPreferredSize(new Dimension(163, 22));
			jLabel510 = new JLabel();
			jLabel510.setBounds(new Rectangle(50, 161, 123, 22));
			jLabel510.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel510
					.setText("企业法人/联系电话");
			jLabel510.setPreferredSize(new Dimension(163, 22));
			jLabel58 = new JLabel();
			jLabel58.setBounds(new Rectangle(74, 135, 99, 22));
			jLabel58.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel58
					.setText("申报企业名称");
			jLabel58.setPreferredSize(new Dimension(163, 22));
			jLabel573 = new JLabel();
			jLabel573.setBounds(new Rectangle(53, 111, 120, 22));
			jLabel573.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel573
					.setText("申报企业代码");
			jLabel573.setPreferredSize(new Dimension(163, 22));
			pnIn = new JPanel();
			pnIn.setLayout(null);
			pnIn.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			pnIn.add(jLabel573, null);
			pnIn.add(jLabel58, null);
			pnIn.add(jLabel510, null);
			pnIn.add(jLabel511, null);
			pnIn.add(jLabel512, null);
			pnIn.add(jLabel513, null);
			pnIn.add(getTfInTradeCode2(), null);
			pnIn.add(getTfInCorp(), null);
			pnIn.add(getTfInAgentCode(), null);
			pnIn.add(getTfInAgentName(), null);
			pnIn.add(getTfInDecl(), null);
			pnIn.add(getTfInTradeName2(), null);
			pnIn.add(getBtnInTradeCode2(), null);
			pnIn.add(getCbbOutDistrict(), null);
			pnIn.add(jLabel5121, null);
		}
		return pnIn;
	}

	/**
	 * This method initializes tfInTradeCode2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfInTradeCode2() {
		if (tfInTradeCode2 == null) {
			tfInTradeCode2 = new JTextField();
			tfInTradeCode2.setBounds(new Rectangle(173, 111, 109, 22));
		}
		return tfInTradeCode2;
	}

	/**
	 * This method initializes tfInCorp
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfInCorp() {
		if (tfInCorp == null) {
			tfInCorp = new JTextField();
			tfInCorp.setBounds(new Rectangle(173, 161, 241, 22));
		}
		return tfInCorp;
	}

	/**
	 * This method initializes tfInAgentCode
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfInAgentCode() {
		if (tfInAgentCode == null) {
			tfInAgentCode = new JTextField();
			tfInAgentCode.setBounds(new Rectangle(173, 63, 129, 22));
		}
		return tfInAgentCode;
	}

	/**
	 * This method initializes tfInAgentName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfInAgentName() {
		if (tfInAgentName == null) {
			tfInAgentName = new JTextField();
			tfInAgentName.setBounds(new Rectangle(173, 88, 241, 22));
		}
		return tfInAgentName;
	}

	/**
	 * This method initializes tfInDecl
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfInDecl() {
		if (tfInDecl == null) {
			tfInDecl = new JTextField();
			tfInDecl.setBounds(new Rectangle(173, 186, 241, 22));
		}
		return tfInDecl;
	}

	/**
	 * This method initializes tfInTradeName2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfInTradeName2() {
		if (tfInTradeName2 == null) {
			tfInTradeName2 = new JTextField();
			tfInTradeName2.setBounds(new Rectangle(173, 135, 242, 22));
		}
		return tfInTradeName2;
	}

	/**
	 * This method initializes btnInTradeCode2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnInTradeCode2() {
		if (btnInTradeCode2 == null) {
			btnInTradeCode2 = new JButton();
			btnInTradeCode2.setBounds(new Rectangle(281, 111, 20, 21));
			btnInTradeCode2.setText("...");
			btnInTradeCode2
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							Brief brief = (Brief) CommonQuery.getInstance()
									.getCustomBrief();
							if (brief != null) {
								tfInTradeCode2.setText(brief.getCode());
								tfInTradeName2.setText(brief.getName());
							}
						}
					});
		}
		return btnInTradeCode2;
	}

	/**
	 * This method initializes cbbOutDistrict
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbOutDistrict() {
		if (cbbOutDistrict == null) {
			cbbOutDistrict = new JComboBox();
			cbbOutDistrict.setBounds(new Rectangle(173, 35, 129, 22));
		}
		return cbbOutDistrict;
	}

	private void initUIComponents() {

		this.cbbOutDistrict.setModel(CustomBaseModel.getInstance()
				.getDistrictModelModel());
		initComboBoxRenderer(this.cbbOutDistrict);
	}

	/**
	 * 初始化Cbb呈现
	 * 
	 * @param cbb
	 */
	private void initComboBoxRenderer(JComboBox cbb) {
		cbb.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbb);
		cbb.setSelectedItem(null);
	}

} // @jve:decl-index=0:visual-constraint="10,10"
