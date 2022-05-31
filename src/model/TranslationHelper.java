package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.DAO.CountryDAO;
import model.DAO.FirstLevelDivisionDAO;

import java.util.HashMap;

/**
 * Translation Helper to provide different translations between Country and FirstLevelDivisions
 * @author Gavin Casey
 */
public class TranslationHelper {
    /**
     * Map of CountryId to Country
     */
    private static HashMap<Integer,Country> countryMap = new HashMap<Integer,Country>();
    /**
     * Map of FirstLevelDivisionId to FirstLevelDivision
     */
    private static HashMap<Integer,FirstLevelDivision> divisionMap = new HashMap<Integer,FirstLevelDivision>();

    /**
     * Map of Country to list of FirstLevelDivision
     */

    private static HashMap<Country,ObservableList<FirstLevelDivision>> countryDivisionMap = new HashMap<Country,ObservableList<FirstLevelDivision>>();


    /**
     * Initialize the values of the maps
     */
    public static void initialize(){
        ObservableList<Country> countryList = new CountryDAO().getAll();
        ObservableList<FirstLevelDivision> divisionList = new FirstLevelDivisionDAO().getAll();



        for(Country country : countryList){
            ObservableList<FirstLevelDivision> filteredDivisionList = FXCollections.observableArrayList();
            for (FirstLevelDivision division : divisionList) {
                if(division.getCountryId() == country.getId()){
                    countryMap.put(division.getId(),country);
                    filteredDivisionList.add(division);
                }
                divisionMap.put(division.getId(),division);
            }
            countryDivisionMap.put(country,filteredDivisionList);
        }
    }

    /**
     * Gets country division map.
     *
     * @return the country division map
     */
    public static HashMap<Country, ObservableList<FirstLevelDivision>> getCountryDivisionMap() {
        return countryDivisionMap;
    }

    /**
     * Gets division map.
     *
     * @return the division map
     */
    public static HashMap<Integer, FirstLevelDivision> getDivisionMap() {
        return divisionMap;
    }

    /**
     * Gets country map.
     *
     * @return the country map
     */
    public static HashMap<Integer, Country> getCountryMap() {
        return countryMap;
    }

}
