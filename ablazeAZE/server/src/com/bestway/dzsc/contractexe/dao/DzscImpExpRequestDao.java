/*
 * Created on 2005-3-29
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.contractexe.dao;

import java.util.ArrayList;
import java.util.List;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;
import com.bestway.common.MaterielType;
import com.bestway.common.constant.DzscState;
import com.bestway.dzsc.contractexe.entity.MakeDzscCustomsDeclaration;

/**
 * com.bestway.dzsc.contractexe.dao.DzscImpExpRequestDao
 * 
 * @author Administrator
 */
public class DzscImpExpRequestDao extends BaseDao {

    /**
     * 查找Dzsc申请单物料主档来自类别-->备案
     * 
     * @param materielType 物料类别
     * @param conditionSql 插入的Sql语句
     * @param conditionParameter 插入的Sql语句的参数
     * @param index 数据的开始下表
     * @param length 数据的长度
     * @param property 属性
     * @param value 属性的值
     * @param isLike 用“like”还是用“＝”，当为true是用“like”
     * @return List 是Materiel型
     */
    public List findMaterielPutOnRecordsByDzscRequestBill(String materielType,
            String conditionSql, Object[] conditionParameter, int index,
            int length, String property, Object value, Boolean isLike) {
        String hsql = null;
        if (materielType.equals(MaterielType.MATERIEL)) {
            hsql = "select m from Materiel m left join fetch m.complex"
                    + " left join fetch m.scmCoc"
                    + " left join fetch m.calUnit"
                    + " where  "
                    + " (m.scmCoi.coiProperty=? or m.scmCoi.coiProperty=?)"
                    + " and m.company.id=? "
                    + " and m.ptNo in ("
                    + "					select e.materiel.ptNo from DzscInnerMergeData e "
                    // + " left join fetch e.materiel"
                    // + " left join fetch e.tenComplex"
                    + "						where e.imrType = ? "
                    + "						and e.company = ?  "
                    + "						and e.head.type = ?  "
                    + "				and e.tenComplex.code in "
                    + "( select a.complex from DzscEmsImgBill a where a.dzscEmsPorHead.declareState = ? and e.company.id = ? ) ) ";

        } else if (materielType.equals(MaterielType.FINISHED_PRODUCT)) {
            hsql = "select m from Materiel m left join fetch m.complex"
                    + " left join fetch m.scmCoc"
                    + " left join fetch m.calUnit"
                    + " where  "
                    + " (m.scmCoi.coiProperty=? or m.scmCoi.coiProperty=?)"
                    + " and m.company.id=? "
                    + " and m.ptNo in ("
                    + "					select e.materiel.ptNo from DzscInnerMergeData e "
                    // + " left join fetch e.materiel"
                    // + " left join fetch e.tenComplex"
                    + "						where e.imrType = ? "
                    + "						and e.company = ?  "
                    + "						and e.head.type = ?  "
                    + "				and e.tenComplex.code in "
                    + "( select a.complex from DzscEmsExgBill a where a.dzscEmsPorHead.declareState = ? and e.company.id = ? ) ) ";

        }
        List<Object> paramterList = new ArrayList<Object>();
        paramterList.add(materielType);
        paramterList.add(MaterielType.SEMI_FINISHED_PRODUCT);
        paramterList.add(CommonUtils.getCompany().getId());
        paramterList.add(materielType);
        paramterList.add(CommonUtils.getCompany().getId());
        paramterList.add(DzscState.EXECUTE);
        paramterList.add(DzscState.EXECUTE);
        paramterList.add(CommonUtils.getCompany().getId());

        if (hsql == null || hsql.equals("")) {
            return null;
        }
        if (conditionSql == null || conditionSql.trim().equals("")) {
            conditionSql = "";
        } else {
            hsql += " " + conditionSql;
            if (conditionParameter != null) {
                for (int i = 0; i < conditionParameter.length; i++) {
                    paramterList.add(conditionParameter[i]);
                }
            }
        }
        if (property != null && !"".equals(property) && value != null) {
            if (isLike) {
                hsql += " and  a." + property + " like ?  ";
                paramterList.add("%" + value + "%");
            } else {
                hsql += " and  a." + property + " = ?  ";
                paramterList.add(value);
            }
        }
        Object[] paramters = paramterList.toArray();
        return this.findPageList(hsql, paramters, index, length);
    }

   /**
     * 查找Dzsc申请单物料主档来自类别-->备案
     * 
     * @param materielType 物料类别
     * @param conditionSql 插入的Sql语句
     * @param conditionParameter 插入的Sql语句的参数
     * @param index 数据的开始下表
     * @param length 数据的长度
     * @param property 属性
     * @param value 属性的值
     * @param isLike 用“like”还是用“＝”，当为true是用“like”
     * @return List 是Materiel型
     */
    public List findMaterielNotPutOnRecordsByDzscRequestBill(
            String materielType, String conditionSql,
            Object[] conditionParameter, int index, int length,
            String property, Object value, Boolean isLike) {
        String hsql = null;
        if (materielType.equals(MaterielType.MATERIEL)) {
            hsql = "select m from Materiel m left join fetch m.complex"
                    + " left join fetch m.scmCoc"
                    + " left join fetch m.calUnit"
                    + " where  "
                    + " (m.scmCoi.coiProperty=? or m.scmCoi.coiProperty=?)"
                    + " and m.company.id=? "
                    + " and m.ptNo not in ("
                    + "					select e.materiel.ptNo from DzscInnerMergeData e "
                    // + " left join fetch e.materiel"
                    // + " left join fetch e.tenComplex"
                    + "						where e.imrType = ? "
                    + "						and e.company = ?  "
                    + "						and e.head.type = ?  "
                    + "				and e.tenComplex.code in "
                    + "( select a.complex from DzscEmsImgBill a where a.dzscEmsPorHead.declareState = ? and e.company.id = ? ) ) ";

        } else if (materielType.equals(MaterielType.FINISHED_PRODUCT)) {
            hsql = "select m from Materiel m left join fetch m.complex"
                    + " left join fetch m.scmCoc"
                    + " left join fetch m.calUnit"
                    + " where  "
                    + " (m.scmCoi.coiProperty=? or m.scmCoi.coiProperty=?)"
                    + " and m.company.id=? "
                    + " and m.ptNo not in ("
                    + "					select e.materiel.ptNo from DzscInnerMergeData e "
                    // + " left join fetch e.materiel"
                    // + " left join fetch e.tenComplex"
                    + "						where e.imrType = ? "
                    + "						and e.company = ?  "
                    + "						and e.head.type = ?  "
                    + "				and e.tenComplex.code in "
                    + "( select a.complex from DzscEmsExgBill a where a.dzscEmsPorHead.declareState = ? and e.company.id = ? ) ) ";

        }
        List<Object> paramterList = new ArrayList<Object>();
        paramterList.add(materielType);
        paramterList.add(MaterielType.SEMI_FINISHED_PRODUCT);
        paramterList.add(CommonUtils.getCompany().getId());
        paramterList.add(materielType);
        paramterList.add(CommonUtils.getCompany().getId());
        paramterList.add(DzscState.EXECUTE);
        paramterList.add(DzscState.EXECUTE);
        paramterList.add(CommonUtils.getCompany().getId());

        if (hsql == null || hsql.equals("")) {
            return null;
        }
        if (conditionSql == null || conditionSql.trim().equals("")) {
            conditionSql = "";
        } else {
            hsql += " " + conditionSql;
            if (conditionParameter != null) {
                for (int i = 0; i < conditionParameter.length; i++) {
                    paramterList.add(conditionParameter[i]);
                }
            }
        }
        if (property != null && !"".equals(property) && value != null) {
            if (isLike) {
                hsql += " and  a." + property + " like ?  ";
                paramterList.add("%" + value + "%");
            } else {
                hsql += " and  a." + property + " = ?  ";
                paramterList.add(value);
            }
        }
        Object[] paramters = paramterList.toArray();
        return this.findPageList(hsql, paramters, index, length);
    }

   /**
     * 查找物料主档来自类别-->进出口报关申请单
     * 
     * @param materielType 物料类别
     * @param conditionSql 插入的Sql语句
     * @param conditionParameter 插入的Sql语句的参数
     * @param index 数据的开始下表
     * @param length 数据的长度
     * @param property 属性
     * @param value 属性的值
     * @param isLike 用“like”还是用“＝”，当为true是用“like”
     * @return List 是Materiel型
     */
    public List findMaterielByType(String materielType, String conditionSql,
            Object[] conditionParameter, int index, int length,
            String property, Object value, Boolean isLike) {
        List<Object> paramterList = new ArrayList<Object>();
        String hsql = "select b from Materiel b left join fetch b.calUnit"
                + " left join fetch b.complex "
                + " where ( b.scmCoi.coiProperty=? or b.scmCoi.coiProperty=? ) "
                + " and b.company.id=?";
        paramterList.add(materielType);
        paramterList.add(MaterielType.SEMI_FINISHED_PRODUCT);
        paramterList.add(CommonUtils.getCompany().getId());

        if (hsql == null || hsql.equals("")) {
            return null;
        }
        if (conditionSql == null || conditionSql.trim().equals("")) {
            conditionSql = "";
        } else {
            hsql += " " + conditionSql;
            if (conditionParameter != null) {
                for (int i = 0; i < conditionParameter.length; i++) {
                    paramterList.add(conditionParameter[i]);
                }
            }
        }
        if (property != null && !"".equals(property) && value != null) {
            if (isLike) {
                hsql += " and  a." + property + " like ?  ";
                paramterList.add("%" + value + "%");
            } else {
                hsql += " and  a." + property + " = ?  ";
                paramterList.add(value);
            }
        }
        return this.findPageList(hsql, paramterList.toArray(), index, length);
    }

    /**
     * 查找自用商品编码
     * 
     * @param sValue 商品编码
     * @return Complex 自用商品编码，返回符合条件的第一条
     */
    public Complex findComplexByCode(String sValue) {
        if (sValue != null) {
            String hsql = "select a from Complex a where code=? and (isOut <> '1' or isOut is null)";
            List list = find(hsql, new Object[] { sValue });
            if (list.size() > 0) {
                return (Complex) list.get(0);
            }
        }
        return null;
    }

    /**
     * 保存申请单转报关单中间表
     * 
     * @param m 申请单转报关单中间表
     */
    public void saveMakeDzscCustomsDeclaration(MakeDzscCustomsDeclaration m) {
        this.saveOrUpdate(m);
    }

    /**
     * 删除申请单转报关单中间表
     * 
     * @param m 申请单转报关单中间表
     */
    public void deleteMakeDzscCustomsDeclaration(MakeDzscCustomsDeclaration m) {
        this.delete(m);
    }

    /**
     * 查找申请单转报关单中间表来自报关单商品信息
     * 
     * @param id 报关单表头Id
     * @return List 是MakeDzscCustomsDeclaration型，申请单转报关单中间表
     */
    public List findMakeDzscCustomsDeclarationByCommInfo(String id) {
        return this.find(
                "select m from MakeDzscCustomsDeclaration m where m.company.id = ? "
                        + " and m.dzscCustomsDeclarationCommInfo.id = ?   ",
                new Object[] { CommonUtils.getCompany().getId(), id });
    }

    /**
     * 查找通关备案料件来自进出口申请单 料号
     * 
     * @param ptNo 料号
     * @param dzscEmsPorHeadId 手册通关备案表头Id
     * @return List 是DzscEmsImgBill型，通关备案料件
     */
    public List findDzscEmsImgBillByPtNo(String ptNo, String dzscEmsPorHeadId) {
        List<Object> paramterList = new ArrayList<Object>();
        String hsql = "select a from DzscEmsImgBill a"
                + " where a.dzscEmsPorHead.declareState = ?"
                + " and a.dzscEmsPorHead.id = ? "
                + " and a.company.id = ? and "
                + " a.tenSeqNum in (select d.dzscTenInnerMerge.tenSeqNum from DzscInnerMergeData d "
                 + " where d.imrType = ?"
                + " and d.company.id = ?"
                + " and d.stateMark = ?"
                + " and d.materiel.ptNo = ? )";
        paramterList.add(DzscState.EXECUTE);
        paramterList.add(dzscEmsPorHeadId);
        paramterList.add(CommonUtils.getCompany().getId());
        paramterList.add(MaterielType.MATERIEL);
        paramterList.add(CommonUtils.getCompany().getId());
        paramterList.add(DzscState.EXECUTE);
        paramterList.add(ptNo);
        return this.find(hsql, paramterList.toArray());
    }
    
    /**
     * 获取料件，成品对应的归并序号
     * @param ptNo
     * @param materielType
     * @return
     */
    public Integer findTenSeqNum(String ptNo,String materielType){
    	String hsql ="select d.dzscTenInnerMerge.tenSeqNum from DzscInnerMergeData d "
    		+ " where d.imrType = ? "
    		 + " and d.company.id = ?"
             + " and d.stateMark = ?"
             + " and d.materiel.ptNo = ? )";
    	List list = this.find(hsql, new Object[]{materielType,CommonUtils.getCompany().getId(),DzscState.EXECUTE,ptNo});
    	if(list!=null && list.size()>0){
    		return (Integer)list.get(0);
    	}
    	return null;
    }

    /**
     * 查找通关备案成品来自进出口申请单 料号
     * 
     * @param ptNo 料号
     * @param dzscEmsPorHeadId 手册通关备案表头Id
     * @return List 是DzscEmsImgBill型，通关备案料件
     */
    public List findDzscEmsExgBillByPtNo(String ptNo, String dzscEmsPorHeadId) {
        List<Object> paramterList = new ArrayList<Object>();
        String hsql = "select a from DzscEmsExgBill a "
                + "	where a.dzscEmsPorHead.declareState = ? "
                + "   and a.dzscEmsPorHead.id = ? "
                + "   and a.company.id = ? and "
                + "  a.tenSeqNum in (select d.dzscTenInnerMerge.tenSeqNum from DzscInnerMergeData d "
                + "						where d.imrType = ?         "
                + "						and d.company.id = ?           "
                + "						and d.stateMark = ?  "
                + "						and d.materiel.ptNo = ? ) ";
        paramterList.add(DzscState.EXECUTE);
        paramterList.add(dzscEmsPorHeadId);
        paramterList.add(CommonUtils.getCompany().getId());
        paramterList.add(MaterielType.FINISHED_PRODUCT);
        paramterList.add(CommonUtils.getCompany().getId());
        paramterList.add(DzscState.EXECUTE);
        paramterList.add(ptNo);
        return this.find(hsql, paramterList.toArray());
    }

}
