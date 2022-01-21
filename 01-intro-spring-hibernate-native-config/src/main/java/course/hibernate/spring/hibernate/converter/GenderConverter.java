package course.hibernate.spring.hibernate.converter;

import course.hibernate.spring.entity.Gender;
import javax.persistence.Converter;
import javax.persistence.AttributeConverter;

@Converter(autoApply = true)
public class GenderConverter implements AttributeConverter<Gender, Character> {

    public Character convertToDatabaseColumn( Gender value ) {
        if ( value == null ) {
            return null;
        }

        return value.getCode();
    }

    public Gender convertToEntityAttribute( Character value ) {
        if ( value == null ) {
            return null;
        }

        return Gender.fromCode( value );
    }
}
