package org.sid.gestionGarage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.sid.gestionGarage.dto.VehicleDto;
import org.sid.gestionGarage.model.Garage;
import org.sid.gestionGarage.repository.GarageRepository;
import org.sid.gestionGarage.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@EmbeddedKafka(partitions = 1, topics = { "vehicles-topic" })
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class VehicleControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private GarageRepository gRepo;
    @Autowired
    private VehicleRepository vRepo;
    @Autowired
    private ObjectMapper mapper;

    private UUID garageId;

    @BeforeEach
    void setup() {
        // Nettoyer la base entre chaque test
        vRepo.deleteAll();
        gRepo.deleteAll();
        Garage g = Garage.builder()
                .name("G1")
                .address("Address")
                .telephone("0123456789")
                .email("g1@test.com")
                .build();
        garageId = gRepo.save(g).getId();
    }

    @Test
    void addGetUpdateDeleteVehicle() throws Exception {
        // 1. Ajouter un véhicule
        VehicleDto dto = VehicleDto.builder()
                .brand("Clio")
                .manufactureYear(2020)
                .fuelType("Diesel")
                .build();
        String json = mapper.writeValueAsString(dto);
        String response = mvc.perform(post("/api/garages/" + garageId + "/vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.brand").value("Clio"))
                .andReturn().getResponse().getContentAsString();

        VehicleDto created = mapper.readValue(response, VehicleDto.class);
        UUID vehicleId = created.getId();

        // 2. Récupérer via listByGarage
        mvc.perform(get("/api/garages/" + garageId + "/vehicles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(vehicleId.toString()));

        // 3. Modifier le véhicule
        created.setBrand("Megane");
        String updatedJson = mapper.writeValueAsString(created);
        mvc.perform(put("/api/garages/" + garageId + "/vehicles/" + vehicleId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brand").value("Megane"));

        // 4. Lister par modèle
        mvc.perform(get("/api/vehicles?brand=Megane"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(vehicleId.toString()));

        // 5. Supprimer le véhicule
        mvc.perform(delete("/api/garages/" + garageId + "/vehicles/" + vehicleId))
                .andExpect(status().isNoContent());

        // 6. Vérifier suppression
        mvc.perform(get("/api/garages/" + garageId + "/vehicles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isEmpty());
    }
}