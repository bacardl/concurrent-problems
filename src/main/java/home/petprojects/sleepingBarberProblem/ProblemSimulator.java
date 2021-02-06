package home.petprojects.sleepingBarberProblem;

import home.petprojects.sleepingBarberProblem.bean.Barber;
import home.petprojects.sleepingBarberProblem.bean.BarberShop;

public class ProblemSimulator {
    private final int numberOfBarbers;
    private final int numberOfSpaces;
    private final int durationInMillis;

    public ProblemSimulator(int numberOfBarbers, int numberOfSpaces, int durationInMillis) {

        this.numberOfBarbers = numberOfBarbers;
        this.numberOfSpaces = numberOfSpaces;
        this.durationInMillis = durationInMillis;
    }

    public void simulate() {
        Thread barberShopThread = new Thread(new BarberShop(numberOfBarbers, numberOfSpaces));
        barberShopThread.start();
        try {
            Thread.sleep(durationInMillis);
        } catch (InterruptedException e) {
            System.out.println("The barber shop's simulation has been interrupted.");
        } finally {
            System.out.println("Interrupting barber shop thread...");
            barberShopThread.interrupt();
        }
    }

}
