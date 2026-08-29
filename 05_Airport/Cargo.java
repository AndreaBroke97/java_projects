public class Cargo extends Airplane {
    private int capacity;

    public Cargo(String model, int capacity) {
        super(model);
        this.capacity = capacity;
    }

    @Override
    public float getWeight() {
        return this.capacity * 1.1f;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "Cargo [capacity=" + capacity + ", getModel()=" + getModel() + ", getWeight()=" + getWeight() + "]";
    }

}