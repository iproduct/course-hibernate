package course.hibernate.spring.examples;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity(name = "Library")
public class Library {

    @Id
    private Long id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "book_id")
    private Set<Book3> books = new HashSet<>();

    //Getters and setters are omitted for brevity
}

@Entity(name = "Book3")
public class Book3 {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String author;

    @NaturalId
    private String isbn;

    //Getters and setters are omitted for brevity

    @Override
    public boolean equals(Object o) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }
        Book3 book = (Book3) o;
        return Objects.equals( isbn, book.isbn );
    }

    @Override
    public int hashCode() {
        return Objects.hash( isbn );
    }
}
