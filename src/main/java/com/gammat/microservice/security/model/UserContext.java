package com.gammat.microservice.security.model;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
 * Created by alejandro on 11/07/17.
 */
public class UserContext {

    private final String firstName;
    private final String lastName;
    private final String username;
    private final List<Integer> groups;

    private final List<GrantedAuthority> authorities;

    private UserContext(String username, String firstName, String lastName, List<Integer> groups, List<GrantedAuthority> authorities) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.groups = groups;
        this.authorities = authorities;
    }

    public static UserContext create(String username, String firstName, String lastName, List<Integer> groups,  List<GrantedAuthority> authorities) {
        if (StringUtils.isBlank(username)) throw new IllegalArgumentException("Username is blank: " + username);
        return new UserContext(username, firstName, lastName, groups,  authorities);
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<Integer> getGroups() {
        return groups;
    }

    public List<GrantedAuthority> getAuthorities() {
        return authorities;
    }
}