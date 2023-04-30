package ea.project.rentalapp.dao;

import ea.project.rentalapp.domain.Customer;
import ea.project.rentalapp.service.dto.CustomerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerDaoTest {

    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private TestEntityManager entityManager;


    @BeforeEach
    void setUp() {
        Customer customerDto = new Customer();
        customerDto.setName("John");
        customerDto.setCustomerNumber("123456789");
        customerDto.setEmail("email123@gmail.com");
        customerDao.save(customerDto);
    }


    @Test
    void findByName() {
        List<Customer> customer = customerDao.findByName("John");
        assertEquals("John", customer.get(0).getName());
    }

    @Test
    void findByCustomerNumber() {
        Customer customer = customerDao.findByCustomerNumber("123456789").get();
        assertEquals("123456789", customer.getCustomerNumber());
    }

    @Test
    void findByEmail() {
        Customer customer = customerDao.findByEmail("email123@gmail.com").get();
        assertEquals("email123@gmail.com", customer.getEmail());
    }

    @Test
    void existsByEmail() {
        boolean customer = customerDao.existsByEmail("email123@gmail.com");
        assertTrue(customer);
    }
}