package cz.mg.toolkit.graphics.images;

import cz.mg.toolkit.graphics.Graphics;
import cz.mg.toolkit.graphics.Image;


public abstract class VectorImage implements Image {
    public abstract void onDraw(Graphics g);
}
