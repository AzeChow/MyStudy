package com.bestway.client.custom.common.report;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.tableeditor.JNumberTableCellEditor;
import com.bestway.bcus.client.common.tableeditor.JQueryFieldTableCellEditor;
import com.bestway.bcus.client.common.tableeditor.JTextFieldTableCellEditor;
import com.bestway.bcus.client.common.tableeditor.TableCellEditorListener;
import com.bestway.bcus.client.common.tableeditor.TableCellEditorParameter;
import com.bestway.bcus.client.common.tableeditor.JQueryFiled.QueryDataListenser;
import com.bestway.bcus.client.common.tableeditor.JQueryFiled.QueryFormListenser;
import com.bestway.bcus.custombase.action.CustomBaseAction;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Wrap;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.footer.JFooterScrollPane;
import com.bestway.client.util.footer.JFooterTable;
import com.bestway.client.util.footer.TableFooterType;
import com.bestway.common.Request;
import com.bestway.customs.common.action.BaseEncAction;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;
import com.bestway.customs.common.entity.TempIEMergerZaihouList;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author lxr
 * 
 */
@SuppressWarnings("unchecked")
public class DgIEMergerZaiHuoListReportSet extends JDialogBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 报关单的基础类接口
	 */
	protected BaseEncAction baseEncAction = null;

	private CustomBaseAction customBaseAction = null;

	private javax.swing.JPanel jContentPane = null;

	private JButton btnCancel = null;

	private int projectType;

	private BaseCustomsDeclaration baseCustomsDeclaration = null; // @jve:decl-index=0:

	private JButton btnDelete = null;

	private JButton btnAdd = null;

	private JSplitPane jSplitPane = null;

	private JToolBar jToolBar = null;

	private JPanel jPanel = null;

	private JFooterScrollPane jScrollPane = null;

	private JFooterTable tbBottom = null;

	private JPanel jPanel1 = null;

	private JFooterScrollPane jScrollPane1 = null;

	private JFooterTable tbTop = null;

	private JButton btnMerger = null;

	private JButton btnMove = null;

	private JPanel jPanel2 = null;

	private JTableListModel tableTop = null;

	private JTableListModel tableBottom = null;

	private Integer ImpExpFlag = null;

	private List returnList = new ArrayList(); // @jve:decl-index=0:

	private Boolean isOk = false;

	private JButton btnOk = null;

	private JPanel jPanel3 = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JTextField tfZuangHou = null;

	private JLabel jLabel2 = null;

	private JTextField tfXieHou = null;

	private JCheckBox cbbReworkTotalPrice = null;

	private JButton btnSearch = null;

	private JCheckBox cbbSelectAll = null;

	public Boolean getIsOk() {
		return isOk;
	}

	public void setIsOk(Boolean isOk) {
		this.isOk = isOk;
	}

	public List getReturnList() {
		return returnList;
	}

	public void setReturnList(List returnList) {
		this.returnList = returnList;
	}

	public String getXieHou() {
		return this.tfXieHou.getText().trim();

	}

	public String getZuangHou() {
		return this.tfZuangHou.getText().trim();
	}

	/**
	 * This is the default constructor
	 */
	public DgIEMergerZaiHuoListReportSet() {
		super();
		customBaseAction = (CustomBaseAction) CommonVars
				.getApplicationContext().getBean("customBaseAction");
		initialize();
		initTableTOP(new ArrayList());
		initTableBootom(new ArrayList());
	}

	public void setVisible(boolean b) {
		if (b) {
			tfZuangHou
					.setText(getImpExpFlag().equals("0") ? baseCustomsDeclaration
							.getCustoms() == null ? "" : baseCustomsDeclaration
							.getCustoms().getName()
							: CustomsDeclarationSubReportDataSource
									.getInstance().getMachName());
			if (getImpExpFlag().equals("0")) {
				tfXieHou.setText(CustomsDeclarationSubReportDataSource
						.getInstance().getMachName());
			} else {
				String xieHou = baseCustomsDeclaration.getCustoms() == null ? ""
						: baseCustomsDeclaration.getCustoms().getCode();
				// 深盐物流(5343)皇岗海关(5301)
				tfXieHou.setText("5301".equals(xieHou) ? "香港" : ("5343"
						.equals(xieHou)) ? "深圳" : "");
			}
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
		this.setTitle("打印数据设置");
		this.setSize(751, 533);
		this.setContentPane(getJContentPane());
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				setIsOk(false);
			}
		});
	}

	/**
	 * 获得源
	 */
	private List getDataSource() {
		String conveyance = baseCustomsDeclaration.getConveyance();// 运输工具
		List allList = null;
		if (conveyance != null && !"".equals(conveyance.trim())){
			allList = this.baseEncAction
					.findMergerCustomsDeclarationCommInfoByConveyance(
							new Request(CommonVars.getCurrUser()), conveyance,
							getImpExpFlag(), cbbReworkTotalPrice.isSelected());
		}

		return allList;
	}

	/**
	 * 初始化报关单合并前
	 */
	private void initTableTOP(List list) {
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List list = new Vector();
				list.add(addColumn("选择", "isSelected", 40));
				list.add(addColumn("是否合并", "addType", 60));
				list.add(addColumn("商品编号", "complex.code", 80));
				list.add(addColumn("商品名称", "commName", 150));
				list.add(addColumn("型号规格", "commSpec", 150));
				list.add(addColumn("包装种类", "wrapType.name", 60));
				list.add(addColumn("总价", "commTotalPrice", 80));
				list.add(addColumn("净重", "netWeight", 60));
				list.add(addColumn("件数", "pieces", 40));

				return list;
			}
		};
		jTableListModelAdapter.setEditableColumn(1);
		tableTop = new JTableListModel(tbTop, list, jTableListModelAdapter);
		settbImgColor(tbTop);
		tbTop.getColumnModel().getColumn(1)
				.setCellRenderer(new MultiRenderer());
		tbTop.getColumnModel().getColumn(1).setCellEditor(
				new CheckBoxEditor(new JCheckBox()));
		// 页脚
		tableTop.clearFooterTypeInfo();
		tableTop.addFooterTypeInfo(new TableFooterType(3,
				TableFooterType.CONSTANT, "合计"));
		tableTop.addFooterTypeInfo(new TableFooterType(9, TableFooterType.SUM,
				"", 2));
		tableTop.addFooterTypeInfo(new TableFooterType(7, TableFooterType.SUM,
				"", 4));
		tableTop.addFooterTypeInfo(new TableFooterType(8, TableFooterType.SUM,
				""));

	}

	public void settbImgColor(JTable table) {
		int columnCount = table.getColumnCount();
		for (int i = 1; i < columnCount; i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(
					new DefaultTableCellRenderer() {

						/**
						 * 
						 */
						private static final long serialVersionUID = 1L;

						public Component getTableCellRendererComponent(
								JTable table, Object value, boolean isSelected,
								boolean hasFocus, int row, int column) {
							super.getTableCellRendererComponent(table, value,
									isSelected, hasFocus, row, column);
							if (row == table.getSelectedRow()) {
								setForeground(table.getSelectionForeground());
								setBackground(table.getSelectionBackground());
							} else {
								setForeground(table.getForeground());
								setBackground(table.getBackground());
							}
							String isSelect = ((String) table.getModel()
									.getValueAt(row, 2).toString());

							if (isSelect != null && "是".equals(isSelect)) {
								setForeground(Color.gray);
							}
							return this;
						}
					});
		}
	}

	/**
	 * 初始化报关单合并后或下移后
	 */
	private void initTableBootom(List list) {
		JTableListModelAdapter tableModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("商品编号", "complexCode", 80));
				list.add(addColumn("商品名称", "commName", 250));
				list.add(addColumn("包装种类", "wrapTypeName", 60));
				list.add(addColumn("总价", "commTotalPrice", 80));
				list.add(addColumn("净重", "netWeight", 80));
				list.add(addColumn("毛重", "grossWeight", 60));
				list.add(addColumn("件数", "pieces", 60));

				return list;
			}
		};
		JTableListModel.dataBind(tbBottom, list, tableModelAdapter,
				ListSelectionModel.SINGLE_SELECTION);
		tableBottom = (JTableListModel) tbBottom.getModel();
		tableModelAdapter.setEditableColumn(1);
		tableModelAdapter.setEditableColumn(2);
		tableModelAdapter.setEditableColumn(3);
		tableModelAdapter.setEditableColumn(4);
		tableModelAdapter.setEditableColumn(5);
		tableModelAdapter.setEditableColumn(6);
		tableBottom.setAllowSetValue(true);
		JQueryFieldTableCellEditor complexTableCellEditor = new JQueryFieldTableCellEditor(
				"");
		complexTableCellEditor.setQueryFormListenser(new QueryFormListenser() {
			public Object getQueryResult() {
				Complex complex = (Complex) CommonQuery.getInstance()
						.getComplex();
				if (complex != null) {
					return complex.getCode();
				}
				return "";
			}
		});
		complexTableCellEditor.setQueryDataListenser(new QueryDataListenser() {

			public Object getQueryResult(String fieldText) {
				List list = customBaseAction.findComplex("code", fieldText);
				if (list.size() > 0) {
					Complex complex = (Complex) list.get(0);
					if (complex != null) {
						return complex.getCode();
					} else {
						JOptionPane.showMessageDialog(
								DgIEMergerZaiHuoListReportSet.this, "商品编码"
										+ fieldText + "不存在");
					}
				}
				return fieldText;
			}
		});
		complexTableCellEditor
				.addDataChangedListener(new TableCellEditorListener() {

					public void run(TableCellEditorParameter parm) {
						TempIEMergerZaihouList zaiHou = (TempIEMergerZaihouList) parm
								.getEditingData();
						String complexCode = zaiHou.getComplexCode();
						if (complexCode == null
								|| "".equals(complexCode.trim())) {
							zaiHou.setComplexCode("");
						} else {
							List list = customBaseAction.findComplex("code",
									complexCode.trim());
							if (list.size() > 0) {
								Complex complex = (Complex) list.get(0);
								if (complex != null) {
									zaiHou.setComplexCode(complex.getCode());
									tableBottom.updateRow(zaiHou);
								}
							}
						}
					}
				});
		tbBottom.getColumnModel().getColumn(1).setCellEditor(
				complexTableCellEditor);

		JTextFieldTableCellEditor commNameTableCellEditor = new JTextFieldTableCellEditor();
		tbBottom.getColumnModel().getColumn(2).setCellEditor(
				commNameTableCellEditor);

		JQueryFieldTableCellEditor wrapTypeTableCellEditor = new JQueryFieldTableCellEditor(
				"");
		wrapTypeTableCellEditor.setQueryFormListenser(new QueryFormListenser() {
			public Object getQueryResult() {
				Wrap wrap = (Wrap) CommonQuery.getInstance().getWrap();
				if (wrap != null) {
					return wrap.getCode();
				}
				return "";
			}
		});
		wrapTypeTableCellEditor.setQueryDataListenser(new QueryDataListenser() {

			public Object getQueryResult(String fieldText) {
				List list = customBaseAction.findWrap("code", fieldText);
				if (list.size() > 0) {
					Wrap wrap = (Wrap) list.get(0);
					if (wrap != null) {
						return wrap.getCode();
					} else {
						JOptionPane.showMessageDialog(
								DgIEMergerZaiHuoListReportSet.this, "商品编码"
										+ fieldText + "不存在");
					}
				}
				return fieldText;
			}
		});
		wrapTypeTableCellEditor
				.addDataChangedListener(new TableCellEditorListener() {

					public void run(TableCellEditorParameter parm) {
						TempIEMergerZaihouList zaiHou = (TempIEMergerZaihouList) parm
								.getEditingData();
						String wrapName = zaiHou.getWrapTypeName();
						if (wrapName == null || "".equals(wrapName.trim())) {
							zaiHou.setComplexCode("");
						} else {
							List list = customBaseAction.findWrap("code",
									wrapName.trim());
							if (list.size() > 0) {
								Wrap wrap = (Wrap) list.get(0);
								if (wrap != null) {
									zaiHou.setWrapTypeName(wrap.getName());
									tableBottom.updateRow(zaiHou);
								}
							}
						}
					}
				});

		tbBottom.getColumnModel().getColumn(3).setCellEditor(
				wrapTypeTableCellEditor);
		JNumberTableCellEditor commTotalPriceTableCellEditor = new JNumberTableCellEditor(
				9);
		tbBottom.getColumnModel().getColumn(4).setCellEditor(
				commTotalPriceTableCellEditor);
		JNumberTableCellEditor netWeightTableCellEditor = new JNumberTableCellEditor(
				9);
		tbBottom.getColumnModel().getColumn(5).setCellEditor(
				netWeightTableCellEditor);
		
		JNumberTableCellEditor grossWeightTableCellEditor = new JNumberTableCellEditor(
				9);
		tbBottom.getColumnModel().getColumn(6).setCellEditor(
				grossWeightTableCellEditor);
		
		JTextFieldTableCellEditor piecesTableCellEditor = new JTextFieldTableCellEditor();
		tbBottom.getColumnModel().getColumn(7).setCellEditor(
				piecesTableCellEditor);
		// 页脚
		tableBottom.clearFooterTypeInfo();
		tableBottom.addFooterTypeInfo(new TableFooterType(1,
				TableFooterType.CONSTANT, "合计"));
		tableBottom.addFooterTypeInfo(new TableFooterType(4,
				TableFooterType.SUM, "", 2));
		tableBottom.addFooterTypeInfo(new TableFooterType(5,
				TableFooterType.SUM, "", 4));
		tableBottom.addFooterTypeInfo(new TableFooterType(6,
				TableFooterType.SUM, "", 4));
		tableBottom.addFooterTypeInfo(new TableFooterType(7,
				TableFooterType.SUM, ""));
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
			jContentPane.add(getJSplitPane(), BorderLayout.CENTER);
			jContentPane.add(getJToolBar(), BorderLayout.SOUTH);
		}

		return jContentPane;
	}

	/**
	 * This method initializes btnCancel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText("取消");
			btnCancel.setPreferredSize(new Dimension(70, 30));
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
					setIsOk(false);
				}
			});
		}
		return btnCancel;
	}

	public int getProjectType() {
		return projectType;
	}

	public void setProjectType(int projectType) {
		this.projectType = projectType;
	}

	public Integer getImpExpFlag() {
		return ImpExpFlag;
	}

	public void setImpExpFlag(Integer impExpFlag) {
		ImpExpFlag = impExpFlag;
	}

	public BaseEncAction getBaseEncAction() {
		return baseEncAction;
	}

	public void setBaseEncAction(BaseEncAction baseEncAction) {
		this.baseEncAction = baseEncAction;
	}

	public BaseCustomsDeclaration getBaseCustomsDeclaration() {
		return baseCustomsDeclaration;
	}

	public void setBaseCustomsDeclaration(
			BaseCustomsDeclaration baseCustomsDeclaration) {
		this.baseCustomsDeclaration = baseCustomsDeclaration;
	}

	/**
	 * This method initializes btnDelete
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setText("删除");
			btnDelete.setPreferredSize(new Dimension(70, 30));
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableBottom == null
							|| tableBottom.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(
								DgIEMergerZaiHuoListReportSet.this,
								"请选择要删除的记录!!!", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					for (int i = 0; i < tableBottom.getCurrentRows().size(); i++) {
						TempIEMergerZaihouList obj = (TempIEMergerZaihouList) tableBottom
								.getCurrentRows().get(i);
						tableBottom.deleteRow(obj);
					}
				}
			});
		}
		return btnDelete;
	}

	/**
	 * This method initializes btnAdd
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setText("新增");
			btnAdd.setPreferredSize(new Dimension(70, 30));
			btnAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					TempIEMergerZaihouList object = new TempIEMergerZaihouList();
					// tableBottom.addEditedData(object);
					tableBottom.addRow(object);
				}
			});
		}
		return btnAdd;
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setBounds(new Rectangle(43, 16, 468, 181));
			jSplitPane.setDividerSize(0);
			jSplitPane.setDividerLocation(280);
			jSplitPane.setTopComponent(getJPanel());
			jSplitPane.setBottomComponent(getJScrollPane());
			jSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			FlowLayout f = new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(1);
			f.setHgap(1);
			jToolBar = new JToolBar();
			jToolBar.setLayout(f);
			jToolBar.add(getJPanel2());
			jToolBar.add(getBtnAdd());
			jToolBar.add(getBtnDelete());
			jToolBar.add(getBtnOk());
			jToolBar.add(getBtnCancel());

		}
		return jToolBar;
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
			jPanel.add(getJPanel1(), BorderLayout.SOUTH);
			jPanel.add(getJScrollPane1(), BorderLayout.CENTER);
			jPanel.add(getJPanel3(), BorderLayout.NORTH);
		}
		return jPanel;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JFooterScrollPane();
			jScrollPane.setViewportView(getTbBottom());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes tbBottom
	 * 
	 * @return javax.swing.JTable
	 */
	private JFooterTable getTbBottom() {
		if (tbBottom == null) {
			tbBottom = new JFooterTable();
		}
		return tbBottom;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLabel2 = new JLabel();
			jLabel2.setText("卸货地点");
			jLabel2.setBounds(new Rectangle(215, 27, 49, 18));
			jLabel1 = new JLabel();
			jLabel1.setText("装货地点");
			jLabel1.setBounds(new Rectangle(13, 27, 54, 18));
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setPreferredSize(new Dimension(1, 35));
			jPanel1.add(getBtnMerger(), null);
			jPanel1.add(getBtnMove(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JFooterScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JFooterScrollPane();
			jScrollPane1.setBounds(new Rectangle(248, 171, 453, 421));
			jScrollPane1.setViewportView(getTbTop());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes tbTop
	 * 
	 * @return javax.swing.JTable
	 */
	private JFooterTable getTbTop() {
		if (tbTop == null) {
			tbTop = new JFooterTable();
		}
		return tbTop;
	}

	/**
	 * This method initializes btnMerger
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnMerger() {
		if (btnMerger == null) {
			btnMerger = new JButton();
			btnMerger.setBounds(new Rectangle(235, 6, 75, 23));
			btnMerger.setText("合并");
			btnMerger.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableTop == null
							|| tableTop.getList().size() <= 0) {
						JOptionPane.showMessageDialog(
								DgIEMergerZaiHuoListReportSet.this, "没有可选择的行",
								"提示", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					List selectList = new ArrayList();
					for (int i = 0; i < tableTop.getList().size(); i++) {
						BaseCustomsDeclarationCommInfo obj = (BaseCustomsDeclarationCommInfo) tableTop
								.getList().get(i);
						if (obj.getIsSelected() != null && obj.getIsSelected()) {
							selectList.add(obj);
						}
					}
					for (int i = 0; i < selectList.size(); i++) {
						BaseCustomsDeclarationCommInfo obj = (BaseCustomsDeclarationCommInfo) selectList.get(i);
						if ("是".equals(obj.getAddType())) {
							JOptionPane.showMessageDialog(
									DgIEMergerZaiHuoListReportSet.this,
									"存在已合并的行", "提示",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}
					}
					List listBootom = new ArrayList();
					List<String> listKey = new ArrayList();
					TempIEMergerZaihouList obj1 = null;
					double totalPrice = 0.0;
					double netWeight = 0.0;
					int pieces = 0;
					double grossWeight = 0.0;
					for (int i = 0; i < selectList.size(); i++) {
						BaseCustomsDeclarationCommInfo commInfo = (BaseCustomsDeclarationCommInfo) selectList
								.get(i);
						//
						// 4位编码相同
						//
						String key = commInfo.getComplex() == null ? ""
								: commInfo.getComplex().getCode().substring(0,
										4);
						if (listKey.contains(key)) {
							totalPrice += commInfo.getCommTotalPrice();
							netWeight += commInfo.getNetWeight();
							grossWeight += (commInfo.getGrossWeight() == null ? 0
									: commInfo.getGrossWeight());
							pieces += commInfo.getPieces();
							obj1.setCommTotalPrice(totalPrice);
							obj1.setNetWeight(netWeight);
							obj1.setGrossWeight(grossWeight);
							obj1.setPieces(String.valueOf(pieces));
						} else {
							totalPrice = 0.0;
							netWeight = 0.0;
							pieces = 0;
							grossWeight= 0;
							obj1 = new TempIEMergerZaihouList();
							obj1
									.setComplexCode(commInfo.getComplex() == null ? ""
											: commInfo.getComplex().getCode());
							obj1.setCommName(commInfo.getCommName());// 商品名称
							obj1
									.setWrapTypeName(commInfo.getWrapType() == null ? ""
											: commInfo.getWrapType().getName());
							totalPrice = commInfo.getCommTotalPrice();// 总价
							netWeight = commInfo.getNetWeight();// 净得
							pieces = commInfo.getPieces();// 件数
							grossWeight = commInfo.getGrossWeight() == null ? 0
									: commInfo.getGrossWeight();//毛重
							obj1.setCommTotalPrice(totalPrice);
							obj1.setNetWeight(netWeight);
							obj1.setPieces(String.valueOf(pieces));
							obj1.setGrossWeight(grossWeight);
							listBootom.add(obj1);
							listKey.add(key);
						}
						commInfo.setAddType("是");
						commInfo.setIsSelected(false);
					}
					tableTop.updateRows(selectList);
					tableBottom.addRows(listBootom);
				}
			});
		}
		return btnMerger;
	}

	/**
	 * This method initializes btnMove
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnMove() {
		if (btnMove == null) {
			btnMove = new JButton();
			btnMove.setBounds(new Rectangle(327, 5, 87, 23));
			btnMove.setText("全部下移");
			btnMove.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableTop == null
							|| tableTop.getList().size() <= 0) {
						JOptionPane.showMessageDialog(
								DgIEMergerZaiHuoListReportSet.this, "没有可选择的行",
								"提示", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					List list = new ArrayList();
					for (int i = 0; i < tableTop.getList().size(); i++) {
						BaseCustomsDeclarationCommInfo obj = (BaseCustomsDeclarationCommInfo) tableTop
								.getList().get(i);
						TempIEMergerZaihouList obj1 = new TempIEMergerZaihouList();
						obj1.setComplexCode(obj.getComplex() == null ? "" : obj
								.getComplex().getCode());
						obj1.setCommName(obj.getCommName());
						obj1.setWrapTypeName(obj.getWrapType() == null ? ""
								: obj.getWrapType().getName());
						obj1.setCommTotalPrice(obj.getCommTotalPrice());
						obj1.setNetWeight(obj.getNetWeight());
						obj1.setPieces(obj.getPieces() == null ? "" : String
								.valueOf(obj.getPieces()));
						obj1.setGrossWeight(obj.getGrossWeight());
						obj.setIsSelected(true);
						obj.setAddType("是");
						tableTop.updateRow(obj);
						list.add(obj1);
					}
					initTableBootom(list);
				}
			});
		}
		return btnMove;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.setPreferredSize(new Dimension(150, 28));
		}
		return jPanel2;
	}

	/**
	 * This method initializes btnOk
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton();
			btnOk.setText("确定");
			btnOk.setPreferredSize(new Dimension(65, 30));
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableBottom == null
							|| tableBottom.getCurrentRow() == null) {
						return;
					}
					if (tableBottom.getList().size() > 6) {
						JOptionPane.showMessageDialog(
								DgIEMergerZaiHuoListReportSet.this,
								"合并的行不能超过6项", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}

					setReturnList(tableBottom.getList());
					setIsOk(true);
					dispose();
				}
			});
		}
		return btnOk;
	}

	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jLabel = new JLabel();
			jLabel
					.setText("说明：当合并项小于等于6项时就按“全部下移”，如果合并项超过6项需要按“合并”,合并时按相同前4码合并");
			jLabel.setBounds(new Rectangle(24, 5, 634, 18));
			jLabel.setForeground(Color.BLUE);
			jPanel3 = new JPanel();
			jPanel3.setLayout(null);
			jPanel3.setPreferredSize(new Dimension(1, 50));
			jPanel3.add(getCbbReworkTotalPrice(), null);
			jPanel3.add(jLabel1, null);
			jPanel3.add(getTfZuangHou(), null);
			jPanel3.add(jLabel2, null);
			jPanel3.add(getTfXieHou(), null);
			jPanel3.add(jLabel, null);
			jPanel3.add(getBtnSearch(), null);
			jPanel3.add(getCbbSelectAll(), null);
		}
		return jPanel3;
	}

	/**
	 * This method initializes tfZuangHou
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfZuangHou() {
		if (tfZuangHou == null) {
			tfZuangHou = new JTextField();
			tfZuangHou.setBounds(new Rectangle(69, 24, 142, 22));
		}
		return tfZuangHou;
	}

	/**
	 * This method initializes tfXieHou
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfXieHou() {
		if (tfXieHou == null) {
			tfXieHou = new JTextField();
			tfXieHou.setBounds(new Rectangle(266, 24, 100, 22));
		}
		return tfXieHou;
	}

	/**
	 * 编辑列
	 */
	class CheckBoxEditor extends DefaultCellEditor implements ActionListener {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private JCheckBox cb;
		private JTable table = null;

		public CheckBoxEditor(JCheckBox checkBox) {
			super(checkBox);
		}

		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {
			if (value == null) {
				return null;
			}
			if (Boolean.valueOf(value.toString()) instanceof Boolean) {
				cb = new JCheckBox();
				cb
						.setSelected(Boolean.valueOf(value.toString())
								.booleanValue());
			}
			cb.setHorizontalAlignment(JLabel.CENTER);
			cb.addActionListener(this);
			this.table = table;
			return cb;
		}

		public Object getCellEditorValue() {
			cb.removeActionListener(this);
			return cb;
		}

		public void actionPerformed(ActionEvent e) {
			JTableListModel tableModel = (JTableListModel) this.table
					.getModel();
			Object obj = tableModel.getCurrentRow();
			if (obj instanceof BaseCustomsDeclarationCommInfo) {
				BaseCustomsDeclarationCommInfo temp = (BaseCustomsDeclarationCommInfo) obj;
				temp.setIsSelected(new Boolean(cb.isSelected()));
				tableModel.updateRow(obj);
			}
			fireEditingStopped();
		}
	}

	/**
	 * render table JchcckBox 列
	 */
	class MultiRenderer extends DefaultTableCellRenderer {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		JCheckBox checkBox = new JCheckBox();

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			if (value == null) {
				return this;
			}
			if (Boolean.valueOf(value.toString()) instanceof Boolean) {
				checkBox.setSelected(Boolean.parseBoolean(value.toString()));
				checkBox.setHorizontalAlignment(JLabel.CENTER);
				checkBox.setBackground(table.getBackground());
				if (isSelected) {
					checkBox.setForeground(table.getSelectionForeground());
					checkBox.setBackground(table.getSelectionBackground());
				} else {
					checkBox.setForeground(table.getForeground());
					checkBox.setBackground(table.getBackground());
				}
				return checkBox;
			}
			String str = (value == null) ? "" : value.toString();
			return super.getTableCellRendererComponent(table, str, isSelected,
					hasFocus, row, column);
		}
	}

	/**
	 * This method initializes cbbReworkTotalPrice
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbbReworkTotalPrice() {
		if (cbbReworkTotalPrice == null) {
			cbbReworkTotalPrice = new JCheckBox();
			cbbReworkTotalPrice.setText("是否除去返工复出金额");
			cbbReworkTotalPrice.setBounds(new Rectangle(371, 25, 153, 21));
		}
		return cbbReworkTotalPrice;
	}

	/**
	 * This method initializes btnSearch
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSearch() {
		if (btnSearch == null) {
			btnSearch = new JButton();
			btnSearch.setBounds(new Rectangle(630, 25, 74, 20));
			btnSearch.setText("查询");
			btnSearch.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List list = getDataSource();
					initTableTOP(list);
					initTableBootom(new ArrayList());
				}
			});
		}
		return btnSearch;
	}

	/**
	 * This method initializes cbbSelectAll	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbbSelectAll() {
		if (cbbSelectAll == null) {
			cbbSelectAll = new JCheckBox();
			cbbSelectAll.setBounds(new Rectangle(533, 25, 60, 21));
			cbbSelectAll.setText("全选");
			cbbSelectAll.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List selectList = new ArrayList();
					for (int i = 0; i < tableTop.getList().size(); i++) {
						BaseCustomsDeclarationCommInfo obj = (BaseCustomsDeclarationCommInfo) tableTop
								.getList().get(i);
						obj.setIsSelected(cbbSelectAll.isSelected());
						selectList.add(obj);
					}
					tableTop.updateRows(selectList);
					System.out.println(tableTop.getList().size());
				}
			});
		}
		return cbbSelectAll;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
