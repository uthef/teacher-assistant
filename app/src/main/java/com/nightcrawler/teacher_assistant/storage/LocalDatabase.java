package com.nightcrawler.teacher_assistant.storage;

import android.annotation.SuppressLint;
import android.util.Log;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.mapper.JacksonMapperModule;
import org.dizitart.no2.mvstore.MVStoreModule;
import org.dizitart.no2.repository.Cursor;
import org.dizitart.no2.repository.ObjectRepository;

import java.io.File;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.dizitart.no2.filters.FluentFilter.where;

public class LocalDatabase {
    private final Nitrite nitrite;
    private static LocalDatabase instance = null;
    public static final String DEFAULT_PATH = "main.db";
    public static String[] defaultDurationSet;
    private static File file = new File(DEFAULT_PATH);
    private final ObjectRepository<GroupModel> groupRepo;
    private final ObjectRepository<StudentModel> studentRepo;

    private LocalDatabase() {
        MVStoreModule storeModule = MVStoreModule.withConfig()
                .filePath(file)
                .build();

        nitrite = Nitrite.builder()
                .loadModule(storeModule)
                .loadModule(new JacksonMapperModule())
                .openOrCreate();

        groupRepo = nitrite.getRepository(GroupModel.class);
        studentRepo = nitrite.getRepository(StudentModel.class);
    }

    public List<GroupModel> listGroups() {
        return new ArrayList<>(groupRepo.find().sort(GroupModel.NAME_FIELD).toList());
    }

    public List<StudentModel> listStudents() {
        return new ArrayList<>(studentRepo.find().sort(StudentModel.LAST_NAME_FIELD).toList());
    }

    public void removeGroup(GroupModel groupModel) { groupRepo.remove(groupModel); }
    public void updateGroup(GroupModel groupModel) { groupRepo.update(groupModel); }
    public void insertGroup(GroupModel groupModel) { groupRepo.insert(groupModel); }

    @SuppressLint("SimpleDateFormat")
    private ObjectRepository<LessonModel> getLessonRepo() {
        ObjectRepository<LessonModel> lessons = nitrite.getRepository(LessonModel.class);

        if (lessons.size() == 0 && defaultDurationSet != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

            try {
                for (int i = 0; i < defaultDurationSet.length - 1; i++) {
                    Date startTime = dateFormat.parse(defaultDurationSet[i]),
                            endTime = dateFormat.parse(defaultDurationSet[i + 1]);

                    lessons.insert(new LessonModel(i + 1, startTime, endTime));
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }

        return lessons;
    }

    public boolean hasGroupNamed(String name) {
        String regexStr = String.format("(?i)^%s$", name);
        Cursor<GroupModel> cursor = groupRepo.find(where(GroupModel.NAME_FIELD).regex(regexStr)).limit(1);
        return cursor.size() > 0;
    }

    public static LocalDatabase getInstance() {
        if (instance == null) {
            instance = new LocalDatabase();
        }

        return instance;
    }
    public static void setFile(File file) {
        LocalDatabase.file = file;
    }
}
