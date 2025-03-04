package eddie.project.cinemabookingsystemgenericdao.exception;


public class DatabaseException extends RuntimeException {
    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
