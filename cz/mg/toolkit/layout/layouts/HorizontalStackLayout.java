package cz.mg.toolkit.layout.layouts;

import cz.mg.toolkit.component.Component;
import cz.mg.toolkit.component.Container;
import cz.mg.toolkit.layout.reshapes.HorizontalLinearReshape;
import cz.mg.toolkit.layout.reshapes.HorizontalOverlayReshape;
import cz.mg.toolkit.layout.reshapes.VerticalLinearReshape;
import cz.mg.toolkit.layout.reshapes.VerticalOverlayReshape;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;


public class HorizontalStackLayout extends StackLayout {
    @Override
    public void reshapeComponents(Container parent) {
        HorizontalLinearReshape.reshape(parent, stacks);
        VerticalOverlayReshape.reshape(parent, stacks);
        
        for(Stack stack : stacks){
            HorizontalOverlayReshape.reshape(stack, stack.components);
            VerticalLinearReshape.reshape(stack, stack.components);
            for(Component component : stack.components){
                component.setX(component.getX() + stack.getX());
                component.setY(component.getY() + stack.getY());
            }
        }
    }

    @Override
    public double computeMinWidth(Container parent) {
        return HorizontalLinearReshape.computeWrapWidth(parent, stacks);
    }

    @Override
    public double computeMinHeight(Container parent) {
        return VerticalOverlayReshape.computeMinHeight(parent, stacks);
    }

    @Override
    public int getStack(Component component) {
        return getColumn(component);
    }

    @Override
    public Stack createStack() {
        return new VerticalStack();
    }
    
    private static class VerticalStack extends Stack {
        @Override
        public void computeContentSize() {
            double vs = getVerticalSpacing(this);
            
            for(Component component : components){
                setContentWidth(Math.max(getContentWidth(), getMinWidth(component)));
                setContentHeight(getContentHeight() + getMinHeight(component));
            }
            
            double totalVerticalSpacing = Math.max(0, components.count() - 1) * vs;
            setContentHeight(getContentHeight() + totalVerticalSpacing);
        }
    }
}
