package course.hibernate.spring.entity;

import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

@Entity
@Table(name = "Person" )
@Data
public class Person {

    @Id
    private Long id;

    private String name;

    private String cityName;

    @ManyToOne
    @NotFound( action = NotFoundAction.IGNORE )
    @JoinColumn(
            name = "cityName",
            referencedColumnName = "name",
            insertable = false,
            updatable = false
    )
    private City city;

}
