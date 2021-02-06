package home.petprojects.honeypotAndBearProblem;

public class Main {
    public static void main(String[] args) {
        int numberOfHoneyBee = Integer.parseInt(args[0]);
        int numberOfHoneyPotPortions = Integer.parseInt(args[1]);
        int maxTurns = Integer.parseInt(args[2]);
        boolean fairSemaphore = Boolean.parseBoolean(args[3]);
        if (numberOfHoneyBee < 1 || numberOfHoneyPotPortions < 1 || maxTurns < 1 ) {
            throw new IllegalArgumentException("The input arguments should be greater than 1.");
        }
        new ProblemSimulator(numberOfHoneyBee, numberOfHoneyPotPortions, maxTurns, fairSemaphore).simulate();
    }
}
