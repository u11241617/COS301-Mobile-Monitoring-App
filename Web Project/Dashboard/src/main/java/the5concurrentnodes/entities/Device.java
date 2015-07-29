package the5concurrentnodes.entities;

import javax.persistence.*;

/**
 * Created by GASwipper Gcc on 7/23/2015.
 */
@Entity
@Table(name = "devicetb", schema = "", catalog = "mmadb")
public class Device {
    private int deviceId;
    private String make;
    private String model;
    private String os;
    private String network;
    private String imeNumber;
    private User usertbByUserId;

    @Id
    @Column(name = "deviceID")
    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    @Basic
    @Column(name = "make")
    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    @Basic
    @Column(name = "model")
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Basic
    @Column(name = "os")
    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    @Basic
    @Column(name = "network")
    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    @Basic
    @Column(name = "imeNumber")
    public String getImeNumber() {
        return imeNumber;
    }

    public void setImeNumber(String imeNumber) {
        this.imeNumber = imeNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Device device = (Device) o;

        if (deviceId != device.deviceId) return false;
        if (make != null ? !make.equals(device.make) : device.make != null) return false;
        if (model != null ? !model.equals(device.model) : device.model != null) return false;
        if (os != null ? !os.equals(device.os) : device.os != null) return false;
        if (network != null ? !network.equals(device.network) : device.network != null) return false;
        if (imeNumber != null ? !imeNumber.equals(device.imeNumber) : device.imeNumber != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = deviceId;
        result = 31 * result + (make != null ? make.hashCode() : 0);
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + (os != null ? os.hashCode() : 0);
        result = 31 * result + (network != null ? network.hashCode() : 0);
        result = 31 * result + (imeNumber != null ? imeNumber.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "userID", referencedColumnName = "userID", nullable = false)
    public User getUsertbByUserId() {
        return usertbByUserId;
    }

    public void setUsertbByUserId(User usertbByUserId) {
        this.usertbByUserId = usertbByUserId;
    }
}
