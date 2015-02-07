/*
 * Created on 2005-4-26
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.dbimport.logic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import org.apache.commons.validator.GenericValidator;

import com.bestway.bcs.bcsinnermerge.entity.BcsTenInnerMerge;
import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contract.entity.ContractBom;
import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcs.contract.entity.ContractImg;
import com.bestway.bcs.contractexe.entity.BcsCustomsDeclaration;
import com.bestway.bcs.contractexe.entity.BcsCustomsDeclarationCommInfo;
import com.bestway.bcs.contractexe.entity.BcsCustomsDeclarationContainer;
import com.bestway.bcus.cas.bill.entity.BillDetailMateriel;
import com.bestway.bcus.cas.bill.entity.BillMasterMateriel;
import com.bestway.bcus.cas.dao.CasDao;
import com.bestway.bcus.cas.entity.BillType;
import com.bestway.bcus.custombase.entity.basecode.Brief;
import com.bestway.bcus.custombase.entity.basecode.MachiningType;
import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.countrycode.District;
import com.bestway.bcus.custombase.entity.countrycode.PortLin;
import com.bestway.bcus.custombase.entity.countrycode.PreDock;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.BalanceMode;
import com.bestway.bcus.custombase.entity.parametercode.BargainType;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.LevyKind;
import com.bestway.bcus.custombase.entity.parametercode.LevyMode;
import com.bestway.bcus.custombase.entity.parametercode.PayMode;
import com.bestway.bcus.custombase.entity.parametercode.SrtJzx;
import com.bestway.bcus.custombase.entity.parametercode.SrtTj;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.bcus.custombase.entity.parametercode.Transac;
import com.bestway.bcus.custombase.entity.parametercode.Transf;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.custombase.entity.parametercode.Uses;
import com.bestway.bcus.custombase.entity.parametercode.Wrap;
import com.bestway.bcus.dbimport.dao.DbCasDao;
import com.bestway.bcus.dbimport.dao.DbimportDao;
import com.bestway.bcus.enc.entity.CustomsDeclaration;
import com.bestway.bcus.enc.entity.CustomsDeclarationCommInfo;
import com.bestway.bcus.enc.entity.CustomsDeclarationContainer;
import com.bestway.bcus.system.entity.Company;
import com.bestway.common.CommonUtils;
import com.bestway.common.MaterielType;
import com.bestway.common.authority.entity.AclUser;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.materialbase.entity.CalUnit;
import com.bestway.common.materialbase.entity.CurrRate;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.materialbase.entity.WareSet;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;
import com.bestway.customs.common.entity.BaseCustomsDeclarationContainer;
import com.bestway.dzsc.contractexe.entity.DzscCustomsDeclaration;
import com.bestway.dzsc.contractexe.entity.DzscCustomsDeclarationCommInfo;
import com.bestway.dzsc.contractexe.entity.DzscCustomsDeclarationContainer;

/**
 * 数据库操作接口
 * checked by cjb 2010.2
 * @author // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class DbBcsLogic {
	/**
	 * 海关帐单据相关操作
	 */
	private DbCasDao dbCasDao = null;
	/**
	 * 海关帐DAO类
	 */
	private CasDao casDao = null;
	/**
	 * 数据库导入接口
	 */
	private DbimportDao dbimportDao = null;
	/**
	 * 数据库连接
	 */
	private Connection conn = null;
	/**
	 * 数据库语句
	 */
	private Statement st = null;
	/**
	 * 选择查询结果行数
	 */
	private int fetchsize = 500;
	/**
	 * 数据库服务器名称
	 */
	private String serverName = null;
	/**
	 * 数据库名称
	 */
	private String dbName = null;
	/**
	 * 数据库用户名
	 */
	private String userName = null;
	/**
	 * 数据库密码
	 */
	private String password = null;

	/**
	 *  连接数据源
	 * @return
	 */
	public Connection getConn() {
		try {
			String driverClassName = "net.sourceforge.jtds.jdbc.Driver";
			String url = "jdbc:jtds:sqlserver://" + serverName + ":1433/"
					+ dbName + ";tds=8.0;lastupdatecount=true";
			if (conn == null) {
				Class.forName(driverClassName);
				conn = DriverManager.getConnection(url, userName, password);
			}
			return conn;
		} catch (SQLException e1) {
			throw new RuntimeException(e1);
		} catch (ClassNotFoundException e2) {
			throw new RuntimeException(e2);
		}
	}

	/**
	 * 获取操作语句
	 */
	private void getst() {
		try {
			st = getConn().createStatement();
			st.setFetchSize(fetchsize);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 格式化Doublei (String ->Double)
	 */
	public Double formatStrDouble(String dou) {
		if (dou != null && !dou.equals("")) {
			return Double.valueOf(dou);
		}
		return Double.valueOf(0);
	}

	/**
	 * 把日期字符串转成日期类型
	 * @param input
	 * @return
	 */
	public Date strToStandDate(String input) {
		if (input == null || input.equals("0") || input.length() == 0) {
			return null;
		}
		java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		try {
			return dateFormat.parse(input);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 把布尔型字符串转布尔型
	 * @param str
	 * @return
	 */
	public Boolean strToBoolean(String str) {
		if (str != null && str.equals("Y")) {
			return new Boolean(true);
		} else if (str != null && str.equals("N")) {
			return new Boolean(false);
		}
		return null;
	}

	/**
	 * 把整型字符串转整型
	 * @param str
	 * @return
	 */
	private double StrTodou(String str) {
		if (str == null || str.equals("")) {
			return 0;
		}
		return Double.parseDouble(str);
	}

	/**
	 * 导入数据
	 * @param list
	 * @param serverName
	 * @param dbName
	 * @param userName
	 * @param password
	 * @param hccode
	 * @param emsNo
	 */
	public void importData(List list, String serverName, String dbName,
			String userName, String password, String hccode, String emsNo) {
		this.setServerName(serverName);
		this.setDbName(dbName);
		this.setUserName(userName);
		this.setPassword(password);

		for (int i = 0; i < list.size(); i++) {
			try {
				String x = (String) list.get(i);
				System.out.println("-- 系统正在导入：" + x);
				if (x.equals("汇率")) {
					insertCurrRate();
				} else if (x.equals("包装种类")) {
					insertWrap();
					/*
					 * } else if (x.equals("合同类型")){ insertBargainType();
					 */
				} else if (x.equals("报关商品资料")) {
					insertBaseCustoms();
				} else if (x.equals("商品编码")) {
					insertComplex();
				} else if (x.equals("客户供应商")) {
					insertScmCoc();
				} else if (x.equals("合同备案")) {
					insertContract(hccode);
				} else if (x.equals("进口报关单_纸质手册")) {
					insertJKBGHead(emsNo);
					// insertJKDzscBGHead(emsNo);
				} else if (x.equals("出口报关单_纸质手册")) {
					insertCKBGHead(emsNo);
					// insertCKDzscBGHead(emsNo);
				} else if (x.equals("特殊报关单_纸质手册")) {
					insertTSBGHead(emsNo);
				} else if (x.equals("进口报关单_电子帐册")) {
					System.out.println(x);
					insertJKBGHead_bcus();
				} else if (x.equals("出口报关单_电子帐册")) {
					System.out.println(x);
					insertCKBGHead_bcus();
				} else if (x.equals("特殊报关单_电子帐册")) {
					System.out.println(x);
					insertTSBGHead_bcus(emsNo);
				} else if (x.equals("单据头_单据中心")) {
					System.out.println(x);
					insertDJHead();
				} else if (x.equals("单据体_单据中心")) {
					System.out.println(x);
					insertDJDetail();
				}
				System.out.println("-- 导入：" + x + "完成！");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 设置汇率
	 * 汇率hg_exchange
	 */
	public void insertCurrRate() throws SQLException {
		getst();
		ResultSet rs;
		rs = st.executeQuery("select * from hg_exchange order by src,dst");
		while (rs.next()) {
			System.out.println("11111111111111111111111111111");
			CurrRate obj = new CurrRate();
			Curr curr = dbimportDao.FindbycodecurrSymb(rs.getString("src"));
			System.out.println("222222222222:" + curr);
			obj.setCurr(curr);// 本地
			Curr curr1 = dbimportDao.FindbycodecurrSymb(rs.getString("dst"));
			obj.setCurr1(curr1);// 外币
			obj.setRate(formatStrDouble(rs.getString("exrate")));
			obj.setCreateDate(strToStandDate(rs.getString("settime")));
			obj.setCompany(CommonUtils.getCompany());
			try {
				dbimportDao.SaveObject(obj);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (rs != null)
			rs.close();
	}

	/**
	 * 设置包装种类
	 */
	public void insertWrap() throws SQLException {
		getst();
		ResultSet rs;
		rs = st.executeQuery("select * from hd_baozhuang");
		while (rs.next()) {
			Wrap obj = new Wrap();
			obj.setCode(rs.getString("bzcode"));
			obj.setName(rs.getString("bzname"));
			try {
				dbimportDao.SaveObject(obj);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (rs != null)
			rs.close();
	}

	/**
	 * 插入海关商品编码
	 * @throws SQLException
	 */
	public void insertComplex() throws SQLException {
		getst();
		ResultSet rs;
		rs = st.executeQuery("select * from HD_Complex");
		while (rs.next()) {
			Complex obj = new Complex();
			obj.setCode(rs.getString("ccode"));
			obj.setId(rs.getString("ccode"));
			obj.setName(rs.getString("cname"));
			Unit unit = dbimportDao.Findbynameunit(rs.getString("unit1"));
			obj.setFirstUnit(unit);
			unit = dbimportDao.Findbynameunit(rs.getString("unit2"));
			obj.setSecondUnit(unit);
			obj.setHighrate(rs.getString(("high_rate")));
			obj.setLowrate(rs.getString(("low_rate")));
			obj.setNote(rs.getString(("notes")));
			try {
				dbimportDao.SaveObject(obj);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (rs != null)
			rs.close();
	}

	/**
	 * 插入报关商品编码
	 */
	public void insertBaseCustoms() throws SQLException {
		getst();
		ResultSet rs;
		rs = st.executeQuery("select * from HD_DQPZ");
		while (rs.next()) {
			BcsTenInnerMerge obj = new BcsTenInnerMerge();
			obj.setName(rs.getString("pz02"));
			obj.setSpec(rs.getString("pz03"));
			obj.setSeqNum(Integer.valueOf(rs.getString("pzcode")));
			obj.setUnitWeight(formatStrDouble(rs.getString("pz09")));
			obj.setPrice(formatStrDouble(rs.getString("pz04")));
			Unit unit = dbimportDao.Findbynameunit(rs.getString("pz06"));
			obj.setComUnit(unit);

			if (rs.getString("pz12").equals("料件")) {
				obj.setScmCoi("0");
			} else {
				obj.setScmCoi("2");
			}
			Curr curr1 = dbimportDao.FindbycodecurrSymb(rs.getString("pz11"));
			obj.setCurr(curr1);
			Complex complex = dbimportDao.Findbycodecomplex(rs
					.getString("pz01"));
			obj.setComplex(complex);
			Country country = dbimportDao.Findbynamecountry(rs
					.getString("pz10"));
			obj.setCountry(country);
			obj.setLegalAmount(formatStrDouble(rs.getString("pz071")));
			obj.setSecondLegalAmount(formatStrDouble(rs.getString("pz07")));
			obj.setCompany(CommonUtils.getCompany());
			try {
				dbimportDao.SaveObject(obj);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (rs != null)
			rs.close();
	}

	/**
	 * 插入合同类型
	 */
	public void insertBargainType() throws SQLException {
		getst();
		ResultSet rs;
		rs = st.executeQuery("select * from HD_ContractType");
		while (rs.next()) {
			BargainType obj = new BargainType();
			obj.setCode(rs.getString("ctcode"));
			obj.setName(rs.getString("ctname"));
			obj.setIsBalance(strToBoolean(rs.getString("ctcount")));
			try {
				dbimportDao.SaveObject(obj);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (rs != null)
			rs.close();
	}

	/**
	 * 插入客户供应商
	 */
	public void insertScmCoc() throws SQLException {
		getst();
		ResultSet rs;
		rs = st.executeQuery("select * from hd_Company order by cycode");
		while (rs.next()) {
			ScmCoc obj = new ScmCoc();
			obj.setCode(rs.getString("cycode"));
			obj.setName(rs.getString("cyname"));
			Brief brief = dbimportDao.Findbycodebrief(rs
					.getString("cycustomid"));
			obj.setBrief(brief);
			obj.setFname(rs.getString("cyname"));
			obj.setIsCustomer(new Boolean(true));
			obj.setIsManufacturer(new Boolean(true));
			obj.setIsCollaborater(new Boolean(true));
			obj.setIsWork(new Boolean(true));
			obj.setFlatCode(rs.getString("cymemo"));
			obj.setLinkMan(rs.getString("cylinkman"));
			obj.setLinkTel(rs.getString("cytel"));
			obj.setMoveTel(rs.getString("cymob"));
			obj.setAddre(rs.getString("cyaddress"));
			obj.setCompany(CommonUtils.getCompany());
			try {
				dbimportDao.SaveObject(obj);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (rs != null)
			rs.close();
	}

	/**
	 * 获取字符串str的一个子字符串
	 * @param str
	 * @param bint
	 * @param eint
	 * @return
	 */
	private String getsubstring(String str, int bint, int eint) {
		if (str == null) {
			return null;
		} else {
			try {
				return str.substring(bint, eint);
			} catch (Exception e) {
				return null;
			}
		}
	}

	/**
	 * 插入合同备案
	 * @param hccode
	 * @throws SQLException
	 */
	public void insertContract(String hccode) throws SQLException {
		getst();
		ResultSet rsHead = null;
		String hsql = "select * from hG_contract";
		if (hccode != null && !hccode.equals("")) {
			hsql += " where hccode='" + hccode + "'";
		}
		hsql += " order by hccode,hc32,hc31,hc01";
		rsHead = st.executeQuery(hsql);
		while (rsHead.next()) {
			Contract head = new Contract();
			String state = null;
			if (rsHead.getString("hc32") != null
					&& rsHead.getString("hc32").trim().equals("正在申请")) {
				state = DeclareState.APPLY_POR;
				head.setIsCancel(new Boolean(false));
			} else if (rsHead.getString("hc32") != null
					&& rsHead.getString("hc32").trim().equals("正在变更")) {
				state = DeclareState.CHANGING_EXE;
				head.setIsCancel(new Boolean(false));
			} else if (rsHead.getString("hc32") != null
					&& rsHead.getString("hc32").trim().equals("正在执行")) {
				state = DeclareState.PROCESS_EXE;
				head.setIsCancel(new Boolean(false));
			} else if (rsHead.getString("hc32") != null
					&& rsHead.getString("hc32").trim().equals("核销合同")) {
				state = DeclareState.CHANGING_CANCEL;
				head.setIsCancel(new Boolean(true));
			}
			head.setDeclareState(state);
			head.setTradeName(rsHead.getString("hc03"));
			ScmCoc tradeCode = dbimportDao.Findbynamescmcoc(rsHead
					.getString("hc03"));
			if (tradeCode != null) {
				head.setTradeCode(tradeCode.getBrief() != null ? tradeCode
						.getBrief().getCode() : null);
			}
			String ss = rsHead.getString("hc33");
			// head.setPreContractCodePrefix(getsubstring(ss,0,6));
			// head.setPreContractCodeSuffix(getsubstring(ss,6,12));
			head.setDeclareType(getsubstring(ss, 0, 1));
			head.setMachName(rsHead.getString("hc07"));
			head.setMachCode(rsHead.getString("hc11"));
			head.setEnterpriseAddress(rsHead.getString("hc12"));
			head.setLinkMan(rsHead.getString("hc13"));
			head.setContactTel(rsHead.getString("hc14"));
			head.setOutTradeCo(rsHead.getString("hc15"));
			head.setSancEmsNo(rsHead.getString("hc17"));

			LevyKind levyKind = dbimportDao.FindLevyKind("", rsHead
					.getString("hc16"));

			head.setLevyKind(levyKind);
			head.setAgreementNo(rsHead.getString("hc18"));
			head.setImpContractNo(rsHead.getString("hc19"));
			head.setExpContractNo(rsHead.getString("hc20"));
			head.setEmsNo(rsHead.getString("hc01"));
			Customs ipo1 = dbimportDao.Findbynamecustoms(rsHead
					.getString("hc02"));
			head.setIePort1(ipo1);
			Customs ipo2 = dbimportDao.Findbynamecustoms(rsHead
					.getString("hc24"));
			head.setIePort2(ipo2);
			Customs ipo3 = dbimportDao.Findbynamecustoms(rsHead
					.getString("hc25"));
			head.setIePort3(ipo3);
			Customs ipo4 = dbimportDao.Findbynamecustoms(rsHead
					.getString("hc26"));
			head.setIePort4(ipo4);
			Customs ipo5 = dbimportDao.Findbynamecustoms(rsHead
					.getString("hc27"));
			head.setIePort5(ipo5);
			head.setApprover(rsHead.getString("hc28"));
			head.setApproveDate(strToStandDate(rsHead.getString("hc29")));
			PayMode payMode = dbimportDao.FindPayMode("", rsHead
					.getString("hc37"));
			head.setPayMode(payMode);
			MachiningType machiningType = dbimportDao.FindMachiningType("",
					rsHead.getString("hc38"));
			head.setMachiningType(machiningType);
			head.setPermitNo(rsHead.getString("hc171"));
			head.setMemo(rsHead.getString("hc30"));
			Customs dec = dbimportDao.Findbynamecustoms(rsHead
					.getString("hc36"));
			head.setDeclareCustoms(dec);
			head.setImgAmount(formatStrDouble(rsHead.getString("hc21")));
			head.setExgAmount(formatStrDouble(rsHead.getString("hc22")));
			Curr curr = dbimportDao.Findbych_encurr("", rsHead
					.getString("hc221"));
			head.setCurr(curr);
			Transac tran = dbimportDao
					.FindTransac("", rsHead.getString("hc34"));
			head.setTransac(tran);
			Trade trade = dbimportDao.Findbynametrade(rsHead.getString("hc04"));
			head.setTrade(trade);
			Country country = dbimportDao.Findbynamecountry(rsHead
					.getString("hc08"));
			head.setTradeCountry(country);
			head.setEndDate(strToStandDate(rsHead.getString("hc06")));
			head.setDeferDate(strToStandDate(rsHead.getString("hc09")));
			head.setDestroyDate(strToStandDate(rsHead.getString("hc10")));
			head.setBeginDate(strToStandDate(rsHead.getString("hc05")));
			head.setWardshipRate(formatStrDouble(rsHead.getString("hc230")));
			head.setWardshipFee(formatStrDouble(rsHead.getString("hc23")));
			head.setCompany(CommonUtils.getCompany());
			head = dbimportDao.SaveContract(head);
			String ccode = rsHead.getString("hccode");
			insertContractImg(head, ccode);// 备案料件
			insertContractExg(head, ccode);// 备案成品

		}
	}

	/**
	 *  合同备案料件
	 * @param head
	 * @param ccode
	 * @throws SQLException
	 */
	public void insertContractImg(Contract head, String ccode)
			throws SQLException {
		getst();
		ResultSet rsImg = null;
		rsImg = st
				.executeQuery("select *,hcmg06*hcmg08 as JS_SL2,Hcmg10*hcmg06 as JS_zl from hg_contract_inmaterialgeneral "
						+ "where hccode='" + ccode + "' order by hcmnum");
		while (rsImg.next()) {
			ContractImg img = new ContractImg();
			img.setContract(head);
			img.setSeqNum(Integer.valueOf(rsImg.getString("HCMNum")));// 序号
			img.setCredenceNo(Integer.valueOf(rsImg.getString("HCMGNum")));// 凭证序号
			Complex complex = dbimportDao.Findbycodecomplex(rsImg
					.getString("HCMG01"));
			img.setComplex(complex);
			img.setName(rsImg.getString("HCMG02") == null ? null : rsImg
					.getString("HCMG02").trim());
			img.setSpec(rsImg.getString("HCMG03") == null ? null : rsImg
					.getString("HCMG03").trim());
			Unit unit = dbimportDao.Findbynameunit(rsImg.getString("HCMG04"));
			img.setUnit(unit);
			img.setDeclarePrice(formatStrDouble(rsImg.getString("HCMG05")));
			img.setAmount(formatStrDouble(rsImg.getString("HCMG06")));
			img.setWasteAmount(formatStrDouble(rsImg.getString("HCMG061")));
			img.setTotalPrice(formatStrDouble(rsImg.getString("HCMG07")));
			img.setLegalAmount(formatStrDouble(rsImg.getString("HCMG08")));
			// Unit legalUnit =
			// dbimportDao.Findbynameunit(rsImg.getString("HCMG09"));
			// img.setLegalUnit(legalUnit);

			// Unit secondUnit =
			// dbimportDao.Findbynameunit(rsImg.getString("HCMG091"));
			// img.setSecondUnit(secondUnit);
			img.setSecondAmount(formatStrDouble(rsImg.getString("HCMG081")));

			img.setUnitWeight(formatStrDouble(rsImg.getString("HCMG10")));
			// img.setMaterialType(rsImg.getString("HCMG11"));
			Country country = dbimportDao.Findbynamecountry(rsImg
					.getString("HCMG12"));
			img.setCountry(country);
			img.setNote(rsImg.getString("HCMG13"));
			LevyMode levyMode = dbimportDao.FindLevyMode("", rsImg
					.getString("HCMG24"));
			img.setLevyMode(levyMode);
			img.setCompany(CommonUtils.getCompany());
			// img.setMaterialType(getMaterielType(rsImg.getString("HCMG11")));
			img.setLegalTotalAmount(formatStrDouble(rsImg.getString("HCMG10")));
			img.setTotalWeight(formatStrDouble(rsImg.getString("JS_zl")));
			dbimportDao.SaveObject(img);
		}
	}

	/**
	 * 合同备案成品
	 * @param head
	 * @param ccode
	 * @throws SQLException
	 */
	public void insertContractExg(Contract head, String ccode)
			throws SQLException {
		getst();
		ResultSet rsExg = null;
		ResultSet rsBom = null;
		rsExg = st
				.executeQuery("select *,cop05*cop10 as fdsl from hg_contract_outProduct "
						+ "where hccode='" + ccode + "' order by hccode,hcpnum");
		while (rsExg.next()) {
			ContractExg exg = new ContractExg();
			exg.setContract(head);
			exg.setCredenceNo(Integer.valueOf(rsExg.getString("OPCode")));// 凭证序号
			exg.setSeqNum(Integer.valueOf(rsExg.getString("HCPNum")));// 产品序号
			Complex complex = dbimportDao.Findbycodecomplex(rsExg
					.getString("COP01"));
			exg.setComplex(complex);
			exg.setName(rsExg.getString("COP02") == null ? null : rsExg
					.getString("COP02").trim());
			exg.setSpec(rsExg.getString("COP03") == null ? null : rsExg
					.getString("COP03").trim());
			exg.setUnitPrice(formatStrDouble(rsExg.getString("COP07")));// 单价
			exg.setTotalPrice(formatStrDouble(rsExg.getString("COP09")));// 总金额
			Unit unit = dbimportDao.Findbynameunit(rsExg.getString("COP06"));
			exg.setUnit(unit);
			exg.setDeclarePrice(formatStrDouble(rsExg.getString("COP07")));
			exg.setExportAmount(formatStrDouble(rsExg.getString("COP05")));
			exg.setMaterialFee(formatStrDouble(rsExg.getString("COP14")));
			exg.setLegalAmount(formatStrDouble(rsExg.getString("fdsl")));
			// Unit legalUnit =
			// dbimportDao.Findbynameunit(rsExg.getString("COP11"));
			// exg.setLegalUnit(legalUnit);
			//			
			// Unit secondUnit =
			// dbimportDao.Findbynameunit(rsExg.getString("COP111"));
			// exg.setSecondUnit(secondUnit);
			exg.setSecondAmount(formatStrDouble(rsExg.getString("COP101")));

			exg.setUnitGrossWeight(formatStrDouble(rsExg.getString("COP122")));
			exg.setUnitNetWeight(formatStrDouble(rsExg.getString("COP121")));
			exg.setProcessUnitPrice(formatStrDouble(rsExg.getString("COP150")));
			exg.setProcessTotalPrice(formatStrDouble(rsExg.getString("COP15")));
			Country country = dbimportDao.Findbynamecountry(rsExg
					.getString("COP13"));
			exg.setCountry(country);
			exg.setNote(rsExg.getString("COP04"));
			LevyMode levyMode = dbimportDao.FindLevyMode("", rsExg
					.getString("COP25"));
			exg.setLevyMode(levyMode);
			exg.setCompany(CommonUtils.getCompany());
			exg = dbimportDao.SaveContractExg(exg);
			String cnum = rsExg.getString("HCPNum");
			getst();
			rsBom = st
					.executeQuery("select c.cop05,a.hcmno,a.hcpnum,a.hcmnum,a.hcmgnum,a.hcm07,"
							+ "a.hcm04,a.hcm05,a.hcm06,"
							+ "b.hcmg01,b.hcmg02,b.hcmg04,b.hcmg05,b.hcmg10,b.hcmg11,"
							+ "b.hcmg03,b.hcmg06,b.hcmg08,b.hcmg09,b.hcmg12,a.hcm06*c.cop05 as sl,"
							+ "a.hcm06*c.cop05*b.hcmg05 as je,a.hcm06*c.cop05*b.hcmg08 as desl,"
							+ "b.HCMGNum from hg_contract_inmaterial a,hg_contract_inmaterialgeneral b,"
							+ "hg_contract_outproduct c where a.hccode='"
							+ ccode
							+ "' and a.hcpnum='"
							+ cnum
							+ "' and b.hccode=a.hccode and"
							+ " b.hcmnum=a.hcmnum and c.hccode=a.hccode and c.hcpnum=a.hcpnum order by a.hccode,a.hcmno");
			while (rsBom.next()) {
				ContractBom bom = new ContractBom();
				bom.setSeqNum(Integer.valueOf(rsBom.getString("HCMNum")));// 料件序号
				bom.setName(rsBom.getString("hcmg02") == null ? null : rsBom
						.getString("hcmg02").trim());
				Complex com = dbimportDao.Findbycodecomplex(rsBom
						.getString("HCMG01"));
				bom.setComplex(com);
				bom.setSpec(rsBom.getString("hcmg03") == null ? null : rsBom
						.getString("hcmg03").trim());
				Unit unitb = dbimportDao.Findbynameunit(rsBom
						.getString("HCMG04"));
				bom.setUnit(unitb);// 单位
				bom.setDeclarePrice(formatStrDouble(rsBom.getString("HCMG05")));
				bom.setContractExg(exg);
				bom.setCompany(CommonUtils.getCompany());
				Country countryb = dbimportDao.Findbynamecountry(rsBom
						.getString("HCMG12"));
				bom.setCountry(countryb);// 原产国
				// Unit legalUnitb =
				// dbimportDao.Findbynameunit(rsBom.getString("HCMG09"));
				// bom.setLegalUnit(legalUnitb);//法定单位
				bom.setLegalAmount(formatStrDouble(rsBom.getString("desl")));// 法定数量
				bom.setContractImgSeqNum(Integer.valueOf(rsBom
						.getString("HCMNum")));
				// bom.setMaterialType(getMaterielType(rsBom.getString("HCMG11")));//主辅料
				bom.setAmount(formatStrDouble(rsBom.getString("sl")));// 数量
				bom.setUnitWaste(formatStrDouble(rsBom.getString("HCM04")));
				bom.setWaste(formatStrDouble(rsBom.getString("HCM05")) * 100.0);
				bom.setUnitWeight(formatStrDouble(rsBom.getString("HCM07")));
				bom.setUnitDosage(formatStrDouble(rsBom.getString("HCM06")));
				dbimportDao.SaveObject(bom);
			}
		}
	}

	/**
	 * 获取料件类型
	 * @param name
	 * @return
	 */
	private String getMaterielType(String name) {
		if (name == null) {
			return null;
		}
		if (name.trim().equals("主料")) {
			return MaterielType.MATERIEL;
		} else if (name.trim().equals("辅料")) {
			return MaterielType.ASSISTANT_MATERIAL;
		}
		return null;
	}

	/**
	 * 插入进口报关单
	 * @param emsNo
	 * @throws SQLException
	 */
	public void insertJKBGHead(String emsNo) throws SQLException {
		getst();
		ResultSet rsHead = null;
		ResultSet rsImg = null;
		String sql = "";
		if (emsNo != null && !emsNo.equals("")) {
			sql = "select * from hg_import_dd  where idd02 = '" + emsNo
					+ "' order by idd02,importnumber";
		} else {
			sql = "select * from hg_import_dd  order by idd02,importnumber";
		}
		rsHead = st.executeQuery(sql);
		while (rsHead.next()) {
			BcsCustomsDeclaration head = new BcsCustomsDeclaration();
			head.setImpExpFlag(ImpExpFlag.IMPORT);
			head.setImpExpType(getType(rsHead.getString("ImportType")));
			head.setSerialNumber(Integer.valueOf(rsHead
					.getString("ImportNumber")));
			head.setEmsHeadH2k(rsHead.getString("IDD02"));
			head.setTradeCode(dbimportDao.FindBriefCodeByName(rsHead
					.getString("IDD05")));
			head.setTradeName(rsHead.getString("IDD05"));
			head.setPreCustomsDeclarationCode(organizeStr(rsHead
					.getString("idd31")));// 预录入号
			head.setUnificationCode(rsHead.getString("idd66"));// 统一编号
			// head.setTempPreCode();
			if (rsHead.getString("ifok").equals("Y"))
				head.setEffective(new Boolean(true));// 是否生效
			else
				head.setEffective(new Boolean(false));// 是否生效
			head.setCancel(new Boolean(false));// 是否作废
			// 是否转关确认
			// 是否申报
			Customs tempCustoms = new Customs();
			tempCustoms = dbimportDao.Findbynamecustoms(rsHead
					.getString("idd01"));
			head.setCustoms(tempCustoms); // 进/出口岸
			head.setCustomsDeclarationCode(rsHead.getString("idd32"));// 报关单号
			head.setCustomsDeclarationCodeinHouse(rsHead.getString("idd67"));// 入库报关单号
			head.setImpExpDate(rsHead.getDate("idd03"));// 进口日期
			head.setDeclarationDate(rsHead.getDate("idd04"));// 出口日期
			head.setMachCode(dbimportDao.FindBriefCodeByName(rsHead
					.getString("idd09"))); // 收货单位代码
			head.setMachName(rsHead.getString("idd09")); // 收货单位名称

			Transf tempTransf = new Transf();
			tempTransf = dbimportDao
					.Findbynametransf(rsHead.getString("idd06"));
			head.setTransferMode(tempTransf);// 运输方式
			head.setConveyance(rsHead.getString("idd07"));// 运输工具
			head.setBillOfLading(rsHead.getString("idd08"));// 提运单号
			Trade tempTrade = new Trade();
			tempTrade = dbimportDao.Findbynametrade(rsHead.getString("idd10"));
			head.setTradeMode(tempTrade);// 贸易方式
			LevyKind tempLevyKind = new LevyKind();
			tempLevyKind = dbimportDao.FindLevyKind("", rsHead
					.getString("idd11"));
			head.setLevyKind(tempLevyKind);// 征免性质
			if (GenericValidator.isDouble(rsHead.getString("idd12"))) {
				head.setPerTax(rsHead.getDouble("idd12"));// 征税比例
			}
			head.setLicense(rsHead.getString("idd13"));// 许可证编号
			Country tempCountry = new Country();
			tempCountry = dbimportDao.Findbynamecountry(rsHead
					.getString("idd14"));
			head.setCountryOfLoadingOrUnloading(tempCountry);// 起运国
			District tempDistrict = new District();
			tempDistrict = dbimportDao.FindDistrict("", rsHead
					.getString("idd16"));
			head.setDomesticDestinationOrSource(tempDistrict);// 境内目的地
			PortLin tempPortLin = new PortLin();
			tempPortLin = dbimportDao
					.FindPortLin("", rsHead.getString("idd15"));
			head.setPortOfLoadingOrUnloading(tempPortLin);// 装货港口
			head.setAuthorizeFile(rsHead.getString("idd17"));// 批准文号(外汇核销单号)
			head.setContract(rsHead.getString("idd22"));// 合同协议号
			Transac tempTransac = new Transac();// 成交方式
			tempTransac = dbimportDao
					.FindTransac("", rsHead.getString("idd18"));
			head.setTransac(tempTransac);
			if ((rsHead.getString("idd191") != null)
					&& (!rsHead.getString("idd191").trim().equals("")))
				head.setFreightageType(Integer.valueOf(rsHead
						.getString("idd191")));// 运费类型
			if ((rsHead.getString("idd19") != null)
					&& (!rsHead.getString("idd19").trim().equals("")))
				head.setFreightage(rsHead.getDouble("idd19"));// 运费

			if ((rsHead.getString("idd201") != null)
					&& (!rsHead.getString("idd201").trim().equals("")))
				head.setInsuranceType(Integer.valueOf(rsHead
						.getString("idd201")));// 保险费类型
			if ((rsHead.getString("idd20") != null)
					&& (!rsHead.getString("idd20").trim().equals("")))
				head.setInsurance(rsHead.getDouble("idd20"));// 保险费

			if ((rsHead.getString("idd211") != null)
					&& (!rsHead.getString("idd211").trim().equals("")))
				head.setIncidentalExpensesType(Integer.valueOf(rsHead
						.getString("idd211")));// 杂费类型
			if ((rsHead.getString("idd21") != null)
					&& (!rsHead.getString("idd21").trim().equals("")))
				head.setIncidentalExpenses(rsHead.getDouble("idd21"));// 杂费

			if (GenericValidator.isDouble(rsHead.getString("idd23")))
				head.setCommodityNum(Convfloattoint(rsHead.getString("idd23")));// 件数
			Wrap tempWrap = new Wrap();
			tempWrap = dbimportDao.FindWrap("", rsHead.getString("idd24"));
			head.setWrapType(tempWrap); // 包装种类
			if ((rsHead.getString("idd25") != null)
					&& (!rsHead.getString("idd25").trim().equals("")))
				head.setGrossWeight(rsHead.getDouble("idd25"));// 毛重
			if ((rsHead.getString("idd26") != null)
					&& (!rsHead.getString("idd26").trim().equals("")))
				head.setNetWeight(rsHead.getDouble("idd26"));// 净重

			head.setContainerNum(rsHead.getString("idd27"));// 集装箱号
			head.setAttachedBill(rsHead.getString("idd37"));// 随附单据
			Uses tempUses = new Uses();
			tempUses = dbimportDao.FindUses("", rsHead.getString("idd29"));
			head.setUses(tempUses);// 用途
			Company tempBrief = new Company();
			tempBrief = dbimportDao
					.Findbynamecompany(rsHead.getString("idd34"));
			head.setDeclarationCompany(tempBrief);// 申报单位

			Curr tempCurr = new Curr();
			tempCurr = dbimportDao.Findbych_encurr("", rsHead
					.getString("idd33"));
			head.setCurrency(tempCurr);// 币别
			if ((rsHead.getString("idd65") != null)// 汇率
					&& (!rsHead.getString("idd65").trim().equals("")))
				head.setExchangeRate(rsHead.getDouble("idd65"));
			AclUser tempAclUser = new AclUser();
			tempAclUser = dbimportDao.FindbyloginnameAclUser(rsHead
					.getString("idd35"));
			head.setCreater(tempAclUser);// 录入员
			ScmCoc tempScmCoc = new ScmCoc();// 客户
			tempScmCoc = dbimportDao.Findbynamescmcoc(rsHead
					.getString("idd091"));
			head.setCustomer(tempScmCoc);

			tempCustoms = dbimportDao.Findbynamecustoms(rsHead
					.getString("idd39"));
			head.setDeclarationCustoms(tempCustoms);// 报送海关
			PreDock tempPreDock = new PreDock();// 装卸码头
			tempPreDock = dbimportDao
					.FindPreDock("", rsHead.getString("idd41"));
			head.setPredock(tempPreDock);
			head.setMemos(rsHead.getString("idd30"));// 备注
			head.setCertificateCode(rsHead.getString("idd70")); // 备注二
			head.setCustomser(rsHead.getString("IDD43"));// 报关员
			head.setTelephone(rsHead.getString("IDD44"));// 报关员电话
			head.setBarCode(rsHead.getString("idd42"));// 条形码
			/*
			 * Brief brief = new Brief(); brief =
			 * dbimportDao.Findbynamebrief(rsHead.getString("idd29"));
			 * head.setManufacturer(brief);
			 */// 生产厂商
			head.setAllContainerNum(rsHead.getString("idd40"));// 所有集装箱号
			if (rsHead.getString("idd69") != null
					&& rsHead.getString("idd69").equals("0")) { // 有无纸报关
				head.setIsNoBumf("O");
			} else {
				head.setIsNoBumf("W");
			}
			// 结汇方式
			head.setOverseasConveyanceCode(rsHead.getString("IDD49"));// 境外运输工具编号
			head.setOverseasConveyanceName(rsHead.getString("IDD50"));// 境外运输工具名字
			head.setOverseasConveyanceFlights(rsHead.getString("IDD51"));// 境外运输工具航次
			head.setOverseasConveyanceBillOfLading(rsHead.getString("IDD52"));// 境外运输工具提单号
			Transf transf = new Transf();
			transf = dbimportDao.Findbynametransf(rsHead.getString("IDD56"));// 境内运输方式
			head.setDomesticTransferMode(transf);

			head.setDomesticConveyanceCode(rsHead.getString("IDD53"));// 境内运输工具编号
			head.setDomesticConveyanceName(rsHead.getString("IDD54"));// 境内运输工具名字
			head.setDomesticConveyanceFlights(rsHead.getString("IDD55"));// 境内运输工具航次
			head.setCompany(CommonUtils.getCompany());
			if (rsHead.getString("idd48") != null
					&& rsHead.getString("idd48").equals("Y")) { // 是否检查通过
				head.setIsCheck(new Boolean(true));
			} else {
				head.setIsCheck(new Boolean(false));
			}
			head = dbimportDao.SaveCustomsDeclaration(head);
			String[] strs = null;
			String s = rsHead.getString("idd40");
			if (s != null && !s.trim().equals("")) {
				strs = s.split(String.valueOf((char) 44));
				for (int i = 0; i < strs.length; i++) {
					BcsCustomsDeclarationContainer declara = new BcsCustomsDeclarationContainer();
					try {
						String code = "";
						String code1 = "";
						String code2 = "";
						try {
							code = strs[i].trim().substring(0, 11);
						} catch (Exception e) {
						}
						declara.setContainerCode(code);
						try {
							code1 = strs[i].trim().substring(12, 16);
						} catch (Exception e) {
						}
						SrtJzx srt = new SrtJzx();
						srt = dbimportDao.getSrtJzx(code1);
						declara.setSrtJzx(srt);
						try {
							code2 = strs[i].trim().substring(16,
									strs[i].trim().length());
						} catch (Exception e) {
						}
						SrtTj tj = new SrtTj();
						tj = dbimportDao.getSrtTj(code2);
						declara.setSrttj(tj);
						declara.setCompany(CommonUtils.getCompany());
						declara.setBaseCustomsDeclaration(head);
						dbimportDao.SaveObject(declara);
					} catch (Exception e) {
						System.out.println("错误：" + e);
					}
				}
			}
			getst();
			rsImg = st.executeQuery("select * from hg_import_dddetails where"
					+ " customcode = '" + rsHead.getString("idd02")
					+ "' and importnumber = '"
					+ rsHead.getString("importnumber") + "'");
			while (rsImg.next()) {
				try {
					LevyMode tempLevyMode1 = new LevyMode();
					Uses tempUses1 = new Uses();
					BcsCustomsDeclarationCommInfo customsDeclarationCommInfo = new BcsCustomsDeclarationCommInfo();// 进口报关单明细表
					customsDeclarationCommInfo.setBaseCustomsDeclaration(head);
					tempUses1 = dbimportDao.FindUses("", rsImg
							.getString("imd15"));// 用途
					customsDeclarationCommInfo.setUses(tempUses1);
					Complex tempComplex = new Complex();
					tempComplex = dbimportDao.Findbycodecomplex(rsImg
							.getString("imd01"));// 商品信息
					customsDeclarationCommInfo.setCommSerialNo(Integer
							.valueOf(rsImg.getString("imd13")));// 序号
					customsDeclarationCommInfo.setSerialNumber(Integer
							.valueOf(rsImg.getString("imd00")));// 商品流水号
					customsDeclarationCommInfo.setComplex(tempComplex);
					customsDeclarationCommInfo.setCommName(rsImg
							.getString("imd02") == null ? null : rsImg
							.getString("imd02").trim());// 商品名称
					customsDeclarationCommInfo.setCommSpec(rsImg
							.getString("imd03") == null ? null : rsImg
							.getString("imd03").trim());// 商品规格

					Unit unit = new Unit();// 单位
					unit = dbimportDao.Findbynameunit(rsImg.getString("imd05"));
					customsDeclarationCommInfo.setUnit(unit);
					Unit unit1 = new Unit();// 第一法定单位
					unit1 = dbimportDao
							.Findbynameunit(rsImg.getString("imd07"));
					customsDeclarationCommInfo.setLegalUnit(unit1);
					Unit unit2 = new Unit();// 第二法定单位
					unit2 = dbimportDao.Findbynameunit(rsImg
							.getString("imd071"));
					customsDeclarationCommInfo.setSecondLegalUnit(unit2);

					customsDeclarationCommInfo.setMaterielNo(rsImg
							.getString("imd16")); // 货号
					customsDeclarationCommInfo.setVersion(rsImg
							.getString("imd17"));// 版本

					if (GenericValidator.isDouble(rsImg.getString("imd04")))// 数量
						customsDeclarationCommInfo.setCommAmount(rsImg
								.getDouble("imd04"));
					double comamount = StrTodou(rsImg.getString("imd04"));
					if (GenericValidator.isDouble(rsImg.getString("imd06"))) // 第一法定比例因子
						customsDeclarationCommInfo.setFirstAmount(Double
								.valueOf(comamount
										* StrTodou(rsImg.getString("imd06"))));
					if (GenericValidator.isDouble(rsImg.getString("imd061"))) // 第二法定比例因子
						customsDeclarationCommInfo.setSecondAmount(Double
								.valueOf(comamount
										* StrTodou(rsImg.getString("imd061"))));
					if (GenericValidator.isDouble(rsImg.getString("imd20")))// 申报单价
						customsDeclarationCommInfo.setCommUnitPrice(Double
								.valueOf(rsImg.getString("imd20")));
					if (GenericValidator.isDouble(rsImg.getString("imd21")))// 总价格
						customsDeclarationCommInfo.setCommTotalPrice(Double
								.valueOf(rsImg.getString("imd21")));

					Country country = new Country();
					country = dbimportDao.Findbynamecountry(rsImg
							.getString("imd09"));// 最终目的产国
					customsDeclarationCommInfo.setCountry(country);

					tempLevyMode1 = dbimportDao.FindLevyMode("", rsImg
							.getString("imd14"));// 减免方式
					customsDeclarationCommInfo.setLevyMode(tempLevyMode1);

					if (GenericValidator.isDouble(rsImg.getString("imd22")))// 净重
						customsDeclarationCommInfo.setNetWeight(Double
								.valueOf(rsImg.getString("imd22")));

					if (GenericValidator.isDouble(rsImg.getString("imd23")))// 毛重
						customsDeclarationCommInfo.setGrossWeight(rsImg
								.getDouble("imd23"));

					if (GenericValidator.isInt(rsImg.getString("imd24")))
						customsDeclarationCommInfo.setPieces(Integer
								.valueOf(rsImg.getString("imd24")));// 件(箱)数
					customsDeclarationCommInfo.setCompany(CommonUtils
							.getCompany());
					dbimportDao.SaveObject(customsDeclarationCommInfo);
				} catch (Exception e) {
					System.out.println("Error_detail:" + rsImg.getRow() + e);
					continue;
				}
			}
		}
	}

	/**
	 * 出口报关单
	 * @param emsNo
	 * @throws SQLException
	 */
	public void insertCKBGHead(String emsNo) throws SQLException {
		getst();
		ResultSet rsHead = null;
		ResultSet rsImg = null;
		String sql = "";
		if (emsNo != null && !emsNo.equals("")) {
			sql = "select * from hg_export_dd  where edd02 = '" + emsNo
					+ "' order by edd02,exportnumber";
		} else {
			sql = "select * from hg_export_dd  order by edd02,exportnumber";
		}
		rsHead = st.executeQuery(sql);
		while (rsHead.next()) {
			BcsCustomsDeclaration head = new BcsCustomsDeclaration();
			head.setImpExpFlag(ImpExpFlag.EXPORT);// 出口标志
			head.setImpExpType(getType(rsHead.getString("exporttype")));// 出口类型
			head.setSerialNumber(Integer.valueOf(rsHead
					.getString("exportnumber")));// 大单流水号
			head.setEmsHeadH2k(rsHead.getString("EDD02"));// 电子帐册号码(电子帐册手册编号)
			head.setTradeCode(dbimportDao// 经营单位代码
					.FindBriefCodeByName(rsHead.getString("eDD05")));
			head.setTradeName(rsHead.getString("eDD05"));// 经营单位名称
			head.setPreCustomsDeclarationCode(organizeStr(rsHead
					.getString("edd31")));// 预录入号
			head.setUnificationCode(rsHead.getString("edd66"));// 统一编号
			// head.setTempPreCode();
			if (rsHead.getString("ifok") != null
					&& rsHead.getString("ifok").equals("Y"))
				head.setEffective(new Boolean(true));// 是否生效
			else
				head.setEffective(new Boolean(false));// 是否生效
			head.setCancel(new Boolean(false));// 是否作废
			Customs tempCustoms = new Customs();
			tempCustoms = dbimportDao.Findbynamecustoms(rsHead
					.getString("edd01"));
			head.setCustoms(tempCustoms); // 进/出口岸
			head.setCustomsDeclarationCode(rsHead.getString("edd32"));// 报关单号
			head.setCustomsDeclarationCodeinHouse(rsHead.getString("edd67"));// 入库报关单号
			head.setImpExpDate(rsHead.getDate("edd03"));// 出口日期
			head.setDeclarationDate(rsHead.getDate("edd04"));// 申报日期
			head.setMachCode(dbimportDao.FindBriefCodeByName(rsHead
					.getString("edd09"))); // 收货单位代码
			head.setMachName(rsHead.getString("edd09")); // 收货单位名称

			Transf tempTransf = new Transf();
			tempTransf = dbimportDao
					.Findbynametransf(rsHead.getString("edd06"));
			head.setTransferMode(tempTransf);// 运输方式
			head.setConveyance(rsHead.getString("edd07"));// 运输工具
			head.setBillOfLading(rsHead.getString("edd08"));// 提运单号
			Trade tempTrade = new Trade();
			tempTrade = dbimportDao.Findbynametrade(rsHead.getString("edd10"));
			head.setTradeMode(tempTrade);// 贸易方式
			LevyKind tempLevyKind = new LevyKind();
			tempLevyKind = dbimportDao.FindLevyKind("", rsHead
					.getString("edd11"));
			head.setLevyKind(tempLevyKind);// 征免性质
			if (GenericValidator.isDouble(rsHead.getString("edd12"))) {
				head.setPerTax(rsHead.getDouble("edd12"));// 征税比例
			}
			head.setLicense(rsHead.getString("edd13"));// 许可证编号
			Country tempCountry = new Country();// 运抵国
			tempCountry = dbimportDao.Findbynamecountry(rsHead
					.getString("edd14"));
			head.setCountryOfLoadingOrUnloading(tempCountry);
			District tempDistrict = new District();// 境内货源地
			tempDistrict = dbimportDao.FindDistrict("", rsHead
					.getString("edd16"));
			head.setDomesticDestinationOrSource(tempDistrict);
			PortLin tempPortLin = new PortLin();// 指运港口
			tempPortLin = dbimportDao
					.FindPortLin("", rsHead.getString("edd15"));
			head.setPortOfLoadingOrUnloading(tempPortLin);
			head.setAuthorizeFile(rsHead.getString("edd17"));// 合同协议号（电子备案进出口合同协议号）
			head.setContract(rsHead.getString("edd22"));// 合同协议号
			Transac tempTransac = new Transac();// 成交方式
			tempTransac = dbimportDao
					.FindTransac("", rsHead.getString("edd18"));
			head.setTransac(tempTransac);
			if ((rsHead.getString("edd191") != null)// 运费
					&& (!rsHead.getString("edd191").trim().equals("")))
				head.setFreightageType(Integer.valueOf(rsHead
						.getString("edd191")));// 运费类型
			if ((rsHead.getString("edd19") != null)
					&& (!rsHead.getString("edd19").trim().equals("")))
				head.setFreightage(rsHead.getDouble("edd19"));

			if ((rsHead.getString("edd201") != null)
					&& (!rsHead.getString("edd201").trim().equals("")))
				head.setInsuranceType(Integer.valueOf(rsHead
						.getString("edd201")));// 保险费类型
			if ((rsHead.getString("edd20") != null)
					&& (!rsHead.getString("edd20").trim().equals("")))
				head.setInsurance(rsHead.getDouble("edd20"));// 保险费

			if ((rsHead.getString("edd211") != null)
					&& (!rsHead.getString("edd211").trim().equals("")))
				head.setIncidentalExpensesType(Integer.valueOf(rsHead
						.getString("edd211")));// 杂费类型
			if ((rsHead.getString("edd21") != null)
					&& (!rsHead.getString("edd21").trim().equals("")))
				head.setIncidentalExpenses(rsHead.getDouble("edd21"));// 杂费

			if (GenericValidator.isDouble(rsHead.getString("edd23")))
				head.setCommodityNum(Convfloattoint(rsHead.getString("edd23")));// 件数
			Wrap tempWrap = new Wrap();
			tempWrap = dbimportDao.FindWrap("", rsHead.getString("edd24"));
			head.setWrapType(tempWrap); // 包装种类
			if ((rsHead.getString("edd25") != null)
					&& (!rsHead.getString("edd25").trim().equals("")))
				head.setGrossWeight(rsHead.getDouble("edd25"));// 毛重
			if ((rsHead.getString("edd26") != null)
					&& (!rsHead.getString("edd26").trim().equals("")))
				head.setNetWeight(rsHead.getDouble("edd26"));// 净重

			head.setContainerNum(rsHead.getString("edd27"));// 集装箱号
			head.setAttachedBill(rsHead.getString("edd28"));// 依附单据
			/*
			 * Uses tempUses = new Uses();// 用途 tempUses =
			 * dbimportDao.FindUses("", rsHead.getString("edd29"));
			 * head.setUses(tempUses);
			 */
			Company tempBrief = new Company();
			tempBrief = dbimportDao
					.Findbynamecompany(rsHead.getString("edd34"));
			head.setDeclarationCompany(tempBrief);// 申报单位

			Curr tempCurr = new Curr();
			tempCurr = dbimportDao.Findbych_encurr("", rsHead
					.getString("edd33"));
			head.setCurrency(tempCurr);// 币别
			if ((rsHead.getString("edd65") != null)// 汇率
					&& (!rsHead.getString("edd65").trim().equals("")))
				head.setExchangeRate(rsHead.getDouble("edd65"));

			AclUser tempAclUser = new AclUser();
			tempAclUser = dbimportDao.FindbyloginnameAclUser(rsHead
					.getString("edd35"));// 录入员
			head.setCreater(tempAclUser);
			ScmCoc tempScmCoc = new ScmCoc();// 客户
			tempScmCoc = dbimportDao.Findbynamescmcoc(rsHead
					.getString("edd091"));
			head.setCustomer(tempScmCoc);

			tempCustoms = dbimportDao.Findbynamecustoms(rsHead
					.getString("edd39"));
			head.setDeclarationCustoms(tempCustoms);// 报送海关
			PreDock tempPreDock = new PreDock();// 码头
			tempPreDock = dbimportDao
					.FindPreDock("", rsHead.getString("edd41"));
			head.setPredock(tempPreDock);
			head.setMemos(rsHead.getString("edd30"));// 备注
			head.setCertificateCode(rsHead.getString("edd70")); // 备注二
			head.setCustomser(rsHead.getString("eDD43"));// 报关员
			head.setTelephone(rsHead.getString("eDD44"));// 报关员电话
			head.setBarCode("");// 条形码
			Brief brief = new Brief();
			brief = dbimportDao.Findbynamebrief(rsHead.getString("edd29"));
			head.setManufacturer(brief);// 生产厂商
			head.setAllContainerNum(rsHead.getString("edd40"));// 所有集装箱号
			if (rsHead.getString("edd69") != null
					&& rsHead.getString("edd69").equals("0")) { // 有无纸报关
				head.setIsNoBumf("O");
			} else {
				head.setIsNoBumf("W");
			}
			head.setOverseasConveyanceCode(rsHead.getString("eDD49"));// 境外运输工具编号
			head.setOverseasConveyanceName(rsHead.getString("eDD50"));// 境外运输工具名字
			head.setOverseasConveyanceFlights(rsHead.getString("eDD51"));// 境外运输工具航次
			head.setOverseasConveyanceBillOfLading(rsHead.getString("eDD52"));// 境外运输工具提单号
			Transf transf = new Transf();
			transf = dbimportDao.Findbynametransf(rsHead.getString("eDD56"));// 境内运输方式
			head.setDomesticTransferMode(transf);

			head.setDomesticConveyanceCode(rsHead.getString("eDD53"));// 境内运输工具编号
			head.setDomesticConveyanceName(rsHead.getString("eDD54"));// 境内运输工具名字
			head.setDomesticConveyanceFlights(rsHead.getString("eDD55"));// 境内运输工具航次
			head.setCompany(CommonUtils.getCompany());
			if (rsHead.getString("edd48") != null
					&& rsHead.getString("edd48").equals("Y")) { // 是否检查通过
				head.setIsCheck(new Boolean(true));
			} else {
				head.setIsCheck(new Boolean(false));
			}
			BalanceMode obj = new BalanceMode();
			obj = dbimportDao.FindbyBalanceMode("", rsHead.getString("edd12"));
			head.setBalanceMode(obj);
			head = dbimportDao.SaveCustomsDeclaration(head);

			String[] strs = null;
			String s = rsHead.getString("edd40");
			if (s != null && !s.trim().equals("")) {
				strs = s.split(String.valueOf((char) 44));
				for (int i = 0; i < strs.length; i++) {
					BcsCustomsDeclarationContainer declara = new BcsCustomsDeclarationContainer();
					try {
						String code = "";
						String code1 = "";
						String code2 = "";
						try {
							code = strs[i].trim().substring(0, 11);
						} catch (Exception e) {
						}
						declara.setContainerCode(code);
						try {
							code1 = strs[i].trim().substring(12, 16);
						} catch (Exception e) {
						}
						SrtJzx srt = new SrtJzx();
						srt = dbimportDao.getSrtJzx(code1);
						declara.setSrtJzx(srt);
						try {
							code2 = strs[i].trim().substring(16,
									strs[i].trim().length());
						} catch (Exception e) {
						}
						SrtTj tj = new SrtTj();
						tj = dbimportDao.getSrtTj(code2);
						declara.setSrttj(tj);
						declara.setCompany(CommonUtils.getCompany());
						declara.setBaseCustomsDeclaration(head);
						dbimportDao.SaveObject(declara);
					} catch (Exception e) {
						System.out.println("错误：" + e);
					}
				}
			}
			getst();
			rsImg = st.executeQuery("select * from hg_export_dddetails where"
					+ " customcode = '" + rsHead.getString("edd02")
					+ "' and exportnumber = '"
					+ rsHead.getString("exportnumber") + "'");
			while (rsImg.next()) {
				try {
					LevyMode tempLevyMode1 = new LevyMode();
					Uses tempUses1 = new Uses();
					BcsCustomsDeclarationCommInfo customsDeclarationCommInfo = new BcsCustomsDeclarationCommInfo();// 进口报关单明细表
					customsDeclarationCommInfo.setBaseCustomsDeclaration(head);
					tempUses1 = dbimportDao.FindUses("", rsImg
							.getString("emd15"));// 用途
					customsDeclarationCommInfo.setUses(tempUses1);
					Complex tempComplex = new Complex();
					tempComplex = dbimportDao.Findbycodecomplex(rsImg
							.getString("emd01"));// 商品信息
					customsDeclarationCommInfo.setCommSerialNo(Integer
							.valueOf(rsImg.getString("emd13")));// 序号
					customsDeclarationCommInfo.setSerialNumber(Integer
							.valueOf(rsImg.getString("emd00")));// 商品流水号
					customsDeclarationCommInfo.setComplex(tempComplex);
					customsDeclarationCommInfo.setCommName(rsImg
							.getString("emd02") == null ? null : rsImg
							.getString("emd02").trim());// 商品名称
					customsDeclarationCommInfo.setCommSpec(rsImg
							.getString("emd03") == null ? null : rsImg
							.getString("emd03").trim());// 商品规格

					Unit unit = new Unit();// 单位
					unit = dbimportDao.Findbynameunit(rsImg.getString("emd05"));
					customsDeclarationCommInfo.setUnit(unit);
					Unit unit1 = new Unit();// 第一法定单位
					unit1 = dbimportDao
							.Findbynameunit(rsImg.getString("emd07"));
					customsDeclarationCommInfo.setLegalUnit(unit1);
					Unit unit2 = new Unit();// 第二法定单位
					unit2 = dbimportDao.Findbynameunit(rsImg
							.getString("emd071"));
					customsDeclarationCommInfo.setSecondLegalUnit(unit2);

					customsDeclarationCommInfo.setMaterielNo(rsImg
							.getString("emd16")); // 货号
					customsDeclarationCommInfo.setVersion(rsImg
							.getString("emd17"));// 版本

					if (GenericValidator.isDouble(rsImg.getString("emd04")))// 数量
						customsDeclarationCommInfo.setCommAmount(rsImg
								.getDouble("emd04"));
					double comamount = StrTodou(rsImg.getString("emd04"));
					if (GenericValidator.isDouble(rsImg.getString("emd06"))) // 第一法定比例因子
						customsDeclarationCommInfo.setFirstAmount(Double
								.valueOf(comamount
										* StrTodou(rsImg.getString("emd06"))));
					if (GenericValidator.isDouble(rsImg.getString("emd061"))) // 第二法定比例因子
						customsDeclarationCommInfo.setSecondAmount(Double
								.valueOf(comamount
										* StrTodou(rsImg.getString("emd061"))));
					if (GenericValidator.isDouble(rsImg.getString("emd20")))// 申报单价
						customsDeclarationCommInfo.setCommUnitPrice(Double
								.valueOf(rsImg.getString("emd20")));
					if (GenericValidator.isDouble(rsImg.getString("emd21")))// 总价格
						customsDeclarationCommInfo.setCommTotalPrice(Double
								.valueOf(rsImg.getString("emd21")));

					Country country = new Country();
					country = dbimportDao.Findbynamecountry(rsImg
							.getString("emd09"));// 最终目的产国
					customsDeclarationCommInfo.setCountry(country);

					tempLevyMode1 = dbimportDao.FindLevyMode("", rsImg
							.getString("emd14"));// 减免方式
					customsDeclarationCommInfo.setLevyMode(tempLevyMode1);

					if (GenericValidator.isDouble(rsImg.getString("emd22")))// 净重
						customsDeclarationCommInfo.setNetWeight(Double
								.valueOf(rsImg.getString("emd22")));

					if (GenericValidator.isDouble(rsImg.getString("emd23")))// 毛重
						customsDeclarationCommInfo.setGrossWeight(rsImg
								.getDouble("emd23"));

					if (GenericValidator.isInt(rsImg.getString("emd24")))
						customsDeclarationCommInfo.setPieces(Integer
								.valueOf(rsImg.getString("emd24")));// 件数
					customsDeclarationCommInfo.setCompany(CommonUtils
							.getCompany());

					dbimportDao.SaveObject(customsDeclarationCommInfo);
				} catch (Exception e) {
					System.out.println("Error_detail:" + rsImg.getRow() + e);
					continue;
				}
			}
		}
	}

	/**
	 *  特殊报关单
	 * @param emsNo
	 * @throws SQLException
	 */
	public void insertTSBGHead(String emsNo) throws SQLException {
		getst();
		ResultSet rsHead = null;
		ResultSet rsImg = null;
		String sql = "";
		if (emsNo != null && !emsNo.equals("")) {
			sql = "select * from HG_Other_DD  where odd02 = '" + emsNo
					+ "' order by odd02,OtherNumber";
		} else {
			sql = "select * from HG_Other_DD  order by odd02,OtherNumber";
		}
		rsHead = st.executeQuery(sql);
		while (rsHead.next()) {
			BcsCustomsDeclaration head = new BcsCustomsDeclaration();
			head.setImpExpFlag(ImpExpFlag.SPECIAL);// 特殊标志
			head.setImpExpType(getType(rsHead.getString("OtherType")));// 出口类型
			head.setSerialNumber(Integer.valueOf(rsHead
					.getString("OtherNumber")));// 大单流水号
			head.setEmsHeadH2k(rsHead.getString("odd02"));// 电子帐册号码(电子帐册手册编号)
			head.setTradeCode(dbimportDao// 经营单位代码
					.FindBriefCodeByName(rsHead.getString("oDD05")));
			head.setTradeName(rsHead.getString("oDD05"));// 经营单位名称
			head.setPreCustomsDeclarationCode(organizeStr(rsHead
					.getString("odd31")));// 预录入号
			// head.setUnificationCode(rsHead.getString("edd66"));// 统一编号
			// head.setTempPreCode();
			if (rsHead.getString("ifok") != null
					&& rsHead.getString("ifok").equals("Y"))
				head.setEffective(new Boolean(true));// 是否生效
			else
				head.setEffective(new Boolean(false));// 是否生效
			head.setCancel(new Boolean(false));// 是否作废
			Customs tempCustoms = new Customs();
			tempCustoms = dbimportDao.Findbynamecustoms(rsHead
					.getString("odd01"));
			head.setCustoms(tempCustoms); // 进/出口岸
			head.setCustomsDeclarationCode(rsHead.getString("odd32"));// 报关单号
			// head.setCustomsDeclarationCodeinHouse(rsHead.getString("edd67"));//入库报关单号
			head.setImpExpDate(rsHead.getDate("odd03"));// 出口日期
			head.setDeclarationDate(rsHead.getDate("odd04"));// 申报日期
			head.setMachCode(dbimportDao.FindBriefCodeByName(rsHead
					.getString("odd09"))); // 收货单位代码
			head.setMachName(rsHead.getString("odd09")); // 收货单位名称

			Transf tempTransf = new Transf();
			tempTransf = dbimportDao
					.Findbynametransf(rsHead.getString("odd06"));
			head.setTransferMode(tempTransf);// 运输方式
			head.setConveyance(rsHead.getString("odd07"));// 运输工具
			head.setBillOfLading(rsHead.getString("odd08"));// 提运单号
			Trade tempTrade = new Trade();
			tempTrade = dbimportDao.Findbynametrade(rsHead.getString("odd10"));
			head.setTradeMode(tempTrade);// 贸易方式
			LevyKind tempLevyKind = new LevyKind();
			tempLevyKind = dbimportDao.FindLevyKind("", rsHead
					.getString("odd11"));
			head.setLevyKind(tempLevyKind);// 征免性质
			if (GenericValidator.isDouble(rsHead.getString("odd121"))) {
				head.setPerTax(rsHead.getDouble("odd121"));// 征税比例
			}
			head.setLicense(rsHead.getString("odd13"));// 许可证编号
			Country tempCountry = new Country();// 运抵国
			tempCountry = dbimportDao.Findbynamecountry(rsHead
					.getString("odd14"));
			head.setCountryOfLoadingOrUnloading(tempCountry);
			District tempDistrict = new District();// 境内货源地
			tempDistrict = dbimportDao.FindDistrict("", rsHead
					.getString("odd16"));
			head.setDomesticDestinationOrSource(tempDistrict);
			PortLin tempPortLin = new PortLin();// 指运港口
			tempPortLin = dbimportDao
					.FindPortLin("", rsHead.getString("odd15"));
			head.setPortOfLoadingOrUnloading(tempPortLin);
			head.setAuthorizeFile(rsHead.getString("odd17"));// 合同协议号（电子备案进出口合同协议号）
			head.setContract(rsHead.getString("odd22"));// 合同协议号
			Transac tempTransac = new Transac();// 成交方式
			tempTransac = dbimportDao
					.FindTransac("", rsHead.getString("odd18"));
			head.setTransac(tempTransac);
			if ((rsHead.getString("odd191") != null)// 运费
					&& (!rsHead.getString("odd191").trim().equals("")))
				head.setFreightageType(Integer.valueOf(rsHead
						.getString("odd191")));// 运费类型
			if ((rsHead.getString("odd19") != null)
					&& (!rsHead.getString("odd19").trim().equals("")))
				head.setFreightage(rsHead.getDouble("odd19"));

			if ((rsHead.getString("odd201") != null)
					&& (!rsHead.getString("odd201").trim().equals("")))
				head.setInsuranceType(Integer.valueOf(rsHead
						.getString("odd201")));// 保险费类型
			if ((rsHead.getString("odd20") != null)
					&& (!rsHead.getString("odd20").trim().equals("")))
				head.setInsurance(rsHead.getDouble("odd20"));// 保险费

			if ((rsHead.getString("odd211") != null)
					&& (!rsHead.getString("odd211").trim().equals("")))
				head.setIncidentalExpensesType(Integer.valueOf(rsHead
						.getString("odd211")));// 杂费类型
			if ((rsHead.getString("odd21") != null)
					&& (!rsHead.getString("odd21").trim().equals("")))
				head.setIncidentalExpenses(rsHead.getDouble("odd21"));// 杂费

			if (GenericValidator.isDouble(rsHead.getString("odd23")))
				head.setCommodityNum(Convfloattoint(rsHead.getString("odd23")));// 件数
			Wrap tempWrap = new Wrap();
			tempWrap = dbimportDao.FindWrap("", rsHead.getString("odd24"));
			head.setWrapType(tempWrap); // 包装种类
			if ((rsHead.getString("odd25") != null)
					&& (!rsHead.getString("odd25").trim().equals("")))
				head.setGrossWeight(rsHead.getDouble("odd25"));// 毛重
			if ((rsHead.getString("odd26") != null)
					&& (!rsHead.getString("odd26").trim().equals("")))
				head.setNetWeight(rsHead.getDouble("odd26"));// 净重

			head.setContainerNum(rsHead.getString("odd27"));// 集装箱号
			head.setAttachedBill(rsHead.getString("odd28"));// 依附单据
			/*
			 * Uses tempUses = new Uses();// 用途 tempUses =
			 * dbimportDao.FindUses("", rsHead.getString("edd29"));
			 * head.setUses(tempUses);
			 */
			Company tempBrief = new Company();
			tempBrief = dbimportDao
					.Findbynamecompany(rsHead.getString("odd34"));
			head.setDeclarationCompany(tempBrief);// 申报单位

			Curr tempCurr = new Curr();
			tempCurr = dbimportDao.Findbych_encurr("", rsHead
					.getString("odd33"));
			head.setCurrency(tempCurr);// 币别
			if ((rsHead.getString("odd65") != null)// 汇率
					&& (!rsHead.getString("odd65").trim().equals("")))
				head.setExchangeRate(rsHead.getDouble("odd65"));

			AclUser tempAclUser = new AclUser();
			tempAclUser = dbimportDao.FindbyloginnameAclUser(rsHead
					.getString("odd35"));// 录入员
			head.setCreater(tempAclUser);
			ScmCoc tempScmCoc = new ScmCoc();// 客户
			tempScmCoc = dbimportDao.Findbynamescmcoc(rsHead
					.getString("odd091"));
			head.setCustomer(tempScmCoc);

			tempCustoms = dbimportDao.Findbynamecustoms(rsHead
					.getString("odd39"));
			head.setDeclarationCustoms(tempCustoms);// 报送海关
			PreDock tempPreDock = new PreDock();// 码头
			tempPreDock = dbimportDao
					.FindPreDock("", rsHead.getString("odd41"));
			head.setPredock(tempPreDock);
			head.setMemos(rsHead.getString("odd30"));// 备注
			// head.setCertificateCode(rsHead.getString("edd70")); //备注二
			head.setCustomser(rsHead.getString("oDD43"));// 报关员
			head.setTelephone(rsHead.getString("oDD44"));// 报关员电话
			head.setBarCode("");// 条形码
			Brief brief = new Brief();
			brief = dbimportDao.Findbynamebrief(rsHead.getString("odd29"));
			head.setManufacturer(brief);// 生产厂商
			head.setAllContainerNum(rsHead.getString("odd40"));// 所有集装箱号
			/*
			 * if (rsHead.getString("edd69")!=null &&
			 * rsHead.getString("edd69").equals("0")) { // 有无纸报关
			 * head.setIsNoBumf("O"); } else { head.setIsNoBumf("W"); }
			 */
			head.setOverseasConveyanceCode(rsHead.getString("oDD49"));// 境外运输工具编号
			head.setOverseasConveyanceName(rsHead.getString("oDD50"));// 境外运输工具名字
			head.setOverseasConveyanceFlights(rsHead.getString("oDD51"));// 境外运输工具航次
			head.setOverseasConveyanceBillOfLading(rsHead.getString("oDD52"));// 境外运输工具提单号
			Transf transf = new Transf();
			transf = dbimportDao.Findbynametransf(rsHead.getString("oDD56"));// 境内运输方式
			head.setDomesticTransferMode(transf);

			head.setDomesticConveyanceCode(rsHead.getString("oDD53"));// 境内运输工具编号
			head.setDomesticConveyanceName(rsHead.getString("oDD54"));// 境内运输工具名字
			head.setDomesticConveyanceFlights(rsHead.getString("oDD55"));// 境内运输工具航次
			head.setCompany(CommonUtils.getCompany());
			if (rsHead.getString("odd48") != null
					&& rsHead.getString("odd48").equals("Y")) { // 是否检查通过
				head.setIsCheck(new Boolean(true));
			} else {
				head.setIsCheck(new Boolean(false));
			}
			BalanceMode obj = new BalanceMode();
			obj = dbimportDao.FindbyBalanceMode("", rsHead.getString("odd12"));
			head.setBalanceMode(obj);
			head = dbimportDao.SaveCustomsDeclaration(head);

			String[] strs = null;
			String s = rsHead.getString("odd40");
			if (s != null && !s.trim().equals("")) {
				strs = s.split(String.valueOf((char) 44));
				for (int i = 0; i < strs.length; i++) {
					BcsCustomsDeclarationContainer declara = new BcsCustomsDeclarationContainer();
					try {
						String code = "";
						String code1 = "";
						String code2 = "";
						try {
							code = strs[i].trim().substring(0, 11);
						} catch (Exception e) {
						}
						declara.setContainerCode(code);
						try {
							code1 = strs[i].trim().substring(12, 16);
						} catch (Exception e) {
						}
						SrtJzx srt = new SrtJzx();
						srt = dbimportDao.getSrtJzx(code1);
						declara.setSrtJzx(srt);
						try {
							code2 = strs[i].trim().substring(16,
									strs[i].trim().length());
						} catch (Exception e) {
						}
						SrtTj tj = new SrtTj();
						tj = dbimportDao.getSrtTj(code2);
						declara.setSrttj(tj);
						declara.setCompany(CommonUtils.getCompany());
						declara.setBaseCustomsDeclaration(head);
						dbimportDao.SaveObject(declara);
					} catch (Exception e) {
						System.out.println("错误：" + e);
					}
				}
			}
			getst();
			rsImg = st.executeQuery("select * from HG_Other_DDDetails where"
					+ " OtherNumber = '" + rsHead.getString("OtherNumber")
					+ "'");
			while (rsImg.next()) {
				try {
					LevyMode tempLevyMode1 = new LevyMode();
					Uses tempUses1 = new Uses();
					BcsCustomsDeclarationCommInfo customsDeclarationCommInfo = new BcsCustomsDeclarationCommInfo();// 特殊报关单明细表
					customsDeclarationCommInfo.setBaseCustomsDeclaration(head);
					tempUses1 = dbimportDao.FindUses("", rsImg
							.getString("omd15"));// 用途
					customsDeclarationCommInfo.setUses(tempUses1);
					Complex tempComplex = new Complex();
					tempComplex = dbimportDao.Findbycodecomplex(rsImg
							.getString("omd01"));// 商品信息
					customsDeclarationCommInfo.setCommSerialNo(Integer
							.valueOf(rsImg.getString("omd13")));// 序号
					customsDeclarationCommInfo.setSerialNumber(Integer
							.valueOf(rsImg.getString("omd00")));// 商品流水号
					customsDeclarationCommInfo.setComplex(tempComplex);
					customsDeclarationCommInfo.setCommName(rsImg
							.getString("omd02") == null ? null : rsImg
							.getString("omd02").trim());// 商品名称
					customsDeclarationCommInfo.setCommSpec(rsImg
							.getString("omd03") == null ? null : rsImg
							.getString("omd03").trim());// 商品规格

					Unit unit = new Unit();// 单位
					unit = dbimportDao.Findbynameunit(rsImg.getString("omd05"));
					customsDeclarationCommInfo.setUnit(unit);
					Unit unit1 = new Unit();// 第一法定单位
					unit1 = dbimportDao
							.Findbynameunit(rsImg.getString("omd07"));
					customsDeclarationCommInfo.setLegalUnit(unit1);
					Unit unit2 = new Unit();// 第二法定单位
					unit2 = dbimportDao.Findbynameunit(rsImg
							.getString("omd071"));
					customsDeclarationCommInfo.setSecondLegalUnit(unit2);

					customsDeclarationCommInfo.setMaterielNo(rsImg
							.getString("omd16")); // 货号
					customsDeclarationCommInfo.setVersion(rsImg
							.getString("omd18"));// 版本

					if (GenericValidator.isDouble(rsImg.getString("omd04")))// 数量
						customsDeclarationCommInfo.setCommAmount(rsImg
								.getDouble("omd04"));
					double comamount = StrTodou(rsImg.getString("omd04"));
					if (GenericValidator.isDouble(rsImg.getString("omd06"))) // 第一法定比例因子
						customsDeclarationCommInfo.setFirstAmount(Double
								.valueOf(comamount
										* StrTodou(rsImg.getString("omd06"))));
					if (GenericValidator.isDouble(rsImg.getString("omd061"))) // 第二法定比例因子
						customsDeclarationCommInfo.setSecondAmount(Double
								.valueOf(comamount
										* StrTodou(rsImg.getString("omd061"))));
					if (GenericValidator.isDouble(rsImg.getString("omd20")))// 申报单价
						customsDeclarationCommInfo.setCommUnitPrice(Double
								.valueOf(rsImg.getString("omd20")));
					if (GenericValidator.isDouble(rsImg.getString("omd21")))// 总价格
						customsDeclarationCommInfo.setCommTotalPrice(Double
								.valueOf(rsImg.getString("omd21")));

					Country country = new Country();
					country = dbimportDao.Findbynamecountry(rsImg
							.getString("omd09"));// 最终目的产国
					customsDeclarationCommInfo.setCountry(country);

					tempLevyMode1 = dbimportDao.FindLevyMode("", rsImg
							.getString("omd14"));// 减免方式
					customsDeclarationCommInfo.setLevyMode(tempLevyMode1);

					if (GenericValidator.isDouble(rsImg.getString("omd08")))// 净重
						customsDeclarationCommInfo.setNetWeight(Double
								.valueOf(rsImg.getString("omd08")));

					/*
					 * if
					 * (GenericValidator.isDouble(rsImg.getString("emd23")))//
					 * 毛重
					 * customsDeclarationCommInfo.setGrossWeight(rsImg.getDouble
					 * ("emd23"));
					 */

					/*
					 * if (GenericValidator.isInt(rsImg.getString("emd24")))
					 * customsDeclarationCommInfo.setPieces(Integer
					 * .valueOf(rsImg.getString("emd24")));// 件数
					 */customsDeclarationCommInfo.setCompany(CommonUtils
							.getCompany());

					dbimportDao.SaveObject(customsDeclarationCommInfo);
				} catch (Exception e) {
					System.out.println("Error_detail:" + rsImg.getRow() + e);
					continue;
				}
			}
		}
	}

	/**
	 *  电子手册进口报关单
	 * @param emsNo
	 * @throws SQLException
	 */
	public void insertJKDzscBGHead(String emsNo) throws SQLException {
		getst();
		ResultSet rsHead = null;
		ResultSet rsImg = null;
		rsHead = st
				.executeQuery("select * from hg_import_dd where idd30 like '%清关%'  and ImportNumber = 1019  order by idd02,importnumber");// where
		// idd02
		// =
		// '"+emsNo+"'
		while (rsHead.next()) {
			DzscCustomsDeclaration head = new DzscCustomsDeclaration();
			head.setBillListId("导入");
			head.setImpExpFlag(ImpExpFlag.IMPORT);
			head.setImpExpType(getType(rsHead.getString("ImportType")));
			head.setSerialNumber(Integer.valueOf(rsHead
					.getString("ImportNumber")));
			System.out.println("---------ImportNumber:"
					+ head.getSerialNumber());
			head.setEmsHeadH2k(rsHead.getString("IDD02"));
			head.setTradeCode(dbimportDao.FindBriefCodeByName(rsHead
					.getString("IDD05")));
			head.setTradeName(rsHead.getString("IDD05"));
			head.setPreCustomsDeclarationCode(organizeStr(rsHead
					.getString("idd31")));// 预录入号
			head.setUnificationCode(rsHead.getString("idd66"));// 统一编号
			// head.setTempPreCode();
			if (rsHead.getString("ifok").equals("Y"))
				head.setEffective(new Boolean(true));// 是否生效
			else
				head.setEffective(new Boolean(false));// 是否生效
			head.setCancel(new Boolean(false));// 是否作废
			// 是否转关确认
			// 是否申报
			Customs tempCustoms = new Customs();
			tempCustoms = dbimportDao.Findbynamecustoms(rsHead
					.getString("idd01"));
			head.setCustoms(tempCustoms); // 进/出口岸
			head.setCustomsDeclarationCode(rsHead.getString("idd32"));// 报关单号
			head.setCustomsDeclarationCodeinHouse(rsHead.getString("idd67"));// 入库报关单号
			head.setImpExpDate(rsHead.getDate("idd03"));// 进口日期
			head.setDeclarationDate(rsHead.getDate("idd04"));// 出口日期
			head.setMachCode(dbimportDao.FindBriefCodeByName(rsHead
					.getString("idd09"))); // 收货单位代码
			head.setMachName(rsHead.getString("idd09")); // 收货单位名称

			Transf tempTransf = new Transf();
			tempTransf = dbimportDao
					.Findbynametransf(rsHead.getString("idd06"));
			head.setTransferMode(tempTransf);// 运输方式
			head.setConveyance(rsHead.getString("idd07"));// 运输工具
			head.setBillOfLading(rsHead.getString("idd08"));// 提运单号
			Trade tempTrade = new Trade();
			tempTrade = dbimportDao.Findbynametrade(rsHead.getString("idd10"));
			head.setTradeMode(tempTrade);// 贸易方式
			LevyKind tempLevyKind = new LevyKind();
			tempLevyKind = dbimportDao.FindLevyKind("", rsHead
					.getString("idd11"));
			head.setLevyKind(tempLevyKind);// 征免性质
			if (GenericValidator.isDouble(rsHead.getString("idd12"))) {
				head.setPerTax(rsHead.getDouble("idd12"));// 征税比例
			}
			head.setLicense(rsHead.getString("idd13"));// 许可证编号
			Country tempCountry = new Country();
			tempCountry = dbimportDao.Findbynamecountry(rsHead
					.getString("idd14"));
			head.setCountryOfLoadingOrUnloading(tempCountry);// 起运国
			District tempDistrict = new District();
			tempDistrict = dbimportDao.FindDistrict("", rsHead
					.getString("idd16"));
			head.setDomesticDestinationOrSource(tempDistrict);// 境内目的地
			PortLin tempPortLin = new PortLin();
			tempPortLin = dbimportDao
					.FindPortLin("", rsHead.getString("idd15"));
			head.setPortOfLoadingOrUnloading(tempPortLin);// 装货港口
			head.setAuthorizeFile(rsHead.getString("idd17"));// 批准文号(外汇核销单号)
			head.setContract(rsHead.getString("idd22"));// 合同协议号
			Transac tempTransac = new Transac();// 成交方式
			tempTransac = dbimportDao
					.FindTransac("", rsHead.getString("idd18"));
			head.setTransac(tempTransac);
			if ((rsHead.getString("idd191") != null)
					&& (!rsHead.getString("idd191").trim().equals("")))
				head.setFreightageType(Integer.valueOf(rsHead
						.getString("idd191")));// 运费类型
			if ((rsHead.getString("idd19") != null)
					&& (!rsHead.getString("idd19").trim().equals("")))
				head.setFreightage(rsHead.getDouble("idd19"));// 运费

			if ((rsHead.getString("idd201") != null)
					&& (!rsHead.getString("idd201").trim().equals("")))
				head.setInsuranceType(Integer.valueOf(rsHead
						.getString("idd201")));// 保险费类型
			if ((rsHead.getString("idd20") != null)
					&& (!rsHead.getString("idd20").trim().equals("")))
				head.setInsurance(rsHead.getDouble("idd20"));// 保险费

			if ((rsHead.getString("idd211") != null)
					&& (!rsHead.getString("idd211").trim().equals("")))
				head.setIncidentalExpensesType(Integer.valueOf(rsHead
						.getString("idd211")));// 杂费类型
			if ((rsHead.getString("idd21") != null)
					&& (!rsHead.getString("idd21").trim().equals("")))
				head.setIncidentalExpenses(rsHead.getDouble("idd21"));// 杂费

			if (GenericValidator.isDouble(rsHead.getString("idd23")))
				head.setCommodityNum(Convfloattoint(rsHead.getString("idd23")));// 件数
			Wrap tempWrap = new Wrap();
			tempWrap = dbimportDao.FindWrap("", rsHead.getString("idd24"));
			head.setWrapType(tempWrap); // 包装种类
			if ((rsHead.getString("idd25") != null)
					&& (!rsHead.getString("idd25").trim().equals("")))
				head.setGrossWeight(rsHead.getDouble("idd25"));// 毛重
			if ((rsHead.getString("idd26") != null)
					&& (!rsHead.getString("idd26").trim().equals("")))
				head.setNetWeight(rsHead.getDouble("idd26"));// 净重

			head.setContainerNum(rsHead.getString("idd27"));// 集装箱号
			head.setAttachedBill(rsHead.getString("idd37"));// 随附单据
			Uses tempUses = new Uses();
			tempUses = dbimportDao.FindUses("", rsHead.getString("idd29"));
			head.setUses(tempUses);// 用途
			Company tempBrief = new Company();
			tempBrief = dbimportDao
					.Findbynamecompany(rsHead.getString("idd34"));
			head.setDeclarationCompany(tempBrief);// 申报单位

			Curr tempCurr = new Curr();
			tempCurr = dbimportDao.Findbych_encurr("", rsHead
					.getString("idd33"));
			head.setCurrency(tempCurr);// 币别
			if ((rsHead.getString("idd65") != null)// 汇率
					&& (!rsHead.getString("idd65").trim().equals("")))
				head.setExchangeRate(rsHead.getDouble("idd65"));
			AclUser tempAclUser = new AclUser();
			tempAclUser = dbimportDao.FindbyloginnameAclUser(rsHead
					.getString("idd35"));
			head.setCreater(tempAclUser);// 录入员
			ScmCoc tempScmCoc = new ScmCoc();// 客户
			tempScmCoc = dbimportDao.Findbynamescmcoc(rsHead
					.getString("idd091"));
			head.setCustomer(tempScmCoc);

			tempCustoms = dbimportDao.Findbynamecustoms(rsHead
					.getString("idd39"));
			head.setDeclarationCustoms(tempCustoms);// 报送海关
			PreDock tempPreDock = new PreDock();// 装卸码头
			tempPreDock = dbimportDao
					.FindPreDock("", rsHead.getString("idd41"));
			head.setPredock(tempPreDock);
			head.setMemos(rsHead.getString("idd30"));// 备注
			head.setCertificateCode(rsHead.getString("idd70")); // 备注二
			head.setCustomser(rsHead.getString("IDD43"));// 报关员
			head.setTelephone(rsHead.getString("IDD44"));// 报关员电话
			head.setBarCode(rsHead.getString("idd42"));// 条形码
			/*
			 * Brief brief = new Brief(); brief =
			 * dbimportDao.Findbynamebrief(rsHead.getString("idd29"));
			 * head.setManufacturer(brief);
			 */// 生产厂商
			head.setAllContainerNum(rsHead.getString("idd40"));// 所有集装箱号

			if (rsHead.getString("idd69") != null
					&& rsHead.getString("idd69").equals("0")) { // 有无纸报关
				head.setIsNoBumf("O");
			} else {
				head.setIsNoBumf("W");
			}
			// 结汇方式
			head.setOverseasConveyanceCode(rsHead.getString("IDD49"));// 境外运输工具编号
			head.setOverseasConveyanceName(rsHead.getString("IDD50"));// 境外运输工具名字
			head.setOverseasConveyanceFlights(rsHead.getString("IDD51"));// 境外运输工具航次
			head.setOverseasConveyanceBillOfLading(rsHead.getString("IDD52"));// 境外运输工具提单号
			Transf transf = new Transf();
			transf = dbimportDao.Findbynametransf(rsHead.getString("IDD56"));// 境内运输方式
			head.setDomesticTransferMode(transf);

			head.setDomesticConveyanceCode(rsHead.getString("IDD53"));// 境内运输工具编号
			head.setDomesticConveyanceName(rsHead.getString("IDD54"));// 境内运输工具名字
			head.setDomesticConveyanceFlights(rsHead.getString("IDD55"));// 境内运输工具航次
			head.setCompany(CommonUtils.getCompany());
			if (rsHead.getString("idd48") != null
					&& rsHead.getString("idd48").equals("Y")) { // 是否检查通过
				head.setIsCheck(new Boolean(true));
			} else {
				head.setIsCheck(new Boolean(false));
			}
			head = dbimportDao.SaveCustomsDeclaration(head);
			String[] strs = null;
			String s = rsHead.getString("idd40");
			if (s != null && !s.trim().equals("")) {
				strs = s.split(String.valueOf((char) 44));
				for (int i = 0; i < strs.length; i++) {
					DzscCustomsDeclarationContainer declara = new DzscCustomsDeclarationContainer();
					try {
						String code = "";
						String code1 = "";
						String code2 = "";
						try {
							code = strs[i].trim().substring(0, 11);
						} catch (Exception e) {
						}
						declara.setContainerCode(code);
						try {
							code1 = strs[i].trim().substring(12, 16);
						} catch (Exception e) {
						}
						SrtJzx srt = new SrtJzx();
						srt = dbimportDao.getSrtJzx(code1);
						declara.setSrtJzx(srt);
						try {
							code2 = strs[i].trim().substring(16,
									strs[i].trim().length());
						} catch (Exception e) {
						}
						SrtTj tj = new SrtTj();
						tj = dbimportDao.getSrtTj(code2);
						declara.setSrttj(tj);
						declara.setCompany(CommonUtils.getCompany());
						declara.setBaseCustomsDeclaration(head);
						dbimportDao.SaveObject(declara);
					} catch (Exception e) {
						System.out.println("错误：" + e);
					}
				}
			}
			getst();
			rsImg = st.executeQuery("select * from hg_import_dddetails where"
					+ " customcode = '" + rsHead.getString("idd02")
					+ "' and importnumber = '"
					+ rsHead.getString("importnumber") + "'");
			while (rsImg.next()) {
				try {
					LevyMode tempLevyMode1 = new LevyMode();
					Uses tempUses1 = new Uses();
					DzscCustomsDeclarationCommInfo customsDeclarationCommInfo = new DzscCustomsDeclarationCommInfo();// 进口报关单明细表
					customsDeclarationCommInfo.setBaseCustomsDeclaration(head);
					tempUses1 = dbimportDao.FindUses("", rsImg
							.getString("imd15"));// 用途
					customsDeclarationCommInfo.setUses(tempUses1);
					Complex tempComplex = new Complex();
					tempComplex = dbimportDao.Findbycodecomplex(rsImg
							.getString("imd01"));// 商品信息
					customsDeclarationCommInfo.setCommSerialNo(Integer
							.valueOf(rsImg.getString("imd13")));// 序号
					customsDeclarationCommInfo.setSerialNumber(Integer
							.valueOf(rsImg.getString("imd00")));// 商品流水号
					customsDeclarationCommInfo.setComplex(tempComplex);
					customsDeclarationCommInfo.setCommName(rsImg
							.getString("imd02") == null ? null : rsImg
							.getString("imd02").trim());// 商品名称
					customsDeclarationCommInfo.setCommSpec(rsImg
							.getString("imd03") == null ? null : rsImg
							.getString("imd03").trim());// 商品规格

					Unit unit = new Unit();// 单位
					unit = dbimportDao.Findbynameunit(rsImg.getString("imd05"));
					customsDeclarationCommInfo.setUnit(unit);
					Unit unit1 = new Unit();// 第一法定单位
					unit1 = dbimportDao
							.Findbynameunit(rsImg.getString("imd07"));
					customsDeclarationCommInfo.setLegalUnit(unit1);
					Unit unit2 = new Unit();// 第二法定单位
					unit2 = dbimportDao.Findbynameunit(rsImg
							.getString("imd071"));
					customsDeclarationCommInfo.setSecondLegalUnit(unit2);

					customsDeclarationCommInfo.setMaterielNo(rsImg
							.getString("imd16")); // 货号
					customsDeclarationCommInfo.setVersion(rsImg
							.getString("imd17"));// 版本

					if (GenericValidator.isDouble(rsImg.getString("imd04")))// 数量
						customsDeclarationCommInfo.setCommAmount(rsImg
								.getDouble("imd04"));
					double comamount = StrTodou(rsImg.getString("imd04"));
					if (GenericValidator.isDouble(rsImg.getString("imd06"))) // 第一法定比例因子
						customsDeclarationCommInfo.setFirstAmount(Double
								.valueOf(comamount
										* StrTodou(rsImg.getString("imd06"))));
					if (GenericValidator.isDouble(rsImg.getString("imd061"))) // 第二法定比例因子
						customsDeclarationCommInfo.setSecondAmount(Double
								.valueOf(comamount
										* StrTodou(rsImg.getString("imd061"))));
					if (GenericValidator.isDouble(rsImg.getString("imd20")))// 申报单价
						customsDeclarationCommInfo.setCommUnitPrice(Double
								.valueOf(rsImg.getString("imd20")));
					if (GenericValidator.isDouble(rsImg.getString("imd21")))// 总价格
						customsDeclarationCommInfo.setCommTotalPrice(Double
								.valueOf(rsImg.getString("imd21")));

					Country country = new Country();
					country = dbimportDao.Findbynamecountry(rsImg
							.getString("imd09"));// 最终目的产国
					customsDeclarationCommInfo.setCountry(country);

					tempLevyMode1 = dbimportDao.FindLevyMode("", rsImg
							.getString("imd14"));// 减免方式
					customsDeclarationCommInfo.setLevyMode(tempLevyMode1);

					if (GenericValidator.isDouble(rsImg.getString("imd22")))// 净重
						customsDeclarationCommInfo.setNetWeight(Double
								.valueOf(rsImg.getString("imd22")));

					if (GenericValidator.isDouble(rsImg.getString("imd23")))// 毛重
						customsDeclarationCommInfo.setGrossWeight(rsImg
								.getDouble("imd23"));

					if (GenericValidator.isInt(rsImg.getString("imd24")))
						customsDeclarationCommInfo.setPieces(Integer
								.valueOf(rsImg.getString("imd24")));// 件(箱)数
					customsDeclarationCommInfo.setCompany(CommonUtils
							.getCompany());
					dbimportDao.SaveObject(customsDeclarationCommInfo);
				} catch (Exception e) {
					System.out.println("Error_detail:" + rsImg.getRow() + e);
					continue;
				}
			}
		}
	}

	/**
	 * 电子手册出口报关单
	 * @param emsNo
	 * @throws SQLException
	 */
	public void insertCKDzscBGHead(String emsNo) throws SQLException {
		getst();
		ResultSet rsHead = null;
		ResultSet rsImg = null;
		rsHead = st
				.executeQuery("select * from hg_export_dd  where edd30 like '%清关%'  order by edd02,exportnumber");// where
		// edd02
		// =
		// '"+emsNo+"'
		while (rsHead.next()) {
			DzscCustomsDeclaration head = new DzscCustomsDeclaration();
			head.setBillListId("导入");
			head.setImpExpFlag(ImpExpFlag.EXPORT);// 出口标志
			head.setImpExpType(getType(rsHead.getString("exporttype")));// 出口类型
			head.setSerialNumber(Integer.valueOf(rsHead
					.getString("exportnumber")));// 大单流水号
			head.setEmsHeadH2k(rsHead.getString("EDD02"));// 电子帐册号码(电子帐册手册编号)
			head.setTradeCode(dbimportDao// 经营单位代码
					.FindBriefCodeByName(rsHead.getString("eDD05")));
			head.setTradeName(rsHead.getString("eDD05"));// 经营单位名称
			head.setPreCustomsDeclarationCode(organizeStr(rsHead
					.getString("edd31")));// 预录入号
			head.setUnificationCode(rsHead.getString("edd66"));// 统一编号
			// head.setTempPreCode();
			if (rsHead.getString("ifok") != null
					&& rsHead.getString("ifok").equals("Y"))
				head.setEffective(new Boolean(true));// 是否生效
			else
				head.setEffective(new Boolean(false));// 是否生效
			head.setCancel(new Boolean(false));// 是否作废
			Customs tempCustoms = new Customs();
			tempCustoms = dbimportDao.Findbynamecustoms(rsHead
					.getString("edd01"));
			head.setCustoms(tempCustoms); // 进/出口岸
			head.setCustomsDeclarationCode(rsHead.getString("edd32"));// 报关单号
			head.setCustomsDeclarationCodeinHouse(rsHead.getString("edd67"));// 入库报关单号
			head.setImpExpDate(rsHead.getDate("edd03"));// 出口日期
			head.setDeclarationDate(rsHead.getDate("edd04"));// 申报日期
			head.setMachCode(dbimportDao.FindBriefCodeByName(rsHead
					.getString("edd09"))); // 收货单位代码
			head.setMachName(rsHead.getString("edd09")); // 收货单位名称

			Transf tempTransf = new Transf();
			tempTransf = dbimportDao
					.Findbynametransf(rsHead.getString("edd06"));
			head.setTransferMode(tempTransf);// 运输方式
			head.setConveyance(rsHead.getString("edd07"));// 运输工具
			head.setBillOfLading(rsHead.getString("edd08"));// 提运单号
			Trade tempTrade = new Trade();
			tempTrade = dbimportDao.Findbynametrade(rsHead.getString("edd10"));
			head.setTradeMode(tempTrade);// 贸易方式
			LevyKind tempLevyKind = new LevyKind();
			tempLevyKind = dbimportDao.FindLevyKind("", rsHead
					.getString("edd11"));
			head.setLevyKind(tempLevyKind);// 征免性质
			if (GenericValidator.isDouble(rsHead.getString("edd12"))) {
				head.setPerTax(rsHead.getDouble("edd12"));// 征税比例
			}
			head.setLicense(rsHead.getString("edd13"));// 许可证编号
			Country tempCountry = new Country();// 运抵国
			tempCountry = dbimportDao.Findbynamecountry(rsHead
					.getString("edd14"));
			head.setCountryOfLoadingOrUnloading(tempCountry);
			District tempDistrict = new District();// 境内货源地
			tempDistrict = dbimportDao.FindDistrict("", rsHead
					.getString("edd16"));
			head.setDomesticDestinationOrSource(tempDistrict);
			PortLin tempPortLin = new PortLin();// 指运港口
			tempPortLin = dbimportDao
					.FindPortLin("", rsHead.getString("edd15"));
			head.setPortOfLoadingOrUnloading(tempPortLin);
			head.setAuthorizeFile(rsHead.getString("edd17"));// 合同协议号（电子备案进出口合同协议号）
			head.setContract(rsHead.getString("edd22"));// 合同协议号
			Transac tempTransac = new Transac();// 成交方式
			tempTransac = dbimportDao
					.FindTransac("", rsHead.getString("edd18"));
			head.setTransac(tempTransac);
			if ((rsHead.getString("edd191") != null)// 运费
					&& (!rsHead.getString("edd191").trim().equals("")))
				head.setFreightageType(Integer.valueOf(rsHead
						.getString("edd191")));// 运费类型
			if ((rsHead.getString("edd19") != null)
					&& (!rsHead.getString("edd19").trim().equals("")))
				head.setFreightage(rsHead.getDouble("edd19"));

			if ((rsHead.getString("edd201") != null)
					&& (!rsHead.getString("edd201").trim().equals("")))
				head.setInsuranceType(Integer.valueOf(rsHead
						.getString("edd201")));// 保险费类型
			if ((rsHead.getString("edd20") != null)
					&& (!rsHead.getString("edd20").trim().equals("")))
				head.setInsurance(rsHead.getDouble("edd20"));// 保险费

			if ((rsHead.getString("edd211") != null)
					&& (!rsHead.getString("edd211").trim().equals("")))
				head.setIncidentalExpensesType(Integer.valueOf(rsHead
						.getString("edd211")));// 杂费类型
			if ((rsHead.getString("edd21") != null)
					&& (!rsHead.getString("edd21").trim().equals("")))
				head.setIncidentalExpenses(rsHead.getDouble("edd21"));// 杂费

			if (GenericValidator.isDouble(rsHead.getString("edd23")))
				head.setCommodityNum(Convfloattoint(rsHead.getString("edd23")));// 件数
			Wrap tempWrap = new Wrap();
			tempWrap = dbimportDao.FindWrap("", rsHead.getString("edd24"));
			head.setWrapType(tempWrap); // 包装种类
			if ((rsHead.getString("edd25") != null)
					&& (!rsHead.getString("edd25").trim().equals("")))
				head.setGrossWeight(rsHead.getDouble("edd25"));// 毛重
			if ((rsHead.getString("edd26") != null)
					&& (!rsHead.getString("edd26").trim().equals("")))
				head.setNetWeight(rsHead.getDouble("edd26"));// 净重

			head.setContainerNum(rsHead.getString("edd27"));// 集装箱号
			head.setAttachedBill(rsHead.getString("edd28"));// 依附单据
			/*
			 * Uses tempUses = new Uses();// 用途 tempUses =
			 * dbimportDao.FindUses("", rsHead.getString("edd29"));
			 * head.setUses(tempUses);
			 */
			Company tempBrief = new Company();
			tempBrief = dbimportDao
					.Findbynamecompany(rsHead.getString("edd34"));
			head.setDeclarationCompany(tempBrief);// 申报单位

			Curr tempCurr = new Curr();
			tempCurr = dbimportDao.Findbych_encurr("", rsHead
					.getString("edd33"));
			head.setCurrency(tempCurr);// 币别
			if ((rsHead.getString("edd65") != null)// 汇率
					&& (!rsHead.getString("edd65").trim().equals("")))
				head.setExchangeRate(rsHead.getDouble("edd65"));

			AclUser tempAclUser = new AclUser();
			tempAclUser = dbimportDao.FindbyloginnameAclUser(rsHead
					.getString("edd35"));// 录入员
			head.setCreater(tempAclUser);
			ScmCoc tempScmCoc = new ScmCoc();// 客户
			tempScmCoc = dbimportDao.Findbynamescmcoc(rsHead
					.getString("edd091"));
			head.setCustomer(tempScmCoc);

			tempCustoms = dbimportDao.Findbynamecustoms(rsHead
					.getString("edd39"));
			head.setDeclarationCustoms(tempCustoms);// 报送海关
			PreDock tempPreDock = new PreDock();// 码头
			tempPreDock = dbimportDao
					.FindPreDock("", rsHead.getString("edd41"));
			head.setPredock(tempPreDock);
			head.setMemos(rsHead.getString("edd30"));// 备注
			head.setCertificateCode(rsHead.getString("edd70")); // 备注二
			head.setCustomser(rsHead.getString("eDD43"));// 报关员
			head.setTelephone(rsHead.getString("eDD44"));// 报关员电话
			head.setBarCode("");// 条形码
			Brief brief = new Brief();
			brief = dbimportDao.Findbynamebrief(rsHead.getString("edd29"));
			head.setManufacturer(brief);// 生产厂商
			head.setAllContainerNum(rsHead.getString("edd40"));// 所有集装箱号
			if (rsHead.getString("edd69") != null
					&& rsHead.getString("edd69").equals("0")) { // 有无纸报关
				head.setIsNoBumf("O");
			} else {
				head.setIsNoBumf("W");
			}
			head.setOverseasConveyanceCode(rsHead.getString("eDD49"));// 境外运输工具编号
			head.setOverseasConveyanceName(rsHead.getString("eDD50"));// 境外运输工具名字
			head.setOverseasConveyanceFlights(rsHead.getString("eDD51"));// 境外运输工具航次
			head.setOverseasConveyanceBillOfLading(rsHead.getString("eDD52"));// 境外运输工具提单号
			Transf transf = new Transf();
			transf = dbimportDao.Findbynametransf(rsHead.getString("eDD56"));// 境内运输方式
			head.setDomesticTransferMode(transf);

			head.setDomesticConveyanceCode(rsHead.getString("eDD53"));// 境内运输工具编号
			head.setDomesticConveyanceName(rsHead.getString("eDD54"));// 境内运输工具名字
			head.setDomesticConveyanceFlights(rsHead.getString("eDD55"));// 境内运输工具航次
			head.setCompany(CommonUtils.getCompany());
			if (rsHead.getString("edd48") != null
					&& rsHead.getString("edd48").equals("Y")) { // 是否检查通过
				head.setIsCheck(new Boolean(true));
			} else {
				head.setIsCheck(new Boolean(false));
			}
			BalanceMode obj = new BalanceMode();
			obj = dbimportDao.FindbyBalanceMode("", rsHead.getString("edd12"));
			head.setBalanceMode(obj);
			head = dbimportDao.SaveCustomsDeclaration(head);

			String[] strs = null;
			String s = rsHead.getString("edd40");
			if (s != null && !s.trim().equals("")) {
				strs = s.split(String.valueOf((char) 44));
				for (int i = 0; i < strs.length; i++) {
					DzscCustomsDeclarationContainer declara = new DzscCustomsDeclarationContainer();
					try {
						String code = "";
						String code1 = "";
						String code2 = "";
						try {
							code = strs[i].trim().substring(0, 11);
						} catch (Exception e) {
						}
						declara.setContainerCode(code);
						try {
							code1 = strs[i].trim().substring(12, 16);
						} catch (Exception e) {
						}
						SrtJzx srt = new SrtJzx();
						srt = dbimportDao.getSrtJzx(code1);
						declara.setSrtJzx(srt);
						try {
							code2 = strs[i].trim().substring(16,
									strs[i].trim().length());
						} catch (Exception e) {
						}
						SrtTj tj = new SrtTj();
						tj = dbimportDao.getSrtTj(code2);
						declara.setSrttj(tj);
						declara.setCompany(CommonUtils.getCompany());
						declara.setBaseCustomsDeclaration(head);
						dbimportDao.SaveObject(declara);
					} catch (Exception e) {
						System.out.println("错误：" + e);
					}
				}
			}
			getst();
			rsImg = st.executeQuery("select * from hg_export_dddetails where"
					+ " customcode = '" + rsHead.getString("edd02")
					+ "' and exportnumber = '"
					+ rsHead.getString("exportnumber") + "'");
			while (rsImg.next()) {
				try {
					LevyMode tempLevyMode1 = new LevyMode();
					Uses tempUses1 = new Uses();
					DzscCustomsDeclarationCommInfo customsDeclarationCommInfo = new DzscCustomsDeclarationCommInfo();// 进口报关单明细表
					customsDeclarationCommInfo.setBaseCustomsDeclaration(head);
					tempUses1 = dbimportDao.FindUses("", rsImg
							.getString("emd15"));// 用途
					customsDeclarationCommInfo.setUses(tempUses1);
					Complex tempComplex = new Complex();
					tempComplex = dbimportDao.Findbycodecomplex(rsImg
							.getString("emd01"));// 商品信息
					customsDeclarationCommInfo.setCommSerialNo(Integer
							.valueOf(rsImg.getString("emd13")));// 序号
					customsDeclarationCommInfo.setSerialNumber(Integer
							.valueOf(rsImg.getString("emd00")));// 商品流水号
					customsDeclarationCommInfo.setComplex(tempComplex);
					customsDeclarationCommInfo.setCommName(rsImg
							.getString("emd02") == null ? null : rsImg
							.getString("emd02").trim());// 商品名称
					customsDeclarationCommInfo.setCommSpec(rsImg
							.getString("emd03") == null ? null : rsImg
							.getString("emd03").trim());// 商品规格

					Unit unit = new Unit();// 单位
					unit = dbimportDao.Findbynameunit(rsImg.getString("emd05"));
					customsDeclarationCommInfo.setUnit(unit);
					Unit unit1 = new Unit();// 第一法定单位
					unit1 = dbimportDao
							.Findbynameunit(rsImg.getString("emd07"));
					customsDeclarationCommInfo.setLegalUnit(unit1);
					Unit unit2 = new Unit();// 第二法定单位
					unit2 = dbimportDao.Findbynameunit(rsImg
							.getString("emd071"));
					customsDeclarationCommInfo.setSecondLegalUnit(unit2);

					customsDeclarationCommInfo.setMaterielNo(rsImg
							.getString("emd16")); // 货号
					customsDeclarationCommInfo.setVersion(rsImg
							.getString("emd17"));// 版本

					if (GenericValidator.isDouble(rsImg.getString("emd04")))// 数量
						customsDeclarationCommInfo.setCommAmount(rsImg
								.getDouble("emd04"));
					double comamount = StrTodou(rsImg.getString("emd04"));
					if (GenericValidator.isDouble(rsImg.getString("emd06"))) // 第一法定比例因子
						customsDeclarationCommInfo.setFirstAmount(Double
								.valueOf(comamount
										* StrTodou(rsImg.getString("emd06"))));
					if (GenericValidator.isDouble(rsImg.getString("emd061"))) // 第二法定比例因子
						customsDeclarationCommInfo.setSecondAmount(Double
								.valueOf(comamount
										* StrTodou(rsImg.getString("emd061"))));
					if (GenericValidator.isDouble(rsImg.getString("emd20")))// 申报单价
						customsDeclarationCommInfo.setCommUnitPrice(Double
								.valueOf(rsImg.getString("emd20")));
					if (GenericValidator.isDouble(rsImg.getString("emd21")))// 总价格
						customsDeclarationCommInfo.setCommTotalPrice(Double
								.valueOf(rsImg.getString("emd21")));

					Country country = new Country();
					country = dbimportDao.Findbynamecountry(rsImg
							.getString("emd09"));// 最终目的产国
					customsDeclarationCommInfo.setCountry(country);

					tempLevyMode1 = dbimportDao.FindLevyMode("", rsImg
							.getString("emd14"));// 减免方式
					customsDeclarationCommInfo.setLevyMode(tempLevyMode1);

					if (GenericValidator.isDouble(rsImg.getString("emd22")))// 净重
						customsDeclarationCommInfo.setNetWeight(Double
								.valueOf(rsImg.getString("emd22")));

					if (GenericValidator.isDouble(rsImg.getString("emd23")))// 毛重
						customsDeclarationCommInfo.setGrossWeight(rsImg
								.getDouble("emd23"));

					if (GenericValidator.isInt(rsImg.getString("emd24")))
						customsDeclarationCommInfo.setPieces(Integer
								.valueOf(rsImg.getString("emd24")));// 件数
					customsDeclarationCommInfo.setCompany(CommonUtils
							.getCompany());

					dbimportDao.SaveObject(customsDeclarationCommInfo);
				} catch (Exception e) {
					System.out.println("Error_detail:" + rsImg.getRow() + e);
					continue;
				}
			}
		}
	}

	/**
	 * 取整
	 * @param arg0
	 * @return
	 */
	public Integer Convfloattoint(String arg0) {
		double d1 = Double.parseDouble(arg0);
		long dl = Math.round(d1);
		String getstr = Long.toString(dl);
		try {
			return Integer.valueOf(getstr);
		} catch (Exception ex) {
			return Integer.valueOf(0);
		}
	}

	/**
	 * 格式化预录入号
	 * @param m
	 * @return
	 */
	private String organizeStr(String m) {
		if (m == null || m.equals(""))
			m = "0";
		int n = 6 - m.length();
		for (int i = 0; i < n; i++) {
			m = "0" + m;
		}
		if (m != null && m.length() == 17) {
			m = m.substring(11, 17);
		}
		return m;
	}

	/**
	 * 获得报关单类型
	 * @param typeName
	 * @return
	 */
	private Integer getType(String typeName) {
		Integer typeCode = null;
		if (typeName == null) {
			return typeCode;
		}
		if (typeName.equals("料件进口")) {
			typeCode = ImpExpType.DIRECT_IMPORT;
		} else if (typeName.equals("料件转厂")) {
			typeCode = ImpExpType.TRANSFER_FACTORY_IMPORT;
		} else if (typeName.equals("退厂返工")) {
			typeCode = ImpExpType.BACK_FACTORY_REWORK;
		} else if (typeName.equals("一般贸易进口")) {
			typeCode = ImpExpType.GENERAL_TRADE_IMPORT;
		} else if (typeName.equals("成品出口")) {
			typeCode = ImpExpType.DIRECT_EXPORT;
		} else if (typeName.equals("转厂出口")) {
			typeCode = ImpExpType.TRANSFER_FACTORY_EXPORT;
		} else if (typeName.equals("退料出口")) {
			typeCode = ImpExpType.BACK_MATERIEL_EXPORT;
		} else if (typeName.equals("返工复出")) {
			typeCode = ImpExpType.REWORK_EXPORT;
		} else if (typeName.equals("边角料退港")) {
			typeCode = ImpExpType.REMIAN_MATERIAL_BACK_PORT;
		} else if (typeName.equals("边角料内销")) {
			typeCode = ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES;
		} else if (typeName.equals("一般贸易出口")) {
			typeCode = ImpExpType.GENERAL_TRADE_EXPORT;
		} else if (typeName.equals("设备进口")) {
			typeCode = ImpExpType.EQUIPMENT_IMPORT;
		} else if (typeName.equals("退港维修")) {
			typeCode = ImpExpType.BACK_PORT_REPAIR;
		} else if (typeName.equals("设备退港")) {
			typeCode = ImpExpType.EQUIPMENT_BACK_PORT;
		} else if (typeName.equals("余料结转")) {
			typeCode = ImpExpType.REMAIN_FORWARD_EXPORT;
		} else if (typeName.equals("出口仓储")) {
			typeCode = ImpExpType.EXPORT_STORAGE;
		} else if (typeName.equals("进口仓储")) {
			typeCode = ImpExpType.IMPORT_STORAGE;
		} else if (typeName.equals("料件内销")) {
			typeCode = ImpExpType.MATERIAL_DOMESTIC_SALES;
		} else if (typeName.equals("料件退换")) {
			typeCode = ImpExpType.MATERIAL_EXCHANGE;
		} else if (typeName.equals("料件复出")) {
			typeCode = ImpExpType.MATERIAL_REOUT;
		}
		return typeCode;
	}

	/**
	 * @return Returns the dbimportDao.
	 */
	public DbimportDao getDbimportDao() {
		return dbimportDao;
	}

	/**
	 * @param dbimportDao
	 *            The dbimportDao to set.
	 */
	public void setDbimportDao(DbimportDao dbimportDao) {
		this.dbimportDao = dbimportDao;
	}

	/**
	 * @return Returns the casDao.
	 */
	public CasDao getCasDao() {
		return casDao;
	}

	/**
	 * @return Returns the dbCasDao.
	 */
	public DbCasDao getDbCasDao() {
		return dbCasDao;
	}

	/**
	 * @param casDao
	 *            The casDao to set.
	 */
	public void setCasDao(CasDao casDao) {
		this.casDao = casDao;
	}

	/**
	 * @param dbCasDao
	 *            The dbCasDao to set.
	 */
	public void setDbCasDao(DbCasDao dbCasDao) {
		this.dbCasDao = dbCasDao;
	}

	/**
	 * @return Returns the dbName.
	 */
	public String getDbName() {
		return dbName;
	}

	/**
	 * @param dbName
	 *            The dbName to set.
	 */
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	/**
	 * @return Returns the password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            The password to set.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return Returns the serverName.
	 */
	public String getServerName() {
		return serverName;
	}

	/**
	 * @param serverName
	 *            The serverName to set.
	 */
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	/**
	 * @return Returns the userName.
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            The userName to set.
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 联网监管报关单导入
	 * @throws SQLException
	 */
	public void insertJKBGHead_bcus() throws SQLException {
		getst();
		ResultSet rsHead = null;
		ResultSet rsImg = null;
		rsHead = st
				.executeQuery("select * from hg_import_dd order by idd02,importnumber");
		while (rsHead.next()) {
			CustomsDeclaration head = new CustomsDeclaration();
			head.setImpExpFlag(ImpExpFlag.IMPORT);
			head.setImpExpType(getType(rsHead.getString("ImportType")));
			String seqnum = (rsHead.getString("ImportNumber") == null || ""
					.equals(rsHead.getString("ImportNumber"))) ? "0" : rsHead
					.getString("ImportNumber");
			head.setSerialNumber(Integer.valueOf(seqnum));
			head.setEmsHeadH2k(rsHead.getString("IDD02"));
			try {
				head.setTradeCode(dbimportDao.FindBriefCodeByName(rsHead
						.getString("IDD05")));
			} catch (Exception e) {
			}
			head.setTradeName(rsHead.getString("IDD05"));
			try {
				head.setPreCustomsDeclarationCode(organizeStr(rsHead
						.getString("idd31")));// 预录入号
			} catch (Exception e) {
			}
			head.setUnificationCode(rsHead.getString("idd66"));// 统一编号
			if (rsHead.getString("ifok") != null
					&& rsHead.getString("ifok").equals("Y"))
				head.setEffective(new Boolean(true));// 是否生效
			else
				head.setEffective(new Boolean(false));// 是否生效
			head.setCancel(new Boolean(false));// 是否作废
			// 是否转关确认
			// 是否申报
			Customs tempCustoms = new Customs();
			tempCustoms = dbimportDao.Findbynamecustoms(rsHead
					.getString("idd01"));
			head.setCustoms(tempCustoms); // 进/出口岸
			head.setCustomsDeclarationCode(rsHead.getString("idd32"));// 报关单号
			head.setCustomsDeclarationCodeinHouse(rsHead.getString("idd67"));// 入库报关单号
			try {
				head.setImpExpDate(rsHead.getDate("idd03"));// 进口日期
				head.setDeclarationDate(rsHead.getDate("idd04"));// 出口日期
				head.setMachCode(dbimportDao.FindBriefCodeByName(rsHead
						.getString("idd09"))); // 收货单位代码
			} catch (Exception e) {
			}
			head.setMachName(rsHead.getString("idd09")); // 收货单位名称
			try {
				Transf tempTransf = new Transf();
				tempTransf = dbimportDao.Findbynametransf(rsHead
						.getString("idd06"));
				head.setTransferMode(tempTransf);// 运输方式
			} catch (Exception e) {
			}
			head.setConveyance(rsHead.getString("idd07"));// 运输工具
			head.setBillOfLading(rsHead.getString("idd08"));// 提运单号
			try {
				Trade tempTrade = new Trade();
				tempTrade = dbimportDao.Findbynametrade(rsHead
						.getString("idd10"));
				head.setTradeMode(tempTrade);// 贸易方式
				LevyKind tempLevyKind = new LevyKind();
				tempLevyKind = dbimportDao.FindLevyKind("", rsHead
						.getString("idd11"));
				head.setLevyKind(tempLevyKind);// 征免性质
				if (rsHead.getString("idd12") != null
						&& GenericValidator.isDouble(rsHead.getString("idd12"))) {
					head.setPerTax(rsHead.getDouble("idd12"));// 征税比例
				}
			} catch (Exception e) {
			}
			head.setLicense(rsHead.getString("idd13"));// 许可证编号
			try {
				Country tempCountry = new Country();
				tempCountry = dbimportDao.Findbynamecountry(rsHead
						.getString("idd14"));
				head.setCountryOfLoadingOrUnloading(tempCountry);// 起运国
				District tempDistrict = new District();
				tempDistrict = dbimportDao.FindDistrict("", rsHead
						.getString("idd16"));
				head.setDomesticDestinationOrSource(tempDistrict);// 境内目的地
				PortLin tempPortLin = new PortLin();
				tempPortLin = dbimportDao.FindPortLin("", rsHead
						.getString("idd15"));
				head.setPortOfLoadingOrUnloading(tempPortLin);// 装货港口
			} catch (Exception e) {
			}
			head.setAuthorizeFile(rsHead.getString("idd17"));// 批准文号(外汇核销单号)
			head.setContract(rsHead.getString("idd22"));// 合同协议号
			try {
				Transac tempTransac = new Transac();// 成交方式
				tempTransac = dbimportDao.FindTransac("", rsHead
						.getString("idd18"));
				head.setTransac(tempTransac);
			} catch (Exception e) {
			}
			try {
				if ((rsHead.getString("idd191") != null)
						&& (!rsHead.getString("idd191").trim().equals("")))
					head.setFreightageType(Integer.valueOf(rsHead
							.getString("idd191")));// 运费类型
				if ((rsHead.getString("idd19") != null)
						&& (!rsHead.getString("idd19").trim().equals("")))
					head.setFreightage(rsHead.getDouble("idd19"));// 运费

				if ((rsHead.getString("idd201") != null)
						&& (!rsHead.getString("idd201").trim().equals("")))
					head.setInsuranceType(Integer.valueOf(rsHead
							.getString("idd201")));// 保险费类型
				if ((rsHead.getString("idd20") != null)
						&& (!rsHead.getString("idd20").trim().equals("")))
					head.setInsurance(rsHead.getDouble("idd20"));// 保险费

				if ((rsHead.getString("idd211") != null)
						&& (!rsHead.getString("idd211").trim().equals("")))
					head.setIncidentalExpensesType(Integer.valueOf(rsHead
							.getString("idd211")));// 杂费类型
				if ((rsHead.getString("idd21") != null)
						&& (!rsHead.getString("idd21").trim().equals("")))
					head.setIncidentalExpenses(rsHead.getDouble("idd21"));// 杂费

				if (GenericValidator.isDouble(rsHead.getString("idd23")))
					head.setCommodityNum(Convfloattoint(rsHead
							.getString("idd23")));// 件数
			} catch (Exception e) {
			}
			try {
				Wrap tempWrap = new Wrap();
				tempWrap = dbimportDao.FindWrap("", rsHead.getString("idd24"));
				head.setWrapType(tempWrap); // 包装种类
				if ((rsHead.getString("idd25") != null)
						&& (!rsHead.getString("idd25").trim().equals("")))
					head.setGrossWeight(rsHead.getDouble("idd25"));// 毛重
				if ((rsHead.getString("idd26") != null)
						&& (!rsHead.getString("idd26").trim().equals("")))
					head.setNetWeight(rsHead.getDouble("idd26"));// 净重
			} catch (Exception e) {
			}
			head.setContainerNum(rsHead.getString("idd27"));// 集装箱号
			head.setAttachedBill(rsHead.getString("idd37"));// 随附单据
			try {
				Uses tempUses = new Uses();
				tempUses = dbimportDao.FindUses("", rsHead.getString("idd29"));
				head.setUses(tempUses);// 用途
				Company tempBrief = new Company();
				tempBrief = dbimportDao.Findbynamecompany(rsHead
						.getString("idd34"));
				head.setDeclarationCompany(tempBrief);// 申报单位

				Curr tempCurr = new Curr();
				tempCurr = dbimportDao.Findbych_encurr("", rsHead
						.getString("idd33"));
				head.setCurrency(tempCurr);// 币别
				if ((rsHead.getString("idd65") != null)// 汇率
						&& (!rsHead.getString("idd65").trim().equals("")))
					head.setExchangeRate(rsHead.getDouble("idd65"));
				AclUser tempAclUser = new AclUser();
				tempAclUser = dbimportDao.FindbyloginnameAclUser(rsHead
						.getString("idd35"));
				head.setCreater(tempAclUser);// 录入员
				ScmCoc tempScmCoc = new ScmCoc();// 客户
				tempScmCoc = dbimportDao.Findbynamescmcoc(rsHead
						.getString("idd091"));
				head.setCustomer(tempScmCoc);

				tempCustoms = dbimportDao.Findbynamecustoms(rsHead
						.getString("idd39"));
				head.setDeclarationCustoms(tempCustoms);// 报送海关
				PreDock tempPreDock = new PreDock();// 装卸码头
				tempPreDock = dbimportDao.FindPreDock("", rsHead
						.getString("idd41"));
				head.setPredock(tempPreDock);
			} catch (Exception e) {
			}
			head.setMemos(rsHead.getString("idd30"));// 备注
			head.setCertificateCode(rsHead.getString("idd70")); // 备注二
			head.setCustomser(rsHead.getString("IDD43"));// 报关员
			head.setTelephone(rsHead.getString("IDD44"));// 报关员电话
			head.setBarCode(rsHead.getString("idd42"));// 条形码

			// head.setAllContainerNum(rsHead.getString("idd40"));// 所有集装箱号
			if (rsHead.getString("idd69") != null
					&& rsHead.getString("idd69").equals("0")) { // 有无纸报关
				head.setIsNoBumf("O");
			} else {
				head.setIsNoBumf("W");
			}
			// 结汇方式
			head.setOverseasConveyanceCode(rsHead.getString("IDD49"));// 境外运输工具编号
			head.setOverseasConveyanceName(rsHead.getString("IDD50"));// 境外运输工具名字
			head.setOverseasConveyanceFlights(rsHead.getString("IDD51"));// 境外运输工具航次
			head.setOverseasConveyanceBillOfLading(rsHead.getString("IDD52"));// 境外运输工具提单号
			try {
				Transf transf = new Transf();
				transf = dbimportDao
						.Findbynametransf(rsHead.getString("IDD56"));// 境内运输方式
				head.setDomesticTransferMode(transf);
			} catch (Exception e) {
			}
			head.setDomesticConveyanceCode(rsHead.getString("IDD53"));// 境内运输工具编号
			head.setDomesticConveyanceName(rsHead.getString("IDD54"));// 境内运输工具名字
			head.setDomesticConveyanceFlights(rsHead.getString("IDD55"));// 境内运输工具航次
			head.setCompany(CommonUtils.getCompany());
			if (rsHead.getString("idd48") != null
					&& rsHead.getString("idd48").equals("Y")) { // 是否检查通过
				head.setIsCheck(new Boolean(true));
			} else {
				head.setIsCheck(new Boolean(false));
			}
			head = dbimportDao.SaveCustomsDeclaration(head);
			String[] strs = null;
			String s = rsHead.getString("idd40");
			try {
				if (s != null && !s.trim().equals("")) {
					strs = s.split(String.valueOf((char) 44));
					for (int i = 0; i < strs.length; i++) {
						CustomsDeclarationContainer declara = new CustomsDeclarationContainer();
						try {
							String code = "";
							String code1 = "";
							String code2 = "";
							try {
								code = strs[i].trim().substring(0, 11);
							} catch (Exception e) {
							}
							declara.setContainerCode(code);
							try {
								code1 = strs[i].trim().substring(12, 16);
							} catch (Exception e) {
							}
							SrtJzx srt = new SrtJzx();
							srt = dbimportDao.getSrtJzx(code1);
							declara.setSrtJzx(srt);
							try {
								code2 = strs[i].trim().substring(16,
										strs[i].trim().length());
							} catch (Exception e) {
							}
							SrtTj tj = new SrtTj();
							tj = dbimportDao.getSrtTj(code2);
							declara.setSrttj(tj);
							declara.setCompany(CommonUtils.getCompany());
							declara.setBaseCustomsDeclaration(head);
							dbimportDao.SaveObject(declara);
						} catch (Exception e) {
							System.out.println("错误：" + e);
						}
					}
				}
			} catch (Exception e) {
			}
			getst();
			rsImg = st.executeQuery("select * from hg_import_dddetails where"
					+ " customcode = '" + rsHead.getString("idd02")
					+ "' and importnumber = '"
					+ rsHead.getString("importnumber") + "'");
			while (rsImg.next()) {
				try {
					LevyMode tempLevyMode1 = new LevyMode();
					Uses tempUses1 = new Uses();
					CustomsDeclarationCommInfo customsDeclarationCommInfo = new CustomsDeclarationCommInfo();// 进口报关单明细表
					customsDeclarationCommInfo.setBaseCustomsDeclaration(head);
					try {
						tempUses1 = dbimportDao.FindUses("", rsImg
								.getString("imd15"));// 用途
						customsDeclarationCommInfo.setUses(tempUses1);
						Complex tempComplex = new Complex();
						tempComplex = dbimportDao.Findbycodecomplex(rsImg
								.getString("imd01"));// 商品信息
						customsDeclarationCommInfo.setComplex(tempComplex);
					} catch (Exception e) {
					}
					String snum = (rsImg.getString("imd13") == null || ""
							.equals(rsImg.getString("imd13"))) ? "0" : rsImg
							.getString("imd13");
					String lsNo = (rsImg.getString("imd00") == null || ""
							.equals(rsImg.getString("imd00"))) ? "0" : rsImg
							.getString("imd00");
					customsDeclarationCommInfo.setCommSerialNo(Integer
							.valueOf(snum));// 序号
					customsDeclarationCommInfo.setSerialNumber(Integer
							.valueOf(lsNo));// 商品流水号

					customsDeclarationCommInfo.setCommName(rsImg
							.getString("imd02") == null ? null : rsImg
							.getString("imd02").trim());// 商品名称
					customsDeclarationCommInfo.setCommSpec(rsImg
							.getString("imd03") == null ? null : rsImg
							.getString("imd03").trim());// 商品规格
					try {
						Unit unit = new Unit();// 单位
						unit = dbimportDao.Findbynameunit(rsImg
								.getString("imd05"));
						customsDeclarationCommInfo.setUnit(unit);
						Unit unit1 = new Unit();// 第一法定单位
						unit1 = dbimportDao.Findbynameunit(rsImg
								.getString("imd07"));
						customsDeclarationCommInfo.setLegalUnit(unit1);
						Unit unit2 = new Unit();// 第二法定单位
						unit2 = dbimportDao.Findbynameunit(rsImg
								.getString("imd071"));
						customsDeclarationCommInfo.setSecondLegalUnit(unit2);
					} catch (Exception e) {
					}
					customsDeclarationCommInfo.setMaterielNo(rsImg
							.getString("imd16")); // 货号
					customsDeclarationCommInfo.setVersion(rsImg
							.getString("imd17"));// 版本

					customsDeclarationCommInfo
							.setCommAmount(formatStrDouble(rsImg
									.getString("imd04")));
					Double comamount = formatStrDouble(rsImg.getString("imd04"));
					customsDeclarationCommInfo.setFirstAmount(comamount
							* formatStrDouble(rsImg.getString("imd06")));
					customsDeclarationCommInfo.setSecondAmount(comamount
							* formatStrDouble(rsImg.getString("imd061")));
					customsDeclarationCommInfo
							.setCommUnitPrice(formatStrDouble(rsImg
									.getString("imd20")));
					customsDeclarationCommInfo
							.setCommTotalPrice(formatStrDouble(rsImg
									.getString("imd21")));
					try {
						Country country = new Country();
						country = dbimportDao.Findbynamecountry(rsImg
								.getString("imd09"));// 最终目的产国
						customsDeclarationCommInfo.setCountry(country);

						tempLevyMode1 = dbimportDao.FindLevyMode("", rsImg
								.getString("imd14"));// 减免方式
						customsDeclarationCommInfo.setLevyMode(tempLevyMode1);
					} catch (Exception e) {
					}
					customsDeclarationCommInfo
							.setNetWeight(formatStrDouble(rsImg
									.getString("imd22")));
					customsDeclarationCommInfo
							.setGrossWeight(formatStrDouble(rsImg
									.getString("imd23")));
					try {
						Integer jianshu = (rsImg.getString("imd24") == null || ""
								.equals(rsImg.getString("imd24"))) ? 0
								: Integer.valueOf(rsImg.getString("imd24"));
						customsDeclarationCommInfo.setPieces(jianshu);// 件(箱)数
					} catch (Exception e) {
					}
					customsDeclarationCommInfo.setCompany(CommonUtils
							.getCompany());
					dbimportDao.SaveObject(customsDeclarationCommInfo);
				} catch (Exception e) {
					System.out.println("Error_detail:" + rsImg.getRow() + e);
					continue;
				}
			}
		}
	}

	/**
	 *  出口报关单
	 * @throws SQLException
	 */
	public void insertCKBGHead_bcus() throws SQLException {
		getst();
		ResultSet rsHead = null;
		ResultSet rsImg = null;
		rsHead = st
				.executeQuery("select * from hg_export_dd order by edd02,exportnumber");
		while (rsHead.next()) {
			CustomsDeclaration head = new CustomsDeclaration();
			head.setImpExpFlag(ImpExpFlag.EXPORT);// 出口标志
			head.setImpExpType(getType(rsHead.getString("exporttype")));// 出口类型
			String seqnum = rsHead.getString("exportnumber") == null
					|| "".equals(rsHead.getString("exportnumber")) ? "0"
					: rsHead.getString("exportnumber");
			head.setSerialNumber(Integer.valueOf(seqnum));// 大单流水号

			head.setEmsHeadH2k(rsHead.getString("EDD02"));// 电子帐册号码(电子帐册手册编号)
			try {
				head.setTradeCode(dbimportDao// 经营单位代码
						.FindBriefCodeByName(rsHead.getString("eDD05")));
			} catch (Exception e) {
			}
			head.setTradeName(rsHead.getString("eDD05"));// 经营单位名称
			try {
				head.setPreCustomsDeclarationCode(organizeStr(rsHead
						.getString("edd31")));// 预录入号
			} catch (Exception e) {

			}
			head.setUnificationCode(rsHead.getString("edd66"));// 统一编号

			if (rsHead.getString("ifok") != null
					&& rsHead.getString("ifok").equals("Y"))
				head.setEffective(new Boolean(true));// 是否生效
			else
				head.setEffective(new Boolean(false));// 是否生效
			head.setCancel(new Boolean(false));// 是否作废
			Customs tempCustoms = new Customs();
			try {
				tempCustoms = dbimportDao.Findbynamecustoms(rsHead
						.getString("edd01"));
				head.setCustoms(tempCustoms); // 进/出口岸
			} catch (Exception e) {
			}
			head.setCustomsDeclarationCode(rsHead.getString("edd32"));// 报关单号
			head.setCustomsDeclarationCodeinHouse(rsHead.getString("edd67"));// 入库报关单号

			try {
				head.setImpExpDate(rsHead.getDate("edd03"));// 出口日期
				head.setDeclarationDate(rsHead.getDate("edd04"));// 申报日期
				head.setMachCode(dbimportDao.FindBriefCodeByName(rsHead
						.getString("edd09"))); // 收货单位代码
			} catch (Exception e) {
			}
			head.setMachName(rsHead.getString("edd09")); // 收货单位名称

			Transf tempTransf = new Transf();
			try {
				tempTransf = dbimportDao.Findbynametransf(rsHead
						.getString("edd06"));
				head.setTransferMode(tempTransf);// 运输方式
			} catch (Exception e) {
			}
			head.setConveyance(rsHead.getString("edd07"));// 运输工具
			head.setBillOfLading(rsHead.getString("edd08"));// 提运单号
			try {
				Trade tempTrade = new Trade();
				tempTrade = dbimportDao.Findbynametrade(rsHead
						.getString("edd10"));
				head.setTradeMode(tempTrade);// 贸易方式

			LevyKind tempLevyKind = new LevyKind();
				tempLevyKind = dbimportDao.FindLevyKind("", rsHead
						.getString("edd11"));
				head.setLevyKind(tempLevyKind);// 征免性质
				if (rsHead.getString("edd12") != null
						&& GenericValidator.isDouble(rsHead.getString("edd12"))) {
					head.setPerTax(rsHead.getDouble("edd12"));// 征税比例
				}
			} catch (Exception e) {
			}
			// /head.setPerTax(formatStrDouble(rsHead.getString("edd12")));//
			// 征税比例
			head.setLicense(rsHead.getString("edd13"));// 许可证编号
			Country tempCountry = new Country();// 运抵国
			try {
				tempCountry = dbimportDao.Findbynamecountry(rsHead
						.getString("edd14"));
				head.setCountryOfLoadingOrUnloading(tempCountry);
			District tempDistrict = new District();// 境内货源地
				tempDistrict = dbimportDao.FindDistrict("", rsHead
						.getString("edd16"));
				head.setDomesticDestinationOrSource(tempDistrict);
			PortLin tempPortLin = new PortLin();// 指运港口
				tempPortLin = dbimportDao.FindPortLin("", rsHead
						.getString("edd15"));
				head.setPortOfLoadingOrUnloading(tempPortLin);
			} catch (Exception e) {

			}
			head.setAuthorizeFile(rsHead.getString("edd17"));// 合同协议号（电子备案进出口合同协议号）
			head.setContract(rsHead.getString("edd22"));// 合同协议号
			Transac tempTransac = new Transac();// 成交方式
			try {
				tempTransac = dbimportDao.FindTransac("", rsHead
						.getString("edd18"));
				head.setTransac(tempTransac);
			} catch (Exception e) {

			}
			try {
				if ((rsHead.getString("edd191") != null)// 运费
						&& (!rsHead.getString("edd191").trim().equals("")))
					head.setFreightageType(Integer.valueOf(rsHead
							.getString("edd191")));// 运费类型
				if ((rsHead.getString("edd19") != null)
						&& (!rsHead.getString("edd19").trim().equals("")))
					head.setFreightage(rsHead.getDouble("edd19"));

				if ((rsHead.getString("edd201") != null)
						&& (!rsHead.getString("edd201").trim().equals("")))
					head.setInsuranceType(Integer.valueOf(rsHead
							.getString("edd201")));// 保险费类型
				if ((rsHead.getString("edd20") != null)
						&& (!rsHead.getString("edd20").trim().equals("")))
					head.setInsurance(rsHead.getDouble("edd20"));// 保险费

				if ((rsHead.getString("edd211") != null)
						&& (!rsHead.getString("edd211").trim().equals("")))
					head.setIncidentalExpensesType(Integer.valueOf(rsHead
							.getString("edd211")));// 杂费类型
				if ((rsHead.getString("edd21") != null)
						&& (!rsHead.getString("edd21").trim().equals("")))
					head.setIncidentalExpenses(rsHead.getDouble("edd21"));// 杂费

				if (GenericValidator.isDouble(rsHead.getString("edd23")))
					head.setCommodityNum(Convfloattoint(rsHead
							.getString("edd23")));// 件数
			} catch (Exception e) {

			}
			Wrap tempWrap = new Wrap();
			try {
				tempWrap = dbimportDao.FindWrap("", rsHead.getString("edd24"));
				head.setWrapType(tempWrap); // 包装种类
				if ((rsHead.getString("edd25") != null)
						&& (!rsHead.getString("edd25").trim().equals("")))
					head.setGrossWeight(rsHead.getDouble("edd25"));// 毛重
				if ((rsHead.getString("edd26") != null)
						&& (!rsHead.getString("edd26").trim().equals("")))
					head.setNetWeight(rsHead.getDouble("edd26"));// 净重
			} catch (Exception e) {
			}
			head.setContainerNum(rsHead.getString("edd27"));// 集装箱号
			head.setAttachedBill(rsHead.getString("edd37"));// 依附单据

			try {
				Brief tempbrief = new Brief();
				Company tempBrief = new Company();
				tempBrief = dbimportDao.Findbynamecompany(rsHead
						.getString("edd34"));
				head.setDeclarationCompany(tempBrief);// 申报单位

				Curr tempCurr = new Curr();
				tempCurr = dbimportDao.Findbych_encurr("", rsHead
						.getString("edd33"));
				head.setCurrency(tempCurr);// 币别
			head.setExchangeRate(formatStrDouble(rsHead.getString("edd65")));
			AclUser tempAclUser = new AclUser();
				tempAclUser = dbimportDao.FindbyloginnameAclUser(rsHead
						.getString("edd35"));// 录入员
				head.setCreater(tempAclUser);
				ScmCoc tempScmCoc = new ScmCoc();// 客户
				tempScmCoc = dbimportDao.Findbynamescmcoc(rsHead
						.getString("edd091"));
				head.setCustomer(tempScmCoc);
				tempCustoms = dbimportDao.Findbynamecustoms(rsHead
						.getString("edd39"));
				head.setDeclarationCustoms(tempCustoms);// 报送海关
				PreDock tempPreDock = new PreDock();// 码头
				tempPreDock = dbimportDao.FindPreDock("", rsHead
						.getString("edd41"));
				head.setPredock(tempPreDock);
			} catch (Exception e) {

			}
			head.setMemos(rsHead.getString("edd30"));// 备注
			head.setCertificateCode(rsHead.getString("edd70")); // 备注二
			head.setCustomser(rsHead.getString("eDD43"));// 报关员
			head.setTelephone(rsHead.getString("eDD44"));// 报关员电话
			head.setBarCode(rsHead.getString("eDD42"));// 条形码
			try {
				Brief brief = new Brief();
				brief = dbimportDao.Findbynamebrief(rsHead.getString("edd29"));
				head.setManufacturer(brief);// 生产厂商
			} catch (Exception e) {

			}
//			head.setAllContainerNum(rsHead.getString("edd40"));// 所有集装箱号
			if (rsHead.getString("edd69") != null
					&& rsHead.getString("edd69").equals("0")) { // 有无纸报关
				head.setIsNoBumf("O");
			} else {
				head.setIsNoBumf("W");
			}
			head.setOverseasConveyanceCode(rsHead.getString("eDD49"));// 境外运输工具编号
			head.setOverseasConveyanceName(rsHead.getString("eDD50"));// 境外运输工具名字
			head.setOverseasConveyanceFlights(rsHead.getString("eDD51"));// 境外运输工具航次
			head.setOverseasConveyanceBillOfLading(rsHead.getString("eDD52"));// 境外运输工具提单号
			Transf transf = new Transf();
			try {
				transf = dbimportDao
						.Findbynametransf(rsHead.getString("eDD56"));// 境内运输方式
				head.setDomesticTransferMode(transf);
			} catch (Exception e) {
			}
			head.setDomesticConveyanceCode(rsHead.getString("eDD53"));// 境内运输工具编号
			head.setDomesticConveyanceName(rsHead.getString("eDD54"));// 境内运输工具名字
			head.setDomesticConveyanceFlights(rsHead.getString("eDD55"));// 境内运输工具航次
			head.setCompany(CommonUtils.getCompany());
			if (rsHead.getString("edd48") != null
					&& rsHead.getString("edd48").equals("Y")) { // 是否检查通过
				head.setIsCheck(new Boolean(true));
			} else {
				head.setIsCheck(new Boolean(false));
			}
			// 结汇方式
//			BalanceMode obj = new BalanceMode();
//			obj = dbimportDao.FindbyBalanceMode("", rsHead.getString("edd12"));
//			head.setBalanceMode(obj);

			head = dbimportDao.SaveCustomsDeclaration(head);
			try {
				String[] strs = null;
				String s = rsHead.getString("edd40");
				if (s != null && !s.trim().equals("")) {
					strs = s.split(String.valueOf((char) 44));
					for (int i = 0; i < strs.length; i++) {
						CustomsDeclarationContainer declara = new CustomsDeclarationContainer();
						try {
							String code = "";
							String code1 = "";
							String code2 = "";
							try {
								code = strs[i].trim().substring(0, 11);
							} catch (Exception e) {
							}
							declara.setContainerCode(code);
							try {
								code1 = strs[i].trim().substring(12, 16);
							} catch (Exception e) {
							}
							SrtJzx srt = new SrtJzx();
							srt = dbimportDao.getSrtJzx(code1);
							declara.setSrtJzx(srt);
							try {
								code2 = strs[i].trim().substring(16,
										strs[i].trim().length());
							} catch (Exception e) {
							}
							SrtTj tj = new SrtTj();
							tj = dbimportDao.getSrtTj(code2);
							declara.setSrttj(tj);
							declara.setCompany(CommonUtils.getCompany());
							declara.setBaseCustomsDeclaration(head);
							dbimportDao.SaveObject(declara);
						} catch (Exception e) {
							System.out.println("错误：" + e);
						}
					}
				}
			} catch (Exception e) {

			}
			getst();
			rsImg = st.executeQuery("select * from hg_export_dddetails where"
					+ " customcode = '" + rsHead.getString("edd02")
					+ "' and exportnumber = '"
					+ rsHead.getString("exportnumber") + "'");
			while (rsImg.next()) {
				try {
					LevyMode tempLevyMode1 = new LevyMode();
					Uses tempUses1 = new Uses();
					CustomsDeclarationCommInfo customsDeclarationCommInfo = new CustomsDeclarationCommInfo();// 进口报关单明细表
					customsDeclarationCommInfo.setBaseCustomsDeclaration(head);
					try {
						tempUses1 = dbimportDao.FindUses("", rsImg
								.getString("emd15"));// 用途
						customsDeclarationCommInfo.setUses(tempUses1);

						Complex tempComplex = new Complex();
						tempComplex = dbimportDao.Findbycodecomplex(rsImg
								.getString("emd01"));// 商品信息
						customsDeclarationCommInfo.setComplex(tempComplex);

					} catch (Exception e) {
					}

					String num = rsImg.getString("emd13") == null
							|| "".equals(rsImg.getString("emd13")) ? "0"
							: rsImg.getString("emd13");
					String lsno = rsImg.getString("emd00") == null
							|| "".equals(rsImg.getString("emd00")) ? "0"
							: rsImg.getString("emd00");
					customsDeclarationCommInfo.setCommSerialNo(Integer
							.valueOf(num));// 序号
					customsDeclarationCommInfo.setSerialNumber(Integer
							.valueOf(lsno));// 商品流水号

					customsDeclarationCommInfo.setCommName(rsImg
							.getString("emd02") == null ? null : rsImg
							.getString("emd02").trim());// 商品名称
					customsDeclarationCommInfo.setCommSpec(rsImg
							.getString("emd03") == null ? null : rsImg
							.getString("emd03").trim());// 商品规格

					Unit unit = new Unit();// 单位
					try {
						unit = dbimportDao.Findbynameunit(rsImg
								.getString("emd05"));
						customsDeclarationCommInfo.setUnit(unit);
						Unit unit1 = new Unit();// 第一法定单位
						unit1 = dbimportDao.Findbynameunit(rsImg
								.getString("emd07"));
						customsDeclarationCommInfo.setLegalUnit(unit1);
						Unit unit2 = new Unit();// 第二法定单位
						unit2 = dbimportDao.Findbynameunit(rsImg
								.getString("emd071"));
						customsDeclarationCommInfo.setSecondLegalUnit(unit2);
					} catch (Exception e) {

					}

					customsDeclarationCommInfo.setMaterielNo(rsImg
							.getString("emd16")); // 货号
					customsDeclarationCommInfo.setVersion(rsImg
							.getString("emd17"));// 版本

					customsDeclarationCommInfo
							.setCommAmount(formatStrDouble(rsImg
									.getString("emd04")));
					Double comamount = formatStrDouble(rsImg.getString("emd04"));
					customsDeclarationCommInfo.setFirstAmount(comamount
							* formatStrDouble(rsImg.getString("emd06")));
					customsDeclarationCommInfo.setSecondAmount(comamount
							* formatStrDouble(rsImg.getString("emd061")));

					customsDeclarationCommInfo
							.setCommUnitPrice(formatStrDouble(rsImg
									.getString("emd20")));
					customsDeclarationCommInfo
							.setCommTotalPrice(formatStrDouble(rsImg
									.getString("emd21")));
					try {
						Country country = new Country();
						country = dbimportDao.Findbynamecountry(rsImg
								.getString("emd09"));// 最终目的产国
						customsDeclarationCommInfo.setCountry(country);

						tempLevyMode1 = dbimportDao.FindLevyMode("", rsImg
								.getString("emd14"));// 减免方式
						customsDeclarationCommInfo.setLevyMode(tempLevyMode1);
					} catch (Exception e) {

					}
					// if
					// (GenericValidator.isDouble(rsImg.getString("emd22")))//
					// 净重
					customsDeclarationCommInfo
							.setNetWeight(formatStrDouble(rsImg
									.getString("emd22")));

					// if
					// (GenericValidator.isDouble(rsImg.getString("emd23")))//
					// 毛重
					customsDeclarationCommInfo
							.setGrossWeight(formatStrDouble(rsImg
									.getString("emd23")));
					try {
						Integer jianshu = (rsImg.getString("emd24") == null || ""
								.equals(rsImg.getString("emd24"))) ? 0
								: Integer.valueOf(rsImg.getString("emd24"));
						customsDeclarationCommInfo.setPieces(jianshu);// 件数
					} catch (Exception e) {

					}
					customsDeclarationCommInfo.setCompany(CommonUtils
							.getCompany());
					dbimportDao.SaveObject(customsDeclarationCommInfo);
				} catch (Exception e) {
					System.out.println("Error_detail:" + rsImg.getRow() + e);
					continue;
				}
			}
		}
	}

	/**
	 *  特殊报关单
	 * @param emsNo
	 * @throws SQLException
	 */
	public void insertTSBGHead_bcus(String emsNo) throws SQLException {
		getst();
		ResultSet rsHead = null;
		ResultSet rsImg = null;
		String sql = "";
		if (emsNo != null && !emsNo.equals("")) {
			sql = "select * from HG_Other_DD  where odd02 = '" + emsNo
					+ "' order by odd02,OtherNumber";
		} else {
			sql = "select * from HG_Other_DD  order by odd02,OtherNumber";
		}
		rsHead = st.executeQuery(sql);
		while (rsHead.next()) {
			CustomsDeclaration head = new CustomsDeclaration();
			head.setImpExpFlag(ImpExpFlag.SPECIAL);// 特殊标志
			head.setImpExpType(getType(rsHead.getString("OtherType")));// 出口类型
			head.setSerialNumber(Integer.valueOf(rsHead
					.getString("OtherNumber")));// 大单流水号
			head.setEmsHeadH2k(rsHead.getString("odd02"));// 电子帐册号码(电子帐册手册编号)
			head.setTradeCode(dbimportDao// 经营单位代码
					.FindBriefCodeByName(rsHead.getString("oDD05")));
			head.setTradeName(rsHead.getString("oDD05"));// 经营单位名称
			head.setPreCustomsDeclarationCode(organizeStr(rsHead
					.getString("odd31")));// 预录入号
			// head.setUnificationCode(rsHead.getString("edd66"));// 统一编号
			// head.setTempPreCode();
			if (rsHead.getString("ifok") != null
					&& rsHead.getString("ifok").equals("Y"))
				head.setEffective(new Boolean(true));// 是否生效
			else
				head.setEffective(new Boolean(false));// 是否生效
			head.setCancel(new Boolean(false));// 是否作废
			Customs tempCustoms = new Customs();
			tempCustoms = dbimportDao.Findbynamecustoms(rsHead
					.getString("odd01"));
			head.setCustoms(tempCustoms); // 进/出口岸
			head.setCustomsDeclarationCode(rsHead.getString("odd32"));// 报关单号
			// head.setCustomsDeclarationCodeinHouse(rsHead.getString("edd67"));//入库报关单号
			head.setImpExpDate(rsHead.getDate("odd03"));// 出口日期
			head.setDeclarationDate(rsHead.getDate("odd04"));// 申报日期
			head.setMachCode(dbimportDao.FindBriefCodeByName(rsHead
					.getString("odd09"))); // 收货单位代码
			head.setMachName(rsHead.getString("odd09")); // 收货单位名称

			Transf tempTransf = new Transf();
			tempTransf = dbimportDao
					.Findbynametransf(rsHead.getString("odd06"));
			head.setTransferMode(tempTransf);// 运输方式
			head.setConveyance(rsHead.getString("odd07"));// 运输工具
			head.setBillOfLading(rsHead.getString("odd08"));// 提运单号
			Trade tempTrade = new Trade();
			tempTrade = dbimportDao.Findbynametrade(rsHead.getString("odd10"));
			head.setTradeMode(tempTrade);// 贸易方式
			LevyKind tempLevyKind = new LevyKind();
			tempLevyKind = dbimportDao.FindLevyKind("", rsHead
					.getString("odd11"));
			head.setLevyKind(tempLevyKind);// 征免性质
			if (GenericValidator.isDouble(rsHead.getString("odd121"))) {
				head.setPerTax(rsHead.getDouble("odd121"));// 征税比例
			}
			head.setLicense(rsHead.getString("odd13"));// 许可证编号
			Country tempCountry = new Country();// 运抵国
			tempCountry = dbimportDao.Findbynamecountry(rsHead
					.getString("odd14"));
			head.setCountryOfLoadingOrUnloading(tempCountry);
			District tempDistrict = new District();// 境内货源地
			tempDistrict = dbimportDao.FindDistrict("", rsHead
					.getString("odd16"));
			head.setDomesticDestinationOrSource(tempDistrict);
			PortLin tempPortLin = new PortLin();// 指运港口
			tempPortLin = dbimportDao
					.FindPortLin("", rsHead.getString("odd15"));
			head.setPortOfLoadingOrUnloading(tempPortLin);
			head.setAuthorizeFile(rsHead.getString("odd17"));// 合同协议号（电子备案进出口合同协议号）
			head.setContract(rsHead.getString("odd22"));// 合同协议号
			Transac tempTransac = new Transac();// 成交方式
			tempTransac = dbimportDao
					.FindTransac("", rsHead.getString("odd18"));
			head.setTransac(tempTransac);
			if ((rsHead.getString("odd191") != null)// 运费
					&& (!rsHead.getString("odd191").trim().equals("")))
				head.setFreightageType(Integer.valueOf(rsHead
						.getString("odd191")));// 运费类型
			if ((rsHead.getString("odd19") != null)
					&& (!rsHead.getString("odd19").trim().equals("")))
				head.setFreightage(rsHead.getDouble("odd19"));

			if ((rsHead.getString("odd201") != null)
					&& (!rsHead.getString("odd201").trim().equals("")))
				head.setInsuranceType(Integer.valueOf(rsHead
						.getString("odd201")));// 保险费类型
			if ((rsHead.getString("odd20") != null)
					&& (!rsHead.getString("odd20").trim().equals("")))
				head.setInsurance(rsHead.getDouble("odd20"));// 保险费

			if ((rsHead.getString("odd211") != null)
					&& (!rsHead.getString("odd211").trim().equals("")))
				head.setIncidentalExpensesType(Integer.valueOf(rsHead
						.getString("odd211")));// 杂费类型
			if ((rsHead.getString("odd21") != null)
					&& (!rsHead.getString("odd21").trim().equals("")))
				head.setIncidentalExpenses(rsHead.getDouble("odd21"));// 杂费

			if (GenericValidator.isDouble(rsHead.getString("odd23")))
				head.setCommodityNum(Convfloattoint(rsHead.getString("odd23")));// 件数
			Wrap tempWrap = new Wrap();
			tempWrap = dbimportDao.FindWrap("", rsHead.getString("odd24"));
			head.setWrapType(tempWrap); // 包装种类
			if ((rsHead.getString("odd25") != null)
					&& (!rsHead.getString("odd25").trim().equals("")))
				head.setGrossWeight(rsHead.getDouble("odd25"));// 毛重
			if ((rsHead.getString("odd26") != null)
					&& (!rsHead.getString("odd26").trim().equals("")))
				head.setNetWeight(rsHead.getDouble("odd26"));// 净重

			head.setContainerNum(rsHead.getString("odd27"));// 集装箱号
			head.setAttachedBill(rsHead.getString("odd28"));// 依附单据
			/*
			 * Uses tempUses = new Uses();// 用途 tempUses =
			 * dbimportDao.FindUses("", rsHead.getString("edd29"));
			 * head.setUses(tempUses);
			 */
			Company tempBrief = new Company();
			tempBrief = dbimportDao
					.Findbynamecompany(rsHead.getString("odd34"));
			head.setDeclarationCompany(tempBrief);// 申报单位

			Curr tempCurr = new Curr();
			tempCurr = dbimportDao.Findbych_encurr("", rsHead
					.getString("odd33"));
			head.setCurrency(tempCurr);// 币别
			if ((rsHead.getString("odd65") != null)// 汇率
					&& (!rsHead.getString("odd65").trim().equals("")))
				head.setExchangeRate(rsHead.getDouble("odd65"));

			AclUser tempAclUser = new AclUser();
			tempAclUser = dbimportDao.FindbyloginnameAclUser(rsHead
					.getString("odd35"));// 录入员
			head.setCreater(tempAclUser);
			ScmCoc tempScmCoc = new ScmCoc();// 客户
			tempScmCoc = dbimportDao.Findbynamescmcoc(rsHead
					.getString("odd091"));
			head.setCustomer(tempScmCoc);

			tempCustoms = dbimportDao.Findbynamecustoms(rsHead
					.getString("odd39"));
			head.setDeclarationCustoms(tempCustoms);// 报送海关
			PreDock tempPreDock = new PreDock();// 码头
			tempPreDock = dbimportDao
					.FindPreDock("", rsHead.getString("odd41"));
			head.setPredock(tempPreDock);
			head.setMemos(rsHead.getString("odd30"));// 备注
			// head.setCertificateCode(rsHead.getString("edd70")); //备注二
			head.setCustomser(rsHead.getString("oDD43"));// 报关员
			head.setTelephone(rsHead.getString("oDD44"));// 报关员电话
			head.setBarCode("");// 条形码
			Brief brief = new Brief();
			brief = dbimportDao.Findbynamebrief(rsHead.getString("odd29"));
			head.setManufacturer(brief);// 生产厂商
			head.setAllContainerNum(rsHead.getString("odd40"));// 所有集装箱号
			/*
			 * if (rsHead.getString("edd69")!=null &&
			 * rsHead.getString("edd69").equals("0")) { // 有无纸报关
			 * head.setIsNoBumf("O"); } else { head.setIsNoBumf("W"); }
			 */
			head.setOverseasConveyanceCode(rsHead.getString("oDD49"));// 境外运输工具编号
			head.setOverseasConveyanceName(rsHead.getString("oDD50"));// 境外运输工具名字
			head.setOverseasConveyanceFlights(rsHead.getString("oDD51"));// 境外运输工具航次
			head.setOverseasConveyanceBillOfLading(rsHead.getString("oDD52"));// 境外运输工具提单号
			Transf transf = new Transf();
			transf = dbimportDao.Findbynametransf(rsHead.getString("oDD56"));// 境内运输方式
			head.setDomesticTransferMode(transf);

			head.setDomesticConveyanceCode(rsHead.getString("oDD53"));// 境内运输工具编号
			head.setDomesticConveyanceName(rsHead.getString("oDD54"));// 境内运输工具名字
			head.setDomesticConveyanceFlights(rsHead.getString("oDD55"));// 境内运输工具航次
			head.setCompany(CommonUtils.getCompany());
			if (rsHead.getString("odd48") != null
					&& rsHead.getString("odd48").equals("Y")) { // 是否检查通过
				head.setIsCheck(new Boolean(true));
			} else {
				head.setIsCheck(new Boolean(false));
			}
			BalanceMode obj = new BalanceMode();
			obj = dbimportDao.FindbyBalanceMode("", rsHead.getString("odd12"));
			head.setBalanceMode(obj);
			head = dbimportDao.SaveCustomsDeclaration(head);

			String[] strs = null;
			String s = rsHead.getString("odd40");
			if (s != null && !s.trim().equals("")) {
				strs = s.split(String.valueOf((char) 44));
				for (int i = 0; i < strs.length; i++) {
					CustomsDeclarationContainer declara = new CustomsDeclarationContainer();
					try {
						String code = "";
						String code1 = "";
						String code2 = "";
						try {
							code = strs[i].trim().substring(0, 11);
						} catch (Exception e) {
						}
						declara.setContainerCode(code);
						try {
							code1 = strs[i].trim().substring(12, 16);
						} catch (Exception e) {
						}
						SrtJzx srt = new SrtJzx();
						srt = dbimportDao.getSrtJzx(code1);
						declara.setSrtJzx(srt);
						try {
							code2 = strs[i].trim().substring(16,
									strs[i].trim().length());
						} catch (Exception e) {
						}
						SrtTj tj = new SrtTj();
						tj = dbimportDao.getSrtTj(code2);
						declara.setSrttj(tj);
						declara.setCompany(CommonUtils.getCompany());
						declara.setBaseCustomsDeclaration(head);
						dbimportDao.SaveObject(declara);
					} catch (Exception e) {
						System.out.println("错误：" + e);
					}
				}
			}
			getst();
			rsImg = st.executeQuery("select * from HG_Other_DDDetails where"
					+ " OtherNumber = '" + rsHead.getString("OtherNumber")
					+ "'");
			while (rsImg.next()) {
				try {
					LevyMode tempLevyMode1 = new LevyMode();
					Uses tempUses1 = new Uses();
					CustomsDeclarationCommInfo customsDeclarationCommInfo = new CustomsDeclarationCommInfo();// 特殊报关单明细表
					customsDeclarationCommInfo.setBaseCustomsDeclaration(head);
					tempUses1 = dbimportDao.FindUses("", rsImg
							.getString("omd15"));// 用途
					customsDeclarationCommInfo.setUses(tempUses1);
					Complex tempComplex = new Complex();
					tempComplex = dbimportDao.Findbycodecomplex(rsImg
							.getString("omd01"));// 商品信息
					customsDeclarationCommInfo.setCommSerialNo(Integer
							.valueOf(rsImg.getString("omd13")));// 序号
					customsDeclarationCommInfo.setSerialNumber(Integer
							.valueOf(rsImg.getString("omd00")));// 商品流水号
					customsDeclarationCommInfo.setComplex(tempComplex);
					customsDeclarationCommInfo.setCommName(rsImg
							.getString("omd02") == null ? null : rsImg
							.getString("omd02").trim());// 商品名称
					customsDeclarationCommInfo.setCommSpec(rsImg
							.getString("omd03") == null ? null : rsImg
							.getString("omd03").trim());// 商品规格

					Unit unit = new Unit();// 单位
					unit = dbimportDao.Findbynameunit(rsImg.getString("omd05"));
					customsDeclarationCommInfo.setUnit(unit);
					Unit unit1 = new Unit();// 第一法定单位
					unit1 = dbimportDao
							.Findbynameunit(rsImg.getString("omd07"));
					customsDeclarationCommInfo.setLegalUnit(unit1);
					Unit unit2 = new Unit();// 第二法定单位
					unit2 = dbimportDao.Findbynameunit(rsImg
							.getString("omd071"));
					customsDeclarationCommInfo.setSecondLegalUnit(unit2);

					customsDeclarationCommInfo.setMaterielNo(rsImg
							.getString("omd16")); // 货号
					customsDeclarationCommInfo.setVersion(rsImg
							.getString("omd18"));// 版本

					if (GenericValidator.isDouble(rsImg.getString("omd04")))// 数量
						customsDeclarationCommInfo.setCommAmount(rsImg
								.getDouble("omd04"));
					double comamount = StrTodou(rsImg.getString("omd04"));
					if (GenericValidator.isDouble(rsImg.getString("omd06"))) // 第一法定比例因子
						customsDeclarationCommInfo.setFirstAmount(Double
								.valueOf(comamount
										* StrTodou(rsImg.getString("omd06"))));
					if (GenericValidator.isDouble(rsImg.getString("omd061"))) // 第二法定比例因子
						customsDeclarationCommInfo.setSecondAmount(Double
								.valueOf(comamount
										* StrTodou(rsImg.getString("omd061"))));
					if (GenericValidator.isDouble(rsImg.getString("omd20")))// 申报单价
						customsDeclarationCommInfo.setCommUnitPrice(Double
								.valueOf(rsImg.getString("omd20")));
					if (GenericValidator.isDouble(rsImg.getString("omd21")))// 总价格
						customsDeclarationCommInfo.setCommTotalPrice(Double
								.valueOf(rsImg.getString("omd21")));
					System.out.println("55555555555");
						Country country = new Country();
						country = dbimportDao.Findbynamecountry(rsImg
								.getString("omd09"));// 最终目的产国
						customsDeclarationCommInfo.setCountry(country);

						tempLevyMode1 = dbimportDao.FindLevyMode("", rsImg
								.getString("omd14"));// 减免方式
						customsDeclarationCommInfo.setLevyMode(tempLevyMode1);

					if (GenericValidator.isDouble(rsImg.getString("omd08")))// 净重
						customsDeclarationCommInfo.setNetWeight(Double
								.valueOf(rsImg.getString("omd08")));
					System.out.println("7777777777");
					/*
					 * if
					 * (GenericValidator.isDouble(rsImg.getString("emd23")))//
					 * 毛重
					 * customsDeclarationCommInfo.setGrossWeight(rsImg.getDouble
					 * ("emd23"));
					 */

					/*
					 * if (GenericValidator.isInt(rsImg.getString("emd24")))
					 * customsDeclarationCommInfo.setPieces(Integer
					 * .valueOf(rsImg.getString("emd24")));// 件数
					 */customsDeclarationCommInfo.setCompany(CommonUtils
							.getCompany());

					dbimportDao.SaveObject(customsDeclarationCommInfo);
				} catch (Exception e) {
					System.out.println("Error_detail:" + rsImg.getRow() + e);
					continue;
				}
			}
		}
	}

	/**
	 * 插入海关帐单据头
	 * @throws SQLException
	 */
	public void insertDJHead() throws SQLException {
		getst();
		ResultSet rs;
		rs = st.executeQuery("select * from hgz_dj order by djno");
		while (rs.next()) {
			BillMasterMateriel obj = new BillMasterMateriel();
			BillType billType = new BillType();
			billType = dbimportDao.Findbynamebilltype(rs.getString("dj02"));
			obj.setBillType(billType);
			obj.setBillNo(rs.getString("dj01"));
			if (rs.getString("dj07") != null
					&& rs.getString("dj07").equals("Y")) {
				obj.setIsValid(new Boolean(true));
			} else
				obj.setIsValid(new Boolean(false));
			obj.setValidDate(rs.getDate("dj03"));
			ScmCoc scmCoc = new ScmCoc();
			scmCoc = dbimportDao.Findbynamescmcoc(rs.getString("dj04"));
			obj.setScmCoc(scmCoc);
			obj.setTempNo(rs.getString("djno"));
			try {
				dbimportDao.SaveObject(obj);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (rs != null)
			rs.close();
	}

	/**
	 * 导入 海关帐单据体
	 * @throws SQLException
	 */
	public void insertDJDetail() throws SQLException {
		getst();
		ResultSet rs;
		rs = st
				.executeQuery("select d.*,h.dj05 from (select a.*,case right(rtrim(b.lj11),2) when '00' then left(b.lj11,8) else b.lj11 end  lj11 "
						+ "  from hgz_djt a,hgz_liaojian b where a.djt01=b.ljno and a.djt09='Y'  union "
						+ " select a.*,case right(rtrim(b.cp11),2) when '00' then left(b.cp11,8) else b.cp11 end  lj11 from hgz_djt a,hgz_chenping b "
						+ " where a.djt01=b.cpno and a.djt09<>'Y' ) d,hgz_dj h where d.djno=h.djno ");
		while (rs.next()) {
			BillDetailMateriel obj = new BillDetailMateriel();
			BillMasterMateriel billMaster = new BillMasterMateriel();
			billMaster = dbimportDao.FindbynamebillMaster(rs.getString("djno"));
			obj.setBillMaster(billMaster);
			obj.setPtPart(rs.getString("djt02"));
			obj.setPtName(rs.getString("djt03"));
			obj.setPtSpec(rs.getString("djt04"));
			CalUnit calUnit = new CalUnit();
			calUnit = dbimportDao.Findbynamecalunit(rs.getString("djt06"));
			obj.setPtUnit(calUnit);
			WareSet wareSet = new WareSet();
			wareSet = dbimportDao.Findbynamewareset("dj05");
			obj.setWareSet(wareSet);
			obj.setPtAmount(rs.getDouble("djt05"));
			obj.setPtPrice(rs.getDouble("djt07"));
			obj.setProductNo(rs.getString("djt10"));
			Complex complex = new Complex();
			complex = dbimportDao.Findbycodecomplex(rs.getString("lj11"));
			obj.setComplex(complex);
			obj.setHsName(rs.getString("djt18"));
			obj.setHsSpec(rs.getString("djt19"));
			obj.setHsPrice(rs.getDouble("djt13"));
			Unit unit = new Unit();
			unit = dbimportDao.Findbynameunit(rs.getString("djt20"));
			obj.setHsUnit(unit);
			obj.setMoney(rs.getDouble("djt08"));
			obj.setHsAmount(rs.getDouble("djt16"));
			obj.setNetWeight(rs.getDouble("djt12"));
			try {
				dbimportDao.SaveObject(obj);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (rs != null)
			rs.close();
	}
}
