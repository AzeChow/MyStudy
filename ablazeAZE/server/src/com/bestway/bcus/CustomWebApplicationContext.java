package com.bestway.bcus;

import java.io.IOException;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.context.support.XmlWebApplicationContext;

public class CustomWebApplicationContext extends XmlWebApplicationContext {

	/**
	 * The default location for the root context is
	 * "/WEB-INF/applicationContext.xml", and "/WEB-INF/test-servlet.xml" for a
	 * context with the namespace "test-servlet" (like for a DispatcherServlet
	 * instance with the servlet-name "test").
	 */
	protected String[] getDefaultConfigLocations() {
		return new String[] {};
	}

	protected void loadBeanDefinitions(XmlBeanDefinitionReader reader)
			throws BeansException, IOException {		
		String[] contexts = new String[] { "commoncontext/CommonContext.xml",
				"commoncontext/InvoiceContext.xml",
				"commoncontext/FecavContext.xml",
				"commoncontext/FinancialContext.xml",
				"commoncontext/FixAssetContext.xml",
				"commoncontext/FixtureContext.xml",
				"commoncontext/LicenseContext.xml",
				"commoncontext/MaterialContext.xml",
				"commoncontext/ErpBillContext.xml",
				"commoncontext/FptContext.xml",
				"commoncontext/TransferFactoryContext.xml",
				"commoncontext/ToolsContext.xml",
				"commoncontext/AptitudeReportContext.xml",
				"commoncontext/ErpBillCenterContext.xml",
				"commoncontext/OutsourceContext.xml",
				"commoncontext/WarningContext.xml",
				"commoncontext/DataExportContext.xml",
				"commoncontext/OrderCommonContext.xml",
				"commoncontext/BlsContext.xml",
				"commoncontext/WaijingContext.xml",
				"commoncontext/OwpContext.xml",
				"commoncontext/PisContext.xml",

				"bcuscontext/AuthorityContext.xml",
				"bcuscontext/BackupDatabase.xml", "bcuscontext/CasContext.xml",
				"bcuscontext/CheckCancelContext.xml",
				"bcuscontext/CheckToolsAuthorityContext.xml",
				"bcuscontext/CustomBaseContext.xml",
				"bcuscontext/DataImportContext.xml",
				"bcuscontext/DbimportContext.xml",
				"bcuscontext/EncContext.xml",
				"bcuscontext/InnerMergeContext.xml",
				"bcuscontext/CheckCancelAuthorityContext.xml",
				"bcuscontext/ManualDeclareContext.xml",
				"bcuscontext/ManualDeclareReportContext.xml",
				"bcuscontext/ImpCustomsAuthorityContext.xml",
				"bcuscontext/ExpCustomsAuthorityContext.xml",
				"bcuscontext/SpecialCustomsAuthorityContext.xml",
				"bcuscontext/MessageContext.xml",
				"bcuscontext/SystemContext.xml", 
				"bcuscontext/CheckStockContext.xml",
				
				"bcscontext/ContractAnalyseContext.xml",
				"bcscontext/ContractCavContext.xml",
				"bcscontext/ContractContext.xml",
				"bcscontext/ContractExeContext.xml",
				"bcscontext/ContractStatContext.xml",
				"bcscontext/BcsInnerMergeContext.xml",
				"bcscontext/BcsDictPorContext.xml",
				"bcscontext/BcsMessageContext.xml",
				"bcscontext/BCSImpCustomsAuthorityContext.xml",
				"bcscontext/BCSExpCustomsAuthorityContext.xml",
				"bcscontext/BCSSpecialCustomsAuthorityContext.xml",
				"bcscontext/VerificationContext.xml",

				"dzsccontext/DzscMessageContext.xml",
				"dzsccontext/MaterialApplyContext.xml",
				"dzsccontext/DzscInnerMergeContext.xml",
				"dzsccontext/DzscContext.xml",
				"dzsccontext/DzscListContext.xml",
				"dzsccontext/DzscContractExeContext.xml",
				"dzsccontext/DzscContractCavContext.xml",
				"dzsccontext/DzscAnalyseContext.xml",
				"dzsccontext/DzscStatContext.xml" ,
				"dzsccontext/DZSCImpCustomsAuthorityContext.xml",
				"dzsccontext/DZSCExpCustomsAuthorityContext.xml",
				"dzsccontext/DZSCSpecialCustomsAuthorityContext.xml"};
		for (int i = 0; i < contexts.length; i++) {
			ClassPathResource resource = new ClassPathResource(contexts[i]);
			reader.loadBeanDefinitions(resource);
		}
	}

}
