package chia.task;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/*
 Use for representing a task with a description and completion status.
 Each task has a description and can be marked as done or not done.
 */

public class Task {
    private final String description;
    private boolean isDone;
    private Set<String> tags;

    /**
     * Creates a new task with the given description.
     * The task is initially marked as not done.
     */
    public Task(String description) {
        assert description != null : "Aiyo, description cannot be null, my friend";
        assert !description.trim().isEmpty() : "NOOOO, the description cannot be empty!!!";

        this.description = description;
        this.isDone = false;
        this.tags = new HashSet<>();
    }

    /**
     * Adds a tag to the task.
     */
    public void addTag(String tag) {
        assert tag != null : "Tag cannot be null";
        assert !tag.trim().isEmpty() : "Tag cannot be empty";

        String cleanTag = tag.toLowerCase().replaceAll("[^a-zA-Z0-9]", "");
        if (!cleanTag.isEmpty()) {
            tags.add(cleanTag);
        }
    }

    /**
     * Removes a tag from this task.
     */
    public void removeTag(String tag) {
        tags.remove(tag.toLowerCase());
    }

    /**
     * Checks if this task has the given tag.
     */
    public boolean hasTag(String tag) {
        return tags.contains(tag.toLowerCase());
    }

    /**
     * Returns all tags for this task.
     */
    public Set<String> getTags() {
        return new HashSet<>(tags);
    }

    /**
     * Returns tags formatted for display (e.g., "#fun #urgent").
     */
    public String getTagsString() {
        if (tags.isEmpty()) {
            return "";
        }
        return tags.stream()
                .map(tag -> "#" + tag)
                .collect(Collectors.joining(" "));
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
        String baseString = "[" + (isDone ? "X" : " ") + "] " + this.description;
        String tagsString = getTagsString();
        return tagsString.isEmpty() ? baseString : baseString + " " + tagsString;
    }
}
