package cz.mg.toolkit.graphics.designer.loader.utilities;

import cz.mg.parser.utilities.Substring;
import cz.mg.parser.utilities.TraceableException;


public class ComposeException extends TraceableException {
    public ComposeException(Substring element, String message) {
        super(element, message);
    }
}
