package com.bestway.bcus.client.system;

import java.awt.BorderLayout;
import java.awt.Color;
import java.text.ParseException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.bestway.bcus.client.authority.FmLogin;
import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseComboBoxUI;
import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.custombase.entity.basecode.ApplicationMode;
import com.bestway.bcus.custombase.entity.basecode.Brief;
import com.bestway.bcus.custombase.entity.basecode.CoType;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.depcode.RedDep;
import com.bestway.bcus.system.action.SystemAction;
import com.bestway.bcus.system.entity.Company;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.windows.form.FmMain;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.CustomsUser;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Rectangle;
import java.awt.Dimension;

/**
 * @author dream
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class DgCompany extends JDialogBase {

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JPanel jPanel2 = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JPanel jPanel3 = null;

	private JComboBox jComboBox4 = null;

	private JComboBox jComboBox5 = null;

	private JTextField jTextField = null;

	private JComboBox jComboBox6 = null;

	private JComboBox jComboBox7 = null;

	private JTextField jTextField1 = null;

	private JPanel jPanel4 = null;

	private JTextField jTextField3 = null;

	private JTextField jTextField5 = null;

	private JTextField jTextField10 = null;

	private JTextField jTextField11 = null;

	private JPanel jPanel5 = null;

	private JScrollPane jScrollPane = null;

	private JTextArea jTextArea = null;

	private JScrollPane jScrollPane1 = null;

	private JTextArea jTextArea1 = null;

	private JTextField jTextField12 = null;

	private JTextField jTextField13 = null;

	private JScrollPane jScrollPane2 = null;

	private JTextField jTextField14 = null;

	private JTextArea jTextArea2 = null;

	private Company company = null; // 企业 // @jve:decl-index=0:

	private boolean isAdd = true;

	private JTextField jTextField15 = null;

	private JTextField jTextField16 = null;

	private JTextField jTextField2 = null;

	private JButton jButton2 = null;

	private JTextField jTextField17 = null;

	private JButton jButton3 = null;

	private SystemAction services = null;

	private Brief brief = null; // 海关编码 // @jve:decl-index=0:

	private Customs custom = null; // 海关关区

	private RedDep redDep = null;// 外经委

	// private ApplicationMode applicationMode = null;// 申报方式

	private JTableListModel tableModel = null;

	private JFormattedTextField jTextField8 = null;

	private DefaultFormatterFactory defaultFormatterFactory = null; // @jve:decl-index=0:parse

	private NumberFormatter numberFormatter = null; // @jve:decl-index=0:parse

	private JFormattedTextField jTextField4 = null;

	private JFormattedTextField jTextField9 = null;

	private JFormattedTextField jTextField6 = null;

	private JFormattedTextField jTextField7 = null;

	private JTextField jTextField18 = null;

	private JButton jButton4 = null;

	private JTextField jTextField19 = null;

	private JTextField jTextField20 = null;

	private JTextField jTextField21 = null;

	private JTextField jTextField22 = null;

	private JTextField jTextField23 = null;
	
	private JTextField jTextField60 = null;

	private JLabel jLabel26 = null;

	private JComboBox jTextField24 = null;

	private JLabel jLabel35 = null;

	private JTextField jTextField25 = null;

	private JLabel jLabel36 = null;

	private JTextField jTextField26 = null;

	private JLabel jLabel12 = null;

	private JLabel jLabel37 = null;

	private JTextField jTextField27 = null;

	private JLabel jLabel38 = null;

	private JTextField jTextField28 = null;

	private JLabel jLabel39 = null;

	private MaterialManageAction materialManageAction = null;

	private JLabel jLabel40 = null;

	private JTextField jTFRedDep = null;

	private JButton jBTRedDep = null;

	private JLabel jLabel41 = null;

	private JTextField tfOutFax = null;

	/**
	 * 
	 */
	public DgCompany() {
		super();
		initialize();
		services = (SystemAction) CommonVars.getApplicationContext().getBean(
				"systemAction");
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setContentPane(getJPanel());
		this.setTitle("公司资料维护");
		this.setHelpId("company");
		this.setSize(738, 616);
		jComboBox5.setModel(CustomBaseModel.getInstance().getCoTypeModel());
		jComboBox5.setRenderer(CustomBaseRender.getInstance().getRender());
		jComboBox7.setModel(CustomBaseModel.getInstance().getCoTypeModel());// 企业性质
		jComboBox7.setRenderer(CustomBaseRender.getInstance().getRender());
		this.addWindowListener(new java.awt.event.WindowAdapter() {

			public void windowOpened(java.awt.event.WindowEvent e) {

				if (isAdd == false) {
					if (tableModel.getCurrentRow() != null) {
						company = (Company) tableModel.getCurrentRow();
						custom = company.getMasterCustoms();
						redDep = company.getMasterFtc();
						// applicationMode = company.getApplicationMode();
					}
					fillWindow();
				} else {

					jTextField24.removeAllItems();
					List customsUserList = materialManageAction
							.findCustomsUser(new Request(CommonVars
									.getCurrUser()));
					for (int i = 0; i < customsUserList.size(); i++) {
						CustomsUser user = (CustomsUser) customsUserList.get(i);
						jTextField24.addItem(user.getName());
					}
					jTextField24.setUI(new CustomBaseComboBoxUI(100));

				}
			}
		});
	}

	/**
	 * 
	 * This method initializes jPanel
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.add(getJPanel1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel;
	}

	private void fillWindow() {
		if (company != null && isAdd == false) {
			// 初始基本信息

			// jComboBox5.setModel(CustomBaseModel.getInstance().getCoTypeModel());
			// jComboBox5.setRenderer(CustomBaseRender.getInstance().getRender());
			// CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
			// this.jComboBox5);

			// jComboBox7.setModel(CustomBaseModel.getInstance().getCoTypeModel());//
			// 企业性质
			// jComboBox7.setRenderer(CustomBaseRender.getInstance().getRender());
			// CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
			// this.jComboBox7);
			CoType coType = null;

			if (company.getBuOwnerType() != null)
				coType = findCoTypeByCode(company.getBuOwnerType().getCode()
						.toString());

			getJComboBox7().setSelectedItem(coType);
			CoType coType1 = null;

			if (company.getOwnerType() != null)
				coType1 = findCoTypeByCode(company.getOwnerType().getCode()
						.toString());

			getJComboBox5().setSelectedItem(coType1);

			jTextField24.removeAll();
			List customsUserList = materialManageAction
					.findCustomsUser(new Request(CommonVars.getCurrUser()));
			for (int i = 0; i < customsUserList.size(); i++) {
				CustomsUser user = (CustomsUser) customsUserList.get(i);
				jTextField24.addItem(user.getName());
			}
			jTextField24.setUI(new CustomBaseComboBoxUI(100));

			jTextField16.setText(company.getBuName());
			jTextField17.setText(company.getBuCode());
			if (this.custom != null) // 主管海关
			{
				this.jTextField18.setText(custom.getName());
			} else {
				this.jTextField18.setText("");
			}
			if (this.redDep != null) // 外经委
			{
				this.jTFRedDep.setText(redDep.getName());
			} else {
				this.jTFRedDep.setText("");
			}
			// if(this.applicationMode!=null){
			// this.tfApplicationMode.setText(this.applicationMode.getName());
			// }else{
			// this.tfApplicationMode.setText("");
			// }
			jComboBox6.setSelectedItem(company.getBuLevel());
			jTextField1.setText(company.getBuOwner());

			jTextField2.setText(company.getCode());
			jTextField15.setText(company.getName());
			jComboBox4.setSelectedItem(company.getCoLevel());
			jTextField.setText(company.getOwner());
			jTextField3.setText(company.getAddress());
			jTextField4.setText(doubleToStr(company.getArea()));
			jTextField5.setText(company.getTel());
			jTextField8.setText(doubleToStr(company.getInverst()));
			jTextField6.setText(doubleToStr(company.getAmountIn()));
			jTextField7.setText(doubleToStr(company.getAmountOut()));
			jTextField9.setText(doubleToStr(company.getAmountProduct()));

			jTextField10.setText(company.getBank());
			jTextField11.setText(company.getAccount());
			jTextArea.setText(company.getInArea());
			jTextArea1.setText(company.getOutArea());
			jTextField12.setText(company.getSysName());
			jTextField13.setText(company.getSysOwner());
			jTextField14.setText(company.getCounFlag());
			jTextField19.setText(company.getOwnercounFlag());
			jTextArea2.setText(company.getSysFunc());
			jTextField20.setText(company.getOutTradeCo());
			jTextField21.setText(company.getOutCoTel());
			jTextField22.setText(company.getOutAddress());
			jTextField23.setText(company.getOutLinkMan());
			jTextField60.setText(company.getOutLinkManager());
			// jTextField24.setText(company.getAppCusMan());
			jTextField25.setText(company.getAppCusManTel());
			tfOutFax.setText(company.getOutFax());
			jTextField26.setText(company.getLinkman());
			jTextField27.setText(company.getBuaddress());
			jTextField28.setText(company.getButel());
		}
	}

	/**
	 * 
	 * This method initializes jPanel1
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.add(getJPanel3(), null);
			jPanel1.add(getJPanel4(), null);
			jPanel1.add(getJPanel5(), null);
			jPanel1.add(getJPanel2(), null);
		}
		return jPanel1;
	}

	/**
	 * 
	 * This method initializes jPanel2
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jLabel39 = new JLabel();
			jLabel39.setText("注：蓝色为必填；红色为联网监管必填。");
			jLabel39.setBounds(new java.awt.Rectangle(6, 10, 216, 18));
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(
					java.awt.SystemColor.desktop, 1));
			jPanel2.setBounds(3, 542, 713, 34);
			jPanel2.add(jLabel39, null);
			jPanel2.add(getJButton(), null);
			jPanel2.add(getJButton1(), null);
		}
		return jPanel2;
	}

	/**
	 * 
	 * This method initializes jButton
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("确定");
			jButton.setBounds(new java.awt.Rectangle(250, 3, 58, 28));
			jButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (checkNull()) {
						return;
					}
					if (isAdd == true) {
						Company company = new Company();
						company.setIsEnable(true);
						fillData(company);
						company = services.saveCompany(new Request(CommonVars
								.getCurrUser()), company);
						tableModel.addRow(company);
						clearData();
					} else {
						fillData(company);
						company = services.saveCompany(new Request(CommonVars
								.getCurrUser()), company);
						tableModel.updateRow(company);
						DgCompany.this.dispose();
						if (CommonVars.getCurrUser().getCompany().equals(
								company)) {
							if (!CommonVars.getCurrUser().getCompany()
									.getName().equals(company.getName())) {
								JOptionPane.showMessageDialog(DgCompany.this,
										"由于当前登录公司的名称已改变，请重新登录！");
								FmMain.getInstance().dispose();
								FmLogin fmLogin = new FmLogin();
								fmLogin.setVisible(true);
								return;
							}
							CommonVars.getCurrUser().setCompany(company);
						}
					}
				}
			});
		}
		return jButton;
	}

	/**
	 * 
	 * This method initializes jButton1
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("取消");
			jButton1.setBounds(new java.awt.Rectangle(389, 3, 58, 28));
			jButton1.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgCompany.this.dispose();
				}
			});

		}
		return jButton1;
	}

	/**
	 * 
	 * This method initializes jPanel3
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jLabel40 = new JLabel();
			jLabel40.setBounds(new java.awt.Rectangle(13, 153, 50, 23));
			jLabel40.setText("外经贸部");
			jLabel40.setForeground(Color.BLUE);
			jLabel38 = new JLabel();
			jLabel37 = new JLabel();
			jLabel36 = new JLabel();
			javax.swing.JLabel jLabel29 = new JLabel();

			javax.swing.JLabel jLabel28 = new JLabel();

			javax.swing.JLabel jLabel9 = new JLabel();

			javax.swing.JLabel jLabel8 = new JLabel();

			javax.swing.JLabel jLabel7 = new JLabel();

			javax.swing.JLabel jLabel6 = new JLabel();

			javax.swing.JLabel jLabel5 = new JLabel();

			javax.swing.JLabel jLabel4 = new JLabel();

			javax.swing.JLabel jLabel3 = new JLabel();

			javax.swing.JLabel jLabel2 = new JLabel();

			javax.swing.JLabel jLabel1 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();

			jPanel3 = new JPanel();
			jPanel3.setLayout(null);
			jPanel3.setBounds(6, 12, 713, 184);
			jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(
					javax.swing.BorderFactory.createLineBorder(
							new java.awt.Color(102, 153, 255), 1), "公司基本信息",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			jLabel.setBounds(246, 22, 56, 18);
			jLabel.setText("公司名称");
			jLabel.setForeground(Color.BLUE);
			jLabel1.setBounds(246, 103, 74, 20);
			jLabel1.setText("经营单位名称");
			jLabel1.setForeground(Color.BLUE);
			jLabel2.setBounds(12, 21, 97, 19);
			jLabel2.setText("加工单位海关编码");
			jLabel2.setForeground(Color.BLUE);
			jLabel3.setBounds(12, 104, 96, 22);
			jLabel3.setText("经营单位海关编码");
			jLabel3.setForeground(Color.BLUE);
			jLabel4.setBounds(246, 47, 104, 21);
			jLabel4.setText("加工单位管理类别");
			jLabel4.setForeground(Color.BLUE);
			jLabel5.setBounds(12, 48, 101, 19);
			jLabel5.setText("加工单位企业性质");
			jLabel5.setForeground(Color.BLUE);
			jLabel6.setBounds(476, 18, 97, 22);
			jLabel6.setText("加工单位法人代表");
			jLabel6.setForeground(Color.BLUE);
			jLabel7.setBounds(246, 132, 101, 19);
			jLabel7.setText("经营单位管理类别");
			jLabel7.setForeground(Color.BLUE);
			jLabel8.setBounds(12, 131, 98, 18);
			jLabel8.setText("经营单位企业性质");
			jLabel8.setForeground(Color.BLUE);
			jLabel9.setBounds(476, 106, 96, 21);
			jLabel9.setText("经营单位法人代表");
			jLabel9.setForeground(Color.BLUE);
			jLabel28.setBounds(423, 47, 76, 20);
			jLabel28.setText("加工单位地址");
			jLabel28.setForeground(Color.BLUE);
			jLabel29.setBounds(477, 132, 50, 21);
			jLabel29.setText("主管海关");
			jLabel29.setForeground(Color.BLUE);
			jLabel36.setBounds(12, 72, 97, 25);
			jLabel36.setText("加工单位联系人");
			jLabel36.setForeground(Color.BLUE);
			jLabel37.setBounds(246, 74, 75, 22);
			jLabel37.setText("经营单位地址");
			jLabel37.setForeground(Color.BLUE);
			jLabel38.setBounds(476, 74, 96, 25);
			jLabel38.setText("经营单位联系电话");
			jPanel3.add(jLabel, null);
			jPanel3.add(jLabel1, null);
			jPanel3.add(jLabel2, null);
			jPanel3.add(jLabel3, null);
			jPanel3.add(jLabel4, null);
			jPanel3.add(getJComboBox4(), null);
			jPanel3.add(jLabel5, null);
			jPanel3.add(getJComboBox5(), null);
			jPanel3.add(jLabel6, null);
			jPanel3.add(getJTextField(), null);
			jPanel3.add(jLabel7, null);
			jPanel3.add(getJComboBox6(), null);
			jPanel3.add(jLabel8, null);
			jPanel3.add(getJComboBox7(), null);
			jPanel3.add(jLabel9, null);
			jPanel3.add(getJTextField1(), null);
			jPanel3.add(getJTextField15(), null);
			jPanel3.add(getJTextField16(), null);
			jPanel3.add(getJTextField3(), null);
			jPanel3.add(jLabel28, null);
			jPanel3.add(getJTextField2(), null);
			jPanel3.add(getJButton2(), null);
			jPanel3.add(getJTextField17(), null);
			jPanel3.add(getJButton3(), null);
			jPanel3.add(jLabel29, null);
			jPanel3.add(getJTextField18(), null);
			jPanel3.add(getJButton4(), null);
			jPanel3.add(jLabel36, null);
			jPanel3.add(getJTextField26(), null);
			jPanel3.add(jLabel37, null);
			jPanel3.add(getJTextField27(), null);
			jPanel3.add(jLabel38, null);
			jPanel3.add(getJTextField28(), null);
			jPanel3.add(jLabel40, null);
			jPanel3.add(getJTFRedDep(), null);
			jPanel3.add(getJBTRedDep(), null);
		}
		return jPanel3;
	}

	/**
	 * 
	 * This method initializes jComboBox4
	 * 
	 * 
	 * 
	 * @return javax.swing.JComboBox
	 * 
	 */
	private JComboBox getJComboBox4() {
		if (jComboBox4 == null) {
			jComboBox4 = new JComboBox(new String[] { "AA类", "A类", "B类", "C类", "D类" });
			jComboBox4.setBounds(351, 48, 69, 23);
		}
		return jComboBox4;
	}

	/**
	 * 
	 * This method initializes jComboBox5
	 * 
	 * 
	 * 
	 * @return javax.swing.JComboBox
	 * 
	 */
	private JComboBox getJComboBox5() {
		if (jComboBox5 == null) {
			jComboBox5 = new JComboBox();
			jComboBox5.setBounds(115, 48, 115,23);
		}
		return jComboBox5;
	}

	/**
	 * 
	 * This method initializes jTextField
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(575, 19, 125, 22);
		}
		return jTextField;
	}

	/**
	 * 
	 * This method initializes jComboBox6
	 * 
	 * 
	 * 
	 * @return javax.swing.JComboBox
	 * 
	 */
	private JComboBox getJComboBox6() {
		if (jComboBox6 == null) {
			jComboBox6 = new JComboBox(new String[] { "A类", "B类", "C类", "D类" });
			jComboBox6.setBounds(346, 130, 120, 22);
		}
		return jComboBox6;
	}

	/**
	 * 
	 * This method initializes jComboBox7
	 * 
	 * 
	 * 
	 * @return javax.swing.JComboBox
	 * 
	 */
	private JComboBox getJComboBox7() {
		if (jComboBox7 == null) {
			jComboBox7 = new JComboBox();
			jComboBox7.setBounds(115, 131, 115, 23);
		}
		return jComboBox7;
	}

	/**
	 * 
	 * This method initializes jTextField1
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			jTextField1.setBounds(575, 103, 124, 22);
		}
		return jTextField1;
	}

	/**
	 * 
	 * This method initializes jPanel4
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jLabel12 = new JLabel();
			javax.swing.JLabel jLabel18 = new JLabel();

			javax.swing.JLabel jLabel17 = new JLabel();

			javax.swing.JLabel jLabel16 = new JLabel();

			javax.swing.JLabel jLabel15 = new JLabel();

			javax.swing.JLabel jLabel14 = new JLabel();

			javax.swing.JLabel jLabel13 = new JLabel();

			javax.swing.JLabel jLabel11 = new JLabel();

			javax.swing.JLabel jLabel10 = new JLabel();
			jPanel4 = new JPanel();
			jPanel4.setLayout(null);
			jPanel4.setBounds(6, 201, 713, 99);
			jLabel11.setBounds(247, 18, 99, 20);
			jLabel11.setText("工厂面积(平方米)");
			jLabel13.setBounds(14, 73, 123, 19);
			jLabel13.setText("上年进口总值(美元)");
			jLabel14.setBounds(14, 19, 135, 19);
			jLabel14.setText("加工企业投资总额(美元)");
			jLabel14.setForeground(Color.RED);
			jLabel15.setBounds(364, 72, 113, 20);
			jLabel15.setText("上年出口总值(美元)");
			jPanel4.add(jLabel11, null);
			jPanel4.add(jLabel13, null);
			jPanel4.add(jLabel14, null);
			jPanel4.add(jLabel15, null);
			jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(
					javax.swing.BorderFactory.createLineBorder(
							new java.awt.Color(102, 153, 255), 1), "加工贸易信息",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			jLabel10.setText("加工企业地址");
			jLabel10.setBounds(417, 48, 78, 18);
			jLabel16.setBounds(14, 46, 121, 18);
			jLabel16.setText("年加工生产能力(美元)");
			jLabel16.setForeground(Color.RED);
			jLabel17.setBounds(247, 48, 55, 20);
			jLabel17.setText("开户银行");
			jLabel18.setBounds(477, 44, 55, 21);
			jLabel18.setText("帐号");
			jLabel12.setBounds(477, 17, 59, 20);
			jLabel12.setText("联系电话");
			jPanel4.add(jLabel16, null);
			jPanel4.add(jLabel17, null);
			jPanel4.add(getJTextField10(), null);
			jPanel4.add(jLabel18, null);
			jPanel4.add(getJTextField11(), null);

			jPanel4.add(getJTextField8(), null);
			jPanel4.add(getJTextField4(), null);
			jPanel4.add(getJTextField9(), null);
			jPanel4.add(getJTextField6(), null);
			jPanel4.add(getJTextField7(), null);
			jPanel4.add(getJTextField5(), null);
			jPanel4.add(jLabel12, null);
			jPanel4.add(getJTextField7(), null);
		}
		return jPanel4;
	}

	/**
	 * 
	 * This method initializes jTextField3
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField3() {
		if (jTextField3 == null) {
			jTextField3 = new JTextField();
			jTextField3.setBounds(503, 48, 196, 22);
		}
		return jTextField3;
	}

	/**
	 * 
	 * This method initializes jTextField5
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField5() {
		if (jTextField5 == null) {
			jTextField5 = new JTextField();
			jTextField5.setBounds(538, 17, 160, 22);
		}
		return jTextField5;
	}

	/**
	 * 
	 * This method initializes jTextField10
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField10() {
		if (jTextField10 == null) {
			jTextField10 = new JTextField();
			jTextField10.setBounds(324, 45, 144, 22);
		}
		return jTextField10;
	}

	/**
	 * 
	 * This method initializes jTextField11
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField11() {
		if (jTextField11 == null) {
			jTextField11 = new JTextField();
			jTextField11.setBounds(536, 45, 163, 22);
		}
		return jTextField11;
	}

	/**
	 * 
	 * This method initializes jPanel5
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel5() {
		if (jPanel5 == null) {
			jLabel41 = new JLabel();
			jLabel41.setBounds(new Rectangle(273, 90, 83, 23));
			jLabel41.setText("外商公司传真");
			jLabel35 = new JLabel();
			jLabel26 = new JLabel();
			javax.swing.JLabel jLabel34 = new JLabel();
			
			javax.swing.JLabel jLabel60 = new JLabel();
			
			javax.swing.JLabel jLabel33 = new JLabel();

			javax.swing.JLabel jLabel32 = new JLabel();

			javax.swing.JLabel jLabel31 = new JLabel();

			javax.swing.JLabel jLabel30 = new JLabel();

			javax.swing.JLabel jLabel27 = new JLabel();

			javax.swing.JLabel jLabel25 = new JLabel();

			javax.swing.JLabel jLabel24 = new JLabel();

			javax.swing.JLabel jLabel23 = new JLabel();

			javax.swing.JLabel jLabel22 = new JLabel();

			javax.swing.JLabel jLabel21 = new JLabel();

			jPanel5 = new JPanel();
			javax.swing.JLabel jLabel19 = new JLabel();

			javax.swing.JLabel jLabel20 = new JLabel();

			jPanel5.setLayout(null);
			jPanel5.setBounds(6, 303, 713, 225);
			jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(
					javax.swing.BorderFactory.createLineBorder(
							new java.awt.Color(102, 153, 255), 1), "其他信息",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			jLabel19.setBounds(11, 20, 80, 22);
			jLabel19.setText("进口料件范围");
			jLabel20.setBounds(11, 40, 61, 17);
			jLabel20.setText("(文字描述)");
			jLabel21.setBounds(363, 20, 79, 18);
			jLabel21.setText("出口成品范围");
			jLabel22.setBounds(363, 36, 80, 18);
			jLabel22.setText("(文字描述)");
			jLabel23.setText("信息管理系统");
			jLabel23.setBounds(10, 139, 81, 23);
			jLabel24.setText("系统开发商");
			jLabel24.setBounds(363, 139, 67, 23);
			jLabel25.setText("系统功能");
			jLabel25.setBounds(10, 189, 64, 23);
			jLabel27.setText("经营单位国标代码");
			jLabel27.setBounds(363, 164, 97, 23);
			jLabel30.setText("加工单位国标代码");
			jLabel30.setBounds(10, 164, 102, 23);
			jLabel31.setBounds(10, 66, 80, 23);
			jLabel31.setText("外商公司");
			jLabel32.setBounds(273, 66, 80, 23);
			jLabel32.setText("外商公司电话");
			jLabel33.setBounds(10, 90, 80, 23);
			jLabel33.setText("外商公司地址");
			jLabel34.setBounds(499, 66, 88, 23);
			jLabel34.setText("外商公司联系人");
			jLabel60.setBounds(499, 90, 88, 23);
			jLabel60.setText("外商公司经理人");
			jLabel26.setBounds(10, 114, 68, 23);
			jLabel26.setText("报关员");
			jLabel35.setBounds(363, 114, 89, 23);
			jLabel35.setText("报关员联系电话");
			jPanel5.add(jLabel19, null);
			jPanel5.add(jLabel20, null);
			jPanel5.add(getJScrollPane(), null);
			jPanel5.add(jLabel21, null);
			jPanel5.add(jLabel22, null);
			jPanel5.add(getJScrollPane1(), null);
			jPanel5.add(getJScrollPane2(), null);
			jPanel5.add(jLabel31, null);
			jPanel5.add(getJTextField20(), null);
			jPanel5.add(jLabel32, null);
			jPanel5.add(getJTextField21(), null);
			jPanel5.add(jLabel33, null);
			jPanel5.add(getJTextField22(), null);
			jPanel5.add(jLabel34, null);
			jPanel5.add(getJTextField23(), null);
			jPanel5.add(jLabel26, null);
			jPanel5.add(getJTextField24(), null);
			jPanel5.add(jLabel35, null);
			jPanel5.add(getJTextField25(), null);
			jPanel5.add(jLabel23, null);
			jPanel5.add(getJTextField12(), null);
			jPanel5.add(jLabel24, null);
			jPanel5.add(getJTextField13(), null);
			jPanel5.add(jLabel30, null);
			jPanel5.add(getJTextField19(), null);
			jPanel5.add(jLabel27, null);
			jPanel5.add(getJTextField14(), null);
			jPanel5.add(jLabel25, null);
			jPanel5.add(jLabel41, null);
			jPanel5.add(getTfOutFax(), null);
			jPanel5.add(jLabel60, null);
			jPanel5.add(getJTextField60(), null);
		}
		return jPanel5;
	}

	/**
	 * 
	 * This method initializes jScrollPane
	 * 
	 * 
	 * 
	 * @return javax.swing.JScrollPane
	 * 
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setBounds(112, 12, 231, 49);
			jScrollPane.setViewportView(getJTextArea());
		}
		return jScrollPane;
	}

	/**
	 * 
	 * This method initializes jTextArea
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextArea
	 * 
	 */
	private JTextArea getJTextArea() {
		if (jTextArea == null) {
			jTextArea = new JTextArea();
		}
		return jTextArea;
	}

	/**
	 * 
	 * This method initializes jScrollPane1
	 * 
	 * 
	 * 
	 * @return javax.swing.JScrollPane
	 * 
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setBounds(464, 12, 235, 49);
			jScrollPane1.setViewportView(getJTextArea1());
		}
		return jScrollPane1;
	}

	/**
	 * 
	 * This method initializes jTextArea1
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextArea
	 * 
	 */
	private JTextArea getJTextArea1() {
		if (jTextArea1 == null) {
			jTextArea1 = new JTextArea();
		}
		return jTextArea1;
	}

	/**
	 * 
	 * This method initializes jTextField12
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField12() {
		if (jTextField12 == null) {
			jTextField12 = new JTextField();
			jTextField12.setBounds(111, 139, 231, 23);
		}
		return jTextField12;
	}

	/**
	 * 
	 * This method initializes jTextField13
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField13() {
		if (jTextField13 == null) {
			jTextField13 = new JTextField();
			jTextField13.setBounds(462, 139, 236, 23);
		}
		return jTextField13;
	}

	/**
	 * 
	 * This method initializes jScrollPane2
	 * 
	 * 
	 * 
	 * @return javax.swing.JScrollPane
	 * 
	 */
	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setBounds(112, 189, 588, 25);
			jScrollPane2.setViewportView(getJTextArea2());
		}
		return jScrollPane2;
	}

	/**
	 * 
	 * This method initializes jTextField14
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField14() {
		if (jTextField14 == null) {
			jTextField14 = new JTextField();
			jTextField14.setBounds(462, 164, 235, 23);
		}
		return jTextField14;
	}

	private JTextArea getJTextArea2() {
		if (jTextArea2 == null) {
			jTextArea2 = new JTextArea();
		}
		return jTextArea2;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Company getCompany() {
		return company;
	}

	private void clearData() {
		getJComboBox7().setSelectedItem("");
		getJComboBox5().setSelectedItem("");

		jTextField16.setText("");
		jTextField17.setText("");
		jComboBox6.setSelectedItem("");
		jComboBox7.setSelectedItem("");
		jTextField1.setText("");
		jTextField15.setText("");
		jTextField2.setText("");
		jComboBox4.setSelectedItem("");
		jTextField.setText("");
		jComboBox5.setSelectedItem("");
		jTextField3.setText("");
		jTextField4.setText("");
		jTextField5.setText("");
		jTextField8.setText("");
		jTextField6.setText("");
		jTextField7.setText("");
		jTextField9.setText("");
		jTextField10.setText("");
		jTextField11.setText("");
		jTextArea.setText("");
		jTextArea1.setText("");
		jTextField12.setText("");
		jTextField14.setText("");
		jTextArea2.setText("");
		jTextField18.setText("");
		jTFRedDep.setText("");
	}

	private boolean checkNull() { // 检查是否为空
		if (jTextField15.getText() == null || jTextField15.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "公司名称不能为空！", "提示！", 0);
			return true;
		}
		if (jTextField2.getText() == null || jTextField2.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "加工单位海关编码不能为空！", "提示！", 0);
			return true;
		}
		if (jTextField.getText() == null || jTextField.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "加工单位法人代表不能为空！", "提示！", 0);
			return true;
		}
		if (jComboBox5.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(this, "加工单位企业性质不能为空！", "提示！", 0);
			return true;
		}
		if (jComboBox4.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(this, "加工单位管理类别不能为空！", "提示！", 0);
			return true;
		}
		if (jTextField3.getText() == null || jTextField3.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "加工单位地址不能为空！", "提示！", 0);
			return true;
		}
		if (jTextField26.getText() == null || jTextField26.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "加工单位联系人不能为空！", "提示！", 0);
			return true;
		}
		if (jTextField27.getText() == null || jTextField27.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "经营单位地址不能为空！", "提示！", 0);
			return true;
		}
		if (jTextField17.getText() == null || jTextField17.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "经营单位海关编码不能为空！", "提示！", 0);
			return true;
		}
		if (jTextField16.getText() == null || jTextField16.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "经营单位名称不能为空！", "提示！", 0);
			return true;
		}
		if (jTextField1.getText() == null || jTextField1.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "经营单位法人代表不能为空！", "提示！", 0);
			return true;
		}
		if (jComboBox7.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(this, "经营单位企业性质不能为空！", "提示！", 0);
			return true;
		}
		if (jComboBox6.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(this, "经营单位管理类别不能为空！", "提示！", 0);
			return true;
		}
		if (jTextField18.getText() == null || jTextField18.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "主管海关不能为空！", "提示！", 0);
			return true;
		}
		if (jTFRedDep.getText() == null || jTFRedDep.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "外经贸部不能为空！", "提示！", 0);
			return true;
		}
		
		return false;
	}

	private Double strToDouble(String value) { // 转换strToDouble 填充数据
		try {
			if (value == null || "".equals(value)) {
				return null;
			}
			getNumberFormatter().setValueClass(Double.class);
			return (Double) getNumberFormatter().stringToValue(value);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	private String doubleToStr(Double value) { // 转换doubleToStr 取数据
		try {
			if (value == null || value.doubleValue() == 0) {
				return null;
			}
			getNumberFormatter().setValueClass(Double.class);
			return getNumberFormatter().valueToString(value);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void fillData(Company company) { // 保存
		// 初始基本信息

		company.setBuName(jTextField16.getText()); // 经营单位名称
		company.setBuCode(jTextField17.getText()); // 经营单位代码
		if (jComboBox6.getSelectedItem() != null)
			company.setBuLevel(jComboBox6.getSelectedItem().toString()); // 经营单位管理类别
		company.setBuOwnerType((CoType) jComboBox7.getSelectedItem());// 经营单位企业性质
		company.setBuOwner(jTextField1.getText()); // 经营单位法人代表
		company.setBuaddress(jTextField27.getText()); // 经营单位联系地址
		company.setButel(jTextField28.getText()); // 经营单位联系电话

		company.setName(jTextField15.getText()); // 公司名称
		company.setCode(jTextField2.getText()); // 加工单位海关编码
		if (jComboBox4.getSelectedItem() != null)
			company.setCoLevel(jComboBox4.getSelectedItem().toString()); // 加工单位管理类别
		if (jTextField24.getSelectedItem() != null)
			company.setAppCusMan(jTextField24.getSelectedItem().toString());// 报关员名称
		company.setOwner(jTextField.getText()); // 加工单位法人代表
		company.setOwnerType((CoType) jComboBox5.getSelectedItem()); // 加工单位企业性质
		company.setAddress(jTextField3.getText()); // 加工单位地址
		company.setMasterCustoms(DgCompany.this.custom); // 主管海关
		company.setMasterFtc(DgCompany.this.redDep);
		// company.setApplicationMode(this.applicationMode);

		// 初始加工贸易信息
		company.setArea(strToDouble(jTextField4.getText())); // 工厂面积

		company.setTel(jTextField5.getText()); // 联系电话
		company.setLinkman(jTextField26.getText()); // 联系人

		company.setInverst(strToDouble(jTextField8.getText())); // 投资总额

		company.setAmountIn(strToDouble(jTextField6.getText())); // 上年加工贸易进口总值(美元)

		company.setAmountOut(strToDouble(jTextField7.getText())); // 上年加工贸易出口总值(美元)

		company.setAmountProduct(strToDouble(jTextField9.getText())); // 年加工生产能力(美元)

		company.setBank(jTextField10.getText()); // 开户银行
		company.setAccount(jTextField11.getText()); // 帐号

		company.setInArea(jTextArea.getText()); // 进口料件范围
		company.setOutArea(jTextArea1.getText()); // 出口料件范围
		company.setSysName(jTextField12.getText()); // 系统名称
		company.setSysOwner(jTextField13.getText()); // 开发商
		company.setCounFlag(jTextField14.getText()); // 国标代码
		company.setSysFunc(jTextArea2.getText()); // 功能描述
		company.setOwnercounFlag(jTextField19.getText());// 加工单位国标代码
		company.setOutTradeCo(jTextField20.getText());
		company.setOutCoTel(jTextField21.getText());
		company.setOutAddress(jTextField22.getText());
		company.setOutLinkMan(jTextField23.getText());
		company.setOutLinkManager(jTextField60.getText());
		// company.setAppCusMan(jTextField24.getText());
		company.setAppCusManTel(jTextField25.getText());
		company.setOutFax(tfOutFax.getText());
	}

	/**
	 * 
	 * This method initializes jTextField15
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField15() {
		if (jTextField15 == null) {
			jTextField15 = new JTextField();
			jTextField15.setBounds(319, 19, 147, 22);
			// jTextField15.setEditable(false);
		}
		return jTextField15;
	}

	/**
	 * 
	 * This method initializes jTextField16
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField16() {
		if (jTextField16 == null) {
			jTextField16 = new JTextField();
			jTextField16.setBounds(319, 103, 147, 22);
			// jTextField16.setEditable(false);
		}
		return jTextField16;
	}

	/**
	 * 
	 * This method initializes jTextField2
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField2() {
		if (jTextField2 == null) {
			jTextField2 = new JTextField();
			jTextField2.setBounds(114, 19, 97, 22);
			jTextField2.setEditable(false);
		}
		return jTextField2;
	}

	/**
	 * 
	 * This method initializes jButton2
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setBounds(209, 19, 21, 21);
			jButton2.setText("...");
			jButton2.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					Brief brief = (Brief) CommonQuery.getInstance()
							.getCustomBrief(DgCompany.this.brief);
					if (brief != null) {
						DgCompany.this.brief = brief;
						getJTextField2().setText(brief.getCode());
						getJTextField15().setText(brief.getName());
						String coCode = brief.getCode().substring(5, 6);

						CoType coType = findCoTypeByCode(coCode);
						getJComboBox5().setSelectedItem(coType);
					}

				}
			});

		}
		return jButton2;
	}

	private CoType findCoTypeByCode(String code) { // 查找
		for (int i = 0; i < CustomBaseList.getInstance().getCoTypes().size(); i++) {
			if (((CoType) CustomBaseList.getInstance().getCoTypes().get(i))
					.getCode().equals(code))
				return (CoType) CustomBaseList.getInstance().getCoTypes()
						.get(i);
		}
		return null;
	}

	/**
	 * 
	 * This method initializes jTextField17
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField17() {
		if (jTextField17 == null) {
			jTextField17 = new JTextField();
			jTextField17.setBounds(115, 103, 96, 22);
			jTextField17.setEditable(false);
		}
		return jTextField17;
	}

	/**
	 * 
	 * This method initializes jButton3
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setBounds(210, 103, 20, 21);
			jButton3.setText("...");
			jButton3.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					Brief brief = (Brief) CommonQuery.getInstance()
							.getCustomBrief(DgCompany.this.brief);
					if (brief != null) {
						DgCompany.this.brief = brief;
						getJTextField17().setText(brief.getCode());
						getJTextField16().setText(brief.getName());
						String coCode = brief.getCode().substring(5, 6);

						CoType coType = findCoTypeByCode(coCode);
						getJComboBox7().setSelectedItem(coType);
					}

				}
			});

		}
		return jButton3;
	}

	/**
	 * @return Returns the brief.
	 */
	public Brief getBrief() {
		return brief;
	}

	/**
	 * @param brief
	 *            The brief to set.
	 */
	public void setBrief(Brief brief) {
		this.brief = brief;
	}

	/**
	 * @return Returns the tableModel.
	 */
	public JTableListModel getTableModel() {
		return tableModel;
	}

	/**
	 * @param tableModel
	 *            The tableModel to set.
	 */
	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
	}

	/**
	 * @return Returns the isAdd.
	 */
	public boolean isAdd() {
		return isAdd;
	}

	/**
	 * @param isAdd
	 *            The isAdd to set.
	 */
	public void setAdd(boolean isAdd) {
		this.isAdd = isAdd;
	}

	/**
	 * 
	 * This method initializes jTextField8
	 * 
	 * 
	 * 
	 * @return javax.swing.JFormattedTextField
	 * 
	 */
	private JFormattedTextField getJTextField8() {
		if (jTextField8 == null) {
			jTextField8 = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jTextField8.setBounds(150, 17, 82, 22);
			jTextField8.setFormatterFactory(getDefaultFormatterFactory());
		}
		return jTextField8;
	}

	/**
	 * 
	 * This method initializes defaultFormatterFactory
	 * 
	 * 
	 * 
	 * @return javax.swing.text.DefaultFormatterFactory
	 * 
	 */
	private DefaultFormatterFactory getDefaultFormatterFactory() {
		if (defaultFormatterFactory == null) {
			defaultFormatterFactory = new DefaultFormatterFactory();
			defaultFormatterFactory.setDefaultFormatter(getNumberFormatter());
			defaultFormatterFactory.setDisplayFormatter(getNumberFormatter());
			defaultFormatterFactory.setEditFormatter(getNumberFormatter());
		}
		return defaultFormatterFactory;
	}

	/**
	 * 
	 * This method initializes numberFormatter
	 * 
	 * 
	 * 
	 * @return javax.swing.text.NumberFormatter
	 * 
	 */
	private NumberFormatter getNumberFormatter() {
		if (numberFormatter == null) {
			numberFormatter = new NumberFormatter();
			numberFormatter.setCommitsOnValidEdit(true);
		}
		return numberFormatter;
	}

	/**
	 * 
	 * This method initializes jTextField4
	 * 
	 * 
	 * 
	 * @return javax.swing.JFormattedTextField
	 * 
	 */
	private JFormattedTextField getJTextField4() {
		if (jTextField4 == null) {
			jTextField4 = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jTextField4.setBounds(352, 17, 115, 22);
			jTextField4.setFormatterFactory(getDefaultFormatterFactory());
		}
		return jTextField4;
	}

	/**
	 * 
	 * This method initializes jTextField9
	 * 
	 * 
	 * 
	 * @return javax.swing.JFormattedTextField
	 * 
	 */
	private JFormattedTextField getJTextField9() {
		if (jTextField9 == null) {
			jTextField9 = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jTextField9.setBounds(140, 45, 91, 22);
			jTextField9.setFormatterFactory(getDefaultFormatterFactory());
		}
		return jTextField9;
	}

	/**
	 * 
	 * This method initializes jTextField6
	 * 
	 * 
	 * 
	 * @return javax.swing.JFormattedTextField
	 * 
	 */
	private JFormattedTextField getJTextField6() {
		if (jTextField6 == null) {
			jTextField6 = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jTextField6.setBounds(139, 70, 208, 22);
			jTextField6.setFormatterFactory(getDefaultFormatterFactory());
		}
		return jTextField6;
	}

	/**
	 * 
	 * This method initializes jTextField7
	 * 
	 * 
	 * 
	 * @return javax.swing.JFormattedTextField
	 * 
	 */
	private JFormattedTextField getJTextField7() {
		if (jTextField7 == null) {
			jTextField7 = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jTextField7.setFormatterFactory(getDefaultFormatterFactory());
			jTextField7.setBounds(480, 70, 219, 22);
		}
		return jTextField7;
	}

	/**
	 * 
	 * This method initializes jTextField18
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField18() {
		if (jTextField18 == null) {
			jTextField18 = new JTextField();
			jTextField18.setBounds(533, 131, 139, 22);
			jTextField18.setEditable(false);
		}
		return jTextField18;
	}

	/**
	 * 
	 * This method initializes jButton4
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new JButton();
			jButton4.setBounds(671, 130, 26, 21);
			jButton4.setText("...");
			jButton4.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					Customs c = (Customs) CommonQuery.getInstance()
							.getCustoms();
					if (c != null) {
						custom = c;
						getJTextField18().setText(c.getName());
					}

				}
			});

		}
		return jButton4;
	}

	/**
	 * 
	 * This method initializes jTextField19
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField19() {
		if (jTextField19 == null) {
			jTextField19 = new JTextField();
			jTextField19.setBounds(111, 164, 229, 23);
		}
		return jTextField19;
	}

	/**
	 * 
	 * This method initializes jTextField20
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField20() {
		if (jTextField20 == null) {
			jTextField20 = new JTextField();
			jTextField20.setBounds(111, 66, 154, 23);
		}
		return jTextField20;
	}

	/**
	 * 
	 * This method initializes jTextField21
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField21() {
		if (jTextField21 == null) {
			jTextField21 = new JTextField();
			jTextField21.setBounds(363, 66, 131, 23);
		}
		return jTextField21;
	}

	/**
	 * 
	 * This method initializes jTextField22
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField22() {
		if (jTextField22 == null) {
			jTextField22 = new JTextField();
			jTextField22.setBounds(111, 90, 153, 23);
		}
		return jTextField22;
	}

	/**
	 * 
	 * This method initializes jTextField23
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField23() {
		if (jTextField23 == null) {
			jTextField23 = new JTextField();
			jTextField23.setBounds(590, 66, 110, 23);
		}
		return jTextField23;
	}
	
	private JTextField getJTextField60() {
		if (jTextField60 == null) {
			jTextField60 = new JTextField();
			jTextField60.setBounds(590, 90, 110, 23);
		}
		return jTextField60;
	}

	/**
	 * This method initializes jTextField24
	 * 
	 * @return javax.swing.JTextField
	 */
	private JComboBox getJTextField24() {
		if (jTextField24 == null) {
			jTextField24 = new JComboBox();
			jTextField24.setBounds(111, 114, 151, 23);
			jTextField24.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String name = jTextField24.getSelectedItem() == null ? null
							: jTextField24.getSelectedItem().toString();
					String tel = materialManageAction.findCustomsUserTel(
							new Request(CommonVars.getCurrUser()), name);
					jTextField25.setText(tel);
				}
			});
		}
		return jTextField24;
	}

	/**
	 * This method initializes jTextField25
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField25() {
		if (jTextField25 == null) {
			jTextField25 = new JTextField();
			jTextField25.setBounds(462, 114, 134, 23);
		}
		return jTextField25;
	}

	/**
	 * This method initializes jTextField26
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField26() {
		if (jTextField26 == null) {
			jTextField26 = new JTextField();
			jTextField26.setBounds(115, 72, 117, 25);
		}
		return jTextField26;
	}

	/**
	 * This method initializes jTextField27
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField27() {
		if (jTextField27 == null) {
			jTextField27 = new JTextField();
			jTextField27.setBounds(319, 71, 147, 22);
		}
		return jTextField27;
	}

	/**
	 * This method initializes jTextField28
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField28() {
		if (jTextField28 == null) {
			jTextField28 = new JTextField();
			jTextField28.setBounds(575, 73, 124, 23);
		}
		return jTextField28;
	}

	/**
	 * This method initializes jTextField29
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTFRedDep() {
		if (jTFRedDep == null) {
			jTFRedDep = new JTextField();
			jTFRedDep.setBounds(new java.awt.Rectangle(63, 154, 144, 22));
			jTFRedDep.setEditable(false);
		}
		return jTFRedDep;
	}

	/**
	 * This method initializes jButton5
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJBTRedDep() {
		if (jBTRedDep == null) {
			jBTRedDep = new JButton();
			jBTRedDep.setBounds(new java.awt.Rectangle(208, 154, 21, 22));
			jBTRedDep.setText("...");
			jBTRedDep.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					RedDep c = (RedDep) CommonQuery.getInstance().getRedDep();
					if (c != null) {
						redDep = c;
						getJTFRedDep().setText(c.getName());
					}

				}
			});
		}
		return jBTRedDep;
	}

	/**
	 * This method initializes tfOutFax	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfOutFax() {
		if (tfOutFax == null) {
			tfOutFax = new JTextField();
			tfOutFax.setBounds(new Rectangle(363, 90, 131, 23));
		}
		return tfOutFax;
	}
} // @jve:decl-index=0:visual-constraint="10,0"

/*
 * class ComboBoxModel extends DefaultComboBoxModel { private List list = null;
 * 
 * private JComboBox combo;
 * 
 * private Hashtable briefTable = new Hashtable();
 * 
 * public ComboBoxModel(List list, JComboBox combo) { this.list = list;
 * this.combo = combo; this.combo.setRenderer(new DefaultListCellRenderer() {
 * public Component getListCellRendererComponent(JList list, Object value, int
 * index, boolean isSelected, boolean cellHasFocus) { Component retValue =
 * super.getListCellRendererComponent(list, value, index, isSelected,
 * cellHasFocus); String name = (index < 0) ? "" : ((Brief)
 * ComboBoxModel.this.list.get(index)) .getName(); String code = (value == null)
 * ? "" : value.toString(); setText(code); list.setToolTipText(name);
 * briefTable.put(code, name); return this; }
 * 
 * }); }
 * 
 * public int getSize() { return (list == null) ? 0 : list.size(); }
 * 
 * public Object getElementAt(int index) { return ((list == null) || list.size()
 * < 0) ? null : ((Brief) list .get(index)).getCode(); }
 * 
 * public Object getSelectItem() { return (combo.getSelectedIndex() < 0) ? null
 * : list.get(combo .getSelectedIndex()); }
 * 
 * public String getName(String code) { return (briefTable.get(code) == null) ?
 * "" : briefTable.get(code) .toString(); } }
 */