package com.sm.statemaster.dto.state;

import jakarta.validation.constraints.Pattern;

public class StateCreateDto {


    @Pattern(regexp = "^[A-Z][a-z]+$" ,message = "state name must not be black")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StateCreateDto(String name) {
        this.name = name;
    }

    public StateCreateDto() {
    }
}
