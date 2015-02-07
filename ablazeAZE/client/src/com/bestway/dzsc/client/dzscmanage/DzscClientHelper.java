/*
 * Created on 2005-3-28
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.client.dzscmanage;

import java.awt.Cursor;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;

import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import com.bestway.bcs.contract.entity.ContractUnitWaste;
import com.bestway.bcus.checkcancel.action.CheckCancelAction;
import com.bestway.bcus.checkcancel.entity.EmsAnalyHead;
import com.bestway.bcus.checkcancel.entity.EmsPdExg;
import com.bestway.bcus.checkcancel.entity.EmsPdImg;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgCommonQuery;
import com.bestway.bcus.client.common.DgCommonQueryPage;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.custombase.action.CustomBaseAction;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.LevyMode;
import com.bestway.bcus.enc.entity.ImpExpRequestBill;
import com.bestway.bcus.system.entity.Company;
import com.bestway.client.util.JTableListColumn;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.constant.DzscEmsType;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.dzsc.contractexe.action.DzscContractExeAction;
import com.bestway.dzsc.contractexe.entity.DzscCustomsDeclaration;
import com.bestway.dzsc.contractexe.entity.DzscCustomsDeclarationCommInfo;
import com.bestway.dzsc.dzscmanage.action.DzscAction;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsBomBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgWj;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsImgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsImgWj;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorWjHead;
import com.bestway.dzsc.innermerge.entity.DzscFourInnerMerge;

/**
 * @author // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class DzscClientHelper {
	private static DzscClientHelper dzscQuery = null;

	private DzscAction dzscAction = (DzscAction) CommonVars
			.getApplicationContext().getBean("dzscAction");

	private CustomBaseAction customBaseAction = (CustomBaseAction) CommonVars
			.getApplicationContext().getBean("customBaseAction");

	public synchronized static DzscClientHelper getInstance() {
		if (dzscQuery == null) {
			dzscQuery = new DzscClientHelper();
		}
		return dzscQuery;
	}

	/**
	 * 获得海关商品编码complex对象//联网监管//bcs使用
	 */
	public List findMaterielByImpExpRequestBillType(final String materialType,
			final ImpExpRequestBill head, String title) {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("类别", "materiel.scmCoi.name", 80));
		list.add(new JTableListColumn("料号", "materiel.ptNo", 100));
		list.add(new JTableListColumn("商品编码", "materiel.complex.code", 80));
		list.add(new JTableListColumn("工厂商品名称", "materiel.factoryName", 100));
		list.add(new JTableListColumn("工厂型号规格", "materiel.factorySpec", 100));
		list.add(new JTableListColumn("单位", "materiel.calUnit.name", 50));
		list.add(new JTableListColumn("单价", "materiel.ptPrice", 50));
		list.add(new JTableListColumn("净重", "materiel.ptNetWeight", 50));
		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(200);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//			
				DzscAction dzscAction = (DzscAction) CommonVars
						.getApplicationContext().getBean("dzscAction");
				if (property != null) {
					property = property.substring(9);
				}
				return dzscAction.findMaterielByImpExpRequestBillType(
						new Request(CommonVars.getCurrUser(), true),
						materialType, head.getId(), index, length, property,
						value, isLike, true);
			}
		};
		dgCommonQuery.setTitle(title + "(来自于物料备案)");
		DgCommonQueryPage.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;

	}

	/**
	 * 获得对照表的物料对象
	 * 
	 * @param single
	 * @param materielType
	 * @return
	 */
	public List getMaterielByTypeDzsc(String title, final String materialType,
			final ImpExpRequestBill head) {
		List<JTableListColumn> list = new ArrayList<JTableListColumn>();
		list.add(new JTableListColumn("类别", "materiel.scmCoi.name", 80));
		list.add(new JTableListColumn("料号", "materiel.ptNo", 100));
		list.add(new JTableListColumn("商品编码", "materiel.complex.code", 80));
		list.add(new JTableListColumn("工厂商品名称", "materiel.factoryName", 100));
		list.add(new JTableListColumn("工厂型号规格", "materiel.factorySpec", 100));
		list.add(new JTableListColumn("单位", "materiel.calUnit.name", 50));
		list.add(new JTableListColumn("单价", "materiel.ptPrice", 50));
		list.add(new JTableListColumn("净重", "materiel.ptNetWeight", 50));
		DgCommonQueryPage.setTableColumns(list);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			public boolean isShowAll = false;
			public JCheckBox jCheckBox = null; // 

			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				DzscAction dzscAction = (DzscAction) CommonVars
						.getApplicationContext().getBean("dzscAction");
				if (isShowAll) {
					if (property != null) {
						property = property.substring(9);
					}
					return dzscAction.findMaterielByImpExpRequestBillType(
							new Request(CommonVars.getCurrUser(), true),
							materialType, head.getId(), index, length,
							property, value, isLike, false);

				} else {
					if (property != null) {
						property = property.substring(9);
					}
					return dzscAction.findMaterielByImpExpRequestBillType(
							new Request(CommonVars.getCurrUser(), true),
							materialType, head.getId(), index, length,
							property, value, isLike, true);
				}

			}

			@Override
			protected JToolBar getJToolBar() {
				if (jToolBar == null) {
					jToolBar = new JToolBar();
					jToolBar.setFloatable(false);
					jToolBar.add(getPnCommonQueryPage());
					jToolBar.add(getJCheckBox());
				}
				return jToolBar;
			}

			private void initComponent() {
			}

			@Override
			public void setVisible(boolean b) {
				if (b) {
					pnCommonQueryPage.setInitState();
					doSomethingBeforeVisable(getJTable());
					pnCommonQueryPage.getBtnQuery().setVisible(true);
				}
				super.setVisible(b);
			}

			public JCheckBox getJCheckBox() {
				if (jCheckBox == null) {
					jCheckBox = new JCheckBox("是否过滤");
					jCheckBox.setSelected(true);
					jCheckBox
							.addActionListener(new java.awt.event.ActionListener() {
								public void actionPerformed(
										java.awt.event.ActionEvent e) {
									isShowAll = !isShowAll;
									pnCommonQueryPage.setInitState();
								}
							});
				}
				return jCheckBox;
			}

		};
		dgCommonQuery.setTitle(title + "(来自于物料备案)");
		dgCommonQuery.setSize(700, 457);
		DgCommonQueryPage.setSingleResult(false);

		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;

	}

	/**
	 * 抓取报关单资料
	 * 
	 * @param projectType
	 * @return
	 */
	public List findMaterielForDzscInnerMerge(String type, List dataSource) {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("BOM编号", "ptNo", 100));
		list.add(new JTableListColumn("商品名称", "factoryName", 100));
		list.add(new JTableListColumn("商品规格", "factorySpec", 100));
		list.add(new JTableListColumn("单价", "ptPrice", 100));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("企业物料基本资料");
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	public Complex getComplexForDzscInnerMerge() {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("商品编号", "code", 80));
		list.add(new JTableListColumn("商品名称", "name", 120));
		list.add(new JTableListColumn("第一单位编码", "firstUnit.code", 80));
		list.add(new JTableListColumn("第二单位编码", "secondUnit.code", 80));
		DgCommonQuery.setTableColumns(list);
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		List list1 = dzscAction.findComplexForInnerMerge(new Request(CommonVars
				.getCurrUser()));
		dgCommonQuery.setDataSource(list1);
		dgCommonQuery.setTitle("选择【电子手册】商品编码表");
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return (Complex) dgCommonQuery.getReturnValue();
		}
		return null;
	}

	/**
	 * 获得海关商品编码
	 */
	public List getComplexList() {
		List list = new Vector();
		list.add(new JTableListColumn("商品编号", "code", 80));
		list.add(new JTableListColumn("商品名称", "name", 100));
		list.add(new JTableListColumn("常用单位", "comUnit.name", 60));
		list.add(new JTableListColumn("第一单位编码", "firstUnit.code", 80));
		list.add(new JTableListColumn("第二单位编码", "secondUnit.code", 80));
		DgCommonQuery.setTableColumns(list);
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(customBaseAction.findComplex("", ""));
		dgCommonQuery.setTitle("选择商品编码");
		dgCommonQuery.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	private Double formatDouble(Double value) {
		if (value == null) {
			return Double.valueOf(0);
		}
		return value;
	}

	private LevyMode getLevyMode(Object code) {
		String code1 = "";
		if (code != null) {
			code1 = code.toString();
		}
		List list = dzscAction.findCustomObject(new Request(CommonVars
				.getCurrUser()), "LevyMode", code1);
		if (list.size() > 0) {
			return (LevyMode) list.get(0);
		}
		return null;
	}

	/**
	 * 新增BOM--来自料件清单
	 */
	public Object getDzscBomBillFromImgBill(boolean single, DzscEmsExgBill exg) {
		DzscAction dzscAction = (DzscAction) CommonVars.getApplicationContext()
				.getBean("dzscAction");
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		List list = null;
		list = dzscAction.findEmsPorImgBillToBill(new Request(CommonVars
				.getCurrUser()), exg);
		List<Object> dzscEmsPorBoms = new Vector<Object>();
		for (int i = 0; i < list.size(); i++) {
			DzscEmsImgBill obj = (DzscEmsImgBill) list.get(i);
			DzscEmsBomBill bom = new DzscEmsBomBill();
			bom.setComplex(obj.getComplex());
			bom.setName(obj.getName());
			bom.setSpec(obj.getSpec());
			bom.setPrice(obj.getPrice());
			// bom.setAmount((exg.getAmount())*(bom.getUnitDosage()==null?0.0:bom.getUnitDosage()));
			bom.setMoney(obj.getMoney());
			bom.setUnitWare(Double.valueOf(0));
			bom.setWare(Double.valueOf(0));
			bom.setUnitDosage(Double.valueOf(0));
			bom.setUnitWeight(obj.getUnitWeight());
			bom.setUnit(obj.getUnit());
			bom.setImgSeqNum(obj.getSeqNum()); // 料件总表序号
			bom.setCountry(obj.getCountry());
			bom.setModifyMark(ModifyMarkState.ADDED);
			dzscEmsPorBoms.add(bom);
		}
		dgCommonQuery.setDataSource(dzscEmsPorBoms);
		List<JTableListColumn> tableColumns = new Vector<JTableListColumn>();
		tableColumns.add(new JTableListColumn("料件序号", "imgSeqNum", 60,
				Integer.class));
		tableColumns.add(new JTableListColumn("海关编码", "complex.code", 80));
		tableColumns.add(new JTableListColumn("商品名称", "name", 120));
		tableColumns.add(new JTableListColumn("规格型号", "spec", 80));
		tableColumns.add(new JTableListColumn("单价", "price", 50));
		tableColumns.add(new JTableListColumn("数量", "amount", 50));
		tableColumns.add(new JTableListColumn("单位", "unit.name", 50));
		tableColumns.add(new JTableListColumn("单位重量", "unitWeight", 80));
		tableColumns.add(new JTableListColumn("原产地", "country.name", 60));
		DgCommonQuery.setTableColumns(tableColumns);
		DgCommonQuery.setSingleResult(single);
		dgCommonQuery.setTitle("料件(来自于已备案的归并关系10码资料)");
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			if (single) {
				return dgCommonQuery.getReturnValue();
			} else {
				return dgCommonQuery.getReturnList();
			}
		}
		return null;
	}

	//
	// // 获得电子手册商品编码清单中
	//
	// public Complex getBillComplex(String code, String type) {
	// DgCommonQuery dgCommonQuery = new DgCommonQuery();
	// dgCommonQuery.setDataSource(dzscAction.findFourComplex(new Request(
	// CommonVars.getCurrUser()), code, type));
	// dgCommonQuery.setVisible(true);
	// if (dgCommonQuery.isOk()) {
	// return (Complex) dgCommonQuery.getReturnValue();
	// }
	// return null;
	// }

	/**
	 * 电子手册--料件--新增料件--来自归并关系
	 */
	public List getDzscImgWjFromMergerImg(DzscEmsPorWjHead head) {
		DzscAction dzscAction = (DzscAction) CommonVars.getApplicationContext()
				.getBean("dzscAction");
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		List list = null;
		list = dzscAction.findMerger4ToWj(
				new Request(CommonVars.getCurrUser()), MaterielType.MATERIEL,
				head);
		List<Object> dzscEmsPorImgs = new Vector<Object>();
		for (int i = 0; i < list.size(); i++) {
			DzscFourInnerMerge dzscFourInnerMerge = (DzscFourInnerMerge) list
					.get(i);
			// Object[] obj = (Object[]) list.get(i);
			DzscEmsImgWj img = new DzscEmsImgWj();
			img.setDzscEmsPorWjHead(head);
			img.setFourSeqNum(dzscFourInnerMerge.getFourSeqNum());
			img.setFourComplex(dzscFourInnerMerge.getFourComplex());
			img.setFourName(dzscFourInnerMerge.getFourPtName());
			img.setFourSpec(dzscFourInnerMerge.getFourPtSpec());
			img.setFourUnit(dzscFourInnerMerge.getFourUnit());
			// img.setFourSeqNum(Integer.parseInt(obj[0].toString()));
			// img.setFourComplex((String) obj[1]);
			// img.setFourName((String) obj[2]);
			// img.setFourSpec((String) obj[3]);
			// img.setFourUnit((Unit) obj[4]);
			dzscEmsPorImgs.add(img);
		}
		dgCommonQuery.setDataSource(dzscEmsPorImgs);
		List<JTableListColumn> tableColumns = new Vector<JTableListColumn>();
		tableColumns.add(new JTableListColumn("归并序号", "fourSeqNum", 50));
		tableColumns.add(new JTableListColumn("商品编码", "fourComplex", 80));
		tableColumns.add(new JTableListColumn("商品名称", "fourName", 80));
		tableColumns.add(new JTableListColumn("规格型号", "fourSpec", 80));
		tableColumns.add(new JTableListColumn("单位", "fourUnit.name", 80));
		DgCommonQuery.setTableColumns(tableColumns);
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.setTitle("料件(来自于已备案的归并关系4码资料)");
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	/**
	 * 电子手册--成品--新增成品--来自归并
	 */
	public List getDzscExgWjFromMergerExg(DzscEmsPorWjHead head) {
		DzscAction dzscAction = (DzscAction) CommonVars.getApplicationContext()
				.getBean("dzscAction");
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		List list = null;
		list = dzscAction.findMerger4ToWj(
				new Request(CommonVars.getCurrUser()),
				MaterielType.FINISHED_PRODUCT, head);
		List<Object> dzscEmsPorExgs = new Vector<Object>();
		for (int i = 0; i < list.size(); i++) {
			DzscFourInnerMerge dzscFourInnerMerge = (DzscFourInnerMerge) list
					.get(i);
			// Object[] obj = (Object[]) list.get(i);
			DzscEmsExgWj exg = new DzscEmsExgWj();
			exg.setDzscEmsPorWjHead(head);
			exg.setFourSeqNum(dzscFourInnerMerge.getFourSeqNum());
			exg.setFourComplex(dzscFourInnerMerge.getFourComplex());
			exg.setFourName(dzscFourInnerMerge.getFourPtName());
			exg.setFourSpec(dzscFourInnerMerge.getFourPtSpec());
			exg.setFourUnit(dzscFourInnerMerge.getFourUnit());
			// exg.setFourSeqNum(Integer.parseInt(obj[0].toString()));
			// exg.setFourComplex((String) obj[1]);
			// exg.setFourName((String) obj[2]);
			// exg.setFourSpec((String) obj[3]);
			// exg.setFourUnit((Unit) obj[4]);
			dzscEmsPorExgs.add(exg);
		}
		dgCommonQuery.setDataSource(dzscEmsPorExgs);
		List<JTableListColumn> tableColumns = new Vector<JTableListColumn>();
		tableColumns.add(new JTableListColumn("归并序号", "fourSeqNum", 50));
		tableColumns.add(new JTableListColumn("商品编码", "fourComplex", 80));
		tableColumns.add(new JTableListColumn("商品名称", "fourName", 80));
		tableColumns.add(new JTableListColumn("规格型号", "fourSpec", 80));
		tableColumns.add(new JTableListColumn("单位", "fourUnit.name", 80));
		DgCommonQuery.setTableColumns(tableColumns);
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.setTitle("成品(来自于已备案的归并关系4码资料)");
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	/**
	 * 盘点料件新增
	 * 
	 * @param single
	 * @param head
	 * @return
	 */
	public Object getEmsPdImgFromMateriel(boolean single, EmsAnalyHead head) {
		CheckCancelAction checkCancelAction = (CheckCancelAction) CommonVars
				.getApplicationContext().getBean("checkCancelAction");
		List list = checkCancelAction.findMateriel(new Request(CommonVars
				.getCurrUser()), head, MaterielType.MATERIEL);
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		List<Object> emsPdImgs = new Vector<Object>();
		for (int i = 0; i < list.size(); i++) {
			Materiel obj = (Materiel) list.get(i);
			EmsPdImg img = new EmsPdImg();
			img.setPtNo(obj.getPtNo());
			img.setPtName(obj.getFactoryName());
			img.setPtSpec(obj.getFactorySpec());
			img.setCalUnit(obj.getCalUnit());
			// img.setNotProNum();
			// img.setProNum();
			img.setPdNum(Double.valueOf(0));
			emsPdImgs.add(img);
		}
		dgCommonQuery.setDataSource(emsPdImgs);
		List<JTableListColumn> tableColumns = new Vector<JTableListColumn>();
		tableColumns.add(new JTableListColumn("料号", "ptNo", 50));
		tableColumns.add(new JTableListColumn("品名", "ptName", 80));
		tableColumns.add(new JTableListColumn("规格", "ptSpec", 80));
		tableColumns.add(new JTableListColumn("计量单位", "calUnit.name", 80));
		DgCommonQuery.setTableColumns(tableColumns);
		DgCommonQuery.setSingleResult(single);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			if (single) {
				return dgCommonQuery.getReturnValue();
			} else {
				return dgCommonQuery.getReturnList();
			}
		}
		return null;
	}

	//
	// /**
	// * 抓取要进行通关备案的商品信息
	// *
	// * @param head
	// * @param type
	// * @return
	// */
	// public List findMerger10ForEmsPor(DzscEmsPorHead head, String type) {
	// DzscAction dzscAction = (DzscAction) CommonVars.getApplicationContext()
	// .getBean("dzscAction");
	// List dataSource = dzscAction.findMerger10ForEmsPor(new Request(
	// CommonVars.getCurrUser()), head, type);
	// DgCommonQuery dgCommonQuery = new DgCommonQuery();
	// dgCommonQuery.setDataSource(dataSource);
	// List<JTableListColumn> tableColumns = new Vector<JTableListColumn>();
	// tableColumns.add(new JTableListColumn("备案序号", "tenSeqNum", 60));
	// tableColumns.add(new JTableListColumn("商品编码", "complex.code", 100));
	// tableColumns.add(new JTableListColumn("商品名称", "name", 150));
	// tableColumns.add(new JTableListColumn("型号规格", "spec", 150));
	// tableColumns.add(new JTableListColumn("单位", "unit.name", 60));
	// tableColumns.add(new JTableListColumn("编码", "fourcomplex", 70));
	// DgCommonQuery.setTableColumns(tableColumns);
	// DgCommonQuery.setSingleResult(false);
	// dgCommonQuery.setVisible(true);
	// if (dgCommonQuery.isOk()) {
	// return dgCommonQuery.getReturnList();
	// }
	// return null;
	// }

	/**
	 * 盘点成品新增
	 * 
	 * @param single
	 * @param head
	 * @return
	 */
	public Object getEmsPdExgFromMateriel(boolean single, EmsAnalyHead head) {
		CheckCancelAction checkCancelAction = (CheckCancelAction) CommonVars
				.getApplicationContext().getBean("checkCancelAction");
		List list = checkCancelAction.findMateriel(new Request(CommonVars
				.getCurrUser()), head, MaterielType.FINISHED_PRODUCT);
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		List<Object> emsPdExgs = new Vector<Object>();
		for (int i = 0; i < list.size(); i++) {
			Materiel obj = (Materiel) list.get(i);
			EmsPdExg exg = new EmsPdExg();
			exg.setPtNo(obj.getPtNo());
			exg.setPtName(obj.getFactoryName());
			exg.setPtSpec(obj.getFactorySpec());
			exg.setCalUnit(obj.getCalUnit());
			exg.setPdNum(Double.valueOf(0));
			exg.setVersionNo(obj.getPtVersion());
			emsPdExgs.add(exg);
		}
		dgCommonQuery.setDataSource(emsPdExgs);
		List<JTableListColumn> tableColumns = new Vector<JTableListColumn>();
		tableColumns.add(new JTableListColumn("料号", "ptNo", 50));
		tableColumns.add(new JTableListColumn("品名", "ptName", 80));
		tableColumns.add(new JTableListColumn("规格", "ptSpec", 80));
		tableColumns.add(new JTableListColumn("计量单位", "calUnit.name", 80));
		DgCommonQuery.setTableColumns(tableColumns);
		DgCommonQuery.setSingleResult(single);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			if (single) {
				return dgCommonQuery.getReturnValue();
			} else {
				return dgCommonQuery.getReturnList();
			}
		}
		return null;
	}

	/**
	 * 报关单表体新增--料件
	 */
	public Object getDzscCustomsFromListImg(boolean single,
			DzscCustomsDeclaration customsDeclaration) {
		DzscContractExeAction contractExeAction = (DzscContractExeAction) CommonVars
				.getApplicationContext().getBean("dzsccontractExeAction");
		CustomBaseAction customBaseAction = (CustomBaseAction) CommonVars
				.getApplicationContext().getBean("customBaseAction");
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		List list = null;
		list = contractExeAction.findDzscMaterialInfo(new Request(CommonVars
				.getCurrUser()), customsDeclaration);
		List<Object> dzscEmsPorImgs = new Vector<Object>();
		for (int i = 0; i < list.size(); i++) {
			DzscEmsImgBill obj = (DzscEmsImgBill) list.get(i);
			DzscCustomsDeclarationCommInfo img = new DzscCustomsDeclarationCommInfo();
			img.setCommSerialNo(obj.getSeqNum());
			img.setComplex(obj.getComplex());
			img.setCommName(obj.getName());
			img.setDetailNote(obj.getDetailNote());// 详细型号规格
			img.setCommSpec(obj.getSpec());
			img.setCommAmount(obj.getAmount());
			img.setCommAmount(obj.getPrice());
			img.setCommTotalPrice(obj.getMoney());
			img.setUnit(obj.getUnit());
			img.setUnitWeight(obj.getUnitWeight());
			img.setCountry(obj.getCountry());
			img.setLevyMode(obj.getLevyMode());
			dzscEmsPorImgs.add(img);
		}
		dgCommonQuery.setDataSource(dzscEmsPorImgs);
		List<JTableListColumn> tableColumns = new Vector<JTableListColumn>();
		tableColumns.add(new JTableListColumn("序号", "commSerialNo", 50));
		tableColumns.add(new JTableListColumn("商品名称", "commName", 80));
		tableColumns.add(new JTableListColumn("规格型号", "commSpec", 80));
		tableColumns.add(new JTableListColumn("商品编码", "complex.code", 80));
		tableColumns.add(new JTableListColumn("单位", "unit.name", 80));
		tableColumns.add(new JTableListColumn("单价", "commUnitPrice", 80));
		tableColumns.add(new JTableListColumn("单位重量", "unitWeight", 80));
		DgCommonQuery.setTableColumns(tableColumns);
		DgCommonQuery.setSingleResult(single);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			if (single) {
				return dgCommonQuery.getReturnValue();
			} else {
				return dgCommonQuery.getReturnList();
			}
		}
		return null;
	}

	/**
	 * 报关单表体新增--成品
	 */
	public Object getDzscCustomsFromListExg(boolean single,
			DzscCustomsDeclaration customsDeclaration) {
		DzscContractExeAction contractExeAction = (DzscContractExeAction) CommonVars
				.getApplicationContext().getBean("dzsccontractExeAction");
		CustomBaseAction customBaseAction = (CustomBaseAction) CommonVars
				.getApplicationContext().getBean("customBaseAction");
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		List list = null;
		list = contractExeAction.findDzscProductInfo(new Request(CommonVars
				.getCurrUser()), customsDeclaration);
		List<Object> dzscEmsPorImgs = new Vector<Object>();
		for (int i = 0; i < list.size(); i++) {
			DzscEmsExgBill obj = (DzscEmsExgBill) list.get(i);
			DzscCustomsDeclarationCommInfo img = new DzscCustomsDeclarationCommInfo();
			img.setCommSerialNo(obj.getSeqNum());
			img.setComplex(obj.getComplex());
			img.setCommName(obj.getName());
			img.setCommSpec(obj.getSpec());
			img.setCommAmount(obj.getAmount());
			img.setCommAmount(obj.getPrice());
			img.setCommTotalPrice(obj.getMoney());
			img.setUnit(obj.getUnit());
			img.setCountry(obj.getCountry());
			img.setLevyMode(obj.getLevyMode());
			dzscEmsPorImgs.add(img);
		}
		dgCommonQuery.setDataSource(dzscEmsPorImgs);
		List<JTableListColumn> tableColumns = new Vector<JTableListColumn>();
		tableColumns.add(new JTableListColumn("序号", "commSerialNo", 50));
		tableColumns.add(new JTableListColumn("商品名称", "commName", 80));
		tableColumns.add(new JTableListColumn("规格型号", "commSpec", 80));
		tableColumns.add(new JTableListColumn("商品编码", "complex.code", 80));
		tableColumns.add(new JTableListColumn("单位", "unit.name", 80));
		tableColumns.add(new JTableListColumn("单价", "commUnitPrice", 80));
		tableColumns.add(new JTableListColumn("单位重量", "unitWeight", 80));
		DgCommonQuery.setTableColumns(tableColumns);
		DgCommonQuery.setSingleResult(single);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			if (single) {
				return dgCommonQuery.getReturnValue();
			} else {
				return dgCommonQuery.getReturnList();
			}
		}
		return null;
	}

	public DzscEmsExgBill getDzscEmsBomBillForCopy(List dataSource) {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("合同号", "dzscEmsPorHead.emsNo", 100));
		list.add(new JTableListColumn("备案序号", "seqNum", 60, Integer.class));
		list.add(new JTableListColumn("商品编码", "complex.code", 80));
		list.add(new JTableListColumn("商品名称", "name", 150));
		list.add(new JTableListColumn("型号规格", "spec", 100));
		list.add(new JTableListColumn("出口数量", "amount", 80));
		list.add(new JTableListColumn("单位", "unit.name", 40));
		list.add(new JTableListColumn("单价", "price", 70));
		list.add(new JTableListColumn("总额", "money", 70));
		list.add(new JTableListColumn("原料费", "imgMoney", 50));
		list.add(new JTableListColumn("消费国", "country.name", 70));
		list.add(new JTableListColumn("加工费单价", "machinPrice", 100));
		list.add(new JTableListColumn("补充说明", "note", 80));
		list.add(new JTableListColumn("单位净重", "unitNetWeight", 80));
		list.add(new JTableListColumn("单位毛重", "unitGrossWeight", 80));
		list.add(new JTableListColumn("合同序号", "seqNum", 80));
		list.add(new JTableListColumn("征免方式", "levyMode.name", 80));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("通关备案成品资料");
		DgCommonQuery.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return (DzscEmsExgBill) dgCommonQuery.getReturnValue();
		}
		return null;
	}

	/**
	 * 查询不在通关备案归并的料件或成品
	 * 
	 * @return
	 */
	public List findInnerMergeNotInDzscEmsPor(String type, DzscEmsPorHead head) {
		List dataSource = dzscAction.findInnerMergeNotInDzscEmsPor(new Request(
				CommonVars.getCurrUser()), type, head);
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("货号", "materiel.ptNo", 50));
		list.add(new JTableListColumn("商品编码", "materiel.complex.code", 100));
		list.add(new JTableListColumn("商品名称", "materiel.factoryName", 100));
		list.add(new JTableListColumn("规格", "materiel.factorySpec", 100));
		list.add(new JTableListColumn("单位", "materiel.calUnit.name", 50));
		list.add(new JTableListColumn("处理标记",
				"dzscTenInnerMerge.tenModifyMarkDesc", 100));
		list.add(new JTableListColumn("序号", "dzscTenInnerMerge.tenSeqNum", 50,
				Integer.class));
		list.add(new JTableListColumn("10位商品编码",
				"dzscTenInnerMerge.tenComplex.code", 100));
		list.add(new JTableListColumn("10位商品名称", "dzscTenInnerMerge.tenPtName",
				100));
		list.add(new JTableListColumn("10位商品规格", "dzscTenInnerMerge.tenPtSpec",
				100));
		list.add(new JTableListColumn("10位常用单位",
				"dzscTenInnerMerge.tenUnit.name", 100));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("通关备案成品资料");
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return new ArrayList();
	}
	
	public void printHead(JDialog dialog,
			DzscEmsPorHead head){
		if (head == null) {
			JOptionPane.showMessageDialog(dialog, "请先保存通关备案记录!!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		try {
			List list = new ArrayList();
			list.add(head);
			CustomReportDataSource ds = new CustomReportDataSource(list);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("copTrNo",head.getCopTrNo());//企业内部编号 
			parameters.put("emsNo", head.getEmsNo());//帐册编号
			//预录入号=企业内部编号 
			parameters.put("emsType", DzscEmsType.getDzscEmsType(head.getEmsType()));//账册类型
			parameters.put("trade", head.getLevyKind()==null?"":head.getLevyKind().getName());//监管方式
			parameters.put("machiningType", head.getMachiningType()==null?"":head.getMachiningType().getName());//加工种类
			
			parameters.put("tradeCode",head.getTradeCode());//经营单位代码
			parameters.put("tradeName",head.getTradeName());//经营单位
			parameters.put("machCode",head.getMachCode());//加工单位
			parameters.put("machName",head.getMachName());//加工单位
			parameters.put("outTradeCo",head.getOutTradeCo());//外商公司
			
			parameters.put("exgAmount",head.getExgAmount()==null?"0":head.getExgAmount().toString());//出口总金额
			parameters.put("productItemCount",head.getProductItemCount()==null?"":head.getProductItemCount().toString());//出口货物项数
			parameters.put("sancEmsNo",head.getSancEmsNo());//批文账册号
			parameters.put("emsApprNo",head.getEmsApprNo());//批准证编号
			parameters.put("tradeCode",head.getLevyKind()==null?"":head.getLevyKind().getCode());//征免规定
			
			parameters.put("imgAmount",head.getImgAmount()==null?"":head.getImgAmount().toString());//进口总金额
			parameters.put("materielItemCount",head.getMaterielItemCount()==null?"":head.getMaterielItemCount().toString());//进口货物项数
			parameters.put("agreementNo",head.getAgreementNo());//协议号
			//仓库体积
			//仓库面积
			
			//生产能力
			//最大周转金额
			//成本率
			//损耗率模式
			parameters.put("saveDate", head.getSaveDate()==null?"":df.format(head.getSaveDate()));//录入日期
			parameters.put("declareDate", head.getDeclareDate()==null?"":df.format(head.getDeclareDate()));//申报日期
			parameters.put("endDate", head.getEndDate()==null?"":df.format(head.getEndDate()));//结束有效期
			
			parameters.put("imContractNo", head.getImContractNo());//出口合同号
			parameters.put("ieContractNo", head.getIeContractNo());//进口合同号
			parameters.put("payMode", head.getPayMode()==null?"":head.getPayMode().getName());//保税方式
			
			parameters.put("note", head.getNote());//备注
			//进出口岸
			parameters.put("no1", "1");
			parameters.put("code1", head.getIePort1()==null?"":head.getIePort1().getCode());
			parameters.put("name1", head.getIePort1()==null?"":head.getIePort1().getName());
			
			parameters.put("no2", "2");
			parameters.put("code2", head.getIePort2()==null?"":head.getIePort2().getCode());
			parameters.put("name2", head.getIePort2()==null?"":head.getIePort2().getName());
			
			parameters.put("no3", "3");
			parameters.put("code3", head.getIePort3()==null?"":head.getIePort3().getCode());
			parameters.put("name3", head.getIePort3()==null?"":head.getIePort3().getName());
			
			parameters.put("no4", "4");
			parameters.put("code4", head.getIePort4()==null?"":head.getIePort4().getCode());
			parameters.put("name4", head.getIePort4()==null?"":head.getIePort4().getName());
			
			parameters.put("no5", "5");
			parameters.put("code5", head.getIePort5()==null?"":head.getIePort5().getCode());
			parameters.put("name5", head.getIePort5()==null?"":head.getIePort5().getName());			
			
			InputStream masterReportStream = DgDzscEmsPor.class
					.getResourceAsStream("report/head.jasper");
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					masterReportStream, parameters, ds);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
	viewer.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}

	/**
	 * 0出口成品备案清单
	 */
	public void printExportExgRecordationInventory(JDialog dialog,
			DzscEmsPorHead head) {
		if (head == null) {
			JOptionPane.showMessageDialog(dialog, "请先保存通关备案记录!!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
//		dialog.setCursor(new Cursor(Cursor.WAIT_CURSOR));
//		String contractNo = "";
//		DgDzscPrint dg = null;
//		dg = new DgDzscPrint();
//		dg.setHead(head);
//		dg.setFlag(DgDzscPrint.PRODUCT_BILL);
//		dg.setVisible(true);
//		dialog.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		try {
			String contractNo = "";
//			String number = this.tfRequestNumber.getText();
//			String card = this.tfCard.getText();
			List list = new ArrayList();
			if (head != null) {
				list = dzscAction.findEmsPorExgBill(new Request(CommonVars
						.getCurrUser()), head);
				contractNo = head.getImContractNo() == null ? "" : head
						.getImContractNo();
			}
			CustomReportDataSource ds = new CustomReportDataSource(list);
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("emsNo", head.getEmsNo());
			parameters.put("companyName", head.getMachName());
			parameters.put("companyCode", head.getCompany() == null ? ""
					: ((Company) head.getCompany()).getCode());
			parameters.put("buName", head.getTradeName());
			parameters.put("contractNo", contractNo);
			parameters.put("totalPrice", head.getExgAmount());
			//parameters.put("number", number);
			//parameters.put("card", card);
			parameters.put("currName", head.getCurr() == null ? "" : head
					.getCurr().getName());
			InputStream masterReportStream = DgDzscEmsPor.class
					.getResourceAsStream("report/ExportProductBillReport.jasper");
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					masterReportStream, parameters, ds);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 1进口料件备案清单
	 */
	public void printImportImgRecordationInventory(JDialog dialog,
			DzscEmsPorHead head) {
		if (head == null) {
			JOptionPane.showMessageDialog(dialog, "请先保存通关备案记录!!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
//		dialog.setCursor(new Cursor(Cursor.WAIT_CURSOR));
//		String contractNo = "";
//		DgDzscPrint dg = null;
//		dg = new DgDzscPrint();
//		dg.setHead(head);
//		dg.setFlag(DgDzscPrint.MATERIER_BILL);
//		dg.setVisible(true);
//		dialog.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		try {
			String contractNo = "";
//			String number = this.tfRequestNumber.getText();
//			String card = this.tfCard.getText();
			List list = new ArrayList();
			if (head != null) {
				list = dzscAction.findEmsPorImgBill(new Request(CommonVars
						.getCurrUser()), head);
				contractNo = head.getIeContractNo() == null ? "" : head
						.getIeContractNo();
			}
			CustomReportDataSource ds = new CustomReportDataSource(list);
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("emsNo", head.getEmsNo());
			parameters.put("companyName", head.getMachName());
			parameters.put("companyCode", head.getCompany() == null ? ""
					: ((Company) head.getCompany()).getCode());
			parameters.put("buName", head.getTradeName());
			parameters.put("totalPrice", head.getImgAmount());
			parameters.put("contractNo", contractNo);
//			parameters.put("number", number);
//			parameters.put("card", card);
			parameters.put("currName", head.getCurr() == null ? "" : head
					.getCurr().getName());
			InputStream masterReportStream = DgDzscEmsPor.class
					.getResourceAsStream("report/ImportMaterielBillReport.jasper");
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					masterReportStream, parameters, ds);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 2出口成品单耗备案清单
	 */
	public void printExportExgUnitConsumptionRecordationInventory(
			JDialog dialog, DzscEmsPorHead head) {
		if (head == null) {
			JOptionPane.showMessageDialog(dialog, "请先保存通关备案记录!!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		dialog.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		String contractNo = "";
		DgDzscPrint dg = null;
		try {
			List list = new ArrayList();
			String emsNo = "";
			int count = 0;
			if (head != null) {
				String parentId = head.getId();
				list = dzscAction
						.findContractUnitWasteByCustom(new Request(
								CommonVars.getCurrUser()), parentId, -1, -1);
				emsNo = head.getEmsApprNo() == null ? "" : head
						.getEmsApprNo();
				count = dzscAction.findDzscEmsExgBillCount(new Request(
						CommonVars.getCurrUser()), head.getId());
				System.out.println(count);
			}
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("emsNo", emsNo);
			parameters.put("isOverprint", new Boolean(false));
			parameters.put("companyName", head.getMachName());
			parameters.put("count", count);
			CustomReportDataSource ds = new CustomReportDataSource(list);
			ds.setMaximumFractionDigits(9);
			InputStream masterReportStream = DgDzscEmsPor.class
					.getResourceAsStream("report/MaterielUnitWasteReport.jasper");
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					masterReportStream, parameters, ds);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		dialog.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	/**
	 * 3加工合同预申报合同组成表
	 */
	public void printProcessContractBeforeHandContractCompose(JDialog dialog,
			DzscEmsPorHead head) {
		if (head == null) {
			JOptionPane.showMessageDialog(dialog, "请先保存通关备案记录!!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		dialog.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		String contractNo = "";
		DgDzscPrint dg = null;
		printPutOnRecordsContract(head, true);
		dialog.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	/**
	 * 4加工合同变更预申报合同组成表
	 */
	public void printProcessContractChangeBeforeHandContractCompose(
			JDialog dialog, DzscEmsPorHead head) {
		if (head == null) {
			JOptionPane.showMessageDialog(dialog, "请先保存通关备案记录!!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		dialog.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		String contractNo = "";
		DgDzscPrint dg = null;
		printPutOnRecordsContract(head, false);
		dialog.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	/**
	 * 5出口成品备案清单变更表
	 */
	public void printExportExgRecordationInventoryChange(JDialog dialog,
			DzscEmsPorHead head) {
		if (head == null) {
			JOptionPane.showMessageDialog(dialog, "请先保存通关备案记录!!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
//		dialog.setCursor(new Cursor(Cursor.WAIT_CURSOR));
//		String contractNo = "";
//		DgDzscPrint dg = null;
//		dg = new DgDzscPrint();
//		dg.setHead(head);
//		dg.setFlag(DgDzscPrint.PRODUCT_BILL_CHANGE);
//		dg.setVisible(true);
//		dialog.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		try {
			String contractNo = "";
//			String number = this.tfRequestNumber.getText();
//			String card = this.tfCard.getText();
			List list = new ArrayList();
			List changeList = new ArrayList();
			if (head != null) {
				list = dzscAction.findEmsPorExgBill(new Request(CommonVars
						.getCurrUser()), head);
				contractNo = head.getIeContractNo() == null ? "" : head
						.getIeContractNo();
			}
			for(int i = 0;i < list.size(); i++){
				DzscEmsExgBill dzscEmsExgBill = (DzscEmsExgBill) list.get(i);
				if(dzscEmsExgBill.getModifyMark().equals(ModifyMarkState.MODIFIED) ||
						dzscEmsExgBill.getModifyMark().equals(ModifyMarkState.ADDED)){
					changeList.add(dzscEmsExgBill);
				}
			}
			CustomReportDataSource ds = new CustomReportDataSource(changeList);
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("emsNo", head.getEmsNo());
			parameters.put("companyName", head.getMachName());
			parameters.put("companyCode", head.getCompany() == null ? ""
					: ((Company) head.getCompany()).getCode());
			parameters.put("buName", head.getTradeName());
			parameters.put("contractNo", contractNo);
			parameters.put("totalPrice", head.getImgAmount());
//			parameters.put("number", number);
//			parameters.put("card", card);
			parameters.put("currName", head.getCurr() == null ? "" : head
					.getCurr().getName());
			InputStream masterReportStream = DgDzscEmsPor.class
					.getResourceAsStream("report/ExportProductBillChangeReport.jasper");
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					masterReportStream, parameters, ds);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 6进口料件备案清单变更表
	 */
	public void printImportImgRecordationInventoryChange(JDialog dialog,
			DzscEmsPorHead head) {
		if (head == null) {
			JOptionPane.showMessageDialog(dialog, "请先保存通关备案记录!!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
//		dialog.setCursor(new Cursor(Cursor.WAIT_CURSOR));
//		String contractNo = "";
//		DgDzscPrint dg = null;
//		dg = new DgDzscPrint();
//		dg.setHead(head);
//		dg.setFlag(DgDzscPrint.MATERIEL_BILL_CHANGE);
//		dg.setVisible(true);
//		dialog.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		try {
			String contractNo = "";
//			String number = this.tfRequestNumber.getText();
//			String card = this.tfCard.getText();
			List list = new ArrayList();
			List changeList = new ArrayList();
			if (head != null) {
				list = dzscAction.findEmsPorImgBill(new Request(CommonVars
						.getCurrUser()), head);
				contractNo = head.getIeContractNo() == null ? "" : head
						.getIeContractNo();
			}
			
			for(int i = 0;i < list.size(); i++){
				DzscEmsImgBill dzscEmsImgBill = (DzscEmsImgBill) list.get(i);
				if(dzscEmsImgBill.getModifyMark().equals(ModifyMarkState.MODIFIED) ||
						dzscEmsImgBill.getModifyMark().equals(ModifyMarkState.ADDED)){
					changeList.add(dzscEmsImgBill);
				}
			}
			CustomReportDataSource ds = new CustomReportDataSource(changeList);
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("emsNo", head.getEmsNo());
			parameters.put("companyName", head.getMachName());
			parameters.put("companyCode", head.getCompany() == null ? ""
					: ((Company) head.getCompany()).getCode());
			parameters.put("buName", head.getTradeName());
			parameters.put("contractNo", contractNo);
			parameters.put("totalPrice", head.getImgAmount());
//			parameters.put("number", number);				
//			parameters.put("card", card);
			parameters.put("currName", head.getCurr() == null ? "" : head
					.getCurr().getName());
			InputStream masterReportStream = DgDzscEmsPor.class
					.getResourceAsStream("report/ImportMaterielBillChangeReport.jasper");
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					masterReportStream, parameters, ds);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 7出口成品单耗备案清单变更表
	 */
	public void printExportExgUnitConsumptionRecordationInventoryChange(
			JDialog dialog, DzscEmsPorHead head) {
		if (head == null) {
			JOptionPane.showMessageDialog(dialog, "请先保存通关备案记录!!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		dialog.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		String contractNo = "";
		DgDzscPrint dg = null;
		try {

			List list = new ArrayList();
			String emsNo = "";
			int count = 0;
			if (head != null) {
				String parentId = head.getId();
				list = dzscAction.findContractUnitWasteByCustomForEdit(new Request(
						CommonVars.getCurrUser()), parentId, -1, -1);
				emsNo = head.getImContractNo() == null ? "" : head
						.getImContractNo();
				count = dzscAction.findDzscEmsExgBillCount(new Request(
						CommonVars.getCurrUser()), head.getId());
			}
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("emsNo", emsNo);
			parameters.put("isOverprint", new Boolean(false));
			parameters.put("companyName", head.getMachName());
			parameters.put("count", count);
			parameters.put("title", "出口成品单耗备案清单变更表");
			CustomReportDataSource ds = new CustomReportDataSource(list);
			ds.setMaximumFractionDigits(9);
			InputStream masterReportStream = DgDzscEmsPor.class
					.getResourceAsStream("report/MaterielUnitWasteReport.jasper");
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					masterReportStream, parameters, ds);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		dialog.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	/**
	 * 8出口成品加工费表
	 */
	public void printExportExgProcessCost(JDialog dialog, DzscEmsPorHead head) {
		if (head == null) {
			JOptionPane.showMessageDialog(dialog, "请先保存通关备案记录!!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		dialog.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		String contractNo = "";
		DgDzscPrint dg = null;
//		try {
//			List list = new ArrayList();
//			if (head != null) {
//				list = dzscAction.findDzscEmsImgAndExgUsedDiffAmount(
//						new Request(CommonVars.getCurrUser()), head);
//			}
//			CustomReportDataSource ds = new CustomReportDataSource(list);
//			Map<String, Object> parameters = new HashMap<String, Object>();
//			parameters.put("companyName", head.getMachName());
//			parameters.put("emsNo", head.getEmsNo());
//			InputStream masterReportStream = DgDzscEmsPor.class
//					.getResourceAsStream("report/ExpProcuctProcessPrice.jasper");
//			JasperPrint jasperPrint = JasperFillManager.fillReport(
//					masterReportStream, parameters, ds);
//			DgReportViewer viewer = new DgReportViewer(jasperPrint);
//			viewer.setVisible(true);
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//		
		try {
			List list = new ArrayList();
			if (head != null) {
				list = dzscAction.findEmsPorExgBill(new Request(CommonVars
						.getCurrUser()), head);
				contractNo = head.getImContractNo() == null ? "" : head
						.getImContractNo();
			}
			CustomReportDataSource ds = new CustomReportDataSource(list);
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("companyName", head.getMachName());
			parameters.put("emsNo", head.getEmsNo());
			InputStream masterReportStream = DgDzscEmsPor.class
					.getResourceAsStream("report/ExpProcuctProcessPrice.jasper");
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					masterReportStream, parameters, ds);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		dialog.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	/**
	 * 9料件进出平衡检查表
	 */
	public void printImgInletOutletBalanceCheck(JDialog dialog,
			DzscEmsPorHead head) {
		if (head == null) {
			JOptionPane.showMessageDialog(dialog, "请先保存通关备案记录!!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		dialog.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		String contractNo = "";
		DgDzscPrint dg = null;
		try {
			List list = new ArrayList();
			if (head != null) {
				list = dzscAction.findDzscEmsImgAndExgUsedDiffAmount(
						new Request(CommonVars.getCurrUser()), head);
			}
			CustomReportDataSource ds = new CustomReportDataSource(list);
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("companyName", head.getMachName());
			parameters.put("emsNo", head.getEmsNo());
			InputStream masterReportStream = DgDzscEmsPor.class
					.getResourceAsStream("report/ImgAndExgUsedDiffAmount.jasper");
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					masterReportStream, parameters, ds);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		dialog.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	/**
	 * 报表打印
	 */
	public void printDzscEmsPorReport(JDialog dialog, DzscEmsPorHead head,
			int flag) {
		if (head == null) {
			JOptionPane.showMessageDialog(dialog, "请先保存通关备案记录!!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		dialog.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		String contractNo = "";
		DgDzscPrint dg = null;
		switch (flag) {
		case 0: // 出口成品备案清单
			dg = new DgDzscPrint();
			dg.setHead(head);
			dg.setFlag(DgDzscPrint.PRODUCT_BILL);
			dg.setVisible(true);
			break;
		case 1: // 进口料件备案清单(新)
			dg = new DgDzscPrint();
			dg.setHead(head);
			dg.setFlag(DgDzscPrint.MATERIER_BILL);
			dg.setVisible(true);
			break;
		case 2: // 出口成品单耗备案清单(新)
			try {
				List list = new ArrayList();
				String emsNo = "";
				int count = 0;
				if (head != null) {
					String parentId = head.getId();
					list = dzscAction.findContractUnitWasteByCustomForEdit(
							new Request(CommonVars.getCurrUser()), parentId,
							-1, -1);
					emsNo = head.getImContractNo() == null ? "" : head
							.getImContractNo();
					count = dzscAction.findDzscEmsExgBillCount(new Request(
							CommonVars.getCurrUser()), head.getId());
				}
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("emsNo", emsNo);
				parameters.put("isOverprint", new Boolean(false));
				parameters.put("companyName", head.getMachName());
				parameters.put("count", count);
				CustomReportDataSource ds = new CustomReportDataSource(list);
				ds.setMaximumFractionDigits(9);
				InputStream masterReportStream = DgDzscEmsPor.class
						.getResourceAsStream("report/MaterielUnitWasteReport.jasper");
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters, ds);
				DgReportViewer viewer = new DgReportViewer(jasperPrint);
				viewer.setVisible(true);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			break;
		case 3: // 加工合同预申报合同组成表
			printPutOnRecordsContract(head, true);
			break;
		case 4: // 加工合同变更预申报合同组成表
			printPutOnRecordsContract(head, false);
			break;
		case 5: // 出口成品备案清单变更表
			dg = new DgDzscPrint();
			dg.setHead(head);
			dg.setFlag(DgDzscPrint.PRODUCT_BILL_CHANGE);
			dg.setVisible(true);
			break;
		case 6: // 进口料件备案清单变更表
			dg = new DgDzscPrint();
			dg.setHead(head);
			dg.setFlag(DgDzscPrint.MATERIEL_BILL_CHANGE);
			dg.setVisible(true);
			break;
		case 7: // 出口成品单耗备案清单变更表(新)
			try {

				List list = new ArrayList();
				String emsNo = "";
				int count = 0;
				if (head != null) {
					String parentId = head.getId();
					list = dzscAction.findContractUnitWasteByCustom(
							new Request(CommonVars.getCurrUser()), parentId,
							-1, -1);
					emsNo = head.getImContractNo() == null ? "" : head
							.getImContractNo();
					count = dzscAction.findDzscEmsExgBillCount(new Request(
							CommonVars.getCurrUser()), head.getId());
				}
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("emsNo", emsNo);
				parameters.put("isOverprint", new Boolean(false));
				parameters.put("companyName", head.getMachName());
				parameters.put("count", count);
				parameters.put("title", "出口成品单耗备案清单变更表");
				CustomReportDataSource ds = new CustomReportDataSource(list);
				ds.setMaximumFractionDigits(9);
				InputStream masterReportStream = DgDzscEmsPor.class
						.getResourceAsStream("report/MaterielUnitWasteReport.jasper");
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters, ds);
				DgReportViewer viewer = new DgReportViewer(jasperPrint);
				viewer.setVisible(true);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			break;
		case 8: // 出口成品加工费表(新)
			try {
				List list = new ArrayList();
				if (head != null) {
					list = dzscAction.findEmsPorExgBill(new Request(CommonVars
							.getCurrUser()), head);
					contractNo = head.getImContractNo() == null ? "" : head
							.getImContractNo();
				}
				CustomReportDataSource ds = new CustomReportDataSource(list);
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("companyName", head.getMachName());
				parameters.put("emsNo", head.getEmsNo());
				InputStream masterReportStream = DgDzscEmsPor.class
						.getResourceAsStream("report/ExpProcuctProcessPrice.jasper");
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters, ds);
				DgReportViewer viewer = new DgReportViewer(jasperPrint);
				viewer.setVisible(true);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			break;
		case 9: // 料件进出平衡检查表
			try {
				List list = new ArrayList();
				if (head != null) {
					list = dzscAction.findDzscEmsImgAndExgUsedDiffAmount(
							new Request(CommonVars.getCurrUser()), head);
				}
				CustomReportDataSource ds = new CustomReportDataSource(list);
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("companyName", head.getMachName());
				parameters.put("emsNo", head.getEmsNo());
				InputStream masterReportStream = DgDzscEmsPor.class
						.getResourceAsStream("report/ImgAndExgUsedDiffAmount.jasper");
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters, ds);
				DgReportViewer viewer = new DgReportViewer(jasperPrint);
				viewer.setVisible(true);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			break;
		}
		dialog.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	/**
	 * 加工合同预申报合同组成表
	 * 
	 * @param isPutOnRecordsContract
	 *            ==true 是合同备案,否则是预申报合同
	 */
	private void printPutOnRecordsContract(DzscEmsPorHead head,
			boolean isPutOnRecordsContract) {
		try {
			// ==========================
			// 合同主报表 + 出品成品明细表
			// ==========================

			//
			// 合同备案情况主报表数据源
			//            
			String emsNo = "";
			// if (isPutOnRecordsContract == true) {
			emsNo = "手册编号: " + head.getEmsNo();
			// } else if (isPutOnRecordsContract == false) {
			// emsNo = "预录入号: " + contract.getPreEmsNo();
			// }
			List<DzscEmsPorHead> contractList = new ArrayList<DzscEmsPorHead>();
			contractList.add(head);
			CustomReportDataSource ds = new CustomReportDataSource(contractList);

			//
			// 成品明细列表数据源
			//
			List finishedProductList = new ArrayList();
			if (head != null) {
				finishedProductList = dzscAction.findEmsPorExgBill(new Request(
						CommonVars.getCurrUser()), head);
			}
			CustomReportDataSource finishedProductDataSource = new CustomReportDataSource(
					finishedProductList);
			//
			// 成品明细子报表
			//               
			InputStream exportFinishedProductSubReportStream = DgDzscEmsPor.class
					.getResourceAsStream("report/BillFinishedProductSubReport.jasper");
			JasperReport exportFinishedProductSubReport = (JasperReport) JRLoader
					.loadObject(exportFinishedProductSubReportStream);

			//
			// 合同备案情况主报表
			//
			Map<String, Object> contractMap = new HashMap<String, Object>();
			contractMap.put("exportFinishedProductSubReport",
					exportFinishedProductSubReport);
			contractMap.put("subReportDataSource", finishedProductDataSource);
			contractMap.put("isPutOnRecordsContract", isPutOnRecordsContract);
			contractMap.put("emsNo", emsNo);
			InputStream masterReportStream = DgDzscEmsPor.class
					.getResourceAsStream("report/BillContractPutOnRecordReport.jasper");
			JasperPrint jasperPrintContract = JasperFillManager.fillReport(
					masterReportStream, contractMap, ds);

			// ====================================
			// 进口料件报表 把进口料件报表加入到总报表中
			// ====================================
			List contractImgList = new ArrayList();
			if (head != null) {
				contractImgList = dzscAction.findEmsPorImgBill(new Request(
						CommonVars.getCurrUser()), head);
			}
			CustomReportDataSource contractImgDataSource = new CustomReportDataSource(
					contractImgList);
			Map<String, Object> contractImgMap = new HashMap<String, Object>();
			contractImgMap.put("emsNo", emsNo);
			contractImgMap.put("beforePageCount", jasperPrintContract
					.getPages().size());
			InputStream contractImgReportStream = DgDzscEmsPor.class
					.getResourceAsStream("report/BillMaterielBillReport.jasper");
			JasperPrint jasperPrintContractImg = JasperFillManager.fillReport(
					contractImgReportStream, contractImgMap,
					contractImgDataSource);
			int count = jasperPrintContractImg.getPages().size();
			for (int i = 0; i < count; i++) {
				jasperPrintContract
						.addPage((JRPrintPage) jasperPrintContractImg
								.getPages().get(i));
			}
			// ========================================
			// 成品对应料件单损耗情况表 并把报表加入到总报表中
			// ========================================
			List contractUnitWasteList = new ArrayList();
			int countExg = 0;
			if (head != null) {
				String parentId = head.getId();
				contractUnitWasteList = dzscAction
						.findContractUnitWasteByCustom(new Request(CommonVars
								.getCurrUser()), parentId, -1, -1);
				countExg = this.dzscAction.findDzscEmsExgBillCount(new Request(
						CommonVars.getCurrUser()), head.getId());
			}
			CustomReportDataSource contractUnitWasteDataSource = new CustomReportDataSource(
					contractUnitWasteList);
			contractUnitWasteDataSource.setMaximumFractionDigits(9);
			Map<String, Object> contractUnitWasteMap = new HashMap<String, Object>();
			contractUnitWasteMap.put("emsNo", emsNo);
			contractUnitWasteMap.put("beforePageCount", jasperPrintContract
					.getPages().size());
			contractUnitWasteMap.put("count", countExg);
			InputStream contractUnitWasteReportStream = DgDzscEmsPor.class
					.getResourceAsStream("report/MaterielUnitWasteSubReport.jasper");
			JasperPrint jasperPrintContractUnitWaste = JasperFillManager
					.fillReport(contractUnitWasteReportStream,
							contractUnitWasteMap, contractUnitWasteDataSource);
			int size = jasperPrintContractUnitWaste.getPages().size();
			for (int i = 0; i < size; i++) {
				jasperPrintContract
						.addPage((JRPrintPage) jasperPrintContractUnitWaste
								.getPages().get(i));
			}
			//
			// 显示所有报表
			//
			DgReportViewer viewer = new DgReportViewer(jasperPrintContract);
			viewer.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}