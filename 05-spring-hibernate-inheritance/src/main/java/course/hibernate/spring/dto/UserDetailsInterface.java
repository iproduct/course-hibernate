package course.hibernate.spring.dto;

import course.hibernate.spring.entity.Role;

import java.util.Set;

import static course.hibernate.spring.entity.Role.READER;

public interface UserDetailsInterface {
    Long getId();
    String getFirstName();
    String getLastName();
    String getUsername();
    Set<Role> getRoles();
}
