package ea.project.rentalapp.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerDto {
    private long id;
    private String customerNumber;
    private String name;
    private String email;
    private List<ReservationDto> reservationDtoList;
    private AddressDto addressDto;
}
