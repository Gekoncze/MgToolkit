package cz.mg.toolkit.component.controls.buttons.special;

import cz.mg.toolkit.component.controls.buttons.ImageButton;
import cz.mg.toolkit.event.adapters.GraphicsDrawAdapter;
import cz.mg.toolkit.graphics.Graphics;


public class CloseButton extends ImageButton {
    public CloseButton() {
        getImageContent().getEventListeners().addFirst(new GraphicsDrawAdapter() {
            @Override
            public void onDrawEventLeave(Graphics g) {
                g.setColor(getCurrentForegroundColor());
            }
        });
    }
}
