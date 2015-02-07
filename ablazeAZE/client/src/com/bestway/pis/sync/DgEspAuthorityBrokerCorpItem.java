package com.bestway.pis.sync;

import java.awt.Rectangle;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.common.Request;
import com.bestway.pis.action.PisAction;
import com.bestway.pis.action.PisVerificationAuthority;
import com.bestway.pis.constant.EspMainBusinessType;
import com.bestway.pis.entity.EspAuthorityBrokerCorp;
import com.bestway.pis.entity.EspAuthorityBrokerCorpItem;
import com.bestway.ui.winuicontrol.JDialogBase;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;

import org.apache.commons.lang.StringUtils;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DgEspAuthorityBrokerCorpItem extends JDialogBase {
	private JLabel lblNewLabel;
	private JLabel lblIc;
	private JTextField tfIcCard;
	private JLabel label_1;
	private JButton btnSave;
	private JButton btnCancel;
	private JComboBox cbbOptionStatus;
	private JComboBox cbbMainBusiness;
	
	private EspAuthorityBrokerCorp head=null;
	private int state = DataState.BROWSE;
	private EspAuthorityBrokerCorpItem espCorpItem;
	
	List<EspAuthorityBrokerCorpItem> items = new ArrayList();
	private PisAction pisAction = null;
	private PisVerificationAuthority pisVerificationAuthority = null;
	public DgEspAuthorityBrokerCorpItem() {
		pisAction = (PisAction) CommonVars.getApplicationContext()
				.getBean("pisAction");
		pisVerificationAuthority = (PisVerificationAuthority) CommonVars
				.getApplicationContext().getBean("PisVerificationAuthority");
		this.setBounds(new Rectangle(400,270));
		getContentPane().setLayout(null);
		getContentPane().add(getLblNewLabel());
//		getContentPane().add(getLblIc());
//		getContentPane().add(getTfIcCard());
		getContentPane().add(getLabel_1());
		getContentPane().add(getBtnSave());
		getContentPane().add(getBtnCancel());
		getContentPane().add(getCbbOptionStatus());
		getContentPane().add(getCbbMainBusiness());
		
		initUIComponents();
		setState();
		fillData();
	}
	
	@Override
	public void setVisible(boolean b) {
		if(true){
			fillData();
		}
		super.setVisible(b);
	}
	
	private void initUIComponents(){
		cbbMainBusiness.addItem(new ItemProperty(EspMainBusinessType.BUSINESS_TYPE_SSZ,
                EspMainBusinessType.getNameByCode(EspMainBusinessType.BUSINESS_TYPE_SSZ)));
		cbbMainBusiness.addItem(new ItemProperty(EspMainBusinessType.BUSINESS_TYPE_DICT,
                EspMainBusinessType.getNameByCode(EspMainBusinessType.BUSINESS_TYPE_DICT)));
		cbbMainBusiness.addItem(new ItemProperty(EspMainBusinessType.BUSINESS_TYPE_CONTRACT,
                EspMainBusinessType.getNameByCode(EspMainBusinessType.BUSINESS_TYPE_CONTRACT)));
		cbbMainBusiness.addItem(new ItemProperty(EspMainBusinessType.BUSINESS_TYPE_EXP,
                EspMainBusinessType.getNameByCode(EspMainBusinessType.BUSINESS_TYPE_EXP)));
		cbbMainBusiness.addItem(new ItemProperty(EspMainBusinessType.BUSINESS_TYPE_IMP,
                EspMainBusinessType.getNameByCode(EspMainBusinessType.BUSINESS_TYPE_IMP)));
		cbbMainBusiness.addItem(new ItemProperty(EspMainBusinessType.BUSINESS_TYPE_CAV,
                EspMainBusinessType.getNameByCode(EspMainBusinessType.BUSINESS_TYPE_CAV)));
		cbbMainBusiness.addItem(new ItemProperty(EspMainBusinessType.BUSINESS_TYPE_GENERALTRADE,
                EspMainBusinessType.getNameByCode(EspMainBusinessType.BUSINESS_TYPE_GENERALTRADE)));
		cbbMainBusiness.addItem(new ItemProperty(EspMainBusinessType.BUSINESS_TYPE_FPT,
                EspMainBusinessType.getNameByCode(EspMainBusinessType.BUSINESS_TYPE_FPT)));
		
		
		
		cbbOptionStatus.addItem(new ItemProperty(String.valueOf(DataState.BROWSE),
				DataState.getNameByCode(DataState.BROWSE)));  
		cbbOptionStatus.addItem(new ItemProperty(String.valueOf(DataState.ADD),
				DataState.getNameByCode(DataState.ADD)));
		cbbOptionStatus.addItem(new ItemProperty(String.valueOf(DataState.EDIT),
				DataState.getNameByCode(DataState.EDIT)));
		
	}
	
	private void fillData(){
		if(state==DataState.EDIT&&espCorpItem!=null){
			cbbOptionStatus.setSelectedItem(new ItemProperty(espCorpItem.getOptionStatus(),
					DataState.getNameByCode(Integer.parseInt(espCorpItem.getOptionStatus()))));
			cbbMainBusiness.setSelectedItem(new ItemProperty(espCorpItem.getMainBusiness(),
					EspMainBusinessType.getNameByCode(espCorpItem.getMainBusiness())));
//			tfIcCard.setText(espCorpItem.getIcCard());
		}
	}
	
	private void setState(){
		cbbOptionStatus.setEditable(state==DataState.BROWSE);
		cbbMainBusiness.setEditable(state==DataState.BROWSE);
//		tfIcCard.setEditable(state==DataState.BROWSE);
	}
	
	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("主营业务");
			lblNewLabel.setBounds(43, 53, 54, 15);
		}
		return lblNewLabel;
	}
	private JLabel getLblIc() {
		if (lblIc == null) {
			lblIc = new JLabel("IC卡号");
			lblIc.setBounds(43, 95, 54, 15);
		}
		return lblIc;
	}
	private JTextField getTfIcCard() {
		if (tfIcCard == null) {
			tfIcCard = new JTextField();
			tfIcCard.setColumns(10);
			tfIcCard.setBounds(107, 92, 202, 21);
		}
		return tfIcCard;
	}
	private JLabel getLabel_1() {
		if (label_1 == null) {
			label_1 = new JLabel("操作授权");
			label_1.setBounds(43, 140, 54, 15);
		}
		return label_1;
	}
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton("保存");
			btnSave.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					pisVerificationAuthority.checkSaveThird(new Request(CommonVars.getCurrUser()));
					if(state==DataState.ADD){
						espCorpItem = new EspAuthorityBrokerCorpItem();
						espCorpItem.setEspAuthorityBrokerCorp(head);
					}
//					espCorpItem.setIcCard(tfIcCard.getText());
					espCorpItem.setMainBusiness(((ItemProperty)cbbMainBusiness.getSelectedItem()).getCode());
					espCorpItem.setOptionStatus(((ItemProperty)cbbOptionStatus.getSelectedItem()).getCode());
					
			        List<EspAuthorityBrokerCorpItem> modifyList = new ArrayList<EspAuthorityBrokerCorpItem>();
			        modifyList.add(espCorpItem);
			        //校验保存信息
			        if (!checkCompanyBrokerCorpItem(modifyList)) {
			            return;
			        }
			        //校验主营业务唯一
			        if (!checkUnique(items)) {
			            return;
			        }

			        if (modifyList != null && modifyList.size() > 0) {
			            pisAction.saveEspAuthorityBrokerCorpItem(new Request(CommonVars.getCurrUser()), modifyList);
			        }
			        
			        dispose();
				}
			});
			btnSave.setBounds(75, 201, 93, 23);
		}
		return btnSave;
	}
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton("取消");
			btnCancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			btnCancel.setBounds(198, 201, 93, 23);
		}
		return btnCancel;
	}
	private JComboBox getCbbOptionStatus() {
		if (cbbOptionStatus == null) {
			cbbOptionStatus = new JComboBox();
			cbbOptionStatus.setBounds(107, 137, 202, 21);
		}
		return cbbOptionStatus;
	}
	private JComboBox getCbbMainBusiness() {
		if (cbbMainBusiness == null) {
			cbbMainBusiness = new JComboBox();
			cbbMainBusiness.setBounds(107, 50, 202, 21);
		}
		return cbbMainBusiness;
	}
	
	public List<EspAuthorityBrokerCorpItem> getItems() {
		return items;
	}

	public void setItems(List<EspAuthorityBrokerCorpItem> item) {
		this.items = item;
	}
	
	/**
     * 授权代理公司明细信息保存校验
     *
     * @return
     */
    private boolean checkCompanyBrokerCorpItem(List<EspAuthorityBrokerCorpItem> companyBrokerCorpItemList) {
        boolean flag = Boolean.TRUE;
        Map<String, EspAuthorityBrokerCorpItem> mapCache = new HashMap<String, EspAuthorityBrokerCorpItem>();
//        EspAuthorityBrokerCorp cbc = (EspAuthorityBrokerCorp) tableModelAuthorityBrokerCorp.getCurrentRow();
        List list = this.pisAction.findEspAuthorityBrokerCorpItem(new Request(CommonVars.getCurrUser()), head);
        list.remove(espCorpItem);
        for (Object obj : list) {
            EspAuthorityBrokerCorpItem item = (EspAuthorityBrokerCorpItem) obj;
            String key = item.getEspAuthorityBrokerCorp().getBrokerCorp().getOrgaCode() + "/" + item.getMainBusiness() + "/" + item.getOptionStatus();
            mapCache.put(key, item);
        }
        for (EspAuthorityBrokerCorpItem companyBrokerCorpItem : companyBrokerCorpItemList) {
            if (StringUtils.isBlank(companyBrokerCorpItem.getMainBusiness())
                    || StringUtils.isBlank(companyBrokerCorpItem.getOptionStatus())) {
                JOptionPane.showMessageDialog(DgEspAuthorityBrokerCorpItem.this, "保存数据不能为空", "提示", JOptionPane.INFORMATION_MESSAGE);
                flag = Boolean.FALSE;
                break;
            }
            String key1 = companyBrokerCorpItem.getEspAuthorityBrokerCorp().getBrokerCorp().getOrgaCode() + "/" + companyBrokerCorpItem.getMainBusiness() + "/" + companyBrokerCorpItem.getOptionStatus();
            if (null != mapCache.get(key1)) {
                JOptionPane.showMessageDialog(DgEspAuthorityBrokerCorpItem.this, "保存数据已存在!", "提示", JOptionPane.INFORMATION_MESSAGE);
                flag = Boolean.FALSE;
                break;
            }
        }

        return flag;
    }
    
    private boolean checkUnique(List<EspAuthorityBrokerCorpItem> companyBrokerCorpItemList) {
        boolean flag = Boolean.TRUE;
        Map<String, Object> contains = new HashMap<String, Object>();
        companyBrokerCorpItemList.remove(espCorpItem);
        for (EspAuthorityBrokerCorpItem companyBrokerCorpItem : companyBrokerCorpItemList) {
            if (companyBrokerCorpItem.getMainBusiness() != null && contains.containsKey(companyBrokerCorpItem.getMainBusiness())) {
                JOptionPane.showMessageDialog(DgEspAuthorityBrokerCorpItem.this, "主营业务不能重复", "提示", JOptionPane.INFORMATION_MESSAGE);
                flag = Boolean.FALSE;
                break;
            }
            contains.put(companyBrokerCorpItem.getMainBusiness(), "");
        }
        
        if(contains.containsKey(espCorpItem.getMainBusiness())){
        	JOptionPane.showMessageDialog(DgEspAuthorityBrokerCorpItem.this, "主营业务不能重复", "提示", JOptionPane.INFORMATION_MESSAGE);
            flag = Boolean.FALSE;
        }
        return flag;
    }

	public EspAuthorityBrokerCorp getCbc() {
		return head;
	}

	public void setCbc(EspAuthorityBrokerCorp cbc) {
		this.head = cbc;
	}

	public EspAuthorityBrokerCorpItem getEspCorpItem() {
		return espCorpItem;
	}

	public void setEspCorpItem(EspAuthorityBrokerCorpItem item) {
		this.espCorpItem = item;
	}
	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
}
