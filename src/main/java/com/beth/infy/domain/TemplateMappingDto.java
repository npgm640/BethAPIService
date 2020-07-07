package com.beth.infy.domain;

public class TemplateMappingDto {
    private long templateId;
    private String mappingData;
    private String active;
    private String crtdBy;
    private String lastMdfdBy;
    private String crtdTs;
    private String lastMdfdTs;
    private String userId;

    public long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(long templateId) {
        this.templateId = templateId;
    }

    public String getMappingData() {
        return mappingData;
    }

    public void setMappingData(String mappingData) {
        this.mappingData = mappingData;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getCrtdBy() {
        return crtdBy;
    }

    public void setCrtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
    }

    public String getLastMdfdBy() {
        return lastMdfdBy;
    }

    public void setLastMdfdBy(String lastMdfdBy) {
        this.lastMdfdBy = lastMdfdBy;
    }

    public String getCrtdTs() {
        return crtdTs;
    }

    public void setCrtdTs(String crtdTs) {
        this.crtdTs = crtdTs;
    }

    public String getLastMdfdTs() {
        return lastMdfdTs;
    }

    public void setLastMdfdTs(String lastMdfdTs) {
        this.lastMdfdTs = lastMdfdTs;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
