package model.DAO;

import database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.DAO.DAO;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * User Database Access Object
 * @author Gavin Casey
 */
public class UserDAO implements DAO<User> {

    /**
     * List of all the Users in the database
     */
    private ObservableList<User> userList = FXCollections.observableArrayList();

    /**
     * Gets all the users in the database to populate the list
     */
    public UserDAO(){
        Connection connection = DBConnection.getConnection();
        try{
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users;");
            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                User user = new User(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getTimestamp(4),
                        rs.getString(5),
                        rs.getTimestamp(6),
                        rs.getString(7)
                );
                userList.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Gets a single User from the database
     * @param id of the User to get
     * @return the User
     */
    @Override
    public User get(int id) {
        return userList.get(id);
    }

    /**
     * Gets list of all the Users from the database
     * @return list of all the Users from the database
     */
    @Override
    public ObservableList<User> getAll() {
        return userList;
    }

    /**
     * Creates a new User and adds it to the database
     * @param user to add
     */

    @Override
    public void create(User user){
        userList.add(user);
    }

    /**
     * Updates an existing User in the database
     * @param index of the User to update
     * @param user to update
     */
    @Override
    public void update(int index, User user) {
        userList.set(index, user);
    }

    /**
     * Delets a User from the database
     * @param user to delete
     */
    @Override
    public void delete(User user) {
        userList.remove(user);
    }
}
