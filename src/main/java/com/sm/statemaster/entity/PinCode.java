package com.sm.statemaster.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sm.statemaster.enums.PinCodeStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "pin_code")
@SQLDelete(sql = "update pin_code set pin_status='INACTIVE',delete_at= now() where pin_id=?")
public class PinCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pin_id")
    private Long pinId;

    @Column(name = "pin_code")
    private Long pinCode;

    @ManyToOne
    @JoinColumn(name = "city_id")
    @JsonIgnore
    private City cities;

    @ManyToOne
    @JoinColumn(name = "state_id")
    @JsonIgnore
    private State states;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @UpdateTimestamp
    @Column(name = "update_at")
    private LocalDateTime updateAt;

    @Column(name = "pin_status")
    @Enumerated(EnumType.STRING)
    private PinCodeStatus pinStatus = PinCodeStatus.ACTIVE;

    public PinCode() {
    }

    public PinCode(Long pinId, Long pinCode, City cities, State states, LocalDateTime createdAt, LocalDateTime deletedAt, LocalDateTime updateAt, PinCodeStatus pinStatus) {
        this.pinId = pinId;
        this.pinCode = pinCode;
        this.cities = cities;
        this.states = states;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
        this.updateAt = updateAt;
        this.pinStatus = pinStatus;
    }

    public State getStates() {
        return states;
    }

    public void setStates(State states) {
        this.states = states;
    }

    public Long getPinId() {
        return pinId;
    }

    public void setPinId(Long pinId) {
        this.pinId = pinId;
    }

    public Long getPinCode() {
        return pinCode;
    }

    public void setPinCode(Long pinCode) {
        this.pinCode = pinCode;
    }

    public City getCities() {
        return cities;
    }

    public void setCities(City cities) {
        this.cities = cities;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public PinCodeStatus getPinStatus() {
        return pinStatus;
    }

    public void setPinStatus(PinCodeStatus pinStatus) {
        this.pinStatus = pinStatus;
    }
}
