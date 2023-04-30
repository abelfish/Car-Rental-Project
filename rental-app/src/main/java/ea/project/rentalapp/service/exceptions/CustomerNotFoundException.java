package ea.project.rentalapp.service.exceptions;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(String message) {
        super(message);
    }
    public CustomerNotFoundException() {
        super("Customer not found");
    }
}
