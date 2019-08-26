package mySpring;

import lombok.SneakyThrows;
import org.reflections.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Set;

public class InjectRandomValueAnnotationConfigurer implements ObjectConfigurer {
    @SneakyThrows
    public void configure(Object t) {
        Class<?> type = t.getClass();
        Set<Field> fields = ReflectionUtils.getAllFields(type, field -> field.isAnnotationPresent(InjectRandomInt.class));
        for (Field field : fields) {
            InjectRandomInt inject = field.getAnnotation(InjectRandomInt.class);
            int min = inject.min();
            int max = inject.max();
            int value = RandomUtil.getRandomValue(min, max);
            field.setAccessible(true);
            field.set(t, value);
        }
    }
}
