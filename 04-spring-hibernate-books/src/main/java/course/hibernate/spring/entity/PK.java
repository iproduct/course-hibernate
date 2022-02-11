package course.hibernate.spring.entity;

import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.io.Serializable;

public class PK implements Serializable {
    private static final long serialVersionUID = -195443016063671997L;
    private Subsystem subsystem;
    private String username;
//    private Integer registrationId;
    public PK(){
    }

    public PK(Subsystem subsystem, String username) {
        this.subsystem = subsystem;
        this.username = username;
    }

    public Subsystem getSubsystem() {
        return subsystem;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PK)) return false;

        PK pk = (PK) o;

        if (!subsystem.equals(pk.subsystem)) return false;
        return username.equals(pk.username);
    }

    @Override
    public int hashCode() {
        int result = subsystem.hashCode();
        result = 31 * result + username.hashCode();
        return result;
    }
}
