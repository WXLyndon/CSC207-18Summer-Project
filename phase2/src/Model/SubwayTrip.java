package Model;

import java.io.Serializable;
import java.util.Date;

/**
 * This class simulate a Trip starts from a subway station.
 */
class SubwayTrip extends Trip implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor of the class SubwayTrip. It takes a start station and start date as parameters and
     * pass the value in the parameters to the variable in its super class, Trip.
     *
     * @param start     The start Station of the subway trip.
     * @param startTime The start Date of the subway trip.
     */
    SubwayTrip(Station start, Date startTime) {
        super(start, startTime);
    }

    /**
     * Returns the amount of money in double that should be charged for this subway trip.
     *
     * @return A double that is the fare of this subway trip.
     */
    public double charge(Cardholder cardholder) {
        return rec(start, end, 0.0);
    }

    /**
     * Returns a string representation which is readable and gives necessary information.
     *
     * @return A string representation of this SubwayTrip.
     */
    @Override
    public String toString() {
        return start.toString()
                + ":"
                + startTime.toString()
                + " to "
                + end.toString()
                + ":"
                + endTime.toString();
    }
}
