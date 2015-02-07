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
import com.bestway.pis.logic.PisLogic;

public class PisActionImp implements PisAction {

	private PisLogic pisLogic = null;

	// /////////////////////////////////////PisDecLogicImpl///////////////////////////////////////////////

	public void setPisLogic(PisLogic pisLogic) {
		this.pisLogic = pisLogic;
	}

	public <T> T saveOrUpdate(Request request, T entity) {
		return pisLogic.saveOrUpdate(request, entity);
	}

	/**
	 * 查询加工贸易报关单
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
			Boolean isCheck, Boolean isSend, Integer projectType) {
		return pisLogic.findDecHead(request, impExpFlag, emsNo, beginDate,
				endDate, isCheck, isSend, projectType);
	}

	/**
	 * 导出数据到物流&报关平台
	 *
	 * @param request
	 * @param head
	 * @return
	 */
	public String makeDecJSONData(Request request, BaseCustomsDeclaration head,
			Integer projectType) {
		return pisLogic.makeDecJSONData(request, head, projectType);
	}

	// /////////////////////////////////////////////////////////////////////////////////////

	// ///////////////////////////////PisGtsDecLogicImpl////////////////////////////////////////
	// ///////////////////////////////PisSyncLogicImpl////////////////////////////////////////

	public String getCompanyCode(Request request) {
		return pisLogic.getCompanyCode(request);
	}

	/**
	 * 查找代理公司
	 *
	 * @param request
	 * @return
	 */
	public List findBrokerCorp(Request request) {
		return pisLogic.findBrokerCorp(request);
	}

	/**
	 * 查找esp服务器
	 *
	 * @param request
	 * @return
	 */
	public List findPisEspServer(Request request) {
		return pisLogic.findPisEspServer(request);
	}

	/**
	 * 查找授权的esp服务器
	 *
	 * @param request
	 * @return
	 */
	public List findEspAuthorityBrokerCorpPisEspServer(Request request) {
		return pisLogic.findEspAuthorityBrokerCorpPisEspServer(request);
	}

	/**
	 *
	 * 同步代理公司
	 *
	 * @param request
	 * @param serverMap
	 * @param agtCompanyList
	 */
	public void syncBrokerCorp(Request request, Map<String, Map> serverMap,
			List<Map> agtCompanyList) {
		pisLogic.syncBrokerCorp(request, serverMap, agtCompanyList);
	}

	/**
	 * 查找同步账号esp
	 *
	 * @param request
	 * @return
	 */
	public List findAclUser(Request request) {
		return pisLogic.findAclUser(request);
	}

	/**
	 * 查找同步账号esp
	 *
	 * @param request
	 * @return
	 */
	public List findPisSyncAclUser(Request request) {
		return pisLogic.findPisSyncAclUser(request);
	}

	public PisSyncAclUser setPisSyncAclUser(PisSyncAclUser pUser, AclUser user) {
		return pisLogic.setPisSyncAclUser(pUser, user);
	}

	/**
	 * 查找同步申报单位esp
	 *
	 * @param request
	 * @return
	 */
	public List findPisSyncAgentCorp(Request request) {
		return pisLogic.findPisSyncAgentCorp(request);
	}

	/**
	 * 将新添加的用户添加到PisSyncAclUser
	 *
	 * @param request
	 */
	public void refreshPisSyncAclUser(Request request) {
		pisLogic.refreshPisSyncAclUser(request);
	}

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
			Map<PisEspServer, Map<String, String>> userErrMap) {
		pisLogic.updatePisSyncAclUser(request, pUserlist, infoMap, userErrMap);
	}

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
			Map<PisEspServer, Map<String, String>> agentErrMap) {
		pisLogic.updatePisSyncAgentCorp(request, pAgentlist, infoMap,
				agentErrMap);
	}

	/**
	 * 同步申报单位后更新
	 *
	 * @param request
	 * @param agtDataList
	 */
	public void updatePisSyncAgentCorp(Request request, List<String> agtDataList) {
		pisLogic.updatePisSyncAgentCorp(request, agtDataList);
	}

	/**
	 * 查找授权代理公司
	 *
	 * @param request
	 * @return
	 */
	public List findEspAuthorityBrokerCorp(Request request) {
		return pisLogic.findEspAuthorityBrokerCorp(request);
	}

	/**
	 * 查找授权代理公司明细
	 *
	 * @param request
	 * @param espAuthorityBrokerCorp
	 * @return
	 */
	public List findEspAuthorityBrokerCorpItem(Request request,
			EspAuthorityBrokerCorp espAuthorityBrokerCorp) {
		return pisLogic.findEspAuthorityBrokerCorpItem(request,
				espAuthorityBrokerCorp);
	}

	/**
	 * 授权代理公司
	 *
	 * @param request
	 * @param corpList
	 */
	public void authorityBrokerCorp(Request request, List<BrokerCorp> corpList) {
		pisLogic.authorityBrokerCorp(request, corpList);
	}

	/**
	 * 取消授权代理公司
	 *
	 * @param request
	 * @param bcList
	 */
	public void cancelAuthorityBrokerBrop(Request request,
			List<EspAuthorityBrokerCorp> bcList) {
		pisLogic.cancelAuthorityBrokerBrop(request, bcList);
	}

	public void saveEspAuthorityBrokerCorpItem(Request request,
			List<EspAuthorityBrokerCorpItem> itemList) {
		pisLogic.saveEspAuthorityBrokerCorpItem(request, itemList);
	}

	public void delEspAuthorityBrokerCorpItem(Request request,
			List<EspAuthorityBrokerCorpItem> entityList) {
		pisLogic.delEspAuthorityBrokerCorpItem(request, entityList);
	}

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
			Map<EspAuthorityBrokerCorp, Map<String, String>> syncErrMap) {
		pisLogic.updateEspAuthorityBrokerCorp(request, brokerlist, infoMap,
				syncErrMap);
	}

	/**
	 * 查询已授权的代理公司
	 *
	 * @return
	 */
	public List findBrokerCorpByMainBusiness(Request request,
			String mainBusiness) {
		return this.pisLogic
				.findBrokerCorpByMainBusiness(request, mainBusiness);
	}

	public AclUser findAclUserById(Request request, String userById) {
		return this.pisLogic.findAclUserById(request, userById);
	}

	public CompanyOther findCompanyOther(Request request) {
		return this.pisLogic.findCompanyOther(request);
	}

	/**
	 * 查询合同
	 * 
	 * @param emsNo
	 *            合同号
	 * @param projectType
	 * @return
	 */
	public BaseEmsHead findBaseEmsHeadByEmsNo(Request request, String emsNo,
			Integer projectType) {
		return this.pisLogic.findBaseEmsHeadByEmsNo(emsNo, projectType);
	}

	/**
	 * 查询 所有合同号
	 * 
	 * 
	 * @param projectType
	 * @return
	 */
	public List findBaseEmsHead(Request request, Integer projectType) {
		return pisLogic.findBaseEmsHead(projectType);
	}

	public Integer findProjectType() {
		return this.pisLogic.findProjectType();
	}

	/**
	 * 查找 Acluser
	 */
	@Override
	public List findSyncAclUserByLoginName(Request request, List loginNames,
			Class entityName) {

		return pisLogic.findAclUserByLoginName(request, loginNames, entityName);

	}

	/**
	 * 下载报关单数据
	 *
	 */
	public BaseCustomsDeclaration downloadDecData(Request request,
			BaseCustomsDeclaration decHead, String downloadDecData,
			Integer projectType) {
		return this.pisLogic.downloadDecData(request, decHead, downloadDecData,
				projectType);
	}

	public void customsDeleteDecl(Request request, String downloadDecData,
			Integer projectType) {
		this.pisLogic.customsDeleteDecl(request, downloadDecData, projectType);
	}
}
