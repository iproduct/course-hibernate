package generated.entity;

import javax.persistence.*;
import java.sql.Timestamp;

//@Entity
//@Table(name = "articles", schema = "hibernate_native2")
public class Article {
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Id
//    @Column(name = "id", nullable = false)
    private Long id;
//    @Basic
//    @Column(name = "content", nullable = true, length = 2048)
    private String content;
//    @Basic
//    @Column(name = "created", nullable = true)
    private Timestamp created;
//    @Basic
//    @Column(name = "picture_url", nullable = true, length = 256)
    private String pictureUrl;
//    @Basic
//    @Column(name = "title", nullable = true, length = 80)
    private String title;
//    @Basic
//    @Column(name = "updated", nullable = true)
    private Timestamp updated;
//    @ManyToOne
//    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private User author;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Timestamp getUpdated() {
        return updated;
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
