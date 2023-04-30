package ea.project.rentalapp.service.adapters;

import ea.project.rentalapp.domain.Customer;
import ea.project.rentalapp.service.dto.CustomerDto;

import java.util.List;

public class CustomerAdapter {
    public static CustomerDto toCustomerDto(Customer customer){
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customer.getId());
        customerDto.setCustomerNumber(customer.getCustomerNumber());
        customerDto.setName(customer.getName());
        customerDto.setEmail(customer.getEmail());
        if(customer.getAddress() != null)
            customerDto.setAddressDto(AddressAdapter.toAddressDto(customer.getAddress()));
        customerDto.setReservationDtoList(ReservationAdapter.toDtoList(customer.getReservations()));
        return customerDto;
    }
    public static Customer toCustomer(CustomerDto customerDto){
        Customer customer = new Customer();
        customer.setId(customerDto.getId());
        customer.setCustomerNumber(customerDto.getCustomerNumber());
        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        if(customerDto.getAddressDto() != null)
            customer.setAddress(AddressAdapter.toAddress(customerDto.getAddressDto()));
        customer.setReservations(ReservationAdapter.toReservationList(customerDto.getReservationDtoList()));
        return customer;
    }
    public static List<CustomerDto> toCustomerDtoList(List<Customer> customerList){
        return customerList.stream().map(CustomerAdapter::toCustomerDto).toList();
    }
}
