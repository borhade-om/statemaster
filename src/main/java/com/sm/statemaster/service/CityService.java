package com.sm.statemaster.service;

import com.sm.statemaster.dto.city.CityCreateDto;
import com.sm.statemaster.dto.city.CityDto;
import com.sm.statemaster.dto.city.CitySearchDto;
import com.sm.statemaster.dto.city.CityUpdateDto;
import com.sm.statemaster.enums.CityStatus;

import java.util.List;

public interface CityService {
    List<CityDto> getAllCities(Integer page, Integer pageSize);

    String insertCities(CityCreateDto cityCreateDto);

    CityDto updateCity(Long cityId, CityUpdateDto cityUpdateDto);

    CityDto updateCityStatus(Long cityId, CityStatus cityStatus);

    String deleteCity(Long cityId);


    List<CityDto> postSearchdata(Integer page, Integer pageSize, CitySearchDto citySearchDto);
}
