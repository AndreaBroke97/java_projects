// Rappresenta un'auto identificata dalla sua targa
public class Car {

    // la targa è privata: nessuno può modificarla dall'esterno dopo la creazione
    private String plate;

    // costruttore: per creare un'auto devi obbligatoriamente passare la targa
    public Car(String plate) {
        this.plate = plate;
    }

    // GETTER: unico modo per leggere la targa dall'esterno della classe
    public String getPlate() {
        return plate;
    }

    // equals: confronta due oggetti Car per CONTENUTO (targa), non per indirizzo in memoria
    // serve per poter usare car1.equals(car2) in modo affidabile
    @Override
    public boolean equals(Object obj) {
        if (this == obj)          // stessa istanza in memoria → sicuramente uguale
            return true;
        if (obj == null)          // confronto con null → sempre falso
            return false;
        if (getClass() != obj.getClass()) // classi diverse → non possono essere uguali
            return false;
        Car other = (Car) obj;    // cast sicuro: ora possiamo accedere a "plate"
        if (plate == null) {
            if (other.plate != null) return false; // questa ha targa null, l'altra no
        } else if (!plate.equals(other.plate))     // confronto testo delle targhe
            return false;
        return true; // targhe identiche → auto uguali
    }

}
