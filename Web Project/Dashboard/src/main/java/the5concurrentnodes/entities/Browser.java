package the5concurrentnodes.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "browsertb")
public class Browser implements Serializable{
    private int browserId;
    private String name;
    private int browserid;

    @Id
    @Column(name = "browserID")
    public int getBrowserId() {
        return browserId;
    }

    public void setBrowserId(int browserId) {
        this.browserId = browserId;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Browser browser = (Browser) o;

        if (browserId != browser.browserId) return false;
        if (name != null ? !name.equals(browser.name) : browser.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = browserId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Id
    @Column(name = "browserID")
    public int getBrowserid() {
        return browserid;
    }

    public void setBrowserid(int browserid) {
        this.browserid = browserid;
    }
}
