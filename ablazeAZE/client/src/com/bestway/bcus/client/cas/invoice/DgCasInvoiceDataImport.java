package com.bestway.bcus.client.cas.invoice;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.SwingWorker;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.entity.BillTemp;
import com.bestway.bcus.cas.invoice.entity.TempEmsImg;
import com.bestway.bcus.client.common.CommonFileFilter;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonStepProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DgInputColumnSet;
import com.bestway.bcus.client.common.FileReading;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.system.entity.InputTableColumnSet;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.util.RegexUtil;

@SuppressWarnings("unchecked")
public class DgCasInvoiceDataImport extends JDialogBase {
	
	private static final long serialVersionUID = 1L;

	private File txtFile = null;

	private boolean ok = false;

	private CasAction casAction = null;

	private JTableListModel tableModelDetail = null;

	private String materielType = null; // @jve:decl-index=0:

	private JPanel jContentPane = null;

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;

	private JPanel jPanel = null;

	private JToolBar jToolBar = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JButton btnColumn = null;

	private JButton jButton2 = null;

	private JCheckBox cbCheckTitle = null;

	private List importList = null;

	private JLabel jLabel = null;

	/**
	 * This method initializes
	 * 
	 */
	public DgCasInvoiceDataImport() {
		super();
		initialize();
		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
				"casAction");
	}

	public DgCasInvoiceDataImport(JFrame owner, boolean isModal) {
		super(owner, isModal);
		initialize();
		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
				"casAction");
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setTitle("国内购买发票数据导入");
		this.setSize(895, 500);
		initTable(new ArrayList());
	}

	private void validate(List list) {
		Request request = new Request(CommonVars.getCurrUser());
		BillTemp data = null;
		HashMap<String, String> scmcocMap = new HashMap();
		HashMap<String, String> scmcocCodeMap = new HashMap();
		HashMap<String, String> complexMap = new HashMap();
		HashMap<String, String> unitMap = new HashMap();
		HashMap<String, Integer> compareMap = new HashMap();
		List<ScmCoc> scmcoc = casAction.findScmcoc(request);
		List<Complex> complex = casAction.findComplex(request);
		List<Unit> unit = casAction.findUnit(request);
		List<TempEmsImg> compare = casAction.findImgFromBaseEmsHead(request,
				null);
		if (scmcoc != null && scmcoc.size() != 0) {
			for (ScmCoc item : scmcoc) {
				if (item.getName() != null)
					scmcocMap.put(item.getName(), item.getId());
					scmcocCodeMap.put(item.getCode(), item.getId());
			}
		}
		if (complex != null && complex.size() != 0) {
			for (Complex item : complex) {
				complexMap.put(item.getCode(), item.getId());
			}
		}
		if (unit != null && unit.size() != 0) {
			for (Unit item : unit) {
				unitMap.put(item.getName(), item.getCode());
			}
		}
		if (compare != null && compare.size() != 0) {
			for (TempEmsImg item : compare) {
				String key = (item.getComplex().getCode() == null ? "" : item
						.getComplex().getCode())
						+ "/"
						+ (item.getCusName() == null ? "" : item.getCusName())
						+ "/"
						+ (item.getCusSpec() == null ? "" : item.getCusSpec());
				compareMap.put(key, item.getSeqNum());
			}
		}
		List invoiceNo = casAction.findInvoiceNo(request);
		
		for (int i = 0; i < list.size(); i++) {
			data = (BillTemp) list.get(i);
			data.setErrinfo("");
			// ========================================================表头
			if (invoiceNo.contains(data.getBill1())) {// 发票号
				data.setErrinfo(data.getErrinfo() + "系统已经存在此发票号,不能重复!");
			}
			
			// 发票日期
			if(!RegexUtil.checkMatch(data.getBill2(), "^\\d{4}-\\d{2}-\\d{2}$")){
				data.setErrinfo(data.getErrinfo() + "发票日期格式错误;");
			}
			
			String scmcocId = "";// 客户ID
			if ((scmcocId = scmcocMap.get(data.getBill3())) != null 
					|| (scmcocId = scmcocCodeMap.get(data.getBill3())) != null) {
				data.setBill23(scmcocId);// 客户ID
			} else {
				data.setErrinfo(data.getErrinfo() + "客户名称不存在;");
			}
			if ("0".equals(data.getBill5()) || "1".equals(data.getBill5())) {
			} else {
				data.setErrinfo(data.getErrinfo() + "是否核销设置不对,请检查是否填0或1;");
			}

			// =======================================================表体
			String complexId = "";// 商品编码
			if ((complexId = complexMap.get(data.getBill6())) != null) {
				data.setBill26(complexId);// 编码ID
			} else {
				data.setErrinfo(data.getErrinfo() + "商品编码不存在;");
			}
			String unitCode = "";// 报关单位
			if ((unitCode = unitMap.get(data.getBill9())) != null) {
				data.setBill29(unitCode);// 报关单位代码
			} else {
				data.setErrinfo(data.getErrinfo() + "报关单位不存在;");
			}
			// 对应的归并序号
			if (data.getBill6() != null) {
				String key = (data.getBill26() == null ? "" : data.getBill26())
						+ "/" + (data.getBill7() == null ? "" : data.getBill7())
						+ "/" + (data.getBill8() == null ? "" : data.getBill8());
				if (compareMap.get(key) == null) {
					data.setErrinfo(data.getErrinfo() + "此商品编码+报关名称+报关规格在手册中不存在;");
				}
			}
			// 发票数量
			if(!RegexUtil.checkFloat(data.getBill11())) {
				data.setErrinfo(data.getErrinfo() + "发票数量格式不正确;");
			}
				
			// 单价
			if(!RegexUtil.checkFloat(data.getBill12())) {
				data.setErrinfo(data.getErrinfo() + "发票单价格式不正确;");
			}
			// 总金额
			if (data.getBill11() != null && data.getBill12() != null) {
				Double tmp = Double.parseDouble(data.getBill11())
						* Double.parseDouble(data.getBill12());
				data.setBill13(Double.toString(tmp));
			}
			
			// 总净量
			if(data.getBill14() == null || "".equals(data.getBill14())) {
				data.setErrinfo(data.getErrinfo() + "总净重量必填;");
			} else if(!RegexUtil.checkFloat(data.getBill14())) {
				data.setErrinfo(data.getErrinfo() + "总净重量格式不正确;");
			}
			
			// 核销数量
			if(data.getBill15() == null || "".equals(data.getBill15())) {
				//data.setErrinfo(data.getErrinfo() + "核销数量必填;");
				//为空补0
				data.setBill15("0");
			} else if(!RegexUtil.checkFloat(data.getBill15())) {
				data.setErrinfo(data.getErrinfo() + "发票核销数量格式不正确;");
			}
		}
	}

	private String getSuffix(File f) {
		String s = f.getPath(), suffix = null;
		int i = s.lastIndexOf('.');
		if (i > 0 && i < s.length() - 1)
			suffix = s.substring(i + 1).toLowerCase();
		return suffix;
	}

	/**
	 * @return Returns the ok.
	 */
	public boolean isOk() {
		return ok;
	}

	/**
	 * @param ok
	 *            The ok to set.
	 */
	public void setOk(boolean ok) {
		this.ok = ok;
	}

	/**
	 * @return the materielType
	 */
	public String getMaterielType() {
		return materielType;
	}

	/**
	 * @param materielType
	 *            the materielType to set
	 */
	public void setMaterielType(String materielType) {
		this.materielType = materielType;
	}


	// 发票体
	private void initTable(final List list) {
		tableModelDetail = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> header = new Vector<JTableListColumn>();
						header.add(addColumn("1.发票号码*", "bill1", 60));								//1
						header.add(addColumn("2.发票日期(YYYY-MM-DD)*", "bill2", 135));					//2
						header.add(addColumn("3.客户供应商(全称或代码，不能为简称)*", "bill3", 100));			//3
						header.add(addColumn("4.手册号*", "bill4", 60));									//4
						header.add(addColumn("5.是否核销('0'为否,'1'为是)*", "bill5", 150));				//5
						header.add(addColumn("6.商品编码*", "bill6", 80));								//6
						header.add(addColumn("7.发票名称*", "bill7", 100));								//7
						header.add(addColumn("8.发票规格*", "bill8", 100));								//8
						header.add(addColumn("9.发票单位*", "bill9", 70));								//9
						header.add(addColumn("10.备案序号", "bill10", 70));								//10
						header.add(addColumn("11.数量*", "bill11", 60));									//11
						header.add(addColumn("12.单价*", "bill12", 60));									//12
						header.add(addColumn("13.总金额", "bill13", 60));									//13
						header.add(addColumn("14.总净重*", "bill14", 60));								//14
						header.add(addColumn("15.核销数量*", "bill15", 70));								//15
						
						// 有导入数据的时候显示错误信息栏
						if(list != null && list.size() > 0) {
							header.add(addColumn("错误信息", "errinfo", 170));
						}
						return header;
					}
				});
	}

	class SaveDataRunnable extends Thread {
		boolean isok = true;

		public void run() {
			List list = null;
			List header = null;
			try {
				String taskId = CommonProgress.getExeTaskId();
				CommonStepProgress.showStepProgressDialog(taskId);
				CommonStepProgress.setStepMessage("系统正在读取导入资料，请稍后...");
				Request request = new Request(CommonVars.getCurrUser());
				request.setTaskId(taskId);
				list = tableModelDetail.getList();
				/*
				 * 抽出表体记录 
				 */
				Map<String, BillTemp> map = new HashMap<String, BillTemp>();
				String key = null;
				for (int i = 0; i < list.size(); i++) {
					BillTemp item = (BillTemp) list.get(i);
					key = (item.getBill1() == null ? "" : item.getBill1()) + "/"
					+ (item.getBill2() == null ? "" : item.getBill2()) + "/"
					+ (item.getBill3() == null ? "" : item.getBill3()) + "/"
					+ (item.getBill4() == null ? "" : item.getBill4()) + "/"
					+ (item.getBill5() == null ? "" : item.getBill5());
					map.put(key, item);
					if(item.getErrinfo()!= null && !"".equals(item.getErrinfo())) {
						throw new RuntimeException("导入存在错误，不能保存！请查看最后一列的错误信息。");
					}
				}
				header = new ArrayList(map.values());
				
				try {
					casAction.importDataToCasInvoice(new Request(CommonVars
							.getCurrUser()), header, list, taskId);
				} catch (SQLException e1) {
					isok = false;
					e1.printStackTrace();
				}
				CommonStepProgress.closeStepProgressDialog();
			} catch (Exception e) {
				isok = false;
				e.printStackTrace();
				System.out.println(e.getMessage());
			} finally {
				CommonStepProgress.closeStepProgressDialog();
				if (isok) {
					JOptionPane.showMessageDialog(DgCasInvoiceDataImport.this,
							"导入完毕!\n\n单据头数据: " + String.valueOf(list.size())
									+ " 笔\n单据体数据: "
									+ String.valueOf(list.size()) + " 笔",
							"提示", 2);
				} else {
					JOptionPane.showMessageDialog(DgCasInvoiceDataImport.this,
							"装载失败！", "提示！", 2);
				}
			}
		}
	}


	/**
	 * This method initializes jContentPane	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
			jContentPane.add(getJPanel(), java.awt.BorderLayout.NORTH);
		}
		return jContentPane;
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
	 * This method initializes jTable	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
			jTable.setRowHeight(25);
		}
		return jTable;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.setPreferredSize(new Dimension(1, 35));
			jPanel.add(getJToolBar(), java.awt.BorderLayout.NORTH);
		}
		return jPanel;
	}

	/**
	 * This method initializes jToolBar	
	 * 	
	 * @return javax.swing.JToolBar	
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jLabel = new JLabel();
			jLabel.setText("'*'号栏位为必填栏位。");
			FlowLayout f = new FlowLayout();
			f.setHgap(1);
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(1);
			jToolBar = new JToolBar();
			jToolBar.setLayout(f);
			jToolBar.setBounds(new Rectangle(5, 5, 783, 30));
			jToolBar.setFloatable(false);
			jToolBar.add(getJButton());
			jToolBar.add(getJButton1());
			jToolBar.add(getBtnColumn());
			jToolBar.add(getJButton2());
			jToolBar.add(getCbCheckTitle());
			jToolBar.add(jLabel);
		}
		return jToolBar;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setPreferredSize(new Dimension(65, 30));
			jButton.setText("1.\u6253\u5f00");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.removeChoosableFileFilter(fileChooser
							.getFileFilter());
					fileChooser.setFileFilter(new CommonFileFilter(
							new String[] { "txt", "xls" }, "选择文档"));
					int state = fileChooser
							.showOpenDialog(DgCasInvoiceDataImport.this);
					if (state != JFileChooser.APPROVE_OPTION) {
						return;
					}
					txtFile = fileChooser.getSelectedFile();
					if (txtFile == null || !txtFile.exists()) {
						return;
					}
					new ImportFileDataRunnable().execute();
				}
			});
		}
		return jButton;
	}
	
	/**
	 * 导入文件线程
	 */
	class ImportFileDataRunnable extends SwingWorker {

		@Override
		protected Object doInBackground() throws Exception {
			try {
				CommonProgress.showProgressDialog(DgCasInvoiceDataImport.this);
				CommonProgress.setMessage("系统正在读取与检验文件资料，请稍后...");
				importList = parseTxtFile();
				validate(importList);
				CommonProgress.closeProgressDialog();
			} catch (Exception ex) {
				CommonProgress.closeProgressDialog();
				ex.printStackTrace();
				JOptionPane.showMessageDialog(DgCasInvoiceDataImport.this,
						"导入数据失败 " + ex.getMessage(), "提示",
						JOptionPane.ERROR_MESSAGE);
			} finally {
				CommonProgress.closeProgressDialog();
			}
			return importList;
		}

		@Override
		protected void done() {
			List listValues = null;
			try {
				listValues = (List) this.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			initTable(listValues);
		}
	}
	
	private List parseTxtFile() {

		String suffix = getSuffix(txtFile);
		List<List> lines = new ArrayList<List>();
		if (suffix.equals("xls")) {
			// 导入xls
			if (cbCheckTitle.isSelected()) {
				lines = FileReading.readExcel(txtFile, 2, null);
			} else {
				lines = FileReading.readExcel(txtFile, 1, null);
			}
		} else {
			// 导入txt
			if (cbCheckTitle.isSelected()) {
				lines = FileReading.readTxt(txtFile, 2, null);
			} else {
				lines = FileReading.readTxt(txtFile, 1, null);
			}
		}
		
		List billList = new ArrayList(lines.size());
		List<String> tmp = null;
		BillTemp bill = null;
		for (int i = 0; i < lines.size(); i++) {
			tmp = lines.get(i);
			bill = new BillTemp();
			bill.setBill1(tmp.get(0));
			bill.setBill2(tmp.get(1));
			bill.setBill3(tmp.get(2));
			bill.setBill4(tmp.get(3));
			bill.setBill5(tmp.get(4));
			bill.setBill6(tmp.get(5));
			bill.setBill7(tmp.get(6));
			bill.setBill8(tmp.get(7));
			bill.setBill9(tmp.get(8));
			bill.setBill10(tmp.get(9));
			bill.setBill11(tmp.get(10));
			bill.setBill12(tmp.get(11));
			bill.setBill13(tmp.get(12));
			bill.setBill14(tmp.get(13));
			bill.setBill15(tmp.get(14));
			billList.add(bill);
		}
		return billList;
	}

	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setPreferredSize(new Dimension(65, 30));
			jButton1.setText("2.\u4fdd\u5b58");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new SaveDataRunnable().start();
				}
			});
		}
		return jButton1;
	}

	private JButton getBtnColumn() {
		if (btnColumn == null) {
			btnColumn = new JButton();
			btnColumn.setPreferredSize(new Dimension(65, 30));
			btnColumn.setText("\u680f\u4f4d\u8bbe\u5b9a");
			btnColumn.setVisible(false);
			btnColumn.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgInputColumnSet dg = new DgInputColumnSet();
					dg.setTableFlag(InputTableColumnSet.EMSIMGER_BOM_INPUT);
					dg.setVisible(true);
					if (dg.isOk()) {
						initTable(new ArrayList());
					}
				}
			});
		}
		return btnColumn;
	}

	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setPreferredSize(new Dimension(65, 30));
			jButton2.setText("\u9000\u51fa");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton2;
	}

	private JCheckBox getCbCheckTitle() {
		if (cbCheckTitle == null) {
			cbCheckTitle = new JCheckBox();
			cbCheckTitle.setSelected(true);
			cbCheckTitle.setText("\u7b2c\u4e00\u884c\u4e3a\u6807\u9898");
		}
		return cbCheckTitle;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
