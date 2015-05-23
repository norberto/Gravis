package gravis.exceptions;

/**
 * Created by GreenFigure on 5/10/2015.
 */
public class InvalidFileException extends GravisException {
    public InvalidFileException(){
        super();
    }

    public InvalidFileException(String message) {
        super(message);
    }

    public InvalidFileException(String message, Throwable clause) {
        super(message, clause);
    }

    public InvalidFileException(Throwable clause) {
        super(clause);
    }

}
