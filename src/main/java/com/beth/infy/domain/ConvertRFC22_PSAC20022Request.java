package com.beth.infy.domain;

public class ConvertRFC22_PSAC20022Request {
    private String fileType;
    private String content;
    private long customerId;
    private String fileName;
    private String templateMappingLocation;
    private String templateName;

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getTemplateMappingLocation() {
        return templateMappingLocation;
    }

    public void setTemplateMappingLocation(String templateMappingLocation) {
        this.templateMappingLocation = templateMappingLocation;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }
}
