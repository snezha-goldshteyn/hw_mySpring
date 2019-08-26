package mySpring;

import lombok.SneakyThrows;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ObjectFactory {
    private List<ObjectConfigurer> configurators = new ArrayList<>();
    private Config config;

    @SneakyThrows
    public ObjectFactory(Config config) {
        this.config = config;
        Reflections scanner = new Reflections(config.getPackageToScan());
        Set<Class<? extends ObjectConfigurer>> classes = scanner.getSubTypesOf(ObjectConfigurer.class);
        for (Class<? extends ObjectConfigurer> aClass : classes) {
            configurators.add(aClass.getDeclaredConstructor().newInstance());
        }
    }

    @SneakyThrows
    public <T> T createObject(Class<T> type) {
        type = resolveImpl(type);
        T t = create(type);
        configure(t);
        t = (T) addProxy(t);
        invokeInit(t);
        return t;
    }

    private <T> Object addProxy(T t) {
        Class<?> type = t.getClass();
        Benchmark annotation = type.getAnnotation(Benchmark.class);
        if (annotation != null) {
           return Proxy.newProxyInstance(type.getClassLoader(),
                    type.getInterfaces(),
                    new BenchmarkInvocationHandler(t));
        }
        return t;
    }

    @SneakyThrows
    private <T> void invokeInit(T t) {
        Class<?> type = t.getClass();
        Method[] initMethods = type.getDeclaredMethods();
        List<Method> methods = Stream.of(initMethods)
                .filter(method -> method.isAnnotationPresent(PostConstruct.class))
                .collect(Collectors.toList());
        for (Method method : methods) {
            method.invoke(t);
        }
    }

    private <T> void configure(T t) {
        configurators.forEach(configurator -> configurator.configure(t));

    }

    private <T> Class<T> resolveImpl(Class<T> type) {
        if (type.isInterface()) {
            type = config.getImplClass(type);
        }
        return type;
    }

    private <T> T create(Class<T> type) throws InstantiationException, IllegalAccessException, java.lang.reflect.InvocationTargetException, NoSuchMethodException {
        return type.getDeclaredConstructor().newInstance();
    }

}
