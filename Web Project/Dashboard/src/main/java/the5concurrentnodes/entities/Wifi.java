package the5concurrentnodes.entities;

import javax.persistence.*;


@Entity
@Table(name = "wifitb", schema = "", catalog = "mmadb")
public class Wifi {
    private int id;
    private String ssid;
    private String mac;
    private String time;
    private String bssid;
    private String status;
    private String ip;
    private Device devicetbByDeviceId;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "ssid")
    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    @Basic
    @Column(name = "mac")
    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    @Basic
    @Column(name = "time")
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Basic
    @Column(name = "bssid")
    public String getBssid() {
        return bssid;
    }

    public void setBssid(String bssid) {
        this.bssid = bssid;
    }

    @Basic
    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "ip")
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Wifi wifi = (Wifi) o;

        if (id != wifi.id) return false;
        if (ssid != null ? !ssid.equals(wifi.ssid) : wifi.ssid != null) return false;
        if (mac != null ? !mac.equals(wifi.mac) : wifi.mac != null) return false;
        if (time != null ? !time.equals(wifi.time) : wifi.time != null) return false;
        if (bssid != null ? !bssid.equals(wifi.bssid) : wifi.bssid != null) return false;
        if (status != null ? !status.equals(wifi.status) : wifi.status != null) return false;
        if (ip != null ? !ip.equals(wifi.ip) : wifi.ip != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (ssid != null ? ssid.hashCode() : 0);
        result = 31 * result + (mac != null ? mac.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (bssid != null ? bssid.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (ip != null ? ip.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "deviceId", referencedColumnName = "deviceID", nullable = false)
    public Device getDevicetbByDeviceId() {
        return devicetbByDeviceId;
    }

    public void setDevicetbByDeviceId(Device devicetbByDeviceId) {
        this.devicetbByDeviceId = devicetbByDeviceId;
    }
}
