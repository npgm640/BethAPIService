package com.beth.infy.model;
import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "MT202")
public class Mt202Orm implements Serializable, IMt202Orm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="MT202_ID", updatable = false, nullable = false)
    private long mt202Id;

    @Column(name="TAG")
    private String tag;

    @Column(name="FIELD_NAME")
    private String fieldName;

    @Column(name="STATUS")
    private String status;

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
    public Long getMt202Id() {
        return this.mt202Id;
    }

    @Override
    public void setMt202Id(Long mt202Id) {
        this.mt202Id = mt202Id;
    }

    @Override
    public String getTag() {
        return this.tag;
    }

    @Override
    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String getFieldName() {
        return this.fieldName;
    }

    @Override
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public String getStatus() {
        return this.status;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
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
