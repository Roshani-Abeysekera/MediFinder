package com.adl.interns.medifinder.entity;

import javax.persistence.*;

@Entity
@Table(name = "request_product")
public class RequestProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String drugName;
    private String email;
    private double latitude;
    private double longitude;
    private boolean notificationIsSent;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public boolean isNotificationIsSent() {
        return notificationIsSent;
    }

    public void setNotificationIsSent(boolean notificationIsSent) {
        this.notificationIsSent = notificationIsSent;
    }

    @Override
    public String toString() {
        return "RequestProduct{" +
                "id=" + id +
                ", drugName='" + drugName + '\'' +
                ", email='" + email + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", notificationIsSent" +notificationIsSent +
                '}';
    }
}
