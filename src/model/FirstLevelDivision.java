package model;

import java.sql.Timestamp;
import java.util.HashMap;

/**
 * FirstLevelDivision
 * @author Gavin Casey
 */
public class FirstLevelDivision {
    private int id;
    private String division;
    private Timestamp createDate;
    private String createdBy;
    private Timestamp lastUpdated;
    private String lastUpdatedBy;
    private int countryId;


    /**
     * Instantiates a new First level division.
     *
     * @param id            the id
     * @param division      the division
     * @param createDate    the create date
     * @param createdBy     the created by
     * @param lastUpdated   the last updated
     * @param lastUpdatedBy the last updated by
     * @param countryId     the country id
     */
    public FirstLevelDivision(int id, String division, Timestamp createDate, String createdBy, Timestamp lastUpdated, String lastUpdatedBy, int countryId) {
        this.id = id;
        this.division = division;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
        this.countryId = countryId;
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
     * Gets division.
     *
     * @return the division
     */
    public String getDivision() {
        return division;
    }

    /**
     * Sets division.
     *
     * @param division the division
     */
    public void setDivision(String division) {
        this.division = division;
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
     * Gets country id.
     *
     * @return the country id
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * Sets country id.
     *
     * @param countryId the country id
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }


    /**
     * Gets String representation of the FirstLevelDivision
     * @return
     */
    @Override
    public String toString(){
        return division;
    }
}
