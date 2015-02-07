package com.bestway.dzsc.client.impexprequest;

import java.awt.Component;
import java.util.List;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DgCommonQuery;
import com.bestway.bcus.enc.entity.ApplyToCustomsBillList;
import com.bestway.client.util.JTableListColumn;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpType;
import com.bestway.dzsc.customslist.action.DzscListAction;

public class DzscImpExpRequestQuery {
	private static DzscImpExpRequestQuery requestQuery = null;

	private DzscImpExpRequestQuery() {

	}

	public synchronized static DzscImpExpRequestQuery getInstance() {
		if (requestQuery == null) {
			requestQuery = new DzscImpExpRequestQuery();
		}
		return requestQuery;
	}
	
    /**
     * 获得状态为草稿的报关清单来自进出口类型
     */
    public Object getDzscCustomsBillListByType(int impExpType) {
        List list = new Vector();
        list.add(new JTableListColumn("进出口类型", "impExpType", 100));
        list.add(new JTableListColumn("清单状态", "listState", 100));
        list.add(new JTableListColumn("电子帐册编号", "emsHeadH2k.emsNo", 80));
        list.add(new JTableListColumn("清单编号", "listNo", 100));
        list.add(new JTableListColumn("经营单位编码", "emsHeadH2k.tradeCode", 100));
        DgCommonQuery.setTableColumns(list);
        final DgCommonQuery dgCommonQuery = new DgCommonQuery();

        DzscListAction dzscListAction = (DzscListAction) CommonVars.getApplicationContext()
                .getBean("dzscListAction");
        List dataSource = dzscListAction.findDzscCustomsBillListByTypeAndState(
                new Request(CommonVars.getCurrUser(), true), impExpType,
                ApplyToCustomsBillList.DRAFT);
        dgCommonQuery.setDataSource(dataSource);
        dgCommonQuery.setTitle("请选择报关清单");
        DgCommonQuery.setSingleResult(true);
        dgCommonQuery.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent e) {
                JTable table = dgCommonQuery.getJTable();
                table.getColumnModel().getColumn(1).setCellRenderer(
                        new DefaultTableCellRenderer() {
                            public Component getTableCellRendererComponent(
                                    JTable table, Object value,
                                    boolean isSelected, boolean hasFocus,
                                    int row, int column) {
                                super.getTableCellRendererComponent(table,
                                        value, isSelected, hasFocus, row,
                                        column);
                                String str = "";
                                if (value != null) {
                                    switch (Integer.parseInt(value.toString())) {
                                    case ImpExpType.DIRECT_IMPORT:
                                        str = "料件进口";
                                        break;
                                    case ImpExpType.TRANSFER_FACTORY_IMPORT:
                                        str = "料件转厂";
                                        break;
                                    case ImpExpType.BACK_FACTORY_REWORK:
                                        str = "退厂返工";
                                        break;
                                    case ImpExpType.GENERAL_TRADE_IMPORT:
                                        str = "一般贸易进口";
                                        break;
                                    case ImpExpType.DIRECT_EXPORT:
                                        str = "成品出口";
                                        break;
                                    case ImpExpType.TRANSFER_FACTORY_EXPORT:
                                        str = "转厂出口";
                                        break;
                                    case ImpExpType.BACK_MATERIEL_EXPORT:
                                        str = "退料出口";
                                        break;
                                    case ImpExpType.REWORK_EXPORT:
                                        str = "返工复出";
                                        break;
                                    case ImpExpType.REMIAN_MATERIAL_BACK_PORT:
                                        str = "边角料退港";
                                        break;
                                    case ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES:
                                        str = "边角料内销";
                                        break;
                                    case ImpExpType.GENERAL_TRADE_EXPORT:
                                        str = "一般贸易出口";
                                        break;
                                    }
                                }
                                this.setText(str);
                                return this;
                            }
                        });
                table.getColumnModel().getColumn(2).setCellRenderer(
                        new DefaultTableCellRenderer() {
                            public Component getTableCellRendererComponent(
                                    JTable table, Object value,
                                    boolean isSelected, boolean hasFocus,
                                    int row, int column) {
                                super.getTableCellRendererComponent(table,
                                        value, isSelected, hasFocus, row,
                                        column);
                                String str = "";
                                if (value != null) {
                                    switch (Integer.parseInt(value.toString())) {
                                    case ApplyToCustomsBillList.DRAFT:
                                        str = "草稿";
                                        break;
                                    case ApplyToCustomsBillList.ALREADY_SEND:
                                        str = "审批通过";
                                        break;
                                    case ApplyToCustomsBillList.PASSED:
                                        str = "审批未通过";
                                        break;
                                     case ApplyToCustomsBillList.Effect:
                                         str = "生效";
                                        break;
//                                    case ApplyToCustomsBillList.GENERATED_CUSTOMS_DECLARATION:
//                                        str = "已转报关单";
//                                        break;
                                   
                                    }
                                }
                                this.setText(str);
                                return this;
                            }
                        });
            }
        });

        dgCommonQuery.setVisible(true);
        if (dgCommonQuery.isOk()) {
            return dgCommonQuery.getReturnValue();
        }
        return null;
    }

}
