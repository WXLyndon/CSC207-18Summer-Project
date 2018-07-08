public class AdminUser extends User{
    private static double revenue = 0;

    AdminUser(String name, String email){
        super(name, email);
    }

    void addTrain(){}

    void addStation(String newStation, Station nextTo){
        Station newStop = new Station(newStation);
        newStop.addRoute(nextTo);
        PublicTrans.allStations.add(newStop);
    }

    static void addRevenue(Double d){
        revenue += d;
    }
}