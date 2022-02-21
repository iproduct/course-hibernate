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
//@DiscriminatorValue("D")
//@PrimaryKeyJoinColumn(name = "debit_account_id", foreignKey = @ForeignKey(name="fk_debit_account_id"))
public class DebitAccount extends Account {
    private BigDecimal overdraftFee;

    public DebitAccount() {
    }

    public DebitAccount(Long id, String owner, BigDecimal balance, BigDecimal interestRate, BigDecimal overdraftFee) {
        super(id, owner, balance, interestRate);
        this.overdraftFee = overdraftFee;
    }

    @Override
    public String toString() {
        return "DebitAccount{" +
                "id=" + getId() +
                ", owner='" + getOwner() + '\'' +
                ", balance=" + getBalance() +
                ", interestRate=" + getInterestRate() +
                ", overdraftFee=" + overdraftFee +
                '}';
    }
}
