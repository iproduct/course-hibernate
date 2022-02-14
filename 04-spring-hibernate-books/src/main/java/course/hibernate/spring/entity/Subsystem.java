package course.hibernate.spring.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Subsystem {
    @Id
    @EqualsAndHashCode.Include
    @NonNull
    private String id;
    @NonNull
    private String description;
    @OneToMany(mappedBy = "subsystem", fetch = FetchType.EAGER)
    private List<SystemUser> systemUsers = new ArrayList<>();

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Subsystem{");
        sb.append("id='").append(id).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", systemUsers=")
                .append(systemUsers.stream().map(SystemUser::getName).collect(Collectors.joining(", ")));
        sb.append('}');
        return sb.toString();
    }
}
