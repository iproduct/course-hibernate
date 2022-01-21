package course.hibernate.spring.util;

import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.spi.MetadataBuilderContributor;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Component;

@Component
public class SqlFunctionsMetadataBuilderContributor implements MetadataBuilderContributor {

    @Override
    public void contribute(MetadataBuilder metadataBuilder) {
        metadataBuilder.applySqlFunction("config_json_extract",
                new StandardSQLFunction("json_extract", StandardBasicTypes.STRING));
        metadataBuilder.applySqlFunction("JSON_UNQUOTE",
                new StandardSQLFunction("JSON_UNQUOTE", StandardBasicTypes.STRING));
        metadataBuilder.applySqlFunction("group_concat",
                new StandardSQLFunction("group_concat", StandardBasicTypes.STRING));
        metadataBuilder.applySqlFunction("group_concat_role_set",
                new StandardSQLFunction("group_concat", StandardBasicTypes.STRING));

    }

}
