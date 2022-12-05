package com.nightcrawler.teacher_assistant.storage;

import org.dizitart.no2.collection.NitriteId;
import org.dizitart.no2.repository.annotations.Entity;
import org.dizitart.no2.repository.annotations.Id;
import org.dizitart.no2.repository.annotations.Index;

import java.io.Serializable;

@Entity(value = "discipline", indices = {
    @Index(DisciplineModel.NAME_FIELD)
})
public class DisciplineModel implements Serializable {
    @Id
    private NitriteId id;

    public String name;
    public int colloquiums, labWorks, maxColloquiumGrade, maxLabWorkGrade;
    public static final String NAME_FIELD = "name";

    private DisciplineModel() {}

    public DisciplineModel(String name, int colloquiums, int labWorks, int maxColloquiumGrade, int maxLabWorkGrade) {
        this.name = name;
        this.colloquiums = colloquiums;
        this.labWorks = labWorks;
        this.maxColloquiumGrade = maxColloquiumGrade;
        this.maxLabWorkGrade = maxLabWorkGrade;
    }

    public NitriteId getId() {
        return id;
    }
}
