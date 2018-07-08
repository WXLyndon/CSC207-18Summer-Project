import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class SubwayTrip extends Trip {
    double netCost = 2.0;

    SubwayTrip(Station start, Date startTime) {
        super(start, startTime);
    }

    public double charge(){
        if ((startTime.getTime() - endTime.getTime()) > 7200000L){
            AdminUser.addRevenue(6.0);
            return 6.0;
        }
        else {
            double price = rec(start, end, 0.0);
            AdminUser.addRevenue(price);
            return price;
        }
    }


    private double rec(Station s1, Station s2, Double c){
        if (s1.equals(s2)){
            return c;
        }
        else if(s1.hasRoute(s2)){
            return c+0.5;
        }
        else if(c >= Trip.capPrice){
            return capPrice;
        }
        else{
            ArrayList<Double> priceList = new ArrayList<>();
            ArrayList<Station> children = s1.getNextStations();
            for (Station s: children){
                priceList.add(rec(s, s2, c +0.5));
            }
            return priceList.indexOf (Collections.min(priceList));
        }
    }
}
