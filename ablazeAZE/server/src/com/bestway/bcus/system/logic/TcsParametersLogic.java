package com.bestway.bcus.system.logic;

import com.bestway.bcus.system.dao.TcsParametersDao;
import com.bestway.bcus.system.entity.TcsLinkMan;
import com.bestway.bcus.system.entity.TcsParameters;

public class TcsParametersLogic {
	private TcsParametersDao tcsParametersDao;

	public void setTcsParametersDao(TcsParametersDao tcsParametersDao) {
		this.tcsParametersDao = tcsParametersDao;
	}
	
	public TcsParameters getTcsParameters() {
		return tcsParametersDao.getTcsParameters();
	}
	
	public void saveParams(TcsParameters tcsParameters) {
		TcsParameters p = tcsParametersDao.getTcsParameters();
		if(p != null) {
			p.setActionId(tcsParameters.getActionId());
			p.setBpNo(tcsParameters.getBpNo());
			p.setReceiverAddress(tcsParameters.getReceiverAddress());
			p.setReceiverId(tcsParameters.getReceiverId());
			p.setSenderAddress(tcsParameters.getSenderAddress());
			p.setSenderId(tcsParameters.getSenderId());
			p.setUserId(tcsParameters.getUserId());
			p.setUserPrivateKey(tcsParameters.getUserPrivateKey());
			p.setIcCardNo(tcsParameters.getIcCardNo());
			p.setCertificateNo(tcsParameters.getCertificateNo());
			p.setOganizationCode(tcsParameters.getOganizationCode());
			p.setOperatorName(tcsParameters.getOperatorName());
			
//			p.setFtpAddress(tcsParameters.getFtpAddress());
//			p.setFtpName(tcsParameters.getFtpName());
//			p.setFtpPwd(tcsParameters.getFtpPwd());
			
			p.setSendNote(tcsParameters.getSendNote());
			p.setBtplsAddress(tcsParameters.getBtplsAddress());
			p.setBtplsPort(tcsParameters.getBtplsPort());
		} else {
			p = tcsParameters;
		}
		
		tcsParametersDao.saveOrUpdate(p);
	}
	
	
	public TcsLinkMan getTcsLinkMan(){
		return tcsParametersDao.getTcsLinkMan();
	}
	
	public void saveLinkMan(TcsLinkMan tcsLinkMan) {
		TcsLinkMan linkMan = tcsParametersDao.getTcsLinkMan();
		if(linkMan != null) {
			linkMan.setAddress(tcsLinkMan.getAddress());
			linkMan.setDepartment(tcsLinkMan.getDepartment());
			linkMan.setDuty(tcsLinkMan.getDuty());
			linkMan.setEmail(tcsLinkMan.getEmail());
			linkMan.setFax(tcsLinkMan.getFax());
			linkMan.setImCode(tcsLinkMan.getImCode());
			linkMan.setImType(tcsLinkMan.getImType());
			linkMan.setMobile(tcsLinkMan.getMobile());
			linkMan.setName(tcsLinkMan.getName());
			linkMan.setTelephone(tcsLinkMan.getTelephone());
			linkMan.setZipCode(tcsLinkMan.getZipCode());
		} else {
			linkMan = tcsLinkMan;
		}
		
		tcsParametersDao.saveOrUpdate(linkMan);
	}
}
