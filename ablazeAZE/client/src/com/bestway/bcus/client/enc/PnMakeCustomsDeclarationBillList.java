package com.bestway.bcus.client.enc;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.enc.entity.ApplyToCustomsBillList;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JPanelBase;
public class PnMakeCustomsDeclarationBillList extends JPanelBase {

	private JList jListLeft = null;
	private JList jListRight = null;
	private JButton btnRightAll = null;
	private JButton btnRight = null;
	private JButton btnLeft = null;
	private JButton btnLeftAll = null;
	
	private EncAction encAction ;
	private JScrollPane jScrollPane = null;
	private JScrollPane jScrollPane1 = null;
	private int impexpflat = 0;
	/**
	 * This is the default constructor
	 */
	public PnMakeCustomsDeclarationBillList() {
		super();
		this.encAction = (EncAction) CommonVars.getApplicationContext().getBean("encAction");
		
		initialize();		
		
		
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private  void initialize() {
		javax.swing.JLabel jLabel1 = new JLabel();
		javax.swing.JLabel jLabel = new JLabel();
		this.setLayout(null);
		this.setSize(500, 248);
		jLabel.setBounds(15, 3, 91, 14);
		jLabel.setText("未选择记录:");
		jLabel1.setBounds(292, 3, 77, 14);
		jLabel1.setText("已选择记录:");
		this.add(getBtnRightAll(), null);
		this.add(getBtnRight(), null);
		this.add(getBtnLeft(), null);
		this.add(getBtnLeftAll(), null);
		this.add(jLabel, null);
		this.add(jLabel1, null);
		this.add(getJScrollPane(), null);
		this.add(getJScrollPane1(), null);
	}
	
	public void setVisible(boolean isFalg){
		if(isFalg == true){
//			fillListData(jListLeft);
//			jListRight.setListData(new Vector());
			System.out.println("impexpflat222:"+impexpflat);
			fillListData(jListLeft,impexpflat);
			jListRight.setListData(new Vector());
		}
		super.setVisible(isFalg);
	}
	
	/**
	 * This method initializes jListLeft	
	 * 	
	 * @return javax.swing.JList	
	 */    
	private JList getJListLeft() {
		if (jListLeft == null) {
			jListLeft = new JList();
			jListLeft.setCellRenderer(new ListCellRenderer());
			jListLeft.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			jListLeft.addMouseListener(new MouseAdapter() {
			     public void mouseClicked(MouseEvent e) {
			         if (e.getClickCount() == 2) {
			         	if(jListLeft.getSelectedIndex()==jListLeft.locationToIndex(e.getPoint())){
			         		addSelectedRecord(jListLeft,jListRight);
			         	}
			          }
			     }
			 });
		}
		return jListLeft;
	}
	/**
	 * This method initializes jListRight	
	 * 	
	 * @return javax.swing.JList	
	 */    
	private JList getJListRight() {
		if (jListRight == null) {
			jListRight = new JList();
			jListRight.setCellRenderer(new ListCellRenderer());
			jListRight.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			jListRight.addMouseListener(new MouseAdapter() 
			{
			     public void mouseClicked(MouseEvent e) 
			     {
			         if (e.getClickCount() == 2) {
			         	if(jListRight.getSelectedIndex()==jListRight.locationToIndex(e.getPoint())){
			         		addSelectedRecord(jListRight,jListLeft);
			         	}			
			         }
			     }});
		}
		return jListRight;
	}
	/**
	 * This method initializes btnRightAll	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getBtnRightAll() {
		if (btnRightAll == null) {
			btnRightAll = new JButton();
			btnRightAll.setBounds(223, 97, 56, 25);
			btnRightAll.setText(">>>");
			btnRightAll.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					addAllRecord(jListLeft,jListRight);
				}
			});
		}
		return btnRightAll;
	}
	/**
	 * This method initializes btnRight	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getBtnRight() {
		if (btnRight == null) {
			btnRight = new JButton();
			btnRight.setBounds(223, 62, 56, 25);
			btnRight.setText(">");
			btnRight.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					if(jListLeft.getSelectedIndex()==-1){
						return;
					}
					addSelectedRecord(jListLeft,jListRight);
				}
			});
		}
		return btnRight;
	}
	/**
	 * This method initializes btnLeft	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getBtnLeft() {
		if (btnLeft == null) {
			btnLeft = new JButton();
			btnLeft.setBounds(223, 132, 56, 25);
			btnLeft.setText("<");
			btnLeft.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					if(jListRight.getSelectedIndex()==-1){
						return;
					}
					addSelectedRecord(jListRight,jListLeft);
				}
			});
		}
		return btnLeft;
	}
	/**
	 * This method initializes btnLeftAll	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getBtnLeftAll() {
		if (btnLeftAll == null) {
			btnLeftAll = new JButton();
			btnLeftAll.setBounds(223, 168, 56, 25);
			btnLeftAll.setText("<<<");
			btnLeftAll.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					addAllRecord(jListRight,jListLeft);
				}
			});
		}
		return btnLeftAll;
	}
	
	
	/**
	 * 获得右边jList的数据源
	 * */
	public List getListData(){
		return convertVectorToList(this.getListData(this.jListRight));
	}
	
	
	/**
	 * 转换Vector到List
	 * */
	private List convertVectorToList(Vector vector){
		List list = new ArrayList();
		if(vector != null){
			list.addAll(vector);
		}
		return list;
	}
	
	/**
	 * 获得 jList所有对象数据
	 * */
	private Vector getListData(JList jList){
		int size = jList.getModel().getSize();
		Vector vector = new Vector();
		for(int i=0;i<size;i++){
			vector.add(jList.getModel().getElementAt(i));
		}
		return vector;
	}
	
	/**
	 * 填充数据到JList
	 * */
	private void fillListData(JList jList,int impexpflat){
		List list = 
			encAction.findBillListNoMakeCustomsDeclaration(
					new Request(CommonVars.getCurrUser()),impexpflat);
		if(list.size() > 0){
			Vector vector = new Vector();
			vector.addAll(list);
			jList.setListData(vector);
		}
	}
	
	/**
	 * 加入一个select JList 对象到另一个 JList
	 */
	private void addSelectedRecord(JList fromJList,JList toJList){
		Object[] selectDataSource = fromJList.getSelectedValues();		
		int[] indices = fromJList.getSelectedIndices();		
		if(indices.length<=0){
			return ;
		}
		Vector fromSource = getListData(fromJList);
		Vector toSource=  getListData(toJList);
		for(int i=indices.length-1;i>=0;i--){
			Object obj=fromJList.getModel().getElementAt(indices[i]);
			fromSource.remove(obj);
			toSource.add(obj);
		}
		fromJList.setListData(fromSource);
		toJList.setListData(toSource);
	}
	
	/**
	 * 加入一个JList 到另一个 JList 对象
	 */
	private void addAllRecord(JList fromJList ,JList toJList){
		Vector fromSource= new Vector();
		int fromSize = fromJList.getModel().getSize();
		int toSize = toJList.getModel().getSize();
		if(fromSize <= 0){
			return ;
		}
		Vector toSource = getListData(toJList);
		for(int i=0;i<fromSize;i++){
			toSource.add(fromJList.getModel().getElementAt(i));
		}
		fromJList.setListData(fromSource);
		toJList.setListData(toSource);
	}	
	
	
	/***
	 * 设置JList呈现类
	 */
	class ListCellRenderer extends DefaultListCellRenderer
	{
		public Component getListCellRendererComponent(JList list,
				Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index,
					isSelected, cellHasFocus);
				if (value != null) {
					setText(((ApplyToCustomsBillList)value).getListNo().toString());
				} else {
					setText("");
				}
				return this;
		}
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */    
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setBounds(15, 19, 195, 224);
			jScrollPane.setViewportView(getJListLeft());
			jScrollPane.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
		}
		return jScrollPane;
	}
	/**
	 * This method initializes jScrollPane1	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */    
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setBounds(292, 19, 194, 224);
			jScrollPane1.setViewportView(getJListRight());
			jScrollPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
		}
		return jScrollPane1;
	}
	/**
	 * @return Returns the impexpflat.
	 */
	public int getImpexpflat() {
		return impexpflat;
	}
	/**
	 * @param impexpflat The impexpflat to set.
	 */
	public void setImpexpflat(int impexpflat) {
		this.impexpflat = impexpflat;
	}
  }  //  @jve:decl-index=0:visual-constraint="10,10"
