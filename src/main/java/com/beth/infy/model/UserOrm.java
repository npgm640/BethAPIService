package com.beth.infy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "USER")
public class UserOrm   implements Serializable, IUserOrm {   // extends AbstractCommonEntity<Long> 

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="USER_ID", updatable = false, nullable = false)
    private long userId;

    @Column(name="USER_NAME")
    private String userName;

    @Column(name="PASSWORD")
    @JsonIgnore
    private String password;

    @Column(name="FIRST_NAME")
    private String firstName;

    @Column(name="LAST_NAME")
    private String lastName;

    @Column(name="EMAIL")
    private String email;

    @Column(name="CRTD_BY")
    private String createdBy;

    @Column(name="ACTIVE")
    private String active;

    @Column(name="CRTD_TS")
    private String createdTimestamp;

    @Column(name="LAST_MDFD_BY")
    private String lastModifiedBy;

    @Column(name="LAST_MDFD_TS")
    private String lastModifiedDate;


    @Override
    public Long getUserId() {
        return this.userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String getUserName() {
        return this.userName;
    }

    @Override
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getFirstName() {
        return this.firstName;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getLastName() {
        return this.lastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getCreatedBy() {
        return this.createdBy;
    }

    @Override
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public String getActive() {
        return this.active;
    }

    @Override
    public void setActive(String active) {
            this.active = active;
    }

    @Override
    public String getCreatedTimestamp() {
        return this.createdTimestamp;
    }

    @Override
    public void setCreatedTimestamp(String createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    @Override
    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    @Override
    public String getLastModifiedTimestamp() {
        return this.getLastModifiedTimestamp();
    }

    @Override
    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Override
    public void setLastModifiedTimestamp(String var1) {
        this.lastModifiedDate = var1;
    }

    @Override
    public String getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    @Override
    public void setLastModifiedDate(String lastModifiedDate) {
            this.lastModifiedDate = lastModifiedDate;
    }

    public Object getUniqueId() {
        return getUserId();
    }
}
