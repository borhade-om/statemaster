package com.sm.statemaster.service;

import com.sm.statemaster.dto.pincode.PinCodeCreateDto;
import com.sm.statemaster.dto.pincode.PinCodeDto;
import com.sm.statemaster.dto.pincode.PinCodeSearchDto;
import com.sm.statemaster.enums.PinCodeStatus;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PinCodeService {

    List<PinCodeDto> getAllPincodes(Integer page, Integer pageSize);

    String insertPincodes(PinCodeCreateDto pinCodeCreateDto);

    PinCodeDto updatePinCodes(Long pinId, PinCodeCreateDto pinCodeCreateDto);

    String deletePinCode(Long pinId);

    PinCodeDto updatePincodeStetus(Long pinId, PinCodeStatus pinCodeStatus);

    List<PinCodeDto> postSearchData(Integer page, Integer pageSize, PinCodeSearchDto pinCodeSearchDto);

    String saveExcelData(MultipartFile file) throws IOException;

    String pinCodeExcelImport(MultipartFile file) throws IOException;
}
