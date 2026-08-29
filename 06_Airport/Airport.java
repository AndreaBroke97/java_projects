import java.time.LocalDateTime;
import java.util.ArrayList;

public class Airport {
    private ArrayList<Fly> flies = new ArrayList<>();
    private String name;
    private String city;
    private String iata;

    public Airport(String name, String city, String iata) {
        this.name = name;
        this.city = city;
        this.iata = iata;
    }

    public ArrayList<Fly> getFlies() {
        return flies;
    }

    public void setFlies(ArrayList<Fly> flies) {
        this.flies = flies;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getIata() {
        return iata;
    }

    public void setIata(String iata) {
        this.iata = iata;
    }

    public void addFly(String departure, String arrival, LocalDateTime departureTime, LocalDateTime arrivalTime,
            Airplane airplane) {
        Fly fly = new Fly(departure, arrival, departureTime, arrivalTime, airplane);
        this.flies.add(fly);
    }

    public String printFly() {
        String s = "";
        for (Fly fly : this.flies) {
            s += fly.toString() + "\n";
        }
        return s;
    }

    @Override
    public String toString() {
        return "Airport [flies=" + flies + ", name=" + name + ", city=" + city + ", iata=" + iata + "]";
    }

}