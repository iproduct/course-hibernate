package course.hibernate.spring.entity;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.net.URL;

@Entity(name = "Contact")
@Data
public class Contact {

    @Id
    private Integer id;

    @Embedded
    private Name name;

    private String notes;

    private URL website;

    private boolean starred;

    public Name getName() {
        return name;
    }
}

