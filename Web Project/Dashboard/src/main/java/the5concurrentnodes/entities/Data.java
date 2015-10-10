package the5concurrentnodes.entities;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@Table(name = "dataUsagetb", schema = "", catalog = "mmadb")
public class Data {
    private int dataUsageId;
    private String name;
    private String total;
    private String usage;
    private Timestamp datetime;
    private Device devicetbByDeviceId;

    @Id
    @Column(name = "dataUsageID")
    public int getDataUsageId() {
        return dataUsageId;
    }

    public void setDataUsageId(int dataUsageId) {
        this.dataUsageId= dataUsageId;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "total")
    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    @Basic
    @Column(name = "usage")
    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
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

        Data data = (Data) o;

        if (dataUsageId != data.dataUsageId) return false;
        if (name != null ? !name.equals(data.name) : data.name != null) return false;
        if (total != null ? !total.equals(data.total) : data.total != null) return false;
        if (usage != null ? !usage.equals(data.usage) : data.usage != null) return false;
        if (datetime != null ? !datetime.equals(data.datetime) : data.datetime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = dataUsageId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (total != null ? total.hashCode() : 0);
        result = 31 * result + (usage != null ? usage.hashCode() : 0);
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
