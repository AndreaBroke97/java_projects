import java.time.LocalDate;
import java.util.ArrayList;

public class NationalFlightRegistry {
    private ArrayList<Fly> flights = new ArrayList<>();

    public void addFlight(Fly fly) {
        flights.add(fly);
    }

    public ArrayList<Fly> getAllFlights() {
        return new ArrayList<>(flights);
    }

    public ArrayList<Fly> filterByDeparture(String departureIata) {
        ArrayList<Fly> result = new ArrayList<>();
        for (Fly fly : flights) {
            if (fly.getDepartureIata().equals(departureIata)) {
                result.add(fly);
            }
        }
        return result;
    }

    public ArrayList<Fly> filterByDepartureAndArrival(String departureIata, String arrivalIata) {
        ArrayList<Fly> result = new ArrayList<>();
        for (Fly fly : flights) {
            if (fly.getDepartureIata().equals(departureIata) && fly.getArrivalIata().equals(arrivalIata)) {
                result.add(fly);
            }
        }
        return result;
    }

    public ArrayList<Fly> filterByDepartureArrivalAndDepartureDate(String departureIata, String arrivalIata,
            LocalDate departureDate) {
        ArrayList<Fly> result = new ArrayList<>();
        for (Fly fly : flights) {
            if (fly.getDepartureIata().equals(departureIata)
                    && fly.getArrivalIata().equals(arrivalIata)
                    && fly.getDepartureTime() != null
                    && fly.getDepartureTime().toLocalDate().equals(departureDate)) {
                result.add(fly);
            }
        }
        return result;
    }

    public ArrayList<Fly> filterByAllFields(String departureIata, String arrivalIata,
            LocalDate departureDate, LocalDate arrivalDate) {
        ArrayList<Fly> result = new ArrayList<>();
        for (Fly fly : flights) {
            if (fly.getDepartureIata().equals(departureIata)
                    && fly.getArrivalIata().equals(arrivalIata)
                    && fly.getDepartureTime() != null
                    && fly.getDepartureTime().toLocalDate().equals(departureDate)
                    && fly.getArrivalTime() != null
                    && fly.getArrivalTime().toLocalDate().equals(arrivalDate)) {
                result.add(fly);
            }
        }
        return result;
    }

    public String printFlights(ArrayList<Fly> list) {
        String s = "";
        for (Fly fly : list) {
            s += fly.toString() + "\n";
        }
        return s;
    }
}