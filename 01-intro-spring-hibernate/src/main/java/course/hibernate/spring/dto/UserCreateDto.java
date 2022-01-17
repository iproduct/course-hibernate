package course.hibernate.spring.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import course.hibernate.spring.entity.Role;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

import static course.hibernate.spring.entity.Role.READER;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class UserCreateDto {
    private String firstName;
    @NotNull
    @Size(min=2, max=20)
    @NonNull
    private String lastName;
    @NotNull
    @Size(min=5, max=20)
    @NonNull
    private String username;
    @NotNull
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$")
    @NonNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private Set<Role> roles = Set.of(READER);
}
