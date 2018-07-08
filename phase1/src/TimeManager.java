import java.util.Date;

public class TimeManager {
    Date systemStartDate;
    Date systemEndDate;

    TimeManager(){
        this.systemStartDate = new Date();
    }


    private static final long timeDifference = 2629746000L;


    public static boolean overOneMonth(Trip trip){
        Date timeNow = new Date();
        return (timeNow.getTime() - trip.startTime.getTime()) > timeDifference;
    }

}
