import java.util.Date;

public class TravelCard {
    private double cardBalance;
    private boolean activated;
    private Cardholder cardholder;
    private static int numCard;
    private int cardId;
    private Trip trip;

    TravelCard(Cardholder cardholder){
        this.cardBalance = 19.0;
        this.activated = true;
        this.cardholder = cardholder;
        this.cardId = numCard++;
    }



    void addBalance(double money){
        this.cardBalance += money;
    }

    void deactivate(){
        this.activated = false;
    }

    void pay(double money){
        this.cardBalance -= money;
        AdminUser.addRevenue(money);
    }

    public double getCardBalance() {
        return cardBalance;
    }

    void startNewTrip(Station start){
        if (!activated){
            System.out.println("This card is not activated plz contact admin");
        }
        else {
            if (start instanceof BusStop) {
                trip = new BusTrip(start, new Date());
                this.pay(trip.charge());
            }
            if (start instanceof SubwayStation){
                trip = new SubwayTrip(start, new Date());
            }
        }
    }

    void endTheTrip(Station end){
        this.trip.endTrip(end, new Date());
        if (trip.start instanceof SubwayStation){
            this.pay(trip.charge());
        }
        this.cardholder.addRecentTrip(this.trip);
        this.trip = null;
    }

    @Override
    public String toString() {
        return this.cardholder + " card ID: " + this.cardId;
    }
}
