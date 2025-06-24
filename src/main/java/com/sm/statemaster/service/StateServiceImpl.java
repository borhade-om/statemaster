package com.sm.statemaster.service;

import com.sm.statemaster.dto.state.StateCreateDto;
import com.sm.statemaster.dto.state.StateDto;
import com.sm.statemaster.dto.state.StateSearchDto;
import com.sm.statemaster.entity.State;
import com.sm.statemaster.enums.StateStatus;
import com.sm.statemaster.mapper.StateMapper;
import com.sm.statemaster.repository.StateRepository;
import com.sm.statemaster.specification.SpecificationHelper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StateServiceImpl implements StateService {

    private StateMapper stateMapper;
    private StateRepository stateRepository;

    public StateServiceImpl(StateMapper stateMapper, StateRepository stateRepository) {
        this.stateMapper = stateMapper;
        this.stateRepository = stateRepository;
    }


    @Override
    public List<StateDto> getAllSate(Integer page, Integer pageSize) {
        Pageable pages = PageRequest.of(page, pageSize);
        Page<State> pageState = stateRepository.findByDeletedAtNull(pages);
        List<State> data = pageState.getContent();
        return data.stream()
                .map(stateData -> stateMapper.toDto(stateData)).collect(Collectors.toList());
    }

    @Override
    public String inserIntoState(StateCreateDto stateCreateDto) {
        State state = stateMapper.toEntity(stateCreateDto);
        stateRepository.save(state);
        return "data inserted Successfully";
    }

    @Override
    public String softDeleteState(Long stateId) {
        State state = stateRepository.findByStateIdAndDeletedAtNull(stateId);
        if (state == null) {
            throw new IllegalArgumentException("id Not Found ");
        }
        stateRepository.delete(state);
        return "deleted successfully";

    }

    @Override
    public StateDto updateStatedata(Long stateId, StateCreateDto stateCreateDto) {
        State state = stateRepository.findById(stateId).orElseThrow(() -> new IllegalArgumentException("id Not Found "));
        if (stateCreateDto.getName() != null) {
            state.setName(stateCreateDto.getName());
        }
        stateRepository.save(state);
        return stateMapper.toDto(state);
    }

    @Override
    public StateDto updateStatus(Long stateId, StateStatus stateStatus) {
        State status = stateRepository.findById(stateId).orElseThrow(() -> new IllegalArgumentException("id Not Found "));
        if (status != null) {
            status.setStateStatus(stateStatus);
            stateRepository.save(status);
        }
        return stateMapper.toDto(status);
    }

    @Override
    public List<StateDto> postSearchData(Integer page, Integer pageSize, StateSearchDto stateSearchDto) {
        Specification<State> dtd = SpecificationHelper.searchHelper(stateSearchDto);
        Pageable pages = PageRequest.of(page, pageSize);
        Page<State> pageState = stateRepository.findAll(dtd, pages);
        List<State> pagedata = pageState.getContent();
        return pagedata.stream().map(data -> stateMapper.toDto(data)).collect(Collectors.toList());
    }
}
