package Model;

import java.util.Date;

class BusTrip extends Trip {

    /**
     * Constructor of the class BusTrip. It takes a start station and start date as parameters and
     * pass the value in the parameters to the variable in its super class, Trip.
     *
     * @param start     The start Station of the bus trip.
     * @param startTime The start Date of the bus trip.
     */
    BusTrip(Station start, Date startTime) {
        super(start, startTime);
    }

    /**
     * Returns a double of 2.0, which is the fare for taking busTrip without transfer.
     *
     * @return A double of 2.0, i.e the fare of one bus trip.
     */
    double charge(Cardholder cardholder) {
        return 2.0;
    }
}
