package cz.mg.toolkit.utilities.text;

import cz.mg.toolkit.graphics.Font;


public interface WrapableTextModel extends TextModel {
    public boolean isWrapable(Font font, double width);
    public void wrap(Font font, double width);
}
