package com.sm.statemaster.dto.state;

import com.sm.statemaster.enums.StateStatus;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class StateSearchDto {

    private String name;

    private StateStatus stateStatus;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate createdAtFrom;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate createdAtTo;

    public StateSearchDto(String name, StateStatus stateStatus, LocalDate createdAtFrom, LocalDate createdAtTo) {
        this.name = name;
        this.stateStatus = stateStatus;
        this.createdAtFrom = createdAtFrom;
        this.createdAtTo = createdAtTo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StateStatus getStateStatus() {
        return stateStatus;
    }

    public void setStateStatus(StateStatus stateStatus) {
        this.stateStatus = stateStatus;
    }

    public LocalDate getCreatedAtFrom() {
        return createdAtFrom;
    }

    public void setCreatedAtFrom(LocalDate createdAtFrom) {
        this.createdAtFrom = createdAtFrom;
    }

    public LocalDate getCreatedAtTo() {
        return createdAtTo;
    }

    public void setCreatedAtTo(LocalDate createdAtTo) {
        this.createdAtTo = createdAtTo;
    }


}
