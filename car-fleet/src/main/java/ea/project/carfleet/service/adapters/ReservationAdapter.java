package ea.project.carfleet.service.adapters;

import ea.project.carfleet.domain.Reservation;
import ea.project.carfleet.service.dto.ReservationDto;

import java.util.List;

public class ReservationAdapter{
    public static ReservationDto toDto(Reservation reservation) {
        ReservationDto dto = new ReservationDto();
        dto.setReservationId(reservation.getReservationId());
        dto.setCustomerNumber(reservation.getCustomerNumber());
        dto.setStartDate(reservation.getStartDate());
        dto.setEndDate(reservation.getEndDate());
        return dto;
    }
    public static Reservation toDomain(ReservationDto dto) {
        Reservation reservation = new Reservation();
        reservation.setReservationId(dto.getReservationId());
        reservation.setCustomerNumber(dto.getCustomerNumber());
        reservation.setStartDate(dto.getStartDate());
        reservation.setEndDate(dto.getEndDate());
        return reservation;
    }
    public static List<ReservationDto> toDtoList(List<Reservation> reservationList) {
        return reservationList.stream().map(ReservationAdapter::toDto).toList();
    }
}
