import java.time.LocalDateTime;

public class Fly {
    private String departureIata;
    private String arrivalIata;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private Airplane airplane;

    public Fly(String departure, String arrival, LocalDateTime departureTime, LocalDateTime arrivalTime,
            Airplane airplane) {
        this.departureIata = departure;
        this.arrivalIata = arrival;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.airplane = airplane;
    }

    public String getDepartureIata() { return departureIata; }
    public String getArrivalIata() { return arrivalIata; }
    public LocalDateTime getDepartureTime() { return departureTime; }
    public LocalDateTime getArrivalTime() { return arrivalTime; }
    public Airplane getAirplane() { return airplane; }

    @Override
    public String toString() {
        return "Fly [departure=" + departureIata + ", arrival=" + arrivalIata + ", departureTime=" + departureTime
                + ", arrivalTime=" + arrivalTime + ", airplane=" + airplane + "]";
    }

}