/*
 * Created on 2005-7-19
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.erpbillcenter.parameter;

import java.awt.Component;
import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.ui.winuicontrol.JPanelBase;

/**
 * @author ls
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PnErpBillParameterCommon extends JPanelBase {

	protected CasAction casAction = null;
    
	/**
	 * This is the default constructor
	 */
	public PnErpBillParameterCommon() {
		super();
        try{
        casAction = (CasAction) CommonVars.getApplicationContext().getBean(
        "casAction");
        }catch(Exception ex){
            
        }
	}
	
    
    /**
     * 关闭窗体
     *
     */
    protected void close(){
        JInternalFrame frame = (JInternalFrame)getComponent(this);
        if(frame != null){
            frame.doDefaultCloseAction();
        }
    }
    
    private Component getComponent(Component component){
        if(component instanceof JInternalFrame){
            return component;
        }
        if(component == null){
            return null;
        }
        return getComponent(component.getParent());
    }
    
    
    
    
    /**
     * 设置容器除JLabel外所有控件的enabled属性
     * */
    protected void setContainerEnabled(Container container,boolean isFlag){
        for(int i=0;i<container.getComponentCount();i++){
            Component component = container.getComponent(i); 
            if(!(component instanceof JLabel) && !(component instanceof JButton)){
                component.setEnabled(isFlag);
                if(component instanceof Container){
                    setContainerEnabled((Container)component,isFlag);
                }
            }
        }
    }
    
    
    
    
	
}  //  @jve:decl-index=0:visual-constraint="10,10"
