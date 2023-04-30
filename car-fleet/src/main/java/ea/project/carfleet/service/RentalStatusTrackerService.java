package ea.project.carfleet.service;

import ea.project.carfleet.domain.RentalStatus;
import ea.project.carfleet.domain.RentalStatusTracker;
import ea.project.carfleet.service.dto.RentalStatusTrackerDTO;

import java.util.List;

public interface RentalStatusTrackerService {

    void incrementRentalStatusTracker(RentalStatus rentalStatus);
    void decrementRentalStatusTracker(RentalStatus rentalStatus);

    List<RentalStatusTrackerDTO> getAllRentalStatus();

    RentalStatusTrackerDTO getRentalStatus(RentalStatus rentalStatus);
}
