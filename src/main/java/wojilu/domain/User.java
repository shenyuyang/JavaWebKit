package wojilu.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by shenyuyang
 */
@Entity
public class User {


    private long id;
    private String name;
    private String pwd;
    private String pwdSalt;
    private String email;
    private Date created;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "Pwd")
    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Column(name = "PwdSalt")
    public String getPwdSalt() {
        return pwdSalt;
    }

    public void setPwdSalt(String pwdSalt) {
        this.pwdSalt = pwdSalt;
    }

    @Column(name = "Email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "Created")
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }



}
