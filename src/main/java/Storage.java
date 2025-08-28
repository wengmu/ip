import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Storage {

    /*handle case where data file does not exist at the start*/
    public void save(List<Task> tasks) {
        try {
            File folder = new File("data");
            if (!folder.exists()) {
                folder.mkdir();
            }

            FileWriter writer = new FileWriter("data/wengmu.txt");
            for (int i = 0; i < tasks.size(); i++) {
                Task task = tasks.get(i);
                String line = "";

                if (task instanceof Todo) {
                    line = "T | " + (task.isDone()? "1" : "0") + " | " + task.getDescription();
                } else if (task instanceof Deadline) {
                    Deadline d = (Deadline) task;
                    line = "D | " + (task.isDone()? "1" : "0") + " | " + task.getDescription() + " | " + d.getBy();
                } else if (task instanceof Event) {
                    Event e = (Event) task;
                    line = "E | " + (task.isDone()? "1" : "0") + " | " + task.getDescription() + " | " + e.getFrom() + " | " + e.getTo();
                }

                writer.write(line  + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Something went wrong! ");
        }
     }

     public List<Task> load() {
        List<Task> tasks = new ArrayList<>();

        try {
            File file = new File("data/wengmu.txt");
            if (!file.exists()) {
                return tasks;
            }

            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(" \\| ");

                if (parts.length >= 3) {
                    Task task = null;

                    if (parts[0].equals("T")) {
                        task = new Todo(parts[2]);

                    } else if (parts[0].equals("D") && parts.length >= 4) {
                        task = new Deadline(parts[2], parts[3]);

                    } else if (parts[0].equals("E") && parts.length >= 5) {
                        task = new Event(parts[2], parts[3], parts[4]);
                    }

                    if (task != null) {
                        if (parts[1].equals("1")) {
                            task.mark();
                        }
                        tasks.add(task);
                    }
                }
            }
            scanner.close();

        } catch(IOException e) {

        }

        return tasks;
     }
}
