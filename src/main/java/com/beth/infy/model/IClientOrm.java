package com.beth.infy.model;

import com.beth.infy.common.persistence.IAuditableEntity;

public interface IClientOrm  {

    Long getClientId();
    void setClientId(Long clientId);

    String getClientName();
    void setClientName(String clientName);

    String getType();
    void setType(String type);

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
