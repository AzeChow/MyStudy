package com.bestway.bswmail.qp.action;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.Hashtable;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import com.bestway.bswmail.qp.entity.StatusInfo;
import com.bestway.common.message.entity.CspParameterSet;

public class FmTest extends JFrame {

	private JPanel jContentPane = null;
	private JButton jButton2 = null;
	/**
	 * This method initializes 
	 * 
	 */
	public FmTest() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new Dimension(451, 235));
        this.setTitle("Test");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setContentPane(getJContentPane());
			
	}

	/**
	 * This method initializes jContentPane	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJButton2(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jButton2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setBounds(new Rectangle(167, 108, 57, 36));
			jButton2.setText("test");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
//					MailSendStatusInfo info=new MailSendStatusInfo();
//					info.setSendList(new ArrayList());
//					info.setHashtable(new Hashtable());
					CspParameterSet paraSet=new CspParameterSet();
					paraSet.setSeqNo("OqkVnE3qlUGp8rRJnP6DZ2GPFU7fwVfMwfivT1xyFfdUdNXMssmL6w==");
					paraSet.setRemoteHostICPwd("12345678");
					paraSet.setReadFromCard(false);
					//paraSet.setRemoteSignData(true);
					paraSet.setRemoteHostAddress("192.168.1.180");
					//paraSet.setRemoteDxpMail(true);
//					System.out.println(DxpEusServiceClient.getInstance(paraSet).getSendMailStatus());
//					StatusInfo info= DxpEusServiceClient.getInstance(paraSet).getStatusInfo();
//					List list=info.getSendList();
//					System.out.println(info.getCurrentDate());
//					for(int i=0;i<list.size();i++){
//						System.out.println(list.get(i).toString()+"11");
//					}
					System.out.println(BswMailQpClient.getInstance(paraSet).getSendLogFileContent("ULOG2008-08-29.txt"));
					System.out.println(BswMailQpClient.getInstance(paraSet).getReceiveLogFileContent("ULOG2008-08-29.txt"));
					List list=BswMailQpClient.getInstance(paraSet).getSendLogFileNameList("2008-08-30","2008-09-30");
					for(int i=0;i<list.size();i++){
						System.out.println(list.get(i).toString());
					}
//					Hashtable hashtable=info.getHashtable();
//					System.out.println(hashtable.get("1"));
//					System.out.println(hashtable.get("2"));
//					System.out.println(hashtable.get("3"));
				}
			});
		}
		return jButton2;
	}

	private void setState(){
		//MailServiceStatus.SPARE;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FmTest fmTest=new FmTest();
		fmTest.setVisible(true);
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
