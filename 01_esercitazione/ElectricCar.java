public class ElectricCar extends Car implements Rechargeable {
    private int batteryLevel;

    public ElectricCar(String plate, double entryTime, int batteryLevel) {
        super(plate, entryTime); //calling the Car(DAD) attributes
        this.batteryLevel = batteryLevel;
    }

    public int getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(int batteryLevel) {
        if (batteryLevel >= 0 && batteryLevel <= 100) {
            this.batteryLevel = batteryLevel; //it work only if battery level is between 0 - 100
        } else {
            System.out.println("Battery level not valid.");
        }
    }

    @Override
    public String getType() {
    return "Electric";
    }

    @Override
    public void recharge() {
        batteryLevel = 100;
        System.out.println("Recharged to 100%");
    }
}