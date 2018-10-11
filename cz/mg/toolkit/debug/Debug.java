package cz.mg.toolkit.debug;

import cz.mg.toolkit.component.Component;
import cz.mg.toolkit.component.Container;
import cz.mg.toolkit.event.adapters.LocalMouseButtonAdapter;
import cz.mg.toolkit.event.adapters.LocalMouseMotionAdapter;
import cz.mg.toolkit.event.events.MouseButtonEvent;
import cz.mg.toolkit.event.events.MouseMotionEvent;
import cz.mg.toolkit.event.events.VisitEvent;
import cz.mg.toolkit.graphics.Color;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;
import cz.mg.toolkit.utilities.DrawableComponent;


public class Debug {
    private static int currentId = 0;
    
    public static void sendRandomColors(Component component){
        component.sendEvent(new VisitEvent() {
            @Override
            public void onComponentEnter(Component component) {
                if(component instanceof DrawableComponent){
                    setRandomColors(component);
                }
            }

            @Override
            public void onComponentLeave(Component component) {
            }
        });
    }
    
    public static void setIds(Component component){
        component.sendEvent(new VisitEvent() {
            @Override
            public void onComponentEnter(Component component) {
                setId(component, currentId);
                currentId++;
            }

            @Override
            public void onComponentLeave(Component component) {
            }
        });
    }
    
    public static void setMouseMotionPrintInfo(Component component){
        component.sendEvent(new VisitEvent() {
            @Override
            public void onComponentEnter(Component component) {
            }

            @Override
            public void onComponentLeave(Component component) {
                component.getEventListeners().addLast(new LocalMouseMotionAdapter() {
                    @Override
                    public void onMouseMotionEventLeave(MouseMotionEvent e) {
                        if(!isShiftPressed()) return;
                        e.consume();
                        System.out.println("### " + getId(component) + ": " + component.getClass().getSimpleName());
                    }
                });
            }
        });
    }
    
    public static void setMouseButtonPrintInfo(Component component){
        component.sendEvent(new VisitEvent() {
            @Override
            public void onComponentEnter(Component component) {
            }

            @Override
            public void onComponentLeave(Component component) {
                component.getEventListeners().addLast(new LocalMouseButtonAdapter() {
                    @Override
                    public void onMouseButtonEventLeave(MouseButtonEvent e) {
                        if(!isShiftPressed()) return;
                        e.consume();
                        System.out.println("### " + getId(component) + ": " + component.getClass().getSimpleName());
                    }
                });
            }
        });
    }
    
    public static void printComponentInfo(Component component){
        component.sendEvent(new VisitEvent() {
            @Override
            public void onComponentEnter(Component component) {
                String message = "";
                message += getId(component);
                message += " - " + component.getClass().getSimpleName();
                if(component instanceof Container){
                    message += " - " + ((Container) component).getLayout().getClass().getSimpleName();
                }
                message += " - Min: " + getMinWidth(component) + " x " + getMinHeight(component);
                message += " - Max: " + getMaxWidth(component) + " x " + getMaxHeight(component);
                message += " - Current: " + component.getWidth() + " x " + component.getHeight();
                debugLine("### " + message, 1);
            }

            @Override
            public void onComponentLeave(Component component) {
                debugLine("", -1);
            }
        });
    }
    
    
    ////////////////////////////////////////////////////////////////////////////
    
    
    private static int level = 0;
    
    public static void debugLine(String string){
        for(int i = 0; i < level; i++) System.out.print("    ");
        System.out.println(string);
    }
    
    public static void debugLine(String string, int sink){
        if(sink < 0) level--;
        for(int i = 0; i < level; i++) System.out.print("    ");
        System.out.println(string);
        if(sink > 0) level++;
        if(level > 100) throw new RuntimeException();
        if(level < 0) throw new RuntimeException();
    }
    
    
    
    ////////////////////////////////////////////////////////////////////////////


    public static void setRaiseRandomColors(Component component){
        component.raiseEvent(new VisitEvent() {
            @Override
            public void onComponentEnter(Component component) {
                if(component instanceof DrawableComponent){
                    setRandomColors(component);
                }
            }

            @Override
            public void onComponentLeave(Component component) {
            }
        });
    }
    
    public static void setRaiseIds(Component component){
        component.raiseEvent(new VisitEvent() {
            @Override
            public void onComponentEnter(Component component) {
                setId(component, currentId);
                currentId++;
            }

            @Override
            public void onComponentLeave(Component component) {
            }
        });
    }
    
    public static void setRaiseMouseMotionPrintInfo(Component component){
        component.raiseEvent(new VisitEvent() {
            @Override
            public void onComponentEnter(Component component) {
            }

            @Override
            public void onComponentLeave(Component component) {
                component.getEventListeners().addLast(new LocalMouseMotionAdapter() {
                    @Override
                    public void onMouseMotionEventLeave(MouseMotionEvent e) {
                        if(!isShiftPressed()) return;
                        e.consume();
                        System.out.println("### " + getId(component) + ": " + component.getClass().getSimpleName());
                    }
                });
            }
        });
    }
    
    public static void setRaiseMouseButtonPrintInfo(Component component){
        component.raiseEvent(new VisitEvent() {
            @Override
            public void onComponentEnter(Component component) {
            }

            @Override
            public void onComponentLeave(Component component) {
                component.getEventListeners().addLast(new LocalMouseButtonAdapter() {
                    @Override
                    public void onMouseButtonEventLeave(MouseButtonEvent e) {
                        if(!isShiftPressed()) return;
                        e.consume();
                        System.out.println("### " + getId(component) + ": " + component.getClass().getSimpleName());
                    }
                });
            }
        });
    }
    
    
    ////////////////////////////////////////////////////////////////////////////

    
    private static void setRandomColors(Component component) {
        setBackgroundColor(component, randomColor());
        setForegroundColor(component, randomColor());
        setDisabledBackgroundColor(component, greyscaleColor(getBackgroundColor(component)));
        setDisabledForegroundColor(component, greyscaleColor(getForegroundColor(component)));
        setHighlightedBackgroundColor(component, highlightColor(getBackgroundColor(component)));
        setHighlightedForegroundColor(component, highlightColor(getForegroundColor(component)));
    }
    
    private static Color randomColor(){
        int r = (int) (Math.random() * 192);
        int g = (int) (Math.random() * 192);
        int b = (int) (Math.random() * 192);
        return new Color(r, g, b, 255);
    }

    private static Color greyscaleColor(Color color){
        int v = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
        return new Color(v, v, v, 55);
    }

    private static Color highlightColor(Color color){
        return new Color(color.getRed()+64, color.getGreen()+64, color.getBlue()+64, 255);
    }
}
