package course.hibernate.spring.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemUserEmbeddedId {
    @EmbeddedId
    private EmbeddedPK id;
    private String name;
}
