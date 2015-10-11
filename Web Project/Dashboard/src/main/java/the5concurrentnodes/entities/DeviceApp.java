package the5concurrentnodes.entities;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "deviceapps", schema = "", catalog = "mmadb")
public class DeviceApp {
    private int id;
    private String name;
    private String version;
    private Device devicetbByDeviceId;
    private String apppackage;
    private String status;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    @Column(name = "version")
    public String getVersion() {
        return version;
    }


    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeviceApp deviceApp = (DeviceApp) o;

        if (id != deviceApp.id) return false;
        if (name != null ? !name.equals(deviceApp.name) : deviceApp.name != null) return false;
        if (version != null ? !version.equals(deviceApp.version) : deviceApp.version != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "device_id", referencedColumnName = "deviceID", nullable = false)
    public Device getDevicetbByDeviceId() {
        return devicetbByDeviceId;
    }

    public void setDevicetbByDeviceId(Device devicetbByDeviceId) {
        this.devicetbByDeviceId = devicetbByDeviceId;
    }

    @Basic
    @Column(name = "apppackage")
    public String getApppackage() {
        return apppackage;
    }

    public void setApppackage(String apppackage) {
        this.apppackage = apppackage;
    }

    @Basic
    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
