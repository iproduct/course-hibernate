package course.hibernate.utils;

import javax.persistence.Entity;
import javax.tools.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class JpaAnnotatedClassUtil {
    public static Collection<Class> getEntityClasses(final String pack) {
        final StandardJavaFileManager fileManager = ToolProvider.getSystemJavaCompiler().getStandardFileManager(null, null, null);
        try {
            return StreamSupport.stream(fileManager.list(StandardLocation.CLASS_PATH, pack, Collections.singleton(JavaFileObject.Kind.CLASS), false).spliterator(), false)
                    .map(FileObject::getName)
                    .map(name -> {
                        try {
                            final String[] split = name
                                    .replace(".class", "")
                                    .replace(")", "")
                                    .split(Pattern.quote(File.separator));

                            final String fullClassName = pack + "." + split[split.length - 1];
                            return Class.forName(fullClassName);
                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }

                    })
                    .filter(aClass -> aClass.isAnnotationPresent(Entity.class))
                    .collect(Collectors.toCollection(ArrayList::new));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
