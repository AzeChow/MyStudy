package com.bestway.bcus.client.common;

import java.util.TimerTask;

public class ProgressTask extends TimerTask{
    boolean isClose = false;
    
    public void run() {
        if(isClose==false){
            this.setClientTipMessage();
        }       
    }
    
    protected void setClientTipMessage(){        
    
    }
    
    
    public void setProgressTaskClose(){
        isClose = true;
    }
        
}
