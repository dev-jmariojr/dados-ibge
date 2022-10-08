package br.com.jj.dadosibge.service;

import br.com.jj.dadosibge.model.City;
import br.com.jj.dadosibge.repository.CityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@ExtendWith(MockitoExtension.class)
class CityServiceImplTest {

    @Mock
    private CityRepository repository;

    private CityService service;

    private List<City> cities;

    @BeforeEach
    void setUp() {
        service = new CityServiceImpl(repository);
        cities = Arrays
                .asList(
                        City
                                .builder()
                                .code(new Random().ints(20).toString())
                                .id(1)
                                .name("Teste")
                                .dt(LocalDateTime.now())
                                .shortNameState("TS")
                                .build()
                );
    }

    @Test
    void deleteAll() {

    }

    @Test
    void findAll() {
        Mockito.when(service.findAll()).thenReturn(cities);
        Assertions.assertFalse(service.findAll()==null);
    }

    @Test
    void findItemsByStateIsOk() {
        Mockito.when(service.findItemsByState("TS")).thenReturn(cities);
        Assertions.assertFalse(service.findItemsByState("TS")==null);
    }

    @Test
    void findByCodeIsOk() {
    }

    @Test
    void findByIdIsOk() {
        City city = cities.get(0);
        Mockito.when(service.findById(1)).thenReturn(city);
        Assertions.assertNotEquals(service.findById(1),null);
    }

    @Test
    void findByNameIsOk() {
        Mockito.when(service.findByName("TESTE")).thenReturn(cities);
        Assertions.assertNotEquals(service.findByName("TESTE"),null);
    }

    @Test
    void saveIsNull() {
        City cityNew = City
                .builder()
                .id(99)
                .name("City Test")
                .build();

        Mockito.when(service.save(cityNew)).thenReturn(null);
        Assertions.assertNull(service.save(cityNew));
    }
}