package ea.project.rentalapp.service.adapters;

import ea.project.rentalapp.domain.Employee;
import ea.project.rentalapp.service.dto.EmployeeDto;

import java.util.List;

public class EmployeeAdapter {
    public static EmployeeDto toDto(Employee employee) {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setEmployeeId(employee.getEmployeeId());
        employeeDto.setName(employee.getName());
        employeeDto.setEmail(employee.getEmail());
        return employeeDto;
    }
    public static Employee toDomain(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setEmployeeId(employeeDto.getEmployeeId());
        employee.setName(employeeDto.getName());
        employee.setEmail(employeeDto.getEmail());
        return employee;
    }
    public static List<EmployeeDto> toDtoList(List<Employee> employeeList) {
        return employeeList.stream().map(EmployeeAdapter::toDto).toList();
    }
}
