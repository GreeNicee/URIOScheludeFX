package ru.greenstudio.urioschedulefx.Utils;

import javafx.collections.ObservableList;
import ru.greenstudio.urioschedulefx.model.Lesson;

public class Funcs {
    public static void checkLessonsData(Lesson selectedLesson, Lesson newLesson,
                                        ObservableList<Lesson> lessonsMaxData, ObservableList<Lesson> lessonsData) {
        if (lessonsMaxData.size() == 0 && selectedLesson.getHours() > 0) {
            lessonsMaxData.add(selectedLesson);
            lessonsData.add(new Lesson(selectedLesson.getName(), 0));
        } else {
            for (int i = 0; i < lessonsMaxData.size(); i++) {
                if (lessonsMaxData.get(i).getName().equals(newLesson.getName())) {
                    int num = lessonsMaxData.get(i).getHours() + newLesson.getHours();
                    lessonsMaxData.get(i).setHours(num);
                    if (lessonsMaxData.get(i).getHours() <= 0)
                        lessonsMaxData.remove(i);
                    break;
                } else if (i == lessonsMaxData.size() - 1) {
                    lessonsMaxData.add(selectedLesson);
                    lessonsData.add(new Lesson(selectedLesson.getName(), 0));
                    if (lessonsMaxData.get(i).getHours() <= 0) {
                        lessonsMaxData.remove(i);
                        lessonsData.remove(i);
                    }
                    break;
                }
            }
        }

        for (Lesson lesson : lessonsMaxData) {
            System.out.println(lesson.getName() + " " + lesson.getHours());
        }
    }

    public static void checkLessonsData(Lesson selectedLesson, Lesson newLesson,
                                        ObservableList<Lesson> lessonsMaxData) {
        if (lessonsMaxData.size() == 0 && selectedLesson.getHours() > 0) {
            lessonsMaxData.add(selectedLesson);
        } else {
            for (int i = 0; i < lessonsMaxData.size(); i++) {
                if (lessonsMaxData.get(i).getName().equals(newLesson.getName())) {
                    int num = lessonsMaxData.get(i).getHours() + newLesson.getHours();
                    lessonsMaxData.get(i).setHours(num);
                    if (lessonsMaxData.get(i).getHours() <= 0)
                        lessonsMaxData.remove(i);
                    break;
                } else if (i == lessonsMaxData.size() - 1) {
                    lessonsMaxData.add(selectedLesson);
                    if (lessonsMaxData.get(i).getHours() <= 0) {
                        lessonsMaxData.remove(i);
                    }
                    break;
                }
            }
        }

        for (Lesson lesson : lessonsMaxData) {
            System.out.println(lesson.getName() + " " + lesson.getHours());
        }
    }
}
