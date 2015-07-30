package the5concurrentnodes.entities;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * Created by GASwipper Gcc on 7/22/2015.
 */
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
        if (source != call.source) return false;
        if (destination != call.destination) return false;
        if (type != null ? !type.equals(call.type) : call.type != null) return false;
        if (datetime != null ? !datetime.equals(call.datetime) : call.datetime != null) return false;
        if (duration != null ? !duration.equals(call.duration) : call.duration != null) return false;

        return true;
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
