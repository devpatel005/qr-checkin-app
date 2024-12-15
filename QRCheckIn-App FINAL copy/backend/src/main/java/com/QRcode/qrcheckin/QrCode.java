package com.QRcode.qrcheckin;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "qr_codes")
public class QrCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String middleName;
    private String lastName;
    private String gradeLvl;
    private String wing;
    private String fatherName;
    private String motherName;
    private String fatherNum;
    private String motherNum;
    private String fatherEmail;
    private String motherEmail;
    private String medInfo;

    private String qrId;

    private String checkInStatus = "Not Checked In"; // Default value


    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGradeLvl() {
        return gradeLvl;
    }

    public void setGradeLvl(String gradeLvl) {
        this.gradeLvl = gradeLvl;
    }

    public String getWing() {
        return wing;
    }

    public void setWing(String wing) {
        this.wing = wing;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getFatherNum() {
        return fatherNum;
    }

    public void setFatherNum(String fatherNum) {
        this.fatherNum = fatherNum;
    }

    public String getMotherNum() {
        return motherNum;
    }

    public void setMotherNum(String motherNum) {
        this.motherNum = motherNum;
    }

    public String getFatherEmail() {
        return fatherEmail;
    }

    public void setFatherEmail(String father_email) {
        this.fatherEmail = father_email;
    }

    public String getMotherEmail() {
        return motherEmail;
    }

    public void setMotherEmail(String motherEmail) {
        this.motherEmail = motherEmail;
    }

    public String getMedInfo() {
        return medInfo;
    }

    public void setMedInfo(String medInfo) {
        this.medInfo = medInfo;
    }

    public String getQrId() {
        return qrId;
    }

    public void setQrId(String qrId) {
        this.qrId = qrId;
    }

    public String getCheckInStatus() {
        return checkInStatus;
    }

    public void setCheckInStatus(String checkInStatus) {
        this.checkInStatus = checkInStatus;
    }
}
