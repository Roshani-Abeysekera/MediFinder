package com.adl.interns.medifinder.service;

import com.adl.interns.medifinder.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


public class CustomerDetails implements UserDetails {

    private Long id;

    private String username;

    private String email;

    @JsonIgnore
    private String password;

    private boolean isVerified;

    private Collection<? extends GrantedAuthority> authorities;

    private double latitude;

    private double longitude;

    private User user;

    public CustomerDetails(Long id, String username, String email, String password,boolean isVerified,
                           Collection<? extends GrantedAuthority> authorities,
                           double latitude, double longitude) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.isVerified=isVerified;
        this.authorities = authorities;
        this.longitude=longitude;
        this.latitude=latitude;
    }

    public static CustomerDetails build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_"+role.getRole().name()))
                .collect(Collectors.toList());

        return new CustomerDetails(
                user.getUserId(),
                user.getFirstName(),
                user.getEmail(),
                user.getPassword(),
                user.isVerified(),
                authorities,user.getLatitude(), user.getLatitude());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isVerified;
    }
}
