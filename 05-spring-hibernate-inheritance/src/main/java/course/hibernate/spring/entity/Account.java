package course.hibernate.spring.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import java.math.BigDecimal;

@MappedSuperclass
@Data
public class Account {
    private Long id;
    private String owner;
    private BigDecimal balance;
    private BigDecimal interestRate;
}
