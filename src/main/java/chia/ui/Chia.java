package chia.ui;

import chia.TaskList;
import chia.storage.Storage;
import chia.task.Task;
import chia.task.Todo;
import chia.task.Deadline;
import chia.task.Event;

public class Chia {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    public Chia() {
        ui = new Ui();
        storage = new Storage();
        tasks = new TaskList(storage.load());
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        try {
            if (input.equals("bye")) {
                return "Goodbye. Hope you have a great day ahead!";

            } else if (input.equals("list")) {
                if (tasks.size() == 0) {
                    return "There is no work to do. Congrats!";
                } else {
                    StringBuilder result = new StringBuilder("Here are the tasks in your list:\n");
                    for (int i = 0; i < tasks.size(); i++) {
                        result.append((i + 1)).append(". ").append(tasks.get(i).toString()).append("\n");
                    }
                    return result.toString();
                }

            } else if (input.startsWith("mark ")) {
                String number = input.substring(5);
                int index = Integer.parseInt(number) - 1;
                Task task = tasks.get(index);
                task.mark();
                storage.save(tasks.getAll());
                return "Nice! I've marked this task as done:\n  " + task;


            } else if (input.startsWith("todo ")) {
                String description = input.substring(5);
                if (description.isEmpty()) {
                    return "Hold up! The description cannot be empty so you SHALL NOT PASS!";
                }
                Task task = new Todo(description);
                tasks.add(task);
                storage.save(tasks.getAll());
                return "Got it. I've added this task:\n " + task +
                        "\nYou have a total of " + tasks.size() + " in the task list";

            } else if (input.startsWith("deadline ")) {
                String details = input.substring(9).trim();
                if (details.isEmpty()) {
                    return "WAIT!!! The description is empty";
                }

                int byIndex = details.indexOf(" /by ");
                String description = byIndex == -1 ? details : details.substring(0, byIndex).trim();
                String by = byIndex == -1 ? "" : details.substring(byIndex + 5).trim();

                Deadline deadline = new Deadline(description, by);
                tasks.add(deadline);
                storage.save(tasks.getAll());
                return "Got it. I've added this task:\n " + deadline +
                        "\nNow you have " + tasks.size() + " tasks in the list.";

            } else if (input.startsWith("event ")) {
                String details = input.substring(6).trim();
                if (details.isEmpty()) {
                    return "The description of an event cannot be empty.";
                }

                int fromIndex = details.indexOf(" /from ");
                int toIndex = details.indexOf(" /to ");
                String description = details;
                String from = "";
                String to = "";

                if (fromIndex != -1) {
                    description = details.substring(0, fromIndex).trim();
                    if (toIndex != -1 && toIndex > fromIndex) {
                        from = details.substring(fromIndex + 7, toIndex).trim();
                        to = details.substring(toIndex + 5).trim();
                    } else {
                        from = details.substring(fromIndex + 7).trim();
                    }
                }

                Event event = new Event(description, from, to);
                tasks.add(event);
                storage.save(tasks.getAll());
                return "Got it. I've added this task:\n  " + event +
                        "\nNow you have " + tasks.size() + " tasks in the list.";

            } else if (input.startsWith("delete ")) {
                String number = input.substring(7).trim();
                int index = Integer.parseInt(number) - 1;
                Task task = tasks.remove(index);
                storage.save(tasks.getAll());
                return "Noted. I've removed this task:\n " + task +
                        "\nNow you have " + tasks.size() + " tasks in the list.";

            } else {
                return "I don't know that command!";
            }
        } catch (Exception e) {
            return "Something went wrong: " + e.getMessage();
        }
    }
}
