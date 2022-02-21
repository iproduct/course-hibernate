package course.hibernate.spring.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * An example PhysicalNamingStrategy that implements database object naming standards
 * for our fictitious company Acme Corp.
 * <p/>
 * In general Acme Corp prefers underscore-delimited words rather than camel casing.
 * <p/>
 * Additionally standards call for the replacement of certain words with abbreviations.
 *
 * @author Steve Ebersole
 * @author Nathan Xu
 */
@Component
@Slf4j
public class MyOrgPhysicalNamingStrategy extends PhysicalNamingStrategyStandardImpl {
    private static final Map<String, String> ABBREVIATIONS;

    static {
        ABBREVIATIONS = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        ABBREVIATIONS.put("first", "f");
        ABBREVIATIONS.put("last", "l");
    }

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        final List<String> parts = splitAndReplace(name.getText());
        return jdbcEnvironment.getIdentifierHelper().toIdentifier(
                StringUtils.join(parts, '_'),
                name.isQuoted()
        );
    }

    @Override
    public Identifier toPhysicalSequenceName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        final List<String> parts = splitAndReplace(name.getText());
        // Acme Corp says all sequences should end with _seq
        if (!"seq".equals(parts.get(parts.size() - 1))) {
            parts.add("seq");
        }
        return jdbcEnvironment.getIdentifierHelper().toIdentifier(
                StringUtils.join(parts, '_'),
                name.isQuoted()
        );
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        final List<String> parts = splitAndReplace(name.getText());
        return jdbcEnvironment.getIdentifierHelper().toIdentifier(
                StringUtils.join(parts, '_'),
                name.isQuoted()
        );
    }

    private List<String> splitAndReplace(String name) {
        return Arrays.stream(StringUtils.splitByCharacterTypeCamelCase(name))
                .filter(StringUtils::isNotBlank)
                .filter(str -> !str.equals("_"))
                .map(p -> ABBREVIATIONS.getOrDefault(p, p).toLowerCase(Locale.ROOT))
                .collect(Collectors.toList());
    }
}
