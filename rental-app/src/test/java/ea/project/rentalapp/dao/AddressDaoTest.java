package ea.project.rentalapp.dao;

import ea.project.rentalapp.domain.Address;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.NoSuchElementException;

import static jdk.dynalink.linker.support.Guards.isNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AddressDaoTest {

    @Autowired
    private AddressDao addressDao;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testSaveAddress() {
        Address address = new Address();
        address.setCity("Fairfield");
        address.setState("Iowa");
        address.setStreet("1000 N 4th St");
        address.setZipCode("52557");
        addressDao.save(address);
        Address address1 = entityManager.find(Address.class, address.getId());
        assertEquals(address.getCity(), address1.getCity());
    }

    @Test
    void testGetAddress() {
        Address address = new Address();
        address.setCity("Fairfield");
        address.setState("Iowa");
        address.setStreet("1000 N 4th St");
        address.setZipCode("52557");
        entityManager.persist(address);
        Address address1 = addressDao.findById(address.getId()).get();
        assertEquals(address.getCity(), address1.getCity());
    }

    @Test
    void testUpdateAddress() {
        Address address = new Address();
        address.setCity("Fairfield");
        address.setState("Iowa");
        address.setStreet("1000 N 4th St");
        address.setZipCode("52557");
        entityManager.persist(address);
        Address address1 = addressDao.findById(address.getId()).get();
        address1.setCity("Chicago");
        addressDao.save(address1);
        Address address2 = addressDao.findById(address.getId()).get();
        assertEquals("Chicago", address2.getCity());
    }



}