package model.DAO;

import database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import model.DAO.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Country Database Access Object
 */

public class CountryDAO implements DAO<Country> {

    /**
     * List of all the country objects in the database
     */

    private ObservableList<Country> countryList = FXCollections.observableArrayList();

    /**
     * Database connection object
     */
    private Connection connection = DBConnection.getConnection();

    /**
     * Gets the Countries from the database
     */
    public CountryDAO() {
        try{
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM countries;");
            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                Country country = new Country(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getTimestamp(3),
                        rs.getString(4),
                        rs.getTimestamp(5),
                        rs.getString(6)
                );
                countryList.add(country);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Gets a single country
     * @param id of the country
     * @return the country
     */
    @Override
    public Country get(int id) {
        return countryList.get(id);
    }

    /**
     * Gets all the countries
     * @return list of all the countries
     */
    @Override
    public ObservableList<Country> getAll() {
        return countryList;
    }

    /**
     * Creates a new country and adds it to the database
     * @param country
     */
    @Override
    public void create(Country country) {
        countryList.add(country);
    }

    /**
     * Updates an existing country in the database
     * @param index of the country
     * @param country to update
     */
    @Override
    public void update(int index, Country country) {
        countryList.set(index,country);
    }

    /**
     * Deletes a country from the database
     * @param country to delete
     */
    @Override
    public void delete(Country country) {
        countryList.remove(country);
    }
}
