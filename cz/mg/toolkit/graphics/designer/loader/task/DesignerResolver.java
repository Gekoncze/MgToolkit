package cz.mg.toolkit.graphics.designer.loader.task;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.parser.utilities.Substring;
import cz.mg.toolkit.graphics.Color;
import cz.mg.toolkit.graphics.Decoration;
import cz.mg.toolkit.graphics.Font;
import cz.mg.toolkit.graphics.designer.StructuredDesign;
import cz.mg.toolkit.graphics.designer.StructuredDesigner;
import cz.mg.toolkit.graphics.designer.StructuredSetter;
import cz.mg.toolkit.graphics.designer.loader.entity.*;
import cz.mg.toolkit.graphics.designer.loader.utilities.ResolverException;
import cz.mg.toolkit.utilities.annotations.ComponentDecoration;
import cz.mg.toolkit.utilities.annotations.ComponentDecorations;
import cz.mg.toolkit.utilities.annotations.ComponentProperties;
import cz.mg.toolkit.utilities.annotations.ComponentProperty;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;


public class DesignerResolver {
    public StructuredDesigner resolve(DesignerRoot designerRoot){
        StructuredDesigner structuredDesigner = new StructuredDesigner();
        ChainList<Method> properties = getUsingPropertiesMethods(getUsingPropertiesClasses(designerRoot));
        ChainList<Field> decorations = getUsingDecorationsFields(getUsingDecorationsClasses(designerRoot));

        for(Object childDefine : designerRoot.getChildren()){
            if(childDefine instanceof DefineDesign){
                StructuredDesign structuredDesign = resolveDesign((DefineDesign) childDefine);
                structuredDesigner.getDesigns().addLast(structuredDesign);
            }
        }

        todo - resolve based on inheritance!!!; will need lock map on names to prevent cycles!!!;

        return structuredDesigner;
    }

    private StructuredDesign resolveDesign(DefineDesign define){
        StructuredDesign structuredDesign = new StructuredDesign(define.getName().toString());
        for(Object childSetter : define.getChildren()){
            if(childSetter instanceof Setter){
                Setter setter = (Setter) childSetter;
                StructuredSetter structuredSetter = resolveSetter(setter);
                structuredDesign.getSetters().addLast(structuredSetter);
            }
        }
    }

    private StructuredSetter resolveSetter(Setter setter, ChainList<Method> availableMethods){
        String methodName = getMethodName(setter.getName());
        ChainList<Method> methods = new ChainList<>();
        for(Method method : availableMethods){
            if(Modifier.isStatic(method.getModifiers())){
                if(method.isAnnotationPresent(ComponentProperty.class)){
                    if(method.getParameterCount() == setter.getValues().count()){
                        if(method.getName().equals(methodName)){
                            methods.addLast(method);
                        }
                    }
                }
            }
        }
        if(methods.count() <= 0) throw new ResolverException("Parameter " + setter.getName().toString() + " was not found.");
        if(methods.count() > 1) throw new ResolverException("Parameter " + setter.getName().toString() + " is ambiguous. (" + methods.count() + " possibilities)");
        Method method = methods.getFirst();
        Object[] values = resolveSetterValues(setter, method);
        return new StructuredSetter(values, method);
    }

    private Object[] resolveSetterValues(Setter setter, Method method){
        Object[] values = new Object[method.getParameterCount()];
        int i = 0;
        for(Value value : setter.getValues()){
            Class c = method.getParameterTypes()[i];
            if(value.isLiteral()){
                values[i] = resolveLiteralValue(c, value.getValue());
            } else {
                values[i] = resolveNamedValue(c, value.getValue());
            }
            i++;
        }
    }

    private Object resolveLiteralValue(Class c, Substring value){
        if(c == int.class || c == Integer.class) return parseInteger(value);
        if(c == long.class || c == Long.class) return parseLong(value);
        if(c == float.class || c == Float.class) return parseFloat(value);
        if(c == double.class || c == Double.class) return parseDouble(value);
        if(c == boolean.class || c == Boolean.class) return parseBoolean(value);
        if(c == Color.class) return parseColor(value);
        if(c == Font.class) return parseFont(value);
        if(c == Decoration.class) return parseDecoration(value);
        throw new RuntimeException("Unsupported type " + c.getClass().getSimpleName() + ".");
    }

    private Object resolveNamedValue(Class c, Substring name){
        todo;
    }

    private Object parseInteger(Substring value){
        return Integer.parseInt(value.toString());
    }

    private Object parseLong(Substring value){
        return Long.parseLong(value.toString());
    }

    private Object parseFloat(Substring value){
        return Float.parseFloat(value.toString());
    }

    private Object parseDouble(Substring value){
        return Double.parseDouble(value.toString());
    }

    private Object parseBoolean(Substring value){
        return Boolean.parseBoolean(value.toString());
    }

    private Object parseColor(Substring value){
        todo;
    }

    private Object parseFont(Substring value){
        todo;
    }

    private Object parseDecoration(Substring value){
        todo;
    }

    private String getMethodName(Substring name){
        if(name.count() <= 0) return "";
        StringBuilder methodName = new StringBuilder();
        methodName.append(Character.toLowerCase(name.get(0)));
        for(int i = 1; i < name.count(); i++){
            char ch = name.get(i);
            char pch = name.get(i-1);
            if(ch == ' ') continue;
            if(pch == ' ') ch = Character.toUpperCase(ch);
            else ch = Character.toLowerCase(ch);
            methodName.append(ch);
        }
        return methodName.toString();
    }

    private ChainList<Class> getUsingPropertiesClasses(DesignerRoot designerRoot){
        ChainList<Class> classes = new ChainList<>();
        for(Object child : designerRoot){
            if(child instanceof UsingInterface){
                String classPath = ((UsingInterface) child).getClassPath().toString();
                try {
                    Class c = Class.forName(classPath);
                    if(c.isAnnotationPresent(ComponentProperties.class)){
                        classes.addLast(c);
                    } else {
                        throw new ResolverException("Missing ComponentProperties annotation on class " + classPath + ".");
                    }
                } catch(ReflectiveOperationException e){
                    throw new ResolverException("Could not load class " + classPath + ": " + e.getClass().getSimpleName() + ": " + e.getMessage());
                }
            }
        }
        return classes;
    }

    private ChainList<Class> getUsingDecorationsClasses(DesignerRoot designerRoot){
        ChainList<Class> classes = new ChainList<>();
        for(Object child : designerRoot){
            if(child instanceof UsingDecorations){
                String classPath = ((UsingDecorations) child).getClassPath().toString();
                try {
                    Class c = Class.forName(classPath);
                    if(c.isAnnotationPresent(ComponentDecorations.class)){
                        classes.addLast(c);
                    } else {
                        throw new ResolverException("Missing ComponentDecorations annotation on class " + classPath + ".");
                    }
                } catch(ReflectiveOperationException e){
                    throw new ResolverException("Could not load class " + classPath + ": " + e.getClass().getSimpleName() + ": " + e.getMessage());
                }
            }
        }
        return classes;
    }

    private ChainList<Method> getUsingPropertiesMethods(ChainList<Class> classes){
        ChainList<Method> methods = new ChainList<>();
        for(Class c : classes){
            for(Method method : c.getMethods()){
                if(method.isAnnotationPresent(ComponentProperty.class)){
                    methods.addLast(method);
                }
            }
        }
        return methods;
    }

    private ChainList<Field> getUsingDecorationsFields(ChainList<Class> classes){
        ChainList<Field> fields = new ChainList<>();
        for(Class c : classes){
            for(Field field : c.getFields()){
                if(field.isAnnotationPresent(ComponentDecoration.class)){
                    fields.addLast(field);
                }
            }
        }
        return fields;
    }
}


//    private void findParentDesign(){
//        parentDesign = designer.getDesign(parentDesignName);
//        if(parentDesign == null) throw new RuntimeException("Could not find parent designer " + parentDesignName + " for " + name + ".");
//        checkCycle();
//    }
//
//    private void checkCycle(){
//        CompositeDesign currentDesign = parentDesign;
//        while(currentDesign != null) {
//            if(currentDesign == this) throw new RuntimeException("Cycle detected in designer " + name + ": " + getCyclePath());
//            currentDesign = currentDesign.parentDesign;
//        }
//    }
//
//    private String getCyclePath(){
//        ChainList<String> path = new ChainList<>();
//        path.addLast(name);
//        CompositeDesign currentDesign = parentDesign;
//        while(currentDesign != null){
//            path.addLast(currentDesign.name);
//            if(currentDesign == this) return path.toString(" -> ");
//            currentDesign = currentDesign.parentDesign;
//        }
//        return "<error>";
//    }