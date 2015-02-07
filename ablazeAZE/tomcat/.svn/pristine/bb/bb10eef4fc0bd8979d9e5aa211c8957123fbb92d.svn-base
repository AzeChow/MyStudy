<%@ page contentType="text/html; charset=UTF-8" import="java.io.*,java.util.Date,java.util.regex.Matcher,java.util.regex.Pattern,javax.servlet.http.HttpUtils" %><%!
	public void getJarHref(String path,String folder,StringBuffer sb){
		String folderPath = path+"/"+folder;			
		
		File file = new File(folderPath);
		if(!file.exists()){
			return;
		}
		File[] files = file.listFiles();
		for (int i=0;i<files.length;i++) {
			File f = files[i];
			String fileName = f.getName().trim();
			if (f.isDirectory() == true || !fileName.endsWith(".jar")) {
				continue;
			}
			if(fileName.equals("jbcusclient.jar")){
				continue;
			}
			try {
				String str = "<jar href=\"" + folder + "/" + fileName+"\"/>	" + "\n";
				sb.append(str);
			} catch (Exception ex) {
			}
		}
		}
%><%	
   try {
		String protocol = request.getParameter("protocol");
		if(protocol==null){
			protocol = "";
		}
        String requestedUrl = HttpUtils.getRequestURL(request).toString();
		String url = requestedUrl.substring(requestedUrl.indexOf("//")+2,requestedUrl.length());
        String hostAddress = url.substring(0,url.indexOf(":"));
        if("localhost".equalsIgnoreCase(hostAddress.trim())){
        	hostAddress = "127.0.0.1";
        }
        String port= url.substring(url.indexOf(":")+1,url.indexOf("/"));
        if(port==null||"".equals(port.trim())){
        	port="80";
        }
        String tempContextPath = url.substring(url.indexOf("/")+1,url.length());
		String contextPath = "";
        if(tempContextPath.indexOf("/")!=-1){
            contextPath = tempContextPath.substring(0,tempContextPath.indexOf("/"));    
        }  
	String rootPath = request.getRealPath("/");
        File file = new File(rootPath+"/Webjbcustemp.jnlp");  
        if (!file.exists()) {
           out.println("不存在 Webjbcustemp.jnlp !");
           return;
        }
        
        StringBuffer jarHrefSb = new StringBuffer();	                      
              
        //clientlib
		//clientplugins
		//reportplugins         
		getJarHref(rootPath,"clientlib",jarHrefSb);
		getJarHref(rootPath,"clientplugins",jarHrefSb);
		getJarHref(rootPath,"reportplugins",jarHrefSb);
		
   		FileInputStream fis = new FileInputStream(file);
  		StringBuffer fout = new StringBuffer();
  		int length=0;
  		int c;
    	while ((c = fis.read()) != -1){
       		fout.append((char)c);
       		++length;
	    }
	  	fis.close();
          
        String outStr =new String(fout.toString().getBytes("8859_1"),"UTF-8") ;

	    Pattern hostAddressPatt = Pattern.compile("#hostAddress#");  
		Pattern portPatt = Pattern.compile("#port#");  
		Pattern contextPathPatt = Pattern.compile("#contextPath#"); 
        Pattern protocolPatt = Pattern.compile("#protocol#"); 
        Pattern jarHrefPatt = Pattern.compile("#jarHref#"); 
        

        Matcher hostAddressMatcher = hostAddressPatt.matcher(outStr);            
        if (hostAddressMatcher.find()) {
            outStr = hostAddressMatcher.replaceAll(hostAddress);                                
        } 
        Matcher portMatcher = portPatt.matcher(outStr);            
        if (portMatcher.find()) {
            outStr = portMatcher.replaceAll(port);                                
        }  
        Matcher contextPathMatcher = contextPathPatt.matcher(outStr);            
        if (contextPathMatcher.find()) {
            outStr = contextPathMatcher.replaceAll(contextPath);                                
        }        
        
		Matcher protocolMatcher = protocolPatt.matcher(outStr);            
        if (protocolMatcher.find()) {
            outStr = protocolMatcher.replaceAll(protocol);                                
        }  
        Matcher jarHrefMatcher = jarHrefPatt.matcher(outStr);            
        if (jarHrefMatcher.find()) {
            outStr = jarHrefMatcher.replaceAll(jarHrefSb.toString());  

        }

        out.print(outStr);
        response.setContentType("application/x-java-jnlp-file");
        response.setHeader("Content-Disposition","attachment; filename=" + java.net.URLEncoder.encode("jbcus.jnlp", "UTF-8"));
          } catch (Exception ex) {
        out.println(ex.toString());
        return;
    }
%>