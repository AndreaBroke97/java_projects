public class Car {
    private String name;
    private String model;

    public Car(String name, String model){
        this.setName(name);
        this.setModel(model);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

}