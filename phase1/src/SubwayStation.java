import java.io.Serializable;

public class SubwayStation extends Station implements Serializable {
    private static final long serialVersionUID = 1L;
    SubwayStation(String stationName) {
        super(stationName);
    }
}
