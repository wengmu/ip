package chia;

import chia.task.*;
import chia.ui.*;
import chia.storage.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Chia {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    public Chia() {
        ui = new Ui();
        storage = new Storage();
        tasks = new TaskList(storage.load());
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

    public static void main(String[] args) {
        new Chia().run();
    }
}
