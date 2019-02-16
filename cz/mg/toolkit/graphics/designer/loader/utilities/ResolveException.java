package cz.mg.toolkit.graphics.designer.loader.utilities;

import cz.mg.parser.utilities.Substring;
import cz.mg.parser.utilities.TraceableException;


public class ResolveException extends TraceableException {
    public ResolveException(Substring element, String message) {
        super(element, message);
    }
}
