package com.beth.infy.model;

public interface ITemplateOrm {

    long getTemplateId();
    void setTemplateId(long templateId);

    ClientOrm getClient();
    void setClient(ClientOrm client);

    String getTemplateName();
    void setTemplateName(String templateName);

    String getTemplateType();
    void setTemplateType(String templateType);

    String getTemplateMappingLocation();
    void setTemplateMappingLocation(String templateMappingLocation);

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
