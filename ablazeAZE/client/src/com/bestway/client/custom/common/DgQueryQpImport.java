package com.bestway.client.custom.common;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.enc.entity.LoadBGDFromQPXml;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.customs.common.action.BaseEncAction;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Rectangle;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JLabel;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JSplitPane;
import java.awt.GridBagConstraints;

public class DgQueryQpImport extends JDialogBase
{

	private JTextField tfCustomsDeclarationCode = null;

	private JCalendarComboBox cbbImportBeginDate = null;

	private JCalendarComboBox cbbImportEndDate = null;
	private JButton btnOk = null;
	private int impExpFlag = ImpExpFlag.IMPORT;
	private List lsResult = null;  //  @jve:decl-index=0:
	protected BaseEncAction baseEncAction = null;  //  @jve:decl-index=0:
	private JLabel jLabel3 = null;
	private JLabel jLabel4 = null;
	private JLabel jLabel5 = null;
	private JTableListModel tableModel;
	private JSplitPane jSplitPane = null;
	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JScrollPane jScrollPane = null;
	private JTable jTable = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;
	/**
	 * This method initializes 
	 * 
	 */
	public DgQueryQpImport() 
	{
		super();
		initialize();
		List list= new ArrayList();
		initTable(list);
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() 
	{
        this.setSize(new Dimension(560, 304));
        this.setContentPane(getJSplitPane());
        //this.setSize(new Dimension(588, 304));
        this.setTitle("查询直接导入的资料");
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */

	/**
	 * This method initializes tfCustomsDeclarationCode	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfCustomsDeclarationCode()
	{
		if(tfCustomsDeclarationCode == null)
		{
			tfCustomsDeclarationCode = new JTextField();
			tfCustomsDeclarationCode.setBounds(new Rectangle(99, 12, 107, 22));
		}
		return tfCustomsDeclarationCode;
	}

	/**
	 * This method initializes cbbImportBeginDate	
	 * 	
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox	
	 */
	private JCalendarComboBox getCbbImportBeginDate()
	{
		if(cbbImportBeginDate == null)
		{
			cbbImportBeginDate = new JCalendarComboBox();
			cbbImportBeginDate.setBounds(new Rectangle(312, 12, 88, 23));
		}
		return cbbImportBeginDate;
	}

	/**
	 * This method initializes cbbImportEndDate	
	 * 	
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox	
	 */
	private JCalendarComboBox getCbbImportEndDate()
	{
		if(cbbImportEndDate == null)
		{
			cbbImportEndDate = new JCalendarComboBox();
			cbbImportEndDate.setBounds(new Rectangle(416, 12, 87, 23));
		}
		return cbbImportEndDate;
	}

	/**
	 * This method initializes btnOk	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnOk()
	{
		if(btnOk == null)
		{
			btnOk = new JButton();
			btnOk.setText("查询");
			btnOk.setBounds(new Rectangle(241, 44, 64, 30));
			btnOk.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
//					List list=null;
//					list=baseEncAction.findAllLoadBGDFromQPXml(new Request(CommonVars.getCurrUser()), tfCustomsDeclarationCode.getText());
//					System.out.println("list.size()="+list.size());
//					LoadBGDFromQPXml lbf=(LoadBGDFromQPXml) list.get(0);
//					Integer impExpFlag=lbf.getImpExpFlag();
					DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
			        //String startDate=dateFormat.format(beginDate);
					String condition = "";
					List<Object> parameters = new ArrayList<Object>();
					if(!tfCustomsDeclarationCode.getText().trim().equals("")) 
					{
						condition += " and a.customsDeclarationCode like ? ";
						parameters.add("%"+ tfCustomsDeclarationCode.getText().trim()+ "%");
					}
					if(cbbImportBeginDate.getDate()!=null)
					{
						String startDate=dateFormat.format(cbbImportBeginDate.getDate());
						condition += " and (a.date>=?  or a.date is null) ";
						parameters.add(cbbImportBeginDate.getDate());
					}
					if(cbbImportEndDate.getDate()!=null)
					{
						String endDate=dateFormat.format(cbbImportEndDate.getDate());
						condition += " and (a.date<=?  or a.date is null) ";
						parameters.add(cbbImportEndDate.getDate());
					}
					if(impExpFlag == ImpExpFlag.IMPORT) 
					{
					 List lsResult = baseEncAction.findBGDFromQPXml(
								new Request(CommonVars.getCurrUser()),ImpExpFlag.IMPORT,
								condition, parameters);
						System.out.println("lsResult.size()="+lsResult.size());
						initTable(lsResult);
					}
					
				}
				
			});
		}
		return btnOk;
	}

	
//	else if(impExpFlag == ImpExpFlag.EXPORT) 
//	{
//		lsResult = baseEncAction.findBGDFromQPXml(
//				new Request(CommonVars.getCurrUser()),ImpExpFlag.EXPORT,
//				condition, parameters);
//		System.out.println("lsResult.size()="+lsResult.size());
//		tableModel.addRows(lsResult);
//	}
//	else if(impExpFlag == ImpExpFlag.SPECIAL) 
//	{
//		lsResult = baseEncAction.findBGDFromQPXml(
//				new Request(CommonVars.getCurrUser()),ImpExpFlag.SPECIAL,
//				condition, parameters);
//		System.out.println("lsResult.size()="+lsResult.size());
//		tableModel.addRows(lsResult);
//	}
	
	
	
	
//	public List getLsResult()
//	{
//		return lsResult;
//	}
//
//	public void setLsResult(List lsResult)
//	{
//		this.lsResult = lsResult;
//	}

	public BaseEncAction getBaseEncAction()
	{
		return baseEncAction;
	}

	public void setBaseEncAction(BaseEncAction baseEncAction)
	{
		this.baseEncAction = baseEncAction;
	}

	/**
	 * This method initializes jJToolBarBar	
	 * 	
	 * @return javax.swing.JToolBar	
	 */
//	private JToolBar getJJToolBarBar()
//	{
//		if(jJToolBarBar == null)
//		{
//			jLabel5 = new JLabel();
//			jLabel5.setText("到");
//			jLabel5.setBounds(new Rectangle(370, 14, 12, 18));
//			jLabel4 = new JLabel();
//			jLabel4.setText("报关单日期从");
//			jLabel4.setBounds(new Rectangle(206, 15, 72, 18));
//		}
//		return jJToolBarBar;
//	}

	/**
	 * 初始化表头
	 * @param list
	 */
	private void initTable(List list) 
	{
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter()
		        {
					public List InitColumns()
					{
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("报关单号", "customsDeclarationCode",150));
						list.add(addColumn("文件名称", "fileName", 200));
						list.add(addColumn("日期", "date", 100));
						list.add(addColumn("进出口标志", "impExpFlag", 80));
						return list;
					}
				});
	}
	/**
	 * This method initializes jLabel3	
	 * 	
	 * @return javax.swing.JLabel	
	 */
	private JLabel getJLabel3()
	{
		if(jLabel3 == null)
		{
			jLabel3 = new JLabel();
			jLabel3.setText("报关单号");
			jLabel3.setBounds(new Rectangle(49, 13, 48, 18));
		}
		return jLabel3;
	}

	public int getImpExpFlag()
	{
		return impExpFlag;
	}

	public void setImpExpFlag(int impExpFlag)
	{
		this.impExpFlag = impExpFlag;
	}

	/**
	 * This method initializes jSplitPane	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */
	private JSplitPane getJSplitPane()
	{
		if(jSplitPane == null)
		{
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setDividerSize(5);
			jSplitPane.setTopComponent(getJPanel());
			jSplitPane.setBottomComponent(getJPanel1());
			jSplitPane.setDividerLocation(80);
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel()
	{
		if(jPanel == null)
		{
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(401, 15, 13, 18));
			jLabel1.setText("到");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(224, 14, 89, 18));
			jLabel.setText("报关单申报日期");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setToolTipText("dfsadf asdf");
			jPanel.add(getJLabel3(), null);
			jPanel.add(getTfCustomsDeclarationCode(), null);
			//jPanel.add(jLabel4, null);
			jPanel.add(getCbbImportBeginDate(), null);
			//jPanel.add(jLabel5, null);
			jPanel.add(getCbbImportEndDate(), null);
			jPanel.add(getBtnOk(), null);
			jPanel.add(jLabel, null);
			jPanel.add(jLabel1, null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1()
	{
		if(jPanel1 == null)
		{
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.BOTH;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.weighty = 1.0;
			gridBagConstraints.gridx = 0;
			jPanel1 = new JPanel();
			jPanel1.setLayout(new GridBagLayout());
			jPanel1.add(getJScrollPane(), gridBagConstraints);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane()
	{
		if(jScrollPane == null)
		{
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTable	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getJTable()
	{
		if(jTable == null)
		{
			jTable = new JTable();
		}
		return jTable;
	}
	
}  //  @jve:decl-index=0:visual-constraint="10,10"
