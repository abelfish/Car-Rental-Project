package ea.project.rentalapp.service.exceptions;

public class InvalidInputException extends RuntimeException{
    public InvalidInputException(String message) {
        super(message);
    }
    public InvalidInputException() {
        super("Invalid input");
    }
}
