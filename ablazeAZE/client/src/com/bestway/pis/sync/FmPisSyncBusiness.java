package com.bestway.pis.sync;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.authority.entity.AclUser;
import com.bestway.pis.action.PisAction;
import com.bestway.pis.action.PisVerificationAuthority;
import com.bestway.pis.common.HttpClientInvoker;
import com.bestway.pis.common.NetUtil;
import com.bestway.pis.constant.EspMainBusinessType;
import com.bestway.pis.entity.BrokerCorp;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.util.FileUtils;

import java.awt.BorderLayout;

import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FmPisSyncBusiness extends JInternalFrameBase {
	private JScrollPane scrollPane;
	private JTable table;
	
	private JTableListModel tableModel = null;
	private PisAction pisAction = null;
	private PisVerificationAuthority pisVerificationAuthority = null;
	private Request request = null;
	private JToolBar toolBar;
	private JButton btnQueryProcess;
	public FmPisSyncBusiness() {
		this.setTitle("云平台业务授权");
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(getToolBar(), BorderLayout.NORTH);
		getContentPane().add(getScrollPane());
		pisAction = (PisAction) CommonVars.getApplicationContext()
				.getBean("pisAction");
//		pisVerificationAuthority = (PisVerificationAuthority) CommonVars
//				.getApplicationContext().getBean("PisVerificationAuthority");
		request = new Request(CommonVars.getCurrUser());
//		pisVerificationAuthority.checkDecProcessQuery(request);
        queryData();
	}
	
    private void queryData() {
    	initTable(this.getDataSource());
    }
    
    protected List getDataSource() {
      List resultList = new ArrayList();
      Set set = new HashSet();
//      set.addAll(pisAction.findBrokerCorpByMainBusiness(request, EspMainBusinessType.BUSINESS_TYPE_EXP));
//      set.addAll(pisAction.findBrokerCorpByMainBusiness(request, EspMainBusinessType.BUSINESS_TYPE_IMP));
      set.addAll(pisAction.findBrokerCorp(new Request(CommonVars.getCurrUser())));
      resultList.addAll(set);
      return resultList;
  }

    
    private void initTable(List list) {
        tableModel = new JTableListModel(table, list, new JTableListModelAdapter() {

            @Override
            public List InitColumns() {
                List<JTableListColumn> list = new ArrayList<JTableListColumn>();
                list.add(new JTableListColumn("代理公司名称", "orgaName", 200));
                list.add(new JTableListColumn("服务器地址", "pisEspServer.serverAddress", 120));
                list.add(new JTableListColumn("端口", "pisEspServer.portNumber", 80));
                return list;
            }
        });
    }

	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTable());
		}
		return scrollPane;
	}
	private JTable getTable() {
		if (table == null) {
			table = new JTable();
		}
		return table;
	}
	
	private JToolBar getToolBar() {
		if (toolBar == null) {
			toolBar = new JToolBar();
			toolBar.add(getBtnQueryProcess());
		}
		return toolBar;
	}
	private JButton getBtnQueryProcess() {
		if (btnQueryProcess == null) {
			btnQueryProcess = new JButton("查询进度");
			btnQueryProcess.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					BrokerCorp brokerCorp = (BrokerCorp) tableModel.getCurrentRow();
	                if (!testConnnect(brokerCorp)) {
	                    JOptionPane.showMessageDialog(rootPane, "连接测试" + brokerCorp.getPisEspServer().getServerAddress() + ":" + brokerCorp.getPisEspServer().getPortNumber() + "失败，网络不通！");
	                }
	                queryProcess();
				}
			});
		}
		return btnQueryProcess;
	}
	
    private boolean testConnnect(BrokerCorp brokerCorp) {
        String serverName = brokerCorp.getPisEspServer().getServerAddress();
        String serverPort = brokerCorp.getPisEspServer().getPortNumber();
        return NetUtil.testConnnect(serverName, Integer.valueOf(serverPort));
    }
    
    private void queryProcess() {
        System.out.println("======执行queryProcess()方法开始======");
        BrokerCorp brokerCorp = (BrokerCorp) tableModel.getCurrentRow();
        String serverName = brokerCorp.getPisEspServer().getServerAddress();
        String serverPort = brokerCorp.getPisEspServer().getPortNumber();
        AclUser aclUser = pisAction.findAclUserById(request, CommonVars.getCurrUser().getId());
        String userEmail = aclUser.getEmail();
        String userPwd = aclUser.getPassword();
        String urlAddress = "http://" + serverName + ":" + serverPort + "/esp-war/webdirect.jnlp.jsp?href=http%3A//" + serverName + "%3A" + serverPort + "/esp-war/"
                + "&useremail=" + userEmail + "&userpwd=" + userPwd
                + "&orgacode=" + brokerCorp.getOrgaCode() + "&isauthbrokercorp=1";//
        System.out.println("=======urlAddress:" + urlAddress);
        HttpClientInvoker clientInvoker = new HttpClientInvoker();
        Map<String, String> params = new HashMap();
        String result = clientInvoker.executeMethod(urlAddress, params, null);
        String filePath = this.getFilePath(brokerCorp.getOrgaCode() + ".jnlp");
        System.out.println("------------------file path:" + filePath);
        try {
            System.out.println("==============writeStringToFile开始===============");
            FileUtils.writeStringToFile(new File(filePath), result, "utf-8");
            System.out.println("==============writeStringToFile结束===============");
        } catch (IOException ex) {
            Logger.getLogger(FmPisSyncBusiness.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("==============write报错：" + ex);
            return;
        }
        try {
            System.out.println("==============javaws开始===============");
            String command = "cmd /c \"javaws " + filePath + "\"";
            System.out.println("执行命令：" + command);
            Runtime.getRuntime().exec(command);
            System.out.println("==============javaws结束===============");
        } catch (IOException ex) {
            System.out.println("==============javaws报错：" + ex);
        }
        System.out.println("======执行queryProcess()方法结束======");
    }
    
    private String getFilePath(String fileName) {
        File path = new File(System.getProperty("java.io.tmpdir") + "TemporaryJnlp");
        if (!path.exists()) {
            path.mkdirs();
        }
        String filePath = System.getProperty("java.io.tmpdir") + "TemporaryJnlp" + File.separator + fileName;
        return filePath;
    }
}
