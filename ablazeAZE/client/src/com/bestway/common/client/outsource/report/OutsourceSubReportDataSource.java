package com.bestway.common.client.outsource.report;

import java.util.ArrayList;
import java.util.List;

import com.bestway.bcus.client.common.CustomReportDataSource;

public class OutsourceSubReportDataSource extends CustomReportDataSource {

    private static List subReportDataByConsigner = null;
    private static List subReportDataByConsignee= null;
    
    private OutsourceSubReportDataSource(List list) {
        super(list);
    }

    /**
     * 获得数据源来自委外明细
     */
    public static OutsourceSubReportDataSource getDataSourceByConsigner(
            String parentCommCode) {
        if(subReportDataByConsigner == null){
            return null;
        }
        parentCommCode = parentCommCode .toUpperCase().trim() ; 
        List list = new ArrayList();
        for(int i=0;i<subReportDataByConsigner.size() ;i++){
            
        }        
        return new OutsourceSubReportDataSource(list);
    }

    /**
     * 获得数据源来自受托明细
     */
    public static OutsourceSubReportDataSource getDataSourceByConsignee(
            String parentCommCode) {
        if(subReportDataByConsignee == null){
            return null;
        }   
        parentCommCode = parentCommCode .toUpperCase().trim() ; 
        List list = new ArrayList();
        for(int i=0;i<subReportDataByConsignee.size() ;i++){
            
        }        
        return new OutsourceSubReportDataSource(list);
    }
    
    /**
     * @param subReportDataByConsigner The subReportDataByConsigner to set.
     */
    public static void setSubReportDataByConsigner(List subReportDataByConsigner) {
        OutsourceSubReportDataSource.subReportDataByConsigner = subReportDataByConsigner;
    }
    /**
     * @param subReportDataByConsignee The subReportDataByConsignee to set.
     */
    public static void setSubReportDataByConsignee(List subReportDataByConsignee) {
        OutsourceSubReportDataSource.subReportDataByConsignee = subReportDataByConsignee;
    }
}