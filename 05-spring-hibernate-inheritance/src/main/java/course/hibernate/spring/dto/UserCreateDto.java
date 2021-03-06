package course.hibernate.spring.dto;

import course.hibernate.spring.entity.Role;
import lombok.*;

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
    private String username;
    @NotNull
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")
    @NonNull
    private String password;
    private Set<Role> roles = Set.of(READER);
}
