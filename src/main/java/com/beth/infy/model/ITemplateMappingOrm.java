package com.beth.infy.model;

public interface ITemplateMappingOrm {

    Long getTemplateMappingId();
    void setTemplateMappingId(Long templateMappingId);

    TemplateOrm getTemplate();
    void setTemplate(TemplateOrm template);

    String getMappingData();
    void setMappingData(String mappingData);

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
