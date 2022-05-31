package model;

import java.sql.Timestamp;

/**
 * Login Attempt Object for viewing Login Report
 * @author Gavin Casey
 */
public class LoginAttempt {
    private String userName;
    private Timestamp time;

    /**
     * Instantiates a new Login attempt.
     *
     * @param userName the user name
     * @param time     the time
     */
    public LoginAttempt(String userName, Timestamp time) {
        this.userName = userName;
        this.time = time;
    }

    /**
     * Gets user name.
     *
     * @return the user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Gets time.
     *
     * @return the time
     */
    public Timestamp getTime() {
        return time;
    }
}
