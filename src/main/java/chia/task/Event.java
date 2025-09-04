package chia.task;

/**
 * Represents an event task that occurs during a specific time period.
 */

public class Event extends Task {
    private final String from;
    private final String to;

    /**
     * constuctor to initialise an event object
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }


    /**
     * Get the start time of the event
     */
    public String getFrom() {
        return from;
    }

    /**
     * Get the end time of the even
     */
    public String getTo() {
        return to;
    }
    
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.from + " to: " + this.to + ")";
    }
}
