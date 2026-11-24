package org.parkinglot.lab4parkinglot1.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "usergroups")
public class UserGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "userGroup")
    private String userGroup;

    public String getUserGroup() {
        return userGroup;
    }

    public String getUsername() {
        return username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserGroup(String userGroup) {
        this.userGroup = userGroup;
    }
}
