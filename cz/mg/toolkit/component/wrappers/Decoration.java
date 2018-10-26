package cz.mg.toolkit.component.wrappers;

import cz.mg.toolkit.component.containers.Wrapper;
import cz.mg.toolkit.graphics.images.BitmapImage;
import static cz.mg.toolkit.utilities.properties.SimplifiedPropertiesInterface.*;
import cz.mg.toolkit.utilities.sizepolices.FillParentSizePolicy;


public abstract class Decoration extends Wrapper {
    public Decoration() {
        setSizePolicy(this, new FillParentSizePolicy());
    }
    
    public abstract void setIcon(BitmapImage icon);
    public abstract void setTitle(String title);
}
