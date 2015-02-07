package com.bestway.bcus.cas.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bestway.bcus.cas.bill.entity.BillDetail;
import com.bestway.bcus.cas.bill.entity.BillDetailMateriel;
import com.bestway.bcus.cas.bill.entity.BillMaster;
import com.bestway.bcus.cas.bill.entity.BillMasterMateriel;
import com.bestway.bcus.cas.bill.entity.BillUtil;
import com.bestway.bcus.cas.dao.CasBillDao;
import com.bestway.bcus.cas.dao.CasDao;
import com.bestway.bcus.cas.dao.CasSpecificontrolDao;
import com.bestway.bcus.cas.entity.BillType;
import com.bestway.bcus.cas.entity.FactoryAndFactualCustomsRalation;
import com.bestway.bcus.cas.entity.StatCusNameRelation;
import com.bestway.bcus.cas.entity.StatCusNameRelationHsn;
import com.bestway.bcus.cas.entity.StatCusNameRelationMt;
import com.bestway.bcus.cas.parameterset.entity.BillCorrespondingControl;
import com.bestway.bcus.cas.specificontrol.entity.CustomsDeclarationCommInfoBillCorresponding;
import com.bestway.bcus.cas.specificontrol.entity.MakeBillCorrespondingInfoBase;
import com.bestway.bcus.cas.specificontrol.entity.MakeBillCorrespondingInfoByMateriel;
import com.bestway.bcus.cas.specificontrol.entity.MakeBillCorrespondingInfoByProduct;
import com.bestway.bcus.cas.specificontrol.entity.TempBillCorrespondingSearch;
import com.bestway.bcus.cas.specificontrol.entity.TempMaterielTypeSetup;
import com.bestway.bcus.cas.specificontrol.entity.TempResult;
import com.bestway.bcus.system.entity.Company;
import com.bestway.common.CommonUtils;
import com.bestway.common.MaterielType;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.materialbase.entity.MaterialBomDetail;
import com.bestway.common.materialbase.entity.MaterialBomMaster;
import com.bestway.common.materialbase.entity.MaterialBomVersion;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;

/**
 * 海关帐特殊控制logic方法类
 * 2009年9月7日 贺巍检查，补加注释
 * @author ?
 *
 */
public class CasSpecificontrolLogic {
	/**
	 * 海关帐特殊控制Dao
	 */
	private CasSpecificontrolDao casSpecificontrolDao = null;

	/**
	 * 海关帐单据Dao
	 */
	private CasBillDao casBillDao = null;

	/**
	 * 海关帐Dao
	 */
	private CasDao casDao = null;

	/**
	 * 日志类
	 */
	private static final Log logger = LogFactory

	.getLog(CasSpecificontrolLogic.class);

	/**
	 * 取得海关帐特殊控制Dao的内容
	 * 
	 * @return 海关帐特殊控制Dao
	 */
	public CasSpecificontrolDao getCasSpecificontrolDao() {
		return casSpecificontrolDao;
	}

	/**
	 * 取得海关帐Dao的内容
	 * 
	 * @return 海关帐Dao
	 */
	public CasDao getCasDao() {
		return casDao;
	}

	/**
	 * 设计海关帐Dao的内容
	 * 
	 * @param casDao
	 *            海关帐Dao
	 */
	public void setCasDao(CasDao casDao) {
		this.casDao = casDao;
	}

	/**
	 * 设计海关帐特殊控制Dao的内容
	 * 
	 * @param casSpecificontrolDao
	 *            海关帐特殊控制Dao
	 */
	public void setCasSpecificontrolDao(
			CasSpecificontrolDao casSpecificontrolDao) {
		this.casSpecificontrolDao = casSpecificontrolDao;
	}

	/**
	 * 取得海关帐单据Dao的内容
	 * 
	 * @return 海关帐单据Dao
	 */
	public CasBillDao getCasBillDao() {
		return casBillDao;
	}

	/**
	 * 设计海关帐单据Dao的内容
	 * 
	 * @param casBillDao
	 *            海关帐单据Dao
	 */
	public void setCasBillDao(CasBillDao casBillDao) {
		this.casBillDao = casBillDao;
	}

	/**
	 * 查找单据的对应的单据明细(生效的记录)
	 * 
	 * @param impExpType
	 *            进出口类型
	 * @param scmCoc
	 *            客户/供应商
	 * @param beginData
	 *            开始日期
	 * @param endData
	 *            截止日期
	 * @param name
	 *            名称
	 * @param spec
	 *            规格
	 *@param isNameSpec 判断是选择名称(true)还是名称+规格(false)查询
	 *            
	 * @return 临时单据明细
	 */
	public List<BillDetail> findBillDetail(Integer impExpType, ScmCoc scmCoc,
			Date beginData, Date endData, String nameSpec,Boolean isNameSpec) {
		String materielType = BillUtil.getMaterielTypeByImpExpType(impExpType);//物料类型
		BillCorrespondingControl b = CommonUtils.getBillCorrespondingControl();//单据对应控制
		//工厂和实际客户对应表 资料（料号、报关名称 、报关规格）
		List<Object[]> lists = casSpecificontrolDao.findMaterielptNoTwo(materielType, nameSpec,
				null, null);
		Map<String,String> map = new HashMap<String,String>();
		List<String> ptNoList = new ArrayList<String>();
		//报关名称 、报关规格为非必要条件
		if(!"".equals(nameSpec)&&nameSpec!=null){
			for(Object[] item : lists){
				String matchStr ="";
				if(isNameSpec){
					 matchStr = (item[1]==null?"":item[1].toString());
				}else{
					 matchStr = (item[1]==null?"":item[1].toString()) + "/" + (item[2]==null?"":item[2].toString());
				}
				if(matchStr.equals(nameSpec)||matchStr.equals(nameSpec+"/")||matchStr.equals(nameSpec+"//")){
					String ptNo = item[0]==null?"":item[0].toString();
					map.put(ptNo, ptNo);//有查询条件相符的   报关名称规格   的料号
				}
			}
		}else{
			for(Object[] item : lists){
				String ptNo = item[0]==null?"":item[0].toString();
				map.put(ptNo, ptNo);//有查询条件相符的料号
			}
		}
		ptNoList.addAll(map.values());//与查询条件相符的料号
			
		System.out.println("=========查询出来的料号list size = "+map);

		// 结转退货是否参与对应   &&  料件出口，转厂出口
		if (b.getIsTransferBack() != null
				&& b.getIsTransferBack().booleanValue() == true
				&& (ImpExpType.TRANSFER_FACTORY_IMPORT == impExpType.intValue() 
						|| ImpExpType.TRANSFER_FACTORY_EXPORT == impExpType.intValue())) {

			// 料件 进口
			List<BillDetail> list = new ArrayList<BillDetail>();
			
			//获取单据体
			list = this.casSpecificontrolDao
				.findBillDetailByBillCorresponding(true,impExpType,
					ptNoList, scmCoc,beginData,endData);
			
//			if (ImpExpType.TRANSFER_FACTORY_IMPORT == impExpType.intValue()) {
				// if (b.getIsSystemControl() == true) {
				// list = this.casSpecificontrolDao
				// .findBillDetailByCarryForwardMateriel(impExpType,
				// scmCoc, beginData, endData, name, spec,
				// false);
//				list = this.casSpecificontrolDao
//						.findBillDetailByCarryForwardMaterielA(impExpType,
//								ptNoList, scmCoc,beginData,endData);
				// } else if (b.getIsSpecialControl() == true) { // 特殊控制
				// list = this.casSpecificontrolDao
				// .findBillDetailByCarryForwardMateriel(impExpType,
				// scmCoc, beginData, endData, name, spec,
				// true);
				// }
//			} else if (ImpExpType.TRANSFER_FACTORY_EXPORT == impExpType
//					.intValue()) { // 成品 出口
			// if (b.getIsSystemControl() == true) {
			// list = this.casSpecificontrolDao
			// .findBillDetailByCarryForwardProduct(impExpType,
			// scmCoc, beginData, endData, name, spec,
			// false);
//				list = this.casSpecificontrolDao
//						.findBillDetailByCarryForwardProductA(impExpType,
//								ptNoList, scmCoc,beginData,endData);
				// } else if (b.getIsSpecialControl(M) == true) { // 特殊控制
				// list = this.casSpecificontrolDao
				// .findBillDetailByCarryForwardProduct(impExpType,
				// scmCoc, beginData, endData, name, spec,
				// true);
				// }
//			}
			//
			// 设置结转退货为负数
			//
//			return setTransferBackAmount(list);
//			String test = null;
//			Double.parseDouble(test);
			return list;

		} else {
			// System.out.println("====== 正常");
//			if (b.getIsSystemControl() == true) {
			// return this.casSpecificontrolDao.findBillDetail(impExpType,
			// scmCoc, beginData, endData, name, spec);			
			//获取单据体
			List list =  this.casSpecificontrolDao
				.findBillDetailByBillCorresponding(false,impExpType,
				ptNoList, scmCoc,beginData,endData);
			return this.casSpecificontrolDao
			.findBillDetailByBillCorresponding(false,impExpType,
			ptNoList, scmCoc,beginData,endData);
//			} else if (b.getIsSpecialControl() == true) { // 特殊控制
//				return this.casSpecificontrolDao.findBillDetailByLike(
//						impExpType, scmCoc, beginData, endData, name, spec);
//			}
		}
//		return new ArrayList<BillDetail>();

	}

	/**
	 * 计算实际的 结转进口 - 结转退货单
	 * 
	 * @return
	 */
	private List setTransferBackAmount(List<BillDetail> list) {
		List<BillDetail> returnList = new ArrayList<BillDetail>();
		for (int i = 0; i < list.size(); i++) {
			BillDetail billDetail = (BillDetail) list.get(i);
			String typeCode = billDetail.getBillMaster().getBillType()
					.getCode().trim();
			//
			// billtype code 结转进口 1004 结转料件退货单 2009 结转成品退货单
			//
			System.out.println("开始设置了！");
			if (typeCode.equals("1106") || typeCode.equals("2009")) {
				double hsAmount = (billDetail.getHsAmount() == null ? 0.0
						: billDetail.getHsAmount());
				billDetail.setHsAmount(hsAmount == 0.0 ? 0.0 : -hsAmount);
			}
			returnList.add(billDetail);
		}		
		return returnList;
	}
	
	/**
	 * 获取已对应的单据
	 * 
	 * @param impExpType
	 * @param scmCoc
	 * @param beginData
	 * @param endData
	 * @param name
	 * @param spec
	 * @param isNameSpec 判断是选择名称(true)还是名称+规格(false)查询
	 * @return
	 */
	public List findMakeBillCorrespondingInfo(Integer impExpType,
			ScmCoc scmCoc, Date beginData, Date endData, String nameSpec,Boolean isNameSpec) {
		List<MakeBillCorrespondingInfoBase> list = this.casSpecificontrolDao
				.findMakeBillCorrespondingInfo(impExpType, scmCoc, beginData,
						endData, nameSpec);
		// 名称，规格位非必要条件
		if (!"".equals(nameSpec) && nameSpec != null) {
			for (int i = 0; i < list.size(); i++) {
				MakeBillCorrespondingInfoBase m = list.get(i);
				String matchStr ="";
				if(isNameSpec){
					matchStr = m.getCommName() + "/" + "";
				}else{
					matchStr = m.getCommName() + "/" + m.getCommSpec();
				}
				
				if (matchStr.equals(nameSpec)
						|| matchStr.equals(nameSpec + "/")
						|| matchStr.equals(nameSpec + "//")) {
					continue;
				}
				list.remove(i);
				i--;
			}
		}
		return list;
	}

	/**
	 * 查找报关单商品信息与海关帐单据的对应
	 * 
	 * @param impExpType
	 *            进出口类型
	 * @param scmCoc
	 *            客户/供应商
	 * @param beginData
	 *            开始日期
	 * @param endData
	 *            截止日期
	 * @param name
	 *            名称
	 * @param spec
	 *            规格
	 *@param isNameSpec 判断是选择名称(true)还是名称+规格(false)查询
	 * @return 已经对应的报关单商品信息和海关帐单据
	 */
	public List findCustomsDeclarationCommInfoBillCorresponding(
			Integer impExpType, ScmCoc scmCoc, Date beginData, Date endData,
			String name,String spec,Boolean isNameSpec) {
		List<CustomsDeclarationCommInfoBillCorresponding> lsResult = 
							new ArrayList<CustomsDeclarationCommInfoBillCorresponding>();
		
		// 已经对应的报关单数据
		List<CustomsDeclarationCommInfoBillCorresponding> alreadyCorrespondingList = new ArrayList();
		BillCorrespondingControl b = CommonUtils.getBillCorrespondingControl();
		if (b.getIsSystemControl() == true) {// 系统自动控制
			//查询相应条件的    报关单商品信息与海关帐单据的对应
			alreadyCorrespondingList = this.casSpecificontrolDao
					.findCustomsDeclarationCommInfoBillCorresponding(
											impExpType, scmCoc, beginData, endData);
			for(int i = alreadyCorrespondingList.size()-1;i>=0;i--){
				CustomsDeclarationCommInfoBillCorresponding c = alreadyCorrespondingList.get(i);
				String commName ;
				String commSpec ;	
				if(isNameSpec){
					 commName = c.getCommName()==null?"":c.getCommName();
					 commSpec = "";	
				}else{
					 commName = c.getCommName()==null?"":c.getCommName();
					 commSpec = c.getCommSpec()==null?"":c.getCommSpec();	
				}
							
				String nameSpec = (name==null?"":name) + "/" + (spec==null?"":spec);				
				String matchStr = commName + "/" + commSpec;
				if(matchStr.equals(nameSpec)
						||matchStr.equals(nameSpec+"/")
						||matchStr.equals(nameSpec+"//")){
					continue;				
				}
				alreadyCorrespondingList.remove(i);//排除不符合    报关名称规格     查询条件的
			}
		}else if (b.getIsSpecialControl() == true) {// 系统特殊控制			
			alreadyCorrespondingList = this.casSpecificontrolDao
					.findCustomsDeclarationCommInfoBillCorrespondingByLike(
							impExpType, scmCoc, beginData, endData,name,spec);
		}
		
		//排除  未转报关单数小于0的
		for (int i = 0; i < alreadyCorrespondingList.size(); i++) {
			CustomsDeclarationCommInfoBillCorresponding c = (CustomsDeclarationCommInfoBillCorresponding) alreadyCorrespondingList
					.get(i);
			if (c.getNoCorrespondingAmount() > 0) {
				lsResult.add(c);
			}
		}
		System.out.println("alreadyCorrespondingList.size()="+alreadyCorrespondingList.size());
		// 没有对应的数据
		lsResult.addAll(this.findNoCorresponding(alreadyCorrespondingList,
				impExpType, scmCoc, beginData, endData, name,spec,isNameSpec));
		return lsResult;
	}

	/**
	 * 获得报关单中没能对应的记录
	 * 
	 * @param impExpType
	 *            进出口类型
	 * @param scmCoc
	 *            客户/供应商
	 * @param beginData
	 *            开始日期
	 * @param endData
	 *            截止日期
	 * @param name
	 *            名称
	 * @param spec
	 *            规格
	 *@param isNameSpec 判断是选择名称(true)还是名称+规格(false)查询
	 * @return 报关单中没有与海关帐单据的记录
	 */
	private List findNoCorresponding(List removeList, Integer impExpType,
			ScmCoc scmCoc, Date beginData, Date endData, String name,String spec,Boolean isNameSpec) {
		List<CustomsDeclarationCommInfoBillCorresponding> returnList = 
									new ArrayList<CustomsDeclarationCommInfoBillCorresponding>();
		List<BaseCustomsDeclarationCommInfo> list = new ArrayList();
		BillCorrespondingControl b = CommonUtils.getBillCorrespondingControl();

		//系统控制
		if (b.getIsSystemControl() == true) {
			
			//报关单物料信息
			list.addAll(this.casSpecificontrolDao
							.findCustomsDeclarationCommInfo(false,
									ProjectType.BCUS, impExpType, scmCoc,
									beginData, endData, null, null));
			 System.out.println("cas list ="+list.size());
			list.addAll(this.casSpecificontrolDao
							.findCustomsDeclarationCommInfo(false,
									ProjectType.BCS, impExpType, scmCoc,
									beginData, endData, null, null));
			 System.out.println("cas list ="+list.size());
			list.addAll(this.casSpecificontrolDao
							.findCustomsDeclarationCommInfo(false,
									ProjectType.DZSC, impExpType, scmCoc,
									beginData, endData, null, null));
			 System.out.println("cas list ="+list.size());
			 
			 List rList=new ArrayList();
			
			for(int i = 0; i <list.size() ; i++){//将不为查找的剔除
				BaseCustomsDeclarationCommInfo temp = list.get(i);
				
//				BaseCustomsDeclarationCommInfo temp = (BaseCustomsDeclarationCommInfo) objs[0];
				String commName ;
				String commSpec ;
				if(isNameSpec){
					 commName = temp.getCommName()==null?"":temp.getCommName();
					 commSpec = "";
				}else{
					 commName = temp.getCommName()==null?"":temp.getCommName();
					 commSpec = temp.getCommSpec()==null?"":temp.getCommSpec();
				}
				
				String matchStr = commName.trim() + "/" + commSpec.trim();
				
				String nameSpec = "";
				nameSpec = (name==null?"":name.trim())+"/"+ (spec==null?"":spec.trim());
//				System.out.println("temp.流水号="+temp.getBaseCustomsDeclaration().getSerialNumber());
//				System.out.println("matchStr="+matchStr);
//				System.out.println("nameSpec="+nameSpec);
				if(matchStr.equals(nameSpec)||
						matchStr.equals(nameSpec+"/")
						||matchStr.equals(nameSpec+"//")){
					System.out.println("iiiiiiiiiiiiii=");
					continue;
				}				
//				System.out.println("commName = " + commName);
//				System.out.println("commSpec = " + commSpec);
//				
//				if(commName.equals(name) && commSpec.equals(spec)){
//					continue;
//				}
				rList.add(temp);
			}
			list.removeAll(rList);
			
		} 
		//特殊控制
		else if (b.getIsSpecialControl() == true) {			
			list
					.addAll(this.casSpecificontrolDao
							.findCustomsDeclarationCommInfo(true,
									ProjectType.BCUS, impExpType, scmCoc,
									beginData, endData, name, spec));
			list
					.addAll(this.casSpecificontrolDao
							.findCustomsDeclarationCommInfo(true,
									ProjectType.BCS, impExpType, scmCoc,
									beginData, endData, name, spec));
			list
					.addAll(this.casSpecificontrolDao
							.findCustomsDeclarationCommInfo(true,
									ProjectType.DZSC, impExpType, scmCoc,
									beginData, endData, name, spec));
		}

		Map<String, Object> map = new HashMap<String, Object>();
		for (int i = 0; i < removeList.size(); i++) {
			CustomsDeclarationCommInfoBillCorresponding c = (CustomsDeclarationCommInfoBillCorresponding) removeList
					.get(i);
			map.put(c.getCustomsDeclarationId()
					+ c.getCustomsDeclarationCommInfoId(), c);
		}
		System.out.println("last cas list ="+list.size());
		for (int i = 0; i < list.size(); i++) {
			BaseCustomsDeclarationCommInfo temp = list.get(i);
			
			//过滤已经存在的
			if (map.get(temp.getBaseCustomsDeclaration().getId()
							+ temp.getId()) != null) {
				continue;
			}
			CustomsDeclarationCommInfoBillCorresponding c = new CustomsDeclarationCommInfoBillCorresponding();
			makeCustomdeclarationCommInfoBillCorresponding(temp, c, 1.0);//将temp包装成 c
			c.setImpExpType(impExpType);

			// 如果报关商品数量 > 已对应的报关数量 才显示
			if (c.getNoCorrespondingAmount() > 0) {
				returnList.add(c);
			}
		}
		return returnList;
	}

	/**
	 * 生成报关单商品信息与海关帐单据的对应
	 * 
	 * @param temp
	 *            临时的报关单商品信息
	 * @param c
	 *            报关单商品信息与海关帐单据的对应
	 * @param unitConvert
	 *            单位折算
	 */
	private void makeCustomdeclarationCommInfoBillCorresponding(
			BaseCustomsDeclarationCommInfo temp,
			CustomsDeclarationCommInfoBillCorresponding c, Double unitConvert) {
		c.setUnitConvert(unitConvert);
		c.setAlreadyCorrespondingAmount(new Double(0));
		c.setCommAmount(temp.getCommAmount());
		c.setNoCorrespondingAmount(new Double(0));
		c.setCommName(temp.getCommName());
		c.setCommSerialNo(temp.getCommSerialNo());
		c.setCommSpec(temp.getCommSpec());
		c.setCompany(temp.getCompany());
		c.setCommUnitPrice(temp.getCommUnitPrice());
		c.setCustomsDeclarationCode(temp.getBaseCustomsDeclaration()
				.getCustomsDeclarationCode());
		c.setCustomsDeclarationCommInfoId(temp.getId());
		c.setCustomsDeclarationId(temp.getBaseCustomsDeclaration().getId());
		c.setEmsHeadH2k(temp.getBaseCustomsDeclaration().getEmsHeadH2k());
		c.setFirstAmount(temp.getFirstAmount());
		c.setImpExpDate(temp.getBaseCustomsDeclaration().getImpExpDate());
		c.setLegalUnit(temp.getLegalUnit());
		c.setScmCoc(temp.getBaseCustomsDeclaration().getCustomer());
		c.setSecondAmount(temp.getSecondAmount());
		c.setSecondLegalUnit(temp.getSecondLegalUnit());
		c.setSerialNumber(temp.getSerialNumber());
		c.setUnit(temp.getUnit());
		c.setVersion(temp.getVersion());
		c.setComplex(temp.getComplex());
		
		//wss2010.09.20新添 关封号
		c.setEnvelopNo(temp.getBaseCustomsDeclaration().getCustomsEnvelopBillNo());
		
		// c.setImpExpType(temp.getBaseCustomsDeclaration().getImpExpType());
	}

	/**
	 * 只是把结转退货单,负数放在前面 1106 2009
	 * 
	 * @param list
	 */
	private List<BillDetail> billDetailBySortHsAmount(List<BillDetail> list) {

		List<BillDetail> returnList = new ArrayList<BillDetail>();
		for (int i = 0; i < list.size(); i++) {
			BillDetail billDetail = (BillDetail) list.get(i);
			String typeCode = billDetail.getBillMaster().getBillType()
					.getCode().trim();
			//
			// billtype code 结转进口 1004 结转料件退货单 2009 结转成品退货单
			//
			if (typeCode.equals("1106") || typeCode.equals("2009")) {
				returnList.add(0, billDetail);// 加入到第一个
			} else {
				returnList.add(billDetail);// 加入到后面
			}
		}
		return returnList;
	}

	/**
	 * 单据对应
	 * 
	 * @param listC
	 *            报关单商品信息与海关帐单据的对应
	 * @param list
	 *            临时单据明细
	 * @return 单据对应 报关单商品信息与海关帐单据的对应
	 * 
	 * （wss:2010.06.11整修）
	 */
	public TempResult billCorresponding(
			List<CustomsDeclarationCommInfoBillCorresponding> listC,
			List<BillDetail> list) {
		Integer impExpType = listC.get(0).getImpExpType();
		BillCorrespondingControl b = CommonUtils.getBillCorrespondingControl();

		// 结转单据对应,包括结转退货单
		if (b.getIsTransferBack() != null
				&& b.getIsTransferBack().booleanValue() == true
				&& (ImpExpType.TRANSFER_FACTORY_IMPORT == impExpType.intValue() 
						|| ImpExpType.TRANSFER_FACTORY_EXPORT == impExpType.intValue())) {
			list = billDetailBySortHsAmount(list);//将退货的放在前边
			for(int i=0;i<list.size();i++){
				System.out.println("wss" + ((BillDetail)list.get(i)).getHsAmount());
			}
		}

		TempResult lsResult = new TempResult();

		//遍历单据体
		for (int i = 0; i < list.size(); i++) {
			BillDetail billDetail = list.get(i);
			String typeCode = billDetail.getBillMaster().getBillType()
					.getCode().trim();
//			//1106结转料件退货单    2009结转成品退货单
//			if (typeCode.equals("1106") || typeCode.equals("2009")) {
//				double hsAmount = (billDetail.getHsAmount() == null ? 0.0
//						: billDetail.getHsAmount());
//				billDetail.setHsAmount(hsAmount == 0.0 ? 0.0 : -hsAmount);//wss问：干吗这里又改回正数？？？
//			}
			
			//未对应报关数量   等于 0  不统计
			if (billDetail.getNoCustomNum() == 0) {
				continue;
			}
			
			//遍历报关单
			for (int k = 0; k < listC.size(); k++) {
				
				//单据中     未和报关单对应的数量
				double noCustomNum = billDetail.getNoCustomNum();
				
				//如果单据商品未对应 == 0,继续下一条    
				if (noCustomNum == 0) {
					break;
				}
				
				//报关单这边的资料
				CustomsDeclarationCommInfoBillCorresponding c = listC.get(k);
				
				//未对应的报关单的商品数量
				double noCorrespondingAmount = c.getNoCorrespondingAmount();
				
				// 如果报关商品末对应的数据小于0,进行下次循环
				if (noCorrespondingAmount <= 0) {
					continue;
				}
				
				double tranferAmount;
				
				//同号取小，异号取负
				if (noCorrespondingAmount >= noCustomNum) { 
					logger.info("[报关单号 == " + c.getCustomsDeclarationCode() 
							+ " 的报关单末对应的数量 == " 
							+ noCorrespondingAmount + " ]" +
							"  >= " +" [单据号 == " + billDetail.getBillNo() +" 的单据末对应的数量 == "+ noCustomNum +" ]" );
					tranferAmount = noCustomNum;
				} else {
					logger.info("[报关单号 == " + c.getCustomsDeclarationCode() 
							+ " 的报关单末对应的数量 == " 
							+ noCorrespondingAmount + " ]" +
							"  < " +" [单据号 == " + billDetail.getBillNo() +" 的单据末对应的数量 == "+ noCustomNum +" ]" );
					tranferAmount = noCorrespondingAmount;
				}
				
				// 修改单据信息
				double oldCustomNum = billDetail.getCustomNum() == null ? 0.0
														: billDetail.getCustomNum();
				
				if (typeCode.equals("1106") || typeCode.equals("2009")) {
					billDetail.setCustomNum(oldCustomNum - tranferAmount);
				}else{
					billDetail.setCustomNum(oldCustomNum + tranferAmount);
				}
				
				
				String newCustomNo = c.getCustomsDeclarationCode() + "("
						+ c.getEmsHeadH2k() + ")";
				String oldCustomNo = billDetail.getCustomNo() == null ? ""
						: billDetail.getCustomNo().trim();
				
				billDetail.setCustomNo(("".equals(oldCustomNo) ? ""
						: oldCustomNo + ",")
						+ newCustomNo);
				
				byte[] blen=billDetail.getCustomNo().getBytes();
				System.out.println("blen.length="+blen.length);
				if(blen.length>255){
					billDetail.setCustomNo(oldCustomNo);
					}
				System.out.println("========");
				System.out.println("billDail.getCustomNo="+billDetail.getCustomNo());
				//1106结转料件退货单    2009结转成品退货单
				if (typeCode.equals("1106") || typeCode.equals("2009")) {
					double hsAmount = (billDetail.getHsAmount() == null ? 0.0
							: billDetail.getHsAmount());
					billDetail.setHsAmount(hsAmount == 0.0 ? 0.0 : -hsAmount);//又要改成正数 为了更新
				}
				
				this.casSpecificontrolDao.saveOrUpdate(billDetail);
				
				// 修改报关单对应数量
				double oldAlreadyCorrespondingAmount = c.getAlreadyCorrespondingAmount() == null ? 0.0 : 
																c.getAlreadyCorrespondingAmount();
				
//				// 是否包函转厂退货数据				
//				if (typeCode.equals("1106") || typeCode.equals("2009")) {
//					c.setAlreadyCorrespondingAmount(oldAlreadyCorrespondingAmount
//									- tranferAmount);
//				} else {
//					c.setAlreadyCorrespondingAmount(oldAlreadyCorrespondingAmount
//									+ tranferAmount);
//				}
				
					
				c.setAlreadyCorrespondingAmount(oldAlreadyCorrespondingAmount + tranferAmount);

				this.casSpecificontrolDao
						.saveCustomsDeclarationCommInfoBillCorresponding(c);
				
				// 产生中间信息             
				this.makeMakeBillCorrspondingInfo(c, billDetail, tranferAmount);
			}

		}
		lsResult.setC(listC);
		lsResult.setBillDetail(list);
		return lsResult;
	}

	/**
	 * 单据对应时产生的中间信息
	 * 
	 * @param c
	 *            报关单商品信息与海关帐单据的对应
	 * @param billDetail
	 *            单据明细
	 * @param tranferAmount
	 *            结转数量
	 * @return 生产单据对应的中间信息
	 */
	private MakeBillCorrespondingInfoBase makeMakeBillCorrspondingInfo(
			CustomsDeclarationCommInfoBillCorresponding c,
			BillDetail billDetail, Double tranferAmount) {

		int impExpType = c.getImpExpType();
		String materielType = BillUtil.getMaterielTypeByImpExpType(impExpType);// 获取（料件/成品/半成品）

		MakeBillCorrespondingInfoBase temp = null;
		if (materielType.equalsIgnoreCase(MaterielType.MATERIEL)) {
			temp = new MakeBillCorrespondingInfoByMateriel();

		} else if (materielType.equalsIgnoreCase(MaterielType.FINISHED_PRODUCT)) {
			temp = new MakeBillCorrespondingInfoByProduct();
		} else {
			return null;
		}

		temp.setBillDetailId(billDetail.getId());
		temp.setBillNo(billDetail.getBillMaster().getBillNo());
		temp.setImpExpType(impExpType);
		temp.setCompany(CommonUtils.getCompany());
		temp.setCommName(c.getCommName());
		temp.setCommSpec(c.getCommSpec());
		temp.setCustomsDeclarationCode(c.getCustomsDeclarationCode());
		temp.setCustomsDeclarationCommInfoId(c
				.getCustomsDeclarationCommInfoId());
		temp.setCustomsDeclarationId(c.getCustomsDeclarationId());
		temp.setEmsHeadH2k(c.getEmsHeadH2k());
		temp.setPtName(billDetail.getPtName());
		temp.setPtPart(billDetail.getPtPart());
		temp.setPtSpec(billDetail.getPtSpec());
		temp.setValidDate(billDetail.getBillMaster().getValidDate());
		temp.setScmCoc(c.getScmCoc());
		temp.setAmount(tranferAmount);
		temp
				.setBillTypeName(billDetail.getBillMaster().getBillType()
						.getName());
		this.casSpecificontrolDao.getHibernateTemplate().saveOrUpdate(temp);
		return temp;
	}

	/**
	 * 取消单据对应
	 * 
	 * @param list
	 *            生产单据对应的临时中间信息
	 *            
	 * （wss:2010.06.11整修）
	 */
	public void cancelCorresponding(List<MakeBillCorrespondingInfoBase> list) {
		//
		// 删除中间表信息
		//
		List<MakeBillCorrespondingInfoBase> deleteList = new ArrayList<MakeBillCorrespondingInfoBase>();
		//
		// 修改单据对象和单据对应表
		//
		for (int i = 0; i < list.size(); i++) {
			MakeBillCorrespondingInfoBase m = list.get(i);
			//
			// 获得真正的单据对象
			//
			BillDetail billDetail = this.casSpecificontrolDao
					.findBillDetailById(m.getBillDetailId(), m.getImpExpType());
			if (billDetail == null) {
				logger.info(" 删除中间表信息是获取 billDetail is null ");
				throw new RuntimeException("单据号为[" + m.getBillNo()
						+ "的单据可能不存在！请检查！"); 
//				continue;
			}
			//
			// 获得真正的报关信息对象
			//			
			CustomsDeclarationCommInfoBillCorresponding c = this.casSpecificontrolDao
					.findCustomsDeclarationCommInfoBillCorresponding(
													m.getCustomsDeclarationId(), 
													m.getCustomsDeclarationCommInfoId());
			if (c == null) {
				logger
						.info(" 删除中间表信息是获取 CustomsDeclarationCommInfoBillCorresponding is null ");
				continue;
			}

			//对应数量
			double deleteAlreadyCorrespondingAmount = m.getAmount() == null ? 0.0
					: m.getAmount();

			// 修改单据信息
			double customNum = billDetail.getCustomNum() == null ? 0.0
					: billDetail.getCustomNum();
			
			//单据类型
			String typeCode = billDetail.getBillMaster().getBillType()
					.getCode().trim();
			
			if ((typeCode.equals("1106") || typeCode.equals("2009"))) {
				billDetail.setCustomNum(customNum + deleteAlreadyCorrespondingAmount);
			}else{
				billDetail.setCustomNum(customNum - deleteAlreadyCorrespondingAmount);
			}
			
			
			// 为了去掉对应表中的单据的对应关系
			String oldCustomNo = billDetail.getCustomNo() == null ? ""
													: billDetail.getCustomNo().trim();
			String newCustomNo = "";
			String deleteCustomNo = c.getCustomsDeclarationCode() + "("
									+ c.getEmsHeadH2k() + ")";

			String[] strArray = oldCustomNo.split(",");
			for (int j = 0; j < strArray.length; j++) {
				if (!deleteCustomNo.trim().equals(strArray[j].trim())) {
					newCustomNo += strArray[j].trim() + ",";
				}
			}
			if (newCustomNo != null && !newCustomNo.equals("")) {
				// 去掉最后一个逗号
				newCustomNo = newCustomNo
						.substring(0, newCustomNo.length() - 1);
			}
			billDetail.setCustomNo(newCustomNo == null ? "" : newCustomNo);

			this.casSpecificontrolDao.getHibernateTemplate().saveOrUpdate(
					billDetail);
			//
			// 修改报关单对应信息
			//
			double alreadyCorrespondingAmount = c
					.getAlreadyCorrespondingAmount() == null ? 0.0 : c
					.getAlreadyCorrespondingAmount();
			
//			// 是否包函转厂退货数据
//			if ((typeCode.equals("1106") || typeCode.equals("2009"))) {
//				c.setAlreadyCorrespondingAmount(alreadyCorrespondingAmount
//						+ deleteAlreadyCorrespondingAmount);
//			} else {
//				c.setAlreadyCorrespondingAmount(alreadyCorrespondingAmount
//						- deleteAlreadyCorrespondingAmount);
//			}
			
			c.setAlreadyCorrespondingAmount(alreadyCorrespondingAmount
					- deleteAlreadyCorrespondingAmount);

			if (c.getAlreadyCorrespondingAmount() == 0) {
				this.casSpecificontrolDao.getHibernateTemplate().delete(c);
			} else {
				this.casSpecificontrolDao.getHibernateTemplate()
						.saveOrUpdate(c);
			}
			//
			// 中间对应表
			//
			deleteList.add(m);
		}
		// 删除所有选中记录(中间信息表)

		this.casSpecificontrolDao.deleteAll(deleteList);
	}

	/**
	 * 写入单据(手工批量对应操作)
	 * 
	 * @param list
	 *            单据表头
	 * @param corresponding
	 *            对应报关单号
	 */
	public void writeIn(List<BillMaster> list, String corresponding) {
		List billDetailList = this.casSpecificontrolDao.findBillDetail(list);
		for (int i = 0; i < billDetailList.size(); i++) {
			BillDetail billDetail = (BillDetail) billDetailList.get(i);
			billDetail.setCustomNo(corresponding);
			this.casSpecificontrolDao.saveOrUpdate(billDetail);
		}
	}

	/**
	 * 查找所有合同协议号
	 * 
	 * @param impExpType
	 *            进出口类型
	 * @return 与指定的进出口类型匹配且来自纸质手册的合同协议号
	 */
	public List findEmsNo() {
		List list = new ArrayList();
		list.addAll(this.casSpecificontrolDao.findEmsNo(ProjectType.BCS));
		list.addAll(this.casSpecificontrolDao.findEmsNo(ProjectType.BCUS));
		list.addAll(this.casSpecificontrolDao.findEmsNo(ProjectType.DZSC));
		return list;
	}

	/**
	 * 查找所有报关单号
	 * 
	 * @param impExpType
	 *            进出口类型
	 * @return 与指定的进出口类型相同且来自纸质手册和联网监管的报关单号
	 */
	public List findCustomsDeclarationCode(Integer impExpType, String emsNo) {
		List list = new ArrayList();
		list.addAll(this.casSpecificontrolDao.findCustomsDeclarationCode(
				impExpType, ProjectType.BCS, emsNo));
		list.addAll(this.casSpecificontrolDao.findCustomsDeclarationCode(
				impExpType, ProjectType.BCUS, emsNo));
		list.addAll(this.casSpecificontrolDao.findCustomsDeclarationCode(
				impExpType, ProjectType.DZSC, emsNo));
		return list;
	}

	/**
	 * 查询已对应的单据与报关单信息
	 * 
	 * @param temp
	 * @return
	 */
	public List findMakeBillCorrespondingInfo(TempBillCorrespondingSearch temp) {
		List list = this.casSpecificontrolDao
				.findMakeBillCorrespondingInfo(temp);
		//
		// 有可能要做一些动作,这要看客户的要求.....
		//
		//
		// for (int i = 0, n = list.size(); i < n; i++) {
		// MakeBillCorrespondingInfoBase m = (MakeBillCorrespondingInfoBase)
		// list
		// .get(i);
		// // ...
		// }
		//
		return list;
	}

	/**
	 * 记帐，和帐务记帐回卷
	 * 
	 * @param list
	 *            单据表头
	 * @param isEffective
	 *            是否记帐
	 */
	public void saveBillMaster(List<BillMaster> list, boolean isEffective) {
		for (int i = 0; i < list.size(); i++) {
			BillMaster billMaster = list.get(i);
			billMaster.setKeepAccounts(isEffective);
			this.casSpecificontrolDao.saveOrUpdate(billMaster);
		}
	}

	/**
	 * 批量删除单据 返回删除成功与出错的记录
	 * 
	 * @param billmasterlist
	 *            需要删除的单据对象
	 */
	public void deleteBatchBillMaster(List<BillMaster> billmasterlist) {
		for (BillMaster bm : billmasterlist) {
			this.casBillDao.deleteBillMaster(bm);
		}
	}

	/**
	 * 生成单据的折算报关数量(未折算报关数量的单据) 当前公司，由客户端调用
	 * 
	 * @param setupParameters
	 *            生成单据的折算报关数量(未折算报关数量的单据)
	 */
	public void makeBillHsAmount(TempMaterielTypeSetup setupParameters) {
		this.makeBillHsAmount(setupParameters, (Company) CommonUtils
				.getCompany());
	}

	/**
	 * 生成单据的折算报关数量(未折算报关数量的单据)
	 * 
	 * @param setupParameters
	 *            生成单据的折算报关数量(未折算报关数量的单据)
	 * @param company
	 *            公司名称
	 */
	private synchronized void makeBillHsAmount(
			TempMaterielTypeSetup setupParameters, Company company) {
		Boolean isMakeCustomsInfo = setupParameters.getIsMakeCustomsInfo();
		// 成品
		if (setupParameters.getIsProuduct() == true) {
			this.makeBillHsAmount(company, MaterielType.FINISHED_PRODUCT,
					isMakeCustomsInfo, setupParameters, "成品");
			this.makeBillHsAmountFromRelation(company, MaterielType.FINISHED_PRODUCT,
					isMakeCustomsInfo, setupParameters, "成品");			
		}
		// 料件
		if (setupParameters.getIsMateriel() == true) {
//			this.makeBillHsAmount(company, MaterielType.MATERIEL,
//					isMakeCustomsInfo, setupParameters, "料件");
			this.makeBillHsAmountFromRelation(company, MaterielType.MATERIEL,
					isMakeCustomsInfo, setupParameters, "成品");
		}
		// 半成品
		if (setupParameters.getIsSemiProduct() == true) {
//			this.makeBillHsAmount(company, MaterielType.SEMI_FINISHED_PRODUCT,
//					isMakeCustomsInfo, setupParameters, "半成品");
			this.makeBillHsAmountFromRelation(company, MaterielType.SEMI_FINISHED_PRODUCT,
					isMakeCustomsInfo, setupParameters, "成品");
		}
		// 设备
		if (setupParameters.getIsMachine() == true) {
//			this.makeBillHsAmount(company, MaterielType.MACHINE,
//					isMakeCustomsInfo, setupParameters, "设备");
			this.makeBillHsAmountFromRelation(company, MaterielType.MACHINE,
					isMakeCustomsInfo, setupParameters, "成品");
		}
		// 残次品
		if (setupParameters.getIsBadProduct() == true) {
//			this.makeBillHsAmount(company, MaterielType.BAD_PRODUCT,
//					isMakeCustomsInfo, setupParameters, "残次品");
			this.makeBillHsAmountFromRelation(company, MaterielType.BAD_PRODUCT,
					isMakeCustomsInfo, setupParameters, "成品");
		}
		// 边角料
		if (setupParameters.getIsRemainMateriel() == true) {
//			this.makeBillHsAmount(company, MaterielType.REMAIN_MATERIEL,
//					isMakeCustomsInfo, setupParameters, "边角料");
			this.makeBillHsAmountFromRelation(company, MaterielType.REMAIN_MATERIEL,
					isMakeCustomsInfo, setupParameters, "成品");
		}
	}

	/**
	 * 生成单据的折算报关数量(未折算报关数量的单据)
	 * 
	 * @param setupParameters
	 *            生成单据的折算报关数量(未折算报关数量的单据)
	 * @return 所有的生成单据的折算报关单的数量
	 */
	public synchronized String makeBillHsAmountByBatch(
			TempMaterielTypeSetup setupParameters) {
		Integer sumCount = 0;
		Integer updateRows = 0;
		// 成品
		if (setupParameters.getIsProuduct() == true) {
			Integer count = this.casBillDao
					.findBillDetailCount(MaterielType.FINISHED_PRODUCT);
			makeBillHsAmountTipMessage = "成品 共 " + count + " 条记录,正在成批计算 ";
			System.out.println(makeBillHsAmountTipMessage);
//			this.makeBillHsAmountBatch(MaterielType.FINISHED_PRODUCT,
//					setupParameters);
			this.makeBillHsAmountBatchFromRelation(MaterielType.FINISHED_PRODUCT,
					setupParameters);			
			sumCount += count;
		}
		// 料件
		if (setupParameters.getIsMateriel() == true) {
			Integer count = this.casBillDao
					.findBillDetailCount(MaterielType.MATERIEL);
			makeBillHsAmountTipMessage = "料件 共 " + count + " 条记录,正在成批计算 ";
			System.out.println(makeBillHsAmountTipMessage);
//			this.makeBillHsAmountBatch(MaterielType.MATERIEL, setupParameters);
			this.makeBillHsAmountBatchFromRelation(MaterielType.MATERIEL,
					setupParameters);
			sumCount += count;
		}
		// 半成品
		if (setupParameters.getIsSemiProduct() == true) {
			Integer count = this.casBillDao
					.findBillDetailCount(MaterielType.SEMI_FINISHED_PRODUCT);
			makeBillHsAmountTipMessage = "半成品 共" + count + " 条记录,正在成批计算 ";
			System.out.println(makeBillHsAmountTipMessage);
//			this.makeBillHsAmountBatch(MaterielType.SEMI_FINISHED_PRODUCT,
//					setupParameters);
			this.makeBillHsAmountBatchFromRelation(MaterielType.SEMI_FINISHED_PRODUCT,
					setupParameters);			
			sumCount += count;
		}
		// 设备
		if (setupParameters.getIsMachine() == true) {
			Integer count = this.casBillDao
					.findBillDetailCount(MaterielType.MACHINE);
			makeBillHsAmountTipMessage = "设备 共" + count + " 条记录,正在成批计算 ";
			System.out.println(makeBillHsAmountTipMessage);
//			this.makeBillHsAmountBatch(MaterielType.MACHINE, setupParameters);
			this.makeBillHsAmountBatchFromRelation(MaterielType.MACHINE,
					setupParameters);
			sumCount += count;
		}
		// 残次品
		if (setupParameters.getIsBadProduct() == true) {
			Integer count = this.casBillDao
					.findBillDetailCount(MaterielType.BAD_PRODUCT);
			makeBillHsAmountTipMessage = "残次品 共" + count + " 条记录,正在成批计算 ";
			System.out.println(makeBillHsAmountTipMessage);
//			this.makeBillHsAmountBatch(MaterielType.BAD_PRODUCT,
//					setupParameters);
			this.makeBillHsAmountBatchFromRelation(MaterielType.BAD_PRODUCT,
					setupParameters);
			sumCount += count;
		}
		// 边角料
		if (setupParameters.getIsRemainMateriel() == true) {
			Integer count = this.casBillDao
					.findBillDetailCount(MaterielType.REMAIN_MATERIEL);
			makeBillHsAmountTipMessage = "边角料 共" + count + " 条记录,正在成批计算 ";
			System.out.println(makeBillHsAmountTipMessage);
//			this.makeBillHsAmountBatch(MaterielType.REMAIN_MATERIEL,
//					setupParameters);
			this.makeBillHsAmountBatchFromRelation(MaterielType.REMAIN_MATERIEL,
					setupParameters);
			sumCount += count;
		}
		return "共计算 " + sumCount + " 条记录,修改了 " + sumCount + " 条记录";

	}

	/**
	 * 生成单据的折算报关数量(未折算报关数量的单据)
	 * 
	 * @param materielType
	 *            物料类型
	 * @param setupParameters
	 *            生成单据的折算报关数量(未折算报关数量的单据)
	 */
	private void makeBillHsAmountBatch(String materielType,
			TempMaterielTypeSetup setupParameters) {
		long beginTime = System.currentTimeMillis();
		List listUnitConvert = this.casSpecificontrolDao
				.findStatCusNameRelationMt(materielType, (Company) CommonUtils
						.getCompany());
		//
		// 查询提示的总数量
		//
		// Integer count = this.casBillDao.findBillDetailCount(materielType);
		// this.casBillDao.makeHsAmountByBatch(materielType,listUnitConvert);
		boolean isUpdateHsAmount = setupParameters.getIsUpdateHsAmount() == null ? false
				: setupParameters.getIsUpdateHsAmount();
		List<StatCusNameRelationMt> temp = new ArrayList<StatCusNameRelationMt>();
		for (int i = 0, n = listUnitConvert.size(); i < n; i++) {
			StatCusNameRelationMt statM = (StatCusNameRelationMt) listUnitConvert
					.get(i);
			temp.add(statM);
			if (i % 50 == 0 || i == n - 1) {
				this.casBillDao.makeHsAmountByBatch(materielType, temp,
						isUpdateHsAmount);
				temp.clear();
				System.out.println(i + " "
						+ (System.currentTimeMillis() - beginTime));
			}
		}

	}
	
	/**
	 * 生成单据的折算报关数量(未折算报关数量的单据)
	 * 
	 * @param materielType
	 *            物料类型
	 * @param setupParameters
	 *            生成单据的折算报关数量(未折算报关数量的单据)
	 */
	private void makeBillHsAmountBatchFromRelation(String materielType,
			TempMaterielTypeSetup setupParameters) {
		long beginTime = System.currentTimeMillis();
		List listUnitConvert = this.casSpecificontrolDao
				.findMaterielFromRalation(materielType, (Company) CommonUtils
						.getCompany());
		boolean isUpdateHsAmount = setupParameters.getIsUpdateHsAmount() == null ? false
				: setupParameters.getIsUpdateHsAmount();
		List<FactoryAndFactualCustomsRalation> temp = new ArrayList<FactoryAndFactualCustomsRalation>();
		for (int i = 0, n = listUnitConvert.size(); i < n; i++) {
			FactoryAndFactualCustomsRalation statM = (FactoryAndFactualCustomsRalation) listUnitConvert
					.get(i);
			temp.add(statM);
			if (i % 50 == 0 || i == n - 1) {
				this.casBillDao.makeHsAmountByBatchFromRelation(materielType, temp,
						isUpdateHsAmount);
				temp.clear();
				System.out.println(i + " "
						+ (System.currentTimeMillis() - beginTime));
			}
		}

	}	

	/**
	 * 生成单据报关数量提示信息
	 */
	private String makeBillHsAmountTipMessage = "";

	/**
	 * 获取生成的单据报关数量提示信息
	 * @return
	 */
	public String getMakeBillHsAmountTipMessage() {
		return makeBillHsAmountTipMessage;
	}

	/**
	 * 生成单据的折算报关数量(未折算报关数量的单据)
	 * 
	 * @param company
	 *            公司名称
	 * @param materielType
	 *            物料类型
	 * @param isMakeCustomsInfo
	 *            是否生成单据信息
	 * @param setupParameters
	 *            生成单据的折算报关数量(未折算报关数量的单据)
	 * @param info
	 *            提示信息
	 */
	private void makeBillHsAmount(Company company, String materielType,
			Boolean isMakeCustomsInfo, TempMaterielTypeSetup setupParameters,
			String info) {
		long beginTime = System.currentTimeMillis();
		List listUnitConvert = this.casSpecificontrolDao
				.findPtNoAndUnitConvert(materielType, company);
		Map<String, Double> map = new HashMap<String, Double>();
		Map<String, StatCusNameRelation> mapStatCusNameRelation = new HashMap<String, StatCusNameRelation>();
		for (int i = 0; i < listUnitConvert.size(); i++) {
			Object[] objs = (Object[]) listUnitConvert.get(i);
			// 以料号为key的 hashMap
			String ptNo = (String) objs[0];
			// 以折算报关系数为 value 的 hashMap
			Double unitConvert = (Double) objs[1];
			// 报关大类对象
			StatCusNameRelation stat = (StatCusNameRelation) objs[2];
			if (ptNo == null || "".equals(ptNo)) {
				logger.info("Jbcus 生成单据的折算报关数量中料号 == " + ptNo);
				continue;
			}
			mapStatCusNameRelation.put(ptNo, stat);
			if (unitConvert == null || unitConvert <= 0) {
				continue;
			}
			map.put(ptNo, unitConvert);
		}
		//
		// 查询提示的总数量
		//
		Integer count = this.casBillDao.findBillDetailCount(materielType);

		//
		// 分页查询并修改
		//
		int index = 0;
		int length = 1000;
		while (true) {
			List<String> listId = new ArrayList<String>();
			if (setupParameters.getIsUpdateHsAmount() == true) {
				listId = this.casBillDao.findBillDetails(materielType, index,
						length, true);
			} else {
				listId = this.casBillDao.findBillDetails(materielType, index,
						length, false);
			}
			int size = listId.size();
			if (size <= 0) {
				break;
			}
			makeBillHsAmountTipMessage = info + " 共 " + count + " 条记录,正在计算前 "
					+ (index + length > count ? count : index + length)
					+ " 条记录...";
			System.out.println(makeBillHsAmountTipMessage);

			String beginId = listId.get(0);
			String endId = listId.get(size - 1);
			List tempBillDetails = this.casBillDao.findBillDetails(
					materielType, beginId, endId);

			List<BillDetail> updateList = new ArrayList<BillDetail>();
			for (int i = 0, n = tempBillDetails.size(); i < n; i++) {

				// logger.info("Jbcus 料号 == " + i);

				BillDetail billDetail = (BillDetail) tempBillDetails.get(i);
				StatCusNameRelation stat = mapStatCusNameRelation
						.get(billDetail.getPtPart());

				boolean isSave = false;

				Double unitConvert = map.get(billDetail.getPtPart());
				if (unitConvert == null || unitConvert <= 0.0) {
					// logger.info("Jbcus 料号 == " + billDetail.getPtPart()
					// + " 的折算系数不存在 !!" + i);
				} else {
					Double ptAmount = billDetail.getPtAmount() == null ? 0.0
							: billDetail.getPtAmount();
					billDetail.setHsAmount(ptAmount * unitConvert);
					isSave = true;
				}
				//
				// 对应报关名称是空
				//
				if (isMakeCustomsInfo) {
					// if (billDetail.getHsName() == null
					// || "".equals(billDetail.getHsName())) {
					if (stat != null) {
						billDetail.setHsName(stat.getCusName());
						billDetail.setComplex(stat.getComplex());
						billDetail.setHsSpec(stat.getCusSpec());
						billDetail.setHsUnit(stat.getCusUnit());
						isSave = true;
					}
					// }
				}
				//
				// 这里直接用 saveOrUpdate 方法是可行的
				//
				if (isSave == true) {
					updateList.add(billDetail);
				}

				if (updateList.size() % 100 == 0 || i == n - 1) {
					this.casBillDao.batchSaveOrUpdate(updateList);
					// System.out.println("updateList.size() ==
					// "+updateList.size());
				}
			}

			index += length;
			if (tempBillDetails.size() <= 0 || tempBillDetails.size() < length) {
				break;
			}
			if ((index + length) % 10000 == 0) {
				System.out.println("生成单据的折算报关数量(未折算报关数量的单据):前 "
						+ (index + length) + " 条共用时间"
						+ (System.currentTimeMillis() - beginTime) + " 毫秒 ");
			}
		}
	}
	
	/**
	 * 生成单据的折算报关数量(未折算报关数量的单据)--不是批量
	 * 
	 * @param company
	 *            公司名称
	 * @param materielType
	 *            物料类型
	 * @param isMakeCustomsInfo
	 *            是否生成单据信息
	 * @param setupParameters
	 *            生成单据的折算报关数量(未折算报关数量的单据)
	 * @param info
	 *            提示信息
	 */
	private void makeBillHsAmountFromRelation(Company company, String materielType,
			Boolean isMakeCustomsInfo, TempMaterielTypeSetup setupParameters,
			String info) {
		long beginTime = System.currentTimeMillis();
		List listUnitConvert = this.casSpecificontrolDao
				.findPtNoAndUnitConvertFromRelation(materielType, company);
		Map<String, Double> map = new HashMap<String, Double>();
		Map<String, StatCusNameRelationHsn> mapStatCusNameRelation = new HashMap<String, StatCusNameRelationHsn>();
		for (int i = 0; i < listUnitConvert.size(); i++) {
			Object[] objs = (Object[]) listUnitConvert.get(i);
			// 以料号为key的 hashMap
			String ptNo = (String) objs[1];
			// 以折算报关系数为 value 的 hashMap
			Double unitConvert = (Double) objs[2];
			// 报关大类对象
			StatCusNameRelationHsn stat = (StatCusNameRelationHsn) objs[0];
			if (ptNo == null || "".equals(ptNo)) {
				logger.info("Jbcus 生成单据的折算报关数量中料号 == " + ptNo);
				continue;
			}
			mapStatCusNameRelation.put(ptNo, stat);
			if (unitConvert == null || unitConvert <= 0) {
				continue;
			}
			map.put(ptNo, unitConvert);
		}
		//
		// 查询提示的总数量
		//
		Integer count = this.casBillDao.findBillDetailCount(materielType);

		//
		// 分页查询并修改
		//
		int index = 0;
		int length = 1000;
		while (true) {
			List<String> listId = new ArrayList<String>();
			if (setupParameters.getIsUpdateHsAmount() == true) {
				listId = this.casBillDao.findBillDetails(materielType, index,
						length, true);
			} else {
				listId = this.casBillDao.findBillDetails(materielType, index,
						length, false);
			}
			int size = listId.size();
			if (size <= 0) {
				break;
			}
			makeBillHsAmountTipMessage = info + " 共 " + count + " 条记录,正在计算前 "
					+ (index + length > count ? count : index + length)
					+ " 条记录...";
			System.out.println(makeBillHsAmountTipMessage);

			String beginId = listId.get(0);
			String endId = listId.get(size - 1);
			List tempBillDetails = this.casBillDao.findBillDetails(
					materielType, beginId, endId);

			List<BillDetail> updateList = new ArrayList<BillDetail>();
			for (int i = 0, n = tempBillDetails.size(); i < n; i++) {

				// logger.info("Jbcus 料号 == " + i);

				BillDetail billDetail = (BillDetail) tempBillDetails.get(i);
				if(billDetail.getPtPart()==null){
					StatCusNameRelationHsn stat = mapStatCusNameRelation
							.get(billDetail.getPtPart());
	
					boolean isSave = false;
	
					Double unitConvert = map.get(billDetail.getPtPart());
					if (unitConvert == null || unitConvert <= 0.0) {
						// logger.info("Jbcus 料号 == " + billDetail.getPtPart()
						// + " 的折算系数不存在 !!" + i);
					} else {
						Double ptAmount = billDetail.getPtAmount() == null ? 0.0
								: billDetail.getPtAmount();
						billDetail.setHsAmount(ptAmount * unitConvert);
						isSave = true;
					}
					//
					// 对应报关名称是空
					//
					if (isMakeCustomsInfo) {
						// if (billDetail.getHsName() == null
						// || "".equals(billDetail.getHsName())) {
						if (stat != null) {
							billDetail.setHsName(stat.getCusName());
							billDetail.setComplex(stat.getComplex());
							billDetail.setHsSpec(stat.getCusSpec());
							billDetail.setHsUnit(stat.getCusUnit());
							isSave = true;
						}
						// }
					}
					//
					// 这里直接用 saveOrUpdate 方法是可行的
					//
					if (isSave == true) {
						updateList.add(billDetail);
					}
	
					if (updateList.size() % 100 == 0 || i == n - 1) {
						this.casBillDao.batchSaveOrUpdate(updateList);
						// System.out.println("updateList.size() ==
						// "+updateList.size());
					}
				}
			}
				

			index += length;
			if (tempBillDetails.size() <= 0 || tempBillDetails.size() < length) {
				break;
			}
			if ((index + length) % 10000 == 0) {
				System.out.println("生成单据的折算报关数量(未折算报关数量的单据):前 "
						+ (index + length) + " 条共用时间"
						+ (System.currentTimeMillis() - beginTime) + " 毫秒 ");
			}
		}
	}	

	/**
	 * 由半成品转成料件(委外加工进库单据--->委外加工返回料件单据) 1103 == 委外加工返回料件单据 4003 == 委外加工进库单据
	 */
	public void mark1013By4003() {
		halfProductTransferMateriel("4003", "1013");
	}

	/**
	 * 由半成品转成料件(委外加工出库单据--->车间领用单据) 1110 == 外发加工出库单据 4103 == 委外加工出库单据
	 */
	public void make1110By4103() {
		halfProductTransferMateriel("4103", "1110");
	}

	/**
	 * 由半成品转成料件(委外加工出库单据--->车间领用单据) 1110 == 外发加工出库单据 4105 == 委外加工出库单据
	 */
	public void mark1101By4105() {
		halfProductTransferMateriel("4105", "1101");
	}

	/**
	 * 由半成品转成料件(委外加工出库单据--->车间领用单据) 1112 == 外发加工出库单据 4104 == 委外加工出库单据
	 */
	public void mark1112By4104() {
		halfProductTransferMateriel("4104", "1112");
	}

	/**
	 * 半成品转料件
	 * 
	 * @param halfProdcutBillCode
	 *            半成品单据号
	 * @param materielBillCode
	 *            料件单据号
	 */
	private void halfProductTransferMateriel(String halfProdcutBillCode,
			String materielBillCode) {
		//
		// 1103 委外加工返回料件单据
		//
		BillType billType1103 = this.casDao
				.findBillTypeByCode(materielBillCode);
		//
		// 分页查询并修改
		//
		int index = 0;
		int length = 1000;
		while (true) {
			List tempBillDetails = this.casBillDao.findBillDetails(
					MaterielType.SEMI_FINISHED_PRODUCT, halfProdcutBillCode,
					index, length);
			Map<String, BillMaster> billMasterMap = new HashMap<String, BillMaster>();
			Map<String, List<BillDetail>> billDetailMap = new HashMap<String, List<BillDetail>>();

			for (int i = 0, n = tempBillDetails.size(); i < n; i++) {
				BillDetail billDetail = (BillDetail) tempBillDetails.get(i);
				String key = billDetail.getBillMaster().getBillNo();
				if (key == null || "".equals(key)) {
					return;
				}
				if (!billMasterMap.containsKey(key)) {
					billMasterMap.put(key, billDetail.getBillMaster());
				}
				if (!billDetailMap.containsKey(key)) {
					List<BillDetail> list = new ArrayList<BillDetail>();
					list.add(billDetail);
					billDetailMap.put(key, list);
				} else {
					List<BillDetail> list = billDetailMap.get(key);
					list.add(billDetail);
				}
			}

			//
			// 进行1103保存(把半成品转换成料件)
			//
			Iterator<BillMaster> iterator = billMasterMap.values().iterator();
			while (iterator.hasNext()) {
				BillMaster billMaster = iterator.next();
				billMaster.setIsFlag(true);
				this.casBillDao.getHibernateTemplate().update(billMaster);

				// System.out.println("billMaster billno is update = " +
				// billMaster.getBillNo());

				BillMasterMateriel billMasterMateriel = new BillMasterMateriel();
				billMasterMateriel.setBillType(billType1103);
				billMasterMateriel.setIsFlag(false);
				billMasterMateriel.setBillNo(billMaster.getBillNo());
				billMasterMateriel.setCompany(billMaster.getCompany());
				billMasterMateriel.setIsValid(billMaster.getIsValid());
				billMasterMateriel.setScmCoc(billMaster.getScmCoc());
				billMasterMateriel
						.setKeepAccounts(billMaster.getKeepAccounts());
				billMasterMateriel.setValidDate(billMaster.getValidDate());

				this.casBillDao.getHibernateTemplate().save(billMasterMateriel);

				// System.out.println("billMaster billno is save = " +
				// billMasterMateriel.getBillNo());

				List<BillDetail> billDetailList = billDetailMap
						.get(billMasterMateriel.getBillNo());
				List<BillDetail> billDetai1103lList = this
						.get1103BillDetailByTransfer(billMasterMateriel,
								billDetailList);
				for (int i = 0, n = billDetai1103lList.size(); i < n; i++) {
					this.casBillDao.getHibernateTemplate().save(
							billDetai1103lList.get(i));
				}
			}

			index += length;
			if (tempBillDetails.size() <= 0 || tempBillDetails.size() < length) {
				break;
			}
		}
	}

	/**
	 * 
	 * 半成品-->转换成1103(委外加工返回料件单据)
	 * 
	 * @param billMaster
	 *            单据表头
	 * @param billDetailList
	 *            单据明细
	 * @return 符合指定条件的单据明细
	 */
	private List<BillDetail> get1103BillDetailByTransfer(BillMaster billMaster,
			List<BillDetail> billDetailList) {
		List<MaterialBomDetail> bomList = new ArrayList<MaterialBomDetail>();
		//
		// 获得 Bom 列表
		//
		List<String> ptNoList = new ArrayList<String>();
		for (int i = 0, n = billDetailList.size(); i < n; i++) {
			BillDetail billDetail = billDetailList.get(i);

			ptNoList.add(billDetail.getPtPart());
			//
			// 50个参数查询一次
			//
			if ((i % 50) == 0 || i == billDetailList.size() - 1) {
				List<MaterialBomDetail> tempBomList = this.casBillDao
						.findMaterielBomDetail(ptNoList);
				ptNoList.clear();
				bomList.addAll(tempBomList);
			}
		}
		//
		// 版本Bom Map , Bom 明细 Map
		//
		Map<String, Map<String, MaterialBomVersion>> bomVersionMap = new HashMap<String, Map<String, MaterialBomVersion>>();
		Map<String, List<MaterialBomDetail>> bomDetailMap = new HashMap<String, List<MaterialBomDetail>>();
		for (int i = 0, n = bomList.size(); i < n; i++) {
			MaterialBomDetail materialBomDetail = bomList.get(i);
			MaterialBomVersion version = materialBomDetail.getVersion();
			MaterialBomMaster bomMaster = version.getMaster();
			String ptNo = bomMaster.getMateriel().getPtNo();
			// bom 版本
			if (!bomVersionMap.containsKey(ptNo)) {
				Map<String, MaterialBomVersion> tempMap = new HashMap<String, MaterialBomVersion>();
				tempMap.put(version.getId(), version);
				bomVersionMap.put(ptNo, tempMap);
			} else {
				Map<String, MaterialBomVersion> tempMap = bomVersionMap
						.get(ptNo);
				if (tempMap.get(version.getId()) == null) {
					tempMap.put(version.getId(), version);
				}
			}
			// bom 明细
			if (!bomDetailMap.containsKey(version.getId())) {
				List<MaterialBomDetail> tempList = new ArrayList<MaterialBomDetail>();
				tempList.add(materialBomDetail);
				bomDetailMap.put(version.getId(), tempList);
			} else {
				List<MaterialBomDetail> tempList = bomDetailMap.get(version
						.getId());
				tempList.add(materialBomDetail);
			}
		}

		//
		// 组合新的料件对象(1103 单据明细)
		// 
		Map<String, BillDetailMateriel> billDetailMap = new HashMap<String, BillDetailMateriel>();
		//
		// for
		//
		for (int i = 0, n = billDetailList.size(); i < n; i++) {
			//
			// 单成品
			//
			BillDetail billDetail4003 = billDetailList.get(i);
			//
			// 成品版本
			//
			Integer version = billDetail4003.getVersion() == null ? 0
					: billDetail4003.getVersion();
			//
			// 料号
			//
			String key = billDetail4003.getPtPart();

			Map<String, MaterialBomVersion> tempMap = bomVersionMap.get(key);
			if (tempMap == null || tempMap.size() <= 0) {
				System.out.println("  continue 1");
				continue;
			}
			Iterator<MaterialBomVersion> versionIterator = tempMap.values()
					.iterator();

			MaterialBomVersion currVersion = null;

			while (versionIterator.hasNext()) {
				MaterialBomVersion temp = versionIterator.next();
				Integer tempVersion = temp.getVersion();
				if (tempVersion.intValue() == version.intValue()) {
					currVersion = temp;
					break;
				}
			}
			if (currVersion == null) {
				System.out.println("  continue 2");
				continue;
			}
			List<MaterialBomDetail> bomDetailList = bomDetailMap
					.get(currVersion.getId());

			if (bomDetailList == null || bomDetailList.size() <= 0) {
				System.out.println("  continue 3");
				continue;
			}

			for (int j = 0, size = bomDetailList.size(); j < size; j++) {
				MaterialBomDetail bomDetail = bomDetailList.get(j);
				//
				// 料件
				//
				String ptNo = bomDetail.getMateriel().getPtNo();
				if (!billDetailMap.containsKey(ptNo)) {
					BillDetailMateriel billDetail1103 = new BillDetailMateriel();
					billDetail1103.setBillMaster(billMaster);
					billDetail1103.setCompany(billDetail4003.getCompany());
					billDetail1103.setComplex(bomDetail.getMateriel()
							.getComplex());

					billDetail1103.setPtName(bomDetail.getMateriel()
							.getFactoryName());
					billDetail1103.setPtSpec(bomDetail.getMateriel()
							.getFactorySpec());

					billDetail1103.setPtPart(bomDetail.getMateriel().getPtNo());
					billDetail1103.setPtUnit(bomDetail.getMateriel()
							.getCalUnit());
					Double ptAmout = billDetail4003.getPtAmount() == null ? 0.0
							: billDetail4003.getPtAmount();
					Double unitUsed = bomDetail.getUnitUsed() == null ? 0.0
							: bomDetail.getUnitUsed();
					billDetail1103.setPtAmount(ptAmout * unitUsed);
					billDetail1103.setWareSet(billDetail4003.getWareSet());
					billDetailMap.put(ptNo, billDetail1103);
				} else {
					BillDetailMateriel billDetail1103 = billDetailMap.get(ptNo);
					Double ptAmout = billDetail4003.getPtAmount() == null ? 0.0
							: billDetail4003.getPtAmount();
					Double unitUsed = bomDetail.getUnitUsed() == null ? 0.0
							: bomDetail.getUnitUsed();
					Double ptAmount = ptAmout * unitUsed;
					billDetail1103.setPtAmount(billDetail1103.getPtAmount()
							+ ptAmount);
				}
			}
		}
		//
		// 返回结果
		//
		List<BillDetail> resultList = new ArrayList<BillDetail>();
		resultList.addAll(billDetailMap.values());
		return resultList;
	}

}
