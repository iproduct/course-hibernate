package course.hibernate.entity;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Account {
    @Id
    private long accountNumber;
    @Embedded
    private Name name;
    private double ballance = 0;
}
