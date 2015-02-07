package com.bestway.bcus.client.cas.otherreport;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcus.cas.invoice.entity.CarryBalance;
import com.bestway.bcus.cas.parameterset.entity.OtherOption;
import com.bestway.bcus.client.cas.otherreport.DgTransferfactoryDiffAllReport.DataModel;
import com.bestway.bcus.client.cas.parameter.CasCommonVars;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomReportDataSourceNew;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.system.entity.Company;
import com.bestway.common.CommonUtils;
import com.bestway.common.authority.entity.AclUser;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * 差额表分组打印选择
 * 
 * @author Administrator
 *
 */
public class DgTransferfactoryDiffAllReportGroupSet extends JDialogBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel jPanel = null;
	private JLabel jLabel = null;
	private ButtonGroup buttonGroup = null; // @jve:decl-index=0:
	private JRadioButton rbScmCoc = null;
	private JRadioButton rbAll = null;
	private JButton btnOk = null;
	private JButton btnCalc = null;
	private Boolean groupSet = false; // @jve:decl-index=0:
	private DataModel parentModel = null; // @jve:decl-index=0:
	private OtherOption otherOption = null; // @jve:decl-index=0:

	/**
	 * This method initializes
	 * 
	 */
	public DgTransferfactoryDiffAllReportGroupSet(DataModel model) {
		super();
		this.parentModel = model;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(358, 221));
		this.setContentPane(getJPanel());
		this.setTitle("选择分组打印方式");
		this.setResizable(false);
		otherOption = CasCommonVars.getOtherOption();
		initButtonGroup();
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(27, 21, 142, 17));
			jLabel.setText("请选择分组打印方式：");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel, null);
			jPanel.add(getRbScmCoc(), null);
			jPanel.add(getRbAll(), null);
			jPanel.add(getBtnOk(), null);
			jPanel.add(getBtnCalc(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes rbScmCoc
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbScmCoc() {
		if (rbScmCoc == null) {
			rbScmCoc = new JRadioButton();
			rbScmCoc.setBounds(new Rectangle(72, 73, 98, 21));
			rbScmCoc.setSelected(true);
			rbScmCoc.setText("分客户打印");
		}
		return rbScmCoc;
	}

	/**
	 * This method initializes rbAll
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbAll() {
		if (rbAll == null) {
			rbAll = new JRadioButton();
			rbAll.setBounds(new Rectangle(180, 74, 128, 21));
			rbAll.setText("打印全部");
		}
		return rbAll;
	}

	/**
	 * This method initializes buttonGroup
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup initButtonGroup() {
		if (buttonGroup == null) {
			buttonGroup = new ButtonGroup();
			buttonGroup.add(getRbScmCoc());
			buttonGroup.add(getRbAll());
		}
		return buttonGroup;
	}

	/**
	 * This method initializes btnOk
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton();
			btnOk.setBounds(new Rectangle(76, 140, 62, 23));
			btnOk.setText("确定");
			btnOk.addActionListener(new java.awt.event.ActionListener() { // TempTransFactCompareInfo
				@SuppressWarnings({ "unchecked", "deprecation" })
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (rbScmCoc.isSelected()) {
						// if(parentModel.groupCondition.size() != 4) {
						// JOptionPane.showMessageDialog(DgTransferfactoryDiffAllReportGroupSet.this,
						// "只有选择全部分组条件查询后才可以分组打印！否则只能打印全部", "提示", 2);
						// return ;
						// }
						groupSet = true;
					} else {
						groupSet = false;
					}

					// 报表月份
					int month = parentModel.reportDate.getMonth() + 1;

					// 表格数据
					List<CarryBalance> list = parentModel.list;

					// 打印数据
					List<CarryBalance> printList = new ArrayList<CarryBalance>();

					CarryBalance c = null;

					int n = otherOption.getOtherReportMaximumFractionDigits() == null ? 2
							: otherOption.getOtherReportMaximumFractionDigits();
					CarryBalance qc = null; // 期初待先转数量
					CarryBalance sh = null; // 本期收货入库数量
					CarryBalance th = null; // 本期退货出库数量
					CarryBalance zc = null; // 本期转厂数量
					CarryBalance qm = null; // 期末待先转数量

					List<CarryBalance> tempList = new ArrayList<CarryBalance>();

					for (int j = 0; j < list.size(); j++) {
						tempList.add(list.get(j).clone());
						if (groupSet) {
							if ((j + 1) % 4 == 0) {
								tempList.add(list.get(j - 1).clone());// 添加多一行存放本期转厂数量
							}
						}
					}

					for (int i = 0; i < tempList.size(); i++) {
						printList.add(tempList.get(i).clone());
						if (groupSet) {
							if ((i + 1) % 5 == 0) {
								qc = printList.get(i - 2);// = 期初
								zc = printList.get(i - 0);// = 转厂
								sh = printList.get(i - 4);// = 收货数
								th = printList.get(i - 3);// = 退货数
								qm = printList.get(i - 1);// = 差额

								qc.setType("期初未转厂数量");
								sh.setType("本期收货入库数量");
								th.setType("本期退货出库数量");
								zc.setType("本期转厂数量");
								qm.setType("期末未转厂数量");

								/*
								 * 调整顺序
								 */
								printList.set(i - 4, qc); // 期初待先转数量
								printList.set(i - 3, sh); // 本期收货入库数量
								printList.set(i - 2, th); // 本期退货出库数量
								printList.set(i - 1, zc); // 本期转厂数量
								printList.set(i - 0, qm); // 期末待先转数量

								/*
								 * 调整 期初待先转数量 第一月份 期初待先转数量 = （期初 - 转厂） 其他月份
								 * 期初待先转数量 = （上月差额 - 转厂）
								 * 
								 * 注：上月差额 = 上期期末待先转数量
								 */
								qc.setAmountMonth1(CommonUtils
										.getDoubleByDigit(qc.getAmountFirst()
												- qc.getAmountMonth1(), n));
								qc.setAmountMonth2(CommonUtils
										.getDoubleByDigit(qm.getAmountMonth1()
												- qc.getAmountMonth2(), n));
								qc.setAmountMonth3(CommonUtils
										.getDoubleByDigit(qm.getAmountMonth2()
												- qc.getAmountMonth3(), n));
								qc.setAmountMonth4(CommonUtils
										.getDoubleByDigit(qm.getAmountMonth3()
												- qc.getAmountMonth4(), n));
								qc.setAmountMonth5(CommonUtils
										.getDoubleByDigit(qm.getAmountMonth4()
												- qc.getAmountMonth5(), n));
								qc.setAmountMonth6(CommonUtils
										.getDoubleByDigit(qm.getAmountMonth5()
												- qc.getAmountMonth6(), n));
								qc.setAmountMonth7(CommonUtils
										.getDoubleByDigit(qm.getAmountMonth6()
												- qc.getAmountMonth7(), n));
								qc.setAmountMonth8(CommonUtils
										.getDoubleByDigit(qm.getAmountMonth7()
												- qc.getAmountMonth8(), n));
								qc.setAmountMonth9(CommonUtils
										.getDoubleByDigit(qm.getAmountMonth8()
												- qc.getAmountMonth9(), n));
								qc.setAmountMonth10(CommonUtils
										.getDoubleByDigit(qm.getAmountMonth9()
												- qc.getAmountMonth10(), n));
								qc.setAmountMonth11(CommonUtils
										.getDoubleByDigit(qm.getAmountMonth10()
												- qc.getAmountMonth11(), n));
								qc.setAmountMonth12(CommonUtils
										.getDoubleByDigit(qm.getAmountMonth11()
												- qc.getAmountMonth12(), n));

								switch (month) {
								case 1:
									qc.setAmountMonth2(0.0);
									sh.setAmountMonth2(0.0);
									th.setAmountMonth2(0.0);
									qm.setAmountMonth2(0.0);
									zc.setAmountMonth2(0.0);
									break;
								case 2:
									qc.setAmountMonth3(0.0);
									sh.setAmountMonth3(0.0);
									th.setAmountMonth3(0.0);
									qm.setAmountMonth3(0.0);
									zc.setAmountMonth3(0.0);
									break;
								case 3:
									qc.setAmountMonth4(0.0);
									sh.setAmountMonth4(0.0);
									th.setAmountMonth4(0.0);
									qm.setAmountMonth4(0.0);
									zc.setAmountMonth4(0.0);
									break;
								case 4:
									qc.setAmountMonth5(0.0);
									sh.setAmountMonth5(0.0);
									th.setAmountMonth5(0.0);
									qm.setAmountMonth5(0.0);
									zc.setAmountMonth5(0.0);
									break;
								case 5:
									qc.setAmountMonth6(0.0);
									sh.setAmountMonth6(0.0);
									th.setAmountMonth6(0.0);
									qm.setAmountMonth6(0.0);
									zc.setAmountMonth6(0.0);
									break;
								case 6:
									qc.setAmountMonth7(0.0);
									sh.setAmountMonth7(0.0);
									th.setAmountMonth7(0.0);
									qm.setAmountMonth7(0.0);
									zc.setAmountMonth7(0.0);
									break;
								case 7:
									qc.setAmountMonth8(0.0);
									sh.setAmountMonth8(0.0);
									th.setAmountMonth8(0.0);
									qm.setAmountMonth8(0.0);
									zc.setAmountMonth8(0.0);
									break;
								case 8:
									qc.setAmountMonth9(0.0);
									sh.setAmountMonth9(0.0);
									th.setAmountMonth9(0.0);
									qm.setAmountMonth9(0.0);
									zc.setAmountMonth9(0.0);
									break;
								case 9:
									qc.setAmountMonth10(0.0);
									sh.setAmountMonth10(0.0);
									th.setAmountMonth10(0.0);
									qm.setAmountMonth10(0.0);
									zc.setAmountMonth10(0.0);
									break;
								case 10:
									qc.setAmountMonth11(0.0);
									sh.setAmountMonth11(0.0);
									th.setAmountMonth11(0.0);
									qm.setAmountMonth11(0.0);
									zc.setAmountMonth11(0.0);
									break;
								case 11:
									qc.setAmountMonth12(0.0);
									sh.setAmountMonth12(0.0);
									th.setAmountMonth12(0.0);
									qm.setAmountMonth12(0.0);
									zc.setAmountMonth12(0.0);
									break;
								}
							}
						} else {
							c = printList.get(i);
							c.setAmountFirst(CommonUtils.getDoubleByDigit(
									c.getAmountFirst(), n));
							c.setAmountMonth1(CommonUtils.getDoubleByDigit(
									c.getAmountMonth1(), n));
							c.setAmountMonth2(CommonUtils.getDoubleByDigit(
									c.getAmountMonth2(), n));
							c.setAmountMonth3(CommonUtils.getDoubleByDigit(
									c.getAmountMonth3(), n));
							c.setAmountMonth4(CommonUtils.getDoubleByDigit(
									c.getAmountMonth4(), n));
							c.setAmountMonth5(CommonUtils.getDoubleByDigit(
									c.getAmountMonth5(), n));
							c.setAmountMonth6(CommonUtils.getDoubleByDigit(
									c.getAmountMonth6(), n));
							c.setAmountMonth7(CommonUtils.getDoubleByDigit(
									c.getAmountMonth7(), n));
							c.setAmountMonth8(CommonUtils.getDoubleByDigit(
									c.getAmountMonth8(), n));
							c.setAmountMonth9(CommonUtils.getDoubleByDigit(
									c.getAmountMonth9(), n));
							c.setAmountMonth10(CommonUtils.getDoubleByDigit(
									c.getAmountMonth10(), n));
							c.setAmountMonth11(CommonUtils.getDoubleByDigit(
									c.getAmountMonth11(), n));
							c.setAmountMonth12(CommonUtils.getDoubleByDigit(
									c.getAmountMonth12(), n));
							c.setTotal(CommonUtils.getDoubleByDigit(
									c.getTotal(), n));
							c.setYearTotal(CommonUtils.getDoubleByDigit(
									c.getYearTotal(), n));
						}
					}
					// 格式化小数位
					for (int j = 0; j < printList.size(); j++) {
						CarryBalance cb = (CarryBalance) printList.get(j);
						cb.setAmountFirst(CommonUtils.getDoubleByDigit(
								cb.getAmountFirst(), n));
						cb.setAmountMonth1(CommonUtils.getDoubleByDigit(
								cb.getAmountMonth1(), n));
						cb.setAmountMonth2(CommonUtils.getDoubleByDigit(
								cb.getAmountMonth2(), n));
						cb.setAmountMonth3(CommonUtils.getDoubleByDigit(
								cb.getAmountMonth3(), n));
						cb.setAmountMonth4(CommonUtils.getDoubleByDigit(
								cb.getAmountMonth4(), n));
						cb.setAmountMonth5(CommonUtils.getDoubleByDigit(
								cb.getAmountMonth5(), n));
						cb.setAmountMonth6(CommonUtils.getDoubleByDigit(
								cb.getAmountMonth6(), n));
						cb.setAmountMonth7(CommonUtils.getDoubleByDigit(
								cb.getAmountMonth7(), n));
						cb.setAmountMonth8(CommonUtils.getDoubleByDigit(
								cb.getAmountMonth8(), n));
						cb.setAmountMonth9(CommonUtils.getDoubleByDigit(
								cb.getAmountMonth9(), n));
						cb.setAmountMonth10(CommonUtils.getDoubleByDigit(
								cb.getAmountMonth10(), n));
						cb.setAmountMonth11(CommonUtils.getDoubleByDigit(
								cb.getAmountMonth11(), n));
						cb.setAmountMonth12(CommonUtils.getDoubleByDigit(
								cb.getAmountMonth12(), n));
						cb.setTotal(CommonUtils.getDoubleByDigit(cb.getTotal(),
								n));
					}

					CustomReportDataSourceNew ds = new CustomReportDataSourceNew(
							printList);
					InputStream reportStream = null;
					if (groupSet) {
						if (parentModel.groupCondition.size() == 4) {
							reportStream = DgTransferfactoryDiffAllReportGroupSet.class
									.getResourceAsStream("report/DgTransferfactoryDiffAllReportSingle.jasper");
						} else {
							reportStream = DgTransferfactoryDiffAllReportGroupSet.class
									.getResourceAsStream("report/DgTransferfactoryDiffAllReportSingle1.jasper");
						}
					} else {
						reportStream = DgTransferfactoryDiffAllReportGroupSet.class
								.getResourceAsStream("report/DgTransferfactoryDiffAllReport.jasper");
					}
					Map<String, Object> parameters = new HashMap<String, Object>();
					AclUser user = CommonVars.getCurrUser();
					Company company = (Company) user.getCompany();
					Date lastDate = new Date(); // 当日期 + 7天
					lastDate.setDate(lastDate.getDate() + 7);
					parameters.put("userName", user.getUserName());
					parameters.put("companyName", company.getName());
					parameters.put("tel", company.getTel());
					parameters.put("fax", company.getOutFax());
					parameters.put("lastDate",
							CommonUtils.getDate(lastDate, "yyyy-MM-dd"));
					parameters.put("title", getTitle());
					parameters.put("impExpType", parentModel.isM ? "转入" : "转出");
					JasperPrint jasperPrint;
					try {
						jasperPrint = JasperFillManager.fillReport(
								reportStream, parameters, ds);
						DgReportViewer viewer = new DgReportViewer(jasperPrint);
						viewer.setVisible(true);
					} catch (JRException e1) {
						e1.printStackTrace();
					}
				}
			});
		}
		return btnOk;
	}

	/**
	 * This method initializes btnCalc
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCalc() {
		if (btnCalc == null) {
			btnCalc = new JButton();
			btnCalc.setBounds(new Rectangle(212, 140, 62, 23));
			btnCalc.setText("取消");
			btnCalc.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnCalc;
	}

	public Boolean getGroupSet() {
		return groupSet;
	}

	public void setGroupSet(Boolean groupSet) {
		this.groupSet = groupSet;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
