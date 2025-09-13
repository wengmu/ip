package chia.ui;

import chia.TaskList;
import chia.storage.Storage;
import chia.task.Task;
import chia.task.Todo;
import chia.task.Deadline;
import chia.task.Event;

public class Chia {
    //command prefixes
    private static final String MARK_COMMAND = "mark ";
    private static final String TODO_COMMAND = "todo ";
    private static final String DEADLINE_COMMAND = "deadline ";
    private static final String EVENT_COMMAND = "event ";
    private static final String DELETE_COMMAND = "delete ";
    private static final String LIST_COMMAND = "list";
    private static final String BYE_COMMAND = "bye";


    //keyword length
    private static final int FROM_LENGTH = 7;
    private static final int TO_LENGTH = 5;

    //length if the command prefixes
    private static final int MARK_LENGTH = 5;
    private static final int TODO_LENGTH = 5;
    private static final int DEADLINE_LENGTH = 9;
    private static final int EVENT_LENGTH = 6;
    private static final int DELETE_LENGTH = 7;

    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    public Chia() {
        ui = new Ui();
        storage = new Storage();
        tasks = new TaskList(storage.load());
    }

    /**
     * Handles the bye command to exit the program.
     * @return goodbye message to the user
     */
    private String handleBye() {
        return "Goodbye. Hope you have a great day ahead!";
    }

    /**
     * Handles the list command to display all tasks.
     * @return formatted string showing all tasks or empty list message
     */
    private String handleList() {
        if (tasks.size() == 0) {
            return "There is no work to do. Congrats!";
        }

        StringBuilder result = new StringBuilder("Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            result.append((i + 1)).append(". ").append(tasks.get(i).toString()).append("\n");
        }
        return result.toString();
    }

    /**
     * Handles the mark command to mark a task as completed.
     * @param input the full user input containing "mark" and task number
     * @return confirmation message with the marked task
     */
    private String handleMark(String input) {
        String number = input.substring(MARK_LENGTH);
        int index = Integer.parseInt(number) - 1;
        Task task = tasks.get(index);
        task.mark();
        storage.save(tasks.getAll());
        return "Nice! I've marked this task as done:\n  " + task;
    }

    /**
     * Handles the todo command to create a new todo task.
     * @param input the full user input containing "todo" and description
     * @return confirmation message with the added task
     */
    private String handleTodo(String input) {
        String description = input.substring(TODO_LENGTH);
        if (description.isEmpty()) {
            return "Hold up! The description cannot be empty so you SHALL NOT PASS!";
        }

        Task task = new Todo(description);
        tasks.add(task);
        storage.save(tasks.getAll());
        return buildTaskAddedMessage(task);
    }

    /**
     * Handles the deadline command to create a new deadline task.
     * Parses the description and due date from the input format "deadline DESCRIPTION /by DATE".
     * @param input the full user input containing "deadline", description, and optional "/by" clause
     * @return confirmation message with the added deadline task
     */
    private String handleDeadline(String input) {
        String details = input.substring(DEADLINE_LENGTH).trim();
        if (details.isEmpty()) {
            return "WAIT!!! The description is empty";
        }

        int byIndex = details.indexOf(" /by ");
        String description = byIndex == -1 ? details : details.substring(0, byIndex).trim();
        String by = byIndex == -1 ? "" : details.substring(byIndex + 5).trim();

        Deadline deadline = new Deadline(description, by);
        tasks.add(deadline);
        storage.save(tasks.getAll());
        return buildTaskAddedMessage(deadline);
    }

    /**
     * Handles the event command to create a new event task.
     * Parses the description, start time, and end time from input format "event DESCRIPTION /from START /to END".
     * @param input the full user input containing "event", description, and optional "/from" and "/to" clauses
     * @return confirmation message with the added event task
     */
    private String handleEvent(String input) {
        String details = input.substring(EVENT_LENGTH).trim();
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
                from = details.substring(fromIndex + FROM_LENGTH, toIndex).trim();
                to = details.substring(toIndex + TO_LENGTH).trim();
            } else {
                from = details.substring(fromIndex + 7).trim();
            }
        }

        Event event = new Event(description, from, to);
        tasks.add(event);
        storage.save(tasks.getAll());
        return buildTaskAddedMessage(event);
    }

    /**
     * Handles the delete command to remove a task from the list.
     * @param input the full user input containing "delete" and task number
     * @return confirmation message with the removed task
     */
    private String handleDelete(String input) {
        String number = input.substring(DELETE_LENGTH).trim();
        int index = Integer.parseInt(number) - 1;
        Task task = tasks.remove(index);
        storage.save(tasks.getAll());
        return "Noted. I've removed this task:\n " + task +
                "\nNow you have " + tasks.size() + " tasks in the list.";
    }

    /**
     * Create a standardized message for when a task is added
     */
    private String buildTaskAddedMessage(Task task) {
        return "Got it. I've added this task:\n " + task +
                "\nNow you have " + tasks.size() + " tasks in the list.";
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        try {
            if (input.equals(BYE_COMMAND)) {
                return handleBye();
            } else if (input.equals(LIST_COMMAND)) {
                return handleList();
            } else if (input.startsWith(MARK_COMMAND)) {
                return handleMark(input);
            } else if (input.startsWith(TODO_COMMAND)) {
                return handleTodo(input);
            } else if (input.startsWith(DEADLINE_COMMAND)) {
                return handleDeadline(input);
            } else if (input.startsWith(EVENT_COMMAND)) {
                return handleEvent(input);
            } else if (input.startsWith(DELETE_COMMAND)) {
                return handleDelete(input);
            } else {
                return "I don't know that command!";
            }
        } catch (Exception e) {
            return "Something went wrong: " + e.getMessage();
        }
    }
}
