package course.hibernate.spring.entity;

import lombok.Data;

import javax.persistence.Embeddable;

@Embeddable
@Data
public class Name {

    private String firstName;

    private String middleName;

    private String lastName;
}
