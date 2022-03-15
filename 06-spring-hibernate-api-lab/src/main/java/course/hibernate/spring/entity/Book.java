package course.hibernate.spring.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.*;
//import org.springframework.cache.annotation.Cacheable;

import javax.persistence.*;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import java.util.List;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;
import static org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
//@Cacheable
//@org.hibernate.annotations.Cache(usage = READ_WRITE, region = "course.hibernate.spring.entity.Book",
//include="all")
//@FilterDef(name = "recentBooks")
//@Filter(name="recentBooks", condition = "year > 2015")
public class Book {
    @Id
    private Long id;
    private String title;

    @ManyToMany(mappedBy = "books", fetch = EAGER)
    private List<Person> author;

    @NaturalId
    private String isbn;

    private int year;

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

    @Version
    private Long version;

    public Book(Long id, String title, List<Person> author, String isbn, int year, String summary, byte[] image, String sampleContent) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.year = year;
        this.summary = summary;
        this.image = image;
        this.sampleContent = sampleContent;
    }

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
