package seedu.address.model.student;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.tag.Tag;
import seedu.address.storage.JsonAdaptedClassDetails;

/**
 * Represents a Student in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Student {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    private final StudentNumber studentNumber;
    private final ClassDetails classDetails;

    // Data fields
    private final Comment comment;
    private final Set<Tag> tags = new HashSet<>();


    /**
     * Every field must be present and not null.
     */
    public Student(StudentNumber studentNumber) {
        requireAllNonNull(studentNumber);
        this.name = null;
        this.studentNumber = studentNumber;
        this.phone = null;
        this.email = null;
        this.classDetails = null;
        this.comment = null;
    }

    /**
     * Every field must be present and not null.
     */
    public Student(Name name, Phone phone, Email email, StudentNumber studentNumber,
                   ClassDetails classDetails, Set<Tag> tags, Comment comment) {
        requireAllNonNull(name, phone, email, studentNumber, classDetails, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.studentNumber = studentNumber;
        this.classDetails = classDetails;
        this.tags.addAll(tags);
        this.comment = comment;
    }

    /**
     * Returns a deep copy of a Student.
     * @return A deep copy of {@code Student}.
     */
    public Student copy() throws IllegalValueException {
        return new Student(this.name, this.phone, this.email, this.studentNumber,
                this.classDetails.copy(), this.tags, this.comment);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public StudentNumber getStudentNumber() {
        return studentNumber;
    }

    public ClassDetails getClassDetails() {
        return classDetails;
    }
    public String getClassNumber() {
        return this.classDetails.getClassNumber();
    }

    public JsonAdaptedClassDetails getJsonAdaptedClassDetails() {
        return classDetails.getJsonAdaptedClassDetails();
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public Comment getComment() {
        return comment;
    }

    /**
     * Returns true if both persons have the same student number.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameStudent(Student otherStudent) {
        if (otherStudent == this) {
            return true;
        }

        return otherStudent != null
                && otherStudent.getStudentNumber().equals(getStudentNumber());
    }

    /**
     * Marks the specific tutorial as present.
     */
    public void markPresent(Index tutNum) {
        this.classDetails.markPresent(tutNum);
    }

    /**
     * Marks the specific tutorial as absent.
     */
    public void markAbsent(Index tutNum) {
        this.classDetails.markAbsent(tutNum);
    }

    /**
     * Marks student as having participated in the specific tutorial.
     */
    public void markClassParticipation(int sessionNumber, boolean hasParticipated) throws CommandException {
        this.classDetails.recordClassParticipation(sessionNumber, hasParticipated);
    }

    /**
     * Marks student as having participated in the specific tutorial.
     */
    public void setGrade(int assignmentNumber, int grade) throws CommandException {
        this.classDetails.setGrade(assignmentNumber, grade);
    }


    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Student)) {
            return false;
        }

        Student otherStudent = (Student) other;

        return studentNumber.equals(otherStudent.studentNumber);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, studentNumber, classDetails, tags, comment);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("student number", studentNumber)
                .add("class number", classDetails)
                .add("tags", tags)
                .add("comment", comment)
                .toString();
    }

}
