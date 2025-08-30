package chia;

import chia.task.Task;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TaskListTest {
    @Test
    public void testIsEmpty() {
        TaskList list = new TaskList();
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }

    @Test
    public void addAndGetTasks() {
        TaskList list = new TaskList();
        Task t = new Task("read book");
        list.add(t);

        assertFalse(list.isEmpty());
        assertEquals(1, list.size());
        assertEquals("[ ] read book", list.get(0).toString());
    }

    @Test
    public void removeItem() {
        TaskList list = new TaskList();
        list.add(new Task("a"));
        list.add(new Task("b"));

        Task removed = list.remove(0);
        assertEquals("a", removed.getDescription());
        assertEquals(1, list.size());
        assertEquals("b", list.get(0).getDescription());
    }

    @Test
    public void removeInvalidIndex() {
        TaskList list = new TaskList();
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(0));
    }
}
