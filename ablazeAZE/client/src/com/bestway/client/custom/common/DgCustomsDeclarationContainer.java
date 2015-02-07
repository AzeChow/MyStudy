/*
 * Created on 2004-8-10
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.client.custom.common;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.List;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicComboBoxEditor;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import com.bestway.bcs.contractexe.entity.BcsCustomsDeclaration;
import com.bestway.bcs.contractexe.entity.BcsCustomsDeclarationContainer;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxUI;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.custombase.entity.CustomBaseEntity;
import com.bestway.bcus.custombase.entity.parametercode.SrtJzx;
import com.bestway.bcus.custombase.entity.parametercode.SrtTj;
import com.bestway.bcus.enc.entity.CustomsDeclaration;
import com.bestway.bcus.enc.entity.CustomsDeclarationContainer;
import com.bestway.client.custom.common.DgTransferCustomsConveyance.CustomDocument;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.customs.common.action.BaseEncAction;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.customs.common.entity.BaseCustomsDeclarationContainer;
import com.bestway.dzsc.contractexe.entity.DzscCustomsDeclaration;
import com.bestway.dzsc.contractexe.entity.DzscCustomsDeclarationContainer;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgCustomsDeclarationContainer extends JDialogBase {

	private JPanel							jContentPane					= null;
	private JToolBar						jToolBar						= null;
	private JSplitPane						jSplitPane						= null;
	private JPanel							jPanel							= null;
	private JTable							jTable							= null;
	private JScrollPane						jScrollPane						= null;
	private JButton							btnEdit							= null;
	private JButton							btnAdd							= null;
	private JButton							btnDelete						= null;
	private JTextField						tfContainerCode					= null;
	protected JComboBox						cbbContainerNumber				= null;
	private JButton							btnSave							= null;
	private JButton							btnCancel						= null;
	protected JTableListModel					tableModel						= null;
	private BaseCustomsDeclaration			baseCustomsDeclaration			= null;
	private BaseCustomsDeclarationContainer	baseCustomsDeclarationContainer	= null;
	private int								dataState						= DataState.ADD;
	private BaseEncAction					baseEncAction					= null;
	private int								customsDeclarationDataState		= DataState.ADD;
	private List							containers						= null;
	private JComboBox						cbbSrttj						= null;

	/**
	 * @return Returns the baseEncAction.
	 */
	public BaseEncAction getBaseEncAction() {
		return baseEncAction;
	}

	/**
	 * @param baseEncAction
	 *            The baseEncAction to set.
	 */
	public void setBaseEncAction(BaseEncAction baseEncAction) {
		this.baseEncAction = baseEncAction;
	}

	/**
	 * @return Returns the baseCustomsDeclaration.
	 */
	public BaseCustomsDeclaration getBaseCustomsDeclaration() {
		return baseCustomsDeclaration;
	}

	public void setVisible(boolean isFlag) {
		if (isFlag) {
			this.initComponents();
			setState();
			emptyData();
		}
		super.setVisible(isFlag);
	}

	/**
	 * @return Returns the customsDeclarationDataState.
	 */
	public int getCustomsDeclarationDataState() {
		return customsDeclarationDataState;
	}

	/**
	 * @param customsDeclarationDataState
	 *            The customsDeclarationDataState to set.
	 */
	public void setCustomsDeclarationDataState(int customsDeclarationDataState) {
		this.customsDeclarationDataState = customsDeclarationDataState;
	}

	/**
	 * 取得返回结果集
	 * 
	 * @return
	 */
	public List getResultContainer() {
		if (this.customsDeclarationDataState == DataState.EDIT) {
			return this.getDataSource();
		} else {
			return this.tableModel.getList();
		}

	}

	/**
	 * @param baseCustomsDeclaration
	 *            The baseCustomsDeclaration to set.
	 */
	public void setBaseCustomsDeclaration(
			BaseCustomsDeclaration customsDeclaration) {
		this.baseCustomsDeclaration = customsDeclaration;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgCustomsDeclarationContainer() {
		super();
		initialize();

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("集装箱编辑");
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setSize(435, 305);
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
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getBtnAdd());
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnSave());
			jToolBar.add(getBtnDelete());
			jToolBar.add(getBtnCancel());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setDividerLocation(100);
			jSplitPane.setDividerSize(8);
			jSplitPane.setTopComponent(getJPanel());
			jSplitPane.setBottomComponent(getJScrollPane());
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			javax.swing.JLabel jLabel3 = new JLabel();
			javax.swing.JLabel jLabel1 = new JLabel();
			javax.swing.JLabel jLabel = new JLabel();
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jLabel.setBounds(19, 24, 68, 22);
			jLabel.setText("集装箱号");
			jLabel1.setBounds(19, 55, 67, 22);
			jLabel1.setText("编号");
			jLabel3.setBounds(229, 24, 41, 22);
			jLabel3.setText("车架号");
			jPanel.add(jLabel, null);
			jPanel.add(jLabel1, null);
			jPanel.add(jLabel3, null);
			jPanel.add(getTfContainerCode(), null);
			jPanel.add(getCbbContainerNumber(), null);
			jPanel.add(getCbbSrttj(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbSrttj() {
		if (cbbSrttj == null) {
			cbbSrttj = new JComboBox();
			cbbSrttj.setBounds(new java.awt.Rectangle(273, 24, 127, 21));
		}
		return cbbSrttj;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
			jTable.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 2) {
						editData();
					}
				}
			});
			jTable.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {

						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							JTableListModel tableModel = (JTableListModel) jTable
									.getModel();
							if (tableModel == null) {
								return;
							}
							if (tableModel.getCurrentRow() != null) {
								showData((BaseCustomsDeclarationContainer) tableModel
										.getCurrentRow());
							}
						}
					});
		}
		return jTable;
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
	 * This method initializes btnEdit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改");
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					editData();
				}
			});
		}
		return btnEdit;
	}

	private void editData() {
		if (!jTable.isEnabled()) {
			return;
		}
		if (tableModel.getCurrentRow() == null) {
			JOptionPane.showMessageDialog(null, "请选择你要修改的数据!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		baseCustomsDeclarationContainer = (BaseCustomsDeclarationContainer) tableModel
				.getCurrentRow();
		showData(baseCustomsDeclarationContainer);
		dataState = DataState.EDIT;
		setState();
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
			btnAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					emptyData();
					dataState = DataState.ADD;
					setState();
				}
			});
		}
		return btnAdd;
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
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(null, "请选择你要删除的数据", "提示",
								0);
						return;
					}
					if (JOptionPane.showConfirmDialog(null, "是否确定删除数据!!!",
							"提示", 0) != 0) {
						return;
					}
					baseCustomsDeclarationContainer = (BaseCustomsDeclarationContainer) tableModel
							.getCurrentRow();
					DgCustomsDeclarationContainer.this.baseEncAction
							.deleteCustomsDeclarationContainer(new Request(
									CommonVars.getCurrUser()),
									baseCustomsDeclarationContainer);
					tableModel.deleteRow(baseCustomsDeclarationContainer);
				}
			});
		}
		return btnDelete;
	}

	/**
	 * This method initializes tfContainerCode
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfContainerCode() {
		if (tfContainerCode == null) {
			tfContainerCode = new JTextField();
			// edit by xxm 2006-11-27 cxq 提出
			tfContainerCode.setDocument(new CustomDocument());
			tfContainerCode.setBounds(88, 24, 124, 22);
		}
		return tfContainerCode;
	}
	
    //判断字符限数
    class CustomDocument extends PlainDocument {
    	 public void insertString(int offs, String str, AttributeSet a)
                throws BadLocationException {
            if (str == null) {
                return;
            }
            if (super.getLength() >= 11 || str.length() > 11
                    || super.getLength() + str.length() > 11) {
                return;
            }
            
            super.insertString(offs, str, a);
        }      
    }
    

	/**
	 * This method initializes cbbContainerNumber
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbContainerNumber() {
		if (cbbContainerNumber == null) {
			cbbContainerNumber = new JComboBox();
			cbbContainerNumber.setBounds(88, 55, 313, 23);
		}
		return cbbContainerNumber;
	}

	/**
	 * This method initializes btnSave
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("保存");
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (validateIsNull()) {
						return;
					}
					if (!validateOther()) {
						return;
					}
					saveData();
					dataState = DataState.BROWSE;
					setState();
				}
			});
		}
		return btnSave;
	}


	/**
	 * @param containers
	 *            The containers to set.
	 */
	public void setContainers(List containers) {
		this.containers = containers;
	}

	/**
	 * @return Returns the containers.
	 */
	public List getContainers() {
		return containers;
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
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dataState = DataState.BROWSE;
					setState();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * 数据
	 */
	private void showData(
			BaseCustomsDeclarationContainer customsDeclarationContainer) {
		if (customsDeclarationContainer != null) {
			this.tfContainerCode.setText(customsDeclarationContainer
					.getContainerCode());
			this.cbbContainerNumber.setSelectedItem(customsDeclarationContainer
					.getSrtJzx());
			this.cbbSrttj.setSelectedItem(customsDeclarationContainer
					.getSrttj());
		}
	}

	/**
	 * 填充数据
	 */
	private void fillData(
			BaseCustomsDeclarationContainer customsDeclarationContainer) {
		customsDeclarationContainer.setSrttj((SrtTj) this.cbbSrttj
				.getSelectedItem());
		customsDeclarationContainer.setContainerCode(this.tfContainerCode
				.getText());
		customsDeclarationContainer.setSrtJzx((SrtJzx) this.cbbContainerNumber
				.getSelectedItem());
		customsDeclarationContainer
				.setBaseCustomsDeclaration(this.baseCustomsDeclaration);
	}

	/**
	 * 清空数据
	 */
	private void emptyData() {
		this.tfContainerCode.setText("");
		this.cbbSrttj.setSelectedItem(null);
		this.cbbContainerNumber.setSelectedItem(null);
	}

	/**
	 * 初始化组件
	 */
	protected void initComponents() {
		//
		// 初始化集装箱编号
		//
		this.cbbContainerNumber.setModel(CustomBaseModel.getInstance()
				.getSrtJzxModel());
		this.cbbContainerNumber
				.setRenderer(new MultiColumnTextListCellRenderer());
		this.cbbContainerNumber.setSelectedItem(null);
		this.cbbContainerNumber.setEditor(new TextFieldComboBoxEditor(cbbContainerNumber));
		//
		// 初始化托架号
		//
		this.cbbSrttj.setModel(CustomBaseModel.getInstance().getSrtTjModel());
		this.cbbSrttj
				.setRenderer(new MultiColumnTextListCellRenderer());
		this.cbbSrttj.setSelectedItem(null);
		this.cbbSrttj.setEditor(new TextFieldComboBoxEditor(cbbSrttj));
		this.cbbSrttj.setUI(new CustomBaseComboBoxUI(360));
		this.cbbSrttj.setMaximumRowCount(15);
		//
		// 初始化表
		//
		this.initTable(this.getDataSource());
	}

	
	/**
	 * 获取 set-->list 数据源
	 */
	private List getDataSource() {
		if (this.customsDeclarationDataState == DataState.EDIT) {
			return baseEncAction.findContainerByCustomsDeclaration(new Request(
					CommonVars.getCurrUser()), baseCustomsDeclaration);
		} else if (this.customsDeclarationDataState == DataState.ADD) {
			return getContainers();
		}
		return null;
	}

	/**
	 * 初始化数据Table
	 */
	private void initTable(List dataSource) {
		tableModel = new JTableListModel(jTable, dataSource,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("集装箱号码", "containerCode", 100));
						list.add(addColumn("编号", "srtJzx.code", 100));
						list.add(addColumn("尺寸", "srtJzx.name", 100));
						list.add(addColumn("特征", "srtJzx.srtUsing", 130));
						list.add(addColumn("托架号", "srttj.code", 100));
						return list;
					}
				});
	}

	/**
	 * 设置 window components 状态
	 */
	private void setState() {
		this.btnAdd.setEnabled(dataState == DataState.BROWSE);
		this.btnEdit.setEnabled(dataState == DataState.BROWSE);
		this.btnSave.setEnabled(dataState != DataState.BROWSE);
		this.btnCancel.setEnabled(dataState != DataState.BROWSE);
		this.btnDelete.setEnabled(dataState == DataState.BROWSE);
		this.tfContainerCode.setEditable(dataState != DataState.BROWSE);
		this.cbbSrttj.setEnabled(dataState != DataState.BROWSE);
		this.cbbContainerNumber.setEnabled(dataState != DataState.BROWSE);
		this.jTable.setEnabled(dataState == DataState.BROWSE);
	}

	/**
	 * 其它必要信息验证
	 */
	private boolean validateOther() {
		if (this.tfContainerCode.getText().trim().length() != 11) {
			JOptionPane.showMessageDialog(this, "集装箱号位数不等于11位,请重新输入!", "提示",
					1);
			return false;
		}
		return true;
	}

	/**
	 * 验证是否为空
	 */
	private boolean validateIsNull() {
		if (this.tfContainerCode.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "集装箱号不能为空", "提示", 1);
			return true;
		}
		// if (this.tfSrttj.getText().equals("")) {
		// JOptionPane.showMessageDialog(this, "车架号不能为空", "提示", 1);
		// return true;
		// }
		if (this.cbbContainerNumber.getSelectedItem() == null
				|| this.cbbContainerNumber.getSelectedIndex() == -1) {
			JOptionPane.showMessageDialog(this, "编号不能为空", "提示", 1);
			return true;
		}
		return false;
	}

	/**
	 * 保存数据只是加入到集合中去,并没有保存到数据库中
	 */
	private void saveData() {
		if (dataState == DataState.ADD) {
			if (baseCustomsDeclaration instanceof CustomsDeclaration) {
				baseCustomsDeclarationContainer = new CustomsDeclarationContainer();
			} else if (baseCustomsDeclaration instanceof BcsCustomsDeclaration) {
				baseCustomsDeclarationContainer = new BcsCustomsDeclarationContainer();
			} else if (baseCustomsDeclaration instanceof DzscCustomsDeclaration) {
				baseCustomsDeclarationContainer = new DzscCustomsDeclarationContainer();
			}
			this.fillData(baseCustomsDeclarationContainer);
			//
			// 如果报关单是修改时才进行实际性保存
			//
			if (this.customsDeclarationDataState == DataState.EDIT) {
				baseCustomsDeclarationContainer = this.baseEncAction
						.saveCustomsDeclarationContainer(new Request(CommonVars
								.getCurrUser()),
								this.baseCustomsDeclarationContainer);
			}
			this.tableModel.addRow(baseCustomsDeclarationContainer);
		} else if (dataState == DataState.EDIT) {
			this.fillData(baseCustomsDeclarationContainer);
			//
			// 如果报关单是修改时才进行实际性保存
			//
			if (this.customsDeclarationDataState == DataState.EDIT) {
				baseCustomsDeclarationContainer = this.baseEncAction
						.saveCustomsDeclarationContainer(new Request(CommonVars
								.getCurrUser()),
								this.baseCustomsDeclarationContainer);
			}
			this.tableModel.updateRow(baseCustomsDeclarationContainer);
		}
	}

	
	/**
	 * 定制文本编辑
	 * @author ls
	 *
	 */
	class TextFieldComboBoxEditor extends BasicComboBoxEditor
	{
		private JComboBox	comboBox	= null;

		private JTextField	textField	= new JTextField();		

		private Object		obj			= null;

		public Component getEditorComponent() {
			return textField;
		}

		public void setItem(Object anObject) {
			if (anObject != null) {
				obj = anObject;
				if (anObject instanceof CustomBaseEntity) {
					CustomBaseEntity obj = (CustomBaseEntity) anObject;
					this.textField.setText(obj.getCode());
				}
			} else {
				this.textField.setText("");
			}
		}

		public Object getItem() {
			return null;
		}

		public void selectAll() {
		}

		public TextFieldComboBoxEditor(JComboBox comboBox) {
			this.comboBox = comboBox;		
			this.comboBox.setEditable(true);
			this.textField.setBorder(javax.swing.BorderFactory
					.createLineBorder(new java.awt.Color(106, 135, 171), 1));
			this.textField.addFocusListener(new FocusAdapter() {
				public void focusGained(FocusEvent e) {
					((JTextField) e.getSource()).selectAll();
				}

				public void focusLost(FocusEvent e) {
					setSelectedIndex(textField.getText().trim());
				}

			});
		}

		public void setSelectedIndex(String code) {
			int index = -1;		
			if (code.equals("")) {
				this.comboBox.setSelectedIndex(index);
				return;
			}
			if (this.comboBox.getItemCount() < 1) {
				this.textField.setText("");
				return;
			}
			
			Object obj = this.comboBox.getItemAt(0);
			if (obj instanceof CustomBaseEntity) {
				CustomBaseEntity entity = null;
				ComboBoxModel model = this.comboBox.getModel();
				int size = model.getSize();
				for (int i = 0; i < size; i++) {
					entity = (CustomBaseEntity) model.getElementAt(i);
					if (entity.getCode().equals(code)) {
						index = i;
						break;
					}
				}
			} 
			if (this.comboBox.getSelectedIndex() < 0 && index < 0) {
				this.textField.setText("");
			}
			this.comboBox.setSelectedIndex(index);
		}
	}

	
	
	/**
	 * 多列文本数据呈现
	 */
	class MultiColumnTextListCellRenderer extends DefaultListCellRenderer {
		private JLabel	lbCode		= new JLabel();

		private JLabel	lbName		= new JLabel();

		private JLabel	lbSrtUsing	= new JLabel();

		private JLabel	lbSrtWeight	= new JLabel();
		
		private JLabel  lbMemo = new JLabel();

		public MultiColumnTextListCellRenderer() {
			lbCode.setBounds(0, 0, 50, 20);
			this.add(lbCode);
			lbName.setBounds(50, 0, 80, 20);
			this.add(lbName);
			lbSrtUsing.setBounds(130, 0, 100, 20);
			this.add(lbSrtUsing);
			lbSrtWeight.setBounds(230, 0, 50, 20);
			this.add(lbSrtWeight);
			lbMemo.setBounds(280, 0, 80, 20);
			this.add(lbMemo);
		}

		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {

			super.getListCellRendererComponent(list, value, index, isSelected,
					cellHasFocus);
			if (value != null) {
				if(value instanceof SrtTj){
					SrtTj obj = (SrtTj) value;
					this.lbCode.setText(obj.getCode());
					this.lbName.setText(obj.getName());
					this.lbSrtUsing.setText(obj.getTjType());
					this.lbSrtWeight.setText(obj.getTjWeight() == null ? "0" : obj
							.getTjWeight().toString());
					this.lbMemo.setText(obj.getBracketEnglishName());
				}else if (value instanceof SrtJzx){
					SrtJzx obj = (SrtJzx) value;
					this.lbCode.setText(obj.getCode());
					this.lbName.setText(obj.getName());
					this.lbSrtUsing.setText(obj.getSrtUsing());
					this.lbSrtWeight.setText(obj.getSrtWeight() == null ? "0" : obj
							.getSrtWeight().toString());
				}				
			} else {
				this.lbCode.setText("");
				this.lbName.setText("");
				this.lbSrtUsing.setText("");
				this.lbSrtWeight.setText("");
			}
			this.setText("                     ");
			return this;
		}
	}

	
	
}
