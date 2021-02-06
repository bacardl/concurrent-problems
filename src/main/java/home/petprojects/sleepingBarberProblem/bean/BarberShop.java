package home.petprojects.sleepingBarberProblem.bean;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class BarberShop implements Runnable {
    private static final int MIN_CUSTOMERS_APPEAR_DURATION = 1; //sec
    private static final int MAX_CUSTOMERS_APPEAR_DURATION = 3; //sec
    private final BlockingQueue<Customer> customersQueue;
    private final int barbersNumber;
    private final ExecutorService executorService;

    public BarberShop(int barbersNumber, int numberOfSpaces) {
        this.barbersNumber = barbersNumber;
        this.customersQueue = new ArrayBlockingQueue<>(numberOfSpaces, true);
        this.executorService = Executors.newFixedThreadPool(barbersNumber);
    }

    @Override
    public void run() {
        Barber[] employees = new Barber[barbersNumber];
        System.out.println("Opening up shop");
        for (int i = 0; i < employees.length; i++) {
            employees[i] = new Barber(customersQueue);
            executorService.execute(employees[i]);
        }
        try {
            while (true) {
                TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(MIN_CUSTOMERS_APPEAR_DURATION,
                        MAX_CUSTOMERS_APPEAR_DURATION));
                Thread customerThread = new Thread(new Customer(customersQueue));
                customerThread.setDaemon(true);
                customerThread.start();
                if (Thread.currentThread().isInterrupted()) {
                    break;
                }
            }
        } catch (InterruptedException e) {
            System.out.println("The barber shop's thread has been interrupted.");
        } finally {
            System.out.println("Interrupting barbers threads.");
            executorService.shutdownNow();
        }
    }
}