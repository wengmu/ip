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
        Storage storage = new Storage();
        List<Task> task = storage.load();

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
                storage.save(task);
                System.out.println("Nice! I've marked this task as done:");
                System.out.println("  " + t);

            } else if (user_input.startsWith("unmark ")) {
                int idx = Integer.parseInt(user_input.substring(7).trim()) - 1;
                Task t = task.get(idx);
                t.unmark();
                storage.save(task);
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println("  " + t);

            } else if (user_input.startsWith("todo")) {
                String description = user_input.substring(4).trim();

                //handling of errors
                if (description.isEmpty()) {
                    System.out.println("Hold up! The description cannot be empty so you SHALL NOT PASS!");
                    continue;
                }

                Task t = new Todo(description);
                task.add(t);
                storage.save(task);
                System.out.println("Got it. I've added this task:");
                System.out.println(" " + t);
                System.out.printf("You have a total of %d in the task list\n", task.size());

            } else if (user_input.startsWith("deadline")) {
                String main_body = user_input.substring(8).trim();

                if (main_body.isEmpty()) {
                    System.out.println("WAIT!!! The description is empty");
                    continue;
                }

                int separation = main_body.indexOf(" /by ");
                String description = (separation == -1)? main_body : main_body.substring(0, separation).trim();
                String by = (separation == -1)? "" : main_body.substring(separation + 5).trim();
                Task t = new Deadline(description, by);
                task.add(t);
                storage.save(task);

                System.out.println("Got it. I've added this task:");
                System.out.println(" " + t);
                System.out.printf("Now you have %d tasks in the list.\n", task.size());
            } else if (user_input.startsWith("event")) {
                String main_body = user_input.substring(5).trim();

                if (main_body.isEmpty()) {
                    System.out.println("The description of an event cannot be empty.");
                    continue;
                }

                int fromIndex = main_body.indexOf(" /from ");
                int toIndex = main_body.indexOf(" /to ");
                String description = main_body;
                String from = "";
                String to = "";
                if (fromIndex != -1) {
                    description = main_body.substring(0, fromIndex).trim();
                    if (toIndex != -1 && toIndex > fromIndex) {
                        from = main_body.substring(fromIndex + 7, toIndex).trim();
                        to = main_body.substring(toIndex + 5).trim();
                    } else {
                        from = main_body.substring(fromIndex + 7).trim();
                    }
                }

                Task t = new Event(description, from, to);
                task.add(t);
                storage.save(task);
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + t);
                System.out.printf("Now you have %d tasks in the list.\n", task.size());

            } else if(user_input.startsWith("delete ")) {
                try {
                    String indexStr = user_input.substring(7).trim();
                    if (indexStr.isEmpty()) {
                        System.out.println("Please specify which task you would like to delete.");
                        continue;
                    }

                    int index = Integer.parseInt(indexStr) - 1;

                    if (index < 0 || index >= task.size()) {
                        System.out.println("Task number " + (index + 1) + " does not exist.");
                        continue;
                    }

                    Task removedTask = task.remove(index);
                    storage.save(task);
                    System.out.println("Noted. I've removed this task: \n" + " " + removedTask);
                    System.out.printf("Now you have %d tasks in the list.\n", task.size());

                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid task number.");
                }
            }

            else if(user_input.equals("sing")) {
                System.out.println("I get money, I'm a star\n" +
                        "Star, star, star, star, star, star");
            } else if(!user_input.isEmpty()){
                Task t = new Task(user_input);
                task.add(t);
                storage.save(task);
                System.out.println("added: " + user_input);
            } else if (user_input.isEmpty()) {
                continue;
            } else {
                System.out.println("What the hell are you even saying??!");
            }
        }
    }
}
