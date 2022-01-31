package course.hibernate.entity;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
public class Account {
    @Id
    @NotNull
    @Positive
    private long accountNumber;
    @Embedded
    @Valid
    private Name name;
    private double ballance = 0;
}
