package course.hibernate.spring.entity;

import lombok.Data;
import org.hibernate.annotations.Any;
import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table( name = "property_holder" )
@Data
public class PropertyHolder {

    @Id
    private Long id;

    @ManyToAny(
            metaDef = "PropertyMetaDef",
            metaColumn = @Column( name = "property_type" )
    )
    @JoinTable(name = "holder_properties",
            joinColumns = @JoinColumn(name = "holder_id"),
            inverseJoinColumns = @JoinColumn(name = "property_id")
    )
    @JoinColumn( name = "property_id" )
    private Set<Property> properties = new HashSet<>();


}
