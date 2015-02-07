
package com.bestway.client.owp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DgCommonQuery;
import com.bestway.bcus.client.manualdeclare.EmsEdiMergerClientLogic;
import com.bestway.client.util.JTableListColumn;
import com.bestway.common.Request;
import com.bestway.owp.action.OwpAnalyAction;
import com.bestway.owp.entity.OwpAppHead;
import com.bestway.owp.entity.OwpBomVersion;
/**
 * 外发加工获取查询器
 * @author hcl
 * check By hcl 2010-09-06
 */
public class OwpHclQuery {
	/**
	 * 静态实体
	 */
	private static OwpHclQuery query=null;
	/**
	 * 空构造方法
	 */
	private OwpHclQuery(){
		
	}
	/**
	 * 获取OwpHclQuery
	 * @return
	 */
	public static OwpHclQuery getInstance(){
		if(query==null){
			query=new OwpHclQuery();
		}
		return query;
	}
	
	
	
	/**
	 * 查询未在BOM表头的物料
	 * 
	 * @param materielType
	 * @return
	 * @author hcl
	 */
	public List findBomMaster() {
		OwpAnalyAction owpAnalyAction = (OwpAnalyAction) CommonVars
		.getApplicationContext().getBean("owpAnalyAction");
		List lsDataSource= owpAnalyAction
				.findBomMaster(new Request(CommonVars
						.getCurrUser(),true));
        List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("料号", "ptNo", 100));
		list.add(new JTableListColumn("工厂商品名称", "factoryName", 100));
		list.add(new JTableListColumn("工厂型号规格", "factorySpec", 100));
        DgCommonQuery.setTableColumns(list);
        final DgCommonQuery dgCommonQuery = new DgCommonQuery();
        dgCommonQuery.setDataSource(lsDataSource);
        DgCommonQuery.setSingleResult(false);
        dgCommonQuery
                .setTitle("成品资料-来自工厂物料主档（成品、半成品、料件）");
        dgCommonQuery.setVisible(true);
       
        if (dgCommonQuery.isOk()) {
            return dgCommonQuery.getReturnList();
        }
        return null;
	}
	
	
	/**
	 * 查询未在BOM表头的物料
	 * 
	 * @param materielType
	 * @return
	 * @author hcl
	 */
	public List findNotInBomDetailMaterial(OwpBomVersion version) {
		OwpAnalyAction owpAnalyAction = (OwpAnalyAction) CommonVars
		.getApplicationContext().getBean("owpAnalyAction");
		List lsDataSource= owpAnalyAction
				.findNotInBomDetailMaterial(new Request(CommonVars
						.getCurrUser(),true),version);
        List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("料号", "ptNo", 100));
		list.add(new JTableListColumn("商品编码", "complex.code", 80));
		list.add(new JTableListColumn("工厂商品名称", "factoryName", 100));
		list.add(new JTableListColumn("工厂型号规格", "factorySpec", 100));
        DgCommonQuery.setTableColumns(list);
        final DgCommonQuery dgCommonQuery = new DgCommonQuery();
        dgCommonQuery.setDataSource(lsDataSource);
        DgCommonQuery.setSingleResult(false);
        dgCommonQuery
                .setTitle("物料资料-来自工厂物料主档（料件、半成品）");
        dgCommonQuery.setVisible(true);
        
        if (dgCommonQuery.isOk()) {
            return dgCommonQuery.getReturnList();
        }
        return null;
	}
	
	
	
	
	/**
	 * 获取所有申请表编号
	 * @return
	 */
	public Object findAllApplyNo() {
		OwpAnalyAction owpAnalyAction = (OwpAnalyAction) CommonVars
		.getApplicationContext().getBean("owpAnalyAction");
		List lsDataSource= owpAnalyAction
				.findAllApplyNo();
        List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("申请表编号", "appNo", 200));
		list.add(new JTableListColumn("承揽企业名称", "inTradeName", 250));
        DgCommonQuery.setTableColumns(list);
        final DgCommonQuery dgCommonQuery = new DgCommonQuery();
        dgCommonQuery.setDataSource(lsDataSource);
        DgCommonQuery.setSingleResult(false);
        dgCommonQuery
                .setTitle("申请表表头");
        dgCommonQuery.setVisible(true);
        if (dgCommonQuery.isOk()) {
        	if(dgCommonQuery.getReturnList()!=null&&dgCommonQuery.getReturnList().size()!=0)
            return dgCommonQuery.getReturnList().get(0);
        }
        return null;
	}
	/**
	 * 获取所有申请表编号
	 * @return
	 */
	public Object findAllInTradeName() {
		OwpAnalyAction owpAnalyAction = (OwpAnalyAction) CommonVars
		.getApplicationContext().getBean("owpAnalyAction");
		List lsDataSource= owpAnalyAction
				.findAllApplyNo();
        List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("承揽企业名称", "inTradeName", 250));
        DgCommonQuery.setTableColumns(list);
        final DgCommonQuery dgCommonQuery = new DgCommonQuery();
        Map map= new HashMap();
        for(int i=0;i<lsDataSource.size();i++){
        	OwpAppHead head=(OwpAppHead)lsDataSource.get(i);
        	map.put(head.getInTradeName(),head);
        }
        lsDataSource=new ArrayList(map.values());
        dgCommonQuery.setDataSource(lsDataSource);
        DgCommonQuery.setSingleResult(false);
        dgCommonQuery
                .setTitle("承揽企业名称");
        dgCommonQuery.setVisible(true);
        if (dgCommonQuery.isOk()) {
        	if(dgCommonQuery.getReturnList()!=null&&dgCommonQuery.getReturnList().size()!=0)
            return dgCommonQuery.getReturnList().get(0);
        }
        return null;
	}
	
	
	
}
