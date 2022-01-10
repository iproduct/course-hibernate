package course.hibernate.spring.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static course.hibernate.spring.entity.Role.READER;

@Entity
public class User extends BaseMappedSuperclass {
    @NotNull
    @Size(min=2, max=20)
    @NonNull
    private String firstName;
    @NotNull
    @Size(min=2, max=20)
    @NonNull
    private String lastName;
    @NotNull
    @Size(min=2, max=20)
    @NonNull
    private String userame;
    @NotNull
    @Size(min=6, max=128)
    @NonNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<Role> role = Set.of(READER);

    public User(@NonNull String firstName, @NonNull String lastName, @NonNull String userame, @NonNull String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userame = userame;
        this.password = password;
    }

    public User(Long id, @NonNull String firstName, @NonNull String lastName, @NonNull String userame, @NonNull String password) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.userame = userame;
        this.password = password;
    }

    public User(Long id, LocalDateTime created, LocalDateTime modified, @NonNull String firstName, @NonNull String lastName, @NonNull String userame, @NonNull String password) {
        super(id, created, modified);
        this.firstName = firstName;
        this.lastName = lastName;
        this.userame = userame;
        this.password = password;
    }

    public User(@NonNull String firstName, @NonNull String lastName, @NonNull String userame, @NonNull String password, Set<Role> role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userame = userame;
        this.password = password;
        this.role = role;
    }

    public User(Long id, @NonNull String firstName, @NonNull String lastName, @NonNull String userame, @NonNull String password, Set<Role> role) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.userame = userame;
        this.password = password;
        this.role = role;
    }

    public User(Long id, LocalDateTime created, LocalDateTime modified, @NonNull String firstName, @NonNull String lastName, @NonNull String userame, @NonNull String password, Set<Role> role) {
        super(id, created, modified);
        this.firstName = firstName;
        this.lastName = lastName;
        this.userame = userame;
        this.password = password;
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserame() {
        return userame;
    }

    public void setUserame(String userame) {
        this.userame = userame;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRole() {
        return role;
    }

    public void setRole(Set<Role> role) {
        this.role = role;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("id=").append(getId());
        sb.append(", created=").append(getCreated());
        sb.append(", modified=").append(getModified());
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", userame='").append(userame).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", role=").append(role);
        sb.append('}');
        return sb.toString();
    }
}
