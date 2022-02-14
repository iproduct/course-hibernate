package course.hibernate.spring.entity;

import javax.persistence.Entity;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

//@Entity
public class SystemUserDetails {
    @OneToOne
    @MapsId
    private SystemUser user;
    private String qualifications;
}
