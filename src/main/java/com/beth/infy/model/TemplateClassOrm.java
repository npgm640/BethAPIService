package com.beth.infy.model;
import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "TEMPLATE_CLASS")
public class TemplateClassOrm implements Serializable, ITemplateClassOrm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="TEMPLATE_CLASS_ID", updatable = false, nullable = false)
    private long templateClassId;

    @OneToOne
    @JoinColumn(name="CLIENT_ID")
    private ClientOrm client;

    @OneToOne
    @JoinColumn(name="TEMPLATE_ID")
    private TemplateOrm template;

    @Column(name="CLAZZ_NAME")
    private String clazzName;

    @Column(name="SUPER_CLAZZ")
    private String superClazz;

    @Column(name="PACKAGE_NAME")
    private String packageName;

    @Column(name="CLAZZ_OUTPUT_LOCATION")
    private String clazzOutputLocation;

    @Column(name="METHOD_URIS")
    private String methodUris;

    @Column(name="DESCRIPTION")
    private String description;

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
    public Long getTemplateClassId() {
        return this.templateClassId;
    }

    @Override
    public void setTemplateClassId(Long templateClassId) {
        this.templateClassId = templateClassId;

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
    public TemplateOrm getTemplate() {
        return this.template;
    }

    @Override
    public void setTemplate(TemplateOrm template) {
        this.template = template;
    }

    @Override
    public String getClazzName() {
        return this.clazzName;
    }

    @Override
    public void setClazzName(String clazzName) {
            this.clazzName = clazzName;
    }

    @Override
    public String getSuperClazz() {
        return this.superClazz;
    }

    @Override
    public void setSuperClazz(String clazzName) {
            this.superClazz = superClazz;
    }

    @Override
    public String getPackageName() {
        return this.packageName;
    }

    @Override
    public void setPackageName(String packageName) {
            this.packageName = packageName;
    }

    @Override
    public String getClazzOutputLocation() {
        return this.clazzOutputLocation;
    }

    @Override
    public void setClazzOutputLocation(String clazzOutputLocation) {
        this.clazzOutputLocation = clazzOutputLocation;
    }

    @Override
    public String getMethodUris() {
        return this.methodUris;
    }

    @Override
    public void setMethodUris(String methodUris) {
            this.methodUris = methodUris;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
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
