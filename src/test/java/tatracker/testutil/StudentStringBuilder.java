package tatracker.testutil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import tatracker.model.student.Email;
import tatracker.model.student.Matric;
import tatracker.model.student.Name;
import tatracker.model.student.Phone;
import tatracker.model.student.Student;
import tatracker.model.tag.Tag;

/**
 * A utility class to help with building Student objects.
 */
public class StudentStringBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_MATRIC = "A0123456B";

    private Name name;
    private Phone phone;
    private Email email;
    private Matric matric;
    private Set<Tag> tags;

    public StudentStringBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        matric = new Matric(DEFAULT_MATRIC);
        tags = new HashSet<>();
    }

    /**
     * Initializes the StudentStringBuilder with the data of {@code studentToCopy}.
     */
    public StudentStringBuilder(Student studentToCopy) {
        name = studentToCopy.getName();
        phone = studentToCopy.getPhone();
        email = studentToCopy.getEmail();
        matric = studentToCopy.getMatric();
        tags = new HashSet<>(studentToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Student} that we are building.
     */
    public StudentStringBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Student} that we are building.
     */
    public StudentStringBuilder withTags(String ... tags) {
        this.tags = Arrays.stream(tags)
                .map(Tag::new)
                .collect(Collectors.toCollection(HashSet::new));
        return this;
    }


    /**
     * Sets the {@code Phone} of the {@code Student} that we are building.
     */
    public StudentStringBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Student} that we are building.
     */
    public StudentStringBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Matric} of the {@code Student} that we are building.
     */
    public StudentStringBuilder withMatric(String matric) {
        this.matric = new Matric(matric);
        return this;
    }

    /**
     * Builds the {@code Student} with the fields specified in the current {@code StudentStringBuilder}.
     */
    public Student build() {
        return new Student.StudentBuilder(name, phone, email, matric)
                .withTags(tags)
                .build();
    }

}
