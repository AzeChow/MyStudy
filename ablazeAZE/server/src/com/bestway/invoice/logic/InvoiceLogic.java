/*
 * Created on 2005-7-18
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.invoice.logic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.bestway.bcs.contractanalyse.entity.ExpFinishProductStat;
import com.bestway.bcus.enc.dao.EncDao;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.constant.ActionState;
import com.bestway.common.constant.InvoiceState;
import com.bestway.common.constant.ProjectType;
import com.bestway.customs.common.dao.BaseEncDao;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.invoice.action.InvoiceAction;
import com.bestway.invoice.dao.InvoiceDao;
import com.bestway.invoice.entity.Invoice;
import com.bestway.invoice.entity.InvoiceParameters;
import com.bestway.invoice.entity.TempCustomsDelcarationInfo;
import com.bestway.invoice.entity.TempInvoiceUsedStatInfo;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class InvoiceLogic {
    private InvoiceDao invoiceDao;

	private InvoiceAction invoiceAction;

	private EncDao bcusDao;

	private com.bestway.bcs.contractexe.dao.ContractExeDao bcsDao;

    /**
	 * @return Returns the invoiceDao.
	 */
    public InvoiceDao getInvoiceDao() {
        return invoiceDao;
    }

    /**
     * @param invoiceDao
     *            The invoiceDao to set.
     */
    public void setInvoiceDao(InvoiceDao invoiceDao) {
        this.invoiceDao = invoiceDao;
    }

    /**
     * @return Returns the bcsDao.
     */
    public com.bestway.bcs.contractexe.dao.ContractExeDao getBcsDao() {
        return bcsDao;
    }

    /**
     * @param bcsDao
     *            The bcsDao to set.
     */
    public void setBcsDao(com.bestway.bcs.contractexe.dao.ContractExeDao bcsDao) {
        this.bcsDao = bcsDao;
    }

    /**
     * @return Returns the bcusDao.
     */
    public EncDao getBcusDao() {
        return bcusDao;
    }

    /**
     * @param bcusDao
     *            The bcusDao to set.
     */
    public void setBcusDao(EncDao bcusDao) {
        this.bcusDao = bcusDao;
    }
 

    /**
     * 抓取报关单资料
     * @param invoice 发票
     * @return 与指定的发票号匹配的出口报关单资料
     */
    public List findExportCustomsDeclaration(Invoice invoice) {
        List lsResult = new ArrayList();
        List list = this.invoiceDao.findExportCustomsDeclaration(
                ProjectType.BCUS, invoice.getBeginDate(), invoice.getEndDate());
        organizeTempInfo(lsResult, list, ProjectType.BCUS);
        list = this.invoiceDao.findExportCustomsDeclaration(ProjectType.BCS,
                invoice.getBeginDate(), invoice.getEndDate());
        organizeTempInfo(lsResult, list, ProjectType.BCS);
        return lsResult;
    }

    /**
     * 分模块的临时报关单信息
     * @param lsResult  临时报关单信息
     * @param lsTemp list临时的对象信息
     * @param projectType 模块类型(报关单来源)
     */
    private void organizeTempInfo(List lsResult, List lsTemp,
            Integer projectType) {
        for (int i = 0; i < lsTemp.size(); i++) {
            Object[] objs = (Object[]) lsTemp.get(i);
            TempCustomsDelcarationInfo temp = new TempCustomsDelcarationInfo();
            if (objs[1] == null || "".equals(objs[1].toString().trim())) {
                continue;
            }
            if (objs[0] != null) {
                temp.setCustomsDeclarationId(objs[0].toString());
            }
            if (objs[1] != null) {
                temp.setCustomsDeclarationCode(objs[1].toString());
            }
            if (objs[2] != null) {
                temp.setMoney(Double.valueOf(objs[2].toString()));
            }
            temp.setProjectType(projectType);
            lsResult.add(temp);
        }
    }

    /**
     * 自动批量生成发票
     * 
     * @param versionCode
     *            发票版次号
     * @param beginSerialNo
     *            开始流水号
     * @param endSerialNo
     *            结束流水号
     * @return 批量发票
     */
    public List autoMakeInvoice(String versionCode, String beginSerialNo,
            String endSerialNo,String year) {
        List<Invoice> lsResult = new ArrayList<Invoice>();

        Integer nBeginSerialNo = Integer.valueOf(beginSerialNo);
        Integer nEndSerialNo = Integer.valueOf(endSerialNo);
        int num = nEndSerialNo - nBeginSerialNo;
        if (num < 0) {
            return lsResult;
        }
        Date beginDate = new Date();
        GregorianCalendar worldTour = new GregorianCalendar();
        worldTour.setTime(beginDate);
        worldTour.add(GregorianCalendar.DATE, 180);

        Calendar calendar = Calendar.getInstance();
        String yearSuffix = String.valueOf(calendar.get(Calendar.YEAR))
                .substring(2);      

        for (int i = 0; i < num; i++) {        	
            String invoiceCode =year+CommonUtils.convertIntToStringByLength(nBeginSerialNo + i,
                            8);
            if (this.invoiceDao.findInvoiceByCode(invoiceCode).size() > 0) {
                continue;
            }
            Invoice invoice = new Invoice();
            invoice.setVersionCode(versionCode);
            invoice.setInvoiceCode(invoiceCode);
            invoice.setBeginDate(beginDate);
            invoice.setEndDate(worldTour.getTime());
            invoice.setState(InvoiceState.DRAFT);
            invoice.setCompany(CommonUtils.getCompany());
            invoice.setDraftDate(new Date());
            this.invoiceDao.saveInvoice(invoice);
            lsResult.add(invoice);
        }
        return lsResult;
    }

    /**
     * 保存发票
     * 
     * @param invoice 发票
     */
    public void saveInvoice(Invoice invoice) {
        this.writeBackCustomsDelarationWhenAdd(invoice);
        this.invoiceDao.saveInvoice(invoice);
    }

    /**
     * 删除发票
     * 
     * @param invoice 发票
     */
    public void deleteInvoice(Invoice invoice) {
        this.writeBackCustomsDelarationWhenDelete(invoice);
        this.invoiceDao.deleteInvoice(invoice);
    }

    /**
     * 回写报关单的发票号,新增或修改发票的报关单号时
     * 
     * @param invoice
     */
    private void writeBackCustomsDelarationWhenAdd(Invoice invoice) {
        if (invoice.getProjectType() == null) {
            return;
        }
        if (invoice.getId() == null || "".equals(invoice.getId())) {
            return;
        }
        BaseEncDao baseDao = null;
        switch (invoice.getProjectType()) {
        case ProjectType.BCUS:
            baseDao = bcusDao;
            break;
        case ProjectType.BCS:
            baseDao = bcsDao;
            break;
        }
        BaseCustomsDeclaration customsDelaration = null;
        Invoice oldInvoice = this.invoiceDao.findInvoiceById(invoice.getId());
        if (invoice.getCustomsDeclarationId() != null
                && !"".equals(invoice.getCustomsDeclarationId().trim())) {
            customsDelaration = baseDao.findCustomsDeclaration(invoice
                    .getCustomsDeclarationId());
            if (customsDelaration != null) {
                customsDelaration.setInvoiceCode(invoice.getInvoiceCode());
                baseDao.saveCustomsDeclaration(customsDelaration);
            }
            if (oldInvoice.getCustomsDeclarationId() != null
                    && !"".equals(oldInvoice.getCustomsDeclarationId().trim())
                    && !invoice.getCustomsDeclarationId().equals(
                            oldInvoice.getCustomsDeclarationId())) {
                customsDelaration = baseDao.findCustomsDeclaration(oldInvoice
                        .getCustomsDeclarationId());
                if (customsDelaration != null) {
                    customsDelaration.setInvoiceCode("");
                    baseDao.saveCustomsDeclaration(customsDelaration);
                }
            }
        } else {
            if (oldInvoice.getCustomsDeclarationId() != null
                    && !"".equals(oldInvoice.getCustomsDeclarationId().trim())) {
                customsDelaration = baseDao.findCustomsDeclaration(oldInvoice
                        .getCustomsDeclarationId());
                if (customsDelaration != null) {
                    customsDelaration.setInvoiceCode("");
                    baseDao.saveCustomsDeclaration(customsDelaration);
                }
            }
        }
    }

    /**
     * 回写报关单的发票号,删除或者作废发票时
     * 
     * @param invoice 发票
     */
    private void writeBackCustomsDelarationWhenDelete(Invoice invoice) {
        if (invoice.getProjectType() == null) {
            return;
        }
        BaseEncDao baseDao = null;
        switch (invoice.getProjectType()) {
        case ProjectType.BCUS:
            baseDao = bcusDao;
            break;
        case ProjectType.BCS:
            baseDao = bcsDao;
            break;
        }
        BaseCustomsDeclaration customsDelaration = null;
        if (invoice.getCustomsDeclarationId() != null
                && !"".equals(invoice.getCustomsDeclarationId().trim())) {
            customsDelaration = baseDao.findCustomsDeclaration(invoice
                    .getCustomsDeclarationId());
            if (customsDelaration != null) {
                customsDelaration.setInvoiceCode("");
                baseDao.saveCustomsDeclaration(customsDelaration);
            }
        }
    }

    /**
     * 使用发票
     * 
     * @param invoice 发票
     */
    public void useInvoice(Invoice invoice) {
        invoice.setState(InvoiceState.USED);
        invoice.setUsedDate(new Date());
        this.invoiceDao.saveInvoice(invoice);
    }

    /**
     * 作废发票
     * 
     * @param invoice 发票
     */
    public void cancelInvoice(Invoice invoice) {
        this.writeBackCustomsDelarationWhenDelete(invoice);
        invoice.setState(InvoiceState.CANCELED);
        invoice.setCanceledDate(new Date());
        this.invoiceDao.saveInvoice(invoice);
    }

    /**
     * 核销发票
     * 
     * @param invoice 发票
     */
    public void cancelAfterVerificationInvoice(Invoice invoice) {
        invoice.setState(InvoiceState.CANCEL_AFTER_VERIFICATION);
        invoice.setCavDate(new Date());
        this.invoiceDao.saveInvoice(invoice);
    }

    /**
     * 发票使用状态时,回卷发票
     * @param invoice 发票
     * @param invoiceState 发票状态
     */
    public void rollBackInvoice(Invoice invoice, int invoiceState) {
        invoice.setState(invoiceState);
        this.invoiceDao.saveInvoice(invoice);

    }

    /**
     * 保存发票
     * 
     * @param projectType
     *            工程类型
     * @param baseCustomsDeclaration
     *            修改过的报关单
     * @param oldInvoiceCode
     *            修改前的核消单号 null
     */
    /*
     * public void saveInvoice(int projectType, int actionState,
     * BaseCustomsDeclaration baseCustomsDeclaration, String oldInvoiceCode) { // //
     * 新增报关单时 末使用核消单-->已使用核消单 // if (actionState == ActionState.ADD) { String
     * code = baseCustomsDeclaration.getInvoiceCode(); // // 新的核消单为空不进行动作 // if
     * (code == null || "".equals(code)) { return; } List<Invoice> invoiceList =
     * this.invoiceDao.findInvoiceByCode(code); Invoice invoice =
     * invoiceList.isEmpty() == false ? invoiceList .get(0) : null;
     * invoice.setCompany(CommonUtils.getCompany());
     * invoice.setCustomsDeclarationId(baseCustomsDeclaration.getId());
     * invoice.setCustomsDeclaredCode(baseCustomsDeclaration
     * .getCustomsDeclarationCode()); invoice.setProjectType(projectType);
     * invoice.setState(InvoiceState.USED); double totalPrice =
     * this.invoiceDao.findCustomsDeclarationMoney( projectType,
     * baseCustomsDeclaration.getId()); invoice.setMoney(totalPrice);
     * this.invoiceDao.saveInvoice(invoice); } }
     */

    /**
     * 保存发票
     * 
     * @param projectType
     *            工程类型
     * @param baseCustomsDeclaration
     *            修改过的报关单
     * @param oldInvoiceCode
     *            修改前的核消单号 null
     * @param actionState 动作状态 选择 新增 修改 删除
     */
    public void saveInvoice(int projectType, int actionState,
            BaseCustomsDeclaration baseCustomsDeclaration, String oldInvoiceCode) {
        //
        // 新增报关单时 末使用核消单-->已使用核消单
        //
        if (actionState == ActionState.ADD) {
            String code = baseCustomsDeclaration.getInvoiceCode();
            //
            // 新的核消单为空不进行动作
            //
            if (code == null || "".equals(code)) {
                return;
            }
            List<Invoice> invoiceList = this.invoiceDao.findInvoiceByCode(code);
            Invoice invoice = invoiceList.isEmpty() == false ? invoiceList
                    .get(0) : null;
            invoice.setCompany(CommonUtils.getCompany());
            invoice.setCustomsDeclarationId(baseCustomsDeclaration.getId());
            invoice.setCustomsDeclaredCode(baseCustomsDeclaration
                    .getCustomsDeclarationCode());
            invoice.setProjectType(projectType);
            invoice.setState(InvoiceState.USED);
            double totalPrice = this.invoiceDao.findCustomsDeclarationMoney(
                    projectType, baseCustomsDeclaration.getId());
            invoice.setMoney(totalPrice);
            this.invoiceDao.saveInvoice(invoice);
        }

        //
        // 删除报关单时 已使用核消单-->末已使用核消单
        //
        if (actionState == ActionState.DELETE) {
            String code = baseCustomsDeclaration.getInvoiceCode();
            //
            // 新的核消单为空不进行动作
            //
            if (code == null || "".equals(code)) {
                return;
            }
            List<Invoice> invoiceList = this.invoiceDao.findInvoiceByCode(code);
            Invoice invoice = invoiceList.isEmpty() == false ? invoiceList
                    .get(0) : null;
            invoice.setCompany(CommonUtils.getCompany());
            invoice.setCustomsDeclarationId(null);
            invoice.setProjectType(null);
            invoice.setCustomsDeclaredCode(null);
            invoice.setState(InvoiceState.DRAFT);
            invoice.setMoney(0.0);
            this.invoiceDao.saveInvoice(invoice);

        }
        //
        // 修改报关单时 把老的已使用核消单-->末已使用核消单 并且 把新末使用核消单-->已使用核消单
        //
        if (actionState == ActionState.EDIT) {
            String newCode = baseCustomsDeclaration.getInvoiceCode();
            String oldCode = oldInvoiceCode;
            if (oldCode != null && !"".equals(oldCode)) {
                List<Invoice> invoiceList = this.invoiceDao
                        .findInvoiceByCode(oldCode);
                Invoice oldInvoice = invoiceList.isEmpty() == false ? invoiceList
                        .get(0)
                        : null;
                oldInvoice.setCompany(CommonUtils.getCompany());
                oldInvoice.setCustomsDeclarationId(null);
                oldInvoice.setProjectType(null);
                oldInvoice.setCustomsDeclaredCode(null);
                oldInvoice.setState(InvoiceState.DRAFT);
                oldInvoice.setMoney(0.0);
                this.invoiceDao.saveInvoice(oldInvoice);
            }
            if (newCode != null && !"".equals(newCode)) {
                List<Invoice> invoiceList = this.invoiceDao
                        .findInvoiceByCode(newCode);
                Invoice invoice = invoiceList.isEmpty() == false ? invoiceList
                        .get(0) : null;
                invoice.setCompany(CommonUtils.getCompany());
                invoice.setCustomsDeclarationId(baseCustomsDeclaration.getId());
                invoice.setCustomsDeclaredCode(baseCustomsDeclaration
                        .getCustomsDeclarationCode());
                invoice.setProjectType(projectType);
                invoice.setState(InvoiceState.USED);
                double totalPrice = this.invoiceDao
                        .findCustomsDeclarationMoney(projectType,
                                baseCustomsDeclaration.getId());
                invoice.setMoney(totalPrice);
                this.invoiceDao.saveInvoice(invoice);
            }
        }

        //
        // 删除报关单时 已使用核消单-->末已使用核消单
        //
        if (actionState == ActionState.DELETE) {
            String code = baseCustomsDeclaration.getInvoiceCode();
            //
            // 新的核消单为空不进行动作
            //
            if (code == null || "".equals(code)) {
                return;
            }
            List<Invoice> invoiceList = this.invoiceDao.findInvoiceByCode(code);
            Invoice invoice = invoiceList.isEmpty() == false ? invoiceList
                    .get(0) : null;
            invoice.setCompany(CommonUtils.getCompany());
            invoice.setCustomsDeclarationId(null);
            invoice.setProjectType(null);
            invoice.setCustomsDeclaredCode(null);
            invoice.setState(InvoiceState.DRAFT);
            invoice.setMoney(0.0);
            this.invoiceDao.saveInvoice(invoice);

        }
        //
        // 修改报关单时 把老的已使用核消单-->末已使用核消单 并且 把新末使用核消单-->已使用核消单
        //
        /*
         * if (actionState == ActionState.EDIT) { String newCode =
         * baseCustomsDeclaration.getInvoiceCode(); String oldCode =
         * oldInvoiceCode; if (oldCode != null && !"".equals(oldCode)) { List<Invoice>
         * invoiceList = this.invoiceDao .findInvoiceByCode(oldCode); Invoice
         * oldInvoice = invoiceList.isEmpty() == false ? invoiceList .get(0) :
         * null; oldInvoice.setCompany(CommonUtils.getCompany());
         * oldInvoice.setCustomsDeclarationId(null);
         * oldInvoice.setProjectType(null);
         * oldInvoice.setCustomsDeclaredCode(null);
         * oldInvoice.setState(InvoiceState.DRAFT); oldInvoice.setMoney(0.0);
         * this.invoiceDao.saveInvoice(oldInvoice); } if (newCode != null &&
         * !"".equals(newCode)) { List<Invoice> invoiceList = this.invoiceDao
         * .findInvoiceByCode(newCode); Invoice invoice = invoiceList.isEmpty() ==
         * false ? invoiceList .get(0) : null;
         * invoice.setCompany(CommonUtils.getCompany());
         * invoice.setCustomsDeclarationId(baseCustomsDeclaration.getId());
         * invoice.setCustomsDeclaredCode(baseCustomsDeclaration
         * .getCustomsDeclarationCode()); invoice.setProjectType(projectType);
         * invoice.setState(InvoiceState.USED); double totalPrice =
         * this.invoiceDao .findCustomsDeclarationMoney(projectType,
         * baseCustomsDeclaration.getId()); invoice.setMoney(totalPrice);
         * this.invoiceDao.saveInvoice(invoice); } }
         */

    }

    /**
     * 出口商品发票各种状态的统计情况
     * 
     * @param beginDate 录入日期
     * @param endDate   截止日期
     * @return 发票各种状态的份数
     */
    public List findInvoiceForObtainStat(Date beginDate, Date endDate) {
        List lsResult = new ArrayList();
        List lsOriginal = this.invoiceDao.findOriginalInvoiceForStat(beginDate,
                endDate);
        List lsDraft = this.invoiceDao.findDraftInvoiceForStat(beginDate,
                endDate);
        List lsUsed = this.invoiceDao
                .findUsedInvoiceForStat(beginDate, endDate);
        List lsCanceled = this.invoiceDao.findCanceledInvoiceForStat(beginDate,
                endDate);
        List lsFinal = this.invoiceDao.findFinalInvoiceForStat(beginDate,
                endDate);
        /**
         * 起初库存
         */
        int iCount = 0;
        int index = -1;
        String beginCode = "", endCode = "";
        Invoice preInvoice = null;
        for (int i = 0; i < lsOriginal.size(); i++) {
            if (preInvoice == null) {
                preInvoice = (Invoice) lsOriginal.get(i);
                iCount++;
                index++;
                beginCode = preInvoice.getInvoiceCode();
                endCode = preInvoice.getInvoiceCode();
                if (lsResult.size() <= index) {
                    TempInvoiceUsedStatInfo temp = new TempInvoiceUsedStatInfo();
                    lsResult.add(temp);
                }
                if (lsOriginal.size() == 1) {
                    TempInvoiceUsedStatInfo temp = (TempInvoiceUsedStatInfo) lsResult
                            .get(index);
                    temp.setOriginalBeginCode(beginCode);
                    temp.setOriginalEndCode(endCode);
                    temp.setOriginalPieces(iCount);
                }
                continue;
            }
            Invoice invoice = (Invoice) lsOriginal.get(i);
            if (!checkInvoiceCodeIsSequence(preInvoice, invoice)) {
                index++;
                if (lsResult.size() <= index) {
                    TempInvoiceUsedStatInfo temp = new TempInvoiceUsedStatInfo();
                    lsResult.add(temp);
                }
                endCode = preInvoice.getInvoiceCode();
                TempInvoiceUsedStatInfo temp = (TempInvoiceUsedStatInfo) lsResult
                        .get(index - 1);
                temp.setOriginalBeginCode(beginCode);
                temp.setOriginalEndCode(endCode);
                temp.setOriginalPieces(iCount);
                iCount = 0;
                beginCode = invoice.getInvoiceCode();
            }
            preInvoice = (Invoice) lsOriginal.get(i);
            iCount++;
            if (i == lsOriginal.size() - 1) {
                endCode = invoice.getInvoiceCode();
                TempInvoiceUsedStatInfo temp = (TempInvoiceUsedStatInfo) lsResult
                        .get(index);
                temp.setOriginalBeginCode(beginCode);
                temp.setOriginalEndCode(endCode);
                temp.setOriginalPieces(iCount);
            }
        }
        /**
         * 本期领用
         */
        iCount = 0;
        index = -1;
        beginCode = "";
        endCode = "";
        preInvoice = null;
        for (int i = 0; i < lsDraft.size(); i++) {
            if (preInvoice == null) {
                preInvoice = (Invoice) lsDraft.get(i);
                iCount++;
                index++;
                beginCode = preInvoice.getInvoiceCode();
                endCode = preInvoice.getInvoiceCode();
                if (lsResult.size() <= index) {
                    TempInvoiceUsedStatInfo temp = new TempInvoiceUsedStatInfo();
                    lsResult.add(temp);
                }
                if (lsDraft.size() == 1) {
                    TempInvoiceUsedStatInfo temp = (TempInvoiceUsedStatInfo) lsResult
                            .get(index);
                    temp.setCurrentDraftBeginCode(beginCode);
                    temp.setCurrentDraftEndCode(endCode);
                    temp.setCurrentDraftPieces(iCount);
                }
                continue;
            }
            Invoice invoice = (Invoice) lsDraft.get(i);
            if (!checkInvoiceCodeIsSequence(preInvoice, invoice)) {
                index++;
                if (lsResult.size() <= index) {
                    TempInvoiceUsedStatInfo temp = new TempInvoiceUsedStatInfo();
                    lsResult.add(temp);
                }
                endCode = preInvoice.getInvoiceCode();
                TempInvoiceUsedStatInfo temp = (TempInvoiceUsedStatInfo) lsResult
                        .get(index - 1);
                temp.setCurrentDraftBeginCode(beginCode);
                temp.setCurrentDraftEndCode(endCode);
                temp.setCurrentDraftPieces(iCount);
                iCount = 0;
                beginCode = invoice.getInvoiceCode();
            }
            preInvoice = (Invoice) lsDraft.get(i);
            iCount++;
            if (i == lsDraft.size() - 1) {
                endCode = invoice.getInvoiceCode();
                TempInvoiceUsedStatInfo temp = (TempInvoiceUsedStatInfo) lsResult
                        .get(index);
                temp.setCurrentDraftBeginCode(beginCode);
                temp.setCurrentDraftEndCode(endCode);
                temp.setCurrentDraftPieces(iCount);
            }
        }
        /**
         * 本期使用
         */
        iCount = 0;
        index = -1;
        beginCode = "";
        endCode = "";
        preInvoice = null;
        for (int i = 0; i < lsUsed.size(); i++) {
            if (preInvoice == null) {
                preInvoice = (Invoice) lsUsed.get(i);
                iCount++;
                index++;
                beginCode = preInvoice.getInvoiceCode();
                endCode = preInvoice.getInvoiceCode();
                if (lsResult.size() <= index) {
                    TempInvoiceUsedStatInfo temp = new TempInvoiceUsedStatInfo();
                    lsResult.add(temp);
                }
                if (lsUsed.size() == 1) {
                    TempInvoiceUsedStatInfo temp = (TempInvoiceUsedStatInfo) lsResult
                            .get(index);
                    temp.setCurrentUsedBeginCode(beginCode);
                    temp.setCurrentUsedEndCode(endCode);
                    temp.setCurrentUsedPieces(iCount);
                }
                continue;
            }
            Invoice invoice = (Invoice) lsUsed.get(i);
            if (!checkInvoiceCodeIsSequence(preInvoice, invoice)) {
                index++;
                if (lsResult.size() <= index) {
                    TempInvoiceUsedStatInfo temp = new TempInvoiceUsedStatInfo();
                    lsResult.add(temp);
                }
                endCode = preInvoice.getInvoiceCode();
                TempInvoiceUsedStatInfo temp = (TempInvoiceUsedStatInfo) lsResult
                        .get(index - 1);
                temp.setCurrentUsedBeginCode(beginCode);
                temp.setCurrentUsedEndCode(endCode);
                temp.setCurrentUsedPieces(iCount);
                iCount = 0;
                beginCode = invoice.getInvoiceCode();
            }
            preInvoice = (Invoice) lsUsed.get(i);
            iCount++;
            if (i == lsUsed.size() - 1) {
                endCode = invoice.getInvoiceCode();
                TempInvoiceUsedStatInfo temp = (TempInvoiceUsedStatInfo) lsResult
                        .get(index);
                temp.setCurrentUsedBeginCode(beginCode);
                temp.setCurrentUsedEndCode(endCode);
                temp.setCurrentUsedPieces(iCount);
            }
        }
        /**
         * 本期作废
         */
        iCount = 0;
        index = -1;
        beginCode = "";
        endCode = "";
        preInvoice = null;
        for (int i = 0; i < lsCanceled.size(); i++) {
            if (preInvoice == null) {
                preInvoice = (Invoice) lsCanceled.get(i);
                iCount++;
                index++;
                beginCode = preInvoice.getInvoiceCode();
                endCode = preInvoice.getInvoiceCode();
                if (lsResult.size() <= index) {
                    TempInvoiceUsedStatInfo temp = new TempInvoiceUsedStatInfo();
                    lsResult.add(temp);
                }
                if (lsCanceled.size() == 1) {
                    TempInvoiceUsedStatInfo temp = (TempInvoiceUsedStatInfo) lsResult
                            .get(index);
                    temp.setCurrentCanceledBeginCode(beginCode);
                    temp.setCurrentCanceledEndCode(endCode);
                    temp.setCurrentCanceledPieces(iCount);
                }
                continue;
            }
            Invoice invoice = (Invoice) lsCanceled.get(i);
            if (!checkInvoiceCodeIsSequence(preInvoice, invoice)) {
                index++;
                if (lsResult.size() <= index) {
                    TempInvoiceUsedStatInfo temp = new TempInvoiceUsedStatInfo();
                    lsResult.add(temp);
                }
                endCode = preInvoice.getInvoiceCode();
                TempInvoiceUsedStatInfo temp = (TempInvoiceUsedStatInfo) lsResult
                        .get(index - 1);
                temp.setCurrentCanceledBeginCode(beginCode);
                temp.setCurrentCanceledEndCode(endCode);
                temp.setCurrentCanceledPieces(iCount);
                iCount = 0;
                beginCode = invoice.getInvoiceCode();
            }
            preInvoice = (Invoice) lsCanceled.get(i);
            iCount++;
            if (i == lsCanceled.size() - 1) {
                endCode = invoice.getInvoiceCode();
                TempInvoiceUsedStatInfo temp = (TempInvoiceUsedStatInfo) lsResult
                        .get(index);
                temp.setCurrentCanceledBeginCode(beginCode);
                temp.setCurrentCanceledEndCode(endCode);
                temp.setCurrentCanceledPieces(iCount);
            }
        }
        /**
         * 期末库存
         */
        iCount = 0;
        index = -1;
        beginCode = "";
        endCode = "";
        preInvoice = null;
        for (int i = 0; i < lsFinal.size(); i++) {
            if (preInvoice == null) {
                preInvoice = (Invoice) lsFinal.get(i);
                iCount++;
                index++;
                beginCode = preInvoice.getInvoiceCode();
                endCode = preInvoice.getInvoiceCode();
                if (lsResult.size() <= index) {
                    TempInvoiceUsedStatInfo temp = new TempInvoiceUsedStatInfo();
                    lsResult.add(temp);
                }
                if (lsFinal.size() == 1) {
                    TempInvoiceUsedStatInfo temp = (TempInvoiceUsedStatInfo) lsResult
                            .get(index);
                    temp.setFinalBeginCode(beginCode);
                    temp.setFinalEndCode(endCode);
                    temp.setFinalPieces(iCount);
                }
                continue;
            }
            Invoice invoice = (Invoice) lsFinal.get(i);
            if (!checkInvoiceCodeIsSequence(preInvoice, invoice)) {
                index++;
                if (lsResult.size() <= index) {
                    TempInvoiceUsedStatInfo temp = new TempInvoiceUsedStatInfo();
                    lsResult.add(temp);
                }
                endCode = preInvoice.getInvoiceCode();
                TempInvoiceUsedStatInfo temp = (TempInvoiceUsedStatInfo) lsResult
                        .get(index - 1);
                temp.setFinalBeginCode(beginCode);
                temp.setFinalEndCode(endCode);
                temp.setFinalPieces(iCount);
                iCount = 0;
                beginCode = invoice.getInvoiceCode();
            }
            preInvoice = (Invoice) lsFinal.get(i);
            iCount++;
            if (i == lsFinal.size() - 1) {
                endCode = invoice.getInvoiceCode();
                TempInvoiceUsedStatInfo temp = (TempInvoiceUsedStatInfo) lsResult
                        .get(index);
                temp.setFinalBeginCode(beginCode);
                temp.setFinalEndCode(endCode);
                temp.setFinalPieces(iCount);
            }
        }
        TempInvoiceUsedStatInfo temp = new TempInvoiceUsedStatInfo();
        lsResult.add(temp);
        temp.setCommName("合计份数：");
        temp.setOriginalPieces(lsOriginal.size());
        temp.setCurrentDraftPieces(lsDraft.size());
        temp.setCurrentUsedPieces(lsUsed.size());
        temp.setCurrentCanceledPieces(lsCanceled.size());
        temp.setFinalPieces(lsFinal.size());
        return lsResult;
    }

    /**
     * 判断两个发票号码是不是连续的
     * @param preInvoice 前一个发票号
     * @param currentInvoice 当前的发票号
     * @return true 
     */
    private boolean checkInvoiceCodeIsSequence(Invoice preInvoice,
            Invoice currentInvoice) {
//        String preVersion = preInvoice.getVersionCode();
//        String nextVersion = currentInvoice.getVersionCode();
//        if (preVersion == null && !preVersion.equals(nextVersion)) {
//            return false;
//        }
//        int preNum = Integer.parseInt(preInvoice.getInvoiceCode().substring(
//                preVersion.length() + 1, preInvoice.getInvoiceCode().length()));
//        int nextNum = Integer.parseInt(currentInvoice.getInvoiceCode()
//                .substring(nextVersion.length() + 1,
//                        currentInvoice.getInvoiceCode().length()));
        
        int preNum = Integer.parseInt(preInvoice.getInvoiceCode().trim());
        int nextNum = Integer.parseInt(currentInvoice.getInvoiceCode().trim());
        
        if (preNum != nextNum && preNum + 1 != nextNum) {
            return false;
        }
        return true;
    }

    /**
     * 检查发票
     * @param invoice 发票
     * @return 初始状态为领用 输入使用日期 自动改状态为使用状态
     */
    public Invoice checkInvoice(Invoice invoice) {
        invoice.setState(InvoiceState.DRAFT);
        invoice.setUsedDate(new Date());
        if (invoice == null) {
            invoice.setState(InvoiceState.USED);
        }
        this.invoiceDao.saveInvoice(invoice);
        return invoice;
    }
    
    /**
     * 根据状态抓取发票
	 * @param state 状态
	 * @return 指定状态匹配的所有发票 按发票号排列
     */
    public List findInvoiceByState(Integer state) {
    	List list=invoiceDao.findInvoiceByState(state);
    	list.add(new Invoice());
    	Invoice temp = makeTotalInvoiceMoney(list);
    	list.add(temp);
    	
		return list;
	}
    
    /**
	 * 根据状态和开始、结束的发票号抓取发票
	 * 
	 * @param state 状态
	 * @param startInvoiceCode 开始发票号
	 * @param endInvoiceCode 结束发票号
	 * @return 指定状态匹配的所有发票 按发票号排列
	 */
    public List findInvoiceByState(Integer state,String startInvoiceCode,String endInvoiceCode) {
    	List list=invoiceDao.findInvoiceByState(state,startInvoiceCode,endInvoiceCode);
    	list.add(new Invoice());
    	Invoice temp = makeTotalInvoiceMoney(list);
    	list.add(temp);
    	
		return list;
    }
    
    /**
	 * 计算发票的总金额
	 * 
	 * @param returnList 查找到的发票资料
	 * @return Invoice 计算好的只含有发票总金额的一条数据
	 */
	private Invoice makeTotalInvoiceMoney(List<Invoice> returnList) {
		Invoice temp = new Invoice();
		/**
		 * 发票总金额
		 */
		Double invoiceTotalMoney = 0.0;
		
		for (int i = 0; i < returnList.size(); i++) {
			Invoice e = returnList.get(i);
			/**
			 * 发票总金额
			 */
			invoiceTotalMoney += e.getMoney() == null ? 0.0 : e
					.getMoney();			
		}
		/**
		 * 发票总金额
		 */
		temp.setMoney(invoiceTotalMoney);
		
		temp.setVersionCode("合计");
		return temp;
	}
    
}
