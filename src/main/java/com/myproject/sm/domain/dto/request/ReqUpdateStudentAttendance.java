package com.myproject.sm.domain.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqUpdateStudentAttendance {

    private String studentAttendanceId;

    private boolean status;
}
