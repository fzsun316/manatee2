package com.fangzhou.manatee.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Patient.
 */
@Entity
@Table(name = "patient")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Patient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "medical_referral_id")
    private String medicalReferralID;

    @Column(name = "age")
    private Long age;

    @Column(name = "condition_desciption")
    private String conditionDesciption;

    @Column(name = "priority")
    private String priority;

    @Column(name = "deadline")
    private ZonedDateTime deadline;

    @ManyToOne
    private ReferralSource referralSource;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Patient name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMedicalReferralID() {
        return medicalReferralID;
    }

    public Patient medicalReferralID(String medicalReferralID) {
        this.medicalReferralID = medicalReferralID;
        return this;
    }

    public void setMedicalReferralID(String medicalReferralID) {
        this.medicalReferralID = medicalReferralID;
    }

    public Long getAge() {
        return age;
    }

    public Patient age(Long age) {
        this.age = age;
        return this;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public String getConditionDesciption() {
        return conditionDesciption;
    }

    public Patient conditionDesciption(String conditionDesciption) {
        this.conditionDesciption = conditionDesciption;
        return this;
    }

    public void setConditionDesciption(String conditionDesciption) {
        this.conditionDesciption = conditionDesciption;
    }

    public String getPriority() {
        return priority;
    }

    public Patient priority(String priority) {
        this.priority = priority;
        return this;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public ZonedDateTime getDeadline() {
        return deadline;
    }

    public Patient deadline(ZonedDateTime deadline) {
        this.deadline = deadline;
        return this;
    }

    public void setDeadline(ZonedDateTime deadline) {
        this.deadline = deadline;
    }

    public ReferralSource getReferralSource() {
        return referralSource;
    }

    public Patient referralSource(ReferralSource referralSource) {
        this.referralSource = referralSource;
        return this;
    }

    public void setReferralSource(ReferralSource referralSource) {
        this.referralSource = referralSource;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Patient patient = (Patient) o;
        if (patient.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, patient.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Patient{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", medicalReferralID='" + medicalReferralID + "'" +
            ", age='" + age + "'" +
            ", conditionDesciption='" + conditionDesciption + "'" +
            ", priority='" + priority + "'" +
            ", deadline='" + deadline + "'" +
            '}';
    }
}
