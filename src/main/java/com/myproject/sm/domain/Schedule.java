package com.myproject.sm.domain;

import java.util.List;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PreRemove;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Schedule {

    @Id
    @UuidGenerator
    private String id;

    @ElementCollection
    private List<Integer> weekdayList;

    @OneToOne
    @JoinColumn(name = "class_id")
    private Class classInfo;

    @PreRemove
    public void handleBeforeDelete() {
        if (classInfo != null) {
            this.classInfo.setSchedule(null);
        }
    }

}
