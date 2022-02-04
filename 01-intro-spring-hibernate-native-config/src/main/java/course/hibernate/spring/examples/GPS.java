package course.hibernate.spring.examples;

import lombok.Data;
import org.hibernate.annotations.Parent;

import javax.persistence.Embeddable;

@Embeddable
@Data
public class GPS {

    private double latitude;

    private double longitude;

    @Parent
    private City city;
}
