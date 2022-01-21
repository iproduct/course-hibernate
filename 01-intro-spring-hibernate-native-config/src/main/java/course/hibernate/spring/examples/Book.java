package course.hibernate.spring.examples;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "Book")
public class Book {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String author;

    @Embedded
    private Publisher2 publisher;

    public Publisher2 getPublisher() {
        return publisher;
    }
}

@Embeddable
@Data
class Publisher2 {

    @Column(name = "publisher_name")
    private String name;

    @Column(name = "publisher_country")
    private String country;
}
