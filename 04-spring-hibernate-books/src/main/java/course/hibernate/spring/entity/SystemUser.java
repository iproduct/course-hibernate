package course.hibernate.spring.entity;

import lombok.*;

import javax.persistence.*;

@Entity(name = "SystemUser")
@IdClass(PK.class)
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class SystemUser {

//    @EmbeddedId
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
//    @MapsId("subsystem")
    @NonNull
    private Subsystem subsystem;
    @Id
    @NonNull
    private String username;
//    @Id
//    @GeneratedValue
//    private Integer registrationId;

    @NonNull
    private String name;

}
