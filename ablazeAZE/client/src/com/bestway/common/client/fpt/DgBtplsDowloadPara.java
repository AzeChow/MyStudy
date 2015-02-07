package com.bestway.common.client.fpt;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.text.NumberFormatter;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.common.fpt.btplsinput.action.BtplsDownloadParaAction;
import com.bestway.common.fpt.btplsinput.entity.BtplsDownloadPara;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgBtplsDowloadPara extends JDialogBase {
	

	private JPanel jContentPane = null;

	private JLabel lbCode = null;

	private JLabel lbName = null;

	private JButton btnOk = null;

	private JButton btnCancel = null;

	private boolean isOk = false;

	private Complex complex = null;  //  @jve:decl-index=0:

	private BtplsDownloadParaAction btplsDowloadParaAction = null;

	private NumberFormatter numberFormatter = null; // @jve:decl-index=0:parse

	private JLabel jLabel = null;

	private JComboBox cbbScmCoc = null;

	private JTextField tfIpAddre = null;

	private JTextField tfPort = null;
	
	private BtplsDownloadPara  btplsDowloadPara =null;
	
	private int dataState = -1;

	/**
	 * This method initializes
	 * 
	 */
	public DgBtplsDowloadPara() {
		super();
		btplsDowloadParaAction = (BtplsDownloadParaAction) CommonVars
				.getApplicationContext().getBean("btplsDownloadParaAction");
		initialize();
	}

	@Override
	public void setVisible(boolean b) {
		if (b) {
			initUIComponents();
			if(dataState==DataState.ADD){
				iniAddData();
			}else{
				showData(btplsDowloadPara);
			}
			
		}
		super.setVisible(b);
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(407, 281));
		this.setContentPane(getJContentPane());
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("深加工结转平台下载参数");

	}
	/**
	 * 初始化新增
	 */
	private void iniAddData(){
		this.tfIpAddre.setText("");
		this.cbbScmCoc.setSelectedItem(null);
		this.tfPort.setText("");
	}
	
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(78, 118, 78, 18));
			jLabel.setText("客户/供应商：");
			lbName = new JLabel();
			lbName.setBounds(new Rectangle(78, 74, 59, 18));
			lbName.setText("端口：");
			lbCode = new JLabel();
			lbCode.setBounds(new Rectangle(79, 30, 51, 18));
			lbCode.setText("IP地址：");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(lbCode, null);
			jContentPane.add(lbName, null);
			jContentPane.add(getBtnOk(), null);
			jContentPane.add(getBtnCancel(), null);
			jContentPane.add(jLabel, null);
			jContentPane.add(getCbbScmCoc(), null);
			jContentPane.add(getTfIpAddre(), null);
			jContentPane.add(getTfPort(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes btnOk
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton();
			btnOk.setBounds(new Rectangle(95, 184, 80, 24));
			btnOk.setText("确定");
			btnOk.addActionListener(new java.awt.event.ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					fillData();
					if(btplsDowloadPara!=null){
						btplsDowloadParaAction.saveBtplsDownloadPara(btplsDowloadPara);
						isOk = true;
						dispose();
					}
				}
			});
		}
		return btnOk;
	}

	/**
	 * This method initializes btnCancel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(new Rectangle(244, 184, 79, 23));
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					isOk = false;
					dispose();
				}
			});
		}
		return btnCancel;
	}

	private void initUIComponents() {
		List list= new ArrayList();
		list = btplsDowloadParaAction.findScmManuFactory();
		this.cbbScmCoc.setModel(new DefaultComboBoxModel(list.toArray()));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbScmCoc,
				"code", "name", 250);
		this.cbbScmCoc.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 80, 170));
	}

	/**
	 * 显示数据
	 * @param btplsDowloadPara
	 */
	private void showData(BtplsDownloadPara btplsDowloadPara) {
		if(btplsDowloadPara!=null){
			if(btplsDowloadPara.getIpAddre()!=null){
				this.tfIpAddre.setText(btplsDowloadPara.getIpAddre());
			}
			if(btplsDowloadPara.getPort()!=null){
				this.tfPort.setText(btplsDowloadPara.getPort());
			}
			this.cbbScmCoc.setSelectedItem(btplsDowloadPara.getScmCoc());
		}
	}

	private Double strToDouble(String value) { // 转换strToDouble 填充数据
		try {
			if (value == null || "".equals(value)) {
				// return new Double("0");
				return null;
			}
			getNumberFormatter().setValueClass(Double.class);
			return (Double) getNumberFormatter().stringToValue(value);
		} catch (ParseException e) {
			e.printStackTrace();
			// return new Double("0");
			return null;
		}
	}

	private NumberFormatter getNumberFormatter() {
		if (numberFormatter == null) {
			numberFormatter = new NumberFormatter();
			DecimalFormat decimalFormat1 = new DecimalFormat(); // @jve:decl-index=0:
			decimalFormat1.setMaximumFractionDigits(6);
			numberFormatter.setFormat(decimalFormat1);
		}
		return numberFormatter;
	}

	private String doubleToStr(Double value) { // 转换doubleToStr 取数据
		try {
			if (value == null || value.doubleValue() == 0) {
				return null;
			}
			getNumberFormatter().setValueClass(Double.class);
			return getNumberFormatter().valueToString(value);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 
	 * 填充对象
	 */
	private void fillData() {
		if("".equals(tfIpAddre.getText().trim())){
			JOptionPane.showMessageDialog(DgBtplsDowloadPara.this, "请填写下载地址", "提示！",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if("".equals(tfPort.getText().trim())){
			JOptionPane.showMessageDialog(DgBtplsDowloadPara.this, "请填写下载端口", "提示！",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		ScmCoc sc = (ScmCoc)cbbScmCoc.getSelectedItem();
		if(sc==null){
			JOptionPane.showMessageDialog(DgBtplsDowloadPara.this, "选择一个客户或供应商", "提示！",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if(btplsDowloadPara==null){
			btplsDowloadPara = new BtplsDownloadPara();
		}
		btplsDowloadPara.setIpAddre(tfIpAddre.getText().trim());
		btplsDowloadPara.setPort(tfPort.getText().trim());
		btplsDowloadPara.setScmCoc((ScmCoc)cbbScmCoc.getSelectedItem());
	}

	public Complex getComplex() {
		return complex;
	}

	public void setComplex(Complex complex) {
		this.complex = complex;
	}

	public boolean isOk() {
		return isOk;
	}

	public void setOk(boolean isOk) {
		this.isOk = isOk;
	}

	/**
	 * This method initializes cbbScmCoc	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbScmCoc() {
		if (cbbScmCoc == null) {
			cbbScmCoc = new JComboBox();
			cbbScmCoc.setBounds(new Rectangle(158, 114, 172, 22));
		}
		return cbbScmCoc;
	}

	/**
	 * This method initializes tfIpAddre	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfIpAddre() {
		if (tfIpAddre == null) {
			tfIpAddre = new JTextField();
			tfIpAddre.setBounds(new Rectangle(158, 30, 172, 22));
		}
		return tfIpAddre;
	}

	/**
	 * This method initializes tfPort	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfPort() {
		if (tfPort == null) {
			tfPort = new JTextField();
			tfPort.setBounds(new Rectangle(158, 74, 172, 22));
		}
		return tfPort;
	}

	public int getDataState() {
		return dataState;
	}

	public void setDataState(int dataState) {
		this.dataState = dataState;
	}

	public BtplsDownloadPara getBtplsDowloadPara() {
		return btplsDowloadPara;
	}

	public void setBtplsDowloadPara(BtplsDownloadPara btplsDowloadPara) {
		this.btplsDowloadPara = btplsDowloadPara;
	}
	
}  //  @jve:decl-index=0:visual-constraint="10,10"
