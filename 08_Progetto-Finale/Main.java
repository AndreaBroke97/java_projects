public class Main {
    public static void main(String[] args) {
        Parking parking = new Parking(2);

        Car a1 = new Car("  ab123  ", 13.0); 
        Car a2 = new Car(" cd456 ", 14.0);

        parking.enter(a1);
        parking.enter(a2);

        parking.printCars();

        try {
            Car c = parking.getCar(3);   
            System.out.println(c);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("No car at that position");
        }

        System.out.println(a1.getEntryTime());

        a1.setEntryTime(18.0);

        System.out.println(a1.getEntryTime());
    }
}
