package Model;

import java.io.Serializable;

/**
 * This Route consist a first station and a second station.
 */
class Route implements Serializable {

    private static final long serialVersionUID = 1L;
    private Station s1;
    private Station s2;

    /**
     * This constructs Route which contains first and second station.
     *
     * @param first  first station
     * @param second second station
     */
    Route(Station first, Station second) {
        this.s1 = first;
        this.s2 = second;
    }

    /**
     * This method override the print method
     *
     * @return print statement
     */
    @Override
    public String toString() {
        return "this is a route from " + s1.toString() + " to " + s2.toString();
    }

    /**
     * This method compare if two route are the same
     *
     * @param other the route we want to compare
     * @return true for same, false for different
     */
    boolean theSame(Route other) {
        return (this.s1.name.equals(other.s1.name)) && (this.s2.name.equals(other.s2.name));
    }

    /**
     * This method return the second station of a route
     *
     * @return the second station
     */
    Station getS2() {
        return s2;
    }
}
