//@@author Cikguseven-reused
//Reused from AddressBook-Level 4 (https://github.com/se-edu/addressbook-level4)
// with minor modifications

package seedu.classmanager.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandHistoryTest {
    private CommandHistory history;

    @BeforeEach
    public void setUp() {
        history = new CommandHistory();
    }

    @Test
    public void constructor_withCommandHistory_copiesCommandHistory() {
        final CommandHistory commandHistoryWithA = new CommandHistory();
        commandHistoryWithA.add("a");

        assertEquals(commandHistoryWithA, new CommandHistory(commandHistoryWithA));
    }

    @Test
    public void add() {
        final String validCommand = "clear";
        final String invalidCommand = "adds Alice";

        history.add(validCommand);
        history.add(invalidCommand);
        assertEquals(Arrays.asList(validCommand, invalidCommand), history.getHistory());
    }

    @Test
    public void equals() {
        final CommandHistory commandHistoryWithA = new CommandHistory();
        commandHistoryWithA.add("a");
        final CommandHistory anotherCommandHistoryWithA = new CommandHistory();
        anotherCommandHistoryWithA.add("a");
        final CommandHistory commandHistoryWithB = new CommandHistory();
        commandHistoryWithB.add("b");

        // same object -> returns true
        assertTrue(commandHistoryWithA.equals(commandHistoryWithA));

        // same values -> returns true
        assertTrue(commandHistoryWithA.equals(anotherCommandHistoryWithA));

        // null -> returns false
        assertFalse(commandHistoryWithA.equals(null));

        // different types -> returns false
        assertFalse(commandHistoryWithA.equals(5.0f));

        // different values -> returns false
        assertFalse(commandHistoryWithA.equals(commandHistoryWithB));
    }

    @Test
    public void hashcode() {
        final CommandHistory commandHistoryWithA = new CommandHistory();
        commandHistoryWithA.add("a");
        final CommandHistory anotherCommandHistoryWithA = new CommandHistory();
        anotherCommandHistoryWithA.add("a");
        final CommandHistory commandHistoryWithB = new CommandHistory();
        commandHistoryWithB.add("b");

        // same values -> returns same hashcode
        assertEquals(commandHistoryWithA.hashCode(), anotherCommandHistoryWithA.hashCode());

        // different values -> returns different hashcode
        assertNotEquals(commandHistoryWithA.hashCode(), commandHistoryWithB.hashCode());
    }
}
//@@author
