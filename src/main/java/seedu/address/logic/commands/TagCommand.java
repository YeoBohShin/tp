package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;


/**
 * Changes the tags of an existing person in the address book.
 */
public class TagCommand extends Command {

    public static final String COMMAND_WORD = "tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the tags of the person identified "
            + "by the student number. "
            + "Existing tags will be overwritten by the input.\n"
            + "Parameters: Student number (must be exist in address book) "
            + "/t [LABEL]\n"
            + "Example: " + COMMAND_WORD + " A1234567N "
            + "/t smart.";
    public static final String MESSAGE_ADD_TAG_SUCCESS = "Added tag to Person: %1$s";
    public static final String MESSAGE_DELETE_TAG_SUCCESS = "Removed tag from Person: %1$s";
    public static final String MESSAGE_TAG_FAILED = COMMAND_WORD
            + ": There was an issue tagging the student.\n Please check "
            + "that the index of the student exists or each label has "
            + "the “/t ” prefix.";
    private final Index index;
    private final Set<Tag> tags;

    /**
     * @param index of the person in the filtered person list to edit the remark
     * @param tags of the person to be updated to
     */
    public TagCommand(Index index, Set<Tag> tags) {
        requireAllNonNull(index, tags);

        this.index = index;
        this.tags = tags;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = new Person(
                personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), this.tags);

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    /**
     * Generates a command execution success message based on whether
     * the tag is added to or removed from
     * {@code personToEdit}.
     */
    private String generateSuccessMessage(Person personToEdit) {
        String message = !tags.isEmpty() ? MESSAGE_ADD_TAG_SUCCESS : MESSAGE_DELETE_TAG_SUCCESS;
        return String.format(message, Messages.format(personToEdit));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TagCommand)) {
            return false;
        }

        TagCommand e = (TagCommand) other;
        return index.equals(e.index)
                && tags.equals(e.tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("tags", tags)
                .toString();
    }
}
