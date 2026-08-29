import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        Airport cta = new Airport("Fontanarossa", "Catania", "CTA");
        Airport mpx = new Airport("Silvio Berlusconi", "Varese", "MPX");
        Airport fco = new Airport("Leonardo da Vinci", "Roma", "FCO");
        Airport nap = new Airport("Capodichino", "Napoli", "NAP");

        Airline a = new Airline("Boing 747", 300);
        Airline a2 = new Airline("Boing 737", 250);
        Cargo c = new Cargo("MD11", 300);

        cta.addFly(cta.getIata(), mpx.getIata(), LocalDateTime.of(2026, 5, 20, 10, 10), LocalDateTime.of(2026, 5, 20, 12, 0), a);
        cta.addFly(cta.getIata(), fco.getIata(), LocalDateTime.of(2026, 6, 20, 10, 10), LocalDateTime.of(2026, 6, 20, 11, 30), a2);
        cta.addFly(cta.getIata(), nap.getIata(), LocalDateTime.of(2026, 7, 20, 11, 20), LocalDateTime.of(2026, 7, 20, 12, 40), c);
        fco.addFly(fco.getIata(), nap.getIata(), LocalDateTime.of(2026, 6, 20, 14, 0), LocalDateTime.of(2026, 6, 20, 15, 0), a);
        fco.addFly(fco.getIata(), cta.getIata(), LocalDateTime.of(2026, 6, 20, 16, 0), LocalDateTime.of(2026, 6, 20, 18, 0), a2);

        NationalFlightRegistry accumulator = new NationalFlightRegistry();
        for (Fly fly : cta.getFlies()) accumulator.addFlight(fly);
        for (Fly fly : fco.getFlies()) accumulator.addFlight(fly);

        System.out.println("=== Tutti i voli nazionali ===");
        for (Fly fly : accumulator.getAllFlights()) System.out.println(fly);

        System.out.println("\n=== Filtro 1: partenza da CTA ===");
        for (Fly fly : accumulator.filterByDeparture("CTA")) System.out.println(fly);

        System.out.println("\n=== Filtro 2: partenza FCO -> arrivo NAP ===");
        for (Fly fly : accumulator.filterByDepartureAndArrival("FCO", "NAP")) System.out.println(fly);

        System.out.println("\n=== Filtro 3: CTA -> FCO il 20/06/2026 ===");
        for (Fly fly : accumulator.filterByDepartureArrivalAndDepartureDate("CTA", "FCO", LocalDate.of(2026, 6, 20))) System.out.println(fly);

        System.out.println("\n=== Filtro 4: CTA -> FCO partenza 20/06 arrivo 20/06 ===");
        for (Fly fly : accumulator.filterByAllFields("CTA", "FCO", LocalDate.of(2026, 6, 20), LocalDate.of(2026, 6, 20))) System.out.println(fly);
    }
}