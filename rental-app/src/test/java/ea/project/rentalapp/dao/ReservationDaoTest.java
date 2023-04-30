package ea.project.rentalapp.dao;

import ea.project.rentalapp.domain.Customer;
import ea.project.rentalapp.domain.Reservation;
import ea.project.rentalapp.domain.enums.ReservationStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ReservationDaoTest {

    @Autowired
    private ReservationDao reservationDao;
    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setUp() {
        Reservation reservation = new Reservation();
        reservation.setCarId("1L");
        reservation.setReservationStatus(ReservationStatus.RESERVED);
        reservation.setReservationDate(LocalDateTime.now());
        entityManager.persist(reservation);

    }

    @Test
    void findAll() {
        List<Reservation> reservation = reservationDao.findAll();
        assertTrue(reservation != null);
    }
    @Test
    void updateReservation(){
        Reservation reservation = reservationDao.findAll().get(0);
        reservation.setCarId("2L");
        reservation.setReservationStatus(ReservationStatus.RETURNED);
        entityManager.persist(reservation);
        assertEquals(ReservationStatus.RETURNED, reservation.getReservationStatus());
    }
}