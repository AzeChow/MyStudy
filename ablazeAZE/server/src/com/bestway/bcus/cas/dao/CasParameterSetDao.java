/*
 * Created on 2004-7-29
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.cas.dao;

import java.util.List;

import com.bestway.bcus.cas.parameterset.entity.BillCorrespondingControl;
import com.bestway.bcus.cas.parameterset.entity.BillCorrespondingOption;
import com.bestway.bcus.cas.parameterset.entity.CasBillOption;
import com.bestway.bcus.cas.parameterset.entity.LotControl;
import com.bestway.bcus.cas.parameterset.entity.OtherOption;
import com.bestway.bcus.cas.parameterset.entity.WriteAccountYear;
import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;

/**
 * 海关帐参数 Dao
 * @author luosheng
 * 2009/09/07
 */
public class CasParameterSetDao extends BaseDao {

    /**
     * 查找单据对应控制对象
     * 
     * @return 单据对应的控制对象 (是否是手工控制 是否是特殊控制......)
     */
    public BillCorrespondingControl findBillCorrespondingControl() {

        List list = this.find(
                "select a from BillCorrespondingControl a where a.company= ? ",
                new Object[] { CommonUtils.getCompany() });
        if (list.size() > 0) {
            return (BillCorrespondingControl) list.get(0);
        }
        return null;
    }

    /**
     * 查找海关帐基本单据项目设定
     * @return 海关帐单据项目设定  (单据是否允许重复 双击是否修改还是流览.......)
     */
    public CasBillOption findCasBillOption() {

        List list = this.find(
                "select a from CasBillOption a where a.company= ? ",
                new Object[] { CommonUtils.getCompany() });
        if (list.size() > 0) {
            return (CasBillOption) list.get(0);
        }
        return null;
    }

    /**
     * 查找制单号控制对象
     * 
     * @return 制单号控制对象 (车间入库单据录入时需要制造令 车间返回单据录入时需要制造令......)
     */
    public LotControl findLotControl() {

        List list = this.find("select a from LotControl a where a.company= ? ",
                new Object[] { CommonUtils.getCompany() });
        if (list.size() > 0) {
            return (LotControl) list.get(0);
        }
        return null;
    }

    /**
     * 查找其它项目选项
     * 
     * @return 其他选项(名称规格是否重复 进出仓栏只显示月结存数......)
     */
    public OtherOption findOtherOption() {

        List list = this.find(
                "select a from OtherOption a where a.company= ? ",
                new Object[] { CommonUtils.getCompany() });
        if (list.size() > 0) {
            return (OtherOption) list.get(0);
        }
        return null;
    }

    /**
     * 查找记帐年度对象
     * 
     * @return  记帐年度对象 (海关帐年度控制  海关帐分客户统计)
     */
    public WriteAccountYear findWriteAccountYear() {
    	//
        List list = this.find(
                "select a from WriteAccountYear a  ");
        if (list.size() > 0) {
            return (WriteAccountYear) list.get(0);
        }
        return null;
    }

    /**
     * 查找单据对应选项
     * 
     * @return 单据对应选项 (是否根据对应报关单计算单据的合同单价 是否跨年度对应......)
     */
    public BillCorrespondingOption findBillCorrespondingOption() {

        List list = this.find(
                "select a from BillCorrespondingOption a where a.company= ? ",
                new Object[] { CommonUtils.getCompany() });
        if (list.size() > 0) {
            return (BillCorrespondingOption) list.get(0);
        }
        return null;
    }

    /**
     * 保存单据对应控制对象
     * @param temp 单据对应的控制对象 (是否是手工控制 是否是特殊控制......)
     */
    public void saveBillCorrespondingControl(BillCorrespondingControl temp) {
        this.saveOrUpdate(temp);
    }

    /**
     * 保存海关帐基本单据项目设定
     * @param temp 海关帐单据项目设定  (单据是否允许重复 双击是否修改还是流览.......)
     */
    public void saveCasBillOption(CasBillOption temp) {
        this.saveOrUpdate(temp);
    }

    /**
     * 保存制单号控制对象
     * @param temp 制单号控制对象 (车间入库单据录入时需要制造令 车间返回单据录入时需要制造令......)
     */
    public void saveLotControl(LotControl temp) {
        this.saveOrUpdate(temp);
    }

    /**
     * 保存其它项目选项
     * @param temp  其他选项(名称规格是否重复 进出仓栏只显示月结存数......)
     */
    public void saveOtherOption(OtherOption temp) {
        this.saveOrUpdate(temp);
    }

    /**
     * 保存记帐年度对象
     * @param temp  记帐年度对象 (海关帐年度控制  海关帐分客户统计)
     */
    public void saveWriteAccountYear(WriteAccountYear temp) {
        this.saveOrUpdate(temp);
    }

    /**
     * 保存单据对应选项
     * @param temp 单据对应选项 (是否根据对应报关单计算单据的合同单价 是否跨年度对应......)
     */
    public void saveBillCorrespondingOption(BillCorrespondingOption temp) {
        this.saveOrUpdate(temp);
    }

    /**
     * 清除单据对应
     * 
     */
    public void clearBillCorresponding() {
       
        // 删除报关单商品信息与海关帐单据的对应
        this
                .batchUpdateOrDelete(
                        "delete from CustomsDeclarationCommInfoBillCorresponding m where m.company.id = ? ",
                        CommonUtils.getCompany().getId());
        // 修改所有单据信息       
        this.batchUpdateOrDelete("update BillDetailProduct set customNo = ? ,customNum = ?  where company.id = ? ",
                new Object[] { null, 0.0 ,CommonUtils.getCompany().getId()});
        this.batchUpdateOrDelete("update BillDetailMateriel set customNo = ? ,customNum = ?  where company.id = ? ",
                new Object[] { null, 0.0 ,CommonUtils.getCompany().getId()});
        this.batchUpdateOrDelete("update BillDetailFixture set customNo = ? ,customNum = ?  where company.id = ? ",
                new Object[] { null, 0.0 ,CommonUtils.getCompany().getId()});
        this.batchUpdateOrDelete("update BillDetailHalfProduct set customNo = ? ,customNum = ?  where company.id = ? ",
                new Object[] { null, 0.0 ,CommonUtils.getCompany().getId()});
        this.batchUpdateOrDelete("update BillDetailLeftoverMateriel set customNo = ? ,customNum = ?  where company.id = ? ",
                new Object[] { null, 0.0 ,CommonUtils.getCompany().getId()});
        this.batchUpdateOrDelete("update BillDetailRemainProduct set customNo = ? ,customNum = ?  where company.id = ? ",
                new Object[] { null, 0.0 ,CommonUtils.getCompany().getId()});
    }

}