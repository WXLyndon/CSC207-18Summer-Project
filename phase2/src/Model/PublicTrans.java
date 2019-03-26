package Model;

import java.util.ArrayList;

/**
 * This PublicTrans take records of all teh stations and stops
 */
public class PublicTrans {
    public static ArrayList<Station> allStations = new ArrayList<>();

    /**
     * This return a station based on its name and type if exists in all the stations.
     *
     * @param name the name of the station
     * @param type the type of the station
     * @return station or null
     */
    static public Station getStation(String name, String type) {
        for (Station s : allStations) {
            if (s.name.equals(name) && s.getType().equals(type)) {
                return s;
            }
        }
        return null;
    }
}
