package course.hibernate.spring.entity;

import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.hibernate.annotations.CacheConcurrencyStrategy.NONSTRICT_READ_WRITE;
import static org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE;

@Entity
@Table(name = "persons")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
//@Cacheable
//@org.hibernate.annotations.Cache(usage = READ_WRITE)
//@FilterDef(name = "youngAuthors")
//@FilterDef(name = "recentBooks", parameters = @ParamDef(name = "afterYear", type = "int"))
//@FilterJoinTable(name="youngAuthors", condition = "date_of_birth > 1973-01-01")
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

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = Set.of(Role.READER);

    @Version
    private Long version;

    @ManyToMany
    @ToString.Exclude
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
//    @Where(clause = "year > 2015")
//    @Filter(name="recentBooks", condition = "year > :afterYear")
//            aliases = {@SqlFragmentAlias(alias = "bk", table = "books")})
    private List<Book> books = new ArrayList<>();
}




















