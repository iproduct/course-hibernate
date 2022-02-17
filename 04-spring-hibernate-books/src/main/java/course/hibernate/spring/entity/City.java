package course.hibernate.spring.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table( name = "City" )
@Data
public class City implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

}
