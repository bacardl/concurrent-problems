package home.petprojects.sleepingBarberProblem.bean;

import java.time.Duration;
import java.time.LocalTime;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Customer extends ConcurrentUnit {
    private boolean beautyBeard;

    public Customer(BlockingQueue customersQueue) {
        super(customersQueue);
    }

    public void setBeautyBeard(boolean beautyBeard) {
        this.beautyBeard = beautyBeard;
    }

    public boolean isBeautyBeard() {
        return beautyBeard;
    }

    @Override
    public void run() {
        LocalTime in = LocalTime.now();
        System.out.println(this + " try to get a place in the waiting room.");
        System.out.println("Number of waiting customers: " + customersQueue.size());
        if (customersQueue.offer(this)) {
            System.out.println(this + " in the waiting room.");
            while (!isBeautyBeard()) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    System.out.println(this + " has been interrupted.");
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        } else {
            System.out.println(this + " have to leave from the barber shop.");
        }
        LocalTime out = LocalTime.now();
        System.out.println(this + " has been served by barber for " + Duration.between(in, out).getSeconds() + " seconds");
    }
}