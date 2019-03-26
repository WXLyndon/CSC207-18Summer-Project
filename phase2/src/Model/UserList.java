package Model;

import java.io.Serializable;
import java.util.ArrayList;

public class UserList implements Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<Cardholder> cardholderList;
    private ArrayList<AdminUser> adminUserList;

    /**
     * The constructor of the UserList.
     */
    UserList() {
        this.cardholderList = new ArrayList<>();
        this.adminUserList = new ArrayList<>();
    }

    /**
     * This method returns a cardholder refers by given name and email.
     *
     * @param name the name of the cardholder
     * @return the cardholder that matches to its name
     */
    public Cardholder getHolderWQ(String name, String email) {
        for (Cardholder s : cardholderList) {
            if (s.name.equals(name) && s.email.equals(email)) {
                return s;
            }
        }
        return null;
    }

    /**
     * This method add one more admin user to the system.
     *
     * @param admin AdminUser who we want to add to the system
     */
    public void addAdmin(AdminUser admin) {
        adminUserList.add(admin);
    }

    /**
     * This method add one more CardHolder user to the system.
     *
     * @param user CardHolder who we want to add to the system
     */
    public void addCardholder(Cardholder user) {
        cardholderList.add(user);
    }

    /**
     * This method checks whether a person is the AdminUser of the system
     *
     * @param name the name of the person
     * @return true if there is, vice versa.
     */
    public boolean hasAdmin(String name, String email) {
        for (AdminUser a : adminUserList) {
            if (a.name.equals(name) && (a.email.equals(email))) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method returns a AdminUser refers by given name.
     *
     * @param name the name of the AdminUser.
     * @return the AdminUser that matches to its name
     */
    public AdminUser getAdmin(String name) {
        for (AdminUser a : adminUserList) {
            if (a.name.equals(name)) {
                return a;
            }
        }
        return null;
    }

    /**
     * This method checks whether a person is the CardHolder of the system.
     *
     * @param name the name of the person
     * @return true if there is, vice versa.
     */
    public boolean hasCardholder(String name, String email) {
        for (Cardholder c : cardholderList) {
            if (c.name.equals(name) && (c.email.equals(email))) {
                return true;
            }
        }
        return false;
    }

}
