package com.beth.infy.model;
import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "TEMPLATE")
public class TemplateOrm implements Serializable, ITemplateOrm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="TEMPLATE_ID", updatable = false, nullable = false)
    private long templateId;

    @OneToOne
    @JoinColumn(name="CLIENT_ID")
    private ClientOrm client;

    @Column(name="TEMPLATE_NAME")
    private String templateName;

    @Column(name="TEMPLATE_TYPE")
    private String templateType;

    @Column(name="TEMPLATE_MAPPING_LOCATION")
    private String templateMappingLocation;

    @Column(name="CRTD_BY")
    private String createdBy;

    @Column(name="ACTIVE")
    private String active;

    @Column(name="CRTD_TS")
    private String createdTimestamp;

    @Column(name="LAST_MDFD_BY")
    private String lastModifiedBy;

    @Column(name="LAST_MDFD_TS")
    private String lastModifiedDate;

    public long getTemplateId() {
        return this.templateId;
    }

    @Override
    public void setTemplateId(long templateId) {
        this.templateId = templateId;
    }

    @Override
    public ClientOrm getClient() {
        return this.client;
    }

    @Override
    public void setClient(ClientOrm client) {
        this.client = client;
    }

    @Override
    public String getTemplateName() {
        return this.templateName;
    }

    @Override
    public void setTemplateName(String templateName) {
            this.templateName = templateName;
    }

    @Override
    public String getTemplateType() {
        return this.getTemplateType();
    }

    @Override
    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }

    @Override
    public String getTemplateMappingLocation() {
        return this.templateMappingLocation;
    }

    @Override
    public void setTemplateMappingLocation(String templateMappingLocation) {
            this.templateMappingLocation = templateMappingLocation;
    }

    @Override
    public String getCreatedBy() {
        return this.createdBy;
    }

    @Override
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public String getActive() {
        return this.active;
    }

    @Override
    public void setActive(String active) {
            this.active = active;
    }

    @Override
    public String getCreatedTimestamp() {
        return this.createdTimestamp;
    }

    @Override
    public void setCreatedTimestamp(String createdTimestamp) {
            this.createdTimestamp = createdTimestamp;
    }

    @Override
    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    @Override
    public void setLastModifiedBy(String lastModifiedBy) {
            this.lastModifiedBy = lastModifiedBy;
    }

    @Override
    public String getLastModifiedDate() {
        return this.getLastModifiedDate();
    }

    @Override
    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }


}
