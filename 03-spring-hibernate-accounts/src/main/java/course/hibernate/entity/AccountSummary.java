package course.hibernate.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Synchronize;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Subselect("select " +
        "	a.id as id, " +
        "	concat(concat(c.first_name, ' '), c.last_name) as client_name, " +
        "	sum(atr.cents) as balance " +
        "from account a " +
        "join client c on c.id = a.client_id " +
        "join account_transaction atr on a.id = atr.account_id " +
        "group by a.id, concat(concat(c.first_name, ' '), c.last_name)"
)
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Synchronize( {"client", "account", "account_transaction"} )
public class AccountSummary {
    @Id
    @EqualsAndHashCode.Include
    private Long id;

    private String clientName;

    private int balance;
}
