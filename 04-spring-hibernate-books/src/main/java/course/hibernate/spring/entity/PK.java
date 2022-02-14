package course.hibernate.spring.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
public class PK implements Serializable {
    private static final long serialVersionUID = -195443016063671997L;
    @ManyToOne(fetch = FetchType.LAZY)
    private Subsystem subsystem;
    private String username;
//    @GeneratedValue
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

    public void setSubsystem(Subsystem subsystem) {
        this.subsystem = subsystem;
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PK{");
        sb.append("subsystem=").append(subsystem);
        sb.append(", username='").append(username).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
