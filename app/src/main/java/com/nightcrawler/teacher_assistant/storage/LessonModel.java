package com.nightcrawler.teacher_assistant.storage;

import org.dizitart.no2.repository.annotations.Entity;
import org.dizitart.no2.repository.annotations.Index;

import java.io.Serializable;
import java.util.Date;

@Entity(value = "lesson", indices = {
        @Index(LessonModel.NUMBER_FIELD)
})
public class LessonModel implements Serializable {
    public long number;
    public Date startTime, endTime;

    public static final String NUMBER_FIELD = "number";

    private LessonModel() { }

    public LessonModel(long number, Date startTime, Date endTime) {
        this.number = number;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
