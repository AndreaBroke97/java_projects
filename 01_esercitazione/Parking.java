import java.util.ArrayList;

public class Parking {
    private ArrayList<Car> cars;
    private final int capacity; //final = capacity non può essere più modificato dopo assegnazione
    private static int totalEntries = 0;

    public Parking(int capacity) {
        this.cars = new ArrayList<>();
        this.capacity = capacity;
    }

    public void enter(Car newCar) throws ParkingFullException {

        if (cars.size() < capacity) {
            cars.add(newCar);
            totalEntries++;
        } else {
            throw new ParkingFullException("the parking is full");
        }
    }

    public void exit(String plate) {
        for (int i = 0; i < cars.size(); i++) {
            if (cars.get(i).getPlate().equals(plate)) {
                cars.remove(i);
                return;
            }
        }
        System.out.println("Car not found");
    }

    public void printCars() {
        for (Car c : cars) {
            System.out.println("Parked car: " + c.getPlate() + " - " + c.getType());
        }
    }

    public static int getTotalEntries() {
        return totalEntries;
    }
}
