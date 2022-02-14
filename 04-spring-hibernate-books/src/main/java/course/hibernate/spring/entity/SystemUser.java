package course.hibernate.spring.entity;

import lombok.*;

import javax.persistence.*;

@Entity(name = "SystemUser")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class SystemUser {

    @EmbeddedId
    @NonNull
    private PK id;

    @ManyToOne
    @MapsId("subsystem")
    private Subsystem subsystem;

    @NonNull
    private String name;

}
