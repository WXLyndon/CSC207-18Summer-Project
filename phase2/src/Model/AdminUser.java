package Model;

import java.io.Serializable;

/**
 * This class implements AdminUser which can track information and operate on the trans system.
 *
 * @version 1.0
 */
public class AdminUser extends User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * This construct AdminUser with a name and e-mail address.
     *
     * @param name  The name of the AdminUser
     * @param email The e-mail address of the AdminUser
     */
    public AdminUser(String name, String email) {
        super(name, email);
    }

    /**
     * This method end the program and print out daily report which contains today's revenue and the
     * number of all the station and stops legally reached.
     */
    public void endProgram(AdminInfo ai) {
        DailyReport d = new DailyReport(ai.getRevenue(), ai.getAllStationReached());
        ai.addDailyReport(d);
        ai.setRevenue();
        ai.setAllStationReached();
        ai.setOperatingCost(0);
        TransInfo.writeSerializableObject();
        System.exit(1);
    }
}
