/*
 * Created on 2005-12-9
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.client.contract;

import java.awt.Cursor;
import java.awt.Dimension;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;

import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import com.bestway.bcs.bcsinnermerge.action.BcsInnerMergeAction;
import com.bestway.bcs.bcsinnermerge.entity.BcsTenInnerMerge;
import com.bestway.bcs.client.contract.report.ContractReportVars;
import com.bestway.bcs.client.contractcav.DgContractCav;
import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contract.entity.ContractBom;
import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcs.contract.entity.ContractImg;
import com.bestway.bcs.contract.entity.ContractUnitWaste;
import com.bestway.bcs.dictpor.entity.BcsDictPorHead;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgCommonQuery;
import com.bestway.bcus.client.common.DgCommonQueryContract;
import com.bestway.bcus.client.common.DgCommonQueryPage;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.custombase.action.CustomBaseAction;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.client.util.JTableListColumn;
import com.bestway.common.CommonUtils;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.constant.ContractKind;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.ManageObject;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.CurrRate;

/**
 * 电子化手册通关备案手册打印专用类
 * 
 * @author ？ 贺巍于2008年12月5日添加部分注释
 */
@SuppressWarnings("unchecked")
public class BcsClientHelper {
	
	private JCheckBox  cb1 = null;
	private JCheckBox  cb2 = null;
	/**
	 * BcsClientHelper静态实例
	 */
	private static BcsClientHelper clientHelper = null;

	/**
	 * 类方法用来返回上面的实例
	 * 
	 * @return
	 */
	public static BcsClientHelper getInstance() {
		if (clientHelper == null) {
			clientHelper = new BcsClientHelper();
		}
		return clientHelper;
	}

	/**
	 * 获得海关商品编码complex对象//联网监管//bcs使用
	 */
	public List getComplex() {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("编码", "code", 100));
		list.add(new JTableListColumn("名称", "name", 150));
		list.add(new JTableListColumn("第一法定单位", "firstUnit.name", 100));
		list.add(new JTableListColumn("第二法定单位", "secondUnit.name", 100));
		DgCommonQueryPage.setTableColumns(list);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//			
				CustomBaseAction customBaseAction = (CustomBaseAction) CommonVars
						.getApplicationContext().getBean("customBaseAction");
				return customBaseAction.findComplex(new Request(CommonVars
						.getCurrUser(), true), index, length, property, value,
						isLike);
			}
		};

		dgCommonQuery.setTitle("商品编码查询");
		DgCommonQueryPage.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;

	}

	/**
	 * 选择合同成品
	 * 
	 * @param dataSource
	 * @return
	 */
	public List findContractExg(List dataSource) {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("成品序号", "seqNum", 80));
		list.add(new JTableListColumn("商品编码", "complex.name", 100));
		list.add(new JTableListColumn("商品名称", "name", 100));
		list.add(new JTableListColumn("型号规格", "spec", 100));
		list.add(new JTableListColumn("计量单位", "unit.name", 80));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("选择合同成品");
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	/**
	 * 复制合同成品表
	 * 
	 * @param dataSource
	 * @return
	 */
	public ContractExg getContractExgForCopy(List dataSource,String corrEmsNo) {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("成品序号", "seqNum", 60, Integer.class));
		list.add(new JTableListColumn("商品编码", "complex.code", 80));
		list.add(new JTableListColumn("商品名称", "name", 150));
		list.add(new JTableListColumn("规格型号", "spec", 100));
		list.add(new JTableListColumn("出口数量", "exportAmount", 100));
		list.add(new JTableListColumn("单位", "unit.name", 80));
		list.add(new JTableListColumn("单价", "unitPrice", 100));
		list.add(new JTableListColumn("总额", "totalPrice", 100));
		list.add(new JTableListColumn("法定数量", "legalAmount", 100));
		list.add(new JTableListColumn("法定单位", "legalUnit.name", 100));
		list.add(new JTableListColumn("原料费", "materialFee", 100));
		list.add(new JTableListColumn("消费国", "country.name", 100));
		list.add(new JTableListColumn("加工费单价", "processUnitPrice", 100));
		list.add(new JTableListColumn("加工费总价", "processTotalPrice", 100));
		list.add(new JTableListColumn("单位毛重", "unitGrossWeight", 100));
		list.add(new JTableListColumn("单位净重", "unitNetWeight", 100));
		list.add(new JTableListColumn("征减免税方式", "levyMode.name", 100));
		list.add(new JTableListColumn("凭证序号", "credenceNo", 60));
		list.add(new JTableListColumn("备注", "note", 100));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQueryContract dgCommonQuery = new DgCommonQueryContract(corrEmsNo,dataSource);
//		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("合同成品资料");
		DgCommonQuery.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return (ContractExg) dgCommonQuery.getReturnValue();
		}
		return null;
	}

	/**
	 * 获得分页查询的物料(来自内部归并的过滤)
	 * 
	 * @param dataSource
	 * @return
	 */
	public Object getMaterielNotInBcsInnerMerge(final String materielType) {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("料号", "ptNo", 100));
		list.add(new JTableListColumn("物料编码", "complex.code", 120));
		list.add(new JTableListColumn("商品名称", "factoryName", 100));// 工厂名称
		list.add(new JTableListColumn("商品规格", "factorySpec", 160));
		list.add(new JTableListColumn("详细型号规格", "ptDeSpec", 160));
		list.add(new JTableListColumn("英文名称", "ptEnglishName", 100));
		list.add(new JTableListColumn("英文规格", "ptEnglishSpec", 100));
		list.add(new JTableListColumn("单位", "calUnit.name", 50));
		list.add(new JTableListColumn("单价", "ptPrice", 70));
		list.add(new JTableListColumn("净重", "ptNetWeight", 70));
		list.add(new JTableListColumn("单位折算", "unitConvert", 70));
		DgCommonQueryPage.setTableColumns(list);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			public boolean isShowAll = false;
			public boolean includeIsUsed = false;
			public JCheckBox cbShowAll = null;
			public JCheckBox cbIncludeIsUsed = null;

			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//			
				BcsInnerMergeAction bcsInnerMergeAction = (BcsInnerMergeAction) CommonVars
						.getApplicationContext().getBean("bcsInnerMergeAction");
				if (isShowAll) {
					return bcsInnerMergeAction.findMaterielForBcsInnerMerge(
							new Request(CommonVars.getCurrUser(), true),
							materielType, -1, -1, property, value,
							isLike, includeIsUsed);
				} else {
					return bcsInnerMergeAction.findMaterielForBcsInnerMerge(
							new Request(CommonVars.getCurrUser(), true),
							materielType, index, length, property, value,
							isLike, includeIsUsed);
				}
			}

			protected JToolBar getJToolBar() {
				if (jToolBar == null) {
					jToolBar = new JToolBar();
					jToolBar.setFloatable(false);
					jToolBar.add(getPnCommonQueryPage());
					jToolBar.add(getJCheckBox());
					jToolBar.add(getCbIncludeIsUsed());
				}
				return jToolBar;
			}

			public JCheckBox getJCheckBox() {
				if (cbShowAll == null) {
					cbShowAll = new JCheckBox("显示全部");
					cbShowAll
							.addActionListener(new java.awt.event.ActionListener() {
								public void actionPerformed(
										java.awt.event.ActionEvent e) {
									isShowAll = ((JCheckBox)e.getSource()).isSelected();
									pnCommonQueryPage.setInitState();
								}
							});
				}
				return cbShowAll;
			}
			
			public JCheckBox getCbIncludeIsUsed() {
				if (cbIncludeIsUsed == null) {
					cbIncludeIsUsed = new JCheckBox("显示已使用");
					cbIncludeIsUsed
							.addActionListener(new java.awt.event.ActionListener() {
								public void actionPerformed(
										java.awt.event.ActionEvent e) {
									includeIsUsed = ((JCheckBox)e.getSource()).isSelected();
									pnCommonQueryPage.setInitState();
								}
							});
				}

				return cbIncludeIsUsed;
			}
		};

		dgCommonQuery.setTitle("物料查询");
		dgCommonQuery.setSize(900, 600);
		DgCommonQueryPage.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	/**
	 * 查找不存在Bcs内部归并的物料来自料件类型
	 * 
	 * @param materielType
	 * @return
	 */
	public List<BcsTenInnerMerge> findBcsInnerMergeNotContract(
			final Contract contract, final String materielType,
			final boolean isInnerMerge) {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("归并序号", "seqNum", 60));
		list.add(new JTableListColumn("编码", "complex.code", 80));
		list.add(new JTableListColumn("商品名称", "name", 100));
		list.add(new JTableListColumn("商品规格", "spec", 100));
		list.add(new JTableListColumn("常用单位", "comUnit.name", 80));
		DgCommonQueryPage.setTableColumns(list);
		// DgCommonQueryPage.setLength(200);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//			
				ContractAction contractAction = (ContractAction) CommonVars
						.getApplicationContext().getBean("contractAction");
				return contractAction.findBcsTenInnerMergeNotContract(
						new Request(CommonVars.getCurrUser(), true), contract,
						materielType, index, length, property, value, isLike,
						isInnerMerge);
			}
		};
		String dataFromSpec = "";
		if (isInnerMerge) {
			dataFromSpec = "来自于物料与报关对应表";
		} else {
			dataFromSpec = "来自于报关商品资料";
		}
		if (MaterielType.MATERIEL.equals(materielType)) {
			dgCommonQuery.setTitle("料件(" + dataFromSpec + ")");
		} else if (MaterielType.FINISHED_PRODUCT.equals(materielType)) {
			dgCommonQuery.setTitle("成品(" + dataFromSpec + ")");
		}
		DgCommonQueryPage.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	/**
	 * 查找不存在Bcs内部归并的物料来自料件类型
	 * 
	 * @param materielType
	 * @return
	 */
	public List findBcsDictPorImgExgNotContract(final String materielType,
			final String contractId, final String dictPorEmsNo,
			final boolean isFilt) {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("备案序号", "seqNum", 60));
		list.add(new JTableListColumn("编码", "complex.code", 80));
		list.add(new JTableListColumn("商品名称", "commName", 100));
		list.add(new JTableListColumn("商品规格", "commSpec", 100));
		list.add(new JTableListColumn("常用单位", "comUnit.name", 80));
		DgCommonQueryPage.setTableColumns(list);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//			
				ContractAction contractAction = (ContractAction) CommonVars
						.getApplicationContext().getBean("contractAction");
				return contractAction.findDictPorImgExgNotInContract(
						new Request(CommonVars.getCurrUser(), true),
						materielType, contractId, dictPorEmsNo, isFilt);
			}
		};
		if (MaterielType.MATERIEL.equals(materielType)) {
			dgCommonQuery.setTitle("备案资料库--料件查询");
		} else if (MaterielType.FINISHED_PRODUCT.equals(materielType)) {
			dgCommonQuery.setTitle("备案资料库--成品查询");
		}
		DgCommonQueryPage.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	/**
	 * 查找不存在Bcs内部归并的物料来自料件类型
	 * 
	 * @param materielType
	 * @return
	 */
	public List findBcsDictPorImgNotContractBom(final String exgId,
			final String dictPorEmsNo) {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("备案序号", "seqNum", 60));
		list.add(new JTableListColumn("编码", "complex.code", 80));
		list.add(new JTableListColumn("商品名称", "commName", 100));
		list.add(new JTableListColumn("商品规格", "commSpec", 100));
		list.add(new JTableListColumn("常用单位", "comUnit.name", 80));
		DgCommonQueryPage.setTableColumns(list);
		DgCommonQueryPage dgCommonQuery = new DgCommonQueryPage() {
			@Override
			public List getDataSource(int index, int length, String property,
					Object value, boolean isLike) {
				//
				// 分页查询的方法
				//			
				ContractAction contractAction = (ContractAction) CommonVars
						.getApplicationContext().getBean("contractAction");
				return contractAction.findDictPorImgNotInContractBom(
						new Request(CommonVars.getCurrUser(), true), exgId,
						dictPorEmsNo);
			}
		};
		dgCommonQuery.setTitle("备案资料库--料件查询");
		DgCommonQueryPage.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	/**
	 * 备案资料库表头资料
	 * 
	 * @param dataSource
	 * @return
	 */
	public BcsDictPorHead findExingBcsDictPorHead(List dataSource) {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("内部编号", "copEmsNo", 200));
		list.add(new JTableListColumn("备案资料库编号", "dictPorEmsNo", 200));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("选择备案资料库");
		DgCommonQuery.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return (BcsDictPorHead) dgCommonQuery.getReturnValue();
		}
		return null;
	}

	/**
	 * 打印套打出口成品表(新)
	 * 
	 * @param dialog
	 * @param contract
	 */
	public void coverPrintExportExgNew(JDialog dialog, Contract contract,
			boolean isChageTable) {
		ContractAction contractAction = (ContractAction) CommonVars
				.getApplicationContext().getBean("contractAction");
		if (contract == null) {
			JOptionPane.showMessageDialog(dialog, "请先保存合同记录!!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		// dialog.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		String emsNo = "";
		String title = "";// 报表名称
		try {
			List list = new ArrayList();
			if (contract != null && !isChageTable) {
				String parentId = contract.getId();
				list = contractAction.findContractExgByParentId(new Request(
						CommonVars.getCurrUser()), parentId);
				if (list == null || list.size() == 0) {
					JOptionPane.showMessageDialog(dialog, "不存在出口成品!");
					return;
				}

			} else if (contract != null && isChageTable) {
				String parentId = contract.getId();
				list = contractAction.findContractExgByParentIdByModifyMark(
						new Request(CommonVars.getCurrUser()), parentId);
				if (list == null || list.size() == 0) {
					JOptionPane.showMessageDialog(dialog, "出口成品没有变更!");
					return;
				}
			}
			emsNo = contract.getExpContractNo() == null ? "" : contract
					.getExpContractNo();
			InputStream subReportStream = DgContract.class
					.getResourceAsStream("report/NewFinishedProductSubReport.jasper");
			JasperReport subReport = (JasperReport) JRLoader
					.loadObject(subReportStream);
			CustomReportDataSource ds = new CustomReportDataSource(list);
			List<Double> currRateList = getHDKAndUSDRate(contract, dialog);
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("title", title);
			parameters.put("isOverprint", new Boolean(true));
			parameters.put("rateHDK", currRateList.get(0));
			parameters.put("rateUSD", currRateList.get(1));
			parameters.put("list", list);
			parameters.put("emsNo", emsNo);
			parameters.put("companyName", contract.getMachName());
			parameters.put("currName", contract.getCurr() == null ? ""
					: contract.getCurr().getName());
			parameters.put("subReport", subReport);
			parameters.put("subReportDataSource", ds);
			InputStream masterReportStream = DgContract.class
					.getResourceAsStream("report/NewExportFinishedProductReport.jasper");
			List<String> tempList = new ArrayList<String>();
			tempList.add("tempData");
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					masterReportStream, parameters, new CustomReportDataSource(
							tempList));
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 打印套打出口成品变更表
	 * 
	 * @param dialog
	 * @param contract
	 */
	public void coverPrintExportChange(JDialog dialog, Contract contract) {
		ContractAction contractAction = (ContractAction) CommonVars
				.getApplicationContext().getBean("contractAction");
		if (contract == null) {
			JOptionPane.showMessageDialog(dialog, "请先保存合同记录!!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		try {
			if (contract != null) {
				List<ContractExg> listAfter = new ArrayList<ContractExg>();
				List<ContractExg> listBefore = new ArrayList<ContractExg>();
				String parentId = contract.getId();
				listAfter = contractAction
						.findContractExgChangeAfterByContract(new Request(
								CommonVars.getCurrUser()), contract);
				listBefore = contractAction
						.findContractExgChangeBeforeByContract(new Request(
								CommonVars.getCurrUser()), contract);
				System.out.println("size=" + listAfter.size());
				if (listAfter == null || listAfter.size() == 0) {
					JOptionPane.showMessageDialog(dialog, "出口成品没有变更!");
					return;
				}
				Double totalPriceAfter = 0d;
				Double totalPriceBefore = 0d;
				if (listAfter != null) {
					for (ContractExg exg : listAfter) {
						totalPriceAfter += CommonUtils.getDoubleExceptNull(exg
								.getTotalPrice());
					}
				}
				if (listBefore != null) {
					for (ContractExg exg : listBefore) {
						totalPriceBefore += CommonUtils.getDoubleExceptNull(exg
								.getTotalPrice());
					}
				} else {
					listBefore = new ArrayList<ContractExg>();
					// listBefore.add(new ContractExg());
				}

				InputStream subReportStream1 = DgContract.class
						.getResourceAsStream("report/ExportFinishedProductChangeReport2Sub.jasper");
				JasperReport subAfterReport = (JasperReport) JRLoader
						.loadObject(subReportStream1);
				CustomReportDataSource dsAfter = new CustomReportDataSource(
						listAfter);
				InputStream subReportStream = DgContract.class
						.getResourceAsStream("report/ExportFinishedProductChangeReport1Sub.jasper");
				JasperReport subBeforeReport = (JasperReport) JRLoader
						.loadObject(subReportStream);
				CustomReportDataSource dsBefore = new CustomReportDataSource(
						listBefore);

				InputStream masterReportStream = DgContract.class
						.getResourceAsStream("report/ExportFinishedProductChangeReport1.jasper");
				List list = new ArrayList();
				list.add("temp");
				CustomReportDataSource ds = new CustomReportDataSource(list);
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("companyName", contract.getCompany().getName());
				parameters.put("currName", contract.getCurr().getName());
				parameters.put("contractNo", "("
						+ contract.getImpContractNo().replace("/", ")"));
				parameters.put("subAfterReport", subAfterReport);
				parameters.put("subAfterList", dsAfter);
				parameters.put("subBeforeReport", subBeforeReport);
				parameters.put("subBoforeList", dsBefore);
				parameters.put("totalPriceAfter", CommonUtils
						.formatDoubleByDigit(totalPriceAfter, 5));
				parameters.put("totalPriceBefore", CommonUtils
						.formatDoubleByDigit(totalPriceBefore, 5));
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters, ds);
				DgReportViewer viewer = new DgReportViewer(jasperPrint);
				viewer.setVisible(true);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 打印套打进口料件变更表
	 * 
	 * @param dialog
	 * @param contract
	 */
	public void coverPrintImportChange(JDialog dialog, Contract contract) {
		ContractAction contractAction = (ContractAction) CommonVars
				.getApplicationContext().getBean("contractAction");
		if (contract == null) {
			JOptionPane.showMessageDialog(dialog, "请先保存合同记录!!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		try {
			if (contract != null) {
				List<ContractImg> listAfter = new ArrayList<ContractImg>();
				List<ContractImg> listBefore = new ArrayList<ContractImg>();
				String parentId = contract.getId();
				listAfter = contractAction
						.findContractImgChangeAfterByContract(new Request(
								CommonVars.getCurrUser()), contract);
				listBefore = contractAction
						.findContractImgChangeBeforeByContract(new Request(
								CommonVars.getCurrUser()), contract);
				// System.out.println("size="+list.size());
				if (listAfter == null || listAfter.size() == 0) {
					JOptionPane.showMessageDialog(dialog, "进口料件没有变更!");
					return;
				}
				Double totalPriceAfter = 0d;
				Double totalPriceBefore = 0d;
				if (listAfter != null) {
					for (ContractImg exg : listAfter) {
						totalPriceAfter += CommonUtils.getDoubleExceptNull(exg
								.getTotalPrice());
					}
				}
				if (listBefore != null) {
					for (ContractImg exg : listBefore) {
						totalPriceBefore += CommonUtils.getDoubleExceptNull(exg
								.getTotalPrice());
					}
				} else {
					listBefore = new ArrayList<ContractImg>();
					// listBefore.add(new ContractImg());
				}
				// System.out.println("变前"+listBefore.size());
				InputStream subReportStream1 = DgContract.class
						.getResourceAsStream("report/ImportFinishedMaterielChangeReport2Sub.jasper");
				JasperReport subAfterReport = (JasperReport) JRLoader
						.loadObject(subReportStream1);
				CustomReportDataSource dsAfter = new CustomReportDataSource(
						listAfter);
				InputStream subReportStream = DgContract.class
						.getResourceAsStream("report/ImportFinishedMaterielChangeReport1Sub.jasper");
				JasperReport subBeforeReport = (JasperReport) JRLoader
						.loadObject(subReportStream);
				CustomReportDataSource dsBefore = new CustomReportDataSource(
						listBefore);

				InputStream masterReportStream = DgContract.class
						.getResourceAsStream("report/ImportFinishedMaterielChangeReport1.jasper");
				List list = new ArrayList();
				list.add("temp");
				CustomReportDataSource ds = new CustomReportDataSource(list);
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("companyName", contract.getCompany().getName());
				parameters.put("currName", contract.getCurr().getName());
				parameters.put("contractNo", "("
						+ contract.getImpContractNo().replace("/", ")"));
				parameters.put("subAfterReport", subAfterReport);
				parameters.put("subAfterList", dsAfter);
				parameters.put("subBeforeReport", subBeforeReport);
				parameters.put("subBoforeList", dsBefore);
				parameters.put("totalPriceAfter", CommonUtils
						.formatDoubleByDigit(totalPriceAfter, 5));
				parameters.put("totalPriceBefore", CommonUtils
						.formatDoubleByDigit(totalPriceBefore, 5));
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters, ds);
				DgReportViewer viewer = new DgReportViewer(jasperPrint);
				viewer.setVisible(true);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 打印套打出口成品变更表
	 * 
	 * @param dialog
	 * @param contract
	 */
	public void coverPrintImportChange(JDialog dialog, Contract contract,
			boolean isTaoDa) {
		ContractAction contractAction = (ContractAction) CommonVars
				.getApplicationContext().getBean("contractAction");
		if (contract == null) {
			JOptionPane.showMessageDialog(dialog, "请先保存合同记录!!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		try {
			if (contract != null) {
				List list = new ArrayList();
				String parentId = contract.getId();
				list = contractAction.findConractImgChangedByContract(
						new Request(CommonVars.getCurrUser()), contract);
				// System.out.println("size="+list.size());
				if (list == null || list.size() == 0) {
					JOptionPane.showMessageDialog(dialog, "进口料件没有变更!");
					return;
				}
				InputStream masterReportStream = DgContract.class
						.getResourceAsStream("report/ImportChangeReport.jasper");
				CustomReportDataSource ds = new CustomReportDataSource(list);
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("isTaoDa", isTaoDa);
				parameters.put("companyName", contract.getCompany().getName());
				parameters.put("currName", contract.getCurr().getName());
				parameters.put("contractNo", contract.getImpContractNo());
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters, ds);
				DgReportViewer viewer = new DgReportViewer(jasperPrint);
				viewer.setVisible(true);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 非套打出口成品表(新)
	 * 
	 * @param dialog
	 * @param contract
	 * @param isChang
	 *            是否打印更新表
	 */
	public void nonCoverPrintExportExgNew(JDialog dialog, Contract contract,
			boolean isChang) {
		ContractAction contractAction = (ContractAction) CommonVars
				.getApplicationContext().getBean("contractAction");
		if (contract == null) {
			JOptionPane.showMessageDialog(dialog, "请先保存合同记录!!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		// dialog.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		String emsNo = "";
		String title = "";// 报表名称
		try {
			List list = new ArrayList();
			if (contract != null && !isChang) {
				String parentId = contract.getId();
				list = contractAction.findContractExgByParentId(new Request(
						CommonVars.getCurrUser()), parentId);
				title = "出口成品表";
				if (list == null || list.size() == 0) {
					JOptionPane.showMessageDialog(dialog, "不存在出口成品!");
					return;
				}
			} else if (contract != null && isChang) {
				String parentId = contract.getId();
				list = contractAction.findContractExgByParentIdByModifyMark(
						new Request(CommonVars.getCurrUser()), parentId);
				title = "出口成品变更表";
				if (list == null || list.size() == 0) {
					JOptionPane.showMessageDialog(dialog, "出口成品没有变更!");
					return;
				}
			} else {
				return;
			}
			emsNo = contract.getExpContractNo() == null ? "" : contract
					.getExpContractNo();
			InputStream subReportStream = DgContract.class
					.getResourceAsStream("report/NewFinishedProductSubReport.jasper");
			JasperReport subReport = (JasperReport) JRLoader
					.loadObject(subReportStream);
			CustomReportDataSource ds = new CustomReportDataSource(list);
			List<Double> currRateList = this.getHDKAndUSDRate(contract, dialog);
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("title", title);
			parameters.put("isOverprint", new Boolean(false));
			parameters.put("rateHDK", currRateList.get(0));
			parameters.put("rateUSD", currRateList.get(1));
			System.out.println("rateHDK=" + currRateList.get(0));
			System.out.println("rateUSD=" + currRateList.get(1));
			parameters.put("list", list);
			parameters.put("emsNo", emsNo);
			parameters.put("companyName", contract.getMachName());
			parameters.put("currName", contract.getCurr() == null ? ""
					: contract.getCurr().getName());
			parameters.put("subReport", subReport);
			parameters.put("subReportDataSource", ds);
			InputStream masterReportStream = DgContract.class
					.getResourceAsStream("report/NewExportFinishedProductReport.jasper");
			List<String> tempList = new ArrayList<String>();
			tempList.add("tempData");
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					masterReportStream, parameters, new CustomReportDataSource(
							tempList));
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 打印套打进口料件表(新)
	 * 
	 * @param dialog
	 * @param contract
	 */
	public void coverPrintImportImgNew(JDialog dialog, Contract contract,
			boolean isChangeTable) {
		ContractAction contractAction = (ContractAction) CommonVars
				.getApplicationContext().getBean("contractAction");
		if (contract == null) {
			JOptionPane.showMessageDialog(dialog, "请先保存合同记录!!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		// dialog.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		String emsNo = "";
		String title = "";
		try {
			List list = new ArrayList();
			if (contract != null && !isChangeTable) {
				String parentId = contract.getId();
				list = contractAction.findContractImgByParentId(new Request(
						CommonVars.getCurrUser()), parentId);
				if (list == null || list.size() == 0) {
					JOptionPane.showMessageDialog(dialog, "不存在进口料件!");
					return;
				}
			} else if (contract != null && isChangeTable) {
				String parentId = contract.getId();
				list = contractAction.findContractImgByContractIdByModifyMark(
						new Request(CommonVars.getCurrUser()), parentId);
				if (list == null || list.size() == 0) {
					JOptionPane.showMessageDialog(dialog, "进口料件没有变更!");
					return;
				}
			}
			emsNo = contract.getImpContractNo() == null ? "" : contract
					.getImpContractNo();
			InputStream subReportStream = DgContract.class
					.getResourceAsStream("report/NewMaterielSubReport.jasper");
			JasperReport subReport = (JasperReport) JRLoader
					.loadObject(subReportStream);
			CustomReportDataSource ds = new CustomReportDataSource(list);
			List<Double> currRateList = this.getHDKAndUSDRate(contract, dialog);
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("title", title);
			parameters.put("isOverprint", new Boolean(true));
			parameters.put("rateHDK", currRateList.get(0));
			parameters.put("rateUSD", currRateList.get(1));
			parameters.put("list", list);
			parameters.put("emsNo", emsNo);
			parameters.put("companyName", contract.getMachName());
			parameters.put("currName", contract.getCurr() == null ? ""
					: contract.getCurr().getName());
			parameters.put("subReport", subReport);
			parameters.put("subReportDataSource", ds);
			InputStream masterReportStream = DgContract.class
					.getResourceAsStream("report/NewImportMaterielReport.jasper");
			List<String> tempList = new ArrayList<String>();
			tempList.add("tempData");
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					masterReportStream, parameters, new CustomReportDataSource(
							tempList));
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 打印非套打进口料件表(新)
	 * 
	 * @param dialog
	 * @param contract
	 */
	public void nonCoverPrintImportImgNew(JDialog dialog, Contract contract,
			boolean isChangeTable) {
		ContractAction contractAction = (ContractAction) CommonVars
				.getApplicationContext().getBean("contractAction");
		if (contract == null) {
			JOptionPane.showMessageDialog(dialog, "请先保存合同记录!!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		// dialog.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		String emsNo = "";
		String title = "";
		try {
			List list = new ArrayList();
			if (contract != null && !isChangeTable) {
				String parentId = contract.getId();
				list = contractAction.findContractImgByParentId(new Request(
						CommonVars.getCurrUser()), parentId);
				title = "进口料件表";
				if (list == null || list.size() == 0) {
					JOptionPane.showMessageDialog(dialog, "不存在进口料件!");
					return;
				}
			} else if (contract != null && isChangeTable) {
				String parentId = contract.getId();
				list = contractAction.findContractImgByContractIdByModifyMark(
						new Request(CommonVars.getCurrUser()), parentId);
				title = "进口料件变更表";
				if (list == null || list.size() == 0) {
					JOptionPane.showMessageDialog(dialog, "进口料件没有变更!");
					return;
				}
			}
			emsNo = contract.getImpContractNo() == null ? "" : contract
					.getImpContractNo();
			InputStream subReportStream = DgContract.class
					.getResourceAsStream("report/NewMaterielSubReport.jasper");
			JasperReport subReport = (JasperReport) JRLoader
					.loadObject(subReportStream);
			CustomReportDataSource ds = new CustomReportDataSource(list);
			List<Double> currRateList = this.getHDKAndUSDRate(contract, dialog);
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("title", title);
			parameters.put("isOverprint", new Boolean(false));
			parameters.put("rateHDK", currRateList.get(0));
			parameters.put("rateUSD", currRateList.get(1));
			parameters.put("list", list);
			parameters.put("emsNo", emsNo);
			parameters.put("companyName", contract.getMachName());
			parameters.put("currName", contract.getCurr() == null ? ""
					: contract.getCurr().getName());
			parameters.put("subReport", subReport);
			parameters.put("subReportDataSource", ds);
			InputStream masterReportStream = DgContract.class
					.getResourceAsStream("report/NewImportMaterielReport.jasper");
			List<String> tempList = new ArrayList<String>();
			tempList.add("tempData");
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					masterReportStream, parameters, new CustomReportDataSource(
							tempList));
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 打印套打成品表
	 * 
	 * @param dialog
	 * @param contract
	 */
	public void coverPrintExg(JDialog dialog, Contract contract) {
		ContractAction contractAction = (ContractAction) CommonVars
				.getApplicationContext().getBean("contractAction");
		if (contract == null) {
			JOptionPane.showMessageDialog(dialog, "请先保存合同记录!!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		// dialog.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		String contractNO = "";
		try {
			List list = new ArrayList();
			if (contract != null) {
				String parentId = contract.getId();
				list = contractAction.findContractExgByParentId(new Request(
						CommonVars.getCurrUser()), parentId);
				contractNO = contract.getExpContractNo() == null ? "" : contract
						.getExpContractNo();
			}
			InputStream subReportStream = DgContract.class
					.getResourceAsStream("report/FinishedProductSubReport.jasper");
			JasperReport subReport = (JasperReport) JRLoader
					.loadObject(subReportStream);
			CustomReportDataSource ds = new CustomReportDataSource(list);
			List<Double> currRateList = getHDKAndUSDRate(contract, dialog);
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("isOverprint", new Boolean(true));
			parameters.put("rateHDK", currRateList.get(0));
			parameters.put("rateUSD", currRateList.get(1));
			parameters.put("list", list);
			parameters.put("contractNO", contractNO);
			parameters.put("companyName", contract.getMachName());
			parameters.put("currName", contract.getCurr() == null ? ""
					: contract.getCurr().getName());
			parameters.put("subReport", subReport);
			parameters.put("subReportDataSource", ds);
			InputStream masterReportStream = DgContract.class
					.getResourceAsStream("report/ExportFinishedProductReport.jasper");
			List<String> tempList = new ArrayList<String>();
			tempList.add("tempData");
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					masterReportStream, parameters, new CustomReportDataSource(
							tempList));
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 打印非套打成品表
	 * 
	 * @param dialog
	 * @param contract
	 */
	public void nonCoverPrintExg(JDialog dialog, Contract contract) {
		ContractAction contractAction = (ContractAction) CommonVars
				.getApplicationContext().getBean("contractAction");
		if (contract == null) {
			JOptionPane.showMessageDialog(dialog, "请先保存合同记录!!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		// dialog.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		String contractNo = "";
		try {
			List list = new ArrayList();
			if (contract != null) {
				String parentId = contract.getId();
				list = contractAction.findContractExgByParentId(new Request(
						CommonVars.getCurrUser()), parentId);
				contractNo = contract.getExpContractNo() == null ? "" : contract
						.getExpContractNo();
		
			}
			InputStream subReportStream = DgContract.class
					.getResourceAsStream("report/FinishedProductSubReport.jasper");
			JasperReport subReport = (JasperReport) JRLoader
					.loadObject(subReportStream);
			CustomReportDataSource ds = new CustomReportDataSource(list);
			List<Double> currRateList = this.getHDKAndUSDRate(contract, dialog);
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("isOverprint", new Boolean(false));
			parameters.put("rateHDK", currRateList.get(0));
			parameters.put("rateUSD", currRateList.get(1));
			parameters.put("list", list);
			parameters.put("contractNo", contractNo);
			parameters.put("companyName", contract.getMachName());
			parameters.put("currName", contract.getCurr() == null ? ""
					: contract.getCurr().getName());
			parameters.put("subReport", subReport);
			parameters.put("subReportDataSource", ds);
			InputStream masterReportStream = DgContract.class
					.getResourceAsStream("report/ExportFinishedProductReport.jasper");
			List<String> tempList = new ArrayList<String>();
			tempList.add("tempData");
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					masterReportStream, parameters, new CustomReportDataSource(
							tempList));
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 打印套打料件表
	 * 
	 * @param dialog
	 * @param contract
	 */
	public void coverPrintImg(JDialog dialog, Contract contract) {
		ContractAction contractAction = (ContractAction) CommonVars
				.getApplicationContext().getBean("contractAction");
		if (contract == null) {
			JOptionPane.showMessageDialog(dialog, "请先保存合同记录!!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		// dialog.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		String contractNo = "";
		try {
			List list = new ArrayList();
			if (contract != null) {
				String parentId = contract.getId();
				list = contractAction.findContractImgByParentId(new Request(
						CommonVars.getCurrUser()), parentId);
				contractNo = contract.getImpContractNo() == null ? "" : contract
						.getImpContractNo();
			}
			InputStream subReportStream = DgContract.class
					.getResourceAsStream("report/MaterielSubReport.jasper");
			JasperReport subReport = (JasperReport) JRLoader
					.loadObject(subReportStream);
			CustomReportDataSource ds = new CustomReportDataSource(list);
			List<Double> currRateList = this.getHDKAndUSDRate(contract, dialog);
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("isOverprint", new Boolean(true));
			parameters.put("rateHDK", currRateList.get(0));
			parameters.put("rateUSD", currRateList.get(1));
			parameters.put("list", list);
			parameters.put("contractNo", contractNo);
			parameters.put("companyName", contract.getMachName());
			parameters.put("currName", contract.getCurr() == null ? ""
					: contract.getCurr().getName());
			parameters.put("subReport", subReport);
			parameters.put("subReportDataSource", ds);
			InputStream masterReportStream = DgContract.class
					.getResourceAsStream("report/ImportMaterielReport.jasper");
			List<String> tempList = new ArrayList<String>();
			tempList.add("tempData");
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					masterReportStream, parameters, new CustomReportDataSource(
							tempList));
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 打印非套打料件表
	 * 
	 * @param dialog
	 * @param contract
	 */
	public void nonCoverPrintImg(JDialog dialog, Contract contract) {
		ContractAction contractAction = (ContractAction) CommonVars
				.getApplicationContext().getBean("contractAction");
		if (contract == null) {
			JOptionPane.showMessageDialog(dialog, "请先保存合同记录!!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		// dialog.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		String contractNo = "";
		try {
			List list = new ArrayList();
			if (contract != null) {
				String parentId = contract.getId();
				list = contractAction.findContractImgByParentId(new Request(
						CommonVars.getCurrUser()), parentId);
				contractNo = contract.getImpContractNo() == null ? "" : contract
						.getImpContractNo();
			}
			InputStream subReportStream = DgContract.class
					.getResourceAsStream("report/MaterielSubReport.jasper");
			JasperReport subReport = (JasperReport) JRLoader
					.loadObject(subReportStream);
			CustomReportDataSource ds = new CustomReportDataSource(list);
			List<Double> currRateList = this.getHDKAndUSDRate(contract, dialog);
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("isOverprint", new Boolean(false));
			parameters.put("rateHDK", currRateList.get(0));
			parameters.put("rateUSD", currRateList.get(1));
			parameters.put("list", list);
			parameters.put("contractNo", contractNo);
			parameters.put("companyName", contract.getMachName());
			parameters.put("currName", contract.getCurr() == null ? ""
					: contract.getCurr().getName());
			parameters.put("subReport", subReport);
			parameters.put("subReportDataSource", ds);
			InputStream masterReportStream = DgContract.class
					.getResourceAsStream("report/ImportMaterielReport.jasper");
			List<String> tempList = new ArrayList<String>();
			tempList.add("tempData");
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					masterReportStream, parameters, new CustomReportDataSource(
							tempList));
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 打印套打单耗表
	 * 
	 * @param dialog
	 * @param contract
	 */
	public void coverPrintUnitConsumption(JDialog dialog, Contract contract,
			boolean isChangeTable) {
		ContractAction contractAction = (ContractAction) CommonVars
				.getApplicationContext().getBean("contractAction");
		if (contract == null) {
			JOptionPane.showMessageDialog(dialog, "请先保存合同记录!!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		// dialog.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		String contractNo="";
		try {
			Boolean isPrintZero = true;
			Object[] message = new Object[1];
			message[0] = getCb1();
			int ms = JOptionPane.showOptionDialog(dialog, message,
					"请选择！",
					JOptionPane.CLOSED_OPTION, // option type
					JOptionPane.INFORMATION_MESSAGE, null,
					new Object[] { "确定" }, "确定");	
			if (!getCb1().isSelected()) {
				isPrintZero = false;
			}
			List list = new ArrayList();
			List squNoList = new ArrayList();
			int count = 0;
			if (contract != null && !isChangeTable) {
				String parentId = contract.getId();
				list = contractAction.findContractUnitWaste(new Request(
						CommonVars.getCurrUser()), parentId, -1, -1);
				contractNo = contract.getExpContractNo() == null ? "" : contract
						.getExpContractNo();
				count = contractAction.findContractExgCount(new Request(
						CommonVars.getCurrUser()), contract.getId());
				if (list == null || list.size() == 0) {
					JOptionPane.showMessageDialog(dialog, "不存在料件单损耗!");
					return;
				}
				int groupId = 0;
				for (int i = 0, size = list.size(); i < size; i++) {
					ContractUnitWaste c = (ContractUnitWaste) list.get(i);
					if (c.getGroupId() == groupId) {
						System.out.println(c.getGroupId());
						squNoList.add(c.getExgSeqNum1() == null ? "" : c
								.getExgSeqNum1().toString());
						squNoList.add(c.getExgSeqNum2() == null ? "" : c
								.getExgSeqNum2().toString());
						squNoList.add(c.getExgSeqNum3() == null ? "" : c
								.getExgSeqNum3().toString());
						squNoList.add(c.getExgSeqNum4() == null ? "" : c
								.getExgSeqNum4().toString());
						squNoList.add(c.getExgSeqNum5() == null ? "" : c
								.getExgSeqNum5().toString());
						squNoList.add(c.getExgSeqNum6() == null ? "" : c
								.getExgSeqNum6().toString());
						groupId++;
					}
				}
			} else if (contract != null && isChangeTable) {
				String parentId = contract.getId();
				list = contractAction
						.findContractUnitWasteByModifyMark(new Request(
								CommonVars.getCurrUser()), parentId, -1, -1);
				count = contractAction.findContractExgCount(new Request(
						CommonVars.getCurrUser()), contract.getId());
				if (list == null || list.size() == 0) {
					JOptionPane.showMessageDialog(dialog, "料件单损耗没有变更!");
					return;
				}
				int groupId = 0;
				for (int i = 0, size = list.size(); i < size; i++) {
					ContractUnitWaste c = (ContractUnitWaste) list.get(i);
					if (c.getGroupId() == groupId) {
						System.out.println(c.getGroupId());
						squNoList.add(c.getExgSeqNum1() == null ? "" : c
								.getExgSeqNum1().toString());
						squNoList.add(c.getExgSeqNum2() == null ? "" : c
								.getExgSeqNum2().toString());
						squNoList.add(c.getExgSeqNum3() == null ? "" : c
								.getExgSeqNum3().toString());
						squNoList.add(c.getExgSeqNum4() == null ? "" : c
								.getExgSeqNum4().toString());
						squNoList.add(c.getExgSeqNum5() == null ? "" : c
								.getExgSeqNum5().toString());
						squNoList.add(c.getExgSeqNum6() == null ? "" : c
								.getExgSeqNum6().toString());
						groupId++;
					}
				}
			}
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("contractNo", contractNo);
			parameters.put("isOverprint", new Boolean(true));
			parameters.put("companyName", contract.getMachName());
			parameters.put("count", count);
			parameters.put("isChange", false);
			parameters.put("groupSeq", new ArrayList());
			parameters.put("squNoList", squNoList);
			parameters.put("isPrintZero",isPrintZero);
			CustomReportDataSource ds = new CustomReportDataSource(list);
			ds.setMaximumFractionDigits(9);
			InputStream masterReportStream = DgContract.class
					.getResourceAsStream("report/MaterielUnitWasteReport.jasper");
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					masterReportStream, parameters, ds);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 打印非套打单耗表
	 * 
	 * @param dialog
	 * @param contract
	 */
	public void nonCoverPrintUnitConsumption(JDialog dialog, Contract contract,
			boolean isChangeTable) {
		ContractAction contractAction = (ContractAction) CommonVars
				.getApplicationContext().getBean("contractAction");
		if (contract == null) {
			JOptionPane.showMessageDialog(dialog, "请先保存合同记录!!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		// dialog.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		String emsNo = "";
		String title = "";
		String contractNo="";
		try {
			Boolean isPrintZero = true;
			boolean isShowZero = false;
			Object[] message = new Object[1];
			message[0] = getCb1();
			//message[1] = getCb2();
			int ms = JOptionPane.showOptionDialog(dialog, message,
					"请选择！",
					JOptionPane.CLOSED_OPTION, // option type
					JOptionPane.INFORMATION_MESSAGE, null,
					new Object[] { "确定" }, "确定");	
			if (!getCb1().isSelected()) {
				isPrintZero = false;
			}
			
			/*if (!getCb2().isSelected()) {
				isShowZero = true;
			}*/
			List list = new ArrayList();
			List squNoList = new ArrayList();
			int count = 0;
			if (contract != null && !isChangeTable) {
				String parentId = contract.getId();
				list = contractAction.findContractUnitWaste(new Request(
						CommonVars.getCurrUser()), parentId, -1, -1);
				contractNo = contract.getExpContractNo() == null ? "" : contract
						.getExpContractNo();
				emsNo = contract.getEmsNo() == null ? "" : contract
						.getEmsNo();
				count = contractAction.findContractExgCount(new Request(
						CommonVars.getCurrUser()), contract.getId());
				title = "单损耗表";
				if (list == null || list.size() == 0) {
					JOptionPane.showMessageDialog(dialog, "不存在单损耗表!");
					return;
				}
				int groupId = 0;
				for (int i = 0, size = list.size(); i < size; i++) {
					ContractUnitWaste c = (ContractUnitWaste) list.get(i);
					if (c.getGroupId() == groupId) {
						System.out.println(c.getGroupId());
						squNoList.add(c.getExgSeqNum1() == null ? "" : c
								.getExgSeqNum1().toString());
						squNoList.add(c.getExgSeqNum2() == null ? "" : c
								.getExgSeqNum2().toString());
						squNoList.add(c.getExgSeqNum3() == null ? "" : c
								.getExgSeqNum3().toString());
						squNoList.add(c.getExgSeqNum4() == null ? "" : c
								.getExgSeqNum4().toString());
						squNoList.add(c.getExgSeqNum5() == null ? "" : c
								.getExgSeqNum5().toString());
						squNoList.add(c.getExgSeqNum6() == null ? "" : c
								.getExgSeqNum6().toString());
						groupId++;
					}
				}
				for (int i = 0; i < squNoList.size(); i++) {
					System.out.println(squNoList.get(i).toString());
				}
			} else if (contract != null && isChangeTable) {
				String parentId = contract.getId();
				list = contractAction
						.findContractUnitWasteByModifyMark(new Request(
								CommonVars.getCurrUser()), parentId, -1, -1);
				emsNo = contract.getExpContractNo() == null ? "" : contract
						.getExpContractNo();
				count = contractAction.findContractExgCount(new Request(
						CommonVars.getCurrUser()), contract.getId());
				title = "单损耗变更表";
				if (list == null || list.size() == 0) {
					JOptionPane.showMessageDialog(dialog, "单损耗没有变更!");
					return;
				}
				int groupId = 0;
				for (int i = 0, size = list.size(); i < size; i++) {
					ContractUnitWaste c = (ContractUnitWaste) list.get(i);
					if (c.getGroupId() == groupId) {
						System.out.println(c.getGroupId());
						squNoList.add(c.getExgSeqNum1() == null ? "" : c
								.getExgSeqNum1().toString());
						squNoList.add(c.getExgSeqNum2() == null ? "" : c
								.getExgSeqNum2().toString());
						squNoList.add(c.getExgSeqNum3() == null ? "" : c
								.getExgSeqNum3().toString());
						squNoList.add(c.getExgSeqNum4() == null ? "" : c
								.getExgSeqNum4().toString());
						squNoList.add(c.getExgSeqNum5() == null ? "" : c
								.getExgSeqNum5().toString());
						squNoList.add(c.getExgSeqNum6() == null ? "" : c
								.getExgSeqNum6().toString());
						groupId++;
					}
				}
				for (int i = 0; i < squNoList.size(); i++) {
					System.out.println(squNoList.get(i).toString());
				}
			}
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("title", title);
			parameters.put("emsNo", emsNo);
			parameters.put("contractNo", contractNo);
			parameters.put("isOverprint", new Boolean(false));
			parameters.put("companyName", contract.getMachName());
			parameters.put("count", count);
			parameters.put("isChange", false);
			parameters.put("groupSeq", new ArrayList());
			parameters.put("squNoList", squNoList);
			parameters.put("isPrintZero", isPrintZero);
			parameters.put("isShowZero", isShowZero);
			parameters.put("squNoList", squNoList);
			CustomReportDataSource ds = new CustomReportDataSource(list);
			ds.setMaximumFractionDigits(9);
			InputStream masterReportStream = DgContract.class
					.getResourceAsStream("report/MaterielUnitWasteReport.jasper");
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					masterReportStream, parameters, ds);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 打印加工合同备案情况表
	 * 
	 * @param dialog
	 * @param contract
	 */
	public void printPutOnRecordContract(JDialog dialog, Contract contract) {
		ContractAction contractAction = (ContractAction) CommonVars
				.getApplicationContext().getBean("contractAction");
		if (contract == null) {
			JOptionPane.showMessageDialog(dialog, "请先保存合同记录!!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		// dialog.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		String emsNo = "";
		printPutOnRecordsContract(contract, true);
	}

	/**
	 * 打印预申报合同组成表
	 * 
	 * @param dialog
	 * @param contract
	 */
	public void printPutOnRecordContract2(JDialog dialog, Contract contract) {
		ContractAction contractAction = (ContractAction) CommonVars
				.getApplicationContext().getBean("contractAction");
		if (contract == null) {
			JOptionPane.showMessageDialog(dialog, "请先保存合同记录!!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		// dialog.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		String emsNo = "";
		printPutOnRecordsContract(contract, false);
	}

	/**
	 * 打印出口成品费用表
	 * 
	 * @param dialog
	 * @param contract
	 */
	public void printExportExgCharge(JDialog dialog, Contract contract) {
		ContractAction contractAction = (ContractAction) CommonVars
				.getApplicationContext().getBean("contractAction");
		if (contract == null) {
			JOptionPane.showMessageDialog(dialog, "请先保存合同记录!!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		// dialog.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		String emsNo = "";
		try {
			List list = new ArrayList();
			if (contract != null) {
				String parentId = contract.getId();
				list = contractAction.findContractExgByParentId(new Request(
						CommonVars.getCurrUser()), parentId);
				emsNo = contract.getExpContractNo() == null ? "" : contract
						.getExpContractNo();
			}
			CustomReportDataSource ds = new CustomReportDataSource(list);
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("companyName", contract.getMachName());
			parameters.put("emsNo", emsNo);
			InputStream masterReportStream = DgContract.class
					.getResourceAsStream("report/ExpProcuctProcessPrice.jasper");
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					masterReportStream, parameters, ds);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 打印料件进出平衡检查表
	 * 
	 * @param dialog
	 * @param contract
	 */
	public void printImEmImgBalance(JDialog dialog, Contract contract) {
		ContractAction contractAction = (ContractAction) CommonVars
				.getApplicationContext().getBean("contractAction");
		if (contract == null) {
			JOptionPane.showMessageDialog(dialog, "请先保存合同记录!!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		// dialog.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		String emsNo = "";
		try {
			List list = new ArrayList();
			if (contract != null) {
				list = contractAction.findContractImgAndExgUsedDiffAmount(
						new Request(CommonVars.getCurrUser()), contract);
				emsNo = (contract.getEmsNo() == null ? "" : contract.getEmsNo());
			}
			CustomReportDataSource ds = new CustomReportDataSource(list);
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("companyName", contract.getMachName());
			parameters.put("emsNo", emsNo);
			InputStream masterReportStream = DgContract.class
					.getResourceAsStream("report/ImgAndExgUsedDiffAmount.jasper");
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					masterReportStream, parameters, ds);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 加工贸易单耗申报表
	 * 
	 * @param dialog
	 * @param contract
	 */
	public void processingTradeUnitConsumption(JDialog dialog, Contract contract) {
		ContractAction contractAction = (ContractAction) CommonVars
				.getApplicationContext().getBean("contractAction");
		if (contract == null) {
			JOptionPane.showMessageDialog(dialog, "请先保存合同记录!!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		
		///根据成品序号，保存每个成品对应的bom
		String parentId = contract.getId();
		List<Integer> listKey = new ArrayList<Integer>();
		Map<Integer,List> map = new HashMap<Integer, List>();
		initDatesource(parentId,contractAction,map,listKey);
		
		try {
			//打印的报表
			JasperPrint jp = null;
			
			//遍历map中的数据
			for(int i = 0;i<listKey.size();i++) {
				
				List li = map.get(listKey.get(i));
				JasperPrint JasperPrint = getPages(contract,li);
				
				if(JasperPrint==null){
					continue;
				}
				
				if(jp==null){
					jp = JasperPrint;
				}else{
					//把生成的也追加上去
					for (int j = 0; j < JasperPrint.getPages().size(); j++) {
						jp.addPage((JRPrintPage) JasperPrint.getPages().get(j));
					}
				}
			}
			
			if(jp == null){
				return;
			}
			
			DgReportViewer viewer = new DgReportViewer(jp);
			viewer.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 组装数据
	 * @param parentId 合同id
	 * @param contractAction
	 * @return
	 */
	private void initDatesource(String parentId,ContractAction contractAction,Map<Integer,List> map,List listKey){
		List<ContractBom> listBom = contractAction.findContractBomByContractId(new Request(CommonVars
				.getCurrUser()), parentId);
		for (int i = 0; i < listBom.size(); i++) {
			ContractExg exg = listBom.get(i).getContractExg();
			if(map.get(exg.getSeqNum())==null){
				List list = new ArrayList();
				list.add(listBom.get(i));
				listKey.add(exg.getSeqNum());
				map.put(exg.getSeqNum(),list);
			}else{
				map.get(exg.getSeqNum()).add(listBom.get(i));
			}
		}
	}
	
	/**
	 * 生成要打印的页
	 * @param contract
	 * @param listBom
	 * @return
	 */
	private JasperPrint getPages(Contract contract,List<ContractBom> listBom){
		try {
			ContractExg exg = null;
			if(listBom.size()>0){
				exg = listBom.get(0).getContractExg();
			}else{
				return null;
			}
			
			CustomReportDataSource ds = new CustomReportDataSource(listBom);
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("comName", contract.getTradeName());
			parameters.put("comCode", contract.getTradeCode());
			parameters.put("emsNo", contract.getEmsNo());
			parameters.put("equipMode", contract.getEquipMode());
			parameters.put("handler", contract.getHandler());
			parameters.put("contactTel", contract.getContactTel());
			parameters.put("saveDate", contract.getSaveDate());
			
			parameters.put("seqNum", exg.getSeqNum()==null?"":exg.getSeqNum().toString());
			parameters.put("complexCode", exg.getComplex()==null?"":exg.getComplex().getCode());
			parameters.put("name", exg.getName());
			parameters.put("spec", exg.getSpec());
			parameters.put("unitName", exg.getUnit()==null?"":exg.getUnit().getName());
			
			InputStream masterReportStream = DgContract.class
					.getResourceAsStream("report/BomApply.jasper");
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					masterReportStream, parameters, ds);
			return jasperPrint;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 打印通关手册表
	 * 
	 * @param dialog
	 * @param contract
	 */
	public void printCustomClearanceContarct(JDialog dialog, Contract contract) {
		ContractAction contractAction = (ContractAction) CommonVars
				.getApplicationContext().getBean("contractAction");
		if (contract == null) {
			JOptionPane.showMessageDialog(dialog, "请先保存合同记录!!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		// dialog.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		String emsNo = "";
		printProceContractSituReport(contract);
	}

	/**
	 * 打印合同国内购料清单表
	 */
	public void printContractDomesticPurchaseBill(JDialog dialog,
			Contract contract) {
		try {
			ContractAction contractAction = (ContractAction) CommonVars
					.getApplicationContext().getBean("contractAction");
			List list = null;
			List list2 = null;

			if (contract != null) {
				String parentId = contract.getId();
				// list = contractAction.findContractDomesticPurchaseBill(
				// new Request(CommonVars.getCurrUser()), parentId);
				list2 = contractAction.setContractDomesticPurchaseBill(
						new Request(CommonVars.getCurrUser()), parentId);
			}
			CustomReportDataSource ds = new CustomReportDataSource(list2);
			Map<String, Object> parameters = new HashMap<String, Object>();

			InputStream masterReportStream = DgContract.class
					.getResourceAsStream("report/ContractDomesticPurchaseBill.jasper");
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					masterReportStream, parameters, ds);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// public void printReport()
	// {
	// if(tableModel == null)
	// {
	// JOptionPane.showMessageDialog(DgPeopleOfForeignersDriverHandout.this,
	// "请先保存资料!!!", "提示", JOptionPane.INFORMATION_MESSAGE);
	// return;
	// }
	//
	// InputStream reportStream = null;
	// List<Object> list = new ArrayList<Object>();
	// Map<String, Object> parameters = new HashMap<String, Object>();
	// Date now = new Date();
	// List list2=tableModel.getList();
	// System.out.println(list2.size());
	// DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
	// String[] date = df.format(now).split("/");
	// CustomReportDataSource ds = new CustomReportDataSource(list2);
	// reportStream =
	// FmDieAwayRegister.class.getResourceAsStream("report/MotorcycleDrivingcardInInfo.jasper");
	// parameters.put("year", date[2]);
	// parameters.put("month", date[1]);
	// parameters.put("day", date[0]);
	// try
	// {
	// JasperPrint jasperPrint = JasperFillManager.fillReport(
	// reportStream, parameters, ds);
	// DgReportViewer viewer = new DgReportViewer(jasperPrint);
	// viewer.setVisible(true);
	// }
	// catch(JRException e1)
	// {
	// e1.printStackTrace();
	// }
	// }

	/**
	 * 报表打印
	 */
	public void printReport(JDialog dialog, Contract contract,
			JComboBox cbbPrint) {
		ContractAction contractAction = (ContractAction) CommonVars
				.getApplicationContext().getBean("contractAction");
		if (contract == null) {
			JOptionPane.showMessageDialog(dialog, "请先保存合同记录!!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		// dialog.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		// int flag = this.cbbPrint.getSelectedIndex();
		String contractNo = "";
		int flag = cbbPrint.getSelectedIndex();
		switch (flag) {
		case 0: // 套打出口成品表(新)
			try {
				List list = new ArrayList();
				if (contract != null) {
					String parentId = contract.getId();
					list = contractAction.findContractExgByParentId(
							new Request(CommonVars.getCurrUser()), parentId);
					contractNo = contract.getExpContractNo() == null ? "" : contract
							.getExpContractNo();contract.getEmsNo();
				}
				InputStream subReportStream = DgContract.class
						.getResourceAsStream("report/NewFinishedProductSubReport.jasper");
				JasperReport subReport = (JasperReport) JRLoader
						.loadObject(subReportStream);
				CustomReportDataSource ds = new CustomReportDataSource(list);
				List<Double> currRateList = getHDKAndUSDRate(contract, dialog);
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("isOverprint", new Boolean(true));
				parameters.put("rateHDK", currRateList.get(0));
				parameters.put("rateUSD", currRateList.get(1));
				parameters.put("list", list);
				parameters.put("emsNo", contractNo);
				parameters.put("companyName", contract.getMachName());
				parameters.put("currName", contract.getCurr() == null ? ""
						: contract.getCurr().getName());
				parameters.put("subReport", subReport);
				parameters.put("subReportDataSource", ds);
				InputStream masterReportStream = DgContract.class
						.getResourceAsStream("report/NewExportFinishedProductReport.jasper");
				List<String> tempList = new ArrayList<String>();
				tempList.add("tempData");
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters,
						new CustomReportDataSource(tempList));
				DgReportViewer viewer = new DgReportViewer(jasperPrint);
				viewer.setVisible(true);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			break;
		case 1: // 非套打出口成品表(新)
			try {
				List list = new ArrayList();
				if (contract != null) {
					String parentId = contract.getId();
					list = contractAction.findContractExgByParentId(
							new Request(CommonVars.getCurrUser()), parentId);
					contractNo = contract.getExpContractNo() == null ? "" : contract
							.getExpContractNo();
				}
				InputStream subReportStream = DgContract.class
						.getResourceAsStream("report/NewFinishedProductSubReport.jasper");
				JasperReport subReport = (JasperReport) JRLoader
						.loadObject(subReportStream);
				CustomReportDataSource ds = new CustomReportDataSource(list);
				List<Double> currRateList = this.getHDKAndUSDRate(contract,
						dialog);
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("isOverprint", new Boolean(false));
				parameters.put("rateHDK", currRateList.get(0));
				parameters.put("rateUSD", currRateList.get(1));
				parameters.put("list", list);
				parameters.put("emsNo", contractNo);
				parameters.put("companyName", contract.getMachName());
				parameters.put("currName", contract.getCurr() == null ? ""
						: contract.getCurr().getName());
				parameters.put("subReport", subReport);
				parameters.put("subReportDataSource", ds);
				InputStream masterReportStream = DgContract.class
						.getResourceAsStream("report/NewExportFinishedProductReport.jasper");
				List<String> tempList = new ArrayList<String>();
				tempList.add("tempData");
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters,
						new CustomReportDataSource(tempList));
				DgReportViewer viewer = new DgReportViewer(jasperPrint);
				viewer.setVisible(true);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			break;
		case 2: // 套打进口料件表
			try {
				List list = new ArrayList();
				if (contract != null) {
					String parentId = contract.getId();
					list = contractAction.findContractImgByParentId(
							new Request(CommonVars.getCurrUser()), parentId);
					contractNo = contract.getImpContractNo() == null ? "" : contract
							.getImpContractNo();
				}
				InputStream subReportStream = DgContract.class
						.getResourceAsStream("report/NewMaterielSubReport.jasper");
				JasperReport subReport = (JasperReport) JRLoader
						.loadObject(subReportStream);
				CustomReportDataSource ds = new CustomReportDataSource(list);
				List<Double> currRateList = this.getHDKAndUSDRate(contract,
						dialog);
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("isOverprint", new Boolean(true));
				parameters.put("rateHDK", currRateList.get(0));
				parameters.put("rateUSD", currRateList.get(1));
				parameters.put("list", list);
				parameters.put("emsNo", contractNo);
				parameters.put("companyName", contract.getMachName());
				parameters.put("currName", contract.getCurr() == null ? ""
						: contract.getCurr().getName());
				parameters.put("subReport", subReport);
				parameters.put("subReportDataSource", ds);
				InputStream masterReportStream = DgContract.class
						.getResourceAsStream("report/NewImportMaterielReport.jasper");
				List<String> tempList = new ArrayList<String>();
				tempList.add("tempData");
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters,
						new CustomReportDataSource(tempList));
				DgReportViewer viewer = new DgReportViewer(jasperPrint);
				viewer.setVisible(true);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			break;
		case 3: // 非套打进口料件表
			try {
				List list = new ArrayList();
				if (contract != null) {
					String parentId = contract.getId();
					list = contractAction.findContractImgByParentId(
							new Request(CommonVars.getCurrUser()), parentId);
					contractNo = contract.getImpContractNo() == null ? "" : contract
							.getImpContractNo();
				}
				InputStream subReportStream = DgContract.class
						.getResourceAsStream("report/NewMaterielSubReport.jasper");
				JasperReport subReport = (JasperReport) JRLoader
						.loadObject(subReportStream);
				CustomReportDataSource ds = new CustomReportDataSource(list);
				List<Double> currRateList = this.getHDKAndUSDRate(contract,
						dialog);
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("isOverprint", new Boolean(false));
				parameters.put("rateHDK", currRateList.get(0));
				parameters.put("rateUSD", currRateList.get(1));
				parameters.put("list", list);
				parameters.put("emsNo", contractNo);
				parameters.put("companyName", contract.getMachName());
				parameters.put("currName", contract.getCurr() == null ? ""
						: contract.getCurr().getName());
				parameters.put("subReport", subReport);
				parameters.put("subReportDataSource", ds);
				InputStream masterReportStream = DgContract.class
						.getResourceAsStream("report/NewImportMaterielReport.jasper");
				List<String> tempList = new ArrayList<String>();
				tempList.add("tempData");
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters,
						new CustomReportDataSource(tempList));
				DgReportViewer viewer = new DgReportViewer(jasperPrint);
				viewer.setVisible(true);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			break;
		case 4: // 套打成品表
			try {
				List list = new ArrayList();
				if (contract != null) {
					String parentId = contract.getId();
					list = contractAction.findContractExgByParentId(
							new Request(CommonVars.getCurrUser()), parentId);
					contractNo = contract.getExpContractNo() == null ? "" : contract
							.getExpContractNo();
				}
				InputStream subReportStream = DgContract.class
						.getResourceAsStream("report/FinishedProductSubReport.jasper");
				JasperReport subReport = (JasperReport) JRLoader
						.loadObject(subReportStream);
				CustomReportDataSource ds = new CustomReportDataSource(list);
				List<Double> currRateList = getHDKAndUSDRate(contract, dialog);
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("isOverprint", new Boolean(true));
				parameters.put("rateHDK", currRateList.get(0));
				parameters.put("rateUSD", currRateList.get(1));
				parameters.put("list", list);
				parameters.put("emsNo", contractNo);
				parameters.put("companyName", contract.getMachName());
				parameters.put("currName", contract.getCurr() == null ? ""
						: contract.getCurr().getName());
				parameters.put("subReport", subReport);
				parameters.put("subReportDataSource", ds);
				InputStream masterReportStream = DgContract.class
						.getResourceAsStream("report/ExportFinishedProductReport.jasper");
				List<String> tempList = new ArrayList<String>();
				tempList.add("tempData");
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters,
						new CustomReportDataSource(tempList));
				DgReportViewer viewer = new DgReportViewer(jasperPrint);
				viewer.setVisible(true);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			break;
		case 5: // 非套打成品表
			try {
				List list = new ArrayList();
				if (contract != null) {
					String parentId = contract.getId();
					list = contractAction.findContractExgByParentId(
							new Request(CommonVars.getCurrUser()), parentId);
					contractNo = contract.getExpContractNo() == null ? "" : contract
							.getExpContractNo();
				}
				InputStream subReportStream = DgContract.class
						.getResourceAsStream("report/FinishedProductSubReport.jasper");
				JasperReport subReport = (JasperReport) JRLoader
						.loadObject(subReportStream);
				CustomReportDataSource ds = new CustomReportDataSource(list);
				List<Double> currRateList = this.getHDKAndUSDRate(contract,
						dialog);
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("isOverprint", new Boolean(false));
				parameters.put("rateHDK", currRateList.get(0));
				parameters.put("rateUSD", currRateList.get(1));
				parameters.put("list", list);
				parameters.put("emsNo", contractNo);
				parameters.put("companyName", contract.getMachName());
				parameters.put("currName", contract.getCurr() == null ? ""
						: contract.getCurr().getName());
				parameters.put("subReport", subReport);
				parameters.put("subReportDataSource", ds);
				InputStream masterReportStream = DgContract.class
						.getResourceAsStream("report/ExportFinishedProductReport.jasper");
				List<String> tempList = new ArrayList<String>();
				tempList.add("tempData");
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters,
						new CustomReportDataSource(tempList));
				DgReportViewer viewer = new DgReportViewer(jasperPrint);
				viewer.setVisible(true);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			break;
		case 6: // 套打料件表
			try {
				List list = new ArrayList();
				if (contract != null) {
					String parentId = contract.getId();
					list = contractAction.findContractImgByParentId(
							new Request(CommonVars.getCurrUser()), parentId);
					contractNo = contract.getImpContractNo() == null ? "" : contract
							.getImpContractNo();
				}
				InputStream subReportStream = DgContract.class
						.getResourceAsStream("report/MaterielSubReport.jasper");
				JasperReport subReport = (JasperReport) JRLoader
						.loadObject(subReportStream);
				CustomReportDataSource ds = new CustomReportDataSource(list);
				List<Double> currRateList = this.getHDKAndUSDRate(contract,
						dialog);
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("isOverprint", new Boolean(true));
				parameters.put("rateHDK", currRateList.get(0));
				parameters.put("rateUSD", currRateList.get(1));
				parameters.put("list", list);
				parameters.put("emsNo", contractNo);
				parameters.put("companyName", contract.getMachName());
				parameters.put("currName", contract.getCurr() == null ? ""
						: contract.getCurr().getName());
				parameters.put("subReport", subReport);
				parameters.put("subReportDataSource", ds);
				InputStream masterReportStream = DgContract.class
						.getResourceAsStream("report/ImportMaterielReport.jasper");
				List<String> tempList = new ArrayList<String>();
				tempList.add("tempData");
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters,
						new CustomReportDataSource(tempList));
				DgReportViewer viewer = new DgReportViewer(jasperPrint);
				viewer.setVisible(true);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			break;
		case 7: // 非套打料件表
			try {
				List list = new ArrayList();
				if (contract != null) {
					String parentId = contract.getId();
					list = contractAction.findContractImgByParentId(
							new Request(CommonVars.getCurrUser()), parentId);
					contractNo = contract.getImpContractNo() == null ? "" : contract
							.getImpContractNo();
				}
				InputStream subReportStream = DgContract.class
						.getResourceAsStream("report/MaterielSubReport.jasper");
				JasperReport subReport = (JasperReport) JRLoader
						.loadObject(subReportStream);
				CustomReportDataSource ds = new CustomReportDataSource(list);
				List<Double> currRateList = this.getHDKAndUSDRate(contract,
						dialog);
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("isOverprint", new Boolean(false));
				parameters.put("rateHDK", currRateList.get(0));
				parameters.put("rateUSD", currRateList.get(1));
				parameters.put("list", list);
				parameters.put("emsNo", contractNo);
				parameters.put("companyName", contract.getMachName());
				parameters.put("currName", contract.getCurr() == null ? ""
						: contract.getCurr().getName());
				parameters.put("subReport", subReport);
				parameters.put("subReportDataSource", ds);
				InputStream masterReportStream = DgContract.class
						.getResourceAsStream("report/ImportMaterielReport.jasper");
				List<String> tempList = new ArrayList<String>();
				tempList.add("tempData");
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters,
						new CustomReportDataSource(tempList));
				DgReportViewer viewer = new DgReportViewer(jasperPrint);
				viewer.setVisible(true);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			break;
		case 8: // 套打单耗表
			try {
				List list = new ArrayList();
				int count = 0;
				if (contract != null) {
					String parentId = contract.getId();
					list = contractAction.findContractUnitWaste(new Request(
							CommonVars.getCurrUser()), parentId, -1, -1);
					contractNo = contract.getExpContractNo() == null ? "" : contract
							.getExpContractNo();
					count = contractAction.findContractExgCount(new Request(
							CommonVars.getCurrUser()), contract.getId());
				}
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("emsNo", contractNo);
				parameters.put("isOverprint", new Boolean(true));
				parameters.put("companyName", contract.getMachName());
				parameters.put("count", count);
				parameters.put("isChange", false);
				parameters.put("groupSeq", new ArrayList());
				CustomReportDataSource ds = new CustomReportDataSource(list);
				ds.setMaximumFractionDigits(9);
				InputStream masterReportStream = DgContract.class
						.getResourceAsStream("report/MaterielUnitWasteReport.jasper");
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters, ds);
				DgReportViewer viewer = new DgReportViewer(jasperPrint);
				viewer.setVisible(true);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			break;
		case 9: // 非套打单耗表
			try {
				List list = new ArrayList();
				int count = 0;
				if (contract != null) {
					String parentId = contract.getId();
					list = contractAction.findContractUnitWaste(new Request(
							CommonVars.getCurrUser()), parentId, -1, -1);
					contractNo = contract.getExpContractNo() == null ? "" : contract
							.getExpContractNo();
					count = contractAction.findContractExgCount(new Request(
							CommonVars.getCurrUser()), contract.getId());
				}
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("emsNo", contractNo);
				parameters.put("isOverprint", new Boolean(false));
				parameters.put("companyName", contract.getMachName());
				parameters.put("count", count);
				parameters.put("isChange", false);
				parameters.put("groupSeq", new ArrayList());
				CustomReportDataSource ds = new CustomReportDataSource(list);
				ds.setMaximumFractionDigits(9);
				InputStream masterReportStream = DgContract.class
						.getResourceAsStream("report/MaterielUnitWasteReport.jasper");
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters, ds);
				DgReportViewer viewer = new DgReportViewer(jasperPrint);
				viewer.setVisible(true);
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			break;
		case 10:// 打印加工合同备案情况表
			printPutOnRecordsContract(contract, true);
			break;
		case 11: // 打印预申报合同组成表
			printPutOnRecordsContract(contract, false);
			break;
		case 12: // 打印出口成品费用表
			try {
				List list = new ArrayList();
				if (contract != null) {
					String parentId = contract.getId();
					list = contractAction.findContractExgByParentId(
							new Request(CommonVars.getCurrUser()), parentId);
					contractNo = contract.getExpContractNo() == null ? "" : contract
							.getExpContractNo();
				}
				CustomReportDataSource ds = new CustomReportDataSource(list);
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("companyName", contract.getMachName());
				parameters.put("emsNo", contractNo);
				InputStream masterReportStream = DgContract.class
						.getResourceAsStream("report/ExpProcuctProcessPrice.jasper");
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters, ds);
				DgReportViewer viewer = new DgReportViewer(jasperPrint);
				viewer.setVisible(true);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			break;
		case 13: // 打印料件进出平衡检查表
			try {
				List list = new ArrayList();
				if (contract != null) {
					list = contractAction.findContractImgAndExgUsedDiffAmount(
							new Request(CommonVars.getCurrUser()), contract);
					contractNo = (contract.getEmsNo() == null ? "" : contract
							.getEmsNo());
				}
				CustomReportDataSource ds = new CustomReportDataSource(list);
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("companyName", contract.getMachName());
				parameters.put("emsNo", contractNo);
				InputStream masterReportStream = DgContract.class
						.getResourceAsStream("report/ImgAndExgUsedDiffAmount.jasper");
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters, ds);
				DgReportViewer viewer = new DgReportViewer(jasperPrint);
				viewer.setVisible(true);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			break;
		case 14: // 加工贸易单耗申报表
			try {
				List list = new ArrayList();
				list.add("nothing");
				CustomReportDataSource ds = new CustomReportDataSource(list);
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("comName", contract.getTradeName());
				parameters.put("comCode", contract.getTradeCode());
				parameters.put("emsNo", contract.getEmsNo());
				InputStream masterReportStream = DgContract.class
						.getResourceAsStream("report/BomApply.jasper");
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters, ds);
				DgReportViewer viewer = new DgReportViewer(jasperPrint);
				viewer.setVisible(true);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			break;
		case 15:// 打印通关手册表
			printProceContractSituReport(contract);
			break;
		default:
			break;
		}
		String cation = cbbPrint.getSelectedItem().toString().trim();
		if (ContractPrintItem.OVERPRINT_IMPORT_CHANGED_REPORT.equals(cation)) {
			printChangedContractImgReport(contract, true);
		} else if (ContractPrintItem.IMPORT_CHANGED_REPORT.equals(cation)) {
			printChangedContractImgReport(contract, false);
		} else if (ContractPrintItem.OVERPRINT_EXPORT_CHANGED_REPORT
				.equals(cation)) {
			printChangedContractExgReport(contract, true);
		} else if (ContractPrintItem.EXPORT_CHANGED_REPORT.equals(cation)) {
			printChangedContractExgReport(contract, false);
		} else if (ContractPrintItem.BUYATHOME_MATERIAL_APPLAY.equals(cation)) {
			printBuyAtHomeMaterialReport(contract);
		} else if (ContractPrintItem.YES_WASTE_CHANGE.equals(cation)) {
			printYesCheWatseChange(contract);
		} else if (ContractPrintItem.NO_WASTE_CHANGE.equals(cation)) {
			printNoCheWatseChange(contract);
		}
		dialog.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	/**
	 * 打印通关手册表
	 * 
	 * @param dialog
	 * @param contract
	 */
	private void printProceContractSituReport(Contract contract) {
		ContractAction contractAction = (ContractAction) CommonVars
				.getApplicationContext().getBean("contractAction");
		if (contract == null) {
			return;
		}
		// 打印表头
		try {
			Boolean declareStateCHANGING_EXE = false;
			if (contract.getDeclareState().equals(DeclareState.CHANGING_EXE)) {// 表示打印变更资料
				declareStateCHANGING_EXE = true;
			}
			Boolean declareStateWAIT_EAA = false;
			if (contract.getDeclareState().equals(DeclareState.WAIT_EAA)) {
				declareStateWAIT_EAA = true;
			}
			System.out.println("contract.getDeclareState()="
					+ contract.getDeclareState());
			List list = new ArrayList();
			list.add(contract);
			CustomReportDataSource ds = new CustomReportDataSource(list);
			Map<String, Object> parameters = new HashMap<String, Object>();
			String manageType = "";
			if (contract.getManageObject() != null
					&& contract.getManageObject().equals(
							ManageObject.MANAGE_COP)) {
				manageType = "经营单位";
			} else if (contract.getManageObject() != null
					&& contract.getManageObject().equals(
							ManageObject.MANUFACTURE_COP)) {
				manageType = "加工单位";
			}
			parameters.put("manageType", manageType == null ? "" : ("以"
					+ manageType + "为管理对象"));
			String iePort = "";
			String equipMode = "";
			// System.out.println("contract.getEquipMode()"+contract.getEquipMode());
			if (contract.getEquipMode() == null) {
				equipMode = "";
			} else if (contract.getEquipMode().equals("1")) {
				equipMode = "备案";
			} else if (contract.getEquipMode().equals("2")) {
				equipMode = "出口前";
			} else if (contract.getEquipMode().equals("3")) {
				equipMode = "报核前";
			}
			parameters.put("equipMode", equipMode);
			if (contract.getIePort1() != null) {
				iePort += contract.getIePort1().getName() + "/";
			}
			if (contract.getIePort2() != null) {
				iePort += contract.getIePort2().getName() + "/";
			}
			if (contract.getIePort3() != null) {
				iePort += contract.getIePort3().getName() + "/";
			}
			if (contract.getIePort4() != null) {
				iePort += contract.getIePort4().getName() + "/";
			}
			if (contract.getIePort5() != null) {
				iePort += contract.getIePort5().getName() + "/";
			}
			parameters.put("iePort", iePort);
			parameters.put("emsType", getContractKind(contract.getEmsType()));
			parameters.put("modifymark", getModify(contract.getModifyMark()));
			
			String bankBodel = contract.getBankModel();
			if (bankBodel == null || "".equals(bankBodel)) {
				bankBodel = "";
			} else if ("0".equals(bankBodel)) {
				bankBodel = "纸质台帐";
			} else if ("1".equals(bankBodel)) {
				bankBodel = "中国银行";
			} else if ("2".equals(bankBodel)) {
				bankBodel = "工商银行";
			} else {
				bankBodel = "";
			}
			parameters.put("bankBodel", bankBodel);
			InputStream masterReportStream = DgContract.class
					.getResourceAsStream("report/ProceContractSituReport.jasper");
			JasperPrint jasperPrintContract = JasperFillManager.fillReport(
					masterReportStream, parameters, ds);

			// 成品明细列表数据源

			List finishedProductList = new ArrayList();
			if (contract != null) {
				String parentId = contract.getId();
				finishedProductList = contractAction.findContractExgBy(
						new Request(CommonVars.getCurrUser()), parentId,
						declareStateCHANGING_EXE, declareStateWAIT_EAA);
				for (int i = 0; i < finishedProductList.size(); i++) {
					ContractExg c = (ContractExg) finishedProductList.get(i);
				}

			}
			if (finishedProductList != null && finishedProductList.size() > 0) {
				ContractExg ce = (ContractExg) finishedProductList.get(0);
				System.out.println("ce.getDutyRate()=" + ce.getDutyRate());
				System.out.println("dutyRate=" + ce.getDutyRate());
				String dutyRate = "";
				double dr = ce.getDutyRate();
				if (ce.getDutyRate() == null) {
					dutyRate = "";
				} else if (ce.getDutyRate() == 1.0) {
					dutyRate = "企业不申报";
				} else if (ce.getDutyRate() == 2.0) {
					dutyRate = "企业申报";
				} else if (ce.getDutyRate() == 9.0) {
					dutyRate = "已核定";
				}
				CustomReportDataSource finishedProductDataSource = new CustomReportDataSource(
						finishedProductList);
				Map<String, Object> contractexgMap = new HashMap<String, Object>();
				contractexgMap.put("dutyRate", dutyRate);
				contractexgMap.put("curr", contract.getCurr() == null ? ""
						: contract.getCurr().getName());
				contractexgMap.put("beforePageCount", jasperPrintContract
						.getPages().size());
				InputStream exportFinishedProductSubReportStream = DgContract.class
						.getResourceAsStream("report/ProceContractSituExgReport.jasper");
				JasperPrint jasperPrintContractExg = JasperFillManager
						.fillReport(exportFinishedProductSubReportStream,
								contractexgMap, finishedProductDataSource);
				int cexg = jasperPrintContractExg.getPages().size();
				for (int i = 0; i < cexg; i++) {
					jasperPrintContract
							.addPage((JRPrintPage) jasperPrintContractExg
									.getPages().get(i));
				}
			}

			// ====================================
			// 进口料件报表 把进口料件报表加入到总报表中
			// ====================================
			List contractImgList = new ArrayList();
			if (contract != null) {
				String parentId = contract.getId();
				contractImgList = contractAction.findContractImgBy(new Request(
						CommonVars.getCurrUser()), parentId,
						declareStateCHANGING_EXE, declareStateWAIT_EAA);
			}
			if (contractImgList != null && contractImgList.size() > 0) {
				ContractImg ci = (ContractImg) contractImgList.get(0);
				double dutyRate2 = ci.getDutyRate() == null ? 0.0 : ci
						.getDutyRate();
				CustomReportDataSource contractImgDataSource = new CustomReportDataSource(
						contractImgList);
				Map<String, Object> contractImgMap = new HashMap<String, Object>();
				contractImgMap.put("dutyRate2", dutyRate2);
				contractImgMap.put("curr", contract.getCurr() == null ? ""
						: contract.getCurr().getName());
				InputStream contractImgReportStream = DgContract.class
						.getResourceAsStream("report/ProceContractSituImgReport.jasper");
				JasperPrint jasperPrintContractImg = JasperFillManager
						.fillReport(contractImgReportStream, contractImgMap,
								contractImgDataSource);
				int count = jasperPrintContractImg.getPages().size();
				for (int i = 0; i < count; i++) {
					jasperPrintContract
							.addPage((JRPrintPage) jasperPrintContractImg
									.getPages().get(i));
				}
			}
			// ========================================
			// 成品对应料件单损耗情况表 并把报表加入到总报表中
			// ========================================
			List contractUnitWasteList = new ArrayList();
			int countExg = 0;
			if (contract != null) {
				String parentId = contract.getId();
				contractUnitWasteList = contractAction.findContractBomBySeq(
						new Request(CommonVars.getCurrUser()), contract, null,
						null, declareStateCHANGING_EXE, declareStateWAIT_EAA);
			}
			if (contractUnitWasteList != null
					&& contractUnitWasteList.size() > 0) {
				CustomReportDataSource contractUnitWasteDataSource = new CustomReportDataSource(
						contractUnitWasteList);
				contractUnitWasteDataSource.setMaximumFractionDigits(9);
				Map<String, Object> contractUnitWasteMap = new HashMap<String, Object>();
				contractUnitWasteMap.put("count", countExg);
				InputStream contractUnitWasteReportStream = DgContract.class
						.getResourceAsStream("report/ProceContractSituWasteReport.jasper");
				JasperPrint jasperPrintContractUnitWaste = JasperFillManager
						.fillReport(contractUnitWasteReportStream,
								contractUnitWasteMap,
								contractUnitWasteDataSource);
				int size = jasperPrintContractUnitWaste.getPages().size();
				for (int i = 0; i < size; i++) {
					jasperPrintContract
							.addPage((JRPrintPage) jasperPrintContractUnitWaste
									.getPages().get(i));
				}
			}
			DgReportViewer viewer = new DgReportViewer(jasperPrintContract);
			viewer.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * 打印表头信息
	 */
	public void printContractHead(JDialog dialog, Contract contract,
			boolean isTaoDa) {
		if (contract == null) {
			return;
		}
		// 打印表头
		try {
			List list = new ArrayList();
			list.add(contract);
			CustomReportDataSource ds = new CustomReportDataSource(list);
			Map<String, Object> parameters = new HashMap<String, Object>();
			String manageType = "";
			if (contract.getManageObject() != null
					&& contract.getManageObject().equals(
							ManageObject.MANAGE_COP)) {
				manageType = "经营单位";
			} else if (contract.getManageObject() != null
					&& contract.getManageObject().equals(
							ManageObject.MANUFACTURE_COP)) {
				manageType = "加工单位";
			}
			parameters.put("manageType", manageType == null ? "" : ("以"
					+ manageType + "为管理对象"));
			String iePort = "";
			if (contract.getIePort1() != null) {
				iePort += contract.getIePort1().getName() + "/";
			}
			if (contract.getIePort2() != null) {
				iePort += contract.getIePort2().getName() + "/";
			}
			if (contract.getIePort3() != null) {
				iePort += contract.getIePort3().getName() + "/";
			}
			if (contract.getIePort4() != null) {
				iePort += contract.getIePort4().getName() + "/";
			}
			if (contract.getIePort5() != null) {
				iePort += contract.getIePort5().getName() + "/";
			}
			parameters.put("iePort", iePort);
			parameters.put("emsType", getContractKind(contract.getEmsType()));
			parameters.put("modifymark", getModify(contract.getModifyMark()));
			InputStream masterReportStream = null;
			if (isTaoDa) {
				masterReportStream = DgContract.class
						.getResourceAsStream("report/PrintContractMiHead.jasper");
			} else {
				masterReportStream = DgContract.class
						.getResourceAsStream("report/PrintContractMiNonHead.jasper");
			}
			JasperPrint jasperPrintContract = JasperFillManager.fillReport(
					masterReportStream, parameters, ds);
			DgReportViewer viewer = new DgReportViewer(jasperPrintContract);
			viewer.setVisible(true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 得到手册种类
	 * 
	 * @param code
	 * @return
	 */
	private String getContractKind(String code) {
		if(code!=null && code.equals(ContractKind.IMPORT_MATERIEL_EMS)){
			return "进料加工";
		}else if(code!=null && code.equals(ContractKind.COME_MATERIEL_EMS)){
			return "来料加工";	
		}
//		if (code != null
//				&& code.equals(ContractKind.FOREIGN_CAPITAL_SELL_PRODUCT)) {
//			return "外资内销产品";
//		} else if (code != null
//				&& code.equals(ContractKind.COME_MATERIEL_PROCESS)) {
//			return "来料加工";
//		} else if (code != null
//				&& code.equals(ContractKind.IMPORT_MATERIEL_PROCESS)) {
//			return "进料加工";
//		} else if (code != null
//				&& code.equals(ContractKind.FOREIGN_CAPITAL_MACHINE)) {
//			return "外资设备";
//		} else if (code != null && code.equals(ContractKind.BONDEN_WAREHOUSE)) {
//			return "保税仓";
//		}
		return "";
	}

	/**
	 * 得到修改标志
	 * 
	 * @param code
	 * @return
	 */
	private String getModify(String code) {
		if (code != null && code.equals(ModifyMarkState.ADDED)) {
			return "新增";
		} else if (code != null && code.equals(ModifyMarkState.MODIFIED)) {
			return "已修改";
		} else if (code != null && code.equals(ModifyMarkState.UNCHANGE)) {
			return "不变";
		}
		return "";
	}

	/**
	 * 打印单耗
	 * 
	 * @param 手册
	 */
	private void printYesCheWatseChange(Contract contract) {
		ContractAction contractAction = (ContractAction) CommonVars
				.getApplicationContext().getBean("contractAction");
		try {
			List list = new ArrayList();
			List clist = new ArrayList();
			int count = 0;
			String emsNo = "";
			if (contract != null) {
				String parentId = contract.getId();
				list = contractAction.findContractUnitWasteChange(new Request(
						CommonVars.getCurrUser()), parentId, -1, -1);
				clist = (List) list.get(list.size() - 1);
				list.remove(list.size() - 1);
				emsNo = contract.getExpContractNo() == null ? "" : contract
						.getExpContractNo();
				count = contractAction.findContractExgCount(new Request(
						CommonVars.getCurrUser()), contract.getId());
			}

			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("emsNo", emsNo);
			parameters.put("isOverprint", new Boolean(true));
			parameters.put("companyName", contract.getMachName());
			parameters.put("count", count);
			parameters.put("isChange", true);
			parameters.put("groupSeq", clist);
			CustomReportDataSource ds = new CustomReportDataSource(list);
			ds.setMaximumFractionDigits(9);
			InputStream masterReportStream = DgContract.class
					.getResourceAsStream("report/MaterielUnitWasteReport.jasper");
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					masterReportStream, parameters, ds);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 打印单耗
	 * 
	 * @param contract
	 */
	private void printNoCheWatseChange(Contract contract) {
		try {
			ContractAction contractAction = (ContractAction) CommonVars
					.getApplicationContext().getBean("contractAction");
			List list = new ArrayList();
			List clist = new ArrayList();
			int count = 0;
			String emsNo = "";
			if (contract != null) {
				String parentId = contract.getId();
				list = contractAction.findContractUnitWasteChange(new Request(
						CommonVars.getCurrUser()), parentId, -1, -1);
				clist = (List) list.get(list.size() - 1);
				list.remove(list.size() - 1);
				emsNo = contract.getExpContractNo() == null ? "" : contract
						.getExpContractNo();
				count = contractAction.findContractExgCount(new Request(
						CommonVars.getCurrUser()), contract.getId());
			}
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("emsNo", emsNo);
			parameters.put("isOverprint", new Boolean(false));
			parameters.put("companyName", contract.getMachName());
			parameters.put("count", count);
			parameters.put("isChange", true);
			parameters.put("groupSeq", clist);
			CustomReportDataSource ds = new CustomReportDataSource(list);
			ds.setMaximumFractionDigits(9);
			InputStream masterReportStream = DgContract.class
					.getResourceAsStream("report/MaterielUnitWasteReport.jasper");
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					masterReportStream, parameters, ds);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 打印国内购买料件
	 * 
	 * @param 手册
	 */
	private void printBuyAtHomeMaterialReport(Contract contract) {
		ContractAction contractAction = (ContractAction) CommonVars
				.getApplicationContext().getBean("contractAction");
		try {
			List list = new ArrayList();
			if (contract != null) {
				list = contractAction.findContractImgByParentId(new Request(
						CommonVars.getCurrUser()), contract.getId());
				List<JTableListColumn> rlist = new Vector<JTableListColumn>();
				rlist.add(new JTableListColumn("序号", "seqNum", 60));
				rlist.add(new JTableListColumn("编码", "complex.code", 80));
				rlist.add(new JTableListColumn("商品名称", "name", 100));
				rlist.add(new JTableListColumn("商品规格", "spec", 100));
				rlist.add(new JTableListColumn("单位", "unit.name", 80));
				DgCommonQuery.setTableColumns(rlist);
				DgCommonQuery dgCommonQuery = new DgCommonQuery();
				dgCommonQuery.setDataSource(list);
				dgCommonQuery.setSingleResult(false);
				dgCommonQuery.setVisible(true);
				if (dgCommonQuery.isOk()) {
					list = dgCommonQuery.getReturnList();
				}
			}
			if (list == null || list.size() == 0) {
				list = new ArrayList();
				return;
			}
			int count = (list.size() / 5) + (list.size() % 5 == 0 ? 0 : 1);
			String[] imgs = new String[count];
			for (int k = 0; k < imgs.length; k++) {
				imgs[k] = "";
			}
			for (int i = 0; i < list.size(); i++) {
				if (i < list.size()) {
					ContractImg img = (ContractImg) list.get(i);
					int s = i / 5;
					imgs[s] += ((img.getName() == null ? "" : img.getName()) + ";");
				} else {
					break;
				}
			}
			ContractReportVars.getInstance().setStrs(imgs);
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("customs", contract.getDeclareCustoms() == null ? ""
					: contract.getDeclareCustoms().getName());// 申报海关
			parameters.put("tradeCode", contract.getTradeCode());
			parameters.put("tradeName", contract.getTradeName());
			parameters.put("emsNo", contract.getEmsNo());
			parameters.put("imgs", imgs);
			parameters.put("rowCount", new Integer(list.size()));
			InputStream masterReportStream = DgContract.class
					.getResourceAsStream("report/ContracyMaterialApply.jasper");
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					masterReportStream, parameters, new CustomReportDataSource(
							list));
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 打印通关手册料件明细表
	 * 
	 * @param 手册
	 * @param 是否套打
	 */
	private void printChangedContractImgReport(Contract contract,
			boolean isOverprint) {
		ContractAction contractAction = (ContractAction) CommonVars
				.getApplicationContext().getBean("contractAction");
		try {
			List[] list = new ArrayList[2];
			if (contract != null) {
				list = contractAction.findChangedContractImg(new Request(
						CommonVars.getCurrUser()), contract);
			}
			JasperReport deleteSubReport = (JasperReport) JRLoader
					.loadObject(DgContract.class
							.getResourceAsStream("report/ImgExgChangedSubReport.jasper"));
			JasperReport addSubReport = (JasperReport) JRLoader
					.loadObject(DgContract.class
							.getResourceAsStream("report/ImgExgChangedSubReport.jasper"));
			CustomReportDataSource dsAddImg = new CustomReportDataSource(
					list[1]);
			CustomReportDataSource dsDeleteImg = new CustomReportDataSource(
					list[0]);
			Double add_delePrice = (Double) list[2].get(0);// 增加-减少的金额
			Integer pageCount = (Integer) list[2].get(1);// 页数
			Map<String, Object> parameters = new HashMap<String, Object>();

			parameters.put("isOverprint", isOverprint);
			parameters.put("companyName", contract.getMachName());
			parameters.put("contractNo", contract.getImpContractNo());
			parameters.put("currName", contract.getCurr() == null ? ""
					: contract.getCurr().getName());
			parameters.put("currSymb", contract.getCurr() == null ? ""
					: contract.getCurr().getCurrSymb());
			parameters.put("deleteSubReport", deleteSubReport);
			parameters.put("deleteSubReportDataSource", dsDeleteImg);
			parameters.put("addSubReport", addSubReport);
			parameters.put("addSubReportDataSource", dsAddImg);
			parameters.put("add_delePrice", add_delePrice);// 增加-减少的金额
			parameters.put("pageCount", pageCount);// 页数
			InputStream masterReportStream = DgContract.class
					.getResourceAsStream("report/ImportImgChangedReport.jasper");
			List<String> tempList = new ArrayList<String>();
			tempList.add("tempData");
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					masterReportStream, parameters, new CustomReportDataSource(
							tempList));
			jasperPrint.removePage(jasperPrint.getPages().size() - 1);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 打印通关手册成品明细表
	 * 
	 * @param 手册
	 * @param 是否套打
	 */
	private void printChangedContractExgReport(Contract contract,
			boolean isOverprint) {
		ContractAction contractAction = (ContractAction) CommonVars
				.getApplicationContext().getBean("contractAction");
		try {
			List[] list = new ArrayList[2];
			if (contract != null) {
				list = contractAction.findChangedContractExg(new Request(
						CommonVars.getCurrUser()), contract);
			}
			JasperReport deleteSubReport = (JasperReport) JRLoader
					.loadObject(DgContract.class
							.getResourceAsStream("report/ImgExgChangedSubReport.jasper"));
			JasperReport addSubReport = (JasperReport) JRLoader
					.loadObject(DgContract.class
							.getResourceAsStream("report/ImgExgChangedSubReport.jasper"));
			CustomReportDataSource dsAddImg = new CustomReportDataSource(
					list[1]);
			CustomReportDataSource dsDeleteImg = new CustomReportDataSource(
					list[0]);
			Double add_delePrice = (Double) list[2].get(0);// 增加-减少的金额
			Integer pageCount = (Integer) list[2].get(1);// 页数
			Map<String, Object> parameters = new HashMap<String, Object>();

			parameters.put("isOverprint", isOverprint);
			parameters.put("companyName", contract.getMachName());
			parameters.put("currSymb", contract.getCurr() == null ? ""
					: contract.getCurr().getCurrSymb());
			parameters.put("contractNo", contract.getImpContractNo());
			parameters.put("currName", contract.getCurr() == null ? ""
					: contract.getCurr().getName());
			parameters.put("deleteSubReport", deleteSubReport);
			parameters.put("deleteSubReportDataSource", dsDeleteImg);
			parameters.put("addSubReport", addSubReport);
			parameters.put("addSubReportDataSource", dsAddImg);
			parameters.put("add_delePrice", add_delePrice);// 增加-减少的金额
			parameters.put("pageCount", pageCount);// 页数
			InputStream masterReportStream = DgContract.class
					.getResourceAsStream("report/ExportExgChangedReport.jasper");
			List<String> tempList = new ArrayList<String>();
			tempList.add("tempData");
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					masterReportStream, parameters, new CustomReportDataSource(
							tempList));
			jasperPrint.removePage(jasperPrint.getPages().size() - 1);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 
	 * @param isPutOnRecordsContract
	 *            ==true 是合同备案,否则是预申报合同
	 */
	private void printPutOnRecordsContract(Contract contract,
			boolean isPutOnRecordsContract) {
		ContractAction contractAction = (ContractAction) CommonVars
				.getApplicationContext().getBean("contractAction");
		try {
			// ==========================
			// 合同主报表 + 出品成品明细表
			// ==========================

			//
			// 合同备案情况主报表数据源
			//            
			String emsNo = "";
			if (isPutOnRecordsContract == true) {
				emsNo = "手册编号: " + contract.getEmsNo();
			} else if (isPutOnRecordsContract == false) {
				emsNo = "预录入号: " + contract.getPreEmsNo();
			}
			List<Contract> contractList = new ArrayList<Contract>();
			contractList.add(contract);
			CustomReportDataSource ds = new CustomReportDataSource(contractList);

			//
			// 合同备案情况主报表
			//
			Map<String, Object> contractMap = new HashMap<String, Object>();
			// contractMap.put("exportFinishedProductSubReport",
			// exportFinishedProductSubReport);
			// contractMap.put("subReportDataSource",
			// finishedProductDataSource);
			contractMap.put("isPutOnRecordsContract", isPutOnRecordsContract);
			contractMap.put("emsNo", emsNo);
			InputStream masterReportStream = DgContract.class
					.getResourceAsStream("report/ContractPutOnRecordReport.jasper");
			JasperPrint jasperPrintContract = JasperFillManager.fillReport(
					masterReportStream, contractMap, ds);
			//
			// 成品明细列表数据源
			//
			List finishedProductList = new ArrayList();
			if (contract != null) {
				String parentId = contract.getId();
				finishedProductList = contractAction.findContractExgByParentId(
						new Request(CommonVars.getCurrUser()), parentId);
			}
			CustomReportDataSource finishedProductDataSource = new CustomReportDataSource(
					finishedProductList);
			// InputStream exportFinishedProductSubReportStream =
			// DgContract.class
			// .getResourceAsStream("report/ExportFinishedProductSubReport.jasper");
			Map<String, Object> contractexgMap = new HashMap<String, Object>();
			contractexgMap.put("emsNo", emsNo);
			contractexgMap.put("beforePageCount", jasperPrintContract
					.getPages().size());
			InputStream exportFinishedProductSubReportStream = DgContract.class
					.getResourceAsStream("report/ExportProductDatailReport.jasper");

			// JasperReport exportFinishedProductSubReport = (JasperReport)
			// JRLoader
			// .loadObject(exportFinishedProductSubReportStream);
			JasperPrint jasperPrintContractExg = JasperFillManager.fillReport(
					exportFinishedProductSubReportStream, contractexgMap,
					finishedProductDataSource);
			int cexg = jasperPrintContractExg.getPages().size();
			for (int i = 0; i < cexg; i++) {
				jasperPrintContract
						.addPage((JRPrintPage) jasperPrintContractExg
								.getPages().get(i));
			}

			// ====================================
			// 进口料件报表 把进口料件报表加入到总报表中
			// ====================================
			List contractImgList = new ArrayList();
			if (contract != null) {
				String parentId = contract.getId();
				contractImgList = contractAction.findContractImgByParentId(
						new Request(CommonVars.getCurrUser()), parentId);
			}
			CustomReportDataSource contractImgDataSource = new CustomReportDataSource(
					contractImgList);
			Map<String, Object> contractImgMap = new HashMap<String, Object>();
			contractImgMap.put("emsNo", emsNo);
			contractImgMap.put("beforePageCount", jasperPrintContract
					.getPages().size());
			InputStream contractImgReportStream = DgContract.class
					.getResourceAsStream("report/ImportMaterielSubReport.jasper");
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
			if (contract != null) {
				String parentId = contract.getId();
				contractUnitWasteList = contractAction
						.findContractUnitWaste(new Request(CommonVars
								.getCurrUser()), parentId, -1, -1);
				countExg = contractAction.findContractExgCount(new Request(
						CommonVars.getCurrUser()), contract.getId());
			}
			CustomReportDataSource contractUnitWasteDataSource = new CustomReportDataSource(
					contractUnitWasteList);
			contractUnitWasteDataSource.setMaximumFractionDigits(9);
			Map<String, Object> contractUnitWasteMap = new HashMap<String, Object>();
			contractUnitWasteMap.put("emsNo", emsNo);
			contractUnitWasteMap.put("beforePageCount", jasperPrintContract
					.getPages().size());
			contractUnitWasteMap.put("count", countExg);
			InputStream contractUnitWasteReportStream = DgContract.class
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

	/**
	 * 获得当前币制与 HDK和USD的汇率
	 * 
	 * @return
	 */
	private List<Double> getHDKAndUSDRate(Contract contract, JDialog dialog) {
		//
		// 汇率设置
		//       
		double rateHKD = 0;
		double rateUSD = 0;
		Curr currentCurr = contract.getCurr();
		MaterialManageAction materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		if (currentCurr != null) {
			if ("110".equals(currentCurr.getCode().trim())) {
				rateHKD = 1;
			} else if ("502".equals(currentCurr.getCode().trim())) {
				rateUSD = 1;
			}
			List currRateList = materialManageAction.findCurrRate(new Request(
					CommonVars.getCurrUser()), currentCurr.getCode());
			for (int i = 0; i < currRateList.size(); i++) {
				CurrRate currRate = (CurrRate) currRateList.get(i);
				System.out.println("A" + currRate.getCurr().getCode().trim());
				System.out.println("B" + currRate.getCurr1().getCode().trim());
				if ("110".equals(currentCurr.getCode().trim())
						&& "110".equals(currRate.getCurr().getCode().trim())
						&& "502".equals(currRate.getCurr1().getCode().trim())) {
					rateUSD = currRate.getRate();
				} else if ("502".equals(currentCurr.getCode().trim())
						&& "502".equals(currRate.getCurr().getCode().trim())
						&& "110".equals(currRate.getCurr1().getCode().trim())) {
					rateHKD = currRate.getRate();
				}
			}
		}

		if (rateHKD == 0) {
			if (currentCurr == null) {
				JOptionPane.showMessageDialog(dialog, "当前合同没有设置币制!!!", "提示",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(dialog, "汇率表中没设置当前币制与港币的汇率!!!",
						"提示", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		if (rateUSD == 0) {
			if (currentCurr == null) {
				JOptionPane.showMessageDialog(dialog, "当前合同没有设置币制!!!", "提示",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(dialog, "汇率表中没设置当前币制与美元的汇率!!!",
						"提示", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		List<Double> list = new ArrayList<Double>();
		list.add(rateHKD);
		list.add(rateUSD);
		return list;
	}

	/**
	 * This method initializes cb3
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCb1() {
		if (cb1 == null) {
			cb1 = new JCheckBox();
			cb1.setSize(new Dimension(153, 27));
			cb1.setText("当单损耗为零时打印“0”");
		}
		return cb1;
	}

	/**
	 * This method initializes cb4
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCb2() {
		if (cb2 == null) {
			cb2 = new JCheckBox();
			cb2.setSize(new Dimension(195, 35));
			cb2.setText("当单损耗为零时不打印");
		}
		return cb2;
	}
}
