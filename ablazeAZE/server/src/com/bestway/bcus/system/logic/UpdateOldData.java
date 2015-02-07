/*
 * Created on 2004-6-18
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.system.logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.bestway.common.CommonUtils;

/**
 * @author 001 // change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class UpdateOldData {
	/**
	 * Commons Logger for this class
	 */
	private static final Log logger = LogFactory.getLog(UpdateOldData.class);

	private String tableOwner = "";

	private DataSource dataSource = null;

	private Connection conn = null;

	public void init() {
		logger.info("init custom base!");
		try {
			conn = dataSource.getConnection();
			this.updateCompanyOther();
			this.updateCASBillType();
			this.updateCancelOwnerHeadLength();
			this.updateCancelHeadLength();
			this.updateCancelOwnerCustomsDeclaraLength();
			this.updateMenos();
			this.updateEmsHeadH2kNote();
			this.updateBillDetailporductedNo();
			this.updateAtcMergeBeforeComInfo();
			this.updatebillTypestjgReturn();
			this.updateCustomsEnvelopBill();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}


	/**
	 * 设置数据表名前缀
	 * 
	 * @return
	 */
	public String getTableOwner() {
		return tableOwner;
	}

	/**
	 * 设置数据表名前缀
	 * 
	 * @return
	 */
	public void setTableOwner(String tableOwner) {
		if (tableOwner != null && !"".equals(tableOwner.trim())) {
			this.tableOwner = tableOwner + ".";
		}
	}
	/**
	 * 初始化CompanyOther乐观锁栏位optLock初始值为0
	 */
	private void updateCompanyOther(){
		String updateSql="update "+tableOwner+"CompanyOther set optLock=0 where optLock is null";
		try {
			PreparedStatement stmtUpdate = conn.prepareStatement(updateSql);
			stmtUpdate.executeUpdate();
			stmtUpdate.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("[UPDATE OLD DATA] update CompanyOther set optLock=0 where optLock is null");
	}
	/**
	 * 更新自用核销表备注栏位长度
	 */
	private void updateCancelOwnerHeadLength(){
		String updateSql="alter   table   CancelOwnerHead   alter   column   note   varchar(4000) ";
		try {
			PreparedStatement stmtUpdate = conn.prepareStatement(updateSql);
			stmtUpdate.executeUpdate();
			stmtUpdate.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("[UPDATE OLD DATA] alter   table   CancelOwnerHead   alter   column   note   varchar(4000)");
		
	}
	/**
	 * 更新核销表备注栏位长度(存放滚动核销－－自用核销报关单头)
	 */
	private void updateCancelOwnerCustomsDeclaraLength(){
		String updateSql="alter   table   CancelOwnerCustomsDeclara   alter   column   note   varchar(4000) ";
		try {
			PreparedStatement stmtUpdate = conn.prepareStatement(updateSql);
			stmtUpdate.executeUpdate();
			stmtUpdate.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("[UPDATE OLD DATA] alter   table   cancelownercustomsdeclara   alter   column   note   varchar(4000)");
		
	}
	/**
	 * 更新核销表备注栏位长度
	 */
	private void updateCancelHeadLength(){
		String updateSql="alter   table   CancelHead   alter   column   note   varchar(4000) ";
		try {
			PreparedStatement stmtUpdate = conn.prepareStatement(updateSql);
			stmtUpdate.executeUpdate();
			stmtUpdate.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("[UPDATE OLD DATA] alter   table   CancelHead   alter   column   note   varchar(4000)");
		
	}
	
	/**
	 * 更新申请单备注长度
	 */
	private void updateMenos(){
		String updateSql="alter   table   ImpExpRequestBill   alter   column   memos   varchar(2000) ";
		try {
			PreparedStatement stmtUpdate = conn.prepareStatement(updateSql);
			stmtUpdate.executeUpdate();
			stmtUpdate.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("[UPDATE OLD DATA] alter   table   ImpExpRequestBill   alter   column   memos   varchar(2000)");
		
	}
	/**
	 * 更电子账册备注长度
	 */
	private void updateEmsHeadH2kNote(){
		String updateSql="alter   table   EmsHeadH2k   alter   column   note   varchar(255) ";
		try {
			PreparedStatement stmtUpdate = conn.prepareStatement(updateSql);
			stmtUpdate.executeUpdate();
			stmtUpdate.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
		}
		logger.info("[UPDATE OLD DATA] alter   table   EmsHeadH2k   alter   column   note   varchar(255)");
		
	}
	/**
	 * 更改成品单据体制单号长度 
	 */
	private void updateBillDetailporductedNo(){
		String casyear = CommonUtils.getYear();
		String BillDetailProduct = "BillDetailProduct" + casyear ;
		String updateSql="alter   table   " + BillDetailProduct + " alter   column   productNo   varchar(255) ";
		try {
			PreparedStatement stmtUpdate = conn.prepareStatement(updateSql);
			stmtUpdate.executeUpdate();
			stmtUpdate.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
		}
		logger.info("[UPDATE OLD DATA] alter   table   BillDetailProduct   alter   column   productNo   varchar(255)");
		
	}
	
	private void updateCASBillType(){
//		String updateSql="update "+tableOwner+"CompanyOther set optLock=0 where optLock is null";
//		try {
//			PreparedStatement stmtUpdate = conn.prepareStatement(updateSql);
//			stmtUpdate.executeUpdate();
//			stmtUpdate.close();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		logger.info("[UPDATE OLD DATA] update CompanyOther set optLock=0 where optLock is null");
	}
	
	/**
	 * 更新报关清单归并前商品信息栏位长度
	 */
	private void updateAtcMergeBeforeComInfo(){
		String updateSql="alter   table   AtcMergeBeforeComInfo   alter   column   boxNo   varchar(5000) ";
		try {
			PreparedStatement stmtUpdate = conn.prepareStatement(updateSql);
			stmtUpdate.executeUpdate();
			stmtUpdate.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("[UPDATE OLD DATA] alter   table   AtcMergeBeforeComInfo   alter   column   boxNo   varchar(2000)");
	}
	/**
	 * 更新报关清单归并前商品信息栏位长度
	 */
	private void updatebillTypestjgReturn(){
		String updateSql="update billtype set name='受托加工料件返回（返修）'  where name='受托加工料件返修' ";
		try {
			PreparedStatement stmtUpdate = conn.prepareStatement(updateSql);
			stmtUpdate.executeUpdate();
			stmtUpdate.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("[UPDATE OLD DATA]update billtype set name='受托加工料件返回（返修）'  where name='受托加工料件返修'");
	}
	/**
	 * 更新关封单据管理 结转报关单号 栏位长度
	 */
	private void updateCustomsEnvelopBill(){
		String updateSql="alter   table   CustomsEnvelopBill   alter   column   applyToCustomsBillNo   varchar(5000) ";
		try {
			PreparedStatement stmtUpdate = conn.prepareStatement(updateSql);
			stmtUpdate.executeUpdate();
			stmtUpdate.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("[UPDATE OLD DATA] alter   table   CustomsEnvelopBill   alter   column   applyToCustomsBillNo   varchar(5000)");
	}
}