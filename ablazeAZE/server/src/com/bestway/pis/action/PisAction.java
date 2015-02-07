package com.bestway.pis.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.common.Request;
import com.bestway.common.authority.entity.AclUser;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.customs.common.entity.BaseEmsHead;
import com.bestway.pis.entity.BrokerCorp;
import com.bestway.pis.entity.EspAuthorityBrokerCorp;
import com.bestway.pis.entity.EspAuthorityBrokerCorpItem;
import com.bestway.pis.entity.PisEspServer;
import com.bestway.pis.entity.PisSyncAclUser;
import com.bestway.pis.entity.PisSyncAgentCorp;

public interface PisAction {

	// /////////////////////////////////////PisDecLogicImpl///////////////////////////////////////////////

	public <T> T saveOrUpdate(Request request, T entity);

	/**
	 * 查询加工贸易报关单 当选择的是已检查的时候，查询已检查未发送的数据；当选择的是已经发送的时候查询已发送且已经检查的数据
	 * 
	 * @param request
	 * @param impExpFlag
	 * @param beginDate
	 * @param endDate
	 * @param isCheck
	 * @param isSend
	 * @param projectType
	 * @return
	 */
	public List<BaseCustomsDeclaration> findDecHead(Request request,
			Integer impExpFlag, String emsNo, Date beginDate, Date endDate,
			Boolean isCheck, Boolean isSend, Integer projectType);

	/**
	 * 导出数据到物流&报关平台
	 *
	 * @param request
	 * @param head
	 * @return
	 */
	public String makeDecJSONData(Request request, BaseCustomsDeclaration head,
			Integer projectType);

	// /////////////////////////////////////////////////////////////////////////////////////

	// ///////////////////////////////PisGtsDecLogicImpl////////////////////////////////////////
	// ///////////////////////////////PisSyncLogicImpl////////////////////////////////////////

	public String getCompanyCode(Request request);

	/**
	 * 查找代理公司
	 *
	 * @param request
	 * @return
	 */
	public List findBrokerCorp(Request request);

	/**
	 * 查找esp服务器
	 *
	 * @param request
	 * @return
	 */
	public List findPisEspServer(Request request);

	/**
	 * 查找授权的esp服务器
	 *
	 * @param request
	 * @return
	 */
	public List findEspAuthorityBrokerCorpPisEspServer(Request request);

	/**
	 *
	 * 同步代理公司
	 *
	 * @param request
	 * @param serverMap
	 * @param agtCompanyList
	 */
	public void syncBrokerCorp(Request request, Map<String, Map> serverMap,
			List<Map> agtCompanyList);

	/**
	 * 查找同步账号esp
	 *
	 * @param request
	 * @return
	 */
	public List findAclUser(Request request);

	/**
	 * 根据被选择的用户名 查找 Acluser
	 * 
	 * @param request
	 * @param loginNames
	 * @return
	 */
	public List findSyncAclUserByLoginName(Request request, List loginNames,
			Class entityName);

	/**
	 * 查找同步账号esp
	 *
	 * @param request
	 * @return
	 */
	public List findPisSyncAclUser(Request request);

	public PisSyncAclUser setPisSyncAclUser(PisSyncAclUser pUser, AclUser user);

	/**
	 * 查找同步申报单位esp
	 *
	 * @param request
	 * @return
	 */
	public List findPisSyncAgentCorp(Request request);

	/**
	 * 将新添加的用户添加到PisSyncAclUser
	 *
	 * @param request
	 */
	public void refreshPisSyncAclUser(Request request);

	/**
	 * 同步用户后更新
	 *
	 * @param request
	 * @param pUserlist
	 * @param infoMap
	 * @param errorInfoMap
	 * @param userErrMap
	 */
	public void updatePisSyncAclUser(Request request,
			List<PisSyncAclUser> pUserlist, Map<PisEspServer, Boolean> infoMap,
			Map<PisEspServer, Map<String, String>> userErrMap);

	/**
	 * 同步申报单位后更新
	 *
	 * @param request
	 * @param pAgentlist
	 * @param infoMap
	 * @param agentErrMap
	 */
	public void updatePisSyncAgentCorp(Request request,
			List<PisSyncAgentCorp> pAgentlist,
			Map<PisEspServer, Boolean> infoMap,
			Map<PisEspServer, Map<String, String>> agentErrMap);

	/**
	 * 同步申报单位后更新
	 *
	 * @param request
	 * @param agtDataList
	 */
	public void updatePisSyncAgentCorp(Request request, List<String> agtDataList);

	/**
	 * 查找授权代理公司
	 *
	 * @param request
	 * @return
	 */
	public List findEspAuthorityBrokerCorp(Request request);

	/**
	 * 查找授权代理公司明细
	 *
	 * @param request
	 * @param espAuthorityBrokerCorp
	 * @return
	 */
	public List findEspAuthorityBrokerCorpItem(Request request,
			EspAuthorityBrokerCorp espAuthorityBrokerCorp);

	/**
	 * 授权代理公司
	 *
	 * @param request
	 * @param corpList
	 */
	public void authorityBrokerCorp(Request request, List<BrokerCorp> corpList);

	/**
	 * 取消授权代理公司
	 *
	 * @param request
	 * @param bcList
	 */
	public void cancelAuthorityBrokerBrop(Request request,
			List<EspAuthorityBrokerCorp> bcList);

	public void saveEspAuthorityBrokerCorpItem(Request request,
			List<EspAuthorityBrokerCorpItem> itemList);

	public void delEspAuthorityBrokerCorpItem(Request request,
			List<EspAuthorityBrokerCorpItem> entityList);

	/**
	 * 授权代理公司后更新
	 *
	 * @param request
	 * @param brokerlist
	 * @param infoMap
	 * @param syncErrMap
	 */
	public void updateEspAuthorityBrokerCorp(Request request,
			List<EspAuthorityBrokerCorp> brokerlist,
			Map<EspAuthorityBrokerCorp, Boolean> infoMap,
			Map<EspAuthorityBrokerCorp, Map<String, String>> syncErrMap);

	/**
	 * 查询已授权的代理公司
	 *
	 * @return
	 */
	public List findBrokerCorpByMainBusiness(Request request,
			String mainBusiness);

	// ////////////////////////////////////////////////////////////////////////////
	public AclUser findAclUserById(Request request, String userById);

	public CompanyOther findCompanyOther(Request request);

	/**
	 * 查询合同
	 * 
	 * @param emsNo
	 *            合同号
	 * @param projectType
	 * @return
	 */
	public BaseEmsHead findBaseEmsHeadByEmsNo(Request request, String emsNo,
			Integer projectType);

	/**
	 * 查询 所有合同号
	 * 
	 * 
	 * @param projectType
	 * @return
	 */
	public List findBaseEmsHead(Request request, Integer projectType);

	public Integer findProjectType();

	/**
	 * 下载报关单数据
	 *
	 */
	public BaseCustomsDeclaration downloadDecData(Request request,
			BaseCustomsDeclaration decHead, String downloadDecData,
			Integer projectType);

	/**
	 * 海关删单
	 * 
	 * @param request
	 * @param decHead
	 * @param downloadDecData
	 * @param projectType
	 * @return
	 */
	public void customsDeleteDecl(Request request, String downloadDecData,
			Integer projectType);
}
