package ea.project.rentalapp.service.implementations;

import ea.project.rentalapp.dao.CustomerDao;
import ea.project.rentalapp.dao.EmployeeDao;
import ea.project.rentalapp.dao.ReservationDao;
import ea.project.rentalapp.domain.Customer;
import ea.project.rentalapp.domain.Employee;
import ea.project.rentalapp.domain.PaymentInfo;
import ea.project.rentalapp.domain.Reservation;
import ea.project.rentalapp.domain.enums.PaymentStatus;
import ea.project.rentalapp.domain.enums.ReservationStatus;
import ea.project.rentalapp.service.CarService;
import ea.project.rentalapp.service.CustomerService;
import ea.project.rentalapp.service.EmployeeService;
import ea.project.rentalapp.service.adapters.EmployeeAdapter;
import ea.project.rentalapp.service.adapters.PaymentInfoAdapter;
import ea.project.rentalapp.service.adapters.ReservationAdapter;
import ea.project.rentalapp.service.dto.CarDto;
import ea.project.rentalapp.service.dto.EmployeeDto;
import ea.project.rentalapp.service.dto.ReservationDto;
import ea.project.rentalapp.service.exceptions.CustomerNotFoundException;
import ea.project.rentalapp.service.exceptions.EmployeeNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeDao employeeDao;
    private final ReservationDao reservationDao;
    private final CarService carService;
    private final CustomerDao customerDao;

    public EmployeeServiceImpl(EmployeeDao employeeDao, ReservationDao reservationDao, CarService carService, CustomerDao customerDao) {
        this.employeeDao = employeeDao;
        this.reservationDao = reservationDao;
        this.carService = carService;
        this.customerDao = customerDao;
    }

    @Override
    public List<EmployeeDto> findAll() {
        return EmployeeAdapter.toDtoList(employeeDao.findAll());
    }

    @Override
    public EmployeeDto findById(long id) {
        return EmployeeAdapter.toDto(employeeDao.findById(id).orElseThrow(() -> new EmployeeNotFoundException("Employee not found")));
    }

    @Override
    public EmployeeDto save(EmployeeDto employeeDto) {
        return EmployeeAdapter.toDto(employeeDao.save(EmployeeAdapter.toDomain(employeeDto)));
    }

    @Override
    public EmployeeDto update(long id, EmployeeDto employeeDto) {
        Employee employee = employeeDao.findById(id).orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
        employee.setName(Optional.ofNullable(employeeDto.getName()).orElse(employee.getName()));
        employee.setEmail(Optional.ofNullable(employeeDto.getEmail()).orElse(employee.getEmail()));
        return EmployeeAdapter.toDto(employeeDao.save(employee));

    }

    @Override
    public void delete(long id) {
        employeeDao.deleteById(id);
    }

    @Override
    public ReservationDto makeReservation(long id, long customerId, ReservationDto reservationDto) {
        Employee employee = employeeDao.findById(id).orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
        Customer customer = customerDao.findById(customerId).orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        if (reservationDto.getReturnDate().isBefore(reservationDto.getReservationDate())) {
            throw new IllegalArgumentException("Return date cannot be before reservation date");
        }
        if (reservationDto.getReturnDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Return date cannot be before current date");
        }
        if (ChronoUnit.DAYS.between(reservationDto.getReservationDate(), reservationDto.getReturnDate()) > 30) {
            throw new IllegalArgumentException("Reservation cannot be longer than 30 days");
        }

        Reservation reservation = new Reservation();
        reservation.setReservationDate(LocalDateTime.now());
        reservation.setReturnDate(Optional.ofNullable(reservationDto.getReturnDate()).orElse(LocalDateTime.now().plus(1, DAYS)));
        reservation.setBookingEmployee(employee);
        reservation.setCarId(reservationDto.getCarId());
        reservation.setReservationStatus(ReservationStatus.RENTED);
        reservation = reservationDao.save(reservation);
        customer.getReservations().add(reservation);
        customerDao.save(customer);
        reservationDto = ReservationAdapter.toDto(reservation);
        try {
            carService.rentCar(reservationDto.getCarId(), reservationDto);
            return reservationDto;
        } catch (Exception e) {
            reservationDao.delete(reservation);
            throw e;
        }

    }

    @Override
    public ReservationDto returnCar(long id, ReservationDto reservationDto) {
        Employee employee = employeeDao.findById(id).orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
        Reservation reservation = reservationDao.findById(reservationDto.getReservationId()).orElseThrow(() -> new IllegalArgumentException("Reservation not found"));
        CarDto carDto = carService.findById(reservation.getCarId());
        reservation.setReturnDate(LocalDateTime.now());
        if (reservationDto.getPaymentInfoDto() == null)
            throw new IllegalArgumentException("Payment info cannot be null");
        PaymentInfo paymentInfo = PaymentInfoAdapter.toPaymentInfo(reservationDto.getPaymentInfoDto());
        if (paymentInfo.getCardHolderName() == null || paymentInfo.getCreditCardNumber() == null
                || paymentInfo.getExpirationDate() == null || paymentInfo.getSecurityCode() == null)
            throw new IllegalArgumentException("Card Info cannot be empty");
        if (paymentInfo.getCardHolderName().isEmpty() || paymentInfo.getCreditCardNumber().isEmpty()
                || paymentInfo.getExpirationDate().isEmpty() || paymentInfo.getSecurityCode().isEmpty())
            throw new IllegalArgumentException("Card Info cannot be empty");

        paymentInfo.setPaymentStatus(PaymentStatus.PAID);
        paymentInfo.setPaymentDate(LocalDateTime.now());
        paymentInfo.setAmount(BigDecimal.valueOf(Period.between(reservation.getReservationDate().toLocalDate(), reservation.getReturnDate().toLocalDate()).getDays())
                .multiply(carDto.getPricePerDay()));
        reservation.setPaymentInfo(paymentInfo);
        reservation.setReturnEmployee(employee);
        reservation.setReservationStatus(ReservationStatus.RETURNED);
        reservation = reservationDao.save(reservation);
        reservationDto = ReservationAdapter.toDto(reservation);
        try {
            carService.returnCar(reservationDto.getCarId(), reservationDto);
            return reservationDto;
        } catch (Exception e) {
            reservationDao.delete(reservation);
            throw e;
        }
    }

    @Override
    public List<ReservationDto> getEmployeeReservations(long id) {
        return reservationDao.findAll().stream().filter(reservation -> reservation.getBookingEmployee().getEmployeeId() == id ||
                reservation.getReturnEmployee().getEmployeeId() == id).map(ReservationAdapter::toDto).collect(Collectors.toList());
    }
}
