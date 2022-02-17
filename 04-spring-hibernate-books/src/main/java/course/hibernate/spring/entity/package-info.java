@AnyMetaDef( name= "PropertyMetaDef", metaType = "char", idType = "long",
        metaValues = {
                @MetaValue(value = "S", targetEntity = StringProperty.class),
                @MetaValue(value = "I", targetEntity = IntProperty.class)
        }
)
package course.hibernate.spring.entity;

import course.hibernate.spring.entity.StringProperty;
import org.hibernate.annotations.AnyMetaDef;
import org.hibernate.annotations.MetaValue;
