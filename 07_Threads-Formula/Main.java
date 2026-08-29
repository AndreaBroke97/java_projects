
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        int laps = 10;
        ArrayList<Formula1> finishLine = new ArrayList<>();
        System.out.println("Race: START! Number of laps: " + laps);
        Formula1 f1 = new Formula1("Hamilton", "Ferrari", laps);
        Formula1 f2 = new Formula1("Leclerc", "Ferrari", laps);
        Formula1 f3 = new Formula1("Antonelli", "Mercedes", laps);
        Formula1 f4 = new Formula1("Piastri", "McLaren", laps);

        Thread t1 = new Thread(f1);
        Thread t2 = new Thread(f2);
        Thread t3 = new Thread(f3);
        Thread t4 = new Thread(f4);
        t1.start();
        t2.start();
        t3.start();
        t4.start();

        // per renderlo più scalabile farei un'arraylist di bool, dove per ogni car si crea e istanzia un nuovo bool per il controllo
        boolean added1 = false, added2 = false, added3 = false, added4 = false;

        while (finishLine.size() < 4) {
            if (!t1.isAlive() && !added1) {
                finishLine.add(f1);
                added1 = true;
            }
            if (!t2.isAlive() && !added2) {
                finishLine.add(f2);
                added2 = true;
            }
            if (!t3.isAlive() && !added3) {
                finishLine.add(f3);
                added3 = true;
            }
            if (!t4.isAlive() && !added4) {
                finishLine.add(f4);
                added4 = true;
            }
        }

        // SampleThread s = new SampleThread();
        // // la classe Thread accetta come argomento QUALUNQUE oggetto che implementa l'interfaccia Runnable
        // Thread t = new Thread(s);
        for (Formula1 racer : finishLine) {
            System.out.println(racer.getName() + " finished " + (finishLine.indexOf(racer) + 1) + "!");
        }
        // Il main non ha coscienza di quando vengono eseguiti e conclusi i thread paralleli, di conseguenza questo sout verrà stampato prima di tutto nonostante gli start sopra
        // System.out.println("Race: END!");
    }
}