package com.sm.statemaster.repository;

import com.sm.statemaster.entity.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City,Long>, JpaSpecificationExecutor {



    Page<City> findByDeletedAtNull(Pageable pages);

    City findByCityIdAndDeletedAtNull(Long cityId);


    Optional<City> findByNameIgnoreCase(String cityName);
}
