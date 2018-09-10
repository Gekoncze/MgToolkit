package cz.mg.toolkit.component.contents;

import cz.mg.collections.Collection;
import cz.mg.collections.list.List;
import cz.mg.collections.list.quicklist.QuickList;
import cz.mg.toolkit.component.DrawableContent;
import cz.mg.toolkit.graphics.Font;
import cz.mg.toolkit.graphics.Graphics;
import cz.mg.toolkit.event.adapters.GraphicsDrawAdapter;
import cz.mg.toolkit.layout.reshapes.Reshape;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;


public class MultilineTextContent extends DrawableContent {
    private static final Font DEFAULT_FONT = new Font("Serif", 18, Font.Style.REGULAR);
    
    private final List<String> lines = new QuickList<>();
    private Font font;

    public MultilineTextContent() {
        this(null, DEFAULT_FONT);
    }

    public MultilineTextContent(String text) {
        this(text, DEFAULT_FONT);
    }
    
    public MultilineTextContent(String text, Font font) {
        setText(text);
        this.font = font;
        addEventListeners();
    }
    
    private void addEventListeners(){
        getEventListeners().addLast(new GraphicsDrawAdapter() {
            @Override
            public void onDrawEventLeave(Graphics g) {
                g.setColor(getCurrentForegroundColor());
                g.setFont(font);
                double y = getVerticalTextPosition();
                for(String line : lines){
                    double x = getHorizontalLinePosition(line);
                    g.drawText(line, x, y);
                    y += font.getHeight();
                }
            }
        });
    }
    
    protected double getHorizontalLinePosition(String line){
        return Reshape.align(getWidth(), getFont().getWidth(line), getHorizontalContentAlignment(this));
    }
    
    protected double getHorizontalTextPosition(){
        return Reshape.align(getWidth(), getTextWidth(), getHorizontalContentAlignment(this));
    }
    
    protected double getVerticalTextPosition(){
        return Reshape.align(getHeight(), getTextHeight(), getVerticalContentAlignment(this));
    }
    
    private double getTextWidth(){
        double maxWidth = 0;
        for(String line : lines) maxWidth = Math.max(maxWidth, getFont().getWidth(line));
        return maxWidth;
    }
    
    private double getTextHeight(){
        return getFont().getHeight() * lines.count();
    }
    
    @Override
    public final double getPrefferedWidth() {
        double maxWidth = 0;
        for(String line : lines){
            maxWidth = Math.max(maxWidth, font.getWidth(line));
        }
        return maxWidth;
    }

    @Override
    public final double getPrefferedHeight() {
        return font.getHeight() * lines.count();
    }

    public final List<String> getLines() {
        return lines;
    }
    
    public final void setLines(Collection<String> lines){
        this.lines.clear();
        for(String line : lines) this.lines.addLast(line);
    }

    public final String getText() {
        return lines.toString("\n");
    }

    public final void setText(String text) {
        lines.clear();
        if(text == null){
            lines.addLast("");
        } else {
            int begin = 0;
            for(int i = 0; i < text.length(); i++){
                char ch = text.charAt(i);
                if(ch == '\n'){
                    int end = i;
                    lines.addLast(text.substring(begin, end));
                    begin = end + 1;
                }
            }
            if(begin < text.length()) lines.addLast(text.substring(begin));
            else lines.addLast("");
        }
    }

    public final Font getFont() {
        return font;
    }

    public final void setFont(Font font) {
        this.font = font;
    }
    
    public final void setFontSize(int size) {
        font = new Font(font.getName(), size, font.getStyle());
    }
}
