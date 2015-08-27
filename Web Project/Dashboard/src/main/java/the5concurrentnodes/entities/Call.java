package the5concurrentnodes.entities;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;


@Entity
@Table(name = "calltb", schema = "", catalog = "mmadb")
public class Call {
    private int callId;
    private String type;
    private String source;
    private String destination;
    private Timestamp datetime;
    private Time duration;
    private Device devicetbByDeviceId;

    @Id
    @Column(name = "callID")
    public int getCallId() {
        return callId;
    }

    public void setCallId(int callId) {
        this.callId = callId;
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

    @Basic
    @Column(name = "duration")
    public Time getDuration() {
        return duration;
    }

    public void setDuration(Time duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Call call = (Call) o;

        if (callId != call.callId) return false;
        if (type != null ? !type.equals(call.type) : call.type != null) return false;
        if (source != null ? !source.equals(call.source) : call.source != null) return false;
        if (destination != null ? !destination.equals(call.destination) : call.destination != null) return false;
        if (datetime != null ? !datetime.equals(call.datetime) : call.datetime != null) return false;
        if (duration != null ? !duration.equals(call.duration) : call.duration != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = callId;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (source != null ? source.hashCode() : 0);
        result = 31 * result + (destination != null ? destination.hashCode() : 0);
        result = 31 * result + (datetime != null ? datetime.hashCode() : 0);
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
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
