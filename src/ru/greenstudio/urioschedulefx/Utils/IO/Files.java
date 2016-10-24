package ru.greenstudio.urioschedulefx.Utils.IO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import ru.greenstudio.urioschedulefx.model.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;

public class Files {
    private static final String path = "data";
    private static final String[] files = {"cabs", "lessons", "groups", "teachers", "schedule"};
    private static final String format = ".xml";

    public static void checkFiles() {
        File filePath = new File(path);
        if (!filePath.exists()) {
            filePath.mkdir();
        }
        for (int i = 0; i < files.length; i++) {
            File file = new File(path + "/" + files[i] + format);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void saveDataToFile(ObservableList<String> lessonsListData, ObservableList<String> cabsListData,
                                      ObservableList<Group> groupsData, ObservableList<Teacher> teachersData, Schedule schedule) {
        checkFiles();
        try {
            JAXBContext context = JAXBContext.newInstance(LessonWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            //LESSONS-------------------------------------------------
            LessonWrapper lessonWrapper = new LessonWrapper();
            lessonWrapper.setLessons(lessonsListData);

            File file = new File("data/" + files[1] + ".xml");

            m.marshal(lessonWrapper, file);
            //CABS-----------------------------------------------------
            context = JAXBContext.newInstance(CabWrapper.class);
            m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            CabWrapper cabWrapper = new CabWrapper();
            cabWrapper.setCabs(cabsListData);

            file = new File("data/" + files[0] + ".xml");

            m.marshal(cabWrapper, file);
            //GROUPS---------------------------------------------------
            context = JAXBContext.newInstance(GroupWrapper.class);
            m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            GroupWrapper groupWrapper = new GroupWrapper();
            groupWrapper.setGroups(groupsData);

            file = new File("data/" + files[2] + ".xml");

            m.marshal(groupWrapper, file);
            //TEACHERS---------------------------------------------------
            context = JAXBContext.newInstance(TeacherWrapper.class);
            m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            TeacherWrapper teacherWrapper = new TeacherWrapper();
            teacherWrapper.setTeachers(teachersData);

            file = new File("data/" + files[3] + ".xml");

            m.marshal(teacherWrapper, file);
            //SCHEDULE
            context = JAXBContext.newInstance(ScheduleWrapper.class);
            m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            ScheduleWrapper scheduleWrapper = new ScheduleWrapper();
            scheduleWrapper.setSchedule(schedule);

            file = new File("data/" + files[4] + ".xml");

            m.marshal(scheduleWrapper, file);

        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not save data");
            alert.setContentText("Could not save data to file" + e);

            alert.showAndWait();
        }
    }

    public static void loadDataFromFile(ObservableList<String> lessonsListData, ObservableList<String> cabsListData,
                                        ObservableList<Group> groupsData, ObservableList<Teacher> teachersData) {
        try {
            JAXBContext context = JAXBContext.newInstance(LessonWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            File file = new File("data/" + files[1] + ".xml");

            LessonWrapper lessonsWrapper = (LessonWrapper) um.unmarshal(file);

            lessonsListData.clear();
            lessonsListData.addAll(lessonsWrapper.getLessons());

            context = JAXBContext.newInstance(CabWrapper.class);
            um = context.createUnmarshaller();

            file = new File("data/" + files[0] + ".xml");

            CabWrapper cabWrapper = (CabWrapper) um.unmarshal(file);

            cabsListData.clear();
            cabsListData.addAll(cabWrapper.getCabs());

            context = JAXBContext.newInstance(GroupWrapper.class);
            um = context.createUnmarshaller();

            file = new File("data/" + files[2] + ".xml");

            GroupWrapper groupWrapper = (GroupWrapper) um.unmarshal(file);

            groupsData.clear();
            groupsData.addAll(groupWrapper.getGroups());

            context = JAXBContext.newInstance(TeacherWrapper.class);
            um = context.createUnmarshaller();

            file = new File("data/" + files[3] + ".xml");

            TeacherWrapper teacherWrapper = (TeacherWrapper) um.unmarshal(file);

            teachersData.clear();
            teachersData.addAll(teacherWrapper.getTeachers());
        } catch (Exception e) { // catches ANY exception
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Error");
//            alert.setHeaderText("Could not load data");
//            alert.setContentText("Could not load data from file");
//
//            alert.showAndWait();
        }
    }

    public static void loadSchedule(Schedule schedule) {
        JAXBContext context = null;
        try {
            context = JAXBContext.newInstance(ScheduleWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            File file = new File("data/" + files[4] + ".xml");

            ScheduleWrapper scheduleWrapper = (ScheduleWrapper) um.unmarshal(file);

            schedule.setName(scheduleWrapper.getSchedule().getName());
            schedule.setMaxGroupLessons(scheduleWrapper.getSchedule().getMaxGroupLessons());
            schedule.setActualGroupLessons(scheduleWrapper.getSchedule().getActualGroupLessons());
            schedule.setMaxTeacherLessons(scheduleWrapper.getSchedule().getMaxTeacherLessons());
            schedule.setActualTeacherLessons(scheduleWrapper.getSchedule().getActualTeacherLessons());

            if (scheduleWrapper.getSchedule().getActualGroups() != null)
                schedule.setActualGroups(scheduleWrapper.getSchedule().getActualGroups());
            else {
                schedule.setActualGroups(FXCollections.observableArrayList());
                for (int i = 0; i < schedule.getMaxGroups().size(); i++) {
                    schedule.getActualGroups().add(i,
                            new Group(schedule.getMaxGroups().get(i).getName(), schedule.getMaxGroups().get(i).getLessonsNames()
                                    , schedule.getMaxGroups().get(i).getNullHours()));
                }
            }

            if (scheduleWrapper.getSchedule().getActualTeachers() != null)
                schedule.setActualTeachers(scheduleWrapper.getSchedule().getActualTeachers());
            else {
                schedule.setActualTeachers(FXCollections.observableArrayList());
                for (int i = 0; i < schedule.getMaxTeachers().size(); i++) {
                    schedule.getActualTeachers().add(
                            new Teacher(schedule.getMaxTeachers().get(i).getName(), FXCollections.observableArrayList()));
                    if (schedule.getMaxTeachers().get(i).getLessons() != null) {
                        for (int j = 0; j < schedule.getMaxTeachers().get(i).getLessons().size(); j++) {
                            schedule.getActualTeachers().get(i).getLessons().add(
                                    new Lesson(schedule.getMaxTeachers().get(i).getLessons().get(j).getName(), 0));
                        }
                    }
                }
            }

            schedule.setDays(FXCollections.observableArrayList());

            for (int i = 0; i < scheduleWrapper.getSchedule().getDays().size(); i++) {
                schedule.getDays().add(new Day(scheduleWrapper.getSchedule().getDays().get(i).getName(),
                        FXCollections.observableArrayList()));
                if (scheduleWrapper.getSchedule().getDays().get(i).getLectures() != null)
                    schedule.getDays().get(i).setLectures(scheduleWrapper.getSchedule().getDays().get(i).getLectures());
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

}
