package com.myproject.sm.domain.dto.request;

import java.time.LocalDate;

import com.myproject.sm.domain.Class;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqCreateStudentAttendance {

    private Class classInfo;

    private LocalDate date;

    private int slot;

}
