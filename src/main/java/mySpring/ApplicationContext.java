package mySpring;

import java.util.HashMap;
import java.util.Map;

public class ApplicationContext {
    private final Config configuration;
    private static ObjectFactory factory;

    public ApplicationContext(Config configuration) {
        this.configuration = configuration;
        factory = new ObjectFactory(configuration);
    }

    public Object getBean(Class<?> type) {
        return factory.createObject(type);
    }

    public static ObjectFactory getFactory() {
        return factory;
    }
}
