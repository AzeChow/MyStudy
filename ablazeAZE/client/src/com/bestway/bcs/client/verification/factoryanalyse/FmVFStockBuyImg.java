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
import com.bestway.bcs.verification.entity.VFStockAnalyse;
import com.bestway.bcs.verification.entity.VFStockBuyImg;
import com.bestway.bcs.verification.entity.VFStockOutFactoryImg;
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

public class FmVFStockBuyImg extends JInternalFrameBase {

  
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JToolBar jtoolBar;
	private JTable table;
	private JComboBox comboBox;
	/**
	 * 导入
	 */
	private JButton btnImport;
	/**
	 * 历史
	 */
	private JButton btnHistroy;
	/**
	 * 折算
	 */
	private JButton btnSwitch;
	/**
	 * 导出
	 */
	private JButton btnExport;
	/**
	 * 清除
	 */
	private JButton btnClean;
	private VFVerificationAction vfAction;
//	private JTableListModel tableMode;
	private AttributiveCellTableModel tableMode;
	private VFSection vfSection;
	private Request request;
	private JTextField tfseqNum;
	private JLabel lblNewLabel;
	private VFVerificationAction vfVerificationAction;
	private JButton btnEdit;
	private JButton btnDelete;
	/**
	 * Create the frame.
	 */
	public FmVFStockBuyImg() {
		this.setTitle("内购库存表");
		request = new Request(CommonVars.getCurrUser());
		vfAction = (VFVerificationAction) CommonVars.getApplicationContext().getBean(
				"vfVerificationAction");
		VFVerificationAuthority authority = (VFVerificationAuthority)CommonVars.getApplicationContext().getBean("vfVerificationAuthority");		
		vfVerificationAction = (VFVerificationAction) CommonVars
		.getApplicationContext().getBean("vfVerificationAction");
		authority.checkStockBuyImg(new Request(CommonVars.getCurrUser()));
		initialize();
	}
	/**
	 * 初始化
	 */
	private void initialize() {
		
		getContentPane().add(getJToolBar(), BorderLayout.NORTH);
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
//		table = new JTable();
		scrollPane.setViewportView(getTable());
		initTable(null);
		
	}
	
	public JTable getTable(){
		if(table==null){
			table = new MultiSpanCellTable();
		}
		return table;
	}
	
	/**
	 * 初始化表格edit
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void initTable(List<VFStockBuyImg> list) {
		if (list == null || list.size() <= 0) {
			list = new Vector();
		}

		tableMode = new AttributiveCellTableModel((MultiSpanCellTable) table, list, new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
//				list.add(addColumn("序号", "serialNumber", 100));
				
				list.add(addColumn("料件料号", "ptNo", 100));
				list.add(addColumn("工厂名称", "ptName", 100));
				list.add(addColumn("工厂规格", "ptSpec", 100));
				list.add(addColumn("工厂单位", "ptUnit", 100));
				list.add(addColumn("库存数量", "storeAmount", 100));
				
				list.add(addColumn("归并序号", "mergerNo", 100));  //mergerNo
				list.add(addColumn("报关商品名称", "hsName", 100));
				list.add(addColumn("报关商品规格", "hsSpec", 100));
				list.add(addColumn("计量单位", "hsUnit", 100));
				list.add(addColumn("折算报关数量", "hsAmount", 100));
				list.add(addColumn("折算系数", "unitConvert", 100));
				
				list.add(addColumn("仓库", "warehouse", 100));
				list.add(addColumn("备注", "memo", 100));
				return list;
			}
		});
		
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

		GroupableTableHeader header = (GroupableTableHeader) table
				.getTableHeader();
		header.addColumnGroup(exgGroup);
		header.addColumnGroup(ptImpGroup);

	}
	
	/**
	 * 按钮
	 * @return
	 */
	private JToolBar getJToolBar() {
		if (jtoolBar == null) {
			jtoolBar = new JToolBar();
			FlowLayout f=new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(0);
			f.setHgap(0);
			jtoolBar.setLayout(f);
			JLabel label = new JLabel("\u6279\u6B21\u9009\u62E9");
			jtoolBar.add(label);
			
			jtoolBar.add(getJComboBox());
			jtoolBar.add(getLblNewLabel());
			jtoolBar.add(getTfseqNum());
			
			jtoolBar.add(getBtnHistroy());

			jtoolBar.add(getBtnImport());
			
			jtoolBar.add(getBtnSwitch());
			jtoolBar.add(getBtnEdit());
			jtoolBar.add(getBtnDelete());
		
			jtoolBar.add(getBtnExport());

			jtoolBar.add(getBtnClean());
		}
		return jtoolBar;
	}
	
	/**
	 * 下拉框
	 * 
	 * @return
	 */
	public JComboBox getJComboBox() {
		if (comboBox == null) {
			comboBox = new JComboBox();
			comboBox.setEditable(true);
			comboBox.setPreferredSize(new Dimension(120,27));
			DefaultComboBoxModel DfComboBox = new DefaultComboBoxModel();
			// 格式化时间
			SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd");
			List<VFSection> dataSource = vfAction.findVFSectionList(new Request(CommonVars.getCurrUser()));
			List<VFSection> manulist = new ArrayList<VFSection>();
			for (int i = 0; i < dataSource.size(); i++) {
				VFSection vfSection = dataSource.get(i);
				vfSection.setEndDate(Date.valueOf(DateFormat.format(vfSection.getEndDate())));
				manulist.add(vfSection);
			}
			DfComboBox = new DefaultComboBoxModel(manulist.toArray());
			this.comboBox.setModel(DfComboBox);
			CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(comboBox, "verificationNo",
					"endDate", 100);
			this.comboBox.setRenderer(CustomBaseRender.getInstance().getRender("verificationNo",
					"endDate", 30, 100)); //endDate

			this.comboBox.setSelectedItem(null);

		}
		return comboBox;
	}
	  
	
	/**
	 * 导入
	 * @return
	 */
	public JButton getBtnImport(){
		if(btnImport == null){
			btnImport = new JButton("1.\u5BFC\u5165\u76D8\u70B9\u6570\u636E");
			btnImport.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//导入盘点数据
					if(checkData()){
						final Request req = new Request(CommonVars.getCurrUser());
						if(vfAction.isExistsBySection(req, vfSection, VFStockBuyImg.class)){						
							if(JOptionPane.YES_OPTION != JOptionPane.showOptionDialog(getParent(), "已存在本批次数据，确定删除本批次数据，重新导入?","提示", JOptionPane.YES_NO_OPTION, 
									JOptionPane.INFORMATION_MESSAGE,null, new Object[]{"是","否"},"否")){
								return;
							}
							vfAction.deleteVFStockBuyImgs(request, vfSection);
							initTable(null);
						}																
						DgVFStockBuyImg dgVFStockBuyImg = new DgVFStockBuyImg();
						dgVFStockBuyImg.setVfSection(vfSection);
						dgVFStockBuyImg.setFmVFStockBuyImg(FmVFStockBuyImg.this);
						dgVFStockBuyImg.setVisible(true);					
					}
				}
			});
		}
		return btnImport;
	}

	/**
	 * 折算
	 * @return
	 */
	public JButton getBtnSwitch(){
		if(btnSwitch == null){
			btnSwitch = new JButton("2.\u6298\u7B97\u62A5\u5173\u6570\u91CF");
			btnSwitch.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(checkData()){
						new SwitchWorker().execute();
					}
				}
			});
		}
		return btnSwitch;
	}

	/**
	 * 数据折算 工作线程
	 * @author Administrator
	 *
	 */
@SuppressWarnings("rawtypes")
	class SwitchWorker extends SwingWorker {
		@Override
		protected List<VFStockBuyImg> doInBackground() throws Exception {
			CommonProgress.showProgressDialog(FmVFStockBuyImg.this);
			CommonProgress.setMessage("系统正在初始化数据，请稍后...");
			String logg;
			List<VFStockBuyImg> ListImgConvert = new ArrayList<VFStockBuyImg>();
			try {
				String table = "VFStockBuyImg";
				logg = vfVerificationAction.convertImgConverts(request,
						vfSection, table);
				if (logg != null && !logg.isEmpty()) {
					String[] ptNos = logg.toString().split(",");
					List<ErrorMessage> errs = new ArrayList<ErrorMessage>();
					for (int i = 0; i < ptNos.length; i++) {
						errs.add(new ErrorMessage(ptNos[i],
								"在【物料与报关对应表】中不存在,或找不到对应关系"));
					}
					DgErrorMessage.show(errs);
					String seqNum = tfseqNum.getText().trim();
					ListImgConvert = vfVerificationAction.findVFStockProcessImgsin(request,vfSection,(seqNum == null || "".equals(seqNum)) ? null
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

		@SuppressWarnings("unchecked")
		@Override
		protected void done() {
			try {
				initTable((List<VFStockBuyImg>) this.get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
}
	
	/**
	 * 历史
	 * @return
	 */
	public JButton getBtnHistroy(){
		if(btnHistroy == null){
			btnHistroy = new JButton("\u67E5\u770B\u5386\u53F2\u6570\u636E");
			btnHistroy.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					if(checkData()){
						String seqNum = tfseqNum.getText().trim();
						List<VFStockBuyImg> dataSource = vfAction.findVFStockBuyImgs(request, vfSection,  (seqNum == null || "".equals(seqNum))?null:Integer.parseInt(seqNum));
						initTable(dataSource);
					}
				}
			});
		}
		return btnHistroy;
	}

	
	/**
	 * 导出
	 * @return
	 */
	public JButton getBtnExport(){
		if(btnExport == null){
			btnExport = new JButton("\u5BFC\u51FAExcel");
			btnExport.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(tableMode.getRowCount() > 0){
						tableMode.getMiSaveAllToExcel().doClick();
					}
				}
			});
		}
		return btnExport;
	}
	
	/**
	 * 清除
	 * @return
	 */
	public JButton getBtnClean(){
		if(btnClean == null){
			btnClean = new JButton("清空当前数据");
			btnClean.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (checkData() && table.getRowCount() > 0) {
						if(JOptionPane.YES_OPTION != JOptionPane.showOptionDialog(FmVFStockBuyImg.this, "将删除本批次下的 历史数据！","提示", JOptionPane.YES_NO_OPTION, 
								JOptionPane.INFORMATION_MESSAGE,null, new Object[]{"是","否"},"否")){
							return;
						}
						vfAction.deleteVFStockBuyImgs(request, vfSection);		
						initTable(null);
					}
				}
			});
		}
		return btnClean;
	}
	
	/**
	 * 数据验证
	 */
	public boolean checkData() {
		vfSection = (VFSection)comboBox.getSelectedItem();
		if (vfSection == null) {
			JOptionPane.showMessageDialog(this, "批次号不能为空\r\n请选择批次号!", "提示!", 1);
			return false;
		}
		return true;
	}
	private JTextField getTfseqNum() {
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
		int count = comboBox.getItemCount();
		for (int i = 0; i < count; i++) {
			Object obj = comboBox.getItemAt(i);
			if(obj instanceof VFSection){
				VFSection vfsection = (VFSection)obj;
				if(vfsection.getId().equals(section.getId())){
					comboBox.setSelectedIndex(i);
				}
			}
		}
		if(mergerNo != null)
			tfseqNum.setText(String.valueOf(mergerNo));
		btnHistroy.doClick();
	}
	
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton("修改");
			btnEdit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					DgVFBaseStockExgOrImgEdit edit = new DgVFBaseStockExgOrImgEdit();
					VFBaseStockImg baseStockImg = (VFBaseStockImg)tableMode.getCurrentRow();
					if(baseStockImg==null){
						return;
					}
					edit.setBaseStockImg(baseStockImg);
					edit.setMaterielType(MaterielType.MATERIEL);
					edit.setVisible(true);
					if(edit.isOk){
						tableMode.updateRow(baseStockImg);
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
					List<VFBaseStockImg> baseStockImgs = (List<VFBaseStockImg>)tableMode.getCurrentRows();
					if(baseStockImgs==null){
						return;
					}
					
					if (JOptionPane.OK_OPTION != JOptionPane.showOptionDialog(FmVFStockBuyImg.this, "确定要删除所选择的数据吗?",
							"提示", JOptionPane.OK_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] { "是",
									"否" }, "否")) {
						return;
					} else {
						vfVerificationAction.deleteVFBaseStockImgs(request, baseStockImgs);
						tableMode.deleteRows(baseStockImgs);
					}
				}
			});
		}
		return btnDelete;
	}
}

