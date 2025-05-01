package com.myproject.sm.domain.dto.response;

import java.util.List;

import com.myproject.sm.domain.Class;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResScheduleDTO {

    private String slot;
    private List<Class> monday;
    private List<Class> tuesday;
    private List<Class> wednesday;
    private List<Class> thursday;
    private List<Class> friday;
    private List<Class> saturday;
    private List<Class> sunday;

}
