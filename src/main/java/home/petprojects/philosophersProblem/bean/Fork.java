package home.petprojects.philosophersProblem.bean;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Fork {
    private Lock up = new ReentrantLock();
    private final int id;
    public Fork(int id) {
        this.id = id;
    }

    public boolean pickUp(Philosopher philosopher, String where) throws InterruptedException {
        if (up.tryLock(10, TimeUnit.MILLISECONDS)) {
            System.out.println(philosopher + " picked up " + where + " " + this);
            return true;
        }
        return false;
    }

    public void putDown(Philosopher philosopher, String name) {
        up.unlock();
        System.out.println(philosopher + " put down " + name + " " + this);
    }

    @Override
    public String toString() {
        return "Fork#" + id;
    }
}