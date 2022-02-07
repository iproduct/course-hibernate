package course.hibernate.spring.examples.code;

import course.hibernate.spring.examples.Book;
import org.apache.tomcat.jni.Library;

import javax.persistence.EntityManager;
import java.util.function.Function;

public class Examples {
    public static void main(String[] args) {

        Book book1 = new Book();
        book1.setTitle("High-Performance Java Persistence");

        Book book2 = new Book();
        book2.setTitle("Java Persistence with Hibernate");

        Library library = doInJPA(entityManager -> {
            Library _library = entityManager.find(Library.class, 1L);

            entityManager.persist(book1);
            entityManager.persist(book2);
            entityManager.flush();

            _library.getBooks().add(book1);
            _library.getBooks().add(book2);

            return _library;
        });

        assertTrue(library.getBooks().contains(book1));
        assertTrue(library.getBooks().contains(book2));
    }

    public static Object doInJPA(Function<EntityManager, Object> f){
        return null;
    }
}


