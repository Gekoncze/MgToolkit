package cz.mg.toolkit.utilities;

import cz.mg.toolkit.component.Component;


public interface ComponentFactory<T extends Component> {
    public T create();
}
