package chia.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 To represents a deadline task - a task that needs to be done by a specific date.
 */
public class Deadline extends Task{
    private LocalDate by;
    private String originalInput;

    /**
     Creates a new deadline task with the given description and deadline
     Attempts to parse the deadline as a LocalDate, falls back to storing as string if the parsing were to fail
     */
    public Deadline(String description, String by) {
        super(description);
        try {
            this.by = LocalDate.parse(by);
            this.originalInput = by;
        } catch(DateTimeParseException e) {
            this.by = null;
            this.originalInput = by;
        }
    }

    /**
     Gets the deadline in a user-friendly format.
     */
    public String getBy() {
        if (by != null) {
            return by.format(DateTimeFormatter.ofPattern("MMM dd yyyy"));
        } else {
            return originalInput;
        }
    }

    /**
     Gets the original deadline input for file storage purposes
     */
    public String getByFile() {
        return originalInput;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + getBy() + " )";
    }
}
