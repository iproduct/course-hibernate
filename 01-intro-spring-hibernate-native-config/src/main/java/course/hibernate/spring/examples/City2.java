package course.hibernate.spring.examples;

import lombok.Data;
import org.hibernate.annotations.Target;

import javax.persistence.*;

interface Coordinates {
    double x();
    double y();
}

@Embeddable
class GPS2 implements Coordinates {

    private double latitude;

    private double longitude;

    public GPS2() {
    }

    public GPS2(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public double x() {
        return latitude;
    }

    @Override
    public double y() {
        return longitude;
    }
}

@Entity(name = "City2")
public class City2 {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Embedded
    @Target( GPS2.class )
    private Coordinates coordinates;
}
