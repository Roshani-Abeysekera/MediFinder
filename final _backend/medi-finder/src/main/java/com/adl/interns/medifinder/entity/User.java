package com.adl.interns.medifinder.entity;


import java.util.HashSet;
import java.util.Set;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;


import java.io.Serializable;

@Entity
@Table(name="users")

public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="user_id")
    private Long userId;

    @Column(name="email")
    private String email;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String LastName;

    @Column(name="cust_password")
    private String password;

    @Column(name="is_verified")
    private boolean isVerified;

    @Column(name="longitude")
    private double longitude;

    @Column(name="latitude")
    private double latitude;

    @ManyToMany(targetEntity =Role.class, fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns =  @JoinColumn(name ="user_id"),inverseJoinColumns= @JoinColumn(name="roleid"))
    private Set<Role> roles =new HashSet<>();

//    @OneToMany(targetEntity = Drug.class,fetch = FetchType.LAZY)
//    @JoinColumn(name = "id", nullable = false,updatable = false,insertable = false)
//    private List<Drug> drug;

//    public List<Drug> getDrug() {
//        return drug;
//    }
//
//    public void setDrug(List<Drug> drug) {
//        this.drug = drug;
//    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;

    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {

        return "User{" +
                "userId=" + userId +

                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", LastName='" + LastName + '\'' +
                ", password='" + password + '\'' +

                ", isVerified=" + isVerified +
                ", longitude=" + longitude +
                ", latitude=" + latitude +

                '}';
    }
}
