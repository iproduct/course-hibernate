package course.hibernate.spring.util;

import course.hibernate.spring.dto.UserDetailsDto;
import course.hibernate.spring.entity.Role;
import course.hibernate.spring.entity.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserToUserDetailsDtoConverterFactory implements ConverterFactory<User, UserDetailsDto> {

    @Override
    public <T extends UserDetailsDto> Converter<User, T> getConverter(Class<T> targetType) {
        return (Converter<User, T>) new UserToUserDetailsDtoConverter();
    }

    private static class UserToUserDetailsDtoConverter implements Converter<User, UserDetailsDto> {

        public UserDetailsDto convert(User u) {
            return new UserDetailsDto(u.getId(), u. getFirstName(), u.getLastName(), u.getUsername(),
                    u.getRoles().stream().map(Role::toString).collect(Collectors.joining(", ")));
        }
    }
}
