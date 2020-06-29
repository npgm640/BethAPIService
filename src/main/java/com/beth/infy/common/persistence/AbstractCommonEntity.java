package com.beth.infy.common.persistence;

import org.joda.time.DateTime;

import javax.persistence.Column;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;

public abstract class AbstractCommonEntity<T> extends AbstractEntity implements ICommonColumns, Serializable {

    private static final long serialVersionUID = 1L;
    
   @Column(name="CRTD_TS")
    private String createdTimestamp;

    @Column(name="LAST_MDFD_BY")
    private String lastModifiedBy;

    @Column(name="LAST_MDFD_TS")
    private String lastModifiedDate;

}
