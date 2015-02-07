package com.bestway.ui.winuicontrol;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

import com.bestway.client.windows.form.FmMain;

public class JFrameBaseHelper {
//  这个方法图标化(iconifies)一个Frame, 最大化位信息没有影响。
    // This method iconifies a frame; the maximized bits are not affected.
    public static void iconify(Frame frame) {
        int state = frame.getExtendedState();
    
        // 设置图标化(iconifies)位
        // Set the iconified bit
        state |= Frame.ICONIFIED;
    
        // 图标化Frame
        // Iconify the frame
        frame.setExtendedState(state);
    }
    
    // 这个方法恢复一个Frame, 最大化位信息没有影响。
    // This method deiconifies a frame; the maximized bits are not affected.
    public static void deiconify(Frame frame) {
        int state = frame.getExtendedState();
    
        // 清除图标化位信息
        // Clear the iconified bit
        state &= ~Frame.ICONIFIED;
    
        // 恢复Frame
        // Deiconify the frame
        frame.setExtendedState(state);
    }
    
    // 这个方法最小化Frame，图标化位信息没有影响。
    // This method minimizes a frame; the iconified bit is not affected
    public static void minimize(Frame frame) {
        int state = frame.getExtendedState();
    
        // 清除最大化位信息
        // Clear the maximized bits
        state &= ~Frame.MAXIMIZED_BOTH;
    
        // 最小化Frame
        // Maximize the frame
        frame.setExtendedState(state);
    }
    
    // 这个方法最大化Frame，图标化位信息没有影响。
    // This method maximize a frame; the iconified bit is not affected
    public static void maximize(Frame frame) {
        int state = frame.getExtendedState();
    
        // 设置最大化位信息
        // Set the maximized bits
        state |= Frame.MAXIMIZED_BOTH;
    
        // 最大化Frame
        // Maximize the frame
        frame.setExtendedState(state);
    }

    
    
    
    
    private static Map<Object,JFrameBase> storeFrameMap = new HashMap<Object,JFrameBase>();
    
    
    public static void putJDialogBaseToFlag(final String flag,final JFrameBase dialog){
        if(storeFrameMap.get(flag)!=null){
            return;
        }
        storeFrameMap.put(flag,dialog);
        dialog.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                storeFrameMap.remove(flag);
                //System.out.println(" close jframse base !!");
            }            
        });
    }
    
    public static JFrameBase getJFrameBaseByFlag(String flag){
        return storeFrameMap.get(flag);
    }
    
    
    

    
}
