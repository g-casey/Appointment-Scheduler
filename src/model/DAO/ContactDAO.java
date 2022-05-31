package model.DAO;

import database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Contact Database Access Object
 * @author Gavin Casey
 */

public class ContactDAO implements DAO<Contact>{

    /**
     * List of all the contacts
     */

    private ObservableList<Contact> contactList = FXCollections.observableArrayList();

    /**
     * Database connection object
     */
    private Connection connection = DBConnection.getConnection();


    /**
     * Gets the list of contacts from the database
     */
    public ContactDAO(){
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM contacts;");
            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                contactList.add(new Contact(
                   rs.getInt(1),
                   rs.getString(2),
                   rs.getString(3)
                ));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Gets a singular contact from the database
     * @param id of the contact to get
     * @return the contact
     */

    @Override
    public Contact get(int id) {
        return contactList.get(id);
    }

    /**
     * Gets the list of all the contacts from the database
     * @return list of all the contacts
     */
    @Override
    public ObservableList<Contact> getAll() {
        return contactList;
    }

    /**
     * Creates and adds a new contact to the database
     * @param contact to add
     */
    @Override
    public void create(Contact contact) {
        contactList.add(contact);
    }

    /**
     * Updates an existing contact in the database
     * @param index of the contact to update
     * @param contact to update
     */
    @Override
    public void update(int index, Contact contact) {
        contactList.set(index,contact);
    }

    /**
     * Deletes a contact from the database
     * @param contact to delete
     */

    @Override
    public void delete(Contact contact) {
        contactList.remove(contact);
    }
}
