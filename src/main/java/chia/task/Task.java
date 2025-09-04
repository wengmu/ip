package chia.task;

/*
 Use for representing a task with a description and completion status.
 Each task has a description and can be marked as done or not done.
 */

public class Task {
    private final String description;
    private boolean isDone;

    /**
     * Creates a new task with the given description.
     * The task is initially marked as not done.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Marks the task as completed.
     */
    public void mark() {
        this.isDone = true;
    }

    /**
     * Marks the task as incompleted.
     */
    public void unmark() {
        this.isDone = false;
    }

    /**
     * Checks whether this task is completed.
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Gets the description of this task.
     */
    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "[" + (isDone ? "X":" ") + "] " + this.description;
    }
}
