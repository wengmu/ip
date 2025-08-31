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

    public static void main(String[] args) {
        new Chia().run();
    }

    public void run() {
        ui.showWelcome();

        while (true) {
            String input = ui.readCommand();

            if (input.equals("bye")) {
                ui.showBye();
                break;

            } else if (input.equals("list")) {
                ui.showList(tasks);

            } else if (input.startsWith("mark ")) {
                String number = input.substring(5);
                int index = Integer.parseInt(number) - 1;
                Task task = tasks.get(index);
                task.mark();
                storage.save(tasks.getAll());
                ui.showMarked(task);

            } else if (input.startsWith("todo ")) {
                String description = input.substring(5);
                Task task = new Todo(description);
                tasks.add(task);
                storage.save(tasks.getAll());
                ui.showAdded(task, tasks.size());

            } else if (input.startsWith("deadline ")) {
                String details = input.substring(9);
                String[] parts = details.split("/by");
                String description = parts[0].trim();
                String by = parts[1].trim();
                Task task = new Deadline(description, by);
                tasks.add(task);
                storage.save(tasks.getAll());
                ui.showAdded(task, tasks.size());

            } else if (input.startsWith("event ")) {
                String details = input.substring(6);
                String[] parts = details.split("/from");
                String description = parts[0].trim();
                String[] timeParts = parts[1].split("/to");
                String from = timeParts[0].trim();
                String to = timeParts[1].trim();
                Task task = new Event(description, from, to);
                tasks.add(task);
                storage.save(tasks.getAll());
                ui.showAdded(task, tasks.size());

            } else if (input.startsWith("delete ")) {
                String number = input.substring(7);
                int index = Integer.parseInt(number) - 1;
                Task task = tasks.remove(index);
                storage.save(tasks.getAll());
                ui.showDeleted(task, tasks.size());

            } else if (input.startsWith("find ")) {
                String keyword = input.substring(5);
                TaskList matchingTasks = tasks.find(keyword);
                ui.showFindResults(matchingTasks);
            } else {
                ui.showError("I don't know that command!");
            }
        }
    }
}
