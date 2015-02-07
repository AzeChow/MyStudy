/*
 * Created on 2004-8-24
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.system;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.DgCommonQuery;
import com.bestway.bcus.custombase.action.CustomBaseAction;
import com.bestway.bcus.dataimport.action.DataImportAction;
import com.bestway.bcus.dataimport.entity.DBDataRoot;
import com.bestway.bcus.system.action.SystemAction;
import com.bestway.bcus.system.entity.ApplyCustomBillParameter;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.bcus.system.entity.ParameterSet;
import com.bestway.bcus.system.entity.ReportControl;
import com.bestway.client.util.JTableListColumn;
import com.bestway.common.Request;
import com.bestway.common.authority.entity.AclUser;
import com.bestway.common.constant.ParameterType;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

/**
 * lm checked by 2009-1-21
 * 
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public class FmParameterSet extends JInternalFrameBase {

	private javax.swing.JPanel pnContent = null;

	private JPanel pnMain = null;

	private JButton btnSave = null;

	private JButton btnClose = null;

	private SystemAction systemAction = null;

	private JButton btnAmend = null;

	private int dataState = DataState.BROWSE;

	private ButtonGroup group = new ButtonGroup(); // @jve:decl-index=0:

	private ButtonGroup group1 = new ButtonGroup(); // @jve:decl-index=0:

	private String emsFrom = "1";

	private String contraceFrom = "1";

	private JPanel pnTop = null;

	private JLabel lbNonceCompany = null;

	private JLabel lbBaseData = null;

	private JComboBox cbbInvisible = null;

	private JButton btnStartUpgrade = null;

	private List list = null;

	private DataImportAction dataImportAction = null;

	private CustomBaseAction customBaseAction = null;

	private Hashtable<String, Object> hs = null;

	private JButton btnElsePreferences = null;

	private JLabel lbAgility = null;

	private JRadioButton rbTenBit = null;

	private JRadioButton rbWareNo = null;

	private JLabel lbImg = null;

	private JButton btnCustomsDeclaration = null;

	private JButton btnModifyNumber = null;

	private Map tmap = new HashMap(); // @jve:decl-index=0:

	private JButton btnSetReport = null;

	private JButton btSetTcsParams = null;

	/**
	 * This is the default constructor
	 */
	public FmParameterSet() {

		systemAction = (SystemAction) CommonVars.getApplicationContext()
				.getBean("systemAction");

		dataImportAction = (DataImportAction) CommonVars
				.getApplicationContext().getBean("dataImportAction");

		customBaseAction = (CustomBaseAction) CommonVars
				.getApplicationContext().getBean("customBaseAction");

		systemAction.checkSystemParameterAuthority(new Request(CommonVars
				.getCurrUser()));

		list = dataImportAction.findDBDataRoot(new Request(CommonVars
				.getCurrUser()));

		initialize();

		setDBRoot();

		if (CommonVars.getDbRoot() != null) {
			this.cbbInvisible.setSelectedItem(CommonVars.getDbRoot().getName());
		}

	}

	/**
	 * 设置数据路径
	 */
	private void setDBRoot() {
		if (hs == null) {
			hs = new Hashtable<String, Object>();
			for (int i = 0; i < list.size(); i++) {
				DBDataRoot root = (DBDataRoot) list.get(i);
				hs.put(root.getName(), root);
			}
		}
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {

		setTitle("系统参数设置");

		setHelpId("parameterSet");

		setSize(895, 529);

		setContentPane(getPnContent());

		fillWindow();

		dataState = DataState.BROWSE;

		setState();

		group1.add(rbTenBit);

		group1.add(rbWareNo);
	}

	/**
	 * 填充窗体
	 */
	private void fillWindow() {
		List list = null;
		ParameterSet para = null;

		list = systemAction.findnameValues(
				new Request(CommonVars.getCurrUser()), ParameterType.year);
		if (list != null && list.size() > 0) {
			para = (ParameterSet) list.get(0);
		}

		list = systemAction.findnameValues(
				new Request(CommonVars.getCurrUser()),
				ParameterType.contractFrom);
		if (list != null && list.size() > 0) {
			para = (ParameterSet) list.get(0);
		}
		if (para.getNameValues().equals("1")) {
			this.rbTenBit.setSelected(true);
		} else if (para.getNameValues().equals("2")) {
			this.rbWareNo.setSelected(true);
		}

		lbNonceCompany.setText("当前公司："
				+ CommonVars.getCurrUser().getCompany().getName());
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
			pnContent.setBorder(javax.swing.BorderFactory.createCompoundBorder(
					null, null));
			pnContent.add(getPnMain(), java.awt.BorderLayout.CENTER);
			pnContent.add(getPnTop(), java.awt.BorderLayout.NORTH);
		}
		return pnContent;
	}

	/**
	 * 
	 * This method initializes pnMain
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getPnMain() {
		if (pnMain == null) {
			lbImg = new JLabel();
			lbImg.setBounds(new java.awt.Rectangle(16, 53, 86, 55));
			lbImg.setIcon(new ImageIcon(getClass().getResource(
					"/com/bestway/bcus/client/resources/images/gj.gif")));
			lbImg.setText("");
			lbAgility = new JLabel();
			lbBaseData = new JLabel();
			lbBaseData.setVisible(false);
			lbNonceCompany = new JLabel();
			pnMain = new JPanel();
			pnMain.setLayout(null);
			lbNonceCompany.setBounds(17, 20, 406, 21);
			lbNonceCompany.setText("当前公司：");
			lbNonceCompany.setForeground(new java.awt.Color(51, 0, 255));
			pnMain.setForeground(new java.awt.Color(51, 0, 255));
			lbBaseData.setBounds(25, 308, 152, 22);
			lbBaseData.setText("关务基本资料升级数据源");
			lbAgility.setBounds(27, 363, 148, 21);
			lbAgility.setText("合同备案来源：");
			lbAgility.setForeground(new java.awt.Color(51, 0, 255));
			lbAgility.setVisible(false);
			pnMain.add(getBtnSave(), null);
			pnMain.add(getBtnClose(), null);
			pnMain.add(getBtnAmend(), null);
			pnMain.add(lbNonceCompany, null);
			pnMain.add(lbBaseData, null);
			pnMain.add(getCbbInvisible(), null);
			pnMain.add(getBtnStartUpgrade(), null);
			pnMain.add(getBtnElsePreferences(), null);
			pnMain.add(lbAgility, null);
			pnMain.add(getRbTenBit(), null);
			pnMain.add(getRbWareNo(), null);
			pnMain.add(lbImg, null);
			pnMain.add(getBtnCustomsDeclaration(), null);
			pnMain.add(getBtnModifyNumber(), null);
			pnMain.add(getBtnSetReport(), null);
			pnMain.add(getBtSetTcsParams(), null);
		}
		return pnMain;
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
			btnSave.setBounds(214, 156, 80, 26);
			btnSave.setText("保存");
			btnSave.setVisible(false);
			btnSave.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (checkNull()) {
						return;
					}
					if (checkRepeat()) {
						return;
					}

					dataState = DataState.BROWSE;
					setState();
				}
			});

		}
		return btnSave;
	}

	private void saveTxtDir() {

	}

	/**
	 * 保存系统参数设置资料
	 * 
	 * @param parameterType
	 * @param values
	 * @param flat
	 */
	private void saveValues(int parameterType, String values, String flat) {
		List list = systemAction.findnameValues(
				new Request(CommonVars.getCurrUser()), parameterType);
		ParameterSet para = (ParameterSet) list.get(0);
		para.setNameValues(values);
		if (flat.equals("0")) {

		} else if (flat.equals("1")) {
			CommonVars.setEmsFrom(values);
		} else if (flat.equals("2")) {
			CommonVars.setContractFrom(values);
		}
		systemAction.saveValues(new Request(CommonVars.getCurrUser()), para);

	}

	/**
	 * 保存输出参数是否繁转简
	 * 
	 * @param parameterType
	 * @param values
	 */

	private void saveIsBigToGBK(int parameterType, String values) {
		List list = systemAction.findnameValues(
				new Request(CommonVars.getCurrUser()), parameterType);
		ParameterSet para = (ParameterSet) list.get(0);
		para.setNameValues(values);
		CommonVars.setIsBigToGBK(Integer.parseInt(values));
		systemAction.saveValues(new Request(CommonVars.getCurrUser()), para);
		systemAction.saveisBigToGbk(new Request(CommonVars.getCurrUser()),
				values);
		System.out.println("保存繁转简值：" + values);
	}

	private boolean checkNull() {

		return false;

	}

	private boolean checkRepeat() {

		return false;
	}

	/**
	 * 
	 * This method initializes btnClose
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setBounds(768, 6, 89, 25);
			btnClose.setText("关闭");
			btnClose.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					FmParameterSet.this.dispose();

				}
			});

		}
		return btnClose;
	}

	/**
	 * 监听事件
	 * 
	 * @author Administrator
	 *
	 */

	private class RadioActionListner implements ActionListener {
		public void actionPerformed(java.awt.event.ActionEvent e) {

		}
	}

	/**
	 * 创建监听事件类
	 * 
	 * @author Administrator
	 *
	 */

	private class RadioActionListner1 implements ActionListener {
		public void actionPerformed(java.awt.event.ActionEvent e) {
			if (rbTenBit.isSelected()) {
				FmParameterSet.this.setContraceFrom("1");
			} else if (rbWareNo.isSelected()) {
				FmParameterSet.this.setContraceFrom("2");
			}
		}
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
			btnAmend.setBounds(124, 156, 81, 26);
			btnAmend.setText("修改");
			btnAmend.setVisible(false);
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
	 * 设置控件状态
	 */
	private void setState() {

		this.rbTenBit.setEnabled(!(dataState == DataState.BROWSE));
		this.rbWareNo.setEnabled(!(dataState == DataState.BROWSE));

		this.btnSave.setEnabled(!(dataState == DataState.BROWSE));
		this.btnAmend.setEnabled(dataState == DataState.BROWSE);

	}

	/**
	 * @return Returns the emsFrom.
	 */
	public String getEmsFrom() {
		return emsFrom;
	}

	/**
	 * @param emsFrom
	 *            The emsFrom to set.
	 */
	public void setEmsFrom(String emsFrom) {
		this.emsFrom = emsFrom;
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
			jLabel17.setIcon(new ImageIcon(getClass().getResource(
					"/com/bestway/bcus/client/resources/images/titlepoint.jpg")));
			jLabel18.setText("系统参数设置");
			jLabel18.setFont(new Font("\u65b0\u5b8b\u4f53", Font.BOLD, 20));
			jLabel18.setForeground(new java.awt.Color(255, 153, 0));
			jLabel19.setText("");
			jLabel19.setIcon(new ImageIcon(getClass().getResource(
					"/com/bestway/bcus/client/resources/images/titlepic.jpg")));
			pnTop.setBorder(javax.swing.BorderFactory
					.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			pnTop.setBackground(java.awt.Color.white);
			pnTop.add(jLabel17, java.awt.BorderLayout.WEST);
			pnTop.add(jLabel18, java.awt.BorderLayout.CENTER);
			pnTop.add(jLabel19, java.awt.BorderLayout.EAST);
		}
		return pnTop;
	}

	/**
	 * This method initializes cbbInvisible
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbInvisible() {
		if (cbbInvisible == null) {
			cbbInvisible = new JComboBox();
			cbbInvisible.setVisible(false);
			for (int i = 0; i < list.size(); i++) {
				DBDataRoot root = (DBDataRoot) list.get(i);
				cbbInvisible.addItem(root.getName());
			}
			cbbInvisible.setBounds(178, 309, 149, 21);
		}
		return cbbInvisible;
	}

	/**
	 * This method initializes btnStartUpgrade
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnStartUpgrade() {
		if (btnStartUpgrade == null) {
			btnStartUpgrade = new JButton();
			btnStartUpgrade.setVisible(false);
			btnStartUpgrade.setBounds(341, 309, 87, 23);
			btnStartUpgrade.setText("开始升级");
			btnStartUpgrade
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (cbbInvisible.getSelectedItem() == null) {
								return;
							}
							String dbName = cbbInvisible.getSelectedItem()
									.toString();
							CommonVars.setDbRoot((DBDataRoot) hs.get(dbName));
							new updateBase().start();
						}
					});
		}
		return btnStartUpgrade;
	}

	/**
	 * 创建线呈类
	 * 
	 * @author Administrator
	 *
	 */

	class updateBase extends Thread {
		public void run() {
			try {
				CommonProgress.showProgressDialog();
				CommonProgress.setMessage("系统正在升级资料，请稍后...");
				customBaseAction.updateCustoms(
						new Request(CommonVars.getCurrUser()),
						CommonVars.getDbRoot());
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmParameterSet.this, "升级完成！",
						"提示", 2);
			} catch (Exception ex) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmParameterSet.this, "升级失败：！"
						+ ex.getMessage(), "提示", 2);
			}
		}
	}

	/**
	 * This method initializes btnElsePreferences
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnElsePreferences() {
		if (btnElsePreferences == null) {
			btnElsePreferences = new JButton();
			btnElsePreferences.setBounds(381, 94, 150, 40);
			btnElsePreferences.setText("其他参数设置");
			btnElsePreferences
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {

							DgCompanyOther dg = new DgCompanyOther();
							List list = systemAction
									.findCompanyOther(new Request(CommonVars
											.getCurrUser()));
							ApplyCustomBillParameter a = systemAction
									.findApplyCustomBillParameter(new Request(
											CommonVars.getCurrUser()));
							if (list != null && list.size() > 0) {
								dg.setCompanyOther((CompanyOther) list.get(0));
							} else {
								dg.setCompanyOther(new CompanyOther());
							}
							if (a != null) {
								dg.setApplyCustomBillParameter(a);
							} else {
								dg.setApplyCustomBillParameter(new ApplyCustomBillParameter());
							}
							dg.setVisible(true);
						}
					});
		}
		return btnElsePreferences;
	}

	/**
	 * This method initializes rbTenBit
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbTenBit() {
		if (rbTenBit == null) {
			rbTenBit = new JRadioButton();
			rbTenBit.setBounds(50, 367, 158, 21);
			rbTenBit.setText("十位商品归并");
			rbTenBit.setVisible(false);
			rbTenBit.addActionListener(new RadioActionListner1());
		}
		return rbTenBit;
	}

	/**
	 * This method initializes rbWareNo
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbWareNo() {
		if (rbWareNo == null) {
			rbWareNo = new JRadioButton();
			rbWareNo.setBounds(240, 367, 142, 21);
			rbWareNo.setText("商品编码");
			rbWareNo.setVisible(false);
			rbWareNo.addActionListener(new RadioActionListner1());
		}
		return rbWareNo;
	}

	/**
	 * @return Returns the contraceFrom.
	 */
	public String getContraceFrom() {
		return contraceFrom;
	}

	/**
	 * @param contraceFrom
	 *            The contraceFrom to set.
	 */
	public void setContraceFrom(String contraceFrom) {
		this.contraceFrom = contraceFrom;
	}

	/**
	 * This method initializes btnCustomsDeclaration
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCustomsDeclaration() {
		if (btnCustomsDeclaration == null) {
			btnCustomsDeclaration = new JButton();
			btnCustomsDeclaration.setBounds(new Rectangle(176, 94, 150, 40));
			btnCustomsDeclaration.setText("报关单参数设置");
			btnCustomsDeclaration
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							FmCustomsDeclarationSet dg = new FmCustomsDeclarationSet();
							dg.setVisible(true);
						}
					});
		}
		return btnCustomsDeclaration;
	}

	/**
	 * This method initializes btnModifyNumber
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnModifyNumber() {
		if (btnModifyNumber == null) {
			btnModifyNumber = new JButton();
			btnModifyNumber.setText("修改统一编号,报关单号,预录入号");
			btnModifyNumber.setBounds(new Rectangle(176, 228, 354, 40));
			btnModifyNumber
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							AclUser user = CommonVars.getCurrUser();
							if (user == null || user.getUserFlag() == null
									|| (!user.getUserFlag().equals("S"))) {
								JOptionPane.showMessageDialog(
										FmParameterSet.this, "只有超级用户拥有该权限！",
										"提示！", JOptionPane.WARNING_MESSAGE);
								return;
							}
							BaseCustomsDeclaration base = (BaseCustomsDeclaration) getEditCustomsdeclaretion();
							if (base == null) {
								return;
							}
							DgEditCustomsDeclaretion dg = new DgEditCustomsDeclaretion();
							dg.setBase(base);
							dg.setTmap(tmap);
							dg.setVisible(true);
						}
					});
		}
		return btnModifyNumber;
	}

	/**
	 * 选择要修改的报关单进行修改
	 * 
	 * @return
	 */
	public Object getEditCustomsdeclaretion() {

		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("流水号", "serialNumber", 50, Integer.class));
		list.add(new JTableListColumn("手册号", "emsHeadH2k", 100));
		list.add(new JTableListColumn("报关单号", "customsDeclarationCode", 150));
		list.add(new JTableListColumn("统一编号", "unificationCode", 150));
		list.add(new JTableListColumn("预录入号", "preCustomsDeclarationCode", 150));
		list.add(new JTableListColumn("申报日期", "declarationDate", 100));
		DgCommonQuery.setTableColumns(list);
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		// List dataSource = mdtAction.findComplex();
		List dataSource = (List) this.customBaseAction
				.findAllCustomsDeclaretion(new Request(CommonVars.getCurrUser()));
		List allTYBH = new ArrayList();
		List allYLRH = new ArrayList();
		List allBGDH = new ArrayList();
		for (int i = 0; i < dataSource.size(); i++) {
			BaseCustomsDeclaration bs = (BaseCustomsDeclaration) dataSource
					.get(i);
			if (bs.getUnificationCode() != null
					&& !bs.getUnificationCode().equals("")) {
				allTYBH.add(bs.getUnificationCode());
			}
			if (bs.getPreCustomsDeclarationCode() != null
					&& !bs.getPreCustomsDeclarationCode().equals("")) {
				allYLRH.add(bs.getPreCustomsDeclarationCode());
			}
			if (bs.getCustomsDeclarationCode() != null
					&& !bs.getCustomsDeclarationCode().equals("")) {
				allBGDH.add(bs.getCustomsDeclarationCode());
			}
		}
		tmap.clear();
		tmap.put("allTYBH", allTYBH);
		tmap.put("allYLRH", allYLRH);
		tmap.put("allBGDH", allBGDH);
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("选择您需要修改的报关单");
		DgCommonQuery.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	/**
	 * This method initializes btnSetReport
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSetReport() {
		if (btnSetReport == null) {

			btnSetReport = new JButton();

			btnSetReport.setBounds(new Rectangle(176, 160, 150, 40));

			btnSetReport.setText("报表栏位设置");

			btnSetReport.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgSetReport dg = new DgSetReport();

					System.out.println("111111111");

					List list = systemAction.findReportControl(new Request(
							CommonVars.getCurrUser()));

					System.out.println("list=" + list.size());

					if (list != null && list.size() > 0) {

						dg.setReportControl((ReportControl) list.get(0));

					} else {

						dg.setReportControl(new ReportControl());

					}

					dg.setVisible(true);
				}
			});
		}
		return btnSetReport;
	}

	/**
	 * This method initializes btSetTcsParams
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtSetTcsParams() {
		if (btSetTcsParams == null) {
			btSetTcsParams = new JButton();
			btSetTcsParams.setBounds(new Rectangle(381, 163, 150, 40));
			btSetTcsParams.setText("EDI接口参数设置");
			btSetTcsParams
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							FmTcsSet dg = new FmTcsSet();
							dg.setVisible(true);
						}
					});
		}
		return btSetTcsParams;
	}
} // @jve:decl-index=0:visual-constraint="10,16"
