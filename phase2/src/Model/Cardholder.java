package Model;

import javafx.scene.control.Alert;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

/**
 * This Class implement Cardholder
 */

public class Cardholder extends User implements Serializable, Observer {
    private static final long serialVersionUID = 1L;
    private ArrayList<TravelCard> cards;
    private ArrayList<Trip> recentTrip = new ArrayList<>(2);
    private ArrayList<Trip> tripReport = new ArrayList<>(30);
    private Double[] monthlyCost = new Double[12];
    private double warningPrice = 15.0;

    /**
     * This constructs a cardholder with name and e-mail address.
     *
     * @param name  the name of the cardholder.
     * @param email the e-mail address of the cardholder.
     */
    public Cardholder(String name, String email) {
        super(name, email);
        this.cards = new ArrayList<>();
    }

    /**
     * This method add cost to monthly Cost
     *
     * @param money how much money spends
     */
    void addCostByMonth(Double money) {
        int month = TimeManager.getMonth();
        if (monthlyCost[month] != null) {
            double allCost = monthlyCost[month];
            allCost += money;
            monthlyCost[month] = allCost;
        } else {
            monthlyCost[month] = money;
        }
    }

    /**
     * This method means to change the name of the cardholder
     *
     * @param name the name the cardholder want to replaced with
     */
    public void changeName(String name) {
        this.name = name;
    }

    /**
     * This method returns all cards from this card holder
     *
     * @return return all cards
     */
    public StringBuilder allCards() {
        StringBuilder all = new StringBuilder();
        for (TravelCard t : cards) {
            all.append(Integer.toString(t.getCardNum()));
            all.append("\n");
        }
        return all;
    }

    /**
     * Return the last applied card num
     *
     * @return the last applied card num
     */
    public int getLastCard() {
        int a = TravelCard.getNumCard();
        return a - 1;
    }

    /**
     * This method means to add a new card to this cardholder
     */
    public void addTravelCard() {
        TravelCard newCard = new TravelCard(this);
        this.cards.add(newCard);
    }

    /**
     * This method removes a card
     *
     * @param cardID card id for removal
     */
    public void removeCard(int cardID) {
        for (int i = 0; i < cards.size(); i++) {
            if (cardID == cards.get(i).cardId) {
                //noinspection SuspiciousListRemoveInLoop
                cards.remove(i);
            }
        }
    }

    /**
     * This method returns the three recent trips of this cardholder
     *
     * @return print out all three recent trips this cardholder traveled.
     */
    public String getRecentTrip() {
        if (this.recentTrip.isEmpty()) {
            return "You don't have any recent trip";
        } else {
            StringBuilder result = new StringBuilder();
            for (Trip t : recentTrip) {
                if (!TimeManager.overOneMonth(t)) {
                    result.append(t.toString());
                    result.append("\n");
                }
            }
            return result.toString();
        }
    }

    /**
     * This method add a trip as the newest recent trip.
     *
     * @param trip the last trip this cardholder traveled.
     */
    void addRecentTrip(Trip trip) {
        if (this.recentTrip.size() < 3) {
            this.recentTrip.add(trip);
            this.tripReport.add(trip);
        } else {
            this.recentTrip.remove(0);
            this.recentTrip.add(trip);
            this.tripReport.add(trip);
        }
    }

    /**
     * This method returns a specific travel card
     *
     * @param id the id of the travel card
     * @return the travel card with that id
     */
    public TravelCard getCard(int id) {
        for (TravelCard t : cards) {
            if (t.cardId == id) {
                return t;
            }
        }
        return null;
    }

    /**
     * This returns a string which represent this cardholder's monthly cost
     *
     * @return this cardholder's monthly cost
     */
    public String trackAverageCost() {
        Double allCost = 0.0;
        int count = 0;
        for (Double cost : monthlyCost) {
            if (cost != null) {
                count += 1;
                allCost += cost;
            }
        }
        return this.name + "'s monthly Cost:" + " " + allCost / count;
    }

    /**
     * This method shows alert
     *
     * @param o   travel card
     * @param arg used to notify all observers
     */
    @Override
    public void update(Observable o, Object arg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("Dear " + this.name);
        alert.setContentText("Your card:" + " has less than " + warningPrice + " dollars");
        alert.showAndWait();
    }

    /**
     * This method set demo time
     *
     * @param firstEnd    firs ending station/stop
     * @param secondStart second starting station/stop
     * @param secondEnd   second ending station/stop
     */
    void setDemoTime(Date firstEnd, Date secondStart, Date secondEnd) {
        recentTrip.get(recentTrip.size() - 2).setEndTime(firstEnd);
        recentTrip.get(recentTrip.size() - 1).setStartTime(secondStart);
        recentTrip.get(recentTrip.size() - 1).setEndTime(secondEnd);
    }

    /**
     * Returns warningPrice
     *
     * @return warningPrice
     */
    double getWarningPrice() {
        return warningPrice;
    }

    /**
     * Returns num of bus trips in all trips for this cardholder
     *
     * @return num of bus trips in all trips
     */
    public int getNumBusTrips() {
        int i = 0;
        for (Trip t : tripReport) {
            if (t instanceof BusTrip) {
                i += 1;
            }
        }
        return i;
    }

    /**
     * Returns num of subway trips in all trips for this cardholder
     *
     * @return num of subway trips in all trips
     */
    public int getNumSubwayTrips() {
        int i = 0;
        for (Trip t : tripReport) {
            if (t instanceof SubwayTrip) {
                i += 1;
            }
        }
        return i;
    }
}