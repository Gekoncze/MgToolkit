package cz.mg.toolkit.graphics.designer;

import cz.mg.toolkit.component.Component;
import java.lang.reflect.Method;


public class StructuredSetter {
    private final Method method;
    private final Object[] values;

    public StructuredSetter(Method method, Object... values) {
        if(method.getParameterCount() != values.length) throw new RuntimeException("Wrong number of values for method. Expected " + method.getParameterCount() + ", but got " + values.length + ".");
        this.method = method;
        this.values = values;
    }

    public void set(Component component){
        try {
            values[0] = component;
            method.invoke(null, values);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Could not invoke design setter: " + e.getClass().getSimpleName() + ": " + e.getMessage());
        } finally {
            values[0] = null;
        }
    }

    public Method getMethod() {
        return method;
    }
}
