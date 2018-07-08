
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Cardholder extends User{
    private ArrayList<TravelCard> cards;
    ArrayList<Trip> recentTrip = new ArrayList<>(2);

    public Cardholder(String name, String email) {
        super(name, email);
        this.cards = new ArrayList<>();
    }

    void addTravelCard(){
        TravelCard newCard = new TravelCard(this);
        this.cards.add(newCard);
    }

    void deleteCard(int id){
    }

    public String getCards() {
        if (this.cards.isEmpty()){
            return "You haven't had any card yet";
        }
        else {

            StringBuilder result = new StringBuilder();
            for (TravelCard t : cards) {
                result.append(t.toString()).append(" ");
            }
            return result.toString();
        }
    }

    String getRecentTrip() {
        if (this.recentTrip.isEmpty()) {
            return "You don't have any recent trip";
        } else {
            StringBuilder result = new StringBuilder();
            for (Trip t : recentTrip) {
                if (!TimeManager.overOneMonth(t)) {
                    result.append(t.toString()).append(" ");
                }
            }
            return result.toString();
        }
    }

    void addRecentTrip(Trip trip){
        if (this.recentTrip.size()<3) {
            this.recentTrip.add(trip);
        }else{
            this.recentTrip.remove(0);
            this.recentTrip.add(trip);
        }
    }
}