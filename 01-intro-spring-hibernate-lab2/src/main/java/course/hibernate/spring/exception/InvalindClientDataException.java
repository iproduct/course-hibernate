package course.hibernate.spring.exception;

import java.util.List;

public class InvalindClientDataException extends RuntimeException{
    private List<String> violations = List.of();
    public InvalindClientDataException() {
    }

    public InvalindClientDataException(String message) {
        super(message);
    }

    public InvalindClientDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalindClientDataException(Throwable cause) {
        super(cause);
    }

    public InvalindClientDataException(List<String> violations) {
        this.violations = violations;
    }

    public InvalindClientDataException(String message, List<String> violations) {
        super(message);
        this.violations = violations;
    }

    public InvalindClientDataException(String message, Throwable cause, List<String> violations) {
        super(message, cause);
        this.violations = violations;
    }

    public InvalindClientDataException(Throwable cause, List<String> violations) {
        super(cause);
        this.violations = violations;
    }
}
