package com.bestway.client.selfcheck;

import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.Rectangle;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.SwingWorker;

import java.awt.Point;
import javax.swing.JCheckBox;
import java.awt.ComponentOrientation;
import javax.swing.JButton;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.action.CasCheckAction;
import com.bestway.bcus.cas.bill.entity.BillUtil;
import com.bestway.bcus.cas.entity.TempObject;
import com.bestway.bcus.cas.invoice.entity.CasCondition;
import com.bestway.bcus.cas.invoice.entity.CasConvert;
import com.bestway.bcus.client.cas.CasQuery;
import com.bestway.bcus.client.cas.DgFactoryQuery;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomReportDataSourceNew;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.client.selfcheck.DgMaterialThatDayBalance.CheckableItem;
import com.bestway.client.selfcheck.DgMaterialThatDayBalance.Find;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.groupableheader.ColumnGroup;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.client.util.mutispan.AttributiveCellTableModel;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import com.bestway.client.util.mutispan.MultiSpanCellTable;
import com.bestway.common.CommonUtils;
import com.bestway.common.Condition;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.Materiel;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.io.InputStream;

import javax.swing.JSplitPane;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

public class DgSemiConvert extends JDialogBase {

	private JCalendarComboBox cbStartDate = null;
	private JCalendarComboBox cbEndDate = null;
	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JScrollPane jScrollPane = null;
	private JLabel lbPtPartBegin = null;
	private JLabel lbPtNoBegin = null;
	private JLabel lbHsNoBegin = null;
	private JLabel lbPtPartEnd = null;
	private JLabel lbPtNoEnd = null;
	private JLabel lbHsNoEnd = null;
	private JLabel jLabel11 = null;
	private JTextField tfPtPartBegin = null;
	private JTextField tfPtNoBegin = null;
	private JTextField tfPtNoEnd = null;
	private JTextField tfPtPartEnd = null;
	private JTable jTable = null;
	private JCheckBox cbFactoryName = null;
	private JCheckBox cbCustomName = null;
	private JButton btQuerry = null;
	private JButton btprint = null;
	private JButton btClose = null;
	private JTableListModel tableModel = null;
	private ArrayList dataSource = null;  //  @jve:decl-index=0:
	private JSplitPane jSplitPane = null;
	private JButton btPtPartBegin = null;
	private JButton btnPtNoBegin = null;
	private JButton btnPtPartEnd = null;
	private JButton btnPtNoEnd = null;
	/**
	 * 物料类型
	 */
	private String materielType = null;  //  @jve:decl-index=0:
	
	/**
	 * 查询action
	 */
	private CasAction casAction = null;
	private MaterialManageAction materialManageAction = null;
	private JLabel lbPtSpec = null;
	private JTextField tfPtName = null;
	private JButton btnPtName = null;
	private JTextField tfComplexCode = null;
	private JLabel lbComplexCode = null;
	private JButton btnComplexCode = null;
	private JLabel lbHsSpec = null;
	private JTextField tfHsName = null;
	private JButton btnHsName = null;
	private JLabel jLabel111 = null;
	private JLabel jLabel1111 = null;
	private JTextField tfHsNoBegin = null;
	private JButton btnHsNoBegin = null;
	private JTextField tfHsNoEnd = null;
	private JButton btnHsNoEnd = null;
	private JLabel lbProductNo = null;
	private JTextField tfProductNo = null;
	private JButton btnProductNo = null;
	private CasCheckAction casCheckAction=null;
	private JButton btprint2 = null;
	private JLabel lbStartDate = null;
	private JLabel jLabel112 = null;
	private JLabel lbEndDate = null;
	/**
	 * This method initializes
	 * 
	 */
	public DgSemiConvert() {
		
		super();
		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
		"casAction");
		casCheckAction = (CasCheckAction) CommonVars.getApplicationContext().getBean(
		"casCheckAction");
		materialManageAction = (MaterialManageAction) CommonVars
		.getApplicationContext().getBean("materialManageAction");
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(768, 541));
		this.setContentPane(getJSplitPane());
		this.setTitle("在产品折料情况表");
		int year = com.bestway.bcus.client.cas.parameter.CasCommonVars
		.getYearInt();
		Calendar beginClarendar = Calendar.getInstance();
		beginClarendar.set(Calendar.YEAR, year);
		beginClarendar.set(Calendar.MONTH, 0);
		beginClarendar.set(Calendar.DAY_OF_MONTH, 1);
		beginClarendar.set(Calendar.HOUR_OF_DAY, 0);
		beginClarendar.set(Calendar.MINUTE, 0);
		beginClarendar.set(Calendar.SECOND, 0);
		this.cbStartDate.setDate(beginClarendar.getTime());
		this.cbStartDate.setCalendar(beginClarendar);
			}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(getJPanel1(), null);
		}
		return jPanel;
	}

	private JCalendarComboBox getCbStartDate() {
		if (cbStartDate == null) {
			cbStartDate = new JCalendarComboBox();
			cbStartDate.setLocation(new Point(164, 156));
			cbStartDate.setSize(new Dimension(120, 20));
		}
		return cbStartDate;
	}
	
	private JCalendarComboBox getCbEndDate() {
		if (cbEndDate == null) {
			cbEndDate = new JCalendarComboBox();
			cbEndDate.setLocation(new Point(410, 156));
			cbEndDate.setSize(new Dimension(120, 20));
			cbEndDate.add(getCbFactoryName(), BorderLayout.NORTH);
		}
		return cbEndDate;
	}
	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			lbEndDate = new JLabel();
			lbEndDate.setBounds(new Rectangle(346, 156, 53, 18));
			lbEndDate.setText("单据日期");
			jLabel112 = new JLabel();
			jLabel112.setBounds(new Rectangle(307, 156, 12, 18));
			jLabel112.setText("\u81f3");
			jLabel112.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			lbStartDate = new JLabel();
			lbStartDate.setBounds(new Rectangle(103, 157, 54, 18));
			lbStartDate.setText("单据日期");
			lbProductNo = new JLabel();
			lbProductNo.setBounds(new Rectangle(14, 54, 81, 18));
			lbProductNo.setText("制单号");
			jLabel1111 = new JLabel();
			jLabel1111.setBounds(new Rectangle(304, 104, 14, 23));
			jLabel1111.setText("\u81f3");
			jLabel1111.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			jLabel111 = new JLabel();
			jLabel111.setBounds(new Rectangle(304, 24, 15, 31));
			jLabel111.setText("\u81f3");
			jLabel111.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			lbHsSpec = new JLabel();
			lbHsSpec.setBounds(new Rectangle(338, 54, 87, 18));
			lbHsSpec.setText("在产品报关名称");
			lbComplexCode = new JLabel();
			lbComplexCode.setBounds(new Rectangle(14, 81, 86, 18));
			lbComplexCode.setText("在产品报关编号");
			lbPtSpec = new JLabel();
			lbPtSpec.setBounds(new Rectangle(338, 78, 87, 19));
			lbPtSpec.setText("在产品工厂名称");
			jLabel11 = new JLabel();
			jLabel11.setText("至");
			jLabel11.setSize(new Dimension(14, 20));
			jLabel11
					.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			jLabel11.setLocation(new Point(305, 129));
			lbHsNoEnd = new JLabel();
			lbHsNoEnd.setText("料件报关编号");
			lbHsNoEnd.setSize(new Dimension(80, 20));
			lbHsNoEnd
					.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			lbHsNoEnd.setLocation(new Point(331, 129));
			lbPtNoEnd = new JLabel();
			lbPtNoEnd.setText("料件工厂料号");
			lbPtNoEnd.setSize(new Dimension(80, 20));
			lbPtNoEnd.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			lbPtNoEnd.setLocation(new Point(331, 105));
			lbPtPartEnd = new JLabel();
			lbPtPartEnd.setText("在产品工厂料号");
			lbPtPartEnd.setSize(new Dimension(97, 20));
			lbPtPartEnd.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			lbPtPartEnd.setLocation(new Point(325, 30));
			lbHsNoBegin = new JLabel();
			lbHsNoBegin.setText("料件报关编号");
			lbHsNoBegin.setSize(new Dimension(80, 20));
			lbHsNoBegin.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			lbHsNoBegin.setLocation(new Point(6, 132));
			lbPtNoBegin = new JLabel();
			lbPtNoBegin.setText("料件工厂料号");
			lbPtNoBegin.setSize(new Dimension(80, 20));
			lbPtNoBegin.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			lbPtNoBegin.setLocation(new Point(7, 106));
			lbPtPartBegin = new JLabel();
			lbPtPartBegin.setText("在产品工厂料号");
			lbPtPartBegin.setSize(new Dimension(91, 20));
			lbPtPartBegin.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			lbPtPartBegin.setLocation(new Point(9, 31));
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setBounds(new Rectangle(10, 10, 736, 187));
			jPanel1.setBorder(BorderFactory.createTitledBorder(null,
					"\u6298\u6599\u9009\u9879",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.PLAIN, 14), Color.blue));
			jPanel1.setFont(new Font("Dialog", Font.PLAIN, 12));
			jPanel1.add(lbPtPartBegin, null);
			jPanel1.add(lbPtNoBegin, null);
			jPanel1.add(lbHsNoBegin, null);
			jPanel1.add(lbPtPartEnd, null);
			jPanel1.add(lbPtNoEnd, null);
			jPanel1.add(lbHsNoEnd, null);
			jPanel1.add(jLabel11, null);
			jPanel1.add(getTfPtPartBegin(), null);
			jPanel1.add(getTfPtNoBegin(), null);
			jPanel1.add(getTfPtNoEnd(), null);
			jPanel1.add(getTfPtPartEnd(), null);
			jPanel1.add(getCbFactoryName(), null);
			jPanel1.add(getCbCustomName(), null);
			jPanel1.add(getBtQuerry(), null);
			jPanel1.add(getBtprint(), null);
			jPanel1.add(getBtClose(), null);
			jPanel1.add(getBtPtPartBegin(), null);
			jPanel1.add(getBtnPtNoBegin(), null);
			jPanel1.add(getBtnPtPartEnd(), null);
			jPanel1.add(getBtnPtNoEnd(), null);
			jPanel1.add(lbPtSpec, null);
			jPanel1.add(getTfPtName(), null);
			jPanel1.add(getBtnPtName(), null);
			jPanel1.add(getTfComplexCode(), null);
			jPanel1.add(lbComplexCode, null);
			jPanel1.add(getBtnComplexCode(), null);
			jPanel1.add(lbHsSpec, null);
			jPanel1.add(getTfHsName(), null);
			jPanel1.add(getBtnHsName(), null);
			jPanel1.add(jLabel111, null);
			jPanel1.add(jLabel1111, null);
			jPanel1.add(getTfHsNoBegin(), null);
			jPanel1.add(getBtnHsNoBegin(), null);
			jPanel1.add(getTfHsNoEnd(), null);
			jPanel1.add(getBtnHsNoEnd(), null);
			jPanel1.add(lbProductNo, null);
			jPanel1.add(getTfProductNo(), null);
			jPanel1.add(getBtnProductNo(), null);
			jPanel1.add(getBtprint2(), null);
			jPanel1.add(getCbStartDate(), null);
			jPanel1.add(getCbEndDate(), null);
			jPanel1.add(lbStartDate, null);
			jPanel1.add(jLabel112, null);
			jPanel1.add(lbEndDate, null);
		}
		return jPanel1;
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
	 * This method initializes tfPtPartBegin
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfPtPartBegin() {
		if (tfPtPartBegin == null) {
			tfPtPartBegin = new JTextField();
			tfPtPartBegin.setLocation(new Point(111, 28));
			tfPtPartBegin.setSize(new Dimension(149, 22));
			tfPtPartBegin.getDocument().addDocumentListener(new DocumentListener() {
				private void setState() {
					String editAfterQueryValue = tfPtPartBegin.getText().trim();
					if (!"".equalsIgnoreCase(editAfterQueryValue)) {
						tfPtPartEnd.setEditable(true);
						btnPtPartEnd.setEnabled(true);
					} else {
						tfPtPartEnd.setEditable(false);
						btnPtPartEnd.setEnabled(false);
					}
				}

				public void insertUpdate(DocumentEvent e) {
					setState();
				}

				public void removeUpdate(DocumentEvent e) {
					setState();
				}

				public void changedUpdate(DocumentEvent e) {
					setState();
				}

			});
		}
		return tfPtPartBegin;
	}

	/**
	 * This method initializes tfPtNoBegin
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfPtNoBegin() {
		if (tfPtNoBegin == null) {
			tfPtNoBegin = new JTextField();
			tfPtNoBegin.setLocation(new Point(110, 105));
			tfPtNoBegin.setSize(new Dimension(149, 22));
			tfPtNoBegin.getDocument().addDocumentListener(new DocumentListener() {
				private void setState() {
					String editAfterQueryValue = tfPtNoBegin.getText().trim();
					if (!"".equalsIgnoreCase(editAfterQueryValue)) {
						tfPtNoEnd.setEditable(true);
						btnPtNoEnd.setEnabled(true);
					} else {
						tfPtNoEnd.setEditable(false);
						btnPtNoEnd.setEnabled(false);
					}
				}

				public void insertUpdate(DocumentEvent e) {
					setState();
				}

				public void removeUpdate(DocumentEvent e) {
					setState();
				}

				public void changedUpdate(DocumentEvent e) {
					setState();
				}

			});
		}
		return tfPtNoBegin;
	}

	/**
	 * This method initializes tfPtNoEnd
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfPtNoEnd() {
		if (tfPtNoEnd == null) {
			tfPtNoEnd = new JTextField();
			tfPtNoEnd.setLocation(new Point(432, 104));
			tfPtNoEnd.setSize(new Dimension(151, 22));
			tfPtNoEnd.setEditable(false);
		}
		return tfPtNoEnd;
	}

	/**
	 * This method initializes tfPtPartEnd
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfPtPartEnd() {
		if (tfPtPartEnd == null) {
			tfPtPartEnd = new JTextField();
			tfPtPartEnd.setLocation(new Point(433, 28));
			tfPtPartEnd.setSize(new Dimension(150, 22));
			tfPtPartEnd.setEditable(false);
		}
		return tfPtPartEnd;
	}


	/**
	 * This method initializes cbFactoryName
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbFactoryName() {
		if (cbFactoryName == null) {
			cbFactoryName = new JCheckBox();
			cbFactoryName.setText("按工厂名称汇总");
			cbFactoryName.setLocation(new Point(497, 150));
			cbFactoryName.setSize(new Dimension(143, 21));
			cbFactoryName.setVisible(false);
		}
		return cbFactoryName;
	}

	/**
	 * This method initializes cbCustomName
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbCustomName() {
		if (cbCustomName == null) {
			cbCustomName = new JCheckBox();
			cbCustomName.setText("按报关名称汇总");
			cbCustomName.setLocation(new Point(499, 161));
			cbCustomName.setSize(new Dimension(157, 21));
			cbCustomName.setVisible(false);
		}
		return cbCustomName;
	}

	/**
	 * This method initializes btQuerry
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtQuerry() {
		if (btQuerry == null) {
			btQuerry = new JButton();
			btQuerry.setText("查询");
			btQuerry.setSize(new Dimension(98, 23));
			btQuerry.setLocation(new Point(625, 29));
			btQuerry.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					queryData();
				}
			}); 
		}
		return btQuerry;
	}

	/**
	 * 查询
	 */
	public void queryData(){
		//查询
		//组织查询条件
		List<Condition> conditions = new ArrayList<Condition>();
		CasConvert casConvert = new CasConvert();
		//生效的单据
		conditions.add(new Condition("and", null,"billMaster.isValid", "=",new Boolean(true), null));
		// 生效日期
		if (cbStartDate.getDate() != null) { // 开始日期
			Date date = cbStartDate.getDate();
			casConvert.setStartDate(date);
		}
		if (cbEndDate.getDate() != null) { // 结束日期
			Date date = cbEndDate.getDate();
			casConvert.setEndDate(date);
		}
		// 工厂料号不等于空,结束料号为空时
		if (!tfPtPartBegin.getText().trim().equals("")
				&& tfPtPartEnd.getText().trim().equals("")) {
			conditions.add(new Condition("and", null, "ptPart",
					"=", tfPtPartBegin.getText(), null));
			casConvert.setStartPtPart(tfPtPartBegin.getText().trim());
		} else // 工厂料号不等于空,结束料号不为空时
		if (!tfPtPartBegin.getText().trim().equals("")
				&& !tfPtPartEnd.getText().trim().equals("")) {
			conditions.add(new Condition("and", "(", "ptPart",
					">=", tfPtPartBegin.getText(), null));
			conditions.add(new Condition("and", null, "ptPart",
					"<=", tfPtPartEnd.getText(), ")"));
			casConvert.setStartPtPart(tfPtPartBegin.getText().trim());
			casConvert.setEndPtPart(tfPtPartEnd.getText().trim());
		}
		 //制单号
		if (!tfProductNo.getText().trim().equals("")) {
			conditions.add(new Condition("and", null, "productNo",
					"=", tfProductNo.getText(), null));
			casConvert.setProductNo(tfProductNo.getText().trim());
		}
		//成品工厂名称
		if (!tfPtName.getText().trim().equals("")) {
			conditions.add(new Condition("and", null, "ptName",
					"=", tfPtName.getText(), null));
			casConvert.setPtName(tfPtName.getText().trim());
		}
		
		 //成品报关名称
		if (!tfHsName.getText().trim().equals("")) {
			conditions.add(new Condition("and", null, "hsName",
					"=", tfHsName.getText(), null));
			casConvert.setHsName(tfHsName.getText().trim());
		}
		
		//成品报关商品编码
		if (!tfComplexCode.getText().trim().equals("")) {
			conditions.add(new Condition("and", null, "complex.code",
					"=", tfComplexCode.getText(), null));
			casConvert.setComplexCode(tfComplexCode.getText().trim());
		}
		// 料件工厂料号不等于空,结束料号为空时
		if (!tfPtNoBegin.getText().trim().equals("")
				&& tfPtNoEnd.getText().trim().equals("")) {
			casConvert.setStartPtNo(tfPtNoBegin.getText().trim());
		} else // 料件工厂料号不等于空,结束料号不为空时
		if (!tfPtNoBegin.getText().trim().equals("")
				&& !tfPtNoEnd.getText().trim().equals("")) {
			casConvert.setStartPtNo(tfPtNoBegin.getText().trim());
			casConvert.setEndPtNo(tfPtNoEnd.getText().trim());
		}
		
		// 料件报关编号不等于空,结束料号为空时
		if (!tfHsNoBegin.getText().trim().equals("")
				&& tfHsNoEnd.getText().trim().equals("")) {
			casConvert.setStartHsPart(tfHsNoBegin.getText().trim());
		} else // 料件报关编号不等于空,结束料号不为空时
		if (!tfHsNoBegin.getText().trim().equals("")
				&& !tfHsNoEnd.getText().trim().equals("")) {
			casConvert.setStartHsPart(tfHsNoBegin.getText().trim());
			casConvert.setEndHsPart(tfHsNoEnd.getText().trim());
		}
		//单据类型
		String billDetail = BillUtil.getBillDetailTableNameByMaterielType(getMaterielType());
		
		//传入料件工厂料号..之前写的方法..已作废..有空再修改
		String PtNoBegin=null;
		if(!tfPtNoBegin.getText().trim().equals("")){
		PtNoBegin =tfPtNoBegin.getText();
		}
		//执行查询线程
		System.out.println("准备进入LOGIC");
		new Find(conditions,"",billDetail,cbFactoryName.isSelected(),cbCustomName.isSelected(),PtNoBegin,casConvert).execute();
	
	}
	class Find extends SwingWorker{
		//查询条件
		List<Condition> conditions = null;
        //排序
		String orderBy = null;
        //查询目标表
		String billDetail = null;
		//报关名称汇总
		Boolean ishsTaotal = false;
		//显示负数结存
		Boolean isShowLess = false;
		//工厂料件编码
		String PtNoBegin=null;
		
		CasConvert casConvert =null;
		
		public Find(List<Condition> conditions, String orderBy,
				String billDetail,Boolean ishsTaotal,Boolean isShowLess,String PtNoBegin,CasConvert casConvert) {
			this.conditions = conditions;
			this.orderBy = orderBy;
			this.billDetail = billDetail;
			this.ishsTaotal = ishsTaotal;
			this.isShowLess = isShowLess;
			this.PtNoBegin=PtNoBegin;
			this.casConvert = casConvert;
		}

		@Override
		protected Object doInBackground() throws Exception {//后台计算
			//查询返回结果
			List list = null;
			//查询
			CommonProgress.showProgressDialog(DgSemiConvert.this);
			CommonProgress.setMessage("系统正在执行查询，请稍后...");
			
//			if(tfPtNoBegin.getText().trim().equals("") && tfHsNoBegin.getText().trim().equals("")){
//			list = casCheckAction.getCurrentBillDetailBom(new Request(CommonVars.getCurrUser()), conditions,
//					getMaterielType(),cbFactoryName.isSelected(),cbCustomName.isSelected(),PtNoBegin);
//			}
//			else{
			list = casCheckAction.getSemiCurrentBillDetailBom(new Request(CommonVars.getCurrUser()),casConvert,billDetail);
//			}
			return list;
		}

		@Override
		protected void done() {//更新视图
			List list=null;
			try {
				list = (List)this.get();
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(list==null){
				list=new ArrayList();
			}
			CommonProgress.closeProgressDialog();
			initTableModel(list);
			combineTable();
		}
	}
	/**
	 * This method initializes btprint
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtprint() {
		if (btprint == null) {
			btprint = new JButton();
			btprint.setText("打印工厂级");
			btprint.setSize(new Dimension(99, 23));
			btprint.setLocation(new Point(626, 67));
			btprint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {
						List list = tableModel.getList();
						CustomReportDataSourceNew ds = new CustomReportDataSourceNew(
								list);
						InputStream masterReportStream = DgMaterialThatDayBalance.class
								.getResourceAsStream("report/CurrentBillDetailConvert.jasper");
						Map<String, Object> parameters = new HashMap<String, Object>();
						parameters.put("title", getTitle());
						parameters.put("type", getMaterielType());
						JasperPrint jasperPrint = JasperFillManager
								.fillReport(masterReportStream,
										parameters, ds);
						DgReportViewer viewer = new DgReportViewer(
								jasperPrint);
						viewer.setVisible(true);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
	});
		}
		return btprint;
	}

	/**
	 * This method initializes btClose
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtClose() {
		if (btClose == null) {
			btClose = new JButton();
			btClose.setText("关闭");
			btClose.setSize(new Dimension(97, 23));
			btClose.setLocation(new Point(627, 143));
			btClose.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgSemiConvert.this.dispose();

				}
			});
		}
		return btClose;
	}

	/**
	 * This method initializes dataSource
	 * 
	 * @return java.util.ArrayList
	 */
	private ArrayList getDataSource() {
		if (dataSource == null) {
			dataSource = new ArrayList();
		}
		return dataSource;
	}

	/**
	 * This method initializes tableModel
	 * 
	 * @return com.bestway.client.util.mutispan.AttributiveCellTableModel
	 */
	private void initTableModel(List list) {
			tableModel = new AttributiveCellTableModel((MultiSpanCellTable)jTable, list,
					new JTableListModelAdapter() {
						public List<JTableListColumn> InitColumns() {
							List<JTableListColumn> list = new Vector<JTableListColumn>();
							list.add(addColumn("单据日期", "validDate", 100));
							list.add(addColumn("单据类型", "billTypeName", 100));
							list.add(addColumn("单据号码", "billNo", 100));
							list.add(addColumn("工厂商品编码", "ptPart", 100));
							list.add(addColumn("工厂商品名称", "ptName", 100));
							list.add(addColumn("工厂商品规格", "ptSpec", 100));
							list.add(addColumn("工厂商品单位", "ptUnitName", 100));
							list.add(addColumn("工厂商品数量", "ptAmount", 100));
							list.add(addColumn("报关商品编码", "complexCode", 100));
							list.add(addColumn("报关商品名称", "hsName", 100));
							list.add(addColumn("报关商品规格", "hsSpec", 100));
							list.add(addColumn("报关商品单位", "hsUnitName", 100));
							list.add(addColumn("报关商品数量", "hsAmount", 100));
							list.add(addColumn("工厂料件编码", "ptNo", 100));
							list.add(addColumn("工厂料件名称", "factoryName", 100));
							list.add(addColumn("工厂料件规格", "factorySpec", 100));
							list.add(addColumn("工厂料件单位", "calUnitName", 100));
							list.add(addColumn("报关料件编码", "mcomplexCode", 100));
							list.add(addColumn("报关料件名称", "mptName", 100));
							list.add(addColumn("报关料件规格", "mptDeSpec", 100));
							list.add(addColumn("报关料件单位", "mptUnitName", 100));
							list.add(addColumn("单耗", "unitWaste", 100));
							list.add(addColumn("损耗", "waste", 100));
							list.add(addColumn("单项用量", "unitUsed", 100));
							list.add(addColumn("总用量", "totalDosage", 100));
							list.add(addColumn("折算系数", "unitConvert", 100));
							list.add(addColumn("报关数量", "ptTotal", 100));
							list.add(addColumn("制单号", "productNo", 100));
							return list;
						}
					});

			TableColumnModel cm = jTable.getColumnModel();
			ColumnGroup g_product = new ColumnGroup("成品");
			g_product.add(cm.getColumn(1));
			g_product.add(cm.getColumn(2));
			g_product.add(cm.getColumn(3));
			g_product.add(cm.getColumn(4));
			g_product.add(cm.getColumn(5));
			g_product.add(cm.getColumn(6));
			g_product.add(cm.getColumn(7));
			g_product.add(cm.getColumn(8));
			g_product.add(cm.getColumn(9));
			g_product.add(cm.getColumn(10));
			g_product.add(cm.getColumn(11));
			g_product.add(cm.getColumn(12));
			g_product.add(cm.getColumn(13));
			
			ColumnGroup g_bom = new ColumnGroup("料件");
			g_bom.add(cm.getColumn(13));
			g_bom.add(cm.getColumn(14));
			g_bom.add(cm.getColumn(15));
			g_bom.add(cm.getColumn(16));
			g_bom.add(cm.getColumn(17));
			g_bom.add(cm.getColumn(18));
			g_bom.add(cm.getColumn(19));
			g_bom.add(cm.getColumn(20));
			g_bom.add(cm.getColumn(21));
			g_bom.add(cm.getColumn(22));
			g_bom.add(cm.getColumn(23));
			g_bom.add(cm.getColumn(24));
			g_bom.add(cm.getColumn(25));
			g_bom.add(cm.getColumn(26));
			g_bom.add(cm.getColumn(27));
			g_bom.add(cm.getColumn(28));
			GroupableTableHeader header = (GroupableTableHeader) jTable
			.getTableHeader();
			header.setColumnModel(jTable.getColumnModel());
			header.addColumnGroup(g_product);
			header.addColumnGroup(g_bom);
			jTable.repaint();
	}
	/**
	 * 合并料件单元格
	 */
	public void combineTable(){
		((MultiSpanCellTable) jTable).splitRows2(new int[] {1,2,3,4,5,6,7,8,9,10,11,12,13});
		((MultiSpanCellTable) jTable).combineRowsByIndeies(
										new int[] {1,2,3,4,5,6,7,8,9,10,11,12,13}, 
										new int[] {1,2,3,4,5,6,7,8,9,10,11,12,13});
	}
	/**
	 * This method initializes jSplitPane	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setDividerLocation(200);
			jSplitPane.setBottomComponent(getJScrollPane());
			jSplitPane.setEnabled(false);
			jSplitPane.setTopComponent(getJPanel());
		}
		return jSplitPane;
	}

	/**
	 * This method initializes btPtPartBegin	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtPtPartBegin() {
		if (btPtPartBegin == null) {
			btPtPartBegin = new JButton();
			btPtPartBegin.setText("...");
			btPtPartBegin.setSize(new Dimension(25, 21));
			btPtPartBegin.setLocation(new Point(260, 28));
			btPtPartBegin.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String materialType = getMaterielType();
					List<TempObject> list = CasQuery.getInstance()
							.getPTFactoryAndFactualCustomsRalation(false,
									materialType, new ArrayList());
					if (list != null && list.size() > 0) {
						TempObject sm = list.get(0);
						Materiel m = (Materiel) sm.getObject();
						tfPtPartBegin.setText(m == null ? "" : m.getPtNo());
					
					}
				}
			});
		}
		return btPtPartBegin;
	}

	/**
	 * This method initializes btnPtNoBegin	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnPtNoBegin() {
		if (btnPtNoBegin == null) {
			btnPtNoBegin = new JButton();
			btnPtNoBegin.setText("...");
			btnPtNoBegin.setBounds(new Rectangle(260, 105, 25, 21));
			btnPtNoBegin
			.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List<TempObject> list = CasQuery.getInstance()
							.getFactoryMaterialPtno(false,
									MaterielType.MATERIEL,
									new ArrayList());
					if (list != null && list.size() > 0) {
						TempObject sm = list.get(0);
						Materiel m = (Materiel) sm.getObject();
						tfPtNoBegin.setText(m == null ? "" : m
								.getPtNo());
					}
					
				}
			});
		}
		return btnPtNoBegin;
	}

	/**
	 * This method initializes btnPtPartEnd	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnPtPartEnd() {
		if (btnPtPartEnd == null) {
			btnPtPartEnd = new JButton();
			btnPtPartEnd.setText("...");
			btnPtPartEnd.setSize(new Dimension(27, 22));
			btnPtPartEnd.setLocation(new Point(584, 28));
			btnPtPartEnd.setEnabled(false);
			btnPtPartEnd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String materialType = getMaterielType();
					List<TempObject> list = CasQuery.getInstance()
							.getPTFactoryAndFactualCustomsRalation(false,
									materialType, new ArrayList());
					if (list != null && list.size() > 0) {
						TempObject sm = list.get(0);
						Materiel m = (Materiel) sm.getObject();
						tfPtPartEnd.setText(m == null ? "" : m.getPtNo());
					
					}
				}
			});
		}
		return btnPtPartEnd;
	}

	/**
	 * This method initializes btnPtNoEnd	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnPtNoEnd() {
		if (btnPtNoEnd == null) {
			btnPtNoEnd = new JButton();
			btnPtNoEnd.setText("...");
			btnPtNoEnd.setSize(new Dimension(27, 22));
			btnPtNoEnd.setLocation(new Point(584, 104));
			btnPtNoEnd.setEnabled(false);
			btnPtNoEnd
			.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List<TempObject> list = CasQuery.getInstance()
							.getFactoryMaterialPtno(false,
									MaterielType.MATERIEL,
									new ArrayList());
					if (list != null && list.size() > 0) {
						TempObject sm = list.get(0);
						Materiel m = (Materiel) sm.getObject();
						tfPtNoEnd.setText(m == null ? "" : m
								.getPtNo());
					}
				}
			});
		}
		return btnPtNoEnd;
	}
	
	/**
	 * @return Returns the intOutFlat.
	 */
	public String getMaterielType() {
		return materielType;
	}

	/**
	 * @param intOutFlat
	 *            The intOutFlat to set.
	 */
	public void setMaterielType(String intOutFlat) {
		this.materielType = intOutFlat;
	}

	/**
	 * This method initializes tfPtName	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfPtName() {
		if (tfPtName == null) {
			tfPtName = new JTextField();
			tfPtName.setBounds(new Rectangle(433, 78, 150, 22));
		}
		return tfPtName;
	}

	/**
	 * This method initializes btnPtName	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnPtName() {
		if (btnPtName == null) {
			btnPtName = new JButton();
			btnPtName.setBounds(new Rectangle(584, 78, 27, 22));
			btnPtName.setText("...");
			btnPtName.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String materialType = getMaterielType();
					List<TempObject> list = CasQuery.getInstance()
							.getPTFactoryAndFactualCustomsRalation(false,
									materialType, new ArrayList());
					if (list != null && list.size() > 0) {
						TempObject sm = list.get(0);
						Materiel m = (Materiel) sm.getObject();
						tfPtName.setText(m == null ? "" : m.getFactoryName());
					
					}
				}
			});
		}
		return btnPtName;
	}

	/**
	 * This method initializes tfComplexCode	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfComplexCode() {
		if (tfComplexCode == null) {
			tfComplexCode = new JTextField();
			tfComplexCode.setBounds(new Rectangle(111, 80, 148, 22));
		}
		return tfComplexCode;
	}

	/**
	 * This method initializes btnComplexCode	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnComplexCode() {
		if (btnComplexCode == null) {
			btnComplexCode = new JButton();
			btnComplexCode.setText("...");
			btnComplexCode.setLocation(new Point(260, 80));
			btnComplexCode.setSize(new Dimension(25, 21));
			btnComplexCode.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String materialType = getMaterielType();
					Object object = CasQuery.getInstance()
							.findHsNameFromStatCusNameRelationHsn(MaterielType.SEMI_FINISHED_PRODUCT);
					if (object != null) {
						tfComplexCode.setText((String)((TempObject)object).getObject3());
					}
				}
			});
		}
		return btnComplexCode;
	}

	/**
	 * This method initializes tfHsName	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfHsName() {
		if (tfHsName == null) {
			tfHsName = new JTextField();
			tfHsName.setBounds(new Rectangle(433, 53, 150, 22));
		}
		return tfHsName;
	}

	/**
	 * This method initializes btnHsName	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnHsName() {
		if (btnHsName == null) {
			btnHsName = new JButton();
			btnHsName.setBounds(new Rectangle(584, 52, 27, 22));
			btnHsName.setText("...");
			btnHsName.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String materialType = getMaterielType();
					Object object = CasQuery.getInstance()
							.findHsNameFromStatCusNameRelationHsnmByXiaoK(materialType);
					if (object != null) {
						tfHsName.setText((String) ((TempObject) object)
								.getObject());
					}
				}
			});
		}
		return btnHsName;
	}

	/**
	 * This method initializes tfHsNoBegin	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfHsNoBegin() {
		if (tfHsNoBegin == null) {
			tfHsNoBegin = new JTextField();
			tfHsNoBegin.setBounds(new Rectangle(110, 130, 148, 22));
			tfHsNoBegin.getDocument().addDocumentListener(new DocumentListener() {
				private void setState() {
					String editAfterQueryValue = tfHsNoBegin.getText().trim();
					if (!"".equalsIgnoreCase(editAfterQueryValue)) {
						tfHsNoEnd.setEditable(true);
						btnHsNoEnd.setEnabled(true);
					} else {
						tfHsNoEnd.setEditable(false);
						btnHsNoEnd.setEnabled(false);
					}
				}

				public void insertUpdate(DocumentEvent e) {
					setState();
				}

				public void removeUpdate(DocumentEvent e) {
					setState();
				}

				public void changedUpdate(DocumentEvent e) {
					setState();
				}

			});
		}
		return tfHsNoBegin;
	}

	/**
	 * This method initializes btnHsNoBegin	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnHsNoBegin() {
		if (btnHsNoBegin == null) {
			btnHsNoBegin = new JButton();
			btnHsNoBegin.setBounds(new Rectangle(260, 130, 25, 20));
			btnHsNoBegin.setText("...");
			btnHsNoBegin
			.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Object object = CasQuery.getInstance()
							.findMertailHs(
									MaterielType.MATERIEL);
					if (object != null) {
						tfHsNoBegin
								.setText((String) ((TempObject) object)
										.getObject3());

					}
				}
			});
		}
		return btnHsNoBegin;
	}

	/**
	 * This method initializes tfHsNoEnd	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfHsNoEnd() {
		if (tfHsNoEnd == null) {
			tfHsNoEnd = new JTextField();
			tfHsNoEnd.setBounds(new Rectangle(432, 129, 151, 22));
			tfHsNoEnd.setEditable(false);
		}
		return tfHsNoEnd;
	}

	/**
	 * This method initializes btnHsNoEnd	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnHsNoEnd() {
		if (btnHsNoEnd == null) {
			btnHsNoEnd = new JButton();
			btnHsNoEnd.setBounds(new Rectangle(584, 129, 27, 22));
			btnHsNoEnd.setText("...");
			btnHsNoEnd
			.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Object object = CasQuery.getInstance()
							.findMertailHs(
									MaterielType.MATERIEL);
					if (object != null) {
						tfHsNoEnd
								.setText((String) ((TempObject) object)
										.getObject3());

					}
				}
			});
		}
		return btnHsNoEnd;
	}

	/**
	 * This method initializes tfProductNo	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfProductNo() {
		if (tfProductNo == null) {
			tfProductNo = new JTextField();
			tfProductNo.setBounds(new Rectangle(111, 54, 148, 22));
		}
		return tfProductNo;
	}

	/**
	 * This method initializes btnProductNo	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnProductNo() {
		if (btnProductNo == null) {
			btnProductNo = new JButton();
			btnProductNo.setBounds(new Rectangle(260, 54, 25, 21));
			btnProductNo.setText("...");
			btnProductNo
			.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Object object = CasQuery.getInstance().findMWorkOrder();
					if (object != null) {
						tfProductNo
								.setText((String) ((TempObject) object)
										.getObject());
						
					}
				}
			});
		}
		return btnProductNo;
	}

	/**
	 * This method initializes btprint2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtprint2() {
		if (btprint2 == null) {
			btprint2 = new JButton();
			btprint2.setBounds(new Rectangle(626, 105, 98, 24));
			btprint2.setText("打印报关级");
			btprint2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {
						List list = tableModel.getList();
						CustomReportDataSourceNew ds = new CustomReportDataSourceNew(
								list);
						InputStream masterReportStream = DgMaterialThatDayBalance.class
								.getResourceAsStream("report/CurrentBillDetailHsConvert.jasper");
						Map<String, Object> parameters = new HashMap<String, Object>();
						parameters.put("title", getTitle());
						parameters.put("type", getMaterielType());
						JasperPrint jasperPrint = JasperFillManager
								.fillReport(masterReportStream,
										parameters, ds);
						DgReportViewer viewer = new DgReportViewer(
								jasperPrint);
						viewer.setVisible(true);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});
		}
		return btprint2;
	}
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new MultiSpanCellTable() {
				protected JTableHeader createDefaultTableHeader() {
					return new GroupableTableHeader(columnModel);
				}
			};
			}
		return jTable;
	}
	/**
	 * 关联查询时的参数注入
	 * @param startDate
	 * @param endDate
	 * @param hsCode
	 * @param hsName
	 * @param hsSpec
	 * @param ptNo
	 * @param ptName
	 * @param ptSpec
	 * @author wss
	 */
	public void setQueryCondition(Date startDate,Date endDate,String hsCode,
			String hsName,String hsSpec,String ptNo,String ptName,String ptSpec){
		//如果结束日期不为空
		if(endDate != null){
			this.cbEndDate.setDate(endDate);
			if(startDate != null){
				this.cbStartDate.setDate(startDate);
			}else{
				this.cbStartDate.setDate(CommonVars.getEndDate(new Date(endDate.getYear(),0,1)));
			}
		}
		
		this.tfComplexCode.setText(hsCode);
		this.tfHsName.setText(hsName);
		
		this.tfPtPartBegin.setText(ptNo);
		this.tfPtPartEnd.setText("");
		this.tfPtName.setText(ptName);
	}
	
}  //  @jve:decl-index=0:visual-constraint="10,10"
