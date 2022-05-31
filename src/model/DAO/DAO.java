package model.DAO;

import javafx.collections.ObservableList;

import java.util.List;

/**
 * Database Access Object Interface
 * @param <DataType> Type of the Table in the database
 */
public interface DAO <DataType>{
    /**
     * Gets a single row from the database
     * @param id of the row to get
     * @return row object
     */
    DataType get(int id);

    /**
     * Gets a list of all the rows from the database
     * @return list of all the rows from the database
     */
    ObservableList<DataType> getAll();

    /**
     * Creates a new row and adds it to the database
     * @param d row to add
     */
    void create(DataType d);

    /**
     * Updates an existing row in the database
     * @param index of the row to update
     * @param d the new row object
     */
    void update(int index, DataType d);

    /**
     * Deletes a row from the database
     * @param d row to delete
     */
    void delete(DataType d);
}
