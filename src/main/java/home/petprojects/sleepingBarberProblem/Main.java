package home.petprojects.sleepingBarberProblem;

public class Main {
    public static void main(String[] args) {
        int numberOfBarbers = Integer.parseInt(args[0]);
        int numberOfSpaces = Integer.parseInt(args[1]);
        int durationInSeconds = Integer.parseInt(args[2]);
        if (numberOfBarbers < 1 || numberOfSpaces < 1 || durationInSeconds < 1) {
            throw new IllegalArgumentException("The input arguments should be greater than 1.");
        }
        new ProblemSimulator(numberOfBarbers, numberOfSpaces, durationInSeconds * 1000).simulate();
    }
}
