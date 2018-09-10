package cz.mg.toolkit.layout.layouts;

import cz.mg.toolkit.component.Component;
import cz.mg.toolkit.component.Container;
import cz.mg.toolkit.layout.reshapes.HorizontalLinearReshape;
import cz.mg.toolkit.layout.reshapes.HorizontalOverlayReshape;
import cz.mg.toolkit.layout.reshapes.VerticalLinearReshape;
import cz.mg.toolkit.layout.reshapes.VerticalOverlayReshape;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;


public class VerticalStackLayout extends StackLayout {
    @Override
    public void reshapeComponents(Container parent) {
        HorizontalOverlayReshape.reshape(parent, stacks);
        VerticalLinearReshape.reshape(parent, stacks);
        
        for(Stack stack : stacks){
            HorizontalLinearReshape.reshape(stack, stack.components);
            VerticalOverlayReshape.reshape(stack, stack.components);
            for(Component component : stack.components){
                component.setX(component.getX() + stack.getX());
                component.setY(component.getY() + stack.getY());
            }
        }
    }

    @Override
    public double computeMinWidth(Container parent) {
        return HorizontalOverlayReshape.computeMinWidth(parent, stacks);
    }

    @Override
    public double computeMinHeight(Container parent) {
        return VerticalLinearReshape.computeWrapHeight(parent, stacks);
    }

    @Override
    public int getStack(Component component) {
        return getRow(component);
    }

    @Override
    public Stack createStack() {
        return new HorizontalStack();
    }
    
    private static class HorizontalStack extends Stack {
        @Override
        public void computeContentSize() {
            double hs = getHorizontalSpacing(this);
            
            for(Component component : components){
                setContentWidth(getContentWidth() + getMinWidth(component));
                setContentHeight(Math.max(getContentHeight(), getMinHeight(component)));
            }
            
            double totalHorizontalSpacing = Math.max(0, components.count() - 1) * hs;
            setContentWidth(getContentWidth() + totalHorizontalSpacing);
        }
    }
}
