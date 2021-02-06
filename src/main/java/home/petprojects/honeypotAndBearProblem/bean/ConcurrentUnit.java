package home.petprojects.honeypotAndBearProblem.bean;

import java.util.StringJoiner;

public abstract class ConcurrentUnit implements Runnable {
    protected String name;

    protected ConcurrentUnit(String name) {
        this.name = name;
    }

    public abstract void entry();
    public abstract void exit();
    public abstract void doStuff();

    @Override
    public String toString() {
        return new StringJoiner(", ", "[", "]")
                .add(name)
                .toString();
    }
}
