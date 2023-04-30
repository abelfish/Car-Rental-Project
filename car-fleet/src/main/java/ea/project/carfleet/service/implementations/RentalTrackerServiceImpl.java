package ea.project.carfleet.service.implementations;

import ea.project.carfleet.dao.RentalStatusTrackerDao;
import ea.project.carfleet.domain.RentalStatus;
import ea.project.carfleet.domain.RentalStatusTracker;
import ea.project.carfleet.service.RentalStatusTrackerService;
import ea.project.carfleet.service.adapters.RentalStatusAdapter;
import ea.project.carfleet.service.dto.RentalStatusTrackerDTO;
import ea.project.carfleet.service.exceptions.InvalidInputException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class RentalTrackerServiceImpl implements RentalStatusTrackerService {

    private final RentalStatusTrackerDao rentalStatusTrackerDao;


    public RentalTrackerServiceImpl(RentalStatusTrackerDao rentalStatusTrackerDao) {
        this.rentalStatusTrackerDao = rentalStatusTrackerDao;
    }

    @Override
    public void incrementRentalStatusTracker(RentalStatus status) {
        Optional<RentalStatusTracker> rentalStatusTracker = rentalStatusTrackerDao.findByRentalStatus(status);
        if (rentalStatusTracker.isPresent()) {
            rentalStatusTracker.get().setCount(rentalStatusTracker.get().getCount() + 1);
            rentalStatusTrackerDao.save(rentalStatusTracker.get());
            return;
        }
        RentalStatusTracker newRentalStatusTracker = new RentalStatusTracker();
        newRentalStatusTracker.setRentalStatus(status);
        newRentalStatusTracker.setCount(1);
        rentalStatusTrackerDao.save(newRentalStatusTracker);

    }

    @Override
    public void decrementRentalStatusTracker(RentalStatus status) {
        Optional<RentalStatusTracker> rentalStatusTracker = rentalStatusTrackerDao.findByRentalStatus(status);
        if (rentalStatusTracker.isPresent()) {
            rentalStatusTracker.get().setCount(rentalStatusTracker.get().getCount() - 1);
            rentalStatusTrackerDao.save(rentalStatusTracker.get());
            return;
        }
        RentalStatusTracker newRentalStatusTracker = new RentalStatusTracker();
        newRentalStatusTracker.setRentalStatus(status);
        newRentalStatusTracker.setCount(1);
        rentalStatusTrackerDao.save(newRentalStatusTracker);
    }

    @Override
    public List<RentalStatusTrackerDTO> getAllRentalStatus() {
        return RentalStatusAdapter.toRentalStatusTrackerDTOList(rentalStatusTrackerDao.findAll());
    }

    @Override
    public RentalStatusTrackerDTO getRentalStatus(RentalStatus status) {
        Optional<RentalStatusTracker> rentalStatusTracker = rentalStatusTrackerDao.findByRentalStatus(status);
        return rentalStatusTracker.map(RentalStatusAdapter::toRentalStatusTrackerDTO).orElseThrow(()-> new InvalidInputException("Rental Status not found"));
    }
}
