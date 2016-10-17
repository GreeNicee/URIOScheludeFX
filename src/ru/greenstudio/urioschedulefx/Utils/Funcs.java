package ru.greenstudio.urioschedulefx.Utils;

import javafx.collections.ObservableList;
import ru.greenstudio.urioschedulefx.model.Lesson;

public class Funcs {
    public static void checkLessonsData(Lesson selectedLesson, Lesson newLesson, ObservableList<Lesson> lessonsData) {
        if (lessonsData.size() == 0 && selectedLesson.getHours() > 0) {
            lessonsData.add(selectedLesson);
        } else {
            for (int i = 0; i < lessonsData.size(); i++) {
                if (lessonsData.get(i).getName().equals(newLesson.getName())) {
                    int num = lessonsData.get(i).getHours() + newLesson.getHours();
                    lessonsData.get(i).setHours(num);
                    if (lessonsData.get(i).getHours() <= 0)
                        lessonsData.remove(i);
                    break;
                } else if (i == lessonsData.size() - 1) {
                    lessonsData.add(selectedLesson);
                    if (lessonsData.get(i).getHours() <= 0)
                        lessonsData.remove(i);
                    break;
                }
            }
        }

        for (Lesson lesson : lessonsData) {
            System.out.println(lesson.getName() + " " + lesson.getHours());
        }
    }

}
