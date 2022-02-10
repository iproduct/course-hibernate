package course.hibernate.spring.mapping;

import com.github.jmnarloch.spring.boot.modelmapper.PropertyMapConfigurerSupport;
import course.hibernate.spring.dto.UserDetailsDto;
import course.hibernate.spring.entity.User;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

//@Component
public class UserDetailDtoMapping extends PropertyMapConfigurerSupport<User, UserDetailsDto> {

    @Override
    public PropertyMap<User, UserDetailsDto> mapping() {

        return new PropertyMap<User, UserDetailsDto>() {
            @Override
            protected void configure() {
//                Set<String> titles = new LinkedHashSet<String>();
//                for (Category c : source.getCategories()) {
//                    titles.add(c.getTitle());
//                }
//                map().setCategoryTitles(titles);
            }
        };
    }
}
