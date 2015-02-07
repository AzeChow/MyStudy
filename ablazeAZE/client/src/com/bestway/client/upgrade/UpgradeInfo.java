
package com.bestway.client.upgrade;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for upgradeInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="upgradeInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="maxValue" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="messageBottom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="messageTop" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="note" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="upgradeError" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="upgradeSucceed" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "upgradeInfo", propOrder = {
    "maxValue",
    "messageBottom",
    "messageTop",
    "note",
    "upgradeError",
    "upgradeSucceed",
    "value"
})
public class UpgradeInfo {

    protected Integer maxValue;
    protected String messageBottom;
    protected String messageTop;
    protected String note;
    protected boolean upgradeError;
    protected boolean upgradeSucceed;
    protected Integer value;

    /**
     * Gets the value of the maxValue property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMaxValue() {
        return maxValue;
    }

    /**
     * Sets the value of the maxValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMaxValue(Integer value) {
        this.maxValue = value;
    }

    /**
     * Gets the value of the messageBottom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessageBottom() {
        return messageBottom;
    }

    /**
     * Sets the value of the messageBottom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessageBottom(String value) {
        this.messageBottom = value;
    }

    /**
     * Gets the value of the messageTop property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessageTop() {
        return messageTop;
    }

    /**
     * Sets the value of the messageTop property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessageTop(String value) {
        this.messageTop = value;
    }

    /**
     * Gets the value of the note property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNote() {
        return note;
    }

    /**
     * Sets the value of the note property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNote(String value) {
        this.note = value;
    }

    /**
     * Gets the value of the upgradeError property.
     * 
     */
    public boolean isUpgradeError() {
        return upgradeError;
    }

    /**
     * Sets the value of the upgradeError property.
     * 
     */
    public void setUpgradeError(boolean value) {
        this.upgradeError = value;
    }

    /**
     * Gets the value of the upgradeSucceed property.
     * 
     */
    public boolean isUpgradeSucceed() {
        return upgradeSucceed;
    }

    /**
     * Sets the value of the upgradeSucceed property.
     * 
     */
    public void setUpgradeSucceed(boolean value) {
        this.upgradeSucceed = value;
    }

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setValue(Integer value) {
        this.value = value;
    }

}
