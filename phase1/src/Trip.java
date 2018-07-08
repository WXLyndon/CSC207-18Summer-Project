import java.rmi.NoSuchObjectException;
import java.util.Date;

public abstract class Trip{
    Station start;
    Station end;
    Date startTime;
    Date endTime;
    static final double capPrice = 4.0;


    Trip (Station start, Date startTime){
        this.start = start;
        this.startTime = startTime;
    }

    void endTrip(Station out, Date endTime){
        this.end = out;
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return start.toString() + ":" + startTime.toString() + " " +  end.toString() + ":" + endTime.toString();
    }

    abstract double charge();
}
