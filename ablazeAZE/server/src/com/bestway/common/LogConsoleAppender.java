package com.bestway.common;

import org.apache.log4j.spi.LoggingEvent;

import com.bestway.bcus.system.entity.Company;
import com.bestway.common.logcommit.BswServer;
import com.bestway.common.logcommit.BswServerService;

public class LogConsoleAppender extends org.apache.log4j.ConsoleAppender {

	private StringBuffer sb = new StringBuffer();

	private BswServerService service = null;

	private BswServer server = null;

	public LogConsoleAppender() {
//		super();
//		try {
//			service = new BswServerService();
//			server = service.getServerPort();
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//		System.out.println("-----------------new LogConsoleAppender");
	}

	@Override
	public void append(LoggingEvent event) {
		try {
			Company company = (Company) CommonUtils.getCompany();
			if (server != null && event.getThrowableInformation() != null) {
				String[] strs = event.getThrowableInformation()
						.getThrowableStrRep();
				if (strs != null && strs.length > 0) {
					sb = new StringBuffer();
					for (int i = 0; i < strs.length; i++) {
						sb.append(strs[i] + "\n");
					}
					if (sb.toString().indexOf(
							"com.bestway.common.authority.AuthorityException") < 0) {
						server.sendBug((company == null ? "系统启动时错误" : ((company
								.getCode() == null || "".equals(company
								.getCode().trim())) ? "加工单位为空的公司" : company
								.getCode())), "7.04", sb.toString());
					}
				}
			}
			super.append(event);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
