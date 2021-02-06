package home.petprojects.honeypotAndBearProblem;

import home.petprojects.honeypotAndBearProblem.bean.ConcurrentUnit;

import java.util.concurrent.Semaphore;

public class ProblemSimulator {
    public final int numberOfHoneyBee;
    public final int maxTurns;
    public final int maxCurrentPortionsAmount;

    private Semaphore semaphoreHoneypotIsNotFull;
    private Semaphore semaphoreHoneypotIsFull;

    private int currentPortionsAmount;
    private int honeyPotFilledCount;


    public ProblemSimulator(int numberOfHoneyBee, int maxCurrentPortionsAmount, int maxTurns, boolean fairSemaphore) {
        if (numberOfHoneyBee < 1 || maxCurrentPortionsAmount < 1 || maxTurns < -1 || maxTurns == 0) {
            throw new IllegalArgumentException("The values of numberOfHoneyBee and maxCurrentPortionsAmount must be positive" +
                    " and maxTurns must be positive or -1");
        }
        this.numberOfHoneyBee = numberOfHoneyBee;
        this.maxTurns = maxTurns;
        this.maxCurrentPortionsAmount = maxCurrentPortionsAmount;
        this.semaphoreHoneypotIsNotFull = new Semaphore(1, fairSemaphore);
        this.semaphoreHoneypotIsFull = new Semaphore(0);
    }

    public void simulate() {
        Thread[] honeybeeThreads = new Thread[numberOfHoneyBee];
        for (int i = 0; i < honeybeeThreads.length; i++) {
            honeybeeThreads[i] = new Thread(new HoneyBee("Honeybee #" + i));
            honeybeeThreads[i].start();
        }
        Thread bearThread = new Thread(new Bear("Bear"));
        bearThread.start();
        try {
            for (int i = 0; i < honeybeeThreads.length; i++) {

                honeybeeThreads[i].join();
                bearThread.join();
            }
        } catch (InterruptedException e) {
           Thread.currentThread().interrupt();
        }
    }

    private class HoneyBee extends ConcurrentUnit {

        protected HoneyBee(String name) {
            super(name);
        }

        @Override
        public void run() {
            while (maxTurns == -1 || honeyPotFilledCount < maxTurns) {
                entry();
                if (maxTurns == -1 || honeyPotFilledCount < maxTurns) {
                    try {
                        doStuff();
                    } finally {
                        exit();
                    }
                }
            }
        }
        @Override
        public void entry() {
            try {
                semaphoreHoneypotIsNotFull.acquire();
                if (honeyPotFilledCount == maxTurns) {
                    semaphoreHoneypotIsNotFull.release();
                    return;
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            currentPortionsAmount++;
        }

        @Override
        public void exit() {
            if (currentPortionsAmount == maxCurrentPortionsAmount) {
                if (honeyPotFilledCount == Integer.MAX_VALUE) {
                    honeyPotFilledCount = 0;
                } else {
                    honeyPotFilledCount++;
                }
                semaphoreHoneypotIsFull.release();
            } else {
                semaphoreHoneypotIsNotFull.release();
            }
        }

        @Override
        public void doStuff() {
            System.out.printf("'%s' says: Filling the honeypot. The honeypot have %d portion(s) of honey.\n", this, currentPortionsAmount);
        }
    }

    public class Bear extends ConcurrentUnit {

        protected Bear(String name) {
            super(name);
        }

        @Override
        public void run() {
            do {
                entry();
                try {
                    doStuff();
                } finally {
                    exit();
                }
            } while (maxTurns == -1 || honeyPotFilledCount < maxTurns || currentPortionsAmount != 0);
        }

        @Override
        public void entry() {
            try {
                semaphoreHoneypotIsFull.acquire();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void exit() {
            currentPortionsAmount = 0;
            semaphoreHoneypotIsNotFull.release();
        }

        @Override
        public void doStuff() {
            System.out.printf("'%s' says: The honeypot is empty.\n", this);
        }
    }

}
