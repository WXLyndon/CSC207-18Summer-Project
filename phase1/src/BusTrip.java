import java.util.Date;

public class BusTrip extends Trip{
    BusTrip(Station start, Date startTime) {
        super(start, startTime);
    }

    double charge(){
        AdminUser.addRevenue(2.0);
        System.out.println("2 dollars had been added to revenue");
        return 2.0;
    }


}
