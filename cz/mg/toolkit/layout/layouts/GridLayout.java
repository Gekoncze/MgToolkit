package cz.mg.toolkit.layout.layouts;

import cz.mg.collections.list.List;
import cz.mg.collections.list.quicklist.QuickList;
import cz.mg.toolkit.component.Component;
import cz.mg.toolkit.component.Container;
import cz.mg.toolkit.component.Content;
import cz.mg.toolkit.event.events.BeforeLayoutEvent;
import cz.mg.toolkit.utilities.ComponentFactory;
import cz.mg.toolkit.layout.Layout;
import cz.mg.toolkit.layout.reshapes.HorizontalLinearReshape;
import cz.mg.toolkit.layout.reshapes.HorizontalOverlayReshape;
import cz.mg.toolkit.layout.reshapes.Reshape;
import cz.mg.toolkit.layout.reshapes.VerticalLinearReshape;
import cz.mg.toolkit.layout.reshapes.VerticalOverlayReshape;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;


public class GridLayout extends Layout {
    private static final BeforeLayoutEvent BEFORE_LAYOUT_EVENT = new BeforeLayoutEvent();
    
    private final List<Column> columns = new QuickList<>();
    private final List<Row> rows = new QuickList<>();
    private final ColumnFactory columnFactory;
    private final RowFactory rowFactory;
    
    public GridLayout() {
        this(new ColumnFactory(), new RowFactory());
    }
    
    public GridLayout(ColumnFactory columnFactory, RowFactory rowFactory) {
        this.columnFactory = columnFactory;
        this.rowFactory = rowFactory;
    }

    public GridLayout(int columnCount, int rowCount) {
        this(columnCount, rowCount, new ColumnFactory(), new RowFactory());
    }
    
    public GridLayout(int columnCount, int rowCount, ColumnFactory columnFactory, RowFactory rowFactory) {
        this.rowFactory = rowFactory;
        this.columnFactory = columnFactory;
        for(int x = 0; x < columnCount; x++) columns.addLast(columnFactory.create());
        for(int y = 0; y < rowCount; y++) rows.addLast(rowFactory.create());
    }

    public final List<Column> getColumns() {
        return columns;
    }

    public final List<Row> getRows() {
        return rows;
    }
    
    public final void setColumnCount(int columnCount){
        while(columnCount < columns.count()) columns.removeLast();
        while(columnCount > columns.count()) columns.addLast(columnFactory.create());
    }
    
    public final void setRowCount(int rowCount){
        while(rowCount < rows.count()) rows.removeLast();
        while(rowCount > rows.count()) rows.addLast(rowFactory.create());
    }

    public final RowFactory getRowFactory() {
        return rowFactory;
    }

    public final ColumnFactory getColumnFactory() {
        return columnFactory;
    }

    @Override
    public final void onBeforeLayoutLeave(Container parent) {
        for(Column column : columns){
            setHidden(column, true);
            column.setContentWidth(0);
        }
        
        for(Row row : rows){
            setHidden(row, true);
            row.setContentHeight(0);
        }
        
        for(Component component : parent.getChildren()) {
            if(isHidden(component)) continue;
            int x = getColumn(component);
            int y = getRow(component);
            int xs = getColumnSpan(component);
            int ys = getRowSpan(component);
            boolean xo = isHorizontallyOptional(component);
            boolean yo = isVerticallyOptional(component);
            for(int ix = x; ix < x+xs; ix++){
                for(int iy = y; iy < y+ys; iy++){
                    Column column = columns.get(ix);
                    Row row = rows.get(iy);
                    if(!xo) setHidden(column, false);
                    if(!yo) setHidden(row, false);
                    if(!xo) column.setContentWidth(Math.max(column.getContentWidth(), getMinWidth(component) / xs));
                    if(!yo) row.setContentHeight(Math.max(row.getContentHeight(), getMinHeight(component) / ys));
                }
            }
        }
        
        for(Column column : columns) column.sendEvent(BEFORE_LAYOUT_EVENT);
        for(Row row : rows) row.sendEvent(BEFORE_LAYOUT_EVENT);
    }

    @Override
    public final void reshapeComponents(Container parent) {
        HorizontalLinearReshape.reshape(parent, columns);
        VerticalLinearReshape.reshape(parent, rows);
        
        for(Component component : parent.getChildren()){
            if(isHidden(component)) continue;
            
            int gx = getColumn(component);
            int gy = getRow(component);
            int xs = getColumnSpan(component);
            int ys = getRowSpan(component);
            
            Column beginColumn = columns.get(gx);
            Column endColumn = columns.get(gx+xs-1);
            Row beginRow = rows.get(gy);
            Row endRow = rows.get(gy+ys-1);
            
            double availableWidth = endColumn.getX() + endColumn.getWidth() - beginColumn.getX();
            double availableHeight = endRow.getY() + endRow.getHeight() - beginRow.getY();
            
            HorizontalOverlayReshape.resize(availableWidth, component);
            VerticalOverlayReshape.resize(availableHeight, component);
            
            double x = beginColumn.getX() + Reshape.align(availableWidth, component.getWidth(), getHorizontalAlignment(component));
            double y = beginRow.getY() + Reshape.align(availableHeight, component.getHeight(), getVerticalAlignment(component));
            
            component.setPosition(x, y);
        }
    }

    @Override
    public final double computeMinWidth(Container parent) {
        return HorizontalLinearReshape.computeWrapWidth(parent, columns);
    }

    @Override
    public final double computeMinHeight(Container parent) {
        return VerticalLinearReshape.computeWrapHeight(parent, rows);
    }
    
    public static class Column extends Content {
    }
    
    public static class Row extends Content {
    }
    
    public static class ColumnFactory implements ComponentFactory<Column> {
        @Override
        public Column create() {
            return new Column();
        }
    }
    
    public static class RowFactory implements ComponentFactory<Row> {
        @Override
        public Row create() {
            return new Row();
        }
    }
}
