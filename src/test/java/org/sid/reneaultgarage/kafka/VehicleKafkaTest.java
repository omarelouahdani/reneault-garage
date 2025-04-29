package org.sid.reneaultgarage.kafka;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = { "vehicles-topic" })
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class VehicleKafkaTest {

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @BeforeEach
    void setUp() {
        // On peut configurer la propriété bootstrap-servers si besoin
        System.setProperty(
                "spring.kafka.bootstrap-servers",
                embeddedKafkaBroker.getBrokersAsString()
        );
    }

    // plus de méthode @AfterAll destroy() manuelle :
    // @DirtiesContext se charge de tout arrêter proprement

    @Test
    void contextLoads() {
        // simple vérification que le broker est bien démarré
        assertThat(embeddedKafkaBroker.getTopics())
                .contains("vehicles-topic");
    }
}
