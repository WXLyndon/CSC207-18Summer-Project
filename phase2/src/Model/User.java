package Model;

import java.io.Serializable;

public abstract class User implements Serializable {
    private static final long serialVersionUID = 1L;
    String name;
    String email;

    /**
     * This constructs a new User with it's name and it's e-mail address
     *
     * @param name  the name of this User
     * @param email the e-mail of this User
     */
    User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    /**
     * Override the toString method so it returns the name of the cardholder and its email.
     *
     * @return the name of the cardholder and its email.
     */
    @Override
    public String toString() {
        return this.name + ": " + this.email;
    }
}
