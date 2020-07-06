package com.beth.infy.domain;

import java.util.List;
import java.util.Map;

public class ConvertToXmlRequest extends AbstractRequest {
    private String templateType;
    private String content;
    private String mappingFields;
    private long clientId;
    private String schemaFileName;
    private String templateMappingLocation;
    private Map mapping;
    private String templateName;
    private long userId;

    public String getTemplateType() {
        return templateType;
    }

    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public String getSchemaFileName() {
        return schemaFileName;
    }

    public void setSchemaFileName(String schemaFileName) {
        this.schemaFileName = schemaFileName;
    }

    public String getTemplateMappingLocation() {
        return templateMappingLocation;
    }

    public void setTemplateMappingLocation(String templateMappingLocation) {
        this.templateMappingLocation = templateMappingLocation;
    }

    public String getMappingFields() {
        return mappingFields;
    }

    public void SetMappingFields(String mappingFields) {
        this.mappingFields = mappingFields;
    }

    public Map getMapping() {
        return mapping;
    }

    public void setMapping(Map mapping) {
        this.mapping = mapping;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    //helper methods

    public Class[] getGenerateXml_01_parameters() {
        Class<?>[] array = new Class<?>[1];
        array[0] = String.class;
        return array;
    }

    public Class[] populateXml_01_parameters() {
        Class list[] = new Class[2];
        list[0] = String.class;
        list[1] = ConvertToXmlRequest.class;
        return list;
    }
}
