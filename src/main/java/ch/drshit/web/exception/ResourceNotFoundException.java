package ch.drshit.web.exception;

/**
 * Created by timo on 19.11.16.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

}
