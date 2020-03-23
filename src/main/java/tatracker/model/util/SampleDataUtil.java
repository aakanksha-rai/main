package tatracker.model.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import tatracker.model.ReadOnlyTaTracker;
import tatracker.model.TaTracker;
import tatracker.model.student.Email;
import tatracker.model.student.Matric;
import tatracker.model.student.Name;
import tatracker.model.student.Phone;
import tatracker.model.student.Student;
import tatracker.model.student.Student.StudentBuilder;
import tatracker.model.tag.Tag;

/**
 * Contains utility methods for populating {@code TaTracker} with sample data.
 */
public class SampleDataUtil {

    public static List<Student> getSampleStudents() {
        return Arrays.asList(
                new StudentBuilder(new Name("Alex Yeoh"),
                        new Phone("87438807"),
                        new Email("alexyeoh@example.com"),
                        new Matric("A0187945J"))
                        .withTags(getTagSet("friends"))
                        .build(),

                new StudentBuilder(new Name("Bernice Yu"),
                        new Phone("99272758"),
                        new Email("berniceyu@example.com"),
                        new Matric("A0181137L"))
                        .withTags(getTagSet("colleagues", "friends"))
                        .build(),

                new StudentBuilder(new Name("Charlotte Oliveiro"),
                        new Phone("93210283"),
                        new Email("charlotte@example.com"),
                        new Matric("A0187565N"))
                        .withTags(getTagSet("neighbours"))
                        .build(),

                new StudentBuilder(new Name("David Li"),
                        new Phone("91031282"),
                        new Email("lidavid@example.com"),
                        new Matric("A0186153P"))
                        .withTags(getTagSet("family"))
                        .build(),

                new StudentBuilder(new Name("Irfan Ibrahim"),
                        new Phone("92492021"),
                        new Email("irfan@example.com"),
                        new Matric("A0180474R"))
                        .withTags(getTagSet("classmates"))
                        .build(),

                new StudentBuilder(new Name("Roy Balakrishnan"),
                        new Phone("92624417"),
                        new Email("royb@example.com"),
                        new Matric("A0187613T"))
                        .withTags(getTagSet("colleagues"))
                        .build(),

                new StudentBuilder(new Name("Jeffry Lum"),
                        new Phone("65162727"),
                        new Email("Jeffry@u.nus.edu"),
                        new Matric("A0195558H"))
                        .withTags(getTagSet("tutors"))
                        .build()
        );
    }

    public static ReadOnlyTaTracker getSampleTaTracker() {
        TaTracker sampleTat = new TaTracker();
        getSampleStudents().forEach(sampleTat::addStudent);
        return sampleTat;
    }

    /**
     * Converts a list of tag names into a set of {@code Tags}.
     */
    private static Set<Tag> getTagSet(String ... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toCollection(HashSet::new));
    }

}
