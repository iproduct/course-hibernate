package generated.entity;

import javax.persistence.*;

//@Entity
//@Table(name = "acct", schema = "hibernate_native2", catalog = "")
public class Account {
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Id
//    @Column(name = "acct_num", nullable = false)
    private Long accountNumber;
//    @Basic
//    @Column(name = "ballance", nullable = false, precision = 0)
    private Double ballance;
//    @Basic
//    @Column(name = "first_name", nullable = true, length = 255)
    private String firstName;
//    @Basic
//    @Column(name = "last_name", nullable = true, length = 255)
    private String lastName;
//    @Basic
//    @Column(name = "middle_name", nullable = true, length = 255)
    private String middleName;


    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Double getBallance() {
        return ballance;
    }

    public void setBallance(Double ballance) {
        this.ballance = ballance;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
}
