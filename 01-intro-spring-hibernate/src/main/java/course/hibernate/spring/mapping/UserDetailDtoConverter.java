package course.hibernate.spring.mapping;

import com.github.jmnarloch.spring.boot.modelmapper.ConverterConfigurerSupport;
import course.hibernate.spring.dto.UserDetailsDto;
import course.hibernate.spring.entity.User;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.springframework.beans.BeanUtils;

//@Component
public class UserDetailDtoConverter extends ConverterConfigurerSupport<User, UserDetailsDto> {

    @Override
    protected Converter<User, UserDetailsDto> converter() {
        return new AbstractConverter<User, UserDetailsDto>() {

            @Override
            protected UserDetailsDto convert(User source) {
                UserDetailsDto result = new UserDetailsDto();
                BeanUtils.copyProperties(source, result);
//                UserDetailDto author = new UserDetailDto();
//                BeanUtils.copyProperties(source.getAuthor(), author);
//                result.setAuthor(author);
//                result.setRoles(source.getRoles());
                return result;
            }
        };
    }
}
