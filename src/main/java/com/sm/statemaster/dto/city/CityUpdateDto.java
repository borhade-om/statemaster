package com.sm.statemaster.dto.city;

public class CityUpdateDto {

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

    public CityUpdateDto(String name, Long stateId) {
        this.name = name;
        this.stateId = stateId;
    }

    public CityUpdateDto() {
    }
}
