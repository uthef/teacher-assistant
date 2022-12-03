package com.nightcrawler.teacher_assistant.storage;

import org.dizitart.no2.collection.NitriteId;
import org.dizitart.no2.repository.annotations.Entity;
import org.dizitart.no2.repository.annotations.Id;
import org.dizitart.no2.repository.annotations.Index;

import java.io.Serializable;

@Entity(value = "student", indices = {
        @Index(Student.GROUP_ID_FIELD)
})
public class Student implements Serializable {
    @Id
    private NitriteId id;

    public static final String GROUP_ID_FIELD = "groupId";

    public String groupId;
    public String lastName;
    public String firstName;
    public String middleName;

    public int variant;
    public int subgroup;

    private Student() {}

    public Student(NitriteId id, String groupId) {
        this.id = id;
        this.groupId = groupId;
    }

    public NitriteId getId() {
        return id;
    }
}
