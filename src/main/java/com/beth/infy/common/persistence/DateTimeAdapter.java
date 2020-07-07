package com.beth.infy.common.persistence;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.sql.Timestamp;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;


public class DateTimeAdapter extends XmlAdapter<Timestamp, DateTime> {

    public DateTimeAdapter(){

    }
    @Override
    public DateTime unmarshal(Timestamp v) throws Exception {
        DateTime dt = new DateTime(v.getTime(), DateTimeZone.UTC);
        return dt;
    }

    @Override
    public Timestamp marshal(DateTime v) throws Exception {
        return new Timestamp(v.getMillis());
    }
}
