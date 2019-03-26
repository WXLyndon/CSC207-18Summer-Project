package Model;

import Controller.UserStage;
import javafx.scene.control.Alert;

import java.io.Serializable;
import java.util.Date;
import java.util.Observable;

public class TravelCard extends Observable implements Serializable {

    private static final long serialVersionUID = 1L;
    private double cardBalance;
    private boolean activated;
    private Cardholder cardholder;
    private static int numCard;
    int cardId;
    private Trip trip;
    private Trip lastTrip;
    private TransferTrip transferStops;
    private boolean isTransfer = false;

    /*
     * log.txt that contains a record of all changes made to all card balances, all card “taps”, and
     * any exceptional events (for example: someone tapping off without tapping on).
     */


    /**
     * This constructs a TravelCard with 19.0 cardBalance, a true activated, a cardholder, a
     * transferStops and create a cardID by incrementing.
     *
     * @param cardholder a cardholder applying for new card
     */
    TravelCard(Cardholder cardholder) {
        this.cardBalance = 19.0;
        this.activated = true;
        this.cardholder = cardholder;
        this.cardId = numCard++;
        this.transferStops = new TransferTrip();
        addObserver(this.cardholder);
    }

    /**
     * Add balance to this travel card
     *
     * @param money the amount cardholder want to add
     */
    public void addBalance(double money) throws Exception {
        this.cardBalance += money;
        String num = Integer.toString(getCardNum());
        String cash = Double.toString(money);
        AdminInfo.logBalance("addBalance", num, cash, this.cardholder.name);
    }

    /**
     * Deactivate this card
     */
    public void deactivate() {
        this.activated = false;
    }

    /**
     * The cardholder will pay for certain amount for money.
     *
     * @param money the amount of charges
     */
    private void pay(double money, AdminInfo ai) throws Exception {

        this.cardBalance -= money;
        this.cardholder.addCostByMonth(money);
        ai.addRevenue(money);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("Success");
        alert.setContentText(this.cardholder + " has been charged with " + money);
        alert.showAndWait();


        String num = Integer.toString(getCardNum());
        String cash = Double.toString(money);
        AdminInfo.logBalance("pay", num, cash, this.cardholder.name);

        if (this.cardBalance <= this.cardholder.getWarningPrice()) {
            setChanged();
            notifyObservers();
        }
    }


    /**
     * Print the card balance.
     */
    public String getCardBalance() {

        return ("dear "
                + this.cardholder.name
                + " your \n card with id "
                + this.cardId
                + "\n has a balance of \n "
                + this.cardBalance);
    }

    /**
     * Assign the corresponding variable with value (Trip) when this travel card "enters" a Station.
     * Log the activities that changes this Travel Card and its cardholder.
     *
     * @param start which station he is entering
     */
    public void startNewTrip(Station start, AdminInfo ai) throws Exception {
        String num = Integer.toString(getCardNum());
        if (!activated || this.cardBalance < 0.0) {
            UserStage.showAlert("WARNING", "failed", "Please give us some money first or this card is deactivated!");
            AdminInfo.logTap("startNewTrip", num, start, 2, this.cardholder.name);
        } else {
            if (trip != null) {
                UserStage.showAlert("WARNING", "BAD BOY", "Illegal in! -6 dollar! but you can still start this trip");
                this.pay(6.0, ai);
                AdminInfo.logTap("startNewTrip", num, start, 1, this.cardholder.name);
            }
            AdminInfo.logTap("startNewTrip", num, start, 0, this.cardholder.name);
            if (start.getType().equals("Bus")) {
                trip = new BusTrip(start, new Date()); // Including small transfer trip
                if (lastTrip != null
                        && (lastTrip.end.name.equals(start.name))
                        && (new Date().getTime() - lastTrip.endTime.getTime()
                        < TimeManager.getCapTime())) { // time
                    // function of transfer station
                    isTransfer = true;
                } else {
                    // function of normal station
                    isTransfer = false;
                    transferStops = new TransferTrip();
                    this.pay(trip.charge(this.cardholder), ai);
                }
            } else if (start.getType().equals("Subway")) {
                trip = new SubwayTrip(start, new Date());
                if (lastTrip != null
                        && (lastTrip.end.name.equals(start.name))
                        && (new Date().getTime() - lastTrip.endTime.getTime() < TimeManager.getCapTime())) {
                    // function of transfer station
                    isTransfer = true;
                } else {
                    isTransfer = false;
                    transferStops = new TransferTrip();
                }
            }
        }
    }

    /**
     * Assign the corresponding variable with value (Trip) when this travel card "gets out" a Station.
     * Log the activities that changes this Travel Card and its cardholder.
     *
     * @param end the station this cardholder is existing
     */
    public void endTheTrip(Station end, AdminInfo ai) throws Exception {
        String num = Integer.toString(getCardNum());
        if (!activated) {
            UserStage.showAlert("WARNING", "Bad luck", "This card is not activated plz contact admin");
            AdminInfo.logTap("startNewTrip", num, end, 2, this.cardholder.name);
        } else if (trip == null) {
            UserStage.showAlert("WARNING", "BAD BOY", "Illegal out! -6 dollar! but you can still start this trip");
            this.pay(6.0, ai);
            AdminInfo.logTap("endTheTrip", num, end, 1, this.cardholder.name);
        } else {
            AdminInfo.logTap("endTheTrip", num, end, 0, this.cardholder.name);
            if (isTransfer) { // if it is a transit
                trip.endTrip(end, new Date());
                transferStops.add(trip);
                TransInfo.getInstance().getAdminInfo().addAllStationReached(trip.rec(trip.start, end, 0.0) * 2);
                this.pay(transferStops.charge(this.cardholder), ai);
                this.lastTrip = this.trip;
                this.cardholder.addRecentTrip(this.trip);
                this.trip = null;
            } else { // just a normal exit
                this.trip.endTrip(end, new Date());
                if (!trip.sameType()) {
                    this.pay(12.0, ai);
                    UserStage.showAlert("WARNING", "BAD BOY", "Illegal out! -12 dollar! You have 2 illegal movement");
                    AdminInfo.logTap("startNewTrip", num, trip.start, 1, this.cardholder.name);
                    AdminInfo.logTap("endTheTrip", num, end, 1, this.cardholder.name);
                } else if (trip.end.getType().equals("Subway")) {
                    this.pay(trip.charge(this.cardholder), ai);
                }
                TransInfo.getInstance().getAdminInfo().addAllStationReached(trip.rec(trip.start, end, 0.0) * 2);
                this.lastTrip = this.trip;
                this.cardholder.addRecentTrip(this.trip);
                this.transferStops.add(trip);
                this.trip = null;
            }
        }
    }

    /**
     * Return the total number of card
     *
     * @return the total number of card
     */
    static int getNumCard() {
        return numCard;
    }

    /**
     * Getter method for cardId.
     *
     * @return cardId
     */
    int getCardNum() {
        return cardId;
    }

    /**
     * Set the total number of card
     *
     * @param numCard the number we want to set
     */
    static void setNumCard(int numCard) {
        TravelCard.numCard = numCard;
    }

    /**
     * Override the toString function to return the name of this cardholder and this cardID
     *
     * @return the name of this cardholder and this cardID
     */
    @Override
    public String toString() {
        return this.cardholder + " card ID: " + this.cardId;
    }

    /**
     * Simulate the situation that transfer do not happen because of over time (over two hours limit)
     * s2 and s3 of this method has to be the station that has the same same.
     *
     * @param s1 The first station this card takes.
     * @param s2 The station that this card gets out from.
     * @param s3 The station that has the same name with s2, type doesn't matter here.
     * @param s4 The station ends the second trip.
     */
    public void demo(Station s1, Station s2, Station s3, Station s4) {
        try {
            TimeManager.setCapTime(10);
            startNewTrip(s1, TransInfo.getInstance().getAdminInfo());
            endTheTrip(s2, TransInfo.getInstance().getAdminInfo());
            Thread.sleep(50);
            startNewTrip(s3, TransInfo.getInstance().getAdminInfo());
            endTheTrip(s4, TransInfo.getInstance().getAdminInfo());
            Date current = new Date();
            Long newEndTime = current.getTime() + 7400000L;
            Long lastStartTime = current.getTime() + 7600000L;
            Date newEndDate = new Date(newEndTime);
            Date lastStartDate = new Date(lastStartTime);
            Date lastEndDate = new Date(lastStartTime + 100000L);
            this.cardholder.setDemoTime(newEndDate, lastStartDate, lastEndDate);
            TimeManager.setCapTime(7200000L);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
