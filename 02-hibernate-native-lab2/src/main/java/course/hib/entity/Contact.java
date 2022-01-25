package course.hib.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.net.URL;

@Getter
@Setter
@Entity
@NamedQueries({
        @NamedQuery(name = "Contact.findByName_FirstNameAndName_LastName", query = "select c from Contact c where c.name.firstName = :firstName and c.name.lastName = :lastName")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contact implements Serializable {
    @Id
    private Integer id;
    @Embedded
    private Name name;
    private String notes;
    private URL website;
    private boolean starred;
}