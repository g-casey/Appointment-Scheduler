package model.DAO;

import database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import model.DAO.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Customer Database Access Object
 * @author Gavin Casey
 */

public class CustomerDAO implements DAO<Customer> {
    /**
     * List of all the customers in the database
     */
    private ObservableList<Customer> customerList = FXCollections.observableArrayList();
    /**
     * Database connection object
     */
    private Connection connection = DBConnection.getConnection();

    /**
     * Gets all the customers from the database
     */
    public CustomerDAO(){
        try{
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM customers;");
            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                Customer customer = new Customer(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getTimestamp(6),
                        rs.getString(7),
                        rs.getTimestamp(8),
                        rs.getString(9),
                        rs.getInt(10)
                );
                customerList.add(customer);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Gets a single customer from the list
     * @param id of the customer
     * @return the customer object
     */

    @Override
    public Customer get(int id) {
        return customerList.get(id);
    }

    /**
     * Gets the list of all the customers
     * @return list of all the customers
     */
    @Override
    public ObservableList<Customer> getAll() {
        return customerList;
    }

    /**
     * Creates a new customer and adds it to the database
     * @param customer to add
     */
    @Override
    public void create(Customer customer) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO customers(Customer_Name,Address,Postal_Code,Phone,Create_Date,Created_By,Last_Update,Last_Updated_By,Division_ID)" +
                    "VALUES(?,?,?,?,?,?,?,?,?);"
            );
            statement.setString(1,customer.getName());
            statement.setString(2,customer.getAddress());
            statement.setString(3,customer.getPostalCode());
            statement.setString(4,customer.getPhoneNumber());
            statement.setTimestamp(5,customer.getCreateDate());
            statement.setString(6, customer.getCreatedBy());
            statement.setTimestamp(7,customer.getLastUpdated());
            statement.setString(8,customer.getLastUpdatedBy());
            statement.setInt(9,customer.getDivisionId());
            statement.executeUpdate();

            ResultSet rs = connection.prepareStatement("SELECT Customer_ID FROM customers ORDER BY Customer_ID DESC LIMIT 1;").executeQuery();

            rs.next();
            customer.setId(rs.getInt("Customer_ID"));

            customerList.add(customer);


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Updates an existing customer in the database
     * @param index of the customer to update
     * @param customer to update
     */
    @Override
    public void update(int index, Customer customer) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE customers SET Customer_Name = ?,Address = ?,Postal_Code=?,Phone=?,Create_Date=?,Created_By=?,Last_Update=?,Last_Updated_By=?,Division_ID=? WHERE Customer_ID = ?;");
            statement.setString(1, customer.getName());
            statement.setString(2,customer.getAddress());
            statement.setString(3,customer.getPostalCode());
            statement.setString(4,customer.getPhoneNumber());
            statement.setTimestamp(5,customer.getCreateDate());
            statement.setString(6,customer.getCreatedBy());
            statement.setTimestamp(7,customer.getLastUpdated());
            statement.setString(8,customer.getLastUpdatedBy());
            statement.setInt(9,customer.getDivisionId());
            statement.setInt(10,index);

            statement.executeUpdate();

            for(int i = 0; i < customerList.size(); i++){
                if(customerList.get(i).getId() == index){
                    customerList.set(i,customer);
                }
            }



        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Deletes a customer from the database
     * @param customer to delete
     */
    @Override
    public void delete(Customer customer) {
        try{
            PreparedStatement statement = connection.prepareStatement("DELETE FROM customers WHERE Customer_ID = ?; ");
            statement.setInt(1,customer.getId());
            statement.executeUpdate();

            customerList.remove(customer);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
