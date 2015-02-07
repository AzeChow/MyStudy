/*
 * Created on 2005-6-10
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bestway.bcs.contract.dao.ContractDao;
import com.bestway.bcs.contract.entity.Contract;
import com.bestway.common.constant.ProjectType;
import com.bestway.customs.common.logic.BaseEncLogic;
import com.bestway.dzsc.dzscmanage.dao.DzscDao;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public class ImportCustomDeclarationServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private BaseEncLogic baseEncLogic;
	private DzscDao dzscDao;
	private ContractDao contractDao;

	public void init() throws ServletException {
		WebApplicationContext wac = WebApplicationContextUtils
		.getRequiredWebApplicationContext(getServletContext());
		if(dzscDao == null) {
			dzscDao = (DzscDao) wac.getBean("dzscDao");
		}
		if(contractDao == null) {
			contractDao = (ContractDao) wac.getBean("contractDao");
		}
	}

	public void destroy() {
		super.destroy();
	}

	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		WebApplicationContext wac = WebApplicationContextUtils
				.getRequiredWebApplicationContext(getServletContext());
		request.setCharacterEncoding("utf-8");
		int projectType = 0;
		String headStr = request.getParameter("headStr");
		String bodyStr = request.getParameter("bodyStr");
		String manual_no = headStr.split("\\|\\|")[2];
		
		if(manual_no.startsWith("E")) {
			projectType = ProjectType.BCUS;
		} else {
			Contract contract = contractDao.findExingContractByEmsNo(manual_no);
			if(contract != null) {
				projectType = ProjectType.BCS;
			} else {
				List list = dzscDao.findDzscEmsPorHeadByEmsNo(manual_no);
				if(list != null && list.size() > 0) {
					projectType = ProjectType.DZSC;
				}
			}
		}
		
		if(baseEncLogic == null) {
			if(projectType == ProjectType.BCUS) {
				
				baseEncLogic = (BaseEncLogic) wac.getBean("encLogic");
				
			} else if(projectType == ProjectType.BCS) {
				
				baseEncLogic = (BaseEncLogic) wac.getBean("contractExeLogic");
				
			} else if(projectType == ProjectType.DZSC) {
				
				baseEncLogic = (BaseEncLogic) wac.getBean("dzscContractExeLogic");
				
			} else {
				
				baseEncLogic = (BaseEncLogic) wac.getBean("");
			}
		}
		
		
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.print(baseEncLogic.buildXML(headStr, bodyStr));
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}
}
