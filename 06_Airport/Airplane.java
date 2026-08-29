public abstract class Airplane {

    private String model;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Airplane(String model) {
        this.model = model;
    }

    public abstract float getWeight();

    @Override
    public String toString() {
        return "Airplane [model=" + model + "]";
    }

}