package home.petprojects.philosophersProblem.bean;

import java.util.concurrent.ThreadLocalRandom;

public class Philosopher implements Runnable {
    private final int id;
    private final Fork leftFork;
    private final Fork rightFork;

    private volatile boolean tummyFull = false;

    private int numberOfTurnsToEat = 0;
    public Philosopher(int id, Fork leftFork, Fork rightFork) {
        this.id = id;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }
    public boolean isTummyFull() {
        return tummyFull;
    }
    public void setTummyFull(boolean tummyFull) {
        this.tummyFull = tummyFull;
    }

    @Override
    public void run() {

        try {
            while (!isTummyFull()) {
                think();
                if (leftFork.pickUp(this, "left")) {
                    if (rightFork.pickUp(this, "right")) {
                        eat();
                        rightFork.putDown(this, "right");
                    }
                    leftFork.putDown(this, "left");
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void think() throws InterruptedException {
        System.out.println(this + " is thinking");
        Thread.sleep(ThreadLocalRandom.current().nextInt(0, 1000));
    }

    private void eat() throws InterruptedException {
        System.out.println(this + " is eating");
        numberOfTurnsToEat++;
        Thread.sleep(ThreadLocalRandom.current().nextInt(0, 1000));
    }

    public int getNumberOfTurnsToEat() {
        return numberOfTurnsToEat;
    }

    @Override
    public String toString() {
        return "Philosopher#" + id;
    }
}