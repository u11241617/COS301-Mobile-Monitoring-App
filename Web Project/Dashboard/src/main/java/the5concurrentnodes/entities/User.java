package the5concurrentnodes.entities;

import javax.persistence.*;


@Entity
@Table(name = "usertb", schema = "", catalog = "mmadb")
public class User {
    private int userId;
    private String email;
    private String password;
    private byte firstLogin;
    private AccessLevel accessleveltbByAccessLevelId;

    @Id
    @Column(name = "userID")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "firstLogin")
    public byte getFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(byte firstLogin) {
        this.firstLogin = firstLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (userId != user.userId) return false;
        if (firstLogin != user.firstLogin) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (int) firstLogin;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "accessLevelID", referencedColumnName = "accessLevelID", nullable = false)
    public AccessLevel getAccessleveltbByAccessLevelId() {
        return accessleveltbByAccessLevelId;
    }

    public void setAccessleveltbByAccessLevelId(AccessLevel accessleveltbByAccessLevelId) {
        this.accessleveltbByAccessLevelId = accessleveltbByAccessLevelId;
    }
}
