package com.beth.infy.model;

import com.beth.infy.common.persistence.IAuditableEntity;

public interface IUserOrm extends IAuditableEntity {

    Long getUserId();
    void setUserId(Long userId);

    String getUserName();
    void setUserName(String userName);

    String getPassword();
    void setPassword(String password);

    String getFirstName();
    void setFirstName(String firstName);

    String getLastName();
    void setLastName(String lastName);

    String getEmail();
    void setEmail(String email);

    String getCreatedBy();
    void setCreatedBy(String createdBy);

    String getActive();
    void setActive(String active);

    String getCreatedTimestamp();
    void setCreatedTimestamp(String createdTimestamp);

    String getLastModifiedBy();
    void setLastModifiedBy(String lastModifiedBy);

    String getLastModifiedDate();
    void setLastModifiedDate(String lastModifiedDate);
    
}
