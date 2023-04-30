package ea.project.carfleet.service.adapters;

import ea.project.carfleet.domain.RentalStatusTracker;
import ea.project.carfleet.service.dto.RentalStatusTrackerDTO;

import java.util.List;

public class RentalStatusAdapter {

    public static RentalStatusTrackerDTO toRentalStatusTrackerDTO(RentalStatusTracker rentalStatusTracker) {
        RentalStatusTrackerDTO rentalStatusTrackerDTO = new RentalStatusTrackerDTO();
        rentalStatusTrackerDTO.setRentalStatus(rentalStatusTracker.getRentalStatus());
        rentalStatusTrackerDTO.setCount(rentalStatusTracker.getCount());
        return rentalStatusTrackerDTO;
    }

    public static RentalStatusTracker toRentalStatusTracker(RentalStatusTrackerDTO rentalStatusTrackerDTO) {
        RentalStatusTracker rentalStatusTracker = new RentalStatusTracker();
        rentalStatusTracker.setRentalStatus(rentalStatusTrackerDTO.getRentalStatus());
        rentalStatusTracker.setCount(rentalStatusTrackerDTO.getCount());
        return rentalStatusTracker;
    }

    public static List<RentalStatusTrackerDTO> toRentalStatusTrackerDTOList(List<RentalStatusTracker> rentalStatusTrackerList) {
        return rentalStatusTrackerList.stream().map(RentalStatusAdapter::toRentalStatusTrackerDTO).toList();
    }
}
