package com.bestway.client.custom.common.report;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
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
import com.bestway.util.RegexUtil;

import javax.swing.JTextField;

/**
 * @author chl
 */
@SuppressWarnings("unchecked")
public class DgCaleReturnExport extends JDialogBase {
	private static final long serialVersionUID = 1L;

	/**
	 * 报关单的基础类接口
	 */
	protected BaseEncAction baseEncAction = null;  //  @jve:decl-index=0:

	private javax.swing.JPanel jContentPane = null;

	private Boolean isOk = false;

	private JFooterScrollPane srcPn = null;

	private JTable tblShow = null;
	
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

	private JLabel lbCommSerial = null;

	private JTextField tfCommSerial = null;
	
	private String emsHeadH2k = null;//电子账册号码
	
	private int projectType;
	
	public int getProjectType() {
		return projectType;
	}
	public void setProjectType(int projectType) {
		this.projectType = projectType;
	}
	/**
	 * 获取电子账册号码
	 * @return
	 */
	public String getEmsHeadH2k() {
		return emsHeadH2k;
	}
	/**
	 * 设置电子账册号码
	 * 
	 */
	public void setEmsHeadH2k(String emsHeadH2k) {
		this.emsHeadH2k = emsHeadH2k;
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
	public DgCaleReturnExport() {
		super();
		baseEncAction = (BaseEncAction) CommonVars.getApplicationContext()
				.getBean("encAction");
		initialize();
	}

	public void setVisible(boolean b) {
		
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
		this.setTitle("统计未复出数据");
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
		// 初始化数据
		initData();
		initTable(data.tbData);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			lbCommSerial = new JLabel();
			lbCommSerial.setBounds(new Rectangle(166, 470, 55, 25));
			lbCommSerial.setText("备案序号");
			lbSelectType = new JLabel();
			lbSelectType.setBounds(new Rectangle(700, 470, 50, 25));
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
			jContentPane.add(lbCommSerial, null);
			jContentPane.add(getTfCommSerial(), null);
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
	private JTable getTblShow() {
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
						commInfo.setComplex((Complex) tmp[11]);//商品信息
						commInfo.setEmsSerialNo(tmp[5].toString());//备案序号
						commInfo.setName(tmp[7].toString());//商品名称
						commInfo.setSpec(tmp[12].toString());//商品规格
						commInfo.setUnit((Unit) tmp[13]);//单位
						commInfo.setVersion(tmp[6] == null ? null : Integer
								.parseInt(tmp[6].toString()));//版本
						
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
			pnGroup.setBounds(new Rectangle(700, 470, 197, 25));
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
					reflash();
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
					reflash();
				}
			});
		}
		return cbbEnd;
	}
	
	public void reflash() {
		List oldList = (baseEncAction.queryReturnExportReport(new Request(CommonVars.getCurrUser()), 
				cbbBegin.getDate(), CommonUtils.getEndDate(cbbEnd.getDate()), ImpExpType.DIRECT_IMPORT, 1,emsHeadH2k,getProjectType()));
		List newList = new ArrayList(); 
		Object[] tmp = null;
		for (int i = 0; i < oldList.size(); i++) {
			tmp = (Object[]) oldList.get(i);
			if(tmp[3]!=null){
				if(CommonUtils.isDoubleToInteger(Double.valueOf(tmp[3].toString()))){
					tmp[3]=new BigDecimal(tmp[3].toString()).setScale(0, BigDecimal.ROUND_HALF_UP).toString();
				}
			}
			if(tmp[4]!=null){
				if(CommonUtils.isDoubleToInteger(Double.valueOf(tmp[4].toString()))){
					tmp[4]=new BigDecimal(tmp[4].toString()).setScale(0, BigDecimal.ROUND_HALF_UP).toString();
				}
			}
			if(tmp[8]!=null){
				if(CommonUtils.isDoubleToInteger(Double.valueOf(tmp[8].toString()))){
					tmp[8]=new BigDecimal(tmp[8].toString()).setScale(0, BigDecimal.ROUND_HALF_UP).toString();
				}
			}
			if(tmp[9]!=null){
				if(CommonUtils.isDoubleToInteger(Double.valueOf(tmp[9].toString()))){
					tmp[9]=new BigDecimal(tmp[9].toString()).setScale(0, BigDecimal.ROUND_HALF_UP).toString();
				}
			}
			if(tmp[10]!=null){
				if(CommonUtils.isDoubleToInteger(Double.valueOf(tmp[10].toString()))){
					tmp[10]=new BigDecimal(tmp[10].toString()).setScale(0, BigDecimal.ROUND_HALF_UP).toString();
				}
			}
			newList.add(tmp);
		}	
		data.tbData = newList;
		showTableModel.setList(data.tbData);
	}
	
	/**
	 * 初始化报关单商品信息
	 */
	private void initTable(List list) {
		showTableModel = new JTableListModel(tblShow, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("备案序号", "1", 50));
						list.add(addColumn("版本号", "2", 50));
						list.add(addColumn("名称规格", "3", 120));
						list.add(addColumn("数量", "4", 50));
						list.add(addColumn("金额", "5", 50));
						list.add(addColumn("备案序号", "6", 50));
						list.add(addColumn("版本号", "7", 50));
						list.add(addColumn("名称规格", "8", 120));
						list.add(addColumn("数量", "9", 50));// legalAmount
						list.add(addColumn("金额", "10", 50));
						list.add(addColumn("未复出数量", "11", 80));
						return list;
					}
				});
		tblShow.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		TableColumnModel cm = tblShow.getColumnModel();
		
		// 表头合并分组显示
		ColumnGroup importGroup = new ColumnGroup("出口返工复出");		
		importGroup.add(cm.getColumn(1));
		importGroup.add(cm.getColumn(2));
		importGroup.add(cm.getColumn(3));
		importGroup.add(cm.getColumn(4));
		importGroup.add(cm.getColumn(5));
		
		// 表头合并分组显示
		ColumnGroup exportGroup = new ColumnGroup("进口退厂返工");
		exportGroup.add(cm.getColumn(6));
		exportGroup.add(cm.getColumn(7));
		exportGroup.add(cm.getColumn(8));
		exportGroup.add(cm.getColumn(9));
		exportGroup.add(cm.getColumn(10));
		
		// 把分组添加到表头
		GroupableTableHeader header = (GroupableTableHeader) tblShow
				.getTableHeader();
		header.addColumnGroup(importGroup);
		header.addColumnGroup(exportGroup);
		
		// 页脚 合计数量
		showTableModel.clearFooterTypeInfo();
		showTableModel.addFooterTypeInfo(new TableFooterType(0,
				TableFooterType.CONSTANT, "合计"));
		showTableModel.addFooterTypeInfo(new TableFooterType(4,
				TableFooterType.SUM, ""));
		showTableModel.addFooterTypeInfo(new TableFooterType(9,
				TableFooterType.SUM, ""));
		showTableModel.addFooterTypeInfo(new TableFooterType(11,
				TableFooterType.SUM, ""));
		
		TableCellRenderer tcr = new DefaultTableCellRenderer(){
			private static final long serialVersionUID = 1L;
			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				super.getTableCellRendererComponent(table, value,
						isSelected, hasFocus, row, column);
				if(value == null) {
					this.setText("0");
				} else {
					this.setText(new BigDecimal(value.toString()).setScale(0, BigDecimal.ROUND_HALF_UP).toString());
				}
				return this;
			}

		};
		tblShow.getColumnModel().getColumn(4).setCellRenderer(tcr);
		tblShow.getColumnModel().getColumn(5).setCellRenderer(tcr);
		tblShow.getColumnModel().getColumn(9).setCellRenderer(tcr);
		tblShow.getColumnModel().getColumn(10).setCellRenderer(tcr);
		tblShow.getColumnModel().getColumn(11).setCellRenderer(tcr);
	}
	
	public void initData() {
		data = new DataModel();
		// 初始化窗体数据
			List oldList = (baseEncAction.queryReturnExportReport(new Request(CommonVars.getCurrUser()), 
					cbbBegin.getDate(), CommonUtils.getEndDate(cbbEnd.getDate()), ImpExpType.DIRECT_IMPORT, 1,emsHeadH2k,getProjectType()));
			List newList = new ArrayList(); 
			Object[] tmp = null;
			for (int i = 0; i < oldList.size(); i++) {
				tmp = (Object[]) oldList.get(i);
				if(tmp[3]!=null){
					if(CommonUtils.isDoubleToInteger(Double.valueOf(tmp[3].toString()))){
						tmp[3]=new BigDecimal(tmp[3].toString()).setScale(0, BigDecimal.ROUND_HALF_UP).toString();
					}
				}
				if(tmp[4]!=null){
					if(CommonUtils.isDoubleToInteger(Double.valueOf(tmp[4].toString()))){
						tmp[4]=new BigDecimal(tmp[4].toString()).setScale(0, BigDecimal.ROUND_HALF_UP).toString();
					}
				}
				if(tmp[8]!=null){
					if(CommonUtils.isDoubleToInteger(Double.valueOf(tmp[8].toString()))){
						tmp[8]=new BigDecimal(tmp[8].toString()).setScale(0, BigDecimal.ROUND_HALF_UP).toString();
					}
				}
				if(tmp[9]!=null){
					if(CommonUtils.isDoubleToInteger(Double.valueOf(tmp[9].toString()))){
						tmp[9]=new BigDecimal(tmp[9].toString()).setScale(0, BigDecimal.ROUND_HALF_UP).toString();
					}
				}
				if(tmp[10]!=null){
					if(CommonUtils.isDoubleToInteger(Double.valueOf(tmp[10].toString()))){
						tmp[10]=new BigDecimal(tmp[10].toString()).setScale(0, BigDecimal.ROUND_HALF_UP).toString();
					}
				}
				newList.add(tmp);
			}	
			data.tbData = newList;
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


	/**
	 * This method initializes tfCommSerial	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfCommSerial() {
		if (tfCommSerial == null) {
			tfCommSerial = new JTextField();
			tfCommSerial.setBounds(new Rectangle(214, 471, 100, 23));
			tfCommSerial.getDocument().addDocumentListener(new DocumentListener() {

				public void changedUpdate(DocumentEvent e) {
					showTableModel.filter(6, tfCommSerial.getText(), false);
				}

				public void insertUpdate(DocumentEvent e) {
					showTableModel.filter(6, tfCommSerial.getText(), false);
					System.out.println("insertUpdate");
				}

				public void removeUpdate(DocumentEvent e) {
					showTableModel.filter(6, tfCommSerial.getText(), false);
					System.out.println("removeUpdate");
				}
			});
		}
		return tfCommSerial;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
