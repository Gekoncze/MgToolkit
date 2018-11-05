package cz.mg.toolkit.designer.designers;

import cz.mg.collections.array.Array;
import cz.mg.collections.list.List;
import cz.mg.toolkit.component.Component;
import cz.mg.toolkit.component.containers.Panel;
import cz.mg.toolkit.component.contents.InteractiveMultilineTextContent;
import cz.mg.toolkit.component.contents.InteractiveSinglelineTextContent;
import cz.mg.toolkit.component.contents.MultilineTextContent;
import cz.mg.toolkit.component.contents.SinglelineTextContent;
import cz.mg.toolkit.component.controls.CheckBox;
import cz.mg.toolkit.component.controls.HorizontalScrollBar;
import cz.mg.toolkit.component.controls.HorizontalSlider;
import cz.mg.toolkit.component.controls.MultilineTextInput;
import cz.mg.toolkit.component.controls.RadioButton;
import cz.mg.toolkit.component.controls.SelectionList;
import cz.mg.toolkit.component.controls.SinglelineTextInput;
import cz.mg.toolkit.component.controls.VerticalScrollBar;
import cz.mg.toolkit.component.controls.VerticalSlider;
import cz.mg.toolkit.designer.CompositeDesigner;
import cz.mg.toolkit.graphics.Color;
import cz.mg.toolkit.graphics.Decoration;
import cz.mg.toolkit.graphics.Font;
import cz.mg.toolkit.graphics.Graphics;
import cz.mg.toolkit.graphics.decorations.BackgroundColorDecoration;
import cz.mg.toolkit.graphics.decorations.ForegroundColorDecoration;
import cz.mg.toolkit.graphics.decorations.RectangleBorderDecoration;
import cz.mg.toolkit.graphics.decorations.RectangleDecoration;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;
import cz.mg.toolkit.utilities.SizePolicy;
import cz.mg.toolkit.utilities.sizepolices.FixedSizePolicy;
import static cz.mg.toolkit.graphics.decorations.BackgroundColorDecoration.*;
import static cz.mg.toolkit.graphics.decorations.ForegroundColorDecoration.*;


public class DefaultDesigner extends CompositeDesigner {
    private static final Color BACKGROUND_COLOR = new Color(0, 16, 0, 255);
    private static final Color FOREGROUND_COLOR = new Color(0, 220, 0, 255);
    
    private static final Color DISABLED_BACKGROUND_COLOR = new Color(16, 16, 16, 255);
    private static final Color DISABLED_FOREGROUND_COLOR = new Color(220, 220, 220, 255);
    
    private static final Color HIGHLIGHTED_BACKGROUND_COLOR = new Color(0, 48, 0, 255);
    private static final Color HIGHLIGHTED_FOREGROUND_COLOR = new Color(0, 255, 0, 255);
    
    private static final Color TITLE_BACKGROUND_COLOR = new Color(0, 224, 0, 255);
    private static final Color TITLE_FOREGROUND_COLOR = new Color(0, 16, 0, 255);
    
    private static final Color TITLE_DISABLED_BACKGROUND_COLOR = new Color(224, 224, 224, 255);
    private static final Color TITLE_DISABLED_FOREGROUND_COLOR = new Color(16, 16, 16, 255);
    
    private static final Color TITLE_HIGHLIGHTED_BACKGROUND_COLOR = new Color(0, 255, 0, 255);
    private static final Color TITLE_HIGHLIGHTED_FOREGROUND_COLOR = new Color(0, 32, 0, 255);
    
    private static final Color CONTRAST_COLOR = new Color(255, 0, 0, 255);
    
    private static final Decoration COMMON_BACKGROUND = new BackgroundColorDecoration(new RectangleDecoration());
    private static final Decoration COMMON_FOREGROUND = new ForegroundColorDecoration(new RectangleBorderDecoration());
    
    private static final Font TITLE_BAR_FONT = new Font("default", 18, Font.Style.BOLD);
    private static final Font MENU_ITEM_DESCRIPTION_FONT = new Font("default", 18, Font.Style.REGULAR);
    private static final Font MENU_ITEM_SHORTCUT_FONT = new Font("default", 18, Font.Style.ITALIC);
    private static final Font TAB_AREA_FONT = new Font("default", 15, Font.Style.REGULAR);
    
    private static final double MENU_SPACING = 6;
    private static final double MENU_PADDING = 6;
    private static final double SPLIT_AREA_SPACING = 4;
    private static final double TAB_AREA_HEADER_SPACING = 4;
    private static final double TITLE_BAR_SPACING = 2;
    
    private static final double BUTTON_PADDING = 6;
    private static final double SCROLL_BUTTON_PADDING = 6;
    private static final double CLOSE_BUTTON_PADDING = 0;
    private static final double TEXT_INPUT_PADDING = 4;
    private static final double TAB_CLOSE_BUTTON_PADDING = 4;
    private static final double TAB_AREA_HEADER_PADDING = 5;
    private static final double TITLE_BAR_BUTTON_PADDING = 4;
    private static final double TITLE_BAR_PADDING = 2;
    private static final double CHECK_BOX_PADDING = 4;
    private static final double RADIO_BUTTON_PADDING = 4;
    private static final double SPINNER_BUTTON_PADDING = 4;
    private static final double COMBO_BOX_TEXT_PADDING = 4;
    private static final double COMBO_BOX_MENU_PADDING = 4;
    private static final double COMBO_BOX_BUTTON_PADDING = 6;
    private static final double SELECTION_LIST_ITEM_HORIZONTAL_PADDING = 4;
    private static final double SELECTION_LIST_ITEM_VERTICAL_PADDING = 2;
    private static final double SELECTION_LIST_VERTICAL_PADDING = 2;
    
    private static final double SCROLL_BUTTON_SIZE = 24;
    private static final double COMBO_BOX_BUTTON_SIZE = 24;
    private static final double CHECK_BOX_SIZE = 16;
    private static final double RADIO_BUTTON_SIZE = 16;
    private static final double SPINNER_BUTTON_WIDTH = 24;
    private static final double SPINNER_BUTTON_HEIGHT = 12;
    private static final double SLIDER_SIZE = 16;
    private static final double TITLE_BAR_SIZE = 24;
    private static final double TAB_AREA_HEADER_SIZE = 24;
    
    private static final Decoration LEFT_SCROLL_BUTTON_CONTENT_FOREGROUND = new ForegroundColorDecoration(new Decoration() {
        @Override
        protected void onDraw(Graphics g, Component component) {
            double w = component.getWidth();
            double h = component.getHeight();
            g.drawLine(1.0*w, 0.0*h, 0.0*w, 0.5*h, -1, 0, 0, 0);
            g.drawLine(0.0*w, 0.5*h, 1.0*w, 1.0*h, 0, 0, -1, -1);
        }
    });
    
    private static final Decoration RIGHT_SCROLL_BUTTON_CONTENT_FOREGROUND = new ForegroundColorDecoration(new Decoration() {
        @Override
        protected void onDraw(Graphics g, Component component) {
            double w = component.getWidth();
            double h = component.getHeight();
            g.drawLine(0.0*w, 0.0*h, 1.0*w, 0.5*h, 0, 0, -1, 0);
            g.drawLine(1.0*w, 0.5*h, 0.0*w, 1.0*h, -1, 0, 0, -1);
        }
    });
    
    private static final Decoration UP_SCROLL_BUTTON_CONTENT_FOREGROUND = new ForegroundColorDecoration(new Decoration() {
        @Override
        protected void onDraw(Graphics g, Component component) {
            double w = component.getWidth();
            double h = component.getHeight();
            g.drawLine(0.0*w, 1.0*h, 0.5*w, 0.0*h, 0, -1, 0, 0);
            g.drawLine(0.5*w, 0.0*h, 1.0*w, 1.0*h, 0, 0, -1, -1);
        }
    });
    
    private static final Decoration DOWN_SCROLL_BUTTON_CONTENT_FOREGROUND = new ForegroundColorDecoration(new Decoration() {
        @Override
        protected void onDraw(Graphics g, Component component) {
            double w = component.getWidth();
            double h = component.getHeight();
            g.drawLine(0.0*w, 0.0*h, 0.5*w, 1.0*h, 0, 0, 0, -1);
            g.drawLine(0.5*w, 1.0*h, 1.0*w, 0.0*h, 0, -1, -1, 0);
        }
    });
    
    private static final Decoration MINIMIZE_BUTTON_CONTENT_FOREGROUND = new ForegroundColorDecoration(new Decoration() {
        @Override
        protected void onDraw(Graphics g, Component component) {
            double w = component.getWidth();
            double h = component.getHeight();
            g.drawLine(0.0*w, 1.0*h, 1.0*w, 1.0*h, 0, -1, -1, -1);
        }
    });
    
    private static final Decoration MAXIMIZE_BUTTON_CONTENT_FOREGROUND = new ForegroundColorDecoration(new Decoration() {
        @Override
        protected void onDraw(Graphics g, Component component) {
            double w = component.getWidth();
            double h = component.getHeight();
            g.drawRectangleBorder(0.0*w, 0.0*h, 1.0*w, 1.0*h);
        }
    });
    
    private static final Decoration CLOSE_BUTTON_CONTENT_FOREGROUND = new ForegroundColorDecoration(new Decoration() {
        @Override
        protected void onDraw(Graphics g, Component component) {
            double w = component.getWidth();
            double h = component.getHeight();
            g.drawLine(0.0*w, 0.0*h, 1.0*w, 1.0*h, 0, 0, -1, -1);
            g.drawLine(0.0*w, 1.0*h, 1.0*w, 0.0*h, 0, -1, -1, 0);
        }
    });
    
    private static final Decoration UP_SPINNER_BUTTON_CONTENT_FOREGROUND = new ForegroundColorDecoration(new Decoration() {
        @Override
        protected void onDraw(Graphics g, Component component) {
            double w = component.getWidth();
            double h = component.getHeight();
            g.drawLine(0.0*w, 1.0*h, 0.5*w, 0.0*h, 0, -1, 0, 0);
            g.drawLine(0.5*w, 0.0*h, 1.0*w, 1.0*h, 0, 0, -1, -1);
            g.drawLine(0.0*w, 1.0*h, 1.0*w, 1.0*h, 0, -1, -1, -1);
        }
    });
    
    private static final Decoration DOWN_SPINNER_BUTTON_CONTENT_FOREGROUND = new ForegroundColorDecoration(new Decoration() {
        @Override
        protected void onDraw(Graphics g, Component component) {
            double w = component.getWidth();
            double h = component.getHeight();
            g.drawLine(0.0*w, 0.0*h, 0.5*w, 1.0*h, 0, 0, 0, -1);
            g.drawLine(0.5*w, 1.0*h, 1.0*w, 0.0*h, 0, -1, -1, 0);
            g.drawLine(0.0*w, 0.0*h, 1.0*w, 0.0*h, 0, 0, -1, 0);
        }
    });
    
    private static final Decoration COMBO_BOX_BUTTON_CONTENT_FOREGROUND = new ForegroundColorDecoration(new Decoration() {
        @Override
        protected void onDraw(Graphics g, Component component) {
            double w = component.getWidth();
            double h = component.getHeight();
            g.drawLine(0.0*w, 0.0*h, 0.5*w, 1.0*h, 0, 0, 0, -1);
            g.drawLine(0.5*w, 1.0*h, 1.0*w, 0.0*h, 0, -1, -1, 0);
            g.drawLine(0.0*w, 0.0*h, 1.0*w, 0.0*h, 0, 0, -1, 0);
        }
    });
    
    private static final Decoration CHECK_BOX_FOREGROUND = new ForegroundColorDecoration(new Decoration() {
        @Override
        protected void onDraw(Graphics g, Component component) {
            double w = component.getWidth();
            double h = component.getHeight();
            boolean s = ((CheckBox)component).isSelected();
            g.drawRectangleBorder(0, 0, w, h);
            if(s) g.drawLine(0.1*w, 0.5*h, 0.4*w, 0.9*h);
            if(s) g.drawLine(0.4*w, 0.8*h, 0.8*w, 0.1*h);
        }
    });
    
    private static final Decoration RADIO_BUTTON_FOREGROUND = new ForegroundColorDecoration(new Decoration() {
        @Override
        protected void onDraw(Graphics g, Component component) {
            double w = component.getWidth();
            double h = component.getHeight();
            boolean s = ((RadioButton)component).isSelected();
            g.drawOvalBorder(0, 0, w, h);
            if(s) g.drawOval(0.2*w, 0.2*h, 0.6*w, 0.6*h);
        }
    });
    
    private static final Decoration HORIZONTAL_SCROLL_BAR_FOREGROUND = new ForegroundColorDecoration(new Decoration(){
        @Override
        protected void onDraw(Graphics g, Component component) {
            HorizontalScrollBar.Content bar = (HorizontalScrollBar.Content) component;
            Panel scrollablePanel = bar.getScrollablePanel();
            
            if(scrollablePanel.getContentWidth() <= 0) return;
            
            double ww = component.getWidth();
            double hh = component.getHeight();

            double hp = 3; // horizontal padding
            double vp = 2; // vertical padding
            double s = 2; // spacing (for |||)
            double aw = ww - 2*hp; // available width


            double scroll = getHorizontalScroll(scrollablePanel);
            double minScroll = scrollablePanel.getMinHorizontalScroll();
            double maxScroll = scrollablePanel.getMaxHorizontalScroll();
            double ds = maxScroll - minScroll;

            if(ds <= 0) return;

            double k = scrollablePanel.getWidth() / scrollablePanel.getContentWidth();
            double p = (scroll - minScroll) / (ds);

            double w =  (aw * k);
            double h = hh - 2*vp;

            double fw = aw - w;

            double x = hp + (fw*p);
            double y = vp;

            double rx = x + w/2;

            if(w > 4*s){
                double vpp = vp+4;
                g.drawLine(rx-s, vpp, rx-s, hh-vpp, 0, 0, 0, -2);
                g.drawLine(rx  , vpp, rx  , hh-vpp, 0, 0, 0, -2);
                g.drawLine(rx+s, vpp, rx+s, hh-vpp, 0, 0, 0, -2);
            }

            g.drawRectangleBorder(x, y, w-1, h-1);
        }
    });
    
    private static final Decoration VERTICAL_SCROLL_BAR_FOREGROUND = new ForegroundColorDecoration(new Decoration() {
        @Override
        protected void onDraw(Graphics g, Component component) {
            VerticalScrollBar.Content bar = (VerticalScrollBar.Content) component;
            Panel scrollablePanel = bar.getScrollablePanel();
            
            if(scrollablePanel.getContentHeight() <= 0) return;
            
            double ww = component.getWidth();
            double hh = component.getHeight();

            double hp = 3; // horizontal padding
            double vp = 2; // vertical padding
            double s = 2; // spacing (for |||)
            double ah = hh - 2*vp; // available height


            double scroll = getVerticalScroll(scrollablePanel);
            double minScroll = scrollablePanel.getMinVerticalScroll();
            double maxScroll = scrollablePanel.getMaxVerticalScroll();
            double ds = maxScroll - minScroll;

            if(ds <= 0) return;

            double k = scrollablePanel.getHeight() / scrollablePanel.getContentHeight();
            double p = (scroll - minScroll) / (ds);

            double w = ww - 2*hp;
            double h =  (ah * k);

            double fh = ah - h;

            double x = hp;
            double y = vp + (fh*p);

            double ry = y + h/2;

            if(h > 4*s){
                double hpp = hp+4;
                g.drawLine(hpp, ry-s, ww-hpp, ry-s, 0, 0, -2, 0);
                g.drawLine(hpp, ry,   ww-hpp, ry  , 0, 0, -2, 0);
                g.drawLine(hpp, ry+s, ww-hpp, ry+s, 0, 0, -2, 0);
            }

            g.drawRectangleBorder(x, y, w-1, h-1);
        }
    });
    
    private static final Decoration HORIZONTAL_SLIDER_FOREGROUND = new ForegroundColorDecoration(new Decoration() {
        @Override
        protected void onDraw(Graphics g, Component component) {
            double ss = ((HorizontalSlider)component).getSliderSize();
            double w = component.getWidth();
            double h = component.getHeight();
            g.drawLine(ss/2, h/2, w-ss/2, h/2);
            
            double pos = ((HorizontalSlider)component).getSliderPosition();
            g.drawOval(pos-ss/2, h/2-ss/2, ss, ss);
        }
    });
    
    private static final Decoration VERTICAL_SLIDER_FOREGROUND = new ForegroundColorDecoration(new Decoration() {
        @Override
        protected void onDraw(Graphics g, Component component) {
            double ss = ((VerticalSlider)component).getSliderSize();
            double w = component.getWidth();
            double h = component.getHeight();
            g.drawLine(w/2, ss/2, w/2, h-ss/2);
            
            double pos = ((VerticalSlider)component).getSliderPosition();
            g.drawOval(w/2-ss/2, pos-ss/2, ss, ss);
        }
    });
    
    private static final Decoration HORIZONTAL_SEPARATOR_FOREGROUND = new ForegroundColorDecoration(new Decoration() {
        @Override
        protected void onDraw(Graphics g, Component component) {
            g.setColor(getCurrentForegroundColor(component));
            double space = component.getHeight() * 0.2;
            double x = component.getWidth()/2;
            double y0 = space;
            double y1 = component.getHeight() - space;
            g.drawLine(x, y0, x, y1, 0, 0, 0, 1);
        }
    });
    
    private static final Decoration VERTICAL_SEPARATOR_FOREGROUND = new ForegroundColorDecoration(new Decoration() {
        @Override
        protected void onDraw(Graphics g, Component component) {
            g.setColor(getCurrentForegroundColor(component));
            double space = component.getWidth() * 0.2;
            double y = component.getHeight()/2;
            double x0 = space;
            double x1 = component.getWidth() - space;
            g.drawLine(x0, y, x1, y, 0, 0, -1, 0);
        }
    });
    
    private static final Decoration SINGLELINE_TEXT_CONTENT_BACKGROUND = new BackgroundColorDecoration(new Decoration() {
        @Override
        protected void onDraw(Graphics g, Component component) {
            COMMON_BACKGROUND.getInnerDecoration().draw(g, component);
        }
    });
    
    private static final Decoration SINGLELINE_TEXT_CONTENT_FOREGROUND = new ForegroundColorDecoration(new Decoration() {
        @Override
        protected void onDraw(Graphics g, Component component) {
            SinglelineTextContent content = (SinglelineTextContent) component;
            g.setFont(getFont(content));
            g.drawText(content.getTextModel().getText(), content.getHorizontalTextPosition(), content.getVerticalTextPosition());
        }
    });
    
    private static final Decoration INTERACTIVE_SINGLELINE_TEXT_CONTENT_BACKGROUND = new BackgroundColorDecoration(new Decoration() {
        @Override
        protected void onDraw(Graphics g, Component component) {
            SINGLELINE_TEXT_CONTENT_BACKGROUND.getInnerDecoration().draw(g, component);
            
            InteractiveSinglelineTextContent content = (InteractiveSinglelineTextContent) component;
            if(content.getCaret() != content.getSelectionCaret()) {
                int min = Math.min(content.getCaret(), content.getSelectionCaret());
                int max = Math.max(content.getCaret(), content.getSelectionCaret());
                double x = content.caretToPosition(min);
                double y = content.getVerticalTextPosition();
                double w = content.caretToPosition(max) - x;
                double h = content.getLineHeight();
                g.setColor(getCurrentForegroundColor(content));
                g.drawRectangle(x, y, w, h);
            }
        }
    });
    
    private static final Decoration INTERACTIVE_SINGLELINE_TEXT_CONTENT_FOREGROUND = new ForegroundColorDecoration(new Decoration() {
        @Override
        protected void onDraw(Graphics g, Component component) {
            SINGLELINE_TEXT_CONTENT_FOREGROUND.getInnerDecoration().draw(g, component);
            
            InteractiveSinglelineTextContent content = (InteractiveSinglelineTextContent) component;
            
            if(content.getCaret() != content.getSelectionCaret()){
                int min = Math.min(content.getCaret(), content.getSelectionCaret());
                double x = content.caretToPosition(min);
                double y = content.getVerticalTextPosition();
                g.setColor(getCurrentBackgroundColor(content));
                g.drawText(content.getSelectedText(), x, y);
            }

            if(content.hasKeyboardFocus()){
                double x = content.caretToPosition(content.getCaret());
                double y = content.getVerticalTextPosition();
                g.setColor(getContrastColor(content));
                g.drawLine(x, y, x, y + content.getLineHeight());
            }
        }
    });
    
    private static final Decoration SINGLELINE_TEXT_INPUT_CONTENT_FOREGROUND = new ForegroundColorDecoration(new Decoration() {
        @Override
        protected void onDraw(Graphics g, Component component) {
            INTERACTIVE_SINGLELINE_TEXT_CONTENT_FOREGROUND.getInnerDecoration().draw(g, component);
            
            SinglelineTextInput.TextContent textContent = (SinglelineTextInput.TextContent) component;
            String placeholderText = ((SinglelineTextInput)textContent.getParent()).getPlaceholderText();
            if(!textContent.hasKeyboardFocus() && textContent.getText().length() <= 0){
                if(placeholderText == null) return;
                g.setTransparency(0.5);
                g.setColor(getCurrentForegroundColor(textContent));
                g.setFont(getFont(textContent));
                g.drawText(placeholderText, textContent.getHorizontalTextPosition(placeholderText), textContent.getVerticalTextPosition(placeholderText));
                g.setTransparency(1.0);
            }
        }
    });
    
    private static final Decoration MULTILINE_TEXT_CONTENT_BACKGROUND = new BackgroundColorDecoration(new Decoration() {
        @Override
        protected void onDraw(Graphics g, Component component) {
            COMMON_BACKGROUND.getInnerDecoration().draw(g, component);
        }
    });
    
    private static final Decoration MULTILINE_TEXT_CONTENT_FOREGROUND = new ForegroundColorDecoration(new Decoration() {
        @Override
        protected void onDraw(Graphics g, Component component) {
            MultilineTextContent content = (MultilineTextContent) component;
            Font font = getFont(content);
            g.setFont(font);
            for(int iy = 0; iy < content.getTextModel().lineCount(); iy++){
                String line = content.getTextModel().getLine(iy);
                double x = content.getHorizontalLinePosition(iy);
                double y = content.getVerticalLinePosition(iy);
                g.drawText(line, x, y);
            }
        }
    });
    
    private static final Decoration INTERACTIVE_MULTILINE_TEXT_CONTENT_BACKGROUND = new BackgroundColorDecoration(new Decoration() {
        @Override
        protected void onDraw(Graphics g, Component component) {
            MULTILINE_TEXT_CONTENT_BACKGROUND.getInnerDecoration().draw(g, component);
            
            InteractiveMultilineTextContent content = (InteractiveMultilineTextContent) component;
            int caret = content.getCaret();
            int selectionCaret = content.getSelectionCaret();
            if(caret != selectionCaret) {
                int min = Math.min(caret, selectionCaret);
                int max = Math.max(caret, selectionCaret);
                int[] minS = content.caretToCarets(min);
                int[] maxS = content.caretToCarets(max);
                g.setColor(getCurrentForegroundColor(content));
                if(minS[1] == maxS[1]){
                    double[] p = content.caretToPosition(min);
                    double w = content.caretToPosition(max)[0] - p[0];
                    double h = content.getLineHeight();
                    g.drawRectangle(p[0], p[1], w, h);
                } else {
                    Font font = getFont(content);

                    // draw leading line
                    String leadingLine = content.getTextModel().getLine(minS[1]);
                    double llx = content.getHorizontalLinePosition(minS[1]) + font.getWidth(leadingLine.substring(0, minS[0]));
                    double lly = content.getVerticalLinePosition(minS[1]);
                    double llw = font.getWidth(leadingLine.substring(minS[0]));
                    double llh = content.getLineHeight();
                    g.drawRectangle(llx, lly, llw, llh);

                    // draw in-between lines
                    for(int i = minS[1] + 1; i <= maxS[1] - 1; i++){
                        String inbetweenLine = content.getTextModel().getLine(i);
                        double ilx = content.getHorizontalLinePosition(i);
                        double ily = content.getVerticalLinePosition(i);
                        double ilw = content.getLineWidth(i);
                        double ilh = content.getLineHeight();
                        g.drawRectangle(ilx, ily, ilw, ilh);
                    }

                    // draw trailing line
                    String trailingLine = content.getTextModel().getLine(maxS[1]);
                    double tlx = content.getHorizontalLinePosition(maxS[1]);
                    double tly = content.getVerticalLinePosition(maxS[1]);
                    double tlw = font.getWidth(trailingLine.substring(0, maxS[0]));
                    double tlh = content.getLineHeight();
                    g.drawRectangle(tlx, tly, tlw, tlh);
                }
            }
        }
    });
    
    private static final Decoration INTERACTIVE_MULTILINE_TEXT_CONTENT_FOREGROUND = new ForegroundColorDecoration(new Decoration() {
        @Override
        protected void onDraw(Graphics g, Component component) {
            MULTILINE_TEXT_CONTENT_FOREGROUND.getInnerDecoration().draw(g, component);
            
            InteractiveMultilineTextContent content = (InteractiveMultilineTextContent) component;
            int caret = content.getCaret();
            int selectionCaret = content.getSelectionCaret();
            if(caret != selectionCaret){
                int min = Math.min(caret, selectionCaret);
                int max = Math.max(caret, selectionCaret);
                int[] minS = content.caretToCarets(min);
                int[] maxS = content.caretToCarets(max);
                g.setColor(getCurrentBackgroundColor(content));
                Font font = getFont(content);
                if(minS[1] == maxS[1]){
                    String textLine = content.getTextModel().getLine(minS[1]);
                    double x = content.getHorizontalLinePosition(minS[1]) + font.getWidth(textLine.substring(0, minS[0]));
                    double y = content.getVerticalLinePosition(minS[1]);
                    g.drawText(textLine.substring(minS[0], maxS[0]), x, y);
                } else {
                    // draw leading line
                    String leadingLine = content.getTextModel().getLine(minS[1]);
                    double llx = content.getHorizontalLinePosition(minS[1]) + font.getWidth(leadingLine.substring(0, minS[0]));
                    double lly = content.getVerticalLinePosition(minS[1]);
                    g.drawText(leadingLine.substring(minS[0]), llx, lly);

                    // draw in-between lines
                    for(int i = minS[1] + 1; i <= maxS[1] - 1; i++){
                        String inbetweenLine = content.getTextModel().getLine(i);
                        double ilx = content.getHorizontalLinePosition(i);
                        double ily = content.getVerticalLinePosition(i);
                        g.drawText(inbetweenLine, ilx, ily);
                    }

                    // draw trailing line
                    String trailingLine = content.getTextModel().getLine(maxS[1]);
                    double tlx = content.getHorizontalLinePosition(maxS[1]);
                    double tly = content.getVerticalLinePosition(maxS[1]);
                    g.drawText(trailingLine.substring(0, maxS[0]), tlx, tly);
                }
            }

            if(content.hasKeyboardFocus()){
                double[] p = content.caretToPosition(caret);
                g.setColor(getContrastColor(content));
                g.drawLine(p[0], p[1], p[0], p[1] + content.getLineHeight());
            }
        }
    });
    
    private static final Decoration MULTILINE_TEXT_INPUT_CONTENT_FOREGROUND = new ForegroundColorDecoration(new Decoration() {
        @Override
        protected void onDraw(Graphics g, Component component) {
            INTERACTIVE_MULTILINE_TEXT_CONTENT_FOREGROUND.getInnerDecoration().draw(g, component);
            
            MultilineTextInput.TextContent textContent = (MultilineTextInput.TextContent) component;
            List<String> placeholderLines = ((MultilineTextInput)textContent.getParent()).getPlaceholderLines();
            if(!textContent.hasKeyboardFocus() && textContent.getText().length() <= 0){
                if(placeholderLines == null) return;
                g.setTransparency(0.5);
                g.setColor(getCurrentForegroundColor(textContent));
                g.setFont(getFont(textContent));
                int i = 0;
                for(String placeholderLine : placeholderLines){
                    g.drawText(
                            placeholderLine,
                            textContent.getHorizontalLinePosition(i, placeholderLine),
                            textContent.getVerticalLinePosition(i, placeholderLine)
                    );
                    i++;
                }
                g.setTransparency(1.0);
            }
        }
    });
    
    private static final Decoration SELECTION_LIST_ITEM_BACKGROUND = new BackgroundColorDecoration(new Decoration() {
        @Override
        protected void onDraw(Graphics g, Component component) {
            SelectionList.ListItem listItem = (SelectionList.ListItem) component;
            g.setColor(listItem.isSelected() ? getCurrentForegroundColor(listItem) : getCurrentBackgroundColor(listItem));
            SINGLELINE_TEXT_CONTENT_BACKGROUND.getInnerDecoration().draw(g, component);
        }
    });
    
    private static final Decoration SELECTION_LIST_ITEM_FOREGROUND = new ForegroundColorDecoration(new Decoration() {
        @Override
        protected void onDraw(Graphics g, Component component) {
            SelectionList.ListItem listItem = (SelectionList.ListItem) component;
            g.setColor(!listItem.isSelected() ? getCurrentForegroundColor(listItem) : getCurrentBackgroundColor(listItem));
            SINGLELINE_TEXT_CONTENT_FOREGROUND.getInnerDecoration().draw(g, component);
        }
    });
    
    
    ////////////////////////////////////////////////////////////////////////////
    
    
    public DefaultDesigner() {
        super(new Array<Design>(new Design[]{
                new Design("component") {
                    @Override
                    public void onDesign(Component component) {
                        setPadding(component, 0);
                        setSpacing(component, 0);
                        setBackground(component, COMMON_BACKGROUND);
                        setForeground(component, COMMON_FOREGROUND);
                        setBackgroundColor(component, BACKGROUND_COLOR);
                        setForegroundColor(component, FOREGROUND_COLOR);
                        setDisabledBackgroundColor(component, DISABLED_BACKGROUND_COLOR);
                        setDisabledForegroundColor(component, DISABLED_FOREGROUND_COLOR);
                        setHighlightedBackgroundColor(component, HIGHLIGHTED_BACKGROUND_COLOR);
                        setHighlightedForegroundColor(component, HIGHLIGHTED_FOREGROUND_COLOR);
                        
//                        setBackgroundColor(component, TITLE_BACKGROUND_COLOR);
//                        setForegroundColor(component, TITLE_FOREGROUND_COLOR);
//                        setDisabledBackgroundColor(component, TITLE_DISABLED_BACKGROUND_COLOR);
//                        setDisabledForegroundColor(component, TITLE_DISABLED_FOREGROUND_COLOR);
//                        setHighlightedBackgroundColor(component, TITLE_HIGHLIGHTED_BACKGROUND_COLOR);
//                        setHighlightedForegroundColor(component, TITLE_HIGHLIGHTED_FOREGROUND_COLOR);
                    }
                }
                ,
                new Design("content", "component") {
                    @Override
                    public void onDesign(Component component) {
                        setForeground(component, null);
                    }
                }
                ,
                new Design("text content", "content") {
                    @Override
                    public void onDesign(Component component) {
                        setContrastColor(component, CONTRAST_COLOR);
                        setContrastColor(component, CONTRAST_COLOR);
                    }
                }
                ,
                new Design("singleline text content", "text content") {
                    @Override
                    public void onDesign(Component component) {
                        setBackground(component, SINGLELINE_TEXT_CONTENT_BACKGROUND);
                        setForeground(component, SINGLELINE_TEXT_CONTENT_FOREGROUND);
                    }
                }
                ,
                new Design("multiline text content", "text content") {
                    @Override
                    public void onDesign(Component component) {
                        setBackground(component, MULTILINE_TEXT_CONTENT_BACKGROUND);
                        setForeground(component, MULTILINE_TEXT_CONTENT_FOREGROUND);
                    }
                }
                ,
                new Design("interactive singleline text content", "singleline text content") {
                    @Override
                    public void onDesign(Component component) {
                        setBackground(component, INTERACTIVE_SINGLELINE_TEXT_CONTENT_BACKGROUND);
                        setForeground(component, INTERACTIVE_SINGLELINE_TEXT_CONTENT_FOREGROUND);
                    }
                }
                ,
                new Design("interactive multiline text content", "multiline text content") {
                    @Override
                    public void onDesign(Component component) {
                        setBackground(component, INTERACTIVE_MULTILINE_TEXT_CONTENT_BACKGROUND);
                        setForeground(component, INTERACTIVE_MULTILINE_TEXT_CONTENT_FOREGROUND);
                    }
                }
                ,
                new Design("singleline text input content", "interactive singleline text content") {
                    @Override
                    public void onDesign(Component component) {
                        setPadding(component, TEXT_INPUT_PADDING);
                        setForeground(component, SINGLELINE_TEXT_INPUT_CONTENT_FOREGROUND);
                    }
                }
                ,
                new Design("multiline text input content", "interactive multiline text content") {
                    @Override
                    public void onDesign(Component component) {
                        setPadding(component, TEXT_INPUT_PADDING);
                        setForeground(component, MULTILINE_TEXT_INPUT_CONTENT_FOREGROUND);
                    }
                }
                ,
                new Design("panel", "component") {
                    @Override
                    public void onDesign(Component component) {
                    }
                }
                ,
                new Design("layout panel", "panel") {
                    @Override
                    public void onDesign(Component component) {
                        setForeground(component, null);
                        setBackground(component, null);
                    }
                }
                ,
                new Design("window", "panel") {
                    @Override
                    public void onDesign(Component component) {
                    }
                }
                ,
                new Design("context menu", "window") {
                    @Override
                    public void onDesign(Component component) {
                    }
                }
                ,
                new Design("content panel", "panel") {
                    @Override
                    public void onDesign(Component component) {
                        setBackground(component, null);
                        setForeground(component, null);
                    }
                }
                ,
                new Design("button", "component") {
                    @Override
                    public void onDesign(Component component) {
                        setPadding(component, BUTTON_PADDING);
                    }
                }
                ,
                new Design("maximize button content", "content") {
                    @Override
                    public void onDesign(Component component) {
                        setForeground(component, MAXIMIZE_BUTTON_CONTENT_FOREGROUND);
                    }
                }
                ,
                new Design("minimize button content", "content") {
                    @Override
                    public void onDesign(Component component) {
                        setForeground(component, MINIMIZE_BUTTON_CONTENT_FOREGROUND);
                    }
                }
                ,
                new Design("close button", "button") {
                    @Override
                    public void onDesign(Component component) {
                        setPadding(component, CLOSE_BUTTON_PADDING);
                    }
                }
                ,
                new Design("close button content", "content") {
                    @Override
                    public void onDesign(Component component) {
                        setForeground(component, CLOSE_BUTTON_CONTENT_FOREGROUND);
                    }
                }
                ,
                new Design("scroll button", "button") {
                    @Override
                    public void onDesign(Component component) {
                        setPadding(component, SCROLL_BUTTON_PADDING);
                        setFixedSize(component, SCROLL_BUTTON_SIZE);
                    }
                }
                ,
                new Design("left scroll button content", "scroll button") {
                    @Override
                    public void onDesign(Component component) {
                        setForeground(component, LEFT_SCROLL_BUTTON_CONTENT_FOREGROUND);
                    }
                }
                ,
                new Design("right scroll button content", "scroll button") {
                    @Override
                    public void onDesign(Component component) {
                        setForeground(component, RIGHT_SCROLL_BUTTON_CONTENT_FOREGROUND);
                    }
                }
                ,
                new Design("up scroll button content", "scroll button") {
                    @Override
                    public void onDesign(Component component) {
                        setForeground(component, UP_SCROLL_BUTTON_CONTENT_FOREGROUND);
                    }
                }
                ,
                new Design("down scroll button content", "scroll button") {
                    @Override
                    public void onDesign(Component component) {
                        setForeground(component, DOWN_SCROLL_BUTTON_CONTENT_FOREGROUND);
                    }
                }
                ,
                new Design("spinner button", "button") {
                    @Override
                    public void onDesign(Component component) {
                        setPadding(component, SPINNER_BUTTON_PADDING);
                        setFixedSize(component, SPINNER_BUTTON_WIDTH, SPINNER_BUTTON_HEIGHT);
                    }
                }
                ,
                new Design("up spinner button content", "content") {
                    @Override
                    public void onDesign(Component component) {
                        setForeground(component, UP_SPINNER_BUTTON_CONTENT_FOREGROUND);
                    }
                }
                ,
                new Design("down spinner button content", "content") {
                    @Override
                    public void onDesign(Component component) {
                        setForeground(component, DOWN_SPINNER_BUTTON_CONTENT_FOREGROUND);
                    }
                }
                ,
                new Design("horizontal tab area close button", "close button") {
                    @Override
                    public void onDesign(Component component) {
                        setPadding(component, TAB_CLOSE_BUTTON_PADDING);
                    }
                }
                ,
                new Design("horizontal tab area header", "button") {
                    @Override
                    public void onDesign(Component component) {
                        setPadding(component, TAB_AREA_HEADER_PADDING);
                        setHorizontalSpacing(component, TAB_AREA_HEADER_SPACING);
                        setFixedSize(component, TAB_AREA_HEADER_SIZE);
                    }
                }
                ,
                new Design("horizontal tab area text", "singleline text content") {
                    @Override
                    public void onDesign(Component component) {
                        setFont(component, TAB_AREA_FONT);
                    }
                }
                ,
                new Design("decoration", "panel") {
                    @Override
                    public void onDesign(Component component) {
                        setBackground(component, null);
                        setForeground(component, null);
                    }
                }
                ,
                new Design("check box", "panel") {
                    @Override
                    public void onDesign(Component component) {
                        setPadding(component, CHECK_BOX_PADDING);
                        setFixedSize(component, CHECK_BOX_SIZE);
                        setForeground(component, CHECK_BOX_FOREGROUND);
                    }
                }
                ,
                new Design("radio button", "panel") {
                    @Override
                    public void onDesign(Component component) {
                        setPadding(component, RADIO_BUTTON_PADDING);
                        setFixedSize(component, RADIO_BUTTON_SIZE);
                        setForeground(component, RADIO_BUTTON_FOREGROUND);
                    }
                }
                ,
                new Design("menu", "panel") {
                    @Override
                    public void onDesign(Component component) {
                        setSpacing(component, MENU_SPACING);
                        setPadding(component, MENU_PADDING);
                    }
                }
                ,
                new Design("separator", "content") {
                    @Override
                    public void onDesign(Component component) {
                        setBackground(component, null);
                    }
                }
                ,
                new Design("horizontal separator", "separator") {
                    @Override
                    public void onDesign(Component component) {
                        setForeground(component, HORIZONTAL_SEPARATOR_FOREGROUND);
                    }
                }
                ,
                new Design("vertical separator", "separator") {
                    @Override
                    public void onDesign(Component component) {
                        setForeground(component, VERTICAL_SEPARATOR_FOREGROUND);
                    }
                }
                ,
                new Design("menu item description", "singleline text content") {
                    @Override
                    public void onDesign(Component component) {
                        setFont(component, MENU_ITEM_DESCRIPTION_FONT);
                    }
                }
                ,
                new Design("menu item shortcut", "singleline text content") {
                    @Override
                    public void onDesign(Component component) {
                        setFont(component, MENU_ITEM_SHORTCUT_FONT);
                    }
                }
                ,
                new Design("slider", "content") {
                    @Override
                    public void onDesign(Component component) {
                        setFixedSize(component, SLIDER_SIZE);
                    }
                }
                ,
                new Design("horizontal slider", "slider") {
                    @Override
                    public void onDesign(Component component) {
                        setForeground(component, HORIZONTAL_SLIDER_FOREGROUND);
                    }
                }
                ,
                new Design("vertical slider", "slider") {
                    @Override
                    public void onDesign(Component component) {
                        setForeground(component, VERTICAL_SLIDER_FOREGROUND);
                    }
                }
                ,
                new Design("split area", "panel") {
                    @Override
                    public void onDesign(Component component) {
                        setSpacing(component, SPLIT_AREA_SPACING);
                    }
                }
                ,
                new Design("seletion list", "panel") {
                    @Override
                    public void onDesign(Component component) {
                        setVerticalPadding(component, SELECTION_LIST_VERTICAL_PADDING);
                    }
                }
                ,
                new Design("selection list item", "singleline text content") {
                    @Override
                    public void onDesign(Component component) {
                        setBackground(component, SELECTION_LIST_ITEM_BACKGROUND);
                        setForeground(component, SELECTION_LIST_ITEM_FOREGROUND);
                        setHorizontalPadding(component, SELECTION_LIST_ITEM_HORIZONTAL_PADDING);
                        setVerticalPadding(component, SELECTION_LIST_ITEM_VERTICAL_PADDING);
                    }
                }
                ,
                new Design("combo box text", "singleline text content") {
                    @Override
                    public void onDesign(Component component) {
                        setPadding(component, COMBO_BOX_TEXT_PADDING);
                    }
                }
                ,
                new Design("combo box menu", "context menu") {
                    @Override
                    public void onDesign(Component component) {
                        setPadding(component, COMBO_BOX_MENU_PADDING);
                    }
                }
                ,
                new Design("combo box open button", "button") {
                    @Override
                    public void onDesign(Component component) {
                        setFixedSize(component, COMBO_BOX_BUTTON_SIZE);
                        setPadding(component, COMBO_BOX_BUTTON_PADDING);
                    }
                }
                ,
                new Design("combo box open button content", "content") {
                    @Override
                    public void onDesign(Component component) {
                        setForeground(component, COMBO_BOX_BUTTON_CONTENT_FOREGROUND);
                    }
                }
                ,
                new Design("toolkit decoration title", "singleline text content") {
                    @Override
                    public void onDesign(Component component) {
                        setFont(component, TITLE_BAR_FONT);
                    }
                }
                ,
                new Design("toolkit decoration title bar", "panel") {
                    @Override
                    public void onDesign(Component component) {
                        setForeground(component, null);
                        setFixedSize(component, TITLE_BAR_SIZE);
                        setPadding(component, TITLE_BAR_PADDING); 
                        setHorizontalSpacing(component, TITLE_BAR_SPACING);
                    }
                }
                ,
                new Design("toolkit decoration title bar button", "button") {
                    @Override
                    public void onDesign(Component component) {
                        setPadding(component, TITLE_BAR_BUTTON_PADDING); 
                    }
                }
                ,
                new Design("horizontal scroll bar content", "content") {
                    @Override
                    public void onDesign(Component component) {
                        setForeground(component, HORIZONTAL_SCROLL_BAR_FOREGROUND);
                    }
                }
                ,
                new Design("vertical scroll bar content", "content") {
                    @Override
                    public void onDesign(Component component) {
                        setForeground(component, VERTICAL_SCROLL_BAR_FOREGROUND);
                    }
                }
                ,
                new Design("split area content panel design", "content panel") {
                    @Override
                    public void onDesign(Component component) {
                        setForeground(component, COMMON_FOREGROUND);
                    }
                }
        }));
    }
    
    private static void setFixedSize(Component conponent, double size){
        setFixedSize(conponent, size, size);
    }
    
    private static void setFixedSize(Component component, double width, double height){
        SizePolicy h = getHorizontalSizePolicy(component);
        SizePolicy v = getVerticalSizePolicy(component);
        if(h instanceof FixedSizePolicy) ((FixedSizePolicy) h).setWidth(width);
        if(v instanceof FixedSizePolicy) ((FixedSizePolicy) v).setHeight(height);
    }
}
