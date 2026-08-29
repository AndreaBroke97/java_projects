public class PetrolCar extends FuelCar {

    
    public PetrolCar(String plate, double entryTime, double fuelLevel) {
        super(plate, entryTime, fuelLevel);
    }

    @Override
    public String getType() {
    return "Petrol";
    }
}
