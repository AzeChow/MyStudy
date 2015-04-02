/*
 * Created on 2004-8-16
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.client.contractexe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contractexe.action.ContractExeAction;
import com.bestway.bcs.contractexe.entity.BcsCustomsDeclaration;
import com.bestway.bcs.contractexe.entity.TempBcsImpExpCommodityInfo;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.enc.entity.ImpExpRequestBill;
import com.bestway.bcus.system.action.SystemAction;
import com.bestway.bcus.system.entity.CustomsDeclarationSet;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author Administrator // change the template for this generated impExpType
 *         comment go to Window - Preferences - Java - Code Style - Code
 *         Templates
 */
@SuppressWarnings("unchecked")
public class DgMakeBcsCustomsDeclaration extends JDialogBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2116582862644653028L;

	private JPanel jContentPane = null;

	private JPanel jPanel = null;

	private JLabel lbHeadText = null;

	private JPanel jPanel1 = null;

	private JButton btnPrevious = null;

	private JButton btnNext = null;

	private JButton btnDone = null;

	private JButton btnExit = null;

	private JPanel pnContext = null;

	private int impExpType = -1;

	private int step = 0;

	private boolean isOk = false;

	private boolean isImportGoods = true;// 是否进口商品类型

	private BcsCustomsDeclaration bcsCustomsDeclaration = null; // @jve:decl-index=0:

	private ScmCoc scmCoc = null;

	private List parentList = null;

	private List commInfoList = null;

	private JTableListModel tableMode = null;

	private List commodityList = null;

	private List checkList = null;

	private String declareState = null;

	private boolean isNewRecord = true;

	private PnMakeBcsCustomsDeclaration pnMakeBcsCustomsDeclaration = null;

	private PnMakeBcsCustomsDeclaration2 pnMakeBcsCustomsDeclaration2 = null;

	private PnMakeBcsCustomsDeclaration3 pnMakeBcsCustomsDeclaration3 = null;
	/**
	 * 报关商品信息
	 */
	private PnMakeBcsCustomsDeclaration4 pnMakeBcsCustomsDeclaration4 = null;

	private int materielProductType = -1;

	private boolean isInRequestBill = true; // 是来自申请单

	private ContractExeAction contractExeAction = null;

	private Date date = null;

	// private boolean isAutoSelectContract = false;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	private Contract contract = null;
	private ImpExpRequestBill headImpExpRequestBill = null;
	/**
	 * 判断转成后报关单表体商品单价来自申请单还是合同 2010-09-15 hcl add
	 */
	private boolean isPriceFromContract = false;

	private JLabel jLabel17 = null;

	private JLabel jLabel19 = null;

	private SystemAction systemAction = null;

	public DgMakeBcsCustomsDeclaration() {
		super();
		initialize();
		contractExeAction = (ContractExeAction) CommonVars
				.getApplicationContext().getBean("contractExeAction");
		systemAction = (SystemAction) CommonVars.getApplicationContext()
				.getBean("systemAction");
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("进出口申请单转---报关单");
		this.setContentPane(getJContentPane());
		this.setSize(681, 456);
	}

	/**
	 * 获取头申请单
	 */
	public ImpExpRequestBill getHeadImpExpRequestBill() {
		return headImpExpRequestBill;
	}

	/**
	 * 设置头申请单
	 */
	public void setHeadImpExpRequestBill(ImpExpRequestBill headImpExpRequestBill) {
		this.headImpExpRequestBill = headImpExpRequestBill;
	}

	/**
	 * @return Returns the isOk.
	 */
	public boolean isOk() {
		return isOk;
	}

	/**
	 * @return Returns the materielProductType.
	 */
	public int getMaterielProductType() {
		return materielProductType;
	}

	/**
	 * @param materielProductType
	 *            The materielProductType to set.
	 */
	public void setMaterielProductType(int materielProductType) {
		this.materielProductType = materielProductType;
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel19 = new JLabel();
			jLabel19.setText("");
			jLabel19.setBounds(new Rectangle(584, 0, 84, 37));
			jLabel19.setIcon(new ImageIcon(getClass().getResource(
					"/com/bestway/bcus/client/resources/images/titlepic.jpg")));
			jLabel17 = new JLabel();
			jLabel17.setText("");
			jLabel17.setBounds(new Rectangle(1, -1, 55, 37));
			jLabel17.setIcon(new ImageIcon(getClass().getResource(
					"/com/bestway/bcus/client/resources/images/titlepoint.jpg")));
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJPanel(), BorderLayout.NORTH);
			jContentPane.add(getJPanel1(), BorderLayout.SOUTH);
			jContentPane.add(getPnContext(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * @return Returns the impExpType.
	 */
	public int getImpExpType() {
		return impExpType;
	}

	/**
	 * @param impExpType
	 *            The impExpType to set.
	 */
	public void setImpExpType(int type) {
		this.impExpType = type;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			lbHeadText = new JLabel();
			jPanel.setLayout(new BorderLayout());
			jPanel.setBackground(Color.white);
			jPanel.setBorder(BorderFactory
					.createEtchedBorder(EtchedBorder.LOWERED));
			jPanel.add(jLabel17, java.awt.BorderLayout.WEST);
			jPanel.add(lbHeadText, java.awt.BorderLayout.CENTER);
			jPanel.add(jLabel19, java.awt.BorderLayout.EAST);
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
			jPanel1.setLayout(new FlowLayout());
			jPanel1.setBorder(javax.swing.BorderFactory
					.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jPanel1.add(getBtnPrevious(), null);
			jPanel1.add(getBtnNext(), null);
			jPanel1.add(getBtnDone(), null);
			jPanel1.add(getBtnExit(), null);
		}
		return jPanel1;
	}

	/**
	 * @return Returns the tableMode.
	 */
	public JTableListModel getTableMode() {
		return tableMode;
	}

	/**
	 * @param tableMode
	 *            The tableMode to set.
	 */
	public void setTableMode(JTableListModel tableMode) {
		this.tableMode = tableMode;
	}

	/**
	 * @return Returns the parentList.
	 */
	public List getParentList() {
		return parentList;
	}

	/**
	 * @param parentList
	 *            The parentList to set.
	 */
	public void setParentList(List parentList) {
		this.parentList = parentList;
	}

	public boolean isPriceFromContract() {
		isPriceFromContract = !pnMakeBcsCustomsDeclaration.getRbApply()
				.isSelected();
		return isPriceFromContract;
	}

	public void setPriceFromContract(boolean isPriceFromContract) {
		this.isPriceFromContract = isPriceFromContract;
	}

	/**
	 * @return Returns the isImportGoods.
	 */
	public boolean isImportGoods() {
		return isImportGoods;
	}

	/**
	 * @param isImportGoods
	 *            The isImportGoods to set.
	 */
	public void setImportGoods(boolean isImportGoods) {
		this.isImportGoods = isImportGoods;
	}

	/**
	 * This method initializes btnPrevious
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrevious() {
		if (btnPrevious == null) {
			btnPrevious = new JButton();
			btnPrevious.setBounds(170, 9, 72, 24);
			btnPrevious.setText("上一步");
			btnPrevious.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					step--;
					gotoStep(step);
				}
			});
		}
		return btnPrevious;
	}

	/**
	 * This method initializes btnNext
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnNext() {
		if (btnNext == null) {
			btnNext = new JButton();
			btnNext.setBounds(245, 9, 72, 24);
			btnNext.setText("下一步");
			btnNext.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (step == 0) {// 开始显示表头数据

						PnMakeBcsCustomsDeclaration p0 = getPnMakeBcsCustomsDeclaration();

						if (!p0.vaildateData()) {

							return;

						}

					} else if (step == 1) {// 第2步:选择申请单表头

						if (!getPnMakeBcsCustomsDeclaration2().vaildateData()) {
							return;
						}

					} else if (step == 2) {// 第3步：选择申请单表体

						PnMakeBcsCustomsDeclaration3 p3 = getPnMakeBcsCustomsDeclaration3();

						if (p3.getCommodityList() == null
								|| p3.getCommodityList().size() <= 0) {

							JOptionPane.showMessageDialog(null,
									"请选择进出口申请单据商品信息!", "提示",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}

						String unitConvertStr = "";

						for (int i = 0; i < p3.getCommodityList().size(); i++) {

							TempBcsImpExpCommodityInfo temp = (TempBcsImpExpCommodityInfo) p3
									.getCommodityList().get(i);

							Double unitConvert = temp.getImpExpCommodityInfo()
									.getMateriel().getUnitConvert();

							// 净重
							Double netWeight = temp.getImpExpCommodityInfo()
									.getNetWeight();

							if (unitConvert == null
									|| unitConvert.doubleValue() == Double
											.valueOf(0)) {

								unitConvertStr = unitConvertStr
										+ "物料主档料号为："
										+ temp.getImpExpCommodityInfo()
												.getMateriel().getPtNo()
										+ "折算系数："
										+ (unitConvert == null ? 0.0
												: unitConvert) + "\n";

							}

							if (netWeight == null
									|| netWeight.doubleValue() == Double
											.valueOf(0)) {
								unitConvertStr = unitConvertStr
										+ "申请单料号为："
										+ temp.getImpExpCommodityInfo()
												.getNetWeight() + "净重："
										+ (netWeight == null ? 0.0 : netWeight)
										+ "\n";
							}
						}

						if (!"".equals(unitConvertStr)) {

							if (JOptionPane.showConfirmDialog(
									DgMakeBcsCustomsDeclaration.this,
									unitConvertStr + "\n  是否继续?\n", "提示", 0) == JOptionPane.NO_OPTION) {
								return;
							}
						}
					}
					step++;
					gotoStep(step);
				}
			});
		}
		return btnNext;
	}

	/**
	 * This method initializes btnDone
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDone() {
		if (btnDone == null) {
			btnDone = new JButton();
			btnDone.setBounds(351, 7, 60, 24);
			btnDone.setText("执行");

			btnDone.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					List comminfoList = pnMakeBcsCustomsDeclaration4
							.getCommodityList();

					doneAction(comminfoList);

				}
			});
		}
		return btnDone;
	}

	private void doneAction(List comminfoList) {

		if (comminfoList == null || comminfoList.size() <= 0) {

			JOptionPane.showMessageDialog(DgMakeBcsCustomsDeclaration.this,
					"请查看申请单对应归并后商品信息!", "提示", 2);
			return;
		}

		if (comminfoList.size() > 20) {

			if (JOptionPane.showConfirmDialog(DgMakeBcsCustomsDeclaration.this,
					"要转的申请单对应归并后商品信息超过[20]项，是否继续？", "确认",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.NO_OPTION) {

				return;
			}
		}

		new DoneThread(comminfoList).start();

	};

	/**
	 * 执行
	 */
	private void done(List<TempBcsImpExpCommodityInfo> commodityInfos) {

		if (commodityInfos == null || commodityInfos.size() == 0) {

			JOptionPane.showMessageDialog(DgMakeBcsCustomsDeclaration.this,
					"没有可以执行数据！");
		}

		CommonProgress.showProgressDialog();

		CommonProgress.setMessage("系统正在生成报关单，请稍候...");

		try {

			// 把系统参数设置－其他参数设置资料放进去HYQ
			CustomsDeclarationSet parameter = systemAction.findCustomsSet(
					new Request(CommonVars.getCurrUser()), this.impExpType);

			if (parameter != null) {

				bcsCustomsDeclaration
						.setLevyKind(parameter.getCustomlevyKind());

				bcsCustomsDeclaration.setBillOfLading(parameter
						.getBillOfLading());

				bcsCustomsDeclaration
						.setBalanceMode(parameter.getBalanceMode());

				bcsCustomsDeclaration.setConveyance(parameter.getConveyance());

				bcsCustomsDeclaration.setTransac(parameter.getTransac());

				bcsCustomsDeclaration.setPredock(parameter.getPredock());

				bcsCustomsDeclaration.setTransferMode(parameter
						.getTransferMode());

				bcsCustomsDeclaration.setDomesticDestinationOrSource(parameter
						.getDistrict());

				bcsCustomsDeclaration.setWrapType(parameter.getCommWrapType());

				bcsCustomsDeclaration.setCountryOfLoadingOrUnloading(parameter
						.getCoun());

				bcsCustomsDeclaration.setPortOfLoadingOrUnloading(parameter
						.getPort());

				bcsCustomsDeclaration.setImpExpDate(date == null ? new Date()
						: date);

				bcsCustomsDeclaration.setDeclarationDate(new Date());
			}

			this.contract = getPnMakeBcsCustomsDeclaration4().getContract();

			bcsCustomsDeclaration.setContract(contract == null ? "" : contract
					.getImpContractNo());

			bcsCustomsDeclaration.setEmsHeadH2k(contract == null ? ""
					: contract.getEmsNo());

			bcsCustomsDeclaration.setTradeCode(contract == null ? "" : contract
					.getTradeCode());

			bcsCustomsDeclaration.setTradeName(contract == null ? "" : contract
					.getTradeName());

			bcsCustomsDeclaration.setMachCode(contract == null ? "" : contract
					.getMachCode());

			bcsCustomsDeclaration.setMachName(contract == null ? "" : contract
					.getMachName());

			if (this.isImportGoods == true) {
				// 进口
				bcsCustomsDeclaration.setImpExpFlag(Integer
						.valueOf(ImpExpFlag.IMPORT));
			} else {
				// 出口
				bcsCustomsDeclaration.setImpExpFlag(Integer
						.valueOf(ImpExpFlag.EXPORT));

			}

			// commodityList =
			// getPnMakeBcsCustomsDeclaration4().getComInfoList();
			commodityList = commodityInfos;

			if (commodityList == null || commodityList.size() <= 0) {
				CommonProgress.closeProgressDialog();
				return;
			}

			if (!isSameCurr()) {

				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgMakeBcsCustomsDeclaration.this,
						"报关单生成失败, 币值不相同不能转成报关单!", "提示", 2);
				return;
			}

			//
			// 生成报关单
			//
			boolean isProduct = this.materielProductType == Integer
					.parseInt(MaterielType.FINISHED_PRODUCT);
			//
			// 单位净重参数
			//
			Double weightpara = contractExeAction.findweightpara(new Request(
					CommonVars.getCurrUser()));

			if (weightpara == null || 0 == weightpara) {
				weightpara = 1.0;
			}

			// List<TempBcsImpExpCommodityInfo> listInfo =
			// getMergeBeforeInfo(commodityInfos);

			List mylist = this.contractExeAction.makeBcsCustomsDeclaration(
					new Request(CommonVars.getCurrUser()),
					bcsCustomsDeclaration, parentList, commodityInfos,
					isProduct, weightpara, isPriceFromContract());

			List list1 = new ArrayList();
			List list0 = new ArrayList();
			String s = "";
			if (mylist.size() <= 0) {
				// 存放报关单
				s += "报关单生成失败!\n";
			} else {
				list0 = (List) mylist.get(1);
				list1 = (List) mylist.get(0);

			}
			if (list1 != null && list1.size() > 0) {

				s += "报关单生成成功!\n";

				for (int k = 0; k < list1.size(); k++) {

					BcsCustomsDeclaration c = (BcsCustomsDeclaration) list1
							.get(k);

					s += "报关单流水号：" + c.getSerialNumber() + "\n";

				}

			} else {

				s += "报关单生成失败!\n";

			}

			if (this.isInRequestBill) {
				// for (int k = 0; k < list0.size(); k++) {
				// ImpExpRequestBill c = (ImpExpRequestBill) list0.get(k);
				// this.tableMode.updateRow(c);
				// }
				this.tableMode.updateRows(list0);

			}
			CommonProgress.closeProgressDialog();

			JOptionPane.showMessageDialog(this, s, "提示!",
					JOptionPane.INFORMATION_MESSAGE);

		} catch (Exception ex) {

			CommonProgress.closeProgressDialog();

			ex.printStackTrace();

			JOptionPane.showMessageDialog(null, "报关单生成失败,数据可能没有在合同中备案!", "提示!",
					JOptionPane.INFORMATION_MESSAGE);

			return;

		} finally {
			this.dispose();
		}
	}

	/**
	 * 获取归并前料件
	 * 
	 * @param list
	 *            归并后料件
	 * @return
	 */
	private List<TempBcsImpExpCommodityInfo> getMergeBeforeInfo(
			List<TempBcsImpExpCommodityInfo> list) {
		List<TempBcsImpExpCommodityInfo> mergeBeforeInfo = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			mergeBeforeInfo.addAll(list.get(i).getCommodityInfos());
		}
		return mergeBeforeInfo;
	}

	/**
	 * 设置表头的件数、净重、毛重
	 */
	// private void setHeadData() {
	// Integer commodityNum=0;
	// Double grossWeight=0.0;
	// Double netWeight=0.0;
	// for(int i=0;i<commodityList.size();i++){
	// commodityNum+=((TempBcsImpExpCommodityInfo)commodityList.get(i)).getImpExpCommodityInfo().getPiece();
	// grossWeight+=((TempBcsImpExpCommodityInfo)commodityList.get(i)).getImpExpCommodityInfo().getGrossWeight();
	// netWeight+=((TempBcsImpExpCommodityInfo)commodityList.get(i)).getImpExpCommodityInfo().getNetWeight();
	//
	//
	// }
	//
	// if(pnMakeBcsCustomsDeclaration.getRbIntoOldBill().isSelected()){
	// BcsCustomsDeclaration
	// b=pnMakeBcsCustomsDeclaration.getBcsCustomsDeclaration();
	// System.out.println("b.commodityNum = "+ b.getCommodityNum());
	// System.out.println("b.getGrossWeight = "+ b.getGrossWeight());
	// System.out.println("b.getNetWeight = "+ b.getNetWeight());
	//
	//
	// commodityNum+=b.getCommodityNum();
	// grossWeight+=b.getGrossWeight();
	// netWeight+=b.getNetWeight();
	// }
	//
	// bcsCustomsDeclaration.setCommodityNum(commodityNum);
	// bcsCustomsDeclaration.setGrossWeight(grossWeight);
	// bcsCustomsDeclaration.setNetWeight(netWeight);
	// }
	private boolean isSameCurr() {
		boolean isAddToExist = pnMakeBcsCustomsDeclaration.getRbIntoOldBill()
				.isSelected();
		if (!isAddToExist)
			for (int i = 0; i < commodityList.size(); i++) {
				if (!((TempBcsImpExpCommodityInfo) commodityList.get(0))
						.getImpExpCommodityInfo()
						.getCurrency()
						.getName()
						.equals(((TempBcsImpExpCommodityInfo) commodityList
								.get(i)).getImpExpCommodityInfo().getCurrency()
								.getName()))
					return false;
			}
		else {// 添加到已有报关单
			BcsCustomsDeclaration b = pnMakeBcsCustomsDeclaration
					.getBcsCustomsDeclaration();
			Curr c = b.getCurrency();
			System.out.println("curr=" + c.getName());
			for (int i = 0; i < commodityList.size(); i++) {
				if (!c.getName().equals(
						((TempBcsImpExpCommodityInfo) commodityList.get(i))
								.getImpExpCommodityInfo().getCurrency()
								.getName()))
					return false;
			}

		}
		// 当币制相同表头设置币制
		bcsCustomsDeclaration
				.setCurrency(((TempBcsImpExpCommodityInfo) commodityList.get(0))
						.getImpExpCommodityInfo().getCurrency());
		return true;
	}

	/**
	 * This method initializes btnExit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setBounds(427, 7, 60, 24);
			btnExit.setText("关闭");
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnExit;
	}

	private PnMakeBcsCustomsDeclaration getPnMakeBcsCustomsDeclaration() {
		if (pnMakeBcsCustomsDeclaration == null) {
			pnMakeBcsCustomsDeclaration = new PnMakeBcsCustomsDeclaration(
					headImpExpRequestBill);
			pnMakeBcsCustomsDeclaration.setBounds(0, 0, 630, 248);

		}

		return pnMakeBcsCustomsDeclaration;
	}

	private PnMakeBcsCustomsDeclaration2 getPnMakeBcsCustomsDeclaration2() {
		if (pnMakeBcsCustomsDeclaration2 == null) {
			pnMakeBcsCustomsDeclaration2 = new PnMakeBcsCustomsDeclaration2();
			pnMakeBcsCustomsDeclaration2.setBounds(0, 0, 630, 248);
		}
		return pnMakeBcsCustomsDeclaration2;
	}

	/**
	 * @return Returns the pnMakeCustomsEnvelopBill3.
	 */
	public PnMakeBcsCustomsDeclaration3 getPnMakeBcsCustomsDeclaration3() {
		if (pnMakeBcsCustomsDeclaration3 == null) {
			pnMakeBcsCustomsDeclaration3 = new PnMakeBcsCustomsDeclaration3();
			pnMakeBcsCustomsDeclaration3.setBounds(0, 0, 630, 248);
		}
		return pnMakeBcsCustomsDeclaration3;
	}

	/**
	 * @return Returns the PnMakeApplyToCustoms4.
	 */
	public PnMakeBcsCustomsDeclaration4 getPnMakeBcsCustomsDeclaration4() {
		if (pnMakeBcsCustomsDeclaration4 == null) {
			pnMakeBcsCustomsDeclaration4 = new PnMakeBcsCustomsDeclaration4();
			pnMakeBcsCustomsDeclaration4.setBounds(0, 0, 630, 248);
		}
		return pnMakeBcsCustomsDeclaration4;
	}

	/**
	 * This method initializes pnContext
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnContext() {
		if (pnContext == null) {
			pnContext = new JPanel();
			pnContext.setLayout(new BorderLayout());
			pnContext.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,
					0, 0, 0));
		}
		return pnContext;
	}

	/**
	 * 第一步 设置报关单表头数据
	 */
	private void initPn0() {

		this.pnContext.removeAll();

		this.pnContext.add(this.getPnMakeBcsCustomsDeclaration(),
				BorderLayout.CENTER);

		this.getPnMakeBcsCustomsDeclaration().setHeadImpExpRequestBill(
				headImpExpRequestBill);

		this.getPnMakeBcsCustomsDeclaration().setImportGoods(isImportGoods);

		this.getPnMakeBcsCustomsDeclaration().setNewBill(isInRequestBill);

		this.getPnMakeBcsCustomsDeclaration().setBcsCustomsDeclaration(
				this.bcsCustomsDeclaration);

		this.getPnMakeBcsCustomsDeclaration().setType(impExpType);

		this.getPnMakeBcsCustomsDeclaration()
				.setIsfromCustoms(!isInRequestBill);

		this.getPnMakeBcsCustomsDeclaration().setVisible(true);

		this.lbHeadText.setText("第一步：设置生成报关单表头数据");
	}

	/**
	 * 第二步：选择需要报关单的单据
	 */
	private void initPn1() {

		this.pnContext.removeAll();

		this.pnContext.add(this.getPnMakeBcsCustomsDeclaration2(),
				BorderLayout.CENTER);

		this.impExpType = this.getPnMakeBcsCustomsDeclaration().getImpExpType();

		this.isNewRecord = this.getPnMakeBcsCustomsDeclaration()
				.getIsNewRecord();

		this.bcsCustomsDeclaration = this.getPnMakeBcsCustomsDeclaration()
				.getBcsCustomsDeclaration();

		this.getPnMakeBcsCustomsDeclaration2().setImpExpType(this.impExpType);

		this.getPnMakeBcsCustomsDeclaration2().setVisible(true);

		this.lbHeadText.setText("第二步：选择需要报关单的单据");
	}

	/**
	 * 第三步：选择需要生成报关清单申请单据体
	 */
	private void initPn2() {

		this.pnContext.removeAll();

		this.pnContext.add(this.getPnMakeBcsCustomsDeclaration3(),
				BorderLayout.CENTER);

		this.parentList = this.getPnMakeBcsCustomsDeclaration2()
				.getParentList();

		this.getPnMakeBcsCustomsDeclaration3().setParentList(parentList);

		this.getPnMakeBcsCustomsDeclaration3().setMaterielProductType(
				this.materielProductType);

		this.getPnMakeBcsCustomsDeclaration3().setVisible(true);

		this.lbHeadText.setText("第三步：选择需要生成报关清单申请单据体");
	}

	/**
	 * 第四步：生成报关单商品信息(电子化手册)
	 */
	private void initPn3() {

		this.pnContext.removeAll();

		this.commInfoList = this.getPnMakeBcsCustomsDeclaration3()
				.getCommodityList();

		this.getPnMakeBcsCustomsDeclaration4().setMaterielProductType(
				this.materielProductType);

		this.getPnMakeBcsCustomsDeclaration4().setImpExpType(
				bcsCustomsDeclaration.getImpExpType());

		this.getPnMakeBcsCustomsDeclaration4().setTradeCode(
				bcsCustomsDeclaration.getTradeCode());

		this.getPnMakeBcsCustomsDeclaration4().setCommInfoList(commInfoList);

		this.pnContext.add(this.getPnMakeBcsCustomsDeclaration4(),
				BorderLayout.CENTER);

		this.getPnMakeBcsCustomsDeclaration4().setVisible(true);

		this.lbHeadText.setText("第四步：生成报关单商品信息(电子化手册)");

	}

	private void gotoStep(int step) {
		System.out.println("----- step:" + step);
		switch (step) {
		case 0:
			initPn0();
			break;
		case 1:
			initPn1();
			break;
		case 2:
			initPn2();
			break;
		case 3:
			initPn3();
			break;
		}
		this.pnContext.repaint();
		this.pnContext.validate();
		setState();
	}

	private void setState() {
		// this.btnPrevious.setEnabled(this.step > 0);
		this.btnPrevious.setEnabled(this.step > 0);
		this.btnNext.setEnabled(this.step < 3);
		this.btnDone.setEnabled(this.step == 3);
	}

	public void setVisible(boolean b) {
		if (b) {
			this.gotoStep(this.step);
		}
		super.setVisible(b);
	}

	/**
	 * @return Returns the isInRequestBill.
	 */
	public boolean isInRequestBill() {
		return isInRequestBill;
	}

	/**
	 * @param isInRequestBill
	 *            The isInRequestBill to set.
	 */
	public void setInRequestBill(boolean isInRequestBill) {
		this.isInRequestBill = isInRequestBill;
	}

	/**
	 * @return Returns the bcsCustomsDeclaration.
	 */
	public BcsCustomsDeclaration getBcsCustomsDeclaration() {
		return bcsCustomsDeclaration;
	}

	/**
	 * @param bcsCustomsDeclaration
	 *            The bcsCustomsDeclaration to set.
	 */
	public void setBcsCustomsDeclaration(
			BcsCustomsDeclaration bcsCustomsDeclaration) {
		this.bcsCustomsDeclaration = bcsCustomsDeclaration;
	}

	// public boolean isAutoSelectContract() {
	// return isAutoSelectContract;
	// }
	//
	// public void setAutoSelectContract(boolean isAutoSelectContract) {
	// this.isAutoSelectContract = isAutoSelectContract;
	// }

	class DoneThread extends Thread {

		public List<TempBcsImpExpCommodityInfo> commodityInfos;

		public DoneThread(List<TempBcsImpExpCommodityInfo> commodityInfos) {
			DoneThread.this.commodityInfos = commodityInfos;
		}

		public void run() {
			done(DoneThread.this.commodityInfos);
		}
	}
} // @jve:decl-index=0:visual-constraint="10,10"
