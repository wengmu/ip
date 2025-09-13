package chia;

import chia.task.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages a collection of tasks with operations to add, remove, and retrieve tasks
 */
public class TaskList {
    private List<Task> tasks;

    /**
     * creates an empty TaskList.
     */
    public TaskList() {
        tasks = new ArrayList<>();
    }

    /**
     * Creates a TaskList initialised with a given list of tasks
     */
    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to the end of the task list.
     */
    public void add(Task task) {
        assert task != null : "You cannot add a null task to the TaskList!";

        tasks.add(task);
    }

    /*
    Remove and return the task at specified index
     */
    public Task remove(int index) {
        assert index >= 0 : "Reminder: Task index cannot be negative";
        assert index < tasks.size() : "Task index out of bounds: " + index + ", Size: " + tasks.size();

        return tasks.remove(index);
    }

    //check if the task list is empty
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    public List<Task> getAll() {
        return tasks;
    }

    // Return the task at a specified
    public Task get(int index) {
        assert index >= 0 : "Reminder: Task index cannot be negative";
        assert index < tasks.size() : "Task index out of bounds: " + index + ", Size: " + tasks.size();

        return tasks.get(index);
    }

    // Return the number of tasks in the list
    public int size() {
        return tasks.size();
    }

    public TaskList find(String keyword) {
        assert keyword != null : "The search keyword cannot be null";

        List<Task> matchingTasks = new ArrayList<>();
        for (Task task: tasks) {
            if (task.getDescription().contains(keyword)) {
                matchingTasks.add(task);
            }
        }
        return new TaskList(matchingTasks);
    }
}
