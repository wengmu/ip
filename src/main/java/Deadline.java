import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task{
    private LocalDate by;
    private String originalInput;

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

    public String getBy() {
        if (by != null) {
            return by.format(DateTimeFormatter.ofPattern("MMM dd yyyy"));
        } else {
            return originalInput;
        }
    }

    public String getByFile() {
        return originalInput;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + getBy() + " )";
    }
}
