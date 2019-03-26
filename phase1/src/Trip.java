import java.io.Serializable;
import java.rmi.NoSuchObjectException;
import java.util.Date;

public abstract class Trip implements Serializable {
    private static final long serialVersionUID = 1L;
    Station start;
    Station end;
    Date startTime;
    Date endTime;
    Station transfer;
    static final double capPrice = 4.0;

    Trip (Station start, Date startTime){
        this.start = start;
        this.startTime = startTime;
    }

    void endTrip(Station out, Date endTime){
        this.end = out;
        this.endTime = endTime;
        //transfer = out;
    }

    @Override
    public String toString() {
        return start.toString() + ":" + startTime.toString() + " to " +  end.toString() + ":" + endTime.toString();
    }

    abstract double charge();


    boolean isTransfer(Station station){
        return transfer.equals(station);
    }
}
