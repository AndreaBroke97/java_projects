public interface RemoteController {
    // Le interfaccie non contengono metodi con un corpo vero e proprio, ma solo la loro firma.
    // Da questo momento in poi tutte le classi che "implements" questa interface avranno l'OBBLIGO di fare override di questi metodi.
    public void turnOn();
    public void turnOff();

}

// public class RemoteController {
//     public void turnOn(){
        
//     }
//     public void turnOff(){

//     }
// }