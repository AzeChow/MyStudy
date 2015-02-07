package com.bestway.bcus.client.checkstock;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.EventObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableCellRenderer;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.commons.io.FileUtils;
import com.bestway.bcus.client.checkcancel.DgCancelOwner;
import com.bestway.bcus.client.checkstock.factoryanalyse.DgErrorMessage;
import com.bestway.bcus.client.checkstock.jtreeTable.*;

import com.bestway.bcus.checkcancel.action.CheckCancelAction;
import com.bestway.bcus.checkcancel.entity.CancelOwnerHead;
import com.bestway.bcus.checkstock.action.ECSCheckStockAction;
import com.bestway.bcus.checkstock.action.ECSCheckStockAuthority;
import com.bestway.bcus.checkstock.entity.ECSAttachmentManagement;
import com.bestway.bcus.checkstock.entity.ECSSection;
import com.bestway.bcus.client.common.CommonDataSource;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.system.entity.Company;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import javax.swing.JSplitPane;

/**
 * @author lyh
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
@SuppressWarnings({ "serial", "rawtypes", "unchecked" })
public class FmECSAttachment extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;
	private JTableListModel tableModel = null;
	private ECSCheckStockAction ecsCheckStockAction = null;
	private JPanel panel;
	private JToolBar toolBar;
	private JScrollPane scrollPane;
	private JTable table;
	private Request request;
	private JScrollPane scrollPane_1;
	private JButton btnAdd;
	private JButton btnAddParent;
	private JButton btnAddChild;
	private JButton btnHidden;
	private JSplitPane splitPane_1;
	private JTreeTable treeTable;
	private JButton btnEdit;
	private JButton btnSaveData;
	private JButton btnDelette;
	private JPanel panel_1;
	private JToolBar toolBar_1;
	private JButton btnDeleteSection;
	AttachmentTableModel treeModel;
	List treeColumnName = new ArrayList();
	private CheckCancelAction checkCancelAction = null;
	private JButton btnCheck;
	private JButton btnPrint;

	/**
	 * This is the default constructor
	 */
	public FmECSAttachment() {
		super();
		request = new Request(CommonVars.getCurrUser());
		ecsCheckStockAction = (ECSCheckStockAction) CommonVars
				.getApplicationContext().getBean("ecsCheckStockAction");
		ECSCheckStockAuthority authority = (ECSCheckStockAuthority) CommonVars
				.getApplicationContext().getBean("ecsCheckStockAuthority");
		authority.checkAnalyse(request);
		checkCancelAction = (CheckCancelAction) CommonVars
				.getApplicationContext().getBean("checkCancelAction");

		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(1088, 566);
		this.setContentPane(getJContentPane());
		this.setTitle("核查资料附件管理");
		List list = ecsCheckStockAction.findAttachmentSection(request, true);
		if (list == null) {
			list = new ArrayList();
		}
		treeColumnName.add("项目内容");
		treeColumnName.add("资料内容");
		treeColumnName.add("提供单位");
		treeColumnName.add("资料类型");
		treeColumnName.add("资料提供要求");
		treeColumnName.add("是否用印");
		treeColumnName.add("资料签名/手印");
		treeColumnName.add("附件名");
		treeColumnName.add("");
		treeColumnName.add("创建时间");
		treeColumnName.add("创建人");
		treeColumnName.add("修改时间");
		treeColumnName.add("修改人");
		treeColumnName.add("是否隐藏");
		initTable(list);
		initTreeTable();
	}

	public JTreeTable getTreeTable() {
		if (treeTable == null) {
			treeModel = new AttachmentTableModel(treeColumnName,
					new ArrayList());
			treeTable = new JTreeTable(treeModel);
			treeTable.setTextProperty("projectContent");
		}
		return treeTable;
	}

	// ///////////////
	class ButtonEditor extends DefaultCellEditor {

		protected JButton button;
		private String label;
		private boolean isPushed;

		public ButtonEditor(JTextField checkBox) {
			super(checkBox);
			this.setClickCountToStart(1);
			button = new JButton();
			button.setOpaque(true);
			button.setPreferredSize(new Dimension(20, 16));
//			button.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.RED, Color.RED, Color.RED, Color.RED));
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					fireEditingStopped();
					ECSAttachmentManagement attachment = getDataByRow();
					if (attachment != null) {
						if (ECSAttachmentManagement.UPLOAD_AND_DOWNLOAD.equals(attachment.getControlsState())) {
							Object[] options ={ "上传文件", "下载文件！","取消选择"};
							int m = JOptionPane.showOptionDialog(null, "请选择！", "标题",JOptionPane.YES_NO_OPTION,
									JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
							if(m==0){
								// 上传文件
								upLoad(attachment);
								initTreeTable();
								
//								new Upload(attachment).execute(); 
							}else if(m==1){
								// 下载文件
								download(attachment);
//								Download download = new Download(attachment);
//								download.execute();
							}
							return;
						} else if (ECSAttachmentManagement.EXPORP_EXCEL.equals(attachment.getControlsState())) {
							File file = getSelectFile(attachment.getProjectContent()
									+ ".xls");
							AttachmentExportExcel exportExecl = new AttachmentExportExcel(
									attachment, file);
							exportExecl.exportExcel();
						} else if (ECSAttachmentManagement.PRINT.equals(attachment.getControlsState())) {
							print(attachment);
						}
					}else{
						JOptionPane.showMessageDialog(FmECSAttachment.this, "当前行信息为空！");
					}
				}
			});

		}

		public Component getTableCellEditorComponent(final JTable table,
				Object value, boolean isSelected, int row, int column) {
			if (isSelected) {
				button.setForeground(table.getSelectionForeground());
				button.setBackground(table.getSelectionBackground());
			} else {
				button.setForeground(table.getForeground());
				button.setBackground(table.getBackground());
			}
			// label = (value == null) ? "" : value.toString();
			if (value != null) {
				if (ECSAttachmentManagement.UPLOAD_AND_DOWNLOAD.equals(value
						.toString())) {
					label = "上传/下载";
				} else if (ECSAttachmentManagement.EXPORP_EXCEL.equals(value
						.toString())) {
					label = "导出Excel";
				} else if (ECSAttachmentManagement.PRINT.equals(value
						.toString())) {
					label = "打印报表";
				}
			}
			isPushed = true;
			if (value == null) {
				return null;
			}
			return button;
		}

		public Object getCellEditorValue() {
			isPushed = false;
			return new String(label);
		}

		public boolean stopCellEditing() {
			isPushed = false;
			return super.stopCellEditing();
		}

		@Override
		public boolean shouldSelectCell(EventObject anEvent) {
			return super.shouldSelectCell(anEvent);
		}

	}

	class ButtonRenderer extends JButton implements TableCellRenderer {

		public ButtonRenderer() {
			setOpaque(true);
		}

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			if (isSelected) {
				setForeground(table.getSelectionForeground());
				setBackground(table.getSelectionBackground());
			} else {
				setForeground(table.getForeground());
				setBackground(UIManager.getColor("UIManager"));
			}
			// setText((value == null) ? "" : value.toString());

			if (value == null) {
				return null;
			} else {
				if (ECSAttachmentManagement.UPLOAD_AND_DOWNLOAD.equals(value
						.toString())) {
					setText("上传/下载");
				} else if (ECSAttachmentManagement.EXPORP_EXCEL.equals(value
						.toString())) {
					setText("导出Excel");
				} else if (ECSAttachmentManagement.PRINT.equals(value
						.toString())) {
					setText("打印报表");
				}
			}
			return this;
		}
	}

	// ///////////////
	/**
	 * 
	 * @param jTable
	 * @param list
	 * @return
	 */
	private void initTable(List list) {
		tableModel = new JTableListModel(table, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new ArrayList();
						list.add(addColumn("报核次数",
								"cancelOwnerHead.cancelTimes", 80));
						list.add(addColumn("批次号", "verificationNo", 60));
						list.add(addColumn("盘点开始日期", "beginDate", 120));
						list.add(addColumn("盘点结束日期", "endDate", 120));
						list.add(addColumn("报核开始日期",
								"cancelOwnerHead.beginDate", 120));
						list.add(addColumn("报核结束日期", "cancelOwnerHead.endDate",
								120));
						return list;
					}
				});
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new java.awt.BorderLayout());
			jContentPane.add(getSplitPane_1(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * @return Returns the tableModel.
	 */
	public JTableListModel getTableModel() {
		return tableModel;
	}

	/**
	 * @param tableModel
	 *            The tableModel to set.
	 */
	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
	}

	/**
	 * 
	 * @return
	 */
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setLayout(new BorderLayout(0, 0));
			panel.add(getToolBar(), BorderLayout.NORTH);
			panel.add(getScrollPane(), BorderLayout.CENTER);
		}
		return panel;
	}

	/**
	 * 
	 * @return
	 */
	private JToolBar getToolBar() {
		if (toolBar == null) {
			toolBar = new JToolBar();
			toolBar.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 0));
			toolBar.add(getBtnAddParent());
			toolBar.add(getBtnAddChild());
			toolBar.add(getBtnEdit());
			toolBar.add(getBtnSaveData());
			toolBar.add(getBtnDelette());
			toolBar.add(getBtnHidden());
			toolBar.add(getBtnCheck());
			toolBar.add(getBtnPrint());
		}
		return toolBar;
	}

//	// 上传文件
//	class upLoadFileDataRunnable implements Runnable {
//		public void run() {
//		}
//	}
	

class Upload extends SwingWorker<Object,Object>{
	private ECSAttachmentManagement attachment = null;
	
	public Upload(ECSAttachmentManagement attachment){
		this.attachment = attachment;
	}
	
	@Override
	protected Object doInBackground() throws Exception {
		if (attachment == null) {
			JOptionPane.showMessageDialog(FmECSAttachment.this, "请选择一行!");
		}
		System.out.println(attachment.getAttachmentName());
		if (attachment.getAttachmentName() != null
				&& !attachment.getAttachmentName().trim().isEmpty()) {
			if (JOptionPane.YES_NO_OPTION != JOptionPane.showOptionDialog(
					getParent(), "确定覆盖上传文档吗?", "提示",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, new Object[] {
							"是(Y)", "否(N)" }, "否(N)")) {
				return null;
			}
		}
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.removeChoosableFileFilter(fileChooser
				.getFileFilter());
		int state = fileChooser.showOpenDialog(FmECSAttachment.this);
		if (state != JFileChooser.APPROVE_OPTION) {
			return null;
		}
		File attachmentFile = fileChooser.getSelectedFile();
		if (attachmentFile == null || !attachmentFile.exists()) {
			return null;
		}
		
		CommonProgress.showProgressDialog(FmECSAttachment.this);
		CommonProgress.setMessage("系统正在上传文件，请稍后...");
		return attachmentFile;
	}
	
	@Override
	protected void done() {
		try {
			File file = (File) get();
			upLoadFileData(file, attachment);
			initTreeTable();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			CommonProgress.closeProgressDialog();
		}
	}
	
}
	
	public void upLoad(ECSAttachmentManagement attachment){
		if (attachment == null) {
			JOptionPane.showMessageDialog(FmECSAttachment.this, "请选择一行!");
		}
		if (attachment.getAttachmentName() != null
				&& !attachment.getAttachmentName().trim().isEmpty()) {
			if (JOptionPane.YES_NO_OPTION != JOptionPane.showOptionDialog(
					getParent(), "确定覆盖上传文档吗?", "提示",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, new Object[] {
							"是(Y)", "否(N)" }, "否(N)")) {
				return;
			}
			attachment.setUpdateDate(new Date());
			attachment.setUpdatePeople(CommonVars.getCurrUser().getUserName());
		}
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.removeChoosableFileFilter(fileChooser
				.getFileFilter());
		// fileChooser.setFileFilter(new CommonFileFilter(
		// new String[] { "*" }, "选择文件"));
		int state = fileChooser.showOpenDialog(FmECSAttachment.this);
		if (state != JFileChooser.APPROVE_OPTION) {
			return;
		}
		File attachmentFile = fileChooser.getSelectedFile();
		if (attachmentFile == null || !attachmentFile.exists()) {
			return;
		}

		upLoadFileData(attachmentFile, attachment);

		initTreeTable();
		

	}

	public void upLoadFileData(File attachmentFile,
			ECSAttachmentManagement attachmentManagement) {
		try {
			ZipUtil zip = new ZipUtil();
			if (attachmentFile == null) {
				JOptionPane.showMessageDialog(FmECSAttachment.this, "清先选择文件");
			}
			String fileName = attachmentFile.getName();
			if (fileName.indexOf(".") >= 0) {
				fileName = fileName.substring(0, fileName.lastIndexOf("."));
			}
			String newFile = attachmentFile.getParentFile() + "\\" + fileName
					+ ".zip";
			zip.zip(newFile, attachmentFile);

			byte[] fileEntity = zip.fileToBytes(newFile);// 将文件转成byte[]
			attachmentManagement.setAttachmentName(attachmentFile.getName());// 文件名称
			attachmentManagement.setCreateDate(new Date());
			attachmentManagement.setCreatePeople(CommonVars.getCurrUser().getUserName());
			ecsCheckStockAction.updateAttachment(
					new Request(CommonVars.getCurrUser()), attachmentManagement);
			ecsCheckStockAction.upLoadAttachment(
					new Request(CommonVars.getCurrUser()), fileEntity, fileName
							+ attachmentManagement.getId() + ".zip");
			new File(newFile).delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @return
	 */
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTreeTable());
		}
		return scrollPane;
	}

	public void initTreeTable() {

		if (tableModel == null) {
			return;
		}
		ECSSection section = (ECSSection) tableModel.getCurrentRow();
		List listData = ecsCheckStockAction.findECSAttachmentManagement(
				new Request(CommonVars.getCurrUser()), section);
		if (listData == null) {
			listData = new ArrayList();
		}
		treeModel = new AttachmentTableModel(treeColumnName, listData);
		treeTable.setModel(treeModel);

		setColumnWidth(treeTable);
		treeTable.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					@Override
					public void valueChanged(ListSelectionEvent e) {
						if (e.getValueIsAdjusting()) {
							ECSAttachmentManagement attachment = getDataByRow();
							if (attachment!=null&&attachment.getIsHidden() != null
									&& attachment.getIsHidden() == true) {
								btnHidden.setText("显示");
							} else {
								btnHidden.setText("隐藏");
							}
							setButtonStatus(attachment);
						}
					}
				});
		scrollPane.setViewportView(treeTable);
		btnEdit.setEnabled(true);

		treeTable.getColumnModel().getColumn(8)
				.setCellRenderer(new ButtonRenderer());
		treeTable.getColumnModel().getColumn(8)
				.setCellEditor(new ButtonEditor(new JTextField()));
	}

	private void setButtonStatus(ECSAttachmentManagement att) {
		if (att == null) {
			btnAddChild.setEnabled(false);
			btnHidden.setEnabled(false);
		} else {
			btnAddChild.setEnabled(isStringEmpty(att.getParentId()));
			btnHidden.setEnabled(isStringEmpty(att.getParentId()));
			btnDelette.setEnabled(!att.getIsTemplate());
		}
	}

	private boolean isStringEmpty(String s) {
		return s == null || s.isEmpty();
	}

	public void setColumnWidth(JTreeTable treeTable) {
		treeTable.getColumnModel().getColumn(0).setPreferredWidth(240);
		treeTable.getColumnModel().getColumn(1).setPreferredWidth(100);
		treeTable.getColumnModel().getColumn(2).setPreferredWidth(100);
		treeTable.getColumnModel().getColumn(3).setPreferredWidth(130);
		treeTable.getColumnModel().getColumn(4).setPreferredWidth(80);
		treeTable.getColumnModel().getColumn(5).setPreferredWidth(125);
		treeTable.getColumnModel().getColumn(6).setPreferredWidth(80);
		treeTable.getColumnModel().getColumn(7).setPreferredWidth(150);
		treeTable.getColumnModel().getColumn(8).setPreferredWidth(100);
		treeTable.getColumnModel().getColumn(9).setPreferredWidth(70);
		treeTable.getColumnModel().getColumn(10).setPreferredWidth(70);
		treeTable.getColumnModel().getColumn(11).setPreferredWidth(70);
		treeTable.getColumnModel().getColumn(12).setPreferredWidth(50);
		treeTable.getColumnModel().getColumn(12).setPreferredWidth(50);
	}

	class Download extends SwingWorker<Object,Object>{
		private ECSAttachmentManagement attachment = null;
		private String fileName = null;
		public Download(ECSAttachmentManagement attachment){
			this.attachment = attachment;
		}
		
		@Override
		protected Object doInBackground() throws Exception {
			if (attachment == null) {
				return null;
			}
			fileName = attachment.getAttachmentName();
			if (fileName == null || fileName.trim().equals("")) {
				JOptionPane.showMessageDialog(FmECSAttachment.this, "服务器中没有您所指定的文件！");
				return null;
			}
			JFileChooser fileChooser = new JFileChooser("./");
			fileChooser.setDialogTitle("请选择文件的存放路径!");
			String extensions = getFileExtension(fileName);
			if (extensions != null) {
				fileChooser.addChoosableFileFilter(new FileNameExtensionFilter(
						extensions, extensions));
			}
			fileChooser.setSelectedFile(new File(fileName));
			int state = fileChooser.showDialog(FmECSAttachment.this, "保存");
			File file = null;
			if (state == JFileChooser.APPROVE_OPTION) {
				file = fileChooser.getSelectedFile();
			} else {
				CommonProgress.showProgressDialog(FmECSAttachment.this);
				CommonProgress.setMessage("系统正在下载文件，请稍后...");
				return file;
			}
			return null;
		}
		
		@Override
		protected void done() {
			try {
				File file = (File) get();
				if(file==null){
					return;
				}
				downLoadFileData(file, attachment.getId(), fileName);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				CommonProgress.closeProgressDialog();
			}
		}
		
	}
	public void download(ECSAttachmentManagement attachment){
		if (attachment == null) {
			return;
		}
		String fileName = attachment.getAttachmentName();
		if (fileName == null || fileName.trim().equals("")) {
			JOptionPane.showMessageDialog(FmECSAttachment.this, "服务器中没有您所指定的文件！");
			return;
		}
		JFileChooser fileChooser = new JFileChooser("./");
		fileChooser.setDialogTitle("请选择文件的存放路径!");
		String extensions = getFileExtension(fileName);
		if (extensions != null) {
			fileChooser.addChoosableFileFilter(new FileNameExtensionFilter(
					extensions, extensions));
		}
		fileChooser.setSelectedFile(new File(fileName));
		int state = fileChooser.showDialog(FmECSAttachment.this, "保存");
		File file = null;
		if (state == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
		} else {
			return;
		}
		if (attachment.getId() != null) {
			downLoadFileData(file, attachment.getId(), fileName);
		}
	}
	
	/**
	 * 下载文件
	 * 
	 * @param depositFile
	 *            存放路径
	 * @param fileId
	 * @param fileName
	 */
	public void downLoadFileData(File depositFile, String fileId,
			String fileName) {
		try {
			if (depositFile == null) {
				JOptionPane.showMessageDialog(FmECSAttachment.this,
						"选择的文件存放地址为空，无法保存！");
				return;
			}
			if (fileId != null) {
				if (fileName.indexOf(".") >= 0) {
					fileName = fileName.substring(0, fileName.lastIndexOf("."));
				}

				// 下载服务器端的文件 文件名加id
				byte[] fileContent = ecsCheckStockAction.downLoadAttachment(
						new Request(CommonVars.getCurrUser()), fileName
								+ fileId + ".zip");
				if (fileContent == null) {
					JOptionPane.showMessageDialog(FmECSAttachment.this,
							"请确认该文件在服务器存在！");
					return;
				}

				// 将文件写入到选择的目录中
				File zipFile = new File(depositFile.getAbsoluteFile()
						.getParent() + "\\" + fileName + ".zip");
				FileUtils.writeByteArrayToFile(zipFile, fileContent);

				ZipUtil zip = new ZipUtil();
				// 解压文件
				zip.unzip(zipFile.getAbsolutePath(),
						depositFile.getAbsolutePath());
				zipFile.delete();
			} else {
				JOptionPane.showMessageDialog(FmECSAttachment.this,
						"选择下载的文件id为空！");
				return;
			}
			CommonProgress.closeProgressDialog();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getFileExtension(String fileName) {
		int index = fileName.lastIndexOf(".");
		if (index >= 0 && fileName.length() > index + 2) {
			return fileName.substring(index + 1);
		}
		return null;
	}

	private JScrollPane getScrollPane_1() {
		if (scrollPane_1 == null) {
			scrollPane_1 = new JScrollPane();
			scrollPane_1.setViewportView(getTable());
		}
		return scrollPane_1;
	}

	private JTable getTable() {
		if (table == null) {
			table = new JTable();
			table.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						@Override
						public void valueChanged(ListSelectionEvent e) {
//							 if (e.getValueIsAdjusting()) {
//							 }
							
							if(tableModel==null||tableModel.getCurrentRow()==null){
								return;
							}
							initTreeTable();
							setButtonStatus(null);
						}
					});
		}
		return table;
	}

	private JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton("新增");
			btnAdd.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					DgAttachmentSectionSel addSection = new DgAttachmentSectionSel();
					addSection.setVisible(true);
					if (addSection.isOk()) {
						tableModel.addRow(addSection.getSection());
						
//						SwingUtilities.invokeLater(new Runnable() {
//							public void run() {
//								initTreeTable();
//							}
//						});
					}
				}
			});
		}
		return btnAdd;
	}

	private JButton getBtnAddParent() {
		if (btnAddParent == null) {
			btnAddParent = new JButton("新增大纲");
			btnAddParent.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ECSSection ecssection = (ECSSection) tableModel
							.getCurrentRow();
					if(ecssection==null){
						JOptionPane.showMessageDialog(FmECSAttachment.this, "请先选择批次！");
						return;
					}
					ECSAttachmentManagement attahment = new ECSAttachmentManagement();
					attahment.setProjectContent("请输入大纲名称");
					attahment.setEcssection(ecssection);
					attahment.setIsTemplate(false);
					ecsCheckStockAction.saveECSAttachment(request, attahment);
					initTreeTable();
				}
			});
		}
		return btnAddParent;
	}

	private JButton getBtnAddChild() {
		if (btnAddChild == null) {
			btnAddChild = new JButton("新增内容");
			btnAddChild.setEnabled(false);
			btnAddChild.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ECSAttachmentManagement patentAttahment = getDataByRow();
					if (patentAttahment == null) {
						return;
					}
					if (patentAttahment.getParentId() == null) {
						ECSAttachmentManagement childAttahment = new ECSAttachmentManagement();
						ECSSection ecssection = (ECSSection) tableModel
								.getCurrentRow();
						childAttahment.setEcssection(ecssection);
						childAttahment.setCreateDate(new Date());
						childAttahment.setControlsState("0");
						childAttahment.setParentId(patentAttahment.getId());
						childAttahment.setIsTemplate(false);
						childAttahment.setProjectContent("请输入大纲内容");
						childAttahment.setProvideUnit("");
						childAttahment.setRequirements("");
						childAttahment.setUsingSeal("");
						childAttahment.setDataType("");
						ecsCheckStockAction.saveECSAttachment(request,
								childAttahment);
						initTreeTable();
					}
				}
			});
		}
		return btnAddChild;
	}

	private JButton getBtnHidden() {
		if (btnHidden == null) {
			btnHidden = new JButton("隐藏");
			btnHidden.setEnabled(false);
			btnHidden.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ECSAttachmentManagement attachment = getDataByRow();
					if (attachment == null) {
						return;
					}
					attachment.setIsHidden(!Boolean.TRUE.equals(attachment
							.getIsHidden()));
					ecsCheckStockAction.saveECSAttachment(request, attachment);
					initTreeTable();
				}
			});
		}
		return btnHidden;
	}

	public void print(ECSAttachmentManagement attachment){
		ECSSection section = (ECSSection) tableModel
				.getCurrentRow();
		if (section == null) {
			return;
		}
		if (attachment == null) {
			return;
		}

		if (ECSAttachmentManagement.PRINT_BEGINDELCARE
				.equals(attachment.getOperateState())) {
			// 打印预报核
			printReport(true, section.getCancelOwnerHead());
		} else if (ECSAttachmentManagement.PRINT_DELCARE
				.equals(attachment.getOperateState())) {
			// 打印正式报核
			printReport(false, section.getCancelOwnerHead());
		} else if (ECSAttachmentManagement.PRINT_DELCARE_ONE
				.equals(attachment.getOperateState())) {
			// 打印正式报核前一次
			String cancelTimes = String.valueOf((Integer
					.parseInt(section.getCancelOwnerHead()
							.getCancelTimes()) - 1));
			CancelOwnerHead cancelOwnerHead = ecsCheckStockAction
					.findCancelOwnerHead(request, cancelTimes);
			printReport(false, cancelOwnerHead);
		} else if (ECSAttachmentManagement.PRINT_DELCARE_TWO
				.equals(attachment.getOperateState())) {
			// 打印正式报核前两次
			String cancelTimes = String.valueOf((Integer
					.parseInt(section.getCancelOwnerHead()
							.getCancelTimes()) - 2));
			CancelOwnerHead cancelOwnerHead = ecsCheckStockAction
					.findCancelOwnerHead(request, cancelTimes);
			printReport(false, cancelOwnerHead);
		}
	}
	/**
	 * 打印
	 * 
	 * @param boo
	 *            为true打印预报核 false 正式报核
	 * @param cancelHead
	 */
	public void printReport(boolean boo, CancelOwnerHead cancelHead) {
		if (boo) {// 预报核
			List list = checkCancelAction.getCustomsIEToTemp(new Request(
					CommonVars.getCurrUser()), cancelHead, true);
			if (list == null && list.size() < 0) {
				JOptionPane.showMessageDialog(FmECSAttachment.this,
						"没有可打印的记录！", "提示", 2);
				return;
			}
			Company company = (Company) CommonVars.getCurrUser().getCompany();
			CommonDataSource imgExgDS = new CommonDataSource(list);
			List headlist = new Vector(); // 只有一条记录
			headlist.add(cancelHead);
			CommonDataSource companyDS = new CommonDataSource(headlist);
			InputStream masterReportStream = DgCancelOwner.class
					.getResourceAsStream("report/cancelYReport.jasper");
			InputStream detailReportStream = DgCancelOwner.class
					.getResourceAsStream("report/cancelYReportSub.jasper");
			try {
				JasperReport detailReport = (JasperReport) JRLoader
						.loadObject(detailReportStream);
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("name", company.getName());
				parameters.put("code", company.getCode());
				parameters.put("imgExgDS", imgExgDS);// 子数据源
				parameters.put("detailReport", detailReport);// 子报表
				JasperPrint jasperPrint;
				jasperPrint = JasperFillManager.fillReport(masterReportStream,
						parameters, companyDS);
				DgReportViewer viewer = new DgReportViewer(jasperPrint);
				viewer.setVisible(true);
			} catch (JRException e1) {
				e1.printStackTrace();
			}
		} else {// 正式报核
			List imgExgs = checkCancelAction.findCancelImgResult(new Request(
					CommonVars.getCurrUser()), cancelHead, true); // 表体
			Company company = (Company) CommonVars.getCurrUser().getCompany();
			CommonDataSource imgExgDS = new CommonDataSource(imgExgs);
			List headlist = new Vector();
			headlist.add(cancelHead);
			CommonDataSource companyDS = new CommonDataSource(headlist);
			InputStream masterReportStream = DgCancelOwner.class
					.getResourceAsStream("report/cancelReport.jasper");
			InputStream detailReportStream = DgCancelOwner.class
					.getResourceAsStream("report/cancelReportSub.jasper");
			try {
				JasperReport detailReport = (JasperReport) JRLoader
						.loadObject(detailReportStream);
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("name", company.getName());
				parameters.put("code", company.getCode());
				parameters.put("imgExgDS", imgExgDS);// 子数据源
				parameters.put("detailReport", detailReport);// 子报表

				parameters.put("tel", company.getTel());
				parameters.put("owner", company.getOwner());
				JasperPrint jasperPrint;
				jasperPrint = JasperFillManager.fillReport(masterReportStream,
						parameters, companyDS);
				DgReportViewer viewer = new DgReportViewer(jasperPrint);
				viewer.setVisible(true);
			} catch (JRException e1) {
				e1.printStackTrace();
			}
		}
	}

	private JSplitPane getSplitPane_1() {
		if (splitPane_1 == null) {
			splitPane_1 = new JSplitPane();
			splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
			splitPane_1.setOneTouchExpandable(true);
			splitPane_1.setDividerSize(5);
			splitPane_1.setBottomComponent(getPanel());
			splitPane_1.setLeftComponent(getPanel_1());
			splitPane_1.setDividerLocation(120);
		}
		return splitPane_1;
	}

	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton("修改");
			btnEdit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if ("修改".equals(btnEdit.getText())) {
						// btnEdit.setEnabled(false);
						btnEdit.setText("取消修改");
						btnSaveData.setEnabled(true);
						if (treeModel != null) {
							treeModel.setCellEditable(true);
						}
					} else {
						btnEdit.setText("修改");
						initTreeTable();
					}
					setState(false);
				}
			});
		}
		return btnEdit;
	}

	public void setState(boolean boo) {
		btnAddParent.setEnabled(boo);
		btnAddChild.setEnabled(boo);
		btnEdit.setEnabled(boo);
		btnSaveData.setEnabled(!boo);
		btnDelette.setEnabled(boo);
		btnHidden.setEnabled(boo);
	}

	private JButton getBtnSaveData() {
		if (btnSaveData == null) {
			btnSaveData = new JButton("保存");
			btnSaveData.setEnabled(false);
			btnSaveData.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					List<ECSAttachmentManagement> ls = ((AttachmentTableModel) ((TreeTableModelAdapter) treeTable
							.getModel()).getModel()).getData();
					
					List<ECSAttachmentManagement> list = new ArrayList<ECSAttachmentManagement>();
					for (int i = 0; i < ls.size(); i++) {
						ECSAttachmentManagement parent = ls.get(i);
						if(parent.getIsModify()){
							parent.setUpdateDate(new Date());
							parent.setUpdatePeople(CommonUtils.getAclUser().getUserName());
							list.add(parent);
						}
						
						List<ECSAttachmentManagement> listChild = parent.getChildren();
						if(listChild!=null){
							for (int j = 0; j < listChild.size(); j++) {
								ECSAttachmentManagement attachmentChild = listChild.get(j);
								if(attachmentChild.getIsModify()){
									attachmentChild.setUpdateDate(new Date());
									attachmentChild.setUpdatePeople(CommonVars.getCurrUser().getUserName());
									list.add(attachmentChild);
								}
							}
						}
						
					}
					
					ecsCheckStockAction.saveECSAttachment(request, list);
					btnEdit.setEnabled(true);
					if (treeModel != null) {
						treeModel.setCellEditable(false);
					}
					btnSaveData.setEnabled(false);
					btnEdit.setText("修改");
					initTreeTable();
					setState(true);
				}
			});
		}
		return btnSaveData;
	}

	private JButton getBtnDelette() {
		if (btnDelette == null) {
			btnDelette = new JButton("删除");
			btnDelette.setEnabled(false);
			btnDelette.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ECSAttachmentManagement attachment = getDataByRow();
					if (attachment == null) {
						JOptionPane.showMessageDialog(FmECSAttachment.this, "请先选择一行！");
						return;
					}
					if (attachment.getIsTemplate() != null
							&& attachment.getIsTemplate()) {
						JOptionPane.showMessageDialog(FmECSAttachment.this,
								"模板允许删除！");
						return;
					}
					ecsCheckStockAction.deleteAttachment(request, attachment);
					initTreeTable();
				}
			});
		}
		return btnDelette;
	}

	public ECSAttachmentManagement getDataByRow() {
		int row = treeTable.getSelectedRow();
		Object obj = ((TreeTableModelAdapter) treeTable.getModel())
				.getDataByRow(row);
		if (obj instanceof ECSAttachmentManagement) {
			return (ECSAttachmentManagement) obj;
		}
		return null;
	}

	private JPanel getPanel_1() {
		if (panel_1 == null) {
			panel_1 = new JPanel();
			panel_1.setLayout(new BorderLayout(0, 0));
			panel_1.add(getToolBar_1(), BorderLayout.NORTH);
			panel_1.add(getScrollPane_1(), BorderLayout.CENTER);
		}
		return panel_1;
	}

	private JToolBar getToolBar_1() {
		if (toolBar_1 == null) {
			toolBar_1 = new JToolBar();
			toolBar_1.add(getBtnAdd());
			toolBar_1.add(getBtnDeleteSection());
		}
		return toolBar_1;
	}

	private JButton getBtnDeleteSection() {
		if (btnDeleteSection == null) {
			btnDeleteSection = new JButton("删除");
			btnDeleteSection.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ECSSection section = (ECSSection) tableModel
							.getCurrentRow();
					section.setIsExist(false);
					ecsCheckStockAction.saveEcsSection(request, section);
					tableModel.deleteRow(section);
					ecsCheckStockAction.deleteAttachment(request, section);
					initTreeTable();
				}
			});
		}
		return btnDeleteSection;
	}

	/**
	 * 获得保存的文件名
	 * 
	 * @return
	 */
	private File getSelectFile(String fileName) {
		JFileChooser fileChooser = new JFileChooser("./");
		fileChooser.setDialogTitle("请选择文件的存放路径!");
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("xls",
				"xls"));
		fileChooser.setSelectedFile(new File(fileName));
		int state = fileChooser.showDialog(FmECSAttachment.this, "保存");
		File file = null;
		if (state == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
		} else {
			return null;
		}

		if (getFileExtension(file.getAbsolutePath()) != null
				&& !"xls".equals(getFileExtension(file.getAbsolutePath()))) {
			file = new File(file.getAbsolutePath().substring(0,
					file.getAbsolutePath().lastIndexOf("."))
					+ ".xls");
		}
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return file;
	}
	private JButton getBtnCheck() {
		if (btnCheck == null) {
			btnCheck = new JButton("检查");
			btnCheck.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					List<ECSAttachmentManagement> ls = ((AttachmentTableModel) ((TreeTableModelAdapter) treeTable
							.getModel()).getModel()).getData();
					
					List<String> list = new ArrayList<String>();
					
					for (int i = 0; i < ls.size(); i++) {
						List<ECSAttachmentManagement> listChild = ls.get(i).getChildren();
						
						if(listChild!=null){
							for (int j = 0; j < listChild.size(); j++) {
								ECSAttachmentManagement attachmentChild = listChild.get(j);
								if(ECSAttachmentManagement.UPLOAD_AND_DOWNLOAD.equals(attachmentChild.getControlsState())
										&&(attachmentChild.getAttachmentName()==null||
										"".equals(attachmentChild.getAttachmentName().trim()))){
									list.add(attachmentChild.getProjectContent());
								}
							}
						}
						
					}
					if(list.size()>0){
						DgErrorMessage message = new DgErrorMessage();
						message.setList(list, "附件还未上传");
						message.setVisible(true);
					}else{
						JOptionPane.showMessageDialog(FmECSAttachment.this, "数据检查完毕，所有模板模块都以上传文件");
					}
				}
			});
		}
		return btnCheck;
	}
	private JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton("工作分配");
			btnPrint.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					List list = getDateSource();
					if (list.size() < 0) {
						JOptionPane.showMessageDialog(FmECSAttachment.this,
								"没有可打印的记录！", "提示", 2);
						return;
					}
					
					Collections.sort(list, new Comparator<ECSAttachmentManagement>() {
						@Override
						public int compare(ECSAttachmentManagement o1,
								ECSAttachmentManagement o2) {
							String stu1 = o1.getProvideUnit();
							String stu2 = o2.getProvideUnit();
							return stu1.compareTo(stu2);
						}
					});
					
					CommonDataSource imgExgDS = new CommonDataSource(list);
					InputStream masterReportStream = DgCancelOwner.class
							.getResourceAsStream("report/AttachmentReport.jasper");
					try {
						Map<String, Object> parameters = new HashMap<String, Object>();
						JasperPrint jasperPrint = JasperFillManager.fillReport(masterReportStream,
								parameters, imgExgDS);
						DgReportViewer viewer = new DgReportViewer(jasperPrint);
						viewer.setVisible(true);
					} catch (JRException e1) {
						e1.printStackTrace();
					}
				}
			});
		}
		return btnPrint;
	}
	
	public List getDateSource(){
		List<ECSAttachmentManagement> list = new ArrayList<ECSAttachmentManagement>();
		
		ECSSection section = (ECSSection)tableModel.getCurrentRow();
		if(section==null)
			return new ArrayList();
		List<ECSAttachmentManagement> listData = ecsCheckStockAction.findECSAttachmentManagement(
				new Request(CommonVars.getCurrUser()), section);
		for (int i = 0; i < listData.size(); i++) {
			ECSAttachmentManagement parent = listData.get(i);
			List<ECSAttachmentManagement> listChild = parent.getChildren();
			if(listChild!=null){
				list.addAll(listChild);
			}
		}
		return list;
	}
	
	/**
	 * 根据数据显示信息
	 * @param section
	 * @param seqNum
	 */
	public void showData(ECSSection section, Integer seqNum) {
		List listData = ecsCheckStockAction.findECSAttachmentManagement(
				new Request(CommonVars.getCurrUser()), section);
		if (listData == null) {
			listData = new ArrayList();
		}
		treeModel = new AttachmentTableModel(treeColumnName, listData);
	}
}
