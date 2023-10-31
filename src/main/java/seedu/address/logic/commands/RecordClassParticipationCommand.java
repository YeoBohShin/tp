package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARTICIPATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL_SESSION;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDENTS;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentNumber;

/**
 * Records the class participation for a student in a specific tutorial session.
 */
public class RecordClassParticipationCommand extends Command {

    public static final String COMMAND_WORD = "class-part";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Records the class participation of a student in a specific tutorial session.\n"
            + "Parameters: "
            + PREFIX_STUDENT_NUMBER + "STUDENT_NUMBER "
            + PREFIX_TUTORIAL_SESSION + "TUTORIAL_SESSION "
            + PREFIX_PARTICIPATION + "PARTICIPATION\n"
            + "Example: "
            + COMMAND_WORD + " " + PREFIX_STUDENT_NUMBER + "A0123456X "
            + PREFIX_TUTORIAL_SESSION + "1 " + PREFIX_PARTICIPATION + "true";

    public static final String MESSAGE_SUCCESS = "Recorded participation for Student: %1$s\n"
            + "Here are the details:\n";

    private final StudentNumber studentNumber;
    private final int sessionNumber;
    private final boolean hasParticipated;

    /**
     * Creates an RecordPartCommand to record the specified {@code Student}'s participation
     */
    public RecordClassParticipationCommand(StudentNumber studentNumber, int sessionNumber, boolean hasParticipated) {
        this.studentNumber = studentNumber;
        this.sessionNumber = sessionNumber;
        this.hasParticipated = hasParticipated;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory)
            throws CommandException, IllegalValueException {
        requireNonNull(model);

        if (!model.hasStudent(new Student(studentNumber))) {
            throw new CommandException(Messages.MESSAGE_NONEXISTENT_STUDENT_NUMBER);
        }

        Student studentToMark = model.getStudent(studentNumber);
        Student markedStudent = studentToMark.copy();
        markedStudent.markClassParticipation(this.sessionNumber, this.hasParticipated);
        model.setStudent(studentToMark, markedStudent);

        if (model.isSelectedStudent(markedStudent)) {
            model.setSelectedStudent(markedStudent);
        }

        model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        model.commitAddressBook();

        return new CommandResult(String.format(MESSAGE_SUCCESS, studentNumber)
                + markedStudent.getClassDetails().displayParticipation());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RecordClassParticipationCommand)) {
            return false;
        }

        RecordClassParticipationCommand otherSetGradeCommand = (RecordClassParticipationCommand) other;
        return studentNumber.equals(otherSetGradeCommand.studentNumber)
                && sessionNumber == otherSetGradeCommand.sessionNumber
                && hasParticipated == otherSetGradeCommand.hasParticipated;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("studentNumber", studentNumber)
                .add("sessionNumber", sessionNumber)
                .add("hasParticipated", hasParticipated)
                .toString();
    }
}
