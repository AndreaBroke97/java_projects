import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
3
public class DBManager {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    public static NationalFlightRegistry load(String path) throws IOException {
        NationalFlightRegistry registry = new NationalFlightRegistry();
        HashMap<String, Airplane> airplanes = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                switch (parts[0]) {
                    case "AIRLINE":
                        airplanes.put(parts[1], new Airline(parts[1], Integer.parseInt(parts[2])));
                        break;
                    case "CARGO":
                        airplanes.put(parts[1], new Cargo(parts[1], Integer.parseInt(parts[2])));
                        break;
                    case "FLY":
                        Airplane airplane = airplanes.get(parts[5]);
                        LocalDateTime dep = LocalDateTime.parse(parts[3], FMT);
                        LocalDateTime arr = LocalDateTime.parse(parts[4], FMT);
                        registry.addFlight(new Fly(parts[1], parts[2], dep, arr, airplane));
                        break;
                }
            }
        }
        return registry;
    }
}