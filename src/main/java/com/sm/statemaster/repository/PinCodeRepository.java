package com.sm.statemaster.repository;

import com.sm.statemaster.entity.PinCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PinCodeRepository extends JpaRepository<PinCode,Long>, JpaSpecificationExecutor {

    Page<PinCode> findByDeletedAtNull(Pageable pages);



    PinCode findByPinIdAndDeletedAtNull(Long pinCodeId);

    Optional<PinCode> findByPinCode(Long pincode);
}
