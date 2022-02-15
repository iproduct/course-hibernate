package course.hibernate.spring.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class UserData  implements Serializable {
    // setting id = user id
    @Id
    @Column(unique = true, nullable = false)
    @NonNull
    private Long id;

    // user with this associated settings
    @MapsId
    @OneToOne
    @JoinColumn(name = "id")
    @ToString.Exclude
    private User user;

    @NonNull
    private String qualifications;
}
