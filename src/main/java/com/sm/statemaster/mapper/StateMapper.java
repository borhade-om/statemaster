package com.sm.statemaster.mapper;

import com.sm.statemaster.dto.state.StateCreateDto;
import com.sm.statemaster.dto.state.StateDto;
import com.sm.statemaster.entity.State;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StateMapper {

    State  toEntity(StateCreateDto dto);

    StateDto toDto(State state);
}
