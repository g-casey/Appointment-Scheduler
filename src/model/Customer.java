package model;

import java.sql.Timestamp;

/**
 * Customer
 * @author Gavin Casey
 */
public class Customer {
    private int id;
    private String name;
    private String address;
    private String postalCode;
    private String phoneNumber;
    private Timestamp createDate;
    private String createdBy;
    private Timestamp lastUpdated;
    private String lastUpdatedBy;
    private int divisionId;

    /**
     * Instantiates a new Customer.
     *
     * @param id            the id
     * @param name          the name
     * @param address       the address
     * @param postalCode    the postal code
     * @param phoneNumber   the phone number
     * @param createDate    the create date
     * @param createdBy     the created by
     * @param lastUpdated   the last updated
     * @param lastUpdatedBy the last updated by
     * @param divisionId    the division id
     */
    public Customer(int id, String name, String address, String postalCode, String phoneNumber, Timestamp createDate, String createdBy, Timestamp lastUpdated, String lastUpdatedBy, int divisionId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionId = divisionId;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Set id.
     *
     * @param id the id
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets address.
     *
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets address.
     *
     * @param address the address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets postal code.
     *
     * @return the postal code
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Sets postal code.
     *
     * @param postalCode the postal code
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Gets phone number.
     *
     * @return the phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets phone number.
     *
     * @param phoneNumber the phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets create date.
     *
     * @return the create date
     */
    public Timestamp getCreateDate() {
        return createDate;
    }

    /**
     * Sets create date.
     *
     * @param createDate the create date
     */
    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    /**
     * Gets created by.
     *
     * @return the created by
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets created by.
     *
     * @param createdBy the created by
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Gets last updated.
     *
     * @return the last updated
     */
    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    /**
     * Sets last updated.
     *
     * @param lastUpdated the last updated
     */
    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    /**
     * Gets last updated by.
     *
     * @return the last updated by
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Sets last updated by.
     *
     * @param lastUpdatedBy the last updated by
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Gets division id.
     *
     * @return the division id
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * Sets division id.
     *
     * @param divisionId the division id
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }
}
