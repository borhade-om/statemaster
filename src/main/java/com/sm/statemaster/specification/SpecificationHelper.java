package com.sm.statemaster.specification;

import com.sm.statemaster.dto.city.CitySearchDto;
import com.sm.statemaster.dto.pincode.PinCodeSearchDto;
import com.sm.statemaster.dto.state.StateSearchDto;
import com.sm.statemaster.entity.City;
import com.sm.statemaster.entity.PinCode;
import com.sm.statemaster.entity.State;

import com.sm.statemaster.enums.StateStatus;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class SpecificationHelper {

    public static Specification<State> hasStateName(String stateName) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), stateName.toLowerCase());
    }

    public static Specification<State> hasStateStatus(StateStatus stateStatus) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("stateStatus"), stateStatus);
    }

    public static Specification<State> hascreatedAtFrom(LocalDate createdAtFrom) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), createdAtFrom);
    }

    public static Specification<State> hascreatedAtTo(LocalDate createdAtTo) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), createdAtTo);
    }

    public static Specification<City> hasCityName(String cityName) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), cityName.toLowerCase());
    }

    public static Specification<City> hasCityInStateName(String stateName) {
        return (root, query, criteriaBuilder) -> {
            Join<City, State> join = root.join("state", JoinType.INNER);
            Predicate name = criteriaBuilder.like(criteriaBuilder.lower(join.get("name")), stateName.toLowerCase());
            return name;
        };

    }

    public static Specification<City> hasCityCreatedAtFrom(LocalDate createdAtFrom) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), createdAtFrom);
    }

    public static Specification<City> hasCityCreatedAtTo(LocalDate createdAtTo) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), createdAtTo);
    }

    public static Specification<PinCode> hasPinCode(Long pinCode) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("pinCode"), pinCode);
    }

    public static Specification<PinCode> hasPinCodeInCity(String cityName) {
        return (root, query, criteriaBuilder) -> {
            Join<PinCode, City> join = root.join("cities", JoinType.INNER);
            Predicate name = criteriaBuilder.like(criteriaBuilder.lower(join.get("name")), cityName.toLowerCase());
            return name;
        };
    }

    public static Specification<PinCode> hasPinCodeInState(String stateName) {
        return (root, query, criteriaBuilder) -> {
            Join<PinCode, State> join = root.join("states", JoinType.INNER);
            Predicate name = criteriaBuilder.like(criteriaBuilder.lower(join.get("name")), stateName.toLowerCase());
            return name;
        };
    }

    public static Specification<PinCode> hasPinCodeCreatedAtFrom(LocalDate createdAtFrom) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), createdAtFrom);
    }

    public static Specification<PinCode> hasPinCodeCreatedAtTo(LocalDate createdAtTo) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), createdAtTo);
    }


    public static Specification<State> searchHelper(StateSearchDto stateSearchDto) {
        Specification<State> sps = (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();

        if (stateSearchDto.getName() != null) {
            sps = sps.and(SpecificationHelper.hasStateName(stateSearchDto.getName()));
        }
        if (stateSearchDto.getStateStatus() != null) {
            sps = sps.and(SpecificationHelper.hasStateStatus(stateSearchDto.getStateStatus()));
        }
        if (stateSearchDto.getCreatedAtFrom() != null) {
            sps = sps.and(SpecificationHelper.hascreatedAtFrom(stateSearchDto.getCreatedAtFrom()));
        }
        if (stateSearchDto.getCreatedAtTo() != null) {
            sps = sps.and(SpecificationHelper.hascreatedAtTo(stateSearchDto.getCreatedAtTo()));
        }

        return sps;
    }

    public static Specification<City> citySearchHelper(CitySearchDto citySearchDto) {
        Specification<City> spcs = (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();

        if (citySearchDto.getCityname() != null) {
            spcs = spcs.and(SpecificationHelper.hasCityName(citySearchDto.getCityname()));
        }
        if (citySearchDto.getCreatedAtFrom() != null) {
            spcs = spcs.and(SpecificationHelper.hasCityCreatedAtFrom(citySearchDto.getCreatedAtFrom()));
        }
        if (citySearchDto.getCreatedAtTo() != null) {
            spcs = spcs.and(SpecificationHelper.hasCityCreatedAtTo(citySearchDto.getCreatedAtTo()));
        }
        if (citySearchDto.getStateName() != null) {
            spcs = spcs.and(SpecificationHelper.hasCityInStateName(citySearchDto.getStateName()));
        }
        return spcs;

    }

    public static Specification<PinCode> pinCodeSeaarchHelper(PinCodeSearchDto pinCodeSearchDto ){
        Specification<PinCode> spcp=(root, query, criteriaBuilder) -> criteriaBuilder.conjunction();

        if (pinCodeSearchDto.getPincode()!=null){
            spcp=spcp.and(SpecificationHelper.hasPinCode(pinCodeSearchDto.getPincode()));
        }
        if (pinCodeSearchDto.getStateName()!=null){
            spcp=spcp.and(SpecificationHelper.hasPinCodeInState(pinCodeSearchDto.getStateName()));
        }
        if (pinCodeSearchDto.getCityName()!=null){
            spcp=spcp.and(SpecificationHelper.hasPinCodeInCity(pinCodeSearchDto.getCityName()));
        }
        if (pinCodeSearchDto.getCreatedAtFrom()!=null){
            spcp=spcp.and(SpecificationHelper.hasPinCodeCreatedAtFrom(pinCodeSearchDto.getCreatedAtFrom()));
        }
        if (pinCodeSearchDto.getCreatedAtTo()!=null){
            spcp=spcp.and(SpecificationHelper.hasPinCodeCreatedAtTo(pinCodeSearchDto.getCreatedAtTo()));
        }
        return spcp;
    }
}
