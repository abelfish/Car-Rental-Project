package ea.project.carfleet.service.exceptions;

public class CarNotFoundException extends RuntimeException {
    public CarNotFoundException(String message) {
        super(message);
    }
    public CarNotFoundException() {
        super("Car not found");
    }

}
