package chia.task;

/**
  Represents a todo task - a task without time constraint
 */

public class Todo extends Task {
    /**
    Creates a new todo task with the given description.
     */
    public Todo(String description) {
        super(description);
    }

    public String toString() {
        return "[T]" + super.toString();
    }
}
