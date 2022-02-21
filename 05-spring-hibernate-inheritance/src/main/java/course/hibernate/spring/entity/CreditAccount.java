package course.hibernate.spring.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.PrimaryKeyJoinColumn;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
//@DiscriminatorValue("C")
//@PrimaryKeyJoinColumn(name = "credit_account_id", foreignKey = @ForeignKey(name="fk_credit_account_id"))
public class CreditAccount extends Account {
    private BigDecimal creditLimit;

    public CreditAccount() {
    }

    public CreditAccount(Long id, String owner, BigDecimal balance, BigDecimal interestRate, BigDecimal creditLimit) {
        super(id, owner, balance, interestRate);
        this.creditLimit = creditLimit;
    }

    @Override
    public String toString() {
        return "CreditAccount{" +
                "id=" + getId() +
                ", owner='" + getOwner() + '\'' +
                ", balance=" + getBalance() +
                ", interestRate=" + getInterestRate() +
                ", credit limit=" + creditLimit +
                '}';
    }
}
