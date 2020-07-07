package com.beth.infy.common.persistence;

import org.joda.time.DateTime;

import java.io.Serializable;

public interface ICommonColumns extends Serializable {
    
    String getCreatedTimestamp();
    
    String getLastModifiedBy();

    String getLastModifiedTimestamp();
    
    void setCreatedTimestamp(String var1);
    
    void setLastModifiedBy(String var1);
    
    void setLastModifiedTimestamp(String var1);
    
}
