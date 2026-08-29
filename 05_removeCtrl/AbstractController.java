// essendo una classe va EXTENDS nelle classi che vogliono utilizzare le abstract
public abstract class AbstractController{
    public boolean isOn;

    // metodi abstract: non serve che abbiano un corpo
    public abstract void turnOn();
    public abstract void turnOff();

    // metodi comuni: necessitano di un corpo
    public void doSmth(){

    }
}