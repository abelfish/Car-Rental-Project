package ea.project.rentalapp.service.jms;

import com.fasterxml.jackson.databind.ObjectMapper;
import ea.project.rentalapp.service.dto.ReservationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;


@Component
public class ReservationJmsSender{

    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    public void send(String queue,ReservationDto message) {
        String jsonMessage = "";
        try {
            jsonMessage = objectMapper.writeValueAsString(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        jmsTemplate.convertAndSend(queue, jsonMessage);
    }
}
