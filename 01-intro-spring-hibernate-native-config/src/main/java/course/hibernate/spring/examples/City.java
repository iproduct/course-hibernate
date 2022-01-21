package course.hibernate.spring.examples;

import lombok.Data;
import org.hibernate.annotations.Target;

import javax.persistence.*;

interface Coordinates {
    double x();
    double y();
}

@Embeddable
class GPS implements Coordinates {

    private double latitude;

    private double longitude;

    public GPS() {
    }

    public GPS(double latitude, double longitude) {
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

@Entity(name = "City")
public class City {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Embedded
    @Target( GPS.class )
    private Coordinates coordinates;
}
