public class Task {
    private final String description;
    private boolean done;

    public Task(String description){
        this.description = description;
        this.done = false;
    }

    public void mark(){
        this.done = true;
    }

    public void unmark(){
        this.done = false;
    }

    @Override
    public String toString(){
        return "[" + (done? "X": " ") + "] " + this.description;
    }
}
