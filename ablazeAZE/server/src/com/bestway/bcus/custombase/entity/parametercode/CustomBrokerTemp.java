package com.bestway.bcus.custombase.entity.parametercode;
/**
 * 报关行信息临时实体
 * @author lyh
 *
 */
public class CustomBrokerTemp {

    /**
     * 代码
     */
    private String code;
    /**
     * 名称
     */
   private String name;
   /**
    * 地址
    */
   private String address;
   /**
    * 联系人
    */
   private String linkMan;
   /**
    * 联系电话
    */
   private String phoneNum;

   public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
   
   
}
