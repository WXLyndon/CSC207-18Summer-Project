package Model;

import java.io.Serializable;
import java.util.ArrayList;

class TransferTrip implements Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<Trip> tripsTravelled;

    /**
     * This constructs a TransferTrip class with a tripsTravelled ArrayList
     */
    TransferTrip() {
        this.tripsTravelled = new ArrayList<>();
    }

    /**
     * Add a trip to tripsTravelled.
     *
     * @param t a trip
     */
    void add(Trip t) {
        this.tripsTravelled.add(t);
    }

    /**
     * Calculate the fares that complete tripsTravelled
     *
     * @return the charges.
     */
    double charge(Cardholder cardholder) {
        double result = 0.0;
        for (int i = 0; i <= tripsTravelled.size() - 1; i++) {
            result += tripsTravelled.get(i).charge(cardholder);
        }
        if (result <= 6.0) {
            return tripsTravelled.get(tripsTravelled.size() - 1).charge(cardholder);
        } else if (result - tripsTravelled.get(tripsTravelled.size() - 1).charge(cardholder) > 6) {
            return 0;
        } else {
            return 6 - result + tripsTravelled.get(tripsTravelled.size() - 1).charge(cardholder);
        }
    }
}
