package Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A station contains a name and its routes
 */
public class Station implements Serializable {
    private static final long serialVersionUID = 1L;
    String name;
    private String stationType;
    private ArrayList<Route> routes;

    /**
     * This construct a station with station name and routes.
     *
     * @param stationName station name
     */
    public Station(String stationName, String stationType) {
        this.name = stationName;
        this.routes = new ArrayList<>();
        this.stationType = stationType;
    }

    /**
     * This method add next station in routes contribute.
     *
     * @param next next station
     */
    public void addRoute(Station next) {
        Route newRoute = new Route(this, next);
        this.routes.add(newRoute);
    }

    /**
     * This override the toString method
     *
     * @return name of the station
     */
    @Override
    public String toString() {
        return this.name;
    }

    /**
     * This override the equal method to check if they are the same station
     *
     * @param obj its can be either name of a station or a station
     * @return true for same, false for not.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof String) {
            return this.name.equals(obj);
        } else {
            if (obj instanceof Station) {
                return this.name.equals(obj.toString()) && this.getType().equals(((Station) obj).getType());
            }
        }
        return false;
    }

    /**
     * This method checks if a station is at the same route as this station
     *
     * @param other a station we want to check
     * @return true for they are in the same route, false for not.
     */
    boolean hasRoute(Station other) {
        Route checker = new Route(this, other);
        for (Route r : routes) {
            if (r.theSame(checker)) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method is getting all the stations after this station
     *
     * @return all the stations as a ArrayList
     */
    ArrayList<Station> getNextStations() {
        ArrayList<Station> result = new ArrayList<>();
        for (Route r : routes) {
            result.add(r.getS2());
        }
        return result;
    }

    /**
     * Get the type of this station.
     *
     * @return type
     */
    String getType() {
        return this.stationType;
    }
}
