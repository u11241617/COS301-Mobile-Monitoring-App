package the5concurrentnodes.entities;

import javax.persistence.*;


@Entity
@Table(name = "accessleveltb", schema = "", catalog = "mmadb")
public class AccessLevel {
    private int accessLevelId;
    private String description;

    @Id
    @Column(name = "accessLevelID")
    public int getAccessLevelId() {
        return accessLevelId;
    }

    public void setAccessLevelId(int accessLevelId) {
        this.accessLevelId = accessLevelId;
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

        AccessLevel that = (AccessLevel) o;

        if (accessLevelId != that.accessLevelId) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = accessLevelId;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
