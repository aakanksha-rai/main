package tatracker.testutil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

import tatracker.model.rating.Rating;
import tatracker.model.student.Email;
import tatracker.model.student.Matric;
import tatracker.model.student.Name;
import tatracker.model.student.Phone;
import tatracker.model.student.Student;
import tatracker.model.student.Student.StudentBuilder;
import tatracker.model.tag.Tag;

/**
 * A utility class to help with building Student objects.
 */
public class StudentStringBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_MATRIC = "A0123456B";

    private StudentBuilder studentBuilder;

    public StudentStringBuilder() {
        this.studentBuilder = new StudentBuilder(
                new Name(DEFAULT_NAME),
                new Phone(DEFAULT_PHONE),
                new Email(DEFAULT_EMAIL),
                new Matric(DEFAULT_MATRIC));
    }

    /**
     * Initializes the StudentStringBuilder with the data of {@code studentToCopy}.
     */
    public StudentStringBuilder(Student studentToCopy) {
        this.studentBuilder = new StudentBuilder(studentToCopy);
    }

    /**
     * Sets the {@code Name} of the {@code Student} that we are building.
     */
    public StudentStringBuilder withName(String name) {
        this.studentBuilder.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Student} that we are building.
     */
    public StudentStringBuilder withPhone(String phone) {
        this.studentBuilder.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Student} that we are building.
     */
    public StudentStringBuilder withEmail(String email) {
        this.studentBuilder.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Matric} of the {@code Student} that we are building.
     */
    public StudentStringBuilder withMatric(String matric) {
        this.studentBuilder.setMatric(new Matric(matric));
        return this;
    }

    /**
     * Sets the {@code Rating} of the {@code Student} that we are building.
     */
    public StudentStringBuilder withRating(int rating) {
        this.studentBuilder.setRating(new Rating(rating));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Student} that we are building.
     */
    public StudentStringBuilder withTags(String ... tags) {
        this.studentBuilder.setTags(Arrays.stream(tags)
                .map(Tag::new)
                .collect(Collectors.toCollection(HashSet::new)));
        return this;
    }

    /**
     * Builds the {@code Student} with the fields specified in the current {@code StudentStringBuilder}.
     */
    public Student build() {
        return studentBuilder.build();
    }

}
