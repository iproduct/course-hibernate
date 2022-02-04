package course.hibernate.spring.examples;

import course.hibernate.spring.examples.GPS;
import lombok.Data;
import org.hibernate.annotations.Target;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "City")
@Data
public class City {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Embedded
    @Target( GPS.class )
    private GPS coordinates;
}
