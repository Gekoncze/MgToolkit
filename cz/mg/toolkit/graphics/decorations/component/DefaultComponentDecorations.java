package cz.mg.toolkit.graphics.decorations.component;

import cz.mg.toolkit.component.Component;
import cz.mg.toolkit.component.containers.Panel;
import cz.mg.toolkit.component.contents.ImageContent;
import cz.mg.toolkit.component.contents.TextContent;
import cz.mg.toolkit.component.controls.*;
import cz.mg.toolkit.graphics.Decoration;
import cz.mg.toolkit.graphics.Font;
import cz.mg.toolkit.graphics.Graphics;
import cz.mg.toolkit.graphics.Image;
import cz.mg.toolkit.graphics.decorations.BackgroundColorDecoration;
import cz.mg.toolkit.graphics.decorations.ForegroundColorDecoration;
import cz.mg.toolkit.graphics.decorations.RectangleBorderDecoration;
import cz.mg.toolkit.graphics.decorations.RectangleDecoration;
import cz.mg.toolkit.utilities.annotations.ComponentDecoration;
import cz.mg.toolkit.utilities.annotations.ComponentDecorations;
import cz.mg.toolkit.utilities.text.Caret;
import cz.mg.toolkit.utilities.text.TextArrangement;
import cz.mg.toolkit.utilities.text.TextPart;
import static cz.mg.toolkit.graphics.decorations.BackgroundColorDecoration.getCurrentBackgroundColor;
import static cz.mg.toolkit.graphics.decorations.ForegroundColorDecoration.getCurrentForegroundColor;
import static cz.mg.toolkit.utilities.properties.PropertiesInterface.*;


@ComponentDecorations
public class DefaultComponentDecorations {
    @ComponentDecoration
    public static final Decoration COMMON_BACKGROUND = new BackgroundColorDecoration(new RectangleDecoration());

    @ComponentDecoration
    public static final Decoration COMMON_FOREGROUND = new ForegroundColorDecoration(new RectangleBorderDecoration());

    @ComponentDecoration
    public static final Decoration LEFT_SCROLL_BUTTON_CONTENT_FOREGROUND = new ForegroundColorDecoration(new Decoration() {
        @Override
        protected void onDraw(Graphics g, Component component) {
            double w = component.getWidth();
            double h = component.getHeight();
            g.drawLine(1.0*w, 0.0*h, 0.0*w, 0.5*h, -1, 0, 0, 0);
            g.drawLine(0.0*w, 0.5*h, 1.0*w, 1.0*h, 0, 0, -1, -1);
        }
    });

    @ComponentDecoration
    public static final Decoration RIGHT_SCROLL_BUTTON_CONTENT_FOREGROUND = new ForegroundColorDecoration(new Decoration() {
        @Override
        protected void onDraw(Graphics g, Component component) {
            double w = component.getWidth();
            double h = component.getHeight();
            g.drawLine(0.0*w, 0.0*h, 1.0*w, 0.5*h, 0, 0, -1, 0);
            g.drawLine(1.0*w, 0.5*h, 0.0*w, 1.0*h, -1, 0, 0, -1);
        }
    });

    @ComponentDecoration
    public static final Decoration UP_SCROLL_BUTTON_CONTENT_FOREGROUND = new ForegroundColorDecoration(new Decoration() {
        @Override
        protected void onDraw(Graphics g, Component component) {
            double w = component.getWidth();
            double h = component.getHeight();
            g.drawLine(0.0*w, 1.0*h, 0.5*w, 0.0*h, 0, -1, 0, 0);
            g.drawLine(0.5*w, 0.0*h, 1.0*w, 1.0*h, 0, 0, -1, -1);
        }
    });

    @ComponentDecoration
    public static final Decoration DOWN_SCROLL_BUTTON_CONTENT_FOREGROUND = new ForegroundColorDecoration(new Decoration() {
        @Override
        protected void onDraw(Graphics g, Component component) {
            double w = component.getWidth();
            double h = component.getHeight();
            g.drawLine(0.0*w, 0.0*h, 0.5*w, 1.0*h, 0, 0, 0, -1);
            g.drawLine(0.5*w, 1.0*h, 1.0*w, 0.0*h, 0, -1, -1, 0);
        }
    });

    @ComponentDecoration
    public static final Decoration MINIMIZE_BUTTON_CONTENT_FOREGROUND = new ForegroundColorDecoration(new Decoration() {
        @Override
        protected void onDraw(Graphics g, Component component) {
            double w = component.getWidth();
            double h = component.getHeight();
            g.drawLine(0.0*w, 1.0*h, 1.0*w, 1.0*h, 0, -1, -1, -1);
        }
    });

    @ComponentDecoration
    public static final Decoration MAXIMIZE_BUTTON_CONTENT_FOREGROUND = new ForegroundColorDecoration(new Decoration() {
        @Override
        protected void onDraw(Graphics g, Component component) {
            double w = component.getWidth();
            double h = component.getHeight();
            g.drawRectangleBorder(0.0*w, 0.0*h, 1.0*w, 1.0*h);
        }
    });

    @ComponentDecoration
    public static final Decoration CLOSE_BUTTON_CONTENT_FOREGROUND = new ForegroundColorDecoration(new Decoration() {
        @Override
        protected void onDraw(Graphics g, Component component) {
            double w = component.getWidth();
            double h = component.getHeight();
            g.drawLine(0.0*w, 0.0*h, 1.0*w, 1.0*h, 0, 0, -1, -1);
            g.drawLine(0.0*w, 1.0*h, 1.0*w, 0.0*h, 0, -1, -1, 0);
        }
    });

    @ComponentDecoration
    public static final Decoration UP_SPINNER_BUTTON_CONTENT_FOREGROUND = new ForegroundColorDecoration(new Decoration() {
        @Override
        protected void onDraw(Graphics g, Component component) {
            double w = component.getWidth();
            double h = component.getHeight();
            g.drawLine(0.0*w, 1.0*h, 0.5*w, 0.0*h, 0, -1, 0, 0);
            g.drawLine(0.5*w, 0.0*h, 1.0*w, 1.0*h, 0, 0, -1, -1);
            g.drawLine(0.0*w, 1.0*h, 1.0*w, 1.0*h, 0, -1, -1, -1);
        }
    });

    @ComponentDecoration
    public static final Decoration DOWN_SPINNER_BUTTON_CONTENT_FOREGROUND = new ForegroundColorDecoration(new Decoration() {
        @Override
        protected void onDraw(Graphics g, Component component) {
            double w = component.getWidth();
            double h = component.getHeight();
            g.drawLine(0.0*w, 0.0*h, 0.5*w, 1.0*h, 0, 0, 0, -1);
            g.drawLine(0.5*w, 1.0*h, 1.0*w, 0.0*h, 0, -1, -1, 0);
            g.drawLine(0.0*w, 0.0*h, 1.0*w, 0.0*h, 0, 0, -1, 0);
        }
    });

    @ComponentDecoration
    public static final Decoration COMBO_BOX_BUTTON_CONTENT_FOREGROUND = new ForegroundColorDecoration(new Decoration() {
        @Override
        protected void onDraw(Graphics g, Component component) {
            double w = component.getWidth();
            double h = component.getHeight();
            g.drawLine(0.0*w, 0.0*h, 0.5*w, 1.0*h, 0, 0, 0, -1);
            g.drawLine(0.5*w, 1.0*h, 1.0*w, 0.0*h, 0, -1, -1, 0);
            g.drawLine(0.0*w, 0.0*h, 1.0*w, 0.0*h, 0, 0, -1, 0);
        }
    });

    @ComponentDecoration
    public static final Decoration CHECK_BOX_FOREGROUND = new ForegroundColorDecoration(new Decoration() {
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

    @ComponentDecoration
    public static final Decoration RADIO_BUTTON_FOREGROUND = new ForegroundColorDecoration(new Decoration() {
        @Override
        protected void onDraw(Graphics g, Component component) {
            double w = component.getWidth();
            double h = component.getHeight();
            boolean s = ((RadioButton)component).isSelected();
            g.drawOvalBorder(0, 0, w, h);
            if(s) g.drawOval(0.2*w, 0.2*h, 0.6*w, 0.6*h);
        }
    });

    @ComponentDecoration
    public static final Decoration HORIZONTAL_SCROLL_BAR_FOREGROUND = new ForegroundColorDecoration(new Decoration(){
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

    @ComponentDecoration
    public static final Decoration VERTICAL_SCROLL_BAR_FOREGROUND = new ForegroundColorDecoration(new Decoration() {
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

    @ComponentDecoration
    public static final Decoration HORIZONTAL_SLIDER_FOREGROUND = new ForegroundColorDecoration(new Decoration() {
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

    @ComponentDecoration
    public static final Decoration VERTICAL_SLIDER_FOREGROUND = new ForegroundColorDecoration(new Decoration() {
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

    @ComponentDecoration
    public static final Decoration HORIZONTAL_SEPARATOR_FOREGROUND = new ForegroundColorDecoration(new Decoration() {
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

    @ComponentDecoration
    public static final Decoration VERTICAL_SEPARATOR_FOREGROUND = new ForegroundColorDecoration(new Decoration() {
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

    @ComponentDecoration
    public static final Decoration TEXT_CONTENT_BACKGROUND = new BackgroundColorDecoration(new Decoration() {
        @Override
        protected void onDraw(Graphics g, Component component) {
            COMMON_BACKGROUND.getInnerDecoration().draw(g, component);
        }
    });

    @ComponentDecoration
    public static final Decoration TEXT_CONTENT_FOREGROUND = new ForegroundColorDecoration(new Decoration() {
        @Override
        protected void onDraw(Graphics g, Component component) {
            TextContent content = (TextContent) component;
            TextArrangement arrangement = content.getTextModel().getTextArrangement();
            g.setFont(arrangement.getOptions().getFont());
            for(TextPart textPart : arrangement.getParts()){
                g.drawText(textPart.getText(), textPart.getX(), textPart.getY());
            }
        }
    });

    @ComponentDecoration
    public static final Decoration INTERACTIVE_TEXT_CONTENT_BACKGROUND = new BackgroundColorDecoration(new Decoration() {
        @Override
        protected void onDraw(Graphics g, Component component) {
            TEXT_CONTENT_BACKGROUND.getInnerDecoration().draw(g, component);

            TextContent content = (TextContent) component;
            TextArrangement arrangement = content.getTextModel().getTextArrangement();
            Caret beginCaret = arrangement.getBeginCaret();
            Caret endCaret = arrangement.getEndCaret();

            // draw selection if needed
            if(beginCaret.getCaret() != endCaret.getCaret()) {
                if(beginCaret.getCaret() >= endCaret.getCaret()) { Caret buffer = beginCaret; beginCaret = endCaret; endCaret = buffer; }
                g.setColor(getCurrentForegroundColor(content));

                double bx = beginCaret.getX();
                double by = beginCaret.getY();
                double ex = endCaret.getX();
                double ey = endCaret.getY();
                double lh = arrangement.getLineHeight();

                if(beginCaret.getRow() == endCaret.getRow()){
                    g.drawRectangle(bx, by, ex - bx, lh); // draw single line selection
                } else {
                    Font font = getFont(content);
                    int bcx = beginCaret.getColumn();
                    int bcy = beginCaret.getRow();
                    int ecx = endCaret.getColumn();
                    int ecy = endCaret.getRow();

                    // draw leading line selection
                    TextPart leadingLine = arrangement.getParts().get(bcy);
                    double lrw = font.getWidth(leadingLine.getText().substring(bcx));
                    double lrx = bx;
                    double lry = by;
                    g.drawRectangle(lrx, lry, lrw, lh);

                    // draw in-between lines
                    for(int i = bcy + 1; i <= ecy - 1; i++){
                        TextPart inbetweenLine = arrangement.getParts().get(i);
                        double irx = inbetweenLine.getX();
                        double iry = inbetweenLine.getY();
                        double irw = font.getWidth(inbetweenLine.getText());
                        g.drawRectangle(irx, iry, irw, lh);
                    }

                    // draw trailing line selection
                    TextPart trailingLine = arrangement.getParts().get(ecy);
                    double trw = font.getWidth(trailingLine.getText().substring(0, ecx));
                    double trx = ex - trw;
                    double tryy = ey;
                    g.drawRectangle(trx, tryy, trw, lh);
                }
            }
        }
    });

    @ComponentDecoration
    public static final Decoration INTERACTIVE_TEXT_CONTENT_FOREGROUND = new ForegroundColorDecoration(new Decoration() {
        @Override
        protected void onDraw(Graphics g, Component component) {
            TEXT_CONTENT_FOREGROUND.getInnerDecoration().draw(g, component);

            TextContent content = (TextContent) component;
            TextArrangement arrangement = content.getTextModel().getTextArrangement();
            Caret beginCaret = arrangement.getBeginCaret();
            Caret endCaret = arrangement.getEndCaret();

            if(beginCaret.getCaret() != endCaret.getCaret()){
                if(beginCaret.getCaret() >= endCaret.getCaret()) { Caret buffer = beginCaret; beginCaret = endCaret; endCaret = buffer; }
                g.setColor(getCurrentBackgroundColor(content));
                Font font = getFont(content);

                int bcx = beginCaret.getColumn();
                int bcy = beginCaret.getRow();
                int ecx = endCaret.getColumn();
                int ecy = endCaret.getRow();

                if(beginCaret.getRow() == endCaret.getRow()){
                    TextPart line = arrangement.getParts().get(bcy);
                    double x = line.getX() + font.getWidth(line.getText().substring(0, bcx));
                    double y = line.getY();
                    g.drawText(line.getText().substring(bcx, ecx), x, y);
                } else {
                    // draw leading line
                    TextPart leadingLine = arrangement.getParts().get(bcy);
                    double llx = leadingLine.getX() + font.getWidth(leadingLine.getText().substring(0, bcx));
                    double lly = leadingLine.getY();
                    g.drawText(leadingLine.getText().substring(bcx), llx, lly);

                    // draw in-between lines
                    for(int i = bcy + 1; i <= ecy - 1; i++){
                        TextPart inbetweenLine = arrangement.getParts().get(i);
                        double ilx = inbetweenLine.getX();
                        double ily = inbetweenLine.getY();
                        g.drawText(inbetweenLine.getText(), ilx, ily);
                    }

                    // draw trailing line
                    TextPart trailingLine = arrangement.getParts().get(ecy);
                    double tlx = trailingLine.getX();
                    double tly = trailingLine.getY();
                    g.drawText(trailingLine.getText().substring(0, ecx), tlx, tly);
                }
            }

            if(content.hasKeyboardFocus()){
                double x = arrangement.getEndCaret().getX();
                double y = arrangement.getEndCaret().getY();
                double lh = arrangement.getLineHeight();
                g.setColor(getContrastColor(content));
                g.drawLine(x, y, x, y + lh);
            }
        }
    });

    @ComponentDecoration
    public static final Decoration TEXT_INPUT_CONTENT_FOREGROUND = new ForegroundColorDecoration(new Decoration() {
        @Override
        protected void onDraw(Graphics g, Component component) {
            INTERACTIVE_TEXT_CONTENT_FOREGROUND.getInnerDecoration().draw(g, component);

            // draw placeholder text if needed
            TextInput.TextContent content = (TextInput.TextContent) component;
            TextArrangement arrangement = content.getPlaceholderTextModel().getTextArrangement();
            if(!content.hasKeyboardFocus() && content.getTextModel().getText().length() <= 0){
                g.setFont(arrangement.getOptions().getFont());
                g.setTransparency(0.5);
                g.setColor(getCurrentForegroundColor(content));
                for(TextPart textPart : arrangement.getParts()){
                    g.drawText(textPart.getText(), textPart.getX(), textPart.getY());
                }
                g.setTransparency(1.0);
            }
        }
    });

    @ComponentDecoration
    public static final Decoration SELECTION_LIST_ITEM_BACKGROUND = new BackgroundColorDecoration(new Decoration() {
        @Override
        protected void onDraw(Graphics g, Component component) {
            SelectionList.ListItem listItem = (SelectionList.ListItem) component;
            g.setColor(listItem.isSelected() ? getCurrentForegroundColor(listItem) : getCurrentBackgroundColor(listItem));
            TEXT_CONTENT_BACKGROUND.getInnerDecoration().draw(g, component);
        }
    });

    @ComponentDecoration
    public static final Decoration SELECTION_LIST_ITEM_FOREGROUND = new ForegroundColorDecoration(new Decoration() {
        @Override
        protected void onDraw(Graphics g, Component component) {
            SelectionList.ListItem listItem = (SelectionList.ListItem) component;
            g.setColor(!listItem.isSelected() ? getCurrentForegroundColor(listItem) : getCurrentBackgroundColor(listItem));
            TEXT_CONTENT_FOREGROUND.getInnerDecoration().draw(g, component);
        }
    });

    @ComponentDecoration
    public static final Decoration IMAGE_CONTENT_FOREGROUND = new ForegroundColorDecoration(new Decoration() {
        @Override
        protected void onDraw(Graphics g, Component component) {
            ImageContent content = (ImageContent) component;
            Image image = content.getImage();
            if(image != null) g.drawImage(image, 0, 0, component.getWidth(), component.getHeight());
        }
    });
}
