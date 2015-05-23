package gravis.exceptions;

/**
 * Created by GreenFigure on 5/10/2015.
 */
public class FileHandlingException extends GravisException {

    public FileHandlingException() {
        super();
    }

    public FileHandlingException(String message) {
        super(message);
    }

    public FileHandlingException(String message, Throwable clause) {
        super(message, clause);
    }

    public FileHandlingException(Throwable clause) {
        super(clause);
    }
}
