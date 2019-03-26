import java.io.Serializable;
import java.util.ArrayList;

public class Cardholder extends User implements Serializable{
    private static final long serialVersionUID = 1L;
    private ArrayList<TravelCard> cards;
    private ArrayList<Trip> recentTrip = new ArrayList<>(3);
    static ArrayList<Cardholder> cardholderList = new ArrayList<>();

    public Cardholder(String name, String email) {
        super(name, email);
        this.cards = new ArrayList<>();
        System.out.println("A new cardholder had been created");
    }

    public static ArrayList<Cardholder> getCardholderList() {
        return cardholderList;
    }

    public static void setCardholderList(ArrayList<Cardholder> cardholderList) {
        Cardholder.cardholderList = cardholderList;
    }

    void addTravelCard(){
        TravelCard newCard = new TravelCard(this);
        this.cards.add(newCard);

        System.out.println(this.name + " got a new travel card! with id" + newCard.cardId);
    }

    public String printCards() {
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

    static Cardholder getHolder(String name){
        for (Cardholder s: Cardholder.cardholderList){
            if (s.name.equals(name)){
                return s;
            }
        }
        return null;
    }

    TravelCard getCard(int id){
        for (TravelCard t: cards){
            if (t.cardId == id){
                return t;
            }
        }
        return null;
    }

    void trackAverageCost(){

    }


}