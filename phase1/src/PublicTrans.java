import java.util.ArrayList;

public class PublicTrans {
    static ArrayList<Station> allStations = new ArrayList<>();

    static Station getStation(String name){
        for (Station s: allStations){
            if (s.name.equals(name)){
                return s;
            }
        }
        return null;
    }

}
