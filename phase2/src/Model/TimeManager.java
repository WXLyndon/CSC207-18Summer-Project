package Model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

class TimeManager implements Serializable {
    private static final long serialVersionUID = 1L;
    private static long capTime = 7200000L;//2 hours
    private static final long timeDifference = 2629746000L;


    /**
     * Returns true if the trip passed in is over one month from now, else false.
     *
     * @param trip The Trip which need to be tested
     * @return True if the trip is over one month from current system time.
     */
    static boolean overOneMonth(Trip trip) {
        Date timeNow = new Date();
        return (timeNow.getTime() - trip.startTime.getTime()) > timeDifference;
    }

    /**
     * Getter method for capTime.
     *
     * @return CapTime of TimeManager.
     */
    static long getCapTime() {
        return capTime;
    }

    /**
     * Setter method for capTime.
     *
     * @param capTime of TimeManager.
     */
    static void setCapTime(long capTime) {
        TimeManager.capTime = capTime;
    }

    /**
     * Returns the month of current system time, in integer from 0 to 11, which corresponding to the
     * 12 months of one year respectively.
     *
     * @return An integer that represents the month of current system time.
     */
    static int getMonth() {
        Date dt = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        return cal.get(Calendar.MONTH);
    }
}
