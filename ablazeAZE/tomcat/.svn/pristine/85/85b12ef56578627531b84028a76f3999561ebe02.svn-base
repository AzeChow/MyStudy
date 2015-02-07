<%@ page contentType="text/html; charset=UTF-8" import="javax.servlet.http.HttpUtils" %>
<HTML>
<BODY>
<%
String requestedUrl = HttpUtils.getRequestURL(request).toString();
requestedUrl=requestedUrl.substring(0,requestedUrl.lastIndexOf('/'));
requestedUrl+="/Webjbcus.jnlp.jsp?protocol=http";
%>
<OBJECT codebase="http://java.sun.com/update/1.6.0/jinstall-1_6-windows-i586.cab#Version=6,0,0,0" 
classid="clsid:5852F5ED-8BF4-11D4-A245-0080C6F74284" height=0 width=0>
<PARAM name="app" value="<%=requestedUrl%>">
<PARAM name="back" value="true">
<A href="jre/jre-6-windows-i586.exe">
请先安装Java运行环境</A>
</OBJECT>
</BODY>
</HTML>



