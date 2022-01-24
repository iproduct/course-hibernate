package course.hibernate.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.net.URL;

@Entity(name = "Contact")
@Data
@NoArgsConstructor
@AllArgsConstructor
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
