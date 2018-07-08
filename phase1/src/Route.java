public class Route {
    Station s1;
    Station s2;
    static double weight;
    Route (Station first, Station second){
        this.s1 = first;
        this.s2 = second;
        this.weight = 0.5;
    }

    Route (Station first, Station second, double weight){
        this.s1 = first;
        this.s2 = second;
        this.weight=weight;
    }

    @Override
    public String toString() {
        return "this is a route from " + s1.toString() + " to " + s2.toString();
    }

    public boolean equals(Route other){
        return this.s1.equals(other.s1) && this.s2.equals(other.s2);
    }

    Station getS2(){
        return s2;
    }
}
