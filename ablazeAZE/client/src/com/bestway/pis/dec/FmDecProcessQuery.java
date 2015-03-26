package com.bestway.pis.dec;

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

public class FmDecProcessQuery extends JInternalFrameBase {
	private JToolBar toolBar;
	private JScrollPane scrollPane;
	private JTable table;
	private JButton btnTestConnect;
	private JButton btnShowProcess;
	private JButton btnClose;
	
	private JTableListModel tableModel = null;
	private PisAction pisAction = null;
	private PisVerificationAuthority pisVerificationAuthority = null;
	private Request request = null;
	public FmDecProcessQuery() {
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(getToolBar(), BorderLayout.NORTH);
		getContentPane().add(getScrollPane(), BorderLayout.CENTER);
		pisAction = (PisAction) CommonVars.getApplicationContext()
				.getBean("pisAction");
		pisVerificationAuthority = (PisVerificationAuthority) CommonVars
				.getApplicationContext().getBean("PisVerificationAuthority");
		request = new Request(CommonVars.getCurrUser());
		pisVerificationAuthority.checkDecProcessQuery(request);
        queryData();
	}
	
    private void queryData() {
        initTable(this.getDataSource());
    }
    
    private void initTable(List list) {
        tableModel = new JTableListModel(table, list, new JTableListModelAdapter() {

            @Override
            public List InitColumns() {
                List<JTableListColumn> list = new ArrayList<JTableListColumn>();
                list.add(new JTableListColumn("代理公司名称", "orgaName", 200));
                list.add(new JTableListColumn("服务器地址", "pisEspServer.serverAddress", 120));
                list.add(new JTableListColumn("端口", "pisEspServer.portNumber", 80));
                list.add(new JTableListColumn("邮箱", "email", 150));
                list.add(new JTableListColumn("联系人", "linkMan", 80));
                list.add(new JTableListColumn("手机号码", "tel", 80));
                list.add(new JTableListColumn("关区代码", "code", 80));
                return list;
            }
        });
//        tableModel.setTableCellActionListener(new String[]{"orgaName"}, new TableCellActionListener() {
//            @Override
//            public void run(TableCellEditorParameter parm) {
//                BrokerCorp brokerCorp = (BrokerCorp) parm.getEditingData();
//                if (!testConnnect(brokerCorp)) {
//                    JOptionPane.showMessageDialog(rootPane, "连接测试" + brokerCorp.getPisEspServer().getServerAddress() + ":" + brokerCorp.getPisEspServer().getPortNumber() + "失败，网络不通！");
//                }
//                queryProcess();
//            }
//        });
    }

	protected List getDataSource() {
		List resultList = new ArrayList();
		Set set = new HashSet();
		set.addAll(pisAction.findBrokerCorpByMainBusiness(
				new Request(CommonVars.getCurrUser()),
				EspMainBusinessType.BUSINESS_TYPE_EXP));
		set.addAll(pisAction.findBrokerCorpByMainBusiness(
				new Request(CommonVars.getCurrUser()),
				EspMainBusinessType.BUSINESS_TYPE_IMP));
		resultList.addAll(set);
		return resultList;
	}
	
	private void queryProcess() {
        BrokerCorp brokerCorp = (BrokerCorp) tableModel.getCurrentRow();
        String serverName = brokerCorp.getPisEspServer().getServerAddress();
        String serverPort = brokerCorp.getPisEspServer().getPortNumber();
        AclUser aclUser = pisAction.findAclUserById(new Request(CommonVars.getCurrUser()), CommonVars.getCurrUser().getId());
        String userEmail = aclUser.getEmail();
        String userPwd = aclUser.getPassword();
//        String urlAddress = "http://" + serverName + ":" + serverPort + "/esp-war/webdirect.jnlp.jsp?href=http%3A//" + serverName + "%3A" + serverPort + "/esp-war/&useremail=" + userEmail + "&userpwd=" + userPwd + "";//
        String urlAddress = "http://" + serverName + ":" + serverPort + "/esp-war/webdirect.jnlp.jsp?";
        urlAddress += "useremail=" + userEmail + "&userpwd=" + userPwd + "&orgacode="+brokerCorp.getOrgaCode() + "&isauthbrokercorp=0&href=";
        String para = "http://" + serverName + ":" + serverPort + "/esp-war/";
        para = URLEncoder.encode(para);
        urlAddress+=para;
        System.out.println(urlAddress);
        HttpClientInvoker clientInvoker = new HttpClientInvoker();
        Map<String, String> params = new HashMap();
        String result = clientInvoker.executeMethod(urlAddress, params, null);
        String filePath = this.getFilePath(brokerCorp.getOrgaCode() + ".jnlp");
        System.out.println("result=="+(result==null||"".equals(result.trim()))+"=="+result);
        System.out.println("------------------file path:" + filePath);
        try {
            FileUtils.writeStringToFile(new File(filePath), result, "utf-8");
        } catch (IOException ex) {
            Logger.getLogger(FmDecProcessQuery.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        try {
            Runtime.getRuntime().exec("javaws " + filePath + "");
        } catch (IOException ex) {
        }
    }
    
	private JToolBar getToolBar() {
		if (toolBar == null) {
			toolBar = new JToolBar();
			toolBar.add(getBtnTestConnect());
			toolBar.add(getBtnShowProcess());
			toolBar.add(getBtnClose());
		}
		return toolBar;
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
	private JButton getBtnTestConnect() {
		if (btnTestConnect == null) {
			btnTestConnect = new JButton("测试网络连接");
			btnTestConnect.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					BrokerCorp brokerCorp = (BrokerCorp) tableModel.getCurrentRow();
			        if (brokerCorp == null) {
			            JOptionPane.showMessageDialog(rootPane, "请选择代理报关公司！");
			            return;
			        }
			        if (testConnnect(brokerCorp)) {
			            JOptionPane.showMessageDialog(rootPane, "网络连接测试通过！");
			        } else {
			            JOptionPane.showMessageDialog(rootPane, "连接测试" + brokerCorp.getPisEspServer().getServerAddress() + ":" + brokerCorp.getPisEspServer().getPortNumber() + "失败，网络不通！");
			        }
				}
			});
		}
		return btnTestConnect;
	}
	private JButton getBtnShowProcess() {
		if (btnShowProcess == null) {
			btnShowProcess = new JButton("进度查看");
			btnShowProcess.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					pisVerificationAuthority.checkShowProcess(request);
					BrokerCorp brokerCorp = (BrokerCorp) tableModel.getCurrentRow();
			        if (brokerCorp == null) {
			            JOptionPane.showMessageDialog(rootPane, "请选择代理报关公司！");
			            return;
			        }
			        if (!testConnnect(brokerCorp)) {
			            JOptionPane.showMessageDialog(rootPane, "连接" + brokerCorp.getPisEspServer().getServerAddress() + ":" + brokerCorp.getPisEspServer().getPortNumber() + "测试失败，网络不通，不能进行进度查询！");
			            return;
			        }
			        queryProcess();
				}
			});
		}
		return btnShowProcess;
	}
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton("关闭");
			btnClose.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
		}
		return btnClose;
	}
	
    private String getFilePath(String fileName) {
        File path = new File(System.getProperty("java.io.tmpdir") + "TemporaryJnlp");
        if (!path.exists()) {
            path.mkdirs();
        }
        String filePath = System.getProperty("java.io.tmpdir") + "TemporaryJnlp" + File.separator + fileName;
        return filePath;
    }
    
    private boolean testConnnect(BrokerCorp brokerCorp) {
        String serverName = brokerCorp.getPisEspServer().getServerAddress();
        String serverPort = brokerCorp.getPisEspServer().getPortNumber();
        return NetUtil.testConnnect(serverName, Integer.valueOf(serverPort));
    }
}
