package com.bestway.bcus.client.cas.otherreport;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.InputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcus.cas.entity.TempTransFactCompareInfo;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.common.CommonUtils;

/**
 * 结转对照表分组打印选择
 * @author Administrator
 *
 */
public class DgTransFactCompareReportGroupSet extends JDialogBase {

	private JPanel jPanel = null;
	private JLabel jLabel = null;
	private ButtonGroup buttonGroup = null;  //  @jve:decl-index=0:
	private JRadioButton rbScmCoc = null;
	private JRadioButton rbScmCocCommname = null;
	private JButton btnOk = null;
	private JButton btnCalc = null;
	private Boolean groupSet = false;  //  @jve:decl-index=0:
	private List tableList = null;  //  @jve:decl-index=0:
	private Map map = null;  //  @jve:decl-index=0:
	private JLabel jLabel1 = null;
	/**
	 * This method initializes 
	 * 
	 */
	public DgTransFactCompareReportGroupSet(List List,Map map) {
		super();
		this.tableList = List;
		this.map = map;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new Dimension(358, 221));
        this.setContentPane(getJPanel());
        this.setTitle("选择分组打印方式");
		this.setResizable(false);
		initButtonGroup();
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(175, 23, 173, 61));
			jLabel1.setText("<html><body><font color='blue'>注：打印此报表的格式只适应于分组条件：供应商+商品名称+型号规格全选的情况！</font></body></html>");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(27, 21, 142, 17));
			jLabel.setText("请选择分组打印方式：");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel, null);
			jPanel.add(getRbScmCoc(), null);
			jPanel.add(getRbScmCocCommname(), null);
			jPanel.add(getBtnOk(), null);
			jPanel.add(getBtnCalc(), null);
			jPanel.add(jLabel1, null);
		}
		return jPanel;
	}
	/**
	 * This method initializes rbScmCoc	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRbScmCoc() {
		if (rbScmCoc == null) {
			rbScmCoc = new JRadioButton();
			rbScmCoc.setBounds(new Rectangle(72, 92, 98, 21));
			rbScmCoc.setSelected(true);
			rbScmCoc.setText("分客户打印");
		}
		return rbScmCoc;
	}

	/**
	 * This method initializes rbScmCocCommname	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRbScmCocCommname() {
		if (rbScmCocCommname == null) {
			rbScmCocCommname = new JRadioButton();
			rbScmCocCommname.setBounds(new Rectangle(180, 92, 128, 21));
			rbScmCocCommname.setText("分客户和品名打印");
		}
		return rbScmCocCommname;
	}
	
	/**
	 * This method initializes buttonGroup
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup initButtonGroup() {
		if (buttonGroup == null) {
			buttonGroup = new ButtonGroup();
			buttonGroup.add(getRbScmCoc());
			buttonGroup.add(getRbScmCocCommname());
		}
		return buttonGroup;
	}
	/**
	 * This method initializes btnOk	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton();
			btnOk.setBounds(new Rectangle(76, 140, 62, 23));
			btnOk.setText("确定");
			btnOk.addActionListener(new java.awt.event.ActionListener() {  //TempTransFactCompareInfo
				@SuppressWarnings("unchecked")
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(rbScmCoc.isSelected()){
						groupSet = true;
					}else{
						groupSet = false;
					}
					// 对list中的数据按照供应商、客户进行排序
							Collections.sort(tableList,
									new Comparator<TempTransFactCompareInfo>() {
										public int compare(
												TempTransFactCompareInfo o1,
												TempTransFactCompareInfo o2) {
											if (o1.getScmCocName() == null
													&& o2.getScmCocName() == null) {
												return 0;
											} else if (o2.getScmCocName() == null) {
												return 1;
											} else if (o1.getScmCocName() == null) {
												return -1;
											}

											return o1.getScmCocName()
													.compareTo(
															o2.getScmCocName());
										}
									});
					CustomReportDataSource ds = new CustomReportDataSource(tableList);
					InputStream reportStream = null;
					if(groupSet){
						 reportStream = DgTransFactCompareInfoOnMonth.class
						.getResourceAsStream("report/TransFactCompareInfoOnMonthByScmCocReport.jasper");
					}else{
						 reportStream = DgTransFactCompareInfoOnMonth.class
								.getResourceAsStream("report/TransFactCompareInfoOnMonthReport.jasper");
					}
					Map<String, Object> parameters = new HashMap<String, Object>();
					parameters.put("companyName", CommonVars.getCurrUser()
							.getCompany().getName());
					parameters.put("year", CommonUtils.getYear());
					parameters.put("scmCocName",map.get("scmCocName"));
					parameters.put("commName", map.get("commName"));
					parameters.put("scmCoc",map.get("scmCoc"));
					parameters.put("linkMan", map.get("linkMan"));
					parameters.put("tel", map.get("tel"));
					parameters.put("fax", map.get("fax"));
					JasperPrint jasperPrint;
					try {
						jasperPrint = JasperFillManager.fillReport(
								reportStream, parameters, ds);
						DgReportViewer viewer = new DgReportViewer(jasperPrint);
						viewer.setVisible(true);
					} catch (JRException e1) {
						e1.printStackTrace();
					}
				}
			});
		}
		return btnOk;
	}

	/**
	 * This method initializes btnCalc	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnCalc() {
		if (btnCalc == null) {
			btnCalc = new JButton();
			btnCalc.setBounds(new Rectangle(212, 140, 62, 23));
			btnCalc.setText("取消");
			btnCalc.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnCalc;
	}

	public Boolean getGroupSet() {
		return groupSet;
	}

	public void setGroupSet(Boolean groupSet) {
		this.groupSet = groupSet;
	}

	public List getTableList() {
		return tableList;
	}

	public void setTableList(List tableList) {
		this.tableList = tableList;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"
