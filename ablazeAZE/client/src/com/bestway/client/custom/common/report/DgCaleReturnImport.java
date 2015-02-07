package com.bestway.client.custom.common.report;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.enc.entity.TempCustomsDeclarationCommInfo;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.footer.JFooterScrollPane;
import com.bestway.client.util.footer.JFooterTable;
import com.bestway.client.util.footer.TableFooterType;
import com.bestway.client.util.groupableheader.ColumnGroup;
import com.bestway.client.util.groupableheader.GroupableHeaderTable;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpType;
import com.bestway.customs.common.action.BaseEncAction;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.DateValueListener;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author chl
 */
@SuppressWarnings("unchecked")
public class DgCaleReturnImport extends JDialogBase {
	private static final long serialVersionUID = 1L;

	/**
	 * 报关单的基础类接口
	 */
	protected BaseEncAction baseEncAction = null;  //  @jve:decl-index=0:

	private javax.swing.JPanel jContentPane = null;

	private Boolean isOk = false;

	private JFooterScrollPane srcPn = null;

	private JFooterTable tblShow = null;
	
	private JTableListModel showTableModel = null;

	private JButton btnOk = null;

	private JButton btnCancel = null;

	private JPanel pnGroup = null;

	private JRadioButton rbAll = null;

	private JRadioButton rbIsReturn = null;

	private JRadioButton rbNotReturn = null;
	
	private ButtonGroup buttonGroup = new ButtonGroup();

	private JLabel lbBegin = null;

	private JLabel lbEnd = null;
	
	private JCalendarComboBox cbbBegin = null;
	
	private JCalendarComboBox cbbEnd = null;

	private JLabel lbSelectType = null;
	
	private DataModel data = new DataModel();  //  @jve:decl-index=0:
	
	private String emsNo;//帐册编号
	
	private int projectType;//设置类型用于区分手册和账册
	
	/**
	 * 获得帐册编号
	 * @return
	 */
	public int getProjectType() {
		return projectType;
	}
	
	/**
	 * 设置帐册编号
	 * @param emsNo
	 */
	public void setProjectType(int projectType) {
		this.projectType = projectType;
	}
	
	/**
	 * 获得帐册编号
	 * @return
	 */
	public String getEmsNo() {
		return emsNo;
	}
	
	/**
	 * 设置帐册编号
	 * @param emsNo
	 */
	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}

	public Boolean getIsOk() {
		return isOk;
	}

	public void setIsOk(Boolean isOk) {
		this.isOk = isOk;
	}

	public List getReturnList() {
		return data.selects;
	}

	/**
	 * This is the default constructor
	 */
	public DgCaleReturnImport() {
		super();
		baseEncAction = (BaseEncAction) CommonVars.getApplicationContext()
				.getBean("encAction");
		initialize();
	}

	public void setVisible(boolean b) {
		// 初始化数据
		initData();
		initTable(data.tbData);
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
		this.setTitle("统计未复进数据");
		this.setSize(751, 537);
		this.setResizable(false);
		this.setContentPane(getJContentPane());
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				setIsOk(false);
			}
		});
		// 设置选择按钮组
		buttonGroup.add(rbAll);
		buttonGroup.add(rbIsReturn);
		buttonGroup.add(rbNotReturn);
		
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			lbSelectType = new JLabel();
			lbSelectType.setBounds(new Rectangle(160, 470, 50, 25));
			//lbSelectType.setText("选择类型");
			lbEnd = new JLabel();
			lbEnd.setBounds(new Rectangle(560, 470, 50, 25));
			lbEnd.setText("截止日期");
			lbBegin = new JLabel();
			lbBegin.setBounds(new Rectangle(420, 470, 50, 25));
			lbBegin.setText("起始日期");
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getSrcPn(), null);
			jContentPane.add(getBtnOk(), null);
			jContentPane.add(getBtnCancel(), null);
			jContentPane.add(getPnGroup(), null);
			jContentPane.add(lbBegin, null);
			jContentPane.add(getCbbBegin(), null);
			jContentPane.add(lbEnd, null);
			jContentPane.add(getCbbEnd(), null);
			jContentPane.add(lbSelectType, null);
		}

		return jContentPane;
	}

	public BaseEncAction getBaseEncAction() {
		return baseEncAction;
	}

	public void setBaseEncAction(BaseEncAction baseEncAction) {
		this.baseEncAction = baseEncAction;
	}

	/**
	 * This method initializes srcPn	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JFooterScrollPane getSrcPn() {
		if (srcPn == null) {
			srcPn = new JFooterScrollPane();
			srcPn.setBounds(new Rectangle(0, 0, 745, 464));
			srcPn.setViewportView(getTblShow());
		}
		return srcPn;
	}

	/**
	 * This method initializes tblShow	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JFooterTable getTblShow() {
		if (tblShow == null) {
			tblShow = new GroupableHeaderTable();
		}
		return tblShow;
	}

	/**
	 * This method initializes btnOk	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton();
			btnOk.setBounds(new Rectangle(8, 470, 60, 25));
			btnOk.setText("确定");
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List list = showTableModel.getCurrentRows();
					data.selects = new ArrayList();
					TempCustomsDeclarationCommInfo commInfo = null;
					Object[] tmp = null;
					for (int i = 0; i < list.size(); i++) {
						tmp = (Object[]) list.get(i);
						commInfo = new TempCustomsDeclarationCommInfo();
						commInfo.setComplex((Complex) tmp[10]);//商品信息
						commInfo.setEmsSerialNo(tmp[4].toString());//备案序号
						commInfo.setName(tmp[6].toString());//商品名称
						commInfo.setSpec(tmp[11].toString());//商品规格
						commInfo.setUnit((Unit) tmp[12]);//单位
						
						data.selects.add(commInfo);
					}
					dispose();
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
			btnCancel.setBounds(new Rectangle(75, 470, 60, 25));
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * This method initializes pnGroup	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPnGroup() {
		if (pnGroup == null) {
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 2;
			gridBagConstraints2.gridy = 0;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 1;
			gridBagConstraints1.gridy = 0;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = 0;
			pnGroup = new JPanel();
			pnGroup.setLayout(new GridBagLayout());
			pnGroup.setBounds(new Rectangle(210, 470, 197, 25));
			pnGroup.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
			pnGroup.add(getRbAll(), gridBagConstraints);
			pnGroup.add(getRbIsReturn(), gridBagConstraints1);
			pnGroup.add(getRbNotReturn(), gridBagConstraints2);
			pnGroup.setVisible(false);
		}
		return pnGroup;
	}

	/**
	 * This method initializes rbAll	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRbAll() {
		if (rbAll == null) {
			rbAll = new JRadioButton();
			rbAll.setText("全部");
			rbAll.setSelected(true);
		}
		return rbAll;
	}

	/**
	 * This method initializes rbIsReturn	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRbIsReturn() {
		if (rbIsReturn == null) {
			rbIsReturn = new JRadioButton();
			rbIsReturn.setText("退换已回");
		}
		return rbIsReturn;
	}

	/**
	 * This method initializes rbNotReturn	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRbNotReturn() {
		if (rbNotReturn == null) {
			rbNotReturn = new JRadioButton();
			rbNotReturn.setText("退回未回");
		}
		return rbNotReturn;
	}

	private JCalendarComboBox getCbbBegin() {
		if(cbbBegin == null) {
			cbbBegin = new JCalendarComboBox();
			cbbBegin.setBounds(new Rectangle(470, 471, 80, 21));
			// 默认开始日期为当月的第一天0时0分0秒
			cbbBegin.setDate(CommonUtils.firstDayOfMonth(cbbBegin.getDate()));
			cbbBegin.addDateValueListener(new DateValueListener(){
				public void valueChanged(Date newDate) {
					data.tbData = (baseEncAction.queryReturnImportReport(new Request(CommonVars.getCurrUser()), 
							cbbBegin.getDate(), CommonUtils.getEndDate(cbbEnd.getDate()), ImpExpType.DIRECT_IMPORT, 1,getEmsNo(),getProjectType()));
					showTableModel.setList(data.tbData);
				}
			});
		}
		return cbbBegin;
	}

	private JCalendarComboBox getCbbEnd() {
		if(cbbEnd == null) {
			cbbEnd = new JCalendarComboBox();
			cbbEnd.setBounds(new Rectangle(610, 471, 80, 21));
			// 默认结束日期为当天时间的最后一秒
			cbbEnd.setDate(CommonUtils.getEndDate(cbbEnd.getDate()));
			cbbEnd.addDateValueListener(new DateValueListener() {
				public void valueChanged(Date newDate) {
					data.tbData = (baseEncAction.queryReturnImportReport(new Request(CommonVars.getCurrUser()), 
							cbbBegin.getDate(), CommonUtils.getEndDate(cbbEnd.getDate()), ImpExpType.DIRECT_IMPORT, 1,getEmsNo(),getProjectType()));
					showTableModel.setList(data.tbData);
				}
			});
		}
		return cbbEnd;
	}
	
	/**
	 * 初始化报关单商品信息
	 */
	private void initTable(List list) {
		showTableModel = new JTableListModel(tblShow, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("版本号", "1", 50,
								Integer.class));
						list.add(addColumn("名称规格", "2", 120,
								Integer.class));
						list.add(addColumn("数量", "3", 50));
						list.add(addColumn("金额", "4", 50));
						list.add(addColumn("备案序号", "5", 50));
						list.add(addColumn("版本号", "6", 50));
						list.add(addColumn("名称规格", "7", 120));
						list.add(addColumn("数量", "8", 50));// legalAmount
						// 修改前用的
						list.add(addColumn("金额", "9", 50));
						list.add(addColumn("未返回数量", "0", 80));
						return list;
					}
				});
		tblShow.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		TableColumnModel cm = tblShow.getColumnModel();
		
		// 表头合并分组显示
		ColumnGroup importGroup = new ColumnGroup("进口料件退换");		
		importGroup.add(cm.getColumn(1));
		importGroup.add(cm.getColumn(2));
		importGroup.add(cm.getColumn(3));
		importGroup.add(cm.getColumn(4));
		
		// 表头合并分组显示
		ColumnGroup exportGroup = new ColumnGroup("出口料件退换");
		exportGroup.add(cm.getColumn(5));
		exportGroup.add(cm.getColumn(6));
		exportGroup.add(cm.getColumn(7));
		exportGroup.add(cm.getColumn(8));
		exportGroup.add(cm.getColumn(9));
		
		// 把分组添加到表头
		GroupableTableHeader header = (GroupableTableHeader) tblShow
				.getTableHeader();
		header.addColumnGroup(importGroup);
		header.addColumnGroup(exportGroup);
		
		tblShow.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		// 页脚 合计数量
		showTableModel.clearFooterTypeInfo();
		showTableModel.addFooterTypeInfo(new TableFooterType(0,
				TableFooterType.CONSTANT, "合计"));
		showTableModel.addFooterTypeInfo(new TableFooterType(3,
				TableFooterType.SUM, ""));
		showTableModel.addFooterTypeInfo(new TableFooterType(8,
				TableFooterType.SUM, ""));
		showTableModel.addFooterTypeInfo(new TableFooterType(10,
				TableFooterType.SUM, ""));
		TableCellRenderer tcr = new DefaultTableCellRenderer(){
			private static final long serialVersionUID = 1L;
			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				super.getTableCellRendererComponent(table, value,
						isSelected, hasFocus, row, column);
				if(value == null || (Double)value == 0) {
					this.setText("0");
				} else {
					this.setText(CommonUtils.formatDoubleByDigit((Double) value, 0));
				}
				
				return this;
			}
			
		};
		tblShow.getColumnModel().getColumn(3).setCellRenderer(tcr);
		tblShow.getColumnModel().getColumn(4).setCellRenderer(tcr);
		tblShow.getColumnModel().getColumn(8).setCellRenderer(tcr);
		tblShow.getColumnModel().getColumn(9).setCellRenderer(tcr);
		tblShow.getColumnModel().getColumn(10).setCellRenderer(tcr);
	}
	
	public void initData() {
		data = new DataModel();
		// 初始化窗体数据
		data.tbData = (baseEncAction.queryReturnImportReport(new Request(CommonVars.getCurrUser()), 
				cbbBegin.getDate(), CommonUtils.getEndDate(cbbEnd.getDate()), ImpExpType.DIRECT_IMPORT, 1,getEmsNo(),getProjectType()));
		
	}
	
	public List getReturnValue() {
		return data.selects;
	}
	
	
	class DataModel {
		// 表格数据
		List tbData = null;
		// 手册类型
		int projectType;
		// 报关单表头
		BaseCustomsDeclaration baseCustomsDeclaration = null; 
		// 进出口类型
		Integer ImpExpType = null;
		
		
		// 窗口返回数据
		List selects = null;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
