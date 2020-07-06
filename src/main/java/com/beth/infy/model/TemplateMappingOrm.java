package com.beth.infy.model;
import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "TEMPLATE_MAPPING")
public class TemplateMappingOrm implements Serializable, ITemplateMappingOrm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="TEMPLATE_MAPPING_ID", updatable = false, nullable = false)
    private long templateMappingId;

    @OneToOne
    @JoinColumn(name="TEMPLATE_ID")
    private TemplateOrm template;

    @Column(name="MAPPING_DATA")
    private String mappingData;

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


    @Override
    public Long getTemplateMappingId() {
        return this.templateMappingId;
    }

    @Override
    public void setTemplateMappingId(Long templateMappingId) {
        this.templateMappingId=templateMappingId;
    }

    @Override
    public TemplateOrm getTemplate() {
        return this.template;
    }

    @Override
    public void setTemplate(TemplateOrm template) {
        this.template = template;
    }

    @Override
    public String getMappingData() {
        return this.mappingData;
    }

    @Override
    public void setMappingData(String mappingData) {
            this.mappingData = mappingData;
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
