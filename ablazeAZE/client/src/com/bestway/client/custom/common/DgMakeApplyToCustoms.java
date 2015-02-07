package com.bestway.client.custom.common;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.border.EtchedBorder;

import com.bestway.bcus.client.DgErrorMessage;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.custombase.action.CustomBaseAction;
import com.bestway.bcus.custombase.entity.basecode.Brief;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.enc.entity.ApplyToCustomsBillList;
import com.bestway.bcus.enc.entity.CustomsDeclaration;
import com.bestway.bcus.enc.entity.MakeCusomsDeclarationParam;
import com.bestway.bcus.enc.entity.TempCustomsList;
import com.bestway.bcus.enc.entity.TempImpExpCommodityInfo;
import com.bestway.bcus.enc.entity.TempImpExpRequestBill;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerHead;
import com.bestway.bcus.manualdeclare.entity.RestirictCommodity;
import com.bestway.bcus.system.action.SystemAction;
import com.bestway.bcus.system.entity.Company;
import com.bestway.bcus.system.entity.ParameterSet;
import com.bestway.common.CommonUtils;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.ParameterType;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * 进出口申请单转－－报关清单－－报关单
 * 
 * Checked by lxr
 * 
 * 
 * @author lxr
 * 
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class DgMakeApplyToCustoms extends JDialogBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JButton btnPrevious = null;
	private JButton btnNext = null;
	private JButton btnDone = null;
	private JButton btnExit = null;

	private JPanel pnContext = null;
	private JPanel jPanel1 = null;
	private JPanel jPanel11 = null;

	private JLabel jLabel17 = null;
	private JLabel jLabel19 = null;
	private JLabel lbHeadText = null;
	/**
	 * 设置步长
	 */
	private int step = 0;
	/**
	 * 检查是否是限制类商品
	 */
	private boolean isRerictCommodity = true;
	/**
	 * 申请单是否有数据备案
	 */
	private boolean isMakeList = true;
	/**
	 * 出现的第一个界面
	 */
	private PnMakeApplyToCustoms pnMakeApplyToCustoms = null;
	/**
	 * 申请单头信息
	 */
	private PnMakeApplyToCustoms2 pnMakeApplyToCustoms2 = null;
	/**
	 * 申请单体信息
	 */
	private PnMakeApplyToCustoms3 pnMakeApplyToCustoms3 = null;
	/**
	 * 报关商品信息
	 */
	private PnMakeApplyToCustoms4 pnMakeApplyToCustoms4 = null;
	/**
	 * 存放申请单转清单已经备案的数据
	 */
	private List isbeianList = new ArrayList(); // @jve:decl-index=0:
	/**
	 * 存放申请单转清单最后结果
	 */
	private List allBillList = new ArrayList(); // @jve:decl-index=0:

	/**
	 * 获取报关单实体
	 */
	private CustomsDeclaration customs = null; // @jve:decl-index=0:
	/**
	 * 获取电子帐册归并关系实体表头
	 */
	private EmsEdiMergerHead emsMergerHead = null; // @jve:decl-index=0:
	/**
	 * 获取报关清单自动生成报关单时的参数
	 */
	private MakeCusomsDeclarationParam para = null; // @jve:decl-index=0:
	private ManualDeclareAction manualDeclearAction = null;
	private CustomBaseAction customBaseAction = null;
	private SystemAction systemAction = null;
	private MaterialManageAction materialManageAction = null;
	private EncAction encAction = null;

	/**
	 * This method initializes
	 * 
	 */
	public DgMakeApplyToCustoms() {
		super();
		customBaseAction = (CustomBaseAction) CommonVars
				.getApplicationContext().getBean("customBaseAction");

		this.encAction = (EncAction) CommonVars.getApplicationContext()
				.getBean("encAction");
		this.manualDeclearAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		this.systemAction = (SystemAction) CommonVars.getApplicationContext()
				.getBean("systemAction");
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("进出口申请单转--报关清单-报关单");
		this.setContentPane(getJContentPane());
		this.setSize(803, 520);

	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJPanel1(), BorderLayout.SOUTH);
			jContentPane.add(getPnContext(), BorderLayout.CENTER);
			jContentPane.add(getJPanel11(), BorderLayout.NORTH);
		}
		return jContentPane;
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
			jPanel1
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jPanel1.add(getBtnPrevious(), null);
			jPanel1.add(getBtnNext(), null);
			jPanel1.add(getBtnDone(), null);
			jPanel1.add(getBtnExit(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes btnPrevious
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrevious() {
		if (btnPrevious == null) {
			btnPrevious = new JButton();
			btnPrevious.setText("上一步");
			btnPrevious.setPreferredSize(new Dimension(70, 25));
			btnPrevious.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					step--;
					initPn(step);
				}
			});
		}
		return btnPrevious;
	}

	/**
	 * 检查申请单转清单或者报关单表头信息是否存在NULL
	 * 
	 * @return
	 */
	private boolean chcekisNull() {
		if (getPnMakeApplyToCustoms().validateImpExpTypeIsNull()) {
			JOptionPane.showMessageDialog(null, "进出口类型不可为空!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		if (getPnMakeApplyToCustoms().validateApplyToCustomsBillListIsNull()) {
			JOptionPane.showMessageDialog(null, "报关清单对象不可为空!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}

		if (getPnMakeApplyToCustoms().getEmsHeadH2k() == null) {
			JOptionPane.showMessageDialog(null, "没有正在执行的电子帐册,不能生成报关清单或报关单!",
					"提示", JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		emsMergerHead = manualDeclearAction
				.findEmsEdiMergerHeadByDeclareState(new Request(CommonVars
						.getCurrUser()), DeclareState.PROCESS_EXE);
		if (emsMergerHead == null) {
			JOptionPane.showMessageDialog(null, "没有正在执行的归并关系，不能转换【报关单】!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		return false;
	}

	/**
	 * This method initializes btnNext
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnNext() {
		if (btnNext == null) {
			btnNext = new JButton();
			btnNext.setText("下一步");
			btnNext.setPreferredSize(new Dimension(70, 25));
			btnNext.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// 开始
					if (step == 0) {
						if (chcekisNull()) {
							return;
						}
						para = getPnMakeApplyToCustoms().getPara();
						// 第一步
					} else if (step == 1) {
						if (getPnMakeApplyToCustoms2().getParentList() == null
								|| getPnMakeApplyToCustoms2().getParentList()
										.size() <= 0) {
							JOptionPane.showMessageDialog(null, "请选择进出口申请单据头!",
									"提示", JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						// 第二步
					} else if (step == 2) {
						// 申请单据体
						List<TempImpExpCommodityInfo> commodityList = pnMakeApplyToCustoms3
								.getCommodityList();
						TempImpExpCommodityInfo info = null;
						String strControl = manualDeclearAction.getBpara(new Request(CommonVars
								.getCurrUser()), BcusParameter.BILL_CONTROL_PRICE);
						if(CommonUtils.notEmpty(strControl)) {
							double price = Double.parseDouble(strControl);
							String str = "";
							for (int i = 0; i < commodityList.size(); i++) {
								info = commodityList.get(i);
								if (info.getImpExpCommodityInfo()
										.getUnitPrice() > price) {
									str += ((i + 1) + ",");
								}
							}
							if(CommonUtils.notEmpty(str)) {
								int r = JOptionPane.showConfirmDialog(DgMakeApplyToCustoms.this, "申请单第【"
										+ str + "】项商品单价大于限制值是否继续："
										+ price + "!", "提示",
										JOptionPane.YES_NO_OPTION);
								if(r != JOptionPane.YES_OPTION ) {
									return;
								}
							}
						}
						
						if (commodityList == null || commodityList.size() <= 0) {
							JOptionPane.showMessageDialog(null,
									"请选择进出口申请单据商品信息!", "提示",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						
						if (isPutOnRecord(commodityList)) {
							step--;
							initPn(step);
							return;
						}
						isMakeList = true;
						new ExecuteMakeList().execute();
					}
					step++;
					initPn(step);
				}
			});
		}
		return btnNext;
	}

	/**
	 * 检查是不是限制类商品并且是否超出了限制类商品中定义的数量
	 * 
	 * @param commodityList
	 * @return
	 */
	public boolean checkRerictCommodity(List commodityList) {
		double DeclarationCommInfo = 0;
		double Amount = 0;
		double commodityamount = 0;
		String seqNum = null;
		Boolean isMaterial = EncCommon.isMaterial(para.getImpExpType());
		Collection cl = new Vector();
		Collection commoditycl = new Vector();
		Collection c2 = new Vector();
		for (int i = 0; i < commodityList.size(); i++) {
			TempCustomsList tt = (TempCustomsList) commodityList.get(i);
			seqNum = tt.getAfterinfo().getEmsSerialNo() == null ? "" : tt
					.getAfterinfo().getEmsSerialNo().toString();
			if (seqNum != null && !seqNum.equals("")) {

				// 得到报关单中的数量
				DeclarationCommInfo = materialManageAction
						.findCustomsDeclarationCommInfo(new Request(CommonVars
								.getCurrUser()), isMaterial, seqNum, null);

				Amount = DeclarationCommInfo
						+ Double.valueOf(tt.getAfterinfo().getDeclaredAmount()
								.toString());

				RestirictCommodity commodity = manualDeclearAction
						.findRerictCommodity(new Request(CommonVars
								.getCurrUser()), isMaterial, seqNum);

				if (commodity != null) {
					if (commodity.getAmount() != null
							&& !commodity.getAmount().equals("")) {
						commodityamount = Double.parseDouble(commodity
								.getAmount().toString());
						if (Amount > commodityamount) {
							cl.add(seqNum);
							commoditycl.add(Amount);
							c2.add(commodity.getAmount().toString());
						}
					}
				}
			}
		}
		if (commoditycl.size() > 0 && cl.size() > 0) {
			if (JOptionPane.showConfirmDialog(this, "备案号：" + cl + "已进(出)数量["
					+ commoditycl + "] 超出了限制类商品中备的数量[" + c2 + "]!\n", "提示", -1) == 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取录入单位
	 * 
	 * @param breifCode
	 * @return
	 */
	private Brief getBreif(String breifCode) {
		List list = customBaseAction.findBrief("code", breifCode);
		if (list.size() < 1) {
			return null;
		} else {
			return (Brief) list.get(0);
		}
	}

	/**
	 * 生成报关清单
	 */
	class ExecuteMakeList extends SwingWorker {
		@Override
		protected Object doInBackground() throws Exception {

			try {
				CommonProgress.showProgressDialog(DgMakeApplyToCustoms.this);
				CommonProgress.setMessage("系统正在执行数据，请稍后...");
				// 已选种申请单体
				List impexpbills = pnMakeApplyToCustoms3.getCommodityList();
				// 清单中添加客户与供应商
				if (impexpbills != null && impexpbills.size() > 0) {
					TempImpExpCommodityInfo t = (TempImpExpCommodityInfo) impexpbills
							.get(0);
					ScmCoc scmcoc = t.getImpExpCommodityInfo()
							.getImpExpRequestBill() == null ? null : t
							.getImpExpCommodityInfo().getImpExpRequestBill()
							.getScmCoc();
					para.getApplyToCustomsBillList().setScmCoc(scmcoc);
					para.getApplyToCustomsBillList().setCatNo(
							t.getImpExpCommodityInfo() == null ? null : t
									.getImpExpCommodityInfo()
									.getImpExpRequestBill().getCatNo());
					// 录入单位
					para.getApplyToCustomsBillList().setCreatedCompany(
							getBreif(((Company) CommonVars.getCurrUser()
									.getCompany()).getCode()));
					para.setDelcarationDate(t.getImpExpCommodityInfo()
							.getImpExpRequestBill().getBeginAvailability());
				}
				if (isbeianList != null && isbeianList.size() > 0) {
					boolean isMaterial = Integer
							.parseInt(MaterielType.MATERIEL) == para
							.getMaterielType();
					TempImpExpCommodityInfo t = (TempImpExpCommodityInfo) isbeianList
							.get(0);
					para.setCurr(t.getImpExpCommodityInfo().getCurrency());// 设置汇率
					allBillList = encAction.makeApplyToCustomsRequestBillList(
							new Request(CommonVars.getCurrUser()), isbeianList,
							isMaterial, para);

					CommonProgress.closeProgressDialog();

					isMakeList = true;
				} else {
					CommonProgress.closeProgressDialog();
					JOptionPane.showMessageDialog(null,
							"选择的申请单没有任何数据备案或者料号被禁用！", "提示",
							JOptionPane.INFORMATION_MESSAGE);
					isMakeList = false;
					step--;
					initPn(step);
				}

			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				e.printStackTrace();
				JOptionPane.showMessageDialog(DgMakeApplyToCustoms.this,
						"执行数据失败：！" + e.getMessage(), "提示", 2);
			} finally {
				if (!isMakeList) {
					return null;
				}
				initP3();
				if (para.isCustomsDeclaration()) {
					if (para.getImpExpType() != ImpExpType.BACK_FACTORY_REWORK
							&& para.getImpExpType() != ImpExpType.BACK_MATERIEL_EXPORT) {
						if (checkRerictCommodity(allBillList)) {
							isRerictCommodity = false;
						} else {
							isRerictCommodity = true;
						}
					}
				}
				pnContext.repaint();
				pnContext.validate();
				DgMakeApplyToCustoms.this.setState();
			}
			return null;
		}
	}

	/**
	 * 检查单据体是否已备案
	 */
	private boolean isPutOnRecord(List commodityList) {
		isbeianList = new ArrayList();
		List mlist = systemAction.findnameValues(new Request(CommonVars
				.getCurrUser()), ParameterType.emsFrom);
		ParameterSet paras = null;
		if (mlist != null && mlist.size() > 0) {
			paras = (ParameterSet) mlist.get(0);
		}
		String emsFrom = "";
		if (paras.getNameValues().equals("1")) {
			emsFrom = "1";
		} else if (paras.getNameValues().equals("2")) {
			emsFrom = "2";
		} else if (paras.getNameValues().equals("3")) {
			emsFrom = "3";
			JOptionPane.showMessageDialog(DgMakeApplyToCustoms.this,
					"电子帐册来源为 : 企业物料内部归并，该种来源不能够执行！", "提示！", 1);
			return true;
		}

		List emsList = encAction.findInnerMergeDataByPtNo(new Request(
				CommonVars.getCurrUser()), para.getEmsHeadH2k(), emsMergerHead,
				String.valueOf(para.getMaterielType()), false, emsFrom);

		if (emsList.size() <= 0) {
			JOptionPane.showMessageDialog(null, "选择的申请单没有任何数据备案！", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		Map<String, String> mapPtNo = new HashMap<String, String>();
		Map mapIsForbid = new HashMap();// 检查是否禁用
		for (int i = 0; i < emsList.size(); i++) {
			Object[] object = (Object[]) emsList.get(i);
			String ptNo = object[0].toString().toUpperCase().trim();
			if (object[1] != null && "true".equals(object[1].toString())) {// 检查是否禁用
				mapIsForbid.put(ptNo, ptNo);
				continue;
			}
			mapPtNo.put(ptNo, ptNo);
		}
		String forbidStr = "";
		String tishi = "";
		String unitConvertStr = "";
		for (int i = 0; i < commodityList.size(); i++) {
			TempImpExpCommodityInfo t = (TempImpExpCommodityInfo) commodityList
					.get(i);
			String pts = t.getImpExpCommodityInfo().getMateriel().getPtNo()
					.toUpperCase();
			String ptNo = mapPtNo
					.get(t.getImpExpCommodityInfo().getMateriel() == null ? t
							.getImpExpCommodityInfo().getMakeBillNo() : pts);
			Double unitConvert = t.getImpExpCommodityInfo().getMateriel()
					.getUnitConvert();
			//净重
			Double netWeight= t.getImpExpCommodityInfo().getNetWeight();
			// 返回已备案
			if (ptNo != null && !"".equals(ptNo)) {
				isbeianList.add(t);
			} else if (mapIsForbid.get(pts) != null) {
				// ===========检查是否有禁用的-==============
				forbidStr = forbidStr + pts + "\n";
			} else {
				// ===========检查未备案的资料===============
				tishi = tishi + pts + "\n";
			}
			if (unitConvert == null
					|| unitConvert.doubleValue() == Double.valueOf(0)) {
				unitConvertStr = unitConvertStr + "物料主档或内部归并料号为：" + ptNo + "折算系数："
						+ (unitConvert==null?0.0:unitConvert) + "\n";
			}
			if (netWeight == null
					|| netWeight.doubleValue() == Double.valueOf(0)) {
				unitConvertStr = unitConvertStr + "申请单料号为：" + ptNo + "净重："
						+ (netWeight==null?0.0:netWeight) + "\n";
			}
		}

		if (!"".equals(forbidStr) || !"".equals(tishi)) {
			if (!"".equals(tishi)) {
				tishi = "未备案 \n" + tishi;
			}
			if (!"".equals(forbidStr)) {
				forbidStr = "已禁用 \n" + forbidStr;
			}
			if (JOptionPane.showConfirmDialog(DgMakeApplyToCustoms.this,
					forbidStr + tishi + "\n  是否继续?\n", "提示", 0) == JOptionPane.OK_OPTION) {
				return false;
			} else {
				return true;
			}
		}

		if (!"".equals(unitConvertStr)) {
			if (JOptionPane.showConfirmDialog(DgMakeApplyToCustoms.this,
					unitConvertStr + "\n  是否继续?\n", "提示", 0) == JOptionPane.OK_OPTION) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	/**
	 * This method initializes btnDone
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDone() {
		if (btnDone == null) {
			btnDone = new JButton();
			btnDone.setText("执行");
			btnDone.setPreferredSize(new Dimension(70, 25));
			btnDone.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					btnDone.setEnabled(false);
					List list = getPnMakeApplyToCustomsBill4()
							.getCommodityList();
					int num = 0;
					if (para.getApplyToCustomsBillList().getId() != null) {
						List nlist = encAction
								.findAtcMergeAfterComInfoByListID(new Request(
										CommonVars.getCurrUser()), para
										.getApplyToCustomsBillList());
						num = nlist.size();
						if ((list.size() + num) > 20) {
							JOptionPane.showMessageDialog(
									DgMakeApplyToCustoms.this, "所选商品项数:"
											+ list.size() + ",\n清单原有项数" + num
											+ " ,\n共" + (list.size() + num)
											+ "项,\n已超过20项，不能追加到现有清单!", "提示",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}
					}
					if (list == null || list.size() <= 0) {
						JOptionPane.showMessageDialog(
								DgMakeApplyToCustoms.this, "请选择进出口商品信息!", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}

					if (list.size() > 20) {
						if (JOptionPane.showConfirmDialog(
								DgMakeApplyToCustoms.this,
								"要转的申请单对应归并后商品信息超过[20]项，是否继续？", "确认",
								JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
							new Execute(list).execute();

						}
					} else {
						new Execute(list).execute();
					}

				}
			});
		}
		return btnDone;
	}

	
	/**
	 * 生成报关单清单与报关单
	 * 
	 * @author ower
	 * 
	 */
	class Execute extends SwingWorker {
		 List tempCustomsList = null;
		
		public Execute(List list) {
			this.tempCustomsList = list;
		}

		@Override
		protected Object doInBackground() throws Exception {
			return makeCusomsDeclarationWork(tempCustomsList);
		}
	}
	public Object makeCusomsDeclarationWork(List tempCustomsList){
		List list1 = new ArrayList();
		List list0 = new ArrayList();
		try {
			CommonProgress.showProgressDialog(DgMakeApplyToCustoms.this);
			CommonProgress.setMessage("系统正在执行数据，请稍后...");
			// 申请单体，报关清单后
			List mylist = encAction.makeCusomsDeclarationFromBillList(
					new Request(CommonVars.getCurrUser()), isbeianList,
					tempCustomsList, para, customs);
			// 存放清单
			list0 = (List) mylist.get(0);
			if (mylist.size() > 1) {
				// 存放报关单
				list1 = (List) mylist.get(1);
			}
			CommonProgress.closeProgressDialog();
			if (list0.size() > 0) {
				String s = "报关清单生成成功!\n";
				for (int i = 0; i < list0.size(); i++) {
					ApplyToCustomsBillList bl = (ApplyToCustomsBillList) list0
							.get(i);
					s += "清单号：" + bl.getListNo() + "\n";
				}
				if (list1.size() > 0) {
					s += "报关单生成成功!\n";
					for (int k = 0; k < list1.size(); k++) {
						CustomsDeclaration c = (CustomsDeclaration) list1
								.get(k);
						s += "报关单流水号：" + c.getSerialNumber() + "\n";
					}
				} else if (para.isCustomsDeclaration()) {
					s += "报关单生成失败!\n";
				}
				JOptionPane.showMessageDialog(DgMakeApplyToCustoms.this, s,
						"提示！", 1);
			} else {
				String s = "报关清单生成失败!\n";
				if (para.isCustomsDeclaration()) {
					s += "报关单生成失败!\n";
				}
				JOptionPane.showMessageDialog(DgMakeApplyToCustoms.this, s,
						"提示！", 1);
			}
		} catch (Exception e) {
			CommonProgress.closeProgressDialog();
			e.printStackTrace();
			JOptionPane.showMessageDialog(DgMakeApplyToCustoms.this,
					"执行数据失败：！" + e.getMessage(), "提示", 2);
		}
		dispose();
		return null;
	}
	/**
	 * This method initializes btnExit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("关闭");
			btnExit.setPreferredSize(new Dimension(70, 25));
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnExit;
	}

	/**
	 * @return Returns the PnMakeApplyToCustoms.
	 */
	private PnMakeApplyToCustoms getPnMakeApplyToCustoms() {
		if (pnMakeApplyToCustoms == null) {
			pnMakeApplyToCustoms = new PnMakeApplyToCustoms();
			if (para == null) {
				para = new MakeCusomsDeclarationParam();
			}
			pnMakeApplyToCustoms.setPara(para);
			pnMakeApplyToCustoms.setBounds(0, 0, 630, 248);
		}
		return pnMakeApplyToCustoms;
	}

	/**
	 * @return Returns the PnMakeApplyToCustoms2.
	 */
	private PnMakeApplyToCustoms2 getPnMakeApplyToCustoms2() {
		if (pnMakeApplyToCustoms2 == null) {
			pnMakeApplyToCustoms2 = new PnMakeApplyToCustoms2();
			pnMakeApplyToCustoms2.setBounds(0, 0, 630, 248);
		}
		return pnMakeApplyToCustoms2;
	}

	/**
	 * @return Returns the pnMakeCustomsEnvelopBill3.
	 */
	public PnMakeApplyToCustoms3 getPnMakeApplyToCustomsBill3() {
		if (pnMakeApplyToCustoms3 == null) {
			pnMakeApplyToCustoms3 = new PnMakeApplyToCustoms3();
			pnMakeApplyToCustoms3.setBounds(0, 0, 630, 248);
		}
		return pnMakeApplyToCustoms3;
	}

	/**
	 * @return Returns the PnMakeApplyToCustoms4.
	 */
	public PnMakeApplyToCustoms4 getPnMakeApplyToCustomsBill4() {
		if (pnMakeApplyToCustoms4 == null) {
			pnMakeApplyToCustoms4 = new PnMakeApplyToCustoms4();
			pnMakeApplyToCustoms4.setBounds(0, 0, 630, 248);
		}
		return pnMakeApplyToCustoms4;
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
	 * 生成报关清单表头数据
	 */
	private void initP0() {
		this.pnContext.removeAll();
		this.pnContext.add(this.getPnMakeApplyToCustoms(), BorderLayout.CENTER);
		this.getPnMakeApplyToCustoms().setVisible(true);
		this.lbHeadText.setText("第一步：设置生成报关清单表头数据");
	}

	/**
	 * 生成报关清单的申请单据头
	 */
	private void initP1() {
		this.pnContext.removeAll();
		this.pnContext
				.add(this.getPnMakeApplyToCustoms2(), BorderLayout.CENTER);
		this.getPnMakeApplyToCustoms2().setImpExpType(para.getImpExpType());
		this.getPnMakeApplyToCustoms2().setVisible(true);
		this.lbHeadText.setText("第二步：选择需要生成报关清单的申请单据头");
	}

	/**
	 * 生成报关清单申请单据体
	 */
	private void initP2() {
		this.pnContext.removeAll();
		this.pnContext.add(this.getPnMakeApplyToCustomsBill3(),
				BorderLayout.CENTER);
		List paralist = this.getPnMakeApplyToCustoms2().getParentList();
		if (paralist.size() > 0) {
			TempImpExpRequestBill temp = (TempImpExpRequestBill) paralist
					.get(0);
			if (para.getApplyToCustomsBillList() == null) {
				if (para.getApplyToCustomsBillList().getImpExpCIQ() != null) {
					para.getApplyToCustomsBillList().setImpExpCIQ(
							temp.getImpExpRequestBill().getIePort());
				}
			}
			para.setCustoms(temp.getImpExpRequestBill().getIePort());
			StringBuffer s = new StringBuffer();
			String memos = para.getApplyToCustomsBillList().getMemos();
			if (memos != null && !"".equals(memos)) {
				s.append(";");
			}
			for (int i = 0; i < paralist.size(); i++) {
				TempImpExpRequestBill temp1 = (TempImpExpRequestBill) paralist
						.get(i);
				if (temp1.getImpExpRequestBill().getMemos() == null
						|| "".equals(temp1.getImpExpRequestBill().getMemos())) {
					continue;
				}
				s.append(temp1.getImpExpRequestBill().getMemos() + ";");
			}
			para.getApplyToCustomsBillList().setMemos(para.getMemo() + s);
		}

		this.getPnMakeApplyToCustomsBill3().setParentList(
				this.getPnMakeApplyToCustoms2().getParentList());
		this.getPnMakeApplyToCustomsBill3().setVisible(true);
		this.lbHeadText.setText("第三步：选择需要生成报关清单申请单据体");
	}

	/**
	 * 生成报关单商品信息
	 */
	private void initP3() {
		this.pnContext.removeAll();
		this.pnContext.add(this.getPnMakeApplyToCustomsBill4(),
				BorderLayout.CENTER);
		String str = allBillList.get(allBillList.size() - 1).toString();
		// 返回list 最后一个对像是错误信息！
		allBillList.remove(allBillList.size() - 1);
		this.getPnMakeApplyToCustomsBill4().setParentList(allBillList);
		this.getPnMakeApplyToCustomsBill4().setVisible(true);
		this.lbHeadText.setText("第四步：选择需要生成报关单商品信息(电子帐册)");
		if (!str.equals("")) {
			DgErrorMessage dg = DgErrorMessage.getInstance(str);
			String m = dg.getTitle();
			dg.setTitle("信息提示");
			DgErrorMessage.showMessage();
			dg.setTitle(m);
			dg = null;
		}
	}

	/**
	 * 初始化各个界面
	 * 
	 * @param step
	 */
	private void initPn(int step) {
		switch (step) {
		case 0:
			initP0();
			break;
		case 1:
			initP1();
			break;
		case 2:
			initP2();
			break;
		}
		this.pnContext.repaint();
		this.pnContext.validate();
		setState();
	}

	/**
	 * 设置各按钮状态
	 */
	private void setState() {
		this.btnPrevious.setEnabled(this.step > 0);
		this.btnNext.setEnabled(this.step < 3);
		this.btnDone.setEnabled(this.step == 3 && isRerictCommodity);
	}

	/**
	 * 设置显示
	 */
	public void setVisible(boolean b) {
		if (b) {
			this.initPn(this.step);
		}
		super.setVisible(b);
	}

	/**
	 * @param customs
	 *            The customs to set.
	 */
	public void setCustoms(CustomsDeclaration customs) {
		this.customs = customs;
	}

	/**
	 * @return Returns the customs.
	 */
	public CustomsDeclaration getCustoms() {
		return customs;
	}

	/**
	 * This method initializes jPanel11
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel11() {
		if (jPanel11 == null) {
			jLabel19 = new JLabel();
			jLabel19.setIcon(new ImageIcon(getClass().getResource(
					"/com/bestway/bcus/client/resources/images/titlepic.jpg")));
			jLabel19.setText("");
			lbHeadText = new JLabel();
			lbHeadText.setFont(new Font("Dialog", Font.BOLD, 20));
			lbHeadText.setText(" ");
			lbHeadText.setForeground(new Color(255, 153, 0));
			jLabel17 = new JLabel();
			jLabel17
					.setIcon(new ImageIcon(
							getClass()
									.getResource(
											"/com/bestway/bcus/client/resources/images/titlepoint.jpg")));
			jLabel17.setText("");
			jPanel11 = new JPanel();
			jPanel11.setLayout(new BorderLayout());
			jPanel11.setBackground(Color.white);
			jPanel11.setBorder(BorderFactory
					.createEtchedBorder(EtchedBorder.LOWERED));
			jPanel11.add(jLabel17, java.awt.BorderLayout.WEST);
			jPanel11.add(lbHeadText, java.awt.BorderLayout.CENTER);
			jPanel11.add(jLabel19, java.awt.BorderLayout.EAST);
		}
		return jPanel11;
	}

	public MakeCusomsDeclarationParam getPara() {
		return para;
	}

	public void setPara(MakeCusomsDeclarationParam para) {
		this.para = para;
	}
}
