package course.hibernate.spring.entity;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
public class DebitAccount extends Account{
    private BigDecimal overdraftFee;

    public DebitAccount() {
    }

    public DebitAccount(Long id, String owner, BigDecimal ballance, BigDecimal interestRate, BigDecimal overdraftFee) {
        super(id, owner, ballance, interestRate);
        this.overdraftFee = overdraftFee;
    }

    public BigDecimal getOverdraftFee() {
        return overdraftFee;
    }

    public void setOverdraftFee(BigDecimal overdraftFee) {
        this.overdraftFee = overdraftFee;
    }

    @Override
    public String toString() {
        return "DebitAccount{" +
                "id=" + getId() +
                ", owner='" + getOwner() + '\'' +
                ", ballance=" + getBallance() +
                ", interestRate=" + getInterestRate() +
                ", overdraftFee=" + overdraftFee +
                '}';
    }
}
