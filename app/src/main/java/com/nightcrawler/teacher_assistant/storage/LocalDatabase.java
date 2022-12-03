package com.nightcrawler.teacher_assistant.storage;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.mapper.JacksonMapperModule;
import org.dizitart.no2.mvstore.MVStoreModule;
import org.dizitart.no2.repository.Cursor;
import org.dizitart.no2.repository.ObjectRepository;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import static org.dizitart.no2.filters.FluentFilter.where;

public class LocalDatabase {
    private final Nitrite nitrite;
    private static LocalDatabase instance = null;
    public static final String DEFAULT_PATH = "main.db";
    private static File file = new File(DEFAULT_PATH);
    private final ObjectRepository<GroupModel> groupRepo;

    private LocalDatabase() {
        MVStoreModule storeModule = MVStoreModule.withConfig()
                .filePath(file)
                .build();

        nitrite = Nitrite.builder()
                .loadModule(storeModule)
                .loadModule(new JacksonMapperModule())
                .openOrCreate();

        groupRepo = nitrite.getRepository(GroupModel.class);
    }

    public List<GroupModel> listGroups() {
        return new ArrayList<>(groupRepo.find().sort(GroupModel.NAME_FIELD).toList());
    }
    public void removeGroup(GroupModel groupModel) { groupRepo.remove(groupModel); }
    public void updateGroup(GroupModel groupModel) { groupRepo.update(groupModel); }
    public void insertGroup(GroupModel groupModel) { groupRepo.insert(groupModel); }
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
