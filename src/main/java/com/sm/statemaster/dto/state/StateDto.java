package com.sm.statemaster.dto.state;

import com.sm.statemaster.enums.StateStatus;

public class StateDto {


    private Long stateId;

    private String name;

    private StateStatus stateStatus;

    public StateStatus getStateStatus() {
        return stateStatus;
    }

    public void setStateStatus(StateStatus stateStatus) {
        this.stateStatus = stateStatus;
    }

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

    public StateDto(Long stateId, String name) {
        this.stateId = stateId;
        this.name = name;
    }

    public StateDto() {
    }
}
