package ea.project.rentalapp.service.adapters;

import ea.project.rentalapp.domain.Address;
import ea.project.rentalapp.service.dto.AddressDto;

import java.util.List;

public class AddressAdapter {
    public static AddressDto toAddressDto(Address address) {
        return new AddressDto(address.getId(), address.getStreet(), address.getCity(), address.getState(), address.getZipCode());
    }

    public static Address toAddress(AddressDto addressDto) {
        return new Address(addressDto.getId(), addressDto.getStreet(), addressDto.getCity(), addressDto.getState(), addressDto.getZipCode());
    }

    public static List<AddressDto> toAddressDtoList(List<Address> addressList) {
        return addressList.stream().map(AddressAdapter::toAddressDto).toList();
    }
}
