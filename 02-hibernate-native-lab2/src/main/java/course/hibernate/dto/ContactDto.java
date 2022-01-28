package course.hibernate.dto;

import lombok.Data;

import java.io.Serializable;
import java.net.URL;

@Data
public class ContactDto implements Serializable {
    private final Integer id;
    private final String nameFirstName;
    private final String nameMiddleName;
    private final String nameLastName;
    private final String notes;
    private final URL website;
    private final boolean starred;
}
