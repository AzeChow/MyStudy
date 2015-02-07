package com.bestway.dzsc.client.checkcancel;

import java.util.List;
import java.util.Vector;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DgCommonQuery;
import com.bestway.bcus.custombase.action.CustomBaseAction;
import com.bestway.client.util.JTableListColumn;
import com.bestway.common.Request;
import com.bestway.dzsc.checkcancel.action.DzscContractCavAction;
import com.bestway.dzsc.checkcancel.entity.DzscCheckHead;
import com.bestway.dzsc.checkcancel.entity.DzscCheckImg;
import com.bestway.dzsc.dzscmanage.action.DzscAction;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.dzsc.innermerge.entity.DzscInnerMergeData;

public class DzscCheckCancelQuery {
	private static DzscCheckCancelQuery dzscCheckCancelQuery = null;

	private DzscCheckCancelQuery() {

	}

	public static DzscCheckCancelQuery getInstance() {
		if (dzscCheckCancelQuery == null) {
			dzscCheckCancelQuery = new DzscCheckCancelQuery();
		}
		return dzscCheckCancelQuery;
	}

	public DzscEmsPorHead findDzscEmsPorHeadExcu() {
		DzscAction dzscAction = (DzscAction) CommonVars.getApplicationContext()
				.getBean("dzscAction");
		List list = dzscAction.findDzscEmsPorHeadExcu(new Request(CommonVars
				.getCurrUser()));
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(list);
		List<JTableListColumn> tableColumns = new Vector<JTableListColumn>();
		tableColumns.add(new JTableListColumn("合同号", "ieContractNo", 100));
		tableColumns.add(new JTableListColumn("手册编号", "emsNo", 100));
		tableColumns.add(new JTableListColumn("起始日期", "beginDate", 100));
		tableColumns.add(new JTableListColumn("结束日期", "endDate", 100));
		tableColumns.add(new JTableListColumn("核销期限", "destroyDate", 100));
		tableColumns.add(new JTableListColumn("备注", "note", 100));
		tableColumns.add(new JTableListColumn("合同类型", "bargainType.name", 100));
		tableColumns.add(new JTableListColumn("合同状态", "declareState", 100));
		tableColumns.add(new JTableListColumn("延期期限", "deferDate", 100));
		tableColumns.add(new JTableListColumn("进出口岸", "iePort1.name", 100));
		tableColumns.add(new JTableListColumn("经营单位", "tradeName", 100));
		tableColumns.add(new JTableListColumn("贸易方式", "trade.name", 100));
		tableColumns.add(new JTableListColumn("收货单位", "machName", 100));
		tableColumns
				.add(new JTableListColumn("贸易国别", "tradeCountry.name", 100));
		DgCommonQuery.setTableColumns(tableColumns);
		DgCommonQuery.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return (DzscEmsPorHead) dgCommonQuery.getReturnValue();
		}
		return null;
	}

	/***************************************************************************
	 * 中期核查--添加料件
	 **************************************************************************/
	public List getImgFromInnerMerge(DzscCheckHead dzscCheckHead) {
		DzscAction dzscAction = (DzscAction) CommonVars.getApplicationContext()
				.getBean("dzscAction");
		DzscContractCavAction dzscContractCavAction = (DzscContractCavAction) CommonVars
				.getApplicationContext().getBean("dzscContractCavAction");
		CustomBaseAction customBaseAction = (CustomBaseAction) CommonVars
				.getApplicationContext().getBean("customBaseAction");
		List list = dzscAction.findDzscEmsPorHeadExcu(new Request(CommonVars
				.getCurrUser(), true), dzscCheckHead.getEmsNo()); // 获取正在执行的电子手册
		if (list.size() == 0) {
			return null;
		}
		DzscEmsPorHead dzscEmsPorHead = (DzscEmsPorHead) list.get(0);
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		List checkImgList = dzscContractCavAction.findImgFromInnerMerge(
				new Request(CommonVars.getCurrUser(), true), dzscEmsPorHead,
				dzscCheckHead);
		List checkImgLists = new Vector();
		for (int i = 0; i < checkImgList.size(); i++) {
			DzscCheckImg checkImg = new DzscCheckImg();
			checkImg.setSeqNum(((DzscInnerMergeData) checkImgList.get(i))
					.getDzscTenInnerMerge().getTenSeqNum().toString());
			checkImg.setPtNum(((DzscInnerMergeData) checkImgList.get(i))
					.getMateriel().getPtNo());
			checkImg.setComplex(customBaseAction
					.findComplexByCode(((DzscInnerMergeData) checkImgList
							.get(i)).getDzscTenInnerMerge().getTenComplex()
							.getCode()));
			checkImg.setName(((DzscInnerMergeData) checkImgList.get(i))
					.getMateriel().getFactoryName());
			checkImg.setSpec(((DzscInnerMergeData) checkImgList.get(i))
					.getMateriel().getFactorySpec());
			checkImg.setUnit(((DzscInnerMergeData) checkImgList.get(i))
					.getDzscTenInnerMerge().getTenUnit());
			checkImgLists.add(checkImg);
		}
		dgCommonQuery.setDataSource(checkImgLists);
		List tableColumns = new Vector();
		tableColumns.add(new JTableListColumn("料件序号", "seqNum", 50,Integer.class));
		tableColumns.add(new JTableListColumn("料件料号", "ptNum", 50));
		tableColumns.add(new JTableListColumn("商品编码", "complex.code", 80));
		tableColumns.add(new JTableListColumn("料件名称", "name", 100));
		tableColumns.add(new JTableListColumn("规格型号", "spec", 100));
		DgCommonQuery.setTableColumns(tableColumns);
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}
}
