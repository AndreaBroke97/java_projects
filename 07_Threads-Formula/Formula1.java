public class Formula1 extends Car implements Runnable{

    private int laps;
    private int currentLaps = 0;
    public Formula1(String name, String model, int laps){
        super(name, model);
        this.laps = laps;
    }

    @Override
    public void run(){
        try {
            for (this.currentLaps = 1; this.currentLaps <= laps; this.currentLaps ++) {
                Thread.sleep((int)((Math.random() * (1000 - 100)) + 100));
                System.out.println(this.getName() + ", Model: " + this.getModel() + " is at " + this.currentLaps + " lap");
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(this.getName() + " has finished the race!");
    }
}