package chia;

import chia.Parser;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTest {
    @Test
    public void testGetCommand() {
        assertEquals("list", Parser.getCommand("list"));
        assertEquals("todo", Parser.getCommand("todo play violin like Ling Ling"));
        assertEquals("deadline", Parser.getCommand("deadline read Winx Club /by Monday"));
        assertEquals("delete", Parser.getCommand("delete 1"));
    }

    @Test
    public void testGetDetails() {
        assertEquals("", Parser.getDetails("list"));
        assertEquals("play violin like Ling Ling", Parser.getDetails("todo play violin like Ling Ling"));
        assertEquals("read Winx Club /by Monday", Parser.getDetails("deadline read Winx Club /by Monday"));
        assertEquals("1", Parser.getDetails("delete 1"));
    }

    @Test
    public void testGetIndex() {
        assertEquals(0, Parser.getIndex("delete 1"));
        assertEquals(4, Parser.getIndex("mark 5"));
        assertEquals(9, Parser.getIndex("unmark 10"));
    }

    @Test
    public void testGetDetailsWithMultipleSpaces() {
        assertEquals("read  book  now", Parser.getDetails("todo read  book  now"));
    }
}
