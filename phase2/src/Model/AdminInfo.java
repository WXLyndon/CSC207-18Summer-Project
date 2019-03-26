package Model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.*;

public class AdminInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private double revenue = 0.0;
    private double operatingCost = 0.0;
    private Double allStationReached = 0.0;
    private static final Logger logger = Logger.getLogger("CardLog");
    private ArrayList<DailyReport> reportOf30Days = new ArrayList<>(30);
    private String announcement = "No Announcement.";


    /**
     * This method logs all the change of the Balance to the log.txt.
     *
     * @param methodName the method call change the balance.
     * @param cardNum    the cardID of the operating TravelCard.
     * @param money      the difference of the balance made by methodName.
     * @param name       the Cardholder of this travel card.
     */
    static void logBalance(String methodName, String cardNum, String money, String name) throws Exception {
        logger.setUseParentHandlers(false);
        FileHandler file = null;
        try {
            file = new FileHandler("log.txt", true);
            // able to change to generate log name with the e-mail of cardholders.
            setFormat(file);
            logger.addHandler(file);

            switch (methodName) {
                case "addBalance":
                    logger.log(Level.INFO, name + "'s Travel card with " +
                            cardNum + " has added " + money + " dollars");
                    break;
                case "pay":
                    logger.log(Level.INFO, name + "'s Travel card with " +
                            cardNum + " has payed " + money + " dollars");
                    break;
            }
        } finally {
            if (file != null) file.close();
        }
    }

    /**
     * This method logs all the tap movements to the log.txt.
     *
     * @param methodName the method call to change the state of the trip.
     * @param cardNum    the cardID of the operating TravelCard.
     * @param stop       the stop the card taps.
     * @param illegal    the num of illegal operations when taps.
     * @param name       the Cardholder of this travel card.
     */
    static void logTap(String methodName, String cardNum, Station stop, int illegal, String name) throws Exception {
        logger.setUseParentHandlers(false);
        FileHandler file = null;
        try {
            file = new FileHandler("log.txt", true);
            // able to change to generate log name with the e-mail of cardholders.
            setFormat(file);
            logger.addHandler(file);

            if (illegal == 0) {
                switch (methodName) {
                    case "startNewTrip":
                        logger.log(Level.INFO,
                                name + "'s Travel card with " +
                                        cardNum + " has tapped in "
                                        + stop.toString());//
                        break;
                    case "endTheTrip":
                        logger.log(Level.INFO, name + "'s Travel card with " +
                                cardNum + " has tapped out " + stop.toString());//
                        break;
                }
            } else if (illegal == 1) {
                switch (methodName) {
                    case "startNewTrip":
                        logger.log(
                                Level.WARNING, name + "'s Travel card with " + cardNum + " has illegally tapped out " +
                                        stop.getType() + stop.toString());
                        break;
                    case "endTheTrip":
                        logger.log(
                                Level.WARNING, name + "'s Travel card with " + cardNum + " has illegally tapped in " +
                                        stop.getType() + " " + stop.toString());
                        break;
                }
            } else if (illegal == 2) {
                logger.log(
                        Level.WARNING, name + "'s Travel card with " + cardNum + " has failed to tap in " +
                                stop.getType() + " " + stop.toString() + " due to insufficient balance"
                                + " or the card is not activated");
            }

        } finally {
            if (file != null) file.close();
        }
    }

    /**
     * This method set the format of the logs.
     *
     * @param log FileHandler
     */
    private static void setFormat(FileHandler log) {
        log.setFormatter(new Formatter() {
            public String format(LogRecord record) {
                DateTimeFormatter timeStyle = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss:SSS");
                LocalDateTime time = LocalDateTime.now();
                return record.getLevel() + " " + timeStyle.format(time)
                        + ": " + record.getMessage() + "\n";
            }
        });
    }

    /**
     * This method add the the num of stations reached which has not be added to the allStationsReached.
     *
     * @param d the num of stations reached which has not be added to the allStationsReached.
     */
    void addAllStationReached(double d) {
        this.allStationReached += d;
    }

    /**
     * This method returns today's allStationReached of the system.
     *
     * @return today's allStationReached of the system.
     */
    public Double getAllStationReached() {
        return allStationReached;
    }

    /**
     * This method set allStationReached to 0 when the system shut down by AdminUser ends the program.
     */
    void setAllStationReached() {
        this.allStationReached = 0.0;
    }

    /**
     * This method returns pure income of the system.
     *
     * @return pure income of the system.
     */
    public double checkIncome() {
        return revenue - operatingCost;
    }

    /**
     * This method add amount of revenue to AdminUser account.
     *
     * @param d The amount of revenue wanted to add to this.revenue.
     */
    void addRevenue(Double d) {
        revenue += d;
    }

    /**
     * This method return the operating cost of the system.
     *
     * @return operating cost of the system.
     */
    public double getOperatingCost() {
        return operatingCost;
    }

    /**
     * This method set system's operating cost to a specific amount.
     *
     * @param operatingCost operating cost of the system.
     */
    public void setOperatingCost(double operatingCost) {
        this.operatingCost = operatingCost;
    }

    /**
     * This returns amount of revenue earned by AdminUser.
     *
     * @return The AdminUser's revenue.
     */
    public double getRevenue() {
        return revenue;
    }

    /**
     * This method set AdminUser's revenue to a specific amount.
     */
    void setRevenue() {
        this.revenue = (double) 0;
    }

    /**
     * This method get the announcement set by AdminUser.
     *
     * @return announcement String
     */
    public String getAnnouncement() {
        return announcement;
    }

    /**
     * This method set the announcement by AdminUser.
     */
    public void setAnnouncement(String announcement) {
        this.announcement = announcement;
    }

    /**
     * This method add add today's DailyReport to the reportOf30Days, and make the reportOf30Days has the last 30 days'
     * daily reports..
     *
     * @param d Today's DailyReport.
     */
    void addDailyReport(DailyReport d) {
        if (this.reportOf30Days.size() == 30) {
            this.reportOf30Days.remove(0);
            this.reportOf30Days.add(d);
        } else {
            this.reportOf30Days.add(d);
        }
    }

    /**
     * This method returns reportOf30Days which is the ArrayList contains all recent 30 days' DailyReports.
     *
     * @return reportOf30Days The ArrayList contains all recent 30 days' DailyReports.
     */
    public ArrayList<DailyReport> getReportOf30Days() {
        return reportOf30Days;
    }
}
