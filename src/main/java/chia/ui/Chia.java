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
    private static final String TAG_COMMAND = "tag ";
    private static final String UNTAG_COMMAND = "untag ";
    private static final String FIND_TAG_COMMAND = "find-tag ";


    //keyword length
    private static final int FROM_LENGTH = 7;
    private static final int TO_LENGTH = 5;

    //length if the command prefixes
    private static final int MARK_LENGTH = 5;
    private static final int TODO_LENGTH = 5;
    private static final int DEADLINE_LENGTH = 9;
    private static final int EVENT_LENGTH = 6;
    private static final int DELETE_LENGTH = 7;
    private static final int TAG_LENGTH = 4;
    private static final int UNTAG_LENGTH = 6;
    private static final int FIND_TAG_LENGTH = 9;

    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    public Chia() {
        ui = new Ui();
        storage = new Storage();
        tasks = new TaskList(storage.load());
    }

    /**
     * Handles the tag command to add tags to existing tasks.
     * @param input the full user input containing "tag", task number, and tags
     * @return confirmation message with updated task
     */
    private String handleTag(String input) {
        String details = input.substring(TAG_LENGTH).trim();
        String[] parts = details.split("\\s+");

        if (parts.length < 2) {
            return "Please specify a task number and tags. Format: tag [number] #tag1";
        }

        int index = Integer.parseInt(parts[0]) - 1;
        Task task = tasks.get(index);

        for (int i = 1; i < parts.length; i++) {
            if (parts[i].startsWith("#")) {
                String tag = parts[i].substring(1);
                task.addTag(tag);
            }
        }

        storage.save(tasks.getAll());
        return "Added tags to: " + task;
    }

    /**
     * Handles the untag command to remove tags from existing tasks.
     * @param input the full user input containing "untag", task number, and tags
     * @return confirmation message with updated task
     */
    private String handleUntag(String input) {
        String details = input.substring(UNTAG_LENGTH).trim();
        String[] parts = details.split("\\s+");

        if (parts.length < 2) {
            return "Please specify a task number and tags. Format: untag [number] #tag1 #tag2";
        }

        int index = Integer.parseInt(parts[0]) - 1;
        Task task = tasks.get(index);

        for (int i = 1; i < parts.length; i++) {
            if (parts[i].startsWith("#")) {
                String tag = parts[i].substring(1);
                task.removeTag(tag);
            }
        }
        storage.save(tasks.getAll());
        return "Removed tags from: " + task;
    }

    /**
     * Handles the find-tag command to search tasks by tags.
     * @param input the full user input containing "find-tag" and tag names
     * @return list of matching tasks
     */
    private String handleFindTag(String input) {
        String details = input.substring(FIND_TAG_LENGTH).trim();
        String[] tagParts = details.split("\\s+");

        StringBuilder result = new StringBuilder("Here are the matching tasks:\n");
        int count = 0;

        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            boolean hasMatchingTag = false;

            for (String tagPart : tagParts) {
                if (tagPart.startsWith("#")) {
                    String tag = tagPart.substring(1);
                    if (task.hasTag(tag)) {
                        hasMatchingTag = true;
                        break;
                    }
                }
            }

            if (hasMatchingTag) {
                result.append((i + 1)).append(". ").append(task).append("\n");
                count++;
            }
        }

        if (count == 0) {
            return "No tasks found with the specified tags.";
        }

        return result.toString();
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
            return "Please provide a todo description! Example: todo read book";
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
            return "Please provide a deadline description! Example: deadline submit report /by 2024-12-01 2359";
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
            return "Please provide an event description! Example: event meeting /from Mon 2pm /to Mon 4pm";
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
            } else if (input.startsWith(TAG_COMMAND)) {
                return handleTag(input);
            } else if (input.startsWith(UNTAG_COMMAND)) {
                return handleUntag(input);
            } else if (input.startsWith(FIND_TAG_COMMAND)) {
                return handleFindTag(input);
            } else {
                return "Unknown command! Try: list, todo, deadline, event, mark, delete, tag, find-tag, or bye";
            }
        } catch (Exception e) {
            return "Something went wrong: " + e.getMessage();
        }
    }
}
