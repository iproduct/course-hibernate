package course.hibernate.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.Valid;
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
    @Valid
    private Name name;
    @Convert(converter = GenderConverter.class)
    private Gender gender;
    private String notes;
    private URL website;
    private boolean starred;
}
