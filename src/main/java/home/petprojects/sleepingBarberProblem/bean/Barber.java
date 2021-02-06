package home.petprojects.sleepingBarberProblem.bean;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Barber extends ConcurrentUnit{

    private static final int MIN_TIME_HAIR_CUTTING = 5; // sec
    private static final int MAX_TIME_HAIR_CUTTING = 11; // sec

    public Barber(BlockingQueue customers) {
        super(customers);
    }

    @Override
    public void run() {
        while (true) {
            if(Thread.currentThread().isInterrupted()) {
                System.out.println(this + " has been interrupted.");
                break;
            }
            try {
                Customer customer = customersQueue.take();
                System.out.println(customer + " getting hair cut by " + this);
                TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(MIN_TIME_HAIR_CUTTING,
                        MAX_TIME_HAIR_CUTTING));
                customer.setBeautyBeard(true);
                System.out.println(customer + " pays to " + this + " and leaves");
            } catch (InterruptedException e) {
                System.out.println(this + " has been interrupted.");
                return;
            }
        }
    }
}