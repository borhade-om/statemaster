package com.sm.statemaster.mapper;

import com.sm.statemaster.dto.city.CityCreateDto;
import com.sm.statemaster.dto.city.CityDto;
import com.sm.statemaster.entity.City;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CityMapper {


    City toCity(CityCreateDto cityCreateDto);

      CityDto toDto(City city);

      List<CityDto> toListCity(List<City> city);

}
