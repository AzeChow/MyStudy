/*
 * Created on 2005-6-8
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.client.contractanalyse;

import java.awt.BorderLayout;
import java.awt.Component;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpType;
import com.bestway.dzsc.contractanalyse.action.DzscAnalyseAction;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import javax.swing.JToolBar;
import javax.swing.JSplitPane;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgDzscPreCustomsDeclaration extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private JPanel jPanel = null;

	private JLabel jLabel = null;

	private JScrollPane jScrollPane1 = null;

	private JList jList = null;

	private JCalendarComboBox cbbBeginDate = null;

	private JCalendarComboBox cbbEndDate = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JButton jButton2 = null;

	private JTableListModel tableModel = null;

	private DzscAnalyseAction contractAnalyseAction = null;

	private JCheckBox jCheckBox = null;

	private JToolBar jToolBar = null;

	private JPanel jPanel1 = null;

	private JLabel jLabel3 = null;

	private JLabel jLabel1 = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel2 = null;
	/**
	 * This is the default constructor
	 */
	public DgDzscPreCustomsDeclaration() {
		super();
		initialize();
		contractAnalyseAction = (DzscAnalyseAction) CommonVars
				.getApplicationContext().getBean("dzscAnalyseAction");
		
	}

	public void setVisible(boolean b) {
		if (b) {
			initUIComponents();
			queryData();
		}
		super.setVisible(b);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setResizable(true);
		this.setTitle("报关单预录入库查询");
		this.setSize(750, 510);
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
		}
		return jTable;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jLabel = new JLabel();
			jPanel.setLayout(new BorderLayout());
			jPanel.setBorder(javax.swing.BorderFactory
					.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
			jLabel.setText("正在执行的合同");
			jLabel.setForeground(java.awt.Color.blue);
			jLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
			jPanel.add(jLabel, java.awt.BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJList());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jList
	 * 
	 * @return javax.swing.JList
	 */
	private JList getJList() {
		if (jList == null) {
			jList = new JDzscContractList();
            jList.addMouseListener(new java.awt.event.MouseAdapter() {
            	public void mouseClicked(java.awt.event.MouseEvent e) {
            		Date beginDate=null;
//            		Date endDate=null;
            		int size=jList.getModel().getSize();
            		for(int i=0;i<size;i++){
            			DzscEmsPorHead contract=(DzscEmsPorHead)jList.getModel().getElementAt(i);
            			if(contract.isSelected()){
            				if(beginDate==null){
            					beginDate=contract.getBeginDate();
            				}else{
            					if(beginDate.compareTo(contract.getBeginDate())>0){
            						beginDate=contract.getBeginDate();
            					}
            				}
            			}            			
            		}
            		if(beginDate!=null){
            			cbbBeginDate.setDate(beginDate);
            		}
            	}
            });
		}
		return jList;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(new java.awt.Rectangle(61,3,83,23));
		}
		return cbbBeginDate;
	}

	/**
	 * This method initializes jCalendarComboBox1
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbEndDate() {
		if (cbbEndDate == null) {
			cbbEndDate = new JCalendarComboBox();
			cbbEndDate.setBounds(new java.awt.Rectangle(158,4,84,23));
		}
		return cbbEndDate;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("刷新");
			jButton.setBounds(new java.awt.Rectangle(388,4,63,23));
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					queryData();
				}
			});
		}
		return jButton;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("打印");
			jButton1.setBounds(new java.awt.Rectangle(456,4,65,23));
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(tableModel==null||tableModel.getList().size()<=0){
						JOptionPane.showMessageDialog(
								DgDzscPreCustomsDeclaration.this, "没有数据可打印", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					CustomReportDataSource ds = new CustomReportDataSource(tableModel.getList());
					InputStream reportStream = DgDzscPreCustomsDeclaration.class
                    .getResourceAsStream("report/PreCustomsDeclaration.jasper");
					Map<String,String> parameters = new HashMap<String,String>();
	                parameters.put("companyName",CommonVars.getCurrUser().getCompany().getName());
	                List contracts=((JDzscContractList)jList).getSelectedContracts();
	                StringBuffer sb=new StringBuffer("");
	                for(int i=0;i<contracts.size();i++){
	                	DzscEmsPorHead contract=(DzscEmsPorHead)contracts.get(i);
                		sb.append(contract.getIeContractNo()+"/"+contract.getEmsNo()+(i!=contracts.size()-1?";":""));
	                }
	                if("".equals(sb.toString().trim())){
	                	sb.append("所有手册");
	                }
	                parameters.put("emsNo",sb.toString());
	                String beginEndDate="";
	                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	                if(cbbBeginDate.getDate()!=null){
	                	beginEndDate+=("开始:"+dateFormat.format(cbbBeginDate.getDate()));
	                }
	                if(cbbEndDate.getDate()!=null){
	                	beginEndDate+=("  到:"+dateFormat.format(cbbEndDate.getDate()));
	                }
	                parameters.put("beginEndDate",beginEndDate);
	                JasperPrint jasperPrint =null;
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
		return jButton1;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("关闭");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton2;
	}

	private void queryData() {
		List contracts=((JDzscContractList)jList).getSelectedContracts();
//		String wrapCode = null;
//		if (cbbWrapType.getSelectedItem() != null) {
//			Wrap wrap = (Wrap) cbbWrapType.getSelectedItem();
//			wrapCode = wrap.getCode();
//		}
		List list = contractAnalyseAction.findPreCustomsDeclaration(new Request(CommonVars
				.getCurrUser()), contracts, cbbBeginDate.getDate(), cbbEndDate
				.getDate(), jCheckBox.isSelected());
		initTable(list);
	}

	/**
	 * 初始化数据Table
	 */
	private void initTable(List list) {
		if (list == null) {
			list = new ArrayList();
		}
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("海关编号", "customsDeclarationCode", 150));
						list.add(addColumn("预录入号", "preCustomsDeclarationCode", 250));
						list.add(addColumn("申报日期", "declarationDate", 100));
						list.add(addColumn("放行日期", "impExpDate", 100));
						list.add(addColumn("报关单类型", "impExpType", 100));
						return list;
					}
				});
        jTable.getColumnModel().getColumn(5).setCellRenderer(
                new DefaultTableCellRenderer() {
                    public Component getTableCellRendererComponent(
                            JTable table, Object value, boolean isSelected,
                            boolean hasFocus, int row, int column) {
                        super.getTableCellRendererComponent(table, value,
                                isSelected, hasFocus, row, column);
                        String str = "";
                        if (value != null) {
                        	str=CommonVars.getImpExpTypeName(value.toString());
//                            switch (Integer.parseInt(value.toString())) {
//                            case ImpExpType.DIRECT_EXPORT:
//                                str = "成品出口";
//                                break;
//                            case ImpExpType.TRANSFER_FACTORY_EXPORT:
//                                str = "转厂出口";
//                                break;
//                            case ImpExpType.BACK_MATERIEL_EXPORT:
//                                str = "退料出口";
//                                break;
//                            case ImpExpType.REWORK_EXPORT:
//                                str = "返工复出";
//                                break;
//                            case ImpExpType.REMIAN_MATERIAL_BACK_PORT:
//                                str = "边角料退港";
//                                break;
//                            case ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES:
//                                str = "边角料内销";
//                                break;
//                            case ImpExpType.GENERAL_TRADE_EXPORT:
//                                str = "一般贸易出口";
//                                break;
//                            }
                        }
                        this.setText(str);
                        return this;
                    }
                });
	}

	private void initUIComponents() {

		// 初始化包装种类
//		this.getCbbWrapType().setModel(
//				CustomBaseModel.getInstance().getWrapModel());
//		this.getCbbWrapType().setRenderer(
//				CustomBaseRender.getInstance().getRender());
//		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
//				this.getCbbWrapType());
	}
	/**
	 * This method initializes jCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */    
	private JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			jCheckBox = new JCheckBox();
			jCheckBox.setText("只显示生效报关单");
			jCheckBox.setBounds(new java.awt.Rectangle(245,4,129,22));
		}
		return jCheckBox;
	}

	/**
	 * This method initializes jToolBar	
	 * 	
	 * @return javax.swing.JToolBar	
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getJPanel1());
			jToolBar.add(getJButton2());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setBounds(new java.awt.Rectangle(144,4,14,23));
			jLabel1.setText("\u81f3");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new java.awt.Rectangle(5,4,56,20));
			jLabel3.setText("\u62a5\u5173\u65e5\u671f:");
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.add(jLabel3, null);
			jPanel1.add(getCbbBeginDate(), null);
			jPanel1.add(jLabel1, null);
			jPanel1.add(getCbbEndDate(), null);
			jPanel1.add(getJCheckBox(), null);
			jPanel1.add(getJButton(), null);
			jPanel1.add(getJButton1(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jSplitPane	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setDividerLocation(550);
			jSplitPane.setDividerSize(6);
			jSplitPane.setLeftComponent(getJScrollPane());
			jSplitPane.setRightComponent(getJPanel2());
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(new BorderLayout());
			jPanel2.add(getJPanel(), java.awt.BorderLayout.NORTH);
			jPanel2.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel2;
	}
 } // @jve:decl-index=0:visual-constraint="10,10"
