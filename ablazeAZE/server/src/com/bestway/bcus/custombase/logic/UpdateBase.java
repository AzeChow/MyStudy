/*
 * Created on 2004-6-18
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.custombase.logic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;

import com.bestway.bcus.custombase.entity.basecode.ApplicationMode;
import com.bestway.bcus.custombase.entity.basecode.Brief;
import com.bestway.bcus.custombase.entity.basecode.CoType;
import com.bestway.bcus.custombase.entity.basecode.InvClass;
import com.bestway.bcus.custombase.entity.basecode.InvestMode;
import com.bestway.bcus.custombase.entity.basecode.MachiningType;
import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.countrycode.District;
import com.bestway.bcus.custombase.entity.countrycode.PortInternal;
import com.bestway.bcus.custombase.entity.countrycode.PortLin;
import com.bestway.bcus.custombase.entity.countrycode.PreDock;
import com.bestway.bcus.custombase.entity.depcode.RedDep;
import com.bestway.bcus.custombase.entity.depcode.SaicCode;
import com.bestway.bcus.custombase.entity.depcode.StsCode;
import com.bestway.bcus.custombase.entity.depcode.TaxCode;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.hscode.LicenseNote;
import com.bestway.bcus.custombase.entity.parametercode.ContaModel;
import com.bestway.bcus.custombase.entity.parametercode.ContaSize;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.LevyKind;
import com.bestway.bcus.custombase.entity.parametercode.LevyMode;
import com.bestway.bcus.custombase.entity.parametercode.LicenseDocu;
import com.bestway.bcus.custombase.entity.parametercode.PayMode;
import com.bestway.bcus.custombase.entity.parametercode.PayType;
import com.bestway.bcus.custombase.entity.parametercode.PayerType;
import com.bestway.bcus.custombase.entity.parametercode.SrtJzx;
import com.bestway.bcus.custombase.entity.parametercode.SrtTj;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.bcus.custombase.entity.parametercode.Transac;
import com.bestway.bcus.custombase.entity.parametercode.Transf;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.custombase.entity.parametercode.Uses;
import com.bestway.bcus.dataimport.entity.DBDataRoot;
import com.bestway.bcus.dbimport.dao.DbCustomDao;
import com.bestway.bcus.dbimport.dao.DbimportDao;

/**
 * @author 001
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class UpdateBase {
	/**
	 * Commons Logger for this class
	 */
	private static final Log logger = LogFactory.getLog(UpdateBase.class);

	private DataSource dataSource = null;

	private Connection jbcusconn = null; // 目标

	private String sourcetableName = null; // 基本资料数据库名（源数据库名）

	private Connection conn = null; // 源

	private DbCustomDao dbCustomDao = null;

	private DbimportDao dbimportDao = null;

	private Statement st = null;

	public void updateCustoms(DBDataRoot db) {
		Conn(db);
		System.out.println("-- Conn(db);");
		setSourcetableName(db.getDataName());
		System.out.println("-- setSourcetableName(db.getDataName());");
		try {
			jbcusconn = dataSource.getConnection();
			System.out.println("-- 正在更新：企业性质");
			insertCoType(); // 企业性质
			System.out.println("-- 正在更新：投资类型");
			insertINVCLASS(); // 投资类型
			System.out.println("-- 正在更新：投资方式");
			investmode(); // 投资方式
			System.out.println("-- 正在更新：加工种类");
			insertMachiningType();// 加工种类
			System.out.println("-- 正在更新：申报报关方式");
			insertDMODE(); // 申报报关方式
			System.out.println("-- 正在更新：海关注册公司");
			insertBrief(); // 海关注册公司
			System.gc();
			System.out.println("-- 正在更新：税务部门");
			insertTaxCode(); // 税务部门
			System.out.println("-- 正在更新：工商行政");
			insertSaicCode(); // 工商行政
			System.out.println("-- 正在更新：技术监督");
			insertStsCode(); // 技术监督
			System.out.println("-- 正在更新：外经贸部门");
			insertRedDep(); // 外经贸部门
			System.out.println("-- 正在更新：国家地区");
			insertCountry(); // 国家地区
			System.out.println("-- 正在更新：海关关区");
			insertCustoms(); // 海关关区
			System.gc();
			System.out.println("-- 正在更新：国内进出口口岸");
			insertPortInternal();// 国内进出口口岸
			System.out.println("-- 正在更新：国际进出口港口");
			insertPortLin(); // 国际进出口港口
			System.out.println("-- 正在更新：国内进出口码头");
			insertPreDock(); // 国内进出口码头
			System.out.println("-- 正在更新：地区代码");
			insertDistrict(); // 地区代码
			System.out.println("-- 正在更新：贸易方式");
			insertTrade(); // 贸易方式
			System.out.println("-- 正在更新：计量单位");
			insertUnit(); // 计量单位
			System.out.println("-- 正在更新：货币代码");
			insertCurr(); // 货币代码
			System.out.println("-- 正在更新：证件代码");
			insertLicenseDocu();// 证件代码
			System.out.println("-- 正在更新：运输方式");
			insertTransf(); // 运输方式
			System.out.println("-- 正在更新：成交方式");
			insertTransac(); // 成交方式
			System.out.println("-- 正在更新：保税方式");
			insertPayMode(); // 保税方式
			System.out.println("-- 正在更新：用途代码");
			insertPayMode(); // 用途代码
			System.out.println("-- 正在更新：集装箱代码");
			insertSrtJzx(); // 集装箱代码
			System.out.println("-- 正在更新：集装箱规格");
			insertContaModel();// 集装箱规格
			System.out.println("-- 正在更新：集装箱尺寸");
			insertContaSize(); // 集装箱尺寸
			System.out.println("-- 正在更新：集装箱托架种类");
			insertSrtTj(); // 集装箱托架种类
			System.out.println("-- 正在更新：付款类型");
			insertPayType(); // 付款类型
			System.out.println("-- 正在更新：付款者类型");
			insertPayerType(); // 付款者类型
			System.out.println("-- 正在更新：商品类别");
			System.gc();
			insertComplex(); // 商品类别
			// System.out.println("-- 正在更新：领证商品备注表");
			// insertLicenseNote();//领证商品备注表 注：系统中暂时没用到，不做处理
			System.out.println("-- 正在更新：征免方式");
			insertLevyMode(); // 征免方式
			System.out.println("-- 正在更新：征免性质");
			insertLevyKind(); // 征免性质

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			try {
				if (jbcusconn != null && !jbcusconn.isClosed()) {
					jbcusconn.close();
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

	private void getst() {
		try {
			st = conn.createStatement();
			st.setFetchSize(500);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 得到数据源
	 * 
	 * @return
	 */
	public void Conn(DBDataRoot db) {
		try {
			System.out.println("-- " + db.getDriverClassName());
			Class.forName(db.getDriverClassName());
			System.out.println("-- " + db.getUrl() + db.getUserName()
					+ db.getPassword());
			conn = DriverManager.getConnection(db.getUrl(), db.getUserName(),
					db.getPassword());
		} catch (Exception e1) {
			throw new RuntimeException(e1);
		}
	}

	private String getUpdateSQL(String jtableName, String jcode, String jname,
			String ctableName, String ccode, String cname) {
		return "update " + jtableName + " set isout='1' where " + jcode
				+ " not in (select " + ccode + " from " + sourcetableName
				+ ".dbo." + ctableName + ")";
	}

	private String getUpdateSQL2(String jtableName, String jcode, String jname,
			String ctableName, String ccode, String cname) {
		return "update " + jtableName + " set " + jname + " = a." + cname
				+ " from " + jtableName + " left join " + sourcetableName
				+ ".dbo." + ctableName + " a on " + jcode + " = a." + ccode
				+ " where isOut is null";
	}

	private String getSql3(String jtableName, String jcode, String jname,
			String ctableName, String ccode, String cname) {
		return "select * from " + ctableName + " where " + ccode
				+ " not in (select a." + jcode + " from jbcus.dbo."
				+ jtableName + " a)";
	}

	/*
	 * 企业性质CoType->BUST_TYPE
	 */
	private void insertCoType() throws SQLException {
		// 修改为作废
		PreparedStatement pstmt = jbcusconn.prepareStatement(getUpdateSQL(
				"cotype", "code", "name", "co_type", "co_owner", "co_ownersh"));
		pstmt.executeUpdate();
		// 更新由编号
		PreparedStatement pstmt1 = jbcusconn.prepareStatement(getUpdateSQL2(
				"cotype", "code", "name", "co_type", "co_owner", "co_ownersh"));
		pstmt1.executeUpdate();
		// 新增新的
		ResultSet rs;
		rs = conn.createStatement().executeQuery(
				getSql3("cotype", "code", "name", "co_type", "co_owner",
						"co_ownersh"));
		while (rs.next()) {
			CoType Cotype = new CoType();// 对企业性质
			Cotype.setCode(rs.getString("co_owner"));
			Cotype.setName(rs.getString("co_ownersh"));
			Cotype.setIsOut("2"); // 表示new
			try {
				dbimportDao.SaveCotype(Cotype);
			} catch (Exception e) {
				continue;
			}
		}
		if (rs != null)
			rs.close();
	}

	/*
	 * 投资类型invclass->INV_CLASS
	 */
	private void insertINVCLASS() throws SQLException {
		PreparedStatement pstmt = jbcusconn
				.prepareStatement(getUpdateSQL("invClass", "code", "name",
						"INV_CLASS", "INV_CODE", "INV_NAME"));
		pstmt.executeUpdate();

		PreparedStatement pstmt1 = jbcusconn
				.prepareStatement(getUpdateSQL2("invClass", "code", "name",
						"INV_CLASS", "INV_CODE", "INV_NAME"));
		pstmt1.executeUpdate();

		ResultSet rs;
		rs = conn.createStatement().executeQuery(
				getSql3("invClass", "code", "name", "INV_CLASS", "INV_CODE",
						"INV_NAME"));
		while (rs.next()) {
			InvClass invClass = new InvClass();// 投资类型
			invClass.setCode(rs.getString("INV_CODE").trim());
			invClass.setName(rs.getString("INV_NAME").trim());
			invClass.setIsOut("2");
			try {
				dbimportDao.SaveInvclass(invClass);
			} catch (Exception e) {
				continue;
			}
		}
		if (rs != null)
			rs.close();
	}

	/*
	 * 投资方式investmode->INVEST_MODE
	 */
	private void investmode() throws SQLException {
		PreparedStatement pstmt = jbcusconn.prepareStatement(getUpdateSQL(
				"investMode", "code", "name", "INVEST_MODE",
				"INVEST_MODE_CODE", "INVEST_MODE_NAME"));
		pstmt.executeUpdate();

		PreparedStatement pstmt1 = jbcusconn.prepareStatement(getUpdateSQL2(
				"investMode", "code", "name", "INVEST_MODE",
				"INVEST_MODE_CODE", "INVEST_MODE_NAME"));
		pstmt1.executeUpdate();

		ResultSet rs;
		rs = conn.createStatement().executeQuery(
				getSql3("investMode", "code", "name", "INVEST_MODE",
						"INVEST_MODE_CODE", "INVEST_MODE_NAME"));
		while (rs.next()) {
			InvestMode investMode = new InvestMode();// 投资方式
			investMode.setCode(rs.getString("INVEST_MODE_CODE"));
			investMode.setName(rs.getString("INVEST_MODE_NAME"));
			investMode.setIsOut("2");
			try {
				dbimportDao.SaveInvestmode(investMode);
			} catch (Exception e) {
				continue;
			}
		}
		if (rs != null)
			rs.close();
	}

	/*
	 * 加工种类MachiningType->hd_jgzl
	 */
	private void insertMachiningType() throws SQLException {
		PreparedStatement pstmt = jbcusconn
				.prepareStatement(getUpdateSQL("machiningType", "code", "name",
						"hd_jgzl", "JGCode", "JGName"));
		pstmt.executeUpdate();

		PreparedStatement pstmt1 = jbcusconn
				.prepareStatement(getUpdateSQL2("machiningType", "code",
						"name", "hd_jgzl", "JGCode", "JGName"));
		pstmt1.executeUpdate();

		ResultSet rs;
		rs = conn.createStatement().executeQuery(
				getSql3("machiningType", "code", "name", "hd_jgzl", "JGCode",
						"JGName"));
		while (rs.next()) {
			MachiningType machiningType = new MachiningType();// 加工种类
			machiningType.setCode(rs.getString("JGCode"));
			machiningType.setName(rs.getString("JGName"));
			machiningType.setIsOut("2");
			try {
				dbimportDao.SaveMachiningType(machiningType);
			} catch (Exception e) {
				continue;
			}
		}
		if (rs != null)
			rs.close();
	}

	/*
	 * 申报报关方式applicationMode->D_MODE
	 */
	private void insertDMODE() throws SQLException {
		PreparedStatement pstmt = jbcusconn.prepareStatement(getUpdateSQL(
				"applicationMode", "code", "name", "D_MODE", "DMODE_CODE",
				"DMODE_NAME"));
		pstmt.executeUpdate();

		PreparedStatement pstmt1 = jbcusconn.prepareStatement(getUpdateSQL2(
				"applicationMode", "code", "name", "D_MODE", "DMODE_CODE",
				"DMODE_NAME"));
		pstmt1.executeUpdate();

		ResultSet rs;
		rs = conn.createStatement().executeQuery(
				getSql3("applicationMode", "code", "name", "D_MODE",
						"DMODE_CODE", "DMODE_NAME"));
		while (rs.next()) {
			ApplicationMode applicationMode = new ApplicationMode();// 申报报关方式
			applicationMode.setCode(rs.getString("DMODE_CODE"));
			applicationMode.setName(rs.getString("DMODE_NAME"));
			applicationMode.setIsOut("2");
			try {
				dbimportDao.SaveApplicationMode(applicationMode);
			} catch (Exception e) {
				continue;
			}
		}
		if (rs != null)
			rs.close();
	}

	/*
	 * 海关注册公司brief->brief
	 */
	private void insertBrief() throws SQLException {
		PreparedStatement pstmt = jbcusconn.prepareStatement(getUpdateSQL(
				"brief", "code", "name", "brief", "REG_CO_CGAC", "ENT_NAME"));
		pstmt.executeUpdate();
		System.gc();
		PreparedStatement pstmt1 = jbcusconn.prepareStatement(getUpdateSQL2(
				"brief", "code", "name", "brief", "REG_CO_CGAC", "ENT_NAME"));
		pstmt1.executeUpdate();
		System.gc();
		ResultSet rs;
		getst();
		rs = st.executeQuery(getSql3("brief", "code", "name", "brief",
				"REG_CO_CGAC", "ENT_NAME"));
		while (rs.next()) {
			Brief brief = new Brief();// 海关注册公司
			brief.setCode(rs.getString("REG_CO_CGAC"));
			brief.setChkManual(rs.getString("CHK_ANNUAL"));
			brief.setName(rs.getString("ENT_NAME"));
			brief.setIsOut("2");
			try {
				dbimportDao.Savebrief(brief);
			} catch (Exception e) {
				continue;
			}
		}
		if (rs != null) {
			rs.close();
		}
	}

	/*
	 * 税务部门TaxCode->TAX_CODE
	 */

	private void insertTaxCode() throws SQLException {
		PreparedStatement pstmt = jbcusconn.prepareStatement(getUpdateSQL(
				"taxCode", "code", "name", "tax_code", "TAX_CO", "TAX_NA"));
		pstmt.executeUpdate();

		PreparedStatement pstmt1 = jbcusconn.prepareStatement(getUpdateSQL2(
				"taxCode", "code", "name", "tax_code", "TAX_CO", "TAX_NA"));
		pstmt1.executeUpdate();

		ResultSet rs;
		getst();
		rs = st.executeQuery(getSql3("taxCode", "code", "name", "tax_code",
				"TAX_CO", "TAX_NA"));
		while (rs.next()) {
			TaxCode taxCode = new TaxCode();// 税务部门
			taxCode.setCode(rs.getString("TAX_CO"));
			taxCode.setName(rs.getString("TAX_NA"));
			taxCode.setIsOut("2");
			try {
				dbimportDao.SavetaxCode(taxCode);
			} catch (Exception e) {
				continue;
			}
		}
		if (rs != null) {
			rs.close();
		}
	}

	/*
	 * 工商行政SaicCode->SAIC_CODE
	 */
	private void insertSaicCode() throws SQLException {
		PreparedStatement pstmt = jbcusconn.prepareStatement(getUpdateSQL(
				"saicCode", "code", "name", "SAIC_CODE", "SAIC_CO", "SAIC_NA"));
		pstmt.executeUpdate();

		PreparedStatement pstmt1 = jbcusconn.prepareStatement(getUpdateSQL2(
				"saicCode", "code", "name", "SAIC_CODE", "SAIC_CO", "SAIC_NA"));
		pstmt1.executeUpdate();

		ResultSet rs;
		getst();
		rs = st.executeQuery(getSql3("saicCode", "code", "name", "SAIC_CODE",
				"SAIC_CO", "SAIC_NA"));
		while (rs.next()) {
			SaicCode saicCode = new SaicCode();// 工商行政
			saicCode.setCode(rs.getString("SAIC_CO"));
			saicCode.setName(rs.getString("SAIC_NA"));
			saicCode.setIsOut("2");
			try {
				dbimportDao.SavesaicCode(saicCode);
			} catch (Exception e) {
				continue;
			}
		}
		if (rs != null)
			rs.close();
	}

	/*
	 * 技术监督StsCode->STS_CODE
	 */
	private void insertStsCode() throws SQLException {
		PreparedStatement pstmt = jbcusconn.prepareStatement(getUpdateSQL(
				"stsCode", "code", "name", "STS_CODE", "STS_CODE", "STS_NAME"));
		pstmt.executeUpdate();

		PreparedStatement pstmt1 = jbcusconn.prepareStatement(getUpdateSQL2(
				"stsCode", "code", "name", "STS_CODE", "STS_CODE", "STS_NAME"));
		pstmt1.executeUpdate();

		ResultSet rs;
		rs = conn.createStatement().executeQuery(
				getSql3("stsCode", "code", "name", "STS_CODE", "STS_CODE",
						"STS_NAME"));
		while (rs.next()) {
			StsCode stsCode = new StsCode();// 技术监督
			stsCode.setCode(rs.getString("STS_CODE"));
			stsCode.setName(rs.getString("STS_NAME"));
			stsCode.setIsOut("2");
			try {
				dbimportDao.SavestsCode(stsCode);
			} catch (Exception e) {
				continue;
			}
		}
		if (rs != null)
			rs.close();
	}

	/*
	 * 外经贸部门RedDep->RED_DEP
	 */
	private void insertRedDep() throws SQLException {
		PreparedStatement pstmt = jbcusconn.prepareStatement(getUpdateSQL(
				"redDep", "code", "name", "mftec_code", "mftec_code",
				"mftec_name"));
		pstmt.executeUpdate();

		PreparedStatement pstmt1 = jbcusconn.prepareStatement(getUpdateSQL2(
				"redDep", "code", "name", "mftec_code", "mftec_code",
				"mftec_name"));
		pstmt1.executeUpdate();

		ResultSet rs;
		rs = conn.createStatement().executeQuery(
				getSql3("redDep", "code", "name", "mftec_code", "mftec_code",
						"mftec_name"));
		while (rs.next()) {
			RedDep redDep = new RedDep();// 外经贸部门
			redDep.setCode(rs.getString("mftec_code"));
			redDep.setName(rs.getString("mftec_name"));
			redDep.setIsOut("2");
			try {
				dbimportDao.SaveredDep(redDep);
			} catch (Exception e) {
				continue;
			}
		}
		if (rs != null)
			rs.close();
	}

	/*
	 * 国家地区Country->COUNTRY
	 */
	private void insertCountry() throws SQLException {
		PreparedStatement pstmt = jbcusconn.prepareStatement(getUpdateSQL(
				"country", "code", "name", "COUNTRY", "country_co",
				"country_na"));
		pstmt.executeUpdate();

		PreparedStatement pstmt1 = jbcusconn
				.prepareStatement("update country set name = a.country_na,countryEnname = a.country_en from country left join bcussi.dbo.COUNTRY a "
						+ " on code = a.country_co where isout is null");
		pstmt1.executeUpdate();

		ResultSet rs;
		rs = conn.createStatement().executeQuery(
				getSql3("country", "code", "name", "COUNTRY", "country_co",
						"country_na"));
		while (rs.next()) {
			Country country = new Country();// 国家地区
			country.setCode(rs.getString("country_co"));
			country.setName(rs.getString("country_na"));
			country.setCountryEnname(rs.getString("country_en"));
			country.setCountryMark(rs.getString("exam_mark"));
			country.setIsOut("2");
			try {
				dbimportDao.Savecountry(country);
			} catch (Exception e) {
				continue;
			}
		}
		if (rs != null)
			rs.close();
	}

	/*
	 * 海关关区Customs->Customs
	 */
	private void insertCustoms() throws SQLException {
		PreparedStatement pstmt = jbcusconn.prepareStatement(getUpdateSQL(
				"customs", "code", "name", "hd_kouan", "kacode", "kaname"));
		pstmt.executeUpdate();

		PreparedStatement pstmt1 = jbcusconn.prepareStatement(getUpdateSQL2(
				"customs", "code", "name", "hd_kouan", "kacode", "kaname"));
		pstmt1.executeUpdate();

		ResultSet rs;
		rs = conn.createStatement().executeQuery(
				getSql3("customs", "code", "name", "hd_kouan", "kacode",
						"kaname"));
		while (rs.next()) {
			Customs customs = new Customs();// 海关关区
			customs.setCode(rs.getString("kacode"));
			customs.setName(rs.getString("kaname"));
			customs.setIsOut("2");
			try {
				dbimportDao.Savecustoms(customs);
			} catch (Exception e) {
				continue;
			}
		}
		if (rs != null)
			rs.close();
	}

	/*
	 * 国内进出口口岸PortInternal->Port_internal
	 */
	private void insertPortInternal() throws SQLException {
		PreparedStatement pstmt = jbcusconn.prepareStatement(getUpdateSQL(
				"portInternal", "code", "name", "Port_internal", "port_code",
				"port_name"));
		pstmt.executeUpdate();

		PreparedStatement pstmt1 = jbcusconn.prepareStatement(getUpdateSQL2(
				"portInternal", "code", "name", "Port_internal", "port_code",
				"port_name"));
		pstmt1.executeUpdate();

		ResultSet rs;
		rs = conn.createStatement().executeQuery(
				getSql3("portInternal", "code", "name", "Port_internal",
						"port_code", "port_name"));
		while (rs.next()) {
			PortInternal portInternal = new PortInternal();// 国内进出口口岸
			portInternal.setCode(rs.getString("port_code"));
			portInternal.setName(rs.getString("port_name"));
			portInternal.setIsOut("2");
			try {
				dbimportDao.SaveportInternal(portInternal);
			} catch (Exception e) {
				continue;
			}
		}
		if (rs != null)
			rs.close();
	}

	/*
	 * 国际进出口港口PortLin->Port_lin
	 */
	private void insertPortLin() throws SQLException {
		PreparedStatement pstmt = jbcusconn.prepareStatement(getUpdateSQL(
				"portLin", "code", "name", "Port_lin", "port_code",
				"port_c_cod"));
		pstmt.executeUpdate();

		PreparedStatement pstmt1 = jbcusconn
				.prepareStatement("update portLin set name = a.port_c_cod,portEnname=a.port_e_cod from portLin "
						+ " left join bcussi.dbo.Port_lin a on code=a.port_code where isout is null");
		pstmt1.executeUpdate();

		ResultSet rs;
		rs = conn.createStatement().executeQuery(
				getSql3("portLin", "code", "name", "Port_lin", "port_code",
						"port_c_cod"));
		while (rs.next()) {
			PortLin portLin = new PortLin();// 国际进出口港口
			portLin.setCode(rs.getString("port_code"));
			portLin.setName(rs.getString("port_c_cod"));
			portLin.setPortEnname(rs.getString("port_e_cod"));
			portLin.setPortLine("port_line");
			portLin.setIsOut("2");
			try {
				dbimportDao.SaveportLin(portLin);
			} catch (Exception e) {
				continue;
			}
		}
		if (rs != null)
			rs.close();
	}

	/*
	 * 国内进出口码头PreDock->Pre_dock
	 */
	private void insertPreDock() throws SQLException {
		PreparedStatement pstmt = jbcusconn.prepareStatement(getUpdateSQL(
				"preDock", "code", "name", "HD_Port", "pocode", "poname"));
		pstmt.executeUpdate();

		PreparedStatement pstmt1 = jbcusconn
				.prepareStatement("update preDock set name = a.poname,cuscode=a.areano from preDock "
						+ " left join bcussi.dbo.HD_Port a on code=a.pocode where isout is null");
		pstmt1.executeUpdate();

		ResultSet rs;
		rs = conn.createStatement().executeQuery(
				getSql3("preDock", "code", "name", "HD_Port", "pocode",
						"poname"));
		while (rs.next()) {
			PreDock preDock = new PreDock();// 国内进出口码头
			preDock.setCode(rs.getString("pocode"));
			preDock.setName(rs.getString("poname"));
			preDock.setCusCode(rs.getString("areano"));
			preDock.setIsOut("2");
			try {
				dbimportDao.SavepreDock(preDock);
			} catch (Exception e) {
				continue;
			}
		}
		if (rs != null)
			rs.close();
	}

	/*
	 * 地区代码District
	 */
	private void insertDistrict() throws SQLException {
		PreparedStatement pstmt = jbcusconn.prepareStatement(getUpdateSQL(
				"district", "code", "name", "DISTRICT_REL", "DISTRICT_CODE",
				"DISTRICT_NAME"));
		pstmt.executeUpdate();

		PreparedStatement pstmt1 = jbcusconn.prepareStatement(getUpdateSQL2(
				"district", "code", "name", "DISTRICT_REL", "DISTRICT_CODE",
				"DISTRICT_NAME"));
		pstmt1.executeUpdate();

		ResultSet rs;
		rs = conn.createStatement().executeQuery(
				getSql3("district", "code", "name", "DISTRICT_REL",
						"DISTRICT_CODE", "DISTRICT_NAME"));
		while (rs.next()) {
			District district = new District(); // 地区代码
			district.setCode(rs.getString("DISTRICT_CODE"));
			district.setName(rs.getString("DISTRICT_NAME"));
			district.setIsOut("2");
			try {
				dbimportDao.saveDistrict(district);
			} catch (Exception e) {
				continue;
			}
		}
		if (rs != null)
			rs.close();
	}

	/*
	 * 贸易方式 Trade->trade
	 */
	private void insertTrade() throws SQLException {
		PreparedStatement pstmt = jbcusconn.prepareStatement(getUpdateSQL(
				"trade", "code", "name", "trade", "TRADE_MODE", "ABBR_TRADE"));
		pstmt.executeUpdate();

		PreparedStatement pstmt1 = jbcusconn
				.prepareStatement("update trade set name = a.abbr_trade,tradeFname=a.full_trade from trade left "
						+ " join bcussi.dbo.trade a on code = a.trade_mode where isout is null");
		pstmt1.executeUpdate();

		ResultSet rs;
		rs = conn.createStatement().executeQuery(
				getSql3("trade", "code", "name", "trade", "TRADE_MODE",
						"ABBR_TRADE"));
		while (rs.next()) {
			Trade trade = new Trade();// 贸易方式
			trade.setCode(rs.getString("TRADE_MODE"));
			trade.setName(rs.getString("ABBR_TRADE"));
			trade.setTradeFname(rs.getString("FULL_TRADE"));
			trade.setIsOut("2");
			try {
				dbimportDao.Savetrade(trade);
			} catch (Exception e) {
				continue;
			}
		}
		if (rs != null)
			rs.close();
	}

	/*
	 * 计量单位Unit->unit
	 */
	private void insertUnit() throws SQLException {
		PreparedStatement pstmt = jbcusconn.prepareStatement(getUpdateSQL(
				"unit", "code", "name", "unit", "unit_code", "unit_name"));
		pstmt.executeUpdate();

		PreparedStatement pstmt1 = jbcusconn
				.prepareStatement("update unit set name = a.unit_name,unitratio = a.conv_ratio from unit "
						+ " left join bcussi.dbo.unit a on code = a.unit_code");
		pstmt1.executeUpdate();

		ResultSet rs;
		rs = conn.createStatement().executeQuery(
				getSql3("unit", "code", "name", "unit", "unit_code",
						"unit_name"));
		while (rs.next()) {
			Unit unit = new Unit(); // 计量单位
			unit.setCode(rs.getString("unit_code"));
			unit.setName(rs.getString("unit_name"));
			if (GenericValidator.isDouble(rs.getString("conv_ratio")))
				unit.setUnitRatio(new Double(rs.getString("conv_ratio")));
			unit.setIsOut("2");
			try {
				dbimportDao.SaveUnit(unit);
			} catch (Exception e) {
				continue;
			}
		}
		if (rs != null)
			rs.close();
	}

	/*
	 * 货币代码Curr->curr
	 */
	private void insertCurr() throws SQLException {
		PreparedStatement pstmt = jbcusconn.prepareStatement(getUpdateSQL(
				"curr", "code", "name", "curr", "CURR_CODE", "CURR_NAME"));
		pstmt.executeUpdate();

		PreparedStatement pstmt1 = jbcusconn
				.prepareStatement("update curr set name = a.curr_name,currsymb = a.curr_symb from curr "
						+ " left join bcussi.dbo.curr a on code = a.curr_code");
		pstmt1.executeUpdate();

		ResultSet rs;
		rs = conn.createStatement().executeQuery(
				getSql3("curr", "code", "name", "curr", "CURR_CODE",
						"CURR_NAME"));
		while (rs.next()) {
			Curr curr = new Curr();// 货币代码
			curr.setCode(rs.getString("CURR_CODE"));
			curr.setName(rs.getString("CURR_NAME"));
			curr.setCurrSymb(rs.getString("CURR_SYMB"));
			curr.setIsOut("2");
			try {
				dbimportDao.Savecurr(curr);
			} catch (Exception e) {
				continue;
			}
		}
		if (rs != null)
			rs.close();
	}

	/*
	 * 证件代码LicenseDocu->LICENSEDOCU
	 */
	private void insertLicenseDocu() throws SQLException {
		PreparedStatement pstmt = jbcusconn.prepareStatement(getUpdateSQL(
				"licenseDocu", "code", "name", "LicenseDocu", "DOCU_CODE",
				"DOCU_NAME"));
		pstmt.executeUpdate();

		PreparedStatement pstmt1 = jbcusconn.prepareStatement(getUpdateSQL2(
				"licenseDocu", "code", "name", "LicenseDocu", "DOCU_CODE",
				"DOCU_NAME"));
		pstmt1.executeUpdate();

		ResultSet rs;
		rs = conn.createStatement().executeQuery(
				getSql3("licenseDocu", "code", "name", "LicenseDocu",
						"DOCU_CODE", "DOCU_NAME"));
		while (rs.next()) {
			LicenseDocu licenseDocu = new LicenseDocu();// 证件代码
			licenseDocu.setCode(rs.getString("DOCU_CODE"));
			licenseDocu.setName(rs.getString("DOCU_NAME"));
			licenseDocu.setIsOut("2");
			try {
				dbimportDao.SavelicenseDocu(licenseDocu);
			} catch (Exception e) {
				continue;
			}
		}
		if (rs != null)
			rs.close();
	}

	/*
	 * 运输方式Transf->TRANSF
	 */
	private void insertTransf() throws SQLException {
		PreparedStatement pstmt = jbcusconn.prepareStatement(getUpdateSQL(
				"transf", "code", "name", "TRANSF", "TRAF_CODE", "TRAF_SPEC"));
		pstmt.executeUpdate();

		PreparedStatement pstmt1 = jbcusconn.prepareStatement(getUpdateSQL2(
				"transf", "code", "name", "TRANSF", "TRAF_CODE", "TRAF_SPEC"));
		pstmt1.executeUpdate();

		ResultSet rs;
		rs = conn.createStatement().executeQuery(
				getSql3("transf", "code", "name", "TRANSF", "TRAF_CODE",
						"TRAF_SPEC"));
		while (rs.next()) {
			Transf transf = new Transf();// 运输方式
			transf.setCode(rs.getString("TRAF_CODE"));
			transf.setName(rs.getString("TRAF_SPEC"));
			transf.setIsOut("2");
			try {
				dbimportDao.Savetransf(transf);
			} catch (Exception e) {
				continue;
			}
		}
		if (rs != null)
			rs.close();
	}

	/*
	 * 成交方式 Transac->TRANSAC
	 */
	private void insertTransac() throws SQLException {
		PreparedStatement pstmt = jbcusconn.prepareStatement(getUpdateSQL(
				"transac", "code", "name", "TRANSAC", "TRANS_MODE",
				"TRANS_SPEC"));
		pstmt.executeUpdate();

		PreparedStatement pstmt1 = jbcusconn.prepareStatement(getUpdateSQL2(
				"transac", "code", "name", "TRANSAC", "TRANS_MODE",
				"TRANS_SPEC"));
		pstmt1.executeUpdate();

		ResultSet rs;
		rs = conn.createStatement().executeQuery(
				getSql3("transac", "code", "name", "TRANSAC", "TRANS_MODE",
						"TRANS_SPEC"));
		while (rs.next()) {
			Transac transac = new Transac();// 成交方式
			transac.setCode(rs.getString("TRANS_MODE"));
			transac.setName(rs.getString("TRANS_SPEC"));
			transac.setIsOut("2");
			try {
				dbimportDao.Savetransac(transac);
			} catch (Exception e) {
				continue;
			}
		}
		if (rs != null)
			rs.close();
	}

	/*
	 * 保税方式PayMode->PAY_MODE
	 */
	private void insertPayMode() throws SQLException {
		PreparedStatement pstmt = jbcusconn.prepareStatement(getUpdateSQL(
				"payMode", "code", "name", "PAY_MODE", "PAY_MODE_CODE",
				"PAY_MODE_NAME"));
		pstmt.executeUpdate();

		PreparedStatement pstmt1 = jbcusconn.prepareStatement(getUpdateSQL2(
				"payMode", "code", "name", "PAY_MODE", "PAY_MODE_CODE",
				"PAY_MODE_NAME"));
		pstmt1.executeUpdate();

		ResultSet rs;
		rs = conn.createStatement().executeQuery(
				getSql3("payMode", "code", "name", "PAY_MODE", "PAY_MODE_CODE",
						"PAY_MODE_NAME"));
		while (rs.next()) {
			PayMode payMode = new PayMode();// 保税方式
			payMode.setCode(rs.getString("PAY_MODE_CODE"));
			payMode.setName(rs.getString("PAY_MODE_NAME"));
			payMode.setIsOut("2");
			try {
				dbimportDao.SavepayMode(payMode);
			} catch (Exception e) {
				continue;
			}
		}
		if (rs != null)
			rs.close();
	}

	/*
	 * 用途代码Uses->USE_TO
	 */
	private void insertUses() throws SQLException {
		PreparedStatement pstmt = jbcusconn.prepareStatement(getUpdateSQL(
				"uses", "code", "name", "USE_TO", "USE_TO_COD", "USE_TO_NAM"));
		pstmt.executeUpdate();

		PreparedStatement pstmt1 = jbcusconn.prepareStatement(getUpdateSQL2(
				"uses", "code", "name", "USE_TO", "USE_TO_COD", "USE_TO_NAM"));
		pstmt1.executeUpdate();

		ResultSet rs;
		rs = conn.createStatement().executeQuery(
				getSql3("uses", "code", "name", "USE_TO", "USE_TO_COD",
						"USE_TO_NAM"));
		while (rs.next()) {
			Uses uses = new Uses();// 用途代码
			uses.setCode(rs.getString("USE_TO_COD"));
			uses.setName(rs.getString("USE_TO_NAM"));
			uses.setIsOut("2");
			try {
				dbimportDao.Saveuses(uses);
			} catch (Exception e) {
				continue;
			}
		}
		if (rs != null)
			rs.close();
	}

	/*
	 * 集装箱代码SrtJzx->SRT_JZX
	 */
	private void insertSrtJzx() throws SQLException {
		PreparedStatement pstmt = jbcusconn.prepareStatement(getUpdateSQL(
				"srtJzx", "code", "name", "hd_jzx", "JZXCODE", "JZXsize"));
		pstmt.executeUpdate();

		PreparedStatement pstmt1 = jbcusconn
				.prepareStatement("update srtJzx set name=a.jzxsize,srtusing=a.jzxmark,srtweight=a.jzxweight from srtJzx"
						+ " left join bcussi.dbo.hd_jzx a on code = a.jzxcode where isout is null");
		pstmt1.executeUpdate();

		ResultSet rs;
		rs = conn
				.createStatement()
				.executeQuery(
						"select * from hd_jzx where jzxcode not in (select a.code from jbcus.dbo.srtJzx a)");
		while (rs.next()) {
			SrtJzx srtJzx = new SrtJzx();// 集装箱代码
			srtJzx.setCode(rs.getString("JZXCODE"));
			srtJzx.setName(rs.getString("JZXsize"));
			srtJzx.setSrtUsing(rs.getString("JZXMark"));
			if (GenericValidator.isDouble(rs.getString("JZXWEIGHT")))
				srtJzx.setSrtWeight(new Double(rs.getString("JZXWEIGHT")));
			srtJzx.setIsOut("2");
			try {
				dbimportDao.SavesrtJzx(srtJzx);
			} catch (Exception e) {
				continue;
			}
		}
		if (rs != null)
			rs.close();
	}

	/*
	 * 集装箱规格ContaModel->CONTA_MODEL
	 */
	private void insertContaModel() throws SQLException {
		PreparedStatement pstmt = jbcusconn.prepareStatement(getUpdateSQL(
				"contaModel", "code", "name", "CONTA_MODEL",
				"CONTA_MODEL_CODE", "CONTA_MODEL_NAME"));
		pstmt.executeUpdate();

		PreparedStatement pstmt1 = jbcusconn.prepareStatement(getUpdateSQL2(
				"contaModel", "code", "name", "CONTA_MODEL",
				"CONTA_MODEL_CODE", "CONTA_MODEL_NAME"));
		pstmt1.executeUpdate();

		ResultSet rs;
		rs = conn.createStatement().executeQuery(
				getSql3("contaModel", "code", "name", "CONTA_MODEL",
						"CONTA_MODEL_CODE", "CONTA_MODEL_NAME"));
		while (rs.next()) {
			ContaModel contaModel = new ContaModel();// 集装箱规格
			contaModel.setCode(rs.getString("CONTA_MODEL_CODE"));
			contaModel.setName(rs.getString("CONTA_MODEL_NAME"));
			contaModel.setIsOut("2");
			try {
				dbimportDao.SavecontaModel(contaModel);
			} catch (Exception e) {
				continue;
			}
		}
		if (rs != null)
			rs.close();
	}

	/*
	 * 集装箱尺寸ContaSize->CONTA_SIZE
	 */
	private void insertContaSize() throws SQLException {
		PreparedStatement pstmt = jbcusconn.prepareStatement(getUpdateSQL(
				"contaSize", "code", "name", "CONTA_SIZE", "CONTA_CODE",
				"CONTA_SIZE"));
		pstmt.executeUpdate();

		PreparedStatement pstmt1 = jbcusconn.prepareStatement(getUpdateSQL2(
				"contaSize", "code", "name", "CONTA_SIZE", "CONTA_CODE",
				"CONTA_SIZE"));
		pstmt1.executeUpdate();

		ResultSet rs;
		rs = conn.createStatement().executeQuery(
				getSql3("contaSize", "code", "name", "CONTA_SIZE",
						"CONTA_CODE", "CONTA_SIZE"));
		while (rs.next()) {
			ContaSize contaSize = new ContaSize();// 集装箱尺寸
			contaSize.setCode(rs.getString("CONTA_CODE"));
			contaSize.setName(rs.getString("CONTA_SIZE"));
			contaSize.setIsOut("2");
			try {
				dbimportDao.SavecontaSize(contaSize);
			} catch (Exception e) {
				continue;
			}
		}
		if (rs != null)
			rs.close();
	}

	/*
	 * 集装箱托架种类SrtTj->SRT_TJ
	 */
	private void insertSrtTj() throws SQLException {
		PreparedStatement pstmt = jbcusconn.prepareStatement(getUpdateSQL(
				"srtTj", "code", "name", "HD_JZXTJ", "jt01", "jt02"));
		pstmt.executeUpdate();

		PreparedStatement pstmt1 = jbcusconn
				.prepareStatement("update srtTj set name=a.jt02,tjtype=a.jt04,tjweight=a.jt05 from srtTj left join bcussi.dbo.HD_JZXTJ a"
						+ " on code = a.jt01 where isout is null");
		pstmt1.executeUpdate();

		ResultSet rs;
		rs = conn
				.createStatement()
				.executeQuery(
						"select * from HD_JZXTJ where jt01 not in (select a.code from jbcus.dbo.srtTj a)");
		while (rs.next()) {
			SrtTj srtTj = new SrtTj();// 集装箱托架种类
			srtTj.setCode(rs.getString("jt01"));
			srtTj.setName(rs.getString("jt02"));
			srtTj.setTjType(rs.getString("jt03"));
			if (GenericValidator.isDouble(rs.getString("jt05")))
				srtTj.setTjWeight(new Double(rs.getString("jt05")));
			srtTj.setIsOut("2");
			try {
				dbimportDao.SavesrtTj(srtTj);
			} catch (Exception e) {
				continue;
			}
		}
		if (rs != null)
			rs.close();
	}

	/*
	 * 付款类型PayType->PAY_TYPE
	 */
	private void insertPayType() throws SQLException {
		PreparedStatement pstmt = jbcusconn.prepareStatement(getUpdateSQL(
				"payType", "code", "name", "Pay_Type", "PAY_TYPE_CO",
				"PAY_TYPE_NA"));
		pstmt.executeUpdate();

		PreparedStatement pstmt1 = jbcusconn.prepareStatement(getUpdateSQL2(
				"payType", "code", "name", "Pay_Type", "PAY_TYPE_CO",
				"PAY_TYPE_NA"));
		pstmt1.executeUpdate();

		ResultSet rs;
		rs = conn.createStatement().executeQuery(
				getSql3("payType", "code", "name", "Pay_Type", "PAY_TYPE_CO",
						"PAY_TYPE_NA"));
		while (rs.next()) {
			PayType payType = new PayType();// 付款类型
			payType.setCode(rs.getString("PAY_TYPE_CO"));
			payType.setName(rs.getString("PAY_TYPE_NA"));
			payType.setIsOut("2");
			try {
				dbimportDao.SavepayType(payType);
			} catch (Exception e) {
				continue;
			}
		}
		if (rs != null)
			rs.close();
	}

	/*
	 * 付款者类型PayerType-> PAYER_TYPE
	 */
	private void insertPayerType() throws SQLException {
		PreparedStatement pstmt = jbcusconn.prepareStatement(getUpdateSQL(
				"payerType", "code", "name", "PAYER_TYPE", "PAYER_TYPE_CO",
				"PAYER_TYPE_NA"));
		pstmt.executeUpdate();

		PreparedStatement pstmt1 = jbcusconn.prepareStatement(getUpdateSQL2(
				"payerType", "code", "name", "PAYER_TYPE", "PAYER_TYPE_CO",
				"PAYER_TYPE_NA"));
		pstmt1.executeUpdate();

		ResultSet rs;
		rs = conn.createStatement().executeQuery(
				getSql3("payerType", "code", "name", "PAYER_TYPE",
						"PAYER_TYPE_CO", "PAYER_TYPE_NA"));
		while (rs.next()) {
			PayerType payerType = new PayerType();// 付款者类型
			payerType.setCode(rs.getString("PAYER_TYPE_CO"));
			payerType.setName(rs.getString("PAYER_TYPE_NA"));
			payerType.setIsOut("2");
			try {
				dbimportDao.SavepayerType(payerType);
			} catch (Exception e) {
				continue;
			}
		}
		if (rs != null)
			rs.close();
	}

	/*
	 * 商品类别Complex->complex
	 */
	private void insertComplex() throws SQLException {
		PreparedStatement pstmt = jbcusconn.prepareStatement(getUpdateSQL(
				"complex", "code", "name", "complex", "CODE_T", "g_name"));
		pstmt.executeUpdate();
		System.gc();

		PreparedStatement pstmt1 = jbcusconn
				.prepareStatement("update complex set name=a.g_name,note=a.note_s,lowrate=a.low_rate,"
						+ " firstUnit  = case when exists (select * from unit b where b.code = a.unit_1) then a.unit_1 else null end,"
						+ " secondUnit = case when exists (select * from unit c where c.code = a.unit_2) then a.unit_2 else null end,"
						+ " highrate=a.high_rate,regrate=a.reg_rate,outrate=a.out_rate from complex left join bcussi.dbo.complex a"
						+ " on code = a.code_t where isout is null");
		pstmt1.executeUpdate();
		System.gc();

		String updateSql = "";
		ResultSet rs;
		getst();
		rs = st.executeQuery(getSql3("complex", "code", "name", "complex",
				"CODE_T", "g_name"));
		while (rs.next()) {
			Complex complex = new Complex();// 商品类别
			complex.setCode(rs.getString("CODE_T"));
			complex.setFirstUnit(dbCustomDao.Findbyidunit(rs
					.getString("UNIT_1")));
			complex.setHighrate(rs.getString("high_rate"));
			complex.setLowrate(rs.getString("low_rate"));
			complex.setName(rs.getString("g_name").trim());
			complex.setNote(rs.getString("note_s").trim());
			// complex.setOutrate(rs.getString("out_rate"));
			// complex.setRegrate(rs.getString("reg_rate"));
			complex.setSecondUnit(dbCustomDao.Findbyidunit(rs
					.getString("UNIT_2")));
			complex.setIsOut("2");
			try {
				dbimportDao.Savecomplex(complex);
			} catch (Exception e) {
				continue;
			}
		}
		if (rs != null)
			rs.close();
	}

	/*
	 * 领证商品备注表LicenseNote->LicenseNote //暂不导入
	 */
	private void insertLicenseNote() throws SQLException {
		ResultSet rs;
		rs = conn.createStatement().executeQuery(
				"select * from LicenseNote order by code_t");
		while (rs.next()) {
			LicenseNote licenseNote = new LicenseNote();// 领证商品备注表
			licenseNote.setCode(rs.getString("CODE_T"));
			licenseNote.setClassElucidation(rs.getString("IE_NOTE"));
			licenseNote.setAssistanceCode(rs.getString("CODE_S"));
			licenseNote.setPkSeq(rs.getString("PK_SEQ"));
			licenseNote.setName(rs.getString("IE_MARK"));
			try {
				dbimportDao.SavelicenseNote(licenseNote);
			} catch (Exception e) {
				continue;
			}
		}
		if (rs != null)
			rs.close();
	}

	/*
	 * 征免方式
	 */
	private void insertLevyMode() throws SQLException {
		PreparedStatement pstmt = jbcusconn.prepareStatement(getUpdateSQL(
				"levyMode", "code", "name", "LEVYMODE", "DUTY_MODE",
				"DUTY_SPEC"));
		pstmt.executeUpdate();

		PreparedStatement pstmt1 = jbcusconn.prepareStatement(getUpdateSQL2(
				"levyMode", "code", "name", "LEVYMODE", "DUTY_MODE",
				"DUTY_SPEC"));
		pstmt1.executeUpdate();

		ResultSet rs;
		rs = conn.createStatement().executeQuery(
				getSql3("levyMode", "code", "name", "LEVYMODE", "DUTY_MODE",
						"DUTY_SPEC"));
		while (rs.next()) {
			LevyMode levyMode = new LevyMode();//
			levyMode.setCode(rs.getString("DUTY_MODE"));
			levyMode.setName(rs.getString("DUTY_SPEC"));
			levyMode.setIsOut("2");
			try {
				dbimportDao.SaveLevyMode(levyMode);
			} catch (Exception e) {
				continue;
			}
		}
		if (rs != null)
			rs.close();
	}

	/*
	 * 征免性质
	 */
	private void insertLevyKind() throws SQLException {
		PreparedStatement pstmt = jbcusconn
				.prepareStatement(getUpdateSQL("levyKind", "code", "name",
						"HD_DueOrFree", "DFCode", "DFName"));
		pstmt.executeUpdate();

		PreparedStatement pstmt1 = jbcusconn
				.prepareStatement(getUpdateSQL2("levyKind", "code", "name",
						"HD_DueOrFree", "DFCode", "DFName"));
		pstmt1.executeUpdate();

		ResultSet rs;
		rs = conn.createStatement().executeQuery(
				getSql3("levyKind", "code", "name", "HD_DueOrFree", "DFCode",
						"DFName"));
		while (rs.next()) {
			LevyKind levyKind = new LevyKind();//
			levyKind.setCode(rs.getString("DFCode"));
			levyKind.setName(rs.getString("DFName"));
			levyKind.setIsOut("2");
			try {
				dbimportDao.SaveLevyKind(levyKind);
			} catch (Exception e) {
				continue;
			}
		}
		if (rs != null)
			rs.close();
	}

	/**
	 * @return Returns the dbCustomDao.
	 */
	public DbCustomDao getDbCustomDao() {
		return dbCustomDao;
	}

	/**
	 * @param dbCustomDao
	 *            The dbCustomDao to set.
	 */
	public void setDbCustomDao(DbCustomDao dbCustomDao) {
		this.dbCustomDao = dbCustomDao;
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
	 * @param sourcetableName
	 *            The sourcetableName to set.
	 */
	public void setSourcetableName(String sourcetableName) {
		this.sourcetableName = sourcetableName;
	}
}