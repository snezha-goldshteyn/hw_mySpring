package mySpring;

import javassist.tools.rmi.ObjectNotFoundException;
import lombok.SneakyThrows;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toMap;

public class JavaConfig implements Config {
    private String packageToScan;
    Map<Class, Class> map = new HashMap<>();

    public JavaConfig(String packageToScan) {
        this.packageToScan = packageToScan;
        initSingletons();
    }

    private void initSingletons() {
        Reflections scanner = new Reflections(this.packageToScan);
        Set<Class<?>> singletons = scanner.getTypesAnnotatedWith(Singleton.class);
        map = singletons.stream().filter(s -> !s.getAnnotation(Singleton.class).lazy())
                .map(this::getNewInstance).collect(toMap(this::getNativeClass, Object::getClass));
    }

    private <T> Class<?> getNativeClass(T t) {
        Class<?>[] interfaces = t.getClass().getInterfaces();
        if (interfaces.length == 0) {
            return t.getClass();
        } else {
            return interfaces[0];
        }
    }

    @SneakyThrows
    private Object getNewInstance(Class<?> s) {
        return s.getDeclaredConstructor().newInstance();
    }

    public String getPackageToScan() {
        return packageToScan;
    }

    @SneakyThrows
    @Override
    public <T> Class<T> getImplClass(Class<T> type) {
        if (map.containsKey(type)) {
            return (Class<T>) map.get(type);
        } else {
            throw new ObjectNotFoundException("Such class is not initialized");
        }
    }
}
