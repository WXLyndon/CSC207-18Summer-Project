import java.util.ArrayList;

public class Station {
    private String name;
    private ArrayList<Route> routes;
    Station (String stationName){
        this.name = stationName;
        this.routes = new ArrayList<>();
        PublicTrans.allStations.add(this);
    }

    void addRoute(Station next){
        Route newRoute = new Route(this, next);
    }

    public ArrayList<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(ArrayList<Route> routes) {
        this.routes = routes;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(Object obj){
        if (obj instanceof String){
            return this.name.equals(obj);
        }
        else{
            return this.name.equals(obj.toString());
        }
    }

    boolean hasRoute(Station other){
        Route checker = new Route(this, other);
        for (Route r: routes){
            if (r.equals(checker)){
                return true;
            }
        }
        return false;
    }

    ArrayList<Station> getNextStations(){
        ArrayList<Station> result = new ArrayList<>();
        for (Route r: routes){
            result.add(r.getS2());
        }
        return result;
    }
}
