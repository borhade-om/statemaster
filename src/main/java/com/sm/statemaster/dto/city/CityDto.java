package com.sm.statemaster.dto.city;

import com.sm.statemaster.entity.State;
import com.sm.statemaster.enums.CityStatus;

public class CityDto {

    private Long cityId;

    private String name;

    private State state;

    private CityStatus cityStatus;

    public CityStatus getCityStatus() {
        return cityStatus;
    }

    public void setCityStatus(CityStatus cityStatus) {
        this.cityStatus = cityStatus;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public CityDto() {
    }

    public Long getCityId() {
        return cityId;
    }

    public String getName() {
        return name;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public void setName(String name) {
        this.name = name;
    }


    public CityDto(Long cityId, String name, State state, CityStatus cityStatus) {
        this.cityId = cityId;
        this.name = name;
        this.state = state;
        this.cityStatus = cityStatus;
    }
}
