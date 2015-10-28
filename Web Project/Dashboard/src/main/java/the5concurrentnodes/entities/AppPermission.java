package the5concurrentnodes.entities;

import javax.persistence.*;


@Entity
@Table(name = "apppermissions", schema = "", catalog = "mmadb")
public class AppPermission {
    private int id;
    private String label;
    private String description;
    private DeviceApp deviceappsByAppId;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "label")
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppPermission that = (AppPermission) o;

        return id == that.id && !(label != null ? !label.equals(that.label) :
                that.label != null) && !(description != null ?
                !description.equals(that.description) : that.description != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (label != null ? label.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "app_id", referencedColumnName = "id", nullable = false)
    public DeviceApp getDeviceappsByAppId() {
        return deviceappsByAppId;
    }

    public void setDeviceappsByAppId(DeviceApp deviceappsByAppId) {

        this.deviceappsByAppId = deviceappsByAppId;
    }
}
