package course.hibernate.events;

import course.hibernate.entity.Contact;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class ContactCreationEvent {
        private final Contact contact;
}
