package org.sid.reneaultgarage.kafka;

import org.sid.reneaultgarage.model.Vehicle;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class VehicleConsumer {

    @KafkaListener(topics = "vehicles-topic", groupId = "garage-group")
    public void consume(Vehicle v) {

        System.out.println("Consumed vehicle: " + v.getId());
    }
}
