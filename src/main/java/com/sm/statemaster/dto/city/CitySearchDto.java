package com.sm.statemaster.dto.city;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class CitySearchDto {


    private String cityname;

    private String stateName;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate createdAtFrom;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate createdAtTo;

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
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

    public CitySearchDto() {
    }

    public CitySearchDto(String cityname, String stateName, LocalDate createdAtFrom, LocalDate createdAtTo) {
        this.cityname = cityname;
        this.stateName = stateName;
        this.createdAtFrom = createdAtFrom;
        this.createdAtTo = createdAtTo;
    }
}
