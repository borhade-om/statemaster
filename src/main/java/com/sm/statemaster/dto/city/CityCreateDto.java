package com.sm.statemaster.dto.city;

import jakarta.validation.constraints.Pattern;

public class CityCreateDto {





    @Pattern(regexp = "^[A-Z][a-z]+$" ,message = "state name must not be black")
    private String name;



    private Long stateId;


    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CityCreateDto(String name, Long state) {
        this.name = name;
        this.stateId = state;
    }

    public CityCreateDto() {
    }
}
