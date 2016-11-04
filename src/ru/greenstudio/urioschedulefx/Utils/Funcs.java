package ru.greenstudio.urioschedulefx.Utils;

import javafx.collections.FXCollections;
import ru.greenstudio.urioschedulefx.MainApp;
import ru.greenstudio.urioschedulefx.model.*;

import java.util.List;

public class Funcs {
    public static boolean checkNumeric(String value) {
        String number = value.replaceAll("\\s+", "");
        for (int i = 0; i < number.length(); i++) {
            if (!(((int) number.charAt(i) > 47 && (int) number.charAt(i) <= 57))) {
                return false;
            }
        }
        return true;
    }

    public static List<Lesson> getTeachersLessonsData(MainApp mainApp) {
        List<Lesson> teachersLessonsData = FXCollections.observableArrayList();
        for (int i = 0; i < mainApp.getTeachersData().size(); i++) {
            for (int j = 0; j < mainApp.getTeachersData().get(i).getLessons().size(); j++) {
                if (teachersLessonsData.size() == 0) {
                    teachersLessonsData.add(new Lesson(mainApp.getTeachersData().get(i).getLessons().get(j).getName(),
                            mainApp.getTeachersData().get(i).getLessons().get(j).getHours()));
                } else {
                    boolean boo = false;
                    for (Lesson aTeachersLessonsData : teachersLessonsData) {
                        if (mainApp.getTeachersData().get(i).getLessons().get(j).getName().equals(
                                aTeachersLessonsData.getName())) {
                            aTeachersLessonsData.setHours(aTeachersLessonsData.getHours() +
                                    mainApp.getTeachersData().get(i).getLessons().get(j).getHours());
                            boo = true;
                            break;
                        }
                    }
                    if (!boo) {
                        teachersLessonsData.add(new Lesson(mainApp.getTeachersData().get(i).getLessons().get(j).getName(),
                                mainApp.getTeachersData().get(i).getLessons().get(j).getHours()));
                    }
                }
            }
        }
        return teachersLessonsData;
    }

    public static List<Lesson> getTeachersLessonsData(MainApp mainApp, List<Teacher> teachersData) {
        List<Lesson> teachersLessonsData = FXCollections.observableArrayList();
        for (Teacher aTeachersData : teachersData) {
            for (int j = 0; j < aTeachersData.getLessons().size(); j++) {
                if (teachersLessonsData.size() == 0) {
                    teachersLessonsData.add(new Lesson(aTeachersData.getLessons().get(j).getName(),
                            aTeachersData.getLessons().get(j).getHours()));
                } else {
                    boolean boo = false;
                    for (Lesson aTeachersLessonsData : teachersLessonsData) {
                        if (aTeachersData.getLessons().get(j).getName().equals(
                                aTeachersLessonsData.getName())) {
                            aTeachersLessonsData.setHours(aTeachersLessonsData.getHours() +
                                    aTeachersData.getLessons().get(j).getHours());
                            boo = true;
                            break;
                        }
                    }
                    if (!boo) {
                        teachersLessonsData.add(new Lesson(aTeachersData.getLessons().get(j).getName(),
                                aTeachersData.getLessons().get(j).getHours()));
                    }
                }
            }
        }
        return teachersLessonsData;
    }

    public static List<Lesson> getGroupsLessonsData(MainApp mainApp) {
        List<Lesson> groupsLessonsData = FXCollections.observableArrayList();
        for (int i = 0; i < mainApp.getGroupsData().size(); i++) {
            for (int j = 0; j < mainApp.getGroupsData().get(i).getLessonsHours().size(); j++) {
                if (mainApp.getGroupsData().get(i).getLessonsHours().get(j) > 0) {
                    if (groupsLessonsData.size() == 0) {
                        groupsLessonsData.add(new Lesson(mainApp.getLessonsListData().get(j),
                                mainApp.getGroupsData().get(i).getLessonsHours().get(j)));
                    } else {
                        boolean boo = false;
                        for (Lesson aGroupsLessonsData : groupsLessonsData) {
                            if (mainApp.getLessonsListData().get(j).equals(
                                    aGroupsLessonsData.getName())) {
                                aGroupsLessonsData.setHours(aGroupsLessonsData.getHours() +
                                        mainApp.getGroupsData().get(i).getLessonsHours().get(j));
                                boo = true;
                                break;
                            }
                        }
                        if (!boo) {
                            groupsLessonsData.add(new Lesson(mainApp.getLessonsListData().get(j),
                                    mainApp.getGroupsData().get(i).getLessonsHours().get(j)));
                        }
                    }
                }
            }
        }
        return groupsLessonsData;
    }

    public static List<Lesson> getGroupsLessonsData(MainApp mainApp, List<Group> groupsData) {
        List<Lesson> groupsLessonsData = FXCollections.observableArrayList();
        for (Group aGroupsData : groupsData) {
            for (int j = 0; j < aGroupsData.getLessonsHours().size(); j++) {
                if (groupsLessonsData.size() == 0) {
                    groupsLessonsData.add(new Lesson(mainApp.getLessonsListData().get(j),
                            aGroupsData.getLessonsHours().get(j)));
                } else {
                    boolean boo = false;
                    for (Lesson aGroupsLessonsData : groupsLessonsData) {
                        if (mainApp.getLessonsListData().get(j).equals(
                                aGroupsLessonsData.getName())) {
                            aGroupsLessonsData.setHours(aGroupsLessonsData.getHours() +
                                    aGroupsData.getLessonsHours().get(j));
                            boo = true;
                            break;
                        }
                    }
                    if (!boo) {
                        groupsLessonsData.add(new Lesson(mainApp.getLessonsListData().get(j),
                                aGroupsData.getLessonsHours().get(j)));
                    }
                }
            }
        }
        return groupsLessonsData;
    }

    public static void setGroupsTeachersLessonsData(MainApp mainApp, List<String> listData) {
        List<Lesson> groupsLessonsData = getGroupsLessonsData(mainApp);
        List<Lesson> teachersLessonsData = getTeachersLessonsData(mainApp);
        if (teachersLessonsData.size() == 0)
            teachersLessonsData.add(new Lesson("", -10));

        for (Lesson aGroupsLessonsData : groupsLessonsData) {
            for (int j = 0; j < teachersLessonsData.size(); j++) {
                if (aGroupsLessonsData.getName().equals(teachersLessonsData.get(j).getName())) {
                    if (aGroupsLessonsData.getHours() > teachersLessonsData.get(j).getHours()) {
                        boolean boo = false;
                        for (String aListData : listData) {
                            if (aListData.equals(aGroupsLessonsData.getName())) {
                                boo = true;
                                break;
                            }
                        }
                        if (!boo) {
                            listData.add(aGroupsLessonsData.getName());
                            break;
                        }
                    } else break;
                } else if (j == teachersLessonsData.size() - 1) {
                    listData.add(aGroupsLessonsData.getName());
                }
            }
        }
    }

    public static void checkActualTeachers(String lessonName, Teacher maxTeacher, Teacher actualTeacher, MainApp mainApp) {
        Lesson lessMaxTeacher = new Lesson();
        Lesson lessActualTeacher = new Lesson();
        for (Lesson lesson : maxTeacher.getLessons()) {
            if (lesson.getName().equals(lessonName)) {
                lessMaxTeacher = lesson;
                break;
            }
        }

        for (Lesson lesson : actualTeacher.getLessons()) {
            if (lesson.getName().equals(lessonName)) {
                lessActualTeacher = lesson;
                break;
            }
        }

        exit:
        for (Day day : mainApp.getSchedule().getDays()) {
            for (Lecture lecture : day.getLectures()) {
                if (lessMaxTeacher.getHours() >= lessActualTeacher.getHours())
                    break exit;

                if (lecture.getTeacher().equals(maxTeacher.getName()) && lecture.getLesson().equals(lessonName)) {
                    lecture.setTeacher("");
                    lecture.setLesson("");
                    lessActualTeacher.setHours(lessActualTeacher.getHours() - 2);
                    for (Group group : mainApp.getSchedule().getActualGroups()) {
                        if (group.getName().equals(lecture.getGroup())) {
                            for (int i = 0; i < mainApp.getLessonsListData().size(); i++) {
                                if (mainApp.getLessonsListData().get(i).equals(lessonName)) {
                                    group.getLessonsHours().set(i,
                                            group.getLessonsHours().get(i) - 2);
                                    break;
                                }
                            }
                            break;
                        }
                    }
                }
            }
        }
    }

}
