package org.sid.gestionGarage.service.impl;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.mockito.*;
import org.sid.gestionGarage.exception.NotFoundException;
import org.sid.gestionGarage.mapper.GarageMapper;
import org.sid.gestionGarage.model.Garage;
import org.sid.gestionGarage.repository.GarageRepository;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.*;

import static org.mockito.Mockito.*;

class GarageServiceImplTest {

    @Mock private GarageRepository repo;
    @InjectMocks private GarageServiceImpl service;
    @Mock private GarageMapper mapper;

    private Garage garage;
    private UUID id;
    private Garage g1, g2;
    private PageRequest pr = PageRequest.of(0, 5);

    @BeforeEach void init() {
        MockitoAnnotations.openMocks(this);
        id=UUID.randomUUID(); garage=new Garage();
        garage.setName("G");
        g1 = new Garage(); g1.setId(UUID.randomUUID()); g1.setName("G1");
        g2 = new Garage(); g2.setId(UUID.randomUUID()); g2.setName("G2");
    }

    @Test void create_shouldSave() {
        when(repo.save(garage)).thenReturn(garage);
        assertEquals(garage, service.create(garage));
    }

    @Test void get_shouldReturn_whenExists() {
        when(repo.findById(id)).thenReturn(Optional.of(garage));
        assertEquals(garage, service.get(id));
    }

    @Test void get_shouldThrow_whenMissing() {
        when(repo.findById(id)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, ()->service.get(id));
    }

    @Test void delete_shouldThrow_whenMissing() {
        when(repo.existsById(id)).thenReturn(false);
        assertThrows(NotFoundException.class, ()->service.delete(id));
    }

    @Test void list_shouldPage() {
        Page<Garage> page=new PageImpl<>(List.of(garage));
        when(repo.findAll(any(Pageable.class))).thenReturn(page);
        assertEquals(1, service.list(PageRequest.of(0,1)).getTotalElements());
    }

    @Test
    void search_ByFuelType() {
        when(repo.findAll(any(Specification.class), eq(pr)))
                .thenReturn(new PageImpl<>(List.of(g1)));

        Page<Garage> res = service.search(
                (root, q, cb) -> cb.equal(root.get("name"), "G1"), pr);

        assertThat(res.getContent()).containsExactly(g1);
        verify(repo).findAll(any(Specification.class), eq(pr));
    }
}