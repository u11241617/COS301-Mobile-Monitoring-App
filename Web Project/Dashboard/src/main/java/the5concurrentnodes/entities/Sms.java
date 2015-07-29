package the5concurrentnodes.entities;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by GASwipper Gcc on 7/22/2015.
 */
@Entity
@Table(name = "smstb", schema = "", catalog = "mmadb")
public class Sms {
    private int smsId;
    private String type;
    private String source;
    private String destination;
    private Timestamp datetime;
    private Device devicetbByDeviceId;

    @Id
    @Column(name = "smsID")
    public int getSmsId() {
        return smsId;
    }

    public void setSmsId(int smsId) {
        this.smsId = smsId;
    }

    @Basic
    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "source")
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Basic
    @Column(name = "destination")
    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    @Basic
    @Column(name = "datetime")
    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sms sms = (Sms) o;

        if (smsId != sms.smsId) return false;
        if (type != null ? !type.equals(sms.type) : sms.type != null) return false;
        if (source != null ? !source.equals(sms.source) : sms.source != null) return false;
        if (destination != null ? !destination.equals(sms.destination) : sms.destination != null) return false;
        if (datetime != null ? !datetime.equals(sms.datetime) : sms.datetime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = smsId;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (source != null ? source.hashCode() : 0);
        result = 31 * result + (destination != null ? destination.hashCode() : 0);
        result = 31 * result + (datetime != null ? datetime.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "deviceID", referencedColumnName = "deviceID", nullable = false)
    public Device getDevicetbByDeviceId() {
        return devicetbByDeviceId;
    }

    public void setDevicetbByDeviceId(Device devicetbByDeviceId) {
        this.devicetbByDeviceId = devicetbByDeviceId;
    }
}
