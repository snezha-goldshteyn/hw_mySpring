package mySpring;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class BenchmarkInvocationHandler implements InvocationHandler {
    private Object t;

    public <T> BenchmarkInvocationHandler(T t) {
        this.t = t;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long start = System.nanoTime();
        method.invoke(t, args);
        long end = System.nanoTime();
        System.out.println(end-start);
        return null;
    }
}
