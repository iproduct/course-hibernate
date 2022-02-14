package course.hibernate.spring.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemUserDetails  implements Serializable {
    @Id
    @OneToOne
    @MapsId
    private SystemUser user;
    @NonNull
    private String qualifications;
}
