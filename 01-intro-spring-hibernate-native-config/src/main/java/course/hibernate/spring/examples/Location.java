package course.hibernate.spring.examples;

import lombok.Data;

import javax.persistence.Embeddable;

@Embeddable
@Data
public class Location {

    private String country;

    private String city;

    public Location(String country, String city) {
        this.country = country;
        this.city = city;
    }

    public Location() {}


    //Getters and setters are omitted for brevity
}
