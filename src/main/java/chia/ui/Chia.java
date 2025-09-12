package chia.ui;

import chia.task.Deadline;
import chia.task.Event;
import chia.TaskList;
import chia.storage.Storage;
import chia.task.Task;
import chia.task.Todo;

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

            } else if (input.startsWith("unmark ")) {
                String number = input.substring(7);
                int index = Integer.parseInt(number) - 1;
                Task task = tasks.get(index);
                task.unmark();
                storage.save(tasks.getAll());
                ui.showUnmarked(task);

            } else if (input.startsWith("todo ")) {
                String description = input.substring(5);
                Task task = new Todo(description);
                tasks.add(task);
                storage.save(tasks.getAll());
                ui.showAdded(task, tasks.size());

            } else if (input.startsWith("deadline ")) {
                String details = input.substring(9);
                int byIndex = details.indexOf(" /by ");
                if (byIndex == -1) {
                    ui.showError("Please use format: deadline <description> /by <date>");
                    continue;
                }
                String description = details.substring(0, byIndex);
                String by = details.substring(byIndex + 5);
                Task task = new Deadline(description, by);
                tasks.add(task);
                storage.save(tasks.getAll());
                ui.showAdded(task, tasks.size());

            } else if (input.startsWith("event ")) {
                String details = input.substring(6);
                int fromIndex = details.indexOf(" /from ");
                int toIndex = details.indexOf(" /to ");
                if (fromIndex == -1) {
                    ui.showError("Please use format: event <description> /from <start> /to <end>");
                    continue;
                }
                String description = details.substring(0, fromIndex);
                String from = details.substring(fromIndex + 7, toIndex);
                String to = details.substring(toIndex + 5);
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

            } else {
                ui.showError("I don't know that command!");
            }
        }
    }
}
