package ea.project.rentalapp.service.adapters;

import ea.project.rentalapp.domain.Reservation;
import ea.project.rentalapp.service.dto.ReservationDto;

import java.util.List;

public class ReservationAdapter {
    public static ReservationDto toDto(Reservation reservation) {
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setReservationId(reservation.getReservationId());
        reservationDto.setReservationDate(reservation.getReservationDate());
        reservationDto.setReturnDate(reservation.getReturnDate());
        if (reservation.getPaymentInfo() != null)
            reservationDto.setPaymentInfoDto(PaymentInfoAdapter.toDto(reservation.getPaymentInfo()));
        reservationDto.setReservationStatus(reservation.getReservationStatus());
        reservationDto.setCarId(reservation.getCarId());
        if (reservation.getBookingEmployee() != null)
            reservationDto.setBookingEmployeeDto(EmployeeAdapter.toDto(reservation.getBookingEmployee()));
        if (reservation.getReturnEmployee() != null)
            reservationDto.setReturnEmployeeDto(EmployeeAdapter.toDto(reservation.getReturnEmployee()));
        return reservationDto;
    }

    public static Reservation toDomain(ReservationDto reservationDto) {
        Reservation reservation = new Reservation();
        reservation.setReservationId(reservationDto.getReservationId());
        reservation.setReservationDate(reservationDto.getReservationDate());
        reservation.setReturnDate(reservationDto.getReturnDate());
        if (reservationDto.getPaymentInfoDto() != null)
            reservation.setPaymentInfo(PaymentInfoAdapter.toPaymentInfo(reservationDto.getPaymentInfoDto()));
        reservation.setReservationStatus(reservationDto.getReservationStatus());
        reservation.setCarId(reservationDto.getCarId());
        if (reservationDto.getBookingEmployeeDto() != null)
            reservation.setBookingEmployee(EmployeeAdapter.toDomain(reservationDto.getBookingEmployeeDto()));
        if (reservationDto.getReturnEmployeeDto() != null)
            reservation.setReturnEmployee(EmployeeAdapter.toDomain(reservationDto.getReturnEmployeeDto()));
        return reservation;
    }

    public static List<ReservationDto> toDtoList(List<Reservation> reservationList) {
        if(reservationList == null)
            return null;
        return reservationList.stream().map(ReservationAdapter::toDto).toList();
    }

    public static List<Reservation> toReservationList(List<ReservationDto> reservationDtoList) {
        if(reservationDtoList == null)
            return null;
        return reservationDtoList.stream().map(ReservationAdapter::toDomain).toList();
    }
}
