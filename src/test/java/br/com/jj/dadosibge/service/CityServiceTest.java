package br.com.jj.dadosibge.service;

import br.com.jj.dadosibge.dto.CityParams;
import br.com.jj.dadosibge.model.City;
import br.com.jj.dadosibge.model.State;
import br.com.jj.dadosibge.repository.CityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CityServiceTest {

    @Mock
    private CityRepository repository;

    @InjectMocks
    private CityService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private State stateMock(String name, String shortName) {
        return State.builder()
                .name(name)
                .shortName(shortName)
                .id(new Random().nextInt())
                .countCities(99)
                .dt(LocalDateTime.now())
                .build();
    }

    private City cityMock(String name, String stateName, String stateShortName) {
        return City
                .builder().id(new Random().nextInt())
                .name(name)
                .state(stateMock(stateName, stateShortName))
                .shortNameState(stateShortName)
                .dt(LocalDateTime.now())
                .build();
    }

    private List<City> citiesMock() {
        return Arrays.asList(
                cityMock("São Paulo", "São Paulo", "SP"),
                cityMock("Rio de Janeiro", "Rio de Janeiro", "RJ"));
    }

    @Test
    void deveExecutarFindSemParametrosComSucesso() {
        var params = new CityParams("", "", 0);
        when(repository.findAll()).thenReturn(citiesMock());

        assertFalse(service.find(params).isEmpty());
        verify(repository, times(1)).findAll();
    }

    @Test
    void deveExecutarFindPorIdComSucesso() {
        var params = new CityParams("", "", 999);
        when(repository.findItemById(anyInt())).thenReturn(Optional.of(cityMock("Teste", "TS", "Teste")));

        assertFalse(service.find(params).isEmpty());
        verify(repository, times(1)).findItemById(anyInt());
    }

    @Test
    void deveExecutarFindPorStateComSucesso() {
        var params = new CityParams("SP",null, null);
        when(repository.findItemsByState(anyString())).thenReturn(citiesMock());

        assertFalse(service.find(params).isEmpty());
        verify(repository, times(1)).findItemsByState(anyString());
    }

    @Test
    void deveExecutarFindPorNameComSucesso() {
        var params = new CityParams(null, "São Paulo", null);
        when(repository.findItemByName(anyString())).thenReturn(citiesMock());

        var values = service.find(params);
        assertFalse(values.isEmpty());
        verify(repository, times(1)).findItemByName(anyString());
    }

    @Test
    void deveExecutarSaveComSucesso() {
        String nameCity  ="Florianópolis";
        String stateShortName = "SC";
        String stateName = "Santa Catarina";

        var city = cityMock(nameCity, stateName, stateShortName);
        city.setCode(UUID.randomUUID().toString());

        when(repository.save(any(City.class))).thenReturn(city);
        var value = service.save(citiesMock().get(0));
        assertEquals(nameCity, value.getName());
        assertEquals(stateShortName, value.getShortNameState());
        assertEquals(stateName, value.getState().getName());

        verify(repository, times(1)).save(any(City.class));
    }

    @Test
    void deveExecutarDeleteAllComSucesso() {
        doNothing().when(repository).deleteAll();
        assertDoesNotThrow(()-> service.deleteAll());
        verify(repository, times(1)).deleteAll();
    }
}