public class Airline extends Airplane {
    private int nOfSeats;

    public Airline(String model, int nOfSeats) {
        super(model);
        this.nOfSeats = nOfSeats;
    }

    @Override
    public float getWeight() {
        return this.nOfSeats * 70;
    }

    public int getNOfSeats() {
        return nOfSeats;
    }

    public void setNOfSeats(int nOfSeats) {
        this.nOfSeats = nOfSeats;
    }

    @Override
    public String toString() {
        return "Airline [nOfSeats=" + nOfSeats + ", getModel()=" + getModel() + ", getWeight()=" + getWeight() + "]";
    }

}