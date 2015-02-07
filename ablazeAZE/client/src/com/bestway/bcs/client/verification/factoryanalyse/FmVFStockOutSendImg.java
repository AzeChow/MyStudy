package com.bestway.bcs.client.verification.factoryanalyse;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.table.TableColumnModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import com.bestway.bcs.client.verification.DgVFBaseStockExgOrImgEdit;
import com.bestway.bcs.verification.action.VFVerificationAction;
import com.bestway.bcs.verification.action.VFVerificationAuthority;
import com.bestway.bcs.verification.entity.VFBaseStockImg;
import com.bestway.bcs.verification.entity.VFSection;
import com.bestway.bcs.verification.entity.VFStockOutSendImg;
import com.bestway.bcs.verification.entity.VFStockTravelingImg;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.groupableheader.ColumnGroup;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.client.util.mutispan.AttributiveCellTableModel;
import com.bestway.client.util.mutispan.MultiSpanCellTable;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.jptds.client.common.CustomBaseComboBoxEditor;
import com.bestway.jptds.client.common.CustomBaseRender;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

public class FmVFStockOutSendImg extends JInternalFrameBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private VFVerificationAction vfVerificationAction;

	private AttributiveCellTableModel showTableModel;

	private JTableListModelAdapter adapter;

	public Request request;
	/**
	 * 存放工厂盘点数据表的JTable
	 */
	private JTable table;

	private JLabel lbBatchSelect;

	/**
	 * 批次时间选择
	 */
	private JComboBox cbbSelectVFSection;

	/**
	 * 导入盘点数据
	 */
	private JButton btnImpInventoryDate;

	/**
	 * 折算报关数量
	 */
	private JButton btnReducedClearanceNumber;

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
	private JTextField tfseqNum;
	private JLabel lblNewLabel;
	private JButton btnEdit;
	private JButton btnDelete;

	public FmVFStockOutSendImg() {
		this.setTitle("原材料");
		request = new Request(CommonVars.getCurrUser());
		vfVerificationAction = (VFVerificationAction) CommonVars.getApplicationContext()
				.getBean("vfVerificationAction");
		VFVerificationAuthority authority = (VFVerificationAuthority)CommonVars.getApplicationContext().getBean("vfVerificationAuthority");		
		authority.checkStockOutSendImg(new Request(CommonVars.getCurrUser()));
		initialize();
	}

	/**
	 * 初始化窗体
	 */
	public void initialize() {

		getContentPane().add(getTbMenu(), BorderLayout.NORTH);
		getContentPane().add(getScrollPane(), BorderLayout.CENTER);

		initJtable(new ArrayList<Object>());
		initCbbSelectTime();
	}

	public JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTable());
		}
		return scrollPane;
	}

	public JTable getTable() {
		if (table == null) {
			table = new MultiSpanCellTable();
		}
		return table;
	}

	public JToolBar getTbMenu() {
		if (tbMenu == null) {
			tbMenu = new JToolBar();
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
			tbMenu.add(getBtnImpInventoryDate());
			tbMenu.add(getBtnReducedClearanceNumber());
			tbMenu.add(getBtnEdit());
			tbMenu.add(getBtnDelete());
			tbMenu.add(getBtnExportExcel());
			tbMenu.add(getBtnemptyCurrentData());
		}
		return tbMenu;
	}

	public JLabel getLbBatchSelect() {
		if (lbBatchSelect == null) {
			lbBatchSelect = new JLabel("批次选择:");
		}
		return lbBatchSelect;
	}

	private JButton getBtnImpInventoryDate() {
		if (btnImpInventoryDate == null) {
			btnImpInventoryDate = new JButton(" 1.导入盘点数据");
			btnImpInventoryDate.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (getVFSection() == null) {
						JOptionPane.showMessageDialog(FmVFStockOutSendImg.this, "请先选择批次!");
						return;
					}
					final Request req = new Request(CommonVars.getCurrUser());
					if(vfVerificationAction.isExistsBySection(req, getVFSection(), VFStockOutSendImg.class)){						
						if(JOptionPane.YES_OPTION != JOptionPane.showOptionDialog(getParent(), "已存在本批次数据，确定删除本批次数据，重新导入?","提示", JOptionPane.YES_NO_OPTION, 
								JOptionPane.INFORMATION_MESSAGE,null, new Object[]{"是","否"},"否")){
							return;
						}
						vfVerificationAction.deleteVFStockOutSendImgs(request, getVFSection());
						initJtable(new ArrayList<Object>());
					}
					DgImportDataImg dgImportData = new DgImportDataImg();
					dgImportData.setSection(getVFSection());
					dgImportData.setVFBaseStockImg(VFStockOutSendImg.class);
					dgImportData.setVisible(true);
					FmVFStockOutSendImg.this.queryVFSectiong();
				}
			});
		}
		return btnImpInventoryDate;
	}

	private JButton getBtnReducedClearanceNumber() {
		if (btnReducedClearanceNumber == null) {
			btnReducedClearanceNumber = new JButton(" 2.折算报关数量");
			btnReducedClearanceNumber.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (getVFSection() == null) {
						JOptionPane.showMessageDialog(FmVFStockOutSendImg.this, "请先选择批次!");
						return;
					}
					new convertVFStockImg().execute();
				}
			});
		}
		return btnReducedClearanceNumber;
	}

	private JButton getBtnViewHistory() {
		if (btnViewHistory == null) {
			btnViewHistory = new JButton(" 查询批次数据");
			btnViewHistory.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					queryVFSectiong();
				}
			});
		}
		return btnViewHistory;
	}

	private JButton getBtnExportExcel() {
		if (btnExportExcel == null) {
			btnExportExcel = new JButton(" 导出Excel");
			btnExportExcel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (getVFSection() == null) {
						JOptionPane.showMessageDialog(FmVFStockOutSendImg.this, "请先选择批次!");
						return;
					}
					disableControls();
					showTableModel.getMiSaveTableListToExcel().doClick();
					enableControls();
				}
			});
		}
		return btnExportExcel;
	}

	private JButton getBtnemptyCurrentData() {
		if (btnemptyCurrentData == null) {
			btnemptyCurrentData = new JButton(" 清空当前数据");
			btnemptyCurrentData.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (getVFSection() == null) {
						JOptionPane.showMessageDialog(FmVFStockOutSendImg.this, "请先选择批次!");
						return;
					}

					if (JOptionPane.OK_OPTION != JOptionPane.showOptionDialog(FmVFStockOutSendImg.this, "确定要清空本次批次的数据吗?",
							"提示", JOptionPane.OK_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] { "是",
									"否" }, "否")) {
						return;
					} else {
						disableControls();
						vfVerificationAction.deleteVFStockOutSendImgs(request, getVFSection());
						initJtable(new ArrayList<Object>());
						enableControls();
					}
				}
			});
		}
		return btnemptyCurrentData;
	}

	private JComboBox getCbbSelectVFSection() {
		if (cbbSelectVFSection == null) {
			cbbSelectVFSection = new JComboBox();
			cbbSelectVFSection.setPreferredSize(new Dimension(120, 27));
			cbbSelectVFSection.setEditable(true);
		}
		return cbbSelectVFSection;
	}

	/**
	 * 初始化Jtable
	 * 
	 * @param list
	 */
	public void initJtable(List list) {
		adapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("工厂料号", "ptNo", 80));
				list.add(addColumn("工厂名称", "ptName", 100));
				list.add(addColumn("工厂规格", "ptSpec", 100));
				list.add(addColumn("工厂单位", "ptUnit", 60));
				list.add(addColumn("库存数量", "storeAmount", 80));
				list.add(addColumn("归并序号", "mergerNo", 80));
				list.add(addColumn("报关商品名称", "hsName", 100));
				list.add(addColumn("报关商品规格", "hsSpec", 100));
				list.add(addColumn("计量单位", "hsUnit", 60));
				list.add(addColumn("折算报关数量", "hsAmount", 80));
				list.add(addColumn("折算系数", "unitConvert", 80));
				
				list.add(addColumn("仓库", "warehouse", 100));
				list.add(addColumn("备注", "memo", 100));
				return list;
			}
		};
		showTableModel = new AttributiveCellTableModel((MultiSpanCellTable) table, list, adapter);

		TableColumnModel cm = table.getColumnModel();
		ColumnGroup exgGroup = new ColumnGroup("【工厂资料】");
		exgGroup.add(cm.getColumn(1));
		exgGroup.add(cm.getColumn(2));
		exgGroup.add(cm.getColumn(3));
		exgGroup.add(cm.getColumn(4));
		exgGroup.add(cm.getColumn(5));
		ColumnGroup ptImpGroup = new ColumnGroup("【报关资料】");
		ptImpGroup.add(cm.getColumn(6));
		ptImpGroup.add(cm.getColumn(7));
		ptImpGroup.add(cm.getColumn(8));
		ptImpGroup.add(cm.getColumn(9));
		ptImpGroup.add(cm.getColumn(10));

		GroupableTableHeader header = (GroupableTableHeader) table.getTableHeader();
		header.addColumnGroup(exgGroup);
		header.addColumnGroup(ptImpGroup);
	}

	/**
	 * 初始化批次选择的下拉列表
	 */
	public void initCbbSelectTime() {

		// 格式化时间
		SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd");
		List<VFSection> dataSource = vfVerificationAction.findVFSectionList(request);
		List<VFSection> manulist = new ArrayList<VFSection>();
		for (int i = 0; i < dataSource.size(); i++) {
			VFSection vfSection = (VFSection) dataSource.get(i);
			vfSection.setEndDate(Date.valueOf(DateFormat.format(vfSection.getEndDate())));
			manulist.add(vfSection);
		}
		DefaultComboBoxModel DfComboBox = new DefaultComboBoxModel(manulist.toArray());
		this.cbbSelectVFSection.setModel(DfComboBox);
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbSelectVFSection, "verificationNo", "endDate", 150);
		this.cbbSelectVFSection.setRenderer(CustomBaseRender.getInstance().getRender("verificationNo", "endDate", 40,
				100));
		this.cbbSelectVFSection.setSelectedItem(null);
	}

	public class convertVFStockImg extends SwingWorker {

		@Override
		protected Object doInBackground() throws Exception {
			CommonProgress.showProgressDialog(FmVFStockOutSendImg.this);
			CommonProgress.setMessage("系统正在初始化数据，请稍后...");
			String logg;
			List<VFStockOutSendImg> ListImgConvert = new ArrayList<VFStockOutSendImg>();
			try {
				String table = "VFStockOutSendImg";
				logg = vfVerificationAction.convertImgConverts(request,
						getVFSection(), table);
				if (logg != null && !logg.isEmpty()) {
					String[] ptNos = logg.toString().split(",");
					List<ErrorMessage> errs = new ArrayList<ErrorMessage>();
					for (int i = 0; i < ptNos.length; i++) {
						errs.add(new ErrorMessage(ptNos[i],
								"在【物料与报关对应表】中不存在,或找不到对应关系"));
					}
					DgErrorMessage.show(errs);
					String seqNum = tfseqNum.getText().trim();
					ListImgConvert = vfVerificationAction.findVFStockProcessImgsoutsend(request,getVFSection(),(seqNum == null || "".equals(seqNum)) ? null
											: Integer.parseInt(seqNum));
				}
				CommonProgress.setMessage("系统正在获取文件数据，请稍后...");
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
			} finally {
				CommonProgress.closeProgressDialog();
			}
			return ListImgConvert;
		}

		@Override
		protected void done() {
			List<VFStockOutSendImg> vFStockOutSendImg;
			try {
				vFStockOutSendImg = (List<VFStockOutSendImg>) get();
				initJtable(vFStockOutSendImg);
			} catch (InterruptedException e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmVFStockOutSendImg.this, e.getMessage());
			} catch (ExecutionException e) {
				JOptionPane.showMessageDialog(FmVFStockOutSendImg.this, e.getMessage());
				e.printStackTrace();
			} finally {
				enableControls();
				CommonProgress.closeProgressDialog();
			}
		}
	}

	/**
	 * 查看批次
	 */
	public void queryVFSectiong() {

		if (getVFSection() == null) {
			JOptionPane.showMessageDialog(FmVFStockOutSendImg.this, "请先选择批次!");
			return;
		}
		disableControls();
		String seqNum = tfseqNum.getText().trim();		
		List<VFStockOutSendImg> listVFStockImg = vfVerificationAction.findVFStockOutSendImgs(request, getVFSection(), ("".equals(seqNum) ?null:Integer.parseInt(seqNum)));
		initJtable(listVFStockImg);
		enableControls();
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

	public void disableControls() {
		cbbSelectVFSection.setEnabled(false);
		btnImpInventoryDate.setEnabled(false);
		btnReducedClearanceNumber.setEnabled(false);
		btnViewHistory.setEnabled(false);
		btnExportExcel.setEnabled(false);
		btnemptyCurrentData.setEnabled(false);
	}

	public void enableControls() {
		cbbSelectVFSection.setEnabled(true);
		btnImpInventoryDate.setEnabled(true);
		btnReducedClearanceNumber.setEnabled(true);
		btnViewHistory.setEnabled(true);
		btnExportExcel.setEnabled(true);
		btnemptyCurrentData.setEnabled(true);
	}

	private JTextField getTfSection() {
		if (tfseqNum == null) {
			tfseqNum = new JTextField();
			tfseqNum.setPreferredSize(new Dimension(6, 27));
			tfseqNum.setColumns(10);
			tfseqNum.setDocument(new PlainDocument (){
				  public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {							             
					  char c = str.charAt(str.length()-1);
					  if(c >='0' && c <='9'){
						  super.insertString(offs, str, a);
					  }
			      }

			});
		}
		return tfseqNum;
	}
	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("归并序号:");
		}
		return lblNewLabel;
	}
	/**
	 * 穿透查询
	 * @param section
	 * @param mergerNo
	 */
	public void showData(VFSection section, Integer mergerNo) {
//		cbbSelectVFSection.setSelectedItem(section);
		int count = cbbSelectVFSection.getItemCount();
		for (int i = 0; i < count; i++) {
			Object obj = cbbSelectVFSection.getItemAt(i);
			if(obj instanceof VFSection){
				VFSection vfsection = (VFSection)obj;
				if(vfsection.getId().equals(section.getId())){
					cbbSelectVFSection.setSelectedIndex(i);
				}
			}
		}
		if(mergerNo != null)
			tfseqNum.setText(String.valueOf(mergerNo));
		btnViewHistory.doClick();
	}
	
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton("修改");
			btnEdit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					DgVFBaseStockExgOrImgEdit edit = new DgVFBaseStockExgOrImgEdit();
					VFBaseStockImg baseStockImg = (VFBaseStockImg)showTableModel.getCurrentRow();
					if(baseStockImg==null){
						return;
					}
					edit.setBaseStockImg(baseStockImg);
					edit.setMaterielType(MaterielType.MATERIEL);
					edit.setVisible(true);
					if(edit.isOk){
						showTableModel.updateRow(baseStockImg);
					}
				}
			});
		}
		return btnEdit;
	}
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton("删除");
			btnDelete.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					List<VFBaseStockImg> baseStockImgs = (List<VFBaseStockImg>)showTableModel.getCurrentRows();
					if(baseStockImgs==null){
						return;
					}
					
					if (JOptionPane.OK_OPTION != JOptionPane.showOptionDialog(FmVFStockOutSendImg.this, "确定要删除所选择的数据吗?",
							"提示", JOptionPane.OK_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] { "是",
									"否" }, "否")) {
						return;
					} else {
						vfVerificationAction.deleteVFBaseStockImgs(request, baseStockImgs);
						showTableModel.deleteRows(baseStockImgs);
					}
				}
			});
		}
		return btnDelete;
	}
}
