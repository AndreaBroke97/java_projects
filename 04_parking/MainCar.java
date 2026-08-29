// Classe di test: serve solo per provare il funzionamento di Car dalla console
// Non fa parte dell'applicazione vera, è un "banco di prova" manuale
public class MainCar {

    // main = punto di ingresso del programma, Java parte sempre da qui
    public static void main(String[] args) {

        // creo tre auto con targhe diverse
        Car car1 = new Car("AB00CD");
        Car car2 = new Car("AB1T3E");
        Car car3 = new Car("KRA41C");

        // equals() confronta le TARGHE, non gli indirizzi in memoria
        // car1 ha targa "AB00CD", car3 ha "KRA41C" → sono diverse → stampa false
        boolean x = car1.equals(car3);
        System.out.println(x); // false

        // variabili String create solo per studiare il confronto tra stringhe
        // (non vengono usate nel confronto qui sotto)
        String s = "Pippo";
        String s2 = "pippo";

        // car1.equals(car1): stessa istanza → equals ritorna subito true (primo controllo)
        System.out.println(car1.equals(car1)); // true
    }
}

/*
IDEA FUTURA (nota del professore):
modificare e dare la possibilità di poter mettere un valore diverso
di guadagno per il gestore del parcheggio per quando ed ogni volta che entrano dei camion,
tipo 1 euro l'ora per le car, 2 euro l'ora per i camion
→ servirebbe creare una classe Truck che estende Car, con una tariffa diversa
*/