package org.sid.gestionGarage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.sid.gestionGarage.dto.AccessoryDto;
import org.sid.gestionGarage.model.Garage;
import org.sid.gestionGarage.model.Vehicle;
import org.sid.gestionGarage.repository.GarageRepository;
import org.sid.gestionGarage.repository.VehicleRepository;

import java.util.UUID;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@EmbeddedKafka(partitions = 1, topics = { "vehicles-topic" })
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
class AccessoryControllerIntegrationTest {

    @Autowired private MockMvc mvc;
    @Autowired private ObjectMapper mapper;
    @Autowired private GarageRepository gRepo;
    @Autowired private VehicleRepository vRepo;

    private UUID garageId, vehicleId;

    @BeforeEach void setup() {
        vRepo.deleteAll(); gRepo.deleteAll();
        Garage g = gRepo.save(Garage.builder().name("G").address("A").telephone("T").email("e@e.com").build());
        Vehicle v = vRepo.save(Vehicle.builder().brand("B").manufactureYear(2020).fuelType("F").garage(g).build());
        vehicleId = v.getId();
    }

    @Test void  addListDeleteAccessory() throws Exception {
        AccessoryDto dto = AccessoryDto.builder().name("Acc").description("D").price(10).type("T").build();
        String json = mapper.writeValueAsString(dto);

        // add
        mvc.perform(post("/api/vehicles/"+vehicleId+"/accessories").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Acc"));

        // list
        mvc.perform(get("/api/vehicles/"+vehicleId+"/accessories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type").value("T"));
    }
}