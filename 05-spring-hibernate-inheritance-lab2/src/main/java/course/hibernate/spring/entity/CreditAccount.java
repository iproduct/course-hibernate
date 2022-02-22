package course.hibernate.spring.entity;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
public class CreditAccount extends Account{
    private BigDecimal creditLimit;

    public CreditAccount() {
    }

    public CreditAccount(Long id, String owner, BigDecimal ballance, BigDecimal interestRate, BigDecimal creditLimit) {
        super(id, owner, ballance, interestRate);
        this.creditLimit = creditLimit;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    @Override
    public String toString() {
        return "DebitAccount{" +
                "id=" + getId() +
                ", owner='" + getOwner() + '\'' +
                ", ballance=" + getBallance() +
                ", interestRate=" + getInterestRate() +
                ", overdraftFee=" + creditLimit +
                '}';
    }
}
