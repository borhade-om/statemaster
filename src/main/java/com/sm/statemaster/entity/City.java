package com.sm.statemaster.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sm.statemaster.enums.CityStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "city")
@SQLDelete(sql = "update city set city_status='INACTIVE' ,delete_at= now() where city_id=?")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id")
    private Long cityId;

    @Column(name = "city_name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "state_id")
    @JsonIgnore
    private State state;

    @OneToMany(mappedBy = "cities")
    @JsonIgnore
    private List<PinCode> pincode;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "deleted_at")

    private LocalDateTime deletedAt;

    @UpdateTimestamp
    @Column(name = "update_at")
    private LocalDateTime updateAt;

    @Column(name = "city_status")
    @Enumerated(EnumType.STRING)
    private CityStatus cityStatus= CityStatus.ACTIVE;

    public City() {
    }

    public City(Long cityId, String name, State state, List<PinCode> pincode, LocalDateTime createdAt, LocalDateTime deletedAt, LocalDateTime updateAt, CityStatus cityStatus) {
        this.cityId = cityId;
        this.name = name;
        this.state = state;
        this.pincode = pincode;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
        this.updateAt = updateAt;
        this.cityStatus = cityStatus;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public List<PinCode> getPincode() {
        return pincode;
    }

    public void setPincode(List<PinCode> pincode) {
        this.pincode = pincode;
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

    public CityStatus getCityStatus() {
        return cityStatus;
    }

    public void setCityStatus(CityStatus cityStatus) {
        this.cityStatus = cityStatus;
    }
}
