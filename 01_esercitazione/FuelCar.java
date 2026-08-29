public abstract  class FuelCar extends Car {
    private double fuelLevel;

    public FuelCar(String plate, double entryTime, double fuelLevel) {
        super(plate, entryTime);
        this.fuelLevel = fuelLevel;
    }

    public double getFuelLevel() {
        return fuelLevel;
    }

    public void setFuelLevel(double fuelLevel) {
        if (fuelLevel >= 0 && fuelLevel <= 100) {
            this.fuelLevel = fuelLevel;
        } else {
            System.out.println("Fuel level not valid.");
        }
    }

}
