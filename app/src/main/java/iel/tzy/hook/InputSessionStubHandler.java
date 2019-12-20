package iel.tzy.hook;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import iel.tzy.watcher.Toggles;

public class InputSessionStubHandler implements InvocationHandler {

    private final Object mOriginObject;

    public InputSessionStubHandler(Object origin) {
        mOriginObject = origin;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equals("updateSelection")) {
            if (Toggles.isEnable("updateSelection")) {
                return method.invoke(mOriginObject, args);
            } else {
                return null;
            }
        } else {
            return method.invoke(mOriginObject, args);
        }
    }
}
