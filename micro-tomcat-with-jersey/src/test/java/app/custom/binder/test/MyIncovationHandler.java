package app.custom.binder.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyIncovationHandler implements InvocationHandler {

    @Override
    public Object invoke(Object obj, Method method, Object[] args)
            throws Throwable {
       System.out.println("Captured!");
        Object result = method.invoke(obj, args);
        return result;
    }
}
