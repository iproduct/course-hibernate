package course.hibernate.spring.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.loader.entity.plan.AbstractLoadPlanBasedEntityLoader;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table( name = "users",
        uniqueConstraints = @UniqueConstraint(name = "uc_username", columnNames = {"username"}),
        indexes = @Index(name="uniqueUsernameIndex", columnList = "username", unique = true))
public class User extends EntityBase {
    @NotNull
    @Size(min=2, max =20)
    private String firstName;
    @NotNull
    @Size(min=2, max =20)
    private String lastName;
    @NotNull
    @Size(min=5, max =20)
    @Column(updatable = false, nullable = false)
    private String username;
    @NotNull
    @Size(min=5, max =120)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = Set.of(Role.READER);

    public User() {
    }

    public User(Long id) {
        super(id);
    }

    public User(String firstName, String lastName, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    public User(Long id, String firstName, String lastName, String username, String password) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    public User(Long id, LocalDateTime created, LocalDateTime modified, String firstName, String lastName, String username, String password) {
        super(id, created, modified);
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    public User(String firstName, String lastName, String username, String password, Set<Role> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public User(Long id, LocalDateTime created, LocalDateTime modified, String firstName, String lastName, String username, String password, Set<Role> roles) {
        super(id, created, modified);
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + getId() +
                ", created=" + getCreated() +
                ", modified=" + getModified() +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                "} " + super.toString();
    }
}
