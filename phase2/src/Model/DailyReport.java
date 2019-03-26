package Model;

import java.io.Serializable;
import java.util.Date;

public class DailyReport implements Serializable {
    private static final long serialVersionUID = 1L;
    private double revenue;
    private double stationReached;
    private Date date;

    /**
     * Constructor for DailyReport
     *
     * @param revenue        revenue
     * @param stationReached how many station reached
     */
    DailyReport(Double revenue, double stationReached) {
        this.revenue = revenue;
        this.stationReached = stationReached;
        date = new Date();
    }

    /**
     * Get revenue
     *
     * @return revenue
     */
    public double getRevenue() {
        return revenue;
    }

    /**
     * return how many station reached
     *
     * @return how many station reached
     */
    public double getStationReached() {
        return stationReached;
    }

    /**
     * Get date
     *
     * @return date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Override toString
     *
     * @return today's data: revenue and station reached
     */
    @Override
    public String toString() {
        return date.toString() + ": revenue(" + this.revenue + "). Stations reached by all cardholders(" +
                this.stationReached + ").";
    }
}
