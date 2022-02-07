package course.hibernate.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints=@UniqueConstraint(name = "uc_username", columnNames = {"username"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username", length = 40)
    private String username;

    @Column(name = "password", length = 80)
    private String password;

    @Column(name = "fname", length = 40)
    private String fname;

    @Column(name = "lname", length = 40)
    private String lname;

    @Convert(converter = GenderConverter.class)
    private Gender gender = Gender.UNSPECIFIED;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id",
                    foreignKey = @ForeignKey(name = "user_roles_fk")))
    private Set<Role> role;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "updated")
    private LocalDateTime updated;

}
