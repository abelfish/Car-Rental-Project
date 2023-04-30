package ea.project.rentalapp.service.exceptions;

public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException(String employeeNotFound) {
        super(employeeNotFound);

    }
    public EmployeeNotFoundException(){
        super("Employee not found");
    }
}
