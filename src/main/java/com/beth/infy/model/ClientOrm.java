package com.beth.infy.model;
import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "CLIENT")
public class ClientOrm implements Serializable, IClientOrm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="CLIENT_ID", updatable = false, nullable = false)
    private long clientId;

    @Column(name="CLIENT_NAME")
    private String clientName;

    @Column(name="TYPE")
    private String type;

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

    public Long getClientId() {
        return this.clientId;
    }

    @Override
    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    @Override
    public String getClientName() {
        return this.clientName;
    }

    @Override
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    @Override
    public String getType() {
        return this.getType();
    }

    @Override
    public void setType(String type) {
            this.type = type;
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
        return this.getActive();
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
        return this.getLastModifiedBy();
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
