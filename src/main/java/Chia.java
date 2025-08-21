import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Chia {
    public static void main(String[] args) {
        String logo =
                          "   ____ _     _       \n"
                        + "  / ___| |__ (_) __ _ \n"
                        + " | |   | '_ \\| |/ _` |\n"
                        + " | |___| | | | | (_| |\n"
                        + "  \\____|_| |_|_|\\__,_|\n";
        System.out.println("Hello Sir, I am \n" + logo);
        System.out.println("What can I help you with? \n");

        Scanner sc = new Scanner(System.in);
        List<Task> task = new ArrayList<>();

        while(true) {
            String user_input = sc.nextLine().trim();
            if (user_input.equals("bye")){
                System.out.println("Goodbye . Hope you have a great day ahead!\n");
                break;
            } else if(user_input.equals("list")) {
                if(task.isEmpty()) {
                    System.out.println("There is no work to do. Congrats!");
                } else {
                    for(int i = 0; i < task.size(); i++) {
                        System.out.printf("%d. %s\n", i+1, task.get(i));
                    }
                }
            }else if(user_input.startsWith("mark ")){
                int index = Integer.parseInt(user_input.substring(5).trim()) - 1;
                Task t = task.get(index);
                t.mark();
                System.out.println("Nice! I've marked this task as done:");
                System.out.println("  " + t);

            } else if (user_input.startsWith("unmark ")) {
                int idx = Integer.parseInt(user_input.substring(7).trim()) - 1;
                Task t = task.get(idx);
                t.unmark();
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println("  " + t);

            } else if(user_input.equals("sing")) {
                System.out.println("I get money, I'm a star\n" +
                        "Star, star, star, star, star, star");
            } else if(!user_input.isEmpty()){
                Task t = new Task(user_input);
                task.add(t);
                System.out.println("added: " + user_input);
            }
        }
    }
}
