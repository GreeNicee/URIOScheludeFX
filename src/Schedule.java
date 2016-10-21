import ru.greenstudio.urioschedulefx.model.Lesson;

import java.util.ArrayList;
import java.util.List;

public class Schedule {
    private List<Lesson> maxGroupLessons;
    private List<Lesson> actualGroupLessons;

    private List<Lesson> maxTeacherLessons;
    private List<Lesson> actualTeacherLessons;

    private String name;
    private Day[] days;//7 дней

    private class Day {
        private String name;//понедельник, вторник и т д
        private ArrayList<Lecture> lectures;//7 пар

        public Day(String name, ArrayList<Lecture> lectures) {
            this.name = name;
            this.lectures = lectures;
        }
    }

    private class Lecture {//Хранилище всех пар для всхе групп для конкретного номера пары (на первой паре такие то предметы и т д)
        private String numName;//номер пары
        private List<String> usedCabs;//использованные аудитории
        private ArrayList<SchLesson> groupsLessons;


        public Lecture() {
            this(null, null);
        }

        public Lecture(String numName, ArrayList<SchLesson> groupsLessons) {
            this.numName = numName;

            this.usedCabs = new ArrayList<String>();

            this.groupsLessons = groupsLessons;
        }

        public ArrayList<SchLesson> getGroupsLessons() {
            return groupsLessons;
        }

        public void setGroupsLessons(ArrayList<SchLesson> groupsLessons) {
            this.groupsLessons = groupsLessons;
        }

    }

    private class SchLesson {//Сама пара для каждой группы со всей инфой
        private String cabName;
        private String groupName;
        private String lessonName;
        private String teacherName;

        public SchLesson() {
            this(null, null, null, null);
        }

        public SchLesson(String cabName, String groupName, String lessonName, String teacherName) {
            this.cabName = cabName;
            this.groupName = groupName;
            this.lessonName = lessonName;
            this.teacherName = teacherName;
        }

        public String getCabName() {
            return cabName;
        }

        public void setCabName(String cabName) {
            this.cabName = cabName;
        }

        public String getGroupName() {
            return groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        public String getLessonName() {
            return lessonName;
        }

        public void setLessonName(String lessonName) {
            this.lessonName = lessonName;
        }

        public String getTeacherName() {
            return teacherName;
        }

        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
        }
    }

}
