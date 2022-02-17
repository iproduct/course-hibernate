package course.hibernate.spring.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.JoinFormula;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import static course.hibernate.spring.entity.Role.READER;

@Entity
@Table(name = "users",
//        indexes = @Index(name = "uniqueUsernameIndex", columnList = "username", unique = true),
        uniqueConstraints=@UniqueConstraint(name = "uc_username", columnNames = {"username"}))
@NamedEntityGraph(name = "User.detail", attributeNodes = @NamedAttributeNode("roles"))
public class User extends BaseMappedSuperclass implements UserDetails {
    @NotNull
    @Size(min=2, max=20)
    @NonNull
    private String firstName;
    @NotNull
    @Size(min=2, max=20)
    @NonNull
    private String lastName;
    @NotNull
    @Size(min=5, max=20)
    @NonNull
    @Column(updatable = false, nullable = false)
    private String username;
    @NotNull
    @Size(min=6, max=128)
    @NonNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @ElementCollection //(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = Set.of(READER);
    private boolean active = true;

//    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
//    @PrimaryKeyJoinColumn
//    private UserData data;

    private String phoneNumber;

    @ManyToOne
//    @JoinFormula( "LTRIM(SUBSTRING(phone_number, 2, 3))" )
    @JoinFormula( "CAST(REGEXP_REPLACE(phone_number, '.?(\\d+)-.*', '\\1') AS DECIMAL(3))" )
    private Country country;


    public User() {
    }

    public User(@NonNull String firstName, @NonNull String lastName, @NonNull String username, @NonNull String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    public User(Long id, @NonNull String firstName, @NonNull String lastName, @NonNull String username, @NonNull String password) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    public User(Long id, LocalDateTime created, LocalDateTime modified, @NonNull String firstName, @NonNull String lastName, @NonNull String username, @NonNull String password) {
        super(id, created, modified);
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    public User(@NonNull String firstName, @NonNull String lastName, @NonNull String username, @NonNull String password, Set<Role> role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.roles = role;
    }

    public User(@NonNull String firstName, @NonNull String lastName, @NonNull String username, @NonNull String password, Set<Role> role, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.roles = role;
        this.phoneNumber = phoneNumber;
    }
    public User(Long id, @NonNull String firstName, @NonNull String lastName, @NonNull String username, @NonNull String password, Set<Role> role) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.roles = role;
    }

    public User(Long id, LocalDateTime created, LocalDateTime modified, @NonNull String firstName, @NonNull String lastName, @NonNull String username, @NonNull String password, Set<Role> role) {
        super(id, created, modified);
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.roles = role;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
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

    public String getUsername() {
        return username;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

//    public UserData getData() {
//        return data;
//    }
//
//    public void setData(UserData data) {
//        this.data = data;
//    }

    @Override
    public boolean isAccountNonExpired() {
        return active;
    }

    @Override
    public boolean isAccountNonLocked() {
        return active;
    }

    @Override
    @Transient
    public boolean isCredentialsNonExpired() {
        return active;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }

    public void setUsername(String userame) {
        this.username = userame;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.toString()))
                .collect(Collectors.toList());
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> role) {
        this.roles = role;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", username='").append(username).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", roles=").append(roles);
        sb.append(", active=").append(active);
//        sb.append(", data=").append(data);
        sb.append(", accountNonExpired=").append(isAccountNonExpired());
        sb.append(", accountNonLocked=").append(isAccountNonLocked());
        sb.append(", credentialsNonExpired=").append(isCredentialsNonExpired());
        sb.append(", enabled=").append(isEnabled());
        sb.append(", authorities=").append(getAuthorities());
        sb.append('}');
        return sb.toString();
    }
}
