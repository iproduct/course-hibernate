package course.hibernate.spring.entity;

import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "persons")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Person {
    @Id
    @NonNull
    private Long id;
    @NotNull
    @Size(min = 2, max = 20)
    @NonNull
    private String firstName;
    @NotNull
    @Size(min = 2, max = 20)
    @NonNull
    private String lastName;
    @NonNull
    @Past
    LocalDate dateOfBirth;

    @OneToMany(mappedBy = "author")
    @ToString.Exclude
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @Where(clause = "year > 2015")
    private List<Book> books = new ArrayList<>();
}

