public class Main {
    public static void main(String[] args) {
        PetrolCar a1 = new PetrolCar("125fawe", 13.00, 50);
        DieselCar a2 = new DieselCar("tata24", 19.00, 70);
        DieselCar a3 = new DieselCar("rere23", 18.00, 100);
        PetrolCar a4 = new PetrolCar("kokad2", 15.00, 30);
        PetrolCar a5 = new PetrolCar("AB123", 9.0, 40);
        PetrolCar a6 = new PetrolCar("AB123", 9.0, 40);

        ElectricCar ac1 = new ElectricCar("flrit124", 14.00 , 80);
        ElectricCar ac2 = new ElectricCar("XX999", 8.0, 90);

        Parking p1 = new Parking(100);
        try {
            p1.enter(a1);
            p1.enter(a2);
            p1.enter(a3);
            p1.enter(a4);
            p1.enter(ac1);
            p1.enter(ac2);
        } catch (ParkingFullException e) {
            System.out.println(e.getMessage());
        }
        

        p1.printCars();
        System.out.println(Parking.getTotalEntries());

        System.out.println(a1);

        System.out.println(a5.equals(a6));

        Car genericCar = ac2;   // casting implicito: ElectricCar → Car
        System.out.println(genericCar);            
        System.out.println(genericCar.getType());  


        //PARSING
        String helloWorld = "40";
        int number = Integer.parseInt(helloWorld);
        System.out.println("\nparsing: " + (number + 10));

        try {
            int number2 = Integer.parseInt("abc");
            System.out.println(number2);
        } catch (NumberFormatException e) {
            System.out.println("is not a number valid");
        } finally {
            System.out.println("Operation terminated");
        }




        System.out.println("\nbefore the recharge: " + ac1.getBatteryLevel());  // prima
        ac1.recharge();                              // ricarica
        System.out.println("\nafter recharge: " + ac1.getBatteryLevel());  // dopo → 100

        Box<String> scatola = new Box<>();
        scatola.set(" \nquesto e un Generico\n ");
        System.out.println(scatola.get());


        MyThread t = new MyThread();
        t.run(); 
        t.start(); 

    }

       
}
