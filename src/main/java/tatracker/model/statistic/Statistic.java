package tatracker.model.statistic;

import javafx.collections.ObservableList;

import tatracker.model.ReadOnlyTaTracker;
import tatracker.model.group.Group;
import tatracker.model.module.Module;
import tatracker.model.session.SessionType;
import tatracker.model.session.UniqueSessionList;
import tatracker.model.student.Rating;
import tatracker.model.student.Student;
import tatracker.model.student.UniqueStudentList;
import tatracker.ui.StatisticWindow;

/**
 * A data container that stores the statistic of TA-Tracker.
 * The UI classes will read statistics from this class to display statistics data in the Statistics window.
 */
public class Statistic {

    public static final String ALL_MODULES_STRING = "ALL MODULES";

    public final int[] numHoursPerCategory = new int[SessionType.NUM_SESSION_TYPES];
    public final int[] studentRatingBinValues = new int[Rating.RANGE];
    public final RatedStudent[] worstStudents = new RatedStudent[StatisticWindow.NUM_STUDENTS_TO_DISPLAY];
    public final String targetModuleCode;

    private final ReadOnlyTaTracker taTracker;

    public Statistic(ReadOnlyTaTracker taTracker, String targetModuleCode) {

        this.taTracker = taTracker;

        UniqueSessionList fList = new UniqueSessionList();
        UniqueStudentList sList = new UniqueStudentList();

        fList.setSessions(taTracker.getDoneSessionList());


        // If targetModule is not null, filter by target module.
        if (targetModuleCode != null) {
            this.targetModuleCode = targetModuleCode;
            fList = fList.getSessionsOfModuleCode(targetModuleCode);

            ObservableList<Module> modules = taTracker.getModuleList();
            for (Module m : modules) {
                if (!m.getIdentifier().toUpperCase().equals(targetModuleCode.toUpperCase())) {
                    continue;
                }

                for (Group group : m.getGroupList()) {
                    for (Student student : group.getStudentList()) {
                        sList.add(student);
                    }
                }
            }
        } else {
            this.targetModuleCode = ALL_MODULES_STRING;
            sList.setStudents(taTracker.getCompleteStudentList());
        }

        for (int i = 0; i < numHoursPerCategory.length; ++i) {
            this.numHoursPerCategory[i] = fList.getSessionsOfType(SessionType.getSessionTypeById(i))
                    .getTotalDuration().toHoursPart();
        }

        for (int i = 0; i < studentRatingBinValues.length; ++i) {
            this.studentRatingBinValues[i] = sList.getStudentsOfRating(new Rating(Rating.MIN_RATING + i)).size();
        }

        // Setup worst students
        sList.sortByRatingAscending();

        for (int i = 0; i < worstStudents.length; ++i) {
            if (i < sList.size()) {
                worstStudents[i] = new RatedStudent(sList.get(i));
            } else {
                worstStudents[i] = new RatedStudent();
            }
        }
    }

    public int getTotalHours() {
        int total = 0;
        for (int h : this.numHoursPerCategory) {
            total += h;
        }
        return total;
    }

    public int getTotalEarnings() {
        return getTotalHours() * taTracker.getRate();
    }

    /**
     * Represents a Statistics entry containing a Student's name and their associated rating.
     */
    public static class RatedStudent {
        private final Student student;
        private final String fullName;
        private final int rating;

        public RatedStudent() {
            this.student = null;
            this.fullName = "";
            this.rating = 0;
        }

        public RatedStudent(Student student) {
            this.student = student;
            this.fullName = student.getName().fullName;
            this.rating = student.getRating().value;
        }

        public String getFullName() {
            return fullName;
        }

        public int getRating() {
            return rating;
        }
    }
}

