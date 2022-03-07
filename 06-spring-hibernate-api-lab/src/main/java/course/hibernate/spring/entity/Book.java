package course.hibernate.spring.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyGroup;
import org.hibernate.annotations.NaturalId;
import org.springframework.cache.annotation.Cacheable;

import javax.persistence.*;
import javax.persistence.Basic;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import static javax.persistence.FetchType.LAZY;
import static org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Cacheable
@org.hibernate.annotations.Cache(usage = READ_WRITE, region = "course.hibernate.spring.entity.Book",
include="all")
public class Book {
    @Id
    private Long id;
    private String title;
    @ManyToOne
    private Person author;

    @NaturalId
    private String isbn;

    @Basic(fetch=LAZY)
    private String summary;

    @Lob
    @Basic(fetch=LAZY)
    @LazyGroup("lobs")
    private byte[] image;

    @Lob
    @Basic(fetch=LAZY)
    @LazyGroup("lobs")
    private String sampleContent;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;

        Book book = (Book) o;

        return id != null ? id.equals(book.id) : book.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
