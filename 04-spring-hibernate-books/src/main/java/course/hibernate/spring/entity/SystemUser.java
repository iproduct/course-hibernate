package course.hibernate.spring.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "SystemUser")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class SystemUser implements Serializable {

    @EmbeddedId
    @NonNull
    private PK id;

    @ManyToOne
    @MapsId("subsystem")
    private Subsystem subsystem;

    @NonNull
    private String name;

}
