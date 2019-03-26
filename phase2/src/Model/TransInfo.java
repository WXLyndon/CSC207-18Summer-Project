package Model;

import Controller.UserStage;

import java.io.*;


public class TransInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private AdminInfo adminInfo;
    private UserList userList;
    private static TransInfo ourInstance;
    private int numCard;

    /**
     * The constructor of the TransInfo.
     */
    private TransInfo() {
        adminInfo = new AdminInfo();
        userList = new UserList();
    }

    /**
     * This methods returns the singleton instance which maintains the lazy singleton pattern.
     *
     * @return TransInfo the singleton instance.
     */
    public static TransInfo getInstance() {
        if (ourInstance == null) {
            ourInstance = new TransInfo();
        }
        return ourInstance;
    }

    /**
     * This methods returns the adminInfo of TransInfo.
     *
     * @return adminInfo The adminInfo of the TransInfo singleton instance.
     */
    public AdminInfo getAdminInfo() {
        return adminInfo;
    }

    /**
     * This methods returns the adminInfo of TransInfo.
     *
     * @return userList The userList of the TransInfo singleton instance.
     */
    public UserList getUserList() {
        return userList;
    }

    /**
     * The method to serialize the TransInfo instance to the TransInfo.sav.
     */
    public static void writeSerializableObject() {
        try {
            FileOutputStream fos = new FileOutputStream("TransInfo.sav", false);
            ObjectOutputStream transInfoOos = new ObjectOutputStream(fos);
            TransInfo transInfo = getInstance();
            transInfo.numCard = TravelCard.getNumCard();
            transInfoOos.writeObject(transInfo);
            transInfoOos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The method to deserialize the TransInfo.sav to get the TransInfo singleton instance and load them.
     */
    public static void readSerializableObject() {
        try {
            FileInputStream fis = new FileInputStream("TransInfo.sav");
            ObjectInputStream transInfoOis = new ObjectInputStream(fis);
            ourInstance = (TransInfo) transInfoOis.readObject();
            transInfoOis.close();
            TravelCard.setNumCard(ourInstance.numCard);
        } catch (Exception e) {
            UserStage.showAlert("First Run", "First Run",
                    "The configuration file is not found, and it will be created after the first run.");
        }
    }

}
