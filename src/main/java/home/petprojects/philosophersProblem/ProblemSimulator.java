package home.petprojects.philosophersProblem;

import home.petprojects.philosophersProblem.bean.Fork;
import home.petprojects.philosophersProblem.bean.Philosopher;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProblemSimulator {
    private int simulationDurationInMillis;
    private int numberOfPhilosophers;
    private int numberOfForks;

    public ProblemSimulator(int numberOfPhilosophers, int simulationDurationInMillis) {
        this.numberOfPhilosophers = numberOfPhilosophers;
        this.numberOfForks = numberOfPhilosophers;
        this.simulationDurationInMillis = simulationDurationInMillis;
    }

    public void simulate() {
        ExecutorService executorService = null;
        Philosopher[] philosophers = null;
        try {
            philosophers = new Philosopher[numberOfPhilosophers];
            Fork[] Forks = new Fork[numberOfPhilosophers];
            for (int i = 0; i < numberOfPhilosophers; i++) {
                Forks[i] = new Fork(i);
            }
            executorService = Executors.newFixedThreadPool(numberOfPhilosophers);
            for (int i = 0; i < numberOfPhilosophers; i++) {
                philosophers[i] = new Philosopher(i, Forks[i], Forks[(i + 1) % numberOfPhilosophers]);
                executorService.execute(philosophers[i]);
            }
            Thread.sleep(simulationDurationInMillis);
            for (Philosopher philosopher : philosophers) {
                philosopher.setTummyFull(true);
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            executorService.shutdown();
            while (!executorService.isTerminated()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println("------REPORT------");
            for (Philosopher philosopher : philosophers) {
                System.out.println(philosopher + " had " + philosopher.getNumberOfTurnsToEat() + " of turns to eat ");
            }
        }
    }
}
