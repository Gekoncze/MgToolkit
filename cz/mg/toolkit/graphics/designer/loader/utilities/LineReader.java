package cz.mg.toolkit.graphics.designer.loader.utilities;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.collections.list.chainlist.ChainListItem;
import cz.mg.parser.entity.Line;


public class LineReader {
    private final ChainList<Line> lines;
    private ChainListItem<Line> position;

    public LineReader(ChainList<Line> lines) {
        this.lines = lines;
        position = lines.getFirstItem();
    }

    public boolean canTake(){
        return position != null;
    }

    public Line take(){
        if(position == null) return null;
        Line line = position.getData();
        position = position.getNextItem();
        return line;
    }

    public Line takeChildOptional(){
        if(position == null) return null;
        int lastIndentation = getLastIndentation();
        int currentIndentation = getNextIndentation();
        if(currentIndentation == (lastIndentation + 1)) return take();
        else return null;
    }

    public Line takeSiblingOptional(){
        if(position == null) return null;
        int lastIndentation = getLastIndentation();
        int currentIndentation = getNextIndentation();
        if(currentIndentation == lastIndentation) return take();
        else return null;
    }

    public Line takeLevelOptional(int level){
        if(position == null) return null;
        int currentIndentation = getNextIndentation();
        if(currentIndentation == level) return take();
        else return null;
    }

    public void backoff(){
        if(position == null) position = lines.getLastItem();
        else position = position.getPreviousItem();
    }

    public int getLastIndentation(){
        if(position == null) return lines.getLast().getIndentation();
        else if(position.getPrevious() != null) return position.getPrevious().getIndentation();
        else return 0;
    }

    public int getNextIndentation(){
        if(position == null) return 0;
        else return position.getData().getIndentation();
    }

    public int getNextLineNumber(){
        if(position == null) return lines.count();
        Line current = position.getData();
        int i = 0;
        for(Line line : lines){
            if(current == line) return i;
            i++;
        }
        throw new RuntimeException("Foreign line in line reader.");
    }

    public void readNoMoreChildren(){
        if(getNextIndentation() > getLastIndentation()) throw new ComposeException(position.getData().getContent(), "Unexpected child block.");
    }
}
