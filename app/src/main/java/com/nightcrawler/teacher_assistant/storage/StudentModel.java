package com.nightcrawler.teacher_assistant.storage;

import android.content.Context;

import org.dizitart.no2.collection.NitriteId;
import org.dizitart.no2.repository.annotations.Entity;
import org.dizitart.no2.repository.annotations.Id;
import org.dizitart.no2.repository.annotations.Index;

import java.io.Serializable;

@Entity(value = "student", indices = {
        @Index(StudentModel.GROUP_ID_FIELD)
})
public class StudentModel implements Serializable {
    @Id
    private NitriteId id;

    public static final String GROUP_ID_FIELD = "groupId",
        LAST_NAME_FIELD = "lastName";

    public String groupId;
    public String lastName;
    public String firstName;
    public String middleName;

    public int labVariant, subgroup, kpVariant;

    private StudentModel() {}

    public StudentModel(NitriteId id, String groupId) {
        this.id = id;
        this.groupId = groupId;
    }

    public StudentModel(String groupId) {
        this.groupId = groupId;
    }

    public String getFullName() {
        return String.format("%s %s %s", lastName, firstName, middleName);
    }

    public NitriteId getId() {
        return id;
    }
}
