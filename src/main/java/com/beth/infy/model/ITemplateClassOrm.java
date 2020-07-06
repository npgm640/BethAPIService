package com.beth.infy.model;

public interface ITemplateClassOrm {

    Long getTemplateClassId();

    void setTemplateClassId(Long templateClassId);

    ClientOrm getClient();

    void setClient(ClientOrm client);

    TemplateOrm getTemplate();

    void setTemplate(TemplateOrm template);

    String getClazzName();

    void setClazzName(String clazzName);

    String getSuperClazz();

    void setSuperClazz(String clazzName);

    String getPackageName();

    void setPackageName(String packageName);

    String getClazzOutputLocation();

    void setClazzOutputLocation(String clazzOutputLocation);

    String getMethodUris();

    void setMethodUris(String methodUris);

    String getDescription();

    void setDescription(String description);


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

