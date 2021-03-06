package the5concurrentnodes.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "visitedwebsitetb", schema = "", catalog = "mmadb")
public class VisitedWebsite {
    private int visitedWebsiteId;
    private String url;
    private Timestamp dateTime;
    private int frequency;
    private Browser browsertbByBrowserId;
    private Device devicetbByDeviceId;

    @Id
    @Column(name = "visitedWebsiteID")
    public int getVisitedWebsiteId() {
        return visitedWebsiteId;
    }

    public void setVisitedWebsiteId(int visitedWebsiteId) {
        this.visitedWebsiteId = visitedWebsiteId;
    }

    @Basic
    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Basic
    @Column(name = "dateTime")
    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    @Basic
    @Column(name = "frequency")
    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VisitedWebsite that = (VisitedWebsite) o;

        if (visitedWebsiteId != that.visitedWebsiteId) return false;
        if (frequency != that.frequency) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;
        if (dateTime != null ? !dateTime.equals(that.dateTime) : that.dateTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = visitedWebsiteId;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (dateTime != null ? dateTime.hashCode() : 0);
        result = 31 * result + frequency;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "browserID", referencedColumnName = "browserID", nullable = false)
    public Browser getBrowsertbByBrowserId() {
        return browsertbByBrowserId;
    }

    public void setBrowsertbByBrowserId(Browser browsertbByBrowserId) {
        this.browsertbByBrowserId = browsertbByBrowserId;
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
