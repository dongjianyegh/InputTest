package iel.tzy.hook;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

public class HookUpdateSelection {

    private static boolean allowUpdateSlection = true;

    public static void hookUpdateSelection(Context context, TextView textView) {

        Object inputManagerInstance = null;

        // 获取InputMethodManager
        try {
            inputManagerInstance = ReflectUtil.getStaticFiled(InputMethodManager.class, "sInstance");
        } catch (Throwable e) {
            return;
        }

        // 获取mCurMethod

        Object curMethod = null;

        try {
            curMethod = ReflectUtil.getFiled(InputMethodManager.class, "mCurMethod", inputManagerInstance);
        } catch (Throwable e) {
            return;
        }


        ClassLoader classLoader = context.getClassLoader();
        Object proxyInputMethodInterface = ReflectUtil.makeProxy(classLoader, curMethod.getClass(), new InputSessionStubHandler(curMethod));

        // 获取

        try {
            ReflectUtil.setFiled(InputMethodManager.class, "mCurMethod", inputManagerInstance, proxyInputMethodInterface);
        } catch (Throwable e) {
            return;
        }

    }
}
