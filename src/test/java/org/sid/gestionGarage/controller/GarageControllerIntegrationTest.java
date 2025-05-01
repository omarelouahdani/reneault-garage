package org.sid.gestionGarage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sid.gestionGarage.dto.GarageDto;
import org.sid.gestionGarage.model.Accessory;
import org.sid.gestionGarage.model.Garage;
import org.sid.gestionGarage.model.Vehicle;
import org.sid.gestionGarage.repository.AccessoryRepository;
import org.sid.gestionGarage.repository.GarageRepository;
import org.sid.gestionGarage.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters=false)
class GarageControllerIntegrationTest {

    @Autowired private MockMvc mvc;
    @Autowired private GarageRepository repo;
    @Autowired private ObjectMapper mapper;
    @Autowired private GarageRepository gRepo;
    @Autowired private VehicleRepository vRepo;
    @Autowired private AccessoryRepository aRepo;

    private UUID id;
    private UUID garageId;

    @BeforeEach void setup() {
        repo.deleteAll();
        Garage g=new Garage(); g.setName("G1"); g.setAddress("A"); g.setTelephone("T"); g.setEmail("e@e");
        id=repo.save(g).getId();
        garageId = gRepo.save(g).getId();
    }

    @Test void crudCycle() throws Exception {
        GarageDto dto=GarageDto.builder().name("G2").address("B").telephone("T2").email("e2@e").build();
        String json=mapper.writeValueAsString(dto);
        // create
        mvc.perform(post("/api/garages").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("G2"));
        // get
        mvc.perform(get("/api/garages/"+id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("G1"));
        // list
        mvc.perform(get("/api/garages?page=0&size=5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
        // update
        dto.setName("G1-upd"); json=mapper.writeValueAsString(dto);
        mvc.perform(put("/api/garages/"+id).contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("G1-upd"));
        // delete
        mvc.perform(delete("/api/garages/"+id))
                .andExpect(status().isNoContent());
    }

    @Test
    void search_ByFuelType_and_Accessory() throws Exception {
        // créer un véhicule Essence avec accessoire GPS
        Vehicle v = new Vehicle(); v.setGarage(gRepo.getById(garageId)); v.setBrand("B"); v.setManufactureYear(2020); v.setFuelType("Essence");
        v = vRepo.save(v);
        Accessory a = new Accessory(); a.setVehicle(v); a.setName("GPS"); a.setDescription(""); a.setPrice(10); a.setType("Nav");
        aRepo.save(a);

        mvc.perform(get("/api/garages/search")
                        .param("fuelType", "Essence")
                        .param("accessory", "GPS")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(garageId.toString()));
    }
}