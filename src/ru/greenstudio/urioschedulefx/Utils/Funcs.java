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
        for (int i = 0; i < teachersData.size(); i++) {
            for (int j = 0; j < teachersData.get(i).getLessons().size(); j++) {
                if (teachersLessonsData.size() == 0) {
                    teachersLessonsData.add(new Lesson(teachersData.get(i).getLessons().get(j).getName(),
                            teachersData.get(i).getLessons().get(j).getHours()));
                } else {
                    boolean boo = false;
                    for (Lesson aTeachersLessonsData : teachersLessonsData) {
                        if (teachersData.get(i).getLessons().get(j).getName().equals(
                                aTeachersLessonsData.getName())) {
                            aTeachersLessonsData.setHours(aTeachersLessonsData.getHours() +
                                    teachersData.get(i).getLessons().get(j).getHours());
                            boo = true;
                            break;
                        }
                    }
                    if (!boo) {
                        teachersLessonsData.add(new Lesson(teachersData.get(i).getLessons().get(j).getName(),
                                teachersData.get(i).getLessons().get(j).getHours()));
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
        for (int i = 0; i < groupsData.size(); i++) {
            for (int j = 0; j < groupsData.get(i).getLessonsHours().size(); j++) {
                if (groupsLessonsData.size() == 0) {
                    groupsLessonsData.add(new Lesson(mainApp.getLessonsListData().get(j),
                            groupsData.get(i).getLessonsHours().get(j)));
                } else {
                    boolean boo = false;
                    for (Lesson aGroupsLessonsData : groupsLessonsData) {
                        if (mainApp.getLessonsListData().get(j).equals(
                                aGroupsLessonsData.getName())) {
                            aGroupsLessonsData.setHours(aGroupsLessonsData.getHours() +
                                    groupsData.get(i).getLessonsHours().get(j));
                            boo = true;
                            break;
                        }
                    }
                    if (!boo) {
                        groupsLessonsData.add(new Lesson(mainApp.getLessonsListData().get(j),
                                groupsData.get(i).getLessonsHours().get(j)));
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

        for (int i = 0; i < groupsLessonsData.size(); i++) {
            for (int j = 0; j < teachersLessonsData.size(); j++) {
                if (groupsLessonsData.get(i).getName().equals(teachersLessonsData.get(j).getName())) {
                    if (groupsLessonsData.get(i).getHours() > teachersLessonsData.get(j).getHours()) {
                        boolean boo = false;
                        for (int k = 0; k < listData.size(); k++) {
                            if (listData.get(k).equals(groupsLessonsData.get(i).getName())) {
                                boo = true;
                                break;
                            }
                        }
                        if (!boo) {
                            listData.add(groupsLessonsData.get(i).getName());
                            break;
                        }
                    } else break;
                } else if (j == teachersLessonsData.size() - 1) {
                    listData.add(groupsLessonsData.get(i).getName());
                }
            }
        }
    }


    //TODO функцию проверки часов у макс групп и макс преподов с актуальными гр,пр
    private void checkScheduleLessons(Schedule schedule) {
        List<Day> days = schedule.getDays();

//        for (int i = 0; i < ; i++) {

//        }

    }

    private void checkDaysLectures(List<Day> days/*TODO ,lessname */) {
        for (int i = 0; i < days.size(); i++) {
            for (int j = 0; j < days.get(i).getLectures().size(); j++) {

            }
        }
    }

}
