/*
 * Created on 2005-10-19
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.cas.bill.entity;

import com.bestway.common.CommonUtils;
import com.bestway.common.MaterielType;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.SBillType;

/**
 * @author ls
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class BillUtil {
    /**
     * 获取明细表来自产品类型
     * 
     * @param materielType
     * @return  tableName 明细表表名
     */
    public static String getBillDetailTableNameByMaterielType(
            String materielType) {
        String tableName = null;
        if (materielType != null) {
            materielType = materielType.trim();
        }
        if (MaterielType.FINISHED_PRODUCT.equals(materielType)) {
            tableName = " BillDetailProduct   ";
        } else if (MaterielType.MATERIEL.equals(materielType)) {
            tableName = " BillDetailMateriel   ";
        } else if (MaterielType.MACHINE.equals(materielType)) {
            tableName = " BillDetailFixture   ";

        } else if (MaterielType.SEMI_FINISHED_PRODUCT.equals(materielType)) {
            tableName = " BillDetailHalfProduct   ";

        } else if (MaterielType.REMAIN_MATERIEL.equals(materielType)) {
            tableName = " BillDetailLeftoverMateriel   ";

        } else if (MaterielType.BAD_PRODUCT.equals(materielType)) {
            tableName = " BillDetailRemainProduct   ";

        }

        /*System.out.println("CAS Get BillDetail ["
                + (tableName == null ? "" : tableName) + "] By ["
                + materielType + " ]");*/
        return tableName;
    }

    /**
     * 获取明细表来自产品类型
     * 
     * @param materielType  产品类型
     * @return tableName 单据表表名
     */
    public static String getBillMasterTableNameByMaterielType(
            String materielType) {
        String tableName = null;
        if (materielType != null) {
            materielType = materielType.trim();
        }
        if (MaterielType.FINISHED_PRODUCT.equals(materielType)) {
            tableName = " BillMasterProduct   ";
        } else if (MaterielType.MATERIEL.equals(materielType)) {
            tableName = " BillMasterMateriel   ";
        } else if (MaterielType.MACHINE.equals(materielType)) {
            tableName = " BillMasterFixture   ";

        } else if (MaterielType.SEMI_FINISHED_PRODUCT.equals(materielType)) {
            tableName = " BillMasterHalfProduct   ";

        } else if (MaterielType.REMAIN_MATERIEL.equals(materielType)) {
            tableName = " BillMasterLeftoverMateriel   ";

        } else if (MaterielType.BAD_PRODUCT.equals(materielType)) {
            tableName = " BillMasterRemainProduct   ";

        }
        return tableName;

    }

    /**
     * 获取明细表来自单据类型
     * 
     * @param sBillType  单据类型
     * @return  tableName 明细表表名
     */
    public static String getBillDetailTableNameByBillType(int sBillType) {
        String tableName = null;
        if (sBillType == SBillType.PRODUCE_IN
                || sBillType == SBillType.PRODUCE_OUT
                || sBillType == SBillType.PRODUCE_INOUT) {
            tableName = " BillDetailProduct ";

        } else if (sBillType == SBillType.MATERIEL_IN
                || sBillType == SBillType.MATERIEL_OUT
                || sBillType == SBillType.MATERIEL_INOUT) {
            tableName = " BillDetailMateriel ";

        } else if (sBillType == SBillType.FIXTURE_IN
                || sBillType == SBillType.FIXTURE_OUT
                || sBillType == SBillType.FIXTURE_INOUT) {
            tableName = " BillDetailFixture ";

        } else if (sBillType == SBillType.HALF_PRODUCE_IN
                || sBillType == SBillType.HALF_PRODUCE_OUT
                || sBillType == SBillType.HALF_PRODUCE_INOUT) {
            tableName = " BillDetailHalfProduct ";

        } else if (sBillType == SBillType.LEFTOVER_MATERIEL_IN
                || sBillType == SBillType.LEFTOVER_MATERIEL_OUT
                || sBillType == SBillType.LEFTOVER_MATERIEL_INOUT) {
            tableName = " BillDetailLeftoverMateriel ";

        } else if (sBillType == SBillType.REMAIN_PRODUCE_IN
                || sBillType == SBillType.REMAIN_PRODUCE_OUT
                || sBillType == SBillType.REMAIN_PRODUCE_INOUT) {
            tableName = " BillDetailRemainProduct ";
        }

        /*System.out.println("CAS Get BillDetail ["
                + (tableName == null ? "" : tableName) + "] By [" + sBillType
                + " ]");*/

        return tableName;
    }

    /**
     * 获取主表来自单据类型
     * 
     * @param sBillType  单据类型
     * @return  tableName 明细表表名
     */
    public static String getBillMasterTableNameByBillType(int sBillType) {
        String tableName = null;
        if (sBillType == SBillType.PRODUCE_IN
                || sBillType == SBillType.PRODUCE_OUT
                || sBillType == SBillType.PRODUCE_INOUT) {
            tableName = " BillMasterProduct ";

        } else if (sBillType == SBillType.MATERIEL_IN
                || sBillType == SBillType.MATERIEL_OUT
                || sBillType == SBillType.MATERIEL_INOUT) {
            tableName = " BillMasterMateriel ";

        } else if (sBillType == SBillType.FIXTURE_IN
                || sBillType == SBillType.FIXTURE_OUT
                || sBillType == SBillType.FIXTURE_INOUT) {
            tableName = " BillMasterFixture ";

        } else if (sBillType == SBillType.HALF_PRODUCE_IN
                || sBillType == SBillType.HALF_PRODUCE_OUT
                || sBillType == SBillType.HALF_PRODUCE_INOUT) {
            tableName = " BillMasterHalfProduct ";

        } else if (sBillType == SBillType.LEFTOVER_MATERIEL_IN
                || sBillType == SBillType.LEFTOVER_MATERIEL_OUT
                || sBillType == SBillType.LEFTOVER_MATERIEL_INOUT) {
            tableName = " BillMasterLeftoverMateriel ";

        } else if (sBillType == SBillType.REMAIN_PRODUCE_IN
                || sBillType == SBillType.REMAIN_PRODUCE_OUT
                || sBillType == SBillType.REMAIN_PRODUCE_INOUT) {
            tableName = " BillMasterRemainProduct ";
        }
        /*System.out.println("CAS Get BillMaster ["
                + (tableName == null ? "" : tableName) + "] By [" + sBillType
                + " ]");*/
        return tableName;
    }
    
    
    /**
     * 获取主表来自单据类型
     * 
     * @param sBillType  单据类型
     * @return  tableName 明细表表名
     */
    public static String getBillMasterTableNameByBillType2(int sBillType) {
        String tableName = null;
        if (sBillType == SBillType.PRODUCE_IN
                || sBillType == SBillType.PRODUCE_OUT
                || sBillType == SBillType.PRODUCE_INOUT) {
            tableName = " BillMasterProduct ";

        } else if (sBillType == SBillType.MATERIEL_IN
                || sBillType == SBillType.MATERIEL_OUT
                || sBillType == SBillType.MATERIEL_INOUT) {
            tableName = " BillMasterMateriel ";

        } else if (sBillType == SBillType.FIXTURE_IN
                || sBillType == SBillType.FIXTURE_OUT
                || sBillType == SBillType.FIXTURE_INOUT) {
            tableName = " BillMasterFixture ";

        } else if (sBillType == SBillType.HALF_PRODUCE_IN
                || sBillType == SBillType.HALF_PRODUCE_OUT
                || sBillType == SBillType.HALF_PRODUCE_INOUT) {
            tableName = " BillMasterHalfProduct ";

        } else if (sBillType == SBillType.LEFTOVER_MATERIEL_IN
                || sBillType == SBillType.LEFTOVER_MATERIEL_OUT
                || sBillType == SBillType.LEFTOVER_MATERIEL_INOUT) {
            tableName = " BillMasterMateriel ";

        } else if (sBillType == SBillType.REMAIN_PRODUCE_IN
                || sBillType == SBillType.REMAIN_PRODUCE_OUT
                || sBillType == SBillType.REMAIN_PRODUCE_INOUT) {
            tableName = " BillMasterRemainProduct ";
        }
        /*System.out.println("CAS Get BillMaster ["
                + (tableName == null ? "" : tableName) + "] By [" + sBillType
                + " ]");*/
        return tableName;
    }
    

    /**
     * 获取明细表来自进出口类型
     * 
     * @param impExpType  进出口类型
     * @return   getBillDetailTableNameByMaterielType(materielType) 与进出口类型匹配的明细表表名
     */
    public static String getBillDetailTableNameByImpExpType(int impExpType) {
        String materielType = getMaterielTypeByImpExpType(impExpType);
        return getBillDetailTableNameByMaterielType(materielType);
    }

    /**
     * 获取明细表头来自进出口类型
     * 
     * @param impExpType  进出口类型
     * @return  getBillMasterTableNameByMaterielType(materielType)  与进出口类型匹配的单据表名
     */
    public static String getBillMasterTableNameByImpExpType(int impExpType) {
        String materielType = getMaterielTypeByImpExpType(impExpType);
        return getBillMasterTableNameByMaterielType(materielType);
    }

    /**
     * 获得物料类型来自进出口类型
     * 
     * @param impExpType  进出口类型
     * @return  materielType  与之匹配的物料类型
     */
    public static String getMaterielTypeByImpExpType(int impExpType) {
        String materielType = MaterielType.MATERIEL;
        switch (impExpType) {
        case ImpExpType.DIRECT_IMPORT:
        case ImpExpType.TRANSFER_FACTORY_IMPORT:
        case ImpExpType.GENERAL_TRADE_IMPORT:
        case ImpExpType.BACK_MATERIEL_EXPORT:
        case ImpExpType.REMIAN_MATERIAL_BACK_PORT:
        case ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES:
        case ImpExpType.REMAIN_FORWARD_EXPORT:
            materielType = MaterielType.MATERIEL;
            break;
        case ImpExpType.BACK_FACTORY_REWORK:
        case ImpExpType.DIRECT_EXPORT:
        case ImpExpType.TRANSFER_FACTORY_EXPORT:
        case ImpExpType.REWORK_EXPORT:
        case ImpExpType.GENERAL_TRADE_EXPORT:
            materielType = MaterielType.FINISHED_PRODUCT;
            break;
        case ImpExpType.EQUIPMENT_IMPORT:
        case ImpExpType.BACK_PORT_REPAIR:
        case ImpExpType.EQUIPMENT_BACK_PORT:
            materielType = MaterielType.MACHINE;
            break;
        }
        return materielType;
    }

    
    /**
     * 获取明细表来自产品类型的名字
     * 
     * @param materielType  物料类型
     * @return  tableName 明细表表名
     */
    public static String getBillDetailTableNameByMaterielTypeName(
            String materielType) {
        String tableName = null;
        if (materielType != null) {
            materielType = materielType.trim();
        }
        if (materielType.equals("成品")) {
            tableName = " BillDetailProduct   ";
        } else if (materielType.equals("料件")) {
            tableName = " BillDetailMateriel   ";
        } else if (materielType.equals("设备")) {
            tableName = " BillDetailFixture   ";

        } else if (materielType.equals("半成品")) {
            tableName = " BillDetailHalfProduct   ";

        } else if (materielType.equals("边角料")) {
            tableName = " BillDetailLeftoverMateriel   ";

        } else if (materielType.equals("残次品")) {
            tableName = " BillDetailRemainProduct   ";

        }

        /*System.out.println("CAS Get BillDetail ["
                + (tableName == null ? "" : tableName) + "] By ["
                + materielType + " ]");*/
        return tableName;
    }
    
    /**
     * 获取明细表来自产品类型的名字
     * 
     * @param materielType  物料类型
     * @return tableName 单据表名
     */
    public static String getBillMasterTableNameByMaterielTypeName(
            String materielType) {
        String tableName = null;
        if (materielType != null) {
            materielType = materielType.trim();
        }
        if (materielType.equals("成品")) {
            tableName = " BillMasterProduct   ";
        } else if (materielType.equals("料件")) {
            tableName = " BillMasterMateriel   ";
        } else if (materielType.equals("设备")) {
            tableName = " BillMasterFixture   ";

        } else if (materielType.equals("半成品")) {
            tableName = " BillMasterHalfProduct   ";

        } else if (materielType.equals("边角料")) {
            tableName = " BillMasterLeftoverMateriel   ";

        } else if (materielType.equals("残次品")) {
            tableName = " BillMasterRemainProduct   ";

        }
        /*System.out.println("CAS Get BillMaster ["
                + (tableName == null ? "" : tableName) + "] By ["
                + materielType + " ]");*/
        return tableName;

    }
    
    
    /**
     * 获取数据库表名来自产品类型的名字
     * 
     * @param materielType  物料类型
     * @return tableName 数据库表名
     */
    public static String getDBTableNameByMaterielType(
            String materielType) {
        String tableName = null;
        if (materielType != null) {
            materielType = materielType.trim();
        }
        if (MaterielType.FINISHED_PRODUCT.equals(materielType)) {
            tableName = " BillDetailProduct   ";
        } else if (MaterielType.MATERIEL.equals(materielType)) {
            tableName = " BillDetailMateriel   ";
        } else if (MaterielType.MACHINE.equals(materielType)) {
            tableName = " BillDetailFixture   ";

        } else if (MaterielType.SEMI_FINISHED_PRODUCT.equals(materielType)) {
            tableName = " BillDetailHalfProduct   ";

        } else if (MaterielType.REMAIN_MATERIEL.equals(materielType)) {
            tableName = " BDLMateriel   ";

        } else if (MaterielType.BAD_PRODUCT.equals(materielType)) {
            tableName = " BillDetailRemainProduct   ";

        }
        //
        // 进行年度切换
        //
        tableName = CommonUtils.tableNamingStrategyByCasYear(tableName.trim());
        return tableName;
    }
    
    
    
    /**
     * 是否是海关帐单据表头
     * @param className 类名
     * @return false
     */
    public static boolean isExistBillMaster(
            String className) {
    	if (className.equalsIgnoreCase(BillMasterMateriel.class.getName())){
    		return true;
    	}else if (className.equalsIgnoreCase(BillMasterProduct.class.getName())){
    		return true;
    	}else if (className.equalsIgnoreCase(BillMasterFixture.class.getName())){
    		return true;
    	}else if (className.equalsIgnoreCase(BillMasterHalfProduct.class.getName())){
    		return true;
    	}else if (className.equalsIgnoreCase(BillMasterLeftoverMateriel.class.getName())){
    		return true;
    	}else if (className.equalsIgnoreCase(BillMasterRemainProduct.class.getName())){
    		return true;
    	}
    	return false;
    }

    /**
     * 是否是海关帐单据表体
     * @param className 类名
     * @return false
     */
    public static boolean isExistBillDetail(
            String className) {
    	if (className.equalsIgnoreCase(BillDetailMateriel.class.getName())){
    		return true;
    	}else if (className.equalsIgnoreCase(BillDetailProduct.class.getName())){
    		return true;
    	}else if (className.equalsIgnoreCase(BillDetailFixture.class.getName())){
    		return true;
    	}else if (className.equalsIgnoreCase(BillDetailHalfProduct.class.getName())){
    		return true;
    	}else if (className.equalsIgnoreCase(BillDetailLeftoverMateriel.class.getName())){
    		return true;
    	}else if (className.equalsIgnoreCase(BillDetailRemainProduct.class.getName())){
    		return true;
    	}
    	return false;
    }
    
    
    
    
    /**
     * 获得物料类型来自进出口类型
     * 
     * @param impExpType  进出口类型
     * @return  materielType  与之匹配的物料类型
     */
    public static String getMakeBillCorrespondingInfoTableName(String materielType) {
    	String tableName = "";
        if (materielType.equalsIgnoreCase(MaterielType.MATERIEL)) {
            tableName = " MakeBillCorrespondingInfoByMateriel ";

        } else if (materielType.equalsIgnoreCase(MaterielType.FINISHED_PRODUCT)) {
            tableName = " MakeBillCorrespondingInfoByProduct ";
        }
        //
        // 进行年度切换
        //
        tableName = CommonUtils.tableNamingStrategyByCasYear(tableName);
        return tableName;
    }
    
    
}
