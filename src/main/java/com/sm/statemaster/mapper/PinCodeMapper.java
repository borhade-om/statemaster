package com.sm.statemaster.mapper;

import com.sm.statemaster.dto.pincode.PinCodeCreateDto;
import com.sm.statemaster.dto.pincode.PinCodeDto;
import com.sm.statemaster.dto.pincode.PinCodeSearchDto;
import com.sm.statemaster.entity.PinCode;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PinCodeMapper {

     PinCode toEntity(PinCodeCreateDto pinCodeCreateDto);

     PinCodeDto toDto(PinCode pinCode);

     List<PinCodeDto> toListDto(List<PinCode> pinCode);

     PinCodeSearchDto toSearchDto(PinCode pinCode);

     List<PinCode> toSearchListDto(List<PinCodeSearchDto> pinCodeSearchDtos);
}
