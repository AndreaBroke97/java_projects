import java.util.Objects;

public abstract class Car {
    private String plate;
    private double entryTime;

    public Car(String plate, double entryTime) {
        this.plate = plate;
        this.entryTime = entryTime;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate.trim().toUpperCase();
    }

    public double getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(double entryTime) {
        this.entryTime = entryTime;
    }

    public abstract String getType();

    @Override
    public String toString() {
    return "Plate: " + plate + ", entryTime: " + entryTime;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Car other = (Car) obj; //casting esplicito
        return plate.equals(other.plate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(plate);
    }

    
}
