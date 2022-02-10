package course.hibernate.entity;

import lombok.*;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class AccountTransaction {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @NonNull
    private Account account;
    @NonNull
    private Integer cents;
    @NonNull
    private String description;
}
