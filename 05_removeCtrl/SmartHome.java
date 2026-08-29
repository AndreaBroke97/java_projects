import java.util.ArrayList;

public class SmartHome {
    private ArrayList<RemoteController> remotesList = new ArrayList<>();

    public void addController(RemoteController remote){
        this.remotesList.add(remote);
    }

    public void turnOnAll(){
        for (RemoteController r : this.remotesList) {
            r.turnOn();
        }
    }

    public void turnOffAll(){
        for (RemoteController r : this.remotesList) {
            r.turnOff();
        }
    }

}