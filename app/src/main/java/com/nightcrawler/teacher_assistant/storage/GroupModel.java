package com.nightcrawler.teacher_assistant.storage;

import org.dizitart.no2.collection.NitriteId;
import org.dizitart.no2.repository.annotations.Entity;
import org.dizitart.no2.repository.annotations.Id;
import org.dizitart.no2.repository.annotations.Index;

import java.io.Serializable;
import java.util.Locale;

@Entity(value = "group", indices = {
        @Index(GroupModel.NAME_FIELD)
})
public class GroupModel implements Serializable {
    @Id
    private NitriteId id;
    public String name;

    public static final String NAME_FIELD = "name";

    private GroupModel() { }
    public GroupModel(NitriteId id, String name) {
        this.id = id;
        this.name = name;
    }
    public GroupModel(String name) {
        this.name = name;
    }

    public String getId() {
        return id.getIdValue();
    }

    public String getLoweredName() {
        return name.toLowerCase(Locale.ROOT);
    }
}
