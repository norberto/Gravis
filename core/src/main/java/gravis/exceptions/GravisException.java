package gravis.exceptions;

import gravis.main.Gravis;

/**
 * Created by GreenFigure on 5/10/2015.
 */
public class GravisException extends Exception{
    public GravisException() {
        super();
    }

    public GravisException(String message) {
        super(message);
    }

    public GravisException(String message, Throwable clause) {
        super(message, clause);
    }

    public GravisException(Throwable clause) {
        super(clause);
    }
}
