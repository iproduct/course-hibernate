package course.hibernate.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Name {
    @NotNull
    @Size(min = 2, max = 20)
    private String firstName;
    private String middleName;
    @NotNull
    @Size(min = 2, max = 20)
    private String lastName;
}
