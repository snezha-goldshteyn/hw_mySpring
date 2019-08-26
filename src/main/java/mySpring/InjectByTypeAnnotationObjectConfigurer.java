package mySpring;

import lombok.SneakyThrows;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.util.Set;

public class InjectByTypeAnnotationObjectConfigurer implements ObjectConfigurer {

    @SneakyThrows
    @Override
    public void configure(Object t) {
        Class<?> aClass = t.getClass();
        Set<Field> fields = ReflectionUtils.getAllFields(aClass,
                field -> field.isAnnotationPresent(InjectByType.class));
        for (Field field : fields) {
            Object value = ApplicationContext.getFactory().createObject(field.getType());
            field.setAccessible(true);
            field.set(t, value);
        }
    }
}
