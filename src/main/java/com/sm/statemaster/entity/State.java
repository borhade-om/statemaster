package com.sm.statemaster.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sm.statemaster.enums.StateStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "state")
@SQLDelete(sql = "update state set state_status='INACTIVE',delete_at= now() where state_id=?")
public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "state_id")
    private Long stateId;

    @Column(name = "state_name")
    private String name;


    @OneToMany(mappedBy = "state")
    @JsonIgnore
    private List<City> cities;

    @OneToMany(mappedBy = "states")
    @JsonIgnore
    private  List<PinCode> pinCodes;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;


    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;


    @UpdateTimestamp
    @Column(name = "update_at")
    private LocalDateTime updateAt;

    @Column(name = "state_status")
    @Enumerated(EnumType.STRING)
    private StateStatus stateStatus= StateStatus.ACTIVE;

    public State() {
    }


    public State(Long stateId, String name, List<City> cities, List<PinCode> pinCodes, LocalDateTime createdAt, LocalDateTime deletedAt, LocalDateTime updateAt, StateStatus stateStatus) {
        this.stateId = stateId;
        this.name = name;
        this.cities = cities;
        this.pinCodes = pinCodes;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
        this.updateAt = updateAt;
        this.stateStatus = stateStatus;
    }

    public List<PinCode> getPinCodes() {
        return pinCodes;
    }

    public void setPinCodes(List<PinCode> pinCodes) {
        this.pinCodes = pinCodes;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }



    public Long getStateId() {
        return stateId;
    }



    public List<City> getCities() {
        return cities;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public StateStatus getStateStatus() {
        return stateStatus;
    }

    public void setStateStatus(StateStatus stateStatus) {
        this.stateStatus = stateStatus;
    }


}
