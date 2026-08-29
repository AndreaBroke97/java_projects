public class Car {
    private String plate;
    private double entryTime;

    public Car(String plate, double entryTime) {
        setPlate(plate); //call the metod setplate + sanification
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

    @Override
    public String toString() {
        return "Plate: " + plate + ", entryTime: " + entryTime;
    }
}
