package org.sid.gestionGarage.kafka;

import org.sid.gestionGarage.model.Vehicle;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class VehicleConsumer {

    @KafkaListener(topics = "vehicles-topic", groupId = "garage-group")
    public void consume(Vehicle v) {
//todo
        System.out.println("Consumed vehicle: " + v.getId());
    }
}
