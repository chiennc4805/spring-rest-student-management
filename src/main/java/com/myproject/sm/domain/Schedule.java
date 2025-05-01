package com.myproject.sm.domain;

import java.util.List;

import org.hibernate.annotations.UuidGenerator;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Schedule {

    @Id
    @UuidGenerator
    private String id;

    private int slotNumber;

    private List<Integer> weekdayList;

    @ManyToOne
    @JoinColumn(name = "class_id")
    @JsonIncludeProperties({ "id", "name" })
    private Class classInfo;

}
