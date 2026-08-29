/*import java.util.ArrayList;
public class Parking {
    private Car[] lots;
    private ArrayList<StopOver> history;
    private static int DEFAULT_SIZE = 10;

    public Parking() {
        this.lots = new Car[DEFAULT_SIZE];
    }

    public Parking(int size) {
        this.lots = new Car[size];
        this.history = new ArrayList<>();
    }




    public int availablePlaces() {
        int count = 0;
        for (Car carInParking : this.lots) {
            if (carInParking == null) count++;
        }
        return count;
    }

    public int occupatedPlaces() {
        return this.lots.length - this.availablePlaces();
    }

    public boolean contains(Car car) {
        for (Car carInParking : this.lots) {
            if (carInParking != null && carInParking.equals(car)) return true; // null check aggiunto
        }
        return false;
    }

    public void addCar(Car car, int place) {
        if (car == null || place < 0 || place >= this.lots.length || this.lots[place] != null || this.contains(car)) return;
        this.lots[place] = car;
    }

    public void removeCar(int place) {
        if (place < 0 || place >= this.lots.length || this.lots[place] == null) return;
        StopOver stopOver = new StopOver(this.lots[place], place);
        stopOver.exit();
        this.history.add(stopOver);
        this.lots[place] = null;
    }

    public boolean isFull() {
        return this.availablePlaces() == 0; // era occupatedPlaces() == 0, logica invertita
    }

    public Car getCarAt(int place) {
        return this.lots[place];
    }

    public int size() {
        return this.lots.length;
    }

    public ArrayList<StopOver> getHistory() {
        return this.history;
    } 
}
*/
import java.util.ArrayList;

public class Parking {

    private int places = 10;                              // numero totale di posti nel parcheggio
    private int priceForMillis = 1;                       // tariffa: costo per millisecondo di sosta
    private ArrayList<StopOver> stopOvers = new ArrayList(); // lista di tutte le soste (attive e terminate)

    // costruttore senza argomenti: usa i valori di default (10 posti, tariffa 1)
    public Parking() {

    }

    // costruttore con argomento: permette di scegliere il numero di posti
    public Parking(int places) {
        this.places = places;
    }

    // tenta di aggiungere un'auto al posto indicato da "position"
    // ritorna false se il posto è già occupato, true se l'auto viene aggiunta
    public boolean addCar(Car car, int position) {
        // scorre tutte le soste esistenti per controllare se quel posto è occupato
        for (StopOver stopOver : this.stopOvers) {
            // isCarIntoTheParking() = l'auto è ancora dentro? getPosition() = qual è il suo posto?
            if (stopOver.isCarIntoTheParking() && stopOver.getPosition() == position) {
                return false; // posto occupato → aggiunta fallita
            }
        }
        // posto libero → crea una nuova sosta e aggiungila alla lista
        this.stopOvers.add(new StopOver(car, position, this.priceForMillis));
        return true;
    }

    // fa uscire dal parcheggio l'auto con la targa "plate"
    // ⚠️ BUG: usare .equals(plate) al posto di == per confrontare stringhe in Java
    public void exitCarFromParking(String plate) {
        for (StopOver stopOver : this.stopOvers) {
            if (stopOver.getCar().getPlate() == plate) {
                stopOver.exit(); // segna l'ora di uscita nella sosta → la chiude
            }
        }
    }

    // calcola e restituisce il numero di posti ancora liberi
    public int getAvailablePlaces() {
        int busyPlaces = 0;
        for (StopOver stopOver : this.stopOvers) {
            if (stopOver.isCarIntoTheParking()) { // conta solo le soste attive (auto ancora dentro)
                busyPlaces++;
            }
        }
        return this.places - busyPlaces; // posti totali - posti occupati = posti liberi
    }

    // ritorna true se il parcheggio è completamente pieno (0 posti liberi)
    public boolean isCompleted() {
        return this.getAvailablePlaces() == 0;
    }

    // calcola l'incasso totale sommando il prezzo di tutte le soste terminate
    public double getAmount() {
        double amount = 0;
        for (StopOver stopOver : this.stopOvers) {
            amount += stopOver.getPrice(); // getPrice() ritorna 0 se la sosta è ancora aperta
        }
        return amount;
    }
}
