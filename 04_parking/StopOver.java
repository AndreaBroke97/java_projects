/*public class StopOver {
    Car car;
    long in;
    long out;
    int position;


    public StopOver(Car car, int position) {
        this.car = car;
        this.position = position;
        this.in = System.currentTimeMillis();
        //restituire valore in long
        //quando auto esce faccio lo stesso
        //e quando esce genera il prezzo    }
    }


    public void exit() {
        this.out = System.currentTimeMillis();
    }

    public long getDuration() {
        return (out - in) / 1000 / 60;
    }

    public double getPrice() {
        return getDuration() * 0.05;
    }
}   

*/
import java.util.Date;

// StopOver = "sosta": registra tutta la vita di un'auto nel parcheggio
// Si crea quando l'auto ENTRA, si chiude quando l'auto ESCE
// Funziona come uno scontrino: aperto all'ingresso, timbrato all'uscita
public class StopOver {

    private long start;          // timestamp di ingresso in millisecondi (es. 1715000000000)
    private long end;            // timestamp di uscita — vale 0 finché l'auto è ancora dentro
    private Car car;             // riferimento all'oggetto auto che ha sostato
    private int position;        // numero del posto occupato nel parcheggio
    private int priceForMillis;  // tariffa: quanto costa ogni millisecondo di sosta

    // costruttore: chiamato quando un'auto ENTRA nel parcheggio
    // Date().getTime() restituisce i millisecondi passati dal 1/1/1970 (epoch time)
    public StopOver(Car car, int position, int priceForMillis) {
        Date d = new Date();
        this.start = d.getTime(); // segna l'ora di ingresso
        this.car = car;
        this.position = position;
        this.priceForMillis = priceForMillis;
    }

    // chiamato quando l'auto ESCE: segna l'ora di uscita
    // dopo questa chiamata, end != 0 e la sosta è considerata terminata
    public void exit() {
        Date d = new Date();
        this.end = d.getTime();
    }

    // ritorna true se la sosta è TERMINATA (exit() è stato già chiamato)
    // end == 0 significa che l'auto è ancora dentro (Java inizializza i long a 0)
    public boolean isOver() {
        return this.end != 0;
    }

    // ritorna true se l'auto è ANCORA nel parcheggio
    // è semplicemente il contrario di isOver()
    public boolean isCarIntoTheParking() {
        return !this.isOver();
    }

    // calcola il prezzo della sosta
    // formula: (millisecondi trascorsi) × (tariffa per millisecondo)
    // se l'auto è ancora dentro → prezzo 0 (non sappiamo ancora quanto starà)
    public double getPrice() {
        if (this.isOver()) {
            return (this.end - this.start) * this.priceForMillis;
        } else {
            return 0;
        }
    }

    // GETTER: restituisce l'oggetto Car associato a questa sosta
    public Car getCar() {
        return car;
    }

    // GETTER: restituisce il numero del posto occupato
    public int getPosition() {
        return position;
    }
}