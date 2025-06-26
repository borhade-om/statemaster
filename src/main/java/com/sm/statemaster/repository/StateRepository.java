package com.sm.statemaster.repository;

import com.sm.statemaster.entity.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StateRepository extends JpaRepository<State,Long>, JpaSpecificationExecutor {

    State findByStateIdAndDeletedAtNull(Long id);

    Page<State> findByDeletedAtNull(Pageable pages);

    Optional<State> findByNameIgnoreCase(String stateName);

//    @Query(value = "SELECT * From state  where delete_At=null" ,nativeQuery = true )
//    Page<State> findAllData(Pageable pages);
}
