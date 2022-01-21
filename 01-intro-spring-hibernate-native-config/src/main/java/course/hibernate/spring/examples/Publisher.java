package course.hibernate.spring.examples;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
@Data
public class Publisher {

    private String name;

    @Embedded
    private Location location;

    public Publisher(String name, Location location) {
        this.name = name;
        this.location = location;
    }

    public Publisher() {}

}

