package com.sm.statemaster.dto.pincode;

import jakarta.validation.constraints.Pattern;

public class PinCodeCreateDto {


    private Long pinCode;


    private Long cityId;


    private Long stateId;

    public PinCodeCreateDto(Long pinCode, Long cityId, Long stateId) {
        this.pinCode = pinCode;
        this.cityId = cityId;
        this.stateId = stateId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    public Long getPinCode() {
        return pinCode;
    }

    public void setPinCode(Long pinCode) {
        this.pinCode = pinCode;
    }

    public PinCodeCreateDto() {
    }


}
