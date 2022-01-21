package course.hibernate.spring.examples;

import javax.persistence.*;

@Entity(name = "Book")
@AttributeOverrides({
        @AttributeOverride(
                name = "ebookPublisher.name",
                column = @Column(name = "ebook_publisher_name")
        ),
        @AttributeOverride(
                name = "paperBackPublisher.name",
                column = @Column(name = "paper_back_publisher_name")
        )
})
@AssociationOverrides({
        @AssociationOverride(
                name = "ebookPublisher.country",
                joinColumns = @JoinColumn(name = "ebook_publisher_country_id")
        ),
        @AssociationOverride(
                name = "paperBackPublisher.country",
                joinColumns = @JoinColumn(name = "paper_back_publisher_country_id")
        )
})
public class Book2 {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String author;

    @Embedded
    private Publisher2 ebookPublisher;

    @Embedded
    private Publisher2 paperBackPublisher;

    public Publisher2 getPaperBackPublisher() {
        return paperBackPublisher;
    }

    public Publisher2 getEbookPublisher() {
        return ebookPublisher;
    }
}
