package com.sm.statemaster.service;

import com.sm.statemaster.dto.state.StateCreateDto;
import com.sm.statemaster.dto.state.StateDto;
import com.sm.statemaster.dto.state.StateSearchDto;
import com.sm.statemaster.enums.StateStatus;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface StateService {


    String inserIntoState(StateCreateDto stateCreateDto);

    List<StateDto> getAllSate(Integer page, Integer pageSize);

    String softDeleteState(Long stateId);

    StateDto updateStatedata(Long stateId, StateCreateDto stateCreateDto);

    StateDto updateStatus(Long stateId, StateStatus stateStatus);

    List<StateDto> postSearchData(Integer page, Integer pageSize, StateSearchDto stateSearchDto);

    String stateExcelImport(MultipartFile file) throws IOException;

    void exportStateData(HttpServletResponse response) throws IOException;
}
