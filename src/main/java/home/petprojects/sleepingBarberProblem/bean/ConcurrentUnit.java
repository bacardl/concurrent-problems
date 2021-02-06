package home.petprojects.sleepingBarberProblem.bean;

import java.util.StringJoiner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class ConcurrentUnit implements Runnable {
    private static final int INITIAL_UNIT_ID = 776;
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(INITIAL_UNIT_ID);
    protected final BlockingQueue<Customer> customersQueue;
    protected final String name;

    public ConcurrentUnit(BlockingQueue customersQueue) {
        this.name = "#" + ID_GENERATOR.incrementAndGet();
        this.customersQueue = customersQueue;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", this.getClass().getSimpleName() + "[", "]")
                .add(this.name)
                .toString();
    }
}
