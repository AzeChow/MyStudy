package com.bestway.bcs.client.verification;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutionException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import com.bestway.bcs.client.verification.contractanalyse.FmVFContractAnalyse;
import com.bestway.bcs.client.verification.factoryanalyse.FmVFStockAnalyse;
import com.bestway.bcs.client.verification.transferanalyse.FmVFTransferDiffCount;
import com.bestway.bcs.verification.action.VFVerificationAction;
import com.bestway.bcs.verification.action.VFVerificationAuthority;
import com.bestway.bcs.verification.entity.VFAnalyse;
import com.bestway.bcs.verification.entity.VFSection;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.tableeditor.JNumberTableCellEditor;
import com.bestway.bcus.client.common.tableeditor.TableCellEditorListener;
import com.bestway.bcus.client.common.tableeditor.TableCellEditorParameter;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.groupableheader.ColumnGroup;
import com.bestway.client.util.groupableheader.GroupableHeaderTable;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.client.windows.control.ShowFormControl;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.jptds.client.common.CustomBaseComboBoxEditor;
import com.bestway.jptds.client.common.CustomBaseRender;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

public class FmVFAnalyse extends JInternalFrameBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private VFVerificationAction vfVerificationAction;

	private JTableListModel tableModel;

	private JTableListModelAdapter adapter;

	public Request request;

	private JTable table;

	private JLabel lbBatchSelect;

	/**
	 * 批次时间选择
	 */
	private JComboBox cbbSelectVFSection;

	/**
	 * 折算报关数量
	 */
	private JButton btnQuery;

	/**
	 * 查看历史
	 */
	private JButton btnViewHistory;

	/**
	 * 导出Excel
	 */
	private JButton btnExportExcel;

	/**
	 * 清空当前数据
	 */
	private JButton btnemptyCurrentData;

	private JToolBar tbMenu;

	private JScrollPane scrollPane;

	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	private JLabel lblNewLabel;
	private JTextField tfseqNum;
	private List<TableColumn> linkedCols = new ArrayList<TableColumn>();

	public FmVFAnalyse() {
		this.setTitle("短溢分析");

		request = new Request(CommonVars.getCurrUser());

		vfVerificationAction = (VFVerificationAction) CommonVars
				.getApplicationContext().getBean("vfVerificationAction");
		VFVerificationAuthority authority = (VFVerificationAuthority) CommonVars
				.getApplicationContext().getBean("vfVerificationAuthority");
		authority.checkAnalyse(request);
		initialize();
	}

	/**
	 * 初始化窗体
	 */
	public void initialize() {

		getContentPane().add(getTbMenu(), BorderLayout.NORTH);
		getContentPane().add(getScrollpane(), BorderLayout.CENTER);

		initTable(new ArrayList<Object>());
		initCbbSelectTime();
	}

	private JScrollPane getScrollpane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTable());
		}
		return scrollPane;
	}

	private JTable getTable() {

		if (table == null) {

			table = new GroupableHeaderTable();

			table.addMouseListener(new MouseAdapter() {

				public void mouseClicked(MouseEvent e) {

					if (e.getClickCount() == 2) {

						int colIdx = table.columnAtPoint(e.getPoint());

						if (colIdx < 0)

							return;

						int idx = CommonUtils.indexOf(linkedCols, table
								.getColumnModel().getColumn(colIdx));

						if (idx < 0)

							return;

						VFAnalyse s = (VFAnalyse) tableModel.getCurrentRow();

						VFSection section = s.getSection();

						section.setEndDate(Date.valueOf(CommonUtils.getDate(
								section.getEndDate(), "yyyy-MM-dd")));

						if (idx == 0) {

							FmVFContractAnalyse fm = new FmVFContractAnalyse();

							fm.showData(section, s.getMergerNo());

							ShowFormControl.refreshInteralForm(fm);

						} else if (idx == 1) {

							FmVFStockAnalyse fm = new FmVFStockAnalyse();

							fm.showData(section, s.getMergerNo());

							ShowFormControl.refreshInteralForm(fm);

						} else if (idx == 2) {

							FmVFTransferDiffCount fm = new FmVFTransferDiffCount();

							fm.showData(section, s.getMergerNo());

							ShowFormControl.refreshInteralForm(fm);
						}
					}
				}
			});
		}
		return table;
	}

	/**
	 * 菜单控件
	 */
	private JToolBar getTbMenu() {
		if (tbMenu == null) {
			tbMenu = new JToolBar();
			// 设置tbMenu布局
			FlowLayout f = new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(0);
			f.setHgap(0);
			tbMenu.setLayout(f);
			tbMenu.add(getLbBatchSelect());
			tbMenu.add(getCbbSelectVFSection());
			tbMenu.add(getLblNewLabel());
			tbMenu.add(getTfSection());
			tbMenu.add(getBtnViewHistory());
			tbMenu.add(getBtnQuery());
			tbMenu.add(getBtnExportExcel());
			tbMenu.add(btnemptyCurrentData());
		}
		return tbMenu;
	}

	private JLabel getLbBatchSelect() {
		if (lbBatchSelect == null) {
			lbBatchSelect = new JLabel("批次选择:");
		}
		return lbBatchSelect;
	}

	private JComboBox getCbbSelectVFSection() {
		if (cbbSelectVFSection == null) {
			cbbSelectVFSection = new JComboBox();
			cbbSelectVFSection.setPreferredSize(new Dimension(120, 27));
			cbbSelectVFSection.setEditable(true);
		}
		return cbbSelectVFSection;
	}

	private JButton getBtnViewHistory() {
		if (btnViewHistory == null) {
			btnViewHistory = new JButton("查询批次数据");
			btnViewHistory.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (getVFSection() == null) {
						JOptionPane.showMessageDialog(FmVFAnalyse.this,
								"请选择需要查询的批次!");
						return;
					}
					String seqNum = tfseqNum.getText();
					List<VFAnalyse> listVFAnalyses = vfVerificationAction
							.findVFAnalyses(request, getVFSection(),
									seqNum == null || "".equals(seqNum) ? null
											: Integer.parseInt(seqNum));
					initTable(listVFAnalyses);
				}
			});
		}
		return btnViewHistory;
	}

	private JButton getBtnQuery() {
		if (btnQuery == null) {
			btnQuery = new JButton("短溢分析");
			btnQuery.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (getVFSection() == null) {
						JOptionPane.showMessageDialog(FmVFAnalyse.this,
								"请选择需要查询的批次!");
						return;
					}
					new verificationAnalyses().execute();
				}
			});
		}
		return btnQuery;
	}

	public class verificationAnalyses extends SwingWorker {

		public long startTime = 0;

		@Override
		protected Object doInBackground() throws Exception {
			CommonProgress.showProgressDialog(FmVFAnalyse.this);
			CommonProgress.setMessage("系统正在分析，请稍后...");
			List<VFAnalyse> listVFAnalyse = null;
			disableControls();
			try {
				listVFAnalyse = vfVerificationAction.verificationAnalyses(
						request, getVFSection());
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
			} finally {
				CommonProgress.closeProgressDialog();
			}
			return listVFAnalyse;
		}

		@Override
		protected void done() {
			List<VFAnalyse> listVFAnalyse = null;
			try {
				listVFAnalyse = (List<VFAnalyse>) get();
				initTable(listVFAnalyse);
			} catch (InterruptedException e) {
				JOptionPane.showMessageDialog(FmVFAnalyse.this, e.getMessage());
			} catch (ExecutionException e) {
				JOptionPane.showMessageDialog(FmVFAnalyse.this, e.getMessage());
			} finally {
				enableControls();
			}
		}
	}

	private JButton getBtnExportExcel() {
		if (btnExportExcel == null) {
			btnExportExcel = new JButton("导出Excel");
			btnExportExcel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (getVFSection() == null) {
						JOptionPane.showMessageDialog(FmVFAnalyse.this,
								"请选择需要查询的批次!");
						return;
					}
					tableModel.getMiSaveAllToExcel().doClick();
				}
			});
		}
		return btnExportExcel;
	}

	private JButton btnemptyCurrentData() {
		if (btnemptyCurrentData == null) {
			btnemptyCurrentData = new JButton("清空当前数据");
			btnemptyCurrentData.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (getVFSection() == null) {
						JOptionPane.showMessageDialog(FmVFAnalyse.this,
								"请选择需要查询的批次!");
						return;
					}
					if (JOptionPane.YES_OPTION == JOptionPane.showOptionDialog(
							FmVFAnalyse.this, "确定要清空本次批次的数据吗?", "提示",
							JOptionPane.OK_OPTION,
							JOptionPane.INFORMATION_MESSAGE, null,
							new Object[] { "是", "否" }, "否")) {
						vfVerificationAction.deleteVFAnalyse(request,
								getVFSection());
						initTable(new ArrayList<Object>());
					} else {
						return;
					}
				}
			});
		}
		return btnemptyCurrentData;
	}

	/**
	 * 初始化Jtable
	 * 
	 * @param list
	 */
	public void initTable(List list) {
		final String[] linkedColNames = new String[] { "合同应剩余(C=A-B)",
				"库存数量(K=D+E+F+G+H+I+J+U+V+W)", "结转差额(P=L+M-N-O)" };

		VFSection section = (VFSection) cbbSelectVFSection.getSelectedItem();
		if (null == section) {
			section = (VFSection) vfVerificationAction.findMaxBuyIsCount(
					new Request(CommonVars.getCurrUser())).get(0);
		}

		final List buyIsCountList = vfVerificationAction.findBuyIsCount(
				new Request(CommonVars.getCurrUser()), section.getId());

		adapter = new JTableListModelAdapter() {

			public List<JTableListColumn> InitColumns() {

				List<JTableListColumn> list = new Vector<JTableListColumn>();

				list.add(addColumn("归并序号  ", "mergerNo", 80));

				list.add(addColumn("商品编码  ", "complex", 80));

				list.add(addColumn("名称", "hsName", 100));

				list.add(addColumn("规格", "hsSpec", 100));

				list.add(addColumn("单位", "hsUnit", 40));

				list.add(addColumn("进口数量(A)", "impAmount", 100));

				list.add(addColumn("出口总耗用(B)", "expAmount", 100));

				list.add(addColumn(linkedColNames[0], "contractLeaveNum", 120));

				list.add(addColumn("料件库存(D)", "stockAmountImg", 80));

				list.add(addColumn("在产品库存(E)", "stockAmountProcessImg", 80));

				list.add(addColumn("成品库存(F)", "stockAmountExg", 80));

				list.add(addColumn("外发库存(G)", "stockAmountOutSend", 80));

				list.add(addColumn("厂外存放库存(H)", "stockAmountOutFactory", 120));

				list.add(addColumn("内购库存(I)", "stockAmountBuy", 80));

				list.add(addColumn("在途库存(J)", "stockAmountTraveling", 80));

				list.add(addColumn("半成品库存(已入库)(U)",
						"stockAmountSemiExgHadStore", 125));

				list.add(addColumn("在制品库存(V)", "stockAmountFinishing", 90));

				list.add(addColumn("残次品库存(W)", "stockAmountBad", 90));

				list.add(addColumn(linkedColNames[1], "stockTotalAmount", 240));

				list.add(addColumn("已转厂未收货(L)", "unReceiveHadTransNum", 110));

				list.add(addColumn("已送货未转厂(M)", "unTransHadSendNum", 110));

				list.add(addColumn("已转厂未送货(N)", "unSendHadTransNum", 110));

				list.add(addColumn("已收货未转厂(O)", "unTransHadReceiveNum", 110));

				list.add(addColumn(linkedColNames[2], "transferDiffNum", 120));

				list.add(addColumn("短溢数量(Q=K-C+10)", "overOrshortNum", 120));

				list.add(addColumn("单价(R)", "price", 80));

				list.add(addColumn("关税(S=R*Q*税率)", "usd", 120));

				list.add(addColumn("增值税(T=(R*Q+S)*17%)", "usdAdd", 150));

				/**
				 * 这里是关联内购库存的栏位
				 */
				Boolean flag = null == buyIsCountList.get(0) ? false
						: (Boolean) buyIsCountList.get(0);

				if (!flag) {

					list.remove(13);

				}

				return list;
			}
		};

		// showTableModel = new JTableListModel(table, list, adapter);
		tableModel = new JTableListModel((GroupableHeaderTable) table, list,
				adapter);

		// 【合同数据分析】：进口数量、出口总耗用、合同应剩余
		// 【盘点数据分析】：料件库存、在产品库存、成品库存、外发库存、厂外存放库存、内购库存、在途库存、库存数量
		// 【结转数据分析】：已转厂未收货、已送货未转厂、已转厂未送货、结转差额
		this.linkedCols.clear();

		TableColumnModel cm = table.getColumnModel();

		ColumnGroup htGroup = new ColumnGroup("【合同数据分析】");

		htGroup.add(cm.getColumn(5));

		htGroup.add(cm.getColumn(6));

		htGroup.add(cm.getColumn(7));

		htGroup.add(cm.getColumn(8));

		this.linkedCols.add(cm.getColumn(8));

		setColumnToolTip(cm.getColumn(8), "双击‘" + linkedColNames[0]
				+ "’可关联【合同分析表】");

		Boolean flag = null == buyIsCountList.get(0) ? false
				: (Boolean) buyIsCountList.get(0);

		ColumnGroup pdGroup = new ColumnGroup("【盘点数据分析】");

		ColumnGroup jzGroup = new ColumnGroup("【结转数据分析】");

		// 不参与内购的
		if (!flag) {

			pdGroup.add(cm.getColumn(9));

			pdGroup.add(cm.getColumn(10));

			pdGroup.add(cm.getColumn(11));

			pdGroup.add(cm.getColumn(12));

			pdGroup.add(cm.getColumn(13));

			pdGroup.add(cm.getColumn(14));

			pdGroup.add(cm.getColumn(15));

			pdGroup.add(cm.getColumn(16));

			pdGroup.add(cm.getColumn(17));

			pdGroup.add(cm.getColumn(18));

			this.linkedCols.add(cm.getColumn(18));

			setColumnToolTip(cm.getColumn(18), "双击‘" + linkedColNames[1]
					+ "’可关联【工厂库存汇总表】");

			jzGroup.add(cm.getColumn(19));

			jzGroup.add(cm.getColumn(20));

			jzGroup.add(cm.getColumn(21));

			jzGroup.add(cm.getColumn(22));

			jzGroup.add(cm.getColumn(23));

			this.linkedCols.add(cm.getColumn(23));

			setColumnToolTip(cm.getColumn(23), "双击‘" + linkedColNames[2]
					+ "’可关联【结转差额汇总表】");

			// 参与内购的
		} else {

			pdGroup.add(cm.getColumn(9));

			pdGroup.add(cm.getColumn(10));

			pdGroup.add(cm.getColumn(11));

			pdGroup.add(cm.getColumn(12));

			pdGroup.add(cm.getColumn(13));

			pdGroup.add(cm.getColumn(14));

			pdGroup.add(cm.getColumn(15));

			pdGroup.add(cm.getColumn(16));

			pdGroup.add(cm.getColumn(17));

			pdGroup.add(cm.getColumn(18));

			pdGroup.add(cm.getColumn(19));

			this.linkedCols.add(cm.getColumn(19));

			setColumnToolTip(cm.getColumn(19), "双击‘" + linkedColNames[1]
					+ "’可关联【工厂库存汇总表】");

			jzGroup.add(cm.getColumn(20));

			jzGroup.add(cm.getColumn(21));

			jzGroup.add(cm.getColumn(22));

			jzGroup.add(cm.getColumn(23));

			jzGroup.add(cm.getColumn(24));

			this.linkedCols.add(cm.getColumn(24));

			setColumnToolTip(cm.getColumn(24), "双击‘" + linkedColNames[2]
					+ "’可关联【结转差额汇总表】");

		}

		GroupableTableHeader header = (GroupableTableHeader) table
				.getTableHeader();

		header.addColumnGroup(htGroup);

		header.addColumnGroup(pdGroup);

		header.addColumnGroup(jzGroup);

		if (!flag) {

			// 设置可编辑
			adapter.setEditableColumn(25);

		} else {

			adapter.setEditableColumn(26);

		}

		tableModel.setAllowSetValue(true);

		JNumberTableCellEditor editor = new JNumberTableCellEditor(9);

		editor.addPersistListener(new TableCellEditorListener() {
			@Override
			public void run(TableCellEditorParameter parm) {
				VFAnalyse a = (VFAnalyse) parm.getEditingData();
				a = vfVerificationAction.updateAnalysePrice(a);
				tableModel.updateRow(a);
			}
		});

		if (!flag) {

			table.getColumnModel().getColumn(25).setCellEditor(editor); // 设置第5列编辑控件

		} else {

			table.getColumnModel().getColumn(26).setCellEditor(editor); // 设置第5列编辑控件

		}
	}

	private void setColumnToolTip(TableColumn column, final String tipText) {
		column.setCellRenderer(new DefaultTableCellRenderer() {
			public String getToolTipText() {
				return tipText;
			}
		});
	}

	/**
	 * 初始化批次选择的下拉列表
	 */
	public void initCbbSelectTime() {

		// 格式化时间
		SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd");
		List<VFSection> dataSource = vfVerificationAction
				.findVFSectionList(new Request(CommonVars.getCurrUser()));
		List<VFSection> manulist = new ArrayList<VFSection>();
		for (int i = 0; i < dataSource.size(); i++) {
			VFSection vfSection = (VFSection) dataSource.get(i);
			vfSection.setEndDate(Date.valueOf(DateFormat.format(vfSection
					.getEndDate())));
			manulist.add(vfSection);
		}
		DefaultComboBoxModel DfComboBox = new DefaultComboBoxModel(
				manulist.toArray());
		this.cbbSelectVFSection.setModel(DfComboBox);
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				cbbSelectVFSection, "verificationNo", "endDate", 150);
		this.cbbSelectVFSection.setRenderer(CustomBaseRender.getInstance()
				.getRender("verificationNo", "endDate", 40, 100));
		// this.cbbSelectVFSection.setSelectedItem(null);
	}

	/**
	 * 获得批次号,当批次号为空时返回null,否则返回批次号
	 * 
	 * @return
	 */
	public VFSection getVFSection() {
		VFSection section = (VFSection) cbbSelectVFSection.getSelectedItem();
		return section;
	}

	private void disableControls() {
		cbbSelectVFSection.setEnabled(false);
		btnViewHistory.setEnabled(false);
		btnExportExcel.setEnabled(false);
		btnemptyCurrentData.setEnabled(false);
	}

	private void enableControls() {
		cbbSelectVFSection.setEnabled(true);
		btnViewHistory.setEnabled(true);
		btnExportExcel.setEnabled(true);
		btnemptyCurrentData.setEnabled(true);
	}

	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("归并序号:");
		}
		return lblNewLabel;
	}

	private JTextField getTfSection() {
		if (tfseqNum == null) {
			tfseqNum = new JTextField();
			tfseqNum.setPreferredSize(new Dimension(6, 27));
			tfseqNum.setColumns(10);
			tfseqNum.setDocument(new PlainDocument() {
				public void insertString(int offs, String str, AttributeSet a)
						throws BadLocationException {
					char c = str.charAt(str.length() - 1);
					if (c >= '0' && c <= '9') {
						super.insertString(offs, str, a);
					}
				}
			});
		}
		return tfseqNum;
	}

	/**
	 * 查询数据并显示与界面
	 * 
	 * @param section
	 * @param mergerNo
	 */
	public void showData(VFSection section) {
		// this.cbbSelectVFSection.setSelectedItem(section);
		int count = cbbSelectVFSection.getItemCount();
		for (int i = 0; i < count; i++) {
			Object obj = cbbSelectVFSection.getItemAt(i);
			if (obj instanceof VFSection) {
				VFSection vfsection = (VFSection) obj;
				if (vfsection.getId().equals(section.getId())) {
					cbbSelectVFSection.setSelectedIndex(i);
				}
			}
		}
		btnViewHistory.doClick();
	}
}
