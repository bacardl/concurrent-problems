package home.petprojects.philosophersProblem;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int numberOfPhilosophers = Integer.parseInt(args[0]);
        int durationInSeconds = Integer.parseInt(args[1]);
        if (numberOfPhilosophers < 1 || durationInSeconds < 0) {
            throw new IllegalArgumentException("Number of philosophers should be greater than 0.");
        }
        new ProblemSimulator(numberOfPhilosophers, durationInSeconds * 1000).simulate();
    }
}
