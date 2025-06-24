package com.sm.statemaster.dto.pincode;

import com.sm.statemaster.entity.City;
import com.sm.statemaster.entity.State;
import com.sm.statemaster.enums.PinCodeStatus;

public class PinCodeDto {

    private Long pinId;

    private Long pinCode;

    private PinCodeStatus pinStatus;

    private City cities;

    private State states;



    public State getStates() {
        return states;
    }

    public void setStates(State states) {
        this.states = states;
    }

    public City getCities() {
        return cities;
    }

    public void setCities(City cities) {
        this.cities = cities;
    }

    public Long getPinId() {
        return pinId;
    }

    public void setPinId(Long pinId) {
        this.pinId = pinId;
    }

    public PinCodeStatus getPinStatus() {
        return pinStatus;
    }

    public void setPinStatus(PinCodeStatus pinStatus) {
        this.pinStatus = pinStatus;
    }

    public PinCodeDto(Long pinId, Long pinCode, PinCodeStatus pinStatus, City cities, State states) {
        this.pinId = pinId;
        this.pinCode = pinCode;
        this.pinStatus = pinStatus;
        this.cities = cities;
        this.states = states;
    }

    public PinCodeDto() {
    }

    public Long getPinCode() {
        return pinCode;
    }

    public void setPinCode(Long pinCode) {
        this.pinCode = pinCode;
    }
}
