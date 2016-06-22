package com.nkang.kxmoment.service;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for organization complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="organization">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="domesticUltimateDUNSName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="domesticUltimateDUNSNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="domesticUltimateOrganizationId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="orgDynamicProfile" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="dynamicProfile" type="{http://webservices.partnerlookup.mdm.it.hp.com/}dynamicProfile" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="orgExtendedProfile" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="extendedProfile" type="{http://webservices.partnerlookup.mdm.it.hp.com/}extendedProfile" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="globalUltimateDUNSName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="globalUltimateDUNSNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="globalUltimateOrganizationId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="opsCustomerAccountFlag" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="opsPartnerAccountFlag" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="orgCustomerAccountFlag" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="orgOutOfBusinessIndicator" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="orgPartnerAccountFlag" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="organizationCountryCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="organizationCountryName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="organizationDUNSNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="organizationDeletionIndicator" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="organizationExtendedName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="organizationId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="organizationLegalName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="organizationNonLatinExtendedName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="organizationNonLatinLegalName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="organizationNonLatinReportingName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="organizationReportingName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="parentOrganizationId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="parentOrganizationName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="standardAccountFlag" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="taxIdentifier" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="tradeStyleName1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="tradeStyleName2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="tradeStyleName3" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="tradeStyleName4" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="tradeStyleName5" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "organization", propOrder = {
    "domesticUltimateDUNSName",
    "domesticUltimateDUNSNumber",
    "domesticUltimateOrganizationId",
    "orgDynamicProfile",
    "orgExtendedProfile",
    "globalUltimateDUNSName",
    "globalUltimateDUNSNumber",
    "globalUltimateOrganizationId",
    "opsCustomerAccountFlag",
    "opsPartnerAccountFlag",
    "orgCustomerAccountFlag",
    "orgOutOfBusinessIndicator",
    "orgPartnerAccountFlag",
    "organizationCountryCode",
    "organizationCountryName",
    "organizationDUNSNumber",
    "organizationDeletionIndicator",
    "organizationExtendedName",
    "organizationId",
    "organizationLegalName",
    "organizationNonLatinExtendedName",
    "organizationNonLatinLegalName",
    "organizationNonLatinReportingName",
    "organizationReportingName",
    "parentOrganizationId",
    "parentOrganizationName",
    "standardAccountFlag",
    "taxIdentifier",
    "tradeStyleName1",
    "tradeStyleName2",
    "tradeStyleName3",
    "tradeStyleName4",
    "tradeStyleName5"
})
public class Organization {

    @XmlElement(required = true, nillable = true)
    protected String domesticUltimateDUNSName;
    @XmlElement(required = true, nillable = true)
    protected String domesticUltimateDUNSNumber;
    @XmlElement(required = true, type = Long.class, nillable = true)
    protected Long domesticUltimateOrganizationId;
    //protected Organization.OrgDynamicProfile orgDynamicProfile;
    //protected Organization.OrgExtendedProfile orgExtendedProfile;
    @XmlElement(required = true, nillable = true)
    protected String globalUltimateDUNSName;
    @XmlElement(required = true, nillable = true)
    protected String globalUltimateDUNSNumber;
    @XmlElement(required = true, type = Long.class, nillable = true)
    protected Long globalUltimateOrganizationId;
    @XmlElement(required = true, type = Boolean.class, nillable = true)
    protected Boolean opsCustomerAccountFlag;
    @XmlElement(required = true, type = Boolean.class, nillable = true)
    protected Boolean opsPartnerAccountFlag;
    @XmlElement(required = true, type = Boolean.class, nillable = true)
    protected Boolean orgCustomerAccountFlag;
    @XmlElement(required = true, type = Boolean.class, nillable = true)
    protected Boolean orgOutOfBusinessIndicator;
    @XmlElement(required = true, type = Boolean.class, nillable = true)
    protected Boolean orgPartnerAccountFlag;
    @XmlElement(required = true, nillable = true)
    protected String organizationCountryCode;
    @XmlElement(required = true, nillable = true)
    protected String organizationCountryName;
    @XmlElement(required = true, nillable = true)
    protected String organizationDUNSNumber;
    @XmlElement(required = true, type = Boolean.class, nillable = true)
    protected Boolean organizationDeletionIndicator;
    @XmlElement(required = true, nillable = true)
    protected String organizationExtendedName;
    @XmlElement(required = true, type = Long.class, nillable = true)
    protected Long organizationId;
    @XmlElement(required = true, nillable = true)
    protected String organizationLegalName;
    @XmlElement(required = true, nillable = true)
    protected String organizationNonLatinExtendedName;
    @XmlElement(required = true, nillable = true)
    protected String organizationNonLatinLegalName;
    @XmlElement(required = true, nillable = true)
    protected String organizationNonLatinReportingName;
    @XmlElement(required = true, nillable = true)
    protected String organizationReportingName;
    @XmlElement(required = true, type = Long.class, nillable = true)
    protected Long parentOrganizationId;
    @XmlElement(required = true, nillable = true)
    protected String parentOrganizationName;
    @XmlElement(required = true, type = Boolean.class, nillable = true)
    protected Boolean standardAccountFlag;
    @XmlElement(required = true, nillable = true)
    protected String taxIdentifier;
    @XmlElement(required = true, nillable = true)
    protected String tradeStyleName1;
    @XmlElement(required = true, nillable = true)
    protected String tradeStyleName2;
    @XmlElement(required = true, nillable = true)
    protected String tradeStyleName3;
    @XmlElement(required = true, nillable = true)
    protected String tradeStyleName4;
    @XmlElement(required = true, nillable = true)
    protected String tradeStyleName5;

    /**
     * Gets the value of the domesticUltimateDUNSName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDomesticUltimateDUNSName() {
        return domesticUltimateDUNSName;
    }

    /**
     * Sets the value of the domesticUltimateDUNSName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDomesticUltimateDUNSName(String value) {
        this.domesticUltimateDUNSName = value;
    }

    /**
     * Gets the value of the domesticUltimateDUNSNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDomesticUltimateDUNSNumber() {
        return domesticUltimateDUNSNumber;
    }

    /**
     * Sets the value of the domesticUltimateDUNSNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDomesticUltimateDUNSNumber(String value) {
        this.domesticUltimateDUNSNumber = value;
    }

    /**
     * Gets the value of the domesticUltimateOrganizationId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getDomesticUltimateOrganizationId() {
        return domesticUltimateOrganizationId;
    }

    /**
     * Sets the value of the domesticUltimateOrganizationId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setDomesticUltimateOrganizationId(Long value) {
        this.domesticUltimateOrganizationId = value;
    }

    /**
     * Gets the value of the orgDynamicProfile property.
     * 
     * @return
     *     possible object is
     *     {@link Organization.OrgDynamicProfile }
     *     
     */
/*    public Organization.OrgDynamicProfile getOrgDynamicProfile() {
        return orgDynamicProfile;
    }

    *//**
     * Sets the value of the orgDynamicProfile property.
     * 
     * @param value
     *     allowed object is
     *     {@link Organization.OrgDynamicProfile }
     *     
     *//*
    public void setOrgDynamicProfile(Organization.OrgDynamicProfile value) {
        this.orgDynamicProfile = value;
    }*/

    /**
     * Gets the value of the orgExtendedProfile property.
     * 
     * @return
     *     possible object is
     *     {@link Organization.OrgExtendedProfile }
     *     
     *//*
    public Organization.OrgExtendedProfile getOrgExtendedProfile() {
        return orgExtendedProfile;
    }

    *//**
     * Sets the value of the orgExtendedProfile property.
     * 
     * @param value
     *     allowed object is
     *     {@link Organization.OrgExtendedProfile }
     *     
     *//*
    public void setOrgExtendedProfile(Organization.OrgExtendedProfile value) {
        this.orgExtendedProfile = value;
    }
*/
    /**
     * Gets the value of the globalUltimateDUNSName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGlobalUltimateDUNSName() {
        return globalUltimateDUNSName;
    }

    /**
     * Sets the value of the globalUltimateDUNSName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGlobalUltimateDUNSName(String value) {
        this.globalUltimateDUNSName = value;
    }

    /**
     * Gets the value of the globalUltimateDUNSNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGlobalUltimateDUNSNumber() {
        return globalUltimateDUNSNumber;
    }

    /**
     * Sets the value of the globalUltimateDUNSNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGlobalUltimateDUNSNumber(String value) {
        this.globalUltimateDUNSNumber = value;
    }

    /**
     * Gets the value of the globalUltimateOrganizationId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getGlobalUltimateOrganizationId() {
        return globalUltimateOrganizationId;
    }

    /**
     * Sets the value of the globalUltimateOrganizationId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setGlobalUltimateOrganizationId(Long value) {
        this.globalUltimateOrganizationId = value;
    }

    /**
     * Gets the value of the opsCustomerAccountFlag property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isOpsCustomerAccountFlag() {
        return opsCustomerAccountFlag;
    }

    /**
     * Sets the value of the opsCustomerAccountFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setOpsCustomerAccountFlag(Boolean value) {
        this.opsCustomerAccountFlag = value;
    }

    /**
     * Gets the value of the opsPartnerAccountFlag property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isOpsPartnerAccountFlag() {
        return opsPartnerAccountFlag;
    }

    /**
     * Sets the value of the opsPartnerAccountFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setOpsPartnerAccountFlag(Boolean value) {
        this.opsPartnerAccountFlag = value;
    }

    /**
     * Gets the value of the orgCustomerAccountFlag property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isOrgCustomerAccountFlag() {
        return orgCustomerAccountFlag;
    }

    /**
     * Sets the value of the orgCustomerAccountFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setOrgCustomerAccountFlag(Boolean value) {
        this.orgCustomerAccountFlag = value;
    }

    /**
     * Gets the value of the orgOutOfBusinessIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isOrgOutOfBusinessIndicator() {
        return orgOutOfBusinessIndicator;
    }

    /**
     * Sets the value of the orgOutOfBusinessIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setOrgOutOfBusinessIndicator(Boolean value) {
        this.orgOutOfBusinessIndicator = value;
    }

    /**
     * Gets the value of the orgPartnerAccountFlag property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isOrgPartnerAccountFlag() {
        return orgPartnerAccountFlag;
    }

    /**
     * Sets the value of the orgPartnerAccountFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setOrgPartnerAccountFlag(Boolean value) {
        this.orgPartnerAccountFlag = value;
    }

    /**
     * Gets the value of the organizationCountryCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrganizationCountryCode() {
        return organizationCountryCode;
    }

    /**
     * Sets the value of the organizationCountryCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrganizationCountryCode(String value) {
        this.organizationCountryCode = value;
    }

    /**
     * Gets the value of the organizationCountryName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrganizationCountryName() {
        return organizationCountryName;
    }

    /**
     * Sets the value of the organizationCountryName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrganizationCountryName(String value) {
        this.organizationCountryName = value;
    }

    /**
     * Gets the value of the organizationDUNSNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrganizationDUNSNumber() {
        return organizationDUNSNumber;
    }

    /**
     * Sets the value of the organizationDUNSNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrganizationDUNSNumber(String value) {
        this.organizationDUNSNumber = value;
    }

    /**
     * Gets the value of the organizationDeletionIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isOrganizationDeletionIndicator() {
        return organizationDeletionIndicator;
    }

    /**
     * Sets the value of the organizationDeletionIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setOrganizationDeletionIndicator(Boolean value) {
        this.organizationDeletionIndicator = value;
    }

    /**
     * Gets the value of the organizationExtendedName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrganizationExtendedName() {
        return organizationExtendedName;
    }

    /**
     * Sets the value of the organizationExtendedName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrganizationExtendedName(String value) {
        this.organizationExtendedName = value;
    }

    /**
     * Gets the value of the organizationId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getOrganizationId() {
        return organizationId;
    }

    /**
     * Sets the value of the organizationId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setOrganizationId(Long value) {
        this.organizationId = value;
    }

    /**
     * Gets the value of the organizationLegalName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrganizationLegalName() {
        return organizationLegalName;
    }

    /**
     * Sets the value of the organizationLegalName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrganizationLegalName(String value) {
        this.organizationLegalName = value;
    }

    /**
     * Gets the value of the organizationNonLatinExtendedName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrganizationNonLatinExtendedName() {
        return organizationNonLatinExtendedName;
    }

    /**
     * Sets the value of the organizationNonLatinExtendedName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrganizationNonLatinExtendedName(String value) {
        this.organizationNonLatinExtendedName = value;
    }

    /**
     * Gets the value of the organizationNonLatinLegalName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrganizationNonLatinLegalName() {
        return organizationNonLatinLegalName;
    }

    /**
     * Sets the value of the organizationNonLatinLegalName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrganizationNonLatinLegalName(String value) {
        this.organizationNonLatinLegalName = value;
    }

    /**
     * Gets the value of the organizationNonLatinReportingName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrganizationNonLatinReportingName() {
        return organizationNonLatinReportingName;
    }

    /**
     * Sets the value of the organizationNonLatinReportingName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrganizationNonLatinReportingName(String value) {
        this.organizationNonLatinReportingName = value;
    }

    /**
     * Gets the value of the organizationReportingName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrganizationReportingName() {
        return organizationReportingName;
    }

    /**
     * Sets the value of the organizationReportingName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrganizationReportingName(String value) {
        this.organizationReportingName = value;
    }

    /**
     * Gets the value of the parentOrganizationId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getParentOrganizationId() {
        return parentOrganizationId;
    }

    /**
     * Sets the value of the parentOrganizationId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setParentOrganizationId(Long value) {
        this.parentOrganizationId = value;
    }

    /**
     * Gets the value of the parentOrganizationName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParentOrganizationName() {
        return parentOrganizationName;
    }

    /**
     * Sets the value of the parentOrganizationName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParentOrganizationName(String value) {
        this.parentOrganizationName = value;
    }

    /**
     * Gets the value of the standardAccountFlag property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isStandardAccountFlag() {
        return standardAccountFlag;
    }

    /**
     * Sets the value of the standardAccountFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setStandardAccountFlag(Boolean value) {
        this.standardAccountFlag = value;
    }

    /**
     * Gets the value of the taxIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaxIdentifier() {
        return taxIdentifier;
    }

    /**
     * Sets the value of the taxIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaxIdentifier(String value) {
        this.taxIdentifier = value;
    }

    /**
     * Gets the value of the tradeStyleName1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTradeStyleName1() {
        return tradeStyleName1;
    }

    /**
     * Sets the value of the tradeStyleName1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTradeStyleName1(String value) {
        this.tradeStyleName1 = value;
    }

    /**
     * Gets the value of the tradeStyleName2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTradeStyleName2() {
        return tradeStyleName2;
    }

    /**
     * Sets the value of the tradeStyleName2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTradeStyleName2(String value) {
        this.tradeStyleName2 = value;
    }

    /**
     * Gets the value of the tradeStyleName3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTradeStyleName3() {
        return tradeStyleName3;
    }

    /**
     * Sets the value of the tradeStyleName3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTradeStyleName3(String value) {
        this.tradeStyleName3 = value;
    }

    /**
     * Gets the value of the tradeStyleName4 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTradeStyleName4() {
        return tradeStyleName4;
    }

    /**
     * Sets the value of the tradeStyleName4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTradeStyleName4(String value) {
        this.tradeStyleName4 = value;
    }

    /**
     * Gets the value of the tradeStyleName5 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTradeStyleName5() {
        return tradeStyleName5;
    }

    /**
     * Sets the value of the tradeStyleName5 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTradeStyleName5(String value) {
        this.tradeStyleName5 = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="dynamicProfile" type="{http://webservices.partnerlookup.mdm.it.hp.com/}dynamicProfile" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
/*    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "dynamicProfile"
    })
    public static class OrgDynamicProfile {

        protected List<DynamicProfile> dynamicProfile;

        *//**
         * Gets the value of the dynamicProfile property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the dynamicProfile property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDynamicProfile().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link DynamicProfile }
         * 
         * 
         *//*
        public List<DynamicProfile> getDynamicProfile() {
            if (dynamicProfile == null) {
                dynamicProfile = new ArrayList<DynamicProfile>();
            }
            return this.dynamicProfile;
        }

    }*/


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="extendedProfile" type="{http://webservices.partnerlookup.mdm.it.hp.com/}extendedProfile" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
/*    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "extendedProfile"
    })
    public static class OrgExtendedProfile {

        protected List<ExtendedProfile> extendedProfile;

        *//**
         * Gets the value of the extendedProfile property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the extendedProfile property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getExtendedProfile().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ExtendedProfile }
         * 
         * 
         *//*
        public List<ExtendedProfile> getExtendedProfile() {
            if (extendedProfile == null) {
                extendedProfile = new ArrayList<ExtendedProfile>();
            }
            return this.extendedProfile;
        }

    }*/

}