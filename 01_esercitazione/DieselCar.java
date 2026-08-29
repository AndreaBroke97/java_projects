public class DieselCar extends FuelCar {

    public DieselCar(String plate, double entryTime, double fuelLevel) {
        super(plate, entryTime, fuelLevel);
        
    }
    
    @Override
    public String getType() {
    return "Diesel";
    }
}
