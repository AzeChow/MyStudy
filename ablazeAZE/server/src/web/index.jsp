<html>
	<%@ page contentType="text/html; charset=GBK"
		import="java.util.List,
	com.bestway.bcus.license.entity.LicenseInfo,
	com.bestway.bcus.license.LicenseInfoCache,
	java.text.DateFormat,
	com.bestway.bcus.MemoryInfoFactory,
	com.bestway.bcus.MemoryInfo,
	com.bestway.bcus.license.ClientInfoCache,
	com.bestway.bcus.license.entity.ClientInfo"%>
	<head>
		<title>服务器信息</title>
	</head>
	<body>
		<div align="center" width="100%">
			<table border="0" cellPadding="2" cellSpacing="2" width="600"
				height="1">
				<tr bgColor=#efefef>
					<td align=center height="50">
						<font size="5" color="#800000">服务器端信息显示</font>
					</td>
				</tr>
				<tr bgColor=#efefef>
					<td valign="top" height="1">
						<table cellpadding="0" cellspacing="0" width="501" align=center
							height="1">
							<%List cache=ClientInfoCache.getClientInfo(); %>
							<tr>
								<td width="106" height="1">
									电脑名称
								</td>
								<td width="112" height="1">
									IP地址
								</td>
								<td width="46" height="1">
									序号
								</td>
								<td width="107" height="1">
									登陆时间
								</td>
								<td width="131" height="1">
									最后一次呼叫时间
								</td>
							</tr>
							<%for(int i=0;i<cache.size();i++) {
			  ClientInfo clientInfo=(ClientInfo)cache.get(i);
			%>
							<tr>
								<td width="106" height="1">
									<%=clientInfo.getClientName() %>
								</td>
								<td width="112" height="1">
									<%=clientInfo.getClientIP() %>
								</td>
								<td width="46" height="1">
									<%=clientInfo.getCommonClientNo() %>
								</td>
								<td width="107" height="1">
									<%=clientInfo.getClientLoginTime() %>
								</td>
								<td width="131" height="1">
									<%=clientInfo.getLastCallTime() %>
								</td>
							</tr>
							<%}%>
						</table>
					</td>
				</tr>
				<tr bgColor=#efefef>
					<td valign="top" height="1">
						<table border="0" cellpadding="2" cellspacing="2" width="501"
							align=center height="1">
							<tr>
								<td width="434" height="1">
									<%=LicenseInfoCache.getInstance().getLicenseInfoString()%>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr bgColor=#efefef>
					<td valign="top" height="1">
						<table border="0" cellpadding="2" cellspacing="2" width="501"
							align=center height="1">
							<%MemoryInfo memoryInfo = MemoryInfoFactory.getInstance().getMemoryInfo();%>
							<tr>
								<td width="174" height="1">
									系统允许最大使用内存
								</td>
								<td width="319" height="1">
									<%=memoryInfo.getMaxMemory()%>
								</td>
							</tr>
							<tr>
								<td width="174" height="1">
									总分配内存
								</td>
								<td width="319" height="1">
									<%=memoryInfo.getTotalMemory()%>
								</td>
							</tr>
							<tr>
								<td width="174" height="1">
									已使用内存
								</td>
								<td width="319" height="1">
									<%=memoryInfo.getUsedMemory()%>
								</td>
							</tr>
							<tr>
								<td width="174" height="1">
									未使用内存
								</td>
								<td width="319" height="1">
									<%=memoryInfo.getFreeMemory()%>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>

	</body>

</html>
