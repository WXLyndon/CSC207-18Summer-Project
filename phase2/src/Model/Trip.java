package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * This class simulate a Trip starts from a Station.
 */
public abstract class Trip implements Serializable {
    private static final long serialVersionUID = 1L;
    Station start;
    Station end;
    Date startTime;
    Date endTime;
    private static final double capPrice = 6.0;

    /**
     * Constructor of the class Trip. It takes a start station and start date as parameters and pass
     * the value in the parameters to the variables of its own.
     *
     * @param start     The start Station of this Trip.
     * @param startTime The Start Date of this Trip.
     */
    Trip(Station start, Date startTime) {
        this.start = start;
        this.startTime = startTime;
    }

    /**
     * Returns nothing but passes the value in the parameters to this.end and this.endTime This method
     * represents that a trip is ended.
     *
     * @param out     The Station that this trip ends.
     * @param endTime The Date which this trip ends.
     */
    void endTrip(Station out, Date endTime) {
        this.end = out;
        this.endTime = endTime;
    }

    /**
     * Returns a string representation which is readable.
     *
     * @return A string representation of this Trip.
     */
    @Override
    public String toString() {
        return start.toString()
                + ":"
                + startTime.toString()
                + " to "
                + end.toString()
                + ":"
                + endTime.toString() + "\n";
    }

    /**
     * Abstract method which needs to be filled later in Trip's subclasses. This method returns the
     * amount that should be charged in the trip.
     *
     * @return The amount that need to be charged in this Trip.
     */
    abstract double charge(Cardholder cardholder);

    /**
     * This recursive method returns a double that counted the overall minimum weights of edges
     * between two stations. The weight by default is 0.5. i.e this method always returns the smallest
     * weights of path between two Station even if the subway or bus line overlap with itself or other
     * trans- port lines.
     *
     * <p>If the path weight is greater than 6.0, the method returns 6.0 instead.
     *
     * @param s1 The starting Station.
     * @param s2 The ending Station.
     * @param c  The initial cost.
     * @return The minimum cost in terms of weights between s1 and s2.
     */
    double rec(Station s1, Station s2, Double c) {
        // Base case 1.
        if (s1.equals(s2)) {
            return c;
        }
        // Base case 2.
        else if (s1.hasRoute(s2)) {
            return c + 0.5;
        }
        // Base case 3.
        else if (c >= Trip.capPrice) {
            return capPrice;
        } else {
            // Create an ArrayList to hold the price of all possible routes.
            // Create an ArrayList which has all the station that s1 is next to.
            ArrayList<Double> priceList = new ArrayList<>();
            ArrayList<Station> children = s1.getNextStations();
            for (Station s : children) {
                // Recursively call this method for the station that is next to s1.
                priceList.add(rec(s, s2, c + 0.5));
            }
            double min = priceList.get(0);
            // Find the minimum of price in price list.
            for (double p : priceList) {
                if (p < min) {
                    min = p;
                }
            }
            return min;
        }
    }

    /**
     * This method returns whether this trip's start type as station is the same
     * with its end station.
     *
     * @return True if start station type is the same with the end station type.
     */
    boolean sameType() {
        return start.getType().equals(end.getType());
    }

    /**
     * Set the variable startTime to a specific Date.
     *
     * @param startTime Date wants to be assigned to startTime.
     */
    void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * Set the variable endTime to a specific Date.
     *
     * @param endTime Date wants to be assigned to endTime.
     */
    void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

}
