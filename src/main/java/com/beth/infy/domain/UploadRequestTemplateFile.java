package com.beth.infy.domain;

public class UploadRequestTemplateFile {
    private String fileType;
    private String content;
    private long customerId;
    private String fileName;
    private String templateMappingLocation;

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
}
