package cz.mg.toolkit.component.wrappers;

import cz.mg.collections.array.Array2D;
import cz.mg.toolkit.component.Component;
import cz.mg.toolkit.component.containers.ContentPanel;
import cz.mg.toolkit.component.containers.Panel;
import cz.mg.toolkit.environment.cursors.ArrowCursor;
import cz.mg.toolkit.environment.cursors.MoveCursor;
import cz.mg.toolkit.event.adapters.GraphicsDrawAdapter;
import cz.mg.toolkit.event.adapters.LocalMouseButtonAdapter;
import cz.mg.toolkit.event.adapters.MouseButtonAdapter;
import cz.mg.toolkit.event.adapters.MouseMotionAdapter;
import cz.mg.toolkit.event.events.MouseButtonEvent;
import cz.mg.toolkit.event.events.MouseMotionEvent;
import cz.mg.toolkit.utilities.ComponentFactory;
import cz.mg.toolkit.graphics.Graphics;
import cz.mg.toolkit.layout.layouts.GridLayout;
import cz.mg.toolkit.layout.layouts.GridLayout.Column;
import cz.mg.toolkit.layout.layouts.GridLayout.Row;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;
import cz.mg.toolkit.utilities.sizepolices.FillParentSizePolicy;


public class SplitArea extends Panel {
    private final GridLayout grid = new GridLayout(1, 1, new ColumnFactory(), new RowFactory());
    private Array2D<Panel> contentPanels = new Array2D<>(1, 1);
    private final ContentPanelFactory contentPanelFactory;
    
    private Column c1, c2;
    private Row r1, r2;
    private double lastMouseX, lastMouseY;
    
    public SplitArea() {
        this(new ContentPanelFactory());
    }
    
    public SplitArea(ContentPanelFactory contentPanelFactory) {
        this.contentPanelFactory = contentPanelFactory;
        initComponent();
        addEventListeners();
    }

    private void initComponent() {
        setLayout(grid);
        setSizePolicy(this, new FillParentSizePolicy());
        updateContentPanelArray();
    }

    public final Array2D<Panel> getContentPanels() {
        return contentPanels;
    }
    
    public final int getColumnCount(){
        return grid.getColumns().count();
    }
    
    public final int getRowCount(){
        return grid.getRows().count();
    }
    
    public final void setColumnCount(int columnCount){
        grid.setColumnCount(columnCount);
        updateContentPanelArray();
    }
    
    public final void setRowCount(int rowCount){
        grid.setRowCount(rowCount);
        updateContentPanelArray();
    }
    
    private void updateContentPanelArray(){
        if(grid.getColumns().count() == contentPanels.getColumnCount() && grid.getRows().count() == contentPanels.getRowCount()) return;
        Array2D<Panel> newContentPanels = new Array2D<>(grid.getColumns().count(), grid.getRows().count());
        for(int x = 0; x < newContentPanels.getColumnCount(); x++){
            for(int y = 0; y < newContentPanels.getRowCount(); y++){
                Panel contentPanel = null;
                if(x < contentPanels.getColumnCount() && y < contentPanels.getRowCount()) contentPanel = contentPanels.get(x, y);
                if(contentPanel == null) contentPanel = createContentPanel(x, y);
                newContentPanels.set(x, y, contentPanel);
            }
        }
        contentPanels = newContentPanels;
    }
    
    private Panel createContentPanel(int cx, int cy){
        Panel contentPanel = contentPanelFactory.create();
        setCell(contentPanel, cx, cy);
        contentPanel.setParent(this);
        return contentPanel;
    }

    private void addEventListeners() {
        getEventListeners().addLast(new LocalMouseButtonAdapter() {
            @Override
            public void onMouseButtonEventLeave(MouseButtonEvent e) {
                if(!wasLeftButton(e) || !wasPressed(e)) return;        
                if((c1 != null && c2 != null) || (r1 != null && r2 != null)) requestMouseFocus();
            }
        });
        
        getEventListeners().addLast(new MouseMotionAdapter() {
            @Override
            public void onMouseMotionEventEnter(MouseMotionEvent e) {
                lastMouseX = getX(e);
                lastMouseY = getY(e);
                
                if(!hasMouseFocus()){
                    c1 = null;
                    c2 = null;
                    r1 = null;
                    r2 = null;

                    double hs = getHorizontalSpacing(SplitArea.this);
                    double vs = getVerticalSpacing(SplitArea.this);

                    for(Column column : grid.getColumns()){
                        if(isInsideOuterBounds(lastMouseX, lastMouseY, column, hs, vs)){
                            if(c1 == null) c1 = column;
                            else c2 = column;
                        }
                    }

                    for(Row row : grid.getRows()){
                        if(isInsideOuterBounds(lastMouseX, lastMouseY, row, hs, vs)){
                            if(r1 == null) r1 = row;
                            else r2 = row;
                        }
                    }
                    if((c1 != null && c2 != null) || (r1 != null && r2 != null)){
                        getWindow().setCursor(new MoveCursor());
                    } else {
                        getWindow().setCursor(new ArrowCursor());
                    }
                } else if((c1 != null && c2 != null) || (r1 != null && r2 != null)) redraw();
            }
        });
        
        getEventListeners().addLast(new MouseButtonAdapter() {
            @Override
            public void onMouseButtonEventEnter(MouseButtonEvent e) {
                if(!wasLeftButton(e) || !wasReleased(e)) return;
                if(!hasMouseFocus()) return;
                releaseMouseFocus();
                if(c1 != null && c2 != null) updateHorizontalWeights(c1, c2, getX(e));
                if(r1 != null && r2 != null) updateVerticalWeights(r1, r2, getY(e));
                relayout();
            }
            
            private void updateHorizontalWeights(Component o1, Component o2, double p){
                double w1 = getHorizontalWeight(o1);
                double w2 = getHorizontalWeight(o2);
                double w = w1 + w2;
                double p1 = o1.getX();
                double p2 = o2.getX() + o2.getWidth();
                if(p < p1) p = p1;
                if(p > p2) p = p2;
                p -= p1;
                double delta = Math.abs(p2 - p1);
                if(delta <= 0) return;
                double tw1 = (double)p / (double)delta;
                double tw2 = 1.0 - tw1;
                setHorizontalWeight(o1, w*tw1);
                setHorizontalWeight(o2, w*tw2);
            }
            
            private void updateVerticalWeights(Component o1, Component o2, double p){
                double w1 = getVerticalWeight(o1);
                double w2 = getVerticalWeight(o2);
                double w = w1 + w2;
                double p1 = o1.getY();
                double p2 = o2.getY() + o2.getHeight();
                if(p < p1) p = p1;
                if(p > p2) p = p2;
                p -= p1;
                double delta = Math.abs(p2 - p1);
                if(delta <= 0) return;
                double tw1 = (double)p / (double)delta;
                double tw2 = 1.0 - tw1;
                setVerticalWeight(o1, w*tw1);
                setVerticalWeight(o2, w*tw2);
            }
        });
        
        getEventListeners().addLast(new GraphicsDrawAdapter() {
            @Override
            public void onDrawEventLeave(Graphics g) {
                if(!hasMouseFocus()) return;
                if(c1 != null && c2 != null) {
                    double x = lastMouseX;
                    double x1 = c1.getX();
                    double x2 = c2.getX() + c2.getWidth();
                    if(x < x1) x = x1;
                    if(x > x2) x = x2;
                    g.setColor(getHighlightedForegroundColor(SplitArea.this));
                    g.drawLine(x, 0, x, getHeight());
                }
                if(r1 != null && r2 != null){
                    double y = lastMouseY;
                    double y1 = r1.getY();
                    double y2 = r2.getY() + r2.getHeight();
                    if(y < y1) y = y1;
                    if(y > y2) y = y2;
                    g.setColor(getHighlightedForegroundColor(SplitArea.this));
                    g.drawLine(0, y, getWidth(), y);
                }
            }
        });
    }
    
    private static boolean isInsideOuterBounds(double px, double py, Column column, double hs, double vs){
        double x = column.getX() - hs;
        double w = column.getWidth() + 2*hs;
        return px >= x && px < (x + w);
    }

    private static boolean isInsideOuterBounds(double px, double py, Row row, double hs, double vs){
        double y = row.getY() - vs;
        double h = row.getHeight() + 2*vs;
        return py >= y && py < (y + h);
    }
    
    private static class ColumnFactory extends GridLayout.ColumnFactory {
        @Override
        public Column create() {
            Column column = super.create();
            setHorizontalSizePolicy(column, new FillParentSizePolicy());
            return column;
        }
    }
    
    private static class RowFactory extends GridLayout.RowFactory {
        @Override
        public Row create() {
            Row row = super.create();
            setVerticalSizePolicy(row, new FillParentSizePolicy());
            return row;
        }
    }
    
    public static class ContentPanelFactory implements ComponentFactory<Panel> {
        @Override
        public Panel create() {
            return new ContentPanel();
        }
    }
}
