package com.sm.statemaster.dto.state;

public class StateUpdateDto {



    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StateUpdateDto(String name) {
        this.name = name;
    }

}
