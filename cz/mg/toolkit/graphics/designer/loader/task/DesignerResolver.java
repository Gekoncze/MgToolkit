package cz.mg.toolkit.graphics.designer.loader.task;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.collections.list.chainlist.ChainListItem;
import cz.mg.parser.utilities.Substring;
import cz.mg.toolkit.graphics.Color;
import cz.mg.toolkit.graphics.Decoration;
import cz.mg.toolkit.graphics.Font;
import cz.mg.toolkit.graphics.designer.Design;
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
    private DesignerRoot designerRoot;
    private ChainList<Method> properties;
    private ChainList<Field> decorations;
    private boolean[] locks;
    private boolean[] resolved;

    public StructuredDesigner resolve(DesignerRoot designerRoot){
        try {
            StructuredDesigner structuredDesigner = new StructuredDesigner();
            this.designerRoot = designerRoot;
            this.properties = getUsingPropertiesMethods(getUsingPropertiesClasses(designerRoot));
            this.decorations = getUsingDecorationsFields(getUsingDecorationsClasses(designerRoot));

            for(Object childDefine : designerRoot.getChildren()){
                if(childDefine instanceof DefineDesign){
                    StructuredDesign structuredDesign = resolveDesign((DefineDesign) childDefine);
                    structuredDesigner.getDesigns().addLast(structuredDesign);
                }
            }

            this.locks = new boolean[structuredDesigner.getDesigns().count()];
            this.resolved = new boolean[structuredDesigner.getDesigns().count()];
            int i = 0;
            for(Design design : structuredDesigner.getDesigns()){
                StructuredDesign structuredDesign = (StructuredDesign) design;
                resolveDesignInheritance(i, structuredDesign, structuredDesigner);
                i++;
            }

            return structuredDesigner;
        } finally {
            this.properties = null;
            this.decorations = null;
            this.designerRoot = null;
            this.locks = null;
            this.resolved = null;
        }
    }

    private StructuredDesign resolveDesign(DefineDesign define){
        StructuredDesign structuredDesign = new StructuredDesign(define.getName().toString(), define.getParentName() != null ? define.getParentName().toString() : null);
        for(Object childSetter : define.getChildren()){
            if(childSetter instanceof Setter){
                Setter setter = (Setter) childSetter;
                StructuredSetter structuredSetter = resolveSetter(setter);
                structuredDesign.getSetters().addLast(structuredSetter);
            }
        }
        return structuredDesign;
    }

    private StructuredSetter resolveSetter(Setter setter){
        String methodName = getMethodName(setter.getName());
        ChainList<Method> candidates = new ChainList<>();
        for(Method property : properties){
            if(property.getParameterCount() == setter.getValues().count()){
                if(property.getName().equals(methodName)){
                    candidates.addLast(property);
                }
            }
        }
        if(candidates.count() <= 0) throw new ResolverException("Parameter " + setter.getName().toString() + " was not found.");
        if(candidates.count() > 1) throw new ResolverException("Parameter " + setter.getName().toString() + " is ambiguous. (" + candidates.count() + " candidates)");
        Method method = candidates.getFirst();
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
        return values;
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
        if(c == Decoration.class){
            String fieldName = name.toString().toUpperCase().replace(" ", "_");
            ChainList<Object> candidates = new ChainList<>();
            for(Field decoration : decorations){
                if(decoration.getName().equals(fieldName)){
                    try {
                        candidates.addLast(decoration.get(null));
                    } catch(ReflectiveOperationException e){
                        throw new RuntimeException("Could not read field: " + e.getClass().getSimpleName() + ": " + e.getMessage());
                    }
                }
            }
            if(candidates.count() <= 0) throw new ResolverException("Undefined constant " + name.toString() + ".");
            if(candidates.count() > 1) throw new ResolverException("Constant " + name.toString() + " is ambiguous. (" + candidates.count() + " candidates)");
            return candidates.getFirst();
        } else {
            ChainList<Substring> candidates = new ChainList<>();
            for(Object child : designerRoot.getChildren()){
                if(child instanceof DefineConstant){
                    DefineConstant constant = (DefineConstant) child;
                    if(constant.getName().equals(name)){
                        candidates.addLast(constant.getValue());
                    }
                }
            }
            if(candidates.count() <= 0) throw new ResolverException("Undefined constant " + name.toString() + ".");
            if(candidates.count() > 1) throw new ResolverException("Constant " + name.toString() + " is ambiguous. (" + candidates.count() + " candidates)");
            return resolveLiteralValue(c, candidates.getFirst());
        }
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
        String v = value.toString();
        if(value.count() < 0) throw new ResolverException("Missing value.");
        if(value.get(0) != '#') throw new ResolverException("Missing # for color definition.");
        if(value.count() == 7){
            return new Color(
                    Integer.parseInt(v.substring(1,1+2), 16),
                    Integer.parseInt(v.substring(3,3+2), 16),
                    Integer.parseInt(v.substring(5,5+2), 16),
                    255
            );
        }
        if(value.count() == 9){
            return new Color(
                    Integer.parseInt(v.substring(1,1+2), 16),
                    Integer.parseInt(v.substring(3,3+2), 16),
                    Integer.parseInt(v.substring(5,5+2), 16),
                    Integer.parseInt(v.substring(7,7+2), 16)
            );
        }
        throw new ResolverException("Invalid color code length. Expected 6 or 8, but got " + (value.count() - 1) + ".");
    }

    private Object parseFont(Substring value){
        String[] values = value.toString().split(",");
        if(values.length != 3) throw new ResolverException("Invalid number of parameters for font. Expected 3 (name, size, style), but got " + values.length);
        return new Font(values[0], Integer.parseInt(values[1]), Font.Style.valueOf(values[2].toUpperCase()));
    }

    private Object parseDecoration(Substring value){
        throw new ResolverException("Decoration cannot be literal value.");
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
                if(Modifier.isStatic(method.getModifiers())) {
                    if(method.isAnnotationPresent(ComponentProperty.class)){
                        methods.addLast(method);
                    }
                }
            }
        }
        return methods;
    }

    private ChainList<Field> getUsingDecorationsFields(ChainList<Class> classes){
        ChainList<Field> fields = new ChainList<>();
        for(Class c : classes){
            for(Field field : c.getFields()){
                if(Modifier.isStatic(field.getModifiers())) {
                    if(field.isAnnotationPresent(ComponentDecoration.class)){
                        fields.addLast(field);
                    }
                }
            }
        }
        return fields;
    }

    private void resolveDesignInheritance(int i, StructuredDesign structuredDesign, StructuredDesigner structuredDesigner){
        if(locks[i]) throw new ResolverException("Cyclic inheritance detected at design " + structuredDesign.getName());
        locks[i] = true;
        if(!resolved[i] && structuredDesign.getParentName() != null){
            String name = structuredDesign.getParentName();
            int ii = 0;
            ChainList<StructuredDesign> candidates = new ChainList<>();
            int parentId = -1;
            for(Design d : structuredDesigner.getDesigns()){
                StructuredDesign candidate = (StructuredDesign) d;
                if(candidate.getName().equals(name)){
                    candidates.addLast(candidate);
                    parentId = ii;
                }
                ii++;
            }
            if(candidates.count() <= 0) throw new ResolverException("Unknown design " + name.toString() + ".");
            if(candidates.count() > 1) throw new ResolverException("Design " + name.toString() + " is ambiguous. (" + candidates.count() + " candidates)");
            StructuredDesign parent = candidates.getFirst();
            if(!resolved[parentId]) resolveDesignInheritance(parentId, parent, structuredDesigner);
            for(ChainListItem<StructuredSetter> item = parent.getSetters().getLastItem(); item != null; item = item.getPreviousItem()){
                structuredDesign.getSetters().addFirst(item.getData());
            }
        }
        resolved[i] = true;
        locks[i] = false;
    }
}
