package model.DAO;

import database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import model.FirstLevelDivision;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Database Access Object for FirstLevelDivisions
 * @author Gavin Casey
 */
public class FirstLevelDivisionDAO implements DAO<FirstLevelDivision> {
    /**
     * List of all the FirstLevelDivisions in the database
     */
    private ObservableList<FirstLevelDivision> firstLevelDivisionList = FXCollections.observableArrayList();
    /**
     * Database Connection object
     */
    private Connection connection = DBConnection.getConnection();


    /**
     * Gets list of all the FirstLevelDivisions in the database
     */
    public FirstLevelDivisionDAO() {
        try{
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM first_level_divisions;");
            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                FirstLevelDivision firstLevelDivision = new FirstLevelDivision(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getTimestamp(3),
                        rs.getString(4),
                        rs.getTimestamp(5),
                        rs.getString(6),
                        rs.getInt(7)
                );
                firstLevelDivisionList.add(firstLevelDivision);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Gets a single FirstLevelDivision from the database
     * @param id of the FirstLevelDivision to get
     * @return FirstLevelDivision
     */
    @Override
    public FirstLevelDivision get(int id) {
        return firstLevelDivisionList.get(id);
    }

    /**
     * Gets a list of all the FirstLevelDivisions in the database
     * @return list of all the FirstLevelDivisions in the database
     */

    @Override
    public ObservableList<FirstLevelDivision> getAll() {
        return firstLevelDivisionList;
    }

    /**
     * Creates a new FirstLevelDivision and adds it to the database
     * @param firstLevelDivision to add
     */
    @Override
    public void create(FirstLevelDivision firstLevelDivision) {
        firstLevelDivisionList.add(firstLevelDivision);
    }

    /**
     * Updates an existing FirstLevelDivision in the database
     * @param index of the FirstLevelDivision to update
     * @param firstLevelDivision to replace with
     */
    @Override
    public void update(int index, FirstLevelDivision firstLevelDivision) {
        firstLevelDivisionList.set(index, firstLevelDivision);
    }

    /**
     * Deletes a FirstLevelDivision from the database
     * @param firstLevelDivision to delete
     */

    @Override
    public void delete(FirstLevelDivision firstLevelDivision) {
        firstLevelDivisionList.remove(firstLevelDivision);
    }
}
