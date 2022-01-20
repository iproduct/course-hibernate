package course.hibernate.spring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ClientEntityDataException extends RuntimeException {
    private List<String> vilations = new ArrayList<>();

    public ClientEntityDataException() {
    }

    public ClientEntityDataException(String message) {
        super(message);
    }

    public ClientEntityDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClientEntityDataException(Throwable cause) {
        super(cause);
    }

    public ClientEntityDataException(List<String> vilations) {
        this.vilations = vilations;
    }

    public ClientEntityDataException(String message, List<String> vilations) {
        super(message);
        this.vilations = vilations;
    }

    public ClientEntityDataException(String message, Throwable cause, List<String> vilations) {
        super(message, cause);
        this.vilations = vilations;
    }

    public ClientEntityDataException(Throwable cause, List<String> vilations) {
        super(cause);
        this.vilations = vilations;
    }

    public List<String> getVilations() {
        return vilations;
    }

}
