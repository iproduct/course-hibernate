package course.hibernate.spring.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
public class BaseMappedSuperclass {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(
            strategy = GenerationType.TABLE,
            generator = "table-generator"
    )
    @TableGenerator(
            name =  "table-generator",
            table = "generated_values",
            pkColumnName = "entity_name",
            valueColumnName = "last_id",
            initialValue = 0,
            allocationSize = 5
    )
    private Long id;
    private LocalDateTime created = LocalDateTime.now();
    private LocalDateTime modified = LocalDateTime.now();

    public BaseMappedSuperclass() {
    }

    public BaseMappedSuperclass(Long id) {
        this.id = id;
    }

    public BaseMappedSuperclass(Long id, LocalDateTime created, LocalDateTime modified) {
        this.id = id;
        this.created = created;
        this.modified = modified;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public void setModified(LocalDateTime modified) {
        this.modified = modified;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseMappedSuperclass)) return false;

        BaseMappedSuperclass that = (BaseMappedSuperclass) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BaseMappedSuperclass{");
        sb.append("id=").append(id);
        sb.append(", created=").append(created);
        sb.append(", modified=").append(modified);
        sb.append('}');
        return sb.toString();
    }
}
