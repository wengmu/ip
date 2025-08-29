import java.util.Scanner;
import java.util.List;

public class Ui {
    private Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public void showWelcome() {
        String logo =
        "   ____ _     _       \n"
                + "  / ___| |__ (_) __ _ \n"
                + " | |   | '_ \\| |/ _` |\n"
                + " | |___| | | | | (_| |\n"
                + "  \\____|_| |_|_|\\__,_|\n";
        System.out.println("Hello Sir, I am \n" + logo);
        System.out.println("What can I help you with? \n");
    }

    public String readCommand() {
        return scanner.nextLine().trim();
    }

    public void showBye() {
        System.out.println("Goodbye . Hope you have a great day ahead!\n");
    }

    public void showList(TaskList tasks) {
        if(tasks.isEmpty()) {
            System.out.println("There is no work to do. Congrats!");
        } else {
            for(int i = 0; i < tasks.size(); i++) {
                System.out.printf("%d. %s\n", i+1, tasks.get(i));
            }
        }
    }

    public void showAdded(Task task, int total) {
        System.out.println("Got it. I've added this task:");
        System.out.println(" " + task);
        System.out.println("Now you have " + total + " tasks in the list.");
    }

    public void showDeleted(Task task, int remaining) {
        System.out.println("Noted. I've removed this task:");
        System.out.println(" " + task);
        System.out.println("Now you have " + remaining + " tasks in the list.");
    }

    public void showMarked(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + task);
    }

    public void showUnmarked(Task task) {
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + task);
    }

    public void showError(String message) {
        System.out.println(message);
    }
}
