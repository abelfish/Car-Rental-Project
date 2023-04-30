package ea.project.rentalapp.service.implementations;

import ea.project.rentalapp.dao.CustomerDao;
import ea.project.rentalapp.dao.ReservationDao;
import ea.project.rentalapp.domain.Customer;
import ea.project.rentalapp.domain.PaymentInfo;
import ea.project.rentalapp.domain.Reservation;
import ea.project.rentalapp.domain.enums.PaymentStatus;
import ea.project.rentalapp.domain.enums.ReservationStatus;
import ea.project.rentalapp.service.CarService;
import ea.project.rentalapp.service.CustomerService;
import ea.project.rentalapp.service.adapters.CustomerAdapter;
import ea.project.rentalapp.service.adapters.ReservationAdapter;
import ea.project.rentalapp.service.configuration.CarFleetProperties;
import ea.project.rentalapp.service.dto.CarDto;
import ea.project.rentalapp.service.dto.CustomerDto;
import ea.project.rentalapp.service.dto.PaymentInfoDto;
import ea.project.rentalapp.service.dto.ReservationDto;
import ea.project.rentalapp.service.exceptions.CustomerNotFoundException;
import ea.project.rentalapp.service.exceptions.InvalidInputException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    private final CarService carService;

    private final CustomerDao customerDao;
    private final ReservationDao reservationDao;

    private final CarFleetProperties carFleetProperties;


    public CustomerServiceImpl(CustomerDao customerDao, CarService carService, ReservationDao reservationDao, CarFleetProperties carFleetProperties) {
        this.customerDao = customerDao;
        this.carService = carService;
        this.reservationDao = reservationDao;
        this.carFleetProperties = carFleetProperties;

    }

    @Override
    public List<CustomerDto> findAll() {
        return CustomerAdapter.toCustomerDtoList(customerDao.findAll());
    }

    @Override
    public CustomerDto findById(Long id) {
        return CustomerAdapter.toCustomerDto(customerDao.findById(id).orElseThrow(() -> new CustomerNotFoundException("Customer with id:" + id + " not found")));
    }

    @Override
    public CustomerDto save(CustomerDto customerDto) {
        Customer customer = CustomerAdapter.toCustomer(customerDto);
        if (customerDao.existsById(customer.getId())) {
            throw new InvalidInputException("Customer with id:" + customer.getId() + " already exists");
        }
        if (customerDao.existsByEmail(customer.getEmail()))
            throw new InvalidInputException("Customer with email:" + customer.getEmail() + " already exists");
        return CustomerAdapter.toCustomerDto(customerDao.save(customer));
    }

    @Override
    public CustomerDto update(Long id, CustomerDto customerDto) {
        Customer customer = customerDao.findById(id).orElseThrow(() -> new CustomerNotFoundException("Customer with id:" + id + " not found"));
        customer.setCustomerNumber(Optional.ofNullable(customerDto.getCustomerNumber()).orElse(customer.getCustomerNumber()));
        customer.setName(Optional.ofNullable(customerDto.getName()).orElse(customer.getName()));
        customer.setEmail(Optional.ofNullable(customerDto.getEmail()).orElse(customer.getEmail()));
        return CustomerAdapter.toCustomerDto(customerDao.save(customer));
    }

    @Override
    public void delete(Long id) {
        if (!customerDao.existsById(id)) throw new CustomerNotFoundException("Customer with id:" + id + " not found");
        customerDao.deleteById(id);
    }

    @Override
    public CustomerDto findByCustomerNumber(String customerNumber) {
        return customerDao.findByCustomerNumber(customerNumber)
                .map(CustomerAdapter::toCustomerDto)
                .orElseThrow(() -> new CustomerNotFoundException("Customer with customer number:" + customerNumber + " not found"));
    }

    @Override
    public CustomerDto findByEmail(String email) {
        return customerDao.findByEmail(email)
                .map(CustomerAdapter::toCustomerDto)
                .orElseThrow(() -> new CustomerNotFoundException("Customer with email:" + email + " not found"));
    }

    @Override
    public List<CustomerDto> findByName(String name) {
        return customerDao.findByName(name).stream()
                .map(CustomerAdapter::toCustomerDto).toList();
    }

    @Override
    public ReservationDto reserveCar(Long customerId, ReservationDto reservationDto) {
        //check customer
        Customer customer = customerDao.findById(customerId).orElseThrow(() -> new CustomerNotFoundException("Customer with id:" + customerId + " not found"));
        //check car
        if (customer.getReservations().stream().filter(reservation -> reservation.getReservationStatus() == ReservationStatus.RESERVED ||
                reservation.getReservationStatus() == ReservationStatus.RENTED).count() > carFleetProperties.getMaxRental())
            throw new InvalidInputException("Customer with id:" + customerId + " can not rent more than " + carFleetProperties.getMaxRental() + " cars");
        if (reservationDto.getCarId() == null)
            throw new InvalidInputException("Please input car id");
        CarDto carDto = carService.findById(reservationDto.getCarId());
        assert carDto != null;
        if (carDto.getRentalStatus().equals("RENTED"))
            throw new InvalidInputException("Car with id:" + reservationDto.getCarId() + " is already rented");
        //check Reservation
        if (reservationDto.getReservationDate() == null || reservationDto.getReturnDate() == null)
            throw new InvalidInputException("Reservation date and return date must be provided");
        //create and persist reservation
        Reservation reservation = ReservationAdapter.toDomain(reservationDto);
        if (reservation.getPaymentInfo() == null)
            reservation.setPaymentInfo(new PaymentInfo());
        reservation.getPaymentInfo().setPaymentStatus(PaymentStatus.UNPAID);
        reservation.setReservationStatus(ReservationStatus.RESERVED);
        BigDecimal amount = carDto.getPricePerDay()
                .multiply(BigDecimal.valueOf(Period.between(reservation.getReservationDate().toLocalDate(), reservation.getReturnDate().toLocalDate()).getDays()));
        reservation.getPaymentInfo().setAmount(amount);
        reservation = reservationDao.save(reservation);
        boolean isSent = carService.rentCar(reservationDto.getCarId(), ReservationAdapter.toDto(reservation));
        //update customer
        if (isSent) {
            customer.getReservations().add(reservation);
            customer = customerDao.save(customer);
            return ReservationAdapter.toDto(reservation);
        } else {
            reservationDao.deleteById(reservation.getReservationId());
            throw new InvalidInputException("Car with id:" + reservation.getCarId() + " could not be rented");
        }
    }

    @Override
    public ReservationDto rentCar(long customerId, long reservationId) {
        Reservation reservation = reservationDao.findById(reservationId).orElseThrow(() -> new InvalidInputException("Please Reserve car first."));
        Customer customer = customerDao.findById(customerId).orElseThrow(() -> new CustomerNotFoundException("Customer does not exist"));
        if (!customer.getReservations().contains(reservation)) {
            throw new InvalidInputException("Customer did not make this reservation");
        }
        reservation.setReservationStatus(ReservationStatus.RENTED);
        return ReservationAdapter.toDto(reservationDao.save(reservation));

    }


    @Override
    public ReservationDto returnCar(Long customerId, ReservationDto reservationDto) {
        long reservationId = reservationDto.getReservationId();
        PaymentInfoDto paymentInfoDto = reservationDto.getPaymentInfoDto();
        Customer customer = customerDao.findById(customerId).orElseThrow(() -> new CustomerNotFoundException("Customer with id:" + customerId + " not found"));
        Reservation reservation = reservationDao.findById(reservationId).orElseThrow(() -> new InvalidInputException("Reservation with id:" + reservationId + " not found"));
        CarDto carDto = carService.findById(reservation.getCarId());
        assert carDto != null;
        if (carDto.getRentalStatus().equals("AVAILABLE"))
            throw new InvalidInputException("Car with id:" + reservation.getCarId() + " is not rented");
        reservation.setReturnDate(LocalDateTime.now());
        if (reservation.getPaymentInfo() == null) {
            reservation.setPaymentInfo(new PaymentInfo());
            reservation.getPaymentInfo().setPaymentStatus(PaymentStatus.UNPAID);
            reservation.getPaymentInfo().setAmount(BigDecimal.valueOf(Period.between(reservation.getReservationDate().toLocalDate(), reservation.getReturnDate().toLocalDate()).getDays())
                    .multiply(carDto.getPricePerDay()));
        }
        if (paymentInfoDto == null)
            throw new InvalidInputException("Payment info must be provided");
        if (paymentInfoDto.getCardHolderName() == null || paymentInfoDto.getCreditCardNumber() == null ||
                paymentInfoDto.getExpirationDate() == null || paymentInfoDto.getSecurityCode() == null)
            throw new InvalidInputException("Card holder name, card number, expiration date and security code must be provided");
        if (paymentInfoDto.getCardHolderName().isBlank() || paymentInfoDto.getCreditCardNumber().isBlank() ||
                paymentInfoDto.getExpirationDate().isBlank() || paymentInfoDto.getSecurityCode().isBlank())
            throw new InvalidInputException("Card holder name, card number, expiration date and security code must be provided");
        reservation.getPaymentInfo().setCardHolderName(paymentInfoDto.getCardHolderName());
        reservation.getPaymentInfo().setCreditCardNumber(paymentInfoDto.getCreditCardNumber());
        reservation.getPaymentInfo().setExpirationDate(paymentInfoDto.getExpirationDate());
        reservation.getPaymentInfo().setSecurityCode(paymentInfoDto.getSecurityCode());
        reservation.getPaymentInfo().setPaymentStatus(PaymentStatus.PAID);
        reservation.getPaymentInfo().setPaymentDate(LocalDateTime.now());
        reservation.setReservationStatus(ReservationStatus.RETURNED);
        reservation = reservationDao.save(reservation);
        boolean isSent = carService.returnCar(reservation.getCarId(), ReservationAdapter.toDto(reservation));
        if (isSent) {
            customer.getReservations().add(reservation);
            customer = customerDao.save(customer);
            return ReservationAdapter.toDto(reservation);
        } else {
            throw new InvalidInputException("Car with id:" + reservation.getCarId() + " could not be returned");
        }


    }

    @Override
    public List<ReservationDto> getRentedCars(long customerId) {
        Customer customer = customerDao.findById(customerId).orElseThrow(() -> new CustomerNotFoundException("Customer with id:" + customerId + " not found"));
        return customer.getReservations().stream()
                .map(ReservationAdapter::toDto).toList();
    }
}
