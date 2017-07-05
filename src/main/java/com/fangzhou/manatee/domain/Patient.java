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
public class Patient extends AbstractAuditingEntity implements Serializable {

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

    @Column(name = "requesting_assignment")
    private Integer requestingAssignment;

    @Column(name = "residentnp_notified")
    private Integer residentnpNotified;

    @Column(name = "attending_notified")
    private Integer attendingNotified;

    @Column(name = "note")
    private String note;

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

    public Integer getRequestingAssignment() {
        return requestingAssignment;
    }

    public Patient requestingAssignment(Integer requestingAssignment) {
        this.requestingAssignment = requestingAssignment;
        return this;
    }

    public void setRequestingAssignment(Integer requestingAssignment) {
        this.requestingAssignment = requestingAssignment;
    }

    public Integer getResidentnpNotified() {
        return residentnpNotified;
    }

    public Patient residentnpNotified(Integer residentnpNotified) {
        this.residentnpNotified = residentnpNotified;
        return this;
    }

    public void setResidentnpNotified(Integer residentnpNotified) {
        this.residentnpNotified = residentnpNotified;
    }

    public Integer getAttendingNotified() {
        return attendingNotified;
    }

    public Patient attendingNotified(Integer attendingNotified) {
        this.attendingNotified = attendingNotified;
        return this;
    }

    public void setAttendingNotified(Integer attendingNotified) {
        this.attendingNotified = attendingNotified;
    }

    public String getNote() {
        return note;
    }

    public Patient note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
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
            ", requestingAssignment='" + requestingAssignment + "'" +
            ", residentnpNotified='" + residentnpNotified + "'" +
            ", attendingNotified='" + attendingNotified + "'" +
            ", note='" + note + "'" +
            '}';
    }
}
