package com.beth.infy.model;

public interface IMt202Orm {

    Long getMt202Id();
    void setMt202Id(Long mt202Id);

    String getTag();
    void setTag(String tag);

    String getFieldName();
    void setFieldName(String fieldName);

    String getStatus();
    void setStatus(String status);

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
