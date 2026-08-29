import java.util.ArrayList;

public class Parking {
    private ArrayList<Car> cars; //arraylist = dynamic structure
    private int capacity;          

    public Parking(int capacity) {
        this.cars = new ArrayList<>();
        this.capacity = capacity;
    }

    public void enter(Car newCar) {
        if (cars.size() < capacity) {
            cars.add(newCar);
        } else {
            System.out.println("The parking is full");
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
            System.out.println(c);   
        }
    }

    public Car getCar(int index) {
        return cars.get(index);
    }
}
