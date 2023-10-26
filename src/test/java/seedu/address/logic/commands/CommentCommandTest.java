package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.student.Comment;
import seedu.address.model.student.StudentNumber;
import seedu.address.testutil.TypicalStudents;

public class CommentCommandTest {

    private Model model = new ModelManager(TypicalStudents.getTypicalAddressBook(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_commentSuccess() throws CommandException {

        // Define student number and comment
        StudentNumber studentNumber = new StudentNumber("A0239123A");
        Comment comment = new Comment("Struggling with tutorials");

        // Create a CommentCommand
        CommentCommand commentCommand = new CommentCommand(studentNumber, comment);

        // Execute the CommentCommand
        CommandResult result = commentCommand.execute(model, commandHistory);

        // Check if the result message matches the expected message
        assertEquals("Comment added successfully.", result.getFeedbackToUser());

        // Check if the student's comment was updated
        assertEquals(comment, model.getStudent(studentNumber).getComment());
    }

    @Test
    public void execute_nonexistentStudentNumber_throwsCommandException() {

        // Define a student number and comment
        StudentNumber studentNumber = new StudentNumber("A1234567M");
        Comment comment = new Comment("Struggling with tutorials");

        // Create a CommentCommand
        CommentCommand commentCommand = new CommentCommand(studentNumber, comment);

        // Execute the CommentCommand and expect a CommandException
        try {
            commentCommand.execute(model, commandHistory);
        } catch (CommandException e) {
            assertEquals("The student number provided does not exist here.", e.getMessage());
        }
    }

    public void equals() {
        StudentNumber studentNumber = new StudentNumber("A0239123A");
        Comment comment = new Comment("Struggling with tutorials");
        CommentCommand commentCommand = new CommentCommand(studentNumber, comment);
        CommentCommand diffCommentCommand = new CommentCommand(studentNumber, new Comment("Different comment"));

        // same object -> returns true
        assertEquals(commentCommand, commentCommand);

        // same values -> returns true
        assertEquals(commentCommand, new CommentCommand(studentNumber, comment));

        // different types -> returns false
        assertEquals(commentCommand, 1);

        // null -> returns false
        assertEquals(commentCommand, null);

        // different student -> returns false
        assertEquals(commentCommand, diffCommentCommand);

        // different comment -> returns false
        assertEquals(commentCommand, new CommentCommand(studentNumber, new Comment("Different comment")));
    }
}
