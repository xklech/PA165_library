package cz.muni.fi.pa165.library.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ws.rs.WebApplicationException;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

    public class DateAdapter extends XmlAdapter<String, Date> {
    final static Logger log = LoggerFactory.getLogger(DateAdapter.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
    "MM/dd/yyyy");

    @Override
    public String marshal(Date v) {
        return dateFormat.format(v);
    }

    @Override
    public Date unmarshal(String v) {
        try {
            log.debug("Parse date: "+v);
            return dateFormat.parse(v);
        } catch (ParseException e) {
            return null;
        }
    }
}