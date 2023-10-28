package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.CLASS_NUMBER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CLASS_NUMBER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STUDENT_NUMBER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NONEXISTENT_STUDENT_NUMBER;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.STUDENT_NUMBER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.STUDENT_NUMBER_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.STUDENT_NUMBER_DESC_NONEXISTENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLASS_NUMBER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_NUMBER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_NUMBER_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_STUDENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_STUDENT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditStudentDescriptor;
import seedu.address.model.student.ClassDetails;
import seedu.address.model.student.Email;
import seedu.address.model.student.Name;
import seedu.address.model.student.Phone;
import seedu.address.model.student.StudentNumber;
import seedu.address.testutil.EditStudentDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no student number specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, " " + VALID_STUDENT_NUMBER_BOB, EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, VALID_STUDENT_NUMBER_BOB + INVALID_NAME_DESC,
                Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, VALID_STUDENT_NUMBER_BOB + INVALID_PHONE_DESC,
                Phone.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, VALID_STUDENT_NUMBER_BOB + INVALID_EMAIL_DESC,
                Email.MESSAGE_CONSTRAINTS); // invalid email
        assertParseFailure(parser, VALID_STUDENT_NUMBER_BOB + INVALID_STUDENT_NUMBER_DESC,
                StudentNumber.MESSAGE_CONSTRAINTS); // invalid student number
        assertParseFailure(parser, VALID_STUDENT_NUMBER_BOB + INVALID_CLASS_NUMBER_DESC,
                ClassDetails.MESSAGE_CONSTRAINTS); // invalid class number

        // invalid phone followed by valid email
        assertParseFailure(parser, VALID_STUDENT_NUMBER_BOB + INVALID_PHONE_DESC + EMAIL_DESC_AMY,
                Phone.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser,
                VALID_STUDENT_NUMBER_BOB + INVALID_NAME_DESC
                        + INVALID_EMAIL_DESC + VALID_STUDENT_NUMBER_AMY
                        + VALID_CLASS_NUMBER_AMY + VALID_PHONE_AMY,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        String targetStudentNumber = VALID_STUDENT_NUMBER_BOB;

        String userInput = targetStudentNumber + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + STUDENT_NUMBER_DESC_AMY + CLASS_NUMBER_DESC_AMY;

        EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY).withStudentNumber(VALID_STUDENT_NUMBER_AMY)
                .withClassNumber(VALID_CLASS_NUMBER_AMY).build();
        EditCommand expectedCommand = new EditCommand(new StudentNumber(targetStudentNumber), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        String targetStudentNumber = VALID_STUDENT_NUMBER_BOB;
        String userInput = targetStudentNumber + PHONE_DESC_BOB + EMAIL_DESC_AMY;

        EditCommand.EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_AMY).build();
        EditCommand expectedCommand = new EditCommand(new StudentNumber(targetStudentNumber), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        String targetStudentNumber = VALID_STUDENT_NUMBER_BOB;
        String userInput = targetStudentNumber + NAME_DESC_AMY;
        EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditCommand expectedCommand = new EditCommand(new StudentNumber(targetStudentNumber), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetStudentNumber + PHONE_DESC_AMY;
        descriptor = new EditStudentDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new EditCommand(new StudentNumber(targetStudentNumber), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetStudentNumber + EMAIL_DESC_AMY;
        descriptor = new EditStudentDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
        expectedCommand = new EditCommand(new StudentNumber(targetStudentNumber), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // student number
        userInput = targetStudentNumber + STUDENT_NUMBER_DESC_AMY;
        descriptor = new EditStudentDescriptorBuilder().withStudentNumber(VALID_STUDENT_NUMBER_AMY).build();
        expectedCommand = new EditCommand(new StudentNumber(targetStudentNumber), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // class number
        userInput = targetStudentNumber + CLASS_NUMBER_DESC_AMY;
        descriptor = new EditStudentDescriptorBuilder().withClassNumber(VALID_CLASS_NUMBER_AMY).build();
        expectedCommand = new EditCommand(new StudentNumber(targetStudentNumber), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddCommandParserTest#parse_repeatedNonTagValue_failure()

        // valid followed by invalid
        String targetStudentNumber = VALID_STUDENT_NUMBER_BOB;
        String userInput = targetStudentNumber + INVALID_PHONE_DESC + PHONE_DESC_BOB;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid followed by valid
        userInput = targetStudentNumber + PHONE_DESC_BOB + INVALID_PHONE_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple valid fields repeated
        userInput = targetStudentNumber + PHONE_DESC_AMY + STUDENT_NUMBER_DESC_AMY + CLASS_NUMBER_DESC_AMY
                + EMAIL_DESC_AMY + PHONE_DESC_AMY + STUDENT_NUMBER_DESC_AMY + EMAIL_DESC_AMY
                + PHONE_DESC_BOB + EMAIL_DESC_BOB;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_EMAIL, PREFIX_STUDENT_NUMBER));

        // multiple invalid values
        userInput = targetStudentNumber + INVALID_PHONE_DESC + INVALID_STUDENT_NUMBER_DESC
                + INVALID_CLASS_NUMBER_DESC + INVALID_EMAIL_DESC + INVALID_PHONE_DESC
                + INVALID_STUDENT_NUMBER_DESC + INVALID_CLASS_NUMBER_DESC + INVALID_EMAIL_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_EMAIL, PREFIX_STUDENT_NUMBER));
    }

}
