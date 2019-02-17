package cz.mg.toolkit.graphics.designer.loader.task;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.collections.list.chainlist.ChainListItem;
import cz.mg.collections.tree.TreeNode;
import cz.mg.parser.utilities.Substring;
import cz.mg.toolkit.graphics.Color;
import cz.mg.toolkit.graphics.Decoration;
import cz.mg.toolkit.graphics.Font;
import cz.mg.toolkit.graphics.designer.StructuredDesign;
import cz.mg.toolkit.graphics.designer.StructuredDesigner;
import cz.mg.toolkit.graphics.designer.StructuredSetter;
import cz.mg.toolkit.graphics.designer.loader.entity.*;
import cz.mg.toolkit.graphics.designer.loader.utilities.ResolveException;
import cz.mg.toolkit.utilities.annotations.ComponentDecoration;
import cz.mg.toolkit.utilities.annotations.ComponentDecorations;
import cz.mg.toolkit.utilities.annotations.ComponentProperties;
import cz.mg.toolkit.utilities.annotations.ComponentProperty;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;


public class DesignerResolver {
    private Logic logic;
    private ChainList<Method> properties;
    private ChainList<Field> decorations;
    private boolean[] locks;
    private boolean[] resolved;
    private HashMap<String, Boolean> usages;
    private HashMap<Method, Boolean> methods;
    private ChainList<Substring> parentNames;

    public DesignerResolver() {
    }

    public StructuredDesigner resolve(Logic logic){
        try {
            StructuredDesigner structuredDesigner = new StructuredDesigner();
            this.logic = logic;
            this.usages = new HashMap<>();
            this.properties = getUsingPropertiesMethods(getUsingPropertiesClasses());
            this.decorations = getUsingDecorationsFields(getUsingDecorationsClasses());
            this.parentNames = new ChainList<>();

            for(LogicalDesigner designer : logic){
                for(TreeNode child : designer){
                    if(child instanceof LogicalDesign){
                        StructuredDesign structuredDesign = resolveDesign((LogicalDesign) child);
                        structuredDesigner.getDesigns().addLast(structuredDesign);
                        parentNames.addLast(((LogicalDesign) child).getParentName());
                    }
                }
            }

            this.locks = new boolean[structuredDesigner.getDesigns().count()];
            this.resolved = new boolean[structuredDesigner.getDesigns().count()];
            int i = 0;
            for(StructuredDesign design : structuredDesigner.getDesigns()){
                resolveDesignInheritance(i, design, structuredDesigner);
                i++;
            }

            for(StructuredDesign design : structuredDesigner.getDesigns()){
                optimizeDesign(design);
            }

            return structuredDesigner;
        } finally {
            this.properties = null;
            this.decorations = null;
            this.logic = null;
            this.locks = null;
            this.resolved = null;
            this.usages = null;
            this.methods = null;
            this.parentNames = null;
        }
    }

    private StructuredDesign resolveDesign(LogicalDesign define){
        StructuredDesign structuredDesign = new StructuredDesign(define.getName().toString(), define.getParentName() != null ? define.getParentName().toString() : null);
        for(Object childSetter : define.getChildren()){
            if(childSetter instanceof LogicalSetter){
                LogicalSetter setter = (LogicalSetter) childSetter;
                StructuredSetter structuredSetter = resolveSetter(setter);
                structuredDesign.getSetters().addLast(structuredSetter);
            }
        }
        return structuredDesign;
    }

    private StructuredSetter resolveSetter(LogicalSetter setter){
        String methodName = getMethodName(setter.getName());
        ChainList<Method> candidates = new ChainList<>();
        for(Method property : properties){
            if(property.getParameterCount() == (setter.getValues().count() + 1)){
                if(property.getName().equals(methodName)){
                    candidates.addLast(property);
                }
            }
        }
        if(candidates.count() <= 0) throw new ResolveException(setter.getName(), "Parameter " + setter.getName().toString() + " was not found. Make sure method " + methodName + " is defined in used class and properly annotated.");
        if(candidates.count() > 1) throw new ResolveException(setter.getName(), "Parameter " + setter.getName().toString() + " is ambiguous. (" + candidates.count() + " candidates)");
        Method method = candidates.getFirst();
        Object[] values = resolveSetterValues(setter, method);
        return new StructuredSetter(method, values);
    }

    private Object[] resolveSetterValues(LogicalSetter setter, Method method){
        Object[] values = new Object[method.getParameterCount()];
        int i = 1;
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
        throw new RuntimeException("Unsupported type " + c.getSimpleName() + ".");
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
            if(candidates.count() <= 0) throw new ResolveException(name, "Undefined constant " + name.toString() + ".");
            if(candidates.count() > 1) throw new ResolveException(name, "Constant " + name.toString() + " is ambiguous. (" + candidates.count() + " candidates)");
            return candidates.getFirst();
        } else {
            ChainList<Substring> candidates = new ChainList<>();
            for(LogicalDesigner designer : logic){
                for(TreeNode child : designer){
                    if(child instanceof LogicalConstant){
                        LogicalConstant constant = (LogicalConstant) child;
                        if(constant.getName().equals(name)){
                            candidates.addLast(constant.getValue());
                        }
                    }
                }
            }
            if(candidates.count() <= 0) throw new ResolveException(name, "Undefined constant " + name.toString() + ".");
            if(candidates.count() > 1) throw new ResolveException(name, "Constant " + name.toString() + " is ambiguous. (" + candidates.count() + " candidates)");
            return resolveLiteralValue(c, candidates.getFirst());
        }
    }

    private Object parseInteger(Substring value){
        try {
            return Integer.parseInt(value.toString());
        } catch(RuntimeException e){
            throw new ResolveException(value, e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }

    private Object parseLong(Substring value){
        try {
            return Long.parseLong(value.toString());
        } catch(RuntimeException e){
            throw new ResolveException(value, e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }

    private Object parseFloat(Substring value){
        try {
            return Float.parseFloat(value.toString());
        } catch(RuntimeException e){
            throw new ResolveException(value, e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }

    private Object parseDouble(Substring value){
        try {
            return Double.parseDouble(value.toString());
        } catch(RuntimeException e){
            throw new ResolveException(value, e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }

    private Object parseBoolean(Substring value){
        try {
            return Boolean.parseBoolean(value.toString());
        } catch(RuntimeException e){
            throw new ResolveException(value, e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }

    private Object parseColor(Substring value){
        String v = value.toString();
        if(value.count() < 0) throw new ResolveException(value, "Missing value.");
        if(value.get(0) != '#') throw new ResolveException(value, "Missing # for color definition. (" + value.toString() + ")");
        if(value.count() == 7){
            try {
                return new Color(
                        Integer.parseInt(v.substring(1,1+2), 16),
                        Integer.parseInt(v.substring(3,3+2), 16),
                        Integer.parseInt(v.substring(5,5+2), 16),
                        255
                );
            } catch(RuntimeException e){
                throw new ResolveException(value, e.getClass().getSimpleName() + ": " + e.getMessage());
            }
        }
        if(value.count() == 9){
            try {
                return new Color(
                        Integer.parseInt(v.substring(1,1+2), 16),
                        Integer.parseInt(v.substring(3,3+2), 16),
                        Integer.parseInt(v.substring(5,5+2), 16),
                        Integer.parseInt(v.substring(7,7+2), 16)
                );
            } catch(RuntimeException e){
                throw new ResolveException(value, e.getClass().getSimpleName() + ": " + e.getMessage());
            }
        }
        throw new ResolveException(value, "Invalid color code length. Expected 6 or 8, but got " + (value.count() - 1) + ".");
    }

    private Object parseFont(Substring value){
        try {
            String[] values = value.toString().split(",");
            if(values.length != 3) throw new ResolveException(value, "Invalid number of parameters for font. Expected 3 (name, size, style), but got " + values.length);
            return new Font(values[0].trim(), Double.parseDouble(values[1].trim()), Font.Style.valueOf(values[2].toUpperCase().trim()));
        } catch(RuntimeException e){
            throw new ResolveException(value, e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }

    private Object parseDecoration(Substring value){
        if(value.equals("null")) return null;
        throw new ResolveException(value, "Decoration cannot be literal value except null. (" + value + ")");
    }

    private String getMethodName(Substring name){
        if(name.count() <= 0) return "";
        String[] names = name.toString().split(" ");
        StringBuilder sb = new StringBuilder("set");
        for(String n : names){
            if(n.length() > 0){
                sb.append(n.substring(0, 1).toUpperCase());
                sb.append(n.substring(1).toLowerCase());
            }
        }
        return sb.toString();
    }

    private ChainList<Class> getUsingPropertiesClasses(){
        ChainList<Class> classes = new ChainList<>();
        for(LogicalDesigner designer : logic){
            for(TreeNode child : designer){
                if(child instanceof LogicalProperties){
                    Substring cp = ((LogicalProperties) child).getClassPath();
                    String classPath = cp.toString();
                    if(usages.containsKey(classPath)) continue;
                    usages.put(classPath, true);
                    try {
                        Class c = Class.forName(classPath);
                        if(c.isAnnotationPresent(ComponentProperties.class)){
                            classes.addLast(c);
                        } else {
                            throw new ResolveException(cp, "Missing ComponentProperties annotation on class " + classPath + ".");
                        }
                    } catch(ReflectiveOperationException e){
                        throw new ResolveException(cp, "Could not load class " + classPath + ": " + e.getClass().getSimpleName() + ": " + e.getMessage());
                    }
                }
            }
        }
        return classes;
    }

    private ChainList<Class> getUsingDecorationsClasses(){
        ChainList<Class> classes = new ChainList<>();
        for(LogicalDesigner designer : logic){
            for(Object child : designer){
                if(child instanceof LogicalDecorations){
                    Substring cp = ((LogicalDecorations) child).getClassPath();
                    String classPath = cp.toString();
                    if(usages.containsKey(classPath)) continue;
                    usages.put(classPath, true);
                    try {
                        Class c = Class.forName(classPath);
                        if(c.isAnnotationPresent(ComponentDecorations.class)){
                            classes.addLast(c);
                        } else {
                            throw new ResolveException(cp, "Missing ComponentDecorations annotation on class " + classPath + ".");
                        }
                    } catch(ReflectiveOperationException e){
                        throw new ResolveException(cp, "Could not load class " + classPath + ": " + e.getClass().getSimpleName() + ": " + e.getMessage());
                    }
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
        if(locks[i]) throw new ResolveException(parentNames.get(i), "Cyclic inheritance detected at design " + structuredDesign.getName());
        locks[i] = true;
        if(!resolved[i] && structuredDesign.getParentName() != null){
            String name = structuredDesign.getParentName();
            int ii = 0;
            ChainList<StructuredDesign> candidates = new ChainList<>();
            int parentId = -1;
            for(StructuredDesign candidate : structuredDesigner.getDesigns()){
                if(candidate.getName().equals(name)){
                    candidates.addLast(candidate);
                    parentId = ii;
                }
                ii++;
            }
            if(candidates.count() <= 0) throw new ResolveException(parentNames.get(i), "Unknown design " + name + ".");
            if(candidates.count() > 1) throw new ResolveException(parentNames.get(i), "Design " + name + " is ambiguous. (" + candidates.count() + " candidates)");
            StructuredDesign parent = candidates.getFirst();
            if(!resolved[parentId]) resolveDesignInheritance(parentId, parent, structuredDesigner);
            for(ChainListItem<StructuredSetter> item = parent.getSetters().getLastItem(); item != null; item = item.getPreviousItem()){
                structuredDesign.getSetters().addFirst(item.getData());
            }
        }
        resolved[i] = true;
        locks[i] = false;
    }

    private void optimizeDesign(StructuredDesign design){
        methods = new HashMap<>();
        ChainList<ChainListItem<StructuredSetter>> redundantSetters = new ChainList<>();
        for(ChainListItem<StructuredSetter> s = design.getSetters().getLastItem(); s != null; s = s.getPreviousItem()){
            Method m = s.getData().getMethod();
            if(!methods.containsKey(m)){
                methods.put(m, true);
            } else {
                redundantSetters.addLast(s);
            }
        }
        for(ChainListItem<StructuredSetter> redundandSetter : redundantSetters) redundandSetter.remove();
    }
}
