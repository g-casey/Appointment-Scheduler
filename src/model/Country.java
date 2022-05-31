package model;


import java.sql.Timestamp;

/**
 * Country
 */
public class Country {
    private int id;
    private String country;
    private Timestamp createDate;
    private String createdBy;
    private Timestamp lastUpdated;
    private String lastUpdatedBy;

    /**
     * Instantiates a new Country.
     *
     * @param id            the id
     * @param country       the country
     * @param createDate    the create date
     * @param createdBy     the created by
     * @param lastUpdated   the last updated
     * @param lastUpdatedBy the last updated by
     */
    public Country(int id, String country, Timestamp createDate, String createdBy, Timestamp lastUpdated, String lastUpdatedBy) {
        this.id = id;
        this.country = country;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
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
     * Gets country.
     *
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets country.
     *
     * @param country the country
     */
    public void setCountry(String country) {
        this.country = country;
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
     * Sets the String value of the Country as shown in dropdowns
     * @return
     */
    @Override
    public String toString() {
        return country;
    }
}
